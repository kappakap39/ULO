package com.eaf.service.common.util;

import com.eaf.service.common.api.ServiceCache;

public class CisGeneralUtil {
	
	public static String displayCISHeaderUserId(String str){
		if(ServiceUtil.empty(str)){
			return "";
		}else if(str.length()<8){
			return str;
		}else if(ServiceCache.getConstant("PREFIX_USER_KH").equals(str.substring(0, 2))){
			return ServiceCache.getConstant("PREFIX_USER_H")+str.substring(3, 8);
		}
		return str.substring(2,8);
	}
}
