/*
 * Created on Oct 25, 2007
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

import com.eaf.orig.formcontrol.view.form.ORIGFormHandler;
import com.eaf.orig.profile.model.UserDetailM;
import com.eaf.orig.shared.constant.OrigConstant;
import com.eaf.orig.shared.ejb.ORIGApplicationManager;
import com.eaf.orig.shared.model.AddressDataM;
import com.eaf.orig.shared.model.BankDataM;
import com.eaf.orig.shared.model.ChangeNameDataM;
import com.eaf.orig.shared.model.PersonalInfoDataM;
import com.eaf.orig.shared.service.ORIGEJBService;
import com.eaf.orig.shared.utility.ORIGCacheUtil;
import com.eaf.orig.shared.utility.ORIGDisplayFormatUtil;
import com.eaf.orig.shared.utility.ORIGMandatoryErrorUtil;
import com.eaf.orig.shared.utility.ORIGUtility;
import com.eaf.xrules.shared.model.XRulesVerificationResultDataM;


/**
 * @author Weeraya
 *
 * Type: RetrievePersonalInfoServlet
 */
public class RetrievePersonalInfoServlet extends HttpServlet {
	Logger logger = Logger.getLogger(RetrievePersonalInfoServlet.class);
	
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
		logger.debug("<<<<<<< Start RetrievePersonalInfoServlet >>>>>>>");
		String returnValue;
		StringBuffer sb = new StringBuffer("");
		UserDetailM userM = (UserDetailM) request.getSession().getAttribute("ORIGUser");
		try{
			ORIGFormHandler formHandler = (ORIGFormHandler) request.getSession().getAttribute(OrigConstant.FORMHANDLER_NAME);
			
	        ORIGMandatoryErrorUtil errorUtil = new ORIGMandatoryErrorUtil();
	        ORIGUtility utility = new ORIGUtility();
	        ORIGCacheUtil cacheUtil = new ORIGCacheUtil();
			
	        String personalID = request.getParameter("identification_no");
	        String customerType = request.getParameter("customer_type");
	        logger.debug("personalID = "+personalID);
	        logger.debug("customerType = "+customerType);
	        String personalType =request.getParameter("appPersonalType");  //(String) request.getSession().getAttribute("PersonalType");
	        logger.debug("personalType = "+personalType);
	         
	        PersonalInfoDataM prmPersonalInfoDataM=null;
	    	if(OrigConstant.PERSONAL_TYPE_GUARANTOR.equals(personalType)){
	    	    prmPersonalInfoDataM = (PersonalInfoDataM) request.getSession(true).getAttribute("MAIN_POPUP_DATA");
	    	    if(prmPersonalInfoDataM==null){
	    	    	prmPersonalInfoDataM=new PersonalInfoDataM(); 
	    	    	request.getSession(true).setAttribute("MAIN_POPUP_DATA",prmPersonalInfoDataM);	    	    
	    	    }	    	    
	    	}else if (OrigConstant.PERSONAL_TYPE_APPLICANT.equals(personalType)){
	    	    prmPersonalInfoDataM = utility.getPersonalInfoByType(formHandler.getAppForm(),OrigConstant.PERSONAL_TYPE_APPLICANT);
	    	}else if (OrigConstant.PERSONAL_TYPE_SUP_CARD.equals(personalType)){
	    	    prmPersonalInfoDataM = (PersonalInfoDataM) request.getSession(true).getAttribute("SUPCARD_POPUP_DATA");
	    	    if(prmPersonalInfoDataM==null){
	    	    	prmPersonalInfoDataM=new PersonalInfoDataM(); 
	    	    	request.getSession(true).setAttribute("SUPCARD_POPUP_DATA",prmPersonalInfoDataM);	    	    
	    	    }	
	    	}
	    	if(prmPersonalInfoDataM!=null){
		        String id = personalID;
		        XRulesVerificationResultDataM resultDataM = prmPersonalInfoDataM.getXrulesVerification();	        
		        //PersonalInfoDataM personalInfoDataM = new PersonalInfoDataM();
		        if(prmPersonalInfoDataM==null){prmPersonalInfoDataM=new PersonalInfoDataM(); }
		        prmPersonalInfoDataM.setIdNo(personalID);
		        
		        //Comment By Rawi Songchaisin Remove CMPCODE
		        //prmPersonalInfoDataM.setCmpCode(OrigConstant.ORIG_CMPCODE);
		        
		        prmPersonalInfoDataM.setPersonalType(personalType);	        
		        String providerUrlEXT = null;
				String jndiEXT = null;
		        //ApplicationEXTManager applicationEXTManager = ORIGEJBService.getApplicationEXTManager(providerUrlEXT, jndiEXT);
		        
		       // PersonalInfoDataM personalInfoDataM = applicationEXTManager.loadModelPersonal(prmPersonalInfoDataM);
		        
				ORIGApplicationManager applicationManager = ORIGEJBService.getApplicationManager();
				PersonalInfoDataM personalInfoDataM = applicationManager.loadModelPersonal(prmPersonalInfoDataM);
		        personalInfoDataM.setXrulesVerification(resultDataM);
		        //request.getSession().setAttribute("TMP_ADDRESS",personalInfoDataM.getAddressTmpVect());	         
		        logger.debug("personalInfoDataM.getCustomerType() = "+personalInfoDataM.getCustomerType());
				if(customerType.equals(personalInfoDataM.getCustomerType())){
					String houseId = "";
			       // Vector addressVect = personalInfoDataM.getAddressTmpVect();
			        Vector addressVect = personalInfoDataM.getAddressVect();
			        //**** For personal type = supplementary card ****//
			        if (OrigConstant.PERSONAL_TYPE_SUP_CARD.equals(personalType)){
				        for (int i=addressVect.size()-1;i>=0;i--){
				        	AddressDataM addressDataM = (AddressDataM) addressVect.get(i);
				        	if (!OrigConstant.ADDRESS_TYPE_HOME.equals(addressDataM.getAddressType())){
				        		addressVect.remove(i);
				        	}
				        }
				        for(int i=0; i<addressVect.size(); i++){
				        	AddressDataM addressDataM = (AddressDataM) addressVect.get(i);
				        	addressDataM.setAddressSeq(i+1);
				        }
			        }
			        //************************************************//
			        AddressDataM addressDataM;
			        for(int i=0; i<addressVect.size(); i++){
			        	addressDataM = (AddressDataM) addressVect.get(i);
			        	/*if(("").equals(addressDataM.getOrigOwner()) || !("Y").equals(addressDataM.getOrigOwner())){
			        		addressDataM.setOrigOwner("N");
			        	}*/
			        	if(OrigConstant.ADDRESS_TYPE_IC.equals(addressDataM.getAddressType())){
			        		houseId = addressDataM.getHouseID();
			        	}
			        	addressDataM.setCreateBy(userM.getUserName());
			        	//addressDataM.setUpdateBy(userM.getUserName());
			        }
			        Vector changeNameVect = personalInfoDataM.getChangeNameVect();
			        ChangeNameDataM changeNameDataM;
			        for(int i=0; i<changeNameVect.size(); i++){
			        	changeNameDataM = (ChangeNameDataM) changeNameVect.get(i);
			        	changeNameDataM.setSeq(i+1);
			        	/*if(("").equals(changeNameDataM.getOrigOwner()) || !("Y").equals(changeNameDataM.getOrigOwner())){
			        		changeNameDataM.setOrigOwner("N");
			        	}*/
			        	changeNameDataM.setCreateBy(userM.getUserName());
			        	//changeNameDataM.setUpdateBy(userM.getUserName());
			        }
			        Vector financeVect = personalInfoDataM.getFinanceVect();
			        BankDataM bankDataM;
			        for(int i=0; i<financeVect.size(); i++){
			        	bankDataM = (BankDataM) financeVect.get(i);
			        	/*if(("").equals(bankDataM.getOrigOwner()) || !("Y").equals(bankDataM.getOrigOwner())){
			        		bankDataM.setOrigOwner("N");
			        	}*/
			        	bankDataM.setCreateBy(userM.getUserName());
			        	//bankDataM.setUpdateBy(userM.getUserName());
			        }
			        //formHandler.setPersonalTmp(personalInfoDataM);
			        
			        //PersonalInfoDataM personalInfoDataMTmp = utility.getPersonalInfoByType(formHandler.getAppForm(),OrigConstant.PERSONAL_TYPE_APPLICANT);
			        //personalInfoDataMTmp.setChangeNameVect(changeNameVect);
			        //personalInfoDataMTmp.setFinanceVect(financeVect);
			        
			        int addressSize = personalInfoDataM.getAddressTmpVect().size();
			        int carSize = 0;
			        int changeNameSize = personalInfoDataM.getChangeNameVect().size();
			        int financeSize = personalInfoDataM.getFinanceVect().size();
			        logger.debug("addressSize = "+addressSize);
			        logger.debug("changeNameSize = "+changeNameSize);
			        logger.debug("financeSize = "+financeSize);
	//		        personalInfoDataM.setAddressIndex(addressSize+1);
	//		        personalInfoDataM.setChangeNameIndex(changeNameSize+1);
	//		        personalInfoDataM.setFinanceIndex(financeSize+1);
			        personalInfoDataM.setAddressIndex((utility.getMaxAddressSeq(personalInfoDataM.getAddressTmpVect()))+1);
					personalInfoDataM.setFinanceIndex((utility.getMaxFinanceSeq(personalInfoDataM.getFinanceVect()))+1);
					personalInfoDataM.setChangeNameIndex((utility.getMaxChangeNameSeq(personalInfoDataM.getChangeNameVect()))+1);
			        personalInfoDataM.setPersonalSeq(0);
			        personalInfoDataM.setAddressTmpVect((Vector)ORIGUtility.cloneObject(addressVect));
			        logger.debug("addressVect "+addressVect);
			        prmPersonalInfoDataM.setAddressTmpVect(personalInfoDataM.getAddressTmpVect());
			        sb.append("<?xml version=\"1.0\" encoding=\"UTF-8\"?>");
			        /*sb.append("<customer_type>");
			        sb.append(personalInfoDataM.getCustomerType());
			        sb.append("</customer_type>");*/
					sb.append("<list>");
					//Card Information
					//sb.append("<field name=\"title_thai_desc\">"+ORIGDisplayFormatUtil.forHTMLTag()+"</field>");
					//Customer Infomation
					String titleThai = cacheUtil.getORIGMasterDisplayNameDataM("Title", personalInfoDataM.getThaiTitleName());
					if(ORIGUtility.isEmptyString(personalInfoDataM.getCardIdentity())){
						if(OrigConstant.CUSTOMER_TYPE_INDIVIDUAL.equals(personalInfoDataM.getCustomerType())){
							personalInfoDataM.setCardIdentity(OrigConstant.CARD_TYPE_IDENTIFICATION);
						}else if(OrigConstant.CUSTOMER_TYPE_CORPORATE.equals(personalInfoDataM.getCustomerType())){
							personalInfoDataM.setCardIdentity(OrigConstant.CARD_TYPE_CERTIFY);
						}
					}
					personalInfoDataM.setCardID(personalID);
					sb.append("<field name=\"title_thai_desc\">"+ORIGDisplayFormatUtil.forHTMLTag(titleThai)+"</field>");
					sb.append("<field name=\"title_thai\">"+ORIGDisplayFormatUtil.forHTMLTag(personalInfoDataM.getThaiTitleName())+"</field>");
					sb.append("<field name=\"name_thai\">"+ORIGDisplayFormatUtil.forHTMLTag(personalInfoDataM.getThaiFirstName())+"</field>");
					sb.append("<field name=\"surname_thai\">"+ORIGDisplayFormatUtil.forHTMLTag(personalInfoDataM.getThaiLastName())+"</field>");
					String titleEng = cacheUtil.getORIGMasterDisplayNameDataM("TitleEng", personalInfoDataM.getEngTitleName());
					sb.append("<field name=\"title_eng_desc\">"+ORIGDisplayFormatUtil.forHTMLTag(titleEng)+"</field>");
					sb.append("<field name=\"title_eng\">"+ORIGDisplayFormatUtil.forHTMLTag(personalInfoDataM.getEngTitleName())+"</field>");
					sb.append("<field name=\"name_eng\">"+ORIGDisplayFormatUtil.forHTMLTag(personalInfoDataM.getEngFirstName())+"</field>");
					sb.append("<field name=\"surname_eng\">"+ORIGDisplayFormatUtil.forHTMLTag(personalInfoDataM.getEngLastName())+"</field>");
					sb.append("<field name=\"customer_segment\">"+ORIGDisplayFormatUtil.forHTMLTag(personalInfoDataM.getCustomerSegment())+"</field>");
					String sDate = null;
					if(personalInfoDataM.getBirthDate()==null){
						sDate="";
					}else{
						sDate=ORIGUtility.displayEngToThaiDate(ORIGDisplayFormatUtil.parseDate(personalInfoDataM.getBirthDate()));
					}
					sb.append("<field name=\"birth_date\">"+sDate+"</field>");
					sb.append("<field name=\"age\">"+ personalInfoDataM.getAge()+"</field>");
					sb.append("<field name=\"race\">"+ORIGDisplayFormatUtil.forHTMLTag(personalInfoDataM.getRace())+"</field>");
					sb.append("<field name=\"gender\">"+ORIGDisplayFormatUtil.forHTMLTag(personalInfoDataM.getGender())+"</field>");
					sb.append("<field name=\"marital_status\">"+ORIGDisplayFormatUtil.forHTMLTag(personalInfoDataM.getMaritalStatus())+"</field>");
					sb.append("<field name=\"customer_type\">"+ORIGDisplayFormatUtil.forHTMLTag(customerType)+"</field>");
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
					if(!ORIGUtility.isEmptyString(personalInfoDataM.getCardID())){
						sb.append("<field name=\"card_id\">"+ORIGDisplayFormatUtil.forHTMLTag(personalInfoDataM.getCardID())+"</field>");
					}else{
						sb.append("<field name=\"card_id\">"+ORIGDisplayFormatUtil.forHTMLTag(personalID)+"</field>");
					}
					sb.append("<field name=\"issue_by\">"+ORIGDisplayFormatUtil.forHTMLTag(personalInfoDataM.getIssueBy())+"</field>");
					
					//Occupation
					if(!OrigConstant.ROLE_CMR.equals(userM.getRoles().elementAt(0))){
						sb.append("<field name=\"occupation\">"+ORIGDisplayFormatUtil.forHTMLTag(personalInfoDataM.getOccupation())+"</field>");
						sb.append("<field name=\"bus_type\">"+ORIGDisplayFormatUtil.forHTMLTag(personalInfoDataM.getBusinessType())+"</field>");
						sb.append("<field name=\"bus_size\">"+ORIGDisplayFormatUtil.forHTMLTag(personalInfoDataM.getBusinessSize())+"</field>");
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
						//sb.append("<field name=\"time_current_address\">"+personalInfoDataM.getTimeInCurrentAddress()+"</field>");
						sb.append("<field name=\"house_regis_desc\">"+ORIGDisplayFormatUtil.forHTMLTag("")+"</field>");
						sb.append("<field name=\"pre_record\">"+ORIGDisplayFormatUtil.forHTMLTag(personalInfoDataM.getPreviousRecord())+"</field>");
						sb.append("<field name=\"building_condition\">"+ORIGDisplayFormatUtil.forHTMLTag(personalInfoDataM.getBulidingCondition())+"</field>");
						sb.append("<field name=\"building_desc\">"+ORIGDisplayFormatUtil.forHTMLTag("")+"</field>");
						sb.append("<field name=\"location\">"+ORIGDisplayFormatUtil.forHTMLTag(personalInfoDataM.getLocation())+"</field>");
						sb.append("<field name=\"location_desc\">"+ORIGDisplayFormatUtil.forHTMLTag("")+"</field>");
						sb.append("<field name=\"asset_amount\">"+ORIGDisplayFormatUtil.displayCommaNumber(personalInfoDataM.getAssetAmount())+"</field>");
						sb.append("<field name=\"debt_amount\">"+ORIGDisplayFormatUtil.displayCommaNumber(personalInfoDataM.getDebitAmount())+"</field>");
						sb.append("<field name=\"land_ownership\">"+ORIGDisplayFormatUtil.forHTMLTag(personalInfoDataM.getLandOwnerShip())+"</field>");
						sb.append("<field name=\"cheque_return\">"+ORIGDisplayFormatUtil.displayCommaNumber(personalInfoDataM.getChequeReturn())+"</field>");
						sb.append("<field name=\"no_of_employee\">"+personalInfoDataM.getNoOfEmployee()+"</field>");
					
						if(OrigConstant.PERSONAL_TYPE_APPLICANT.equals(personalType)){
							//Marry Infomation
							logger.debug("personalInfoDataM.getMThaiTitleName() = "+personalInfoDataM.getMThaiTitleName());
							sb.append("<field name=\"m_card_type\">"+ORIGDisplayFormatUtil.forHTMLTag(personalInfoDataM.getMCardType())+"</field>");
							sb.append("<field name=\"m_id_no\">"+ORIGDisplayFormatUtil.forHTMLTag(personalInfoDataM.getMIDNo())+"</field>");
							titleThai = cacheUtil.getORIGMasterDisplayNameDataM("Title", personalInfoDataM.getMThaiTitleName());
							sb.append("<field name=\"m_title_thai_desc\">"+titleThai+"</field>");
							sb.append("<field name=\"m_title_thai\">"+ORIGDisplayFormatUtil.forHTMLTag(personalInfoDataM.getMThaiTitleName())+"</field>");
							sb.append("<field name=\"m_name_thai\">"+ORIGDisplayFormatUtil.forHTMLTag(personalInfoDataM.getMThaiFirstName())+"</field>");
							sb.append("<field name=\"m_surname_thai\">"+ORIGDisplayFormatUtil.forHTMLTag(personalInfoDataM.getMThaiLastName())+"</field>");
							titleEng = cacheUtil.getORIGMasterDisplayNameDataM("TitleEng", personalInfoDataM.getMEngTitleName());
							sb.append("<field name=\"m_title_eng_desc\">"+titleEng+"</field>");
							sb.append("<field name=\"m_title_eng\">"+ORIGDisplayFormatUtil.forHTMLTag(personalInfoDataM.getMEngTitleName())+"</field>");
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
							sb.append("<field name=\"m_gender\">"+ORIGDisplayFormatUtil.forHTMLTag(personalInfoDataM.getMGender())+"</field>");
							sb.append("<field name=\"m_company\">"+ORIGDisplayFormatUtil.forHTMLTag(personalInfoDataM.getMCompany())+"</field>");
							sb.append("<field name=\"m_department\">"+ORIGDisplayFormatUtil.forHTMLTag(personalInfoDataM.getMDepartment())+"</field>");
							sb.append("<field name=\"m_position\">"+ORIGDisplayFormatUtil.forHTMLTag(personalInfoDataM.getMPosition())+"</field>");
							sb.append("<field name=\"m_position_desc\">"+ORIGDisplayFormatUtil.forHTMLTag("")+"</field>");
							//Found Record
							sb.append("<field name=\"Finance\">"+utility.convertXMLIllegalChar(utility.getFinanceTable(financeVect, request))+"</field>");
							
							//sb.append("<field name=\"foundChangeName\">"+changeNameSize+"</field>");
						}
						sb.append("<field name=\"Address\">"+utility.convertXMLIllegalChar(utility.getAddressTable(personalInfoDataM.getAddressVect(), request))+"</field>");
						//sb.append("<field name=\"foundAddress\">"+addressSize+"</field>");
						//address Table
						//String addressTableData = utility.getAddressTable(addressVect, request);
						//sb.append("<field name=\"Address\">"+ORIGDisplayFormatUtil.replaceDoubleQuot(addressTableData)+"</field>");					
					}else{
						sb.append("<field name=\"pre_score_occupation\">"+ORIGDisplayFormatUtil.forHTMLTag(personalInfoDataM.getOccupation())+"</field>");
						sb.append("<field name=\"pre_score_bus_type\">"+ORIGDisplayFormatUtil.forHTMLTag(personalInfoDataM.getBusinessType())+"</field>");
						sb.append("<field name=\"pre_score_bus_size\">"+ORIGDisplayFormatUtil.forHTMLTag(personalInfoDataM.getBusinessSize())+"</field>");
						sb.append("<field name=\"pre_score_position\">"+ORIGDisplayFormatUtil.forHTMLTag(personalInfoDataM.getPosition())+"</field>");
						sb.append("<field name=\"pre_score_salary\">"+ORIGDisplayFormatUtil.displayCommaNumber(personalInfoDataM.getSalary())+"</field>");
						sb.append("<field name=\"pre_score_other_income\">"+ORIGDisplayFormatUtil.displayCommaNumber(personalInfoDataM.getOtherIncome())+"</field>");
						sb.append("<field name=\"pre_score_qualification\">"+personalInfoDataM.getQualification()+"</field>");
						sb.append("<field name=\"pre_score_year\">"+personalInfoDataM.getYearOfWork()+"</field>");
						sb.append("<field name=\"pre_score_month\">"+personalInfoDataM.getMonthOfWork()+"</field>");
						sb.append("<field name=\"pre_score_house_regis_status\">"+ORIGDisplayFormatUtil.forHTMLTag(personalInfoDataM.getHouseRegisStatus())+"</field>");
						//sb.append("<field name=\"pre_score_time_current_address\">"+personalInfoDataM.getTimeInCurrentAddress()+"</field>");
						sb.append("<field name=\"pre_score_land_ownership\">"+ORIGDisplayFormatUtil.forHTMLTag(personalInfoDataM.getLandOwnerShip())+"</field>");
						sb.append("<field name=\"pre_score_houseid\">"+ORIGDisplayFormatUtil.forHTMLTag(houseId)+"</field>");
						sb.append("<field name=\"pre_score_cheque_return\">"+ORIGDisplayFormatUtil.displayCommaNumber(personalInfoDataM.getChequeReturn())+"</field>");
						sb.append("<field name=\"pre_score_no_of_employee\">"+personalInfoDataM.getNoOfEmployee()+"</field>");
					}
					//if(OrigConstant.PERSONAL_TYPE_APPLICANT.equals(personalType)){
						sb.append("<field name=\"ChangeName\">"+utility.convertXMLIllegalChar(utility.getChangeNameTable(changeNameVect, request))+"</field>");
					//}
					sb.append("</list>");
					
					logger.debug("addressSizeVect = "+personalInfoDataM.getAddressTmpVect().size());
					logger.debug("addressSize = "+personalInfoDataM.getAddressVect().size());
					Vector personalVect = formHandler.getAppForm().getPersonalInfoVect();
					
					//Remove personal
					PersonalInfoDataM personalInfoDataM2;
					for(int i=0; i< personalVect.size(); i++){
						personalInfoDataM2 = (PersonalInfoDataM) personalVect.get(i);
						if(OrigConstant.PERSONAL_TYPE_APPLICANT.equals(personalType)){
							personalVect.remove(i);
							logger.debug("remove Applicant");
						}else{
							if(personalInfoDataM2 != null && personalInfoDataM2.getIdNo() != null){
								if(personalInfoDataM2.getIdNo().equals(id)){
									personalVect.remove(i);
									logger.debug("remove personalInfoDataM 2");
								}
							}
						}
					}
					//Set new personal seq
					Vector guarantorVect = utility.getVectorPersonalInfoByType(formHandler.getAppForm(), OrigConstant.PERSONAL_TYPE_GUARANTOR);
					for(int i = 0; i < guarantorVect.size(); i++){
						PersonalInfoDataM personalInfoDataMG = (PersonalInfoDataM)guarantorVect.get(i);
						personalInfoDataMG.setPersonalSeq(i + 1);
					}
					Vector supCardVect = utility.getVectorPersonalInfoByType(formHandler.getAppForm(), OrigConstant.PERSONAL_TYPE_SUP_CARD);
					for(int i = 0; i < supCardVect.size(); i++){
						PersonalInfoDataM personalInfoDataMG = (PersonalInfoDataM)supCardVect.get(i);
						personalInfoDataMG.setPersonalSeq(i + 1);
					}
					//Add new personal
					if(OrigConstant.PERSONAL_TYPE_GUARANTOR.equals(personalType)){
						request.getSession().setAttribute("MAIN_POPUP_DATA", personalInfoDataM);
					}else if(OrigConstant.PERSONAL_TYPE_SUP_CARD.equals(personalType)){
						request.getSession().setAttribute("SUPCARD_POPUP_DATA", personalInfoDataM);
					}else{
						logger.debug("add new personalInfoDataM");
						personalVect.add(personalInfoDataM);
					}
				}else{
					sb.append("<?xml version=\"1.0\" encoding=\"UTF-8\"?>");
					sb.append("<list>");
					if(ORIGUtility.isEmptyString(personalInfoDataM.getCustomerType())){
						sb.append("<field name=\"error\">Not Found Customer ID."+personalInfoDataM.getIdNo()+"</field>");
					}else{
						sb.append("<field name=\"error\">Customer Type Not Match.</field>");
					}
					sb.append("</list>");
				}
				logger.debug("personalInfoDataM===>"+personalInfoDataM);
				logger.debug("personalInfoDataM.getThaiTitleName()===>"+personalInfoDataM.getThaiTitleName());
				request.getSession().setAttribute("personalInfoSession",personalInfoDataM);
	    	}else{
	    	    logger.debug("Personal type=null");
	    	    sb = new StringBuffer("");
				sb.append("<?xml version=\"1.0\" encoding=\"UTF-8\"?>");
				sb.append("<list>");
				sb.append("<field name=\"error\">Can not retrieve data, please contact admin.</field>");
				sb.append("</list>");
	    	}
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
				sb.append("<field name=\"error\">Can not retrieve data, please contact admin.</field>");
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
		logger.debug("==> out doPost ");
		
	}

}
