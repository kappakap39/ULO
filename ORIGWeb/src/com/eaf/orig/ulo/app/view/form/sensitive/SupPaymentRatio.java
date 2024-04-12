package com.eaf.orig.ulo.app.view.form.sensitive;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashMap;

import javax.servlet.http.HttpServletRequest;

import org.apache.log4j.Logger;

import com.eaf.core.ulo.common.display.ElementHelper;
import com.eaf.core.ulo.common.display.ElementInf;
import com.eaf.core.ulo.common.display.FormatUtil;
import com.eaf.core.ulo.common.display.HtmlUtil;
import com.eaf.core.ulo.common.message.LabelUtil;
import com.eaf.core.ulo.common.model.CompareFieldElement;
import com.eaf.core.ulo.common.model.FieldElement;
import com.eaf.core.ulo.common.properties.FormControl;
import com.eaf.core.ulo.common.properties.SystemConstant;
import com.eaf.core.ulo.common.util.CompareObject;
import com.eaf.core.ulo.common.util.FormErrorUtil;
import com.eaf.core.ulo.common.util.Util;
import com.eaf.orig.ulo.app.view.form.sensitive.util.CompareSensitiveFieldUtil;
import com.eaf.orig.ulo.constant.MConstant;
import com.eaf.orig.ulo.formcontrol.view.form.ModuleFormHandler;
import com.eaf.orig.ulo.formcontrol.view.form.ORIGFormHandler;
import com.eaf.orig.ulo.model.app.ApplicationDataM;
import com.eaf.orig.ulo.model.app.ApplicationGroupDataM;
import com.eaf.orig.ulo.model.app.PaymentMethodDataM;
import com.eaf.orig.ulo.model.app.PersonalInfoDataM;
import com.eaf.orig.ulo.model.compare.CompareDataM;

public class SupPaymentRatio extends ElementHelper implements ElementInf{
	private static transient Logger logger = Logger.getLogger(SupPaymentRatio.class);
	String FIELD_ID_PAYMENT_RATIO = SystemConstant.getConstant("FIELD_ID_PAYMENT_RATIO");	
	String REKEY_FORM = SystemConstant.getConstant("REKEY_FORM");
	String PAYMENT_METHOD_DEPOSIT_ACCOUNT = SystemConstant.getConstant("PAYMENT_METHOD_DEPOSIT_ACCOUNT");
	String PERSONAL_TYPE_SUPPLEMENTARY = SystemConstant.getConstant("PERSONAL_TYPE_SUPPLEMENTARY");	
	String PRODUCT_CRADIT_CARD = SystemConstant.getConstant("PRODUCT_CRADIT_CARD");
	@Override
	public String getElement(HttpServletRequest request,Object objectElement) {
		String displayMode = HtmlUtil.EDIT;		
		ModuleFormHandler RekeyForm = (ModuleFormHandler)request.getSession().getAttribute(REKEY_FORM);
		ApplicationGroupDataM applicationGroup = (ApplicationGroupDataM)RekeyForm.getObjectForm();
		CompareDataM compareData = (CompareDataM)objectElement;
		PaymentMethodDataM paymentMethodDataM = CompareSensitiveFieldUtil.getPaymentMethodByUniqueId(applicationGroup, compareData);
		if(null == paymentMethodDataM){
			paymentMethodDataM = new PaymentMethodDataM();
		}
		
		String paymentMethodId = paymentMethodDataM.getPaymentMethodId();
		ApplicationDataM applicationDataM =applicationGroup.getApplicationByPaymentMethodId(paymentMethodId);
		if(null == applicationDataM){
			applicationDataM = new ApplicationDataM();
		}
		
		PersonalInfoDataM personalInfoM = applicationGroup.getPersonalInfoByRelation(applicationDataM.getApplicationRecordId());
		if(null!=personalInfoM){
			personalInfoM = new PersonalInfoDataM();
		}
		String product = CompareSensitiveFieldUtil.getProductByRefId(compareData);
		String elementId = FormatUtil.getProductElementId(personalInfoM,product);
		String tagId = HtmlUtil.elementTagId(compareData.getFieldNameType(),elementId);	
		StringBuffer HTML = new StringBuffer();
		String labelDesc = LabelUtil.getText(request, "PAYMENT_RATIO")+"("+LabelUtil.getText(request, "PRODUCT_"+product)+")";
		
		HTML.append("<div class='col-sm-12'>")
			.append("<div class='form-group'>")
			.append(HtmlUtil.geCustomFieldLabel(labelDesc, "col-sm-4 col-md-5 control-label"))
			.append(HtmlUtil.dropdown(CompareSensitiveFieldUtil.getElementName(compareData),CompareSensitiveFieldUtil.getElementSuffix(compareData), "PAYMENT_RATIO", "","", FIELD_ID_PAYMENT_RATIO, 
				"ALL_ALL_ALL", displayMode,tagId, "col-sm-8 col-md-7", request))
			.append("</div></div>")
			.append("<div class='clearfix'></div>");		
		return HTML.toString();
	}
	
	@Override
	public <T>Object getObjectElement(HttpServletRequest request,Object objectForm,FieldElement fieldElement){
		String roleId = FormControl.getFormRoleId(request);
		ApplicationGroupDataM applicationGroup = ((ApplicationGroupDataM)objectForm);
		ArrayList<CompareDataM> compareList = new ArrayList<CompareDataM>();
		ArrayList<PersonalInfoDataM> personalInfos = applicationGroup.getPersonalInfos(PERSONAL_TYPE_SUPPLEMENTARY);
		if(!Util.empty(personalInfos)){
			for(PersonalInfoDataM subPersonalInfo : personalInfos){
				PaymentMethodDataM paymentMethod = applicationGroup.getPaymentMethodLifeCycleByPersonalId(subPersonalInfo.getPersonalId());
				if(!Util.empty(paymentMethod)){
					String productRefId = CompareSensitiveFieldUtil.getProductRefId(subPersonalInfo, PRODUCT_CRADIT_CARD);
					CompareDataM compareData  = new CompareDataM();
					compareData.setApplicationGroupId(applicationGroup.getApplicationGroupId());
					compareData.setFieldNameType(getImplementId());
					compareData.setValue(FormatUtil.display(paymentMethod.getPaymentRatio(), true));
					compareData.setRole(roleId);
					compareData.setRefId(productRefId);
					compareData.setCurrentRefId(PRODUCT_CRADIT_CARD);
					compareData.setRefLevel(CompareDataM.RefLevel.PAYMENT_METHOD);
					compareData.setSrcOfData(CompareDataM.SoruceOfData.TWO_MAKER);
					compareData.setUniqueId(paymentMethod.getPaymentMethodId());
					compareData.setUniqueLevel(CompareDataM.UniqueLevel.PAYMENT_METHOD);
					compareData.setSeq(CompareSensitiveFieldUtil.getCompareFieldSeq(getImplementId()));
					compareData.setConfigData(CompareSensitiveFieldUtil.fieldConfigDataMToJSonString(subPersonalInfo,PRODUCT_CRADIT_CARD));
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
		CompareDataM compareData = (CompareDataM)objectElement;
		/*PersonalInfoDataM relatedPersonalInfo = CompareSensitiveFieldUtil.getPersonalInfoByRefId(applicationGroup, compareData);
		ApplicationDataM linkedAppM = CompareSensitiveFieldUtil.getApplicationByRefId(applicationGroup, compareData);
		if(relatedPersonalInfo == null) {
			relatedPersonalInfo = new PersonalInfoDataM();
		}
		if(linkedAppM == null) {
			linkedAppM = new ApplicationDataM();
		}
		PaymentMethodDataM paymentMethod = applicationGroup.getPaymentMethodLifeCycleByPersonalId(relatedPersonalInfo.getPersonalId(), linkedAppM.getProduct());*/
		PaymentMethodDataM paymentMethod = CompareSensitiveFieldUtil.getPaymentMethodByUniqueId(applicationGroup, compareData);
		if(!Util.empty(paymentMethod)) {
			String paramName = CompareSensitiveFieldUtil.getParameterName(compareData);
			logger.debug("paramName>>>"+paramName);
			BigDecimal paymentRatio = FormatUtil.toBigDecimal(request.getParameter(paramName),true);
			paymentMethod.setPaymentRatio(paymentRatio);
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
		PersonalInfoDataM relatedPersonalInfo = CompareSensitiveFieldUtil.getPersonalInfoByRefId(applicationGroup, compareData);
		ApplicationDataM linkedAppM = CompareSensitiveFieldUtil.getApplicationByRefId(applicationGroup, compareData);
		if(relatedPersonalInfo == null) {
			relatedPersonalInfo = new PersonalInfoDataM();
		}
		if(linkedAppM == null) {
			linkedAppM = new ApplicationDataM();
		}
		PaymentMethodDataM paymentMethod = applicationGroup.getPaymentMethodLifeCycleByPersonalId(relatedPersonalInfo.getPersonalId(), linkedAppM.getProduct());
		if(Util.empty(paymentMethod) ||
				(PAYMENT_METHOD_DEPOSIT_ACCOUNT.equals(paymentMethod.getPaymentMethod()) && Util.empty(paymentMethod.getPaymentRatio()))) {
			formError.error(compareData.getFieldName(),PREFIX_ERROR+LabelUtil.getText(request,compareData.getFieldNameType()));
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
