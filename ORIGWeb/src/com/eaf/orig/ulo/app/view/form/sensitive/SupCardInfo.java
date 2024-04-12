package com.eaf.orig.ulo.app.view.form.sensitive;

import java.util.ArrayList;
import java.util.HashMap;

import javax.servlet.http.HttpServletRequest;

import org.apache.log4j.Logger;

import com.eaf.common.utility.CompareSensitiveUtility;
import com.eaf.core.ulo.common.display.ElementInf;
import com.eaf.core.ulo.common.display.FormatUtil;
import com.eaf.core.ulo.common.display.HtmlUtil;
import com.eaf.core.ulo.common.engine.SQLQueryEngine;
import com.eaf.core.ulo.common.message.LabelUtil;
import com.eaf.core.ulo.common.model.CompareFieldElement;
import com.eaf.core.ulo.common.model.FieldElement;
import com.eaf.core.ulo.common.properties.CacheControl;
import com.eaf.core.ulo.common.properties.FormControl;
import com.eaf.core.ulo.common.properties.SystemConstant;
import com.eaf.core.ulo.common.util.CompareObject;
import com.eaf.core.ulo.common.util.Util;
import com.eaf.orig.ulo.app.view.form.sensitive.util.CompareSensitiveFieldUtil;
import com.eaf.orig.ulo.app.view.form.subform.product.manual.CardInfoUtil;
import com.eaf.orig.ulo.constant.MConstant;
import com.eaf.orig.ulo.formcontrol.view.form.ModuleFormHandler;
import com.eaf.orig.ulo.formcontrol.view.form.ORIGFormHandler;
import com.eaf.orig.ulo.model.app.ApplicationDataM;
import com.eaf.orig.ulo.model.app.ApplicationGroupDataM;
import com.eaf.orig.ulo.model.app.CardDataM;
import com.eaf.orig.ulo.model.app.PersonalInfoDataM;
import com.eaf.orig.ulo.model.compare.CompareDataM;

public class SupCardInfo extends CardInfo implements ElementInf{
	String PERSONAL_RELATION_APPLICATION_LEVEL = SystemConstant.getConstant("PERSONAL_RELATION_APPLICATION_LEVEL");
	String PERSONAL_TYPE_SUPPLEMENTARY = SystemConstant.getConstant("PERSONAL_TYPE_SUPPLEMENTARY");	
	String PRODUCT_CRADIT_CARD = SystemConstant.getConstant("PRODUCT_CRADIT_CARD");
	String HTML_REPLACE_PARAM="&HTML";
	String SUFFIX_NAME_CARD_TYPE ="_CARD_TYPE";
	String SUFFIX_NAME_CARD_LEVEL ="_CARD_LEVEL";
	String PRODUCT_TEXT = "PRODUCT";
	private static transient Logger logger = Logger.getLogger(SupCardInfo.class);
	String FIELD_NAME_TYPE ="SUP_CARD_INFO";
//	@Override
//	public String getElement(HttpServletRequest request,Object objectElement) {	
//		ModuleFormHandler RekeyForm = (ModuleFormHandler)request.getSession().getAttribute(REKEY_FORM);
//		ApplicationGroupDataM applicationGroup = (ApplicationGroupDataM)RekeyForm.getObjectForm();	
//        if(objectElement instanceof CompareFieldElement){
//        	CompareFieldElement compareFieldElement = (CompareFieldElement)objectElement;
//        	String warningMessage = (String)compareFieldElement.getObjectElement();
//        	return getElementHtml(request,compareFieldElement.getCompareDatas(),applicationGroup,compareFieldElement.isDisplayMessage(),warningMessage);
//        }else{
//        	CompareDataM compareData = ((CompareDataM)objectElement);
//        	ArrayList<CompareDataM> compareDatas = new ArrayList<CompareDataM>();
//        	compareDatas.add(compareData);
//        	return getElementHtml(request,compareDatas , applicationGroup, false, "");
//        }
//	}
	
//	private String getElementHtml(HttpServletRequest request,ArrayList<CompareDataM> compareDatas, ApplicationGroupDataM applicationGroup,boolean isShowMessage,String warningMessage){
//		StringBuilder SECTION_HTML = new StringBuilder();
//		SECTION_HTML.append("<div class='col-xs-12'>")
//			.append("<table class='table table-striped' style='margin-top:12px;'>")
//			.append(HTML_REPLACE_PARAM);
//		SECTION_HTML.append("</table></div>");
//		
//		if(isShowMessage){
//			StringBuilder htmlMessage = new StringBuilder();
//			htmlMessage.append("<div class='col-sm-12'>")
//						.append("<div class='form-group'>")
//						.append(HtmlUtil.getFieldLabel(request, "PRODUCT_NAME","control-label col-sm-4 col-md-5"))
//						.append("<div class='col-sm-8 col-md-7' style='font-weight: bold;'>")						
//						.append(LabelUtil.getText(request,"REKEY_CC_SUP"))						
//						.append("</div>")
//						.append("</div>")
//						.append("</div>");		 
//			htmlMessage.append("<div class='clearfix'></div>");
//			htmlMessage.append("<div class='col-sm-12'>")
//						.append("<div class='form-group'>")
//						.append( "<div class='col-sm-4 col-md-5 control-label'></div>")
//						.append("<div class='col-sm-8 col-md-7'>")
//						.append("<div class='rekey-warning alert alert-warning alert-dismissible fade in' role='alert'>")
//						.append("<div>"+warningMessage+"</div>")
//						.append("</div></div>")
//						.append("</div></div>")
//						.append("<div class='clearfix'></div>");
//			return SECTION_HTML.toString().replace(HTML_REPLACE_PARAM, getRowSectionHtml(htmlMessage.toString()));
//		}else{
//			if(!Util.empty(compareDatas)){	
//				StringBuilder HTML = new StringBuilder();
//				String productTypeDesc = "";
//				for(CompareDataM compareData : compareDatas ){
//					StringBuilder htmlText = new StringBuilder();
//					ApplicationDataM applicationM = CompareSensitiveFieldUtil.getApplicationByUniqueId(applicationGroup, compareData);
//					if(applicationM == null) {
//						applicationM = new ApplicationDataM();
//					}
//					PersonalInfoDataM personalInfoM = applicationGroup.getPersonalInfoByRelation(applicationM.getApplicationRecordId());
//					if(null == personalInfoM){
//						personalInfoM = new PersonalInfoDataM();
//					}
//					String applicationType = applicationGroup.getApplicationType();
//					logger.debug("applicationType : "+applicationType);
//					String product = CompareSensitiveFieldUtil.getProductByRefId(compareData);
//					logger.debug("product >> "+product);
//					String cardCode = CompareSensitiveFieldUtil.getProductTypeByRefId(compareData);
//					CardDataM cardM = applicationM.getCard();
//					logger.debug("cardM : "+cardM);			
//					if(null != cardM){
//						String cardTypeId = cardM.getCardType();
//						logger.debug("cardTypeId : "+cardTypeId);
//						HashMap<String, Object> CardInfoList = CardInfoUtil.getCardInfo(cardTypeId);
//						productTypeDesc = LabelUtil.getText(request,"REKEY_CC_SUP")+"-"+CacheControl.getName(FIELD_ID_CARD_TYPE, SQLQueryEngine.display(CardInfoList, "CARD_CODE"), "DISPLAY_NAME");
//					}
//					
//					htmlText.append("<div class='col-sm-12' style='display:none'>")
//							.append("<div class='form-group'>")
//							.append(HtmlUtil.getFieldLabel(request, "PRODUCT_NAME","control-label col-sm-4 col-md-5"))
//							.append("<div class='col-sm-8 col-md-7' style='font-weight: bold;'>")						
//							.append(productTypeDesc)						
//							.append("</div>")
//							.append("</div>")
//							.append("</div>");		 
//					htmlText.append("<div class='clearfix'></div>");		
//					String oldCardTypeId = compareData.getOldValue();
//					logger.debug("oldCardTypeId >> "+oldCardTypeId);			
//								
//					HashMap<String, Object> oldCardInfo = CardInfoUtil.getCardInfo(oldCardTypeId);
//					String oldCardType = SQLQueryEngine.display(oldCardInfo, "CARD_CODE");
//					String oldCardLevel = SQLQueryEngine.display(oldCardInfo, "CARD_LEVEL");
//
//					
////					CardDataM cardM = applicationM.getCard();
//					String cardTypeId = cardM.getCardType();	
//					logger.debug("cardTypeId >> "+cardTypeId);
//					HashMap<String, Object> CardInfoList = CardInfoUtil.getCardInfo(cardTypeId);
//					String cardType = SQLQueryEngine.display(CardInfoList, "CARD_CODE");
//					String cardLevel = SQLQueryEngine.display(CardInfoList, "CARD_LEVEL");
//					logger.debug("cardType >> "+cardType);
//					logger.debug("cardLevel >> "+cardLevel);
//					
//					String cardTypeDisplayMode = HtmlUtil.VIEW;
//					String cardLevelDisplayMode = HtmlUtil.VIEW;
//					String cardTypeDisplayValue = cardType;
//					String cardLevelDisplayValue = cardLevel;			
//					if(Util.empty(oldCardType) || Util.empty(cardType) || !oldCardType.equals(cardType)) {
//						cardTypeDisplayMode = HtmlUtil.EDIT;
//						cardLevelDisplayMode = HtmlUtil.EDIT;
//						cardTypeDisplayValue = "";
//						cardLevelDisplayValue = "";
//					}else{
//						if(!Util.empty(oldCardType) && !Util.empty(cardType) && oldCardType.equals(cardType)) {
//							if(Util.empty(oldCardLevel) || Util.empty(cardLevel) || !oldCardLevel.equals(cardLevel)) {
//								cardLevelDisplayMode = HtmlUtil.EDIT;
//								cardLevelDisplayValue = "";
//							}
//						}
//					}
//					
//					logger.debug("cardTypeDisplayValue : "+cardTypeDisplayValue);
//					logger.debug("cardLevelDisplayValue : "+cardLevelDisplayValue);
//					
//					String cardTypeListBoxId = "SUB_CARD_TYPE_LISTBOX";
//					String cardLevelListBoxId = CARD_LEVEL_LISTBOX;
//					String cardTypeLabel = "CARD_TYPE_SUP";
//					String cardLevelLabel = "CARD_LEVEL_SUP";
//					String cacheID = "FIELD_ID_CARD_TYPE";
//
//					String businessClassId = applicationM.getBusinessClassId();	
//					logger.debug("businessClassId >> "+businessClassId);		
//					String cardCodeRefId = CompareSensitiveFieldUtil.getBusClassRefId(personalInfoM, product, cardCode);
//					logger.debug("cardCodeRefId >> "+cardCodeRefId);
//					String TEXT_LABEL =LabelUtil.getText(request, cardTypeLabel)+"/"+LabelUtil.getText(request, cardLevelLabel);
//					PersonalInfoDataM personalInfo = applicationGroup.getPersonalInfoByRelation(applicationM.getApplicationRecordId());
//					
//					String SUFFIX_NAME_CARD_TYPE ="_CARD_TYPE";
//					String SUFFIX_NAME_CARD_LEVEL ="_CARD_LEVEL";
//					String elementId = FormatUtil.getProductElementId(personalInfo, PRODUCT_CRADIT_CARD);
//					String tagElementCardType = HtmlUtil.elementTagId(PRODUCT_TEXT+SUFFIX_NAME_CARD_TYPE,elementId);
//					String tagElementCardLevel = HtmlUtil.elementTagId(PRODUCT_TEXT+SUFFIX_NAME_CARD_LEVEL,elementId);
//					htmlText.append("<div class='col-sm-12'>")
//							.append("  <div class='form-group'>")
//							.append(HtmlUtil.geCustomFieldLabel(TEXT_LABEL, "col-sm-4 col-md-5 control-label"));
//					htmlText.append(HtmlUtil.dropdown(CompareSensitiveFieldUtil.getElementName(compareData,SUFFIX_NAME_CARD_TYPE), CompareSensitiveFieldUtil.getElementSuffix(compareData), cardTypeListBoxId, cardTypeDisplayValue, "", cacheID,businessClassId, 
//							cardTypeDisplayMode,tagElementCardType, "col-sm-4 col-md-4", request));
//					htmlText.append(HtmlUtil.dropdown(CompareSensitiveFieldUtil.getElementName(compareData,SUFFIX_NAME_CARD_LEVEL), CompareSensitiveFieldUtil.getElementSuffix(compareData), cardLevelListBoxId, cardLevelDisplayValue, "", FIELD_ID_CARD_LEVEL,
//							cardTypeDisplayValue, cardLevelDisplayMode,tagElementCardLevel, "col-sm-4 col-md-3",false,request));
//					htmlText.append("</div></div>");
//					htmlText.append("<div class='clearfix'></div>");	
//					HTML.append(getRowSectionHtml(htmlText.toString()));
//					
//				}
//				return SECTION_HTML.toString().replace(HTML_REPLACE_PARAM, HTML.toString());
//			}
//		}
//		return "";
//	}
//	
//	private String getRowSectionHtml(String html){
//		StringBuilder  SECTION_HTML =  new  StringBuilder();
//		SECTION_HTML.append("<tr>")
//					.append("<td class='rekey-td-boder'>")
//					.append("<div class='row'>")
//					.append(html)
//					.append("</div>")
//					.append("</td>")
//					.append("</tr>");
//		SECTION_HTML.append("<div class='clearfix'></div>");
//		return SECTION_HTML.toString();
//		 
//	}
	
	
	@Override
	public <T>Object getObjectElement(HttpServletRequest request,Object objectForm,FieldElement fieldElement){
		String roleId = FormControl.getFormRoleId(request);
		ApplicationGroupDataM applicationGroup = ((ApplicationGroupDataM)objectForm);		
		ArrayList<PersonalInfoDataM> personalInfos = applicationGroup.getPersonalInfos(PERSONAL_TYPE_SUPPLEMENTARY);
		ArrayList<CompareDataM> compareList = new ArrayList<CompareDataM>();
		if(!Util.empty(personalInfos)) {			
			for(PersonalInfoDataM personalInfo: personalInfos) {
				String personalId = personalInfo.getPersonalId();
				logger.debug("personalId : "+personalId);
				ArrayList<ApplicationDataM> applications = applicationGroup.filterApplicationRelationLifeCycle(personalId);
				logger.debug("applications : "+applications);
				if(!Util.empty(applications)) {
					for(ApplicationDataM application : applications) {
						CardDataM cardM = application.getCard();
						if(!Util.empty(cardM)) {
							HashMap<String, Object> CardInfoList = CardInfoUtil.getCardInfo(cardM.getCardType());
							String cardCode = SQLQueryEngine.display(CardInfoList, "CARD_CODE");
							String cardCodeRefId = CompareSensitiveFieldUtil.getCardCodeRefId(personalInfo, application.getProduct(), cardCode);
							String cardTypeRefId = CompareSensitiveFieldUtil.getCardTypeRefId(personalInfo, application.getProduct(), cardM.getCardType());
							CompareDataM compareData  = new CompareDataM();
							compareData.setApplicationGroupId(applicationGroup.getApplicationGroupId());
							compareData.setFieldNameType(getImplementId());
							compareData.setValue(cardM.getCardType());
							compareData.setRole(roleId);
							compareData.setRefId(cardCodeRefId);
							compareData.setRefLevel(CompareDataM.RefLevel.CARD);
							compareData.setCurrentRefId(cardTypeRefId);
							compareData.setSrcOfData(CompareDataM.SoruceOfData.TWO_MAKER);
							compareData.setUniqueId(application.getApplicationRecordId());
							compareData.setUniqueLevel(CompareDataM.UniqueLevel.APPLICATION);
							compareData.setSeq(CompareSensitiveFieldUtil.getCompareFieldSeq(getImplementId()));
							compareData.setConfigData(CompareSensitiveFieldUtil.fieldConfigDataMToJSonString(personalInfo,application.getProduct()));
							compareData.setFieldName(CompareSensitiveFieldUtil.getFieldName(compareData));
							compareList.add(compareData);
						}
					}
				}
			}
		}
		CompareSensitiveFieldUtil.setNoCurrentProduct(applicationGroup,compareList,FIELD_NAME_TYPE);
		return compareList;
	}
	
	@Override
	public String processElement(HttpServletRequest request,Object objectElement) {
		logger.debug("CardInfo.processElement..");
		ModuleFormHandler RekeyForm = (ModuleFormHandler)request.getSession().getAttribute(REKEY_FORM);
		ApplicationGroupDataM applicationGroup = (ApplicationGroupDataM)RekeyForm.getObjectForm();
		CompareDataM compareData = ((CompareDataM)objectElement);
//		String refID = compareData.getRefId();
//		String product = CompareSensitiveFieldUtil.getProductByRefId(compareData);
		ApplicationDataM application = CompareSensitiveFieldUtil.getApplicationByUniqueId(applicationGroup, compareData);
		if(application == null) {
			application = new ApplicationDataM();
		}
		PersonalInfoDataM personalInfoM = applicationGroup.getPersonalInfoByRelation(application.getApplicationRecordId());
		if(null == personalInfoM){
			personalInfoM = new PersonalInfoDataM();
		}
		if(!Util.empty(application)){
			String applicationType = applicationGroup.getApplicationType();
			logger.debug("applicationType : "+applicationType);
			CardDataM cardM = application.getCard();
			if(Util.empty(cardM)) {
				cardM = new CardDataM();
			}
			String cardTypeElementValue = request.getParameter(CompareSensitiveFieldUtil.getParameterName(compareData, SUFFIX_NAME_CARD_TYPE));
			String cardLevel = request.getParameter(CompareSensitiveFieldUtil.getParameterName(compareData, SUFFIX_NAME_CARD_LEVEL));
			logger.debug("cardTypeElementValue : "+cardTypeElementValue);
			logger.debug("cardLevel : "+cardLevel);
			HashMap<String,Object> cardInfo = CardInfoUtil.getCardInfo(cardTypeElementValue,cardLevel);
			String CARD_TYPE_ID = SQLQueryEngine.display(cardInfo,"CARD_TYPE_ID");
			logger.debug("CARD_TYPE_ID : "+CARD_TYPE_ID);
			if(!Util.empty(CARD_TYPE_ID)){
				cardM.setCardType(CARD_TYPE_ID);
			}
		} 
		return null;
	}
	
	@Override
	public ArrayList<CompareFieldElement> compareFieldElement(HttpServletRequest request, Object objectForm) {
		ArrayList<CompareFieldElement> compareFieldElementList = new ArrayList<CompareFieldElement>();
		ApplicationGroupDataM applicationGroup = ORIGFormHandler.getObjectForm(request);	
		ArrayList<CompareDataM> currCompareDataMList = (ArrayList<CompareDataM>)objectForm;

		ArrayList<PersonalInfoDataM> personalInfoList = FormControl.getOrigObjectForm(request).getPersonalInfos(PERSONAL_TYPE_SUPPLEMENTARY);
		if(!Util.empty(personalInfoList) && Util.empty(applicationGroup.getProductElementFlag())){
			String compareFlag = "";
			if(currCompareDataMList.size()>0){
				compareFlag=currCompareDataMList.get(0).getCompareFlag();
			}
			if(!MConstant.FLAG.SUBMIT.equals(compareFlag)){
				for(PersonalInfoDataM personalInfo : personalInfoList){
					ArrayList<CompareDataM> prevCompareDataMList =CompareSensitiveFieldUtil.getPrevCompareDataMByPersonalInfo(applicationGroup,FIELD_NAME_TYPE,personalInfo);
					ArrayList<CompareDataM> currentFilterCompareDataMList = CompareSensitiveFieldUtil.filterCompareDataByPersonalInfo(currCompareDataMList,FIELD_NAME_TYPE, personalInfo);
					
					if(!Util.empty(currentFilterCompareDataMList)){						
//						compareFieldElementList.addAll(CompareObject.compareProductFieldElement(currentFilterCompareDataMList,prevCompareDataMList,FIELD_NAME_TYPE,personalInfo,true));
						
						ORIGFormHandler ORIGForm = ORIGFormHandler.getForm(request);
						HashMap<String, ArrayList<CompareDataM>> currentCompareFields = CompareSensitiveUtility.getCompareDataFieldNameTypes(applicationGroup,ORIGForm.getSubForm(),request);
						ArrayList<CompareDataM> compareDataMList = new ArrayList<CompareDataM>();
						ArrayList<String> keySupElementField = new ArrayList<String>();
						keySupElementField = SystemConstant.getArrayListConstant("FIELD_ELEMENT_REKEY_SUPPLEMENTARY_APPLICATION");
						logger.debug("keySupElementField >>"+keySupElementField);
						ArrayList<String> uniqIdListData = CompareObject.getUniqIdCompareSuplementCCElementField(currentCompareFields,keySupElementField,applicationGroup,personalInfo);
						logger.debug("uniqIdListData >>"+uniqIdListData);
						compareFieldElementList.addAll(CompareObject.compareProductFieldElementCC(uniqIdListData,currentFilterCompareDataMList,prevCompareDataMList,FIELD_NAME_TYPE,personalInfo,true));
					
					}
									
					if(prevCompareDataMList.size()>currentFilterCompareDataMList.size()){
						ArrayList<CompareDataM> dummyCompareDatas = new ArrayList<CompareDataM>();
						CompareSensitiveFieldUtil.setNoCurrentProduct(applicationGroup,dummyCompareDatas,FIELD_NAME_TYPE);
						compareFieldElementList.addAll(CompareObject.setNotEqualProductCompareFieldElement(request,dummyCompareDatas,FIELD_NAME_TYPE,personalInfo));
					}
				}
			}
		}
		logger.debug("compareFieldElementList.size()>>>"+compareFieldElementList.size());
		return compareFieldElementList;
	}
}
