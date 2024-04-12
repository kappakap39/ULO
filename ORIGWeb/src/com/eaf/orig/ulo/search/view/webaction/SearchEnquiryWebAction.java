package com.eaf.orig.ulo.search.view.webaction;

import java.sql.Date;
import java.util.ArrayList;
import java.util.HashMap;

import org.apache.log4j.Logger;

import com.eaf.core.ulo.common.display.FormatUtil;
import com.eaf.core.ulo.common.engine.SQLQueryEngine;
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
import com.eaf.orig.ulo.app.view.form.subform.product.manual.CardInfoUtil;
import com.eaf.orig.ulo.control.util.PersonalInfoUtil;

public class SearchEnquiryWebAction extends WebActionHelper implements WebAction {
	private static transient Logger logger = Logger.getLogger(SearchEnquiryWebAction.class);
	String FIELD_ID_PRODUCT_TYPE = SystemConstant.getConstant("FIELD_ID_PRODUCT_TYPE");
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

		SearchFormHandler searchForm = new SearchFormHandler(getRequest()) {
			@Override
			public void postSearchResult(ArrayList<HashMap<String, Object>> List) {
				logger.debug("do postSearchResult()..");
				int MIN_LIFE_CYCLE = SystemConstant.getIntConstant("MIN_LIFE_CYCLE");
				if (null != List) {
					for (HashMap<String, Object> tableResult : List) {

						// df 2560
						PersonalInfoUtil.setPersonalInfoForSearch(tableResult);
						
						String[] productsName = SQLQueryEngine.display(tableResult,
								"PRODUCT_NAME").split("\\,");
						String newProductsName = "";
						if (null != productsName) {
							for (String productName : productsName) {
								newProductsName += (Util.empty(newProductsName)) ? productName
										: "<br/>" + productName;
							}
						}

						tableResult.put("PRODUCT_NAME", newProductsName);
						
						try{
							String applicationGroupId = (String) tableResult.get("APPLICATION_GROUP_ID");
							if(!Util.empty(applicationGroupId)){
								int lifeCycle = ORIGDAOFactory.getApplicationGroupDAO().getMaxLifeCycle(applicationGroupId);
								tableResult.put("", lifeCycle);
								Date finalAppDecisionDate = ORIGDAOFactory.getApplicationDAO().getFinalAppDecisionDate(applicationGroupId,MIN_LIFE_CYCLE);
								tableResult.put("MIN_FINAL_APP_DECISION_DATE", finalAppDecisionDate);
							
								//KPL Additional get KPL Application DE2_SUBMIT_DATE
								try
								{									
									Date bookingDate = ORIGDAOFactory.getApplicationLogDAO().getDE2SubmitDate(applicationGroupId, lifeCycle);
									Date dateOfSearch = ORIGDAOFactory.getApplicationLogDAO().getDateOfSearch(applicationGroupId, lifeCycle);
									tableResult.put("DE2_SUBMIT_DATE", bookingDate);
									tableResult.put("DATE_OF_SEARCH", dateOfSearch);
								}
								catch(Exception e)
								{
									logger.fatal("ERROR ",e);
								}
								
							}
						}catch(Exception e){
							logger.fatal("ERROR ",e);
						}
					}
				}
			}
		};
		logger.info(" AllEnquiryWebAction.... ");
		// UserDetailM userM = (UserDetailM)
		// getRequest().getSession().getAttribute("ORIGUser");
		searchForm.setNextPage(false);
		searchForm.setAtPage(1);
		searchForm.setSQL(setParameter(searchForm));
		searchForm.setSubSQL(subSQL());
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

	private String subSQL() {
		StringBuilder SQL = new StringBuilder();
		SQL.append(" PKA_SEARCH_INFO.GET_PRODUCT(M_RESULT.APPLICATION_GROUP_ID) PRODUCT_NAME ");
		
		SQL.append(" , ( SELECT CASE WHEN INF_BATCH_LOG.INTERFACE_CODE IS NOT NULL THEN 'Y' ELSE 'N' END  CLOSE_APP_FLAG  ");
		SQL.append(" FROM INF_BATCH_LOG ");
		SQL.append(" WHERE INF_BATCH_LOG.INTERFACE_CODE IN ('KVI001','IM001','PEGA001') ");
		SQL.append(" AND INF_BATCH_LOG.APPLICATION_GROUP_ID = M_RESULT.APPLICATION_GROUP_ID ");
		SQL.append(" GROUP BY INF_BATCH_LOG.APPLICATION_GROUP_ID, CASE WHEN INF_BATCH_LOG.INTERFACE_CODE IS NOT NULL THEN 'Y' ELSE 'N' END ) CLOSE_APP_FLAG ");

		return SQL.toString();
	}

	private String setParameter(SearchFormHandler searchForm) {
		StringBuilder sql = new StringBuilder();

		// String WIP_JOBSTATE_IA = String.format("(%s)",
		// SystemConfig.getGeneralParam("WIP_JOBSTATE_IA"));
		String FIELD_ID_PRODUCT_TYPE = SystemConstant.getConstant("FIELD_ID_PRODUCT_TYPE");
		String product = searchForm.getString("PRODUCT");
		product = ListBoxControl.getName(FIELD_ID_PRODUCT_TYPE, "CHOICE_NO", product, "SYSTEM_ID1");

		sql.append("   SELECT DISTINCT   "
				+ "		 AG.WF_STATE,	"
				+ "      AG.PRIORITY,   "
				+ "      AG.APPLICATION_GROUP_NO,   "
				+ "      AG.APPLICATION_DATE,   "
				+ "      AG.APPLICATION_GROUP_ID,   "
				+ "      AG.BRANCH_NO,   "
				+ "      AG.APPLICATION_STATUS,    "
				+ "      AG.UPDATE_DATE,    "
				+ "      AG.JOB_STATE,    "
				+ "      IBL.INTERFACE_CODE,    "
				+ "      AG.SOURCE    "
				//+ "      ,AGLC.MAX_LIFE_CYCLE    " //Defect#2560 //Remove as of PROD Incident 1238939
				+ "      ,AG.LIFE_CYCLE AS MAX_LIFE_CYCLE    " // Add as of Incident 1238939
				+ "   FROM ORIG_APPLICATION_GROUP AG   "
				+ "   LEFT JOIN ORIG_APPLICATION APP ON AG.APPLICATION_GROUP_ID = APP.APPLICATION_GROUP_ID   "
				+ "   LEFT JOIN ORIG_PERSONAL_INFO PI ON AG.APPLICATION_GROUP_ID = PI.APPLICATION_GROUP_ID  "
				+ "   LEFT JOIN ORIG_PERSONAL_ADDRESS PA ON PI.PERSONAL_ID=PA.PERSONAL_ID AND PA.ADDRESS_TYPE = ? "
				+ "   LEFT JOIN ORIG_SALE_INFO S ON AG.APPLICATION_GROUP_ID = S.APPLICATION_GROUP_ID   "
				+ "   LEFT JOIN BUSINESS_CLASS BUS ON APP.BUSINESS_CLASS_ID = BUS.BUS_CLASS_ID   "
				+ "   LEFT JOIN ORGANIZATION ORG ON BUS.ORG_ID = ORG.ORG_ID   "
				+ "   LEFT JOIN ORIG_LOAN L ON APP.APPLICATION_RECORD_ID = L.APPLICATION_RECORD_ID   "
				+ "   LEFT JOIN ORIG_CARD C ON L.LOAN_ID = C.LOAN_ID   "
				+ "   LEFT JOIN INF_BATCH_LOG IBL ON AG.APPLICATION_GROUP_ID = IBL.APPLICATION_GROUP_ID AND IBL.INTERFACE_CODE = ? "
				//Defect#2560 Add LIFE_CYCLE
				//Remove as of PROD Incident 1238939
				//+ "   JOIN (SELECT NVL(MAX(LIFE_CYCLE),1) AS MAX_LIFE_CYCLE, APPLICATION_GROUP_ID FROM ORIG_APPLICATION GROUP BY APPLICATION_GROUP_ID) AGLC ON AG.APPLICATION_GROUP_ID = AGLC.APPLICATION_GROUP_ID "
				+ "   WHERE (1=1) "
				+ "   AND AG.JOB_STATE in (select distinct CHOICE_NO from LIST_BOX_MASTER where FIELD_ID= ? ) ");
		
		searchForm.setString(SystemConstant.getConstant("ADDRESS_TYPE_WORK"));
		searchForm.setString(SystemConstant.getConstant("INTERFACE_CODE_CARDLINK"));
		searchForm.setString(SystemConstant.getConstant("FILED_ID_JOB_STATE"));
		
		if(!searchForm.empty("APPLICATION_GROUP_NO")) {
			sql.append(" AND AG.APPLICATION_GROUP_NO = ? ");
			searchForm.setString(searchForm.getString("APPLICATION_GROUP_NO"));
		}
		if(!searchForm.empty("CARD_LINK_REF_NO")){
			sql.append(" AND APP.CARDLINK_REF_NO = ? ");
			searchForm.setString(searchForm.getString("CARD_LINK_REF_NO"));
		}
		if(!searchForm.empty("PRODUCT")){
			String BUSINESS_CLASS_ID = CacheControl.getName(FIELD_ID_PRODUCT_TYPE, "CHOICE_NO", searchForm.getString("PRODUCT"), "SYSTEM_ID2");
			logger.debug("BUSINESS_CLASS_ID>>"+BUSINESS_CLASS_ID);
			sql.append(" AND APP.BUSINESS_CLASS_ID = ? ");
			searchForm.setString(BUSINESS_CLASS_ID);
		}
		if(!searchForm.empty("TH_FIRST_NAME")) {
			sql.append(" AND PI.TH_FIRST_NAME LIKE ( ? || '%') ");
			searchForm.setString(searchForm.getString("TH_FIRST_NAME"));
		}
		if(!searchForm.empty("TH_LAST_NAME")) {
			sql.append(" AND PI.TH_LAST_NAME LIKE ( ? || '%') ");
			searchForm.setString(searchForm.getString("TH_LAST_NAME"));
		}
		if(!searchForm.empty("IDNO")) {
			sql.append(" AND PI.IDNO = ? ");
			searchForm.setString(searchForm.getString("IDNO"));
		}
		if(!searchForm.empty("APPLICATION_STATUS")) {
			sql.append(" AND AG.APPLICATION_STATUS = ? ");
			searchForm.setString(searchForm.getString("APPLICATION_STATUS"));
		}
		if(!searchForm.empty("SCAN_DATE_FROM") && !searchForm.empty("SCAN_DATE_TO")){
			sql.append(" AND AG.APPLICATION_DATE BETWEEN TRUNC( ? ) AND TRUNC( ? )+0.99999 ");
			searchForm.setDate(searchForm.getDate("SCAN_DATE_FROM", FormatUtil.TH));
			searchForm.setDate(searchForm.getDate("SCAN_DATE_TO", FormatUtil.TH));
		}
		if(!searchForm.empty("SCAN_DATE_FROM") && searchForm.empty("SCAN_DATE_TO")){
			sql.append(" AND AG.APPLICATION_DATE BETWEEN TRUNC( ? ) AND TRUNC( ? )+0.99999 ");
			searchForm.setDate(searchForm.getDate("SCAN_DATE_FROM", FormatUtil.TH));
			searchForm.setDate(searchForm.getDate("SCAN_DATE_FROM", FormatUtil.TH));
		}
		if(!searchForm.empty("PROJECT_CODE")){
			sql.append(" AND APP.PROJECT_CODE = ? ");
			searchForm.setString(searchForm.getString("PROJECT_CODE"));
		}
		if(!searchForm.empty("BRANCH_NO")){
			sql.append(" AND AG.BRANCH_NO = ? ");
			searchForm.setString(searchForm.getString("BRANCH_NO"));
		}
		if(!searchForm.empty("SALES_ID")){
			sql.append(" AND S.SALES_ID = ? ");
			searchForm.setString(searchForm.getString("SALES_ID"));
		}
		if(!searchForm.empty("COMPANY")){
			sql.append(" AND PA.COMPANY_NAME LIKE ( ? || '%') ");
			searchForm.setString(searchForm.getString("COMPANY"));
		}
		//Add CARD_TYPE as KPL Implements
		if(!searchForm.empty("CARD_TYPE")){
			sql.append(" AND C.CARD_TYPE IN (SELECT CARD_TYPE_ID FROM CARD_TYPE WHERE CARD_CODE = ?) ");
			searchForm.setString(searchForm.getString("CARD_TYPE"));
		}
			
		//Add FINAL_DECISION_DATE_FROM/TO as KPL Implements
		if(!searchForm.empty("FINAL_DECISION_DATE_FROM") && !searchForm.empty("FINAL_DECISION_DATE_TO")){
			sql.append(" AND APP.FINAL_APP_DECISION_DATE BETWEEN TRUNC( ? ) AND TRUNC( ? )+0.99999 ");
			searchForm.setDate(searchForm.getDate("FINAL_DECISION_DATE_FROM", FormatUtil.TH));
			searchForm.setDate(searchForm.getDate("FINAL_DECISION_DATE_TO", FormatUtil.TH));
		}
		if(!searchForm.empty("FINAL_DECISION_DATE_FROM") && searchForm.empty("FINAL_DECISION_DATE_TO")){
			sql.append(" AND APP.FINAL_APP_DECISION_DATE BETWEEN TRUNC( ? ) AND TRUNC( ? )+0.99999 ");
			searchForm.setDate(searchForm.getDate("FINAL_DECISION_DATE_FROM", FormatUtil.TH));
			searchForm.setDate(searchForm.getDate("FINAL_DECISION_DATE_FROM", FormatUtil.TH));
		}
		
		sql.append(" ORDER BY AG.APPLICATION_GROUP_NO DESC ");
		logger.debug("sql : "+sql);
		return sql.toString();
	}
}
