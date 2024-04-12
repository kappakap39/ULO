/*
 * Created on Nov 25, 2007
 * Created by Prawit Limwattanachai
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

import com.eaf.orig.master.shared.model.PolicyRulesDataM;
import com.eaf.orig.shared.dao.exceptions.OrigApplicationMException;

/**
 * @author Administrator
 *
 * Type: OrigMasterRunParamDAOImpl
 */
public class OrigMasterPolicyRulesDAOImpl extends OrigObjectDAO implements
		OrigMasterPolicyRulesDAO {
	Logger log = Logger.getLogger(OrigMasterPolicyRulesDAOImpl.class);
	
	/* (non-Javadoc)
	 * @see com.eaf.orig.shared.dao.OrigMasterRunParamDAO#updateOrigMasterRunParamM(com.eaf.orig.master.shared.model.RunningParamM)
	 */
	 
    private double updateTableOrigMasterPolicyRules(PolicyRulesDataM policyRulesDataM)throws OrigApplicationMException{
    	double returnRows = 0;
		PreparedStatement ps = null;
		Connection conn = null;
		try {
			//conn = Get Connection
			conn = getConnection();
			StringBuffer sql = new StringBuffer("");
			sql.append("UPDATE POLICY_RULES ");
			
			sql.append(" SET   POLICY_TYPE=?, DESCRIPTION=?, UPDATE_BY=?, UPDATE_DATE=SYSDATE ");
			
			sql.append(" WHERE POLICY_CODE = ?  ");
			
			String dSql = String.valueOf(sql);
			log.debug("Sql="+dSql);
			ps = conn.prepareStatement(dSql);
			ps.setString(1, policyRulesDataM.getPolicyType());
			ps.setString(2, policyRulesDataM.getDesciption());
			ps.setString(3, policyRulesDataM.getUpdateBy());
			ps.setString(4, policyRulesDataM.getPolicyCode());			 			
			returnRows = ps.executeUpdate();
		} catch (Exception e) {
			log.fatal("",e);
			throw new OrigApplicationMException(e.getMessage());
		} finally {
			try {
				closeConnection(conn, ps);
			} catch (Exception e) {
				log.fatal("",e);
			}
		}
		return returnRows;
        
    }
    private void InsertTableOrigMasterPolicyRules(PolicyRulesDataM policyRulesDataM)throws OrigApplicationMException{
        PreparedStatement ps = null;
        Connection conn = null;
		try{
			//conn = Get Connection
			conn = getConnection();
			StringBuffer sql = new StringBuffer("");
			sql.append(" INSERT INTO POLICY_RULES ");
			sql.append(" ( POLICY_CODE,POLICY_TYPE,DESCRIPTION,CREATE_DATE,CREATE_BY,UPDATE_BY,UPDATE_DATE) ");
			sql.append(" VALUES (?,?,?,SYSDATE,?,?,SYSDATE)");
			String dSql = String.valueOf( sql);
			log.debug("dSql="+dSql);
			ps = conn.prepareStatement(dSql);			
			ps.setString(1, policyRulesDataM.getPolicyCode());
			ps.setString(2, policyRulesDataM.getPolicyType());
			ps.setString(3,policyRulesDataM.getDesciption());
			ps.setString(4,policyRulesDataM.getCreateBy());
			ps.setString(5,policyRulesDataM.getUpdateBy());			 
			ps.executeUpdate();
			ps.close();
		} catch (Exception e) {
			log.fatal("Insert Error",e);
			throw new OrigApplicationMException(e.getMessage());
		} finally {
			try {
				closeConnection(conn, ps);
			} catch (Exception e) {
				log.fatal("",e);
			}
		}
        
    }
	public PolicyRulesDataM selectOrigMasterPolicyRulesDataM(String policyCode,
			String policyType) throws OrigApplicationMException {
		PolicyRulesDataM policyRulesDataM = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		Connection conn = null;
		try {
			//conn = Get Connection
			conn = getConnection();
			StringBuffer sql = new StringBuffer("");
			
			sql.append(" SELECT POLICY_CODE,POLICY_TYPE,DESCRIPTION  ");
			sql.append(" FROM POLICY_RULES  WHERE POLICY_CODE = ?  ");
			
			String dSql = String.valueOf(sql);
			log.debug("Sql=" + dSql);
			ps = conn.prepareStatement(dSql);
			rs = null;
			ps.setString(1, policyCode);
			//ps.setString(2, runPrmType);
			rs = ps.executeQuery();

			if(rs.next()) {
			    policyRulesDataM = new PolicyRulesDataM();
			    policyRulesDataM.setPolicyCode(rs.getString(1));
			    policyRulesDataM.setPolicyType(rs.getString(2));
			    policyRulesDataM.setDesciption(rs.getString(3));				 
			}			
			return policyRulesDataM;

		} catch (Exception e) {
			log.fatal("",e);
			throw new OrigApplicationMException(e.getMessage());
		} finally {
			try {
				closeConnection(conn, rs, ps);
			} catch (Exception e) {
				log.fatal("",e);
			}
		}
	}
 
    /**
     * @param string
     */
    private void deleteOrigMasterPolicyRulesDataM(PolicyRulesDataM policyRulesDataM)throws OrigApplicationMException {
    	PreparedStatement ps = null;
    	Connection conn = null;
		try {
				//conn = Get Connection
				conn = getConnection();
				StringBuffer sql = new StringBuffer("");
				sql.append("DELETE POLICY_RULES ");
				sql.append(" WHERE  POLICY_CODE =?    ");
				String dSql = String.valueOf(sql);
				log.debug("Sql=" + dSql);				 
				ps = conn.prepareStatement(dSql);
				ps.setString(1,policyRulesDataM.getPolicyCode());				 
				ps.executeUpdate(); 
		
		} catch (Exception e) {
			log.fatal(e.getStackTrace());
			throw new   OrigApplicationMException(e.getMessage());
		} finally {
			try {
				closeConnection(conn, ps);
			} catch (Exception e) {
				log.fatal(e.getStackTrace());
				throw new OrigApplicationMException(e.getMessage());
			}
		}
        
    }
	//===========================
     
    /* (non-Javadoc)
     * @see com.eaf.orig.shared.dao.OrigMasterPolicyRulesDAO#updateOrigMasterPolicyRulesDataM(com.eaf.orig.master.shared.model.PolicyRulesDataM)
     */
    public double updateOrigMasterPolicyRulesDataM(PolicyRulesDataM policyRulesDataM) throws OrigApplicationMException {
        double returnRows = updateTableOrigMasterPolicyRules(policyRulesDataM);
		if(returnRows==0){
		  log.debug(" Can't Update Insert new Record");
		   InsertTableOrigMasterPolicyRules(policyRulesDataM);
		}		
		  return returnRows;        
    }
    /* (non-Javadoc)
     * @see com.eaf.orig.shared.dao.OrigMasterPolicyRulesDAO#deleteOrigMasterPolicyRulesDataM(java.util.Vector)
     */
    public void deleteOrigMasterPolicyRulesDataM(Vector policyRulesToDelete) throws OrigApplicationMException {
        for(int i = 0; i < policyRulesToDelete.size(); i++){
			 deleteOrigMasterPolicyRulesDataM( (PolicyRulesDataM)policyRulesToDelete.get(i));
		}    
        
    }
    /* (non-Javadoc)
     * @see com.eaf.orig.shared.dao.OrigMasterPolicyRulesDAO#loadPolicyRules()
     */
    public Vector loadPolicyRules() throws OrigApplicationMException {
    	Connection conn = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		Vector result=new Vector();
		try {
			//conn = Get Connection
			conn = getConnection();
			StringBuffer sql = new StringBuffer("");
			
			sql.append(" SELECT POLICY_CODE,POLICY_TYPE,DESCRIPTION  ");
			//sql.append(" FROM POLICY_RULES  WHERE POLICY_CODE = ?  ");
			sql.append(" FROM POLICY_RULES  ORDER BY POLICY_CODE ");
			String dSql = String.valueOf(sql);
			log.debug("Sql=" + dSql);
			ps = conn.prepareStatement(dSql);
			rs = null;
		//	ps.setString(1, policyCode);
			//ps.setString(2, runPrmType);
			rs = ps.executeQuery();

			while(rs.next()) {
			    PolicyRulesDataM policyRulesDataM =new PolicyRulesDataM();			     
			    policyRulesDataM.setPolicyCode(rs.getString(1));
			    policyRulesDataM.setPolicyType(rs.getString(2));
			    policyRulesDataM.setDesciption(rs.getString(3));	
			    result.add(policyRulesDataM);
			}			
			return result;

		} catch (Exception e) {
			log.fatal("",e);
			throw new OrigApplicationMException(e.getMessage());
		} finally {
			try {
				closeConnection(conn, rs, ps);
			} catch (Exception e) {
				log.fatal("",e);
			}
		}
    }
    

}
