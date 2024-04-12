/*
 * Created on Oct 17, 2007
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
import com.eaf.orig.shared.model.LoanDataM;
import com.eaf.orig.shared.utility.ORIGCacheUtil;
import com.eaf.orig.shared.utility.ORIGDisplayFormatUtil;
import com.eaf.orig.shared.utility.ORIGUtility;


/**
 * @author Weeraya
 *
 * Type: DeleteLoanServlet
 */
public class DeleteLoanServlet extends HttpServlet implements Servlet {
	Logger logger = Logger.getLogger(DeleteLoanServlet.class);
	public DeleteLoanServlet() {
		super();
	}

	/* (non-Java-doc)
	 * @see javax.servlet.http.HttpServlet#doPost(HttpServletRequest arg0, HttpServletResponse arg1)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		ORIGFormHandler formHandler = (ORIGFormHandler) request.getSession().getAttribute(OrigConstant.FORMHANDLER_NAME);
		String personalType = (String) request.getSession().getAttribute("PersonalType");
		ORIGUtility utility = new ORIGUtility();
		try{
			ApplicationDataM applicationDataM = formHandler.getAppForm();
		    Vector loanVect = applicationDataM.getLoanVect();
		    
		    //String loanSeq = request.getParameter("loanSeq");
		    //logger.debug("loanSeq ->" + loanSeq);
		    //LoanDataM loanDataM = null;
		    //if(loanSeq != null && !"".equals(loanSeq)){
//				String[] loanSeqVt = loanSeq.split(",");
//				int seq = 0;
//				for(int i = 0; i < loanSeqVt.length; i++){
//					seq = Integer.parseInt(loanSeqVt[i].trim());
//					for(int j = 0; j < loanVect.size(); j++){
//						loanDataM = (LoanDataM)loanVect.get(j);
//						if(seq == loanDataM.getSeq()){
							loanVect.removeElementAt(0);
//						}
//					}
//				}
//			}
		    
		    /*for(int i = 0; i < loanVect.size(); i++){
				loanDataM = (LoanDataM)loanVect.get(i);
				loanDataM.setSeq(i + 1);
			}*/
		    //Rewrite
		    String tableData = utility.getLoanTable(null, request);
	        
			response.setContentType("text/xml;charset=UTF-8");
			response.setHeader("Pragma", "No-cache");
			response.setHeader("Cache-Control", "no-cache,no-store");
			response.setDateHeader("Expires", 0);
			PrintWriter pw = response.getWriter();
			pw.write(tableData.toString());
			pw.close();
		}catch(Exception e){
			logger.error("Error in DeleteLoanServlet >> ", e);
		}
	}
}
