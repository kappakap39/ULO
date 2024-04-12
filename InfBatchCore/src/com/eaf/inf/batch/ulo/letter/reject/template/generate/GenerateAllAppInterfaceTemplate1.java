package com.eaf.inf.batch.ulo.letter.reject.template.generate;

import java.util.ArrayList;
import java.util.HashMap;

import org.apache.log4j.Logger;

import com.eaf.core.ulo.common.cache.InfBatchProperty;
import com.eaf.core.ulo.common.util.InfBatchUtil;
import com.eaf.core.ulo.service.template.cont.TemplateBuilderConstant;
import com.eaf.core.ulo.service.template.model.TemplateBuilderResponse;
import com.eaf.core.ulo.service.template.model.TemplateMasterDataM;
import com.eaf.core.ulo.service.template.model.TemplateVariableDataM;
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

public class GenerateAllAppInterfaceTemplate1 extends GenerateRejectLetterHelper{
	private static transient Logger logger = Logger.getLogger(GenerateInterfaceTemplate1.class);	
	String REJECT_LETTER_INTERFACE_CODE_T1=InfBatchProperty.getInfBatchConfig("REJECT_LETTER_INTERFACE_CODE_T1");
	String REJECT_LETTER_INTERFACE_STATUS_COMPLETE=InfBatchProperty.getInfBatchConfig("REJECT_LETTER_INTERFACE_STATUS_COMPLETE");
	String NOTIFICATION_DUMMY_LETTER_NO=InfBatchProperty.getInfBatchConfig("NOTIFICATION_DUMMY_LETTER_NO");
	String NOTIFICATION_SEND_TO_TYPE_SELLER=InfBatchProperty.getInfBatchConfig("NOTIFICATION_SEND_TO_TYPE_SELLER");
	String REJECT_LETTER_INTERFACE_CODE=InfBatchProperty.getInfBatchConfig("REJECT_LETTER_INTERFACE_CODE");
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
			logger.debug("templateVariable.getTemplateVariable()>>T1>>"+hData);
			logger.debug("templateVariable.getSortingVariable()>>T1>>"+hSorting);
			//String infLogLetterType = "";
			if(!InfBatchUtil.empty(hData)){
			RejectLetterDAO dao = RejectLetterFactory.getRejectLetterDAO();
			String detail1 =RejectLetterUtil.getNewLine()+templateBuiderResponse.getDetail1();
			String postScript1 =RejectLetterUtil.getNewLine()+templateBuiderResponse.getPostScript1();
			String postScript2 =RejectLetterUtil.getNewLine()+templateBuiderResponse.getPostScript2();
			String remark1 =RejectLetterUtil.getNewLine()+templateBuiderResponse.getRemark1();
			String LANGUAGE_FLAG =templateMaster.getLanguage();
			String LETTER_NO = ""; // dao.generateLetterNo(LANGUAGE_FLAG);			
			String CUSTOMER_NAME =(String)hData.get(TemplateBuilderConstant.TemplateVariableName.CUSTOMER_NAME);
			String EMAIL_PRIMARY  =(String)hData.get(TemplateBuilderConstant.TemplateVariableName.EMAIL_PRIMARY);
			String ADDRESS_LINE1 =(String)hData.get(TemplateBuilderConstant.TemplateVariableName.ADDRESS_LINE_1);
			String ADDRESS_LINE2 =(String)hData.get(TemplateBuilderConstant.TemplateVariableName.ADDRESS_LINE_2);
			String ID_NO =(String)hData.get(TemplateBuilderConstant.TemplateVariableName.IDNO);
			String FINAL_DECISION_DATE =(String)hData.get(TemplateBuilderConstant.TemplateVariableName.FINAL_DECISION_DATE);
			String APPLICATION_NO =(String)hData.get(TemplateBuilderConstant.TemplateVariableName.APPLICATION_NO);
			String PRODUCT_TYPE =(String)hData.get(TemplateBuilderConstant.TemplateVariableName.PRODUCT_TYPE);
			ArrayList<String> aplicationRecordIds =(ArrayList<String>)hData.get(TemplateBuilderConstant.TemplateVariableName.APPLICATION_RECORD_ID);
			String letterType = "";
				String configId = getConfigAllAppTemplateCondition(templateMaster);
				String className = InfBatchProperty.getInfBatchConfig(configId);
				TemplateConditionRequestDataM conditionRequest = new TemplateConditionRequestDataM();
					conditionRequest.setTemplateMasterDataM(templateMaster);
					conditionRequest.setEmailPrimary(EMAIL_PRIMARY);
				RejectLetterTemplateConditionInf conditionInf = (RejectLetterTemplateConditionInf)Class.forName(className).newInstance();
				TemplateConditionResponseDataM conditionResponse = conditionInf.processTemplateCondition(conditionRequest);
			letterType = conditionResponse.getLetterType();
//			logger.debug("templateVariable.getTemplateVariable()>>T1>>"+hData);
			StringBuilder generateTextFormat = new StringBuilder("");
			generateTextFormat.append("10"+CUSTOMER_NAME);
			generateTextFormat.append(RejectLetterUtil.getNewLineNuberTag("10",0)+ADDRESS_LINE1);
			generateTextFormat.append(RejectLetterUtil.getNewLineNuberTag("10",0)+ADDRESS_LINE2);
			generateTextFormat.append(RejectLetterUtil.getNewLineNuberTag("11",0)+LANGUAGE_FLAG+ID_NO);
			generateTextFormat.append(RejectLetterUtil.getNewLineNuberTag("20",0)+NOTIFICATION_DUMMY_LETTER_NO); // generateTextFormat.append(RejectLetterUtil.getNewLineNuberTag("20",0)+LETTER_NO);
			generateTextFormat.append(RejectLetterUtil.getNewLineNuberTag("21",0)+FINAL_DECISION_DATE);
			generateTextFormat.append(RejectLetterUtil.getNewLineNuberTag("22",0)+APPLICATION_NO);
			generateTextFormat.append(RejectLetterUtil.getNewLineNuberTag("23",0)+PRODUCT_TYPE);
			generateTextFormat.append(RejectLetterUtil.getNewLineNuberTag("24",0)+letterType);
			generateTextFormat.append(detail1.replace("\n", RejectLetterUtil.getNewLineNuberTag("30",0)));
			generateTextFormat.append(postScript1.replace("\n", RejectLetterUtil.getNewLineNuberTag("40",0)));
			generateTextFormat.append(postScript2.replace("\n", RejectLetterUtil.getNewLineNuberTag("40",0)));
			generateTextFormat.append(remark1.replace("\n", RejectLetterUtil.getNewLineNuberTag("41",0)));
			generateTextFormat.append(RejectLetterUtil.getNewLineNuberTag("90",0));
			generateTextFormat.append(RejectLetterUtil.getNewLine());
			
			rejectLetterInterfaceResponse.setContentT1(generateTextFormat.toString());
			rejectLetterInterfaceResponse.setInterfaceCode(REJECT_LETTER_INTERFACE_CODE);
			rejectLetterInterfaceResponse.setSubInterfaceCode(REJECT_LETTER_INTERFACE_CODE_T1);
			rejectLetterInterfaceResponse.setLetterNo(LETTER_NO);
			rejectLetterInterfaceResponse.setLanguageFlag(LANGUAGE_FLAG);
			rejectLetterInterfaceResponse.setLetterType(letterType);
			if(!InfBatchUtil.empty(hSorting)){
				String ZIPCODE =(String)hSorting.get(TemplateBuilderConstant.TemplateSortingName.ZIPCODE);
				rejectLetterSortingContent.setZipcode(ZIPCODE);
				rejectLetterInterfaceResponse.setRejectLetterSortingContent(rejectLetterSortingContent);
			 }
			String infLogSendTo = "";
			RejectLetterLogDataM rejectLetterLog = getRejectLetterLog(aplicationRecordIds, LETTER_NO, rejectLetterInterfaceResponse.getLetterType(), LANGUAGE_FLAG, templateVariable.getUniqueId().getId(), infLogSendTo, templateVariable.getUniqueId().getLifeCycle(), rejectTemplateReq.getTemplateBuilderRequest().getTemplateId());
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
    	 rejectLetterLog.setInterfaceCode(REJECT_LETTER_INTERFACE_CODE);
    	 rejectLetterLog.setInterfaceStatus(REJECT_LETTER_INTERFACE_STATUS_COMPLETE);
    	 rejectLetterLog.setLanguage(language);
    	 rejectLetterLog.setLetterType(letterType);
    	 rejectLetterLog.setSendTo(sendTo);
    	 rejectLetterLog.setApplicationGroupId(applicationGroupId);
    	 rejectLetterLog.setLifeCycle(lifeCycle);
    	 rejectLetterLog.setSubInterfaceCode(REJECT_LETTER_INTERFACE_CODE_T1);
    	 rejectLetterLog.setTemplateId(templateId);
    	 return rejectLetterLog;
     }
}
