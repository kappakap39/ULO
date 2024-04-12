package com.eaf.orig.ulo.app.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;

import com.eaf.core.ulo.common.date.ApplicationDate;
import com.eaf.core.ulo.common.util.Util;
import com.eaf.orig.shared.dao.OrigObjectDAO;
import com.eaf.orig.ulo.app.dao.exception.ApplicationException;
import com.eaf.orig.ulo.model.app.ReferencePersonDataM;

public class OrigReferencePersonDAOImpl extends OrigObjectDAO implements
		OrigReferencePersonDAO {
	public OrigReferencePersonDAOImpl(String userId){
		this.userId = userId;
	}
	public OrigReferencePersonDAOImpl(){
		
	}
	private String userId = "";
	@Override
	public void createOrigReferencePersonM(ReferencePersonDataM referencePersonM)
			throws ApplicationException {
		referencePersonM.setCreateBy(userId);
		referencePersonM.setUpdateBy(userId);
		createTableORIG_REFERENCE_PERSON(referencePersonM);
	}

	private void createTableORIG_REFERENCE_PERSON(
			ReferencePersonDataM referencePersonM) throws ApplicationException {
		PreparedStatement ps = null;
		Connection conn = null;
		try {
			//conn = Get Connection
			conn = getConnection();
			StringBuffer sql = new StringBuffer("");
			sql.append("INSERT INTO ORIG_REFERENCE_PERSON ");
			sql.append("( APPLICATION_GROUP_ID, SEQ, TH_TITLE_CODE, TH_FIRST_NAME, ");
			sql.append(" TH_LAST_NAME, RELATION, MOBILE, PHONE1, EXT1, ");
			sql.append(" PHONE2, EXT2, OFFICE_PHONE, OFFICE_PHONE_EXT, TH_TITLE_DESC, ");
			sql.append("CREATE_DATE, CREATE_BY, UPDATE_DATE, UPDATE_BY ) ");

			sql.append(" VALUES(?,?,?,?,  ?,?,?,?,?,  ?,?,?,?,?,  ?,?,?,?)");
			String dSql = String.valueOf(sql);
			logger.debug("Sql=" + dSql);
			ps = conn.prepareStatement(dSql);
			int cnt = 1;
			ps.setString(cnt++, referencePersonM.getApplicationGroupId());
			ps.setInt(cnt++, referencePersonM.getSeq());
			ps.setString(cnt++, referencePersonM.getThTitleCode());
			ps.setString(cnt++, referencePersonM.getThFirstName());
			
			ps.setString(cnt++, referencePersonM.getThLastName());
			ps.setString(cnt++, referencePersonM.getRelation());
			ps.setString(cnt++, referencePersonM.getMobile());
			ps.setString(cnt++, referencePersonM.getPhone1());
			ps.setString(cnt++, referencePersonM.getExt1());

			ps.setString(cnt++, referencePersonM.getPhone2());
			ps.setString(cnt++, referencePersonM.getExt2());
			ps.setString(cnt++, referencePersonM.getOfficePhone());
			ps.setString(cnt++, referencePersonM.getOfficePhoneExt());
			ps.setString(cnt++, referencePersonM.getThTitleDesc());
			
			ps.setTimestamp(cnt++, ApplicationDate.getTimestamp());
			ps.setString(cnt++, referencePersonM.getCreateBy());
			ps.setTimestamp(cnt++, ApplicationDate.getTimestamp());
			ps.setString(cnt++, referencePersonM.getUpdateBy());
			
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
	public void deleteOrigReferencePersonM(String applicationGroupId, int seq)
			throws ApplicationException {
		deleteTableORIG_REFERENCE_PERSON(applicationGroupId, seq);

	}

	private void deleteTableORIG_REFERENCE_PERSON(String applicationGroupId, int seq) 
			throws ApplicationException {
		PreparedStatement ps = null;
		Connection conn = null;
		try {
			//conn = Get Connection
			conn = getConnection();
			StringBuffer sql = new StringBuffer("");
			sql.append("DELETE ORIG_REFERENCE_PERSON ");
			sql.append(" WHERE APPLICATION_GROUP_ID = ? ");
			if(seq != 0){
				sql.append(" AND SEQ = ? ");
			}
			String dSql = String.valueOf(sql);
			logger.debug("Sql=" + dSql);
			ps = conn.prepareStatement(dSql);
			ps.setString(1, applicationGroupId);
			if(seq != 0){
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
	public ArrayList<ReferencePersonDataM> loadOrigReferencePersonM(
			String applicationGroupId) throws ApplicationException {
		Connection conn = null;
		try{
			conn = getConnection();
			return selectTableORIG_REFERENCE_PERSON(applicationGroupId,conn);
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
	public ArrayList<ReferencePersonDataM> loadOrigReferencePersonM(
			String applicationGroupId, Connection conn)
			throws ApplicationException {
		return selectTableORIG_REFERENCE_PERSON(applicationGroupId, conn);
	}
	private ArrayList<ReferencePersonDataM> selectTableORIG_REFERENCE_PERSON(
			String applicationGroupId,Connection conn) throws ApplicationException {
		PreparedStatement ps = null;
		ResultSet rs = null;
		ArrayList<ReferencePersonDataM> referencePersonList = new ArrayList<ReferencePersonDataM>();
		try {
			StringBuffer sql = new StringBuffer("");
			sql.append("SELECT APPLICATION_GROUP_ID, SEQ, TH_TITLE_CODE, TH_FIRST_NAME, ");
			sql.append(" TH_LAST_NAME, RELATION, MOBILE, PHONE1, EXT1, ");
			sql.append(" PHONE2, EXT2, OFFICE_PHONE, OFFICE_PHONE_EXT, TH_TITLE_DESC, ");
			sql.append(" CREATE_DATE, CREATE_BY, UPDATE_DATE, UPDATE_BY ");
			sql.append(" FROM ORIG_REFERENCE_PERSON WHERE APPLICATION_GROUP_ID = ? ");
			String dSql = String.valueOf(sql);
			logger.debug("Sql=" + dSql);
			ps = conn.prepareStatement(dSql);
			rs = null;
			ps.setString(1, applicationGroupId);

			rs = ps.executeQuery();

			while(rs.next()) {
				ReferencePersonDataM referencePersonM = new ReferencePersonDataM();
				referencePersonM.setApplicationGroupId(rs.getString("APPLICATION_GROUP_ID"));
				referencePersonM.setSeq(rs.getInt("SEQ"));
				referencePersonM.setThTitleCode(rs.getString("TH_TITLE_CODE"));
				referencePersonM.setThFirstName(rs.getString("TH_FIRST_NAME"));
				
				referencePersonM.setThLastName(rs.getString("TH_LAST_NAME"));
				referencePersonM.setRelation(rs.getString("RELATION"));
				referencePersonM.setMobile(rs.getString("MOBILE"));
				referencePersonM.setPhone1(rs.getString("PHONE1"));
				referencePersonM.setExt1(rs.getString("EXT1"));

				referencePersonM.setPhone2(rs.getString("PHONE2"));
				referencePersonM.setExt2(rs.getString("EXT2"));
				referencePersonM.setOfficePhone(rs.getString("OFFICE_PHONE"));
				referencePersonM.setOfficePhoneExt(rs.getString("OFFICE_PHONE_EXT"));
				referencePersonM.setThTitleDesc(rs.getString("TH_TITLE_DESC"));
				
				referencePersonM.setCreateBy(rs.getString("CREATE_BY"));
				referencePersonM.setCreateDate(rs.getTimestamp("CREATE_DATE"));
				referencePersonM.setUpdateBy(rs.getString("UPDATE_BY"));
				referencePersonM.setUpdateDate(rs.getTimestamp("UPDATE_DATE"));
				referencePersonList.add(referencePersonM);
			}

			return referencePersonList;
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
	public void saveUpdateOrigReferencePersonM(
			ReferencePersonDataM referencePersonM) throws ApplicationException {
		int returnRows = 0;
		referencePersonM.setUpdateBy(userId);
		returnRows = updateTableORIG_REFERENCE_PERSON(referencePersonM);
		if (returnRows == 0) {
			referencePersonM.setCreateBy(userId);
			createTableORIG_REFERENCE_PERSON(referencePersonM);
		}
	}

	private int updateTableORIG_REFERENCE_PERSON(
			ReferencePersonDataM referencePersonM) throws ApplicationException {
		int returnRows = 0;
		PreparedStatement ps = null;
		Connection conn = null;
		try {
			//conn = Get Connection
			conn = getConnection();
			StringBuffer sql = new StringBuffer("");
			sql.append("UPDATE ORIG_REFERENCE_PERSON ");
			sql.append(" SET TH_TITLE_CODE = ?, TH_FIRST_NAME = ?, TH_LAST_NAME = ?, RELATION = ?, ");
			sql.append(" MOBILE = ?, PHONE1 = ?, EXT1 = ?, PHONE2 = ?, EXT2 = ?, OFFICE_PHONE = ?, ");
			sql.append(" OFFICE_PHONE_EXT = ?, TH_TITLE_DESC = ?, UPDATE_DATE = ?, UPDATE_BY = ? ");			
			sql.append(" WHERE APPLICATION_GROUP_ID = ? AND SEQ = ? ");
			String dSql = String.valueOf(sql);
			logger.debug("Sql=" + dSql);
			ps = conn.prepareStatement(dSql);
			int cnt = 1;
			ps.setString(cnt++, referencePersonM.getThTitleCode());
			ps.setString(cnt++, referencePersonM.getThFirstName());			
			ps.setString(cnt++, referencePersonM.getThLastName());
			ps.setString(cnt++, referencePersonM.getRelation());
			
			ps.setString(cnt++, referencePersonM.getMobile());
			ps.setString(cnt++, referencePersonM.getPhone1());
			ps.setString(cnt++, referencePersonM.getExt1());
			ps.setString(cnt++, referencePersonM.getPhone2());
			ps.setString(cnt++, referencePersonM.getExt2());
			ps.setString(cnt++, referencePersonM.getOfficePhone());
			
			ps.setString(cnt++, referencePersonM.getOfficePhoneExt());
			ps.setString(cnt++, referencePersonM.getThTitleDesc());
			ps.setTimestamp(cnt++, ApplicationDate.getTimestamp());
			ps.setString(cnt++, referencePersonM.getUpdateBy());
			// WHERE CLAUSE
			ps.setString(cnt++, referencePersonM.getApplicationGroupId());			
			ps.setInt(cnt++, referencePersonM.getSeq());			
			
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
	public void deleteNotInKeyReferencePerson(ArrayList<ReferencePersonDataM> referencePersonList,
			String applicationGroupId) throws ApplicationException {
		PreparedStatement ps = null;
		Connection conn = null;
		try {
            // conn = Get Connection
            conn = getConnection();

            StringBuffer sql = new StringBuffer("");
            sql.append("DELETE FROM ORIG_REFERENCE_PERSON ");
            sql.append(" WHERE APPLICATION_GROUP_ID = ? ");
            
            if (!Util.empty(referencePersonList)) {
                sql.append(" AND SEQ NOT IN ( ");
                for (ReferencePersonDataM referPersonalInfoM: referencePersonList) {
                	logger.debug("referPersonalInfoM.getSeq() = "+referPersonalInfoM.getSeq());
                    sql.append(referPersonalInfoM.getSeq() + ", ");
                }
                
                if (sql.toString().trim().endsWith(",")) {
                    sql.delete(sql.toString().lastIndexOf(","), sql.toString().length());
                }

                sql.append(" ) ");
            }

            String dSql = String.valueOf(sql);
            ps = conn.prepareStatement(dSql);
            ps.setString(1, applicationGroupId);
            
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
