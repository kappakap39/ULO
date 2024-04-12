package com.eaf.orig.ulo.pl.app.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

import org.apache.log4j.Logger;

import com.eaf.orig.shared.dao.OrigObjectDAO;
import com.eaf.orig.ulo.pl.app.dao.exception.PLOrigApplicationException;
import com.eaf.orig.ulo.pl.model.app.PLProjectCodeDataM;

public class PLProjectCodeDAOImpl extends OrigObjectDAO implements PLProjectCodeDAO {
	private static Logger log = Logger.getLogger(PLProjectCodeDAOImpl.class);
	@Override
	public PLProjectCodeDataM Loadtable(String projectcode) throws PLOrigApplicationException {		
		PreparedStatement ps = null;
		ResultSet rs = null;
		PLProjectCodeDataM loadprojectcode = new PLProjectCodeDataM();	
		Connection conn = null;
		try{
			conn = getConnection();
			StringBuilder sql = new StringBuilder("");
			sql.append("SELECT PROJECT_CODE, PRODUCT_CODE, CUSTOMER_TYPE, PROJECT_NAME, PROJECT_DESC");
			sql.append(", PROMOTION, START_DATE, END_DATE, APPROVE_DATE, CREDIT_LINE, PRIORITY, ACTIVE_STATUS,APPLICANT_PROPERTY, PROFESSION_TYPE");
			sql.append(", OR_DESC");
			sql.append(" FROM MS_PROJECT_CODE ");
			sql.append(" WHERE PROJECT_CODE=?");
			String dSql = String.valueOf(sql);
//			log.debug("Sql=" + dSql);
			ps = conn.prepareStatement(dSql);
			ps.setString(1, projectcode);

			rs = ps.executeQuery();
			
			if (rs.next()) {
				loadprojectcode.setProjectcode(rs.getString(1));			
				loadprojectcode.setProductcode(rs.getString(2));
				loadprojectcode.setCustomertype(rs.getString(3));
				loadprojectcode.setProjectname(rs.getString(4));
				loadprojectcode.setProjectdesc(rs.getString(5));
				loadprojectcode.setPromotion(rs.getString(6));
				loadprojectcode.setStartprojectdate(rs.getTimestamp(7));
				loadprojectcode.setEndprojectdate(rs.getTimestamp(8));
				loadprojectcode.setApprovedate(rs.getTimestamp(9));
				//loadprojectcode.setCustomergroup(rs.getString(10));
				loadprojectcode.setCreditline(rs.getString(10));
				loadprojectcode.setPriority(rs.getString(11));
				loadprojectcode.setActivestatus(rs.getString(12));
				loadprojectcode.setApplicationProperty(rs.getString(13));
				loadprojectcode.setProfessionCode(rs.getString(14));
				loadprojectcode.setOverideRules(rs.getString(15));
			}
			
			
		}catch(Exception e){
			throw new PLOrigApplicationException(e.getMessage());
		} finally {
			try {
				closeConnection(conn, rs, ps);
			} catch (Exception e) {
				log.fatal(e.getLocalizedMessage());
				throw new PLOrigApplicationException(e.getMessage());
			}
		}
		return loadprojectcode;
	}
	@Override
	public PLProjectCodeDataM Loadtable(String projectcode,String productFeature ,String busClassID,String exception_project) throws PLOrigApplicationException {
		PreparedStatement ps = null;
		ResultSet rs = null;
		PLProjectCodeDataM loadprojectcode = new PLProjectCodeDataM();
		Connection conn = null;
		try{
			conn = getConnection();
			StringBuilder sql = new StringBuilder("");
			sql.append(" SELECT MSPB.PROJECT_CODE, PRODUCT_CODE, CUSTOMER_TYPE, PROJECT_NAME, PROJECT_DESC ,PROMOTION, START_DATE,");
			sql.append(" END_DATE, APPROVE_DATE, CREDIT_LINE, PRIORITY, ACTIVE_STATUS ,APPLICANT_PROPERTY, PROFESSION_TYPE ,OR_DESC ");
			sql.append(" FROM MS_PROJECT_CODE MSP ,MS_PROJECT_CODE_BUS_CLASS MSPB ");
			sql.append(" WHERE MSP.PROJECT_CODE = MSPB.PROJECT_CODE AND MSP.ACTIVE_STATUS = 'A' ");
			sql.append(" AND MSP.PROJECT_CODE = ? ");
			sql.append(" AND MSPB.BUS_CLASS_ID = ? ");
			sql.append(" AND MSP.APPLICANT_PROPERTY = ? ");
			
			if(!"Y".equals(exception_project)){
				 sql.append("AND TRUNC(SYSDATE) BETWEEN MSP.START_DATE AND MSP.APPROVE_DATE ");
			}	
			
			String dSql = String.valueOf(sql);			
			log.debug("Sql=" + dSql);			
			int index = 1;
			ps = conn.prepareStatement(dSql);
			ps.setString(index++, projectcode);
			ps.setString(index++, busClassID);
			ps.setString(index++, productFeature);

			rs = ps.executeQuery();
			if(rs.next()){
				loadprojectcode.setProjectcode(rs.getString(1));			
				loadprojectcode.setProductcode(rs.getString(2));
				loadprojectcode.setCustomertype(rs.getString(3));
				loadprojectcode.setProjectname(rs.getString(4));
				loadprojectcode.setProjectdesc(rs.getString(5));
				loadprojectcode.setPromotion(rs.getString(6));
				loadprojectcode.setStartprojectdate(rs.getTimestamp(7));
				loadprojectcode.setEndprojectdate(rs.getTimestamp(8));
				loadprojectcode.setApprovedate(rs.getTimestamp(9));
				//loadprojectcode.setCustomergroup(rs.getString(10));
				loadprojectcode.setCreditline(rs.getString(10));
				loadprojectcode.setPriority(rs.getString(11));
				loadprojectcode.setActivestatus(rs.getString(12));
				loadprojectcode.setApplicationProperty(rs.getString(13));
				loadprojectcode.setProfessionCode(rs.getString(14));
				loadprojectcode.setOverideRules(rs.getString(15));
			}			
		}catch(Exception e){
			throw new PLOrigApplicationException(e.getMessage());
		}finally{
			try{
				closeConnection(conn, rs, ps);
			}catch(Exception e){
				log.fatal(e.getLocalizedMessage());
				throw new PLOrigApplicationException(e.getMessage());
			}
		}
		return loadprojectcode;
	}

}
