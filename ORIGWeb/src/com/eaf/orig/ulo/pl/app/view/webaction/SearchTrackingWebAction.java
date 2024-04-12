package com.eaf.orig.ulo.pl.app.view.webaction;

import java.util.Vector;

import org.apache.log4j.Logger;
import com.eaf.j2ee.pattern.control.FrontController;
import com.eaf.j2ee.pattern.control.event.Event;
import com.eaf.j2ee.pattern.control.event.EventResponse;
import com.eaf.j2ee.pattern.view.webaction.WebAction;
import com.eaf.j2ee.pattern.view.webaction.WebActionHelper;
import com.eaf.orig.cache.properties.ORIGCacheDataM;
import com.eaf.orig.profile.model.UserDetailM;
import com.eaf.orig.shared.constant.OrigConstant;
import com.eaf.orig.shared.model.ValueListM;
import com.eaf.orig.shared.util.OrigUtil;
import com.eaf.orig.shared.utility.ORIGCacheUtil;
import com.eaf.orig.ulo.pl.app.utility.ORIGLogic;
import com.eaf.orig.ulo.pl.app.utility.WebActionUtil;
import com.eaf.orig.ulo.pl.constant.WorkflowConstant;
import com.eaf.orig.ulo.pl.formcontrol.view.form.SearchHandler;

public class SearchTrackingWebAction extends WebActionHelper implements WebAction {
	
	static Logger log = Logger.getLogger(SearchTrackingWebAction.class);
	
	static String TYPE_WFROLE = "TYPE_WFROLE";
	static String TYPE_SEARCH_ROLE = "TYPE_SEARCH_ROLE";
	
	private String nextAction = null;
	private int index = 0;
	
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

		UserDetailM userM = (UserDetailM) getRequest().getSession().getAttribute("ORIGUser");
		
		String current_role = userM.getCurrentRole();
		
		SearchHandler handler = (SearchHandler)getRequest().getSession().getAttribute("SEARCH_DATAM");
		if(handler==null){
			handler = new SearchHandler();
		}
		SearchHandler.SearchDataM searchDataM = handler.getSearchM();		
		if (searchDataM == null) {
			searchDataM = new SearchHandler.SearchDataM();
		}
		
		setParameter(searchDataM);
		
		handler.setSearchM(searchDataM);
		
		getRequest().getSession().setAttribute("SEARCH_DATAM", handler);
		
		try{			
			index = 0;
			
			ValueListM valueListM = new ValueListM();
				
			SQL(valueListM,searchDataM,current_role);
			
			valueListM.setNextPage(false);
			valueListM.setItemsPerPage(20);
			valueListM.setReturnToAction("page=TRACKING_SCREEN");
			getRequest().getSession().setAttribute("VALUE_LIST", valueListM);
			nextAction = "action=ValueListWebAction";

			getRequest().getSession().setAttribute("searchType", "Y");
			
		}catch(Exception e){
			log.error("exception ", e);
		}
		return true;
	}
	
	public void setParameter(SearchHandler.SearchDataM searchDataM){
		String logOn = getRequest().getParameter("statusLogOn");
		String onJob = getRequest().getParameter("statusOnJob");
		String empFirstName = getRequest().getParameter("empFirstName");
		String empLastName = getRequest().getParameter("empLastName");
		String empId = getRequest().getParameter("empId");
		String tracking_group = getRequest().getParameter("tracking_group");
		String tracking_role = getRequest().getParameter("tracking_role");
		
		String checkSearch = getRequest().getParameter("checkSearch");
		
		if(OrigConstant.FLAG_Y.equals(checkSearch)){
			searchDataM.setEmpFirstName(empFirstName);
			searchDataM.setEmpLastName(empLastName);
			searchDataM.setEmpId(empId);
			searchDataM.setLogOn(logOn);
			searchDataM.setOnJob(onJob);
			searchDataM.setGroupRole(tracking_group);
			searchDataM.setRole(tracking_role);			
		}
	}
	
	@Override
	public int getNextActivityType(){
		return FrontController.ACTION;
	}

	@Override
	public String getNextActionParameter(){
		return nextAction;
	}

	@Override
	public boolean getCSRFToken(){
		return false;
	}

	public void SQL(ValueListM valueListM ,SearchHandler.SearchDataM searchDataM,String current_role){
				
		String searchRole = WebActionUtil.getUnderRole(current_role);
		String wfRole = WebActionUtil.getUnderRoleWf(current_role);
		
		Vector<ORIGCacheDataM> vRole =  new Vector<ORIGCacheDataM>();
		
		boolean fixrole = true;
		if(ORIGLogic.superTrackingSearch(current_role) && !OrigUtil.isEmptyString(searchDataM.getRole())){
			searchRole = searchDataM.getRole();
			wfRole = WebActionUtil.getRoleWf(searchDataM.getRole());
		}else if(ORIGLogic.superTrackingSearch(current_role)){
			fixrole = false;
			vRole = getRole(current_role, searchDataM);
		}
						
		log.debug(" >> current_role = "+current_role);
		log.debug(" >> search role = "+searchRole);
		log.debug(" >> workflow role = "+wfRole);
		
		StringBuilder SQL = new StringBuilder("");
				
		SQL.append(" SELECT DISTINCT ");
		SQL.append("     US.THAI_FIRSTNAME, ");
		SQL.append("     US.THAI_LASTNAME, ");
		SQL.append("     RO.ROLE_DESC, ");
		SQL.append("     UR.USER_NAME, ");
		SQL.append("     CASE NVL(US.LOGON_FLAG,'N') WHEN 'Y' THEN 'Log On' ELSE 'Log Off' END LOG_ON , ");			
		SQL.append("     ( ");
		SQL.append("         SELECT ");
		SQL.append("             MAX (ACTION_DATE) ");
		SQL.append("         FROM ");
		SQL.append("             HISTORY_LOG ");
		SQL.append("         WHERE ");
		SQL.append("             USERNAME = US.USER_NAME ");
		SQL.append("         AND ACTION_TYPE = ? ");
		SQL.append("         AND DESCRIPTION = ? ");		
			valueListM.setString(++index, OrigConstant.LOGIN);
			valueListM.setString(++index, OrigConstant.SUCCESS);
		
		SQL.append("     ) ");
		SQL.append("     LOGON_DATE, ");
		SQL.append("     ( ");
		SQL.append("         SELECT ");
		SQL.append("             MAX (ACTION_DATE) ");
		SQL.append("         FROM ");
		SQL.append("             HISTORY_LOG ");
		SQL.append("         WHERE ");
		SQL.append("             USERNAME = US.USER_NAME ");
		SQL.append("         AND ACTION_TYPE = ? ");
		SQL.append("         AND DESCRIPTION = ? ");		
			valueListM.setString(++index, OrigConstant.LOGOUT);
			valueListM.setString(++index, OrigConstant.SUCCESS);
		
		SQL.append("     ) ");
		SQL.append("     LOGOFF_DATE, ");
		SQL.append("     ( ");
		SQL.append("         SELECT ");
		SQL.append("             LISTAGG(TODO_LIST_NAME ||'#'|| FLAG || '|') WITHIN GROUP ( ");
		SQL.append("         ORDER BY ");
		SQL.append("             TODO_LIST_NAME) JOB_STATUS ");
		SQL.append("         FROM ");
		SQL.append("             ( ");
		SQL.append("                 SELECT DISTINCT ");
		SQL.append("                     UQ.USER_NAME, ");
		SQL.append("                     TLM.TODO_LIST_NAME TODO_LIST_NAME, ");
		SQL.append("                     DECODE(UQ.ONJOB_FLAG, 'N', 'Off', 'Y', 'On') FLAG , ");
		SQL.append("                     ACT.ROLE_ID ");
		SQL.append("                 FROM ");
		SQL.append("                     USER_WORK_QUEUE UQ, ");
		SQL.append("                     WF_TODO_LIST TL, WF_TODO_LIST_MASTER TLM,");
		SQL.append("                     WF_ACTIVITY_TEMPLATE ACT ");
		SQL.append("                 WHERE ");
		SQL.append("                     UQ.TDID = TL.TDID AND TL.TDID = TLM.TDID AND TLM.QUEUE_FLAG = 'Y' ");
		SQL.append("                 AND TL.ATID = ACT.ATID ");
				if(ORIGLogic.RoleICDC(searchRole)){
						SQL.append(" AND ACT.PTID = ? ");
						valueListM.setString(++index, WorkflowConstant.ProcessTemplate.KLOP002);
				}else{
						SQL.append(" AND ACT.PTID IN (?,?) ");
						valueListM.setString(++index,  WorkflowConstant.ProcessTemplate.KLOP001);
					valueListM.setString(++index,  WorkflowConstant.ProcessTemplate.KLOP003);
				}
		SQL.append("                 ORDER BY ");
		SQL.append("                     TLM.TODO_LIST_NAME ");
		SQL.append("             ) ");
		SQL.append("         WHERE ");
		SQL.append("             USER_NAME = US.USER_NAME ");
		
//		#septemwi modify
//		SQL.append("         AND ROLE_ID = RO.ROLE_NAME ");		
		if(fixrole){
			SQL.append(" AND ROLE_ID = ? ");	
			valueListM.setString(++index, wfRole);
		}
		
		SQL.append("     ) ");
		SQL.append("     JOB_STATUS , ");
		SQL.append("     NVL(PREV_JOB.PREV_JOB,0) + NVL(PREV_BLOCKER.PREV_BLOCKED,0) PREV_JOB, ");
		SQL.append("     NVL(PREV_JOB.PREV_EDIT,0) PREV_EDIT, ");
		SQL.append("     NVL(INPUT.INPUT,0) INPUT, ");
		SQL.append("     CASE WHEN NVL(OUTPUT.OUTPUT,0)  ");
		SQL.append("                 - NVL(SEND_BACK.SEND_BACK,0)  ");
		SQL.append("                 -NVL(REASSIGN.REASSIGN,0)  ");
		SQL.append("                 -(NVL(BLOCKER.BLOCKED,NVL(PREV_BLOCKER.PREV_BLOCKED,0)) -NVL(PREV_BLOCKER.PREV_BLOCKED,0) )  ");
		SQL.append("                 - NVL(CANCEL.CANCELED,0) > 0  ");
		SQL.append("     THEN NVL(OUTPUT.OUTPUT,0)  ");
		SQL.append("                 - NVL(SEND_BACK.SEND_BACK,0)  ");
		SQL.append("                 -NVL(REASSIGN.REASSIGN,0)  ");
		SQL.append("                 -(NVL(BLOCKER.BLOCKED,NVL(PREV_BLOCKER.PREV_BLOCKED,0)) -NVL(PREV_BLOCKER.PREV_BLOCKED,0) )  ");
		SQL.append("                 - NVL(CANCEL.CANCELED,0) ");
		SQL.append("     ELSE 0 END  OUTPUT, ");
		SQL.append("     NVL(SEND_BACK.SEND_BACK,0) SEND_BACK , ");
		SQL.append("     NVL(CANCEL.CANCELED,0) CANCEL , ");
		SQL.append("     NVL(REASSIGN.REASSIGN,0) RE_ASSIGN , ");
		SQL.append("     NVL(BLOCKER.BLOCKED,0) BLOCKED , ");
		SQL.append("     ( ");
		SQL.append("         SELECT ");
		SQL.append("             LISTAGG(APP_STATUS||'#'|| CNT|| '|' ) WITHIN GROUP ( ");
		SQL.append("         ORDER BY ");
		SQL.append("             APP_STATUS) APP_STATUS_NUM ");
		SQL.append("         FROM ");
		SQL.append("             ( ");
		SQL.append("                 SELECT ");
		SQL.append("                     COUNT (1) CNT, ");
		SQL.append("                     WQ.OWNER, ");
		SQL.append("                     WQ.APP_STATUS, ");
		SQL.append("                     ACT.ROLE_ID ");
		SQL.append("                 FROM ");
		SQL.append("                     WF_WORK_QUEUE WQ, ");
		SQL.append("                     WF_ACTIVITY_TEMPLATE ACT ");
		SQL.append("                 WHERE ACT.ATID = WQ.ATID  ");		
		if(fixrole){
			SQL.append(" AND ACT.ROLE_ID = ? ");	
			valueListM.setString(++index, wfRole);
			if(ORIGLogic.RoleICDC(searchRole)){
				SQL.append("  AND ACT.PTID = ? ");
				valueListM.setString(++index, WorkflowConstant.ProcessTemplate.KLOP002);
			}else{
				SQL.append("  AND ACT.PTID IN (?,?) ");
				valueListM.setString(++index,  WorkflowConstant.ProcessTemplate.KLOP001);
				valueListM.setString(++index,  WorkflowConstant.ProcessTemplate.KLOP003);
			}
		}		
		SQL.append("                 GROUP BY ");
		SQL.append("                     WQ.APP_STATUS, ");
		SQL.append("                     WQ.OWNER, ");
		SQL.append("                     ACT.ROLE_ID ");
		SQL.append("                 ORDER BY ");
		SQL.append("                     WQ.APP_STATUS ");
		SQL.append("             ) ");
		SQL.append("         WHERE ");
		SQL.append("             OWNER = US.USER_NAME ");
		SQL.append("     ) ");
		SQL.append("     APP_STATUS ");
		SQL.append(" FROM ");
		SQL.append("     USER_ROLE UR, ");
		SQL.append("     ROLE RO, ");
		SQL.append("     US_USER_DETAIL US , ");
		SQL.append("     ( ");
		SQL.append("         SELECT ");
		SQL.append("             OWNER , ");
		SQL.append("             SUM(PREV_JOB) - SUM(PREV_EDIT) PREV_JOB , ");
		SQL.append("             SUM(PREV_EDIT) PREV_EDIT ");
		SQL.append("         FROM ");
		SQL.append("             ( ");
		SQL.append("                 SELECT ");
		SQL.append("                     WF_HIS.OWNER OWNER , ");
		SQL.append("                     1 AS PREV_JOB , ");
		SQL.append("                     CASE ");
		SQL.append("                         WHEN WF_HIS.APP_STATUS LIKE '%(Edit)%' ");
		SQL.append("                         THEN 1 ");
		SQL.append("                         ELSE 0 ");
		SQL.append("                     END PREV_EDIT ");
		SQL.append("                 FROM ");
		SQL.append("                     WF_INSTANT_HISTORY WF_HIS ,WF_ACTIVITY_TEMPLATE ");
		SQL.append("                 WHERE ");	
		SQL.append("                 	 WF_ACTIVITY_TEMPLATE.ATID = WF_HIS.ATID ");
		SQL.append("                 AND WF_HIS.SEQ = ");
		SQL.append("                     ( ");
		SQL.append("                         SELECT ");
		SQL.append("                             MAX(WF.SEQ) ");
		SQL.append("                         FROM ");
		SQL.append("                             WF_INSTANT_HISTORY WF, ");
		SQL.append("                             WF_ACTIVITY_TEMPLATE TEMP ");
		SQL.append("                         WHERE ");
		SQL.append("                             WF.ATID = TEMP.ATID ");
		SQL.append("                         AND WF.JOB_ID = WF_HIS.JOB_ID ");
		SQL.append("                         AND WF.CREATE_DATE < TRUNC(SYSDATE) ");
		SQL.append("                 		 AND ( ");
		SQL.append("                         		WF.COMPLETE_DATE >= TRUNC(SYSDATE) ");
		SQL.append("                      			OR WF.COMPLETE_DATE IS NULL ");
		SQL.append("                    		 ) ");
				SQL.append("                 AND WF.ATID LIKE 'STI%' ");				
				if(fixrole){
					SQL.append(" AND TEMP.ROLE_ID = ? ");	
					valueListM.setString(++index, wfRole);
					if(ORIGLogic.RoleICDC(searchRole)){
						SQL.append("  AND TEMP.PTID = ? ");
						valueListM.setString(++index, WorkflowConstant.ProcessTemplate.KLOP002);
					}else{
						SQL.append("  AND TEMP.PTID IN (?,?) ");
						valueListM.setString(++index,  WorkflowConstant.ProcessTemplate.KLOP001);
						valueListM.setString(++index,  WorkflowConstant.ProcessTemplate.KLOP003);
					}
				}
		SQL.append("                         GROUP BY ");
		SQL.append("                             WF.JOB_ID ");
		SQL.append("                     ) ");
		SQL.append(" 	AND WF_HIS.ATID LIKE 'STI%' ");
		if(fixrole){
			SQL.append(" AND WF_ACTIVITY_TEMPLATE.ROLE_ID = ? ");	
			valueListM.setString(++index, wfRole);
			if(ORIGLogic.RoleICDC(searchRole)){
				SQL.append("  AND WF_ACTIVITY_TEMPLATE.PTID = ? ");
				valueListM.setString(++index, WorkflowConstant.ProcessTemplate.KLOP002);
			}else{
				SQL.append("  AND WF_ACTIVITY_TEMPLATE.PTID IN (?,?) ");
				valueListM.setString(++index,  WorkflowConstant.ProcessTemplate.KLOP001);
				valueListM.setString(++index,  WorkflowConstant.ProcessTemplate.KLOP003);
			}
		}
		SQL.append("             ) ");
		SQL.append("         GROUP BY ");
		SQL.append("             OWNER ");
		SQL.append("     ) ");
		SQL.append("     PREV_JOB , ");
		SQL.append("     ( ");
		SQL.append("         SELECT ");
		SQL.append("             OWNER, ");
		SQL.append("             COUNT(DISTINCT JOB_ID) INPUT ");
		SQL.append("         FROM ");
		SQL.append("             WF_INSTANT_HISTORY HIS , ");
		SQL.append("             WF_ACTIVITY_TEMPLATE ACT ");
		SQL.append("         WHERE ");
		SQL.append("             HIS.CREATE_DATE BETWEEN TRUNC(SYSDATE) AND TRUNC(SYSDATE) + .99999 ");
		SQL.append("         AND HIS.ATID LIKE 'STI%' ");
		SQL.append("         AND HIS.ATID = ACT.ATID ");
		SQL.append("         AND ACTION <> 'Unblock' ");

		if(fixrole){
			SQL.append(" AND ACT.ROLE_ID = ? ");	
			valueListM.setString(++index, wfRole);
			if(ORIGLogic.RoleICDC(searchRole)){
				SQL.append("  AND ACT.PTID = ? ");
				valueListM.setString(++index, WorkflowConstant.ProcessTemplate.KLOP002);
			}else{
				SQL.append("  AND ACT.PTID IN (?,?) ");
				valueListM.setString(++index,  WorkflowConstant.ProcessTemplate.KLOP001);
				valueListM.setString(++index,  WorkflowConstant.ProcessTemplate.KLOP003);
			}
		}
		
		
		SQL.append("         AND JOB_ID NOT IN ");
		SQL.append("             ( ");
		SQL.append("                 SELECT ");
		SQL.append("                     JOB_ID ");
		SQL.append("                 FROM ");
		SQL.append("                     WF_INSTANT_HISTORY WF_HIS , ");
		SQL.append("                     WF_ACTIVITY_TEMPLATE ");
		SQL.append("                 WHERE ");
		SQL.append("                     WF_HIS.ATID = WF_ACTIVITY_TEMPLATE.ATID ");
		SQL.append("                 AND WF_HIS.ATID LIKE 'STI%' ");
		SQL.append("                 AND WF_HIS.CREATE_DATE < TRUNC(SYSDATE) ");
		SQL.append("                 AND ");
		SQL.append("                     ( ");
		SQL.append("                         WF_HIS.COMPLETE_DATE >= TRUNC(SYSDATE) ");
		SQL.append("                      OR WF_HIS.COMPLETE_DATE IS NULL ");
		SQL.append("                     ) ");
		SQL.append("                 AND WF_ACTIVITY_TEMPLATE.ROLE_ID = WF_ACTIVITY_TEMPLATE.ROLE_ID ");
		SQL.append("                 AND WF_HIS.OWNER = HIS.OWNER ");
		SQL.append("                 AND WF_HIS.SEQ = ");
		SQL.append("                     ( ");
		SQL.append("                         SELECT ");
		SQL.append("                             MAX(WF.SEQ) ");
		SQL.append("                         FROM ");
		SQL.append("                             WF_INSTANT_HISTORY WF, ");
		SQL.append("                             WF_ACTIVITY_TEMPLATE TEMP ");
		SQL.append("                         WHERE ");
		SQL.append("                             WF.ATID = TEMP.ATID ");
		SQL.append("                         AND WF.JOB_ID = WF_HIS.JOB_ID ");
		SQL.append("                         AND TEMP.ROLE_ID = WF_ACTIVITY_TEMPLATE.ROLE_ID ");
		SQL.append("                         AND WF.CREATE_DATE < TRUNC(SYSDATE) ");
		SQL.append("                         GROUP BY ");
		SQL.append("                             WF.JOB_ID ");
		SQL.append("                     ) ");
		SQL.append("             ) ");
		SQL.append("         GROUP BY ");
		SQL.append("             OWNER ");
		SQL.append("     ) ");
		SQL.append("     INPUT , ");
		SQL.append("     ( ");
		SQL.append("         SELECT ");
		SQL.append("             WF_HIS.OWNER , ");
		SQL.append("             NVL(COUNT(WF_HIS.JOB_ID),0) OUTPUT ");
		SQL.append("         FROM ");
		SQL.append("             WF_INSTANT_HISTORY WF_HIS , ");
		SQL.append("             WF_ACTIVITY_TEMPLATE ACT ");
		SQL.append("         WHERE ");
		SQL.append("             WF_HIS.ATID = ACT.ATID ");
		
		if(fixrole){
			SQL.append(" AND ACT.ROLE_ID = ? ");	
			valueListM.setString(++index, wfRole);
			if(ORIGLogic.RoleICDC(searchRole)){
				SQL.append("  AND ACT.PTID = ? ");
				valueListM.setString(++index, WorkflowConstant.ProcessTemplate.KLOP002);
			}else{
				SQL.append("  AND ACT.PTID IN (?,?) ");
				valueListM.setString(++index,  WorkflowConstant.ProcessTemplate.KLOP001);
				valueListM.setString(++index,  WorkflowConstant.ProcessTemplate.KLOP003);
			}
		}
		
//		#Septemwi Comment count output count unblock
//		SQL.append("         AND WF_HIS.ACTION <> 'Unblock' ");
		
		SQL.append("         AND ");
		SQL.append("             ( ");
		SQL.append("                 WF_HIS.ATID LIKE 'STI%' ");
		SQL.append("              OR WF_HIS.ATID IN ('STC0401','STC0402','STC0403','STC0411') ");
		SQL.append("             ) ");
		SQL.append("         AND WF_HIS.COMPLETE_DATE BETWEEN TRUNC(SYSDATE) AND TRUNC(SYSDATE) + .99999 ");
		SQL.append("         AND WF_HIS.SEQ = ");
		SQL.append("             ( ");
		SQL.append("                 SELECT ");
		SQL.append("                     MAX(WF.SEQ) ");
		SQL.append("                 FROM ");
		SQL.append("                     WF_INSTANT_HISTORY WF , ");
		SQL.append("                     WF_ACTIVITY_TEMPLATE TEMP ");
		SQL.append("                 WHERE ");
		SQL.append("                     WF.ATID = TEMP.ATID ");
		SQL.append("                 AND WF.JOB_ID =WF_HIS.JOB_ID ");
		SQL.append("                 AND TEMP.ROLE_ID =ACT.ROLE_ID ");
		SQL.append("                 AND WF.OWNER = WF_HIS.OWNER ");
		SQL.append("                 GROUP BY ");
		SQL.append("                     WF.JOB_ID ");
		SQL.append("             ) ");
		SQL.append("         GROUP BY ");
		SQL.append("             WF_HIS.OWNER ");
		SQL.append("     ) ");
		SQL.append("     OUTPUT , ");
		SQL.append("     ( ");
		SQL.append("         SELECT ");
		SQL.append("             WF.OWNER , ");
		SQL.append("             COUNT(WF.JOB_ID) BLOCKED ");
		SQL.append("         FROM ");
		SQL.append("             WF_INSTANT_HISTORY WF, ");
		SQL.append("             WF_ACTIVITY_TEMPLATE TEMP, ");
		SQL.append("             ( ");
		SQL.append("                 SELECT ");
		SQL.append("                     JOB_ID, ");
		SQL.append("                     FROM_ATID, ");
		SQL.append("                     SEQ ");
		SQL.append("                 FROM ");
		SQL.append("                     WF_INSTANT_HISTORY WF_HIS ");
		SQL.append("                 WHERE ");
		SQL.append("                     SEQ = ");
		SQL.append("                     ( ");
		SQL.append("                         SELECT ");
		SQL.append("                             MAX(SEQ) ");
		SQL.append("                         FROM ");
		SQL.append("                             WF_INSTANT_HISTORY ");
		SQL.append("                         WHERE ");
		SQL.append("                             JOB_ID = WF_HIS.JOB_ID ");
		SQL.append("                     ) ");
		SQL.append("                 AND ACTION LIKE '%Block%' ");
		SQL.append("                 AND SUBSTR(WF_HIS.ATID,1,7) = FROM_ATID ");
		SQL.append("                 AND ");
		SQL.append("                     ( ");
		SQL.append("                         WF_HIS.FROM_ATID NOT LIKE 'STC%' ");
		SQL.append("                     AND WF_HIS.FROM_ATID NOT LIKE 'STA%' ");
		SQL.append("                     ) ");
		SQL.append("                 AND ");
		SQL.append("                     ( ");
		SQL.append("                         WF_HIS.ATID NOT LIKE 'STC%' ");
		SQL.append("                     AND WF_HIS.ATID NOT LIKE 'STA%' ");
		SQL.append("                     ) ");
		SQL.append("             ) ");
		SQL.append("             BLOCKER ");
		SQL.append("         WHERE ");
		SQL.append("             WF.ATID = TEMP.ATID ");
		
		if(fixrole){
			SQL.append(" AND TEMP.ROLE_ID = ? ");	
			valueListM.setString(++index, wfRole);
			if(ORIGLogic.RoleICDC(searchRole)){
				SQL.append("  AND TEMP.PTID = ? ");
				valueListM.setString(++index, WorkflowConstant.ProcessTemplate.KLOP002);
			}else{
				SQL.append("  AND TEMP.PTID IN (?,?) ");
				valueListM.setString(++index,  WorkflowConstant.ProcessTemplate.KLOP001);
				valueListM.setString(++index,  WorkflowConstant.ProcessTemplate.KLOP003);
			}
		}
		
		SQL.append("         AND WF.JOB_ID = BLOCKER.JOB_ID ");
		SQL.append("         AND WF.ATID = BLOCKER.FROM_ATID ");
		SQL.append("         AND WF.SEQ = (BLOCKER.SEQ - 1) ");
		SQL.append("         GROUP BY ");
		SQL.append("             OWNER ");
		SQL.append("     ) ");
		SQL.append("     BLOCKER , ");
		SQL.append("     ( ");
		SQL.append("         SELECT ");
		SQL.append("             WF.OWNER , ");
		SQL.append("             COUNT(WF.JOB_ID) PREV_BLOCKED ");
		SQL.append("         FROM ");
		SQL.append("             WF_INSTANT_HISTORY WF, ");
		SQL.append("             WF_ACTIVITY_TEMPLATE TEMP, ");
		SQL.append("             ( ");
		SQL.append("                 SELECT ");
		SQL.append("                     JOB_ID, ");
		SQL.append("                     FROM_ATID, ");
		SQL.append("                     SEQ ");
		SQL.append("                 FROM ");
		SQL.append("                     WF_INSTANT_HISTORY WF_HIS ");
		SQL.append("                 WHERE ");
		SQL.append("                     SEQ = ");
		SQL.append("                     ( ");
		SQL.append("                         SELECT ");
		SQL.append("                             MAX(SEQ) ");
		SQL.append("                         FROM ");
		SQL.append("                             WF_INSTANT_HISTORY ");
		SQL.append("                         WHERE ");
		SQL.append("                             JOB_ID = WF_HIS.JOB_ID ");
		SQL.append("                         AND WF_INSTANT_HISTORY.CREATE_DATE < TRUNC(SYSDATE) ");
		SQL.append("                     ) ");
		SQL.append("                 AND ACTION LIKE '%Block%' ");
		SQL.append("                 AND SUBSTR(WF_HIS.ATID,1,7) = FROM_ATID ");
		SQL.append("                 AND ");
		SQL.append("                     ( ");
		SQL.append("                         WF_HIS.FROM_ATID NOT LIKE 'STC%' ");
		SQL.append("                     AND WF_HIS.FROM_ATID NOT LIKE 'STA%' ");
		SQL.append("                     ) ");
		SQL.append("                 AND ");
		SQL.append("                     ( ");
		SQL.append("                         WF_HIS.ATID NOT LIKE 'STC%' ");
		SQL.append("                     AND WF_HIS.ATID NOT LIKE 'STA%' ");
		SQL.append("                     ) ");
		SQL.append("                 AND WF_HIS.CREATE_DATE < TRUNC(SYSDATE) ");
		SQL.append("             ) ");
		SQL.append("             BLOCKER ");
		SQL.append("         WHERE ");
		SQL.append("             WF.ATID = TEMP.ATID ");

		if(fixrole){
			SQL.append(" AND TEMP.ROLE_ID = ? ");	
			valueListM.setString(++index, wfRole);
			if(ORIGLogic.RoleICDC(searchRole)){
				SQL.append("  AND TEMP.PTID = ? ");
				valueListM.setString(++index, WorkflowConstant.ProcessTemplate.KLOP002);
			}else{
				SQL.append("  AND TEMP.PTID IN (?,?) ");
				valueListM.setString(++index,  WorkflowConstant.ProcessTemplate.KLOP001);
				valueListM.setString(++index,  WorkflowConstant.ProcessTemplate.KLOP003);
			}
		}
		
		SQL.append("         AND WF.JOB_ID = BLOCKER.JOB_ID ");
		SQL.append("         AND WF.ATID = BLOCKER.FROM_ATID ");
		SQL.append("         AND WF.SEQ = (BLOCKER.SEQ - 1) ");
		SQL.append("         GROUP BY ");
		SQL.append("             OWNER ");
		SQL.append("     ) ");
		SQL.append("     PREV_BLOCKER , ");
		SQL.append("     ( ");
		SQL.append("         SELECT ");
		SQL.append("             WF.OWNER , ");
		SQL.append("             COUNT(DISTINCT WF.JOB_ID) REASSIGN ");
		SQL.append("         FROM ");
		SQL.append("             WF_INSTANT_HISTORY WF, ");
		SQL.append("             WF_ACTIVITY_TEMPLATE TEMP, ");
		SQL.append("             ( ");
		SQL.append("                 SELECT DISTINCT ");
		SQL.append("                     T.ROLE_ID, ");
		SQL.append("                     JOB_ID, ");
		SQL.append("                     FROM_ATID, ");
		SQL.append("                     SEQ ");
		SQL.append("                 FROM ");
		SQL.append("                     WF_INSTANT_HISTORY WF_HIS, ");
		SQL.append("                     WF_ACTIVITY_TEMPLATE T ");
		SQL.append("                 WHERE ");
		SQL.append("                     WF_HIS.ATID = T.ATID ");
		
		if(fixrole){
			SQL.append(" AND T.ROLE_ID = ? ");	
			valueListM.setString(++index, wfRole);
			if(ORIGLogic.RoleICDC(searchRole)){
				SQL.append("  AND T.PTID = ? ");
				valueListM.setString(++index, WorkflowConstant.ProcessTemplate.KLOP002);
			}else{
				SQL.append("  AND T.PTID IN (?,?) ");
				valueListM.setString(++index,  WorkflowConstant.ProcessTemplate.KLOP001);
				valueListM.setString(++index,  WorkflowConstant.ProcessTemplate.KLOP003);
			}
		}
		
		SQL.append("                 AND ACTION IN('Reassign','Reallocate') ");
		SQL.append("                 AND WF_HIS.CREATE_DATE BETWEEN TRUNC(SYSDATE) AND TRUNC(SYSDATE) +0.99999 ");
		SQL.append("             ) ");
		SQL.append("             REASSING ");
		SQL.append("         WHERE ");
		SQL.append("             WF.ATID = TEMP.ATID ");
		
		if(fixrole){
			SQL.append(" AND TEMP.ROLE_ID = ? ");	
			valueListM.setString(++index, wfRole);
			if(ORIGLogic.RoleICDC(searchRole)){
				SQL.append("  AND TEMP.PTID = ? ");
				valueListM.setString(++index, WorkflowConstant.ProcessTemplate.KLOP002);
			}else{
				SQL.append("  AND TEMP.PTID IN (?,?) ");
				valueListM.setString(++index,  WorkflowConstant.ProcessTemplate.KLOP001);
				valueListM.setString(++index,  WorkflowConstant.ProcessTemplate.KLOP003);
			}
		}
		
		SQL.append("         AND WF.JOB_ID = REASSING.JOB_ID ");
		SQL.append("         AND WF.ATID = REASSING.FROM_ATID ");
		SQL.append("         AND WF.SEQ = (REASSING.SEQ - 1) ");
		SQL.append("         AND NOT EXISTS ");
		SQL.append("             ( ");
		SQL.append("                 SELECT ");
		SQL.append("                     1 ");
		SQL.append("                 FROM ");
		SQL.append("                     WF_INSTANT_HISTORY TMP_WF, ");
		SQL.append("                     WF_ACTIVITY_TEMPLATE TEMP, ");
		SQL.append("                     ( ");
		SQL.append("                         SELECT ");
		SQL.append("                             JOB_ID, ");
		SQL.append("                             FROM_ATID, ");
		SQL.append("                             SEQ ");
		SQL.append("                         FROM ");
		SQL.append("                             WF_INSTANT_HISTORY WF_HIS ");
		SQL.append("                         WHERE ");
		SQL.append("                             SEQ = ");
		SQL.append("                             ( ");
		SQL.append("                                 SELECT ");
		SQL.append("                                     MAX(SEQ) ");
		SQL.append("                                 FROM ");
		SQL.append("                                     WF_INSTANT_HISTORY ");
		SQL.append("                                 WHERE ");
		SQL.append("                                     JOB_ID = WF_HIS.JOB_ID ");
		SQL.append("                             ) ");
		SQL.append("                         AND ACTION LIKE '%Block%' ");
		SQL.append("                         AND SUBSTR(WF_HIS.ATID,1,7) = FROM_ATID ");
		SQL.append("                         AND ");
		SQL.append("                             ( ");
		SQL.append("                                 WF_HIS.FROM_ATID NOT LIKE 'STC%' ");
		SQL.append("                             AND WF_HIS.FROM_ATID NOT LIKE 'STA%' ");
		SQL.append("                             ) ");
		SQL.append("                         AND ");
		SQL.append("                             ( ");
		SQL.append("                                 WF_HIS.ATID NOT LIKE 'STC%' ");
		SQL.append("                             AND WF_HIS.ATID NOT LIKE 'STA%' ");
		SQL.append("                             ) ");
		SQL.append("                     ) ");
		SQL.append("                     BLOCKER ");
		SQL.append("                 WHERE ");
		SQL.append("                     TMP_WF.ATID = TEMP.ATID ");

		if(fixrole){
			SQL.append(" AND TEMP.ROLE_ID = ? ");	
			valueListM.setString(++index, wfRole);
			if(ORIGLogic.RoleICDC(searchRole)){
				SQL.append("  AND TEMP.PTID = ? ");
				valueListM.setString(++index, WorkflowConstant.ProcessTemplate.KLOP002);
			}else{
				SQL.append("  AND TEMP.PTID IN (?,?) ");
				valueListM.setString(++index,  WorkflowConstant.ProcessTemplate.KLOP001);
				valueListM.setString(++index,  WorkflowConstant.ProcessTemplate.KLOP003);
			}
		}
		
		SQL.append("                 AND TMP_WF.JOB_ID = BLOCKER.JOB_ID ");
		SQL.append("                 AND TMP_WF.ATID = BLOCKER.FROM_ATID ");
		SQL.append("                 AND TMP_WF.SEQ = (BLOCKER.SEQ - 1) ");
		SQL.append("                 AND TMP_WF.JOB_ID = WF.JOB_ID ");
		SQL.append("                 AND TMP_WF.OWNER = WF.OWNER ");
		SQL.append("             ) ");
		SQL.append("         AND NOT EXISTS ");
		SQL.append("             ( ");
		SQL.append("                 SELECT ");
		SQL.append("                     JOB_ID, ");
		SQL.append("                     OWNER ");
		SQL.append("                 FROM ");
		SQL.append("                     WF_WORK_QUEUE WQ, ");
		SQL.append("                     USER_ROLE_JOIN_ROLE UR ");
		SQL.append("                 WHERE ");
		SQL.append("                     UR.USER_NAME = WQ.OWNER ");
		
		if(fixrole){		
			SQL.append("                 AND UR.ROLE_NAME = ? ");
			valueListM.setString(++index, searchRole);
		}
		
		SQL.append("                 AND UR.USER_NAME = WF.OWNER ");
		SQL.append("                 AND WQ.JOB_ID = WF.JOB_ID ");
		SQL.append("             ) ");
		SQL.append("         AND NOT EXISTS ");
		SQL.append("             ( ");
		SQL.append("                 SELECT ");
		SQL.append("                     1 ");
		SQL.append("                 FROM ");
		SQL.append("                     ORIG_APPLICATION , ");
		SQL.append("                     WF_JOBID_MAPPING ");
		SQL.append("                 WHERE ");
		SQL.append("                     ORIG_APPLICATION.FINAL_APP_DECISION_DATE BETWEEN TRUNC(SYSDATE) AND TRUNC( ");
		SQL.append("                     SYSDATE)+0.99999 ");
		SQL.append("                 AND WF_JOBID_MAPPING.APPLICATION_RECORD_ID = ORIG_APPLICATION.APPLICATION_RECORD_ID ");
		SQL.append("                 AND WF_JOBID_MAPPING.JOB_ID = WF.JOB_ID ");
		SQL.append("                 AND WF_JOBID_MAPPING.JOB_STATUS='ACTIVE' ");
		SQL.append("                 AND ORIG_APPLICATION.FINAL_APP_DECISION_BY = WF.OWNER ");
		SQL.append("                 AND JOB_STATE IN ");
		SQL.append("                     ( ");
		SQL.append("                         SELECT ");
		SQL.append("                             SUBSTR(CSV, INSTR(CSV,',',1,LEV) + 1, INSTR(CSV,',',1,LEV+1 )-INSTR(CSV ");
		SQL.append("                             ,',',1,LEV)-1) ");
		SQL.append("                         FROM ");
		SQL.append("                             ( ");
		SQL.append("                                 SELECT ");
		SQL.append("                                     ','||PARAM_VALUE||',' CSV ");
		SQL.append("                                 FROM ");
		SQL.append("                                     GENERAL_PARAM ");
		SQL.append("                                 WHERE ");
		SQL.append("                                     PARAM_CODE = 'JOBSTATE_CANCEL' ");
		SQL.append("                             ) ");
		SQL.append("                             , ");
		SQL.append("                             ( ");
		SQL.append("                                 SELECT ");
		SQL.append("                                     LEVEL LEV ");
		SQL.append("                                 FROM ");
		SQL.append("                                     DUAL CONNECT BY LEVEL <= 100 ");
		SQL.append("                             ) ");
		SQL.append("                         WHERE ");
		SQL.append("                             LEV <= LENGTH(CSV)-LENGTH(REPLACE(CSV,','))-1 ");
		SQL.append("                     ) ");
		SQL.append("             ) ");
		SQL.append("         AND EXISTS ");
		SQL.append("             ( ");
		SQL.append("                 SELECT ");
		SQL.append("                     1 ");
		SQL.append("                 FROM ");
		SQL.append("                     WF_INSTANT_HISTORY ");
		SQL.append("                 WHERE ");
		SQL.append("                     JOB_ID = WF.JOB_ID ");
		SQL.append("                 AND ACTION IN('Reassign','Reallocate') ");
		SQL.append("                 AND SEQ = ");
		SQL.append("                     ( ");
		SQL.append("                         SELECT ");
		SQL.append("                             MAX(SEQ) +1 ");
		SQL.append("                         FROM ");
		SQL.append("                             WF_INSTANT_HISTORY ");
		SQL.append("                         WHERE ");
		SQL.append("                             JOB_ID = WF.JOB_ID ");
		SQL.append("                         AND OWNER = WF.OWNER ");
		SQL.append("                     ) ");
		SQL.append("             ) ");
		SQL.append("         GROUP BY ");
		SQL.append("             OWNER ");
		SQL.append("     ) ");
		SQL.append("     REASSIGN , ");
		SQL.append("     ( ");
		SQL.append("         SELECT ");
		SQL.append("             ORIG_APPLICATION.FINAL_APP_DECISION_BY , ");
		SQL.append("             COUNT(1) CANCELED ");
		SQL.append("         FROM ");
		SQL.append("             ORIG_APPLICATION ");
		SQL.append("         WHERE ");
		SQL.append("             ORIG_APPLICATION.FINAL_APP_DECISION_DATE BETWEEN TRUNC(SYSDATE) AND TRUNC(SYSDATE)+ ");
		SQL.append("             0.99999 ");
		SQL.append("         AND JOB_STATE IN ");
		SQL.append("             ( ");
		SQL.append("                 SELECT ");
		SQL.append("                     SUBSTR(CSV, INSTR(CSV,',',1,LEV) + 1, INSTR(CSV,',',1,LEV+1 )-INSTR(CSV,',',1, ");
		SQL.append("                     LEV)-1) ");
		SQL.append("                 FROM ");
		SQL.append("                     ( ");
		SQL.append("                         SELECT ");
		SQL.append("                             ','||PARAM_VALUE||',' CSV ");
		SQL.append("                         FROM ");
		SQL.append("                             GENERAL_PARAM ");
		SQL.append("                         WHERE ");
		SQL.append("                             PARAM_CODE = 'JOBSTATE_CANCEL' ");
		SQL.append("                     ) ");
		SQL.append("                     , ");
		SQL.append("                     ( ");
		SQL.append("                         SELECT ");
		SQL.append("                             LEVEL LEV ");
		SQL.append("                         FROM ");
		SQL.append("                             DUAL CONNECT BY LEVEL <= 100 ");
		SQL.append("                     ) ");
		SQL.append("                 WHERE ");
		SQL.append("                     LEV <= LENGTH(CSV)-LENGTH(REPLACE(CSV,','))-1 ");
		SQL.append("             ) ");
		SQL.append("         GROUP BY ");
		SQL.append("             ORIG_APPLICATION.FINAL_APP_DECISION_BY ");
		SQL.append("     ) ");
		SQL.append("     CANCEL , ");
		SQL.append("     ( ");
		SQL.append("         SELECT ");
		SQL.append("             WF.OWNER , ");
		SQL.append("             COUNT( WF.JOB_ID) SEND_BACK ");
		SQL.append("         FROM ");
		SQL.append("             WF_INSTANT_HISTORY WF, ");
		SQL.append("             WF_ACTIVITY_TEMPLATE TEMP, ");
		SQL.append("             ( ");
		SQL.append("                 SELECT ");
		SQL.append("                     T.ROLE_ID, ");
		SQL.append("                     JOB_ID, ");
		SQL.append("                     FROM_ATID, ");
		SQL.append("                     SEQ ");
		SQL.append("                 FROM ");
		SQL.append("                     WF_INSTANT_HISTORY WF_HIS, ");
		SQL.append("                     WF_ACTIVITY_TEMPLATE T ");
		SQL.append("                 WHERE ");
		SQL.append("                     WF_HIS.ATID = T.ATID ");
		SQL.append("                 AND SEQ = ");
		SQL.append("                     ( ");
		SQL.append("                         SELECT ");
		SQL.append("                             MAX(SEQ) ");
		SQL.append("                         FROM ");
		SQL.append("                             WF_INSTANT_HISTORY T1, ");
		SQL.append("                             WF_ACTIVITY_TEMPLATE T2 ");
		SQL.append("                         WHERE ");
		SQL.append("                             T1.JOB_ID = WF_HIS.JOB_ID ");
		SQL.append("                         AND T1.ATID = T2.ATID ");
		SQL.append("                         AND T2.ROLE_ID = T.ROLE_ID ");
		SQL.append("                         AND ");
		SQL.append("                             ( ");
		SQL.append("                                 PROCESS_STATE IN ( 'SEND_BACKX','SEND_BACK') ");
		SQL.append("                              OR ");
		SQL.append("                                 ( ");
		SQL.append("                                     ACTION LIKE 'Send back to % Block' ");
		SQL.append("                                 AND PROCESS_STATE NOT IN ('SEND_M', 'SENDX_M','SENDX') ");
		SQL.append("                                 ) ");
		SQL.append("                             ) ");
		SQL.append("                         AND WF_HIS.CREATE_DATE BETWEEN TRUNC(SYSDATE) AND TRUNC(SYSDATE) +0.99999 ");
		SQL.append("                     ) ");
		SQL.append("             ) ");
		SQL.append("             SEND_BACK ");
		SQL.append("         WHERE ");
		SQL.append("             WF.ATID = TEMP.ATID ");
		
		if(fixrole){
			SQL.append(" AND TEMP.ROLE_ID = ? ");	
			valueListM.setString(++index, wfRole);
			if(ORIGLogic.RoleICDC(searchRole)){
				SQL.append("  AND TEMP.PTID = ? ");
				valueListM.setString(++index, WorkflowConstant.ProcessTemplate.KLOP002);
			}else{
				SQL.append("  AND TEMP.PTID IN (?,?) ");
				valueListM.setString(++index,  WorkflowConstant.ProcessTemplate.KLOP001);
				valueListM.setString(++index,  WorkflowConstant.ProcessTemplate.KLOP003);
			}
		}
		
		SQL.append("         AND WF.JOB_ID = SEND_BACK.JOB_ID ");
		SQL.append("         AND WF.ATID = SEND_BACK.FROM_ATID ");
		SQL.append("         AND WF.SEQ = (SEND_BACK.SEQ - 1) ");
		SQL.append("         AND NOT EXISTS ");
		SQL.append("             ( ");
		SQL.append("                 SELECT ");
		SQL.append("                     JOB_ID, ");
		SQL.append("                     OWNER ");
		SQL.append("                 FROM ");
		SQL.append("                     WF_WORK_QUEUE WQ, ");
		SQL.append("                     USER_ROLE_JOIN_ROLE UR ");
		SQL.append("                 WHERE ");
		SQL.append("                     UR.USER_NAME = WQ.OWNER ");
		
		if(fixrole){	
			SQL.append("                 AND UR.ROLE_NAME = ? ");
			valueListM.setString(++index, searchRole);
		}
		
		SQL.append("                 AND UR.USER_NAME = WF.OWNER ");
		SQL.append("                 AND WQ.JOB_ID = WF.JOB_ID ");
		SQL.append("             ) ");
		SQL.append("         GROUP BY ");
		SQL.append("             OWNER ");
		SQL.append("     ) ");
		SQL.append("     SEND_BACK ");
		SQL.append(" WHERE ");
		SQL.append("     UR.ROLE_ID = RO.ROLE_ID ");
		
		if(fixrole){		
			SQL.append("     AND RO.ROLE_NAME = ? ");
			valueListM.setString(++index, searchRole);			
		}else{
			SQL.append(SQLIN(searchDataM, vRole, valueListM,"RO.ROLE_NAME", TYPE_SEARCH_ROLE));
		}
		
		SQL.append(" AND US.USER_NAME = UR.USER_NAME ");
		SQL.append(" AND US.ACTIVE_STATUS = ? ");
		SQL.append(" AND US.USER_NAME = PREV_JOB.OWNER(+) ");
		SQL.append(" AND US.USER_NAME = INPUT.OWNER(+) ");
		SQL.append(" AND US.USER_NAME = OUTPUT.OWNER(+) ");
		SQL.append(" AND US.USER_NAME = BLOCKER.OWNER(+) ");
		SQL.append(" AND US.USER_NAME = REASSIGN.OWNER(+) ");
		SQL.append(" AND US.USER_NAME = CANCEL.FINAL_APP_DECISION_BY(+) ");
		SQL.append(" AND US.USER_NAME = SEND_BACK.OWNER(+) ");
		SQL.append(" AND US.USER_NAME = PREV_BLOCKER.OWNER(+) ");
		
		valueListM.setString(++index, OrigConstant.ACTIVE_FLAG);			
		
		if(!OrigUtil.isEmptyString(searchDataM.getEmpFirstName())){
			SQL.append(" AND US.THAI_FIRSTNAME LIKE ? ");
			valueListM.setString(++index, searchDataM.getEmpFirstName()+"%");
		}
		
		if(!OrigUtil.isEmptyString(searchDataM.getEmpLastName())){
			SQL.append(" AND US.THAI_LASTNAME LIKE ? ");
			valueListM.setString(++index, searchDataM.getEmpLastName()+"%");
		}

		if(OrigConstant.FLAG_Y.equals(searchDataM.getLogOn())){
			SQL.append(" AND US.LOGON_FLAG = ? ");
			valueListM.setString(++index,OrigConstant.FLAG_Y);
		}else if(OrigConstant.FLAG_N.equals(searchDataM.getLogOn())){
			SQL.append(" AND (US.LOGON_FLAG = ? OR US.LOGON_FLAG IS NULL)");
			valueListM.setString(++index,OrigConstant.FLAG_N);
		}
					
		if(!OrigUtil.isEmptyString(searchDataM.getOnJob())){
			SQL.append(" AND EXISTS ( ");
				SQL.append(" SELECT ");
				SQL.append("     'X' ");
				SQL.append(" FROM ");
				SQL.append("     USER_WORK_QUEUE, ");
				SQL.append("     WF_TODO_LIST_MASTER, ");
				SQL.append("     WF_TODO_LIST, ");
				SQL.append("     WF_ACTIVITY_TEMPLATE ");
				SQL.append(" WHERE ");
				SQL.append("     USER_WORK_QUEUE.TDID = WF_TODO_LIST_MASTER.TDID ");
				SQL.append(" AND WF_TODO_LIST_MASTER.TDID = WF_TODO_LIST.TDID ");
				SQL.append(" AND USER_WORK_QUEUE.TDID = WF_TODO_LIST.TDID ");
				SQL.append(" AND WF_TODO_LIST_MASTER.QUEUE_FLAG = 'Y' ");
				SQL.append(" AND WF_ACTIVITY_TEMPLATE.ATID = WF_TODO_LIST.ATID ");
				SQL.append(" AND USER_WORK_QUEUE.USER_NAME = US.USER_NAME ");
				SQL.append(" AND USER_WORK_QUEUE.ONJOB_FLAG = ? ");
				valueListM.setString(++index, searchDataM.getOnJob());
				if(fixrole){
					if(ORIGLogic.RoleICDC(searchRole)){
						SQL.append("  AND WF_ACTIVITY_TEMPLATE.PTID = ? ");
						valueListM.setString(++index, WorkflowConstant.ProcessTemplate.KLOP002);
					}else{
						SQL.append("  AND WF_ACTIVITY_TEMPLATE.PTID IN (?,?) ");
						valueListM.setString(++index,  WorkflowConstant.ProcessTemplate.KLOP001);
						valueListM.setString(++index,  WorkflowConstant.ProcessTemplate.KLOP003);
					}
				}
			SQL.append(" ) ");
		}else{
			SQL.append(" AND EXISTS ( ");
				SQL.append(" SELECT ");
				SQL.append("     'X' ");
				SQL.append(" FROM ");
				SQL.append("     USER_WORK_QUEUE, ");
				SQL.append("     WF_TODO_LIST_MASTER, ");
				SQL.append("     WF_TODO_LIST, ");
				SQL.append("     WF_ACTIVITY_TEMPLATE ");
				SQL.append(" WHERE ");
				SQL.append("     USER_WORK_QUEUE.TDID = WF_TODO_LIST_MASTER.TDID ");
				SQL.append(" AND WF_TODO_LIST_MASTER.TDID = WF_TODO_LIST.TDID ");
				SQL.append(" AND USER_WORK_QUEUE.TDID = WF_TODO_LIST.TDID ");
				SQL.append(" AND WF_TODO_LIST_MASTER.QUEUE_FLAG = 'Y' ");
				SQL.append(" AND WF_ACTIVITY_TEMPLATE.ATID = WF_TODO_LIST.ATID ");
				SQL.append(" AND USER_WORK_QUEUE.USER_NAME = US.USER_NAME ");
				if(fixrole){
					if(ORIGLogic.RoleICDC(searchRole)){
						SQL.append("  AND WF_ACTIVITY_TEMPLATE.PTID = ? ");
						valueListM.setString(++index, WorkflowConstant.ProcessTemplate.KLOP002);
					}else{
						SQL.append("  AND WF_ACTIVITY_TEMPLATE.PTID IN (?,?) ");
						valueListM.setString(++index,  WorkflowConstant.ProcessTemplate.KLOP001);
						valueListM.setString(++index,  WorkflowConstant.ProcessTemplate.KLOP003);
					}
				}
			SQL.append(" ) ");
		}
		
		if(!OrigUtil.isEmptyString(searchDataM.getEmpId())){
			SQL.append(" AND US.USER_NO = ? ");
			valueListM.setString(++index, searchDataM.getEmpId());
		}	
		
		SQL.append(" ORDER BY ");
		SQL.append(" US.THAI_FIRSTNAME, ");
		SQL.append(" US.THAI_LASTNAME ");
		
		log.debug("SQL >> "+SQL);
		
		valueListM.setSQL(SQL.toString());
		
	}
	
	public String SQLIN(SearchHandler.SearchDataM searchDataM,Vector<ORIGCacheDataM> vRole,ValueListM valueListM ,String SQLSTRING,String type){		
		StringBuilder SQL = new StringBuilder("");
		SQL.append(" AND ");
		SQL.append(SQLSTRING);
		if(null != vRole && vRole.size() > 0){
			SQL.append(" IN ( ");
			int i = 0;
			for(ORIGCacheDataM dataM : vRole){
				String role = dataM.getCode();
				if(TYPE_WFROLE.equals(type)){
					role = WebActionUtil.getRoleWf(role);
				}
				if(i != 0){
					SQL.append(",");
				}
				SQL.append("?");
				valueListM.setString(++index,role);
				i++;
			}	
			SQL.append(" ) ");
		}else{
			SQL.append(" = ? ");
			valueListM.setString(++index,"ROLE");
		}
		SQL.append(" ");
		return SQL.toString();
	}
	public Vector<ORIGCacheDataM> getRole(String current_role ,SearchHandler.SearchDataM searchDataM){
		Vector<ORIGCacheDataM> vRole = new Vector<ORIGCacheDataM>();
		if(ORIGLogic.superTrackingSearch(current_role)){		
			ORIGCacheUtil origCache = ORIGCacheUtil.getInstance();
			vRole = (Vector<ORIGCacheDataM>)origCache.getRoleTracking();
			if(!OrigUtil.isEmptyString(searchDataM.getGroupRole())){
				vRole = getRoleByGroup(vRole,searchDataM.getGroupRole());
			}
			if(!OrigUtil.isEmptyString(searchDataM.getRole())){
				vRole = getRole(vRole,searchDataM.getRole());
			}
		}
		return vRole;		
	}
	public Vector<ORIGCacheDataM> getRole(Vector<ORIGCacheDataM> vRole,String role){
		Vector<ORIGCacheDataM>  data = new Vector<ORIGCacheDataM>();
			for(ORIGCacheDataM dataM : vRole){
				if(null != role && role.equals(dataM.getCode())){
					data.add(dataM);
				}
			}
		return data;		
	}
	public Vector<ORIGCacheDataM> getRoleByGroup(Vector<ORIGCacheDataM> vRole,String tracking_group){
		Vector<ORIGCacheDataM>  data = new Vector<ORIGCacheDataM>();
			for(ORIGCacheDataM dataM : vRole){
				if(null != tracking_group && tracking_group.equals(dataM.getSystemID2())){
					data.add(dataM);
				}
			}
		return data;		
	}
}
