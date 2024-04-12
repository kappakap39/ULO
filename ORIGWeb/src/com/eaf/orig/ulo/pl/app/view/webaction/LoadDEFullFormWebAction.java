package com.eaf.orig.ulo.pl.app.view.webaction;

//import java.sql.Timestamp;
//import java.util.Vector;
import org.apache.log4j.Logger;
//import com.ava.bpm.model.response.WorkflowResponse;
//import com.ava.bpm.util.ResultCodeConstant;
import com.eaf.j2ee.pattern.control.FrontController;
import com.eaf.j2ee.pattern.control.event.Event;
import com.eaf.j2ee.pattern.control.event.EventResponse;
import com.eaf.j2ee.pattern.view.webaction.WebAction;
import com.eaf.j2ee.pattern.view.webaction.WebActionHelper;
//import com.eaf.orig.profile.model.UserDetailM;
//import com.eaf.orig.shared.model.ProcessMenuM;
//import com.eaf.orig.shared.service.ORIGEJBService;
//import com.eaf.orig.shared.utility.ORIGFormUtil;
//import com.eaf.orig.ulo.pl.app.ejb.PLORIGApplicationManager;
//import com.eaf.orig.ulo.pl.formcontrol.view.form.PLOrigFormHandler;
//import com.eaf.orig.ulo.pl.model.app.PLApplicationDataM;
//import com.eaf.orig.ulo.pl.model.app.personal.PLPersonalInfoDataM;
//import com.eaf.xrules.ulo.pl.tool.display.DisplayMatrixTool;

public class LoadDEFullFormWebAction extends WebActionHelper implements	WebAction {
	Logger logger = Logger.getLogger(LoadDEFullFormWebAction.class);

	@Override
	public Event toEvent() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public boolean requiredModelRequest() {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean processEventResponse(EventResponse response) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean preModelRequest() {
//		// TODO Auto-generated method stub
//		logger.debug("+++++++++++++++ Start LoadFullFormWebAction +++++++++++++");
//		PLOrigFormHandler PLORIGForm = (PLOrigFormHandler) getRequest()
//				.getSession().getAttribute("PLORIGForm");
//		PLApplicationDataM plApplicationM = PLORIGForm.getAppForm();
//		String formID = null;
//		String drawDownFlag = null;
//		String currentTab = "MAIN_TAB";
//		String role = "";
//		UserDetailM userM = (UserDetailM) getRequest().getSession()
//				.getAttribute("ORIGUser");
//		logger.debug("+++++++++++++++ UserDetailM= " + userM);
//
//		Vector userRoles = userM.getRoles();
//
//		//if (!OrigConstant.ROLE_DE_FULL.equals(userRoles.elementAt(0))) {
//		//	userRoles.insertElementAt(OrigConstant.ROLE_DE_FULL, 0);
//		//}
//		userM.setRoles(userRoles);
//		WorkflowResponse workFlowResponse = new WorkflowResponse();
//		PLORIGApplicationManager plApplicationManager = ORIGEJBService
//				.getPLORIGApplicationManager();
//		// claim appp
//		try {
//			workFlowResponse = plApplicationManager.claimApplication(
//					plApplicationM, userM);
//		} catch (Exception e) {
//			logger.debug("[LoadFullFormWebAction].. Error Claim app.");
//			// e.printStackTrace();
//		}
//		// Load form database
//		if (ResultCodeConstant.RESULT_CODE_SUCCESS
//				.equalsIgnoreCase(workFlowResponse.getResultCode())) {
//
//			logger.debug("[LoadMainPLApplicationWebAction].. Claim Application Success! Load Application Data");
//
//			plApplicationM = plApplicationManager
//					.loadPLApplicationDataM(plApplicationM.getAppRecordID());
//
//			plApplicationM.setClaimDate(new Timestamp(System
//					.currentTimeMillis()));
//			plApplicationM.setJobID(workFlowResponse.getJobId());
//			plApplicationM.setPtID(workFlowResponse.getPtid());
//			plApplicationM.setPtType(workFlowResponse.getPtType());
//
//			formID = ORIGFormUtil.getFormIDByBus(plApplicationM
//					.getBusinessClassId());
//
//			// if(OrigConstant.ROLE_DE_FULL.equalsIgnoreCase(roleElement)){
//			// userRoles.insertElementAt(OrigConstant.ROLE_DE_FULL, 0);
//			// }
//
//			role = userM.getCurrentRole();
//
//			logger.debug("[LoadMainPLApplicationWebAction]..FormID " + formID);
//			logger.debug("[LoadMainPLApplicationWebAction]..Role " + role);
//			logger.debug("[LoadMainPLApplicationWebAction]..Job State "
//					+ plApplicationM.getJobState());
//
//			PLORIGForm.setAppForm(plApplicationM);
//
////			PLORIGForm.getSubForms().clear();
//			PLORIGForm.setLoadedSubForms(false);
//
//			PLORIGForm.loadSubFormsForDrawDown(userRoles, formID, drawDownFlag);
//			PLORIGForm.setCurrentTab(currentTab);
//			PLORIGForm.setFormID(formID);
//
//			logger.debug("[LoadMainPLApplicationWebAction]..getSubForms() "
//					+ PLORIGForm.getSubForms());
//			// nextAction="page=PL_MAIN_APPFORM";
//		}
//
//		// formID =
//		// ORIGFormUtil.getFormIDByBus(PLApplicationM.getBusinessClassId());
//
//		 PLPersonalInfoDataM plPersonalInfo=plApplicationM.getPLPersonalInfoDataM(PLPersonalInfoDataM.PersonalType.PERSONAL_TYPE_APPLICANT);
//		logger.debug("Personal type="+plPersonalInfo.getPersonalType());
//		logger.debug("idno=" +plPersonalInfo.getIdNo());
//		logger.debug("plApplicationM.getPersonalInfoVect().size()="+plApplicationM.getPersonalInfoVect().size());
////		PLORIGForm.getSubForms().clear();
//		PLORIGForm.setLoadedSubForms(false);
//		// ****************************************
//		PLORIGForm.loadSubForms(userRoles, formID);
//		PLORIGForm.setCurrentTab(currentTab);
//		PLORIGForm.setFormID(formID);
//		logger.debug("PLORIGForm.getSubForms()=" + PLORIGForm.getSubForms());
//		// *****************************************
//		// getRequest().getSession().setAttribute("PLORIGForm", PLORIGForm);

		return true;
	}

	@Override
	public int getNextActivityType() {
		return FrontController.PAGE;
	}

	@Override
	public boolean getCSRFToken() {
		// TODO Auto-generated method stub
		return false;
	}

}
