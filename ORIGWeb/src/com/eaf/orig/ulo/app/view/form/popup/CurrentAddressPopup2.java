package com.eaf.orig.ulo.app.view.form.popup;

import java.util.ArrayList;
import java.util.HashMap;

import javax.servlet.http.HttpServletRequest;

import org.apache.log4j.Logger;

import com.eaf.core.ulo.common.display.ElementInf;
import com.eaf.core.ulo.common.message.MessageErrorUtil;
import com.eaf.core.ulo.common.model.FieldElement;
import com.eaf.core.ulo.common.properties.FormControl;
import com.eaf.core.ulo.common.properties.ImplementControl;
import com.eaf.core.ulo.common.properties.SystemConstant;
import com.eaf.core.ulo.common.util.FormDisplayValueUtil;
import com.eaf.core.ulo.common.util.FormErrorUtil;
import com.eaf.core.ulo.common.util.Util;
import com.eaf.j2ee.pattern.control.ProcessForm;
import com.eaf.orig.shared.view.form.ORIGSubForm;
import com.eaf.orig.ulo.constant.MConstant;
import com.eaf.orig.ulo.control.address.util.DisplayAddressUtil;
import com.eaf.orig.ulo.control.util.PersonalInfoUtil;
import com.eaf.orig.ulo.formcontrol.view.form.ORIGFormHandler;
import com.eaf.orig.ulo.model.app.AddressDataM;
import com.eaf.orig.ulo.model.app.ApplicationGroupDataM;
import com.eaf.orig.ulo.model.app.PersonalInfoDataM;
import com.eaf.orig.ulo.pl.app.view.webaction.AddressUtil;

@SuppressWarnings("serial")
public class CurrentAddressPopup2 extends ORIGSubForm{
	private static transient Logger logger = Logger.getLogger(CurrentAddressPopup2.class);
	String subformId = "CURRENT_ADDRESS_POPUP_2";
	String ADDRESS_TYPE_CURRENT = SystemConstant.getConstant("ADDRESS_TYPE_CURRENT");
	String PERSONAL_TYPE = SystemConstant.getConstant("PERSONAL_TYPE_APPLICANT");
	String PERSONAL_RELATION_APPLICATION_LEVEL = SystemConstant.getConstant("PERSONAL_RELATION_APPLICATION_LEVEL");
	String DECISION_FINAL_DECISION_REJECT = SystemConstant.getConstant("DECISION_FINAL_DECISION_REJECT");
	String ROLE_DE2 = SystemConstant.getConstant("ROLE_DE2");
	String COMPLETE_FLAG_Y = SystemConstant.getConstant("COMPLETE_FLAG_Y");
	String COMPLETE_FLAG_N = SystemConstant.getConstant("COMPLETE_FLAG_N");
	@SuppressWarnings("unchecked")
	@Override
	public void setProperties(HttpServletRequest request, Object appForm) {
		logger.debug("CurrentAddressPopup1.setProperties");
		String PERSONAL_TYPE = getSubformData("PERSONAL_TYPE");
		logger.debug("PERSONAL_TYPE : "+PERSONAL_TYPE);
		HashMap<String,AddressDataM> hashAddress = (HashMap<String,AddressDataM>)appForm;
		AddressDataM address = hashAddress.get(ADDRESS_TYPE_CURRENT);		
		ArrayList<ElementInf> elements = ImplementControl.getElements(MConstant.IMPLEMENT_TYPE.ADDRESS_MAILING);
		logger.debug("element >> "+elements.size());
		for(ElementInf element:elements){
			if(MConstant.FLAG_Y.equals(element.renderElementFlag(request, PERSONAL_TYPE))){
				element.processElement(request, address);
			}
		}	
		DisplayAddressUtil.setAddressLine(address);
	}

	@SuppressWarnings({ "unchecked", "rawtypes" })
	@Override
	public HashMap validateForm(HttpServletRequest request, Object appForm) {
		logger.debug("subformId >> "+subformId);
//		String roleId = FormControl.getFormRoleId(request);
		FormErrorUtil formError = new FormErrorUtil();
		try{
			ApplicationGroupDataM applicationGroup = ORIGFormHandler.getObjectForm(request);
			/*String PERSONAL_TYPE = getSubformData("PERSONAL_TYPE");
			PersonalInfoDataM personalInfo = applicationGroup.getPersonalInfo(PERSONAL_TYPE);*/
			PersonalInfoDataM personalInfo = PersonalInfoUtil.getPersonalInfoObjectForm(request);
			logger.debug("processForm : "+processForm);
			if(ProcessForm.SUP_APPLICANT_VALIDATE.equals(processForm)){
				Object masterObjectForm = FormControl.getMasterObjectForm(request);
				if(masterObjectForm instanceof ApplicationGroupDataM){
//					ApplicationGroupDataM applicationGroup = (ApplicationGroupDataM)masterObjectForm;
					String personalId = getSubformData("PERSONAL_ID");
					logger.debug("personalId : "+personalId);
					personalInfo = applicationGroup.getPersonalInfoById(personalId);
					if(Util.empty(personalInfo)){
						personalInfo = new PersonalInfoDataM();
					}
				}
			}
//			
//			ApplicationDataM application = applicationGroup.getApplication(personalInfo.getPersonalId(),
//					personalInfo.getPersonalType(), PERSONAL_RELATION_APPLICATION_LEVEL);
//			if(Util.empty(application)){
//				application = new ApplicationDataM();
//			}
			//FUT Defect : 784 #Change condition to validate
//			if(DECISION_FINAL_DECISION_REJECT.equals(application.getFinalAppDecision()) && ADDRESS_TYPE_CURRENT.equals(personalInfo.getMailingAddress())){
			String personalInfoType = PERSONAL_TYPE;
			if(!Util.empty(personalInfo.getPersonalType())){
				personalInfoType = personalInfo.getPersonalType();
			}
			HashMap<String,AddressDataM> hashAddress = (HashMap<String,AddressDataM>)appForm;
			AddressDataM address = hashAddress.get(ADDRESS_TYPE_CURRENT);	
			if(SystemConstant.lookup("JOBSTATE_REJECT",applicationGroup.getJobState())&&ADDRESS_TYPE_CURRENT.equals(personalInfo.getMailingAddress())){
				ArrayList<ElementInf> elements = ImplementControl.getElements(MConstant.IMPLEMENT_TYPE.ADDRESS_MAILING);
				logger.debug("element >> "+elements.size());
				for(ElementInf element:elements){
					if(MConstant.FLAG_Y.equals(element.renderElementFlag(request, personalInfoType))){
						element.setObjectRequest(subformId);
						formError.addAll(element.validateElement(request, hashAddress));
					}
				}
			}else if(!SystemConstant.lookup("JOBSTATE_REJECT",applicationGroup.getJobState())){
				ArrayList<ElementInf> elements = ImplementControl.getElements(MConstant.IMPLEMENT_TYPE.ADDRESS_MAILING);
				logger.debug("element >> "+elements.size());
				for(ElementInf element:elements){
					if(MConstant.FLAG_Y.equals(element.renderElementFlag(request, personalInfoType))){
						element.setObjectRequest(subformId);
						formError.addAll(element.validateElement(request, hashAddress));
					}
				}
			}
			formError.mandatory(subformId, "MATCHES_ADDRESS", address, request);
//			HashMap<String,AddressDataM> hashAddress = (HashMap<String,AddressDataM>)appForm;
//			AddressDataM address = hashAddress.get(ADDRESS_TYPE_CURRENT);
//			#rawi comment for change logic set edit to after process form
//			if(Util.empty(formError.getFormError())){
//				address.setEditFlag(COMPLETE_FLAG_Y);
//			}else{
//				address.setEditFlag(COMPLETE_FLAG_N);
//			}
//			
		}catch(Exception e){
			logger.fatal("ERROR ",e);
		}
		return formError.getFormError();
	}

	@Override
	public boolean validateSubForm(HttpServletRequest request) {
		return false;
	}
	
//	@Override
//	public ArrayList<AuditDataM> auditForm(HttpServletRequest request,Object objectForm, Object lastObjectForm) {	
//		HashMap<String,AddressDataM> hashAddress = (HashMap<String,AddressDataM>)objectForm;
//		AddressDataM addressHome = hashAddress.get(ADDRESS_TYPE_CURRENT);
//		if(null == addressHome){
//			addressHome = new AddressDataM();
//		}
//		HashMap<String,AddressDataM> hashlastAddress = (HashMap<String,AddressDataM>)objectForm;
//		AddressDataM lastAddress = hashAddress.get(ADDRESS_TYPE_CURRENT);
//		if(null == lastAddress){
//			lastAddress = new AddressDataM();
//		}
//
//		AuditFormUtil auditFormUtil = new AuditFormUtil();
//		String subformId = getSubFormID();
//		auditFormUtil.auditForm(subformId,"ADDRESS_ID",addressHome,lastAddress,request);
//
//		return auditFormUtil.getAuditForm();
//	}	

	@Override
	public void displayValueForm(HttpServletRequest request, Object objectForm) {
		ApplicationGroupDataM applicationGroup = (ApplicationGroupDataM)objectForm;
		String addressId = getSubformData("ADDRESS_ID");
		String personalType = getSubformData("PERSONAL_TYPE");
		logger.debug("addressId >> "+addressId);
		FormDisplayValueUtil formValue = new FormDisplayValueUtil(formId,formLevel,roleId,subformId,applicationGroup,request);
			
		PersonalInfoDataM personalInfo = applicationGroup.getPersonalInfo(personalType);
		ArrayList<ElementInf> elements = ImplementControl.getElements(MConstant.IMPLEMENT_TYPE.ADDRESS_MAILING);
		logger.debug("element >> "+elements.size());
		for(ElementInf element:elements){
			if(MConstant.FLAG_Y.equals(element.renderElementFlag(request, personalType))){
				element.displayValueElement(request, personalInfo,formValue);
			}
		}
	}
	
	
	@Override
	public ArrayList<FieldElement> elementForm(HttpServletRequest request,Object objectForm) {
		ArrayList<FieldElement> fieldElements = new ArrayList<FieldElement>();
		
		ApplicationGroupDataM applicationGroup = (ApplicationGroupDataM)objectForm;
		String addressId = getSubformData("ADDRESS_ID");
		String personalType = getSubformData("PERSONAL_TYPE");
		logger.debug("addressId >> "+addressId);	
		PersonalInfoDataM personalInfo = applicationGroup.getPersonalInfo(personalType);
		ArrayList<ElementInf> elements = ImplementControl.getElements(MConstant.IMPLEMENT_TYPE.ADDRESS_MAILING);
		logger.debug("element >> "+elements.size());
		for(ElementInf element:elements){
			if(MConstant.FLAG_Y.equals(element.renderElementFlag(request, personalType))){
				ArrayList<FieldElement> fieldElementAddress = element.elementForm(request, personalInfo);
				if(!Util.empty(fieldElementAddress)){
					fieldElements.addAll(fieldElementAddress);
				}
			}
		}
		return fieldElements;
	}
}
