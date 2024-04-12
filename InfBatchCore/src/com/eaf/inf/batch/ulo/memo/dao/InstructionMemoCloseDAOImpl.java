package com.eaf.inf.batch.ulo.memo.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

import org.apache.log4j.Logger;

import com.eaf.core.ulo.common.cache.InfBatchProperty;
import com.eaf.core.ulo.common.dao.InfBatchObjectDAO;
import com.eaf.core.ulo.common.exception.InfBatchException;
import com.eaf.inf.batch.ulo.memo.model.InstructionMemoDataM;

public class InstructionMemoCloseDAOImpl extends InfBatchObjectDAO implements InstructionMemoCloseDAO {
	private static transient Logger logger = Logger.getLogger(InstructionMemoCloseDAOImpl.class);
	@Override
	public InstructionMemoDataM getInstructionCloseMemo(String applicationGroupId) throws InfBatchException {
		InstructionMemoDataM result = new InstructionMemoDataM();
		mapReportParam(result);
		mapOrigApplicationGroup(result, applicationGroupId);
		logger.info("Mapping " + applicationGroupId);
		return result;
	}
	private void mapReportParam(InstructionMemoDataM instructionMemo) {
		if (null == instructionMemo) {
			logger.debug("InstructionMemo is NULL!");
			return;
		}
		String PARAM_TYPE = "KPL_INSTRUCTION_MEMO_CLOSE";
		String PARAM_VALUE = "PARAM_VALUE";
		instructionMemo.setDearTo(InfBatchProperty.getReportParam(PARAM_TYPE, "01", PARAM_VALUE));
		instructionMemo.setPaymentReason(InfBatchProperty.getReportParam(PARAM_TYPE, "02", PARAM_VALUE));
		instructionMemo.setBlAccountNo(InfBatchProperty.getReportParam(PARAM_TYPE, "03",PARAM_VALUE));
	}
	private void mapOrigApplicationGroup(InstructionMemoDataM instructionMemo,String applicationGroupId) {
		Connection conn = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		try {
			conn = getConnection();
			StringBuilder sql = new StringBuilder();
			sql.append(" SELECT APPLICATION_GROUP_NO FROM ORIG_APPLICATION_GROUP WHERE APPLICATION_GROUP_ID = ? ");
			logger.debug("sql : " + sql);
			ps = conn.prepareStatement(sql.toString());
			ps.setString(1, applicationGroupId);
			rs = ps.executeQuery();
			if (rs.next()) {
				instructionMemo.setApplicationGroupNo(rs.getString("APPLICATION_GROUP_NO"));
			}
		}catch(Exception e){
			logger.fatal("ERROR ",e);
		}finally{
			try{
				closeConnection(conn, rs, ps);
			}catch(Exception e){
				logger.fatal("ERROR ",e);
			}
		}
		mapApplication(instructionMemo, applicationGroupId);
	}
	private void mapApplication(InstructionMemoDataM instructionMemo, String applicationGroupId) {
		String applicationRecordId = null;
		String businessClassId = null;
		Connection conn = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		try {
			conn = getConnection();
			StringBuilder sql = new StringBuilder();
			sql.append(" SELECT APP.APPLICATION_RECORD_ID, APP.BUSINESS_CLASS_ID " +
					"FROM ORIG_APPLICATION APP " +
					"LEFT JOIN BUSINESS_CLASS BUS ON APP.BUSINESS_CLASS_ID = BUS.BUS_CLASS_ID " +
					"WHERE APPLICATION_GROUP_ID = ? AND BUS.ORG_ID = ? ");
			logger.debug("sql : " + sql);
			ps = conn.prepareStatement(sql.toString());
			int index = 1;
			ps.setString(index++, applicationGroupId);
			ps.setString(index++, "KPL");
			rs = ps.executeQuery();
			if (rs.next()) {
				applicationRecordId = rs.getString("APPLICATION_RECORD_ID");
				businessClassId = rs.getString("BUSINESS_CLASS_ID");
			}
		} catch (Exception e) {
			logger.fatal("ERROR ",e);
		} finally {
			try {
				closeConnection(conn, rs, ps);
			} catch (Exception e) {
				logger.fatal("ERROR ",e);
			}
		}
		logger.debug("applicationRecordId : " + applicationRecordId);
		logger.debug("businessClassId : " + businessClassId);
		mapPersonalInfo(instructionMemo, applicationGroupId);
		mapLoan(instructionMemo, applicationRecordId);
	}
	private void mapPersonalInfo(InstructionMemoDataM instructionMemo, String applicationGroupId) {
		Connection conn = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		try{
			conn = getConnection();
			StringBuilder sql = new StringBuilder();
			sql.append("SELECT * FROM ORIG_PERSONAL_INFO WHERE APPLICATION_GROUP_ID = ? AND PERSONAL_TYPE = ? ");
			logger.debug("sql : " + sql);
			ps = conn.prepareStatement(sql.toString());
			int index = 1;
			ps.setString(index++, applicationGroupId);
			ps.setString(index++, "A");
			rs = ps.executeQuery();
			if (rs.next()) {
				instructionMemo.setIdNo(rs.getString("IDNO"));
				instructionMemo.setOwnerName(rs.getString("TH_FIRST_NAME") + " "+ rs.getString("TH_LAST_NAME"));
			}
		}catch(Exception e){
			logger.fatal("ERROR ",e);
		}finally{
			try{
				closeConnection(conn, rs, ps);
			}catch(Exception e){
				logger.fatal("ERROR ",e);
			}
		}
	}
	private void mapLoan(InstructionMemoDataM instructionMemo, String applicationRecordId){
		Connection conn = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		try{
			conn = getConnection();
			StringBuilder sql = new StringBuilder();
			sql.append(" SELECT * FROM ORIG_LOAN WHERE APPLICATION_RECORD_ID = ? ");
			logger.debug("sql : " + sql);
			ps = conn.prepareStatement(sql.toString());
			ps.setString(1, applicationRecordId);
			rs = ps.executeQuery();
			if (rs.next()) {
				instructionMemo.setOldAccountNo(rs.getString("OLD_ACCOUNT_NO"));
			}
		}catch(Exception e){
			logger.fatal("ERROR ",e);
		}finally{
			try{
				closeConnection(conn, rs, ps);
			}catch(Exception e){
				logger.fatal("ERROR ",e);
			}
		}
	}
}
