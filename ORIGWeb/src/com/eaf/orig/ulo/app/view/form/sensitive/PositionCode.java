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
import com.eaf.orig.ulo.model.app.ApplicationGroupDataM;
import com.eaf.orig.ulo.model.app.PersonalInfoDataM;
import com.eaf.orig.ulo.model.compare.CompareDataM;

public class PositionCode extends ElementHelper implements ElementInf{
	private static transient Logger logger = Logger.getLogger(PositionCode.class);
	String REKEY_FORM = SystemConstant.getConstant("REKEY_FORM");
	String FIELD_ID_POSITION_CODE = SystemConstant.getConstant("FIELD_ID_POSITION_CODE");
	String PERSONAL_TYPE_INFO = SystemConstant.getConstant("PERSONAL_TYPE_INFO");	
	String GOVERNMENT_OFFICER = SystemConstant.getConstant("GOVERNMENT_OFFICER");		
	@Override
	public String getElement(HttpServletRequest request,Object objectElement)  {		
		ModuleFormHandler RekeyForm = (ModuleFormHandler)request.getSession().getAttribute(REKEY_FORM);
		ApplicationGroupDataM applicationGroup = (ApplicationGroupDataM)RekeyForm.getObjectForm();		
		CompareDataM compareData = (CompareDataM)objectElement;
		PersonalInfoDataM personalInfoM = CompareSensitiveFieldUtil.getPersonalInfoByUniqueId(applicationGroup, compareData);
		if(null == personalInfoM){
			personalInfoM = new PersonalInfoDataM();
		}			
		String elementId = FormatUtil.getPersonalElementId(personalInfoM);
		String tagId = HtmlUtil.elementTagId(compareData.getFieldNameType(),elementId);
		String displayMode = HtmlUtil.EDIT;		
		StringBuffer HTML = new StringBuffer();
		HTML.append("<div class='col-sm-12'>")
		.append("<div class='form-group'>")
		.append(HtmlUtil.getFieldLabel(request, compareData.getFieldNameType(), "col-sm-4 col-md-5 control-label"))
		.append(HtmlUtil.dropdown(CompareSensitiveFieldUtil.getElementName(compareData),CompareSensitiveFieldUtil.getElementSuffix(compareData),"POSITION_LEVEL","","",FIELD_ID_POSITION_CODE,"",
				displayMode,tagId,"col-sm-8 col-md-7",request))
		.append("</div></div>")
		.append("<div class='clearfix'></div>");		
		return HTML.toString();
	}
	
	@Override
	public <T>Object getObjectElement(HttpServletRequest request,Object objectForm,FieldElement fieldElement){
		String roleId = FormControl.getFormRoleId(request);
		ApplicationGroupDataM applicationGroup = ((ApplicationGroupDataM)objectForm);
		PersonalInfoDataM personalInfoM = applicationGroup.getPersonalById(fieldElement.getElementGroupId());
		ArrayList<CompareDataM> compareList = new ArrayList<CompareDataM>();
		if(!Util.empty(personalInfoM)) {
			CompareDataM compareData  = new CompareDataM();
			String personalRefId = CompareSensitiveFieldUtil.getPersonalRefId(personalInfoM);	
			compareData.setApplicationGroupId(applicationGroup.getApplicationGroupId());
			compareData.setFieldNameType(getImplementId());
			compareData.setValue(personalInfoM.getPositionCode());
			logger.debug("personalInfoM.getPositionCode()>>>"+personalInfoM.getPositionCode());
			compareData.setRole(roleId);
			compareData.setRefId(personalRefId);
			compareData.setRefLevel(CompareDataM.RefLevel.PERSONAL);
			compareData.setSrcOfData(CompareDataM.SoruceOfData.TWO_MAKER);
			compareData.setUniqueId(personalInfoM.getPersonalId());
			compareData.setUniqueLevel(CompareDataM.UniqueLevel.PERSONAL);
			compareData.setSeq(CompareSensitiveFieldUtil.getCompareFieldSeq(getImplementId()));
			compareData.setConfigData(CompareSensitiveFieldUtil.fieldConfigDataMToJSonString(personalInfoM,""));
			compareData.setFieldName(CompareSensitiveFieldUtil.getFieldName(compareData));
			compareList.add(compareData);
		}
		return compareList;
	}

	@Override
	public String processElement(HttpServletRequest request,Object objectElement) {
		ModuleFormHandler RekeyForm = (ModuleFormHandler)request.getSession().getAttribute(REKEY_FORM);
		ApplicationGroupDataM applicationGroup = (ApplicationGroupDataM)RekeyForm.getObjectForm();		
		CompareDataM compareData = ((CompareDataM)objectElement);
		PersonalInfoDataM personalInfoM = CompareSensitiveFieldUtil.getPersonalInfoByUniqueId(applicationGroup, compareData);
		if(!Util.empty(personalInfoM)) {
			String paramName =  CompareSensitiveFieldUtil.getParameterName(compareData);
			logger.debug("paramName>>"+paramName);
			String positionCode = request.getParameter(paramName);
			logger.debug("positionCode>>>"+positionCode);
			if (GOVERNMENT_OFFICER.equals(personalInfoM.getOccupation())) {
				personalInfoM.setPositionCode(positionCode);
			}else{
				personalInfoM.setPositionCode("");
			}			
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
		if(Util.empty(personalInfoM) || 
				(GOVERNMENT_OFFICER.equals(personalInfoM.getOccupation()) && Util.empty(personalInfoM.getPositionCode()))) {
			formError.error(compareData.getFieldName(),PREFIX_ERROR+LabelUtil.getText(request,compareData.getFieldNameType()));
		}*/
		return formError.getFormError();
	}
}
