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
import com.eaf.orig.shared.xrules.dao.exception.XRulesBlacklistVehicleException;
import com.eaf.xrules.shared.constant.XRulesConstant;
import com.eaf.xrules.shared.model.XRulesBlacklistVehicleDataM;

/**
 * @author Sankom Sanpunya
 * 
 * Type: XRulesBlacklistVehicleDAOImpl
 */
public class XRulesBlacklistVehicleDAOImpl extends OrigObjectDAO implements
		XRulesBlacklistVehicleDAO {
	private static Logger log = Logger.getLogger(XRulesBlacklistDAOImpl.class);

	/**
	 *  
	 */
	public XRulesBlacklistVehicleDAOImpl() {
		super();
		
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.eaf.orig.shared.xrules.dao.XRulesBlacklistVehicleDAO#createModelXRulesBlacklistVehicleM(com.eaf.xrules.shared.model.XRulesBlacklistVehicleDataM)
	 */
	public void createModelXRulesBlacklistVehicleM(
			XRulesBlacklistVehicleDataM prmXRulesBlacklistVehicleDataM)
			throws XRulesBlacklistVehicleException {
		try {
			createTableXRULES_BLACKLIST_VEHICLE(prmXRulesBlacklistVehicleDataM);
		} catch (Exception e) {
			log.fatal("Error:",e);
			throw new XRulesBlacklistVehicleException(e.getMessage());
		}

	}

	/**
	 * @param prmXRulesBlacklistVehicleDataM
	 */
	private void createTableXRULES_BLACKLIST_VEHICLE(
			XRulesBlacklistVehicleDataM prmXRulesBlacklistVehicleDataM)
			throws XRulesBlacklistVehicleException {
		PreparedStatement ps = null;
		Connection conn = null;
		try {
			//conn = Get Connection
			conn = getConnection();
			StringBuffer sql = new StringBuffer("");
			/*sql.append("INSERT INTO XRULES_BLACKLIST_VEHICLE ");
			sql.append("( APPLICATION_RECORD_ID,CMPCDE,IDNO,CHASSIS_NO,ENGINE_NO  ");
			sql.append("  ,BRAND,MODEL,BL_SOURCE,BL_REASON,BL_DETAILS   ");
			sql.append(" ,UPDATE_DATE ,UPDATE_BY,SEQ,BL_FLAG,REGISTRATION_NUMBER  )");*/
			sql.append("INSERT INTO XRULES_BLACKLIST_VEHICLE ");
			sql.append("( PERSONAL_ID,CHASSIS_NO,ENGINE_NO  ");
			sql.append("  ,BRAND,MODEL,BL_SOURCE,BL_REASON,BL_DETAILS   ");
			sql.append(" ,UPDATE_DATE ,UPDATE_BY,SEQ,BL_FLAG,REGISTRATION_NUMBER  )");
			
			sql.append(" VALUES(?,?,?,?,?   ,?,?,?,?,?   ,SYSDATE,?,?,?,?   ) ");
			String dSql = String.valueOf(sql);
			log.debug("Sql=" + dSql);
			ps = conn.prepareStatement(dSql);
            /*ps.setString(1,prmXRulesBlacklistVehicleDataM.getApplicationRecordId());
            ps.setString(2,prmXRulesBlacklistVehicleDataM.getCmpCde());
            ps.setString(3,prmXRulesBlacklistVehicleDataM.getIdNo());
            ps.setString(4,prmXRulesBlacklistVehicleDataM.getChassisNo());
            ps.setString(5,prmXRulesBlacklistVehicleDataM.getEngineNo());
            ps.setString(6,prmXRulesBlacklistVehicleDataM.getBrand());
            ps.setString(7,prmXRulesBlacklistVehicleDataM.getModel());
            ps.setString(8,prmXRulesBlacklistVehicleDataM.getBLSource());
            ps.setString(9,prmXRulesBlacklistVehicleDataM.getBLReason());
            ps.setString(10,prmXRulesBlacklistVehicleDataM.getBLDetail());
            ps.setString(11,prmXRulesBlacklistVehicleDataM.getUpdateBy());
            ps.setInt(12,prmXRulesBlacklistVehicleDataM.getSeq());
            ps.setString(13,prmXRulesBlacklistVehicleDataM.getBLFlag());
            ps.setString(14,prmXRulesBlacklistVehicleDataM.getRegistrationNo());*/
			
			ps.setString(1,prmXRulesBlacklistVehicleDataM.getPersonalID());
            ps.setString(2,prmXRulesBlacklistVehicleDataM.getChassisNo());
            ps.setString(3,prmXRulesBlacklistVehicleDataM.getEngineNo());
            ps.setString(4,prmXRulesBlacklistVehicleDataM.getBrand());
            ps.setString(5,prmXRulesBlacklistVehicleDataM.getModel());
            ps.setString(6,prmXRulesBlacklistVehicleDataM.getBLSource());
            ps.setString(7,prmXRulesBlacklistVehicleDataM.getBLReason());
            ps.setString(8,prmXRulesBlacklistVehicleDataM.getBLDetail());
            ps.setString(9,prmXRulesBlacklistVehicleDataM.getUpdateBy());
            ps.setInt(10,prmXRulesBlacklistVehicleDataM.getSeq());
            ps.setString(11,prmXRulesBlacklistVehicleDataM.getBLFlag());
            ps.setString(12,prmXRulesBlacklistVehicleDataM.getRegistrationNo());
			ps.executeUpdate();
		} catch (Exception e) {
			log.fatal("Error:",e);
			throw new XRulesBlacklistVehicleException(e.getMessage());
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
	 * @see com.eaf.orig.shared.xrules.dao.XRulesBlacklistVehicleDAO#deleteModelXRulesBlacklistVehicleM(com.eaf.xrules.shared.model.XRulesBlacklistVehicleDataM)
	 */
	public void deleteModelXRulesBlacklistVehicleM(
			XRulesBlacklistVehicleDataM prmXRulesBlacklistVehicleDataM)
			throws XRulesBlacklistVehicleException {
		try {
			deleteTableXRULES_BLACKLIST_VEHICLE(prmXRulesBlacklistVehicleDataM);
		} catch (Exception e) {
			log.fatal("Error:",e);
			throw new XRulesBlacklistVehicleException(e.getMessage());
		}

	}

	/**
	 * @param prmXRulesBlacklistVehicleDataM
	 */
	private void deleteTableXRULES_BLACKLIST_VEHICLE(
			XRulesBlacklistVehicleDataM prmXRulesBlacklistVehicleDataM)
			throws XRulesBlacklistVehicleException {
		PreparedStatement ps = null;
		Connection conn = null;
		try {
			//conn = Get Connection
			conn = getConnection();
			StringBuffer sql = new StringBuffer("");
			sql.append("DELETE XRULES_BLACKLIST_VEHICLE ");
			sql.append(" WHERE APPLICATION_RECORD_ID = ? AND PERSONAL_ID=? AND SEQ=?");
			String dSql = String.valueOf(sql);
			log.debug("Sql=" + dSql);
			ps = conn.prepareStatement(dSql);
			/*ps.setString(1, prmXRulesBlacklistVehicleDataM.getApplicationRecordId());
			ps.setString(2, prmXRulesBlacklistVehicleDataM.getCmpCde());
			ps.setString(3, prmXRulesBlacklistVehicleDataM.getIdNo());
			ps.setInt(4, prmXRulesBlacklistVehicleDataM.getSeq());*/
			ps.setString(1, prmXRulesBlacklistVehicleDataM.getApplicationRecordId());
			ps.setString(2, prmXRulesBlacklistVehicleDataM.getPersonalID());
			ps.setInt(3, prmXRulesBlacklistVehicleDataM.getSeq());
			ps.executeUpdate();

		} catch (Exception e) {
			log.fatal("Error:",e);
			throw new XRulesBlacklistVehicleException(e.getMessage());
		} finally {
			try {
				closeConnection(conn, ps);
			} catch (Exception e) {
				log.fatal("Error:",e);
				throw new XRulesBlacklistVehicleException(e.getMessage());
			}
		}

	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.eaf.orig.shared.xrules.dao.XRulesBlacklistVehicleDAO#loadModelXRulesBlacklistVehicleM(java.lang.String)
	 */
	public Vector loadModelXRulesBlacklistVehicleM(String personalID)
			throws XRulesBlacklistVehicleException {
		try {
			Vector vResult = selectTableXRULES_BLACKLIST_VEHICLE(personalID);
			return vResult;
		} catch (Exception e) {
			log.fatal("Error:",e);
			throw new XRulesBlacklistVehicleException(e.getMessage());
		}
	}

	/**
	 * @param applicationRecordId
	 * @return
	 */
	private Vector selectTableXRULES_BLACKLIST_VEHICLE(String personalID) throws XRulesBlacklistVehicleException {
		PreparedStatement ps = null;
		ResultSet rs = null;
		Connection conn = null;
		try {
			//conn = Get Connection
			conn = getConnection();
			StringBuffer sql = new StringBuffer("");
			/*sql.append("SELECT  CMPCDE,IDNO,CHASSIS_NO,ENGINE_NO");
			sql.append("  ,BRAND,MODEL,BL_SOURCE,BL_REASON,BL_DETAILS   ");
			sql.append(" ,UPDATE_DATE ,UPDATE_BY,SEQ,BL_FLAG,REGISTRATION_NUMBER  ");
			sql.append("FROM XRULES_BLACKLIST_VEHICLE WHERE APPLICATION_RECORD_ID = ? AND CMPCDE=? AND IDNO=? ");*/
			sql.append("SELECT  PERSONAL_ID,CHASSIS_NO,ENGINE_NO");
			sql.append("  ,BRAND,MODEL,BL_SOURCE,BL_REASON,BL_DETAILS   ");
			sql.append(" ,UPDATE_DATE ,UPDATE_BY,SEQ,BL_FLAG,REGISTRATION_NUMBER  ");
			sql.append("FROM XRULES_BLACKLIST_VEHICLE WHERE PERSONAL_ID=? ");
			String dSql = String.valueOf(sql);
			log.debug("Sql=" + dSql);
			ps = conn.prepareStatement(dSql);
			rs = null;
			ps.setString(1, personalID);
			rs = ps.executeQuery();
			Vector vt = new Vector();
			while (rs.next()) {
				XRulesBlacklistVehicleDataM prmXRulesBlacklistVehicleDataM = new XRulesBlacklistVehicleDataM();
				prmXRulesBlacklistVehicleDataM.setPersonalID(rs.getString(1));
				prmXRulesBlacklistVehicleDataM.setChassisNo(rs.getString(2));
				prmXRulesBlacklistVehicleDataM.setEngineNo(rs.getString(3));
				prmXRulesBlacklistVehicleDataM.setBrand(rs.getString(4));
				prmXRulesBlacklistVehicleDataM.setModel(rs.getString(5));
				prmXRulesBlacklistVehicleDataM.setBLSource(rs.getString(6));
				prmXRulesBlacklistVehicleDataM.setBLReason(rs.getString(7));
				prmXRulesBlacklistVehicleDataM.setBLDetail(rs.getString(8));
				prmXRulesBlacklistVehicleDataM.setUpdateDate(rs.getTimestamp(9));
				prmXRulesBlacklistVehicleDataM.setUpdateBy(rs.getString(10));
				prmXRulesBlacklistVehicleDataM.setSeq(rs.getInt(11));
				prmXRulesBlacklistVehicleDataM.setBLFlag(rs.getString(12));
				prmXRulesBlacklistVehicleDataM.setRegistrationNo(rs.getString(13));
				vt.add(prmXRulesBlacklistVehicleDataM);
			}
			return vt;
		} catch (Exception e) {
			log.fatal("Error:",e);
			throw new XRulesBlacklistVehicleException(e.getMessage());
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
	 * @see com.eaf.orig.shared.xrules.dao.XRulesBlacklistVehicleDAO#saveUpdateModelXRulesBlacklistVehicleM(com.eaf.xrules.shared.model.XRulesBlacklistVehicleDataM)
	 */
	public void saveUpdateModelXRulesBlacklistVehicleM(
			Vector vXRulesBlacklistVehicleDataM)
			throws XRulesBlacklistVehicleException {
		try {
			//update data
			if (vXRulesBlacklistVehicleDataM != null) {
				for (int i = 0; i < vXRulesBlacklistVehicleDataM.size(); i++) {
					this.saveUpdateModelXRulesBlacklistVehicleM((XRulesBlacklistVehicleDataM) vXRulesBlacklistVehicleDataM.get(i));
				}
			}
			//delete table
			deleteSomeTableXRULES_BLACKLIST_VEHICLE(vXRulesBlacklistVehicleDataM);
		} catch (Exception e) {
			log.fatal("Error:",e);
			throw new XRulesBlacklistVehicleException(e.getMessage());
		}

	}

	/**
	 * @param rulesBlacklistDataM
	 */
	private void deleteSomeTableXRULES_BLACKLIST_VEHICLE(
			Vector vXRulesBlacklistVehicleDataM)
			throws XRulesBlacklistVehicleException {
		PreparedStatement ps = null;
		Connection conn = null;
		try {
			//conn = Get Connection
			if (vXRulesBlacklistVehicleDataM == null) {
				log.debug("XRulesBlacklistVehicleDAOImpl-->deleteSomeTableXRULES_BLACKLIST_VEHICLE vXRulesBlacklistVehicleDataM=null");
				return;
			}
			if (vXRulesBlacklistVehicleDataM.size() < 1) {
				log.debug("XRulesBlacklistVehicleDAOImpl-->deleteSomeTableXRULES_BLACKLIST_VEHICLE vXRulesBlacklistVehicleDataM=0");
				return;
			}
			conn = getConnection();
			StringBuffer sql = new StringBuffer("");
			sql.append("DELETE XRULES_BLACKLIST_VEHICLE ");
			//sql.append(" WHERE APPLICATION_RECORD_ID = ? AND CMPCDE=? AND IDNO=? ");
			sql.append(" WHERE PERSONAL_ID=? ");
			sql.append(" AND SEQ not in(");
			XRulesBlacklistVehicleDataM prmXRuleBlackListVehicleDataM;
			prmXRuleBlackListVehicleDataM = (XRulesBlacklistVehicleDataM) vXRulesBlacklistVehicleDataM
					.get(0);
			sql.append(prmXRuleBlackListVehicleDataM.getSeq());
			for (int i = 1; i < vXRulesBlacklistVehicleDataM.size()&&i<XRulesConstant.limitDeleteSQLParam; i++) {
				prmXRuleBlackListVehicleDataM = (XRulesBlacklistVehicleDataM) vXRulesBlacklistVehicleDataM
						.get(i);
				sql.append("," + prmXRuleBlackListVehicleDataM.getSeq());
			}
			sql.append(" )");
			String dSql = String.valueOf(sql);
			log.debug("Sql=" + dSql);
			ps = conn.prepareStatement(dSql);
			/*ps.setString(1, prmXRuleBlackListVehicleDataM.getApplicationRecordId());
			ps.setString(2, prmXRuleBlackListVehicleDataM.getCmpCde());
			ps.setString(3, prmXRuleBlackListVehicleDataM.getIdNo());*/
			ps.setString(1, prmXRuleBlackListVehicleDataM.getPersonalID());
			ps.executeUpdate();

		} catch (Exception e) {
			log.fatal("Error:",e);
			throw new XRulesBlacklistVehicleException(e.getMessage());
		} finally {
			try {
				closeConnection(conn, ps);
			} catch (Exception e) {
				log.fatal("Error:",e);
				throw new XRulesBlacklistVehicleException(e.getMessage());
			}
		}

	}

	public void saveUpdateModelXRulesBlacklistVehicleM(
			XRulesBlacklistVehicleDataM prmXRulesBlacklistVehicleDataM)
			throws XRulesBlacklistVehicleException {
		double returnRows = 0;
		returnRows = updateTableXRULES_BLACKLIST_VEHICLE(prmXRulesBlacklistVehicleDataM);
		if (returnRows == 0) {
			log
					.debug("New record then can't update record in table XRULES_BLACKLIST_VEHICLE then call Insert method");
			createTableXRULES_BLACKLIST_VEHICLE(prmXRulesBlacklistVehicleDataM);
		}

	}

	/**
	 * @param prmXRulesBlacklistDataM
	 * @return
	 */
	private double updateTableXRULES_BLACKLIST_VEHICLE(
			XRulesBlacklistVehicleDataM prmXRulesBlacklistVehicleDataM)
			throws XRulesBlacklistVehicleException {
		double returnRows = 0;
		PreparedStatement ps = null;
		Connection conn = null;
		try {
			//conn = Get Connection
			conn = getConnection();
			StringBuffer sql = new StringBuffer("");
			sql.append("UPDATE XRULES_BLACKLIST_VEHICLE ");
			sql.append(" SET  CHASSIS_NO=?,ENGINE_NO=?,BRAND=?,MODEL=?,BL_SOURCE=?");
			sql.append("  ,BL_REASON=?,BL_DETAILS=?,UPDATE_DATE=SYSDATE ,UPDATE_BY=?,BL_FLAG=?,REGISTRATION_NUMBER=?  ");
			//sql.append(" WHERE APPLICATION_RECORD_ID = ? AND CMPCDE=? AND IDNO=? AND SEQ=?");
			sql.append(" WHERE PERSONAL_ID=? AND SEQ=?");
			String dSql = String.valueOf(sql);
			log.debug("Sql=" + dSql);
			ps = conn.prepareStatement(dSql);			
            /*ps.setString(1,prmXRulesBlacklistVehicleDataM.getChassisNo());
            ps.setString(2,prmXRulesBlacklistVehicleDataM.getEngineNo());
            ps.setString(3,prmXRulesBlacklistVehicleDataM.getBrand());
            ps.setString(4,prmXRulesBlacklistVehicleDataM.getModel());
            ps.setString(5,prmXRulesBlacklistVehicleDataM.getBLSource());
            ps.setString(6,prmXRulesBlacklistVehicleDataM.getBLReason());
            ps.setString(7,prmXRulesBlacklistVehicleDataM.getBLDetail());
            ps.setString(8,prmXRulesBlacklistVehicleDataM.getUpdateBy());            
            ps.setString(9,prmXRulesBlacklistVehicleDataM.getBLFlag());
            ps.setString(10,prmXRulesBlacklistVehicleDataM.getRegistrationNo());
            ps.setString(11,prmXRulesBlacklistVehicleDataM.getApplicationRecordId());
            ps.setString(12,prmXRulesBlacklistVehicleDataM.getCmpCde());
            ps.setString(13,prmXRulesBlacklistVehicleDataM.getIdNo());
            ps.setInt(14,prmXRulesBlacklistVehicleDataM.getSeq());*/
			ps.setString(1,prmXRulesBlacklistVehicleDataM.getChassisNo());
            ps.setString(2,prmXRulesBlacklistVehicleDataM.getEngineNo());
            ps.setString(3,prmXRulesBlacklistVehicleDataM.getBrand());
            ps.setString(4,prmXRulesBlacklistVehicleDataM.getModel());
            ps.setString(5,prmXRulesBlacklistVehicleDataM.getBLSource());
            ps.setString(6,prmXRulesBlacklistVehicleDataM.getBLReason());
            ps.setString(7,prmXRulesBlacklistVehicleDataM.getBLDetail());
            ps.setString(8,prmXRulesBlacklistVehicleDataM.getUpdateBy());            
            ps.setString(9,prmXRulesBlacklistVehicleDataM.getBLFlag());
            ps.setString(10,prmXRulesBlacklistVehicleDataM.getRegistrationNo());
            ps.setString(11,prmXRulesBlacklistVehicleDataM.getPersonalID());
            ps.setInt(12,prmXRulesBlacklistVehicleDataM.getSeq());
			returnRows = ps.executeUpdate();
		} catch (Exception e) {
			log.fatal("Error:",e);
			throw new XRulesBlacklistVehicleException(e.getMessage());
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
     * @see com.eaf.orig.shared.xrules.dao.XRulesBlacklistVehicleDAO#deleteModelXRulesBlacklistVehicleM(java.lang.String, java.lang.String, java.util.Vector)
     */
    public void deleteModelXRulesBlacklistVehicleM(String applicationRecordId, String cmpCde, Vector idNoVects) throws XRulesBlacklistVehicleException {
        PreparedStatement ps = null;
        Connection conn = null;
		try {
			//conn = Get Connection
			if (idNoVects == null) {
				log
						.debug("XRulesBlacklistVehicleDAOImpl-->deleteModelXRulesBlacklistVehicleM  idno Vects=null");
				return;
			}
			if (idNoVects.size() < 1) {
				log
						.debug("XRulesBlacklistVehicleDAOImpl-->deleteModelXRulesBlacklistVehicleM  Idno size =0");
				return;
			}
			conn = getConnection();
			StringBuffer sql = new StringBuffer("");
			sql.append("DELETE XRULES_BLACKLIST_VEHICLE ");
			sql
					.append(" WHERE APPLICATION_RECORD_ID = ? AND CMPCDE=?   ");
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
			throw new XRulesBlacklistVehicleException(e.getMessage());
		} finally {
			try {
				closeConnection(conn, ps);
			} catch (Exception e) {
				log.fatal("Error:",e);
				throw new XRulesBlacklistVehicleException(e.getMessage());
			}
		}

    }
}
