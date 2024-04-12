package com.eaf.orig.ulo.app.view.form.subform.sup;

import java.util.ArrayList;
import java.util.HashMap;

import javax.servlet.http.HttpServletRequest;

import org.apache.log4j.Logger;

import com.eaf.core.ulo.common.message.MessageErrorUtil;
import com.eaf.core.ulo.common.model.AuditDataM;
import com.eaf.core.ulo.common.model.FieldElement;
import com.eaf.core.ulo.common.properties.SystemConstant;
import com.eaf.core.ulo.common.util.AuditFormUtil;
import com.eaf.core.ulo.common.util.FormDisplayValueUtil;
import com.eaf.core.ulo.common.util.FormErrorUtil;
import com.eaf.core.ulo.common.util.Util;
import com.eaf.orig.shared.constant.OrigConstant;
import com.eaf.orig.shared.view.form.ORIGSubForm;
import com.eaf.orig.ulo.app.util.GenerateUnique;
import com.eaf.orig.ulo.app.view.form.sensitive.util.CompareSensitiveFieldUtil;
import com.eaf.orig.ulo.control.address.util.DisplayAddressUtil;
import com.eaf.orig.ulo.control.util.PersonalInfoUtil;
import com.eaf.orig.ulo.formcontrol.view.form.ORIGFormHandler;
import com.eaf.orig.ulo.model.app.AddressDataM;
import com.eaf.orig.ulo.model.app.ApplicationGroupDataM;
import com.eaf.orig.ulo.model.app.PersonalApplicationInfoDataM;
import com.eaf.orig.ulo.model.app.PersonalInfoDataM;
//import com.eaf.core.ulo.common.properties.ListBoxControl;
import com.eaf.orig.ulo.model.compare.CompareDataM;
import com.eaf.orig.ulo.pl.app.view.webaction.AddressUtil;

@SuppressWarnings("serial")
public class AddressSubForm1 extends ORIGSubForm{
	private static transient Logger logger = Logger.getLogger(AddressSubForm1.class);
	private String ADDRESS_TYPE_CURRENT = SystemConstant.getConstant("ADDRESS_TYPE_CURRENT");
	private String ADDRESS_TYPE_WORK = SystemConstant.getConstant("ADDRESS_TYPE_WORK");
//	private String CACHE_COMPANY_CIS = SystemConstant.getConstant("CACHE_COMPANY_CIS");
	private String subformId = "SUP_ADDRESS_SUBFORM_1";
	String PERSONAL_TYPE_SUPPLEMENTARY = SystemConstant.getConstant("PERSONAL_TYPE_SUPPLEMENTARY");
	
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
		
		String SUFFIX_CURRENT_ADDRESS = ADDRESS_TYPE_CURRENT;
		String SUFFIX_WORK_ADDRESS = ADDRESS_TYPE_WORK;

		String CURRENT_ADDRESS_STYLE = request.getParameter("ADDRESS_STYLE_"+SUFFIX_CURRENT_ADDRESS);
		String ADRSTS = request.getParameter("ADRSTS_"+SUFFIX_CURRENT_ADDRESS);
		String CURRENT_PROVINCE = request.getParameter("PROVINCE_"+SUFFIX_CURRENT_ADDRESS);
		String CURRENT_ZIPCODE = request.getParameter("ZIPCODE_"+SUFFIX_CURRENT_ADDRESS);
		String WORK_COMPANY_TITLE = request.getParameter("COMPANY_TITLE_"+SUFFIX_WORK_ADDRESS);
		String WORK_COMPANY_NAME = request.getParameter("COMPANY_NAME_"+SUFFIX_WORK_ADDRESS);
		String WORK_PROVINCE = request.getParameter("PROVINCE_"+SUFFIX_WORK_ADDRESS);
		String WORK_ZIPCODE = request.getParameter("ZIPCODE_"+SUFFIX_WORK_ADDRESS);
		String SEND_DOC = request.getParameter("SEND_DOC");
		//String PLACE_RECEIVE_CARD = request.getParameter("PLACE_RECEIVE_CARD");
		//String BRANCH_RECEIVE_CARD = request.getParameter("BRANCH_RECEIVE_CARD");
		//String BRANCH_RECEIVE_CARD_NAME = request.getParameter("BRANCH_RECEIVE_CARD_NAME");
		//BRANCH_RECEIVE_CARD_NAME = PersonalInfoUtil.getBranchReceiceName(BRANCH_RECEIVE_CARD, BRANCH_RECEIVE_CARD_NAME);
		
		logger.debug("CURRENT_ADDRESS_STYLE >>"+CURRENT_ADDRESS_STYLE);	
		logger.debug("CURRENT_ADRSTS >>"+ADRSTS);	
		logger.debug("CURRENT_PROVINCE >>"+CURRENT_PROVINCE);	
		logger.debug("CURRENT_ZIPCODE >>"+CURRENT_ZIPCODE);	
		logger.debug("WORK_COMPANY_TITLE >>"+WORK_COMPANY_TITLE);
		logger.debug("WORK_COMPANY_NAME >>"+WORK_COMPANY_NAME);	
		logger.debug("WORK_PROVINCE >>"+WORK_PROVINCE);	
		logger.debug("WORK_ZIPCODE >>"+WORK_ZIPCODE);	
		logger.debug("SEND_DOC >>"+SEND_DOC);	
		//logger.debug("PLACE_RECEIVE_CARD >>"+PLACE_RECEIVE_CARD);	
		//logger.debug("BRANCH_RECEIVE_CARD >>"+BRANCH_RECEIVE_CARD);
		//logger.debug("BRANCH_RECEIVE_CARD_NAME >>"+BRANCH_RECEIVE_CARD_NAME);
		if(!Util.empty(WORK_COMPANY_NAME)){
			WORK_COMPANY_NAME = Util.removeNotAllowSpeacialCharactors(WORK_COMPANY_NAME.trim());
		}
		currentAddress.setBuildingType(CURRENT_ADDRESS_STYLE);
		currentAddress.setAdrsts(ADRSTS);
		currentAddress.setProvinceDesc(CURRENT_PROVINCE);
		currentAddress.setZipcode(CURRENT_ZIPCODE);
		DisplayAddressUtil.setAddressLine(currentAddress);
		
		workAddress.setCompanyTitle(WORK_COMPANY_TITLE);
		workAddress.setCompanyName(WORK_COMPANY_NAME);
		workAddress.setProvinceDesc(WORK_PROVINCE);
		workAddress.setZipcode(WORK_ZIPCODE);
		DisplayAddressUtil.setAddressLine(workAddress);
		
		personalInfo.setMailingAddress(SEND_DOC);
		personalInfo.setPlaceReceiveCard(SEND_DOC);
		
		//personalInfo.setPlaceReceiveCard(PLACE_RECEIVE_CARD);
		//personalInfo.setBranchReceiveCard(BRANCH_RECEIVE_CARD);
		//personalInfo.setBranchReceiveCardName(BRANCH_RECEIVE_CARD_NAME);
	}

	@SuppressWarnings({ "rawtypes", "unchecked" })
	@Override
	public HashMap validateForm(HttpServletRequest request, Object appForm) {
		PersonalApplicationInfoDataM personalApplicationInfo = (PersonalApplicationInfoDataM)appForm;
		PersonalInfoDataM personalInfo = personalApplicationInfo.getPersonalInfo();
		
		logger.debug("subformId >> "+subformId);
		FormErrorUtil formError = new FormErrorUtil(personalApplicationInfo);
		
		AddressDataM currentAddress = personalInfo.getAddress(ADDRESS_TYPE_CURRENT);	
		AddressDataM workAddress = personalInfo.getAddress(ADDRESS_TYPE_WORK);
		if(Util.empty(currentAddress)) {
			currentAddress = new AddressDataM();
		}
		if(Util.empty(workAddress)) {
			workAddress = new AddressDataM();
		}
		formError.mandatory(subformId,"ADDRESS_STYLE_"+ADDRESS_TYPE_CURRENT,currentAddress.getBuildingType(),request);
		formError.mandatory(subformId,"ADRSTS_"+ADDRESS_TYPE_CURRENT,currentAddress.getAdrsts(),request);
		formError.mandatory(subformId,"PROVINCE_"+ADDRESS_TYPE_CURRENT,currentAddress.getProvinceDesc(),request);
		formError.mandatory(subformId,"ZIPCODE_"+ADDRESS_TYPE_CURRENT,currentAddress.getZipcode(),request);
		
		formError.mandatory(subformId,"COMPANY_NAME_"+ADDRESS_TYPE_WORK,workAddress,request);
		formError.mandatory(subformId,"PROVINCE_"+ADDRESS_TYPE_WORK,workAddress.getProvinceDesc(),request);
		formError.mandatory(subformId,"ZIPCODE_"+ADDRESS_TYPE_WORK,workAddress.getZipcode(),request);
				
		formError.mandatory(subformId,"SEND_DOC_SUB_"+ADDRESS_TYPE_WORK,"SEND_DOC_SUB","SEND_DOC_SUB",personalInfo.getMailingAddress(),request);
		//formError.mandatory(subformId,"PLACE_RECEIVE_CARD_SUB",personalInfo.getPlaceReceiveCard(),request);
		//formError.mandatory(subformId,"BRANCH_RECEIVE_CARD_SUB",personalInfo.getBranchReceiveCard(),request);
						
		return formError.getFormError();
	}

	@Override
	public boolean validateSubForm(HttpServletRequest request) {
		return false;
	}
	
	public ArrayList<AuditDataM> auditForm(HttpServletRequest request,Object objectForm, Object lastObjectForm) {
		ApplicationGroupDataM applicationGroup = (ApplicationGroupDataM)objectForm;
		ApplicationGroupDataM lastApplicationGroup = (ApplicationGroupDataM)lastObjectForm;
		
		PersonalInfoDataM personalSupplementary = applicationGroup.getPersonalInfo(PERSONAL_TYPE_SUPPLEMENTARY);
		PersonalInfoDataM lastPersonalSupplementary = lastApplicationGroup.getPersonalInfo(PERSONAL_TYPE_SUPPLEMENTARY);
		
		String ADDRESS_TYPE_CURRENT = SystemConstant.getConstant("ADDRESS_TYPE_CURRENT");
		String ADDRESS_TYPE_WORK = SystemConstant.getConstant("ADDRESS_TYPE_WORK");
		
		AddressDataM currentAddress = personalSupplementary.getAddress(ADDRESS_TYPE_CURRENT);	
		AddressDataM workAddress = personalSupplementary.getAddress(ADDRESS_TYPE_WORK);
		
		AddressDataM lastcurrentAddress = lastPersonalSupplementary.getAddress(ADDRESS_TYPE_CURRENT);	
		AddressDataM lastworkAddress = lastPersonalSupplementary.getAddress(ADDRESS_TYPE_WORK);
		
		String subformId = getSubFormID();
		AuditFormUtil auditFormUtil = new AuditFormUtil();
		auditFormUtil.setObjectValue(PERSONAL_TYPE_SUPPLEMENTARY);
		auditFormUtil.auditForm(subformId, "COMPANY_NAME_02", workAddress, lastworkAddress, request);
		
		auditFormUtil.auditForm(subformId, "ZIPCODE_01", currentAddress, lastcurrentAddress, request);
		auditFormUtil.auditForm(subformId, "ZIPCODE_02", workAddress, lastworkAddress, request);
		auditFormUtil.auditForm(subformId, "PROVINCE_01", currentAddress,lastcurrentAddress, request);
		auditFormUtil.auditForm(subformId, "PROVINCE_02", workAddress,lastworkAddress, request);
		//auditFormUtil.auditForm(subformId, "BRANCH_RECEIVE_CARD_SUB", personalSupplementary,lastPersonalSupplementary, request);
		auditFormUtil.auditForm(subformId, "SEND_DOC_SUB_02", personalSupplementary,lastPersonalSupplementary, request);
		//auditFormUtil.auditForm(subformId, "PLACE_RECEIVE_CARD_SUB", personalSupplementary,lastPersonalSupplementary, request);
		
		return auditFormUtil.getAuditForm();
	}
	
	@Override
	public void displayValueForm(HttpServletRequest request, Object objectForm) {
		ApplicationGroupDataM applicationGroup = ORIGFormHandler.getObjectForm(request);
		FormDisplayValueUtil formValue = new FormDisplayValueUtil(formId,formLevel,roleId,subformId,applicationGroup,request);
		PersonalApplicationInfoDataM personalApplicationInfo = (PersonalApplicationInfoDataM)objectForm;
		PersonalInfoDataM personalInfo = personalApplicationInfo.getPersonalInfo();		
//		String PERSONAL_TYPE = personalInfo.getPersonalType();
		String ADDRESS_TYPE_CURRENT = SystemConstant.getConstant("ADDRESS_TYPE_CURRENT");
		String ADDRESS_TYPE_WORK = SystemConstant.getConstant("ADDRESS_TYPE_WORK");
		
		AddressDataM currentAddress = personalInfo.getAddress(ADDRESS_TYPE_CURRENT);	
		AddressDataM workAddress = personalInfo.getAddress(ADDRESS_TYPE_WORK);
		
//		String PREFIX_ELEMENT_ID = PERSONAL_TYPE+"_"+personalInfo.getIdno();
		String CURR_ADDRESS_PREFIX_ELEMENT_ID = CompareSensitiveFieldUtil.getSuffixFieldName(CompareDataM.UniqueLevel.ADDRESS,currentAddress.getAddressId());
		
		currentAddress.setProvinceDesc(formValue.getValue("PROVINCE_"+ADDRESS_TYPE_CURRENT,"PROVINCE_"+CURR_ADDRESS_PREFIX_ELEMENT_ID,currentAddress.getProvinceDesc()));
		currentAddress.setZipcode(formValue.getValue("ZIPCODE_"+ADDRESS_TYPE_CURRENT,"ZIPCODE_"+CURR_ADDRESS_PREFIX_ELEMENT_ID,currentAddress.getZipcode()));
		
		String WORK_ADDRESS_PREFIX_ELEMENT_ID = CompareSensitiveFieldUtil.getSuffixFieldName(CompareDataM.UniqueLevel.ADDRESS,workAddress.getAddressId());
		workAddress.setCompanyTitle(formValue.getValue("COMPANY_NAME_"+ADDRESS_TYPE_WORK,"COMPANY_TITLE_"+WORK_ADDRESS_PREFIX_ELEMENT_ID,workAddress.getCompanyTitle()));
		workAddress.setCompanyName(formValue.getValue("COMPANY_NAME_"+ADDRESS_TYPE_WORK,"COMPANY_NAME_"+WORK_ADDRESS_PREFIX_ELEMENT_ID,workAddress.getCompanyName()));
		workAddress.setProvinceDesc(formValue.getValue("PROVINCE_"+ADDRESS_TYPE_WORK,"PROVINCE_"+WORK_ADDRESS_PREFIX_ELEMENT_ID,workAddress.getProvinceDesc()));
		workAddress.setZipcode(formValue.getValue("ZIPCODE_"+ADDRESS_TYPE_WORK,"ZIPCODE_"+WORK_ADDRESS_PREFIX_ELEMENT_ID,workAddress.getZipcode()));
		
		String PERS_PREFIX_ELEMENT_ID = CompareSensitiveFieldUtil.getSuffixFieldName(CompareDataM.UniqueLevel.PERSONAL,personalInfo.getPersonalId());
		personalInfo.setMailingAddress(formValue.getValue("SEND_DOC", "SEND_DOC_"+PERS_PREFIX_ELEMENT_ID, personalInfo.getMailingAddress()));
		personalInfo.setPlaceReceiveCard(formValue.getValue("SEND_DOC", "SEND_DOC_"+PERS_PREFIX_ELEMENT_ID, personalInfo.getMailingAddress()));
		//personalInfo.setBranchReceiveCard(formValue.getValue("BRANCH_RECEIVE_CARD","BRANCH_RECEIVE_CARD_"+PERS_PREFIX_ELEMENT_ID, personalInfo.getBranchReceiveCard()));
	}

	@Override
	public ArrayList<FieldElement> elementForm(HttpServletRequest request,Object objectForm) {
		ArrayList<FieldElement> fieldElements = new ArrayList<FieldElement>();
		String[] filedNames ={"SEND_DOC","CURRENT_PROVINCE",
							 "CURRENT_ZIPCODE","COMPANY_NAME","COMPANY_PROVINCE","COMPANY_ZIPCODE", "COMPANY_TITLE"};
		try {
			ArrayList<PersonalInfoDataM>    personalInfos  = new ArrayList<PersonalInfoDataM>();
			if(objectForm instanceof PersonalInfoDataM){
				PersonalInfoDataM personalInfo = (PersonalInfoDataM)objectForm;
				personalInfos.add(personalInfo);
			}else if(objectForm instanceof ApplicationGroupDataM){
				ApplicationGroupDataM applicationGroup = ((ApplicationGroupDataM)objectForm);			
				ArrayList<PersonalInfoDataM>  personalInfoList = applicationGroup.getPersonalInfos(PERSONAL_TYPE_SUPPLEMENTARY);
				personalInfos.addAll(personalInfoList);
			}
			if(!Util.empty(personalInfos)){
				for(PersonalInfoDataM personalInfo :personalInfos){
					 for(String elementId:filedNames){
						 FieldElement fieldElement = new FieldElement();
						 fieldElement.setElementId(elementId);
						 fieldElement.setElementLevel(CompareDataM.UniqueLevel.PERSONAL);
						 fieldElement.setElementGroupId(personalInfo.getPersonalId());
						 fieldElement.setElementGroupLevel(CompareSensitiveFieldUtil.getGroupDataLevelByPersonal(personalInfo));
						 fieldElements.add(fieldElement);
					 }
				}
			}

			
		} catch (Exception e) {
			logger.fatal("ERROR",e);
		}
		return fieldElements;
	}
}
