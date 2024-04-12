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
import com.eaf.orig.shared.xrules.dao.exception.XRulesPolicyRulesException;
import com.eaf.xrules.shared.constant.XRulesConstant;
import com.eaf.xrules.shared.model.XRulesPolicyRulesDataM;

/**
 * @author Sankom Sanpunya
 * 
 * Type: XRulesPolicyRulesDAOImpl
 */
public class XRulesPolicyRulesDAOImpl extends OrigObjectDAO implements
		XRulesPolicyRulesDAO {
	private static Logger log = Logger
			.getLogger(XRulesPolicyRulesDAOImpl.class);

	/**
	 *  
	 */
	public XRulesPolicyRulesDAOImpl() {
		super();
		
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.eaf.orig.shared.xrules.dao.XRulesPolicyRulesDAO#createModelXRulesPolicyRulesM(com.eaf.xrules.shared.model.XRulesPolicyRulesDataM)
	 */
	public void createModelXRulesPolicyRulesM(
			XRulesPolicyRulesDataM prmXRulesPolicyRulesDataM)
			throws XRulesPolicyRulesException {
		try {
			createTableXRULES_POLICY_RULES(prmXRulesPolicyRulesDataM);
		} catch (Exception e) {
			log.fatal("Error:",e);
			throw new XRulesPolicyRulesException(e.getMessage());
		}

	}

	/**
	 * @param prmXRulesPolicyRulesDataM
	 */
	private void createTableXRULES_POLICY_RULES(
			XRulesPolicyRulesDataM prmXRulesPolicyRulesDataM)
			throws XRulesPolicyRulesException {
		PreparedStatement ps = null;
		Connection conn = null;
		try {
			//conn = Get Connection
			conn = getConnection();
			StringBuffer sql = new StringBuffer("");
			sql.append("INSERT INTO XRULES_POLICY_RULES ");
			/*sql.append("( APPLICATION_RECORD_ID,CMPCDE,IDNO,POLICY_CODE,POLICY_TYPE ");
			sql.append("  ,RESULT,UPDATE_DATE,UPDATE_BY )");
			sql.append(" VALUES(?,?,?,?,?  ,?,SYSDATE,? ) ");*/
			sql.append("( PERSONAL_ID,POLICY_CODE,POLICY_TYPE ");
			sql.append("  ,RESULT,UPDATE_DATE,UPDATE_BY )");
			sql.append(" VALUES(?,?,?  ,?,SYSDATE,? ) ");
			String dSql = String.valueOf(sql);
			log.debug("Sql=" + dSql);
			ps = conn.prepareStatement(dSql);
			/*ps.setString(1, prmXRulesPolicyRulesDataM.getApplicationRecordId());
			ps.setString(2, prmXRulesPolicyRulesDataM.getCmpCode());
			ps.setString(3, prmXRulesPolicyRulesDataM.getIdNo());
			ps.setString(4, prmXRulesPolicyRulesDataM.getPolicyCode());
			ps.setString(5, prmXRulesPolicyRulesDataM.getPolicyType());
			ps.setString(6, prmXRulesPolicyRulesDataM.getResult());
			ps.setString(7, prmXRulesPolicyRulesDataM.getUpdateBy());*/
			ps.setString(1, prmXRulesPolicyRulesDataM.getPersonalID());
			ps.setString(2, prmXRulesPolicyRulesDataM.getPolicyCode());
			ps.setString(3, prmXRulesPolicyRulesDataM.getPolicyType());
			ps.setString(4, prmXRulesPolicyRulesDataM.getResult());
			ps.setString(5, prmXRulesPolicyRulesDataM.getUpdateBy());
			ps.executeUpdate();
		} catch (Exception e) {
			log.fatal("Error:",e);
			throw new XRulesPolicyRulesException(e.getMessage());
		} finally {
			try {
				closeConnection(conn, ps);
			} catch (Exception e) {
				log.fatal("Error:",e);
			}
		}

	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.eaf.orig.shared.xrules.dao.XRulesPolicyRulesDAO#deleteModelXRulesPolicyRulesM(com.eaf.xrules.shared.model.XRulesPolicyRulesDataM)
	 */
	public void deleteModelXRulesPolicyRulesM(
			XRulesPolicyRulesDataM prmXRulesPolicyRulesDataM)
			throws XRulesPolicyRulesException {
		try {
			deleteTableXRULES_POLICY_RULES(prmXRulesPolicyRulesDataM);
		} catch (Exception e) {
			log.fatal("Error:",e);
			throw new XRulesPolicyRulesException(e.getMessage());
		}

	}

	/**
	 * @param prmXRulesPolicyRulesDataM
	 */
	private void deleteTableXRULES_POLICY_RULES(
			XRulesPolicyRulesDataM prmXRulesPolicyRulesDataM)
			throws XRulesPolicyRulesException {
		PreparedStatement ps = null;
		Connection conn = null;
		try {
			//conn = Get Connection
			conn = getConnection();
			StringBuffer sql = new StringBuffer("");
			sql.append("DELETE XRULES_POLICY_RULES ");
			//sql.append(" WHERE APPLICATION_RECORD_ID = ? AND CMPCDE=? AND IDNO=? AND POLICY_CODE=?");
			sql.append(" WHERE  AND PERSONAL_ID=? AND POLICY_CODE=?");
			String dSql = String.valueOf(sql);
			log.debug("Sql=" + dSql);
			ps = conn.prepareStatement(dSql);
			/*ps.setString(1, prmXRulesPolicyRulesDataM.getApplicationRecordId());
			ps.setString(2, prmXRulesPolicyRulesDataM.getCmpCode());
			ps.setString(3, prmXRulesPolicyRulesDataM.getIdNo());
			ps.setString(4, prmXRulesPolicyRulesDataM.getPolicyCode());*/
		   //ps.setString(1, prmXRulesPolicyRulesDataM.getApplicationRecordId());
			ps.setString(1, prmXRulesPolicyRulesDataM.getPersonalID());
			ps.setString(2, prmXRulesPolicyRulesDataM.getPolicyCode());
			ps.executeUpdate();

		} catch (Exception e) {
			log.fatal("Error:",e);
			throw new XRulesPolicyRulesException(e.getMessage());
		} finally {
			try {
				closeConnection(conn, ps);
			} catch (Exception e) {
				log.fatal("Error:",e);
				throw new XRulesPolicyRulesException(e.getMessage());
			}
		}

	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.eaf.orig.shared.xrules.dao.XRulesPolicyRulesDAO#loadModelXRulesPolicyRulesM(java.lang.String)
	 */
	public Vector loadModelXRulesPolicyRulesM(String personalID)
			throws XRulesPolicyRulesException {
		try {
			Vector vResult = selectTableXRULES_POLICY_RULES(personalID);
			return vResult;
		} catch (Exception e) {
			log.fatal("Error:",e);
			throw new XRulesPolicyRulesException(e.getMessage());
		}
	}

	/**
	 * @param applicationRecordId
	 * @return
	 */
	private Vector selectTableXRULES_POLICY_RULES(String personalID)
			throws XRulesPolicyRulesException {
		PreparedStatement ps = null;
		ResultSet rs = null;
		Connection conn = null;
		try {
			//conn = Get Connection
			conn = getConnection();
			StringBuffer sql = new StringBuffer("");
			sql.append("SELECT  PERSONAL_ID,POLICY_CODE,POLICY_TYPE,RESULT ");
			sql.append("  ,UPDATE_DATE,UPDATE_BY ");
			sql.append(" FROM XRULES_POLICY_RULES WHERE PERSONAL_ID=?");
			String dSql = String.valueOf(sql);
			log.debug("Sql=" + dSql);
			ps = conn.prepareStatement(dSql);
			rs = null;
			ps.setString(1, personalID);
			rs = ps.executeQuery();
			Vector vt = new Vector();
			while (rs.next()) {
				XRulesPolicyRulesDataM prmXRulesPolicyRulesDataM = new XRulesPolicyRulesDataM();
				prmXRulesPolicyRulesDataM.setIdNo(rs.getString(1));
				prmXRulesPolicyRulesDataM.setPolicyCode(rs.getString(2));
				prmXRulesPolicyRulesDataM.setPolicyType(rs.getString(3));
				prmXRulesPolicyRulesDataM.setResult(rs.getString(4));
				prmXRulesPolicyRulesDataM.setUpdateDate(rs.getTimestamp(5));
				prmXRulesPolicyRulesDataM.setUpdateBy(rs.getString(6));
				vt.add(prmXRulesPolicyRulesDataM);
			}
			return vt;
		} catch (Exception e) {
			log.fatal("Error:",e);
			throw new XRulesPolicyRulesException(e.getMessage());
		} finally {
			try {
				closeConnection(conn, rs, ps);
			} catch (Exception e) {
				log.fatal("Error:",e);
			}
		}
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.eaf.orig.shared.xrules.dao.XRulesPolicyRulesDAO#saveUpdateModelXRulesPolicyRulesM(com.eaf.xrules.shared.model.XRulesPolicyRulesDataM)
	 */
	public void saveUpdateModelXRulesPolicyRulesM(
			XRulesPolicyRulesDataM prmXRulesPolicyRulesDataM)
			throws XRulesPolicyRulesException {
		double returnRows = 0;
		returnRows = updateTableXRULES_POLICY_RULES(prmXRulesPolicyRulesDataM);
		if (returnRows == 0) {
			log
					.debug("New record then can't update record in table XRULES_POLICY_RULES then call Insert method");
			createTableXRULES_POLICY_RULES(prmXRulesPolicyRulesDataM);
		}

	}

	/**
	 * @param prmXRulesPolicyRulesDataM
	 * @return
	 */
	private double updateTableXRULES_POLICY_RULES(
			XRulesPolicyRulesDataM prmXRulesPolicyRulesDataM)
			throws XRulesPolicyRulesException {
		double returnRows = 0;
		PreparedStatement ps = null;
		Connection conn = null;
		try {
			//conn = Get Connection
			conn = getConnection();
			StringBuffer sql = new StringBuffer("");
			sql.append("UPDATE XRULES_POLICY_RULES ");
			sql.append(" SET POLICY_TYPE=?,RESULT=?,UPDATE_DATE=SYSDATE,UPDATE_BY=? ");
			sql.append(" WHERE PERSONAL_ID=? AND POLICY_CODE=?");
			String dSql = String.valueOf(sql);
			log.debug("Sql=" + dSql);
			ps = conn.prepareStatement(dSql);
			ps.setString(1, prmXRulesPolicyRulesDataM.getPolicyType());
			ps.setString(2, prmXRulesPolicyRulesDataM.getResult());
			ps.setString(3, prmXRulesPolicyRulesDataM.getUpdateBy());
			ps.setString(4, prmXRulesPolicyRulesDataM.getPersonalID());
			ps.setString(5, prmXRulesPolicyRulesDataM.getPolicyCode());

			returnRows = ps.executeUpdate();
		} catch (Exception e) {
			log.fatal("Error:",e);
			throw new XRulesPolicyRulesException(e.getMessage());
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
     * @see com.eaf.orig.shared.xrules.dao.XRulesPolicyRulesDAO#deleteModelXRulesPolicyRulesM(java.lang.String, java.lang.String, java.util.Vector)
     */
    public void deleteModelXRulesPolicyRulesM(String applicationRecordId, String cmpCde, Vector idNoVects) throws XRulesPolicyRulesException {
        PreparedStatement ps = null;
        Connection conn = null;
        try {
            //conn = Get Connection
            if (idNoVects == null) {
                log.debug("XRulesPolicyRulesDAOImpl-->deleteModelXRulesPolicyRulesM  idno Vects=null");
                return;
            }
            if (idNoVects.size() < 1) {
                log.debug("XRulesPolicyRulesDAOImpl-->deleteModelXRulesPolicyRulesM  Idno size =0");
                return;
            }
            conn = getConnection();
            StringBuffer sql = new StringBuffer("");
            sql.append("DELETE XRULES_POLICY_RULES ");
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
            log.fatal("Error:", e);
            throw new XRulesPolicyRulesException(e.getMessage());
        } finally {
            try {
                closeConnection(conn, ps);
            } catch (Exception e) {
                log.fatal("Error:", e);
                throw new XRulesPolicyRulesException(e.getMessage());
            }
        }

    }
}
