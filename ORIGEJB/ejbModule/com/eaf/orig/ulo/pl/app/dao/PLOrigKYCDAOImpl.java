package com.eaf.orig.ulo.pl.app.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import org.apache.log4j.Logger;
import com.eaf.orig.shared.dao.OrigObjectDAO;
import com.eaf.orig.ulo.pl.app.dao.exception.PLOrigApplicationException;
import com.eaf.orig.ulo.pl.model.app.PLKYCDataM;

public class PLOrigKYCDAOImpl extends OrigObjectDAO implements PLOrigKYCDAO {

	private Logger log = Logger.getLogger(this.getClass());
	
	@Override
	public void saveUpdate(PLKYCDataM kycM, String appRecId) throws PLOrigApplicationException{
		try{
			if(kycM!=null){
				int returnRow = this.updateTable_Oirg_KYC(kycM, appRecId);
				if(returnRow == 0){
					this.insertTable_Oirg_KYC(kycM, appRecId);
				}
			}
		} catch (Exception e){
			log.fatal(e.getLocalizedMessage());
			throw new PLOrigApplicationException(e.getMessage());
		}
	}
	
	private int updateTable_Oirg_KYC(PLKYCDataM kycM, String appRecId) throws PLOrigApplicationException {
		
		PreparedStatement ps = null;
		Connection conn = null;
		try {
			
			conn = getConnection();
			StringBuilder sql = new StringBuilder("");
			sql.append("UPDATE ORIG_KYC SET APPLICATION_RECORD_ID=?, RELATION_FLAG=?, REL_NAME=?, REL_SURNAME=?, REL_POSITION=? ");
			sql.append(", REL_DETAIL=?, WORK_START_DATE=?, WORK_END_DATE=?, SANCTION_LIST=?, CUSTOMER_RISK_GRADE=? ");
			sql.append(", UPDATE_DATE=SYSDATE, UPDATE_BY=?, REL_TITLE_NAME=? ");
			sql.append("WHERE APPLICATION_RECORD_ID = ?");
			
			
			String dSql = String.valueOf(sql);
			log.debug("Sql=" + dSql);
			ps = conn.prepareStatement(dSql);
			
			int index = 1;
			ps.setString(index++, appRecId);
			ps.setString(index++, kycM.getRelationFlag());
			ps.setString(index++, kycM.getRelName());
			ps.setString(index++, kycM.getRelSurName());
			ps.setString(index++, kycM.getRelPosition());
			
			ps.setString(index++, kycM.getRelDetail());
			ps.setDate(index++, this.parseDate(kycM.getWorkStartDate()));
			ps.setDate(index++, this.parseDate(kycM.getWorkEndDate()));
			ps.setString(index++, kycM.getSanctionList());
			ps.setString(index++, kycM.getCustmoerRiskGrade());
			
			ps.setString(index++, kycM.getUpdateBy());
			ps.setString(index++, kycM.getRelTitleName());
			
			ps.setString(index++, appRecId);
			
			return ps.executeUpdate();
			
			
		} catch (Exception e){
			log.fatal(e.getLocalizedMessage());
			throw new PLOrigApplicationException(e.getMessage());
		}
	}
	
	private void insertTable_Oirg_KYC(PLKYCDataM kycM, String appRecId) throws PLOrigApplicationException {
		
		PreparedStatement ps = null;
		Connection conn = null;
		try {
			
			conn = getConnection();
			StringBuilder sql = new StringBuilder("");
			sql.append("INSERT INTO ORIG_KYC ");
			sql.append("( APPLICATION_RECORD_ID, RELATION_FLAG, REL_NAME, REL_SURNAME, REL_POSITION ");
			sql.append(", REL_DETAIL, WORK_START_DATE, WORK_END_DATE, SANCTION_LIST, CUSTOMER_RISK_GRADE ");
			sql.append(", CREATE_DATE, CREATE_BY, UPDATE_DATE, UPDATE_BY, REL_TITLE_NAME ) ");
			sql.append("VALUES (?,?,?,?,?  ,?,?,?,?,?  ,SYSDATE,?,SYSDATE,?,? ) ");
			
			String dSql = String.valueOf(sql);
			log.debug("Sql=" + dSql);
			ps = conn.prepareStatement(dSql);
			
			int index = 1;
			
			ps.setString(index++, appRecId);
			ps.setString(index++, kycM.getRelationFlag());
			ps.setString(index++, kycM.getRelName());
			ps.setString(index++, kycM.getRelSurName());
			ps.setString(index++, kycM.getRelPosition());
			
			ps.setString(index++, kycM.getRelDetail());
			ps.setDate(index++, this.parseDate(kycM.getWorkStartDate()));
			ps.setDate(index++, this.parseDate(kycM.getWorkEndDate()));
			ps.setString(index++, kycM.getSanctionList());
			ps.setString(index++, kycM.getCustmoerRiskGrade());
			
			ps.setString(index++, kycM.getCreateBy());
			ps.setString(index++, kycM.getUpdateBy());
			ps.setString(index++, kycM.getRelTitleName());
			
			ps.executeUpdate();
		}catch (Exception e) {
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

	@Override
	public PLKYCDataM loadKYC(String appRecId) throws PLOrigApplicationException {

		PreparedStatement ps = null;
		ResultSet rs = null;		
		Connection conn = null;
		PLKYCDataM kycM = null;
		
		try{
			conn = getConnection();
			StringBuilder sql = new StringBuilder("");
			sql.append("SELECT APPLICATION_RECORD_ID, RELATION_FLAG, REL_NAME, REL_SURNAME, REL_POSITION ");
			sql.append(", REL_DETAIL, WORK_START_DATE, WORK_END_DATE, SANCTION_LIST, CUSTOMER_RISK_GRADE ");
			sql.append(", CREATE_DATE, CREATE_BY, UPDATE_DATE, UPDATE_BY, REL_TITLE_NAME ");
			sql.append("FROM ORIG_KYC WHERE APPLICATION_RECORD_ID = ?");
			
			String dSql = String.valueOf(sql);
			
			log.debug("Sql=" + dSql);
			
			ps = conn.prepareStatement(dSql);
			ps.setString(1, appRecId);
			rs = ps.executeQuery();
						
			if(rs.next()){	
				kycM = new PLKYCDataM(); 
				int index = 1;				
				kycM.setAppRecId(rs.getString(index++));
				kycM.setRelationFlag(rs.getString(index++));
				kycM.setRelName(rs.getString(index++));
				kycM.setRelSurName(rs.getString(index++));
				kycM.setRelPosition(rs.getString(index++));
				
				kycM.setRelDetail(rs.getString(index++));
				kycM.setWorkStartDate(rs.getDate(index++));
				kycM.setWorkEndDate(rs.getDate(index++));
				kycM.setSanctionList(rs.getString(index++));
				kycM.setCustmoerRiskGrade(rs.getString(index++));
				
				kycM.setCreateDate(rs.getTimestamp(index++));
				kycM.setCreateBy(rs.getString(index++));
				kycM.setUpdateDate(rs.getTimestamp(index++));
				kycM.setUpdateBy(rs.getString(index++));
				kycM.setRelTitleName(rs.getString(index++));				
			}			
		}catch(Exception e){
			log.fatal(e.getLocalizedMessage());
			throw new PLOrigApplicationException(e.getMessage());
		}finally{
			try{
				closeConnection(conn, rs, ps);
			}catch(Exception e){
				log.fatal(e.getLocalizedMessage());
				throw new PLOrigApplicationException(e.getMessage());
			}
		}
		return kycM;
	}
}
