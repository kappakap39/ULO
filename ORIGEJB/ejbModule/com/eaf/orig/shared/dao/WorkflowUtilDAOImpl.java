/*
 * Created on Dec 20, 2007
 *
 *  
 * Window - Preferences - Java - Code Style - Code Templates
 */
package com.eaf.orig.shared.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.Vector;

import oracle.jdbc.OracleResultSet;

import org.apache.log4j.Logger;

import com.eaf.orig.shared.dao.exceptions.OrigApplicationMException;
import com.eaf.orig.shared.service.OrigServiceLocator;
import com.eaf.orig.wf.shared.model.WorkListM;

/**
 * @author Joe
 *
 * 
 * Window - Preferences - Java - Code Style - Code Templates
 */
public class WorkflowUtilDAOImpl extends OrigObjectDAO implements WorkflowUtilDAO {
	Logger log = Logger.getLogger(WorkflowUtilDAOImpl.class);
	public Vector loadCriticalProcess() throws OrigApplicationMException{
		Vector vAIID = new Vector();
		PreparedStatement ps = null;
		ResultSet rs = null;
		Connection conn = null;
		try {
			//conn = Get Connection
			conn = getConnection(OrigServiceLocator.BPC_DB);
			StringBuffer sql = new StringBuffer("");
			
			sql.append("SELECT activity.aiid, process_instance.NAME ");
			sql.append("FROM activity, process_instance ");
			sql.append("WHERE activity.state = '13' AND process_instance.piid = activity.piid ");
			
			String dSql = String.valueOf(sql);
			log.debug("Sql=" + dSql);
			ps = conn.prepareStatement(dSql);
		
			rs = ps.executeQuery();
		
			while(rs.next()) {
				WorkListM worklistM = new WorkListM();
				//log.debug(">>> AIID = "+(rs).getRAW(1));
				worklistM.setAiid(((OracleResultSet)rs).getRAW(1).stringValue());
				worklistM.setProcessInstanceName(rs.getString(2));
				vAIID.add(worklistM);
			}
			return vAIID;
		
		} catch (Exception e) {
			log.fatal("##### loadCriticalProcess Error #####",e);
			throw new OrigApplicationMException(e.getMessage());
		} finally {
			try {
				closeConnection(conn, rs, ps);
			} catch (Exception e) {
				log.fatal("##### loadCriticalProcess Error #####",e);
			}
		}
	}
	
	public double updateForSearchCriticalProcess()throws OrigApplicationMException {
		double returnRows = 0;
		PreparedStatement ps = null;
		Connection conn = null;
		try {
			//conn = Get Connection
			conn = getConnection(OrigServiceLocator.BPC_DB);
			StringBuffer sql = new StringBuffer("");
			sql.append("UPDATE work_item_t ");
			sql.append("SET owner_id = NULL, everybody = 1 ");
			sql.append("WHERE object_id in (SELECT object_id ");
			sql.append("FROM activity_instance_b_t ");
			sql.append("WHERE A_TKIID = object_id AND state = 13) ");
			
			String dSql = String.valueOf(sql);
			log.debug("Sql="+dSql);
			ps = conn.prepareStatement(dSql);
			
			returnRows = ps.executeUpdate();
		} catch (Exception e) {
			log.fatal("##### updateQTimeOutLoanType #####",e);
			throw new OrigApplicationMException(e.getMessage());
		} finally {
			try {
				closeConnection(conn, ps);
			} catch (Exception e) {
				log.fatal("##### updateQTimeOutLoanType #####",e);
			}
		}
		return returnRows;
	}
}
