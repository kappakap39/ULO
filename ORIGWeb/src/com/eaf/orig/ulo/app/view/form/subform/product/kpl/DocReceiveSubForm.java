package com.eaf.orig.ulo.app.view.form.subform.product.kpl;

import java.util.ArrayList;
import java.util.HashMap;

import javax.servlet.http.HttpServletRequest;

import org.apache.log4j.Logger;

import com.eaf.core.ulo.common.display.FormatUtil;
import com.eaf.core.ulo.common.model.AuditDataM;
import com.eaf.core.ulo.common.model.FieldElement;
import com.eaf.core.ulo.common.properties.RenderSubform;
import com.eaf.core.ulo.common.properties.SystemConstant;
import com.eaf.core.ulo.common.util.AuditFormUtil;
import com.eaf.core.ulo.common.util.FormDisplayValueUtil;
import com.eaf.core.ulo.common.util.FormErrorUtil;
import com.eaf.core.ulo.common.util.SessionControl;
import com.eaf.core.ulo.common.util.Util;
import com.eaf.orig.shared.constant.OrigConstant;
import com.eaf.orig.shared.view.form.ORIGSubForm;
import com.eaf.orig.ulo.app.util.GenerateUnique;
import com.eaf.orig.ulo.app.view.form.sensitive.util.CompareSensitiveFieldUtil;
import com.eaf.orig.ulo.app.view.util.dih.DIHProxy;
import com.eaf.orig.ulo.app.view.util.dih.model.DIHQueryResult;
import com.eaf.orig.ulo.app.view.util.kpl.KPLUtil;
import com.eaf.orig.ulo.constant.MConstant;
import com.eaf.orig.ulo.formcontrol.view.form.ORIGFormHandler;
import com.eaf.orig.ulo.model.app.ApplicationDataM;
import com.eaf.orig.ulo.model.app.ApplicationGroupDataM;
import com.eaf.orig.ulo.model.app.LoanDataM;
import com.eaf.orig.ulo.model.app.PaymentMethodDataM;
import com.eaf.orig.ulo.model.app.PersonalInfoDataM;
import com.eaf.orig.ulo.model.app.SpecialAdditionalServiceDataM;
import com.eaf.orig.ulo.model.compare.CompareDataM;
import com.eaf.orig.ulo.model.control.FlowControlDataM;

@SuppressWarnings("serial")
public class DocReceiveSubForm extends ORIGSubForm{
	String PERSONAL_TYPE = SystemConstant.getConstant("PERSONAL_TYPE_APPLICANT");	
	String PERSONAL_TYPE_APPLICANT = SystemConstant.getConstant("PERSONAL_TYPE_APPLICANT");
	String PRODUCT_K_PERSONAL_LOAN = SystemConstant.getConstant("PRODUCT_K_PERSONAL_LOAN");
	String subformId = "KPL_DOC_RECEIVE_SUBFORM";
	String SPECIAL_ADDITIONAL_SERVICE_POSTAL = SystemConstant.getConstant("SPECIAL_ADDITIONAL_SERVICE_POSTAL");
	String SPECIAL_ADDITIONAL_SERVICE_EMAIL = SystemConstant.getConstant("SPECIAL_ADDITIONAL_SERVICE_EMAIL");
	
	String FLAG_YES = SystemConstant.getConstant("FLAG_YES");
	
	private static transient Logger logger = Logger.getLogger(DocReceiveSubForm.class);
	@Override
	public void setProperties(HttpServletRequest request, Object appForm){
		ApplicationGroupDataM applicationGroup = ((ApplicationGroupDataM)appForm);
		PersonalInfoDataM personalInfo = applicationGroup.getPersonalInfo(PERSONAL_TYPE_APPLICANT);
		String personalId = personalInfo.getPersonalId();
		
		//Channel to receive approve letter section - checked = by mail
		String receiveKPLDocByMail = request.getParameter("KPL_DOC_RECEIVE");
		logger.debug("receiveKPLDocByMail = " + receiveKPLDocByMail);
		
		ApplicationDataM kplApp = applicationGroup.filterApplicationProductLifeCycle(PRODUCT_K_PERSONAL_LOAN);
		kplApp.setLetterChannel(receiveKPLDocByMail);
		
		SpecialAdditionalServiceDataM postalService = applicationGroup.getSpecialAdditionalServiceLifeCycleByPersonalId(personalId
				, PRODUCT_K_PERSONAL_LOAN, SPECIAL_ADDITIONAL_SERVICE_POSTAL);
		
		SpecialAdditionalServiceDataM emailService = applicationGroup.getSpecialAdditionalServiceLifeCycleByPersonalId(personalId
				, PRODUCT_K_PERSONAL_LOAN, SPECIAL_ADDITIONAL_SERVICE_EMAIL);
		
		if(FLAG_YES.equals(receiveKPLDocByMail)){
			if(!Util.empty(emailService) ){
				applicationGroup.removeSpecialAdditionalLifeCycleByPersonalId(personalId, PRODUCT_K_PERSONAL_LOAN,SPECIAL_ADDITIONAL_SERVICE_EMAIL);
				logger.debug("remove SPECIAL_ADDITIONAL_SERVICE_EMAIL :"+SPECIAL_ADDITIONAL_SERVICE_EMAIL);
				if(!Util.empty(kplApp)){
					kplApp.removeAdditionalServiceId(emailService.getServiceId());
				}
			}
			if(Util.empty(postalService) ){
				postalService = new SpecialAdditionalServiceDataM();
				String serviceId= GenerateUnique.generate(OrigConstant.SeqNames.ORIG_ADDITIONAL_SERVICE_PK);
				postalService.init(serviceId);
				postalService.setPersonalId(personalId);
				postalService.setServiceType(SPECIAL_ADDITIONAL_SERVICE_POSTAL);
				applicationGroup.addSpecialAdditionalService(postalService);
				logger.debug("add specialAdditionalService :"+SPECIAL_ADDITIONAL_SERVICE_POSTAL);
			}
			if(!Util.empty(kplApp)){
				kplApp.addAdditionalServiceId(postalService.getServiceId());
			}
		}else{
			if(!Util.empty(postalService) ){
				applicationGroup.removeSpecialAdditionalLifeCycleByPersonalId(personalId, PRODUCT_K_PERSONAL_LOAN,SPECIAL_ADDITIONAL_SERVICE_POSTAL);
				logger.debug("remove SPECIAL_ADDITIONAL_SERVICE_POSTAL :"+SPECIAL_ADDITIONAL_SERVICE_POSTAL);
				if(!Util.empty(kplApp)){
					kplApp.removeAdditionalServiceId(postalService.getServiceId());
				}
			}
			if(Util.empty(emailService) ){
				emailService = new SpecialAdditionalServiceDataM();
				String serviceId= GenerateUnique.generate(OrigConstant.SeqNames.ORIG_ADDITIONAL_SERVICE_PK);
				emailService.init(serviceId);
				emailService.setPersonalId(personalId);
				emailService.setServiceType(SPECIAL_ADDITIONAL_SERVICE_EMAIL);
				applicationGroup.addSpecialAdditionalService(emailService);
				logger.debug("add specialAdditionalService :"+SPECIAL_ADDITIONAL_SERVICE_EMAIL);
			}
			if(!Util.empty(kplApp)){
				kplApp.addAdditionalServiceId(emailService.getServiceId());
			}
		}
	}
	
	@SuppressWarnings({ "unchecked", "rawtypes" })
	@Override
	public HashMap validateForm(HttpServletRequest request, Object appForm){
		FormErrorUtil formError = new FormErrorUtil();
		return formError.getFormError();
	}
	
	@Override
	public boolean validateSubForm(HttpServletRequest request){
		return false;
	}
	
	@Override
	public String renderSubformFlag(HttpServletRequest request,Object objectForm)
	{
		String ACTION_TYPE_ENQUIRY = SystemConstant.getConstant("ACTION_TYPE_ENQUIRY");
		String ROLE_DE1_2 = SystemConstant.getConstant("ROLE_DE1_2");
		String ROLE_CA = SystemConstant.getConstant("ROLE_CA");
		String ROLE_VT = SystemConstant.getConstant("ROLE_VT");
		String KPL_SINGLE_TEMPLATE = SystemConstant.getConstant("KPL_SINGLE_TEMPLATE");
		String KPL_V2_TEMPLATE = SystemConstant.getConstant("KPL_V2_TEMPLATE");
		String renderFlag = MConstant.FLAG_N;
		ApplicationGroupDataM applicationGroup = (ApplicationGroupDataM)objectForm;
		//This subform will only render if application template or product is KPL
		logger.debug("renderSubformFlag - KPL - DocReceiveSubForm");
		FlowControlDataM flowControl = (FlowControlDataM) request.getSession().getAttribute(SessionControl.FlowControl);
		if(KPLUtil.isKPL(applicationGroup) && flowControl != null
		   //Defect#3260 not render if template = KPL_V2
		   && !KPL_SINGLE_TEMPLATE.equals(applicationGroup.getApplicationTemplate())
		   && !KPL_V2_TEMPLATE.equals(applicationGroup.getApplicationTemplate()))
		{
			//Defect#2977 - Add render from General Inquiry page
			if(ACTION_TYPE_ENQUIRY.equals(flowControl.getActionType()))
			{
				renderFlag = MConstant.FLAG_Y;
			}
			else if(ROLE_DE1_2.equals(flowControl.getRole()) 
					|| ROLE_CA.equals(flowControl.getRole()) 
					|| ROLE_VT.equals(flowControl.getRole()))
			{
				renderFlag = MConstant.FLAG_Y;
			}
		}		
		return renderFlag;
	}
	
}
