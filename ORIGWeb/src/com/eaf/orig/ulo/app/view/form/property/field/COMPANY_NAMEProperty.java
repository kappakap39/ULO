package com.eaf.orig.ulo.app.view.form.property.field;

import java.util.ArrayList;
import java.util.HashMap;

import javax.servlet.http.HttpServletRequest;

import org.apache.log4j.Logger;

import com.eaf.core.ulo.common.display.FieldPropertyHelper;
import com.eaf.core.ulo.common.display.FormatUtil;
import com.eaf.core.ulo.common.display.ValidateFormInf;
import com.eaf.core.ulo.common.message.LabelUtil;
import com.eaf.core.ulo.common.message.MessageErrorUtil;
import com.eaf.core.ulo.common.model.AuditDataM;
import com.eaf.core.ulo.common.properties.FormControl;
import com.eaf.core.ulo.common.properties.SystemConstant;
import com.eaf.core.ulo.common.util.AuditFormUtil;
import com.eaf.core.ulo.common.util.CompareObject;
import com.eaf.core.ulo.common.util.FormErrorUtil;
import com.eaf.core.ulo.common.util.Util;
import com.eaf.j2ee.pattern.control.ProcessForm;
import com.eaf.orig.ulo.control.util.PersonalInfoUtil;
import com.eaf.orig.ulo.formcontrol.view.form.ORIGFormHandler;
import com.eaf.orig.ulo.model.app.AddressDataM;
import com.eaf.orig.ulo.model.app.PersonalApplicationInfoDataM;
import com.eaf.orig.ulo.model.app.PersonalInfoDataM;

public class COMPANY_NAMEProperty extends FieldPropertyHelper {
	private static transient Logger logger = Logger.getLogger(COMPANY_NAMEProperty.class);
	String ADDRESS_TYPE_WORK = SystemConstant.getConstant("ADDRESS_TYPE_WORK");
	String PERSONAL_TYPE_APPLICANT = SystemConstant.getConstant("PERSONAL_TYPE_APPLICANT");
	
	@Override
	public boolean invokeValidateFlag() {
		return true;
	}
	
	@Override
	public boolean invokeValidateForm() {
		return true;
	}
	
	@Override
	public HashMap<String, Object> validateForm(HttpServletRequest request,	Object objectForm) {
		AddressDataM addressWork = new AddressDataM();
		if(objectForm instanceof HashMap){
			HashMap<String,AddressDataM> hashAddress = (HashMap<String,AddressDataM>)objectForm;
			addressWork = hashAddress.get(ADDRESS_TYPE_WORK);
		}else if(objectForm instanceof AddressDataM){
			addressWork = (AddressDataM)objectForm;
		}
		if(null == addressWork){
			addressWork = new AddressDataM();
		}
		String PREFIX_ERROR = MessageErrorUtil.getText(request,"PREFIX_ERROR");
		FormErrorUtil formError = new FormErrorUtil();
		if(Util.empty(addressWork.getCompanyName())){
			logger.debug("addressWork.getCompanyTitle() : "+addressWork.getCompanyTitle());
			logger.debug("addressWork.getCompanyName() : "+addressWork.getCompanyName());
			formError.error("COMPANY_NAME",PREFIX_ERROR+LabelUtil.getText(request,"COMPANY_NAME"));
		}
		return formError.getFormError();
	}
	
	@Override
	public String validateFlag(HttpServletRequest request, Object objectForm) {
		logger.debug("COMPANY_NAMEProperty.validateFlag...");
		logger.debug("mandatoryConfig >> "+mandatoryConfig);
		if(ValidateFormInf.VALIDATE_SUBMIT.equals(mandatoryConfig)){
		ORIGFormHandler ORIGForm = (ORIGFormHandler)request.getSession().getAttribute(ORIGFormHandler.ORIGForm);
		String PERSONAL_TYPE = SystemConstant.getConstant("PERSONAL_TYPE_APPLICANT");	
		PersonalInfoDataM personalInfo = null;
		AddressDataM addressWork = new AddressDataM();
		if(objectForm instanceof HashMap){
			HashMap<String,AddressDataM> hashAddress = (HashMap<String,AddressDataM>)objectForm;
			addressWork = hashAddress.get(ADDRESS_TYPE_WORK);
		}else if(objectForm instanceof AddressDataM){
			addressWork = (AddressDataM)objectForm;
		}
		if(null == addressWork){
			addressWork = new AddressDataM();
		}
		

		personalInfo = ORIGForm.getObjectForm().getPersonalInfo(PERSONAL_TYPE);
		Object masterObject = FormControl.getMasterObjectForm(request);
		if(masterObject instanceof PersonalApplicationInfoDataM){
			PersonalApplicationInfoDataM personalApplicationInfoDataM = (PersonalApplicationInfoDataM)masterObject;
			if(Util.empty(personalApplicationInfoDataM)){
				personalApplicationInfoDataM = new PersonalApplicationInfoDataM();
			}
			personalInfo = personalApplicationInfoDataM.getPersonalInfo();
		}	
		
		if(ProcessForm.SUP_APPLICANT_VALIDATE.equals(processForm)){
			personalInfo = PersonalInfoUtil.getPersonalInfoByProcessForm(processForm,requestData,FormControl.getOrigObjectForm(request));
		}	
		if(null == personalInfo){
			personalInfo = new PersonalInfoDataM();
		}	
		logger.debug("UNMANDATORY_COMPANY_NAME BY OCCUPATION >> "+personalInfo.getOccupation());
		ArrayList<String>  UNMANDATORY_COMPANY_NAME =  SystemConstant.getArrayListConstant("UNMANDATORY_COMPANY_NAME");
		if(PERSONAL_TYPE_APPLICANT.equals(personalInfo.getPersonalType()) && !Util.empty(personalInfo.getOccupation()) 
				&& !UNMANDATORY_COMPANY_NAME.contains(personalInfo.getOccupation())){
			return ValidateFormInf.VALIDATE_YES;
			}
		}
		return ValidateFormInf.VALIDATE_NO;
	}
	
	@Override
	public ArrayList<AuditDataM> auditForm(HttpServletRequest request,Object objectForm, Object lastObjectForm, Object objectValue) {
		logger.debug("COMPANY_NAMEProperty.auditForm");
		String personalType = (String)objectValue;
		ArrayList<AuditDataM> audits = new ArrayList<AuditDataM>();
		AddressDataM address = (AddressDataM)objectForm;
		if(null == address){
			address = new AddressDataM();
		}
		AddressDataM lastaddress = (AddressDataM)lastObjectForm;
		if(null == lastaddress){
			lastaddress = new AddressDataM();
		}
		boolean compareCompanyName = CompareObject.compare(address.getCompanyName(), lastaddress.getCompanyName(),null);
		if(!compareCompanyName){
			AuditDataM audit = new AuditDataM();
			audit.setFieldName(AuditFormUtil.getAuditFieldName(personalType, LabelUtil.getText(request, "COMPANY_NAME"), request));
			audit.setOldValue(FormatUtil.displayText(lastaddress.getCompanyName()));
			audit.setNewValue(FormatUtil.displayText(address.getCompanyName()));
			audits.add(audit);
		}
		return audits;
	}
}
