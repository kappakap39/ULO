package com.eaf.orig.ulo.pl.app.dao;

import java.io.FileInputStream;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.Vector;
import org.apache.log4j.Logger;
import com.eaf.orig.cache.properties.ORIGCacheDataM;
import com.eaf.orig.shared.dao.OrigObjectDAO;
import com.eaf.orig.shared.model.InfBackupLogM;
import com.eaf.orig.ulo.pl.app.dao.exception.PLOrigApplicationException;

public class ORIGManualImportDAOImpl extends OrigObjectDAO implements ORIGManualImportDAO {
	private static Logger logger = Logger.getLogger(ORIGManualImportDAOImpl.class);

	@Override
	public void processManualImportData(String plSQLfunction, String fileName) throws PLOrigApplicationException {
		PreparedStatement ps = null;
		Connection conn = null;
		try {
			//conn = Get Connection
			conn = getConnection();
			StringBuffer sql = new StringBuffer("");
			sql.append(" CALL p_interface_master(?,?)");
			String dSql = String.valueOf(sql);
			logger.debug("@@@@@ processManualImportData SQL:"+dSql+":plSQLfunction="+plSQLfunction+":fileName="+fileName);
			ps = conn.prepareCall(dSql);
			ps.setString(1,plSQLfunction);
			ps.setString(2,fileName);
			ps.executeQuery();
		} catch (Exception e) {
			e.printStackTrace();
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

	@Override
	public void createInterfaceBackupLog(InfBackupLogM infBackupM, String userName) throws PLOrigApplicationException {
		try {
			int updateRecord = updateInterfaceBackupLog(infBackupM, userName);
			if(updateRecord == 0){
				insertInterfaceBackupLog(infBackupM, userName);
			}
		} catch (Exception e) {
			e.printStackTrace();
			throw new PLOrigApplicationException(e.getMessage());
		} 
	}
	
	private int updateInterfaceBackupLog(InfBackupLogM infBackupM, String userName)throws PLOrigApplicationException {
		PreparedStatement ps = null;
		int resultUpdate = 0;
		Connection conn = null;
		try {
			conn = getConnection();
			StringBuffer sql = new StringBuffer("");
			sql.append("update inf_backup_log set backup_date = sysdate, backup_by = ?");
			sql.append("where module_id = ? and backup_path = ?");
			String dSql = String.valueOf(sql);
			logger.debug("@@@@@ updateInterfaceBackupLog SQL:"+dSql);
			ps = conn.prepareCall(dSql);
			ps.setString(1,userName);
			ps.setString(2,infBackupM.getModuleId());
			ps.setString(3,infBackupM.getBackupPath());
			resultUpdate = ps.executeUpdate();
			
			return resultUpdate;
		} catch (Exception e) {
			e.printStackTrace();
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
	
	private void insertInterfaceBackupLog(InfBackupLogM infBackupM, String userName) throws PLOrigApplicationException {
		PreparedStatement ps = null;
		Connection conn = null;
		try {
			conn = getConnection();
			StringBuffer sql = new StringBuffer("");
			sql.append("insert into inf_backup_log (inf_backup_id,module_id,backup_path,backup_file,backup_date,backup_by) ");
			sql.append("values(inf_backup_id_seq.nextval, ?, ?, ?, sysdate, ?)");
			String dSql = String.valueOf(sql);
			logger.debug("@@@@@ createInterfaceBackupLog SQL:"+dSql);
			ps = conn.prepareCall(dSql);
			ps.setString(1,infBackupM.getModuleId());
			ps.setString(2,infBackupM.getBackupPath());
			ps.setString(3,infBackupM.getBackupFile());
			ps.setString(4,userName);
			ps.executeUpdate();
		} catch (Exception e) {
			e.printStackTrace();
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

	@Override
	public Vector<InfBackupLogM> loadInterfaceBackupLogOverTime(String moduleId, int days) throws PLOrigApplicationException {
		PreparedStatement ps = null;
		ResultSet rs = null;
		Connection conn = null;
		try {
			conn = getConnection();
			StringBuilder sql = new StringBuilder("");
			sql.append("select * from inf_backup_log ib ");
			sql.append("where ib.module_id = ? and ib.backup_date < trunc(sysdate - ?)");
			String dSql = String.valueOf(sql);
			logger.debug("@@@@@ loadInterfaceBackupLogOverTime Sql=" + dSql);
			ps = conn.prepareStatement(dSql);
			rs = null;
			ps.setString(1, moduleId);
			ps.setInt(2, days);

			rs = ps.executeQuery();
			
			Vector<InfBackupLogM> resultInfBackupVT = new Vector<InfBackupLogM>();
			
			if (rs.next()) {
				InfBackupLogM infBackupM = new InfBackupLogM();
				infBackupM.setInfBackupId(rs.getInt("INF_BACKUP_ID"));
				infBackupM.setModuleId(rs.getString("MODULE_ID"));
				infBackupM.setBackupPath(rs.getString("BACKUP_PATH"));
				infBackupM.setBackupFile(rs.getString("BACKUP_FILE"));
				infBackupM.setBackupDate(rs.getTimestamp("BACKUP_DATE"));
				infBackupM.setBackupBy(rs.getString("BACKUP_BY"));
				
				resultInfBackupVT.add(infBackupM);
			}
			
			return resultInfBackupVT;
			
		} catch (Exception e) {
			logger.fatal(e.getLocalizedMessage());
			throw new PLOrigApplicationException(e.getMessage());
		} finally {
			try {
				closeConnection(conn, rs, ps);
			} catch (Exception e) {
				logger.fatal(e.getLocalizedMessage());
				throw new PLOrigApplicationException(e.getMessage());
			}
		}
	}

	@Override
	public void deleteInterfaceBackupLog(int infBackupId) throws PLOrigApplicationException {
		PreparedStatement ps = null;
		Connection conn = null;
		try {
			conn = getConnection();
			StringBuffer sql = new StringBuffer("");
			sql.append("delete from inf_backup_log ib where ib.inf_backup_id = ?");
			String dSql = String.valueOf(sql);
			logger.debug("@@@@@ deleteInterfaceBackupLog SQL:"+dSql);
			ps = conn.prepareCall(dSql);
			ps.setInt(1,infBackupId);
			ps.executeUpdate();
		} catch (Exception e) {
			e.printStackTrace();
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
	@Override
	public void deleteInterfaceBackupLog(ORIGCacheDataM infCacheM) throws PLOrigApplicationException{
	PreparedStatement ps = null;
	Connection conn = null;
	try {
		conn = getConnection();
		StringBuffer sql = new StringBuffer("");
		sql.append("delete from inf_backup_log ib where ib.module_id = ? and ib.backup_date > trim(sysdate)");
		String dSql = String.valueOf(sql);
		logger.debug("@@@@@ deleteInterfaceBackupLog SQL:"+dSql +"|"+infCacheM.getListBoxID());
		ps = conn.prepareCall(dSql);
		ps.setString(1,infCacheM.getListBoxID());
		ps.executeUpdate();
	} catch (Exception e) {
		e.printStackTrace();
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

	@Override
	public void createBLobInfImport(String fileFullName, String moduleId, String userName)throws PLOrigApplicationException {
		PreparedStatement pstmt = null;
		InputStream input = null;
		Connection conn = null;
		try{
			input = new FileInputStream(fileFullName);
			
			conn = getConnection();
			StringBuffer sql = new StringBuffer("");
			sql.append("update inf_import set content = ?, update_date = sysdate, update_by = ? ");
			sql.append("where module_id = ?");
			String dSql = String.valueOf(sql);
			
			logger.debug("@@@@@ createBLobInfImport SQL:"+dSql);
			pstmt = conn.prepareStatement(dSql);
			pstmt.setBinaryStream(1, input);
			pstmt.setString(2, userName);
			pstmt.setString(3, moduleId);
			
			pstmt.execute();
			
		}catch(Exception e){
			logger.fatal("##### createBLobInfImport error :" + e.getMessage());
			throw new PLOrigApplicationException(e.getMessage());
		}finally {
			try {
				if(input != null)input.close();
				closeConnection(conn, pstmt);
			} catch (Exception e) {
				logger.fatal(e.getLocalizedMessage());
				throw new PLOrigApplicationException(e.getMessage());
			}
		}
	}
	
	@Override
	public void clearBLobInfImport(String moduleId, String userName)throws PLOrigApplicationException {
		PreparedStatement pstmt = null;
		InputStream input = null;
		Connection conn = null;
		try{
			
			conn = getConnection();
			StringBuffer sql = new StringBuffer("");
			sql.append("update inf_import set content = null, update_date = sysdate, update_by = ? ");
			sql.append("where module_id = ?");
			String dSql = String.valueOf(sql);
			
			logger.debug("@@@@@ createBLobInfImport SQL:"+dSql);
			pstmt = conn.prepareStatement(dSql);
			pstmt.setString(1, userName);
			pstmt.setString(2, moduleId);
			
			pstmt.execute();
			
		}catch(Exception e){
			logger.fatal("##### createBLobInfImport error :" + e.getMessage());
			throw new PLOrigApplicationException(e.getMessage());
		}finally {
			try {
				if(input != null)input.close();
				closeConnection(conn, pstmt);
			} catch (Exception e) {
				logger.fatal(e.getLocalizedMessage());
				throw new PLOrigApplicationException(e.getMessage());
			}
		}
	}
}
