package com.eaf.orig.ulo.pl.app.utility;

import javax.servlet.http.HttpServletRequest;

import org.apache.log4j.Logger;

import com.eaf.cache.MandatoryFieldCache;
//import com.eaf.j2ee.pattern.util.ErrorUtil;
import com.eaf.orig.profile.model.UserDetailM;
import com.eaf.orig.shared.constant.OrigConstant;
import com.eaf.orig.shared.servlet.AjaxDisplayGenerateInf;
import com.eaf.orig.shared.util.OrigUtil;
import com.eaf.orig.shared.utility.ORIGFormUtil;
import com.eaf.orig.shared.utility.ORIGUtility;
import com.eaf.orig.ulo.control.message.Message;
import com.eaf.orig.ulo.pl.app.dao.exception.PLOrigApplicationException;
import com.eaf.orig.ulo.pl.formcontrol.view.form.PLOrigFormHandler;
import com.eaf.orig.ulo.pl.model.app.PLApplicationDataM;
import com.eaf.orig.ulo.pl.model.app.PLPersonalInfoDataM;

public class ApplicationMandatory implements AjaxDisplayGenerateInf {

	Logger logger = Logger.getLogger(ApplicationMandatory.class);
	
	Performance perf = new Performance("ApplicationMandatory",Performance.Module.MANDATORY);
	
	@Override
	public String getDisplayObject(HttpServletRequest request) throws PLOrigApplicationException {
		logger.info("[Mandatory Application]");
		PLOrigFormHandler fromHandler = (PLOrigFormHandler) request.getSession().getAttribute(PLOrigFormHandler.PLORIGForm);			
		UserDetailM userM = (UserDetailM) request.getSession().getAttribute("ORIGUser");		
		try{	
			PLApplicationDataM applicationM = PLOrigFormHandler.getPLApplicationDataM(request);
	
			ORIGMandatoryErrorTool errorUtil = ORIGMandatoryErrorTool.getInstance();
	
			PLPersonalInfoDataM personalM = applicationM.getPLPersonalInfoDataM(OrigConstant.PERSONAL_TYPE_APPLICANT);
	
			String submitType = request.getParameter("submitType");
			String customer_type = personalM.getCustomerType();
			String formID = request.getParameter("formID");
			String mandatoryType = request.getParameter("mandatoryType");
			String decision = request.getParameter("decision_option");
			
			perf.init("getDisplayObject", applicationM.getAppRecordID(), applicationM.getTransactionID(), userM);
			
	//		logger.debug("decision = "+decision);
			
			perf.track(Performance.Action.MANDATORY, Performance.START);
			
			boolean isError = true;
			
			if(OrigUtil.isEmptyString(formID)){
				formID = ORIGFormUtil.getFormIDByBus(applicationM.getBusinessClassId());
			}
	
			/** Create New Error Fields #SeptemWi */
			if(null != fromHandler){
				fromHandler.DestoryErrorFields();
				fromHandler.setSaveType(null);
			}
			if (ORIGUtility.isEmptyString(customer_type))
				customer_type = OrigConstant.CUSTOMER_TYPE_INDIVIDUAL;
			
			logger.info("Submit Type >> " + submitType+"Mandatory Type "+mandatoryType+" Customer Type "+customer_type);
	//		logger.info("Mandatory Type >> " + mandatoryType);
	//		logger.info("Customer Type >> " + customer_type);
			if (OrigUtil.isEmptyString(mandatoryType)){
				mandatoryType = String.valueOf(OrigConstant.Mandatory.MANDATORY_TYPE_SUMMIT);
			}
			
			fromHandler.setSaveType(submitType);
			/** Set Submit Type Can Use For Manual Mandatory #SeptemWi */
			fromHandler.setMandatoryType(Integer.parseInt(mandatoryType));
			
	//		logger.debug("fromHandler.getMandatoryType() = "+fromHandler.getMandatoryType()+" decision = "+decision);
				
			switch (fromHandler.getMandatoryType()) {
				case OrigConstant.Mandatory.MANDATORY_TYPE_DRAFT:
					isError = errorUtil.getMandateErrorBySubFormDraft(request, customer_type, formID);
					break;
				case OrigConstant.Mandatory.MANDATORY_TYPE_SUMMIT:
					isError = errorUtil.getMandateErrorBySubForm(request, customer_type, formID);
					break;
				case OrigConstant.Mandatory.MANDATORY_TYPE_EXECUTE:
					String buttonID = request.getParameter("buttonID");
					fromHandler.setSaveType(buttonID);
					isError = errorUtil.GetMassageMandatory(request, customer_type, formID, MandatoryFieldCache.MandatoryConstant.BUTTON_EXECUTE, buttonID);
					break;
				case OrigConstant.Mandatory.MANDATORY_TYPE_SEND_BACK:
					isError = errorUtil.getMandateErrorBySubFormDraft(request, customer_type, formID);
					break;
				case OrigConstant.Mandatory.MANDATORY_TYPE_CASH_DAY5:
					isError = errorUtil.getMandatoryCashDay5(request, customer_type, formID);
					break;
				default:
					break;
			}
			perf.track(Performance.Action.MANDATORY, Performance.END);
			logger.debug("Has Error Field = " + isError);
		}catch(Exception e){
//			logger.fatal("Exception >> ",e);
			String MSG = Message.error(e);
			fromHandler.DestoryErrorFields();
			fromHandler.PushErrorMessage("", MSG);
		}
		return fromHandler.GetErrorFields();
	}

}
