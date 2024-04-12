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

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

import org.apache.log4j.Logger;

import com.eaf.orig.master.shared.model.MandatoryM;
import com.eaf.orig.shared.dao.exceptions.OrigApplicationMException;

/**
 * @author Administrator
 *
 * Type: OrigMasterMandFieldDAOImpl
 */
public class OrigMasterMandFieldDAOImpl extends OrigObjectDAO implements
		OrigMasterMandFieldDAO {
	Logger log = Logger.getLogger(OrigMasterMandFieldDAOImpl.class); 
	
	/* (non-Javadoc)
	 * @see com.eaf.orig.shared.dao.OrigMasterMandFieldDAO#updateOrigMasterMandatoryM(com.eaf.orig.master.shared.model.MandatoryM)
	 */
	public double updateOrigMasterMandatoryM(MandatoryM mandatoryM)
			throws OrigApplicationMException {
		double returnRows = 0;
		PreparedStatement ps = null;
		Connection conn = null;
		try {
			//conn = Get Connection
			conn = getConnection();
			StringBuffer sql = new StringBuffer("");
			sql.append("UPDATE SC_MANDATORY_FIELDS ");
			
			sql.append(" SET FORM_NAME_ID=?, FIELD_NAME=?, CMR_MANDATORY_FLAG=?, DE_MANDATORY_FLAG=?, UW_MANDATORY_FLAG=?, PD_MANDATORY_FLAG=?, XCMR_MANDATORY_FLAG=?, CUSTOMER_TYPE=? ");
			
			sql.append(" WHERE FORM_NAME_ID = ? AND FIELD_NAME=? AND CUSTOMER_TYPE=?");
			
			String dSql = String.valueOf(sql);
			log.debug("Sql="+dSql);
						
			ps = conn.prepareStatement(dSql);
			ps.setString(1, mandatoryM.getFormNameId());
			ps.setString(2, mandatoryM.getFieldName());
			ps.setString(3, mandatoryM.getCmrFlag());
			ps.setString(4, mandatoryM.getDeFlag());
			ps.setString(5, mandatoryM.getUwFlag());
			ps.setString(6, mandatoryM.getPdFlag());
			ps.setString(7, mandatoryM.getXcmrFlag());
			ps.setString(8, mandatoryM.getCusType());
			ps.setString(9, mandatoryM.getFormNameId());
			ps.setString(10, mandatoryM.getFieldName());
			ps.setString(11, mandatoryM.getCusType());
			
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
		}
		return returnRows;
	}
	
	/* (non-Javadoc)
	 * @see com.eaf.orig.shared.dao.OrigMasterMandFieldDAO#selectOrigMasterMandatoryM(java.lang.String, java.lang.String, java.lang.String)
	 */
	public MandatoryM selectOrigMasterMandatoryM(String frmNameIDEdit,
			String frmNameEdit, String cusTypeEdit)
			throws OrigApplicationMException {
		PreparedStatement ps = null;
		ResultSet rs = null;
		Connection conn = null;
		try {
			//conn = Get Connection
			conn = getConnection();
			StringBuffer sql = new StringBuffer("");
			
			sql.append(" SELECT FORM_NAME_ID, FIELD_NAME, CMR_MANDATORY_FLAG, DE_MANDATORY_FLAG, UW_MANDATORY_FLAG, PD_MANDATORY_FLAG, XCMR_MANDATORY_FLAG, CUSTOMER_TYPE ");
			sql.append(" FROM SC_MANDATORY_FIELDS  WHERE FORM_NAME_ID = ? AND FIELD_NAME = ? AND CUSTOMER_TYPE = ? ");
			
			String dSql = String.valueOf(sql);
			log.debug("Sql=" + dSql);
			ps = conn.prepareStatement(dSql);
			rs = null;
			ps.setString(1, frmNameIDEdit);
			ps.setString(2, frmNameEdit);
			ps.setString(3, cusTypeEdit);

			rs = ps.executeQuery();
			MandatoryM mandatoryM = null;

			if (rs.next()) {
				mandatoryM = new MandatoryM();
				mandatoryM.setFormNameId(rs.getString(1));
				mandatoryM.setFieldName(rs.getString(2));
				mandatoryM.setCmrFlag(rs.getString(3));
				mandatoryM.setDeFlag(rs.getString(4));
				mandatoryM.setUwFlag(rs.getString(5));
				mandatoryM.setPdFlag(rs.getString(6));
				mandatoryM.setXcmrFlag(rs.getString(7));
				mandatoryM.setCusType(rs.getString(8));
			}
			
			return mandatoryM;

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

}
