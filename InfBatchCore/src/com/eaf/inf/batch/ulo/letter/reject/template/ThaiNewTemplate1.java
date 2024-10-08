package com.eaf.inf.batch.ulo.letter.reject.template;

import java.util.ArrayList;
import java.util.HashMap;

import org.apache.log4j.Logger;

import com.eaf.core.ulo.common.cache.InfBatchProperty;
import com.eaf.core.ulo.common.display.Formatter;
import com.eaf.core.ulo.common.util.InfBatchUtil;
import com.eaf.core.ulo.common.util.Util;
import com.eaf.core.ulo.service.template.cont.TemplateBuilderConstant;
import com.eaf.core.ulo.service.template.inf.TemplateBuilderHelper;
import com.eaf.core.ulo.service.template.model.TemplateBuilderRequest;
import com.eaf.core.ulo.service.template.model.TemplateMasterDataM;
import com.eaf.core.ulo.service.template.model.TemplateVariableDataM;
import com.eaf.inf.batch.ulo.letter.reject.dao.RejectLetterDAO;
import com.eaf.inf.batch.ulo.letter.reject.dao.RejectLetterFactory;
import com.eaf.inf.batch.ulo.letter.reject.inf.NotifyRejectLetterInf;
import com.eaf.inf.batch.ulo.letter.reject.model.RejectLetterProcessDataM;
import com.eaf.inf.batch.ulo.letter.reject.model.RejectTemplateVariableDataM;
import com.eaf.inf.batch.ulo.letter.reject.model.TemplateReasonCodeDetailDataM;
import com.eaf.inf.batch.ulo.letter.reject.util.RejectLetterUtil;
import com.eaf.orig.ulo.constant.MConstant;
import com.eaf.service.common.util.ServiceUtil;

public class ThaiNewTemplate1 extends TemplateBuilderHelper {
	private static transient Logger logger = Logger.getLogger(ThaiNewTemplate1.class);
	String PERSONAL_TYPE_APPLICANT= InfBatchProperty.getInfBatchConfig("REJECT_LETTER_PERSONAL_TYPE_APPLICANT");
	String PERSONAL_TYPE_SUPPLEMENTARY= InfBatchProperty.getInfBatchConfig("REJECT_LETTER_PERSONAL_TYPE_SUPPLEMENTARY");
	String REJECT_LETTER_CONJUNCTION_AND_TH= InfBatchProperty.getInfBatchConfig("REJECT_LETTER_CONJUNCTION_AND_TH");
	String REJECT_LETTER_CONJUNCTION_FOR_TH= InfBatchProperty.getInfBatchConfig("REJECT_LETTER_CONJUNCTION_FOR_TH");
	String REJECT_LETTER_PRODUCT_NAME_CC = InfBatchProperty.getInfBatchConfig("REJECT_LETTER_PRODUCT_NAME_CC");	
	String REJECT_LETTER_PRODUCT_NAME_KPL =InfBatchProperty.getInfBatchConfig("REJECT_LETTER_PRODUCT_NAME_KPL");	
	String REJECT_LETTER_PRODUCT_NAME_KEC = InfBatchProperty.getInfBatchConfig("REJECT_LETTER_PRODUCT_NAME_KEC");
	@Override
	public TemplateVariableDataM getTemplateVariable() {
		TemplateVariableDataM templateVariable = new TemplateVariableDataM();
		TemplateBuilderRequest templateBuilderRequest = getTemplateBuilderRequest();
		RejectLetterProcessDataM rejectLetterProcess = (RejectLetterProcessDataM)templateBuilderRequest.getRequestObject();
		ArrayList<TemplateReasonCodeDetailDataM> templateReasonCodes= rejectLetterProcess.getTemplateReasonCodes();
		TemplateMasterDataM templateMaster = getTemplateMaster();
		try {
			RejectLetterDAO  dao = RejectLetterFactory.getRejectLetterDAO();
			ArrayList<RejectTemplateVariableDataM>  rejectTemplateVariables  = dao.getRejectTemplate1Values(rejectLetterProcess);
			logger.debug("rejectTemplateVariables.size>>"+rejectTemplateVariables.size());
			HashMap<String, Object> hData = new HashMap<String, Object>();
			HashMap<String, Object> hSorting = new HashMap<String, Object>();
			if(!InfBatchUtil.empty(rejectTemplateVariables)){
				if(!ServiceUtil.empty(rejectLetterProcess.getLanguage()) && !rejectLetterProcess.getLanguage().equals(RejectLetterUtil.TH)){
					rejectLetterProcess.setLanguage(RejectLetterUtil.EN);
				}
				HashMap<String, String>  hCallCenterNo = dao.getContactCallCenterNoProduct(templateMaster.getProducts(),rejectLetterProcess.getLanguage());
				logger.debug("hCallCenterNo>>"+hCallCenterNo);
				RejectTemplateVariableDataM rejectTemplateVariable = getRejectTemplateVariableDataM(rejectTemplateVariables,hCallCenterNo);
				String className = InfBatchProperty.getInfBatchConfig("REJECT_LETTER_TEMPLATE_VALIDATION");
				logger.debug("className : "+className);
				NotifyRejectLetterInf notify = (NotifyRejectLetterInf)Class.forName(className).newInstance();
				if(notify.validateTaskTransaction(rejectTemplateVariable)){
					hData.put(TemplateBuilderConstant.TemplateVariableName.APPLICATION_NO, Formatter.displayText(rejectTemplateVariable.getApplicationGroupNo()));
					hData.put(TemplateBuilderConstant.TemplateVariableName.CUSTOMER_NAME, Formatter.displayText(rejectTemplateVariable.getTitleName())+ " "+ Formatter.displayText(rejectTemplateVariable.getPersonalNameTh()));
					hData.put(TemplateBuilderConstant.TemplateVariableName.ADDRESS_LINE_1, Formatter.displayText(rejectTemplateVariable.getAddressLine1()));
					hData.put(TemplateBuilderConstant.TemplateVariableName.ADDRESS_LINE_2, Formatter.displayText(rejectTemplateVariable.getAddressLine2()));
					hData.put(TemplateBuilderConstant.TemplateVariableName.FINAL_DECISION_DATE, Formatter.displayText(rejectTemplateVariable.getFinalDecisionDate()));
					hData.put(TemplateBuilderConstant.TemplateVariableName.PRODUCT_NAME, Formatter.displayText(rejectTemplateVariable.getProductName()));
					hData.put(TemplateBuilderConstant.TemplateVariableName.PRODUCT_NAME_TH_EN, Formatter.displayText(rejectTemplateVariable.getProductNameThEn()));
					hData.put(TemplateBuilderConstant.TemplateVariableName.IDNO, Formatter.displayText(rejectTemplateVariable.getIdNo()));
					hData.put(TemplateBuilderConstant.TemplateVariableName.PRODUCT_TYPE, Formatter.displayText(rejectTemplateVariable.getProductType()));
					hData.put(TemplateBuilderConstant.TemplateVariableName.APPLICATION_RECORD_ID,rejectTemplateVariable.getAllApplicationRecordIds());			
					hData.put(TemplateBuilderConstant.TemplateVariableName.CONTACT_CENTER_NO_PRODUCT,Formatter.displayText(rejectTemplateVariable.getContactCenterNoProduct()));
					hData.put(TemplateBuilderConstant.TemplateVariableName.CONTACT_CENTER_NO_CC,Formatter.displayText(rejectTemplateVariable.getContactCenterNoCC()));
					hData.put(TemplateBuilderConstant.TemplateVariableName.CONTACT_CENTER_NO_KPL,Formatter.displayText(rejectTemplateVariable.getContactCenterNoKPL()));
					hData.put(TemplateBuilderConstant.TemplateVariableName.CONTACT_CENTER_NO_KEC,Formatter.displayText(rejectTemplateVariable.getContactCenterNoKEC()));
					hData.put(TemplateBuilderConstant.TemplateVariableName.EMAIL_PRIMARY,Formatter.displayText(rejectTemplateVariable.getEmailPrimary()));
					hData.put(TemplateBuilderConstant.TemplateVariableName.DOCUMENT_LIST,Formatter.displayText(RejectLetterUtil.getDocumentList(rejectTemplateVariable)));
					hSorting.put(TemplateBuilderConstant.TemplateSortingName.ZIPCODE, Formatter.displayText(rejectTemplateVariable.getZipcode()));
					// For EDOCGen
					hData.put(TemplateBuilderConstant.TemplateVariableName.IDNO, Formatter.displayText(rejectTemplateVariable.getIdNo()));
					hData.put(TemplateBuilderConstant.TemplateVariableName.LANGUAGE,Formatter.displayText(rejectLetterProcess.getLanguage()));
					hData.put(TemplateBuilderConstant.TemplateVariableName.CIS_NO,Formatter.displayText(rejectTemplateVariable.getCisNo()));
					hData.put(TemplateBuilderConstant.TemplateVariableName.EDOC_LETTER_TEMPLATE,Formatter.displayText(templateMaster.getPostScript1()));
					hData.put(TemplateBuilderConstant.TemplateVariableName.LETTER_CHANNEL, Formatter.displayText(rejectTemplateVariable.getLetterChannel()));
					if (rejectLetterProcess.isEmail() && !Util.empty(rejectTemplateVariable.getEmailPrimary())) 
					{
						hData.put(TemplateBuilderConstant.TemplateVariableName.SEND_METHOD, "3");
						hData.put(TemplateBuilderConstant.TemplateVariableName.ATTACH_FILE, "1");
						hData.put(TemplateBuilderConstant.TemplateVariableName.EDOC_EMAIL_TEMPLATE,Formatter.displayText(templateMaster.getPostScript2()));
					}
					else {
						hData.put(TemplateBuilderConstant.TemplateVariableName.SEND_METHOD, "1");
						hData.put(TemplateBuilderConstant.TemplateVariableName.ATTACH_FILE, "0");
					}
					hData.put(TemplateBuilderConstant.TemplateVariableName.MASTER_OR_COPY,MConstant.FLAG_M);
					hData.put(TemplateBuilderConstant.TemplateVariableName.TH_FIRST_NAME, Formatter.displayText(rejectTemplateVariable.getThFirstName()));
					hData.put(TemplateBuilderConstant.TemplateVariableName.TH_MID_NAME, Formatter.displayText(rejectTemplateVariable.getThMidName()));
					hData.put(TemplateBuilderConstant.TemplateVariableName.TH_LAST_NAME, Formatter.displayText(rejectTemplateVariable.getThLastName()));
				}
			}
			templateVariable.setTemplateVariable(hData);
			templateVariable.setSortingVariable(hSorting);
			templateVariable.setUniqueId(rejectLetterProcess.getApplicationGroupId());
			templateVariable.getUniqueId().setLifeCycle(rejectLetterProcess.getLifeCycle());
		} catch (Exception e) {
			logger.fatal("ERROR");
		}
		return templateVariable;
	}

	private RejectTemplateVariableDataM getRejectTemplateVariableDataM(ArrayList<RejectTemplateVariableDataM>  rejectTemplateVariables, HashMap<String, String>  hCallCenterNo){
		ArrayList<RejectTemplateVariableDataM>   rejectTemplateVariableApplicantList = new ArrayList<RejectTemplateVariableDataM>  ();
		ArrayList<RejectTemplateVariableDataM> rejectTemplateVariableSupplementaryList = new ArrayList<RejectTemplateVariableDataM>();
		StringBuilder  productName= new StringBuilder("");
		ArrayList<String> allApplicationRecordIDs = new ArrayList<String>();
		StringBuilder callCenterNoCC = new StringBuilder("");
		StringBuilder callCenterNoKEC = new StringBuilder("");
		StringBuilder callCenterNoKPL = new StringBuilder("");
		String CC_CALL_NO =  hCallCenterNo.get(REJECT_LETTER_PRODUCT_NAME_CC);
		String KEC_CALL_NO = hCallCenterNo.get(REJECT_LETTER_PRODUCT_NAME_KEC);
		String KPL_CALL_NO = hCallCenterNo.get(REJECT_LETTER_PRODUCT_NAME_KPL);
		int countNewLine = 0;
		countNewLine = setCallCenterNo(CC_CALL_NO, callCenterNoCC, countNewLine);
		countNewLine = setCallCenterNo(KEC_CALL_NO, callCenterNoKEC, countNewLine);
		countNewLine = setCallCenterNo(KPL_CALL_NO, callCenterNoKPL, countNewLine);
		String kcc_slast="";
		String kec_slast="";
		String kpl_slast="";
		try {
			int count=0;
			ArrayList<String> personalTypes = new ArrayList<String>();
			if(!InfBatchUtil.empty(rejectTemplateVariables)){
				for(RejectTemplateVariableDataM rejectVariable : rejectTemplateVariables){
					allApplicationRecordIDs.add(rejectVariable.getApplicationRecordId());
					String productNameDesc =rejectVariable.getProductName();
					if(-1==productName.indexOf(productNameDesc)){
						count++;
						if(count==rejectTemplateVariables.size() && rejectTemplateVariables.size()>1){
							productName.append(REJECT_LETTER_CONJUNCTION_AND_TH+productNameDesc);
						}else{
							productName.append(productNameDesc+" ");
						}
					}
					if(REJECT_LETTER_PRODUCT_NAME_CC.equals(rejectVariable.getBusinessClassProductType())){
						//callCenterNoCC.append(kcc_slast+productNameDesc);
						callCenterNoCC.append(kcc_slast)
							.append(" ").append(productNameDesc);
						kcc_slast="/";
					}else if(REJECT_LETTER_PRODUCT_NAME_KEC.equals(rejectVariable.getBusinessClassProductType())){
						//callCenterNoKEC.append(kec_slast+productNameDesc);
						callCenterNoKEC.append(kec_slast)
							.append(" ").append(productNameDesc);
						kec_slast="/";
					}else if(REJECT_LETTER_PRODUCT_NAME_KPL.equals(rejectVariable.getBusinessClassProductType())){
						//callCenterNoKPL.append(kpl_slast+productNameDesc);
						callCenterNoKPL.append(kpl_slast)
							.append(" ").append(productNameDesc);
						kpl_slast="/";
					}
										
					personalTypes.add(rejectVariable.getPersonalType());
					if(PERSONAL_TYPE_APPLICANT.equals(rejectVariable.getPersonalType())){
						rejectTemplateVariableApplicantList.add(rejectVariable);
					}else if(PERSONAL_TYPE_SUPPLEMENTARY.equals(rejectVariable.getPersonalType())){
						rejectTemplateVariableSupplementaryList.add(rejectVariable);
					}
				}
				
				RejectTemplateVariableDataM rejectTemplateVal =   new RejectTemplateVariableDataM();
				
				if(personalTypes.contains(PERSONAL_TYPE_APPLICANT) && rejectTemplateVariableApplicantList.size()>0){
					rejectTemplateVal = rejectTemplateVariableApplicantList.get(0);
				}else{
					rejectTemplateVal = rejectTemplateVariableSupplementaryList.get(0);				
				}
				rejectTemplateVal.setProductName(productName.toString());
				rejectTemplateVal.setAllApplicationRecordIds(allApplicationRecordIDs);
				rejectTemplateVal.setContactCenterNoCC(CC_CALL_NO);
				rejectTemplateVal.setContactCenterNoKEC(KEC_CALL_NO);
				rejectTemplateVal.setContactCenterNoKPL(KPL_CALL_NO);
				rejectTemplateVal.setContactCenterNoProduct(callCenterNoCC.toString()+callCenterNoKEC.toString()+callCenterNoKPL.toString());
				return rejectTemplateVal;
			}
		} catch (Exception e) {
			logger.fatal("ERROR",e);
		}
		return null;
		}
	
		private int  setCallCenterNo (String callCenterNo,StringBuilder callcenter, int countNewLine){
		try {
			if(!InfBatchUtil.empty(callCenterNo)){
				//callcenter.append(RejectLetterUtil.getNewLine()+RejectLetterUtil.getIndent(12));
				if(countNewLine>0){
					callcenter.append(RejectLetterUtil.getNewLine())
						.append(RejectLetterUtil.getIndent(12));
				}
				//callcenter.append(callCenterNo+RejectLetterUtil.getIndent(2)+REJECT_LETTER_CONJUNCTION_FOR_TH+RejectLetterUtil.getIndent(1));
				callcenter.append(callCenterNo+RejectLetterUtil.getIndent(2)+REJECT_LETTER_CONJUNCTION_FOR_TH);
				countNewLine++;
			}
		} catch (Exception e) {
			logger.fatal("ERROR",e);
		}
		return countNewLine;
	}
}
