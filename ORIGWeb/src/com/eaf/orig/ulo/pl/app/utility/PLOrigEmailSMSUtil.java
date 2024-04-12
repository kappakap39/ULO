package com.eaf.orig.ulo.pl.app.utility;

import java.util.Date;
//import java.util.Vector;
import org.apache.log4j.Logger;
import com.eaf.orig.cache.properties.GeneralParamProperties;
import com.eaf.orig.shared.constant.OrigConstant;
import com.eaf.orig.shared.dao.PLORIGDAOFactory;
import com.eaf.orig.shared.util.OrigUtil;
//import com.eaf.orig.ulo.pl.app.dao.ORIGBusinessClassDAO;
import com.eaf.orig.ulo.pl.app.dao.exception.PLOrigApplicationException;
import com.eaf.orig.ulo.pl.model.app.PLApplicationDataM;
//import com.eaf.orig.ulo.pl.model.app.PLCashTransferDataM;
//import com.eaf.orig.ulo.pl.model.app.PLReasonDataM;

public class PLOrigEmailSMSUtil {
	
	static Logger logger = Logger.getLogger(PLOrigEmailSMSUtil.class);
	
	public void sendEmailSMS(PLApplicationDataM applicationM, String role) throws PLOrigApplicationException{
		
//		PLOrigEmailSMSUtil sendUtil = new PLOrigEmailSMSUtil();
		
		PLOrigEmailUtil emailUtil = new PLOrigEmailUtil();
		PLOrigSMSUtil smsUtil = new PLOrigSMSUtil();
		
		if(LogicIgnoreSendEmail(applicationM)){
			logger.debug("IgnoreSendEmail..>> "+applicationM.getAppRecordID());
			return;
		}
		
		try{
			if(OrigConstant.ROLE_DE.equals(role)){
				
//				#septemwi comment change logic to call EmailSMSControl()...
//				if(sendUtil.countOrigContractLog(applicationM.getAppRecordID(), OrigConstant.EmailSMS.EMAIL_RECEIVE_NEW_APP_TO_BRANCH) == 0){
//					if(!emailUtil.sendEmailWithEmailTemplate(OrigConstant.EmailSMS.EMAIL_RECEIVE_NEW_APP_TO_BRANCH, applicationM)){
//						logger.error("##### Cannot send RECEIVE NEW APP TO Branch Email");
//					}
//				}
//				if (sendUtil.countOrigContractLog(applicationM.getAppRecordID(), OrigConstant.EmailSMS.SMS_RECEIVE_APPLICATION) == 0){
//					boolean isCustomerCancel = false;
//					//check cancel with customer request cancel or not?
//					if(OrigConstant.Action.CANCEL.equals(applicationM.getAppDecision())){
//						Vector<PLReasonDataM> reasonVt = applicationM.getReasonVect();
//						if(reasonVt != null && reasonVt.size() > 0){
//							for(int i=0;i<reasonVt.size();i++){
//								PLReasonDataM reasonM = (PLReasonDataM)reasonVt.get(i);
//								if(OrigConstant.fieldId.CANCEL_REASON.equals(reasonM.getReasonType()) &&  
//										OrigConstant.cancelReason.CUST_CANCEL.equals(reasonM.getReasonCode())){
//									isCustomerCancel = true;
//									break;
//								}
//							}
//						}
//					}
//					All decision except cancel with customer request cancel
//					if(!isCustomerCancel){
//						if(!smsUtil.sendSMSWithSMSCode(OrigConstant.EmailSMS.SMS_RECEIVE_APPLICATION, applicationM)){
//							logger.error("##### Cannot send RECEIVE APPLICATION SMS");
//						}
//					}
//				}
				
			}else if(OrigConstant.ROLE_DC.equals(role)){ 
				
//				#septemwi comment change logic to call EmailSMSControl()...
//				if(!OrigConstant.BusClass.FCP_KEC_CC.equals(applicationM.getBusinessClassId())
//						&& !OrigConstant.BusClass.FCP_KEC_CG.equals(applicationM.getBusinessClassId())){
//					if(sendUtil.countOrigContractLog(applicationM.getAppRecordID(), OrigConstant.EmailSMS.EMAIL_RECEIVE_NEW_APP_TO_BRANCH) == 0){
//							if(!emailUtil.sendEmailWithEmailTemplate(OrigConstant.EmailSMS.EMAIL_RECEIVE_NEW_APP_TO_BRANCH, applicationM)){
//						logger.error("##### Cannot send RECEIVE NEW APP TO Branch Email");
//							}
//					}
//				}				
//				if (sendUtil.countOrigContractLog(applicationM.getAppRecordID(), OrigConstant.EmailSMS.SMS_RECEIVE_APPLICATION) == 0){
//					boolean isCustomerCancel = false;
//					//check cancel with customer request cancel or not?
//					if(OrigConstant.Action.CANCEL.equals(applicationM.getAppDecision())){
//						Vector<PLReasonDataM> reasonVt = applicationM.getReasonVect();
//						if(reasonVt != null && reasonVt.size() > 0){
//							for(int i=0;i<reasonVt.size();i++){
//								PLReasonDataM reasonM = (PLReasonDataM)reasonVt.get(i);
//								if(OrigConstant.fieldId.CANCEL_REASON.equals(reasonM.getReasonType()) && 
//								   OrigConstant.cancelReason.CUST_CANCEL.equals(reasonM.getReasonCode())){
//									isCustomerCancel = true;
//									break;
//								}
//							}
//						}
//					}
//					All decision except cancel with customer request cancel
//					if(!isCustomerCancel){
//						if(!smsUtil.sendSMSWithSMSCode(OrigConstant.EmailSMS.SMS_RECEIVE_APPLICATION, applicationM)){
//							logger.error("##### Cannot send RECEIVE APPLICATION SMS");
//						}
//					}
//				}
				
			}else if(OrigConstant.ROLE_FU.equals(role)){				
				if(OrigConstant.FLAG_Y.equals(applicationM.getSmsFollowUp()) 
						&& OrigUtil.indexof(applicationM.getAppDecision(), OrigConstant.Action.COMPLETE_FU)){
					smsUtil.sendSMSWithSMSCode(OrigConstant.EmailSMS.SMS_COMPLETE_DOC, applicationM);
					emailUtil.sendEmailWithEmailTemplate(OrigConstant.EmailSMS.EMAIL_RECEIVE_FOLLOW_DOC_TO_BRANCH, applicationM);
				}				
			}else if(OrigConstant.ROLE_CA.equals(role)){ 
				
//				#septemwi comment change logic to call EmailSMSControl()...
//				if(OrigConstant.Action.APPROVE.equals(applicationM.getCaDecision()) || 
//						OrigConstant.Action.OVERRIDE.equals(applicationM.getCaDecision()) ||
//							OrigConstant.Action.POLICY_EXCEPTION.equals(applicationM.getCaDecision())){
//					if(OrigConstant.Channel.BRANCH.equals(applicationM.getApplyChannel()) ||
//							OrigConstant.Channel.K_CONTRACT_CENTER.equals(applicationM.getApplyChannel()) ||
//								OrigConstant.Channel.OUTBOUND_SALE.equals(applicationM.getApplyChannel()) ||
//									OrigConstant.Channel.DSA.equals(applicationM.getApplyChannel())){
//						if(!emailUtil.sendEmailWithEmailTemplate(OrigConstant.EmailSMS.EMAIL_APPROVE_TO_BRANCH, applicationM)){
//							logger.error("##### Cannot send Approve Email");
//						}
//					}
//				}		
//				
//				//SMS
//				if(OrigConstant.Action.APPROVE.equals(applicationM.getCaDecision()) || 
//						OrigConstant.Action.OVERRIDE.equals(applicationM.getCaDecision()) ||
//							OrigConstant.Action.POLICY_EXCEPTION.equals(applicationM.getCaDecision())){
//					if(OrigConstant.BusClass.FCP_KEC_IC.equals(applicationM.getBusinessClassId())){
//						if(!smsUtil.sendSMSWithSMSCode(OrigConstant.EmailSMS.SMS_APPROVE_INCREASE, applicationM)){
//							logger.error("##### Cannot send Approve SMS APPROVE_INCREASE");
//						}
//					}else if(OrigConstant.BusClass.FCP_KEC_DC.equals(applicationM.getBusinessClassId())){
//						if(!smsUtil.sendSMSWithSMSCode(OrigConstant.EmailSMS.SMS_APPROVE_DECREASE, applicationM)){
//							logger.error("##### Cannot send Approve SMS APPROVE_DECREASE");
//						}
//					}else{				
//						PLCashTransferDataM cashTransferM = applicationM.getCashTransfer();
//						if(cashTransferM != null && OrigConstant.cashDayType.CASH_NA.equals(cashTransferM.getCashTransferType())){
//							if(!smsUtil.sendSMSWithSMSCode(OrigConstant.EmailSMS.SMS_APPROVE_NON_CASH_DAY1, applicationM)){
//								logger.error("##### Cannot send Approve SMS NON_CASH_DAY1");
//							}
//						}
//					}
//				}
			}
		}catch(Exception e){
			logger.fatal("ERROR ",e);
			throw new PLOrigApplicationException(e.getLocalizedMessage());
		}
	}
		
	public Date getDateExtendWorkingDay(Date startCalDate, int extendDays){
		try{
			return PLORIGDAOFactory.getPLOrigAppUtilDAO().getDateExtendWorkingDay(startCalDate, extendDays);
		}catch (Exception e){
			logger.fatal("##### getDateExtendWorkingDay error:" + e.getMessage());
			e.printStackTrace();
			return null;
		}
	}
	
	public String getDateExtendWorkingDay(String appRecID){
		try{
			return PLORIGDAOFactory.getPLOrigAppUtilDAO().getDateExtendWorkingDay(appRecID);
		}catch (Exception e){
			logger.fatal("##### getDateExtendWorkingDay error:" + e.getMessage());
			e.printStackTrace();
			return null;
		}
	}
	
	public int getFollowUpSLA(String appId){
		try{
			return PLORIGDAOFactory.getPLOrigEmailSMSDAO().getFollowUpSLA(appId);
		}catch (Exception e){
			logger.fatal("##### getDateExtendWorkingDay error:" + e.getMessage());
			e.printStackTrace();
			return 0;
		}
	}
	
	public GeneralParamProperties getGeneralParamDetails(String paramCode){
		try{
			return PLORIGDAOFactory.getPLOrigEmailSMSDAO().getGeneralParamData(paramCode);
		}catch (Exception e){
			logger.fatal("##### getGeneralParamDetails error:" + e.getMessage());
			e.printStackTrace();
			return null;
		}
	}
	
	public int countOrigContractLog(String appRecordId, String templateName){
		try{
			return PLORIGDAOFactory.getPLOrigEmailSMSDAO().countOrigContactLog(appRecordId, templateName);
		}catch (Exception e){
			logger.fatal("##### createOrigContractLog error:" + e.getMessage());
			e.printStackTrace();
			return 0;
		}
	}
	public boolean LogicIgnoreSendEmail(PLApplicationDataM applicationM){
		if(OrigConstant.FLAG_C.equals(applicationM.getBlockFlag()) 
				&& OrigConstant.Action.CANCEL.equals(applicationM.getAppDecision())){
			return true;
		}
		return false;
	}
	public int getContactLogID(){
		try{
			return PLORIGDAOFactory.getPLOrigEmailSMSDAO().getContactLogID();
		}catch (Exception e){
			logger.fatal("##### getContactLogID error:" + e.getMessage());
			e.printStackTrace();
			return 0;
		}
	}
	
	public int getEmailSMSQCount(int contactLogID){
		try{
			return PLORIGDAOFactory.getPLOrigEmailSMSDAO().getEmailSMSQCount(contactLogID);
		}catch (Exception e){
			logger.fatal("##### getEmailSMSQCount error:" + e.getMessage());
			e.printStackTrace();
			return 0;
		}
	}
	
}
