package com.eaf.orig.ulo.app.view.form.subform.product.kec;

import java.util.ArrayList;
import java.util.HashMap;

import javax.servlet.http.HttpServletRequest;

import org.apache.log4j.Logger;

import com.eaf.core.ulo.common.properties.SystemConstant;
import com.eaf.core.ulo.common.util.FormDisplayValueUtil;
import com.eaf.core.ulo.common.util.Util;
import com.eaf.orig.formcontrol.view.form.ORIGFormHandler;
import com.eaf.orig.shared.constant.OrigConstant;
import com.eaf.orig.shared.view.form.ORIGSubForm;
import com.eaf.orig.ulo.app.util.GenerateUnique;
import com.eaf.orig.ulo.model.app.ApplicationDataM;
import com.eaf.orig.ulo.model.app.ApplicationGroupDataM;
import com.eaf.orig.ulo.model.app.PersonalInfoDataM;
import com.eaf.orig.ulo.model.app.SpecialAdditionalServiceDataM;

@SuppressWarnings("serial")
public class AdditionalServiceSubForm extends ORIGSubForm{
	private static transient Logger logger = Logger.getLogger(AdditionalServiceSubForm.class);
	String PERSONAL_TYPE_APPLICANT = SystemConstant.getConstant("PERSONAL_TYPE_APPLICANT");
	String PRODUCT_K_EXPRESS_CASH =  SystemConstant.getConstant("PRODUCT_K_EXPRESS_CASH");
	String subformId ="KEC_ADDITIONAL_SERVICE_SUBFORM";
	String SPECIAL_ADDITIONAL_SERVICE_DUE_ALERT = SystemConstant.getConstant("SPECIAL_ADDITIONAL_SERVICE_DUE_ALERT");
	String SPECIAL_ADDITIONAL_SERVICE_POSTAL = SystemConstant.getConstant("SPECIAL_ADDITIONAL_SERVICE_POSTAL");
	
	String FLAG_YES = SystemConstant.getConstant("FLAG_YES");
	
	@Override
	public void setProperties(HttpServletRequest request, Object appForm) {
		ApplicationGroupDataM applicationGroup = ((ApplicationGroupDataM)appForm);	
		PersonalInfoDataM personalInfo = applicationGroup.getPersonalInfo(PERSONAL_TYPE_APPLICANT);
		String personalId = personalInfo.getPersonalId();
		
		String SERVICE_TYPE_DUE_ALERT = request.getParameter("SERVICE_TYPE_DUE_ALERT_"+PRODUCT_K_EXPRESS_CASH);
		String SERVICE_TYPE_POSTAL_FLAG = request.getParameter("SERVICE_TYPE_POSTAL_FLAG_"+PRODUCT_K_EXPRESS_CASH);
				
		logger.debug("KEC SERVICE_TYPE_DUE_ALERT >> "+SERVICE_TYPE_DUE_ALERT);
		logger.debug("KEC SERVICE_TYPE_POSTAL_FLAG >> "+SERVICE_TYPE_POSTAL_FLAG);

		ArrayList<ApplicationDataM> applications = applicationGroup.filterApplicationLifeCycle(PRODUCT_K_EXPRESS_CASH);
		
		// Spending Alert Service
		SpecialAdditionalServiceDataM postalService = applicationGroup.getSpecialAdditionalServiceLifeCycleByPersonalId(personalId
				, PRODUCT_K_EXPRESS_CASH, SPECIAL_ADDITIONAL_SERVICE_POSTAL);
		if(FLAG_YES.equals(SERVICE_TYPE_POSTAL_FLAG)){
			if(Util.empty(postalService) ){
				postalService = new SpecialAdditionalServiceDataM();
				String serviceId= GenerateUnique.generate(OrigConstant.SeqNames.ORIG_ADDITIONAL_SERVICE_PK);
				postalService.init(serviceId);
				postalService.setPersonalId(personalId);
				postalService.setServiceType(SPECIAL_ADDITIONAL_SERVICE_POSTAL);
				applicationGroup.addSpecialAdditionalService(postalService);
				logger.debug("add specialAdditionalService :"+SPECIAL_ADDITIONAL_SERVICE_POSTAL);
			}
			if(!Util.empty(applications)){
				for(ApplicationDataM application : applications){
					application.addAdditionalServiceId(postalService.getServiceId());
				}
			}
		}else{
			if(!Util.empty(postalService) ){
				applicationGroup.removeSpecialAdditionalLifeCycleByPersonalId(personalId, PRODUCT_K_EXPRESS_CASH,SPECIAL_ADDITIONAL_SERVICE_POSTAL);
				logger.debug("remove SPECIAL_ADDITIONAL_SERVICE_POSTAL :"+SPECIAL_ADDITIONAL_SERVICE_POSTAL);
				if(!Util.empty(applications)){
					for(ApplicationDataM application : applications){
						application.removeAdditionalServiceId(postalService.getServiceId());
					}
				}
			}
		}
				
		// Due Alert service
		SpecialAdditionalServiceDataM dueAlertService = applicationGroup.getSpecialAdditionalServiceLifeCycleByPersonalId(personalId
				, PRODUCT_K_EXPRESS_CASH, SPECIAL_ADDITIONAL_SERVICE_DUE_ALERT);
		if(FLAG_YES.equals(SERVICE_TYPE_DUE_ALERT)){
			if(Util.empty(dueAlertService) ){
				dueAlertService = new SpecialAdditionalServiceDataM();
				String serviceId = GenerateUnique.generate(OrigConstant.SeqNames.ORIG_ADDITIONAL_SERVICE_PK);
				dueAlertService.init(serviceId);
				dueAlertService.setPersonalId(personalId);
				dueAlertService.setServiceType(SPECIAL_ADDITIONAL_SERVICE_DUE_ALERT);
				applicationGroup.addSpecialAdditionalService(dueAlertService);
				logger.debug("add specialAdditionalService :"+SPECIAL_ADDITIONAL_SERVICE_DUE_ALERT);
			}
			if(!Util.empty(applications)){
				for(ApplicationDataM application : applications){
					application.addAdditionalServiceId(dueAlertService.getServiceId());
				}
			}
		}else{
			if(!Util.empty(dueAlertService) ){
				applicationGroup.removeSpecialAdditionalLifeCycleByPersonalId(personalId, PRODUCT_K_EXPRESS_CASH,SPECIAL_ADDITIONAL_SERVICE_DUE_ALERT);
				logger.debug("remove specialAdditionalService :"+SPECIAL_ADDITIONAL_SERVICE_DUE_ALERT);
				if(!Util.empty(applications)){
					for(ApplicationDataM application : applications){
						application.removeAdditionalServiceId(dueAlertService.getServiceId());
					}
				}
			}
		}
		
	}
	@SuppressWarnings({ "rawtypes", "unchecked" })
	@Override
	public HashMap validateForm(HttpServletRequest request, Object appForm) {
		return null;
	}
	
	@Override
	public void displayValueForm(HttpServletRequest request, Object objectForm) {
//		ORIGFormHandler formHandler = (ORIGFormHandler)request.getSession().getAttribute("ORIGForm");
//		ApplicationGroupDataM applicationGroup = (ApplicationGroupDataM)formHandler.getObjectForm();
		ApplicationGroupDataM applicationGroup = (ApplicationGroupDataM)objectForm;
		PersonalInfoDataM personalInfo = applicationGroup.getPersonalInfo(PERSONAL_TYPE_APPLICANT);
		String personalId = personalInfo.getPersonalId();
		ArrayList<ApplicationDataM> applications = applicationGroup.filterApplicationLifeCycle(PRODUCT_K_EXPRESS_CASH);
		// Due Alert service
		SpecialAdditionalServiceDataM dueAlertService = applicationGroup.getSpecialAdditionalServiceLifeCycleByPersonalId(personalId
				, PRODUCT_K_EXPRESS_CASH, SPECIAL_ADDITIONAL_SERVICE_DUE_ALERT);
		FormDisplayValueUtil formValue = new FormDisplayValueUtil(formId,formLevel,roleId,subformId,applicationGroup,request);
		logger.debug("formValue.getValue >> "+formValue.getValue("SERVICE_TYPE_DUE_ALERT","SERVICE_TYPE_DUE_ALERT_"+PRODUCT_K_EXPRESS_CASH,""));
		if(FLAG_YES.equals(formValue.getValue("SERVICE_TYPE_DUE_ALERT","SERVICE_TYPE_DUE_ALERT_"+PRODUCT_K_EXPRESS_CASH,""))){
			if(Util.empty(dueAlertService) ){
				logger.debug("Open >>>>");
				dueAlertService = new SpecialAdditionalServiceDataM();
				String serviceId = GenerateUnique.generate(OrigConstant.SeqNames.ORIG_ADDITIONAL_SERVICE_PK);
				dueAlertService.init(serviceId);
				dueAlertService.setPersonalId(personalId);
				dueAlertService.setServiceType(SPECIAL_ADDITIONAL_SERVICE_DUE_ALERT);
				applicationGroup.addSpecialAdditionalService(dueAlertService);
				logger.debug("add specialAdditionalService :"+SPECIAL_ADDITIONAL_SERVICE_DUE_ALERT);
			}
			if(!Util.empty(applications)){
				for(ApplicationDataM application : applications){
					application.addAdditionalServiceId(dueAlertService.getServiceId());
				}
			}
		}
	};
	@Override
	public boolean validateSubForm(HttpServletRequest request) {
		return false;
	}
}
