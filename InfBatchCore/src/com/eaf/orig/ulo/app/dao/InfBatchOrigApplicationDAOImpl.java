package com.eaf.orig.ulo.app.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;

import org.apache.log4j.Logger;

import com.eaf.core.ulo.common.cache.InfBatchProperty;
import com.eaf.core.ulo.common.dao.InfBatchObjectDAO;
import com.eaf.core.ulo.common.exception.InfBatchException;
import com.eaf.core.ulo.common.util.InfBatchUtil;
import com.eaf.orig.ulo.app.factory.InfBatchOrigDAOFactory;
import com.eaf.orig.ulo.app.model.ApplicationDataM;
import com.eaf.orig.ulo.app.model.VerificationResultDataM;

public class InfBatchOrigApplicationDAOImpl extends InfBatchObjectDAO implements InfBatchOrigApplicationDAO{
	private static transient Logger logger = Logger.getLogger(InfBatchOrigApplicationDAOImpl.class);
	public InfBatchOrigApplicationDAOImpl(){
		
	}
	@Override
	public ArrayList<ApplicationDataM> loadApplicationsForGroup(String applicationGroupId, Connection conn)throws InfBatchException {
		return loadOrigApplication(applicationGroupId, conn);
	}
	private ArrayList<ApplicationDataM> loadOrigApplication(String applicationGroupId,Connection conn) throws InfBatchException{
		try{
			ArrayList<ApplicationDataM> applications =  selectTableORIG_APPLICATION(applicationGroupId,conn);
			if(!InfBatchUtil.empty(applications)){
				// load XRULES_VERIFICATION_RESULT
				String XRULES_VER_LEVEL_APPLICATION=InfBatchProperty.getInfBatchConfig("XRULES_VER_LEVEL_APPLICATION");
				InfBatchXrulesVerificationResultDAO verificationDAO = InfBatchOrigDAOFactory.getXrulesVerificationDAO();
				for(ApplicationDataM application:applications){
					VerificationResultDataM verResult = verificationDAO.loadVerificationResult(application.getApplicationRecordId(),XRULES_VER_LEVEL_APPLICATION,conn);
					if(!InfBatchUtil.empty(verResult)){
						application.setVerificationResult(verResult);
					}
				}
			}
			return applications;
		}catch (Exception e){
			logger.fatal("ERROR ",e);
			throw new InfBatchException(e);
		}
	}
	private ArrayList<ApplicationDataM> selectTableORIG_APPLICATION(String applicationGroupId,Connection conn) throws InfBatchException{
		PreparedStatement ps = null;
		ResultSet rs = null;
		ArrayList<ApplicationDataM> applications = new ArrayList<ApplicationDataM>();
		try{
//			conn = getConnection();
			StringBuilder sql = new StringBuilder("");
				sql.append("	SELECT APPLICATION_GROUP_ID,APPLICATION_RECORD_ID,BUSINESS_CLASS_ID,CREATE_BY,FINAL_APP_DECISION,FINAL_APP_DECISION_BY,FINAL_APP_DECISION_DATE,LIFE_CYCLE	");
				sql.append("	,CREATE_DATE,UPDATE_BY,UPDATE_DATE	");
				sql.append("	FROM ORIG_APPLICATION	");
				sql.append("	WHERE APPLICATION_GROUP_ID=?	");
			String dSql = sql.toString();
			logger.debug("Sql=" + dSql);
			ps = conn.prepareStatement(dSql);
			rs = null;
				ps.setString(1, applicationGroupId);
			rs = ps.executeQuery();
			while(rs.next()){
				ApplicationDataM application = new ApplicationDataM();
					application.setApplicationGroupId(rs.getString("APPLICATION_GROUP_ID"));
					application.setApplicationRecordId(rs.getString("APPLICATION_RECORD_ID"));
					application.setBusinessClassId(rs.getString("BUSINESS_CLASS_ID"));
					application.setCreateBy(rs.getString("CREATE_BY"));
					application.setCreateDate(rs.getTimestamp("CREATE_DATE"));
					application.setUpdateBy(rs.getString("UPDATE_BY"));
					application.setUpdateDate(rs.getTimestamp("UPDATE_DATE"));
					application.setFinalAppdecision(rs.getString("FINAL_APP_DECISION"));
					application.setFinalAppDecisionBy(rs.getString("FINAL_APP_DECISION_BY"));
					application.setFinalAppDecisionDate(rs.getDate("FINAL_APP_DECISION_DATE"));
					application.setLifeCycle(rs.getString("LIFE_CYCLE"));
				applications.add(application);
			}
			return applications;
		}catch(Exception e){
			logger.fatal("ERROR ",e);
			throw new InfBatchException(e);
		}finally{
			try{
				closeConnection(ps, rs);
			}catch(Exception e){
				logger.fatal("ERROR ",e);
			}
		}
	}
}
