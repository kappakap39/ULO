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
import com.eaf.core.ulo.service.template.model.TemplateVariableDataM;
import com.eaf.inf.batch.ulo.letter.reject.dao.RejectLetterDAO;
import com.eaf.inf.batch.ulo.letter.reject.dao.RejectLetterFactory;
import com.eaf.inf.batch.ulo.letter.reject.model.RejectLetterBuildTemplateEntity;
import com.eaf.inf.batch.ulo.letter.reject.model.RejectTemplateVariableBundleEntity;
import com.eaf.inf.batch.ulo.letter.reject.model.RejectTemplateVariableDataM;
import com.eaf.inf.batch.ulo.letter.reject.util.RejectLetterUtil;

public class BundleTemplate extends TemplateBuilderHelper{
	private static transient Logger logger = Logger.getLogger(BundleTemplate.class);
	String PERSONAL_TYPE_APPLICANT= InfBatchProperty.getInfBatchConfig("REJECT_LETTER_PERSONAL_TYPE_APPLICANT");
	String PERSONAL_TYPE_SUPPLEMENTARY= InfBatchProperty.getInfBatchConfig("REJECT_LETTER_PERSONAL_TYPE_SUPPLEMENTARY");
	String REJECT_LETTER_CONJUNCTION_AND_TH= InfBatchProperty.getInfBatchConfig("REJECT_LETTER_CONJUNCTION_AND_TH");
	String REJECT_LETTER_CONJUNCTION_AND_EN= InfBatchProperty.getInfBatchConfig("REJECT_LETTER_CONJUNCTION_AND_EN");
	String REJECT_LETTER_CONJUNCTION_FOR_TH= InfBatchProperty.getInfBatchConfig("REJECT_LETTER_CONJUNCTION_FOR_TH");
	String REJECT_LETTER_CONJUNCTION_FOR_EN= InfBatchProperty.getInfBatchConfig("REJECT_LETTER_CONJUNCTION_FOR_EN");
	String REJECT_LETTER_PRODUCT_NAME_CC = InfBatchProperty.getInfBatchConfig("REJECT_LETTER_PRODUCT_NAME_CC");	
	String REJECT_LETTER_PRODUCT_NAME_KPL =InfBatchProperty.getInfBatchConfig("REJECT_LETTER_PRODUCT_NAME_KPL");	
	String REJECT_LETTER_PRODUCT_NAME_KEC = InfBatchProperty.getInfBatchConfig("REJECT_LETTER_PRODUCT_NAME_KEC");
	String REJECT_LETTER_PRODUCT_PREFIX_EN_BUNDLE = InfBatchProperty.getInfBatchConfig("REJECT_LETTER_PRODUCT_PREFIX_EN_BUNDLE");
	String REJECT_LETTER_PRODUCT_POSTFIX_EN_BUNDLE = InfBatchProperty.getInfBatchConfig("REJECT_LETTER_PRODUCT_POSTFIX_EN_BUNDLE");
	ArrayList<String> REJECT_LETTER_SPACE_PARAM_CODE = InfBatchProperty.getListInfBatchConfig("REJECT_LETTER_SPACE_PARAM_CODE");
	String REJECT_LETTER_FIELD_DELIMETER = InfBatchProperty.getInfBatchConfig("REJECT_LETTER_FIELD_DELIMETER");

	private RejectLetterBuildTemplateEntity appGroup;
	private String language;
	private String CONJUNCTION_AND;
	private String CONJUNCTION_FOR;
	
	@Override
	public TemplateVariableDataM getTemplateVariable() {
			TemplateVariableDataM templateVariable = new TemplateVariableDataM();
			TemplateBuilderRequest templateBuilderRequest = getTemplateBuilderRequest();
			appGroup = (RejectLetterBuildTemplateEntity)templateBuilderRequest.getRequestObject();
		try {
			RejectLetterDAO dao = RejectLetterFactory.getRejectLetterDAO();
			this.language = appGroup.getLanguage();
			this.CONJUNCTION_AND = RejectLetterUtil.TH.equals(language)?RejectLetterUtil.getIndent(1)+REJECT_LETTER_CONJUNCTION_AND_TH:RejectLetterUtil.getIndent(1)+REJECT_LETTER_CONJUNCTION_AND_EN+RejectLetterUtil.getIndent(1);
			this.CONJUNCTION_FOR = RejectLetterUtil.TH.equals(language)?RejectLetterUtil.getIndent(1)+REJECT_LETTER_CONJUNCTION_FOR_TH:RejectLetterUtil.getIndent(1)+REJECT_LETTER_CONJUNCTION_FOR_EN+RejectLetterUtil.getIndent(1);
			
			ArrayList<RejectTemplateVariableDataM> rejectTemplateVariables = new ArrayList<RejectTemplateVariableDataM>();
			HashMap<String, String> hProductList = new HashMap<String, String>();
			for(RejectTemplateVariableDataM item : dao.getBundleTemplateValues(appGroup)){//getAllProductForGenerateVariables
				if(!rejectTemplateVariables.contains(item))
					rejectTemplateVariables.add(item);
				if(!hProductList.containsKey(item.getProductType()))
					hProductList.put(item.getProductType(), item.getProductName());
			}
			ArrayList<String> productList = new ArrayList<String>(hProductList.keySet());
			HashMap<String,String> hCallCenterNo = dao.getContactCallCenterNoProduct(productList,language);
			HashMap<String,String> hRejectLetterSpace = dao.getRejectLetterSpace();
			
			HashMap<String, Object> hData = new HashMap<String, Object>();
			HashMap<String, Object> hSorting = new HashMap<String, Object>();
			if(!InfBatchUtil.empty(rejectTemplateVariables)){
				RejectTemplateVariableDataM rejectTemplateVariable = getRejectTemplateVariableDataM(rejectTemplateVariables,hCallCenterNo,hRejectLetterSpace,hProductList);
				
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
				hData.put(TemplateBuilderConstant.TemplateVariableName.BUNDLE_FULL_DESCRIPTION,Formatter.displayText(rejectTemplateVariable.getBundleFullDescription()));
				
				hData.put(TemplateBuilderConstant.TemplateVariableName.REJECT_REASON,Formatter.displayText(rejectTemplateVariable.getRejectReason()));
				hData.put(TemplateBuilderConstant.TemplateVariableName.CONTACT_NO_SPACE,Formatter.displayText(rejectTemplateVariable.getContactNoSpace()));
				hData.put(TemplateBuilderConstant.TemplateVariableName.WEBSITE_NOSPACE,Formatter.displayText(rejectTemplateVariable.getWebsiteNoSpace()));
				
				hSorting.put(TemplateBuilderConstant.TemplateSortingName.ZIPCODE, Formatter.displayText(rejectTemplateVariable.getZipcode()));
			}
			templateVariable.setUniqueId(appGroup.getApplicationGroupId());
			templateVariable.getUniqueId().setLifeCycle(appGroup.getLifeCycle());
			templateVariable.setTemplateVariable(hData);
			templateVariable.setSortingVariable(hSorting);
		} catch (Exception e) {
			logger.fatal("ERROR");
		}
		return templateVariable;
	}

	private RejectTemplateVariableDataM getRejectTemplateVariableDataM(ArrayList<RejectTemplateVariableDataM>  rejectTemplateVariables,HashMap<String,String> hCallCenterNo, HashMap<String,String> hRejectLetterSpace, HashMap<String,String> hProductList){
		ArrayList<RejectTemplateVariableDataM>   rejectTemplateVariableApplicantList = new ArrayList<RejectTemplateVariableDataM>  ();
		ArrayList<RejectTemplateVariableDataM> rejectTemplateVariableSupplementaryList = new ArrayList<RejectTemplateVariableDataM>();
		StringBuilder productName= new StringBuilder("");
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
		
		String productNameCC = "";
		String productNameKEC = "";
		String productNameKPL = "";
		String productTypeCC = "";
		String productTypeKEC = "";
		String productTypeKPL = "";
		
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
		ArrayList<String> allApplicationRecordIDs = new ArrayList<String>();
		try {
			
			HashMap<String,String> hWebsite = RejectLetterUtil.getAllWebsite();
			ArrayList<String> personalTypes = new ArrayList<String>();
			if(!InfBatchUtil.empty(rejectTemplateVariables)){
//				HashMap<String, String> hProduct = new HashMap<String,String>();
				boolean isGotCC, isGotKEC, isGotKPL;
				isGotCC = isGotKEC = isGotKPL = false;
				int countProduct = 0;
				int countWebsite = 0;
				for(RejectTemplateVariableDataM rejectVariable : rejectTemplateVariables){
					allApplicationRecordIDs.add(rejectVariable.getApplicationRecordId());
					String productType = rejectVariable.getProductType();
					String productNameDesc =rejectVariable.getProductName();
					String reasonCodeDesc = rejectVariable.getRejectReasonAllProduct();
//					if(InfBatchUtil.empty(hProduct) || !hProduct.keySet().contains(productType)){
//						hProduct.put(productType, productNameDesc);
//					}
					if(!InfBatchUtil.empty(reasonCodeDesc)){
						String[] reasonCodeList = reasonCodeDesc.split("_");		
						reasonProduct.append(RejectLetterUtil.getNewLine()+RejectLetterUtil.getIndent(12));
						reasonProduct.append(CONJUNCTION_FOR);
						reasonProduct.append(productNameDesc);
						reasonProduct.append(gerejectReasonCodes(reasonCodeList));
					}
					String gapBetweenTelNo="";
					if(REJECT_LETTER_PRODUCT_NAME_CC.equals(rejectVariable.getBusinessClassProductType())&&!isGotCC){
						isGotCC=true;
						productNameCC = productNameDesc;
						productTypeCC = productType;
						contactCenterNoProductWithoutSpace.append(gapBetweenTelNo+CC_CALL_NO+" "+CONJUNCTION_FOR+productNameDesc);
//						countProduct = setProductFullDescription(productType , productNameDesc, language, fullDescriptionCC, hRejectLetterSpace, countProduct, rejectReasonWithoutSpace);
						gapBetweenTelNo=" ";
						callCenterNoCC.append(kcc_slast)
							.append(CONJUNCTION_FOR)
							.append(productNameDesc);
						kcc_slast="/";
					}else if(REJECT_LETTER_PRODUCT_NAME_KEC.equals(rejectVariable.getBusinessClassProductType())&&!isGotKEC){
						isGotKEC=true;
						productNameKEC = productNameDesc;
						productTypeKEC = productType;
						contactCenterNoProductWithoutSpace.append(gapBetweenTelNo+KEC_CALL_NO+" "+CONJUNCTION_FOR+productNameDesc);
//						countProduct = setProductFullDescription(productType, productNameDesc, language, fullDescriptionKEC, hRejectLetterSpace, countProduct, rejectReasonWithoutSpace);
						callCenterNoKEC.append(kec_slast)
							.append(CONJUNCTION_FOR)
							.append(productNameDesc);
						kec_slast="/";
					}else if(REJECT_LETTER_PRODUCT_NAME_KPL.equals(rejectVariable.getBusinessClassProductType())&&!isGotKPL){
						isGotKPL=true;
						productNameKPL = productNameDesc;
						productTypeKPL = productType;
						contactCenterNoProductWithoutSpace.append(gapBetweenTelNo+KPL_CALL_NO+" "+CONJUNCTION_FOR+productNameDesc);
//						countProduct = setProductFullDescription(productType, productNameDesc, language, fullDescriptionKPL, hRejectLetterSpace, countProduct, rejectReasonWithoutSpace);
						callCenterNoKPL.append(kpl_slast)
							.append(CONJUNCTION_FOR)
							.append(productNameDesc);
						kpl_slast="/";
					}
					countWebsite = setWebsite(productType, productNameDesc, CONJUNCTION_FOR, hWebsite, allWebsite, hRejectLetterSpace, countWebsite, websiteByProductWithoutSpace);
					if(!personalTypes.contains(rejectVariable.getPersonalType()))
						personalTypes.add(rejectVariable.getPersonalType());
					if(PERSONAL_TYPE_APPLICANT.equals(rejectVariable.getPersonalType())){
						rejectTemplateVariableApplicantList.add(rejectVariable);
					}else if(PERSONAL_TYPE_SUPPLEMENTARY.equals(rejectVariable.getPersonalType())){
						rejectTemplateVariableSupplementaryList.add(rejectVariable);
					}
				}//end for product
				ArrayList<String> productNameList = new ArrayList<String>();
				if(!productNameCC.isEmpty()) productNameList.add(productNameCC);
				if(!productNameKEC.isEmpty()) productNameList.add(productNameKEC);
				if(!productNameKPL.isEmpty()) productNameList.add(productNameKPL);
				ArrayList<String> productTypeList = new ArrayList<String>();
				if(!productTypeCC.isEmpty()) productTypeList.add(productTypeCC);
				if(!productTypeKEC.isEmpty()) productTypeList.add(productTypeKEC);
				if(!productTypeKPL.isEmpty()) productTypeList.add(productTypeKPL);
				String productNameAll = "";
				for(int i = 0; i<productNameList.size(); i++) {
					String productNameItem = productNameList.get(i);
					if((i+1)==productNameList.size() && productNameList.size()>1){
						productNameAll += CONJUNCTION_AND+productNameItem;
					}else{
						if(!productNameAll.isEmpty())
							productNameAll += " ";
						productNameAll += productNameItem;
					}
				}
				productName.append(productNameAll);
				String productTypeAll = "";
				String delimiter = "";
				for(String productTypeItem : productTypeList) {
					productTypeAll += delimiter + productTypeItem;
					delimiter = REJECT_LETTER_FIELD_DELIMETER;
				}
				
				RejectTemplateVariableDataM rejectTemplateVal =   new RejectTemplateVariableDataM();
				if(personalTypes.contains(PERSONAL_TYPE_APPLICANT) && rejectTemplateVariableApplicantList.size()>0){
					rejectTemplateVal = rejectTemplateVariableApplicantList.get(0);
				}else{
					rejectTemplateVal = rejectTemplateVariableSupplementaryList.get(0);	
				}
				countProduct = setBundleFullDescription(hProductList, language, fullDescriptionCC, hRejectLetterSpace, countProduct, rejectReasonWithoutSpace);
//				logger.debug("hProduct : "+hProduct);
//				if(!InfBatchUtil.empty(hProduct)){
//					int countProduct = 0;
//					int countWebsite = 0;
//					for(String type : hProduct.keySet()){
//						String name = hProduct.get(type);
//						if(REJECT_LETTER_PRODUCT_NAME_CC.equals(type)){
//							countProduct = setProductFullDescription(type, name, language, fullDescriptionCC, hRejectLetterSpace, countProduct, rejectReasonWithoutSpace);
//						}else if(REJECT_LETTER_PRODUCT_NAME_KEC.equals(type)){
//							countProduct = setProductFullDescription(type, name, language, fullDescriptionKEC, hRejectLetterSpace, countProduct, rejectReasonWithoutSpace);
//						}else if(REJECT_LETTER_PRODUCT_NAME_KPL.equals(type)){
//							countProduct = setProductFullDescription(type, name, language, fullDescriptionKPL, hRejectLetterSpace, countProduct, rejectReasonWithoutSpace);
//						}
//						countWebsite = setWebsite(name, type, CONJUNCTION_FOR, hWebsite, allWebsite, hRejectLetterSpace, countWebsite, websiteByProductWithoutSpace);
//					}
//				}
				rejectTemplateVal.setProductName(productName.toString());
				rejectTemplateVal.setAllApplicationRecordIds(allApplicationRecordIDs);
				rejectTemplateVal.setRejectReasonAllProduct(reasonProduct.toString());
				rejectTemplateVal.setContactCenterNoCC(CC_CALL_NO);
				rejectTemplateVal.setContactCenterNoKEC(KEC_CALL_NO);
				rejectTemplateVal.setContactCenterNoKPL(KPL_CALL_NO);
				rejectTemplateVal.setContactCenterNoProduct(callCenterNoCC.toString()+callCenterNoKEC.toString()+callCenterNoKPL.toString());
				rejectTemplateVal.setBundleFullDescription(fullDescriptionCC.toString()+fullDescriptionKEC.toString()+fullDescriptionKPL.toString());
				rejectTemplateVal.setWebsite(allWebsite.toString());
				
				rejectTemplateVal.setRejectReason(rejectReasonWithoutSpace.toString());
				rejectTemplateVal.setContactNoSpace(contactCenterNoProductWithoutSpace.toString());
				rejectTemplateVal.setWebsiteNoSpace(websiteByProductWithoutSpace.toString());
				rejectTemplateVal.setProductType(productTypeAll);
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
		String SPACE_1 = hRejectLetterSpace.get(templateType+"_SPACE_1");
		try {
			if(!InfBatchUtil.empty(callCenterNo)){
				if(countNewLine>0){
					callcenter.append(RejectLetterUtil.getNewLine());
				}
				callcenter.append(SPACE_1).append(callCenterNo);
				countNewLine++;
			}
		} catch (Exception e) {
			logger.fatal("ERROR",e);
		}
		return countNewLine;
	}
	private int setBundleFullDescription(HashMap<String,String> hProductList, String language, StringBuilder fullDescriptionBuilder, HashMap<String,String> hRejectLetterSpace,int countProduct, StringBuilder rejectReasonWithoutSpace)throws InfBatchException{
		TemplateBuilderRequest templateBuilderRequest = getTemplateBuilderRequest();
		for(RejectTemplateVariableBundleEntity app: appGroup.getApps()){
			String templateType = app.getTemplateType();
			String templateId = app.getTemplateId();
			String productName = hProductList.get(app.getProductType());
			templateBuilderRequest.setTemplateType(templateType);
			templateBuilderRequest.setTemplateId(templateId);
			String SPACE_1 = hRejectLetterSpace.get(templateType+"_SPACE_1");
			String rejectReason = RejectLetterUtil.getRejectLetterReason(templateBuilderRequest);
			if(!InfBatchUtil.empty(productName)){
				String prefix = RejectLetterUtil.TH.equals(language)?REJECT_LETTER_CONJUNCTION_FOR_TH:REJECT_LETTER_PRODUCT_PREFIX_EN_BUNDLE;
				String postfix = RejectLetterUtil.TH.equals(language)?"":(RejectLetterUtil.getIndent(1)+REJECT_LETTER_PRODUCT_POSTFIX_EN_BUNDLE);
				if(countProduct>0){
					fullDescriptionBuilder.append(RejectLetterUtil.getNewLine());
				}
				if(!rejectReason.trim().isEmpty()){
					fullDescriptionBuilder.append(SPACE_1).append(prefix).append(productName).append(postfix).append(RejectLetterUtil.getIndent(1)).append(rejectReason);
					rejectReasonWithoutSpace.append(rejectReason);
					countProduct++;
				}
			}
		}
		return countProduct;
	}
	private int setWebsite(String productName,String productType,String CONJUNCTION_FOR,HashMap<String,String> hWebsite,StringBuilder websiteBuilder,HashMap<String,String> hRejectLetterSpace,int countWebsite, StringBuilder websiteByProductWithoutSpace)throws InfBatchException{
		TemplateBuilderRequest templateBuilderRequest = getTemplateBuilderRequest();
		String templateType = templateBuilderRequest.getTemplateType();
		String SPACE_1 = hRejectLetterSpace.get(templateType+"_SPACE_1");
		if(!InfBatchUtil.empty(productType) && !InfBatchUtil.empty(hWebsite)){
			String website = hWebsite.get("WEBSITE_"+productType+"_PRODUCT");
			if(!InfBatchUtil.empty(website)){
				if(countWebsite>0){
					websiteBuilder.append(RejectLetterUtil.getNewLine());
				}
				websiteBuilder.append(SPACE_1).append(website).append(CONJUNCTION_FOR).append(productName);
				websiteByProductWithoutSpace.append(website);
				countWebsite++;
			}
		}
		return countWebsite;
	}
}
