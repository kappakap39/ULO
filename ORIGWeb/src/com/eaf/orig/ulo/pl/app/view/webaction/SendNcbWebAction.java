package com.eaf.orig.ulo.pl.app.view.webaction;

import java.sql.Connection;
import java.util.Vector;
import org.apache.log4j.Logger;
import com.eaf.j2ee.pattern.control.FrontController;
import com.eaf.j2ee.pattern.control.event.Event;
import com.eaf.j2ee.pattern.control.event.EventResponse;
import com.eaf.j2ee.pattern.view.webaction.WebAction;
import com.eaf.j2ee.pattern.view.webaction.WebActionHelper;
import com.eaf.orig.profile.model.UserDetailM;
import com.eaf.orig.shared.constant.OrigConstant;
import com.eaf.orig.shared.service.OrigServiceLocator;
import com.eaf.orig.shared.utility.ORIGUtility;
import com.eaf.orig.ulo.pl.app.utility.JsonObjectUtil;
import com.eaf.orig.ulo.pl.control.event.PLApplicationEvent;
import com.eaf.orig.ulo.pl.formcontrol.view.form.PLOrigFormHandler;
import com.eaf.orig.ulo.pl.model.app.PLApplicationDataM;
import com.eaf.orig.ulo.pl.model.app.PLApplicationImageDataM;
import com.eaf.orig.ulo.pl.model.app.PLApplicationImageSplitDataM;
import com.eaf.orig.ulo.pl.model.app.PLNCBDocDataM;
import com.eaf.orig.ulo.pl.model.app.PLPersonalInfoDataM;
import com.orig.bpm.proxy.BpmProxyService;
import com.orig.bpm.ulo.model.WorkflowDataM;
import com.orig.bpm.ulo.proxy.BpmWorkflow;

public class SendNcbWebAction extends WebActionHelper implements WebAction{	
	Logger logger = Logger.getLogger(this.getClass());	
	@Override
	public Event toEvent(){
		UserDetailM userM = (UserDetailM) getRequest().getSession().getAttribute("ORIGUser");		
		if(userM == null) userM = new UserDetailM();		
		PLApplicationDataM plApplicationDataM = PLOrigFormHandler.getPLApplicationDataM(getRequest());		
		if(plApplicationDataM == null) plApplicationDataM = new PLApplicationDataM();
		PLApplicationEvent event = new PLApplicationEvent(PLApplicationEvent.SEND_NCB, plApplicationDataM, userM);
		return event;
	}
	@Override
	public boolean requiredModelRequest(){
		return true;
	}
	@Override
	public boolean processEventResponse(EventResponse response){		
		return defaultProcessResponse(response);
	}
	@Override
	public boolean preModelRequest(){
		JsonObjectUtil objectUtil = new JsonObjectUtil();
		try{					
			PLApplicationDataM plApplicationM = PLOrigFormHandler.getPLApplicationDataM(getRequest());		
			if(plApplicationM == null) plApplicationM = new PLApplicationDataM();
			BpmWorkflow bpmWorkflow = BpmProxyService.getBpmWorkflow();	
			WorkflowDataM workflowM = new WorkflowDataM();
				workflowM.setAppRecordID(plApplicationM.getAppRecordID());
				workflowM.setUserRole(OrigConstant.ROLE_CB);
			boolean isExisting = bpmWorkflow.ValidateExistMainWorkflow(workflowM, this.getWorkFlowConnection());
			logger.debug("[preModelRequest] isExisting In Workflow "+isExisting);
			if(isExisting){
				objectUtil.CreateJsonMessage("status", "Connot Start Ncb Workflow.");
				objectUtil.ResponseJsonArray(getResponse());
				return true;
			}			
			Vector<PLApplicationImageDataM>	plImageVect = plApplicationM.getApplicationImageVect();
			Vector<PLApplicationImageSplitDataM> plImageSplitVect = null;		
			Vector<PLNCBDocDataM> plNcbDocVect = new Vector<PLNCBDocDataM>();
			PLPersonalInfoDataM plPersonInfoM = plApplicationM.getPLPersonalInfoDataM(PLPersonalInfoDataM.PersonalType.PERSONAL_TYPE_APPLICANT);				
			PLNCBDocDataM plNcbDocM = null;			
			if(!ORIGUtility.isEmptyVector(plImageVect)){
				for(PLApplicationImageDataM plImageM : plImageVect) {
					plImageSplitVect = plImageM.getAppImageSplitVect();
					if(!ORIGUtility.isEmptyVector(plImageSplitVect)){
						for(PLApplicationImageSplitDataM plImageSplitM:plImageSplitVect){
							String imgCheck = getRequest().getParameter("checkBox_"+plImageSplitM.getImgID());
							if(ORIGUtility.isEmptyString(imgCheck))
								continue;							
							plNcbDocM = new PLNCBDocDataM();
							plNcbDocM.setImgID(plImageSplitM.getImgID());
							plNcbDocM.setPersonalID(plPersonInfoM.getPersonalID());
							plNcbDocVect.add(plNcbDocM);
						}
					}
				}
			}
			plPersonInfoM.setNcbDocVect(plNcbDocVect);			
		}catch(Exception e){
			logger.debug("Error ",e);
			return false;
		}
		return true;
	}
	@Override
	public int getNextActivityType() {
		return FrontController.NOTFORWARD;
	}
	private Connection getWorkFlowConnection() {
		try {
			OrigServiceLocator origService = OrigServiceLocator.getInstance();
			return origService.getConnection(OrigServiceLocator.WORKFLOW_DB);
		} catch (Exception e) {
			logger.fatal("Connection is null "+e.getMessage());
		}
		return null;
	}
	protected void doSuccess(EventResponse erp) {
		logger.debug("[doSuccess].");
		try {
			JsonObjectUtil objectUtil = new JsonObjectUtil();
			objectUtil.CreateJsonMessage("status","Stat Ncb Success.");			
			objectUtil.ResponseJsonArray(getResponse());
		} catch (Exception e) {
			logger.fatal("Error "+e.getMessage());
		}
	}

	@Override
	protected void doFail(EventResponse erp) {
		logger.debug("[doFail].");
		try {
			JsonObjectUtil objectUtil = new JsonObjectUtil();
			objectUtil.CreateJsonMessage("status","Error Start Ncb.");
			objectUtil.ResponseJsonArray(getResponse());
		} catch (Exception e) {
			logger.fatal("Error "+e.getMessage());
		}
	}
	@Override
	public boolean getCSRFToken() {
		// TODO Auto-generated method stub
		return false;
	}
}
