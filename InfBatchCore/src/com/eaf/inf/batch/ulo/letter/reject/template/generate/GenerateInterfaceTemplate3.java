package com.eaf.inf.batch.ulo.letter.reject.template.generate;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;

import org.apache.log4j.Logger;

import com.eaf.core.ulo.common.cache.InfBatchProperty;
import com.eaf.core.ulo.common.dao.InfBatchObjectDAO;
import com.eaf.core.ulo.common.display.Formatter;
import com.eaf.core.ulo.common.util.InfBatchUtil;
import com.eaf.core.ulo.service.template.cont.TemplateBuilderConstant;
import com.eaf.core.ulo.service.template.model.TemplateBuilderResponse;
import com.eaf.core.ulo.service.template.model.TemplateMasterDataM;
import com.eaf.core.ulo.service.template.model.TemplateVariableDataM;
import com.eaf.inf.batch.ulo.inf.dao.InfDAO;
import com.eaf.inf.batch.ulo.inf.dao.InfFactory;
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
import com.eaf.orig.ulo.model.app.InfBatchLogDataM;

public class GenerateInterfaceTemplate3 extends GenerateRejectLetterHelper{
	private static transient Logger  logger  = Logger.getLogger(GenerateInterfaceTemplate3.class);
	String REJECT_LETTER_INTERFACE_CODE_T2=InfBatchProperty.getInfBatchConfig("REJECT_LETTER_INTERFACE_CODE_T2");
	String REJECT_LETTER_INTERFACE_STATUS_COMPLETE=InfBatchProperty.getInfBatchConfig("REJECT_LETTER_INTERFACE_STATUS_COMPLETE");
	String NOTIFICATION_DUMMY_LETTER_NO=InfBatchProperty.getInfBatchConfig("NOTIFICATION_DUMMY_LETTER_NO");
	String NOTIFICATION_SEND_TO_TYPE_SELLER=InfBatchProperty.getInfBatchConfig("NOTIFICATION_SEND_TO_TYPE_SELLER");
	 @Override
		public Object generateTemplate(Object... object) {
		 RejectLetterInterfaceResponse  rejectLetterInterfaceResponse = new RejectLetterInterfaceResponse();
		 RejectLetterSortingContent rejectLetterSortingContent = new RejectLetterSortingContent();
		 TemplateBuilderResponse templateBuiderResponse = (TemplateBuilderResponse)object[0];
	     RejectTemplateMasterRequestDataM  rejectTemplateReq = (RejectTemplateMasterRequestDataM)object[1];
	     TemplateMasterDataM templateMasterDataM = rejectTemplateReq.getTemplateMasterDataM();
	     TemplateVariableDataM templateVariable = templateBuiderResponse.getTemplateVariable();
	     HashMap<String, Object> hData = (HashMap<String, Object>)templateVariable.getTemplateVariable();
	     HashMap<String, Object> hSorting = (HashMap<String, Object>)templateVariable.getSortingVariable();
		 try {
			  logger.debug("templateVariable.getTemplateVariable()>>T2>>"+hData);
			  logger.debug("templateVariable.getSortingVariable()>>T1>>"+hSorting);
			 	if(!InfBatchUtil.empty(hData)){
			 	RejectLetterDAO dao = RejectLetterFactory.getRejectLetterDAO();
				String detail1 =RejectLetterUtil.getNewLine()+templateBuiderResponse.getDetail1();
				String detail2 =RejectLetterUtil.getNewLine()+templateBuiderResponse.getDetail2();
				String detail3 =RejectLetterUtil.getNewLine()+templateBuiderResponse.getDetail3();
				String postScript1 =RejectLetterUtil.getNewLine()+templateBuiderResponse.getPostScript1();
				String postScript2 =RejectLetterUtil.getNewLine()+templateBuiderResponse.getPostScript2();
				String remark1 =RejectLetterUtil.getNewLine()+templateBuiderResponse.getRemark1();
				
				String LANGUAGE_FLAG =templateMasterDataM.getLanguage();
				String EMAIL_PRIMARY  =(String)hData.get(TemplateBuilderConstant.TemplateVariableName.EMAIL_PRIMARY);
				String LETTER_NO = ""; // dao.generateLetterNo(LANGUAGE_FLAG);			
				String CUSTOMER_NAME =(String)hData.get(TemplateBuilderConstant.TemplateVariableName.CUSTOMER_NAME);
				String ADDRESS_LINE1 =(String)hData.get(TemplateBuilderConstant.TemplateVariableName.ADDRESS_LINE_1);
				String ADDRESS_LINE2 =(String)hData.get(TemplateBuilderConstant.TemplateVariableName.ADDRESS_LINE_2);
				String ID_NO =(String)hData.get(TemplateBuilderConstant.TemplateVariableName.IDNO);
				String APPLICATION_NO =(String)hData.get(TemplateBuilderConstant.TemplateVariableName.APPLICATION_NO);
				String FINAL_DECISION_DATE =(String)hData.get(TemplateBuilderConstant.TemplateVariableName.FINAL_DECISION_DATE);				
				String PRODUCT_TYPE =(String)hData.get(TemplateBuilderConstant.TemplateVariableName.PRODUCT_TYPE);
				ArrayList<String> aplicationRecordIds =(ArrayList<String>)hData.get(TemplateBuilderConstant.TemplateVariableName.APPLICATION_RECORD_ID);
				String letterType = "";
				String configId = getConfigTemplateCondition(templateMasterDataM);
					String className = InfBatchProperty.getInfBatchConfig(configId);
					TemplateConditionRequestDataM conditionRequest = new TemplateConditionRequestDataM();
						conditionRequest.setTemplateMasterDataM(templateMasterDataM);
						conditionRequest.setEmailPrimary(EMAIL_PRIMARY);
					RejectLetterTemplateConditionInf conditionInf = (RejectLetterTemplateConditionInf)Class.forName(className).newInstance();
					TemplateConditionResponseDataM conditionResponse = conditionInf.processTemplateCondition(conditionRequest);
				letterType = conditionResponse.getLetterType();
			
				StringBuilder generateTextFormat = new StringBuilder("");
				generateTextFormat.append("10"+CUSTOMER_NAME);
				generateTextFormat.append(RejectLetterUtil.getNewLineNuberTag("10",0)+ADDRESS_LINE1);
				generateTextFormat.append(RejectLetterUtil.getNewLineNuberTag("10",0)+ADDRESS_LINE2);
				generateTextFormat.append(RejectLetterUtil.getNewLineNuberTag("11",0)+Formatter.displayText(LANGUAGE_FLAG)+ID_NO);
				generateTextFormat.append(RejectLetterUtil.getNewLineNuberTag("20",0)+NOTIFICATION_DUMMY_LETTER_NO);
				generateTextFormat.append(RejectLetterUtil.getNewLineNuberTag("21",0)+FINAL_DECISION_DATE);
				generateTextFormat.append(RejectLetterUtil.getNewLineNuberTag("22",0)+APPLICATION_NO);
				generateTextFormat.append(RejectLetterUtil.getNewLineNuberTag("23",0)+PRODUCT_TYPE);
//				if(!templateMasterDataM.isGeneratePaperOnly() && !InfBatchUtil.empty(EMAIL_PRIMARY)){
//					generateTextFormat.append(RejectLetterUtil.getNewLineNuberTag("24",0)+RejectLetterUtil.EMAIL);
//					rejectLetterInterfaceResponse.setLetterType(RejectLetterUtil.EMAIL);
//				}else{
//					generateTextFormat.append(RejectLetterUtil.getNewLineNuberTag("24",0)+RejectLetterUtil.PAPER);
//					rejectLetterInterfaceResponse.setLetterType(RejectLetterUtil.PAPER);
//				}	
				generateTextFormat.append(RejectLetterUtil.getNewLineNuberTag("24",0)+letterType);
				generateTextFormat.append(detail1.replace("\n", RejectLetterUtil.getNewLineNuberTag("30",0)));
				generateTextFormat.append(detail2.replace("\n", RejectLetterUtil.getNewLineNuberTag("31",0)));
				generateTextFormat.append(detail3.replace("\n", RejectLetterUtil.getNewLineNuberTag("30",0)));
				generateTextFormat.append(postScript1.replace("\n", RejectLetterUtil.getNewLineNuberTag("40",0)));
				generateTextFormat.append(postScript2.replace("\n", RejectLetterUtil.getNewLineNuberTag("40",0)));
				generateTextFormat.append(remark1.replace("\n", RejectLetterUtil.getNewLineNuberTag("41",0)));
				generateTextFormat.append(RejectLetterUtil.getNewLineNuberTag("90",0));
				generateTextFormat.append(RejectLetterUtil.getNewLine());
				
//				logger.debug("generateTextFormat>>"+generateTextFormat);
				rejectLetterInterfaceResponse.setContentT2(generateTextFormat.toString());
				rejectLetterInterfaceResponse.setInterfaceCode(REJECT_LETTER_INTERFACE_CODE_T2);
				rejectLetterInterfaceResponse.setLetterNo(LETTER_NO);
				rejectLetterInterfaceResponse.setLanguageFlag(LANGUAGE_FLAG);
				rejectLetterInterfaceResponse.setLetterType(letterType);
				if(!InfBatchUtil.empty(hSorting)){
					String ZIPCODE =(String)hSorting.get(TemplateBuilderConstant.TemplateSortingName.ZIPCODE);
					rejectLetterSortingContent.setZipcode(ZIPCODE);
					rejectLetterInterfaceResponse.setRejectLetterSortingContent(rejectLetterSortingContent);
				 }
				String infLogSendTo = (templateMasterDataM.isSendSellerNoCust()) ? NOTIFICATION_SEND_TO_TYPE_SELLER : rejectTemplateReq.getSendTo();
				//RejectLetterLogDataM rejectLetterLogDataM = getRejectLetterLogDataM(aplicationRecordIds, LETTER_NO, rejectLetterInterfaceResponse.getLetterType(), LANGUAGE_FLAG, templateVariable.getUniqueId(), rejectTemplateReq.getSendTo());
				RejectLetterLogDataM rejectLetterLogDataM = getRejectLetterLog(aplicationRecordIds, LETTER_NO, rejectLetterInterfaceResponse.getLetterType(), LANGUAGE_FLAG, templateVariable.getUniqueId().getId(), infLogSendTo,templateVariable.getUniqueId().getLifeCycle(), rejectTemplateReq.getTemplateBuilderRequest().getTemplateId());
				rejectLetterInterfaceResponse.setRejectLetterLogDataM(rejectLetterLogDataM);
				//insertInfBatchLog(aplicationRecordIds,LETTER_NO,rejectLetterInterfaceResponse.getLetterType(),LANGUAGE_FLAG,templateVariable.getUniqueId(),rejectTemplateReq.getSendTo());
			 }
		} catch (Exception e) {
			logger.fatal("ERROR",e);
		}
		 	return rejectLetterInterfaceResponse;
		}
	 private RejectLetterLogDataM getRejectLetterLog(ArrayList<String> aplicationRecordIds,String letterNo,String letterType,String language,String applicationGroupId,String sendTo,int lifeCycle,String templateId) throws Exception{
    	 RejectLetterLogDataM getRejectLetterLog = new RejectLetterLogDataM();
    	 getRejectLetterLog.setAplicationRecordIds(aplicationRecordIds);
    	 getRejectLetterLog.setLetterNo(letterNo);
    	 getRejectLetterLog.setInterfaceCode(REJECT_LETTER_INTERFACE_CODE_T2);
    	 getRejectLetterLog.setInterfaceStatus(REJECT_LETTER_INTERFACE_STATUS_COMPLETE);
    	 getRejectLetterLog.setLanguage(language);
    	 getRejectLetterLog.setLetterType(letterType);
    	 getRejectLetterLog.setSendTo(sendTo);
    	 getRejectLetterLog.setApplicationGroupId(applicationGroupId);
    	 getRejectLetterLog.setLifeCycle(lifeCycle);
    	 getRejectLetterLog.setTemplateId(templateId);
    	 return getRejectLetterLog;
     }
     private void insertInfBatchLog(ArrayList<String> aplicationRecordIds,String letterNo,String letterType,
    		 String language,String applicationGroupId,String sendTo){
    	 Connection conn = InfBatchObjectDAO.getConnection();	
    	 try {
    		 InfDAO dao = InfFactory.getInfDAO();
    		 conn.setAutoCommit(false);
    		 if(!InfBatchUtil.empty(aplicationRecordIds)){
    			 for(String applicationRecordId : aplicationRecordIds){
    				 InfBatchLogDataM infBatchLogDataM = new InfBatchLogDataM();
    				 infBatchLogDataM.setApplicationRecordId(applicationRecordId);
    				 infBatchLogDataM.setInterfaceCode(REJECT_LETTER_INTERFACE_CODE_T2);
    				 infBatchLogDataM.setInterfaceStatus(REJECT_LETTER_INTERFACE_STATUS_COMPLETE);
    				 infBatchLogDataM.setSystem01(letterType);
    				 infBatchLogDataM.setSystem02(language);
    				 infBatchLogDataM.setSystem03(sendTo);
    				 infBatchLogDataM.setRefId(letterNo);
    				 infBatchLogDataM.setApplicationGroupId(applicationGroupId);
    				 infBatchLogDataM.setInterfaceDate(InfBatchProperty.getTimestamp());
    				 infBatchLogDataM.setCreateDate(InfBatchProperty.getTimestamp());
    				 dao.insertInfBatchLog(infBatchLogDataM, conn);
    			 }
    		 }
    		 conn.commit();
		}catch (Exception e) {
			logger.fatal("ERROR",e);
			try {
				conn.rollback();
			} catch (SQLException e1) {
				logger.fatal("ERROR",e1);
			}			
		}finally{
			try{
				InfBatchObjectDAO.closeConnection(conn);
			}catch(Exception e){
				logger.fatal("ERROR ",e);
			}
			
		}
     }
}
