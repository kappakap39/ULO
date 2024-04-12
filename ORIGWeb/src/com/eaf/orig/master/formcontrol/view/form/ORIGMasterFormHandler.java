/*
 * Created on Nov 15, 2007
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
package com.eaf.orig.master.formcontrol.view.form;

import javax.servlet.http.HttpServletRequest;
import org.apache.log4j.Logger;
import com.eaf.j2ee.pattern.view.form.FormHandler;
import com.eaf.orig.shared.model.SLADataM;



/**
 * @author Administrator
 *
 * Type: ORIGMasterFormHandler
 */
public class ORIGMasterFormHandler extends FormHandler {	
	static Logger log = Logger.getLogger(ORIGMasterFormHandler.class);
	
	private String shwAddFrm = "";
	private String shwAppScore = "";
	private String shwProfileFrm = "";
//	private String shwSearchField = "";
	private String disable ="true";
	private String status ="";
	
	private SLADataM slaDataM;
	
	

	
	/* (non-Javadoc)
	 * @see com.eaf.j2ee.pattern.view.form.FormHandler#validInSessionScope()
	 */
	public boolean validInSessionScope() {
		 
		return false;
	}

	/* (non-Javadoc)
	 * @see com.eaf.j2ee.pattern.view.form.FormHandler#validateForm(javax.servlet.http.HttpServletRequest)
	 */
	public boolean validateForm(HttpServletRequest request) {
		 
		return true;
	}

	/* (non-Javadoc)
	 * @see com.eaf.j2ee.pattern.view.form.FormHandler#postProcessForm(javax.servlet.http.HttpServletRequest)
	 */
	public void postProcessForm(HttpServletRequest request) {
		 

	}

	/* (non-Javadoc)
	 * @see com.eaf.j2ee.pattern.view.form.FormHandler#setProperties(javax.servlet.http.HttpServletRequest)
	 */
	public void setProperties(HttpServletRequest request) {
		
		log.debug("now i'm in setProperties");
		
		if (request.getParameter("shwAddFrm")!=null && !"".equals(request.getParameter("shwAddFrm"))){
			this.setShwAddFrm(request.getParameter("shwAddFrm"));
			log.debug("///ORIGMasterFormHandler/// shwAddFrm = " + shwAddFrm);
		}
		if (request.getParameter("showProfile")!=null && !"".equals(request.getParameter("showProfile"))){
			this.setShwProfileFrm(request.getParameter("showProfile"));
			log.debug("///ORIGMasterFormHandler/// shwProfileFrm = " + shwProfileFrm);
		}
		if (request.getParameter("shwAppScore")!=null && !"".equals(request.getParameter("shwAppScore"))){
			this.setShwAppScore(request.getParameter("shwAppScore"));
			log.debug("///ORIGMasterFormHandler/// shwAppScore = " + shwAppScore);
		}
		if (request.getParameter("disableInputField")!=null && !"".equals(request.getParameter("disableInputField"))){
			this.setDisable(request.getParameter("disableInputField"));
			log.debug("///ORIGMasterFormHandler/// disable = " + disable);
		}
						
	}

	/* (non-Javadoc)
	 * @see com.eaf.j2ee.pattern.view.form.FormHandler#clearForm()
	 */
	public void clearForm() {
		 

	}
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	public String getShwAddFrm() {
		return shwAddFrm;
	}
	public void setShwAddFrm(String shwAddFrm) {
		this.shwAddFrm = shwAddFrm;
	}
	public String getShwProfileFrm() {
		return shwProfileFrm;
	}
	public void setShwProfileFrm(String shwProfileFrm) {
		this.shwProfileFrm = shwProfileFrm;
	}
	
	public String getShwAppScore() {
		return shwAppScore;
	}
	public void setShwAppScore(String shwAppScore) {
		this.shwAppScore = shwAppScore;
	}
	public String getDisable() {
		return disable;
	}
	public void setDisable(String disable) {
		this.disable = disable;
	}
	
	/**
	 * @return Returns the slaDataM.
	 */
	public SLADataM getSlaDataM() {
		return slaDataM;
	}
	/**
	 * @param slaDataM The slaDataM to set.
	 */
	public void setSlaDataM(SLADataM slaDataM) {
		this.slaDataM = slaDataM;
	}
}
