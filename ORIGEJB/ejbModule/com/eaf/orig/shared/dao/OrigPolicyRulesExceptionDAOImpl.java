/*
 * Created on Nov 10, 2008
 * Created by Avalant
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

import com.eaf.orig.master.shared.model.OrigPolicyRulesExceptionDataM;
import com.eaf.orig.shared.dao.exceptions.OrigMasterMException;

/**
 * @author Avalant
 *
 * Type: OrigPolicyRulesExceptionDAOImpl
 */
public class OrigPolicyRulesExceptionDAOImpl extends OrigObjectDAO implements OrigPolicyRulesExceptionDAO {
    private static Logger log = Logger.getLogger(OrigPolicyRulesExceptionDAOImpl.class);
    /* (non-Javadoc)
     * @see com.eaf.orig.shared.dao.OrigPolicyRulesExceptionDAO#createModelOrigPolicyRulesExceptionDataM(com.eaf.orig.master.shared.model.OrigPolicyRulesExceptionDataM)
     */
    public void createModelOrigPolicyRulesExceptionDataM(OrigPolicyRulesExceptionDataM prmOrigPolicyRulesExceptionDataM) throws OrigMasterMException {
        try {			
			createTableORIG_POLICY_RULES_EX(prmOrigPolicyRulesExceptionDataM);			
		} catch (Exception e) {
			log.error("Error >> ", e);
			throw new OrigMasterMException(e.getMessage());
		}

    }

    /**
     * @param prmOrigPolicyRulesExceptionDataM
     */
    private void createTableORIG_POLICY_RULES_EX(OrigPolicyRulesExceptionDataM prmOrigPolicyRulesExceptionDataM)throws OrigMasterMException {
        PreparedStatement ps = null;
        Connection conn = null;
		try {
			//conn = Get Connection
			conn = getConnection();
			StringBuffer sql = new StringBuffer("");
			sql.append("INSERT INTO ORIG_POLICY_RULES_EX ");
			sql.append("(POLICY_VERSION,POLICY_CODE,POLICY_FLAG,CREATE_BY,CREATE_DATE ");
			sql.append(",UPDATE_BY,UPDATE_DATE  ) ");
			sql.append(" VALUES(?,?,?,?,? ,?,SYSDATE) ");
			String dSql = String.valueOf(sql);
			log.debug("Sql=" + dSql  ); 
			ps = conn.prepareStatement(dSql);
			ps.setString(1, prmOrigPolicyRulesExceptionDataM.getPolicyVersion());
			ps.setString(2,  prmOrigPolicyRulesExceptionDataM.getPolicyCode());
			ps.setString(3, prmOrigPolicyRulesExceptionDataM.getPolicyFlag());			
			ps.setString(4, prmOrigPolicyRulesExceptionDataM.getCreateBy());
			ps.setTimestamp(5, prmOrigPolicyRulesExceptionDataM.getCreateDate());
			ps.setString(6, prmOrigPolicyRulesExceptionDataM.getUpdateBy());			
			ps.executeUpdate();
		} catch (Exception e) {
			log.error("Error >> ", e);
			throw new OrigMasterMException(e.getMessage());
		} finally {
			try {
				closeConnection(conn, ps);
			} catch (Exception e) {
				log.error("Close Connection Error >> ", e);
			}
		}
        
    }

    /* (non-Javadoc)
     * @see com.eaf.orig.shared.dao.OrigPolicyRulesExceptionDAO#deleteModelOrigPolicyRulesExceptionDataM(com.eaf.orig.master.shared.model.OrigPolicyRulesExceptionDataM)
     */
    public void deleteModelOrigPolicyRulesExceptionDataM(OrigPolicyRulesExceptionDataM prmOrigPolicyRulesExceptionDataM) throws OrigMasterMException {
        try {			
			deleteTableORIG_POLICY_RULES_EX(prmOrigPolicyRulesExceptionDataM);			
		} catch (Exception e) {
			log.error("Error >> ", e);
			throw new OrigMasterMException(e.getMessage());
		}

    }

    /**
     * @param prmOrigPolicyRulesExceptionDataM
     */
    private void deleteTableORIG_POLICY_RULES_EX(OrigPolicyRulesExceptionDataM prmOrigPolicyRulesExceptionDataM) throws OrigMasterMException {
        PreparedStatement ps = null;
        Connection conn = null;
		try {
			//conn = Get Connection
			conn = getConnection();
			StringBuffer sql = new StringBuffer("");
			sql.append("DELETE ORIG_POLICY_RULES_EX ");
			sql.append(" WHERE POLICY_VERSION = ? ");
			sql.append(" AND POOLICY_CODE=? ");
			String dSql = String.valueOf(sql);
			log.debug("Sql="+dSql);
			ps = conn.prepareStatement(dSql);
			ps.setString(1, prmOrigPolicyRulesExceptionDataM.getPolicyVersion());
			ps.setString(2, prmOrigPolicyRulesExceptionDataM.getPolicyCode());		
			ps.executeUpdate(); 
		} catch (Exception e) {
			logger.error("ERROR >> ",e);
			throw new OrigMasterMException(e.getMessage());
		} finally {
			try {
				closeConnection(conn, ps);
			} catch (Exception e) {
				log.error("Close Connection Error >> ", e);
				throw new OrigMasterMException(e.getMessage());
			}
		}
        
    }

    /* (non-Javadoc)
     * @see com.eaf.orig.shared.dao.OrigPolicyRulesExceptionDAO#loadModelOrigPolicyRulesExceptionDataM(java.lang.String)
     */
    public Vector loadModelOrigPolicyRulesExceptionDataM(String policyVersion) throws OrigMasterMException {
        try {
			Vector result = selectTablePOLICY_RULES_EX(policyVersion);
			return result;
		} catch (Exception e) {
			log.error("Error >> ", e);
			throw new OrigMasterMException(e.getMessage());
		}
       // return null;
    }

    /**
     * @param policyVersion
     * @return
     */
    private Vector selectTablePOLICY_RULES_EX(String policyVersion) throws OrigMasterMException {
        PreparedStatement ps = null;
		ResultSet rs = null;
		Connection conn = null;
		try {
			//conn = Get Connection
			conn = getConnection();
			StringBuffer sql = new StringBuffer("");			
			sql.append("SELECT POLICY_VERSION,POLICY_CODE,POLICY_FLAG,CREATE_BY,CREATE_DATE,UPDATE_BY,UPDATE_DATE  ");		
			sql.append(" FROM ORIG_POLICY_RULES_EX  WHERE POLICY_VERSION = ? "); 		
			String dSql = String.valueOf(sql);
			log.debug("Sql=" + dSql);
			ps = conn.prepareStatement(dSql);
			rs = null;
			ps.setString(1, policyVersion);

			rs = ps.executeQuery();
			OrigPolicyRulesExceptionDataM prmOrigPolicyRulesExceptionDataM = null;
			Vector resultVect = new Vector();
			while (rs.next()) {
			    prmOrigPolicyRulesExceptionDataM = new  OrigPolicyRulesExceptionDataM();
			    prmOrigPolicyRulesExceptionDataM.setPolicyVersion(rs.getString(1));
			    prmOrigPolicyRulesExceptionDataM.setPolicyCode(rs.getString(2));
			    prmOrigPolicyRulesExceptionDataM.setPolicyFlag(rs.getString(3));
			    prmOrigPolicyRulesExceptionDataM.setCreateBy(rs.getString(4));
			    prmOrigPolicyRulesExceptionDataM.setCreateDate(rs.getTimestamp(5));
			    prmOrigPolicyRulesExceptionDataM.setUpdateBy(rs.getString(6));
			    prmOrigPolicyRulesExceptionDataM.setUpdateDate(rs.getTimestamp(7));			    			 				
				resultVect.add(prmOrigPolicyRulesExceptionDataM);
			}
			return resultVect;
		} catch (Exception e) {
			log.error("Error >> ", e);
			throw new OrigMasterMException(e.getMessage());
		} finally {
			try {
				closeConnection(conn, rs, ps);
			} catch (Exception e) {
				log.error("Close Connection Error >> ", e);
			}
		}
    }

    /* (non-Javadoc)
     * @see com.eaf.orig.shared.dao.OrigPolicyRulesExceptionDAO#saveUpdateModelOrigPolicyRulesExceptionDataM(com.eaf.orig.master.shared.model.OrigPolicyLevelMapDataM)
     */
    public void saveUpdateModelOrigPolicyRulesExceptionDataM(OrigPolicyRulesExceptionDataM prmOrigPolicyRulesExceptionDataM) throws OrigMasterMException {
        int returnRows = 0;
		try {
			returnRows = updateTableORIG_POLICY_RULES_EX(prmOrigPolicyRulesExceptionDataM);
			
			if (returnRows == 0) {
				log.debug("New record then can't update record in table POLICY_RULES_EX then call Insert method");	
				createTableORIG_POLICY_RULES_EX(prmOrigPolicyRulesExceptionDataM);
			}
		} catch (Exception e) {
			log.error("Error >> ", e);
			throw new OrigMasterMException(e.getMessage());
		} 

    }

    /**
     * @param prmOrigPolicyRulesExceptionDataM
     * @return
     */
    private int updateTableORIG_POLICY_RULES_EX(OrigPolicyRulesExceptionDataM prmOrigPolicyRulesExceptionDataM) throws OrigMasterMException{
        int returnRows = 0;
		PreparedStatement ps = null;
		Connection conn = null;
		try {
			//conn = Get Connection
			conn = getConnection();
			StringBuffer sql = new StringBuffer("");
			sql.append("UPDATE ORIG_POLICY_RULES_EX ");
			
			sql.append(" SET   POLICY_FLAG=?,UPDATE_BY=?,UPDATE_DATE=SYSDATE ");			 			 			
			sql.append(" WHERE POLICY_VERSION =? AND POLICY_CODE=?   ");
			
			String dSql = String.valueOf(sql);
			log.debug("Sql="+dSql);
			ps = conn.prepareStatement(dSql);
			ps.setString(1, prmOrigPolicyRulesExceptionDataM.getPolicyFlag());
			ps.setString(2, prmOrigPolicyRulesExceptionDataM.getUpdateBy());	
			ps.setString(3, prmOrigPolicyRulesExceptionDataM.getPolicyVersion());
			ps.setString(4, prmOrigPolicyRulesExceptionDataM.getPolicyCode());			
			returnRows = ps.executeUpdate();
		} catch (Exception e) {
			log.fatal("",e);
			throw new OrigMasterMException(e.getMessage());
		} finally {
			try {
				closeConnection(conn, ps);
			} catch (Exception e) {
				log.fatal("",e);
			}
		}
		return returnRows;
       
    }

    /**
     * @param prmOrigPolicyRulesExceptionDataM
     * @return
     */
    

    /* (non-Javadoc)
     * @see com.eaf.orig.shared.dao.OrigPolicyRulesExceptionDAO#deleteTableORIG_POLICY_RULES_EX(java.lang.String)
     */
    public void deleteTableORIG_POLICY_RULES_EX(String policyVersion) throws OrigMasterMException {
        PreparedStatement ps = null;
        Connection conn = null;
		try {
			//conn = Get Connection
			conn = getConnection();
			StringBuffer sql = new StringBuffer("");
			sql.append("DELETE ORIG_POLICY_RULES_EX ");
			sql.append(" WHERE POLICY_VERSION = ? ");		 
			String dSql = String.valueOf(sql);
			log.debug("Sql="+dSql);
			ps = conn.prepareStatement(dSql);
			ps.setString(1, policyVersion);
			ps.executeUpdate(); 
		} catch (Exception e) {
			logger.error("ERROR >> ",e);
			throw new OrigMasterMException(e.getMessage());
		} finally {
			try {
				closeConnection(conn, ps);
			} catch (Exception e) {
				log.error("Close Connection Error >> ", e);
				throw new OrigMasterMException(e.getMessage());
			}
		}

    }

}
