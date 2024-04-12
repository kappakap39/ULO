/*
 * Created on Oct 30, 2007
 * Created by weeraya
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
package com.eaf.orig.shared.servlet;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.Servlet;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;

import com.eaf.orig.formcontrol.view.form.ORIGFormHandler;
import com.eaf.orig.profile.model.UserDetailM;
import com.eaf.orig.shared.constant.OrigConstant;
import com.eaf.orig.shared.utility.ORIGCacheUtil;
import com.eaf.orig.shared.utility.ORIGUtility;


/**
 * @author Weeraya
 *
 * Type: CalculateLoanServlet
 */
public class LookupDescriptionServlet extends HttpServlet implements Servlet {
	Logger logger = Logger.getLogger(LookupDescriptionServlet.class);
	public LookupDescriptionServlet() {
		super();
	}

	/* (non-Java-doc)
	 * @see javax.servlet.http.HttpServlet#doPost(HttpServletRequest arg0, HttpServletResponse arg1)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		ORIGFormHandler formHandler = (ORIGFormHandler) request.getSession().getAttribute(OrigConstant.FORMHANDLER_NAME);
		ORIGUtility utility = new ORIGUtility();
		try{
			ORIGCacheUtil cacheUtil = new ORIGCacheUtil();
			
			String code = request.getParameter("code");
			String param1 = request.getParameter("param1");
			String cacheName = request.getParameter("cacheName");
			String campaignCode = request.getParameter("campaignCode");
			logger.debug("code = "+code);
			logger.debug("cacheName = "+cacheName);
			logger.debug("campaignCode = "+campaignCode);
			String description = cacheUtil.getORIGMasterDisplayNameDataM(cacheName, code);
			logger.debug("description 1 = "+description);
			if(OrigConstant.CacheName.CACHE_NAME_TITLE_ENG.equals(cacheName) || OrigConstant.CacheName.CACHE_NAME_TITLE_THAI.equals(cacheName)){
				description = cacheUtil.getORIGMasterCodeByDesc(cacheName, code);
			}else if(OrigConstant.CacheName.CACHE_NAME_BRANCH.equals(cacheName) || OrigConstant.CacheName.CACHE_NAME_CAR_MODEL.equals(cacheName)){
				description = cacheUtil.getORIGCacheDisplayNameFormDB(code, cacheName, param1);
			}else if(OrigConstant.CacheName.CACHE_NAME_AREA.equals(cacheName)){
				UserDetailM userM = (UserDetailM) request.getSession().getAttribute("ORIGUser");
				param1 = userM.getUserName();
				description = cacheUtil.getORIGCacheDisplayNameFormDB(code, cacheName, param1);
			}else if(OrigConstant.CacheName.CACHE_NAME_BL_REASON.equals(cacheName)){
				description = cacheUtil.getNaosCacheDisplayNameDataM(22, code);
			}else if(OrigConstant.CacheName.CACHE_NAME_BL_SOURCE.equals(cacheName)){
				description = cacheUtil.getNaosCacheDisplayNameDataM(23, code);
			}else if(OrigConstant.CacheName.CACHE_NAME_LPM_LOAN_TYPE.equals(cacheName)){
			    description = cacheUtil.getNaosCacheDisplayNameDataM(24, code);
			}else if(OrigConstant.CacheName.CACHE_NAME_DEALER.equals(cacheName)){
			    description = cacheUtil.getORIGCacheDisplayNameFormDB(code, OrigConstant.CacheName.CACHE_NAME_DEALER);
			}else if("IntScheme".equals(cacheName)){
				if(ORIGUtility.isEmptyString(description)){
					description = cacheUtil.getORIGCacheDisplayNameDataMByType(code, cacheName);
					logger.debug("description 2 = "+description);
				}
				if(!ORIGUtility.isEmptyString(description)){
					description = cacheUtil.getORIGCacheDisplayNameSchemeByCampaign(code, cacheName, campaignCode);
					logger.debug("description 3 = "+description);
				}
			}else if("Campaign".equals(cacheName)){
				String bus_class = request.getParameter("bus_class");
				logger.debug("bus_class = "+bus_class);
				description = cacheUtil.getORIGMasterDisplayNameDataMByBusClass(code, cacheName, bus_class);
				logger.debug("description 2 = "+description);
			}else{
				if(ORIGUtility.isEmptyString(description)){
					description = cacheUtil.getORIGCacheDisplayNameDataMByType(code, cacheName);
					logger.debug("description 2 = "+description);
				} 
				if(ORIGUtility.isEmptyString(description)){
					description = cacheUtil.getORIGCacheDisplayNameFormDB(code, cacheName);
					logger.debug("description 3 = "+description);
				}
			}
			logger.debug("description 4 = "+description);
			if(description == null){
				description = "";
			}
			response.setContentType("text/xml;charset=UTF-8");
			response.setHeader("Pragma", "No-cache");
			response.setHeader("Cache-Control", "no-cache,no-store");
			response.setDateHeader("Expires", 0);
			PrintWriter pw = response.getWriter();
			pw.write(description);
			pw.close();
		}catch(Exception e){
			logger.error("Error in LookupDescriptionServlet >> ", e);
		}
	}
}
