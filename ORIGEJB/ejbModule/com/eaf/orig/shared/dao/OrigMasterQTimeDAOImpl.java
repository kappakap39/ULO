/*
 * Created on Nov 28, 2007
 * Created by Prawit Limwattanachai
 * 
 * Copyright (c) 2007 Avalant Co.,Ltd.
 * 20 North Sathorn Road, 15-16th Floor Bubhajit Bldg., Silom, Bangrak, Bangkok 10500, Thailand
 * All rights reserved.
 *
 * This software is the confidential and prorietary infomation of
 * Avalant Co.,Ltd. ("Confidential Infomation"). You shall not
 * disclose such Confidential Infomation and shall use it only in
 * accordance with the terms of the license agreement you entered into
 * with Avalant Co.,Ltd.
 * 
 */
package com.eaf.orig.shared.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.Vector;

import org.apache.log4j.Logger;

import com.eaf.orig.master.shared.model.QTimeOutLoanTypeM;
import com.eaf.orig.master.shared.model.QueueTimeOutM;
import com.eaf.orig.shared.dao.exceptions.OrigApplicationMException;

/**
 * @author Administrator
 *
 * Type: OrigMasterQTimeDAOImpl
 */
public class OrigMasterQTimeDAOImpl extends OrigObjectDAO implements
		OrigMasterQTimeDAO {
	Logger log = Logger.getLogger(OrigMasterQTimeDAOImpl.class);
	
	/* (non-Javadoc)
	 * @see com.eaf.orig.shared.dao.OrigMasterQTimeDAO#deleteOrigMasterQTimeOut(java.lang.String[])
	 */
	public void deleteOrigMasterQTimeOut(String[] qTimeIDToDelete)
			throws OrigApplicationMException {
		
		if(qTimeIDToDelete!=null && qTimeIDToDelete.length>0){
			for(int i=0;i<qTimeIDToDelete.length;i++){
				deleteQTimeOutLoanType(qTimeIDToDelete[i]);
				deleteQTimeOut(qTimeIDToDelete[i]);
			}
		}
			
	}
	
	public void deleteQTimeOutLoanType(String qTimeID)
			throws OrigApplicationMException {
		PreparedStatement ps = null;
		Connection conn = null;
		try {
				//conn = Get Connection
				conn = getConnection();
				StringBuffer sql = new StringBuffer("");
				sql.append("DELETE QUEUE_TIME_OUT_DETAIL ");
				sql.append(" WHERE Q_TIME_OUT_ID = ?");
				String dSql = String.valueOf(sql);
				log.debug("Sql=" + dSql);
				ps = conn.prepareStatement(dSql);
				ps.setString(1, qTimeID);
				ps.executeUpdate(); 
		
		} catch (Exception e) {
			log.fatal(e.getStackTrace());
			throw new OrigApplicationMException(e.getMessage());
		} finally {
			try {
				closeConnection(conn, ps);
			} catch (Exception e) {
				log.fatal(e.getStackTrace());
				throw new OrigApplicationMException(e.getMessage());
			}
		}
}
	
	public void deleteQTimeOut(String qTimeID)
			throws OrigApplicationMException {
		PreparedStatement ps = null;
		Connection conn = null;
		try {
				//conn = Get Connection
				conn = getConnection();
				StringBuffer sql = new StringBuffer("");
				sql.append("DELETE QUEUE_TIME_OUT ");
				sql.append(" WHERE Q_TIME_OUT_ID = ?");
				String dSql = String.valueOf(sql);
				log.debug("Sql=" + dSql);
				ps = conn.prepareStatement(dSql);
				ps.setString(1, qTimeID);
				ps.executeUpdate(); 
		
		} catch (Exception e) {
			log.fatal(e.getStackTrace());
			throw new OrigApplicationMException(e.getMessage());
		} finally {
			try {
				closeConnection(conn, ps);
			} catch (Exception e) {
				log.fatal(e.getStackTrace());
				throw new OrigApplicationMException(e.getMessage());
			}
		}
}
	
	public double updateOrigMasterQTimeOutM(QueueTimeOutM qTimeOutM)
			throws OrigApplicationMException {
		double returnRows = -1;
		
		updateQTimeOut(qTimeOutM);
		
		Vector qTimeLoanTypeVect = qTimeOutM.getQTimeOutLoanTypeMVect();
		if(qTimeLoanTypeVect!=null && qTimeLoanTypeVect.size()>0){
			log.debug("qTimeLoanTypeVect="+qTimeLoanTypeVect.size());
			QTimeOutLoanTypeM qTimeOutLoanTypeM;
			for(int i=0;i<qTimeLoanTypeVect.size();i++){
				qTimeOutLoanTypeM = (QTimeOutLoanTypeM)qTimeLoanTypeVect.get(i);
				returnRows = updateQTimeOutLoanType(qTimeOutLoanTypeM);
				
				if(returnRows == 0){
					saveQTimeOutLoanType(qTimeOutLoanTypeM);
				}
			}
		}
		
		return 0;
	}
	
	public double updateQTimeOutLoanType(QTimeOutLoanTypeM qTimeOutLoanTypeM)
			throws OrigApplicationMException {
		double returnRows = 0;
		PreparedStatement ps = null;
		Connection conn = null;
		try {
			//conn = Get Connection
			conn = getConnection();
			StringBuffer sql = new StringBuffer("");
			sql.append("UPDATE QUEUE_TIME_OUT_DETAIL ");
			
			sql.append(" SET Q_TIME_OUT_ID=?, STATE=?, EXPIRY_DAY=?, REASON_CODE=?, REMINDER=?, UPDATE_BY=?, UPDATE_DATE=SYSDATE ");
			
			sql.append(" WHERE Q_TIME_OUT_ID=? AND STATE=?");
			
			String dSql = String.valueOf(sql);
			log.debug("Sql="+dSql);
			ps = conn.prepareStatement(dSql);
			ps.setString(1, qTimeOutLoanTypeM.getQTimeOutID());
			ps.setString(2, qTimeOutLoanTypeM.getLoanType());
			ps.setInt(3, qTimeOutLoanTypeM.getExpiry());
			ps.setString(4, qTimeOutLoanTypeM.getReasonCode());
			ps.setInt(5, qTimeOutLoanTypeM.getReminder());
			ps.setString(6, qTimeOutLoanTypeM.getUpdateBy());
			ps.setString(7, qTimeOutLoanTypeM.getQTimeOutID());
			ps.setString(8, qTimeOutLoanTypeM.getLoanType());
			
			returnRows = ps.executeUpdate();
		} catch (Exception e) {
			log.fatal("",e);
			throw new OrigApplicationMException(e.getMessage());
		} finally {
			try {
				closeConnection(conn, ps);
			} catch (Exception e) {
				log.fatal("",e);
			}
		}
		return returnRows;

}
	
	public double updateQTimeOut(QueueTimeOutM qTimeOutM)
			throws OrigApplicationMException {
		double returnRows = 0;
		PreparedStatement ps = null;
		Connection conn = null;
		try {
			//conn = Get Connection
			conn = getConnection();
			StringBuffer sql = new StringBuffer("");
			sql.append("UPDATE QUEUE_TIME_OUT ");
			
			sql.append(" SET Q_TIME_OUT_ID=?, Q_TIME_OUT_DESC=?, WORKING_TIME=?, UPDATE_BY=?, UPDATE_DATE=SYSDATE ");
			
			sql.append(" WHERE Q_TIME_OUT_ID=? ");
			
			String dSql = String.valueOf(sql);
			log.debug("Sql="+dSql);
			ps = conn.prepareStatement(dSql);
			ps.setString(1, qTimeOutM.getQTimeOutID());
			ps.setString(2, qTimeOutM.getQTimeOutDesc());
			ps.setString(3, qTimeOutM.getWorkingTime());
			ps.setString(4, qTimeOutM.getUpdateBy());
			ps.setString(5, qTimeOutM.getQTimeOutID());
			
			returnRows = ps.executeUpdate();
		} catch (Exception e) {
			log.fatal("",e);
			throw new OrigApplicationMException(e.getMessage());
		} finally {
			try {
				closeConnection(conn, ps);
			} catch (Exception e) {
				log.fatal("",e);
			}
		}
		return returnRows;

}
	
	public QueueTimeOutM selectOrigMasterQTimeM(String qTimeID)
			throws OrigApplicationMException {
		
		QueueTimeOutM qTimeOutM = selectQTimeOut(qTimeID);
		
		Vector qTimeLoanTypeVect = new Vector();
		qTimeLoanTypeVect = selectQTimeOutLoanType(qTimeID);
		log.debug("qTimeLoanTypeVect.size() = " + qTimeLoanTypeVect.size());
		
		qTimeOutM.setQTimeOutLoanTypeMVect(qTimeLoanTypeVect);
		
		return qTimeOutM;
	}
	
	public Vector selectQTimeOutLoanType(String qTimeID)
			throws OrigApplicationMException {
		PreparedStatement ps = null;
		ResultSet rs = null;
		Connection conn = null;
		try {
			//conn = Get Connection
			conn = getConnection();
			StringBuffer sql = new StringBuffer("");
			
			sql.append(" SELECT Q_TIME_OUT_ID, STATE, EXPIRY_DAY, REASON_CODE, REMINDER ");
			sql.append(" FROM QUEUE_TIME_OUT_DETAIL  WHERE Q_TIME_OUT_ID = ? ");
			
			String dSql = String.valueOf(sql);
			log.debug("Sql=" + dSql);
			ps = conn.prepareStatement(dSql);
			rs = null;
			ps.setString(1, qTimeID);
		
			rs = ps.executeQuery();
			QTimeOutLoanTypeM qTimeOutLoanTypeM = null;
			Vector qTimeLoanTypeVect = new Vector();
		
			while(rs.next()) {
				qTimeOutLoanTypeM = new QTimeOutLoanTypeM();
				qTimeOutLoanTypeM.setQTimeOutID(rs.getString(1));
				qTimeOutLoanTypeM.setLoanType(rs.getString(2));
				qTimeOutLoanTypeM.setExpiry(rs.getInt(3));
				qTimeOutLoanTypeM.setReasonCode(rs.getString(4));
				qTimeOutLoanTypeM.setReminder(rs.getInt(5));
				
				qTimeLoanTypeVect.add(qTimeOutLoanTypeM);
			}
			return qTimeLoanTypeVect;
		
		} catch (Exception e) {
			log.fatal("",e);
			throw new OrigApplicationMException(e.getMessage());
		} finally {
			try {
				closeConnection(conn, rs, ps);
			} catch (Exception e) {
				log.fatal("",e);
			}
		}
}
	
	public QueueTimeOutM selectQTimeOut(String qTimeID)
			throws OrigApplicationMException {
		PreparedStatement ps = null;
		ResultSet rs = null;
		Connection conn = null;
		try {
			//conn = Get Connection
			conn = getConnection();
			StringBuffer sql = new StringBuffer("");
			
			sql.append(" SELECT Q_TIME_OUT_ID, Q_TIME_OUT_DESC, WORKING_TIME ");
			sql.append(" FROM QUEUE_TIME_OUT  WHERE Q_TIME_OUT_ID = ? ");
			
			String dSql = String.valueOf(sql);
			log.debug("Sql=" + dSql);
			ps = conn.prepareStatement(dSql);
			rs = null;
			ps.setString(1, qTimeID);
		
			rs = ps.executeQuery();
			QueueTimeOutM qTimeOutM = null;
		
			if (rs.next()) {
				qTimeOutM = new QueueTimeOutM();
				qTimeOutM.setQTimeOutID(rs.getString(1));
				qTimeOutM.setQTimeOutDesc(rs.getString(2));
				qTimeOutM.setWorkingTime(rs.getString(3));
			}
			return qTimeOutM;
		
		} catch (Exception e) {
			log.fatal("",e);
			throw new OrigApplicationMException(e.getMessage());
		} finally {
			try {
				closeConnection(conn, rs, ps);
			} catch (Exception e) {
				log.fatal("",e);
			}
		}
}
	
	public void createModelOrigMasterQTimeOutM(QueueTimeOutM qTimeOutM)
			throws OrigApplicationMException {

		saveQTime(qTimeOutM);
		
		Vector qTimeLoanTypeVect = qTimeOutM.getQTimeOutLoanTypeMVect();
		if(qTimeLoanTypeVect!=null && qTimeLoanTypeVect.size()>0){
			QTimeOutLoanTypeM qTimeOutLoanTypeM;
			for(int i=0;i<qTimeLoanTypeVect.size();i++){
				qTimeOutLoanTypeM = (QTimeOutLoanTypeM)qTimeLoanTypeVect.get(i);
				saveQTimeOutLoanType(qTimeOutLoanTypeM);
			}
		}
		
	}
	
	public void saveQTimeOutLoanType(QTimeOutLoanTypeM qTimeLoanTypeM)
			throws OrigApplicationMException {
		Connection conn = null;
			PreparedStatement ps = null;
			try{
				//conn = Get Connection
				conn = getConnection();
				StringBuffer sql = new StringBuffer("");
				sql.append(" INSERT INTO QUEUE_TIME_OUT_DETAIL ");
				sql.append(" (Q_TIME_OUT_ID, STATE, REASON_CODE, EXPIRY_DAY, REMINDER, UPDATE_BY, UPDATE_DATE) ");
				sql.append(" VALUES (?,?,?,?,?,?,SYSDATE)");
				String dSql = String.valueOf( sql);
				log.debug("dSql="+dSql);
				ps = conn.prepareStatement(dSql);
				
				ps.setString(1, qTimeLoanTypeM.getQTimeOutID());
				ps.setString(2, qTimeLoanTypeM.getLoanType());
				ps.setString(3, qTimeLoanTypeM.getReasonCode());
				ps.setInt(4, qTimeLoanTypeM.getExpiry());
				ps.setInt(5, qTimeLoanTypeM.getReminder());
				ps.setString(6, qTimeLoanTypeM.getUpdateBy());
				
				ps.executeUpdate();
				ps.close();
			} catch (Exception e) {
				log.fatal("",e);
				throw new OrigApplicationMException(e.getMessage());
			} finally {
				try {
					closeConnection(conn, ps);
				} catch (Exception e) {
					log.fatal("",e);
				}
			}
	}	

	
	public void saveQTime(QueueTimeOutM qTimeOutM)
			throws OrigApplicationMException {
		Connection conn = null;
			PreparedStatement ps = null;
			try{
				//conn = Get Connection
				conn = getConnection();
				StringBuffer sql = new StringBuffer("");
				sql.append(" INSERT INTO QUEUE_TIME_OUT ");
				sql.append(" (Q_TIME_OUT_ID, Q_TIME_OUT_DESC, WORKING_TIME, UPDATE_BY, UPDATE_DATE) ");
				sql.append(" VALUES (?,?,?,?,SYSDATE)");
				String dSql = String.valueOf( sql);
				log.debug("dSql="+dSql);
				ps = conn.prepareStatement(dSql);
				
				ps.setString(1, qTimeOutM.getQTimeOutID());
				ps.setString(2, qTimeOutM.getQTimeOutDesc());
				ps.setString(3, qTimeOutM.getWorkingTime());
				ps.setString(4, qTimeOutM.getUpdateBy());
				
				ps.executeUpdate();
				ps.close();
			} catch (Exception e) {
				log.fatal("",e);
				throw new OrigApplicationMException(e.getMessage());
			} finally {
				try {
					closeConnection(conn, ps);
				} catch (Exception e) {
					log.fatal("",e);
				}
			}
	}	

	public boolean hvQTimeID(String QTimeID) throws OrigApplicationMException {
		PreparedStatement ps = null;
		ResultSet rs = null;
		Connection conn = null;
		try {
			//conn = Get Connection
			conn = getConnection();
			StringBuffer sql = new StringBuffer("");
			
			sql.append(" SELECT *");
			sql.append(" FROM QUEUE_TIME_OUT  WHERE Q_TIME_OUT_ID = ? ");
			
			String dSql = String.valueOf(sql);
			log.debug("Sql=" + dSql);
			ps = conn.prepareStatement(dSql);
			rs = null;
			ps.setString(1, QTimeID);

			rs = ps.executeQuery();
			if (rs.next()) {
				return true;
			}else{
				return false;
			}

		} catch (Exception e) {
			log.fatal("",e);
			throw new OrigApplicationMException(e.getMessage());
		} finally {
			try {
				closeConnection(conn, rs, ps);
			} catch (Exception e) {
				log.fatal("",e);
			}
		}
	}

}
