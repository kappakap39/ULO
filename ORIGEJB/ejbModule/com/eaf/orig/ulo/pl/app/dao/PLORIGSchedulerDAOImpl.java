package com.eaf.orig.ulo.pl.app.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Vector;
import com.eaf.orig.shared.constant.OrigConstant;
import com.eaf.orig.shared.dao.OrigObjectDAO;
import com.eaf.orig.shared.model.InfLogDataM;
import com.eaf.orig.shared.model.ServiceEmailSMSSchedulerQDataM;
import com.eaf.orig.ulo.pl.app.dao.exception.PLOrigApplicationException;
import com.eaf.orig.ulo.pl.constant.WorkflowConstant;

public class PLORIGSchedulerDAOImpl extends OrigObjectDAO implements PLORIGSchedulerDAO {
	public ArrayList<String> loadAutoRejectConfirmReject(int sla) throws PLOrigApplicationException{
		ArrayList<String> appArray = new ArrayList<String>();
		PreparedStatement ps = null;
		ResultSet rs = null;Connection conn = null;
		try {
			//conn = Get Connection
			conn = getConnection();
			StringBuilder sql = new StringBuilder("");
			
			sql.append("SELECT app_record_id FROM ( ");
				sql.append("SELECT JOB.app_record_id, JOB.desup_queue_date QUEUE_DATE, JOB.job_status, JOB.activity_type FROM WF_JOB_DETAIL JOB, WF_TODO_LIST WTL ");
				sql.append("WHERE WTL.ATID = JOB.job_state AND WTL.TDID = ? ");
			sql.append("UNION ");
				sql.append("SELECT JOB.app_record_id, JOB.dcsup_queue_date QUEUE_DATE, JOB.job_status, JOB.activity_type FROM WF_JOB_DETAIL JOB, WF_TODO_LIST WTL ");
				sql.append("WHERE WTL.ATID = JOB.job_state AND WTL.TDID = ? ");
			sql.append("UNION ");
				sql.append("SELECT JOB.app_record_id, JOB.vcsup_queue_date QUEUE_DATE, JOB.job_status, JOB.activity_type FROM WF_JOB_DETAIL JOB, WF_TODO_LIST WTL ");
				sql.append("WHERE WTL.ATID = JOB.job_state AND WTL.TDID = ? ");
			sql.append(") ");
			sql.append("WHERE job_status = ? and activity_type = ? and pka_app_util.count_working_day(QUEUE_DATE,sysdate) >= ?");
			
			String dSql = String.valueOf(sql);
			
//			logger.debug("@@@@@ loadAutoRejectConfirmReject Sql=" + dSql);
			
			ps = conn.prepareStatement(dSql);
			rs = null;
			ps.setString(1, WorkflowConstant.TODO_LIST_ID.DE_SUP_CONFIRM_REJECT);
			ps.setString(2, WorkflowConstant.TODO_LIST_ID.DC_SUP_CONFIRM_REJECT);
			ps.setString(3, WorkflowConstant.TODO_LIST_ID.VC_SUP_CONFIRM_REJECT);
			ps.setString(4, WorkflowConstant.JobStatus.ACTIVE);
			ps.setString(5, WorkflowConstant.ActivityType.INDIVIDUAL_QUEUE);
			ps.setInt(6, sla);

			rs = ps.executeQuery();

			while(rs.next()) {
				appArray.add(rs.getString("app_record_id"));
			}
			
			return appArray;

		} catch (Exception e) {
			logger.fatal("",e);
			throw new PLOrigApplicationException(e.getMessage());
		} finally {
			try {
				closeConnection(conn, rs, ps);
			} catch (Exception e) {
				logger.fatal("",e);
			}
		}
	}
	
	public ArrayList<String> loadAutoRejectVC(int sla) throws PLOrigApplicationException{
		ArrayList<String> appArray = new ArrayList<String>();
		PreparedStatement ps = null;
		ResultSet rs = null;Connection conn = null;
		try {
			//conn = Get Connection
			conn = getConnection();
			StringBuilder sql = new StringBuilder("");
			
			sql.append("SELECT JOB.app_record_id  FROM WF_JOB_DETAIL JOB, WF_TODO_LIST WTL ");
			sql.append("WHERE WTL.ATID = JOB.job_state AND WTL.TDID = ? ");
				sql.append("and JOB.job_status = ? ");
				sql.append("and JOB.activity_type = ? ");
				sql.append("and PKA_APP_UTIL.count_date_ver_HR_CUS(JOB.app_record_id, JOB.vc_queue_date) >= ?");
				
			String dSql = String.valueOf(sql);
			
//			logger.debug("@@@@@ loadAutoRejectVC Sql=" + dSql + "|sla:" + sla);
			
			ps = conn.prepareStatement(dSql);
			rs = null;
			ps.setString(1, WorkflowConstant.TODO_LIST_ID.VC_INBOX);
			ps.setString(2, WorkflowConstant.JobStatus.ACTIVE);
			ps.setString(3, WorkflowConstant.ActivityType.INDIVIDUAL_QUEUE);
			ps.setInt(4, sla);

			rs = ps.executeQuery();

			while(rs.next()) {
				appArray.add(rs.getString("app_record_id"));
			}
			
			return appArray;

		} catch (Exception e) {
			logger.fatal("",e);
			throw new PLOrigApplicationException(e.getMessage());
		} finally {
			try {
				closeConnection(conn, rs, ps);
			} catch (Exception e) {
				logger.fatal("",e);
			}
		}
	}
	
	public ArrayList<String> loadAutoRejectFollowUpIQ() throws PLOrigApplicationException{
		ArrayList<String> appArray = new ArrayList<String>();
		PreparedStatement ps = null;
		ResultSet rs = null;Connection conn = null;
		try {
			//conn = Get Connection
			conn = getConnection();
			StringBuilder sql = new StringBuilder("");
			
			sql.append("select JOB.app_record_id, pka_fu_util.is_follow_up_auto_reject(JOB.app_record_id) reject_flag ");
			sql.append("from WF_JOB_DETAIL JOB, WF_TODO_LIST WTL ");
			sql.append("where WTL.ATID = JOB.job_state AND WTL.TDID = ? and JOB.job_status = ? and JOB.activity_type = ? ");
				
			String dSql = String.valueOf(sql);
			
//			logger.debug("@@@@@ loadAutoRejectFollowUpIQ Sql=" + dSql);
			
			ps = conn.prepareStatement(dSql);
			rs = null;
			ps.setString(1, WorkflowConstant.TODO_LIST_ID.FU_INBOX);
			ps.setString(2, WorkflowConstant.JobStatus.ACTIVE);
			ps.setString(3, WorkflowConstant.ActivityType.INDIVIDUAL_QUEUE);

			rs = ps.executeQuery();

			while(rs.next()) {
				if(OrigConstant.FLAG_Y.equals(rs.getString("reject_flag"))){
					appArray.add(rs.getString("app_record_id"));
				}
			}
			
			return appArray;

		} catch (Exception e) {
			logger.fatal("",e);
			throw new PLOrigApplicationException(e.getMessage());
		} finally {
			try {
				closeConnection(conn, rs, ps);
			} catch (Exception e) {
				logger.fatal("",e);
			}
		}
	}
	
	public ArrayList<String> loadAutoSendEmailFollowUp() throws PLOrigApplicationException{
		ArrayList<String> appArray = new ArrayList<String>();
		PreparedStatement ps = null;
		ResultSet rs = null;
		Connection conn = null;
		try {
			//conn = Get Connection
			conn = getConnection();
			StringBuilder sql = new StringBuilder("");
			
			sql.append("SELECT JOB.app_record_id  FROM WF_JOB_DETAIL JOB, WF_TODO_LIST WTL ");
			sql.append("WHERE WTL.ATID = JOB.job_state AND WTL.TDID = ? ");
				sql.append("and JOB.job_status = ? ");
				sql.append("and JOB.activity_type = ? ");
				sql.append("and pka_fu_util.is_follow_up_auto_email(JOB.app_record_id, JOB.job_id,JOB.bus_class) = ?");
			String dSql = String.valueOf(sql);
			
//			logger.debug("@@@@@ loadAutoSendEmailFollowUp Sql=" + dSql);
			
			ps = conn.prepareStatement(dSql);
			rs = null;
			ps.setString(1, WorkflowConstant.TODO_LIST_ID.FU_INBOX);
			ps.setString(2, WorkflowConstant.JobStatus.ACTIVE);
			ps.setString(3, WorkflowConstant.ActivityType.INDIVIDUAL_QUEUE);
			ps.setString(4, OrigConstant.FLAG_Y);

			rs = ps.executeQuery();

			while(rs.next()) {
				appArray.add(rs.getString("app_record_id"));
			}
			
			return appArray;

		} catch (Exception e) {
			logger.fatal("",e);
			throw new PLOrigApplicationException(e.getMessage());
		} finally {
			try {
				closeConnection(conn, rs, ps);
			} catch (Exception e) {
				logger.fatal("",e);
			}
		}
	}

	@Override
	public Vector<ServiceEmailSMSSchedulerQDataM> loadEmailSMSSchedulerQueue( String serviceType) throws PLOrigApplicationException {
		PreparedStatement ps = null;
		ResultSet rs = null;
		Connection conn = null;
		Vector<ServiceEmailSMSSchedulerQDataM> schedQueueVT = new Vector<ServiceEmailSMSSchedulerQDataM>();
		try {
			//conn = Get Connection
			conn = getConnection();
			StringBuilder sql = new StringBuilder("");
			
			sql.append("select * ");
			sql.append("from SERVICE_EMAIL_SMS_SCHEDULE_Q sched ");
			sql.append("where sched.status = ? ");
				  sql.append("and (sched.schedule_date < sysdate or sched.schedule_date is null) ");
				  sql.append("and sched.service_type = ?");
			String dSql = String.valueOf(sql);
			
//			logger.debug("@@@@@ loadEmailSMSSchedulerQueue Sql=" + dSql);
			
			ps = conn.prepareStatement(dSql);
			rs = null;
			ps.setString(1, OrigConstant.FLAG_N);
			ps.setString(2, serviceType);

			rs = ps.executeQuery();

			while(rs.next()) {
				ServiceEmailSMSSchedulerQDataM serviceSchedulerQM = new ServiceEmailSMSSchedulerQDataM();
				serviceSchedulerQM.setSeqId(rs.getInt("SEQ_ID"));
				serviceSchedulerQM.setAppRecordId(rs.getString("APPLICATION_RECORD_ID"));
				serviceSchedulerQM.setServiceType(rs.getString("SERVICE_TYPE"));
				serviceSchedulerQM.setSubject(rs.getString("SUBJECT"));
				serviceSchedulerQM.setContent(rs.getString("CONTENT"));
				serviceSchedulerQM.setSendTo(rs.getString("SEND_TO"));
				serviceSchedulerQM.setSendFrom(rs.getString("SEND_FROM"));
				serviceSchedulerQM.setSendFromName(rs.getString("SENDER_NAME"));
				serviceSchedulerQM.setStatus(OrigConstant.FLAG_S);
				serviceSchedulerQM.setSchedulerDate(rs.getTimestamp("SCHEDULE_DATE"));
				schedQueueVT.add(serviceSchedulerQM);
			}
			
			return schedQueueVT;

		} catch (Exception e) {
			logger.fatal("",e);
			throw new PLOrigApplicationException(e.getMessage());
		} finally {
			try {
				closeConnection(conn, rs, ps);
			} catch (Exception e) {
				logger.fatal("",e);
			}
		}
	}

	@Override
	public void updateStatusEmailSMSSchedulerQ( ServiceEmailSMSSchedulerQDataM emailSMSschedulerQM) throws PLOrigApplicationException {
		PreparedStatement ps = null;
		Connection conn = null;
		try {

			conn = getConnection();
			StringBuilder sql = new StringBuilder("");
			
			sql.append("update SERVICE_EMAIL_SMS_SCHEDULE_Q schedQ ");
			sql.append("set schedQ.Status = ?, schedQ.Contact_Log_Id = ?, schedQ.Update_By = ?, schedQ.Update_Date = sysdate ");
			sql.append("where schedQ.Seq_Id = ?");

			String dSql = String.valueOf(sql);
			
//			logger.debug("@@@@@ updateStatusEmailSMSSchedulerQ Sql=" + dSql);
			
			ps = conn.prepareStatement(dSql);
			
			int index = 1;
			ps.setString(index++, emailSMSschedulerQM.getStatus());
			ps.setInt(index++, emailSMSschedulerQM.getContactLogId());
			ps.setString(index++, OrigConstant.UPDATE_BY_SCHEDULER);
			
			ps.setInt(index++, emailSMSschedulerQM.getSeqId());
			
			ps.executeUpdate();
			
		} catch (Exception e) {
			logger.fatal(e.getLocalizedMessage());
			throw new PLOrigApplicationException(e.getMessage());
		} finally {
			try {
				closeConnection(conn, ps);
			} catch (Exception e) {
				logger.fatal(e.getLocalizedMessage());
				throw new PLOrigApplicationException(e.getMessage());
			}
		}
	}
	
	public ArrayList<String> loadAutoCompleteJob() throws PLOrigApplicationException{
		ArrayList<String> appArray = new ArrayList<String>();
		PreparedStatement ps = null;
		ResultSet rs = null;Connection conn = null;
		try {
			//conn = Get Connection
			conn = getConnection();
			StringBuilder sql = new StringBuilder("");
			
			sql.append("select app.application_record_id ");
			sql.append("from orig_application app ");
				sql.append("inner join ac_account acc on acc.application_no = app.application_no ");
				sql.append("inner join wf_jobid_mapping wjm on wjm.application_record_id = app.application_record_id ");
				sql.append("inner join wf_work_queue wq on wq.job_id = wjm.job_id ");
			sql.append("where acc.account_status = ? ");
			String dSql = String.valueOf(sql);
			
//			logger.debug("@@@@@ loadAutoSendEmailFollowUp Sql=" + dSql);
			
			ps = conn.prepareStatement(dSql);
			rs = null;
			ps.setString(1, OrigConstant.AcAccount.ACCOUNT_STATUS_COMPLETE_CARD_LINK);

			rs = ps.executeQuery();

			while(rs.next()) {
				appArray.add(rs.getString("app_record_id"));
			}
			
			return appArray;

		} catch (Exception e) {
			logger.fatal("",e);
			throw new PLOrigApplicationException(e.getMessage());
		} finally {
			try {
				closeConnection(conn, rs, ps);
			} catch (Exception e) {
				logger.fatal("",e);
			}
		}
	}

	@Override
	public HashMap<String, Integer> loadWarningCardNo(String paramIdMain, String paramIdTemp)throws PLOrigApplicationException {
		PreparedStatement ps = null;
		ResultSet rs = null;
		Connection conn = null;
		HashMap<String, Integer> cardNoLength = new HashMap<String, Integer>();
		try {
			//conn = Get Connection
			conn = getConnection();
			StringBuilder sql = new StringBuilder("");
			
			sql.append("select ");
			sql.append("nvl((select nvl(rp.value_to,0) - (nvl(rp.value_from,0)+ nvl(rp.value1,0)) ");
			sql.append("from running_param rp where rp.param_id = ? and rp.param_type = 'CRD_NO'),0) main_amount, ");
			sql.append("nvl((select nvl(rp.value_to,0) - (nvl(rp.value_from,0)+ nvl(rp.value1,0)) ");
			sql.append("from running_param rp where rp.param_id = ? and rp.param_type = 'CRD_NO'),0) temp_amount ");
			sql.append("from dual ");
			
			String dSql = String.valueOf(sql);
			
//			logger.debug("@@@@@ loadWorningRunningParam Sql=" + dSql + " |paramIdMain :" + paramIdMain + "| paramIdTemp :" + paramIdTemp);
			
			ps = conn.prepareStatement(dSql);
			rs = null;
			ps.setString(1, paramIdMain);
			ps.setString(2, paramIdTemp);

			rs = ps.executeQuery();

			while(rs.next()) {
				cardNoLength.put(OrigConstant.WarningCard.KEY_MAIN, rs.getInt("main_amount"));
				cardNoLength.put(OrigConstant.WarningCard.KEY_TEMP, rs.getInt("temp_amount"));
			}
			
			return cardNoLength;

		} catch (Exception e) {
			logger.fatal("",e);
			throw new PLOrigApplicationException(e.getMessage());
		} finally {
			try {
				closeConnection(conn, rs, ps);
			} catch (Exception e) {
				logger.fatal("",e);
			}
		}
	}
	
	@Override
	public void insertInterfaceLog(InfLogDataM infLogM)throws PLOrigApplicationException {
		PreparedStatement ps = null;
		Connection conn = null;
		try {
			conn = getConnection();
			StringBuffer sql = new StringBuffer("");
			
			sql.append("insert into inf_log (module_id, log_type, log_code, message, create_date, create_by, ref_id) ");
			sql.append("values(?,?,?,?,sysdate,?,?)");
			String dSql = String.valueOf(sql);
			logger.debug("@@@@@ insertInterfaceLog Sql=" + dSql);
			ps = conn.prepareStatement(dSql);
			ps.setString(1, infLogM.getModuleID());
			ps.setString(2, infLogM.getLogType());
			ps.setString(3, infLogM.getLogCode());
			ps.setString(4, infLogM.getMessage());
			ps.setString(5, infLogM.getCreateBy());
			ps.setString(6, infLogM.getRefID());
			ps.executeUpdate();

		} catch (Exception e) {
			logger.fatal("",e);
			throw new PLOrigApplicationException(e.getMessage());
		} finally {
			try {
				closeConnection(conn, ps);
			} catch (Exception e) {
				logger.fatal("",e);
			}
		}
	}
}
