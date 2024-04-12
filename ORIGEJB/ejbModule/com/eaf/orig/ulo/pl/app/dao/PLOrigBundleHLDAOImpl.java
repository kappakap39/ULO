package com.eaf.orig.ulo.pl.app.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import org.apache.log4j.Logger;
import com.eaf.orig.shared.dao.OrigObjectDAO;
import com.eaf.orig.ulo.pl.app.dao.exception.PLOrigApplicationException;
import com.eaf.orig.ulo.pl.model.app.PLBundleHLDataM;

public class PLOrigBundleHLDAOImpl extends OrigObjectDAO implements PLOrigBundleHLDAO {
	
	private Logger log = Logger.getLogger(PLOrigBundleHLDAOImpl.class);

	@Override
	public void saveUpdateBundleHL(PLBundleHLDataM bundleHLM, String appRecId) throws PLOrigApplicationException{
		try{
			if(bundleHLM!=null){
				int returnRow = this.updateTable_Oirg_Bundle_HL(bundleHLM, appRecId);
				if(returnRow == 0){
					this.insertTable_Oirg_Bundle_HL(bundleHLM, appRecId);
				}	
			}
		}catch(Exception e){
			log.fatal(e.getLocalizedMessage());
			throw new PLOrigApplicationException(e.getMessage());
		}
	}
	
	private int updateTable_Oirg_Bundle_HL(PLBundleHLDataM bundleHLM, String appRecId) throws PLOrigApplicationException {
		
		PreparedStatement ps = null;
		Connection conn = null;
		try {
			
			conn = getConnection();
			StringBuilder sql = new StringBuilder("");
			sql.append("UPDATE ORIG_BUNDLE_HL SET APPLICATION_RECORD_ID=?, APPROVE_CREDIT_LINE=?, UPDATE_DATE=SYSDATE, UPDATE_BY=? ");
			sql.append("WHERE APPLICATION_RECORD_ID = ?");
			
			
			String dSql = String.valueOf(sql);
			log.debug("Sql=" + dSql);
			ps = conn.prepareStatement(dSql);
			
			int index = 1;
			ps.setString(index++, appRecId);
			ps.setBigDecimal(index++, bundleHLM.getApproveCreditLine());
			ps.setString(index++, bundleHLM.getUpdateBy());
			ps.setString(index++, appRecId);
			
			return ps.executeUpdate();
			
			
		} catch (Exception e){
			log.fatal(e.getLocalizedMessage());
			throw new PLOrigApplicationException(e.getMessage());
		}
	}
	
	private void insertTable_Oirg_Bundle_HL(PLBundleHLDataM bundleHLM, String appRecId) throws PLOrigApplicationException {
		
		PreparedStatement ps = null;
		Connection conn = null;
		try {
			
			conn = getConnection();
			StringBuilder sql = new StringBuilder("");
			sql.append("INSERT INTO ORIG_BUNDLE_HL ");
			sql.append("( APPLICATION_RECORD_ID, APPROVE_CREDIT_LINE, CREATE_DATE, CREATE_BY, UPDATE_DATE ");
			sql.append(", UPDATE_BY ) ");
			sql.append("VALUES (?,?,SYSDATE,?,SYSDATE  ,?) ");
			
			String dSql = String.valueOf(sql);
			log.debug("Sql=" + dSql);
			ps = conn.prepareStatement(dSql);
			
			int index = 1;
			
			ps.setString(index++, appRecId);
			ps.setBigDecimal(index++, bundleHLM.getApproveCreditLine());
			ps.setString(index++, bundleHLM.getCreateBy());
			
			ps.setString(index++, bundleHLM.getUpdateBy());
			
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
	public PLBundleHLDataM loadBundleHL(String appRecId) throws PLOrigApplicationException{
		PreparedStatement ps = null;
		ResultSet rs = null;
		PLBundleHLDataM bundleHLM = null;
		Connection conn = null;
		try{
			conn = getConnection();
			StringBuilder sql = new StringBuilder("");
			sql.append("SELECT APPLICATION_RECORD_ID, APPROVE_CREDIT_LINE, CREATE_DATE, CREATE_BY, UPDATE_DATE ");
			sql.append(", UPDATE_BY ");
			sql.append("FROM ORIG_BUNDLE_HL WHERE APPLICATION_RECORD_ID = ?");
			
			String dSql = String.valueOf(sql);
			
			log.debug("Sql=" + dSql);			
			ps = conn.prepareStatement(dSql);
			ps.setString(1, appRecId);
			rs = ps.executeQuery();			
			
			if(rs.next()){	
				bundleHLM = new PLBundleHLDataM();		
				int index = 1;				
				bundleHLM.setAppRecId(rs.getString(index++));
				bundleHLM.setApproveCreditLine(rs.getBigDecimal(index++));
				bundleHLM.setCreateDate(rs.getTimestamp(index++));
				bundleHLM.setCreateBy(rs.getString(index++));
				bundleHLM.setUpdateDate(rs.getTimestamp(index++));				
				bundleHLM.setUpdateBy(rs.getString(index++));				
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
		return bundleHLM;
	}
	
}
