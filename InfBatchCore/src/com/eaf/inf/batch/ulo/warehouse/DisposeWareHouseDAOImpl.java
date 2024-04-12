package com.eaf.inf.batch.ulo.warehouse;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;

import org.apache.log4j.Logger;

import com.eaf.core.ulo.common.cache.InfBatchProperty;
import com.eaf.core.ulo.common.dao.InfBatchObjectDAO;
import com.eaf.core.ulo.common.dao.InfBatchServiceLocator;
import com.eaf.core.ulo.common.exception.InfBatchException;
import com.eaf.core.ulo.common.task.model.TaskObjectDataM;
import com.eaf.orig.ulo.constant.MConstant;

public class DisposeWareHouseDAOImpl extends InfBatchObjectDAO  implements DisposeWareHouseDAO {
	private static transient Logger logger = Logger.getLogger(DisposeWareHouseDAOImpl.class);
	@Override
	public void disposeWareHouseProcess(String dmId ,Connection conn) throws InfBatchException {
		try {
			this.updateDMDocStatusDispose(dmId ,conn);
			this.insertDisposeActionDMTransaction(dmId ,conn);
		} catch (Exception e) {
			logger.fatal("ERROR",e);
			throw new InfBatchException(e.getLocalizedMessage());
		}
	}
	public void updateDMDocStatusDispose(String dmId ,Connection conn) throws InfBatchException {
		PreparedStatement ps = null;
		String DM_STATUS_DISPOSE =InfBatchProperty.getInfBatchConfig("WAREHOUSE_DISPOSE_DM_STATUS_DISPOSE");
		try {

			StringBuilder sql = new StringBuilder("");					
			sql.append("UPDATE  DM_DOC ");
			sql.append(" SET STATUS=?, UPDATE_DATE=?,UPDATE_BY=?");
			sql.append(" WHERE  DM_ID=?");
			
			String dSql = String.valueOf(sql);
			logger.debug("Sql=" + dSql);
			logger.debug("dmId=" + dmId);
			logger.debug("DM_STATUS_DISPOSE=" + DM_STATUS_DISPOSE);

			ps = conn.prepareStatement(dSql);	
			int intdex = 1;
			ps.setString(intdex++,DM_STATUS_DISPOSE);
			ps.setTimestamp(intdex++,InfBatchProperty.getTimestamp());
			ps.setString(intdex++,InfBatchProperty.getInfBatchConfig("SYSTEM_USER_ID"));
			ps.setString(intdex++,dmId);
			ps.executeUpdate();
		} catch (Exception e) {
			logger.fatal("ERROR",e);
			throw new InfBatchException(e.getLocalizedMessage());
		} finally {
			try {
				if (ps != null){
					try{
						ps.close();
					} catch (Exception e) {
						logger.fatal("ERROR",e);
						throw new InfBatchException(e.getLocalizedMessage());
					}
				}
			} catch (Exception e) {
				logger.fatal("ERROR",e);
				throw new InfBatchException(e.getLocalizedMessage());
			}
		}
	}
	public void insertDisposeActionDMTransaction(String dmId,Connection conn) throws InfBatchException {
		PreparedStatement ps = null;
		String DM_TRANSACTION_ACTION_DISPOSE =InfBatchProperty.getInfBatchConfig("WAREHOUSE_DISPOSE_TRANSACTION_ACTION_DISPOSE");
		String DM_TRANSACTION_STATUS_DISPOSE =InfBatchProperty.getInfBatchConfig("WAREHOUSE_DISPOSE_TRANSACTION_STATUS_DISPOSE");
		try {
			StringBuilder sql = new StringBuilder("");
			sql.append("INSERT INTO DM_TRANSACTION ");
			sql.append("(DM_TRANSACTION_ID, DM_ID, ACTION, STATUS, ACTION_DATE, CREATE_DATE, CREATE_BY )");
			sql.append(" SELECT DM_TRANSACTION_ID_PK.NEXTVAL,?,?,?,SYSDATE ,SYSDATE,? FROM DUAL");
		
			String dSql = String.valueOf(sql);
			logger.debug("Sql=" + dSql);
			ps = conn.prepareStatement(dSql);	
			int intdex = 1;
			ps.setString(intdex++,dmId);
			ps.setString(intdex++,DM_TRANSACTION_ACTION_DISPOSE);
			ps.setString(intdex++,DM_TRANSACTION_STATUS_DISPOSE);
			ps.setString(intdex++,InfBatchProperty.getInfBatchConfig("SYSTEM_USER_ID"));
			ps.executeUpdate();
			
		} catch (Exception e) {
			logger.fatal("ERROR",e);
			throw new InfBatchException(e.getLocalizedMessage());
		} finally {
			try {
				if (ps != null){
					try{
						ps.close();
					} catch (Exception e) {
						logger.fatal("ERROR",e);
						throw new InfBatchException(e.getLocalizedMessage());
					}
				}
			} catch (Exception e) {
				logger.fatal("ERROR",e);
				throw new InfBatchException(e.getLocalizedMessage());
			}
		}
	}
	
	@Override
	public ArrayList<TaskObjectDataM>  selectDisposeDMDocId() throws InfBatchException {
		Connection conn = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		ArrayList<TaskObjectDataM> taskObjects = new ArrayList<TaskObjectDataM>();
		String[] DM_APP_STATUS_CONDITION =InfBatchProperty.getInfBatchConfig("WAREHOUSE_DISPOSE_APP_STATUS_CONDITION").split(",");
		String APP_DATE = getApplicationDate();
		logger.debug("APP_DATE>>"+APP_DATE);
		try{
			conn = getConnection(InfBatchServiceLocator.DM_DB);
			StringBuilder sql = new StringBuilder();
			sql.append(" SELECT DISTINCT DM_ID FROM  DM_DOC ");
			sql.append(" INNER JOIN  MS_DM_DISPOSE_POLICY  DM_AGE ON DM_AGE.POLICY_ID  =DM_DOC.POLICY_ID  AND  DM_AGE.DOC_SET_TYPE= DM_DOC.DOC_SET_TYPE");
			sql.append(" WHERE TO_DATE('"+APP_DATE+"','yyyy-mm-dd hh24:mi:ss') -TO_DATE(NVL(DM_DOC.PARAM2,'"+APP_DATE+"'),'yyyy-mm-dd hh24:mi:ss') > DM_AGE.DISPOSE_AGE ");
			sql.append("  AND  DM_DOC.PARAM1 IN (");
			String COMMA ="";
			for (int i = 0, count = DM_APP_STATUS_CONDITION.length ; i < count ; i++) {
					sql.append(COMMA+"?");
					COMMA = ",";
			}
			sql.append(")");
			sql.append(" AND DM_DOC.STATUS=?");
			logger.debug("sql : " + sql);
			ps = conn.prepareStatement(sql.toString());
			int index = 1;					 
			for(String applicationStatus : DM_APP_STATUS_CONDITION){
				ps.setString(index++,applicationStatus); 							 
			}
			ps.setString(index++,MConstant.DM_STATUS.AVAILABLE); 	
			rs = ps.executeQuery();
			TaskObjectDataM queueObject = new TaskObjectDataM();
			while(rs.next()) {
				String DM_ID=rs.getString("DM_ID");
				queueObject = new TaskObjectDataM();
				queueObject.setUniqueId(DM_ID);
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
	
	
	private String getApplicationDate() throws InfBatchException {
		Connection conn = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		String applicationdate="";		
		try{
			conn = getConnection(InfBatchServiceLocator.ORIG_DB);
			StringBuilder sql = new StringBuilder();
			sql.append(" SELECT TO_CHAR(APP_DATE,'yyyy-mm-dd hh24:mi:ss') AS APP_DATE FROM APPLICATION_DATE ");
			logger.debug("sql : " + sql);
			ps = conn.prepareStatement(sql.toString());
			rs = ps.executeQuery();
			if(rs.next()) {
				applicationdate=rs.getString("APP_DATE");
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
		 return applicationdate;
		}
}
