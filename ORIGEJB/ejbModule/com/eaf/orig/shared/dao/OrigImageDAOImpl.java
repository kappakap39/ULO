/*
 * Created on Nov 15, 2007
 * Created by Weeraya
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

import org.apache.log4j.Logger;

import com.eaf.orig.shared.dao.OrigObjectDAO;
import com.eaf.orig.shared.service.OrigServiceLocator;

/**
 * @author Weeraya
 *
 * Type: OrigImageDAOImpl
 */
public class OrigImageDAOImpl extends OrigObjectDAO implements OrigImageDAO {
	Logger logger = Logger.getLogger(OrigImageDAOImpl.class);
	
	public int updateTableAPPLICATION_IMG_REQUEST(String requestID, String appRecordID) throws OrigImageException {
		int returnRows = 0;
		PreparedStatement ps = null;
		Connection conn = null;
		try {
			//conn = Get Connection
			conn = getConnection(OrigServiceLocator.IM_DB);
			StringBuffer sql = new StringBuffer("");
			sql.append("UPDATE APPLICATION_IMG_REQUEST ");
			sql.append(" SET APPLICATION_RECORD_ID = ?");
			sql.append(" WHERE IMG_REQUEST = ? ");
			String dSql = String.valueOf(sql);
			logger.debug("Sql=" + dSql);
			ps = conn.prepareStatement(dSql);
			ps.setString(1, appRecordID);
			ps.setString(2, requestID);
			
			returnRows = ps.executeUpdate();
		} catch (Exception e) {
			logger.error("Error >> ", e);
			throw new OrigImageException(e.getMessage());
		} finally {
			try {
				closeConnection(conn, ps);
			} catch (Exception e) {
				logger.error("Close Connection Error >> ", e);
			}
		}
		return returnRows;
	}
}
