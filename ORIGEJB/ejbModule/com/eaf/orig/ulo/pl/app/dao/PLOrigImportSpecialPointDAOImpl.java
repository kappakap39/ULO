package com.eaf.orig.ulo.pl.app.dao;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Vector;
import org.apache.log4j.Logger;

import com.eaf.orig.shared.constant.OrigConstant;
import com.eaf.orig.shared.dao.OrigObjectDAO;
import com.eaf.orig.shared.util.OrigUtil;
import com.eaf.orig.ulo.pl.app.dao.exception.PLOrigApplicationException;
import com.eaf.orig.ulo.pl.model.app.PLImportSpecialPointDataM;

public class PLOrigImportSpecialPointDAOImpl extends OrigObjectDAO implements
		PLOrigImportSpecialPointDAO {
	
	private static Logger log = Logger.getLogger(PLOrigImportSpecialPointDAOImpl.class);

	@Override
	public Vector<PLImportSpecialPointDataM> saveTableOrig_Special_Point(Vector<PLImportSpecialPointDataM> importSpePointVect, Date dataDate) throws PLOrigApplicationException {
		
		int returnRow = selectTableOrig_Special_Point(dataDate);
		if (returnRow != 0) {
			deleteTableOrig_Special_Point(dataDate);
		}
		if (importSpePointVect != null && importSpePointVect.size() > 0) {
			
			Vector<PLImportSpecialPointDataM> ErrorimportSpePointVect = new Vector<PLImportSpecialPointDataM>();

			for (int i = 0; i < importSpePointVect.size(); i++) {
				PLImportSpecialPointDataM importSpePointM = importSpePointVect.get(i);
				
				if (OrigUtil.isEmptyString(importSpePointM.getReason())) {
					PLImportSpecialPointDataM ErrorimportSpePointM = insertTableOrig_Special_Point(importSpePointM, dataDate);
					if (ErrorimportSpePointM == importSpePointM) {
						ErrorimportSpePointVect.add(ErrorimportSpePointM);
					}
				} else {
					ErrorimportSpePointVect.add(importSpePointM);
				}
				
			}
			
			return ErrorimportSpePointVect;
		}

		return null;
	}
	
	private int selectTableOrig_Special_Point(Date dataDate) throws PLOrigApplicationException {
		Connection conn = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		int returnRow = 0;
		try {
			conn = getConnection();
			StringBuilder sql = new StringBuilder("SELECT USER_ID FROM USER_SPECIAL_POINT WHERE TRUNC(DATA_DATE) = TRUNC(?)");
			String dSql = String.valueOf(sql);
			log.debug("Sql=" + dSql);
			ps = conn.prepareStatement(dSql);
			ps.setDate(1, dataDate);

			rs = ps.executeQuery();
			if(rs.next()){
				returnRow = 1;
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
		return returnRow;
	}
	
	private void deleteTableOrig_Special_Point(Date dataDate) throws PLOrigApplicationException {
		Connection conn = null;
		PreparedStatement ps = null;
		try {
			conn = getConnection();
			StringBuilder sql = new StringBuilder("");
			sql.append("DELETE USER_SPECIAL_POINT ");
			sql.append(" WHERE TRUNC(DATA_DATE) = TRUNC(?)");
			String dSql = String.valueOf(sql);
			log.debug("Sql="+dSql);
			ps = conn.prepareStatement(dSql);
			ps.setDate(1, dataDate);
			ps.executeUpdate();

		} catch (Exception e) {
			log.fatal(e.getLocalizedMessage());
			throw new PLOrigApplicationException(e.getMessage());
		} finally {
			try {
				closeConnection(conn, ps);
			} catch (Exception e) {
				log.fatal(e.getLocalizedMessage());
				throw new PLOrigApplicationException(e.getMessage());
			}
		}
		
	}
	
	private PLImportSpecialPointDataM insertTableOrig_Special_Point(PLImportSpecialPointDataM importSpePointM, Date dataDate) throws PLOrigApplicationException {
		Connection conn = null;
		PreparedStatement ps = null;
		StringBuilder reason = new StringBuilder();
		reason.append(importSpePointM.getReason()).append(", ");
		try {

			conn = getConnection();
			StringBuilder sql = new StringBuilder("");
			sql.append("INSERT INTO USER_SPECIAL_POINT ");
			sql.append("( USER_ID, SPECIAL_WORK_HRS, SPECIAL_WORK_POINT, OT_HRS , OT_APP ");
			sql.append(", OT_POINT, REMARK, DATA_DATE, CREATE_DATE, CREATE_BY ");
			sql.append(", UPDATE_DATE, UPDATE_BY, SPECIAL_WORK_APP ) ");
			sql.append(" VALUES(?,?,?,?,?    ,?,?,?,SYSDATE,?   ,SYSDATE,?,? ) ");
			String dSql = String.valueOf(sql);
			log.debug("Sql=" + dSql);
			ps = conn.prepareStatement(dSql);
			
			ps.setString(1, importSpePointM.getUserId());
			ps.setBigDecimal(2, importSpePointM.getSpecialWork_hrs());
			ps.setInt(3, importSpePointM.getSpecialWorkPoint());
			ps.setBigDecimal(4, importSpePointM.getOt_hrs());
			ps.setInt(5, importSpePointM.getOt_app());
			
			ps.setInt(6, importSpePointM.getOt_point());
			ps.setString(7, importSpePointM.getRemark());
			ps.setDate(8, importSpePointM.getDataDate());
			ps.setString(9, importSpePointM.getCreateBy());
			
			ps.setString(10, importSpePointM.getUpdateBy());
			ps.setInt(11, importSpePointM.getSpecialWorkApp());
			
			ps.executeUpdate();
			
		} catch (SQLException sqle) {
			if (sqle.getErrorCode() == 1403) {// ORA-01403: no data found
				reason.append(OrigConstant.ErrorMsg.DATA_NOT_FOUND);
			} else if (sqle.getErrorCode() == 1840){
				reason.append(OrigConstant.ErrorMsg.DATA_TOO_LARGE);
			} else if (sqle.getErrorCode() == 2291){
				reason.append(OrigConstant.ErrorMsg.NOT_FOUND_USER_ID);
			} else if (sqle.getErrorCode() == 1722){
				reason.append(OrigConstant.ErrorMsg.INVALID_NUMBER);
			} else if (sqle.getErrorCode() == 1858){
				reason.append(OrigConstant.ErrorMsg.FOUND_CHARACTER_IN_COLUMN_DATE);
			} else if (sqle.getErrorCode() == 12899){
				reason.append(OrigConstant.ErrorMsg.VALUE_TOO_LARGE);
			} else {
				reason.append("Error");
			}
			return importSpePointM;
		} catch (Exception e) {
			log.fatal(e.getLocalizedMessage());
			throw new PLOrigApplicationException(e.getMessage());
		} finally {
			try {
				closeConnection(conn, ps);
			} catch (Exception e) {
				log.fatal(e.getLocalizedMessage());
				throw new PLOrigApplicationException(e.getMessage());
			}
		}
		return new PLImportSpecialPointDataM();
	}

}
