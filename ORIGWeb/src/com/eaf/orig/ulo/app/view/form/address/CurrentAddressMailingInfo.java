package com.eaf.orig.ulo.app.view.form.address;

import java.util.ArrayList;
import java.util.HashMap;

import javax.servlet.http.HttpServletRequest;

import org.apache.log4j.Logger;

import com.eaf.core.ulo.common.display.ElementHelper;
import com.eaf.core.ulo.common.display.ElementInf;
import com.eaf.core.ulo.common.model.FieldElement;
import com.eaf.core.ulo.common.properties.SystemConstant;
import com.eaf.core.ulo.common.util.FormDisplayValueUtil;
import com.eaf.core.ulo.common.util.FormErrorUtil;
import com.eaf.core.ulo.common.util.Util;
import com.eaf.orig.ulo.app.view.form.sensitive.util.CompareSensitiveFieldUtil;
import com.eaf.orig.ulo.control.address.util.DisplayAddressUtil;
import com.eaf.orig.ulo.model.app.AddressDataM;
import com.eaf.orig.ulo.model.app.PersonalInfoDataM;
import com.eaf.orig.ulo.model.compare.CompareDataM;

public class CurrentAddressMailingInfo extends ElementHelper implements ElementInf{
	private static transient Logger logger = Logger.getLogger(CurrentAddressMailingInfo.class);
	String ADDRESS_TYPE_CURRENT = SystemConstant.getConstant("ADDRESS_TYPE_CURRENT");
	String ADDRESS_FORMAT_MANUAL = SystemConstant.getConstant("ADDRESS_FORMAT_MANUAL");
	@Override
	public String getElement(HttpServletRequest request, Object objectElement) {
		return "/orig/ulo/popup/CurrentAddressMailingInfo.jsp";
	}	
	@Override
	public String getDisplayType() {
		return ElementInf.DISPLAY_JSP;
	}	
	@Override
	public String processElement(HttpServletRequest request, Object objectElement) {
		AddressDataM address = (AddressDataM)objectElement;
		String ADDRESS_NAME = request.getParameter("ADDRESS_NAME");
		String BUILDING = request.getParameter("BUILDING");
		String ROOM = request.getParameter("ROOM");
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
		
		logger.debug("ADDRESS_NAME >>"+ADDRESS_NAME);	
		logger.debug("BUILDING >>"+BUILDING);	
		logger.debug("ROOM >>"+ROOM);	
		logger.debug("FLOOR >>"+FLOOR);	
		logger.debug("ADDRESS_ID >>"+ADDRESS_ID);	
		logger.debug("MOO >>"+MOO);	
		logger.debug("SOI >>"+SOI);	
		logger.debug("ROAD >>"+ROAD);	
		logger.debug("COUNTRY >>"+COUNTRY);	
		logger.debug("ADDRESS_FORMAT >>"+ADDRESS_FORMAT);
		logger.debug("TAMBOL >>"+TAMBOL);	
		logger.debug("AMPHUR >>"+AMPHUR);	
		logger.debug("PROVINCE >>"+PROVINCE);	
		logger.debug("ZIPCODE >>"+ZIPCODE);	
		
		address.setVilapt(ADDRESS_NAME);
		address.setBuilding(BUILDING);
		address.setRoom(ROOM);
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
		return null;
	}
	@SuppressWarnings("unchecked")
	@Override
	public HashMap<String, Object> validateElement(HttpServletRequest request, Object objectElement) {
		String subformId = (String)getObjectRequest();
		logger.debug("subformId >> "+subformId);
		FormErrorUtil formError = new FormErrorUtil();
		
		HashMap<String,AddressDataM> hashAddress = (HashMap<String,AddressDataM>)objectElement;
		AddressDataM currentAddress = hashAddress.get(ADDRESS_TYPE_CURRENT);
		if(null == currentAddress){
			currentAddress = new AddressDataM();
		}
		formError.mandatory(subformId,"ADDRESS_NAME",currentAddress.getVilapt(),request);
		formError.mandatory(subformId,"BUILDING",currentAddress.getBuilding(),request);
		formError.mandatory(subformId,"ROOM_ID",currentAddress.getRoom(),request);
		formError.mandatory(subformId,"FLOOR",currentAddress.getFloor(),request);
		formError.mandatory(subformId,"MOO",currentAddress.getMoo(),request);
		formError.mandatory(subformId,"SOI",currentAddress.getSoi(),request);
		formError.mandatory(subformId,"ROAD",currentAddress.getRoad(),request);
		formError.mandatory(subformId,"COUNTRY",currentAddress.getCountry(),request);
		
		//KPL Additional
		String TOPUP_FLAG = request.getParameter("TOPUP_FLAG");
		if(Util.empty(TOPUP_FLAG))
		{
			formError.mandatory(subformId,"ADDRESS_ID",currentAddress.getAddress(),request);
			formError.mandatory(subformId,"TAMBOL",currentAddress.getTambol(),request);
			formError.mandatory(subformId,"AMPHUR",currentAddress.getAmphur(),request);
			formError.mandatory(subformId,"PROVINCE",currentAddress.getProvinceDesc(),request);
			formError.mandatory(subformId,"ZIPCODE",currentAddress.getZipcode(),request);
		}
		
		return formError.getFormError();
	}
	@Override
	public void displayValueElement(HttpServletRequest request,	Object objectElement, FormDisplayValueUtil formValue) {
		PersonalInfoDataM personalInfo = (PersonalInfoDataM)objectElement;		
//		String PERSONAL_TYPE = personalInfo.getPersonalType();	
		AddressDataM address = personalInfo.getAddress(ADDRESS_TYPE_CURRENT);
		if(null == address){
			address = new AddressDataM();
		}
		String PREFIX_ELEMENT_ID = CompareSensitiveFieldUtil.getAddressRefId(personalInfo, ADDRESS_TYPE_CURRENT);
		address.setVilapt(formValue.getValue("ADDRESS_NAME","ADDRESS_NAME_"+PREFIX_ELEMENT_ID,address.getVilapt()));
		address.setBuilding(formValue.getValue("BUILDING","BUILDING_"+PREFIX_ELEMENT_ID,address.getBuilding()));
		address.setRoom(formValue.getValue("ROOM","ROOM_"+PREFIX_ELEMENT_ID,address.getRoom()));
		address.setFloor(formValue.getValue("FLOOR","FLOOR_"+PREFIX_ELEMENT_ID,address.getFloor()));
		address.setAddress(formValue.getValue("ADDRESS_ID","ADDRESS_ID_"+PREFIX_ELEMENT_ID,address.getAddress()));
		address.setMoo(formValue.getValue("MOO","MOO_"+PREFIX_ELEMENT_ID,address.getMoo()));
		address.setSoi(formValue.getValue("SOI","SOI_"+PREFIX_ELEMENT_ID,address.getSoi()));
		address.setRoad(formValue.getValue("ROAD","ROAD_"+PREFIX_ELEMENT_ID,address.getRoad()));
		address.setCountry(formValue.getValue("COUNTRY","COUNTRY_"+PREFIX_ELEMENT_ID,address.getCountry()));
		address.setTambol(formValue.getValue("TAMBOL","TAMBOL_"+PREFIX_ELEMENT_ID,address.getTambol()));
		address.setAmphur(formValue.getValue("AMPHUR","AMPHUR_"+PREFIX_ELEMENT_ID,address.getAmphur()));
		address.setProvinceDesc(formValue.getValue("PROVINCE","CURRENT_PROVINCE_"+PREFIX_ELEMENT_ID,address.getProvinceDesc()));
		address.setZipcode(formValue.getValue("ZIPCODE","CURRENT_ZIPCODE_"+PREFIX_ELEMENT_ID,address.getZipcode()));
	}	
	
	@Override
	public ArrayList<FieldElement> elementForm(HttpServletRequest request,Object objectForm) {
		ArrayList<FieldElement> fieldElements = new ArrayList<FieldElement>();
		String[] fieldNameList={"CURRENT_PROVINCE","CURRENT_ZIPCODE", "CURRENT_ADDRESS_NAME",
								"CURRENT_BUILDING", "CURRENT_ROOM", "CURRENT_FLOOR", "CURRENT_ADDRESS",
								"CURRENT_MOO", "CURRENT_SOI", "CURRENT_ROAD", "CURRENT_COUNTRY", "CURRENT_TAMBOL", "CURRENT_AMPHUR","CURRENT_ADDRESS_ID"};
		try {		
			PersonalInfoDataM personalInfo = (PersonalInfoDataM)objectForm;
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
