package com.eaf.orig.ulo.app.view.webaction;

import java.util.ArrayList;
import java.util.HashMap;

import org.apache.log4j.Logger;

import com.eaf.core.ulo.common.engine.SearchFormHandler;
import com.eaf.core.ulo.common.engine.SearchQueryEngine;
import com.eaf.core.ulo.common.util.Util;
import com.eaf.j2ee.pattern.control.FrontController;
import com.eaf.j2ee.pattern.control.event.Event;
import com.eaf.j2ee.pattern.control.event.EventResponse;
import com.eaf.j2ee.pattern.view.webaction.WebAction;
import com.eaf.j2ee.pattern.view.webaction.WebActionHelper;
import com.eaf.orig.profile.model.UserDetailM;
import com.eaf.orig.shared.service.OrigServiceLocator;
import com.eaf.orig.ulo.app.factory.DMModuleFactory;
import com.eaf.orig.ulo.dm.dao.DMApplicationGroupDAO;

public class SearchDMWareHouseWebAction extends WebActionHelper implements WebAction{
	private static transient Logger logger = Logger.getLogger(SearchDMWareHouseWebAction.class);
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
				DMApplicationGroupDAO dmDao = DMModuleFactory.getDMApplicationGroupDAO();
				if(!Util.empty(List)){
					for(HashMap<String, Object> hData :List){
						 
					}
				}
			}
		};				
			searchForm.setDbType(OrigServiceLocator.WAREHOUSE_DB);
			searchForm.setNextPage(false);
			searchForm.setAtPage(1);
			searchForm.setSQL(createSearchSQL(searchForm));
		try{
			SearchQueryEngine SearchEngine = new SearchQueryEngine();
				SearchEngine.search(searchForm);
		}catch(Exception e){
			logger.fatal("ERROR ",e);
		}		
		getRequest().getSession().setAttribute(SearchFormHandler.SearchForm,searchForm);
		return true;
	}
	public String createSearchSQL(SearchFormHandler searchForm){
		StringBuilder SQL = new StringBuilder();
			SQL.append(" SELECT DISTINCT  DM_ID,"); 
			SQL.append(" REF_NO2  AS APPLICATION_NO,");
			SQL.append(" DM_DOC.PARAM4 AS PRODUCT_NAME,");			
			SQL.append(" DM_DOC.PARAM1 AS APPLICATION_STATUS,");
			SQL.append(" TH_FIRST_NAME ||' '||TH_LAST_NAME AS  APPLICANT_NAME,");
			SQL.append(" ID_NO,");
			SQL.append(" STATUS,");
			SQL.append(" TO_CHAR(UPDATE_DATE,'dd/mm/yyyy | HH24:MI') AS LAST_STORE_DATE");
			SQL.append(" FROM DM_DOC ");
			SQL.append(" WHERE 1=1 ");
			if(!searchForm.empty("DM_FIRST_NAME")){
				SQL.append(" AND TH_FIRST_NAME LIKE ? ");
				searchForm.setString(searchForm.getString("DM_FIRST_NAME")+"%");
			}
			if(!searchForm.empty("DM_LAST_NAME")){
				SQL.append(" AND TH_LAST_NAME LIKE ? ");
				searchForm.setString(searchForm.getString("DM_LAST_NAME")+"%");
			}
			if(!searchForm.empty("DM_IMPORTANT_DOC_NO")){
				SQL.append(" AND ID_NO = ? ");
				searchForm.setString(searchForm.getString("DM_IMPORTANT_DOC_NO"));
			}
			if(!searchForm.empty("DM_APPLICATION_NO")){
				SQL.append(" AND REF_NO2 = ? ");
				searchForm.setString(searchForm.getString("DM_APPLICATION_NO"));
			}
			if(!searchForm.empty("DM_APPLICATION_STATUS")){
				SQL.append(" AND DM_DOC.PARAM1 = ? ");
				searchForm.setString(searchForm.getString("DM_APPLICATION_STATUS"));
			}
			if(!searchForm.empty("DM_STATUS")){
				SQL.append(" AND  STATUS = ? ");
				searchForm.setString(searchForm.getString("DM_STATUS"));
			}
			if(!searchForm.empty("DM_BRANCH_NO")){
				SQL.append(" AND  BRANCH_ID = ? ");
				searchForm.setString(searchForm.getString("DM_BRANCH_NO"));
			}
			SQL.append(" ORDER BY APPLICATION_NO DESC");
		return SQL.toString();
	}
	@Override
	public int getNextActivityType() {
		return FrontController.PAGE;
	}
}
