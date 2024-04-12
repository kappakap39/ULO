package com.eaf.orig.ulo.pl.app.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import org.apache.log4j.Logger;
import com.eaf.orig.shared.constant.OrigConstant;
import com.eaf.orig.shared.dao.OrigObjectDAO;
import com.eaf.orig.ulo.pl.app.dao.exception.PLOrigApplicationException;

public class PLOrigAddressDAOImpl extends OrigObjectDAO implements	PLOrigAddressDAO {
	private final Logger logger = Logger.getLogger(PLOrigAddressDAOImpl.class);
	@Override
	public String getProvince(String provinceCode) throws PLOrigApplicationException {
		PreparedStatement ps = null;
		ResultSet rs = null;
		String provinceDesc = "0"; //default to 0 (that mean not found record)
		int cnt = 0;
		Connection conn = null;
		try {
			conn = getConnection();
			StringBuilder sql = new StringBuilder("");
			sql.append("SELECT PROVINCE_DESC ");
			sql.append("FROM MS_PROVINCE ");
			sql.append("WHERE PROVINCE_ID like ? AND ACTIVE_STATUS= ? ");
			sql.append("ORDER BY PROVINCE_ID ");
			String dSql = String.valueOf(sql);
			logger.debug("Sql=" + dSql);
			ps = conn.prepareStatement(dSql);
			rs = null;
			
			ps.setString(1, provinceCode + "%");
			ps.setString(2, OrigConstant.Status.STATUS_ACTIVE);

			rs = ps.executeQuery();
			
			while (rs.next()) {
				cnt ++;
				provinceDesc = rs.getString(1);
				if(cnt > 1){
					return "1"; //return 1 (that mean found more than one record)
				}
			}
			
			return provinceDesc;
			
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
	public String getDistrict(String DistrictCode, String optionalCase) throws PLOrigApplicationException {
		PreparedStatement ps = null;
		ResultSet rs = null;
		String districtDesc = "0"; //default to 0 (that mean not found record)
		int cnt = 0;
		Connection conn = null;
		try {
			conn = getConnection();
			StringBuilder sql = new StringBuilder("");
			sql.append("SELECT DISTRICT_DESC ");
			sql.append("FROM MS_DISTRICT ");
			sql.append("WHERE DISTRICT_ID like ? ");
			sql.append("AND PROVINCE_ID = ? ");
			sql.append("ORDER BY DISTRICT_ID ");
			String dSql = String.valueOf(sql);
			logger.debug("Sql=" + dSql);
			ps = conn.prepareStatement(dSql);
			rs = null;
			
			ps.setString(1, DistrictCode + "%");
			ps.setString(2, optionalCase);
			
			rs = ps.executeQuery();
			
			while (rs.next()) {
				cnt ++;
				districtDesc = rs.getString(1);
				if(cnt > 1){
					return "1"; //return 1 (that mean found more than one record)
				}
			}
			
			return districtDesc;
			
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
	public String getSubDistrict(String SubDistrictCode, String optionalCase) throws PLOrigApplicationException {
		PreparedStatement ps = null;
		ResultSet rs = null;
		String subDistrictDesc = "0"; //default to 0 (that mean not found record)
		int cnt = 0;
		Connection conn = null;
		try {
			conn = getConnection();
			StringBuilder sql = new StringBuilder("");
			sql.append("SELECT SUB_DISTRICT_DESC ");
			sql.append("FROM MS_SUB_DISTRICT ");
			sql.append("WHERE SUB_DISTRICT_ID like ? ");
			sql.append("AND DISTRICT_ID = ? ");
			sql.append("ORDER BY SUB_DISTRICT_ID ");
			String dSql = String.valueOf(sql);
			logger.debug("Sql=" + dSql);
			ps = conn.prepareStatement(dSql);
			rs = null;
			
			ps.setString(1, SubDistrictCode + "%");
			ps.setString(2, optionalCase);
			rs = ps.executeQuery();
			
			while (rs.next()) {
				cnt ++;
				subDistrictDesc = rs.getString(1);
				if(cnt > 1){
					return "1"; //return 1 (that mean found more than one record)
				}
			}
			
			return subDistrictDesc;
			
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
	public String getZipcode(String zipCode) throws PLOrigApplicationException {
		PreparedStatement ps = null;
		ResultSet rs = null;
		int recordCount = 0;
		Connection conn = null;
		try {
			conn = getConnection();
			StringBuilder sql = new StringBuilder("");
			sql.append(" select count(*) cnt from ms_zipcode z where z.zipcode like '"+zipCode+"%' and z.active_status = ?");
			String dSql = String.valueOf(sql);
			logger.debug("Sql=" + dSql);
			ps = conn.prepareStatement(dSql);
			rs = null;
			
			ps.setString(1, OrigConstant.Status.STATUS_ACTIVE);

			rs = ps.executeQuery();
			if (rs.next()) {
				recordCount = rs.getInt("cnt");
			}

			if(recordCount > 0){
				return "1"; //return found
			}else{
				return "0"; //return not found
			}
			
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
	public String getSubDistrictID(String subDistrictDesc, String districtId)throws PLOrigApplicationException {
		PreparedStatement ps = null;
		ResultSet rs = null;
		String subDistrictID = null;
		Connection conn = null;
		try {
			conn = getConnection();
			StringBuilder sql = new StringBuilder("");
			sql.append("SELECT sub_district_id ");
			sql.append("FROM MS_SUB_DISTRICT ");
			sql.append("WHERE sub_district_desc = ? ");
			sql.append("AND DISTRICT_ID = ? ");
			sql.append("ORDER BY SUB_DISTRICT_ID ");
			String dSql = String.valueOf(sql);
			logger.debug("getSubDistrictCode Sql=" + dSql);
			ps = conn.prepareStatement(dSql);
			rs = null;
			
			ps.setString(1, subDistrictDesc);
			ps.setString(2, districtId);
			rs = ps.executeQuery();
			
			if (rs.next()) {
				subDistrictID = rs.getString(1);
			}
			return subDistrictID;
			
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
	public String getDistrictID(String districtDesc, String provinceId)throws PLOrigApplicationException {
		PreparedStatement ps = null;
		ResultSet rs = null;
		String districtID = null;
		Connection conn = null;
		try {
			conn = getConnection();
			StringBuilder sql = new StringBuilder("");
			sql.append("SELECT district_id ");
			sql.append("FROM MS_DISTRICT ");
			sql.append("WHERE DISTRICT_DESC = ? ");
			sql.append("AND PROVINCE_ID = ? ");
			sql.append("ORDER BY DISTRICT_ID ");
			String dSql = String.valueOf(sql);
			logger.debug("getDistrictID Sql=" + dSql);
			ps = conn.prepareStatement(dSql);
			rs = null;
			
			ps.setString(1, districtDesc);
			ps.setString(2, provinceId);
			
			rs = ps.executeQuery();
			
			while (rs.next()) {
				districtID = rs.getString(1);
			}
			return districtID;
			
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
	public String getProvinceID(String provinceDesc)throws PLOrigApplicationException {
		PreparedStatement ps = null;
		ResultSet rs = null;
		String provinceID = null;
		Connection conn = null;
		try {
			conn = getConnection();
			StringBuilder sql = new StringBuilder("");
			sql.append("SELECT PROVINCE_ID ");
			sql.append("FROM MS_PROVINCE ");
			sql.append("WHERE PROVINCE_DESC = ? AND ACTIVE_STATUS= ? ");
			sql.append("ORDER BY PROVINCE_ID ");
			String dSql = String.valueOf(sql);
			logger.debug("getProvinceID Sql=" + dSql);
			ps = conn.prepareStatement(dSql);
			rs = null;
			
			ps.setString(1, provinceDesc);
			ps.setString(2, OrigConstant.Status.STATUS_ACTIVE);

			rs = ps.executeQuery();
			
			while (rs.next()) {
				provinceID = rs.getString(1);
			}
			return provinceID;
			
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

}
