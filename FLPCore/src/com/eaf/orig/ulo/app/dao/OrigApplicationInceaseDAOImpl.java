package com.eaf.orig.ulo.app.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;

import org.apache.log4j.Logger;

import com.eaf.core.ulo.common.date.ApplicationDate;
import com.eaf.core.ulo.common.util.Util;
import com.eaf.orig.shared.constant.OrigConstant;
import com.eaf.orig.shared.dao.OrigObjectDAO;
import com.eaf.orig.ulo.app.dao.exception.ApplicationException;
import com.eaf.orig.ulo.app.util.GenerateUnique;
import com.eaf.orig.ulo.model.app.ApplicationIncreaseDataM;

public class OrigApplicationInceaseDAOImpl extends OrigObjectDAO implements OrigApplicationInceaseDAO{
	private static transient Logger logger = Logger.getLogger(OrigApplicationInceaseDAOImpl.class);
	public OrigApplicationInceaseDAOImpl(){
		
	}
	public OrigApplicationInceaseDAOImpl(String userId){
		this.userId = userId;
	}
	private String userId = "";
	@Override
	public void createOrigApplicationIncreaseM(ApplicationIncreaseDataM applicationincreaseM)throws ApplicationException {
		Connection conn = null;
		try{
			conn = getConnection();
			createOrigApplicationIncreaseM(applicationincreaseM, conn);
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
	public void createOrigApplicationIncreaseM(ApplicationIncreaseDataM applicationincreaseM,Connection conn)throws ApplicationException {
		logger.debug("applicationincreaseM.getApplicationRecordId() :"+applicationincreaseM.getApplicationRecordId());
	    if(Util.empty(applicationincreaseM.getApplicationRecordId())){
			String prmApplicationRecordID = GenerateUnique.generate(OrigConstant.SeqNames.ORIG_APPLICATION_PK,conn); 
			applicationincreaseM.setCreateBy(userId);
			applicationincreaseM.setApplicationRecordId(prmApplicationRecordID);
		}
	    createTableORIG_APPLICATION_INCREASE(applicationincreaseM,conn);
	}
	
	@Override
	public void deleteOrigApplicationIncreaseM(ApplicationIncreaseDataM applicationincreaseM)throws ApplicationException {
		//For delete By AppId
	}
	@Override
	public ArrayList<ApplicationIncreaseDataM> loadOrigApplicationIncreaseM(
			String applicationGroupId, Connection conn)
			throws ApplicationException {
		return selectTableORIG_APPLICATIONINCREASE(applicationGroupId, conn);
	}
	@Override
	public ArrayList<ApplicationIncreaseDataM> loadOrigApplicationIncreaseM(String applicationGroupId)throws ApplicationException {
		Connection conn = null;
		try{
			conn = getConnection();
			return selectTableORIG_APPLICATIONINCREASE(applicationGroupId,conn);
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
	public void saveOrigApplicationIncreaseM(ApplicationIncreaseDataM applicationincreaseM)throws ApplicationException {
		Connection conn = null;
		try{
			conn = getConnection();
			saveOrigApplicationIncreaseM(applicationincreaseM, conn);
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
	public void saveOrigApplicationIncreaseM(
			ApplicationIncreaseDataM applicationincreaseM,Connection conn)
			throws ApplicationException {
		int returnRows = 0;
		applicationincreaseM.setUpdateBy(userId);
		returnRows = updateTableORIG_APPLICATION_INCREASE(applicationincreaseM,conn);
		if (returnRows == 0) {
			applicationincreaseM.setCreateBy(userId);
			createOrigApplicationIncreaseM(applicationincreaseM,conn);
		}
	}
	
	private void createTableORIG_APPLICATION_INCREASE(ApplicationIncreaseDataM applicationincreaseM,Connection conn) throws ApplicationException {
		PreparedStatement ps = null;
//		Connection conn = null;
		try {
			//conn = Get Connection
//			conn = getConnection();
			StringBuilder sql = new StringBuilder("");
		
			sql.append(" INSERT INTO ORIG_APPLICATION_INCREASE ");
			sql.append(" ( APPLICATION_RECORD_ID, APPLICATION_GROUP_ID, CARD_NO, ");
			sql.append(" CARD_NO_MARK, CARDLINK_CUST_NO, TH_NAME,");
			sql.append(" UPDATE_BY, UPDATE_DATE, CREATE_BY, ");
			sql.append(" CREATE_DATE, MAIN_CARD_NO ) ");
			sql.append(" VALUES(?,?,?,  ?,?,?,  ?,?,?,  ?, ?) ");
			String dSql = sql.toString();
			logger.debug("Sql=" + dSql);
			ps = conn.prepareStatement(dSql);
			int cnt = 1;
			ps.setString(cnt++, applicationincreaseM.getApplicationRecordId());
			ps.setString(cnt++, applicationincreaseM.getApplicationGroupId());
			ps.setString(cnt++, applicationincreaseM.getCardNoEncrypted());
			
			ps.setString(cnt++, applicationincreaseM.getCardNoMark());
			ps.setString(cnt++, applicationincreaseM.getCardLinkCustNo());
			ps.setString(cnt++, applicationincreaseM.getThName());
			
			ps.setString(cnt++, applicationincreaseM.getUpdateBy());
			ps.setTimestamp(cnt++, ApplicationDate.getTimestamp());
			ps.setString(cnt++, applicationincreaseM.getCreateBy());
			
			ps.setTimestamp(cnt++, ApplicationDate.getTimestamp());
			ps.setString(cnt++, applicationincreaseM.getMainCardNo());
			
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
	
	private int updateTableORIG_APPLICATION_INCREASE(ApplicationIncreaseDataM applicationincreaseM,Connection conn) throws ApplicationException {
		int returnRows = 0;
		PreparedStatement ps = null;
//		Connection conn = null;
		try {
			//conn = Get Connection
//			conn = getConnection();
			StringBuilder sql = new StringBuilder("");
			sql.append(" UPDATE ORIG_APPLICATION_INCREASE ");
			sql.append(" SET APPLICATION_RECORD_ID = ?, APPLICATION_GROUP_ID = ?, CARD_NO = ?, ");
			sql.append(" CARD_NO_MARK = ?, CARDLINK_CUST_NO = ?, TH_NAME = ?, ");
			sql.append(" UPDATE_BY = ?, UPDATE_DATE = ?, CREATE_BY = ?, ");
			sql.append(" CREATE_DATE = ?, MAIN_CARD_NO = ? ");
			sql.append(" WHERE APPLICATION_RECORD_ID = ? ");
			String dSql = sql.toString();
			logger.debug("Sql=" + dSql);
			ps = conn.prepareStatement(dSql);
			int cnt = 1;
			ps.setString(cnt++, applicationincreaseM.getApplicationRecordId());
			ps.setString(cnt++, applicationincreaseM.getApplicationGroupId());
			ps.setString(cnt++, applicationincreaseM.getCardNoEncrypted());
			
			ps.setString(cnt++, applicationincreaseM.getCardNoMark());
			ps.setString(cnt++, applicationincreaseM.getCardLinkCustNo());
			ps.setString(cnt++, applicationincreaseM.getThName());
			
			ps.setString(cnt++, applicationincreaseM.getUpdateBy());
			ps.setTimestamp(cnt++, ApplicationDate.getTimestamp());
			ps.setString(cnt++, applicationincreaseM.getCreateBy());
			
			ps.setTimestamp(cnt++, ApplicationDate.getTimestamp());
			ps.setString(cnt++, applicationincreaseM.getMainCardNo());
			
			// WHERE CLAUSE
			ps.setString(cnt++, applicationincreaseM.getApplicationRecordId());
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
	public void deleteNotInKeyApplicationIncrease(ArrayList<ApplicationIncreaseDataM> ApplicationIncreases,String applicationGroupId) throws ApplicationException {
		Connection conn = null;
		try{
			conn = getConnection();
			deleteNotInKeyApplicationIncrease(ApplicationIncreases, applicationGroupId, conn);
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
	public void deleteNotInKeyApplicationIncrease(
			ArrayList<ApplicationIncreaseDataM> ApplicationIncreases,
			String applicationGroupId, Connection conn)
			throws ApplicationException {
		deleteNotInKeyTableORIG_APPLICATIONINCREASE(ApplicationIncreases, applicationGroupId, conn);
	}
	private void deleteNotInKeyTableORIG_APPLICATIONINCREASE(ArrayList<ApplicationIncreaseDataM> appList, String applicationGroupId,Connection conn) throws ApplicationException {
		PreparedStatement ps = null;
//		Connection conn = null;
		try {
            // conn = Get Connection
//            conn = getConnection();

            StringBuffer sql = new StringBuffer("");
            sql.append("DELETE FROM ORIG_APPLICATION_INCREASE ");
            sql.append(" WHERE APPLICATION_GROUP_ID = ? ");
            
            if (!Util.empty(appList)) {
                sql.append(" AND APPLICATION_RECORD_ID NOT IN ( ");
                for (ApplicationIncreaseDataM appM: appList) {
                	logger.debug("appM.getApplicationRecordId() = "+appM.getApplicationRecordId());
                    sql.append(" '" + appM.getApplicationRecordId() + "', ");
                }
                
                if (sql.toString().trim().endsWith(",")) {
                    sql.delete(sql.toString().lastIndexOf(","), sql.toString().length());
                }

                sql.append(" ) ");
            }

            String dSql = String.valueOf(sql);
            logger.debug("Sql=" + dSql);
			ps = conn.prepareStatement(dSql);
            ps.setString(1, applicationGroupId);
            
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
	
	private ArrayList<String> selectNotInKeyTableORIG_APPLICATIONIncrease(ArrayList<ApplicationIncreaseDataM> appList, String applicationGroupId) throws ApplicationException {
		PreparedStatement ps = null;
		Connection conn = null;
		ResultSet rs = null;
		ArrayList<String> idList = new ArrayList<String>();
		try {
            // conn = Get Connection
            conn = getConnection();

            StringBuffer sql = new StringBuffer("");
            sql.append("SELECT APPLICATION_RECORD_ID FROM ORIG_APPLICATION_INCREASE ");
            sql.append(" WHERE APPLICATION_GROUP_ID = ? ");
            
            if (!Util.empty(appList)) {
                sql.append(" AND APPLICATION_RECORD_ID NOT IN ( ");

                for (ApplicationIncreaseDataM appM: appList) {
                    sql.append(" '" + appM.getApplicationRecordId()+ "' , ");
                }

                if (sql.toString().trim().endsWith(",")) {
                    sql.delete(sql.toString().lastIndexOf(","), sql.toString().length());
                }

                sql.append(" ) ");
            }

            String dSql = String.valueOf(sql);
            logger.debug("Sql=" + dSql);
			ps = conn.prepareStatement(dSql);
            ps.setString(1, applicationGroupId);
            
            rs = ps.executeQuery();
            while(rs.next()) {
            	String keyId =  rs.getString("APPLICATION_RECORD_ID");
            	idList.add(keyId);
            }

            return idList;
        } catch (Exception e) {
        	logger.error("ERROR >> ",e);
			throw new ApplicationException(e.getMessage());
		} finally {
			try {
				closeConnection(conn, rs, ps);
			} catch (Exception e) {
				logger.error("ERROR >> ",e);
				throw new ApplicationException(e.getMessage());
			}
		}
	}
	
	private ArrayList<ApplicationIncreaseDataM> selectTableORIG_APPLICATIONINCREASE(String applicationRecordId,Connection conn) throws ApplicationException {
		PreparedStatement ps = null;
		ResultSet rs = null;
		ArrayList<ApplicationIncreaseDataM> applicationIncreases = new ArrayList<ApplicationIncreaseDataM>();
		ApplicationIncreaseDataM applicationIncreaseDataM = null;
		try {
			StringBuilder sql = new StringBuilder("");
			sql.append("SELECT APPLICATION_RECORD_ID, APPLICATION_GROUP_ID, CARD_NO, ");
			sql.append(" CARD_NO_MARK, CARDLINK_CUST_NO, TH_NAME, UPDATE_BY, ");
			sql.append(" UPDATE_DATE, CREATE_BY, CREATE_DATE, MAIN_CARD_NO ");
			sql.append(" FROM ORIG_APPLICATION_INCREASE  WHERE APPLICATION_GROUP_ID = ? ");
			String dSql = sql.toString();
			logger.debug("Sql=" + dSql);
			ps = conn.prepareStatement(dSql);
			rs = null;
			ps.setString(1, applicationRecordId);

			rs = ps.executeQuery();

			while (rs.next()) {
				applicationIncreaseDataM = new ApplicationIncreaseDataM();
				applicationIncreaseDataM.setApplicationRecordId(applicationRecordId);
				applicationIncreaseDataM.setApplicationGroupId(rs.getString("APPLICATION_GROUP_ID"));
				applicationIncreaseDataM.setCardNoEncrypted(rs.getString("CARD_NO"));
				applicationIncreaseDataM.setCardNoMark(rs.getString("CARD_NO_MARK"));
				applicationIncreaseDataM.setCardLinkCustNo(rs.getString("CARDLINK_CUST_NO"));
				applicationIncreaseDataM.setThName(rs.getString("TH_NAME"));
				applicationIncreaseDataM.setCreateBy(rs.getString("CREATE_BY"));
				applicationIncreaseDataM.setCreateDate(rs.getTimestamp("CREATE_DATE"));
				applicationIncreaseDataM.setUpdateBy(rs.getString("UPDATE_BY"));
				applicationIncreaseDataM.setUpdateDate(rs.getTimestamp("UPDATE_DATE"));
				applicationIncreaseDataM.setMainCardNo(rs.getString("MAIN_CARD_NO"));
				applicationIncreases.add(applicationIncreaseDataM);
			}
			return applicationIncreases;
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
	
}
