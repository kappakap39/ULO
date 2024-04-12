/*
 * Created on Dec 17, 2007
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

import com.eaf.orig.master.shared.model.FicoM;
import com.eaf.orig.shared.constant.EJBConstant;
import com.eaf.orig.shared.dao.exceptions.OrigApplicationMException;
import com.eaf.orig.shared.ejb.ORIGGeneratorManager;
import com.eaf.orig.shared.service.ORIGEJBService;

/**
 * @author Administrator
 *
 * Type: OrigMasterFICODAOImpl
 */
public class OrigMasterFICODAOImpl extends OrigObjectDAO implements
		OrigMasterFICODAO {
	Logger log = Logger.getLogger(OrigMasterFICODAOImpl.class);
	
	/* (non-Javadoc)
	 * @see com.eaf.orig.shared.dao.OrigMasterFICODAO#getFicoLastInfo()
	 */
	public FicoM getFicoLastInfo() throws OrigApplicationMException {
		PreparedStatement ps = null;
		ResultSet rs = null;
		Connection conn = null;
		try {
			//conn = Get Connection
			conn = getConnection();
			StringBuffer sql = new StringBuffer("");
			
			sql.append(" SELECT ACCEPT_SCORE, REJECT_SCORE ");
			sql.append(" FROM FICO_SCORE  WHERE FICO_ID = (SELECT MAX(S.FICO_ID) FROM FICO_SCORE S ) ");
			
			String dSql = String.valueOf(sql);
			log.debug("Sql=" + dSql);
			ps = conn.prepareStatement(dSql);
			rs = null;

			rs = ps.executeQuery();
			FicoM ficoM = null;

			if (rs.next()) {
				ficoM = new FicoM();
				ficoM.setAcceptScore(rs.getDouble(1));
				ficoM.setRejectScore(rs.getDouble(2));
				
			}
			return ficoM;

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
	
	public void createModelOrigMasterFicoScore(FicoM ficoM)
			throws OrigApplicationMException {
		try {
			//String ficoID=ORIGDAOFactory.getUniqueIDGeneratorDAO2().getUniqByName(EJBConstant.FICO_ID);
			//ficoM.setFicoID(Integer.parseInt(ficoID));
			ORIGGeneratorManager generatorManager = ORIGEJBService.getGeneratorManager();
			String ficoID = generatorManager.generateUniqueIDByName(EJBConstant.FICO_ID);
			ficoM.setFicoID(Integer.parseInt(ficoID));
			createFicoM(ficoM);
		} catch (Exception e) {
			log.fatal("",e);
			throw new OrigApplicationMException(e.getMessage());
		}
	
	}
	
	public void createFicoM(FicoM ficoM)
			throws OrigApplicationMException {
		PreparedStatement ps = null;
		Connection conn = null;
		try{
			//conn = Get Connection
			conn = getConnection();
			StringBuffer sql = new StringBuffer("");
			sql.append(" INSERT INTO FICO_SCORE ");
			sql.append(" (FICO_ID, ACCEPT_SCORE, REJECT_SCORE, UPDATE_BY, UPDATE_DATE) ");
			sql.append(" VALUES (?,?,?,?,SYSDATE)");
			String dSql = String.valueOf( sql);
			log.debug("dSql="+dSql);
			ps = conn.prepareStatement(dSql);
			
			ps.setInt(1, ficoM.getFicoID());
			ps.setDouble(2, ficoM.getAcceptScore());
			ps.setDouble(3, ficoM.getRejectScore());
			ps.setString(4, ficoM.getUpdateBy());
			
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
