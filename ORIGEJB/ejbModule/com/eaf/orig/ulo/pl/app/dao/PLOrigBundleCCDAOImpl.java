package com.eaf.orig.ulo.pl.app.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import org.apache.log4j.Logger;
import com.eaf.orig.shared.dao.OrigObjectDAO;
import com.eaf.orig.ulo.pl.app.dao.exception.PLOrigApplicationException;
import com.eaf.orig.ulo.pl.model.app.PLBundleCCDataM;

public class PLOrigBundleCCDAOImpl extends OrigObjectDAO implements PLOrigBundleCCDAO {

	private Logger log = Logger.getLogger(PLOrigBundleCCDAOImpl.class);
	
	@Override
	public void saveUpdateBundleCC(PLBundleCCDataM bundleCCM, String appRecId) throws PLOrigApplicationException{
		try{
			if(bundleCCM!=null){
				int returnRow = this.updateTable_Oirg_Bundle_CC(bundleCCM, appRecId);
				if(returnRow == 0){
					this.insertTable_Oirg_Bundle_CC(bundleCCM, appRecId);
				}	
			}
		} catch (Exception e){
			log.fatal(e.getLocalizedMessage());
			throw new PLOrigApplicationException(e.getMessage());
		}
	}
	
	private int updateTable_Oirg_Bundle_CC(PLBundleCCDataM bundleCCM, String appRecId) throws PLOrigApplicationException {
		
		PreparedStatement ps = null;
		Connection conn = null;
		try {
			
			conn = getConnection();
			StringBuilder sql = new StringBuilder("");
			sql.append("UPDATE ORIG_BUNDLE_CC SET CREDIT_CARD_RESULT=?, CREDIT_CARD_APP_SCORE=?, UPDATE_DATE=SYSDATE, UPDATE_BY=? ");
			sql.append("WHERE APPLICATION_RECORD_ID = ?");
			
			String dSql = String.valueOf(sql);
			log.debug("Sql=" + dSql);
			ps = conn.prepareStatement(dSql);
			
			int index = 1;
			ps.setString(index++, bundleCCM.getCreditCardResult());
			ps.setString(index++, bundleCCM.getCreditCardAppScore());
			ps.setString(index++, bundleCCM.getUpdateBy());
			ps.setString(index++, appRecId);
			
			
			return ps.executeUpdate();
			
			
		} catch (Exception e){
			log.fatal(e.getLocalizedMessage());
			throw new PLOrigApplicationException(e.getMessage());
		}
	}
	
	private void insertTable_Oirg_Bundle_CC(PLBundleCCDataM bundleCCM, String appRecId) throws PLOrigApplicationException {
		
		PreparedStatement ps = null;
		Connection conn = null;
		try {
			
			conn = getConnection();
			StringBuilder sql = new StringBuilder("");
			sql.append("INSERT INTO ORIG_BUNDLE_CC ");
			sql.append("( CREDIT_CARD_RESULT, CREDIT_CARD_APP_SCORE, CREATE_DATE, CREATE_BY, UPDATE_DATE ");
			sql.append(", UPDATE_BY, APPLICATION_RECORD_ID ) ");
			sql.append("VALUES (?,?,SYSDATE,?,SYSDATE  ,?,? ) ");
			
			String dSql = String.valueOf(sql);
			log.debug("Sql=" + dSql);
			ps = conn.prepareStatement(dSql);
			
			int index = 1;
			
			ps.setString(index++, bundleCCM.getCreditCardResult());
			ps.setString(index++, bundleCCM.getCreditCardAppScore());
			ps.setString(index++, bundleCCM.getCreateBy());
			
			ps.setString(index++, bundleCCM.getUpdateBy());
			ps.setString(index++, appRecId);
			
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
	public PLBundleCCDataM loadBundleCC(String appRecId) throws PLOrigApplicationException {

		PreparedStatement ps = null;
		ResultSet rs = null;		

		PLBundleCCDataM bundleCCM = new PLBundleCCDataM();
		Connection conn = null;
		try {
			conn = getConnection();
			StringBuilder sql = new StringBuilder("");
			sql.append("SELECT CREDIT_CARD_RESULT, CREDIT_CARD_APP_SCORE, CREATE_DATE, CREATE_BY, UPDATE_DATE ");
			sql.append(", UPDATE_BY, APPLICATION_RECORD_ID ");
			sql.append("FROM ORIG_BUNDLE_CC WHERE APPLICATION_RECORD_ID = ?");
			
			String dSql = String.valueOf(sql);
			log.debug("Sql=" + dSql);
			
			ps = conn.prepareStatement(dSql);
			ps.setString(1, appRecId);
			rs = ps.executeQuery();
						
			if(rs.next()){				
				int index = 1;				
				bundleCCM.setCreditCardResult(rs.getString(index++));
				bundleCCM.setCreditCardAppScore(rs.getString(index++));
				bundleCCM.setCreateDate(rs.getTimestamp(index++));
				bundleCCM.setCreateBy(rs.getString(index++));
				bundleCCM.setUpdateDate(rs.getTimestamp(index++));
				
				bundleCCM.setUpdateBy(rs.getString(index++));
				bundleCCM.setAppRecId(rs.getString(index++));				
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
		return bundleCCM;
	}

}
