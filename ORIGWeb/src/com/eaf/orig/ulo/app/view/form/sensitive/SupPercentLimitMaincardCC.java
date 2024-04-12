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
import com.eaf.orig.ulo.model.app.PersonalInfoDataM;
import com.eaf.orig.ulo.model.compare.CompareDataM;

public class SupPercentLimitMaincardCC extends ElementHelper implements ElementInf{
	private static transient Logger logger = Logger.getLogger(SupPercentLimitMaincardCC.class);	
	String REKEY_FORM = SystemConstant.getConstant("REKEY_FORM");
	String PERSONAL_TYPE_SUPPLEMENTARY = SystemConstant.getConstant("PERSONAL_TYPE_SUPPLEMENTARY");
	String FIELD_ID_PERCENT_LIMIT_MAINCARD = SystemConstant.getConstant("FIELD_ID_PERCENT_LIMIT_MAINCARD");
	String PERSONAL_RELATION_APPLICATION_LEVEL = SystemConstant.getConstant("PERSONAL_RELATION_APPLICATION_LEVEL");
	String PRODUCT_CRADIT_CARD = SystemConstant.getConstant("PRODUCT_CRADIT_CARD");
	String DECISION_FINAL_DECISION_CANCEL = SystemConstant.getConstant("DECISION_FINAL_DECISION_CANCEL");
	String DECISION_FINAL_DECISION_REJECT = SystemConstant.getConstant("DECISION_FINAL_DECISION_REJECT");
	
	@Override
	public String getElement(HttpServletRequest request,Object objectElement) {	
		logger.debug("CLASS_NAME >> com.eaf.orig.ulo.app.view.form.sensitive.SubPercentLimitMaincardCC");
		ModuleFormHandler RekeyForm = (ModuleFormHandler)request.getSession().getAttribute(REKEY_FORM);
		ApplicationGroupDataM applicationGroup = (ApplicationGroupDataM)RekeyForm.getObjectForm();		
		CompareDataM compareData = ((CompareDataM)objectElement);
		ApplicationDataM applicationM = CompareSensitiveFieldUtil.getApplicationProductTypeByRefId(applicationGroup, compareData);
		if(applicationM == null) {
			applicationM = new ApplicationDataM();
		}
		PersonalInfoDataM personalInfo = applicationGroup.getPersonalInfoApplication(applicationM.getApplicationRecordId(), PERSONAL_RELATION_APPLICATION_LEVEL);
		String element = FormatUtil.getProductElementId(personalInfo, PRODUCT_CRADIT_CARD);
		String tagId = HtmlUtil.elementTagId(compareData.getFieldNameType(), element);
		String displayMode = HtmlUtil.EDIT;		
		StringBuffer HTML = new StringBuffer();
		HTML.append("<div class='col-sm-12'>")
			.append("<div class='form-group'>")
			.append(HtmlUtil.getFieldLabel(request, compareData.getFieldNameType(), "col-sm-4 col-md-5 control-label"))
			.append(HtmlUtil.dropdown(CompareSensitiveFieldUtil.getElementName(compareData), CompareSensitiveFieldUtil.getElementSuffix(compareData), 
				    compareData.getFieldNameType(), "", "", FIELD_ID_PERCENT_LIMIT_MAINCARD,"ALL_ALL_ALL",displayMode,tagId, "col-sm-8 col-md-7",request))
			.append("</div></div>")
			.append("<div class='clearfix'></div>");
		return HTML.toString();
	}
	@Override
	public <T>Object getObjectElement(HttpServletRequest request,Object objectForm,FieldElement fieldElement){
		String roleId = FormControl.getFormRoleId(request);
		ApplicationGroupDataM applicationGroup = ((ApplicationGroupDataM)objectForm);
		ArrayList<CompareDataM> compareList = new ArrayList<CompareDataM>();	
			PersonalInfoDataM personalInfo = applicationGroup.getPersonalInfo(PERSONAL_TYPE_SUPPLEMENTARY);
			if(!Util.empty(personalInfo)){
			ArrayList<String> applicationIds = applicationGroup.filterPersonalApplicationIdLifeCycle(personalInfo.getPersonalId(),PERSONAL_RELATION_APPLICATION_LEVEL);
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
						compareData.setRefLevel(CompareDataM.UniqueLevel.APPLICATION);
						compareData.setSrcOfData(CompareDataM.SoruceOfData.TWO_MAKER);	
						compareData.setSeq(CompareSensitiveFieldUtil.getCompareFieldSeq(getImplementId()));
						CardDataM cardM = application.getCard();
							if(!Util.empty(cardM) && !Util.empty(cardM.getCardType())) {
								HashMap<String, Object> CardInfoList = CardInfoUtil.getCardInfo(cardM.getCardType());
								String cardCode = SQLQueryEngine.display(CardInfoList, "CARD_CODE");
								String cardCodeRefId = CompareSensitiveFieldUtil.getCardCodeRefId(personalInfo, product, cardCode);
								String cardTypeRefId = CompareSensitiveFieldUtil.getCardTypeRefId(personalInfo, product, cardM.getCardType());
//								compareData.setFieldName(getImplementId()+CompareDataM.MARKER+cardCodeRefId);
								compareData.setValue(cardM.getPercentLimitMaincard());
								compareData.setRefId(cardCodeRefId);
								compareData.setCurrentRefId(cardTypeRefId);	
								compareData.setFieldName(CompareSensitiveFieldUtil.getFieldName(compareData));
							}				
						compareList.add(compareData);
					}
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
		CardDataM cardM = application.getCard();
		if(!Util.empty(cardM)) {
			String paramName = CompareSensitiveFieldUtil.getParameterName(compareData);
			logger.debug("paramName>>>"+paramName);
			String percentlimitmaincard = request.getParameter(paramName);
			cardM.setPercentLimitMaincard(percentlimitmaincard);
			
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
