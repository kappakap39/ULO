package com.eaf.orig.ulo.pl.app.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

import org.apache.log4j.Logger;

import com.ava.bpm.model.response.WorkflowResponse;
import com.eaf.orig.shared.constant.EJBConstant;
import com.eaf.orig.shared.dao.OrigObjectDAO;
import com.eaf.orig.shared.ejb.ORIGGeneratorManager;
import com.eaf.orig.shared.service.ORIGEJBService;
import com.eaf.orig.ulo.pl.app.dao.exception.UpdateApplicationStatusDAOException;

public class UpdateApplicationStatusDAOImpl extends OrigObjectDAO implements UpdateApplicationStatusDAO{
	
	private static Logger log = Logger.getLogger(UpdateApplicationStatusDAOImpl.class);
	@Override
	public boolean updateAppStatus(WorkflowResponse response)throws UpdateApplicationStatusDAOException {
		// TODO Auto-generated method stub
		 boolean result=true;
	        log.info("##### updateAppStatus ##### " + response.getAppRecordID());
	        try{
	            
	        	updateStatus_ORIG_APPLICATION(response);
	            
	            int logSeq = getNextSeq(response.getAppRecordID());
	            
	            log.info("##### updateAppStatus ##### logSeq " + logSeq);
	            
	            create_ORIG_APPLICATION_LOG(response, logSeq);
	            
	        }catch(Exception e){
	            log.fatal("[UpdateApplicationStatusDAOImpl] updateAppStatus() ",e);
				throw new UpdateApplicationStatusDAOException(e.getMessage());
	        }
	        
	        return result;
	}
    private void updateStatus_ORIG_APPLICATION(WorkflowResponse response) throws UpdateApplicationStatusDAOException{
		PreparedStatement ps = null;
		Connection conn = null;
		try {
			conn = getConnection();
			StringBuffer sql = new StringBuffer("");
			sql.append("UPDATE ORIG_APPLICATION SET APPLICATION_STATUS=?, JOB_STATE=?, UPDATE_DATE=SYSDATE, UPDATE_BY=? ");
			sql.append("WHERE APPLICATION_RECORD_ID = ? ");
			
			String strSql = sql.toString();
			log.debug("updateStatus_ORIG_APPLICATION="+strSql);

			ps = conn.prepareStatement(strSql);

			ps.setString(1, response.getAppStatus());
			ps.setString(2, response.getToAtid());
			ps.setString(3, response.getUserName());
			ps.setString(4, response.getAppRecordID());
			ps.executeUpdate();

		} catch (Exception e) {
			log.error(">>> updateStatus_ORIG_APPLICATION has error",e);			
			throw new UpdateApplicationStatusDAOException(e.getMessage());
		} finally {
			try {
				closeConnection(conn, ps);
			} catch (Exception ex) {
				log.error(">>> closeConnection() has error",ex);
				throw new UpdateApplicationStatusDAOException(ex.getMessage());
			}
		}
	}
    
    public void create_ORIG_APPLICATION_LOG(WorkflowResponse response, int logSeq) throws UpdateApplicationStatusDAOException{
		PreparedStatement ps = null;
		Connection conn = null;
		try {
			conn = getConnection();

			StringBuffer sql = new StringBuffer("");
			sql.append("INSERT INTO ORIG_APPLICATION_LOG ");
																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																									
			sql.append("(APP_LOG_ID,APPLICATION_RECORD_ID,LOG_SEQ,ACTION,JOB_STATE,CREATE_BY,CREATE_DATE,UPDATE_BY,UPDATE_DATE,APPLICATION_STATUS,CLAIM_DATE) ");

			sql.append("VALUES (?,?,?,?,?,?,SYSDATE,?,SYSDATE,?,SYSDATE) ");
			String dSql = String.valueOf(sql);
			log.debug("create_ORIG_APPLICATION_LOG="+dSql);

			ps = conn.prepareStatement(dSql);
			
			ORIGGeneratorManager generatorManager = ORIGEJBService.getGeneratorManager();
			
			ps.setString(1, generatorManager.generateUniqueIDByName(EJBConstant.APP_LOG_ID));
			ps.setString(2, response.getAppRecordID());
			ps.setInt(3, logSeq);
			ps.setString(4, response.getAction());
			ps.setString(5, response.getToAtid());
			ps.setString(6, response.getUserName());
			ps.setString(7, response.getUserName());
			ps.setString(8, response.getAppStatus());
			
			ps.executeUpdate();

		} catch (Exception e) {
			log.error(">>> create_ORIG_APPLICATION_LOG has error",e);
			throw new UpdateApplicationStatusDAOException(e.getMessage());
		} finally {
			try {
				closeConnection(conn, ps);
			} catch (Exception e) {
				log.error(">>> closeConnection() has error",e);
				throw new UpdateApplicationStatusDAOException(e.getMessage());
			}
		}
	}
    
    public int getNextSeq(String applicationRecordId)throws UpdateApplicationStatusDAOException{
		try{
			return this.getMaxSeq(applicationRecordId)+1;
		}catch(Exception e){
			log.error(">>> getNextSeq has error",e);
			throw new UpdateApplicationStatusDAOException(e.toString());
		}
	}

	private int getMaxSeq(String applicationRecordId)throws UpdateApplicationStatusDAOException{
		ResultSet rs = null;
		PreparedStatement ps = null;
		Connection conn = null;
		try{
			conn = this.getConnection();
			String sql = "SELECT NVL(MAX(LOG_SEQ),0) FROM ORIG_APPLICATION_LOG WHERE APPLICATION_RECORD_ID = ?";
			
			log.debug("getMaxSeq="+sql);
			
			ps = conn.prepareStatement(sql);
			
			ps.setString(1, applicationRecordId);
			
			rs = ps.executeQuery();
			
			if(rs.next()) return rs.getInt(1);
			return 0;
			
		}catch(Exception e){
			log.error(">>> getMaxSeq has error",e);
			throw new UpdateApplicationStatusDAOException(e.toString());
		}finally{
			try {
				closeConnection(conn, rs, ps);
			} catch (Exception e) {
				log.error(">>> closeConnection() has error",e);
				throw new UpdateApplicationStatusDAOException(e.getMessage());
			}
		}
	}

}
