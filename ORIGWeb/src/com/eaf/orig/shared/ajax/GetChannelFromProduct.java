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

import com.eaf.cache.data.CacheDataM;
import com.eaf.orig.cache.properties.ProductChannelProperties;
import com.eaf.orig.shared.servlet.AjaxDisplayGenerateInf;
import com.eaf.orig.shared.utility.AjaxUtils;
import com.eaf.orig.shared.utility.ORIGCacheUtil;
import com.eaf.orig.shared.utility.ORIGDisplayFormatUtil;

/**
 * @author wichaya
 *
 * Type: GetChannelFromProduct
 */
public class GetChannelFromProduct implements AjaxDisplayGenerateInf {

    /* (non-Javadoc)
     * @see com.eaf.orig.shared.servlet.AjaxDisplayGenerateInf#getDisplayObject(javax.servlet.http.HttpServletRequest)
     */
    public String getDisplayObject(HttpServletRequest request) {
        String loanType = (String)request.getParameter("loanType");
        if(loanType!=null && !"".equals(loanType)){
        	ORIGCacheUtil utility = new ORIGCacheUtil();
            ProductChannelProperties productChObj = new ProductChannelProperties();
            Vector vProductCh = utility.loadCacheByName("ProductChannel");
            Vector vChannel = utility.loadCacheByName("Channel");
            Vector vChannelTmp = new Vector();
            CacheDataM channelDataM = new CacheDataM();
            String xmlObj = "";
            
			for (int i = 0; i < vProductCh.size(); i++) {
			    productChObj = (ProductChannelProperties) vProductCh.get(i);
			    if(loanType.equals(productChObj.getProductID())){
			        for(int j=0; j<vChannel.size(); j++){
			            channelDataM = (CacheDataM)vChannel.get(j);
			            if(productChObj.getChannelID().equals(channelDataM.getCode())){
			                vChannelTmp.add(channelDataM);
			            }
			        }
			    }
			}
			xmlObj = ORIGDisplayFormatUtil.displaySelectTagScriptActionAndCode_ORIG(vChannelTmp,"" ,"channel",ORIGDisplayFormatUtil.DISPLAY_MODE_EDIT," style=\"width:80%;\"");
			AjaxUtils util = new AjaxUtils();			
			String xmlStr = util.getXML4OneItem("div_channel",xmlObj);
			return xmlStr;
        }
        return null;
    }

}
