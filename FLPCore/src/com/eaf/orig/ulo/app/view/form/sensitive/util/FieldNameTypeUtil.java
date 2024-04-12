package com.eaf.orig.ulo.app.view.form.sensitive.util;

import java.util.Arrays;

import org.apache.log4j.Logger;

import com.eaf.core.ulo.common.properties.SystemConstant;
import com.eaf.core.ulo.common.util.Util;
import com.eaf.orig.ulo.model.app.AddressDataM;
import com.eaf.orig.ulo.model.app.ApplicationDataM;
import com.eaf.orig.ulo.model.app.PersonalInfoDataM;
import com.eaf.orig.ulo.model.compare.CompareDataM;

public class FieldNameTypeUtil {
	private static transient Logger logger = Logger.getLogger(FieldNameTypeUtil.class);
	private static String ADDRESS_TYPE_CURRENT  =SystemConstant.getConstant("ADDRESS_TYPE_CURRENT");
	private static String ADDRESS_TYPE_WORK  =SystemConstant.getConstant("ADDRESS_TYPE_WORK");
	private static String ADDRESS_TYPE_DOCUMENT  =SystemConstant.getConstant("ADDRESS_TYPE_DOCUMENT");
	private static String ADDRESS_TYPE_NATION  =SystemConstant.getConstant("ADDRESS_TYPE_NATION");
	private static String ADDRESS_TYPE_VAT  =SystemConstant.getConstant("ADDRESS_TYPE_VAT");
	
	private static String PREFIX_FIELD_CURRENT_ADDRESS = "CURRENT_";
	private static String PREFIX_FIELD_WORK_ADDRESS = "COMPANY_";
	private static String PREFIX_FIELD_DOCUMENT_ADDRESS = "DOCUMENT_";
	private static String PREFIX_FIELD_NATION_ADDRESS = "NATION_";
	private static String PREFIX_FIELD_VAT_ADDRESS = "VAT_";
	
	private static String FIELD_NAME_PERSONAL_TYPE  ="PERSONAL_TYPE";
	private static String FIELD_NAME_ADD_SUP_PERSONAL_INFO  ="ADD_SUP_PERSONAL_INFO";
	private static String FIELD_NAME_TH_FIRST_NAME  ="TH_FIRST_NAME";
	
	private static String[] EXCEPTION_CURRENT_ADDRESS_FILE_NAME ={"HOME_PHONE","HOME_PHONE_EXT"};
	private static String[] EXCEPTION_CARD_INFORMATION_FILE_NAME ={"PERCENT_LIMIT_MAINCARD"};
	private static String[] EXCEPTION_WORK_ADDRESS_FILE_NAME ={"PHONE1_WORK","PHONE1_WORK_EXT","COMPANY_TITLE","COMPANY_NAME"};
	
	public static String getFieldNameType(String fieldName,AddressDataM address) {
		String addressType = address.getAddressType();
		logger.debug("addressType>>>"+addressType);
		logger.debug("fieldName>>>"+fieldName);
		
		if(ADDRESS_TYPE_CURRENT.equals(addressType) && !Arrays.asList(EXCEPTION_CURRENT_ADDRESS_FILE_NAME).contains(fieldName)){
			return PREFIX_FIELD_CURRENT_ADDRESS+fieldName;
		}else if(ADDRESS_TYPE_WORK.equals(addressType) && !Arrays.asList(EXCEPTION_WORK_ADDRESS_FILE_NAME).contains(fieldName)){
			return PREFIX_FIELD_WORK_ADDRESS+fieldName;
		}else if(ADDRESS_TYPE_DOCUMENT.equals(addressType)){
			return PREFIX_FIELD_DOCUMENT_ADDRESS+fieldName;
		}else if(ADDRESS_TYPE_NATION.equals(addressType)){
			return PREFIX_FIELD_NATION_ADDRESS+fieldName;
		}else if(ADDRESS_TYPE_VAT.equals(addressType)){
			return PREFIX_FIELD_VAT_ADDRESS+fieldName;
		}
		
		return fieldName;
	}
	public static String getFieldNameType(String fieldName,PersonalInfoDataM personalInfo) {
		 
		if(FIELD_NAME_PERSONAL_TYPE.equals(fieldName) || FIELD_NAME_ADD_SUP_PERSONAL_INFO.equals(fieldName)){
			return FIELD_NAME_TH_FIRST_NAME;
		} 
		
		return fieldName;
	}
	
	public static String getFieldName(String fieldName,ApplicationDataM application) {
		 
		if(!Util.empty(fieldName) && Arrays.asList(EXCEPTION_CARD_INFORMATION_FILE_NAME).contains(fieldName)){
			return CompareDataM.UniqueLevel.CARD_INFORMATION;
		} 
		
		return CompareDataM.UniqueLevel.APPLICATION;
	}
}