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
import com.eaf.core.ulo.service.template.model.TemplateMasterDataM;
import com.eaf.core.ulo.service.template.model.TemplateVariableDataM;
import com.eaf.inf.batch.ulo.letter.reject.dao.RejectLetterDAO;
import com.eaf.inf.batch.ulo.letter.reject.dao.RejectLetterFactory;
import com.eaf.inf.batch.ulo.letter.reject.inf.NotifyRejectLetterInf;
import com.eaf.inf.batch.ulo.letter.reject.model.RejectLetterProcessDataM;
import com.eaf.inf.batch.ulo.letter.reject.model.RejectTemplateVariableDataM;
import com.eaf.inf.batch.ulo.letter.reject.model.TemplateReasonCodeDetailDataM;
import com.eaf.inf.batch.ulo.letter.reject.util.RejectLetterUtil;
import com.eaf.service.common.util.ServiceUtil;

public class IncreaseTemplate5 extends TemplateBuilderHelper {
	private static transient Logger logger = Logger.getLogger(IncreaseTemplate5.class);
	String PERSONAL_TYPE_APPLICANT= InfBatchProperty.getInfBatchConfig("REJECT_LETTER_PERSONAL_TYPE_APPLICANT");
	String PERSONAL_TYPE_SUPPLEMENTARY= InfBatchProperty.getInfBatchConfig("REJECT_LETTER_PERSONAL_TYPE_SUPPLEMENTARY");
	String REJECT_LETTER_CONJUNCTION_AND_TH= InfBatchProperty.getInfBatchConfig("REJECT_LETTER_CONJUNCTION_AND_TH");
	String REJECT_LETTER_CONJUNCTION_FOR_TH= InfBatchProperty.getInfBatchConfig("REJECT_LETTER_CONJUNCTION_FOR_TH");
	String REJECT_LETTER_CONJUNCTION_AND_EN= InfBatchProperty.getInfBatchConfig("REJECT_LETTER_CONJUNCTION_AND_EN");
	String REJECT_LETTER_CONJUNCTION_FOR_EN= InfBatchProperty.getInfBatchConfig("REJECT_LETTER_CONJUNCTION_FOR_EN");
	String REJECT_LETTER_PRODUCT_NAME_CC = InfBatchProperty.getInfBatchConfig("REJECT_LETTER_PRODUCT_NAME_CC");	
	String REJECT_LETTER_PRODUCT_NAME_KPL =InfBatchProperty.getInfBatchConfig("REJECT_LETTER_PRODUCT_NAME_KPL");	
	String REJECT_LETTER_PRODUCT_NAME_KEC = InfBatchProperty.getInfBatchConfig("REJECT_LETTER_PRODUCT_NAME_KEC");	
	String REJECT_LETTER_MAIN_CARD_TH = InfBatchProperty.getInfBatchConfig("REJECT_LETTER_MAIN_CARD_TH");	
	String REJECT_LETTER_SUP_CARD_TH = InfBatchProperty.getInfBatchConfig("REJECT_LETTER_SUP_CARD_TH");	
	String REJECT_LETTER_MAIN_CARD_EN = InfBatchProperty.getInfBatchConfig("REJECT_LETTER_MAIN_CARD_EN");	
	String REJECT_LETTER_SUP_CARD_EN = InfBatchProperty.getInfBatchConfig("REJECT_LETTER_SUP_CARD_EN");
	String LANGUAGE_TH = InfBatchProperty.getInfBatchConfig("LANGUAGE_TH");
	String LANGUAGE_OTHER = InfBatchProperty.getInfBatchConfig("LANGUAGE_OTHER");
	@Override
	public TemplateVariableDataM getTemplateVariable() {
			TemplateVariableDataM templateVariable = new TemplateVariableDataM();
			TemplateBuilderRequest templateBuilderRequest = getTemplateBuilderRequest();
			RejectLetterProcessDataM rejectLetterProcess = (RejectLetterProcessDataM)templateBuilderRequest.getRequestObject();
			ArrayList<TemplateReasonCodeDetailDataM> templateReasonCodes=rejectLetterProcess.getTemplateReasonCodes();
			TemplateMasterDataM templateMaster = getTemplateMaster();
		try {
			RejectLetterDAO  dao = RejectLetterFactory.getRejectLetterDAO();
			if(!ServiceUtil.empty(rejectLetterProcess.getLanguage()) && !rejectLetterProcess.getLanguage().equals(RejectLetterUtil.TH)){
				rejectLetterProcess.setLanguage(RejectLetterUtil.EN);
			}
			HashMap<String,String>  hCallCenterNo = dao.getContactCallCenterNoProduct(templateMaster.getProducts(),rejectLetterProcess.getLanguage());
			ArrayList<RejectTemplateVariableDataM>  rejectTemplateVariables  = dao.getRejectTemplate1Values(rejectLetterProcess);
			
			HashMap<String, Object> hData = new HashMap<String, Object>();
			HashMap<String, Object> hSorting = new HashMap<String, Object>();
			if(!InfBatchUtil.empty(rejectTemplateVariables)){
				RejectTemplateVariableDataM rejectTemplateVariable = getRejectTemplateVariableDataM(rejectTemplateVariables,hCallCenterNo);
				String className = InfBatchProperty.getInfBatchConfig("REJECT_LETTER_TEMPLATE_VALIDATION");
				logger.debug("className : "+className);
				NotifyRejectLetterInf notify = (NotifyRejectLetterInf)Class.forName(className).newInstance();
				if(notify.validateTaskTransaction(rejectTemplateVariable)){
					String rejectReason = RejectLetterUtil.getRejectLetterReason(templateBuilderRequest);
					hData.put(TemplateBuilderConstant.TemplateVariableName.APPLICATION_NO, Formatter.displayText(rejectTemplateVariable.getApplicationGroupNo()));
					if(RejectLetterUtil.TH.equals(rejectTemplateVariable.getNationality())){
						hData.put(TemplateBuilderConstant.TemplateVariableName.CUSTOMER_NAME, Formatter.displayText(rejectTemplateVariable.getTitleName())+" "+Formatter.displayText(rejectTemplateVariable.getPersonalNameTh()));
					}else{
						hData.put(TemplateBuilderConstant.TemplateVariableName.CUSTOMER_NAME, Formatter.displayText(rejectTemplateVariable.getTitleName())+" "+Formatter.displayText(rejectTemplateVariable.getPersonalNameEn()));
					}
					
					hData.put(TemplateBuilderConstant.TemplateVariableName.ADDRESS_LINE_1,Formatter.displayText(rejectTemplateVariable.getAddressLine1()));
					hData.put(TemplateBuilderConstant.TemplateVariableName.ADDRESS_LINE_2,Formatter.displayText(rejectTemplateVariable.getAddressLine2()));
					hData.put(TemplateBuilderConstant.TemplateVariableName.FINAL_DECISION_DATE,Formatter.displayText(rejectTemplateVariable.getFinalDecisionDate()));
					hData.put(TemplateBuilderConstant.TemplateVariableName.PRODUCT_NAME,Formatter.displayText(rejectTemplateVariable.getProductName()));
					hData.put(TemplateBuilderConstant.TemplateVariableName.PRODUCT_NAME_TH_EN,Formatter.displayText(rejectTemplateVariable.getProductNameThEn()));
					hData.put(TemplateBuilderConstant.TemplateVariableName.IDNO,Formatter.displayText(rejectTemplateVariable.getIdNo()));
					hData.put(TemplateBuilderConstant.TemplateVariableName.PRODUCT_TYPE,Formatter.displayText(rejectTemplateVariable.getProductType()));
					hData.put(TemplateBuilderConstant.TemplateVariableName.APPLICATION_RECORD_ID,rejectTemplateVariable.getAllApplicationRecordIds());			
					hData.put(TemplateBuilderConstant.TemplateVariableName.CONTACT_CENTER_NO_PRODUCT,Formatter.displayText(rejectTemplateVariable.getContactCenterNoProduct()));
					hData.put(TemplateBuilderConstant.TemplateVariableName.CONTACT_CENTER_NO_CC,Formatter.displayText(rejectTemplateVariable.getContactCenterNoCC()));
					hData.put(TemplateBuilderConstant.TemplateVariableName.CONTACT_CENTER_NO_KPL,Formatter.displayText(rejectTemplateVariable.getContactCenterNoKPL()));
					hData.put(TemplateBuilderConstant.TemplateVariableName.CONTACT_CENTER_NO_KEC,Formatter.displayText(rejectTemplateVariable.getContactCenterNoKEC()));
					hData.put(TemplateBuilderConstant.TemplateVariableName.EMAIL_PRIMARY,Formatter.displayText(rejectTemplateVariable.getEmailPrimary()));
					hData.put(TemplateBuilderConstant.TemplateVariableName.PRODUCT_FULL_DESCRIPTION,Formatter.displayText(rejectReason));
					hData.put(TemplateBuilderConstant.TemplateVariableName.DOCUMENT_LIST,Formatter.displayText(RejectLetterUtil.getDocumentList(rejectTemplateVariable)));
					//hData.put(TemplateBuilderConstant.TemplateVariableName.DOCUMENT_LIST,Formatter.displayText(getDocumentList(rejectLetterProcess.getApplicationGroupId(),rejectTemplateVariable.getNationality())));
					
					hSorting.put(TemplateBuilderConstant.TemplateSortingName.ZIPCODE, Formatter.displayText(rejectTemplateVariable.getZipcode()));
				}
			}
			templateVariable.setUniqueId(rejectLetterProcess.getApplicationGroupId());
			templateVariable.getUniqueId().setLifeCycle(rejectLetterProcess.getLifeCycle());
			templateVariable.setTemplateVariable(hData);
			templateVariable.setSortingVariable(hSorting);
		} catch (Exception e) {
			logger.fatal("ERROR");
		}
		return templateVariable;
	}

	private RejectTemplateVariableDataM getRejectTemplateVariableDataM(ArrayList<RejectTemplateVariableDataM>  rejectTemplateVariables,HashMap<String,String> hCallCenterNo){
		ArrayList<RejectTemplateVariableDataM>   rejectTemplateVariableApplicantList = new ArrayList<RejectTemplateVariableDataM>  ();
		ArrayList<RejectTemplateVariableDataM> rejectTemplateVariableSupplementaryList = new ArrayList<RejectTemplateVariableDataM>();
		StringBuilder  productName= new StringBuilder("");
		StringBuilder reasonProduct = new StringBuilder("");
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
		ArrayList<String> allApplicationRecordIDs = new ArrayList<String>();
		try {
			int count=0;
			ArrayList<String> personalTypes = new ArrayList<String>();
			if(!InfBatchUtil.empty(rejectTemplateVariables)){
				for(RejectTemplateVariableDataM rejectVariable : rejectTemplateVariables){
					String language = rejectVariable.getNationality();
					allApplicationRecordIDs.add(rejectVariable.getApplicationRecordId());
					String productNameDesc =rejectVariable.getProductName();
					if(-1==productName.indexOf(productNameDesc)){
						count++;
						if(count==rejectTemplateVariables.size() && rejectTemplateVariables.size()>1){
							productName.append(RejectLetterUtil.TH.equals(language)?REJECT_LETTER_CONJUNCTION_AND_TH:REJECT_LETTER_CONJUNCTION_AND_EN);
							productName.append(productNameDesc);
						}else{
							productName.append(productNameDesc+" ");
						}
					}
					if((++count)==rejectTemplateVariables.size() && rejectTemplateVariables.size()>1){
						productName.append(RejectLetterUtil.TH.equals(language)?REJECT_LETTER_CONJUNCTION_AND_TH:REJECT_LETTER_CONJUNCTION_AND_EN);
					}
					if(REJECT_LETTER_PRODUCT_NAME_CC.equals(rejectVariable.getBusinessClassProductType())){
						//callCenterNoCC.append(kcc_slast+productNameDesc);
						callCenterNoCC.append(kcc_slast)
							//.append(" ").append(RejectLetterUtil.TH.equals(language)?REJECT_LETTER_CONJUNCTION_AND_TH:REJECT_LETTER_CONJUNCTION_AND_EN).append(" ")
							.append(" ").append(productNameDesc);
						kcc_slast="/";
					}else if(REJECT_LETTER_PRODUCT_NAME_KEC.equals(rejectVariable.getBusinessClassProductType())){
						//callCenterNoKEC.append(kec_slast+productNameDesc);
						callCenterNoKEC.append(kec_slast)
							//.append(" ").append(RejectLetterUtil.TH.equals(language)?REJECT_LETTER_CONJUNCTION_AND_TH:REJECT_LETTER_CONJUNCTION_AND_EN).append(" ")
							.append(" ").append(productNameDesc);
						kec_slast="/";
					}else if(REJECT_LETTER_PRODUCT_NAME_KPL.equals(rejectVariable.getBusinessClassProductType())){
						//callCenterNoKPL.append(kpl_slast+productNameDesc);
						callCenterNoKPL.append(kpl_slast)
							//.append(" ").append(RejectLetterUtil.TH.equals(language)?REJECT_LETTER_CONJUNCTION_AND_TH:REJECT_LETTER_CONJUNCTION_AND_EN).append(" ")
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
				rejectTemplateVal.setRejectReasonAllProduct(reasonProduct.toString());
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
	private int setCallCenterNo (String callCenterNo,StringBuilder callcenter, int countNewLine){
		try {
			if(!InfBatchUtil.empty(callCenterNo)){
				//callcenter.append(RejectLetterUtil.getNewLine()+RejectLetterUtil.getIndent(12)+callCenterNo);
				if(countNewLine>0){
					callcenter.append(RejectLetterUtil.getNewLine())
						.append(RejectLetterUtil.getIndent(12));
				}
				callcenter.append(callCenterNo);
				countNewLine++;
			}
		} catch (Exception e) {
			logger.fatal("ERROR",e);
		}
		return countNewLine;
	}
	
	//private String getDocumentList(String applicationGroupId,String language){
//	private String getDocumentList(RejectTemplateVariableDataM rejectTemplateVariable)throws InfBatchException{
//		StringBuilder documentList  = new StringBuilder("");
//		String applicationGroupId = rejectTemplateVariable.getApplicationGroupId();
//		String personalId = rejectTemplateVariable.getPersonalId();
//		String personalType = rejectTemplateVariable.getPersonalType();
//		String language = rejectTemplateVariable.getNationality().equals(LANGUAGE_TH)?LANGUAGE_TH:LANGUAGE_OTHER;
//		logger.debug("applicationGroupId : "+applicationGroupId);
//		logger.debug("personalId : "+personalId);
//		logger.debug("personalType : "+personalType);
//		logger.debug("language : "+language);
//		try {
//			String cardTitle = "";
//			String MAIN_CARD_TITLE =REJECT_LETTER_MAIN_CARD_TH;
//			String SUB_CARD_TITLE=REJECT_LETTER_SUP_CARD_TH;
//			if(!RejectLetterUtil.TH.equals(language)){
//				MAIN_CARD_TITLE =REJECT_LETTER_MAIN_CARD_EN;
//				SUB_CARD_TITLE=REJECT_LETTER_SUP_CARD_EN;
//			}
//			if(personalType.equals(PERSONAL_TYPE_APPLICANT)){
//				cardTitle = MAIN_CARD_TITLE;
//			}else if(personalType.equals(PERSONAL_TYPE_SUPPLEMENTARY)){
//				cardTitle = SUB_CARD_TITLE;
//			}
////			RejectLetterDAO dao = RejectLetterFactory.getRejectLetterDAO();
////			String docList = dao.getRejectDocumentListNotComplete(personalId, personalType);
////			logger.debug("cardTitle : "+cardTitle);
////			logger.debug("docList : "+docList);
////			if(!InfBatchUtil.empty(cardTitle) && !InfBatchUtil.empty(docList)){
////				setDocumentList(documentList,docList,cardTitle);
////			}
//			TemplateDAO  dao = NotificationFactory.getTemplateDAO();
//			ApplicationGroupDataM applicationGroup = ORIGDAOFactory.getApplicationGroupDAO()
//					.loadApplicationGroupDocument(applicationGroupId, personalId);
//			ArrayList<FollowDocHistoryDataM> followDocHistorys = dao.loadFollowDocHistorys(applicationGroup);
//			if(null!=followDocHistorys){
//				List<String> notCompleteDocs = new ArrayList<String>();
//				for(FollowDocHistoryDataM followDocHistory : followDocHistorys){
//					logger.debug("followDocHistory.DocumentCode : "+followDocHistory);
//					notCompleteDocs.add(CacheControl.getName(SystemConstant.getConstant("CACH_NAME_DOCUMENT_LIST"), followDocHistory.getDocumentCode()));
//				}
//				logger.debug("notCompleteDocs : "+notCompleteDocs);
//				if(!InfBatchUtil.empty(notCompleteDocs)){
//					setDocumentList(documentList, notCompleteDocs, cardTitle);
//				}
//			}
//		} catch (Exception e) {
//			logger.fatal("ERROR",e);
//			throw new InfBatchException(e);
//			
//		}
//		logger.debug("documentList : "+documentList.toString());
//		return documentList.toString();
//	}
//	private void setDocumentList(StringBuilder documentListAppend,String docName ,String title){
//		try {
//			String[] docList = docName.split("_");
//			documentListAppend.append(RejectLetterUtil.getNewLine()+RejectLetterUtil.getIndent(10)+title);
//			if(!InfBatchUtil.empty(docList)){
//				for(String doc:docList){
//					documentListAppend.append(RejectLetterUtil.getNewLine()+RejectLetterUtil.getIndent(12)+doc);
//				}
//			}
//		} catch (Exception e) {
//			logger.fatal("ERROR",e);
//		}
//	}
//	private void setDocumentList(StringBuilder documentListAppend,List<String> documents ,String title)throws InfBatchException{
//		try {
//			documentListAppend.append(RejectLetterUtil.getNewLine()+RejectLetterUtil.getIndent(10)+title);
//			for(String document : documents){
//				documentListAppend.append(RejectLetterUtil.getNewLine()+RejectLetterUtil.getIndent(12)+document);
//			}
//		} catch (Exception e) {
//			logger.fatal("ERROR",e);
//			throw new InfBatchException(e);
//		}
//	}
}
