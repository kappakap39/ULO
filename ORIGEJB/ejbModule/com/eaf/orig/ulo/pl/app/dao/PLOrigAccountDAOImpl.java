package com.eaf.orig.ulo.pl.app.dao;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Types;

import org.apache.log4j.Logger;

import com.eaf.orig.profile.model.UserDetailM;
import com.eaf.orig.shared.constant.EJBConstant;
import com.eaf.orig.shared.constant.OrigConstant;
import com.eaf.orig.shared.dao.OrigObjectDAO;
import com.eaf.orig.shared.dao.PLORIGDAOFactory;
import com.eaf.orig.ulo.pl.app.dao.exception.PLOrigApplicationException;
import com.eaf.orig.ulo.pl.model.app.PLAccountDataM;
import com.eaf.orig.ulo.pl.model.app.PLApplicationDataM;
import com.eaf.orig.ulo.pl.model.app.PLPersonalInfoDataM;
import com.eaf.orig.ulo.pl.service.PLORIGEJBService;

public class PLOrigAccountDAOImpl extends OrigObjectDAO implements PLOrigAccountDAO {

	private static Logger log = Logger.getLogger(PLOrigAccountDAOImpl.class);
	
	@Override
	public PLAccountDataM loadAccount(String accId) throws PLOrigApplicationException {
		PreparedStatement ps = null;
		ResultSet rs = null;
		PLAccountDataM accM = new PLAccountDataM();
		Connection conn = null;
		try {
			conn = getConnection();
			StringBuilder sql = new StringBuilder("");
			sql.append("SELECT ACC.IDNO, ACC.TH_TITLE_CODE, ACC.TH_FIRST_NAME, ACC.TH_LAST_NAME, ACC.TH_MID_NAME, ");
			sql.append("ACC.APPROVE_DATE, ACC.APPLICATION_NO, ACC.CARDLINK_CUST_NO, ACC.CARDLINK_FLAG, ACC.CREATE_DATE, ");
			sql.append("ACC.CREATE_BY, ACC.UPDATE_DATE, ACC.UPDATE_BY, ACC.PROJECT_CODE, BS.PRODUCT_ID, ");
			sql.append("ACC.CARDLINK_STATUS, ACC.CARDLINK_DATE, ");
			sql.append("CASE WHEN ACC.CARDLINK_STATUS_RESULT IN (SELECT PARAM_CODE FROM MS_REPORT_PARAM WHERE PARAM_TYPE = 'CARD_LINK_ERROR_CODE') THEN 'N' ELSE 'Y' END ENABLE_REISSUE_CUS_NO ");
			sql.append("FROM AC_ACCOUNT ACC, BUSINESS_CLASS BS ");
			sql.append("WHERE ACCOUNT_ID = ? AND BS.BUS_CLASS_ID = ACC.BUSINESS_CLASS_ID");
			
			String dSql = String.valueOf(sql);
			log.debug("Sql=" + dSql);
			ps = conn.prepareStatement(dSql);
			ps.setString(1, accId);
			int index = 1;
			rs = ps.executeQuery();
			
			if (rs.next()) {
				accM.setIDNO(rs.getString(index++));
				accM.setThTitleCode(rs.getString(index++));
				accM.setThFirstName(rs.getString(index++));
				accM.setThLastName(rs.getString(index++));
				accM.setThMidName(rs.getString(index++));
				
				accM.setApproveDate(rs.getTimestamp(index++));
				accM.setApplicationNo(rs.getString(index++));
				accM.setCardlinkCustNo(rs.getString(index++));
				accM.setCardlinkFlag(rs.getString(index++));
				accM.setCreateDate(rs.getTimestamp(index++));
				
				accM.setCreateBy(rs.getString(index++));
				accM.setUpdateDate(rs.getTimestamp(index++));
				accM.setUpdateBy(rs.getString(index++));
				accM.setProjectCode(rs.getString(index++));
				accM.setBusinessId(rs.getString(index++));
				
				accM.setCardLinkStatus(rs.getString(index++));
				accM.setCardLinkDate(rs.getTimestamp(index++));
				accM.setEnabelReissueCusNo(rs.getString(index++));
				
				PLOrigAccountCardDAO accountCardDAO = PLORIGDAOFactory.getPLOrigAccountCardDAO();
				accM.setPlAccountCardVect(accountCardDAO.loadAccountCardAndCustNo(accId));
				
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
		return accM;
	}

	@Override
	public void saveUpdateAccount(PLApplicationDataM appM) throws PLOrigApplicationException {
		try{
			this.insertTableAC_ACCOUNT(appM);
		}catch (Exception e){
			e.printStackTrace();
			log.fatal("##### Can not save update account :"+e.getMessage());
			throw new PLOrigApplicationException(e.getMessage());
		}
	}
	
	public void insertTableAC_ACCOUNT(PLApplicationDataM appM) throws PLOrigApplicationException{
		
		PreparedStatement ps = null;
		Connection conn = null;
		try {
			
			String accountId = PLORIGEJBService.getGeneratorManager().generateUniqueIDByName(EJBConstant.ACCOUNT_ID);
			appM.setAccountNo(accountId);
			
			conn = getConnection();
			StringBuilder sql = new StringBuilder("");
			sql.append("insert into ac_account (account_id, idno, th_title_code, th_first_name, th_last_name, th_mid_name, approve_date, ");
			sql.append("application_no, cardlink_flag, create_date, create_by, update_date, update_by, project_code, business_class_id) ");
			sql.append("values (?,?,?,?,?,?,sysdate,?,?,sysdate,?,sysdate,?,?,?)");
			
			PLPersonalInfoDataM personM = appM.getPLPersonalInfoDataM(OrigConstant.PERSONAL_TYPE_APPLICANT);
			
			String dSql = String.valueOf(sql);
			log.debug("Sql=" + dSql);
			ps = conn.prepareStatement(dSql);
			ps.setString(1, appM.getAccountNo());
			ps.setString(2, personM.getIdNo());
			ps.setString(3, personM.getThaiTitleName());
			ps.setString(4, personM.getThaiFirstName());
			ps.setString(5, personM.getThaiLastName());
			ps.setString(6, personM.getThaiMidName());
			ps.setString(7, appM.getApplicationNo());
			ps.setString(8, appM.getCardlinkFlag());
			ps.setString(9, appM.getUpdateBy());
			ps.setString(10, appM.getUpdateBy());
			ps.setString(11, appM.getProductCode());
			ps.setString(12, appM.getBusinessClassId());
			
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
	
	public void saveUpdateAccountByPLSQL(PLApplicationDataM appM) throws PLOrigApplicationException {
		// TODO Auto-generated method stub
		PreparedStatement ps = null;
		Connection conn = null;
		try {
			//conn = Get Connection
			conn = getConnection();
			StringBuffer sql = new StringBuffer("");
			sql.append(" CALL pka_account_card.process_account_card(?)");
			String dSql = String.valueOf(sql);
			logger.debug("@@@@@ saveUpdateAccountByPLSQL SQL:"+dSql);
			logger.debug("@@@@@ appM.getAppRecordID() :"+appM.getAppRecordID());
			ps = conn.prepareCall(dSql);
			ps.setString(1,appM.getAppRecordID());
			ps.executeQuery();
		} catch (Exception e) {
			e.printStackTrace();
			throw new PLOrigApplicationException(e.getMessage());
		} finally {
			try {
				closeConnection(conn, ps);
			} catch (Exception e) {
				logger.fatal(e.getLocalizedMessage());
				throw new PLOrigApplicationException(e.getMessage());
			}
		}
	}

	@Override
	public String reissueCustNo(String accId) throws PLOrigApplicationException {
		CallableStatement callStmt = null;
		PreparedStatement ps = null;
		Connection conn = null;
		try{
			String custNo = null;
			conn = getConnection();
			callStmt = conn.prepareCall("{? = call f_gen_cust_no()}");		
			callStmt.registerOutParameter(1, Types.VARCHAR);		
			callStmt.execute();
			
			custNo = callStmt.getString(1);
			
			StringBuffer sql = new StringBuffer("");
			sql.append("UPDATE AC_ACCOUNT SET AC_ACCOUNT.CARDLINK_CUST_NO = ?,  AC_ACCOUNT.CARDLINK_STATUS = ? WHERE AC_ACCOUNT.ACCOUNT_ID = ?");
			String dSql = String.valueOf(sql);
			log.debug("Sql=" + dSql);
			ps = conn.prepareStatement(dSql);
			ps.setString(1, custNo);
			ps.setString(2, OrigConstant.cardLinkStatus.RE_ISSUED);
			ps.setString(3, accId);
			
			ps.executeUpdate();
			
			return custNo;
		} catch(Exception e){			
			logger.fatal("reissueCustNo  Error "+e.getLocalizedMessage());
			throw new PLOrigApplicationException(e.getMessage());
		} finally {
			if (callStmt != null){
				logger.debug("reissueCustNo completed ");
				try{
					callStmt.close();
				} catch (Exception e) {
				}
			}
		}
		
	}

	@Override
	public void updateCardLinkStatus(String accId, String cardLink_Status, String userName) throws PLOrigApplicationException {
		Connection conn = null;
		PreparedStatement ps = null;
		try {

			conn = getConnection();
			StringBuilder sql = new StringBuilder("");

			sql.append("UPDATE AC_ACCOUNT ");
			sql.append(" SET CARDLINK_STATUS=?, UPDATE_BY=?, UPDATE_DATE=SYSDATE ");
			sql.append(" WHERE ACCOUNT_ID=? ");
			String dSql = String.valueOf(sql);
			log.debug("Sql=" + dSql);
			ps = conn.prepareStatement(dSql);
			
			int index = 1;
			ps.setString(index++, cardLink_Status);
			ps.setString(index++, userName);
			ps.setString(index++, accId);
			
			ps.executeUpdate();
			
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
	}
	@Override
	public PLAccountDataM loadAccountByApplicationNo(String applicationNo) throws PLOrigApplicationException {
		PreparedStatement ps = null;
		ResultSet rs = null;
		Connection conn = null;
		try {
			conn = getConnection();
			StringBuilder sql = new StringBuilder("");
			sql.append("SELECT ACC.IDNO, ACC.TH_TITLE_CODE, ACC.TH_FIRST_NAME, ACC.TH_LAST_NAME, ACC.TH_MID_NAME, ");
			sql.append("ACC.APPROVE_DATE, ACC.APPLICATION_NO, ACC.CARDLINK_CUST_NO, ACC.CARDLINK_FLAG, ACC.CREATE_DATE, ");
			sql.append("ACC.CREATE_BY, ACC.UPDATE_DATE, ACC.UPDATE_BY, ACC.PROJECT_CODE, BS.PRODUCT_ID, ");
			sql.append("ACC.CARDLINK_STATUS, ACC.CARDLINK_DATE ,ACCOUNT_ID");
			sql.append("FROM AC_ACCOUNT ACC, BUSINESS_CLASS BS ");
			sql.append("WHERE ACC.APPLICATION_NO = ? AND BS.BUS_CLASS_ID = ACC.BUSINESS_CLASS_ID");
			
			String dSql = String.valueOf(sql);
			log.debug("Sql=" + dSql);
			ps = conn.prepareStatement(dSql);
			ps.setString(1, applicationNo);
			int index = 1;
			rs = ps.executeQuery();
			
			PLAccountDataM accM = new PLAccountDataM();
			
			if (rs.next()) {
				accM.setIDNO(rs.getString(index++));
				accM.setThTitleCode(rs.getString(index++));
				accM.setThFirstName(rs.getString(index++));
				accM.setThLastName(rs.getString(index++));
				accM.setThMidName(rs.getString(index++));
				
				accM.setApproveDate(rs.getTimestamp(index++));
				accM.setApplicationNo(rs.getString(index++));
				accM.setCardlinkCustNo(rs.getString(index++));
				accM.setCardlinkFlag(rs.getString(index++));
				accM.setCreateDate(rs.getTimestamp(index++));
				
				accM.setCreateBy(rs.getString(index++));
				accM.setUpdateDate(rs.getTimestamp(index++));
				accM.setUpdateBy(rs.getString(index++));
				accM.setProjectCode(rs.getString(index++));
				accM.setBusinessId(rs.getString(index++));
				
				accM.setCardLinkStatus(rs.getString(index++));
				accM.setCardLinkDate(rs.getTimestamp(index++));				
				PLOrigAccountCardDAO plOrigAccountCardDAO = PLORIGDAOFactory.getPLOrigAccountCardDAO();
				accM.setPlAccountCardVect(plOrigAccountCardDAO.loadAccountCardAndCustNo(accM.getAccountId()));				
			}
			
			return accM;
		} catch (Exception e) {
			log.fatal(e.getLocalizedMessage());
			throw new PLOrigApplicationException(e.getMessage());
		} finally {
			try {
				closeConnection(conn, rs, ps);
			} catch (Exception e) {
				log.fatal(e.getLocalizedMessage());
				throw new PLOrigApplicationException(e.getMessage());
			}
		}
	}
	@Override
	public void updateCASHDAY1_STATUS(String application_no, String cashDay1_Status, String userName) throws PLOrigApplicationException {
		Connection conn = null;
		PreparedStatement ps = null;
		try {

			conn = getConnection();
			StringBuilder sql = new StringBuilder("");

			sql.append("UPDATE AC_ACCOUNT ");
			sql.append(" SET CASHDAY1_STATUS=?, UPDATE_BY=?, UPDATE_DATE=SYSDATE ");
			sql.append(" WHERE APPLICATION_NO=? ");
			String dSql = String.valueOf(sql);
			log.debug("Sql=" + dSql);
			ps = conn.prepareStatement(dSql);
			
			int index = 1;
			ps.setString(index++, cashDay1_Status);
			ps.setString(index++, userName);
			ps.setString(index++, application_no);
			
			ps.executeUpdate();
			
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
	}

	@Override
	public void InactiveAccount(String appNo, UserDetailM userM) throws PLOrigApplicationException {
		PreparedStatement ps = null;
		Connection conn = null;
		try{
			conn = getConnection();
			StringBuilder sql = new StringBuilder("");

			sql.append("UPDATE AC_ACCOUNT AC SET AC.ACCOUNT_STATUS = ?, AC.UPDATE_BY = ?, AC.UPDATE_DATE = SYSDATE WHERE AC.APPLICATION_NO = ? ");
			String dSql = String.valueOf(sql);
			log.debug("Sql=" + dSql);
			ps = conn.prepareStatement(dSql);
			
			int index = 1;
			ps.setString(index++, OrigConstant.Status.STATUS_INACTIVE);
			ps.setString(index++, userM.getUserName());
			ps.setString(index++, appNo);
			
			ps.executeUpdate();
			
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
		
	}

	@Override
	public int getCardLinkStatus(String accId) throws PLOrigApplicationException {
		Connection conn = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		try {
			conn = getConnection();
			StringBuilder sql = new StringBuilder("");
			sql.append("SELECT AC.CARDLINK_STATUS FROM AC_ACCOUNT AC WHERE AC.ACCOUNT_ID = ? AND AC.ACCOUNT_STATUS = ?");
			
			String dSql = String.valueOf(sql);
			log.debug("Sql=" + dSql);
			ps = conn.prepareStatement(dSql);
			ps.setString(1, accId);
			ps.setString(2, OrigConstant.ACTIVE_FLAG);
			
			rs = ps.executeQuery();
			
			if (rs.next()) {
				return rs.getInt(1);
			}			
			return 999;
		} catch (Exception e) {
			log.fatal(e.getLocalizedMessage());
			throw new PLOrigApplicationException(e.getMessage());
		} finally {
			try {
				closeConnection(conn, rs, ps);
			} catch (Exception e) {
				log.fatal(e.getLocalizedMessage());
				throw new PLOrigApplicationException(e.getMessage());
			}
		}
		
	}
	
	@Override
	public void setInActiveAccount(String accountID, UserDetailM userM) throws PLOrigApplicationException {
		PreparedStatement ps = null;
		Connection conn = null;
		try{
			conn = getConnection();
			StringBuilder sql = new StringBuilder("");

			sql.append(" UPDATE AC_ACCOUNT SET ACCOUNT_STATUS = ?, UPDATE_BY = ?, UPDATE_DATE = SYSDATE WHERE ACCOUNT_ID = ? ");
			String dSql = String.valueOf(sql);
			log.debug("Sql=" + dSql);
			ps = conn.prepareStatement(dSql);
			
			int index = 1;
			ps.setString(index++, OrigConstant.Status.STATUS_INACTIVE);
			ps.setString(index++, userM.getUserName());
			ps.setString(index++, accountID);			
			ps.executeUpdate();
			
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
	}
	
}
