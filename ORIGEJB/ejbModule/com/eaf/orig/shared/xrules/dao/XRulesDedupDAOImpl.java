/*
 * Created on Oct 8, 2007
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
package com.eaf.orig.shared.xrules.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.Vector;

import org.apache.log4j.Logger;

import com.eaf.orig.shared.dao.OrigObjectDAO;
import com.eaf.orig.shared.xrules.dao.exception.XRulesDedupException;
import com.eaf.xrules.shared.constant.XRulesConstant;
import com.eaf.xrules.shared.model.XRulesDedupDataM;

/**
 * @author Sankom Sanpunya
 *
 * Type: XRulesDedupDAOImpl
 */
public class XRulesDedupDAOImpl extends OrigObjectDAO implements XRulesDedupDAO {
	private static Logger log = Logger.getLogger(XRulesDedupDAOImpl.class );
	/**
	 * 
	 */
	public XRulesDedupDAOImpl() {
		super();
		
	}

	/* (non-Javadoc)
	 * @see com.eaf.orig.shared.xrules.dao.XRulesDedupDAO#createModelXRulesDedupM(com.eaf.xrules.shared.model.XRulesDedupDataM)
	 */
	public void createModelXRulesDedupM(XRulesDedupDataM prmXRulesDedupDataM)
			throws XRulesDedupException {
		try {
			createTableXRULES_DEDUP(prmXRulesDedupDataM);
		} catch (Exception e) {
			log.fatal("Error:",e);
			throw new XRulesDedupException(e.getMessage());
		}


	}

	/**
	 * @param prmXRulesDedupDataM
	 */
	private void createTableXRULES_DEDUP(XRulesDedupDataM prmXRulesDedupDataM) throws XRulesDedupException{
		PreparedStatement ps = null;
		Connection conn = null;
		try {
			//conn = Get Connection
			conn = getConnection();
			StringBuffer sql = new StringBuffer("");
			sql.append("INSERT INTO XRULES_DEDUP ");
			/*sql.append("( APPLICATION_RECORD_ID,CMPCDE,IDNO,APPLICATION_NO,CUSTOMER_TYPE");
			sql.append("  ,THTITLE,THNAME,THLASTNAME,ENTITLE,ENNAME");
			sql.append(" ,ENLASTNAME ,PERSONAL_TYPE,APPLICATION_STATUS,TOTAL_AMOUNT_FINANCED,UPDATE_DATE");
			sql.append(" ,UPDATE_BY,SEQ,DUP_CITIZEN_ID,OFFICE_CODE,MARKETING_CODE   )");
			sql.append(" VALUES(?,?,?,?,?  ,?,?,?,?,? ,?,?,?,?,SYSDATE  ,?,?,?,?,? ) ");*/
			sql.append("( PERSONAL_ID,APPLICATION_NO,CUSTOMER_TYPE");
			sql.append("  ,THTITLE,THNAME,THLASTNAME,ENTITLE,ENNAME");
			sql.append(" ,ENLASTNAME ,PERSONAL_TYPE,APPLICATION_STATUS,TOTAL_AMOUNT_FINANCED,UPDATE_DATE");
			sql.append(" ,UPDATE_BY,SEQ,DUP_CITIZEN_ID,OFFICE_CODE,MARKETING_CODE   )");
			sql.append(" VALUES(?,?,?  ,?,?,?,?,? ,?,?,?,?,SYSDATE  ,?,?,?,?,? ) ");
			String dSql = String.valueOf(sql);
			log.debug("Sql=" + dSql);
			ps = conn.prepareStatement(dSql);
			/*ps.setString(1, prmXRulesDedupDataM.getApplicationRecordId());
			ps.setString(2, prmXRulesDedupDataM.getCmpCde());			 
			ps.setString(3, prmXRulesDedupDataM.getIdNo());
			ps.setString(4, prmXRulesDedupDataM.getApplicationNo());
			ps.setString(5, prmXRulesDedupDataM.getCustomerType());
			ps.setString(6, prmXRulesDedupDataM.getThaiTitleName());
			ps.setString(7, prmXRulesDedupDataM.getThaiFirstName());
			ps.setString(8, prmXRulesDedupDataM.getThaiLastName());
			ps.setString(9, prmXRulesDedupDataM.getEngTitleName());
			ps.setString(10, prmXRulesDedupDataM.getEngFirstName());
			ps.setString(11, prmXRulesDedupDataM.getEngLastName());			 
			ps.setString(12, prmXRulesDedupDataM.getPersonalType());
			ps.setString(13, prmXRulesDedupDataM.getApplciationStatus());
			ps.setBigDecimal(14, prmXRulesDedupDataM.getTotalAmountFinanced());					
			ps.setString(15, prmXRulesDedupDataM.getUpdateBy());
			ps.setInt(16, prmXRulesDedupDataM.getSeq()); 
			ps.setString(17,prmXRulesDedupDataM.getDupCitizenId());
			ps.setString(18,prmXRulesDedupDataM.getOfficeCode());
			ps.setString(19,prmXRulesDedupDataM.getMktCode());*/
			
			ps.setString(1, prmXRulesDedupDataM.getPersonalID());
			ps.setString(2, prmXRulesDedupDataM.getApplicationNo());
			ps.setString(3, prmXRulesDedupDataM.getCustomerType());
			
			ps.setString(4, prmXRulesDedupDataM.getThaiTitleName());
			ps.setString(5, prmXRulesDedupDataM.getThaiFirstName());
			ps.setString(6, prmXRulesDedupDataM.getThaiLastName());
			ps.setString(7, prmXRulesDedupDataM.getEngTitleName());
			ps.setString(8, prmXRulesDedupDataM.getEngFirstName());
			
			ps.setString(9, prmXRulesDedupDataM.getEngLastName());			 
			ps.setString(10, prmXRulesDedupDataM.getPersonalType());
			ps.setString(11, prmXRulesDedupDataM.getApplicationStatus());
			ps.setBigDecimal(12, prmXRulesDedupDataM.getTotalAmountFinanced());					
			ps.setString(13, prmXRulesDedupDataM.getUpdateBy());
			
			ps.setInt(14, prmXRulesDedupDataM.getSeq()); 
			ps.setString(15,prmXRulesDedupDataM.getDupCitizenId());
			ps.setString(16,prmXRulesDedupDataM.getOfficeCode());
			ps.setString(17,prmXRulesDedupDataM.getMktCode());
			ps.executeUpdate();
		} catch (Exception e) {
			log.fatal("Error:",e);
			throw new XRulesDedupException(e.getMessage());
		} finally {
			try {
				closeConnection(conn, ps);
			} catch (Exception e) {
				log.fatal("Error:",e);
			}
		}
		
	}

	/* (non-Javadoc)
	 * @see com.eaf.orig.shared.xrules.dao.XRulesDedupDAO#deleteModelXRulesDedupM(com.eaf.xrules.shared.model.XRulesDedupDataM)
	 */
	public void deleteModelXRulesDedupM(XRulesDedupDataM prmXRulesDedupDataM)
			throws XRulesDedupException {
		try {
			deleteTableXRULES_DEDUP(prmXRulesDedupDataM);
		} catch (Exception e) {
			log.fatal("Error:",e);
			throw new XRulesDedupException(e.getMessage());
		}

	}

	/**
	 * @param XRulesDebBdDataM
	 */
	private void deleteTableXRULES_DEDUP(XRulesDedupDataM XRulesDebBdDataM)
			throws XRulesDedupException {
		PreparedStatement ps = null;
		Connection conn = null;
		try {
			//conn = Get Connection
			conn = getConnection();
			StringBuffer sql = new StringBuffer("");
			sql.append("DELETE XRULES_DEDUP ");
			//sql.append(" WHERE APPLICATION_RECORD_ID = ? AND CMPCDE=? AND IDNO=? AND SEQ=?");
			sql.append(" WHERE APPLICATION_RECORD_ID = ? AND PERSONAL_ID=? AND SEQ=?");
			String dSql = String.valueOf(sql);
			log.debug("Sql=" + dSql);
			ps = conn.prepareStatement(dSql);
			/*ps.setString(1, XRulesDebBdDataM.getApplicationRecordId());
			ps.setString(2, XRulesDebBdDataM.getCmpCde());
			ps.setString(3, XRulesDebBdDataM.getIdNo());
			ps.setInt(4, XRulesDebBdDataM.getSeq());*/
			ps.setString(1, XRulesDebBdDataM.getApplicationRecordId());
			ps.setString(2, XRulesDebBdDataM.getPersonalID());
			ps.setInt(3, XRulesDebBdDataM.getSeq());
			ps.executeUpdate();

		} catch (Exception e) {
			log.fatal("Error:",e);
			throw new XRulesDedupException(e.getMessage());
		} finally {
			try {
				closeConnection(conn, ps);
			} catch (Exception e) {
				log.fatal("Error:",e);
				throw new XRulesDedupException(e.getMessage());
			}
		}

	}

	/* (non-Javadoc)
	 * @see com.eaf.orig.shared.xrules.dao.XRulesDedupDAO#loadModelXRulesDedupM(java.lang.String)
	 */
	public Vector loadModelXRulesDedupM(String personalID)
			throws XRulesDedupException {
		try {
			Vector vResult = selectTableXRULES_DEDUP(personalID);
			return vResult;
		} catch (Exception e) {
			log.fatal("Error:",e);
			throw new XRulesDedupException(e.getMessage());
		}
	}

	/**
	 * @param applicationRecordId
	 * @return
	 */
	private Vector selectTableXRULES_DEDUP(String personalID)
			throws XRulesDedupException {
		PreparedStatement ps = null;
		ResultSet rs = null;
		Connection conn = null;
		try {
			//conn = Get Connection
			conn = getConnection();
			StringBuffer sql = new StringBuffer("");
			/*sql.append("SELECT  CMPCDE,IDNO,APPLICATION_NO,CUSTOMER_TYPE ");
			sql.append(" ,THTITLE,THNAME,THLASTNAME,ENTITLE,ENNAME");
			sql.append(" ,ENLASTNAME ,PERSONAL_TYPE,APPLICATION_STATUS,TOTAL_AMOUNT_FINANCED,UPDATE_DATE");
			sql.append(" ,UPDATE_BY,SEQ,DUP_CITIZEN_ID,OFFICE_CODE,MARKETING_CODE ");
			sql.append("FROM XRULES_DEDUP WHERE APPLICATION_RECORD_ID = ? AND CMPCDE=? AND IDNO =?");*/
			sql.append("SELECT  PERSONAL_ID,APPLICATION_NO,CUSTOMER_TYPE ");
			sql.append(" ,THTITLE,THNAME,THLASTNAME,ENTITLE,ENNAME");
			sql.append(" ,ENLASTNAME ,PERSONAL_TYPE,APPLICATION_STATUS,TOTAL_AMOUNT_FINANCED,UPDATE_DATE");
			sql.append(" ,UPDATE_BY,SEQ,DUP_CITIZEN_ID,OFFICE_CODE,MARKETING_CODE ");
			sql.append("FROM XRULES_DEDUP WHERE PERSONAL_ID=?");
			String dSql = String.valueOf(sql);
			log.debug("Sql=" + dSql);
			ps = conn.prepareStatement(dSql);
			rs = null;
			ps.setString(1, personalID);
			rs = ps.executeQuery();
			Vector vt = new Vector();
			while (rs.next()) {
				XRulesDedupDataM prmXRulesDedupDataM = new XRulesDedupDataM();
				/*prmXRulesDedupDataM.setApplicationRecordId(applicationRecordId);
				prmXRulesDedupDataM.setCmpCde(rs.getString(1));
				prmXRulesDedupDataM.setIdNo(rs.getString(2));
				prmXRulesDedupDataM.setApplicationNo(rs.getString(3));
				prmXRulesDedupDataM.setCustomerType(rs.getString(4));
				prmXRulesDedupDataM.setThaiTitleName(rs.getString(5));
				prmXRulesDedupDataM.setThaiFirstName(rs.getString(6));
				prmXRulesDedupDataM.setThaiLastName(rs.getString(7));
				prmXRulesDedupDataM.setEngTitleName(rs.getString(8));
				prmXRulesDedupDataM.setEngFirstName(rs.getString(9));
				prmXRulesDedupDataM.setEngLastName(rs.getString(10));				
				//prmXRulesDedupDataM.setCitizenId(rs.getString(11));
				prmXRulesDedupDataM.setPersonalType(rs.getString(11));
				prmXRulesDedupDataM.setApplciationStatus(rs.getString(12));
				prmXRulesDedupDataM.setTotalAmountFinanced(rs.getBigDecimal(13));
				prmXRulesDedupDataM.setUpdateDate(rs.getTimestamp(14));
				prmXRulesDedupDataM.setUpdateBy(rs.getString(15));
				prmXRulesDedupDataM.setSeq(rs.getInt(16));
				prmXRulesDedupDataM.setDupCitizenId(rs.getString(17));
				prmXRulesDedupDataM.setOfficeCode(rs.getString(18));
				prmXRulesDedupDataM.setMktCode(rs.getString(19));*/
				prmXRulesDedupDataM.setPersonalID(rs.getString(1));
				prmXRulesDedupDataM.setApplicationNo(rs.getString(2));
				prmXRulesDedupDataM.setCustomerType(rs.getString(3));
				prmXRulesDedupDataM.setThaiTitleName(rs.getString(4));
				prmXRulesDedupDataM.setThaiFirstName(rs.getString(5));
				prmXRulesDedupDataM.setThaiLastName(rs.getString(6));
				prmXRulesDedupDataM.setEngTitleName(rs.getString(7));
				prmXRulesDedupDataM.setEngFirstName(rs.getString(8));
				prmXRulesDedupDataM.setEngLastName(rs.getString(9));				
				//prmXRulesDedupDataM.setCitizenId(rs.getString(11));
				prmXRulesDedupDataM.setPersonalType(rs.getString(10));
				prmXRulesDedupDataM.setApplicationStatus(rs.getString(11));
				prmXRulesDedupDataM.setTotalAmountFinanced(rs.getBigDecimal(12));
				prmXRulesDedupDataM.setUpdateDate(rs.getTimestamp(13));
				prmXRulesDedupDataM.setUpdateBy(rs.getString(14));
				prmXRulesDedupDataM.setSeq(rs.getInt(15));
				prmXRulesDedupDataM.setDupCitizenId(rs.getString(16));
				prmXRulesDedupDataM.setOfficeCode(rs.getString(17));
				prmXRulesDedupDataM.setMktCode(rs.getString(18));
				vt.add(prmXRulesDedupDataM);
			}
			return vt;
		} catch (Exception e) {
			log.fatal("Error:",e);
			throw new XRulesDedupException(e.getMessage());
		} finally {
			try {
				closeConnection(conn, rs, ps);
			} catch (Exception e) {
				log.fatal("Error:",e);
			}
		}
	}
	/* (non-Javadoc)
	 * @see com.eaf.orig.shared.xrules.dao.XRulesDedupDAO#saveUpdateModelXRulesDedupM(com.eaf.xrules.shared.model.XRulesDedupDataM)
	 */
	public void saveUpdateModelXRulesDedupM(XRulesDedupDataM prmXRulesDedupDataM)
			throws XRulesDedupException {
		double returnRows = 0;
		returnRows = updateTableXRULES_DEDUP(prmXRulesDedupDataM);
		if (returnRows == 0) {
			log.debug("New record then can't update record in table XRULES_DEDUP then call Insert method");
			createTableXRULES_DEDUP(prmXRulesDedupDataM);
		}

	}
	/**
	 * @param prmXRulesDebBdDataM
	 * @return
	 */
	private double updateTableXRULES_DEDUP(XRulesDedupDataM prmXRulesDedupDataM)
			throws XRulesDedupException {
		double returnRows = 0;
		PreparedStatement ps = null;
		Connection conn = null;
		try {
			//conn = Get Connection
			conn = getConnection();
			StringBuffer sql = new StringBuffer("");
			/*sql.append("UPDATE XRULES_DEDUP ");
			sql.append(" SET APPLICATION_NO=?,CUSTOMER_TYPE=?");
			sql.append(" ,THTITLE=?,THNAME=?,THLASTNAME=?,ENTITLE=?,ENNAME=?");
			sql.append(" ,ENLASTNAME=? ,PERSONAL_TYPE=?,APPLICATION_STATUS=?,TOTAL_AMOUNT_FINANCED=?,UPDATE_DATE=SYSDATE ");
			sql.append(" ,UPDATE_BY=?,DUP_CITIZEN_ID=?,OFFICE_CODE=?,MARKETING_CODE=? ");
			sql.append(" WHERE APPLICATION_RECORD_ID = ? AND CMPCDE=? AND IDNO=? AND SEQ=?");*/
			sql.append("UPDATE XRULES_DEDUP ");
			sql.append(" SET APPLICATION_NO=?,CUSTOMER_TYPE=?");
			sql.append(" ,THTITLE=?,THNAME=?,THLASTNAME=?,ENTITLE=?,ENNAME=?");
			sql.append(" ,ENLASTNAME=? ,PERSONAL_TYPE=?,APPLICATION_STATUS=?,TOTAL_AMOUNT_FINANCED=?,UPDATE_DATE=SYSDATE ");
			sql.append(" ,UPDATE_BY=?,DUP_CITIZEN_ID=?,OFFICE_CODE=?,MARKETING_CODE=? ");
			sql.append(" WHERE PERSONAL_ID=? AND SEQ=?");
			String dSql = String.valueOf(sql);
			log.debug("Sql=" + dSql);
			ps = conn.prepareStatement(dSql);			
			/*ps.setString(1, prmXRulesDedupDataM.getApplicationNo());
			ps.setString(2, prmXRulesDedupDataM.getCustomerType());
			ps.setString(3, prmXRulesDedupDataM.getThaiTitleName());
			ps.setString(4, prmXRulesDedupDataM.getThaiFirstName());
			ps.setString(5, prmXRulesDedupDataM.getThaiLastName());
			ps.setString(6, prmXRulesDedupDataM.getEngTitleName());
			ps.setString(7, prmXRulesDedupDataM.getEngFirstName());
			ps.setString(8, prmXRulesDedupDataM.getEngLastName());			 
			ps.setString(9, prmXRulesDedupDataM.getPersonalType());
			ps.setString(10, prmXRulesDedupDataM.getApplciationStatus());
			ps.setBigDecimal(11, prmXRulesDedupDataM.getTotalAmountFinanced());			 
			ps.setString(12, prmXRulesDedupDataM.getUpdateBy());
			ps.setString(13, prmXRulesDedupDataM.getDupCitizenId()); 
			ps.setString(14, prmXRulesDedupDataM.getOfficeCode()); 
			ps.setString(15, prmXRulesDedupDataM.getMktCode());
			ps.setString(16, prmXRulesDedupDataM.getApplicationRecordId());
			ps.setString(17, prmXRulesDedupDataM.getCmpCde());			 
			ps.setString(18, prmXRulesDedupDataM.getIdNo());
			ps.setInt(19, prmXRulesDedupDataM.getSeq());*/
			ps.setString(1, prmXRulesDedupDataM.getApplicationNo());
			ps.setString(2, prmXRulesDedupDataM.getCustomerType());
			ps.setString(3, prmXRulesDedupDataM.getThaiTitleName());
			ps.setString(4, prmXRulesDedupDataM.getThaiFirstName());
			ps.setString(5, prmXRulesDedupDataM.getThaiLastName());
			ps.setString(6, prmXRulesDedupDataM.getEngTitleName());
			ps.setString(7, prmXRulesDedupDataM.getEngFirstName());
			ps.setString(8, prmXRulesDedupDataM.getEngLastName());			 
			ps.setString(9, prmXRulesDedupDataM.getPersonalType());
			ps.setString(10, prmXRulesDedupDataM.getApplicationStatus());
			ps.setBigDecimal(11, prmXRulesDedupDataM.getTotalAmountFinanced());			 
			ps.setString(12, prmXRulesDedupDataM.getUpdateBy());
			ps.setString(13, prmXRulesDedupDataM.getDupCitizenId()); 
			ps.setString(14, prmXRulesDedupDataM.getOfficeCode()); 
			ps.setString(15, prmXRulesDedupDataM.getMktCode());
			ps.setString(16, prmXRulesDedupDataM.getPersonalID());
			ps.setInt(17, prmXRulesDedupDataM.getSeq());
			returnRows = ps.executeUpdate();
		} catch (Exception e) {
			log.fatal("Error:",e);
			throw new XRulesDedupException(e.getMessage());
		} finally {
			try {
				closeConnection(conn, ps);
			} catch (Exception e) {
				log.fatal("Error:",e);
			}
		}
		return returnRows;
	}

	/* (non-Javadoc)
	 * @see com.eaf.orig.shared.xrules.dao.XRulesDedupDAO#saveUpdateModelXRulesDedupM(java.util.Vector)
	 */
	public void saveUpdateModelXRulesDedupM(Vector vXRulesDedupDataM) throws XRulesDedupException {

		try {
			//update data
			if (vXRulesDedupDataM != null) {
				for (int i = 0; i < vXRulesDedupDataM.size()&&i<900; i++) {
					this.saveUpdateModelXRulesDedupM((XRulesDedupDataM) vXRulesDedupDataM.get(i));
				}
			}
			//delete table
			deleteSomeTableXRULES_DEDUP(vXRulesDedupDataM);
		} catch (Exception e) {
			log.fatal("Error:",e);
			throw new XRulesDedupException(e.getMessage());
		}

		
	}
	/**
	 * @param vXRulesDedupDataM
	 */
	private void deleteSomeTableXRULES_DEDUP(Vector vXRulesDedupDataM)
			throws XRulesDedupException {
		PreparedStatement ps = null;
		Connection conn = null;
		try {
			//conn = Get Connection
			if (vXRulesDedupDataM == null) {
				log.debug("XRulesBlacklistDAOImpl-->deleteSomeTableXRULES_DEDUP vXRulesDedupDataM=null");
				return;
			}
			if (vXRulesDedupDataM.size() < 1) {
				log.debug("XRulesBlacklistDAOImpl-->deleteSomeTableXRULES_DEDUP vXRulesDedupDataM=0");
				return;
			}
			conn = getConnection();
			StringBuffer sql = new StringBuffer("");
			sql.append("DELETE XRULES_DEDUP ");
			//sql.append(" WHERE APPLICATION_RECORD_ID = ? AND CMPCDE=? AND IDNO=? ");
			sql.append(" WHERE PERSONAL_ID=? ");
			sql.append(" AND SEQ not in(");
			XRulesDedupDataM prmXRulesDebtDbDataM;
			prmXRulesDebtDbDataM = (XRulesDedupDataM) vXRulesDedupDataM.get(0);
			sql.append(prmXRulesDebtDbDataM.getSeq());
			for (int i = 1; i < vXRulesDedupDataM.size()&&i<XRulesConstant.limitDeleteSQLParam; i++) {
				prmXRulesDebtDbDataM = (XRulesDedupDataM) vXRulesDedupDataM.get(i);
				sql.append("," + prmXRulesDebtDbDataM.getSeq());
			}
			sql.append(" )");
			String dSql = String.valueOf(sql);
			log.debug("Sql=" + dSql);
			ps = conn.prepareStatement(dSql);
			/*ps.setString(1, prmXRulesDebtDbDataM.getApplicationRecordId());
			ps.setString(2, prmXRulesDebtDbDataM.getCmpCde());
			ps.setString(3, prmXRulesDebtDbDataM.getIdNo());*/
			
			ps.setString(1, prmXRulesDebtDbDataM.getPersonalID());
			ps.executeUpdate();

		} catch (Exception e) {
			log.fatal("Error:",e);
			throw new XRulesDedupException(e.getMessage());
		} finally {
			try {
				closeConnection(conn, ps);
			} catch (Exception e) {
				log.fatal("Error:",e);
				throw new XRulesDedupException(e.getMessage());
			}
		}

	}

    /* (non-Javadoc)
     * @see com.eaf.orig.shared.xrules.dao.XRulesDedupDAO#deleteModelXRulesDedupM(java.lang.String, java.lang.String, java.util.Vector)
     */
    public void deleteModelXRulesDedupM(String applicationRecordId, String cmpCde, Vector idNoVects) throws XRulesDedupException {
        PreparedStatement ps = null;
        Connection conn = null;
		try {
			//conn = Get Connection
			if (idNoVects == null) {
				log
						.debug("XRulesDedupDAOImpl-->deleteModelXRulesDedupM  idno Vects=null");
				return;
			}
			if (idNoVects.size() < 1) {
				log
						.debug("XRulesDedupDAOImpl-->deleteModelXRulesDedupM  Idno size =0");
				return;
			}
			conn = getConnection();
			StringBuffer sql = new StringBuffer("");
			sql.append("DELETE XRULES_DEDUP ");
			sql.append(" WHERE APPLICATION_RECORD_ID = ? AND CMPCDE=?   ");
			sql.append(" AND IDNO not in('");           
            sql.append((String) idNoVects.get(0));
            sql.append("'");
            for (int i = 1; i < idNoVects.size() && i < XRulesConstant.limitDeleteSQLParam; i++) {
                String idNo = (String) idNoVects.get(i);
                sql.append("," + "'" +idNo+"'");
            }
			sql.append(" )");
			String dSql = String.valueOf(sql);
			log.debug("Sql=" + dSql);
			ps = conn.prepareStatement(dSql);
			ps.setString(1, applicationRecordId);
			ps.setString(2, cmpCde);			
			ps.executeUpdate();

		} catch (Exception e) {
			log.fatal("Error:",e);
			throw new XRulesDedupException(e.getMessage());
		} finally {
			try {
				closeConnection(conn, ps);
			} catch (Exception e) {
				log.fatal("Error:",e);
				throw new XRulesDedupException(e.getMessage());
			}
		}

    }
}
