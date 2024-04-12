package com.eaf.orig.ulo.app.xrules.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;

import com.eaf.core.ulo.common.date.ApplicationDate;
import com.eaf.core.ulo.common.util.Util;
import com.eaf.orig.shared.constant.OrigConstant;
import com.eaf.orig.shared.dao.OrigObjectDAO;
import com.eaf.orig.ulo.app.dao.exception.ApplicationException;
import com.eaf.orig.ulo.app.util.GenerateUnique;
import com.eaf.orig.ulo.model.app.PhoneVerificationDataM;

public class OrigPhoneVerificationDAOImpl extends OrigObjectDAO implements
		OrigPhoneVerificationDAO {
	public OrigPhoneVerificationDAOImpl(String userId){
		this.userId = userId;
	}
	public OrigPhoneVerificationDAOImpl(){
		
	}
	private String userId = "";
	@Override
	public void createOrigPhoneVerificationM(
			PhoneVerificationDataM phoneVerificationM, Connection conn)
			throws ApplicationException {
		try {
			logger.debug("phoneVerificationM.getPhoneVerifyId() :"+phoneVerificationM.getPhoneVerifyId());
		    if(Util.empty(phoneVerificationM.getPhoneVerifyId())){
				String phoneVerifyId = GenerateUnique.generate(OrigConstant.SeqNames.XRULES_PHONE_VERIFICATION_PK,conn); 
				phoneVerificationM.setPhoneVerifyId(phoneVerifyId);
			}
		    phoneVerificationM.setCreateBy(userId);
		    phoneVerificationM.setUpdateBy(userId);
			createTableXRULES_PHONE_VERIFICATION(phoneVerificationM,conn);
		} catch (Exception e) {
			logger.fatal(e.getLocalizedMessage());
			throw new ApplicationException(e.getMessage());
		}
	}
	@Override
	public void createOrigPhoneVerificationM(
			PhoneVerificationDataM phoneVerificationM) throws ApplicationException {

		Connection conn = null;
		try{
			conn = getConnection();
			createOrigPhoneVerificationM(phoneVerificationM, conn);
		}catch(Exception e){
			logger.fatal(e);
			throw new ApplicationException(e.getMessage());
		}finally{
			try{
				closeConnection(conn);
			}catch(Exception e){
				logger.fatal(e.getLocalizedMessage());
			}
		}
	}

	private void createTableXRULES_PHONE_VERIFICATION(
			PhoneVerificationDataM phoneVerificationM,Connection conn) throws ApplicationException {
		PreparedStatement ps = null;
//		Connection conn = null;
		try {
			//conn = Get Connection
//			conn = getConnection();
			StringBuffer sql = new StringBuffer("");
			sql.append("INSERT INTO XRULES_PHONE_VERIFICATION ");
			sql.append("( PHONE_VERIFY_ID, VER_RESULT_ID, CONTACT_TYPE, ");
			sql.append(" TELEPHONE_NUMBER, PHONE_VER_STATUS, REMARK, ");
			sql.append(" CREATE_BY, CREATE_DATE, UPDATE_BY, UPDATE_DATE, LIFE_CYCLE )");

			sql.append(" VALUES(?,?,?,  ?,?,?,  ?,?,?,? ,?)");
			String dSql = String.valueOf(sql);
			logger.debug("Sql=" + dSql);
			ps = conn.prepareStatement(dSql);
			int cnt = 1;
			ps.setString(cnt++, phoneVerificationM.getPhoneVerifyId());
			ps.setString(cnt++, phoneVerificationM.getVerResultId());
			ps.setString(cnt++, phoneVerificationM.getContactType());
			
			ps.setString(cnt++, phoneVerificationM.getTelephoneNumber());
			ps.setString(cnt++, phoneVerificationM.getPhoneVerStatus());
			ps.setString(cnt++, phoneVerificationM.getRemark());
			
			ps.setString(cnt++, phoneVerificationM.getCreateBy());
			ps.setTimestamp(cnt++, ApplicationDate.getTimestamp());
			ps.setString(cnt++, phoneVerificationM.getUpdateBy());
			ps.setTimestamp(cnt++, ApplicationDate.getTimestamp());
			ps.setInt(cnt++, phoneVerificationM.getLifeCycle());
			
			ps.executeUpdate();
		} catch (Exception e) {
			logger.fatal(e.getLocalizedMessage());
			throw new ApplicationException(e.getMessage());
		} finally {
			try {
//				closeConnection(conn, ps);
				closeConnection(ps);
			} catch (Exception e) {
				logger.fatal(e.getLocalizedMessage());
			}
		}
	}

	@Override
	public void deleteOrigPhoneVerificationM(String verResultId,
			String phoneVerifyId) throws ApplicationException {
		try {
			deleteTableXRULES_PHONE_VERIFICATION(verResultId, phoneVerifyId);
		} catch (Exception e) {
			logger.fatal(e.getLocalizedMessage());
			throw new ApplicationException(e.getMessage());
		}
	}

	private void deleteTableXRULES_PHONE_VERIFICATION(String verResultId,
			String phoneVerifyId) throws ApplicationException {
		PreparedStatement ps = null;
		Connection conn = null;
		try {
			//conn = Get Connection
			conn = getConnection();
			StringBuffer sql = new StringBuffer("");
			sql.append(" DELETE XRULES_PHONE_VERIFICATION ");
			sql.append(" WHERE VER_RESULT_ID = ? ");
			if(!Util.empty(phoneVerifyId)) {
				sql.append(" AND PHONE_VERIFY_ID = ? ");
			}
			String dSql = String.valueOf(sql);
			logger.debug("Sql=" + dSql);
			ps = conn.prepareStatement(dSql);
			ps.setString(1, verResultId);
			if(!Util.empty(phoneVerifyId)) {
				ps.setString(2, phoneVerifyId);
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
	public ArrayList<PhoneVerificationDataM> loadOrigPhoneVerificationM(
			String verResultId, Connection conn) throws ApplicationException {
		return selectTableXRULES_PHONE_VERIFICATION(verResultId, conn);
	}
	@Override
	public ArrayList<PhoneVerificationDataM> loadOrigPhoneVerificationM(
			String verResultId) throws ApplicationException {
		Connection conn = null;
		try{
			conn = getConnection();
			return selectTableXRULES_PHONE_VERIFICATION(verResultId,conn);
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

	private ArrayList<PhoneVerificationDataM> selectTableXRULES_PHONE_VERIFICATION(
			String verResultId,Connection conn) throws ApplicationException {
		PreparedStatement ps = null;
		ResultSet rs = null;
		ArrayList<PhoneVerificationDataM> phoneVerificationList = new ArrayList<PhoneVerificationDataM>();
		try {
			StringBuffer sql = new StringBuffer("");
			sql.append("SELECT PHONE_VERIFY_ID, VER_RESULT_ID, CONTACT_TYPE, ");
			sql.append(" TELEPHONE_NUMBER, PHONE_VER_STATUS, REMARK, ");
			sql.append(" CREATE_DATE, CREATE_BY, UPDATE_DATE, UPDATE_BY, LIFE_CYCLE ");
			sql.append(" FROM XRULES_PHONE_VERIFICATION WHERE VER_RESULT_ID = ? ");
			String dSql = String.valueOf(sql);
			logger.debug("Sql=" + dSql);
			ps = conn.prepareStatement(dSql);
			rs = null;
			ps.setString(1, verResultId);

			rs = ps.executeQuery();

			while(rs.next()) {
				PhoneVerificationDataM phoneVerResultM = new PhoneVerificationDataM();
				phoneVerResultM.setPhoneVerifyId(rs.getString("PHONE_VERIFY_ID"));
				phoneVerResultM.setVerResultId(rs.getString("VER_RESULT_ID"));
				phoneVerResultM.setContactType(rs.getString("CONTACT_TYPE"));
				
				phoneVerResultM.setTelephoneNumber(rs.getString("TELEPHONE_NUMBER"));
				phoneVerResultM.setPhoneVerStatus(rs.getString("PHONE_VER_STATUS"));
				phoneVerResultM.setRemark(rs.getString("REMARK"));
				
				phoneVerResultM.setCreateBy(rs.getString("CREATE_BY"));
				phoneVerResultM.setCreateDate(rs.getTimestamp("CREATE_DATE"));
				phoneVerResultM.setUpdateBy(rs.getString("UPDATE_BY"));
				phoneVerResultM.setUpdateDate(rs.getTimestamp("UPDATE_DATE"));
				phoneVerResultM.setLifeCycle(rs.getInt("LIFE_CYCLE"));
				
				phoneVerificationList.add(phoneVerResultM);
			}

			return phoneVerificationList;
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
	public void saveUpdateOrigPhoneVerificationM(
			PhoneVerificationDataM phoneVerificationM) throws ApplicationException {
		Connection conn = null;
		try{
			conn = getConnection();
			saveUpdateOrigPhoneVerificationM(phoneVerificationM, conn);
		}catch(Exception e){
			logger.fatal(e);
			throw new ApplicationException(e.getMessage());
		}finally{
			try{
				closeConnection(conn);
			}catch(Exception e){
				logger.fatal(e.getLocalizedMessage());
			}
		}
	}
	@Override
	public void saveUpdateOrigPhoneVerificationM(
			PhoneVerificationDataM phoneVerificationM, Connection conn)
			throws ApplicationException {
		try { 
			int returnRows = 0;
			phoneVerificationM.setUpdateBy(userId);
			returnRows = updateTableXRULES_PHONE_VERIFICATION(phoneVerificationM,conn);
			if (returnRows == 0) {
				phoneVerificationM.setCreateBy(userId);
			    phoneVerificationM.setUpdateBy(userId);
				createOrigPhoneVerificationM(phoneVerificationM,conn);
			} 
		} catch (Exception e) {
			logger.fatal(e.getLocalizedMessage());
			throw new ApplicationException(e.getMessage());
		}
	}
	private int updateTableXRULES_PHONE_VERIFICATION(
			PhoneVerificationDataM phoneVerificationM,Connection conn) throws ApplicationException {
		int returnRows = 0;
		PreparedStatement ps = null;
//		Connection conn = null;
		try {
			//conn = Get Connection
//			conn = getConnection();
			StringBuffer sql = new StringBuffer("");
			sql.append("UPDATE XRULES_PHONE_VERIFICATION ");
			sql.append(" SET CONTACT_TYPE = ?, TELEPHONE_NUMBER = ?, PHONE_VER_STATUS = ?, ");
			sql.append(" REMARK = ?, UPDATE_DATE = ?, UPDATE_BY = ? , LIFE_CYCLE = ? ");
			sql.append(" WHERE VER_RESULT_ID = ? AND PHONE_VERIFY_ID = ? ");
			String dSql = String.valueOf(sql);
			logger.debug("Sql=" + dSql);
			ps = conn.prepareStatement(dSql);
			int cnt = 1;
			ps.setString(cnt++, phoneVerificationM.getContactType());
			ps.setString(cnt++, phoneVerificationM.getTelephoneNumber());
			ps.setString(cnt++, phoneVerificationM.getPhoneVerStatus());
			
			ps.setString(cnt++, phoneVerificationM.getRemark());
			ps.setTimestamp(cnt++, ApplicationDate.getTimestamp());
			ps.setString(cnt++, phoneVerificationM.getUpdateBy());
			
			ps.setInt(cnt++, phoneVerificationM.getLifeCycle());
			
			// WHERE CLAUSE
			ps.setString(cnt++, phoneVerificationM.getVerResultId());
			ps.setString(cnt++, phoneVerificationM.getPhoneVerifyId());
			
			returnRows = ps.executeUpdate();
		} catch (Exception e) {
			logger.fatal(e.getLocalizedMessage());
			throw new ApplicationException(e.getMessage());
		} finally {
			try {
//				closeConnection(conn, ps);
				closeConnection(ps);
			} catch (Exception e) {
				logger.fatal(e.getLocalizedMessage());
			}
		}
		return returnRows;
	}

	@Override
	public void deleteNotInKeyPhoneVerification(
			ArrayList<PhoneVerificationDataM> phoneVerificationList,
			String verResultId) throws ApplicationException {
		Connection conn = null;
		try{
			conn = getConnection();
			deleteNotInKeyPhoneVerification(phoneVerificationList, verResultId, conn);
		}catch(Exception e){
			logger.fatal(e);
			throw new ApplicationException(e.getMessage());
		}finally{
			try{
				closeConnection(conn);
			}catch(Exception e){
				logger.fatal(e.getLocalizedMessage());
			}
		}
	}
	@Override
	public void deleteNotInKeyPhoneVerification(
			ArrayList<PhoneVerificationDataM> phoneVerificationList,
			String verResultId, Connection conn) throws ApplicationException {
		deleteNotInKeyTableXRULES_PHONE_VERIFICATION(phoneVerificationList, verResultId, conn);
	}
	private void deleteNotInKeyTableXRULES_PHONE_VERIFICATION(
			ArrayList<PhoneVerificationDataM> phoneVerificationList,
			String verResultId,Connection conn) throws ApplicationException {
		PreparedStatement ps = null;
//		Connection conn = null;
        try {
            // conn = Get Connection
//            conn = getConnection();

            StringBuffer sql = new StringBuffer("");
            sql.append("DELETE FROM XRULES_PHONE_VERIFICATION ");
            sql.append(" WHERE VER_RESULT_ID = ? ");
            
            if (!Util.empty(phoneVerificationList)) {
                sql.append(" AND PHONE_VERIFY_ID NOT IN ( ");

                for (PhoneVerificationDataM phoneVerificationM: phoneVerificationList) {
                    sql.append(" '" + phoneVerificationM.getPhoneVerifyId() + "' , ");
                }

                if (sql.toString().trim().endsWith(",")) {
                    sql.delete(sql.toString().lastIndexOf(","), sql.toString().length());
                }

                sql.append(" ) ");
            }

            String dSql = String.valueOf(sql);
            ps = conn.prepareStatement(dSql);
            ps.setString(1, verResultId);
            
            ps.executeUpdate();
            ps.close();

        } catch (Exception e) {
        	logger.error("ERROR >> ",e);
			throw new ApplicationException(e.getMessage());
		} finally {
			try {
//				closeConnection(conn, ps);
				closeConnection(ps);
			} catch (Exception e) {
				logger.error("ERROR >> ",e);
				throw new ApplicationException(e.getMessage());
			}
		}
	}

}
