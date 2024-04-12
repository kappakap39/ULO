package com.eaf.orig.ulo.app.view.form.subform.sup;

import java.util.ArrayList;
import java.util.HashMap;

import javax.servlet.http.HttpServletRequest;

import org.apache.log4j.Logger;

import com.eaf.core.ulo.common.display.FormatUtil;
import com.eaf.core.ulo.common.model.AuditDataM;
import com.eaf.core.ulo.common.properties.SystemConstant;
import com.eaf.core.ulo.common.util.AuditFormUtil;
import com.eaf.core.ulo.common.util.FormErrorUtil;
import com.eaf.core.ulo.common.util.Util;
import com.eaf.orig.shared.constant.OrigConstant;
import com.eaf.orig.shared.view.form.ORIGSubForm;
import com.eaf.orig.ulo.app.util.GenerateUnique;
import com.eaf.orig.ulo.model.app.AddressDataM;
import com.eaf.orig.ulo.model.app.ApplicationGroupDataM;
import com.eaf.orig.ulo.model.app.PersonalApplicationInfoDataM;
import com.eaf.orig.ulo.model.app.PersonalInfoDataM;

public class PhoneNoSubForm3 extends ORIGSubForm{
	private static transient Logger logger = Logger.getLogger(PhoneNoSubForm3.class);
	private String PERSONAL_TYPE_SUPPLEMENTARY = SystemConstant.getConstant("PERSONAL_TYPE_SUPPLEMENTARY");
	String ADDRESS_TYPE_CURRENT = SystemConstant.getConstant("ADDRESS_TYPE_CURRENT");
	String ADDRESS_TYPE_WORK = SystemConstant.getConstant("ADDRESS_TYPE_WORK");
	
	@Override
	public void setProperties(HttpServletRequest request, Object appForm) {
		PersonalApplicationInfoDataM personalApplicationInfo = (PersonalApplicationInfoDataM)appForm;
		PersonalInfoDataM personalInfo = personalApplicationInfo.getPersonalInfo();
				
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
		String suffixC = ADDRESS_TYPE_CURRENT;
		String suffixW = ADDRESS_TYPE_WORK;
		
		String MOBILE = request.getParameter("PHONE_NUMBER");
		String EMAIL = request.getParameter("EMAIL");
		String PHONE1 = request.getParameter("PHONE1_"+suffixC);
		String PHONE1_EXT = request.getParameter("EXT1_"+suffixC);
		String OFFICE_PHONE = request.getParameter("PHONE1_"+suffixW);
		String OFFICE_PHONE_EXT = request.getParameter("EXT1_"+suffixW);
		String OFFICE_FAX = request.getParameter("FAX_"+suffixW);
		
		logger.debug("MOBILE >>"+MOBILE);
		logger.debug("EMAIL >>"+EMAIL);
		logger.debug("PHONE1 >>"+PHONE1);	
		logger.debug("PHONE1_EXT >>"+PHONE1_EXT);	
		logger.debug("OFFICE_PHONE >>"+OFFICE_PHONE);	
		logger.debug("OFFICE_PHONE_EXT >>"+OFFICE_PHONE_EXT);	
		logger.debug("OFFICE_FAX >>"+OFFICE_FAX);
		
		personalInfo.setMobileNo(FormatUtil.removeDash(MOBILE));
		personalInfo.setEmailPrimary(EMAIL);
		currentAddress.setPhone1(FormatUtil.removeDash(PHONE1));
		currentAddress.setExt1(PHONE1_EXT);
		workAddress.setPhone1(FormatUtil.removeDash(OFFICE_PHONE));
		workAddress.setExt1(OFFICE_PHONE_EXT);
		workAddress.setFax(FormatUtil.removeDash(OFFICE_FAX));
	}

	@Override
	public HashMap validateForm(HttpServletRequest request, Object appForm) {
		String subformId = "SUP_PHONE_NO_SUBFORM_3";
		logger.debug("subformId >> "+subformId);
		
		PersonalApplicationInfoDataM personalApplicationInfo = (PersonalApplicationInfoDataM)appForm;
		PersonalInfoDataM personalInfo = personalApplicationInfo.getPersonalInfo();
		
		String ADDRESS_TYPE_CURRENT = SystemConstant.getConstant("ADDRESS_TYPE_CURRENT");
		String ADDRESS_TYPE_WORK = SystemConstant.getConstant("ADDRESS_TYPE_WORK");
				
		AddressDataM currentAddress = personalInfo.getAddress(ADDRESS_TYPE_CURRENT);	
		AddressDataM workAddress = personalInfo.getAddress(ADDRESS_TYPE_WORK);	
		if(Util.empty(currentAddress)){
			currentAddress = new AddressDataM();
		}
		if(Util.empty(workAddress)){
			workAddress = new AddressDataM();
		}
		FormErrorUtil formError = new FormErrorUtil();	
		
		formError.mandatory(subformId,"MOBILE",personalInfo.getMobileNo(),request);
		formError.mandatory(subformId,"EMAIL",personalInfo.getEmailPrimary(),request);
		formError.mandatory(subformId,"PHONE1",currentAddress.getPhone1(),request);
		formError.mandatory(subformId,"PHONE_EXT",currentAddress.getExt1(),request);
		formError.mandatory(subformId,"OFFICE_PHONE",workAddress.getPhone1(),request);
		formError.mandatory(subformId,"OFFICE_PHONE_EXT",workAddress.getExt1(),request);		
		formError.mandatory(subformId,"FAX",workAddress.getFax(),request);
		
		return formError.getFormError();
	}

	@Override
	public boolean validateSubForm(HttpServletRequest request) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public ArrayList<AuditDataM> auditForm(HttpServletRequest request,Object objectForm, Object lastObjectForm) {	
		ApplicationGroupDataM applicationGroup = ((ApplicationGroupDataM)objectForm);
		ApplicationGroupDataM lastapplicationGroup = ((ApplicationGroupDataM)lastObjectForm);		
		PersonalInfoDataM personalInfo = applicationGroup.getPersonalInfo(PERSONAL_TYPE_SUPPLEMENTARY);
		PersonalInfoDataM lastpersonalInfo = lastapplicationGroup.getPersonalInfo(PERSONAL_TYPE_SUPPLEMENTARY);
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
		auditFormUtil.auditForm(subformId, "HOME_PHONE", currentAddress, lastCurrentAddress, request);
		auditFormUtil.auditForm(subformId, "OFFICE_PHONE", workAddress, lastWorkAddress, request);
		auditFormUtil.auditForm(subformId, "EMAIL", personalInfo, lastpersonalInfo, request);
		return auditFormUtil.getAuditForm();
	}
}
