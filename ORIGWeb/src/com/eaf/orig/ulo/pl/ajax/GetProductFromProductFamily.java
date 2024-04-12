package com.eaf.orig.ulo.pl.ajax;

import java.util.Vector;

import javax.servlet.http.HttpServletRequest;

import org.apache.log4j.Logger;

import com.eaf.orig.cache.properties.MainProductProperties;
import com.eaf.orig.shared.servlet.AjaxDisplayGenerateInf;
import com.eaf.orig.shared.utility.ORIGCacheUtil;
import com.eaf.orig.shared.utility.ORIGUtility;
import com.eaf.orig.ulo.pl.app.utility.HTMLRenderUtil;

/**
 * @author Pipe 05/03/12
 * Type: GetProductFromProductFamily
 */

public class GetProductFromProductFamily implements AjaxDisplayGenerateInf {
	Logger log = Logger.getLogger(GetProductFromProductFamily.class);
	
	public String getDisplayObject(HttpServletRequest request){		
	        String productfamily = (String)request.getParameter("productfamily");
        	ORIGCacheUtil cacheUtil = ORIGCacheUtil.getInstance();  
            MainProductProperties productObj = new MainProductProperties();

            Vector vProduct = cacheUtil.loadCacheByActive("MainProduct");
            Vector tmp = new Vector();
            
            if(!ORIGUtility.isEmptyVector(vProduct)){
				for (int i = 0; i < vProduct.size(); i++) {
					productObj = (MainProductProperties)vProduct.get(i);
				    if(null != productObj && productObj.getmainProductFamilyCode() != null 
				    		&& !"ALL".equalsIgnoreCase(productObj.getCode())
				    			&& productObj.getmainProductFamilyCode().equals(productfamily)){
				    	tmp.add(productObj);
				    }
				}
            }
			return   HTMLRenderUtil.displaySelectScriptAutoSelectListBox(tmp,"KEC","product",HTMLRenderUtil.DISPLAY_MODE_EDIT," style=\"width:165px;\" onChange=\"javascript:getSaleType()\"");

    }

}
