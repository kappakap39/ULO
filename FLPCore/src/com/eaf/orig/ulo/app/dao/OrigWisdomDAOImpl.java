package com.eaf.orig.ulo.app.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

import com.eaf.core.ulo.common.date.ApplicationDate;
import com.eaf.orig.shared.dao.OrigObjectDAO;
import com.eaf.orig.ulo.app.dao.exception.ApplicationException;
import com.eaf.orig.ulo.model.app.WisdomDataM;

public class OrigWisdomDAOImpl extends OrigObjectDAO implements OrigWisdomDAO {
	public OrigWisdomDAOImpl(String userId){
		this.userId = userId;
	}
	public OrigWisdomDAOImpl() {
		// TODO Auto-generated constructor stub
	}
	private String userId = "";
	@Override
	public void createOrigWisdomM(WisdomDataM wisdomM)
			throws ApplicationException {
		wisdomM.setCreateBy(userId);
		wisdomM.setUpdateBy(userId);
		createTableORIG_WISDOM_INFO(wisdomM);
	}

	private void createTableORIG_WISDOM_INFO(WisdomDataM wisdomM) throws ApplicationException {
		PreparedStatement ps = null;
		Connection conn = null;
		try {
			//conn = Get Connection
			conn = getConnection();
			StringBuffer sql = new StringBuffer("");
			sql.append("INSERT INTO ORIG_WISDOM_INFO ");
			sql.append("( CARD_ID, PRIORITY_PASS_FLAG, INSURANCE_TYPE, PREMIUM_AMT, ");
			sql.append(" TRANSFER_FROM, QUOTA_OF, PRIORITY_PASS_NO, REFERRAL_WISDOM_NO, ");
			sql.append(" CREATE_DATE, CREATE_BY, UPDATE_DATE, UPDATE_BY, ");
			sql.append(" REQ_PRIORITY_PASS_SUP,PRIORITY_PASS_MEMO,PRIORITY_PASS_MAIN,NO_PRIORITY_PASS_SUP,NO_PRIORITY_PASS) ");

			sql.append(" VALUES(?,?,?,?,  ?,?,?,?,  ?,?,?,?,  ?,?,?,?,?)");
			String dSql = String.valueOf(sql);
			logger.debug("Sql=" + dSql);
			ps = conn.prepareStatement(dSql);
			int cnt = 1;
			ps.setString(cnt++, wisdomM.getCardId());
			ps.setString(cnt++, wisdomM.getPriorityPassFlag());
			ps.setString(cnt++, wisdomM.getInsuranceType());			
			ps.setBigDecimal(cnt++, wisdomM.getPremiumAmt());
			
			ps.setString(cnt++, wisdomM.getTransferFrom());
			ps.setString(cnt++, wisdomM.getQuotaOf());
			ps.setString(cnt++, wisdomM.getPriorityPassNo());
			ps.setString(cnt++, wisdomM.getReferralWisdomNo());
			
			ps.setTimestamp(cnt++, ApplicationDate.getTimestamp());
			ps.setString(cnt++, wisdomM.getCreateBy());
			ps.setTimestamp(cnt++, ApplicationDate.getTimestamp());
			ps.setString(cnt++, wisdomM.getUpdateBy());
			
			ps.setString(cnt++, wisdomM.getReqPriorityPassSup());
			ps.setString(cnt++, wisdomM.getPriorityPassMemo());
			ps.setString(cnt++, wisdomM.getPriorityPassMain());
			ps.setBigDecimal(cnt++, wisdomM.getNoPriorityPassSup());
			ps.setBigDecimal(cnt++, wisdomM.getNoPriorityPass());
			
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
	public void deleteOrigWisdomM(String cardID)
			throws ApplicationException {
		deleteTableORIG_WISDOM_INFO(cardID);
	}

	private void deleteTableORIG_WISDOM_INFO(String cardID) throws ApplicationException {
		PreparedStatement ps = null;
		Connection conn = null;
		try {
			//conn = Get Connection
			conn = getConnection();
			StringBuffer sql = new StringBuffer("");
			sql.append("DELETE ORIG_WISDOM_INFO ");
			sql.append(" WHERE CARD_ID = ?");
			String dSql = String.valueOf(sql);
			logger.debug("Sql=" + dSql);
			ps = conn.prepareStatement(dSql);
			ps.setString(1, cardID);
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
	public WisdomDataM loadOrigWisdomM(String cardID)
			throws ApplicationException {
		Connection conn = null;
		try{
			conn = getConnection();
			return selectTableORIG_WISDOM_INFO(cardID,conn);
		}catch (Exception e) {
			logger.fatal(e.getLocalizedMessage());
			throw new ApplicationException(e.getMessage());
		} finally {
			try {
				closeConnection(conn);
			} catch (Exception e) {
				logger.fatal(e.getLocalizedMessage());
				throw new ApplicationException(e.getMessage());
			}
		}
	}
	@Override
	public WisdomDataM loadOrigWisdomM(String cardID, Connection conn)
			throws ApplicationException {
		return selectTableORIG_WISDOM_INFO(cardID, conn);
	}
	private WisdomDataM selectTableORIG_WISDOM_INFO(String cardID,Connection conn) throws ApplicationException {
		PreparedStatement ps = null;
		ResultSet rs = null;
		WisdomDataM wisdomM = null;
		try {
			StringBuffer sql = new StringBuffer("");
			sql.append("SELECT CARD_ID, PRIORITY_PASS_FLAG, INSURANCE_TYPE, PREMIUM_AMT, ");
			sql.append(" TRANSFER_FROM, QUOTA_OF, PRIORITY_PASS_NO, REFERRAL_WISDOM_NO, ");
			sql.append(" CREATE_DATE, CREATE_BY, UPDATE_DATE, UPDATE_BY, ");
			sql.append(" REQ_PRIORITY_PASS_SUP ,PRIORITY_PASS_MEMO,PRIORITY_PASS_MAIN,NO_PRIORITY_PASS_SUP,NO_PRIORITY_PASS ");
			sql.append(" FROM ORIG_WISDOM_INFO WHERE CARD_ID = ? ");
			String dSql = String.valueOf(sql);
			logger.debug("Sql=" + dSql);
			ps = conn.prepareStatement(dSql);
			rs = null;
			ps.setString(1, cardID);

			rs = ps.executeQuery();

			if(rs.next()) {
				wisdomM = new WisdomDataM();
				wisdomM.setCardId(rs.getString("CARD_ID"));
				wisdomM.setPriorityPassFlag(rs.getString("PRIORITY_PASS_FLAG"));
				wisdomM.setInsuranceType(rs.getString("INSURANCE_TYPE"));				
				wisdomM.setPremiumAmt(rs.getBigDecimal("PREMIUM_AMT"));
				
				wisdomM.setTransferFrom(rs.getString("TRANSFER_FROM"));
				wisdomM.setQuotaOf(rs.getString("QUOTA_OF"));
				wisdomM.setPriorityPassNo(rs.getString("PRIORITY_PASS_NO"));
				wisdomM.setReferralWisdomNo(rs.getString("REFERRAL_WISDOM_NO"));
				
				wisdomM.setCreateBy(rs.getString("CREATE_BY"));
				wisdomM.setCreateDate(rs.getTimestamp("CREATE_DATE"));
				wisdomM.setUpdateBy(rs.getString("UPDATE_BY"));
				wisdomM.setUpdateDate(rs.getTimestamp("UPDATE_DATE"));
				
				wisdomM.setReqPriorityPassSup(rs.getString("REQ_PRIORITY_PASS_SUP"));
				wisdomM.setPriorityPassMemo(rs.getString("PRIORITY_PASS_MEMO"));
				wisdomM.setPriorityPassMain(rs.getString("PRIORITY_PASS_MAIN"));
				wisdomM.setNoPriorityPassSup(rs.getBigDecimal("NO_PRIORITY_PASS_SUP"));
				wisdomM.setNoPriorityPass(rs.getBigDecimal("NO_PRIORITY_PASS"));
			}

			return wisdomM;
		} catch (Exception e) {
			logger.fatal(e.getLocalizedMessage());
			throw new ApplicationException(e.getMessage());
		} finally {
			try {
				closeConnection(rs, ps);
			} catch (Exception e) {
				logger.fatal(e.getLocalizedMessage());
			}
		}
	}

	@Override
	public void saveUpdateOrigWisdomM(WisdomDataM wisdomM)
			throws ApplicationException {
		int returnRows = 0;
		wisdomM.setUpdateBy(userId);
		returnRows = updateTableORIG_WISDOM_INFO(wisdomM);
		if (returnRows == 0) {
			wisdomM.setCreateBy(userId);
			createTableORIG_WISDOM_INFO(wisdomM);
		}
	}

	private int updateTableORIG_WISDOM_INFO(WisdomDataM wisdomM) throws ApplicationException {
		int returnRows = 0;
		PreparedStatement ps = null;
		Connection conn = null;
		try {
			//conn = Get Connection
			conn = getConnection();
			StringBuffer sql = new StringBuffer("");
			sql.append("UPDATE ORIG_WISDOM_INFO ");
			sql.append(" SET PRIORITY_PASS_FLAG = ?, INSURANCE_TYPE = ?, PREMIUM_AMT = ?, ");
			sql.append(" TRANSFER_FROM = ?, QUOTA_OF = ?, PRIORITY_PASS_NO = ?, ");
			sql.append(" REFERRAL_WISDOM_NO = ?, UPDATE_DATE = ?, UPDATE_BY = ? ,");	
			sql.append(" REQ_PRIORITY_PASS_SUP=?, PRIORITY_PASS_MAIN=?, NO_PRIORITY_PASS_SUP=?, ");
			sql.append(" PRIORITY_PASS_MEMO = ? ,NO_PRIORITY_PASS = ?");
			
			sql.append(" WHERE CARD_ID = ? ");
			String dSql = String.valueOf(sql);
			logger.debug("Sql=" + dSql);
			ps = conn.prepareStatement(dSql);
			int cnt = 1;
			ps.setString(cnt++, wisdomM.getPriorityPassFlag());
			ps.setString(cnt++, wisdomM.getInsuranceType());			
			ps.setBigDecimal(cnt++, wisdomM.getPremiumAmt());
			
			ps.setString(cnt++, wisdomM.getTransferFrom());
			ps.setString(cnt++, wisdomM.getQuotaOf());
			ps.setString(cnt++, wisdomM.getPriorityPassNo());
			
			ps.setString(cnt++, wisdomM.getReferralWisdomNo());
			ps.setTimestamp(cnt++, ApplicationDate.getTimestamp());
			ps.setString(cnt++, wisdomM.getUpdateBy());
			
			ps.setString(cnt++, wisdomM.getReqPriorityPassSup());
			ps.setString(cnt++, wisdomM.getPriorityPassMain());
			ps.setBigDecimal(cnt++, wisdomM.getNoPriorityPassSup());
			
			ps.setString(cnt++, wisdomM.getPriorityPassMemo());
			ps.setBigDecimal(cnt++, wisdomM.getNoPriorityPass());
			// WHERE CLAUSE
			ps.setString(cnt++, wisdomM.getCardId());
			
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

}
