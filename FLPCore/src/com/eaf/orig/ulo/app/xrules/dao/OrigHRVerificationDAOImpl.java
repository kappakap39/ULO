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
import com.eaf.orig.ulo.model.app.HRVerificationDataM;

public class OrigHRVerificationDAOImpl extends OrigObjectDAO implements
		OrigHRVerificationDAO {
	public OrigHRVerificationDAOImpl(String userId){
		this.userId = userId;
	}
	public OrigHRVerificationDAOImpl(){
		
	}
	private String userId = "";
	@Override
	public void createOrigHRVerificationM(HRVerificationDataM hRVerificationM,
			Connection conn) throws ApplicationException {
		try {
			logger.debug("hRVerificationM.getHrVerifyId() :"+hRVerificationM.getHrVerifyId());
		    if(Util.empty(hRVerificationM.getHrVerifyId())){
				String hrVerifyId = GenerateUnique.generate(OrigConstant.SeqNames.XRULES_HR_VERIFICATION_PK,conn); 
				hRVerificationM.setHrVerifyId(hrVerifyId);
			}
		    hRVerificationM.setCreateBy(userId);
		    hRVerificationM.setUpdateBy(userId);
			createTableXRULES_HR_VERIFICATION(hRVerificationM,conn);
		} catch (Exception e) {
			logger.fatal(e.getLocalizedMessage());
			throw new ApplicationException(e.getMessage());
		}
	}
	@Override
	public void createOrigHRVerificationM(HRVerificationDataM hRVerificationM)
			throws ApplicationException {
		Connection conn = null;
		try{
			conn = getConnection();
			createOrigHRVerificationM(hRVerificationM, conn);
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

	private void createTableXRULES_HR_VERIFICATION(
			HRVerificationDataM hRVerificationM,Connection conn) throws ApplicationException {
		PreparedStatement ps = null;
//		Connection conn = null;
		try {
			//conn = Get Connection
//			conn = getConnection();
			StringBuffer sql = new StringBuffer("");
			sql.append("INSERT INTO XRULES_HR_VERIFICATION ");
			sql.append("( HR_VERIFY_ID, VER_RESULT_ID, CONTACT_TYPE, ");
			sql.append(" PHONE_NO, PHONE_VER_STATUS, REMARK, ");
			sql.append(" CREATE_BY, CREATE_DATE, UPDATE_BY, UPDATE_DATE )");

			sql.append(" VALUES(?,?,?,  ?,?,?,  ?,?,?,? )");
			String dSql = String.valueOf(sql);
			logger.debug("Sql=" + dSql);
			ps = conn.prepareStatement(dSql);
			int cnt = 1;
			ps.setString(cnt++, hRVerificationM.getHrVerifyId());
			ps.setString(cnt++, hRVerificationM.getVerResultId());
			ps.setString(cnt++, hRVerificationM.getContactType());
			
			ps.setString(cnt++, hRVerificationM.getPhoneNo());
			ps.setString(cnt++, hRVerificationM.getPhoneVerStatus());
			ps.setString(cnt++, hRVerificationM.getRemark());
			
			ps.setString(cnt++, hRVerificationM.getCreateBy());
			ps.setTimestamp(cnt++, ApplicationDate.getTimestamp());
			ps.setString(cnt++, hRVerificationM.getUpdateBy());
			ps.setTimestamp(cnt++, ApplicationDate.getTimestamp());
			
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
	public void deleteOrigHRVerificationM(String verResultId, String hrVerifyId)
			throws ApplicationException {
		try {
			deleteTableXRULES_HR_VERIFICATION(verResultId, hrVerifyId);
		} catch (Exception e) {
			logger.fatal(e.getLocalizedMessage());
			throw new ApplicationException(e.getMessage());
		}
	}

	private void deleteTableXRULES_HR_VERIFICATION(String verResultId,
			String hrVerifyId) throws ApplicationException {
		PreparedStatement ps = null;
		Connection conn = null;
		try {
			//conn = Get Connection
			conn = getConnection();
			StringBuffer sql = new StringBuffer("");
			sql.append(" DELETE XRULES_HR_VERIFICATION ");
			sql.append(" WHERE VER_RESULT_ID = ? ");
			if(!Util.empty(hrVerifyId)) {
				sql.append(" AND HR_VERIFY_ID = ? ");
			}
			String dSql = String.valueOf(sql);
			logger.debug("Sql=" + dSql);
			ps = conn.prepareStatement(dSql);
			ps.setString(1, verResultId);
			if(!Util.empty(hrVerifyId)) {
				ps.setString(2, hrVerifyId);
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
	public ArrayList<HRVerificationDataM> loadOrigHRVerificationM(
			String verResultId) throws ApplicationException {
		Connection conn = null;
		try{
			conn = getConnection();
			return selectTableXRULES_HR_VERIFICATION(verResultId,conn);
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
	public ArrayList<HRVerificationDataM> loadOrigHRVerificationM(
			String verResultId, Connection conn) throws ApplicationException {
		return selectTableXRULES_HR_VERIFICATION(verResultId, conn);
	}
	
	private ArrayList<HRVerificationDataM> selectTableXRULES_HR_VERIFICATION(
			String verResultId,Connection conn) throws ApplicationException {
		PreparedStatement ps = null;
		ResultSet rs = null;
		ArrayList<HRVerificationDataM> hrVerResultList = new ArrayList<HRVerificationDataM>();
		try {
			StringBuffer sql = new StringBuffer("");
			sql.append("SELECT HR_VERIFY_ID, VER_RESULT_ID, CONTACT_TYPE, ");
			sql.append(" PHONE_NO, PHONE_VER_STATUS, REMARK, ");
			sql.append(" CREATE_DATE, CREATE_BY, UPDATE_DATE, UPDATE_BY ");
			sql.append(" FROM XRULES_HR_VERIFICATION WHERE VER_RESULT_ID = ? ");
			String dSql = String.valueOf(sql);
			logger.debug("Sql=" + dSql);
			ps = conn.prepareStatement(dSql);
			rs = null;
			ps.setString(1, verResultId);

			rs = ps.executeQuery();

			while(rs.next()) {
				HRVerificationDataM hrVerResultM = new HRVerificationDataM();
				hrVerResultM.setHrVerifyId(rs.getString("HR_VERIFY_ID"));
				hrVerResultM.setVerResultId(rs.getString("VER_RESULT_ID"));
				hrVerResultM.setContactType(rs.getString("CONTACT_TYPE"));
				
				hrVerResultM.setPhoneNo(rs.getString("PHONE_NO"));
				hrVerResultM.setPhoneVerStatus(rs.getString("PHONE_VER_STATUS"));
				hrVerResultM.setRemark(rs.getString("REMARK"));
				
				hrVerResultM.setCreateBy(rs.getString("CREATE_BY"));
				hrVerResultM.setCreateDate(rs.getTimestamp("CREATE_DATE"));
				hrVerResultM.setUpdateBy(rs.getString("UPDATE_BY"));
				hrVerResultM.setUpdateDate(rs.getTimestamp("UPDATE_DATE"));
				
				hrVerResultList.add(hrVerResultM);
			}

			return hrVerResultList;
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
	public void saveUpdateOrigHRVerificationM(
			HRVerificationDataM hRVerificationM) throws ApplicationException {
		Connection conn = null;
		try{
			conn = getConnection();
			saveUpdateOrigHRVerificationM(hRVerificationM, conn);
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
	public void saveUpdateOrigHRVerificationM(
			HRVerificationDataM hRVerificationM, Connection conn)
			throws ApplicationException {
		try { 
			int returnRows = 0;
			hRVerificationM.setUpdateBy(userId);
			returnRows = updateTableXRULES_HR_VERIFICATION(hRVerificationM,conn);
			if (returnRows == 0) {
				hRVerificationM.setCreateBy(userId);
			    hRVerificationM.setUpdateBy(userId);
				createOrigHRVerificationM(hRVerificationM,conn);
			} 
		} catch (Exception e) {
			logger.fatal(e.getLocalizedMessage());
			throw new ApplicationException(e.getMessage());
		}
	}
	private int updateTableXRULES_HR_VERIFICATION(
			HRVerificationDataM hRVerificationM,Connection conn) throws ApplicationException {
		int returnRows = 0;
		PreparedStatement ps = null;
//		Connection conn = null;
		try {
			//conn = Get Connection
//			conn = getConnection();
			StringBuffer sql = new StringBuffer("");
			sql.append("UPDATE XRULES_HR_VERIFICATION ");
			sql.append(" SET CONTACT_TYPE = ?, PHONE_NO = ?, PHONE_VER_STATUS = ?, ");
			sql.append(" REMARK = ?, UPDATE_DATE = ?, UPDATE_BY = ? ");
			sql.append(" WHERE VER_RESULT_ID = ? AND HR_VERIFY_ID = ? ");
			String dSql = String.valueOf(sql);
			logger.debug("Sql=" + dSql);
			ps = conn.prepareStatement(dSql);
			int cnt = 1;
			ps.setString(cnt++, hRVerificationM.getContactType());
			ps.setString(cnt++, hRVerificationM.getPhoneNo());
			ps.setString(cnt++, hRVerificationM.getPhoneVerStatus());
			
			ps.setString(cnt++, hRVerificationM.getRemark());
			ps.setTimestamp(cnt++, ApplicationDate.getTimestamp());
			ps.setString(cnt++, hRVerificationM.getUpdateBy());
			
			// WHERE CLAUSE
			ps.setString(cnt++, hRVerificationM.getVerResultId());
			ps.setString(cnt++, hRVerificationM.getHrVerifyId());
			
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
	public void deleteNotInKeyHRVerification(
			ArrayList<HRVerificationDataM> hRVerificationList,
			String verResultId) throws ApplicationException {
//		deleteNotInKeyTableXRULES_HR_VERIFICATION(hRVerificationList, verResultId);
		Connection conn = null;
		try{
			conn = getConnection();
			deleteNotInKeyHRVerification(hRVerificationList, verResultId, conn);
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
	public void deleteNotInKeyHRVerification(
			ArrayList<HRVerificationDataM> hRVerificationList,
			String verResultId, Connection conn) throws ApplicationException {
		deleteNotInKeyTableXRULES_HR_VERIFICATION(hRVerificationList, verResultId, conn);
	}
	private void deleteNotInKeyTableXRULES_HR_VERIFICATION(
			ArrayList<HRVerificationDataM> hRVerificationList,
			String verResultId,Connection conn) throws ApplicationException {
		PreparedStatement ps = null;
//		Connection conn = null;
        try {
            // conn = Get Connection
//            conn = getConnection();

            StringBuffer sql = new StringBuffer("");
            sql.append("DELETE FROM XRULES_HR_VERIFICATION ");
            sql.append(" WHERE VER_RESULT_ID = ? ");
            
            if (!Util.empty(hRVerificationList)) {
                sql.append(" AND HR_VERIFY_ID NOT IN ( ");

                for (HRVerificationDataM hrVerificationM: hRVerificationList) {
                    sql.append(" '" + hrVerificationM.getHrVerifyId() + "' , ");
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
