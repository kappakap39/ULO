/*
 * Created on Dec 8, 2008
 * Created by wichaya
 * 
 * Copyright (c) 2007 Avalant Co.,Ltd.
 * 20 North Sathorn Road, 15-16th Floor Bubhajit Bldg., Silom, Bangrak, Bangkok 10500, Thailand
 * All rights reserved.
 *
 * This software is the confidential and prorietary infomation of
 * Avalant Co.,Ltd. ("Confidential Infomation"). You shall not
 * disclose such Confidential Infomation and shall use it only in
 * accordance with the terms of the license agreement you entered into
 * with Avalant Co.,Ltd.
 * 
 */
package com.eaf.orig.ulo.pl.ajax;

import java.util.Vector;

import javax.servlet.http.HttpServletRequest;

import org.apache.log4j.Logger;

import com.eaf.orig.cache.properties.MainProductGroupProperties;
//import com.eaf.orig.cache.properties.ProductProperties;
import com.eaf.orig.shared.servlet.AjaxDisplayGenerateInf;
import com.eaf.orig.shared.utility.ORIGCacheUtil;
import com.eaf.orig.shared.utility.ORIGUtility;
import com.eaf.orig.ulo.pl.app.utility.HTMLRenderUtil;

/**
 * @author Pipe 05/03/12
 *
 * Type: GetProductGrpFromProductDomain
 */

public class GetProductGrpFromProductDomain implements AjaxDisplayGenerateInf {
	
	Logger log = Logger.getLogger(GetProductGrpFromProductDomain.class);

    public String getDisplayObject(HttpServletRequest request) {
        String productDomain = (String)request.getParameter("productdomain");
        
//        log.debug("[getDisplayObject] productDomain = "+productDomain);

        ORIGCacheUtil cacheUtil = ORIGCacheUtil.getInstance();    
        MainProductGroupProperties productObj = new MainProductGroupProperties();
        
        Vector vProductGrp = cacheUtil.loadCacheByActive("MainProductGroup");
        Vector vProductGrpTmp = new Vector();           
        if(!ORIGUtility.isEmptyVector(vProductGrp)){
			for (int i = 0; i < vProductGrp.size(); i++) {
				productObj = (MainProductGroupProperties) vProductGrp.get(i);
			    if(productObj.getmainProductDomainCode() != null && productObj.getmainProductDomainCode().equals(productDomain)){
			        vProductGrpTmp.add(productObj);
			    }
			}
        }
		return	HTMLRenderUtil.displaySelectScriptAutoSelectListBox(vProductGrpTmp,"","productgroup",HTMLRenderUtil.DISPLAY_MODE_EDIT," style=\"width: 165px;\" onChange=\"javascript:getProductFamily()\"");
		
    }
    
}
