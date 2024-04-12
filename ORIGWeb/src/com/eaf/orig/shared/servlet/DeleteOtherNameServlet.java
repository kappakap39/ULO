/*
 * Created on Oct 19, 2007
 * Created by Prawit Limwattanachai
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

import javax.servlet.Servlet;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;

import com.eaf.j2ee.pattern.util.MessageResourceUtil;
import com.eaf.orig.formcontrol.view.form.ORIGFormHandler;
import com.eaf.orig.shared.constant.OrigConstant;
import com.eaf.orig.shared.model.ApplicationDataM;
import com.eaf.orig.shared.model.OtherNameDataM;
import com.eaf.orig.shared.utility.ORIGDisplayFormatUtil;
import com.eaf.orig.shared.utility.ORIGUtility;

/**
 * @author Administrator
 *
 * Type: DeleteOtherNameServlet
 */
public class DeleteOtherNameServlet extends HttpServlet implements Servlet {

	Logger logger = Logger.getLogger(DeleteChangeNameServlet.class);
	public DeleteOtherNameServlet() {
		super();
	}

	/* (non-Java-doc)
	 * @see javax.servlet.http.HttpServlet#doPost(HttpServletRequest arg0, HttpServletResponse arg1)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		ORIGFormHandler formHandler = (ORIGFormHandler) request.getSession().getAttribute(OrigConstant.FORMHANDLER_NAME);
	//	String personalType = (String) request.getSession().getAttribute("PersonalType");
		ORIGUtility utility = new ORIGUtility();
		try{
			ApplicationDataM applicationDataM = formHandler.getAppForm();
	/*	    PersonalInfoDataM personalInfoDataM;
			if(OrigConstant.PERSONAL_TYPE_GUARANTOR.equals(personalType)){
				personalInfoDataM = (PersonalInfoDataM) request.getSession(true).getAttribute("MAIN_POPUP_DATA");
			}else{
				personalInfoDataM = utility.getPersonalInfoByType(formHandler.getAppForm(),OrigConstant.PERSONAL_TYPE_APPLICANT);
			} 
		    Vector changeNameVect = personalInfoDataM.getChangeNameVect(); */
			Vector otherNameVect = applicationDataM.getOtherNameDataM(); 
		    
		    String otherNameSeq = request.getParameter("otherNameSeq");
		    logger.debug("otherNameSeq ->" + otherNameSeq);
		    OtherNameDataM otherNameDataM;
		    if(otherNameSeq != null && !"".equals(otherNameSeq)){
				String[] otherNameSeqVt = otherNameSeq.split(",");
				int seq = 0;
				for(int i = 0; i < otherNameSeqVt.length; i++){
					seq = Integer.parseInt(otherNameSeqVt[i].trim());
					for(int j = 0; j < otherNameVect.size(); j++){
						otherNameDataM = (OtherNameDataM)otherNameVect.get(j);
						if(seq == otherNameDataM.getSeq()){
							otherNameVect.removeElementAt(j);
						}
					}
				}
			}
			
		    otherNameVect = applicationDataM.getOtherNameDataM();
			for(int i = 0; i < otherNameVect.size(); i++){
				otherNameDataM = (OtherNameDataM)otherNameVect.get(i);
				otherNameDataM.setSeq(i + 1);
			}
			
		    //Rewrite
			String tableData = utility.getOtherNameTable(otherNameVect, request);
	        
			response.setContentType("text/xml;charset=UTF-8");
			response.setHeader("Pragma", "No-cache");
			response.setHeader("Cache-Control", "no-cache,no-store");
			response.setDateHeader("Expires", 0);
			PrintWriter pw = response.getWriter();
			pw.write(tableData.toString());
			pw.close();
		}catch(Exception e){
			logger.error("Error in DeleteOtherNameServlet >> ", e);
		}
	}

}
