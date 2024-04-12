package com.eaf.orig.ulo.app.view.form.sensitive;

import java.util.ArrayList;
import java.util.HashMap;

import javax.servlet.http.HttpServletRequest;

import org.apache.log4j.Logger;

import com.eaf.core.ulo.common.display.ElementHelper;
import com.eaf.core.ulo.common.display.ElementInf;
import com.eaf.core.ulo.common.display.FormatUtil;
import com.eaf.core.ulo.common.display.HtmlUtil;
import com.eaf.core.ulo.common.engine.SQLQueryEngine;
import com.eaf.core.ulo.common.model.CompareFieldElement;
import com.eaf.core.ulo.common.model.FieldElement;
import com.eaf.core.ulo.common.properties.FormControl;
import com.eaf.core.ulo.common.properties.SystemConstant;
import com.eaf.core.ulo.common.util.CompareObject;
import com.eaf.core.ulo.common.util.Util;
import com.eaf.orig.ulo.app.view.form.sensitive.util.CompareSensitiveFieldUtil;
import com.eaf.orig.ulo.app.view.form.subform.product.manual.CardInfoUtil;
import com.eaf.orig.ulo.constant.MConstant;
import com.eaf.orig.ulo.formcontrol.view.form.ModuleFormHandler;
import com.eaf.orig.ulo.model.app.ApplicationDataM;
import com.eaf.orig.ulo.model.app.ApplicationGroupDataM;
import com.eaf.orig.ulo.model.app.CardDataM;
import com.eaf.orig.ulo.model.app.LoanDataM;
import com.eaf.orig.ulo.model.app.PersonalInfoDataM;
import com.eaf.orig.ulo.model.compare.CompareDataM;

public class SupPercentLimitCC extends ElementHelper implements ElementInf{
	private static transient Logger logger = Logger.getLogger(SupPercentLimitMaincardCC.class);	
	String REKEY_FORM = SystemConstant.getConstant("REKEY_FORM");
	String PERSONAL_TYPE_SUPPLEMENTARY = SystemConstant.getConstant("PERSONAL_TYPE_SUPPLEMENTARY");
	String PERSONAL_RELATION_APPLICATION_LEVEL = SystemConstant.getConstant("PERSONAL_RELATION_APPLICATION_LEVEL");
	String PRODUCT_CRADIT_CARD = SystemConstant.getConstant("PRODUCT_CRADIT_CARD");
	String DECISION_FINAL_DECISION_CANCEL = SystemConstant.getConstant("DECISION_FINAL_DECISION_CANCEL");
	String DECISION_FINAL_DECISION_REJECT = SystemConstant.getConstant("DECISION_FINAL_DECISION_REJECT");
	
	@Override
	public String getElement(HttpServletRequest request,Object objectElement) {	
		logger.debug("CLASS_NAME >> com.eaf.orig.ulo.app.view.form.sensitive.SupPercentLimitCC");
		ModuleFormHandler RekeyForm = (ModuleFormHandler)request.getSession().getAttribute(REKEY_FORM);
		ApplicationGroupDataM applicationGroup = (ApplicationGroupDataM)RekeyForm.getObjectForm();		
		CompareDataM compareData = ((CompareDataM)objectElement);
		ApplicationDataM applicationM = CompareSensitiveFieldUtil.getApplicationProductTypeByRefId(applicationGroup, compareData);
		if(applicationM == null) {
			applicationM = new ApplicationDataM();
		}
		PersonalInfoDataM personalInfo = applicationGroup.getPersonalInfoApplication(applicationM.getApplicationRecordId(), PERSONAL_RELATION_APPLICATION_LEVEL);
		String elementId = FormatUtil.getProductElementId(personalInfo, PRODUCT_CRADIT_CARD);
		String tagId = HtmlUtil.elementTagId(compareData.getFieldNameType(), elementId);
		String displayMode = HtmlUtil.EDIT;		
		StringBuffer HTML = new StringBuffer();
		HTML.append("<div class='col-sm-12'>")
			.append("<div class='form-group'>")
			.append(HtmlUtil.getFieldLabel(request, compareData.getFieldNameType(), "col-sm-4 col-md-5 control-label"))
			.append(HtmlUtil.currencyBox(CompareSensitiveFieldUtil.getElementName(compareData), 
					CompareSensitiveFieldUtil.getElementSuffix(compareData), null, "", true, "", displayMode, tagId, "col-sm-4 col-md-5 control-label", request))    
			.append("</div></div>")
			.append("<div class='clearfix'></div>");
		return HTML.toString();
	}
	@Override
	public <T>Object getObjectElement(HttpServletRequest request,Object objectForm,FieldElement fieldElement){
		String roleId = FormControl.getFormRoleId(request);
		ApplicationGroupDataM applicationGroup = ((ApplicationGroupDataM)objectForm);
		ArrayList<CompareDataM> compareList = new ArrayList<CompareDataM>();			
//			ApplicationDataM application = applicationGroup.getApplicationById(fieldElement.getElementGroupId());
//			PersonalInfoDataM personalInfo = applicationGroup.getPersonalInfoByRelation(application.getApplicationRecordId());
			PersonalInfoDataM personalInfo = applicationGroup.getPersonalInfo(PERSONAL_TYPE_SUPPLEMENTARY);
			ArrayList<String> applicationIds = applicationGroup.filterPersonalApplicationIdLifeCycle(personalInfo.getPersonalId(),PERSONAL_RELATION_APPLICATION_LEVEL);
			logger.debug("personalInfo Type >> "+personalInfo.getPersonalType());
		for(String applicationId:applicationIds){
			ApplicationDataM application = applicationGroup.getApplicationById(applicationId,PRODUCT_CRADIT_CARD);
			if(!Util.empty(application)) {
				String product = application.getProduct();
				CompareDataM compareData  = new CompareDataM();
				compareData.setApplicationGroupId(applicationGroup.getApplicationGroupId());
				compareData.setFieldNameType(getImplementId());
				compareData.setUniqueLevel(CompareDataM.UniqueLevel.CARD_INFORMATION);
				compareData.setUniqueId(application.getApplicationRecordId()); 
				if(DECISION_FINAL_DECISION_CANCEL.equals(application.getFinalAppDecision()) || DECISION_FINAL_DECISION_REJECT.equals(application.getFinalAppDecision())){
					compareData.setConfigData(CompareSensitiveFieldUtil.unCompareFieldConfigDataMToJSonString(applicationGroup,""));
				}else{
					compareData.setConfigData(CompareSensitiveFieldUtil.fieldConfigDataMToJSonString(personalInfo,product));
				}
				compareData.setRole(roleId);
				compareData.setRefLevel(CompareDataM.RefLevel.CARD);
				compareData.setSrcOfData(CompareDataM.SoruceOfData.TWO_MAKER);	
				compareData.setSeq(CompareSensitiveFieldUtil.getCompareFieldSeq(getImplementId()));
				CardDataM cardM = application.getCard();
				LoanDataM loan = application.getLoan();
					if(!Util.empty(cardM) && !Util.empty(cardM.getCardType())&&!Util.empty(loan)) {
						HashMap<String, Object> CardInfoList = CardInfoUtil.getCardInfo(cardM.getCardType());
						String cardCode = SQLQueryEngine.display(CardInfoList, "CARD_CODE");
						String cardCodeRefId = CompareSensitiveFieldUtil.getCardCodeRefId(personalInfo, product, cardCode);
						String cardTypeRefId = CompareSensitiveFieldUtil.getCardTypeRefId(personalInfo, product, cardM.getCardType());
						compareData.setValue(FormatUtil.display(loan.getRequestLoanAmt()));
						compareData.setRefId(cardCodeRefId);
						compareData.setCurrentRefId(cardTypeRefId);		
						compareData.setFieldName(CompareSensitiveFieldUtil.getFieldName(compareData));
					}				
				compareList.add(compareData);
			}
		}
		
		return compareList;
	}	
	@Override
	public String processElement(HttpServletRequest request,Object objectElement){
		ModuleFormHandler RekeyForm = (ModuleFormHandler)request.getSession().getAttribute(REKEY_FORM);
		ApplicationGroupDataM applicationGroup = (ApplicationGroupDataM)RekeyForm.getObjectForm();
		CompareDataM compareData = ((CompareDataM)objectElement);
		ApplicationDataM application = CompareSensitiveFieldUtil.getApplicationProductTypeByRefId(applicationGroup, compareData);
		LoanDataM loan = application.getLoan();
		if(!Util.empty(loan)) {
			String paramName = CompareSensitiveFieldUtil.getParameterName(compareData);
			logger.debug("paramName>>>"+paramName);
			String percentlimitcc = request.getParameter(paramName);
//			cardM.setPercentLimitMaincard(percentlimitmaincard);		
			loan.setRequestLoanAmt(FormatUtil.toBigDecimal(percentlimitcc,true));
		}	

		return null;
	}	
	
	@Override
	public ArrayList<CompareFieldElement> compareFieldElement(HttpServletRequest request, Object objectForm) {
		ArrayList<CompareFieldElement> compareFieldElementList = new ArrayList<CompareFieldElement>();
		ApplicationGroupDataM applicationGroup = FormControl.getOrigObjectForm(request);
		ArrayList<CompareDataM> currCompareDataMList = (ArrayList<CompareDataM>)objectForm;
		if(!Util.empty(currCompareDataMList)){
			for(CompareDataM currentCompareData : currCompareDataMList){
				if(CompareSensitiveFieldUtil.isCompareSensitiveField(currentCompareData.getConfigData())){
					String currentFieldName = currentCompareData.getFieldName();
					String currentFieldNameType = currentCompareData.getFieldNameType();
					logger.debug("currentFieldName>>>"+currentFieldName);
					logger.debug("currentFieldNameType>>>"+currentFieldNameType);
					CompareDataM prevCompareData  = CompareSensitiveFieldUtil.prevCompareDataM(applicationGroup, currentCompareData);
					if(!Util.empty(prevCompareData)){
						boolean isSame = CompareObject.compare(currentCompareData.getValue(), prevCompareData.getValue(),currentCompareData.getCompareFlag());
						logger.debug("isSame>>>>"+isSame);
						if(!isSame) {
							currentCompareData.setOldValue(prevCompareData.getValue());
							currentCompareData.setCompareFlag(MConstant.FLAG.YES);
							CompareFieldElement compareFieldElement = new CompareFieldElement();
							compareFieldElement.addCompareDatas(currentCompareData);
							compareFieldElement.setCompareFieldType(CompareDataM.UniqueLevel.APPLICATION+currentCompareData.getUniqueId());
							compareFieldElement.setImplementId(currentFieldNameType);
							compareFieldElementList.add(compareFieldElement);
						}
					}
				}
			}				
		}		
		return compareFieldElementList;
	}
	
}