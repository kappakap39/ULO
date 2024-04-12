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
import com.eaf.orig.shared.xrules.dao.exception.XRulesPhoneVerificationException;
import com.eaf.xrules.shared.constant.XRulesConstant;
import com.eaf.xrules.shared.model.XRulesPhoneVerificationDataM;

/**
 * @author Sankom Sanpunya
 *
 * Type: XRulesPhoneVerificationDAOImpl
 */
public class XRulesPhoneVerificationDAOImpl extends OrigObjectDAO implements
		XRulesPhoneVerificationDAO {
	private static Logger log = Logger.getLogger(XRulesPhoneVerificationDAOImpl.class );
	/**
	 * 
	 */
	public XRulesPhoneVerificationDAOImpl() {
		super();
		
	}

	/* (non-Javadoc)
	 * @see com.eaf.orig.shared.xrules.dao.XRulesPhoneVerificationDAO#createModelXRulesPhoneVerificationM(com.eaf.xrules.shared.model.XRulesPhoneVerificationDataM)
	 */
	public void createModelXRulesPhoneVerificationM(
			XRulesPhoneVerificationDataM prmXRulesPhoneVerificationDataM)
			throws XRulesPhoneVerificationException {
		try {
			createTableXRULES_PHONE_VERIFICATION(prmXRulesPhoneVerificationDataM);
		} catch (Exception e) {
			log.fatal("Error:",e);
			throw new XRulesPhoneVerificationException(e.getMessage());
		}


	}
	/**
	 * @param prmXRulesPhoneVerificationDataM
	 */
	private void createTableXRULES_PHONE_VERIFICATION(XRulesPhoneVerificationDataM prmXRulesPhoneVerificationDataM) throws XRulesPhoneVerificationException{
		PreparedStatement ps = null;
		Connection conn = null;
		try {
			//conn = Get Connection
			conn = getConnection();
			StringBuffer sql = new StringBuffer("");
			/*sql.append("INSERT INTO XRULES_PHONE_VERIFICATION ");
			sql.append("( APPLICATION_RECORD_ID,CMPCDE,IDNO,SEQ,CALL_TYPE");
			sql.append("  ,CONTACT_TYPE,PHONE_VER_STATUS ,UPDATE_BY,UPDATE_DATE,REMARK  )");
			sql.append(" VALUES(?,?,?,?,?  ,?,?,?,?,? ) ");*/
			sql.append("INSERT INTO XRULES_PHONE_VERIFICATION ");
			sql.append("( PERSONAL_ID,SEQ,CALL_TYPE");
			sql.append("  ,CONTACT_TYPE,PHONE_VER_STATUS ,UPDATE_BY,UPDATE_DATE,REMARK  )");
			sql.append(" VALUES(?,?,?  ,?,?,?,?,? ) ");
			
			String dSql = String.valueOf(sql);
			log.debug("Sql=" + dSql);
			ps = conn.prepareStatement(dSql);

			/*ps.setString(1, prmXRulesPhoneVerificationDataM.getApplicationRecordId());
			ps.setString(2, prmXRulesPhoneVerificationDataM.getCmpCode());			 
			ps.setString(3, prmXRulesPhoneVerificationDataM.getIdNo());
			ps.setInt(4, prmXRulesPhoneVerificationDataM.getSeq());
            ps.setString(5,prmXRulesPhoneVerificationDataM.getCallType());
            ps.setString(6,prmXRulesPhoneVerificationDataM.getContactType());
            ps.setString(7,prmXRulesPhoneVerificationDataM.getPhoneVerStatus());
            ps.setString(8,prmXRulesPhoneVerificationDataM.getUpdateBy());
            ps.setTimestamp(9,prmXRulesPhoneVerificationDataM.getUpdateDate());
            ps.setString(10,prmXRulesPhoneVerificationDataM.getRemark());*/
			ps.setString(1, prmXRulesPhoneVerificationDataM.getPersonalID());
			ps.setInt(2, prmXRulesPhoneVerificationDataM.getSeq());
            ps.setString(3, prmXRulesPhoneVerificationDataM.getCallType());
            ps.setString(4, prmXRulesPhoneVerificationDataM.getContactType());
            ps.setString(5, prmXRulesPhoneVerificationDataM.getPhoneVerStatus());
            ps.setString(6, prmXRulesPhoneVerificationDataM.getUpdateBy());
            ps.setTimestamp(7, prmXRulesPhoneVerificationDataM.getUpdateDate());
            ps.setString(8, prmXRulesPhoneVerificationDataM.getRemark());
			ps.executeUpdate();
		} catch (Exception e) {
			log.fatal("Error:",e);
			throw new XRulesPhoneVerificationException(e.getMessage());
		} finally {
			try {
				closeConnection(conn, ps);
			} catch (Exception e) {
				log.fatal("Error:",e);
			}
		}
		
	}
	/* (non-Javadoc)
	 * @see com.eaf.orig.shared.xrules.dao.XRulesPhoneVerificationDAO#deleteModelXRulesPhoneVerificationM(com.eaf.xrules.shared.model.XRulesPhoneVerificationDataM)
	 */
	public void deleteModelXRulesPhoneVerificationM(
			XRulesPhoneVerificationDataM prmXRulesPhoneVerificationDataM)
			throws XRulesPhoneVerificationException {
		try {
			deleteTableXRULES_PHONE_VERIFICATION(prmXRulesPhoneVerificationDataM);
		} catch (Exception e) {
			log.fatal("Error:",e);
			throw new XRulesPhoneVerificationException(e.getMessage());
		}

	}
	/**
	 * @param XRulesDebBdDataM
	 */
	private void deleteTableXRULES_PHONE_VERIFICATION(XRulesPhoneVerificationDataM XRulesDebBdDataM)
			throws XRulesPhoneVerificationException {
		PreparedStatement ps = null;
		Connection conn = null;
		try {
			//conn = Get Connection
			conn = getConnection();
			StringBuffer sql = new StringBuffer("");
			sql.append("DELETE XRULES_PHONE_VERIFICATION ");
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
			throw new XRulesPhoneVerificationException(e.getMessage());
		} finally {
			try {
				closeConnection(conn, ps);
			} catch (Exception e) {
				log.fatal("Error:",e);
				throw new XRulesPhoneVerificationException(e.getMessage());
			}
		}

	}
	/* (non-Javadoc)
	 * @see com.eaf.orig.shared.xrules.dao.XRulesPhoneVerificationDAO#loadModelXRulesPhoneVerificationM(java.lang.String)
	 */
	public Vector loadModelXRulesPhoneVerificationM(String personalID)
			throws XRulesPhoneVerificationException {
		try {
			Vector vResult = selectTableXRULES_PHONE_VERIFICATION(personalID);
			return vResult;
		} catch (Exception e) {
			log.fatal("Error:",e);
			throw new XRulesPhoneVerificationException(e.getMessage());
		}
	}
	/**
	 * @param applicationRecordId
	 * @return
	 */
	private Vector selectTableXRULES_PHONE_VERIFICATION(String personalID)
			throws XRulesPhoneVerificationException {
		PreparedStatement ps = null;
		ResultSet rs = null;
		Connection conn = null;
		try {
			//conn = Get Connection
			conn = getConnection();
			StringBuffer sql = new StringBuffer("");
			sql.append("SELECT  PERSONAL_ID,SEQ,CALL_TYPE");
			sql.append("  ,CONTACT_TYPE,PHONE_VER_STATUS ,UPDATE_BY,UPDATE_DATE,REMARK  ");
			sql.append(" FROM XRULES_PHONE_VERIFICATION WHERE PERSONAL_ID=? ");
			sql.append(" ORDER BY SEQ ");
			String dSql = String.valueOf(sql);
			log.debug("Sql=" + dSql);
			ps = conn.prepareStatement(dSql);
			rs = null;
			ps.setString(1, personalID);
			rs = ps.executeQuery();
			Vector vt = new Vector();
			while (rs.next()) {
				XRulesPhoneVerificationDataM prmXRulesPhoneVerificationDataM = new XRulesPhoneVerificationDataM();
				prmXRulesPhoneVerificationDataM.setPersonalID(rs.getString(1));
				prmXRulesPhoneVerificationDataM.setSeq(rs.getInt(2));
				prmXRulesPhoneVerificationDataM.setCallType(rs.getString(3));
				prmXRulesPhoneVerificationDataM.setContactType(rs.getString(4));
				prmXRulesPhoneVerificationDataM.setPhoneVerStatus(rs.getString(5));
				prmXRulesPhoneVerificationDataM.setUpdateBy(rs.getString(6));
				prmXRulesPhoneVerificationDataM.setUpdateDate(rs.getTimestamp(7));
				prmXRulesPhoneVerificationDataM.setRemark(rs.getString(8));				 
				vt.add(prmXRulesPhoneVerificationDataM);
			}
			return vt;
		} catch (Exception e) {
			log.fatal("Error:",e);
			throw new XRulesPhoneVerificationException(e.getMessage());
		} finally {
			try {
				closeConnection(conn, rs, ps);
			} catch (Exception e) {
				log.fatal("Error:",e);
			}
		}
	}
	/* (non-Javadoc)
	 * @see com.eaf.orig.shared.xrules.dao.XRulesPhoneVerificationDAO#saveUpdateModelXRulesPhoneVerificationM(com.eaf.xrules.shared.model.XRulesPhoneVerificationDataM)
	 */
	public void saveUpdateModelXRulesPhoneVerificationM(
			XRulesPhoneVerificationDataM prmXRulesPhoneVerificationDataM)
			throws XRulesPhoneVerificationException {
		double returnRows = 0;
		returnRows = updateTableXRULES_PHONE_VERIFICATION(prmXRulesPhoneVerificationDataM);
		if (returnRows == 0) {
			log
					.debug("New record then can't update record in table XRULES_PHONE_VERIFICATION then call Insert method");
			createTableXRULES_PHONE_VERIFICATION(prmXRulesPhoneVerificationDataM);
		}


	}
	/**
	 * @param prmXRulesDebBdDataM
	 * @return
	 */
	private double updateTableXRULES_PHONE_VERIFICATION(XRulesPhoneVerificationDataM prmXRulesPhoneVerificationDataM)
			throws XRulesPhoneVerificationException {
		double returnRows = 0;
		PreparedStatement ps = null;
		Connection conn = null;
		try {
			//conn = Get Connection
			conn = getConnection();
			StringBuffer sql = new StringBuffer("");
			sql.append("UPDATE XRULES_PHONE_VERIFICATION ");
			sql.append(" SET CALL_TYPE=?,CONTACT_TYPE=?,PHONE_VER_STATUS=?,UPDATE_BY=?,UPDATE_DATE=? ");
			sql.append("  ,REMARK=? ");	 
			//sql.append(" WHERE APPLICATION_RECORD_ID = ? AND CMPCDE=? AND IDNO=? AND SEQ=?");
			sql.append(" WHERE PERSONAL_ID=? AND SEQ=?");
			String dSql = String.valueOf(sql);
			log.debug("Sql=" + dSql);
			ps = conn.prepareStatement(dSql);			
			/*ps.setString(1,prmXRulesPhoneVerificationDataM.getCallType());
            ps.setString(2,prmXRulesPhoneVerificationDataM.getContactType());
            ps.setString(3,prmXRulesPhoneVerificationDataM.getPhoneVerStatus());
            ps.setString(4,prmXRulesPhoneVerificationDataM.getUpdateBy());
            ps.setTimestamp(5,prmXRulesPhoneVerificationDataM.getUpdateDate());
            ps.setString(6,prmXRulesPhoneVerificationDataM.getRemark());
			ps.setString(7, prmXRulesPhoneVerificationDataM.getApplicationRecordId());
			ps.setString(8, prmXRulesPhoneVerificationDataM.getCmpCode());			 
			ps.setString(9, prmXRulesPhoneVerificationDataM.getIdNo());
			ps.setInt(10, prmXRulesPhoneVerificationDataM.getSeq());*/
			ps.setString(1,prmXRulesPhoneVerificationDataM.getCallType());
            ps.setString(2,prmXRulesPhoneVerificationDataM.getContactType());
            ps.setString(3,prmXRulesPhoneVerificationDataM.getPhoneVerStatus());
            ps.setString(4,prmXRulesPhoneVerificationDataM.getUpdateBy());
            ps.setTimestamp(5,prmXRulesPhoneVerificationDataM.getUpdateDate());
            ps.setString(6,prmXRulesPhoneVerificationDataM.getRemark());
			ps.setString(7, prmXRulesPhoneVerificationDataM.getPersonalID());			 
			ps.setInt(8, prmXRulesPhoneVerificationDataM.getSeq()); 
			returnRows = ps.executeUpdate();
		} catch (Exception e) {
			log.fatal("Error:",e);
			throw new XRulesPhoneVerificationException(e.getMessage());
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
	public void saveUpdateModelXRulesPhoneVerificationM(Vector vXRulesPhoneVerificationDataM) throws XRulesPhoneVerificationException {

		try {
			//update data
			if (vXRulesPhoneVerificationDataM != null) {
				for (int i = 0; i < vXRulesPhoneVerificationDataM.size(); i++) {
					this.saveUpdateModelXRulesPhoneVerificationM((XRulesPhoneVerificationDataM) vXRulesPhoneVerificationDataM.get(i));
				}
			}
			//delete table
			deleteSomeTableXRULES_PHONE_VERIFICATION(vXRulesPhoneVerificationDataM);
		} catch (Exception e) {
			log.fatal("Error:",e);
			throw new XRulesPhoneVerificationException(e.getMessage());
		}

		
	}
	/**
	 * @param vXRulesPhoneVerificationDataM
	 */
	private void deleteSomeTableXRULES_PHONE_VERIFICATION(Vector vXRulesPhoneVerificationDataM)
			throws XRulesPhoneVerificationException {
		PreparedStatement ps = null;
		Connection conn = null;
		try {
			//conn = Get Connection
			if (vXRulesPhoneVerificationDataM == null) {
				log.debug("XRulesPoneverificationDAOImpl-->deleteSomeTableXRULES_PHONE_VERIFICATION vXRulesPhoneVerificationDataM=null");
				return;
			}
			if (vXRulesPhoneVerificationDataM.size() < 1) {
				log.debug("XRulesPoneverificationDAOImpl-->deleteSomeTableXRULES_PHONE_VERIFICATION vXRulesPhoneVerificationDataM=0");
				return;
			}
			conn = getConnection();
			StringBuffer sql = new StringBuffer("");
			sql.append("DELETE XRULES_PHONE_VERIFICATION");
			//sql.append(" WHERE APPLICATION_RECORD_ID = ? AND CMPCDE=? AND IDNO=? ");
			sql.append(" WHERE PERSONAL_ID=? ");
			sql.append(" AND SEQ not in(");
			XRulesPhoneVerificationDataM prmXRulesDebtDbDataM;
			prmXRulesDebtDbDataM = (XRulesPhoneVerificationDataM) vXRulesPhoneVerificationDataM.get(0);
			sql.append(prmXRulesDebtDbDataM.getSeq());
			for (int i = 1; i < vXRulesPhoneVerificationDataM.size()&&i<XRulesConstant.limitDeleteSQLParam; i++) {
				prmXRulesDebtDbDataM = (XRulesPhoneVerificationDataM) vXRulesPhoneVerificationDataM.get(i);
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
			throw new XRulesPhoneVerificationException(e.getMessage());
		} finally {
			try {
				closeConnection(conn, ps);
			} catch (Exception e) {
				log.fatal("Error:",e);
				throw new XRulesPhoneVerificationException(e.getMessage());
			}
		}

	}

    /* (non-Javadoc)
     * @see com.eaf.orig.shared.xrules.dao.XRulesPhoneVerificationDAO#deleteModelXRulesPhoneVerificationM(java.lang.String, java.lang.String, java.util.Vector)
     */
    public void deleteModelXRulesPhoneVerificationM(String applicationRecordId, String cmpCde, Vector idNoVects) throws XRulesPhoneVerificationException {
        PreparedStatement ps = null;
        Connection conn = null;
        try {
             
            if (idNoVects == null) {
                log.debug("XRulesPhoneVerificationDAOImpl-->deleteModelXRulesPhoneVerificationM  idNOVects is null ");
                return;
            }
            if (idNoVects.size() < 1) {
                log.debug("XRulesPhoneVerificationDAOImpl-->deleteModelXRulesPhoneVerificationM  IdNO vects Size =0");
                return;
            }
            conn = getConnection();
            StringBuffer sql = new StringBuffer("");
            sql.append("DELETE XRULES_PHONE_VERIFICATION ");
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
            throw new XRulesPhoneVerificationException(e.getMessage());
        } finally {
            try {
                closeConnection(conn, ps);
            } catch (Exception e) {
                log.fatal("Error:", e);
                throw new XRulesPhoneVerificationException(e.getMessage());
            }
        }

    }
}
