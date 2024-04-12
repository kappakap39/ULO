package com.eaf.orig.ulo.pl.ajax;

import java.util.Vector;
import javax.servlet.http.HttpServletRequest;
import org.apache.log4j.Logger;
import com.eaf.orig.cache.properties.MainProductFamilyProperties;
import com.eaf.orig.shared.servlet.AjaxDisplayGenerateInf;
import com.eaf.orig.shared.utility.ORIGCacheUtil;
import com.eaf.orig.shared.utility.ORIGUtility;
import com.eaf.orig.ulo.pl.app.utility.HTMLRenderUtil;

/**
 * @author Pipe 05/03/12
 *
 * Type: GetProductFamFromProductGroup
 */

public class GetProductFamFromProductGroup implements AjaxDisplayGenerateInf {
	
	Logger log = Logger.getLogger(GetProductFamFromProductGroup.class);
	
	public String getDisplayObject(HttpServletRequest request) {
        String productgroup = (String)request.getParameter("productgroup");
        
            ORIGCacheUtil cacheUtil = ORIGCacheUtil.getInstance();  
            MainProductFamilyProperties productObj = new MainProductFamilyProperties();

            Vector vProductFam = cacheUtil.loadCacheByActive("MainProductFamily");
            Vector vProductFamTmp = new Vector();
            
            if(!ORIGUtility.isEmptyVector(vProductFam)){
				for (int i = 0; i < vProductFam.size(); i++) {
					productObj = (MainProductFamilyProperties)vProductFam.get(i);
						if(null != productObj && productObj.getmainProductGroupCode() != null 
							&& !"ALL".equals(productObj.getmainProductGroupCode())
								&& productObj.getmainProductGroupCode().equals(productgroup)){
						        vProductFamTmp.add(productObj);
						}
				}
			}
            
			return HTMLRenderUtil.displaySelectScriptAutoSelectListBox(vProductFamTmp, "","productfamily",HTMLRenderUtil.DISPLAY_MODE_EDIT," style=\"width:165px;\" onChange=\"javascript:getProduct()\"");

    }
        
}
