package com.eaf.service.common.activemq;

import java.util.List;

import com.eaf.orig.ulo.model.app.PersonalInfoDataM;



public class MyCommonUtils {

	public static boolean isCollectionNotEmpty(Object list){
		if(list==null){
			return false;
		}
//		if(list.size()==0){
//			return false;
//		}
		return true;
	}
	
}
