package com.eaf.orig.ulo.app.view.form.subform;

import java.util.ArrayList;
import java.util.HashMap;

import javax.servlet.http.HttpServletRequest;

import org.apache.log4j.Logger;

import com.eaf.core.ulo.common.display.FormatUtil;
import com.eaf.core.ulo.common.message.MessageErrorUtil;
import com.eaf.core.ulo.common.model.AuditDataM;
import com.eaf.core.ulo.common.model.FieldElement;
import com.eaf.core.ulo.common.properties.FormControl;
import com.eaf.core.ulo.common.properties.SystemConstant;
import com.eaf.core.ulo.common.util.AuditFormUtil;
import com.eaf.core.ulo.common.util.FormDisplayValueUtil;
import com.eaf.core.ulo.common.util.FormErrorUtil;
import com.eaf.core.ulo.common.util.Util;
import com.eaf.orig.shared.constant.OrigConstant;
import com.eaf.orig.shared.view.form.ORIGSubForm;
import com.eaf.orig.ulo.app.util.GenerateUnique;
import com.eaf.orig.ulo.app.view.form.sensitive.util.CompareSensitiveFieldUtil;
import com.eaf.orig.ulo.model.app.AddressDataM;
import com.eaf.orig.ulo.model.app.ApplicationGroupDataM;
import com.eaf.orig.ulo.model.app.PersonalInfoDataM;
import com.eaf.orig.ulo.model.compare.CompareDataM;

@SuppressWarnings("serial")
public class PhoneNoSubForm1 extends ORIGSubForm{
	private static transient Logger logger = Logger.getLogger(PhoneNoSubForm1.class);
	String subformId = "PHONE_NO_SUBFORM_1";
	private String PERSONAL_TYPE = SystemConstant.getConstant("PERSONAL_TYPE_APPLICANT");	
	String ADDRESS_TYPE_CURRENT = SystemConstant.getConstant("ADDRESS_TYPE_CURRENT");
	String ADDRESS_TYPE_WORK = SystemConstant.getConstant("ADDRESS_TYPE_WORK");
	ArrayList<String> ROLE_VALIDATE_PHONE_NO= SystemConstant.getArrayListConstant("ROLE_VALIDATE_PHONE_NO");
	
	@Override
	public void setProperties(HttpServletRequest request, Object appForm) {	
		ApplicationGroupDataM applicationGroup = ((ApplicationGroupDataM)appForm);		
		PersonalInfoDataM personalInfo = applicationGroup.getPersonalInfo(PERSONAL_TYPE);		
		AddressDataM currentAddress = personalInfo.getAddress(ADDRESS_TYPE_CURRENT);
		if(null == currentAddress){
			String addressId = GenerateUnique.generate(OrigConstant.SeqNames.ORIG_PERSONAL_ADDRESS_PK);
			currentAddress = new AddressDataM();
			currentAddress.setAddressId(addressId);
			currentAddress.setAddressType(ADDRESS_TYPE_CURRENT);
			personalInfo.addAddress(currentAddress);
		}		
		AddressDataM workAddress = personalInfo.getAddress(ADDRESS_TYPE_WORK);
		if(null == workAddress){
			String addressId = GenerateUnique.generate(OrigConstant.SeqNames.ORIG_PERSONAL_ADDRESS_PK);
			workAddress = new AddressDataM();
			workAddress.setAddressId(addressId);
			workAddress.setAddressType(ADDRESS_TYPE_WORK);
			personalInfo.addAddress(workAddress);
		}	
		
		String MOBILE = request.getParameter("MOBILE");
		String EMAIL = request.getParameter("EMAIL");
		String CURRENT_PHONE = request.getParameter("PHONE1_"+ADDRESS_TYPE_CURRENT);
		String CURRENT_PHONE_EXT = request.getParameter("EXT1_"+ADDRESS_TYPE_CURRENT);
		String OFFICE_PHONE = request.getParameter("PHONE1_"+ADDRESS_TYPE_WORK);
		String OFFICE_PHONE_EXT = request.getParameter("EXT1_"+ADDRESS_TYPE_WORK);
		
		logger.debug("MOBILE >>"+MOBILE);	
		logger.debug("CURRENT_PHONE >>"+CURRENT_PHONE);	
		logger.debug("CURRENT_PHONE_EXT >>"+CURRENT_PHONE_EXT);	
		logger.debug("OFFICE_PHONE >>"+OFFICE_PHONE);	
		logger.debug("OFFICE_PHONE_EXT >>"+OFFICE_PHONE_EXT);	
		logger.debug("EMAIL >>"+EMAIL);
		personalInfo.setMobileNo(FormatUtil.removeDash(MOBILE));
		personalInfo.setEmailPrimary(EMAIL);
		currentAddress.setPhone1(FormatUtil.removeDash(CURRENT_PHONE));
		currentAddress.setExt1(CURRENT_PHONE_EXT);
		workAddress.setPhone1(FormatUtil.removeDash(OFFICE_PHONE));
		workAddress.setExt1(OFFICE_PHONE_EXT);
		
	}

	@SuppressWarnings({ "unchecked", "rawtypes" })
	@Override
	public HashMap validateForm(HttpServletRequest request, Object appForm) {
		String roleId = FormControl.getFormRoleId(request);		
		FormErrorUtil formError = new FormErrorUtil();				
		ApplicationGroupDataM applicationGroup = ((ApplicationGroupDataM)appForm);
		logger.debug("subformId >> "+subformId);		
		PersonalInfoDataM personalInfo = applicationGroup.getPersonalInfo(PERSONAL_TYPE);			
		AddressDataM currentAddress = personalInfo.getAddress(ADDRESS_TYPE_CURRENT);	
		AddressDataM workAddress = personalInfo.getAddress(ADDRESS_TYPE_WORK);		
		if(ROLE_VALIDATE_PHONE_NO.contains(roleId)){
//			if(Util.empty(personalInfo.getMobileNo()) && Util.empty(currentAddress.getPhone1())  
//					&& Util.empty(currentAddress.getExt1()) && Util.empty(workAddress.getPhone1())
//					&& Util.empty(workAddress.getPhone1())){
//					formError.error(MessageErrorUtil.getText("ERROR_PHONE_NUMBER_NULL"));
//			}
//			
//		}else{
			formError.mandatory(subformId,"MOBILE",personalInfo.getMobileNo(),request);
			formError.mandatory(subformId,"HOME_PHONE",currentAddress.getPhone1(),request);
			formError.mandatory(subformId,"HOME_PHONE_EXT",currentAddress.getExt1(),request);
			formError.mandatory(subformId,"OFFICE_PHONE",workAddress.getPhone1(),request);
			formError.mandatory(subformId,"OFFICE_PHONE_EXT",workAddress.getExt1(),request);
		}
					
		return formError.getFormError();
	}

	@Override
	public boolean validateSubForm(HttpServletRequest request) {
		return false;
	}
	
	@Override
	public ArrayList<AuditDataM> auditForm(HttpServletRequest request,Object objectForm, Object lastObjectForm) {	
		ApplicationGroupDataM applicationGroup = ((ApplicationGroupDataM)objectForm);
		ApplicationGroupDataM lastapplicationGroup = ((ApplicationGroupDataM)lastObjectForm);		
		PersonalInfoDataM personalInfo = applicationGroup.getPersonalInfo(PERSONAL_TYPE);
		PersonalInfoDataM lastpersonalInfo = lastapplicationGroup.getPersonalInfo(PERSONAL_TYPE);
		if(Util.empty(personalInfo)){
			personalInfo = new PersonalInfoDataM();
		}
		if(Util.empty(lastpersonalInfo)){
			lastpersonalInfo = new PersonalInfoDataM();
		}		
		AddressDataM currentAddress = personalInfo.getAddress(ADDRESS_TYPE_CURRENT);
		if(Util.empty(currentAddress)){
			currentAddress = new AddressDataM();
		}
		AddressDataM lastCurrentAddress = lastpersonalInfo.getAddress(ADDRESS_TYPE_CURRENT);
		if(Util.empty(lastCurrentAddress)){
			lastCurrentAddress = new AddressDataM();
		}	
		AddressDataM workAddress = personalInfo.getAddress(ADDRESS_TYPE_WORK);
		if(Util.empty(workAddress)){
			workAddress = new AddressDataM();
		}	
		AddressDataM lastWorkAddress = lastpersonalInfo.getAddress(ADDRESS_TYPE_WORK);
		if(Util.empty(lastWorkAddress)){
			lastWorkAddress = new AddressDataM();
		}
		AuditFormUtil auditFormUtil = new AuditFormUtil();
		String subformId = getSubFormID();
		auditFormUtil.setObjectValue(personalInfo.getPersonalType());
		
		auditFormUtil.auditForm(subformId, "MOBILE", personalInfo, lastpersonalInfo, request);
		auditFormUtil.auditForm(subformId, "EMAIL", personalInfo, lastpersonalInfo, request);
		auditFormUtil.auditForm(subformId, "HOME_PHONE", currentAddress, lastCurrentAddress, request);
		auditFormUtil.auditForm(subformId, "OFFICE_PHONE", workAddress, lastWorkAddress, request);
		return auditFormUtil.getAuditForm();
	}
	
	@Override
	public void displayValueForm(HttpServletRequest request, Object objectForm){
		ApplicationGroupDataM applicationGroup = (ApplicationGroupDataM)objectForm;
		FormDisplayValueUtil formValue = new FormDisplayValueUtil(formId,formLevel,roleId,subformId,applicationGroup,request);
		PersonalInfoDataM personalInfo = applicationGroup.getPersonalInfo(PERSONAL_TYPE);
		if(null == personalInfo){
			personalInfo = new PersonalInfoDataM();
		}
		AddressDataM currentAddress = personalInfo.getAddress(ADDRESS_TYPE_CURRENT);
		if(Util.empty(currentAddress)){
			currentAddress = new AddressDataM();
		}
		AddressDataM workAddress = personalInfo.getAddress(ADDRESS_TYPE_WORK);
		if(null == workAddress){
			workAddress = new AddressDataM();
		}				
//		String PREFIX_ELEMENT_ID = PERSONAL_TYPE+"_"+personalInfo.getIdno();
		String PERS_PREFIX_ELEMENT_ID = CompareSensitiveFieldUtil.getSuffixFieldName(CompareDataM.UniqueLevel.PERSONAL,personalInfo.getPersonalId());
		personalInfo.setMobileNo(formValue.getValue("MOBILE","PHONE_NUMBER_"+PERS_PREFIX_ELEMENT_ID,personalInfo.getMobileNo()));
		personalInfo.setEmailPrimary(formValue.getValue("EMAIL","EMAIL_"+PERS_PREFIX_ELEMENT_ID,personalInfo.getEmailPrimary()));
		String CURRENT_PREFIX_ELEMENT_ID = CompareSensitiveFieldUtil.getSuffixFieldName(CompareDataM.UniqueLevel.ADDRESS,currentAddress.getAddressId());
		currentAddress.setPhone1(formValue.getValue("PHONE1_"+ADDRESS_TYPE_CURRENT,"HOME_PHONE_"+CURRENT_PREFIX_ELEMENT_ID,currentAddress.getPhone1()));
		currentAddress.setExt1(formValue.getValue("EXT1_"+ADDRESS_TYPE_CURRENT, "HOME_PHONE_EXT_"+CURRENT_PREFIX_ELEMENT_ID, currentAddress.getExt1()));
		
		String WORKADDRS_PREFIX_ELEMENT_ID = CompareSensitiveFieldUtil.getSuffixFieldName(CompareDataM.UniqueLevel.ADDRESS,workAddress.getAddressId());
		workAddress.setPhone1(formValue.getValue("PHONE1_"+ADDRESS_TYPE_WORK,"PHONE1_WORK_"+WORKADDRS_PREFIX_ELEMENT_ID,workAddress.getPhone1()));
		workAddress.setExt1(formValue.getValue("EXT1_"+ADDRESS_TYPE_WORK, "PHONE1_WORK_EXT_"+WORKADDRS_PREFIX_ELEMENT_ID, workAddress.getExt1()));
	}

	@Override
	public ArrayList<FieldElement> elementForm(HttpServletRequest request,Object objectForm) {
	ArrayList<FieldElement> fieldElements = new ArrayList<FieldElement>();
	String[] fieldNameList={"PHONE_NUMBER","HOME_PHONE","HOME_PHONE_EXT","PHONE1_WORK","PHONE1_WORK_EXT","EMAIL"};
	try {
		ApplicationGroupDataM applicationGroup = ((ApplicationGroupDataM)objectForm);			
		PersonalInfoDataM personalInfo = applicationGroup.getPersonalInfo(PERSONAL_TYPE);	
		 for(String elementId:fieldNameList){
			 FieldElement fieldElement = new FieldElement();
			 fieldElement.setElementId(elementId);
			 fieldElement.setElementLevel(CompareDataM.UniqueLevel.PERSONAL);
			 fieldElement.setElementGroupId(personalInfo.getPersonalId());
			 fieldElement.setElementGroupLevel(CompareSensitiveFieldUtil.getGroupDataLevelByPersonal(personalInfo));
			 fieldElements.add(fieldElement);
		 }
	} catch (Exception e) {
		logger.fatal("ERROR",e);
	}
	return fieldElements;
	}
}
