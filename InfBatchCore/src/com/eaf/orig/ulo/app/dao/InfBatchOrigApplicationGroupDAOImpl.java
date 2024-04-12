package com.eaf.orig.ulo.app.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;

import org.apache.log4j.Logger;

import com.eaf.core.ulo.common.dao.InfBatchObjectDAO;
import com.eaf.core.ulo.common.exception.InfBatchException;
import com.eaf.core.ulo.common.util.InfBatchUtil;
import com.eaf.orig.ulo.app.factory.InfBatchOrigDAOFactory;
import com.eaf.orig.ulo.app.model.ApplicationDataM;
import com.eaf.orig.ulo.app.model.ApplicationGroupDataM;
import com.eaf.orig.ulo.app.model.ApplicationLogDataM;
import com.eaf.orig.ulo.app.model.PersonalInfoDataM;

public class InfBatchOrigApplicationGroupDAOImpl extends InfBatchObjectDAO implements InfBatchOrigApplicationGroupDAO{
	private static transient Logger logger = Logger.getLogger(InfBatchOrigApplicationGroupDAOImpl.class);
	public InfBatchOrigApplicationGroupDAOImpl() {
	
	}
	@Override
	public ApplicationGroupDataM loadOrigApplicationGroup(String applicationGroupId) throws InfBatchException{
		Connection conn = null;
		try {
			conn = getConnection();
			// load ORIG_APPLICATION_GROUP 
			ApplicationGroupDataM applicationGroup = selectTableORIG_APPLICATION_GROUP(applicationGroupId,conn);
			if(!InfBatchUtil.empty(applicationGroup)){
				InfBatchOrigApplicationDAO applicationDAO = InfBatchOrigDAOFactory.getApplicationDAO();
				// load ORIG_APPLICATION
				ArrayList<ApplicationDataM>  applicationList = applicationDAO.loadApplicationsForGroup(applicationGroup.getApplicationGroupId(), conn);
				if(!InfBatchUtil.empty(applicationList)){
					applicationGroup.setApplications(applicationList);
				}
				// load ORIG_PERSONAL_INFO
				InfBatchOrigPersonalInfoDAO personalInfoDAO = InfBatchOrigDAOFactory.getPersonalInfoDAO();
				ArrayList<PersonalInfoDataM> personalInfoList = personalInfoDAO.loadOrigPersonalInfo(applicationGroup.getApplicationGroupId(), conn);
				if(!InfBatchUtil.empty(personalInfoList)){
					applicationGroup.setPersonalInfos(personalInfoList);
				}
				// load ORIG_APPLICATION_LOG
				InfBatchOrigApplicationLogDAO appLogDAO = InfBatchOrigDAOFactory.getApplicationLogDAO();
				ArrayList<ApplicationLogDataM>  applicationLogList = appLogDAO.loadOrigApplicationLogM(applicationGroup.getApplicationGroupId(), conn);
				if(!InfBatchUtil.empty(applicationLogList)){
					applicationGroup.setApplicationLogs(applicationLogList);
				}
			}
			return applicationGroup;
		} catch (Exception e) {
			logger.fatal("ERROR",e);
			throw new InfBatchException(e);
		}finally{
			try{
				closeConnection(conn);
			}catch(Exception e){
				logger.fatal("ERROR ",e);
				throw new InfBatchException(e);
			}
		}
	}
	private ApplicationGroupDataM selectTableORIG_APPLICATION_GROUP(String applicationGroupId,Connection conn)throws InfBatchException{
		PreparedStatement ps = null;
		ResultSet rs = null;
		ApplicationGroupDataM applicationGroup = null;
		try {
			StringBuilder sql = new StringBuilder("");
				sql.append("	SELECT APPLICATION_GROUP_ID,APPLICATION_GROUP_NO,APPLICATION_STATUS,APPLICATION_TEMPLATE,APPLICATION_TYPE,JOB_STATE,LIFE_CYCLE	");
				sql.append("	,CREATE_BY,CREATE_DATE,UPDATE_BY,UPDATE_DATE,SOURCE	");
				sql.append("	FROM ORIG_APPLICATION_GROUP	");
				sql.append("    WHERE APPLICATION_GROUP_ID=? ");
			String dSql = String.valueOf(sql);
			logger.debug("Sql=" + dSql);
			ps = conn.prepareStatement(dSql);
				ps.setString(1, applicationGroupId);
			rs = ps.executeQuery();
			if(rs.next()){
				applicationGroup = new ApplicationGroupDataM();
				applicationGroup.setApplicationGroupId(rs.getString("APPLICATION_GROUP_ID"));
				applicationGroup.setApplicationGroupNo(rs.getString("APPLICATION_GROUP_NO"));
				applicationGroup.setApplicationStatus(rs.getString("APPLICATION_STATUS"));
				applicationGroup.setApplicationTemplate(rs.getString("APPLICATION_TEMPLATE"));
				applicationGroup.setApplicationType(rs.getString("APPLICATION_TYPE"));
				applicationGroup.setJobState(rs.getString("JOB_STATE"));
				applicationGroup.setLifeCycle(rs.getString("LIFE_CYCLE"));
				applicationGroup.setCreateBy(rs.getString("CREATE_BY"));
				applicationGroup.setCreateDate(rs.getTimestamp("CREATE_DATE"));
				applicationGroup.setUpdateBy(rs.getString("UPDATE_BY"));
				applicationGroup.setUpdateDate(rs.getTimestamp("UPDATE_DATE"));
				applicationGroup.setSource(rs.getString("SOURCE"));
			}
			return applicationGroup;
		}catch (Exception e){
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
