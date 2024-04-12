package com.eaf.orig.ulo.pl.app.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.Vector;
import org.apache.log4j.Logger;
import com.eaf.orig.profile.model.UserDetailM;
import com.eaf.orig.shared.constant.OrigConstant;
import com.eaf.orig.shared.dao.OrigObjectDAO;
import com.eaf.orig.ulo.pl.app.dao.exception.PLOrigApplicationException;
import com.eaf.orig.ulo.pl.model.app.PLImportCreditLineDataM;
import com.eaf.orig.ulo.pl.model.app.PLResponseImportDataM;

public class PLOrigImportCreditLineDataDAOImpl extends OrigObjectDAO implements
		PLOrigImportCreditLineDataDAO {
	private static Logger logger = Logger.getLogger(PLOrigImportCreditLineDataDAOImpl.class);
	/*
	public Vector<PLImportCreditLineDataM> saveTable_ORIG_IMPORT_CREDIT_LINE_DATA (Vector<PLImportCreditLineDataM> importCreditLineVect) throws PLOrigApplicationException{
		Vector<PLImportCreditLineDataM> errorImportVect = new Vector<PLImportCreditLineDataM>();
		if(importCreditLineVect != null && importCreditLineVect.size() > 0){
			for(int i=0;i<importCreditLineVect.size();i++){
				PLImportCreditLineDataM importM = (PLImportCreditLineDataM)importCreditLineVect.get(i);
				if(importM != null){
					logger.debug("@@@@@ Process [cardNo="+importM.getCardNo()+"][creditLine="+importM.getCreditLine()+"]");
					try{
						//validate card no format
						Long.parseLong(importM.getCardNo());
						try{
							//validate credit line format
							new BigDecimal(importM.getCreditLine()).setScale(2, RoundingMode.HALF_UP);
							//call create credit line data
							try{
								String responseCode = this.callCreate_ORIG_IMPORT_CREDIT_LINE_DATA(importM);
								//if found duplicate
								if("DUPLICATE".equals(responseCode)){
									importM.setReason("CARD_NO_DUPLICATE");
									errorImportVect.add(importM);
								}
							}catch(Exception e){
								//if call database error
								importM.setReason("SYSTEM_ERROR");
								errorImportVect.add(importM);
							}
						}catch (Exception e){
							// wrong credit line format
							logger.error("##### Wrong format Credit Line :"+importM.getCreditLine());
							importM.setReason("CREDIT_LINE_FORMAT");
							errorImportVect.add(importM);
						}
					}catch (Exception e){
						//wrong card format
						logger.error("##### Wrong format Card No :"+importM.getCardNo());
						importM.setReason("CARD_NO_FORMAT");
						errorImportVect.add(importM);
					}
				}
			}
		}
		return errorImportVect;
	}*/
	
	public void saveTable_ORIG_CREDIT_LINE_IMPORT_DETAIL (Vector<PLImportCreditLineDataM> importCreditLineVect) throws PLOrigApplicationException{
		try{
			if(importCreditLineVect != null && importCreditLineVect.size() > 0){
				for(int i=0;i<importCreditLineVect.size();i++){
					PLImportCreditLineDataM importM = (PLImportCreditLineDataM)importCreditLineVect.get(i);
					if(importM != null){
//						logger.debug("@@@@@ Process [cardNo="+importM.getCardNo()+"][creditLine="+importM.getCreditLine()+"]");
						callCreate_ORIG_CREDIT_LINE_IMPORT_DETAIL(importM);
					}
				}
			}
		}catch (Exception e){
			e.printStackTrace();
			throw new PLOrigApplicationException(e.getMessage());
		}
	}
	
	private void callCreate_ORIG_CREDIT_LINE_IMPORT_DETAIL(PLImportCreditLineDataM importM) throws PLOrigApplicationException{
		PreparedStatement ps = null;		
		Connection conn = null;
		try {			
			conn = getConnection();
			StringBuilder sql = new StringBuilder("");
			sql.append("insert into orig_creditline_import_detail ");
			sql.append("(credit_import_id,session_id,card_no,credit_line,create_by,create_date,update_by,update_date) ");
			sql.append("values (credit_import_seq.nextval, ?, ?, ?, ?, sysdate, ?, sysdate)");
			
			String dSql = String.valueOf(sql);
			logger.debug("Sql=" + dSql);
			ps = conn.prepareStatement(dSql);
			ps.setString(1, importM.getSessionId());
			ps.setString(2, importM.getCardNo());
			ps.setString(3, importM.getCreditLine());
			ps.setString(4, importM.getCreateBy());
			ps.setString(5, importM.getUpdateBy());
			
			ps.executeUpdate();
		}catch (Exception e) {
			logger.fatal(e.getLocalizedMessage());
			throw new PLOrigApplicationException(e.getMessage());
		} finally {
			try {
				closeConnection(conn, ps);
			} catch (Exception e) {
				logger.fatal(e.getLocalizedMessage());
				throw new PLOrigApplicationException(e.getMessage());
			}
		}
	}

	@Override
	public void processAutoIncreaseDecrease(String sessionId) throws PLOrigApplicationException {
		// TODO Auto-generated method stub
		PreparedStatement ps = null;
		Connection conn = null;
		try {
			//conn = Get Connection
			conn = getConnection();
			StringBuffer sql = new StringBuffer("");
			sql.append(" CALL pka_auto_icdc.auto_increase_decrease(?)");
			String dSql = String.valueOf(sql);
			logger.debug("@@@@@ processAutoIncreaseDecrease SQL:"+dSql);
			ps = conn.prepareCall(dSql);
			ps.setString(1,sessionId);
			ps.executeQuery();
		} catch (Exception e) {
			e.printStackTrace();
			throw new PLOrigApplicationException(e.getMessage());
		} finally {
			try {
				closeConnection(conn, ps);
			} catch (Exception e) {
				logger.fatal(e.getLocalizedMessage());
				throw new PLOrigApplicationException(e.getMessage());
			}
		}
	}

	@Override
	public PLResponseImportDataM loadResultAutoIncreaseDecrease(String sessionId) throws PLOrigApplicationException {
		// TODO Auto-generated method stub
		PLResponseImportDataM resultResponseM = null;
		try{
			resultResponseM = this.load_ORIG_CREDIT_LINE_IMPORT_MASTER(sessionId);
			resultResponseM.setErrorImportVt(this.load_REJECT_ORIG_CREDIT_LINE_IMPORT_DETAIL(sessionId));
			return resultResponseM;
		}catch (Exception e){
			e.printStackTrace();
			throw new PLOrigApplicationException(e.getMessage());
		}
	}

	@Override
	public void saveTable_ORIG_CREDIT_LINE_IMPORT_MASTER(String sessionId, String attachmentId, UserDetailM userM) throws PLOrigApplicationException {
		PreparedStatement ps = null;	
		Connection conn = null;
		try {			
			conn = getConnection();
			StringBuilder sql = new StringBuilder("");
			sql.append("insert into orig_creditline_import_master ");
			sql.append("(session_id, attachment_id, create_by, create_date, update_by, update_date) ");
			sql.append("values (?, ?, ?, sysdate, ?, sysdate)");
			
			String dSql = String.valueOf(sql);
			logger.debug("Sql=" + dSql);
			ps = conn.prepareStatement(dSql);
			ps.setString(1, sessionId);
			ps.setString(2, attachmentId);
			ps.setString(3, userM.getUserName());
			ps.setString(4, userM.getUserName());
			
			ps.executeUpdate();
		}catch (Exception e) {
			logger.fatal(e.getLocalizedMessage());
			throw new PLOrigApplicationException(e.getMessage());
		} finally {
			try {
				closeConnection(conn, ps);
			} catch (Exception e) {
				logger.fatal(e.getLocalizedMessage());
				throw new PLOrigApplicationException(e.getMessage());
			}
		}
	}
	
	private PLResponseImportDataM load_ORIG_CREDIT_LINE_IMPORT_MASTER(String sessionId) throws PLOrigApplicationException {
		PreparedStatement ps = null;
		ResultSet rs = null;
		PLResponseImportDataM responseM = null;
		Connection conn = null;
		try {
			conn = getConnection();
			StringBuilder sql = new StringBuilder("");
			sql.append("SELECT * ");
			sql.append(" FROM ORIG_CREDITLINE_IMPORT_MASTER WHERE SESSION_ID = ? ");
			String dSql = String.valueOf(sql);
			logger.debug("Sql=" + dSql);
			ps = conn.prepareStatement(dSql);
			rs = null;
			ps.setString(1, sessionId);

			rs = ps.executeQuery();
			
			if (rs.next()) {
				responseM = new PLResponseImportDataM(); 
				responseM.setTotalRecord(rs.getInt("total_record"));
				responseM.setSuccessRecord(rs.getInt("complete_record"));
				responseM.setErrorRecord(rs.getInt("reject_record"));
			}
			
			return responseM;
			
		} catch (Exception e) {
			logger.fatal(e.getLocalizedMessage());
			throw new PLOrigApplicationException(e.getMessage());
		} finally {
			try {
				closeConnection(conn, rs, ps);
			} catch (Exception e) {
				logger.fatal(e.getLocalizedMessage());
				throw new PLOrigApplicationException(e.getMessage());
			}
		}
	}
	
	private Vector<PLImportCreditLineDataM> load_REJECT_ORIG_CREDIT_LINE_IMPORT_DETAIL(String sessionId) throws PLOrigApplicationException {
		PreparedStatement ps = null;
		ResultSet rs = null;
		Vector<PLImportCreditLineDataM> rejectVT = new Vector<PLImportCreditLineDataM>();
		Connection conn = null;
		try {
			conn = getConnection();
			StringBuilder sql = new StringBuilder("");
			sql.append("SELECT * ");
			sql.append(" FROM orig_creditline_import_detail WHERE SESSION_ID = ? and STATUS = ?");
			String dSql = String.valueOf(sql);
			logger.debug("Sql=" + dSql);
			ps = conn.prepareStatement(dSql);
			rs = null;
			ps.setString(1, sessionId);
			ps.setString(2, OrigConstant.Action.REJECT);

			rs = ps.executeQuery();
			
			while (rs.next()) {
				PLImportCreditLineDataM rejectM = new PLImportCreditLineDataM();
				rejectM.setSessionId(sessionId);
				rejectM.setCardNo(rs.getString("card_no"));
				rejectM.setCreditLine(rs.getString("credit_line"));
				rejectM.setReason(rs.getString("reason"));
				
				rejectVT.add(rejectM);
			}
			if(rejectVT.size() > 0){
				return rejectVT;
			}else{
				return null;
			}
			
		} catch (Exception e) {
			logger.fatal(e.getLocalizedMessage());
			throw new PLOrigApplicationException(e.getMessage());
		} finally {
			try {
				closeConnection(conn, rs, ps);
			} catch (Exception e) {
				logger.fatal(e.getLocalizedMessage());
				throw new PLOrigApplicationException(e.getMessage());
			}
		}
	}
	
	public void delete_ORIG_CREDIT_LINE_IMPORT_MASTER(String sessionId) throws PLOrigApplicationException {
		PreparedStatement ps = null;
		Connection conn = null;
		try {
			conn = getConnection();
			StringBuilder sql = new StringBuilder("");

			sql.append("DELETE FROM ORIG_CREDITLINE_IMPORT_MASTER ");
			sql.append("WHERE SESSION_ID = ?");
			String dSql = String.valueOf(sql);
			logger.debug("Sql=" + dSql);
			ps = conn.prepareStatement(dSql);
			
			ps.setString(1, sessionId);

			ps.executeUpdate();			
		} catch (Exception e) {
			logger.fatal(e.getLocalizedMessage());
			throw new PLOrigApplicationException(e.getMessage());
		} finally {
			try {
				closeConnection(conn, ps);
			} catch (Exception e) {
				logger.fatal(e.getLocalizedMessage());
				throw new PLOrigApplicationException(e.getMessage());
			}
		}
	}
}
