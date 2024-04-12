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
import com.eaf.orig.shared.xrules.dao.exception.XRulesDedupVehicleException;
import com.eaf.xrules.shared.constant.XRulesConstant;
import com.eaf.xrules.shared.model.XRulesDedupVehicleDataM;

/**
 * @author Sankom Sanpunya
 * 
 * Type: XRulesDedupVehicleDAOImpl
 */
public class XRulesDedupVehicleDAOImpl extends OrigObjectDAO implements
		XRulesDedupVehicleDAO {
	private static Logger log = Logger
			.getLogger(XRulesDedupVehicleDAOImpl.class);

	/**
	 *  
	 */
	public XRulesDedupVehicleDAOImpl() {
		super();
		
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.eaf.orig.shared.xrules.dao.XRulesDedupVehicleDAO#createModelXRulesDedupVenhicleM(com.eaf.xrules.shared.model.XRulesDedupVehicleDataM)
	 */
	public void createModelXRulesDedupVenhicleM(
			XRulesDedupVehicleDataM prmXRulesDedupVehicleDataM)
			throws XRulesDedupVehicleException {
		try {
			createTableXRULES_DEDUP_VEHICLE(prmXRulesDedupVehicleDataM);
		} catch (Exception e) {
			log.fatal("Error:",e);
			throw new XRulesDedupVehicleException(e.getMessage());
		}

	}

	/**
	 * @param prmXRulesDedupVehicleDataM
	 */
	private void createTableXRULES_DEDUP_VEHICLE(
			XRulesDedupVehicleDataM prmXRulesDedupVehicleDataM)
			throws XRulesDedupVehicleException {
		PreparedStatement ps = null;
		Connection conn = null;
		try {
			//conn = Get Connection
			conn = getConnection();
			StringBuffer sql = new StringBuffer("");
			sql.append("INSERT INTO XRULES_DEDUP_VEHICLE ");
			/*sql.append("( APPLICATION_RECORD_ID,CMPCDE,IDNO,APPLICATION_NO,ENGINE_NO  ");
			sql.append("  ,CHASSIS_NO ,REGISTER_NO,BRAND,MODEL,GEAR");
			sql.append("  ,UPDATE_DATE,UPDATE_BY,SEQ)");*/
			sql.append("( PERSONAL_ID,APPLICATION_NO,ENGINE_NO  ");
			sql.append("  ,CHASSIS_NO ,REGISTER_NO,BRAND,MODEL,GEAR");
			sql.append("  ,UPDATE_DATE,UPDATE_BY,SEQ)");

			sql.append(" VALUES(?,?,?  ,?,?,?,?,?   ,SYSDATE,?,? ) ");
			String dSql = String.valueOf(sql);
			log.debug("Sql=" + dSql);
			ps = conn.prepareStatement(dSql);
			/*ps.setString(1, prmXRulesDedupVehicleDataM.getApplicationRecordId());
			ps.setString(2, prmXRulesDedupVehicleDataM.getCmpCde());
			ps.setString(3, prmXRulesDedupVehicleDataM.getIdNo());
			ps.setString(4, prmXRulesDedupVehicleDataM.getApplicationNo());
			ps.setString(5, prmXRulesDedupVehicleDataM.getEngineNo());
			ps.setString(6, prmXRulesDedupVehicleDataM.getChassisNo());
			ps.setString(7, prmXRulesDedupVehicleDataM.getRegisterNo());
			ps.setString(8, prmXRulesDedupVehicleDataM.getBrand());
			ps.setString(9, prmXRulesDedupVehicleDataM.getModel());
			ps.setString(10, prmXRulesDedupVehicleDataM.getGear());
			ps.setString(11, prmXRulesDedupVehicleDataM.getUpdateBy());
			ps.setInt(12, prmXRulesDedupVehicleDataM.getSeq());*/
			
			ps.setString(1, prmXRulesDedupVehicleDataM.getPersonalID());
			ps.setString(2, prmXRulesDedupVehicleDataM.getApplicationNo());
			ps.setString(3, prmXRulesDedupVehicleDataM.getEngineNo());
			ps.setString(4, prmXRulesDedupVehicleDataM.getChassisNo());
			ps.setString(5, prmXRulesDedupVehicleDataM.getRegisterNo());
			ps.setString(6, prmXRulesDedupVehicleDataM.getBrand());
			ps.setString(7, prmXRulesDedupVehicleDataM.getModel());
			ps.setString(8, prmXRulesDedupVehicleDataM.getGear());
			ps.setString(9, prmXRulesDedupVehicleDataM.getUpdateBy());
			ps.setInt(11, prmXRulesDedupVehicleDataM.getSeq());
			ps.executeUpdate();
		} catch (Exception e) {
			log.fatal("Error:",e);
			throw new XRulesDedupVehicleException(e.getMessage());
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
	 * @see com.eaf.orig.shared.xrules.dao.XRulesDedupVehicleDAO#deleteModelXRulesDedupVenhicleM(com.eaf.xrules.shared.model.XRulesDedupVehicleDataM)
	 */
	public void deleteModelXRulesDedupVenhicleM(
			XRulesDedupVehicleDataM prmXRulesDedupVehicleDataM)
			throws XRulesDedupVehicleException {
		try {
			deleteTableXRULES_DEDUP_VEHICLE(prmXRulesDedupVehicleDataM);
		} catch (Exception e) {
			log.fatal("Error:",e);
			throw new XRulesDedupVehicleException(e.getMessage());
		}

	}

	/**
	 * @param XRulesDebBdDataM
	 */
	private void deleteTableXRULES_DEDUP_VEHICLE(
			XRulesDedupVehicleDataM XRulesDebBdDataM)
			throws XRulesDedupVehicleException {
		PreparedStatement ps = null;
		Connection conn = null;
		try {
			//conn = Get Connection
			conn = getConnection();
			StringBuffer sql = new StringBuffer("");
			sql.append("DELETE XRULES_DEDUP_VEHICLE ");
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
			throw new XRulesDedupVehicleException(e.getMessage());
		} finally {
			try {
				closeConnection(conn, ps);
			} catch (Exception e) {
				log.fatal("Error:",e);
				throw new XRulesDedupVehicleException(e.getMessage());
			}
		}

	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.eaf.orig.shared.xrules.dao.XRulesDedupVehicleDAO#loadModelXRulesDedupVenhicleM(java.lang.String)
	 */
	public Vector loadModelXRulesDedupVenhicleM(String personalID)
			throws XRulesDedupVehicleException {
		try {
			Vector vResult = selectTableXRULES_DEDUP_VEHICLE(personalID);
			return vResult;
		} catch (Exception e) {
			log.fatal("Error:",e);
			throw new XRulesDedupVehicleException(e.getMessage());
		}
	}

	/**
	 * @param applicationRecordId
	 * @return
	 */
	private Vector selectTableXRULES_DEDUP_VEHICLE(String personalID)
			throws XRulesDedupVehicleException {
		PreparedStatement ps = null;
		ResultSet rs = null;
		Connection conn = null;
		try {
			//conn = Get Connection
			conn = getConnection();
			StringBuffer sql = new StringBuffer("");
			sql.append("SELECT  PERSONAL_ID,APPLICATION_NO,ENGINE_NO");
			sql.append(" ,CHASSIS_NO ,REGISTER_NO,BRAND,MODEL,GEAR" );
			sql.append(" ,UPDATE_DATE,UPDATE_BY,SEQ ");
			sql.append("FROM XRULES_DEDUP_VEHICLE WHERE PERSONAL_ID=?");
			String dSql = String.valueOf(sql);
			log.debug("Sql=" + dSql);
			ps = conn.prepareStatement(dSql);
			rs = null;
			ps.setString(1, personalID);
			rs = ps.executeQuery();
			Vector vt = new Vector();
			while (rs.next()) {
				XRulesDedupVehicleDataM prmXRulesDedupVehicleDataM = new XRulesDedupVehicleDataM();
				prmXRulesDedupVehicleDataM.setPersonalID(rs.getString(1));
				prmXRulesDedupVehicleDataM.setApplicationNo(rs.getString(2));
				prmXRulesDedupVehicleDataM.setEngineNo(rs.getString(3));//				
				prmXRulesDedupVehicleDataM.setEngineNo(rs.getString(4));
				prmXRulesDedupVehicleDataM.setRegisterNo(rs.getString(5));
				prmXRulesDedupVehicleDataM.setBrand(rs.getString(6));
				prmXRulesDedupVehicleDataM.setModel(rs.getString(7));
				prmXRulesDedupVehicleDataM.setGear(rs.getString(8));//
				prmXRulesDedupVehicleDataM.setUpdateDate(rs.getTimestamp(9));
				prmXRulesDedupVehicleDataM.setUpdateBy(rs.getString(10));
				prmXRulesDedupVehicleDataM.setSeq(rs.getInt(11));
				vt.add(prmXRulesDedupVehicleDataM);
			}
			return vt;
		} catch (Exception e) {
			log.fatal("Error:",e);
			throw new XRulesDedupVehicleException(e.getMessage());
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
	 * @see com.eaf.orig.shared.xrules.dao.XRulesDedupVehicleDAO#saveUpdateModelXRulesDedupVenhicleM(com.eaf.xrules.shared.model.XRulesDedupVehicleDataM)
	 */
	public void saveUpdateModelXRulesDedupVenhicleM(
			XRulesDedupVehicleDataM prmXRulesDedupVehicleDataM)
			throws XRulesDedupVehicleException {
		double returnRows = 0;
		returnRows = updateTableXRULES_DEDUP_VEHICLE(prmXRulesDedupVehicleDataM);
		if (returnRows == 0) {
			log.debug("New record then can't update record in table XRULES_DEDUP_VEHICLE then call Insert method");
			createTableXRULES_DEDUP_VEHICLE(prmXRulesDedupVehicleDataM);
		}

	}

	/**
	 * @param XRulesDebBdDataM
	 * @return
	 */
	private double updateTableXRULES_DEDUP_VEHICLE(
			XRulesDedupVehicleDataM prmXRulesDedupVehicleDataM)
			throws XRulesDedupVehicleException {
		double returnRows = 0;
		PreparedStatement ps = null;
		Connection conn = null;
		try {
			//conn = Get Connection
			conn = getConnection();
			StringBuffer sql = new StringBuffer("");
			/*sql.append("UPDATE XRULES_DEDUP_VEHICLE ");
			sql.append(" SET  APPLICATION_NO=?,ENGINE_NO=?,CHASSIS_NO=? ,REGISTER_NO=?,BRAND=? ");
			sql.append(" ,MODEL=?,GEAR=?,UPDATE_DATE=SYSDATE ,UPDATE_BY=? ");
			sql.append(" WHERE APPLICATION_RECORD_ID = ? AND CMPCDE=? AND IDNO=? AND SEQ=?");*/
			sql.append("UPDATE XRULES_DEDUP_VEHICLE ");
			sql.append(" SET  APPLICATION_NO=?,ENGINE_NO=?,CHASSIS_NO=? ,REGISTER_NO=?,BRAND=? ");
			sql.append(" ,MODEL=?,GEAR=?,UPDATE_DATE=SYSDATE ,UPDATE_BY=? ");
			sql.append(" WHERE PERSONAL_ID=? AND SEQ=?");
			String dSql = String.valueOf(sql);
			log.debug("Sql=" + dSql);
			ps = conn.prepareStatement(dSql);			
			/*ps.setString(1, prmXRulesDedupVehicleDataM.getApplicationNo());
			ps.setString(2, prmXRulesDedupVehicleDataM.getEngineNo());
			ps.setString(3, prmXRulesDedupVehicleDataM.getChassisNo());
			ps.setString(4, prmXRulesDedupVehicleDataM.getRegisterNo());
			ps.setString(5, prmXRulesDedupVehicleDataM.getBrand());
			ps.setString(6, prmXRulesDedupVehicleDataM.getModel());
			ps.setString(7, prmXRulesDedupVehicleDataM.getGear());
			ps.setString(8, prmXRulesDedupVehicleDataM.getUpdateBy());
			ps.setString(9, prmXRulesDedupVehicleDataM.getApplicationRecordId());
			ps.setString(10, prmXRulesDedupVehicleDataM.getCmpCde());
			ps.setString(11, prmXRulesDedupVehicleDataM.getIdNo());
			ps.setInt(12, prmXRulesDedupVehicleDataM.getSeq());*/
			ps.setString(1, prmXRulesDedupVehicleDataM.getApplicationNo());
			ps.setString(2, prmXRulesDedupVehicleDataM.getEngineNo());
			ps.setString(3, prmXRulesDedupVehicleDataM.getChassisNo());
			ps.setString(4, prmXRulesDedupVehicleDataM.getRegisterNo());
			ps.setString(5, prmXRulesDedupVehicleDataM.getBrand());
			ps.setString(6, prmXRulesDedupVehicleDataM.getModel());
			ps.setString(7, prmXRulesDedupVehicleDataM.getGear());
			ps.setString(8, prmXRulesDedupVehicleDataM.getUpdateBy());
			ps.setString(9, prmXRulesDedupVehicleDataM.getPersonalID());
			ps.setInt(10, prmXRulesDedupVehicleDataM.getSeq());
			returnRows = ps.executeUpdate();
		} catch (Exception e) {
			log.fatal("Error:",e);
			throw new XRulesDedupVehicleException(e.getMessage());
		} finally {
			try {
				closeConnection(conn, ps);
			} catch (Exception e) {
				log.fatal("Error:",e);
			}
		}
		return returnRows;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.eaf.orig.shared.xrules.dao.XRulesDedupVehicleDAO#saveUpdateModelXRulesDedupVenhicleM(java.util.Vector)
	 */
	public void saveUpdateModelXRulesDedupVenhicleM(
			Vector vXRulesDedupVehicleDataM) throws XRulesDedupVehicleException {
		try {
			//update data
			if (vXRulesDedupVehicleDataM != null) {
				for (int i = 0; i < vXRulesDedupVehicleDataM.size(); i++) {
					this.saveUpdateModelXRulesDedupVenhicleM((XRulesDedupVehicleDataM) vXRulesDedupVehicleDataM.get(i));
				}
			}
			//delete table
			deleteSomeTableXRULES_DEDUP_VEHICLE(vXRulesDedupVehicleDataM);
		} catch (Exception e) {
			log.fatal("Error:",e);
			throw new XRulesDedupVehicleException(e.getMessage());
		}

	}

	/**
	 * @param vXRulesDedupVehicleDataM
	 */
	private void deleteSomeTableXRULES_DEDUP_VEHICLE(
			Vector vXRulesDedupVehicleDataM) throws XRulesDedupVehicleException {
		PreparedStatement ps = null;
		Connection conn = null;
		try {
			//conn = Get Connection
			if (vXRulesDedupVehicleDataM == null) {
				log.debug("XRulesDedupVehicleDAOImpl-->deleteSomeTableXRULES_DEDUP_VEHICLE vXRulesDedupVehicleDataM=null");
				return ;
			}
			if (vXRulesDedupVehicleDataM.size() < 1) {
				log.debug("XRulesDedupVehicleDAOImpl-->deleteSomeTableXRULES_DEDUP_VEHICLE vXRulesDedupVehicleDataM=0");
				return;
			}
			conn = getConnection();
			StringBuffer sql = new StringBuffer("");
			sql.append("DELETE XRULES_DEDUP_VEHICLE ");
			//sql.append(" WHERE APPLICATION_RECORD_ID = ? AND CMPCDE=? AND IDNO=? ");
			sql.append(" WHERE PERSONAL_ID=? ");
			sql.append(" AND SEQ not in(");
			XRulesDedupVehicleDataM prmXRulesDebtDbDataM;
			prmXRulesDebtDbDataM = (XRulesDedupVehicleDataM) vXRulesDedupVehicleDataM.get(0);
			sql.append(prmXRulesDebtDbDataM.getSeq());
			for (int i = 1; i < vXRulesDedupVehicleDataM.size()&&i<XRulesConstant.limitDeleteSQLParam; i++) {
				prmXRulesDebtDbDataM = (XRulesDedupVehicleDataM) vXRulesDedupVehicleDataM.get(i);
				sql.append("," + prmXRulesDebtDbDataM.getSeq());
			}
			sql.append(" )");
			String dSql = String.valueOf(sql);
			log.debug("Sql=" + dSql);
			ps = conn.prepareStatement(dSql);
			ps.setString(1, prmXRulesDebtDbDataM.getPersonalID());
			ps.executeUpdate();

		} catch (Exception e) {
			log.fatal("Error:",e);
			throw new XRulesDedupVehicleException(e.getMessage());
		} finally {
			try {
				closeConnection(conn, ps);
			} catch (Exception e) {
				log.fatal("Error:",e);
				throw new XRulesDedupVehicleException(e.getMessage());
			}
		}

	}
	
    /* (non-Javadoc)
     * @see com.eaf.orig.shared.xrules.dao.XRulesDedupVehicleDAO#deleteModelXRulesDedupVenhicleM(java.lang.String, java.lang.String, java.util.Vector)
     */
    public void deleteModelXRulesDedupVenhicleM(String applicationRecordId, String cmpCde, Vector idNoVects) throws XRulesDedupVehicleException {
        PreparedStatement ps = null;
        Connection conn = null;
		try {
			//conn = Get Connection
			if (idNoVects == null) {
				log
						.debug("XRulesDedupVehicleDAOImpl-->deleteModelXRulesDedupVenhicleM  idno Vects=null");
				return;
			}
			if (idNoVects.size() < 1) {
				log
						.debug("XRulesDedupVehicleDAOImpl-->deleteModelXRulesDedupVenhicleM  Idno size =0");
				return;
			}
			conn = getConnection();
			StringBuffer sql = new StringBuffer("");
			sql.append("DELETE XRULES_DEDUP_VEHICLE ");
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
			throw new XRulesDedupVehicleException(e.getMessage());
		} finally {
			try {
				closeConnection(conn, ps);
			} catch (Exception e) {
				log.fatal("Error:",e);
				throw new XRulesDedupVehicleException(e.getMessage());
			}
		}

    }
}
