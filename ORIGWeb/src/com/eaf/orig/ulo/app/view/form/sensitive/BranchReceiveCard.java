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
import com.eaf.orig.ulo.control.util.PersonalInfoUtil;
import com.eaf.orig.ulo.formcontrol.view.form.ModuleFormHandler;
import com.eaf.orig.ulo.model.app.ApplicationGroupDataM;
import com.eaf.orig.ulo.model.app.PersonalInfoDataM;
import com.eaf.orig.ulo.model.compare.CompareDataM;

public class BranchReceiveCard extends ElementHelper implements ElementInf {
	private static transient Logger logger = Logger.getLogger(BranchReceiveCard.class);
	String SEARCH_BRANCH_INFO = SystemConstant.getConstant("SEARCH_BRANCH_INFO");
	String REKEY_FORM = SystemConstant.getConstant("REKEY_FORM");
	String PERSONAL_TYPE_INFO = SystemConstant.getConstant("PERSONAL_TYPE_INFO");
	String PLACE_RECEIVE_CARD_MAJOR = SystemConstant.getConstant("PLACE_RECEIVE_CARD_MAJOR");
	String PERSONAL_TYPE_SUPPLEMENTARY = SystemConstant.getConstant("PERSONAL_TYPE_SUPPLEMENTARY");
	@Override
	public String getElement(HttpServletRequest request,Object objectElement) {		
		ModuleFormHandler RekeyForm = (ModuleFormHandler)request.getSession().getAttribute(REKEY_FORM);
		ApplicationGroupDataM applicationGroup = (ApplicationGroupDataM)RekeyForm.getObjectForm();
		CompareDataM compareData = ((CompareDataM)objectElement);
		PersonalInfoDataM personalInfoM = CompareSensitiveFieldUtil.getPersonalInfoByUniqueId(applicationGroup, compareData);
		if(null == personalInfoM){
			personalInfoM = new PersonalInfoDataM();
		}		
//		String personalRefId = CompareSensitiveFieldUtil.getPersonalRefId(personalInfoM);	
		String elementId = FormatUtil.getPersonalElementId(personalInfoM);
		String tagId = HtmlUtil.elementTagId(compareData.getFieldNameType(),elementId);
		String displayMode = HtmlUtil.VIEW;
		if(PLACE_RECEIVE_CARD_MAJOR.equals(personalInfoM.getPlaceReceiveCard())){
			displayMode = HtmlUtil.EDIT;
		}
		
		String fieldCode="BRANCH_RECEIVE_CARD";
		if(PERSONAL_TYPE_SUPPLEMENTARY.equals(personalInfoM.getPersonalType())){
			fieldCode="BRANCH_RECEIVE_CARD_SUB";
		}
		
		StringBuilder HTML = new StringBuilder();
		HTML.append("<div class='col-sm-12'>")
			.append("<div class='form-group'>")
			.append(HtmlUtil.getFieldLabel(request, fieldCode, "col-sm-4 col-md-5 control-label"))
			.append(HtmlUtil.search(CompareSensitiveFieldUtil.getElementName(compareData),CompareSensitiveFieldUtil.getElementSuffix(compareData),SEARCH_BRANCH_INFO, "", "","",
					"",displayMode,tagId,"col-sm-8 col-md-7",request))
			.append("</div></div>")
			.append(HtmlUtil.hidden("REKEY_BRANCH_RECEIVE_CARD_NAME", ""))
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
			CompareDataM compareData  = new CompareDataM();
			String personalRefId = CompareSensitiveFieldUtil.getPersonalRefId(personalInfoM);	
			compareData.setApplicationGroupId(applicationGroup.getApplicationGroupId());
			compareData.setFieldNameType(elementId);
			compareData.setValue(personalInfoM.getBranchReceiveCard());
			compareData.setRole(roleId);
			compareData.setRefId(personalRefId);
			compareData.setRefLevel(CompareDataM.RefLevel.PERSONAL);
			compareData.setSrcOfData(CompareDataM.SoruceOfData.TWO_MAKER);
			compareData.setUniqueId(personalInfoM.getPersonalId());
			compareData.setUniqueLevel(CompareDataM.UniqueLevel.PERSONAL);
			compareData.setSeq(CompareSensitiveFieldUtil.getCompareFieldSeq(elementId));
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
		if(!Util.empty(personalInfoM)){
			String paramName = CompareSensitiveFieldUtil.getParameterName(compareData);
			logger.debug("paramName>>"+paramName);
			String branchReceiveCard = request.getParameter(paramName);
			String BRANCH_RECEIVE_CARD_NAME = request.getParameter("REKEY_BRANCH_RECEIVE_CARD_NAME");		
			BRANCH_RECEIVE_CARD_NAME = PersonalInfoUtil.getBranchReceiceName(branchReceiveCard, BRANCH_RECEIVE_CARD_NAME);
			personalInfoM.setBranchReceiveCard(branchReceiveCard);
			personalInfoM.setBranchReceiveCardName(BRANCH_RECEIVE_CARD_NAME);
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
				(PLACE_RECEIVE_CARD_MAJOR.equals(personalInfoM.getPlaceReceiveCard()) 
						&& Util.empty(personalInfoM.getBranchReceiveCard()))) {
			formError.error(compareData.getFieldName(),PREFIX_ERROR+LabelUtil.getText(request,compareData.getFieldNameType()));
		}*/
		return formError.getFormError();
	}
}
