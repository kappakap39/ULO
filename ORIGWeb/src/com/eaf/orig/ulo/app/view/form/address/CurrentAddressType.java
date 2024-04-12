package com.eaf.orig.ulo.app.view.form.address;

import java.util.ArrayList;
import java.util.HashMap;

import javax.servlet.http.HttpServletRequest;

import org.apache.log4j.Logger;

import com.eaf.core.ulo.common.display.ElementHelper;
import com.eaf.core.ulo.common.display.ElementInf;
import com.eaf.core.ulo.common.display.FormatUtil;
import com.eaf.core.ulo.common.model.FieldElement;
import com.eaf.core.ulo.common.properties.SystemConstant;
import com.eaf.core.ulo.common.util.FormDisplayValueUtil;
import com.eaf.core.ulo.common.util.FormErrorUtil;
import com.eaf.core.ulo.common.util.Util;
import com.eaf.orig.ulo.app.view.form.sensitive.util.CompareSensitiveFieldUtil;
import com.eaf.orig.ulo.constant.MConstant;
import com.eaf.orig.ulo.model.app.AddressDataM;
import com.eaf.orig.ulo.model.app.PersonalInfoDataM;
import com.eaf.orig.ulo.model.compare.CompareDataM;

public class CurrentAddressType extends ElementHelper implements ElementInf{
	private static transient Logger logger = Logger.getLogger(CurrentAddressType.class);
	String PERSONAL_TYPE_SUPPLEMENTARY = SystemConstant.getConstant("PERSONAL_TYPE_SUPPLEMENTARY");
	String ADDRESS_TYPE_CURRENT = SystemConstant.getConstant("ADDRESS_TYPE_CURRENT");
	
	@Override
	public String getElement(HttpServletRequest request, Object objectElement) {
		return "/orig/ulo/popup/CurrentAddressType.jsp";
	}
	
	@Override
	public String getDisplayType() {
		return ElementInf.DISPLAY_JSP;
	}
	
	@Override
	public String renderElementFlag(HttpServletRequest request, Object objectElement) {
		String FLAG = MConstant.FLAG.NO;
		String PERSONAL_TYPE = (String)objectElement;
		logger.debug("PERSONAL_TYPE : "+PERSONAL_TYPE);
		if(!PERSONAL_TYPE_SUPPLEMENTARY.equals(PERSONAL_TYPE)){			
			FLAG = MConstant.FLAG.YES;
		}
		return FLAG;
	}

	@Override
	public String processElement(HttpServletRequest request, Object objectElement) {
		AddressDataM address = (AddressDataM)objectElement;
		
		String ADDRESS_STYLE = request.getParameter("ADDRESS_STYLE");
		String ADRSTS = request.getParameter("ADRSTS");
		String RENTS = request.getParameter("RENTS");
		String RESIDEY = request.getParameter("RESIDEY");
		String RESIDEM = request.getParameter("RESIDEM");
		address.setBuildingType(ADDRESS_STYLE);
		address.setAdrsts(ADRSTS);
		address.setRents(FormatUtil.toBigDecimal(RENTS));
		address.setResidey(FormatUtil.toBigDecimal(RESIDEY));
		address.setResidem(FormatUtil.toBigDecimal(RESIDEM));
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
		
		formError.mandatory(subformId,"ADDRESS_STYLE",currentAddress.getBuildingType(),request);
		
		String TOPUP_FLAG = request.getParameter("TOPUP_FLAG");
		if(Util.empty(TOPUP_FLAG))
		{formError.mandatory(subformId,"ADRSTS",hashAddress,request);}
		
		formError.mandatory(subformId,"RENTS",currentAddress.getRents(),request);
		formError.mandatory(subformId,"RESIDE",hashAddress,request);
		return formError.getFormError();
	}

	@Override
	public void displayValueElement(HttpServletRequest request, Object objectElement, FormDisplayValueUtil formValue) {
//		ApplicationGroupDataM applicationGroup = (ApplicationGroupDataM)FormControl.getOrigObjectForm(request);
//		ModuleFormHandler moduleHandler = (ModuleFormHandler) request.getSession().getAttribute("ModuleForm");
//		String personalType = moduleHandler.getRequestData("PERSONAL_TYPE");
		PersonalInfoDataM personalInfo = (PersonalInfoDataM)objectElement;		
//		String PERSONAL_TYPE = personalInfo.getPersonalType();	
		AddressDataM currentAddress = personalInfo.getAddress(ADDRESS_TYPE_CURRENT);
		if(null == currentAddress){
			currentAddress = new AddressDataM();
		}
//		String PREFIX_ELEMENT_ID = PERSONAL_TYPE+"_"+personalInfo.getIdno()+"_"+ADDRESS_TYPE_CURRENT;
		String PREFIX_ELEMENT_ID = CompareSensitiveFieldUtil.getAddressRefId(personalInfo,ADDRESS_TYPE_CURRENT);
		currentAddress.setBuildingType(formValue.getValue("ADDRESS_STYLE","ADDRESS_STYLE_"+PREFIX_ELEMENT_ID,currentAddress.getBuildingType()));
		currentAddress.setAdrsts(formValue.getValue("ADRSTS","ADRSTS_"+PREFIX_ELEMENT_ID,currentAddress.getAdrsts()));
		currentAddress.setRents(formValue.getValue("RENTS","RENTS_"+PREFIX_ELEMENT_ID,currentAddress.getRents()));
		currentAddress.setResidey(formValue.getValue("RESIDEY","RESIDEY_"+PREFIX_ELEMENT_ID,currentAddress.getResidey()));
		currentAddress.setResidem(formValue.getValue("RESIDEM","RESIDEM_"+PREFIX_ELEMENT_ID,currentAddress.getResidem()));
	}
	
	@Override
	public ArrayList<FieldElement> elementForm(HttpServletRequest request,Object objectForm) {
		ArrayList<FieldElement> fieldElements = new ArrayList<FieldElement>();
		String[] fieldNameList={"CURRENT_ADDRESS_STYLE","CURRENT_ADRSTS", "CURRENT_RENTS", "CURRENT_RESIDEM", "CURRENT_RESIDEY"};
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
