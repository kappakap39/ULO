package com.eaf.orig.ulo.pl.app.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.Vector;
import org.apache.log4j.Logger;
import com.eaf.orig.shared.constant.EJBConstant;
import com.eaf.orig.shared.dao.OrigObjectDAO;
import com.eaf.orig.ulo.pl.app.dao.exception.PLOrigApplicationException;
import com.eaf.orig.ulo.pl.model.app.PLReasonLogDataM;
import com.eaf.orig.ulo.pl.service.PLORIGEJBService;

public class PLOrigReasonLogDAOImpl extends OrigObjectDAO implements PLOrigReasonLogDAO {
	
	private static Logger log = Logger.getLogger(PLOrigReasonLogDAOImpl.class);

	@Override
	public void saveUpdateOrigReasonLog(Vector<PLReasonLogDataM> reasonLogM, String appLogID)throws PLOrigApplicationException{
		try{
			if(reasonLogM != null && reasonLogM.size() > 0){
				for(int i=0 ; i<reasonLogM.size() ; i++){
					PLReasonLogDataM reasonLogDataM = (PLReasonLogDataM)reasonLogM.get(i);
					int reasonID =Integer.parseInt(PLORIGEJBService.getGeneratorManager().generateUniqueIDByName(EJBConstant.REASON_LOG_ID));
					reasonLogDataM.setReasonLogId(reasonID);
					this.insertTableOrig_Reason_Log(reasonLogDataM, reasonID, appLogID);				
				}
			}			
		}catch(Exception e){
			log.fatal(e.getLocalizedMessage());
			throw new PLOrigApplicationException(e.getMessage());
		}
	}
	
	private void updateTableOrig_Reason_Log(PLReasonLogDataM reasonLogDataM, int reasonId) throws PLOrigApplicationException{
		PreparedStatement ps = null;
		Connection conn = null;
		try {

			conn = getConnection();
			StringBuilder sql = new StringBuilder("");

			sql.append("UPDATE ORIG_REASON_LOG ");
			sql.append("SET  APP_LOG_ID=?, REASON_CODE=?, REASON_TYPE=?, ROLE=?, REMARK=? ");
			sql.append(" WHERE APP_LOG_ID=? AND REASON_LOG_ID=?");
			String dSql = String.valueOf(sql);
//			log.debug("Sql=" + dSql);
			ps = conn.prepareStatement(dSql);
			
			ps.setString(1, reasonLogDataM.getApplicationReordId());
			ps.setString(2, reasonLogDataM.getReasonCode());
			ps.setString(3, reasonLogDataM.getReasonType());
			ps.setString(4, reasonLogDataM.getRole());
			
			ps.setString(5, reasonLogDataM.getRemark());
			ps.setString(6, reasonLogDataM.getApplicationReordId());
			ps.setInt(7, reasonId);

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
	
	private void insertTableOrig_Reason_Log(PLReasonLogDataM reasonLogDataM, int reasonId, String appLogId)throws PLOrigApplicationException {
		
		PreparedStatement ps = null;
		Connection conn = null;
		try {
			conn = getConnection();
			StringBuilder sql = new StringBuilder("");
			sql.append("INSERT INTO ORIG_REASON_LOG ");
			sql.append("( REASON_LOG_ID, APP_LOG_ID, REASON_CODE, REASON_TYPE, CREATE_DATE");
			sql.append(", CREATE_BY, ROLE, REMARK ) ");
			sql.append(" VALUES(?,?,?,?,SYSDATE   ,?,?,? ) ");
			String dSql = String.valueOf(sql);
//			log.debug("Sql=" + dSql);
			ps = conn.prepareStatement(dSql);
			
			ps.setInt(1, reasonId);
			ps.setString(2, appLogId);
			ps.setString(3, reasonLogDataM.getReasonCode());
			ps.setString(4, reasonLogDataM.getReasonType());
			
			ps.setString(5, reasonLogDataM.getCreateBy());
			ps.setString(6, reasonLogDataM.getRole());
			ps.setString(7, reasonLogDataM.getRemark());
			
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
	
	private void deleteTableOrig_Reason_Log(String appLogId, Vector<Integer> rId)throws PLOrigApplicationException{
		PreparedStatement ps = null;
		Connection conn = null;
		try {
			conn = getConnection();
			StringBuilder sql = new StringBuilder("");
			sql.append("DELETE ORIG_REASON_LOG ");
			sql.append(" WHERE APP_LOG_ID = ? AND REASON_LOG_ID NOT IN ");
			sql.append("( ");
			if (rId != null && rId.size() > 0) {
				for (int i=0; i<rId.size(); i++){
					sql.append("?,");
				}
				sql.deleteCharAt((sql.length()-1));
				
			}else{
				sql.append(" ? ");
			}
			sql.append(" )");
			String dSql = String.valueOf(sql);
//			log.debug("Sql="+dSql);
			ps = conn.prepareStatement(dSql);
			ps.setString(1, appLogId);
			if (rId != null && rId.size() > 0) {
				for (int i=0; i<rId.size(); i++){
					ps.setInt((i+2), rId.get(i));
				}
			}else{
				ps.setString(2, null);
			}
			
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
	public Vector<PLReasonLogDataM> loadOrigReasonLog(String appLogId)throws PLOrigApplicationException {
		PreparedStatement ps = null;
		ResultSet rs = null;
		Connection conn = null;
		try {
			conn = getConnection();
			StringBuilder sql = new StringBuilder("");
			sql.append("SELECT REASON_LOG_ID, APP_LOG_ID, REASON_CODE, REASON_TYPE, CREATE_DATE ");
			sql.append(", CREATE_BY, ROLE, REMARK ");
			sql.append(" FROM ORIG_REASON_LOG WHERE APP_LOG_ID = ? ");
			String dSql = String.valueOf(sql);
//			log.debug("Sql=" + dSql);
			ps = conn.prepareStatement(dSql);
			rs = null;
			ps.setString(1, appLogId);
			rs = ps.executeQuery();
			
			Vector<PLReasonLogDataM> reasonLogVect = new Vector<PLReasonLogDataM>();
			
			while (rs.next()) {
				PLReasonLogDataM reasonLogDataM = new PLReasonLogDataM();
				
				reasonLogDataM.setReasonLogId(rs.getInt(1));
				reasonLogDataM.setApplicationReordId(rs.getString(2));
				reasonLogDataM.setReasonCode(rs.getString(3));
				reasonLogDataM.setReasonType(rs.getString(4));
				reasonLogDataM.setCreateDate(rs.getTimestamp(5));
				
				reasonLogDataM.setCreateBy(rs.getString(6));
				reasonLogDataM.setRole(rs.getString(7));
				reasonLogDataM.setRemark(rs.getString(8));
				
				reasonLogVect.add(reasonLogDataM);
			}
			
			return reasonLogVect;
			
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
	public Vector<String> loadDisplayReasonLog(String appLogID) throws PLOrigApplicationException {		
		PreparedStatement ps = null;
		ResultSet rs = null;
		Vector<String> reasonVect = new Vector<String>();
		Connection conn = null;
		try{
			conn = getConnection();
			StringBuilder SQL = new StringBuilder("");
				SQL.append(" SELECT DISTINCT ");
				SQL.append("     LBX.DISPLAY_NAME || DECODE(ORL.REMARK,null,null,'-' || ORL.REMARK) ");
				SQL.append(" FROM ");
				SQL.append("     LIST_BOX_MASTER LBX, ORIG_REASON_LOG ORL ");
				SQL.append(" WHERE ");
				SQL.append("     ORL.APP_LOG_ID = ? ");
				SQL.append(" AND LBX.FIELD_ID =ORL.REASON_TYPE ");
				SQL.append(" AND LBX.CHOICE_NO = ORL.REASON_CODE ");
							
			ps = conn.prepareStatement(SQL.toString());
			int index = 1;
			ps.setString(index++, appLogID);
//			ps.setString(index++, appLogID);
			
			rs = ps.executeQuery();
						
			while(rs.next()){
				reasonVect.add(rs.getString(1));
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
		return reasonVect;
	}

	@Override
	public String reasonFromRejectCancel(String appRecId) throws PLOrigApplicationException{		
		PreparedStatement ps = null;
		ResultSet rs = null;
		Connection conn = null;
		try {
			conn = getConnection();
			StringBuilder sql = new StringBuilder("");
			sql.append("SELECT ");
			sql.append("(SELECT lbm.DISPLAY_NAME FROM LIST_BOX_MASTER lbm WHERE lbm.FIELD_ID = rLog.REASON_TYPE and lbm.CHOICE_NO = rLog.REASON_CODE and lbm.ACTIVE_STATUS = 'A') reason ");
			sql.append("FROM ORIG_APPLICATION_LOG log, ORIG_REASON_LOG rLog ");
			sql.append("WHERE log.APP_LOG_ID = rLog.APP_LOG_ID ");
			sql.append("AND log.ACTION in ('Cancel', 'Cancel After Approve', 'Complete Reject', 'Confirm Reject', 'Reject', 'Reject Block', 'Reject skip DF') ");
			sql.append("AND log.APPLICATION_RECORD_ID = ?");
			
			String dSql = String.valueOf(sql);
//			log.debug("Sql=" + dSql);
			ps = conn.prepareStatement(dSql);
			rs = null;
			ps.setString(1, appRecId);
			rs = ps.executeQuery();
			
			StringBuilder strB = new StringBuilder("");
			while (rs.next()) {
				strB.append(rs.getString(1));
				strB.append("|");
			}
			strB.deleteCharAt(strB.length()-1);
//			log.debug("strB.toString() = "+strB.toString());
			return strB.toString();
			
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

}
