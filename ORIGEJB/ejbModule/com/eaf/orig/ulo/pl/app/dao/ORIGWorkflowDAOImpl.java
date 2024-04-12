package com.eaf.orig.ulo.pl.app.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.Vector;

import com.eaf.orig.shared.constant.OrigConstant;
import com.eaf.orig.shared.dao.OrigObjectDAO;
import com.eaf.orig.shared.service.OrigServiceLocator;
import com.eaf.orig.ulo.pl.app.dao.exception.PLOrigApplicationException;
import com.eaf.orig.ulo.pl.constant.WorkflowConstant;
import com.eaf.orig.ulo.pl.model.app.ORIGWorkflowDataM;

public class ORIGWorkflowDAOImpl extends OrigObjectDAO implements ORIGWorkflowDAO{
	@Override
	public Vector<ORIGWorkflowDataM> getQueueByOwner(String owner, String tdID) throws PLOrigApplicationException {
		Vector<ORIGWorkflowDataM> queueVect = new Vector<ORIGWorkflowDataM>();
		PreparedStatement ps = null;
		ResultSet rs = null;
		ORIGWorkflowDataM wfM = null;
		Connection conn = null;
		try{
			conn = getConnection(OrigServiceLocator.WORKFLOW_DB);			
			StringBuilder sql = new StringBuilder("");
				
			sql.append(" SELECT WWQ.JOB_ID JOB_ID, WWQ.ATID ATID, WWQ.OWNER OWNER, WWQ.APP_STATUS APP_STATUS");
			sql.append(" ,WWQ.ACTIVITY_STATE ACTIVITY_STATE, WI.APP_INFO APP_INFO, WWQ.PRIORITY PRIORITY ");
			sql.append(" ,WJM.APPLICATION_RECORD_ID APPLICATION_RECORD_ID ,WI.FIELD_01 FIELD_01");
			sql.append(" FROM WF_WORK_QUEUE WWQ, WF_INSTANT WI, WF_TODO_LIST WTL ,WF_JOBID_MAPPING WJM");
			sql.append(" WHERE WTL.ATID = WWQ.ATID AND WWQ.JOB_ID = WI.JOB_ID AND WWQ.JOB_ID = WJM.JOB_ID ");
			sql.append(" AND WTL.TDID = ? AND WJM.JOB_STATUS = ? AND WWQ.ACTIVITY_STATE = ? ");
			sql.append(" AND UPPER(WWQ.OWNER) = UPPER(?) ");
			
			ps = conn.prepareStatement(sql.toString());
			
			int index = 1;
			ps.setString(index++, tdID);
			ps.setString(index++ ,WorkflowConstant.JOB_STATUS_ACTIVE);
			ps.setString(index++ ,WorkflowConstant.ACTIVITY_STATE_READY);
			ps.setString(index++, owner);
			rs = ps.executeQuery();
			
			while(rs.next()){
				wfM = new ORIGWorkflowDataM();
				wfM.setJobID(rs.getString("JOB_ID"));
				wfM.setJobState(rs.getString("ATID"));
				wfM.setOwner(rs.getString("OWNER"));
				wfM.setAppStatus(rs.getString("APP_STATUS"));
				wfM.setState(rs.getString("ACTIVITY_STATE"));
				wfM.setAppInfo(rs.getString("APP_INFO"));
				wfM.setPriority(rs.getInt("PRIORITY"));
				wfM.setBusClassID(rs.getString("FIELD_01"));
				wfM.setAppRecID(rs.getString("APPLICATION_RECORD_ID"));
				queueVect.add(wfM);
			}						
		}catch(Exception e){
			logger.fatal("Exception ",e);
			throw new PLOrigApplicationException(e.getMessage());
		}finally{
			try{
				closeConnection(conn, rs, ps);
			}catch(Exception e){
				logger.fatal("Exception ",e);
			}
		}
		return queueVect;
	}

	@Override
	public String isDataFinalCompleteApprove(String appRecordId) throws PLOrigApplicationException {
		PreparedStatement ps = null;
		ResultSet rs = null;
		String dfCompleteFlag = "N";
		Connection conn = null;
		try{
			conn = getConnection(OrigServiceLocator.WORKFLOW_DB);			
			StringBuilder sql = new StringBuilder("");
				
			sql.append(" select case when count(1) > 0 then 'Y' else 'N' end df_complete_flag");
			sql.append(" from wf_jobid_mapping jm inner join wf_instant_history ih on ih.job_id = jm.job_id");
			sql.append(" where jm.application_record_id = ? and ih.atid = ?");
			
			logger.debug("isDataFinalCompleteApprove sql :" + sql.toString());
			ps = conn.prepareStatement(sql.toString());
			
			int index = 1;
			ps.setString(index++, appRecordId);
			ps.setString(index++ ,OrigConstant.JobState.COMPLETE_CIS);
			
			rs = ps.executeQuery();
			
			while(rs.next()){
				dfCompleteFlag = rs.getString("df_complete_flag");
			}						
		}catch(Exception e){
			logger.fatal("Exception ",e);
			throw new PLOrigApplicationException(e.getMessage());
		}finally{
			try{
				closeConnection(conn, rs, ps);
			}catch(Exception e){
				logger.fatal("Exception ",e);
			}
		}
		return dfCompleteFlag;
	}
	
}
