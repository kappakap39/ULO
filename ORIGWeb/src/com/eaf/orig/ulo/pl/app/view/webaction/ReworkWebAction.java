package com.eaf.orig.ulo.pl.app.view.webaction;

import java.util.Vector;
import org.apache.log4j.Logger;
import com.eaf.j2ee.pattern.control.FrontController;
import com.eaf.j2ee.pattern.control.event.Event;
import com.eaf.j2ee.pattern.control.event.EventResponse;
//import com.eaf.j2ee.pattern.util.ErrorUtil;
import com.eaf.j2ee.pattern.view.webaction.WebAction;
import com.eaf.j2ee.pattern.view.webaction.WebActionHelper;
import com.eaf.orig.profile.model.UserDetailM;
import com.eaf.orig.shared.constant.OrigConstant;
import com.eaf.orig.shared.util.OrigUtil;
import com.eaf.orig.ulo.control.message.Message;
import com.eaf.orig.ulo.pl.control.event.PLApplicationEvent;
import com.eaf.orig.ulo.pl.formcontrol.view.form.SearchHandler;
import com.eaf.orig.ulo.pl.model.app.PLApplicationDataM;
import com.eaf.orig.ulo.pl.model.app.PLApplicationLogDataM;
import com.eaf.orig.ulo.pl.service.PLORIGEJBService;
import com.eaf.orig.ulo.pl.util.ejb.ORIGDAOUtilLocal;

public class ReworkWebAction extends WebActionHelper implements WebAction {

	static Logger log = Logger.getLogger(ReworkWebAction.class);
	Vector<PLApplicationDataM> appDataVect = new Vector<PLApplicationDataM>();
	UserDetailM userM = new UserDetailM();
	
	@Override
	public Event toEvent() {
		PLApplicationEvent appEvent = new PLApplicationEvent();
		appEvent.setObject(appDataVect);
		appEvent.setEventType(PLApplicationEvent.REWORK);
		appEvent.setUserM(userM);
		return appEvent;
	}

	@Override
	public boolean requiredModelRequest() {
		return true;
	}

	@Override
	public boolean processEventResponse(EventResponse response) {
		return defaultProcessResponse(response);
	}

	@Override
	public boolean preModelRequest() {

		String appRecId[] = getRequest().getParameterValues("checkAppRecId");
		userM = (UserDetailM) getRequest().getSession().getAttribute("ORIGUser");
		
		try {
			if (appRecId != null && appRecId.length > 0) {
				
				ORIGDAOUtilLocal daoUtil = PLORIGEJBService.getORIGDAOUtilLocal();
				for (int i = 0; i < appRecId.length; i++) {
					
					PLApplicationDataM appDataM = new PLApplicationDataM();
					String[] appRecIdAppNo = appRecId[i].split("\\|");
					
					appDataM = daoUtil.loadOrigApplication(appRecIdAppNo[0]);
					
					appDataM.setAppRecordID(appRecIdAppNo[0]);
					appDataM.setApplicationNo(appRecIdAppNo[1]);
					appDataM.setUpdateBy(userM.getUserName());
					appDataM.setAppDecision(OrigConstant.Action.REWORK);
					appDataM.setAppInfo(OrigUtil.getApplicatonXML(appDataM));
					appDataM.setApplicationStatus(null);
					
					Vector<PLApplicationLogDataM> appLogVect = new Vector<PLApplicationLogDataM>();
					PLApplicationLogDataM appLogM = new PLApplicationLogDataM();
					appLogM.setApplicationRecordID(appRecId[i]);
					appLogM.setUpdateBy(userM.getUserName());
					appLogM.setCreateBy(userM.getUserName());
					appLogVect.add(appLogM);
					
					appDataM.setApplicationLogVect(appLogVect);
					
					//Cleare final app decision
					appDataM.setFinalAppDecision(null);
					appDataM.setFinalAppDecisionBy(null);
					appDataM.setFinalAppDecisionDate(null);
					
					appDataVect.add(appDataM);
				}
			}
		} catch (Exception e) {
//			log.fatal("Exception ReworkWebAction >> ",e);
//			SearchHandler.PushErrorMessage(getRequest(), ErrorUtil.getShortErrorMessage(getRequest(),"INTERNAL_SERVER_ERROR"));
			String MSG = Message.error(e);
			SearchHandler.PushErrorMessage(getRequest(), MSG);	
			return false;
		}
		return true;
	}
	
	@Override
	public int getNextActivityType() {
		return FrontController.ACTION;
	}
	
	@Override
	public String getNextActionParameter() {
		return "action=SearchReworkWebAction";
	}

	@Override
	protected void doSuccess(EventResponse erp) {
		super.doSuccess(erp);
	}

	@Override
	protected void doFail(EventResponse erp) {
//		super.doFail(erp);
		SearchHandler.PushErrorMessage(getRequest(), erp.getMessage());	
	}

	@Override
	public boolean getCSRFToken() {
		return false;
	}
}
