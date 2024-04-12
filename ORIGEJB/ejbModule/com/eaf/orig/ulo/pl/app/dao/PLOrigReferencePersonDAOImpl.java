package com.eaf.orig.ulo.pl.app.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import org.apache.log4j.Logger;
import com.eaf.orig.shared.dao.OrigObjectDAO;
import com.eaf.orig.ulo.pl.app.dao.exception.PLOrigApplicationException;
import com.eaf.orig.ulo.pl.model.app.PLReferencePersonDataM;

public class PLOrigReferencePersonDAOImpl extends OrigObjectDAO implements PLOrigReferencePersonDAO {

	private static Logger log = Logger.getLogger(PLOrigReferencePersonDAOImpl.class);
	
	@Override
	public void updateSaveReferencePerson(PLReferencePersonDataM refPersonM, String appRecId)throws PLOrigApplicationException {
		try{
			if(null != refPersonM){
				int returnRows = this.updateTableOrig_ReferencePerson(refPersonM, appRecId);
				if(returnRows == 0){
					this.insertTableOrig_ReferencePerson(refPersonM, appRecId);
				}			
			}
		}catch(Exception e){
			log.fatal(e.getLocalizedMessage());
			throw new PLOrigApplicationException(e.getMessage());
		}

	}
	
	private int updateTableOrig_ReferencePerson(PLReferencePersonDataM refPersonM, String appRecId) throws PLOrigApplicationException {
		int returnRows = 0;
		PreparedStatement ps = null;
		Connection conn = null;
		try{
			conn = getConnection();
			StringBuilder sql = new StringBuilder("");
			sql.append("UPDATE ORIG_REFERENCE_PERSON ");
			sql.append(" SET TH_TITLE_CODE=?, TH_FIRST_NAME=?, TH_LAST_NAME=?, PHONE1=?, EXT1=? ");
			sql.append(", MOBILE=?, UPDATE_DATE=SYSDATE, UPDATE_BY=?, RELATION=?, PHONE2=? ");
			sql.append(", EXT2=? ");
			sql.append(" WHERE APPLICATION_RECORD_ID=?");
			String dSql = String.valueOf(sql);
			log.debug("Sql=" + dSql);
			ps = conn.prepareStatement(dSql);
			int index = 1;
			ps.setString(index++, refPersonM.getThaiTitleCode());
			ps.setString(index++, refPersonM.getThaiFirstName());
			ps.setString(index++, refPersonM.getThaiLastName());
			ps.setString(index++, refPersonM.getPhone());
			ps.setString(index++, refPersonM.getPhoneExt());
			
			ps.setString(index++, refPersonM.getMobile());
			ps.setString(index++, refPersonM.getUpdateBy());
			ps.setString(index++, refPersonM.getRelation());
			ps.setString(index++, refPersonM.getPhone2());
			
			ps.setString(index++, refPersonM.getPhoneExt2());
			
			ps.setString(index++, appRecId);
			
			returnRows = ps.executeUpdate();
			
		} catch (Exception e) {
			log.fatal(e.getLocalizedMessage());
			throw new PLOrigApplicationException(e.getMessage());
		} finally {
			try {
				closeConnection(conn, ps);
			} catch (Exception e) {
				log.fatal(e.getLocalizedMessage());
				throw new PLOrigApplicationException(e.getMessage());
			}
		}
		return returnRows;
	}
	
	private void insertTableOrig_ReferencePerson(PLReferencePersonDataM refPersonM, String appRecId) throws PLOrigApplicationException {

		PreparedStatement ps = null;
		Connection conn = null;
		try {
			
			conn = getConnection();
			StringBuilder sql = new StringBuilder("");
			sql.append("INSERT INTO ORIG_REFERENCE_PERSON ");
			sql.append("( APPLICATION_RECORD_ID, TH_TITLE_CODE, TH_FIRST_NAME, TH_LAST_NAME, PHONE1 ");
			sql.append(", EXT1, MOBILE, CREATE_DATE, CREATE_BY, UPDATE_DATE ");
			sql.append(", UPDATE_BY, RELATION, PHONE2, EXT2 ) " );
			sql.append(" VALUES(?,?,?,?,?   ,?,?,SYSDATE,?,SYSDATE  ,?,?,?,? ) ");
			String dSql = String.valueOf(sql);
			log.debug("Sql=" + dSql);
			ps = conn.prepareStatement(dSql);
			
			int index = 1;
			ps.setString(index++, appRecId);
			ps.setString(index++, refPersonM.getThaiTitleCode());
			ps.setString(index++, refPersonM.getThaiFirstName());
			ps.setString(index++, refPersonM.getThaiLastName());
			ps.setString(index++, refPersonM.getPhone());
			
			ps.setString(index++, refPersonM.getPhoneExt());
			ps.setString(index++, refPersonM.getMobile());
			ps.setString(index++, refPersonM.getCreateBy());
			
			ps.setString(index++, refPersonM.getUpdateBy());
			ps.setString(index++, refPersonM.getRelation());
			ps.setString(index++, refPersonM.getPhone2());
			ps.setString(index++, refPersonM.getPhoneExt2());
			
			ps.executeUpdate();
			
		}catch (Exception e) {
			log.fatal(e.getLocalizedMessage());
			throw new PLOrigApplicationException(e.getMessage());
		} finally {
			try {
				closeConnection(conn, ps);
			} catch (Exception e) {
				log.fatal(e.getLocalizedMessage());
				throw new PLOrigApplicationException(e.getMessage());
			}
		}
		
	}

	@Override
	public PLReferencePersonDataM loadReferencePerson(String appRecId) throws PLOrigApplicationException {

		PreparedStatement ps = null;
		ResultSet rs = null;		

		PLReferencePersonDataM refPersonDataM = null;
		Connection conn = null;
		try {
			conn = getConnection();
			StringBuilder sql = new StringBuilder("");
			sql.append("SELECT TH_TITLE_CODE, TH_FIRST_NAME, TH_LAST_NAME, PHONE1, EXT1 ");
			sql.append(", MOBILE, CREATE_DATE, CREATE_BY, UPDATE_DATE, UPDATE_BY ");
			sql.append(", RELATION, PHONE2, EXT2 ");
			sql.append(" FROM ORIG_REFERENCE_PERSON WHERE APPLICATION_RECORD_ID = ? ");
			String dSql = String.valueOf(sql);
			log.debug("Sql=" + dSql);
			ps = conn.prepareStatement(dSql);
			rs = null;
			ps.setString(1, appRecId);

			rs = ps.executeQuery();
			if (rs.next()) {		
				refPersonDataM = new PLReferencePersonDataM();
				refPersonDataM.setThaiTitleCode(rs.getString(1));
				refPersonDataM.setThaiFirstName(rs.getString(2));
				refPersonDataM.setThaiLastName(rs.getString(3));
				refPersonDataM.setPhone(rs.getString(4));
				refPersonDataM.setPhoneExt(rs.getString(5));
				
				refPersonDataM.setMobile(rs.getString(6));
				refPersonDataM.setCreateDate(rs.getTimestamp(7));
				refPersonDataM.setCreateBy(rs.getString(8));
				refPersonDataM.setUpdateDate(rs.getTimestamp(9));
				refPersonDataM.setUpdateBy(rs.getString(10));
				
				refPersonDataM.setRelation(rs.getString(11));
				refPersonDataM.setPhone2(rs.getString(12));
				refPersonDataM.setPhoneExt2(rs.getString(13));				
			}
			
		}catch(Exception e){
			log.fatal(e.getLocalizedMessage());
			throw new PLOrigApplicationException(e.getMessage());
		}finally{
			try{
				closeConnection(conn, rs, ps);
			}catch(Exception e){
				log.fatal(e.getLocalizedMessage());
				throw new PLOrigApplicationException(e.getMessage());
			}
		}
		return refPersonDataM;
	}

}
