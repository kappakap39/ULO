package com.eaf.orig.ulo.app.view.form.subform.normal;

import java.util.ArrayList;
import java.util.HashMap;

import javax.servlet.http.HttpServletRequest;

import org.apache.log4j.Logger;

import com.eaf.core.ulo.common.display.FormatUtil;
import com.eaf.core.ulo.common.message.MessageErrorUtil;
import com.eaf.core.ulo.common.model.AuditDataM;
import com.eaf.core.ulo.common.model.FieldElement;
import com.eaf.core.ulo.common.model.FormControlDataM;
import com.eaf.core.ulo.common.properties.CacheControl;
import com.eaf.core.ulo.common.properties.FormControl;
import com.eaf.core.ulo.common.properties.SystemConstant;
import com.eaf.core.ulo.common.util.AuditFormUtil;
import com.eaf.core.ulo.common.util.FormDisplayValueUtil;
import com.eaf.core.ulo.common.util.FormErrorUtil;
import com.eaf.core.ulo.common.util.Util;
import com.eaf.orig.shared.view.form.ORIGSubForm;
import com.eaf.orig.ulo.app.view.form.sensitive.util.CompareSensitiveFieldUtil;
import com.eaf.orig.ulo.app.view.util.kpl.KPLUtil;
import com.eaf.orig.ulo.constant.MConstant;
import com.eaf.orig.ulo.control.util.PersonalAddressUtil;
import com.eaf.orig.ulo.control.util.PersonalInfoUtil;
import com.eaf.orig.ulo.model.app.AddressDataM;
import com.eaf.orig.ulo.model.app.ApplicationGroupDataM;
import com.eaf.orig.ulo.model.app.PersonalInfoDataM;
import com.eaf.orig.ulo.model.compare.CompareDataM;

@SuppressWarnings("serial")
public class AddressSubForm2 extends ORIGSubForm{
	private static transient Logger logger = Logger.getLogger(AddressSubForm2.class);
	String subformId = "NORMAL_ADDRESS_SUBFORM_2";
	String PERSONAL_TYPE = SystemConstant.getConstant("PERSONAL_TYPE_APPLICANT");	
	String ADDRESS_TYPE_CURRENT = SystemConstant.getConstant("ADDRESS_TYPE_CURRENT");
	String ADDRESS_TYPE_WORK = SystemConstant.getConstant("ADDRESS_TYPE_WORK");
	String ADDRESS_TYPE_DOCUMENT = SystemConstant.getConstant("ADDRESS_TYPE_DOCUMENT");
	String ROLE_DE1_2 = SystemConstant.getConstant("ROLE_DE1_2");
	String[] DISPLAY_ADDRESS_TYPE = SystemConstant.getConstant("DISPLAY_ADDRESS_TYPE").split("\\,");
	
	@Override
	public void setProperties(HttpServletRequest request, Object appForm) {
		ApplicationGroupDataM applicationGroup = ((ApplicationGroupDataM)appForm);		
		PersonalInfoDataM personalInfo = applicationGroup.getPersonalInfo(PERSONAL_TYPE);
				
		String SEND_DOC = request.getParameter("SEND_DOC");
		//String PLACE_RECEIVE_CARD = request.getParameter("PLACE_RECEIVE_CARD");
		//String BRANCH_RECEIVE_CARD = request.getParameter("BRANCH_RECEIVE_CARD");
		//String BRANCH_RECEIVE_CARD_NAME = request.getParameter("BRANCH_RECEIVE_CARD_NAME");		
		//BRANCH_RECEIVE_CARD_NAME = PersonalInfoUtil.getBranchReceiceName(BRANCH_RECEIVE_CARD, BRANCH_RECEIVE_CARD_NAME);	
		logger.debug("SEND_DOC >>"+SEND_DOC);	
		//logger.debug("PLACE_RECEIVE_CARD >>"+PLACE_RECEIVE_CARD);	
		//logger.debug("BRANCH_RECEIVE_CARD >>"+BRANCH_RECEIVE_CARD);	
		//logger.debug("BRANCH_RECEIVE_CARD_NAME >> "+BRANCH_RECEIVE_CARD_NAME);
		personalInfo.setMailingAddress(SEND_DOC);
		personalInfo.setPlaceReceiveCard(SEND_DOC);
		//personalInfo.setPlaceReceiveCard(PLACE_RECEIVE_CARD);
		//personalInfo.setBranchReceiveCard(BRANCH_RECEIVE_CARD);
		//personalInfo.setBranchReceiveCardName(BRANCH_RECEIVE_CARD_NAME);
	}

	@SuppressWarnings({ "unchecked", "rawtypes" })
	@Override
	public HashMap validateForm(HttpServletRequest request, Object appForm) {
		ApplicationGroupDataM applicationGroup = ((ApplicationGroupDataM)appForm);
		logger.debug("subformId >> "+subformId);
		FormErrorUtil formError = new FormErrorUtil();	
		PersonalAddressUtil addressUtil = new PersonalAddressUtil();	
		PersonalInfoDataM personalInfo = applicationGroup.getPersonalInfo(PERSONAL_TYPE);
		int PERSONAL_SEQ = personalInfo.getSeq();
		String roleId = FormControl.getFormRoleId(request);
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
				String addressTypeDesc = CacheControl.getName(cacheId,address.getAddressType());				
				if(!errorForm){
					formError.error(MESSAGE_ERROR+addressTypeDesc);
				}
			}
		}
		formError.mandatory(subformId,"SEND_DOC",personalInfo.getMailingAddress(),request);
		//formError.mandatory(subformId,"PLACE_RECEIVE_CARD",personalInfo.getPlaceReceiveCard(),request);
		//formError.mandatory(subformId,"BRANCH_RECEIVE_CARD",personalInfo.getBranchReceiveCard(),request);		
		return formError.getFormError();
	}
	@Override
	public boolean validateSubForm(HttpServletRequest request) {
		return false;
	}
	@Override
	public void displayValueForm(HttpServletRequest request, Object objectForm) {
		ApplicationGroupDataM applicationGroup = (ApplicationGroupDataM)objectForm;
		FormDisplayValueUtil formValue = new FormDisplayValueUtil(formId,formLevel,roleId,subformId,applicationGroup,request);
		PersonalAddressUtil addressUtil = new PersonalAddressUtil();
		PersonalInfoDataM personalInfo = applicationGroup.getPersonalInfo(PERSONAL_TYPE);
		if (null == personalInfo) {
			personalInfo = new PersonalInfoDataM();
		}
//		String PREFIX_ELEMENT_ID = CompareSensitiveFieldUtil.getPersonalRefId(personalInfo);
		String PREFIX_ELEMENT_ID = CompareSensitiveFieldUtil.getSuffixFieldName(CompareDataM.UniqueLevel.PERSONAL,personalInfo.getPersonalId());
		personalInfo.setMailingAddress(formValue.getValue("SEND_DOC","SEND_DOC_"+PREFIX_ELEMENT_ID,personalInfo.getMailingAddress()));
		personalInfo.setPlaceReceiveCard(formValue.getValue("PLACE_RECEIVE_CARD","PLACE_RECEIVE_CARD_"+PREFIX_ELEMENT_ID,personalInfo.getPlaceReceiveCard()));
		personalInfo.setBranchReceiveCard(formValue.getValue("BRANCH_RECEIVE_CARD","BRANCH_RECEIVE_CARD_"+PREFIX_ELEMENT_ID,personalInfo.getBranchReceiveCard()));
		
		String roleId = FormControl.getFormRoleId(request);
		ArrayList<AddressDataM> addresses = personalInfo.getAddress(DISPLAY_ADDRESS_TYPE);
		if(!Util.empty(addresses)){
			for (AddressDataM address : addresses) {	
//				String formId = getAddressFormId(personalInfo.getMailingAddress(),address.getAddressType(),applicationGroup);			
				String formId = addressUtil.getAddressFormId(personalInfo.getMailingAddress(),address.getAddressType(),applicationGroup,roleId);
				ArrayList<ORIGSubForm> addressSubforms = FormControl.getSubForm(formId,roleId);
				if(!Util.empty(addressSubforms)){
					for (ORIGSubForm subForm : addressSubforms) {
						if(MConstant.FLAG.YES.equals(subForm.renderSubformFlag(request,objectForm))){
							subForm.setFormId(formId);
							subForm.setFormLevel(formLevel);
							subForm.setRoleId(roleId);
							subForm.addSubformData("ADDRESS_ID",address.getAddressId());
							subForm.addSubformData("PERSONAL_TYPE",PERSONAL_TYPE);
							if(Util.empty(applicationGroup.getReprocessFlag())){
								subForm.displayValueForm(request, applicationGroup);
							}
						}
					}
				}
			}
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
		auditFormUtil.auditForm(subformId, "COMPANY_NAME", workAddress,lastworkAddress, request);
		auditFormUtil.auditForm(subformId, "PROVINCE", currentAddress,lastcurrentAddress, request);
		auditFormUtil.auditForm(subformId, "PROVINCE", workAddress,lastworkAddress, request);
		auditFormUtil.auditForm(subformId, "PROVINCE", documentAddress,lastdocumentAddress, request);
		//auditFormUtil.auditForm(subformId, "BRANCH_RECEIVE_CARD", personalInfo,lastPersonalInfo, request);
		auditFormUtil.auditForm(subformId, "ADDRESS_STYLE", currentAddress,lastcurrentAddress, request);
		
		//auditFormUtil.auditForm(subformId, "PLACE_RECEIVE_CARD", personalInfo,lastPersonalInfo, request);
		auditFormUtil.auditForm(subformId, "SEND_DOC", personalInfo,lastPersonalInfo, request);
		auditFormUtil.auditForm(subformId, "ADRSTS", currentAddress,lastcurrentAddress, request);
		
		return auditFormUtil.getAuditForm();
	}
	
	@Override
	public ArrayList<FieldElement> elementForm(HttpServletRequest request,Object objectForm) {
		PersonalAddressUtil addressUtil = new PersonalAddressUtil();
		ArrayList<FieldElement> fieldElements = new ArrayList<FieldElement>();
		
		ApplicationGroupDataM applicationGroup = ((ApplicationGroupDataM)objectForm);			
		PersonalInfoDataM personalInfo = applicationGroup.getPersonalInfo(PERSONAL_TYPE);
		String roleId = FormControl.getFormRoleId(request);
		ArrayList<AddressDataM> addresses = personalInfo.getAddress(DISPLAY_ADDRESS_TYPE);
		if(!Util.empty(addresses)){
			for (AddressDataM address : addresses) {			
				String formId = addressUtil.getAddressFormId(personalInfo.getMailingAddress(),address.getAddressType(),applicationGroup,roleId);
				logger.debug("formId>>>"+formId);
				ArrayList<ORIGSubForm> addressSubforms = FormControl.getSubForm(formId,roleId);
				if(!Util.empty(addressSubforms)){
					for (ORIGSubForm subForm : addressSubforms) {
						if(MConstant.FLAG.YES.equals(subForm.renderSubformFlag(request,objectForm))){
							subForm.setFormId(formId);
							subForm.setFormLevel(formLevel);
							subForm.setRoleId(roleId);
							subForm.addSubformData("ADDRESS_ID",address.getAddressId());
							subForm.addSubformData("PERSONAL_TYPE",PERSONAL_TYPE);
							ArrayList<FieldElement> popupFieldElements = subForm.elementForm(request, applicationGroup);
							if(!Util.empty(popupFieldElements)){
								fieldElements.addAll(popupFieldElements);
							}		
						}
					}
				}
			}
		}
		
		//String[] filedNames ={"SEND_DOC","PLACE_RECEIVE_CARD","BRANCH_RECEIVE_CARD"};
		String[] filedNames ={"SEND_DOC"};
		 for(String elementId:filedNames){
			 FieldElement fieldElement = new FieldElement();
			 fieldElement.setElementId(elementId);
			 fieldElement.setElementLevel(CompareDataM.UniqueLevel.PERSONAL);
			 fieldElement.setElementGroupId(personalInfo.getPersonalId());
			 fieldElement.setElementGroupLevel(CompareSensitiveFieldUtil.getGroupDataLevelByPersonal(personalInfo));
			 fieldElements.add(fieldElement);
		 }
		return fieldElements;
	}
}
