package com.eaf.orig.shared.dao.utility;

import java.sql.Blob;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.Vector;

import com.eaf.cache.data.CacheDataM;
import com.eaf.orig.inf.log.model.INFExportDataM;
import com.eaf.orig.master.shared.model.ReportParam;
import com.eaf.orig.shared.dao.OrigObjectDAO;

public class ReportParamDAOImpl extends OrigObjectDAO implements ReportParamDAO{
	@Override
	public Vector<ReportParam> getReportParamM(String param_type){	
		logger.debug("param_type > "+param_type);
		Connection conn = null;
		Vector<ReportParam> vReportparam = new Vector<ReportParam>();
		PreparedStatement ps = null;
		ResultSet rs = null;
		try{
			conn = getConnection();
			StringBuilder SQL = new StringBuilder("");
				SQL.append(" SELECT ");
				SQL.append("     PARAM_TYPE, ");
				SQL.append("     PARAM_CODE, ");
				SQL.append("     PARAM_VALUE, ");
				SQL.append("     PARAM_VALUE2, ");
				SQL.append("     CREATE_DATE, ");
				SQL.append("     CREATE_BY, ");
				SQL.append("     UPDATE_DATE, ");
				SQL.append("     UPDATE_BY, ");
				SQL.append("     REMARK, ");
				SQL.append("     PARAM_VALUE3 ");
				SQL.append(" FROM ");
				SQL.append("     MS_REPORT_PARAM ");
				SQL.append(" WHERE ");
				SQL.append("     PARAM_TYPE = ? ");
				SQL.append(" ORDER BY PARAM_CODE ");
			String dSql = String.valueOf(SQL);
			ps = conn.prepareCall(dSql);			
			int index = 1;			
			ps.setString(index++,param_type);			
			rs = ps.executeQuery();		
			ReportParam reportparam = null;			
			while(rs.next()){
				reportparam = new ReportParam();
				reportparam.setParamType(rs.getString("PARAM_TYPE"));
				reportparam.setParamCode(rs.getString("PARAM_CODE"));
				reportparam.setParamValue(rs.getString("PARAM_VALUE"));
				reportparam.setParamValue2(rs.getString("PARAM_VALUE2"));
				reportparam.setParamValue3(rs.getString("PARAM_VALUE3"));
				reportparam.setCreateDate(rs.getDate("CREATE_DATE"));
				reportparam.setCreateBy(rs.getString("CREATE_BY"));
				reportparam.setUpdateDate(rs.getDate("UPDATE_DATE"));
				reportparam.setUpdateBy(rs.getString("UPDATE_BY"));
				reportparam.setRemark(rs.getString("REMARK"));
				vReportparam.add(reportparam);
			}
		}catch(Exception e){
			logger.fatal(e.getLocalizedMessage());
		}finally{
			try{
				closeConnection(conn,rs,ps);
			}catch(Exception e){
				logger.fatal(e.getLocalizedMessage());
			}
		}
		return vReportparam;
	}
	@Override
	public ReportParam getReportParamM(String paramType, String paramCode) {		
		logger.debug("paramType : "+paramType);
		logger.debug("paramCode : "+paramCode);
		ReportParam reportparam = new ReportParam();	
		PreparedStatement ps = null;
		ResultSet rs = null;
		Connection conn = null;
		try{
			conn = getConnection();
			StringBuilder SQL = new StringBuilder("");
			SQL.append(" SELECT ");
			SQL.append("     PARAM_TYPE, ");
			SQL.append("     PARAM_CODE, ");
			SQL.append("     PARAM_VALUE, ");
			SQL.append("     PARAM_VALUE2, ");
			SQL.append("     CREATE_DATE, ");
			SQL.append("     CREATE_BY, ");
			SQL.append("     UPDATE_DATE, ");
			SQL.append("     UPDATE_BY, ");
			SQL.append("     REMARK, ");
			SQL.append("     PARAM_VALUE3 ");
			SQL.append(" FROM ");
			SQL.append("     MS_REPORT_PARAM ");
			SQL.append(" WHERE ");
			SQL.append("     PARAM_TYPE = ? AND PARAM_CODE = ?");
			String dSql = String.valueOf(SQL);
			ps = conn.prepareCall(dSql);			
			int index = 1;			
			ps.setString(index++,paramType);		
			ps.setString(index++,paramCode);	
			rs = ps.executeQuery();	
			while(rs.next()){
				reportparam.setParamType(rs.getString("PARAM_TYPE"));
				reportparam.setParamCode(rs.getString("PARAM_CODE"));
				reportparam.setParamValue(rs.getString("PARAM_VALUE"));
				reportparam.setParamValue2(rs.getString("PARAM_VALUE2"));
				reportparam.setParamValue3(rs.getString("PARAM_VALUE3"));
				reportparam.setCreateDate(rs.getDate("CREATE_DATE"));
				reportparam.setCreateBy(rs.getString("CREATE_BY"));
				reportparam.setUpdateDate(rs.getDate("UPDATE_DATE"));
				reportparam.setUpdateBy(rs.getString("UPDATE_BY"));
				reportparam.setRemark(rs.getString("REMARK"));
			}
		}catch(Exception e){
			logger.fatal(e.getLocalizedMessage());
		}finally{
			try{
				closeConnection(conn,rs,ps);
			}catch(Exception e){
				logger.fatal(e.getLocalizedMessage());
			}
		}
		return reportparam;
	}
	
	@Override
	public Vector<CacheDataM> getDATE() {
		Vector<CacheDataM> dateVect = new Vector<CacheDataM>();
		PreparedStatement ps = null;
		ResultSet rs = null;
		Connection conn = null;
		try{
			conn = getConnection();
			
			StringBuilder SQL = new StringBuilder();
				SQL.append(" SELECT DISTINCT TO_CHAR(DATA_DATE,'yyyyMMdd') DATA_DATE FROM INF_EXPORT ORDER BY DATA_DATE DESC ");
				
			ps = conn.prepareCall(SQL.toString());
			
			rs = ps.executeQuery();	
			
			CacheDataM dataM = null;
			while(rs.next()){
				dataM = new CacheDataM();
				dataM.setThDesc(rs.getString("DATA_DATE"));
				dataM.setCode(rs.getString("DATA_DATE"));
				dateVect.add(dataM);
			}
			
		}catch (Exception e) {
			logger.fatal(e.getLocalizedMessage());
		}finally{
			try{
				closeConnection(conn,rs,ps);
			}catch(Exception e){
				logger.fatal(e.getLocalizedMessage());
			}
		}
		return dateVect;
	}
	@Override
	public Vector<INFExportDataM> getINFExport(String moduleID, String date) {
		Vector<INFExportDataM> exportVect = new Vector<INFExportDataM>();
		PreparedStatement ps = null;
		ResultSet rs = null;
		Connection conn = null;
		try{
			conn = getConnection();
			
			StringBuilder SQL = new StringBuilder();
				SQL.append(" SELECT MODULE_ID, TO_CHAR(DATA_DATE,'yyyyMMddHH24MISS') ID FROM INF_EXPORT ");
				SQL.append(" WHERE MODULE_ID = ? AND TO_CHAR(DATA_DATE ,'yyyyMMdd') = ? ");
				
			ps = conn.prepareCall(SQL.toString());
						
			int index = 1;
			ps.setString(index++, moduleID);
			ps.setString(index++, date);
			
			rs = ps.executeQuery();	
			
			INFExportDataM dataM = null;
			while(rs.next()){
				dataM = new INFExportDataM();
				dataM.setModuleID(rs.getString("MODULE_ID"));
				dataM.setDateID(rs.getString("ID"));
				exportVect.add(dataM);
			}
			
		}catch (Exception e) {
			logger.fatal(e.getLocalizedMessage());
		}finally{
			try{
				closeConnection(conn,rs,ps);
			}catch(Exception e){
				logger.fatal(e.getLocalizedMessage());
			}
		}
		return exportVect;
	}
	
	@Override
	public Blob getBlob(String moduleID, String ID) {
		PreparedStatement ps = null;
		ResultSet rs = null;
		Blob file = null;
		Connection conn = null;
		try{
			conn = getConnection();
			
			StringBuilder SQL = new StringBuilder();
				SQL.append(" SELECT CONTENT FROM INF_EXPORT ");
				SQL.append(" WHERE MODULE_ID = ? AND TO_CHAR(DATA_DATE ,'yyyyMMddHH24MISS') = ? ");
				
			ps = conn.prepareCall(SQL.toString());
						
			int index = 1;
			ps.setString(index++, moduleID);
			ps.setString(index++, ID);
			
			rs = ps.executeQuery();	
			
			if(rs.next()){
				file = rs.getBlob("CONTENT");
			}
			
		}catch (Exception e) {
			logger.fatal(e.getLocalizedMessage());
		}finally{
			try{
				closeConnection(conn,rs,ps);
			}catch(Exception e){
				logger.fatal(e.getLocalizedMessage());
			}
		}
		return file;
	}
}
