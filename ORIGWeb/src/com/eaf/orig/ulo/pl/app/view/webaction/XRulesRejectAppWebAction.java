package com.eaf.orig.ulo.pl.app.view.webaction;

import java.math.BigDecimal;
import java.sql.Timestamp;
import java.util.Date;

import org.apache.log4j.Logger;

import com.eaf.j2ee.pattern.control.FrontController;
import com.eaf.j2ee.pattern.control.event.Event;
import com.eaf.j2ee.pattern.control.event.EventResponse;
//import com.eaf.j2ee.pattern.util.ErrorUtil;
import com.eaf.j2ee.pattern.view.webaction.WebAction;
import com.eaf.j2ee.pattern.view.webaction.WebActionHelper;
import com.eaf.orig.profile.model.UserDetailM;
import com.eaf.orig.shared.constant.OrigConstant;
import com.eaf.orig.shared.dao.utility.ObjectDAOFactory;
import com.eaf.orig.shared.dao.utility.OrigApplicationUtil;
import com.eaf.orig.shared.dao.utility.UtilityDAO;
import com.eaf.orig.shared.model.ProcessMenuM;
import com.eaf.orig.ulo.control.message.Message;
import com.eaf.orig.ulo.pl.app.utility.WebActionUtil;
import com.eaf.orig.ulo.pl.app.utility.WorkflowTool;
import com.eaf.orig.ulo.pl.constant.WorkflowConstant;
import com.eaf.orig.ulo.pl.control.event.PLApplicationEvent;
import com.eaf.orig.ulo.pl.formcontrol.view.form.PLOrigFormHandler;
import com.eaf.orig.ulo.pl.model.app.PLApplicationDataM;
import com.eaf.orig.ulo.pl.model.app.PLPersonalInfoDataM;
import com.eaf.orig.ulo.pl.service.PLORIGEJBService;
import com.eaf.orig.ulo.pl.util.ejb.ORIGDAOUtilLocal;
import com.eaf.xrules.shared.constant.PLXrulesConstant;
import com.eaf.xrules.ulo.pl.model.app.rule.PLXRulesVerificationResultDataM;

public class XRulesRejectAppWebAction extends WebActionHelper implements WebAction{	
	static Logger logger = Logger.getLogger(XRulesRejectAppWebAction.class);	
	private String nextaction = "";
	@Override
	public Event toEvent(){		
		UserDetailM userM = (UserDetailM) getRequest().getSession().getAttribute("ORIGUser");
		PLApplicationDataM applicationM = PLOrigFormHandler.getPLApplicationDataM(getRequest());
		String userName = userM.getUserName();		
		applicationM.setOwner(userName);	
		
		applicationM.setUpdateBy(userName);
		applicationM.setUpdateDate(new Timestamp(new Date().getTime()));
			
		PLApplicationEvent event = new PLApplicationEvent(WebActionUtil.getEvenAction(userM.getCurrentRole()), applicationM, userM);		
			event.setCloanPlApplicationM(PLOrigFormHandler.getCloanPLApplicationDataM(getRequest()));		
		return event;
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
	public boolean preModelRequest(){	
//		PLApplicationDataM applicatonM = PLOrigFormHandler.getPLApplicationDataM(getRequest());
		
		PLOrigFormHandler formHandler = (PLOrigFormHandler) getRequest().getSession().getAttribute(OrigConstant.PL_FORMHANDLER_NAME);
		PLApplicationDataM applicatonM = formHandler.getAppForm();
		
		PLPersonalInfoDataM personlM = applicatonM.getPLPersonalInfoDataM(PLPersonalInfoDataM.PersonalType.PERSONAL_TYPE_APPLICANT);
		PLXRulesVerificationResultDataM xrulesVerM = personlM.getXrulesVerification();
		UserDetailM userM = (UserDetailM) getRequest().getSession().getAttribute("ORIGUser");
		if(null == xrulesVerM){
			xrulesVerM = new PLXRulesVerificationResultDataM();
			personlM.setXrulesVerification(xrulesVerM);
		}
		try{
			/**#septemwi modify set final decision for confirm reject or reject skip df*/
			String action =  this.MakeFixDecisionReject(applicatonM, userM);
			applicatonM.setAppDecision(action);	
			
			if(OrigConstant.Action.CONFIRM_REJECT.equals(action)
					|| OrigConstant.Action.REJECT_SKIP_DF.equals(action)){
				OrigApplicationUtil.getInstance().setFinalAppDecision(applicatonM, userM);
			}
				
			applicatonM.setApplicationStatus(null);
			
			if(OrigConstant.ROLE_DC.equals(userM.getCurrentRole())){
				if(OrigConstant.BusClass.FCP_KEC_CC.equals(applicatonM.getBusinessClassId())
						|| OrigConstant.BusClass.FCP_KEC_CG.equals(applicatonM.getBusinessClassId())){
					if(OrigConstant.Action.REJECT.equals(applicatonM.getAppDecision())){
						UtilityDAO DAO = ObjectDAOFactory.getUtilityDAO();
						String NEXT = DAO.getNextWfSendBack(applicatonM.getAppRecordID(), OrigConstant.ROLE_CA);
						if(OrigConstant.FLAG_Y.equals(NEXT)){
							applicatonM.setApplicationStatus(OrigConstant.ApplicationStatus.CREDIT_VERIFYINGEDIT);
						}
					}
				}
			}
			applicatonM.setAppInfo(WorkflowTool.getApplicatonXML(applicatonM));
			
			applicatonM.setFinalCreditLimit(new BigDecimal(0));		
			applicatonM.setJobType(OrigConstant.typeColor.typeRed);
			
			xrulesVerM.setRecommendResult(PLXrulesConstant.ResultCode.CODE_REJECT);
			xrulesVerM.setSummaryOverideRuleCode(PLXrulesConstant.ResultCode.CODE_FAIL);
			xrulesVerM.setSummaryOverideRuleResult(PLXrulesConstant.ResultDesc.CODE_FAIL_DESC);
				
			applicatonM.setReasonVect(applicatonM.getExecuteReasonVect());
			applicatonM.setAuditFlag(OrigConstant.FLAG_Y);
		}catch(Exception e){
			String MSG = Message.error(e);
			formHandler.PushErrorMessage("", MSG);	
			return false;
		}
		return true;
	}

	@Override
	public int getNextActivityType() {
		return FrontController.ACTION;
	}
	
	protected void doSuccess(EventResponse erp) {
		logger.debug("[doSuccess].");
		UserDetailM userM = (UserDetailM) getRequest().getSession().getAttribute("ORIGUser");
		nextaction= "page="+WebActionUtil.getForwordPageSummaryRole(userM.getCurrentRole());
	}
	
	@Override
	protected void doFail(EventResponse erp) {
		logger.debug("[doFail].");
		PLOrigFormHandler formHandler = (PLOrigFormHandler) getRequest().getSession().getAttribute(OrigConstant.PL_FORMHANDLER_NAME);
		formHandler.DestoryErrorFields();
//		formHandler.PushErrorMessage("", ErrorUtil.getShortErrorMessage(getRequest(),"INTERNAL_SERVER_ERROR"));
		formHandler.PushErrorMessage("", erp.getMessage());
	}
	
	@Override
	public String getNextActionParameter(){	
		return nextaction;
	}
	
	public String MakeFixDecisionReject(PLApplicationDataM applicationM ,UserDetailM userM ){
		ProcessMenuM menuM = (ProcessMenuM) getRequest().getSession().getAttribute("CurrentMenuM");
		if(menuM == null) menuM = new ProcessMenuM();
		String role = userM.getCurrentRole();
		logger.debug("@@@@@ role[" + role +"] menu[" + menuM.getMenuID() + "]");
		String action = OrigConstant.Action.REJECT;	
		if(OrigConstant.ROLE_DE_SUP.equals(role)|| OrigConstant.ROLE_CA_SUP.equals(role)
				|| OrigConstant.ROLE_DC_SUP.equals(role)
				|| OrigConstant.ROLE_VC_SUP.equals(role) 
				|| (OrigConstant.ROLE_CA.equals(role) && WorkflowConstant.TODO_LIST_ID.CA_SUP_ICDC_CONFIRM_REJECT.equals(menuM.getMenuID()))){
			action = OrigConstant.Action.CONFIRM_REJECT;
			if(OrigConstant.FLAG_Y.equals(applicationM.getReopenFlag())){
				action = OrigConstant.Action.REJECT_SKIP_DF;
			}
		}else{
			ORIGDAOUtilLocal daoBean = PLORIGEJBService.getORIGDAOUtilLocal();
				applicationM.setBlockFlag(daoBean.loadBlockFlag(applicationM.getAppRecordID()));
			if(OrigConstant.BLOCK_FLAG.equals(applicationM.getBlockFlag())){
				action = OrigConstant.Action.REJECT_BLOCK;
			}	
		}
		logger.debug("MakeFixDecisionReject >> "+action);
		return action;
	}

	@Override
	public boolean getCSRFToken(){
		return true;
	}
	
}
