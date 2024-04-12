package com.eaf.orig.ulo.pl.app.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.Vector;

import org.apache.log4j.Logger;

import com.eaf.orig.shared.dao.OrigObjectDAO;
import com.eaf.orig.shared.util.OrigUtil;
import com.eaf.orig.ulo.pl.app.dao.exception.PLOrigApplicationException;
import com.eaf.orig.ulo.pl.model.app.PLBOT5XDataM;

public class Bot5XDAOImpl extends OrigObjectDAO implements Bot5XDAO{
	static Logger logger = Logger.getLogger(Bot5XDAOImpl.class);
	@Override
	public void saveORIG_BOT(String appRecID,Vector<PLBOT5XDataM> bot5xVect) throws PLOrigApplicationException {
		if(!OrigUtil.isEmptyVector(bot5xVect)){
			Vector<String> seqVect = new Vector<String>();
			for(PLBOT5XDataM bot5xM : bot5xVect){
				double returnRows = updateORIG_BOT(appRecID, bot5xM);
				if(returnRows == 0){
					insertORIG_BOT(appRecID, bot5xM);
				}
				seqVect.add(bot5xM.getProductID());
			}
			if(!OrigUtil.isEmptyVector(seqVect)){
				deleteORIG_BOT(appRecID, seqVect);
			}
		}
	}
	
	private double updateORIG_BOT(String appRecID,PLBOT5XDataM bot5xM)throws PLOrigApplicationException{
		Connection conn = null;
		int returnRows;
		PreparedStatement ps = null;
		try {
			conn = getConnection();
			StringBuilder sql = new StringBuilder("");
			sql.append(" UPDATE ORIG_BOT ");
			sql.append(" SET AMOUNT=?, UPDATE_DATE=SYSDATE, UPDATE_BY=? ");			
			sql.append(" WHERE APPLICATION_RECORD_ID = ? AND PRODUCT_ID = ? ");
			
			String dSql = String.valueOf(sql);
			logger.debug("Sql=" + dSql);
			
			ps = conn.prepareStatement(dSql);
			
			int index = 1;
			ps.setBigDecimal(index++, bot5xM.getAmount());
			ps.setString(index++, bot5xM.getUpdateBy());
			ps.setString(index++, appRecID);
			ps.setString(index++, bot5xM.getProductID());
			returnRows = ps.executeUpdate();
		}catch(Exception e){
			logger.fatal(e.getLocalizedMessage());
			throw new PLOrigApplicationException(e.getMessage());
		}finally{
			try{
				closeConnection(conn, ps);
			}catch(Exception e){
				logger.fatal(e.getLocalizedMessage());
				throw new PLOrigApplicationException(e.getMessage());
			}
		}		
		return returnRows;
	}
	private void insertORIG_BOT(String appRecID,PLBOT5XDataM bot5xM)throws PLOrigApplicationException{
		PreparedStatement ps = null;
		Connection conn = null;
		try{			
			conn = getConnection();
			StringBuilder sql = new StringBuilder("");
			sql.append(" INSERT INTO ORIG_BOT ");
			sql.append(" (APPLICATION_RECORD_ID, PRODUCT_ID, AMOUNT, CREATE_BY, CREATE_DATE, UPDATE_BY, UPDATE_DATE) ");
			sql.append(" VALUES (?,?,?,?,SYSDATE,?,SYSDATE) ");
			
			String dSql = String.valueOf(sql);
			logger.debug("Sql=" + dSql);
			
			ps = conn.prepareStatement(dSql);
			
			int index = 1;
			ps.setString(index++, appRecID);			
			ps.setString(index++, bot5xM.getProductID());
			ps.setBigDecimal(index++, bot5xM.getAmount());
			ps.setString(index++,bot5xM.getCreateBy());
			ps.setString(index++,bot5xM.getUpdateBy());
			
			ps.executeUpdate();		
			
		}catch(Exception e){
			logger.fatal(e.getLocalizedMessage());
			throw new PLOrigApplicationException(e.getMessage());
		}finally{
			try{
				closeConnection(conn, ps);
			}catch(Exception e){
				logger.fatal(e.getLocalizedMessage());
				throw new PLOrigApplicationException(e.getMessage());
			}
		}
	}
	public void deleteORIG_BOT(String appRecID,Vector<String> seqVect)throws PLOrigApplicationException {
		PreparedStatement ps = null;
		Connection conn = null;
		try {
			conn = getConnection();
			StringBuilder sql = new StringBuilder("");
			sql.append(" DELETE ORIG_BOT WHERE APPLICATION_RECORD_ID = ? ");
			sql.append(" AND PRODUCT_ID NOT IN ");
			sql.append("( ");
			sql.append(ParameterINVect(seqVect));
			sql.append(" )");
			
			String dSql = String.valueOf(sql);			
			ps = conn.prepareStatement(dSql);
			int index = 1;
			ps.setString(index++, appRecID);
			PreparedStatementParameter(index, ps, seqVect);
			ps.executeUpdate();

		}catch(Exception e){
			logger.fatal(e.getLocalizedMessage());
			throw new PLOrigApplicationException(e.getMessage());
		}finally{
			try{
				closeConnection(conn, ps);
			}catch(Exception e){
				logger.fatal(e.getLocalizedMessage());
				throw new PLOrigApplicationException(e.getMessage());
			}
		}
	}
	
	@Override
	public Vector<PLBOT5XDataM> selectORIG_BOT(String appRecID) throws PLOrigApplicationException {
		PreparedStatement ps = null;
		ResultSet rs = null;
		Connection conn = null;
		Vector<PLBOT5XDataM> dataVect = new Vector<PLBOT5XDataM>();
		try{
			conn = getConnection();
			StringBuilder SQL = new StringBuilder("");
			
			SQL.append("SELECT * FROM ORIG_BOT WHERE APPLICATION_RECORD_ID = ?");
			
			String dSql = String.valueOf(SQL);
			logger.debug("SQL =" + dSql);
			
			ps = conn.prepareStatement(dSql);
			rs = null;
			
			int index = 1;
			ps.setString(index++, appRecID);

			rs = ps.executeQuery();
						
			PLBOT5XDataM bot5xM = null;
			while(rs.next()){
				bot5xM = new PLBOT5XDataM();
				bot5xM.setAppRecID(rs.getString("APPLICATION_RECORD_ID"));
				bot5xM.setProductID(rs.getString("PRODUCT_ID"));
				bot5xM.setAmount(rs.getBigDecimal("AMOUNT"));
				bot5xM.setCreateBy(rs.getString("CREATE_BY"));
				bot5xM.setCreateDate(rs.getTimestamp("CREATE_DATE"));
				bot5xM.setUpdateBy(rs.getString("UPDATE_BY"));
				bot5xM.setUpdateDate(rs.getTimestamp("UPDATE_DATE"));
				dataVect.add(bot5xM);				
			}			
		}catch(Exception e){
			logger.fatal(e.getLocalizedMessage());
			throw new PLOrigApplicationException(e.getMessage());
		}finally{
			try{
				closeConnection(conn, rs, ps);
			}catch(Exception e){
				logger.fatal(e.getLocalizedMessage());
				throw new PLOrigApplicationException(e.getMessage());
			}
		}
		return dataVect;
	}

	@Override
	public void deleteORIG_BOT(String appRecID)throws PLOrigApplicationException {
		PreparedStatement ps = null;
		Connection conn = null;
		try {
			conn = getConnection();
			StringBuilder sql = new StringBuilder("");
			sql.append(" DELETE ORIG_BOT WHERE APPLICATION_RECORD_ID = ? ");
			String dSql = String.valueOf(sql);
			logger.debug("SQL ="+dSql);
			ps = conn.prepareStatement(dSql);
			int index = 1;
			ps.setString(index++, appRecID);
			ps.executeUpdate();
		}catch(Exception e){
			logger.fatal(e.getLocalizedMessage());
			throw new PLOrigApplicationException(e.getMessage());
		}finally{
			try{
				closeConnection(conn, ps);
			}catch(Exception e){
				logger.fatal(e.getLocalizedMessage());
				throw new PLOrigApplicationException(e.getMessage());
			}
		}
	}
}
