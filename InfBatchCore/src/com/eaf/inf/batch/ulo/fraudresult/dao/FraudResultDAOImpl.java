package com.eaf.inf.batch.ulo.fraudresult.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

import org.apache.log4j.Logger;

import com.eaf.core.ulo.common.dao.InfBatchObjectDAO;
import com.eaf.core.ulo.common.exception.InfBatchException;
import com.eaf.core.ulo.common.util.InfBatchUtil;
import com.eaf.inf.batch.ulo.cardlink.result.model.CardLinkResultConditionDataM;
import com.eaf.inf.batch.ulo.fraudresult.model.FraudResultDataM;
import com.eaf.orig.ulo.app.dao.ORIGDAOFactory;
import com.eaf.orig.ulo.app.dao.OrigApplicationGroupDAO;
import com.eaf.orig.ulo.app.dao.exception.ApplicationException;
import com.eaf.orig.ulo.app.factory.ModuleFactory;
import com.eaf.orig.ulo.app.model.ApplicationDataM;
import com.eaf.orig.ulo.model.app.ApplicationGroupDataM;
import com.eaf.orig.ulo.model.compare.CompareDataM;

public class FraudResultDAOImpl extends InfBatchObjectDAO implements FraudResultDAO {
	private static transient Logger logger = Logger.getLogger(FraudResultDAOImpl.class);


	public void updateApplicationStatus(String applicationGroupId,String applicationRecordId,String finalDecision,String finalDecisionBy,String finalDecisionDate,String vetoEligible,Connection conn) throws InfBatchException{
		PreparedStatement ps = null;
		try{
			StringBuilder sql = new StringBuilder("");
			sql.append(" UPDATE ORIG_APPLICATION ");
				sql.append(" SET FINAL_APP_DECISION = ?,");
				sql.append(" FINAL_APP_DECISION_DATE = ?, ");
				sql.append(" FINAL_APP_DECISION_BY = ?, ");
				sql.append(" RECOMMEND_DECISION = 'AP', ");
				sql.append(" IS_VETO_ELIGIBLE = ? ");
			sql.append(" WHERE APPLICATION_GROUP_ID=? AND APPLICATION_RECORD_ID =? ");
			String dSql = String.valueOf(sql);
			logger.debug("Sql=" + dSql);
			ps = conn.prepareStatement(dSql);
			int index = 1;		
			ps.setString(index++,finalDecision);
			ps.setString(index++,finalDecisionDate);
			ps.setString(index++,finalDecisionBy);
			ps.setString(index++,vetoEligible);
			ps.setString(index++,applicationGroupId);
			ps.setString(index++,applicationRecordId);
			ps.executeUpdate();			
		} catch (Exception e) {
			logger.fatal(e.getLocalizedMessage());
			throw new InfBatchException(e.getMessage());
		} finally {
			try {
				closeConnection(ps);
			} catch (Exception e) {
				logger.fatal(e.getLocalizedMessage());
			}
		}
	}

	
	/**
	 * Get applicationGroupId from applicationGroupNo. If return rows is more than 1, get applicationGroupId of the
	 * first row
	 */
	public String selectApplicationGroupId(String applicationGroupNo,Connection conn) throws InfBatchException{
		PreparedStatement ps = null;
		ResultSet rs = null;
		String applicationGroupId = null;
		
		try{
			String dSql = " SELECT APPLICATION_GROUP_ID FROM ORIG_APPLICATION_GROUP WHERE APPLICATION_GROUP_NO=? ";
			logger.debug("Sql=" + dSql);
			ps = conn.prepareStatement(dSql);
			int index = 1;
			ps.setString(index++,applicationGroupNo);
			rs = ps.executeQuery();
			if (rs.next())
				applicationGroupId = rs.getString("APPLICATION_GROUP_ID");
		} catch (Exception e) {
			logger.fatal(e.getLocalizedMessage());
			throw new InfBatchException(e.getMessage());
		} finally {
			try {
				closeConnection(ps);
			} catch (Exception e) {
				logger.fatal(e.getLocalizedMessage());
			}
		}
		return applicationGroupId;
	}
	
	
	/**
	 * Load data from database into object
	 * 
	 * @param applicationGroupNo
	 * @return
	 * @throws ApplicationException
	 */
	public ApplicationGroupDataM loadApplicationGroup(String applicationGroupId) throws InfBatchException {
		try {
			OrigApplicationGroupDAO  origApplicationGroup = ORIGDAOFactory.getApplicationGroupDAO();
			return origApplicationGroup.loadOrigApplicationGroupM(applicationGroupId);
		} catch (ApplicationException e) {
			logger.fatal(e.getLocalizedMessage());
			throw new InfBatchException(e.getMessage());
		}
	}

	/**
	 * Save data to database
	 *  
	 * @param applicationGroup
	 * @param userId
	 * @param updateDM
	 * @throws EJBException
	 */
	public void saveApplication(ApplicationGroupDataM applicationGroup,String userId) throws InfBatchException {
		try{
			String applicationGroupId = applicationGroup.getApplicationGroupId();
			logger.debug("applicationGroupId : "+applicationGroupId);
			ORIGDAOFactory.getApplicationGroupDAO(userId).saveUpdateOrigApplicationGroupM(applicationGroup);
			int lifeCycle = applicationGroup.getMaxLifeCycle();
			if(applicationGroup.isClearCompareData()){
				ModuleFactory.getOrigComparisionDataDAO(userId)
				.deleteComparisonDataNotMatchUniqueId(applicationGroupId, CompareDataM.SoruceOfData.TWO_MAKER, null,lifeCycle);
			}
		}catch(Exception e){
			logger.fatal(e.getLocalizedMessage());
			throw new InfBatchException(e.getMessage());
		}
	}


	private void updateInfBatchLogStatus(String moduleId,String flag,String applicationGroupId,String applicationRecordId,Connection conn) throws InfBatchException{
		PreparedStatement ps = null;
		try{
			StringBuilder sql = new StringBuilder("");
			sql.append(" UPDATE INF_BATCH_LOG ");
			sql.append(" SET INTERFACE_STATUS = ? ");
			sql.append(" WHERE INTERFACE_CODE = ? AND APPLICATION_GROUP_ID=? AND APPLICATION_RECORD_ID =? ");
			String dSql = String.valueOf(sql);
			logger.debug("Sql=" + dSql);
			ps = conn.prepareStatement(dSql);
			int index = 1;		
			ps.setString(index++,flag);
			ps.setString(index++,moduleId);
			ps.setString(index++,applicationGroupId);
			ps.setString(index++,applicationRecordId);
			ps.executeUpdate();			
		} catch (Exception e) {
			logger.fatal(e.getLocalizedMessage());
			throw new InfBatchException(e.getMessage());
		} finally {
			try {
				closeConnection(ps);
			} catch (Exception e) {
				logger.fatal(e.getLocalizedMessage());
			}
		}
	}
	public String selectJobStateByApplicationGroupNo(String applicationGroupNo) throws InfBatchException{
		PreparedStatement ps = null;
		ResultSet rs = null;
		String jobState = null;
		Connection conn = null;
		try{
			conn = getConnection();
			String dSql = " SELECT JOB_STATE FROM ORIG_APPLICATION_GROUP WHERE APPLICATION_GROUP_NO=? ";
			logger.debug("Sql=" + dSql);
			ps = conn.prepareStatement(dSql);
			int index = 1;
			ps.setString(index++,applicationGroupNo);
			rs = ps.executeQuery();
			if (rs.next())
				jobState = rs.getString("JOB_STATE");
		} catch (Exception e) {
			logger.fatal(e.getLocalizedMessage());
			throw new InfBatchException(e.getMessage());
		} finally {
			try {
				closeConnection(conn, rs, ps);
			} catch (Exception e) {
				logger.fatal(e.getLocalizedMessage());
			}
		}
		return jobState;
	}
}
