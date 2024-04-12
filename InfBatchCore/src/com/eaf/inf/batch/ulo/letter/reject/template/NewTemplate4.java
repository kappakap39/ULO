package com.eaf.inf.batch.ulo.letter.reject.template;

import java.util.ArrayList;
import java.util.HashMap;

import org.apache.log4j.Logger;

import com.eaf.core.ulo.common.cache.InfBatchProperty;
import com.eaf.core.ulo.common.display.Formatter;
import com.eaf.core.ulo.common.exception.InfBatchException;
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
import com.eaf.inf.batch.ulo.letter.reject.util.RejectLetterUtil;
import com.eaf.service.common.util.ServiceUtil;

public class NewTemplate4 extends TemplateBuilderHelper{
	private static transient Logger logger = Logger.getLogger(NewTemplate4.class);
	String PERSONAL_TYPE_APPLICANT= InfBatchProperty.getInfBatchConfig("REJECT_LETTER_PERSONAL_TYPE_APPLICANT");
	String PERSONAL_TYPE_SUPPLEMENTARY= InfBatchProperty.getInfBatchConfig("REJECT_LETTER_PERSONAL_TYPE_SUPPLEMENTARY");
	String REJECT_LETTER_CONJUNCTION_AND_TH= InfBatchProperty.getInfBatchConfig("REJECT_LETTER_CONJUNCTION_AND_TH");
	String REJECT_LETTER_CONJUNCTION_AND_EN= InfBatchProperty.getInfBatchConfig("REJECT_LETTER_CONJUNCTION_AND_EN");
	String REJECT_LETTER_CONJUNCTION_FOR_TH= InfBatchProperty.getInfBatchConfig("REJECT_LETTER_CONJUNCTION_FOR_TH");
	String REJECT_LETTER_CONJUNCTION_FOR_EN= InfBatchProperty.getInfBatchConfig("REJECT_LETTER_CONJUNCTION_FOR_EN");
	String REJECT_LETTER_PRODUCT_NAME_CC = InfBatchProperty.getInfBatchConfig("REJECT_LETTER_PRODUCT_NAME_CC");	
	String REJECT_LETTER_PRODUCT_NAME_KPL =InfBatchProperty.getInfBatchConfig("REJECT_LETTER_PRODUCT_NAME_KPL");	
	String REJECT_LETTER_PRODUCT_NAME_KEC = InfBatchProperty.getInfBatchConfig("REJECT_LETTER_PRODUCT_NAME_KEC");
	String REJECT_LETTER_PRODUCT_PREFIX_EN = InfBatchProperty.getInfBatchConfig("REJECT_LETTER_PRODUCT_PREFIX_EN");
	String REJECT_LETTER_PRODUCT_POSTFIX_EN = InfBatchProperty.getInfBatchConfig("REJECT_LETTER_PRODUCT_POSTFIX_EN");
	ArrayList<String> REJECT_LETTER_SPACE_PARAM_CODE = InfBatchProperty.getListInfBatchConfig("REJECT_LETTER_SPACE_PARAM_CODE");
	@Override
	public TemplateVariableDataM getTemplateVariable() {
			TemplateVariableDataM templateVariable = new TemplateVariableDataM();
			TemplateBuilderRequest templateBuilderRequest = getTemplateBuilderRequest();
			RejectLetterProcessDataM rejectLetterProcess = (RejectLetterProcessDataM)templateBuilderRequest.getRequestObject();
			TemplateMasterDataM templateMaster = getTemplateMaster();
		try {
			RejectLetterDAO  dao = RejectLetterFactory.getRejectLetterDAO();
			if(!ServiceUtil.empty(rejectLetterProcess.getLanguage()) && !rejectLetterProcess.getLanguage().equals(RejectLetterUtil.TH)){
				rejectLetterProcess.setLanguage(RejectLetterUtil.EN);
			}
			HashMap<String,String> hCallCenterNo = dao.getContactCallCenterNoProduct(templateMaster.getProducts(),rejectLetterProcess.getLanguage());
			HashMap<String,String> hRejectLetterSpace = dao.getRejectLetterSpace();
			//String website = NotificationFactory.getNotificationDAO().getGeneralParamValue("WEBSITE_ALL_PRODUCT");
			//String website = RejectLetterUtil.getProductWebsite(templateMaster.getProducts());
			//logger.debug("website : "+website);
			
			ArrayList<RejectTemplateVariableDataM>  rejectTemplateVariables  = dao.getNewTemplate4Values(rejectLetterProcess);
			
			HashMap<String, Object> hData = new HashMap<String, Object>();
			HashMap<String, Object> hSorting = new HashMap<String, Object>();
			if(!InfBatchUtil.empty(rejectTemplateVariables)){
				RejectTemplateVariableDataM rejectTemplateVariable = getRejectTemplateVariableDataM(rejectTemplateVariables,hCallCenterNo,hRejectLetterSpace);
				String className = InfBatchProperty.getInfBatchConfig("REJECT_LETTER_TEMPLATE_VALIDATION");
				logger.debug("className : "+className);
				NotifyRejectLetterInf notify = (NotifyRejectLetterInf)Class.forName(className).newInstance();
				if(notify.validateTaskTransaction(rejectTemplateVariable)){
					hData.put(TemplateBuilderConstant.TemplateVariableName.APPLICATION_NO, Formatter.displayText(rejectTemplateVariable.getApplicationGroupNo()));
					if(RejectLetterUtil.TH.equals(rejectTemplateVariable.getNationality())){
						hData.put(TemplateBuilderConstant.TemplateVariableName.CUSTOMER_NAME, Formatter.displayText(rejectTemplateVariable.getTitleName())+ " " +Formatter.displayText(rejectTemplateVariable.getPersonalNameTh()));
					}else{
						hData.put(TemplateBuilderConstant.TemplateVariableName.CUSTOMER_NAME, Formatter.displayText(rejectTemplateVariable.getTitleName()) + " " + Formatter.displayText(rejectTemplateVariable.getPersonalNameEn()));
					}
					
					hData.put(TemplateBuilderConstant.TemplateVariableName.ADDRESS_LINE_1, Formatter.displayText(rejectTemplateVariable.getAddressLine1()));
					hData.put(TemplateBuilderConstant.TemplateVariableName.ADDRESS_LINE_2, Formatter.displayText(rejectTemplateVariable.getAddressLine2()));
					hData.put(TemplateBuilderConstant.TemplateVariableName.FINAL_DECISION_DATE, Formatter.displayText(rejectTemplateVariable.getFinalDecisionDate()));
					hData.put(TemplateBuilderConstant.TemplateVariableName.PRODUCT_NAME, Formatter.displayText(rejectTemplateVariable.getProductName()));
					hData.put(TemplateBuilderConstant.TemplateVariableName.PRODUCT_NAME_TH_EN, Formatter.displayText(rejectTemplateVariable.getProductNameThEn()));
					hData.put(TemplateBuilderConstant.TemplateVariableName.PRODUCT_NAME_2LANG, Formatter.displayText(rejectTemplateVariable.getProductName2lang()));
					hData.put(TemplateBuilderConstant.TemplateVariableName.IDNO, Formatter.displayText(rejectTemplateVariable.getIdNo()));
					hData.put(TemplateBuilderConstant.TemplateVariableName.PRODUCT_TYPE, Formatter.displayText(rejectTemplateVariable.getProductType()));
					hData.put(TemplateBuilderConstant.TemplateVariableName.APPLICATION_RECORD_ID, rejectTemplateVariable.getAllApplicationRecordIds());			
					hData.put(TemplateBuilderConstant.TemplateVariableName.CONTACT_CENTER_NO_PRODUCT,Formatter.displayText(rejectTemplateVariable.getContactCenterNoProduct()));
					hData.put(TemplateBuilderConstant.TemplateVariableName.CONTACT_CENTER_NO_CC,Formatter.displayText(rejectTemplateVariable.getContactCenterNoCC()));
					hData.put(TemplateBuilderConstant.TemplateVariableName.CONTACT_CENTER_NO_KPL,Formatter.displayText(rejectTemplateVariable.getContactCenterNoKPL()));
					hData.put(TemplateBuilderConstant.TemplateVariableName.CONTACT_CENTER_NO_KEC,Formatter.displayText(rejectTemplateVariable.getContactCenterNoKEC()));
					hData.put(TemplateBuilderConstant.TemplateVariableName.REJECTED_REASON_ALL_PRODUCT,Formatter.displayText(rejectTemplateVariable.getRejectReasonAllProduct()));
					hData.put(TemplateBuilderConstant.TemplateVariableName.EMAIL_PRIMARY,Formatter.displayText(rejectTemplateVariable.getEmailPrimary()));
					hData.put(TemplateBuilderConstant.TemplateVariableName.WEBSITE_BY_PRODUCT,Formatter.displayText(rejectTemplateVariable.getWebsite()));
					hData.put(TemplateBuilderConstant.TemplateVariableName.PRODUCT_FULL_DESCRIPTION,Formatter.displayText(rejectTemplateVariable.getProductFullDescription()));
					hData.put(TemplateBuilderConstant.TemplateVariableName.REJECT_REASON,Formatter.displayText(rejectTemplateVariable.getRejectReason()));
					hData.put(TemplateBuilderConstant.TemplateVariableName.CONTACT_NO_SPACE,Formatter.displayText(rejectTemplateVariable.getContactNoSpace()));
					hData.put(TemplateBuilderConstant.TemplateVariableName.WEBSITE_NOSPACE,Formatter.displayText(rejectTemplateVariable.getWebsiteNoSpace()));
					
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

	private RejectTemplateVariableDataM getRejectTemplateVariableDataM(ArrayList<RejectTemplateVariableDataM>  rejectTemplateVariables,HashMap<String,String> hCallCenterNo, HashMap<String,String>  hRejectLetterSpace){
		ArrayList<RejectTemplateVariableDataM>   rejectTemplateVariableApplicantList = new ArrayList<RejectTemplateVariableDataM>  ();
		ArrayList<RejectTemplateVariableDataM> rejectTemplateVariableSupplementaryList = new ArrayList<RejectTemplateVariableDataM>();
		StringBuilder  productName= new StringBuilder("");
		StringBuilder reasonProduct = new StringBuilder("");
		StringBuilder callCenterNoCC = new StringBuilder("");
		StringBuilder callCenterNoKEC = new StringBuilder("");
		StringBuilder callCenterNoKPL = new StringBuilder("");
		StringBuilder fullDescriptionCC = new StringBuilder("");
		StringBuilder fullDescriptionKEC = new StringBuilder("");
		StringBuilder fullDescriptionKPL = new StringBuilder("");
		StringBuilder allWebsite = new StringBuilder("");
		StringBuilder rejectReasonWithoutSpace = new StringBuilder("");
		StringBuilder contactCenterNoProductWithoutSpace = new StringBuilder("");
		StringBuilder websiteByProductWithoutSpace = new StringBuilder("");
		
		String CC_CALL_NO =  hCallCenterNo.get(REJECT_LETTER_PRODUCT_NAME_CC);
		String KEC_CALL_NO = hCallCenterNo.get(REJECT_LETTER_PRODUCT_NAME_KEC);
		String KPL_CALL_NO = hCallCenterNo.get(REJECT_LETTER_PRODUCT_NAME_KPL);
		int countNewLine = 0;
		countNewLine = setCallCenterNo(CC_CALL_NO,callCenterNoCC,hRejectLetterSpace,countNewLine);
		countNewLine = setCallCenterNo(KEC_CALL_NO,callCenterNoKEC,hRejectLetterSpace,countNewLine);
		countNewLine = setCallCenterNo(KPL_CALL_NO,callCenterNoKPL,hRejectLetterSpace,countNewLine);
		String kcc_slast="";
		String kec_slast="";
		String kpl_slast="";
		String gapBetweenTelNo="";
		ArrayList<String> allApplicationRecordIDs = new ArrayList<String>();
		try {
			HashMap<String,String> hWebsite = RejectLetterUtil.getAllWebsite();
			int count=0;
			ArrayList<String> personalTypes = new ArrayList<String>();
			if(!InfBatchUtil.empty(rejectTemplateVariables)){
				HashMap<String, String> hProduct = new HashMap<String,String>();
				boolean isGotCC = false;
				boolean isGotKEC = false;
				boolean isGotKPL = false;
				for(RejectTemplateVariableDataM rejectVariable : rejectTemplateVariables){
					String language = rejectVariable.getNationality();
					String CONJUNCTION_AND = RejectLetterUtil.TH.equals(language)?RejectLetterUtil.getIndent(1)+REJECT_LETTER_CONJUNCTION_AND_TH:RejectLetterUtil.getIndent(1)+REJECT_LETTER_CONJUNCTION_AND_EN+RejectLetterUtil.getIndent(1);
					String CONJUNCTION_FOR = RejectLetterUtil.TH.equals(language)?RejectLetterUtil.getIndent(1)+REJECT_LETTER_CONJUNCTION_FOR_TH:RejectLetterUtil.getIndent(1)+REJECT_LETTER_CONJUNCTION_FOR_EN+RejectLetterUtil.getIndent(1);
					allApplicationRecordIDs.add(rejectVariable.getApplicationRecordId());
					String productType = rejectVariable.getProductType();
					String productNameDesc =rejectVariable.getProductName();
					String reasonCodeDesc = rejectVariable.getRejectReasonAllProduct();
					if(InfBatchUtil.empty(hProduct) || !hProduct.keySet().contains(productType)){
						hProduct.put(productType, productNameDesc);
					}
					if(-1==productName.indexOf(productNameDesc)){
						count++;
						if(count==rejectTemplateVariables.size() && rejectTemplateVariables.size()>1){
							productName.append(CONJUNCTION_AND);
							productName.append(productNameDesc);
						}else{
							if(!InfBatchUtil.empty(productName.toString())){
								productName.append(" ");
							}
							productName.append(productNameDesc);
						}
					}
					if(!InfBatchUtil.empty(reasonCodeDesc)){
						String[] reasonCodeList = reasonCodeDesc.split("_");		
						reasonProduct.append(RejectLetterUtil.getNewLine()+RejectLetterUtil.getIndent(12));
						reasonProduct.append(CONJUNCTION_FOR);
						reasonProduct.append(productNameDesc);
						reasonProduct.append(gerejectReasonCodes(reasonCodeList));
					}
					if(REJECT_LETTER_PRODUCT_NAME_CC.equals(rejectVariable.getBusinessClassProductType())&&!isGotCC){
						isGotCC=true;
						contactCenterNoProductWithoutSpace.append(gapBetweenTelNo+CC_CALL_NO);
						gapBetweenTelNo=" ";
						//callCenterNoCC.append(kcc_slast+productNameDesc);
						callCenterNoCC.append(kcc_slast)
							.append(CONJUNCTION_FOR)
							.append(productNameDesc);
						kcc_slast="/";
					}else if(REJECT_LETTER_PRODUCT_NAME_KEC.equals(rejectVariable.getBusinessClassProductType())&&!isGotKEC){
						isGotKEC=true;
						contactCenterNoProductWithoutSpace.append(gapBetweenTelNo+KEC_CALL_NO);
						gapBetweenTelNo=" ";
						//callCenterNoKEC.append(kec_slast+productNameDesc);
						callCenterNoKEC.append(kec_slast)
							.append(CONJUNCTION_FOR)
							.append(productNameDesc);
						kec_slast="/";
					}else if(REJECT_LETTER_PRODUCT_NAME_KPL.equals(rejectVariable.getBusinessClassProductType())&&!isGotKPL){
						isGotKPL=true;
						contactCenterNoProductWithoutSpace.append(gapBetweenTelNo+KPL_CALL_NO);
						gapBetweenTelNo=" ";
						//callCenterNoKPL.append(kpl_slast+productNameDesc);
						callCenterNoKPL.append(kpl_slast)
							.append(CONJUNCTION_FOR)
							.append(productNameDesc);
						kpl_slast="/";
					}
					personalTypes.add(rejectVariable.getPersonalType());
					if(PERSONAL_TYPE_APPLICANT.equals(rejectVariable.getPersonalType())){
						rejectTemplateVariableApplicantList.add(rejectVariable);
					}else if(PERSONAL_TYPE_SUPPLEMENTARY.equals(rejectVariable.getPersonalType())){
						rejectTemplateVariableSupplementaryList.add(rejectVariable);
					}
					count++;
				}
				
				
				RejectTemplateVariableDataM rejectTemplateVal =   new RejectTemplateVariableDataM();
				if(personalTypes.contains(PERSONAL_TYPE_APPLICANT) && rejectTemplateVariableApplicantList.size()>0){
					rejectTemplateVal = rejectTemplateVariableApplicantList.get(0);
				}else{
					rejectTemplateVal = rejectTemplateVariableSupplementaryList.get(0);	
				}
				logger.debug("hProduct : "+hProduct);
				if(!InfBatchUtil.empty(hProduct)){
					int countProduct = 0;
					int countWebsite = 0;
					for(String type : hProduct.keySet()){
						String name = hProduct.get(type);
						String language = rejectTemplateVal.getNationality();
						String CONJUNCTION_FOR = RejectLetterUtil.TH.equals(language)?RejectLetterUtil.getIndent(1)+REJECT_LETTER_CONJUNCTION_FOR_TH:RejectLetterUtil.getIndent(1)+REJECT_LETTER_CONJUNCTION_FOR_EN+RejectLetterUtil.getIndent(1);
						if(REJECT_LETTER_PRODUCT_NAME_CC.equals(type)){
							countProduct = setProductFullDescription(name, language, fullDescriptionCC, hRejectLetterSpace, countProduct);
						}else if(REJECT_LETTER_PRODUCT_NAME_KEC.equals(type)){
							countProduct = setProductFullDescription(name, language, fullDescriptionKEC, hRejectLetterSpace, countProduct);
						}else if(REJECT_LETTER_PRODUCT_NAME_KPL.equals(type)){
							countProduct = setProductFullDescription(name, language, fullDescriptionKPL, hRejectLetterSpace, countProduct);
						}
						countWebsite = setWebsite(name, type, CONJUNCTION_FOR, hWebsite, allWebsite, hRejectLetterSpace, countWebsite);
						generateWebsiteNoSpace(type,hWebsite,websiteByProductWithoutSpace);
					}
					generateRejectLetterReason(rejectReasonWithoutSpace);
				}
				rejectTemplateVal.setProductName(productName.toString());
				rejectTemplateVal.setAllApplicationRecordIds(allApplicationRecordIDs);
				rejectTemplateVal.setRejectReasonAllProduct(reasonProduct.toString());
				rejectTemplateVal.setContactCenterNoCC(CC_CALL_NO);
				rejectTemplateVal.setContactCenterNoKEC(KEC_CALL_NO);
				rejectTemplateVal.setContactCenterNoKPL(KPL_CALL_NO);
				rejectTemplateVal.setContactCenterNoProduct(callCenterNoCC.toString()+callCenterNoKEC.toString()+callCenterNoKPL.toString());
				rejectTemplateVal.setProductFullDescription(fullDescriptionCC.toString()+fullDescriptionKEC.toString()+fullDescriptionKPL.toString());
				rejectTemplateVal.setWebsite(allWebsite.toString());
				rejectTemplateVal.setRejectReason(rejectReasonWithoutSpace.toString());
				rejectTemplateVal.setContactNoSpace(contactCenterNoProductWithoutSpace.toString());
				rejectTemplateVal.setWebsiteNoSpace(websiteByProductWithoutSpace.toString());
				return rejectTemplateVal;
			}
		} catch (Exception e) {
			logger.fatal("ERROR",e);
		}
		return null;
		}
	
	private String  gerejectReasonCodes (String[] reasonCodeList){
		StringBuilder reasonCodes = new StringBuilder("");
		try {
			if(!InfBatchUtil.empty(reasonCodeList)){
				for(String reason : reasonCodeList){
					reasonCodes.append(RejectLetterUtil.getNewLine()+RejectLetterUtil.getIndent(12)+reason);
				}
			}
		} catch (Exception e) {
			logger.fatal("ERROR",e);
		}
		return reasonCodes.toString();
	}
	
	private int setCallCenterNo (String callCenterNo,StringBuilder callcenter,HashMap<String,String> hRejectLetterSpace,int countNewLine){
		TemplateBuilderRequest templateBuilderRequest = getTemplateBuilderRequest();
		String templateType = templateBuilderRequest.getTemplateType();
		String SPACE_2 = hRejectLetterSpace.get(templateType+"_SPACE_2");
		try {
			if(!InfBatchUtil.empty(callCenterNo)){
				//callcenter.append(RejectLetterUtil.getNewLine()+RejectLetterUtil.getIndent(12)+callCenterNo);
				if(countNewLine>0){
					//callcenter.append(RejectLetterUtil.getNewLine()).append(RejectLetterUtil.getIndent(12));
					callcenter.append(RejectLetterUtil.getNewLine());
				}
				//callcenter.append(RejectLetterUtil.getIndent(12)).append(callCenterNo);
				callcenter.append(SPACE_2).append(callCenterNo);
				countNewLine++;
			}
		} catch (Exception e) {
			logger.fatal("ERROR",e);
		}
		return countNewLine;
	}
	private int setProductFullDescription(String productName,String language,StringBuilder fullDescriptionBuilder,HashMap<String,String> hRejectLetterSpace,int countProduct)throws InfBatchException{
		TemplateBuilderRequest templateBuilderRequest = getTemplateBuilderRequest();
		String templateType = templateBuilderRequest.getTemplateType();
		String SPACE_1 = hRejectLetterSpace.get(templateType+"_SPACE_1");
		String SPACE_2 = hRejectLetterSpace.get(templateType+"_SPACE_2");
		String rejectReason = RejectLetterUtil.getRejectLetterReason(templateBuilderRequest);
		if(!InfBatchUtil.empty(productName)){
			String prefix = RejectLetterUtil.TH.equals(language)?REJECT_LETTER_CONJUNCTION_FOR_TH:(REJECT_LETTER_PRODUCT_PREFIX_EN+RejectLetterUtil.getIndent(1));
			String postfix = RejectLetterUtil.TH.equals(language)?"":(RejectLetterUtil.getIndent(1)+REJECT_LETTER_PRODUCT_POSTFIX_EN);
			if(countProduct>0){
				fullDescriptionBuilder.append(RejectLetterUtil.getNewLine());
			}
			fullDescriptionBuilder.append(SPACE_1).append(prefix).append(productName).append(postfix)
				.append(RejectLetterUtil.getNewLine()).append(SPACE_2).append(rejectReason);
		}
		return countProduct;
	}
	private int setWebsite(String productName,String productType,String CONJUNCTION_FOR,HashMap<String,String> hWebsite,StringBuilder websiteBuilder,HashMap<String,String> hRejectLetterSpace,int countWebsite)throws InfBatchException{
		TemplateBuilderRequest templateBuilderRequest = getTemplateBuilderRequest();
		String templateType = templateBuilderRequest.getTemplateType();
		String SPACE_2 = hRejectLetterSpace.get(templateType+"_SPACE_2");
		if(!InfBatchUtil.empty(productType) && !InfBatchUtil.empty(hWebsite)){
			String website = hWebsite.get("WEBSITE_"+productType+"_PRODUCT");
			if(!InfBatchUtil.empty(website)){
				if(countWebsite>0){
					websiteBuilder.append(RejectLetterUtil.getNewLine());
				}
				websiteBuilder.append(SPACE_2).append(website).append(CONJUNCTION_FOR).append(productName);
			}
		}
		return countWebsite;
	}
	
	private void generateRejectLetterReason(StringBuilder rejectReasonWithoutSpace)throws InfBatchException{
		TemplateBuilderRequest templateBuilderRequest = getTemplateBuilderRequest();
		String rejectReason = RejectLetterUtil.getRejectLetterReason(templateBuilderRequest);
		rejectReasonWithoutSpace.append(rejectReason);
	}
	
	private void generateWebsiteNoSpace(String productType,HashMap<String,String> hWebsite,StringBuilder websiteByProductWithoutSpace)throws InfBatchException{
		if(!InfBatchUtil.empty(productType) && !InfBatchUtil.empty(hWebsite)){
			String website = hWebsite.get("WEBSITE_"+productType+"_PRODUCT");
			if(!InfBatchUtil.empty(website)){
				if(!websiteByProductWithoutSpace.toString().equals("")){
					websiteByProductWithoutSpace.append(" ");
				}
				websiteByProductWithoutSpace.append(website);
			}
		}
	}
}
