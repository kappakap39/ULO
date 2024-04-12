package com.eaf.inf.batch.ulo.letter.reject.template.generate;

import java.util.ArrayList;
import java.util.HashMap;

import org.apache.log4j.Logger;

import com.eaf.core.ulo.common.cache.InfBatchProperty;
import com.eaf.core.ulo.common.dao.InfBatchObjectDAO;
import com.eaf.core.ulo.common.display.FormatUtil;
import com.eaf.core.ulo.common.util.InfBatchUtil;
import com.eaf.core.ulo.common.util.Util;
import com.eaf.core.ulo.service.template.cont.TemplateBuilderConstant;
import com.eaf.core.ulo.service.template.model.TemplateBuilderResponse;
import com.eaf.core.ulo.service.template.model.TemplateMasterDataM;
import com.eaf.core.ulo.service.template.model.TemplateVariableDataM;
import com.eaf.inf.batch.ulo.inf.dao.InfDAO;
import com.eaf.inf.batch.ulo.inf.dao.InfFactory;
import com.eaf.inf.batch.ulo.letter.history.dao.LetterHistoryDAO;
import com.eaf.inf.batch.ulo.letter.history.dao.LetterHistoryDAOImpl;
import com.eaf.inf.batch.ulo.letter.history.dao.LetterHistoryFactory;
import com.eaf.inf.batch.ulo.letter.history.model.LetterHistoryDataM;
import com.eaf.inf.batch.ulo.letter.reject.dao.RejectLetterDAO;
import com.eaf.inf.batch.ulo.letter.reject.dao.RejectLetterFactory;
import com.eaf.inf.batch.ulo.letter.reject.model.RejectLetterInterfaceResponse;
import com.eaf.inf.batch.ulo.letter.reject.model.RejectLetterLogDataM;
import com.eaf.inf.batch.ulo.letter.reject.model.RejectLetterSortingContent;
import com.eaf.inf.batch.ulo.letter.reject.model.RejectTemplateMasterRequestDataM;
import com.eaf.inf.batch.ulo.letter.reject.model.TemplateConditionRequestDataM;
import com.eaf.inf.batch.ulo.letter.reject.model.TemplateConditionResponseDataM;
import com.eaf.inf.batch.ulo.letter.reject.template.condition.inf.RejectLetterTemplateConditionInf;
import com.eaf.inf.batch.ulo.letter.reject.template.generate.inf.GenerateRejectLetterHelper;
import com.eaf.inf.batch.ulo.letter.reject.util.RejectLetterUtil;
import com.eaf.orig.ulo.constant.MConstant;
import com.eaf.orig.ulo.model.app.InfBatchLogDataM;
import java.lang.StringBuilder;

public class GenerateAllAppInterfaceTemplate3 extends GenerateRejectLetterHelper {
	private static transient Logger  logger  = Logger.getLogger(GenerateInterfaceTemplate3.class);
	String REJECT_LETTER_INTERFACE_CODE=InfBatchProperty.getInfBatchConfig("REJECT_LETTER_INTERFACE_CODE");
	String REJECT_LETTER_INTERFACE_CODE_T3=InfBatchProperty.getInfBatchConfig("REJECT_LETTER_INTERFACE_CODE_T3");
	String REJECT_LETTER_INTERFACE_STATUS_COMPLETE=InfBatchProperty.getInfBatchConfig("REJECT_LETTER_INTERFACE_STATUS_COMPLETE");
	String NOTIFICATION_DUMMY_LETTER_NO=InfBatchProperty.getInfBatchConfig("NOTIFICATION_DUMMY_LETTER_NO");
	String NOTIFICATION_SEND_TO_TYPE_SELLER=InfBatchProperty.getInfBatchConfig("NOTIFICATION_SEND_TO_TYPE_SELLER");
	String LETTER_CHANNEL_PAPER = InfBatchProperty.getInfBatchConfig("LETTER_CHANNEL_PAPER");
	String LETTER_CHANNEL_EMAIL = InfBatchProperty.getInfBatchConfig("LETTER_CHANNEL_EMAIL");
	String EDOC_EMAIL_TEMPLATE_REJECT_CUSTOMER = InfBatchProperty.getInfBatchConfig("EDOC_EMAIL_TEMPLATE_REJECT_CUSTOMER");
	String KPL_RJ_LETTER_PREFIX_NO = InfBatchProperty.getGeneralParam("KPL_RJ_LETTER_PREFIX_NO");
	String RJ_LETTER_PREFIX_NO_TH = InfBatchProperty.getGeneralParam("RJ_LETTER_PREFIX_NO_TH");
	String RJ_LETTER_PREFIX_NO_EN = InfBatchProperty.getGeneralParam("RJ_LETTER_PREFIX_NO_EN");
	String REJECT_LETTER_PDF_NCB_TEMPLATE = InfBatchProperty.getGeneralParam("REJECT_LETTER_PDF_NCB_TEMPLATE");
	
	 @Override
		public Object generateTemplate(Object... object) {
		 RejectLetterInterfaceResponse  rejectLetterInterfaceResponse = new RejectLetterInterfaceResponse();
		 RejectLetterSortingContent rejectLetterSortingContent = new RejectLetterSortingContent();
		 TemplateBuilderResponse templateBuiderResponse = (TemplateBuilderResponse)object[0];
	     RejectTemplateMasterRequestDataM  rejectTemplateReq = (RejectTemplateMasterRequestDataM)object[1];
	     TemplateMasterDataM templateMaster = rejectTemplateReq.getTemplateMasterDataM();
	     TemplateVariableDataM templateVariable = templateBuiderResponse.getTemplateVariable();
	     HashMap<String, Object> hData = (HashMap<String, Object>)templateVariable.getTemplateVariable();
	     HashMap<String, Object> hSorting = (HashMap<String, Object>)templateVariable.getSortingVariable();
		try {
			logger.debug("templateVariable.getTemplateVariable()>>T3>>"+hData);
			logger.debug("templateVariable.getSortingVariable()>>T3>>"+hSorting);
			
			if(!InfBatchUtil.empty(hData)){
				RejectLetterDAO dao = RejectLetterFactory.getRejectLetterDAO();
				
				String detail1 = templateBuiderResponse.getDetail1();
				String FINAL_DECISION_DATE = (String)hData.get(TemplateBuilderConstant.TemplateVariableName.FINAL_DECISION_DATE);
				String CIS_NO = (String)hData.get(TemplateBuilderConstant.TemplateVariableName.CIS_NO);
				String APPLICATION_NO = (String)hData.get(TemplateBuilderConstant.TemplateVariableName.APPLICATION_NO);
				String PRODUCT_NAME = (String)hData.get(TemplateBuilderConstant.TemplateVariableName.PRODUCT_NAME);
				String CUSTOMER_NAME = (String)hData.get(TemplateBuilderConstant.TemplateVariableName.CUSTOMER_NAME);
				String ADDRESS1 = (String)hData.get(TemplateBuilderConstant.TemplateVariableName.ADDRESS_LINE_1);
				String ADDRESS2 = (String)hData.get(TemplateBuilderConstant.TemplateVariableName.ADDRESS_LINE_2);
				String EDOC_LETTER_TEMPLATE = templateBuiderResponse.getPostScript1();
				//String SEND_METHOD = (String)hData.get(TemplateBuilderConstant.TemplateVariableName.SEND_METHOD);
				String EMAIL_PRIMARY = (String)hData.get(TemplateBuilderConstant.TemplateVariableName.EMAIL_PRIMARY);
				String EDOC_EMAIL_TEMPLATE = (String)hData.get(TemplateBuilderConstant.TemplateVariableName.EDOC_EMAIL_TEMPLATE);
				String LANGUAGE = templateMaster.getLanguage();
				String ATTACH_FILE = (String)hData.get(TemplateBuilderConstant.TemplateVariableName.ATTACH_FILE);
				String MASTER_OR_COPY = MConstant.FLAG_M;
				String LETTER_NO = dao.generateLetterNo(LANGUAGE);
				String IDNO = (String)hData.get(TemplateBuilderConstant.TemplateVariableName.IDNO);
				String TH_FIRST_NAME = (String)hData.get(TemplateBuilderConstant.TemplateVariableName.TH_FIRST_NAME);
				String TH_MID_NAME = (String)hData.get(TemplateBuilderConstant.TemplateVariableName.TH_MID_NAME);
				String TH_LAST_NAME = (String)hData.get(TemplateBuilderConstant.TemplateVariableName.TH_LAST_NAME);
				String LETTER_CHANNEL = (String)hData.get(TemplateBuilderConstant.TemplateVariableName.LETTER_CHANNEL);
				
				String letterType = "";
				String configId = getConfigAllAppTemplateCondition(templateMaster);
					String className = InfBatchProperty.getInfBatchConfig(configId);
					TemplateConditionRequestDataM conditionRequest = new TemplateConditionRequestDataM();
						conditionRequest.setTemplateMasterDataM(templateMaster);
						conditionRequest.setEmailPrimary(EMAIL_PRIMARY);
					RejectLetterTemplateConditionInf conditionInf = (RejectLetterTemplateConditionInf)Class.forName(className).newInstance();
					TemplateConditionResponseDataM conditionResponse = conditionInf.processTemplateConditionKPL(conditionRequest);
				letterType = conditionResponse.getLetterType();
				logger.debug("letterType = " + letterType);
				
				//Prod Incident change SEND_METHOD = letterType
				String SEND_METHOD = "";
				if(RejectLetterUtil.EMAIL.equals(letterType))
				{SEND_METHOD = LETTER_CHANNEL_EMAIL;}
				if(RejectLetterUtil.PAPER.equals(letterType))
				{SEND_METHOD = LETTER_CHANNEL_PAPER;}
				
				if(RejectLetterUtil.EN.equals(LANGUAGE))
				{
					LETTER_NO = LETTER_NO.replace(RJ_LETTER_PREFIX_NO_EN, KPL_RJ_LETTER_PREFIX_NO);
				}
				else
				{
					LETTER_NO = LETTER_NO.replace(RJ_LETTER_PREFIX_NO_TH, KPL_RJ_LETTER_PREFIX_NO);
				}
				
				String formatFDD = "";
				if(!Util.empty(FINAL_DECISION_DATE))
				{
					//Re-format dd/MM/yyyy to yyyy-MM-dd
					String dStrSplit[] = FINAL_DECISION_DATE.split("/");
					formatFDD = dStrSplit[2] + "-" + dStrSplit[1] + "-" + dStrSplit[0];
				}
				
				//Some of fields in detail1 already replace in TemplateBuilderHelper.java
				detail1 = detail1.replace("<LETTER_NO>", LETTER_NO);
				detail1 = detail1.replace("<FINAL_DECISION_DATE>", formatFDD);
				detail1 = detail1.replace("<SEND_METHOD>", "{SEND_METHOD}");
				detail1 = detail1.replace("<EMAIL_PRIMARY>", "{EMAIL_PRIMARY}");
				detail1 = detail1.replace("<EDOC_EMAIL_TEMPLATE>", "{EDOC_EMAIL_TEMPLATE}");
				detail1 = detail1.replace("<LANGUAGE>", "{LANGUAGE}");
				detail1 = detail1.replace("<ATTACH_FILE>", "{ATTACH_FILE}");
				detail1 = detail1.replace("<MASTER_OR_COPY>", "{MASTER_OR_COPY}");
				
				//Add KPL Reject Data to LETTER_HISTORY
				logger.debug("detail1 = " + detail1);
				
				LetterHistoryDataM letterHistory = new LetterHistoryDataM();

				letterHistory.setLetterNo(LETTER_NO);
				letterHistory.setAppGroupNo(APPLICATION_NO);
				letterHistory.setIdNo(IDNO);
				letterHistory.setFirstName(TH_FIRST_NAME);
				letterHistory.setMidName((Util.empty(TH_MID_NAME)) ? "-" : TH_MID_NAME);
				letterHistory.setLastName(TH_LAST_NAME);
				
				letterHistory.setDe2SubmitDate(FormatUtil.toDate(FINAL_DECISION_DATE, FormatUtil.EN));
				letterHistory.setLetterType("02"); //01 Approve , 02 Reject
				letterHistory.setLetterTemplate(EDOC_LETTER_TEMPLATE);
				letterHistory.setLetterContent(detail1);
				
				//Customer info			
				letterHistory.setCustomerEmailTemplate(EDOC_EMAIL_TEMPLATE_REJECT_CUSTOMER);
				letterHistory.setCustomerEmailAddress(EMAIL_PRIMARY);
				letterHistory.setCustomerLanguage(LANGUAGE);
				letterHistory.setCustomerAttachFile(LETTER_CHANNEL_EMAIL.equals(SEND_METHOD) ? "1" : "0");
				letterHistory.setCustomerResendCount(0);
				
				letterHistory.setCustomerSendMethod(SEND_METHOD);
				letterHistory.setSendFlag(Util.empty(SEND_METHOD) ? MConstant.FLAG.NO : MConstant.FLAG.YES);
				letterHistory.setCustomerSendFlag(Util.empty(SEND_METHOD) ? MConstant.FLAG.NO : MConstant.FLAG.YES);
				
				//Seller info
				letterHistory.setSellerSendMethod(LETTER_CHANNEL_EMAIL);
				letterHistory.setSellerEmailAddress(null);
				letterHistory.setSellerEmailTemplate(null);
				letterHistory.setSellerLanguage(null);
				letterHistory.setSellerAttachFile(null);
				letterHistory.setSellerSendFlag(MConstant.FLAG.NO);
				
				//Insert info to table ORIG_APP.LETTER_HISTORY
				LetterHistoryFactory.getLetterHistoryDAO().createLetterHistory(letterHistory);
				
				//Set rejectLetterInterfaceResponse
				ArrayList<String> aplicationRecordIds =(ArrayList<String>)hData.get(TemplateBuilderConstant.TemplateVariableName.APPLICATION_RECORD_ID);				
				rejectLetterInterfaceResponse.setContentT3(LETTER_NO);
				rejectLetterInterfaceResponse.setInterfaceCode(REJECT_LETTER_INTERFACE_CODE_T3);
				rejectLetterInterfaceResponse.setLetterNo(LETTER_NO);
				rejectLetterInterfaceResponse.setLanguageFlag(LANGUAGE);
				rejectLetterInterfaceResponse.setLetterType(letterType);
				if(!InfBatchUtil.empty(hSorting)){
					String ZIPCODE =(String)hSorting.get(TemplateBuilderConstant.TemplateSortingName.ZIPCODE);
					rejectLetterSortingContent.setZipcode(ZIPCODE);
					rejectLetterInterfaceResponse.setRejectLetterSortingContent(rejectLetterSortingContent);
				 }
				String infLogSendTo = (templateMaster.isSendSellerNoCust()) ? NOTIFICATION_SEND_TO_TYPE_SELLER : rejectTemplateReq.getSendTo();
				RejectLetterLogDataM rejectLetterLog = getRejectLetterLog(aplicationRecordIds, LETTER_NO, rejectLetterInterfaceResponse.getLetterType(), LANGUAGE, templateVariable.getUniqueId().getId(), infLogSendTo, templateVariable.getUniqueId().getLifeCycle(), rejectTemplateReq.getTemplateBuilderRequest().getTemplateId());
				rejectLetterInterfaceResponse.setRejectLetterLogDataM(rejectLetterLog);
			}
		} catch (Exception e) {
			logger.fatal("ERROR",e);
		}
		return rejectLetterInterfaceResponse;
	}
     private RejectLetterLogDataM getRejectLetterLog(ArrayList<String> aplicationRecordIds,String letterNo,String letterType,String language,String applicationGroupId,String sendTo,int lifeCycle,String templateId) throws Exception{
    	 RejectLetterLogDataM rejectLetterLog = new RejectLetterLogDataM();
    	 rejectLetterLog.setAplicationRecordIds(aplicationRecordIds);
    	 rejectLetterLog.setLetterNo(letterNo);
    	 rejectLetterLog.setInterfaceCode(REJECT_LETTER_INTERFACE_CODE_T3);
    	 rejectLetterLog.setInterfaceStatus(REJECT_LETTER_INTERFACE_STATUS_COMPLETE);
    	 rejectLetterLog.setLanguage(language);
    	 rejectLetterLog.setLetterType(letterType);
    	 rejectLetterLog.setSendTo(sendTo);
    	 rejectLetterLog.setApplicationGroupId(applicationGroupId);
    	 rejectLetterLog.setLifeCycle(lifeCycle);
    	 rejectLetterLog.setTemplateId(templateId);
    	 return rejectLetterLog;
     }
}
