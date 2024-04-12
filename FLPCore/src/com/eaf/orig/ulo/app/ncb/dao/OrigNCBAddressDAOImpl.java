package com.eaf.orig.ulo.app.ncb.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;

import com.eaf.core.ulo.common.date.ApplicationDate;
import com.eaf.core.ulo.common.util.Util;
import com.eaf.orig.shared.dao.OrigObjectDAO;
import com.eaf.orig.ulo.app.dao.exception.ApplicationException;
import com.eaf.orig.ulo.model.ncb.NcbAddressDataM;

public class OrigNCBAddressDAOImpl extends OrigObjectDAO implements
		OrigNCBAddressDAO {
	public OrigNCBAddressDAOImpl(String userId){
		this.userId = userId;
	}
	public OrigNCBAddressDAOImpl(){
		
	}
	private String userId = "";
	@Override
	public void createOrigNcbAddressM(NcbAddressDataM ncbAddressM)
			throws ApplicationException {
		try {
			ncbAddressM.setCreateBy(userId);
			ncbAddressM.setUpdateBy(userId);
			createTableNCB_ADDRESS(ncbAddressM);
		} catch (Exception e) {
			logger.fatal(e.getLocalizedMessage());
			throw new ApplicationException(e.getMessage());
		}
	}

	private void createTableNCB_ADDRESS(NcbAddressDataM ncbAddressM) throws ApplicationException {
		PreparedStatement ps = null;
		Connection conn = null;
		try {
			//conn = Get Connection
			conn = getConnection();
			StringBuffer sql = new StringBuffer("");
			sql.append("INSERT INTO NCB_ADDRESS ");
			sql.append("( TRACKING_CODE, SEQ, GROUP_SEQ, SEGMENT_VALUE, ");
			sql.append(" ADDRESS_LINE1, ADDRESS_LINE2, ADDRESS_LINE3, ");
			sql.append(" SUBDISTRICT, DISTRICT, PROVINCE, COUNTRY, ");
			sql.append(" POSTAL_CODE, TELEPHONE, TELEPHONE_TYPE, ");
			sql.append(" ADDRESS_TYPE, RESIDENTIAL_STATUS, REPORTED_DATE, ");
			sql.append(" CREATE_DATE, CREATE_BY, UPDATE_DATE, UPDATE_BY) ");

			sql.append(" VALUES(?,?,?,?,  ?,?,?,  ?,?,?,?,  ?,?,?,  ?,?,?,  ");
			sql.append("?,?,?,? )");
			String dSql = String.valueOf(sql);
			logger.debug("Sql=" + dSql);
			ps = conn.prepareStatement(dSql);
			int cnt = 1;
			ps.setString(cnt++, ncbAddressM.getTrackingCode());
			ps.setInt(cnt++, ncbAddressM.getSeq());
			ps.setInt(cnt++, ncbAddressM.getGroupSeq());
			ps.setString(cnt++, ncbAddressM.getSegmentValue());
			
			ps.setString(cnt++, ncbAddressM.getAddressLine1());
			ps.setString(cnt++, ncbAddressM.getAddressLine2());
			ps.setString(cnt++, ncbAddressM.getAddressLine3());
			
			ps.setString(cnt++, ncbAddressM.getSubdistrict());
			ps.setString(cnt++, ncbAddressM.getDistrict());
			ps.setString(cnt++, ncbAddressM.getProvince());
			ps.setString(cnt++, ncbAddressM.getCountry());
			
			ps.setString(cnt++, ncbAddressM.getPostalCode());
			ps.setString(cnt++, ncbAddressM.getTelephone());
			ps.setString(cnt++, ncbAddressM.getTelephoneType());
			
			ps.setString(cnt++, ncbAddressM.getAddressType());
			ps.setString(cnt++, ncbAddressM.getResidentialStatus());
			ps.setDate(cnt++, ncbAddressM.getReportedDate());
			
			ps.setTimestamp(cnt++, ApplicationDate.getTimestamp());
			ps.setString(cnt++, ncbAddressM.getCreateBy());
			ps.setTimestamp(cnt++, ApplicationDate.getTimestamp());
			ps.setString(cnt++, ncbAddressM.getUpdateBy());
			
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

	@Override
	public void deleteOrigNcbAddressM(String trackingCode, int seq)
			throws ApplicationException {
		deleteTableNCB_ADDRESS(trackingCode, seq);
	}

	private void deleteTableNCB_ADDRESS(String trackingCode, int seq) throws ApplicationException {
		PreparedStatement ps = null;
		Connection conn = null;
		try {
			//conn = Get Connection
			conn = getConnection();
			StringBuffer sql = new StringBuffer("");
			sql.append("DELETE NCB_ADDRESS ");
			sql.append(" WHERE TRACKING_CODE = ?");
			if(seq != 0) {
				sql.append(" AND SEQ = ? ");
			}
			String dSql = String.valueOf(sql);
			logger.debug("Sql=" + dSql);
			ps = conn.prepareStatement(dSql);
			ps.setString(1, trackingCode);
			if(seq != 0) {
				ps.setInt(2, seq);
			}
			ps.executeUpdate();

		} catch (Exception e) {
			logger.fatal(e.getLocalizedMessage());
			throw new ApplicationException(e.getMessage());
		} finally {
			try {
				closeConnection(conn, ps);
			} catch (Exception e) {
				logger.fatal(e.getLocalizedMessage());
				throw new ApplicationException(e.getMessage());
			}
		}
	}

	@Override
	public ArrayList<NcbAddressDataM> loadOrigNcbAddressM(String trackingCode)
			throws ApplicationException {
		Connection conn = null;
		try{
			conn = getConnection();
			return selectTableNCB_ADDRESS(trackingCode,conn);
		}catch (Exception e) {
			logger.fatal(e.getLocalizedMessage());
			throw new ApplicationException(e.getMessage());
		} finally {
			try {
				closeConnection(conn);
			} catch (Exception e) {
				logger.fatal(e.getLocalizedMessage());
				throw new ApplicationException(e.getMessage());
			}
		}
		
	}
	
	@Override
	public ArrayList<NcbAddressDataM> loadOrigNcbAddressM(String trackingCode,
			Connection conn) throws ApplicationException {
		return selectTableNCB_ADDRESS(trackingCode, conn);
	}
	
	private ArrayList<NcbAddressDataM> selectTableNCB_ADDRESS(
			String trackingCode,Connection conn) throws ApplicationException {
		PreparedStatement ps = null;
		ResultSet rs = null;
		ArrayList<NcbAddressDataM> ncbAddressList = new ArrayList<NcbAddressDataM>();
		try {
			StringBuffer sql = new StringBuffer("");
			sql.append("SELECT TRACKING_CODE, SEQ, GROUP_SEQ, SEGMENT_VALUE, ");
			sql.append(" ADDRESS_LINE1, ADDRESS_LINE2, ADDRESS_LINE3, ");
			sql.append(" SUBDISTRICT, DISTRICT, PROVINCE, COUNTRY, ");
			sql.append(" POSTAL_CODE, TELEPHONE, TELEPHONE_TYPE, ");
			sql.append(" ADDRESS_TYPE, RESIDENTIAL_STATUS, REPORTED_DATE, ");
			sql.append(" CREATE_DATE, CREATE_BY, UPDATE_DATE, UPDATE_BY ");
			sql.append(" FROM NCB_ADDRESS WHERE TRACKING_CODE = ? ");
			String dSql = String.valueOf(sql);
			logger.debug("Sql=" + dSql);
			ps = conn.prepareStatement(dSql);
			rs = null;
			ps.setString(1, trackingCode);

			rs = ps.executeQuery();

			while(rs.next()) {
				NcbAddressDataM ncbAddressM = new NcbAddressDataM();
				ncbAddressM.setTrackingCode(rs.getString("TRACKING_CODE"));
				ncbAddressM.setSeq(rs.getInt("SEQ"));
				ncbAddressM.setGroupSeq(rs.getInt("GROUP_SEQ"));
				ncbAddressM.setSegmentValue(rs.getString("SEGMENT_VALUE"));
				
				ncbAddressM.setAddressLine1(rs.getString("ADDRESS_LINE1"));
				ncbAddressM.setAddressLine2(rs.getString("ADDRESS_LINE2"));
				ncbAddressM.setAddressLine3(rs.getString("ADDRESS_LINE3"));
				
				ncbAddressM.setSubdistrict(rs.getString("SUBDISTRICT"));
				ncbAddressM.setDistrict(rs.getString("DISTRICT"));
				ncbAddressM.setProvince(rs.getString("PROVINCE"));
				ncbAddressM.setCountry(rs.getString("COUNTRY"));
				
				ncbAddressM.setPostalCode(rs.getString("POSTAL_CODE"));
				ncbAddressM.setTelephone(rs.getString("TELEPHONE"));
				ncbAddressM.setTelephoneType(rs.getString("TELEPHONE_TYPE"));
				
				ncbAddressM.setAddressType(rs.getString("ADDRESS_TYPE"));
				ncbAddressM.setResidentialStatus(rs.getString("RESIDENTIAL_STATUS"));
				ncbAddressM.setReportedDate(rs.getDate("REPORTED_DATE"));
				
				ncbAddressM.setCreateBy(rs.getString("CREATE_BY"));
				ncbAddressM.setCreateDate(rs.getTimestamp("CREATE_DATE"));
				ncbAddressM.setUpdateBy(rs.getString("UPDATE_BY"));
				ncbAddressM.setUpdateDate(rs.getTimestamp("UPDATE_DATE"));
				ncbAddressList.add(ncbAddressM);
			}

			return ncbAddressList;
		} catch (Exception e) {
			logger.fatal(e.getLocalizedMessage());
			throw new ApplicationException(e.getMessage());
		} finally {
			try {
				closeConnection(rs, ps);
			} catch (Exception e) {
				logger.fatal(e.getLocalizedMessage());
			}
		}
	}

	@Override
	public void saveUpdateOrigNcbAddressM(NcbAddressDataM ncbAddressM)
			throws ApplicationException {
		int returnRows = 0;
		ncbAddressM.setUpdateBy(userId);
		returnRows = updateTableNCB_ADDRESS(ncbAddressM);
		if (returnRows == 0) {
			logger.debug("New record then can't update record in table NCB_ADDRESS then call Insert method");
			ncbAddressM.setCreateBy(userId);
			ncbAddressM.setUpdateBy(userId);
			createOrigNcbAddressM(ncbAddressM);
		}
	}

	private int updateTableNCB_ADDRESS(NcbAddressDataM ncbAddressM) throws ApplicationException {
		int returnRows = 0;
		PreparedStatement ps = null;
		Connection conn = null;
		try {
			//conn = Get Connection
			conn = getConnection();
			StringBuffer sql = new StringBuffer("");
			sql.append("UPDATE NCB_ADDRESS ");
			sql.append(" SET GROUP_SEQ = ?, SEGMENT_VALUE = ?, ");
			sql.append(" ADDRESS_LINE1 = ?, ADDRESS_LINE2 = ?, ADDRESS_LINE3 = ?, ");
			sql.append(" SUBDISTRICT = ?, DISTRICT = ?, PROVINCE = ?, COUNTRY = ?, ");
			sql.append(" POSTAL_CODE = ?, TELEPHONE = ?, TELEPHONE_TYPE = ?, ");
			sql.append(" ADDRESS_TYPE = ?, RESIDENTIAL_STATUS = ?, REPORTED_DATE = ?, ");
			sql.append(" UPDATE_BY = ?, UPDATE_DATE = ? ");			
			sql.append(" WHERE TRACKING_CODE = ? AND SEQ = ?");
			String dSql = String.valueOf(sql);
			logger.debug("Sql=" + dSql);
			ps = conn.prepareStatement(dSql);
			int cnt = 1;
			ps.setInt(cnt++, ncbAddressM.getGroupSeq());
			ps.setString(cnt++, ncbAddressM.getSegmentValue());
			
			ps.setString(cnt++, ncbAddressM.getAddressLine1());
			ps.setString(cnt++, ncbAddressM.getAddressLine2());
			ps.setString(cnt++, ncbAddressM.getAddressLine3());
			
			ps.setString(cnt++, ncbAddressM.getSubdistrict());
			ps.setString(cnt++, ncbAddressM.getDistrict());
			ps.setString(cnt++, ncbAddressM.getProvince());
			ps.setString(cnt++, ncbAddressM.getCountry());
			
			ps.setString(cnt++, ncbAddressM.getPostalCode());
			ps.setString(cnt++, ncbAddressM.getTelephone());
			ps.setString(cnt++, ncbAddressM.getTelephoneType());
			
			ps.setString(cnt++, ncbAddressM.getAddressType());
			ps.setString(cnt++, ncbAddressM.getResidentialStatus());
			ps.setDate(cnt++, ncbAddressM.getReportedDate());
			
			ps.setString(cnt++, ncbAddressM.getUpdateBy());
			ps.setTimestamp(cnt++, ApplicationDate.getTimestamp());
			// WHERE CLAUSE
			ps.setString(cnt++, ncbAddressM.getTrackingCode());
			ps.setInt(cnt++, ncbAddressM.getSeq());
			
			returnRows = ps.executeUpdate();
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
		return returnRows;
	}

	@Override
	public void deleteNotInKeyNcbAddress(
			ArrayList<NcbAddressDataM> ncbAddressList, String trackingCode)
			throws ApplicationException {
		deleteNotInKeyNCB_ADDRESS(ncbAddressList, trackingCode);
	}

	private void deleteNotInKeyNCB_ADDRESS(
			ArrayList<NcbAddressDataM> ncbAddressList, String trackingCode) throws ApplicationException {
		PreparedStatement ps = null;
		Connection conn = null;
		try {
            // conn = Get Connection
            conn = getConnection();

            StringBuffer sql = new StringBuffer("");
            sql.append("DELETE FROM NCB_ADDRESS ");
            sql.append(" WHERE TRACKING_CODE = ? ");
            
            if (!Util.empty(ncbAddressList)) {
                sql.append(" AND SEQ NOT IN ( ");
                for (NcbAddressDataM ncbAddressM: ncbAddressList) {
                	logger.debug("ncbAddressM.getSeq() = "+ncbAddressM.getSeq());
                    sql.append(" " + ncbAddressM.getSeq() + ", ");
                }
                
                if (sql.toString().trim().endsWith(",")) {
                    sql.delete(sql.toString().lastIndexOf(","), sql.toString().length());
                }

                sql.append(" ) ");
            }

            String dSql = String.valueOf(sql);
            ps = conn.prepareStatement(dSql);
            ps.setString(1, trackingCode);
            
            ps.executeUpdate();
            ps.close();

        } catch (Exception e) {
        	logger.error("ERROR >> ",e);
			throw new ApplicationException(e.getMessage());
		} finally {
			try {
				closeConnection(conn, ps);
			} catch (Exception e) {
				logger.error("ERROR >> ",e);
				throw new ApplicationException(e.getMessage());
			}
		}
	}

}
