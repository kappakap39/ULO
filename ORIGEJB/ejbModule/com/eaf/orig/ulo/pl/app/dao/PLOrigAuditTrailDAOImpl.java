package com.eaf.orig.ulo.pl.app.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.Vector;
import org.apache.log4j.Logger;
import com.eaf.orig.shared.dao.OrigObjectDAO;
import com.eaf.orig.ulo.pl.app.dao.exception.PLOrigApplicationException;
import com.eaf.orig.ulo.pl.model.app.PLAuditTrailDataM;

public class PLOrigAuditTrailDAOImpl extends OrigObjectDAO implements PLOrigAuditTrailDAO{	
	private static Logger log = Logger.getLogger(PLOrigAuditTrailDAOImpl.class);
	
	@Override
	public void saveUpdateOrigAuditTrail(Vector<PLAuditTrailDataM> auditTrailVect, String appLogId)throws PLOrigApplicationException {
		try{			
			if(auditTrailVect!=null && auditTrailVect.size() > 0){	
				for(PLAuditTrailDataM auditTrail:auditTrailVect){
					this.insertTableOrig_Audit_Trail(auditTrail, appLogId);
				}
			}			
		}catch (Exception e){
			log.fatal(e.getLocalizedMessage());
			throw new PLOrigApplicationException(e.getMessage());
		}
	}
	
	
	private void insertTableOrig_Audit_Trail(PLAuditTrailDataM auditM, String appLogId) throws PLOrigApplicationException{		
		PreparedStatement ps = null;		
		Connection conn = null;
		try{			
			conn = getConnection();
			StringBuilder sql = new StringBuilder("");
			sql.append(" INSERT INTO ORIG_AUDIT_TRAIL ");
			sql.append(" (APP_LOG_ID,FIELD_NAME,OLD_VALUE,NEW_VALUE,CREATE_DATE,CREATE_BY,ROLE) ");
			sql.append(" VALUES(?,?,?,?,SYSDATE,?,?) ");
			String dSql = String.valueOf(sql);
			int index = 1;
			ps = conn.prepareStatement(dSql);
			
			ps.setString(index++, appLogId);
			ps.setString(index++, auditM.getFieldName());
			ps.setString(index++, auditM.getOldValue());
			ps.setString(index++, auditM.getNewValue());			
			ps.setString(index++, auditM.getCreateBy());
			ps.setString(index++, auditM.getRole());
			
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
	public Vector<PLAuditTrailDataM> loadOrigAuditTrail(String appLogId) throws PLOrigApplicationException {
		PreparedStatement ps = null;
		ResultSet rs = null;
		Connection conn = null;
		Vector<PLAuditTrailDataM> auditVect = new Vector<PLAuditTrailDataM>();
		try{
			conn = getConnection();
			
			StringBuilder sql = new StringBuilder("");
			sql.append(" SELECT FIELD_NAME,OLD_VALUE,NEW_VALUE,CREATE_DATE,CREATE_BY,ROLE ");
			sql.append(" FROM ORIG_AUDIT_TRAIL WHERE APP_LOG_ID = ? ");
			
			String dSql = String.valueOf(sql);
//			log.debug("Sql=" + dSql);
			ps = conn.prepareStatement(dSql);
			rs = null;
			ps.setString(1, appLogId);

			rs = ps.executeQuery();
			
			
			while(rs.next()){
				PLAuditTrailDataM auditM = new PLAuditTrailDataM();				
				auditM.setFieldName(rs.getString("FIELD_NAME"));
				auditM.setOldValue(rs.getString("OLD_VALUE"));
				auditM.setNewValue(rs.getString("NEW_VALUE"));
				auditM.setCreateDate(rs.getTimestamp("CREATE_DATE"));				
				auditM.setCreateBy(rs.getString("CREATE_BY"));
				auditM.setRole(rs.getString("ROLE"));
				auditVect.add(auditM);
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
		return auditVect;
	}
	
	@Override
	public Vector<PLAuditTrailDataM> loadOrigAuditTrailByRoleAppRecId(String appRecID) throws PLOrigApplicationException{
		PreparedStatement ps = null;
		ResultSet rs = null;		
		Connection conn = null;
		Vector<PLAuditTrailDataM> auditVect = new Vector<PLAuditTrailDataM>();		
		try{
			conn = getConnection();
			
			StringBuilder SQL = new StringBuilder("");			
			SQL.append(" SELECT ");
			SQL.append("     T.FIELD_NAME, ");
			SQL.append("     T.OLD_VALUE, ");
			SQL.append("     T.NEW_VALUE, ");
			SQL.append("     T.CREATE_DATE, ");
			SQL.append("     T.CREATE_BY, ");
			SQL.append("     T.ROLE ");
			SQL.append(" FROM ");
			SQL.append("     ORIG_APPLICATION_LOG L, ");
			SQL.append("     ORIG_AUDIT_TRAIL T ");
			SQL.append(" WHERE ");
			SQL.append("     L.APP_LOG_ID = T.APP_LOG_ID ");
			SQL.append(" AND L.APPLICATION_RECORD_ID = ? ");
			SQL.append(" ORDER BY ");
			SQL.append("     T.FIELD_NAME ASC, ");
			SQL.append("     T.CREATE_DATE DESC ");
			
			String dSql = String.valueOf(SQL);
			
//			log.debug("Sql=" + dSql);
			ps = conn.prepareStatement(dSql);
			
			int index = 1;
			ps.setString(index++, appRecID);
			rs = ps.executeQuery();
			
			PLAuditTrailDataM auditM = null;
			while(rs.next()){
				auditM = new PLAuditTrailDataM();				
				auditM.setFieldName(rs.getString("FIELD_NAME"));
				auditM.setOldValue(rs.getString("OLD_VALUE"));
				auditM.setNewValue(rs.getString("NEW_VALUE"));
				auditM.setCreateDate(rs.getTimestamp("CREATE_DATE"));
				auditM.setCreateBy(rs.getString("CREATE_BY"));
				auditM.setRole(rs.getString("ROLE"));				
				auditVect.add(auditM);
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
		return auditVect;
	}

}
