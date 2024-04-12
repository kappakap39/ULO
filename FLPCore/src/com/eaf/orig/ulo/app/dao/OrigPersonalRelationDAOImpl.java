package com.eaf.orig.ulo.app.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;

import com.eaf.core.ulo.common.date.ApplicationDate;
import com.eaf.core.ulo.common.util.Util;
import com.eaf.orig.shared.dao.OrigObjectDAO;
import com.eaf.orig.ulo.app.dao.exception.ApplicationException;
import com.eaf.orig.ulo.model.app.PersonalRelationDataM;

public class OrigPersonalRelationDAOImpl extends OrigObjectDAO implements
		OrigPersonalRelationDAO {
	public OrigPersonalRelationDAOImpl(String userId){
		this.userId = userId;
	}
	public OrigPersonalRelationDAOImpl(){
		
	}
	private String userId = "";
	@Override
	public void createOrigPersonalRelationM(PersonalRelationDataM personalRelationM)
			throws ApplicationException {
		personalRelationM.setCreateBy(userId);
		personalRelationM.setUpdateBy(userId);
		createTableORIG_PERSONAL_RELATION(personalRelationM);
	}

	private void createTableORIG_PERSONAL_RELATION(
			PersonalRelationDataM personalRelationM) throws ApplicationException {
		PreparedStatement ps = null;
		Connection conn = null;
		try {
			//conn = Get Connection
			conn = getConnection();
			StringBuffer sql = new StringBuffer("");
			sql.append("INSERT INTO ORIG_PERSONAL_RELATION ");
			sql.append("( PERSONAL_ID, REF_ID, PERSONAL_TYPE, RELATION_LEVEL, ");
			sql.append(" CREATE_DATE, CREATE_BY, UPDATE_DATE, UPDATE_BY ) ");

			sql.append(" VALUES(?,?,?,?,  ?,?,?,?)");
			String dSql = String.valueOf(sql);
			logger.debug("Sql=" + dSql);
			ps = conn.prepareStatement(dSql);
			int cnt = 1;
			ps.setString(cnt++, personalRelationM.getPersonalId());
			ps.setString(cnt++, personalRelationM.getRefId());
			ps.setString(cnt++, personalRelationM.getPersonalType());
			ps.setString(cnt++, personalRelationM.getRelationLevel());
			
			ps.setTimestamp(cnt++, ApplicationDate.getTimestamp());
			ps.setString(cnt++, personalRelationM.getCreateBy());
			ps.setTimestamp(cnt++, ApplicationDate.getTimestamp());
			ps.setString(cnt++, personalRelationM.getUpdateBy());
			
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
	public void deleteOrigPersonalRelationM(String personalId, String refId, String relationLevel) throws ApplicationException {
		deleteTableORIG_PERSONAL_RELATION(personalId, refId, relationLevel);
	}

	private void deleteTableORIG_PERSONAL_RELATION(String personalId, String refId, String relationLevel) throws ApplicationException {
		PreparedStatement ps = null;
		Connection conn = null;
		try {
			//conn = Get Connection
			conn = getConnection();
			StringBuffer sql = new StringBuffer("");
			sql.append(" DELETE ORIG_PERSONAL_RELATION ");
			sql.append(" WHERE PERSONAL_ID = ? ");
			if(!Util.empty(refId) && !Util.empty(relationLevel)) {
				sql.append(" AND REF_ID = ? ");
				sql.append(" AND RELATION_LEVEL = ? ");
			}
			String dSql = String.valueOf(sql);
			logger.debug("Sql=" + dSql);
			ps = conn.prepareStatement(dSql);
			ps.setString(1, personalId);
			if(!Util.empty(refId) && !Util.empty(relationLevel)) {
				ps.setString(2, refId);
				ps.setString(3, relationLevel);
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
	public ArrayList<PersonalRelationDataM> loadOrigPersonalRelationM(
			String personalId) throws ApplicationException {
		Connection conn = null;
		try{
			conn = getConnection();
			return selectTableORIG_PERSONAL_RELATION(personalId,conn);
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
	public ArrayList<PersonalRelationDataM> loadOrigPersonalRelationM(
			String personalId, Connection conn) throws ApplicationException {
		return selectTableORIG_PERSONAL_RELATION(personalId, conn);
	}
	private ArrayList<PersonalRelationDataM> selectTableORIG_PERSONAL_RELATION(
			String personalId,Connection conn) throws ApplicationException {
		PreparedStatement ps = null;
		ResultSet rs = null;
		ArrayList<PersonalRelationDataM> personalRelationList = new ArrayList<PersonalRelationDataM>();
		try {
			StringBuffer sql = new StringBuffer("");
			sql.append("SELECT PERSONAL_ID, REF_ID, PERSONAL_TYPE, RELATION_LEVEL, ");
			sql.append(" CREATE_DATE, CREATE_BY, UPDATE_DATE, UPDATE_BY ");
			sql.append(" FROM ORIG_PERSONAL_RELATION WHERE PERSONAL_ID = ? ");
			String dSql = String.valueOf(sql);
			logger.debug("Sql=" + dSql);
			ps = conn.prepareStatement(dSql);
			rs = null;
			ps.setString(1, personalId);

			rs = ps.executeQuery();

			while(rs.next()) {
				PersonalRelationDataM personalRelationM = new PersonalRelationDataM();
				personalRelationM.setPersonalId(rs.getString("PERSONAL_ID"));
				personalRelationM.setRefId(rs.getString("REF_ID"));
				personalRelationM.setPersonalType(rs.getString("PERSONAL_TYPE"));
				personalRelationM.setRelationLevel(rs.getString("RELATION_LEVEL"));
				
				personalRelationM.setCreateBy(rs.getString("CREATE_BY"));
				personalRelationM.setCreateDate(rs.getTimestamp("CREATE_DATE"));
				personalRelationM.setUpdateBy(rs.getString("UPDATE_BY"));
				personalRelationM.setUpdateDate(rs.getTimestamp("UPDATE_DATE"));
				
				personalRelationList.add(personalRelationM);
			}

			return personalRelationList;
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
	public void saveUpdateOrigPersonalRelationM(PersonalRelationDataM personalRelationM)
			throws ApplicationException {
		int returnRows = 0;
		personalRelationM.setUpdateBy(userId);
		returnRows = updateTableORIG_PERSONAL_RELATION(personalRelationM);
		if (returnRows == 0) {
			personalRelationM.setCreateBy(userId);
			createOrigPersonalRelationM(personalRelationM);
		} 
	}

	private int updateTableORIG_PERSONAL_RELATION(PersonalRelationDataM personalRelationM) 
			throws ApplicationException {
		int returnRows = 0;
		PreparedStatement ps = null;
		Connection conn = null;
		try {
			//conn = Get Connection
			conn = getConnection();
			StringBuffer sql = new StringBuffer("");
			sql.append("UPDATE ORIG_PERSONAL_RELATION ");
			sql.append(" SET PERSONAL_TYPE = ?, UPDATE_DATE = ?, UPDATE_BY = ? ");
			sql.append(" WHERE PERSONAL_ID = ? AND REF_ID = ? AND RELATION_LEVEL = ? ");
			String dSql = String.valueOf(sql);
			logger.debug("Sql=" + dSql);
			ps = conn.prepareStatement(dSql);
			int cnt = 1;
			ps.setString(cnt++, personalRelationM.getPersonalType());
			ps.setTimestamp(cnt++, ApplicationDate.getTimestamp());
			ps.setString(cnt++, personalRelationM.getUpdateBy());
			
			// WHERE CLAUSE
			ps.setString(cnt++, personalRelationM.getPersonalId());
			ps.setString(cnt++, personalRelationM.getRefId());
			ps.setString(cnt++, personalRelationM.getRelationLevel());
			
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
	public void deleteNotInKeyPersonalRelation(
			ArrayList<PersonalRelationDataM> personalRelationList, String personalId)
			throws ApplicationException {
		deleteNotInKeyORIG_PERSONAL_RELATION(personalRelationList, personalId);
	}

	private void deleteNotInKeyORIG_PERSONAL_RELATION(
			ArrayList<PersonalRelationDataM> personalRelationList, String personalId) throws ApplicationException {
		PreparedStatement ps = null;
		Connection conn = null;
		try {
            // conn = Get Connection
			conn = getConnection();
			StringBuffer sql = new StringBuffer("");
			sql.append(" DELETE FROM ORIG_PERSONAL_RELATION relation WHERE PERSONAL_ID = ? ");
			if (!Util.empty(personalRelationList)) {
                sql.append(" AND NOT EXISTS ( ");
                sql.append(" select * from ORIG_PERSONAL_RELATION innerRelation ");
                sql.append(" where relation.personal_id = innerRelation.personal_id ");
                sql.append(" and relation.ref_id = innerRelation.ref_id ");
                sql.append(" and relation.relation_level = innerRelation.relation_level ");
                sql.append(" and innerRelation.personal_id = ? AND ( ");
                for (PersonalRelationDataM personalRelationM: personalRelationList) {
                	sql.append(" ( relation.REF_ID = ");
                	sql.append(" '" + personalRelationM.getRefId() + "' ");
                	sql.append(" AND relation.RELATION_LEVEL = ");
                	sql.append(" '" + personalRelationM.getRelationLevel() + "' )");
                	sql.append(" OR");
                }
                
                if (sql.toString().trim().endsWith("OR")) {
                    sql.delete(sql.toString().lastIndexOf("OR"), sql.toString().length());
                }

                sql.append(") ) ");
            }

			String dSql = String.valueOf(sql);
			logger.debug("Sql=" + dSql);
			ps = conn.prepareStatement(dSql);
			ps.setString(1, personalId);
			if (!Util.empty(personalRelationList)) {
                ps.setString(2, personalId);
			}
			
			ps.executeUpdate();

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
