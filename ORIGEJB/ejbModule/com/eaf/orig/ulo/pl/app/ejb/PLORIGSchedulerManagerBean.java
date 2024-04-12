package com.eaf.orig.ulo.pl.app.ejb;

import java.io.File;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Vector;

import javax.ejb.EJBException;
//import javax.ejb.Stateless;

//import org.apache.commons.net.ftp.FTPClient;
//import org.apache.commons.net.ftp.FTPReply;
import org.apache.log4j.Logger;

//import com.eaf.orig.cache.properties.GeneralParamProperties;
import com.eaf.orig.cache.properties.ORIGCacheDataM;
import com.eaf.orig.master.shared.model.GeneralParamM;
import com.eaf.orig.profile.model.UserDetailM;
import com.eaf.orig.shared.constant.OrigConstant;
import com.eaf.orig.shared.dao.OrigMasterDAOFactory;
import com.eaf.orig.shared.dao.OrigMasterGenParamDAO;
import com.eaf.orig.shared.dao.PLORIGDAOFactory;
//import com.eaf.orig.shared.ejb.ORIGContactManager;
//import com.eaf.orig.shared.model.EmailM;
//import com.eaf.orig.shared.model.EmailTemplateMasterM;
//import com.eaf.orig.shared.model.InfBackupLogM;
//import com.eaf.orig.shared.model.SMSDataM;
//import com.eaf.orig.shared.model.ServiceEmailSMSSchedulerQDataM;
import com.eaf.orig.shared.util.OrigUtil;
//import com.eaf.orig.shared.utility.ORIGCacheUtil;
import com.eaf.orig.ulo.pl.app.dao.ORIGManualImportDAO;
//import com.eaf.orig.ulo.pl.app.dao.PLORIGSchedulerDAO;
import com.eaf.orig.ulo.pl.app.dao.PLOrigApplicationDAO;
//import com.eaf.orig.ulo.pl.app.utility.CommissionUtility;
//import com.eaf.orig.ulo.pl.app.utility.DataFormatUtility;
//import com.eaf.orig.ulo.pl.app.utility.PLOrigEmailSMSUtil;
//import com.eaf.orig.ulo.pl.app.utility.PLOrigEmailUtil;
//import com.eaf.orig.ulo.pl.app.utility.PLOrigSMSUtil;
//import com.eaf.orig.ulo.pl.app.utility.PLOrigSchedulerUtil;
import com.eaf.orig.ulo.pl.app.utility.ServiceReqRespTool;
import com.eaf.orig.ulo.pl.constant.PLBusClassConstant;
import com.eaf.orig.ulo.pl.model.app.PLApplicationDataM;
import com.eaf.orig.ulo.pl.model.app.PLAttachmentHistoryDataM;
import com.eaf.orig.ulo.pl.model.app.PLImportCreditLineDataM;
import com.eaf.orig.ulo.pl.model.app.PLOrigContactDataM;
import com.eaf.orig.ulo.pl.model.app.PLReasonDataM;
import com.eaf.orig.ulo.pl.model.app.PLResponseImportDataM;
import com.eaf.orig.ulo.pl.service.PLORIGEJBService;

/**
 * Session Bean implementation class PLORIGSchedulerManagerBean
 */
//@Stateless
public class PLORIGSchedulerManagerBean implements PLORIGSchedulerManager {
	private Logger logger = Logger.getLogger(PLORIGSchedulerManagerBean.class);

	/**
	 * Default constructor.
	 */
	public PLORIGSchedulerManagerBean() {
		// TODO Auto-generated constructor stub
	}

	@Override
	public void processAutoRejectConfirmReject() {
		GeneralParamM pendingSLAParamM;
		try {
			OrigMasterGenParamDAO masterDAO = OrigMasterDAOFactory.getOrigMasterGenParamDAO();

			pendingSLAParamM = masterDAO.loadModelOrigMasterGenParamM(OrigConstant.ParamCode.CONFIRM_REJECT_SLA, PLBusClassConstant.BusClass.FCP_KEC_ALL);
			int confirmRejectSLA = Integer.parseInt(pendingSLAParamM.getParamValue());
			ArrayList<String> appRejectConfirmRejectArray = PLORIGDAOFactory.getPLORIGSchedulerDAO().loadAutoRejectConfirmReject(confirmRejectSLA);
			if (appRejectConfirmRejectArray.size() > 0) {
				UserDetailM userM = new UserDetailM();
				userM.setUserName(OrigConstant.UPDATE_BY_SCHEDULER);
				PLApplicationDataM applicationM;
				PLORIGApplicationManager manager = PLORIGEJBService.getPLORIGApplicationManager();
				PLOrigApplicationDAO origDAO = PLORIGDAOFactory.getPLOrigApplicationDAO();
				for(String appRecID : appRejectConfirmRejectArray){
					
					applicationM = origDAO.selectOrig_Appplication(appRecID);
						    origDAO.LoadAppInfo(applicationM,appRecID);
					
				    applicationM.setApplicationStatus(null);
					applicationM.setAppDecision(OrigConstant.Action.CONFIRM_REJECT);
					
					/**#septemwi modify set FinalAppDecision*/
					applicationM.setFinalAppDecision(OrigConstant.Action.REJECT);
					applicationM.setFinalAppDecisionBy(userM.getUserName());
					applicationM.setFinalAppDecisionDate(new Timestamp(new Date().getTime()));
										
					applicationM.setAppInfo(OrigUtil.getApplicatonXML(applicationM));
					
					logger.debug("@@@@@ process Auto Reject Confirm Reject for application_record_id :" + appRecID);
					
					manager.claimAndCompleteApplicationWithOutRole(applicationM, userM);
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
			throw new EJBException(e.getMessage());
		}
	}
	
	public void processAutoRejectFU() {
		try {
			ArrayList<String> appRejectFollowUpArray = PLORIGDAOFactory.getPLORIGSchedulerDAO().loadAutoRejectFollowUpIQ();
			if (appRejectFollowUpArray.size() > 0) {
				UserDetailM userM = new UserDetailM();
				userM.setUserName(OrigConstant.UPDATE_BY_SCHEDULER);
				PLApplicationDataM applicationM;
				PLORIGApplicationManager manager = PLORIGEJBService.getPLORIGApplicationManager();
				
				PLOrigApplicationDAO origDAO = PLORIGDAOFactory.getPLOrigApplicationDAO();
				
				for(String appRecID : appRejectFollowUpArray){
					
					applicationM = origDAO.selectOrig_Appplication(appRecID);					
						origDAO.LoadAppInfo(applicationM,appRecID);
						applicationM.setApplicationStatus(null);
					
					if(OrigConstant.FLAG_Y.equals(applicationM.getReopenFlag())){
						applicationM.setAppDecision(OrigConstant.Action.AUTO_REJECT_SCRIP_DF);
						//set final decision incase of skip DF
						applicationM.setFinalAppDecision(OrigConstant.Action.REJECT);
						applicationM.setFinalAppDecisionBy(userM.getUserName());
						applicationM.setFinalAppDecisionDate(new Timestamp(new Date().getTime()));
					}else{
						applicationM.setAppDecision(OrigConstant.Action.AUTO_REJECT);
					}
					
					logger.debug("@@@@@ process Auto Reject Follow Up for application_record_id :" + appRecID);
					
					PLReasonDataM reasonM = new PLReasonDataM();					
						reasonM.setApplicationRecordId(appRecID);
						reasonM.setReasonType(OrigConstant.fieldId.REJECT_REASON);
						reasonM.setReasonCode(OrigConstant.RejectReason.DOC_NOT_COMPLETE);
						reasonM.setRole(OrigConstant.ROLE_FU);
					
					Vector<PLReasonDataM> reasonVT = new Vector<PLReasonDataM>();
					reasonVT.add(reasonM);
					applicationM.setReasonVect(reasonVT);
					
					
					applicationM.setAppInfo(OrigUtil.getApplicatonXML(applicationM));
					
					manager.claimAndCompleteApplicationWithOutRole(applicationM, userM);
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
			throw new EJBException(e.getMessage());
		}
	}
	
	public void processAutoRejectVC() {
		try {
			OrigMasterGenParamDAO masterDAO = OrigMasterDAOFactory.getOrigMasterGenParamDAO();
			
			GeneralParamM pendingSLAParamM = masterDAO.loadModelOrigMasterGenParamM(OrigConstant.ParamCode.VERIFY_CUSTOMER_SLA, PLBusClassConstant.BusClass.FCP_KEC_ALL);
			int verifyCustomerSLA = Integer.parseInt(pendingSLAParamM.getParamValue());
			ArrayList<String> appRejectVerifyCustomerArray = PLORIGDAOFactory.getPLORIGSchedulerDAO().loadAutoRejectVC(verifyCustomerSLA);
			PLOrigApplicationDAO origDAO = PLORIGDAOFactory.getPLOrigApplicationDAO();
			if (appRejectVerifyCustomerArray.size() > 0) {
				UserDetailM userM = new UserDetailM();
				
				userM.setUserName(OrigConstant.UPDATE_BY_SCHEDULER);				
				PLApplicationDataM applicationM;
				
				PLORIGApplicationManager manager = PLORIGEJBService.getPLORIGApplicationManager();
				for (String appRecID : appRejectVerifyCustomerArray) {
					applicationM = origDAO.selectOrig_Appplication(appRecID);
					origDAO.LoadAppInfo(applicationM,appRecID);
					
					applicationM.setApplicationStatus(null);
					applicationM.setAppDecision(OrigConstant.Action.REJECT);
										
					applicationM.setAppInfo(OrigUtil.getApplicatonXML(applicationM));
					
					logger.debug("@@@@@ process Auto Reject VC for application_record_id :" + appRecID);
					manager.claimAndCompleteApplicationWithOutRole(applicationM, userM);
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
			throw new EJBException(e.getMessage());
		}
	}

	@Override
	public void processAutoIncreaseDecrease(String attachmentId) {
//		// TODO Auto-generated method stub
//		Vector<PLAttachmentHistoryDataM> oldAttachVT = null;
//		PLAttachmentHistoryDataM attachM = null;
//		String message = null;
//		PLOrigEmailUtil emailUtil = new PLOrigEmailUtil();
//		PLOrigSchedulerUtil schedUtil = new PLOrigSchedulerUtil();
//		ORIGCacheDataM cacheM = ORIGCacheUtil.getInstance().GetListboxCacheDataM(OrigConstant.fieldId.INTERFACE_TYPE, OrigConstant.BusClass.FCP_ALL_ALL,
//				OrigConstant.InterfaceType.INCREASE_CREDIT_LINE);
//		int backupLimit = 0;
//		if (cacheM.getSystemID10() != null && !"".equals(cacheM.getSystemID10())) {
//			backupLimit = Integer.parseInt(cacheM.getSystemID10());
//		}
//		try {
//			// find attachment data that create date less than current date - day from configuration for delete file from disk
//			oldAttachVT = PLORIGDAOFactory.getPLOrigAttachmentHistoryMDAO().loadModelOrigAttachmentHistory(backupLimit);
//			if (oldAttachVT != null && oldAttachVT.size() > 0) {
//				for (int i = 0; i < oldAttachVT.size(); i++) {
//					PLAttachmentHistoryDataM oldAttachM = (PLAttachmentHistoryDataM) oldAttachVT.get(i);
//					schedUtil.removeFileAceptAttachment(oldAttachM.getFilePath(), oldAttachM.getFileName());
//				}
//			}
//			// find current attachment data for process
//			attachM = PLORIGDAOFactory.getPLOrigAttachmentHistoryMDAO().loadModelOrigAttachmentHistoryMFromAttachId(attachmentId);
//
//			if (attachM != null) {
//				String creditLineFileName = null;
//				File directory = new File(attachM.getFilePath());
//				// find credit line file for process data
//				if (directory.isDirectory()) {
//					if (directory.list().length > 0) {
//						// list all the directory contents
//						String files[] = directory.list();
//						for (String temp : files) {
//							if (temp.indexOf(cacheM.getSystemID8()) >= 0) { // System ID8 = Import file name
//								creditLineFileName = temp; // Get file name that include file type
//								break;
//							}
//						}
//					}
//				}
//				// if found import file name, load excel data for process auto increase/decrease data
//				if (creditLineFileName != null) {
//					// create session id
//					String sessionId = String.valueOf(Math.round(Math.random() * Math.pow(10, 16))) + String.valueOf(schedUtil.getSysTimeStamp().getTime());
//					UserDetailM userM = new UserDetailM();
//					userM.setUserName("Scheduler");
//					// load data from excel file to model
//					Vector<PLImportCreditLineDataM> importCreditLineVect = schedUtil.loadXLSToModel(creditLineFileName, attachM.getFilePath(), sessionId);
//					if (importCreditLineVect != null && importCreditLineVect.size() > 0) {
//						// create table master
//						PLORIGDAOFactory.getPLOrigImportCreditLineDataDAO().saveTable_ORIG_CREDIT_LINE_IMPORT_MASTER(sessionId, attachM.getAttachId(), userM);
//						// create table details
//						PLORIGDAOFactory.getPLOrigImportCreditLineDataDAO().saveTable_ORIG_CREDIT_LINE_IMPORT_DETAIL(importCreditLineVect);
//						// process pl/sql auto increase/decrease
//						PLORIGDAOFactory.getPLOrigImportCreditLineDataDAO().processAutoIncreaseDecrease(sessionId);
//						// load data for send email to user
//						PLResponseImportDataM importResponseM = PLORIGDAOFactory.getPLOrigImportCreditLineDataDAO().loadResultAutoIncreaseDecrease(sessionId);
//						if (importResponseM != null) {
//							logger.debug("send response email");
//							String rejectFileDir = null;
//							// create reject file, if found reject record
//							if (importResponseM.getErrorImportVt() != null && importResponseM.getErrorImportVt().size() > 0) {
//								rejectFileDir = schedUtil.createRejectFile(importResponseM.getErrorImportVt(), attachM.getFilePath());
//							}
//							// create message
//							message = schedUtil.createMessageCreditLine(importResponseM);
//							// send email to user with attach file
//							emailUtil.sendEmailAutoICDCToUser(attachM.getUpdateBy(), attachM.getAttachId(), message, rejectFileDir);
//						}
//					} else {
//						logger.debug("@@@@@ Not found data from credit_inport.*");
//					}
//				} else {
//					logger.debug("@@@@@ Not found import file name");
//				}
//			} else {
//				logger.debug("@@@@@ Not found data for proccess auto increase/decrease");
//			}
//		} catch (Exception e) {
//			e.printStackTrace();
//			message = "Error occur, the system cannot import credit line from current credit line file.";
//			try {
//				// send error email to user
//				emailUtil.sendEmailAutoICDCToUser(attachM.getCreateBy(), attachM.getAttachId(), message, null);
//			} catch (Exception ex) {
//				ex.printStackTrace();
//			}
//			throw new EJBException(e.getMessage());
//		}
	}

	public void processAutoSendEmail() {
//		try {
//			PLOrigEmailUtil emailUtil = new PLOrigEmailUtil();
//
//			// Auto send Email FU
//
//			ArrayList<String> appAutoSendEmailFU = PLORIGDAOFactory.getPLORIGSchedulerDAO().loadAutoSendEmailFollowUp();
//			if (appAutoSendEmailFU.size() > 0) {
//				for (String appId : appAutoSendEmailFU) {
//					logger.debug("@@@@@ process Auto send followup for application_record_id :" + appId);
//					if (!emailUtil.sendEmailFollowUpAuto(appId)) {
//						logger.error("##### Cannot send Follow Up Auto Email for application record id :" + appId);
//					}
//				}
//			}
//		} catch (Exception e) {
//			e.printStackTrace();
//			throw new EJBException(e.getMessage());
//		}
	}

	@Override
	public void processImportInterface(ORIGCacheDataM cacheM, UserDetailM userM) throws Exception {
//		logger.info("@@@@@ Begin processImportInterface");
//		//boolean success = false;
//		PLOrigSchedulerUtil schedUtil = new PLOrigSchedulerUtil();
//		String moduleId = cacheM.getSystemID13(); //Interface module ID
//		String filePath = cacheM.getSystemID11(); //Interface file path
//		String importFileName = cacheM.getSystemID8();
//		//String needFTP = cacheM.getSystemID12();
//		String fileShortName = getFileName(filePath, importFileName);
//		String fileFullName = filePath + File.separator + fileShortName;
//		int backupLimit = 0;
//		if (cacheM.getSystemID10() != null && !"".equals(cacheM.getSystemID10())) {
//			backupLimit = Integer.parseInt(cacheM.getSystemID10());
//		}
//		try {
//			logger.info("@@@@@ create BLOP from :" + fileFullName);
//			PLORIGDAOFactory.getORIGManualImportDAO().createBLobInfImport(fileFullName, moduleId, userM.getUserName()); //create BLOP
//			
//			logger.info("@@@@@ delete file :" + fileFullName);
//			schedUtil.deleteFile(fileFullName);
//			
//			ORIGContactManager contactManager = PLORIGEJBService.getORIGContactManager();
//			InfBackupLogM infBackupM = new InfBackupLogM();
//			infBackupM.setModuleId(cacheM.getListBoxID());
//			infBackupM.setBackupPath(filePath + File.separator + "backup" + File.separator + DataFormatUtility.dateToStringYYYYMMDD(new Date()));
//			logger.info("@@@@@ insert interface backup log");
//			contactManager.createInterfaceBackupLog(infBackupM, userM.getUserName());
//			Vector<InfBackupLogM> infBackupLogVT = PLORIGDAOFactory.getORIGManualImportDAO().loadInterfaceBackupLogOverTime(cacheM.getListBoxID(),backupLimit);
//			if (infBackupLogVT != null && infBackupLogVT.size() > 0) {
//				logger.info("@@@@@ delete backup file :" + infBackupLogVT.size());
//				for (int i = 0; i < infBackupLogVT.size(); i++) {
//					InfBackupLogM infBackM = (InfBackupLogM) infBackupLogVT.get(i);
//					schedUtil.deleteFile(infBackM.getBackupPath());
//					contactManager.deleteInterfaceBackupLog(infBackM.getInfBackupId());
//				}
//				logger.info("@@@@@ delete backup file success");
//			}
//			logger.info("@@@@@ End processImportInterface");
//		} catch (Exception e) {
//			logger.fatal("##### processImportInterface:" + e.getMessage());
//			e.printStackTrace();
//			throw new EJBException(e.getMessage());
//		}
	}

	private String getFileName(String filePath, String startWith) {
		String fileFullName = null;
		try {
			File directory = new File(filePath);
			if (directory.isDirectory()) {
				if (directory.list().length > 0) {
					// list all the directory contents
					String files[] = directory.list();
					for (String fileName : files) {
						if (fileName.indexOf(startWith) >= 0) {
							fileFullName = fileName;
						}
					}
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return fileFullName;
	}
//
//	private boolean ftpTransfer(String localfile, String destinationPath, String destinationFile) {
//		String server = ORIGConfig.FTP_DB_HOST;
//		String username = ORIGConfig.FTP_DB_USER;
//		String password = ORIGConfig.FTP_DB_PASSWORD;
//		try {
//			FTPClient ftp = new FTPClient();
//			ftp.connect(server);
//			if (!ftp.login(username, password)) {
//				logger.debug("##### Cannot login FTP to:" + server);
//				ftp.logout();
//				return false;
//			}
//			int reply = ftp.getReplyCode();
//			if (!FTPReply.isPositiveCompletion(reply)) {
//				logger.debug("##### FTP Reply Code not positive:" + reply);
//				ftp.disconnect();
//				return false;
//			}
//			InputStream in = new FileInputStream(new File(localfile));
//			ftp.setFileType(ftp.BINARY_FILE_TYPE);
//			ftp.changeWorkingDirectory(destinationPath);
//			boolean Store = ftp.storeFile(destinationFile, in);
//			logger.debug("@@@@@ Store:" + Store);
//			in.close();
//			ftp.logout();
//			ftp.disconnect();
//		} catch (Exception ex) {
//			ex.printStackTrace();
//			return false;
//		}
//		return true;
//	}

	@Override
	public void processSendEmailSMSScheduler(String serviceType) {
//		PLOrigEmailUtil emailUtil = null;
//		PLOrigSMSUtil smsUtil = null;
//		PLOrigEmailSMSUtil emailSMSUtil = new PLOrigEmailSMSUtil();
//		if (OrigConstant.EmailSMS.CONTACT_TYPE_EMAIL.equals(serviceType)) {
//			emailUtil = new PLOrigEmailUtil();
//		} else if (OrigConstant.EmailSMS.CONTACT_TYPE_SMS.equals(serviceType)) {
//			smsUtil = new PLOrigSMSUtil();
//		}
//		try {
//			ORIGContactManager contactManager = PLORIGEJBService.getORIGContactManager();
//			PLORIGSchedulerDAO origSchedulerDao = PLORIGDAOFactory.getPLORIGSchedulerDAO();
//			ServiceReqRespTool serviceTool = new ServiceReqRespTool();
//			Vector<ServiceEmailSMSSchedulerQDataM> serviceSchedulerQVt = origSchedulerDao.loadEmailSMSSchedulerQueue(serviceType);
//			if (serviceSchedulerQVt != null && serviceSchedulerQVt.size() > 0) {
//				for (int i = 0; i < serviceSchedulerQVt.size(); i++) {
//					ServiceEmailSMSSchedulerQDataM serviceSchedulerQM = (ServiceEmailSMSSchedulerQDataM) serviceSchedulerQVt.get(i);
//					PLOrigContactDataM contactM = new PLOrigContactDataM();
//					contactM.setContactLogId(emailSMSUtil.getContactLogID()); // generate
//																				// contact
//																				// log
//																				// id
//					contactM.setContactType(serviceType);
//					contactM.setApplicationRecordId(serviceSchedulerQM.getAppRecordId());
//					contactM.setCreateBy(OrigConstant.UPDATE_BY_SCHEDULER);
//
//					if (OrigConstant.EmailSMS.CONTACT_TYPE_EMAIL.equals(serviceType)) {
//						EmailM emailM = new EmailM();
//						emailM.setFrom(serviceSchedulerQM.getSendFrom());
//						emailM.setFromName(serviceSchedulerQM.getSendFromName());
//						emailM.setTo(serviceSchedulerQM.getSendTo());
//						emailM.setSubject(serviceSchedulerQM.getSubject());
//						emailM.setContent(serviceSchedulerQM.getContent());
//						contactM.setEmail(emailM);
//						emailUtil.sendEmail(contactM);
//					} else if (OrigConstant.EmailSMS.CONTACT_TYPE_SMS.equals(serviceType)) {
//						SMSDataM smsM = new SMSDataM();
//						smsM.setNumber(serviceSchedulerQM.getSendTo());
//						smsM.setCtype(OrigConstant.EmailSMS.SMS_CTYPE);
//						smsM.setContent(serviceSchedulerQM.getContent());
//						contactM.setSms(smsM);
//						contactM.setTransactionId(serviceTool.GenerateTransectionId());
//						smsUtil.sendSMS(contactM);
//					}
//					serviceSchedulerQM.setContactLogId(contactM.getContactLogId());
//					contactManager.updateStatusEmailSMSSchedulerQ(serviceSchedulerQM);
//				}
//			}
//		} catch (Exception e) {
//			e.printStackTrace();
//			throw new EJBException(e.getMessage());
//		}
	}
	public void processAutoCompleteJob() {
		try {
			ArrayList<String> appRejectVerifyCustomerArray = PLORIGDAOFactory.getPLORIGSchedulerDAO().loadAutoCompleteJob();
			if (appRejectVerifyCustomerArray.size() > 0) {
				UserDetailM userM = new UserDetailM();
				userM.setUserName("Scheduler");
				PLApplicationDataM appM;
				PLORIGApplicationManager manager = PLORIGEJBService.getPLORIGApplicationManager();
				for (String appId : appRejectVerifyCustomerArray) {
					appM = new PLApplicationDataM();
					appM.setAppRecordID(appId);
					appM.setAppDecision(OrigConstant.Action.GEN_CARDLINK);
					logger.debug("@@@@@ process Auto Complete Job for application_record_id :" + appId);
					manager.claimAndCompleteApplicationWithOutRole(appM, userM);
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
			throw new EJBException(e.getMessage());
		}
	}

	@Override
	public void processCalculateCommision(java.util.Date calDate) {
//		logger.debug("CAll processCalculateCommision");
//		try {
//			CommissionUtility.callCulateComission(calDate);
//		} catch (Exception e) {
//			e.printStackTrace();
//			throw new EJBException(e.getMessage());			
//		}			
//		logger.debug("End processCalculateCommision");
	}

	public void processWarnningCardNo() {
//		try {
//			GeneralParamProperties warnigCardProp = ORIGCacheUtil.getInstance().loadGeneralParamCacheGroup(OrigConstant.GeneralParamCode.WARNING_CARD_NO,
//					OrigConstant.BusClass.FCP_KEC_NM);
//			String warningAmount = "1000"; // default value
//			String paramIdMain = "";
//			String paramIdTemp = OrigConstant.WarningCard.TEMP_PARAM_ID;
//			if (warnigCardProp != null){
//				warningAmount = warnigCardProp.getParamvalue()==null?"0":warnigCardProp.getParamvalue(); // warning amount
//				paramIdMain = warnigCardProp.getParamvalue2(); // param id for check warning
//			}
//			logger.debug("@@@@@ warningAmount:" + warningAmount);
//			HashMap<String, Integer> currentLenght = PLORIGDAOFactory.getPLORIGSchedulerDAO().loadWarningCardNo(paramIdMain, paramIdTemp);
//			int currentMainAmount = (int) currentLenght.get(OrigConstant.WarningCard.KEY_MAIN);
//			int currentTempAmount = (int) currentLenght.get(OrigConstant.WarningCard.KEY_TEMP);
//			logger.debug("@@@@@ currentMainAmount:" + currentMainAmount);
//			logger.debug("@@@@@ currentTempAmount:" + currentTempAmount);
//			if ((currentMainAmount+currentTempAmount) <= Integer.parseInt(warningAmount)) {
//				GeneralParamProperties warningEmailProp = ORIGCacheUtil.getInstance().loadGeneralParamCacheGroup(
//						OrigConstant.GeneralParamCode.WARNING_CARD_NO_EMAIL, OrigConstant.BusGroup.FCP_KEC_ALL);
//				if (warningEmailProp != null && warningEmailProp.getParamvalue() != null && !"".equals(warningEmailProp.getParamvalue())) {
//					PLOrigEmailSMSUtil emailSMSUtil = new PLOrigEmailSMSUtil();
//					PLOrigEmailUtil emailUtil = new PLOrigEmailUtil();
//					PLOrigContactDataM contactM = new PLOrigContactDataM();
//					
//					GeneralParamProperties poParamM = emailUtil.getPOEmail();
//					contactM.setContactLogId(emailSMSUtil.getContactLogID()); // generate contact log id
//					contactM.setContactType(OrigConstant.EmailSMS.CONTACT_TYPE_EMAIL);
//					EmailM emailM = new EmailM();
//					emailM.setFrom(poParamM.getParamvalue());
//					emailM.setFromName(poParamM.getParamvalue2());
//					emailM.setTo(warningEmailProp.getParamvalue());
//
//					EmailTemplateMasterM emailTemplateM = PLORIGDAOFactory.getPLOrigEmailSMSDAO().getEMailTemplateMaster(
//							OrigConstant.EmailSMS.EMAIL_WARNING_CARD_NO, OrigConstant.BusClass.FCP_KEC_NM);
//					if (emailTemplateM == null)
//						emailTemplateM = new EmailTemplateMasterM();
//
//					emailM.setSubject(emailTemplateM.getSubject());
//					emailM.setContent(emailTemplateM.getContent().replaceAll("CURRENT", String.valueOf(currentMainAmount+currentTempAmount)));
//					contactM.setEmail(emailM);
//					emailUtil.sendEmail(contactM);
//				}
//			}
//		} catch (Exception e) {
//			e.printStackTrace();
//			throw new EJBException(e.getMessage());
//		}
	}

	@Override
	public void processCalculateCommisionPeriod(String period) {
//		logger.debug("CAll processCalculateCommision Period");
//		try {
//			CommissionUtility.callCulateComissionPeriod(  period);
//		} catch (Exception e) {
//			e.printStackTrace();
//			throw new EJBException(e.getMessage());			
//		}			
//		logger.debug("End processCalculateCommision Period");
		
	}

	@Override
	public void clearDataDeleteImportInterface(ORIGCacheDataM cacheM, String userName) {
		try{
			ORIGManualImportDAO manualImportDao = PLORIGDAOFactory.getORIGManualImportDAO();
			manualImportDao.clearBLobInfImport(cacheM.getSystemID13(), userName);
			manualImportDao.deleteInterfaceBackupLog(cacheM);
		}catch (Exception e) {
			e.printStackTrace();
			throw new EJBException(e.getMessage());			
		}
	}

}
