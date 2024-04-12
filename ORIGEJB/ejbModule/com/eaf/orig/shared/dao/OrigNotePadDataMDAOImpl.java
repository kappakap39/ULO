/*
 * Created on Oct 2, 2007
 * Created by Sankom Sanpunya
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

import com.eaf.orig.shared.constant.EJBConstant;
import com.eaf.orig.shared.dao.exceptions.OrigNotePadDataMException;
import com.eaf.orig.shared.ejb.ORIGGeneratorManager;
import com.eaf.orig.shared.model.NotePadDataM;
import com.eaf.orig.ulo.pl.app.dao.exception.PLOrigApplicationException;
import com.eaf.orig.ulo.pl.service.PLORIGEJBService;

/**
 * @author Sankom Sanpunya
 * 
 * Type: OrigNotePadDataMDAOImpl
 */
public class OrigNotePadDataMDAOImpl extends OrigObjectDAO implements OrigNotePadDataMDAO {
	private static Logger log = Logger.getLogger(OrigNotePadDataMDAOImpl.class);

	public OrigNotePadDataMDAOImpl() {
		super();
		
	}

	public void createModelOrigNotePadDataM(NotePadDataM prmNotePadDataM)throws OrigNotePadDataMException {
		try {
			createTableORIG_NOTE_PAD_DATA(prmNotePadDataM);
		} catch (Exception e) {
			log.fatal(e.getLocalizedMessage());
			throw new OrigNotePadDataMException(e.getMessage());
		}
	}

	private void createTableORIG_NOTE_PAD_DATA(NotePadDataM prmNotePadDataM)throws OrigNotePadDataMException {
		PreparedStatement ps = null;
		Connection conn = null;
		try {
			ORIGGeneratorManager generatorManager = PLORIGEJBService.getGeneratorManager();
			int notePadDataID = Integer.parseInt(generatorManager.generateUniqueIDByName(EJBConstant.NOTE_PAD_DATA_ID));

			conn = getConnection();
			StringBuilder sql = new StringBuilder("");
			sql.append("INSERT INTO ORIG_NOTE_PAD_DATA ");
			sql.append("( NOTE_PAD_ID ,APPLICATION_RECORD_ID,NOTE_PAD_DESC ");
			sql.append(" ,CREATE_BY,CREATE_DATE,UPDATE_BY,UPDATE_DATE)");
			sql.append(" VALUES(?,?,?   ,?,?,?,? ) ");
			String dSql = String.valueOf(sql);
			log.debug("Sql=" + dSql);
			
			ps = conn.prepareStatement(dSql);
			ps.setInt(1, notePadDataID);
			ps.setString(2, prmNotePadDataM.getApplicationRecordId());
			ps.setString(3, prmNotePadDataM.getNotePadDesc());
			ps.setString(4, prmNotePadDataM.getCreateBy());
			ps.setTimestamp(5, prmNotePadDataM.getCreateDate());
			ps.setString(6, prmNotePadDataM.getUpdateBy());
			ps.setTimestamp(7, prmNotePadDataM.getUpdateDate());
			ps.executeUpdate();
		} catch (Exception e) {
			log.fatal(e.getLocalizedMessage());
			throw new OrigNotePadDataMException(e.getMessage());
		} finally {
			try {
				closeConnection(conn, ps);
			} catch (Exception e) {
				log.fatal(e.getLocalizedMessage());
			}
		}

	}

	public void deleteModelOrigNotePadDataM(NotePadDataM prmNotePadDataM)throws OrigNotePadDataMException {
		try {
			deleteTableORIG_NOTE_PAD_DATA(prmNotePadDataM);
		} catch (Exception e) {
			log.fatal(e.getLocalizedMessage());
			throw new OrigNotePadDataMException(e.getMessage());
		}

	}

	private void deleteTableORIG_NOTE_PAD_DATA(NotePadDataM prmNotePadDataM)throws OrigNotePadDataMException {
		PreparedStatement ps = null;
		Connection conn = null;
		try {
			//conn = Get Connection
			conn = getConnection();
			StringBuilder sql = new StringBuilder("");
			sql.append("DELETE ORIG_NOTE_PAD_DATA ");
			sql.append(" WHERE APPLICATION_RECORD_ID = ? AND NOTE_PAD_ID =? ");
			String dSql = String.valueOf(sql);
			log.debug("Sql=" + dSql);
			ps = conn.prepareStatement(dSql);
			ps.setString(1, prmNotePadDataM.getApplicationRecordId());
			ps.setInt(2, prmNotePadDataM.getNotePadId());
			ps.executeUpdate();

		} catch (Exception e) {
			log.fatal(e.getLocalizedMessage());
			throw new OrigNotePadDataMException(e.getMessage());
		} finally {
			try {
				closeConnection(conn, ps);
			} catch (Exception e) {
				log.fatal(e.getLocalizedMessage());
				throw new OrigNotePadDataMException(e.getMessage());
			}
		}

	}

	public Vector<NotePadDataM> loadModelOrigNotePadDataM(String applicationRecordId)throws OrigNotePadDataMException {
		try {
			return selectTableORIG_NOTE_PAD_DATA(applicationRecordId);
		} catch (Exception e) {
			log.fatal(e.getLocalizedMessage());
			throw new OrigNotePadDataMException(e.getMessage());
		}
	}

	private Vector<NotePadDataM> selectTableORIG_NOTE_PAD_DATA(String applicationRecordId) throws OrigNotePadDataMException {
		Connection conn = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		Vector<NotePadDataM> vNotePadData = new Vector<NotePadDataM>();
		try{
			conn = getConnection();
			StringBuilder sql = new StringBuilder("");
			sql.append("SELECT NOTE_PAD_ID,NOTE_PAD_DESC ");
			sql.append(" ,CREATE_BY,CREATE_DATE,UPDATE_BY,UPDATE_DATE ");
			sql.append(" FROM ORIG_NOTE_PAD_DATA WHERE APPLICATION_RECORD_ID = ? ORDER BY CREATE_DATE ");
			String dSql = String.valueOf(sql);
			log.debug("Sql=" + dSql);
			ps = conn.prepareStatement(dSql);
			rs = null;
			ps.setString(1, applicationRecordId);

			rs = ps.executeQuery();
			while (rs.next()) {
				NotePadDataM notePadDataM = new NotePadDataM();
				notePadDataM.setNotePadId(rs.getInt(1));
				notePadDataM.setApplicationRecordId(applicationRecordId);
				notePadDataM.setNotePadDesc(rs.getString(2));
				//notePadDataM.setNotePadType(rs.getString(3));
				//notePadDataM.setAccountId(rs.getString(5));
				notePadDataM.setCreateBy(rs.getString(3));
				notePadDataM.setCreateDate(rs.getTimestamp(4));
				notePadDataM.setUpdateBy(rs.getString(5));
				notePadDataM.setUpdateDate(rs.getTimestamp(6));
				vNotePadData.add(notePadDataM);
			}
		}catch(Exception e){
			log.fatal(e.getLocalizedMessage());
			throw new OrigNotePadDataMException(e.getMessage());
		}finally{
			try{
				closeConnection(conn, rs, ps);
			}catch(Exception e){
				log.fatal(e.getLocalizedMessage());
			}
		}
		return vNotePadData;
	}

	public void saveUpdateModelOrigNotePadDataM(NotePadDataM prmNotePadDataM)throws OrigNotePadDataMException {
		double returnRows = 0;
		try {
			returnRows = updateTableORIG_NOTE_PAD_DATA(prmNotePadDataM);
			if (returnRows == 0) {
				log.debug("New record then can't update record in table ORIG_NOTE_PAD_DATA then call Insert method");
							
				createTableORIG_NOTE_PAD_DATA(prmNotePadDataM);
			}
		} catch (Exception e) {
			log.fatal(e.getLocalizedMessage());
			throw new OrigNotePadDataMException(e.getMessage());
		}

	}
	@Override
	public void saveUpdateModelOrigNotePadDataVect(Vector<NotePadDataM> notePadVect, String appRecId) throws PLOrigApplicationException {
		try {
			for(NotePadDataM notePadM :notePadVect){
				notePadM.setApplicationRecordId(appRecId);
				double returnRows = this.updateTableORIG_NOTE_PAD_DATA(notePadM);
				if(returnRows == 0){
					this.createTableORIG_NOTE_PAD_DATA(notePadM);
				}
			}
		} catch (Exception e) {
			log.fatal(e.getLocalizedMessage());
			throw new PLOrigApplicationException(e.getMessage());
		}
	}

	private double updateTableORIG_NOTE_PAD_DATA(NotePadDataM prmNotePadDataM)throws OrigNotePadDataMException {
		double returnRows = 0;
		PreparedStatement ps = null;
		Connection conn = null;
		try{
			conn = getConnection();
			StringBuilder sql = new StringBuilder("");
			sql.append("UPDATE ORIG_NOTE_PAD_DATA ");
			sql.append(" SET  NOTE_PAD_DESC=?,UPDATE_BY=?,UPDATE_DATE=SYSDATE ");
			sql.append(" WHERE APPLICATION_RECORD_ID = ? AND NOTE_PAD_ID =? ");
			String dSql = String.valueOf(sql);
			log.debug("Sql=" + dSql);
			ps = conn.prepareStatement(dSql);
            ps.setString(1,prmNotePadDataM.getNotePadDesc());
            ps.setString(2,prmNotePadDataM.getUpdateBy());
            ps.setString(3,prmNotePadDataM.getApplicationRecordId());
            ps.setInt(4,prmNotePadDataM.getNotePadId());
			returnRows = ps.executeUpdate();
		} catch (Exception e) {
			log.fatal(e.getLocalizedMessage());
			throw new OrigNotePadDataMException(e.getMessage());
		} finally {
			try {
				closeConnection(conn, ps);
			} catch (Exception e) {
				log.fatal(e.getLocalizedMessage());
			}
		}
		return returnRows;
	}

}
