package com.eaf.orig.ulo.app.view.form.popup;

import java.util.ArrayList;
import java.util.HashMap;

import javax.servlet.http.HttpServletRequest;

import org.apache.log4j.Logger;

import com.eaf.core.ulo.common.display.ElementInf;
import com.eaf.core.ulo.common.message.MessageErrorUtil;
import com.eaf.core.ulo.common.model.FieldElement;
import com.eaf.core.ulo.common.properties.ImplementControl;
import com.eaf.core.ulo.common.properties.SystemConstant;
import com.eaf.core.ulo.common.util.FormDisplayValueUtil;
import com.eaf.core.ulo.common.util.FormErrorUtil;
import com.eaf.core.ulo.common.util.Util;
import com.eaf.orig.shared.view.form.ORIGSubForm;
import com.eaf.orig.ulo.constant.MConstant;
import com.eaf.orig.ulo.control.address.util.DisplayAddressUtil;
import com.eaf.orig.ulo.model.app.AddressDataM;
import com.eaf.orig.ulo.model.app.ApplicationGroupDataM;
import com.eaf.orig.ulo.model.app.PersonalInfoDataM;
import com.eaf.orig.ulo.pl.app.view.webaction.AddressUtil;

@SuppressWarnings("serial")
public class CurrentAddressPopup1 extends ORIGSubForm{
	private static transient Logger logger = Logger.getLogger(CurrentAddressPopup1.class);
	String subformId = "CURRENT_ADDRESS_POPUP_1";
	String ADDRESS_TYPE_CURRENT = SystemConstant.getConstant("ADDRESS_TYPE_CURRENT");
	String COMPLETE_FLAG_Y = SystemConstant.getConstant("COMPLETE_FLAG_Y");
	String COMPLETE_FLAG_N = SystemConstant.getConstant("COMPLETE_FLAG_N");
//	String PERSONAL_TYPE = SystemConstant.getConstant("PERSONAL_TYPE_APPLICANT");	

	@SuppressWarnings("unchecked")
	@Override
	public void setProperties(HttpServletRequest request, Object appForm) {
		logger.debug("CurrentAddressPopup1.setProperties");
		String PERSONAL_TYPE = getSubformData("PERSONAL_TYPE");
		HashMap<String,AddressDataM> hashAddress = (HashMap<String,AddressDataM>)appForm;
		AddressDataM address = hashAddress.get(ADDRESS_TYPE_CURRENT);
		logger.debug("PERSONAL_TYPE : "+PERSONAL_TYPE);
		ArrayList<ElementInf> elements = ImplementControl.getElements(MConstant.IMPLEMENT_TYPE.ADDRESS);
		logger.debug("element >> "+elements.size());
		for(ElementInf element:elements){
			if(MConstant.FLAG_Y.equals(element.renderElementFlag(request, PERSONAL_TYPE))){
				element.processElement(request, address);
			}
		}
		DisplayAddressUtil.setAddressLine(address);
	}

	@SuppressWarnings({ "unchecked", "rawtypes" })
	@Override
	public HashMap validateForm(HttpServletRequest request, Object appForm) {
		logger.debug("subformId >> "+subformId);
		FormErrorUtil formError = new FormErrorUtil();
		String PERSONAL_TYPE = getSubformData("PERSONAL_TYPE");
		HashMap<String,AddressDataM> hashAddress = (HashMap<String,AddressDataM>)appForm;
		AddressDataM address = hashAddress.get(ADDRESS_TYPE_CURRENT);
		ArrayList<ElementInf> elements = ImplementControl.getElements(MConstant.IMPLEMENT_TYPE.ADDRESS);
		logger.debug("element >> "+elements.size());
		for(ElementInf element:elements){
			if(MConstant.FLAG_Y.equals(element.renderElementFlag(request, PERSONAL_TYPE))){
				element.setObjectRequest(subformId);
				formError.addAll(element.validateElement(request, hashAddress));
			}
		}
		formError.mandatory(subformId, "MATCHES_ADDRESS", address, request);
//		#rawi comment for change logic set edit to after process form
//		if(Util.empty(formError.getFormError())){
//			address.setEditFlag(COMPLETE_FLAG_Y);
//		}else{
//			address.setEditFlag(COMPLETE_FLAG_N);
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
//		String addressId = getSubformData("ADDRESS_ID");
		String personalType = getSubformData("PERSONAL_TYPE");
		FormDisplayValueUtil formValue = new FormDisplayValueUtil(formId,formLevel,roleId,subformId,applicationGroup,request);	
		PersonalInfoDataM personalInfo = applicationGroup.getPersonalInfo(personalType);
		ArrayList<ElementInf> elements = ImplementControl.getElements(MConstant.IMPLEMENT_TYPE.ADDRESS);
		logger.debug("element >> "+elements.size());
		for(ElementInf element:elements){
			if(MConstant.FLAG_Y.equals(element.renderElementFlag(request, personalType))){
				element.displayValueElement(request, personalInfo,formValue);
			}
		}
		
	}
	
	@Override
	public ArrayList<FieldElement> elementForm(HttpServletRequest request,Object objectForm) {
		ArrayList<FieldElement> fieldElements = new ArrayList<FieldElement>();
 
		try {
			String PERSONAL_TYPE = getSubformData("PERSONAL_TYPE");
			logger.debug("personalType>>>"+PERSONAL_TYPE);
			ApplicationGroupDataM applicationGroup = ((ApplicationGroupDataM)objectForm);			
			PersonalInfoDataM personalInfo = applicationGroup.getPersonalInfo(PERSONAL_TYPE);
			
			ArrayList<ElementInf> elements = ImplementControl.getElements(MConstant.IMPLEMENT_TYPE.ADDRESS);
			logger.debug("element >> "+elements.size());
			for(ElementInf element:elements){
				if(MConstant.FLAG_Y.equals(element.renderElementFlag(request, PERSONAL_TYPE))){
					ArrayList<FieldElement> fieldElementList = element.elementForm(request, personalInfo);
					if(!Util.empty(fieldElementList)){
						fieldElements.addAll(fieldElementList);
					}
				}
			}
		} catch (Exception e) {
			logger.fatal("ERROR",e);
		}
		return fieldElements;
	}
}
