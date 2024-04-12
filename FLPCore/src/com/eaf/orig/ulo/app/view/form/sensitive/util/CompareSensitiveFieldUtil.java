package com.eaf.orig.ulo.app.view.form.sensitive.util;

import java.util.ArrayList;
import java.util.HashMap;

import org.apache.log4j.Logger;

import com.eaf.core.ulo.common.display.FormatUtil;
import com.eaf.core.ulo.common.engine.SQLQueryEngine;
import com.eaf.core.ulo.common.model.FieldConfigDataM;
import com.eaf.core.ulo.common.properties.CacheControl;
import com.eaf.core.ulo.common.properties.SystemConstant;
import com.eaf.core.ulo.common.util.Util;
import com.eaf.orig.ulo.app.view.form.subform.product.manual.CardInfoUtil;
import com.eaf.orig.ulo.model.app.AddressDataM;
import com.eaf.orig.ulo.model.app.ApplicationDataM;
import com.eaf.orig.ulo.model.app.ApplicationGroupDataM;
import com.eaf.orig.ulo.model.app.CardDataM;
import com.eaf.orig.ulo.model.app.ComparisonGroupDataM;
import com.eaf.orig.ulo.model.app.PaymentMethodDataM;
import com.eaf.orig.ulo.model.app.PersonalInfoDataM;
import com.eaf.orig.ulo.model.compare.CompareDataM;
import com.google.gson.Gson;
public class CompareSensitiveFieldUtil {
	private static transient Logger logger = Logger.getLogger(CompareSensitiveFieldUtil.class);
	public static final String REKEY = "REKEY"; 
	private static String PERSONAL_TYPE_APPLICANT = SystemConstant.getConstant("PERSONAL_TYPE_APPLICANT");	
	private static String PERSONAL_TYPE_SUPPLEMENTARY = SystemConstant.getConstant("PERSONAL_TYPE_SUPPLEMENTARY");
	private static String PERSONAL_TYPE_INFO = SystemConstant.getConstant("PERSONAL_TYPE_INFO");
	private static String PRODUCT_CRADIT_CARD = SystemConstant.getConstant("PRODUCT_CRADIT_CARD");
	private static String CACHE_COMPARE_SENSITIVE = SystemConstant.getConstant("CACHE_COMPARE_SENSITIVE");
	
	public static PersonalInfoDataM getPersonalInfoByRefId(ApplicationGroupDataM applicationGroup,CompareDataM compareData){
		String[] tokens = compareData.getRefId().split(CompareDataM.MARKER);
		String personalType = tokens[0];	
		String uniqueId = tokens[1];	
		logger.debug("personalType : "+personalType);
		logger.debug("uniqueId : "+uniqueId);
		return (PERSONAL_TYPE_APPLICANT.equals(personalType))
					?applicationGroup.getPersonalById(uniqueId)
						:applicationGroup.getPersonalInfoByIDNo(uniqueId,PERSONAL_TYPE_INFO);
	}	
	public static PersonalInfoDataM getPersonalInfoByUniqueId(ApplicationGroupDataM applicationGroup,CompareDataM compareData){
		String  uniqueId = compareData.getUniqueId();	
		logger.debug("uniqueId : "+uniqueId);
		return applicationGroup.getPersonalById(uniqueId);
	}	
	public static AddressDataM getAddressByUniqueId(ApplicationGroupDataM applicationGroup,CompareDataM compareData){
		String  uniqueId = compareData.getUniqueId();	
		logger.debug("uniqueId : "+uniqueId);		
		if(!Util.empty(uniqueId)){
			return applicationGroup.getAddressByAddressId(uniqueId);
		}
		return null;
	}	
	public static PaymentMethodDataM getPaymentMethodByUniqueId(ApplicationGroupDataM applicationGroup,CompareDataM compareData){
		String  uniqueId = compareData.getUniqueId();	
		logger.debug("uniqueId : "+uniqueId);		
		return applicationGroup.getPaymentMethodById(uniqueId);
	}	
	public static String getPersonalTypeByRefId(CompareDataM compareData){
		String[] tokens = compareData.getRefId().split(CompareDataM.MARKER);
		String personalType = tokens[0];	
		return personalType;
	}
	public static String getProductByRefId(CompareDataM compareData){
		String[] tokens = compareData.getRefId().split(CompareDataM.MARKER);
		String product = tokens[2];	
		return product;
	}
	public static String getProductTypeByRefId(CompareDataM compareData){
		String[] tokens = compareData.getRefId().split(CompareDataM.MARKER);
		try{
			return tokens[3];	
		}catch(Exception e){
			return null;
		}
	}
	public static String getHashCardNoByRefId(CompareDataM compareData){
		String[] tokens = compareData.getRefId().split(CompareDataM.MARKER);
		try{
			return tokens[3];	
		}catch(Exception e){
			return null;
		}
	}
	public static ApplicationDataM getApplicationByRefId(ApplicationGroupDataM applicationGroup,CompareDataM compareData){
		PersonalInfoDataM personalInfo = getPersonalInfoByRefId(applicationGroup, compareData);
		String[] tokens = compareData.getRefId().split(CompareDataM.MARKER);
		String product = tokens[2];
		String personalId = personalInfo.getPersonalId();
		return applicationGroup.getApplicationRelationLifeCycleByProduct(personalId,product);
	}
	public static ApplicationDataM getApplicationProductTypeByRefId(ApplicationGroupDataM applicationGroup,CompareDataM compareData){
		PersonalInfoDataM personalInfoM = getPersonalInfoByRefId(applicationGroup, compareData);
		String[] tokens = compareData.getRefId().split(CompareDataM.MARKER);
		String product = tokens[2];		
		String personalId = personalInfoM.getPersonalId();
		String prevCardCode = getProductTypeByRefId(compareData);
		logger.debug("product : "+product);
		logger.debug("personalId : "+personalId);
		logger.debug("prevCardCode : "+prevCardCode);
		ArrayList<ApplicationDataM> applications = applicationGroup.filterApplicationRelationLifeCycle(personalId);
		if(!Util.empty(applications)) {
			for(ApplicationDataM application : applications) {
				if(product.equals(application.getProduct())) {
					if(PRODUCT_CRADIT_CARD.equals(product)){
						CardDataM cardM = application.getCard();
						logger.debug(cardM);
						if(!Util.empty(cardM) && !Util.empty(cardM.getCardType())) {
							HashMap<String, Object> CardInfo = CardInfoUtil.getCardInfo(cardM.getCardType());
							String cardCode = SQLQueryEngine.display(CardInfo, "CARD_CODE");
							logger.debug("cardCode : "+cardCode);
							if(!Util.empty(prevCardCode) && prevCardCode.equals(cardCode)) {
								return application;
							}
						}
					}else{
						return application;
					}
				}
			}
		}
		return null;
	}
	public static ApplicationDataM getApplicationByUniqueId(ApplicationGroupDataM applicationGroup,CompareDataM compareData){
		String uniqueId = compareData.getUniqueId();
		logger.debug("uniqueId>>>>"+uniqueId);
		return applicationGroup.getApplicationById(uniqueId);
	}
	public static ApplicationDataM getApplicationCardNoByRefId(ApplicationGroupDataM applicationGroup,CompareDataM compareData){
		PersonalInfoDataM personalInfoM = getPersonalInfoByRefId(applicationGroup, compareData);
		String personalId = personalInfoM.getPersonalId();
		String hashCardNo = getHashCardNoByRefId(compareData);
		ArrayList<ApplicationDataM> applications = applicationGroup.filterApplicationRelationLifeCycle(personalId);
		if(!Util.empty(applications)) {
			for(ApplicationDataM application : applications) {
				CardDataM card = application.getCard();
				if(null != card){
					if(null != hashCardNo && hashCardNo.equals(card.getHashingCardNo())){
						return application;
					}
				}
			}
		}
		return null;
	}
	public static String getAddressRefId(PersonalInfoDataM personalInfo,String addressType){
		String PERSONAL_TYPE_APPLICANT = SystemConstant.getConstant("PERSONAL_TYPE_APPLICANT");
		String uniqueId = (PERSONAL_TYPE_APPLICANT.equals(personalInfo.getPersonalType()))?personalInfo.getPersonalId():personalInfo.getIdno();
		return personalInfo.getPersonalType()+"_"+uniqueId+"_"+addressType;
	}
	
	public static String getApplicationRefId(ApplicationDataM applciation){
		return "A_"+applciation.getApplicationRecordId();
	}
	
	public static String getPersonalRefId(PersonalInfoDataM personalInfo){
		String PERSONAL_TYPE_APPLICANT = SystemConstant.getConstant("PERSONAL_TYPE_APPLICANT");
		return personalInfo.getPersonalType()
				+"_"
				+((PERSONAL_TYPE_APPLICANT.equals(personalInfo.getPersonalType()))
						?personalInfo.getPersonalId()
								:personalInfo.getIdno());
	}
	public static String generateCISUniqueObjectId(Object object){
		if(object instanceof PersonalInfoDataM){
			PersonalInfoDataM personalInfo = (PersonalInfoDataM)object;
			return personalInfo.getPersonalType()+"_"+personalInfo.getIdno();
		}else if(object instanceof FieldConfigDataM){
			FieldConfigDataM fieldConfigData = (FieldConfigDataM)object;
			return fieldConfigData.getPersonalType()+"_"+fieldConfigData.getIdNo();
		}
		return "";
		
		 
	}
	public static String getProductRefId(PersonalInfoDataM personalInfo,String product){
		return getPersonalRefId(personalInfo)+"_"+product;
	}
	public static String getCardCodeRefId(PersonalInfoDataM personalInfo,String product,String cardCode){
		return getPersonalRefId(personalInfo)+"_"+product+"_"+cardCode;
	}
	public static String getCardTypeRefId(PersonalInfoDataM personalInfo,String product,String cardType){
		return getPersonalRefId(personalInfo)+"_"+product+"_"+cardType;
	}
	public static String getBusClassRefId(PersonalInfoDataM personalInfo,String product,String busClassId){
		return getPersonalRefId(personalInfo)+"_"+product+"_"+busClassId;
	}
	public static String getCardNoRefId(PersonalInfoDataM personalInfo,String product,CardDataM card){
		return getPersonalRefId(personalInfo)+"_"+product+((null != card)?"_"+card.getHashingCardNo():"");
	}
	public static String getElementName(CompareDataM compareData,String suffix){
		if(null==compareData) return "";
		return REKEY+"_"+compareData.getFieldNameType()+suffix;
	}
	public static String getElementName(CompareDataM compareData){
		if(null==compareData) return "";
		return REKEY+"_"+compareData.getFieldNameType();
	}
	public static String getPrefixAddress(PersonalInfoDataM  personalInfo,String addressType){
		if(null==personalInfo) return "";
		String seqStr ="";
		if(!Util.empty(personalInfo.getSeq())){
			seqStr =FormatUtil.toString(personalInfo.getSeq());
		}
		return  getPrefixAddress(personalInfo.getPersonalType(),seqStr,addressType);
	}
	public static String getPrefixAddress(String personaType,String seq,String addressType){
		return REKEY+"_"+personaType+"_"+seq+"_"+addressType;
	}
	
	public static String getAddressRekeyFieldName(String fieldName,String UniqueId){
		return REKEY+"_"+fieldName+"_"+CompareDataM.UniqueLevel.ADDRESS+"_"+UniqueId;
	}
	
	public static String getElementSuffix(CompareDataM compareData){
		if(null==compareData) return "";
		return compareData.getUniqueLevel()+"_"+compareData.getUniqueId();
	}
	public static String getParameterName(CompareDataM compareData){
		if(null==compareData) return "";
		return getElementName(compareData)+"_"+getElementSuffix(compareData);
	}
	public static String getParameterName(CompareDataM compareData,String elementSuffix){
		if(null==compareData) return "";
		return getElementName(compareData, elementSuffix)+"_"+getElementSuffix(compareData);
	}
	public static String getFieldName(CompareDataM compareData){
		if(null==compareData) return "";
		return  getFieldName(compareData.getFieldNameType(),compareData.getUniqueLevel(),compareData.getUniqueId());
	}
	public static String getFieldName(String fieldNameType,String uniqueLevel,String uniqueId){
		return fieldNameType+"_"+uniqueLevel+"_"+uniqueId;
	}
	public static String getSuffixFieldName(String uniqueLevel, String uniqueLevelId){
		return uniqueLevel+"_"+uniqueLevelId;
	}
	public static ArrayList<CompareDataM> getPrevCompareDataMByFieldNameType(ApplicationGroupDataM applicationGroup,String fieldNameType){
		ArrayList<CompareDataM> prevCompareDataList  = new ArrayList<CompareDataM>();
		ComparisonGroupDataM prevRoleData = applicationGroup.getComparisonGroups(CompareDataM.SoruceOfData.PREV_ROLE);		
		HashMap<String,CompareDataM> prevCompareList = prevRoleData.getComparisonFields();
		if(!Util.empty(prevCompareList) && !Util.empty(fieldNameType)){
			for(CompareDataM compareDataM :prevCompareList.values()){
				String prevFieldNameType = compareDataM.getFieldNameType();
				if(fieldNameType.equals(prevFieldNameType)){
					prevCompareDataList.add(compareDataM);
				}
			}
		}
		
		return prevCompareDataList;
	}
	public static ArrayList<CompareDataM> getPrevCompareDataMByPersonalInfo(ApplicationGroupDataM applicationGroup,String fieldNameType,PersonalInfoDataM personalInfo){
		ArrayList<CompareDataM> prevCompareDataList  = new ArrayList<CompareDataM>();
		ComparisonGroupDataM prevRoleData = applicationGroup.getComparisonGroups(CompareDataM.SoruceOfData.PREV_ROLE);		
		HashMap<String,CompareDataM> prevCompareList = prevRoleData.getComparisonFields();
		if(!Util.empty(prevCompareList) && !Util.empty(fieldNameType)){
			for(CompareDataM compareDataM :prevCompareList.values()){
				String prevFieldNameType = compareDataM.getFieldNameType();
				FieldConfigDataM preFidConfigM = JSonStringToFieldConfigDataM(compareDataM.getConfigData());
				String prePersonalType = preFidConfigM.getPersonalType();
				int prePersonalSeq =preFidConfigM.getSeq(); 
				logger.debug("prevFieldNameType>>>>"+prevFieldNameType);
				logger.debug("currFieldNameType>>>>"+fieldNameType);
				logger.debug("prePersonalType>>>>"+prePersonalType);
				String personalType = personalInfo.getPersonalType();
				int personalSeq = personalInfo.getSeq();
				logger.debug("personalType>>>>"+personalType);
				logger.debug("personalSeq>>>>"+personalSeq);
				
				if(fieldNameType.equals(prevFieldNameType) && personalType.equals(prePersonalType) && personalSeq == prePersonalSeq){
					prevCompareDataList.add(compareDataM);
				}
			}
		}
		
		return prevCompareDataList;
	}
	public static CompareDataM prevCompareDataM(ApplicationGroupDataM applicationGroup,CompareDataM currCompareData){
		CompareDataM prevCompareData  = new CompareDataM();
		FieldConfigDataM currFieldConfigData = CompareSensitiveFieldUtil.JSonStringToFieldConfigDataM(currCompareData.getConfigData());      
		if(PERSONAL_TYPE_SUPPLEMENTARY.equals(currFieldConfigData.getPersonalType())){
			prevCompareData = prevSupplementaryCompareDataM(applicationGroup, currCompareData, currFieldConfigData);
		}else{
			prevCompareData = prevApplicantCompareDataM(applicationGroup, currCompareData.getFieldName());
		}
		return prevCompareData;
	}
	public static CompareDataM prevCompareDataMRefByProduct(ApplicationGroupDataM applicationGroup,CompareDataM currCompareData){
		FieldConfigDataM currFieldConfigData = CompareSensitiveFieldUtil.JSonStringToFieldConfigDataM(currCompareData.getConfigData());      
		ComparisonGroupDataM prevRoleData = applicationGroup.getComparisonGroups(CompareDataM.SoruceOfData.PREV_ROLE);		
		HashMap<String,CompareDataM> prevCompareList = prevRoleData.getComparisonFields();
		if(!Util.empty(prevCompareList)){
			for(CompareDataM preCompareData : prevCompareList.values()){
				FieldConfigDataM preFieldConfigData = JSonStringToFieldConfigDataM(preCompareData.getConfigData());
				String prePersonalType = preFieldConfigData.getPersonalType();
				String preProduct = preFieldConfigData.getProduct();
				int preSeq = preFieldConfigData.getSeq();
				if(currCompareData.getFieldNameType().equals(preCompareData.getFieldNameType()) && 
				   currFieldConfigData.getPersonalType().equals(prePersonalType) &&
				   currFieldConfigData.getProduct().equals(preProduct)&&
				   currFieldConfigData.getSeq()==preSeq){
					return preCompareData;
				}
			}
			
		}
		
		return null;
	}

	public static CompareDataM filterCompareDataM(HashMap<String,CompareDataM> compareDataList ,CompareDataM filterCompareData ){
		if(!Util.empty(compareDataList) && !Util.empty(filterCompareData)){
			FieldConfigDataM filterFieldConfigData = JSonStringToFieldConfigDataM(filterCompareData.getConfigData());
			for(CompareDataM compareData : compareDataList.values()){
				FieldConfigDataM fieldConfigData = JSonStringToFieldConfigDataM(compareData.getConfigData());
				String personalType = fieldConfigData.getPersonalType();
				int seq = fieldConfigData.getSeq();
				if(filterCompareData.getFieldNameType().equals(compareData.getFieldNameType()) && 
						filterCompareData.getUniqueLevel().equals(compareData.getUniqueLevel()) && 
						filterFieldConfigData.getPersonalType().equals(personalType) &&
						filterCompareData.getSeq()==seq){
					return compareData;
				}
			}
			
		}
		
		return null;
	}
		
	public static CompareDataM prevApplicantCompareDataM(ApplicationGroupDataM applicationGroup,String fieldName){
		ComparisonGroupDataM prevRoleData = applicationGroup.getComparisonGroups(CompareDataM.SoruceOfData.PREV_ROLE);		
		HashMap<String,CompareDataM> prevCompareList = prevRoleData.getComparisonFields();
		if(!Util.empty(fieldName) && !Util.empty(prevCompareList)){
			return prevCompareList.get(fieldName);
		}
		return null;
	}
	
	public static CompareDataM prevSupplementaryCompareDataM(ApplicationGroupDataM applicationGroup,CompareDataM currCompareData, FieldConfigDataM currFieldConfigData){
		ComparisonGroupDataM prevRoleData = applicationGroup.getComparisonGroups(CompareDataM.SoruceOfData.PREV_ROLE);		
		HashMap<String,CompareDataM> hPrevCompare = prevRoleData.getComparisonFields();
		if(!Util.empty(hPrevCompare) && !Util.empty(currFieldConfigData) && !Util.empty(currCompareData)){
			String currFieldNameType = currCompareData.getFieldNameType();
			int currSeq = currFieldConfigData.getSeq();
			String currPersonalType = currFieldConfigData.getPersonalType();			
			ArrayList<String> keySets = new ArrayList<String>(hPrevCompare.keySet());
			for(String preKey : keySets){
				logger.debug("preKey>>>"+preKey);
				CompareDataM preCompareData =(CompareDataM)hPrevCompare.get(preKey);
				FieldConfigDataM preFieldConfigData = JSonStringToFieldConfigDataM(preCompareData.getConfigData());
				if(Util.empty(preFieldConfigData)){
					preFieldConfigData = new FieldConfigDataM();
				}
				String preFieldNameType = preCompareData.getFieldNameType();
				int preSeq = Util.empty(preFieldConfigData.getSeq())?0:preFieldConfigData.getSeq();
				String prePersonalType =preFieldConfigData.getPersonalType();
				if(currSeq==preSeq && currFieldNameType.equals(preFieldNameType) && currPersonalType.equals(prePersonalType)){
					return preCompareData;
				}
			}
		}
		return null;
	}
	
	public static String fieldConfigDataMToJSonString(Object objectModel,String productName){
		FieldConfigDataM  fieldConfigDataM = new FieldConfigDataM();
		if(!Util.empty(objectModel)){
			if(objectModel instanceof ApplicationGroupDataM){
				ApplicationGroupDataM applicationGroup = (ApplicationGroupDataM)objectModel;
				fieldConfigDataM.setGroupDataId(applicationGroup.getApplicationGroupId());
				fieldConfigDataM.setGroupDataLevel(CompareDataM.GroupDataLevel.APPLICATION_GROUP);
			}else if(objectModel instanceof PersonalInfoDataM){
				PersonalInfoDataM personalInfo = (PersonalInfoDataM)objectModel;
				fieldConfigDataM.setGroupDataId(personalInfo.getPersonalId());
				fieldConfigDataM.setPersonalType(personalInfo.getPersonalType());
				fieldConfigDataM.setPersonalId(personalInfo.getPersonalId());
				fieldConfigDataM.setIdNo(personalInfo.getIdno());
				fieldConfigDataM.setSeq(personalInfo.getSeq());
				String groupDataLevel =CompareDataM.GroupDataLevel.PERSONAL_APPLICANT;
				if(PERSONAL_TYPE_SUPPLEMENTARY.equals(personalInfo.getPersonalType())){
					groupDataLevel= CompareDataM.GroupDataLevel.PERSONAL_SUPPLEMENTATY;
			}
			fieldConfigDataM.setGroupDataLevel(groupDataLevel);
			if(!Util.empty(productName)){
				fieldConfigDataM.setProduct(productName);
			}
			}
		}
		Gson gson = new Gson();
		return gson.toJson(fieldConfigDataM);
	}
	
	public static String unCompareFieldConfigDataMToJSonString(Object objectModel,String productName){
//		logger.debug("objectModel : "+objectModel);
		FieldConfigDataM  fieldConfigDataM = new FieldConfigDataM();
		if(!Util.empty(objectModel)){
			if(objectModel instanceof ApplicationGroupDataM){
				ApplicationGroupDataM applicationGroup = (ApplicationGroupDataM)objectModel;
				fieldConfigDataM.setGroupDataId(applicationGroup.getApplicationGroupId());
				fieldConfigDataM.setGroupDataLevel(CompareDataM.GroupDataLevel.APPLICATION_GROUP);
				fieldConfigDataM.setIsCompareSensitiveField(false);
			}else if(objectModel instanceof PersonalInfoDataM){
				PersonalInfoDataM personalInfo = (PersonalInfoDataM)objectModel;
				fieldConfigDataM.setGroupDataId(personalInfo.getPersonalId());
				fieldConfigDataM.setPersonalType(personalInfo.getPersonalType());
				fieldConfigDataM.setPersonalId(personalInfo.getPersonalId());
				fieldConfigDataM.setIdNo(personalInfo.getIdno());
				fieldConfigDataM.setSeq(personalInfo.getSeq());
				String groupDataLevel =CompareDataM.GroupDataLevel.PERSONAL_APPLICANT;
				if(PERSONAL_TYPE_SUPPLEMENTARY.equals(personalInfo.getPersonalType())){
					groupDataLevel= CompareDataM.GroupDataLevel.PERSONAL_SUPPLEMENTATY;
				}
				fieldConfigDataM.setGroupDataLevel(groupDataLevel);
				fieldConfigDataM.setIsCompareSensitiveField(false);
				if(!Util.empty(productName)){
					fieldConfigDataM.setProduct(productName);
				}
			}
		}
		Gson gson = new Gson();
		return gson.toJson(fieldConfigDataM);
	}
	
	public static String fieldConfigDataMToJSonString(PersonalInfoDataM personalInfo,String productName,String cardNumber){
		FieldConfigDataM  fieldConfigDataM = new FieldConfigDataM();
		if(!Util.empty(personalInfo)){
			fieldConfigDataM.setGroupDataId(personalInfo.getPersonalId());
			fieldConfigDataM.setPersonalType(personalInfo.getPersonalType());
			fieldConfigDataM.setPersonalId(personalInfo.getPersonalId());
			fieldConfigDataM.setIdNo(personalInfo.getIdno());
			fieldConfigDataM.setSeq(personalInfo.getSeq());
			fieldConfigDataM.setCardNumber(cardNumber);
			String groupDataLevel =CompareDataM.GroupDataLevel.PERSONAL_APPLICANT;
			if(PERSONAL_TYPE_SUPPLEMENTARY.equals(personalInfo.getPersonalType())){
				groupDataLevel= CompareDataM.GroupDataLevel.PERSONAL_SUPPLEMENTATY;
		}
		fieldConfigDataM.setGroupDataLevel(groupDataLevel);
		if(!Util.empty(productName)){
			fieldConfigDataM.setProduct(productName);
		}
	    }
		Gson gson = new Gson();
		return gson.toJson(fieldConfigDataM);
	}
	public static FieldConfigDataM JSonStringToFieldConfigDataM(String fieldConfigdata){
		if(!Util.empty(fieldConfigdata)){
			Gson gson = new Gson();
			return gson.fromJson(fieldConfigdata,FieldConfigDataM.class);
		}
		return null;
	}
	
	public static int getCompareFieldSeq(String fieldNameType){
		if(!Util.empty(fieldNameType)){
			String seqString =  CacheControl.getName(CACHE_COMPARE_SENSITIVE, "IMPLEMENT_ID",fieldNameType, "SEQ");
			if(Util.empty(seqString)) return 0;
			return Integer.parseInt(seqString);
		}
		return 0;
	}
	public static String getCompareElementFieldType(CompareDataM currCompareData){
		FieldConfigDataM currFieldConfigData = CompareSensitiveFieldUtil.JSonStringToFieldConfigDataM(currCompareData.getConfigData());  
		if(Util.empty(currFieldConfigData)) return "";
		String groupDataLevel = currFieldConfigData.getGroupDataLevel();
		String personalSeq =FormatUtil.toString(currFieldConfigData.getSeq());
		if(CompareDataM.GroupDataLevel.APPLICATION_GROUP.equals(groupDataLevel)){
			return groupDataLevel;
		}
		return groupDataLevel+personalSeq;
	}
	public static String getCompareElementFieldTypeAppcalition(String cardType){
		return CompareDataM.GroupDataLevel.CARD_INFORMATION+cardType;
	}
	public static String getCompareElementFieldTypePersonal(String personalType,String personalSeq){
		if(PERSONAL_TYPE_SUPPLEMENTARY.equals(personalType)){
			return CompareDataM.GroupDataLevel.PERSONAL_SUPPLEMENTATY+personalSeq;
		}
		return CompareDataM.GroupDataLevel.PERSONAL_APPLICANT+personalSeq;
	}
	
	public static ArrayList<CompareDataM> filterCompareDataByPersonalInfo(ArrayList<CompareDataM> compareDataList,String fieldNameType,PersonalInfoDataM personalInfo){
		ArrayList<CompareDataM> filterCompareDataList = new ArrayList<CompareDataM>();
		if(!Util.empty(compareDataList) && !Util.empty(personalInfo)){
			int personalSeq = personalInfo.getSeq();
			String personalType = personalInfo.getPersonalType();
			for(CompareDataM compareData :compareDataList){
				if(!CompareDataM.CompareDataType.DUMMY.equals(compareData.getCompareDataType())){
					FieldConfigDataM currFieldConfigData = CompareSensitiveFieldUtil.JSonStringToFieldConfigDataM(compareData.getConfigData()); 
					if(fieldNameType.equals(compareData.getFieldNameType()) && personalType.equals(currFieldConfigData.getPersonalType()) &&  personalSeq==currFieldConfigData.getSeq()){
						filterCompareDataList.add(compareData);
					}
				}
			}
		}
		return filterCompareDataList;
	}
	public static String getGroupDataLevelByPersonal(PersonalInfoDataM personalInfo){
		if(PERSONAL_TYPE_SUPPLEMENTARY.equals(personalInfo.getPersonalType())){
			return CompareDataM.GroupDataLevel.PERSONAL_SUPPLEMENTATY;
		}
		return CompareDataM.GroupDataLevel.PERSONAL_APPLICANT;
	}
	
	public static void setNoCurrentProduct(ApplicationGroupDataM applicationGroup,ArrayList<CompareDataM> compareList,String fieldNameType){
		if(Util.empty(compareList) && compareList.size()==0){
			CompareDataM compareDataM = new CompareDataM();
			compareDataM.setApplicationGroupId(applicationGroup.getApplicationGroupId());
			compareDataM.setFieldNameType(fieldNameType);
			compareDataM.setRefLevel(CompareDataM.RefLevel.CARD);
			compareDataM.setSrcOfData(CompareDataM.SoruceOfData.TWO_MAKER);	
			compareDataM.setUniqueId("");
			compareDataM.setUniqueLevel(CompareDataM.UniqueLevel.APPLICATION);	
			compareDataM.setCompareDataType(CompareDataM.CompareDataType.DUMMY);
			compareDataM.setSeq(CompareSensitiveFieldUtil.getCompareFieldSeq(fieldNameType));
			compareDataM.setFieldName(CompareSensitiveFieldUtil.getFieldName(compareDataM)); 
			compareList.add(compareDataM);
		}
	}
	
	public static boolean existSrcOfData(ApplicationGroupDataM applicationGroup,String srcOfData){
		ArrayList<ComparisonGroupDataM>	comparisonGroups = applicationGroup.getComparisonGroups();
		if(null != comparisonGroups){
			for (ComparisonGroupDataM comparisonGroup: comparisonGroups) {
				if(null != comparisonGroup && comparisonGroup.getSrcOfData().equals(srcOfData)){
					if(!Util.empty(comparisonGroup.getComparisonFields())&&comparisonGroup.getComparisonFields().size()>0){
						return true;
					}
				}
			}
		}
		return false;
	}
	
	public static boolean isCompareSensitiveField(String configData){
		try {
			 if(!Util.empty(configData)){
				 FieldConfigDataM fieldConfigDataM=JSonStringToFieldConfigDataM(configData);
				 return fieldConfigDataM.isCompareSensitiveField();
			 }
			
		} catch (Exception e) {
			logger.fatal("ERROR",e);
		}
		return true;
	}
	
	public static boolean isPreviousCompareData(ApplicationGroupDataM applicationGroup, String fieldConfigId, Object objectElement){	
		boolean isContainPrevData = false;
		String fieldNameType =fieldConfigId;
		String fieldName="";
		ComparisonGroupDataM prevRoleData = applicationGroup.getComparisonGroups(CompareDataM.SoruceOfData.PREV_ROLE);		
		HashMap<String,CompareDataM> prevCompareList = prevRoleData.getComparisonFields();
		
		if(Util.empty(objectElement)){
			return false;
		}
		
		if(objectElement instanceof ApplicationGroupDataM){
			fieldName = CompareSensitiveFieldUtil.getFieldName(fieldNameType, CompareDataM.UniqueLevel.APPLICATION_GROUP, applicationGroup.getApplicationGroupId());
		}else if(objectElement instanceof PersonalInfoDataM){
			PersonalInfoDataM personalInfo = (PersonalInfoDataM)objectElement;
			fieldNameType = FieldNameTypeUtil.getFieldNameType(fieldConfigId,  personalInfo);
			fieldName = CompareSensitiveFieldUtil.getFieldName(fieldNameType, CompareDataM.UniqueLevel.PERSONAL, personalInfo.getPersonalId());
		}else if(objectElement instanceof AddressDataM){
			AddressDataM address = (AddressDataM)objectElement;
			fieldNameType = FieldNameTypeUtil.getFieldNameType(fieldConfigId,  address);
			fieldName = CompareSensitiveFieldUtil.getFieldName(fieldNameType, CompareDataM.UniqueLevel.ADDRESS, address.getAddressId());
		}else if(objectElement instanceof ApplicationDataM){
			ApplicationDataM applicationDataM = (ApplicationDataM)objectElement;
			fieldName = CompareSensitiveFieldUtil.getFieldName(fieldNameType, FieldNameTypeUtil.getFieldName(fieldConfigId,  applicationDataM), applicationDataM.getApplicationRecordId());
		}else if(objectElement instanceof PaymentMethodDataM){
			PaymentMethodDataM paymentMethodDataM = (PaymentMethodDataM)objectElement;
			fieldName = CompareSensitiveFieldUtil.getFieldName(fieldNameType, CompareDataM.UniqueLevel.PAYMENT_METHOD, paymentMethodDataM.getPaymentMethodId());
		}
		
		
		logger.debug("fieldNameType >> "+fieldNameType);
		logger.debug("fieldName >> "+fieldName);
		logger.debug("prevCompareList >> "+prevCompareList.get(fieldName));
		CompareDataM prevCompareData = prevCompareList.get(fieldName);
		if(!Util.empty(prevCompareData) && !Util.empty(prevCompareData.getValue())){
			isContainPrevData= true;
		}
		logger.debug("isContainPrevData >> "+isContainPrevData);
		return isContainPrevData;
	}
}
