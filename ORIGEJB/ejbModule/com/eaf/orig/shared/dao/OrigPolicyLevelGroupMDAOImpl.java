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

import com.eaf.orig.master.shared.model.OrigPolicyLevelGroupDataM;
import com.eaf.orig.shared.dao.exceptions.OrigMasterMException;

/**
 * @author Sankom
 *
 * Type: OrigPolicyLevelGroupMDAOImpl
 */
public class OrigPolicyLevelGroupMDAOImpl extends OrigObjectDAO implements OrigPolicyLevelGroupMDAO {
    private static Logger log = Logger.getLogger(OrigPolicyLevelGroupMDAOImpl.class);
    /**
     * 
     */
    public OrigPolicyLevelGroupMDAOImpl() {
        super();
        
    }

    /* (non-Javadoc)
     * @see com.eaf.orig.shared.dao.OrigPolicyLevelGroupMDAO#createModelOrigPolicyLevelGroupM(com.eaf.orig.master.shared.model.OrigPolicyLevelGroupDataM)
     */
    public void createModelOrigPolicyLevelGroupM(OrigPolicyLevelGroupDataM prmOrigPolicyLevelGroupDataM) throws OrigMasterMException {
        try {
			
			createTableORIG_POLICY_LEVEL_GROUP(prmOrigPolicyLevelGroupDataM);
			
		} catch (Exception e) {
			log.error("Error >> ", e);
			throw new OrigMasterMException(e.getMessage());
		}
        
    }

    /**
     * @param prmOrigPolicyLevelGroupDataM
     */
    private void createTableORIG_POLICY_LEVEL_GROUP(OrigPolicyLevelGroupDataM prmOrigPolicyLevelGroupDataM) throws OrigMasterMException {
        PreparedStatement ps = null;
        Connection conn = null;
		try {
			//conn = Get Connection
			conn = getConnection();
			StringBuffer sql = new StringBuffer("");
			sql.append("INSERT INTO ORIG_POLICY_LEVEL_GROUP ");
			sql.append("(POLICY_VERSION,GROUP_NAME,LEVEL_NAME,LEVEL_AMOUNT,CREATE_BY ");
			sql.append(" ,CREATE_DATE,UPDATE_BY,UPDATE_DATE,DESCRIPTION  ) ");
			sql.append(" VALUES(?,?,?,?,?  ,?,?,SYSDATE,?) ");
			String dSql = String.valueOf(sql);
			log.debug("Sql=" + dSql  ); 
			ps = conn.prepareStatement(dSql);
			ps.setString(1, prmOrigPolicyLevelGroupDataM.getPolicyVersion());
			ps.setString(2,  prmOrigPolicyLevelGroupDataM.getGroupName());
			ps.setString(3, prmOrigPolicyLevelGroupDataM.getLevelName());
			ps.setInt(4, prmOrigPolicyLevelGroupDataM.getLevelAmount());			
			ps.setString(5, prmOrigPolicyLevelGroupDataM.getCreateBy());
			ps.setTimestamp(6, prmOrigPolicyLevelGroupDataM.getCreateDate());
			ps.setString(7, prmOrigPolicyLevelGroupDataM.getUpdateBy());
			ps.setString(8,prmOrigPolicyLevelGroupDataM.getDescription());
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
     * @see com.eaf.orig.shared.dao.OrigPolicyLevelGroupMDAO#deleteModelOrigPolicyLevelGroupM(com.eaf.orig.master.shared.model.OrigPolicyLevelGroupDataM)
     */
    public void deleteModelOrigPolicyLevelGroupM(OrigPolicyLevelGroupDataM prmOrigPolicyLevelGroupDataM) throws OrigMasterMException {
        try {
			deleteTableORIG_POLICY_LEVEL_GROUP(prmOrigPolicyLevelGroupDataM);
		} catch (Exception e) {
			log.fatal(e.getLocalizedMessage());
			throw new OrigMasterMException(e.getMessage());
		}
    }

    /**
     * @param prmOrigPolicyLevelGroupDataM
     */
    private void deleteTableORIG_POLICY_LEVEL_GROUP(OrigPolicyLevelGroupDataM prmOrigPolicyLevelGroupDataM)throws OrigMasterMException {
        PreparedStatement ps = null;
        Connection conn = null;
		try {
			//conn = Get Connection
			conn = getConnection();
			StringBuffer sql = new StringBuffer("");
			sql.append("DELETE ORIG_POLICY_LEVEL_GROUP ");
			sql.append(" WHERE POLICY_VERSION = ? ");
			sql.append(" AND GROUP_NAME=? ");
			sql.append(" AND LEVEL_NAME=? ");
			String dSql = String.valueOf(sql);
			log.debug("Sql="+dSql);
			ps = conn.prepareStatement(dSql);
			ps.setString(1, prmOrigPolicyLevelGroupDataM.getPolicyVersion());
			ps.setString(2, prmOrigPolicyLevelGroupDataM.getGroupName());
			ps.setString(3, prmOrigPolicyLevelGroupDataM.getLevelName());
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
     * @see com.eaf.orig.shared.dao.OrigPolicyLevelGroupMDAO#loadModelOrigOrigPolicyLevelGroupM(java.lang.String)
     */
    public Vector loadModelOrigOrigPolicyLevelGroupM(String policyVersion) throws OrigMasterMException {
        try {
			Vector result = selectTableORIG_POLICY_LEVEL_GROUP(policyVersion);
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
    private Vector selectTableORIG_POLICY_LEVEL_GROUP(String policyVersion) throws OrigMasterMException{
        PreparedStatement ps = null;
		ResultSet rs = null;
		Connection conn = null;
		try {
			//conn = Get Connection
			conn = getConnection();
			StringBuffer sql = new StringBuffer("");
			
			sql.append("SELECT POLICY_VERSION,GROUP_NAME,LEVEL_NAME,LEVEL_AMOUNT,CREATE_BY,CREATE_DATE,UPDATE_BY,UPDATE_DATE,DESCRIPTION   ");		
			sql.append(" FROM ORIG_POLICY_LEVEL_GROUP WHERE POLICY_VERSION = ? ");
 			
			String dSql = String.valueOf(sql);
			log.debug("Sql=" + dSql);
			ps = conn.prepareStatement(dSql);
			rs = null;
			ps.setString(1, policyVersion);

			rs = ps.executeQuery();
			OrigPolicyLevelGroupDataM prmOrigPolicyLevelGroupDataM = null;
			Vector resultVect = new Vector();
			while (rs.next()) {
			    prmOrigPolicyLevelGroupDataM = new  OrigPolicyLevelGroupDataM();
			    prmOrigPolicyLevelGroupDataM.setPolicyVersion(policyVersion);
			    prmOrigPolicyLevelGroupDataM.setGroupName(rs.getString(2));
			    prmOrigPolicyLevelGroupDataM.setLevelName(rs.getString(3));
			    prmOrigPolicyLevelGroupDataM.setLevelAmount(rs.getInt(4));			    
			    prmOrigPolicyLevelGroupDataM.setCreateBy(rs.getString(5));
			    prmOrigPolicyLevelGroupDataM.setCreateDate(rs.getTimestamp(6));
			    prmOrigPolicyLevelGroupDataM.setUpdateBy(rs.getString(7));
			    prmOrigPolicyLevelGroupDataM.setUpdateDate(rs.getTimestamp(8));		
			    prmOrigPolicyLevelGroupDataM.setDescription(rs.getString(9));
				resultVect.add(prmOrigPolicyLevelGroupDataM);
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
     * @see com.eaf.orig.shared.dao.OrigPolicyLevelGroupMDAO#saveUpdateModelOrigPolicyLevelGroupM(com.eaf.orig.master.shared.model.OrigPolicyLevelGroupDataM)
     */
    public void saveUpdateModelOrigPolicyLevelGroupM(OrigPolicyLevelGroupDataM prmOrigPolicyLevelGroupDataM) throws OrigMasterMException {
        int returnRows = 0;
		try {
			returnRows = updateTableORIG_POLICY_LEVEL_GROUP(prmOrigPolicyLevelGroupDataM);
			
			if (returnRows == 0) {
				log.debug("New record then can't update record in table ORIG_POLICY_LEVEL_GROUP then call Insert method");	
				createTableORIG_POLICY_LEVEL_GROUP(prmOrigPolicyLevelGroupDataM);
			}
		} catch (Exception e) {
			log.error("Error >> ", e);
			throw new OrigMasterMException(e.getMessage());
		}
        
    }

    /**
     * @param prmOrigPolicyLevelGroupDataM
     * @return
     */
    private int updateTableORIG_POLICY_LEVEL_GROUP(OrigPolicyLevelGroupDataM prmOrigPolicyLevelGroupDataM) throws OrigMasterMException {
        int returnRows = 0;
		PreparedStatement ps = null;
		Connection conn = null;
		try {
			//conn = Get Connection
			conn = getConnection();
			StringBuffer sql = new StringBuffer("");
			sql.append("UPDATE ORIG_POLICY_LEVEL_GROUP ");
			
			sql.append(" SET LEVEL_AMOUNT=?,UPDATE_BY=?,UPDATE_DATE=SYSDATE,DESCRIPTION=? ");			 			
			sql.append(" WHERE POLICY_VERSION = ? AND GROUP_NAME=? AND LEVEL_NAME=? ");
			
			String dSql = String.valueOf(sql);
			log.debug("Sql="+dSql);
			ps = conn.prepareStatement(dSql);			
			ps.setInt(1, prmOrigPolicyLevelGroupDataM.getLevelAmount());
			ps.setString(2, prmOrigPolicyLevelGroupDataM.getUpdateBy());	
			ps.setString(3,prmOrigPolicyLevelGroupDataM.getDescription());
			ps.setString(4,prmOrigPolicyLevelGroupDataM.getPolicyVersion());
			ps.setString(5,prmOrigPolicyLevelGroupDataM.getGroupName());
			ps.setString(6,prmOrigPolicyLevelGroupDataM.getLevelName());			
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
     * @see com.eaf.orig.shared.dao.OrigPolicyLevelGroupMDAO#deleteNotInKeyTableORIG_POLICY_LEVEL_GROUP(java.util.Vector, java.lang.String)
     */
  //  public void deleteNotInKeyTableORIG_POLICY_LEVEL_GROUP(Vector policyGroupVect, String policyVersion) throws OrigMasterMException {
       /*  PreparedStatement ps = null;
		try {
			//conn = Get Connection
			conn = getConnection();
			StringBuffer sql = new StringBuffer("");
			sql.append("DELETE ORIG_POLICY_LEVEL_GROUP ");
			sql.append(" WHERE POLICY_VERSION = ? ");
			sql.append(" AND GROUP=? ");
			sql.append(" AND LEVEL_NAME=? ");
			String dSql = String.valueOf(sql);
			log.debug("Sql="+dSql);
			ps = conn.prepareStatement(dSql);
			ps.setString(1, prmOrigPolicyLevelGroupDataM.getPolicyVersion());
			ps.setString(2, prmOrigPolicyLevelGroupDataM.getGroupName());
			ps.setString(3, prmOrigPolicyLevelGroupDataM.getLevelName());
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
		}*/
        
    //}

    /* (non-Javadoc)
     * @see com.eaf.orig.shared.dao.OrigPolicyLevelGroupMDAO#deleteTableORIG_POLICY_LEVEL_GROUP(java.lang.String)
     */
    public void deleteTableORIG_POLICY_LEVEL_GROUP(String policyVersion) throws OrigMasterMException {
        PreparedStatement ps = null;
        Connection conn = null;
		try {
			//conn = Get Connection
			conn = getConnection();
			StringBuffer sql = new StringBuffer("");
			sql.append("DELETE ORIG_POLICY_LEVEL_GROUP ");
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
     * @see com.eaf.orig.shared.dao.OrigPolicyLevelGroupMDAO#findPolicyLevelGroup(com.eaf.orig.master.shared.model.OrigPolicyLevelGroupDataM)
     */
    public Vector findPolicyLevelGroup(String policyVersion,String groupName) throws OrigMasterMException {
        PreparedStatement ps = null;
		ResultSet rs = null;
		Connection conn = null;
		try {
			//conn = Get Connection
			conn = getConnection();
			StringBuffer sql = new StringBuffer("");
			
			sql.append("SELECT POLICY_VERSION,GROUP_NAME,LEVEL_NAME,LEVEL_AMOUNT,CREATE_BY,CREATE_DATE,UPDATE_BY,UPDATE_DATE,DESCRIPTION   ");		
			sql.append(" FROM ORIG_POLICY_LEVEL_GROUP WHERE POLICY_VERSION = ? ");
 			sql.append(" AND GROUP_NAME=? ");
			String dSql = String.valueOf(sql);
			log.debug("Sql=" + dSql);
			ps = conn.prepareStatement(dSql);
			rs = null;
			ps.setString(1, policyVersion);
            ps.setString(2,groupName);
			rs = ps.executeQuery();
			OrigPolicyLevelGroupDataM resOrigPolicyLevelGroupDataM = null;
			Vector resultVect = new Vector();
			while (rs.next()) {
			    resOrigPolicyLevelGroupDataM = new  OrigPolicyLevelGroupDataM();
			    resOrigPolicyLevelGroupDataM.setPolicyVersion(rs.getString(1));
			    resOrigPolicyLevelGroupDataM.setGroupName(rs.getString(2));
			    resOrigPolicyLevelGroupDataM.setLevelName(rs.getString(3));
			    resOrigPolicyLevelGroupDataM.setLevelAmount(rs.getInt(4));			    
			    resOrigPolicyLevelGroupDataM.setCreateBy(rs.getString(5));
			    resOrigPolicyLevelGroupDataM.setCreateDate(rs.getTimestamp(6));
			    resOrigPolicyLevelGroupDataM.setUpdateBy(rs.getString(7));
			    resOrigPolicyLevelGroupDataM.setUpdateDate(rs.getTimestamp(8));
			    resOrigPolicyLevelGroupDataM.setDescription(rs.getString(9));
				resultVect.add(resOrigPolicyLevelGroupDataM);
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

}
