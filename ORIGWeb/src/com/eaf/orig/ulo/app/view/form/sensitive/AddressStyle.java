package com.eaf.orig.ulo.app.view.form.sensitive;

import java.util.ArrayList;
import java.util.HashMap;

import javax.servlet.http.HttpServletRequest;

import org.apache.log4j.Logger;

import com.eaf.core.ulo.common.display.ElementHelper;
import com.eaf.core.ulo.common.display.ElementInf;
import com.eaf.core.ulo.common.display.FormatUtil;
import com.eaf.core.ulo.common.display.HtmlUtil;
import com.eaf.core.ulo.common.model.FieldElement;
import com.eaf.core.ulo.common.properties.FormControl;
import com.eaf.core.ulo.common.properties.SystemConstant;
import com.eaf.core.ulo.common.util.FormErrorUtil;
import com.eaf.core.ulo.common.util.Util;
import com.eaf.orig.ulo.app.view.form.sensitive.util.CompareSensitiveFieldUtil;
import com.eaf.orig.ulo.formcontrol.view.form.ModuleFormHandler;
import com.eaf.orig.ulo.model.app.AddressDataM;
import com.eaf.orig.ulo.model.app.ApplicationGroupDataM;
import com.eaf.orig.ulo.model.app.PersonalInfoDataM;
import com.eaf.orig.ulo.model.compare.CompareDataM;

public class AddressStyle extends ElementHelper implements ElementInf {
	private static transient Logger logger = Logger.getLogger(AddressStyle.class);
	String ADDRESS_TYPE_CURRENT = SystemConstant.getConstant("ADDRESS_TYPE_CURRENT");
	String FIELD_ID_ADDRESS_STYLE = SystemConstant.getConstant("FIELD_ID_ADDRESS_STYLE");
	String REKEY_FORM = SystemConstant.getConstant("REKEY_FORM");
	String PERSONAL_TYPE_INFO = SystemConstant.getConstant("PERSONAL_TYPE_INFO");	
	@Override
	public String getElement(HttpServletRequest request,Object objectElement) {				
		ModuleFormHandler RekeyForm = (ModuleFormHandler)request.getSession().getAttribute(REKEY_FORM);
		ApplicationGroupDataM applicationGroup = (ApplicationGroupDataM)RekeyForm.getObjectForm();
		CompareDataM compareData = ((CompareDataM)objectElement);
		AddressDataM address =   CompareSensitiveFieldUtil.getAddressByUniqueId(applicationGroup, compareData);
		if(null==address){
			address = new AddressDataM();
		}
		String personalId = address.getPersonalId();
		logger.debug("personalId>>>"+personalId);
		PersonalInfoDataM personalInfoM = applicationGroup.getPersonalInfoId(personalId);
		if(null == personalInfoM){
			personalInfoM = new PersonalInfoDataM();
		}
//		String addressRefId = CompareSensitiveFieldUtil.getAddressRefId(personalInfoM,ADDRESS_TYPE_CURRENT);
		String elementId = FormatUtil.getAddressElementId(personalInfoM, ADDRESS_TYPE_CURRENT);
		String tagId = HtmlUtil.elementTagId(compareData.getFieldNameType(),elementId);
		String displayMode = HtmlUtil.EDIT;
		StringBuilder HTML = new StringBuilder();
		HTML.append("<div class='col-sm-12'>")
			.append("<div class='form-group'>")
			.append(HtmlUtil.getFieldLabel(request, "ADDRESS_STYLE_"+ADDRESS_TYPE_CURRENT, "col-sm-4 col-md-5 control-label"))
			.append(HtmlUtil.dropdown(CompareSensitiveFieldUtil.getElementName(compareData),CompareSensitiveFieldUtil.getElementSuffix(compareData), "ADDRESS_STYLE_"+ADDRESS_TYPE_CURRENT, "", "", 
				FIELD_ID_ADDRESS_STYLE,"ALL_ALL_ALL",displayMode,tagId, "col-sm-8 col-md-7",request))
			.append("</div></div>")
			.append("<div class='clearfix'></div>");		
		return HTML.toString();
	}	
	@Override
	public <T>Object getObjectElement(HttpServletRequest request,Object objectForm,FieldElement fieldElement){
		String roleId = FormControl.getFormRoleId(request);
		String elementId = getImplementId();
		ApplicationGroupDataM applicationGroup = ((ApplicationGroupDataM)objectForm);
		PersonalInfoDataM personalInfoM = applicationGroup.getPersonalById(fieldElement.getElementGroupId());
		ArrayList<CompareDataM> compareList = new ArrayList<CompareDataM>();
		if(!Util.empty(personalInfoM)) {
			AddressDataM address =   personalInfoM.getAddress(ADDRESS_TYPE_CURRENT);
			if(!Util.empty(address)) {
				String addressRefId = CompareSensitiveFieldUtil.getAddressRefId(personalInfoM,ADDRESS_TYPE_CURRENT);
				CompareDataM compareData  = new CompareDataM();
				compareData.setApplicationGroupId(applicationGroup.getApplicationGroupId());
				compareData.setFieldNameType(elementId);
				compareData.setValue(address.getBuildingType());
				compareData.setRole(roleId);
				compareData.setRefId(CompareSensitiveFieldUtil.getPersonalRefId(personalInfoM));
				compareData.setCurrentRefId(addressRefId);
				compareData.setRefLevel(CompareDataM.RefLevel.ADDRESS);
				compareData.setSrcOfData(CompareDataM.SoruceOfData.TWO_MAKER);
				compareData.setUniqueId(address.getAddressId());
				compareData.setUniqueLevel(CompareDataM.UniqueLevel.ADDRESS);
				compareData.setSeq(CompareSensitiveFieldUtil.getCompareFieldSeq(elementId));
				compareData.setConfigData(CompareSensitiveFieldUtil.fieldConfigDataMToJSonString(personalInfoM,""));
				compareData.setFieldName(CompareSensitiveFieldUtil.getFieldName(compareData));
				compareList.add(compareData);
			}
		}
		return compareList;
	}
	@Override
	public String processElement(HttpServletRequest request,Object objectElement) {
		ModuleFormHandler RekeyForm = (ModuleFormHandler)request.getSession().getAttribute(REKEY_FORM);
		ApplicationGroupDataM applicationGroup = (ApplicationGroupDataM)RekeyForm.getObjectForm();		
		CompareDataM compareData = ((CompareDataM)objectElement);
		AddressDataM currentAddress = CompareSensitiveFieldUtil.getAddressByUniqueId(applicationGroup, compareData);
		if(!Util.empty(currentAddress)) {
			String paramName = CompareSensitiveFieldUtil.getParameterName(compareData);
			logger.debug("paramName>>>"+paramName);
			String buildingType = request.getParameter(paramName);
			currentAddress.setBuildingType(buildingType);
		}
		return null;
	}	
	@Override
	public HashMap<String,Object> validateElement(HttpServletRequest request, Object objectElement){
		FormErrorUtil formError = new FormErrorUtil();
		/*String PREFIX_ERROR = MessageErrorUtil.getText(request,"PREFIX_ERROR");
		ModuleFormHandler RekeyForm = (ModuleFormHandler)request.getSession().getAttribute(REKEY_FORM);
		ApplicationGroupDataM applicationGroup = (ApplicationGroupDataM)RekeyForm.getObjectForm();		
		CompareDataM compareData = ((CompareDataM)objectElement);
		PersonalInfoDataM personalInfoM = CompareSensitiveFieldUtil.getPersonalInfoByRefId(applicationGroup, compareData);
		AddressDataM currentAddress = null;
		if(!Util.empty(personalInfoM)) {
			currentAddress = personalInfoM.getAddress(ADDRESS_TYPE_CURRENT);
		}		
		if(Util.empty(currentAddress) || Util.empty(currentAddress.getBuildingType())) {
			formError.error(compareData.getFieldName(),PREFIX_ERROR+LabelUtil.getText(request,compareData.getFieldNameType()));
		}*/
		return formError.getFormError();
	}
}
