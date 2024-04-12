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
import java.sql.Timestamp;
import java.util.Calendar;
import java.util.Vector;

import org.apache.log4j.Logger;

import com.eaf.orig.master.shared.model.ApprovAuthorM;
import com.eaf.orig.master.shared.model.OrigPolicyLevelGroupDataM;
import com.eaf.orig.master.shared.model.OrigPolicyLevelGroupTotalDataM;
import com.eaf.orig.master.shared.model.OrigPolicyLevelMapDataM;
import com.eaf.orig.master.shared.model.OrigPolicyRulesExceptionDataM;
import com.eaf.orig.master.shared.model.OrigPolicyVersionDataM;
import com.eaf.orig.master.shared.model.OrigPolicyVersionLevelDataM;
import com.eaf.orig.shared.dao.exceptions.OrigMasterMException;

/**
 * @author Sankom
 *
 * Type: OrigPolicyVersionMDAOImpl
 */
public class OrigPolicyVersionMDAOImpl extends OrigObjectDAO implements OrigPolicyVersionMDAO {
    private static Logger log = Logger.getLogger(OrigPolicyVersionMDAOImpl.class);
    /**
     * 
     */
    public OrigPolicyVersionMDAOImpl() {
        super();
        // TODO Auto-generated constructor stub
    }

    /* (non-Javadoc)
     * @see com.eaf.orig.shared.dao.OrigPolicyVersionMDAO#createModelOrigPolicyVersionDataM(com.eaf.orig.master.shared.model.OrigPolicyVersionDataM)
     */
    public void createModelOrigPolicyVersionDataM(OrigPolicyVersionDataM prmOrigPolicyVersionDataM) throws OrigMasterMException {
        try {
            setupPolicyVersion(prmOrigPolicyVersionDataM);
			createTableORIG_POLICY_VERSION(prmOrigPolicyVersionDataM);
			//Level
			Vector vOrigPolicyVersionLevel=prmOrigPolicyVersionDataM.getVPolicyVersionLevel();
			 OrigPolicyVersionLevelMDAO origPolicyversionLevelDAO=ORIGDAOFactory.getOrigPolicyVersionLevelMDAO();
	        if(vOrigPolicyVersionLevel!=null){
	          for(int i=0;i<vOrigPolicyVersionLevel.size();i++){
	             OrigPolicyVersionLevelDataM origPolicyVersionLevelDataM= (OrigPolicyVersionLevelDataM)vOrigPolicyVersionLevel.get(i);
	             origPolicyversionLevelDAO.createModelOrigPolicyVersionLevelM(origPolicyVersionLevelDataM);	            
	          }    
	          //origPolicyversionLevelDAO.deleteNotInKeyTableORIG_POLICY_VERSION_LEVEL(vOrigPolicyVersionLevel);
	        }
	        //Group 
	        Vector vOrigPolicyVersionGroup=prmOrigPolicyVersionDataM.getVPolicyVesionGroup();
	        OrigPolicyLevelGroupMDAO origPolicyLevelGroupDAO=ORIGDAOFactory.getOrigPolicyLevelGroupMDAO();
	        if (vOrigPolicyVersionGroup != null) {
	            for (int i = 0; i < vOrigPolicyVersionGroup.size(); i++) {
	                OrigPolicyLevelGroupDataM  origPolicyLevelGroupDataM = (OrigPolicyLevelGroupDataM) vOrigPolicyVersionGroup.get(i);
	                origPolicyLevelGroupDAO.createModelOrigPolicyLevelGroupM(origPolicyLevelGroupDataM);
	            }
	           // origPolicyLevelGroupDAO.deleteNotInKeyTableORIG_POLICY_LEVEL_GROUP(vOrigPolicyVersionGroup,prmOrigPolicyVersionDataM.getPolicyVersion());
	        }
	        //Map Rules
	        Vector vOrigPolicyMapRules=prmOrigPolicyVersionDataM.getVPolicyMapRules();      
	        OrigPolicyLevelMapMDAO origPolicyLevelMapMDAO=ORIGDAOFactory.getOrigPolicyLevelMapMDAO();
	         if (vOrigPolicyMapRules != null) {
	             for (int i = 0; i < vOrigPolicyMapRules.size(); i++) {
	                 OrigPolicyLevelMapDataM  origPolicyMapRulesDataM = (OrigPolicyLevelMapDataM) vOrigPolicyMapRules.get(i);
	                 origPolicyLevelMapMDAO.createModelOrigPolicyLevelMapDataM(origPolicyMapRulesDataM);
	             }
	            // origPolicyLevelMapMDAO.deleteNotInKeyTableORIG_POLICY_LEVEL_MAP(vOrigPolicyMapRules);
	         }
	        //Approval 
	        Vector vOrigPolicyApprovalDetail=prmOrigPolicyVersionDataM.getVPolicyApprovalDetails();         
	        OrigMasterApprovAuthorDAO  origApprvalAuthorDAO=OrigMasterDAOFactory.getOrigMasterApprovAuthorDAO();
	        //origApprvalAuthorDAO.deleteTableAPPROVAL_AUTHORITY(prmOrigPolicyVersionDataM.getPolicyVersion());
	        if (vOrigPolicyApprovalDetail != null) {
	            for (int i = 0; i < vOrigPolicyApprovalDetail.size(); i++) {
	                ApprovAuthorM  origvApprovAuthorM = (ApprovAuthorM) vOrigPolicyApprovalDetail.get(i);
	                origApprvalAuthorDAO.createModelOrigMasterApprovAuthorM(origvApprovAuthorM);
	            }
	        }
	        //Exception
	        Vector vOrigPolicyRulesException=prmOrigPolicyVersionDataM.getVPolicyRulesException();      
	        OrigPolicyRulesExceptionDAO origPolicyRulesExceptionDAO=ORIGDAOFactory.getOrigPolicyRulesExceptionDAO();
	         if (vOrigPolicyRulesException != null) {
	             for (int i = 0; i < vOrigPolicyRulesException.size(); i++) {
	                 OrigPolicyRulesExceptionDataM origPolicyRulesExceptionDataM = (OrigPolicyRulesExceptionDataM) vOrigPolicyRulesException.get(i);
	                 origPolicyRulesExceptionDAO.createModelOrigPolicyRulesExceptionDataM(origPolicyRulesExceptionDataM);
	             }
	            // origPolicyLevelMapMDAO.deleteNotInKeyTableORIG_POLICY_LEVEL_MAP(vOrigPolicyMapRules);
	         }
	         //Group Total
	         Vector vOrigPolicyVersionGroupTptal=prmOrigPolicyVersionDataM.getVPolicyVesionGroupTotal();
		        OrigPolicyLevelGroupTotalMDAO origPolicyLevelGroupTotalDAO=ORIGDAOFactory.getOrigPolicyLevelGroupTotalMDAO();
		        if (vOrigPolicyVersionGroupTptal != null) {
		            for (int i = 0; i < vOrigPolicyVersionGroupTptal.size(); i++) {
		                OrigPolicyLevelGroupTotalDataM  origPolicyLevelGroupTotalDataM = (OrigPolicyLevelGroupTotalDataM) vOrigPolicyVersionGroupTptal.get(i);
		                origPolicyLevelGroupTotalDAO.createModelOrigPolicyLevelGroupTotalM(origPolicyLevelGroupTotalDataM);
		            }
		           // origPolicyLevelGroupDAO.deleteNotInKeyTableORIG_POLICY_LEVEL_GROUP(vOrigPolicyVersionGroup,prmOrigPolicyVersionDataM.getPolicyVersion());
		        }
		} catch (Exception e) {
			log.error("Error >> ", e);
			throw new OrigMasterMException(e.getMessage());
		}
        
    }

    /**
     * @param prmOrigPolicyVersionDataM
     */
    private void createTableORIG_POLICY_VERSION(OrigPolicyVersionDataM prmOrigPolicyVersionDataM) throws OrigMasterMException {
        PreparedStatement ps = null;
        Connection conn = null;
		try {
			//conn = Get Connection
			conn = getConnection();
			StringBuffer sql = new StringBuffer("");
			sql.append("INSERT INTO ORIG_POLICY_VERSION ");
			sql.append("(POLICY_VERSION,EFFECTIVE_DATE,EXPIRE_DATE,CREATE_BY,CREATE_DATE ");
			sql.append(",UPDATE_BY,UPDATE_DATE,DESCRIPTION  ) ");
			sql.append(" VALUES(?,?,?,?,SYSDATE  ,?,SYSDATE,?) ");
			String dSql = String.valueOf(sql);
			log.debug("Sql=" + dSql  ); 
			ps = conn.prepareStatement(dSql);			 
			ps.setString(1, prmOrigPolicyVersionDataM.getPolicyVersion());			 
			ps.setDate(2, prmOrigPolicyVersionDataM.getEffectiveDate());
			ps.setDate(3, prmOrigPolicyVersionDataM.getExpireDate());
			ps.setString(4, prmOrigPolicyVersionDataM.getCreateBy());
			ps.setString(5, prmOrigPolicyVersionDataM.getUpdateBy());
			ps.setString(6,prmOrigPolicyVersionDataM.getDescription());
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
     * @see com.eaf.orig.shared.dao.OrigPolicyVersionMDAO#deleteModelOrigPolicyVersionDataM(com.eaf.orig.master.shared.model.OrigPolicyVersionDataM)
     */
    public void deleteModelOrigPolicyVersionDataM(OrigPolicyVersionDataM prmOrigPolicyVersionDataM) throws OrigMasterMException {
        try {
			
			deleteTableORIG_POLICY_VERSION(prmOrigPolicyVersionDataM);
			
		} catch (Exception e) {
			log.error("Error >> ", e);
			throw new OrigMasterMException(e.getMessage());
		}
        
    }

    /**
     * @param prmOrigPolicyVersionDataM
     */
    private void deleteTableORIG_POLICY_VERSION(OrigPolicyVersionDataM prmOrigPolicyVersionDataM)throws OrigMasterMException {
        PreparedStatement ps = null;
        Connection conn = null;
		try {
			//conn = Get Connection
			conn = getConnection();
			StringBuffer sql = new StringBuffer("");
			sql.append("DELETE ORIG_POLICY_VERSION ");
			sql.append(" WHERE POLICY_VERSION = ? ");			 			 
			String dSql = String.valueOf(sql);
			log.debug("Sql="+dSql);
			ps = conn.prepareStatement(dSql);
			ps.setString(1, prmOrigPolicyVersionDataM.getPolicyVersion());			
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
     * @see com.eaf.orig.shared.dao.OrigPolicyVersionMDAO#loadModelOrigPolicyVersionDataM(java.lang.String)
     */
    public OrigPolicyVersionDataM loadModelOrigPolicyVersionDataM(String policyVersion) throws OrigMasterMException {
        try {
            OrigPolicyVersionDataM result = selectTableORIG_POLICY_VERSION(policyVersion);
            Vector vPolicyLevels=ORIGDAOFactory.getOrigPolicyVersionLevelMDAO().loadModelOrigPolicyVersionLevelM(policyVersion);
            //Level
            if(vPolicyLevels!=null){
                result.setVPolicyVersionLevel(vPolicyLevels); 
            }else{
                result.setVPolicyVersionLevel(new Vector()); 
            }
            //Group
            Vector vPolicyGroups=ORIGDAOFactory.getOrigPolicyLevelGroupMDAO().loadModelOrigOrigPolicyLevelGroupM(policyVersion);
            if(vPolicyGroups!=null){
                result.setVPolicyVesionGroup(vPolicyGroups); 
            }else{
                result.setVPolicyVesionGroup(new Vector()); 
            }
            //Policy rules Map
            Vector vPolicyRulesMap=ORIGDAOFactory.getOrigPolicyLevelMapMDAO().loadModelOrigPolicyLevelMapDataM(policyVersion);
            if(vPolicyRulesMap!=null){
                result.setVPolicyMapRules(vPolicyRulesMap); 
            }else{
                result.setVPolicyMapRules(new Vector()); 
            }
            
            //Approval Authroty
            Vector vPolicyApprovalAuthority=OrigMasterDAOFactory.getOrigMasterApprovAuthorDAO().loadOrigMasterApprovalAuthority(policyVersion);
            if(vPolicyApprovalAuthority!=null){
                result.setVPolicyApprovalDetails(vPolicyApprovalAuthority); 
            }else{
                result.setVPolicyApprovalDetails(new Vector()); 
            }
            //Rules Exception
            Vector vPolicyRulesException=ORIGDAOFactory.getOrigPolicyRulesExceptionDAO().loadModelOrigPolicyRulesExceptionDataM(policyVersion);
            if(vPolicyRulesException!=null){
                result.setVPolicyRulesException(vPolicyRulesException);
            }else{
                result.setVPolicyRulesException(new Vector());
            }
            //Group Total
            Vector vPolicyGroupsTotal=ORIGDAOFactory.getOrigPolicyLevelGroupTotalMDAO().loadModelOrigOrigPolicyLevelGroupTotalM(policyVersion);
            if(vPolicyGroupsTotal!=null){
                result.setVPolicyVesionGroupTotal(vPolicyGroupsTotal); 
            }else{
                result.setVPolicyVesionGroupTotal(new Vector()); 
            }
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
    private OrigPolicyVersionDataM selectTableORIG_POLICY_VERSION(String policyVersion) throws OrigMasterMException {
        PreparedStatement ps = null;
		ResultSet rs = null;
		Connection conn = null;
		try {
			//conn = Get Connection
			conn = getConnection();
			StringBuffer sql = new StringBuffer("");			
			sql.append("SELECT POLICY_VERSION,EFFECTIVE_DATE,EXPIRE_DATE,CREATE_BY,CREATE_DATE,UPDATE_BY,UPDATE_DATE,DESCRIPTION ");		
			sql.append(" FROM ORIG_POLICY_VERSION  WHERE POLICY_VERSION = ? "); 		
			String dSql = String.valueOf(sql);
			log.debug("Sql=" + dSql);
			ps = conn.prepareStatement(dSql);
			rs = null;
			ps.setString(1, policyVersion);

			rs = ps.executeQuery();
			OrigPolicyVersionDataM prmOrigPolicyVersionDataM = null;
			//Vector resultVect = new Vector();
			if (rs.next()) {
			    prmOrigPolicyVersionDataM = new  OrigPolicyVersionDataM();
			    prmOrigPolicyVersionDataM.setPolicyVersion(rs.getString(1));
			    prmOrigPolicyVersionDataM.setEffectiveDate(rs.getDate(2));
			    prmOrigPolicyVersionDataM.setExpireDate(rs.getDate(3));
			    prmOrigPolicyVersionDataM.setCreateBy(rs.getString(4));
			    prmOrigPolicyVersionDataM.setCreateDate(rs.getTimestamp(5));
			    prmOrigPolicyVersionDataM.setUpdateBy(rs.getString(6));
			    prmOrigPolicyVersionDataM.setUpdateDate(rs.getTimestamp(7));		
			    prmOrigPolicyVersionDataM.setDescription(rs.getString(8));
				//resultVect.add(prmOrigPolicyVersionDataM);
			}
			return prmOrigPolicyVersionDataM;
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
     * @see com.eaf.orig.shared.dao.OrigPolicyVersionMDAO#saveUpdateModelOrigPolicyVersionDataM(com.eaf.orig.master.shared.model.OrigPolicyVersionDataM)
     */
    public void saveUpdateModelOrigPolicyVersionDataM(OrigPolicyVersionDataM prmOrigPolicyVersionDataM) throws OrigMasterMException {
        int returnRows = 0;
		try {
		    log.debug("Setup Policy Version");
		    setupPolicyVersion(prmOrigPolicyVersionDataM);
		    log.debug("update OrigPoicy Version");
			returnRows = updateTableORIG_POLICY_VERSION(prmOrigPolicyVersionDataM);			
			if (returnRows == 0) {
				log.debug("New record then can't update record in table ORIG_POLICY_VERSION then call Insert method");	
				createTableORIG_POLICY_VERSION(prmOrigPolicyVersionDataM);
			}
			//update sub
			//Level
			Vector vOrigPolicyVersionLevel=prmOrigPolicyVersionDataM.getVPolicyVersionLevel();
			 OrigPolicyVersionLevelMDAO origPolicyversionLevelDAO=ORIGDAOFactory.getOrigPolicyVersionLevelMDAO();
	        if(vOrigPolicyVersionLevel!=null){
	          for(int i=0;i<vOrigPolicyVersionLevel.size();i++){
	             OrigPolicyVersionLevelDataM origPolicyVersionLevelDataM= (OrigPolicyVersionLevelDataM)vOrigPolicyVersionLevel.get(i);
	             origPolicyversionLevelDAO.saveUpdateModelOrigPolicyVersionLevelM(origPolicyVersionLevelDataM);	            
	          }    
	          origPolicyversionLevelDAO.deleteNotInKeyTableORIG_POLICY_VERSION_LEVEL(vOrigPolicyVersionLevel,prmOrigPolicyVersionDataM.getPolicyVersion());
	        }
	        //Group
	        Vector vOrigPolicyVersionGroup=prmOrigPolicyVersionDataM.getVPolicyVesionGroup();
	        OrigPolicyLevelGroupMDAO origPolicyLevelGroupDAO=ORIGDAOFactory.getOrigPolicyLevelGroupMDAO();
	        if (vOrigPolicyVersionGroup != null) {
	            origPolicyLevelGroupDAO.deleteTableORIG_POLICY_LEVEL_GROUP(prmOrigPolicyVersionDataM.getPolicyVersion());
	            for (int i = 0; i < vOrigPolicyVersionGroup.size(); i++) {
	                OrigPolicyLevelGroupDataM  origPolicyLevelGroupDataM = (OrigPolicyLevelGroupDataM) vOrigPolicyVersionGroup.get(i);
	                origPolicyLevelGroupDAO.saveUpdateModelOrigPolicyLevelGroupM(origPolicyLevelGroupDataM);
	            }	            
	        }
	        //Map Rules
	        Vector vOrigPolicyMapRules=prmOrigPolicyVersionDataM.getVPolicyMapRules();      
	        OrigPolicyLevelMapMDAO origPolicyLevelMapMDAO=ORIGDAOFactory.getOrigPolicyLevelMapMDAO();
	         if (vOrigPolicyMapRules != null) {
	             origPolicyLevelMapMDAO.deleteTableORIG_POLICY_LEVEL_MAP(prmOrigPolicyVersionDataM.getPolicyVersion());
	             for (int i = 0; i < vOrigPolicyMapRules.size(); i++) {
	                 OrigPolicyLevelMapDataM  origPolicyMapRulesDataM = (OrigPolicyLevelMapDataM) vOrigPolicyMapRules.get(i);
	                 origPolicyLevelMapMDAO.createModelOrigPolicyLevelMapDataM(origPolicyMapRulesDataM);
	             }
	             
	         }
	         //Approval
	        Vector vOrigPolicyApprovalDetail=prmOrigPolicyVersionDataM.getVPolicyApprovalDetails();         
	        OrigMasterApprovAuthorDAO  origApprvalAuthorDAO=OrigMasterDAOFactory.getOrigMasterApprovAuthorDAO();
	        origApprvalAuthorDAO.deleteTableAPPROVAL_AUTHORITY(prmOrigPolicyVersionDataM.getPolicyVersion());
	        if (vOrigPolicyApprovalDetail != null) {
	            for (int i = 0; i < vOrigPolicyApprovalDetail.size(); i++) {
	                ApprovAuthorM  origvApprovAuthorM = (ApprovAuthorM) vOrigPolicyApprovalDetail.get(i);
	                origApprvalAuthorDAO.createModelOrigMasterApprovAuthorM(origvApprovAuthorM);
	            }
	        }
		 
	        //     	Exception
	        Vector vOrigPolicyRulesException=prmOrigPolicyVersionDataM.getVPolicyRulesException();      
	        OrigPolicyRulesExceptionDAO origPolicyRulesExceptionDAO=ORIGDAOFactory.getOrigPolicyRulesExceptionDAO();
	        origPolicyRulesExceptionDAO.deleteTableORIG_POLICY_RULES_EX(prmOrigPolicyVersionDataM.getPolicyVersion());
	         if (vOrigPolicyRulesException != null) {
	             for (int i = 0; i < vOrigPolicyRulesException.size(); i++) {
	                 OrigPolicyRulesExceptionDataM origPolicyRulesExceptionDataM = (OrigPolicyRulesExceptionDataM) vOrigPolicyRulesException.get(i);
	                 origPolicyRulesExceptionDAO.createModelOrigPolicyRulesExceptionDataM(origPolicyRulesExceptionDataM);
	             }	             
	         }
	         //Group Total
		        Vector vOrigPolicyVersionGroupTotal=prmOrigPolicyVersionDataM.getVPolicyVesionGroupTotal();
		        OrigPolicyLevelGroupTotalMDAO origPolicyLevelGroupTotalDAO=ORIGDAOFactory.getOrigPolicyLevelGroupTotalMDAO();
		        if (vOrigPolicyVersionGroupTotal != null) {
		            origPolicyLevelGroupTotalDAO.deleteTableORIG_POLICY_LEVEL_GROUP_TOTAL(prmOrigPolicyVersionDataM.getPolicyVersion());
		            for (int i = 0; i < vOrigPolicyVersionGroupTotal.size(); i++) {
		                OrigPolicyLevelGroupTotalDataM  origPolicyLevelGroupTotalDataM = (OrigPolicyLevelGroupTotalDataM) vOrigPolicyVersionGroupTotal.get(i);
		                origPolicyLevelGroupTotalDAO.saveUpdateModelOrigPolicyLevelGroupTotalM(origPolicyLevelGroupTotalDataM);
		            }	            
		        }
		} catch (Exception e) {
			log.error("Error >> ", e);
			throw new OrigMasterMException(e.getMessage());
		}
        
    }

    

    /**
     * @param prmOrigPolicyVersionDataM
     * @return
     */
    private int updateTableORIG_POLICY_VERSION(OrigPolicyVersionDataM prmOrigPolicyVersionDataM)throws OrigMasterMException{
        int returnRows = 0;
		PreparedStatement ps = null;
		Connection conn = null;
		try {
			//conn = Get Connection
			conn = getConnection();
			StringBuffer sql = new StringBuffer("");
			sql.append("UPDATE ORIG_POLICY_VERSION ");
			
			sql.append(" SET  EFFECTIVE_DATE=?,EXPIRE_DATE=?,UPDATE_BY=?,UPDATE_DATE=SYSDATE,DESCRIPTION=? ");			 			 			
			sql.append(" WHERE POLICY_VERSION = ?");
			
			String dSql = String.valueOf(sql);
			log.debug("Sql="+dSql);
			ps = conn.prepareStatement(dSql);
			ps.setDate(1, prmOrigPolicyVersionDataM.getEffectiveDate());
			ps.setDate(2, prmOrigPolicyVersionDataM.getExpireDate());	
			ps.setString(3, prmOrigPolicyVersionDataM.getUpdateBy());
			ps.setString(4,prmOrigPolicyVersionDataM.getDescription());
			ps.setString(5,prmOrigPolicyVersionDataM.getPolicyVersion());					
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
     * @see com.eaf.orig.shared.dao.OrigPolicyVersionMDAO#getPolicyVersion(java.sql.Date)
     */
    public OrigPolicyVersionDataM getPolicyVersion(String createDate) throws OrigMasterMException {
        PreparedStatement ps = null;
		ResultSet rs = null;
		Connection conn = null;
		try {
			//conn = Get Connection
			conn = getConnection();
			StringBuffer sql = new StringBuffer("");			
			sql.append("SELECT POLICY_VERSION,EFFECTIVE_DATE,EXPIRE_DATE,CREATE_BY,CREATE_DATE,UPDATE_BY,UPDATE_DATE,DESCRIPTION ");		
			sql.append(" FROM ORIG_POLICY_VERSION  WHERE   TO_DATE(?,'DD/MM/YYYY')  between EFFECTIVE_DATE  and EXPIRE_DATE ");
			sql.append(" ORDER BY UPDATE_DATE DESC ");
			String dSql = String.valueOf(sql);
			log.debug("Sql=" + dSql);
			ps = conn.prepareStatement(dSql);
			rs = null;
			
			ps.setString(1, createDate);
			 
			rs = ps.executeQuery();
			OrigPolicyVersionDataM prmOrigPolicyVersionDataM = null;			 
			if (rs.next()) {
			    prmOrigPolicyVersionDataM = new  OrigPolicyVersionDataM();
			    prmOrigPolicyVersionDataM.setPolicyVersion(rs.getString(1));
			    prmOrigPolicyVersionDataM.setEffectiveDate(rs.getDate(2));
			    prmOrigPolicyVersionDataM.setExpireDate(rs.getDate(3));
			    prmOrigPolicyVersionDataM.setCreateBy(rs.getString(4));
			    prmOrigPolicyVersionDataM.setCreateDate(rs.getTimestamp(5));
			    prmOrigPolicyVersionDataM.setUpdateBy(rs.getString(6));
			    prmOrigPolicyVersionDataM.setUpdateDate(rs.getTimestamp(7));			
			    prmOrigPolicyVersionDataM.setDescription(rs.getString(8));
			}
			return prmOrigPolicyVersionDataM;
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
    public void deleteModelOrigPolicyVersionDataM(String[] policyVesion) throws OrigMasterMException {
        try {
			if(policyVesion!=null){
			    OrigPolicyVersionLevelMDAO origPolicyversionLevelDAO=ORIGDAOFactory.getOrigPolicyVersionLevelMDAO();
			    OrigMasterApprovAuthorDAO  origApprvalAuthorDAO=OrigMasterDAOFactory.getOrigMasterApprovAuthorDAO();
                OrigPolicyLevelMapMDAO origPolicyLevelMapMDAO=ORIGDAOFactory.getOrigPolicyLevelMapMDAO();
			    OrigPolicyLevelGroupMDAO origPolicyLevelGroupDAO=ORIGDAOFactory.getOrigPolicyLevelGroupMDAO();
			    OrigPolicyRulesExceptionDAO origPolicyRulesExceptionDAO=ORIGDAOFactory.getOrigPolicyRulesExceptionDAO();
			    OrigPolicyLevelGroupTotalMDAO origPolicyLevelGroupTotalDAO=ORIGDAOFactory.getOrigPolicyLevelGroupTotalMDAO();
			    for(int i=0;i<policyVesion.length;i++){
			        String policyVersionName=(String)policyVesion[i];
			         deleteTableORIG_POLICY_VERSION(policyVersionName);
			         origPolicyversionLevelDAO.deleteTableORIG_POLICY_VERSION_LEVEL(policyVersionName);
			         origPolicyLevelMapMDAO.deleteTableORIG_POLICY_LEVEL_MAP(policyVersionName);
			         origApprvalAuthorDAO.deleteTableAPPROVAL_AUTHORITY(policyVersionName);
			         origPolicyLevelGroupDAO.deleteTableORIG_POLICY_LEVEL_GROUP(policyVersionName);
			         origPolicyRulesExceptionDAO.deleteTableORIG_POLICY_RULES_EX(policyVersionName);
			         origPolicyLevelGroupTotalDAO.deleteTableORIG_POLICY_LEVEL_GROUP_TOTAL(policyVersionName);
			    }
			}
			
		} catch (Exception e) {
			log.error("Error >> ", e);
			throw new OrigMasterMException(e.getMessage());
		}
        
    }
    private void deleteTableORIG_POLICY_VERSION(String policyVersion)throws OrigMasterMException {
        PreparedStatement ps = null;
        Connection conn = null;
		try {
			//conn = Get Connection
			conn = getConnection();
			StringBuffer sql = new StringBuffer("");
			sql.append("DELETE ORIG_POLICY_VERSION ");
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
    public void setupPolicyVersion(OrigPolicyVersionDataM origPolicyVersionDataM){
        String versionName=origPolicyVersionDataM.getPolicyVersion();
        String updateBy=origPolicyVersionDataM.getUpdateBy();
        String createBy=origPolicyVersionDataM.getUpdateBy();
        if(origPolicyVersionDataM.getCreateBy()==null){
            origPolicyVersionDataM.setCreateBy(createBy);                        
        }
        Vector vOrigPolicyVersionLevel=origPolicyVersionDataM.getVPolicyVersionLevel();
        if(vOrigPolicyVersionLevel!=null){
          for(int i=0;i<vOrigPolicyVersionLevel.size();i++){
              OrigPolicyVersionLevelDataM origPolicyVersionLevelDataM= (OrigPolicyVersionLevelDataM)vOrigPolicyVersionLevel.get(i);
              origPolicyVersionLevelDataM.setPolicyVersion(versionName);
             if(origPolicyVersionLevelDataM.getCreateBy()==null){
                 origPolicyVersionLevelDataM.setCreateBy(createBy);
                 origPolicyVersionLevelDataM.setCreateDate(new Timestamp(Calendar.getInstance().getTimeInMillis()));
             }
             origPolicyVersionLevelDataM.setUpdateBy(updateBy);
          }    
        }
        Vector vOrigPolicyVersionGroup=origPolicyVersionDataM.getVPolicyVesionGroup();
       
        if (vOrigPolicyVersionGroup != null) {            
            for (int i = 0; i < vOrigPolicyVersionGroup.size(); i++) {
                OrigPolicyLevelGroupDataM  origPolicyLevelGroupDataM = (OrigPolicyLevelGroupDataM) vOrigPolicyVersionGroup.get(i);
                origPolicyLevelGroupDataM.setPolicyVersion(versionName);
                if(origPolicyLevelGroupDataM.getCreateBy()==null){
                    origPolicyLevelGroupDataM.setCreateBy(createBy);
                    origPolicyLevelGroupDataM.setCreateDate(new Timestamp(Calendar.getInstance().getTimeInMillis()));
                }
                origPolicyLevelGroupDataM.setUpdateBy(updateBy);
            }
        }
        Vector vOrigPolicyMapRules=origPolicyVersionDataM.getVPolicyMapRules();          
         if (vOrigPolicyMapRules != null) {
             for (int i = 0; i < vOrigPolicyMapRules.size(); i++) {
                 OrigPolicyLevelMapDataM  origPolicyMapRulesDataM = (OrigPolicyLevelMapDataM) vOrigPolicyMapRules.get(i);
                 origPolicyMapRulesDataM.setPolicyVersion(versionName);
                 if(origPolicyMapRulesDataM.getCreateBy()==null){
                     origPolicyMapRulesDataM.setCreateBy(createBy);
                     origPolicyMapRulesDataM.setCreateDate(new Timestamp(Calendar.getInstance().getTimeInMillis())); 
                 }
                 origPolicyMapRulesDataM.setUpdateBy(updateBy);
             }
         }
        Vector vOrigPolicyApprovalDetail=origPolicyVersionDataM.getVPolicyApprovalDetails();                  
        if (vOrigPolicyApprovalDetail != null) {
            for (int i = 0; i < vOrigPolicyApprovalDetail.size(); i++) {
                ApprovAuthorM  origvApprovAuthorM = (ApprovAuthorM) vOrigPolicyApprovalDetail.get(i);
                origvApprovAuthorM.setPolicyVersion(versionName);
                if(origvApprovAuthorM.getCreateBy()==null){
                    origvApprovAuthorM.setCreateBy(createBy);
                    origvApprovAuthorM.setCreateDate(new Timestamp(Calendar.getInstance().getTimeInMillis()));
                }
                origvApprovAuthorM.setUpdateBy(updateBy);
            }
        }
        //Exception
        Vector vOrigPolicyRulesException=origPolicyVersionDataM.getVPolicyRulesException();          
        if (vOrigPolicyRulesException != null) {
            for (int i = 0; i < vOrigPolicyRulesException.size(); i++) {
                OrigPolicyRulesExceptionDataM  origPolicyRulesExceptionDataM = (OrigPolicyRulesExceptionDataM) vOrigPolicyRulesException.get(i);
                origPolicyRulesExceptionDataM.setPolicyVersion(versionName);
                if(origPolicyRulesExceptionDataM.getCreateBy()==null){
                    origPolicyRulesExceptionDataM.setCreateBy(createBy);
                    origPolicyRulesExceptionDataM.setCreateDate(new Timestamp(Calendar.getInstance().getTimeInMillis())); 
                }
                origPolicyRulesExceptionDataM.setUpdateBy(updateBy);
            }
        }
        //Group total
        Vector vOrigPolicyGroupTotal=origPolicyVersionDataM.getVPolicyVesionGroupTotal();  
        if (vOrigPolicyGroupTotal != null) {            
            for (int i = 0; i < vOrigPolicyGroupTotal.size(); i++) {
                OrigPolicyLevelGroupTotalDataM  origPolicyLevelGroupTotalDataM = (OrigPolicyLevelGroupTotalDataM) vOrigPolicyGroupTotal.get(i);
                origPolicyLevelGroupTotalDataM.setPolicyVersion(versionName);
                if(origPolicyLevelGroupTotalDataM.getCreateBy()==null){
                    origPolicyLevelGroupTotalDataM.setCreateBy(createBy);
                    origPolicyLevelGroupTotalDataM.setCreateDate(new Timestamp(Calendar.getInstance().getTimeInMillis()));
                }
                origPolicyLevelGroupTotalDataM.setUpdateBy(updateBy);
            }
        }
        
    }
}
