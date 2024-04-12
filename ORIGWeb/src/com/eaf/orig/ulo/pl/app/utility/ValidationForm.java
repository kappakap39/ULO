package com.eaf.orig.ulo.pl.app.utility;

import java.util.Vector;
import javax.servlet.http.HttpServletRequest;

import org.apache.log4j.Logger;

import com.eaf.j2ee.pattern.util.ErrorUtil;
import com.eaf.orig.cache.properties.ORIGCacheDataM;
import com.eaf.orig.profile.model.UserDetailM;
import com.eaf.orig.shared.constant.OrigConstant;
import com.eaf.orig.shared.service.ORIGEJBService;
import com.eaf.orig.shared.util.OrigUtil;
import com.eaf.orig.shared.utility.ORIGCacheUtil;
import com.eaf.orig.shared.utility.SerializeUtil;
import com.eaf.orig.ulo.control.message.Message;
import com.eaf.orig.ulo.pl.ajax.CaDecisionAuthorize;
import com.eaf.orig.ulo.pl.app.dao.exception.PLOrigApplicationException;
import com.eaf.orig.ulo.pl.app.view.webaction.ExecuteXRulesManualWebAction;
import com.eaf.orig.ulo.pl.formcontrol.view.form.PLOrigFormHandler;
import com.eaf.orig.ulo.pl.formcontrol.view.form.SearchHandler;
import com.eaf.orig.ulo.pl.model.app.PLApplicationDataM;
import com.eaf.orig.ulo.pl.model.app.PLPersonalInfoDataM;
import com.eaf.orig.ulo.pl.service.PLORIGEJBService;
import com.eaf.orig.ulo.pl.util.ejb.ORIGDAOUtilLocal;
import com.eaf.xrules.shared.constant.PLXrulesConstant;
import com.eaf.xrules.ulo.pl.model.XrulesRequestDataM;
import com.eaf.xrules.ulo.pl.model.XrulesResponseDataM;
import com.eaf.xrules.ulo.pl.model.app.rule.PLXRulesVerificationResultDataM;
import com.eaf.xrules.ulo.pl.moduleservice.core.ejb.ExecuteServiceManager;

public class ValidationForm {
	
	static Logger logger = Logger.getLogger(ValidationForm.class);
	
	public static boolean getValidationForm (HttpServletRequest request){
		
		PLOrigFormHandler formHandler = (PLOrigFormHandler) request.getSession().getAttribute(OrigConstant.PL_FORMHANDLER_NAME);
		if(null == formHandler || formHandler.getAppForm() == null) return false;
		formHandler.DestoryErrorFields();
		
		String submitType = request.getParameter("submitType");
		if(OrigUtil.isEmptyString(submitType)){
			formHandler.PushErrorMessage("", ErrorUtil.getShortErrorMessage(request, "USER_AUTHORIZE_ERROR"));
			return false;
		}
		if(OrigConstant.SUBMIT_TYPE_DRAFT.equals(submitType)){
			return true;
		}
		
		PLApplicationDataM appM = formHandler.getAppForm();
		UserDetailM userM = (UserDetailM) request.getSession().getAttribute("ORIGUser");
		
		String decision = request.getParameter("decision_option");
		ORIGCacheUtil cacheUtil = ORIGCacheUtil.getInstance();
		Vector<ORIGCacheDataM> decisionVect = cacheUtil.GetDecision(userM.getCurrentRole(), appM.getBusinessClassId());
		decisionVect = PLOrigUtility.filterDecision(appM, decisionVect, userM.getCurrentRole()); 
		if(!OrigUtil.isEmptyVector(decisionVect) && !OrigUtil.isEmptyString(decision)){
			for (int i=0; i<decisionVect.size(); i++){
				ORIGCacheDataM cache = decisionVect.get(i);
				if (cache.getCode().equals(decision)) {
					return true;
				}
			}
		}
		
		formHandler.PushErrorMessage("", ErrorUtil.getShortErrorMessage(request, "USER_AUTHORIZE_ERROR"));
		return false;
		
	}
	
	public static boolean getDCSUPValidationForm (HttpServletRequest request){
		
		PLOrigFormHandler formHandler = (PLOrigFormHandler) request.getSession().getAttribute(OrigConstant.PL_FORMHANDLER_NAME);
		if(null == formHandler || formHandler.getAppForm() == null) return false;
		formHandler.DestoryErrorFields();
		
		String submitType = request.getParameter("submitType");
		if(OrigUtil.isEmptyString(submitType)){
			formHandler.PushErrorMessage("", ErrorUtil.getShortErrorMessage(request, "USER_AUTHORIZE_ERROR"));
			return false;
		}
		if(OrigConstant.SUBMIT_TYPE_DRAFT.equals(submitType)){
			return true;
		}
		
		PLApplicationDataM applicationM = formHandler.getAppForm();
		UserDetailM userM = (UserDetailM) request.getSession().getAttribute("ORIGUser");
		
		PLPersonalInfoDataM personalM = applicationM.getPLPersonalInfoDataM(OrigConstant.PERSONAL_TYPE_APPLICANT);
		
		PLXRulesVerificationResultDataM xruleVerM = personalM.getXrulesVerification();
		if(OrigUtil.isEmptyObject(xruleVerM)){
			xruleVerM = new PLXRulesVerificationResultDataM();
			personalM.setXrulesVerification(xruleVerM);
		}
		
		String decision = request.getParameter("decision_option");
		
//		#SeptemWi Comment Not Check Manual Decision !
//		if(!OrigUtil.isEmptyString(decision) && !OrigConstant.Action.SEND_BACK_TO_DC.equals(decision) && !OrigUtil.isEmptyString(verM.getNCBResult())){
//			ORIGCacheUtil cacheUtil = ORIGCacheUtil.getInstance();
//			Vector<ORIGCacheDataM> decisionVect = cacheUtil.GetDecision(userM.getCurrentRole(), applicationM.getBusinessClassId());
//			decisionVect = PLOrigUtility.filterDecision(applicationM, decisionVect, userM.getCurrentRole()); 
//			if(!OrigUtil.isEmptyVector(decisionVect) && !OrigUtil.isEmptyString(decision)){
//				for (int i=0; i<decisionVect.size(); i++){
//					ORIGCacheDataM cache = decisionVect.get(i);
//					if (cache.getCode().equals(decision)) {
//						return true;
//					}
//				}
//			}
//		}
		
		
		ORIGCacheUtil cacheUtil = ORIGCacheUtil.getInstance();
		Vector<ORIGCacheDataM> decisionVect = cacheUtil.GetDecision(userM.getCurrentRole(), applicationM.getBusinessClassId());
		decisionVect = PLOrigUtility.filterDecision(applicationM, decisionVect, userM.getCurrentRole()); 
		if(!OrigUtil.isEmptyVector(decisionVect) && !OrigUtil.isEmptyString(decision)){
			for (int i=0; i<decisionVect.size(); i++){
				ORIGCacheDataM cache = decisionVect.get(i);
				if (cache.getCode().equals(decision)) {
					return true;
				}
			}
		}
		
		formHandler.PushErrorMessage("", ErrorUtil.getShortErrorMessage(request, "USER_AUTHORIZE_ERROR"));
		return false;
		
	}
	
	public static boolean getCaI_ValidationForm (HttpServletRequest request){
		
		PLOrigFormHandler formHandler = (PLOrigFormHandler) request.getSession().getAttribute(OrigConstant.PL_FORMHANDLER_NAME);
		if(null == formHandler || formHandler.getAppForm() == null) return false;
		formHandler.DestoryErrorFields();
		
		String submitType = request.getParameter("submitType");
		if(OrigUtil.isEmptyString(submitType)){
			formHandler.PushErrorMessage("", ErrorUtil.getShortErrorMessage(request, "USER_AUTHORIZE_ERROR"));
			return false;
		}
		if(OrigConstant.SUBMIT_TYPE_DRAFT.equals(submitType)){
			return true;
		}
		
		PLApplicationDataM appM = formHandler.getAppForm();
		
		String decision = request.getParameter("decision_option");
		
		logger.debug("getCaI_ValidationForm().."+decision);
		
		ORIGCacheUtil cacheUtil = ORIGCacheUtil.getInstance();
		Vector<ORIGCacheDataM> decisionVect = cacheUtil.GetDecision(OrigConstant.ROLE_CA_SUP, appM.getBusinessClassId());
		decisionVect = PLOrigUtility.filterDecision(appM, decisionVect, OrigConstant.ROLE_CA_SUP); 
		if(!OrigUtil.isEmptyVector(decisionVect) && !OrigUtil.isEmptyString(decision)){
			for (int i=0; i<decisionVect.size(); i++){
				ORIGCacheDataM cache = decisionVect.get(i);
				if (cache.getCode().equals(decision)) {
					return true;
				}
			}
		}
		
		formHandler.PushErrorMessage("", ErrorUtil.getShortErrorMessage(request, "USER_AUTHORIZE_ERROR"));
		return false;
		
	}
	
	public static boolean getPoValidationForm (HttpServletRequest request){
		
		PLOrigFormHandler formHandler = (PLOrigFormHandler) request.getSession().getAttribute(OrigConstant.PL_FORMHANDLER_NAME);
		if(null == formHandler || formHandler.getAppForm() == null) return false;
		formHandler.DestoryErrorFields();
		
		String submitType = request.getParameter("submitType");
		if(OrigUtil.isEmptyString(submitType)){
			formHandler.PushErrorMessage("", ErrorUtil.getShortErrorMessage(request, "USER_AUTHORIZE_ERROR"));
			return false;
		}
		if(OrigConstant.SUBMIT_TYPE_DRAFT.equals(submitType)){
			return true;
		}
		
		String decision = request.getParameter("decision_option");
		if(!OrigUtil.isEmptyString(decision) && (OrigConstant.Action.APPROVE.equals(decision) || OrigConstant.Action.REJECT.equals(decision))){
			return true;
		}
		
		formHandler.PushErrorMessage("", ErrorUtil.getShortErrorMessage(request, "USER_AUTHORIZE_ERROR"));
		return false;
	}
	
	public static boolean getCbSupValidationForm(HttpServletRequest request){

/**#SeptemWi Comment This Action Not Have FormHandler !!
		PLOrigFormHandler formHandler = (PLOrigFormHandler) request.getSession().getAttribute(OrigConstant.PL_FORMHANDLER_NAME);
		if(null == formHandler || formHandler.getAppForm() == null) return false;
		formHandler.DestoryErrorFields();
		
		String submitType = request.getParameter("submitType");
		if(OrigUtil.isEmptyString(submitType)){
			formHandler.PushErrorMessage("", ErrorUtil.getShortErrorMessage(request, "USER_AUTHORIZE_ERROR"));
			return false;
		}
		if(OrigConstant.SUBMIT_TYPE_DRAFT.equals(submitType)){
			return true;
		}
*/		
		
		String decision = request.getParameter("param-action");
		
		if(OrigUtil.isEmptyString(decision) || (OrigConstant.Action.SEND_TO_CB.equals(decision) || OrigConstant.Action.REJECT.equals(decision)
						|| OrigConstant.wfProcessState.SENDX.equals(decision))) {
			return true;
		}
		SearchHandler.PushErrorMessage(request, ErrorUtil.getShortErrorMessage(request,"USER_AUTHORIZE_ERROR"));
		return false;
	}
	
	public static boolean getCaValidationForm (HttpServletRequest request){
		
		PLOrigFormHandler formHandler = (PLOrigFormHandler) request.getSession().getAttribute(OrigConstant.PL_FORMHANDLER_NAME);
		if(null == formHandler || formHandler.getAppForm() == null) return false;
		formHandler.DestoryErrorFields();
		
		String submitType = request.getParameter("submitType");
		if(OrigUtil.isEmptyString(submitType)){
			formHandler.PushErrorMessage("", ErrorUtil.getShortErrorMessage(request, "USER_AUTHORIZE_ERROR"));
			return false;
		}
		if(OrigConstant.SUBMIT_TYPE_DRAFT.equals(submitType)){
			return true;
		}
		
		String decision = request.getParameter("decision_option");
		
		CaDecisionAuthorize caDecicsionAut = new CaDecisionAuthorize();
		try {
			String caDecicsion = caDecicsionAut.getDisplayObject(request);
			if(!OrigUtil.isEmptyString(caDecicsion) && caDecicsion.equals(decision))
				return true;
		} catch (PLOrigApplicationException e) {
			e.printStackTrace();
		}
		
		/*if(!OrigUtil.isEmptyString(decision)){
			if(OrigConstant.Action.APPROVE.equals(decision) || OrigConstant.Action.OVERRIDE.equals(decision) 
					|| OrigConstant.Action.ESCALATE.equals(decision) || OrigConstant.Action.POLICY_EXCEPTION.equals(decision)){
				UserDetailM userM = (UserDetailM) request.getSession().getAttribute("ORIGUser");
			
				CADecisionSubForm caDecision = new CADecisionSubForm();
				caDecision.setProperties(request, formHandler);
				
				PLApplicationDataM applicationM = formHandler.getAppForm();
				
				ProcessMenuM menuM = (ProcessMenuM) request.getSession().getAttribute("CurrentMenuM");
				if(menuM == null) menuM = new ProcessMenuM();
				applicationM = OrigApplicationUtil.getInstance().defaultCaDecision(userM, menuM, applicationM);
				if(decision.equals(applicationM.getCaDecision())){
					return true;
				}

			}
			formHandler.PushErrorMessage("", ErrorUtil.getShortErrorMessage(request, "USER_AUTHORIZE_ERROR"));
			return false;
		}*/
		
		formHandler.PushErrorMessage("", ErrorUtil.getShortErrorMessage(request, "USER_AUTHORIZE_ERROR"));
		return false;
	}
	
	public static boolean doValidateDuplicate(HttpServletRequest request){
		
		boolean validate = true;
		
		PLOrigFormHandler formHandler = (PLOrigFormHandler) request.getSession().getAttribute(OrigConstant.PL_FORMHANDLER_NAME);
		if(null == formHandler || formHandler.getAppForm() == null) return false;
		formHandler.DestoryErrorFields();
		
		String IDNO = request.getParameter("identification_no");
		
		UserDetailM userM = (UserDetailM) request.getSession().getAttribute("ORIGUser");
		
		logger.debug("IDNO >> "+IDNO);
		
		PLApplicationDataM applicationM = formHandler.getAppForm();
		
		if(null == applicationM){
			applicationM = new PLApplicationDataM();
		}
		
		applicationM.setServerFLAG(null);
		
		PLPersonalInfoDataM personalM = applicationM.getPLPersonalInfoDataM(PLPersonalInfoDataM.PersonalType.PERSONAL_TYPE_APPLICANT);
		
		PLXRulesVerificationResultDataM xrulesVerM = personalM.getXrulesVerification();
		
		if(null == xrulesVerM){
			xrulesVerM = new PLXRulesVerificationResultDataM();
		}
		
		ORIGDAOUtilLocal daoBean = PLORIGEJBService.getORIGDAOUtilLocal();
			applicationM.setBlockFlag(daoBean.loadBlockFlag(applicationM.getAppRecordID()));
		
		logger.debug("BLOCK FLAG >> "+applicationM.getBlockFlag());	
		
		if(!OrigConstant.BLOCK_FLAG.equals(applicationM.getBlockFlag())){		
			if(!OrigUtil.isEmptyString(IDNO) && IDNO.equals(personalM.getIdNo())){
				try{					
					String serviceID = String.valueOf(PLXrulesConstant.ModuleService.DEPLICATE_APPLICATION);
					String buttonID = PLXrulesConstant.ButtonID.BUTTON_7001;	
					
					String execute1 = xrulesVerM.getExecute1Result();
					String execute2 = xrulesVerM.getExecute2Result();
					
					ExecuteXRulesManualWebAction.doResetService(applicationM, serviceID, userM);
					
					ORIGXRulesTool tool = new ORIGXRulesTool();						
					XrulesRequestDataM requestM = tool.MapXrulesRequestDataM(applicationM, Integer.parseInt(serviceID), userM);
						requestM.setActionExecute(PLXrulesConstant.ButtonID.BUTTON_7001);
					
					ExecuteServiceManager execute = ORIGEJBService.getExecuteServiceManager();					
					XrulesResponseDataM reponse = execute.ExecuteManualService(requestM);			
					
					String execute_code = tool.getExecuteCode(reponse);
					
					logger.debug("execute_code >> "+execute_code);
					
					if(PLXrulesConstant.ExecuteCode.EXE_SUCCESS.equals(execute_code)){
												
						tool.MapServiceExecute(reponse,applicationM,request);		
												
						tool.MapDecision(reponse,applicationM,userM,buttonID);		
												
//						#septemwi comment not set button style
//						tool.ButtonStyleEngine(buttonID,applicationM,request);
						
						String execute_result = tool.getExcuteResult(reponse);
						
						logger.debug("execute_result >> "+execute_result);
						
						if(PLXrulesConstant.ResultCode.CODE_CANCEL.equals(execute_result)
								 || PLXrulesConstant.ResultCode.CODE_BLOCK.equals(execute_result)){
							 applicationM.setServerFLAG(execute_result);
							 validate = false;
						}else{
							// last execute1 result
							xrulesVerM.setExecute1Result(execute1);
						}
					}else{
						String MSG = Message.error();
						formHandler.PushErrorMessage("", MSG);
						validate = false;
					}
					
					PLApplicationDataM sensitiveappM = (PLApplicationDataM)SerializeUtil.clone(formHandler.getAppForm());
						request.getSession(true).setAttribute(PLOrigFormHandler.PLORIGSensitive,sensitiveappM);
										
				}catch(Exception e){
					String MSG = Message.error(e);
					formHandler.PushErrorMessage("", MSG);
					validate = false;
				}
			}else if(!OrigUtil.isEmptyString(IDNO) && !IDNO.equals(personalM.getIdNo())){
				formHandler.PushErrorMessage("", ErrorUtil.getShortErrorMessage(request, "USER_AUTHORIZE_ERROR"));
				validate = false;
			}
		}				
		return validate;
	}
	
}
