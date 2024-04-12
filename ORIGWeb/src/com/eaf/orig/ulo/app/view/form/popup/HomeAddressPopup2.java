package com.eaf.orig.ulo.app.view.form.popup;

//import java.util.ArrayList;
import java.util.ArrayList;
import java.util.HashMap;

import javax.servlet.http.HttpServletRequest;

import org.apache.log4j.Logger;

import com.eaf.core.ulo.common.message.MessageErrorUtil;
import com.eaf.core.ulo.common.model.FieldElement;
import com.eaf.core.ulo.common.properties.SystemConstant;
import com.eaf.core.ulo.common.util.FormDisplayValueUtil;
import com.eaf.core.ulo.common.util.FormErrorUtil;
import com.eaf.core.ulo.common.util.Util;
import com.eaf.orig.shared.view.form.ORIGSubForm;
import com.eaf.orig.ulo.app.view.form.sensitive.util.CompareSensitiveFieldUtil;
import com.eaf.orig.ulo.control.address.util.DisplayAddressUtil;
import com.eaf.orig.ulo.control.util.PersonalInfoUtil;
import com.eaf.orig.ulo.formcontrol.view.form.ORIGFormHandler;
import com.eaf.orig.ulo.model.app.AddressDataM;
import com.eaf.orig.ulo.model.app.ApplicationGroupDataM;
import com.eaf.orig.ulo.model.app.PersonalInfoDataM;
import com.eaf.orig.ulo.model.compare.CompareDataM;
import com.eaf.orig.ulo.pl.app.view.webaction.AddressUtil;

@SuppressWarnings("serial")
public class HomeAddressPopup2 extends ORIGSubForm{
	private static transient Logger logger = Logger.getLogger(HomeAddressPopup2.class);
	String subformId = "HOME_ADDRESS_POPUP_2";
	String PERSONAL_TYPE = SystemConstant.getConstant("PERSONAL_TYPE_APPLICANT");	
	String ADDRESS_TYPE_DOCUMENT = SystemConstant.getConstant("ADDRESS_TYPE_DOCUMENT");	
	String ADDRESS_FORMAT_MANUAL = SystemConstant.getConstant("ADDRESS_FORMAT_MANUAL");
	String CIDTYPE_IDCARD = SystemConstant.getConstant("CIDTYPE_IDCARD");
	String NATIONALITY_TH = SystemConstant.getConstant("NATIONALITY_TH");
	String COMPLETE_FLAG_Y = SystemConstant.getConstant("COMPLETE_FLAG_Y");
	String COMPLETE_FLAG_N = SystemConstant.getConstant("COMPLETE_FLAG_N");
	@SuppressWarnings("unchecked")
	@Override
	public void setProperties(HttpServletRequest request, Object appForm) {
		HashMap<String,AddressDataM> hashAddress = (HashMap<String,AddressDataM>)appForm;
		AddressDataM address = hashAddress.get(ADDRESS_TYPE_DOCUMENT);
		
		String ADRSTS = request.getParameter("ADRSTS");
		String ADDRESS_ID = request.getParameter("ADDRESS_ID");
		String MOO = request.getParameter("MOO");
		String SOI = request.getParameter("SOI");
		String ROAD = request.getParameter("ROAD");
		String COUNTRY = request.getParameter("COUNTRY");
		String ADDRESS_FORMAT = request.getParameter("ADDRESS_FORMAT");
		String TAMBOL = request.getParameter("TAMBOL");
		String AMPHUR = request.getParameter("AMPHUR");
		String PROVINCE = request.getParameter("PROVINCE");
		String ZIPCODE = request.getParameter("ZIPCODE");

		logger.debug("ADRSTS >>"+ADRSTS);	
		logger.debug("ADDRESS_ID >>"+ADDRESS_ID);	
		logger.debug("MOO >>"+MOO);	
		logger.debug("SOI >>"+SOI);	
		logger.debug("ROAD"+ROAD);	
		logger.debug("COUNTRY >>"+COUNTRY);	
		logger.debug("ADDRESS_FORMAT >>"+ADDRESS_FORMAT);
		logger.debug("TAMBOL >>"+TAMBOL);	
		logger.debug("AMPHUR >>"+AMPHUR);
		logger.debug("PROVINCE >>"+PROVINCE);	
		logger.debug("ZIPCODE >>"+ZIPCODE);	

		address.setAdrsts(ADRSTS);
		address.setAddress(ADDRESS_ID);
		address.setMoo(MOO);
		address.setSoi(SOI);
		address.setRoad(ROAD);
		address.setCountry(DisplayAddressUtil.getCountryCode(COUNTRY));
		if(Util.empty(ADDRESS_FORMAT)){
			address.setAddressFormat(ADDRESS_FORMAT_MANUAL);
		}else{
			address.setAddressFormat(ADDRESS_FORMAT);
		}
		address.setTambol(TAMBOL);
		address.setAmphur(AMPHUR);
		address.setProvinceDesc(PROVINCE);
		address.setZipcode(ZIPCODE);
		DisplayAddressUtil.setAddressLine(address);
	}

	@SuppressWarnings({ "unchecked", "rawtypes" })
	@Override
	public HashMap validateForm(HttpServletRequest request, Object appForm) {
		logger.debug("subformId >> "+subformId);
		FormErrorUtil formError = new FormErrorUtil();
		HashMap<String,AddressDataM> hashAddress = (HashMap<String,AddressDataM>)appForm;
		AddressDataM addressHome = hashAddress.get(ADDRESS_TYPE_DOCUMENT);
		PersonalInfoDataM personalInfo = PersonalInfoUtil.getPersonalInfoObjectForm(request);
		if(null == addressHome){
			addressHome = new AddressDataM();
		}
		ApplicationGroupDataM applicationGroup = ORIGFormHandler.getObjectForm(request);
		if(!CIDTYPE_IDCARD.equals(personalInfo.getCidType())){
			logger.debug("address.getCountry() >> "+addressHome.getCountry());
			logger.debug("NATIONALITY_TH >> "+NATIONALITY_TH);
			if(!Util.empty(addressHome.getCountry()) && NATIONALITY_TH.equals(addressHome.getCountry())){
				formError.error(MessageErrorUtil.getText("ERROR_NATIONALITY_TH"));
			}
		}
		
		formError.mandatory(subformId,"MATCHES_ADDRESS",addressHome,request);
		//KPL Additional
		String TOPUP_FLAG = request.getParameter("TOPUP_FLAG");
		if(Util.empty(TOPUP_FLAG))
		{
			if(!SystemConstant.lookup("JOBSTATE_REJECT",applicationGroup.getJobState())){
			AddressDataM address = personalInfo.getAddress(ADDRESS_TYPE_DOCUMENT);
			formError.mandatory(subformId, "ADDRESS_ID", addressHome.getAddress(), request);
			formError.mandatory(subformId, "MOO", addressHome.getMoo(), request);
			formError.mandatory(subformId, "SOI", addressHome.getSoi(), request);
			formError.mandatory(subformId, "ROAD", addressHome.getRoad(), request);
			formError.mandatory(subformId, "COUNTRY", addressHome.getCountry(), request);
			formError.mandatory(subformId, "TAMBOL", addressHome.getTambol(), request);
			formError.mandatory(subformId, "AMPHUR", addressHome.getAmphur(), request);
			formError.mandatory(subformId,"PROVINCE",addressHome.getProvinceDesc(),request);
			formError.mandatory(subformId,"ZIPCODE",addressHome.getZipcode(),request);
			}
		}
		
//		#rawi comment for change logic set edit to after process form
//		if(Util.empty((formError.getFormError()))){
//			addressHome.setEditFlag(COMPLETE_FLAG_Y);
//		}else{
//			addressHome.setEditFlag(COMPLETE_FLAG_N);
//		}
		return formError.getFormError();
	}

	@Override
	public boolean validateSubForm(HttpServletRequest request) {
		// TODO Auto-generated method stub
		return false;
	}
	
	
//	@Override
//	public ArrayList<AuditDataM> auditForm(HttpServletRequest request,Object objectForm, Object lastObjectForm) {	
//		HashMap<String,AddressDataM> hashAddress = (HashMap<String,AddressDataM>)objectForm;
//		AddressDataM addressHome = hashAddress.get(ADDRESS_TYPE_DOCUMENT);
//		if(null == addressHome){
//			addressHome = new AddressDataM();
//		}
//		HashMap<String,AddressDataM> hashlastAddress = (HashMap<String,AddressDataM>)objectForm;
//		AddressDataM lastAddress = hashAddress.get(ADDRESS_TYPE_DOCUMENT);
//		if(null == lastAddress){
//			lastAddress = new AddressDataM();
//		}
//
//		AuditFormUtil auditFormUtil = new AuditFormUtil();
//		String subformId = getSubFormID();
//		auditFormUtil.auditForm(subformId,"ADDRESS_ID",addressHome,lastAddress,request);
//
//		return auditFormUtil.getAuditForm();
//	}	
	
	@Override
	public void displayValueForm(HttpServletRequest request, Object objectForm) {
//		ApplicationGroupDataM applicationGroup = (ApplicationGroupDataM)FormControl.getOrigObjectForm(request);
		ApplicationGroupDataM applicationGroup = (ApplicationGroupDataM)objectForm;

		String addressId = getSubformData("ADDRESS_ID");
		String personalType = getSubformData("PERSONAL_TYPE");
		logger.debug("addressId >> "+addressId);
		FormDisplayValueUtil formValue = new FormDisplayValueUtil(formId,formLevel,roleId,subformId,applicationGroup,request);
			
		PersonalInfoDataM personalInfo = applicationGroup.getPersonalInfo(personalType);
//		String PERSONAL_TYPE = personalInfo.getPersonalType();	
//		ArrayList<AddressDataM> addresses = personalInfo.getAddresses();
		AddressDataM address =  personalInfo.getAddressById(addressId);
		if(null == address){
			address = new AddressDataM();
		}
//		HashMap<String,AddressDataM> hashAddress = (HashMap<String,AddressDataM>)objectForm;
//		if(null == hashAddress){
//			hashAddress = new HashMap<String,AddressDataM>();
//		}
//		AddressDataM address = hashAddress.get(ADDRESS_TYPE_DOCUMENT);
//		if(null == address){
//			address = new AddressDataM();
//		}

//		String PREFIX_ELEMENT_ID = PERSONAL_TYPE+"_"+personalInfo.getIdno()+"_"+ADDRESS_TYPE_DOCUMENT;
		String PREFIX_ELEMENT_ID = CompareSensitiveFieldUtil.getAddressRefId(personalInfo, ADDRESS_TYPE_DOCUMENT);
		String PREFIX_ADDRESS_CIS = CompareDataM.UniqueLevel.ADDRESS+"_"+address.getAddressId();
		address.setAddress(formValue.getValue("ADDRESS_ID","ADDRESS_ID_"+PREFIX_ADDRESS_CIS,address.getAddress()));
		address.setMoo(formValue.getValue("MOO","MOO_"+PREFIX_ADDRESS_CIS,address.getMoo()));
		address.setSoi(formValue.getValue("SOI","SOI_"+PREFIX_ADDRESS_CIS,address.getSoi()));
		address.setRoad(formValue.getValue("ROAD","ROAD_"+PREFIX_ADDRESS_CIS,address.getRoad()));
		address.setCountry(formValue.getValue("COUNTRY","COUNTRY_"+PREFIX_ADDRESS_CIS,address.getCountry()));
		address.setTambol(formValue.getValue("TAMBOL","TAMBOL_"+PREFIX_ADDRESS_CIS,address.getTambol()));
		address.setAmphur(formValue.getValue("AMPHUR","AMPHUR_"+PREFIX_ADDRESS_CIS,address.getAmphur()));
		address.setProvinceDesc(formValue.getValue("PROVINCE","PROVINCE_"+PREFIX_ADDRESS_CIS,address.getProvinceDesc()));
		address.setZipcode(formValue.getValue("ZIPCODE","ZIPCODE_"+PREFIX_ADDRESS_CIS,address.getZipcode()));
	}
	
	@Override
	public ArrayList<FieldElement> elementForm(HttpServletRequest request,Object objectForm) {
	ArrayList<FieldElement> fieldElements = new ArrayList<FieldElement>();
	String[] fieldNameList={"DOCUMENT_PROVINCE","DOCUMENT_ZIPCODE","DOCUMENT_ADDRESS",
							"DOCUMENT_MOO", "DOCUMENT_SOI", "DOCUMENT_ROAD", "DOCUMENT_COUNTRY" ,
							"DOCUMENT_TAMBOL", "DOCUMENT_AMPHUR"};
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
