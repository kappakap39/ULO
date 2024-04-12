package com.eaf.orig.ulo.search.view.webaction;

import java.util.ArrayList;
import java.util.HashMap;

import org.apache.log4j.Logger;

import com.eaf.core.ulo.common.display.FormatUtil;
import com.eaf.core.ulo.common.engine.SQLQueryEngine;
import com.eaf.core.ulo.common.engine.SearchFormHandler;
import com.eaf.core.ulo.common.engine.SearchQueryEngine;
import com.eaf.core.ulo.common.properties.CacheControl;
import com.eaf.core.ulo.common.properties.SystemConstant;
import com.eaf.core.ulo.common.util.Util;
import com.eaf.j2ee.pattern.control.FrontController;
import com.eaf.j2ee.pattern.control.event.Event;
import com.eaf.j2ee.pattern.control.event.EventResponse;
import com.eaf.j2ee.pattern.view.webaction.WebAction;
import com.eaf.j2ee.pattern.view.webaction.WebActionHelper;

public class SearchCardMaintenanceWebAction extends WebActionHelper implements
		WebAction {
	private static transient Logger logger = Logger.getLogger(SearchCardMaintenanceWebAction.class);
	SearchFormHandler searchForm = null;
	String fail = SystemConstant.getConstant("CARDLINK_FLAG_FAIL");

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
		try {
			 searchForm = new SearchFormHandler(getRequest()){	
				@Override
				public void postSearchResult(ArrayList<HashMap<String, Object>> list) {
					logger.debug("do postSearchResult()..");
					ArrayList<HashMap<String, Object>> resultInfo = new ArrayList<HashMap<String,Object>>();
					HashMap<String, Object> applicationCardInfo= null;
					String KEY_GROUP="";
					if(!Util.empty(list)){
						for(HashMap<String, Object> Row : list){
							String QR  = SQLQueryEngine.display(Row,"QR");
							String  APPLICATION_GROUP_ID =SQLQueryEngine.display(Row,"APPLICATION_GROUP_ID");
							String CARDLINK_REF_NO  = SQLQueryEngine.display(Row,"CARDLINK_REF_NO");
							String CARDLINK_FLAG  = SQLQueryEngine.display(Row,"CARDLINK_FLAG");
							String PROCESSIONG_DATE  = SQLQueryEngine.display(Row,"PROCESSIONG_DATE");
							String REG_TYPE = SQLQueryEngine.display(Row,"REG_TYPE");
							
							//List cards
							String TH_FIRST_NAME_LAST_NAME  = SQLQueryEngine.display(Row,"TH_FIRST_NAME_LAST_NAME");
							String IDNO  = SQLQueryEngine.display(Row,"IDNO");
							String PERSONAL_TYPE = SQLQueryEngine.display(Row,"PERSONAL_TYPE");
							String CARDLINK_CUST_NO = SQLQueryEngine.display(Row,"CARDLINK_CUST_NO");
							String APPLY_TYPE  = SQLQueryEngine.display(Row,"APPLY_TYPE");
							String CARD_NO_MARK  = SQLQueryEngine.display(Row,"CARD_NO_MARK");
							String LOAN_AMT = SQLQueryEngine.display(Row,"LOAN_AMT");
							String CARD_DESC = SQLQueryEngine.display(Row,"CARD_DESC");
							String CARDLINK_CARD_STATUS = SQLQueryEngine.display(Row,"CARDLINK_STATUS");
							String KEY_DATA = QR+"_"+CARDLINK_REF_NO+"_"+CARDLINK_FLAG+"_"+PROCESSIONG_DATE;
							
							logger.debug("KEY_DATA=:"+KEY_DATA);
							if(!KEY_GROUP.equals(KEY_DATA)){
								applicationCardInfo = new HashMap<String, Object>();
								resultInfo.add(applicationCardInfo);
								
							}
							if(fail.equals(CARDLINK_CARD_STATUS)){
								CARDLINK_FLAG = CARDLINK_CARD_STATUS;
							}
							applicationCardInfo.put("QR", QR);
							applicationCardInfo.put("APPLICATION_GROUP_ID", APPLICATION_GROUP_ID);
							applicationCardInfo.put("CARDLINK_REF_NO", CARDLINK_REF_NO);
							applicationCardInfo.put("CARDLINK_FLAG", CARDLINK_FLAG);
							applicationCardInfo.put("PROCESSIONG_DATE", PROCESSIONG_DATE);
							applicationCardInfo.put("CNT", SQLQueryEngine.display(Row,"CNT"));
							applicationCardInfo.put("CURSOR_INDEX", SQLQueryEngine.display(Row,"CURSOR_INDEX"));
							
							HashMap<String, Object> hPersonal = (HashMap<String, Object>)applicationCardInfo.get("PERSONAL_CARD");
							if(Util.empty(hPersonal)){
								hPersonal = new HashMap<String, Object>();
								applicationCardInfo.put("PERSONAL_CARD", hPersonal);
							}
							
							applicationCardInfo.put("REG_TYPE", REG_TYPE);
														
							String PERSONAL_KEY = IDNO+"_"+PERSONAL_TYPE+"_"+CARDLINK_CUST_NO;
							HashMap<String, Object> personalCardInfo  = (HashMap<String, Object> )hPersonal.get(PERSONAL_KEY);
							if(Util.empty(personalCardInfo)){
								personalCardInfo = new HashMap<String, Object>();
								hPersonal.put(PERSONAL_KEY, personalCardInfo);
							}
							
							personalCardInfo.put("TH_FIRST_NAME_LAST_NAME", TH_FIRST_NAME_LAST_NAME);
							personalCardInfo.put("PERSONAL_TYPE", PERSONAL_TYPE);
							personalCardInfo.put("CARDLINK_CUST_NO", CARDLINK_CUST_NO);
							
							
							ArrayList<HashMap<String, Object>>  cardInfos  = (ArrayList<HashMap<String, Object>>)personalCardInfo.get("CARD_INFO_LIST");
							if(Util.empty(cardInfos)){
								cardInfos = new ArrayList<HashMap<String,Object>>();
								personalCardInfo.put("CARD_INFO_LIST", cardInfos);
							}
							HashMap<String, Object>  hCardInfo = new HashMap<String, Object>();
							hCardInfo.put("APPLY_TYPE", APPLY_TYPE);
							hCardInfo.put("CARD_NO_MARK",CARD_NO_MARK);
							hCardInfo.put("LOAN_AMT",LOAN_AMT);
							hCardInfo.put("CARD_DESC",CARD_DESC);
							cardInfos.add(hCardInfo);
							
							KEY_GROUP = KEY_DATA;
							
						}
					}
					
					if(null!=applicationCardInfo){
//						logger.debug("newGrouplist>>"+ new Gson().toJson(resultInfo));
						searchForm.setResults(resultInfo);
					}
					
					
				}
			};
			
			logger.debug("do postSearchResult()..");
			logger.debug("CM_APPLICATION_GROUP_NO ::" + searchForm.getString("CM_APPLICATION_GROUP_NO"));
			
			
			searchForm.setNextPage(false);
			searchForm.setAtPage(1);	
			SearchQueryEngine SearchEngine = new SearchQueryEngine();
			searchForm.setSQL(getSelectSearchSQL(searchForm));
			SearchEngine.search(searchForm);
			getRequest().getSession().setAttribute(SearchFormHandler.SearchForm,searchForm);
		} catch (Exception e) {
			logger.fatal("ERROR",e);
		}
		return true;
	}

	public String getSelectSearchSQL(SearchFormHandler searchForm) {
		StringBuilder sql = new StringBuilder();
		
		String CARDLINK_FLAG_FAIL = SystemConstant.getConstant("CARDLINK_FLAG_FAIL");
		String CARDLINK_TEN_MILLION = SystemConstant.getConstant("CARDLINK_TEN_MILLION");
		String FIELD_ID_CARD_TYPE = SystemConstant.getConstant("FIELD_ID_CARD_TYPE");
		String CARDLINK_FLAG_TEN_MILLION_STATUS = SystemConstant.getConstant("CARDLINK_FLAG_TEN_MILLION_STATUS");
		String CARDLINK_BATCH_WAITING = SystemConstant.getConstant("CARDLINK_BATCH_WAITING");
		
		String CARDLINK_FLAG_SUCCESS = SystemConstant.getConstant("CARDLINK_FLAG_SUCCESS");
		String CARDLINK_FLAG_FAIL_STATUS = SystemConstant.getConstant("CARDLINK_FLAG_FAIL_STATUS");
		String CARDLINK_FLAG_BATCH_WAITING_STATUS = SystemConstant.getConstant("CARDLINK_FLAG_BATCH_WAITING_STATUS");
		String FIELD_ID_SEND_DATA_TO_CARDLINK =  SystemConstant.getConstant("FIELD_ID_SEND_DATA_TO_CARDLINK");
		
		sql.append(" SELECT * FROM ( ");
		
		sql.append("SELECT DISTINCT G.APPLICATION_GROUP_NO AS QR,G.APPLICATION_GROUP_ID,  ");
		sql.append("  A.CARDLINK_REF_NO, ");
		sql.append("  A.APPLICATION_TYPE AS APPLY_TYPE, ");
		sql.append("  P.IDNO, ");
		sql.append("  P.TH_FIRST_NAME || ' ' ||  P.TH_LAST_NAME AS TH_FIRST_NAME_LAST_NAME , ");
		sql.append("  CC.CARDLINK_CUST_NO, ");
		sql.append("  C.CARD_NO_MARK, ");
		sql.append("  BM.DISPLAY_NAME CARD_DESC, ");
		sql.append("  L.LOAN_AMT, ");
		sql.append("  P.PERSONAL_TYPE, ");
		sql.append("  A.CARDLINK_FLAG, ");
		sql.append("  TO_CHAR(CR.PROCESSIONG_DATE,'dd/MM/yyyy | HH:MM','NLS_CALENDAR=''THAI BUDDHA') AS PROCESSIONG_DATE, ");
		sql.append("  C.CARD_TYPE, ");
		sql.append("  C.CARD_LEVEL, ");
		sql.append(" CC.CARDLINK_STATUS, ");
		sql.append(" CASE WHEN G.APPLICATION_GROUP_NO LIKE 'EA%' THEN 'EAPP' ELSE 'FLP' END AS REG_TYPE ");
		sql.append(" FROM ORIG_APPLICATION_GROUP G ");
		sql.append(" JOIN ORIG_APPLICATION   A ON A.APPLICATION_GROUP_ID = G.APPLICATION_GROUP_ID ");
		sql.append(" JOIN ORIG_LOAN     L ON L.APPLICATION_RECORD_ID = A.APPLICATION_RECORD_ID ");
		sql.append(" JOIN ORIG_CARD     C ON C.LOAN_ID = L.LOAN_ID ");
		sql.append(" JOIN ORIG_PERSONAL_RELATION PR ON PR.REF_ID = A.APPLICATION_RECORD_ID ");
		sql.append(" JOIN ORIG_PERSONAL_INFO  P ON PR.PERSONAL_ID = P.PERSONAL_ID ");
		sql.append(" JOIN INF_CARDLINK_RESULT  CR ON CR.REF_ID = A.CARDLINK_REF_NO ");
		sql.append(" JOIN ORIG_CARDLINK_CUSTOMER CC ON CC.CARDLINK_CUST_ID = C.CARDLINK_CUST_ID ");
		sql.append(" JOIN CARD_TYPE CT ON CT.CARD_TYPE_ID = C.CARD_TYPE "); 
		sql.append(" JOIN LIST_BOX_MASTER BM ON BM.FIELD_ID = '"+FIELD_ID_CARD_TYPE+"' AND BM.CHOICE_NO = CT.CARD_CODE ");
		sql.append(" JOIN INF_BATCH_LOG BL ON BL.REF_ID = A.CARDLINK_REF_NO ");
		sql.append(" AND CC.CARDLINK_STATUS IN ('F','W','S') AND A.CARDLINK_FLAG IN ('F','W','S') AND BL.INTERFACE_CODE = 'CL001' ");
		sql.append(" WHERE (1=1) ");
		sql.append(" AND G.JOB_STATE IS NOT NULL ");
		if (!searchForm.empty("CM_CARDLINK_FLAG")) {
			String cardlinkFlagStatus = searchForm.getString("CM_CARDLINK_FLAG");
			
			if(CARDLINK_FLAG_SUCCESS.equals(cardlinkFlagStatus)){
				sql.append(" AND A.CARDLINK_FLAG   = 'S' AND CC.CARDLINK_STATUS        = 'S' ");
				searchForm.setString(CARDLINK_FLAG_SUCCESS);
				searchForm.setString(CARDLINK_FLAG_SUCCESS);
			}else if(CARDLINK_FLAG_FAIL_STATUS.equals(cardlinkFlagStatus)){
				sql.append(" AND (A.CARDLINK_FLAG   = 'F' OR CC.CARDLINK_STATUS        = 'F') ");
			}else if(CARDLINK_FLAG_BATCH_WAITING_STATUS.equals(cardlinkFlagStatus)){
				sql.append(" AND ((A.CARDLINK_FLAG = 'W' AND CC.CARDLINK_STATUS = 'W') OR (A.CARDLINK_FLAG = 'W' AND CC.CARDLINK_STATUS = 'S') OR (A.CARDLINK_FLAG = 'S' AND CC.CARDLINK_STATUS = 'W'))");
			}else if(CARDLINK_FLAG_TEN_MILLION_STATUS.equals(cardlinkFlagStatus)){
				String loanAmtCondition = CacheControl.getName(FIELD_ID_SEND_DATA_TO_CARDLINK,cardlinkFlagStatus, "SYSTEM_ID2");
				logger.debug("loanAmtCondition : "+loanAmtCondition);
				if(!Util.empty(loanAmtCondition)){
					sql.append(" AND L.LOAN_AMT "+loanAmtCondition);
				}
				sql.append(" AND CC.CARDLINK_STATUS = 'S' ");
				cardlinkFlagStatus = CARDLINK_FLAG_SUCCESS;
			}
		}
		if (!searchForm.empty("CM_CARDLINK_REF_NO")) {
			sql.append(" AND A.CARDLINK_REF_NO        = ? ");
			searchForm.setString(searchForm.getString("CM_CARDLINK_REF_NO"));
		}
		if (!searchForm.empty("CM_PROCESSIONG_DATE")) {
			sql.append(" AND TRUNC(CR.PROCESSIONG_DATE) = TRUNC(?) ");
			searchForm.setDate(searchForm.getDate("CM_PROCESSIONG_DATE", FormatUtil.TH));
		}
		if (!searchForm.empty("CM_APPLICATION_GROUP_NO")) {
			sql.append(" AND G.APPLICATION_GROUP_NO        = ? ");
			searchForm.setString(searchForm.getString("CM_APPLICATION_GROUP_NO"));
		}
		if (!searchForm.empty("FIRST_NAME")) {
			sql.append(" AND P.TH_FIRST_NAME        = ? ");
			searchForm.setString(searchForm.getString("FIRST_NAME"));
		}
		if (!searchForm.empty("LAST_NAME")) {
			sql.append(" AND P.TH_LAST_NAME        = ? ");
			searchForm.setString(searchForm.getString("LAST_NAME"));
		}
		sql.append(" AND CR.RESULT_ID = (SELECT MAX(CR1.RESULT_ID) FROM INF_CARDLINK_RESULT CR1 WHERE CR1.REF_ID = A.CARDLINK_REF_NO) ");
		
		sql.append(" UNION ALL ");
		
		sql.append("SELECT DISTINCT G.APPLICATION_GROUP_NO AS QR,G.APPLICATION_GROUP_ID,  ");
		sql.append("  A.CARDLINK_REF_NO, ");
		sql.append("  A.APPLICATION_TYPE AS APPLY_TYPE, ");
		sql.append("  P.IDNO, ");
		sql.append("  P.TH_FIRST_NAME || ' ' ||  P.TH_LAST_NAME AS TH_FIRST_NAME_LAST_NAME , ");
		sql.append("  CC.CARDLINK_CUST_NO, ");
		sql.append("  C.CARD_NO_MARK, ");
		sql.append("  BM.DISPLAY_NAME CARD_DESC, ");
		sql.append("  L.LOAN_AMT, ");
		sql.append("  P.PERSONAL_TYPE, ");
		sql.append("  A.CARDLINK_FLAG, ");
		sql.append("  TO_CHAR(CR.PROCESSIONG_DATE,'dd/MM/yyyy | HH:MM','NLS_CALENDAR=''THAI BUDDHA') AS PROCESSIONG_DATE, ");
		sql.append("  C.CARD_TYPE, ");
		sql.append("  C.CARD_LEVEL, ");
		sql.append(" CC.CARDLINK_STATUS, ");
		sql.append(" 'MLP' AS REG_TYPE  ");
		sql.append(" FROM MLP_ORIG_APPLICATION_GROUP G ");
		sql.append(" JOIN MLP_ORIG_APPLICATION   A ON A.APPLICATION_GROUP_ID = G.APPLICATION_GROUP_ID ");
		sql.append(" JOIN MLP_ORIG_LOAN     L ON L.APPLICATION_RECORD_ID = A.APPLICATION_RECORD_ID ");
		sql.append(" JOIN MLP_ORIG_CARD     C ON C.LOAN_ID = L.LOAN_ID ");
		sql.append(" JOIN MLP_ORIG_PERSONAL_RELATION PR ON PR.REF_ID = A.APPLICATION_RECORD_ID ");
		sql.append(" JOIN MLP_ORIG_PERSONAL_INFO  P ON PR.PERSONAL_ID = P.PERSONAL_ID ");
		sql.append(" JOIN MLP_INF_CARDLINK_RESULT  CR ON CR.REF_ID = A.CARDLINK_REF_NO ");
		sql.append(" JOIN MLP_ORIG_CARDLINK_CUSTOMER CC ON CC.CARDLINK_CUST_ID = C.CARDLINK_CUST_ID ");
		sql.append(" JOIN CARD_TYPE CT ON CT.CARD_TYPE_ID = C.CARD_TYPE "); 
		sql.append(" JOIN LIST_BOX_MASTER BM ON BM.FIELD_ID = '"+FIELD_ID_CARD_TYPE+"' AND BM.CHOICE_NO = CT.CARD_CODE ");
		sql.append(" JOIN MLP_INF_BATCH_LOG BL ON BL.REF_ID = A.CARDLINK_REF_NO ");
		sql.append(" AND CC.CARDLINK_STATUS IN ('F','W','S') AND A.CARDLINK_FLAG IN ('F','W','S') AND BL.INTERFACE_CODE = 'CL001' ");
		sql.append(" WHERE (1=1) ");
		sql.append(" AND G.JOB_STATE IS NOT NULL ");
		if (!searchForm.empty("CM_CARDLINK_FLAG")) {
			String cardlinkFlagStatus = searchForm.getString("CM_CARDLINK_FLAG");
			
			if(CARDLINK_FLAG_SUCCESS.equals(cardlinkFlagStatus)){
				sql.append(" AND A.CARDLINK_FLAG   = 'S' AND CC.CARDLINK_STATUS        = 'S' ");
				searchForm.setString(CARDLINK_FLAG_SUCCESS);
				searchForm.setString(CARDLINK_FLAG_SUCCESS);
			}else if(CARDLINK_FLAG_FAIL_STATUS.equals(cardlinkFlagStatus)){
				sql.append(" AND (A.CARDLINK_FLAG   = 'F' OR CC.CARDLINK_STATUS        = 'F') ");
			}else if(CARDLINK_FLAG_BATCH_WAITING_STATUS.equals(cardlinkFlagStatus)){
				sql.append(" AND ((A.CARDLINK_FLAG = 'W' AND CC.CARDLINK_STATUS = 'W') OR (A.CARDLINK_FLAG = 'W' AND CC.CARDLINK_STATUS = 'S') OR (A.CARDLINK_FLAG = 'S' AND CC.CARDLINK_STATUS = 'W'))");
			}else if(CARDLINK_FLAG_TEN_MILLION_STATUS.equals(cardlinkFlagStatus)){
				String loanAmtCondition = CacheControl.getName(FIELD_ID_SEND_DATA_TO_CARDLINK,cardlinkFlagStatus, "SYSTEM_ID2");
				logger.debug("loanAmtCondition : "+loanAmtCondition);
				if(!Util.empty(loanAmtCondition)){
					sql.append(" AND L.LOAN_AMT "+loanAmtCondition);
				}
				sql.append(" AND CC.CARDLINK_STATUS = 'S' ");
				cardlinkFlagStatus = CARDLINK_FLAG_SUCCESS;
			}
		}
		if (!searchForm.empty("CM_CARDLINK_REF_NO")) {
			sql.append(" AND A.CARDLINK_REF_NO        = ? ");
			searchForm.setString(searchForm.getString("CM_CARDLINK_REF_NO"));
		}
		if (!searchForm.empty("CM_PROCESSIONG_DATE")) {
			sql.append(" AND TRUNC(CR.PROCESSIONG_DATE) = TRUNC(?) ");
			searchForm.setDate(searchForm.getDate("CM_PROCESSIONG_DATE", FormatUtil.TH));
		}
		if (!searchForm.empty("CM_APPLICATION_GROUP_NO")) {
			sql.append(" AND G.APPLICATION_GROUP_NO        = ? ");
			searchForm.setString(searchForm.getString("CM_APPLICATION_GROUP_NO"));
		}
		if (!searchForm.empty("FIRST_NAME")) {
			sql.append(" AND P.TH_FIRST_NAME        = ? ");
			searchForm.setString(searchForm.getString("FIRST_NAME"));
		}
		if (!searchForm.empty("LAST_NAME")) {
			sql.append(" AND P.TH_LAST_NAME        = ? ");
			searchForm.setString(searchForm.getString("LAST_NAME"));
		}
		sql.append(" AND CR.RESULT_ID = (SELECT MAX(CR1.RESULT_ID) FROM MLP_INF_CARDLINK_RESULT CR1 WHERE CR1.REF_ID = A.CARDLINK_REF_NO) ");
		
		sql.append(" ) ");
		
		//sql.append(" ORDER BY G.APPLICATION_GROUP_NO, A.CARDLINK_REF_NO, PROCESSIONG_DATE,P.PERSONAL_TYPE");
		sql.append(" ORDER BY QR, CARDLINK_REF_NO, PROCESSIONG_DATE, PERSONAL_TYPE");

		logger.debug("sql.toString() ::" + sql.toString());
		return sql.toString();
	}

	@Override
	public int getNextActivityType() {
		return FrontController.PAGE;
	}

}
