/*
 * Created on Mar 14, 2010
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
import com.eaf.orig.shared.model.AddressDataM;

/**
 * @author wichaya
 *
 * Type: OrigMGCollatateralAddressDAOImpl
 */
public class OrigMGCollatateralAddressDAOImpl extends OrigObjectDAO implements OrigMGCollateralAddressDAO {
    private static Logger log = Logger.getLogger(OrigMGCollatateralAddressDAOImpl.class);
    
    public void createCollateralAddressM(AddressDataM addressDataM, String collatera_id, String username) throws OrigApplicationMException,UniqueIDGeneratorException {
        insertORIG_MG_COLLATERAL_ADDRESS(addressDataM, collatera_id, username);

    }
    
    private void insertORIG_MG_COLLATERAL_ADDRESS(AddressDataM addressDataM, String collatera_id, String username)throws OrigApplicationMException{
        PreparedStatement ps = null;
        Connection conn = null;
		try {
			//conn = Get Connection
			conn = getConnection();
			StringBuffer sql = new StringBuffer("");
			sql.append("INSERT INTO ORIG_MG_COLLATERAL_ADDRESS ");
			sql.append("( MG_COLLATERAL_ID, ADDRESS_NO, ROAD, SOI, VIL_APARTMENT, MOO, ROOM, FLOOR, PROVINCE, ZIP_CODE, TAMBOL, AMPHUR, CREATE_DATE, CREATE_BY, UPDATE_DATE, UPDATE_BY) ");
			sql.append(" VALUES(?,?,?,?,?  ,?,?,?,?,?  ,?,?,SYSDATE,?,SYSDATE  ,? ) ");
			String dSql = String.valueOf(sql);
			log.debug("Sql=" + dSql);
			ps = conn.prepareStatement(dSql);
			
			ps.setString(1, collatera_id);
			ps.setString(2, addressDataM.getAddressNo());
			ps.setString(3, addressDataM.getRoad());
			ps.setString(4, addressDataM.getSoi());
			ps.setString(5, addressDataM.getApartment());
			ps.setString(6, addressDataM.getMoo());
			ps.setString(7, addressDataM.getRoom());
			ps.setString(8, addressDataM.getFloor());
			ps.setString(9, addressDataM.getProvince());
			ps.setString(10, addressDataM.getZipcode());
			ps.setString(11, addressDataM.getTambol());
			ps.setString(12, addressDataM.getAmphur());
			ps.setString(13, username);
			ps.setString(14, username);
			
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


    public AddressDataM loadCollateralAddressM(String collatera_id) throws OrigApplicationMException {
        AddressDataM addressDataM = selectORIG_MG_COLLATERAL_ADDRESS(collatera_id);
        return addressDataM;
    }
    
    private AddressDataM selectORIG_MG_COLLATERAL_ADDRESS(String collatera_id) throws OrigApplicationMException {
		PreparedStatement ps = null;
		ResultSet rs = null;
		Connection conn = null;
		try {
			//conn = Get Connection
			conn = getConnection();
			StringBuffer sql = new StringBuffer("");
			sql.append("SELECT MG_COLLATERAL_ID, ADDRESS_NO, ROAD, SOI, VIL_APARTMENT, MOO, ROOM, FLOOR, PROVINCE, ZIP_CODE, TAMBOL, AMPHUR, UPDATE_DATE, UPDATE_BY ");
			sql.append(" FROM ORIG_MG_COLLATERAL_ADDRESS WHERE MG_COLLATERAL_ID = ? ");
			String dSql = String.valueOf(sql);
			log.debug("Sql="+dSql);
			ps = conn.prepareStatement(dSql);
			rs = null;
			ps.setString(1, collatera_id);

			rs = ps.executeQuery();
			AddressDataM addressDataM = new AddressDataM();
			
			while (rs.next()) {
			    addressDataM.setAddressNo(rs.getString("ADDRESS_NO"));
			    addressDataM.setRoad(rs.getString("ROAD"));
			    addressDataM.setSoi(rs.getString("SOI"));
			    addressDataM.setMoo(rs.getString("MOO"));
			    addressDataM.setRoom(rs.getString("ROOM"));
			    addressDataM.setFloor(rs.getString("FLOOR"));
			    addressDataM.setProvince(rs.getString("PROVINCE"));
			    addressDataM.setZipcode(rs.getString("ZIP_CODE"));
			    addressDataM.setTambol(rs.getString("TAMBOL"));
			    addressDataM.setAmphur(rs.getString("AMPHUR"));
			}
			return addressDataM;
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


    public void updateCollateralAddressM(AddressDataM addressDataM, String collatera_id, String username) throws OrigApplicationMException {
        updateTableORIG_MG_COLLATERAL_ADDRESS(addressDataM, collatera_id, username);

    }
    
    private double updateTableORIG_MG_COLLATERAL_ADDRESS(AddressDataM addressDataM, String collatera_id, String username) throws OrigApplicationMException {
		double returnRows = 0;
		PreparedStatement ps = null;
		Connection conn = null;
		try {
			//conn = Get Connection
			conn = getConnection();
			StringBuffer sql = new StringBuffer("");
			sql.append("UPDATE ORIG_MG_COLLATERAL_ADDRESS ");
			sql.append(" SET ADDRESS_NO=?, ROAD=?, SOI=?, VIL_APARTMENT=?, MOO=?, ROOM=?, FLOOR=?, PROVINCE=?, ZIP_CODE=?, TAMBOL=?, AMPHUR=?, UPDATE_DATE=SYSDATE, UPDATE_BY=? ");
			sql.append("  WHERE MG_COLLATERAL_ID = ?  ");
			String dSql = String.valueOf(sql);
			log.debug("Sql=" + dSql);
			ps = conn.prepareStatement(dSql);
			
			ps.setString(1, addressDataM.getAddressNo());
			ps.setString(2, addressDataM.getRoad());
			ps.setString(3, addressDataM.getSoi());
			ps.setString(4, addressDataM.getApartment());
			ps.setString(5, addressDataM.getMoo());
			ps.setString(6, addressDataM.getRoom());
			ps.setString(7, addressDataM.getFloor());
			ps.setString(8, addressDataM.getProvince());
			ps.setString(9, addressDataM.getZipcode());
			ps.setString(10, addressDataM.getTambol());
			ps.setString(11, addressDataM.getAmphur());
			ps.setString(13, username);
			
			ps.setString(14, collatera_id);
			
			
			returnRows = ps.executeUpdate();
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
		return returnRows;
	}
    
    public void deleteCollateralAddressM(String collatera_id)throws OrigApplicationMException{
        deleteORIG_MG_COLLATERAL(collatera_id);
    }
    
    private void deleteORIG_MG_COLLATERAL(String collatera_id)throws OrigApplicationMException {
		PreparedStatement ps = null;
		Connection conn = null;
		try {
			//conn = Get Connection
			conn = getConnection();
			StringBuffer sql = new StringBuffer("");
			sql.append("DELETE ORIG_MG_COLLATERAL_ADDRESS ");
			sql.append(" WHERE MG_COLLATERAL_ID = ?");
			String dSql = String.valueOf(sql);
			log.debug("Sql="+dSql);
			ps = conn.prepareStatement(dSql);
			
			ps.setString(1, collatera_id);
			
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
    
    public void deleteAllCollateralAddressM(String appRecordID)throws OrigApplicationMException{
        deleteAllORIG_MG_COLLATERAL(appRecordID);
    }
    
    private void deleteAllORIG_MG_COLLATERAL(String appRecordID)throws OrigApplicationMException {
		PreparedStatement ps = null;
		Connection conn = null;
		try {
			//conn = Get Connection
			conn = getConnection();
			StringBuffer sql = new StringBuffer("");
			sql.append("DELETE ORIG_MG_COLLATERAL_ADDRESS ");
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
