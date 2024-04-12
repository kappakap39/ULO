package com.eaf.orig.ulo.dm.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;

import org.apache.log4j.Logger;

import com.eaf.core.ulo.common.util.Util;
import com.eaf.orig.shared.dao.OrigObjectDAO;
import com.eaf.orig.shared.service.OrigServiceLocator;
import com.eaf.orig.ulo.app.util.DMGenerateUnique;
import com.eaf.orig.ulo.constant.MConstant;
import com.eaf.orig.ulo.dm.dao.exception.DMException;
import com.eaf.orig.ulo.model.dm.HistoryDataM;

public class DMTransactionDAOImpl extends OrigObjectDAO implements DMTransactionDAO{
private static transient Logger logger = Logger.getLogger(DMTransactionDAOImpl.class);
	@Override
	public void saveTableDMTransaction(String dmId,HistoryDataM docHistory,Connection conn) throws DMException { 
		try{
			if(!Util.empty(docHistory)){
				docHistory.setDmId(dmId);
				int returnRows = this.updateTableDMTransaction(docHistory,conn);
				if(returnRows == 0){
					docHistory.setDmTransactionId(DMGenerateUnique.generate(MConstant.DM_SEQUENCE_NAME.PK_DM_TRANSACTION_ID, OrigServiceLocator.WAREHOUSE_DB));
					this.insertTableDMTransaction(docHistory,conn);
				}			
			}
		}catch(Exception e){
			logger.fatal("ERROR",e);
			throw new DMException(e.getLocalizedMessage());
		}
	}

	private void insertTableDMTransaction(HistoryDataM historyM,Connection conn) throws DMException {
		PreparedStatement ps = null;
		try{
			StringBuilder sql = new StringBuilder("");
			sql.append("INSERT INTO DM_TRANSACTION ");
			sql.append("(DM_ID, DM_TRANSACTION_ID, PHONE_NO, REMARK, STATUS,");
			sql.append(" DUE_DATE, ACTION, MOBILE_NO, ACTION_DATE, REQUESTED_USER,");
			sql.append(" REQUESTER_NAME, SEQ, REQUESTER_DEPARTMENT, CREATE_BY, CREATE_DATE");
			sql.append(" )");
			sql.append(" VALUES (?,?,?,?,?  ,?,?,?,?,?  ,?,?,?,?,?) ");
		
			String dSql = String.valueOf(sql);
			logger.debug("Sql=" + dSql);
			ps = conn.prepareStatement(dSql);
			
			int index = 1;
			ps.setString(index++, historyM.getDmId());
			ps.setString(index++, historyM.getDmTransactionId());
			ps.setString(index++, historyM.getPhoneNo());
			ps.setString(index++, historyM.getRemark());
			ps.setString(index++, historyM.getStatus());

			ps.setDate(index++, historyM.getDueDate());
			ps.setString(index++, historyM.getAction());
			ps.setString(index++, historyM.getMobileNo());
			ps.setDate(index++, historyM.getActionDate());
			ps.setString(index++, historyM.getRequestedUser());
			
			ps.setString(index++, historyM.getRequesterName());
			ps.setInt(index++, historyM.getSeq());
			ps.setString(index++, historyM.getRequesterDepartment());
			ps.setString(index++, historyM.getCreateBy());
			ps.setTimestamp(index++, historyM.getCreateDate());
			
			ps.executeUpdate();
			
		}catch(Exception e){
			logger.fatal("ERROR",e);
			throw new DMException(e.getLocalizedMessage());
		}finally{
			try{
				closeConnection(ps);
			}catch(Exception e){
				logger.fatal("ERROR",e);
				throw new DMException(e.getLocalizedMessage());
			}
		}
	}

	private int updateTableDMTransaction(HistoryDataM historyM,Connection conn) throws DMException {
		int returnRows = 0;
		PreparedStatement ps = null;
		try{
			StringBuilder sql = new StringBuilder("");									
			sql.append("UPDATE  DM_TRANSACTION ");
			sql.append("SET  DM_ID=?, DM_TRANSACTION_ID=?, PHONE_NO=?, REMARK=?, STATUS=?,");
			sql.append(" DUE_DATE=?, ACTION=?, MOBILE_NO=?, ACTION_DATE=?, REQUESTED_USER=?,");
			sql.append(" REQUESTER_NAME=?, SEQ=?, REQUESTER_DEPARTMENT=?, CREATE_BY=?, CREATE_DATE=?");
			sql.append(" WHERE DM_ID=?  AND DM_TRANSACTION_ID=?"); 
						
			String dSql = String.valueOf(sql);
			logger.debug("Sql=" + dSql);
			ps = conn.prepareStatement(dSql);
			
			int index = 1;
			
			ps.setString(index++, historyM.getDmId());
			ps.setString(index++, historyM.getDmTransactionId());
			ps.setString(index++, historyM.getPhoneNo());
			ps.setString(index++, historyM.getRemark());
			ps.setString(index++, historyM.getStatus());

			ps.setDate(index++, historyM.getDueDate());
			ps.setString(index++, historyM.getAction());
			ps.setString(index++, historyM.getMobileNo());
			ps.setDate(index++, historyM.getActionDate());
			ps.setString(index++, historyM.getRequestedUser());
			
			ps.setString(index++, historyM.getRequesterName());
			ps.setInt(index++, historyM.getSeq());
			ps.setString(index++, historyM.getRequesterDepartment());
			ps.setString(index++, historyM.getCreateBy());
			ps.setTimestamp(index++, historyM.getCreateDate());
			
			ps.setString(index++, historyM.getDmId());
			ps.setString(index++, historyM.getDmTransactionId());
			
			returnRows = ps.executeUpdate();
			
		}catch(Exception e){
			logger.fatal("ERROR",e);
			throw new DMException(e.getLocalizedMessage());
		}finally{
			try{
				closeConnection(ps);
			}catch(Exception e){
				logger.fatal("ERROR",e);
				throw new DMException(e.getLocalizedMessage());
			}
		}
		return returnRows;		
	}

	@Override
	public ArrayList<HistoryDataM> selectTableDMTransaction(String dmId)throws DMException {
		PreparedStatement ps = null;
		ResultSet rs = null;
		Connection conn = null;
		ArrayList<HistoryDataM> historyList = new ArrayList<HistoryDataM>();	
		try{			
			conn = getConnection(OrigServiceLocator.WAREHOUSE_DB);
			StringBuilder sql = new StringBuilder("");
			sql.append("SELECT *  FROM DM_TRANSACTION ");
			sql.append("WHERE DM_ID = ? ");			
			
			String dSql = String.valueOf(sql);
			logger.debug("Sql=" + dSql);
			ps = conn.prepareStatement(dSql);
			rs = null;
			ps.setString(1, dmId);
			rs = ps.executeQuery();					
			while (rs.next()) {				
				HistoryDataM historyM = new HistoryDataM();	
				
				historyM.setDmId(rs.getString("DM_ID"));
				historyM.setDmTransactionId(rs.getString("DM_TRANSACTION_ID"));
				historyM.setPhoneNo(rs.getString("PHONE_NO"));
				historyM.setRemark(rs.getString("REMARK"));
				historyM.setStatus(rs.getString("STATUS"));
				historyM.setDueDate(rs.getDate("DUE_DATE"));
				historyM.setAction(rs.getString("ACTION"));
				historyM.setMobileNo(rs.getString("MOBILE_NO"));
				historyM.setActionDate(rs.getDate("ACTION_DATE"));
				historyM.setRequestedUser(rs.getString("REQUESTED_USER"));
				historyM.setRequesterName(rs.getString("REQUESTER_NAME"));
				historyM.setSeq(rs.getInt("SEQ"));
				historyM.setRequesterDepartment(rs.getString("REQUESTER_DEPARTMENT"));
				historyM.setCreateBy(rs.getString("CREATE_BY"));
				historyM.setCreateDate(rs.getTimestamp("CREATE_DATE"));
							 
				historyList.add(historyM);				
			}						
		}catch(Exception e){
			logger.fatal("ERROR",e);
			throw new DMException(e.getLocalizedMessage());
		}finally{
			try{
				closeConnection(conn, rs, ps);
			}catch(Exception e){
				logger.fatal("ERROR",e);
				throw new DMException(e.getLocalizedMessage());
			}
		}
		return historyList;
	}
	
}
