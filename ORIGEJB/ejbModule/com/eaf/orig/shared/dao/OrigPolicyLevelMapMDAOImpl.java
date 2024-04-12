/*
 * Created on Sep 11, 2008
 * Created by Sankom
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

import com.eaf.orig.master.shared.model.OrigPolicyLevelMapDataM;
import com.eaf.orig.shared.dao.exceptions.OrigMasterMException;

/**
 * @author Sankom
 *
 * Type: OrigPolicyLevelMapMDAOImpl
 */
public class OrigPolicyLevelMapMDAOImpl extends OrigObjectDAO implements OrigPolicyLevelMapMDAO {
    private static Logger log = Logger.getLogger(OrigPolicyLevelMapMDAOImpl.class);
    /**
     * 
     */
    public OrigPolicyLevelMapMDAOImpl() {
        super();
        // TODO Auto-generated constructor stub
    }

    /* (non-Javadoc)
     * @see com.eaf.orig.shared.dao.OrigPolicyLevelMapMDAO#createModelOrigPolicyLevelMapDataM(com.eaf.orig.master.shared.model.OrigPolicyLevelMapDataM)
     */
    public void createModelOrigPolicyLevelMapDataM(OrigPolicyLevelMapDataM prmOrigPolicyLevelMapDataM) throws OrigMasterMException {
        try {			
			createTableORIG_POLICY_LEVEL_MAP(prmOrigPolicyLevelMapDataM);			
		} catch (Exception e) {
			log.error("Error >> ", e);
			throw new OrigMasterMException(e.getMessage());
		}

    }

    /**
     * @param prmOrigPolicyLevelMapDataM
     */
    private void createTableORIG_POLICY_LEVEL_MAP(OrigPolicyLevelMapDataM prmOrigPolicyLevelMapDataM) throws OrigMasterMException {
        PreparedStatement ps = null;
        Connection conn = null;
		try {
			//conn = Get Connection
			conn = getConnection();
			StringBuffer sql = new StringBuffer("");
			sql.append("INSERT INTO ORIG_POLICY_LEVEL_MAP ");
			sql.append("(POLICY_VERSION,POLICY_CODE,POLICY_LEVEL,CREATE_BY,CREATE_DATE ");
			sql.append(",UPDATE_BY,UPDATE_DATE  ) ");
			sql.append(" VALUES(?,?,?,?,? ,?,SYSDATE) ");
			String dSql = String.valueOf(sql);
			log.debug("Sql=" + dSql  ); 
			ps = conn.prepareStatement(dSql);
			ps.setString(1, prmOrigPolicyLevelMapDataM.getPolicyVersion());
			ps.setString(2,  prmOrigPolicyLevelMapDataM.getPolicyCode());
			ps.setString(3, prmOrigPolicyLevelMapDataM.getPolicyLevel());			
			ps.setString(4, prmOrigPolicyLevelMapDataM.getCreateBy());
			ps.setTimestamp(5, prmOrigPolicyLevelMapDataM.getCreateDate());
			ps.setString(6, prmOrigPolicyLevelMapDataM.getUpdateBy());			
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
     * @see com.eaf.orig.shared.dao.OrigPolicyLevelMapMDAO#deleteModelOrigPolicyLevelMapDataM(com.eaf.orig.master.shared.model.OrigPolicyLevelMapDataM)
     */
    public void deleteModelOrigPolicyLevelMapDataM(OrigPolicyLevelMapDataM prmOrigPolicyLevelMapDataM) throws OrigMasterMException {
        try {			
			deleteTableORIG_POLICY_LEVEL_MAP(prmOrigPolicyLevelMapDataM);			
		} catch (Exception e) {
			log.error("Error >> ", e);
			throw new OrigMasterMException(e.getMessage());
		}

    }

    /**
     * @param prmOrigPolicyLevelMapDataM
     */
    private void deleteTableORIG_POLICY_LEVEL_MAP(OrigPolicyLevelMapDataM prmOrigPolicyLevelMapDataM) throws OrigMasterMException{
        PreparedStatement ps = null;
        Connection conn = null;
		try {
			//conn = Get Connection
			conn = getConnection();
			StringBuffer sql = new StringBuffer("");
			sql.append("DELETE ORIG_POLICY_LEVEL_MAP ");
			sql.append(" WHERE POLICY_VERSION = ? ");
			sql.append(" AND POOLICY_CODE=? ");
			sql.append(" AND POLICY_LEVEL=? ");
			String dSql = String.valueOf(sql);
			log.debug("Sql="+dSql);
			ps = conn.prepareStatement(dSql);
			ps.setString(1, prmOrigPolicyLevelMapDataM.getPolicyVersion());
			ps.setString(2, prmOrigPolicyLevelMapDataM.getPolicyCode());
			ps.setString(3, prmOrigPolicyLevelMapDataM.getPolicyLevel());
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
     * @see com.eaf.orig.shared.dao.OrigPolicyLevelMapMDAO#loadModelOrigPolicyLevelMapDataM(java.lang.String)
     */
    public Vector loadModelOrigPolicyLevelMapDataM(String policyVersion) throws OrigMasterMException {
        try {
			Vector result = selectTableORIG_POLICY_LEVEL_MAP(policyVersion);
			return result;
		} catch (Exception e) {
			log.error("Error >> ", e);
			throw new OrigMasterMException(e.getMessage());
		}
    }

    /**
     * @param policyVersion
     * @return
     */
    private Vector selectTableORIG_POLICY_LEVEL_MAP(String policyVersion)throws OrigMasterMException {
        PreparedStatement ps = null;
		ResultSet rs = null;
		Connection conn = null;
		try {
			//conn = Get Connection
			conn = getConnection();
			StringBuffer sql = new StringBuffer("");			
			sql.append("SELECT POLICY_VERSION,POLICY_CODE,POLICY_LEVEL,CREATE_BY,CREATE_DATE,UPDATE_BY,UPDATE_DATE  ");		
			sql.append(" FROM ORIG_POLICY_LEVEL_MAP  WHERE POLICY_VERSION = ? "); 		
			String dSql = String.valueOf(sql);
			log.debug("Sql=" + dSql);
			ps = conn.prepareStatement(dSql);
			rs = null;
			ps.setString(1, policyVersion);

			rs = ps.executeQuery();
			OrigPolicyLevelMapDataM prmOrigPolicyLevelMapDataM = null;
			Vector resultVect = new Vector();
			while (rs.next()) {
			    prmOrigPolicyLevelMapDataM = new  OrigPolicyLevelMapDataM();
			    prmOrigPolicyLevelMapDataM.setPolicyVersion(rs.getString(1));
			    prmOrigPolicyLevelMapDataM.setPolicyCode(rs.getString(2));
			    prmOrigPolicyLevelMapDataM.setPolicyLevel(rs.getString(3));
			    prmOrigPolicyLevelMapDataM.setCreateBy(rs.getString(4));
			    prmOrigPolicyLevelMapDataM.setCreateDate(rs.getTimestamp(5));
			    prmOrigPolicyLevelMapDataM.setUpdateBy(rs.getString(6));
			    prmOrigPolicyLevelMapDataM.setUpdateDate(rs.getTimestamp(7));			    			 				
				resultVect.add(prmOrigPolicyLevelMapDataM);
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
     * @see com.eaf.orig.shared.dao.OrigPolicyLevelMapMDAO#saveUpdateModelOrigPolicyLevelMapDataM(com.eaf.orig.master.shared.model.OrigPolicyLevelMapDataM)
     */
    public void saveUpdateModelOrigPolicyLevelMapDataM(OrigPolicyLevelMapDataM prmOrigPolicyLevelMapDataM) throws OrigMasterMException {
        int returnRows = 0;
		try {
			returnRows = updateTableORIG_POLICY_LEVEL_MAP(prmOrigPolicyLevelMapDataM);
			
			if (returnRows == 0) {
				log.debug("New record then can't update record in table ORIG_POLICY_VERSION_LEVEL then call Insert method");	
				createTableORIG_POLICY_LEVEL_MAP(prmOrigPolicyLevelMapDataM);
			}
		} catch (Exception e) {
			log.error("Error >> ", e);
			throw new OrigMasterMException(e.getMessage());
		} 

    }

    /**
     * @param prmOrigPolicyLevelMapDataM
     * @return
     */
    private int updateTableORIG_POLICY_LEVEL_MAP(OrigPolicyLevelMapDataM prmOrigPolicyLevelMapDataM)throws OrigMasterMException {
        int returnRows = 0;
		PreparedStatement ps = null;
		Connection conn = null;
		try {
			//conn = Get Connection
			conn = getConnection();
			StringBuffer sql = new StringBuffer("");
			sql.append("UPDATE ORIG_POLICY_LEVEL_MAP ");
			
			sql.append(" SET   POLICY_LEVEL=?,UPDATE_BY=?,UPDATE_DATE=SYSDATE ");			 			 			
			sql.append(" WHERE POLICY_VERSION =? AND POLICY_CODE=?  AND POLICY_LEVEL=?");
			
			String dSql = String.valueOf(sql);
			log.debug("Sql="+dSql);
			ps = conn.prepareStatement(dSql);
			ps.setString(1, prmOrigPolicyLevelMapDataM.getPolicyLevel());
			ps.setString(2, prmOrigPolicyLevelMapDataM.getUpdateBy());	
			ps.setString(3, prmOrigPolicyLevelMapDataM.getPolicyVersion());
			ps.setString(4, prmOrigPolicyLevelMapDataM.getPolicyCode());
			ps.setString(5, prmOrigPolicyLevelMapDataM.getPolicyLevel());
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

    /* (non-Javadoc)
     * @see com.eaf.orig.shared.dao.OrigPolicyLevelMapMDAO#deleteTableORIG_POLICY_LEVEL_MAP(java.lang.String)
     */
    public void deleteTableORIG_POLICY_LEVEL_MAP(String policyVersion) throws OrigMasterMException {
        PreparedStatement ps = null;
        Connection conn = null;
		try {
			//conn = Get Connection
			conn = getConnection();
			StringBuffer sql = new StringBuffer("");
			sql.append("DELETE ORIG_POLICY_LEVEL_MAP ");
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
