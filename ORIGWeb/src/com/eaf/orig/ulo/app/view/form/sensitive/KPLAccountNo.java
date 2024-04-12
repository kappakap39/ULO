package com.eaf.orig.ulo.app.view.form.sensitive;

import java.util.ArrayList;
import java.util.HashMap;

import javax.servlet.http.HttpServletRequest;

import org.apache.log4j.Logger;

import com.eaf.core.ulo.common.display.ElementHelper;
import com.eaf.core.ulo.common.display.ElementInf;
import com.eaf.core.ulo.common.display.FormatUtil;
import com.eaf.core.ulo.common.display.HtmlUtil;
import com.eaf.core.ulo.common.model.CompareFieldElement;
import com.eaf.core.ulo.common.model.FieldElement;
import com.eaf.core.ulo.common.properties.FormControl;
import com.eaf.core.ulo.common.properties.SystemConstant;
import com.eaf.core.ulo.common.util.CompareObject;
import com.eaf.core.ulo.common.util.FormErrorUtil;
import com.eaf.core.ulo.common.util.Util;
import com.eaf.orig.shared.constant.OrigConstant;
import com.eaf.orig.ulo.app.util.GenerateUnique;
import com.eaf.orig.ulo.app.view.form.sensitive.util.CompareSensitiveFieldUtil;
import com.eaf.orig.ulo.constant.MConstant;
import com.eaf.orig.ulo.formcontrol.view.form.ModuleFormHandler;
import com.eaf.orig.ulo.formcontrol.view.form.ORIGFormHandler;
import com.eaf.orig.ulo.model.app.ApplicationDataM;
import com.eaf.orig.ulo.model.app.ApplicationGroupDataM;
import com.eaf.orig.ulo.model.app.CashTransferDataM;
import com.eaf.orig.ulo.model.app.PaymentMethodDataM;
import com.eaf.orig.ulo.model.app.PersonalInfoDataM;
import com.eaf.orig.ulo.model.compare.CompareDataM;

public class KPLAccountNo extends ElementHelper implements ElementInf{
	String CALL_FOR_CASH = SystemConstant.getConstant("CALL_FOR_CASH");
	String PRODUCT_K_PERSONAL_LOAN = SystemConstant.getConstant("PRODUCT_K_PERSONAL_LOAN");
	String PERSONAL_RELATION_APPLICATION_LEVEL = SystemConstant.getConstant("PERSONAL_RELATION_APPLICATION_LEVEL");
	String PERSONAL_TYPE = SystemConstant.getConstant("PERSONAL_TYPE_APPLICANT");
	String REKEY_FORM = SystemConstant.getConstant("REKEY_FORM");
	private static transient Logger logger = Logger.getLogger(CallForCashAccountNo.class);
	@Override
	public String getElement(HttpServletRequest request,Object objectElement) {		
		ModuleFormHandler RekeyForm = (ModuleFormHandler)request.getSession().getAttribute(REKEY_FORM);
		ApplicationGroupDataM applicationGroup = (ApplicationGroupDataM)RekeyForm.getObjectForm();
		CompareDataM compareData = (CompareDataM)objectElement;
		ApplicationDataM applicationInfo = CompareSensitiveFieldUtil.getApplicationByUniqueId(applicationGroup, compareData);
		if(null == applicationInfo){
			applicationInfo = new ApplicationDataM();
		}
		PersonalInfoDataM personalInfoM = applicationGroup.getPersonalInfoByRelation(applicationInfo.getApplicationRecordId());
		if(null == personalInfoM){
			personalInfoM = new PersonalInfoDataM();
		}
		String elementId = FormatUtil.getProductElementId(personalInfoM,PRODUCT_K_PERSONAL_LOAN);
		String tagId = HtmlUtil.elementTagId(compareData.getFieldNameType(),elementId);
		String displayMode = HtmlUtil.EDIT;			
//		ApplicationDataM application = applicationGroup.filterApplicationProductLifeCycle(PRODUCT_K_EXPRESS_CASH);
		if(!Util.empty(applicationInfo)) {
			StringBuffer HTML = new StringBuffer();
			HTML.append("<div class='col-sm-12'>")
				.append("<div class='form-group'>")
				.append(HtmlUtil.getFieldLabel(request, "ACCOUNT_NO", "col-sm-4 col-md-5 control-label"))
				.append(HtmlUtil.textBoxAccountNo(CompareSensitiveFieldUtil.getElementName(compareData),CompareSensitiveFieldUtil.getElementSuffix(compareData),"","",displayMode, 
					"","col-sm-8 col-md-7",request))
				.append("</div></div>")
				.append("<div class='col-sm-12'>")
				.append("<div class='form-group'>")
				.append(HtmlUtil.getFieldLabel(request, "CALL_FOR_CASH_ACCT_NAME", "col-sm-4 col-md-5 control-label"))
				.append(HtmlUtil.hidden("PRODUCT_NAME_"+CompareSensitiveFieldUtil.getParameterName(compareData),PRODUCT_K_PERSONAL_LOAN))
				.append("<div class='col-sm-8 col-md-7' id='ACCOUNT_NAME"+CompareSensitiveFieldUtil.getParameterName(compareData)+"'></div>")
				.append("</div>")
				.append("</div>")
			.append("<div class='clearfix'></div>");			
/*			.append(HtmlUtil.textBoxAccountNo("CALL_FOR_CASH_ACCOUNT_NO", SUFFIX_CALL_FOR_CASH,"","",displayMode, 
					tagId,"col-sm-8 col-md-7",request))
					.append("</div></div>")
					.append("<div class='clearfix'></div>");*/			
			return HTML.toString();
		}
		return null;
	}
	@Override
	public <T>Object getObjectElement(HttpServletRequest request,Object objectForm,FieldElement fieldElement){
		String roleId = FormControl.getFormRoleId(request);
		String elementId = getImplementId();
		ApplicationGroupDataM applicationGroup = ((ApplicationGroupDataM)objectForm);
		ApplicationDataM application = applicationGroup.filterApplicationProductLifeCycle(PRODUCT_K_PERSONAL_LOAN);
		ArrayList<CompareDataM> compareList = new ArrayList<CompareDataM>();
		if(!Util.empty(application)) {
			PaymentMethodDataM paymentMethod = applicationGroup.getPaymentMethodLifeCycleByPersonalId(fieldElement.getElementGroupId(),PRODUCT_K_PERSONAL_LOAN); 
				PersonalInfoDataM personalInfoM = applicationGroup.getPersonalInfoByRelation(application.getApplicationRecordId());
				CompareDataM compareData  = new CompareDataM();
				compareData.setApplicationGroupId(applicationGroup.getApplicationGroupId());
				compareData.setFieldNameType(elementId);
				if(null != paymentMethod){
					compareData.setValue(paymentMethod.getAccountNo());
				}
				compareData.setRole(roleId);
				compareData.setRefId(CompareSensitiveFieldUtil.getProductRefId(personalInfoM,application.getProduct()));
				compareData.setRefLevel(CompareDataM.RefLevel.APPLICATION);
				compareData.setSrcOfData(CompareDataM.SoruceOfData.TWO_MAKER);
				compareData.setUniqueId(application.getApplicationRecordId());
				compareData.setUniqueLevel(CompareDataM.UniqueLevel.APPLICATION);				
				compareData.setSeq(CompareSensitiveFieldUtil.getCompareFieldSeq(elementId));
				compareData.setConfigData(CompareSensitiveFieldUtil.fieldConfigDataMToJSonString(personalInfoM,application.getProduct()));
				compareData.setFieldName(CompareSensitiveFieldUtil.getFieldName(compareData));
				compareList.add(compareData);
		}
		return compareList;
	}
	@Override
	public String processElement(HttpServletRequest request,Object objectElement) {
		ModuleFormHandler RekeyForm = (ModuleFormHandler)request.getSession().getAttribute(REKEY_FORM);
		ApplicationGroupDataM applicationGroup = (ApplicationGroupDataM)RekeyForm.getObjectForm();
		CompareDataM compareData = (CompareDataM)objectElement;
		ApplicationDataM application = applicationGroup.filterApplicationProductLifeCycle(PRODUCT_K_PERSONAL_LOAN);
		PersonalInfoDataM personalInfo = applicationGroup.getPersonalInfoByRelation(application.getApplicationRecordId());
		if(!Util.empty(application)){
		PaymentMethodDataM paymentMethod = applicationGroup.getPaymentMethodLifeCycleByPersonalId(personalInfo.getPersonalId(),PRODUCT_K_PERSONAL_LOAN); 
			if(!Util.empty(paymentMethod)) {
				String paramName = CompareSensitiveFieldUtil.getParameterName(compareData);
				logger.debug("paramName : "+paramName);
				String acountNo = request.getParameter(paramName);
				acountNo = FormatUtil.removeDash(acountNo);
				logger.debug("transferAcct : "+acountNo);
				paymentMethod.setAccountNo(acountNo);
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
		CompareDataM compareData = (CompareDataM)objectElement;
		logger.debug("compareData : "+compareData);
		ApplicationDataM application = applicationGroup.filterApplicationProductLifeCycle(PRODUCT_K_EXPRESS_CASH);
		logger.debug("application : "+application);
		if(null != application){
			CashTransferDataM callForCash = application.getCashTransfer(CALL_FOR_CASH);
			logger.debug("callForCash : "+callForCash);
			logger.debug("callForCash.getTransferAccount() : "+callForCash.getTransferAccount());
			if(!Util.empty(callForCash) && Util.empty(callForCash.getTransferAccount())) {
				formError.error(compareData.getFieldName(),PREFIX_ERROR+LabelUtil.getText(request,"CALL_FOR_CASH_ACCT_NO"));
			}
		}*/
		return formError.getFormError();
	}	
	@Override
	public ArrayList<CompareFieldElement> compareFieldElement(HttpServletRequest request, Object objectForm) {
		ArrayList<CompareFieldElement> compareFieldElementList = new ArrayList<CompareFieldElement>();
		ApplicationGroupDataM applicationGroup = ORIGFormHandler.getObjectForm(request);	
		ArrayList<CompareDataM> currCompareDataMList = (ArrayList<CompareDataM>)objectForm;
		if(!Util.empty(currCompareDataMList)){
			for(CompareDataM currentCompareData : currCompareDataMList){
				String currentFieldName = currentCompareData.getFieldName();
				String currentFieldNameType = currentCompareData.getFieldNameType();
				logger.debug("currentFieldName>>>"+currentFieldName);
				logger.debug("currentFieldNameType>>>"+currentFieldNameType);
				CompareDataM prevCompareData  = CompareSensitiveFieldUtil.prevCompareDataMRefByProduct(applicationGroup, currentCompareData);
				if(!Util.empty(prevCompareData)){
					boolean isSame = CompareObject.compare(currentCompareData.getValue(), prevCompareData.getValue(),currentCompareData.getCompareFlag());
					logger.debug("isSame>>>>"+isSame);
					if(!isSame) {
						currentCompareData.setCompareFlag(MConstant.FLAG.YES);
						currentCompareData.setOldValue(prevCompareData.getValue());
						
						CompareFieldElement compareFieldElement = new CompareFieldElement();
						compareFieldElement.addCompareDatas(currentCompareData);
						compareFieldElement.setCompareFieldType(CompareSensitiveFieldUtil.getCompareElementFieldType(currentCompareData));
						compareFieldElement.setImplementId(currentFieldNameType);
						compareFieldElementList.add(compareFieldElement);
					}
				}
			}
		}		
		return compareFieldElementList;
	}
}
