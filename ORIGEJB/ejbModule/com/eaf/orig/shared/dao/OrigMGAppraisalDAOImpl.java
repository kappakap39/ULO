/*
 * Created on Mar 15, 2010
 * Created by wichaya
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

import org.apache.log4j.Logger;

import com.eaf.orig.shared.dao.exceptions.OrigApplicationMException;
//import com.eaf.orig.shared.dao.utility.exception.UniqueIDGeneratorException;
import com.eaf.orig.shared.key.dao.exception.UniqueIDGeneratorException;
import com.eaf.orig.shared.model.AppraisalDataM;

/**
 * @author wichaya
 *
 * Type: OrigMGAppraisalDAOImpl
 */
public class OrigMGAppraisalDAOImpl extends OrigObjectDAO implements OrigMGAppraisalDAO {
    private static Logger log = Logger.getLogger(OrigMGAppraisalDAOImpl.class);
    /* (non-Javadoc)
     * @see com.eaf.orig.shared.dao.OrigMGAppraisalDAO#createAppraisalDataM(com.eaf.orig.shared.model.AppraisalDataM, java.lang.String, java.lang.String)
     */
    public void createAppraisalDataM(AppraisalDataM appraisalDataM, String collatera_id, String username) throws OrigApplicationMException,
            UniqueIDGeneratorException {
        insertORIG_MG_APPRAISAL(appraisalDataM, collatera_id, username);
    }
    
    private void insertORIG_MG_APPRAISAL(AppraisalDataM appraisalDataM, String collatera_id, String username)throws OrigApplicationMException{
        PreparedStatement ps = null;
        Connection conn = null;
		try {
			//conn = Get Connection
			conn = getConnection();
			StringBuffer sql = new StringBuffer("");
			sql.append("INSERT INTO ORIG_MG_APPRAISAL ");
			sql.append("(MG_COLLATERAL_ID, APPRAISAL_PERFORMED_BY, APPRAISAL_NAME, APPRAISAL_DATE, TOTAL_CONTACT_PRICE, TOTAL_FMV, TOTAL_LOAN_ABLE_VALUE, LTV, UNACCEPTABLE_COLLATERAL, PURPOSE, ACCOUNT_NAME, RECEIVED_DATE, INSPECTED_DATE, REGISTERED_OWNER, LOCATION, TCT, ENTERED_AT, AREA, LIENS_AND_ENCUMBRANCES, ISSUED_DATE, LESS_DEPRECIATION, LESS_DEPRECIATION_FMV, IM_FMV_LAND, IM_AV_LAND, IM_LV_LAND, IM_FMV_BUILDING, IM_AV_BUILDING, IM_LV_BUILDING, IM_TOTAL_FMV, IM_TOTAL_AV, IM_TOTAL_LV, OT_FMV_LAND, OT_AV_LAND, OT_LV_LAND, OT_FMV_BUILDING, OT_AV_BUILDING, OT_LV_BUILDING, OT_TOTAL_FMV, OT_TOTAL_AV, OT_TOTAL_LV, FMV_OF_TOTAL_RCN, AV_OF_TOTAL_RCN, LV_OF_TOTAL_RCN, SUM_FMV_LAND, SUM_AV_LAND, SUM_LV_LAND, SUM_FMV_BUILDING, SUM_AV_BUILDING, SUM_LV_BUILDING, SUM_TOTAL_FMV, SUM_TOTAL_AV, SUM_TOTAL_LV, CREATE_DATE, CREATE_BY, UPDATE_DATE, UPDATE_BY) ");
			sql.append(" VALUES(?,?,?,?,?  ,?,?,?,?,?  ,?,?,?,?,?  ,?,?,?,?,?  ,?,?,?,?,?  ,?,?,?,?,?  ,?,?,?,?,?  ,?,?,?,?,?  ,?,?,?,?,?  ,?,?,?,?,?  ,?,?,SYSDATE,?,SYSDATE,? ) ");
			String dSql = String.valueOf(sql);
			log.debug("Sql=" + dSql);
			ps = conn.prepareStatement(dSql);
			
			ps.setString(1, collatera_id);
			ps.setString(2, appraisalDataM.getAppraisalPerformedBy());
			ps.setString(3, appraisalDataM.getAppraisalName());
			ps.setDate(4, this.parseDate(appraisalDataM.getAppraisalDate()));
			ps.setBigDecimal(5, appraisalDataM.getTotalContactPrice());
			ps.setBigDecimal(6, appraisalDataM.getTotalFMV());
			ps.setBigDecimal(7, appraisalDataM.getTotalLoanableValue());
			ps.setBigDecimal(8, appraisalDataM.getLtv());
			ps.setString(9, appraisalDataM.getUnacceptableCollateral());
			ps.setString(10, appraisalDataM.getPurpose());
			ps.setString(11, appraisalDataM.getAccountName());
			ps.setTimestamp(12, appraisalDataM.getReceivedDate());
			ps.setTimestamp(13, appraisalDataM.getInspectedDate());
			ps.setString(14, appraisalDataM.getRegisteredOwner());
			ps.setString(15, appraisalDataM.getLocation());
			ps.setString(16, appraisalDataM.getTct());
			ps.setString(17, appraisalDataM.getEnteredAt());
			ps.setBigDecimal(18, appraisalDataM.getArea());
			ps.setString(19, appraisalDataM.getLiensAndEncumbrances());
			ps.setTimestamp(20, appraisalDataM.getIssuedDate());
			ps.setBigDecimal(21, appraisalDataM.getLessDepreciation());
			ps.setBigDecimal(22, appraisalDataM.getLessDepreciationFMV());
			ps.setBigDecimal(23, appraisalDataM.getImFMVLand());
			ps.setBigDecimal(24, appraisalDataM.getImAVLand());
			ps.setBigDecimal(25, appraisalDataM.getImLVLand());
			ps.setBigDecimal(26, appraisalDataM.getImFMVBuilding());
			ps.setBigDecimal(27, appraisalDataM.getImAVBuilding());
			ps.setBigDecimal(28, appraisalDataM.getImLVBuilding());
			ps.setBigDecimal(29, appraisalDataM.getImTotalFMV());
			ps.setBigDecimal(30, appraisalDataM.getImTotalAV());
			ps.setBigDecimal(31, appraisalDataM.getImTotalLV());
			ps.setBigDecimal(32, appraisalDataM.getOtFMVLand());
			ps.setBigDecimal(33, appraisalDataM.getOtAVLand());
			ps.setBigDecimal(34, appraisalDataM.getOtLVLand());
			ps.setBigDecimal(35, appraisalDataM.getOtFMVBuilding());
			ps.setBigDecimal(36, appraisalDataM.getOtAVBuilding());
			ps.setBigDecimal(37, appraisalDataM.getOtLVBuilding());
			ps.setBigDecimal(38, appraisalDataM.getOtTotalFMV());
			ps.setBigDecimal(39, appraisalDataM.getOtTotalAV());
			ps.setBigDecimal(40, appraisalDataM.getOtTotalLV());
			ps.setBigDecimal(41, appraisalDataM.getFmvOfTotalRCN());
			ps.setBigDecimal(42, appraisalDataM.getAvOfTotalRCN());
			ps.setBigDecimal(43, appraisalDataM.getLvOfTotalRCN());
			ps.setBigDecimal(44, appraisalDataM.getSumFMVLand());
			ps.setBigDecimal(45, appraisalDataM.getSumAVLand());
			ps.setBigDecimal(46, appraisalDataM.getSumLVLand());
			ps.setBigDecimal(47, appraisalDataM.getSumFMVBuilding());
			ps.setBigDecimal(48, appraisalDataM.getSumAVBuilding());
			ps.setBigDecimal(49, appraisalDataM.getSumLVBuilding());
			ps.setBigDecimal(50, appraisalDataM.getSumTotalFMV());
			ps.setBigDecimal(51, appraisalDataM.getSumTotalAV());
			ps.setBigDecimal(52, appraisalDataM.getSumTotalLV());
			ps.setString(53, username);
			ps.setString(54, username);
			
			ps.executeUpdate();
		} catch (Exception e) {
			log.fatal(e.getLocalizedMessage());
			throw new OrigApplicationMException(e.getMessage());
		} finally {
			try {
				closeConnection(conn, ps);
			} catch (Exception e) {
				log.fatal(e.getLocalizedMessage());
			}
		}
    }

    /* (non-Javadoc)
     * @see com.eaf.orig.shared.dao.OrigMGAppraisalDAO#updateAppraisalDataM(com.eaf.orig.shared.model.AppraisalDataM, java.lang.String, java.lang.String)
     */
    public void updateAppraisalDataM(AppraisalDataM appraisalDataM, String collatera_id, String username) throws OrigApplicationMException {
        // TODO Auto-generated method stub

    }

    /* (non-Javadoc)
     * @see com.eaf.orig.shared.dao.OrigMGAppraisalDAO#loadAppraisalDataM(java.lang.String)
     */
    public AppraisalDataM loadAppraisalDataM(String collateral_id) throws OrigApplicationMException {
        return selectORIG_MG_APPRAISAL(collateral_id);
    }
    
    private AppraisalDataM selectORIG_MG_APPRAISAL(String collateral_id) throws OrigApplicationMException {
		PreparedStatement ps = null;
		ResultSet rs = null;
		Connection conn = null;
		try {
			//conn = Get Connection
			conn = getConnection();
			StringBuffer sql = new StringBuffer("");
			sql.append("SELECT MG_COLLATERAL_ID, APPRAISAL_PERFORMED_BY, APPRAISAL_NAME, APPRAISAL_DATE, TOTAL_CONTACT_PRICE, TOTAL_FMV, TOTAL_LOAN_ABLE_VALUE, LTV, UNACCEPTABLE_COLLATERAL, PURPOSE, ACCOUNT_NAME, RECEIVED_DATE, INSPECTED_DATE, REGISTERED_OWNER, LOCATION, TCT, ENTERED_AT, AREA, LIENS_AND_ENCUMBRANCES, ISSUED_DATE, LESS_DEPRECIATION, LESS_DEPRECIATION_FMV, IM_FMV_LAND, IM_AV_LAND, IM_LV_LAND, IM_FMV_BUILDING, IM_AV_BUILDING, IM_LV_BUILDING, IM_TOTAL_FMV, IM_TOTAL_AV, IM_TOTAL_LV, OT_FMV_LAND, OT_AV_LAND, OT_LV_LAND, OT_FMV_BUILDING, OT_AV_BUILDING, OT_LV_BUILDING, OT_TOTAL_FMV, OT_TOTAL_AV, OT_TOTAL_LV, FMV_OF_TOTAL_RCN, AV_OF_TOTAL_RCN, LV_OF_TOTAL_RCN, SUM_FMV_LAND, SUM_AV_LAND, SUM_LV_LAND, SUM_FMV_BUILDING, SUM_AV_BUILDING, SUM_LV_BUILDING, SUM_TOTAL_FMV, SUM_TOTAL_AV, SUM_TOTAL_LV, CREATE_DATE, CREATE_BY, UPDATE_DATE, UPDATE_BY ");
			sql.append(" FROM ORIG_MG_APPRAISAL WHERE MG_COLLATERAL_ID = ? ");
			String dSql = String.valueOf(sql);
			log.debug("Sql="+dSql);
			ps = conn.prepareStatement(dSql);
			rs = null;
			ps.setString(1, collateral_id);

			rs = ps.executeQuery();
			AppraisalDataM appraisalDataM = new AppraisalDataM();
			
			while (rs.next()) {
			    appraisalDataM.setAppraisalPerformedBy(rs.getString("APPRAISAL_PERFORMED_BY"));
			    appraisalDataM.setAppraisalName(rs.getString("APPRAISAL_NAME"));
			    appraisalDataM.setAppraisalDate(rs.getDate("APPRAISAL_DATE"));
			    appraisalDataM.setTotalContactPrice(rs.getBigDecimal("TOTAL_CONTACT_PRICE"));
			    appraisalDataM.setTotalFMV(rs.getBigDecimal("TOTAL_FMV"));
			    appraisalDataM.setTotalLoanableValue(rs.getBigDecimal("TOTAL_LOAN_ABLE_VALUE"));
			    appraisalDataM.setLtv(rs.getBigDecimal("LTV"));
			    appraisalDataM.setUnacceptableCollateral(rs.getString("UNACCEPTABLE_COLLATERAL"));
			    appraisalDataM.setPurpose(rs.getString("PURPOSE"));
			    appraisalDataM.setAccountName(rs.getString("ACCOUNT_NAME"));
			    appraisalDataM.setReceivedDate(rs.getTimestamp("RECEIVED_DATE"));
			    appraisalDataM.setInspectedDate(rs.getTimestamp("INSPECTED_DATE"));
			    appraisalDataM.setRegisteredOwner(rs.getString("REGISTERED_OWNER"));
			    appraisalDataM.setLocation(rs.getString("LOCATION"));
			    appraisalDataM.setTct(rs.getString("TCT"));
			    appraisalDataM.setEnteredAt(rs.getString("ENTERED_AT"));
			    appraisalDataM.setArea(rs.getBigDecimal("AREA"));
			    appraisalDataM.setLiensAndEncumbrances(rs.getString("LIENS_AND_ENCUMBRANCES"));
			    appraisalDataM.setIssuedDate(rs.getTimestamp("ISSUED_DATE"));
			    appraisalDataM.setLessDepreciation(rs.getBigDecimal("LESS_DEPRECIATION"));
			    appraisalDataM.setLessDepreciationFMV(rs.getBigDecimal("LESS_DEPRECIATION_FMV"));
			    appraisalDataM.setImFMVLand(rs.getBigDecimal("IM_FMV_LAND"));
			    appraisalDataM.setImAVLand(rs.getBigDecimal("IM_AV_LAND"));
			    appraisalDataM.setImLVLand(rs.getBigDecimal("IM_LV_LAND"));
			    appraisalDataM.setImFMVBuilding(rs.getBigDecimal("IM_FMV_BUILDING"));
			    appraisalDataM.setImAVBuilding(rs.getBigDecimal("IM_AV_BUILDING"));
			    appraisalDataM.setImLVBuilding(rs.getBigDecimal("IM_LV_BUILDING"));
			    appraisalDataM.setImTotalFMV(rs.getBigDecimal("IM_TOTAL_FMV"));
			    appraisalDataM.setImTotalAV(rs.getBigDecimal("IM_TOTAL_AV"));
			    appraisalDataM.setImTotalLV(rs.getBigDecimal("IM_TOTAL_LV"));
			    appraisalDataM.setOtFMVLand(rs.getBigDecimal("OT_FMV_LAND"));
			    appraisalDataM.setOtAVLand(rs.getBigDecimal("OT_AV_LAND"));
			    appraisalDataM.setOtLVLand(rs.getBigDecimal("OT_LV_LAND"));
			    appraisalDataM.setOtFMVBuilding(rs.getBigDecimal("OT_FMV_BUILDING"));
			    appraisalDataM.setOtAVBuilding(rs.getBigDecimal("OT_AV_BUILDING"));
			    appraisalDataM.setOtLVBuilding(rs.getBigDecimal("OT_LV_BUILDING"));
			    appraisalDataM.setOtTotalFMV(rs.getBigDecimal("OT_TOTAL_FMV"));
			    appraisalDataM.setOtTotalAV(rs.getBigDecimal("OT_TOTAL_AV"));
			    appraisalDataM.setOtTotalLV(rs.getBigDecimal("OT_TOTAL_LV"));
			    appraisalDataM.setFmvOfTotalRCN(rs.getBigDecimal("FMV_OF_TOTAL_RCN"));
			    appraisalDataM.setAvOfTotalRCN(rs.getBigDecimal("AV_OF_TOTAL_RCN"));
			    appraisalDataM.setLvOfTotalRCN(rs.getBigDecimal("LV_OF_TOTAL_RCN"));
			    appraisalDataM.setSumFMVLand(rs.getBigDecimal("SUM_FMV_LAND"));
			    appraisalDataM.setSumAVLand(rs.getBigDecimal("SUM_AV_LAND"));
			    appraisalDataM.setSumLVLand(rs.getBigDecimal("SUM_LV_LAND"));
			    appraisalDataM.setSumFMVBuilding(rs.getBigDecimal("SUM_FMV_BUILDING"));
			    appraisalDataM.setSumAVBuilding(rs.getBigDecimal("SUM_AV_BUILDING"));
			    appraisalDataM.setSumLVBuilding(rs.getBigDecimal("SUM_LV_BUILDING"));
			    appraisalDataM.setSumTotalFMV(rs.getBigDecimal("SUM_TOTAL_FMV"));
			    appraisalDataM.setSumTotalAV(rs.getBigDecimal("SUM_TOTAL_AV"));
			    appraisalDataM.setSumTotalLV(rs.getBigDecimal("SUM_TOTAL_LV"));

			}
			return appraisalDataM;
		} catch (Exception e) {
			log.fatal(e.getLocalizedMessage());
			throw new OrigApplicationMException(e.getMessage());
		} finally {
			try {
				closeConnection(conn, rs, ps);
			} catch (Exception e) {
				log.fatal(e.getLocalizedMessage());
			}
		}
	}

    /* (non-Javadoc)
     * @see com.eaf.orig.shared.dao.OrigMGAppraisalDAO#deleteAppraisalDataM(java.lang.String)
     */
    public void deleteAppraisalDataM(String collatera_id) throws OrigApplicationMException {
        // TODO Auto-generated method stub

    }

    /* (non-Javadoc)
     * @see com.eaf.orig.shared.dao.OrigMGAppraisalDAO#deleteAllAppraisalDataM(java.lang.String)
     */
    public void deleteAllAppraisalDataM(String appRecordID) throws OrigApplicationMException {
        deleteAllORIG_MG_APPRAISAL(appRecordID);

    }
    
    private void deleteAllORIG_MG_APPRAISAL(String appRecordID)throws OrigApplicationMException {
		PreparedStatement ps = null;
		Connection conn = null;
		try {
			//conn = Get Connection
			conn = getConnection();
			StringBuffer sql = new StringBuffer("");
			sql.append("DELETE ORIG_MG_APPRAISAL ");
			sql.append(" WHERE MG_COLLATERAL_ID in (Select MG_COLLATERAL_ID from ORIG_MG_COLLATERAL where APPLICATION_RECORD_ID = ?)");
			String dSql = String.valueOf(sql);
			log.debug("Sql="+dSql);
			ps = conn.prepareStatement(dSql);
			
			ps.setString(1, appRecordID);
			
			ps.executeUpdate();

		} catch (Exception e) {
			log.fatal(e.getLocalizedMessage());
			throw new OrigApplicationMException(e.getMessage());
		} finally {
			try {
				closeConnection(conn, ps);
			} catch (Exception e) {
				log.fatal(e.getLocalizedMessage());
				throw new OrigApplicationMException(e.getMessage());
			}
		}

	}

}
