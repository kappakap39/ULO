/*
 * Created on Dec 29, 2008
 *
 * TODO To change the template for this generated file go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
package com.eaf.orig.shared.ajax;

import java.util.Vector;

import javax.servlet.http.HttpServletRequest;

import org.apache.log4j.Logger;

import com.eaf.orig.cache.properties.ORIGCacheDataM;
import com.eaf.orig.shared.servlet.AjaxDisplayGenerateInf;
import com.eaf.orig.shared.utility.AjaxUtils;
import com.eaf.orig.shared.utility.ORIGCacheUtil;
import com.eaf.orig.shared.utility.ORIGDisplayFormatUtil;

/**
 * @author Administrator
 *
 * TODO To change the template for this generated type comment go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
public class GetPropertySubType implements AjaxDisplayGenerateInf {
	static Logger log = Logger.getLogger(GetPropertySubType.class);

	/* (non-Javadoc)
	 * @see com.eaf.orig.shared.servlet.AjaxDisplayGenerateInf#getDisplayObject(javax.servlet.http.HttpServletRequest)
	 */
	public String getDisplayObject(HttpServletRequest request) {
		// TODO Auto-generated method stub
		String collateralType = (String)request.getParameter("collateralType");
		String propertyType = (String)request.getParameter("propertyType");
		String propertySubType = (String)request.getParameter("propertySubType");
		String displayMode = (String)request.getParameter("displayMode");
		log.debug("getDisplayObject collateralType = "+collateralType+" : propertyType = "+propertyType+" : propertySubType = "+propertySubType+" : displayMode = "+displayMode);
        if(collateralType!=null && propertyType!=null){
            ORIGCacheUtil cacheUtil = ORIGCacheUtil.getInstance();
        	Vector propertySubTypeVect = cacheUtil.getNaosCacheDataMs("AL_ALL_ALL",30);
        	
        	Vector vPropertySubTypeTmp = new Vector();
            String xmlObj = "";
            
			for (int i = 0; i < propertySubTypeVect.size(); i++) {
			    ORIGCacheDataM tempPropertyTypeCache = (ORIGCacheDataM) propertySubTypeVect.get(i);
			    if (tempPropertyTypeCache.getValueDesc()!=null && tempPropertyTypeCache.getOtherDesc()!=null){
				    String splitValue[] = tempPropertyTypeCache.getValueDesc().split("_");
				    String splitOther[] = tempPropertyTypeCache.getOtherDesc().split("_");
			        if (splitValue!=null && splitValue.length>0 && splitOther!=null && splitOther.length>0){
			            for (int j=0;j<splitValue.length;j++){
			                if (collateralType.equalsIgnoreCase(splitValue[j])){
				                for (int k=0;k<splitOther.length;k++){
								    if (propertyType.equalsIgnoreCase(splitOther[k])){
								        vPropertySubTypeTmp.add(tempPropertyTypeCache);
								        break;
								    }
				                }
				                break;
			                }
			            }
			        }
			    }
			}
			
			xmlObj = ORIGDisplayFormatUtil.displaySelectTagScriptAction_ORIG(vPropertySubTypeTmp,propertySubType ,"propertySubType",displayMode," style=\"width:80%;\" textbox");

			AjaxUtils util = new AjaxUtils();			
			String xmlStr = util.getXML4OneItem("div_propertySubType",xmlObj);
			return xmlStr;
        }
		return null;
	}

}
