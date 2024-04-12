/*
 * Created on Oct 17, 2007
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

import javax.servlet.Servlet;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;

import com.eaf.orig.formcontrol.view.form.ORIGFormHandler;
import com.eaf.orig.shared.constant.OrigConstant;
import com.eaf.orig.shared.model.ApplicationDataM;
import com.eaf.orig.shared.model.ChangeNameDataM;
import com.eaf.orig.shared.model.PersonalInfoDataM;
import com.eaf.orig.shared.utility.ORIGUtility;


/**
 * @author Weeraya
 *
 * Type: DeleteChangeNameServlet
 */
public class DeleteChangeNameServlet extends HttpServlet implements Servlet {
	Logger logger = Logger.getLogger(DeleteChangeNameServlet.class);
	public DeleteChangeNameServlet() {
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
		    PersonalInfoDataM personalInfoDataM = new PersonalInfoDataM();
			if(OrigConstant.PERSONAL_TYPE_GUARANTOR.equals(personalType)){
				personalInfoDataM = (PersonalInfoDataM) request.getSession(true).getAttribute("MAIN_POPUP_DATA");
			}else if (OrigConstant.PERSONAL_TYPE_APPLICANT.equals(personalType)) {
				personalInfoDataM = utility.getPersonalInfoByType(formHandler.getAppForm(),OrigConstant.PERSONAL_TYPE_APPLICANT);
			}else if (OrigConstant.PERSONAL_TYPE_SUP_CARD.equals(personalType)) {
	    		personalInfoDataM = (PersonalInfoDataM) request.getSession(true).getAttribute("SUPCARD_POPUP_DATA");
			}
		    Vector changeNameVect = personalInfoDataM.getChangeNameVect();
		    
		    String changeNameSeq = request.getParameter("changeNameSeq");
		    logger.debug("changeNameSeq ->" + changeNameSeq);
		    ChangeNameDataM changeNameDataM;
		    if(changeNameSeq != null && !"".equals(changeNameSeq)){
				String[] changeNameSeqVt = changeNameSeq.split(",");
				int seq = 0;
				for(int i = 0; i < changeNameSeqVt.length; i++){
					seq = Integer.parseInt(changeNameSeqVt[i].trim());
					for(int j = 0; j < changeNameVect.size(); j++){
						changeNameDataM = (ChangeNameDataM)changeNameVect.get(j);
						if(seq == changeNameDataM.getSeq()){
							changeNameVect.removeElementAt(j);
						}
					}
				}
			}
			
		    changeNameVect = personalInfoDataM.getChangeNameVect();
			/*for(int i = 0; i < changeNameVect.size(); i++){
				changeNameDataM = (ChangeNameDataM)changeNameVect.get(i);
				changeNameDataM.setSeq(i + 1);
			}*/
			
		    //Rewrite
			String tableData = utility.getChangeNameTable(changeNameVect, request);
	        
			response.setContentType("text/xml;charset=UTF-8");
			response.setHeader("Pragma", "No-cache");
			response.setHeader("Cache-Control", "no-cache,no-store");
			response.setDateHeader("Expires", 0);
			PrintWriter pw = response.getWriter();
			pw.write(tableData.toString());
			pw.close();
		}catch(Exception e){
			logger.error("Error in DeleteChangeNameServlet >> ", e);
		}
	}
}
