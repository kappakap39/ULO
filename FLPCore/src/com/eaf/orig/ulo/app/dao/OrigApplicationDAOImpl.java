package com.eaf.orig.ulo.app.dao;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;

import org.apache.log4j.Logger;

import com.eaf.core.ulo.common.date.ApplicationDate;
import com.eaf.core.ulo.common.properties.SystemConstant;
import com.eaf.core.ulo.common.util.Util;
import com.eaf.orig.shared.constant.OrigConstant;
import com.eaf.orig.shared.dao.OrigObjectDAO;
import com.eaf.orig.ulo.app.dao.exception.ApplicationException;
import com.eaf.orig.ulo.app.util.GenerateUnique;
import com.eaf.orig.ulo.app.xrules.dao.OrigVerificationResultDAO;
import com.eaf.orig.ulo.constant.MConstant;
import com.eaf.orig.ulo.model.app.ApplicationDataM;
import com.eaf.orig.ulo.model.app.BundleHLDataM;
import com.eaf.orig.ulo.model.app.BundleKLDataM;
import com.eaf.orig.ulo.model.app.BundleSMEDataM;
import com.eaf.orig.ulo.model.app.LoanDataM;
import com.eaf.orig.ulo.model.app.ReasonDataM;
import com.eaf.orig.ulo.model.app.ReasonLogDataM;
import com.eaf.orig.ulo.model.app.VerificationResultDataM;

public class OrigApplicationDAOImpl extends OrigObjectDAO implements OrigApplicationDAO{
	private static transient Logger logger = Logger.getLogger(OrigApplicationDAOImpl.class);
	public OrigApplicationDAOImpl(){
		
	}
	public OrigApplicationDAOImpl(String userId){
		this.userId = userId;
	}
	private String userId = "";
	@Override
	public void createOrigApplicationM(ApplicationDataM application) throws ApplicationException {
		try {
			//Get Application Record ID
			logger.debug("application.getApplicationRecordId() :"+application.getApplicationRecordId());
		    if(Util.empty(application.getApplicationRecordId())){
				String prmApplicationRecordID = GenerateUnique.generate(OrigConstant.SeqNames.ORIG_APPLICATION_PK); 
				application.setCreateBy(userId);
				application.setApplicationRecordId(prmApplicationRecordID);
			}
		    application.setUpdateBy(userId);
			//Get Application Number
		    logger.debug("application.getApplicationNo() :"+application.getApplicationNo());
		    if(Util.empty(application.getApplicationNo())){
//		    	ORIGGeneratorManager generatorManager = ORIGEJBService.getGeneratorManager();
//				String applicationNo = generatorManager.generateApplicationNo(application);
//				application.setApplicationNo(applicationNo);
		    }
			createTableORIG_APPLICATION(application);
			
			
			BundleHLDataM bundleHL = application.getBundleHL();
			if(bundleHL != null) {
				OrigBundleHLDAO origBundleHLDAO = ORIGDAOFactory.getBundleHLDAO(userId);
				bundleHL.setApplicationRecordId(application.getApplicationRecordId());
				origBundleHLDAO.createOrigBundleHLM(bundleHL);
			}
			
			BundleKLDataM bundleKL = application.getBundleKL();
			if(bundleKL != null) {
				OrigBundleKLDAO origBundleKLDAO = ORIGDAOFactory.getBundleKLDAO(userId);
				bundleKL.setApplicationRecordId(application.getApplicationRecordId());
				origBundleKLDAO.createOrigBundleKLM(bundleKL);
			}
			
			BundleSMEDataM bundelSME = application.getBundleSME();
			if(bundelSME != null) {
				OrigBundleSMEDAO origBundleSMEDAO = ORIGDAOFactory.getBundleSMEDAO(userId);
				bundelSME.setApplicationRecordId(application.getApplicationRecordId());
				origBundleSMEDAO.createOrigBundleSMEM(bundelSME);
			}
			
			ArrayList<ReasonDataM> reasonList = application.getReasons();
			if(reasonList != null && !reasonList.isEmpty()) {
				OrigReasonDAO reasonDAO = ORIGDAOFactory.getReasonDAO(userId);
				for(ReasonDataM reasonM : reasonList){
					reasonM.setApplicationRecordId(application.getApplicationRecordId());
					reasonM.setApplicationGroupId(application.getApplicationGroupId());
					reasonDAO.createOrigReasonM(reasonM);
				}
			}
			
			ArrayList<ReasonLogDataM> reasonLogList = application.getReasonLogs();
			if(reasonLogList != null && !reasonLogList.isEmpty()) {
				OrigReasonLogDAO reasonLogDAO = ORIGDAOFactory.getReasonLogDAO(userId);
				for(ReasonLogDataM reasonLogM : reasonLogList){
					reasonLogM.setApplicationRecordId(application.getApplicationRecordId());
					reasonLogM.setApplicationGroupId(application.getApplicationGroupId());
					reasonLogDAO.createOrigReasonLogM(reasonLogM);
				}
			}
			
			ArrayList<LoanDataM> loanList = application.getLoans();
			if(loanList != null && !loanList.isEmpty()) {
				OrigLoanDAO loanDAO = ORIGDAOFactory.getLoanDAO(userId);
				for(LoanDataM loanM : loanList){
					loanM.setApplicationRecordId(application.getApplicationRecordId());
					loanDAO.createOrigLoanM(loanM);
				}
			}
			
		
			VerificationResultDataM verificationM = application.getVerificationResult();					 
			if(!Util.empty(verificationM)) {
				OrigVerificationResultDAO verificationDAO = ORIGDAOFactory.getVerificationResultDAO(userId);
				verificationM.setRefId(application.getApplicationRecordId());
				verificationM.setVerLevel(MConstant.REF_LEVEL.APPLICATION);
				verificationDAO.createOrigVerificationResultM(verificationM);
			}
			
		} catch (Exception e) {
			logger.fatal(e.getLocalizedMessage());
			throw new ApplicationException(e.getMessage());
		}
	}

	private void createTableORIG_APPLICATION(ApplicationDataM application) throws ApplicationException {
		PreparedStatement ps = null;
		Connection conn = null;
		try {
			//conn = Get Connection
			conn = getConnection();
			StringBuilder sql = new StringBuilder("");
		
			sql.append("INSERT INTO ORIG_APPLICATION ");
			sql.append("( APPLICATION_RECORD_ID, APPLICATION_NO, APPLICATION_GROUP_ID, ");
			sql.append(" REF_APPLICATION_RECORD_ID, BUSINESS_CLASS_ID, PROJECT_CODE,");
			sql.append(" SP_SIGN_OFF_FLAG, APPLICATION_TYPE, LIFE_CYCLE, ");
			sql.append(" CARDLINK_REF_NO, TCB_ACCOUNT_NO, RECOMMEND_DECISION, ");
			sql.append(" SP_SIGNOFF_DATE, SP_SIGNOFF_AUTH_BY, MAINCARD_RECORD_ID, ");
			sql.append(" DIFF_REQUEST_RESULT, PROLICY_PROGRAM_ID, EXISTING_PRIORITY_PASS, ");
			sql.append(" POLICY_SEGMENT_ID, FINAL_APP_DECISION, IS_VETO_ELIGIBLE,REFER_CRITERIA, ");
			sql.append(" FINAL_APP_DECISION_BY,FINAL_APP_DECISION_DATE, ");
			sql.append(" CREATE_BY, CREATE_DATE, UPDATE_BY, UPDATE_DATE, CARDLINK_SEQ, CARDLINK_FLAG, VLINK_FLAG, ");
			sql.append(" RE_EXECUTE_KEY_PROGRAM_FLAG, APPLICATION_DATE , BLOCK_FLAG, REOPEN_FLAG, LETTER_CHANNEL, "); //KPL Additional
			sql.append(" E_RECOMMEND_DECISION,LOW_INCOME_FLAG,E_APPLICATION_RECORD_ID,APPLY_TYPE_FLAG ) ");
			
			sql.append(" VALUES(?,?,?,  ?,?,?,  ?,?,?,  ?,?,?,  ?,?,?,  ?,?,?,  ?,?,?,?, ");
			sql.append(" ?,?,");
			//Add extra ? ? ? to this line for blockFlag, reOpenFlag letterChannel field
			sql.append(" ?,?,?,? ,?,?,? ,?,? , ?,?,?,");
			sql.append(" ?,?,?,? )");
			String dSql = sql.toString();
			logger.debug("Sql=" + dSql);
			ps = conn.prepareStatement(dSql);
			int cnt = 1;
			ps.setString(cnt++, application.getApplicationRecordId());
			ps.setString(cnt++, application.getApplicationNo());
			ps.setString(cnt++, application.getApplicationGroupId());
			
			ps.setString(cnt++, application.getRefApplicationRecordId());
			ps.setString(cnt++, application.getBusinessClassId());
			ps.setString(cnt++, application.getProjectCode());
			
			ps.setString(cnt++, application.getSpSignOffFlag());
			ps.setString(cnt++, application.getApplicationType());
			ps.setInt(cnt++, application.getLifeCycle());

			ps.setString(cnt++, application.getCardlinkRefNo());
			ps.setString(cnt++, application.getTcbAccountNo());
			ps.setString(cnt++, application.getRecommendDecision());
			
			ps.setDate(cnt++, application.getSpSignoffDate());
			ps.setString(cnt++, application.getSpSignoffAuthBy());
			ps.setString(cnt++, application.getMaincardRecordId());
			
			ps.setString(cnt++, application.getDiffRequestResult());
			ps.setString(cnt++, application.getPolicyProgramId());
			ps.setString(cnt++, application.getExistingPriorityPass());
			
			ps.setString(cnt++, application.getPolicySegmentId());
			ps.setString(cnt++, application.getFinalAppDecision());
			ps.setString(cnt++, application.getIsVetoEligibleFlag());
			ps.setString(cnt++, application.getReferCriteria());
			
			ps.setString(cnt++, application.getFinalAppDecisionBy());
			ps.setDate(cnt++, application.getFinalAppDecisionDate());
			
			ps.setString(cnt++, application.getCreateBy());
			ps.setTimestamp(cnt++, ApplicationDate.getTimestamp());
			ps.setString(cnt++, application.getUpdateBy());
			ps.setTimestamp(cnt++, ApplicationDate.getTimestamp());
			
			ps.setInt(cnt++, application.getCardlinkSeq());
			ps.setString(cnt++, application.getCardlinkFlag());
			ps.setString(cnt++, application.getVlinkFlag());
			ps.setString(cnt++, application.getReExecuteKeyProgramFlag());
			ps.setDate(cnt++, application.getApplicationDate());
			
			//KPL Additional
			ps.setString(cnt++, application.getSavingPlusFlag());
			ps.setString(cnt++, application.getDiffRequestFlag());
			ps.setString(cnt++, application.getLetterChannel());
			
			ps.setString(cnt++, application.geteRecommendDecision());
			ps.setString(cnt++, application.getLowIncomeFlag());
			ps.setString(cnt++, application.geteApplicationRecordId());
			ps.setString(cnt++, application.getApplyTypeFlag());
			
			ps.executeUpdate();
		} catch (Exception e) {
			logger.fatal(e.getLocalizedMessage());
			throw new ApplicationException(e.getMessage());
		} finally {
			try {
				closeConnection(conn, ps);
			} catch (Exception e) {
				logger.fatal(e.getLocalizedMessage());
			}
		}
	}

	@Override
	public void deleteOrigApplicationM(ApplicationDataM application) throws ApplicationException {
		try {
			OrigBundleHLDAO origBundleHLDAO = ORIGDAOFactory.getBundleHLDAO();
			OrigBundleKLDAO origBundleKLDAO = ORIGDAOFactory.getBundleKLDAO();
			OrigBundleSMEDAO origBundleSMEDAO = ORIGDAOFactory.getBundleSMEDAO();
			OrigReasonDAO reasonDAO = ORIGDAOFactory.getReasonDAO();			
			OrigReasonLogDAO reasonLogDAO = ORIGDAOFactory.getReasonLogDAO();
			OrigLoanDAO loanDAO = ORIGDAOFactory.getLoanDAO();
			
			origBundleHLDAO.deleteOrigBundleHLM(application.getApplicationRecordId());
			origBundleKLDAO.deleteOrigBundleKLM(application.getApplicationRecordId());
			origBundleSMEDAO.deleteOrigBundleSMEM(application.getApplicationRecordId());
			reasonDAO.deleteOrigReasonM(application.getApplicationRecordId());
			reasonLogDAO.deleteOrigReasonLogM(application.getApplicationRecordId(), null);
			loanDAO.deleteOrigLoanM(application.getApplicationRecordId(), null);
			
			deleteTableORIG_APPLICATION(application);
		} catch (Exception e) {
			logger.fatal(e.getLocalizedMessage());
			throw new ApplicationException(e.getMessage());
		}
	}

	private void deleteTableORIG_APPLICATION(ApplicationDataM application) throws ApplicationException {
		PreparedStatement ps = null;
		Connection conn = null;
		try {
			//conn = Get Connection
			conn = getConnection();
			StringBuilder sql = new StringBuilder("");
			sql.append("DELETE ORIG_APPLICATION ");
			sql.append(" WHERE APPLICATION_RECORD_ID = ?");
			String dSql = sql.toString();
			logger.debug("Sql=" + dSql);
			ps = conn.prepareStatement(dSql);
			ps.setString(1, application.getApplicationRecordId());
			ps.executeUpdate();

		} catch (Exception e) {
			logger.fatal(e.getLocalizedMessage());
			throw new ApplicationException(e.getMessage());
		} finally {
			try {
				closeConnection(conn, ps);
			} catch (Exception e) {
				logger.fatal(e.getLocalizedMessage());
				throw new ApplicationException(e.getMessage());
			}
		}
	}

	@Override
	public ArrayList<ApplicationDataM> loadOrigApplicationM(String applicationGroupId) throws ApplicationException {
		try{
			ArrayList<ApplicationDataM> results =  selectTableORIG_APPLICATION(applicationGroupId);
			if(!Util.empty(results)){
				for(ApplicationDataM result:results){
					String applicationRecordId = result.getApplicationRecordId();
					OrigBundleHLDAO origBundleHLDAO = ORIGDAOFactory.getBundleHLDAO();
					BundleHLDataM bundleHL = origBundleHLDAO.loadOrigBundleHLM(applicationRecordId);
					if (bundleHL != null) {
						result.setBundleHL(bundleHL);
					}
					
					OrigBundleKLDAO origBundleKLDAO = ORIGDAOFactory.getBundleKLDAO();
					BundleKLDataM bundleKL = origBundleKLDAO.loadOrigBundleKLM(applicationRecordId);
					if (bundleKL != null) {
						result.setBundleKL(bundleKL);
					}
					
					OrigBundleSMEDAO origBundleSMEDAO = ORIGDAOFactory.getBundleSMEDAO();
					BundleSMEDataM bundleSME = origBundleSMEDAO.loadOrigBundleSMEM(applicationRecordId);
					if (bundleSME != null) {
						result.setBundleSME(bundleSME);
					}
					
					OrigReasonDAO reasonDAO = ORIGDAOFactory.getReasonDAO();
					ArrayList<ReasonDataM> reasonList = reasonDAO.loadOrigReasonM(applicationRecordId);
					if(reasonList != null && !reasonList.isEmpty()) {
						result.setReasons(reasonList);
					}
					
//					OrigReasonLogDAO reasonLogDAO = ORIGDAOFactory.getReasonLogDAO();
//					ArrayList<ReasonLogDataM> reasonLogList = reasonLogDAO.loadOrigReasonLogM(applicationRecordId);
//					if(reasonLogList != null && !reasonLogList.isEmpty()) {
//						result.setReasonLogs(reasonLogList);
//					}
					
					OrigLoanDAO loanDAO = ORIGDAOFactory.getLoanDAO();
					ArrayList<LoanDataM> loanList = loanDAO.loadOrigLoanM(applicationRecordId);
					if(loanList != null && !loanList.isEmpty()) {
						result.setLoans(loanList);
					}
					
					OrigVerificationResultDAO verificationDAO = ORIGDAOFactory.getVerificationResultDAO();
					VerificationResultDataM verificationM = verificationDAO.loadOrigVerificationResultM(applicationRecordId, MConstant.REF_LEVEL.APPLICATION);
					if(!Util.empty(verificationM)) {
						result.setVerificationResult(verificationM);
					}
				}
			}
			return results;
		}catch(Exception e){
			logger.fatal(e.getLocalizedMessage());
			throw new ApplicationException(e.getMessage());
		}
	}
	
	private ArrayList<ApplicationDataM> loadOrigApplicationM(String applicationGroupId,Connection conn) throws ApplicationException {
		try{
			ArrayList<ApplicationDataM> results =  selectTableORIG_APPLICATION(applicationGroupId,conn);
			if(!Util.empty(results)){
				for(ApplicationDataM result:results){
					String applicationRecordId = result.getApplicationRecordId();
					OrigBundleHLDAO origBundleHLDAO = ORIGDAOFactory.getBundleHLDAO();
					BundleHLDataM bundleHL = origBundleHLDAO.loadOrigBundleHLM(applicationRecordId,conn);
					if (bundleHL != null) {
						result.setBundleHL(bundleHL);
					}
					
					OrigBundleKLDAO origBundleKLDAO = ORIGDAOFactory.getBundleKLDAO();
					BundleKLDataM bundleKL = origBundleKLDAO.loadOrigBundleKLM(applicationRecordId,conn);
					if (bundleKL != null) {
						result.setBundleKL(bundleKL);
					}
					
					OrigBundleSMEDAO origBundleSMEDAO = ORIGDAOFactory.getBundleSMEDAO();
					BundleSMEDataM bundleSME = origBundleSMEDAO.loadOrigBundleSMEM(applicationRecordId,conn);
					if (bundleSME != null) {
						result.setBundleSME(bundleSME);
					}
					
					OrigReasonDAO reasonDAO = ORIGDAOFactory.getReasonDAO();
					ArrayList<ReasonDataM> reasonList = reasonDAO.loadOrigReasonM(applicationRecordId,conn);
					if(reasonList != null && !reasonList.isEmpty()) {
						result.setReasons(reasonList);
					}
					
//					OrigReasonLogDAO reasonLogDAO = ORIGDAOFactory.getReasonLogDAO();
//					ArrayList<ReasonLogDataM> reasonLogList = reasonLogDAO.loadOrigReasonLogM(applicationRecordId);
//					if(reasonLogList != null && !reasonLogList.isEmpty()) {
//						result.setReasonLogs(reasonLogList);
//					}
					
					OrigLoanDAO loanDAO = ORIGDAOFactory.getLoanDAO();
					ArrayList<LoanDataM> loanList = loanDAO.loadOrigLoanM(applicationRecordId,conn);
					if(loanList != null && !loanList.isEmpty()) {
						result.setLoans(loanList);
					}
					
					OrigVerificationResultDAO verificationDAO = ORIGDAOFactory.getVerificationResultDAO();
					VerificationResultDataM verificationM = verificationDAO.loadOrigVerificationResultM(applicationRecordId, MConstant.REF_LEVEL.APPLICATION,conn);
					if(!Util.empty(verificationM)) {
						result.setVerificationResult(verificationM);
					}
				}
			}
			return results;
		}catch(Exception e){
			logger.fatal(e.getLocalizedMessage());
			throw new ApplicationException(e.getMessage());
		}
	}
	
	private ArrayList<ApplicationDataM> selectTableORIG_APPLICATION(String applicationGroupId) throws ApplicationException {
		Connection conn = null;
		try{
			conn = getConnection();
			return selectTableORIG_APPLICATION(applicationGroupId, conn);
		}catch(Exception e){
			logger.fatal(e.getLocalizedMessage());
			throw new ApplicationException(e.getMessage());
		}finally{
			try{
				closeConnection(conn);
			}catch(Exception e){
				logger.fatal(e.getLocalizedMessage());
			}
		}
	}
	
	private ArrayList<ApplicationDataM> selectTableORIG_APPLICATION(String applicationGroupId,Connection conn) throws ApplicationException {
		PreparedStatement ps = null;
		ResultSet rs = null;
		ArrayList<ApplicationDataM> applications = new ArrayList<ApplicationDataM>();
		try {
			//conn = Get Connection
//			conn = getConnection();
			StringBuilder sql = new StringBuilder("");
			sql.append("SELECT APPLICATION_RECORD_ID, APPLICATION_NO, APPLICATION_GROUP_ID, ");
			sql.append(" REF_APPLICATION_RECORD_ID, BUSINESS_CLASS_ID, PROJECT_CODE, SP_SIGN_OFF_FLAG, ");
			sql.append(" APPLICATION_TYPE, LIFE_CYCLE, CARDLINK_REF_NO, TCB_ACCOUNT_NO, ");
			sql.append(" RECOMMEND_DECISION, SP_SIGNOFF_DATE, SP_SIGNOFF_AUTH_BY, ");
			sql.append(" MAINCARD_RECORD_ID, DIFF_REQUEST_RESULT, PROLICY_PROGRAM_ID, ");
			sql.append(" EXISTING_PRIORITY_PASS, ");
			sql.append(" CREATE_BY, CREATE_DATE, UPDATE_BY, UPDATE_DATE,");
			sql.append(" FINAL_APP_DECISION , FINAL_APP_DECISION_BY, FINAL_APP_DECISION_DATE, ");
			sql.append(" POLICY_SEGMENT_ID, IS_VETO_ELIGIBLE, REFER_CRITERIA, CARDLINK_SEQ, CARDLINK_FLAG, VLINK_FLAG ,RE_EXECUTE_KEY_PROGRAM_FLAG ,APPLICATION_DATE, ");
			sql.append(" PREVIOUS_RECOMMEND_DECISION ");
			sql.append(" , BLOCK_FLAG , REOPEN_FLAG, LETTER_CHANNEL "); //KPL Additional
			sql.append(" , E_RECOMMEND_DECISION,LOW_INCOME_FLAG,E_APPLICATION_RECORD_ID,APPLY_TYPE_FLAG ");
			sql.append(" FROM ORIG_APPLICATION  WHERE APPLICATION_GROUP_ID = ? ");
			sql.append(" ORDER BY LIFE_CYCLE ASC,APPLICATION_NO ASC ");
			String dSql = sql.toString();
			logger.debug("Sql=" + dSql);
			ps = conn.prepareStatement(dSql);
			rs = null;
			ps.setString(1, applicationGroupId);

			rs = ps.executeQuery();
			//ApplicationDataM applicationDataM = null;

			while(rs.next()){
				ApplicationDataM application = new ApplicationDataM();
				application.setApplicationRecordId(rs.getString("APPLICATION_RECORD_ID"));
				application.setApplicationNo(rs.getString("APPLICATION_NO"));
				application.setApplicationGroupId(rs.getString("APPLICATION_GROUP_ID"));
				
				application.setRefApplicationRecordId(rs.getString("REF_APPLICATION_RECORD_ID"));
				application.setBusinessClassId(rs.getString("BUSINESS_CLASS_ID"));
				application.setProjectCode(rs.getString("PROJECT_CODE"));
				application.setSpSignOffFlag(rs.getString("SP_SIGN_OFF_FLAG"));
				
				application.setApplicationType(rs.getString("APPLICATION_TYPE"));
				application.setLifeCycle(rs.getInt("LIFE_CYCLE"));
				application.setCardlinkRefNo(rs.getString("CARDLINK_REF_NO"));
				application.setTcbAccountNo(rs.getString("TCB_ACCOUNT_NO"));
				
				application.setRecommendDecision(rs.getString("RECOMMEND_DECISION"));
				application.setSpSignoffDate(rs.getDate("SP_SIGNOFF_DATE"));
				application.setSpSignoffAuthBy(rs.getString("SP_SIGNOFF_AUTH_BY"));
				
				application.setMaincardRecordId(rs.getString("MAINCARD_RECORD_ID"));
				application.setDiffRequestResult(rs.getString("DIFF_REQUEST_RESULT"));
				application.setPolicyProgramId(rs.getString("PROLICY_PROGRAM_ID"));
				
				application.setExistingPriorityPass(rs.getString("EXISTING_PRIORITY_PASS"));

				application.setCreateBy(rs.getString("CREATE_BY"));
				application.setCreateDate(rs.getTimestamp("CREATE_DATE"));
				application.setUpdateBy(rs.getString("UPDATE_BY"));
				application.setUpdateDate(rs.getTimestamp("UPDATE_DATE"));
				
				application.setFinalAppDecision(rs.getString("FINAL_APP_DECISION"));
				application.setFinalAppDecisionBy(rs.getString("FINAL_APP_DECISION_BY"));
				application.setFinalAppDecisionDate(rs.getDate("FINAL_APP_DECISION_DATE"));
				application.setPolicySegmentId(rs.getString("POLICY_SEGMENT_ID"));
				application.setIsVetoEligibleFlag(rs.getString("IS_VETO_ELIGIBLE"));
				application.setReferCriteria(rs.getString("REFER_CRITERIA"));
				
				application.setCardlinkSeq(rs.getInt("CARDLINK_SEQ"));
				application.setCardlinkFlag(rs.getString("CARDLINK_FLAG"));
				application.setVlinkFlag(rs.getString("VLINK_FLAG"));
				application.setReExecuteKeyProgramFlag(rs.getString("RE_EXECUTE_KEY_PROGRAM_FLAG"));
				application.setApplicationDate(rs.getDate("APPLICATION_DATE"));
				application.setPreviousRecommendDecision(rs.getString("PREVIOUS_RECOMMEND_DECISION"));
				
				application.setSavingPlusFlag(rs.getString("BLOCK_FLAG"));
				application.setDiffRequestFlag(rs.getString("REOPEN_FLAG"));
				application.setLetterChannel(rs.getString("LETTER_CHANNEL"));
				
				application.seteRecommendDecision(rs.getString("E_RECOMMEND_DECISION"));
				application.setLowIncomeFlag(rs.getString("LOW_INCOME_FLAG"));
				application.seteApplicationRecordId(rs.getString("E_APPLICATION_RECORD_ID"));
				application.setApplyTypeFlag(rs.getString("APPLY_TYPE_FLAG"));
				
				applications.add(application);
			}
			return applications;
		}catch(Exception e){
			logger.fatal(e.getLocalizedMessage());
			throw new ApplicationException(e.getMessage());
		}finally{
			try{
				closeConnection(rs, ps);
			}catch(Exception e){
				logger.fatal(e.getLocalizedMessage());
			}
		}
	}

	@Override
	public void saveUpdateSigleOrigApplicationM(ApplicationDataM application) throws ApplicationException {
		application.setUpdateBy(userId);
		updateTableORIG_APPLICATION(application);
	}
	@Override
	public void saveUpdateSigleOrigApplicationM(ApplicationDataM application,Connection conn) throws ApplicationException {
		application.setUpdateBy(userId);
		updateTableORIG_APPLICATION(application,conn);
	}
	@Override
	public void saveUpdateOrigApplicationM(ApplicationDataM application) throws ApplicationException {
		Connection conn = null;
		try{
			conn = getConnection();
			saveUpdateOrigApplicationM(application, conn);
		}catch(Exception e){
			logger.fatal(e);
			throw new ApplicationException(e.getMessage());
		}finally{
			try{
				closeConnection(conn);
			}catch(Exception e){
				logger.fatal(e.getLocalizedMessage());
			}
		}
	}
	@Override
	public void saveUpdateOrigApplicationM(ApplicationDataM application,Connection conn) throws ApplicationException {
		int returnRows = 0;
		application.setUpdateBy(userId);
		returnRows = updateTableORIG_APPLICATION(application);
		if (returnRows == 0) {
			application.setCreateBy(userId);
			createOrigApplicationM(application);
		} else {
		
			BundleHLDataM bundleHL = application.getBundleHL();
			OrigBundleHLDAO origBundleHLDAO = ORIGDAOFactory.getBundleHLDAO(userId);
			if(bundleHL != null) {	
				bundleHL.setApplicationRecordId(application.getApplicationRecordId());
				origBundleHLDAO.saveUpdateOrigBundleHLM(bundleHL);
			}else{
				origBundleHLDAO.deleteOrigBundleHLM(application.getApplicationRecordId());
			}
			
			BundleKLDataM bundleKL = application.getBundleKL();
			OrigBundleKLDAO origBundleKLDAO = ORIGDAOFactory.getBundleKLDAO(userId);
			if(bundleKL != null) {
				bundleKL.setApplicationRecordId(application.getApplicationRecordId());
				origBundleKLDAO.saveUpdateOrigBundleKLM(bundleKL);
			}else{
				origBundleKLDAO.deleteOrigBundleKLM(application.getApplicationRecordId());
			}
			
			BundleSMEDataM bundelSME = application.getBundleSME();
			OrigBundleSMEDAO origBundleSMEDAO = ORIGDAOFactory.getBundleSMEDAO(userId);
			if(bundelSME != null) {
				bundelSME.setApplicationRecordId(application.getApplicationRecordId());
				origBundleSMEDAO.saveUpdateOrigBundleSMEM(bundelSME);
			}else{
				origBundleSMEDAO.deleteOrigBundleSMEM(application.getApplicationRecordId());
			}
			
			ArrayList<ReasonDataM> reasonList = application.getReasons();
			OrigReasonDAO reasonDAO = ORIGDAOFactory.getReasonDAO(userId);
			if(reasonList != null && !reasonList.isEmpty()) {
				for(ReasonDataM reasonM :reasonList){
					reasonM.setApplicationRecordId(application.getApplicationRecordId());
					reasonM.setApplicationGroupId(application.getApplicationGroupId());
					reasonDAO.saveUpdateOrigReasonM(reasonM);
				}				
			}else{
				//DF#FLP-02664,FLP-03115
				reasonDAO.deleteOrigReasonM(application.getApplicationRecordId());
			}
			ArrayList<ReasonLogDataM> reasonLogList = application.getReasonLogs();
			OrigReasonLogDAO reasonLogDAO = ORIGDAOFactory.getReasonLogDAO(userId);
			if(reasonLogList != null && !reasonLogList.isEmpty()) {
				for(ReasonLogDataM reasonLogM :reasonLogList){
					reasonLogM.setApplicationRecordId(application.getApplicationRecordId());
					reasonLogM.setApplicationGroupId(application.getApplicationGroupId());
					reasonLogDAO.saveUpdateOrigReasonLogM(reasonLogM);
				}				
			}
			
			ArrayList<LoanDataM> loanList = application.getLoans();
			OrigLoanDAO loanDAO = ORIGDAOFactory.getLoanDAO(userId);
			if(loanList != null && !loanList.isEmpty()) {
				for(LoanDataM loanM : loanList){
					loanM.setApplicationRecordId(application.getApplicationRecordId());
					loanDAO.saveUpdateOrigLoanM(loanM);
				}
			}
			loanDAO.deleteNotInKeyLoan(loanList, application.getApplicationRecordId());
			
			
			VerificationResultDataM verificationM = application.getVerificationResult();					 
			OrigVerificationResultDAO verificationDAO = ORIGDAOFactory.getVerificationResultDAO(userId);
			if(!Util.empty(verificationM)) {
				verificationM.setRefId(application.getApplicationRecordId());
				verificationM.setVerLevel(MConstant.REF_LEVEL.APPLICATION);
				verificationDAO.saveUpdateOrigVerificationResultM(verificationM,conn);
			}
		}
	}

	private int updateTableORIG_APPLICATION(ApplicationDataM application) throws ApplicationException {
		int returnRows = 0;
		PreparedStatement ps = null;
		Connection conn = null;
		try {
			//conn = Get Connection
			conn = getConnection();
			StringBuilder sql = new StringBuilder("");
			sql.append("UPDATE ORIG_APPLICATION ");
			sql.append(" SET REF_APPLICATION_RECORD_ID = ?, BUSINESS_CLASS_ID = ?, PROJECT_CODE = ? ");
			sql.append(" ,SP_SIGN_OFF_FLAG = ?, APPLICATION_TYPE = ?,LIFE_CYCLE = ?, CARDLINK_REF_NO = ? ");
			sql.append(" ,TCB_ACCOUNT_NO = ?, RECOMMEND_DECISION = ?, SP_SIGNOFF_DATE = ? ");
			sql.append(" ,SP_SIGNOFF_AUTH_BY = ?, MAINCARD_RECORD_ID = ?, DIFF_REQUEST_RESULT = ? ");
			sql.append(" ,PROLICY_PROGRAM_ID = ?, EXISTING_PRIORITY_PASS = ? ");
			sql.append(" ,FINAL_APP_DECISION =? , FINAL_APP_DECISION_BY =?, FINAL_APP_DECISION_DATE = ? ");
			sql.append(" ,POLICY_SEGMENT_ID= ?, IS_VETO_ELIGIBLE = ?, REFER_CRITERIA = ?, UPDATE_BY = ?, UPDATE_DATE = ?");
			sql.append(" , CARDLINK_SEQ = ?, CARDLINK_FLAG = ?, VLINK_FLAG = ? ,RE_EXECUTE_KEY_PROGRAM_FLAG=? ,APPLICATION_DATE = ? ");
			sql.append(" , APPLICATION_NO = ? ");
			sql.append(" , BLOCK_FLAG = ? , REOPEN_FLAG = ? , LETTER_CHANNEL = ? "); //KPL Additional
			sql.append(" , E_RECOMMEND_DECISION = ? , LOW_INCOME_FLAG = ?, E_APPLICATION_RECORD_ID = ?, APPLY_TYPE_FLAG = ? ");
			sql.append(" WHERE APPLICATION_RECORD_ID = ? ");
			String dSql = sql.toString();
			logger.debug("Sql=" + dSql);
			ps = conn.prepareStatement(dSql);
			int cnt = 1;
			ps.setString(cnt++, application.getRefApplicationRecordId());
			ps.setString(cnt++, application.getBusinessClassId());
			ps.setString(cnt++, application.getProjectCode());
			
			ps.setString(cnt++, application.getSpSignOffFlag());
			ps.setString(cnt++, application.getApplicationType());
			ps.setInt(cnt++, application.getLifeCycle());
			ps.setString(cnt++, application.getCardlinkRefNo());
			
			ps.setString(cnt++, application.getTcbAccountNo());
			ps.setString(cnt++, application.getRecommendDecision());
			ps.setDate(cnt++, application.getSpSignoffDate());
			
			ps.setString(cnt++, application.getSpSignoffAuthBy());
			ps.setString(cnt++, application.getMaincardRecordId());
			ps.setString(cnt++, application.getDiffRequestResult());
			
			ps.setString(cnt++, application.getPolicyProgramId());
			ps.setString(cnt++, application.getExistingPriorityPass());
			
			ps.setString(cnt++, application.getFinalAppDecision());
			ps.setString(cnt++, application.getFinalAppDecisionBy());
			ps.setDate(cnt++, application.getFinalAppDecisionDate());
			
			ps.setString(cnt++, application.getPolicySegmentId());
			ps.setString(cnt++, application.getIsVetoEligibleFlag());
			ps.setString(cnt++, application.getReferCriteria());
			ps.setString(cnt++, application.getUpdateBy());
			ps.setTimestamp(cnt++, ApplicationDate.getTimestamp());
			
			ps.setInt(cnt++, application.getCardlinkSeq());
			ps.setString(cnt++, application.getCardlinkFlag());
			ps.setString(cnt++, application.getVlinkFlag());
			ps.setString(cnt++, application.getReExecuteKeyProgramFlag());
			ps.setDate(cnt++, application.getApplicationDate());
			
			ps.setString(cnt++, application.getApplicationNo());
			
			ps.setString(cnt++, application.getSavingPlusFlag()); //KPL Additional
			ps.setString(cnt++, application.getDiffRequestFlag()); //KPL Additional
			ps.setString(cnt++, application.getLetterChannel()); //KPL Additional
			
			ps.setString(cnt++, application.geteRecommendDecision());
			ps.setString(cnt++, application.getLowIncomeFlag());
			ps.setString(cnt++, application.geteApplicationRecordId());
			ps.setString(cnt++, application.getApplyTypeFlag());
			// WHERE CLAUSE
			ps.setString(cnt++, application.getApplicationRecordId());
			
			
			returnRows = ps.executeUpdate();
		} catch (Exception e) {
			logger.fatal(e.getLocalizedMessage());
			throw new ApplicationException(e.getMessage());
		} finally {
			try {
				closeConnection(conn, ps);
			} catch (Exception e) {
				logger.fatal(e.getLocalizedMessage());
			}
		}
		return returnRows;
	}
	
	private int updateTableORIG_APPLICATION(ApplicationDataM application,Connection conn) throws ApplicationException {
		int returnRows = 0;
		PreparedStatement ps = null;
//		Connection conn = null;
		try {
//			conn = getConnection();
			StringBuilder sql = new StringBuilder("");
			sql.append("UPDATE ORIG_APPLICATION ");
			sql.append(" SET REF_APPLICATION_RECORD_ID = ?, BUSINESS_CLASS_ID = ?, PROJECT_CODE = ? ");
			sql.append(" ,SP_SIGN_OFF_FLAG = ?, APPLICATION_TYPE = ?,LIFE_CYCLE = ?, CARDLINK_REF_NO = ? ");
			sql.append(" ,TCB_ACCOUNT_NO = ?, RECOMMEND_DECISION = ?, SP_SIGNOFF_DATE = ? ");
			sql.append(" ,SP_SIGNOFF_AUTH_BY = ?, MAINCARD_RECORD_ID = ?, DIFF_REQUEST_RESULT = ? ");
			sql.append(" ,PROLICY_PROGRAM_ID = ?, EXISTING_PRIORITY_PASS = ? ");
			sql.append(" ,FINAL_APP_DECISION =? , FINAL_APP_DECISION_BY =?, FINAL_APP_DECISION_DATE = ? ");
			sql.append(" ,POLICY_SEGMENT_ID= ?, IS_VETO_ELIGIBLE = ?, REFER_CRITERIA = ?, UPDATE_BY = ?, UPDATE_DATE = ?");
			sql.append(" , CARDLINK_SEQ = ?, CARDLINK_FLAG = ?, VLINK_FLAG = ? ,RE_EXECUTE_KEY_PROGRAM_FLAG=? ");
			sql.append(" , BLOCK_FLAG = ? , REOPEN_FLAG = ? "); //KPL Additional
			sql.append(" , E_RECOMMEND_DECISION = ? , LOW_INCOME_FLAG = ?, E_APPLICATION_RECORD_ID = ?, APPLY_TYPE_FLAG = ? ");
			sql.append(" WHERE APPLICATION_RECORD_ID = ?");
			String dSql = sql.toString();
			logger.debug("Sql=" + dSql);
			ps = conn.prepareStatement(dSql);
			int cnt = 1;
			ps.setString(cnt++, application.getRefApplicationRecordId());
			ps.setString(cnt++, application.getBusinessClassId());
			ps.setString(cnt++, application.getProjectCode());
			
			ps.setString(cnt++, application.getSpSignOffFlag());
			ps.setString(cnt++, application.getApplicationType());
			ps.setInt(cnt++, application.getLifeCycle());
			ps.setString(cnt++, application.getCardlinkRefNo());
			
			ps.setString(cnt++, application.getTcbAccountNo());
			ps.setString(cnt++, application.getRecommendDecision());
			ps.setDate(cnt++, application.getSpSignoffDate());
			
			ps.setString(cnt++, application.getSpSignoffAuthBy());
			ps.setString(cnt++, application.getMaincardRecordId());
			ps.setString(cnt++, application.getDiffRequestResult());
			
			ps.setString(cnt++, application.getPolicyProgramId());
			ps.setString(cnt++, application.getExistingPriorityPass());
			
			ps.setString(cnt++, application.getFinalAppDecision());
			ps.setString(cnt++, application.getFinalAppDecisionBy());
			ps.setDate(cnt++, application.getFinalAppDecisionDate());
			
			ps.setString(cnt++, application.getPolicySegmentId());
			ps.setString(cnt++, application.getIsVetoEligibleFlag());
			ps.setString(cnt++, application.getReferCriteria());
			ps.setString(cnt++, application.getUpdateBy());
			ps.setTimestamp(cnt++, ApplicationDate.getTimestamp());
			
			ps.setInt(cnt++, application.getCardlinkSeq());
			ps.setString(cnt++, application.getCardlinkFlag());
			ps.setString(cnt++, application.getVlinkFlag());
			ps.setString(cnt++, application.getReExecuteKeyProgramFlag());
			
			ps.setString(cnt++, application.getSavingPlusFlag()); //KPL Additional
			ps.setString(cnt++, application.getDiffRequestFlag()); //KPL Additional
			
			ps.setString(cnt++, application.geteRecommendDecision());
			ps.setString(cnt++, application.getLowIncomeFlag());
			ps.setString(cnt++, application.geteApplicationRecordId());
			ps.setString(cnt++, application.getApplyTypeFlag());
			// WHERE CLAUSE
			ps.setString(cnt++, application.getApplicationRecordId());
			
			
			returnRows = ps.executeUpdate();
		} catch (Exception e) {
			logger.fatal(e.getLocalizedMessage());
			throw new ApplicationException(e.getMessage());
		} finally {
			try {
				closeConnection(ps);
			} catch (Exception e) {
				logger.fatal(e.getLocalizedMessage());
			}
		}
		return returnRows;
	}
	
	public int updateFinalAppDecision(String applicationRecordId, String decision) throws ApplicationException {
		// FINAL_APP_DECISION
		int returnRows = 0;
		PreparedStatement ps = null;
		Connection conn = null;
		try{
			String sql = "UPDATE ORIG_APPLICATION SET FINAL_APP_DECISION = ?, FINAL_APP_DECISION_BY = ?, FINAL_APP_DECISION_DATE=? " +
					", UPDATE_BY = ?, UPDATE_DATE = ? WHERE APPLICATION_RECORD_ID = ? ";
			logger.debug("sql >> "+sql);
			conn = getConnection();
			ps = conn.prepareStatement(sql);
			
			int cnt = 1;
			ps.setString(cnt++, decision);
			ps.setString(cnt++, userId);			
			ps.setTimestamp(cnt++, ApplicationDate.getTimestamp());
			ps.setString(cnt++, userId);			
			ps.setTimestamp(cnt++, ApplicationDate.getTimestamp());
			ps.setString(cnt++, applicationRecordId);
			
			returnRows = ps.executeUpdate();
		}catch(Exception e){
			logger.fatal(e.getLocalizedMessage());
			throw new ApplicationException(e.getMessage());
		}finally{
			try{
				closeConnection(conn, ps);
			}catch(Exception e){
				logger.fatal(e.getLocalizedMessage());
			}
		}		
		return returnRows;
	}
	
//	#rawi comment used updateFinalAppDecision to update ORIG_APPLICATION.FINAL_APP_DECISION_BY
//	public int updateFinalAppDecisionBy(String applicationId, String userName) throws ApplicationException {
//		// FINAL_APP_DECISION
//		int returnRows = 0;
//		PreparedStatement ps = null;
//		Connection conn = null;
//		try {
//			String sql = "UPDATE ORIG_APPLICATION SET FINAL_APP_DECISION_BY = ?, FINAL_APP_DECISION_DATE=?  WHERE APPLICATION_RECORD_ID = ? ";
//			logger.debug("sql >> "+sql);
//			conn = getConnection();
//			ps = conn.prepareStatement(sql);
//			int cnt = 1;
//			ps.setString(cnt++, userName);
//			ps.setTimestamp(cnt++, ApplicationDate.getTimestamp());
//			
//			ps.setString(cnt++, applicationId);
//			
//			returnRows = ps.executeUpdate();
//		} catch (Exception e) {
//			logger.fatal(e.getLocalizedMessage());
//			throw new ApplicationException(e.getMessage());
//		} finally {
//			try {
//				closeConnection(conn, ps);
//			} catch (Exception e) {
//				logger.fatal(e.getLocalizedMessage());
//			}
//		}
//		return returnRows;
//	}
	
	public boolean isAllCancelled(String applicationId) throws ApplicationException {
		PreparedStatement ps = null;
		Connection conn = null;
		ResultSet rs = null;
		boolean result = false;
		try{
			String sql = "SELECT PKA_CANCEL_APP.CHECK_ALL_APP_CANCELLED(?) ALL_CANCELLED FROM DUAL  ";
			conn = getConnection();
			ps = conn.prepareStatement(sql);
			int cnt = 1;
			ps.setString(cnt++, applicationId);
			rs = ps.executeQuery();
			if(rs.next()) {
				if ("TRUE".equals(rs.getString("ALL_CANCELLED"))) {
					result = true;
				}
			}
		}catch(Exception e){
			logger.fatal(e.getLocalizedMessage());
			throw new ApplicationException(e.getMessage());
		}finally{
			try{
				closeConnection(conn,rs, ps);
			}catch(Exception e){
				logger.fatal(e.getLocalizedMessage());
			}
		}
		
		return result;
	}

	@Override
	public ArrayList<ApplicationDataM> loadApplicationsForGroup(String applicationGroupId) throws ApplicationException {
//		List<String> applicationIds = selectApplicationForGroups(applicationGroupId);
//		ArrayList<ApplicationDataM> appList = new ArrayList<ApplicationDataM>();
//		for(String applicaitonId: applicationIds) {
//			appList.add(loadOrigApplicationM(applicaitonId));
//		}
//		return appList;
		return loadOrigApplicationM(applicationGroupId);
	}
	@Override
	public ArrayList<ApplicationDataM> loadApplicationsForGroup(String applicationGroupId, Connection conn)throws ApplicationException {
		return loadOrigApplicationM(applicationGroupId,conn);
	}
	@Override
	public ArrayList<ApplicationDataM> loadApplicationsVlink(String applicationGroupId, Connection conn)throws ApplicationException {
		try{
			ArrayList<ApplicationDataM> applications =  selectTableORIG_APPLICATION(applicationGroupId,conn);
			if(!Util.empty(applications)){
				for(ApplicationDataM application:applications){
					OrigLoanDAO loanDAO = ORIGDAOFactory.getLoanDAO();
					ArrayList<LoanDataM> loans = loanDAO.loadOrigLoanVlink(application.getApplicationRecordId(),conn);
					if(!Util.empty(loans)) {
						application.setLoans(loans);
					}
				}
			}
			return applications;
		}catch(Exception e){
			logger.fatal("ERROR",e);
			throw new ApplicationException(e.getMessage());
		}
	}
	@Override
	public ArrayList<ApplicationDataM> loadTableORIG_APPLICATION(String applicationGroupId) throws ApplicationException {
//		List<String> applicationRecordIds = selectApplicationForGroups(applicationGroupId);
//		ArrayList<ApplicationDataM> applications = new ArrayList<ApplicationDataM>();
//		for(String applicationRecordId: applicationRecordIds) {
//			applications.add(selectTableORIG_APPLICATION(applicationRecordId));
//		}
//		return applications;
		return selectTableORIG_APPLICATION(applicationGroupId);
	}
	
//	private List<String> selectApplicationForGroups(String applicationGroupId) throws ApplicationException {
//		PreparedStatement ps = null;
//		ResultSet rs = null;
//		Connection conn = null;
//		try {
//			//conn = Get Connection
//			conn = getConnection();
//			StringBuilder sql = new StringBuilder("");
//			sql.append("SELECT APPLICATION_RECORD_ID ");
//			sql.append(" FROM ORIG_APPLICATION WHERE APPLICATION_GROUP_ID = ? ");
//			String dSql = sql.toString();
//			logger.debug("Sql=" + dSql);
//			ps = conn.prepareStatement(dSql);
//			ps.setString(1, applicationGroupId);
//
//			rs = ps.executeQuery();
//			ArrayList<String> appRecordLst = new ArrayList<String>();
//			
//			while (rs.next()) {
//				appRecordLst.add(rs.getString("APPLICATION_RECORD_ID"));
//			}
//
//			return appRecordLst;
//		} catch (Exception e) {
//			logger.fatal(e.getLocalizedMessage());
//			throw new ApplicationException(e.getMessage());
//		} finally {
//			try {
//				closeConnection(conn, rs, ps);
//			} catch (Exception e) {
//				logger.fatal(e.getLocalizedMessage());
//			}
//		}
//	}

	@Override
	public void deleteNotInKeyApplication(ArrayList<ApplicationDataM> appList,
			String applicationGroupId) throws ApplicationException {
		ArrayList<String> notInKeyIdList = selectNotInKeyTableORIG_APPLICATION(appList, applicationGroupId);
		if(!Util.empty(notInKeyIdList)) {
			OrigBundleHLDAO origBundleHLDAO = ORIGDAOFactory.getBundleHLDAO();
			OrigBundleKLDAO origBundleKLDAO = ORIGDAOFactory.getBundleKLDAO();
			OrigBundleSMEDAO origBundleSMEDAO = ORIGDAOFactory.getBundleSMEDAO();
			OrigReasonDAO reasonDAO = ORIGDAOFactory.getReasonDAO();			
			OrigReasonLogDAO reasonLogDAO = ORIGDAOFactory.getReasonLogDAO();
			OrigLoanDAO loanDAO = ORIGDAOFactory.getLoanDAO();
			OrigVerificationResultDAO verificationDAO = ORIGDAOFactory.getVerificationResultDAO();
			
			for(String applicationRecordId: notInKeyIdList) {
				origBundleHLDAO.deleteOrigBundleHLM(applicationRecordId);
				origBundleKLDAO.deleteOrigBundleKLM(applicationRecordId);
				origBundleSMEDAO.deleteOrigBundleSMEM(applicationRecordId);
				reasonDAO.deleteOrigReasonM(applicationRecordId);
				reasonLogDAO.deleteOrigReasonLogM(applicationRecordId, null);
				loanDAO.deleteOrigLoanM(applicationRecordId, null);
				verificationDAO.deleteOrigVerificationResultM(applicationRecordId, MConstant.REF_LEVEL.APPLICATION, null);
			}
		}
		deleteNotInKeyTableORIG_APPLICATION(appList, applicationGroupId);
	}

	private void deleteNotInKeyTableORIG_APPLICATION(
			ArrayList<ApplicationDataM> appList, String applicationGroupId) throws ApplicationException {
		PreparedStatement ps = null;
		Connection conn = null;
		try {
            // conn = Get Connection
            conn = getConnection();

            StringBuffer sql = new StringBuffer("");
            sql.append("DELETE FROM ORIG_APPLICATION ");
            sql.append(" WHERE APPLICATION_GROUP_ID = ? ");
            
            if (!Util.empty(appList)) {
                sql.append(" AND APPLICATION_RECORD_ID NOT IN ( ");
                for (ApplicationDataM appM: appList) {
                	logger.debug("appM.getApplicationRecordId() = "+appM.getApplicationRecordId());
                    sql.append(" '" + appM.getApplicationRecordId() + "', ");
                }
                
                if (sql.toString().trim().endsWith(",")) {
                    sql.delete(sql.toString().lastIndexOf(","), sql.toString().length());
                }

                sql.append(" ) ");
            }

            String dSql = String.valueOf(sql);
            logger.debug("Sql=" + dSql);
			ps = conn.prepareStatement(dSql);
            ps.setString(1, applicationGroupId);
            
            ps.executeUpdate();
            ps.close();

        } catch (Exception e) {
        	logger.error("ERROR >> ",e);
			throw new ApplicationException(e.getMessage());
		} finally {
			try {
				closeConnection(conn, ps);
			} catch (Exception e) {
				logger.error("ERROR >> ",e);
				throw new ApplicationException(e.getMessage());
			}
		}
	}

	private ArrayList<String> selectNotInKeyTableORIG_APPLICATION(ArrayList<ApplicationDataM> appList, String applicationGroupId) throws ApplicationException {
		PreparedStatement ps = null;
		Connection conn = null;
		ResultSet rs = null;
		ArrayList<String> idList = new ArrayList<String>();
		try {
            // conn = Get Connection
            conn = getConnection();

            StringBuffer sql = new StringBuffer("");
            sql.append("SELECT APPLICATION_RECORD_ID FROM ORIG_APPLICATION ");
            sql.append(" WHERE APPLICATION_GROUP_ID = ? ");
            
            if (!Util.empty(appList)) {
                sql.append(" AND APPLICATION_RECORD_ID NOT IN ( ");

                for (ApplicationDataM appM: appList) {
                    sql.append(" '" + appM.getApplicationRecordId()+ "' , ");
                }

                if (sql.toString().trim().endsWith(",")) {
                    sql.delete(sql.toString().lastIndexOf(","), sql.toString().length());
                }

                sql.append(" ) ");
            }

            String dSql = String.valueOf(sql);
            logger.debug("Sql=" + dSql);
			ps = conn.prepareStatement(dSql);
            ps.setString(1, applicationGroupId);
            
            rs = ps.executeQuery();
            while(rs.next()) {
            	String keyId =  rs.getString("APPLICATION_RECORD_ID");
            	idList.add(keyId);
            }

            return idList;
        } catch (Exception e) {
        	logger.error("ERROR >> ",e);
			throw new ApplicationException(e.getMessage());
		} finally {
			try {
				closeConnection(conn, rs, ps);
			} catch (Exception e) {
				logger.error("ERROR >> ",e);
				throw new ApplicationException(e.getMessage());
			}
		}
	}
	
	@Override
	public void setApplicationFinalDecision(ApplicationDataM application,ArrayList<String> finalDecision)throws ApplicationException {
		PreparedStatement ps = null;
		ResultSet rs = null;
		Connection conn = null;
		try {
			conn = getConnection();
			StringBuilder sql = new StringBuilder("");
			sql.append("SELECT FINAL_APP_DECISION, FINAL_APP_DECISION_BY, FINAL_APP_DECISION_DATE ");
			sql.append(" FROM ORIG_APPLICATION WHERE APPLICATION_RECORD_ID = ? ");
			String dSql = sql.toString();
			logger.debug("Sql=" + dSql);
			ps = conn.prepareStatement(dSql);
			ps.setString(1, application.getApplicationRecordId());

			rs = ps.executeQuery();
			if(rs.next()) {
				String FINAL_APP_DECISION = rs.getString("FINAL_APP_DECISION");
				String FINAL_APP_DECISION_BY = rs.getString("FINAL_APP_DECISION_BY");
				Date FINAL_APP_DECISION_DATE = rs.getDate("FINAL_APP_DECISION_DATE");
				logger.debug("PRE FINAL_APP_DECISION>>"+FINAL_APP_DECISION);
				if(finalDecision.contains(FINAL_APP_DECISION)){					
					application.setFinalAppDecision(FINAL_APP_DECISION);
					application.setFinalAppDecisionBy(FINAL_APP_DECISION_BY);
					application.setFinalAppDecisionDate(FINAL_APP_DECISION_DATE);	
				}
			 
			}
		} catch (Exception e) {
			logger.fatal(e.getLocalizedMessage());
			throw new ApplicationException(e.getMessage());
		} finally {
			try {
				closeConnection(conn, rs, ps);
			} catch (Exception e) {
				logger.fatal(e.getLocalizedMessage());
			}
		}
	}
	@Override
	public ArrayList<String> loadApplicationUniqueIds(String applicationGroupId, int lifeCycle)throws ApplicationException {
		PreparedStatement ps = null;
		ResultSet rs = null;
		Connection conn = null;
		ArrayList<String> uniqueIds = new ArrayList<String>();
		try{
			conn = getConnection();
			StringBuilder sql = new StringBuilder("");
			sql.append("SELECT APPLICATION_RECORD_ID FROM ORIG_APPLICATION WHERE APPLICATION_GROUP_ID = ? AND LIFE_CYCLE = ?");
			String dSql = sql.toString();
			logger.debug("Sql=" + dSql);
			ps = conn.prepareStatement(dSql);
			ps.setString(1,applicationGroupId);
			ps.setInt(2,lifeCycle);
			rs = ps.executeQuery();
			while(rs.next()){
				uniqueIds.add(rs.getString("APPLICATION_RECORD_ID"));
			}
		}catch(Exception e){
			logger.fatal(e.getLocalizedMessage());
			throw new ApplicationException(e.getMessage());
		}finally{
			try{
				closeConnection(conn, rs, ps);
			}catch(Exception e){
				logger.fatal(e.getLocalizedMessage());
			}
		}
		return uniqueIds;
	}
	@Override
	public ArrayList<String> loadFinalAppDecision(String applicationGroupId,int lifeCycle) throws ApplicationException {
		PreparedStatement ps = null;
		ResultSet rs = null;
		Connection conn = null;
		ArrayList<String> finalAppDecisions = new ArrayList<String>();
		try{
			conn = getConnection();
			StringBuilder sql = new StringBuilder("");
			sql.append("SELECT FINAL_APP_DECISION FROM ORIG_APPLICATION WHERE APPLICATION_GROUP_ID = ? AND LIFE_CYCLE = ?");
			String dSql = sql.toString();
			logger.debug("Sql=" + dSql);
			ps = conn.prepareStatement(dSql);
			ps.setString(1,applicationGroupId);
			ps.setInt(2,lifeCycle);
			rs = ps.executeQuery();
			while(rs.next()){
				if(!Util.empty(rs.getString("FINAL_APP_DECISION"))){
					finalAppDecisions.add(rs.getString("FINAL_APP_DECISION"));	
				}else{
					finalAppDecisions.add("UNKNOW");
				}
			}
		}catch(Exception e){
			logger.fatal(e.getLocalizedMessage());
			throw new ApplicationException(e.getMessage());
		}finally{
			try{
				closeConnection(conn, rs, ps);
			}catch(Exception e){
				logger.fatal(e.getLocalizedMessage());
			}
		}
		return finalAppDecisions;
	}
	@Override
	public ArrayList<String> loadRefUniqueIds(String applicationGroupId,String applicationRecordId) throws ApplicationException {
		PreparedStatement ps = null;
		ResultSet rs = null;
		Connection conn = null;
		ArrayList<String> uniqueIds = new ArrayList<String>();
		try{
			conn = getConnection();
			StringBuilder sql = new StringBuilder("");
			sql.append("SELECT APPLICATION_RECORD_ID FROM ORIG_APPLICATION WHERE APPLICATION_GROUP_ID = ? AND MAINCARD_RECORD_ID = ?");
			String dSql = sql.toString();
			logger.debug("Sql=" + dSql);
			ps = conn.prepareStatement(dSql);
			ps.setString(1,applicationGroupId);
			ps.setString(2,applicationRecordId);
			rs = ps.executeQuery();
			while(rs.next()){
				uniqueIds.add(rs.getString("APPLICATION_RECORD_ID"));	 
			}
		}catch(Exception e){
			logger.fatal(e.getLocalizedMessage());
			throw new ApplicationException(e.getMessage());
		}finally{
			try{
				closeConnection(conn, rs, ps);
			}catch(Exception e){
				logger.fatal(e.getLocalizedMessage());
			}
		}
		return uniqueIds;
	}
	@Override
	public Date getFinalAppDecisionDate(String applicationGroupId,int lifeCycle)throws ApplicationException{
		PreparedStatement ps = null;
		ResultSet rs = null;
		Connection conn = null;
		Date finalAppDecisionDate = null;
		try{
			conn = getConnection();
			StringBuilder sql = new StringBuilder("");
			sql.append("SELECT FINAL_APP_DECISION_DATE FROM ORIG_APPLICATION WHERE APPLICATION_GROUP_ID = ? AND LIFE_CYCLE = ?");
			String dSql = sql.toString();
			logger.debug("Sql=" + dSql);
			ps = conn.prepareStatement(dSql);
				ps.setString(1,applicationGroupId);
				ps.setInt(2,lifeCycle);
			rs = ps.executeQuery();
			if(rs.next()){
				finalAppDecisionDate = rs.getDate("FINAL_APP_DECISION_DATE");
			}
		}catch(Exception e){
			logger.fatal(e.getLocalizedMessage());
			throw new ApplicationException(e.getMessage());
		}finally{
			try{
				closeConnection(conn, rs, ps);
			}catch(Exception e){
				logger.fatal(e.getLocalizedMessage());
			}
		}
		return finalAppDecisionDate;
	}
}
