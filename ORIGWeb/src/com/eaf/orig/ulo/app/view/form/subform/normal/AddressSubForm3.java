package com.eaf.orig.ulo.app.view.form.subform.normal;

import java.util.ArrayList;
import java.util.HashMap;

import javax.servlet.http.HttpServletRequest;

import org.apache.log4j.Logger;

import com.eaf.core.ulo.common.display.ElementInf;
import com.eaf.core.ulo.common.display.FormatUtil;
import com.eaf.core.ulo.common.message.MessageErrorUtil;
import com.eaf.core.ulo.common.model.AuditDataM;
import com.eaf.core.ulo.common.model.FormControlDataM;
import com.eaf.core.ulo.common.properties.CacheControl;
import com.eaf.core.ulo.common.properties.FormControl;
import com.eaf.core.ulo.common.properties.ImplementControl;
import com.eaf.core.ulo.common.properties.SystemConstant;
import com.eaf.core.ulo.common.util.AuditFormUtil;
import com.eaf.core.ulo.common.util.FormDisplayValueUtil;
import com.eaf.core.ulo.common.util.FormErrorUtil;
import com.eaf.core.ulo.common.util.Util;
import com.eaf.orig.shared.constant.OrigConstant;
import com.eaf.orig.shared.view.form.ORIGSubForm;
import com.eaf.orig.ulo.app.util.GenerateUnique;
import com.eaf.orig.ulo.app.view.form.sensitive.util.CompareSensitiveFieldUtil;
import com.eaf.orig.ulo.constant.MConstant;
import com.eaf.orig.ulo.control.address.util.DisplayAddressUtil;
import com.eaf.orig.ulo.control.util.PersonalAddressUtil;
import com.eaf.orig.ulo.control.util.PersonalInfoUtil;
import com.eaf.orig.ulo.model.app.AddressDataM;
import com.eaf.orig.ulo.model.app.ApplicationGroupDataM;
import com.eaf.orig.ulo.model.app.PersonalInfoDataM;
import com.eaf.orig.ulo.model.compare.CompareDataM;

public class AddressSubForm3 extends ORIGSubForm{
	private static transient Logger logger = Logger.getLogger(AddressSubForm3.class);
	private String PERSONAL_TYPE = SystemConstant.getConstant("PERSONAL_TYPE_APPLICANT");
	String ADDRESS_TYPE_CURRENT = SystemConstant.getConstant("ADDRESS_TYPE_CURRENT");
	String ADDRESS_TYPE_WORK = SystemConstant.getConstant("ADDRESS_TYPE_WORK");
	String ADDRESS_TYPE_DOCUMENT = SystemConstant.getConstant("ADDRESS_TYPE_DOCUMENT");
	String ROLE_DE2 = SystemConstant.getConstant("ROLE_DE2");
	String VAT_REGISTRATION_IMPLEMENT_TYPE = SystemConstant.getConstant("VAT_REGISTRATION_IMPLEMENT_TYPE");
	String ADDRESS_TYPE_NATION = SystemConstant.getConstant("ADDRESS_TYPE_NATION");
	String ADDRESS_TYPE_VAT = SystemConstant.getConstant("ADDRESS_TYPE_VAT");
	
	@Override
	public void setProperties(HttpServletRequest request, Object appForm) {
		ApplicationGroupDataM applicationGroup = ((ApplicationGroupDataM)appForm);		
		PersonalInfoDataM personal = applicationGroup.getPersonalInfo(PERSONAL_TYPE);
				
		AddressDataM addressN = personal.getAddress(ADDRESS_TYPE_NATION);
		if(null == addressN){
			String addressId = GenerateUnique.generate(OrigConstant.SeqNames.ORIG_PERSONAL_ADDRESS_PK);
			addressN = new AddressDataM();
			addressN.setAddressId(addressId);
			addressN.setAddressType(ADDRESS_TYPE_NATION);
			personal.addAddress(addressN);
		}
		String suffixN = ADDRESS_TYPE_NATION;

		String SEND_DOC = request.getParameter("SEND_DOC");
		//String PLACE_RECEIVE_CARD = request.getParameter("PLACE_RECEIVE_CARD");
		//String BRANCH_RECEIVE_CARD = request.getParameter("BRANCH_RECEIVE_CARD");
		String ADDRESS1_N = request.getParameter("ADDRESS1_"+suffixN);
		String STATE_N = request.getParameter("STATE_"+suffixN);
		String COUNTRY_N = request.getParameter("COUNTRY_"+suffixN);
		String ZIPCODE_N = request.getParameter("ZIPCODE_"+suffixN);
		//String BRANCH_RECEIVE_CARD_NAME = request.getParameter("BRANCH_RECEIVE_CARD_NAME");		
		//BRANCH_RECEIVE_CARD_NAME = PersonalInfoUtil.getBranchReceiceName(BRANCH_RECEIVE_CARD, BRANCH_RECEIVE_CARD_NAME);
		
		//logger.debug("BRANCH_RECEIVE_CARD_NAME >>"+BRANCH_RECEIVE_CARD_NAME);
		logger.debug("SEND_DOC >>"+SEND_DOC);
		//logger.debug("PLACE_RECEIVE_CARD >>"+PLACE_RECEIVE_CARD);
		//logger.debug("BRANCH_RECEIVE_CARD >>"+BRANCH_RECEIVE_CARD);
		logger.debug("ADDRESS1 >>"+STATE_N);
		logger.debug("STATE >>"+STATE_N);
		logger.debug("COUNTRY >>"+COUNTRY_N);
		logger.debug("ZIPCODE >>"+ZIPCODE_N);

		personal.setMailingAddress(SEND_DOC);
		personal.setPlaceReceiveCard(SEND_DOC);
		//personal.setPlaceReceiveCard(PLACE_RECEIVE_CARD);
		//personal.setBranchReceiveCard(BRANCH_RECEIVE_CARD);
		//personal.setBranchReceiveCardName(BRANCH_RECEIVE_CARD_NAME);
		
		addressN.setAddress1(ADDRESS1_N);
		addressN.setState(STATE_N);
		addressN.setCountry(DisplayAddressUtil.getCountryCode(COUNTRY_N));
		addressN.setZipcode(ZIPCODE_N);
		
		
		AddressDataM addressV = personal.getAddress(ADDRESS_TYPE_VAT);
		if(null == addressV){
			String addressId = GenerateUnique.generate(OrigConstant.SeqNames.ORIG_PERSONAL_ADDRESS_PK);
			addressV = new AddressDataM();
			addressV.setAddressId(addressId);
			addressV.setAddressType(ADDRESS_TYPE_VAT);
			personal.addAddress(addressV);
		}
		ElementInf element = ImplementControl.getElement(VAT_REGISTRATION_IMPLEMENT_TYPE,"VAT_REGISTRATION");
		if(MConstant.FLAG_Y.equals(element.renderElementFlag(request, addressV))){
			element.processElement(request, addressV);
		}
		
		DisplayAddressUtil.setAddressLine(addressN);
		DisplayAddressUtil.setAddressLine(addressV);
		
	}

	@Override
	public HashMap validateForm(HttpServletRequest request, Object appForm) {
		ApplicationGroupDataM applicationGroup = ((ApplicationGroupDataM)appForm);
		String subformId = "NORMAL_ADDRESS_SUBFORM_3";
		logger.debug("subformId >> "+subformId);
		FormErrorUtil formError = new FormErrorUtil();
		PersonalAddressUtil addressUtil = new PersonalAddressUtil();	
		PersonalInfoDataM personalInfo = applicationGroup.getPersonalInfo(PERSONAL_TYPE);
		int PERSONAL_SEQ = personalInfo.getSeq();
		String roleId = FormControl.getFormRoleId(request);
		String[] DISPLAY_ADDRESS_TYPE = SystemConstant.getConstant("DISPLAY_ADDRESS_TYPE").split("\\,");
		logger.debug("DISPLAY_ADDRESS_TYPE >> "+DISPLAY_ADDRESS_TYPE);
		ArrayList<AddressDataM> addresses = personalInfo.getAddress(DISPLAY_ADDRESS_TYPE);
		if(!Util.empty(addresses)){
			for(AddressDataM address:addresses){
				FormControlDataM formControlM = new FormControlDataM();
//				String formId = getAddressFormId(personalInfo.getMailingAddress(),address.getAddressType(),applicationGroup);
				String formId = addressUtil.getAddressFormId(personalInfo.getMailingAddress(),address.getAddressType(),applicationGroup,roleId);
				formControlM.setFormId(formId);
				formControlM.setRoleId(roleId);
				formControlM.setRequest(request);
				HashMap<String,String> requestData = new HashMap<String, String>();
				requestData.put("PERSONAL_SEQ",FormatUtil.getString(PERSONAL_SEQ));
				requestData.put("PERSONAL_TYPE",personalInfo.getPersonalType());
				formControlM.setRequestData(requestData);
				boolean errorForm = formError.mandatoryForm(formControlM);
				logger.debug("errorForm >> "+errorForm);
				
				String MESSAGE_ERROR = MessageErrorUtil.getText(request, "PREFIX_POPUP_ADDRESS_ERROR");
				String cacheId = SystemConstant.getConstant("FIELD_ID_ADDRESS_TYPE");
				String AddressTypeDesc = CacheControl.getName(cacheId,address.getAddressType());
				
				if(!errorForm){
					formError.error(MESSAGE_ERROR+AddressTypeDesc);
				}
			}
		}
		AddressDataM addressN = personalInfo.getAddress(ADDRESS_TYPE_NATION);				
		formError.mandatory(subformId,"SEND_DOC",personalInfo.getMailingAddress(),request);
		//formError.mandatory(subformId,"PLACE_RECEIVE_CARD",personalInfo.getPlaceReceiveCard(),request);
		//formError.mandatory(subformId,"BRANCH_RECEIVE_CARD",personalInfo.getBranchReceiveCard(),request);
		formError.mandatory(subformId,"ADDRESS1",addressN.getAddress1(),request);
		formError.mandatory(subformId,"STATE",addressN.getState(),request);
		formError.mandatory(subformId,"COUNTRY",addressN.getCountry(),request);
		formError.mandatory(subformId,"ZIPCODE",addressN.getZipcode(),request);
		
		AddressDataM addressV = personalInfo.getAddress(ADDRESS_TYPE_VAT);
		ElementInf element = ImplementControl.getElement(VAT_REGISTRATION_IMPLEMENT_TYPE,"VAT_REGISTRATION");
		if(MConstant.FLAG_Y.equals(element.renderElementFlag(request, addressV))){
			HashMap<String,Object>  errorList = element.validateElement(request, addressV);
			if(!Util.empty(errorList)){
				formError.addAll(errorList);
			}
		}
		
		return formError.getFormError();
	}
	public String getAddressFormId(String sendDoc,String addressType,ApplicationGroupDataM applicationGroup){
		boolean matchesCardLink = applicationGroup.matchesCardLinkByproduct();
		logger.debug("matchesCardLink : "+matchesCardLink);
		String popupForm="";
		String formType = "_2";
		if(!Util.empty(sendDoc) && !Util.empty(addressType) && !addressType.equals(sendDoc) && !addressType.equals(ADDRESS_TYPE_DOCUMENT)){
			formType = "_1";
		}
		if(!matchesCardLink){
			formType = "_3";
		}		
		if(ADDRESS_TYPE_CURRENT.equals(addressType)){
			popupForm="POPUP_CURRENT_ADDRESS_FORM"+formType;
		}else if(ADDRESS_TYPE_WORK.equals(addressType)){
			popupForm="POPUP_OFFICE_ADDRESS_FORM"+formType;
		}else if(ADDRESS_TYPE_DOCUMENT.equals(addressType)){
			popupForm="POPUP_HOME_ADDRESS_FORM"+formType;
		}
		logger.debug("popupForm >> "+popupForm);
		return popupForm;
	}
	@Override
	public boolean validateSubForm(HttpServletRequest request) {
		return false;
	}	
	@Override
	public void displayValueForm(HttpServletRequest request, Object objectForm) {
		String subformId = "NORMAL_ADDRESS_SUBFORM_3";
		ApplicationGroupDataM applicationGroup = (ApplicationGroupDataM)objectForm;
		FormDisplayValueUtil formValue = new FormDisplayValueUtil(formId,formLevel,roleId,subformId,applicationGroup,request);
		PersonalInfoDataM personalInfo = applicationGroup.getPersonalInfo(PERSONAL_TYPE);
		String PREFIX_ELEMENT_ID = CompareSensitiveFieldUtil.getSuffixFieldName(CompareDataM.UniqueLevel.PERSONAL,personalInfo.getPersonalId());//CompareSensitiveFieldUtil.getPersonalRefId(personalInfo);
		AddressDataM  addressWork = personalInfo.getAddress(ADDRESS_TYPE_WORK);
		if(!Util.empty(addressWork)){
			addressWork.setEditFlag(formValue.getValue("SEND_DOC", "SEND_DOC_"+PREFIX_ELEMENT_ID, addressWork.getEditFlag()));
		}
		AddressDataM  addressCurrent = personalInfo.getAddress(ADDRESS_TYPE_CURRENT);
		if(!Util.empty(addressCurrent)){
			addressCurrent.setEditFlag(formValue.getValue("SEND_DOC", "SEND_DOC_"+PREFIX_ELEMENT_ID, addressCurrent.getEditFlag()));
		}
		AddressDataM  addressDocument = personalInfo.getAddress(ADDRESS_TYPE_DOCUMENT);
		if(!Util.empty(addressDocument)){
			addressDocument.setEditFlag(formValue.getValue("SEND_DOC", "SEND_DOC_"+PREFIX_ELEMENT_ID, addressDocument.getEditFlag()));
		}
	}
	
	@Override
	public ArrayList<AuditDataM> auditForm(HttpServletRequest request,Object objectForm, Object lastObjectForm) {	
		ApplicationGroupDataM applicationGroup = ((ApplicationGroupDataM)objectForm);
		ApplicationGroupDataM lastApplicationGroup = ((ApplicationGroupDataM)lastObjectForm);
		PersonalInfoDataM personalInfo = applicationGroup.getPersonalInfo(PERSONAL_TYPE);
		PersonalInfoDataM lastPersonalInfo = lastApplicationGroup.getPersonalInfo(PERSONAL_TYPE);
		
		String ADDRESS_TYPE_CURRENT = SystemConstant.getConstant("ADDRESS_TYPE_CURRENT");
		String ADDRESS_TYPE_WORK = SystemConstant.getConstant("ADDRESS_TYPE_WORK");
		String ADDRESS_TYPE_DOCUMENT = SystemConstant.getConstant("ADDRESS_TYPE_DOCUMENT");
		
		AddressDataM currentAddress = personalInfo.getAddress(ADDRESS_TYPE_CURRENT);	
		AddressDataM workAddress = personalInfo.getAddress(ADDRESS_TYPE_WORK);
		AddressDataM documentAddress = personalInfo.getAddress(ADDRESS_TYPE_DOCUMENT);
		
		AddressDataM lastcurrentAddress = lastPersonalInfo.getAddress(ADDRESS_TYPE_CURRENT);	
		AddressDataM lastworkAddress = lastPersonalInfo.getAddress(ADDRESS_TYPE_WORK);
		AddressDataM lastdocumentAddress = lastPersonalInfo.getAddress(ADDRESS_TYPE_DOCUMENT);
		
		AuditFormUtil auditFormUtil = new AuditFormUtil();
		String subformId = getSubFormID();
		auditFormUtil.setObjectValue(PERSONAL_TYPE);
		
		auditFormUtil.auditForm(subformId, "ADDRESS_ID", currentAddress,lastcurrentAddress, request);
		auditFormUtil.auditForm(subformId, "ADDRESS_ID", workAddress,lastworkAddress, request);
		auditFormUtil.auditForm(subformId, "ADDRESS_ID", documentAddress,lastdocumentAddress, request);
		auditFormUtil.auditForm(subformId, "ZIPCODE", currentAddress,lastcurrentAddress, request);
		auditFormUtil.auditForm(subformId, "ZIPCODE", workAddress,lastworkAddress, request);
		auditFormUtil.auditForm(subformId, "ZIPCODE", documentAddress,lastdocumentAddress, request);
		auditFormUtil.auditForm(subformId, "PROVINCE", currentAddress,lastcurrentAddress, request);
		auditFormUtil.auditForm(subformId, "PROVINCE", workAddress,lastworkAddress, request);
		auditFormUtil.auditForm(subformId, "PROVINCE", documentAddress,lastdocumentAddress, request);
		auditFormUtil.auditForm(subformId, "COMPANY_NAME", workAddress,lastworkAddress, request);
		//auditFormUtil.auditForm(subformId, "BRANCH_RECEIVE_CARD", personalInfo,lastPersonalInfo, request);
		//auditFormUtil.auditForm(subformId, "PLACE_RECEIVE_CARD", personalInfo,lastPersonalInfo, request);
		auditFormUtil.auditForm(subformId, "SEND_DOC", personalInfo,lastPersonalInfo, request);
		return auditFormUtil.getAuditForm();
	}
}
