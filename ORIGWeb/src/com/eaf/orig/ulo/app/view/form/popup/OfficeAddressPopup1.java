package com.eaf.orig.ulo.app.view.form.popup;

import java.util.ArrayList;
import java.util.HashMap;

import javax.servlet.http.HttpServletRequest;

import org.apache.log4j.Logger;

import com.eaf.core.ulo.common.message.MessageErrorUtil;
import com.eaf.core.ulo.common.model.FieldElement;
import com.eaf.core.ulo.common.properties.CacheControl;
import com.eaf.core.ulo.common.properties.SystemConstant;
import com.eaf.core.ulo.common.util.FormDisplayValueUtil;
import com.eaf.core.ulo.common.util.FormErrorUtil;
import com.eaf.core.ulo.common.util.Util;
import com.eaf.orig.shared.view.form.ORIGSubForm;
import com.eaf.orig.ulo.app.view.form.sensitive.util.CompareSensitiveFieldUtil;
import com.eaf.orig.ulo.control.address.util.DisplayAddressUtil;
import com.eaf.orig.ulo.model.app.AddressDataM;
import com.eaf.orig.ulo.model.app.ApplicationGroupDataM;
import com.eaf.orig.ulo.model.app.PersonalInfoDataM;
import com.eaf.orig.ulo.model.compare.CompareDataM;
import com.eaf.orig.ulo.pl.app.view.webaction.AddressUtil;
import com.eaf.ulo.cache.util.CacheUtil;
import com.google.gson.Gson;
import com.ibm.ws.sip.stack.util.AddressUtils;

@SuppressWarnings("serial")
public class OfficeAddressPopup1 extends ORIGSubForm{
	private static transient Logger logger = Logger.getLogger(OfficeAddressPopup1.class);
	private String subformId = "OFFICE_ADDRESS_POPUP_1";	
	private String ADDRESS_TYPE_WORK = SystemConstant.getConstant("ADDRESS_TYPE_WORK");
	String COMPLETE_FLAG_Y = SystemConstant.getConstant("COMPLETE_FLAG_Y");
	String COMPLETE_FLAG_N = SystemConstant.getConstant("COMPLETE_FLAG_N");
//	private String CACHE_COMPANY_CIS = SystemConstant.getConstant("CACHE_COMPANY_CIS");

	@SuppressWarnings("unchecked")
	@Override
	public void setProperties(HttpServletRequest request, Object appForm) {
		HashMap<String,AddressDataM> hashAddress = (HashMap<String,AddressDataM>)appForm;
	
		AddressDataM address = hashAddress.get(ADDRESS_TYPE_WORK);
		String COMPANY_TITLE = request.getParameter("COMPANY_TITLE");
		String COMPANY_NAME = request.getParameter("COMPANY_NAME");
		String DEPARTMENT = request.getParameter("DEPARTMENT");
		String PROVINCE = request.getParameter("PROVINCE");
		String ZIPCODE = request.getParameter("ZIPCODE");
		String AMPHUR = request.getParameter("AMPHUR");
		String TAMBOL = request.getParameter("TAMBOL");
		String COUNTRY = request.getParameter("COUNTRY");
		
		logger.debug("COMPANY_TITLE >>"+COMPANY_TITLE);	
		logger.debug("COMPANY_NAME >>"+COMPANY_NAME);	
		logger.debug("DEPARTMENT >>"+DEPARTMENT);	
		logger.debug("PROVINCE >>"+PROVINCE);	
		logger.debug("ZIPCODE >>"+ZIPCODE);	
		logger.debug("AMPHUR >>"+AMPHUR);	
		logger.debug("TAMBOL >>"+TAMBOL);	
		logger.debug("COUNTRY >>"+COUNTRY);	
		
		address.setCompanyTitle(COMPANY_TITLE);
		if(!Util.empty(COMPANY_NAME)){
			COMPANY_NAME = Util.removeNotAllowSpeacialCharactors(COMPANY_NAME.trim());
		}
		address.setCompanyName(COMPANY_NAME);
		address.setDepartment(DEPARTMENT);
		address.setProvinceDesc(PROVINCE);
		address.setZipcode(ZIPCODE);
		address.setAmphur(AMPHUR);
		address.setTambol(TAMBOL);
		address.setCountry(COUNTRY);
		
		DisplayAddressUtil.setAddressLine(address);
		
//		String COMPANY_CIS_ID = ListBoxControl.getName(CACHE_COMPANY_CIS,"VALUE",COMPANY_NAME,"CODE");
//		personalInfo.setCompanyCISId(COMPANY_CIS_ID);
//		DIHProxy dih = new DIHProxy();
//		HashMap<String, CompareDataM> comparisonFields = ApplicationUtil.getCISComparisonField(request);
//		if(null == comparisonFields){
//			comparisonFields = new HashMap<String, CompareDataM>();
//		}
//		try {
//			String titleDesc = CacheControl.getName(SystemConstant.getConstant("FIELD_ID_COMPANY_TITLE"), COMPANY_TITLE,"MAPPING4"); 
//			dih.getCisCompanyInfo(titleDesc, COMPANY_NAME,personalInfo,comparisonFields);
//		} catch (ApplicationException e) {
//			e.printStackTrace();
//		}
//		
	}

	@SuppressWarnings({ "unchecked", "rawtypes"})
	@Override
	public HashMap validateForm(HttpServletRequest request, Object appForm) {
		logger.debug("validateForm.subformId >> "+subformId);
		FormErrorUtil formError = new FormErrorUtil(processForm,getSubformData());
		HashMap<String,AddressDataM> hashAddress = (HashMap<String,AddressDataM>)appForm;
		AddressDataM addressWork = hashAddress.get(ADDRESS_TYPE_WORK);
		if(null == addressWork){
			addressWork = new AddressDataM();
		}
		
		//KPL Additional
		String TOPUP_FLAG = request.getParameter("TOPUP_FLAG");
		if(Util.empty(TOPUP_FLAG))
		{
			formError.mandatory(subformId,"COMPANY_NAME",hashAddress,request);
			formError.mandatory(subformId,"PROVINCE",addressWork.getProvinceDesc(),request);
			formError.mandatory(subformId,"ZIPCODE",addressWork.getZipcode(),request);
			formError.mandatory(subformId, "MATCHES_ADDRESS", addressWork, request);
		}

//		#rawi comment for change logic set edit to after process form
//		if(Util.empty(formError.getFormError())){
//			addressWork.setEditFlag(COMPLETE_FLAG_Y);
//		}else{
//			addressWork.setEditFlag(COMPLETE_FLAG_N);
//		}
		return formError.getFormError();
	}

	@Override
	public boolean validateSubForm(HttpServletRequest request) {
		return false;
	}
	
	@Override
	public void displayValueForm(HttpServletRequest request, Object objectForm) {
		ApplicationGroupDataM applicationGroup = (ApplicationGroupDataM)objectForm;
		String addressId = getSubformData("ADDRESS_ID");
		String personalType = getSubformData("PERSONAL_TYPE");
		logger.debug("addressId >> "+addressId);
		FormDisplayValueUtil formValue = new FormDisplayValueUtil(formId,formLevel,roleId,subformId,applicationGroup,request);
			
		PersonalInfoDataM personalInfo = applicationGroup.getPersonalInfo(personalType);
//		String PERSONAL_TYPE = personalInfo.getPersonalType();	
		AddressDataM address = personalInfo.getAddress(ADDRESS_TYPE_WORK);
		if(null == address){
			address = new AddressDataM();
		}
//		String PREFIX_ELEMENT_ID = PERSONAL_TYPE+"_"+personalInfo.getIdno()+"_"+ADDRESS_TYPE_WORK;
		String PREFIX_ELEMENT_ID = CompareSensitiveFieldUtil.getAddressRefId(personalInfo, ADDRESS_TYPE_WORK);
		String PREFIX_ADDRESS_CIS = CompareDataM.UniqueLevel.ADDRESS+"_"+address.getAddressId();
		address.setCompanyTitle(formValue.getValue("COMPANY_TITLE","COMPANY_TITLE_"+PREFIX_ADDRESS_CIS,address.getCompanyTitle()));
		address.setCompanyName(formValue.getValue("COMPANY_NAME","COMPANY_NAME_"+PREFIX_ADDRESS_CIS,address.getCompanyName()));
		address.setProvinceDesc(formValue.getValue("PROVINCE","PROVINCE_"+PREFIX_ADDRESS_CIS,address.getProvinceDesc()));
		address.setZipcode(formValue.getValue("ZIPCODE","ZIPCODE_"+PREFIX_ADDRESS_CIS,address.getZipcode()));
		address.setAmphur(formValue.getValue("PROVINCE","AMPHUR_"+PREFIX_ADDRESS_CIS,address.getAmphur()));
		address.setTambol(formValue.getValue("ZIPCODE","TAMBOL_"+PREFIX_ADDRESS_CIS,address.getTambol()));
	}
	
//	@Override
//	public ArrayList<AuditDataM> auditForm(HttpServletRequest request, Object objectForm, Object lastObjectForm) {
//		AuditFormUtil auditFormUtil = new AuditFormUtil();
//		try{
//			HashMap<String,AddressDataM> hashNewAddress = (HashMap<String,AddressDataM>)objectForm;
//			AddressDataM newAddress = hashNewAddress.get(ADDRESS_TYPE_WORK);
//			if(null == newAddress){
//				newAddress = new AddressDataM();
//			}
//			HashMap<String,AddressDataM> hashLastAddress = (HashMap<String,AddressDataM>)lastObjectForm;
//			AddressDataM lastAddress = hashLastAddress.get(ADDRESS_TYPE_WORK);
//			if(null == lastAddress){
//				lastAddress = new AddressDataM();
//			}
//	
//			String subformId = getSubFormID();

//			auditFormUtil.auditForm(subformId,"ZIPCODE", newAddress, lastAddress,request);
//		}catch(Exception e){
//			logger.fatal("ERROR ",e);
//		}
//
//		return auditFormUtil.getAuditForm();
//	}
	
	@Override
	public ArrayList<FieldElement> elementForm(HttpServletRequest request,Object objectForm) {
		ArrayList<FieldElement> fieldElements = new ArrayList<FieldElement>();
		String[] fieldNameList={"COMPANY_NAME","COMPANY_PROVINCE","COMPANY_ZIPCODE", "COMPANY_TITLE", "COMPANY_DEPARTMENT","COMPANY_AMPHUR","COMPANY_TAMBOL"};
		try {
			String personalType = getSubformData("PERSONAL_TYPE");
			logger.debug("personalType>>>"+personalType);
			ApplicationGroupDataM applicationGroup = ((ApplicationGroupDataM)objectForm);			
			PersonalInfoDataM personalInfo = applicationGroup.getPersonalInfo(personalType);
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
