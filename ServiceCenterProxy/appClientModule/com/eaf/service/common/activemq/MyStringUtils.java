package com.eaf.service.common.activemq;

import java.util.List;

public class MyStringUtils {


	public static boolean isNotEmpty(String str){
		if(str==null){
			return false;
		}
		if("".equals(str)){
			return false;
		}
		return true;
	}
	
	public static boolean isEmpty(String str){
		if(str==null){
			return true;
		}
		if("".equals(str)){
			return true;
		}
		return false;
	}

}
