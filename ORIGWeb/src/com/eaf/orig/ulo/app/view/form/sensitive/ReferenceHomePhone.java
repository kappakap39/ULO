package com.eaf.orig.ulo.app.view.form.sensitive;

import java.util.ArrayList;
import java.util.HashMap;

import javax.servlet.http.HttpServletRequest;

import org.apache.log4j.Logger;

import com.eaf.core.ulo.common.display.ElementHelper;
import com.eaf.core.ulo.common.display.ElementInf;
import com.eaf.core.ulo.common.display.FormatUtil;
import com.eaf.core.ulo.common.display.HtmlUtil;
import com.eaf.core.ulo.common.message.LabelUtil;
import com.eaf.core.ulo.common.model.FieldElement;
import com.eaf.core.ulo.common.properties.FormControl;
import com.eaf.core.ulo.common.properties.SystemConstant;
import com.eaf.core.ulo.common.util.FormErrorUtil;
import com.eaf.core.ulo.common.util.Util;
import com.eaf.orig.ulo.app.view.form.sensitive.util.CompareSensitiveFieldUtil;
import com.eaf.orig.ulo.formcontrol.view.form.ModuleFormHandler;
import com.eaf.orig.ulo.model.app.ApplicationGroupDataM;
import com.eaf.orig.ulo.model.app.ReferencePersonDataM;
import com.eaf.orig.ulo.model.compare.CompareDataM;

public class ReferenceHomePhone extends ElementHelper implements ElementInf {
	private static transient Logger logger = Logger.getLogger(ReferenceHomePhone.class);
	String REKEY_FORM = SystemConstant.getConstant("REKEY_FORM");
	@Override
	public String getElement(HttpServletRequest request,Object objectElement) {
		CompareDataM compareData = (CompareDataM)objectElement;		
		StringBuffer HTML = new StringBuffer();
		HTML.append("<div class='col-sm-12'>");
		HTML.append("<div class='form-group'>");
		String[] tokens = compareData.getRefId().split(CompareDataM.MARKER);
		String seq = tokens[1];
		String label = LabelUtil.getText(request, "REFERENCE_"+seq)+" "+LabelUtil.getText(request,compareData.getFieldNameType());
		HTML.append(HtmlUtil.geCustomFieldLabel(label, "col-sm-4 col-md-5 control-label"))
			.append(HtmlUtil.textBoxTel(CompareSensitiveFieldUtil.getElementName(compareData),CompareSensitiveFieldUtil.getElementSuffix(compareData),"","",HtmlUtil.EDIT,
			HtmlUtil.elementTagId(compareData.getFieldNameType()),"col-sm-8 col-md-7",request))
			.append("</div></div>")
			.append("<div class='clearfix'></div>");		
		return HTML.toString();
	}	
	@Override
	public <T>Object getObjectElement(HttpServletRequest request,Object objectForm,FieldElement fieldElement){
		String roleId = FormControl.getFormRoleId(request);
		ApplicationGroupDataM applicationGroup = (ApplicationGroupDataM)objectForm;
		ArrayList<CompareDataM> compareList = new ArrayList<CompareDataM>();
		ArrayList<ReferencePersonDataM> referencePersonList = applicationGroup.getReferencePersons();
		if(!Util.empty(referencePersonList)) {
			for(ReferencePersonDataM referencePerson : referencePersonList) {
				CompareDataM compareData  = new CompareDataM();
				compareData.setApplicationGroupId(applicationGroup.getApplicationGroupId());
				compareData.setFieldNameType(getImplementId());
				compareData.setValue(referencePerson.getPhone1());
				compareData.setRole(roleId);
				compareData.setRefId(applicationGroup.getApplicationGroupId()+CompareDataM.MARKER+referencePerson.getSeq());
				compareData.setRefLevel(CompareDataM.RefLevel.APPLICATION_GROUP);
				compareData.setSrcOfData(CompareDataM.SoruceOfData.TWO_MAKER);
				compareData.setUniqueId(FormatUtil.toString(referencePerson.getSeq()));
				compareData.setUniqueLevel(CompareDataM.UniqueLevel.APPLICATION_GROUP);
				compareData.setSeq(CompareSensitiveFieldUtil.getCompareFieldSeq(getImplementId()));
				compareData.setConfigData(CompareSensitiveFieldUtil.fieldConfigDataMToJSonString(applicationGroup,""));
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
		String[] tokens = compareData.getRefId().split(CompareDataM.MARKER);
		String seq = tokens[1];
		ReferencePersonDataM referencePerson = applicationGroup.getReferencePersons(Integer.parseInt(seq));
		if(!Util.empty(referencePerson)) {
			String paramName = CompareSensitiveFieldUtil.getParameterName(compareData);
			logger.debug("paramName>>"+paramName);
			String phone = FormatUtil.removeDash(request.getParameter(paramName));
			referencePerson.setPhone1(phone);
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
		String[] tokens = compareData.getRefId().split(CompareDataM.MARKER);
		String seq = tokens[1];
		ReferencePersonDataM referencePerson = applicationGroup.getReferencePersons(Integer.parseInt(seq));
		if(Util.empty(referencePerson) || Util.empty(referencePerson.getPhone1())) {
			formError.error(compareData.getFieldName(),PREFIX_ERROR+LabelUtil.getText(request,compareData.getFieldNameType()));
		}*/
		return formError.getFormError();
	}
}
