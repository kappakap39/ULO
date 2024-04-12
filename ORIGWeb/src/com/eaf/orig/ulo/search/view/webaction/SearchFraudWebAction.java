package com.eaf.orig.ulo.search.view.webaction;

import java.util.ArrayList;
import java.util.HashMap;

import org.apache.log4j.Logger;

import com.eaf.core.ulo.common.engine.SearchFormHandler;
import com.eaf.core.ulo.common.engine.SearchQueryEngine;
import com.eaf.core.ulo.common.properties.ListBoxControl;
import com.eaf.core.ulo.common.properties.SystemConfig;
import com.eaf.core.ulo.common.properties.SystemConstant;
import com.eaf.core.ulo.common.util.Util;
import com.eaf.j2ee.pattern.control.FrontController;
import com.eaf.j2ee.pattern.control.event.Event;
import com.eaf.j2ee.pattern.control.event.EventResponse;
import com.eaf.j2ee.pattern.view.webaction.WebAction;
import com.eaf.j2ee.pattern.view.webaction.WebActionHelper;
import com.eaf.orig.ulo.app.dao.ORIGDAOFactory;
import com.eaf.orig.ulo.constant.MConstant;

public class SearchFraudWebAction extends WebActionHelper	implements WebAction {
	private static transient Logger logger = Logger.getLogger(SearchFraudWebAction.class);
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
		String[]  WIP_JOBSTATE_FRAUD = SystemConfig.getGeneralParam("WIP_JOBSTATE_FRAUD").split(",");
		SearchFormHandler searchForm = new SearchFormHandler(getRequest()){	
			@Override
			public void postSearchResult(ArrayList<HashMap<String, Object>> List) {
				logger.debug("do postSearchResult()..");
				try{
					if(!Util.empty(List)){
						for (HashMap<String, Object> tableResult : List) {
							String applicationGroupId = (String) tableResult.get("APPLICATION_GROUP_ID");
							if(!Util.empty(applicationGroupId)){
								int lifeCycle = ORIGDAOFactory.getApplicationGroupDAO().getMaxLifeCycle(applicationGroupId);
								tableResult.put("MAX_LIFE_CYCLE", lifeCycle);
							}
						}
					}
				}catch(Exception e){
					logger.fatal("ERROR ",e);
				}
			}
		};
		searchForm.setNextPage(false);
		searchForm.setAtPage(1);
		searchForm.setSQL(getSelectSerchSQL(searchForm,WIP_JOBSTATE_FRAUD));
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
	private String getSelectSerchSQL(SearchFormHandler searchForm,String[] jobStates){
		String FIELD_ID_PRODUCT_TYPE = SystemConstant.getConstant("FIELD_ID_PRODUCT_TYPE");
		String WF_STATE_RUNNING = SystemConstant.getConstant("WF_STATE_RUNNING");
		String product = searchForm.getString("PRODUCT_NAME");
		product = ListBoxControl.getName(FIELD_ID_PRODUCT_TYPE, "CHOICE_NO", product, "SYSTEM_ID1");
		logger.debug("PRODUCT_NAME : " + product);
		
		StringBuilder sql = new StringBuilder();
		
		sql.append("SELECT  APPLICATION_GROUP_ID,");
		sql.append(" PRIORITY,");
		sql.append(" APPLICATION_GROUP_NO,");
		sql.append(" APPLICATION_STATUS,");
        sql.append(" LAST_UPDATE,");
        sql.append(" LISTAGG (PRODUCT_NAME,' / ') WITHIN GROUP (ORDER BY PRODUCT_NAME) AS PRODUCT_NAME,");
        sql.append(" MAIN_CUSTOMER_NAME,");
        sql.append(" MAIN_ID_NO,");
        sql.append(" SUP_CUSTOMER_NAME,");
        sql.append(" SUP_ID_NO,");
        sql.append(" WF_STATE");
        sql.append(" FROM (");
        sql.append("  SELECT DISTINCT APP_GROUP.APPLICATION_GROUP_ID, APP_GROUP.JOB_STATE,  APP_GROUP.WF_STATE,");
        sql.append("  	APP_GROUP.PRIORITY,");
        sql.append(" 	APP_GROUP.APPLICATION_GROUP_NO,");
        sql.append(" 	APP_GROUP.APPLICATION_STATUS,");
        sql.append(" 	TO_CHAR(APP_GROUP.UPDATE_DATE,'dd/mm/yyyy | HH24:MI','NLS_CALENDAR=''THAI BUDDHA'' NLS_DATE_LANGUAGE=THAI') AS LAST_UPDATE,");
        sql.append(" 	ORG.ORG_DESC AS PRODUCT_NAME,");
        sql.append(" 	MAIN_PERS.TH_FIRST_NAME || ' ' || MAIN_PERS.TH_LAST_NAME AS MAIN_CUSTOMER_NAME,");
        sql.append(" 	MAIN_PERS.IDNO AS MAIN_ID_NO,");
        sql.append(" 	SUP_PERS.TH_FIRST_NAME || ' ' || SUP_PERS.TH_LAST_NAME  AS SUP_CUSTOMER_NAME,");
        sql.append("  	SUP_PERS.IDNO AS SUP_ID_NO");
        sql.append(" 	FROM  ORIG_APPLICATION_GROUP APP_GROUP");
        sql.append(" 	LEFT JOIN  ORIG_PERSONAL_INFO MAIN_PERS  ON  APP_GROUP.APPLICATION_GROUP_ID = MAIN_PERS.APPLICATION_GROUP_ID AND MAIN_PERS.PERSONAL_TYPE ='"+MConstant.PERSONAL_TYPE.APPLICANT+"'");
        sql.append(" 	LEFT JOIN  ORIG_PERSONAL_INFO SUP_PERS  ON  APP_GROUP.APPLICATION_GROUP_ID = SUP_PERS.APPLICATION_GROUP_ID AND SUP_PERS.PERSONAL_TYPE ='"+MConstant.PERSONAL_TYPE.SUP_CARD+"'");
        sql.append(" 	LEFT JOIN  ORIG_APPLICATION APP ON  APP_GROUP.APPLICATION_GROUP_ID = APP.APPLICATION_GROUP_ID");
        sql.append(" 	LEFT JOIN  BUSINESS_CLASS BU_CLASS ON  APP.BUSINESS_CLASS_ID = BU_CLASS.BUS_CLASS_ID");
        sql.append(" 	LEFT JOIN  ORGANIZATION ORG ON  BU_CLASS.ORG_ID = ORG.ORG_ID");
        sql.append(" 	LEFT JOIN  ORIG_LOAN  LOAN ON APP.APPLICATION_RECORD_ID = LOAN.APPLICATION_RECORD_ID");
        sql.append("  	LEFT JOIN  ORIG_CARD CARD ON  LOAN.LOAN_ID = CARD.LOAN_ID");
        sql.append("  	LEFT JOIN  CARD_TYPE CARD_TYPE ON  CARD.CARD_TYPE = CARD_TYPE.CARD_TYPE_ID");
        sql.append("  	LEFT JOIN  ORIG_CASH_TRANSFER CASH_TRAN ON  LOAN.LOAN_ID = CASH_TRAN.LOAN_ID");
        sql.append("  	WHERE APP_GROUP.JOB_STATE IS NOT NULL");
        
        if(!Util.empty(jobStates)){
        	String COMMA = "";
			sql.append(" AND  APP_GROUP.JOB_STATE IN ( ");
			for (String jobState : jobStates) {
				sql.append(COMMA+"?");
				COMMA = ",";
				searchForm.setString(jobState);
				logger.debug("JOB_STATE::"+jobState);
			}
			sql.append(" ) ");
        }    
        
    	if(!searchForm.empty("APPLICATION_GROUP_NO")){
			sql.append(" AND   APP_GROUP.APPLICATION_GROUP_NO = ? ");
			searchForm.setString(searchForm.getString("APPLICATION_GROUP_NO"));
		}
    	if(!searchForm.empty("APPLICATION_STATUS")){
    		sql.append(" AND   APP_GROUP.APPLICATION_STATUS= ? ");
			searchForm.setString(searchForm.getString("APPLICATION_STATUS"));
    	}
    	if(!searchForm.empty("TH_FIRST_NAME")){
    		sql.append(" AND  ( UPPER(MAIN_PERS.TH_FIRST_NAME) LIKE ? OR UPPER(SUP_PERS.TH_FIRST_NAME) LIKE ? ) ");
			searchForm.setString(searchForm.getString("TH_FIRST_NAME")+"%");
			searchForm.setString(searchForm.getString("TH_FIRST_NAME")+"%");
    	}
    	if(!searchForm.empty("TH_LAST_NAME")){
    		sql.append(" AND  ( UPPER(MAIN_PERS.TH_LAST_NAME) LIKE ? OR UPPER(SUP_PERS.TH_LAST_NAME) LIKE ? ) ");
			searchForm.setString(searchForm.getString("TH_LAST_NAME")+"%");
			searchForm.setString(searchForm.getString("TH_LAST_NAME")+"%");
    	}
    	if(!searchForm.empty("ID_NO")){
    		sql.append(" AND  ( UPPER(MAIN_PERS.IDNO) =  ? OR UPPER(SUP_PERS.IDNO) =  ? ) ");
			searchForm.setString(searchForm.getString("ID_NO"));
			searchForm.setString(searchForm.getString("ID_NO"));
    	}
    	if(!searchForm.empty("CASH_TRANSFER_TYPE")){
    		sql.append(" AND   CASH_TRAN.CASH_TRANSFER_TYPE= ? ");
			searchForm.setString(searchForm.getString("CASH_TRANSFER_TYPE"));
    	}
    	if(!searchForm.empty("PRODUCT_NAME")){
    		sql.append(" AND   ORG.ORG_ID= ? ");
			searchForm.setString(product);
    	}
    	if(!searchForm.empty("CARD_TYPE_ID")){
    		sql.append(" AND   CARD_TYPE.CARD_TYPE_ID= ? ");
			searchForm.setString(searchForm.getString("CARD_TYPE_ID"));
    	}
    	   	
        sql.append(") GROUP BY");
        sql.append(" APPLICATION_GROUP_ID,");
        sql.append(" PRIORITY,");
        sql.append(" APPLICATION_GROUP_NO,");
        sql.append(" APPLICATION_STATUS,");
        sql.append(" LAST_UPDATE,");
        sql.append(" MAIN_CUSTOMER_NAME,");
        sql.append(" MAIN_ID_NO,");
        sql.append(" SUP_CUSTOMER_NAME, WF_STATE,");
        sql.append(" SUP_ID_NO  ORDER BY  APPLICATION_GROUP_NO DESC ");

		return sql.toString();
	}
}
