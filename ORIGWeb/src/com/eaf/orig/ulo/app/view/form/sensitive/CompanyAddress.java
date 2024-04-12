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

public class CompanyAddress extends ElementHelper implements ElementInf {
	String ADDRESS_TYPE_WORK = SystemConstant.getConstant("ADDRESS_TYPE_WORK");	
	String REKEY_FORM = SystemConstant.getConstant("REKEY_FORM");
	String PERSONAL_TYPE_INFO = SystemConstant.getConstant("PERSONAL_TYPE_INFO");
	private static transient Logger logger = Logger.getLogger(CompanyAddress.class);
	@Override
	public String getElement(HttpServletRequest request,Object objectElement) {		
		ModuleFormHandler RekeyForm = (ModuleFormHandler)request.getSession().getAttribute(REKEY_FORM);
		ApplicationGroupDataM applicationGroup = (ApplicationGroupDataM)RekeyForm.getObjectForm();		
		CompareDataM compareData = ((CompareDataM)objectElement);
		String displayMode = HtmlUtil.EDIT;
		AddressDataM address = CompareSensitiveFieldUtil.getAddressByUniqueId(applicationGroup, compareData);
		if(null==address){
			address = new AddressDataM();
		}
		
		String personalId = address.getPersonalId();
		logger.debug("personalId>>>"+personalId);
		PersonalInfoDataM personalInfo = applicationGroup.getPersonalInfoId(personalId);
		if(null == personalInfo){
			personalInfo = new PersonalInfoDataM();
		}
 
//		String addressRefId = CompareSensitiveFieldUtil.getAddressRefId(personalInfo,ADDRESS_TYPE_WORK);
		String elementId = FormatUtil.getAddressElementId(personalInfo,ADDRESS_TYPE_WORK);
		String tagId = HtmlUtil.elementTagId(compareData.getFieldNameType(),elementId);
		StringBuilder HTML = new StringBuilder();
		HTML.append("<div class='col-sm-12'>")
		.append("<div class='form-group'>")
		.append(HtmlUtil.getFieldLabel(request,"ADDRESS_NO_"+ADDRESS_TYPE_WORK, "col-sm-4 col-md-5 control-label"))
		.append("<div class='input-group'>")
		.append(HtmlUtil.textBox(CompareSensitiveFieldUtil.getElementName(compareData),CompareSensitiveFieldUtil.getElementSuffix(compareData), "","","20",displayMode,
				tagId,"col-sm-8 col-md-7",request))
		.append("</div>")
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
			AddressDataM address =   personalInfoM.getAddress(ADDRESS_TYPE_WORK);
			if(!Util.empty(address)) {
				String addressRefId = CompareSensitiveFieldUtil.getAddressRefId(personalInfoM,ADDRESS_TYPE_WORK);
				CompareDataM compareData  = new CompareDataM();
				compareData.setApplicationGroupId(applicationGroup.getApplicationGroupId());
				compareData.setFieldNameType(elementId);
				compareData.setValue(address.getAddress());
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
		AddressDataM address = CompareSensitiveFieldUtil.getAddressByUniqueId(applicationGroup, compareData);
		if(null==address){
			address = new AddressDataM();
		}
		if(!Util.empty(address)){
			String paramName = CompareSensitiveFieldUtil.getParameterName(compareData);
			logger.debug("paramName>>>"+paramName);
			String addressStr = request.getParameter(paramName);
			address.setAddress(request.getParameter(addressStr));
		}
		/*PersonalInfoDataM personalInfoM = CompareSensitiveFieldUtil.getPersonalInfoByRefId(applicationGroup, compareData);
		AddressDataM address = null;
		if(!Util.empty(personalInfoM)) {
			address = personalInfoM.getAddress(ADDRESS_TYPE_WORK);
		}
		if(!Util.empty(address)){
			String addressStr = request.getParameter(compareData.getFieldName());
			address.setAddress(request.getParameter(addressStr));
		}*/
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
		AddressDataM address = null;
		if(!Util.empty(personalInfoM)) {
			address = personalInfoM.getAddress(ADDRESS_TYPE_WORK);
		}
		if(Util.empty(address) || Util.empty(address.getAddress())) {
			formError.error(compareData.getFieldName(),PREFIX_ERROR+LabelUtil.getText(request,"ADDRESS_NO_"+ADDRESS_TYPE_WORK));
		}*/
		return formError.getFormError();
	}
}
