package com.eaf.orig.ulo.search.view.webaction;

import java.sql.Date;
import java.util.ArrayList;
import java.util.HashMap;

import org.apache.log4j.Logger;

import com.eaf.core.ulo.common.display.FormatUtil;
import com.eaf.core.ulo.common.engine.SearchFormHandler;
import com.eaf.core.ulo.common.engine.SearchQueryEngine;
import com.eaf.core.ulo.common.model.ResponseData;
import com.eaf.core.ulo.common.properties.ListBoxControl;
import com.eaf.core.ulo.common.properties.SystemConstant;
import com.eaf.core.ulo.common.util.Util;
import com.eaf.j2ee.pattern.control.FrontController;
import com.eaf.j2ee.pattern.control.event.Event;
import com.eaf.j2ee.pattern.control.event.EventResponse;
import com.eaf.j2ee.pattern.view.webaction.WebAction;
import com.eaf.j2ee.pattern.view.webaction.WebActionHelper;
import com.eaf.orig.profile.model.UserDetailM;
import com.eaf.orig.ulo.app.dao.ORIGDAOFactory;
import com.eaf.orig.ulo.app.view.util.dih.DIHProxy;
import com.eaf.orig.ulo.app.view.util.dih.model.DIHQueryResult;
import com.eaf.orig.ulo.app.view.util.pa.PAUtil;
import com.eaf.orig.ulo.constant.MConstant;
import com.eaf.orig.ulo.control.util.PersonalInfoUtil;
import com.eaf.orig.ulo.model.dih.AccountInformationDataM;

public class SearchClaimJobEnquiryWebAction extends WebActionHelper implements WebAction {
	private static transient Logger logger = Logger.getLogger(SearchEnquiryWebAction.class);
	String FIELD_ID_PRODUCT_TYPE = SystemConstant.getConstant("FIELD_ID_PRODUCT_TYPE");
	String DIH_ACCT_STATUS_ACTIVE = SystemConstant.getConstant("DIH_ACCT_STATUS_ACTIVE");
	
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
								
								//Find account status
								/*String accStatus = "Inactive";
								try
								{
									String accountNumber = (String) tableResult.get("LOAN_ACCOUNT_NO");
									DIHProxy proxy = new DIHProxy();
									DIHQueryResult<String> accountStatusResult = proxy.getAccountInfo(accountNumber,"AR_LCS_TP_CD");
									if(ResponseData.SUCCESS.equals(accountStatusResult.getStatusCode()))
									{
										String statusCode = accountStatusResult.getResult();
										if(statusCode != null && statusCode.equals(DIH_ACCT_STATUS_ACTIVE))
										{
											accStatus = "Active";
										}
									}
								}
								catch(Exception e)
								{logger.fatal("ERROR ",e);}
								finally
								{tableResult.put("LOAN_ACCOUNT_STATUS", accStatus);}*/
							}
						}catch(Exception e){
							logger.fatal("ERROR ",e);
						}
					}
				}
			}
		};
		
		logger.info("SearchClaimJobEnquiryWebAction.... ");
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
		
		String ACTION_TYPE = searchForm.getString("ACTION_TYPE");
		System.out.println("SearchClaimjob : " + ACTION_TYPE);

		String FIELD_ID_PRODUCT_TYPE = SystemConstant.getConstant("FIELD_ID_PRODUCT_TYPE");
		String PA_COMPLETED = SystemConstant.getConstant("PA_COMPLETED");
		String product = searchForm.getString("PRODUCT");
		product = ListBoxControl.getName(FIELD_ID_PRODUCT_TYPE, "CHOICE_NO", product, "SYSTEM_ID1");
		
		sql.append("   SELECT DISTINCT   "
				+ "		 LSC.TYPE AS CLAIM_JOB_TYPE,	"
				+ "      AG.APPLICATION_GROUP_NO,   "
				+ "      PI.CIS_NO,   "
				+ "      PI.TH_FIRST_NAME,   "
				+ "      PI.TH_MID_NAME,   "
				+ "      PI.TH_LAST_NAME,    "
				+ "      LSC.DE2_SUBMIT_DATE,    "
				+ "      LSSD.STAMP_DUTY_FEE,    "
				+ "      LSC.CLAIM_ID,    "
				+ "      LSC.CLAIM_FLAG,    "
				+ "      LSC.CLAIM_BY,    "
				+ "      LSC.CLAIM_DATE,    "
				+ "      LSC.COMPLETE_DATE,    "
				+ "      LSC.LOAN_ACCOUNT_NO,    "
				+ "      LSC.LOAN_ACCOUNT_OPEN_DATE,   "	
				+ "      LSC.LOAN_SETUP_STATUS,    "
				+ "      LSC.COMPLETE_FLAG,    "
				+ "      LSC.LOAN_ACCOUNT_STATUS,    "
				+ "      AG.APPLICATION_TYPE,    "
				+ "		 AG.APPLICATION_GROUP_ID    "
				+ "   FROM ORIG_APPLICATION_GROUP AG   "
				+ "   LEFT JOIN ORIG_APPLICATION APP ON AG.APPLICATION_GROUP_ID = APP.APPLICATION_GROUP_ID   "
				+ "   LEFT JOIN ORIG_PERSONAL_INFO PI ON AG.APPLICATION_GROUP_ID = PI.APPLICATION_GROUP_ID  "
				+ "   LEFT JOIN ORIG_LOAN LN ON LN.APPLICATION_RECORD_ID=APP.APPLICATION_RECORD_ID "
				+ "   LEFT JOIN BUSINESS_CLASS BUS ON APP.BUSINESS_CLASS_ID = BUS.BUS_CLASS_ID AND BUS.ORG_ID = 'KPL'   "
				+ "   LEFT JOIN ORGANIZATION ORG ON BUS.ORG_ID = ORG.ORG_ID   "
				+ "   JOIN ( SELECT MAX(CREATE_DATE) AS CREATE_DATE , APPLICATION_GROUP_ID FROM ORIG_APPLICATION_LOG WHERE ACTION_DESC = 'DE2 Submit' " 
				+ "   GROUP BY APPLICATION_GROUP_ID ) AGL ON AGL.APPLICATION_GROUP_ID = AG.APPLICATION_GROUP_ID "
				
				//[Claim Info Table]
				+ "   JOIN LOAN_SETUP_CLAIM LSC ON LSC.APPLICATION_GROUP_NO = AG.APPLICATION_GROUP_NO AND LSC.LOAN_SETUP_STATUS <> 'Pending Setup'   "
				+ "   LEFT JOIN LOAN_SETUP_STAMP_DUTY LSSD ON LSC.CLAIM_ID = LSSD.CLAIM_ID AND LSC.TYPE = 'S'   "
				
				+ "   WHERE (1=1) "
				//+ "   AND AG.REPROCESS_FLAG IS NULL "
				+ "   AND AG.APPLICATION_STATUS in ('Post Approved','Approved') ");
		
		if(!searchForm.empty("DE2_SUBMIT_DATE")){
			sql.append(" AND LSC.DE2_SUBMIT_DATE BETWEEN TRUNC( ? ) AND TRUNC( ? )+0.99999 ");
			searchForm.setDate(searchForm.getDate("DE2_SUBMIT_DATE", FormatUtil.TH));
			searchForm.setDate(searchForm.getDate("DE2_SUBMIT_DATE", FormatUtil.TH));
		}
		
		if(!searchForm.empty("APPLICATION_GROUP_NO")) {
			sql.append(" AND AG.APPLICATION_GROUP_NO = ? ");
			searchForm.setString(searchForm.getString("APPLICATION_GROUP_NO"));
		}
		if(!searchForm.empty("CIS_NO")) {
			sql.append(" AND PI.CIS_NO = ? ");
			searchForm.setString(searchForm.getString("CIS_NO"));
		}
		if(!searchForm.empty("TH_FIRST_NAME")) {
			sql.append(" AND PI.TH_FIRST_NAME LIKE ( ? || '%') ");
			searchForm.setString(searchForm.getString("TH_FIRST_NAME"));
		}
		if(!searchForm.empty("TH_LAST_NAME")) {
			sql.append(" AND PI.TH_LAST_NAME LIKE ( ? || '%') ");
			searchForm.setString(searchForm.getString("TH_LAST_NAME"));
		}
		if(!searchForm.empty("CREDIT_LIMIT_DATE")){
			sql.append(" AND LSC.LOAN_ACCOUNT_OPEN_DATE BETWEEN TRUNC( ? ) AND TRUNC( ? )+0.99999 ");
			searchForm.setDate(searchForm.getDate("CREDIT_LIMIT_DATE", FormatUtil.TH));
			searchForm.setDate(searchForm.getDate("CREDIT_LIMIT_DATE", FormatUtil.TH));
		}
		if(!searchForm.empty("CLAIM_BY")){
			sql.append(" AND LSC.CLAIM_BY = ? ");
			searchForm.setString(searchForm.getString("CLAIM_BY"));
		}
		if(!searchForm.empty("CLAIM_JOB_STATUS")){
			sql.append(" AND LSC.LOAN_SETUP_STATUS = ? ");
			searchForm.setString(searchForm.getString("CLAIM_JOB_STATUS"));
		}
		if(!searchForm.empty("CLAIM_FLAG")){
			
			if(MConstant.FLAG.YES.equals(searchForm.getString("CLAIM_FLAG")))
			{
				sql.append(" AND LSC.LOAN_SETUP_STATUS = ? ");
			}
			else
			{
				sql.append(" AND LSC.LOAN_SETUP_STATUS <> ? ");
			}
			searchForm.setString(PA_COMPLETED);
		}
		
		//ACTION_TYPE CONDITION
		if(!Util.empty(ACTION_TYPE) && !PAUtil.isMyTask(ACTION_TYPE))
		{
			sql.append(" AND LSC.TYPE = ? ");
			searchForm.setString(PAUtil.getClaimJobActionType(ACTION_TYPE));
		}
		
		if(!Util.empty(ACTION_TYPE) && PAUtil.isMyTask(ACTION_TYPE))
		{
			UserDetailM userM = (UserDetailM) getRequest().getSession().getAttribute("ORIGUser");
			sql.append(" AND LSC.CLAIM_BY = ? ");
			searchForm.setString(userM.getUserName());
		}
		
		sql.append(" ORDER BY AG.APPLICATION_TYPE ASC ");
		sql.append(" , LSC.CLAIM_DATE ASC ");
		sql.append(" , LSC.DE2_SUBMIT_DATE ASC ");
		logger.debug("sql : "+sql);
		return sql.toString();
	}
	
}
