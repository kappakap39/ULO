package com.eaf.inf.batch.ulo.letter.reject.template;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;

import org.apache.log4j.Logger;

import com.eaf.core.ulo.common.cache.InfBatchProperty;
import com.eaf.core.ulo.common.display.Formatter;
import com.eaf.core.ulo.common.util.InfBatchUtil;
import com.eaf.core.ulo.service.template.cont.TemplateBuilderConstant;
import com.eaf.core.ulo.service.template.inf.TemplateBuilderHelper;
import com.eaf.core.ulo.service.template.model.TemplateBuilderRequest;
import com.eaf.core.ulo.service.template.model.TemplateVariableDataM;
import com.eaf.inf.batch.ulo.letter.reject.dao.RejectLetterFactory;
import com.eaf.inf.batch.ulo.letter.reject.dao.RejectLetterPDFDAO;
import com.eaf.inf.batch.ulo.letter.reject.model.RejectLetterPDFDataM;
import com.eaf.inf.batch.ulo.letter.reject.model.RejectLetterPDFPersonalInfoDataM;
import com.eaf.inf.batch.ulo.letter.reject.model.RejectTemplateVariableDataM;
import com.eaf.inf.batch.ulo.letter.reject.template.product.GenerateLetterUtil;
import com.eaf.inf.batch.ulo.letter.reject.util.RejectLetterPDFUtil;
import com.eaf.inf.batch.ulo.letter.reject.util.RejectLetterUtil;

public class RejectEmailPDFTemplateThaiIncrease extends TemplateBuilderHelper {
	private static transient Logger logger = Logger.getLogger(RejectEmailPDFTemplateThaiIncrease.class);
	String PERSONAL_TYPE_APPLICANT= InfBatchProperty.getInfBatchConfig("REJECT_LETTER_PERSONAL_TYPE_APPLICANT");
	String PERSONAL_TYPE_SUPPLEMENTARY= InfBatchProperty.getInfBatchConfig("REJECT_LETTER_PERSONAL_TYPE_SUPPLEMENTARY");
	String REJECT_LETTER_PDF_PRODUCT_NAME_CC = InfBatchProperty.getInfBatchConfig("REJECT_LETTER_PDF_PRODUCT_NAME_CC");
	String REJECT_LETTER_PDF_PRODUCT_NAME_KPL = InfBatchProperty.getInfBatchConfig("REJECT_LETTER_PDF_PRODUCT_NAME_KPL");
	String REJECT_LETTER_PDF_PRODUCT_NAME_KEC = InfBatchProperty.getInfBatchConfig("REJECT_LETTER_PDF_PRODUCT_NAME_KEC");
	String REJECT_LETTER_PDF_CONJUNCTION_AND_TH = InfBatchProperty.getInfBatchConfig("REJECT_LETTER_PDF_CONJUNCTION_AND_TH");
	String REJECT_LETTER_PDF_CONJUNCTION_AND_EN = InfBatchProperty.getInfBatchConfig("REJECT_LETTER_PDF_CONJUNCTION_AND_EN");
	String REJECT_LETTER_PDF_CONJUNCTION_FOR_TH = InfBatchProperty.getInfBatchConfig("REJECT_LETTER_PDF_CONJUNCTION_FOR_TH");
	String REJECT_LETTER_PDF_CONJUNCTION_FOR_EN = InfBatchProperty.getInfBatchConfig("REJECT_LETTER_PDF_CONJUNCTION_FOR_EN");
	@Override
	public TemplateVariableDataM getTemplateVariable() {
		TemplateVariableDataM templateVariable = new TemplateVariableDataM();
		TemplateBuilderRequest templateBuilderRequest = getTemplateBuilderRequest();
		RejectLetterPDFDataM rejectTemplatePDFDataM = (RejectLetterPDFDataM)templateBuilderRequest.getRequestObject();
		try {
			templateVariable.setUniqueId(rejectTemplatePDFDataM.getApplicationGroupId());
			RejectLetterPDFDAO dao = RejectLetterFactory.getRejectLetterPDFDAO();
//			ArrayList<RejectLetterPDFPersonalInfoDataM> pdfPersonalInfos  = dao.selectRejectPDFTemplateDatas(rejectTemplatePDFDataM);
//			RejectLetterPDFPersonalInfoDataM rejectLetterPDFPersonalInfoDataM = GenerateLetterUtil.getPDFLetterConditionCase(pdfPersonalInfos, rejectTemplatePDFDataM.getTemplateId());
			RejectLetterPDFPersonalInfoDataM rejectLetterPDFPersonalInfoDataM = rejectTemplatePDFDataM.getRejectedPersonalInfo();
			if(null!=rejectLetterPDFPersonalInfoDataM){
			 ArrayList<RejectTemplateVariableDataM> rejectTemplateVariables = dao.getRejectPDFTemplate(rejectTemplatePDFDataM,rejectLetterPDFPersonalInfoDataM);
			 HashMap<String, Object> hMapping = new HashMap<String, Object>();
				 
				 RejectTemplateVariableDataM rejectTemplateVariable = getRejectTemplateVariableDataM(rejectTemplateVariables);
				 if(!InfBatchUtil.empty(rejectTemplateVariable)){
					 String website = "";
					 HashMap<String,String> hCallCenterNo = new HashMap<String,String>(); 
					 if(!InfBatchUtil.empty(rejectTemplateVariables)){
						 String businessClassId = rejectTemplateVariable.getBusinessClassId();
						 if(!InfBatchUtil.empty(businessClassId)){
							 String productType[] =  businessClassId.split("\\_");
							 if(!InfBatchUtil.empty(productType)){
								 ArrayList<String> productTypeList = new ArrayList<String>(Arrays.asList(new String[]{productType[0]}));
								 logger.debug("productTypeList : "+productTypeList);
								 logger.debug("Language : "+templateBuilderRequest.getLanguage());
								 website = RejectLetterUtil.getProductWebsite(productTypeList);
								 hCallCenterNo = dao.getContactCallCenterNoProduct(productTypeList,templateBuilderRequest.getLanguage());
							 }
						 }
						 logger.debug("website : "+website);
						 logger.debug("hCallCenterNo : "+hCallCenterNo);
						 setCallCenterInfo(rejectTemplateVariable, hCallCenterNo);
						 //hMapping.put(TemplateBuilderConstant.TemplateVariableName.CUSTOMER_NAME, rejectTemplateVariable.getPersonalNameEn());
						 hMapping.put(TemplateBuilderConstant.TemplateVariableName.CUSTOMER_NAME, RejectLetterPDFUtil.generatePDFCustomerName(rejectTemplatePDFDataM.getSendTo(), rejectTemplateVariable.getPersonalNameEn(), rejectTemplateVariable.getSellerCustomerNameTh()));
						 hMapping.put(TemplateBuilderConstant.TemplateVariableName.IDNO, rejectTemplateVariable.getIdNo());
						 hMapping.put(TemplateBuilderConstant.TemplateVariableName.FINAL_DECISION_DATE, rejectTemplateVariable.getFinalDecisionDate());
						 hMapping.put(TemplateBuilderConstant.TemplateVariableName.EMAIL_PRIMARY, rejectTemplateVariable.getEmailPrimary());
						 hMapping.put(TemplateBuilderConstant.TemplateVariableName.PRODUCT_NAME,rejectTemplateVariable.getProductName());
						 hMapping.put(TemplateBuilderConstant.TemplateVariableName.PRODUCT_NAME_TH_EN,rejectTemplateVariable.getProductNameThEn());
						 hMapping.put(TemplateBuilderConstant.TemplateVariableName.WEBSITE_BY_PRODUCT,website);
						 hMapping.put(TemplateBuilderConstant.TemplateVariableName.CONTACT_CENTER_NO_PRODUCT,Formatter.displayText(rejectTemplateVariable.getContactCenterNoProduct()));
						 hMapping.put(TemplateBuilderConstant.TemplateVariableName.CONTACT_CENTER_NO_CC,Formatter.displayText(rejectTemplateVariable.getContactCenterNoCC()));
						 hMapping.put(TemplateBuilderConstant.TemplateVariableName.CONTACT_CENTER_NO_KPL,Formatter.displayText(rejectTemplateVariable.getContactCenterNoKPL()));
						 hMapping.put(TemplateBuilderConstant.TemplateVariableName.CONTACT_CENTER_NO_KEC,Formatter.displayText(rejectTemplateVariable.getContactCenterNoKEC()));
					 }
					 templateVariable.setTemplateVariable(hMapping);
			 }
			}
		} catch (Exception e) {
			logger.fatal("ERROR",e);
		}
		return templateVariable;
	}
	private RejectTemplateVariableDataM getRejectTemplateVariableDataM(ArrayList<RejectTemplateVariableDataM>  rejectTemplateVariables){
//		RejectTemplateVariableDataM rejectTemplateVariableApplicant = new RejectTemplateVariableDataM();
//		RejectTemplateVariableDataM rejectTemplateVariableSupplementary = new RejectTemplateVariableDataM();
		RejectTemplateVariableDataM rejectTemplateVariableApplicant = null;
		RejectTemplateVariableDataM rejectTemplateVariableSupplementary = null;
		try {
			if(!InfBatchUtil.empty(rejectTemplateVariables)){
				for(RejectTemplateVariableDataM rejectVariable : rejectTemplateVariables){
					if(PERSONAL_TYPE_APPLICANT.equals(rejectVariable.getPersonalType())){
						rejectTemplateVariableApplicant =rejectVariable;
					}else if(PERSONAL_TYPE_SUPPLEMENTARY.equals(rejectVariable.getPersonalType())){
						rejectTemplateVariableSupplementary=rejectVariable;
					}
				}
			}
			
			if(InfBatchUtil.empty(rejectTemplateVariableApplicant)){
				return rejectTemplateVariableSupplementary;
			}
		} catch (Exception e) {
			logger.fatal("ERROR",e);
		}
		return rejectTemplateVariableApplicant;
	}
	private void setCallCenterInfo(RejectTemplateVariableDataM rejectTemplateVariable,HashMap<String,String> hCallCenterNo){
		StringBuilder callCenterNoCC = new StringBuilder("");
		StringBuilder callCenterNoKEC = new StringBuilder("");
		StringBuilder callCenterNoKPL = new StringBuilder("");
		String CC_CALL_NO =  hCallCenterNo.get(REJECT_LETTER_PDF_PRODUCT_NAME_CC);
		String KEC_CALL_NO = hCallCenterNo.get(REJECT_LETTER_PDF_PRODUCT_NAME_KEC);
		String KPL_CALL_NO = hCallCenterNo.get(REJECT_LETTER_PDF_PRODUCT_NAME_KPL);
		setCallCenterNo(CC_CALL_NO, callCenterNoCC);
		setCallCenterNo(KEC_CALL_NO, callCenterNoKEC);
		setCallCenterNo(KPL_CALL_NO, callCenterNoKPL);
		String kcc_slast="";
		String kec_slast="";
		String kpl_slast="";
		String productName = rejectTemplateVariable.getProductName();
		String productType = GenerateLetterUtil.gerProductType(rejectTemplateVariable.getBusinessClassId());
		if(REJECT_LETTER_PDF_PRODUCT_NAME_CC.equals(productType)){
			callCenterNoCC.append(kcc_slast)
				.append(" ").append(REJECT_LETTER_PDF_CONJUNCTION_FOR_TH).append(" ")
				.append(productName);
			kcc_slast="/";
		}else if(REJECT_LETTER_PDF_PRODUCT_NAME_KEC.equals(productType)){
			callCenterNoKEC.append(kec_slast)
				.append(" ").append(REJECT_LETTER_PDF_CONJUNCTION_FOR_TH).append(" ")
				.append(productName);
			kec_slast="/";
		}else if(REJECT_LETTER_PDF_PRODUCT_NAME_KPL.equals(productType)){
			callCenterNoKPL.append(kpl_slast)
				.append(" ").append(REJECT_LETTER_PDF_CONJUNCTION_FOR_TH).append(" ")
				.append(productName);
			kpl_slast="/";
		}
		rejectTemplateVariable.setContactCenterNoCC(CC_CALL_NO);
		rejectTemplateVariable.setContactCenterNoKEC(KEC_CALL_NO);
		rejectTemplateVariable.setContactCenterNoKPL(KPL_CALL_NO);
		rejectTemplateVariable.setContactCenterNoProduct(callCenterNoCC.toString()+callCenterNoKEC.toString()+callCenterNoKPL.toString());
	}
	private void setCallCenterNo (String callCenterNo,StringBuilder callcenter){
		try {
			if(!InfBatchUtil.empty(callCenterNo)){
				callcenter.append(RejectLetterUtil.getIndent(12))
					.append(callCenterNo);
			}
		} catch (Exception e) {
			logger.fatal("ERROR",e);
		}
	}
}
