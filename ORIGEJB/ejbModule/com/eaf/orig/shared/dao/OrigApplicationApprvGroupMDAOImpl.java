/*
 * Created on Sep 21, 2007
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

import org.apache.log4j.Logger;

import com.eaf.orig.shared.dao.exceptions.OrigApplicationApprvGroupMException;
import com.eaf.orig.shared.model.ApplicationApprvGroupM;
import com.eaf.orig.shared.service.OrigServiceLocator;

/**
 * @author Sankom Sanpunya
 * 
 * Type: OrigApplicationApprvGroupMDAOImpl
 */
public class OrigApplicationApprvGroupMDAOImpl extends OrigObjectDAO implements
		OrigApplicationApprvGroupMDAO {
	private static Logger log = Logger
			.getLogger(OrigApplicationApprvGroupMDAOImpl.class);

	/**
	 *  
	 */
	public OrigApplicationApprvGroupMDAOImpl() {
		super();
		
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.eaf.orig.share.dao.OrigApplicationApprvGroupMDAO#createModelOrigApplicationApprvGroupM(com.eaf.orig.shared.model.ApplicationApprvGroupM)
	 */
	public void createModelOrigApplicationApprvGroupM(
			ApplicationApprvGroupM prmApplicationApprvGroupM)
			throws OrigApplicationApprvGroupMException {
		try {
			createTableORIG_APPLICATION_APPV_GRP(prmApplicationApprvGroupM);
		} catch (Exception e) {
			log.fatal(e.getLocalizedMessage());
			throw new OrigApplicationApprvGroupMException(e.getMessage());
		}

	}

	/**
	 * @param prmApplicationApprvGroupM
	 */
	private void createTableORIG_APPLICATION_APPV_GRP(
			ApplicationApprvGroupM prmApplicationApprvGroupM)
			throws OrigApplicationApprvGroupMException {
		PreparedStatement ps = null;Connection conn = null;
		try {
			//conn = Get Connection
			conn = getConnection();
			StringBuffer sql = new StringBuffer("");
			sql.append("INSERT INTO ORIG_APPLICATION_APPV_GRP ");
			sql.append("( APPLICATION_RECORD_ID,GROUP_NAME,LOAN_TYPE");
			sql.append(",CREATE_DATE,CREATE_BY,UPDATE_DATE ,UPDATE_BY, CUSTOMER_TYPE  )");
			sql.append(" VALUES(?,?,?  ,SYSDATE,?,SYSDATE,?,?) ");
			String dSql = String.valueOf(sql);
			log.debug("Sql="+dSql);
			ps = conn.prepareStatement(dSql);
			ps.setString(1, prmApplicationApprvGroupM.getApplcationRecordId());
			ps.setString(2, prmApplicationApprvGroupM.getGroupName());
			ps.setString(3, "-");
			ps.setString(4, prmApplicationApprvGroupM.getCreateBy());
			ps.setString(5, prmApplicationApprvGroupM.getUpdateBy());
			ps.setString(6, "-");
			ps.executeUpdate();
		} catch (Exception e) {
			e.printStackTrace();
			throw new OrigApplicationApprvGroupMException(e.getMessage());
		} finally {
			try {
				closeConnection(conn, ps);
			} catch (Exception e) {
				e.printStackTrace();
			}
		}

	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.eaf.orig.share.dao.OrigApplicationApprvGroupMDAO#deleteModelOrigApplicationApprvGroupM(com.eaf.orig.shared.model.ApplicationApprvGroupM)
	 */
	public void deleteModelOrigApplicationApprvGroupM(
			ApplicationApprvGroupM prmApplicationApprvGroupM)
			throws OrigApplicationApprvGroupMException {
		try {
			deleteTableORIG_APPLICATION_APPV_GRP(prmApplicationApprvGroupM);
		} catch (Exception e) {
			log.fatal(e.getLocalizedMessage());
			throw new OrigApplicationApprvGroupMException(e.getMessage());
		}

	}

	/**
	 * @param prmApplicationApprvGroupM
	 */
	private void deleteTableORIG_APPLICATION_APPV_GRP(
			ApplicationApprvGroupM prmApplicationApprvGroupM)
			throws OrigApplicationApprvGroupMException {
		PreparedStatement ps = null;Connection conn = null;
		try {
			//conn = Get Connection
			conn = getConnection();
			StringBuffer sql = new StringBuffer("");
			sql.append("DELETE ORIG_APPLICATION_APPV_GRP ");
			sql.append(" WHERE APPLICATION_RECORD_ID = ?");
			String dSql = String.valueOf(sql);
			log.debug("Sql="+dSql);
			ps = conn.prepareStatement(dSql);
			ps.setString(1, prmApplicationApprvGroupM.getApplcationRecordId());
			ps.executeUpdate();

		} catch (Exception e) {
			e.printStackTrace();
			throw new OrigApplicationApprvGroupMException(e.getMessage());
		} finally {
			try {
				closeConnection(conn, ps);
			} catch (Exception e) {
				log.fatal(e.getLocalizedMessage());
				throw new OrigApplicationApprvGroupMException(e.getMessage());
			}
		}

	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.eaf.orig.share.dao.OrigApplicationApprvGroupMDAO#loadModelOrigApplicationApprvGroupM(java.lang.String)
	 */
	public ApplicationApprvGroupM loadModelOrigApplicationApprvGroupM(
			String applicationRecordId)
			throws OrigApplicationApprvGroupMException {
		try {
			ApplicationApprvGroupM result = selectTableORIG_APPLICATION_APPV_GRP(applicationRecordId);
			return result;
		} catch (Exception e) {
			log.fatal(e.getLocalizedMessage());
			throw new OrigApplicationApprvGroupMException(e.getMessage());
		}
	}

	/**
	 * @param applicationRecordId
	 * @return
	 */
	private ApplicationApprvGroupM selectTableORIG_APPLICATION_APPV_GRP(
			String applicationRecordId)
			throws OrigApplicationApprvGroupMException {

		ApplicationApprvGroupM resApplicationApprvGroupM = null;
		PreparedStatement ps = null;
		ResultSet rs = null;Connection conn = null;
		try {
			//conn = Get Connection
			conn = getConnection(OrigServiceLocator.ORIG_DB);
			StringBuffer sql = new StringBuffer("");
			sql
					.append("SELECT  GROUP_NAME, LOAN_TYPE  FROM ORIG_APPLICATION_APPV_GRP ");
			sql.append(" WHERE APPLICATION_RECORD_ID = ?");
			String dSql = String.valueOf(sql);
			log.debug("Sql="+dSql);
			ps = conn.prepareStatement(dSql);
			ps.setString(1, applicationRecordId);
			rs = ps.executeQuery();
			if (rs.next()) {
				resApplicationApprvGroupM = new ApplicationApprvGroupM();
				resApplicationApprvGroupM.setGroupName(rs
						.getString("GROUP_NAME"));
				resApplicationApprvGroupM.setLoanType(rs
						.getString("LOAN_TYPE"));
				resApplicationApprvGroupM
						.setApplcationRecordId(applicationRecordId);
			}
		} catch (Exception e) {
			e.printStackTrace();
			throw new OrigApplicationApprvGroupMException(e.getMessage());
		} finally {
			try {
				closeConnection(conn, rs, ps);
			} catch (Exception e) {
				log.fatal(e.getLocalizedMessage());
				throw new OrigApplicationApprvGroupMException(e.getMessage());
			}
		}
		return resApplicationApprvGroupM;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.eaf.orig.share.dao.OrigApplicationApprvGroupMDAO#saveUpdateModelOrigApplicationApprvGroupM(com.eaf.orig.shared.model.ApplicationApprvGroupM)
	 */
	public void saveUpdateModelOrigApplicationApprvGroupM(
			ApplicationApprvGroupM prmApplicationApprvGroupM)
			throws OrigApplicationApprvGroupMException {
		double returnRows = 0;
		try {
			returnRows = updateTableORIG_APPLICATION_APPV_GRP(prmApplicationApprvGroupM);
			if (returnRows == 0) {
				log
						.warn("New record then can't update record in table ORIG_APPLICATION_APPV_GRP then call Insert method");
				createTableORIG_APPLICATION_APPV_GRP(prmApplicationApprvGroupM);
			}
		} catch (Exception e) {
			e.printStackTrace();
			throw new OrigApplicationApprvGroupMException(e.getMessage());
		}

	}

	/**
	 * @param prmApplicationApprvGroupM
	 * @return
	 */
	private double updateTableORIG_APPLICATION_APPV_GRP(
			ApplicationApprvGroupM prmApplicationApprvGroupM)
			throws OrigApplicationApprvGroupMException {
		double returnRows = 0;
		PreparedStatement ps = null;Connection conn = null;
		try {
			//conn = Get Connection
			conn = getConnection();
			StringBuffer sql = new StringBuffer("");
			sql.append("UPDATE ORIG_APPLICATION_APPV_GRP ");
			sql.append(" SET UPDATE_BY = ?, UPDATE_DATE = SYSDATE");
			//sql.append(" WHERE APPLICATION_RECORD_ID = ? AND GROUP_NAME = ? AND LOAN_TYPE = ? AND CUSTOMER_TYPE = ? ");
			sql.append(" WHERE APPLICATION_RECORD_ID = ? AND GROUP_NAME = ? ");
			String dSql = String.valueOf(sql);
            log.debug("Sql="+dSql);   
			ps = conn.prepareStatement(dSql);

			ps.setString(1, prmApplicationApprvGroupM.getUpdateBy());
			ps.setString(2, prmApplicationApprvGroupM.getApplcationRecordId());
			ps.setString(3, prmApplicationApprvGroupM.getGroupName());
			//ps.setString(4, prmApplicationApprvGroupM.getLoanType());
			//ps.setString(5, prmApplicationApprvGroupM.getCustomerType());
			returnRows = ps.executeUpdate();
		} catch (Exception e) {
			log.fatal(e.getLocalizedMessage());
			throw new OrigApplicationApprvGroupMException(e.getMessage());
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
	public void deleteNotInKeyTableORIG_APPLICATION_APPV_GRP(String escalateGroup, String appRecordID) throws OrigApplicationApprvGroupMException{
		PreparedStatement ps = null;
//		String cmpCode = null;
		Connection conn = null;
        try {
            // conn = Get Connection
            conn = getConnection();

            StringBuffer sql = new StringBuffer("");
            sql.append("DELETE FROM ORIG_APPLICATION_APPV_GRP");
            sql.append(" WHERE APPLICATION_RECORD_ID = ? ");
            sql.append(" AND GROUP_NAME NOT IN ( ? ) ");

            String dSql = String.valueOf(sql);

            ps = conn.prepareStatement(dSql);

            ps.setString(1, appRecordID);
            ps.setString(2, escalateGroup);
            
            ps.executeUpdate();
            ps.close();

        } catch (Exception e) {
        	logger.error("ERROR >> ",e);
			throw new OrigApplicationApprvGroupMException(e.getMessage());
		} finally {
			try {
				closeConnection(conn, ps);
			} catch (Exception e) {
				log.error("Close Connection Error >> ", e);
				throw new OrigApplicationApprvGroupMException(e.getMessage());
			}
		}
	}
	
	public void saveUpdateModelOrigApplicationApprvGroupM(String escalateGroup, String appRecordID, String loanType, String userName, String customerType)throws OrigApplicationApprvGroupMException {
		double returnRows = 0;
		try {
			ApplicationApprvGroupM applicationApprvGroupM = new ApplicationApprvGroupM();
			applicationApprvGroupM.setApplcationRecordId(appRecordID);
			//applicationApprvGroupM.setLoanType(loanType);
			applicationApprvGroupM.setUpdateBy(userName);
			applicationApprvGroupM.setGroupName(escalateGroup);
			applicationApprvGroupM.setCustomerType(customerType);
	
			returnRows = updateTableORIG_APPLICATION_APPV_GRP(applicationApprvGroupM);
			if (returnRows == 0) {
				log
						.warn("New record then can't update record in table ORIG_APPLICATION_APPV_GRP then call Insert method");
				applicationApprvGroupM.setCreateBy(userName);
				createTableORIG_APPLICATION_APPV_GRP(applicationApprvGroupM);
			}
			//delete
			deleteNotInKeyTableORIG_APPLICATION_APPV_GRP(escalateGroup, appRecordID);
			
		} catch (Exception e) {
			e.printStackTrace();
			throw new OrigApplicationApprvGroupMException(e.getMessage());
		}

	}
}
