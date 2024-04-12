package com.eaf.orig.ulo.search.view.webaction;

import java.sql.Date;
import java.util.ArrayList;
import java.util.HashMap;

import org.apache.log4j.Logger;

import com.eaf.core.ulo.common.display.FormatUtil;
import com.eaf.core.ulo.common.engine.SearchFormHandler;
import com.eaf.core.ulo.common.engine.SearchQueryEngine;
import com.eaf.core.ulo.common.properties.CacheControl;
import com.eaf.core.ulo.common.properties.ListBoxControl;
import com.eaf.core.ulo.common.properties.SystemConstant;
import com.eaf.core.ulo.common.util.Util;
import com.eaf.j2ee.pattern.control.FrontController;
import com.eaf.j2ee.pattern.control.event.Event;
import com.eaf.j2ee.pattern.control.event.EventResponse;
import com.eaf.j2ee.pattern.view.webaction.WebAction;
import com.eaf.j2ee.pattern.view.webaction.WebActionHelper;
import com.eaf.orig.ulo.app.dao.ORIGDAOFactory;

public class SearchLetterWebAction extends WebActionHelper implements WebAction {
	private static transient Logger logger = Logger.getLogger(SearchEnquiryWebAction.class);
	String FIELD_ID_PRODUCT_TYPE = SystemConstant.getConstant("FIELD_ID_PRODUCT_TYPE");
	String LETTER_TYPE_NA = SystemConstant.getConstant("LETTER_TYPE_NA");
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

		SearchFormHandler searchForm = new SearchFormHandler(getRequest()) 
		{
			@Override
			public void postSearchResult(ArrayList<HashMap<String, Object>> List) {
				logger.debug("do postSearchResult()..");
				int MIN_LIFE_CYCLE = SystemConstant.getIntConstant("MIN_LIFE_CYCLE");
				if (null != List) {
					for (HashMap<String, Object> tableResult : List) 
					{
						try{
							String applicationGroupId = (String) tableResult.get("APPLICATION_GROUP_ID");
							if(!Util.empty(applicationGroupId)){
								int lifeCycle = ORIGDAOFactory.getApplicationGroupDAO().getMaxLifeCycle(applicationGroupId);
								tableResult.put("MAX_LIFE_CYCLE", lifeCycle);
								Date finalAppDecisionDate = ORIGDAOFactory.getApplicationDAO().getFinalAppDecisionDate(applicationGroupId,MIN_LIFE_CYCLE);
								tableResult.put("MIN_FINAL_APP_DECISION_DATE", finalAppDecisionDate);
							}
						}catch(Exception e){
							logger.fatal("ERROR ",e);
						}
					}
				}
			}
		};
		
		logger.info("SearchLetterWebAction.... ");
		searchForm.setNextPage(false);
		searchForm.setAtPage(1);
		searchForm.setSQL(setParameter(searchForm));
		SearchQueryEngine engine = new SearchQueryEngine();
		
		try {
			engine.search(searchForm);
		} catch (Exception e) {
			logger.fatal("ERROR ", e);
		}
		getRequest().getSession().setAttribute(SearchFormHandler.SearchForm, searchForm);
		return true;
	}

	@Override
	public int getNextActivityType() {
		return FrontController.PAGE;
	}

	private String setParameter(SearchFormHandler searchForm) {
		StringBuilder sql = new StringBuilder();

		String FIELD_ID_PRODUCT_TYPE = SystemConstant.getConstant("FIELD_ID_PRODUCT_TYPE");
		String POST_APPROVED = SystemConstant.getConstant("POST_APPROVED");
		String FINAL_APP_DECISION_REJECTED = SystemConstant.getConstant("FINAL_APP_DECISION_REJECTED");
		String product = searchForm.getString("PRODUCT");
		product = ListBoxControl.getName(FIELD_ID_PRODUCT_TYPE, "CHOICE_NO", product, "SYSTEM_ID1");
		
		sql.append("   SELECT DISTINCT   "
				+ "		 LET.LETTER_NUMBER,	"
				+ "      LET.APPLICATION_GROUP_NO,   "
				+ "      LB.SYSTEM_ID6 AS PRODUCT,   "
				+ "      LET.IDNO,   "
				+ "      LET.FIRST_NAME AS TH_FIRST_NAME,   "
				+ "      LET.MID_NAME AS TH_MID_NAME,   "
				+ "      LET.LAST_NAME AS TH_LAST_NAME,    "
				+ "      LET.CUSTOMER_EMAIL_ADDRESS AS EMAIL_PRIMARY,    "
				+ "      LET.DE2_SUBMIT_DATE,    "
				+ "      LET.LETTER_TYPE,    "
				+ "      LET.LETTER_TEMPLATE,    "
				+ "      LET.CUSTOMER_RESEND_SEND_METHOD,    "
				+ "      LET.CUSTOMER_RESEND_EMAIL_ADDRESS,    "
				+ "      LET.CUSTOMER_RESEND_DATE,    "
				+ "      AG.APPLICATION_TYPE    "
				+ "   FROM LETTER_HISTORY LET   "
				+ "   LEFT JOIN ORIG_APPLICATION_GROUP AG ON AG.APPLICATION_GROUP_NO = LET.APPLICATION_GROUP_NO  "
				+ "   LEFT JOIN ORIG_APPLICATION APP ON AG.APPLICATION_GROUP_ID = APP.APPLICATION_GROUP_ID "
				+ "   LEFT JOIN ORIG_APPLICATION_GROUP_CAT AGC ON AGC.APPLICATION_GROUP_NO = LET.APPLICATION_GROUP_NO  "
				+ "   LEFT JOIN ORIG_APPLICATION_CAT APPC ON AGC.APPLICATION_GROUP_ID = APPC.APPLICATION_GROUP_ID  "
				+ "   LEFT JOIN BUSINESS_CLASS BUS ON (APP.BUSINESS_CLASS_ID = BUS.BUS_CLASS_ID OR BUS.BUS_CLASS_ID = APPC.BUSINESS_CLASS_ID) AND BUS.ORG_ID = 'KPL'  "
				+ "   LEFT JOIN LIST_BOX_MASTER LB ON LB.SYSTEM_ID1 = BUS.ORG_ID AND FIELD_ID = '53'  "
				+ "   WHERE (1=1) ");
		
		if(!searchForm.empty("LETTER_NUMBER")) {
			sql.append(" AND LET.LETTER_NUMBER = ? ");
			searchForm.setString(searchForm.getString("LETTER_NUMBER"));
		}
		if(!searchForm.empty("APPLICATION_GROUP_NO")) {
			sql.append(" AND LET.APPLICATION_GROUP_NO = ? ");
			searchForm.setString(searchForm.getString("APPLICATION_GROUP_NO"));
		}
		if(!searchForm.empty("PRODUCT")){
			String BUSINESS_CLASS_ID = CacheControl.getName(FIELD_ID_PRODUCT_TYPE, "CHOICE_NO", searchForm.getString("PRODUCT"), "SYSTEM_ID2");
			sql.append(" AND (APP.BUSINESS_CLASS_ID = ? OR APPC.BUSINESS_CLASS_ID = ?) ");
			searchForm.setString(BUSINESS_CLASS_ID);
			searchForm.setString(BUSINESS_CLASS_ID);
		}
		if(!searchForm.empty("IDNO")) {
			sql.append(" AND LET.IDNO = ? ");
			searchForm.setString(searchForm.getString("IDNO"));
		}
		if(!searchForm.empty("TH_FIRST_NAME")) {
			sql.append(" AND LET.FIRST_NAME LIKE ( ? || '%') ");
			searchForm.setString(searchForm.getString("TH_FIRST_NAME"));
		}
		if(!searchForm.empty("TH_LAST_NAME")) {
			sql.append(" AND LET.LAST_NAME LIKE ( ? || '%') ");
			searchForm.setString(searchForm.getString("TH_LAST_NAME"));
		}
		if(!searchForm.empty("DE2_SUBMIT_DATE_FROM") && !searchForm.empty("DE2_SUBMIT_DATE_TO")){
			sql.append(" AND LET.DE2_SUBMIT_DATE BETWEEN TRUNC( ? ) AND TRUNC( ? )+0.99999 ");
			searchForm.setDate(searchForm.getDate("DE2_SUBMIT_DATE_FROM", FormatUtil.TH));
			searchForm.setDate(searchForm.getDate("DE2_SUBMIT_DATE_TO", FormatUtil.TH));
		}
		if(!searchForm.empty("DE2_SUBMIT_DATE_FROM") && searchForm.empty("DE2_SUBMIT_DATE_TO")){
			sql.append(" AND LET.DE2_SUBMIT_DATE BETWEEN TRUNC( ? ) AND TRUNC( ? )+0.99999 ");
			searchForm.setDate(searchForm.getDate("DE2_SUBMIT_DATE_FROM", FormatUtil.TH));
			searchForm.setDate(searchForm.getDate("DE2_SUBMIT_DATE_FROM", FormatUtil.TH));
		}
		if(!searchForm.empty("LETTER_TYPE")) {
			if(!(LETTER_TYPE_NA.equals(searchForm.getString("LETTER_TYPE"))))
			{
				sql.append(" AND LET.LETTER_TYPE = ? ");
				searchForm.setString(searchForm.getString("LETTER_TYPE"));
			}
		}
		
		//Defect#2506
		//sql.append(" AND AG.APPLICATION_STATUS IN ('" + FINAL_APP_DECISION_REJECTED + "','" + POST_APPROVED +"') ");
		
		sql.append(" AND (AG.APPLICATION_STATUS IN ('" + FINAL_APP_DECISION_REJECTED + "','" + POST_APPROVED +"') ");
		sql.append(" OR AGC.APPLICATION_STATUS IN ('" + FINAL_APP_DECISION_REJECTED + "','" + POST_APPROVED +"')) ");
		
		sql.append(" ORDER BY AG.APPLICATION_TYPE ASC ");
		sql.append(" , LET.DE2_SUBMIT_DATE ASC ");
		logger.debug("sql : "+sql);
		return sql.toString();
	}
	
}
