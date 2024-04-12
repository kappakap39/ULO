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
public class GetPropertyType implements AjaxDisplayGenerateInf {
	static Logger log = Logger.getLogger(GetPropertyType.class);

	/* (non-Javadoc)
	 * @see com.eaf.orig.shared.servlet.AjaxDisplayGenerateInf#getDisplayObject(javax.servlet.http.HttpServletRequest)
	 */
	public String getDisplayObject(HttpServletRequest request) {
		// TODO Auto-generated method stub
		String collateralType = (String)request.getParameter("collateralType");
		String propertyType = (String)request.getParameter("propertyType");
		String displayMode = (String)request.getParameter("displayMode");
		log.debug("getDisplayObject collateralType = "+collateralType+" : propertyType = "+propertyType+" : displayMode = "+displayMode);
        if(collateralType!=null){
        	ORIGCacheUtil cacheUtil = ORIGCacheUtil.getInstance();
        	Vector propertyTypeVect = cacheUtil.getNaosCacheDataMs("AL_ALL_ALL",29);

            Vector vPropertyTypeTmp = new Vector();
            String xmlObj = "";
            
			for (int i = 0; i < propertyTypeVect.size(); i++) {
			    ORIGCacheDataM tempPropertyTypeCache = (ORIGCacheDataM) propertyTypeVect.get(i);
			    if (tempPropertyTypeCache.getValueDesc()!=null){
				    String split[] = tempPropertyTypeCache.getValueDesc().split("_");
			        if (split!=null && split.length>0){
			            for (int j=0;j<split.length;j++){
						    if (collateralType.equalsIgnoreCase(split[j])){
						        vPropertyTypeTmp.add(tempPropertyTypeCache);
						        break;
						    }
			            }
			        }
			    }
			}
			
			xmlObj = ORIGDisplayFormatUtil.displaySelectTagScriptAction_ORIG(vPropertyTypeTmp,propertyType ,"propertyType",displayMode,"onChange=\"javascript:getPropertyOnDemand();\" style=\"width:80%;\" textbox");

			AjaxUtils util = new AjaxUtils();			
			String xmlStr = util.getXML4OneItem("div_propertyType",xmlObj);
			return xmlStr;
        }
		return null;
	}

}
