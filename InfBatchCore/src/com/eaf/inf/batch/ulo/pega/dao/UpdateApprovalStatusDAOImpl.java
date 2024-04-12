package com.eaf.inf.batch.ulo.pega.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;

import com.eaf.core.ulo.common.cache.InfBatchProperty;
import com.eaf.core.ulo.common.cont.InfBatchConstant;
import com.eaf.core.ulo.common.dao.InfBatchObjectDAO;
import com.eaf.core.ulo.common.exception.InfBatchException;
import com.eaf.orig.ulo.model.app.ApplicationDataM;
import com.eaf.orig.ulo.model.app.ApplicationGroupDataM;
import com.eaf.orig.ulo.model.pega.UpdateApprovalStatusRequestDataM;

public class UpdateApprovalStatusDAOImpl extends InfBatchObjectDAO implements UpdateApprovalStatusDAO{
	private static transient Logger logger = Logger.getLogger(UpdateApprovalStatusDAOImpl.class);
	
//	@Override
//	public Date getApplicationDate() throws InfBatchException {
//		Connection conn = null;
//		PreparedStatement ps = null;
//		ResultSet rs = null;
//		Date applicationDate = null;
//		try{
//			conn = getConnection();
//			StringBuilder sql = new StringBuilder();
//			sql.append(" SELECT * FROM APPLICATION_DATE ");
//			logger.debug("sql : "+sql);
//			ps = conn.prepareStatement(sql.toString());
//			rs = ps.executeQuery();
//			while(rs.next()){
//				applicationDate = rs.getDate("APP_DATE");
//			}
//		}catch(Exception e){
//			logger.fatal("ERROR ",e);
//		}finally{
//			try{
//				closeConnection(conn, rs, ps);
//			}catch(Exception e){
//				logger.fatal("ERROR ",e);
//			}
//		}
//		return applicationDate;
//	}

	@Override
	public ArrayList<ApplicationGroupDataM> loadApplicationGroup() throws InfBatchException {
		Connection conn = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		ArrayList<ApplicationGroupDataM> applicationGroups = new ArrayList<>();
		try{
			String WIP_JOBSTATE_SYS = InfBatchProperty.getGeneralParam("WIP_JOBSTATE_SYS");
			String WIP_JOBSTATE_END = InfBatchProperty.getGeneralParam("WIP_JOBSTATE_END");
			String[] CONDITION = WIP_JOBSTATE_END.split(",");
			String JOB_STATE = "'"+StringUtils.join(CONDITION, "','")+"','"+WIP_JOBSTATE_SYS+"'";
			conn = getConnection();
			StringBuilder sql = new StringBuilder();
			sql.append(" SELECT * FROM ORIG_APPLICATION_GROUP ");
			sql.append(" WHERE ORIG_APPLICATION_GROUP.JOB_STATE IN("+JOB_STATE+")  AND ROWNUM <=10 ");
			logger.debug("sql : "+sql);
			ps = conn.prepareStatement(sql.toString());
			rs = ps.executeQuery();
			while(rs.next()){
				ApplicationGroupDataM applicationGroup = new ApplicationGroupDataM();
				applicationGroup.setApplicationGroupId(rs.getString("APPLICATION_GROUP_ID"));
				applicationGroups.add(applicationGroup);
			}
		}catch(Exception e){
			logger.fatal("ERROR ",e);
		}finally{
			try{
				closeConnection(conn, rs, ps);
			}catch(Exception e){
				logger.fatal("ERROR ",e);
			}
		}
		return applicationGroups;
	}
	
	@Override
	public UpdateApprovalStatusRequestDataM loadNotifyApplication(String applicationGroupId) throws InfBatchException {
		Connection conn = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		UpdateApprovalStatusRequestDataM notifyApplicationRequest = new UpdateApprovalStatusRequestDataM();
		try{
			conn = getConnection();
			StringBuilder sql = new StringBuilder();
			sql.append(" SELECT ORIG_APPLICATION_GROUP.APPLICATION_GROUP_NO, ORIG_APPLICATION_GROUP.JOB_STATE, ");
			sql.append(" ORIG_APPLICATION_GROUP.APPLICATION_DATE, ORIG_APPLICATION_GROUP.IS_VETO_ELIGIBLE ");
			sql.append(" FROM ORIG_APPLICATION_GROUP ");
			sql.append(" WHERE ORIG_APPLICATION_GROUP.APPLICATION_GROUP_ID = ? ");
			logger.debug("sql : "+sql);
			int parameter = 1;
			ps = conn.prepareStatement(sql.toString());
			ps.setString(parameter++, applicationGroupId);
			rs = ps.executeQuery();
			while(rs.next()){
				notifyApplicationRequest.setApplicationGroupNo(rs.getString("APPLICATION_GROUP_NO"));
				notifyApplicationRequest.setJobState(rs.getString("JOB_STATE"));
				notifyApplicationRequest.setApplicationDate(rs.getDate("APPLICATION_DATE"));
				notifyApplicationRequest.setIsVetoEligible(rs.getString("IS_VETO_ELIGIBLE"));
				notifyApplicationRequest.setApplications(loadApplication(applicationGroupId));
			}
		}catch(Exception e){
			logger.fatal("ERROR ",e);
		}finally{
			try{
				closeConnection(conn, rs, ps);
			}catch(Exception e){
				logger.fatal("ERROR ",e);
			}
		}
		return notifyApplicationRequest;
	}
	
	@Override
	public ArrayList<ApplicationDataM> loadApplication(String applicationGroupId) throws InfBatchException{
		Connection conn = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		ArrayList<ApplicationDataM> applications = new ArrayList<>();
		try{
			conn = getConnection();
			StringBuilder sql = new StringBuilder();
			sql.append(" SELECT * FROM ORIG_APPLICATION WHERE APPLICATION_GROUP_ID = ? ");
			logger.debug("sql : "+sql);
			int parameter = 1;
			ps = conn.prepareStatement(sql.toString());
			ps.setString(parameter++, applicationGroupId);
			rs = ps.executeQuery();
			logger.debug("APPLICATION_GROUP_ID : "+applicationGroupId);
			while(rs.next()){
				ApplicationDataM application = new ApplicationDataM();
				logger.debug("applicationRecordId : "+rs.getString("APPLICATION_RECORD_ID"));
				application.setApplicationRecordId(rs.getString("APPLICATION_RECORD_ID"));
				application.setFinalAppDecisionDate(rs.getDate("FINAL_APP_DECISION_DATE"));
				application.setLifeCycle(rs.getInt("LIFE_CYCLE"));
				applications.add(application);
			}
		}catch(Exception e){
			logger.fatal("ERROR ",e);
		}finally{
			try{
				closeConnection(conn, rs, ps);
			}catch(Exception e){
				logger.fatal("ERROR ",e);
			}
		}
		return applications;
	}

	@Override
	public boolean isUpdateBatchLog(String applicationRecordId) throws InfBatchException {
		boolean isUpdate = true;
		Connection conn = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		try{
			conn = getConnection();
			StringBuilder sql = new StringBuilder();
			sql.append(" SELECT 1 FROM INF_BATCH_LOG WHERE APPLICATION_RECORD_ID = ? AND INTERFACE_CODE = ? ");
			logger.debug("sql : "+sql);
			int parameter = 1;
			ps = conn.prepareStatement(sql.toString());
			ps.setString(parameter++, applicationRecordId);
			ps.setString(parameter++, InfBatchProperty.getInfBatchConfig("PEGA_UPDATE_APPROVE_MODULE_ID"));
			rs = ps.executeQuery();
			if(rs.next()){
				isUpdate = false;
			}
		}catch(Exception e){
			logger.fatal("ERROR ",e);
		}finally{
			try{
				closeConnection(conn, rs, ps);
			}catch(Exception e){
				logger.fatal("ERROR ",e);
			}
		}
		return isUpdate;
	}
	
	@Override
	public ArrayList<ApplicationGroupDataM> loadUpdateApprovalStatusApplicationGroup() throws InfBatchException{
		Connection conn = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		ArrayList<ApplicationGroupDataM> applicationGroups = new ArrayList<>();
		try{
			conn = getConnection();
			StringBuilder sql = new StringBuilder();
			sql.append(" SELECT DISTINCT");
			sql.append("     G.APPLICATION_GROUP_NO,");
			sql.append("     G.APPLICATION_GROUP_ID,");
			sql.append("     G.APPLICATION_STATUS,");
			sql.append("     G.JOB_STATE,");
			sql.append("     G.IS_VETO_ELIGIBLE,");
			sql.append("     WP.VALUE1 VETO_DAY");
			sql.append(" FROM ORIG_APPLICATION_GROUP G");
			sql.append(" LEFT JOIN ORIG_APPLICATION A1 ON A1.APPLICATION_GROUP_ID = G.APPLICATION_GROUP_ID AND A1.LIFE_CYCLE = ? AND A1.RECOMMEND_DECISION = ?");
			sql.append(" LEFT JOIN ORIG_APPLICATION A3 ON A3.APPLICATION_GROUP_ID = G.APPLICATION_GROUP_ID AND A3.LIFE_CYCLE = ? AND A3.RECOMMEND_DECISION = ?");
			sql.append(" LEFT JOIN WORKFLOW_PARAM WP ON WP.PARAM_ID = ?");
			sql.append(" LEFT JOIN ORIG_PERSONAL_INFO PS ON A1.APPLICATION_GROUP_ID = PS.APPLICATION_GROUP_ID");
			sql.append(" LEFT JOIN NCB_INFO NCB ON PS.PERSONAL_ID = NCB.PERSONAL_ID");
			sql.append(" WHERE");
			sql.append("     (( G.JOB_STATE = ? OR  G.JOB_STATE = ? OR  G.JOB_STATE = ? OR  G.JOB_STATE = ? OR  G.JOB_STATE = ? )");
			sql.append("     OR ( G.JOB_STATE = ? AND G.IS_VETO_ELIGIBLE = ?)");
			sql.append("     OR ( G.JOB_STATE = ? AND G.IS_VETO_ELIGIBLE = ? ");
			//sql.append("                 AND (A3.LIFE_CYCLE IS NOT NULL OR ((SELECT TRUNC(APP_DATE) FROM APPLICATION_DATE) - TRUNC(A1.FINAL_APP_DECISION_DATE) >= TO_NUMBER(WP.VALUE1)))");
			sql.append("                 AND (A3.LIFE_CYCLE IS NOT NULL OR ((SELECT TRUNC(APP_DATE) FROM APPLICATION_DATE) - NVL(TRUNC(NCB.DATE_OF_SEARCH),TRUNC(A1.FINAL_APP_DECISION_DATE)) >= TO_NUMBER(WP.VALUE1)))");
			sql.append("         ))");
			sql.append(" AND NOT EXISTS");
			sql.append("     ( SELECT 1 FROM INF_BATCH_LOG L WHERE L.APPLICATION_GROUP_ID = G.APPLICATION_GROUP_ID");
			sql.append("         AND L.INTERFACE_CODE = ? AND L.INTERFACE_STATUS = ?)");
			sql.append(" AND NOT EXISTS (SELECT 1 FROM TABLE( ORIG_APP.F_GET_REPROCESS_APP) RP WHERE G.APPLICATION_GROUP_ID = RP.APPLICATION_GROUP_ID) ");
			sql.append(" ORDER BY");
			sql.append("     G.APPLICATION_GROUP_NO");
			ps = conn.prepareStatement(sql.toString());
			
			int parameterIndex = 1;
			String RECOMMEND_DECISION = InfBatchProperty.getInfBatchConfig("UPDATE_APPROVAL_STATUS_RECOMMEND_DECISION"); 
			
			ps.setString(parameterIndex++, InfBatchProperty.getGeneralParam("UP_AP_ST_MAX_LIFE_CYCLE_LOW"));
			ps.setString(parameterIndex++, RECOMMEND_DECISION);
			
			ps.setString(parameterIndex++, InfBatchProperty.getGeneralParam("UP_AP_ST_MAX_LIFE_CYCLE_HIGH"));
			ps.setString(parameterIndex++, RECOMMEND_DECISION);
			ps.setString(parameterIndex++, InfBatchProperty.getInfBatchConfig("VETO_DAY_PARAM_ID"));
			
			String[] UPDATE_APPROVAL_STATUS_JOB_STATES = InfBatchProperty.getArrayInfBatchConfig("UPDATE_APPROVAL_STATUS_JOB_STATES");
			for(String UPDATE_APPROVAL_STATUS_JOB_STATE : UPDATE_APPROVAL_STATUS_JOB_STATES){
				ps.setString(parameterIndex++, UPDATE_APPROVAL_STATUS_JOB_STATE);
			}
			
			ps.setString(parameterIndex++, InfBatchProperty.getGeneralParam("JOB_STATE_REJECTED"));
			ps.setString(parameterIndex++, InfBatchConstant.FLAG_NO);
			ps.setString(parameterIndex++, InfBatchProperty.getGeneralParam("JOB_STATE_REJECTED"));
			ps.setString(parameterIndex++, InfBatchConstant.FLAG_YES);
			
			ps.setString(parameterIndex++, InfBatchProperty.getInfBatchConfig("UPDATE_APPROVAL_STATUS_MODULE_ID"));
			ps.setString(parameterIndex++, InfBatchConstant.STATUS_COMPLETE);
			
			rs = ps.executeQuery();
			while(rs.next()){
				ApplicationGroupDataM applicationGroup = new ApplicationGroupDataM();
					applicationGroup.setApplicationGroupNo(rs.getString("APPLICATION_GROUP_NO"));
					applicationGroup.setApplicationGroupId(rs.getString("APPLICATION_GROUP_ID"));
					applicationGroup.setApplicationStatus(rs.getString("APPLICATION_STATUS"));
					applicationGroup.setJobState(rs.getString("JOB_STATE"));
					applicationGroup.setIsVetoEligible(InfBatchConstant.FLAG_NO);
				applicationGroups.add(applicationGroup);
			}
		}catch(Exception e){
			logger.fatal("ERROR ",e);
			throw new InfBatchException(e);
		}finally{
			try{
				closeConnection(conn, rs, ps);
			}catch(Exception e){
				logger.fatal("ERROR ",e);
				throw new InfBatchException(e);
			}
		}
		return applicationGroups;
	}
	
}
