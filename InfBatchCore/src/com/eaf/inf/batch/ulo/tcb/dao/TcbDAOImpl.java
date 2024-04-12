package com.eaf.inf.batch.ulo.tcb.dao;

import java.math.BigDecimal;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Vector;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;

import com.eaf.core.ulo.common.cache.InfBatchProperty;
import com.eaf.core.ulo.common.dao.InfBatchObjectDAO;
import com.eaf.core.ulo.common.dao.InfBatchServiceLocator;
import com.eaf.core.ulo.common.exception.InfBatchException;
import com.eaf.core.ulo.common.properties.SystemConstant;
import com.eaf.core.ulo.common.task.model.TaskObjectDataM;
import com.eaf.core.ulo.common.util.Util;
import com.eaf.inf.batch.ulo.tcb.core.UpdateAccountTask;
import com.eaf.inf.batch.ulo.tcb.core.UpdateListTaskObject;
import com.eaf.orig.ulo.constant.MConstant;
import com.eaf.orig.ulo.model.app.LoanSetupDataM;

public class TcbDAOImpl extends InfBatchObjectDAO implements TcbDAO{
	private static transient Logger logger = Logger.getLogger(TcbDAOImpl.class);
	private static final String CIS_NO = "CIS_NO";
	private static final String ACCOUNT_NO = "ACCOUNT_NO";
	private static final String OPEN_DATE = "OPEN_DATE";
	String DIH_ACCT_STATUS_ACTIVE = SystemConstant.getConstant("DIH_ACCT_STATUS_ACTIVE");
	
	@Override
	public ArrayList<TaskObjectDataM> loadCISNo() throws InfBatchException{
		Connection conn = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		ArrayList<TaskObjectDataM> hTaskObjectCis = new ArrayList<TaskObjectDataM>();
		try{
			conn = getConnection();
			StringBuilder sql = new StringBuilder();
			// Select record which has Claim record (created after Fraud result is passed) and status is not Pending Setup (after autosetup return)
			sql.append(" SELECT AG.APPLICATION_GROUP_NO, LC.CIS_NO, LC.DE2_SUBMIT_DATE, LC.CLAIM_ID, LC.LOAN_ID, LN.CB_PRODUCT, LN.CB_SUB_PRODUCT, CB_MARKET_CODE, LN.LOAN_AMT, LN.TERM"
					+  " FROM ORIG_APPLICATION_GROUP AG"
					+  " JOIN ORIG_APPLICATION AP ON AP.APPLICATION_GROUP_ID=AG.APPLICATION_GROUP_ID AND AP.LIFE_CYCLE=AG.LIFE_CYCLE"
					+  " JOIN ORIG_LOAN LN ON LN.APPLICATION_RECORD_ID=AP.APPLICATION_RECORD_ID"
					+  " JOIN LOAN_SETUP_CLAIM LC ON LC.LOAN_ID=LN.LOAN_ID"
					+  " WHERE"
					+  "	AP.FINAL_APP_DECISION = 'Approve'"
					+  "	AND LC.LOAN_SETUP_STATUS <> '" + LoanSetupDataM.PENDING_SETUP_STATUS + "'"
					+  " 	AND LC.LOAN_ACCOUNT_NO IS NULL"
					+  " 	AND LC.TYPE = '" + LoanSetupDataM.STAMP_DUTY_TYPE + "'");
			logger.debug("sql : "+sql);
			ps = conn.prepareStatement(sql.toString());
			rs = ps.executeQuery();
			while(rs.next()){
				String APPLICATION_GROUP_NO = rs.getString("APPLICATION_GROUP_NO");
				String CIS_NO = rs.getString("CIS_NO");
				Timestamp APPROVE_DATE = rs.getTimestamp("DE2_SUBMIT_DATE");
				String CLAIM_ID = rs.getString("CLAIM_ID");
				String LOAN_ID = rs.getString("LOAN_ID");
				String PRODUCT = rs.getString("CB_PRODUCT");
				String SUB_PRODUCT = rs.getString("CB_SUB_PRODUCT");
				String MARKET_CODE = rs.getString("CB_MARKET_CODE");
				BigDecimal LIMIT_AMOUNT = rs.getBigDecimal("LOAN_AMT");
				BigDecimal TERM = rs.getBigDecimal("TERM");
				UpdateListTaskObject taskObject = new UpdateListTaskObject();
					taskObject.setApplicationGroupNo(APPLICATION_GROUP_NO);
					taskObject.setCisNo(CIS_NO);
					taskObject.setApproveDate(APPROVE_DATE);
					taskObject.setLoanId(LOAN_ID);
					taskObject.setClaimId(CLAIM_ID);
					taskObject.setProduct(PRODUCT);
					taskObject.setSubProduct(SUB_PRODUCT);
					taskObject.setMarketCode(MARKET_CODE);
					taskObject.setLimit(LIMIT_AMOUNT);
					taskObject.setTerm(TERM);
				hTaskObjectCis.add(taskObject);
			}
		}catch(Exception e){
			logger.fatal("ERROR ",e);
			throw new InfBatchException(e.getMessage());
		}finally{
			try{
				closeConnection(conn, rs, ps);
			}catch(Exception e){
				logger.fatal("ERROR "+e.getLocalizedMessage());
			}
		}
		return hTaskObjectCis;
	}
	
	@Override
	public ArrayList<TaskObjectDataM> loadAccountNoFromDIH(UpdateListTaskObject taskObject) throws InfBatchException{
		Connection conn = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		ArrayList<TaskObjectDataM> acctTaskObjectList = new ArrayList<TaskObjectDataM>();
		try{
			conn = getConnection(InfBatchServiceLocator.DIH);
			StringBuilder sql = new StringBuilder();
			// We directly search for account in V_FNC_SVC_AR as KPL has only main applicant (IP_ID in this view reflects only main applicant)
			// If it turns out that supplementary applicant is applicable, then need to join with AR_X_IP
			sql.append(" SELECT MAIN_CIS_NO, ACCOUNT_NO, OPEN_DATE, STATUS ");
			sql.append(" FROM ( ");
			sql.append(" SELECT ROW_NUMBER() OVER(PARTITION BY AR_ID ORDER BY POS_DT DESC) AS RN, ");
			sql.append(" IP_ID AS MAIN_CIS_NO, AR_ID AS ACCOUNT_NO, OPN_DT AS OPEN_DATE, AR_LCS_TP_CD AS STATUS ");
			sql.append(" FROM AR_SHR.V_FNC_SVC_AR ");
			sql.append(" WHERE IP_ID = ? ");
			sql.append(" AND PD_TP_CD = ? ");
			sql.append(" AND PD_SUB_TP_CD = ? ");
			sql.append(" AND MKT_CD = ? ");
			sql.append(" AND OPN_DT >= ? ");
			sql.append(" AND LMT_AMT = ? ");
			sql.append(" AND CTR_AR_TERM = ? ");
			sql.append(" ) A ");
			sql.append(" WHERE A.RN = 1 ");

			logger.info("sql : "+sql + "(value for " + taskObject.getApplicationGroupNo() + "=" + taskObject.getCisNo() 
					+ "," + taskObject.getProduct() + "," + taskObject.getSubProduct() 
					+ "," + taskObject.getMarketCode() + "," + taskObject.getApproveDate() 
					+ "," + taskObject.getLimit() + "," + taskObject.getTerm() +")");

			ps = conn.prepareStatement(sql.toString());
			int cnt = 1;
			
			ps.setString(cnt++, taskObject.getCisNo());
			ps.setString(cnt++, taskObject.getProduct());
			ps.setString(cnt++, taskObject.getSubProduct());
			ps.setString(cnt++, taskObject.getMarketCode());
			ps.setDate(cnt++, new java.sql.Date(taskObject.getApproveDate().getTime()));
			ps.setBigDecimal(cnt++, taskObject.getLimit());
			ps.setBigDecimal(cnt++, taskObject.getTerm());
			rs = ps.executeQuery();
			while(rs.next()){
				String MAIN_CIS_NO = rs.getString("MAIN_CIS_NO");
				String ACCOUNT_NO = rs.getString("ACCOUNT_NO");
				String STATUS = rs.getString("STATUS");
				String LOAN_ID = taskObject.getLoanId();
				Timestamp OPEN_DATE = rs.getTimestamp("OPEN_DATE");
				UpdateListTaskObject acctTaskObject = new UpdateListTaskObject();
				acctTaskObject.setApplicationGroupNo(taskObject.getApplicationGroupNo());
				acctTaskObject.setMainCisNo(MAIN_CIS_NO);
				acctTaskObject.setAccountNo(ACCOUNT_NO);
				acctTaskObject.setOpenDate(OPEN_DATE);
				acctTaskObject.setStatus(STATUS);
				acctTaskObject.setLoanId(LOAN_ID);
				acctTaskObject.setClaimId(taskObject.getClaimId());
				acctTaskObjectList.add(acctTaskObject);
				
				logger.info("For CIS=" + MAIN_CIS_NO + " with " + taskObject.getApplicationGroupNo() 
				+ ", set account no=" + ACCOUNT_NO + " opendate=" + OPEN_DATE + " status=" + STATUS);
			}
		}catch(Exception e){
			logger.fatal("ERROR ",e);
			throw new InfBatchException(e.getMessage());
		}finally{
			try{
				closeConnection(conn, rs, ps);
			}catch(Exception e){
				logger.fatal("ERROR "+e.getLocalizedMessage());
			}
		}
		return acctTaskObjectList;
	}
	
	@Override
	public void updateAccountNo(UpdateListTaskObject data) throws InfBatchException{
		Connection conn = null;
		PreparedStatement ps = null;
		try{
			conn = getConnection();
			conn.setAutoCommit(false);
			
			// Update ORIG_LOAN
			String sql = "UPDATE ORIG_LOAN SET ACCOUNT_NO = ? , ACCOUNT_OPEN_DATE = ? , UPDATE_DATE = SYSDATE WHERE LOAN_ID = ?";
			logger.debug("sql : "+sql);
			ps = conn.prepareStatement(sql);
			int parameter = 1;
			ps.setString(parameter++, data.getAccountNo());
			ps.setTimestamp(parameter++, data.getOpenDate());
			ps.setString(parameter++, data.getLoanId());
			if (ps.executeUpdate() == 0) {
				logger.error("No row updated");
				conn.rollback();
				throw new InfBatchException("No row in ORIG_LOAN was updated");
			}
			// Update LOAN_SETUP_CLAIM
			sql = "UPDATE LOAN_SETUP_CLAIM SET LOAN_ACCOUNT_NO = ?, LOAN_ACCOUNT_STATUS = ?, LOAN_ACCOUNT_OPEN_DATE = ? , UPDATE_DATE = SYSDATE WHERE CLAIM_ID = ?";
			logger.debug("sql : "+sql);
			ps = conn.prepareStatement(sql);
			parameter = 1;
			ps.setString(parameter++, data.getAccountNo());
			
			if(Util.empty(data.getStatus()))
			{ps.setString(parameter++, "");}
			else
			{ps.setString(parameter++, (DIH_ACCT_STATUS_ACTIVE.equals(data.getStatus())) ? MConstant.FLAG.ACTIVE : MConstant.FLAG.INACTIVE );}
			
			ps.setTimestamp(parameter++, data.getOpenDate());
			ps.setString(parameter++, data.getClaimId());
			ps.executeUpdate();
			if (ps.executeUpdate() == 0) {
				logger.error("No row updated");
				conn.rollback();
				throw new InfBatchException("No row in LOAN_SETUP_CLAIM was updated");
			}
			conn.commit();
		}catch(Exception e){
			logger.fatal("ERROR ",e);
			try {
				conn.rollback();
			} catch (Exception e1) {
				logger.fatal("ROLLBACK ERROR ", e);
			};
			throw new InfBatchException(e.getMessage());
		}finally{
			try{
				closeConnection(conn, ps);
			}catch(Exception e){
				logger.fatal("ERROR "+e.getLocalizedMessage());
			}
		}
	}

	@Override
	public void updateKPLTopUPAccountNo(HashMap<String, String> data)throws InfBatchException {	
		logger.error("You are using unimplement method!!!");
		/*
		Connection conn = null;
		PreparedStatement ps = null;
		try{
			conn = getConnection();
			StringBuilder sql = new StringBuilder();
			sql.append(" UPDATE ORIG_LOAN ");
			sql.append(" SET ");
			sql.append(" ACCOUNT_NO = ? ");
			sql.append(" WHERE APPLICATION_RECORD_ID IN (  ");
				sql.append(" SELECT  DISTINCT ORIG_APPLICATION.APPLICATION_RECORD_ID FROM  ORIG_APPLICATION_GROUP ");
				sql.append(" INNER JOIN ORIG_APPLICATION ON ORIG_APPLICATION.APPLICATION_GROUP_ID = ORIG_APPLICATION_GROUP.APPLICATION_GROUP_ID");
				sql.append(" INNER JOIN ORIG_PERSONAL_INFO ON ORIG_PERSONAL_INFO.APPLICATION_GROUP_ID = ORIG_APPLICATION_GROUP.APPLICATION_GROUP_ID");
				sql.append(" INNER JOIN BUSINESS_CLASS ON BUSINESS_CLASS.BUS_CLASS_ID = ORIG_APPLICATION.BUSINESS_CLASS_ID ");
				sql.append(" INNER JOIN ORIG_LOAN ON ORIG_LOAN.APPLICATION_RECORD_ID = ORIG_APPLICATION.APPLICATION_RECORD_ID ");
				sql.append(" WHERE (ORIG_LOAN.OLD_ACCOUNT_NO <> ? OR ORIG_LOAN.OLD_ACCOUNT_NO IS NULL) ");
				sql.append(" AND BUSINESS_CLASS.ORG_ID = ? ");
				sql.append(" AND ORIG_PERSONAL_INFO.CIS_NO = ? ");
			sql.append(" ) ");

			logger.debug("sql : "+sql);
			ps = conn.prepareStatement(sql.toString());
			int parameter = 1;
			ps.setString(parameter++, data.get(ACCOUNT_NO));
			ps.setString(parameter++, data.get(ACCOUNT_NO));
			ps.setString(parameter++, InfBatchProperty.getInfBatchConfig("TCB_ORG_ID"));
			ps.setString(parameter++, data.get(CIS_NO));
			ps.executeUpdate();
		}catch(Exception e){
			logger.fatal("ERROR ",e);
			throw new InfBatchException(e.getMessage());
		}finally{
			try{
				closeConnection(conn, ps);
			}catch(Exception e){
				logger.fatal("ERROR "+e.getLocalizedMessage());
			}
		}
		*/
	}
}
