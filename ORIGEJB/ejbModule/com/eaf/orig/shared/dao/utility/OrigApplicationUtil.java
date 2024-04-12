/*
 * Created on Oct 12, 2007
 * Created by Sankom Sanpunya
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
package com.eaf.orig.shared.dao.utility;

//import java.io.File;
import java.math.BigDecimal;
import java.sql.Connection;
import java.sql.Timestamp;
import java.util.Date;
import java.util.Vector;
import org.apache.log4j.Logger;
import com.eaf.orig.profile.model.UserDetailM;
import com.eaf.orig.shared.constant.OrigConstant;
import com.eaf.orig.shared.model.ProcessMenuM;
import com.eaf.orig.shared.service.OrigServiceLocator;
import com.eaf.orig.shared.util.OrigUtil;
//import com.eaf.orig.ulo.pl.app.utility.AttachmentUtility;
import com.eaf.orig.ulo.pl.model.app.PLApplicationDataM;
import com.eaf.orig.ulo.pl.model.app.PLAttachmentHistoryDataM;
import com.eaf.orig.ulo.pl.model.app.PLPersonalInfoDataM;
import com.eaf.orig.ulo.pl.model.app.PLReasonDataM;
import com.eaf.orig.ulo.pl.model.app.RulesDetailsDataM;
import com.eaf.orig.ulo.pl.service.PLORIGEJBService;
import com.eaf.xrules.shared.constant.PLXrulesConstant;
import com.eaf.xrules.ulo.pl.model.app.rule.PLXRulesVerificationResultDataM;
import com.orig.bpm.proxy.BpmProxyService;
import com.orig.bpm.ulo.proxy.BpmWorkflow;

/**
 * @author Sankom Sanpunya
 * 
 *         Type: OrigApplicationUtil
 */
public class OrigApplicationUtil {
	Logger logger = Logger.getLogger(this.getClass().getName());
	static OrigApplicationUtil me;

	/**
	 * 
	 */
	public OrigApplicationUtil() {
		super();
	}

	public static OrigApplicationUtil getInstance() {
		if (me == null) {
			me = new OrigApplicationUtil();
		}
		return me;
	}

	public BigDecimal getUserLendingLimit(String userName, String busClass) {
		try {
			// return
			// PLORIGDAOFactory.getPLOrigUserDAO().getUserLendingLimit(userName,
			// busClass);
			return PLORIGEJBService.getORIGDAOUtilLocal().getUserLendingLimit(userName, busClass);
		} catch (Exception e) {
			logger.fatal("##### getUserLendingLimit error:" + e.getMessage());
			e.printStackTrace();
			return new BigDecimal(0);
		}
	}

	public Date getDateExtendWorkingDay(Date startCalDate, int extendDays) {
		try {
			// return
			// PLORIGDAOFactory.getPLOrigAppUtilDAO().getDateExtendWorkingDay(startCalDate,
			// extendDays);
			return PLORIGEJBService.getORIGDAOUtilLocal().getDateExtendWorkingDay(startCalDate, extendDays);
		} catch (Exception e) {
			logger.fatal("##### getDateExtendWorkingDay error:" + e.getMessage());
			e.printStackTrace();
			return null;
		}
	}

	public String getDefaultCycleCut(String businessClass) {
		try {
			return PLORIGEJBService.getORIGDAOUtilLocal().getDefaultCycleCut(businessClass);
		} catch (Exception e) {
			logger.fatal("##### getDateExtendWorkingDay error:" + e.getMessage());
			e.printStackTrace();
			return null;
		}
	}

	public Vector<PLReasonDataM> generateRejectReasonFromRulesResult(PLApplicationDataM applicationM, UserDetailM userM) {
		ORIGRuleDetailsUtil ruleUtil = ORIGRuleDetailsUtil.getInstance();
		Vector<RulesDetailsDataM> ruleDetailVT = ruleUtil.getRulesDetailsConfig(applicationM.getBusinessClassId());
		Vector<PLReasonDataM> resultReasonVT = applicationM.getReasonVect();
		if (null == resultReasonVT) {
			resultReasonVT = new Vector<PLReasonDataM>();
		}
		if (ruleDetailVT != null && ruleDetailVT.size() > 0){
			for (int i = 0; i < ruleDetailVT.size(); i++) {
				RulesDetailsDataM ruleM = ruleDetailVT.get(i);
				if(ruleM != null){
					String ruleResult = ruleUtil.getRuleDetatisResultCode(applicationM, ruleM.getPolicyRuleID());
					if (PLXrulesConstant.ResultCode.CODE_FAIL.equals(ruleResult)) {
						PLReasonDataM reasonM = new PLReasonDataM();
						reasonM.setApplicationRecordId(applicationM.getAppRecordID());
						reasonM.setCreateBy(userM.getUserName());
						reasonM.setRole(userM.getCurrentRole());
						reasonM.setReasonCode(ruleM.getRejectReasonCode());
						reasonM.setReasonType(OrigConstant.fieldId.REJECT_REASON);
						resultReasonVT.add(reasonM);
					}
				}
			}
		}
		return resultReasonVT;
	}

	public Vector<PLAttachmentHistoryDataM> findCreditLineFile() {
//		String filePath = AttachmentUtility.getInstance().getImportCreditFilePath();
//		try {
//			File directory = new File(filePath);
//			if (directory.isDirectory()) {
//				Vector<PLAttachmentHistoryDataM> attachVT = new Vector<PLAttachmentHistoryDataM>();
//				// directory is empty, then delete it
//				if (directory.list().length > 0) {
//					// list all the directory contents
//					String files[] = directory.list();
//					for (String temp : files) {
//						// construct the file structure
//						// File fileFound = new File(directory, temp);
//						PLAttachmentHistoryDataM attachM = new PLAttachmentHistoryDataM();
//						attachM.setFileName(temp);
//						attachVT.add(attachM);
//					}
//					if (attachVT.size() == 2) {
//						return attachVT;
//					} else {
//						return null;
//					}
//				}
//			} else {
//				return null;
//			}
//			return null;
//		} catch (Exception e) {
//			return null;
//		}
		return null;
	}

	public boolean isCreditLimitAuthorize(String userId, String tdID, double finalCreditLine, String jobType) {
		BpmWorkflow bpmWorkflow = BpmProxyService.getBpmWorkflow();
		try {
			return bpmWorkflow.isCreditLimitAuthorize(userId, tdID, finalCreditLine, jobType, getWorkFlowConnection());
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}
	}

	public boolean isOverrideAuthorize(String userId, String tdID, PLApplicationDataM appM) {
		BpmWorkflow bpmWorkflow = BpmProxyService.getBpmWorkflow();
		String ruleIds = "";
		int cnt = 0;

		ORIGRuleDetailsUtil ruleUtil = ORIGRuleDetailsUtil.getInstance();
		Vector<RulesDetailsDataM> ruleDetailVT = ruleUtil.getRulesDetailsConfig(appM.getBusinessClassId());
		if (ruleDetailVT != null && ruleDetailVT.size() > 0) {
			for (int i = 0; i < ruleDetailVT.size(); i++) {
				RulesDetailsDataM ruleM = ruleDetailVT.get(i);
				if (ruleM != null) {
					String ruleResult = ruleUtil.getRuleDetatisResultCode(appM, ruleM.getPolicyRuleID());
					if(PLXrulesConstant.ResultCode.CODE_OVERRIDE.equals(ruleResult)){
						if(cnt ==0){
							ruleIds = ruleM.getPolicyRuleID();
						} else {
							ruleIds = ruleIds + "|" + ruleM.getPolicyRuleID();
						}
					}
				}
			}
		}
		if (!"".equals(ruleIds)) {
			try {
				return bpmWorkflow.isOverrideAuthorize(userId, tdID, ruleIds, getWorkFlowConnection());
			} catch (Exception e) {
				e.printStackTrace();
				return false;
			}
		} else {
			return false;
		}
	}

	public PLApplicationDataM defaultCaDecision(UserDetailM userM, ProcessMenuM menuM, PLApplicationDataM applicationM) {
		PLPersonalInfoDataM personalInfoM = applicationM.getPLPersonalInfoDataM(OrigConstant.PERSONAL_TYPE_APPLICANT);
		PLXRulesVerificationResultDataM plXrulesVerM = personalInfoM.getXrulesVerification();
		if (plXrulesVerM == null) {
			plXrulesVerM = new PLXRulesVerificationResultDataM();
			personalInfoM.setXrulesVerification(plXrulesVerM);
		}
		
//		logger.debug("@@@@@ application no:" + applicationM.getApplicationNo());
//		logger.debug("@@@@@ final credit line:" + applicationM.getFinalCreditLimit());
//		logger.debug("@@@@@ plXrulesVerM.getRecommendResult():" + plXrulesVerM.getRecommendResult());
//		logger.debug("@@@@@ menuM.getMenuID():" + menuM.getMenuID());
//		logger.debug("@@@@@ applicationM.getJobType():" + applicationM.getJobType());

		double creditLine = 0;
		double recommendCreditLine = 0;
		if (applicationM.getFinalCreditLimit() != null)
			creditLine = applicationM.getFinalCreditLimit().doubleValue();
		
//		logger.debug("@@@@@ creditLine:" + creditLine);
		
		if (applicationM.getRecommentCreditLine() != null)
			recommendCreditLine = applicationM.getRecommentCreditLine().doubleValue();
		if (applicationM.getPolicyExcDocNo() != null && !"".equals(applicationM.getPolicyExcDocNo())) {
			applicationM.setCaDecision(OrigConstant.Action.POLICY_EXCEPTION);
			if(!OrigApplicationUtil.getInstance().isCreditLimitAuthorize(userM.getUserName(), menuM.getMenuID(), creditLine, OrigConstant.typeColor.typeGreen)){
				applicationM.setCaDecision(OrigConstant.Action.ESCALATE);
			}
		} else if (OrigConstant.recommendResult.REJECT.equals(plXrulesVerM.getRecommendResult())) {
			applicationM.setCaDecision(OrigConstant.Action.REJECT);
		} else if (OrigConstant.documentResult.INCOMPLETE.equals(applicationM.getDocListResult())) {
			applicationM.setCaDecision(OrigConstant.Action.REQUEST_FU);
		} else if (OrigConstant.recommendResult.ACCEPT.equals(plXrulesVerM.getRecommendResult())) {
			if (!isCreditLimitAuthorize(userM.getUserName(), menuM.getMenuID(), creditLine, applicationM.getJobType())) {
				applicationM.setCaDecision(OrigConstant.Action.ESCALATE);
			} else if (creditLine <= recommendCreditLine) {
				applicationM.setCaDecision(OrigConstant.Action.APPROVE);
			}
		} else if (OrigConstant.recommendResult.OVERIDE.equals(plXrulesVerM.getRecommendResult())) {
			if (!isOverrideAuthorize(userM.getUserName(), menuM.getMenuID(), applicationM) ||
					!isCreditLimitAuthorize(userM.getUserName(), menuM.getMenuID(), creditLine, applicationM.getJobType())) {
				applicationM.setCaDecision(OrigConstant.Action.ESCALATE);
			} else if (creditLine <= recommendCreditLine) {
				applicationM.setCaDecision(OrigConstant.Action.OVERRIDE);
			}
		}
		return applicationM;
	}

	private Connection getWorkFlowConnection() {
		try {
			OrigServiceLocator origService = OrigServiceLocator.getInstance();
			return origService.getConnection(OrigServiceLocator.WORKFLOW_DB);
		} catch (Exception e) {
			logger.fatal("Connection is null" + e.getMessage());
		}
		return null;
	}

	public String getJobTypeByPolicy(PLApplicationDataM appM) {
		String ruleIds = "";
		int cnt = 0;
		String jobType = "";
		ORIGRuleDetailsUtil ruleUtil = ORIGRuleDetailsUtil.getInstance();
		Vector<RulesDetailsDataM> ruleDetailVT = ruleUtil.getRulesDetailsConfig(appM.getBusinessClassId());

		if (ruleDetailVT != null && ruleDetailVT.size() > 0) {
			logger.debug("ruleDetailVT.size()=" + ruleDetailVT.size());
			boolean haveRulefail = false;
			for (int i = 0; i < ruleDetailVT.size(); i++) {
				RulesDetailsDataM ruleM = ruleDetailVT.get(i);
				if (ruleM != null) {
					String ruleResult = ruleUtil.getRuleDetatisResultCode(appM, ruleM.getPolicyRuleID());
					logger.debug("Policy id=" + ruleM.getPolicyRuleID() + "   result=" + ruleResult);
					if (PLXrulesConstant.ResultCode.CODE_OVERRIDE.equals(ruleResult)) {
						if (cnt == 0) {
							ruleIds = "'" + ruleM.getPolicyRuleID();
						} else {
							ruleIds = ruleIds + "','" + ruleM.getPolicyRuleID();
						}
						haveRulefail = true;
					}
				}
			}
			ruleIds = ruleIds + "'";
			logger.debug("ruleIds=" + ruleIds);
			// Rule Id param Parameter
			if (haveRulefail) {
				//20121213 Sankom add parameter for CR 
				//jobType = PLORIGEJBService.getORIGDAOUtilLocal().getJobTypeByPolicy(ruleIds);
				jobType = PLORIGEJBService.getORIGDAOUtilLocal().getJobTypeByPolicy(ruleIds,appM.getRecommentCreditLine());
			}
		}
		return jobType;
	}
	
	public void setFinalAppDecision(PLApplicationDataM applicationM, UserDetailM userM) {
		
		if(!OrigUtil.isEmptyString(applicationM.getAppDecision())){
			if (OrigConstant.Action.APPROVE.equals(applicationM.getAppDecision())
				 || OrigConstant.Action.APPROVE_SKIP_DF.equals(applicationM.getAppDecision())
					|| OrigConstant.Action.OVERRIDE.equals(applicationM.getAppDecision())
						|| OrigConstant.Action.OVERRIDE_SKIP_DF.equals(applicationM.getAppDecision())
							|| OrigConstant.Action.REJECT.equals(applicationM.getAppDecision())
								|| OrigConstant.Action.REJECT_SKIP_DF.equals(applicationM.getAppDecision())
									|| OrigConstant.Action.CONFIRM_REJECT.equals(applicationM.getAppDecision())
										|| OrigConstant.Action.CANCEL.equals(applicationM.getAppDecision())
									 		|| OrigConstant.Action.REOPEN_WITH_CANCEL.equals(applicationM.getAppDecision())
									 			|| OrigConstant.Action.REOPEN_CONFIRM_REJECT.equals(applicationM.getAppDecision())
									 				|| OrigConstant.Action.POLICY_EXCEPTION.equals(applicationM.getAppDecision())
									 					|| OrigConstant.Action.POLICY_EXCEPTION_SKIP_DF.equals(applicationM.getAppDecision())) {
				//if not CA reject no need to set final app decision
				if(OrigConstant.Action.REJECT.equals(applicationM.getAppDecision()) && !OrigConstant.ROLE_CA.equals((userM.getCurrentRole()))) {
					return;
				}
				String action = applicationM.getAppDecision();
				if (OrigConstant.Action.CONFIRM_REJECT.equals(applicationM.getAppDecision()) 
						|| OrigConstant.Action.REOPEN_CONFIRM_REJECT.equals(applicationM.getAppDecision())
						|| OrigConstant.Action.REJECT_SKIP_DF.equals(applicationM.getAppDecision())) {
					action = OrigConstant.Action.REJECT;
				}else if (OrigConstant.Action.APPROVE_SKIP_DF.equals(applicationM.getAppDecision())) {
					action = OrigConstant.Action.APPROVE;
				}else if (OrigConstant.Action.OVERRIDE_SKIP_DF.equals(applicationM.getAppDecision())) {
					action = OrigConstant.Action.OVERRIDE;
				}else if (OrigConstant.Action.POLICY_EXCEPTION_SKIP_DF.equals(applicationM.getAppDecision())) {
					action = OrigConstant.Action.POLICY_EXCEPTION;
				}else if (OrigConstant.Action.REOPEN_WITH_CANCEL.equals(applicationM.getAppDecision())){
					action = OrigConstant.Action.CANCEL;
				}
				
				applicationM.setFinalAppDecision(action);
				applicationM.setFinalAppDecisionBy(userM.getUserName());
				applicationM.setFinalAppDecisionDate(new Timestamp(new java.util.Date().getTime()));

			}else if(OrigConstant.Action.CANCEL_APPLICATION_AFTER_CARDLINK.equals(applicationM.getAppDecision())){
				applicationM.setFinalAppDecision(OrigConstant.Action.CANCEL);
				applicationM.setFinalAppDecisionBy(userM.getUserName());
				applicationM.setFinalAppDecisionDate(new Timestamp(new java.util.Date().getTime()));
			}
		}
		logger.debug("@@@@@ FinalAppDecision:" + applicationM.getFinalAppDecision());
	}
}
