package com.eaf.orig.ulo.pl.app.view.webaction;

import org.apache.log4j.Logger;
import com.eaf.j2ee.pattern.control.FrontController;
import com.eaf.j2ee.pattern.control.event.Event;
import com.eaf.j2ee.pattern.control.event.EventResponse;
import com.eaf.j2ee.pattern.view.webaction.WebAction;
import com.eaf.j2ee.pattern.view.webaction.WebActionHelper;
import com.eaf.orig.profile.model.UserDetailM;
import com.eaf.orig.shared.constant.OrigConstant;
import com.eaf.orig.ulo.pl.constant.WorkflowConstant;
import com.eaf.orig.ulo.pl.control.event.PLApplicationEvent;
import com.orig.bpm.ulo.model.WorkflowDataM;

public class ButtonOnJobWebAction extends WebActionHelper implements WebAction {
	
	static Logger log = Logger.getLogger(ButtonOnJobWebAction.class);
	WorkflowDataM workflowM = new WorkflowDataM();
	UserDetailM userM = new UserDetailM();

	@Override
	public Event toEvent() {
		PLApplicationEvent appEvent = new PLApplicationEvent();
		appEvent.setObject(workflowM);
		appEvent.setEventType(PLApplicationEvent.STATUS_ON_JOB);
		appEvent.setUserM(userM);
		return appEvent;
	}

	@Override
	public boolean requiredModelRequest() {
		// TODO Auto-generated method stub
		return true;
	}

	@Override
	public boolean processEventResponse(EventResponse response) {
		// TODO Auto-generated method stub
		return defaultProcessResponse(response);
	}

	@Override
	public boolean preModelRequest() {
		
		userM = (UserDetailM) getRequest().getSession().getAttribute("ORIGUser");
		workflowM.setStatus(getRequest().getParameter("status"));
		String role = userM.getCurrentRole();
		String tdid = null;
		if (role.equals(OrigConstant.ROLE_CA)) {
			tdid = WorkflowConstant.TODO_LIST_ID.CA_INBOX;
		}/* else if (role.equals(OrigConstant.ROLE_CA_SUP)) {
			tdid = WorkflowConstant.TODO_LIST_ID.CA_SUP_INBOX;
		}*/ else if (role.equals(OrigConstant.ROLE_CB)) {
			tdid = WorkflowConstant.TODO_LIST_ID.NCB_IQ_INBOX;
		} else if (role.equals(OrigConstant.ROLE_DC)) {
			tdid = WorkflowConstant.TODO_LIST_ID.DC_INBOX;
		} else if (role.equals(OrigConstant.ROLE_DC_SUP)) {
			tdid = WorkflowConstant.TODO_LIST_ID.DC_SUP_INBOX;
		} else if (role.equals(OrigConstant.ROLE_DE)) {
			tdid = WorkflowConstant.TODO_LIST_ID.DE_INBOX;
		} else if (role.equals(OrigConstant.ROLE_DE_SUP)) {
			tdid = WorkflowConstant.TODO_LIST_ID.DE_SUP_INBOX;
		} else if (role.equals(OrigConstant.ROLE_FU)) {
			tdid = WorkflowConstant.TODO_LIST_ID.DF_INBOX;
		}/* else if (role.equals(OrigConstant.ROLE_FU_SUP)) {
			tdid = WorkflowConstant.TODO_LIST_ID.DF_SUP_INBOX;
		}*/ else if (role.equals(OrigConstant.ROLE_VC)) {
			tdid = WorkflowConstant.TODO_LIST_ID.VC_INBOX;
		} else if (role.equals(OrigConstant.ROLE_VC_SUP)) {
			tdid = WorkflowConstant.TODO_LIST_ID.VC_SUP_INBOX;
		}
		workflowM.setTdID(tdid);
		
		/*getResponse().setContentType("text;charset=UTF-8");
		try {
			pw = getResponse().getWriter();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}		
		pw.write("");
		pw.close();*/
		
		return true;
	}

	@Override
	public int getNextActivityType() {
		// TODO Auto-generated method stub
		return FrontController.NOTFORWARD;
	}
	
	@Override
	public String getNextActionParameter() {
		
		return null;
	}

	@Override
	protected void doSuccess(EventResponse erp) {
		super.doSuccess(erp);
	}

	@Override
	protected void doFail(EventResponse erp) {
		log.error("errorrrrrrrrr");
		super.doFail(erp);
	}

	@Override
	public boolean getCSRFToken() {
		// TODO Auto-generated method stub
		return false;
	}

}
