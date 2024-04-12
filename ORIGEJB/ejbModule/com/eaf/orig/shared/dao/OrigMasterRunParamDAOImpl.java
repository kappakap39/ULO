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
import java.util.Vector;

import org.apache.log4j.Logger;

import com.eaf.orig.master.shared.model.RunningParamM;
import com.eaf.orig.shared.dao.exceptions.OrigApplicationMException;

/**
 * @author Administrator
 *
 * Type: OrigMasterRunParamDAOImpl
 */
public class OrigMasterRunParamDAOImpl extends OrigObjectDAO implements
		OrigMasterRunParamDAO {
	Logger log = Logger.getLogger(OrigMasterRunParamDAOImpl.class);
	
	/* (non-Javadoc)
	 * @see com.eaf.orig.shared.dao.OrigMasterRunParamDAO#updateOrigMasterRunParamM(com.eaf.orig.master.shared.model.RunningParamM)
	 */
	public double updateOrigMasterRunParamM(RunningParamM runParamM)
			throws OrigApplicationMException {
		double returnRows = updateTableOrigMasterRunParamM(runParamM);
		if(returnRows==0){
		  log.debug(" Can't Update Insert new Record");
		   InsertTableOrigMasterRunParamM(runParamM);
		}		
		  return returnRows;
	}
    private double updateTableOrigMasterRunParamM(RunningParamM runParamM)throws OrigApplicationMException{
    	double returnRows = 0;
		PreparedStatement ps = null;
		Connection conn = null;
		try {
			//conn = Get Connection
			conn = getConnection();
			StringBuffer sql = new StringBuffer("");
			sql.append("UPDATE RUNNING_PARAM ");
			
			sql.append(" SET PARAM_ID=?, PARAM_TYPE=?, PARAM_DESC=?, VALUE_FROM=?, VALUE_TO=?, VALUE1=?, VALUE2=?, UPDATE_BY=?, UPDATE_DATE=SYSDATE ");
			
			sql.append(" WHERE PARAM_ID = ? AND PARAM_TYPE=? ");
			
			String dSql = String.valueOf(sql);
			log.debug("Sql="+dSql);
			ps = conn.prepareStatement(dSql);
			ps.setString(1, runParamM.getParamID());
			ps.setString(2, runParamM.getParamType());
			ps.setString(3, runParamM.getParamDesc());
			ps.setDouble(4, runParamM.getValueFrom());
			ps.setDouble(5, runParamM.getValueTo());
			ps.setDouble(6, runParamM.getValue1());
			ps.setDouble(7, runParamM.getValue2());
			ps.setString(8, runParamM.getUpdateBy());
			ps.setString(9, runParamM.getParamID());
			ps.setString(10, runParamM.getParamType());
			
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
    private void InsertTableOrigMasterRunParamM(RunningParamM runParamM)throws OrigApplicationMException{
        PreparedStatement ps = null;
        Connection conn = null;
		try{
			//conn = Get Connection
			conn = getConnection();
			StringBuffer sql = new StringBuffer("");
			sql.append(" INSERT INTO RUNNING_PARAM ");
			sql.append(" (PARAM_ID, PARAM_TYPE, PARAM_DESC, VALUE_FROM,VALUE_TO ,VALUE1,VALUE2,UPDATE_BY,UPDATE_DATE) ");
			sql.append(" VALUES (?,?,?,?,?  ,?,?,?,SYSDATE)");
			String dSql = String.valueOf( sql);
			log.debug("dSql="+dSql);
			ps = conn.prepareStatement(dSql);
			
			ps.setString(1, runParamM.getParamID());
			ps.setString(2, runParamM.getParamType());
			ps.setString(3,runParamM.getParamDesc());
			ps.setDouble(4,runParamM.getValueFrom());
			ps.setDouble(5,runParamM.getValueTo());
			ps.setDouble(6,runParamM.getValue1());
			ps.setDouble(7,runParamM.getValue2());
			ps.setString(8,runParamM.getUpdateBy());
			ps.executeUpdate();
			ps.close();
		} catch (Exception e) {
			log.fatal("Insert Error",e);
			throw new OrigApplicationMException(e.getMessage());
		} finally {
			try {
				closeConnection(conn, ps);
			} catch (Exception e) {
				log.fatal("",e);
			}
		}
        
    }
	public RunningParamM selectOrigMasterRunParamM(String runPrmID,
			String runPrmType) throws OrigApplicationMException {
		RunningParamM runParamM = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		Connection conn = null;
		try {
			//conn = Get Connection
			conn = getConnection();
			StringBuffer sql = new StringBuffer("");
			
			sql.append(" SELECT PARAM_ID, PARAM_TYPE, PARAM_DESC, VALUE_FROM, VALUE_TO, VALUE1, VALUE2 ");
			sql.append(" FROM RUNNING_PARAM  WHERE PARAM_ID = ? AND PARAM_TYPE = ?");
			
			String dSql = String.valueOf(sql);
			log.debug("Sql=" + dSql);
			ps = conn.prepareStatement(dSql);
			rs = null;
			ps.setString(1, runPrmID);
			ps.setString(2, runPrmType);

			rs = ps.executeQuery();

			if(rs.next()) {
				runParamM = new RunningParamM();
				runParamM.setParamID(rs.getString(1));
				runParamM.setParamType(rs.getString(2));
				runParamM.setParamDesc(rs.getString(3));
				runParamM.setValueFrom(rs.getDouble(4));
				runParamM.setValueTo(rs.getDouble(5));
				runParamM.setValue1(rs.getDouble(6));
				runParamM.setValue2(rs.getDouble(7));
			}
			
			return runParamM;

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
    /* (non-Javadoc)
     * @see com.eaf.orig.shared.dao.OrigMasterRunParamDAO#deleteOrigMasterRunParamM(java.lang.String[])
     */
    public void deleteOrigMasterRunParamM(Vector runParamToDelete) throws OrigApplicationMException {
    	 
		//String[] busID;
		for(int i = 0; i < runParamToDelete.size(); i++){
			 deleteOrigMasterRunParamM( (RunningParamM)runParamToDelete.get(i));
		}        
    }
    /**
     * @param string
     */
    private void deleteOrigMasterRunParamM(RunningParamM runningPram)throws OrigApplicationMException {
    	PreparedStatement ps = null;
    	Connection conn = null;
		try {
				//conn = Get Connection
				conn = getConnection();
				StringBuffer sql = new StringBuffer("");
				sql.append("DELETE RUNNING_PARAM ");
				sql.append(" WHERE  PARAM_ID=? and PARAM_TYPE =?  ");
				String dSql = String.valueOf(sql);
				log.debug("Sql=" + dSql);
				 
				ps = conn.prepareStatement(dSql);
				ps.setString(1,runningPram.getParamID());
				ps.setString(2,runningPram.getParamType());
				ps.executeUpdate(); 
		
		} catch (Exception e) {
			log.fatal(e.getStackTrace());
			throw new   OrigApplicationMException(e.getMessage());
		} finally {
			try {
				closeConnection(conn, ps);
			} catch (Exception e) {
				log.fatal(e.getStackTrace());
				throw new OrigApplicationMException(e.getMessage());
			}
		}
        
    }
	

}
