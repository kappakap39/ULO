package com.eaf.orig.ulo.dm.util;

import org.apache.log4j.Logger;

import com.eaf.core.ulo.common.display.HtmlUtil;
import com.eaf.core.ulo.common.properties.CacheControl;
import com.eaf.core.ulo.common.properties.SystemConstant;
import com.eaf.core.ulo.common.util.FormEffects;
import com.eaf.core.ulo.common.util.Util;
import com.eaf.orig.ulo.constant.MConstant;
import com.eaf.orig.ulo.model.dm.DocumentManagementDataM;

public class DocumentManageUtil {
	private static transient Logger logger = Logger.getLogger(DocumentManageUtil.class);
	private static String DM_APPLICATION_STATUS_REJECTED = SystemConstant.getConstant("DM_APPLICATION_STATUS_REJECTED");
	private static String DM_APPLICATION_STATUS_IN_PROCESS = SystemConstant.getConstant("DM_APPLICATION_STATUS_IN_PROCESS");
	private static String DM_CACHE_PRODUCT = SystemConstant.getConstant("DM_CACHE_PRODUCT");
	public static String PIPE = "\\|";
	
	public static boolean isCustomerRequestDocument(DocumentManagementDataM dmManageDataM){
		String appStatus  = dmManageDataM.getParam1();
		if(DM_APPLICATION_STATUS_REJECTED.equals(appStatus)
		  && MConstant.DM_STATUS.AVAILABLE.equals(dmManageDataM.getStatus())){
			return true;
		}		
		return false;
	}
	public static String displayProductDesc(String businessClass){
		StringBuilder productDescs = new StringBuilder("");
		logger.debug("businessClass>>"+businessClass);
		if(!Util.empty(businessClass)){
			String[]  busClassList = businessClass.split(PIPE);
			String SLASH="";
			for(String businesClassId : busClassList){
				String product = getProduct(businesClassId);
				logger.debug("businesClassId>>"+businesClassId);
				logger.debug("product>>"+product);
				String productDesc = CacheControl.getName(DM_CACHE_PRODUCT, product);
				logger.debug("productDesc>>>"+productDesc);
				if(!Util.empty(productDesc)){
					productDescs.append(SLASH+productDesc);
					SLASH="/";
				}
			}
		}	
		return productDescs.toString();
	}
	
	public static String getProduct(String businessClassId){
		return (null != businessClassId)?businessClassId.split("\\_")[0]:null;
	}
	
	public static String dmStoreMode(String applicationStatus){
		if(DM_APPLICATION_STATUS_IN_PROCESS.equals(applicationStatus)){
			return  HtmlUtil.VIEW;
		}
		return HtmlUtil.EDIT;
	}
	public static String dmButtonStoreMode(String applicationStatus){
		if(DM_APPLICATION_STATUS_IN_PROCESS.equals(applicationStatus)){
			return  FormEffects.Effects.HIDE;
		}
		return FormEffects.Effects.SHOW;
	}
}
