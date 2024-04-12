package com.eaf.inf.batch.ulo.letter.reject.template;

import java.util.ArrayList;
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
import com.eaf.inf.batch.ulo.notification.dao.NotificationDAO;
import com.eaf.inf.batch.ulo.notification.dao.NotificationFactory;
import com.eaf.inf.batch.ulo.notification.model.VCEmpInfoDataM;

public class RejectEmailPDFTemplateSeller extends TemplateBuilderHelper{
	private static transient Logger logger = Logger.getLogger(RejectEmailPDFTemplateSeller.class);
	String PERSONAL_TYPE_APPLICANT= InfBatchProperty.getInfBatchConfig("REJECT_LETTER_PERSONAL_TYPE_APPLICANT");
	String PERSONAL_TYPE_SUPPLEMENTARY= InfBatchProperty.getInfBatchConfig("REJECT_LETTER_PERSONAL_TYPE_SUPPLEMENTARY");
	String REJECT_LETTER_PDF_PRODUCT_NAME_KPL = InfBatchProperty.getInfBatchConfig("REJECT_LETTER_PDF_PRODUCT_NAME_KPL");
	@Override
	public TemplateVariableDataM getTemplateVariable() {
		String REJECT_LETTER_PDF_FUNCTION_TYPE_WEALTH_SUP_REJECT = InfBatchProperty.getInfBatchConfig("REJECT_LETTER_PDF_FUNCTION_TYPE_WEALTH_SUP_REJECT");
		TemplateVariableDataM templateVariable = new TemplateVariableDataM();
		TemplateBuilderRequest templateBuilderRequest = getTemplateBuilderRequest();
		RejectLetterPDFDataM rejectLetterPdf = (RejectLetterPDFDataM)templateBuilderRequest.getRequestObject();
		try {
			
			NotificationDAO  daoNotifiaction = NotificationFactory.getNotificationDAO();
			ArrayList<String> saleList = new ArrayList<String>();
			saleList.add(rejectLetterPdf.getSaleId());
			ArrayList<VCEmpInfoDataM>  vcEmplist = daoNotifiaction.selectVCEmpList(saleList);
			VCEmpInfoDataM vcEmpInfoDataM = new VCEmpInfoDataM();
			if(vcEmplist.size()>0){
				vcEmpInfoDataM = vcEmplist.get(0);
			}
			templateVariable.setUniqueId(rejectLetterPdf.getApplicationGroupId());
			RejectLetterPDFDAO dao = RejectLetterFactory.getRejectLetterPDFDAO();
//			ArrayList<RejectLetterPDFPersonalInfoDataM> pdfPersonalInfos  = dao.selectRejectPDFTemplateDatas(rejectTemplatePDFDataM);
//			RejectLetterPDFPersonalInfoDataM rejectLetterPDFPersonalInfoDataM = GenerateLetterUtil.getPDFLetterConditionCase(pdfPersonalInfos, rejectTemplatePDFDataM.getTemplateId());
			RejectLetterPDFPersonalInfoDataM rejectLetterPDFPersonalInfo = rejectLetterPdf.getRejectedPersonalInfo();
			ArrayList<RejectTemplateVariableDataM> rejectTemplateVariables = new ArrayList<RejectTemplateVariableDataM>();
			logger.debug("rejectLetterPDFPersonalInfo : "+rejectLetterPDFPersonalInfo);
			logger.debug("functionType : "+rejectLetterPdf.getFunctionType());
			if(!InfBatchUtil.empty(rejectLetterPDFPersonalInfo)){
				rejectTemplateVariables = dao.getRejectPDFTemplate(rejectLetterPdf,rejectLetterPDFPersonalInfo);
			}else if(REJECT_LETTER_PDF_FUNCTION_TYPE_WEALTH_SUP_REJECT.equals(rejectLetterPdf.getFunctionType())){
				ArrayList<RejectLetterPDFPersonalInfoDataM> personalInfos = rejectLetterPdf.getPersonalInfos();
				RejectLetterPDFPersonalInfoDataM supPersonalInfo = GenerateLetterUtil.getPDFPersonalInfoByType(personalInfos,PERSONAL_TYPE_SUPPLEMENTARY);
				rejectTemplateVariables = dao.getRejectPDFTemplate(rejectLetterPdf,supPersonalInfo);
			}
			logger.debug("rejectTemplateVariables : "+rejectTemplateVariables);
			HashMap<String, Object> hMapping = new HashMap<String, Object>();
			 if(!InfBatchUtil.empty(rejectTemplateVariables)){
				 RejectTemplateVariableDataM rejectTemplateVariable = getRejectTemplateVariableDataM(rejectTemplateVariables);
				 if(!InfBatchUtil.empty(rejectTemplateVariable)){
					//hMapping.put(TemplateBuilderConstant.TemplateVariableName.CUSTOMER_NAME, rejectTemplateVariable.getPersonalNameTh());
					 hMapping.put(TemplateBuilderConstant.TemplateVariableName.CUSTOMER_NAME, RejectLetterPDFUtil.generatePDFCustomerName(rejectLetterPdf.getSendTo(), rejectTemplateVariable.getPersonalNameTh(), rejectTemplateVariable.getSellerCustomerNameTh()));
					 hMapping.put(TemplateBuilderConstant.TemplateVariableName.IDNO, rejectTemplateVariable.getIdNo());
					 hMapping.put(TemplateBuilderConstant.TemplateVariableName.FINAL_DECISION_DATE, rejectTemplateVariable.getFinalDecisionDate());
					 hMapping.put(TemplateBuilderConstant.TemplateVariableName.EMAIL_PRIMARY, vcEmpInfoDataM.getEmail());
					 hMapping.put(TemplateBuilderConstant.TemplateVariableName.PRODUCT_NAME,rejectTemplateVariable.getProductName());
					 hMapping.put(TemplateBuilderConstant.TemplateVariableName.PRODUCT_NAME_EN,rejectTemplateVariable.getProductNameEn());
					 hMapping.put(TemplateBuilderConstant.TemplateVariableName.PRODUCT_NAME_TH,rejectTemplateVariable.getProductNameTh());
					 hMapping.put(TemplateBuilderConstant.TemplateVariableName.PRODUCT_NAME_TH_EN,rejectTemplateVariable.getProductNameThEn());
					 hMapping.put(TemplateBuilderConstant.TemplateVariableName.CUSTOMER_NAME, rejectTemplateVariable.getPersonalNameTh());
					 hMapping.put(TemplateBuilderConstant.TemplateVariableName.APPLICATION_NO, rejectTemplateVariable.getApplicationNo());
					 hMapping.put(TemplateBuilderConstant.TemplateVariableName.REFERENCE_NO, rejectTemplateVariable.getApplicationGroupNo());
					 // KPL: Populate additional template variable for KPL
					 String product = rejectTemplateVariable.getProductType();
					 if (!InfBatchUtil.empty(product) && product.equals(REJECT_LETTER_PDF_PRODUCT_NAME_KPL)) {
						 logger.debug("Additional KPL template variables:");
						 logger.debug("Product =>"+product);
						 logger.debug("Send to =>"+rejectLetterPdf.getSendTo());
						 logger.debug("Language=>"+templateBuilderRequest.getLanguage());
						 hMapping.put(TemplateBuilderConstant.TemplateVariableName.LANGUAGE, templateBuilderRequest.getLanguage());
						 hMapping.put(TemplateBuilderConstant.TemplateVariableName.PRODUCT_TYPE, product);
						 hMapping.put(TemplateBuilderConstant.TemplateVariableName.SEND_TO, rejectLetterPdf.getSendTo());
					 }
				 }
				 templateVariable.setTemplateVariable(hMapping);
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

}
