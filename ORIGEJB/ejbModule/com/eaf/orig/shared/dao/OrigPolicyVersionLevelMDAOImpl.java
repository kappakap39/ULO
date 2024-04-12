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

import com.eaf.orig.master.shared.model.OrigPolicyVersionLevelDataM;
import com.eaf.orig.shared.dao.exceptions.OrigMasterMException;

/**
 * @author Sankom
 *
 * Type: OrigPolicyVersionLevelMDAOImpl
 */
public class OrigPolicyVersionLevelMDAOImpl extends OrigObjectDAO implements OrigPolicyVersionLevelMDAO {
    private static Logger log = Logger.getLogger(OrigPolicyVersionLevelMDAOImpl.class);
    /**
     * 
     */
    public OrigPolicyVersionLevelMDAOImpl() {
        super();
        // TODO Auto-generated constructor stub
    }

    /* (non-Javadoc)
     * @see com.eaf.orig.shared.dao.OrigPolicyVersionLevelMDAO#createModelOrigPolicyVersionLevelM(com.eaf.orig.master.shared.model.OrigPolicyVersionLevelDataM)
     */
    public void createModelOrigPolicyVersionLevelM(OrigPolicyVersionLevelDataM prmOrigPolicyVersionLevelDataM) throws OrigMasterMException {
        try {			
			createTableORIG_POLICY_VERSION_LEVEL(prmOrigPolicyVersionLevelDataM);			
		} catch (Exception e) {
			log.error("Error >> ", e);
			throw new OrigMasterMException(e.getMessage());
		}
        
    }

    /**
     * @param prmOrigPolicyVersionLevelDataM
     */
    private void createTableORIG_POLICY_VERSION_LEVEL(OrigPolicyVersionLevelDataM prmOrigPolicyVersionLevelDataM) throws OrigMasterMException{
        PreparedStatement ps = null;
        Connection conn = null;
		try {
			//conn = Get Connection
			conn = getConnection();
			StringBuffer sql = new StringBuffer("");
			sql.append("INSERT INTO ORIG_POLICY_VERSION_LEVEL ");
			sql.append("(POLICY_VERSION,LEVEL_NAME,CREATE_BY,CREATE_DATE,UPDATE_BY ");
			sql.append(",UPDATE_DATE ,DESCRIPTION ) ");
			sql.append(" VALUES(?,?,?,?,? ,SYSDATE,?) ");
			String dSql = String.valueOf(sql);
			log.debug("Sql=" + dSql  ); 
			ps = conn.prepareStatement(dSql);			 
			ps.setString(1, prmOrigPolicyVersionLevelDataM.getPolicyVersion());			 
			ps.setString(2, prmOrigPolicyVersionLevelDataM.getLevelName());			
			ps.setString(3, prmOrigPolicyVersionLevelDataM.getCreateBy());
			ps.setTimestamp(4, prmOrigPolicyVersionLevelDataM.getCreateDate());
			ps.setString(5, prmOrigPolicyVersionLevelDataM.getUpdateBy());			
			ps.setString(6,prmOrigPolicyVersionLevelDataM.getDescription());
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
     * @see com.eaf.orig.shared.dao.OrigPolicyVersionLevelMDAO#deleteModelOrigPolicyVersionLevelM(com.eaf.orig.master.shared.model.OrigPolicyVersionLevelDataM)
     */
    public void deleteModelOrigPolicyVersionLevelM(OrigPolicyVersionLevelDataM prmOrigPolicyVersionLevelDataM) throws OrigMasterMException {
        try {			
			deleteTableORIG_POLICY_VERSION_LEVEL(prmOrigPolicyVersionLevelDataM);			
		} catch (Exception e) {
			log.error("Error >> ", e);
			throw new OrigMasterMException(e.getMessage());
		}
    }

    /**
     * @param prmOrigPolicyVersionLevelDataM
     */
    private void deleteTableORIG_POLICY_VERSION_LEVEL(OrigPolicyVersionLevelDataM prmOrigPolicyVersionLevelDataM)throws OrigMasterMException {
        PreparedStatement ps = null;
        Connection conn = null;
		try {
			//conn = Get Connection
			conn = getConnection();
			StringBuffer sql = new StringBuffer("");
			sql.append("DELETE ORIG_POLICY_VERSION_LEVEL ");
			sql.append(" WHERE POLICY_VERSION = ? ");			 
			sql.append(" AND LEVEL_NAME =? ");
			String dSql = String.valueOf(sql);
			log.debug("Sql="+dSql);
			ps = conn.prepareStatement(dSql);
			ps.setString(1, prmOrigPolicyVersionLevelDataM.getPolicyVersion());
			ps.setString(2, prmOrigPolicyVersionLevelDataM.getLevelName());
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
     * @see com.eaf.orig.shared.dao.OrigPolicyVersionLevelMDAO#loadModelOrigPolicyVersionLevelM(java.lang.String)
     */
    public Vector loadModelOrigPolicyVersionLevelM(String policyVersion) throws OrigMasterMException {
        try {
			Vector result = selectTableORIG_POLICY_VERSION_LEVEL(policyVersion);
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
    private Vector selectTableORIG_POLICY_VERSION_LEVEL(String policyVersion)throws OrigMasterMException {
        PreparedStatement ps = null;
		ResultSet rs = null;
		Connection conn = null;
		try {
			//conn = Get Connection
			conn = getConnection();
			StringBuffer sql = new StringBuffer("");
			
			sql.append("SELECT POLICY_VERSION,LEVEL_NAME,CREATE_BY,CREATE_DATE,UPDATE_BY,UPDATE_DATE,DESCRIPTION   ");		
			sql.append(" FROM ORIG_POLICY_VERSION_LEVEL WHERE POLICY_VERSION = ? ");
 			
			String dSql = String.valueOf(sql);
			log.debug("Sql=" + dSql);
			ps = conn.prepareStatement(dSql);
			rs = null;
			ps.setString(1, policyVersion);

			rs = ps.executeQuery();
			OrigPolicyVersionLevelDataM prmOrigPolicyVersionLevelDataM = null;
			Vector resultVect = new Vector();
			while (rs.next()) {
			    prmOrigPolicyVersionLevelDataM = new  OrigPolicyVersionLevelDataM();
			    prmOrigPolicyVersionLevelDataM.setPolicyVersion(rs.getString(1));
			    prmOrigPolicyVersionLevelDataM.setLevelName(rs.getString(2));			    			    
			    prmOrigPolicyVersionLevelDataM.setCreateBy(rs.getString(3));
			    prmOrigPolicyVersionLevelDataM.setCreateDate(rs.getTimestamp(4));
			    prmOrigPolicyVersionLevelDataM.setUpdateBy(rs.getString(5));
			    prmOrigPolicyVersionLevelDataM.setUpdateDate(rs.getTimestamp(6));		
			    prmOrigPolicyVersionLevelDataM.setDescription(rs.getString(7));
				resultVect.add(prmOrigPolicyVersionLevelDataM);
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
     * @see com.eaf.orig.shared.dao.OrigPolicyVersionLevelMDAO#saveUpdateModelOrigPolicyVersionLevelM(com.eaf.orig.master.shared.model.OrigPolicyVersionLevelDataM)
     */
    public void saveUpdateModelOrigPolicyVersionLevelM(OrigPolicyVersionLevelDataM prmOrigPolicyVersionLevelDataM) throws OrigMasterMException {
        int returnRows = 0;
		try {
			returnRows = updateTableORIG_POLICY_VERSION_LEVEL(prmOrigPolicyVersionLevelDataM);
			
			if (returnRows == 0) {
				log.debug("New record then can't update record in table ORIG_POLICY_VERSION_LEVEL then call Insert method");	
				createTableORIG_POLICY_VERSION_LEVEL(prmOrigPolicyVersionLevelDataM);
			}
		} catch (Exception e) {
			log.error("Error >> ", e);
			throw new OrigMasterMException(e.getMessage());
		} 
        
    }

    /**
     * @param prmOrigPolicyVersionLevelDataM
     * @return
     */
    private int updateTableORIG_POLICY_VERSION_LEVEL(OrigPolicyVersionLevelDataM prmOrigPolicyVersionLevelDataM) throws OrigMasterMException{
        int returnRows = 0;
		PreparedStatement ps = null;
		Connection conn = null;
		try {
			//conn = Get Connection
			conn = getConnection();
			StringBuffer sql = new StringBuffer("");
			sql.append("UPDATE ORIG_POLICY_VERSION_LEVEL ");
			
			sql.append(" SET LEVEL_NAME=?,UPDATE_BY=?,UPDATE_DATE=SYSDATE,DESCRIPTION=?   ");
			 
			
			sql.append(" WHERE POLICY_VERSION=? AND LEVEL_NAME=?");
			
			String dSql = String.valueOf(sql);
			log.debug("Sql="+dSql);
			ps = conn.prepareStatement(dSql);
			ps.setString(1, prmOrigPolicyVersionLevelDataM.getLevelName());
			ps.setString(2, prmOrigPolicyVersionLevelDataM.getUpdateBy());	
			ps.setString(3,prmOrigPolicyVersionLevelDataM.getDescription());
			ps.setString(4, prmOrigPolicyVersionLevelDataM.getPolicyVersion());
			ps.setString(5, prmOrigPolicyVersionLevelDataM.getLevelName()); 
			;
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
     * @see com.eaf.orig.shared.dao.OrigPolicyVersionLevelMDAO#deleteNotInKeyTableORIG_POLICY_VERSION_LEVEL(java.util.Vector)
     */
    public void deleteNotInKeyTableORIG_POLICY_VERSION_LEVEL(Vector vOrigPolicyVersionLevel,String policyVersion) throws OrigMasterMException {
        PreparedStatement ps = null;
        Connection conn = null;
		try {
			//conn = Get Connection
			conn = getConnection();
			StringBuffer sql = new StringBuffer("");			
			if(vOrigPolicyVersionLevel!=null  ){					    
			  
			sql.append("DELETE ORIG_POLICY_VERSION_LEVEL ");
			sql.append(" WHERE POLICY_VERSION = ? ");			 
			
			if(vOrigPolicyVersionLevel!=null &&vOrigPolicyVersionLevel.size()>0){
			OrigPolicyVersionLevelDataM prmOrigPolicyVersionLevelDataM =(OrigPolicyVersionLevelDataM) vOrigPolicyVersionLevel.get(0);
			sql.append(" AND LEVEL_NAME  NOT in (");
			sql.append( "'"+ prmOrigPolicyVersionLevelDataM.getLevelName()+"'");
			for(int i=1;i<vOrigPolicyVersionLevel.size();i++){
			    OrigPolicyVersionLevelDataM origPolicyVersionLevelDataM=(OrigPolicyVersionLevelDataM)vOrigPolicyVersionLevel.get(i);
			    sql.append( ",'"+ origPolicyVersionLevelDataM.getLevelName()+"'");
			}
			sql.append(")");
			}		
			String dSql = String.valueOf(sql);
			log.debug("Sql="+dSql);
			ps = conn.prepareStatement(dSql);
			ps.setString(1, policyVersion);
			 
			ps.executeUpdate(); 
		    
			}
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
    public void deleteTableORIG_POLICY_VERSION_LEVEL(String policyVersion)throws OrigMasterMException {
        PreparedStatement ps = null;
        Connection conn = null;
		try {
			//conn = Get Connection
			conn = getConnection();
			StringBuffer sql = new StringBuffer("");
			sql.append("DELETE ORIG_POLICY_VERSION_LEVEL ");
			sql.append(" WHERE POLICY_VERSION = ? ");
			String dSql = String.valueOf(sql);
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
