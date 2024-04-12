/*
 * Created on Dec 1, 2007
 * Created by Administrator
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

import com.eaf.orig.shared.model.PersonalInfoDataM;
import com.eaf.orig.shared.utility.ORIGCacheUtil;
import com.eaf.orig.shared.utility.ORIGDisplayFormatUtil;

/**
 * @author Administrator
 *
 * Type: ParseOccupationToPositionServlet
 */
public class ParseOccupationToPositionServlet extends HttpServlet {
	Logger logger = Logger.getLogger(ParseOccupationToPositionServlet.class);
	public ParseOccupationToPositionServlet() {
		super();
	}

	/* (non-Java-doc)
	 * @see javax.servlet.http.HttpServlet#doPost(HttpServletRequest arg0, HttpServletResponse arg1)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		try{
			String occupation = request.getParameter("occupation");
			String positionField = request.getParameter("positionField");
			String positionSelect;
			try {
				PersonalInfoDataM personalInfoDataM = (PersonalInfoDataM) request.getSession().getAttribute("personalInfoSession");
				positionSelect = personalInfoDataM.getPosition();
			} catch(Exception e) {
				positionSelect = (String) request.getParameter("positionSelect");
			}
			
			logger.debug("occupation = "+occupation);
			ORIGCacheUtil cacheUtil = new ORIGCacheUtil();
			Vector positionVect = cacheUtil.getPositionByOccupation(occupation);
			
			StringBuffer tableData = new StringBuffer("");
			//if(positionVect != null && positionVect.size() > 0){
				tableData.append("&nbsp;");
				//tableData.append(ORIGDisplayFormatUtil.displaySelectTagScriptAction_ORIG(positionVect,"",positionField,ORIGDisplayFormatUtil.DISPLAY_MODE_EDIT,""));
				tableData.append(ORIGDisplayFormatUtil.displaySelectTagScriptAction_ORIG_WithCheckStatus(positionVect,positionSelect,positionField,ORIGDisplayFormatUtil.DISPLAY_MODE_EDIT,""));
			//}
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
