/*
 * Created on Feb 5, 2008
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

import com.eaf.orig.cache.properties.ReasonProperties;
import com.eaf.orig.shared.model.ReasonDataM;
import com.eaf.orig.shared.utility.ORIGDisplayFormatUtil;
import com.eaf.orig.shared.utility.ORIGUtility;

/**
 * @author Weeraya
 *
 * Type: GetReasonByDecisionServlet
 */
public class GetReasonByDecisionServlet extends HttpServlet {
	Logger logger = Logger.getLogger(GetReasonByDecisionServlet.class);
	public GetReasonByDecisionServlet() {
		super();
	}

	/* (non-Java-doc)
	 * @see javax.servlet.http.HttpServlet#doPost(HttpServletRequest arg0, HttpServletResponse arg1)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		try{
			String decision = request.getParameter("decision");
			String reasonField = request.getParameter("reasonField");
			logger.debug("decision = "+decision);
			logger.debug("reasonField = "+reasonField);
			ORIGUtility utility = new ORIGUtility();
		 			 			 
			decision = utility.getReasonDecisionByAppDecision(decision);
			
			Vector reasonVect = utility.getReasonByDesicion(decision);
			
			StringBuffer tableData = new StringBuffer("");
			
			if(reasonVect != null){
				ReasonProperties reasonProperties;
				ReasonDataM reasonDataM;
				String check;
				String reasonCode;
				for(int i=0; i<reasonVect.size(); i++){
					reasonProperties = (ReasonProperties) reasonVect.get(i);
					tableData.append("&nbsp;");
					tableData.append(ORIGDisplayFormatUtil.displayCheckBoxTagDesc(reasonProperties.getCode(),"",ORIGDisplayFormatUtil.DISPLAY_MODE_EDIT,reasonField, "",reasonProperties.getTHDESC()));
					tableData.append("<br>");
		
				}
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
