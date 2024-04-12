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
import com.eaf.core.ulo.common.message.MessageErrorUtil;
import com.eaf.core.ulo.common.model.FieldElement;
import com.eaf.core.ulo.common.properties.CacheControl;
import com.eaf.core.ulo.common.properties.FormControl;
import com.eaf.core.ulo.common.properties.SystemConstant;
import com.eaf.core.ulo.common.util.FormErrorUtil;
import com.eaf.core.ulo.common.util.Util;
import com.eaf.orig.ulo.app.view.form.sensitive.util.CompareSensitiveFieldUtil;
import com.eaf.orig.ulo.app.view.form.subform.product.manual.CardInfoUtil;
import com.eaf.orig.ulo.formcontrol.view.form.ModuleFormHandler;
import com.eaf.orig.ulo.model.app.ApplicationDataM;
import com.eaf.orig.ulo.model.app.ApplicationGroupDataM;
import com.eaf.orig.ulo.model.app.CardDataM;
import com.eaf.orig.ulo.model.app.PersonalInfoDataM;
import com.eaf.orig.ulo.model.compare.CompareDataM;

public class CardInfo extends ElementHelper implements ElementInf{
	static String MAIN = "MAIN";	
	static String SUP = "SUP";	
	String PRODUCT_K_PERSONAL_LOAN = SystemConstant.getConstant("PRODUCT_K_PERSONAL_LOAN");
	String PRODUCT_CRADIT_CARD = SystemConstant.getConstant("PRODUCT_CRADIT_CARD");
	String PERSONAL_TYPE_SUPPLEMENTARY = SystemConstant.getConstant("PERSONAL_TYPE_SUPPLEMENTARY");	
	String PERSONAL_RELATION_APPLICATION_LEVEL = SystemConstant.getConstant("PERSONAL_RELATION_APPLICATION_LEVEL");
	String PERSONAL_TYPE = SystemConstant.getConstant("PERSONAL_TYPE_APPLICANT");	
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
	
	private static transient Logger logger = Logger.getLogger(CardInfo.class);	
	@Override
	public String getElement(HttpServletRequest request,Object objectElement) {	
		ModuleFormHandler RekeyForm = (ModuleFormHandler)request.getSession().getAttribute(REKEY_FORM);
		ApplicationGroupDataM applicationGroup = (ApplicationGroupDataM)RekeyForm.getObjectForm();	
		CompareDataM compareData = ((CompareDataM)objectElement);
		logger.debug("compareData >> "+compareData);
		PersonalInfoDataM personalInfoM = CompareSensitiveFieldUtil.getPersonalInfoByRefId(applicationGroup, compareData);
		if(null == personalInfoM){
			personalInfoM = new PersonalInfoDataM();
		}
		ApplicationDataM applicationM = CompareSensitiveFieldUtil.getApplicationProductTypeByRefId(applicationGroup, compareData);
		if(applicationM == null) {
			applicationM = new ApplicationDataM();
		}
		String personalApplicationType = (PersonalInfoDataM.PersonalType.APPLICANT.equals(personalInfoM.getPersonalType())?MAIN:SUP);
		logger.debug("personalApplicationType : "+personalApplicationType);
		String applicationType = applicationGroup.getApplicationType();
		logger.debug("applicationType : "+applicationType);
		String product = CompareSensitiveFieldUtil.getProductByRefId(compareData);
		logger.debug("product >> "+product);
		String cardCode = CompareSensitiveFieldUtil.getProductTypeByRefId(compareData);
				
		StringBuilder HTML = new StringBuilder();
		String PERSONAL_TYPE_DESC =	CacheControl.getName(FIELD_ID_PERSONAL_TYPE, personalInfoM.getPersonalType());		
		HTML.append("<div class='col-sm-12'>")
		.append("<div class='form-group'>")
		.append("<div class='row'><div class='col-xs-12'>")
		.append("<div  class='col-sm-6' style='font-weight: bold;' >"+FormatUtil.display(PERSONAL_TYPE_DESC)+"</div>")
		.append("<div  class='col-sm-6' class='input-group'>");
		if(!Util.empty(personalInfoM)) {
			HTML.append(FormatUtil.display(personalInfoM.getThFirstName())+"&nbsp;"+FormatUtil.display(personalInfoM.getThLastName()));
		}
		String productTypeDesc = "";
		if(PRODUCT_K_PERSONAL_LOAN.equals(product)){
			productTypeDesc = CacheControl.getName(CACHE_BUSINESS_CLASS, "BUS_CLASS_ID",applicationM.getBusinessClassId(), "BUS_CLASS_DESC");
		}else{
			CardDataM cardM = applicationM.getCard();
			logger.debug("cardM : "+cardM);			
			if(null != cardM){
				String cardTypeId = cardM.getCardType();
				logger.debug("cardTypeId : "+cardTypeId);
				HashMap<String, Object> CardInfoList = CardInfoUtil.getCardInfo(cardTypeId);
				productTypeDesc = CacheControl.getName(FIELD_ID_CARD_TYPE
						, SQLQueryEngine.display(CardInfoList, "CARD_CODE"), "DISPLAY_NAME");
			}
		}
		logger.debug("productTypeDesc >> "+productTypeDesc);
		HTML.append("</div>")
		.append("</div></div>")
		.append("</div></div>")
		.append("<div class='clearfix'></div>")		
		.append("<div class='col-sm-12'>")
		.append("<div class='form-group'>")
		.append("<div class='row'><div class='col-xs-12'>")
		.append("<div class='col-sm-6' style='font-weight: bold;'>"+HtmlUtil.getFieldLabel(request, "REKEY_"+product+"_"+personalApplicationType,"control-label")+" </div>")
		.append("<div class='col-sm-6' style='font-weight: bold;'>"+productTypeDesc+"</div>")
		.append("</div></div>")
		.append("</div></div>")
		.append("<div class='clearfix'></div>");	
		
		if(PRODUCT_K_PERSONAL_LOAN.equals(product)){
			String KPL_PRODUCT_LISTBOX_FILTER = SystemConstant.getConstant("KPL_PRODUCT_LISTBOX_FILTER");
			String busClassRefId = CompareSensitiveFieldUtil.getBusClassRefId(personalInfoM, product, applicationM.getBusinessClassId());			
			String productTypeDisplayMode = HtmlUtil.EDIT;			
			HTML.append("<div class='col-sm-12'>")
			.append("<div class='form-group'>")
			.append(HtmlUtil.getFieldLabel(request, "PRODUCTS_CARD_TYPE", "col-sm-4 col-md-5 control-label"))
			.append(HtmlUtil.dropdown("PRODUCTS_CARD_TYPE",busClassRefId,KPL_PRODUCT_LISTBOX_FILTER,"","",FIELD_ID_PRODUCT_TYPE,"ALL_ALL_ALL"
					,productTypeDisplayMode,"","col-sm-8 col-md-7",request))
			.append("</div></div>")
			.append("<div class='clearfix'></div>");
		}else{
			String oldCardTypeId = compareData.getOldValue();
			logger.debug("oldCardTypeId >> "+oldCardTypeId);			
						
			HashMap<String, Object> oldCardInfo = CardInfoUtil.getCardInfo(oldCardTypeId);
			String oldCardType = SQLQueryEngine.display(oldCardInfo, "CARD_CODE");
			String oldCardLevel = SQLQueryEngine.display(oldCardInfo, "CARD_LEVEL");
			logger.debug("oldCardType >> "+oldCardType);			
			logger.debug("oldCardLevel >> "+oldCardLevel);
			
			CardDataM cardM = applicationM.getCard();
			String cardTypeId = cardM.getCardType();	
			logger.debug("cardTypeId >> "+cardTypeId);
			HashMap<String, Object> CardInfoList = CardInfoUtil.getCardInfo(cardTypeId);
			String cardType = SQLQueryEngine.display(CardInfoList, "CARD_CODE");
			String cardLevel = SQLQueryEngine.display(CardInfoList, "CARD_LEVEL");
			logger.debug("cardType >> "+cardType);
			logger.debug("cardLevel >> "+cardLevel);
			
			String cardTypeDisplayMode = HtmlUtil.VIEW;
			String cardLevelDisplayMode = HtmlUtil.VIEW;
			String cardTypeDisplayValue = cardType;
			if(!APPLICATION_TYPE_ADD_SUP.equals(applicationType) && PERSONAL_TYPE_SUPPLEMENTARY.equals(personalInfoM.getPersonalType())){
				if(!Util.empty(applicationM.getMaincardRecordId())){
					cardTypeDisplayValue = applicationM.getMaincardRecordId()+"_"+cardType;
				}
			}
			String cardLevelDisplayValue = cardLevel;			
			if(Util.empty(oldCardType) || Util.empty(cardType) || !oldCardType.equals(cardType)) {
				cardTypeDisplayMode = HtmlUtil.EDIT;
				cardLevelDisplayMode = HtmlUtil.EDIT;
				cardTypeDisplayValue = "";
				cardLevelDisplayValue = "";
			}else{
				if(!Util.empty(oldCardType) && !Util.empty(cardType) && oldCardType.equals(cardType)) {
					if(Util.empty(oldCardLevel) || Util.empty(cardLevel) || !oldCardLevel.equals(cardLevel)) {
						cardLevelDisplayMode = HtmlUtil.EDIT;
						cardLevelDisplayValue = "";
					}
				}
			}
			
			logger.debug("cardTypeDisplayValue : "+cardTypeDisplayValue);
			logger.debug("cardLevelDisplayValue : "+cardLevelDisplayValue);
			
			String cardTypeListBoxId = CARD_TYPE_REKEY_LISTBOX;
			String cardLevelListBoxId = CARD_LEVEL_LISTBOX;
			String cardTypeLabel = "CARD_TYPE";
			String cardLevelLabel = "CARD_LEVEL";
			String cacheID = cardType;
			if(APPLICATION_TYPE_ADD_SUP.equals(applicationType)){
				cardTypeListBoxId = "SUB_CARD_TYPE_LISTBOX";
				cacheID = FIELD_ID_CARD_TYPE;
			}else{
				if(PERSONAL_TYPE_SUPPLEMENTARY.equals(personalInfoM.getPersonalType())){
					cardTypeListBoxId = CARD_TYPE_MAIN_APP_LISTBOX;
					cardTypeLabel = "CARD_TYPE_SUP";
					cardLevelLabel = "CARD_LEVEL_SUP";
					cacheID = FIELD_ID_CARD_TYPE;
				}
			}
			HTML.append("<div class='col-sm-12'>")
			.append("<div class='form-group'>")
			.append(HtmlUtil.getFieldLabel(request, cardTypeLabel, "col-sm-4 col-md-5 control-label"));			
			String businessClassId = applicationM.getBusinessClassId();	
			logger.debug("businessClassId >> "+businessClassId);
			
			String cardCodeRefId = CompareSensitiveFieldUtil.getBusClassRefId(personalInfoM, product, cardCode);
			logger.debug("cardCodeRefId >> "+cardCodeRefId);
			
			HTML.append(HtmlUtil.dropdown("CARD_TYPE", cardCodeRefId, cardTypeListBoxId, cardTypeDisplayValue, "", cacheID,businessClassId, 
					cardTypeDisplayMode,"", "col-sm-8 col-md-7", request))					
			.append("</div></div>")
			.append("<div class='clearfix'></div>");
			
			if(PRODUCT_CRADIT_CARD.equals(applicationM.getProduct())){
				HTML.append("<div class='col-sm-12'>")
				.append("<div class='form-group'>")
				.append(HtmlUtil.getFieldLabel(request, cardLevelLabel, "col-sm-4 col-md-5 control-label"))
				.append(HtmlUtil.dropdown("CARD_LEVEL", cardCodeRefId, cardLevelListBoxId, cardLevelDisplayValue, "", FIELD_ID_CARD_LEVEL,
						cardTypeDisplayValue, cardLevelDisplayMode,"", "col-sm-8 col-md-7", request))
				.append("</div></div>")
				.append("<div class='clearfix'></div>");
			}
		}
		return HTML.toString();
	}
	
	@Override
	public <T>Object getObjectElement(HttpServletRequest request,Object objectForm,FieldElement fieldElement){
		String roleId = FormControl.getFormRoleId(request);
		ApplicationGroupDataM applicationGroup = ((ApplicationGroupDataM)objectForm);
		ArrayList<CompareDataM> compareList = new ArrayList<CompareDataM>();
		if(!APPLICATION_TYPE_ADD_SUP.equals(applicationGroup.getApplicationType())) {			
			PersonalInfoDataM personalInfoM = applicationGroup.getPersonalInfo(PERSONAL_TYPE);
			if(null != personalInfoM){				
				ArrayList<String> applicationIds = applicationGroup.filterPersonalApplicationIdLifeCycle(personalInfoM.getPersonalId()
						,PERSONAL_RELATION_APPLICATION_LEVEL);
				if(!Util.empty(applicationIds)) {
					for(String applicationId : applicationIds) {
						ApplicationDataM application = applicationGroup.getApplicationById(applicationId);						
						if(!Util.empty(application)) {
							String product = application.getProduct();
							CompareDataM compareData  = new CompareDataM();
							compareData.setApplicationGroupId(applicationGroup.getApplicationGroupId());
							compareData.setFieldNameType(getImplementId());
							compareData.setRole(roleId);
							compareData.setRefLevel(CompareDataM.RefLevel.CARD);
							compareData.setSrcOfData(CompareDataM.SoruceOfData.TWO_MAKER);							
							logger.debug("BusinessClassId >> "+application.getBusinessClassId());
							if(PRODUCT_K_PERSONAL_LOAN.equals(product)){
								String busClassRefId = CompareSensitiveFieldUtil.getBusClassRefId(personalInfoM,product,application.getBusinessClassId());
								String productRefId = CompareSensitiveFieldUtil.getProductRefId(personalInfoM,product);
								compareData.setFieldName(getImplementId()+CompareDataM.MARKER+busClassRefId);
								compareData.setValue(application.getBusinessClassId());
								compareData.setRefId(busClassRefId);
								compareData.setCurrentRefId(productRefId);								
							}else{
								CardDataM cardM = application.getCard();
								if(!Util.empty(cardM) && !Util.empty(cardM.getCardType())) {
									HashMap<String, Object> CardInfoList = CardInfoUtil.getCardInfo(cardM.getCardType());
									String cardCode = SQLQueryEngine.display(CardInfoList, "CARD_CODE");
									String cardCodeRefId = CompareSensitiveFieldUtil.getCardCodeRefId(personalInfoM, product, cardCode);
									String cardTypeRefId = CompareSensitiveFieldUtil.getCardTypeRefId(personalInfoM, product, cardM.getCardType());
									compareData.setFieldName(getImplementId()+CompareDataM.MARKER+cardCodeRefId);
									compareData.setValue(cardM.getCardType());
									compareData.setRefId(cardCodeRefId);
									compareData.setCurrentRefId(cardTypeRefId);									
								}else{
									continue;
								}
							}
							compareList.add(compareData);
						}
					}
				}
			}
		}
		return compareList;
	}
	
	@Override
	public String processElement(HttpServletRequest request,Object objectElement) {
		logger.debug("CardInfo.processElement..");
		ModuleFormHandler RekeyForm = (ModuleFormHandler)request.getSession().getAttribute(REKEY_FORM);
		ApplicationGroupDataM applicationGroup = (ApplicationGroupDataM)RekeyForm.getObjectForm();
		CompareDataM compareData = ((CompareDataM)objectElement);
		String refID = compareData.getRefId();
		String product = CompareSensitiveFieldUtil.getProductByRefId(compareData);
		PersonalInfoDataM personalInfoM = CompareSensitiveFieldUtil.getPersonalInfoByRefId(applicationGroup, compareData);
		if(null == personalInfoM){
			personalInfoM = new PersonalInfoDataM();
		}
		ApplicationDataM application = CompareSensitiveFieldUtil.getApplicationProductTypeByRefId(applicationGroup, compareData);
		if(PRODUCT_K_PERSONAL_LOAN.equals(product)){
			String busClassRefId = CompareSensitiveFieldUtil.getBusClassRefId(personalInfoM, product, application.getBusinessClassId());
			String PRODUCT_TYPE = request.getParameter("PRODUCTS_CARD_TYPE_"+busClassRefId);
			String businessClassId = CacheControl.getName(CACHE_BUSINESS_CLASS, "PRODUCT_ID", PRODUCT_TYPE, "BUS_CLASS_ID");
			logger.debug("businessClassId : "+businessClassId);
			if(!Util.empty(businessClassId)){
				application.setBusinessClassId(businessClassId);
			}
		}else if(!Util.empty(application)){
			String applicationType = applicationGroup.getApplicationType();
			logger.debug("applicationType : "+applicationType);
			CardDataM cardM = application.getCard();
			if(Util.empty(cardM)) {
				cardM = new CardDataM();
			}			
			String cardTypeElementValue = request.getParameter("CARD_TYPE_"+refID);
			String cardLevel = request.getParameter("CARD_LEVEL_"+refID);
			logger.debug("cardTypeElementValue : "+cardTypeElementValue);
			logger.debug("cardLevel : "+cardLevel);
			if(!APPLICATION_TYPE_ADD_SUP.equals(applicationType) && PERSONAL_TYPE_SUPPLEMENTARY.equals(personalInfoM.getPersonalType())){
				if(!Util.empty(cardTypeElementValue)){
					try{
						String [] objectElementValue = cardTypeElementValue.split("\\_");
						String cardType = objectElementValue[0];
						String maincardRecordId = objectElementValue[1];
						logger.debug("cardType : "+cardType);
						logger.debug("maincardRecordId : "+maincardRecordId);						
						HashMap<String,Object> cardInfo = CardInfoUtil.getCardInfo(cardType,cardLevel);
						String CARD_TYPE_ID = SQLQueryEngine.display(cardInfo,"CARD_TYPE_ID");
						logger.debug("CARD_TYPE_ID : "+CARD_TYPE_ID);
						if(!Util.empty(CARD_TYPE_ID)){
							cardM.setCardType(CARD_TYPE_ID);
							application.setMaincardRecordId(maincardRecordId);
						}
					}catch(Exception e){
						logger.fatal("ERROR",e);
					}
				}
			}else{
				HashMap<String,Object> cardInfo = CardInfoUtil.getCardInfo(cardTypeElementValue,cardLevel);
				String CARD_TYPE_ID = SQLQueryEngine.display(cardInfo,"CARD_TYPE_ID");
				logger.debug("CARD_TYPE_ID : "+CARD_TYPE_ID);
				if(!Util.empty(CARD_TYPE_ID)){
					cardM.setCardType(CARD_TYPE_ID);
				}
			}
		}
		return null;
	}
	
	@Override
	public HashMap<String,Object> validateElement(HttpServletRequest request, Object objectElement){
		FormErrorUtil formError = new FormErrorUtil();
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
			CardDataM card = application.getCard();
			String cardType = request.getParameter(CompareSensitiveFieldUtil.getElementName(compareData,"_CARD_TYPE")+"_"+CompareSensitiveFieldUtil.getElementSuffix(compareData));
			if(Util.empty(cardType)){
				formError.error("CARD_TYPE_"+refID,PREFIX_ERROR+LabelUtil.getText(request,cardTypeLabel));
			}
			String cardLevel = request.getParameter(CompareSensitiveFieldUtil.getElementName(compareData,"_CARD_LEVEL")+"_"+CompareSensitiveFieldUtil.getElementSuffix(compareData));
			if(PRODUCT_CRADIT_CARD.equals(product) && Util.empty(cardLevel)){
				formError.error("CARD_LEVEL_"+refID,PREFIX_ERROR+LabelUtil.getText(request,cardLevelLabel));
			}
		}
		return formError.getFormError();
	}
}
