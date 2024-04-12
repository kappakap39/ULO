/*
 * Created on Nov 25, 2007
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
import com.eaf.orig.master.shared.model.GeneralParamM;
import com.eaf.orig.shared.dao.exceptions.OrigApplicationMException;
/**
 * @author Administrator
 *
 * Type: OrigMasterGenParamDAOImpl
 */
public class OrigMasterGenParamDAOImpl extends OrigObjectDAO implements OrigMasterGenParamDAO {
	Logger log = Logger.getLogger(OrigMasterGenParamDAOImpl.class);
	
	public boolean hvGenPrmCde(String genPrmCde) throws OrigApplicationMException {
		PreparedStatement ps = null;
		ResultSet rs = null;
		Connection conn = null;
		try {
			//conn = Get Connection
			conn = getConnection();
			StringBuilder sql = new StringBuilder("");
			
			sql.append(" SELECT *");
			sql.append(" FROM GENERAL_PARAM  WHERE PARAM_CODE = ? ");
			
			String dSql = String.valueOf(sql);
//			log.debug("Sql=" + dSql);
			ps = conn.prepareStatement(dSql);
			rs = null;
			ps.setString(1, genPrmCde);

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
	
	public void deleteOrigMasterGenParamM(String[] genParamToDelete)throws OrigApplicationMException {
		String[] cdeAndBusID;
		//String[] busID;
		for(int i = 0; i < genParamToDelete.length; i++){
			cdeAndBusID = genParamToDelete[i].split(",");
			deleteGenParamM(cdeAndBusID[0],cdeAndBusID[1]);
		}

	}
	
	public void deleteGenParamM(String fieldId,String busID)throws OrigApplicationMException {
		PreparedStatement ps = null;
		Connection conn = null;
		try {
				//conn = Get Connection
				conn = getConnection(); 
				StringBuilder sql = new StringBuilder("");
				sql.append("DELETE GENERAL_PARAM ");
				sql.append(" WHERE PARAM_CODE = ? AND BUS_CLASS_ID=?");
				String dSql = String.valueOf(sql);
//				log.debug("Sql=" + dSql);
				ps = conn.prepareStatement(dSql);
				ps.setString(1, fieldId);
				ps.setString(2, busID);
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
	
	public double updateOrigMasterGenParamM(GeneralParamM genParamM)throws OrigApplicationMException {
		double returnRows = 0;
		PreparedStatement ps = null;
		Connection conn = null;
		try {
			//conn = Get Connection
			conn = getConnection();
			StringBuilder sql = new StringBuilder("");
			
			sql.append("UPDATE GENERAL_PARAM ");			
			sql.append(" SET PARAM_CODE=?, PARAM_VALUE=?, PARAM_VALUE2=?, BUS_CLASS_ID=?, UPDATE_BY=?, UPDATE_DATE=SYSDATE ");			
			sql.append(" WHERE PARAM_CODE = ? AND BUS_CLASS_ID=? ");
			
			String dSql = String.valueOf(sql);
//			log.debug("Sql="+dSql);
			ps = conn.prepareStatement(dSql);
			ps.setString(1, genParamM.getParamCode());
			ps.setString(2, genParamM.getParamValue());
			ps.setString(3, genParamM.getParamValue2());
			ps.setString(4, genParamM.getBusClassID());
			ps.setString(5, genParamM.getUpdateBy());
			ps.setString(6, genParamM.getParamCode());
			ps.setString(7, genParamM.getBusClassID());
			
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
	
	public GeneralParamM loadModelOrigMasterGenParamM(String genParamCde,String busID)throws OrigApplicationMException {
		GeneralParamM genParamM = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		Connection conn = null;
		try {
			//conn = Get Connection
			conn = getConnection();
			StringBuilder sql = new StringBuilder("");
			
			sql.append(" SELECT PARAM_CODE, PARAM_VALUE, BUS_CLASS_ID, PARAM_VALUE2 ");
			sql.append(" FROM GENERAL_PARAM  WHERE PARAM_CODE = ? AND BUS_CLASS_ID = ?");
			
			String dSql = String.valueOf(sql);
//			log.debug("Sql=" + dSql);
			ps = conn.prepareStatement(dSql);
			rs = null;
			ps.setString(1, genParamCde);
			ps.setString(2, busID);

			rs = ps.executeQuery();

			if(rs.next()) {
				genParamM = new GeneralParamM();
				genParamM.setParamCode(rs.getString(1));
				genParamM.setParamValue(rs.getString(2));
				genParamM.setBusClassID(rs.getString(3));
				genParamM.setParamValue2(rs.getString(4));
			}
			
			return genParamM;

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
	
	public void createModelOrigMasterGenParamM(GeneralParamM genParamM)throws OrigApplicationMException {
		PreparedStatement ps = null;
		Connection conn = null;
		try{
			//conn = Get Connection
			conn = getConnection();
			StringBuilder sql = new StringBuilder("");
			sql.append(" INSERT INTO GENERAL_PARAM ");
			sql.append(" (PARAM_CODE, BUS_CLASS_ID, PARAM_VALUE, PARAM_VALUE2, UPDATE_BY, UPDATE_DATE) ");
			sql.append(" VALUES (?,?,?,?,?,SYSDATE)");
			String dSql = String.valueOf( sql);
			log.debug("dSql="+dSql);
			ps = conn.prepareStatement(dSql);
			
			ps.setString(1, genParamM.getParamCode());
			ps.setString(2, genParamM.getBusClassID());
			ps.setString(3, genParamM.getParamValue());
			ps.setString(4, genParamM.getParamValue2());
			ps.setString(5, genParamM.getUpdateBy());
			
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

	@Override
	public String GetGeneralParam(String paramCode, String busClassID)throws OrigApplicationMException {
//		logger.debug("[GetGeneralParam] paramCode "+paramCode);
//		logger.debug("[GetGeneralParam] busClassID "+busClassID);
		PreparedStatement ps = null;
		ResultSet rs = null;
		Connection conn = null;
		try{
			conn = getConnection();
			StringBuilder sql = new StringBuilder();
			sql.append(" SELECT GP.* FROM GENERAL_PARAM GP ");
			sql.append(" WHERE GP.PARAM_CODE = ? ");
			sql.append(" AND GP.BUS_CLASS_ID = ? ");
			ps = conn.prepareStatement(sql.toString());
			int index = 1;
				ps.setString(index++,paramCode);
				ps.setString(index++,busClassID);
			rs = ps.executeQuery();
			if(rs.next()){
				return rs.getString("PARAM_VALUE");
			}
		}catch (Exception e) {
			logger.fatal(e.getLocalizedMessage());
			throw new OrigApplicationMException(e.getMessage());
		}finally{
			try {
				closeConnection(conn, rs, ps);
			} catch (Exception e) {
				logger.fatal(e.getLocalizedMessage());
				throw new OrigApplicationMException(e.getMessage());
			}
		}
		return "";
	}

	@Override
	public String GetParamBusClassGroup(String paramCode, String busClassID)throws OrigApplicationMException {
		PreparedStatement ps = null;
		ResultSet rs = null;
		String param = null;
		Connection conn = null;
		try{
			conn = getConnection();
			StringBuilder sql = new StringBuilder();
			sql.append(" SELECT GP.* FROM GENERAL_PARAM GP ,BUSINESS_CLASS_GROUP BCG ,BUS_CLASS_GRP_MAP CGM ");
			sql.append(" WHERE GP.PARAM_CODE = ? ");
			sql.append(" AND BCG.BUS_GRP_ID = CGM.BUS_GRP_ID ");
			sql.append(" AND GP.BUS_CLASS_ID = CGM.BUS_GRP_ID ");
			sql.append(" AND CGM.BUS_CLASS_ID = ? ");
			ps = conn.prepareStatement(sql.toString());
			int index = 1;
				ps.setString(index++,paramCode);
				ps.setString(index++,busClassID);
			rs = ps.executeQuery();
			if(rs.next()){
				param = rs.getString("PARAM_VALUE");				
			}
		}catch(Exception e){
			logger.fatal(e.getLocalizedMessage());
			throw new OrigApplicationMException(e.getMessage());
		}finally{
			try{
				closeConnection(conn, rs, ps);
			}catch(Exception e){
				logger.fatal(e.getLocalizedMessage());
				throw new OrigApplicationMException(e.getMessage());
			}
		}
		return param;
	}

	@Override
	public String GetGeneralParam(String paramCode)throws OrigApplicationMException {
		PreparedStatement ps = null;
		ResultSet rs = null;
		String param = null;
		Connection conn = null;
		try {
			conn = getConnection();
			StringBuilder sql = new StringBuilder("");
			
			sql.append(" SELECT *");
			sql.append(" FROM GENERAL_PARAM  WHERE PARAM_CODE = ? ");
			
			String dSql = String.valueOf(sql);
			ps = conn.prepareStatement(dSql);
			rs = null;
			ps.setString(1, paramCode);

			rs = ps.executeQuery();
			if(rs.next()){
				param = rs.getString("PARAM_VALUE");
			}
		}catch(Exception e){
			log.fatal("",e);
			throw new OrigApplicationMException(e.getMessage());
		}finally{
			try{
				closeConnection(conn, rs, ps);
			}catch(Exception e){
				log.fatal("",e);
			}
		}
		return param;
	}

}
