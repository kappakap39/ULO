package com.eaf.orig.ulo.app.ncb.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;

import com.eaf.core.ulo.common.date.ApplicationDate;
import com.eaf.core.ulo.common.util.Util;
import com.eaf.orig.shared.dao.OrigObjectDAO;
import com.eaf.orig.ulo.app.dao.exception.ApplicationException;
import com.eaf.orig.ulo.model.ncb.NcbNameDataM;

public class OrigNCBNameDAOImpl extends OrigObjectDAO implements OrigNCBNameDAO {
	public OrigNCBNameDAOImpl(String userId){
		this.userId = userId;
	}
	public OrigNCBNameDAOImpl(){
		
	}
	private String userId = "";
	@Override
	public void createOrigNcbNameM(NcbNameDataM ncbNameM)
			throws ApplicationException {
		try {
			ncbNameM.setCreateBy(userId);
			ncbNameM.setUpdateBy(userId);
			createTableNCB_NAME(ncbNameM);
		} catch (Exception e) {
			logger.fatal(e.getLocalizedMessage());
			throw new ApplicationException(e.getMessage());
		}
	}

	private void createTableNCB_NAME(NcbNameDataM ncbNameM) throws ApplicationException {
		PreparedStatement ps = null;
		Connection conn = null;
		try {
			//conn = Get Connection
			conn = getConnection();
			StringBuffer sql = new StringBuffer("");
			sql.append("INSERT INTO NCB_NAME ");
			sql.append("( TRACKING_CODE, SEQ, GROUP_SEQ, SEGMENT_VALUE, ");
			sql.append(" FAMILY_NAME1, FAMILY_NAME2, FIRST_NAME, MIDDLE_NAME, ");
			sql.append(" MARITAL_STATUS, DATE_OF_BIRTH, GENDER, TITLE, NATIONALITY, ");
			sql.append(" NUMBER_OF_CHILDREN, SPOUSE_NAME, OCCUPATION, CONSENT_TO_ENQUIRE, ");
			sql.append(" CREATE_DATE, CREATE_BY, UPDATE_DATE, UPDATE_BY) ");

			sql.append(" VALUES(?,?,?,?,  ?,?,?,?,  ?,?,?,?,?,  ?,?,?,?,  ");
			sql.append(" ?,?,?,? )");
			String dSql = String.valueOf(sql);
			logger.debug("Sql=" + dSql);
			ps = conn.prepareStatement(dSql);
			int cnt = 1;
			ps.setString(cnt++, ncbNameM.getTrackingCode());
			ps.setInt(cnt++, ncbNameM.getSeq());
			ps.setInt(cnt++, ncbNameM.getGroupSeq());
			ps.setString(cnt++, ncbNameM.getSegmentValue());
			
			ps.setString(cnt++, ncbNameM.getFamilyName1());
			ps.setString(cnt++, ncbNameM.getFamilyName2());
			ps.setString(cnt++, ncbNameM.getFirstName());
			ps.setString(cnt++, ncbNameM.getMiddleName());
			
			ps.setString(cnt++, ncbNameM.getMaritalStatus());
			ps.setDate(cnt++, ncbNameM.getDateOfBirth());
			ps.setInt(cnt++, ncbNameM.getGender());
			ps.setString(cnt++, ncbNameM.getTitle());
			ps.setString(cnt++, ncbNameM.getNationality());
			
			ps.setInt(cnt++, ncbNameM.getNumberOfChildren());
			ps.setString(cnt++, ncbNameM.getSpouseName());
			ps.setString(cnt++, ncbNameM.getOccupation());
			ps.setString(cnt++, ncbNameM.getConsentToEnquire());
			
			ps.setTimestamp(cnt++, ApplicationDate.getTimestamp());
			ps.setString(cnt++, ncbNameM.getCreateBy());
			ps.setTimestamp(cnt++, ApplicationDate.getTimestamp());
			ps.setString(cnt++, ncbNameM.getUpdateBy());
			
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
	public void deleteOrigNcbNameM(String trackingCode, int seq)
			throws ApplicationException {
		deleteTableNCB_NAME(trackingCode, seq);
	}

	private void deleteTableNCB_NAME(String trackingCode, int seq) throws ApplicationException {
		PreparedStatement ps = null;
		Connection conn = null;
		try {
			//conn = Get Connection
			conn = getConnection();
			StringBuffer sql = new StringBuffer("");
			sql.append("DELETE NCB_NAME ");
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
	public ArrayList<NcbNameDataM> loadOrigNcbNameM(String trackingCode)
			throws ApplicationException {
		Connection conn = null;
		try{
			conn = getConnection();
			return selectTableNCB_NAME(trackingCode,conn);
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
	public ArrayList<NcbNameDataM> loadOrigNcbNameM(String trackingCode,
			Connection conn) throws ApplicationException {
		return selectTableNCB_NAME(trackingCode, conn);
	}
	
	private ArrayList<NcbNameDataM> selectTableNCB_NAME(String trackingCode,Connection conn) throws ApplicationException {
		PreparedStatement ps = null;
		ResultSet rs = null;
		ArrayList<NcbNameDataM> ncbNameList = new ArrayList<NcbNameDataM>();
		try {
			StringBuffer sql = new StringBuffer("");
			sql.append("SELECT TRACKING_CODE, SEQ, GROUP_SEQ, SEGMENT_VALUE, ");
			sql.append(" FAMILY_NAME1, FAMILY_NAME2, FIRST_NAME, MIDDLE_NAME, ");
			sql.append(" MARITAL_STATUS, DATE_OF_BIRTH, GENDER, TITLE, NATIONALITY, ");
			sql.append(" NUMBER_OF_CHILDREN, SPOUSE_NAME, OCCUPATION, CONSENT_TO_ENQUIRE, ");
			sql.append(" CREATE_DATE, CREATE_BY, UPDATE_DATE, UPDATE_BY ");
			sql.append(" FROM NCB_NAME WHERE TRACKING_CODE = ? ");
			String dSql = String.valueOf(sql);
			logger.debug("Sql=" + dSql);
			ps = conn.prepareStatement(dSql);
			rs = null;
			ps.setString(1, trackingCode);

			rs = ps.executeQuery();

			while(rs.next()) {
				NcbNameDataM ncbNameM = new NcbNameDataM();
				ncbNameM.setTrackingCode(rs.getString("TRACKING_CODE"));
				ncbNameM.setSeq(rs.getInt("SEQ"));
				ncbNameM.setGroupSeq(rs.getInt("GROUP_SEQ"));
				ncbNameM.setSegmentValue(rs.getString("SEGMENT_VALUE"));
				
				ncbNameM.setFamilyName1(rs.getString("FAMILY_NAME1"));
				ncbNameM.setFamilyName2(rs.getString("FAMILY_NAME2"));
				ncbNameM.setFirstName(rs.getString("FIRST_NAME"));
				ncbNameM.setMiddleName(rs.getString("MIDDLE_NAME"));
				
				ncbNameM.setMaritalStatus(rs.getString("MARITAL_STATUS"));
				ncbNameM.setDateOfBirth(rs.getDate("DATE_OF_BIRTH"));
				ncbNameM.setGender(rs.getInt("GENDER"));
				ncbNameM.setTitle(rs.getString("TITLE"));
				ncbNameM.setNationality(rs.getString("NATIONALITY"));
				
				ncbNameM.setNumberOfChildren(rs.getInt("NUMBER_OF_CHILDREN"));
				ncbNameM.setSpouseName(rs.getString("SPOUSE_NAME"));
				ncbNameM.setOccupation(rs.getString("OCCUPATION"));
				ncbNameM.setConsentToEnquire(rs.getString("CONSENT_TO_ENQUIRE"));
				
				ncbNameM.setCreateBy(rs.getString("CREATE_BY"));
				ncbNameM.setCreateDate(rs.getTimestamp("CREATE_DATE"));
				ncbNameM.setUpdateBy(rs.getString("UPDATE_BY"));
				ncbNameM.setUpdateDate(rs.getTimestamp("UPDATE_DATE"));
				ncbNameList.add(ncbNameM);
			}

			return ncbNameList;
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
	public void saveUpdateOrigNcbNameM(NcbNameDataM ncbNameM)
			throws ApplicationException {
		int returnRows = 0;
		ncbNameM.setUpdateBy(userId);
		returnRows = updateTableNCB_NAME(ncbNameM);
		if (returnRows == 0) {
			ncbNameM.setCreateBy(userId);
			ncbNameM.setUpdateBy(userId);
			logger.debug("New record then can't update record in table NCB_NAME then call Insert method");
			createOrigNcbNameM(ncbNameM);
		}
	}

	private int updateTableNCB_NAME(NcbNameDataM ncbNameM) throws ApplicationException {
		int returnRows = 0;
		PreparedStatement ps = null;
		Connection conn = null;
		try {
			//conn = Get Connection
			conn = getConnection();
			StringBuffer sql = new StringBuffer("");
			sql.append("UPDATE NCB_NAME ");
			sql.append(" SET GROUP_SEQ = ?, SEGMENT_VALUE = ?, FAMILY_NAME1 = ?, ");
			sql.append(" FAMILY_NAME2 = ?, FIRST_NAME = ?, MIDDLE_NAME = ?, MARITAL_STATUS = ?, ");
			sql.append(" DATE_OF_BIRTH = ?, GENDER = ?, TITLE = ?, NATIONALITY = ?, ");
			sql.append(" NUMBER_OF_CHILDREN = ?, SPOUSE_NAME = ?, OCCUPATION = ?, ");
			sql.append(" CONSENT_TO_ENQUIRE = ?, UPDATE_BY = ?, UPDATE_DATE = ? ");			
			sql.append(" WHERE TRACKING_CODE = ? AND SEQ = ?");
			String dSql = String.valueOf(sql);
			logger.debug("Sql=" + dSql);
			ps = conn.prepareStatement(dSql);
			int cnt = 1;
			ps.setInt(cnt++, ncbNameM.getGroupSeq());
			ps.setString(cnt++, ncbNameM.getSegmentValue());			
			ps.setString(cnt++, ncbNameM.getFamilyName1());
			
			ps.setString(cnt++, ncbNameM.getFamilyName2());
			ps.setString(cnt++, ncbNameM.getFirstName());
			ps.setString(cnt++, ncbNameM.getMiddleName());
			ps.setString(cnt++, ncbNameM.getMaritalStatus());
			
			ps.setDate(cnt++, ncbNameM.getDateOfBirth());
			ps.setInt(cnt++, ncbNameM.getGender());
			ps.setString(cnt++, ncbNameM.getTitle());
			ps.setString(cnt++, ncbNameM.getNationality());
			
			ps.setInt(cnt++, ncbNameM.getNumberOfChildren());
			ps.setString(cnt++, ncbNameM.getSpouseName());
			ps.setString(cnt++, ncbNameM.getOccupation());
			
			ps.setString(cnt++, ncbNameM.getConsentToEnquire());
			ps.setString(cnt++, ncbNameM.getUpdateBy());
			ps.setTimestamp(cnt++, ApplicationDate.getTimestamp());
			// WHERE CLAUSE
			ps.setString(cnt++, ncbNameM.getTrackingCode());
			ps.setInt(cnt++, ncbNameM.getSeq());
			
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
	public void deleteNotInKeyNcbName(ArrayList<NcbNameDataM> ncbNameList,
			String trackingCode) throws ApplicationException {
		deleteNotInKeyNCB_NAME(ncbNameList, trackingCode);
	}

	private void deleteNotInKeyNCB_NAME(ArrayList<NcbNameDataM> ncbNameList,
			String trackingCode) throws ApplicationException {
		PreparedStatement ps = null;
		Connection conn = null;
		try {
            // conn = Get Connection
            conn = getConnection();

            StringBuffer sql = new StringBuffer("");
            sql.append("DELETE FROM NCB_NAME ");
            sql.append(" WHERE TRACKING_CODE = ? ");
            
            if (!Util.empty(ncbNameList)) {
                sql.append(" AND SEQ NOT IN ( ");
                for (NcbNameDataM ncbNameM: ncbNameList) {
                	logger.debug("ncbNameM.getSeq() = "+ncbNameM.getSeq());
                    sql.append(" " + ncbNameM.getSeq() + ", ");
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
