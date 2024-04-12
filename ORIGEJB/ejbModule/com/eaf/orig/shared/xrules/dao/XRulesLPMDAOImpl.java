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
import com.eaf.orig.shared.xrules.dao.exception.XRulesLPMException;
import com.eaf.xrules.shared.constant.XRulesConstant;
import com.eaf.xrules.shared.model.XRulesLPMDataM;

/**
 * @author Sankom Sanpunya
 *
 * Type: XRulesLPMDAOImpl
 */
public class XRulesLPMDAOImpl extends OrigObjectDAO implements XRulesLPMDAO {
	private static Logger log = Logger.getLogger(XRulesLPMDAOImpl.class );
	/**
	 * 
	 */
	public XRulesLPMDAOImpl() {
		super();
		
	}

	/* (non-Javadoc)
	 * @see com.eaf.orig.shared.xrules.dao.XRulesLPMDAO#createModelXRulesLPMM(com.eaf.xrules.shared.model.XRulesLPMDataM)
	 */
	public void createModelXRulesLPMM(XRulesLPMDataM prmXRulesLPMDataM)
			throws XRulesLPMException {
		try {
			createTableXRULES_LPM(prmXRulesLPMDataM);
		} catch (Exception e) {
			log.fatal("Error:",e);
			throw new XRulesLPMException(e.getMessage());
		}


	}
	/**
	 * @param prmXRulesLPMDataM
	 */
	private void createTableXRULES_LPM(XRulesLPMDataM prmXRulesLPMDataM) throws XRulesLPMException{
		PreparedStatement ps = null;
		Connection conn = null;
		try {
			//conn = Get Connection
			conn = getConnection();
			StringBuffer sql = new StringBuffer("");
			/*sql.append("INSERT INTO XRULES_LPM ");
			sql.append("( APPLICATION_RECORD_ID,CMPCDE,IDNO,SEQ,LOAN_TYPE");
			sql.append("  ,CREDITLIMIT,OS_BALANCE,STATUS,UPDATE_DATE,UPDATE_BY  ,LPM_FLAG  )");*/
			sql.append("INSERT INTO XRULES_LPM ");
			sql.append("( PERSONAL_ID,SEQ,LOAN_TYPE");
			sql.append("  ,CREDITLIMIT,OS_BALANCE,STATUS,UPDATE_DATE,UPDATE_BY  ,LPM_FLAG  )");
			sql.append(" VALUES(?,?,?    ,?,?,?,SYSDATE,?  ,?) ");
			String dSql = String.valueOf(sql);
			log.debug("Sql=" + dSql);
			ps = conn.prepareStatement(dSql);
			/*ps.setString(1, prmXRulesLPMDataM.getApplicationRecordId());
			ps.setString(2, prmXRulesLPMDataM.getCmpCode());			 
			ps.setString(3, prmXRulesLPMDataM.getIdNo());
			ps.setInt(4, prmXRulesLPMDataM.getSeq());
			ps.setString(5, prmXRulesLPMDataM.getLoanType());
			ps.setBigDecimal(6, prmXRulesLPMDataM.getCreditLimit());
			ps.setBigDecimal(7, prmXRulesLPMDataM.getOSBalnace());
			ps.setString(8, prmXRulesLPMDataM.getStatus());
			ps.setString(9, prmXRulesLPMDataM.getUpdateBy());
			ps.setString(10, prmXRulesLPMDataM.getLpmFlag());*/
			
			ps.setString(1, prmXRulesLPMDataM.getPersonalID());
			ps.setInt(2, prmXRulesLPMDataM.getSeq());
			ps.setString(3, prmXRulesLPMDataM.getLoanType());
			ps.setBigDecimal(4, prmXRulesLPMDataM.getCreditLimit());
			ps.setBigDecimal(5, prmXRulesLPMDataM.getOSBalnace());
			ps.setString(6, prmXRulesLPMDataM.getStatus());
			ps.setString(7, prmXRulesLPMDataM.getUpdateBy());
			ps.setString(8, prmXRulesLPMDataM.getLpmFlag());
			ps.executeUpdate();
		} catch (Exception e) {
			log.fatal("Error:",e);
			throw new XRulesLPMException(e.getMessage());
		} finally {
			try {
				closeConnection(conn, ps);
			} catch (Exception e) {
				log.fatal("Error:",e);
			}
		}
		
	}
	/* (non-Javadoc)
	 * @see com.eaf.orig.shared.xrules.dao.XRulesLPMDAO#deleteModelXRulesLPMM(com.eaf.xrules.shared.model.XRulesLPMDataM)
	 */
	public void deleteModelXRulesLPMM(XRulesLPMDataM prmXRulesLPMDataM)
			throws XRulesLPMException {
		try {
			deleteTableXRULES_LPM(prmXRulesLPMDataM);
		} catch (Exception e) {
			log.fatal("Error:",e);
			throw new XRulesLPMException(e.getMessage());
		}

	}
	/**
	 * @param XRulesDebBdDataM
	 */
	private void deleteTableXRULES_LPM(XRulesLPMDataM XRulesDebBdDataM)
			throws XRulesLPMException {
		PreparedStatement ps = null;
		Connection conn = null;
		try {
			//conn = Get Connection
			conn = getConnection();
			StringBuffer sql = new StringBuffer("");
			sql.append("DELETE XRULES_LPM ");
			//sql.append(" WHERE APPLICATION_RECORD_ID = ? AND CMPCDE=? AND IDNO=? AND SEQ=?");
			sql.append(" WHERE APPLICATION_RECORD_ID = ? AND PERSONAL_ID=? AND SEQ=?");
			String dSql = String.valueOf(sql);
			log.debug("Sql=" + dSql);
			ps = conn.prepareStatement(dSql);
			ps.setString(1, XRulesDebBdDataM.getApplicationRecordId());
			ps.setString(2, XRulesDebBdDataM.getPersonalID());
			ps.setInt(3, XRulesDebBdDataM.getSeq());
			ps.executeUpdate();

		} catch (Exception e) {
			log.fatal("Error:",e);
			throw new XRulesLPMException(e.getMessage());
		} finally {
			try {
				closeConnection(conn, ps);
			} catch (Exception e) {
				log.fatal("Error:",e);
				throw new XRulesLPMException(e.getMessage());
			}
		}

	}
	/* (non-Javadoc)
	 * @see com.eaf.orig.shared.xrules.dao.XRulesLPMDAO#loadModelXRulesLPMM(java.lang.String)
	 */
	public Vector loadModelXRulesLPMM(String personalID)
			throws XRulesLPMException {
		try {
			Vector vResult = selectTableXRULES_LPM(personalID);
			return vResult;
		} catch (Exception e) {
			log.fatal("Error:",e);
			throw new XRulesLPMException(e.getMessage());
		}
	}
	/**
	 * @param applicationRecordId
	 * @return
	 */
	private Vector selectTableXRULES_LPM(String personalID)
			throws XRulesLPMException {
		PreparedStatement ps = null;
		ResultSet rs = null;
		Connection conn = null;
		try {
			//conn = Get Connection
			conn = getConnection();
			StringBuffer sql = new StringBuffer("");
			sql.append("SELECT  PERSONAL_ID,SEQ,LOAN_TYPE ");
			sql.append(" ,CREDITLIMIT,OS_BALANCE,STATUS,UPDATE_DATE,UPDATE_BY,LPM_FLAG  ");
			sql.append(" FROM XRULES_LPM WHERE PERSONAL_ID=?");
			String dSql = String.valueOf(sql);
			log.debug("Sql=" + dSql);
			ps = conn.prepareStatement(dSql);
			rs = null;
			ps.setString(1, personalID);
			rs = ps.executeQuery();
			Vector vt = new Vector();
			while (rs.next()) {
				XRulesLPMDataM prmXRulesLPMDataM = new XRulesLPMDataM();
				prmXRulesLPMDataM.setPersonalID(rs.getString(1));
				prmXRulesLPMDataM.setSeq(rs.getInt(2));
				prmXRulesLPMDataM.setLoanType(rs.getString(3)); 
				prmXRulesLPMDataM.setCreditLimit(rs.getBigDecimal(4));
				prmXRulesLPMDataM.setOSBalnace(rs.getBigDecimal(5)); 
				prmXRulesLPMDataM.setStatus(rs.getString(6));
				prmXRulesLPMDataM.setUpdateDate(rs.getTimestamp(7));
				prmXRulesLPMDataM.setUpdateBy(rs.getString(8));
				prmXRulesLPMDataM.setLpmFlag(rs.getString(9));
				vt.add(prmXRulesLPMDataM);
			}
			return vt;
		} catch (Exception e) {
			log.fatal("Error:",e);
			throw new XRulesLPMException(e.getMessage());
		} finally {
			try {
				closeConnection(conn, rs, ps);
			} catch (Exception e) {
				log.fatal("Error:",e);
			}
		}
	}
	/* (non-Javadoc)
	 * @see com.eaf.orig.shared.xrules.dao.XRulesLPMDAO#saveUpdateModelXRulesLPMM(com.eaf.xrules.shared.model.XRulesLPMDataM)
	 */
	public void saveUpdateModelXRulesLPMM(XRulesLPMDataM prmXRulesLPMDataM)
			throws XRulesLPMException {
		double returnRows = 0;
		returnRows = updateTableXRULES_LPM(prmXRulesLPMDataM);
		if (returnRows == 0) {
			log.debug("New record then can't update record in table XRULES_LPM then call Insert method");
			createTableXRULES_LPM(prmXRulesLPMDataM);
		}
	}
	/**
	 * @param prmXRulesDebBdDataM
	 * @return
	 */
	private double updateTableXRULES_LPM(XRulesLPMDataM prmXRulesLPMDataM)
			throws XRulesLPMException {
		double returnRows = 0;
		PreparedStatement ps = null;
		Connection conn = null;
		try {
			//conn = Get Connection
			conn = getConnection();
			StringBuffer sql = new StringBuffer("");
			sql.append("UPDATE XRULES_LPM ");
			sql.append(" SET LOAN_TYPE=?,CREDITLIMIT=?,OS_BALANCE=?,STATUS=?,UPDATE_DATE=SYSDATE ,LPM_FLAG=?");
			sql.append("  ,UPDATE_BY=?  ");
			//sql.append(" WHERE APPLICATION_RECORD_ID = ? AND CMPCDE=? AND IDNO=? AND SEQ=?");
			sql.append(" WHERE PERSONAL_ID=? AND SEQ=?");
			String dSql = String.valueOf(sql);
			log.debug("Sql=" + dSql);
			ps = conn.prepareStatement(dSql);			
			/*ps.setString(1, prmXRulesLPMDataM.getLoanType());
			ps.setBigDecimal(2, prmXRulesLPMDataM.getCreditLimit());
			ps.setBigDecimal(3, prmXRulesLPMDataM.getOSBalnace());
			ps.setString(4, prmXRulesLPMDataM.getStatus()); 			
			ps.setString(5, prmXRulesLPMDataM.getLpmFlag());
			ps.setString(6, prmXRulesLPMDataM.getUpdateBy());
			ps.setString(7, prmXRulesLPMDataM.getApplicationRecordId());
			ps.setString(8, prmXRulesLPMDataM.getCmpCode());			 
			ps.setString(9, prmXRulesLPMDataM.getIdNo());
			ps.setInt(10, prmXRulesLPMDataM.getSeq());*/
			ps.setString(1, prmXRulesLPMDataM.getLoanType());
			ps.setBigDecimal(2, prmXRulesLPMDataM.getCreditLimit());
			ps.setBigDecimal(3, prmXRulesLPMDataM.getOSBalnace());
			ps.setString(4, prmXRulesLPMDataM.getStatus()); 			
			ps.setString(5, prmXRulesLPMDataM.getLpmFlag());
			ps.setString(6, prmXRulesLPMDataM.getUpdateBy());
			ps.setString(7, prmXRulesLPMDataM.getPersonalID());
			ps.setInt(8, prmXRulesLPMDataM.getSeq());
			returnRows = ps.executeUpdate();
		} catch (Exception e) {
			log.fatal("Error:",e);
			throw new XRulesLPMException(e.getMessage());
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
	public void saveUpdateModelXRulesLPMM(Vector vXRulesLPMDataM) throws XRulesLPMException {

		try {
			//update data
			if (vXRulesLPMDataM != null) {
				for (int i = 0; i < vXRulesLPMDataM.size(); i++) {
				    XRulesLPMDataM  prmXRulesLPMDataM= (XRulesLPMDataM) vXRulesLPMDataM.get(i);
				    prmXRulesLPMDataM.setSeq(i+1);
					this.saveUpdateModelXRulesLPMM(prmXRulesLPMDataM);
				}
			}
			//delete table
			deleteSomeTableXRULES_LPM(vXRulesLPMDataM);
		} catch (Exception e) {
			log.fatal("Error:",e);
			throw new XRulesLPMException(e.getMessage());
		}

		
	}
	/**
	 * @param vXRulesLPMDataM
	 */
	private void deleteSomeTableXRULES_LPM(Vector vXRulesLPMDataM)
			throws XRulesLPMException {
		PreparedStatement ps = null;
		Connection conn = null;
		try {
			//conn = Get Connection
			if (vXRulesLPMDataM == null) {
				log.debug("XRulesLPMDAOImpl-->deleteSomeTableXRULES_LPM vXRulesLPMDataM=null");
				return;
			}
			if (vXRulesLPMDataM.size() < 1) {
				log.debug("XRulesLPMDAOImpl-->deleteSomeTableXRULES_LPM vXRulesLPMDataM=0");
				return;
			}
			conn = getConnection();
			StringBuffer sql = new StringBuffer("");
			sql.append("DELETE XRULES_LPM ");
			sql.append(" WHERE PERSONAL_ID=? ");
			sql.append(" AND SEQ not in(");
			XRulesLPMDataM prmXRulesDebtDbDataM;
			prmXRulesDebtDbDataM = (XRulesLPMDataM) vXRulesLPMDataM.get(0);
			sql.append(prmXRulesDebtDbDataM.getSeq());
			for (int i = 1; i < vXRulesLPMDataM.size()&&i<XRulesConstant.limitDeleteSQLParam; i++) {
				prmXRulesDebtDbDataM = (XRulesLPMDataM) vXRulesLPMDataM.get(i);
				sql.append("," + prmXRulesDebtDbDataM.getSeq());
			}
			sql.append(" )");
			String dSql = String.valueOf(sql);
			log.debug("Sql=" + dSql);
			ps = conn.prepareStatement(dSql);
			/*ps.setString(1, prmXRulesDebtDbDataM.getApplicationRecordId());
			ps.setString(2, prmXRulesDebtDbDataM.getCmpCode());
			ps.setString(3, prmXRulesDebtDbDataM.getIdNo());*/
			ps.setString(1, prmXRulesDebtDbDataM.getPersonalID());
			ps.executeUpdate();

		} catch (Exception e) {
			log.fatal("Error:",e);
			throw new XRulesLPMException(e.getMessage());
		} finally {
			try {
				closeConnection(conn, ps);
			} catch (Exception e) {
				log.fatal("Error:",e);
				throw new XRulesLPMException(e.getMessage());
			}
		}

	}
	
    /* (non-Javadoc)
     * @see com.eaf.orig.shared.xrules.dao.XRulesLPMDAO#deleteModelXRulesLPMM(java.lang.String, java.lang.String, java.util.Vector)
     */
    public void deleteModelXRulesLPMM(String applicationRecordId, String cmpCde, Vector idNoVects) throws XRulesLPMException {
        PreparedStatement ps = null;
        Connection conn = null;
        try {
             
            if (idNoVects == null) {
                log.debug("XRulesLPMDAOImpl-->deleteModelXRulesLPMM  idNOVects is null ");
                return;
            }
            if (idNoVects.size() < 1) {
                log.debug("XRulesLPMDAOImpl-->deleteModelXRulesLPMM  IdNO vects Size =0");
                return;
            }
            conn = getConnection();
            StringBuffer sql = new StringBuffer("");
            sql.append("DELETE XRULES_LPM ");
            sql.append(" WHERE APPLICATION_RECORD_ID = ? AND CMPCDE=?  ");
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
            throw new XRulesLPMException(e.getMessage());
        } finally {
            try {
                closeConnection(conn, ps);
            } catch (Exception e) {
                log.fatal("Error:", e);
                throw new XRulesLPMException(e.getMessage());
            }
        }

    }
}
