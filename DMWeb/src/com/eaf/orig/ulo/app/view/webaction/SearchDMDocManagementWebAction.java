package com.eaf.orig.ulo.app.view.webaction;

import java.util.ArrayList;
import java.util.HashMap;

import org.apache.log4j.Logger;

import com.eaf.core.ulo.common.engine.SearchFormHandler;
import com.eaf.core.ulo.common.engine.SearchQueryEngine;
import com.eaf.j2ee.pattern.control.FrontController;
import com.eaf.j2ee.pattern.control.event.Event;
import com.eaf.j2ee.pattern.control.event.EventResponse;
import com.eaf.j2ee.pattern.view.webaction.WebAction;
import com.eaf.j2ee.pattern.view.webaction.WebActionHelper;
import com.eaf.orig.profile.model.UserDetailM;
import com.eaf.orig.shared.service.OrigServiceLocator;
import com.eaf.orig.ulo.constant.MConstant;

public class SearchDMDocManagementWebAction  extends WebActionHelper implements WebAction{
	private static transient Logger logger = Logger.getLogger(SearchDMDocManagementWebAction.class);
	@Override
	public Event toEvent() {
		return null;
	}

	@Override
	public boolean requiredModelRequest() {
		return false;
	}

	@Override
	public boolean processEventResponse(EventResponse response) {
		return false;
	}

	@Override
	public boolean preModelRequest() {
		UserDetailM userM =	(UserDetailM) getRequest().getSession().getAttribute("ORIGUser");
		SearchFormHandler searchForm = new SearchFormHandler(getRequest()){	
			@Override
			public void postSearchResult(ArrayList<HashMap<String, Object>> List) {
				logger.debug("do postSearchResult()..");
			}
		};
		searchForm.setDbType(OrigServiceLocator.WAREHOUSE_DB);
		searchForm.setNextPage(false);
		searchForm.setAtPage(1);
		searchForm.setSQL(createSelectSerchSQL(searchForm));
		try{
			SearchQueryEngine SearchEngine = new SearchQueryEngine();
				SearchEngine.search(searchForm);
		}catch(Exception e){
			logger.fatal("ERROR ",e);
		}		
		getRequest().getSession().setAttribute(SearchFormHandler.SearchForm,searchForm);
		return true;
	}

	@Override
	public int getNextActivityType() {
		return FrontController.PAGE;
	}
		
	private String createSelectSerchSQL(SearchFormHandler searchForm){
		StringBuilder sql = new StringBuilder();
		sql.append("SELECT DISTINCT DM_DOC.DM_ID,");
		sql.append(" DM_DOC.REF_NO2 AS APPLICATION_NO,");
		sql.append(" DM_DOC.CONTAINER_NO ||'/'||DM_DOC.BOX_NO AS  FILE_BOX_NO,");
		sql.append(" DM_DOC.TH_FIRST_NAME ||' '||DM_DOC.TH_LAST_NAME AS  APPLICANT_NAME,");
		sql.append(" DM_DOC.ID_NO,");
		sql.append(" DM_DOC.STATUS,");
		sql.append(" TO_CHAR(MAX(DM_TRANSACTION.ACTION_DATE),'dd/mm/yyyy | HH24:MI') AS ACTION_DATE_TIME");
		sql.append(" FROM DM_DOC");
		sql.append(" LEFT JOIN DM_TRANSACTION ON DM_DOC.DM_ID  = DM_TRANSACTION.DM_ID WHERE DM_DOC.STATUS <> ?");
		searchForm.setString(MConstant.DM_STATUS.NOT_IN_WAREHOUSE);
		
		if(!searchForm.empty("DM_FIRST_NAME")){
			sql.append(" AND DM_DOC.TH_FIRST_NAME LIKE ? ");
			searchForm.setString(searchForm.getString("DM_FIRST_NAME")+"%");
		}
		if(!searchForm.empty("DM_LAST_NAME")){
			sql.append(" AND DM_DOC.TH_LAST_NAME LIKE ? ");
			searchForm.setString(searchForm.getString("DM_LAST_NAME")+"%");
		}
		if(!searchForm.empty("DM_IMPORTANT_DOC_NO")){
			sql.append(" AND DM_DOC.ID_NO = ? ");
			searchForm.setString(searchForm.getString("DM_IMPORTANT_DOC_NO"));
		}
		if(!searchForm.empty("DM_APPLICATION_NO")){
			sql.append(" AND   REF_NO2 = ? ");
			searchForm.setString(searchForm.getString("DM_APPLICATION_NO"));
		}
		if(!searchForm.empty("DM_BORROW_BY")){
			sql.append(" AND  DM_TRANSACTION.REQUESTED_USER= ? AND DM_TRANSACTION.ACTION ='"+MConstant.DM_MANAGEMENT_ACTION.BORROW_ACTION+"'");
			sql.append(" AND  TO_NUMBER(DM_TRANSACTION.DM_TRANSACTION_ID) = (SELECT MAX(TO_NUMBER(DT.DM_TRANSACTION_ID)) FROM DM_TRANSACTION DT WHERE  DT.DM_ID = DM_DOC.DM_ID )");
			searchForm.setString(searchForm.getString("DM_BORROW_BY"));
		}
		if(!searchForm.empty("DM_RETURN_BY")){
			sql.append(" AND  DM_TRANSACTION.REQUESTED_USER = ? AND DM_TRANSACTION.ACTION ='"+MConstant.DM_MANAGEMENT_ACTION.RETURN_ACTION+"'");
			sql.append(" AND TO_NUMBER(DM_TRANSACTION.DM_TRANSACTION_ID) = (SELECT MAX(TO_NUMBER(DT.DM_TRANSACTION_ID)) FROM DM_TRANSACTION DT WHERE  DT.DM_ID = DM_DOC.DM_ID )");
			searchForm.setString(searchForm.getString("DM_RETURN_BY"));
		}
		if(!searchForm.empty("DM_SEARCH_DOC_STATUS")){
			sql.append(" AND   DM_DOC.STATUS= ? ");
			searchForm.setString(searchForm.getString("DM_SEARCH_DOC_STATUS"));
		}
		sql.append(" GROUP BY DM_DOC.DM_ID, DM_DOC.REF_NO2");
		sql.append(" ,DM_DOC.CONTAINER_NO, DM_DOC.BOX_NO");
        sql.append(" ,DM_DOC.TH_FIRST_NAME, DM_DOC.TH_LAST_NAME");
        sql.append(" ,DM_DOC.ID_NO, DM_DOC.STATUS");
		sql.append(" ORDER BY APPLICATION_NO DESC");
		return sql.toString();
	}
}
