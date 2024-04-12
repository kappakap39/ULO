/*
 * Created on Dec 11, 2007
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
package com.eaf.orig.ulo.pl.app.utility;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.ConnectException;
import java.net.InetAddress;
import java.net.Socket;
import java.util.Vector;

import org.apache.log4j.Logger;

import com.eaf.orig.master.shared.model.GeneralParamM;
import com.eaf.orig.profile.model.UserDetailM;
import com.eaf.orig.shared.constant.OrigConstant;
//import com.eaf.orig.shared.dao.OrigMasterDAOFactory;
//import com.eaf.orig.shared.dao.OrigMasterGenParamDAO;
import com.eaf.orig.shared.model.ApplicationDataM;
import com.eaf.orig.shared.model.PersonalInfoDataM;
import com.eaf.orig.shared.model.SMSDataM;
import com.eaf.orig.shared.model.ServiceDataM;
import com.eaf.orig.ulo.pl.service.PLORIGEJBService;
import com.eaf.orig.wf.shared.ORIGWFConstant;

/**
 * @author Administrator
 *
 * Type: SendSMS
 */
public class SendSMS {
	Logger logger = Logger.getLogger(SendSMS.class);
	
	public SendSMS() {
		
	}

	public String sendSMS(SMSDataM smsDataM, ServiceDataM serviceDataM) {
		//ORIGUtility utility = new ORIGUtility();
		String smsHost = serviceDataM.getSmsHost();
		String smsPortStr = serviceDataM.getSmsPort();
		logger.debug("smsHost = "+smsHost);
		logger.debug("smsPortStr = "+smsPortStr);
		int smsPort = 0;
		if(smsPortStr != null){
			smsPort = Integer.parseInt(smsPortStr);
		}else{
			logger.info("SMS Port is null");
			return null;
		}
		
		StringBuffer strBuf = new StringBuffer();
		StringBuffer resBuf = new StringBuffer();
		String line = "";
		try {
			// Construct data
			strBuf.append("CMD=SENDMSG");
			strBuf.append("&FROM=" + smsDataM.getFrom());
			strBuf.append("&TO=" + smsDataM.getTo());
			strBuf.append("&CODE=" + smsDataM.getCode());
			strBuf.append("&REPORT=Y");
			strBuf.append("&CHARGE=Y");
			strBuf.append("&CTYPE=" + smsDataM.getCtype());
			if (smsDataM.getCtype().equals("UNICODE")) {
				strBuf.append("&CONTENT=" + TextToUnicode(smsDataM.getContent()));
			} else {
				strBuf.append("&CONTENT=" + smsDataM.getContent());
			}

			InetAddress addr = InetAddress.getByName(smsHost);
			Socket socket = new Socket(addr, smsPort);

			// Send header
			String path = "/servlet/SomeServlet";
			BufferedWriter wr = new BufferedWriter(new OutputStreamWriter(
					socket.getOutputStream(), "UTF8"));
			wr.write("POST " + path + " HTTP/1.0\r\n");
			wr.write("Content-Length: " + strBuf.length() + "\r\n");
			wr.write("Content-Type: application/x-www-form-urlencoded\r\n");
			wr.write("\r\n");

			// Send data
			wr.write(strBuf.toString());
			wr.flush();

			// Get response
			BufferedReader rd = new BufferedReader(new InputStreamReader(socket.getInputStream()));

			while ((line = rd.readLine()) != null) {
				resBuf.append(line);
			}
			wr.close();
			rd.close();

		} catch (ConnectException connExc) {
			logger.error("ConnectException Error >>> ", connExc);
			//connExc.printStackTrace();
		} catch (IOException e) {
			logger.error("IOException Error >>> ", e);
			//e.printStackTrace();
		}
		return resBuf.toString();
	}

	public String TextToUnicode(String text) {
		int ascii = 0;
		String temp = "";
		StringBuffer strbuf = new StringBuffer();
		//System.out.println(text.length());
		for (int i = 0; i <= text.length() - 1; i++) {
			ascii = (int) text.charAt(i);
			//System.out.println(ascii);
			temp = Integer.toHexString(0x10000 | ascii).substring(1)
					.toUpperCase();
			strbuf.append("%" + temp.substring(0, 2));
			strbuf.append("%" + temp.substring(2, 4));
			//System.out.println(Integer.toHexString(0x10000 | ascii)
			//		.substring(1).toUpperCase());
		}
		//System.out.println(strbuf.toString());
		//System.out.println("************");
		return strbuf.toString();
	}
	
	public SMSDataM getSMSData(ApplicationDataM appForm, String mobileNo, ServiceDataM serviceDataM, String userRole, String cmrOwner,String sendRole){
		SMSDataM dataM = new SMSDataM();
		try{
			dataM.setFrom(serviceDataM.getSmsSender());
			if("0".equals(mobileNo.substring(0,1))){
				mobileNo = "66"+mobileNo.substring(1);
			}
			dataM.setTo(mobileNo);
			dataM.setCtype("TEXT");
			//dataM.setCtype("UNICODE");
			logger.debug("serviceDataM.getSmsSender() = "+serviceDataM.getSmsSender());
			logger.debug("mobileNo = "+mobileNo);
			if(OrigConstant.ROLE_XCMR.equals(sendRole)){
				dataM.setContent(getMessageForXCMR(appForm, cmrOwner));
				//dataM.setContent(new String(getMessageForUW(appForm).getBytes("ISO8859_1"),"UTF-8"));
			}else if (OrigConstant.ROLE_UW.equals(sendRole)){
				dataM.setContent(getMessageForUW(appForm, cmrOwner));
				//dataM.setContent(new String(getMessageForXCMR(appForm).getBytes("ISO8859_1"),"UTF-8"));
			}else if(OrigConstant.ROLE_XCMR.equals(sendRole)){
			    dataM.setContent(getMessageForXUW(appForm, cmrOwner));
			}
			
//			OrigMasterGenParamDAO genParamDAO = OrigMasterDAOFactory.getOrigMasterGenParamDAO();
//			GeneralParamM generalParamM =  genParamDAO.loadModelOrigMasterGenParamM(OrigConstant.ParamCode.SMS_CODE, OrigConstant.BUSINESS_CLASS_AL);
			
			GeneralParamM generalParamM = PLORIGEJBService.getORIGDAOUtilLocal()
												.loadModelOrigMasterGenParamM(OrigConstant.ParamCode.SMS_CODE, OrigConstant.BUSINESS_CLASS_AL);
			
			dataM.setCode(generalParamM.getParamValue());
			logger.debug("code = "+generalParamM.getParamValue());
			logger.debug("content"+dataM.getContent());
			
		}catch(Exception e){
			logger.error("Error in getSMSData(ApplicationDataM appForm, String mobileNo) >>> ",e);
		}
		
		return dataM;
	}
	
	public String getMessageForUW(ApplicationDataM appForm, String cmrOwner){
		StringBuffer buffer = new StringBuffer();
		try{
//			OrigMasterGenParamDAO genParamDAO = OrigMasterDAOFactory.getOrigMasterGenParamDAO();
//			GeneralParamM generalParamM =  genParamDAO.loadModelOrigMasterGenParamM(OrigConstant.ParamCode.SMS_CONTENT_UW, OrigConstant.BUSINESS_CLASS_AL);
			
			GeneralParamM generalParamM = PLORIGEJBService.getORIGDAOUtilLocal()
												.loadModelOrigMasterGenParamM(OrigConstant.ParamCode.SMS_CONTENT_UW, OrigConstant.BUSINESS_CLASS_AL);
			
			String decision = appForm.getUwDecision();
			if(ORIGWFConstant.ApplicationDecision.UW_PENDING.equals(decision)){
				decision = ORIGWFConstant.ApplicationStatus.PENDING;
			}else if(ORIGWFConstant.ApplicationDecision.APPROVE.equals(decision)){
				decision = ORIGWFConstant.ApplicationStatus.APPROVED;
			}else if(ORIGWFConstant.ApplicationDecision.CANCEL.equals(decision)){
				decision = ORIGWFConstant.ApplicationStatus.CANCELLED;
			}else if(ORIGWFConstant.ApplicationDecision.REJECT.equals(decision)){
				decision = ORIGWFConstant.ApplicationStatus.REJECTED;
			}else if(ORIGWFConstant.ApplicationDecision.ESCALATE.equals(decision)){
				decision = ORIGWFConstant.ApplicationStatus.ESCALATED;
			}else if(ORIGWFConstant.ApplicationDecision.APPROVE_WITH_EXCEPTION.equals(decision)){
				decision = "Sent to Exception CMR";
			}
			
			String content = generalParamM.getParamValue();
			//content = content.replaceAll(OrigConstant.P_APPNO, appForm.getApplicationNo());
			//content = content.replaceAll(OrigConstant.P_DECISION, "\""+decision+"\"");
			//content = content.replaceAll(OrigConstant.P_CA, appForm.getUpdateBy());
			PersonalInfoDataM  personalInfoDataM=this.getPersonalInfoApplicaintType(appForm);
			content = content.replaceAll(OrigConstant.MailSmsParam.APPNO, "<b>"+appForm.getApplicationNo()+"</b>");
			content = content.replaceAll(OrigConstant.MailSmsParam.FIRST_NAME, "<b>\""+personalInfoDataM.getThaiFirstName()+"\"</b>");
			content = content.replaceAll(OrigConstant.MailSmsParam.LAST_NAME, "<b>\""+personalInfoDataM.getThaiLastName()+"\"</b>");
			content = content.replaceAll(OrigConstant.MailSmsParam.DECISION, "<b>\""+decision+"\"</b>");
			content = content.replaceAll(OrigConstant.MailSmsParam.USER, "<b>"+appForm.getUpdateBy()+"</b>");
			buffer.append(content);
			//buffer.append(" (CMR : "+cmrOwner+")");
			
		}catch(Exception e){
			logger.debug("Error >>> ", e);
		}
		return buffer.toString();
	}
	
	public String getMessageForXCMR(ApplicationDataM appForm, String cmrOwner){
		StringBuffer buffer = new StringBuffer();
		try{
//			OrigMasterGenParamDAO genParamDAO = OrigMasterDAOFactory.getOrigMasterGenParamDAO();
//			GeneralParamM generalParamM =  genParamDAO.loadModelOrigMasterGenParamM(OrigConstant.ParamCode.SMS_CONTENT_XCMR, OrigConstant.BUSINESS_CLASS_AL);
//			
			
			GeneralParamM generalParamM = PLORIGEJBService.getORIGDAOUtilLocal()
													.loadModelOrigMasterGenParamM(OrigConstant.ParamCode.SMS_CONTENT_XCMR, OrigConstant.BUSINESS_CLASS_AL);
			
			String decision = appForm.getXcmrDecision();
			String content = generalParamM.getParamValue();
			//content = content.replaceAll(OrigConstant.P_APPNO, appForm.getApplicationNo());
			PersonalInfoDataM  personalInfoDataM=this.getPersonalInfoApplicaintType(appForm);
			content = content.replaceAll(OrigConstant.MailSmsParam.APPNO, "<b>"+appForm.getApplicationNo()+"</b>");
			content = content.replaceAll(OrigConstant.MailSmsParam.FIRST_NAME, "<b>\""+personalInfoDataM.getThaiFirstName()+"\"</b>");
			content = content.replaceAll(OrigConstant.MailSmsParam.LAST_NAME, "<b>\""+personalInfoDataM.getThaiLastName()+"\"</b>");
			content = content.replaceAll(OrigConstant.MailSmsParam.DECISION, "<b>\""+decision+"\"</b>");
			content = content.replaceAll(OrigConstant.MailSmsParam.USER, "<b>"+appForm.getUpdateBy()+"</b>");
			buffer.append(content);
			//buffer.append(" (CMR : "+cmrOwner+")");
			
		}catch(Exception e){
			logger.debug("Error >>> ", e);
		}
		return buffer.toString();
	}
	public String getMessageForXUW(ApplicationDataM appForm, String cmrOwner){
		StringBuffer buffer = new StringBuffer();
		try{
//			OrigMasterGenParamDAO genParamDAO = OrigMasterDAOFactory.getOrigMasterGenParamDAO();
//			GeneralParamM generalParamM =  genParamDAO.loadModelOrigMasterGenParamM(OrigConstant.ParamCode.SMS_CONTENT_XUW, OrigConstant.BUSINESS_CLASS_AL);
			
			GeneralParamM generalParamM = PLORIGEJBService.getORIGDAOUtilLocal()
												.loadModelOrigMasterGenParamM(OrigConstant.ParamCode.SMS_CONTENT_XUW, OrigConstant.BUSINESS_CLASS_AL);
			
			String content = generalParamM.getParamValue();
			String xuwDecision = appForm.getXuwDecision();
			String decision = xuwDecision;
			if(OrigConstant.ApplicationDecision.OVERIDED_POLICY.equals(decision)){
				decision = "UW Exception Overide Policy";
			}else if(OrigConstant.ApplicationDecision.NOT_OVERIDED_POLICY.equals(decision)){			 
			    decision = "UW Exception not Overide Policy";
			}
			PersonalInfoDataM  personalInfoDataM=this.getPersonalInfoApplicaintType(appForm);
			content = content.replaceAll(OrigConstant.MailSmsParam.APPNO, "<b>"+appForm.getApplicationNo()+"</b>");
			content = content.replaceAll(OrigConstant.MailSmsParam.FIRST_NAME, "<b>\""+personalInfoDataM.getThaiFirstName()+"\"</b>");
			content = content.replaceAll(OrigConstant.MailSmsParam.LAST_NAME, "<b>\""+personalInfoDataM.getThaiLastName()+"\"</b>");
			content = content.replaceAll(OrigConstant.MailSmsParam.DECISION, "<b>\""+decision+"\"</b>");
			content = content.replaceAll(OrigConstant.MailSmsParam.USER, "<b>"+appForm.getUpdateBy()+"</b>");
			
			buffer.append(content);
			//buffer.append(" (CMR : "+cmrOwner+")");
			
		}catch(Exception e){
			logger.debug("Error >>> ", e);
		}
		return buffer.toString();
	}
	
	public SMSDataM getSMSDataForSmsLog(SMSDataM dataM, String userName, UserDetailM userDetailM){
		try{
			SMSDataM dataMReturn = new SMSDataM();
			
			dataMReturn.setContent(dataM.getContent());
			dataMReturn.setFrom(userName);
			dataMReturn.setNumber(dataM.getTo());
			dataMReturn.setStatus(dataM.getContent());
			dataMReturn.setTo(userDetailM.getUserName());
			dataMReturn.setUpdateBy(userName);
			
			return dataMReturn;
			
		}catch(Exception e){
			logger.error("Error in getSMSDataForSmsLog(SMSDataM dataM) >>> ",e);
		}
		
		return dataM;
	}
	public PersonalInfoDataM getPersonalInfoApplicaintType(ApplicationDataM applicationDataM ) {
        Vector personalInfoVect = applicationDataM.getPersonalInfoVect();
        PersonalInfoDataM personalInfoDataM = null;
        if (personalInfoVect != null && personalInfoVect.size() > 0) {
            for (int i = 0; i < personalInfoVect.size(); i++) {
                personalInfoDataM = (PersonalInfoDataM) personalInfoVect.get(i);
                if (OrigConstant.PERSONAL_TYPE_APPLICANT.equals(personalInfoDataM.getPersonalType())) {
                    return personalInfoDataM;
                } else {
                    personalInfoDataM = null;
                }
            }
        }
        return personalInfoDataM;
    }
}
