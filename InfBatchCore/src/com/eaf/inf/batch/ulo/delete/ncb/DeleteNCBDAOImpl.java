package com.eaf.inf.batch.ulo.delete.ncb;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;

import org.apache.log4j.Logger;

import com.eaf.core.ulo.common.cache.InfBatchProperty;
import com.eaf.core.ulo.common.dao.InfBatchObjectDAO;
import com.eaf.core.ulo.common.exception.InfBatchException;
import com.eaf.core.ulo.common.task.model.TaskObjectDataM;

public class DeleteNCBDAOImpl  extends InfBatchObjectDAO  implements DeleteNCBDAO {
	private static transient Logger logger = Logger.getLogger(DeleteNCBDAOImpl.class);
	String PARAM_NCB_DATE_EXPIRE = InfBatchProperty.getInfBatchConfig("DELETE_NCB_PARAM_NCB_DATE_EXPIRE");
	@Override
	public void deleteNCBDTable(String trackingCode, Connection conn) throws InfBatchException {
		PreparedStatement ps = null;
		logger.debug(">>trackingCodes>>>"+trackingCode);
		try {
			StringBuffer sql = new StringBuffer("");
			sql.append(" DELETE  NCB_ACCOUNT WHERE TRACKING_CODE  =?");
			String dSql1 = String.valueOf(sql);
			logger.debug("Sql1=" + dSql1);
			ps = conn.prepareStatement(dSql1);
			ps.setString(1,trackingCode);
			ps.executeUpdate();
			
			sql = new StringBuffer("");
			sql.append(" DELETE   NCB_ADDRESS WHERE TRACKING_CODE  =?");
			String dSql2 = String.valueOf(sql);
			logger.debug("Sql2=" + dSql2);
			ps = conn.prepareStatement(dSql2);
			ps.setString(1, trackingCode);
			ps.executeUpdate();
			
			sql = new StringBuffer("");
			sql.append(" DELETE NCB_ID WHERE TRACKING_CODE  =?");
			String dSql3 = String.valueOf(sql);
			logger.debug("Sql3=" + dSql3);
			ps = conn.prepareStatement(dSql3);
			ps.setString(1, trackingCode);
			ps.executeUpdate();
			
			sql = new StringBuffer("");
			sql.append(" DELETE NCB_NAME WHERE TRACKING_CODE  =?");
			String dSql4 = String.valueOf(sql);
			logger.debug("Sql4=" + dSql4);
			ps = conn.prepareStatement(dSql4);
			ps.setString(1, trackingCode);
			ps.executeUpdate();

		} catch (Exception e) {
			logger.fatal("ERROR",e);
			throw new InfBatchException(e.getMessage());
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
	public void deleteNCBAccountReport(String trackingCode, Connection conn) throws InfBatchException {
		PreparedStatement ps = null;
		logger.debug(">>trackingCodes>>>"+trackingCode);
		try{
			StringBuffer sql = new StringBuffer("");
			sql.append(" DELETE   NCB_ACCOUNT_REPORT WHERE TRACKING_CODE  =?");
			String dSql = String.valueOf(sql);
			logger.debug("Sql=" + dSql);
			ps = conn.prepareStatement(dSql);
			ps.setString(1, trackingCode);
			ps.executeUpdate();
		}catch (Exception e){
			logger.fatal("ERROR",e);
			throw new InfBatchException(e.getMessage());
		}finally{
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
	public void deleteNCBAccountRuleReport(ArrayList<String> accountReportIDs,Connection conn) throws InfBatchException {
		PreparedStatement ps = null;
		logger.debug(">>accountReportIDs>>>"+accountReportIDs);
		try{
			StringBuffer sql = new StringBuffer("");
			sql.append(" DELETE   NCB_ACCOUNT_RULE_REPORT WHERE ACCOUNT_REPORT_ID IN (");
			String COMMA="";
			for(String accountReportId :accountReportIDs){
				sql.append(COMMA+"?");
				COMMA=",";
			}
			sql.append(" ) ");
			String dSql = String.valueOf(sql);
			logger.debug(dSql);
			ps = conn.prepareStatement(dSql);
			int index=1;
			for(String accountReportId :accountReportIDs){
				ps.setString(index++, accountReportId);
			}
			ps.executeUpdate();
		}catch (Exception e){
			logger.fatal("ERROR",e);
			throw new InfBatchException(e.getMessage());
		}finally{
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
	public ArrayList<TaskObjectDataM>  selectDeleteTrackingCode() throws InfBatchException {
		Connection conn = null;
		PreparedStatement ps = null;
		ResultSet rs = null; 
		ArrayList<TaskObjectDataM> taskObjects = new ArrayList<TaskObjectDataM>();
		try{
			conn = getConnection();
			StringBuilder sql = new StringBuilder();
			sql.append(" SELECT TRACKING_CODE FROM  NCB_INFO");
			sql.append(" WHERE (SELECT TO_DATE(APP_DATE,'dd-mm-yyyy') FROM APPLICATION_DATE) -TO_DATE(NCB_INFO.CREATE_DATE,'dd-mm-yyyy') " +
					" >(SELECT  PARAM_VALUE FROM GENERAL_PARAM WHERE PARAM_CODE='"+PARAM_NCB_DATE_EXPIRE+"')");
			
			logger.debug(">>sql>>"+sql);
			ps = conn.prepareStatement(sql.toString());
			rs = ps.executeQuery();
			TaskObjectDataM queueObject = new TaskObjectDataM();
			while(rs.next()) {
				String TRACKING_CODE = rs.getString("TRACKING_CODE");	
				logger.debug("TRACKING_CODE >> "+TRACKING_CODE);
				queueObject = new TaskObjectDataM();
				queueObject.setUniqueId(TRACKING_CODE);
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
	public ArrayList<String> selectDeleteAccountReportID(String trackingCode, Connection conn) throws InfBatchException {
		PreparedStatement ps = null;
		ResultSet rs = null; 
		ArrayList<String> deleteAccountReportIDs = new ArrayList<String>();
		try{
			StringBuilder sql = new StringBuilder();
			sql.append(" SELECT ACCOUNT_REPORT_ID FROM  NCB_ACCOUNT_REPORT WHERE TRACKING_CODE = ? ");
			logger.debug(">>sql>>"+sql);
			ps = conn.prepareStatement(sql.toString());
				int index = 1;
				ps.setString(index++, trackingCode);
			rs = ps.executeQuery();
			while(rs.next()) {
				String ACCOUNT_REPORT_ID = rs.getString("ACCOUNT_REPORT_ID");	
				deleteAccountReportIDs.add(ACCOUNT_REPORT_ID);
			}
		}catch(Exception e){
			logger.fatal("ERROR ",e);
			throw new InfBatchException(e.getLocalizedMessage());
		}finally{
			try{
				closeConnection(ps, rs);
			}catch(Exception e){
				logger.fatal("ERROR ",e);
				throw new InfBatchException(e.getLocalizedMessage());
			}
		}
		return deleteAccountReportIDs;
	}
}
