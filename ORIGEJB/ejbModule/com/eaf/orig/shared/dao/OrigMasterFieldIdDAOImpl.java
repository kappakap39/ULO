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

import com.eaf.orig.master.shared.model.FieldIdM;
import com.eaf.orig.shared.dao.exceptions.OrigApplicationMException;

/**
 * @author Administrator
 *
 * Type: OrigMasterFieldIdDAOImpl
 */
public class OrigMasterFieldIdDAOImpl extends OrigObjectDAO implements
		OrigMasterFieldIdDAO {
	Logger log = Logger.getLogger(OrigMasterFieldIdDAOImpl.class);
	
	/* (non-Javadoc)
	 * @see com.eaf.orig.shared.dao.OrigMasterFieldIdDAO#hvFieldID(int)
	 */
	public boolean hvFieldID(int fieldID) throws OrigApplicationMException {
		PreparedStatement ps = null;
		ResultSet rs = null;
		Connection conn = null;
		try {
			//conn = Get Connection
			conn = getConnection();
			StringBuffer sql = new StringBuffer("");
			
			sql.append(" SELECT *");
			sql.append(" FROM FIELD_ID  WHERE FIELD_ID = ? ");
			
			String dSql = String.valueOf(sql);
			log.debug("Sql=" + dSql);
			ps = conn.prepareStatement(dSql);
			rs = null;
			ps.setInt(1, fieldID);

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
	
	public void deleteOrigMasterFieldIdM(int[] fieldIdToDelete)
			throws OrigApplicationMException{
		for(int i = 0; i < fieldIdToDelete.length; i++){
			deleteFieldIdM(fieldIdToDelete[i]);
		}
	}
	
	public void deleteFieldIdM(int fieldId)
			throws OrigApplicationMException {
		PreparedStatement ps = null;
		Connection conn = null;
		try {
				//conn = Get Connection
				conn = getConnection();
				StringBuffer sql = new StringBuffer("");
				sql.append("DELETE FIELD_ID ");
				sql.append(" WHERE FIELD_ID = ?");
				String dSql = String.valueOf(sql);
				log.debug("Sql=" + dSql);
				ps = conn.prepareStatement(dSql);
				ps.setInt(1, fieldId);
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

	/* (non-Javadoc)
	 * @see com.eaf.orig.shared.dao.OrigMasterFieldIdDAO#updateOrigMasterFieldIdM(com.eaf.orig.master.shared.model.FieldIdM)
	 */
	public double updateOrigMasterFieldIdM(FieldIdM fieldIdM)
			throws OrigApplicationMException {
		double returnRows = 0;
		PreparedStatement ps = null;
		Connection conn = null;
		try {
			//conn = Get Connection
			conn = getConnection();
			StringBuffer sql = new StringBuffer("");
			sql.append("UPDATE FIELD_ID ");
			
			sql.append(" SET FIELD_ID=?, FIELD_DESC=?, UPDATE_BY=?, UPDATE_DATE=SYSDATE ");
			
			sql.append(" WHERE FIELD_ID = ?");
			
			String dSql = String.valueOf(sql);
			log.debug("Sql="+dSql);
			ps = conn.prepareStatement(dSql);
			ps.setInt(1, fieldIdM.getFieldID());
			ps.setString(2, fieldIdM.getFieldDesc());
			ps.setString(3, fieldIdM.getUpdateBy());
			ps.setInt(4, fieldIdM.getFieldID());
			
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
	
	public FieldIdM selectOrigMasterFieldIdM(int fieldId)
			throws OrigApplicationMException {
		PreparedStatement ps = null;
		ResultSet rs = null;
		Connection conn = null;
		try {
			//conn = Get Connection
			conn = getConnection();
			StringBuffer sql = new StringBuffer("");
			
			sql.append(" SELECT FIELD_ID,FIELD_DESC ");
			sql.append(" FROM FIELD_ID  WHERE FIELD_ID = ? ");
			
			String dSql = String.valueOf(sql);
			log.debug("Sql=" + dSql);
			ps = conn.prepareStatement(dSql);
			rs = null;
			ps.setInt(1, fieldId);

			rs = ps.executeQuery();
			FieldIdM fieldIdM = null;

			if (rs.next()) {
				fieldIdM = new FieldIdM();
				fieldIdM.setFieldID(rs.getInt(1));
				fieldIdM.setFieldDesc(rs.getString(2));
				
			}
			return fieldIdM;

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
	
	public void createModelOrigMasterFieldIdM(FieldIdM fieldIdM)
			throws OrigApplicationMException {
		
//		try {
//			int fieldId=Integer.parseInt(ORIGDAOFactory.getUniqueIDGeneratorDAO2().getUniqByName(EJBConstant.FIELD_ID));
//			log.debug("/////fieldId gen = "+fieldId);
//			fieldIdM.setFieldID( fieldId);
//			createFieldIdM(fieldIdM);
//		} catch (Exception e) {
//			log.fatal("",e);
//			throw new OrigApplicationMException(e.getMessage());
//		}
		Connection conn = null;
		PreparedStatement ps = null;
		try{
			//conn = Get Connection
			conn = getConnection();
			StringBuffer sql = new StringBuffer("");
			sql.append(" INSERT INTO FIELD_ID ");
			sql.append(" (FIELD_ID, FIELD_DESC, UPDATE_BY, UPDATE_DATE) ");
			sql.append(" VALUES (?,?,?,SYSDATE)");
			String dSql = String.valueOf( sql);
			log.debug("dSql="+dSql);
			ps = conn.prepareStatement(dSql);
			
			ps.setInt(1, fieldIdM.getFieldID());
			ps.setString(2, fieldIdM.getFieldDesc());
			ps.setString(3, fieldIdM.getUpdateBy());
			
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
		
	
	
//	public void createFieldIdM(FieldIdM fieldIdM)
//	throws OrigApplicationMException {
//		
//		PreparedStatement ps = null;
//		try{
//			//conn = Get Connection
//			conn = getConnection();
//			StringBuffer sql = new StringBuffer("");
//			sql.append(" INSERT INTO FIELD_ID ");
//			sql.append(" (FIELD_ID, FIELD_DESC) ");
//			sql.append(" VALUES (?,?)");
//			String dSql = String.valueOf( sql);
//			log.debug("dSql="+dSql);
//			ps = conn.prepareStatement(dSql);
//			
//			ps.setInt(1, fieldIdM.getFieldID());
//			ps.setString(2, fieldIdM.getFieldDesc());
//			
//			ps.executeUpdate();
//			ps.close();
//		} catch (Exception e) {
//			log.fatal("",e);
//			throw new OrigApplicationMException(e.getMessage());
//		} finally {
//			try {
//				closeConnection(conn, ps);
//			} catch (Exception e) {
//				log.fatal("",e);
//			}
//		}
//	}

}








