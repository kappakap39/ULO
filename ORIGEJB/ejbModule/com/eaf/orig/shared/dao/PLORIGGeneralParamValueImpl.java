package com.eaf.orig.shared.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

import org.apache.log4j.Logger;

import com.eaf.orig.master.shared.model.GeneralParamM;
import com.eaf.orig.ulo.pl.app.dao.PLORIGGeneralParamValue;
import com.eaf.orig.ulo.pl.app.dao.exception.PLOrigApplicationException;
//import com.eaf.orig.ulo.pl.app.utility.DataFormatUtility;

public class PLORIGGeneralParamValueImpl extends OrigObjectDAO implements PLORIGGeneralParamValue {
	private static Logger log = Logger.getLogger(PLORIGGeneralParamValueImpl.class);
	
	@Override
	public GeneralParamM getGeneralParamvalueWithBussclass(String paramCode, String busClass) throws PLOrigApplicationException {
		PreparedStatement ps = null;
		ResultSet rs = null;
		GeneralParamM genResult = null;
		Connection conn = null;
		try {
			conn = getConnection();
			StringBuilder sql = new StringBuilder();
			sql.append("SELECT	* FROM GENERAL_PARAM p,	BUS_CLASS_GRP_MAP b ");
			sql.append("WHERE P.BUS_CLASS_ID = B.BUS_GRP_ID AND P.PARAM_CODE = ? ");
			sql.append("AND B.BUS_CLASS_ID = ? ");
			sql.append("AND rownum = 1 ");

			
			String dSql = String.valueOf(sql);
			log.debug("Sql=" + dSql);
			ps = conn.prepareStatement(dSql);
			ps.setString(1, paramCode);
			ps.setString(2, busClass);
			rs = ps.executeQuery();
			if(rs.next()){
			genResult = new GeneralParamM();
			genResult.setParamCode(rs.getString("PARAM_CODE"));
			genResult.setParamValue(rs.getString("PARAM_VALUE"));
			genResult.setParamValue2(rs.getString("PARAM_VALUE2"));
			genResult.setBusClassID(rs.getString("BUS_CLASS_ID"));
			genResult.setUpdateBy(rs.getString("UPDATE_BY"));
			genResult.setUpdateDate(rs.getDate("UPDATE_DATE"));
			
			}
			
		} catch (Exception e) {
			log.fatal(e.getLocalizedMessage());
			throw new PLOrigApplicationException(e.getMessage());
		} finally {
			try {
				closeConnection(conn, rs, ps);
			} catch (Exception e) {
				log.fatal(e.getLocalizedMessage());
				throw new PLOrigApplicationException(e.getMessage());
			}
		}
		return genResult;
		}
	@Override
	public GeneralParamM getGeneralParamvalue(String paramCode) throws PLOrigApplicationException {
		PreparedStatement ps = null;
		ResultSet rs = null;
		GeneralParamM genResult = null;
		Connection conn = null;
		try {
			conn = getConnection();
			StringBuilder sql = new StringBuilder();
			sql.append("SELECT	* FROM GENERAL_PARAM p,	BUS_CLASS_GRP_MAP b ");
			sql.append("WHERE P.BUS_CLASS_ID = B.BUS_GRP_ID AND P.PARAM_CODE = ? ");		
			String dSql = String.valueOf(sql);
			log.debug("Sql=" + dSql);
			ps = conn.prepareStatement(dSql);
			ps.setString(1, paramCode);
			rs = ps.executeQuery();
			if(rs.next()){
				if(rs.next()){
					genResult = new GeneralParamM();
					genResult.setParamCode(rs.getString("PARAM_CODE"));
					genResult.setParamValue(rs.getString("PARAM_VALUE"));
					genResult.setParamValue2(rs.getString("PARAM_VALUE2"));
					genResult.setBusClassID(rs.getString("BUS_CLASS_ID"));
					genResult.setUpdateBy(rs.getString("UPDATE_BY"));
//					genResult.setUpdateDate(DataFormatUtility.stringToDate(rs.getString("UPDATE_DATE"),"ddmmyyyy"));
					
				}
			}
			
		} catch (Exception e) {
			log.fatal(e.getLocalizedMessage());
			throw new PLOrigApplicationException(e.getMessage());
		} finally {
			try {
				closeConnection(conn, rs, ps);
			} catch (Exception e) {
				log.fatal(e.getLocalizedMessage());
				throw new PLOrigApplicationException(e.getMessage());
			}
		}
		return genResult;
		}
	}
