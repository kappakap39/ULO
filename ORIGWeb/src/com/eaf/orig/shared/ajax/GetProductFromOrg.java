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
package com.eaf.orig.shared.ajax;

import java.util.Vector;

import javax.servlet.http.HttpServletRequest;

import com.eaf.orig.cache.properties.ProductProperties;
import com.eaf.orig.shared.servlet.AjaxDisplayGenerateInf;
import com.eaf.orig.shared.utility.AjaxUtils;
import com.eaf.orig.shared.utility.ORIGDisplayFormatUtil;
import com.eaf.orig.shared.utility.ORIGUtility;

/**
 * @author wichaya
 *
 * Type: GetProductFromOrg
 */
public class GetProductFromOrg implements AjaxDisplayGenerateInf {

    /* (non-Javadoc)
     * @see com.eaf.orig.shared.servlet.AjaxDisplayGenerateInf#getDisplayObject(javax.servlet.http.HttpServletRequest)
     */
    public String getDisplayObject(HttpServletRequest request) {
        String orgType = (String)request.getParameter("orgType");
        String xmlObj = "";
        if(orgType!=null && !"".equals(orgType)){
            ORIGUtility utility = new ORIGUtility();
            ProductProperties productObj = new ProductProperties();
            Vector vProduct = utility.loadCacheByName("Product");
            Vector vProductTmp = new Vector();
          
            
			for (int i = 0; i < vProduct.size(); i++) {
			    productObj = (ProductProperties) vProduct.get(i);
			    if(orgType.equals(productObj.getOrgID())){
			        ProductProperties productObjTmp = new ProductProperties();
			        productObjTmp.setCode(productObj.getCode());
			        productObjTmp.setEnDesc(productObj.getEnDesc());
			        productObjTmp.setThDesc(productObj.getThDesc());
			        vProductTmp.add(productObjTmp);
			    }
			}
			
			xmlObj = ORIGDisplayFormatUtil.displaySelectTagScriptActionAndCode_ORIG(vProductTmp,"" ,"loanType",ORIGDisplayFormatUtil.DISPLAY_MODE_EDIT," style=\"width:165px;\" onChange=\"javascript:getChannel()\"");

			AjaxUtils util = new AjaxUtils();			
			String xmlStr = util.getXML4OneItem("div_product",xmlObj);
			return xmlStr;
        }else{
        	xmlObj = ORIGDisplayFormatUtil.displaySelectTagScriptActionAndCode_ORIG(null,"" ,"loanType",ORIGDisplayFormatUtil.DISPLAY_MODE_EDIT," style=\"width:165px;\" onChange=\"javascript:getChannel()\"");
        }
        
        return xmlObj;
    }

}
