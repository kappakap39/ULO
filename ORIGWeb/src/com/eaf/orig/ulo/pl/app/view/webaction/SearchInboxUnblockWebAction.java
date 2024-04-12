package com.eaf.orig.ulo.pl.app.view.webaction;

import org.apache.log4j.Logger;
import com.eaf.j2ee.pattern.control.FrontController;
import com.eaf.j2ee.pattern.control.event.Event;
import com.eaf.j2ee.pattern.control.event.EventResponse;
import com.eaf.j2ee.pattern.view.webaction.WebAction;
import com.eaf.j2ee.pattern.view.webaction.WebActionHelper;
import com.eaf.orig.profile.model.UserDetailM;
import com.eaf.orig.shared.constant.OrigConstant;
import com.eaf.orig.shared.model.ValueListM;
import com.eaf.orig.ulo.pl.constant.WorkflowConstant;

public class SearchInboxUnblockWebAction extends WebActionHelper implements WebAction {
	
	static Logger log = Logger.getLogger(SearchInboxUnblockWebAction.class);
	
	private String nextAction = null;
	
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
		
		try{			
			ValueListM valueListM = new ValueListM();
				
			String current_role = userM.getCurrentRole();
			
			log.debug("current role >> "+current_role);
			
			int index = 0;
			
			if(OrigConstant.ROLE_DE_SUP.equals(current_role)){
				SQL_ROLE_DE(current_role, valueListM, index);
			}else if(OrigConstant.ROLE_DC_SUP.equals(current_role)){
				SQL_ROLE_DC(current_role, valueListM, index);
			}else if(OrigConstant.ROLE_I_SUP_DC.equals(current_role)){
				SQL_ROLE_I_SUP_DC(current_role, valueListM, index);
			}
			
			valueListM.setNextPage(false);
			valueListM.setItemsPerPage(20);
			
			valueListM.setReturnToAction("page=UNLOCK_SCREEN");
			getRequest().getSession().setAttribute("VALUE_LIST", valueListM);
			
			nextAction = "action=ValueListWebAction";				

		} catch (Exception e) {
			log.error("exception ", e);
		}
		return true;
	}
	
	public void SQL_ROLE_DE(String current_role,ValueListM valueListM, int index){
		StringBuilder SQL = new StringBuilder();
		
			SQL.append(" SELECT ");
			SQL.append(" P.IDNO , ");
			SQL.append(" MIN(A.UPDATE_DATE) UPDATE_DATE ");
			SQL.append(" FROM ");
			SQL.append("   ORIG_APPLICATION A , ");
			SQL.append("   ORIG_PERSONAL_INFO P ");
			SQL.append(" WHERE ");
			SQL.append("    A.APPLICATION_RECORD_ID = P.APPLICATION_RECORD_ID ");
			SQL.append(" AND A.BLOCK_FLAG = ? ");
			SQL.append(" AND P.PERSONAL_TYPE = ? ");
			
				valueListM.setString(++index, OrigConstant.BLOCK_FLAG);
				valueListM.setString(++index, OrigConstant.PERSONAL_TYPE_APPLICANT);
			
			SQL.append(" AND EXISTS ");
			SQL.append("     ( ");
			SQL.append("         SELECT ");
			SQL.append("             1 ");
			SQL.append("         FROM ");
			SQL.append("             ORIG_APPLICATION TA, ");
			SQL.append("             ORIG_PERSONAL_INFO AP, ");
			SQL.append("             WF_JOBID_MAPPING TJ, ");
			SQL.append("             WF_WORK_QUEUE TQ, ");
			SQL.append("             WF_ACTIVITY_TEMPLATE TT ");
			SQL.append("         WHERE ");
			SQL.append("             TA.APPLICATION_RECORD_ID = AP.APPLICATION_RECORD_ID ");
			SQL.append("         AND TA.BLOCK_FLAG = ? ");
			SQL.append("         AND AP.PERSONAL_TYPE = ? ");
			SQL.append("         AND TA.APPLICATION_RECORD_ID = TJ.APPLICATION_RECORD_ID ");
			SQL.append("         AND TJ.JOB_STATUS = ? ");
			SQL.append("         AND TJ.JOB_ID = TQ.JOB_ID ");
			SQL.append("         AND TQ.ATID = TT.ATID ");
			SQL.append("         AND TT.ROLE_ID IN(?,?) ");
			SQL.append("         AND P.IDNO = AP.IDNO ");
			SQL.append("     ) ");
				
				valueListM.setString(++index, OrigConstant.BLOCK_FLAG);
				valueListM.setString(++index, OrigConstant.PERSONAL_TYPE_APPLICANT);
				valueListM.setString(++index, WorkflowConstant.JobStatus.ACTIVE);
				valueListM.setString(++index, WorkflowConstant.WfRole.DE);
				valueListM.setString(++index, WorkflowConstant.WfRole.DE_SUP);
			
			SQL.append(" GROUP BY ");
			SQL.append("     IDNO ");
			SQL.append(" ORDER BY ");
			SQL.append("     UPDATE_DATE ");
			
		valueListM.setSQL(SQL.toString());
		
	}
	
	public void SQL_ROLE_DC(String current_role,ValueListM valueListM, int index){
		StringBuilder SQL = new StringBuilder();
		
			SQL.append(" SELECT ");
			SQL.append(" P.IDNO , ");
			SQL.append(" MIN(A.UPDATE_DATE) UPDATE_DATE ");
			SQL.append(" FROM ");
			SQL.append("   ORIG_APPLICATION A , ");
			SQL.append("   ORIG_PERSONAL_INFO P, ");
			SQL.append("   WF_JOBID_MAPPING J, ");
			SQL.append("   WF_WORK_QUEUE Q, ");
			SQL.append("   WF_ACTIVITY_TEMPLATE T ");
			SQL.append(" WHERE ");
			SQL.append("    A.APPLICATION_RECORD_ID = P.APPLICATION_RECORD_ID ");
			SQL.append(" AND A.BLOCK_FLAG = ? ");
			SQL.append(" AND P.PERSONAL_TYPE = ? ");
			
				valueListM.setString(++index, OrigConstant.BLOCK_FLAG);
				valueListM.setString(++index, OrigConstant.PERSONAL_TYPE_APPLICANT);
			
			SQL.append(" AND A.APPLICATION_RECORD_ID = J.APPLICATION_RECORD_ID ");
			SQL.append(" AND J.JOB_STATUS = ? ");
			SQL.append(" AND J.JOB_ID = Q.JOB_ID ");
			SQL.append(" AND Q.ATID = T.ATID ");
			SQL.append(" AND T.PTID <> ? ");
			
				valueListM.setString(++index, WorkflowConstant.JobStatus.ACTIVE);
				valueListM.setString(++index, WorkflowConstant.ProcessTemplate.KLOP002);
				
			SQL.append(" AND NOT EXISTS ");
			SQL.append("     ( ");
			SQL.append("         SELECT ");
			SQL.append("             1 ");
			SQL.append("         FROM ");
			SQL.append("             ORIG_APPLICATION TA, ");
			SQL.append("             ORIG_PERSONAL_INFO AP, ");
			SQL.append("             WF_JOBID_MAPPING TJ, ");
			SQL.append("             WF_WORK_QUEUE TQ, ");
			SQL.append("             WF_ACTIVITY_TEMPLATE TT ");
			SQL.append("         WHERE ");
			SQL.append("             TA.APPLICATION_RECORD_ID = AP.APPLICATION_RECORD_ID ");
			SQL.append("         AND TA.BLOCK_FLAG = ? ");
			SQL.append("         AND AP.PERSONAL_TYPE = ? ");
			SQL.append("         AND TA.APPLICATION_RECORD_ID = TJ.APPLICATION_RECORD_ID ");
			SQL.append("         AND TJ.JOB_STATUS = ? ");
			SQL.append("         AND TJ.JOB_ID = TQ.JOB_ID ");
			SQL.append("         AND TQ.ATID = TT.ATID ");
			SQL.append("         AND TT.ROLE_ID IN(?,?) ");			
			SQL.append("         AND P.IDNO = AP.IDNO ");
			SQL.append("     ) ");
				
				valueListM.setString(++index, OrigConstant.BLOCK_FLAG);
				valueListM.setString(++index, OrigConstant.PERSONAL_TYPE_APPLICANT);
				valueListM.setString(++index, WorkflowConstant.JobStatus.ACTIVE);
				valueListM.setString(++index, WorkflowConstant.WfRole.DE);
				valueListM.setString(++index, WorkflowConstant.WfRole.DE_SUP);
			
			SQL.append(" GROUP BY ");
			SQL.append("     IDNO ");
			SQL.append(" ORDER BY ");
			SQL.append("     UPDATE_DATE ");
			
		valueListM.setSQL(SQL.toString());
		
	}
	
	public void SQL_ROLE_I_SUP_DC(String current_role,ValueListM valueListM, int index){
		StringBuilder SQL = new StringBuilder();
		
			SQL.append(" SELECT ");
			SQL.append(" P.IDNO , ");
			SQL.append(" MIN(A.UPDATE_DATE) UPDATE_DATE ");
			SQL.append(" FROM ");
			SQL.append("   ORIG_APPLICATION A , ");
			SQL.append("   ORIG_PERSONAL_INFO P ");
			SQL.append(" WHERE ");
			SQL.append("    A.APPLICATION_RECORD_ID = P.APPLICATION_RECORD_ID ");
			SQL.append(" AND A.BLOCK_FLAG = ? ");
			SQL.append(" AND P.PERSONAL_TYPE = ? ");
			
				valueListM.setString(++index, OrigConstant.BLOCK_FLAG);
				valueListM.setString(++index, OrigConstant.PERSONAL_TYPE_APPLICANT);
			
			SQL.append(" AND NOT EXISTS ");
			SQL.append("     ( ");
			SQL.append("         SELECT ");
			SQL.append("             1 ");
			SQL.append("         FROM ");
			SQL.append("             ORIG_APPLICATION TA, ");
			SQL.append("             ORIG_PERSONAL_INFO AP, ");
			SQL.append("             WF_JOBID_MAPPING TJ, ");
			SQL.append("             WF_WORK_QUEUE TQ, ");
			SQL.append("             WF_ACTIVITY_TEMPLATE TT ");
			SQL.append("         WHERE ");
			SQL.append("             TA.APPLICATION_RECORD_ID = AP.APPLICATION_RECORD_ID ");
			SQL.append("         AND TA.BLOCK_FLAG = ? ");
			SQL.append("         AND AP.PERSONAL_TYPE = ? ");
			SQL.append("         AND TA.APPLICATION_RECORD_ID = TJ.APPLICATION_RECORD_ID ");
			SQL.append("         AND TJ.JOB_STATUS = ? ");
			SQL.append("         AND TJ.JOB_ID = TQ.JOB_ID ");
			SQL.append("         AND TQ.ATID = TT.ATID ");
			SQL.append("         AND ( TT.ROLE_ID IN(?,?) OR TT.PTID IN(?,?) ) ");
			SQL.append("         AND P.IDNO = AP.IDNO ");
			SQL.append("     ) ");
				
				valueListM.setString(++index, OrigConstant.BLOCK_FLAG);
				valueListM.setString(++index, OrigConstant.PERSONAL_TYPE_APPLICANT);
				valueListM.setString(++index, WorkflowConstant.JobStatus.ACTIVE);
				valueListM.setString(++index, WorkflowConstant.WfRole.DE);
				valueListM.setString(++index, WorkflowConstant.WfRole.DE_SUP);
				valueListM.setString(++index, WorkflowConstant.ProcessTemplate.KLOP001);
				valueListM.setString(++index, WorkflowConstant.ProcessTemplate.KLOP003);
			
			SQL.append(" GROUP BY ");
			SQL.append("     IDNO ");
			SQL.append(" ORDER BY ");
			SQL.append("     UPDATE_DATE ");
			
		valueListM.setSQL(SQL.toString());
		
	}
	
	@Override
	public int getNextActivityType() {
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
}
