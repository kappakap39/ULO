package com.eaf.orig.ulo.app.view.form.privilegeprojectcode.util;

import java.util.ArrayList;
import java.util.Arrays;

import org.apache.log4j.Logger;

import com.eaf.core.ulo.common.properties.SystemConfig;
import com.eaf.core.ulo.common.properties.SystemConstant;
public class PrivilegeUtil {
	private static transient Logger logger = Logger.getLogger(PrivilegeUtil.class);
	public static final String  WISDOM_DESC = SystemConstant.getConstant("PRVLG_CARD_TYPE_WISDOM");
	public static final String  PREMIER_DESC = SystemConstant.getConstant("PRVLG_CARD_TYPE_PREMIER");
	public static final String  INFINITE_DESC = SystemConstant.getConstant("PRVLG_CARD_TYPE_INFINITE");
	public static final String  GENERIC_DESC = SystemConstant.getConstant("PRVLG_CARD_TYPE_GENERIC");
	
	public static ArrayList<String> ccInfinitesCardType(){
		return new ArrayList<String>(Arrays.asList(SystemConfig.getGeneralParam("TEMPLATE_CC_INFINITE").split(",")));
	}
	public static ArrayList<String> ccWisdomsCardType(){
		return new ArrayList<String>(Arrays.asList(SystemConfig.getGeneralParam("TEMPLATE_CC_WISDOM").split(",")));
	}
	public static ArrayList<String> ccPremierCardType(){
		return new ArrayList<String>(Arrays.asList(SystemConfig.getGeneralParam("TEMPLATE_CC_PREMIER").split(",")));
	}
	
	public static String getCardTypeDesc(String templateId){
		if(null==templateId || "".equals(templateId)){
			return "-";
		}
		 if(ccInfinitesCardType().contains(templateId)){
			 return INFINITE_DESC;
		}else if(ccWisdomsCardType().contains(templateId)){
			return WISDOM_DESC;
		}else if(ccPremierCardType().contains(templateId)){
			return PREMIER_DESC;
		}else {
			return GENERIC_DESC;
		}
	}
	
	
	
}
