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
import com.eaf.orig.ulo.model.app.AddressDataM;
import com.eaf.orig.ulo.model.app.PersonalInfoDataM;
import com.eaf.orig.ulo.model.compare.CompareDataM;

public class CurrentAddressInfo extends ElementHelper implements ElementInf{
	private static transient Logger logger = Logger.getLogger(CurrentAddressInfo.class);
	String PERSONAL_TYPE_SUPPLEMENTARY = SystemConstant.getConstant("PERSONAL_TYPE_SUPPLEMENTARY");
	String ADDRESS_TYPE_CURRENT = SystemConstant.getConstant("ADDRESS_TYPE_CURRENT");
	
	@Override
	public String getElement(HttpServletRequest request, Object objectElement) {
		return "/orig/ulo/popup/CurrentAddressInfo.jsp";
	}
	
	@Override
	public String getDisplayType() {
		return ElementInf.DISPLAY_JSP;
	}
	
	@Override
	public String processElement(HttpServletRequest request, Object objectElement) {
		AddressDataM address = (AddressDataM)objectElement;
		String PROVINCE = request.getParameter("PROVINCE");
		String ZIPCODE = request.getParameter("ZIPCODE");
		String AMPHUR = request.getParameter("AMPHUR");
		String TAMBOL = request.getParameter("TAMBOL");
		String COUNTRY = request.getParameter("COUNTRY");
		logger.debug("PROVINCE >>"+PROVINCE);	
		logger.debug("ZIPCODE >>"+ZIPCODE);	
		logger.debug("AMPHUR >>"+AMPHUR);
		logger.debug("TAMBOL >>"+TAMBOL);	
		logger.debug("COUNTRY >>"+COUNTRY);	

		address.setProvinceDesc(PROVINCE);
		address.setZipcode(ZIPCODE);
		address.setAmphur(AMPHUR);
		address.setTambol(TAMBOL);
		address.setCountry(COUNTRY);
		return null;
	}

	@SuppressWarnings("unchecked")
	@Override
	public HashMap<String, Object> validateElement(HttpServletRequest request, Object objectElement) {
		String subformId = (String)getObjectRequest();
		logger.debug("subformId : "+subformId);
		FormErrorUtil formError = new FormErrorUtil();		
		HashMap<String,AddressDataM> hashAddress = (HashMap<String,AddressDataM>)objectElement;
		AddressDataM currentAddress = hashAddress.get(ADDRESS_TYPE_CURRENT);
		if(null == currentAddress){
			currentAddress = new AddressDataM();
		}

		formError.mandatory(subformId,"PROVINCE",currentAddress.getProvinceDesc(),request);
		
		String TOPUP_FLAG = request.getParameter("TOPUP_FLAG");
		if(Util.empty(TOPUP_FLAG))
		{formError.mandatory(subformId,"ZIPCODE",currentAddress.getZipcode(),request);}

		return formError.getFormError();
	}

	@Override
	public void displayValueElement(HttpServletRequest request, Object objectElement, FormDisplayValueUtil formValue) {
		PersonalInfoDataM personalInfo = (PersonalInfoDataM)objectElement;		
//		String PERSONAL_TYPE = personalInfo.getPersonalType();
		AddressDataM currentAddress = personalInfo.getAddress(ADDRESS_TYPE_CURRENT);
		if(null == currentAddress){
			currentAddress = new AddressDataM();
		}
//		String PREFIX_ELEMENT_ID = PERSONAL_TYPE+"_"+personalInfo.getIdno()+"_"+ADDRESS_TYPE_CURRENT;
		String PREFIX_ELEMENT_ID = CompareSensitiveFieldUtil.getAddressRefId(personalInfo, ADDRESS_TYPE_CURRENT);	
		String PREFIX_ADDRESS_CIS = CompareDataM.UniqueLevel.ADDRESS+"_"+currentAddress.getAddressId();
		currentAddress.setProvinceDesc(formValue.getValue("PROVINCE","PROVINCE_"+PREFIX_ADDRESS_CIS,currentAddress.getProvinceDesc()));
		currentAddress.setZipcode(formValue.getValue("ZIPCODE","ZIPCODE_"+PREFIX_ADDRESS_CIS,currentAddress.getZipcode()));
		currentAddress.setAmphur(formValue.getValue("PROVINCE","AMPHUR_"+PREFIX_ADDRESS_CIS,currentAddress.getAmphur()));
		currentAddress.setTambol(formValue.getValue("ZIPCODE","TAMBOL_"+PREFIX_ADDRESS_CIS,currentAddress.getTambol()));
	}
	
	@Override
	public ArrayList<FieldElement> elementForm(HttpServletRequest request,Object objectForm) {
		ArrayList<FieldElement> fieldElements = new ArrayList<FieldElement>();
		String[] fieldNameList={"CURRENT_PROVINCE","CURRENT_ZIPCODE","CURRENT_AMPHUR","CURRENT_TAMBOL"};
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
