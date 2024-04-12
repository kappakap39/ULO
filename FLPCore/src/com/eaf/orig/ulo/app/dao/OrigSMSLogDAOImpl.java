package com.eaf.orig.ulo.app.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;

import com.eaf.core.ulo.common.date.ApplicationDate;
import com.eaf.core.ulo.common.util.Util;
import com.eaf.orig.shared.constant.OrigConstant;
import com.eaf.orig.shared.dao.OrigObjectDAO;
import com.eaf.orig.ulo.app.dao.exception.ApplicationException;
import com.eaf.orig.ulo.app.util.GenerateUnique;
import com.eaf.orig.ulo.model.app.SMSLogDataM;

public class OrigSMSLogDAOImpl extends OrigObjectDAO implements OrigSMSLogDAO {
	public OrigSMSLogDAOImpl(String userId){
		this.userId = userId;
	}
	public OrigSMSLogDAOImpl(){
		
	}
	private String userId = "";
	@Override
	public void createOrigSMSLogM(SMSLogDataM smsLogM)
			throws ApplicationException {
		logger.debug("smsLogM.getSmsLogId() :"+smsLogM.getSmsLogId());
	    if(Util.empty(smsLogM.getSmsLogId())){
			String smsLogId = GenerateUnique.generate(OrigConstant.SeqNames.ORIG_SMS_LOG_PK);
			smsLogM.setSmsLogId(smsLogId);
			smsLogM.setCreateBy(userId);
	    }
		createTableORIG_SMS_LOG(smsLogM);
	}

	private void createTableORIG_SMS_LOG(SMSLogDataM smsLogM) throws ApplicationException {
		PreparedStatement ps = null;
		Connection conn = null;
		try {
			//conn = Get Connection
			conn = getConnection();
			StringBuffer sql = new StringBuffer("");
			sql.append("INSERT INTO ORIG_SMS_LOG ");
			sql.append("( SMS_LOG_ID, APPLICATION_GROUP_ID, SEND_TO, SEND_NO, ");
			sql.append(" MESSAGE, SEND_DATE, SEND_BY, SEND_STATUS, ");
			sql.append(" CREATE_DATE, CREATE_BY ) ");

			sql.append(" VALUES(?,?,?,?,  ?,?,?,?,  ?,?)");
			String dSql = String.valueOf(sql);
			logger.debug("Sql=" + dSql);
			ps = conn.prepareStatement(dSql);
			int cnt = 1;
			ps.setString(cnt++, smsLogM.getSmsLogId());
			ps.setString(cnt++, smsLogM.getApplicationGroupId());
			ps.setString(cnt++, smsLogM.getSendTo());
			ps.setString(cnt++, smsLogM.getSendNo());
			
			ps.setString(cnt++, smsLogM.getMessage());
			ps.setDate(cnt++, smsLogM.getSendDate());
			ps.setString(cnt++, smsLogM.getSendBy());
			ps.setString(cnt++, smsLogM.getSendStatus());
			
			ps.setTimestamp(cnt++, ApplicationDate.getTimestamp());
			ps.setString(cnt++, smsLogM.getCreateBy());
			
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
	public void deleteOrigSMSLogM(String applicationGroupId, String smsLogId)
			throws ApplicationException {
		deleteTableORIG_SMS_LOG(applicationGroupId, smsLogId);
	}

	private void deleteTableORIG_SMS_LOG(String applicationGroupId, String smsLogId) 
			throws ApplicationException {
		PreparedStatement ps = null;
		Connection conn = null;
		try {
			//conn = Get Connection
			conn = getConnection();
			StringBuffer sql = new StringBuffer("");
			sql.append(" DELETE ORIG_SMS_LOG ");
			sql.append(" WHERE APPLICATION_GROUP_ID = ? ");
			if(smsLogId != null && !smsLogId.isEmpty()) {
				sql.append(" AND SMS_LOG_ID = ? ");
			}
			String dSql = String.valueOf(sql);
			logger.debug("Sql=" + dSql);
			ps = conn.prepareStatement(dSql);
			ps.setString(1, applicationGroupId);
			if(smsLogId != null && !smsLogId.isEmpty()) {
				ps.setString(2, smsLogId);
			}
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
	public ArrayList<SMSLogDataM> loadOrigSMSLogM(String applicationGroupId)
			throws ApplicationException {
		ArrayList<SMSLogDataM> result = selectTableORIG_SMS_LOG(applicationGroupId);
		return result;
	}

	private ArrayList<SMSLogDataM> selectTableORIG_SMS_LOG(
			String applicationGroupId) throws ApplicationException {
		PreparedStatement ps = null;
		ResultSet rs = null;
		Connection conn = null;
		ArrayList<SMSLogDataM> smsLogList = new ArrayList<SMSLogDataM>();
		try {
			//conn = Get Connection
			conn = getConnection();
			StringBuffer sql = new StringBuffer("");
			sql.append(" SELECT SMS_LOG_ID, APPLICATION_GROUP_ID, SEND_TO, ");
			sql.append(" SEND_NO, MESSAGE, SEND_DATE, SEND_BY, SEND_STATUS, ");
			sql.append(" CREATE_DATE, CREATE_BY ");
			sql.append(" FROM ORIG_SMS_LOG WHERE APPLICATION_GROUP_ID = ? ");
			String dSql = String.valueOf(sql);
			logger.debug("Sql=" + dSql);
			ps = conn.prepareStatement(dSql);
			rs = null;
			ps.setString(1, applicationGroupId);

			rs = ps.executeQuery();

			while(rs.next()) {
				SMSLogDataM smsLogM = new SMSLogDataM();
				smsLogM.setSmsLogId(rs.getString("SMS_LOG_ID"));
				smsLogM.setApplicationGroupId(rs.getString("APPLICATION_GROUP_ID"));
				smsLogM.setSendTo(rs.getString("SEND_TO"));
				
				smsLogM.setSendNo(rs.getString("SEND_NO"));
				smsLogM.setMessage(rs.getString("MESSAGE"));
				smsLogM.setSendDate(rs.getDate("SEND_DATE"));
				smsLogM.setSendBy(rs.getString("SEND_BY"));
				smsLogM.setSendStatus(rs.getString("SEND_STATUS"));
				
				smsLogM.setCreateBy(rs.getString("CREATE_BY"));
				smsLogM.setCreateDate(rs.getTimestamp("CREATE_DATE"));
				smsLogList.add(smsLogM);
			}

			return smsLogList;
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

}
