package com.eaf.orig.ulo.app.view.form.popup;

import java.util.ArrayList;
import java.util.HashMap;

import javax.servlet.http.HttpServletRequest;

import org.apache.log4j.Logger;

import com.eaf.core.ulo.common.message.MessageErrorUtil;
import com.eaf.core.ulo.common.model.FieldElement;
import com.eaf.core.ulo.common.properties.FormControl;
import com.eaf.core.ulo.common.properties.SystemConstant;
import com.eaf.core.ulo.common.util.FormDisplayValueUtil;
import com.eaf.core.ulo.common.util.FormErrorUtil;
import com.eaf.core.ulo.common.util.Util;
import com.eaf.j2ee.pattern.control.ProcessForm;
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
public class OfficeAddressPopup2 extends ORIGSubForm{
	private static transient Logger logger = Logger.getLogger(OfficeAddressPopup2.class);
	private String subformId = "OFFICE_ADDRESS_POPUP_2";
//	private String PERSONAL_TYPE = SystemConstant.getConstant("PERSONAL_TYPE_APPLICANT");	
	private String ADDRESS_TYPE_WORK = SystemConstant.getConstant("ADDRESS_TYPE_WORK");	
//	private String CACHE_COMPANY_CIS = SystemConstant.getConstant("CACHE_COMPANY_CIS");
	String PERSONAL_RELATION_APPLICATION_LEVEL = SystemConstant.getConstant("PERSONAL_RELATION_APPLICATION_LEVEL");
	String DECISION_FINAL_DECISION_REJECT = SystemConstant.getConstant("DECISION_FINAL_DECISION_REJECT");
	String ADDRESS_FORMAT_MANUAL = SystemConstant.getConstant("ADDRESS_FORMAT_MANUAL");
	String ROLE_DE2 = SystemConstant.getConstant("ROLE_DE2");
	String COMPLETE_FLAG_Y = SystemConstant.getConstant("COMPLETE_FLAG_Y");
	String COMPLETE_FLAG_N = SystemConstant.getConstant("COMPLETE_FLAG_N");
	@SuppressWarnings("unchecked")
	@Override
	public void setProperties(HttpServletRequest request, Object appForm) {
		HashMap<String,AddressDataM> hashAddress = (HashMap<String,AddressDataM>)appForm;
//		ApplicationGroupDataM applicationGroup = (ApplicationGroupDataM)FormControl.getOrigObjectForm(request);
		String personalType = getSubformData("PERSONAL_TYPE");
		logger.debug("personalType >> "+personalType);
//		PersonalInfoDataM personalInfo = applicationGroup.getPersonalInfo(personalType);		
//		EntityFormHandler entityForm = (EntityFormHandler) request.getSession().getAttribute("EntityForm");
//		if(!Util.empty(entityForm)){
//			PersonalApplicationInfoDataM  personalAppInfo = (PersonalApplicationInfoDataM) entityForm.getObjectForm();
//			personalInfo = personalAppInfo.getPersonalInfo();
//		}
		
		AddressDataM address = hashAddress.get(ADDRESS_TYPE_WORK);

		String COMPANY_TITLE = request.getParameter("COMPANY_TITLE");
		String COMPANY_NAME = request.getParameter("COMPANY_NAME");
		String DEPARTMENT = request.getParameter("DEPARTMENT");
		String ADRSTS = request.getParameter("ADRSTS");
		String BUILDING = request.getParameter("BUILDING");
		String FLOOR = request.getParameter("FLOOR");
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

		logger.debug("COMPANY_TITLE >>"+COMPANY_TITLE);	
		logger.debug("COMPANY_NAME >>"+COMPANY_NAME);	
		logger.debug("DEPARTMENT >>"+DEPARTMENT);	
		logger.debug("ADRSTS >>"+ADRSTS);	
		logger.debug("BUILDING >>"+BUILDING);	
		logger.debug("FLOOR >>"+FLOOR);	
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

		address.setCompanyTitle(COMPANY_TITLE);
		if(!Util.empty(COMPANY_NAME)){
			COMPANY_NAME = Util.removeNotAllowSpeacialCharactors(COMPANY_NAME.trim());
		}
		address.setCompanyName(COMPANY_NAME);
		address.setDepartment(DEPARTMENT);
		address.setAdrsts(ADRSTS);
		address.setBuilding(BUILDING);
		address.setFloor(FLOOR);
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
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		}
		
	}

	@SuppressWarnings({ "unchecked", "rawtypes" })
	@Override
	public HashMap validateForm(HttpServletRequest request, Object appForm) {
		logger.debug("subformId >> "+subformId);
		FormErrorUtil formError = new FormErrorUtil(processForm,getSubformData());
		try{
			String roleId = FormControl.getFormRoleId(request);
			ApplicationGroupDataM applicationGroup = ORIGFormHandler.getObjectForm(request);
		/*String PERSONAL_TYPE = getSubformData("PERSONAL_TYPE");
			PersonalInfoDataM personalInfo = applicationGroup.getPersonalInfo(PERSONAL_TYPE);
			ApplicationDataM application = applicationGroup.getApplication(personalInfo.getPersonalId(),
					personalInfo.getPersonalType(), PERSONAL_RELATION_APPLICATION_LEVEL);*/
//			if(DECISION_FINAL_DECISION_REJECT.equals(application.getFinalAppDecision())){ //use whith DE1.2 & DE2
			PersonalInfoDataM personalInfo = PersonalInfoUtil.getPersonalInfoObjectForm(request);
			if(ProcessForm.SUP_APPLICANT_VALIDATE.equals(processForm)){
				personalInfo = PersonalInfoUtil.getPersonalInfoByProcessForm(processForm,getSubformData(),applicationGroup);
			}
			logger.debug("personalInfo.getMailingAddress()>>"+personalInfo.getMailingAddress());
			logger.debug("personalInfo.getPersonalId()>>"+personalInfo.getPersonalId());
			HashMap<String,AddressDataM> hashAddress = (HashMap<String,AddressDataM>)appForm;
			AddressDataM addressWork = hashAddress.get(ADDRESS_TYPE_WORK);
			if(null == addressWork){
				addressWork = new AddressDataM();
			}
			
			//KPL Additional
			String TOPUP_FLAG = request.getParameter("TOPUP_FLAG");
			if(Util.empty(TOPUP_FLAG))
			{
				if(SystemConstant.lookup("JOBSTATE_REJECT",applicationGroup.getJobState()) && ADDRESS_TYPE_WORK.equals(personalInfo.getMailingAddress())){
					
					formError.mandatory(subformId,"COMPANY_NAME",hashAddress,request);
					formError.mandatory(subformId,"DEPARTMENT",addressWork.getDepartment(),request);
					formError.mandatory(subformId,"BUILDING",addressWork.getBuilding(),request);
					formError.mandatory(subformId,"FLOOR",addressWork.getFloor(),request);
					formError.mandatory(subformId,"ADDRESS_ID",addressWork.getAddress(),request);
					formError.mandatory(subformId,"MOO",addressWork.getMoo(),request);
					formError.mandatory(subformId,"SOI",addressWork.getSoi(),request);
					formError.mandatory(subformId,"ROAD",addressWork.getRoad(),request);
					formError.mandatory(subformId,"COUNTRY",addressWork.getCountry(),request);
					formError.mandatory(subformId,"TAMBOL",addressWork.getTambol(),request);
					formError.mandatory(subformId,"AMPHUR",addressWork.getAmphur(),request);
					formError.mandatory(subformId,"PROVINCE",addressWork.getProvinceDesc(),request);
					formError.mandatory(subformId,"ZIPCODE",addressWork.getZipcode(),request);
										
				}else if(!SystemConstant.lookup("JOBSTATE_REJECT",applicationGroup.getJobState())){
					formError.mandatory(subformId,"COMPANY_NAME",hashAddress,request);
					formError.mandatory(subformId,"DEPARTMENT",addressWork.getDepartment(),request);
					formError.mandatory(subformId,"BUILDING",addressWork.getBuilding(),request);
					formError.mandatory(subformId,"FLOOR",addressWork.getFloor(),request);
					formError.mandatory(subformId,"ADDRESS_ID",addressWork.getAddress(),request);
					formError.mandatory(subformId,"MOO",addressWork.getMoo(),request);
					formError.mandatory(subformId,"SOI",addressWork.getSoi(),request);
					formError.mandatory(subformId,"ROAD",addressWork.getRoad(),request);
					formError.mandatory(subformId,"COUNTRY",addressWork.getCountry(),request);
					formError.mandatory(subformId,"TAMBOL",addressWork.getTambol(),request);
					formError.mandatory(subformId,"AMPHUR",addressWork.getAmphur(),request);
					formError.mandatory(subformId,"PROVINCE",addressWork.getProvinceDesc(),request);
					formError.mandatory(subformId,"ZIPCODE",addressWork.getZipcode(),request);
				}
				
				formError.mandatory(subformId, "MATCHES_ADDRESS", addressWork, request);
			}
//				#rawi comment for change logic set edit to after process form
//				if(Util.empty(formError.getFormError())){
//					addressWork.setEditFlag(COMPLETE_FLAG_Y);
//				}else{
//					addressWork.setEditFlag(COMPLETE_FLAG_N);
//				}
//			}
		}catch(Exception e){
			logger.fatal("ERROR ",e);
		}
 
		return formError.getFormError();
	}

	@Override
	public boolean validateSubForm(HttpServletRequest request) {
		// TODO Auto-generated method stub
		return false;
	}
	
//	@Override
//	public ArrayList<AuditDataM> auditForm(HttpServletRequest request,Object objectForm, Object lastObjectForm) {	
//		HashMap<String,AddressDataM> hashNewAddress = (HashMap<String,AddressDataM>)objectForm;
//		AddressDataM newAddress = hashNewAddress.get(ADDRESS_TYPE_WORK);
//		if(null == newAddress){
//			newAddress = new AddressDataM();
//		}
//		HashMap<String,AddressDataM> hashLastAddress = (HashMap<String,AddressDataM>)lastObjectForm;
//		AddressDataM lastAddress = hashLastAddress.get(ADDRESS_TYPE_WORK);
//		if(null == lastAddress){
//			lastAddress = new AddressDataM();
//		}
//
//		AuditFormUtil auditFormUtil = new AuditFormUtil();
//		String subformId = getSubFormID();
//		auditFormUtil.auditForm(subformId,"ADDRESS_ID", newAddress , lastAddress,request);
//		auditFormUtil.auditForm(subformId,"ZIPCODE", newAddress, lastAddress,request);
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
		AddressDataM address = personalInfo.getAddress(ADDRESS_TYPE_WORK);
		if(null == address){
			address = new AddressDataM();
		}
//		String PREFIX_ELEMENT_ID = PERSONAL_TYPE+"_"+personalInfo.getIdno()+"_"+ADDRESS_TYPE_WORK;
		String PREFIX_ELEMENT_ID = CompareSensitiveFieldUtil.getAddressRefId(personalInfo, ADDRESS_TYPE_WORK);
		
		address.setCompanyTitle(formValue.getValue("COMPANY_TITLE","COMPANY_TITLE_"+PREFIX_ELEMENT_ID,address.getCompanyTitle()));
		address.setCompanyName(formValue.getValue("COMPANY_NAME","COMPANY_NAME_"+PREFIX_ELEMENT_ID,address.getCompanyName()));
		address.setDepartment(formValue.getValue("DEPARTMENT","DEPARTMENT_"+PREFIX_ELEMENT_ID,address.getDepartment()));
		address.setBuilding(formValue.getValue("BUILDING","BUILDING_"+PREFIX_ELEMENT_ID,address.getBuilding()));
//		address.setRoom(formValue.getValue("ROOM",TYPE_WORK+"ROOM"+PREFIX_ELEMENT_ID,address.getRoom()));
		address.setFloor(formValue.getValue("FLOOR","FLOOR_"+PREFIX_ELEMENT_ID,address.getFloor()));
		address.setAddress(formValue.getValue("ADDRESS_ID","ADDRESS_ID_"+PREFIX_ELEMENT_ID,address.getAddress()));
		address.setMoo(formValue.getValue("MOO","MOO_"+PREFIX_ELEMENT_ID,address.getMoo()));
		address.setSoi(formValue.getValue("SOI","SOI_"+PREFIX_ELEMENT_ID,address.getSoi()));
		address.setRoad(formValue.getValue("ROAD","ROAD_"+PREFIX_ELEMENT_ID,address.getRoad()));
		address.setCountry(formValue.getValue("COUNTRY","COUNTRY_"+PREFIX_ELEMENT_ID,address.getCountry()));
		address.setTambol(formValue.getValue("TAMBOL","TAMBOL_"+PREFIX_ELEMENT_ID,address.getTambol()));
		address.setAmphur(formValue.getValue("AMPHUR","AMPHUR_"+PREFIX_ELEMENT_ID,address.getAmphur()));
		address.setProvinceDesc(formValue.getValue("PROVINCE","PROVINCE_"+PREFIX_ELEMENT_ID,address.getProvinceDesc()));
		address.setZipcode(formValue.getValue("ZIPCODE","ZIPCODE_"+PREFIX_ELEMENT_ID,address.getZipcode()));
	}
	
	@Override
	public ArrayList<FieldElement> elementForm(HttpServletRequest request,Object objectForm) {
		ArrayList<FieldElement> fieldElements = new ArrayList<FieldElement>();
		
		ApplicationGroupDataM applicationGroup = (ApplicationGroupDataM)objectForm;
		String addressId = getSubformData("ADDRESS_ID");
		String personalType = getSubformData("PERSONAL_TYPE");
		logger.debug("addressId >> "+addressId);	
		PersonalInfoDataM personalInfo = applicationGroup.getPersonalInfo(personalType);
		String[] fieldNameList = {"COMPANY_TAMBOL","COMPANY_AMPHUR","COMPANY_PROVINCE","COMPANY_ZIPCODE","COMPANY_DEPARTMENT","PREV_COMPANY_NAME","PREV_COMPANY_TITLE","COMPANY_BUILDING",
				"COMPANY_COUNTRY","COMPANY_FLOOR","COMPANY_MOO","COMPANY_ROAD","COMPANY_ROOM","COMPANY_SOI","COMPANY_ADDRESS"};
		try {		
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
//		ArrayList<ElementInf> elements = ImplementControl.getElements(MConstant.IMPLEMENT_TYPE.ADDRESS_MAILING);
//		logger.debug("element >> "+elements.size());
//		for(ElementInf element:elements){
//			if(MConstant.FLAG_Y.equals(element.renderElementFlag(request, personalType))){
//				ArrayList<FieldElement> fieldElementAddress = element.elementForm(request, personalInfo);
//				if(!Util.empty(fieldElementAddress)){
//					fieldElements.addAll(fieldElementAddress);
//				}
//			}
//		}
		return fieldElements;
	}
	
}
