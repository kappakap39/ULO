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

import com.eaf.orig.master.shared.model.ExceptActionM;
import com.eaf.orig.shared.constant.EJBConstant;
import com.eaf.orig.shared.dao.exceptions.OrigApplicationMException;
import com.eaf.orig.shared.dao.utility.EjbUtil;
import com.eaf.orig.shared.ejb.ORIGGeneratorManager;
import com.eaf.orig.shared.service.ORIGEJBService;

/**
 * @author Administrator
 *
 * Type: OrigMasterExceptActDAOImpl
 */
public class OrigMasterExceptActDAOImpl extends OrigObjectDAO implements
		OrigMasterExceptActDAO {
	Logger log = Logger.getLogger(OrigMasterExceptActDAOImpl.class);
	
	/* (non-Javadoc)
	 * @see com.eaf.orig.shared.dao.OrigMasterExceptActDAO#deleteOrigMasterExceptActM(java.lang.String[])
	 */
	public void deleteOrigMasterExceptActM(String[] exceptIDToDelete)
			throws OrigApplicationMException {
		
		for(int i = 0; i < exceptIDToDelete.length; i++){
			deleteUserExcept(exceptIDToDelete[i]);
			deleteExcept(exceptIDToDelete[i]);
		}
	}
	
	public void deleteUserExcept(String exceptID)
	throws OrigApplicationMException {
		PreparedStatement ps = null;
		Connection conn = null;
		try {
				//conn = Get Connection
				conn = getConnection();
				StringBuffer sql = new StringBuffer("");
				sql.append("DELETE USER_EXCEPTION ");
				sql.append(" WHERE EXCEPTION_ID = ?");
				String dSql = String.valueOf(sql);
				log.debug("Sql=" + dSql);
				ps = conn.prepareStatement(dSql);
				ps.setString(1, exceptID);
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
	
	public void deleteExcept(String exceptID)
	throws OrigApplicationMException {
		PreparedStatement ps = null;
		Connection conn = null;
		try {
				//conn = Get Connection
				conn = getConnection();
				StringBuffer sql = new StringBuffer("");
				sql.append("DELETE EXCEPTION ");
				sql.append(" WHERE EXCEPTION_ID = ?");
				String dSql = String.valueOf(sql);
				log.debug("Sql=" + dSql);
				ps = conn.prepareStatement(dSql);
				ps.setString(1, exceptID);
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
	
	public double updateOrigMasterExceptActM(ExceptActionM exceptActM)
			throws OrigApplicationMException {
		double returnRows = 0;
		PreparedStatement ps = null;
		Connection conn = null;
		try {
			//conn = Get Connection
			conn = getConnection();
			StringBuffer sql = new StringBuffer("");
			sql.append("UPDATE EXCEPTION ");
			
			sql.append(" SET EXCEPTION_NAME=?, EXCEPTION_DESC=?, UPDATE_BY=?, UPDATE_DATE=SYSDATE ");
			
			sql.append(" WHERE EXCEPTION_ID = ?");
			
			String dSql = String.valueOf(sql);
			log.debug("Sql="+dSql);
			ps = conn.prepareStatement(dSql);
			ps.setString(1, exceptActM.getExceptionName());
			ps.setString(2, exceptActM.getExceptionDesc());
			ps.setString(3, exceptActM.getUpdateBy());
			ps.setString(4, exceptActM.getExceptionId());
			
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
	
	public ExceptActionM selectOrigMasterExceptActM(String exceptIDEdit)
			throws OrigApplicationMException {
		PreparedStatement ps = null;
		ResultSet rs = null;
		Connection conn = null;
		try {
			//conn = Get Connection
			conn = getConnection();
			StringBuffer sql = new StringBuffer("");
			
			sql.append(" SELECT EXCEPTION_NAME, EXCEPTION_DESC ");
			sql.append(" FROM EXCEPTION  WHERE EXCEPTION_ID = ? ");
			
			String dSql = String.valueOf(sql);
			log.debug("Sql=" + dSql);
			ps = conn.prepareStatement(dSql);
			rs = null;
			ps.setString(1, exceptIDEdit);

			rs = ps.executeQuery();
			ExceptActionM exceptActM = null;

			if (rs.next()) {
				exceptActM = new ExceptActionM();
				exceptActM.setExceptionName(rs.getString(1));
				exceptActM.setExceptionDesc(rs.getString(2));
				
			}
			log.debug("selectOrigMasterExceptActM --->>> exceptActM=" + exceptActM);
			return exceptActM;

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
	
	public void createModelOrigMasterExceptActM(ExceptActionM exceptActM)
			throws OrigApplicationMException {
		
		try {
			//String exceptID = ORIGDAOFactory.getUniqueIDGeneratorDAO2().getUniqByName(EJBConstant.EXCEPTION_ID);
			//exceptID = EjbUtil.appendZero(exceptID, 3);
			//log.debug("/////exceptID gen = "+exceptID);
			//exceptActM.setExceptionId( exceptID);
			ORIGGeneratorManager generatorManager = ORIGEJBService.getGeneratorManager();
			String exceptID = generatorManager.generateUniqueIDByName(EJBConstant.EXCEPTION_ID);
			exceptActM.setExceptionId(exceptID);
			createExceptM(exceptActM);
		} catch (Exception e) {
			log.fatal("",e);
			throw new OrigApplicationMException(e.getMessage());
		}
		
	}
	
	public void createExceptM(ExceptActionM exceptActM)
			throws OrigApplicationMException {
		PreparedStatement ps = null;
		Connection conn = null;
		try{
			//conn = Get Connection
			conn = getConnection();
			StringBuffer sql = new StringBuffer("");
			sql.append(" INSERT INTO EXCEPTION ");
			sql.append(" (EXCEPTION_ID, EXCEPTION_NAME, EXCEPTION_DESC, CREATE_BY, CREATE_DATE, UPDATE_BY, UPDATE_DATE) ");
			sql.append(" VALUES (?,?,?,?,SYSDATE,?,SYSDATE)");
			String dSql = String.valueOf( sql);
			log.debug("dSql="+dSql);
			ps = conn.prepareStatement(dSql);
			
			ps.setString(1, exceptActM.getExceptionId());
			ps.setString(2, exceptActM.getExceptionName());
			ps.setString(3, exceptActM.getExceptionDesc());
			ps.setString(4, exceptActM.getCreateBy());
			ps.setString(5, exceptActM.getUpdateBy());
			
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
