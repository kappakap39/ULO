/*
 * Created on Dec 17, 2008
 *
 * TODO To change the template for this generated file go to
 * Window - Preferences - Java - Code Style - Code Templates
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
import com.eaf.orig.shared.model.CardInformationDataM;
import com.eaf.orig.shared.model.PersonalInfoDataM;
import com.eaf.orig.shared.utility.ORIGUtility;

/**
 * @author Administrator
 *
 * TODO To change the template for this generated type comment go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
public class DeleteSupplementaryCardServlet extends HttpServlet implements Servlet {
	Logger logger = Logger.getLogger(DeleteSupplementaryCardServlet.class);

	public DeleteSupplementaryCardServlet() {
		super();
	}

	/* (non-Java-doc)
	 * @see javax.servlet.http.HttpServlet#doPost(HttpServletRequest arg0, HttpServletResponse arg1)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		ORIGFormHandler formHandler = (ORIGFormHandler) request.getSession().getAttribute(OrigConstant.FORMHANDLER_NAME);
		ORIGUtility utility = new ORIGUtility();
		try{
			ApplicationDataM applicationDataM = formHandler.getAppForm();
		    PersonalInfoDataM personalInfoDataM;
		    Vector personalVect = applicationDataM.getPersonalInfoVect(); 
		    
		    String supCardSeq = request.getParameter("supCardSeq");
		    logger.debug("supCardSeq ->" + supCardSeq);
		    if(supCardSeq != null && !"".equals(supCardSeq)){
				String[] supCardSeqVt = supCardSeq.split(",");
				int seq = 0;
//				for(int i = 0; i < supCardSeqVt.length; i++){
				for(int i=supCardSeqVt.length-1;i>=0;i--){
					seq = Integer.parseInt(supCardSeqVt[i].trim());
					for(int j=0;j<personalVect.size();j++){
						personalInfoDataM = (PersonalInfoDataM)personalVect.get(j);
						if(seq == personalInfoDataM.getPersonalSeq()){
							Vector vecCardInformation = applicationDataM.getCardInformation();
							if (vecCardInformation!=null && vecCardInformation.size()>0){
								for (int k=0;k<vecCardInformation.size();k++){
									CardInformationDataM cardInformationDataM = (CardInformationDataM)vecCardInformation.get(k);
									if (personalInfoDataM.getIdNo().equals(cardInformationDataM.getIdNo())){
										vecCardInformation.removeElementAt(k);
									}
								}
							}
							personalVect.removeElementAt(j);
							//set Remove Gurantor Flag
							applicationDataM.setIsReExcuteAppScoreFlag(true);
			                applicationDataM.setScorringResult(null);
						}
					}
				}
			}
		    Vector supCardVect = utility.getVectorPersonalInfoByType(applicationDataM, OrigConstant.PERSONAL_TYPE_SUP_CARD);
			for(int i = 0; i < supCardVect.size(); i++){
				personalInfoDataM = (PersonalInfoDataM)supCardVect.get(i);
				personalInfoDataM.setPersonalSeq(i + 1);
			}
		    //Rewrite
			String tableData = utility.getSupplementaryCardTable(supCardVect, request, applicationDataM.getCardInformation());
	        
			response.setContentType("text/xml;charset=UTF-8");
			response.setHeader("Pragma", "No-cache");
			response.setHeader("Cache-Control", "no-cache,no-store");
			response.setDateHeader("Expires", 0);
			PrintWriter pw = response.getWriter();
			pw.write(tableData.toString());
			pw.close();
		}catch(Exception e){
			logger.error("Error in DeleteSupplementaryCardServlet >> ", e);
		}
	}
}
