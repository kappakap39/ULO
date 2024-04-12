package com.eaf.orig.ulo.pl.control.action;

import java.util.Vector;
import org.apache.log4j.Logger;

import com.eaf.j2ee.pattern.control.action.Action;
import com.eaf.j2ee.pattern.control.event.Event;
import com.eaf.j2ee.pattern.control.event.EventResponse;
import com.eaf.j2ee.pattern.control.event.EventResponseHelper;
import com.eaf.orig.profile.model.UserDetailM;
import com.eaf.orig.shared.constant.OrigConstant;
import com.eaf.orig.ulo.control.message.Message;
import com.eaf.orig.ulo.pl.app.ejb.PLORIGApplicationManager;
import com.eaf.orig.ulo.pl.app.utility.EmailSMSControl;
import com.eaf.orig.ulo.pl.control.event.PLApplicationEvent;
import com.eaf.orig.ulo.pl.control.event.PLApplicationEventResponse;
import com.eaf.orig.ulo.pl.model.app.PLAccountCardDataM;
import com.eaf.orig.ulo.pl.model.app.PLAccountDataM;
import com.eaf.orig.ulo.pl.model.app.PLApplicationActionDataM;
import com.eaf.orig.ulo.pl.model.app.PLApplicationDataM;
import com.eaf.orig.ulo.pl.service.PLORIGEJBService;
import com.orig.bpm.ulo.model.WorkflowDataM;

public class PLApplicationAction implements Action {

	private static final long serialVersionUID = 1L;
	private int eventResp = EventResponseHelper.SUCCESS;

	Logger logger = Logger.getLogger(PLApplicationAction.class);

	@SuppressWarnings({"unused","unchecked"})
	@Override
	public EventResponse perform(Event ev) {
		
		PLApplicationEventResponse eventResponse = null;
		PLApplicationEvent event = (PLApplicationEvent) ev;
		
		Object returnObject = null;
		Vector<PLApplicationDataM> appDataVect = null;		
		PLApplicationDataM applicationM = null;		
		WorkflowDataM workflowM = null;		
		
		PLORIGApplicationManager manager = PLORIGEJBService.getPLORIGApplicationManager();
		int eventType = event.getEventType();
		Object object = event.getObject();
		UserDetailM userDetailM = event.getUserM();		
		try {
			switch(eventType){
				case PLApplicationEvent.DE_CONFIRM_REJECT:
					logger.debug("[perform] ..DE Confirm Reject");
					appDataVect = (Vector<PLApplicationDataM>) object;
					manager.confirmReject(appDataVect, userDetailM);
					break;
				case PLApplicationEvent.DE_UNBLOCK:
					logger.debug("[perform] ..DE UnBlock");
					appDataVect = (Vector<PLApplicationDataM>) object;
					manager.unblockApplication(appDataVect, userDetailM);
					break;
				case PLApplicationEvent.REWORK:
					logger.debug("[perform] ..REWORK");
					appDataVect = (Vector<PLApplicationDataM>)object;
					if (appDataVect != null && appDataVect.size() > 0) {
						String appNo = null;
						StringBuilder strB = new StringBuilder();
						for (PLApplicationDataM appDataM : appDataVect) {
							try {
								returnObject = manager.reWorkApplication(appDataM, userDetailM);
							} catch (Exception e){
								strB.append(appNo).append(", ");
								eventResp = EventResponseHelper.FAILED;
							}
						}
					}
					break;
				case PLApplicationEvent.DE_REASSIGN:
					logger.debug("[perform] ..DE Reassign");
					String assignTo = null;
					appDataVect = (Vector<PLApplicationDataM>) object;
					if (appDataVect != null && appDataVect.size() > 0) {
						PLApplicationActionDataM appActM = null;
						String appNo = null;
						StringBuilder strB = new StringBuilder();
						for (PLApplicationDataM appDataM : appDataVect) {
							try {
								appNo = appDataM.getApplicationNo();
								appActM = appDataM.getApplicationActionM();
								manager.reassingOrReallocateOrSendbackApplication(appDataM, userDetailM, appActM.getAction(), appActM.getAssignTo(), appActM.getAssignType());
							} catch (Exception e){
								strB.append(appNo).append(", ");
								eventResp = EventResponseHelper.FAILED;
							}
						}
					if (strB != null && strB.length() > 0) {
							strB.delete(strB.length()-2, strB.length()-1);
							returnObject = strB.toString();
						}
					}
					break;
				case PLApplicationEvent.DC_SAVE_DRAFT:
					logger.debug("[perform] ..DC Save Draft");
					applicationM = (PLApplicationDataM) object;
					applicationM.setSubmitType(OrigConstant.Action.SAVE_DRAFT);
					manager.savePLApplicationDataM(applicationM, userDetailM);
					break;
				case PLApplicationEvent.DC_SUBMIT:
					logger.debug("[perform] ..DC Submit");
					applicationM = (PLApplicationDataM) object;
					manager.savePLApplicationDataM(applicationM, userDetailM);
					
//					#septemwi comment change Logic send email sms!
//					try{
//						manager.sendEmailSMS(applicationM, userDetailM.getCurrentRole());
//					}catch(Exception e){
//						logger.fatal("##### Send Email or SMS error");
//					}
					try{
						EmailSMSControl control = new EmailSMSControl();
							control.send(applicationM, userDetailM);
					}catch(Exception e){
						logger.error("ERROR Send Email & SMS >> "+e.getMessage());
					}
					break;
				case PLApplicationEvent.DC_CREATE_ICDC:
					logger.debug("[perform] ..DC Create Increase/Decrease");
					applicationM = (PLApplicationDataM) object;
					returnObject = manager.createApplicationICDC(applicationM, userDetailM);
					break;
				case PLApplicationEvent.DC_CREATE_BUNDLING:
					logger.debug("[perform] ..DC Create Bundling");
					applicationM = (PLApplicationDataM) object;
					manager.createAppBundling(applicationM, userDetailM);
					break;	
				case PLApplicationEvent.DE_SAVE_DRAFT:
					logger.debug("[perform] ..DE Save Draft");
					applicationM = (PLApplicationDataM) object;
					applicationM.setSubmitType(OrigConstant.Action.SAVE_DRAFT);
					manager.savePLApplicationDataM(applicationM, userDetailM);
					break;
				case PLApplicationEvent.DE_SUBMIT:
					logger.debug("[perform] ..DE Submit");
					applicationM = (PLApplicationDataM) object;
					manager.savePLApplicationDataM(applicationM, userDetailM);
//					#septemwi comment change Logic send email sms!
//					try{
//						manager.sendEmailSMS(applicationM, userDetailM.getCurrentRole());
//					}catch(Exception e){
//						logger.fatal("##### Send Email or SMS error");
//					}
					try{
						EmailSMSControl control = new EmailSMSControl();
							control.send(applicationM, userDetailM);
					}catch(Exception e){
						logger.error("ERROR Send Email & SMS >> "+e.getMessage());
					}
					break;
				case PLApplicationEvent.VC_SAVE_DRAFT:
					logger.debug("[perform] ..VC Save Draft");
					applicationM = (PLApplicationDataM) object;
					applicationM.setSubmitType(OrigConstant.Action.SAVE_DRAFT);
					manager.savePLApplicationDataM(applicationM, userDetailM);
					break;
				case PLApplicationEvent.VC_SUBMIT:
					logger.debug("[perform] ..VC Submit");
					applicationM = (PLApplicationDataM) object;
					manager.savePLApplicationDataM(applicationM, userDetailM);
					try{
						manager.sendEmailSMS(applicationM, userDetailM.getCurrentRole());
					}catch(Exception e){
						logger.fatal("##### Send Email or SMS error");
					}
					break;
				case PLApplicationEvent.RE_ISSUE_CARD_NO:
					logger.debug("[perform] ..Reissue Card No");
					PLAccountCardDataM accCardM = (PLAccountCardDataM) object;
					manager.reIssueCardNo(accCardM, userDetailM);
					break;
				case PLApplicationEvent.RE_ISSUE_CUST_NO:
					logger.debug("[perform] ..Reissue Card No");
					PLAccountDataM accM = (PLAccountDataM) object;
					manager.reIssueCustNo(accM, userDetailM);
					break;
				case PLApplicationEvent.CB_PULL_JOB:
					logger.debug("[perform] ..CB Pull Job");
					applicationM = (PLApplicationDataM) object;
					manager.claimAndCompleteApplication(applicationM, userDetailM);
					break;
				case PLApplicationEvent.CB_SEND_BACK:
					logger.debug("[perform] ..CB Send Back");
					applicationM = (PLApplicationDataM) object;
					manager.claimSaveAndCompleteApplication(applicationM, userDetailM);
					break;
				case PLApplicationEvent.CB_REALLOCATE:
					logger.debug("[perform] ..CB Re Allocate");
					appDataVect = (Vector<PLApplicationDataM>) object;
					manager.claimAndCompleteApplicationVect(appDataVect , userDetailM);
					break;
				case PLApplicationEvent.CB_SAVE:
					logger.debug("[perform] ..CB_SAVE");
					applicationM = (PLApplicationDataM) object;
					applicationM.setSubmitType(OrigConstant.Action.SAVE_DRAFT);
					manager.savePLApplicationDataM(applicationM, userDetailM);
					break;
				case PLApplicationEvent.CB_VERIFY:
					logger.debug("[perform] ..CB_VERIFY");
					applicationM = (PLApplicationDataM) object;
					manager.savePLApplicationDataM(applicationM , userDetailM);
					break;
				case PLApplicationEvent.CA_SAVE_DRAFT:
					logger.debug("[perform] ..CA Save Draft");
					applicationM = (PLApplicationDataM) object;
					applicationM.setSubmitType(OrigConstant.Action.SAVE_DRAFT);
					manager.savePLApplicationDataM(applicationM, userDetailM);
					break;
				case PLApplicationEvent.CA_SUBMIT:
					logger.debug("[perform] ..CA Submit");
					applicationM = (PLApplicationDataM) object;
					manager.savePLApplicationDataM(applicationM, userDetailM);
//					#septemwi comment change Logic send email sms!
//					try{
//						manager.sendEmailSMS(applicationM, userDetailM.getCurrentRole());
//					}catch(Exception e){
//						logger.fatal("##### Send Email or SMS error");
//					}
					try{
						EmailSMSControl control = new EmailSMSControl();
							control.send(applicationM, userDetailM);
					}catch(Exception e){
						logger.error("ERROR Send Email & SMS >> "+e.getMessage());
					}
					break;
				case PLApplicationEvent.SUBMIT_REOPEN:
					logger.debug("[perform] ..Submit Reopen");
					applicationM = (PLApplicationDataM) object;
					manager.reOpenFlow(applicationM, userDetailM);
					break;
				case PLApplicationEvent.REOPEN_DUP:
					logger.debug("[perform] ..Submit Reopen");
					applicationM = (PLApplicationDataM) object;
					manager.SaveDeplicatePOApplication(applicationM, userDetailM);
					break;
				case PLApplicationEvent.REOPEN_DUP_CANCEL:
					logger.debug("[perform] ..Submit Reopen");
					applicationM = (PLApplicationDataM) object;
					manager.SaveCancelPOApplication(applicationM, userDetailM);
					break;
				case PLApplicationEvent.FU_SAVE_DRAFT:
					logger.debug("[perform] ..FU Save Draft");
					applicationM = (PLApplicationDataM) object;
					applicationM.setSubmitType(OrigConstant.Action.SAVE_DRAFT);
					manager.saveFuApplication(applicationM, userDetailM);
					break;
				case PLApplicationEvent.FU_SUBMIT:
					logger.debug("[perform] ..FU Submit");
					applicationM = (PLApplicationDataM) object;
					manager.savePLApplicationDataM(applicationM, userDetailM);
					try{
						manager.sendEmailSMS(applicationM, userDetailM.getCurrentRole());
					} catch (Exception e) {
						logger.fatal("##### Send Email or SMS error");
					}
					break;
				case PLApplicationEvent.DEPLICATE_APPLICATION:
					logger.debug("[perform] ..Duplicate Application");
					applicationM = (PLApplicationDataM) object;
					manager.SaveDeplicateApplication(applicationM, userDetailM);
					break;
				case PLApplicationEvent.DF_SAVE_DRAFT:
					logger.debug("[perform] ..DF Save Draft");
					applicationM = (PLApplicationDataM) object;
					applicationM.setSubmitType(OrigConstant.Action.SAVE_DRAFT);
					manager.savePLApplicationDataM(applicationM, userDetailM);
					break;
				case PLApplicationEvent.DF_SUBMIT:
					logger.debug("[perform] ..DF Submit");
					applicationM = (PLApplicationDataM) object;
					manager.savePLApplicationDataM(applicationM, userDetailM);
					break;
				case PLApplicationEvent.CASHDAY5_SAVE:
					logger.debug("[perform] ..Cash day5 Save");
					applicationM = (PLApplicationDataM) object;
					applicationM.setSubmitType(OrigConstant.Action.SAVE_DRAFT);
					applicationM.setAppDecision(OrigConstant.Action.SAVE_CASHDAY5);
					manager.saveCashDay5PLApplicationDataM(applicationM, userDetailM);
					break;
				case PLApplicationEvent.SS_CANCEL:
					logger.debug("[perform] ..SS_CANCEL_CARD_MAINTENANCE");
					applicationM = (PLApplicationDataM) object;
					manager.CancelApplication(applicationM, userDetailM);
					break;
				case PLApplicationEvent.BLOCK_CANCEL:
					logger.debug("[perform] ..BLOCK_CANCEL");
					applicationM = (PLApplicationDataM) object;
					manager.saveBlockCancelApplication(applicationM, userDetailM);
					break;
				case PLApplicationEvent.DE_CREDATE_MANUAL:
					logger.debug("[perform] ..DE Create  Manaual");
					applicationM = (PLApplicationDataM) object;
					manager.createApplicationManual(applicationM, userDetailM);
					break;	
				default:
					break;
			}

		}catch(Exception e){
//			#septem comment change Logic ERROR Message !
//			logger.fatal("PLApplicationAction error >>", e);
//			String errMsg = "Operation fail,please contact admin";
			String MSG = Message.error(e);
			Object encapData = null;
			if (eventResponse != null)encapData = eventResponse.getEncapData();
			if(null != applicationM){
				applicationM.setJobState(applicationM.getLastJobState());
				applicationM.setApplicationStatus(applicationM.getLastAppStatus());
			}
			return new PLApplicationEventResponse(event.getEventType(),	EventResponseHelper.FAILED, MSG, encapData);
		}
		return new PLApplicationEventResponse(event.getEventType(),	eventResp, "", returnObject);
	}

}
