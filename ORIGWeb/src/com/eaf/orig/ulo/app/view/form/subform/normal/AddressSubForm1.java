package com.eaf.orig.ulo.app.view.form.subform.normal;

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
import com.eaf.orig.ulo.app.view.util.kpl.KPLUtil;
import com.eaf.orig.ulo.control.address.util.DisplayAddressUtil;
import com.eaf.orig.ulo.control.util.PersonalInfoUtil;
import com.eaf.orig.ulo.model.app.AddressDataM;
import com.eaf.orig.ulo.model.app.ApplicationGroupDataM;
import com.eaf.orig.ulo.model.app.PersonalInfoDataM;
import com.eaf.orig.ulo.model.compare.CompareDataM;
//import com.eaf.core.ulo.common.properties.ListBoxControl;
import com.eaf.orig.ulo.pl.app.view.webaction.AddressUtil;

@SuppressWarnings("serial")
public class AddressSubForm1 extends ORIGSubForm{
	private static transient Logger logger = Logger.getLogger(AddressSubForm1.class);
	private String PERSONAL_TYPE = SystemConstant.getConstant("PERSONAL_TYPE_APPLICANT");
//	private String CACHE_COMPANY_CIS = SystemConstant.getConstant("CACHE_COMPANY_CIS");
	private String subformId = "NORMAL_ADDRESS_SUBFORM_1";
	
	String ADDRESS_TYPE_CURRENT = SystemConstant.getConstant("ADDRESS_TYPE_CURRENT");
	String ADDRESS_TYPE_WORK = SystemConstant.getConstant("ADDRESS_TYPE_WORK");
	String ADDRESS_TYPE_DOCUMENT = SystemConstant.getConstant("ADDRESS_TYPE_DOCUMENT");
	
	
	
	@Override
	public void setProperties(HttpServletRequest request, Object appForm) {
		ApplicationGroupDataM applicationGroup = ((ApplicationGroupDataM)appForm);
			
		PersonalInfoDataM personalInfo = applicationGroup.getPersonalInfo(PERSONAL_TYPE);	
		int PERSONAL_SEQ = personalInfo.getSeq();
		logger.debug("PERSONAL_SEQ >> "+PERSONAL_SEQ);
		logger.debug("PERSONAL_TYPE >> "+PERSONAL_TYPE);
		
//		String ADDRESS_TYPE_CURRENT = SystemConstant.getConstant("ADDRESS_TYPE_CURRENT");
//		String ADDRESS_TYPE_WORK = SystemConstant.getConstant("ADDRESS_TYPE_WORK");
//		String ADDRESS_TYPE_DOCUMENT = SystemConstant.getConstant("ADDRESS_TYPE_DOCUMENT");
		
		AddressDataM currentAddress = personalInfo.getAddress(ADDRESS_TYPE_CURRENT);
		if(null == currentAddress){
			String addressId = GenerateUnique.generate(OrigConstant.SeqNames.ORIG_PERSONAL_ADDRESS_PK);
			currentAddress = new AddressDataM();
			currentAddress.setAddressId(addressId);
			currentAddress.setAddressType(ADDRESS_TYPE_CURRENT);
			personalInfo.addAddress(currentAddress);
		}		
		if(Util.empty(currentAddress.getAddressId())){
			String addressId = GenerateUnique.generate(OrigConstant.SeqNames.ORIG_PERSONAL_ADDRESS_PK);
			currentAddress.setAddressId(addressId);
//			personalInfo.addAddress(currentAddress);
		}
		AddressDataM workAddress = personalInfo.getAddress(ADDRESS_TYPE_WORK);
		if(null == workAddress){
			String addressId = GenerateUnique.generate(OrigConstant.SeqNames.ORIG_PERSONAL_ADDRESS_PK);
			workAddress = new AddressDataM();
			workAddress.setAddressId(addressId);
			workAddress.setAddressType(ADDRESS_TYPE_WORK);
			personalInfo.addAddress(workAddress);
		}	
		if(Util.empty(workAddress.getAddressId())){
			String addressId = GenerateUnique.generate(OrigConstant.SeqNames.ORIG_PERSONAL_ADDRESS_PK);
			workAddress.setAddressId(addressId);
//			personalInfo.addAddress(workAddress);
		}
		AddressDataM documentAddress = personalInfo.getAddress(ADDRESS_TYPE_DOCUMENT);
		if(null == documentAddress){
			String addressId = GenerateUnique.generate(OrigConstant.SeqNames.ORIG_PERSONAL_ADDRESS_PK);
			documentAddress = new AddressDataM();
			documentAddress.setAddressId(addressId);
			documentAddress.setAddressType(ADDRESS_TYPE_DOCUMENT);
			personalInfo.addAddress(documentAddress);
		}
		if(Util.empty(documentAddress.getAddressId())){
			String addressId = GenerateUnique.generate(OrigConstant.SeqNames.ORIG_PERSONAL_ADDRESS_PK);
			documentAddress.setAddressId(addressId);
//			personalInfo.addAddress(documentAddress);
		}
		String SUFFIX_CURRENT_ADDRESS = ADDRESS_TYPE_CURRENT;
		String SUFFIX_WORK_ADDRESS = ADDRESS_TYPE_WORK;
		String SUFFIX_DOCUMENT_ADDRESS = ADDRESS_TYPE_DOCUMENT;
		
		String CURRENT_ADDRESS_STYLE = request.getParameter("ADDRESS_STYLE_"+SUFFIX_CURRENT_ADDRESS);
		String ADRSTS = request.getParameter("ADRSTS_"+SUFFIX_CURRENT_ADDRESS);
		String CURRENT_PROVINCE = request.getParameter("PROVINCE_"+SUFFIX_CURRENT_ADDRESS);
		String CURRENT_ZIPCODE = request.getParameter("ZIPCODE_"+SUFFIX_CURRENT_ADDRESS);
		String CURRENT_TAMBOL = request.getParameter("TAMBOL_"+SUFFIX_CURRENT_ADDRESS);
		String CURRENT_AMPHUR = request.getParameter("AMPHUR_"+SUFFIX_CURRENT_ADDRESS);
		String WORK_COMPANY_TITLE = request.getParameter("COMPANY_TITLE_"+SUFFIX_WORK_ADDRESS);
		String WORK_COMPANY_NAME = request.getParameter("COMPANY_NAME_"+SUFFIX_WORK_ADDRESS);
		String WORK_PROVINCE = request.getParameter("PROVINCE_"+SUFFIX_WORK_ADDRESS);
		String WORK_ZIPCODE = request.getParameter("ZIPCODE_"+SUFFIX_WORK_ADDRESS);
		String WORK_TAMBOL = request.getParameter("TAMBOL_"+SUFFIX_WORK_ADDRESS);
		String WORK_AMPHUR = request.getParameter("AMPHUR_"+SUFFIX_WORK_ADDRESS);
		String DOCUMENT_PROVINCE = request.getParameter("PROVINCE_"+SUFFIX_DOCUMENT_ADDRESS);
		String DOCUMENT_ZIPCODE = request.getParameter("ZIPCODE_"+SUFFIX_DOCUMENT_ADDRESS);
		String DOCUMENT_TAMBOL = request.getParameter("TAMBOL_"+SUFFIX_DOCUMENT_ADDRESS);
		String DOCUMENT_AMPHUR = request.getParameter("AMPHUR_"+SUFFIX_DOCUMENT_ADDRESS);
		String SEND_DOC = request.getParameter("SEND_DOC");
		//String PLACE_RECEIVE_CARD = request.getParameter("PLACE_RECEIVE_CARD");
		//String BRANCH_RECEIVE_CARD = request.getParameter("BRANCH_RECEIVE_CARD");
		//String BRANCH_RECEIVE_CARD_NAME = request.getParameter("BRANCH_RECEIVE_CARD_NAME");		
		//BRANCH_RECEIVE_CARD_NAME = PersonalInfoUtil.getBranchReceiceName(BRANCH_RECEIVE_CARD, BRANCH_RECEIVE_CARD_NAME);
		
		logger.debug("CURRENT_ADDRESS_STYLE >>"+CURRENT_ADDRESS_STYLE);	
		logger.debug("CURRENT_ADRSTS >>"+ADRSTS);	
		logger.debug("CURRENT_PROVINCE >>"+CURRENT_PROVINCE);	
		logger.debug("CURRENT_ZIPCODE >>"+CURRENT_ZIPCODE);	
		logger.debug("CURRENT_TAMBOL >>"+CURRENT_TAMBOL);	
		logger.debug("CURRENT_AMPHUR >>"+CURRENT_AMPHUR);	
		logger.debug("WORK_COMPANY_TITLE >>"+WORK_COMPANY_TITLE);	
		logger.debug("WORK_COMPANY_NAME >>"+WORK_COMPANY_NAME);	
		logger.debug("WORK_PROVINCE >>"+WORK_PROVINCE);	
		logger.debug("WORK_ZIPCODE >>"+WORK_ZIPCODE);	
		logger.debug("WORK_TAMBOL >>"+WORK_TAMBOL);	
		logger.debug("WORK_AMPHUR >>"+WORK_AMPHUR);	
		logger.debug("DOCUMENT_PROVINCE >>"+DOCUMENT_PROVINCE);	
		logger.debug("DOCUMENT_ZIPCODE >>"+DOCUMENT_ZIPCODE);	
		logger.debug("DOCUMENT_TAMBOL >>"+DOCUMENT_TAMBOL);	
		logger.debug("DOCUMENT_AMPHUR >>"+DOCUMENT_AMPHUR);	
		logger.debug("SEND_DOC >>"+SEND_DOC);	
		//logger.debug("PLACE_RECEIVE_CARD >>"+PLACE_RECEIVE_CARD);	
		//logger.debug("BRANCH_RECEIVE_CARD >>"+BRANCH_RECEIVE_CARD);	
		//logger.debug("BRANCH_RECEIVE_CARD_NAME >>"+BRANCH_RECEIVE_CARD_NAME);
		
		currentAddress.setBuildingType(CURRENT_ADDRESS_STYLE);
		currentAddress.setAdrsts(ADRSTS);
		currentAddress.setProvinceDesc(CURRENT_PROVINCE);
		currentAddress.setZipcode(CURRENT_ZIPCODE);
		currentAddress.setAmphur(CURRENT_AMPHUR);
		currentAddress.setTambol(CURRENT_TAMBOL);
		
		workAddress.setCompanyTitle(WORK_COMPANY_TITLE);
		if(!Util.empty(WORK_COMPANY_NAME)){
			WORK_COMPANY_NAME = Util.removeNotAllowSpeacialCharactors(WORK_COMPANY_NAME.trim());
		}
		workAddress.setCompanyName(WORK_COMPANY_NAME);
		workAddress.setProvinceDesc(WORK_PROVINCE);
		workAddress.setZipcode(WORK_ZIPCODE);
		workAddress.setTambol(WORK_TAMBOL);
		workAddress.setAmphur(WORK_AMPHUR);
		
		documentAddress.setProvinceDesc(DOCUMENT_PROVINCE);
		documentAddress.setZipcode(DOCUMENT_ZIPCODE);
		documentAddress.setAmphur(DOCUMENT_AMPHUR);
		documentAddress.setTambol(DOCUMENT_TAMBOL);
		
		personalInfo.setMailingAddress(SEND_DOC);
		personalInfo.setPlaceReceiveCard(SEND_DOC);
		
		//personalInfo.setPlaceReceiveCard(PLACE_RECEIVE_CARD);
		//personalInfo.setBranchReceiveCard(BRANCH_RECEIVE_CARD);
		//personalInfo.setBranchReceiveCardName(BRANCH_RECEIVE_CARD_NAME);
		
		DisplayAddressUtil.setAddressLine(currentAddress);
		DisplayAddressUtil.setAddressLine(workAddress);
		DisplayAddressUtil.setAddressLine(documentAddress);
		
	}

	@SuppressWarnings({ "unchecked", "rawtypes" })
	@Override
	public HashMap validateForm(HttpServletRequest request, Object appForm) {
		ApplicationGroupDataM applicationGroup = ((ApplicationGroupDataM)appForm);
		
		logger.debug("subformId >> "+subformId);
		FormErrorUtil formError = new FormErrorUtil();
		
//		String ADDRESS_TYPE_CURRENT = SystemConstant.getConstant("ADDRESS_TYPE_CURRENT");
//		String ADDRESS_TYPE_WORK = SystemConstant.getConstant("ADDRESS_TYPE_WORK");
//		String ADDRESS_TYPE_DOCUMENT = SystemConstant.getConstant("ADDRESS_TYPE_DOCUMENT");
		
		PersonalInfoDataM personalInfo = applicationGroup.getPersonalInfo(PERSONAL_TYPE);	
		AddressDataM currentAddress = personalInfo.getAddress(ADDRESS_TYPE_CURRENT);	
		AddressDataM workAddress = personalInfo.getAddress(ADDRESS_TYPE_WORK);
		AddressDataM documentAddress = personalInfo.getAddress(ADDRESS_TYPE_DOCUMENT);

		
		formError.mandatory(subformId,"PROVINCE_"+ADDRESS_TYPE_CURRENT,currentAddress.getProvinceDesc(),request);
		formError.mandatory(subformId,"ZIPCODE_"+ADDRESS_TYPE_CURRENT,currentAddress.getZipcode(),request);
		
		
		formError.mandatory(subformId,"PROVINCE_"+ADDRESS_TYPE_WORK,workAddress.getProvinceDesc(),request);
		formError.mandatory(subformId,"ZIPCODE_"+ADDRESS_TYPE_WORK,workAddress.getZipcode(),request);

		
		formError.mandatory(subformId,"BRANCH_RECEIVE_CARD",personalInfo.getBranchReceiveCard(),request);
		formError.mandatory(subformId,"PROVINCE_"+ADDRESS_TYPE_DOCUMENT,documentAddress.getProvinceDesc(),request);
		formError.mandatory(subformId,"ZIPCODE_"+ADDRESS_TYPE_DOCUMENT,documentAddress.getZipcode(),request);
		formError.mandatory(subformId,"SEND_DOC",personalInfo.getMailingAddress(),request);
		formError.mandatory(subformId,"PLACE_RECEIVE_CARD",personalInfo.getPlaceReceiveCard(),request);
		
		if(!KPLUtil.isKPL_TOPUP(applicationGroup))
		{
			formError.mandatory(subformId,"ADDRESS_STYLE_"+ADDRESS_TYPE_CURRENT,currentAddress.getBuildingType(),request);
			formError.mandatory(subformId,"ADRSTS_"+ADDRESS_TYPE_CURRENT,currentAddress.getAdrsts(),request);
			formError.mandatory(subformId,"COMPANY_NAME_"+ADDRESS_TYPE_WORK,workAddress,request);
		}
		
		return formError.getFormError();
	}

	@Override
	public boolean validateSubForm(HttpServletRequest request) {
		return false;
	}
	@Override
	public ArrayList<AuditDataM> auditForm(HttpServletRequest request,Object objectForm, Object lastObjectForm) {
		AuditFormUtil auditFormUtil = new AuditFormUtil();
		try{
			ApplicationGroupDataM applicationGroup = ((ApplicationGroupDataM)objectForm);
			ApplicationGroupDataM lastApplicationGroup = ((ApplicationGroupDataM)lastObjectForm);
			PersonalInfoDataM personalInfo = applicationGroup.getPersonalInfo(PERSONAL_TYPE);
			PersonalInfoDataM lastPersonalInfo = lastApplicationGroup.getPersonalInfo(PERSONAL_TYPE);
			
//			String ADDRESS_TYPE_CURRENT = SystemConstant.getConstant("ADDRESS_TYPE_CURRENT");
//			String ADDRESS_TYPE_WORK = SystemConstant.getConstant("ADDRESS_TYPE_WORK");
//			String ADDRESS_TYPE_DOCUMENT = SystemConstant.getConstant("ADDRESS_TYPE_DOCUMENT");
			
			AddressDataM currentAddress = personalInfo.getAddress(ADDRESS_TYPE_CURRENT);	
			AddressDataM workAddress = personalInfo.getAddress(ADDRESS_TYPE_WORK);
			AddressDataM documentAddress = personalInfo.getAddress(ADDRESS_TYPE_DOCUMENT);
			
			AddressDataM lastcurrentAddress = lastPersonalInfo.getAddress(ADDRESS_TYPE_CURRENT);	
			AddressDataM lastworkAddress = lastPersonalInfo.getAddress(ADDRESS_TYPE_WORK);
			AddressDataM lastdocumentAddress = lastPersonalInfo.getAddress(ADDRESS_TYPE_DOCUMENT);
			
			
			String subformId = getSubFormID();
			auditFormUtil.setObjectValue(PERSONAL_TYPE);
			
			auditFormUtil.auditForm(subformId, "COMPANY_NAME_02", workAddress, lastworkAddress, request);
			
			auditFormUtil.auditForm(subformId, "ZIPCODE_01", currentAddress,lastcurrentAddress, request);
			auditFormUtil.auditForm(subformId, "ZIPCODE_02", workAddress,lastworkAddress, request);
			auditFormUtil.auditForm(subformId, "ZIPCODE_03", documentAddress,lastdocumentAddress, request);
			auditFormUtil.auditForm(subformId, "BRANCH_RECEIVE_CARD", personalInfo,lastPersonalInfo, request);
			auditFormUtil.auditForm(subformId, "PLACE_RECEIVE_CARD", personalInfo,lastPersonalInfo, request);
			auditFormUtil.auditForm(subformId, "SEND_DOC", personalInfo,lastPersonalInfo, request);
			auditFormUtil.auditForm(subformId, "ADDRESS_STYLE_01", currentAddress,lastcurrentAddress, request);
			auditFormUtil.auditForm(subformId, "ADRSTS_01", currentAddress,lastcurrentAddress, request);
			auditFormUtil.auditForm(subformId, "PROVINCE_01", currentAddress,lastcurrentAddress, request);
			auditFormUtil.auditForm(subformId, "PROVINCE_02", workAddress,lastworkAddress, request);
			auditFormUtil.auditForm(subformId, "PROVINCE_03", documentAddress,lastdocumentAddress, request);
			
		}catch(Exception e){
			logger.fatal("ERROR ",e);
		}

		return auditFormUtil.getAuditForm();
	}
	
	@Override
	public void displayValueForm(HttpServletRequest request, Object objectForm) {
		ApplicationGroupDataM applicationGroup = (ApplicationGroupDataM)objectForm;
		FormDisplayValueUtil formValue = new FormDisplayValueUtil(formId,formLevel,roleId,subformId,applicationGroup,request);		
		PersonalInfoDataM personalInfo = applicationGroup.getPersonalInfo(PERSONAL_TYPE);			
		AddressDataM currentAddress = personalInfo.getAddress(ADDRESS_TYPE_CURRENT);
		if(null == currentAddress){
			currentAddress = new AddressDataM();
			currentAddress.setAddressType(ADDRESS_TYPE_CURRENT);
			personalInfo.addAddress(currentAddress);
		}		
		AddressDataM workAddress = personalInfo.getAddress(ADDRESS_TYPE_WORK);
		if(null == workAddress){
			workAddress = new AddressDataM();
			workAddress.setAddressType(ADDRESS_TYPE_WORK);
			personalInfo.addAddress(workAddress);
		}	
		AddressDataM documentAddress = personalInfo.getAddress(ADDRESS_TYPE_DOCUMENT);
		if(null == documentAddress){
			documentAddress = new AddressDataM();
			documentAddress.setAddressType(ADDRESS_TYPE_DOCUMENT);
			personalInfo.addAddress(documentAddress);
		}
//		String PREFIX_ELEMENT_ID = CompareSensitiveFieldUtil.getPersonalRefId(personalInfo);
		String CURRADDRSS_PREFIX_ELEMENT_ID = CompareSensitiveFieldUtil.getSuffixFieldName(CompareDataM.UniqueLevel.ADDRESS,currentAddress.getAddressId());
		
		currentAddress.setBuildingType(formValue.getValue("ADDRESS_STYLE_"+ADDRESS_TYPE_CURRENT,"ADDRESS_STYLE_"+CURRADDRSS_PREFIX_ELEMENT_ID,currentAddress.getBuildingType()));
		currentAddress.setAdrsts(formValue.getValue("ADRSTS_"+ADDRESS_TYPE_CURRENT,"ADRSTS_"+CURRADDRSS_PREFIX_ELEMENT_ID,currentAddress.getAdrsts()));
		currentAddress.setProvinceDesc(formValue.getValue("PROVINCE_"+ADDRESS_TYPE_CURRENT,"PROVINCE_"+CURRADDRSS_PREFIX_ELEMENT_ID,currentAddress.getProvinceDesc()));
		currentAddress.setZipcode(formValue.getValue("ZIPCODE_"+ADDRESS_TYPE_CURRENT,"ZIPCODE_"+CURRADDRSS_PREFIX_ELEMENT_ID,currentAddress.getZipcode()));
		
		String WORKADDRSS_PREFIX_ELEMENT_ID = CompareSensitiveFieldUtil.getSuffixFieldName(CompareDataM.UniqueLevel.ADDRESS,workAddress.getAddressId());
		workAddress.setCompanyTitle(formValue.getValue("COMPANY_NAME_"+ADDRESS_TYPE_WORK,"COMPANY_TITLE_"+WORKADDRSS_PREFIX_ELEMENT_ID,workAddress.getCompanyTitle()));
		workAddress.setCompanyName(formValue.getValue("COMPANY_NAME_"+ADDRESS_TYPE_WORK,"COMPANY_NAME_"+WORKADDRSS_PREFIX_ELEMENT_ID,workAddress.getCompanyName()));
		workAddress.setProvinceDesc(formValue.getValue("PROVINCE_"+ADDRESS_TYPE_WORK,"PROVINCE_"+WORKADDRSS_PREFIX_ELEMENT_ID,workAddress.getProvinceDesc()));
		workAddress.setZipcode(formValue.getValue("ZIPCODE_"+ADDRESS_TYPE_WORK,"ZIPCODE_"+WORKADDRSS_PREFIX_ELEMENT_ID,workAddress.getZipcode()));
		
		String DOCADDRSS_PREFIX_ELEMENT_ID = CompareSensitiveFieldUtil.getSuffixFieldName(CompareDataM.UniqueLevel.ADDRESS,documentAddress.getAddressId());
		documentAddress.setProvinceDesc(formValue.getValue("PROVINCE_"+ADDRESS_TYPE_DOCUMENT,"PROVINCE_"+DOCADDRSS_PREFIX_ELEMENT_ID,documentAddress.getProvinceDesc()));
		documentAddress.setZipcode(formValue.getValue("ZIPCODE_"+ADDRESS_TYPE_DOCUMENT,"ZIPCODE_"+DOCADDRSS_PREFIX_ELEMENT_ID,documentAddress.getZipcode()));
		
		String PERS_PREFIX_ELEMENT_ID = CompareSensitiveFieldUtil.getSuffixFieldName(CompareDataM.UniqueLevel.PERSONAL,personalInfo.getPersonalId());
		personalInfo.setMailingAddress(formValue.getValue("SEND_DOC", "SEND_DOC_"+PERS_PREFIX_ELEMENT_ID, personalInfo.getMailingAddress()));
		personalInfo.setPlaceReceiveCard(formValue.getValue("SEND_DOC", "SEND_DOC_"+PERS_PREFIX_ELEMENT_ID, personalInfo.getMailingAddress()));
		//personalInfo.setBranchReceiveCard(formValue.getValue("BRANCH_RECEIVE_CARD", "BRANCH_RECEIVE_CARD_"+PERS_PREFIX_ELEMENT_ID, personalInfo.getBranchReceiveCard()));
		//personalInfo.setBranchReceiveCardName(formValue.getValue("BRANCH_RECEIVE_CARD", "BRANCH_RECEIVE_CARD_"+PERS_PREFIX_ELEMENT_ID, personalInfo.getBranchReceiveCardName()));
	}
	
	@Override
	public ArrayList<FieldElement> elementForm(HttpServletRequest request,Object objectForm) {
		ArrayList<FieldElement>  fieldElementList = new ArrayList<FieldElement>();
		ApplicationGroupDataM applicationGroup = (ApplicationGroupDataM) objectForm;
		PersonalInfoDataM personalInfo = applicationGroup.getPersonalInfo(PERSONAL_TYPE);
		//String[] fieldNameTypes = {"SEND_DOC","PLACE_RECEIVE_CARD","BRANCH_RECEIVE_CARD","CURRENT_ADDRESS_STYLE","CURRENT_ADRSTS","CURRENT_PROVINCE",
		String[] fieldNameTypes = {"SEND_DOC","CURRENT_ADDRESS_STYLE","CURRENT_ADRSTS","CURRENT_PROVINCE",
									"CURRENT_ZIPCODE","COMPANY_NAME","COMPANY_PROVINCE","COMPANY_ZIPCODE",
									"DOCUMENT_PROVINCE","DOCUMENT_ZIPCODE","COMPANY_TITLE"};

		try {
			 for(String elementId:fieldNameTypes){
				 FieldElement fieldElement = new FieldElement();
				 fieldElement.setElementId(elementId);
				 fieldElement.setElementLevel(CompareDataM.UniqueLevel.ADDRESS);
				 fieldElement.setElementGroupId(personalInfo.getPersonalId());
				 fieldElement.setElementGroupLevel(CompareSensitiveFieldUtil.getGroupDataLevelByPersonal(personalInfo));
				 fieldElementList.add(fieldElement);
			 }

		} catch (Exception e) {
			logger.fatal("ERROR",e);
		}
		return fieldElementList;
	}
}
