/*
 * Created on 12 เม.ย. 2550
 *
 *  
 * Window - Preferences - Java - Code Style - Code Templates
 */
package com.eaf.orig.scheduler.dao.Scheduler;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.Vector;

import org.apache.log4j.Logger;

import com.eaf.orig.scheduler.model.ExpireAppM;
import com.eaf.orig.shared.dao.OrigObjectDAO;

/**
 * @author Wichaya
 *
 * 
 * Window - Preferences - Java - Code Style - Code Templates
 */
public class SchedulerDAOImpl extends OrigObjectDAO implements SchedulerDAO {
	static Logger log = Logger.getLogger(SchedulerDAOImpl.class);
	public Connection conn = null;
	
	public String loadWorkingTime(String queueTimeOutID) throws Exception{
		PreparedStatement ps = null;
		ResultSet rs = null;
		String workingTime = "";
		try{
			conn = getConnection();
			StringBuffer sql = new StringBuffer("");
			sql.append("select WORKING_TIME from QUEUE_TIME_OUT ");
			sql.append("where Q_TIME_OUT_ID = ? ");
			
			log.debug("loadWorkingTime="+sql.toString());
			
			ps = conn.prepareStatement(sql.toString());
			ps.setString(1,queueTimeOutID);
			
			rs = ps.executeQuery();
			
			while (rs.next()) {
				workingTime = rs.getString("WORKING_TIME");
			}
			
		}catch(Exception e){
			log.error(">>> loadWorkingTime has error",e);
			throw new Exception(e.getMessage());
		}finally{
			try {
				closeConnection(conn, rs, ps);
			} catch (Exception e) {
				log.error(">>> closeConnection() has error",e);
				throw new Exception(e.getMessage());
			}
		}
		
		return workingTime;
	}
	
	public Vector loadExpireApplication() throws Exception{
		PreparedStatement ps = null;
		ResultSet rs = null;
		Vector vExpApp  = new Vector();
		try{
			conn = getConnection();
			StringBuffer sql = new StringBuffer("");
			sql.append("SELECT a.application_record_id, a.application_status, a.job_state ");
			sql.append("FROM ORIG_APPLICATION a, QUEUE_TIME_OUT_DETAIL qt ");
			sql.append("WHERE a.job_state = qt.state ");
			sql.append("AND a.update_date <= SYSDATE - qt.expiry_day ");
			
			log.debug("loadExpireApplication="+sql.toString());
			
			ps = conn.prepareStatement(sql.toString());

			rs = ps.executeQuery();
			
			while (rs.next()) {
				ExpireAppM expireAppM = new ExpireAppM();
				expireAppM.setAppRecID(rs.getString("APPLICATION_RECORD_ID"));
				expireAppM.setAppStatus(rs.getString("APPLICATION_STATUS"));
				expireAppM.setJobState(rs.getString("JOB_STATE"));
				vExpApp.add(expireAppM);
			}
			
		}catch(Exception e){
			log.error(">>> loadExpireApplication has error",e);
			throw new Exception(e.getMessage());
		}finally{
			try {
				closeConnection(conn, rs, ps);
			} catch (Exception e) {
				log.error(">>> closeConnection() has error",e);
				throw new Exception(e.getMessage());
			}
		}
		
		return vExpApp;
	}
	
	public int deleteOldTask(String taskName) throws Exception{
		PreparedStatement ps = null;
		int returnRows = 0;
		try {
			conn = getConnection();
			StringBuffer sql = new StringBuffer("delete ORIG_SCHED_TASK where name = ? ");

			String strSql = sql.toString();
			
			log.debug("deleteOldTask="+strSql);

			ps = conn.prepareStatement(strSql);

			ps.setString(1, taskName);

			returnRows = ps.executeUpdate();

		}catch(Exception e){
			log.error(">>> deleteOldTask has error",e);
			throw new Exception(e.getMessage());
		}finally{
			try {
				closeConnection(conn, ps);
			} catch (Exception e) {
				log.error(">>> closeConnection() has error",e);
				throw new Exception(e.getMessage());
			}
		}
		return 0;
	}
	
//	public Calendar loadExpiryDate(String org, String AppRecID, String appDecision, String queue) throws Exception{
//		PreparedStatement ps = null;
//		ResultSet rs = null;
//		Calendar expireDate = Calendar.getInstance();
//		expireDate.set(2007,4,12,11,23);
//		try{
//			conn = getConnection();
//			StringBuffer sql = new StringBuffer("");
//			sql.append("select qb.EXPIRY_DAY, q.WORKING_TIME, qb.REASON_CODE, sysdate+qb.EXPIRY_DAY EXP_DATE from queue_timeout q, queue_timeout_bus_class qb ");
//			sql.append("where q.Q_TIME_OUT_ID = qb.Q_TIME_OUT_ID ");
//			sql.append("and qb.ORG_ID = ? ");
//			
//			log.debug("loadExpiryDate="+sql.toString());
//			
//			ps = conn.prepareStatement(sql.toString());
//			ps.setString(1,org);
//			
//			rs = ps.executeQuery();
//			String expiryDay = "";
//			String workingTime = "";
//			String reasonCode = "";
//			int expHour = 0;
//			int expMinute = 0;
//			Date expDate = null;
//			
//			while (rs.next()) {
//				expiryDay = rs.getString("EXPIRY_DAY");
//				workingTime = rs.getString("WORKING_TIME");
//				reasonCode = rs.getString("REASON_CODE");
//				expDate = rs.getDate("EXP_DATE");
//			}
//			if(workingTime!=null){
//				expHour = Integer.parseInt(workingTime.substring(0,2));
//				expMinute = Integer.parseInt(workingTime.substring(2));
//			}
//			
//			if(("DE".equals(queue)&&NaosTWBusinessProcessConstant.ApplicationDecision.PENDING.equals(appDecision))
//				||(("DV".equals(queue)||"PV".equals(queue)||"FU".equals(queue))&&NaosTWBusinessProcessConstant.ApplicationDecision.DRAFT.equals(appDecision))){
//				log.debug(">>> loadExpiryDate >> expDate.getYear() : "+expDate.getYear()+1900);
//				log.debug(">>> loadExpiryDate >> expDate.getMonth() : "+expDate.getMonth());
//				log.debug(">>> loadExpiryDate >> expDate.getDate() : "+expDate.getDate());
//				expireDate.set(expDate.getYear()+1900,expDate.getMonth(),expDate.getDate(),expHour,expMinute,00);
//			}else{
//				expireDate.set(expDate.getYear()+2900,expDate.getMonth(),expDate.getDate(),expHour,expMinute,00);
//			}
//			log.debug(">>> loadExpiryDate >> expireDate : "+expireDate);
//			
//		}catch(Exception e){
//			log.error(">>> loadExpireApplication has error",e);
//			throw new Exception(e.getMessage());
//		}finally{
//			try {
//				closeConnection(conn, rs, ps);
//			} catch (Exception e) {
//				log.error(">>> closeConnection() has error",e);
//				throw new Exception(e.getMessage());
//			}
//		}
//		
//		return expireDate;
//	}
	
	
}
