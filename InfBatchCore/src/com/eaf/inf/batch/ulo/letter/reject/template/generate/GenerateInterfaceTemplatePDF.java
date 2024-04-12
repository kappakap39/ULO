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
import com.eaf.core.ulo.service.template.model.TemplateVariableDataM;
import com.eaf.inf.batch.ulo.letter.history.dao.LetterHistoryDAO;
import com.eaf.inf.batch.ulo.letter.history.dao.LetterHistoryFactory;
import com.eaf.inf.batch.ulo.letter.reject.dao.RejectLetterFactory;
import com.eaf.inf.batch.ulo.letter.reject.dao.RejectLetterPDFDAO;
import com.eaf.inf.batch.ulo.letter.reject.model.RejectLetterInterfaceResponse;
import com.eaf.inf.batch.ulo.letter.reject.model.RejectLetterPDFDataM;
import com.eaf.inf.batch.ulo.letter.reject.template.generate.inf.GenerateRejectLetterHelper;
import com.eaf.orig.ulo.model.app.InfBatchLogDataM;

public class GenerateInterfaceTemplatePDF   extends GenerateRejectLetterHelper{
	private static transient Logger logger = Logger.getLogger(GenerateInterfaceTemplatePDF.class);
	String REJECT_LETTER_INTERFACE_STATUS_COMPLETE=InfBatchProperty.getInfBatchConfig("REJECT_LETTER_INTERFACE_STATUS_COMPLETE");
	String REJECT_LETTER_PDF_INTERFACE_CODE=InfBatchProperty.getInfBatchConfig("REJECT_LETTER_PDF_INTERFACE_CODE");
	String PDF_FIELD_DELIMETER=InfBatchProperty.getInfBatchConfig("REJECT_LETTER_PDF_FIELD_DELIMETER");
	String REJECT_LETTER_PDF_PRODUCT_NAME_KPL = InfBatchProperty.getInfBatchConfig("REJECT_LETTER_PDF_PRODUCT_NAME_KPL");
	String REJECT_LETTER_PDF_SENDTO_CUSTOMER = InfBatchProperty.getInfBatchConfig("REJECT_LETTER_PDF_SENDTO_CUSTOMER");
	String REJECT_LETTER_PDF_SENDTO_SELLER = InfBatchProperty.getInfBatchConfig("REJECT_LETTER_PDF_SENDTO_SELLER");
	String REJECT_LETTER_PDF_EDOC_SUBINTERFACE_CODE=InfBatchProperty.getInfBatchConfig("REJECT_LETTER_PDF_EDOC_SUBINTERFACE_CODE");
	@Override
	public Object generateTemplate(Object... object) {
		RejectLetterInterfaceResponse  rejectLetterInterfaceResponse = new RejectLetterInterfaceResponse();
		TemplateBuilderResponse  templateBuilderResponse = (TemplateBuilderResponse)object[0];
		RejectLetterPDFDataM rejectLetterPDFdataM = (RejectLetterPDFDataM)object[1];
		TemplateVariableDataM templateVariable = templateBuilderResponse.getTemplateVariable();
	   	Connection conn = InfBatchObjectDAO.getConnection();
		try {
		   	conn.setAutoCommit(false);
			if(!InfBatchUtil.empty(templateBuilderResponse)){
				HashMap<String, Object> hData = (HashMap<String, Object>) templateVariable.getTemplateVariable();
				String LETTER_NO = rejectLetterPDFdataM.getLetterNo();
				String APPLICATION_NO = rejectLetterPDFdataM.getApplicationGroupNo();
				String SEND_TYPE = rejectLetterPDFdataM.getLetterType();
				String FINAL_APP_DECISION_DATE = (String)hData.get(TemplateBuilderConstant.TemplateVariableName.FINAL_DECISION_DATE);
				String ID_NO = (String)hData.get(TemplateBuilderConstant.TemplateVariableName.IDNO);
				String EMAIL = (String)hData.get(TemplateBuilderConstant.TemplateVariableName.EMAIL_PRIMARY);
				String SUBJECT =templateBuilderResponse.getHeaderMsg().replaceAll("[\t\n\r]","");;
				String DETAIL_LINE =templateBuilderResponse.getBodyMsg().replaceAll("[\t\n\r]","");;
				//logger.debug("EMAIL>>"+EMAIL);
				//logger.debug("SUBJECT>>"+SUBJECT);
				//logger.debug("DETAIL_LINE>>"+DETAIL_LINE);
				StringBuilder generateTextFormat = new StringBuilder("");
				
				// KPL: Update LETTER HISTORY table
				String product = (String)hData.get(TemplateBuilderConstant.TemplateVariableName.PRODUCT_TYPE);
				String language = (String)hData.get(TemplateBuilderConstant.TemplateVariableName.LANGUAGE);
				String sendTo = (String)hData.get(TemplateBuilderConstant.TemplateVariableName.SEND_TO);
				String subInterfaceCode = "";
				if (!InfBatchUtil.empty(product) && product.equals(REJECT_LETTER_PDF_PRODUCT_NAME_KPL)) {
					logger.debug("Update LETTER_HISTORY for KPL. Send to => " + sendTo);
					subInterfaceCode = REJECT_LETTER_PDF_EDOC_SUBINTERFACE_CODE;
					LetterHistoryDAO dao = LetterHistoryFactory.getLetterHistoryDAO();
					dao.setEmailProperties(LETTER_NO, sendTo, EMAIL, language, conn);
				}
				else {
					generateTextFormat.append("1");
					generateTextFormat.append(PDF_FIELD_DELIMETER+Formatter.displayText(FINAL_APP_DECISION_DATE));
					generateTextFormat.append(PDF_FIELD_DELIMETER+Formatter.displayText(LETTER_NO));
					generateTextFormat.append(PDF_FIELD_DELIMETER+Formatter.displayText(APPLICATION_NO));
					generateTextFormat.append(PDF_FIELD_DELIMETER+Formatter.displayText(ID_NO));
					generateTextFormat.append(PDF_FIELD_DELIMETER+Formatter.displayText(SEND_TYPE));
					generateTextFormat.append(PDF_FIELD_DELIMETER+Formatter.displayText(EMAIL));
					generateTextFormat.append(PDF_FIELD_DELIMETER+Formatter.displayText(SUBJECT));
					generateTextFormat.append(PDF_FIELD_DELIMETER+Formatter.displayText(DETAIL_LINE));
				}
				rejectLetterInterfaceResponse.setLetterNo(LETTER_NO);
				rejectLetterInterfaceResponse.setLetterType(SEND_TYPE);
				rejectLetterInterfaceResponse.setInterfaceCode(REJECT_LETTER_PDF_INTERFACE_CODE);
				rejectLetterInterfaceResponse.setContentPDF(generateTextFormat.toString());
				rejectLetterInterfaceResponse.setInterfaceObject(rejectLetterPDFdataM);
				rejectLetterInterfaceResponse.setSubInterfaceCode(subInterfaceCode);
				this.insertInfBatchLog(rejectLetterPDFdataM, conn);
				conn.commit();
			}			
		} catch (Exception e) {
			logger.fatal("ERROR",e);
			try {
				conn.rollback();
			} catch (SQLException e1) {
				logger.fatal("ERROR",e1);
			}			
		}finally{
			try{
				conn.close();
			}catch(Exception e){
				logger.fatal("ERROR ",e);
			}
		}
		return rejectLetterInterfaceResponse;
	}
	private void insertInfBatchLog(RejectLetterPDFDataM rejectLeterPDFDataM, Connection conn) throws Exception {
//   	 Connection conn = InfBatchObjectDAO.getConnection();	
   	 try {
   		 ArrayList<String> applicationRecordIds = rejectLeterPDFDataM.getApplicationRecordIds();
   		//InfDAO dao = InfFactory.getInfDAO();
   		 RejectLetterPDFDAO dao = RejectLetterFactory.getRejectLetterPDFDAO();
//   		 conn.setAutoCommit(false);
   		 if(!InfBatchUtil.empty(applicationRecordIds)){
   			 for(String applicationRecordId : applicationRecordIds){
   				 InfBatchLogDataM infBatchLogDataM = new InfBatchLogDataM();
	   				 infBatchLogDataM.setApplicationRecordId(applicationRecordId);
	   				 infBatchLogDataM.setInterfaceCode(REJECT_LETTER_PDF_INTERFACE_CODE);
	   				 infBatchLogDataM.setInterfaceStatus(REJECT_LETTER_INTERFACE_STATUS_COMPLETE);
	   				 infBatchLogDataM.setRefId(rejectLeterPDFDataM.getLetterNo());
	   				 infBatchLogDataM.setSystem01(rejectLeterPDFDataM.getLetterType());
	   				 infBatchLogDataM.setSystem02(rejectLeterPDFDataM.getLanguage());
	   				 infBatchLogDataM.setSystem03(rejectLeterPDFDataM.getSendTo());
	   				 infBatchLogDataM.setApplicationGroupId(rejectLeterPDFDataM.getApplicationGroupId());
	   				 infBatchLogDataM.setLifeCycle(rejectLeterPDFDataM.getLifeCycle());
	   				 infBatchLogDataM.setInterfaceDate(InfBatchProperty.getTimestamp());
	   				 infBatchLogDataM.setCreateDate(InfBatchProperty.getTimestamp());
   				 dao.insertInfBatchLog(infBatchLogDataM, conn);
   			 }
   		 }
//   		 conn.commit();
		}catch (Exception e) {
			logger.fatal("ERROR",e);
//			try {
//				conn.rollback();
//			} catch (SQLException e1) {
//				logger.fatal("ERROR",e1);
//			}			
//		}finally{
//			try{
//				InfBatchObjectDAO.closeConnection(conn);
//			}catch(Exception e){
//				logger.fatal("ERROR ",e);
//			}
			throw e;
		}
    }
}
