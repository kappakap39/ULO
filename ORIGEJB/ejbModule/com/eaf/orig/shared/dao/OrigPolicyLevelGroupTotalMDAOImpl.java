/*
 * Created on Sep 9, 2008
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

import com.eaf.orig.master.shared.model.OrigPolicyLevelGroupTotalDataM;
import com.eaf.orig.shared.dao.exceptions.OrigMasterMException;

/**
 * @author Sankom
 *
 * Type: OrigPolicyLevelGroupMDAOImpl
 */
public class OrigPolicyLevelGroupTotalMDAOImpl extends OrigObjectDAO implements OrigPolicyLevelGroupTotalMDAO {
    private static Logger log = Logger.getLogger(OrigPolicyLevelGroupTotalMDAOImpl.class);

    /* (non-Javadoc)
     * @see com.eaf.orig.shared.dao.OrigPolicyLevelGroupTotalMDAO#createModelOrigPolicyLevelGroupTotalM(com.eaf.orig.master.shared.model.OrigPolicyLevelGroupTotalDataM)
     */
    public void createModelOrigPolicyLevelGroupTotalM(OrigPolicyLevelGroupTotalDataM prmOrigPolicyLevelGroupTotalDataM) throws OrigMasterMException {
        try {
			
			createTableORIG_POLICY_LEVEL_GROUP_TOTAL(prmOrigPolicyLevelGroupTotalDataM);
			
		} catch (Exception e) {
			log.error("Error >> ", e);
			throw new OrigMasterMException(e.getMessage());
		}
        
        
    }

    /* (non-Javadoc)
     * @see com.eaf.orig.shared.dao.OrigPolicyLevelGroupTotalMDAO#deleteModelOrigPolicyLevelGroupTotalM(com.eaf.orig.master.shared.model.OrigPolicyLevelGroupTotalDataM)
     */
    public void deleteModelOrigPolicyLevelGroupTotalM(OrigPolicyLevelGroupTotalDataM prmOrigPolicyLevelGroupTotalDataM) throws OrigMasterMException {
        try {
			deleteTableORIG_POLICY_LEVEL_GROUP_TOTAL(prmOrigPolicyLevelGroupTotalDataM);
		} catch (Exception e) {
			log.fatal(e.getLocalizedMessage());
			throw new OrigMasterMException(e.getMessage());
		}
        
    }

    /**
     * @param prmOrigPolicyLevelGroupTotalDataM
     */
    private void deleteTableORIG_POLICY_LEVEL_GROUP_TOTAL(OrigPolicyLevelGroupTotalDataM prmOrigPolicyLevelGroupTotalDataM) throws OrigMasterMException {
        PreparedStatement ps = null;
        Connection conn = null;
		try {
			//conn = Get Connection
			conn = getConnection();
			StringBuffer sql = new StringBuffer("");
			sql.append("DELETE ORIG_POLICY_LEVEL_GROUP_TOTAL ");
			sql.append(" WHERE POLICY_VERSION = ? ");
			sql.append(" AND GROUP_NAME=? ");			 
			String dSql = String.valueOf(sql);
			log.debug("Sql="+dSql);
			ps = conn.prepareStatement(dSql);
			ps.setString(1, prmOrigPolicyLevelGroupTotalDataM.getPolicyVersion());
			ps.setString(2, prmOrigPolicyLevelGroupTotalDataM.getGroupName());			 
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
     * @see com.eaf.orig.shared.dao.OrigPolicyLevelGroupTotalMDAO#loadModelOrigOrigPolicyLevelGroupTotalM(java.lang.String)
     */
    public Vector loadModelOrigOrigPolicyLevelGroupTotalM(String policyVersion) throws OrigMasterMException {
        try {
			Vector result = selectTableORIG_POLICY_LEVEL_GROUP_TOTAL(policyVersion);
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
    private Vector selectTableORIG_POLICY_LEVEL_GROUP_TOTAL(String policyVersion) throws OrigMasterMException{
        PreparedStatement ps = null;
		ResultSet rs = null;
		Connection conn = null;
		try {
			//conn = Get Connection
			conn = getConnection();
			StringBuffer sql = new StringBuffer("");
			
			sql.append("SELECT POLICY_VERSION,GROUP_NAME,TOTAL_POLICY_AMT,CREATE_BY,CREATE_DATE,UPDATE_BY,UPDATE_DATE,DESCRIPTION   ");		
			sql.append(" FROM ORIG_POLICY_LEVEL_GROUP_TOTAL WHERE POLICY_VERSION = ? ");
 			
			String dSql = String.valueOf(sql);
			log.debug("Sql=" + dSql);
			ps = conn.prepareStatement(dSql);
			rs = null;
			ps.setString(1, policyVersion);

			rs = ps.executeQuery();
			OrigPolicyLevelGroupTotalDataM prmOrigPolicyLevelGroupTotalDataM = null;
			Vector resultVect = new Vector();
			while (rs.next()) {
			    prmOrigPolicyLevelGroupTotalDataM = new  OrigPolicyLevelGroupTotalDataM();
			    prmOrigPolicyLevelGroupTotalDataM.setPolicyVersion(policyVersion);
			    prmOrigPolicyLevelGroupTotalDataM.setGroupName(rs.getString(2));			    
			    prmOrigPolicyLevelGroupTotalDataM.setToalLevelAmount(rs.getInt(3));			    
			    prmOrigPolicyLevelGroupTotalDataM.setCreateBy(rs.getString(4));
			    prmOrigPolicyLevelGroupTotalDataM.setCreateDate(rs.getTimestamp(5));
			    prmOrigPolicyLevelGroupTotalDataM.setUpdateBy(rs.getString(6));
			    prmOrigPolicyLevelGroupTotalDataM.setUpdateDate(rs.getTimestamp(7));
			    prmOrigPolicyLevelGroupTotalDataM.setDescription(rs.getString(8));
				resultVect.add(prmOrigPolicyLevelGroupTotalDataM);
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
     * @see com.eaf.orig.shared.dao.OrigPolicyLevelGroupTotalMDAO#saveUpdateModelOrigPolicyLevelGroupTotalM(com.eaf.orig.master.shared.model.OrigPolicyLevelGroupTotalDataM)
     */
    public void saveUpdateModelOrigPolicyLevelGroupTotalM(OrigPolicyLevelGroupTotalDataM prmOrigPolicyLevelGroupTotalDataM) throws OrigMasterMException {
          
        int returnRows = 0;
		try {
			returnRows = updateTableORIG_POLICY_LEVEL_GROUP_TOTAL(prmOrigPolicyLevelGroupTotalDataM);
			
			if (returnRows == 0) {
				log.debug("New record then can't update record in table ORIG_POLICY_LEVEL_GROUP TOTAL then call Insert method");	
				createTableORIG_POLICY_LEVEL_GROUP_TOTAL(prmOrigPolicyLevelGroupTotalDataM);
			}
		} catch (Exception e) {
			log.error("Error >> ", e);
			throw new OrigMasterMException(e.getMessage());
		}
        
    }

    /**
     * @param prmOrigPolicyLevelGroupDataM
     */
    private void createTableORIG_POLICY_LEVEL_GROUP_TOTAL(OrigPolicyLevelGroupTotalDataM prmOrigPolicyLevelGroupDataM) throws OrigMasterMException {
        PreparedStatement ps = null;
        Connection conn = null;
		try {
			//conn = Get Connection
			conn = getConnection();
			StringBuffer sql = new StringBuffer("");
			sql.append("INSERT INTO ORIG_POLICY_LEVEL_GROUP_TOTAL ");
			sql.append("(POLICY_VERSION,GROUP_NAME,TOTAL_POLICY_AMT,CREATE_BY ");
			sql.append(" ,CREATE_DATE,UPDATE_BY,UPDATE_DATE,DESCRIPTION ) ");
			sql.append(" VALUES(?,?,?,?,?  ,?,SYSDATE,?) ");
			String dSql = String.valueOf(sql);
			log.debug("Sql=" + dSql  ); 
			ps = conn.prepareStatement(dSql);
			ps.setString(1, prmOrigPolicyLevelGroupDataM.getPolicyVersion());
			ps.setString(2,  prmOrigPolicyLevelGroupDataM.getGroupName());
			ps.setInt(3, prmOrigPolicyLevelGroupDataM.getToalLevelAmount()); 	
			ps.setString(4, prmOrigPolicyLevelGroupDataM.getCreateBy());
			ps.setTimestamp(5, prmOrigPolicyLevelGroupDataM.getCreateDate());
			ps.setString(6, prmOrigPolicyLevelGroupDataM.getUpdateBy());
			ps.setString(7,prmOrigPolicyLevelGroupDataM.getDescription());
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

    /**
     * @param prmOrigPolicyLevelGroupDataM
     * @return
     */
    private int updateTableORIG_POLICY_LEVEL_GROUP_TOTAL(OrigPolicyLevelGroupTotalDataM prmOrigPolicyLevelGroupDataM) throws OrigMasterMException {
        int returnRows = 0;
		PreparedStatement ps = null;
		Connection conn = null;
		try {
			//conn = Get Connection
			conn = getConnection();
			StringBuffer sql = new StringBuffer("");
			sql.append("UPDATE ORIG_POLICY_LEVEL_GROUP_TOTAL ");
			
			sql.append(" SET TOTAL_POLICY_AMT =?,UPDATE_BY=?,UPDATE_DATE=SYSDATE,DESCRIPTION=? ");			 			
			sql.append(" WHERE POLICY_VERSION = ? AND GROUP_NAME=?  ");
			
			String dSql = String.valueOf(sql);
			log.debug("Sql="+dSql);
			ps = conn.prepareStatement(dSql);			
			ps.setInt(1, prmOrigPolicyLevelGroupDataM.getToalLevelAmount());
			ps.setString(2, prmOrigPolicyLevelGroupDataM.getUpdateBy());	
			ps.setString(3,prmOrigPolicyLevelGroupDataM.getDescription());
			ps.setString(4,prmOrigPolicyLevelGroupDataM.getPolicyVersion());
			ps.setString(5,prmOrigPolicyLevelGroupDataM.getGroupName());			 			
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
     * @see com.eaf.orig.shared.dao.OrigPolicyLevelGroupTotalMDAO#deleteTableORIG_POLICY_LEVEL_GROUP_TOTAL(java.lang.String)
     */
    public void deleteTableORIG_POLICY_LEVEL_GROUP_TOTAL(String policyVersion) throws OrigMasterMException {
        PreparedStatement ps = null;
        Connection conn = null;
		try {
			//conn = Get Connection
			conn = getConnection();
			StringBuffer sql = new StringBuffer("");
			sql.append("DELETE ORIG_POLICY_LEVEL_GROUP_TOTAL ");
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

    /* (non-Javadoc)
     * @see com.eaf.orig.shared.dao.OrigPolicyLevelGroupTotalMDAO#findPolicyLevelGroup(java.lang.String, java.lang.String)
     */
    public OrigPolicyLevelGroupTotalDataM findPolicyLevelGroup(String policyVersion, String groupName) throws OrigMasterMException {
        PreparedStatement ps = null;
		ResultSet rs = null;
		Connection conn = null;
		try {
			//conn = Get Connection
			conn = getConnection();
			StringBuffer sql = new StringBuffer("");
			
			sql.append("SELECT POLICY_VERSION,GROUP_NAME,TOTAL_POLICY_AMT,CREATE_BY,CREATE_DATE,UPDATE_BY,UPDATE_DATE,DESCRIPTION  ");		
			sql.append(" FROM ORIG_POLICY_LEVEL_GROUP_TOTAL WHERE POLICY_VERSION = ? ");
 			sql.append(" AND GROUP_NAME=? ");
			String dSql = String.valueOf(sql);
			log.debug("Sql=" + dSql);
			ps = conn.prepareStatement(dSql);
			rs = null;
			ps.setString(1, policyVersion);
            ps.setString(2,groupName);
			rs = ps.executeQuery();
			OrigPolicyLevelGroupTotalDataM prmOrigPolicyLevelGroupTotalDataM = null;
			Vector resultVect = new Vector();
			if (rs.next()) {
			    prmOrigPolicyLevelGroupTotalDataM = new  OrigPolicyLevelGroupTotalDataM();
			    prmOrigPolicyLevelGroupTotalDataM.setPolicyVersion(policyVersion);
			    prmOrigPolicyLevelGroupTotalDataM.setGroupName(rs.getString(2));			    
			    prmOrigPolicyLevelGroupTotalDataM.setToalLevelAmount(rs.getInt(3));			    
			    prmOrigPolicyLevelGroupTotalDataM.setCreateBy(rs.getString(4));
			    prmOrigPolicyLevelGroupTotalDataM.setCreateDate(rs.getTimestamp(5));
			    prmOrigPolicyLevelGroupTotalDataM.setUpdateBy(rs.getString(6));
			    prmOrigPolicyLevelGroupTotalDataM.setUpdateDate(rs.getTimestamp(7));
			    prmOrigPolicyLevelGroupTotalDataM.setDescription(rs.getString(8));				 
			}
			return prmOrigPolicyLevelGroupTotalDataM;
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

  
    

   

}
