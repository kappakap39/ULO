/*
 * Created on Nov 28, 2007
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

import java.math.BigDecimal;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.Vector;

import org.apache.log4j.Logger;

import com.eaf.orig.master.shared.model.ApprovAuthorM;
import com.eaf.orig.shared.constant.OrigConstant;
import com.eaf.orig.shared.dao.exceptions.OrigApplicationMException;

/**
 * @author Administrator
 *
 * Type: OrigMasterApprovAuthorDAOImpl
 */
public class OrigMasterApprovAuthorDAOImpl extends OrigObjectDAO implements
		OrigMasterApprovAuthorDAO {
	Logger log = Logger.getLogger(OrigMasterApprovAuthorDAOImpl.class);
	
	/* (non-Javadoc)
	 * @see com.eaf.orig.shared.dao.OrigMasterApprovAuthorDAO#hvColumn(java.lang.String, java.lang.String, java.lang.String)
	 */
	public boolean hvColumn(String grpNm, String lnTyp, String cusTyp)
			throws OrigApplicationMException {
		PreparedStatement ps = null;
		ResultSet rs = null;
		Connection conn = null;
		try {
			//conn = Get Connection
			conn = getConnection();
			StringBuffer sql = new StringBuffer("");
			
			sql.append(" SELECT *");
			sql.append(" FROM APPROVAL_AUTHORITY  WHERE GROUP_NAME = ? AND LOAN_TYPE = ? AND CUSTOMER_TYPE = ? ");
			
			String dSql = String.valueOf(sql);
			log.debug("Sql=" + dSql);
			ps = conn.prepareStatement(dSql);
			rs = null;
			ps.setString(1, grpNm);
			ps.setString(2, lnTyp);
			ps.setString(3, cusTyp);

			rs = ps.executeQuery();
			if (rs.next()) {
				return true;
			}else{
				return false;
			}

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
	
	public void deleteOrigMasterApprovAuthorM(String[] appAuthorToDelete)
			throws OrigApplicationMException {
		String[] grpNm_LnTyp_CusTyp;
		//String[] busID;
		for(int i = 0; i < appAuthorToDelete.length; i++){
			grpNm_LnTyp_CusTyp = appAuthorToDelete[i].split(",");
			deleteApprovAuthorM(grpNm_LnTyp_CusTyp[0], grpNm_LnTyp_CusTyp[1], grpNm_LnTyp_CusTyp[2]);
		}

	}
	public void deleteApprovAuthorM(String grpNm, String lnTyp, String cusTyp)
			throws OrigApplicationMException {
		PreparedStatement ps = null;
		Connection conn = null;
		try {
				//conn = Get Connection
				conn = getConnection(); 
				StringBuffer sql = new StringBuffer("");
				sql.append("DELETE APPROVAL_AUTHORITY ");
				sql.append(" WHERE GROUP_NAME = ? AND LOAN_TYPE = ? AND CUSTOMER_TYPE = ? ");
				String dSql = String.valueOf(sql);
				log.debug("Sql=" + dSql);
				ps = conn.prepareStatement(dSql);
				ps.setString(1, grpNm);
				ps.setString(2, lnTyp);
				ps.setString(3, cusTyp);
				ps.executeUpdate(); 
		
		} catch (Exception e) {
			log.fatal(e.getStackTrace());
			throw new OrigApplicationMException(e.getMessage());
		} finally {
			try {
				closeConnection(conn, ps);
			} catch (Exception e) {
				log.fatal(e.getStackTrace());
				throw new OrigApplicationMException(e.getMessage());
			}
		}
	}
	
	public int updateOrigMasterApprovAuthor(ApprovAuthorM approvAuthorM)
			throws OrigApplicationMException {
	    int returnRows = 0;
		/* PreparedStatement ps = null;
		try {
			//conn = Get Connection
			conn = getConnection();
			StringBuffer sql = new StringBuffer("");
			sql.append("UPDATE APPROVAL_AUTHORITY ");
			
			sql.append(" SET GROUP_NAME=?, LOAN_TYPE=?, CREDIT_APPROVAL=?, MIN_DOWN_GUA=?, MIN_TERM_GUA=?, MAX_TERM_GUA=?, MIN_DOWN_NO_GUA=?, MIN_TERM_NO_GUA=?, MAX_TERM_NO_GUA=?, UPDATE_DATE=SYSDATE, UPDATE_BY=?, CUSTOMER_TYPE=? ");
			
			sql.append(" WHERE GROUP_NAME = ? AND LOAN_TYPE = ? AND CUSTOMER_TYPE = ? ");
			
			String dSql = String.valueOf(sql);
			log.debug("Sql="+dSql);
			ps = conn.prepareStatement(dSql);
			ps.setString(1, approvAuthorM.getGroupName());
			ps.setString(2, approvAuthorM.getLoanType());
			ps.setBigDecimal(3, approvAuthorM.getCreditApproval());
			ps.setBigDecimal(4, approvAuthorM.getMinDownGua());
			ps.setBigDecimal(5, approvAuthorM.getMinTermGua());
			ps.setBigDecimal(6, approvAuthorM.getMaxTermGua());
			ps.setBigDecimal(7, approvAuthorM.getMinDownNoGua());
			ps.setBigDecimal(8, approvAuthorM.getMinTermNoGua());
			ps.setBigDecimal(9, approvAuthorM.getMaxTermNoGua());
			ps.setString(10, approvAuthorM.getUpdateBy());
			
			ps.setString(15 ,approvAuthorM.getPolicyVersion());
			ps.setString(16,approvAuthorM.getCarType());
			ps.setString(17,approvAuthorM.getScoringResult());
			ps.setBigDecimal(19,approvAuthorM.getFailPolicyCreditApproval());
			ps.setBigDecimal(20, approvAuthorM.getFailPolicyMinDownGua());
			ps.setBigDecimal(21, approvAuthorM.getFailPolicyMinTermGua());
			ps.setBigDecimal(20, approvAuthorM.getFailPolicyMaxTermGua());
			ps.setBigDecimal(21, approvAuthorM.getFailPolicyMinDownNoGua());
			ps.setBigDecimal(22, approvAuthorM.getFailPolicyMinTermNoGua());
			ps.setBigDecimal(23, approvAuthorM.getFailPolicyMaxTermNoGua());
			
			ps.setString(11, approvAuthorM.getCustomerType());
			ps.setString(12, approvAuthorM.getGroupName());
			ps.setString(13, approvAuthorM.getLoanType());
			ps.setString(14, approvAuthorM.getCustomerType());
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
		}*/
		return returnRows;
	}
	
	/* (non-Javadoc)
	 * @see com.eaf.orig.shared.dao.OrigMasterApprovAuthorDAO#selectOrigMasterApprovAuthor(java.lang.String, java.lang.String, java.lang.String)
	 */
	public ApprovAuthorM selectOrigMasterApprovalAuthority(String groupName, String loanType,String carType,String custType,String scoringResult,String policyVersion,boolean policyNotFail,BigDecimal totalExposure, String appType,
            BigDecimal installmentTerm, BigDecimal downPayment) throws OrigApplicationMException {
		PreparedStatement ps = null;
		ResultSet rs = null;
		Connection conn = null;
	     logger.debug("Get escerate Group");
	        logger.debug("Policy Version " + policyVersion );
	        logger.debug(" Policy not Fail "+ policyNotFail);
	        logger.debug(" Customer Type "+custType);
	        logger.debug(" Car Type "+carType);
	        logger.debug(" Loan type "+ loanType);
	        logger.debug("scoringResult "+scoringResult);
	        logger.debug("totalExposure "+totalExposure);
	        logger.debug("appType "+appType);
	        logger.debug("installmentTerm "+installmentTerm);
	        logger.debug("downPayment "+downPayment);
		try {
			//conn = Get Connection
			conn = getConnection();
			StringBuffer sql = new StringBuffer("");
			
			sql.append(" SELECT distinct GROUP_NAME, LOAN_TYPE, CREDIT_APPROVAL, MIN_DOWN_GUA, MIN_TERM_GUA, MAX_TERM_GUA, MIN_DOWN_NO_GUA, MIN_TERM_NO_GUA, MAX_TERM_NO_GUA, CUSTOMER_TYPE ");
			sql.append("  ,POLICY_VERSION,CAR_TYPE,SCORING_RESULT,F_CREDIT_APPROVAL,F_MIN_DOWN_GUA    ");
			sql.append("  ,F_MIN_TERM_GUA,F_MAX_TERM_GUA,F_MIN_DOWN_NO_GUA,F_MIN_TERM_NO_GUA,F_MAX_TERM_NO_GUA  ");
			sql.append(" FROM APPROVAL_AUTHORITY  WHERE GROUP_NAME = ? AND LOAN_TYPE = ? AND CUSTOMER_TYPE = ? ");
			sql.append(" AND CAR_TYPE=? AND SCORING_RESULT =? AND POLICY_VERSION =? ");
			if(policyNotFail){
			    sql.append(" AND ( CREDIT_APPROVAL >= ?  OR  CREDIT_APPROVAL=0)");
			     if( OrigConstant.HAVE_GUARANTOR.equals(appType)){
			        sql.append(" AND ( ? >= MIN_DOWN_GUA OR MIN_DOWN_GUA=0 )");
			        sql.append(" AND  (? BETWEEN 	MIN_TERM_GUA AND MAX_TERM_GUA  OR MAX_TERM_GUA=0 )");
			        
			     }else{
			       sql.append(" AND ( ? >= MIN_DOWN_NO_GUA OR MIN_DOWN_NO_GUA=0 ) ");
				   sql.append(" AND (? BETWEEN 	MIN_TERM_NO_GUA AND MAX_TERM_NO_GUA  OR MAX_TERM_NO_GUA=0 )"); 
			     }
			
			}else{
			    sql.append(" and (F_CREDIT_APPROVAL >= ? OR F_CREDIT_APPROVAL=0 ) ");
			    if( OrigConstant.HAVE_GUARANTOR.equals(appType)){
			        sql.append(" AND (? >= F_MIN_DOWN_GUA OR F_MIN_DOWN_GUA =0)");
			        sql.append(" AND (? BETWEEN 	F_MIN_TERM_GUA AND F_MAX_TERM_GUA  OR F_MAX_TERM_GUA=0)");
			     }else{
			        sql.append(" AND (? >= F_MIN_DOWN_NO_GUA OR F_MIN_DOWN_NO_GUA=0) ");
					sql.append(" AND ( ? BETWEEN 	F_MIN_TERM_NO_GUA AND F_MAX_TERM_NO_GUA OR F_MAX_TERM_NO_GUA=0 )");
			     }			
			}
			String dSql = String.valueOf(sql);
			log.debug("Sql=" + dSql);
			ps = conn.prepareStatement(dSql);
			rs = null;
			ps.setString(1, groupName);
			ps.setString(2, loanType);
			ps.setString(3, custType);
            ps.setString(4,carType);
            ps.setString(5,scoringResult);
            ps.setString(6,policyVersion);
            ps.setBigDecimal(7,totalExposure);
            ps.setBigDecimal(8,downPayment);
            ps.setBigDecimal(9,installmentTerm);
			rs = ps.executeQuery();
			ApprovAuthorM approvAuthorM = null;

			if (rs.next()) {
				approvAuthorM = new ApprovAuthorM();
				approvAuthorM.setGroupName(rs.getString(1));
				approvAuthorM.setLoanType(rs.getString(2));
				approvAuthorM.setCreditApproval(rs.getBigDecimal(3));
				approvAuthorM.setMinDownGua(rs.getBigDecimal(4));
				approvAuthorM.setMinTermGua(rs.getBigDecimal(5));
				approvAuthorM.setMaxTermGua(rs.getBigDecimal(6));
				approvAuthorM.setMinDownNoGua(rs.getBigDecimal(7));
				approvAuthorM.setMinTermNoGua(rs.getBigDecimal(8));
				approvAuthorM.setMaxTermNoGua(rs.getBigDecimal(9));
				approvAuthorM.setCustomerType(rs.getString(10));
				
				approvAuthorM.setPolicyVersion(rs.getString(11));
				approvAuthorM.setCarType(rs.getString(12));
				approvAuthorM.setScoringResult(rs.getString(13));
				approvAuthorM.setFailPolicyCreditApproval(rs.getBigDecimal(14));			 
				approvAuthorM.setFailPolicyMinDownGua(rs.getBigDecimal(15 ));
				approvAuthorM.setFailPolicyMinTermGua(rs.getBigDecimal(16));
				approvAuthorM.setFailPolicyMaxTermGua(rs.getBigDecimal(17));
			    approvAuthorM.setFailPolicyMinDownNoGua(rs.getBigDecimal(18));
			    approvAuthorM.setFailPolicyMinTermNoGua(rs.getBigDecimal(19));
			    approvAuthorM.setFailPolicyMaxTermNoGua(rs.getBigDecimal(20 ));
				
			}
			return approvAuthorM;

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
	
	
	public void createModelOrigMasterApprovAuthorM(ApprovAuthorM approvAuthorM)
			throws OrigApplicationMException {
		PreparedStatement ps = null;
		Connection conn = null;
		try{
			//conn = Get Connection
			conn = getConnection();
			StringBuffer sql = new StringBuffer("");
			sql.append(" INSERT INTO APPROVAL_AUTHORITY ");
			sql.append(" (GROUP_NAME, LOAN_TYPE, CREDIT_APPROVAL, MIN_DOWN_GUA, MIN_TERM_GUA, MAX_TERM_GUA, MIN_DOWN_NO_GUA, MIN_TERM_NO_GUA, MAX_TERM_NO_GUA, CREATE_DATE, CREATE_BY, UPDATE_DATE, UPDATE_BY, CUSTOMER_TYPE ");
			sql.append("  ,POLICY_VERSION,CAR_TYPE,SCORING_RESULT,F_CREDIT_APPROVAL,F_MIN_DOWN_GUA    ");
			sql.append("  ,F_MIN_TERM_GUA,F_MAX_TERM_GUA,F_MIN_DOWN_NO_GUA,F_MIN_TERM_NO_GUA,F_MAX_TERM_NO_GUA  ");
			sql.append(" )");
			sql.append(" VALUES (?,?,?,?,?  ,?,?,?,?,?  ,?,SYSDATE,?,? ");
			sql.append(" ,?,?,?,?,?  ,?,?,?,?,? ");		
			sql.append(" )");
			String dSql = String.valueOf( sql);
			log.debug("dSql="+dSql);
			ps = conn.prepareStatement(dSql);
			
			ps.setString(1, approvAuthorM.getGroupName());
			ps.setString(2, approvAuthorM.getLoanType());
			ps.setBigDecimal(3, approvAuthorM.getCreditApproval());
			ps.setBigDecimal(4, approvAuthorM.getMinDownGua());
			ps.setBigDecimal(5, approvAuthorM.getMinTermGua());
			ps.setBigDecimal(6, approvAuthorM.getMaxTermGua());
			ps.setBigDecimal(7, approvAuthorM.getMinDownNoGua());
			ps.setBigDecimal(8, approvAuthorM.getMinTermNoGua());
			ps.setBigDecimal(9, approvAuthorM.getMaxTermNoGua());
			ps.setTimestamp(10, approvAuthorM.getCreateDate());
			ps.setString(11, approvAuthorM.getCreateBy());		
			ps.setString(12, approvAuthorM.getUpdateBy());
			ps.setString(13, approvAuthorM.getCustomerType());
			
			ps.setString(14 ,approvAuthorM.getPolicyVersion());
			ps.setString(15,approvAuthorM.getCarType());
			ps.setString(16,approvAuthorM.getScoringResult());
			ps.setBigDecimal(17,approvAuthorM.getFailPolicyCreditApproval());
			ps.setBigDecimal(18, approvAuthorM.getFailPolicyMinDownGua());
			ps.setBigDecimal(19, approvAuthorM.getFailPolicyMinTermGua());
			ps.setBigDecimal(20, approvAuthorM.getFailPolicyMaxTermGua());
			ps.setBigDecimal(21, approvAuthorM.getFailPolicyMinDownNoGua());
			ps.setBigDecimal(22, approvAuthorM.getFailPolicyMinTermNoGua());
			ps.setBigDecimal(23, approvAuthorM.getFailPolicyMaxTermNoGua());
			ps.executeUpdate();
			ps.close();
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

	}
	public Vector loadOrigMasterApprovalAuthority(String policyVersion)throws OrigApplicationMException{
	    PreparedStatement ps = null;
		ResultSet rs = null;
	     logger.debug("Get escerate Group");
	        logger.debug("Policy Version " + policyVersion );
	     Vector result=new Vector(); 
	     Connection conn = null;
		try {
			//conn = Get Connection
			conn = getConnection();
			StringBuffer sql = new StringBuffer("");
			
			sql.append(" SELECT distinct GROUP_NAME, LOAN_TYPE, CREDIT_APPROVAL, MIN_DOWN_GUA, MIN_TERM_GUA, MAX_TERM_GUA, MIN_DOWN_NO_GUA, MIN_TERM_NO_GUA, MAX_TERM_NO_GUA, CUSTOMER_TYPE ");
			sql.append("  ,POLICY_VERSION,CAR_TYPE,SCORING_RESULT,F_CREDIT_APPROVAL,F_MIN_DOWN_GUA    ");
			sql.append("  ,F_MIN_TERM_GUA,F_MAX_TERM_GUA,F_MIN_DOWN_NO_GUA,F_MIN_TERM_NO_GUA,F_MAX_TERM_NO_GUA  ");
			sql.append(" FROM APPROVAL_AUTHORITY  WHERE  ");
			sql.append("   POLICY_VERSION =? ");
			sql.append(" ORDER BY  GROUP_NAME,LOAN_TYPE,CUSTOMER_TYPE,CAR_TYPE,SCORING_RESULT ");
			String dSql = String.valueOf(sql);
			log.debug("Sql=" + dSql);
			ps = conn.prepareStatement(dSql);
			rs = null; 
            ps.setString(1,policyVersion);            
			rs = ps.executeQuery();
			ApprovAuthorM approvAuthorM = null;
			while (rs.next()) {
				approvAuthorM = new ApprovAuthorM();
				approvAuthorM.setGroupName(rs.getString(1));
				approvAuthorM.setLoanType(rs.getString(2));
				approvAuthorM.setCreditApproval(rs.getBigDecimal(3));
				approvAuthorM.setMinDownGua(rs.getBigDecimal(4));
				approvAuthorM.setMinTermGua(rs.getBigDecimal(5));
				approvAuthorM.setMaxTermGua(rs.getBigDecimal(6));
				approvAuthorM.setMinDownNoGua(rs.getBigDecimal(7));
				approvAuthorM.setMinTermNoGua(rs.getBigDecimal(8));
				approvAuthorM.setMaxTermNoGua(rs.getBigDecimal(9));
				approvAuthorM.setCustomerType(rs.getString(10));
				
				approvAuthorM.setPolicyVersion(rs.getString(11));
				approvAuthorM.setCarType(rs.getString(12));
				approvAuthorM.setScoringResult(rs.getString(13));
				approvAuthorM.setFailPolicyCreditApproval(rs.getBigDecimal(14));			 
				approvAuthorM.setFailPolicyMinDownGua(rs.getBigDecimal(15 ));
				approvAuthorM.setFailPolicyMinTermGua(rs.getBigDecimal(16));
				approvAuthorM.setFailPolicyMaxTermGua(rs.getBigDecimal(17));
			    approvAuthorM.setFailPolicyMinDownNoGua(rs.getBigDecimal(18));
			    approvAuthorM.setFailPolicyMinTermNoGua(rs.getBigDecimal(19));
			    approvAuthorM.setFailPolicyMaxTermNoGua(rs.getBigDecimal(20 ));
			    result.add(approvAuthorM);
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
	public void saveUpdateModelOrigMasterApprovalAuthority(ApprovAuthorM approvAuthorM) throws OrigApplicationMException{
	    int returnRows = 0;
		try {
			returnRows = updateOrigMasterApprovAuthor(approvAuthorM);			
			if (returnRows == 0) {
				log.debug("New record then can't update record in table APPROVAL_AUTHORITY then call Insert method");	
				createModelOrigMasterApprovAuthorM(approvAuthorM);
			}
		} catch (Exception e) {
			log.error("Error >> ", e);
			throw new OrigApplicationMException(e.getMessage());
		} 
	
	}

    /* (non-Javadoc)
     * @see com.eaf.orig.shared.dao.OrigMasterApprovAuthorDAO#deleteTableAPPROVAL_AUTHORITY(java.lang.String)
     */
    public void deleteTableAPPROVAL_AUTHORITY(String policyVersion) throws OrigApplicationMException {
    	PreparedStatement ps = null;
    	Connection conn = null;
		try {
				//conn = Get Connection
				conn = getConnection(); 
				StringBuffer sql = new StringBuffer("");
				sql.append("DELETE APPROVAL_AUTHORITY ");
				sql.append(" WHERE  POLICY_VERSION=? ");
				String dSql = String.valueOf(sql);
				log.debug("Sql=" + dSql);
				ps = conn.prepareStatement(dSql);
				ps.setString(1, policyVersion);
				 
				ps.executeUpdate(); 
		
		} catch (Exception e) {
			log.fatal(e.getStackTrace());
			throw new OrigApplicationMException(e.getMessage());
		} finally {
			try {
				closeConnection(conn, ps);
			} catch (Exception e) {
				log.fatal(e.getStackTrace());
				throw new OrigApplicationMException(e.getMessage());
			}
		}
    }

    
	
}
