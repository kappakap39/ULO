/*
 * Created on Nov 7, 2007
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

import com.eaf.orig.formcontrol.view.form.ORIGFormHandler;
import com.eaf.orig.shared.constant.OrigConstant;
import com.eaf.orig.shared.model.CorperatedDataM;
import com.eaf.orig.shared.model.PersonalInfoDataM;
import com.eaf.orig.shared.utility.ORIGUtility;

/**
 * @author Administrator
 *
 * Type: DeleteCorperatedServlet
 */
public class DeleteCorperatedServlet extends HttpServlet implements Servlet {
	
	Logger logger = Logger.getLogger(DeleteCorperatedServlet.class);
	public DeleteCorperatedServlet() {
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
		//	ApplicationDataM applicationDataM = formHandler.getAppForm();
		    PersonalInfoDataM personalInfoDataM = new PersonalInfoDataM();
			if(OrigConstant.PERSONAL_TYPE_GUARANTOR.equals(personalType)){
				personalInfoDataM = (PersonalInfoDataM) request.getSession(true).getAttribute("MAIN_POPUP_DATA");
			}else if (OrigConstant.PERSONAL_TYPE_APPLICANT.equals(personalType)) {
				personalInfoDataM = utility.getPersonalInfoByType(formHandler.getAppForm(),OrigConstant.PERSONAL_TYPE_APPLICANT);
			}else if (OrigConstant.PERSONAL_TYPE_SUP_CARD.equals(personalType)) {
                personalInfoDataM = (PersonalInfoDataM) request.getSession(true).getAttribute("SUPCARD_POPUP_DATA");
            }
		    Vector corperatedVect = personalInfoDataM.getCorperatedVect(); 
			//Vector otherNameVect = applicationDataM.getOtherNameDataM(); 
		    
		    String corperatedSeq = request.getParameter("corperatedSeq");
		    logger.debug("corperatedSeq ->" + corperatedSeq);		  
		    CorperatedDataM corperatedDataM;
		    
		    if(corperatedSeq != null && !"".equals(corperatedSeq)){
				String[] corperatedSeqVt = corperatedSeq.split(",");
				int seq = 0;
				for(int i = 0; i < corperatedSeqVt.length; i++){
					seq = Integer.parseInt(corperatedSeqVt[i].trim());
					for(int j = 0; j < corperatedVect.size(); j++){
						corperatedDataM = (CorperatedDataM)corperatedVect.get(j);
						if(seq == corperatedDataM.getSeq()){
							corperatedVect.removeElementAt(j);
						}
					}
				}
			}
			
		    corperatedVect = personalInfoDataM.getCorperatedVect();
			for(int i = 0; i < corperatedVect.size(); i++){
				corperatedDataM = (CorperatedDataM)corperatedVect.get(i);
				corperatedDataM.setSeq(i + 1);
			}
			
		    //Rewrite
			String tableData = utility.getCorperatedTable(corperatedVect, request);
	        
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
