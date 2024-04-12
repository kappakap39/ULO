package com.eaf.orig.ulo.app.view.util.inf_export;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;

import org.apache.log4j.Logger;

import com.eaf.core.ulo.common.properties.SystemConstant;
import com.eaf.orig.shared.dao.OrigObjectDAO;
import com.eaf.orig.ulo.app.dao.exception.ApplicationException;
import com.eaf.orig.ulo.app.view.util.inf_export.model.InfExportDataM;

public class infExportDAOImpl extends OrigObjectDAO implements infExportDAO{
	private static transient Logger logger = Logger.getLogger(infExportDAOImpl.class);
	String CIDTYPE_IDCARD = SystemConstant.getConstant("CIDTYPE_IDCARD");
	@Override
	public ArrayList<InfExportDataM> getMODULE_ID(String MODULE_ID  , String DATE_NOW ) throws ApplicationException {
		PreparedStatement ps = null;
		ResultSet rs = null;
		Connection conn = null;
		ArrayList<InfExportDataM> resultList = new ArrayList<InfExportDataM>();
		try {
//			conn = getConnection(OrigServiceLocator.ORIG_DB);
			conn = getConnection();
			StringBuilder SQL = new StringBuilder();
			SQL.append(" SELECT * FROM INF_EXPORT ");
			SQL.append(" WHERE MODULE_ID = ? ");
			SQL.append(" AND DATA_DATE like  to_date(?,'YYYY-MM-DD') ");
			logger.debug("SQL >> "+SQL);
			ps = conn.prepareStatement(SQL.toString());
			int index = 1;
			ps.setString(index++,MODULE_ID);
			ps.setString(index++,DATE_NOW);
			rs = ps.executeQuery();
				
			while(rs.next()) {
				InfExportDataM InfExport = new InfExportDataM();			
				InfExport.setModuleId(rs.getString("MODULE_ID"));
				InfExport.setDataDate(rs.getDate("DATA_DATE"));
				InfExport.setFileName(rs.getString("FILE_NAME"));
				InfExport.setContent(rs.getClob("CONTENT"));
				InfExport.setUpdateDate(rs.getDate("UPDATE_DATE"));
				InfExport.setUpdateBy(rs.getString("UPDATE_BY"));
			    
				resultList.add(InfExport);
					
			}
		} catch (Exception e) {
			logger.fatal(e.getLocalizedMessage());
			throw new ApplicationException(e.getMessage());
		} finally {
			try {
				closeConnection(conn, rs, ps);
			} catch (Exception e) {
				logger.fatal(e.getLocalizedMessage());
			}
		}
		return resultList;
	}
	public void createTableINF_EXPORT(InfExportDataM InfExportM) throws ApplicationException {
		PreparedStatement ps = null;
		Connection conn = null;
		try {
			//conn = Get Connection
			conn = getConnection();
			StringBuffer sql = new StringBuffer("");
			sql.append(" INSERT INTO INF_EXPORT ");
			sql.append("(EXPORT_ID,MODULE_ID, DATA_DATE, UPDATE_DATE, UPDATE_BY) ");

			sql.append(" VALUES(INF_EXPORT_PK.NEXTVAL,?,sysdate,sysdate,?)");
			String dSql = String.valueOf(sql);
			logger.debug("Sql=" + dSql);
			ps = conn.prepareStatement(dSql);
			int cnt = 1;
			ps.setString(cnt++, InfExportM.getModuleId());
			ps.setString(cnt++, InfExportM.getUpdateBy());
			
			ps.executeUpdate();
		} catch (Exception e) {
			logger.fatal(e.getLocalizedMessage());
			throw new ApplicationException(e.getMessage());
		} finally {
			try {
				closeConnection(conn, ps);
			} catch (Exception e) {
				logger.fatal(e.getLocalizedMessage());
			}
		}
	}
}
