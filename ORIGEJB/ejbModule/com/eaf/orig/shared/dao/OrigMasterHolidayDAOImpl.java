/*
 * Created on Dec 6, 2007
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
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

import org.apache.log4j.Logger;

import com.eaf.orig.master.shared.model.HolidayM;
import com.eaf.orig.shared.dao.exceptions.OrigApplicationMException;

/**
 * @author Administrator
 *
 * Type: OrigMasterHolidayDAOImpl
 */
public class OrigMasterHolidayDAOImpl extends OrigObjectDAO implements
		OrigMasterHolidayDAO {
	Logger log = Logger.getLogger(OrigMasterHolidayDAOImpl.class);
	
	/* (non-Javadoc)
	 * @see com.eaf.orig.shared.dao.OrigMasterHolidayDAO#hvHolDate(java.util.Date)
	 */
	public boolean hvHolDate(java.util.Date holDate)
			throws OrigApplicationMException {
		PreparedStatement ps = null;
		ResultSet rs = null;
		Connection conn = null;
		try {
			//conn = Get Connection
			conn = getConnection();
			StringBuffer sql = new StringBuffer("");
			
			sql.append(" SELECT *");
			sql.append(" FROM HOLIDAY_MASTER  WHERE HOLIDAY_DATE = ? ");
			
			String dSql = String.valueOf(sql);
			log.debug("Sql=" + dSql);
			ps = conn.prepareStatement(dSql);
			rs = null;
			ps.setDate(1, new Date(holDate.getTime()));

			rs = ps.executeQuery();
			if (rs.next()) {
				return true;
			}else{
				return false;
			}

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
	
	public void deleteOrigMasterHoliday(String[] holDateToDelete)
			throws OrigApplicationMException {
		for(int i = 0; i < holDateToDelete.length; i++){
			deleteHoliday(holDateToDelete[i]);
			log.debug("holDateToDelete[i]=" + holDateToDelete[i]);
		}

	}
	
	public void deleteHoliday(String holDate)
			throws OrigApplicationMException {
		PreparedStatement ps = null;
		Connection conn = null;
		try {
				//conn = Get Connection
				conn = getConnection();
				StringBuffer sql = new StringBuffer("");
				sql.append("DELETE HOLIDAY_MASTER ");
				sql.append(" WHERE HOLIDAY_DATE = to_date(?,'mm/dd/yyyy') ");
				String dSql = String.valueOf(sql);
				log.debug("Sql=" + dSql);
				log.debug("holDate=" + holDate);
				ps = conn.prepareStatement(dSql);
				ps.setString(1,holDate);
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
	
	public double updateOrigMasterHolidayM(HolidayM holidayM)
			throws OrigApplicationMException {
		double returnRows = 0;
		PreparedStatement ps = null;
		Connection conn = null;
		try {
			//conn = Get Connection
			conn = getConnection();
			StringBuffer sql = new StringBuffer("");
			sql.append("UPDATE HOLIDAY_MASTER ");
			
			sql.append(" SET HOLIDAY_DESC=?, WORKING_FLAG=?, UPDATE_BY=?, UPDATE_DATE=SYSDATE ");
			
			sql.append(" WHERE HOLIDAY_DATE = ?");
			
			String dSql = String.valueOf(sql);
			log.debug("Sql="+dSql);
			ps = conn.prepareStatement(dSql);
			ps.setString(1, holidayM.getHolDesc());
			ps.setString(2, holidayM.getWorkFlag());
			ps.setString(3, holidayM.getUpdateBy());
			ps.setDate(4, new Date(holidayM.getHolDate().getTime()));
			
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
	
	public HolidayM selectOrigMasterHolidayM(java.util.Date holDate)
			throws OrigApplicationMException {
		PreparedStatement ps = null;
		ResultSet rs = null;
		Connection conn = null;
		try {
			//conn = Get Connection
			conn = getConnection();
			StringBuffer sql = new StringBuffer("");
			
			sql.append(" SELECT HOLIDAY_DATE, HOLIDAY_DESC, WORKING_FLAG ");
			sql.append(" FROM HOLIDAY_MASTER  WHERE HOLIDAY_DATE = to_date(?,'dd/MM/yyyy') ");
			
			String dSql = String.valueOf(sql);
			log.debug("Sql=" + dSql);
			ps = conn.prepareStatement(dSql);
			rs = null;
			ps.setString(1,  this.datetoString(holDate));

			rs = ps.executeQuery();
			HolidayM holidayM = null;

			if (rs.next()) {
				holidayM = new HolidayM();
				holidayM.setHolDate(rs.getDate(1));
				holidayM.setHolDesc(rs.getString(2));
				holidayM.setWorkFlag(rs.getString(3));
				
			}
			return holidayM;

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
	
	public void createModelOrigMasterHolidayM(HolidayM holidayM)
			throws OrigApplicationMException {
		PreparedStatement ps = null;
		Connection conn = null;
		try{
			//conn = Get Connection
			conn = getConnection();
			StringBuffer sql = new StringBuffer("");
			sql.append(" INSERT INTO HOLIDAY_MASTER ");
			sql.append(" (HOLIDAY_DATE, HOLIDAY_DESC, WORKING_FLAG, UPDATE_BY, UPDATE_DATE) ");
			sql.append(" VALUES (?,?,?,?,SYSDATE)");
			String dSql = String.valueOf( sql);
			log.debug("dSql="+dSql);
			ps = conn.prepareStatement(dSql);
			
			
			ps.setDate(1, new Date(holidayM.getHolDate().getTime()));
			ps.setString(2, holidayM.getHolDesc());
			ps.setString(3, holidayM.getWorkFlag());
			ps.setString(4, holidayM.getUpdateBy());
			
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
