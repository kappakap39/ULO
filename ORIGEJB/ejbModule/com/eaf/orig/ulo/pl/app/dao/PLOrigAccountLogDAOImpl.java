package com.eaf.orig.ulo.pl.app.dao;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.Vector;
import oracle.jdbc.OracleTypes;
import org.apache.log4j.Logger;
import com.eaf.orig.shared.constant.EJBConstant;
import com.eaf.orig.shared.constant.OrigConstant;
import com.eaf.orig.shared.dao.OrigObjectDAO;
import com.eaf.orig.ulo.pl.app.dao.exception.PLOrigApplicationException;
//import com.eaf.orig.ulo.pl.app.utility.DataFormatUtility;
import com.eaf.orig.ulo.pl.model.app.PLAccountLogDataM;
import com.eaf.orig.ulo.pl.service.PLORIGEJBService;

public class PLOrigAccountLogDAOImpl extends OrigObjectDAO implements PLOrigAccountLogDAO {
	
	private static Logger log = Logger.getLogger(PLOrigAccountLogDAOImpl.class);

	@Override
	public void saveAccLog(PLAccountLogDataM accLogM) throws PLOrigApplicationException {

		PreparedStatement ps = null;
		Connection conn = null;
		try {
			//generate key
			accLogM.setAccLogId(PLORIGEJBService.getGeneratorManager().generateUniqueIDByName(EJBConstant.ACCOUNT_LOG));	
			
			conn = getConnection();
			StringBuilder sql = new StringBuilder("");
			sql.append("INSERT INTO AC_ACCOUNT_LOG ");
			sql.append("( ACCOUNT_LOG_ID, ACCOUNT_ID, ACTION, OLD_VALUE, NEW_VALUE ");
			sql.append(", CREATE_DATE, CREATE_BY ) ");
			sql.append(" VALUES(?,?,?,?,?   ,SYSDATE,? ) ");
			String dSql = String.valueOf(sql);
			log.debug("Sql=" + dSql);
			ps = conn.prepareStatement(dSql);
			int index = 1;
			
			ps.setString(index++, accLogM.getAccLogId());
			ps.setString(index++, accLogM.getAccId());
			ps.setString(index++, accLogM.getAction());
			ps.setString(index++, accLogM.getOldValue());
			ps.setString(index++, accLogM.getNewValue());
			
			ps.setString(index++, accLogM.getCreateBy());
			
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
	public Vector<PLAccountLogDataM> loadAccLog(String accId) throws PLOrigApplicationException {
		PreparedStatement ps = null;
		ResultSet rs = null;
		Connection conn = null;
		try {
			conn = getConnection();
			StringBuilder sql = new StringBuilder("");
			sql.append("SELECT ACCOUNT_LOG_ID, ACCOUNT_ID, ACTION, OLD_VALUE, NEW_VALUE ");
			sql.append(", CREATE_DATE, CREATE_BY ");
			sql.append("FROM AC_ACCOUNT_LOG ");
			sql.append("WHERE ACCOUNT_ID = ?");
			
			String dSql = String.valueOf(sql);
			log.debug("Sql=" + dSql);
			ps = conn.prepareStatement(dSql);
			ps.setString(1, accId);
			rs = ps.executeQuery();
			
			Vector<PLAccountLogDataM> accLogVect = new Vector<PLAccountLogDataM>();
			
			while (rs.next()) {
				
				PLAccountLogDataM accLogM = new PLAccountLogDataM();
				
				int index = 1;
				accLogM.setAccLogId(rs.getString(index++));
				accLogM.setAccId(rs.getString(index++));
				accLogM.setAction(rs.getString(index++));
				accLogM.setOldValue(rs.getString(index++));
				accLogM.setNewValue(rs.getString(index++));
				
				accLogM.setCreateDate(rs.getTimestamp(index++));
				accLogM.setCreateBy(rs.getString(index++));
				
				accLogVect.add(accLogM);
				
			}
			
			return accLogVect;
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
	public Vector<PLAccountLogDataM> loadAccLogByCard(String accId) throws PLOrigApplicationException {
		PreparedStatement ps = null;
		ResultSet rs = null;
		Connection conn = null;
		try {
			conn = getConnection();
			StringBuilder sql = new StringBuilder("");
			sql.append("SELECT ACCOUNT_LOG_ID, ACCOUNT_ID, ACTION, OLD_VALUE, NEW_VALUE ");
			sql.append(", CREATE_DATE, CREATE_BY ");
			sql.append("FROM AC_ACCOUNT_LOG ");
			sql.append("WHERE ACCOUNT_ID = ? AND ACTION = ? ORDER BY CREATE_DATE DESC");
			
			String dSql = String.valueOf(sql);
			log.debug("Sql=" + dSql);
			ps = conn.prepareStatement(dSql);
			ps.setString(1, accId);
			ps.setString(2, OrigConstant.cardMaintenance.RE_ISSUE_CARD_NO);
			rs = ps.executeQuery();
			
			Vector<PLAccountLogDataM> accLogVect = new Vector<PLAccountLogDataM>();
			
			while (rs.next()) {
				
				PLAccountLogDataM accLogM = new PLAccountLogDataM();
				
				int index = 1;
				accLogM.setAccLogId(rs.getString(index++));
				accLogM.setAccId(rs.getString(index++));
				accLogM.setAction(rs.getString(index++));
				accLogM.setOldValue(rs.getString(index++));
				accLogM.setNewValue(rs.getString(index++));
				
				accLogM.setCreateDate(rs.getTimestamp(index++));
				accLogM.setCreateBy(rs.getString(index++));
				
				accLogVect.add(accLogM);
				
			}
			
			return accLogVect;
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
	public Vector<PLAccountLogDataM> loadAccLogByCust(String accId) throws PLOrigApplicationException {
		PreparedStatement ps = null;
		ResultSet rs = null;
		Connection conn = null;
		try {
			conn = getConnection();
			StringBuilder sql = new StringBuilder("");
			sql.append("SELECT ACCOUNT_LOG_ID, ACCOUNT_ID, ACTION, OLD_VALUE, NEW_VALUE ");
			sql.append(", CREATE_DATE, CREATE_BY ");
			sql.append("FROM AC_ACCOUNT_LOG ");
			sql.append("WHERE ACCOUNT_ID = ? AND ACTION = ? ORDER BY CREATE_DATE DESC");
			
			String dSql = String.valueOf(sql);
			log.debug("Sql=" + dSql);
			ps = conn.prepareStatement(dSql);
			ps.setString(1, accId);
			ps.setString(2, OrigConstant.cardMaintenance.RE_ISSUE_CUST_NO);
			rs = ps.executeQuery();
			
			Vector<PLAccountLogDataM> accLogVect = new Vector<PLAccountLogDataM>();
			
			while (rs.next()) {
				
				PLAccountLogDataM accLogM = new PLAccountLogDataM();
				
				int index = 1;
				accLogM.setAccLogId(rs.getString(index++));
				accLogM.setAccId(rs.getString(index++));
				accLogM.setAction(rs.getString(index++));
				accLogM.setOldValue(rs.getString(index++));
				accLogM.setNewValue(rs.getString(index++));
				
				accLogM.setCreateDate(rs.getTimestamp(index++));
				accLogM.setCreateBy(rs.getString(index++));
				
				accLogVect.add(accLogM);
				
			}
			
			return accLogVect;
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
	public Vector<PLAccountLogDataM> loadAccLogSortAsc(String accId) throws PLOrigApplicationException {
		PreparedStatement ps = null;
		ResultSet rs = null;
		Vector<PLAccountLogDataM> accLogVect = new Vector<PLAccountLogDataM>();
		Connection conn = null;
		try{
			conn = getConnection();
			StringBuilder sql = new StringBuilder("");
			sql.append(" SELECT ACCOUNT_LOG_ID, ACCOUNT_ID, ACTION, OLD_VALUE, NEW_VALUE, CREATE_DATE, CREATE_BY ");
			sql.append(" FROM AC_ACCOUNT_LOG ");
			sql.append(" WHERE ACCOUNT_ID = ? ORDER BY CREATE_DATE ASC ");
			
			String dSql = String.valueOf(sql);
			log.debug("Sql=" + dSql);
			ps = conn.prepareStatement(dSql);
			ps.setString(1, accId);
			rs = ps.executeQuery();
						
			while(rs.next()){				
				PLAccountLogDataM accLogM = new PLAccountLogDataM();
				int index = 1;
				accLogM.setAccLogId(rs.getString(index++));
				accLogM.setAccId(rs.getString(index++));
				accLogM.setAction(rs.getString(index++));
				accLogM.setOldValue(rs.getString(index++));
				accLogM.setNewValue(rs.getString(index++));				
				accLogM.setCreateDate(rs.getTimestamp(index++));
				accLogM.setCreateBy(rs.getString(index++));				
				accLogVect.add(accLogM);				
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
		return accLogVect;
	}

	@Override
	public String[] loadLogFirstRow(String accId) throws PLOrigApplicationException {
		PreparedStatement ps = null;
		ResultSet rs = null;
		Connection conn = null;
		try {
			conn = getConnection();
			StringBuilder sql = new StringBuilder("");
			sql.append("SELECT AAC.ACCOUNT_CARD_ID , PKA_CRYPTO.DECRYPT_FIELD(AAC.CARD_NO) , ");
			sql.append("NVL( ( SELECT AL.OLD_VALUE FROM AC_ACCOUNT_LOG AL WHERE AL.ACCOUNT_ID = AC.ACCOUNT_ID AND AL.CREATE_DATE = ");
			sql.append("( SELECT MIN(SAL.CREATE_DATE) FROM AC_ACCOUNT_LOG SAL WHERE SAL.ACCOUNT_ID = AC.ACCOUNT_ID AND SAL.ACTION = ? ) ) , AC.CARDLINK_CUST_NO) CUST_NO, ");
			sql.append("AAC.CARD_TYPE , (SELECT CF.CARD_FACE_DESC FROM CARD_FACE CF WHERE CF.CARD_FACE_ID = AAC.CARD_FACE AND CF.ACTIVE_STATUS = 'A') CARD_FACE, ");
			sql.append("AAC.CARD_STATUS, AAC.EMBOSS_NAME, AAC.UPDATE_DATE CREATE_DATE, AAC.UPDATE_BY CREATE_BY ");
			sql.append("FROM AC_ACCOUNT_CARD AAC, AC_ACCOUNT AC ");
			sql.append("WHERE AAC.ACCOUNT_ID = ? AND AAC.ACCOUNT_ID = AC.ACCOUNT_ID ");
			sql.append("ORDER BY CREATE_DATE ASC");
			
			String dSql = String.valueOf(sql);
			log.debug("Sql=" + dSql);
			ps = conn.prepareStatement(dSql);
			ps.setString(1, OrigConstant.cardMaintenance.RE_ISSUE_CUST_NO);
			ps.setString(2, accId);
			rs = ps.executeQuery();
			
			if (rs.next()) {				
				String result[] = new String[9];				
				int index = 1, indexResult = 0;
				result[indexResult++] = rs.getString(index++);
				result[indexResult++] = rs.getString(index++);
				result[indexResult++] = rs.getString(index++);
				result[indexResult++] = rs.getString(index++);
				result[indexResult++] = rs.getString(index++);
				
				result[indexResult++] = rs.getString(index++);
				result[indexResult++] = rs.getString(index++);
//				result[indexResult++] = DataFormatUtility.TimestampEnToStringDateTh(rs.getTimestamp(index++), 6);
				result[indexResult++] = rs.getString(index++);
				
				return result;
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
	public String deCodeCardNo(String cardNo) throws PLOrigApplicationException {
		log.debug("cardNo = "+cardNo);
		CallableStatement callStmt = null;
		Connection conn = null;
		try{
			conn = getConnection();
			callStmt = conn.prepareCall("{? = call PKA_CRYPTO.DECRYPT_FIELD(?) }");			
			logger.debug("{? = call PKA_CRYPTO.DECRYPT_FIELD(?) }");
			callStmt.registerOutParameter(1,OracleTypes.VARCHAR);
			callStmt.setString(2,cardNo);	
			callStmt.executeQuery();
			
			return callStmt.getString(1);
		}catch(Exception e){
			logger.fatal("deCodeCardNo Error "+e.getLocalizedMessage());
			throw new PLOrigApplicationException(e.getMessage());
		}finally{
			try {
				closeConnection(conn,callStmt);
			} catch (Exception e) {
				throw new PLOrigApplicationException(e.getMessage());
			}			
		}
		
	}

}
