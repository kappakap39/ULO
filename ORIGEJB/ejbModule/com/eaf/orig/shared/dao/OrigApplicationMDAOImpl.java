/*
 * Created on Sep 26, 2007
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
package com.eaf.orig.shared.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Timestamp;
import java.util.Vector;
import org.apache.log4j.Logger;
import com.eaf.orig.shared.constant.OrigConstant;
import com.eaf.orig.shared.dao.exceptions.OrigApplicationMException;
import com.eaf.orig.shared.ejb.ORIGGeneratorManager;
import com.eaf.orig.shared.model.ApplicationDataM;
import com.eaf.orig.shared.model.AttachmentHistoryDataM;
import com.eaf.orig.shared.model.CardInformationDataM;
import com.eaf.orig.shared.model.CollateralDataM;
import com.eaf.orig.shared.model.DocumentCheckListDataM;
import com.eaf.orig.shared.model.LoanDataM;
import com.eaf.orig.shared.model.NotePadDataM;
import com.eaf.orig.shared.model.OtherNameDataM;
import com.eaf.orig.shared.model.PersonalInfoDataM;
import com.eaf.orig.shared.model.ProposalDataM;
import com.eaf.orig.shared.model.ReasonDataM;
import com.eaf.orig.shared.model.VehicleDataM;
import com.eaf.orig.shared.service.ORIGEJBService;
import com.eaf.orig.shared.xrules.dao.XRulesVerificationResultDAO;
import com.eaf.orig.wf.shared.ORIGWFConstant;

/**
 * @author Sankom Sanpunya
 * 
 * Type: OrigApplicationMDAOImpl
 */

public class OrigApplicationMDAOImpl extends OrigObjectDAO implements
		OrigApplicationMDAO {
	private static Logger log = Logger.getLogger(OrigApplicationMDAOImpl.class);

	/**
	 *  
	 */
	public OrigApplicationMDAOImpl() {
		super();

	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.eaf.orig.share.dao.OrigApplicationMDAO#createModelOrigApplicationM(com.eaf.orig.shared.model.ApplicationDataM)
	 */
	public void createModelOrigApplicationM(ApplicationDataM prmApplicationDataM)
			throws OrigApplicationMException {
		try {
			//Get Application Record ID
			/*String prmApplicationRecordID = ORIGDAOFactory.getUniqueIDGeneratorDAO2().getUniqByName(EJBConstant.APPLICATION_ID); 
			prmApplicationDataM.setAppRecordID(prmApplicationRecordID);*/
			
			//Get Application Number
		    log.debug("prmApplicationDataM.getApplicationNo() :"+prmApplicationDataM.getApplicationNo());
		    if(prmApplicationDataM.getApplicationNo()==null||"".equals(prmApplicationDataM.getApplicationNo())){
				//String applicationNo = ORIGDAOFactory.getUniqueIDGeneratorDAO2().getApplicationNo(prmApplicationDataM);
				//prmApplicationDataM.setApplicationNo(applicationNo);
		    	ORIGGeneratorManager generatorManager = ORIGEJBService.getGeneratorManager();
				String applicationNo = generatorManager.generateApplicationNo(prmApplicationDataM);
				prmApplicationDataM.setApplicationNo(applicationNo);
		    }
			createTableORIG_APPLICATION(prmApplicationDataM);
			
			//create Persoanl Info
			Vector vPersoanlInfo = prmApplicationDataM.getPersonalInfoVect();
			OrigApplicationCustomerMDAO origApplicationCustomerMDAO = ORIGDAOFactory
					.getOrigApplicationCustomerMDAO();
			if (vPersoanlInfo != null) {
				for (int i = 0; i < vPersoanlInfo.size(); i++) {
					PersonalInfoDataM  personalInfoDataM= (PersonalInfoDataM) vPersoanlInfo.get(i);
					personalInfoDataM.setAppRecordID(prmApplicationDataM.getAppRecordID());
					if(personalInfoDataM.getCreateBy() == null || ("").equals(personalInfoDataM.getCreateBy())){
						personalInfoDataM.setCreateBy(prmApplicationDataM.getCreateBy());
					}else{
						personalInfoDataM.setUpdateBy(prmApplicationDataM.getUpdateBy());
					}
					origApplicationCustomerMDAO.createModelOrigApplicationCustomerM(personalInfoDataM);
					personalInfoDataM.setOfficeCode(prmApplicationDataM.getOfficeCode());
				}
			}
			
			//create loan
			Vector vLoanDataM = prmApplicationDataM.getLoanVect();
			logger.debug("VLoanDataM >>>>"+vLoanDataM);
			OrigLoanMDAO origLoanMDAO = ORIGDAOFactory.getOrigLoanMDAO();
			if (vLoanDataM != null) {
				for (int i = 0; i < vLoanDataM.size(); i++) {
					LoanDataM loanDataM = (LoanDataM) vLoanDataM.get(i);
					loanDataM.setAppRecordID(prmApplicationDataM.getAppRecordID());
					origLoanMDAO.createModelOrigLoanM(loanDataM);
				}
			}
			//create vehicle
			VehicleDataM prmVehicleDataM = prmApplicationDataM
					.getVehicleDataM();
			if(prmVehicleDataM!=null){
			prmVehicleDataM.setAppRecordID(prmApplicationDataM.getAppRecordID());
			ORIGDAOFactory.getOrigVehicleMDAO().createModelVehicleDataM(
					prmVehicleDataM);
			}
			
			//create loan
			Vector vOtherNameM = prmApplicationDataM.getOtherNameDataM();
			OrigOtherNameMDAO origOtherNameMDAO = ORIGDAOFactory.getOrigOtherNameMDAO();
			if (vOtherNameM != null) {
				OtherNameDataM otherNameDataM;
				for (int i = 0; i < vOtherNameM.size(); i++) {
					otherNameDataM = (OtherNameDataM) vOtherNameM.get(i);
					otherNameDataM.setApplicationRecordId(prmApplicationDataM.getAppRecordID());
					origOtherNameMDAO.createModelOtherNameDataM(otherNameDataM);
				}
			}
			
			//create proposal
			ProposalDataM prmProposalDataM = prmApplicationDataM.getProposalDataM();
			if(prmProposalDataM!=null){
			prmProposalDataM.setAppRecID(prmApplicationDataM.getAppRecordID());
			ORIGDAOFactory.getOrigProposalDAO().createModelOrigProposalM(prmProposalDataM);
			}
			
			//create collateral
			Vector vCollateral = prmApplicationDataM.getCollateralVect();
			if(vCollateral!=null && vCollateral.size()>0){
			    ORIGDAOFactory.getOrigMGCollateralMDAO().createCollateralM(vCollateral,prmApplicationDataM.getAppRecordID(),prmApplicationDataM.getCreateBy());
			}
			
		} catch (Exception e) {
			log.fatal(e.getLocalizedMessage());
			throw new OrigApplicationMException(e.getMessage());
		}

	}

	/**
	 * @param prmApplicationDataM
	 */
	private void createTableORIG_APPLICATION(
			ApplicationDataM prmApplicationDataM)
			throws OrigApplicationMException {
		PreparedStatement ps = null;
		Connection conn = null;
		try {
			//conn = Get Connection
			conn = getConnection();
			StringBuffer sql = new StringBuffer("");
			sql.append("INSERT INTO ORIG_APPLICATION ");
			sql.append("( APPLICATION_RECORD_ID,APPLICATION_NO,IDENTIFICATION_NO,OFFICE_CODE,DEALER_CODE");
			sql.append(" ,SELLER_CODE,SALEMAN_CODE,CREATE_BY,CREATE_DATE,UPDATE_BY");
			sql.append(" ,UPDATE_DATE,CONSENT,CONSENT_DATE,CMR_START_DATE,CMR_END_DATE");
			sql.append(" ,CMR_FIRST_ID,CMR_LAST_ID,DE_START_DATE,DE_LAST_DATE,DE_FIRST_ID");
			sql.append(" ,DE_LAST_ID,CMR_EX_START_DATE,CMR_EX_END_DATE,CMR_EX_FIRST_ID,CMR_EX_LAST_ID");
			sql.append(" ,UW_START_DATE,UW_END_DATE,UW_FIRST_ID,UW_LAST_ID,PD_START_DATE");
			sql.append(" ,PD_END_DATE,PD_FIRST_ID,PD_LAST_ID,ESCALATE_BY,ESCALATE_TO");
			//sql
			//		.append(", ESCALATE_DATE,APPLICATION_STATUS,JOB_STATE,LOAN_TYPE,CMR_CODE");
			sql.append(", ESCALATE_DATE,LOAN_TYPE,CMR_CODE");
			sql.append(", AREA_CODE, SRCOFCUS, UW_DECISION, PD_DECISION, XCMR_OVERRIDE_BY");
			sql.append(", XCMR_OVERRIDE_DATE, XCMR_DECISION, UW_DECISION_REASON,DRAW_DOWN_FLAG, DE_DECISION");
			sql.append(", PRIORITY, PD_DECISION_REASON,SCORING_RESULT");
			sql.append(" ,POLICY_VERSION ,XUW_POLICY_EXCEPTION,XUW_DECISION,XUW_DECISION_REASON,XUW_OVERRIDE_BY  ");
			sql.append(" ,XUW_OVERRIDE_DATE,XUW_START_DATE ,XUW_END_DATE,XUW_FIRST_ID,XUW_LAST_ID, BUSINESS_CLASS_ID, FINAL_CREDIT_LIMIT, GROUP_APP_ID)");
			sql.append(" VALUES(?,?,?,?,?   ,?,?,?,SYSDATE,?,SYSDATE,?  ,?,?,?,?,?  ,?,?,?,?,?");
			sql.append("	    ,?,?,?,?,?   ,?,?,?,?,?	 ,?,?,?,?,?, ?,?,?,?,?, ?,?,?,?,?, ?,?,?,?");
			sql.append("  ,?,?,?,?,?  ,?,?,?,?,? ,?,?,?  )");
			String dSql = String.valueOf(sql);
			log.debug("Sql=" + dSql);
			ps = conn.prepareStatement(dSql);
			ps.setString(1, prmApplicationDataM.getAppRecordID());
			ps.setString(2, prmApplicationDataM.getApplicationNo());
			ps.setString(3, prmApplicationDataM.getIdentificationNo());
			ps.setString(4, prmApplicationDataM.getOfficeCode());
			ps.setString(5, prmApplicationDataM.getDealerCode());
			ps.setString(6, prmApplicationDataM.getSellerCode());
			ps.setString(7, prmApplicationDataM.getSalesManCode());
			ps.setString(8, prmApplicationDataM.getCreateBy());
			ps.setString(9, prmApplicationDataM.getUpdateBy());
			ps.setString(10, prmApplicationDataM.getConsent());
			ps
					.setDate(11, this.parseDate(prmApplicationDataM
							.getConsentDate()));
			ps.setDate(12, this
					.parseDate(prmApplicationDataM.getCmrStartDate()));
			ps.setDate(13, this.parseDate(prmApplicationDataM.getCmrEndDate()));//
			ps.setString(14, prmApplicationDataM.getCmrFirstId());
			ps.setString(15, prmApplicationDataM.getCmrLastId());
			ps
					.setDate(16, this.parseDate(prmApplicationDataM
							.getDeStartDate()));
			ps.setDate(17, this.parseDate(prmApplicationDataM.getDeLastDate()));
			ps.setString(18, prmApplicationDataM.getDeFirstId());//
			ps.setString(19, prmApplicationDataM.getDeLastId());
			ps.setDate(20, this.parseDate(prmApplicationDataM
					.getCmrExStartDate()));
			ps.setDate(21, this
					.parseDate(prmApplicationDataM.getCmrExEndDate()));
			ps.setString(22, prmApplicationDataM.getCmrExFirstId());
			ps.setString(23, prmApplicationDataM.getCmrExLastId());
			ps
					.setDate(24, this.parseDate(prmApplicationDataM
							.getUwStartDate()));
			ps.setDate(25, this.parseDate(prmApplicationDataM.getUwEndDate()));
			ps.setString(26, prmApplicationDataM.getUwFirstId());
			ps.setString(27, prmApplicationDataM.getUwLastId());
			ps
					.setDate(28, this.parseDate(prmApplicationDataM
							.getPdStartDate()));
			ps.setDate(29, this.parseDate(prmApplicationDataM.getPdEndDate()));
			ps.setString(30, prmApplicationDataM.getPdFirstId());
			ps.setString(31, prmApplicationDataM.getPdLastId());
			ps.setString(32, prmApplicationDataM.getEscalateBy());
			ps.setString(33, prmApplicationDataM.getEscalateTo());
			ps.setDate(34, this
					.parseDate(prmApplicationDataM.getEscalateDate()));
			//ps.setString(35, prmApplicationDataM.getApplicationStatus());
			//ps.setString(35, prmApplicationDataM.getJobState());
			ps.setString(35, prmApplicationDataM.getLoanType());
			ps.setString(36, prmApplicationDataM.getCmrCode());
			ps.setString(37, prmApplicationDataM.getAreaCode());
			ps.setString(38, prmApplicationDataM.getSourceOfCustomer());
			ps.setString(39, prmApplicationDataM.getUwDecision());
			ps.setString(40, prmApplicationDataM.getPdDecision());
			ps.setString(41, prmApplicationDataM.getXcmrOverrideBy());
			ps.setDate(42, this.parseDate(prmApplicationDataM.getXcmrOverrideDate()));
			ps.setString(43, prmApplicationDataM.getXcmrDecision());
			ps.setString(44, prmApplicationDataM.getUwDecisionReason());
			ps.setString(45, prmApplicationDataM.getDrawDownFlag());
			ps.setString(46, prmApplicationDataM.getDeDecision());
			ps.setString(47, prmApplicationDataM.getPriority());
			ps.setString(48, prmApplicationDataM.getPdDecisionReason());
			ps.setString(49,prmApplicationDataM.getScorringResult());
			ps.setString(50,prmApplicationDataM.getPolicyVersion());
			ps.setString(51,prmApplicationDataM.getXuwPolicyException());
			ps.setString(52,prmApplicationDataM.getXuwDecision());
			ps.setString(53,prmApplicationDataM.getXuwDecisionReason());
			ps.setString(54,prmApplicationDataM.getXuwOverrideBy());
			ps.setTimestamp(55,prmApplicationDataM.getXuwOverrideDate());
			ps.setTimestamp(56,prmApplicationDataM.getXuwStartDate());
			ps.setTimestamp(57,prmApplicationDataM.getXuwEndDate());
			ps.setString(58,prmApplicationDataM.getXuwFirstId());
			ps.setString(59,prmApplicationDataM.getXuwLastId());
			ps.setString(60,prmApplicationDataM.getBusinessClassId());
			ps.setBigDecimal(61,prmApplicationDataM.getFinalCreditLimit());
			ps.setString(62,prmApplicationDataM.getGroupAppID());
			ps.executeUpdate();
		} catch (Exception e) {
			log.fatal(e.getLocalizedMessage());
			throw new OrigApplicationMException(e.getMessage());
		} finally {
			try {
				closeConnection(conn, ps);
			} catch (Exception e) {
				log.fatal(e.getLocalizedMessage());
			}
		}

	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.eaf.orig.share.dao.OrigApplicationMDAO#deleteModelOrigApplicationM(com.eaf.orig.shared.model.ApplicationDataM)
	 */
	public void deleteModelOrigApplicationM(ApplicationDataM prmApplicationDataM)
			throws OrigApplicationMException {
		try {
			deleteTableORIG_APPLICATION(prmApplicationDataM);
		} catch (Exception e) {
			log.fatal(e.getLocalizedMessage());
			throw new OrigApplicationMException(e.getMessage());
		}

	}

	/**
	 * @param prmApplicationDataM
	 */
	private void deleteTableORIG_APPLICATION(
			ApplicationDataM prmApplicationDataM)
			throws OrigApplicationMException {
		PreparedStatement ps = null;
		Connection conn = null;
		try {
			//conn = Get Connection
			conn = getConnection();
			StringBuffer sql = new StringBuffer("");
			sql.append("DELETE ORIG_APPLICATION ");
			sql.append(" WHERE APPLICATION_RECORD_ID = ?");
			String dSql = String.valueOf(sql);
			log.debug("Sql=" + dSql);
			ps = conn.prepareStatement(dSql);
			ps.setString(1, prmApplicationDataM.getAppRecordID());
			ps.executeUpdate();

		} catch (Exception e) {
			log.fatal(e.getLocalizedMessage());
			throw new OrigApplicationMException(e.getMessage());
		} finally {
			try {
				closeConnection(conn, ps);
			} catch (Exception e) {
				log.fatal(e.getLocalizedMessage());
				throw new OrigApplicationMException(e.getMessage());
			}
		}

	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.eaf.orig.share.dao.OrigApplicationMDAO#loadModelOrigApplicationM(java.lang.String,
	 *      int)
	 */
	public ApplicationDataM loadModelOrigApplicationM(
			String applicationRecordId, int seq, String providerUrlEXT, String jndiEXT)
			throws OrigApplicationMException {
		try {
			//Load Applciation
			ApplicationDataM result = selectTableORIG_APPLICATION(applicationRecordId);
			result = selectImageData(result.getAppRecordID(), result);
			
			//Load Personal
			OrigApplicationCustomerMDAO applicationCustomerMDAO = ORIGDAOFactory.getOrigApplicationCustomerMDAO();
			result.setPersonalInfoVect(applicationCustomerMDAO.loadModelOrigApplicationCustomerM(result.getAppRecordID(), providerUrlEXT, jndiEXT));
			log.debug("Personal Size = "+result.getPersonalInfoVect().size());
			//Load Vehicle
			OrigVehicleMDAO origVehicleMDAO = ORIGDAOFactory.getOrigVehicleMDAO();
			result.setVehicleDataM(origVehicleMDAO.loadModelVehicleDataM(result.getAppRecordID()));
			
			//Load Notepad
			OrigNotePadDataMDAO notePadDataMDAO = ORIGDAOFactory.getOrigNotePadDataMDAO();
			result.setNotepadDataM(notePadDataMDAO.loadModelOrigNotePadDataM(result.getAppRecordID()));

			//Load Loan
			OrigLoanMDAO loanMDAO = ORIGDAOFactory.getOrigLoanMDAO();
			result.setLoanVect(loanMDAO.loadModelOrigLoanM(result.getAppRecordID()));
			
			//Load Other Name
			OrigOtherNameMDAO otherNameMDAO = ORIGDAOFactory.getOrigOtherNameMDAO();
			result.setOtherNameDataM(otherNameMDAO.loadModelOtherNameDataM(result.getAppRecordID()));
			
			//Load Proposal
			OrigProposalMDAO origProposalMDAO = ORIGDAOFactory.getOrigProposalDAO();
			result.setProposalDataM(origProposalMDAO.loadModelOrigProposalM(result.getAppRecordID()));
			
			//Load Reason
			OrigReasonMDAO reasonMDAOImpl = ORIGDAOFactory.getOrigReasonMDAO();
			result.setReasonVect(reasonMDAOImpl.loadModelOrigReasonM(result.getAppRecordID()));
			
			//Load Attachment list
			OrigAttachmentHistoryMDAO attachmentHistoryMDAO = ORIGDAOFactory.getOrigAttachmentHistoryMDAO();
			result.setAttachmentVect(attachmentHistoryMDAO.loadModelOrigAttachmentHistoryM(result.getAppRecordID()));
			
			//Load Documnet list
			OrigDocumentCheckListMDAO documentCheckListMDAO = ORIGDAOFactory.getOrigDocumentCheckListMDAO();
			result.setDocumentCheckListDataM(documentCheckListMDAO.loadModelOrigDocumentCheckListM(result.getAppRecordID()));
			
			//Load Action Flow
			OrigApplicationLogMDAO logMDAO = ORIGDAOFactory.getOrigApplicationLogMDAO();
			result.setActionFlowVect(logMDAO.loadModelOrigApplicationLogM(result.getAppRecordID()));
			
			//Load Action Flow
			OrigApplicationCardInformationMDAO cardInformationMDAO = ORIGDAOFactory.getOrigApplicationCardInformationMDAO();
			result.setCardInformation(cardInformationMDAO.loadModelOrigApplicationCardInformationDataM(result.getAppRecordID(), providerUrlEXT, jndiEXT));

			//Load collateral
			result.setCollateralVect(ORIGDAOFactory.getOrigMGCollateralMDAO().loadCollateralM(result.getAppRecordID()));
			if(result.getCollateralVect()!=null && result.getCollateralVect().size()>0){
			    CollateralDataM collateralDataM = (CollateralDataM)result.getCollateralVect().get(0);
			    System.out.println(">> collateralDataM.getPropertyType()="+collateralDataM.getPropertyType());
			}
			
			////load PreScore
			//OrigPreScoreMDAO  preScoreDAO=ORIGDAOFactory.getPreScoreDataMDAO();
			//result.setPreScoreDataM(preScoreDAO.loadModelOrigPreScoreM(applicationRecordId,result.getcm));
			
			return result;
		} catch (Exception e) {
			log.fatal(e.getLocalizedMessage());
			throw new OrigApplicationMException(e.getMessage());
		}
	}
	
	/*
	 * (non-Javadoc)
	 * 
	 * @see com.eaf.orig.share.dao.OrigApplicationMDAO#loadModelOrigApplicationM(java.lang.String,
	 *      int)
	 */
	public ApplicationDataM loadModelOrigApplicationM(String applicationRecordId, int seq, String providerUrlEXT, String jndiEXT, ApplicationDataM result)throws OrigApplicationMException {
		try {
			//Load Applciation
			result = selectTableORIG_APPLICATION(applicationRecordId, result);
			result = selectImageData(result.getAppRecordID(), result);
			
			//Load Personal
			OrigApplicationCustomerMDAO applicationCustomerMDAO = ORIGDAOFactory.getOrigApplicationCustomerMDAO();
			result.setPersonalInfoVect(applicationCustomerMDAO.loadModelOrigApplicationCustomerM(result.getAppRecordID(), providerUrlEXT, jndiEXT));
			log.debug("Personal Size = "+result.getPersonalInfoVect().size());
			//Load Vehicle
			OrigVehicleMDAO origVehicleMDAO = ORIGDAOFactory.getOrigVehicleMDAO();
			result.setVehicleDataM(origVehicleMDAO.loadModelVehicleDataM(result.getAppRecordID()));
			
			//Load Notepad
			OrigNotePadDataMDAO notePadDataMDAO = ORIGDAOFactory.getOrigNotePadDataMDAO();
			result.setNotepadDataM(notePadDataMDAO.loadModelOrigNotePadDataM(result.getAppRecordID()));

			//Load Loan
			OrigLoanMDAO loanMDAO = ORIGDAOFactory.getOrigLoanMDAO();
			result.setLoanVect(loanMDAO.loadModelOrigLoanM(result.getAppRecordID()));
			
			//Load Other Name
			OrigOtherNameMDAO otherNameMDAO = ORIGDAOFactory.getOrigOtherNameMDAO();
			result.setOtherNameDataM(otherNameMDAO.loadModelOtherNameDataM(result.getAppRecordID()));
			
			//Load Proposal
			OrigProposalMDAO origProposalMDAO = ORIGDAOFactory.getOrigProposalDAO();
			result.setProposalDataM(origProposalMDAO.loadModelOrigProposalM(result.getAppRecordID()));
			
			//Load Reason
			OrigReasonMDAO reasonMDAOImpl = ORIGDAOFactory.getOrigReasonMDAO();
			result.setReasonVect(reasonMDAOImpl.loadModelOrigReasonM(result.getAppRecordID()));
			
			//Load Attachment list
			OrigAttachmentHistoryMDAO attachmentHistoryMDAO = ORIGDAOFactory.getOrigAttachmentHistoryMDAO();
			result.setAttachmentVect(attachmentHistoryMDAO.loadModelOrigAttachmentHistoryM(result.getAppRecordID()));
			
			//Load Documnet list
			OrigDocumentCheckListMDAO documentCheckListMDAO = ORIGDAOFactory.getOrigDocumentCheckListMDAO();
			result.setDocumentCheckListDataM(documentCheckListMDAO.loadModelOrigDocumentCheckListM(result.getAppRecordID()));
			
			//Load Action Flow
			OrigApplicationLogMDAO logMDAO = ORIGDAOFactory.getOrigApplicationLogMDAO();
			result.setActionFlowVect(logMDAO.loadModelOrigApplicationLogM(result.getAppRecordID()));
			
			//Load collateral
			result.setCollateralVect(ORIGDAOFactory.getOrigMGCollateralMDAO().loadCollateralM(result.getAppRecordID()));
			
			
			logger.debug("result.getVehicleDataM().getVehicleID() = "+result.getVehicleDataM().getVehicleID());
			
			return result;
		} catch (Exception e) {
			log.fatal(e.getLocalizedMessage());
			throw new OrigApplicationMException(e.getMessage());
		}
	}
	
	public ApplicationDataM loadModelOrigApplicationMForDrawDown(String applicationRecordId)throws OrigApplicationMException {
		try {
			//Load Applciation
			ApplicationDataM result = selectTableORIG_APPLICATIONForDrawDown(applicationRecordId);
			result = selectImageData(applicationRecordId, result);
			
			//Load Notepad
			OrigNotePadDataMDAO notePadDataMDAO = ORIGDAOFactory.getOrigNotePadDataMDAO();
			result.setNotepadDataM(notePadDataMDAO.loadModelOrigNotePadDataM(applicationRecordId));

			//Load Other Name
			OrigOtherNameMDAO otherNameMDAO = ORIGDAOFactory.getOrigOtherNameMDAO();
			result.setOtherNameDataM(otherNameMDAO.loadModelOtherNameDataM(applicationRecordId));
			
			//Load Proposal
			/*OrigProposalMDAO origProposalMDAO = ORIGDAOFactory.getOrigProposalDAO();
			result.setProposalDataM(origProposalMDAO.loadModelOrigProposalM(applicationRecordId));*/
			
			//Load Attachment list
			//OrigAttachmentHistoryMDAO attachmentHistoryMDAO = ORIGDAOFactory.getOrigAttachmentHistoryMDAO();
			//result.setAttachmentVect(attachmentHistoryMDAO.loadModelOrigAttachmentHistoryM(applicationRecordId));
			
			//Load Documnet list
			OrigDocumentCheckListMDAO documentCheckListMDAO = ORIGDAOFactory.getOrigDocumentCheckListMDAO();
			result.setDocumentCheckListDataM(documentCheckListMDAO.loadModelOrigDocumentCheckListM(applicationRecordId));
			
			//Load Action Flow
			OrigApplicationLogMDAO logMDAO = ORIGDAOFactory.getOrigApplicationLogMDAO();
			result.setActionFlowVect(logMDAO.loadModelOrigApplicationLogM(applicationRecordId));
			
			return result;
		} catch (Exception e) {
			log.fatal(e.getLocalizedMessage());
			throw new OrigApplicationMException(e.getMessage());
		}
	}
	
	/**
	 * @param applicationRecordId
	 * @return
	 */
	private ApplicationDataM selectTableORIG_APPLICATION(String applicationRecordId, ApplicationDataM applicationDataM) throws OrigApplicationMException {
		PreparedStatement ps = null;
		ResultSet rs = null;
		Connection conn = null;
		try {
			//conn = Get Connection
			conn = getConnection();
			StringBuffer sql = new StringBuffer("");
			sql
					.append("SELECT APPLICATION_NO,IDENTIFICATION_NO,OFFICE_CODE,DEALER_CODE,SELLER_CODE");
			sql
					.append(",SALEMAN_CODE,CREATE_BY,CREATE_DATE,UPDATE_BY,UPDATE_DATE");
			sql
					.append(",CONSENT,CONSENT_DATE,CMR_START_DATE,CMR_END_DATE,CMR_FIRST_ID");
			sql
					.append(",CMR_LAST_ID,DE_START_DATE,DE_LAST_DATE,DE_FIRST_ID,DE_LAST_ID");
			sql
					.append(",CMR_EX_START_DATE,CMR_EX_END_DATE,CMR_EX_FIRST_ID,CMR_EX_LAST_ID,UW_START_DATE");
			sql
					.append(",UW_END_DATE,UW_FIRST_ID,UW_LAST_ID,PD_START_DATE,PD_END_DATE");
			sql
					.append(",PD_FIRST_ID,PD_LAST_ID,ESCALATE_BY,ESCALATE_TO,ESCALATE_DATE");
			sql
					.append(",APPLICATION_STATUS,JOB_STATE,LOAN_TYPE,CMR_CODE");
			sql
					.append(",AREA_CODE,SRCOFCUS, UW_DECISION, PD_DECISION, XCMR_OVERRIDE_BY");
			sql
					.append(", XCMR_OVERRIDE_DATE, XCMR_DECISION, UW_DECISION_REASON,DRAW_DOWN_FLAG, DE_DECISION");
			sql
					.append(", PRIORITY, PD_DECISION_REASON,SCORING_RESULT ");
			sql.append(" ,POLICY_VERSION ,XUW_POLICY_EXCEPTION,XUW_DECISION,XUW_DECISION_REASON,XUW_OVERRIDE_BY  ");
			sql.append(" ,XUW_OVERRIDE_DATE,XUW_START_DATE ,XUW_END_DATE,XUW_FIRST_ID,XUW_LAST_ID, BUSINESS_CLASS_ID, FINAL_CREDIT_LIMIT,GROUP_APP_ID");
			sql
					.append(" FROM ORIG_APPLICATION  WHERE APPLICATION_RECORD_ID = ? ");
			String dSql = String.valueOf(sql);
			log.debug("Sql=" + dSql);
			ps = conn.prepareStatement(dSql);
			rs = null;
			ps.setString(1, applicationRecordId);

			rs = ps.executeQuery();
			//ApplicationDataM applicationDataM = null;

			if (rs.next()) {
				if(applicationDataM == null){
					applicationDataM = new ApplicationDataM();
				}
				applicationDataM.setAppRecordID(applicationRecordId);
				applicationDataM.setApplicationNo(rs.getString(1));
				applicationDataM.setIdentificationNo(rs.getString(2));
				applicationDataM.setOfficeCode(rs.getString(3));
				applicationDataM.setDealerCode(rs.getString(4));
				applicationDataM.setSellerCode(rs.getString(5));
				applicationDataM.setSalesManCode(rs.getString(6));
				applicationDataM.setCreateBy(rs.getString(7));
				applicationDataM.setCreateDate(rs.getTimestamp(8));
				applicationDataM.setUpdateBy(rs.getString(9));
				applicationDataM.setUpdateDate(rs.getTimestamp(10));
				applicationDataM.setConsent(rs.getString(11));
				applicationDataM.setConsentDate(rs.getDate(12));
				applicationDataM.setCmrStartDate(rs.getDate(13));
				applicationDataM.setCmrEndDate(rs.getDate(14));
				applicationDataM.setCmrFirstId(rs.getString(15));
				applicationDataM.setCmrLastId(rs.getString(16));
				applicationDataM.setDeStartDate(rs.getDate(17));
				applicationDataM.setDeLastDate(rs.getDate(18));
				applicationDataM.setDeFirstId(rs.getString(19));
				applicationDataM.setDeLastId(rs.getString(20));
				applicationDataM.setCmrExStartDate(rs.getDate(21));
				applicationDataM.setCmrExEndDate(rs.getDate(22));
				applicationDataM.setCmrExFirstId(rs.getString(23));
				applicationDataM.setCmrExLastId(rs.getString(24));
				applicationDataM.setUwStartDate(rs.getDate(25));
				applicationDataM.setUwEndDate(rs.getDate(26));
				applicationDataM.setUwFirstId(rs.getString(27));
				applicationDataM.setUwLastId(rs.getString(28));
				applicationDataM.setPdStartDate(rs.getDate(29));
				applicationDataM.setPdEndDate(rs.getDate(30));
				applicationDataM.setPdFirstId(rs.getString(31));
				applicationDataM.setPdLastId(rs.getString(32));
				applicationDataM.setEscalateBy(rs.getString(33));
				applicationDataM.setEscalateTo(rs.getString(34));
				applicationDataM.setEscalateDate(rs.getDate(35));
				applicationDataM.setApplicationStatus(rs.getString(36));
				applicationDataM.setJobState(rs.getString(37));
				applicationDataM.setLoanType(rs.getString(38));
				applicationDataM.setCmrCode(rs.getString(39));
				applicationDataM.setAreaCode(rs.getString(40));
				applicationDataM.setSourceOfCustomer(rs.getString(41));
				applicationDataM.setUwDecision(rs.getString(42));
				applicationDataM.setPdDecision(rs.getString(43));
				applicationDataM.setXcmrOverrideBy(rs.getString(44));
				applicationDataM.setXcmrOverrideDate(rs.getDate(45));
				applicationDataM.setXcmrDecision(rs.getString(46));
				applicationDataM.setUwDecisionReason(rs.getString(47));
				applicationDataM.setDrawDownFlag(rs.getString(48));
				applicationDataM.setDeDecision(rs.getString(49));
				applicationDataM.setPriority(rs.getString(50));
				applicationDataM.setPdDecisionReason(rs.getString(51));
				applicationDataM.setScorringResult(rs.getString(52));
				applicationDataM.setPolicyVersion(rs.getString(53));
				applicationDataM.setXuwPolicyException(rs.getString(54));
				applicationDataM.setXuwDecision(rs.getString(55));
				applicationDataM.setXuwDecisionReason(rs.getString(56));
				applicationDataM.setXuwOverrideBy(rs.getString(57));
				applicationDataM.setXuwOverrideDate(rs.getTimestamp(58));
				applicationDataM.setXuwStartDate(rs.getTimestamp(59));
				applicationDataM.setXuwEndDate(rs.getTimestamp(60));
				applicationDataM.setXuwFirstId(rs.getString(61));
				applicationDataM.setXuwLastId(rs.getString(62));
				applicationDataM.setBusinessClassId(rs.getString(63));
				applicationDataM.setFinalCreditLimit(rs.getBigDecimal(64));
				applicationDataM.setGroupAppID(rs.getString("GROUP_APP_ID"));
			}

			return applicationDataM;
		} catch (Exception e) {
			log.fatal(e.getLocalizedMessage());
			throw new OrigApplicationMException(e.getMessage());
		} finally {
			try {
				closeConnection(conn, rs, ps);
			} catch (Exception e) {
				log.fatal(e.getLocalizedMessage());
			}
		}
	}
	
	/**
	 * @param applicationRecordId
	 * @return
	 */
	private ApplicationDataM selectTableORIG_APPLICATION(
			String applicationRecordId) throws OrigApplicationMException {
		PreparedStatement ps = null;
		ResultSet rs = null;
		Connection conn = null;
		try {
			//conn = Get Connection
			conn = getConnection();
			StringBuffer sql = new StringBuffer("");
			sql
					.append("SELECT APPLICATION_NO,IDENTIFICATION_NO,OFFICE_CODE,DEALER_CODE,SELLER_CODE");
			sql
					.append(",SALEMAN_CODE,CREATE_BY,CREATE_DATE,UPDATE_BY,UPDATE_DATE");
			sql
					.append(",CONSENT,CONSENT_DATE,CMR_START_DATE,CMR_END_DATE,CMR_FIRST_ID");
			sql
					.append(",CMR_LAST_ID,DE_START_DATE,DE_LAST_DATE,DE_FIRST_ID,DE_LAST_ID");
			sql
					.append(",CMR_EX_START_DATE,CMR_EX_END_DATE,CMR_EX_FIRST_ID,CMR_EX_LAST_ID,UW_START_DATE");
			sql
					.append(",UW_END_DATE,UW_FIRST_ID,UW_LAST_ID,PD_START_DATE,PD_END_DATE");
			sql
					.append(",PD_FIRST_ID,PD_LAST_ID,ESCALATE_BY,ESCALATE_TO,ESCALATE_DATE");
			sql
					.append(",APPLICATION_STATUS,JOB_STATE,LOAN_TYPE,CMR_CODE");
			sql
					.append(",AREA_CODE,SRCOFCUS, UW_DECISION, PD_DECISION, XCMR_OVERRIDE_BY");
			sql
					.append(", XCMR_OVERRIDE_DATE, XCMR_DECISION, UW_DECISION_REASON,DRAW_DOWN_FLAG, DE_DECISION");
			sql
					.append(", PRIORITY, PD_DECISION_REASON,SCORING_RESULT ");
			sql.append(" ,POLICY_VERSION ,XUW_POLICY_EXCEPTION,XUW_DECISION,XUW_DECISION_REASON,XUW_OVERRIDE_BY  ");
			sql.append(" ,XUW_OVERRIDE_DATE,XUW_START_DATE ,XUW_END_DATE,XUW_FIRST_ID,XUW_LAST_ID, BUSINESS_CLASS_ID, FINAL_CREDIT_LIMIT , GROUP_APP_ID ");
			sql
					.append(" FROM ORIG_APPLICATION  WHERE APPLICATION_RECORD_ID = ? ");
			String dSql = String.valueOf(sql);
			log.debug("Sql=" + dSql);
			ps = conn.prepareStatement(dSql);
			rs = null;
			ps.setString(1, applicationRecordId);

			rs = ps.executeQuery();
			ApplicationDataM applicationDataM = null;

			if (rs.next()) {
				applicationDataM = new ApplicationDataM();
				applicationDataM.setAppRecordID(applicationRecordId);
				applicationDataM.setApplicationNo(rs.getString(1));
				applicationDataM.setIdentificationNo(rs.getString(2));
				applicationDataM.setOfficeCode(rs.getString(3));
				applicationDataM.setDealerCode(rs.getString(4));
				applicationDataM.setSellerCode(rs.getString(5));
				applicationDataM.setSalesManCode(rs.getString(6));
				applicationDataM.setCreateBy(rs.getString(7));
				applicationDataM.setCreateDate(rs.getTimestamp(8));
				applicationDataM.setUpdateBy(rs.getString(9));
				applicationDataM.setUpdateDate(rs.getTimestamp(10));
				applicationDataM.setConsent(rs.getString(11));
				applicationDataM.setConsentDate(rs.getDate(12));
				applicationDataM.setCmrStartDate(rs.getDate(13));
				applicationDataM.setCmrEndDate(rs.getDate(14));
				applicationDataM.setCmrFirstId(rs.getString(15));
				applicationDataM.setCmrLastId(rs.getString(16));
				applicationDataM.setDeStartDate(rs.getDate(17));
				applicationDataM.setDeLastDate(rs.getDate(18));
				applicationDataM.setDeFirstId(rs.getString(19));
				applicationDataM.setDeLastId(rs.getString(20));
				applicationDataM.setCmrExStartDate(rs.getDate(21));
				applicationDataM.setCmrExEndDate(rs.getDate(22));
				applicationDataM.setCmrExFirstId(rs.getString(23));
				applicationDataM.setCmrExLastId(rs.getString(24));
				applicationDataM.setUwStartDate(rs.getDate(25));
				applicationDataM.setUwEndDate(rs.getDate(26));
				applicationDataM.setUwFirstId(rs.getString(27));
				applicationDataM.setUwLastId(rs.getString(28));
				applicationDataM.setPdStartDate(rs.getDate(29));
				applicationDataM.setPdEndDate(rs.getDate(30));
				applicationDataM.setPdFirstId(rs.getString(31));
				applicationDataM.setPdLastId(rs.getString(32));
				applicationDataM.setEscalateBy(rs.getString(33));
				applicationDataM.setEscalateTo(rs.getString(34));
				applicationDataM.setEscalateDate(rs.getDate(35));
				applicationDataM.setApplicationStatus(rs.getString(36));
				applicationDataM.setJobState(rs.getString(37));
				applicationDataM.setLoanType(rs.getString(38));
				applicationDataM.setCmrCode(rs.getString(39));
				applicationDataM.setAreaCode(rs.getString(40));
				applicationDataM.setSourceOfCustomer(rs.getString(41));
				applicationDataM.setUwDecision(rs.getString(42));
				applicationDataM.setPdDecision(rs.getString(43));
				applicationDataM.setXcmrOverrideBy(rs.getString(44));
				applicationDataM.setXcmrOverrideDate(rs.getDate(45));
				applicationDataM.setXcmrDecision(rs.getString(46));
				applicationDataM.setUwDecisionReason(rs.getString(47));
				applicationDataM.setDrawDownFlag(rs.getString(48));
				applicationDataM.setDeDecision(rs.getString(49));
				applicationDataM.setPriority(rs.getString(50));
				applicationDataM.setPdDecisionReason(rs.getString(51));
				applicationDataM.setScorringResult(rs.getString(52));
				applicationDataM.setPolicyVersion(rs.getString(53));
				applicationDataM.setXuwPolicyException(rs.getString(54));
				applicationDataM.setXuwDecision(rs.getString(55));
				applicationDataM.setXuwDecisionReason(rs.getString(56));
				applicationDataM.setXuwOverrideBy(rs.getString(57));
				applicationDataM.setXuwOverrideDate(rs.getTimestamp(58));
				applicationDataM.setXuwStartDate(rs.getTimestamp(59));
				applicationDataM.setXuwEndDate(rs.getTimestamp(60));
				applicationDataM.setXuwFirstId(rs.getString(61));
				applicationDataM.setXuwLastId(rs.getString(62));
				applicationDataM.setBusinessClassId(rs.getString(63));
				applicationDataM.setFinalCreditLimit(rs.getBigDecimal(64));
				String rr = rs.getString("GROUP_APP_ID");
				String r1 = rs.getString(65);
				applicationDataM.setGroupAppID(rs.getString("GROUP_APP_ID"));
			}

			return applicationDataM;
		} catch (Exception e) {
			log.fatal(e.getLocalizedMessage());
			throw new OrigApplicationMException(e.getMessage());
		} finally {
			try {
				closeConnection(conn, rs, ps);
			} catch (Exception e) {
				log.fatal(e.getLocalizedMessage());
			}
		}
	}
	/**
	 * @param applicationRecordId
	 * @return
	 */
	private ApplicationDataM selectTableORIG_APPLICATIONForDrawDown(
			String applicationRecordId) throws OrigApplicationMException {
		PreparedStatement ps = null;
		ResultSet rs = null;
		Connection conn = null;
		try {
			//conn = Get Connection
			conn = getConnection();
			StringBuffer sql = new StringBuffer("");
			sql
					.append("SELECT OFFICE_CODE,DEALER_CODE,SELLER_CODE,SALEMAN_CODE,DRAW_DOWN_FLAG");
			sql
					.append(",CONSENT,CONSENT_DATE,CMR_CODE,AREA_CODE,SRCOFCUS");
			sql
					.append(" FROM ORIG_APPLICATION  WHERE APPLICATION_RECORD_ID = ? ");
			
			String dSql = String.valueOf(sql);
			log.debug("Sql=" + dSql);
			ps = conn.prepareStatement(dSql);
			rs = null;
			ps.setString(1, applicationRecordId);

			rs = ps.executeQuery();
			ApplicationDataM applicationDataM = null;

			if (rs.next()) {
				applicationDataM = new ApplicationDataM();
				applicationDataM.setOfficeCode(rs.getString(1));
				applicationDataM.setDealerCode(rs.getString(2));
				applicationDataM.setSellerCode(rs.getString(3));
				applicationDataM.setSalesManCode(rs.getString(4));
				applicationDataM.setDrawDownFlag(rs.getString(5));
				applicationDataM.setConsent(rs.getString(6));
				applicationDataM.setConsentDate(rs.getDate(7));
				applicationDataM.setCmrCode(rs.getString(8));
				applicationDataM.setAreaCode(rs.getString(9));
				applicationDataM.setSourceOfCustomer(rs.getString(10));
			}

			return applicationDataM;
		} catch (Exception e) {
			log.fatal(e.getLocalizedMessage());
			throw new OrigApplicationMException(e.getMessage());
		} finally {
			try {
				closeConnection(conn, rs, ps);
			} catch (Exception e) {
				log.fatal(e.getLocalizedMessage());
			}
		}
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.eaf.orig.share.dao.OrigApplicationMDAO#saveUpdateModelOrigApplicationM(com.eaf.orig.shared.model.ApplicationDataM)
	 */
	public Vector saveUpdateModelOrigApplicationM(
			ApplicationDataM prmApplicationDataM, String role)
			throws OrigApplicationMException {
		int returnRows = 0;
		try {
			String appRecordID = null;
			VehicleDataM prmVehicleDataM = prmApplicationDataM.getVehicleDataM();
			if(("Y").equals(prmApplicationDataM.getDrawDownFlag())){
				//Check car
				appRecordID = ORIGDAOFactory.getOrigVehicleMDAO().selectAppRecordIdByVehicleId(prmVehicleDataM.getVehicleID());
				logger.debug("appRecordID = "+appRecordID);
			}
			if(appRecordID == null || prmApplicationDataM.getAppRecordID().equals(appRecordID)){
				returnRows = updateTableORIG_APPLICATION(prmApplicationDataM);
				if (returnRows == 0) {
					log.debug("New record then can't update record in table ORIG_APPLICATION then call Insert method");
					//Get Application Record ID
					/*String prmApplicationRecordID = ORIGDAOFactory.getUniqueIDGeneratorDAO2().getUniqByName(EJBConstant.APPLICATION_ID); 
					prmApplicationDataM.setAppRecordID(prmApplicationRecordID);*/
					
					if(prmApplicationDataM.getApplicationNo()==null||"".equals(prmApplicationDataM.getApplicationNo())){
					//Get Application Number
						//String applicationNo = ORIGDAOFactory.getUniqueIDGeneratorDAO2().getApplicationNo(prmApplicationDataM);
						//prmApplicationDataM.setApplicationNo(applicationNo);
						ORIGGeneratorManager generatorManager = ORIGEJBService.getGeneratorManager();
						String applicationNo = generatorManager.generateApplicationNo(prmApplicationDataM);
						prmApplicationDataM.setApplicationNo(applicationNo);
					}
					createTableORIG_APPLICATION(prmApplicationDataM);
				}
				String prmApplicationRecordID = prmApplicationDataM.getAppRecordID();
				
				//update Persoanl Info
				Vector vPersoanlInfo = prmApplicationDataM.getPersonalInfoVect();
				OrigApplicationCustomerMDAO origApplicationCustomerMDAO = ORIGDAOFactory
						.getOrigApplicationCustomerMDAO();
				String customerType = "";
				if (vPersoanlInfo != null) {
					for (int i = 0; i < vPersoanlInfo.size(); i++) {
						PersonalInfoDataM  personalInfoDataM= (PersonalInfoDataM) vPersoanlInfo.get(i);
						personalInfoDataM.setAppRecordID(prmApplicationRecordID);
						if(personalInfoDataM.getCreateBy() == null || ("").equals(personalInfoDataM.getCreateBy())){
							personalInfoDataM.setCreateBy(prmApplicationDataM.getUpdateBy());
						}else{
							personalInfoDataM.setUpdateBy(prmApplicationDataM.getUpdateBy());
						}
						//String personalID = ORIGDAOFactory.getUniqueIDGeneratorDAO2().getUniqByName(EJBConstant.PERSONAL_ID);
						//personalInfoDataM.setPersonalID(personalID);
						log.debug("prmApplicationRecordID = "+prmApplicationRecordID);
						log.debug("personalInfoDataM.getPersonalID() = "+personalInfoDataM.getPersonalID());
						log.debug("personalInfoDataM.getIdNo() = "+personalInfoDataM.getIdNo());
						log.debug("personalInfoDataM.getPersonalType() = "+personalInfoDataM.getPersonalType());
						origApplicationCustomerMDAO.saveUpdateModelOrigApplicationCustomerM(personalInfoDataM);
						if(OrigConstant.PERSONAL_TYPE_APPLICANT.equals(personalInfoDataM.getPersonalType())){
							customerType = personalInfoDataM.getCustomerType();
						}
						//set Office Code
						personalInfoDataM.setOfficeCode(prmApplicationDataM.getOfficeCode());
						
					}
				}
				//update Card Information
				Vector vCardInformation = prmApplicationDataM.getCardInformation();
				OrigApplicationCardInformationMDAO origApplicationCardInformationMDAO = ORIGDAOFactory.getOrigApplicationCardInformationMDAO();
				if (vCardInformation != null) {
					for (int i = 0; i < vCardInformation.size(); i++) {
						CardInformationDataM  cardInformationDataM= (CardInformationDataM) vCardInformation.get(i);
						cardInformationDataM.setApplication_record_id(prmApplicationRecordID);
						if(cardInformationDataM.getCreatedBy() == null || ("").equals(cardInformationDataM.getCreatedBy())){
							cardInformationDataM.setCreatedBy(prmApplicationDataM.getUpdateBy());
						}else{
							cardInformationDataM.setUpdatedBy(prmApplicationDataM.getUpdateBy());
						}
						log.debug("cardInformationDataM.getApplication_record_id() = "+cardInformationDataM.getApplication_record_id());
						log.debug("cardInformationDataM.getIdNo() = "+cardInformationDataM.getIdNo());
						log.debug("cardInformationDataM.getApplicationType() = "+cardInformationDataM.getApplicationType());
						log.debug("cardInformationDataM.getCardFace() = "+cardInformationDataM.getCardFace());
						log.debug("cardInformationDataM.getCardNo() = "+cardInformationDataM.getCardNo());
						log.debug("cardInformationDataM.getCardSeq() = "+cardInformationDataM.getCardSeq());
						log.debug("cardInformationDataM.getCardStatus() = "+cardInformationDataM.getCardStatus());
						log.debug("cardInformationDataM.getCardType() = "+cardInformationDataM.getCardType());
						log.debug("cardInformationDataM.getCreatedBy() = "+cardInformationDataM.getCreatedBy());
						log.debug("cardInformationDataM.getEmbossName() = "+cardInformationDataM.getEmbossName());
						log.debug("cardInformationDataM.getUpdatedBy() = "+cardInformationDataM.getUpdatedBy());
						log.debug("cardInformationDataM.getCreatedDate() = "+cardInformationDataM.getCreatedDate());
						log.debug("cardInformationDataM.getUpdatedDate() = "+cardInformationDataM.getUpdatedDate());
						log.debug("cardInformationDataM.getValidFromDate() = "+cardInformationDataM.getValidFromDate());
						log.debug("cardInformationDataM.getValidThruDate() = "+cardInformationDataM.getValidThruDate());
						origApplicationCardInformationMDAO.saveUpdateModelOrigApplicationCardInformationDataM(cardInformationDataM);
					}
				}
				
				//update loan
				Vector vLoanDataM = prmApplicationDataM.getLoanVect();
				OrigLoanMDAO origLoanMDAO = ORIGDAOFactory.getOrigLoanMDAO();
				if (vLoanDataM != null) {
					for (int i = 0; i < vLoanDataM.size(); i++) {
						LoanDataM loanDataM = (LoanDataM) vLoanDataM.get(i);
						if(loanDataM.getCreateBy() == null || ("").equals(loanDataM.getCreateBy())){
							loanDataM.setCreateBy(prmApplicationDataM.getUpdateBy());
						}else{
							loanDataM.setUpdateBy(prmApplicationDataM.getUpdateBy());
						}
						logger.debug("loanDataM.getAppRecordID() = "+loanDataM.getAppRecordID());
						if((loanDataM.getAppRecordID() == null || ("").equals(loanDataM.getAppRecordID())) && !("Y").equals(prmApplicationDataM.getDrawDownFlag())){
							loanDataM.setAppRecordID(prmApplicationRecordID);
							origLoanMDAO.saveUpdateModelOrigLoanMForCreateProposal(loanDataM);
						}else{
							if(("Y").equals(prmApplicationDataM.getDrawDownFlag())){
								//Update when change car
								origLoanMDAO.updateLoanForChangeCar(loanDataM.getLoanID(), prmApplicationRecordID);
							}else{
								loanDataM.setAppRecordID(prmApplicationRecordID);
								origLoanMDAO.saveUpdateModelOrigLoanM(loanDataM);
							}
						}
					}
				}
				//update vehicle
				if(prmVehicleDataM!=null){
					logger.debug("prmVehicleDataM.getAppRecordID() = "+prmVehicleDataM.getAppRecordID());
					if(prmVehicleDataM.getCreateBy() == null || ("").equals(prmVehicleDataM.getCreateBy())){
						prmVehicleDataM.setCreateBy(prmApplicationDataM.getUpdateBy());
					}else{
						prmVehicleDataM.setUpdateBy(prmApplicationDataM.getUpdateBy());
					}
					if((prmVehicleDataM.getAppRecordID() == null || ("").equals(prmVehicleDataM.getAppRecordID())) && !("Y").equals(prmApplicationDataM.getDrawDownFlag())){
						prmVehicleDataM.setAppRecordID(prmApplicationRecordID);
						ORIGDAOFactory.getOrigVehicleMDAO().saveUpdateModelVehicleDataMForCreateProposal(prmVehicleDataM);
					}else{
						if(("Y").equals(prmApplicationDataM.getDrawDownFlag())){
							//Update when change car
							prmVehicleDataM.setAppRecordID(prmApplicationRecordID);
							ORIGDAOFactory.getOrigVehicleMDAO().updateVehicleForChangeCar(prmVehicleDataM);
						}else{
							prmVehicleDataM.setAppRecordID(prmApplicationRecordID);
							logger.debug("prmApplicationRecordID = "+prmApplicationRecordID);
							ORIGDAOFactory.getOrigVehicleMDAO().saveUpdateModelVehicleDataM(prmVehicleDataM);
						}
					}
				}
				
				//update notePad
				Vector notepadVect = prmApplicationDataM.getNotepadDataM();
				OrigNotePadDataMDAO notePadDataMDAO = ORIGDAOFactory.getOrigNotePadDataMDAO();
				if (notepadVect != null) {
					for (int i = 0; i < notepadVect.size(); i++) {
						NotePadDataM notePadDataM = (NotePadDataM) notepadVect.get(i);
						if(notePadDataM.getCreateBy() == null || ("").equals(notePadDataM.getCreateBy())){
							notePadDataM.setCreateBy(prmApplicationDataM.getUpdateBy());
						}else{
							notePadDataM.setUpdateBy(prmApplicationDataM.getUpdateBy());
						}
						notePadDataM.setApplicationRecordId(prmApplicationRecordID);
						notePadDataMDAO.saveUpdateModelOrigNotePadDataM(notePadDataM);
					}
				}
				
				//update document list
				Vector documentVect = prmApplicationDataM.getDocumentCheckListDataM();
				OrigDocumentCheckListMDAO documentCheckListMDAO = ORIGDAOFactory.getOrigDocumentCheckListMDAO();
				if (documentVect != null) {
					DocumentCheckListDataM documentCheckListDataM;
					for (int i = 0; i < documentVect.size(); i++) {
						documentCheckListDataM = (DocumentCheckListDataM) documentVect.get(i);
						documentCheckListDataM.setApplicationRecordId(prmApplicationRecordID);
						if(documentCheckListDataM.getCreateBy() == null || ("").equals(documentCheckListDataM.getCreateBy())){
							documentCheckListDataM.setCreateBy(prmApplicationDataM.getUpdateBy());
						}
						documentCheckListMDAO.saveUpdateModelOrigDocumentCheckListM(documentCheckListDataM);
					}
				}
				
				//update other name
				Vector otherNameVect = prmApplicationDataM.getOtherNameDataM();
				OrigOtherNameMDAO otherNameMDAO = ORIGDAOFactory.getOrigOtherNameMDAO();
				if (otherNameVect != null) {
					OtherNameDataM otherNameDataM;
					for (int i = 0; i < otherNameVect.size(); i++) {
						otherNameDataM = (OtherNameDataM) otherNameVect.get(i);
						otherNameDataM.setApplicationRecordId(prmApplicationRecordID);
						if(otherNameDataM.getCreateBy() == null || ("").equals(otherNameDataM.getCreateBy())){
							otherNameDataM.setCreateBy(prmApplicationDataM.getUpdateBy());
						}else{
							otherNameDataM.setUpdateBy(prmApplicationDataM.getUpdateBy());
						}
						otherNameMDAO.saveUpdateModelOtherNameDataM(otherNameDataM);
					}
				}
				
				//update reason
				Vector reasonVect = prmApplicationDataM.getReasonVect();
				OrigReasonMDAO reasonMDAO = ORIGDAOFactory.getOrigReasonMDAO();
				OrigReasonLogMDAO reasonLogMDAO = ORIGDAOFactory.getOrigReasonLogMDAO();
				if (reasonVect != null) {
					ReasonDataM reasonDataM;
					for (int i = 0; i < reasonVect.size(); i++) {
						reasonDataM = (ReasonDataM) reasonVect.get(i);
						if(reasonDataM.getCreateBy() == null || ("").equals(reasonDataM.getCreateBy())){
							reasonDataM.setCreateBy(prmApplicationDataM.getUpdateBy());
						}else{
							reasonDataM.setUpdateBy(prmApplicationDataM.getUpdateBy());
						}
						reasonDataM.setApplicationRecordId(prmApplicationRecordID);
						reasonMDAO.saveUpdateModelOrigReasonM(reasonDataM);
						if(role.equals(reasonDataM.getRole())){
							reasonLogMDAO.createModelOrigReasonLogM(reasonDataM);
						}
					}
				}
				
				//update proposal
				ProposalDataM prmProposalDataM = prmApplicationDataM.getProposalDataM();
				if(prmProposalDataM!=null){
					prmProposalDataM.setAppRecID(prmApplicationRecordID);
					ORIGDAOFactory.getOrigProposalDAO().saveUpdateModelOrigProposalM(prmProposalDataM);
				}
				
				//update attachment list
				Vector attachVect = prmApplicationDataM.getAttachmentVect();
				OrigAttachmentHistoryMDAO attachmentHistoryMDAO = ORIGDAOFactory.getOrigAttachmentHistoryMDAO();
				if (attachVect != null) {
					AttachmentHistoryDataM attachmentHistoryDataM;
					for (int i = 0; i < attachVect.size(); i++) {
						attachmentHistoryDataM = (AttachmentHistoryDataM) attachVect.get(i);
						attachmentHistoryDataM.setApplicationRecordId(prmApplicationRecordID);
						if(attachmentHistoryDataM.getCreateBy() == null || ("").equals(attachmentHistoryDataM.getCreateBy())){
							attachmentHistoryDataM.setCreateBy(prmApplicationDataM.getUpdateBy());
						}else{
							attachmentHistoryDataM.setUpdateBy(prmApplicationDataM.getUpdateBy());
						}
						attachmentHistoryMDAO.saveUpdateModelOrigAttachmentHistoryM(attachmentHistoryDataM);
					}
				}
				
				//update collateral
				Vector vCollateral = prmApplicationDataM.getCollateralVect();
				ORIGDAOFactory.getOrigMGCollateralMDAO().updateCollateralM(vCollateral,prmApplicationDataM.getAppRecordID(),prmApplicationDataM.getCreateBy());
				
			
				/** delete not in key **/
				//Delete Guarantor
				//Vector guarantorIDVect = deleteNotInKeyApplicationData(prmApplicationDataM, role);
				
				//Update approval group for uw eacalate application
				if(ORIGWFConstant.ApplicationDecision.ESCALATE.equals(prmApplicationDataM.getAppDecision())){
					OrigApplicationApprvGroupMDAO applicationApprvGroupMDAO = ORIGDAOFactory.getOrigApplicationApprvGroupMDAO();
					//VehicleDataM vehicleDataM = prmApplicationDataM.getVehicleDataM();
					
					log.debug("vehicleDataM > "+prmApplicationDataM.getVehicleDataM());
					log.debug("prmApplicationDataM.getEscalateTo() > "+prmApplicationDataM.getEscalateTo());
					log.debug("prmApplicationRecordID > "+prmApplicationRecordID);
					//log.debug("vehicleDataM.getCar() > "+vehicleDataM.getCar());
					log.debug("customerType > "+customerType);
					log.debug("prmApplicationDataM.getUpdateBy() > "+prmApplicationDataM.getUpdateBy());
		
					applicationApprvGroupMDAO.saveUpdateModelOrigApplicationApprvGroupM(prmApplicationDataM.getEscalateTo(),prmApplicationRecordID,"", prmApplicationDataM.getUpdateBy(), customerType);
				}
				return null;
			}
			return null;
		} catch (Exception e) {
			log.fatal("Error >> ", e);
			throw new OrigApplicationMException(e.getMessage());
		}
	}
	
	/*
	 * (non-Javadoc)
	 * 
	 * @see com.eaf.orig.share.dao.OrigApplicationMDAO#saveUpdateModelOrigApplicationM(com.eaf.orig.shared.model.ApplicationDataM)
	 */
	public Vector saveUpdateModelOrigApplicationMForCreateCar(ApplicationDataM prmApplicationDataM, String role)throws OrigApplicationMException {
		int returnRows = 0;
		try {
			returnRows = updateTableORIG_APPLICATION(prmApplicationDataM);
//			if (returnRows == 0) {
//				log
//						.debug("New record then can't update record in table ORIG_APPLICATION then call Insert method");
//				//Get Application Record ID
//				/*String prmApplicationRecordID = ORIGDAOFactory.getUniqueIDGeneratorDAO2().getUniqByName(EJBConstant.APPLICATION_ID); 
//				prmApplicationDataM.setAppRecordID(prmApplicationRecordID);*/
//				
//				//Get Application Number
//				String applicationNo = ORIGDAOFactory.getUniqueIDGeneratorDAO2().getApplicationNo(prmApplicationDataM);
//				prmApplicationDataM.setApplicationNo(applicationNo);
//				
//				createTableORIG_APPLICATION(prmApplicationDataM);
//			}
//			String prmApplicationRecordID = prmApplicationDataM.getAppRecordID();
			
			//update Persoanl Info
			Vector vPersoanlInfo = prmApplicationDataM.getPersonalInfoVect();
			OrigApplicationCustomerMDAO origApplicationCustomerMDAO = ORIGDAOFactory
					.getOrigApplicationCustomerMDAO();
			if (vPersoanlInfo != null) {
				for (int i = 0; i < vPersoanlInfo.size(); i++) {
					PersonalInfoDataM  personalInfoDataM= (PersonalInfoDataM) vPersoanlInfo.get(i);
					if(personalInfoDataM.getCreateBy() == null || ("").equals(personalInfoDataM.getCreateBy())){
						personalInfoDataM.setCreateBy(prmApplicationDataM.getCreateBy());
					}else{
						personalInfoDataM.setUpdateBy(prmApplicationDataM.getUpdateBy());
					}
					//personalInfoDataM.setAppRecordID(prmApplicationRecordID);
					personalInfoDataM.setOfficeCode(prmApplicationDataM.getOfficeCode());
					origApplicationCustomerMDAO.saveUpdateModelOrigApplicationCustomerM(personalInfoDataM);
				}
			}
			
			//update loan
			Vector vLoanDataM = prmApplicationDataM.getLoanVect();
			OrigLoanMDAO origLoanMDAO = ORIGDAOFactory.getOrigLoanMDAO();
			if (vLoanDataM != null) {
				for (int i = 0; i < vLoanDataM.size(); i++) {
					LoanDataM loanDataM = (LoanDataM) vLoanDataM.get(i);
					//loanDataM.setAppRecordID(prmApplicationRecordID);
					if(loanDataM.getCreateBy() == null || ("").equals(loanDataM.getCreateBy())){
						loanDataM.setCreateBy(prmApplicationDataM.getCreateBy());
					}else{
						loanDataM.setUpdateBy(prmApplicationDataM.getUpdateBy());
					}
					origLoanMDAO.saveUpdateModelOrigLoanMForCreateProposal(loanDataM);
				}
			}
			//update vehicle
			VehicleDataM prmVehicleDataM = prmApplicationDataM.getVehicleDataM();
			if(prmVehicleDataM!=null){
				//prmVehicleDataM.setAppRecordID(prmApplicationRecordID);
				if(prmVehicleDataM.getCreateBy() == null || ("").equals(prmVehicleDataM.getCreateBy())){
					prmVehicleDataM.setCreateBy(prmApplicationDataM.getCreateBy());
				}else{
					prmVehicleDataM.setUpdateBy(prmApplicationDataM.getUpdateBy());
				}
				ORIGDAOFactory.getOrigVehicleMDAO().saveUpdateModelVehicleDataMForCreateProposal(prmVehicleDataM);
			}
			
//			//update notePad
//			Vector notepadVect = prmApplicationDataM.getNotepadDataM();
//			OrigNotePadDataMDAO notePadDataMDAO = ORIGDAOFactory.getOrigNotePadDataMDAO();
//			if (notepadVect != null) {
//				for (int i = 0; i < notepadVect.size(); i++) {
//					NotePadDataM notePadDataM = (NotePadDataM) notepadVect.get(i);
//					//notePadDataM.setApplicationRecordId(prmApplicationRecordID);
//					notePadDataMDAO.saveUpdateModelOrigNotePadDataM(notePadDataM);
//				}
//			}
			
//			//update document list
//			Vector documentVect = prmApplicationDataM.getDocumentCheckListDataM();
//			OrigDocumentCheckListMDAO documentCheckListMDAO = ORIGDAOFactory.getOrigDocumentCheckListMDAO();
//			if (documentVect != null) {
//				DocumentCheckListDataM documentCheckListDataM;
//				for (int i = 0; i < documentVect.size(); i++) {
//					documentCheckListDataM = (DocumentCheckListDataM) documentVect.get(i);
//					//documentCheckListDataM.setApplicationRecordId(prmApplicationRecordID);
//					documentCheckListMDAO.saveUpdateModelOrigDocumentCheckListM(documentCheckListDataM);
//				}
//			}
			
//			//update other name
//			Vector otherNameVect = prmApplicationDataM.getOtherNameDataM();
//			OrigOtherNameMDAO otherNameMDAO = ORIGDAOFactory.getOrigOtherNameMDAO();
//			if (otherNameVect != null) {
//				OtherNameDataM otherNameDataM;
//				for (int i = 0; i < otherNameVect.size(); i++) {
//					otherNameDataM = (OtherNameDataM) otherNameVect.get(i);
//					//otherNameDataM.setApplicationRecordId(prmApplicationRecordID);
//					otherNameMDAO.saveUpdateModelOtherNameDataM(otherNameDataM);
//				}
//			}
			
//			//update reason
//			Vector reasonVect = prmApplicationDataM.getReasonVect();
//			OrigReasonMDAO reasonMDAO = ORIGDAOFactory.getOrigReasonMDAO();
//			OrigReasonLogMDAO reasonLogMDAO = ORIGDAOFactory.getOrigReasonLogMDAO();
//			if (reasonVect != null) {
//				ReasonDataM reasonDataM;
//				for (int i = 0; i < reasonVect.size(); i++) {
//					reasonDataM = (ReasonDataM) reasonVect.get(i);
//					//reasonDataM.setApplicationReordId(prmApplicationRecordID);
//					reasonMDAO.saveUpdateModelOrigReasonM(reasonDataM);
//					if(role.equals(reasonDataM.getRole())){
//						reasonLogMDAO.createModelOrigReasonLogM(reasonDataM);
//					}
//				}
//			}
			
//			//update proposal
//			ProposalDataM prmProposalDataM = prmApplicationDataM.getProposalDataM();
//			if(prmProposalDataM!=null){
//				//prmProposalDataM.setAppRecID(prmApplicationRecordID);
//				ORIGDAOFactory.getOrigProposalDAO().saveUpdateModelOrigProposalM(prmProposalDataM);
//			}
			
			
			return new Vector();
		} catch (Exception e) {
			log.fatal("Error >> ", e);
			throw new OrigApplicationMException(e.getMessage());
		}
	}
	
	/**
	 * @param prmApplicationDataM
	 * @return
	 */
	private int updateTableORIG_APPLICATION(
			ApplicationDataM prmApplicationDataM)
			throws OrigApplicationMException {
		int returnRows = 0;
		PreparedStatement ps = null;
		Connection conn = null;
		try {
			//conn = Get Connection
			conn = getConnection();
			StringBuffer sql = new StringBuffer("");
			sql.append("UPDATE ORIG_APPLICATION ");
			sql
					.append(" SET  IDENTIFICATION_NO=?,OFFICE_CODE=?,DEALER_CODE=?,SELLER_CODE=?,SALEMAN_CODE=?");
			sql
					.append(" ,UPDATE_BY=?,UPDATE_DATE=SYSDATE,CONSENT=?,CONSENT_DATE=?,CMR_START_DATE=?,CMR_END_DATE=?");
			sql
					.append(" ,CMR_FIRST_ID=?,CMR_LAST_ID=?,DE_START_DATE=?,DE_LAST_DATE=?,DE_FIRST_ID=?");
			sql
					.append(" ,DE_LAST_ID=?,CMR_EX_START_DATE=?,CMR_EX_END_DATE=?,CMR_EX_FIRST_ID=?,CMR_EX_LAST_ID=?");
			sql
					.append(" ,UW_START_DATE=?,UW_END_DATE=?,UW_FIRST_ID=?,UW_LAST_ID=?,PD_START_DATE=?");
			sql
					.append(" ,PD_END_DATE=?,PD_FIRST_ID=?,PD_LAST_ID=?,ESCALATE_BY=?,ESCALATE_DATE=?");
			//sql.append(" ,ESCALATE_TO=?,APPLICATION_STATUS=?,JOB_STATE=?,LOAN_TYPE=?,CMR_CODE=?");
			sql
			.append(" ,ESCALATE_TO=?,LOAN_TYPE=?,CMR_CODE=?");
			sql
					.append(" ,AREA_CODE=?, SRCOFCUS=?, UW_DECISION=?, PD_DECISION=?, XCMR_OVERRIDE_BY=?");
			sql
					.append(", XCMR_OVERRIDE_DATE=?, XCMR_DECISION=?, UW_DECISION_REASON=?,DRAW_DOWN_FLAG=?, DE_DECISION=? ");
			sql
					.append(", PRIORITY=?, PD_DECISION_REASON=?,SCORING_RESULT=? ");
			sql.append(" ,POLICY_VERSION=? ,XUW_POLICY_EXCEPTION=?,XUW_DECISION=?,XUW_DECISION_REASON=?,XUW_OVERRIDE_BY =? ");
			sql.append(" ,XUW_OVERRIDE_DATE=?,XUW_START_DATE=? ,XUW_END_DATE=?,XUW_FIRST_ID=?,XUW_LAST_ID=? , BUSINESS_CLASS_ID=?, FINAL_CREDIT_LIMIT=?, GROUP_APP_ID=? ");
			sql.append(" WHERE APPLICATION_RECORD_ID = ?");
			
			String dSql = String.valueOf(sql);
			
			log.debug("Sql=" + dSql);
			
			ps = conn.prepareStatement(dSql);
			
			ps.setString(1, prmApplicationDataM.getIdentificationNo());
			ps.setString(2, prmApplicationDataM.getOfficeCode());
			ps.setString(3, prmApplicationDataM.getDealerCode());
			ps.setString(4, prmApplicationDataM.getSellerCode());
			ps.setString(5, prmApplicationDataM.getSalesManCode());
			ps.setString(6, prmApplicationDataM.getUpdateBy());
			ps.setString(7, prmApplicationDataM.getConsent());
			ps.setDate(8, this.parseDate(prmApplicationDataM.getConsentDate()));
			ps.setDate(9, this.parseDate(prmApplicationDataM.getCmrStartDate()));
			ps.setDate(10, this.parseDate(prmApplicationDataM.getCmrEndDate()));//
			ps.setString(11, prmApplicationDataM.getCmrFirstId());
			ps.setString(12, prmApplicationDataM.getCmrLastId());
			ps.setDate(13, this.parseDate(prmApplicationDataM.getDeStartDate()));
			ps.setDate(14, this.parseDate(prmApplicationDataM.getDeLastDate()));
			ps.setString(15, prmApplicationDataM.getDeFirstId());//
			ps.setString(16, prmApplicationDataM.getDeLastId());
			ps.setDate(17, this.parseDate(prmApplicationDataM.getCmrExStartDate()));
			ps.setDate(18, this.parseDate(prmApplicationDataM.getCmrExEndDate()));
			ps.setString(19, prmApplicationDataM.getCmrExFirstId());
			ps.setString(20, prmApplicationDataM.getCmrExLastId());
			ps.setDate(21, this.parseDate(prmApplicationDataM.getUwStartDate()));
			ps.setDate(22, this.parseDate(prmApplicationDataM.getUwEndDate()));
			ps.setString(23, prmApplicationDataM.getUwFirstId());
			ps.setString(24, prmApplicationDataM.getUwLastId());
			ps.setDate(25, this.parseDate(prmApplicationDataM.getPdStartDate()));
			ps.setDate(26, this.parseDate(prmApplicationDataM.getPdEndDate()));
			ps.setString(27, prmApplicationDataM.getPdFirstId());
			ps.setString(28, prmApplicationDataM.getPdLastId());
			ps.setString(29, prmApplicationDataM.getEscalateBy());
			ps.setDate(30, this	.parseDate(prmApplicationDataM.getEscalateDate()));
			ps.setString(31, prmApplicationDataM.getEscalateTo());
			//ps.setString(32, prmApplicationDataM.getApplicationStatus());
			//ps.setString(32, prmApplicationDataM.getJobState());
			ps.setString(32, prmApplicationDataM.getLoanType());
			ps.setString(33, prmApplicationDataM.getCmrCode());
			ps.setString(34, prmApplicationDataM.getAreaCode());
			ps.setString(35, prmApplicationDataM.getSourceOfCustomer());
			ps.setString(36, prmApplicationDataM.getUwDecision());
			ps.setString(37, prmApplicationDataM.getPdDecision());
			ps.setString(38, prmApplicationDataM.getXcmrOverrideBy());
			ps.setDate(39, this.parseDate(prmApplicationDataM.getXcmrOverrideDate()));
			ps.setString(40, prmApplicationDataM.getXcmrDecision());
			ps.setString(41, prmApplicationDataM.getUwDecisionReason());
			ps.setString(42, prmApplicationDataM.getDrawDownFlag());
			ps.setString(43, prmApplicationDataM.getDeDecision());
			ps.setString(44, prmApplicationDataM.getPriority());
			ps.setString(45, prmApplicationDataM.getPdDecisionReason());
			ps.setString(46,prmApplicationDataM.getScorringResult());
			ps.setString(47,prmApplicationDataM.getPolicyVersion());
			ps.setString(48,prmApplicationDataM.getXuwPolicyException());
			ps.setString(49,prmApplicationDataM.getXuwDecision());
			ps.setString(50,prmApplicationDataM.getXuwDecisionReason());
			ps.setString(51,prmApplicationDataM.getXuwOverrideBy());
			ps.setTimestamp(52,prmApplicationDataM.getXuwOverrideDate());
			ps.setTimestamp(53,prmApplicationDataM.getXuwStartDate());
			ps.setTimestamp(54,prmApplicationDataM.getXuwEndDate());
			ps.setString(55,prmApplicationDataM.getXuwFirstId());
			ps.setString(56,prmApplicationDataM.getXuwLastId());
			ps.setString(57, prmApplicationDataM.getBusinessClassId());
			ps.setBigDecimal(58, prmApplicationDataM.getFinalCreditLimit());
			ps.setString(59, prmApplicationDataM.getGroupAppID());
			
			ps.setString(60, prmApplicationDataM.getAppRecordID());
			
			returnRows = ps.executeUpdate();
		} catch (Exception e) {
			log.fatal(e.getLocalizedMessage());
			throw new OrigApplicationMException(e.getMessage());
		} finally {
			try {
				closeConnection(conn, ps);
			} catch (Exception e) {
				log.fatal(e.getLocalizedMessage());
			}
		}
		return returnRows;
	}
	
	/** Delete not in key**/

	private Vector deleteNotInKeyApplicationData(ApplicationDataM prmApplicationDataM, String role) throws OrigApplicationMException {
		try{
			OrigApplicationCustomerMDAO origApplicationCustomerMDAO = ORIGDAOFactory.getOrigApplicationCustomerMDAO();
			OrigLoanMDAO loanMDAO = ORIGDAOFactory.getOrigLoanMDAO();
			OrigOtherNameMDAO otherNameMDAO = ORIGDAOFactory.getOrigOtherNameMDAO();
			OrigAttachmentHistoryMDAO attachmentHistoryMDAO = ORIGDAOFactory.getOrigAttachmentHistoryMDAO();
			OrigReasonMDAO reasonMDAO = ORIGDAOFactory.getOrigReasonMDAO();
			XRulesVerificationResultDAO resultDAO = ORIGDAOFactory.getXRulesVerificationResultDAO();
			//Delete Guarantor
			Vector personalVect = prmApplicationDataM.getPersonalInfoVect();
			Vector customerIdVect = new Vector();
			PersonalInfoDataM dataM;
			for(int i=0; i<personalVect.size(); i++){
				dataM = (PersonalInfoDataM) personalVect.get(i);
				customerIdVect.add(dataM.getIdNo());
				logger.debug("dataM.getIdNo() = "+dataM.getIdNo());
			}
			//delete xrules
			resultDAO.deleteModelXRulesVerificationResultM(prmApplicationDataM.getAppRecordID(), OrigConstant.ORIG_CMPCODE, customerIdVect);
			
			Vector guarantorIDVect = origApplicationCustomerMDAO.selectPersonalIDNotInModel(personalVect, prmApplicationDataM.getAppRecordID());
			if(personalVect != null && personalVect.size() > 0){
				//delete personal
				origApplicationCustomerMDAO.deleteNotInKeyTableORIG_APPLICATION_CUSTOMER(personalVect, prmApplicationDataM.getAppRecordID());
			}
			
			//Delete loan
			logger.debug("prmApplicationDataM.getDrawDownFlag() = "+prmApplicationDataM.getDrawDownFlag());
			if(!("Y").equals(prmApplicationDataM.getDrawDownFlag())){
				if(prmApplicationDataM.getLoanVect() == null || prmApplicationDataM.getLoanVect().size() == 0){
					loanMDAO.deleteTableORIG_LOAN(prmApplicationDataM.getAppRecordID());
				}else{
					loanMDAO.deleteNotInKeyTableORIG_LOAN(prmApplicationDataM.getLoanVect(), prmApplicationDataM.getAppRecordID());
				}
			}
			
			//Delete other name
			if(prmApplicationDataM.getOtherNameDataM() == null || prmApplicationDataM.getOtherNameDataM().size() == 0){
				otherNameMDAO.deleteTableORIG_OTHER_NAME(prmApplicationDataM.getAppRecordID());
			}else{
				otherNameMDAO.deleteNotInKeyTableORIG_OTHER_NAME(prmApplicationDataM.getOtherNameDataM(), prmApplicationDataM.getAppRecordID());
			}
			
			//Delete Attachment list
			if(prmApplicationDataM.getAttachmentVect() == null || prmApplicationDataM.getAttachmentVect().size() == 0){
				attachmentHistoryMDAO.deleteTableORIG_ATTACHMENT_HISTORY(prmApplicationDataM.getAppRecordID());
			}else{
				attachmentHistoryMDAO.deleteNotInKeyTableORIG_ATTACHMENT_HISTORY(prmApplicationDataM.getAttachmentVect(), prmApplicationDataM.getAppRecordID());
			}
			
			//Delete Reason
			logger.debug("reason size = "+prmApplicationDataM.getReasonVect().size());
			if(prmApplicationDataM.getReasonVect() == null || prmApplicationDataM.getReasonVect().size() == 0){
				reasonMDAO.deleteTableORIG_REASON(prmApplicationDataM.getAppRecordID());
			}else{
				reasonMDAO.deleteNotInKeyTableORIG_REASON(prmApplicationDataM.getReasonVect(), prmApplicationDataM.getAppRecordID(), role);
			}
			
			return guarantorIDVect;
		}catch (Exception e) {
			log.fatal(e.getLocalizedMessage());
			throw new OrigApplicationMException(e.getMessage());
		}
	}
	
	public String selectApplicationNo(String applicationRecordId) throws OrigApplicationMException {
		PreparedStatement ps = null;
		ResultSet rs = null;
		Connection conn = null;
		try {
			//conn = Get Connection
			conn = getConnection();
			StringBuffer sql = new StringBuffer("");
			sql.append("SELECT APPLICATION_NO");
			sql.append(" FROM ORIG_APPLICATION  WHERE APPLICATION_RECORD_ID = ? ");
			String dSql = String.valueOf(sql);
			log.debug("Sql=" + dSql);
			ps = conn.prepareStatement(dSql);
			rs = null;
			ps.setString(1, applicationRecordId);

			rs = ps.executeQuery();
			String appNo = null;
			if (rs.next()) {
				appNo = rs.getString(1);
			}

			return appNo;
		} catch (Exception e) {
			log.fatal(e.getLocalizedMessage());
			throw new OrigApplicationMException(e.getMessage());
		} finally {
			try {
				closeConnection(conn, rs, ps);
			} catch (Exception e) {
				log.fatal(e.getLocalizedMessage());
			}
		}
	}
	/**
	 * 
	 * @param appRecordId
	 * @param priority
	 * @param userName
	 * @return
	 * @throws OrigApplicationMException
	 */
	public int updateSetPriority(String appRecordId, String priority, String userName)throws OrigApplicationMException {
		int returnRows = 0;
		PreparedStatement ps = null;
		Connection conn = null;
		try {
			//conn = Get Connection
			conn = getConnection();
			StringBuffer sql = new StringBuffer("");
			sql.append("UPDATE ORIG_APPLICATION ");
			sql.append(" SET  PRIORITY=?, UPDATE_BY=?, UW_LAST_ID=?,UPDATE_DATE=?");
			sql.append(" WHERE APPLICATION_RECORD_ID = ?");
			String dSql = String.valueOf(sql);
			log.debug("Sql=" + dSql);
			ps = conn.prepareStatement(dSql);
			
			ps.setString(1, priority);
			ps.setString(2, userName);
			ps.setString(3, userName);
			ps.setTimestamp(4, new Timestamp(System.currentTimeMillis()));
			
			ps.setString(5, appRecordId);
			returnRows = ps.executeUpdate();
			
		} catch (Exception e) {
			log.fatal(e.getLocalizedMessage());
			throw new OrigApplicationMException(e.getMessage());
		} finally {
			try {
				closeConnection(conn, ps);
			} catch (Exception e) {
				log.fatal(e.getLocalizedMessage());
			}
		}
		return returnRows;
	}
	
	public int updateReassignApplication(String appRecordId,String userName)throws OrigApplicationMException {
		int returnRows = 0;
		PreparedStatement ps = null;
		Connection conn = null;
		try {
			//conn = Get Connection
			conn = getConnection();
			StringBuffer sql = new StringBuffer("");
			sql.append("UPDATE ORIG_APPLICATION ");
			sql.append(" SET UPDATE_BY=?, UW_LAST_ID=?, UPDATE_DATE=?, ESCALATE_BY=?, ESCALATE_DATE=?");
			sql.append(" WHERE APPLICATION_RECORD_ID = ?");
			String dSql = String.valueOf(sql);
			log.debug("Sql=" + dSql);
			ps = conn.prepareStatement(dSql);
			
			ps.setString(1, userName);
			ps.setString(2, userName);
			ps.setTimestamp(3, new Timestamp(System.currentTimeMillis()));
			ps.setString(4, null);
			ps.setString(5, null);
			
			ps.setString(6, appRecordId);
			returnRows = ps.executeUpdate();
			
		} catch (Exception e) {
			log.fatal(e.getLocalizedMessage());
			throw new OrigApplicationMException(e.getMessage());
		} finally {
			try {
				closeConnection(conn, ps);
			} catch (Exception e) {
				log.fatal(e.getLocalizedMessage());
			}
		}
		return returnRows;
	}
	
	public int updateApprecordForIMG(String appRecordId, String requestID)throws OrigApplicationMException {
		int returnRows = 0;
		PreparedStatement ps = null;
		Connection conn = null;
		try {
			//conn = Get Connection
			conn = getConnection();
			StringBuffer sql = new StringBuffer("");
			sql.append("UPDATE APPLICATION_IMG_REQUEST ");
			sql.append(" SET APPLICATION_RECORD_ID=?");
			sql.append(" WHERE IMG_REQUEST = ?");
			String dSql = String.valueOf(sql);
			log.debug("Sql=" + dSql);
			ps = conn.prepareStatement(dSql);
			
			ps.setString(1, appRecordId);
			ps.setString(2, requestID);
			
			returnRows = ps.executeUpdate();
			
		} catch (Exception e) {
			log.fatal(e.getLocalizedMessage());
			throw new OrigApplicationMException(e.getMessage());
		} finally {
			try {
				closeConnection(conn, ps);
			} catch (Exception e) {
				log.fatal(e.getLocalizedMessage());
			}
		}
		return returnRows;
	}
	
	public int updateStatusForIMG(String requestID)throws OrigApplicationMException {
		int returnRows = 0;
		PreparedStatement ps = null;
		Connection conn = null;
		try {
			//conn = Get Connection
			conn = getConnection();
			StringBuffer sql = new StringBuffer("");
			sql.append("UPDATE IMG_REQUEST ");
			sql.append(" SET REQUEST_STATUS=?");
			sql.append(" WHERE REQUEST_ID=?");
			String dSql = String.valueOf(sql);
			log.debug("Sql=" + dSql);
			ps = conn.prepareStatement(dSql);
			
			ps.setString(1, OrigConstant.IMG_STATUS_COMPLETE);
			ps.setString(2, requestID);
			
			returnRows = ps.executeUpdate();
			
		} catch (Exception e) {
			log.fatal(e.getLocalizedMessage());
			throw new OrigApplicationMException(e.getMessage());
		} finally {
			try {
				closeConnection(conn, ps);
			} catch (Exception e) {
				log.fatal(e.getLocalizedMessage());
			}
		}
		return returnRows;
	}
	
	public int updateStatusForReverse(ApplicationDataM applicationDataM)throws OrigApplicationMException {
		int returnRows = 0;
		PreparedStatement ps = null;
		Connection conn = null;
		try {
			//conn = Get Connection
			conn = getConnection();
			StringBuffer sql = new StringBuffer("");
			sql.append("UPDATE ORIG_APPLICATION ");
			sql.append(" SET PD_DECISION=?,PD_LAST_ID=?,UPDATE_BY=?,UPDATE_DATE=SYSDATE,APPLICATION_STATUS=?,JOB_STATE=?");
			sql.append(" WHERE APPLICATION_RECORD_ID = ?");
			String dSql = String.valueOf(sql);
			log.debug("Sql=" + dSql);
			ps = conn.prepareStatement(dSql);
			
			ps.setString(1,applicationDataM.getPdDecision());
			ps.setString(2, applicationDataM.getPdLastId());
			ps.setString(3, applicationDataM.getUpdateBy());
			ps.setString(4, applicationDataM.getApplicationStatus());
			ps.setString(5, applicationDataM.getJobState());
			ps.setString(6, applicationDataM.getAppRecordID());
			returnRows = ps.executeUpdate();			
		} catch (Exception e) {
			log.fatal(e.getLocalizedMessage());
			throw new OrigApplicationMException(e.getMessage());
		} finally {
			try {
				closeConnection(conn, ps);
			} catch (Exception e) {
				log.fatal(e.getLocalizedMessage());
			}
		}
		return returnRows;
	}
	private ApplicationDataM selectImageData(String applicationRecordId, ApplicationDataM applicationDataM) throws OrigApplicationMException {
		PreparedStatement ps = null;
		ResultSet rs = null;
		Connection conn = null;
		try {
			//conn = Get Connection
			conn = getConnection();
			StringBuffer sql = new StringBuffer("");
			sql.append("SELECT REQUEST_ID, ATTACH_ID");
			sql.append(" FROM VIEW_IMG_REQUEST WHERE APPLICATION_RECORD_ID = ? ");
			String dSql = String.valueOf(sql);
			log.debug("Sql=" + dSql);
			ps = conn.prepareStatement(dSql);
			rs = null;
			ps.setString(1, applicationRecordId);

			rs = ps.executeQuery();
			String appNo = null;
			if (rs.next()) {
				applicationDataM.setRequestID(rs.getString(1));
				applicationDataM.setAttachId(rs.getString(2));
			}

			return applicationDataM;
		} catch (Exception e) {
			log.fatal(e.getLocalizedMessage());
			throw new OrigApplicationMException(e.getMessage());
		} finally {
			try {
				closeConnection(conn, rs, ps);
			} catch (Exception e) {
				log.fatal(e.getLocalizedMessage());
			}
		}
	}	 
	public int updateStatus(ApplicationDataM applicationDataM)throws OrigApplicationMException {
		int returnRows = 0;
		PreparedStatement ps = null;
		Connection conn = null;
		try {
			//conn = Get Connection
			conn = getConnection();
			StringBuffer sql = new StringBuffer("");
			sql.append(" UPDATE ORIG_APPLICATION ");
			sql.append(" SET  APPLICATION_STATUS=?,JOB_STATE=?");
			sql.append(" WHERE APPLICATION_RECORD_ID = ?");
			String dSql = String.valueOf(sql);
			log.debug("Sql=" + dSql);
			ps = conn.prepareStatement(dSql);						 
			ps.setString(1, applicationDataM.getApplicationStatus());
			ps.setString(2, applicationDataM.getJobState());
			ps.setString(3, applicationDataM.getAppRecordID());
			returnRows = ps.executeUpdate();			
		} catch (Exception e) {
			log.fatal(e.getLocalizedMessage());
			throw new OrigApplicationMException(e.getMessage());
		} finally {
			try {
				closeConnection(conn, ps);
			} catch (Exception e) {
				log.fatal(e.getLocalizedMessage());
			}
		}
		return returnRows;
	}

	@Override
	public void SaveUpdateModelApplicationDataM(ApplicationDataM applicationDataM) throws OrigApplicationMException {
		
		int returnRows = 0;
		
		returnRows = updateTableORIG_APPLICATION(applicationDataM);
		
		if (returnRows == 0) {
			
			log.debug("New record then can't update record in table ORIG_APPLICATION then call Insert method");
			
			createTableORIG_APPLICATION(applicationDataM);
		}
		
	}
}
