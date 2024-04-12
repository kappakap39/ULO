/*
 * Created on Sep 27, 2007
 * Created by Sankom Sanpunya
 * 
 * Copyright (c) 2007 Avalant Co.,Ltd.
 * 20 North Sathorn Road, 15-16th Floor Bubhajit Bldg., Silom, Bangrak, Bangkok 10500, Thailand
 * All rights reserved.
 *
 * This software is the confidential and prorietary infomation of
 * Avalant Co.,Ltd. ("Confidential Infomation"). You shall not
 * disclose such Confidential Infomation and shall use it only in
 * accordance with the terms of the license agreement you entered into
 * with Avalant Co.,Ltd.
 * 
 */
package com.eaf.orig.shared.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.Vector;

import org.apache.log4j.Logger;

import com.eaf.orig.shared.dao.exceptions.OrigCustomerFinnanceMException;
import com.eaf.orig.shared.model.BankDataM;

/**
 * @author Sankom Sanpunya
 * 
 * Type: OrigCustomerFinanceMDAOImpl
 */
public class OrigCustomerFinanceMDAOImpl extends OrigObjectDAO implements
		OrigCustomerFinanceMDAO {
	private static Logger log = Logger
			.getLogger(OrigCustomerFinanceMDAOImpl.class);

	/**
	 *  
	 */
	public OrigCustomerFinanceMDAOImpl() {
		super();
		
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.eaf.orig.share.dao.OrigCustomerFinnanceMDAO#createModelOrigCustomerFinnanceM(com.eaf.orig.shared.model.CustomerFinanceDataM)
	 */
	public void createModelOrigCustomerFinnanceM(
			BankDataM prmBankDataM)
			throws OrigCustomerFinnanceMException {
		try {
			createTableORIG_CUSTOMER_FINANCE(prmBankDataM, "");
		} catch (Exception e) {
			log.fatal(e.getLocalizedMessage());
			throw new OrigCustomerFinnanceMException(e.getMessage());
		}

	}

	/**
	 * @param prmBankDataM
	 */
	private void createTableORIG_CUSTOMER_FINANCE(
			BankDataM prmBankDataM, String personalID)
			throws OrigCustomerFinnanceMException {
		PreparedStatement ps = null;
		Connection conn = null;
		try {
			//conn = Get Connection
			conn = getConnection();
			StringBuffer sql = new StringBuffer("");
			/*sql.append("INSERT INTO ORIG_CUSTOMER_FINANCE ");
			sql
					.append("( APPLICATION_RECORD_ID,CMPCDE,IDNO ,SEQ,ORIG_OWNER ,CREATE_BY");
			sql.append(" ,CREATE_DATE,UPDATE_BY,UPDATE_DATE   )");
			sql.append(" VALUES(?,?,?,?,?  ,?,SYSDATE,?,SYSDATE ) ");
			String dSql = String.valueOf(sql);
			log.debug("Sql="+dSql);
			ps = conn.prepareStatement(dSql);
			ps.setString(1, prmBankDataM.getAppRecodeID());
			ps.setString(2, prmBankDataM.getCmpCode());
			ps.setString(3, prmBankDataM.getPersonalID());
			ps.setInt(4, prmBankDataM.getSeq());
			ps.setString(5, prmBankDataM.getOrigOwner());
			ps.setString(6, prmBankDataM.getCreateBy());
			ps.setString(7, prmBankDataM.getUpdateBy());*/
			sql.append("INSERT INTO ORIG_FINANCIAL_INFO (");
			/*sql.append("CMPCDE,IDNO,SEQ,FINTYP,FINNO");
			sql.append(",BANK ,BBRANCH,OPNDTE,EXPDTE,FINAMT");
			sql.append(",RECSTS,UPDDTE,UPDBY");
			*/
			sql.append(" PERSONAL_ID,SEQ,FINANCIAL_TYPE,FINANCIAL_NO,BANK");
			sql.append(",BRANCH,OPEN_DATE,EXP_DATE,FINANCIAL_AMOUNT,CREATE_DATE");
			sql.append(",CREATE_BY,UPDATE_DATE,UPDATE_BY");
			sql.append(") VALUES (?,?,?,?,?  ,?,?,?,?,SYSDATE  ,?,SYSDATE,? )");

			String dSql = String.valueOf(sql);
			ps = conn.prepareStatement(dSql);
			logger.debug("createTableHPMSHP01M sql=" + dSql);
			ps.setString(1, personalID);
			ps.setInt(2, prmBankDataM.getSeq());
			ps.setString(3, prmBankDataM.getFinancialType());
			ps.setString(4, prmBankDataM.getAccountNo());
			ps.setString(5, prmBankDataM.getBankCode());

			ps.setString(6, prmBankDataM.getBranchCode());
			ps.setDate(7, this.parseDate(prmBankDataM.getOpenDate()));
			ps.setDate(8, this.parseDate(prmBankDataM.getExpiryDate()));
			ps.setBigDecimal(9, prmBankDataM.getAmount());
			//ps.setDate(10, this.parseDate(prmBankDataM.getCreateDate()));
			
			ps.setString(10, prmBankDataM.getCreateBy());
			//ps.setString(12, OrigConstant.EXT_DEFAULT_RECSTS);
			ps.setString(11, prmBankDataM.getUpdateBy());
			ps.executeUpdate();
		} catch (Exception e) {
			log.fatal(e.getLocalizedMessage());
			throw new OrigCustomerFinnanceMException(e.getMessage());
		} finally {
			try {
				closeConnection(conn, ps);
			} catch (Exception e) {
				log.fatal(e.getLocalizedMessage());
			}
		}

	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.eaf.orig.share.dao.OrigCustomerFinnanceMDAO#deleteModelOrigCustomerFinnanceM(com.eaf.orig.shared.model.BankDataM)
	 */
	public void deleteModelOrigCustomerFinnanceM(
			BankDataM prmBankDataM)
			throws OrigCustomerFinnanceMException {
		try {
			deleteTableORIG_CUSTOMER_FINANCE(prmBankDataM);
		} catch (Exception e) {
			log.fatal(e.getLocalizedMessage());
			throw new OrigCustomerFinnanceMException(e.getMessage());
		}

	}

	/**
	 * @param prmBankDataM
	 */
	private void deleteTableORIG_CUSTOMER_FINANCE(
			BankDataM prmBankDataM)
			throws OrigCustomerFinnanceMException {
		PreparedStatement ps = null;
		Connection conn = null;
		try {
			//conn = Get Connection
			conn = getConnection();
			StringBuffer sql = new StringBuffer("");
			sql.append("DELETE ORIG_CUSTOMER_FINANCE ");
			sql.append(" WHERE APPLICATION_RECORD_ID = ? AND SEQ= ? AND CMPCDE=? AND IDNO=?");
			String dSql = String.valueOf(sql);
			log.debug("Sql="+dSql);
			ps = conn.prepareStatement(dSql);
			ps.setString(1, prmBankDataM.getAppRecodeID());
			ps.setInt(2, prmBankDataM.getSeq());
			ps.setString(3, prmBankDataM.getCmpCode());
			ps.setString(4, prmBankDataM.getPersonalID());
			ps.executeUpdate();

		} catch (Exception e) {
			log.fatal(e.getLocalizedMessage());
			throw new OrigCustomerFinnanceMException(e.getMessage());
		} finally {
			try {
				closeConnection(conn, ps);
			} catch (Exception e) {
				log.fatal(e.getLocalizedMessage());
				throw new OrigCustomerFinnanceMException(e.getMessage());
			}
		}

	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.eaf.orig.share.dao.OrigCustomerFinnanceMDAO#loadModelOrigCustomerFinnanceM(java.lang.String,
	 *      int)
	 */
	public Vector loadModelOrigCustomerFinnanceM(
			String applicationRecordId, String personalID, String providerUrlEXT, String jndiEXT)
			throws OrigCustomerFinnanceMException {
		try {
			Vector result = selectTableORIG_CUSTOMER_FINANCE(applicationRecordId, personalID, providerUrlEXT, jndiEXT);
			return result;
		} catch (Exception e) {
			log.fatal(e.getLocalizedMessage());
			throw new OrigCustomerFinnanceMException(e.getMessage());
		}
	}

	/**
	 * @param personalID
	 * @return
	 */
	private Vector selectTableORIG_CUSTOMER_FINANCE(
			String applicationRecordId, String personalID, String providerUrlEXT, String jndiEXT) throws OrigCustomerFinnanceMException {
		PreparedStatement ps = null;
		ResultSet rs = null;
		Connection conn = null;
		try {
			//conn = Get Connection
			conn = getConnection();
			StringBuffer sql = new StringBuffer("");
			/*sql.append("SELECT   CMPCDE,IDNO ,SEQ  ,ORIG_OWNER,CREATE_BY,CREATE_DATE ,UPDATE_BY,UPDATE_DATE  ");
			sql.append(" FROM ORIG_CUSTOMER_FINANCE WHERE APPLICATION_RECORD_ID = ? AND IDNO = ?");
			String dSql = String.valueOf(sql);
			log.debug("Sql="+dSql);
			ps = conn.prepareStatement(dSql);
			rs = null;
			ps.setString(1, applicationRecordId);
			ps.setString(2, idNo);*/
			sql.append("SELECT ");
			sql.append(" SEQ,FINANCIAL_TYPE,FINANCIAL_NO,BANK,BRANCH");
			sql.append(" ,OPEN_DATE,EXP_DATE,FINANCIAL_AMOUNT,UPDATE_DATE,UPDATE_BY");
			sql.append(" FROM ORIG_FINANCIAL_INFO WHERE PERSONAL_ID = ?");
			String dSql = String.valueOf(sql);
			log.debug("Sql="+dSql);
			ps = conn.prepareStatement(dSql);
			//rs = null;
			ps.setString(1, personalID);

			rs = ps.executeQuery();
			Vector vBankDataM = new Vector();
			
			while (rs.next()) {
				BankDataM bankDataM = new BankDataM();
				bankDataM.setSeq(rs.getInt(1));
				bankDataM.setFinancialType(rs.getString(2));
				bankDataM.setAccountNo(rs.getString(3));
				bankDataM.setBankCode(rs.getString(4));
				bankDataM.setBranchCode(rs.getString(5));
				
				bankDataM.setOpenDate(rs.getDate(6));
				bankDataM.setExpiryDate(rs.getDate(7));
				bankDataM.setAmount(rs.getBigDecimal(8));
				bankDataM.setUpdateDate(rs.getTimestamp(9));
				bankDataM.setUpdateBy(rs.getString(10));
				//Load in EXT
				/*ApplicationEXTManager applicationEXTManager = ORIGEJBService.getApplicationEXTManager(providerUrlEXT, jndiEXT);
				bankDataM = applicationEXTManager.loadPersonalFinance(bankDataM);
				*/
				vBankDataM.add(bankDataM);
			}
			return vBankDataM;
		} catch (Exception e) {
			log.fatal(e.getLocalizedMessage());
			throw new OrigCustomerFinnanceMException(e.getMessage());
		} finally {
			try {
				closeConnection(conn, rs, ps);
			} catch (Exception e) {
				log.fatal(e.getLocalizedMessage());
			}
		}
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.eaf.orig.share.dao.OrigCustomerFinnanceMDAO#saveUpdateModelOrigCustomerFinnanceM(com.eaf.orig.shared.model.CustomerFinanceDataM)
	 */
	public void saveUpdateModelOrigCustomerFinnanceM(
			BankDataM prmBankDataM, String personalID)
			throws OrigCustomerFinnanceMException {
		double returnRows = 0;
		try {
			returnRows = updateTableORIG_CUSTOMER_FINANCE(prmBankDataM, personalID);
			if (returnRows == 0) {
				log.debug("New record then can't update record in table ORIG_CUSTOMER_FINANCE then call Insert method");
				createTableORIG_CUSTOMER_FINANCE(prmBankDataM, personalID);
			}
		} catch (Exception e) {
			log.fatal(e.getLocalizedMessage());
			throw new OrigCustomerFinnanceMException(e.getMessage());
		}
	}

	/**
	 * @param prmCustomerFinanceDataM
	 * @return
	 */
	private double updateTableORIG_CUSTOMER_FINANCE(
			BankDataM prmBankDataM, String personalID)
			throws OrigCustomerFinnanceMException {
		double returnRows = 0;
		PreparedStatement ps = null;
		Connection conn = null;
		try {
			//conn = Get Connection
			conn = getConnection();
			StringBuffer sql = new StringBuffer("");
			sql.append("UPDATE ORIG_FINANCIAL_INFO ");
			/*sql.append(" SET  ORIG_OWNER=?,UPDATE_By=?,UPDATE_DATE=SYSDATE ");
			sql.append(" WHERE APPLICATION_RECORD_ID = ? AND SEQ =? AND CMPCDE=? AND IDNO=? ");
			String dSql = String.valueOf(sql);
			log.debug("Sql="+dSql+" : seq = "+prmBankDataM.getSeq()+" : personalID = "+prmBankDataM.getPersonalID());
			ps = conn.prepareStatement(dSql);
			ps.setString(1, prmBankDataM.getOrigOwner());		
			ps.setString(2, prmBankDataM.getUpdateBy());
			ps.setString(3, prmBankDataM.getAppRecodeID());
			ps.setInt(4, prmBankDataM.getSeq());
			ps.setString(5, prmBankDataM.getCmpCode());
			ps.setString(6, prmBankDataM.getPersonalID());	*/
			sql.append(" SET FINANCIAL_TYPE = ?, FINANCIAL_NO = ?,BANK = ?, BRANCH = ?,OPEN_DATE = ?");
			sql.append(" ,EXP_DATE = ?,FINANCIAL_AMOUNT = ?,UPDATE_BY =?,UPDATE_DATE=SYSDATE");
			sql.append(" WHERE PERSONAL_ID = ? AND SEQ =?");
			String dSql = String.valueOf(sql);
			log.debug(">>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>Sql="+dSql);
			log.debug(">>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>Sql="+personalID);
			log.debug(">>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>by="+prmBankDataM.getUpdateBy());
			log.debug(">>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>seq="+prmBankDataM.getSeq());
			ps = conn.prepareStatement(dSql);
			ps.setString(1, prmBankDataM.getFinancialType());
			ps.setString(2, prmBankDataM.getAccountNo());
			ps.setString(3, prmBankDataM.getBankCode());
			ps.setString(4, prmBankDataM.getBranchCode());
			ps.setDate(5, this.parseDate(prmBankDataM.getOpenDate()));
			
			ps.setDate(6, this.parseDate(prmBankDataM.getExpiryDate()));
			ps.setBigDecimal(7, prmBankDataM.getAmount());
			ps.setString(8, prmBankDataM.getUpdateBy());
			ps.setString(9, personalID);
			ps.setInt(10, prmBankDataM.getSeq());
			returnRows = ps.executeUpdate();
		} catch (Exception e) {
			log.fatal(e.getLocalizedMessage());
			throw new OrigCustomerFinnanceMException(e.getMessage());
		} finally {
			try {
				closeConnection(conn, ps);
			} catch (Exception e) {
				log.fatal(e.getLocalizedMessage());
			}
		}
		return returnRows;

	}
	
	/** delete not in key**/
	/*public void deleteNotInKeyTableORIG_CUSTOMER_FINANCE(Vector financeVect, String appRecordID, String cmpCode, String idNo) throws OrigCustomerFinnanceMException{
		PreparedStatement ps = null;
        try {
            // conn = Get Connection
            conn = getConnection();

            StringBuffer sql = new StringBuffer("");
            sql.append("DELETE FROM ORIG_CUSTOMER_FINANCE");
            sql.append(" WHERE APPLICATION_RECORD_ID = ? AND CMPCDE = ? AND IDNO = ? ");
            
            if ((financeVect != null) && (financeVect.size() != 0)) {
                sql.append(" AND SEQ NOT IN ( ");
                BankDataM bankDataM;
                for (int i = 0; i < financeVect.size(); i++) {
                    bankDataM = (BankDataM) financeVect.get(i);
                    sql.append(" '" + bankDataM.getSeq() + "' , ");
                }

                if (sql.toString().trim().endsWith(",")) {
                    sql.delete(sql.toString().lastIndexOf(","), sql.toString().length());
                }

                sql.append(" ) ");
            }

            String dSql = String.valueOf(sql);

            ps = conn.prepareStatement(dSql);

            ps.setString(1, appRecordID);
            ps.setString(2, cmpCode);
            ps.setString(3, idNo);
            
            ps.executeUpdate();
            ps.close();

        } catch (Exception e) {
        	logger.error("ERROR >> ",e);
			throw new OrigCustomerFinnanceMException(e.getMessage());
		} finally {
			try {
				closeConnection(conn, ps);
			} catch (Exception e) {
				logger.error("ERROR >> ",e);
				throw new OrigCustomerFinnanceMException(e.getMessage());
			}
		}
	}*/
	public void deleteNotInKeyTableORIG_CUSTOMER_FINANCE(Vector financeVect, String personalID) throws OrigCustomerFinnanceMException{
		PreparedStatement ps = null;
		Connection conn = null;
        try {
            // conn = Get Connection
            conn = getConnection();

            StringBuffer sql = new StringBuffer("");
            sql.append("DELETE FROM ORIG_FINANCIAL_INFO");
            sql.append(" WHERE PERSONAL_ID = ? ");
            
            if ((financeVect != null) && (financeVect.size() != 0)) {
                sql.append(" AND SEQ NOT IN ( ");
                BankDataM bankDataM;
                for (int i = 0; i < financeVect.size(); i++) {
                    bankDataM = (BankDataM) financeVect.get(i);
                    sql.append(" '" + bankDataM.getSeq() + "' , ");
                }

                if (sql.toString().trim().endsWith(",")) {
                    sql.delete(sql.toString().lastIndexOf(","), sql.toString().length());
                }

                sql.append(" ) ");
            }

            String dSql = String.valueOf(sql);

            ps = conn.prepareStatement(dSql);

            ps.setString(1, personalID);
            
            ps.executeUpdate();
            ps.close();

        } catch (Exception e) {
        	logger.error("ERROR >> ",e);
			throw new OrigCustomerFinnanceMException(e.getMessage());
		} finally {
			try {
				closeConnection(conn, ps);
			} catch (Exception e) {
				logger.error("ERROR >> ",e);
				throw new OrigCustomerFinnanceMException(e.getMessage());
			}
		}
	}
	
	/**
	 * @param appRecordID
	 */
	public void deleteTableORIG_CUSTOMER_FINANCE(String appRecordID, String cmpCode, String idNo)throws OrigCustomerFinnanceMException {
		PreparedStatement ps = null;
		Connection conn = null;
		try {
			//conn = Get Connection
			conn = getConnection();
			StringBuffer sql = new StringBuffer("");
			sql.append("DELETE ORIG_CUSTOMER_FINANCE ");
			sql.append(" WHERE CMPCDE = ? AND APPLICATION_RECORD_ID = ? AND IDNO = ?");
			String dSql = String.valueOf(sql);
			log.debug("Sql="+dSql);
			ps = conn.prepareStatement(dSql);
			
			ps.setString(1, cmpCode);
			ps.setString(2, appRecordID);
			ps.setString(3, idNo);
			
			ps.executeUpdate();

		} catch (Exception e) {
			log.fatal(e.getLocalizedMessage());
			throw new OrigCustomerFinnanceMException(e.getMessage());
		} finally {
			try {
				closeConnection(conn, ps);
			} catch (Exception e) {
				log.fatal(e.getLocalizedMessage());
				throw new OrigCustomerFinnanceMException(e.getMessage());
			}
		}

	}
}
