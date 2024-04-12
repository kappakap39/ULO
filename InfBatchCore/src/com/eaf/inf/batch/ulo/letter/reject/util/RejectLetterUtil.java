package com.eaf.inf.batch.ulo.letter.reject.util;

import java.sql.Date;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.log4j.Logger;

import com.eaf.core.ulo.common.cache.InfBatchProperty;
import com.eaf.core.ulo.common.display.Formatter;
import com.eaf.core.ulo.common.exception.InfBatchException;
import com.eaf.core.ulo.common.properties.CacheControl;
import com.eaf.core.ulo.common.properties.SystemConfig;
import com.eaf.core.ulo.common.properties.SystemConstant;
import com.eaf.core.ulo.common.util.InfBatchUtil;
import com.eaf.core.ulo.common.util.Util;
import com.eaf.core.ulo.service.template.model.TemplateBuilderRequest;
import com.eaf.inf.batch.ulo.letter.reject.model.RejectTemplateVariableDataM;
import com.eaf.inf.batch.ulo.notification.dao.NotificationFactory;
import com.eaf.inf.batch.ulo.notification.dao.TemplateDAO;
import com.eaf.orig.ulo.app.dao.ORIGDAOFactory;
import com.eaf.orig.ulo.model.app.ApplicationGroupDataM;
import com.eaf.orig.ulo.model.app.FollowDocHistoryDataM;

public class RejectLetterUtil {
	private static transient Logger logger = Logger.getLogger(RejectLetterUtil.class);
	public static String REJECT_LETTER_TEMPLATE_APPY_TYPE_NW=InfBatchProperty.getInfBatchConfig("REJECT_LETTER_TEMPLATE_APPY_TYPE_NW");
	public static String REJECT_LETTER_TEMPLATE_APPY_TYPE_INC=InfBatchProperty.getInfBatchConfig("REJECT_LETTER_TEMPLATE_APPY_TYPE_INC");
	public static String CASE_NW_APPLICATION_TYPE=InfBatchProperty.getInfBatchConfig("REJECT_LETTER_CASE_NW_APPLICATION_TYPE");
	public static String CASE_INC_APPLICATION_TYPE=InfBatchProperty.getInfBatchConfig("REJECT_LETTER_CASE_INC_APPLICATION_TYPE");
	public static String REJECT_LETTER_BUSINESS_CLASS_ID_KPL_TOPUP=InfBatchProperty.getInfBatchConfig("REJECT_LETTER_BUSINESS_CLASS_ID_KPL_TOPUP");
	public static String REJECT_LETTER_PRODUCT_NAME_CC=InfBatchProperty.getInfBatchConfig("REJECT_LETTER_PRODUCT_NAME_CC");
	public static String REJECT_LETTER_PRODUCT_NAME_KPL=InfBatchProperty.getInfBatchConfig("REJECT_LETTER_PRODUCT_NAME_KPL");
	public static String REJECT_LETTER_PRODUCT_NAME_KEC=InfBatchProperty.getInfBatchConfig("REJECT_LETTER_PRODUCT_NAME_KEC");
	public static String REJECT_LETTER_CONJUNCTION_FOR_TH= InfBatchProperty.getInfBatchConfig("REJECT_LETTER_CONJUNCTION_FOR_TH");
	public static String REJECT_LETTER_CONJUNCTION_FOR_EN= InfBatchProperty.getInfBatchConfig("REJECT_LETTER_CONJUNCTION_FOR_EN");
	public static String REJECT_LETTER_MAIN_CARD_TH = InfBatchProperty.getInfBatchConfig("REJECT_LETTER_MAIN_CARD_TH");	
	public static String REJECT_LETTER_SUP_CARD_TH = InfBatchProperty.getInfBatchConfig("REJECT_LETTER_SUP_CARD_TH");	
	public static String REJECT_LETTER_MAIN_CARD_EN = InfBatchProperty.getInfBatchConfig("REJECT_LETTER_MAIN_CARD_EN");	
	public static String REJECT_LETTER_SUP_CARD_EN = InfBatchProperty.getInfBatchConfig("REJECT_LETTER_SUP_CARD_EN");
	public static String PERSONAL_TYPE_APPLICANT= InfBatchProperty.getInfBatchConfig("REJECT_LETTER_PERSONAL_TYPE_APPLICANT");
	public static String PERSONAL_TYPE_SUPPLEMENTARY= InfBatchProperty.getInfBatchConfig("REJECT_LETTER_PERSONAL_TYPE_SUPPLEMENTARY");
	public static String LANGUAGE_TH = InfBatchProperty.getInfBatchConfig("LANGUAGE_TH");
	public static String LANGUAGE_OTHER = InfBatchProperty.getInfBatchConfig("LANGUAGE_OTHER");
	public static final String TH = "TH";
	public static final String EN = "EN";
	public static final String PAPER = "PAPER";
	public static final String EMAIL = "EMAIL";
	public static final String EMAIL_TO_SELLER = "EMAIL_TO_SELLER";
	public static final String EMAIL_ALL_AFP = "EMAIL_ALL_AFP";
	public static final String PDF = "PDF";
	public static final String Y = "Y";
	public static final String N = "N";
	public static final ArrayList<String> applicationtypeNew = new ArrayList<String>(Arrays.asList(CASE_NW_APPLICATION_TYPE.split(",")));
	public static final ArrayList<String> applicationTypeInc = new ArrayList<String>(Arrays.asList(CASE_INC_APPLICATION_TYPE.split(",")));
	
	public static String getLetterTemplateAppyType(String applicationType,String businessClassId){
		String letterAppyType ="";		
		logger.debug(">>applicationType>>"+applicationType);
		logger.debug(">>businessClassId>>"+businessClassId);
		if(applicationtypeNew.contains(applicationType) && !isIncreaseKPLTopUp(applicationType,businessClassId)){
			letterAppyType= REJECT_LETTER_TEMPLATE_APPY_TYPE_NW;
		}else if(applicationTypeInc.contains(applicationType)){
			letterAppyType= REJECT_LETTER_TEMPLATE_APPY_TYPE_INC;
		}
		return letterAppyType;
	}
	
	private static boolean isIncreaseKPLTopUp(String applicationType,String businessClassId){
		if(REJECT_LETTER_BUSINESS_CLASS_ID_KPL_TOPUP.equals(businessClassId) &&  applicationtypeNew.contains(applicationType)){
			return true;
		}
		return false;
	}
	
	public static String generateRejectLetterFileName(String fielName){
		try {
			String yyMMDD = Formatter.display(InfBatchProperty.getDate(), Formatter.EN, Formatter.Format.YYYYMMDD);
			fielName =fielName.replace("YYYYMMDD", yyMMDD);
			logger.debug("OUTPUT_NAME : "+fielName);
		} catch (Exception e) {
			logger.fatal("ERROR",e);
		}
		return fielName;		
	}
	
	public static String generateRejectLetterParamFileName(String fielName){
		try {
			Date date = InfBatchProperty.getDate();
			if(!Util.empty(SystemConfig.getGeneralParam("CTE_DATE"))){
				date = InfBatchProperty.getDateGeneralParam();
			}
			String yyMMDD = Formatter.display(date, Formatter.EN, Formatter.Format.YYYYMMDD);
			fielName =fielName.replace("YYYYMMDD", yyMMDD);
			logger.debug("OUTPUT_NAME : "+fielName);
		} catch (Exception e) {
			logger.fatal("ERROR",e);
		}
		return fielName;		
	}
	
	public static String getIndent (int indentNum){
		StringBuilder indentSpace   = new StringBuilder("");
		try {
			if(0!=indentNum){
				for(int i =0;i<indentNum;i++){
					indentSpace.append(" ");
				}
			}
		} catch (Exception e) {
			logger.fatal("ERROR",e);
		}
		return indentSpace.toString();		
	}
	
	public static String getNewLineNuberTag (String numberTag,int indentNum){
		try {
		 if(!InfBatchUtil.empty(numberTag)){
			 return "\n"+numberTag+getIndent(indentNum);
		 }
		} catch (Exception e) {
			logger.fatal("ERROR",e);
		}
		return "\n";		
	}
	public static String getNewLine(){
	return System.getProperty("line.separator");		
	}
	public static String getReplaceDummyContent(String dummyContent, String field, String value){
		String NOTIFICATION_REPLACE_DUMMY_PATTERN=InfBatchProperty.getInfBatchConfig("NOTIFICATION_REPLACE_DUMMY_PATTERN");
		StringBuilder builder = new StringBuilder("");
		if(!InfBatchUtil.empty(dummyContent)){
			Pattern pattern = Pattern.compile(NOTIFICATION_REPLACE_DUMMY_PATTERN);
			Matcher matcher = pattern.matcher(dummyContent);
			int i = 0;
			while (matcher.find()) {
				builder.append(dummyContent.substring(i, matcher.start()));
				if(field.equals(matcher.group(1))){
					 builder.append(value);
				}else{
					builder.append("");
				}
				i = matcher.end();
			}
			builder.append(dummyContent.substring(i, dummyContent.length()));
		}
		
		return builder.toString();
	}
	public static String getProductWebsite(ArrayList<String> productList)throws InfBatchException{
		logger.debug("productList : "+productList);
		if(!InfBatchUtil.empty(productList)){
			String generalParamCode = "";
			if(productList.size() > 1){
				generalParamCode = "WEBSITE_BUNDLE_PRODUCT";
			}else{
				generalParamCode = "WEBSITE_"+productList.get(0)+"_PRODUCT";
			}
			logger.debug("generalParamCode : "+generalParamCode);
			return getAllWebsite().get(generalParamCode);
		}
		return "";
	}
	public static HashMap<String,String> getAllWebsite()throws InfBatchException{
		ArrayList<String> REJECT_LETTER_WEBSITE_PARAM_ALL = InfBatchProperty.getListInfBatchConfig("REJECT_LETTER_WEBSITE_PARAM_ALL");
		HashMap hWebsite = NotificationFactory.getNotificationDAO().getGeneralParamMap(REJECT_LETTER_WEBSITE_PARAM_ALL);
		if(!InfBatchUtil.empty(hWebsite)){
			return hWebsite;
		}
		return new HashMap<String,String>();
	}
	public static String getRejectLetterReason(TemplateBuilderRequest templateBuilderRequest)throws InfBatchException{
		String templateType =templateBuilderRequest.getTemplateType();
		String templateId =templateBuilderRequest.getTemplateId();
		String configId = templateType+"_"+templateId+"_REASON_PARAM_CODE";
		String REASON_PARAM_CODE = InfBatchProperty.getInfBatchConfig(templateType+"_"+templateId+"_REASON_PARAM_CODE");
		logger.debug("configId : "+configId);
		logger.debug("REASON_PARAM_CODE : "+REASON_PARAM_CODE);
		if(!InfBatchUtil.empty(REASON_PARAM_CODE)){
			return NotificationFactory.getNotificationDAO().getGeneralParamValue(REASON_PARAM_CODE);
		}
		return "";
	}
	public static String getDocumentList(RejectTemplateVariableDataM rejectTemplateVariable)throws InfBatchException{
		StringBuilder documentList  = new StringBuilder("");
		String applicationGroupId = rejectTemplateVariable.getApplicationGroupId();
		String personalId = rejectTemplateVariable.getPersonalId();
		String personalType = rejectTemplateVariable.getPersonalType();
		String language = rejectTemplateVariable.getNationality().equals(LANGUAGE_TH)?LANGUAGE_TH:LANGUAGE_OTHER;
		logger.debug("applicationGroupId : "+applicationGroupId);
		logger.debug("personalId : "+personalId);
		logger.debug("personalType : "+personalType);
		logger.debug("language : "+language);
		try {
			String cardTitle = "";
			String MAIN_CARD_TITLE =REJECT_LETTER_MAIN_CARD_TH;
			String SUB_CARD_TITLE=REJECT_LETTER_SUP_CARD_TH;
			String cacheFieldId = "VALUE";
			if(!RejectLetterUtil.TH.equals(language)){
				MAIN_CARD_TITLE =REJECT_LETTER_MAIN_CARD_EN;
				SUB_CARD_TITLE=REJECT_LETTER_SUP_CARD_EN;
				cacheFieldId = "EN_DESC";
			}
			if(personalType.equals(PERSONAL_TYPE_APPLICANT)){
				cardTitle = MAIN_CARD_TITLE;
			}else if(personalType.equals(PERSONAL_TYPE_SUPPLEMENTARY)){
				cardTitle = SUB_CARD_TITLE;
			}
//			RejectLetterDAO dao = RejectLetterFactory.getRejectLetterDAO();
//			String docList = dao.getRejectDocumentListNotComplete(personalId, personalType);
//			logger.debug("cardTitle : "+cardTitle);
//			logger.debug("docList : "+docList);
//			if(!InfBatchUtil.empty(cardTitle) && !InfBatchUtil.empty(docList)){
//				setDocumentList(documentList,docList,cardTitle);
//			}
			TemplateDAO  dao = NotificationFactory.getTemplateDAO();
			ApplicationGroupDataM applicationGroup = ORIGDAOFactory.getApplicationGroupDAO()
					.loadApplicationGroupDocument(applicationGroupId, personalId);
			ArrayList<FollowDocHistoryDataM> followDocHistorys = dao.loadFollowDocHistorys(applicationGroup);
			if(null!=followDocHistorys){
				List<String> notCompleteDocs = new ArrayList<String>();
				List<String> existingDocumentCodes = new ArrayList<String>();
				for(FollowDocHistoryDataM followDocHistory : followDocHistorys){
					logger.debug("followDocHistory.DocumentCode : "+followDocHistory);
					//notCompleteDocs.add(CacheControl.getName(SystemConstant.getConstant("CACH_NAME_DOCUMENT_LIST"), followDocHistory.getDocumentCode()));
					if(!existingDocumentCodes.contains(followDocHistory.getDocumentCode())){
						existingDocumentCodes.add(followDocHistory.getDocumentCode());
						notCompleteDocs.add(CacheControl.getName(SystemConstant.getConstant("CACH_NAME_DOCUMENT_LIST"), followDocHistory.getDocumentCode(), cacheFieldId));
					}
				}
				logger.debug("notCompleteDocs : "+notCompleteDocs);
				if(!InfBatchUtil.empty(notCompleteDocs)){
					setDocumentList(documentList, notCompleteDocs, cardTitle);
				}
			}
		} catch (Exception e) {
			logger.fatal("ERROR",e);
			throw new InfBatchException(e);
			
		}
		logger.debug("documentList : "+documentList.toString());
		return documentList.toString();
	}
	private  static void setDocumentList(StringBuilder documentListAppend,List<String> documents ,String title)throws InfBatchException{
		try {
			documentListAppend.append(RejectLetterUtil.getNewLine()+RejectLetterUtil.getIndent(10)+title);
			for(String document : documents){
				documentListAppend.append(RejectLetterUtil.getNewLine()+RejectLetterUtil.getIndent(12)+document);
			}
		} catch (Exception e) {
			logger.fatal("ERROR",e);
			throw new InfBatchException(e);
		}
	}
}
