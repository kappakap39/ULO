package com.eaf.orig.ulo.app.view.form.subform.sup;

import java.util.ArrayList;
import java.util.HashMap;

import javax.servlet.http.HttpServletRequest;

import org.apache.log4j.Logger;

import com.eaf.core.ulo.common.display.FormatUtil;
import com.eaf.core.ulo.common.message.MessageErrorUtil;
import com.eaf.core.ulo.common.model.AuditDataM;
import com.eaf.core.ulo.common.model.FormControlDataM;
import com.eaf.core.ulo.common.properties.CacheControl;
import com.eaf.core.ulo.common.properties.FormControl;
import com.eaf.core.ulo.common.properties.SystemConstant;
import com.eaf.core.ulo.common.util.AuditFormUtil;
import com.eaf.core.ulo.common.util.FormErrorUtil;
import com.eaf.core.ulo.common.util.Util;
import com.eaf.orig.shared.constant.OrigConstant;
import com.eaf.orig.shared.view.form.ORIGSubForm;
import com.eaf.orig.ulo.app.util.GenerateUnique;
import com.eaf.orig.ulo.control.address.util.DisplayAddressUtil;
import com.eaf.orig.ulo.control.util.PersonalAddressUtil;
import com.eaf.orig.ulo.control.util.PersonalInfoUtil;
import com.eaf.orig.ulo.model.app.AddressDataM;
import com.eaf.orig.ulo.model.app.ApplicationGroupDataM;
import com.eaf.orig.ulo.model.app.PersonalApplicationInfoDataM;
import com.eaf.orig.ulo.model.app.PersonalInfoDataM;

public class AddressSubForm3 extends ORIGSubForm{
	private static transient Logger logger = Logger.getLogger(AddressSubForm3.class);
	String subformId = "SUP_ADDRESS_SUBFORM_3";
	String ADDRESS_TYPE_CURRENT = SystemConstant.getConstant("ADDRESS_TYPE_CURRENT");
	String ADDRESS_TYPE_WORK = SystemConstant.getConstant("ADDRESS_TYPE_WORK");
	String ADDRESS_TYPE_DOCUMENT = SystemConstant.getConstant("ADDRESS_TYPE_DOCUMENT");
	String[] DISPLAY_ADDRESS_TYPE = SystemConstant.getConstant("DISPLAT_ADDRESS_TYPE_SUPPLEMENT").split("\\,");
	String PERSONAL_TYPE_SUPPLEMENTARY = SystemConstant.getConstant("PERSONAL_TYPE_SUPPLEMENTARY");
	
	@Override
	public void setProperties(HttpServletRequest request, Object appForm) {
		PersonalApplicationInfoDataM personalApplicationInfo = (PersonalApplicationInfoDataM)appForm;
		PersonalInfoDataM personalInfo = personalApplicationInfo.getPersonalInfo();				
		AddressDataM address = personalInfo.getAddress(ADDRESS_TYPE_CURRENT);
		if(null == address){
			String addressId = GenerateUnique.generate(OrigConstant.SeqNames.ORIG_PERSONAL_ADDRESS_PK);
			address = new AddressDataM();
			address.setAddressId(addressId);
			address.setAddressType(ADDRESS_TYPE_CURRENT);
			personalInfo.addAddress(address);
		}	
		
		String SEND_DOC = request.getParameter("SEND_DOC");
		//String PLACE_RECEIVE_CARD = request.getParameter("PLACE_RECEIVE_CARD");
		//String BRANCH_RECEIVE_CARD = request.getParameter("BRANCH_RECEIVE_CARD");
		//String BRANCH_RECEIVE_CARD_NAME = request.getParameter("BRANCH_RECEIVE_CARD_NAME");		
		//BRANCH_RECEIVE_CARD_NAME = PersonalInfoUtil.getBranchReceiceName(BRANCH_RECEIVE_CARD, BRANCH_RECEIVE_CARD_NAME);
		logger.debug("SEND_DOC >>"+SEND_DOC);
		//logger.debug("PLACE_RECEIVE_CARD >>"+PLACE_RECEIVE_CARD);
		//logger.debug("BRANCH_RECEIVE_CARD >>"+BRANCH_RECEIVE_CARD);
		//logger.debug("BRANCH_RECEIVE_CARD_NAME >>"+BRANCH_RECEIVE_CARD_NAME);
		personalInfo.setMailingAddress(SEND_DOC);
		personalInfo.setPlaceReceiveCard(SEND_DOC);
		//personalInfo.setPlaceReceiveCard(PLACE_RECEIVE_CARD);
		//personalInfo.setBranchReceiveCard(BRANCH_RECEIVE_CARD);
		//personalInfo.setBranchReceiveCardName(BRANCH_RECEIVE_CARD_NAME);
		DisplayAddressUtil.setAddressLine(address);
	}

	@Override
	public HashMap validateForm(HttpServletRequest request, Object appForm) {
		PersonalApplicationInfoDataM personalApplicationInfo = (PersonalApplicationInfoDataM)appForm;
		PersonalInfoDataM personalInfo = personalApplicationInfo.getPersonalInfo();				
		PersonalAddressUtil addressUtil = new PersonalAddressUtil();
		logger.debug("subformId >> "+subformId);		
		FormErrorUtil formError = new FormErrorUtil(personalApplicationInfo);
		AddressDataM currentAddress = personalInfo.getAddress(ADDRESS_TYPE_CURRENT);
		if(Util.empty(currentAddress)) {
			currentAddress = new AddressDataM();
		}
		int PERSONAL_SEQ = personalInfo.getSeq();
		ApplicationGroupDataM applicationGroup = FormControl.getOrigObjectForm(request);
		String roleId = FormControl.getFormRoleId(request);
		ArrayList<AddressDataM> addresses = personalInfo.getAddress(DISPLAY_ADDRESS_TYPE);
		if(!Util.empty(addresses)){
			for(AddressDataM address:addresses){
				if(!Util.empty(address.getAddressType()) && !Util.empty(personalInfo.getMailingAddress())){
					FormControlDataM formControlM = new FormControlDataM();
	//				String formId = getAddressFormId(personalInfo.getMailingAddress(),address.getAddressType(),applicationGroup);
					String formId = addressUtil.getAddressFormId(personalInfo.getMailingAddress(),address.getAddressType(),applicationGroup,roleId);
					formControlM.setFormId(formId);
					formControlM.setRoleId(roleId);
					formControlM.setRequest(request);
					formControlM.setProcessForm(processForm);
					HashMap<String,String> requestData = new HashMap<String, String>();
					requestData.put("PERSONAL_SEQ",FormatUtil.getString(PERSONAL_SEQ));
					requestData.put("PERSONAL_TYPE",personalInfo.getPersonalType());
					requestData.put("PERSONAL_ID",personalInfo.getPersonalId());
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
		}
		
		formError.mandatory(subformId, "SEND_DOC", "", "SEND_DOC_SUB", personalInfo.getMailingAddress(), request);
		//formError.mandatory(subformId,"PLACE_RECEIVE_CARD","","PLACE_RECEIVE_CARD_SUB",personalInfo.getPlaceReceiveCard(),request);
		//formError.mandatory(subformId,"BRANCH_RECEIVE_CARD","","BRANCH_RECEIVE_CARD_SUB",personalInfo.getBranchReceiveCard(),request);	
		return formError.getFormError();
	}
	@Override
	public boolean validateSubForm(HttpServletRequest request) {
		return false;
	}

	@Override
	public ArrayList<AuditDataM> auditForm(HttpServletRequest request, Object objectForm, Object lastObjectForm) {
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
		
		auditFormUtil.auditForm(subformId, "ZIPCODE", currentAddress, lastcurrentAddress, request);
		auditFormUtil.auditForm(subformId, "ZIPCODE", workAddress, lastworkAddress, request);
		auditFormUtil.auditForm(subformId, "PROVINCE", currentAddress,lastcurrentAddress, request);
		auditFormUtil.auditForm(subformId, "PROVINCE", workAddress,lastworkAddress, request);
		//auditFormUtil.auditForm(subformId, "BRANCH_RECEIVE_CARD", personalSupplementary,lastPersonalSupplementary, request);
		auditFormUtil.auditForm(subformId, "SEND_DOC", personalSupplementary,lastPersonalSupplementary, request);
		//auditFormUtil.auditForm(subformId, "PLACE_RECEIVE_CARD", personalSupplementary,lastPersonalSupplementary, request);
		return auditFormUtil.getAuditForm();
	}
}
