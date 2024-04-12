/*
 * Created on Oct 30, 2007
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

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;

import com.eaf.orig.formcontrol.view.form.ORIGFormHandler;
import com.eaf.orig.profile.model.UserDetailM;
import com.eaf.orig.shared.constant.OrigConstant;
import com.eaf.orig.shared.model.PersonalInfoDataM;
import com.eaf.orig.shared.utility.ORIGCacheUtil;
import com.eaf.orig.shared.utility.ORIGDisplayFormatUtil;
import com.eaf.orig.shared.utility.ORIGMandatoryErrorUtil;
import com.eaf.orig.shared.utility.ORIGUtility;


/**
 * @author Weeraya
 *
 * Type: EditPersonalInfoServlet
 */
public class EditPersonalInfoServlet extends HttpServlet {
	Logger logger = Logger.getLogger(EditPersonalInfoServlet.class);
	
	/**
	* @see javax.servlet.http.HttpServlet#void (javax.servlet.http.HttpServletRequest, javax.servlet.http.HttpServletResponse)
	*/
	public void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		doPost(req,resp);
	}

	/**
	* @see javax.servlet.http.HttpServlet#void (javax.servlet.http.HttpServletRequest, javax.servlet.http.HttpServletResponse)
	*/
	public void doPost(HttpServletRequest request, HttpServletResponse resp) throws ServletException, IOException {
		logger.debug("<<<<<<< Start EditPersonalInfoServlet >>>>>>>");
		UserDetailM userM = (UserDetailM) request.getSession().getAttribute("ORIGUser");
		String returnValue;
		StringBuffer sb = new StringBuffer("");
		try{
			ORIGFormHandler formHandler = (ORIGFormHandler) request.getSession().getAttribute(OrigConstant.FORMHANDLER_NAME);
			
	        ORIGMandatoryErrorUtil errorUtil = new ORIGMandatoryErrorUtil();
	        ORIGUtility utility = new ORIGUtility();
	        ORIGCacheUtil cacheUtil = new ORIGCacheUtil();
			
	        String personalType = request.getParameter("appPersonalType");//(String) request.getSession().getAttribute("PersonalType");
	        logger.debug("personalType = "+personalType);
	    	
	        PersonalInfoDataM personalInfoDataM = new PersonalInfoDataM();
	        
	        int addressSize = personalInfoDataM.getAddressVect().size();
	        int carSize = 0;
	        int changeNameSize = personalInfoDataM.getChangeNameVect().size();
	        int financeSize = personalInfoDataM.getFinanceVect().size();
	        logger.debug("addressSize = "+addressSize);
	        logger.debug("changeNameSize = "+changeNameSize);
	        logger.debug("financeSize = "+financeSize);
	        personalInfoDataM.setAddressIndex(addressSize+1);
	        personalInfoDataM.setChangeNameIndex(changeNameSize+1);
	        personalInfoDataM.setFinanceIndex(financeSize+1);
	        
	        sb.append("<?xml version=\"1.0\" encoding=\"UTF-8\"?>");
			sb.append("<list>");
			//Customer Infomation
			String titleThai = cacheUtil.getORIGMasterDisplayNameDataM("Title", personalInfoDataM.getThaiTitleName());
			sb.append("<field name=\"title_thai_desc\">"+titleThai+"</field>");
			//sb.append("<field name=\"title_thai_desc\">"+"weeraya"+"</field>"); //pae add
			sb.append("<field name=\"name_thai\">"+ORIGDisplayFormatUtil.forHTMLTag(personalInfoDataM.getThaiFirstName())+"</field>");
			sb.append("<field name=\"surname_thai\">"+ORIGDisplayFormatUtil.forHTMLTag(personalInfoDataM.getThaiLastName())+"</field>");
			String titleEng = cacheUtil.getORIGMasterDisplayNameDataM("TitleEng", personalInfoDataM.getEngTitleName());
			sb.append("<field name=\"title_eng_desc\">"+titleEng+"</field>");
			sb.append("<field name=\"name_eng\">"+ORIGDisplayFormatUtil.forHTMLTag(personalInfoDataM.getEngFirstName())+"</field>");
			sb.append("<field name=\"surname_eng\">"+ORIGDisplayFormatUtil.forHTMLTag(personalInfoDataM.getEngLastName())+"</field>");
			String sDate = null;
			if(personalInfoDataM.getBirthDate()==null){
				sDate="";
			}else{
				sDate=ORIGUtility.displayEngToThaiDate(ORIGDisplayFormatUtil.parseDate(personalInfoDataM.getBirthDate()));
			}
			sb.append("<field name=\"birth_date\">"+sDate+"</field>");
			sb.append("<field name=\"age\">"+personalInfoDataM.getAge()+"</field>");
			sb.append("<field name=\"race\">"+ORIGDisplayFormatUtil.forHTMLTag(personalInfoDataM.getRace())+"</field>");
			sb.append("<field name=\"gender\">"+ORIGDisplayFormatUtil.forHTMLTag(personalInfoDataM.getGender())+"</field>");
			sb.append("<field name=\"marital_status\">"+ORIGDisplayFormatUtil.forHTMLTag(personalInfoDataM.getMaritalStatus())+"</field>");
			//sb.append("<field name=\"customer_type\">"+ORIGDisplayFormatUtil.forHTMLTag(personalInfoDataM.getCustomerType())+"</field>");
			sb.append("<field name=\"card_identity\">"+ORIGDisplayFormatUtil.forHTMLTag(personalInfoDataM.getCardIdentity())+"</field>");
			sb.append("<field name=\"nationality\">"+ORIGDisplayFormatUtil.forHTMLTag(personalInfoDataM.getNationality())+"</field>");
			if(personalInfoDataM.getIssueDate()==null){
				sDate="";
			}else{
				sDate=ORIGUtility.displayEngToThaiDate(ORIGDisplayFormatUtil.parseDate(personalInfoDataM.getIssueDate()));
			}
			sb.append("<field name=\"issue_date\">"+sDate+"</field>");
			if(personalInfoDataM.getExpiryDate()==null){
				sDate="";
			}else{
				sDate=ORIGUtility.displayEngToThaiDate(ORIGDisplayFormatUtil.parseDate(personalInfoDataM.getExpiryDate()));
			}
			sb.append("<field name=\"expiry_date\">"+sDate+"</field>");
			sb.append("<field name=\"tax_id\">"+ORIGDisplayFormatUtil.forHTMLTag(personalInfoDataM.getTaxID())+"</field>");
			String clientGroup = cacheUtil.getORIGMasterDisplayNameDataM("ClientGroup", personalInfoDataM.getClientGroup());
			sb.append("<field name=\"client_group\">"+ORIGDisplayFormatUtil.forHTMLTag(personalInfoDataM.getClientGroup())+"</field>");
			sb.append("<field name=\"client_desc\">"+ORIGDisplayFormatUtil.forHTMLTag(clientGroup)+"</field>");
			sb.append("<field name=\"card_id\">"+ORIGDisplayFormatUtil.forHTMLTag(personalInfoDataM.getCardID())+"</field>");
			sb.append("<field name=\"issue_by\">"+ORIGDisplayFormatUtil.forHTMLTag(personalInfoDataM.getIssueBy())+"</field>");
			
			//Occupation
			if(!OrigConstant.ROLE_CMR.equals(userM.getRoles().elementAt(0))){
				sb.append("<field name=\"occupation\">"+ORIGDisplayFormatUtil.forHTMLTag(personalInfoDataM.getOccupation())+"</field>");
				sb.append("<field name=\"occupation_desc\">"+ORIGDisplayFormatUtil.forHTMLTag("")+"</field>");
				sb.append("<field name=\"bus_type\">"+ORIGDisplayFormatUtil.forHTMLTag(personalInfoDataM.getBusinessType())+"</field>");
				sb.append("<field name=\"bus_type_desc\">"+ORIGDisplayFormatUtil.forHTMLTag("")+"</field>");
				sb.append("<field name=\"bus_size\">"+ORIGDisplayFormatUtil.forHTMLTag(personalInfoDataM.getBusinessSize())+"</field>");
				sb.append("<field name=\"bus_size_desc\">"+ORIGDisplayFormatUtil.forHTMLTag("")+"</field>");
				sb.append("<field name=\"company\">"+ORIGDisplayFormatUtil.forHTMLTag(personalInfoDataM.getCompanyName())+"</field>");
				sb.append("<field name=\"department\">"+ORIGDisplayFormatUtil.forHTMLTag(personalInfoDataM.getDepartment())+"</field>");
				sb.append("<field name=\"position\">"+ORIGDisplayFormatUtil.forHTMLTag(personalInfoDataM.getPosition())+"</field>");
				sb.append("<field name=\"salary\">"+ORIGDisplayFormatUtil.displayCommaNumber(personalInfoDataM.getSalary())+"</field>");
				sb.append("<field name=\"other_income\">"+ORIGDisplayFormatUtil.displayCommaNumber(personalInfoDataM.getOtherIncome())+"</field>");
				sb.append("<field name=\"position_desc\">"+ORIGDisplayFormatUtil.forHTMLTag(personalInfoDataM.getPositionDesc())+"</field>");		
				sb.append("<field name=\"source_other_income\">"+ORIGDisplayFormatUtil.forHTMLTag(personalInfoDataM.getSourceOfOtherIncome())+"</field>");
				sb.append("<field name=\"qualification\">"+personalInfoDataM.getQualification()+"</field>");
				sb.append("<field name=\"qualification_desc\">"+ORIGDisplayFormatUtil.forHTMLTag("")+"</field>");
				sb.append("<field name=\"year\">"+personalInfoDataM.getYearOfWork()+"</field>");
				sb.append("<field name=\"month\">"+personalInfoDataM.getMonthOfWork()+"</field>");
				sb.append("<field name=\"house_regis_status\">"+ORIGDisplayFormatUtil.forHTMLTag(personalInfoDataM.getHouseRegisStatus())+"</field>");
				sb.append("<field name=\"house_regis_desc\">"+ORIGDisplayFormatUtil.forHTMLTag("")+"</field>");
				sb.append("<field name=\"pre_record\">"+ORIGDisplayFormatUtil.forHTMLTag(personalInfoDataM.getPreviousRecord())+"</field>");
				sb.append("<field name=\"building_condition\">"+ORIGDisplayFormatUtil.forHTMLTag(personalInfoDataM.getBulidingCondition())+"</field>");
				sb.append("<field name=\"building_desc\">"+ORIGDisplayFormatUtil.forHTMLTag("")+"</field>");
				sb.append("<field name=\"location\">"+ORIGDisplayFormatUtil.forHTMLTag(personalInfoDataM.getLocation())+"</field>");
				sb.append("<field name=\"location_desc\">"+ORIGDisplayFormatUtil.forHTMLTag("")+"</field>");
				sb.append("<field name=\"asset_amount\">"+ORIGDisplayFormatUtil.displayCommaNumber(personalInfoDataM.getAssetAmount())+"</field>");
				sb.append("<field name=\"debt_amount\">"+ORIGDisplayFormatUtil.displayCommaNumber(personalInfoDataM.getDebitAmount())+"</field>");
				sb.append("<field name=\"land_ownership\">"+ORIGDisplayFormatUtil.forHTMLTag(personalInfoDataM.getLandOwnerShip())+"</field>");
				if(OrigConstant.PERSONAL_TYPE_APPLICANT.equals(personalType)){
					//Marry Infomation
					sb.append("<field name=\"m_card_type\">"+ORIGDisplayFormatUtil.forHTMLTag(personalInfoDataM.getOccupation())+"</field>");
					sb.append("<field name=\"m_id_no\">"+ORIGDisplayFormatUtil.forHTMLTag("")+"</field>");
					titleThai = cacheUtil.getORIGMasterDisplayNameDataM("Title", personalInfoDataM.getMThaiTitleName());
					sb.append("<field name=\"m_title_thai_desc\">"+titleThai+"</field>");
					sb.append("<field name=\"m_name_thai\">"+ORIGDisplayFormatUtil.forHTMLTag(personalInfoDataM.getMThaiFirstName())+"</field>");
					sb.append("<field name=\"m_surname_thai\">"+ORIGDisplayFormatUtil.forHTMLTag(personalInfoDataM.getMThaiLastName())+"</field>");
					titleEng = cacheUtil.getORIGMasterDisplayNameDataM("TitleEng", personalInfoDataM.getMEngTitleName());
					sb.append("<field name=\"m_title_eng_desc\">"+titleEng+"</field>");
					sb.append("<field name=\"m_name_eng\">"+ORIGDisplayFormatUtil.forHTMLTag(personalInfoDataM.getMEngFirstName())+"</field>");
					sb.append("<field name=\"m_surname_eng\">"+ORIGDisplayFormatUtil.forHTMLTag(personalInfoDataM.getMEngLastName())+"</field>");
					if(personalInfoDataM.getMBirthDate()==null){
						sDate="";
					}else{
						sDate=ORIGUtility.displayEngToThaiDate(ORIGDisplayFormatUtil.parseDate(personalInfoDataM.getMBirthDate()));
					}
					sb.append("<field name=\"m_birth_date\">"+sDate+"</field>");
					sb.append("<field name=\"m_income\">"+ORIGDisplayFormatUtil.displayCommaNumber(personalInfoDataM.getMIncome())+"</field>");
					sb.append("<field name=\"m_age\">"+personalInfoDataM.getMAge()+"</field>");
					sb.append("<field name=\"m_occupation\">"+ORIGDisplayFormatUtil.forHTMLTag(personalInfoDataM.getMOccupation())+"</field>");
					sb.append("<field name=\"m_occupation_desc\">"+ORIGDisplayFormatUtil.forHTMLTag("")+"</field>");
					sb.append("<field name=\"m_company\">"+ORIGDisplayFormatUtil.forHTMLTag(personalInfoDataM.getMCompany())+"</field>");
					sb.append("<field name=\"m_department\">"+ORIGDisplayFormatUtil.forHTMLTag(personalInfoDataM.getMDepartment())+"</field>");
					sb.append("<field name=\"m_position\">"+ORIGDisplayFormatUtil.forHTMLTag(personalInfoDataM.getMPosition())+"</field>");
					sb.append("<field name=\"m_position_desc\">"+ORIGDisplayFormatUtil.forHTMLTag("")+"</field>");
					//Found Record
					sb.append("<field name=\"Finance\">"+utility.convertXMLIllegalChar(utility.getFinanceTable(personalInfoDataM.getFinanceVect(), request))+"</field>");
					sb.append("<field name=\"ChangeName\">"+utility.convertXMLIllegalChar(utility.getChangeNameTable(personalInfoDataM.getChangeNameVect(), request))+"</field>");
					//sb.append("<field name=\"foundChangeName\">"+changeNameSize+"</field>");
				}
			}
			
			sb.append("<field name=\"Address\">"+utility.convertXMLIllegalChar(utility.getAddressTable(personalInfoDataM.getAddressVect(), request))+"</field>");
			sb.append("<field name=\"foundAddress\">"+addressSize+"</field>");
			sb.append("</list>");
			
			returnValue = sb.toString();
		}catch(Exception e){
			logger.error("Error >>> ", e);
			if(userM == null){
				sb = new StringBuffer("");
				sb.append("<?xml version=\"1.0\" encoding=\"UTF-8\"?>");
				sb.append("<list>");
				sb.append("<field name=\"error\">This page has been expired. Please close this window and login again on new window.</field>");
				sb.append("</list>");
				returnValue = sb.toString();
			}else{
				sb = new StringBuffer("");
				sb.append("<?xml version=\"1.0\" encoding=\"UTF-8\"?>");
				sb.append("<list>");
				sb.append("<field name=\"error\">Can not edit data, please contact admin.</field>");
				sb.append("</list>");
				returnValue = sb.toString();
			}
		}
		//Create response
		resp.setContentType("text/xml;charset=UTF-8");
		resp.setHeader("Cache-Control", "no-cache");
		
		PrintWriter pw = resp.getWriter();	
		logger.debug("returnValue = "+returnValue);
		
		pw.write(returnValue);
		pw.close();
		logger.debug("==> out doPost");
		
	}

}
