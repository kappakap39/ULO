package com.eaf.orig.ulo.app.view.form.popup;

import java.util.ArrayList;
import java.util.HashMap;

import javax.servlet.http.HttpServletRequest;

import org.apache.log4j.Logger;

import com.eaf.core.ulo.common.model.FieldElement;
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

@SuppressWarnings("serial")
public class HomeAddressPopup1 extends ORIGSubForm{
	private static transient Logger logger = Logger.getLogger(HomeAddressPopup1.class);
	String subformId = "HOME_ADDRESS_POPUP_1";
	String PERSONAL_TYPE = SystemConstant.getConstant("PERSONAL_TYPE_APPLICANT");	
	String ADDRESS_TYPE_DOCUMENT = SystemConstant.getConstant("ADDRESS_TYPE_DOCUMENT");	
	String COMPLETE_FLAG_Y = SystemConstant.getConstant("COMPLETE_FLAG_Y");
	String COMPLETE_FLAG_N = SystemConstant.getConstant("COMPLETE_FLAG_N");

	@SuppressWarnings("unchecked")
	@Override
	public void setProperties(HttpServletRequest request, Object appForm) {
		HashMap<String,AddressDataM> hashAddress = (HashMap<String,AddressDataM>)appForm;
		AddressDataM address = hashAddress.get(ADDRESS_TYPE_DOCUMENT);
		
		String PROVINCE = request.getParameter("PROVINCE");
		String ZIPCODE = request.getParameter("ZIPCODE");

		logger.debug("PROVINCE >>"+PROVINCE);	
		logger.debug("ZIPCODE >>"+ZIPCODE);	

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
		if(null == addressHome){
			addressHome = new AddressDataM();
		}
		
		//KPL Additional
		String TOPUP_FLAG = request.getParameter("TOPUP_FLAG");
		if(Util.empty(TOPUP_FLAG))
		{
			formError.mandatory(subformId,"PROVINCE",addressHome.getProvinceDesc(),request);
			formError.mandatory(subformId,"ZIPCODE",addressHome.getZipcode(),request);
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
	
	@Override
	public void displayValueForm(HttpServletRequest request, Object objectForm) {
		ApplicationGroupDataM applicationGroup = (ApplicationGroupDataM)objectForm;

		String addressId = getSubformData("ADDRESS_ID");
		String personalType = getSubformData("PERSONAL_TYPE");
		logger.debug("addressId >> "+addressId);
		FormDisplayValueUtil formValue = new FormDisplayValueUtil(formId,formLevel,roleId,subformId,applicationGroup,request);
		PersonalInfoDataM personalInfo = applicationGroup.getPersonalInfo(personalType);
//		String PERSONAL_TYPE = personalInfo.getPersonalType();	
		AddressDataM address = personalInfo.getAddress(ADDRESS_TYPE_DOCUMENT);
		if(null == address){
			address = new AddressDataM();
		}
//		String PREFIX_ELEMENT_ID = PERSONAL_TYPE+"_"+personalInfo.getIdno()+"_"+ADDRESS_TYPE_DOCUMENT;
		String PREFIX_ELEMENT_ID = CompareSensitiveFieldUtil.getAddressRefId(personalInfo, ADDRESS_TYPE_DOCUMENT);
		address.setProvinceDesc(formValue.getValue("PROVINCE","PROVINCE_"+PREFIX_ELEMENT_ID,address.getProvinceDesc()));
		address.setZipcode(formValue.getValue("ZIPCODE","ZIPCODE_"+PREFIX_ELEMENT_ID,address.getZipcode()));
	}
	
	@Override
	public ArrayList<FieldElement> elementForm(HttpServletRequest request,Object objectForm) {
	ArrayList<FieldElement> fieldElements = new ArrayList<FieldElement>();
	String[] fieldNameList={"DOCUMENT_PROVINCE","DOCUMENT_ZIPCODE"};
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

