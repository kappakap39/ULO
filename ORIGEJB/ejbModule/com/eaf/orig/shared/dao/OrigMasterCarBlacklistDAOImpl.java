/*
 * Created on Nov 29, 2007
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

import com.eaf.orig.master.shared.model.CarBlacklistM;
import com.eaf.orig.shared.constant.EJBConstant;
import com.eaf.orig.shared.dao.exceptions.OrigApplicationMException;
import com.eaf.orig.shared.ejb.ORIGGeneratorManager;
import com.eaf.orig.shared.service.ORIGEJBService;

/**
 * @author Administrator
 *
 * Type: OrigMasterCarBlacklistDAOImpl
 */
public class OrigMasterCarBlacklistDAOImpl extends OrigObjectDAO implements
		OrigMasterCarBlacklistDAO {
	Logger log = Logger.getLogger(OrigMasterCarBlacklistDAOImpl.class);
	
	/* (non-Javadoc)
	 * @see com.eaf.orig.shared.dao.OrigMasterCarBlacklistDAO#deleteOrigMasterCarBlacklist(int[])
	 */
	public void deleteOrigMasterCarBlacklist(int[] blackVehID_intToDelete)
			throws OrigApplicationMException {
		for(int i = 0; i < blackVehID_intToDelete.length; i++){
			deleteCarBlacklist(blackVehID_intToDelete[i]);
		}
	}
	
	public void deleteCarBlacklist(int blackVehID)
			throws OrigApplicationMException {
		PreparedStatement ps = null;
		Connection conn = null;
		try {
				//conn = Get Connection
				conn = getConnection();
				StringBuffer sql = new StringBuffer("");
				sql.append("DELETE BLACKLIST_VEHICAL ");
				sql.append(" WHERE BL_VEHICAL_ID = ?");
				String dSql = String.valueOf(sql);
				log.debug("Sql=" + dSql);
				ps = conn.prepareStatement(dSql);
				ps.setInt(1, blackVehID);
				ps.executeUpdate(); 

		} catch (Exception e) {
			log.fatal(e.getStackTrace());
			throw new OrigApplicationMException(e.getMessage());
		} finally {
			try {
				closeConnection(conn, ps);
			} catch (Exception e) {
				log.fatal(e.getStackTrace());
				throw new OrigApplicationMException(e.getMessage());
			}
		}
	}
	
	public double updateOrigMasterCarBlacklistM(CarBlacklistM carBlacklistM)
			throws OrigApplicationMException {
		double returnRows = 0;
		PreparedStatement ps = null;Connection conn = null;
		try {
			//conn = Get Connection
			conn = getConnection();
			StringBuffer sql = new StringBuffer("");
			sql.append("UPDATE BLACKLIST_VEHICAL ");
			
			sql.append(" SET CHASSIS_NUMBER=?, ENGINE_NUMBER=?, REGISTRATION_NUMBER=?, BL_SOURCE=?, BL_REASON=?, BL_NOTE=?, BRAND=?, MODEL=?, UPDATE_DATE=SYSDATE, UPDATE_BY=? ");
			
			sql.append(" WHERE BL_VEHICAL_ID = ?");
			
			String dSql = String.valueOf(sql);
			log.debug("Sql="+dSql);
			ps = conn.prepareStatement(dSql);
			
			ps.setString(1, carBlacklistM.getChassisNum());
			ps.setString(2, carBlacklistM.getEngineNum());
			ps.setString(3, carBlacklistM.getRegistrationNum());
			ps.setString(4, carBlacklistM.getBlSource());
			ps.setString(5, carBlacklistM.getBlReason());
			ps.setString(6, carBlacklistM.getBlNote());
			ps.setString(7, carBlacklistM.getBrand());
			ps.setString(8, carBlacklistM.getModel());
			ps.setString(9, carBlacklistM.getUpdateBy());
			ps.setInt(10, carBlacklistM.getBlVehicleID());
			
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
	 * @see com.eaf.orig.shared.dao.OrigMasterCarBlacklistDAO#selectOrigMasterCarBlacklistM(int)
	 */
	public CarBlacklistM selectOrigMasterCarBlacklistM(int blackVehID)
			throws OrigApplicationMException {
		PreparedStatement ps = null;
		ResultSet rs = null;Connection conn = null;
		try {
			//conn = Get Connection
			conn = getConnection();
			StringBuffer sql = new StringBuffer("");
			
			sql.append(" SELECT BL_VEHICAL_ID, CHASSIS_NUMBER, ENGINE_NUMBER, REGISTRATION_NUMBER, BL_SOURCE, BL_REASON, BL_NOTE, BRAND, MODEL ");
			sql.append(" FROM BLACKLIST_VEHICAL  WHERE BL_VEHICAL_ID = ? ");
			
			String dSql = String.valueOf(sql);
			log.debug("Sql=" + dSql);
			ps = conn.prepareStatement(dSql);
			rs = null;
			ps.setInt(1, blackVehID);

			rs = ps.executeQuery();
			CarBlacklistM carBlacklistM = null;

			if (rs.next()) {
				carBlacklistM = new CarBlacklistM();
				carBlacklistM.setBlVehicleID(rs.getInt(1));
				carBlacklistM.setChassisNum(rs.getString(2));
				carBlacklistM.setEngineNum(rs.getString(3));
				carBlacklistM.setRegistrationNum(rs.getString(4));
				carBlacklistM.setBlSource(rs.getString(5));
				carBlacklistM.setBlReason(rs.getString(6));
				carBlacklistM.setBlNote(rs.getString(7));
				carBlacklistM.setBrand(rs.getString(8));
				carBlacklistM.setModel(rs.getString(9));
				
			}
			return carBlacklistM;

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
	
	public void createModelOrigMasterCarBlacklistM(CarBlacklistM carBlacklistM)
	throws OrigApplicationMException {
		try {
			//int blVehicleID = Integer.parseInt(ORIGDAOFactory.getUniqueIDGeneratorDAO2().getUniqByName(EJBConstant.BL_VEHICAL_ID));
			//log.debug("/////blVehicleID gen = "+blVehicleID);
			//carBlacklistM.setBlVehicleID( blVehicleID);
			ORIGGeneratorManager generatorManager = ORIGEJBService.getGeneratorManager();
			int blVehicleID = Integer.parseInt(generatorManager.generateUniqueIDByName(EJBConstant.BL_VEHICAL_ID));
			carBlacklistM.setBlVehicleID(blVehicleID);
			createCarBlacklistM(carBlacklistM);
		} catch (Exception e) {
			log.fatal("",e);
			throw new OrigApplicationMException(e.getMessage());
		}

	}
	
	public void createCarBlacklistM(CarBlacklistM carBlacklistM)
	throws OrigApplicationMException {
		PreparedStatement ps = null;Connection conn = null;
		try{
			//conn = Get Connection
			conn = getConnection();
			StringBuffer sql = new StringBuffer("");
			sql.append(" INSERT INTO BLACKLIST_VEHICAL ");
			sql.append(" (BL_VEHICAL_ID, CHASSIS_NUMBER, ENGINE_NUMBER, REGISTRATION_NUMBER, BL_SOURCE, BL_REASON, BL_NOTE, BRAND, MODEL, CREATE_DATE, CREATE_BY, UPDATE_DATE, UPDATE_BY  ) ");
			sql.append(" VALUES (?,?,?,?,?  ,?,?,?,?,SYSDATE  ,?,SYSDATE,?)");
			String dSql = String.valueOf( sql);
			log.debug("dSql="+dSql);
			ps = conn.prepareStatement(dSql);
			
			ps.setInt(1, carBlacklistM.getBlVehicleID());
			ps.setString(2, carBlacklistM.getChassisNum());
			ps.setString(3, carBlacklistM.getEngineNum());
			ps.setString(4, carBlacklistM.getRegistrationNum());
			ps.setString(5, carBlacklistM.getBlSource());
			ps.setString(6, carBlacklistM.getBlReason());
			ps.setString(7, carBlacklistM.getBlNote());
			ps.setString(8, carBlacklistM.getBrand());
			ps.setString(9, carBlacklistM.getModel());
			ps.setString(10, carBlacklistM.getCreateBy());
			ps.setString(11, carBlacklistM.getUpdateBy());
			
			ps.executeUpdate();
			ps.close();
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
	}
	
}
