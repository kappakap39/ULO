/*
 * Created on Jan 18, 2008
 * Created by Weeraya
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
import java.util.Vector;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;

import com.eaf.orig.cache.properties.HPTBIS06Properties;
import com.eaf.orig.shared.utility.ORIGCacheUtil;
import com.eaf.orig.shared.utility.ORIGDisplayFormatUtil;

/**
 * @author Weeraya
 *
 * Type: GetInsuaranceDataServlet
 */
public class GetInsuaranceDataServlet extends HttpServlet {
	Logger logger = Logger.getLogger(GetInsuaranceDataServlet.class);
	public GetInsuaranceDataServlet() {
		super();
	}

	/* (non-Java-doc)
	 * @see javax.servlet.http.HttpServlet#doPost(HttpServletRequest arg0, HttpServletResponse arg1)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		try{
			String car_license_type = request.getParameter("car_license_type");
			String acc_insurence = request.getParameter("acc_insurence");
			logger.debug("car_license_type = "+car_license_type);
			logger.debug("acc_insurence = "+acc_insurence);
			
			ORIGCacheUtil cacheUtil = new ORIGCacheUtil();
			HPTBIS06Properties properties = cacheUtil.getInsDataByType(car_license_type);
			
			StringBuffer tableData = new StringBuffer("");
			if(properties != null){
				tableData.append(properties.getACCRATE());
				tableData.append(",");
				tableData.append(properties.getNETACCRATE());
				tableData.append(",");
			}else{
				tableData.append(0.00);
				tableData.append(",");
				tableData.append(0.00);
				tableData.append(",");
			}
			
			response.setContentType("text/xml;charset=UTF-8");
			response.setHeader("Pragma", "No-cache");
			response.setHeader("Cache-Control", "no-cache,no-store");
			response.setDateHeader("Expires", 0);
			PrintWriter pw = response.getWriter();
			pw.write(tableData.toString());
			pw.close();
		}catch(Exception e){
			logger.error("Error in DeleteAddressServlet >> ", e);
		}
	}

}
