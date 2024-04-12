/*
 * Created on Jan 17, 2008
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
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

import org.apache.log4j.Logger;

import com.eaf.orig.shared.dao.exceptions.OrigApplicationLogMException;
import com.eaf.orig.shared.model.SMSDataM;

/**
 * @author Weeraya
 *
 * Type: OrigApplicaitonLogMDAOImpl
 */
public class OrigSmsLogDAOImpl extends OrigObjectDAO implements OrigSmsLogDAO {
	private static Logger log = Logger.getLogger(OrigSmsLogDAOImpl.class);
	
	/**
	 *  
	 */
	public OrigSmsLogDAOImpl() {
		super();
		
	}
	
	public void createModelSmsLogM(String appRecordId, SMSDataM prmSMSDataM)throws OrigApplicationLogMException {
		try {
			prmSMSDataM.setSeq(getNextSeq(appRecordId));
			createTableORIG_SMS_LOG(appRecordId, prmSMSDataM);
		} catch (Exception e) {
			log.error("Error >> ", e);
			throw new OrigApplicationLogMException(e.getMessage());
		}

	}

	/**
	 * @param prmApplicationLogM
	 */
	private void createTableORIG_SMS_LOG(String appRecordId, SMSDataM prmSMSDataM)throws OrigApplicationLogMException {
		PreparedStatement ps = null;
		Connection conn = null;
		try {
			//conn = Get Connection
			conn = getConnection();
			StringBuffer sql = new StringBuffer("");
			sql.append("INSERT INTO ORIG_SMS_LOG ");
			sql.append("( APPLICATION_RECORD_ID,SEQ ,SEND_TO, SEND_NO,MESSAGE, ");
			sql.append(" SEND_DATE, SEND_BY, SEND_STATUS, CREATE_DATE ,CREATE_BY )");
			sql.append(" VALUES(?,?,?,?,?,  ?,?,?,SYSDATE,?) ");
			String dSql = String.valueOf(sql);
			
			log.debug("SmsLog Sql="+dSql);
			log.debug("appRecordId="+appRecordId);
			
			ps = conn.prepareStatement(dSql);
			ps.setString(1, appRecordId);
			ps.setInt(2, prmSMSDataM.getSeq());
			ps.setString(3, prmSMSDataM.getTo());
			ps.setString(4, prmSMSDataM.getNumber());
			ps.setString(5, prmSMSDataM.getContent());
			ps.setDate(6, new Date(System.currentTimeMillis()));
			ps.setString(7, prmSMSDataM.getFrom());
			ps.setString(8, prmSMSDataM.getStatus());
			ps.setString(9, prmSMSDataM.getUpdateBy());
			ps.executeUpdate();
		} catch (Exception e) {
			log.error("Error >> ", e);
			throw new OrigApplicationLogMException(e.getMessage());
		} finally {
			try {
				closeConnection(conn, ps);
			} catch (Exception e) {
				log.error("Error Close Connection>> ", e);
			}
		}

	}
	
	public int getNextSeq(String applicationRecordId)throws OrigApplicationLogMException{
		try{
			return this.getMaxSeq(applicationRecordId)+1;
		}catch(Exception e){
			log.error(">>> getNextSeq has error",e);
			throw new OrigApplicationLogMException(e.toString());
		}
	}
	
	private int getMaxSeq(String applicationRecordId)throws OrigApplicationLogMException{
		ResultSet rs = null;
		PreparedStatement ps = null;
		Connection conn = null;
		try{
			conn = getConnection();
			String sql = "SELECT MAX(SEQ) FROM ORIG_SMS_LOG WHERE APPLICATION_RECORD_ID = ?";
	
			log.debug("getMaxSeq="+sql);
	
			ps = conn.prepareStatement(sql);
	
			ps.setString(1, applicationRecordId);
	
			rs = ps.executeQuery();
	
			if(rs.next()) return rs.getInt(1);
			return 0;

		}catch(Exception e){
			log.error(">>> getMaxSeq has error",e);
			throw new OrigApplicationLogMException(e.toString());
		}finally{
			try {
				closeConnection(conn, rs, ps);
			} catch (Exception e) {
				log.error(">>> closeConnection() has error",e);
				throw new OrigApplicationLogMException(e.getMessage());
			}
		}
	}
}
