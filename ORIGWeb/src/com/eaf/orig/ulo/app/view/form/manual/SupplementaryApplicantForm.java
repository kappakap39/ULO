package com.eaf.orig.ulo.app.view.form.manual;

import java.util.ArrayList;
import java.util.Iterator;

import javax.servlet.http.HttpServletRequest;

import org.apache.log4j.Logger;

import com.eaf.core.ulo.common.properties.SystemConstant;
import com.eaf.core.ulo.common.util.Util;
import com.eaf.j2ee.pattern.control.FormAction;
import com.eaf.j2ee.pattern.control.FormHelper;
import com.eaf.orig.shared.constant.OrigConstant;
import com.eaf.orig.shared.view.form.ORIGSubForm;
import com.eaf.orig.ulo.app.util.GenerateUnique;
import com.eaf.orig.ulo.app.view.form.displaymode.ConsentModelDisplayMode;
import com.eaf.orig.ulo.constant.MConstant;
import com.eaf.orig.ulo.control.util.ApplicationUtil;
import com.eaf.orig.ulo.control.util.PersonalInfoUtil;
import com.eaf.orig.ulo.formcontrol.view.form.ORIGFormHandler;
import com.eaf.orig.ulo.model.app.ApplicationDataM;
import com.eaf.orig.ulo.model.app.ApplicationGroupDataM;
import com.eaf.orig.ulo.model.app.PaymentMethodDataM;
import com.eaf.orig.ulo.model.app.PersonalApplicationInfoDataM;
import com.eaf.orig.ulo.model.app.PersonalInfoDataM;
import com.eaf.orig.ulo.model.app.PersonalRelationDataM;
import com.eaf.orig.ulo.model.app.SpecialAdditionalServiceDataM;

public class SupplementaryApplicantForm extends FormHelper implements FormAction {
	private static transient Logger logger = Logger.getLogger(SupplementaryApplicantForm.class);
	String PERSONAL_TYPE_SUPPLEMENTARY = SystemConstant.getConstant("PERSONAL_TYPE_SUPPLEMENTARY");
	String PERSONAL_RELATION_APPLICATION_LEVEL = SystemConstant.getConstant("PERSONAL_RELATION_APPLICATION_LEVEL");
	String APPLICATION_TYPE_ADD_SUP = SystemConstant.getConstant("APPLICATION_TYPE_ADD_SUP");
	String FUNCTION_PERSONAL_ADD = SystemConstant.getConstant("FUNCTION_PERSONAL_ADD");
	private String COMPLETE_FLAG_FIRST_LOAD_PERSONAL = SystemConstant.getConstant("COMPLETE_FLAG_FIRST_LOAD_PERSONAL");
	@Override
	public Object getObjectForm() {		
		String PERSONAL_SEQ = getRequestData("REQ_PERSONAL_SEQ");
		String personalType = getRequestData("REQ_PERSONAL_TYPE");
		String PERSONAL_ID = getRequestData("REQ_PERSONAL_ID");
		String FUNTION_SUPPLEMENT = request.getParameter("FUNTION_SUPPLEMENT");
		logger.debug("PERSONAL_SEQ >> " + PERSONAL_SEQ);
		logger.debug("personalType >> " + personalType);
		logger.debug("PERSONAL_ID >> " + PERSONAL_ID);	
		logger.debug("FUNTION_SUPPLEMENT >> " + FUNTION_SUPPLEMENT);
		PersonalInfoDataM personalInfo = null;
		PersonalApplicationInfoDataM personalApplicationInfo = new PersonalApplicationInfoDataM();				
		ORIGFormHandler ORIGForm = (ORIGFormHandler) request.getSession().getAttribute("ORIGForm");
		ApplicationGroupDataM applicationGroup = ORIGForm.getObjectForm();
		if(FUNCTION_PERSONAL_ADD.equals(FUNTION_SUPPLEMENT)){
			 ArrayList<PersonalInfoDataM> personalInfos = applicationGroup.getPersonalInfos(PERSONAL_TYPE_SUPPLEMENTARY);
			 for(PersonalInfoDataM personal:personalInfos){
				 if(COMPLETE_FLAG_FIRST_LOAD_PERSONAL.equals(personal.getCompleteData())){
				 personalInfo = personal;
				 break;
				 }
			 }
		}else{
			 personalInfo = applicationGroup.getPersonalById(PERSONAL_ID);
		}
		logger.debug("personalInfo >> " + personalInfo);
		if (null == personalInfo) {
			personalInfo = new PersonalInfoDataM();
			personalInfo.setPersonalType(personalType);
			personalInfo.setSeq(OrigApplicationForm.getPersonalSeq(personalType,applicationGroup));
		}
		String personalId = personalInfo.getPersonalId();
		if (Util.empty(personalId)) {
			personalId = GenerateUnique.generate(OrigConstant.SeqNames.ORIG_PERSONAL_INFO_PK);
		}
		personalInfo.setPersonalId(personalId);
		ArrayList<PersonalRelationDataM> personalRelations = personalInfo.getPersonalRelations();
		if(null == personalRelations){
			personalRelations = new ArrayList<PersonalRelationDataM>();
			personalInfo.setPersonalRelations(personalRelations);
		}
		logger.debug("personalInfo.getPersonalId >> "+personalInfo.getPersonalId());
		logger.debug("personalInfo.getPersonalType >> "+personalInfo.getPersonalType());
		logger.debug("personalInfo.getSeq >> "+personalInfo.getSeq());
		logger.debug("personalRelations >> "+personalRelations);
		
		personalApplicationInfo.setApplicationType(applicationGroup.getApplicationType());
		
		ArrayList<ApplicationDataM> applications = applicationGroup.filterApplicationPersonal(personalInfo.getPersonalId(),PERSONAL_RELATION_APPLICATION_LEVEL);
		personalApplicationInfo.setPersonalInfo(personalInfo);
		personalApplicationInfo.setApplications(applications);		
		logger.debug("Applications size : " + applications.size());
		
		personalApplicationInfo.setPaymentMethods(applicationGroup.getListPaymentMethodByPersonalId(personalId));
		
		personalApplicationInfo.setSpecialAdditionalServices(applicationGroup.getListSpecialAdditionalServiceByPersonalId(personalId));
		
		logger.debug("personalApplicationInfo.getPaymentMethods() >> "+personalApplicationInfo.getPaymentMethods());
		logger.debug("personalApplicationInfo.getSpecialAdditionalServices() >> "+personalApplicationInfo.getSpecialAdditionalServices());
		
		if(Util.empty(personalInfo.getCidType())){
			ConsentModelDisplayMode docCheck = new ConsentModelDisplayMode();
			String doucumentCodeNonThai = SystemConstant.getConstant("DOCUMENT_TYPE_NON_THAI_NATIONALITY");
			String doucumentCodeThai = SystemConstant.getConstant("DOCUMENT_TYPE_THAI_NATIONALITY");
			String doucumentCodePassport = SystemConstant.getConstant("DOCUMENT_TYPE_PASSPORT");
			String doucumentCodeAlien = SystemConstant.getConstant("DOCUMENT_TYPE_ALIEN_ID");
			boolean checkNonThai= docCheck.checkReceiveDoc(applicationGroup,personalInfo,doucumentCodeNonThai);
			boolean checkThai= docCheck.checkReceiveDoc(applicationGroup,personalInfo,doucumentCodeThai);
			boolean checkPassport= docCheck.checkReceiveDoc(applicationGroup,personalInfo,doucumentCodePassport);
			boolean checkAlien= docCheck.checkReceiveDoc(applicationGroup,personalInfo,doucumentCodeAlien);
			if(checkNonThai){
				personalInfo.setCidType(SystemConstant.getConstant("CIDTYPE_NON_THAI_NATINALITY"));
			}else if(checkAlien){
				personalInfo.setCidType(SystemConstant.getConstant("CIDTYPE_NON_THAI_NATINALITY"));
			}else if(checkThai){
				personalInfo.setCidType(SystemConstant.getConstant("CIDTYPE_IDCARD"));
			}else if(checkPassport){
				personalInfo.setCidType(SystemConstant.getConstant("CIDTYPE_PASSPORT"));
			}else{
				personalInfo.setCidType(SystemConstant.getConstant("CIDTYPE_IDCARD"));
			}
		}
		return personalApplicationInfo;
	}

	@Override
	public String processForm() {
		ORIGFormHandler ORIGForm = (ORIGFormHandler) request.getSession().getAttribute("ORIGForm");
		ApplicationGroupDataM applicationGroup = ORIGForm.getObjectForm();
		ArrayList<PersonalInfoDataM> personalInfos = applicationGroup.getPersonalInfos();
		if(null == personalInfos){
			personalInfos = new ArrayList<PersonalInfoDataM>();
			applicationGroup.setPersonalInfos(personalInfos);
		}
		ArrayList<ApplicationDataM> applications = applicationGroup.getApplications();
		if(null == applications){
			applications = new ArrayList<ApplicationDataM>();
			applicationGroup.setApplications(applications);
		}
		int lifeCycle = applicationGroup.getMaxLifeCycle();
		logger.debug("lifeCycle >> "+lifeCycle);
		
		PersonalApplicationInfoDataM personalApplicationInfo = (PersonalApplicationInfoDataM) objectForm;
		ApplicationUtil.clearNotMatchApplicationRelation(personalApplicationInfo);
		PersonalInfoDataM supPersonalInfo = personalApplicationInfo.getPersonalInfo();
		ArrayList<ApplicationDataM> supApplications = personalApplicationInfo.getApplications();
		String personalId = supPersonalInfo.getPersonalId();
		String personalType = supPersonalInfo.getPersonalType();
		int personalSeq = supPersonalInfo.getSeq();
		logger.debug("personalId >> "+personalId);
		logger.debug("personalType >> "+personalType);
		logger.debug("personalSeq >> "+personalSeq);
		int personalIndex = -1;
		try {
			if (Util.empty(personalId)) {
				personalId = GenerateUnique.generate(OrigConstant.SeqNames.ORIG_PERSONAL_INFO_PK);
				supPersonalInfo.setPersonalId(personalId);
			} else {
				personalIndex = applicationGroup.getPersonalIndex(personalId);
			}
		} catch (Exception e) {
			logger.fatal("ERROR ", e);
		}
		logger.debug("PERSONAL_ID >> "+personalId);
		logger.debug("PERSONAL_INDEX >> "+personalIndex);
		if(personalIndex == -1){
			personalInfos.add(supPersonalInfo);
		}else{
			personalInfos.set(personalIndex,supPersonalInfo);
		}		
				
		ArrayList<PaymentMethodDataM> paymentMethods = applicationGroup.getPaymentMethods();
		ArrayList<PaymentMethodDataM> supPaymentMethods = personalApplicationInfo.getPaymentMethods();
		logger.debug("supPaymentMethods >> "+supPaymentMethods);
		if(null != supPaymentMethods){
			for (PaymentMethodDataM paymentMethod : supPaymentMethods) {
				logger.debug("paymentMethod >> "+paymentMethod);
				String paymentMethodId = paymentMethod.getPaymentMethodId();
				logger.debug("paymentMethodId >> "+paymentMethodId);
				int paymentMethodIndex = applicationGroup.getPaymentMethodIndex(paymentMethodId);
				logger.debug("paymentMethodIndex >> "+paymentMethodIndex);
				if(paymentMethodIndex == -1){
					paymentMethods.add(paymentMethod);
				}else{
					paymentMethods.set(paymentMethodIndex,paymentMethod);
				}	
			}
		}
		logger.debug("paymentMethods >> "+paymentMethods);
		
		ArrayList<SpecialAdditionalServiceDataM> specialAdditionalServices = applicationGroup.getSpecialAdditionalServices();
		ArrayList<SpecialAdditionalServiceDataM> supSpecialAdditionalServices = personalApplicationInfo.getSpecialAdditionalServices();
		if(null != supSpecialAdditionalServices){
			for (SpecialAdditionalServiceDataM specialAdditionalService : supSpecialAdditionalServices) {
				String serviceId = specialAdditionalService.getServiceId();
				int specialAdditionalServiceIndex = applicationGroup.getSpecialAdditionalServicesIndex(serviceId);
				if(specialAdditionalServiceIndex == -1){
					specialAdditionalServices.add(specialAdditionalService);
				}else{
					specialAdditionalServices.set(specialAdditionalServiceIndex,specialAdditionalService);
				}
			}
		}
						
//		Set Application Item
		if(!Util.empty(supApplications)){
			for (ApplicationDataM supApplication : supApplications) {
				int applicationIndex = applicationGroup.getApplicationIndex(supApplication.getApplicationRecordId());
				if (applicationIndex == -1) {
					applications.add(supApplication);
				}else{
					applications.set(applicationIndex, supApplication);
				}
			}
		}
		
		ArrayList<String> uniqueSupApplicationIds = getUniqueApplicationIds(supApplications);
		ArrayList<String> currentSupApplicationIds = applicationGroup.filterPersonalApplicationIdLifeCycle(personalId,PERSONAL_RELATION_APPLICATION_LEVEL);
		logger.debug("uniqueSupApplicationIds >> "+uniqueSupApplicationIds);
		logger.debug("currentSupApplicationIds >> "+currentSupApplicationIds);
		if(!Util.empty(applications)){
			Iterator<ApplicationDataM> iterator = applications.iterator();
			while(iterator.hasNext()){
				ApplicationDataM application = iterator.next();
				String applicationRecordId = application.getApplicationRecordId();
				if(currentSupApplicationIds.contains(applicationRecordId) && !uniqueSupApplicationIds.contains(applicationRecordId)
						&& lifeCycle == application.getLifeCycle()){						
					iterator.remove();
				}
			}
		}			
		
		ApplicationUtil.clearNotMatchApplicationRelation(applicationGroup);		
		PersonalInfoUtil.defaultPersonalIdOfImage(applicationGroup,personalId,personalType,personalSeq);	
		return null;
		
		
	}
				
	private ArrayList<String> getUniqueApplicationIds(ArrayList<ApplicationDataM> supApplications){
		ArrayList<String> uniqueApplicationIds = new ArrayList<String>();
		if(!Util.empty(supApplications)){
			for (ApplicationDataM application : supApplications) {
				uniqueApplicationIds.add(application.getApplicationRecordId());
			}
		}
		return uniqueApplicationIds;
	}
	
	@Override
	public ArrayList<ORIGSubForm> filterSubform(HttpServletRequest request,ArrayList<ORIGSubForm> subforms, Object objectForm) {
		logger.debug("filterSubform...");
		ArrayList<ORIGSubForm> filterSubforms = new ArrayList<ORIGSubForm>();
		if(!Util.empty(subforms)){
			for (ORIGSubForm origSubForm : subforms) {
				String subformId = origSubForm.getSubFormID();
				String renderSubformFlag = origSubForm.renderSubformFlag(request,objectForm);
				logger.debug("[subformId] "+subformId+" renderSubformFlag >> "+renderSubformFlag);
				if(MConstant.FLAG.YES.equals(renderSubformFlag)){
					filterSubforms.add(origSubForm);
				}
			}
		}
		return filterSubforms;
	}
	
}
