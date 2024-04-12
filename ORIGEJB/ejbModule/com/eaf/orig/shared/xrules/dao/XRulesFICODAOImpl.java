/*
 * Created on Nov 28, 2007
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
import com.eaf.orig.shared.xrules.dao.exception.XRulesFICOException;
import com.eaf.xrules.shared.constant.XRulesConstant;
import com.eaf.xrules.shared.model.XRulesFICODataM;

/**
 * @author Sankom
 *
 * Type: XRulesFICODAOImpl
 */
public class XRulesFICODAOImpl extends OrigObjectDAO implements XRulesFICODAO {
	private static Logger log = Logger
	.getLogger(XRulesFICODAOImpl.class);
    /**
     * 
     */
    public XRulesFICODAOImpl() {
        super();
 
    }

    /* (non-Javadoc)
     * @see com.eaf.orig.shared.xrules.dao.XRulesFICODAO#createModelXRulesFICOM(com.eaf.xrules.shared.model.XRulesFICODataM)
     */
    public void createModelXRulesFICODataM(XRulesFICODataM prmXRulesFICODataM)
            throws XRulesFICOException {     
		    try {
	            createTableXRULES_FICO(prmXRulesFICODataM);
	        } catch (Exception e) {
	            log.fatal("Error:",e);
	            throw new XRulesFICOException(e.getMessage());
	        }
    }

    /**
     * @param prmXRulesFICODataM
     */
    private void createTableXRULES_FICO(XRulesFICODataM prmXRulesFICODataM) throws XRulesFICOException {
        PreparedStatement ps = null;
        Connection conn = null;
        try {
            //conn = Get Connection
            conn = getConnection();
            StringBuffer sql = new StringBuffer("");
            /*sql.append("INSERT INTO XRULES_FICO ");
            sql.append("( APPLICATION_RECORD_ID,CMPCDE,IDNO,UPDATE_DATE,UPDATE_BY  ");
            sql.append("  ,SCORE,REASON_CODE1 ,REASON_CODE2 ,REASON_CODE3,REASON_CODE4,ERROR_MSG");
            sql.append("  ,ERROR_CODE )");*/
            sql.append("INSERT INTO XRULES_FICO ");
            sql.append("( PERSONAL_ID,UPDATE_DATE,UPDATE_BY  ");
            sql.append("  ,SCORE,REASON_CODE1 ,REASON_CODE2 ,REASON_CODE3,REASON_CODE4,ERROR_MSG");
            sql.append("  ,ERROR_CODE )");
            
            sql.append(" VALUES(?,SYSDATE,?  ,?,?,?,?,?  ,?,? ) ");
            String dSql = String.valueOf(sql);
            log.debug("Sql=" + dSql);
            ps = conn.prepareStatement(dSql);

            /*ps.setString(1, prmXRulesFICODataM.getApplicationRecordId());
            ps.setString(2, prmXRulesFICODataM.getCmpCde());
            ps.setString(3, prmXRulesFICODataM.getIdNo());
            ps.setString(4, prmXRulesFICODataM.getUpdateBy());
            ps.setLong(5, prmXRulesFICODataM.getScore());
            ps.setString(6, prmXRulesFICODataM.getReasonCode1());
            ps.setString(7, prmXRulesFICODataM.getReasonCode2());
            ps.setString(8, prmXRulesFICODataM.getReasonCode3());
            ps.setString(9, prmXRulesFICODataM.getReasonCode4());   
            ps.setString(10,prmXRulesFICODataM.getErrorMessage());
            ps.setString(11,prmXRulesFICODataM.getErrorCode());*/
            ps.setString(1, prmXRulesFICODataM.getPersonalID());
            ps.setString(2, prmXRulesFICODataM.getUpdateBy());
            ps.setLong(3, prmXRulesFICODataM.getScore());
            ps.setString(4, prmXRulesFICODataM.getReasonCode1());
            ps.setString(5, prmXRulesFICODataM.getReasonCode2());
            ps.setString(6, prmXRulesFICODataM.getReasonCode3());
            ps.setString(7, prmXRulesFICODataM.getReasonCode4());   
            ps.setString(8,prmXRulesFICODataM.getErrorMessage());
            ps.setString(9,prmXRulesFICODataM.getErrorCode());
            ps.executeUpdate();
        } catch (Exception e) {
            log.fatal("Error:",e);
            throw new XRulesFICOException(e.getMessage());
        } finally {
            try {
                closeConnection(conn, ps);
            } catch (Exception e) {
                log.fatal("Error:",e);
            }
        }
        
    }

    /* (non-Javadoc)
     * @see com.eaf.orig.shared.xrules.dao.XRulesFICODAO#deleteModelXRulesFICOM(com.eaf.xrules.shared.model.XRulesFICODataM)
     */
    public void deleteModelXRulesFICODataM(XRulesFICODataM prmXRulesFICODataM)
            throws XRulesFICOException {
        try {
            deleteTableXRULES_FICO(prmXRulesFICODataM);
        } catch (Exception e) {
            log.fatal("Error:",e);
            throw new XRulesFICOException(e.getMessage());
        }

    }

    /**
     * @param prmXRulesFICODataM
     */
    private void deleteTableXRULES_FICO(XRulesFICODataM prmXRulesFICODataM) throws XRulesFICOException{
    	Connection conn = null;
        PreparedStatement ps = null;
        try {
            //conn = Get Connection
            conn = getConnection();
            StringBuffer sql = new StringBuffer("");
            sql.append("DELETE XRULES_FICO ");
            //sql.append(" WHERE APPLICATION_RECORD_ID = ? AND CMPCDE=? AND IDNO=? ");
            sql.append(" WHERE APPLICATION_RECORD_ID = ? AND PERSONAL_ID=? ");
            String dSql = String.valueOf(sql);
            log.debug("Sql=" + dSql);
            ps = conn.prepareStatement(dSql);
            /*ps.setString(1, prmXRulesFICODataM.getApplicationRecordId());
            ps.setString(2, prmXRulesFICODataM.getCmpCde());
            ps.setString(3, prmXRulesFICODataM.getIdNo());*/
            ps.setString(1, prmXRulesFICODataM.getApplicationRecordId());
            ps.setString(2, prmXRulesFICODataM.getPersonalID());
            ps.executeUpdate();

        } catch (Exception e) {
            log.fatal("Error:",e);
            throw new XRulesFICOException(e.getMessage());
        } finally {
            try {
                closeConnection(conn, ps);
            } catch (Exception e) {
                log.fatal("Error:",e);
                throw new XRulesFICOException(e.getMessage());
            }
        }
    }

    /* (non-Javadoc)
     * @see com.eaf.orig.shared.xrules.dao.XRulesFICODAO#loadModelXRulesFICOM(java.lang.String)
     */
    public XRulesFICODataM loadModelXRulesFICODataM(String personalID)
            throws XRulesFICOException {
        try {
            XRulesFICODataM result = selectTableXRULES_FICO(personalID);
            return result;
        } catch (Exception e) {
            log.fatal("Error:",e);
            throw new XRulesFICOException(e.getMessage());
        }
    }

    /**
     * @param applicationRecordId
     * @return
     */
    private XRulesFICODataM selectTableXRULES_FICO(String personalID)throws XRulesFICOException {
        PreparedStatement ps = null;
        ResultSet rs = null;
        Connection conn = null;
        try {
            //conn = Get Connection
            conn = getConnection();
            StringBuffer sql = new StringBuffer("");
            sql.append("SELECT   PERSONAL_ID,UPDATE_DATE,UPDATE_BY,SCORE ");
            sql.append(" ,REASON_CODE1 ,REASON_CODE2 ,REASON_CODE3,REASON_CODE4,ERROR_MSG,ERROR_CODE  ");
            sql.append(" FROM XRULES_FICO WHERE PERSONAL_ID=? ");
            String dSql = String.valueOf(sql);
            log.debug("Sql=" + dSql);
            ps = conn.prepareStatement(dSql);
            rs = null;
            ps.setString(1, personalID);
            rs = ps.executeQuery();            
            XRulesFICODataM prmXRulesFICODataM = null;
            if (rs.next()) {
                prmXRulesFICODataM = new XRulesFICODataM();
                prmXRulesFICODataM.setPersonalID(rs.getString(1));                 
                prmXRulesFICODataM.setUpdateDate(rs.getTimestamp(2));
                prmXRulesFICODataM.setUpdateBy(rs.getString(3));
                prmXRulesFICODataM.setScore(rs.getLong(4));
                prmXRulesFICODataM.setReasonCode1(rs.getString(5));
                prmXRulesFICODataM.setReasonCode2(rs.getString(6));
                prmXRulesFICODataM.setReasonCode3(rs.getString(7));
                prmXRulesFICODataM.setReasonCode4(rs.getString(8));
                prmXRulesFICODataM.setErrorMessage(rs.getString(9));
                prmXRulesFICODataM.setErrorCode(rs.getString(10));
            }
            return prmXRulesFICODataM;
        } catch (Exception e) {
            log.fatal("Error:",e);
            throw new XRulesFICOException(e.getMessage());
        } finally {
            try {
                closeConnection(conn, rs, ps);
            } catch (Exception e) {
                log.fatal("Error:",e);
            }
        }
    }

    /* (non-Javadoc)
     * @see com.eaf.orig.shared.xrules.dao.XRulesFICODAO#saveUpdateModelXRulesFICOM(com.eaf.xrules.shared.model.XRulesFICODataM)
     */
    public void saveUpdateModelXRulesFICODataM(XRulesFICODataM prmXRulesFICODataM)
            throws XRulesFICOException {
        double returnRows = 0;
        returnRows = updateTableXRULES_FICO(prmXRulesFICODataM);
        if (returnRows == 0) {
            log.debug("New record then can't update record in table XRULES_DEBT_BD then call Insert method");
            createTableXRULES_FICO(prmXRulesFICODataM);
        }


    }

    /**
     * @param prmXRulesFICODataM
     * @return
     */
    private double updateTableXRULES_FICO(XRulesFICODataM prmXRulesFICODataM)throws XRulesFICOException {
        double returnRows = 0;
        PreparedStatement ps = null;
        Connection conn = null;
        try {
            //conn = Get Connection
            conn = getConnection();
            StringBuffer sql = new StringBuffer("");
            /*sql.append("UPDATE XRULES_FICO ");
            sql.append(" SET  SCORE=?,REASON_CODE1=? ,REASON_CODE2=? ,REASON_CODE3=?,REASON_CODE4=?,UPDATE_DATE=SYSDATE ,UPDATE_BY=?,ERROR_MSG=?,ERROR_CODE=? ");
            sql.append(" WHERE APPLICATION_RECORD_ID = ? AND CMPCDE=? AND IDNO=?  ");*/
            sql.append("UPDATE XRULES_FICO ");
            sql.append(" SET  SCORE=?,REASON_CODE1=? ,REASON_CODE2=? ,REASON_CODE3=?,REASON_CODE4=?,UPDATE_DATE=SYSDATE ,UPDATE_BY=?,ERROR_MSG=?,ERROR_CODE=? ");
            sql.append(" WHERE PERSONAL_ID=?  ");
            String dSql = String.valueOf(sql);
            log.debug("Sql=" + dSql);
            ps = conn.prepareStatement(dSql);
            /*ps.setLong(1, prmXRulesFICODataM.getScore());
            ps.setString(2, prmXRulesFICODataM.getReasonCode1());
            ps.setString(3, prmXRulesFICODataM.getReasonCode2());
            ps.setString(4, prmXRulesFICODataM.getReasonCode3());
            ps.setString(5, prmXRulesFICODataM.getReasonCode4());     
            ps.setString(6, prmXRulesFICODataM.getUpdateBy());
            ps.setString(7,prmXRulesFICODataM.getErrorMessage());
            ps.setString(8,prmXRulesFICODataM.getErrorCode());
            ps.setString(9, prmXRulesFICODataM.getApplicationRecordId());
            ps.setString(10, prmXRulesFICODataM.getCmpCde());
            ps.setString(11, prmXRulesFICODataM.getIdNo());*/
            ps.setLong(1, prmXRulesFICODataM.getScore());
            ps.setString(2, prmXRulesFICODataM.getReasonCode1());
            ps.setString(3, prmXRulesFICODataM.getReasonCode2());
            ps.setString(4, prmXRulesFICODataM.getReasonCode3());
            ps.setString(5, prmXRulesFICODataM.getReasonCode4());     
            ps.setString(6, prmXRulesFICODataM.getUpdateBy());
            ps.setString(7,prmXRulesFICODataM.getErrorMessage());
            ps.setString(8,prmXRulesFICODataM.getErrorCode());
            ps.setString(9, prmXRulesFICODataM.getPersonalID());
            returnRows = ps.executeUpdate();
        } catch (Exception e) {
            log.fatal("Error:",e);
            e.printStackTrace();   
            throw new XRulesFICOException(e.getMessage());
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
     * @see com.eaf.orig.shared.xrules.dao.XRulesFICODAO#deleteModelXRulesFICODataM(java.lang.String, java.lang.String, java.util.Vector)
     */
    public void deleteModelXRulesFICODataM(String applicationRecordId, String cmpCde, Vector idNoVects) throws XRulesFICOException {
        PreparedStatement ps = null;
        Connection conn = null;
		try {
			//conn = Get Connection
			if (idNoVects == null) {
				log
						.debug("XRulesFICODAOImpl-->deleteModelXRulesFICODataM  idno Vects=null");
				return;
			}
			if (idNoVects.size() < 1) {
				log
						.debug("XRulesFICODAOImpl-->deleteModelXRulesFICODataM  Idno size =0");
				return;
			}
			conn = getConnection();
			StringBuffer sql = new StringBuffer("");
			sql.append("DELETE XRULES_FICO ");
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
			throw new XRulesFICOException(e.getMessage());
		} finally {
			try {
				closeConnection(conn, ps);
			} catch (Exception e) {
				log.fatal("Error:",e);
				throw new XRulesFICOException(e.getMessage());
			}
		}


    }
}
