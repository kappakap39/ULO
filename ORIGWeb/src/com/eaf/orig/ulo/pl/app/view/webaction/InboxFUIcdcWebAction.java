package com.eaf.orig.ulo.pl.app.view.webaction;


import org.apache.log4j.Logger;

import com.eaf.j2ee.pattern.control.FrontController;
import com.eaf.j2ee.pattern.control.event.Event;
import com.eaf.j2ee.pattern.control.event.EventResponse;
import com.eaf.j2ee.pattern.view.webaction.WebAction;
import com.eaf.j2ee.pattern.view.webaction.WebActionHelper;
import com.eaf.orig.profile.model.UserDetailM;
import com.eaf.orig.shared.constant.OrigConstant;
import com.eaf.orig.shared.model.ProcessMenuM;
import com.eaf.orig.shared.search.SearchM;
import com.eaf.orig.shared.search.SearchMHelper;
import com.eaf.orig.shared.search.SearchMInf;
import com.eaf.orig.shared.util.OrigUtil;
import com.eaf.orig.ulo.pl.constant.WorkflowConstant;

public class InboxFUIcdcWebAction extends WebActionHelper implements WebAction,SearchMInf{
	
	static Logger logger = Logger.getLogger(InboxFUIcdcWebAction.class);	
	private String nextAction = null;
	
	@Override
	public Event toEvent(){
		return null;
	}

	@Override
	public boolean requiredModelRequest(){
		return false;
	}

	@Override
	public boolean processEventResponse(EventResponse response){
		return false;
	}

	@Override
	public boolean preModelRequest(){		
		try{	
			UserDetailM userM =	(UserDetailM) getRequest().getSession().getAttribute("ORIGUser");
			SearchM searchM = (SearchM) getRequest().getSession().getAttribute("SearchM");	
			
			String fromSearch = getRequest().getParameter("fromSearch");			
			if(null == searchM || "Y".equals(fromSearch)){
				searchM = new SearchM();
			}
			
			setParameter(searchM,userM);			
			setCountSQL(searchM,userM);
			setMainSQL(searchM,userM);
			
			searchM.setNextPage(false);
			searchM.setItemsPerPage(20);
			searchM.setReturnToAction("page=FU_INBOX_ICDC_SCREEN");			

			getRequest().getSession().setAttribute("SearchM",searchM);
			
            nextAction = "action=SearchMWebAction";
            
		}catch(Exception e){
			logger.fatal("Exception >> ",e);
			return false;
		}
		return true;
	}
		
	private String getToDoListID(){
		String tdID = getRequest().getParameter("MenuID");
		if(OrigUtil.isEmptyString(tdID)){
			 ProcessMenuM currentMenuM = (ProcessMenuM) getRequest().getSession().getAttribute("CurrentMenuM");
			 if(currentMenuM != null){
				 tdID = currentMenuM.getMenuID();
			 }else{
				 tdID = WorkflowConstant.TODO_LIST_ID.FU_ICDC_INBOX;
			 }
		}		
		return tdID;
	}

	@Override
	public void setParameter(SearchM searchM,UserDetailM userM) throws Exception {	
		String fromSearch = getRequest().getParameter("fromSearch");		
		logger.debug("fromSearch >> "+fromSearch);		
		if("Y".equals(fromSearch)){			
			String appDateFrom 	 = (String)getRequest().getParameter("app_date_from");
			String appDateTo	 = (String)getRequest().getParameter("app_date_to");
			String fuDateFrom	 = (String)getRequest().getParameter("fu_date_from");
			String fuDateTo		 = (String)getRequest().getParameter("fu_date_to");	
			String thaiFirstName = (String)getRequest().getParameter("firstName");
			String thaiLastName = (String)getRequest().getParameter("lastName");
			String applicationNo = (String)getRequest().getParameter("appNo");
			String idNo = (String)getRequest().getParameter("idNo");
			
			searchM.setSearch("APP_DATE_FROM",SearchMHelper.Date,appDateFrom);
			searchM.setSearch("APP_DATE_TO",SearchMHelper.Date,appDateTo);
			searchM.setSearch("FU_DATE_FROM",SearchMHelper.Date,fuDateFrom);
			searchM.setSearch("FU_DATE_TO",SearchMHelper.Date,fuDateTo);
			searchM.setSearch("THAI_FNAME",SearchMHelper.String,thaiFirstName);
			searchM.setSearch("SURNAME_THAI",SearchMHelper.String,thaiLastName);
			searchM.setSearch("APPLICATION_NO",SearchMHelper.String,applicationNo);
			searchM.setSearch("IDENTIFICATION_NO",SearchMHelper.String,idNo);	
		}	
	}
	
	@Override
	public void setCountSQL(SearchM searchM,UserDetailM userM) throws Exception {
		StringBuilder SQL = new StringBuilder();
			SQL.append(" SELECT COUNT (1) COUNT ");
			SQL.append("   FROM (SELECT M.FU_QUEUE_DATE ");
			SQL.append("           FROM (SELECT (SELECT MAX(WIH.CREATE_DATE) ");
			SQL.append("                           FROM WF_INSTANT_HISTORY WIH, ");
			SQL.append("                                WF_ACTIVITY_TEMPLATE WAT ");
			SQL.append("                          WHERE     WIH.JOB_ID = J.JOB_ID ");
			SQL.append("                                AND WIH.ATID = WAT.ATID ");
			SQL.append("                                AND WAT.ROLE_ID = ? AND WAT.ACTIVITY_TYPE = 'AQ' AND WIH.ACTION <> 'Reallocate') ");		
			SQL.append("                           FU_QUEUE_DATE ");
			SQL.append("  FROM ORIG_APPLICATION A, ");
			SQL.append("       ORIG_PERSONAL_INFO P, ");
			SQL.append("       WF_JOBID_MAPPING J, ");
			SQL.append("       WF_WORK_QUEUE Q, ");
			SQL.append("       WF_TODO_LIST WTL ");
			SQL.append("  WHERE A.APPLICATION_RECORD_ID = P.APPLICATION_RECORD_ID ");
			SQL.append("  	AND A.APPLICATION_RECORD_ID = J.APPLICATION_RECORD_ID ");
			SQL.append("    AND J.JOB_ID = Q.JOB_ID ");
			SQL.append("    AND Q.ATID = WTL.ATID ");
			SQL.append("    AND P.PERSONAL_TYPE = ? ");	
			SQL.append("    AND J.JOB_STATUS = ? ");
			SQL.append("    AND Q.OWNER = ? ");
			SQL.append("    AND WTL.TDID = ? ");

			searchM.addCriteria(OrigConstant.ROLE_FU,SearchMHelper.COUNT);	
			searchM.addCriteria(OrigConstant.PERSONAL_TYPE_APPLICANT,SearchMHelper.COUNT);
			searchM.addCriteria(WorkflowConstant.JobStatus.ACTIVE,SearchMHelper.COUNT);
			searchM.addCriteria(userM.getUserName(),SearchMHelper.COUNT);	
			searchM.addCriteria(getToDoListID(),SearchMHelper.COUNT);

	        if(null != searchM.getObject("FU_DATE_FROM")){
	        	SQL.append("AND A.FU_LAST_DATE >= ? ");
	        	searchM.addCriteria(searchM.getObject("FU_DATE_FROM"),SearchMHelper.COUNT);
	        }
	        if(null != searchM.getObject("FU_DATE_TO")){
	        	SQL.append("AND A.FU_LAST_DATE < ?+1");
	        	searchM.addCriteria(searchM.getObject("FU_DATE_TO"),SearchMHelper.COUNT);
	        }
	        if(!OrigUtil.isEmptyString(searchM.getString("THAI_FNAME"))){
	        	SQL.append("AND P.TH_FIRST_NAME LIKE ? ");
	        	searchM.addCriteria(searchM.getString("THAI_FNAME")+"%",SearchMHelper.COUNT);
	        }
	        if(!OrigUtil.isEmptyString(searchM.getString("SURNAME_THAI"))){
	        	SQL.append("AND P.TH_LAST_NAME LIKE ? ");
	        	searchM.addCriteria(searchM.getString("SURNAME_THAI")+"%",SearchMHelper.COUNT);
	        } 
	        if(!OrigUtil.isEmptyString(searchM.getString("APPLICATION_NO"))){
	        	SQL.append("AND A.APPLICATION_NO = UPPER(?) ");
	        	searchM.addCriteria(searchM.getString("APPLICATION_NO"),SearchMHelper.COUNT);
	        } 
	        if(!OrigUtil.isEmptyString(searchM.getString("IDENTIFICATION_NO"))){
	        	SQL.append("AND P.IDNO = ? ");
	        	searchM.addCriteria(searchM.getString("IDENTIFICATION_NO"),SearchMHelper.COUNT);
	        }			
	        SQL.append(" ) M ");
	        
	        if(null != searchM.getObject("APP_DATE_FROM") && null != searchM.getObject("APP_DATE_TO")){
	        	SQL.append("  WHERE M.FU_QUEUE_DATE >= ? ");	
	        	searchM.addCriteria(searchM.getObject("APP_DATE_FROM"),SearchMHelper.COUNT);
	         	SQL.append("  AND M.FU_QUEUE_DATE  <  ?+1 ");	
	        	searchM.addCriteria(searchM.getObject("APP_DATE_TO"),SearchMHelper.COUNT);
	        }	
	        
	        SQL.append(" )");
	        
		searchM.setSqlCount(SQL.toString());
	}
	
	@Override
	public void setMainSQL(SearchM searchM,UserDetailM userM) throws Exception {
		StringBuilder SQL = new StringBuilder();
			SQL.append(" SELECT M. CURSOR_INDEX ,");
			SQL.append("      M.APPLICATION_RECORD_ID,  ");
			SQL.append("      PKA_UTIL.DATE_TO_STRING_THAI_DATE(M.FU_QUEUE_DATE,'dd/mm/yyyy hh24:mi:ss') FU_QUEUE_DATE,  ");
			SQL.append("      M.PRIORITY,  ");
			SQL.append("      M.APPLICATION_NO,  ");
			SQL.append("      M.APPLICATION_STATUS,  ");
			SQL.append("      M.V_TO_FLAG,  ");
			SQL.append("      M.NAME_SURNAME,  ");
			SQL.append("      M.IDNO,  ");
			SQL.append("      (SELECT MP.PRODUCT_DESC FROM MS_PRODUCT MP WHERE MP.PRODUCT_CODE = SUBSTR(M.BUSINESS_CLASS_ID,5,3) AND ROWNUM = 1),  ");
			SQL.append("      PKA_UTIL.DATE_TO_STRING_THAI_DATE(M.APPLICATION_DATE,'dd/mm/yyyy hh24:mi:ss') APPLICATION_DATE,  ");
			SQL.append("      PKA_APP_UTIL.GET_KBANK_USER_NAME_SURNAME(M.FU_LAST_ID) FU_LAST_ID,  ");
			SQL.append("      PKA_UTIL.DATE_TO_STRING_THAI_DATE(M.FU_LAST_DATE,'dd/mm/yyyy hh24:mi:ss') FU_LAST_DATE,  ");
			SQL.append("      PKA_FU_UTIL.FU_COUNT(M.APPLICATION_RECORD_ID,M.JOB_STATE,M.FU_QUEUE_DATE) FU_COUNT,  ");
			SQL.append("      PKA_FU_UTIL.FU_OVER_TIME(M.FU_QUEUE_DATE, M.BUSINESS_CLASS_ID) RED_FLAG,  ");
			SQL.append("      M.FU_NEW_IMG_READY,  ");
			SQL.append("  PKA_FU_UTIL.FU_REJECT_DATE(M.APPLICATION_RECORD_ID,M.APPLY_CHANNEL,M.FU_NO_TIME_OUT_FLAG,M.BUSINESS_CLASS_ID,M.SMS_FOLLOW_UP_DATE) FU_REJECT_DATE  ");
			SQL.append(" FROM ( ");
			SQL.append(" SELECT ROWNUM AS CURSOR_INDEX,X.* FROM ( ");
			SQL.append(" SELECT ");
			SQL.append("     A.APPLICATION_RECORD_ID, ");
			SQL.append("     A.PRIORITY, ");
			SQL.append("     A.APPLICATION_NO, ");
			SQL.append("     A.APPLICATION_STATUS, ");
			SQL.append("     DECODE(A.REOPEN_FLAG,'Y','Yes','No') V_TO_FLAG, ");
			SQL.append("     P.TH_FIRST_NAME || ' ' || P.TH_LAST_NAME NAME_SURNAME, ");
			SQL.append("     P.IDNO, ");
			SQL.append("     A.APPLICATION_DATE, ");
			SQL.append("     A.FU_LAST_ID, ");
			SQL.append("     A.FU_LAST_DATE, ");
			SQL.append("     A.JOB_STATE, ");
			SQL.append("     J.JOB_ID, ");
			SQL.append("     A.BUSINESS_CLASS_ID, ");
			SQL.append("     A.FU_NEW_IMG_READY, ");
			SQL.append("     WIH.CREATE_DATE FU_QUEUE_DATE, ");
			SQL.append("     A.APPLY_CHANNEL, ");
			SQL.append("     A.FU_NO_TIME_OUT_FLAG, ");
			SQL.append("     A.SMS_FOLLOW_UP_DATE ");
			SQL.append(" FROM ");
			SQL.append("     ORIG_APPLICATION A,ORIG_PERSONAL_INFO P, ");
			SQL.append("     WF_JOBID_MAPPING J,WF_WORK_QUEUE Q, ");
			SQL.append("     WF_INSTANT_HISTORY WIH,  WF_TODO_LIST WTL ");
			SQL.append(" WHERE ");
			SQL.append("     A.APPLICATION_RECORD_ID = P.APPLICATION_RECORD_ID ");
			SQL.append(" AND A.APPLICATION_RECORD_ID = J.APPLICATION_RECORD_ID ");
			SQL.append(" AND J.JOB_ID = Q.JOB_ID ");
			SQL.append(" AND J.JOB_ID = WIH.JOB_ID ");
			SQL.append(" AND WIH.SEQ = ");
			SQL.append("     ( ");
			SQL.append("         SELECT MAX(SEQ) ");
			SQL.append("         FROM WF_INSTANT_HISTORY TMP_WIH ,WF_ACTIVITY_TEMPLATE TMP_WAT ");
			SQL.append("         WHERE ");
			SQL.append("             TMP_WIH.JOB_ID = WIH.JOB_ID  AND TMP_WIH.ATID = TMP_WAT.ATID ");
			SQL.append("         AND TMP_WAT.ROLE_ID = ? AND TMP_WAT.ACTIVITY_TYPE = 'AQ' AND TMP_WIH.ACTION <> 'Reallocate' ");
			SQL.append("     ) ");
			SQL.append(" AND Q.ATID = WTL.ATID ");
			SQL.append(" AND P.PERSONAL_TYPE = ? ");
			SQL.append(" AND J.JOB_STATUS = ? ");
			SQL.append(" AND WTL.TDID = ? ");
			SQL.append(" AND Q.OWNER = ? ");
			
			searchM.addCriteria(OrigConstant.ROLE_FU);	
			searchM.addCriteria(OrigConstant.PERSONAL_TYPE_APPLICANT);
			searchM.addCriteria(WorkflowConstant.JobStatus.ACTIVE);
			searchM.addCriteria(getToDoListID());		
			searchM.addCriteria(userM.getUserName());	
			
	        if(null != searchM.getObject("FU_DATE_FROM")){
	        	SQL.append("AND A.FU_LAST_DATE >= ? ");
	        	searchM.addCriteria(searchM.getObject("FU_DATE_FROM"));
	        }
	        if(null != searchM.getObject("FU_DATE_TO")){
	        	SQL.append("AND A.FU_LAST_DATE < ?+1 ");
	        	searchM.addCriteria(searchM.getObject("FU_DATE_TO"));
	        }
	        if(!OrigUtil.isEmptyString(searchM.getString("THAI_FNAME"))){
	        	SQL.append("AND P.TH_FIRST_NAME LIKE ? ");
	        	searchM.addCriteria(searchM.getString("THAI_FNAME")+"%");
	        }
	        if(!OrigUtil.isEmptyString(searchM.getString("SURNAME_THAI"))){
	        	SQL.append("AND P.TH_LAST_NAME LIKE ? ");
	        	searchM.addCriteria(searchM.getString("SURNAME_THAI")+"%");
	        } 
	        if(!OrigUtil.isEmptyString(searchM.getString("APPLICATION_NO"))){
	        	SQL.append("AND A.APPLICATION_NO = ? ");
	        	searchM.addCriteria(searchM.getString("APPLICATION_NO"));
	        } 
	        if(!OrigUtil.isEmptyString(searchM.getString("IDENTIFICATION_NO"))){
	        	SQL.append("AND P.IDNO = ? ");
	        	searchM.addCriteria(searchM.getString("IDENTIFICATION_NO"));
	        }	
	        
	        if(null != searchM.getObject("APP_DATE_FROM") && null != searchM.getObject("APP_DATE_TO")){
	        	SQL.append("  AND WIH.CREATE_DATE >= ? ");	
	        	searchM.addCriteria(searchM.getObject("APP_DATE_FROM"));
	         	SQL.append("  AND WIH.CREATE_DATE  <  ?+1 ");	
	        	searchM.addCriteria(searchM.getObject("APP_DATE_TO"));
	        }
	        
			SQL.append(" ORDER BY ");
			SQL.append("     A.PRIORITY DESC, ");
			SQL.append("     A.APPLICATION_DATE )X )M ");
			SQL.append(" WHERE M.CURSOR_INDEX > ? AND M.CURSOR_INDEX < ? ");
			
		searchM.setSqlMain(SQL.toString());
	}
	
	@Override
	public int getNextActivityType(){
		return FrontController.ACTION;
	}

    public String getNextActionParameter() {
        return nextAction;
    }
    
	@Override
	public boolean getCSRFToken(){
		return false;
	}	
}
