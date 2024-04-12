package com.eaf.orig.ulo.app.view.form.subform.sup;

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
import com.eaf.orig.ulo.constant.MConstant;
import com.eaf.orig.ulo.control.util.PersonalAddressUtil;
import com.eaf.orig.ulo.control.util.PersonalInfoUtil;
import com.eaf.orig.ulo.model.app.AddressDataM;
import com.eaf.orig.ulo.model.app.ApplicationGroupDataM;
import com.eaf.orig.ulo.model.app.PersonalApplicationInfoDataM;
import com.eaf.orig.ulo.model.app.PersonalInfoDataM;
import com.eaf.orig.ulo.model.compare.CompareDataM;

@SuppressWarnings("serial")
public class AddressSubForm2 extends ORIGSubForm{
	private static transient Logger logger = Logger.getLogger(AddressSubForm2.class);
	String subformId = "SUP_ADDRESS_SUBFORM_2";
	String ADDRESS_TYPE_CURRENT = SystemConstant.getConstant("ADDRESS_TYPE_CURRENT");
	String ADDRESS_TYPE_WORK = SystemConstant.getConstant("ADDRESS_TYPE_WORK");
	String ADDRESS_TYPE_DOCUMENT = SystemConstant.getConstant("ADDRESS_TYPE_DOCUMENT");
	String[] DISPLAY_ADDRESS_TYPE = SystemConstant.getConstant("DISPLAT_ADDRESS_TYPE_SUPPLEMENT").split("\\,");
	String PERSONAL_TYPE_SUPPLEMENTARY = SystemConstant.getConstant("PERSONAL_TYPE_SUPPLEMENTARY");
	
	@Override
	public void setProperties(HttpServletRequest request, Object appForm) {
		PersonalApplicationInfoDataM personalApplicationInfo = (PersonalApplicationInfoDataM)appForm;
		PersonalInfoDataM personalInfo = personalApplicationInfo.getPersonalInfo();
				
		String SEND_DOC = request.getParameter("SEND_DOC");
		//String PLACE_RECEIVE_CARD = request.getParameter("PLACE_RECEIVE_CARD");
		//String BRANCH_RECEIVE_CARD = request.getParameter("BRANCH_RECEIVE_CARD");
		//String BRANCH_RECEIVE_CARD_NAME = request.getParameter("BRANCH_RECEIVE_CARD_NAME");
		//BRANCH_RECEIVE_CARD_NAME = PersonalInfoUtil.getBranchReceiceName(BRANCH_RECEIVE_CARD, BRANCH_RECEIVE_CARD_NAME);
		
		logger.debug("SEND_DOC >>"+SEND_DOC);
		//logger.debug("PLACE_RECEIVE_CARD >>"+PLACE_RECEIVE_CARD);
		//logger.debug("BRANCH_RECEIVE_CARD >>"+BRANCH_RECEIVE_CARD);
		//logger.debug("BRANCH_RECEIVE_CARD_NAME >>"+BRANCH_RECEIVE_CARD_NAME);
		logger.debug("PERSONAL TYPE >>"+personalInfo.getPersonalType());
		
		personalInfo.setMailingAddress(SEND_DOC);
		personalInfo.setPlaceReceiveCard(SEND_DOC);
		
		//personalInfo.setPlaceReceiveCard(PLACE_RECEIVE_CARD);
		//personalInfo.setBranchReceiveCard(BRANCH_RECEIVE_CARD);
		//personalInfo.setBranchReceiveCardName(BRANCH_RECEIVE_CARD_NAME);
	}

	@SuppressWarnings({ "rawtypes", "unchecked" })
	@Override
	public HashMap validateForm(HttpServletRequest request, Object appForm) {
		ApplicationGroupDataM applicationGroup = (ApplicationGroupDataM)FormControl.getOrigObjectForm(request);
		PersonalApplicationInfoDataM personalApplicationInfo = (PersonalApplicationInfoDataM)appForm;
		PersonalInfoDataM personalInfo = personalApplicationInfo.getPersonalInfo();
		logger.debug("subformId >> "+subformId);
		FormErrorUtil formError = new FormErrorUtil(personalApplicationInfo);
		PersonalAddressUtil addressUtil = new PersonalAddressUtil();
		String mailingAddress = personalInfo.getMailingAddress();
		logger.debug("mailingAddress : "+mailingAddress);
		
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
				formControlM.setProcessForm(processForm);
				logger.debug("processForm : "+processForm);
				HashMap<String,String> requestData = new HashMap<String, String>();
				requestData.put("PERSONAL_SEQ",FormatUtil.getString(PERSONAL_SEQ));
				requestData.put("PERSONAL_TYPE",personalInfo.getPersonalType());
				requestData.put("PERSONAL_ID",personalInfo.getPersonalId());
				logger.debug("formId : "+formId);
				logger.debug("personalInfo.getPersonalId() : "+personalInfo.getPersonalId());
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
		
		formError.mandatory(subformId, "SEND_DOC", "", "SEND_DOC_SUB", mailingAddress, request);
		//formError.mandatory(subformId,"PLACE_RECEIVE_CARD","","PLACE_RECEIVE_CARD_SUB",personalInfo.getPlaceReceiveCard(),request);
		//formError.mandatory(subformId,"BRANCH_RECEIVE_CARD","","BRANCH_RECEIVE_CARD_SUB",personalInfo.getBranchReceiveCard(),request);		
		return formError.getFormError();
	}

	@Override
	public boolean validateSubForm(HttpServletRequest request) {
		return false;
	}
	
	@Override
	public void displayValueForm(HttpServletRequest request, Object objectForm){		
		logger.debug("displayValueForm.subformId : "+subformId);
		ApplicationGroupDataM applicationGroup = (ApplicationGroupDataM)objectForm;
		String personalId = getSubformData("PERSONAL_ID");
		logger.debug("personalId : "+personalId);
		FormDisplayValueUtil formValue = new FormDisplayValueUtil(formId,formLevel,roleId,subformId,applicationGroup,request);		
		PersonalAddressUtil addressUtil = new PersonalAddressUtil();
		PersonalInfoDataM personalInfo = applicationGroup.getPersonalById(personalId);	
		if(null != personalInfo){	
			String PERSONAL_TYPE = personalInfo.getPersonalType();
//			String PREFIX_ELEMENT_ID = CompareSensitiveFieldUtil.getPersonalRefId(personalInfo);
			String PREFIX_ELEMENT_ID = CompareSensitiveFieldUtil.getSuffixFieldName(CompareDataM.UniqueLevel.PERSONAL,personalInfo.getPersonalId());
			logger.debug("PERSONAL_TYPE : "+PERSONAL_TYPE);
			logger.debug("PREFIX_ELEMENT_ID : "+PREFIX_ELEMENT_ID);
			logger.debug("personalInfo.getMailingAddress : "+personalInfo.getMailingAddress());
			personalInfo.setMailingAddress(formValue.getValue("SEND_DOC","SEND_DOC_"+PREFIX_ELEMENT_ID,personalInfo.getMailingAddress()));
			logger.debug("personalInfo.getMailingAddress : "+personalInfo.getMailingAddress());
			personalInfo.setPlaceReceiveCard(formValue.getValue("SEND_DOC","SEND_DOC_"+PREFIX_ELEMENT_ID,personalInfo.getMailingAddress()));
			
			//personalInfo.setPlaceReceiveCard(formValue.getValue("PLACE_RECEIVE_CARD","PLACE_RECEIVE_CARD_"+PREFIX_ELEMENT_ID,personalInfo.getPlaceReceiveCard()));
			//personalInfo.setBranchReceiveCard(formValue.getValue("BRANCH_RECEIVE_CARD","BRANCH_RECEIVE_CARD_"+PREFIX_ELEMENT_ID,personalInfo.getBranchReceiveCard()));
			
			String roleId = FormControl.getFormRoleId(request);
			ArrayList<AddressDataM> addresses = personalInfo.getAddress(DISPLAY_ADDRESS_TYPE);
			if(!Util.empty(addresses)){
				for (AddressDataM address : addresses) {	
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
	}
	
	@Override
	public ArrayList<AuditDataM> auditForm(HttpServletRequest request, Object objectForm, Object lastObjectForm) {
		AuditFormUtil auditFormUtil = new AuditFormUtil();
		logger.debug("AddressSubForm2.auditForm");
		try{
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
			
			logger.debug("getSubFormId() >> "+getSubFormID());
			
			auditFormUtil.setObjectValue(PERSONAL_TYPE_SUPPLEMENTARY);
			
			auditFormUtil.auditForm(subformId, "ADDRESS_ID", currentAddress,lastcurrentAddress, request);
			auditFormUtil.auditForm(subformId, "ADDRESS_ID", workAddress,lastworkAddress, request);
			
			auditFormUtil.auditForm(subformId, "ZIPCODE", currentAddress, lastcurrentAddress, request);
			auditFormUtil.auditForm(subformId, "ZIPCODE", workAddress, lastworkAddress, request);
			auditFormUtil.auditForm(subformId, "PROVINCE", currentAddress,lastcurrentAddress, request);
			auditFormUtil.auditForm(subformId, "PROVINCE", workAddress,lastworkAddress, request);
			auditFormUtil.auditForm(subformId, "COMPANY_NAME", workAddress,lastworkAddress, request);
			//auditFormUtil.auditForm(subformId, "BRANCH_RECEIVE_CARD", personalSupplementary,lastPersonalSupplementary, request);
			auditFormUtil.auditForm(subformId, "SEND_DOC", personalSupplementary,lastPersonalSupplementary, request);
			//auditFormUtil.auditForm(subformId, "PLACE_RECEIVE_CARD", personalSupplementary,lastPersonalSupplementary, request);
			
		}catch(Exception e){
			logger.fatal("ERROR ",e);
		}
		return auditFormUtil.getAuditForm();
	}
	
	@Override
	public ArrayList<FieldElement> elementForm(HttpServletRequest request,Object objectForm) {
		ArrayList<FieldElement> fieldElements = new ArrayList<FieldElement>();
		//String[] filedNames ={"SEND_DOC","PLACE_RECEIVE_CARD","BRANCH_RECEIVE_CARD"};
		String[] filedNames ={"SEND_DOC"};
		try {
			ApplicationGroupDataM applicationGroup = FormControl.getOrigObjectForm(request);
			PersonalInfoDataM personalInfo = (PersonalInfoDataM)objectForm;
			logger.debug("PEROSNAL_ID>>>"+personalInfo.getPersonalId());
			if(!Util.empty(personalInfo)){
				 for(String elementId:filedNames){
					 FieldElement fieldElement = new FieldElement();
					 fieldElement.setElementId(elementId);
					 fieldElement.setElementLevel(CompareDataM.UniqueLevel.PERSONAL);
					 fieldElement.setElementGroupId(personalInfo.getPersonalId());
					 fieldElement.setElementGroupLevel(CompareSensitiveFieldUtil.getGroupDataLevelByPersonal(personalInfo));
					 fieldElements.add(fieldElement);
				 }
				 
				String roleId = FormControl.getFormRoleId(request);
				PersonalAddressUtil addressUtil = new PersonalAddressUtil();
				ArrayList<AddressDataM> addresses = personalInfo.getAddress(DISPLAY_ADDRESS_TYPE);
				if(!Util.empty(addresses)){
					for (AddressDataM address : addresses) {	
						String formId = addressUtil.getAddressFormId(personalInfo.getMailingAddress(),address.getAddressType(),applicationGroup,roleId);		
						ArrayList<ORIGSubForm> addressSubforms = FormControl.getSubForm(formId,roleId);
						if(!Util.empty(addressSubforms)){
							for (ORIGSubForm subForm : addressSubforms) {
								if(MConstant.FLAG.YES.equals(subForm.renderSubformFlag(request,objectForm))){
									subForm.setFormId(formId);
									subForm.setFormLevel(formLevel);
									subForm.setRoleId(roleId);
									subForm.addSubformData("ADDRESS_ID",address.getAddressId());
									subForm.addSubformData("PERSONAL_TYPE",PERSONAL_TYPE_SUPPLEMENTARY);
									subForm.addSubformData("PERSONAL_ID",personalInfo.getPersonalId());
									ArrayList<FieldElement>   fieldElementPoupUp = subForm.elementForm(request, applicationGroup);
									if(!Util.empty(fieldElementPoupUp)){
										fieldElements.addAll(fieldElementPoupUp);
									}
								}
							}
						}
					}
				}
			}
 
		} catch (Exception e) {
			logger.fatal("ERROR",e);
		}
		return fieldElements;
	}
}
