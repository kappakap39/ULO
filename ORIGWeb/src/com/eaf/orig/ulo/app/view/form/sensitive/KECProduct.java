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
import com.eaf.core.ulo.common.message.LabelUtil;
import com.eaf.core.ulo.common.model.CompareFieldElement;
import com.eaf.core.ulo.common.model.FieldElement;
import com.eaf.core.ulo.common.properties.CacheControl;
import com.eaf.core.ulo.common.properties.FormControl;
import com.eaf.core.ulo.common.properties.SystemConstant;
import com.eaf.core.ulo.common.util.CompareObject;
import com.eaf.core.ulo.common.util.FormErrorUtil;
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

public class KECProduct extends ElementHelper implements ElementInf{
	static String MAIN = "MAIN";	
	static String SUP = "SUP";	
	String HTML_REPLACE_PARAM="&HTML";
	String PRODUCT_K_EXPRESS_CASH = SystemConstant.getConstant("PRODUCT_K_EXPRESS_CASH");
	String PERSONAL_RELATION_APPLICATION_LEVEL = SystemConstant.getConstant("PERSONAL_RELATION_APPLICATION_LEVEL");
	String PERSONAL_TYPE_APPLICANT = SystemConstant.getConstant("PERSONAL_TYPE_APPLICANT");	
	String CACHE_BUSINESS_CLASS = SystemConstant.getConstant("CACHE_BUSINESS_CLASS");	
	String FIELD_ID_CARD_TYPE = SystemConstant.getConstant("FIELD_ID_CARD_TYPE");	 
	String FIELD_ID_PRODUCT_TYPE = SystemConstant.getConstant("FIELD_ID_PRODUCT_TYPE");	 
	String FIELD_ID_CARD_LEVEL =  SystemConstant.getConstant("FIELD_ID_CARD_LEVEL");
	String CARD_LEVEL_LISTBOX =  SystemConstant.getConstant("CARD_LEVEL_LISTBOX");
	String CARD_TYPE_MAIN_APP_LISTBOX =  SystemConstant.getConstant("CARD_TYPE_MAIN_APP_LISTBOX");
	String CARD_TYPE_REKEY_LISTBOX =  SystemConstant.getConstant("CARD_TYPE_REKEY_LISTBOX");
	String FIELD_ID_PERSONAL_TYPE = SystemConstant.getConstant("FIELD_ID_PERSONAL_TYPE");
	String APPLICATION_TYPE_ADD_SUP = SystemConstant.getConstant("APPLICATION_TYPE_ADD_SUP");
	String REKEY_FORM = SystemConstant.getConstant("REKEY_FORM");
	String SUFFIX_NAME_CARD_TYPE ="_CARD_TYPE";
	String FIELD_NAME_TYPE ="KEC";
	private static transient Logger logger = Logger.getLogger(CardInfo.class);	
	@Override
	public String getElement(HttpServletRequest request,Object objectElement) {	
		ModuleFormHandler RekeyForm = (ModuleFormHandler)request.getSession().getAttribute(REKEY_FORM);
		ApplicationGroupDataM applicationGroup = (ApplicationGroupDataM)RekeyForm.getObjectForm();	
        if(objectElement instanceof CompareFieldElement){
        	CompareFieldElement compareFieldElement = (CompareFieldElement)objectElement;
        	String warningMessage = (String)compareFieldElement.getObjectElement();
        	return getElementHtml(request,compareFieldElement.getCompareDatas(),applicationGroup,compareFieldElement.isDisplayMessage(),warningMessage);
        }else{
        	CompareDataM compareData = ((CompareDataM)objectElement);
        	ArrayList<CompareDataM> compareDatas = new ArrayList<CompareDataM>();
        	compareDatas.add(compareData);
        	return getElementHtml(request, compareDatas, applicationGroup, false, "");
        }

	}
	
	private String getElementHtml(HttpServletRequest request,ArrayList<CompareDataM> compareDataList, ApplicationGroupDataM applicationGroup,boolean isShowMessage,String warningMessage){
		logger.debug("compareData size >> "+compareDataList.size());
		StringBuilder SECTION_HTML = new StringBuilder();
		SECTION_HTML.append("<div class='col-xs-12'>")
			.append("<table class='table table-striped' style='margin-top:12px;'>")
			.append(HTML_REPLACE_PARAM);
		SECTION_HTML.append("</table></div>");
		
		
		if(isShowMessage){
			StringBuilder htmlMessage = new StringBuilder();
			htmlMessage.append("<div class='col-sm-12'>")
						.append("<div class='form-group'>")
						.append(HtmlUtil.getFieldLabel(request, "PRODUCT_NAME","control-label col-sm-4 col-md-5"))
						.append("<div class='col-sm-8 col-md-7' style='font-weight: bold;'>")						
						.append(LabelUtil.getText(request,"PRODUCT_KEC"))						
						.append("</div>")
						.append("</div>")
						.append("</div>");		 
			htmlMessage.append("<div class='clearfix'></div>");
			htmlMessage.append("<div class='col-sm-12'>")
						.append("<div class='form-group'>")
						.append( "<div class='col-sm-4 col-md-5 control-label'></div>")
						.append("<div class='col-sm-8 col-md-7'>")
						.append("<div class='rekey-warning alert alert-warning alert-dismissible fade in' role='alert'>")
						.append("<div>"+warningMessage+"</div>")
						.append("</div></div>")
						.append("</div></div>")
						.append("<div class='clearfix'></div>");
			return SECTION_HTML.toString().replace(HTML_REPLACE_PARAM, getRowSectionHtml(htmlMessage.toString()));
		}else{
			if(!Util.empty(compareDataList)){	
				StringBuilder HTML_APPEND = new StringBuilder();
				for(CompareDataM compareDataM :compareDataList){
					StringBuilder HTML = new StringBuilder();
					ApplicationDataM applicationM = CompareSensitiveFieldUtil.getApplicationByUniqueId(applicationGroup, compareDataM);
					if(applicationM == null) {
						applicationM = new ApplicationDataM();
					}
					PersonalInfoDataM personalInfoM = applicationGroup.getPersonalInfoByRelation(applicationM.getApplicationRecordId());
					if(null == personalInfoM){
						personalInfoM = new PersonalInfoDataM();
					}
					String applicationType = applicationGroup.getApplicationType();
					logger.debug("applicationType : "+applicationType);
					String product = CompareSensitiveFieldUtil.getProductByRefId(compareDataM);
					logger.debug("product >> "+product);
					String productTypeDesc = "";
					CardDataM cardM = applicationM.getCard();
					logger.debug("cardM : "+cardM);			
					if(null != cardM){
						String cardTypeId = cardM.getCardType();
						logger.debug("cardTypeId : "+cardTypeId);
						HashMap<String, Object> CardInfoList = CardInfoUtil.getCardInfo(cardTypeId);
						productTypeDesc = LabelUtil.getText(request,"PRODUCT_KEC")+"/"+CacheControl.getName(FIELD_ID_CARD_TYPE, SQLQueryEngine.display(CardInfoList, "CARD_CODE"), "DISPLAY_NAME");
					}
					logger.debug("productTypeDesc >> "+productTypeDesc);
					
				    HTML.append("<div class='col-sm-12'>")
						.append("<div class='form-group'>")
						.append(HtmlUtil.getFieldLabel(request, "PRODUCT_NAME","control-label col-sm-4 col-md-5"))
						.append("<div class='col-sm-8 col-md-7' style='font-weight: bold;'>"+productTypeDesc+"</div>")
						.append("</div>")
						.append("</div>");		 
				    HTML.append("<div class='clearfix'></div>");

					
					String oldCardTypeId = compareDataM.getOldValue();
					logger.debug("oldCardTypeId >> "+oldCardTypeId);			
								
					HashMap<String, Object> oldCardInfo = CardInfoUtil.getCardInfo(oldCardTypeId);
					String oldCardType = SQLQueryEngine.display(oldCardInfo, "CARD_CODE");
					String oldCardLevel = SQLQueryEngine.display(oldCardInfo, "CARD_LEVEL");
					logger.debug("oldCardType >> "+oldCardType);			
					logger.debug("oldCardLevel >> "+oldCardLevel);
					
//					CardDataM cardM = applicationM.getCard();
					String cardTypeId = cardM.getCardType();	
					logger.debug("cardTypeId >> "+cardTypeId);
					HashMap<String, Object> CardInfoList = CardInfoUtil.getCardInfo(cardTypeId);
					String cardType = SQLQueryEngine.display(CardInfoList, "CARD_CODE");
					String cardLevel = SQLQueryEngine.display(CardInfoList, "CARD_LEVEL");
					logger.debug("cardType >> "+cardType);
					logger.debug("cardLevel >> "+cardLevel);
					
					String cardTypeDisplayMode = HtmlUtil.VIEW;
					String cardTypeDisplayValue = cardType;
					
					if(!Util.empty(applicationM.getMaincardRecordId())){
						cardTypeDisplayValue = applicationM.getMaincardRecordId()+"_"+cardType;
					}
					
					String cardLevelDisplayValue = cardLevel;			
					if(Util.empty(oldCardType) || Util.empty(cardType) || !oldCardType.equals(cardType)) {
						cardTypeDisplayMode = HtmlUtil.EDIT;
						cardTypeDisplayValue = "";
						cardLevelDisplayValue = "";
					}else{
						if(!Util.empty(oldCardType) && !Util.empty(cardType) && oldCardType.equals(cardType)) {
							if(Util.empty(oldCardLevel) || Util.empty(cardLevel) || !oldCardLevel.equals(cardLevel)) {
								cardLevelDisplayValue = "";
							}
						}
					}
					
					logger.debug("cardTypeDisplayValue : "+cardTypeDisplayValue);
					logger.debug("cardLevelDisplayValue : "+cardLevelDisplayValue);
					
					String cardTypeListBoxId = CARD_TYPE_REKEY_LISTBOX;
					String cardTypeLabel = "CARD_TYPE";
					String cacheID = cardType;
					PersonalInfoDataM personalInfo = applicationGroup.getPersonalInfoApplication(applicationM.getApplicationRecordId(), PERSONAL_RELATION_APPLICATION_LEVEL);
					String elementId = FormatUtil.getProductElementId(personalInfo, PRODUCT_K_EXPRESS_CASH);
					String tagId = HtmlUtil.elementTagId(compareDataM.getFieldNameType(), elementId);
					String businessClassId = applicationM.getBusinessClassId();	
					logger.debug("businessClassId >> "+businessClassId);
					HTML.append("<div class='col-sm-12'>")
						.append("<div class='form-group'>")
						.append(HtmlUtil.getFieldLabel(request, cardTypeLabel, "col-sm-4 col-md-5 control-label"))
						.append(HtmlUtil.dropdown(CompareSensitiveFieldUtil.getElementName(compareDataM,SUFFIX_NAME_CARD_TYPE), CompareSensitiveFieldUtil.getElementSuffix(compareDataM), cardTypeListBoxId, cardTypeDisplayValue, "", cacheID,businessClassId, 
								cardTypeDisplayMode,tagId, "col-sm-8 col-md-7", request))
						.append("</div></div>");	
					
					HTML_APPEND.append(getRowSectionHtml(HTML.toString()));
				}
				
				return SECTION_HTML.toString().replace(HTML_REPLACE_PARAM, HTML_APPEND.toString());
			}
		}

		return "";
	}
		
	private String getRowSectionHtml(String html){
		StringBuilder  SECTION_HTML =  new  StringBuilder();
		SECTION_HTML.append("<tr>")
					.append("<td class='rekey-td-boder'>")
					.append("<div class='row'>")
					.append(html)
					.append("</div>")
					.append("</td>")
					.append("</tr>");
		SECTION_HTML.append("<div class='clearfix'></div>");
		return SECTION_HTML.toString();	 
	}
	
	@Override
	public <T>Object getObjectElement(HttpServletRequest request,Object objectForm,FieldElement fieldElement){
		String roleId = FormControl.getFormRoleId(request);
		ApplicationGroupDataM applicationGroup = ((ApplicationGroupDataM)objectForm);
		ArrayList<CompareDataM> compareList = new ArrayList<CompareDataM>();			
			PersonalInfoDataM personalInfoM = applicationGroup.getPersonalInfo(PERSONAL_TYPE_APPLICANT);
			if(null != personalInfoM){				
				ArrayList<String> applicationIds = applicationGroup.filterPersonalApplicationIdLifeCycle(personalInfoM.getPersonalId()
						,PERSONAL_RELATION_APPLICATION_LEVEL);
				if(!Util.empty(applicationIds)) {
					for(String applicationId : applicationIds) {
						ApplicationDataM application = applicationGroup.getApplicationById(applicationId,PRODUCT_K_EXPRESS_CASH);					
						if(!Util.empty(application)) {
							String product = application.getProduct();
							CompareDataM compareData  = new CompareDataM();
							compareData.setApplicationGroupId(applicationGroup.getApplicationGroupId());
							compareData.setFieldNameType(getImplementId());
							compareData.setRole(roleId);
							compareData.setRefLevel(CompareDataM.RefLevel.CARD);
							compareData.setSrcOfData(CompareDataM.SoruceOfData.TWO_MAKER);	
							compareData.setUniqueId(application.getApplicationRecordId());
							compareData.setUniqueLevel(CompareDataM.UniqueLevel.APPLICATION);	
							compareData.setConfigData(CompareSensitiveFieldUtil.fieldConfigDataMToJSonString(personalInfoM,application.getProduct()));
							compareData.setSeq(CompareSensitiveFieldUtil.getCompareFieldSeq(getImplementId()));
							CardDataM cardM = application.getCard();
							if(!Util.empty(cardM) && !Util.empty(cardM.getCardType())) {
								HashMap<String, Object> CardInfoList = CardInfoUtil.getCardInfo(cardM.getCardType());
								String cardCode = SQLQueryEngine.display(CardInfoList, "CARD_CODE");
								String cardCodeRefId = CompareSensitiveFieldUtil.getCardCodeRefId(personalInfoM, product, cardCode);
								String cardTypeRefId = CompareSensitiveFieldUtil.getCardTypeRefId(personalInfoM, product, cardM.getCardType());
								compareData.setValue(cardM.getCardType());
								compareData.setRefId(cardCodeRefId);
								compareData.setCurrentRefId(cardTypeRefId);	
								compareData.setFieldName(CompareSensitiveFieldUtil.getFieldName(compareData));
							}else{
								continue;
							}
							compareList.add(compareData);
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
			logger.debug("cardTypeElementValue : "+cardTypeElementValue);
			HashMap<String, Object> CardInfoList = CardInfoUtil.getCardInfoCardType(cardTypeElementValue);
			String cardTypeId = SQLQueryEngine.display(CardInfoList, "CARD_TYPE_ID");
			logger.debug("cardTypeId >>"+cardTypeId);
			if(!Util.empty(cardTypeId)){
				cardM.setCardType(cardTypeId);
			}
		} 
		return null;
	}
	
	@Override
	public HashMap<String,Object> validateElement(HttpServletRequest request, Object objectElement){
		FormErrorUtil formError = new FormErrorUtil();
		/*
		String PREFIX_ERROR = MessageErrorUtil.getText(request,"PREFIX_ERROR");
		ModuleFormHandler RekeyForm = (ModuleFormHandler)request.getSession().getAttribute(REKEY_FORM);
		ApplicationGroupDataM applicationGroup = (ApplicationGroupDataM)RekeyForm.getObjectForm();		
		CompareDataM compareData = ((CompareDataM)objectElement);	
		logger.debug(compareData);
		String refID = compareData.getRefId();
		logger.debug("refID : "+refID);
		PersonalInfoDataM personalInfoM = CompareSensitiveFieldUtil.getPersonalInfoByRefId(applicationGroup, compareData);
		if(null == personalInfoM){
			personalInfoM = new PersonalInfoDataM();
		}
		ApplicationDataM application = CompareSensitiveFieldUtil.getApplicationProductTypeByRefId(applicationGroup, compareData);
		if(Util.empty(application)){
			application = new ApplicationDataM();
		}
		String product = application.getProduct();
		logger.debug("product : "+product);
		String cardTypeLabel = "CARD_TYPE";
		String cardLevelLabel = "CARD_LEVEL";
		if(PERSONAL_TYPE_SUPPLEMENTARY.equals(personalInfoM.getPersonalType())) {
			cardTypeLabel = "CARD_TYPE_SUP";
			cardLevelLabel = "CARD_LEVEL_SUP";
		}
		if(PRODUCT_K_PERSONAL_LOAN.equals(product)){
			String busClassRefId = CompareSensitiveFieldUtil.getBusClassRefId(personalInfoM, product, application.getBusinessClassId());
			if(Util.empty(application.getBusinessClassId())){
				formError.error("PRODUCTS_CARD_TYPE_"+busClassRefId,PREFIX_ERROR+LabelUtil.getText(request,"PRODUCTS_CARD_TYPE"));
			}
		}else{
			String cardType = request.getParameter("CARD_TYPE_"+refID);
			if(Util.empty(cardType)){
				formError.error("CARD_TYPE_"+refID,PREFIX_ERROR+LabelUtil.getText(request,cardTypeLabel));
			}
			String cardLevel = request.getParameter("CARD_LEVEL_"+refID);
			if(PRODUCT_CRADIT_CARD.equals(product) && Util.empty(cardLevel)){
				formError.error("CARD_LEVEL_"+refID,PREFIX_ERROR+LabelUtil.getText(request,cardLevelLabel));
			}
		}*/
		return formError.getFormError();
	}
	
	@Override
	public ArrayList<CompareFieldElement> compareFieldElement(HttpServletRequest request, Object objectForm) {
		ArrayList<CompareFieldElement> compareFieldElementList = new ArrayList<CompareFieldElement>();
		ApplicationGroupDataM applicationGroup = ORIGFormHandler.getObjectForm(request);	
		ArrayList<CompareDataM> currCompareDataMList = (ArrayList<CompareDataM>)objectForm;
		PersonalInfoDataM personalInfoM = applicationGroup.getPersonalInfo(PERSONAL_TYPE_APPLICANT);
		if(!Util.empty(personalInfoM)){
			String compareFlag = "";
			if(currCompareDataMList.size()>0){
				compareFlag=currCompareDataMList.get(0).getCompareFlag();
			}
			
			if(!MConstant.FLAG.SUBMIT.equals(compareFlag)){
				ArrayList<CompareDataM> prevCompareDataMList =CompareSensitiveFieldUtil.getPrevCompareDataMByFieldNameType(applicationGroup,FIELD_NAME_TYPE);
				ArrayList<CompareDataM> currentFilterCompareDataMList = CompareSensitiveFieldUtil.filterCompareDataByPersonalInfo(currCompareDataMList,FIELD_NAME_TYPE, personalInfoM);
				
				if(!Util.empty(currentFilterCompareDataMList)){
					compareFieldElementList.addAll(CompareObject.compareProductFieldElement(currentFilterCompareDataMList,prevCompareDataMList,FIELD_NAME_TYPE,personalInfoM,false));
				}

				if(prevCompareDataMList.size()>currentFilterCompareDataMList.size()){
					ArrayList<CompareDataM> dummyCompareDatas = new ArrayList<CompareDataM>();
					CompareSensitiveFieldUtil.setNoCurrentProduct(applicationGroup,dummyCompareDatas,FIELD_NAME_TYPE);
					compareFieldElementList.addAll(CompareObject.setNotEqualProductCompareFieldElement(request,dummyCompareDatas, FIELD_NAME_TYPE,personalInfoM));
				}
				logger.debug("currentFilterCompareDataMList SIZE>>>"+currentFilterCompareDataMList.size());
				logger.debug("prevCurrCompareDataMList.size()>>>"+prevCompareDataMList.size());
			}
		}		
		return compareFieldElementList;
	}
}
