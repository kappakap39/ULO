package com.eaf.inf.batch.ulo.warehouse;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;

import com.eaf.core.ulo.common.cache.InfBatchProperty;
import com.eaf.core.ulo.common.dao.InfBatchObjectDAO;
import com.eaf.core.ulo.common.exception.InfBatchException;
import com.eaf.core.ulo.common.task.model.TaskObjectDataM;
import com.eaf.core.ulo.common.util.Util;
import com.eaf.inf.batch.ulo.warehouse.model.CreateWarehouseDataM;

public class SendApplicationWarehouseDAOImpl extends InfBatchObjectDAO implements SendApplicationWarehouseDAO {
	private static transient Logger logger = Logger.getLogger(SendApplicationWarehouseDAOImpl.class);
	String CREATE_WAREHOUSE_MODULE_ID = InfBatchProperty.getInfBatchConfig("CREATE_WAREHOUSE_MODULE_ID");
	String INTERFACE_STATUS_COMPLETE = InfBatchProperty.getInfBatchConfig("INTERFACE_STATUS_COMPLETE");
		
	@Override
	public ArrayList<TaskObjectDataM> selectApplicationToWarehouse(String[] jobStates) throws InfBatchException {
		Connection conn = null;
		PreparedStatement ps = null;
		ResultSet rs = null; 
		ArrayList<TaskObjectDataM> taskObjects = new ArrayList<TaskObjectDataM>();
		try{
			conn = getConnection();
			StringBuilder sql = new StringBuilder();
			String jobStateCondition = "";
			if(!Util.empty(jobStates)){
				String[] size = new String[jobStates.length];
				jobStateCondition = " AND AG.JOB_STATE IN ( ? "+StringUtils.join(size, ", ?")+" ) ";
			}
			sql.append(" SELECT AG.INSTANT_ID, AG.APPLICATION_GROUP_ID, AG.APPLICATION_GROUP_NO, ");
			sql.append(" MAX(A.LIFE_CYCLE) MAX_LIFE_CYCLE ");
			sql.append(" FROM ORIG_APPLICATION_GROUP AG ");
			sql.append(" LEFT JOIN ORIG_APPLICATION A ");
			sql.append(" ON A.APPLICATION_GROUP_ID = AG.APPLICATION_GROUP_ID ");
			sql.append(" WHERE NOT EXISTS ");
			sql.append(" 	( ");
			sql.append("		SELECT APPLICATION_GROUP_ID ");
			sql.append("		FROM INF_BATCH_LOG ");
			sql.append("		WHERE INTERFACE_CODE = ? AND INTERFACE_STATUS = ? ");
			sql.append("		AND A.LIFE_CYCLE = INF_BATCH_LOG.LIFE_CYCLE ");
			sql.append("		AND AG.APPLICATION_GROUP_ID = INF_BATCH_LOG.APPLICATION_GROUP_ID ");
			sql.append(" 	) ");
			sql.append(jobStateCondition);
			sql.append(" GROUP BY AG.INSTANT_ID, AG.APPLICATION_GROUP_ID, AG.APPLICATION_GROUP_NO "); 
			logger.debug("sql : " + sql);
			ps = conn.prepareStatement(sql.toString());
			int index = 1;
			ps.setString(index++, CREATE_WAREHOUSE_MODULE_ID);
			ps.setString(index++, INTERFACE_STATUS_COMPLETE);
			for(String jobState : jobStates){
				logger.debug("jobState >> "+jobState);
				ps.setString(index++, jobState);						 
			}
			rs = ps.executeQuery();
			while(rs.next()) {
				String INSTANT_ID = rs.getString("INSTANT_ID");	
				logger.debug("INSTANT_ID >> "+INSTANT_ID);
				CreateWarehouseDataM createWarehouse = new CreateWarehouseDataM();
					createWarehouse.setInterfaceCode(CREATE_WAREHOUSE_MODULE_ID);
					createWarehouse.setInstantId(INSTANT_ID);
					createWarehouse.setApplicationGroupId(rs.getString("APPLICATION_GROUP_ID"));
					createWarehouse.setApplicationGroupNo(rs.getString("APPLICATION_GROUP_NO"));
					createWarehouse.setMaxLifeCycle(rs.getInt("MAX_LIFE_CYCLE"));
				TaskObjectDataM queueObject = new TaskObjectDataM();
					queueObject.setUniqueId(INSTANT_ID);
					queueObject.setObject(createWarehouse);
				taskObjects.add(queueObject);
			}
		}catch(Exception e){
			logger.fatal("ERROR ",e);
			throw new InfBatchException(e.getLocalizedMessage());
		}finally{
			try{
				closeConnection(conn, rs, ps);
			}catch(Exception e){
				logger.fatal("ERROR ",e);
				throw new InfBatchException(e.getLocalizedMessage());
			}
		}
		return taskObjects;
	}
	@Override
	public ArrayList<TaskObjectDataM> selectAllApplicationToWarehouse() throws InfBatchException {
		Connection conn = null;
		PreparedStatement ps = null;
		ResultSet rs = null; 
		ArrayList<TaskObjectDataM> taskObjects = new ArrayList<TaskObjectDataM>();
		try{
			conn = getConnection();
			StringBuilder sql = new StringBuilder();
			sql.append(" SELECT AG.INSTANT_ID,AG.APPLICATION_GROUP_ID, AG.APPLICATION_GROUP_NO, ");
			sql.append(" MAX(A.LIFE_CYCLE) MAX_LIFE_CYCLE ");
			sql.append(" FROM ORIG_APPLICATION_GROUP AG ");
			sql.append(" LEFT JOIN ORIG_APPLICATION A ");
			sql.append(" ON A.APPLICATION_GROUP_ID = AG.APPLICATION_GROUP_ID ");
			sql.append(" GROUP BY AG.INSTANT_ID, AG.APPLICATION_GROUP_ID, AG.APPLICATION_GROUP_NO "); 
			logger.debug("sql : " + sql);
			ps = conn.prepareStatement(sql.toString());
			rs = ps.executeQuery();
			while(rs.next()) {
				String APPLICATION_GROUP_ID = rs.getString("APPLICATION_GROUP_ID");	
				logger.debug("APPLICATION_GROUP_ID >> "+APPLICATION_GROUP_ID);
				CreateWarehouseDataM createWarehouse = new CreateWarehouseDataM();
				createWarehouse.setInterfaceCode(CREATE_WAREHOUSE_MODULE_ID);
				createWarehouse.setInstantId(rs.getString("INSTANT_ID"));
				createWarehouse.setApplicationGroupId(rs.getString("APPLICATION_GROUP_ID"));
				createWarehouse.setApplicationGroupNo(rs.getString("APPLICATION_GROUP_NO"));
				createWarehouse.setMaxLifeCycle(rs.getInt("MAX_LIFE_CYCLE"));
				TaskObjectDataM queueObject = new TaskObjectDataM();
				queueObject.setUniqueId(APPLICATION_GROUP_ID);
				queueObject.setObject(createWarehouse);
				taskObjects.add(queueObject);
			}
		}catch(Exception e){
			logger.fatal("ERROR ",e);
			throw new InfBatchException(e.getLocalizedMessage());
		}finally{
			try{
				closeConnection(conn, rs, ps);
			}catch(Exception e){
				logger.fatal("ERROR ",e);
				throw new InfBatchException(e.getLocalizedMessage());
			}
		}
		return taskObjects;
	}
}
