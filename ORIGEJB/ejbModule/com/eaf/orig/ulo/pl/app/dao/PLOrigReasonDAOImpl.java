package com.eaf.orig.ulo.pl.app.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.Vector;
import org.apache.log4j.Logger;

import com.eaf.orig.shared.constant.OrigConstant;
import com.eaf.orig.shared.dao.OrigObjectDAO;
//import com.eaf.orig.shared.utility.ORIGCacheUtil;
import com.eaf.orig.ulo.pl.app.dao.exception.PLOrigApplicationException;
import com.eaf.orig.ulo.pl.model.app.PLReasonDataM;

public class PLOrigReasonDAOImpl extends OrigObjectDAO implements PLOrigReasonDAO{	
	
	static Logger log = Logger.getLogger(PLOrigReasonDAOImpl.class);
	
	@Override
	public void updateSaveOrigReason(Vector<PLReasonDataM> reasonVect, String appRecID ,String role)throws PLOrigApplicationException{		
		try{
			if(null != reasonVect && reasonVect.size() > 0){				
				this.deleteTableOrig_Reason(appRecID,role);	
				
				for(PLReasonDataM reasonM: reasonVect){
					this.insertTableOrig_Reason(reasonM, appRecID);
				}				
			}			
		}catch(Exception e){
			log.fatal(e.getLocalizedMessage());
			throw new PLOrigApplicationException(e.getMessage());
		}
	}
	
	private void deleteTableOrig_Reason(String appRecId, String role) throws PLOrigApplicationException{	
		PreparedStatement ps = null;
		Connection conn = null;
		try {
			conn = getConnection();
			StringBuilder sql = new StringBuilder("");
			sql.append(" DELETE ORIG_REASON ");
			sql.append(" WHERE APPLICATION_RECORD_ID = ? AND ROLE =? ");
			String dSql = String.valueOf(sql);
			log.debug("Sql="+dSql);
			ps = conn.prepareStatement(dSql);
			ps.setString(1, appRecId);
			ps.setString(2, role);
			ps.executeUpdate();

		}catch(Exception e){
			log.fatal(e.getLocalizedMessage());
			throw new PLOrigApplicationException(e.getMessage());
		}finally{
			try{
				closeConnection(conn, ps);
			}catch(Exception e){
				log.fatal(e.getLocalizedMessage());
				throw new PLOrigApplicationException(e.getMessage());
			}
		}
		
	}
	
	
	private void insertTableOrig_Reason(PLReasonDataM reasonM, String appRecId)throws PLOrigApplicationException{		
		PreparedStatement ps = null;	
		Connection conn = null;
		try{
			
			conn = getConnection();
			StringBuilder sql = new StringBuilder("");
			sql.append("INSERT INTO ORIG_REASON ");
			sql.append("( APPLICATION_RECORD_ID, REASON_TYPE, REASON_CODE, CREATE_DATE , CREATE_BY");
			sql.append(", UPDATE_DATE, UPDATE_BY, ROLE, REMARK ) ");
			sql.append(" VALUES(?,?,?,SYSDATE,?    ,SYSDATE,?,?,? ) ");
				
			log.debug("SQL >> "+sql);
			
			ps = conn.prepareStatement(String.valueOf(sql));
			
			ps.setString(1, appRecId);
			ps.setString(2, reasonM.getReasonType());
			ps.setString(3, reasonM.getReasonCode());
			ps.setString(4, reasonM.getCreateBy());
			
			ps.setString(5, reasonM.getUpdateBy());
			ps.setString(6, reasonM.getRole());
			ps.setString(7, reasonM.getRemark());
			
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
	public Vector<PLReasonDataM> loadOrigReason(String appRecId) throws PLOrigApplicationException {
		PreparedStatement ps = null;
		ResultSet rs = null;
		Connection conn = null;
		Vector<PLReasonDataM> reasonVect = new Vector<PLReasonDataM>();
		try {
			conn = getConnection();
			StringBuilder sql = new StringBuilder("");
			sql.append(" SELECT APPLICATION_RECORD_ID, REASON_TYPE, REASON_CODE, CREATE_DATE ");
			sql.append(" ,CREATE_BY, UPDATE_DATE, UPDATE_BY, ROLE, REMARK ");
			sql.append(" FROM ORIG_REASON WHERE APPLICATION_RECORD_ID = ? ");
			String dSql = String.valueOf(sql);
			log.debug("Sql=" + dSql);
			ps = conn.prepareStatement(dSql);
			rs = null;
			ps.setString(1, appRecId);

			rs = ps.executeQuery();
						
			while (rs.next()){
				PLReasonDataM reasonM = new PLReasonDataM();
				reasonM.setApplicationRecordId(rs.getString(1));
				reasonM.setReasonType(rs.getString(2));
				reasonM.setReasonCode(rs.getString(3));
				reasonM.setCreateDate(rs.getTimestamp(4));
				reasonM.setCreateBy(rs.getString(5));
				
				reasonM.setUpdateDate(rs.getTimestamp(6));
				reasonM.setUpdateBy(rs.getString(7));
				reasonM.setRole(rs.getString(8));
				reasonM.setRemark(rs.getString(9));		
				reasonM.setLoadFLAG(OrigConstant.FLAG_Y);
				reasonVect.add(reasonM);				
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
		return reasonVect;
	}

	@Override
	public Vector<String> loadReasonEnquiry(String appRecId) throws PLOrigApplicationException {
		PreparedStatement ps = null;
		ResultSet rs = null;
		Connection conn = null;
		Vector<String> reasonVect = new Vector<String>();
		try {
			conn = getConnection();
			StringBuilder sql = new StringBuilder("");
			
//			#SeptemWi Comment
//			sql.append("SELECT LB.DISPLAY_NAME FROM ORIG_REASON ORE, LIST_BOX_MASTER LB ");
//			sql.append("WHERE ORE.APPLICATION_RECORD_ID = ? AND ORE.CREATE_DATE = ");
//			sql.append("( SELECT MAX(sore.CREATE_DATE) FROM ORIG_REASON sore WHERE sore.APPLICATION_RECORD_ID = ? )");
//			sql.append("AND LB.FIELD_ID = ORE.REASON_TYPE AND LB.CHOICE_NO = ORE.REASON_CODE");
			
			sql.append(" SELECT DISTINCT LB.DISPLAY_NAME FROM ORIG_REASON ORE, LIST_BOX_MASTER LB  ");
			sql.append(" WHERE LB.FIELD_ID = ORE.REASON_TYPE AND LB.CHOICE_NO = ORE.REASON_CODE");
			sql.append(" AND ORE.APPLICATION_RECORD_ID = ? ");
			
			String dSql = String.valueOf(sql);
			
//			log.debug("Sql=" + dSql);
			
			ps = conn.prepareStatement(dSql);
			rs = null;
			ps.setString(1, appRecId);
			
//			#SepteMwi Comment
//			ps.setString(2, appRecId);

			rs = ps.executeQuery();
						
			while (rs.next()){
				reasonVect.add(rs.getString(1));
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
		return reasonVect;
	}

	@Override
	public Vector<String> loadReasonCallCenterTracking(String appRecId, String jobState) throws PLOrigApplicationException {
		PreparedStatement ps = null;
		ResultSet rs = null;
		Connection conn = null;
		Vector<String> reasonVect = new Vector<String>();
		try {
			conn = getConnection();
			StringBuilder sql = new StringBuilder("");
			
			String rejectState = ""; //ORIGCacheUtil.getInstance().getGeneralParamByCode(OrigConstant.GeneralParamCode.JOBSTATE_REJECT_ALL);
			if(rejectState.indexOf(jobState) >= 0){			
				sql.append("SELECT PKA_REJECT_LETTER.GET_REJECT_TEMPLATE_REASON(?) FROM DUAL");
			}else{			
				sql.append(" SELECT DISTINCT LB.DISPLAY_NAME FROM ORIG_REASON ORE, LIST_BOX_MASTER LB  ");
				sql.append(" WHERE LB.FIELD_ID = ORE.REASON_TYPE AND LB.CHOICE_NO = ORE.REASON_CODE");
				sql.append(" AND ORE.APPLICATION_RECORD_ID = ? ");
			}
			String dSql = String.valueOf(sql);
			
			ps = conn.prepareStatement(dSql);
			rs = null;
			ps.setString(1, appRecId);

			rs = ps.executeQuery();
						
			while(rs.next()){
				reasonVect.add(rs.getString(1));
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
		return reasonVect;
	}

}
