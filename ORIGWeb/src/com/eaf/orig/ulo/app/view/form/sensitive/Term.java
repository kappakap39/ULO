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
import com.eaf.orig.ulo.app.view.util.kpl.KPLUtil;
import com.eaf.orig.ulo.control.util.PersonalInfoUtil;
import com.eaf.orig.ulo.formcontrol.view.form.ModuleFormHandler;
import com.eaf.orig.ulo.model.app.ApplicationDataM;
import com.eaf.orig.ulo.model.app.ApplicationGroupDataM;
import com.eaf.orig.ulo.model.app.PersonalInfoDataM;
import com.eaf.orig.ulo.model.compare.CompareDataM;
import com.eaf.orig.ulo.model.app.LoanDataM;

public class Term extends ElementHelper implements ElementInf{
	private static transient Logger logger = Logger.getLogger(Term.class);
	String REKEY_FORM = SystemConstant.getConstant("REKEY_FORM");
	String FIELD_ID_TERM = SystemConstant.getConstant("FIELD_ID_TERM");	
	@Override
	public String getElement(HttpServletRequest request,Object objectElement) {		
		ModuleFormHandler RekeyForm = (ModuleFormHandler)request.getSession().getAttribute(REKEY_FORM);
		ApplicationGroupDataM applicationGroup = (ApplicationGroupDataM)RekeyForm.getObjectForm();		
		CompareDataM compareData = ((CompareDataM)objectElement);
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
		.append(HtmlUtil.getFieldLabel(request, "TERM_LENGTH", "col-sm-4 col-md-5 control-label"))
		.append(HtmlUtil.dropdown(CompareSensitiveFieldUtil.getElementName(compareData),CompareSensitiveFieldUtil.getElementSuffix(compareData), compareData.getFieldNameType(), "", "", 
				FIELD_ID_TERM,"ALL_ALL_ALL", displayMode,tagId, "col-sm-8 col-md-7", request))
		.append("</div></div>")
		.append("<div class='clearfix'></div>");		
		return HTML.toString();
	}	
	@Override
	public <T>Object getObjectElement(HttpServletRequest request,Object objectForm,FieldElement fieldElement){
		String roleId = FormControl.getFormRoleId(request);
		ApplicationGroupDataM applicationGroup = ((ApplicationGroupDataM)objectForm);
		ArrayList<CompareDataM> compareList = new ArrayList<CompareDataM>();	
		ArrayList<String> products = applicationGroup.getProducts();
		PersonalInfoDataM personalInfoM = PersonalInfoUtil.getApplicationTypePersonalInfo(applicationGroup);
		if(!Util.empty(products)){
			for(String product :products){
				ApplicationDataM applicationM = applicationGroup.filterApplicationRelationLifeCycle(personalInfoM.getPersonalId(), product).get(0);
				if(!Util.empty(applicationM)){
					String productRefId = CompareSensitiveFieldUtil.getProductRefId(personalInfoM, applicationM.getProduct());					
					CompareDataM compareData  = new CompareDataM();
					compareData.setApplicationGroupId(applicationGroup.getApplicationGroupId());
					compareData.setFieldNameType(getImplementId());
					compareData.setValue(FormatUtil.display(KPLUtil.getKPLLoanDataM(applicationGroup).getRequestTerm()));
					compareData.setRole(roleId);
					compareData.setRefId(productRefId);
					compareData.setRefLevel(CompareDataM.RefLevel.APPLICATION);
					compareData.setSrcOfData(CompareDataM.SoruceOfData.TWO_MAKER);
					compareData.setUniqueId(applicationM.getApplicationRecordId());
					compareData.setUniqueLevel(CompareDataM.UniqueLevel.APPLICATION);
					compareData.setSeq(CompareSensitiveFieldUtil.getCompareFieldSeq(getImplementId()));
					compareData.setConfigData(CompareSensitiveFieldUtil.fieldConfigDataMToJSonString(personalInfoM,product));
					compareData.setFieldName(CompareSensitiveFieldUtil.getFieldName(compareData));
					compareList.add(compareData);
				}
			}
		}
		return compareList;
	}
	@Override
	public String processElement(HttpServletRequest request,Object objectElement) {
		ModuleFormHandler RekeyForm = (ModuleFormHandler)request.getSession().getAttribute(REKEY_FORM);
		ApplicationGroupDataM applicationGroup = (ApplicationGroupDataM)RekeyForm.getObjectForm();		
		CompareDataM compareData = ((CompareDataM)objectElement);
		if(!Util.empty(applicationGroup)) 
		{
			String paramName = CompareSensitiveFieldUtil.getParameterName(compareData);
			logger.debug("paramName>>>"+paramName);
			String term = request.getParameter(paramName);
			LoanDataM loan = KPLUtil.getKPLLoanDataM(applicationGroup);
			loan.setRequestTerm(FormatUtil.toBigDecimal(term,true));
		}		
		return null;
	}	
	@Override
	public HashMap<String,Object> validateElement(HttpServletRequest request, Object objectElement){
		FormErrorUtil formError = new FormErrorUtil();
		return formError.getFormError();
	}	
}
