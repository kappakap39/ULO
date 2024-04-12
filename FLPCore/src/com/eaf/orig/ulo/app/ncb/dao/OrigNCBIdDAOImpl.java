package com.eaf.orig.ulo.app.ncb.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;

import com.eaf.core.ulo.common.date.ApplicationDate;
import com.eaf.core.ulo.common.util.Util;
import com.eaf.orig.shared.dao.OrigObjectDAO;
import com.eaf.orig.ulo.app.dao.exception.ApplicationException;
import com.eaf.orig.ulo.model.ncb.NcbIdDataM;

public class OrigNCBIdDAOImpl extends OrigObjectDAO implements OrigNCBIdDAO {
	public OrigNCBIdDAOImpl(String userId){
		this.userId = userId;
	}
	public OrigNCBIdDAOImpl(){
		
	}	
	private String userId = "";
	@Override
	public void createOrigNcbIdM(NcbIdDataM ncbIdM) throws ApplicationException {
		try {
			ncbIdM.setCreateBy(userId);
			ncbIdM.setUpdateBy(userId);
			createTableNCB_ID(ncbIdM);
		} catch (Exception e) {
			logger.fatal(e.getLocalizedMessage());
			throw new ApplicationException(e.getMessage());
		}
	}

	private void createTableNCB_ID(NcbIdDataM ncbIdM) throws ApplicationException {
		PreparedStatement ps = null;
		Connection conn = null;
		try {
			//conn = Get Connection
			conn = getConnection();
			StringBuffer sql = new StringBuffer("");
			sql.append("INSERT INTO NCB_ID ");
			sql.append("( TRACKING_CODE, SEQ, GROUP_SEQ, SEGMENT_VALUE, ");
			sql.append(" ID_TYPE, ID_NUMBER, ID_ISSUE_COUNTRY, ");
			sql.append(" CREATE_DATE, CREATE_BY, UPDATE_DATE, UPDATE_BY) ");

			sql.append(" VALUES(?,?,?,?,  ?,?,?,  ?,?,?,? )");
			String dSql = String.valueOf(sql);
			logger.debug("Sql=" + dSql);
			ps = conn.prepareStatement(dSql);
			int cnt = 1;
			ps.setString(cnt++, ncbIdM.getTrackingCode());
			ps.setInt(cnt++, ncbIdM.getSeq());
			ps.setInt(cnt++, ncbIdM.getGroupSeq());
			ps.setString(cnt++, ncbIdM.getSegmentValue());
			
			ps.setString(cnt++, ncbIdM.getIdType());
			ps.setString(cnt++, ncbIdM.getIdNumber());
			ps.setString(cnt++, ncbIdM.getIdIssueCountry());
			
			ps.setTimestamp(cnt++, ApplicationDate.getTimestamp());
			ps.setString(cnt++, ncbIdM.getCreateBy());
			ps.setTimestamp(cnt++, ApplicationDate.getTimestamp());
			ps.setString(cnt++, ncbIdM.getUpdateBy());
			
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
	public void deleteOrigNcbIdM(String trackingCode, int seq)
			throws ApplicationException {
		deleteTableNCB_ID(trackingCode, seq);
	}

	private void deleteTableNCB_ID(String trackingCode, int seq) throws ApplicationException {
		PreparedStatement ps = null;
		Connection conn = null;
		try {
			//conn = Get Connection
			conn = getConnection();
			StringBuffer sql = new StringBuffer("");
			sql.append("DELETE NCB_ID ");
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
	public ArrayList<NcbIdDataM> loadOrigNcbIdM(String trackingCode)
			throws ApplicationException {
		Connection conn = null;
		try{
			conn = getConnection();
			return selectTableNCB_ID(trackingCode,conn);
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
	public ArrayList<NcbIdDataM> loadOrigNcbIdM(String trackingCode,
			Connection conn) throws ApplicationException {
		return selectTableNCB_ID(trackingCode, conn);
	}
	
	private ArrayList<NcbIdDataM> selectTableNCB_ID(String trackingCode,Connection conn) throws ApplicationException {
		PreparedStatement ps = null;
		ResultSet rs = null;
		ArrayList<NcbIdDataM> ncbIdList = new ArrayList<NcbIdDataM>();
		try {
			StringBuffer sql = new StringBuffer("");
			sql.append("SELECT TRACKING_CODE, SEQ, GROUP_SEQ, SEGMENT_VALUE, ");
			sql.append(" ID_TYPE, ID_NUMBER, ID_ISSUE_COUNTRY, ");
			sql.append(" CREATE_DATE, CREATE_BY, UPDATE_DATE, UPDATE_BY ");
			sql.append(" FROM NCB_ID WHERE TRACKING_CODE = ? ");
			String dSql = String.valueOf(sql);
			logger.debug("Sql=" + dSql);
			ps = conn.prepareStatement(dSql);
			rs = null;
			ps.setString(1, trackingCode);

			rs = ps.executeQuery();

			while(rs.next()) {
				NcbIdDataM ncbIdM = new NcbIdDataM();
				ncbIdM.setTrackingCode(rs.getString("TRACKING_CODE"));
				ncbIdM.setSeq(rs.getInt("SEQ"));
				ncbIdM.setGroupSeq(rs.getInt("GROUP_SEQ"));
				ncbIdM.setSegmentValue(rs.getString("SEGMENT_VALUE"));
				
				ncbIdM.setIdType(rs.getString("ID_TYPE"));
				ncbIdM.setIdNumber(rs.getString("ID_NUMBER"));
				ncbIdM.setIdIssueCountry(rs.getString("ID_ISSUE_COUNTRY"));
				
				ncbIdM.setCreateBy(rs.getString("CREATE_BY"));
				ncbIdM.setCreateDate(rs.getTimestamp("CREATE_DATE"));
				ncbIdM.setUpdateBy(rs.getString("UPDATE_BY"));
				ncbIdM.setUpdateDate(rs.getTimestamp("UPDATE_DATE"));
				ncbIdList.add(ncbIdM);
			}

			return ncbIdList;
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
	public void saveUpdateOrigNcbIdM(NcbIdDataM ncbIdM)
			throws ApplicationException {
		int returnRows = 0;
		ncbIdM.setUpdateBy(userId);
		returnRows = updateTableNCB_ID(ncbIdM);
		if (returnRows == 0) {
			logger.debug("New record then can't update record in table NCB_ID then call Insert method");
			ncbIdM.setCreateBy(userId);
			ncbIdM.setUpdateBy(userId);
			createOrigNcbIdM(ncbIdM);
		}
	}

	private int updateTableNCB_ID(NcbIdDataM ncbIdM) throws ApplicationException {
		int returnRows = 0;
		PreparedStatement ps = null;
		Connection conn = null;
		try {
			//conn = Get Connection
			conn = getConnection();
			StringBuffer sql = new StringBuffer("");
			sql.append("UPDATE NCB_ID ");
			sql.append(" SET GROUP_SEQ = ?, SEGMENT_VALUE = ?, ");
			sql.append(" ID_TYPE = ?, ID_NUMBER = ?, ID_ISSUE_COUNTRY = ?, ");
			sql.append(" UPDATE_BY = ?, UPDATE_DATE = ? ");			
			sql.append(" WHERE TRACKING_CODE = ? AND SEQ = ?");
			String dSql = String.valueOf(sql);
			logger.debug("Sql=" + dSql);
			ps = conn.prepareStatement(dSql);
			int cnt = 1;
			ps.setInt(cnt++, ncbIdM.getGroupSeq());
			ps.setString(cnt++, ncbIdM.getSegmentValue());
			
			ps.setString(cnt++, ncbIdM.getIdType());
			ps.setString(cnt++, ncbIdM.getIdNumber());
			ps.setString(cnt++, ncbIdM.getIdIssueCountry());
			
			ps.setString(cnt++, ncbIdM.getUpdateBy());
			ps.setTimestamp(cnt++, ApplicationDate.getTimestamp());
			// WHERE CLAUSE
			ps.setString(cnt++, ncbIdM.getTrackingCode());
			ps.setInt(cnt++, ncbIdM.getSeq());
			
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
	public void deleteNotInKeyNcbId(ArrayList<NcbIdDataM> ncbIdList,
			String trackingCode) throws ApplicationException {
		deleteNotInKeyNCB_ID(ncbIdList, trackingCode);
	}

	private void deleteNotInKeyNCB_ID(ArrayList<NcbIdDataM> ncbIdList,
			String trackingCode) throws ApplicationException {
		PreparedStatement ps = null;
		Connection conn = null;
		try {
            // conn = Get Connection
            conn = getConnection();

            StringBuffer sql = new StringBuffer("");
            sql.append("DELETE FROM NCB_ID ");
            sql.append(" WHERE TRACKING_CODE = ? ");
            
            if (!Util.empty(ncbIdList)) {
                sql.append(" AND SEQ NOT IN ( ");
                for (NcbIdDataM ncbIdM: ncbIdList) {
                	logger.debug("ncbIdM.getSeq() = "+ncbIdM.getSeq());
                    sql.append(" " + ncbIdM.getSeq() + ", ");
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
