package com.eaf.orig.ulo.pl.app.dao;

import java.math.BigDecimal;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.Vector;
import org.apache.log4j.Logger;
import com.ava.bpm.model.response.WorkflowResponse;
import com.eaf.orig.profile.model.UserDetailM;
import com.eaf.orig.shared.constant.EJBConstant;
import com.eaf.orig.shared.constant.OrigConstant;
import com.eaf.orig.shared.dao.OrigApplicationMDAOImpl;
import com.eaf.orig.shared.dao.OrigNotePadDataMDAO;
import com.eaf.orig.shared.dao.PLORIGDAOFactory;
import com.eaf.orig.shared.ejb.ORIGGeneratorManager;
import com.eaf.orig.shared.service.ORIGEJBService;
import com.eaf.orig.shared.util.OrigUtil;
import com.eaf.orig.ulo.pl.app.dao.exception.PLOrigApplicationException;
//import com.eaf.orig.ulo.pl.app.utility.PLOrigApplicationUtil;
import com.eaf.orig.ulo.pl.model.app.ApplicationDuplicateM;
import com.eaf.orig.ulo.pl.model.app.PLApplicationDataM;
import com.eaf.orig.ulo.pl.model.app.PLPersonalInfoDataM;
import com.eaf.orig.ulo.pl.service.PLORIGEJBService;
import com.eaf.xrules.ulo.pl.dao.util.XRulesRemoteDAOUtilManager;

public class PLOrigApplicationDAOImpl extends OrigApplicationMDAOImpl implements PLOrigApplicationDAO {

	private static Logger log = Logger.getLogger(PLOrigApplicationDAOImpl.class);
	
	@Override
	public void updateSaveOrigApplication(PLApplicationDataM applicationM, UserDetailM userM)throws PLOrigApplicationException{
		try{
			ORIGGeneratorManager origGenBean = PLORIGEJBService.getGeneratorManager();
						
			if (OrigUtil.isEmptyString(applicationM.getAppRecordID())){
				String appRecId = origGenBean.generateUniqueIDByName(EJBConstant.APPLICATION_RECORD_ID);
				applicationM.setAppRecordID(appRecId);				
			}
			
			log.info("[updateSaveOrigApplication] AppRecID = "+applicationM.getAppRecordID());
			
			if(OrigUtil.isEmptyString(applicationM.getApplicationNo())){
				String appNo = origGenBean.getApplicationNo(applicationM);				
				applicationM.setApplicationNo(appNo);			
			}			
			
			log.info("[updateSaveOrigApplication] Application No = "+applicationM.getApplicationNo());
						
//			#septem comment call from Load Application
//			if(appUtil.isApproveApplication(applicationM)){
//				String capportNo = appUtil.getCreditLineCapportNo(applicationM); //get credit line capport number from ILog
//				applicationM.setCapPortNo(capportNo);
//			}
								
			int returnRows = this.updateTableOrigApplication(applicationM);
			if(returnRows == 0){
				this.insertTableOrigApplication(applicationM);
			}
			
			this.updateSaveSubTable(applicationM);

//			PLOrigApplicationUtil appUtil = new PLOrigApplicationUtil();
//			if(appUtil.isApproveApplication(applicationM)){
//				this.processAllApproveDecision(applicationM, userM);
//			}else if(appUtil.isRejectApplication(applicationM) ||  appUtil.isCancelApplication(applicationM)){
//				this.processCAPoint(applicationM, userM);
//			}
			
		}catch(Exception e){
			log.fatal("[updateSaveOrigApplication] Error ",e);
			throw new PLOrigApplicationException(e.getMessage());
		}
	}
	
	private int updateTableOrigApplication(PLApplicationDataM applicationM)throws PLOrigApplicationException{
		Connection conn = null;
		int returnRows = 0;
		PreparedStatement ps = null;
		try {

			conn = getConnection();
			StringBuilder sql = new StringBuilder("");
			
			sql.append("UPDATE ORIG_APPLICATION ");
			sql.append(" SET APPLICATION_RECORD_ID=?, APPLICATION_NO=?, ACCOUNT_NO=?, OFFICE_CODE=?, DEALER_CODE=? ");
			sql.append(", SELLER_CODE=?, SALEMAN_CODE=?, UPDATE_BY=?, UPDATE_DATE=SYSDATE, CONSENT=?, CONSENT_DATE=? ");//+1
			sql.append(", CMR_START_DATE=?, CMR_END_DATE=?, CMR_FIRST_ID=?, CMR_LAST_ID=?, DE_START_DATE=? ");
			sql.append(", DE_LAST_DATE=?, DE_FIRST_ID=?, DE_LAST_ID=?, CMR_EX_START_DATE=?, CMR_EX_END_DATE=? ");
			sql.append(", CMR_EX_FIRST_ID=?, CMR_EX_LAST_ID=?, UW_START_DATE=?, UW_END_DATE=?, UW_FIRST_ID=? ");
			sql.append(", UW_LAST_ID=?, PD_START_DATE=?, PD_END_DATE=?, PD_FIRST_ID=?, PD_LAST_ID=? ");
			sql.append(", ESCALATE_BY=?, ESCALATE_TO=?, ESCALATE_DATE=?, APPLICATION_STATUS=?, JOB_STATE=?, LOAN_TYPE=? "); //+1
			sql.append(", APPLICATION_TYPE=?, SCORING_RESULT=?, PRIORITY=?, SRCOFCUS=?, CMR_CODE=? ");
			sql.append(", AREA_CODE=?, BOOKING_DATE=?, BOOKING_BY=?, FINAL_CREDIT_LIMIT=?, CREDIT_EXPIRY_DATE=? ");
			sql.append(", UW_DECISION=?, PD_DECISION=?, XCMR_OVERRIDE_BY=?, XCMR_OVERRIDE_DATE=?, XCMR_DECISION=? ");  //50
			sql.append(", UW_DECISION_REASON=?, DRAW_DOWN_FLAG=?, DE_DECISION=?, IDENTIFICATION_NO=?, PD_DECISION_REASON=? ");
			sql.append(", POLICY_VERSION=?, XUW_POLICY_EXCEPTION=?, XUW_DECISION=?, XUW_DECISION_REASON=?, XUW_OVERRIDE_BY=? ");
			sql.append(", XUW_OVERRIDE_DATE=?, XUW_START_DATE=?, XUW_END_DATE=?, XUW_FIRST_ID=?, XUW_LAST_ID=? ");
			sql.append(", BUSINESS_CLASS_ID=?, GROUP_APP_ID=?, APPLICATION_GROUP_ID=?, APPLICATION_DATE=?, APPLY_CHANNEL=? ");
			sql.append(", PROJECT_CODE=?, EXCEPTION_PROJECT =?, EXCEPTION_PROJECT_MEMO=?, EXCEPTION_PROJECT_BY=?, DOC_LIST_RESULT=? ");
			sql.append(", DOC_LIST_NOTE=?, DOC_LIST_UPDATE_BY=?, DOC_LIST_UPDATE_DATE=?, RECOMMENT_CREDIT_LINE=?, SCORING_CREDIT_LINE=? ");
			sql.append(", FINAL_CREDIT_LIMIT_REMARK=?, CA_DECISION=?, POLICY_EXCEPTION_DOC_NO=?, CA_REMARK=? ");
			sql.append(", DOC_LIST_NOTEPAD=?, DC_START_DATE=?, DC_LAST_DATE=?, DC_FIRST_ID=?, DC_LAST_ID=? ");
			sql.append(", CA_START_DATE=?, CA_LAST_DATE=?, CA_FIRST_ID=?, CA_LAST_ID=?, DF_START_DATE=? ");
			sql.append(", DF_LAST_DATE=?, DF_FIRST_ID=?, DF_LAST_ID=?, FU_START_DATE=?, FU_LAST_DATE=? ");   //100
			sql.append(", FU_FIRST_ID=?, FU_LAST_ID=?, CB_START_DATE=?, CB_LAST_DATE=?, CB_FIRST_ID=? ");
			sql.append(", CB_LAST_ID=?, VC_START_DATE=?, VC_LAST_DATE=?, VC_FIRST_ID=?, VC_LAST_ID=? ");
			sql.append(", DOCUMENT_REF_NO=?, PURPOSE_OF_LOAN=?, PRODUCT_HOLDING=?, CAP_PORT_NO=?, APPROVE_DATE=? ");
			sql.append(", APPROVE_BY=?, REOPEN_FLAG=?, APPLICATION_JUSIFY_DATE=?, SALE_TYPE=?, REF_NO=? ");
			sql.append(", REJECT_DATE=?, REJECT_BY=?, CANCEL_DATE=?, CANCEL_BY=?, APPROVE_REASON=? ");
			sql.append(", LIFE_CYCLE=?, CB_DECISION=?, BLOCK_FLAG=?, DC_DECISION=?, DE_SUP_DECISION=? ");
			sql.append(", DE_SUP_FIRST_ID=?, DE_SUP_START_DATE=?, DE_SUP_LAST_ID=?, DE_SUP_LAST_DATE=?, DC_SUP_DECISION=? ");
			sql.append(", DC_SUP_FIRST_ID=?, DC_SUP_START_DATE=?, DC_SUP_LAST_ID=?, DC_SUP_LAST_DATE=?, VC_DECISION=? ");
			sql.append(", DF_DECISION=?, REF_APPLICATION_RECORD_ID=?, JOB_TYPE=?, CUSTOMER_GROUP=?, FU_DECISION=? ");  //145
			sql.append(", FU_NEW_IMG_READY=?, FU_NO_TIME_OUT_FLAG=?, PRODUCT_FEATURE=?, DOC_LIST_RESULT_CODE=?, ICDC_FLAG=? ");
			sql.append(", CUST_REQUEST_CREDIT_LINE=?, CURRENT_CREDIT_LINE=?, PO_DECISION=?, PO_FIRST_ID=?, PO_START_DATE=? ");
			sql.append(", PO_LAST_ID=?, PO_LAST_DATE=?, FINAL_CREDIT_LIMIT_REASON=?, AUDIT_LOG_FLAG=?, VC_SUP_DECISION=? ");
			sql.append(", VC_SUP_FIRST_ID=?, VC_SUP_LAST_DATE=?, VC_SUP_LAST_ID=?, VC_SUP_START_DATE=?, FINAL_APP_DECISION=? ");
			sql.append(", FINAL_APP_DECISION_DATE=?, FINAL_APP_DECISION_BY=?, DLA_LV=? , EXCEPTION_DOC = ? ,SMS_FOLLOW_UP = ?,SMS_FOLLOW_UP_DATE=? ");
			sql.append(", TOTAL_BOT = ?, ACCOUNT_CARD_ID = ?, EXCEPTION_CREDIT_LINE = ? ");
			sql.append(" WHERE APPLICATION_RECORD_ID=? ");

			String dSql = String.valueOf(sql);
			log.debug("Sql=" + dSql);
			ps = conn.prepareStatement(dSql);
			
			int index = 1;
			ps.setString(index++, applicationM.getAppRecordID());
			ps.setString(index++, applicationM.getApplicationNo());
			ps.setString(index++, applicationM.getAccountNo());
			ps.setString(index++, applicationM.getOfficeCode());
			ps.setString(index++, applicationM.getDealerCode());

			ps.setString(index++, applicationM.getSellerCode());
			ps.setString(index++, applicationM.getSalesManCode());
			ps.setString(index++, applicationM.getUpdateBy());
			ps.setString(index++, applicationM.getConsent());
			ps.setTimestamp(index++, this.parseTimestamp(applicationM.getConsentDate()));
			
			ps.setTimestamp(index++, this.parseTimestamp(applicationM.getCmrStartDate()));
			ps.setTimestamp(index++, this.parseTimestamp(applicationM.getCmrEndDate()));
			ps.setString(index++, applicationM.getCmrFirstId());
			ps.setString(index++, applicationM.getCmrLastId());
			ps.setTimestamp(index++, this.parseTimestamp(applicationM.getDeStartDate()));
			
			ps.setTimestamp(index++, this.parseTimestamp(applicationM.getDeLastDate()));
			ps.setString(index++, applicationM.getDeFirstId());
			ps.setString(index++, applicationM.getDeLastId());
			ps.setTimestamp(index++, this.parseTimestamp(applicationM.getCmrExStartDate()));
			ps.setTimestamp(index++, this.parseTimestamp(applicationM.getCmrExEndDate()));
			
			ps.setString(index++, applicationM.getCmrExFirstId());
			ps.setString(index++, applicationM.getCmrExLastId());
			ps.setTimestamp(index++, this.parseTimestamp(applicationM.getUwStartDate()));
			ps.setTimestamp(index++, this.parseTimestamp(applicationM.getUwEndDate()));
			ps.setString(index++, applicationM.getUwFirstId());
			
			ps.setString(index++, applicationM.getUwLastId());
			ps.setTimestamp(index++, this.parseTimestamp(applicationM.getPdStartDate()));
			ps.setTimestamp(index++, this.parseTimestamp(applicationM.getPdEndDate()));
			ps.setString(index++, applicationM.getPdFirstId());
			ps.setString(index++, applicationM.getPdLastId());
			
			ps.setString(index++, applicationM.getEscalateBy());
			ps.setString(index++, applicationM.getEscalateTo());
			ps.setTimestamp(index++, this.parseTimestamp(applicationM.getEscalateDate()));
			ps.setString(index++, applicationM.getApplicationStatus());
			ps.setString(index++, applicationM.getJobState());
			ps.setString(index++, applicationM.getLoanType());
			
			ps.setString(index++, applicationM.getApplicationType());
			ps.setString(index++, applicationM.getScorringResult());
			ps.setString(index++, applicationM.getPriority());
			ps.setString(index++, applicationM.getSrcofcus());
			ps.setString(index++, applicationM.getCmrCode());
			
			ps.setString(index++, applicationM.getAreaCode());
			ps.setTimestamp(index++, applicationM.getBookDate());
			ps.setString(index++, applicationM.getBookBy());
			ps.setBigDecimal(index++, applicationM.getFinalCreditLimit());
			ps.setTimestamp(index++, applicationM.getCreditExpiryDate());
			
			ps.setString(index++, applicationM.getUwDecision());
			ps.setString(index++, applicationM.getPdDecision());
			ps.setString(index++, applicationM.getXcmrOverrideBy());
			ps.setTimestamp(index++, this.parseTimestamp(applicationM.getXcmrOverrideDate()));
			ps.setString(index++, applicationM.getXcmrDecision());  //50
			
			ps.setString(index++, applicationM.getUwDecisionReason());
			ps.setString(index++, applicationM.getDrawDownFlag());
			ps.setString(index++, applicationM.getDeDecision());
			ps.setString(index++, applicationM.getIdentificationNo());
			ps.setString(index++, applicationM.getPdDecisionReason());
			
			ps.setString(index++, applicationM.getPolicyVersion());
			ps.setString(index++, applicationM.getXuwPolicyException());
			ps.setString(index++, applicationM.getXuwDecision());
			ps.setString(index++, applicationM.getXuwDecisionReason());
			ps.setString(index++, applicationM.getXuwOverrideBy());
			
			ps.setTimestamp(index++, applicationM.getXuwOverrideDate());
			ps.setTimestamp(index++, applicationM.getXuwStartDate());
			ps.setTimestamp(index++, this.parseTimestamp(applicationM.getXuwEndDate()));
			ps.setString(index++, applicationM.getXuwFirstId());
			ps.setString(index++, applicationM.getXuwLastId());
			
			ps.setString(index++, applicationM.getBusinessClassId());
			ps.setString(index++, applicationM.getGroupAppID());
			ps.setString(index++, applicationM.getAppGroupId());
			ps.setTimestamp(index++, applicationM.getAppDate());
			ps.setString(index++, applicationM.getApplyChannel());
			
			ps.setString(index++, applicationM.getProjectCode());
			ps.setString(index++, applicationM.getExceptionProject());
			ps.setString(index++, applicationM.getExceptionProjectMemo());
			ps.setString(index++, applicationM.getExceptionProjectBy());
			ps.setString(index++, applicationM.getDocListResult());
			
			ps.setString(index++, applicationM.getDocListNote());
			ps.setString(index++, applicationM.getDocListUpdateBy());
			ps.setTimestamp(index++, applicationM.getDocListUpdateDate());
			ps.setBigDecimal(index++, applicationM.getRecommentCreditLine());
			ps.setBigDecimal(index++, applicationM.getScoringCreditLine());
			
			ps.setString(index++, applicationM.getFinalCreditLimitRemark());
			ps.setString(index++, applicationM.getCaDecision());
			ps.setString(index++, applicationM.getPolicyExcDocNo());
			ps.setString(index++, applicationM.getCaRemark());
			
			ps.setString(index++, applicationM.getDocListNotepad());
			ps.setTimestamp(index++, this.parseTimestamp(applicationM.getDcStartDate()));
			ps.setTimestamp(index++, this.parseTimestamp(applicationM.getDcLastDate()));
			ps.setString(index++, applicationM.getDcFirstId());
			ps.setString(index++, applicationM.getDcLastId());
			
			ps.setTimestamp(index++, this.parseTimestamp(applicationM.getCaStartDate()));
			ps.setTimestamp(index++, this.parseTimestamp(applicationM.getCaLastDate()));
			ps.setString(index++, applicationM.getCaFirstId());
			ps.setString(index++, applicationM.getCaLastId());
			ps.setTimestamp(index++, this.parseTimestamp(applicationM.getDfStartDate()));
			
			ps.setTimestamp(index++, this.parseTimestamp(applicationM.getDfLastDate()));
			ps.setString(index++, applicationM.getDfFirstId());
			ps.setString(index++, applicationM.getDfLastId());
			ps.setTimestamp(index++, this.parseTimestamp(applicationM.getFuStartDate()));
			ps.setTimestamp(index++, this.parseTimestamp(applicationM.getFuLastDate()));  //100
			
			ps.setString(index++, applicationM.getFuFirstId());
			ps.setString(index++, applicationM.getFuLastId());
			ps.setTimestamp(index++, this.parseTimestamp(applicationM.getCbStartDate()));
			ps.setTimestamp(index++, this.parseTimestamp(applicationM.getCbLastDate()));
			ps.setString(index++, applicationM.getCbFirstId());
			
			ps.setString(index++, applicationM.getCbLastId());
			ps.setTimestamp(index++, this.parseTimestamp(applicationM.getVcStartDate()));
			ps.setTimestamp(index++, this.parseTimestamp(applicationM.getVcLastDate()));
			ps.setString(index++, applicationM.getVcFirstId());
			ps.setString(index++, applicationM.getVcLastId());
			
			ps.setString(index++, applicationM.getDocRefNo());
			ps.setString(index++, applicationM.getPurposeOfLaon());
			ps.setString(index++, applicationM.getProductHolding());
			ps.setString(index++, applicationM.getCapPortNo());
			ps.setTimestamp(index++, applicationM.getApproveDate());
			
			ps.setString(index++, applicationM.getApproveBy());
			ps.setString(index++, applicationM.getReopenFlag());
			ps.setTimestamp(index++, this.parseTimestamp(applicationM.getApplicationJusifyDate()));
			ps.setString(index++, applicationM.getSaleType());
			ps.setString(index++, applicationM.getRefNo());
			
			ps.setTimestamp(index++, applicationM.getRejectDate());
			ps.setString(index++, applicationM.getRejectBy());
			ps.setTimestamp(index++, applicationM.getCancelDate());
			ps.setString(index++, applicationM.getCancelBy());
			ps.setString(index++, applicationM.getApproveReason());   //125
			
			ps.setInt(index++, applicationM.getLifeCycle());
			ps.setString(index++, applicationM.getCbDecision());
			ps.setString(index++, applicationM.getBlockFlag());
			ps.setString(index++, applicationM.getDcDecision());
			ps.setString(index++, applicationM.getDeSupDecision());
			
			ps.setString(index++, applicationM.getDeSupFirstId());
			ps.setTimestamp(index++, applicationM.getDeSupFirstDate());
			ps.setString(index++, applicationM.getDeSupLastId());
			ps.setTimestamp(index++, applicationM.getDeSupLastDate());
			ps.setString(index++, applicationM.getDcSupDecision());
			
			ps.setString(index++, applicationM.getDcSupFirstId());
			ps.setTimestamp(index++, applicationM.getDcSupFirstDate());
			ps.setString(index++, applicationM.getDcSupLastId());
			ps.setTimestamp(index++, applicationM.getDcSupLastDate());
			ps.setString(index++, applicationM.getVcDecision());
			
			ps.setString(index++, applicationM.getDF_Decision());
			ps.setString(index++, applicationM.getRefAppRecordId());
			ps.setString(index++, applicationM.getJobType());
			ps.setString(index++, applicationM.getCustomerGroup());
			ps.setString(index++, applicationM.getFuDecision());
			
			ps.setString(index++, applicationM.getFuNewImgReady());
			ps.setString(index++, applicationM.getFuNoTimeOutFlag());
			ps.setString(index++, applicationM.getProductFeature());
			ps.setString(index++, applicationM.getDocListResultCode());
			ps.setString(index++, applicationM.getICDCFlag());
			
			ps.setBigDecimal(index++, applicationM.getCustReqCreditLine());
			ps.setBigDecimal(index++, applicationM.getCurCreditLine());
			ps.setString(index++, applicationM.getPoDecision());
			ps.setString(index++, applicationM.getPoFirstId());
			ps.setTimestamp(index++, applicationM.getPoStartDate());
			
			ps.setString(index++, applicationM.getPoLastId());
			ps.setTimestamp(index++, applicationM.getPoLastDate());
			ps.setString(index++, applicationM.getFinalCreditLimitReason()); //Praisan 20121002
			ps.setString(index++, applicationM.getAuditFlag());
			ps.setString(index++, applicationM.getVcSupDecision());
			
			ps.setString(index++, applicationM.getVcSupFirstId());
			ps.setTimestamp(index++, applicationM.getVcSupLastDate());
			ps.setString(index++, applicationM.getVcSupLastId());
			ps.setTimestamp(index++, applicationM.getVcSupLastDate());
			ps.setString(index++, applicationM.getFinalAppDecision());
			
			ps.setTimestamp(index++, applicationM.getFinalAppDecisionDate());
			ps.setString(index++, applicationM.getFinalAppDecisionBy());
			ps.setString(index++, applicationM.getLevelID());
			ps.setString(index++, applicationM.getExceptionDoc());
			
			ps.setString(index++, applicationM.getSmsFollowUp());
			ps.setTimestamp(index++, applicationM.getSmsFollowUpDate());
			ps.setBigDecimal(index++, applicationM.getBot5xTotal());
			ps.setString(index++, applicationM.getAccountCardId());
			ps.setBigDecimal(index++, applicationM.getExceptionCreditLine());
			
			ps.setString(index++, applicationM.getAppRecordID());

			returnRows = ps.executeUpdate();
			
		} catch (Exception e) {
			log.fatal(e.getLocalizedMessage());
			throw new PLOrigApplicationException(e.getMessage());
		} finally {
			try {
				closeConnection(conn, ps);
			} catch (Exception e) {
				log.fatal(e.getLocalizedMessage());
				throw new PLOrigApplicationException(e.getMessage());
			}
		}
		return returnRows;		
	}
	
	private void updateSaveSubTable(PLApplicationDataM applicationM)throws PLOrigApplicationException{
		
		if(!OrigUtil.isEmptyObject(applicationM.getPaymentMethod())){
			PLOrigPaymentMethodDAO paymentMethodDAO = PLORIGDAOFactory.getPLOrigPaymentMethodDAO();
				paymentMethodDAO.updateSavePaymentMethod(applicationM.getPaymentMethod(), applicationM.getAppRecordID());
		}		
		if(!OrigUtil.isEmptyObject(applicationM.getReferencePerson())){
			PLOrigReferencePersonDAO referencePersonDAO = PLORIGDAOFactory.getPLOrigReferencePersonDAO();
				referencePersonDAO.updateSaveReferencePerson(applicationM.getReferencePerson(), applicationM.getAppRecordID());
		}
		if(!OrigUtil.isEmptyObject(applicationM.getCashTransfer())){
			PLOrigCashTransferDAO cashTransferDAO = PLORIGDAOFactory.getPLOrigCashTransferDAO();
				cashTransferDAO.updateSaveCashTransfer(applicationM.getCashTransfer(), applicationM.getAppRecordID());
		}
		if(!OrigUtil.isEmptyObject(applicationM.getSaleInfo())){
			PLOrigSalesInfoDAO salesInfoDAO = PLORIGDAOFactory.getPLOrigSalesInfoDAO();
				salesInfoDAO.saveUpdatePLOrigSaleInfo(applicationM.getSaleInfo(), applicationM.getAppRecordID());
		}		
		if(!OrigUtil.isEmptyVector(applicationM.getDocCheckListVect())){
			PLOrigDocumentCheckListDAO documentCheckListDAO = PLORIGDAOFactory.getPLOrigDocumentCheckListDAO();
				documentCheckListDAO.SaveUpdateOrigDocumentCheckList(applicationM.getDocCheckListVect(), applicationM.getAppRecordID());
		}
		if(!OrigUtil.isEmptyVector(applicationM.getApplicationImageVect())){
			PLOrigApplicationImageDAO applicationImageDAO = PLORIGDAOFactory.getPLOrigApplicationImageDAO();
				applicationImageDAO.saveUpdateOrigApplicationImage(applicationM.getApplicationImageVect(), applicationM.getAppRecordID());
		}
		if(!OrigUtil.isEmptyObject(applicationM.getBundleALM())){
			PLOrigBundleALDAO bundleALDAO = PLORIGDAOFactory.getPLOrigBundleALDAO();
				bundleALDAO.saveUpdateBundleAL(applicationM.getBundleALM(), applicationM.getAppRecordID());
		}
		if(!OrigUtil.isEmptyObject(applicationM.getBundleCCM())){
			PLOrigBundleCCDAO bundleCCDAO = PLORIGDAOFactory.getPLOrigBundleCCDAO();
				bundleCCDAO.saveUpdateBundleCC(applicationM.getBundleCCM(), applicationM.getAppRecordID());
		}
		if(!OrigUtil.isEmptyObject(applicationM.getBundleHLM())){
			PLOrigBundleHLDAO bundleHLDAO = PLORIGDAOFactory.getPLOrigBundleHLDAO();
				bundleHLDAO.saveUpdateBundleHL(applicationM.getBundleHLM(), applicationM.getAppRecordID());
		}
		if(!OrigUtil.isEmptyObject(applicationM.getBundleSMEM())){
			PLOrigBundleSMEDAO bundleSMEDAO = PLORIGDAOFactory.getPLOrigBundleSMEDAO();
				bundleSMEDAO.saveUpdateBundleSME(applicationM.getBundleSMEM(), applicationM.getAppRecordID());
		}
		if(!OrigUtil.isEmptyObject(applicationM.getKycM())){
			PLOrigKYCDAO kYCDAO = PLORIGDAOFactory.getPLOrigKYCDAO();
				kYCDAO.saveUpdate(applicationM.getKycM(), applicationM.getAppRecordID());
		}
		
		if(!OrigUtil.isEmptyVector(applicationM.getPersonalInfoVect())){			
			PLOrigPersonalInfoDAO personalInfoDAO = PLORIGDAOFactory.getPLOrigPersonalInfoDAO();
				personalInfoDAO.saveUpdateOrigPersonalInfo(applicationM.getPersonalInfoVect(), applicationM.getAppRecordID(), applicationM);
		}	
		
		if(!OrigUtil.isEmptyVector(applicationM.getNotepadDataM())){
			OrigNotePadDataMDAO origNotePadDataMDAO = PLORIGDAOFactory.getOrigNotePadDataMDAO();
				origNotePadDataMDAO.saveUpdateModelOrigNotePadDataVect(applicationM.getNotepadDataM(), applicationM.getAppRecordID());
		}
		
		if(!OrigUtil.isEmptyVector(applicationM.getBot5xVect())){
			Bot5XDAO bot5xDAO = PLORIGDAOFactory.getBot5xDAO();
				bot5xDAO.saveORIG_BOT(applicationM.getAppRecordID(), applicationM.getBot5xVect());
		}else{
			Bot5XDAO bot5xDAO = PLORIGDAOFactory.getBot5xDAO();
				bot5xDAO.deleteORIG_BOT(applicationM.getAppRecordID());
		}
		
	}
	
	
	private void insertTableOrigApplication(PLApplicationDataM applicationM)throws PLOrigApplicationException{		
		PreparedStatement ps = null;
		Connection conn = null;
		try{
			
			conn = getConnection();
			StringBuilder sql = new StringBuilder("");
			sql.append("INSERT INTO ORIG_APPLICATION ");
			sql.append("( APPLICATION_RECORD_ID, APPLICATION_NO, ACCOUNT_NO, OFFICE_CODE, DEALER_CODE");
			sql.append(", SELLER_CODE, SALEMAN_CODE, CREATE_BY, CREATE_DATE, UPDATE_BY ");
			sql.append(", UPDATE_DATE, CONSENT, CONSENT_DATE, CMR_START_DATE, CMR_END_DATE ");
			sql.append(", CMR_FIRST_ID, CMR_LAST_ID, DE_START_DATE, DE_LAST_DATE, DE_FIRST_ID ");
			sql.append(", DE_LAST_ID, CMR_EX_START_DATE, CMR_EX_END_DATE, CMR_EX_FIRST_ID, CMR_EX_LAST_ID ");
			sql.append(", UW_START_DATE, UW_END_DATE, UW_FIRST_ID, UW_LAST_ID, PD_START_DATE ");
			sql.append(", PD_END_DATE, PD_FIRST_ID, PD_LAST_ID, ESCALATE_BY, ESCALATE_TO ");
			sql.append(", ESCALATE_DATE, APPLICATION_STATUS, JOB_STATE, LOAN_TYPE, APPLICATION_TYPE ");
			sql.append(", SCORING_RESULT, PRIORITY, SRCOFCUS, CMR_CODE, AREA_CODE ");
			sql.append(", BOOKING_DATE, BOOKING_BY, FINAL_CREDIT_LIMIT, CREDIT_EXPIRY_DATE, UW_DECISION ");
			sql.append(", PD_DECISION , XCMR_OVERRIDE_BY, XCMR_OVERRIDE_DATE, XCMR_DECISION, UW_DECISION_REASON ");
			sql.append(", DRAW_DOWN_FLAG, DE_DECISION, IDENTIFICATION_NO, PD_DECISION_REASON, POLICY_VERSION ");
			sql.append(", XUW_POLICY_EXCEPTION, XUW_DECISION, XUW_DECISION_REASON, XUW_OVERRIDE_BY, XUW_OVERRIDE_DATE ");
			sql.append(", XUW_START_DATE, XUW_END_DATE, XUW_FIRST_ID, XUW_LAST_ID, BUSINESS_CLASS_ID ");
			sql.append(", GROUP_APP_ID, APPLICATION_GROUP_ID, APPLICATION_DATE, APPLY_CHANNEL, PROJECT_CODE ");
			sql.append(", EXCEPTION_PROJECT , EXCEPTION_PROJECT_MEMO, EXCEPTION_PROJECT_BY, DOC_LIST_RESULT, DOC_LIST_NOTE ");
			sql.append(", DOC_LIST_UPDATE_BY, DOC_LIST_UPDATE_DATE, RECOMMENT_CREDIT_LINE, SCORING_CREDIT_LINE, EXCEPTION_CREDIT_LINE ");
			sql.append(", FINAL_CREDIT_LIMIT_REMARK, CA_DECISION, POLICY_EXCEPTION_DOC_NO, CA_REMARK, DOC_LIST_NOTEPAD ");
			sql.append(", DC_START_DATE, DC_LAST_DATE, DC_FIRST_ID, DC_LAST_ID, CA_START_DATE ");
			sql.append(", CA_LAST_DATE, CA_FIRST_ID, CA_LAST_ID, DF_START_DATE, DF_LAST_DATE ");
			sql.append(", DF_FIRST_ID, DF_LAST_ID, FU_START_DATE, FU_LAST_DATE, FU_FIRST_ID ");
			sql.append(", FU_LAST_ID, CB_START_DATE, CB_LAST_DATE, CB_FIRST_ID, CB_LAST_ID ");
			sql.append(", VC_START_DATE, VC_LAST_DATE, VC_FIRST_ID, VC_LAST_ID, DOCUMENT_REF_NO ");
			sql.append(", PURPOSE_OF_LOAN, PRODUCT_HOLDING, CAP_PORT_NO, APPROVE_DATE, APPROVE_BY ");
			sql.append(", REOPEN_FLAG, APPLICATION_JUSIFY_DATE, SALE_TYPE, REF_NO, REJECT_DATE ");
			sql.append(", REJECT_BY , CANCEL_DATE, CANCEL_BY, APPROVE_REASON, LIFE_CYCLE ");
			sql.append(", CB_DECISION , BLOCK_FLAG, DC_DECISION, DE_SUP_DECISION, DE_SUP_FIRST_ID ");
			sql.append(", DE_SUP_START_DATE , DE_SUP_LAST_ID, DE_SUP_LAST_DATE, DC_SUP_DECISION, DC_SUP_FIRST_ID ");
			sql.append(", DC_SUP_START_DATE , DC_SUP_LAST_ID, DC_SUP_LAST_DATE, VC_DECISION, DF_DECISION ");
			sql.append(", REF_APPLICATION_RECORD_ID, JOB_TYPE, CUSTOMER_GROUP, FU_DECISION, FU_NEW_IMG_READY ");
			sql.append(", FU_NO_TIME_OUT_FLAG, PRODUCT_FEATURE, DOC_LIST_RESULT_CODE, ICDC_FLAG, CUST_REQUEST_CREDIT_LINE ");
			sql.append(", CURRENT_CREDIT_LINE, PO_DECISION, PO_FIRST_ID, PO_START_DATE, PO_LAST_ID ");
			sql.append(", PO_LAST_DATE, FINAL_CREDIT_LIMIT_REASON, AUDIT_LOG_FLAG, VC_SUP_DECISION, VC_SUP_FIRST_ID ");
			sql.append(", VC_SUP_LAST_DATE, VC_SUP_LAST_ID, VC_SUP_START_DATE, FINAL_APP_DECISION, FINAL_APP_DECISION_DATE ");
			sql.append(", FINAL_APP_DECISION_BY, DLA_LV ,EXCEPTION_DOC , SMS_FOLLOW_UP, SMS_FOLLOW_UP_DATE, TOTAL_BOT, ACCOUNT_CARD_ID) ");
			sql.append(" VALUES(?,?,?,?,?  ,?,?,?,SYSDATE,?  ,SYSDATE,?,?,?,?  ,?,?,?,?,? ");
			sql.append(",?,?,?,?,?  ,?,?,?,?,?  ,?,?,?,?,?  ,?,?,?,?,? ");
			sql.append(",?,?,?,?,?  ,?,?,?,?,?  ,?,?,?,?,?  ,?,?,?,?,? ");
			sql.append(",?,?,?,?,?  ,?,?,?,?,?  ,?,?,?,?,?  ,?,?,?,?,? ");
			sql.append(",?,?,?,?,?  ,?,?,?,?,?  ,?,?,?,?,?  ,?,?,?,?,? ");
			sql.append(",?,?,?,?,?  ,?,?,?,?,?  ,?,?,?,?,?  ,?,?,?,?,? ");
			sql.append(",?,?,?,?,?  ,?,?,?,?,?  ,?,?,?,?,?  ,?,?,?,?,? ");
			sql.append(",?,?,?,?,?  ,?,?,?,?,?  ,?,?,?,?,?  ,?,?,?,?,? ");
			sql.append(",?,?,?,?,?  ,?,?,?,?,?  ,?,?,?,?,? 	,?,?)");
		
			String dSql = String.valueOf(sql);
			log.debug("Sql=" + dSql);
			ps = conn.prepareStatement(dSql);
			
			int index = 1;
			ps.setString(index++, applicationM.getAppRecordID());
			ps.setString(index++, applicationM.getApplicationNo());
			ps.setString(index++, applicationM.getAccountNo());
			ps.setString(index++, applicationM.getOfficeCode());
			ps.setString(index++, applicationM.getDealerCode());

			ps.setString(index++, applicationM.getSellerCode());
			ps.setString(index++, applicationM.getSalesManCode());
			ps.setString(index++, applicationM.getCreateBy());
			ps.setString(index++, applicationM.getUpdateBy());
			
			ps.setString(index++, applicationM.getConsent());
			ps.setTimestamp(index++, this.parseTimestamp(applicationM.getConsentDate()));
			ps.setTimestamp(index++, this.parseTimestamp(applicationM.getCmrStartDate()));
			ps.setTimestamp(index++, this.parseTimestamp(applicationM.getCmrEndDate()));
			
			ps.setString(index++, applicationM.getCmrFirstId());
			ps.setString(index++, applicationM.getCmrLastId());
			ps.setTimestamp(index++, this.parseTimestamp(applicationM.getDeStartDate()));
			ps.setTimestamp(index++, this.parseTimestamp(applicationM.getDeLastDate()));
			ps.setString(index++, applicationM.getDeFirstId());
			
			ps.setString(index++, applicationM.getDeLastId());
			ps.setTimestamp(index++, this.parseTimestamp(applicationM.getCmrExStartDate()));
			ps.setTimestamp(index++, this.parseTimestamp(applicationM.getCmrExEndDate()));
			ps.setString(index++, applicationM.getCmrExFirstId());
			ps.setString(index++, applicationM.getCmrExLastId());
			
			ps.setTimestamp(index++, this.parseTimestamp(applicationM.getUwStartDate()));
			ps.setTimestamp(index++, this.parseTimestamp(applicationM.getUwEndDate()));
			ps.setString(index++, applicationM.getUwFirstId());
			ps.setString(index++, applicationM.getUwLastId());
			ps.setTimestamp(index++, this.parseTimestamp(applicationM.getPdStartDate()));
			
			ps.setTimestamp(index++, this.parseTimestamp(applicationM.getPdEndDate()));
			ps.setString(index++, applicationM.getPdFirstId());
			ps.setString(index++, applicationM.getPdLastId());
			ps.setString(index++, applicationM.getEscalateBy());
			ps.setString(index++, applicationM.getEscalateTo());
			
			ps.setTimestamp(index++, this.parseTimestamp(applicationM.getEscalateDate()));
			ps.setString(index++, applicationM.getApplicationStatus());
			ps.setString(index++, applicationM.getJobState());
			ps.setString(index++, applicationM.getLoanType());
			ps.setString(index++, applicationM.getApplicationType());
			
			ps.setString(index++, applicationM.getScorringResult());
			ps.setString(index++, applicationM.getPriority());
			ps.setString(index++, applicationM.getSrcofcus());
			ps.setString(index++, applicationM.getCmrCode());
			ps.setString(index++, applicationM.getAreaCode());
			
			ps.setTimestamp(index++, applicationM.getBookDate());
			ps.setString(index++, applicationM.getBookBy());
			ps.setBigDecimal(index++, applicationM.getFinalCreditLimit());
			ps.setTimestamp(index++, applicationM.getCreditExpiryDate());
			ps.setString(index++, applicationM.getUwDecision());
			
			ps.setString(index++, applicationM.getPdDecision());
			ps.setString(index++, applicationM.getXcmrOverrideBy());
			ps.setTimestamp(index++, this.parseTimestamp(applicationM.getXcmrOverrideDate()));
			ps.setString(index++, applicationM.getXcmrDecision());
			ps.setString(index++, applicationM.getUwDecisionReason());
			
			ps.setString(index++, applicationM.getDrawDownFlag());
			ps.setString(index++, applicationM.getDeDecision());
			ps.setString(index++, applicationM.getIdentificationNo());
			ps.setString(index++, applicationM.getPdDecisionReason());
			ps.setString(index++, applicationM.getPolicyVersion());
			
			ps.setString(index++, applicationM.getXuwPolicyException());
			ps.setString(index++, applicationM.getXuwDecision());
			ps.setString(index++, applicationM.getXuwDecisionReason());
			ps.setString(index++, applicationM.getXuwOverrideBy());
			ps.setTimestamp(index++, applicationM.getXuwOverrideDate());
			
			ps.setTimestamp(index++, applicationM.getXuwStartDate());
			ps.setTimestamp(index++, this.parseTimestamp(applicationM.getXuwEndDate()));
			ps.setString(index++, applicationM.getXuwFirstId());
			ps.setString(index++, applicationM.getXuwLastId());
			ps.setString(index++, applicationM.getBusinessClassId());
			
			ps.setString(index++, applicationM.getGroupAppID());
			ps.setString(index++, applicationM.getAppGroupId());
			ps.setTimestamp(index++, applicationM.getAppDate());
			ps.setString(index++, applicationM.getApplyChannel());
			ps.setString(index++, applicationM.getProjectCode());
			
			ps.setString(index++, applicationM.getExceptionProject());
			ps.setString(index++, applicationM.getExceptionProjectMemo());
			ps.setString(index++, applicationM.getExceptionProjectBy());
			ps.setString(index++, applicationM.getDocListResult());
			ps.setString(index++, applicationM.getDocListNote());
			
			ps.setString(index++, applicationM.getDocListUpdateBy());
			ps.setTimestamp(index++, applicationM.getDocListUpdateDate());
			ps.setBigDecimal(index++, applicationM.getRecommentCreditLine());
			ps.setBigDecimal(index++, applicationM.getScoringCreditLine());
			ps.setBigDecimal(index++, applicationM.getExceptionCreditLine());
			
			ps.setString(index++, applicationM.getFinalCreditLimitRemark());
			ps.setString(index++, applicationM.getCaDecision());
			ps.setString(index++, applicationM.getPolicyExcDocNo());
			ps.setString(index++, applicationM.getCaRemark());
			ps.setString(index++, applicationM.getDocListNotepad());
			
			ps.setTimestamp(index++, this.parseTimestamp(applicationM.getDcStartDate()));
			ps.setTimestamp(index++, this.parseTimestamp(applicationM.getDcLastDate()));
			ps.setString(index++, applicationM.getDcFirstId());
			ps.setString(index++, applicationM.getDcLastId());
			ps.setTimestamp(index++, this.parseTimestamp(applicationM.getCaStartDate()));
			
			ps.setTimestamp(index++, this.parseTimestamp(applicationM.getCaLastDate()));
			ps.setString(index++, applicationM.getCaFirstId());
			ps.setString(index++, applicationM.getCaLastId());
			ps.setTimestamp(index++, this.parseTimestamp(applicationM.getDfStartDate()));
			ps.setTimestamp(index++, this.parseTimestamp(applicationM.getDfLastDate()));
			
			ps.setString(index++, applicationM.getDfFirstId());
			ps.setString(index++, applicationM.getDfLastId());
			ps.setTimestamp(index++, this.parseTimestamp(applicationM.getFuStartDate()));
			ps.setTimestamp(index++, this.parseTimestamp(applicationM.getFuLastDate()));
			ps.setString(index++, applicationM.getFuFirstId());
			
			ps.setString(index++, applicationM.getFuLastId());
			ps.setTimestamp(index++, this.parseTimestamp(applicationM.getCbStartDate()));
			ps.setTimestamp(index++, this.parseTimestamp(applicationM.getCbLastDate()));
			ps.setString(index++, applicationM.getCbFirstId());
			ps.setString(index++, applicationM.getCbLastId());
			
			ps.setTimestamp(index++, this.parseTimestamp(applicationM.getVcStartDate()));
			ps.setTimestamp(index++, this.parseTimestamp(applicationM.getVcLastDate()));
			ps.setString(index++, applicationM.getVcFirstId());
			ps.setString(index++, applicationM.getVcLastId());
			ps.setString(index++, applicationM.getDocRefNo());
			
			ps.setString(index++, applicationM.getPurposeOfLaon());
			ps.setString(index++, applicationM.getProductHolding());
			ps.setString(index++, applicationM.getCapPortNo());
			ps.setTimestamp(index++, this.parseTimestamp(applicationM.getApproveDate()));
			ps.setString(index++, applicationM.getApproveBy());
			
			ps.setString(index++, applicationM.getReopenFlag());
			ps.setTimestamp(index++, this.parseTimestamp(applicationM.getApplicationJusifyDate()));
			ps.setString(index++, applicationM.getSaleType());
			ps.setString(index++, applicationM.getRefNo());
			ps.setTimestamp(index++, applicationM.getRejectDate());
			
			ps.setString(index++, applicationM.getRejectBy());
			ps.setTimestamp(index++, applicationM.getCancelDate());
			ps.setString(index++, applicationM.getCancelBy());
			ps.setString(index++, applicationM.getApproveReason());
			ps.setInt(index++, applicationM.getLifeCycle());
			
			ps.setString(index++, applicationM.getCbDecision());
			ps.setString(index++, applicationM.getBlockFlag());
			ps.setString(index++, applicationM.getDcDecision());
			ps.setString(index++, applicationM.getDeSupDecision());
			ps.setString(index++, applicationM.getDeSupFirstId());
			
			ps.setTimestamp(index++, applicationM.getDeSupFirstDate());
			ps.setString(index++, applicationM.getDeSupLastId());
			ps.setTimestamp(index++, applicationM.getDeSupLastDate());
			ps.setString(index++, applicationM.getDcSupDecision());
			ps.setString(index++, applicationM.getDcSupFirstId());

			ps.setTimestamp(index++, applicationM.getDcSupFirstDate());
			ps.setString(index++, applicationM.getDcSupLastId());
			ps.setTimestamp(index++, applicationM.getDcSupLastDate());
			ps.setString(index++, applicationM.getVcDecision());
			ps.setString(index++, applicationM.getDF_Decision());
			
			ps.setString(index++, applicationM.getRefAppRecordId());
			ps.setString(index++, applicationM.getJobType());
			ps.setString(index++, applicationM.getCustomerGroup());
			ps.setString(index++, applicationM.getFuDecision());
			ps.setString(index++, applicationM.getFuNewImgReady());
			
			ps.setString(index++, applicationM.getFuNoTimeOutFlag());
			ps.setString(index++, applicationM.getProductFeature());
			ps.setString(index++, applicationM.getDocListResultCode());
			ps.setString(index++, applicationM.getICDCFlag());
			ps.setBigDecimal(index++, applicationM.getCustReqCreditLine());
			
			ps.setBigDecimal(index++, applicationM.getCurCreditLine());
			ps.setString(index++, applicationM.getPoDecision());
			ps.setString(index++, applicationM.getPoFirstId());
			ps.setTimestamp(index++, applicationM.getPoStartDate());
			ps.setString(index++, applicationM.getPoLastId());
			
			ps.setTimestamp(index++, applicationM.getPoLastDate());
			ps.setString(index++, applicationM.getFinalCreditLimitReason());
			ps.setString(index++, applicationM.getAuditFlag());
			ps.setString(index++, applicationM.getVcSupDecision());
			ps.setString(index++, applicationM.getVcSupFirstId());
			
			ps.setTimestamp(index++, applicationM.getVcSupLastDate());
			ps.setString(index++, applicationM.getVcSupLastId());
			ps.setTimestamp(index++, applicationM.getVcSupLastDate());
			ps.setString(index++, applicationM.getFinalAppDecision());
			ps.setTimestamp(index++, applicationM.getFinalAppDecisionDate());
			
			ps.setString(index++, applicationM.getFinalAppDecisionBy());
			ps.setString(index++, applicationM.getLevelID());
			ps.setString(index++, applicationM.getExceptionDoc());
			
			ps.setString(index++, applicationM.getSmsFollowUp());
			ps.setTimestamp(index++, applicationM.getSmsFollowUpDate());
			ps.setBigDecimal(index++, applicationM.getBot5xTotal());
			ps.setString(index++, applicationM.getAccountCardId());
			
			ps.executeUpdate();
			
		}catch (Exception e) {
			log.fatal(e.getLocalizedMessage());
			throw new PLOrigApplicationException(e.getMessage());
		} finally {
			try {
				closeConnection(conn, ps);
			} catch (Exception e) {
				log.fatal(e.getLocalizedMessage());
				throw new PLOrigApplicationException(e.getMessage());
			}
		}
		
	}

	@Override
	public PLApplicationDataM loadOrigApplication(String appRecId) throws PLOrigApplicationException{		
		try{			
			//load appData
			PLApplicationDataM appDataM = this.selectOrig_Appplication(appRecId);
			
			//load reason
			PLOrigReasonDAO reasonDAO = PLORIGDAOFactory.getPLOrigReasonDAO();
			appDataM.setReasonVect(reasonDAO.loadOrigReason(appRecId));
			
			//load payment
			PLOrigPaymentMethodDAO paymentMethodDAO = PLORIGDAOFactory.getPLOrigPaymentMethodDAO();
			appDataM.setPaymentMethod(paymentMethodDAO.loadPaymentMethod(appRecId));
			
			//load reference person
			PLOrigReferencePersonDAO referencePersonDAO = PLORIGDAOFactory.getPLOrigReferencePersonDAO();
			appDataM.setReferencePerson(referencePersonDAO.loadReferencePerson(appRecId));
			
			//load cash transfer
			PLOrigCashTransferDAO cashTransferDAO = PLORIGDAOFactory.getPLOrigCashTransferDAO();
			appDataM.setCashTransfer(cashTransferDAO.loadCashTransfer(appRecId));
			
			//load sales info
			PLOrigSalesInfoDAO salesInfoDAO = PLORIGDAOFactory.getPLOrigSalesInfoDAO();
			appDataM.setSaleInfo(salesInfoDAO.loadPLOrigSaleInfoDataM(appRecId));
			
			//load doc check list
			PLOrigDocumentCheckListDAO docCheckListDAO = PLORIGDAOFactory.getPLOrigDocumentCheckListDAO();
			appDataM.setDocCheckListVect(docCheckListDAO.LoadOrigDocumentCheckList(appRecId));
			
			//load app log
			PLOrigApplicationLogDAO applicationLogDAO = PLORIGDAOFactory.getPLOrigApplicationLogDAO();
			appDataM.setApplicationLogVect(applicationLogDAO.loadOrigApplicationLog(appDataM.getAppRecordID()));
			
			//load app image
			PLOrigApplicationImageDAO applicationImageDAO = PLORIGDAOFactory.getPLOrigApplicationImageDAO();
			appDataM.setApplicationImageVect(applicationImageDAO.loadOrigApplicationImage(appDataM.getAppRecordID()));
			
			//bundle AL
			PLOrigBundleALDAO bundleALDAO = PLORIGDAOFactory.getPLOrigBundleALDAO();
			appDataM.setBundleALM(bundleALDAO.loadBundleAL(appDataM.getAppRecordID()));
			
			//bundle CC
			PLOrigBundleCCDAO bundleCCDAO = PLORIGDAOFactory.getPLOrigBundleCCDAO();
			appDataM.setBundleCCM(bundleCCDAO.loadBundleCC(appDataM.getAppRecordID()));
			
			//bundle HL
			PLOrigBundleHLDAO bundleHLDAO = PLORIGDAOFactory.getPLOrigBundleHLDAO();
			appDataM.setBundleHLM(bundleHLDAO.loadBundleHL(appDataM.getAppRecordID()));
			
			//bundle SME
			PLOrigBundleSMEDAO bundleSMEDAO = PLORIGDAOFactory.getPLOrigBundleSMEDAO();
			appDataM.setBundleSMEM(bundleSMEDAO.loadBundleSME(appDataM.getAppRecordID()));
			
			//KYC
			PLOrigKYCDAO kYCDAO = PLORIGDAOFactory.getPLOrigKYCDAO();
			appDataM.setKycM(kYCDAO.loadKYC(appDataM.getAppRecordID()));
			
			//load personal
			PLOrigPersonalInfoDAO personalInfoDAO = PLORIGDAOFactory.getPLOrigPersonalInfoDAO();			
			appDataM.setPersonalInfoVect(personalInfoDAO.loadOrigPersonalInfo(appDataM.getAppRecordID(), appDataM.getBusinessClassId()));
			//appDataM.setPersonalInfoVect(plOrigPersonalInfoDAO.loadOrigPersonalInfo(appDataM.getAppRecordID()));
			
			OrigNotePadDataMDAO origNotePadDataMDAO = PLORIGDAOFactory.getOrigNotePadDataMDAO();
			appDataM.setNotepadDataM(origNotePadDataMDAO.loadModelOrigNotePadDataM(appDataM.getAppRecordID()));
			
			//Sankom Add attachment history DAO
			PLOrigAttachmentHistoryMDAO origAttachmentHistoryDAO=PLORIGDAOFactory.getPLOrigAttachmentHistoryMDAO();
			appDataM.setAttachmentHistoryVect(origAttachmentHistoryDAO.loadModelOrigAttachmentHistoryM(appDataM.getAppRecordID()));
			
			Bot5XDAO bot5xDAO = PLORIGDAOFactory.getBot5xDAO();
			appDataM.setBot5xVect(bot5xDAO.selectORIG_BOT(appDataM.getAppRecordID()));
			
			return appDataM;
		} catch (Exception e) {
			log.fatal("Exception >> ",e);
			throw new PLOrigApplicationException(e.getMessage());
		}
		
	}
	
	public PLApplicationDataM selectOrig_Appplication (String appRecId)throws PLOrigApplicationException {		
		PreparedStatement ps = null;
		ResultSet rs = null;
		PLApplicationDataM applicationM = new PLApplicationDataM();
		Connection conn = null;
		try {
			conn = getConnection();
			StringBuilder sql = new StringBuilder("");
			sql.append("SELECT APPLICATION_RECORD_ID, APPLICATION_NO, ACCOUNT_NO, OFFICE_CODE, DEALER_CODE ");
			sql.append(", SELLER_CODE, SALEMAN_CODE, CREATE_BY, CREATE_DATE, UPDATE_BY ");
			sql.append(", UPDATE_DATE, CONSENT, CONSENT_DATE, CMR_START_DATE, CMR_END_DATE ");
			sql.append(", CMR_FIRST_ID, CMR_LAST_ID, DE_START_DATE, DE_LAST_DATE, DE_FIRST_ID ");
			sql.append(", DE_LAST_ID, CMR_EX_START_DATE, CMR_EX_END_DATE, CMR_EX_FIRST_ID, CMR_EX_LAST_ID ");
			sql.append(", UW_START_DATE, UW_END_DATE, UW_FIRST_ID, UW_LAST_ID, PD_START_DATE ");
			sql.append(", PD_END_DATE, PD_FIRST_ID, PD_LAST_ID, ESCALATE_BY, ESCALATE_TO ");
			sql.append(", ESCALATE_DATE, APPLICATION_STATUS, JOB_STATE, LOAN_TYPE, APPLICATION_TYPE ");
			sql.append(", SCORING_RESULT, PRIORITY, SRCOFCUS, CMR_CODE, AREA_CODE ");
			sql.append(", BOOKING_DATE, BOOKING_BY, FINAL_CREDIT_LIMIT, CREDIT_EXPIRY_DATE, UW_DECISION ");
			sql.append(", PD_DECISION , XCMR_OVERRIDE_BY, XCMR_OVERRIDE_DATE, XCMR_DECISION, UW_DECISION_REASON ");
			sql.append(", DRAW_DOWN_FLAG, DE_DECISION, IDENTIFICATION_NO, PD_DECISION_REASON, POLICY_VERSION ");
			sql.append(", XUW_POLICY_EXCEPTION, XUW_DECISION, XUW_DECISION_REASON, XUW_OVERRIDE_BY, XUW_OVERRIDE_DATE ");
			sql.append(", XUW_START_DATE, XUW_END_DATE, XUW_FIRST_ID, XUW_LAST_ID, BUSINESS_CLASS_ID ");
			sql.append(", GROUP_APP_ID, APPLICATION_GROUP_ID, APPLICATION_DATE, APPLY_CHANNEL, PROJECT_CODE ");
			sql.append(", EXCEPTION_PROJECT , EXCEPTION_PROJECT_MEMO, EXCEPTION_PROJECT_BY, DOC_LIST_RESULT, DOC_LIST_NOTE ");
			sql.append(", DOC_LIST_UPDATE_BY, DOC_LIST_UPDATE_DATE, RECOMMENT_CREDIT_LINE, SCORING_CREDIT_LINE, EXCEPTION_CREDIT_LINE ");
			sql.append(", FINAL_CREDIT_LIMIT_REMARK, CA_DECISION, POLICY_EXCEPTION_DOC_NO, CA_REMARK, DOC_LIST_NOTEPAD ");
			sql.append(", DC_START_DATE, DC_LAST_DATE, DC_FIRST_ID, DC_LAST_ID, CA_START_DATE ");
			sql.append(", CA_LAST_DATE, CA_FIRST_ID, CA_LAST_ID, DF_START_DATE, DF_LAST_DATE ");
			sql.append(", DF_FIRST_ID, DF_LAST_ID, FU_START_DATE, FU_LAST_DATE, FU_FIRST_ID ");
			sql.append(", FU_LAST_ID, CB_START_DATE, CB_LAST_DATE, CB_FIRST_ID, CB_LAST_ID ");
			sql.append(", VC_START_DATE, VC_LAST_DATE, VC_FIRST_ID, VC_LAST_ID, DOCUMENT_REF_NO ");
			sql.append(", PURPOSE_OF_LOAN, PRODUCT_HOLDING, CAP_PORT_NO, APPROVE_DATE, APPROVE_BY ");
			sql.append(", REOPEN_FLAG, APPLICATION_JUSIFY_DATE, SALE_TYPE, REF_NO, REJECT_DATE ");
			sql.append(", REJECT_BY , CANCEL_DATE, CANCEL_BY, APPROVE_REASON, LIFE_CYCLE ");
			sql.append(", CB_DECISION , BLOCK_FLAG, DC_DECISION, DE_SUP_DECISION, DE_SUP_FIRST_ID ");
			sql.append(", DE_SUP_START_DATE , DE_SUP_LAST_ID, DE_SUP_LAST_DATE, DC_SUP_DECISION, DC_SUP_FIRST_ID ");
			sql.append(", DC_SUP_START_DATE , DC_SUP_LAST_ID, DC_SUP_LAST_DATE, VC_DECISION, DF_DECISION ");
			sql.append(", REF_APPLICATION_RECORD_ID, JOB_TYPE, CUSTOMER_GROUP, FU_DECISION, FU_NEW_IMG_READY ");
			sql.append(", FU_NO_TIME_OUT_FLAG, PRODUCT_FEATURE, DOC_LIST_RESULT_CODE, ICDC_FLAG, CUST_REQUEST_CREDIT_LINE ");
			sql.append(", CURRENT_CREDIT_LINE, PO_DECISION, PO_FIRST_ID, PO_START_DATE, PO_LAST_ID ");
			sql.append(", PO_LAST_DATE, FINAL_CREDIT_LIMIT_REASON, AUDIT_LOG_FLAG, VC_SUP_DECISION, VC_SUP_FIRST_ID ");
			sql.append(", VC_SUP_LAST_DATE, VC_SUP_LAST_ID, VC_SUP_START_DATE, FINAL_APP_DECISION, FINAL_APP_DECISION_DATE ");
			sql.append(", FINAL_APP_DECISION_BY, DLA_LV ,EXCEPTION_DOC,SMS_FOLLOW_UP,SMS_FOLLOW_UP_DATE ,TOTAL_BOT, ACCOUNT_CARD_ID ");
			
			sql.append(" FROM ORIG_APPLICATION WHERE APPLICATION_RECORD_ID = ? ");
			String dSql = String.valueOf(sql);
			log.debug("Sql=" + dSql);
			ps = conn.prepareStatement(dSql);
			ps.setString(1, appRecId);

			rs = ps.executeQuery();
			if (rs.next()) {
				int index = 1;					
				
				applicationM.setAppRecordID(rs.getString(index++));
				applicationM.setApplicationNo(rs.getString(index++));
				applicationM.setAccountNo(rs.getString(index++));
				applicationM.setOfficeCode(rs.getString(index++));
				applicationM.setDealerCode(rs.getString(index++));
				
				applicationM.setSellerCode(rs.getString(index++));
				applicationM.setSalesManCode(rs.getString(index++));
				applicationM.setCreateBy(rs.getString(index++));
				applicationM.setCreateDate(rs.getTimestamp(index++));
				applicationM.setUpdateBy(rs.getString(index++));
				
				applicationM.setUpdateDate(rs.getTimestamp(index++));
				applicationM.setConsent(rs.getString(index++));
				applicationM.setConsentDate(rs.getTimestamp(index++));
				applicationM.setCmrStartDate(rs.getTimestamp(index++));
				applicationM.setCmrEndDate(rs.getTimestamp(index++));
				
				applicationM.setCmrFirstId(rs.getString(index++));
				applicationM.setCmrLastId(rs.getString(index++));
				applicationM.setDeStartDate(rs.getTimestamp(index++));
				applicationM.setDeLastDate(rs.getTimestamp(index++));
				applicationM.setDeFirstId(rs.getString(index++));
				
				applicationM.setDeLastId(rs.getString(index++));
				applicationM.setCmrExStartDate(rs.getTimestamp(index++));
				applicationM.setCmrExEndDate(rs.getTimestamp(index++));
				applicationM.setCmrExFirstId(rs.getString(index++));
				applicationM.setCmrExLastId(rs.getString(index++));
				
				applicationM.setUwStartDate(rs.getTimestamp(index++));
				applicationM.setUwEndDate(rs.getTimestamp(index++));
				applicationM.setUwFirstId(rs.getString(index++));
				applicationM.setUwLastId(rs.getString(index++));
				applicationM.setPdStartDate(rs.getTimestamp(index++));
				
				applicationM.setPdEndDate(rs.getTimestamp(index++));
				applicationM.setPdFirstId(rs.getString(index++));
				applicationM.setPdLastId(rs.getString(index++));
				applicationM.setEscalateBy(rs.getString(index++));
				applicationM.setEscalateTo(rs.getString(index++));
				
				applicationM.setEscalateDate(rs.getTimestamp(index++));
				applicationM.setApplicationStatus(rs.getString(index++));
				applicationM.setJobState(rs.getString(index++));
				applicationM.setLoanType(rs.getString(index++));
				applicationM.setApplicationType(rs.getString(index++));
				
				applicationM.setScorringResult(rs.getString(index++));
				applicationM.setPriority(rs.getString(index++));
				applicationM.setSrcofcus(rs.getString(index++));
				applicationM.setCmrCode(rs.getString(index++));
				applicationM.setAreaCode(rs.getString(index++));
				
				applicationM.setBookDate(rs.getTimestamp(index++));
				applicationM.setBookBy(rs.getString(index++));
				applicationM.setFinalCreditLimit(rs.getBigDecimal(index++));
				applicationM.setCreditExpiryDate(rs.getTimestamp(index++));
				applicationM.setUwDecision(rs.getString(index++));
				
				applicationM.setPdDecision(rs.getString(index++));
				applicationM.setXcmrOverrideBy(rs.getString(index++));
				applicationM.setXcmrOverrideDate(rs.getTimestamp(index++));
				applicationM.setXcmrDecision(rs.getString(index++));
				applicationM.setUwDecisionReason(rs.getString(index++));
				
				applicationM.setDrawDownFlag(rs.getString(index++));
				applicationM.setDeDecision(rs.getString(index++));
				applicationM.setIdentificationNo(rs.getString(index++));
				applicationM.setPdDecisionReason(rs.getString(index++));
				applicationM.setPolicyVersion(rs.getString(index++));
				
				applicationM.setXuwPolicyException(rs.getString(index++));
				applicationM.setXuwDecision(rs.getString(index++));
				applicationM.setXuwDecisionReason(rs.getString(index++));
				applicationM.setXuwOverrideBy(rs.getString(index++));
				applicationM.setXuwOverrideDate(rs.getTimestamp(index++));
				
				applicationM.setXuwStartDate(rs.getTimestamp(index++));
				applicationM.setXuwEndDate(rs.getTimestamp(index++));
				applicationM.setXuwFirstId(rs.getString(index++));
				applicationM.setXuwLastId(rs.getString(index++));
				applicationM.setBusinessClassId(rs.getString(index++));
				
				applicationM.setGroupAppID(rs.getString(index++));
				applicationM.setAppGroupId(rs.getString(index++));
				applicationM.setAppDate(rs.getTimestamp(index++));
				applicationM.setApplyChannel(rs.getString(index++));
				applicationM.setProjectCode(rs.getString(index++));
				
				applicationM.setExceptionProject(rs.getString(index++));
				applicationM.setExceptionProjectMemo(rs.getString(index++));
				applicationM.setExceptionProjectBy(rs.getString(index++));
				applicationM.setDocListResult(rs.getString(index++));
				applicationM.setDocListNote(rs.getString(index++));
				
				applicationM.setDocListUpdateBy(rs.getString(index++));
				applicationM.setDocListUpdateDate(rs.getTimestamp(index++));
				applicationM.setRecommentCreditLine(rs.getBigDecimal(index++));
				applicationM.setScoringCreditLine(rs.getBigDecimal(index++));
				applicationM.setExceptionCreditLine(rs.getBigDecimal(index++));
				
				applicationM.setFinalCreditLimitRemark(rs.getString(index++));
				applicationM.setCaDecision(rs.getString(index++));
				applicationM.setPolicyExcDocNo(rs.getString(index++));
				applicationM.setCaRemark(rs.getString(index++));
				applicationM.setDocListNotepad(rs.getString(index++));
				
				applicationM.setDcStartDate(rs.getTimestamp(index++));
				applicationM.setDcLastDate(rs.getTimestamp(index++));
				applicationM.setDcFirstId(rs.getString(index++));
				applicationM.setDcLastId(rs.getString(index++));
				applicationM.setCaStartDate(rs.getTimestamp(index++));
				
				applicationM.setCaLastDate(rs.getTimestamp(index++));
				applicationM.setCaFirstId(rs.getString(index++));
				applicationM.setCaLastId(rs.getString(index++));
				applicationM.setDfStartDate(rs.getTimestamp(index++));
				applicationM.setDfLastDate(rs.getTimestamp(index++));
				
				applicationM.setDfFirstId(rs.getString(index++));
				applicationM.setDfLastId(rs.getString(index++));
				applicationM.setFuStartDate(rs.getTimestamp(index++));
				applicationM.setFuLastDate(rs.getTimestamp(index++));
				applicationM.setFuFirstId(rs.getString(index++));
				
				applicationM.setFuLastId(rs.getString(index++));
				applicationM.setCbStartDate(rs.getTimestamp(index++));
				applicationM.setCbLastDate(rs.getTimestamp(index++));
				applicationM.setCbFirstId(rs.getString(index++));
				applicationM.setCbLastId(rs.getString(index++));
				
				applicationM.setVcStartDate(rs.getTimestamp(index++));
				applicationM.setVcLastDate(rs.getTimestamp(index++));
				applicationM.setVcFirstId(rs.getString(index++));
				applicationM.setVcLastId(rs.getString(index++));
				applicationM.setDocRefNo(rs.getString(index++));
				
				applicationM.setPurposeOfLaon(rs.getString(index++));
				applicationM.setProductHolding(rs.getString(index++));
				applicationM.setCapPortNo(rs.getString(index++));
				applicationM.setApproveDate(rs.getTimestamp(index++));
				applicationM.setApproveBy(rs.getString(index++));
				
				applicationM.setReopenFlag(rs.getString(index++));
				applicationM.setApplicationJusifyDate(rs.getDate(index++));
				applicationM.setSaleType(rs.getString(index++));
				applicationM.setRefNo(rs.getString(index++));
				applicationM.setRejectDate(rs.getTimestamp(index++));
				
				applicationM.setRejectBy(rs.getString(index++));
				applicationM.setCancelDate(rs.getTimestamp(index++));
				applicationM.setCancelBy(rs.getString(index++));
				applicationM.setApproveReason(rs.getString(index++));
				applicationM.setLifeCycle(rs.getInt(index++));
				
				applicationM.setCbDecision(rs.getString(index++));
				applicationM.setBlockFlag(rs.getString(index++));
				applicationM.setDcDecision(rs.getString(index++));
				applicationM.setDeSupDecision(rs.getString(index++));
				applicationM.setDeSupFirstId(rs.getString(index++));
				
				applicationM.setDeSupFirstDate(rs.getTimestamp(index++));
				applicationM.setDeSupLastId(rs.getString(index++));
				applicationM.setDeSupLastDate(rs.getTimestamp(index++));
				applicationM.setDcSupDecision(rs.getString(index++));
				applicationM.setDcSupFirstId(rs.getString(index++));
				
				applicationM.setDcSupFirstDate(rs.getTimestamp(index++));
				applicationM.setDcSupLastId(rs.getString(index++));
				applicationM.setDcSupLastDate(rs.getTimestamp(index++));
				applicationM.setVcDecision(rs.getString(index++));
				applicationM.setDF_Decision(rs.getString(index++));
				
				applicationM.setRefAppRecordId(rs.getString(index++));
				applicationM.setJobType(rs.getString(index++));
				applicationM.setCustomerGroup(rs.getString(index++));
				applicationM.setFuDecision(rs.getString(index++));
				applicationM.setFuNewImgReady(rs.getString(index++));
				
				applicationM.setFuNoTimeOutFlag(rs.getString(index++));
				applicationM.setProductFeature(rs.getString(index++));
				applicationM.setDocListResultCode(rs.getString(index++));
				applicationM.setICDCFlag(rs.getString(index++));
				applicationM.setCustReqCreditLine(rs.getBigDecimal(index++));
				
				applicationM.setCurCreditLine(rs.getBigDecimal(index++));
				applicationM.setPoDecision(rs.getString(index++));
				applicationM.setPoFirstId(rs.getString(index++));
				applicationM.setPoStartDate(rs.getTimestamp(index++));
				applicationM.setPoLastId(rs.getString(index++));
				
				applicationM.setPoLastDate(rs.getTimestamp(index++));
				applicationM.setFinalCreditLimitReason(rs.getString(index++));
				applicationM.setAuditFlag(rs.getString(index++));
				applicationM.setVcSupDecision(rs.getString(index++));
				applicationM.setVcSupFirstId(rs.getString(index++));
				
				applicationM.setVcSupLastDate(rs.getTimestamp(index++));
				applicationM.setVcSupLastId(rs.getString(index++));
				applicationM.setVcSupStartDate(rs.getTimestamp(index++));
				applicationM.setFinalAppDecision(rs.getString(index++));
				applicationM.setFinalAppDecisionDate(rs.getTimestamp(index++));
				
				applicationM.setFinalAppDecisionBy(rs.getString(index++));
				applicationM.setLevelID(rs.getString(index++));
				applicationM.setExceptionDoc(rs.getString(index++));
				
				applicationM.setSmsFollowUp(rs.getString(index++));
				applicationM.setSmsFollowUpDate(rs.getTimestamp(index++));
				
				applicationM.setBot5xTotal(rs.getBigDecimal(index++));
				applicationM.setAccountCardId(rs.getString(index++));
				
			}
			
		}catch(Exception e){
			log.fatal(e.getLocalizedMessage());
			throw new PLOrigApplicationException(e.getMessage());
		}finally{
			try{
				closeConnection(conn, rs, ps);
			}catch(Exception e){
				log.fatal(e.getLocalizedMessage());
				throw new PLOrigApplicationException(e.getMessage());
			}
		}		
		return applicationM;
	}

	@Override
	public int updatePLApplicationStatus(WorkflowResponse response ,UserDetailM userM)throws PLOrigApplicationException {
		PreparedStatement ps = null;
		int retrunRows = 0;
		Connection conn = null;
		try{
			conn = getConnection();
			StringBuilder sql = new StringBuilder("");

			sql.append("UPDATE ORIG_APPLICATION SET APPLICATION_STATUS=?, JOB_STATE=?, UPDATE_DATE=SYSDATE, UPDATE_BY=? ");
			sql.append("WHERE APPLICATION_RECORD_ID = ? ");
			
			String dSql = String.valueOf(sql);
			log.debug("Sql=" + dSql);
			ps = conn.prepareStatement(dSql);
			ps.setString(1, response.getAppStatus());
			ps.setString(2, response.getToAtid());
			ps.setString(3, userM.getUserName());
			ps.setString(4, response.getAppRecordID());
			retrunRows = ps.executeUpdate();
			
		}catch(Exception e){
			log.fatal(e.getLocalizedMessage());
			throw new PLOrigApplicationException(e.getMessage());
		}finally{
			try {
				closeConnection(conn, ps);
			}catch(Exception e){
				log.fatal(e.getLocalizedMessage());
				throw new PLOrigApplicationException(e.getMessage());
			}
		}
		return retrunRows;		
	}

	@Override
	public int updatePriority(PLApplicationDataM appDataM)throws PLOrigApplicationException {
		PreparedStatement ps = null;
		int returnRow = 0;
		Connection conn = null;
		try {
			
			conn = getConnection();
			StringBuilder sql = new StringBuilder("");

			sql.append("UPDATE ORIG_APPLICATION SET PRIORITY=?, UPDATE_BY=?, UPDATE_DATE=SYSDATE ");
			sql.append("WHERE APPLICATION_RECORD_ID IN (SELECT APP.APPLICATION_RECORD_ID ");
			sql.append("FROM ORIG_APPLICATION APP, WF_JOBID_MAPPING WJM, WF_WORK_QUEUE WWQ ");
			sql.append("WHERE APP.APPLICATION_RECORD_ID = WJM.APPLICATION_RECORD_ID AND WJM.JOB_ID = WWQ.JOB_ID ");
			sql.append("AND APP.APPLICATION_RECORD_ID = ?) ");
			
			String dSql = String.valueOf(sql);
			log.debug("Sql="+dSql);
			ps = conn.prepareStatement(dSql);
			
			ps.setString(1, appDataM.getPriority());
			ps.setString(2, appDataM.getUpdateBy());
			ps.setString(3, appDataM.getAppRecordID());
			
			returnRow = ps.executeUpdate();
			
		} catch (Exception e) {
			log.fatal(e.getLocalizedMessage());
			throw new PLOrigApplicationException(e.getMessage());
		} finally {
			try {
				closeConnection(conn, ps);
			} catch (Exception e) {
				log.fatal(e.getLocalizedMessage());
				throw new PLOrigApplicationException(e.getMessage());
			}
		}
		return returnRow;
	}

	@Override
	public int selectLifeCycle(String appRecId)
			throws PLOrigApplicationException {
		Connection conn = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		int returnInt = 1;
		try {
			conn = getConnection();
			StringBuilder sql = new StringBuilder("");
			sql.append("SELECT LIFE_CYCLE FROM ORIG_APPLICATION WHERE APPLICATION_RECORD_ID = ?");
			String dSql = String.valueOf(sql);
			log.debug("Sql=" + dSql);
			ps = conn.prepareStatement(dSql);
			ps.setString(1, appRecId);

			rs = ps.executeQuery();
			if (rs.next()) {
				returnInt = rs.getInt(1);
			}
			
			if (returnInt == 0) {
				return 1;
			}
			return returnInt;

		} catch (Exception e) {
			log.fatal(e.getLocalizedMessage());
			throw new PLOrigApplicationException(e.getMessage());
		} finally {
			try {
				closeConnection(conn, rs, ps);
			} catch (Exception e) {
				log.fatal(e.getLocalizedMessage());
				throw new PLOrigApplicationException(e.getMessage());
			}
		}
	}
	
	@Override
	public void updateRoleOrigApplication(PLApplicationDataM appM, UserDetailM userM, String role)throws PLOrigApplicationException {		
		try{
			boolean checkFirst;
			checkFirst = checkFirstRole(appM.getAppRecordID(), role);
			if(checkFirst){
				updateFirstRole(appM, userM, role);
			}else{
				updateLastRole(appM, userM, role);
			}			
		}catch(Exception e){
			log.fatal(e.getLocalizedMessage());
			throw new PLOrigApplicationException(e.getMessage());
		}		
	}
	
	private boolean checkFirstRole(String appRecId, String role) throws PLOrigApplicationException {
		PreparedStatement ps = null;
		ResultSet rs = null;
		Connection conn = null;
		try {
			conn = getConnection();
			StringBuilder sql = new StringBuilder("");
			sql.append("SELECT "+role+"_FIRST_ID ");
			sql.append("FROM ORIG_APPLICATION WHERE APPLICATION_RECORD_ID = ? ");
			sql.append("AND "+role+"_FIRST_ID IS NULL AND "+role+"_START_DATE IS NULL AND "+role+"_DECISION IS NULL");
			String dSql = String.valueOf(sql);
			log.debug("Sql=" + dSql);
			ps = conn.prepareStatement(dSql);
			ps.setString(1, appRecId);

			rs = ps.executeQuery();
			if (rs.next()) {
				return true;
			}			
			return false;
			
		} catch (Exception e) {
			log.fatal(e.getLocalizedMessage());
			throw new PLOrigApplicationException(e.getMessage());
		} finally {
			try {
				closeConnection(conn, rs, ps);
			} catch (Exception e) {
				log.fatal(e.getLocalizedMessage());
				throw new PLOrigApplicationException(e.getMessage());
			}
		}
	}
	
	private void updateFirstRole(PLApplicationDataM appM, UserDetailM userM, String role) throws PLOrigApplicationException {
		PreparedStatement ps = null;
		Connection conn = null;
		try {
			conn = getConnection();
			StringBuilder sql = new StringBuilder("");

			sql.append("UPDATE ORIG_APPLICATION SET "+role+"_FIRST_ID=?, "+role+"_LAST_DATE=SYSDATE, "+role+"_LAST_ID=?, "+role+"_START_DATE=SYSDATE ");
			sql.append("WHERE APPLICATION_RECORD_ID = ? ");
			String dSql = String.valueOf(sql);
			log.debug("Sql=" + dSql);
			ps = conn.prepareStatement(dSql);
			
			ps.setString(1, userM.getUserName());
			ps.setString(2, userM.getUserName());
			ps.setString(3, appM.getAppRecordID());
			
			ps.executeUpdate();

		} catch (Exception e) {
			log.fatal(e.getLocalizedMessage());
			throw new PLOrigApplicationException(e.getMessage());
		} finally {
			try {
				closeConnection(conn, ps);
			} catch (Exception e) {
				log.fatal(e.getLocalizedMessage());
				throw new PLOrigApplicationException(e.getMessage());
			}
		}
	}
	
	private void updateLastRole(PLApplicationDataM appM, UserDetailM userM, String role) throws PLOrigApplicationException {
		PreparedStatement ps = null;
		Connection conn = null;
		try{
			conn = getConnection();
			StringBuilder sql = new StringBuilder("");

			sql.append("UPDATE ORIG_APPLICATION SET "+role+"_LAST_DATE=SYSDATE, "+role+"_LAST_ID=? ");
			sql.append("WHERE APPLICATION_RECORD_ID = ? ");
			String dSql = String.valueOf(sql);
			log.debug("Sql=" + dSql);
			ps = conn.prepareStatement(dSql);
			
			ps.setString(1, userM.getUserName());
			ps.setString(2, appM.getAppRecordID());
			
			ps.executeUpdate();

		} catch (Exception e) {
			log.fatal(e.getLocalizedMessage());
			throw new PLOrigApplicationException(e.getMessage());
		} finally {
			try {
				closeConnection(conn, ps);
			} catch (Exception e) {
				log.fatal(e.getLocalizedMessage());
				throw new PLOrigApplicationException(e.getMessage());
			}
		}
	}

	@Override
	public void updateBlockFlag(PLApplicationDataM applicationM ,UserDetailM userM) throws PLOrigApplicationException{
		PreparedStatement ps = null;
		Connection conn = null;
		try{			
			conn = getConnection();
			StringBuilder sql = new StringBuilder("");

			sql.append("UPDATE ORIG_APPLICATION SET BLOCK_FLAG=?, UPDATE_BY=?, UPDATE_DATE=SYSDATE ");
			sql.append("WHERE APPLICATION_RECORD_ID = ?");
			
			String dSql = String.valueOf(sql);
			log.debug("Sql="+dSql);
			ps = conn.prepareStatement(dSql);
			ps.setString(1, applicationM.getBlockFlag());
			ps.setString(2, userM.getUserName());
			ps.setString(3, applicationM.getAppRecordID());			
			ps.executeUpdate();			
		} catch (Exception e) {
			log.fatal(e.getLocalizedMessage());
			throw new PLOrigApplicationException(e.getMessage());
		} finally {
			try {
				closeConnection(conn, ps);
			} catch (Exception e) {
				log.fatal(e.getLocalizedMessage());
				throw new PLOrigApplicationException(e.getMessage());
			}
		}		
	}

	@Override
	public Vector<String[]> loadCBConsent(String dateFrom, String dateTo) throws PLOrigApplicationException {
		PreparedStatement ps = null;
		ResultSet rs = null;
		Connection conn = null;
		try {

			conn = getConnection();
			StringBuilder sql = new StringBuilder("");
			
			sql.append(" SELECT TO_CHAR(XVR.NCB_CONSENT_REF_NO_DATE,'yyyy-mm-dd hh24:mi:ss') CONSENT_DATE, NCB.CB_IN_QUEUE_DATE, ");
			sql.append(" APP.APPLICATION_NO ,APP.SALE_TYPE , PI.TH_FIRST_NAME ||' '||PI.TH_LAST_NAME, PI.IDNO, TO_CHAR(PI.BIRTH_DATE,'yyyy-mm-dd') BIRTHDATE, ");
			sql.append(" XVR.NCB_REQUESTER, XVR.NCB_RQ_APPROVER, XVR.NCB_CONSENT_REF_NO ");			
			sql.append(" FROM ORIG_PERSONAL_INFO PI, XRULES_VERIFICATION_RESULT XVR, ORIG_APPLICATION APP, NCB_REQ_RESP NCB ");
			sql.append(" WHERE PI.PERSONAL_ID = XVR.PERSONAL_ID AND APP.APPLICATION_RECORD_ID = PI.APPLICATION_RECORD_ID(+) AND NVL(PI.PERSONAL_TYPE, ?) = ? ");  //A, A
			sql.append(" AND TRUNC(XVR.NCB_CONSENT_REF_NO_DATE) BETWEEN TRUNC(TO_DATE(?,'dd-mm-yyyy') ) AND TRUNC(TO_DATE(?, 'dd-mm-yyyy') ) ");  //date,date
			sql.append(" AND NCB.APPLICATION_NO = APP.APPLICATION_NO AND XVR.NCB_CONSENT_REF_NO IS NOT NULL ");
			sql.append(" AND XVR.NCB_TRACKING_CODE = NCB.TRACKING_CODE ");
			sql.append(" ORDER BY XVR.NCB_CONSENT_REF_NO_DATE ");
			
			Vector<String[]> StringVect = new Vector<String[]>();
			
			String dSql = String.valueOf(sql);
			log.debug(dSql);
			ps = conn.prepareStatement(dSql);
			
			ps.setString(1, OrigConstant.PERSONAL_TYPE_APPLICANT);
			ps.setString(2, OrigConstant.PERSONAL_TYPE_APPLICANT);
			ps.setString(3, dateFrom);
			ps.setString(4, dateTo);
			
			rs = ps.executeQuery();
			
			while (rs.next()) {
				int rsIndex = 1;
				int arrIndex = 0;
				String[] result = new String[10];
				
				result[arrIndex++] = rs.getString(rsIndex++);
				result[arrIndex++] = rs.getString(rsIndex++);
				result[arrIndex++] = rs.getString(rsIndex++);
				result[arrIndex++] = rs.getString(rsIndex++);
				result[arrIndex++] = rs.getString(rsIndex++);
				result[arrIndex++] = rs.getString(rsIndex++);
				
				result[arrIndex++] = rs.getString(rsIndex++);
				result[arrIndex++] = rs.getString(rsIndex++);
				result[arrIndex++] = rs.getString(rsIndex++);
				result[arrIndex++] = rs.getString(rsIndex++);
				
				StringVect.add(result);
			}

			return StringVect;

		} catch (Exception e) {
			log.fatal(e.getLocalizedMessage());
			throw new PLOrigApplicationException(e.getMessage());
		} finally {
			try {
				closeConnection(conn, rs, ps);
			} catch (Exception e) {
				log.fatal(e.getLocalizedMessage());
				throw new PLOrigApplicationException(e.getMessage());
			}
		}
	}

	@Override
	public void updatePLApplicationStatus(PLApplicationDataM applicationM ,UserDetailM userM) throws PLOrigApplicationException {
		Connection conn = null;
		PreparedStatement ps = null;
		try {

			conn = getConnection();
			StringBuilder sql = new StringBuilder("");

			sql.append("UPDATE ORIG_APPLICATION SET APPLICATION_STATUS=?, JOB_STATE=?, UPDATE_DATE=SYSDATE, UPDATE_BY=? ");
			sql.append("WHERE APPLICATION_RECORD_ID IN (SELECT APP.APPLICATION_RECORD_ID ");
			sql.append("FROM ORIG_APPLICATION APP, WF_JOBID_MAPPING WJM, WF_WORK_QUEUE WWQ ");
			sql.append("WHERE APP.APPLICATION_RECORD_ID = WJM.APPLICATION_RECORD_ID AND WJM.JOB_ID = WWQ.JOB_ID ");
			sql.append("AND APP.APPLICATION_RECORD_ID = ? AND WWQ.CLAIMED_BY IS NULL) ");
			
			String dSql = String.valueOf(sql);
			log.debug("Sql=" + dSql);
			ps = conn.prepareStatement(dSql);
			ps.setString(1, applicationM.getApplicationStatus());
			ps.setString(2, applicationM.getJobState());
			ps.setString(3, userM.getUserName());
			ps.setString(4, applicationM.getAppRecordID());
			ps.executeUpdate();
			
		} catch (Exception e) {
			log.fatal(e.getLocalizedMessage());
			throw new PLOrigApplicationException(e.getMessage());
		} finally {
			try {
				closeConnection(conn, ps);
			} catch (Exception e) {
				log.fatal(e.getLocalizedMessage());
				throw new PLOrigApplicationException(e.getMessage());
			}
		}
		
	}
	
	@Override
	public void updateReOpenFlag(PLApplicationDataM appM) throws PLOrigApplicationException {
		Connection conn = null;
		PreparedStatement ps = null;
		try {
			
			conn = getConnection();
			StringBuilder sql = new StringBuilder("");

			sql.append("UPDATE ORIG_APPLICATION SET REOPEN_FLAG=?, LIFE_CYCLE=? ");
			sql.append("WHERE APPLICATION_RECORD_ID = ?");
			
			String dSql = String.valueOf(sql);
			log.debug("Sql="+dSql);
			ps = conn.prepareStatement(dSql);
			ps.setString(1, appM.getReopenFlag());
			ps.setInt(2, appM.getLifeCycle());
			ps.setString(3, appM.getAppRecordID());
			
			ps.executeUpdate();
			
		} catch (Exception e) {
			log.fatal(e.getLocalizedMessage());
			throw new PLOrigApplicationException(e.getMessage());
		} finally {
			try {
				closeConnection(conn, ps);
			} catch (Exception e) {
				log.fatal(e.getLocalizedMessage());
				throw new PLOrigApplicationException(e.getMessage());
			}
		}
		
	}

	@Override
	public String loadApplicationStatus(String appRecId) throws PLOrigApplicationException{		
		PreparedStatement ps = null;
		ResultSet rs = null;
		String result = null;
		Connection conn = null;
		try{
			conn = getConnection();
			StringBuilder sql = new StringBuilder("");
			sql.append("SELECT application_status FROM orig_application WHERE application_record_id = ? ");
			
			String dSql = String.valueOf(sql);
			log.debug(dSql);
			ps = conn.prepareStatement(dSql);
			
			ps.setString(1, appRecId);
			
			rs = ps.executeQuery();
			
			if (rs.next()) {
				result = rs.getString(1);
			}
			return result;
		}catch(Exception e){
			log.fatal(e.getLocalizedMessage());
			throw new PLOrigApplicationException(e.getMessage());
		}finally{
			try{
				closeConnection(conn, rs, ps);
			}catch(Exception e){
				log.fatal(e.getLocalizedMessage());
				throw new PLOrigApplicationException(e.getMessage());
			}
		}
	}

	/*@Override
	public PLApplicationDataM loadApplication_IncreaseDecrease(String appRecId, PLApplicationDataM currentAppM) throws PLOrigApplicationException {

		PLApplicationDataM appDataM = selectOrig_Appplication_IncreaseDecrease(appRecId);
		
		//personal + address + cardInfo
		PLOrigPersonalInfoDAO plOrigPersonalInfoDAO = PLORIGDAOFactory.getPLOrigPersonalInfoDAO();
		appDataM.setPersonalInfoVect(plOrigPersonalInfoDAO.loadOrigPersonalInfo_IncreaseDecrease(appRecId, currentAppM));
		
		//load sales info
		PLOrigSalesInfoDAO plOrigSalesInfoDAO = PLORIGDAOFactory.getPLOrigSalesInfoDAO();
		appDataM.setSaleInfo(plOrigSalesInfoDAO.loadPLOrigSaleInfoDataM_IncreaseDecrease(appRecId));
		
		
		
		return appDataM;
	}*/
	
	private PLApplicationDataM selectOrig_Appplication_IncreaseDecrease (String appRecId)throws PLOrigApplicationException {
		Connection conn = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		try {
			conn = getConnection();
			StringBuilder sql = new StringBuilder("");
			sql.append("SELECT APP.PROJECT_CODE, APP.REF_NO, APP.Application_Record_Id FROM ORIG_APPLICATION APP WHERE APPLICATION_RECORD_ID = ?");
			String dSql = String.valueOf(sql);
			log.debug("@@@@@ selectOrig_Appplication_IncreaseDecrease Sql=" + dSql);
			ps = conn.prepareStatement(dSql);
			ps.setString(1, appRecId);

			rs = ps.executeQuery();
			PLApplicationDataM appDataM = new PLApplicationDataM();
			if (rs.next()) {
				int index = 1;
				appDataM.setProjectCode(rs.getString(index++));
				appDataM.setDocRefNo(rs.getString(index++));
				appDataM.setRefAppRecordId(rs.getString(index++));
			}
			
			return appDataM;
			
		} catch (Exception e) {
			log.fatal(e.getLocalizedMessage());
			throw new PLOrigApplicationException(e.getMessage());
		} finally {
			try {
				closeConnection(conn, rs, ps);
			} catch (Exception e) {
				log.fatal(e.getLocalizedMessage());
				throw new PLOrigApplicationException(e.getMessage());
			}
		}
		
	}

	@Override
	public PLApplicationDataM loadAppInFo(String appRecID) throws PLOrigApplicationException{	
		PLApplicationDataM applicationM = new PLApplicationDataM();
		try{
			ApplicationInfoDAO applicationDAO = PLORIGDAOFactory.getApplicationInfoDAO();
			applicationM = applicationDAO.LoadAppInfo(appRecID);
		}catch(Exception e){
			log.fatal("Exception ",e);
			throw new PLOrigApplicationException(e.getMessage());
		}
		return applicationM;
	}
	
	//Praisan Khunkaew for process capport after CA approve
	private void processCapport(PLApplicationDataM applicationM) throws PLOrigApplicationException{
		String capportNo = applicationM.getCapPortNo();
		if(capportNo != null && !"".equals(capportNo)){
			try{
				PLORIGDAOFactory.getPLOrigCapportDAO().updateCapportUsed(capportNo, 
						                                                 applicationM.getAppRecordID(), 
						                                                 applicationM.getBusinessClassId(), 
						                                                 OrigConstant.capportType.INCREASE, 
						                                                 applicationM.getUpdateBy());
			}catch (Exception e){
				e.printStackTrace();
				throw new PLOrigApplicationException(e.getMessage());
			}
		}
	}
	
	private void processCAPoint(PLApplicationDataM applicationM, UserDetailM userM) throws PLOrigApplicationException{
		String action = "";
		if(applicationM.getAppDecision().indexOf(OrigConstant.Action.APPROVE) >= 0){
			action = OrigConstant.Action.APPROVE;
		}else if(applicationM.getAppDecision().indexOf(OrigConstant.Action.OVERRIDE) >= 0){
			action = OrigConstant.Action.OVERRIDE;
		}else if(applicationM.getAppDecision().indexOf(OrigConstant.Action.POLICY_EXCEPTION) >= 0){
			action = OrigConstant.Action.POLICY_EXCEPTION;
		}else if(applicationM.getAppDecision().indexOf(OrigConstant.Action.REJECT) >= 0){
			action = OrigConstant.Action.REJECT;
		}else if(applicationM.getAppDecision().indexOf(OrigConstant.Action.CANCEL) >= 0){
			action = OrigConstant.Action.CANCEL;
		}
		int updateCount = updateOrigApplicationPoint(applicationM, applicationM.getCaPoint(), action, userM);
		if(updateCount == 0){
			insertOrigApplicationPoint(applicationM, applicationM.getCaPoint(), action, userM);
		}
	}
	
	private int updateOrigApplicationPoint(PLApplicationDataM applicationM, double point, String action, UserDetailM userM)throws PLOrigApplicationException{
		int returnRows = 0;
		Connection conn = null;
		PreparedStatement ps = null;
		try {

			conn = getConnection();
			StringBuilder sql = new StringBuilder("");
			
			sql.append("update orig_application_point ap ");
			sql.append("set ap.point=?, ap.point_date=sysdate, ap.action=?, ap.update_by=?, ap.update_date=sysdate ");
			sql.append("where ap.application_record_id = ? and ap.life_cycle = ?");

			String dSql = String.valueOf(sql);
			log.debug("@@@@@ updateOrigApplicationPoint Sql=" + dSql +"|point=" + point+"|action=" + action);
			ps = conn.prepareStatement(dSql);
			
			int index = 1;
			ps.setDouble(index++, point);
			ps.setString(index++, action);
			ps.setString(index++, userM.getUserName());
			ps.setString(index++, applicationM.getAppRecordID());
			ps.setInt(index++, applicationM.getLifeCycle());

			returnRows = ps.executeUpdate();
			
		} catch (Exception e) {
			log.fatal(e.getLocalizedMessage());
			throw new PLOrigApplicationException(e.getMessage());
		} finally {
			try {
				closeConnection(conn, ps);
			} catch (Exception e) {
				log.fatal(e.getLocalizedMessage());
				throw new PLOrigApplicationException(e.getMessage());
			}
		}
		return returnRows;	
	}
	
	private void insertOrigApplicationPoint(PLApplicationDataM applicationM, double point, String action, UserDetailM userM)throws PLOrigApplicationException{
		PreparedStatement ps = null;
		Connection conn = null;
		try {
			
			conn = getConnection();
			StringBuilder sql = new StringBuilder("");
			sql.append("insert into orig_application_point ");
			sql.append("(application_record_id,life_cycle,point,point_date,action,create_by,create_date,update_by,update_date) ");
			sql.append("values (?,?,?,sysdate,?,?,sysdate,?,sysdate)");

			String dSql = String.valueOf(sql);
			log.debug("@@@@@ insertOrigApplicationPoint Sql=" + dSql +"|point=" + point+"|action=" + action);
			ps = conn.prepareStatement(dSql);
			
			ps.setString(1, applicationM.getAppRecordID());
			ps.setInt(2, applicationM.getLifeCycle());
			ps.setDouble(3, point);
			ps.setString(4, action);
			ps.setString(5, userM.getUserName());
			ps.setString(6, userM.getUserName());
			ps.executeUpdate();
			
		}catch (Exception e) {
			log.fatal(e.getLocalizedMessage());
			throw new PLOrigApplicationException(e.getMessage());
		} finally {
			try {
				closeConnection(conn, ps);
			} catch (Exception e) {
				log.fatal(e.getLocalizedMessage());
				throw new PLOrigApplicationException(e.getMessage());
			}
		}
	}
	
	private void processCard(PLApplicationDataM applicationM) throws PLOrigApplicationException{
		PLOrigCardInformationDAO plOrigCardInformationDAO = PLORIGDAOFactory.getPLOrigCardInformationDAO();
		PLPersonalInfoDataM personM = applicationM.getPLPersonalInfoDataM(OrigConstant.PERSONAL_TYPE_APPLICANT);
		plOrigCardInformationDAO.saveUpdateOrigCard(personM.getCardInformation(), personM.getPersonalID(), applicationM);
	}
	
	public void processAllApproveDecision(PLApplicationDataM applicationM, UserDetailM userM)throws PLOrigApplicationException{
		try{
			this.processCapport(applicationM);
			this.processCAPoint(applicationM, userM);
			this.processCard(applicationM);
			PLORIGDAOFactory.getPLOrigAccountDAO().saveUpdateAccountByPLSQL(applicationM);
		}catch(Exception e){
			e.printStackTrace();
			throw new PLOrigApplicationException(e.getMessage());
		}
	}

	@Override
	public PLApplicationDataM loadOrigApplicationAppNo(String appNo) throws PLOrigApplicationException {		
		try{
			PLApplicationDataM prmDataM = selectOrig_Applicaiton_appNo(appNo);
			PLApplicationDataM appDataM = loadOrigApplication(prmDataM.getAppRecordID());
			return appDataM;
		}catch(Exception e){
			log.fatal(e.getLocalizedMessage());
			throw new PLOrigApplicationException(e.getMessage());
		}		
	}
	
	private PLApplicationDataM selectOrig_Applicaiton_appNo (String appNo) throws PLOrigApplicationException {
		Connection conn = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		try {
			conn = getConnection();
			StringBuilder sql = new StringBuilder("");
			sql.append("SELECT APPLICATION_RECORD_ID, APPLICATION_NO, ACCOUNT_NO, OFFICE_CODE, DEALER_CODE ");
			sql.append(", SELLER_CODE, SALEMAN_CODE, CREATE_BY, CREATE_DATE, UPDATE_BY ");
			sql.append(", UPDATE_DATE, CONSENT, CONSENT_DATE, CMR_START_DATE, CMR_END_DATE ");
			sql.append(", CMR_FIRST_ID, CMR_LAST_ID, DE_START_DATE, DE_LAST_DATE, DE_FIRST_ID ");
			sql.append(", DE_LAST_ID, CMR_EX_START_DATE, CMR_EX_END_DATE, CMR_EX_FIRST_ID, CMR_EX_LAST_ID ");
			sql.append(", UW_START_DATE, UW_END_DATE, UW_FIRST_ID, UW_LAST_ID, PD_START_DATE ");
			sql.append(", PD_END_DATE, PD_FIRST_ID, PD_LAST_ID, ESCALATE_BY, ESCALATE_TO ");
			sql.append(", ESCALATE_DATE, APPLICATION_STATUS, JOB_STATE, LOAN_TYPE, APPLICATION_TYPE ");
			sql.append(", SCORING_RESULT, PRIORITY, SRCOFCUS, CMR_CODE, AREA_CODE ");
			sql.append(", BOOKING_DATE, BOOKING_BY, FINAL_CREDIT_LIMIT, CREDIT_EXPIRY_DATE, UW_DECISION ");
			sql.append(", PD_DECISION , XCMR_OVERRIDE_BY, XCMR_OVERRIDE_DATE, XCMR_DECISION, UW_DECISION_REASON ");
			sql.append(", DRAW_DOWN_FLAG, DE_DECISION, IDENTIFICATION_NO, PD_DECISION_REASON, POLICY_VERSION ");
			sql.append(", XUW_POLICY_EXCEPTION, XUW_DECISION, XUW_DECISION_REASON, XUW_OVERRIDE_BY, XUW_OVERRIDE_DATE ");
			sql.append(", XUW_START_DATE, XUW_END_DATE, XUW_FIRST_ID, XUW_LAST_ID, BUSINESS_CLASS_ID ");
			sql.append(", GROUP_APP_ID, APPLICATION_GROUP_ID, APPLICATION_DATE, APPLY_CHANNEL, PROJECT_CODE ");
			sql.append(", EXCEPTION_PROJECT , EXCEPTION_PROJECT_MEMO, EXCEPTION_PROJECT_BY, DOC_LIST_RESULT, DOC_LIST_NOTE ");
			sql.append(", DOC_LIST_UPDATE_BY, DOC_LIST_UPDATE_DATE, RECOMMENT_CREDIT_LINE, SCORING_CREDIT_LINE, EXCEPTION_CREDIT_LINE ");
			sql.append(", FINAL_CREDIT_LIMIT_REMARK, CA_DECISION, POLICY_EXCEPTION_DOC_NO, CA_REMARK, DOC_LIST_NOTEPAD ");
			sql.append(", DC_START_DATE, DC_LAST_DATE, DC_FIRST_ID, DC_LAST_ID, CA_START_DATE ");
			sql.append(", CA_LAST_DATE, CA_FIRST_ID, CA_LAST_ID, DF_START_DATE, DF_LAST_DATE ");
			sql.append(", DF_FIRST_ID, DF_LAST_ID, FU_START_DATE, FU_LAST_DATE, FU_FIRST_ID ");
			sql.append(", FU_LAST_ID, CB_START_DATE, CB_LAST_DATE, CB_FIRST_ID, CB_LAST_ID ");
			sql.append(", VC_START_DATE, VC_LAST_DATE, VC_FIRST_ID, VC_LAST_ID, DOCUMENT_REF_NO ");
			sql.append(", PURPOSE_OF_LOAN, PRODUCT_HOLDING, CAP_PORT_NO, APPROVE_DATE, APPROVE_BY ");
			sql.append(", REOPEN_FLAG, APPLICATION_JUSIFY_DATE, SALE_TYPE, REF_NO, REJECT_DATE ");
			sql.append(", REJECT_BY , CANCEL_DATE, CANCEL_BY, APPROVE_REASON, LIFE_CYCLE ");
			sql.append(", CB_DECISION , BLOCK_FLAG, DC_DECISION, DE_SUP_DECISION, DE_SUP_FIRST_ID ");
			sql.append(", DE_SUP_START_DATE , DE_SUP_LAST_ID, DE_SUP_LAST_DATE, DC_SUP_DECISION, DC_SUP_FIRST_ID ");
			sql.append(", DC_SUP_START_DATE , DC_SUP_LAST_ID, DC_SUP_LAST_DATE, VC_DECISION, DF_DECISION ");
			sql.append(", REF_APPLICATION_RECORD_ID, JOB_TYPE, CUSTOMER_GROUP, FU_DECISION, FU_NEW_IMG_READY ");
			sql.append(", FU_NO_TIME_OUT_FLAG, PRODUCT_FEATURE, DOC_LIST_RESULT_CODE, ICDC_FLAG, CUST_REQUEST_CREDIT_LINE ");
			sql.append(", CURRENT_CREDIT_LINE, PO_DECISION, PO_FIRST_ID, PO_START_DATE, PO_LAST_ID ");
			sql.append(", PO_LAST_DATE, FINAL_CREDIT_LIMIT_REASON, AUDIT_LOG_FLAG, VC_SUP_DECISION, VC_SUP_FIRST_ID ");
			sql.append(", VC_SUP_LAST_DATE, VC_SUP_LAST_ID, VC_SUP_START_DATE, FINAL_APP_DECISION, FINAL_APP_DECISION_DATE ");
			sql.append(", FINAL_APP_DECISION_BY, DLA_LV ");
			
			sql.append(" FROM ORIG_APPLICATION WHERE APPLICATION_NO = ? ");
			String dSql = String.valueOf(sql);
			log.debug("Sql=" + dSql);
			ps = conn.prepareStatement(dSql);
			ps.setString(1, appNo);

			rs = ps.executeQuery();
			PLApplicationDataM appDataM = new PLApplicationDataM();
			if (rs.next()) {
				int index = 1;
				
				appDataM.setAppRecordID(rs.getString(index++));
				appDataM.setApplicationNo(rs.getString(index++));
				appDataM.setAccountNo(rs.getString(index++));
				appDataM.setOfficeCode(rs.getString(index++));
				appDataM.setDealerCode(rs.getString(index++));
				
				appDataM.setSellerCode(rs.getString(index++));
				appDataM.setSalesManCode(rs.getString(index++));
				appDataM.setCreateBy(rs.getString(index++));
				appDataM.setCreateDate(rs.getTimestamp(index++));
				appDataM.setUpdateBy(rs.getString(index++));
				
				appDataM.setUpdateDate(rs.getTimestamp(index++));
				appDataM.setConsent(rs.getString(index++));
				appDataM.setConsentDate(rs.getTimestamp(index++));
				appDataM.setCmrStartDate(rs.getTimestamp(index++));
				appDataM.setCmrEndDate(rs.getTimestamp(index++));
				
				appDataM.setCmrFirstId(rs.getString(index++));
				appDataM.setCmrLastId(rs.getString(index++));
				appDataM.setDeStartDate(rs.getTimestamp(index++));
				appDataM.setDeLastDate(rs.getTimestamp(index++));
				appDataM.setDeFirstId(rs.getString(index++));
				
				appDataM.setDeLastId(rs.getString(index++));
				appDataM.setCmrExStartDate(rs.getTimestamp(index++));
				appDataM.setCmrExEndDate(rs.getTimestamp(index++));
				appDataM.setCmrExFirstId(rs.getString(index++));
				appDataM.setCmrExLastId(rs.getString(index++));
				
				appDataM.setUwStartDate(rs.getTimestamp(index++));
				appDataM.setUwEndDate(rs.getTimestamp(index++));
				appDataM.setUwFirstId(rs.getString(index++));
				appDataM.setUwLastId(rs.getString(index++));
				appDataM.setPdStartDate(rs.getTimestamp(index++));
				
				appDataM.setPdEndDate(rs.getTimestamp(index++));
				appDataM.setPdFirstId(rs.getString(index++));
				appDataM.setPdLastId(rs.getString(index++));
				appDataM.setEscalateBy(rs.getString(index++));
				appDataM.setEscalateTo(rs.getString(index++));
				
				appDataM.setEscalateDate(rs.getTimestamp(index++));
				appDataM.setApplicationStatus(rs.getString(index++));
				appDataM.setJobState(rs.getString(index++));
				appDataM.setLoanType(rs.getString(index++));
				appDataM.setApplicationType(rs.getString(index++));
				
				appDataM.setScorringResult(rs.getString(index++));
				appDataM.setPriority(rs.getString(index++));
				appDataM.setSrcofcus(rs.getString(index++));
				appDataM.setCmrCode(rs.getString(index++));
				appDataM.setAreaCode(rs.getString(index++));
				
				appDataM.setBookDate(rs.getTimestamp(index++));
				appDataM.setBookBy(rs.getString(index++));
				appDataM.setFinalCreditLimit(rs.getBigDecimal(index++));
				appDataM.setCreditExpiryDate(rs.getTimestamp(index++));
				appDataM.setUwDecision(rs.getString(index++));
				
				appDataM.setPdDecision(rs.getString(index++));
				appDataM.setXcmrOverrideBy(rs.getString(index++));
				appDataM.setXcmrOverrideDate(rs.getTimestamp(index++));
				appDataM.setXcmrDecision(rs.getString(index++));
				appDataM.setUwDecisionReason(rs.getString(index++));
				
				appDataM.setDrawDownFlag(rs.getString(index++));
				appDataM.setDeDecision(rs.getString(index++));
				appDataM.setIdentificationNo(rs.getString(index++));
				appDataM.setPdDecisionReason(rs.getString(index++));
				appDataM.setPolicyVersion(rs.getString(index++));
				
				appDataM.setXuwPolicyException(rs.getString(index++));
				appDataM.setXuwDecision(rs.getString(index++));
				appDataM.setXuwDecisionReason(rs.getString(index++));
				appDataM.setXuwOverrideBy(rs.getString(index++));
				appDataM.setXuwOverrideDate(rs.getTimestamp(index++));
				
				appDataM.setXuwStartDate(rs.getTimestamp(index++));
				appDataM.setXuwEndDate(rs.getTimestamp(index++));
				appDataM.setXuwFirstId(rs.getString(index++));
				appDataM.setXuwLastId(rs.getString(index++));
				appDataM.setBusinessClassId(rs.getString(index++));
				
				appDataM.setGroupAppID(rs.getString(index++));
				appDataM.setAppGroupId(rs.getString(index++));
				appDataM.setAppDate(rs.getTimestamp(index++));
				appDataM.setApplyChannel(rs.getString(index++));
				appDataM.setProjectCode(rs.getString(index++));
				
				appDataM.setExceptionProject(rs.getString(index++));
				appDataM.setExceptionProjectMemo(rs.getString(index++));
				appDataM.setExceptionProjectBy(rs.getString(index++));
				appDataM.setDocListResult(rs.getString(index++));
				appDataM.setDocListNote(rs.getString(index++));
				
				appDataM.setDocListUpdateBy(rs.getString(index++));
				appDataM.setDocListUpdateDate(rs.getTimestamp(index++));
				appDataM.setRecommentCreditLine(rs.getBigDecimal(index++));
				appDataM.setScoringCreditLine(rs.getBigDecimal(index++));
				appDataM.setExceptionCreditLine(rs.getBigDecimal(index++));
				
				appDataM.setFinalCreditLimitRemark(rs.getString(index++));
				appDataM.setCaDecision(rs.getString(index++));
				appDataM.setPolicyExcDocNo(rs.getString(index++));
				appDataM.setCaRemark(rs.getString(index++));
				appDataM.setDocListNotepad(rs.getString(index++));
				
				appDataM.setDcStartDate(rs.getTimestamp(index++));
				appDataM.setDcLastDate(rs.getTimestamp(index++));
				appDataM.setDcFirstId(rs.getString(index++));
				appDataM.setDcLastId(rs.getString(index++));
				appDataM.setCaStartDate(rs.getTimestamp(index++));
				
				appDataM.setCaLastDate(rs.getTimestamp(index++));
				appDataM.setCaFirstId(rs.getString(index++));
				appDataM.setCaLastId(rs.getString(index++));
				appDataM.setDfStartDate(rs.getTimestamp(index++));
				appDataM.setDfLastDate(rs.getTimestamp(index++));
				
				appDataM.setDfFirstId(rs.getString(index++));
				appDataM.setDfLastId(rs.getString(index++));
				appDataM.setFuStartDate(rs.getTimestamp(index++));
				appDataM.setFuLastDate(rs.getTimestamp(index++));
				appDataM.setFuFirstId(rs.getString(index++));
				
				appDataM.setFuLastId(rs.getString(index++));
				appDataM.setCbStartDate(rs.getTimestamp(index++));
				appDataM.setCbLastDate(rs.getTimestamp(index++));
				appDataM.setCbFirstId(rs.getString(index++));
				appDataM.setCbLastId(rs.getString(index++));
				
				appDataM.setVcStartDate(rs.getTimestamp(index++));
				appDataM.setVcLastDate(rs.getTimestamp(index++));
				appDataM.setVcFirstId(rs.getString(index++));
				appDataM.setVcLastId(rs.getString(index++));
				appDataM.setDocRefNo(rs.getString(index++));
				
				appDataM.setPurposeOfLaon(rs.getString(index++));
				appDataM.setProductHolding(rs.getString(index++));
				appDataM.setCapPortNo(rs.getString(index++));
				appDataM.setApproveDate(rs.getTimestamp(index++));
				appDataM.setApproveBy(rs.getString(index++));
				
				appDataM.setReopenFlag(rs.getString(index++));
				appDataM.setApplicationJusifyDate(rs.getDate(index++));
				appDataM.setSaleType(rs.getString(index++));
				appDataM.setRefNo(rs.getString(index++));
				appDataM.setRejectDate(rs.getTimestamp(index++));
				
				appDataM.setRejectBy(rs.getString(index++));
				appDataM.setCancelDate(rs.getTimestamp(index++));
				appDataM.setCancelBy(rs.getString(index++));
				appDataM.setApproveReason(rs.getString(index++));
				appDataM.setLifeCycle(rs.getInt(index++));
				
				appDataM.setCbDecision(rs.getString(index++));
				appDataM.setBlockFlag(rs.getString(index++));
				appDataM.setDcDecision(rs.getString(index++));
				appDataM.setDeSupDecision(rs.getString(index++));
				appDataM.setDeSupFirstId(rs.getString(index++));
				
				appDataM.setDeSupFirstDate(rs.getTimestamp(index++));
				appDataM.setDeSupLastId(rs.getString(index++));
				appDataM.setDeSupLastDate(rs.getTimestamp(index++));
				appDataM.setDcSupDecision(rs.getString(index++));
				appDataM.setDcSupFirstId(rs.getString(index++));
				
				appDataM.setDcSupFirstDate(rs.getTimestamp(index++));
				appDataM.setDcSupLastId(rs.getString(index++));
				appDataM.setDcSupLastDate(rs.getTimestamp(index++));
				appDataM.setVcDecision(rs.getString(index++));
				appDataM.setDF_Decision(rs.getString(index++));
				
				appDataM.setRefAppRecordId(rs.getString(index++));
				appDataM.setJobType(rs.getString(index++));
				appDataM.setCustomerGroup(rs.getString(index++));
				appDataM.setFuDecision(rs.getString(index++));
				appDataM.setFuNewImgReady(rs.getString(index++));
				
				appDataM.setFuNoTimeOutFlag(rs.getString(index++));
				appDataM.setProductFeature(rs.getString(index++));
				appDataM.setDocListResultCode(rs.getString(index++));
				appDataM.setICDCFlag(rs.getString(index++));
				appDataM.setCustReqCreditLine(rs.getBigDecimal(index++));
				
				appDataM.setCurCreditLine(rs.getBigDecimal(index++));
				appDataM.setPoDecision(rs.getString(index++));
				appDataM.setPoFirstId(rs.getString(index++));
				appDataM.setPoStartDate(rs.getTimestamp(index++));
				appDataM.setPoLastId(rs.getString(index++));
				
				appDataM.setPoLastDate(rs.getTimestamp(index++));
				appDataM.setFinalCreditLimitReason(rs.getString(index++));
				appDataM.setAuditFlag(rs.getString(index++));
				appDataM.setVcSupDecision(rs.getString(index++));
				appDataM.setVcSupFirstId(rs.getString(index++));
				
				appDataM.setVcSupLastDate(rs.getTimestamp(index++));
				appDataM.setVcSupLastId(rs.getString(index++));
				appDataM.setVcSupStartDate(rs.getTimestamp(index++));
				appDataM.setFinalAppDecision(rs.getString(index++));
				appDataM.setFinalAppDecisionDate(rs.getTimestamp(index++));
				
				appDataM.setFinalAppDecisionBy(rs.getString(index++));
				appDataM.setLevelID(rs.getString(index++));
				
			}
			
			return appDataM;
			
		} catch (Exception e) {
			log.fatal(e.getLocalizedMessage());
			throw new PLOrigApplicationException(e.getMessage());
		} finally {
			try {
				closeConnection(conn, rs, ps);
			} catch (Exception e) {
				log.fatal(e.getLocalizedMessage());
				throw new PLOrigApplicationException(e.getMessage());
			}
		}
		
	}

	@Override
	public Vector<ApplicationDuplicateM> GetDuplicateApplication(String idNo,String appRecordID, String gProduct, String gJobstate)throws PLOrigApplicationException {
		PreparedStatement ps = null;
		ResultSet rs = null;
		Connection conn = null;
		StringBuilder sql = new StringBuilder();
		ApplicationDuplicateM appDuplicateM = null;
		Vector<ApplicationDuplicateM> appDupVect = new Vector<ApplicationDuplicateM>();
		try{
			conn = getConnection();
			sql.append(" SELECT A.APPLICATION_RECORD_ID APPLICATION_RECORD_ID, A.APPLICATION_NO APPLICATION_NO,A.BUSINESS_CLASS_ID BUSINESS_CLASS_ID");
			sql.append(",P.IDNO IDNO,A.JOB_STATE JOB_STATE,A.APPLICATION_STATUS APPLICATION_STATUS,WR.ROLE_NAME ROLE_NAME , ");
			sql.append(" A.BLOCK_FLAG BLOCK_FLAG ,WAT.ACTIVITY_TYPE ACTIVITY_TYPE ");
			sql.append(" FROM ORIG_APPLICATION A,ORIG_PERSONAL_INFO P,WF_ACTIVITY_TEMPLATE WAT,WF_ROLE WR ");
			sql.append(" WHERE P.IDNO = ? AND P.PERSONAL_TYPE = ? ");
			sql.append(" AND A.APPLICATION_RECORD_ID = P.APPLICATION_RECORD_ID AND WAT.ATID = A.JOB_STATE AND WAT.ROLE_ID = WR.ROLE_ID ");
			if(!OrigUtil.isEmptyString(appRecordID)) sql.append(" AND A.APPLICATION_RECORD_ID <> ? ");			
			if(!OrigUtil.isEmptyString(gJobstate)){			
				sql.append(" AND A.JOB_STATE NOT IN(" );				
				sql.append(splitParameterIN(gJobstate));				
				sql.append(	") ");
			}			
			if(!OrigUtil.isEmptyString(gProduct)){			
				sql.append("AND A.BUSINESS_CLASS_ID IN (SELECT BGM.BUS_CLASS_ID FROM BUS_CLASS_GRP_MAP BGM WHERE BGM.BUS_GRP_ID IN(" );				
				sql.append(splitParameterIN(gProduct));				
				sql.append(	")) ");
			}		
			log.debug("[GetDuplicateApplication] sql "+sql);			
			int index  = 0;			
			ps = conn.prepareStatement(sql.toString());
			ps.setString(++index, idNo);
			ps.setString(++index, PLPersonalInfoDataM.PersonalType.PERSONAL_TYPE_APPLICANT);
			if(!OrigUtil.isEmptyString(appRecordID))  ps.setString(++index, appRecordID);
			if(!OrigUtil.isEmptyString(gJobstate)){
				String [] job = gJobstate.split(",");
				for(int i=0; i<job.length; i++){
					ps.setString(++index,(String)job[i]);
				}
			}
			if(!OrigUtil.isEmptyString(gProduct)){
				String [] bus = gProduct.split(",");
				for(int i=0; i<bus.length; i++){
					ps.setString(++index,(String)bus[i]);
				}
			}						
			rs = ps.executeQuery();
			while(rs.next()){
				appDuplicateM = new ApplicationDuplicateM();
				appDuplicateM.setAppRecordID(rs.getString("APPLICATION_RECORD_ID"));
				appDuplicateM.setRoleState(rs.getString("ROLE_NAME"));
				appDuplicateM.setBlockFlag(rs.getString("BLOCK_FLAG"));
				appDuplicateM.setActivityState(rs.getString("ACTIVITY_TYPE"));
				appDuplicateM.setBusinessClassId(rs.getString("BUSINESS_CLASS_ID"));
				appDupVect.add(appDuplicateM);
			}
		} catch (Exception e) {
			log.fatal(e.getLocalizedMessage());
			throw new PLOrigApplicationException(e.getMessage());
		} finally {
			try {
				closeConnection(conn, rs, ps);
			} catch (Exception e) {
				log.fatal(e.getLocalizedMessage());
				throw new PLOrigApplicationException(e.getMessage());
			}
		}		
		return appDupVect;
	}

	@Override
	public Vector<String[]> loadAuditTrailField(String SubFormID) throws PLOrigApplicationException {
		Connection conn = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		try {
			conn = getConnection();
			StringBuilder sql = new StringBuilder("");
			sql.append("SELECT SMF.FIELD_NAME, SMF.MODEL_NAME, SMF.MODEL_ATTRIBUTE_NAME ");
			sql.append("FROM SC_MANDATORY_FIELDS SMF ");
			sql.append("WHERE SMF.AUDIT_FLAG = ? AND SMF.FORM_NAME_ID = ? ");
			
			String dSql = String.valueOf(sql);
			log.debug("Sql=" + dSql);
			ps = conn.prepareStatement(dSql);
			
			ps.setString(1, OrigConstant.FLAG_Y);
			ps.setString(2, SubFormID);

			rs = ps.executeQuery();
			
			Vector<String[]> AuditField = new Vector<String[]>();
			while(rs.next()){
				int index = 1;
				String Resutl[] = new String[3];
				Resutl[0] = rs.getString(index++);
				Resutl[1] = rs.getString(index++);
				Resutl[2] = rs.getString(index++);
				AuditField.add(Resutl);
			}
			
			return AuditField;
			
		} catch (Exception e) {
			log.fatal(e.getLocalizedMessage());
			throw new PLOrigApplicationException(e.getMessage());
		} finally {
			try {
				closeConnection(conn, rs, ps);
			} catch (Exception e) {
				log.fatal(e.getLocalizedMessage());
				throw new PLOrigApplicationException(e.getMessage());
			}
		}
	}

	@Override
	public String loadBlockFlag(String appRecID) throws PLOrigApplicationException{
		PreparedStatement ps = null;
		ResultSet rs = null;
		String FLAG = null;		
		Connection conn = null;
		try{
			conn = getConnection();
			StringBuilder sql = new StringBuilder("");
			sql.append("SELECT BLOCK_FLAG FROM ORIG_APPLICATION WHERE APPLICATION_RECORD_ID = ?");
			
			String dSql = String.valueOf(sql);
			log.debug("Sql=" + dSql);
			ps = conn.prepareStatement(dSql);
			
			int index = 1;
			ps.setString(index++, appRecID);

			rs = ps.executeQuery();
			
			if(rs.next()){
				FLAG =  rs.getString(1);
			}
		}catch(Exception e){
			log.fatal(e.getLocalizedMessage());
			throw new PLOrigApplicationException(e.getMessage());
		}finally{
			try{
				closeConnection(conn, rs, ps);
			}catch(Exception e){
				log.fatal(e.getLocalizedMessage());
				throw new PLOrigApplicationException(e.getMessage());
			}
		}
		return FLAG;
	}

	@Override
	public String loadJobID(String appRecId) throws PLOrigApplicationException {
		PreparedStatement ps = null;
		ResultSet rs = null;
		Connection conn = null;
		try {
			conn = getConnection();
			StringBuilder sql = new StringBuilder("");
			sql.append("SELECT JM.JOB_ID FROM WF_JOBID_MAPPING JM ");
			sql.append("WHERE JM.JOB_STATUS = 'ACTIVE' AND JM.APPLICATION_RECORD_ID = ?");
			
			String dSql = String.valueOf(sql);
			log.debug("Sql=" + dSql);
			ps = conn.prepareStatement(dSql);
			
			ps.setString(1, appRecId);

			rs = ps.executeQuery();
			
			if(rs.next()){
				return rs.getString(1);
			}
			
			return null;
			
		} catch (Exception e) {
			log.fatal(e.getLocalizedMessage());
			throw new PLOrigApplicationException(e.getMessage());
		} finally {
			try {
				closeConnection(conn, rs, ps);
			} catch (Exception e) {
				log.fatal(e.getLocalizedMessage());
				throw new PLOrigApplicationException(e.getMessage());
			}
		}
	}

	@Override
	public BigDecimal loadCreditLine(String projectCode) throws PLOrigApplicationException {
		Connection conn = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		try {
			conn = getConnection();
			StringBuilder sql = new StringBuilder("");
			sql.append("SELECT PC.CREDIT_LINE FROM MS_PROJECT_CODE PC WHERE PC.PROJECT_CODE = ?");
			
			String dSql = String.valueOf(sql);
			log.debug("Sql=" + dSql);
			ps = conn.prepareStatement(dSql);
			
			ps.setString(1, projectCode);

			rs = ps.executeQuery();
			
			if(rs.next()){
				return rs.getBigDecimal(1);
			}
			return new BigDecimal(0);
			
		} catch (Exception e) {
			log.fatal(e.getLocalizedMessage());
			throw new PLOrigApplicationException(e.getMessage());
		} finally {
			try {
				closeConnection(conn, rs, ps);
			} catch (Exception e) {
				log.fatal(e.getLocalizedMessage());
				throw new PLOrigApplicationException(e.getMessage());
			}
		}
		
	}

	@Override
	public Vector<String> loadBlockApplication(String appRecID) throws PLOrigApplicationException{		
		PreparedStatement ps = null;
		ResultSet rs = null;
		Connection conn = null;
		Vector<String> sVect = new Vector<String>();
		try {
			conn = getConnection();
			StringBuilder SQL = new StringBuilder("");
			
			SQL.append(" SELECT ");
			SQL.append("     APP.APPLICATION_RECORD_ID APPLICATION_RECORD_ID ");
			SQL.append(" FROM ");
			SQL.append("     ORIG_APPLICATION APP, ");
			SQL.append("     ORIG_PERSONAL_INFO PI, ");
			SQL.append("     WF_JOBID_MAPPING JM ");
			SQL.append(" WHERE ");
			SQL.append("     APP.APPLICATION_RECORD_ID = PI.APPLICATION_RECORD_ID ");
			SQL.append(" AND NVL(PI.PERSONAL_TYPE , ?) = ? ");
			SQL.append(" AND APP.APPLICATION_RECORD_ID = JM.APPLICATION_RECORD_ID ");
			SQL.append(" AND JM.JOB_STATUS = 'ACTIVE' ");
			SQL.append(" AND APP.BLOCK_FLAG = ? ");
			SQL.append(" AND PI.IDNO = ");
			SQL.append("     ( ");
			SQL.append("         SELECT ");
			SQL.append("             IDNO ");
			SQL.append("         FROM ");
			SQL.append("             ORIG_APPLICATION SAPP, ");
			SQL.append("             ORIG_PERSONAL_INFO SPI ");
			SQL.append("         WHERE ");
			SQL.append("             SAPP.APPLICATION_RECORD_ID = SPI.APPLICATION_RECORD_ID ");
			SQL.append("         AND SPI.PERSONAL_TYPE = ? ");
			SQL.append("         AND SAPP.APPLICATION_RECORD_ID = ? ");
			SQL.append("     ) ");
			SQL.append(" AND APP.APPLICATION_RECORD_ID != ? ");
			
			String dSql = String.valueOf(SQL);
			log.debug("Sql=" + dSql);
			
			ps = conn.prepareStatement(dSql);
			int index = 1;
			ps.setString(index++, OrigConstant.PERSONAL_TYPE_APPLICANT);
			ps.setString(index++, OrigConstant.PERSONAL_TYPE_APPLICANT);
			ps.setString(index++, OrigConstant.FLAG_B);
			ps.setString(index++, OrigConstant.PERSONAL_TYPE_APPLICANT);
			ps.setString(index++, appRecID);
			ps.setString(index++, appRecID);

			rs = ps.executeQuery();
			while(rs.next()){
				sVect.add(rs.getString("APPLICATION_RECORD_ID"));
			}			
		}catch(Exception e){
			log.fatal(e.getLocalizedMessage());
			throw new PLOrigApplicationException(e.getMessage());
		}finally{
			try{
				closeConnection(conn, rs, ps);
			}catch(Exception e){
				log.fatal(e.getLocalizedMessage());
				throw new PLOrigApplicationException(e.getMessage());
			}
		}
		return sVect;
	}

	@Override
	public void updateFinalAppDecision(PLApplicationDataM appM) throws PLOrigApplicationException {
		Connection conn = null;
		PreparedStatement ps = null;
		try {
			
			conn = getConnection();
			StringBuilder sql = new StringBuilder("");

			sql.append("UPDATE ORIG_APPLICATION SET FINAL_APP_DECISION=?, FINAL_APP_DECISION_DATE=?, FINAL_APP_DECISION_BY=? ");
			sql.append("WHERE APPLICATION_RECORD_ID=?");
			
			String dSql = String.valueOf(sql);
			log.debug("Sql="+dSql);
			ps = conn.prepareStatement(dSql);
			ps.setString(1, appM.getFinalAppDecision());
			ps.setTimestamp(2, appM.getFinalAppDecisionDate());
			ps.setString(3, appM.getFinalAppDecisionBy());
			ps.setString(4, appM.getAppRecordID());
			
			ps.executeUpdate();
			
		} catch (Exception e) {
			log.fatal(e.getLocalizedMessage());
			throw new PLOrigApplicationException(e.getMessage());
		} finally {
			try {
				closeConnection(conn, ps);
			} catch (Exception e) {
				log.fatal(e.getLocalizedMessage());
				throw new PLOrigApplicationException(e.getMessage());
			}
		}
		
	}
	@Override
	public void updateAppStatusNotJoinWF(PLApplicationDataM applicationM, UserDetailM userM) throws PLOrigApplicationException{
		PreparedStatement ps = null;
		Connection conn = null;
		try{			
			conn = getConnection();
			StringBuilder sql = new StringBuilder("");

			sql.append(" update ORIG_APPLICATION app set app.APPLICATION_STATUS=?, app.UPDATE_BY=?, app.UPDATE_DATE=SYSDATE, ");
			sql.append(" app.job_state=? ");
			sql.append(" where app.APPLICATION_RECORD_ID = ?");
			
			String dSql = String.valueOf(sql);
			log.debug("Sql="+dSql);
			ps = conn.prepareStatement(dSql);
			ps.setString(1, applicationM.getApplicationStatus());
			ps.setString(2, userM.getUserName());
			ps.setString(3, applicationM.getJobState());
			ps.setString(4, applicationM.getAppRecordID());
			
			ps.executeUpdate();			
		}catch(Exception e){
			log.fatal(e.getLocalizedMessage());
			throw new PLOrigApplicationException(e.getMessage());
		}finally{
			try{
				closeConnection(conn, ps);
			}catch(Exception e){
				log.fatal(e.getLocalizedMessage());
				throw new PLOrigApplicationException(e.getMessage());
			}
		}		
	}
	
	@Override
	public void UpdateRetrieveNewImage(String appRecID)throws PLOrigApplicationException {
		PreparedStatement ps = null;
		Connection conn = null;
		try{			
			conn = getConnection();
			StringBuilder sql = new StringBuilder("");
				sql.append(" UPDATE ORIG_APPLICATION SET FU_NEW_IMG_READY=? , UPDATE_BY=?, UPDATE_DATE=SYSDATE");
				sql.append(" WHERE APPLICATION_RECORD_ID=? ");			
			String dSql = String.valueOf(sql);
			log.debug("Sql="+dSql);
			ps = conn.prepareStatement(dSql);
			int index = 1;
			ps.setString(index++, OrigConstant.FLAG_Y);		
			ps.setString(index++, OrigConstant.SYSTEM);
			ps.setString(index++, appRecID);			
			ps.executeUpdate();			
		}catch(Exception e){
			log.fatal(e.getLocalizedMessage());
			throw new PLOrigApplicationException(e.getMessage());
		}finally{
			try{
				closeConnection(conn, ps);
			}catch(Exception e){
				log.fatal(e.getLocalizedMessage());
				throw new PLOrigApplicationException(e.getMessage());
			}
		}		
	}

	@Override
	public void UpdateFollowApplicationStatus(String appRecID, String appStatus)throws PLOrigApplicationException {
		PreparedStatement ps = null;
		Connection conn = null;
		try{			
			conn = getConnection();
			StringBuilder sql = new StringBuilder("");
				sql.append(" UPDATE ORIG_APPLICATION SET APPLICATION_STATUS=?, UPDATE_BY=?, UPDATE_DATE=SYSDATE");
				sql.append(" WHERE APPLICATION_RECORD_ID=? ");			
			String dSql = String.valueOf(sql);
			log.debug("Sql="+dSql);
			ps = conn.prepareStatement(dSql);
			int index = 1;
			ps.setString(index++, appStatus);		
			ps.setString(index++, OrigConstant.SYSTEM);
			ps.setString(index++, appRecID);			
			ps.executeUpdate();			
		}catch(Exception e){
			log.fatal(e.getLocalizedMessage());
			throw new PLOrigApplicationException(e.getMessage());
		}finally{
			try{
				closeConnection(conn, ps);
			}catch(Exception e){
				log.fatal(e.getLocalizedMessage());
				throw new PLOrigApplicationException(e.getMessage());
			}
		}
	}

	@Override
	public void updateRoleDecision(PLApplicationDataM applicationM) throws PLOrigApplicationException {
		Connection conn = null;
		PreparedStatement ps = null;
		try {
			conn = getConnection();
			StringBuilder sql = new StringBuilder("");
			sql.append("UPDATE ORIG_APPLICATION APP SET ");
			sql.append("APP.CA_DECISION=?, APP.CA_FIRST_ID=?, APP.CA_START_DATE=?, APP.CA_LAST_ID=?, APP.CA_LAST_DATE=?,");
			sql.append("APP.CB_DECISION=?, APP.CB_FIRST_ID=?, APP.CB_START_DATE=?, APP.CB_LAST_ID=?, APP.CB_LAST_DATE=?,");
			sql.append("APP.DC_DECISION=?, APP.DC_FIRST_ID=?, APP.DC_START_DATE=?, APP.DC_LAST_ID=?, APP.DC_LAST_DATE=?,");
			sql.append("APP.DC_SUP_DECISION=?, APP.DC_SUP_FIRST_ID=?, APP.DC_SUP_START_DATE=?, APP.DC_SUP_LAST_ID=?, APP.DC_SUP_LAST_DATE=?,");
			sql.append("APP.DE_DECISION=?, APP.DE_FIRST_ID=?, APP.DE_START_DATE=?, APP.DE_LAST_ID=?, APP.DE_LAST_DATE=?,");
			sql.append("APP.DE_SUP_DECISION=?, APP.DE_SUP_FIRST_ID=?, APP.DE_SUP_START_DATE=?, APP.DE_SUP_LAST_ID=?, APP.DE_SUP_LAST_DATE=?,");
			sql.append("APP.DF_DECISION=?, APP.DF_FIRST_ID=?, APP.DF_START_DATE=?, APP.DF_LAST_ID=?, APP.DF_LAST_DATE=?,");
			sql.append("APP.FU_DECISION=?, APP.FU_FIRST_ID=?, APP.FU_START_DATE=?, APP.FU_LAST_ID=?, APP.FU_LAST_DATE=?,");
			sql.append("APP.PO_DECISION=?, APP.PO_FIRST_ID=?, APP.PO_START_DATE=?, APP.PO_LAST_ID=?, APP.PO_LAST_DATE=?,");
			sql.append("APP.VC_DECISION=?, APP.VC_FIRST_ID=?, APP.VC_START_DATE=?, APP.VC_LAST_ID=?, APP.VC_LAST_DATE=?,");
			sql.append("APP.VC_SUP_DECISION=?, APP.VC_SUP_FIRST_ID=?, APP.VC_SUP_START_DATE=?, APP.VC_SUP_LAST_ID=?, APP.VC_SUP_LAST_DATE=? ");
			sql.append("WHERE APP.APPLICATION_RECORD_ID = ?");			
			String dSql = String.valueOf(sql);
			log.debug("Sql="+dSql);
			ps = conn.prepareStatement(dSql);
			
			int index = 1;
			ps.setString(index++, applicationM.getCaDecision());
			ps.setString(index++, applicationM.getCaFirstId());
			ps.setTimestamp(index++, this.parseTimestamp(applicationM.getCaStartDate()));
			ps.setString(index++, applicationM.getCaLastId());
			ps.setTimestamp(index++, this.parseTimestamp(applicationM.getCaLastDate()));
			
			ps.setString(index++, applicationM.getCbDecision());
			ps.setString(index++, applicationM.getCbFirstId());
			ps.setTimestamp(index++, this.parseTimestamp(applicationM.getCbStartDate()));
			ps.setString(index++, applicationM.getCbLastId());
			ps.setTimestamp(index++, this.parseTimestamp(applicationM.getCbLastDate()));
			
			ps.setString(index++, applicationM.getDcDecision());
			ps.setString(index++, applicationM.getDcFirstId());
			ps.setTimestamp(index++, this.parseTimestamp(applicationM.getDcStartDate()));
			ps.setString(index++, applicationM.getDcLastId());
			ps.setTimestamp(index++, this.parseTimestamp(applicationM.getDcLastDate()));
			
			ps.setString(index++, applicationM.getDcSupDecision());
			ps.setString(index++, applicationM.getDcSupFirstId());
			ps.setTimestamp(index++, this.parseTimestamp(applicationM.getDcSupFirstDate()));
			ps.setString(index++, applicationM.getDcSupLastId());
			ps.setTimestamp(index++, this.parseTimestamp(applicationM.getDcSupLastDate()));
			
			ps.setString(index++, applicationM.getDeDecision());
			ps.setString(index++, applicationM.getDeFirstId());
			ps.setTimestamp(index++, this.parseTimestamp(applicationM.getDeStartDate()));
			ps.setString(index++, applicationM.getDeLastId());
			ps.setTimestamp(index++, this.parseTimestamp(applicationM.getDeLastDate()));
			
			ps.setString(index++, applicationM.getDeSupDecision());
			ps.setString(index++, applicationM.getDeSupFirstId());
			ps.setTimestamp(index++, this.parseTimestamp(applicationM.getDeSupFirstDate()));
			ps.setString(index++, applicationM.getDeSupLastId());
			ps.setTimestamp(index++, this.parseTimestamp(applicationM.getDeSupLastDate()));
			
			ps.setString(index++, applicationM.getDF_Decision());
			ps.setString(index++, applicationM.getDfFirstId());
			ps.setTimestamp(index++, this.parseTimestamp(applicationM.getDfStartDate()));
			ps.setString(index++, applicationM.getDfLastId());
			ps.setTimestamp(index++, this.parseTimestamp(applicationM.getDfLastDate()));
			
			ps.setString(index++, applicationM.getFuDecision());
			ps.setString(index++, applicationM.getFuFirstId());
			ps.setTimestamp(index++, this.parseTimestamp(applicationM.getFuStartDate()));
			ps.setString(index++, applicationM.getFuLastId());
			ps.setTimestamp(index++, this.parseTimestamp(applicationM.getFuLastDate()));
			
			ps.setString(index++, applicationM.getPoDecision());
			ps.setString(index++, applicationM.getPoFirstId());
			ps.setTimestamp(index++, this.parseTimestamp(applicationM.getPoStartDate()));
			ps.setString(index++, applicationM.getPoLastId());
			ps.setTimestamp(index++, this.parseTimestamp(applicationM.getPoLastDate()));
			
			ps.setString(index++, applicationM.getVcDecision());
			ps.setString(index++, applicationM.getVcFirstId());
			ps.setTimestamp(index++, this.parseTimestamp(applicationM.getVcStartDate()));
			ps.setString(index++, applicationM.getVcLastId());
			ps.setTimestamp(index++, this.parseTimestamp(applicationM.getVcLastDate()));
			
			ps.setString(index++, applicationM.getVcSupDecision());
			ps.setString(index++, applicationM.getVcSupFirstId());
			ps.setTimestamp(index++, this.parseTimestamp(applicationM.getVcSupStartDate()));
			ps.setString(index++, applicationM.getVcSupLastId());
			ps.setTimestamp(index++, this.parseTimestamp(applicationM.getVcSupLastDate()));
			
			ps.setString(index++, applicationM.getAppRecordID());
			
			ps.executeUpdate();			
		} catch (Exception e) {
			log.fatal(e.getLocalizedMessage());
			throw new PLOrigApplicationException(e.getMessage());
		} finally {
			try {
				closeConnection(conn, ps);
			} catch (Exception e) {
				log.fatal(e.getLocalizedMessage());
				throw new PLOrigApplicationException(e.getMessage());
			}
		}
		
	}

	@Override
	public PLApplicationDataM loadOrigApplicationForNCB(String appRecId) throws PLOrigApplicationException {
		try{
			PLApplicationDataM applicationM = this.selectOrig_Appplication(appRecId);
			Vector<PLPersonalInfoDataM> perVect = new Vector<PLPersonalInfoDataM>();	
			PLOrigPersonalInfoDAO personalDAO = PLORIGDAOFactory.getPLOrigPersonalInfoDAO();
			PLPersonalInfoDataM personalM = personalDAO.loadPersonalMForNCB(appRecId);
			if(!OrigUtil.isEmptyObject(personalM)){
				XRulesRemoteDAOUtilManager xrulesDAO = ORIGEJBService.getORIGXRulesDAOUtilManager();
				personalM.setXrulesVerification(xrulesDAO.selectTable_XrulesVeririficationResult(personalM.getPersonalID()));
				perVect.add(personalM);
			}
			applicationM.setPersonalInfoVect(perVect);
			return applicationM;
		}catch(Exception e){
			throw new PLOrigApplicationException(e.getMessage());
		}
	}

	@Override
	public void saveAppMForNCB(PLApplicationDataM applicationM, UserDetailM userM) throws PLOrigApplicationException{		
//		this.updateTableOrigApplication(applicationM);
		saveSubTableForNCB(applicationM, userM);		
	}
	
	private void saveSubTableForNCB(PLApplicationDataM applicationM, UserDetailM userM) throws PLOrigApplicationException{		
		PLPersonalInfoDataM personalM = applicationM.getPLPersonalInfoDataM(OrigConstant.PERSONAL_TYPE_APPLICANT);
//		PLOrigPersonalInfoDAO personalDAO = PLORIGDAOFactory.getPLOrigPersonalInfoDAO();
//			personalDAO.updateTableOrig_Personal_Info(personalM, applicationM.getAppRecordID());
		try{
			XRulesRemoteDAOUtilManager xrulesBean = ORIGEJBService.getORIGXRulesDAOUtilManager();
//				xrulesBean.updateTableXRules_Verification_Result(personalM.getXrulesVerification(), personalM.getPersonalID());
			xrulesBean.UpdateNcbResultVerification(personalM.getXrulesVerification(), personalM.getPersonalID());
		}catch(Exception e){
			log.fatal(e.getLocalizedMessage());
			throw new PLOrigApplicationException(e.getMessage());
		}		
	}

	@Override
	public PLApplicationDataM LoadNCBImageDataM(String appRecID)throws PLOrigApplicationException {
		try{			
			PLApplicationDataM applicationM = selectOrig_Appplication(appRecID);

			PLOrigApplicationImageDAO imageDAO = PLORIGDAOFactory.getPLOrigApplicationImageDAO();
			applicationM.setApplicationImageVect(imageDAO.loadOrigApplicationImage(applicationM.getAppRecordID()));

			PLOrigPersonalInfoDAO personalDAO = PLORIGDAOFactory.getPLOrigPersonalInfoDAO();			
				applicationM.setPersonalInfoVect(personalDAO.ImageORIGPersonalInfo(appRecID));
			
			return applicationM;
		} catch (Exception e) {
			log.fatal("Exception >> "+e.getLocalizedMessage());
			throw new PLOrigApplicationException(e.getMessage());
		}
	}
	
	@Override
	public void LoadAppInfo(PLApplicationDataM applicationM,String appRecID)throws PLOrigApplicationException{
		try{
			ApplicationInfoDAO applicationDAO = PLORIGDAOFactory.getApplicationInfoDAO();
				applicationDAO.LoadAppInfo(applicationM,appRecID);
		}catch(Exception e){
			log.fatal("Exception ",e);
			throw new PLOrigApplicationException(e.getMessage());
		}
	}
	
	
	@Override
	public void LoadRoleDecision(PLApplicationDataM applicationM)throws PLOrigApplicationException{
		PreparedStatement ps = null;
		ResultSet rs = null;
		Connection conn = null;
		try{
			conn = getConnection();
			
			StringBuilder SQL = new StringBuilder("");
				SQL.append(" SELECT ");
				SQL.append("     CA_DECISION, ");
				SQL.append("     CA_FIRST_ID, ");
				SQL.append("     CA_START_DATE, ");
				SQL.append("     CA_LAST_ID, ");
				SQL.append("     CA_LAST_DATE, ");
				SQL.append("     CB_DECISION, ");
				SQL.append("     CB_FIRST_ID, ");
				SQL.append("     CB_START_DATE, ");
				SQL.append("     CB_LAST_ID, ");
				SQL.append("     CB_LAST_DATE, ");
				SQL.append("     DC_DECISION, ");
				SQL.append("     DC_FIRST_ID, ");
				SQL.append("     DC_START_DATE, ");
				SQL.append("     DC_LAST_ID, ");
				SQL.append("     DC_LAST_DATE, ");
				SQL.append("     DC_SUP_DECISION, ");
				SQL.append("     DC_SUP_FIRST_ID, ");
				SQL.append("     DC_SUP_START_DATE, ");
				SQL.append("     DC_SUP_LAST_ID, ");
				SQL.append("     DC_SUP_LAST_DATE, ");
				SQL.append("     DE_DECISION, ");
				SQL.append("     DE_FIRST_ID, ");
				SQL.append("     DE_START_DATE, ");
				SQL.append("     DE_LAST_ID, ");
				SQL.append("     DE_LAST_DATE, ");
				SQL.append("     DE_SUP_DECISION, ");
				SQL.append("     DE_SUP_FIRST_ID, ");
				SQL.append("     DE_SUP_START_DATE, ");
				SQL.append("     DE_SUP_LAST_ID, ");
				SQL.append("     DE_SUP_LAST_DATE, ");
				SQL.append("     DF_DECISION, ");
				SQL.append("     DF_FIRST_ID, ");
				SQL.append("     DF_START_DATE, ");
				SQL.append("     DF_LAST_ID, ");
				SQL.append("     DF_LAST_DATE, ");
				SQL.append("     FU_DECISION, ");
				SQL.append("     FU_FIRST_ID, ");
				SQL.append("     FU_START_DATE, ");
				SQL.append("     FU_LAST_ID, ");
				SQL.append("     FU_LAST_DATE, ");
				SQL.append("     PO_DECISION, ");
				SQL.append("     PO_FIRST_ID, ");
				SQL.append("     PO_START_DATE, ");
				SQL.append("     PO_LAST_ID, ");
				SQL.append("     PO_LAST_DATE, ");
				SQL.append("     VC_DECISION, ");
				SQL.append("     VC_FIRST_ID, ");
				SQL.append("     VC_START_DATE, ");
				SQL.append("     VC_LAST_ID, ");
				SQL.append("     VC_LAST_DATE, ");
				SQL.append("     VC_SUP_DECISION, ");
				SQL.append("     VC_SUP_FIRST_ID, ");
				SQL.append("     VC_SUP_START_DATE, ");
				SQL.append("     VC_SUP_LAST_ID, ");
				SQL.append("     VC_SUP_LAST_DATE ");
				SQL.append(" FROM ");
				SQL.append("     ORIG_APPLICATION ");
				SQL.append(" WHERE ");
				SQL.append("     APPLICATION_RECORD_ID = ? ");
				
			String dSql = String.valueOf(SQL);			
			ps = conn.prepareStatement(dSql);
			int index = 1;
			ps.setString(index++,applicationM.getAppRecordID());
			
			rs = ps.executeQuery();
			
			if(rs.next()){
				
				applicationM.setCaDecision(rs.getString("CA_DECISION"));
				applicationM.setCaFirstId(rs.getString("CA_FIRST_ID"));
				applicationM.setCaStartDate(rs.getTimestamp("CA_START_DATE"));
				applicationM.setCaLastId(rs.getString("CA_LAST_ID"));
				applicationM.setCaLastDate(rs.getTimestamp("CA_LAST_DATE"));
				
				applicationM.setCbDecision(rs.getString("CB_DECISION"));
				applicationM.setCbFirstId(rs.getString("CB_FIRST_ID"));
				applicationM.setCbStartDate(rs.getTimestamp("CB_START_DATE"));
				applicationM.setCbLastId(rs.getString("CB_LAST_ID"));
				applicationM.setCbLastDate(rs.getTimestamp("CB_LAST_DATE"));
				
				applicationM.setDcDecision(rs.getString("DC_DECISION"));
				applicationM.setDcFirstId(rs.getString("DC_FIRST_ID"));
				applicationM.setDcStartDate(rs.getTimestamp("DC_START_DATE"));
				applicationM.setDcLastId(rs.getString("DC_LAST_ID"));
				applicationM.setDcLastDate(rs.getTimestamp("DC_LAST_DATE"));
				
				applicationM.setDcSupDecision(rs.getString("DC_SUP_DECISION"));
				applicationM.setDcSupFirstId(rs.getString("DC_SUP_FIRST_ID"));
				applicationM.setDcSupFirstDate(rs.getTimestamp("DC_SUP_START_DATE"));
				applicationM.setDcSupLastId(rs.getString("DC_SUP_LAST_ID"));
				applicationM.setDcSupLastDate(rs.getTimestamp("DC_SUP_LAST_DATE"));
				
				applicationM.setDeDecision(rs.getString("DE_DECISION"));
				applicationM.setDeFirstId(rs.getString("DE_FIRST_ID"));
				applicationM.setDeStartDate(rs.getTimestamp("DE_START_DATE"));
				applicationM.setDeLastId(rs.getString("DE_LAST_ID"));
				applicationM.setDeLastDate(rs.getTimestamp("DE_LAST_DATE"));
				
				applicationM.setDeSupDecision(rs.getString("DE_SUP_DECISION"));
				applicationM.setDeSupFirstId(rs.getString("DE_SUP_FIRST_ID"));
				applicationM.setDeSupFirstDate(rs.getTimestamp("DE_SUP_START_DATE"));
				applicationM.setDeSupLastId(rs.getString("DE_SUP_LAST_ID"));
				applicationM.setDeSupLastDate(rs.getTimestamp("DE_SUP_LAST_DATE"));
				
				applicationM.setDF_Decision(rs.getString("DF_DECISION"));
				applicationM.setDfFirstId(rs.getString("DF_FIRST_ID"));
				applicationM.setDfStartDate(rs.getTimestamp("DF_START_DATE"));
				applicationM.setDfLastId(rs.getString("DF_LAST_ID"));
				applicationM.setDfLastDate(rs.getTimestamp("DF_LAST_DATE"));
				
				applicationM.setFuDecision(rs.getString("FU_DECISION"));
				applicationM.setFuFirstId(rs.getString("FU_FIRST_ID"));
				applicationM.setFuStartDate(rs.getTimestamp("FU_START_DATE"));
				applicationM.setFuLastId(rs.getString("FU_LAST_ID"));
				applicationM.setFuLastDate(rs.getTimestamp("FU_LAST_DATE"));
				
				applicationM.setPoDecision(rs.getString("PO_DECISION"));
				applicationM.setPoFirstId(rs.getString("PO_FIRST_ID"));
				applicationM.setPoStartDate(rs.getTimestamp("PO_START_DATE"));
				applicationM.setPoLastId(rs.getString("PO_LAST_ID"));
				applicationM.setPoLastDate(rs.getTimestamp("PO_LAST_DATE"));
				
				applicationM.setVcDecision(rs.getString("VC_DECISION"));
				applicationM.setVcFirstId(rs.getString("VC_FIRST_ID"));
				applicationM.setVcStartDate(rs.getTimestamp("VC_START_DATE"));
				applicationM.setVcLastId(rs.getString("VC_LAST_ID"));
				applicationM.setVcLastDate(rs.getTimestamp("VC_LAST_DATE"));
				
				applicationM.setVcSupDecision(rs.getString("VC_SUP_DECISION"));
				applicationM.setVcSupFirstId(rs.getString("VC_SUP_FIRST_ID"));
				applicationM.setVcSupStartDate(rs.getTimestamp("VC_SUP_START_DATE"));
				applicationM.setVcSupLastId(rs.getString("VC_SUP_LAST_ID"));
				applicationM.setVcSupLastDate(rs.getTimestamp("VC_SUP_LAST_DATE"));
							
			}			
		}catch(Exception e){
			log.fatal(e.getLocalizedMessage());
			throw new PLOrigApplicationException(e.getMessage());
		}finally{
			try{
				closeConnection(conn, rs, ps);
			}catch(Exception e){
				log.fatal(e.getLocalizedMessage());
				throw new PLOrigApplicationException(e.getMessage());
			}
		}		
	}
	
	@Override
	public void updateSMSFollowDocFLAG(PLApplicationDataM applicationM)	throws PLOrigApplicationException {
		PreparedStatement ps = null;
		Connection conn = null;
		try{			
			conn = getConnection();
			StringBuilder sql = new StringBuilder("");
				sql.append(" UPDATE ORIG_APPLICATION SET SMS_FOLLOW_UP=?,UPDATE_BY=?,UPDATE_DATE=SYSDATE,SMS_FOLLOW_UP_DATE=? ");
				sql.append(" WHERE APPLICATION_RECORD_ID=? ");
			String dSql = String.valueOf(sql);
			log.debug("Sql="+dSql);
			ps = conn.prepareStatement(dSql);
			int index = 1;
			ps.setString(index++, applicationM.getSmsFollowUp());
			ps.setString(index++, applicationM.getUpdateBy());
			ps.setTimestamp(index++, applicationM.getSmsFollowUpDate());
			ps.setString(index++, applicationM.getAppRecordID());			
			ps.executeUpdate();			
		}catch(Exception e){
			log.fatal(e.getLocalizedMessage());
			throw new PLOrigApplicationException(e.getMessage());
		}finally{
			try{
				closeConnection(conn, ps);
			}catch(Exception e){
				log.fatal(e.getLocalizedMessage());
				throw new PLOrigApplicationException(e.getMessage());
			}
		}
	}
	
}
