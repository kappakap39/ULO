package com.eaf.service.common.util;

import org.apache.log4j.Logger;

import com.eaf.service.common.api.ServiceCache;
import com.eaf.service.common.util.ServiceUtil;

public class CisApplicantUtil {
	private static transient Logger logger = Logger.getLogger(CisApplicantUtil.class);
	
	public static String mappingProfessionRiskGroup(String businessType){
		String[] BUSINESS_TYPE_RISK_GROUP = ServiceCache.getArrayConstant("BUSINESS_TYPE_RISK_GROUP");
		String FIELD_ID_BUSINESS_TYPE = ServiceCache.getConstant("FIELD_ID_BUSINESS_TYPE");
		String FIELD_ID_PROFESSION = ServiceCache.getConstant("FIELD_ID_PROFESSION");
		String professionCd = "";
		if(!ServiceUtil.empty(businessType)){
			logger.debug("mappingProfessionRiskGroup businessType >>"+businessType);
			for(int i = 0; i< BUSINESS_TYPE_RISK_GROUP.length ;i++){
				if(BUSINESS_TYPE_RISK_GROUP[i].equals(businessType)){
					String choiceNoProf = ServiceCache.getName(FIELD_ID_BUSINESS_TYPE, businessType, "SYSTEM_ID3"); 
					professionCd = ServiceCache.getName(FIELD_ID_PROFESSION, choiceNoProf, "MAPPING4"); 
				}
			}
			logger.debug("mappingProfessionRiskGroup professionCd >>"+professionCd);
		}
		return professionCd;
	}
	public static String mappingProfessionOther(String professionCode){
		String[] PROFESSION_OTHER_GROUP = ServiceCache.getArrayConstant("PROFESSION_OTHER_GROUP");
		String FIELD_ID_PROFESSION = ServiceCache.getConstant("FIELD_ID_PROFESSION"); 
		String professionDesc = "";
		if(!ServiceUtil.empty(professionCode)){
			logger.debug("mappingProfessionOther professionCode >>"+professionCode);
				for(int i = 0; i< PROFESSION_OTHER_GROUP.length ;i++){
					if(PROFESSION_OTHER_GROUP[i].equals(professionCode)){
						professionDesc = ServiceCache.getName(FIELD_ID_PROFESSION, professionCode, "DISPLAY_NAME"); 
					}
				}
				logger.debug("mappingProfessionOther professionDesc >>"+professionDesc);
		}
		return professionDesc;
	}
}
