package com.eaf.orig.ulo.app.view.form.sensitive;

import java.util.ArrayList;
import java.util.HashMap;

import javax.servlet.http.HttpServletRequest;

import org.apache.log4j.Logger;

import com.eaf.core.ulo.common.display.ElementHelper;
import com.eaf.core.ulo.common.display.ElementInf;
import com.eaf.core.ulo.common.display.HtmlUtil;
import com.eaf.core.ulo.common.model.FieldElement;
import com.eaf.core.ulo.common.properties.FormControl;
import com.eaf.core.ulo.common.properties.SystemConstant;
import com.eaf.core.ulo.common.util.FormErrorUtil;
import com.eaf.orig.ulo.app.view.form.sensitive.util.CompareSensitiveFieldUtil;
import com.eaf.orig.ulo.formcontrol.view.form.ModuleFormHandler;
import com.eaf.orig.ulo.model.app.ApplicationGroupDataM;
import com.eaf.orig.ulo.model.compare.CompareDataM;

public class PolicyExceptionSignOffAuthorizedBy  extends ElementHelper implements ElementInf{
	private static transient Logger logger = Logger.getLogger(PolicyExceptionSignOffAuthorizedBy.class);
	@Override
	public String getElement(HttpServletRequest request,Object objectElement) {
		CompareDataM compareData = (CompareDataM)objectElement;
		
		StringBuffer HTML = new StringBuffer();
		HTML.append("<div class='col-sm-12'>");
		HTML.append("<div class='form-group'>");
		HTML.append(HtmlUtil.getFieldLabel(request, "POLICY_EXCEPTION_AUTHORIZED_BY", "col-sm-4 col-md-5 control-label"));
		HTML.append(HtmlUtil.textBox(CompareSensitiveFieldUtil.getElementName(compareData),CompareSensitiveFieldUtil.getElementSuffix(compareData),"","","50",HtmlUtil.EDIT,
				HtmlUtil.elementTagId(compareData.getFieldNameType()),"col-sm-8 col-md-7",request));
		HTML.append("</div></div>");
		HTML.append("<div class='clearfix'></div>");
		
		return HTML.toString();
	}
	
	@Override
	public <T>Object getObjectElement(HttpServletRequest request,Object objectForm,FieldElement fieldElement){
		String roleId = FormControl.getFormRoleId(request);
		ApplicationGroupDataM applicationGroup = ((ApplicationGroupDataM)objectForm);
		ArrayList<CompareDataM> compareList = new ArrayList<CompareDataM>();
		
		CompareDataM compareData  = new CompareDataM();
		compareData.setApplicationGroupId(applicationGroup.getApplicationGroupId());
		compareData.setFieldNameType(getImplementId());
		compareData.setValue(applicationGroup.getPolicyExSignOffBy());
		compareData.setRole(roleId);
		compareData.setRefId(applicationGroup.getApplicationGroupId());
		compareData.setRefLevel(CompareDataM.RefLevel.APPLICATION_GROUP);
		compareData.setSrcOfData(CompareDataM.SoruceOfData.TWO_MAKER);
		compareData.setUniqueId(applicationGroup.getApplicationGroupId());
		compareData.setUniqueLevel(CompareDataM.UniqueLevel.APPLICATION_GROUP);
		compareData.setSeq(CompareSensitiveFieldUtil.getCompareFieldSeq(getImplementId()));
		compareData.setConfigData(CompareSensitiveFieldUtil.fieldConfigDataMToJSonString(applicationGroup,""));
		compareData.setFieldName(CompareSensitiveFieldUtil.getFieldName(compareData));
		
		compareList.add(compareData);
		
		return compareList;
	}

	@Override
	public String processElement(HttpServletRequest request,Object objectElement) {
		String REKEY_FORM = SystemConstant.getConstant("REKEY_FORM");
		ModuleFormHandler RekeyForm = (ModuleFormHandler)request.getSession().getAttribute(REKEY_FORM);
		ApplicationGroupDataM applicationGroup = (ApplicationGroupDataM)RekeyForm.getObjectForm();
		
		CompareDataM compareData = (CompareDataM)objectElement;
		String paramName  = CompareSensitiveFieldUtil.getParameterName(compareData);
		logger.debug("paramName>>"+paramName);
		String policySignOffBy = request.getParameter(paramName);
		applicationGroup.setPolicyExSignOffBy(policySignOffBy);
		return null;
	}
	
	@Override
	public HashMap<String,Object> validateElement(HttpServletRequest request, Object objectElement){
		FormErrorUtil formError = new FormErrorUtil();
		/*String PREFIX_ERROR = MessageErrorUtil.getText(request,"PREFIX_ERROR");
		String REKEY_FORM = SystemConstant.getConstant("REKEY_FORM");
		ModuleFormHandler RekeyForm = (ModuleFormHandler)request.getSession().getAttribute(REKEY_FORM);
		ApplicationGroupDataM applicationGroup = (ApplicationGroupDataM)RekeyForm.getObjectForm();
		
		CompareDataM compareData = (CompareDataM)objectElement;
		
		if(Util.empty(applicationGroup.getPolicyExSignOffBy())) {
			formError.error(compareData.getFieldName(),PREFIX_ERROR+LabelUtil.getText(request,compareData.getFieldNameType()));
		}*/
		return formError.getFormError();
	}
}
