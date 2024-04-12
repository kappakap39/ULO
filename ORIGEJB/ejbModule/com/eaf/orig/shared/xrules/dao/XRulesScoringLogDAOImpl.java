/*
 * Created on Dec 13, 2007
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
import com.eaf.orig.shared.xrules.dao.exception.XRulesScoringException;
import com.eaf.xrules.shared.model.XRulesScoringLogDataM;

/**
 * @author Sankom Sanpunya
 *
 * Type: XRulesScoringLogDAOImpl
 */
public class XRulesScoringLogDAOImpl extends OrigObjectDAO implements XRulesScoringLogDAO {
    private static Logger log = Logger
	.getLogger(XRulesScoringLogDAOImpl.class);
    /**
     * 
     */
    public XRulesScoringLogDAOImpl() {
        super();
        
    }

    /* (non-Javadoc)
     * @see com.eaf.orig.shared.xrules.dao.XRulesScoringLogDAO#createXRulesScoringLogM(com.eaf.xrules.shared.model.XRulesScoringLogDataM)
     */
    public void createXRulesScoringLogM(
            XRulesScoringLogDataM  prmXRulesScoringLogDataM)
            throws XRulesScoringException {
        try {
			createTableXRULES_SCORING_LOG(prmXRulesScoringLogDataM);
		} catch (Exception e) {
			log.fatal("Error:",e);
			throw new XRulesScoringException(e.getMessage());
		}

    }

    /**
     * @param pamXRulesScoringLogDataM
     */
    private void createTableXRULES_SCORING_LOG(XRulesScoringLogDataM prmXRulesScoringLogDataM)throws XRulesScoringException {
    	   PreparedStatement ps = null;
    	   Connection conn = null;
		try {
			//conn = Get Connection
			conn = getConnection();
			StringBuffer sql = new StringBuffer("");
			sql.append("INSERT INTO XRULES_SCORING_LOG ");
			sql
					.append("( APPLICATION_RECORD_ID,SEQ,SCORING_SCORE,SCORING_RESULT,UPDATE_BY");
			sql.append(" ,UPDATE_DATE )");
			sql.append(" VALUES(?,?,?,?,? ,?  ) ");
			String dSql = String.valueOf(sql);
			log.debug("Sql=" + dSql);
			ps = conn.prepareStatement(dSql);
			ps.setString(1, prmXRulesScoringLogDataM.getApplicationRecordId());
			ps.setInt(2, prmXRulesScoringLogDataM.getSeq());
		 
			ps.setDouble(3, prmXRulesScoringLogDataM.getScoring());
			ps.setString(4, prmXRulesScoringLogDataM.getScoringResult());
			ps.setString(5, prmXRulesScoringLogDataM.getUpdateBy());
			ps.setTimestamp(6, prmXRulesScoringLogDataM.getUpdateDate());
			ps.executeUpdate();
		} catch (Exception e) {
			log.fatal("Error:",e);
			throw new XRulesScoringException(e.getMessage());
		} finally {
			try {
				closeConnection(conn, ps);
			} catch (Exception e) {
				log.fatal("Error:",e);
			}
		}
        
    }

    /* (non-Javadoc)
     * @see com.eaf.orig.shared.xrules.dao.XRulesScoringLogDAO#getMaxScoringSequence(com.eaf.xrules.shared.model.XRulesScoringLogDataM)
     */
    public int getMaxScoringSequence(
            XRulesScoringLogDataM prmXRulesScoringLogDataM)
            throws XRulesScoringException {
        int maxScoring=0;
        try {
            maxScoring=selectMaxSeqTableXRULES_SCORING_LOG(prmXRulesScoringLogDataM);
		} catch (Exception e) {
			log.fatal("Error:",e);
			throw new XRulesScoringException(e.getMessage());
		}
        return maxScoring;
    }

    /**
     * @param prmXRulesScoringLogDataM
     */
    private int selectMaxSeqTableXRULES_SCORING_LOG(XRulesScoringLogDataM prmXRulesScoringLogDataM) throws XRulesScoringException{
    	PreparedStatement ps = null;
		ResultSet rs = null;
		int maxSeq=0;
		Connection conn = null;
		try {
			//conn = Get Connection
			conn = getConnection();
			StringBuffer sql = new StringBuffer("");
			sql.append("SELECT  max(SEQ) ");			 
			sql
					.append(" FROM XRULES_SCORING_LOG WHERE APPLICATION_RECORD_ID = ? ");
			String dSql = String.valueOf(sql);
			log.debug("Sql=" + dSql);
			ps = conn.prepareStatement(dSql);
			rs = null;
			ps.setString(1, prmXRulesScoringLogDataM.getApplicationRecordId());	 
			rs = ps.executeQuery();
			Vector vt = new Vector();
			while (rs.next()) {
			    maxSeq=rs.getInt(1);				 				 
			}
			return maxSeq;
		} catch (Exception e) {
			log.fatal("Error:",e);
			throw new XRulesScoringException(e.getMessage());
		} finally {
			try {
				closeConnection(conn, rs, ps);
			} catch (Exception e) {
				log.fatal("Error:",e);
			}
		}
    }

    /* (non-Javadoc)
     * @see com.eaf.orig.shared.xrules.dao.XRulesScoringLogDAO#getLastScoring(java.lang.String)
     */
    public int getLastScoring(String applicationRecordId) throws XRulesScoringException {
        PreparedStatement ps = null;
		ResultSet rs = null;
		int scoring=0;
		Connection conn = null;
		try {
			//conn = Get Connection
			conn = getConnection();
			StringBuffer sql = new StringBuffer("");
			sql.append(" select SCORING_SCORE from xrules_scoring_log where seq=(");
            sql.append("  select max(seq)  from xrules_scoring_log   ");			 
			sql.append(" where APPLICATION_RECORD_ID =?   ) and APPLICATION_RECORD_ID =?");
			String dSql = String.valueOf(sql);
			log.debug("Sql=" + dSql);
			ps = conn.prepareStatement(dSql);
			rs = null;
			ps.setString(1, applicationRecordId);
			ps.setString(2, applicationRecordId);	 
			rs = ps.executeQuery();
			Vector vt = new Vector();
			if (rs.next()) {
			    scoring=rs.getInt(1);				 				 
			}
			return scoring;
		} catch (Exception e) {
			log.fatal("Error:",e);
			throw new XRulesScoringException(e.getMessage());
		} finally {
			try {
				closeConnection(conn, rs, ps);
			} catch (Exception e) {
				log.fatal("Error:",e);
			}
		}         
    }

}
