package com.eaf.inf.batch.ulo.lotusnote.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;

import org.apache.log4j.Logger;

import com.eaf.core.ulo.common.cache.InfBatchProperty;
import com.eaf.core.ulo.common.dao.InfBatchObjectDAO;
import com.eaf.core.ulo.common.exception.InfBatchException;
import com.eaf.inf.batch.ulo.lotusnote.model.LotusNoteDataM;

public class LotusNoteDAOImpl extends InfBatchObjectDAO implements LotusNoteDAO{
	private static transient Logger logger = Logger.getLogger(LotusNoteDAOImpl.class);
	
	@Override
	public void insertUpdateMSSaleInfo(LotusNoteDataM lotusNote) throws InfBatchException {
		try{
			int updateRows = 0;
			updateRows = updateMSSaleInfo(lotusNote);
			if(updateRows == 0){
				insertMSSaleInfo(lotusNote);
			}
			updateInactiveSaleInfo();
		}catch(Exception e){
			logger.fatal("ERROR ",e);
			throw new InfBatchException(e.getLocalizedMessage());
		}
	}
	
	public void insertMSSaleInfo(LotusNoteDataM lotusNote) throws InfBatchException{
		Connection conn = null;
		PreparedStatement ps = null;
		try{
			conn = getConnection();
			StringBuilder sql = new StringBuilder();
			sql.append(" INSERT INTO MS_SALES_INFO ( ");
				sql.append(" SALE_ID, SALE_TYPE, TEAM_ID, ");
				sql.append(" SALE_NAME, REGION, TEAM_ZONE, ");
				sql.append(" TEAM_NAME, STATUS, ACTIVE_STATUS, ");
				sql.append(" CREATE_DATE, CREATE_BY, UPDATE_DATE, ");
				sql.append(" UPDATE_BY, KLOP_STATUS ");
			sql.append(" ) VALUES ( ");
				sql.append(" ?, ?, ?, ");
				sql.append(" ?, ?, ?, ");
				sql.append(" ?, ?, ?, ");
				sql.append(" SYSDATE, ?, SYSDATE, ");
				sql.append(" ?, ?) ");
			logger.debug("sql >> "+sql);
			ps = conn.prepareStatement(sql.toString());
			int parameterIndex = 1;
			ps.setString(parameterIndex++, lotusNote.getSaleId());
			ps.setInt(parameterIndex++, lotusNote.getSaleType());
			ps.setInt(parameterIndex++, lotusNote.getTeamId());
			ps.setString(parameterIndex++, lotusNote.getName());
			ps.setInt(parameterIndex++, lotusNote.getRegion());
			ps.setInt(parameterIndex++, lotusNote.getZone());
			ps.setString(parameterIndex++, lotusNote.getTeamName());
			ps.setInt(parameterIndex++, lotusNote.getStatus());
			ps.setString(parameterIndex++, InfBatchProperty.getInfBatchConfig("LOTUS_NOTE_DSA_ACTIVE_STATUS"));
			ps.setString(parameterIndex++, InfBatchProperty.getInfBatchConfig("SYSTEM_USER_ID"));
			ps.setString(parameterIndex++, InfBatchProperty.getInfBatchConfig("SYSTEM_USER_ID"));
			ps.setString(parameterIndex++, InfBatchProperty.getInfBatchConfig("LOTUS_NOTE_DSA_KLOP_STATUS"));
			
			ps.executeUpdate();
		}catch(Exception e){
			logger.fatal("ERROR ",e);
			throw new InfBatchException(e.getLocalizedMessage());
		}finally{
			try{
				closeConnection(conn, ps);
			}catch(Exception e){
				logger.fatal("ERROR ",e);
				throw new InfBatchException(e.getLocalizedMessage());
			}
		}
	}
	
	public int updateMSSaleInfo(LotusNoteDataM lotusNote) throws InfBatchException{
		int returnRows = 0;
		Connection conn = null;
		PreparedStatement ps = null;
		try{
			conn = getConnection();
			StringBuilder sql = new StringBuilder();
			sql.append("UPDATE MS_SALES_INFO SET ");
				sql.append("SALE_TYPE = ?, ");
				sql.append("TEAM_ID = ?, ");
				sql.append("SALE_NAME = ?, ");
				sql.append("REGION = ?, ");
				sql.append("TEAM_ZONE = ?, ");
				sql.append("TEAM_NAME = ?, ");
				sql.append("STATUS = ?, ");
				sql.append("ACTIVE_STATUS = ?, ");
				sql.append("UPDATE_DATE = SYSDATE, ");
				sql.append("UPDATE_BY = ?, ");
				sql.append("KLOP_STATUS = ? ");
			sql.append("WHERE SALE_ID = ? ");
			logger.debug("sql >> "+sql);
			logger.debug("lotusNote.getSaleId() >> "+lotusNote.getSaleId());
			ps = conn.prepareStatement(sql.toString());
			int parameterIndex = 1;
			ps.setInt(parameterIndex++, lotusNote.getSaleType());
			ps.setInt(parameterIndex++, lotusNote.getTeamId());
			ps.setString(parameterIndex++, lotusNote.getName());
			ps.setInt(parameterIndex++, lotusNote.getRegion());
			ps.setInt(parameterIndex++, lotusNote.getZone());
			ps.setString(parameterIndex++, lotusNote.getTeamName());
			ps.setInt(parameterIndex++, lotusNote.getStatus());
			ps.setString(parameterIndex++, InfBatchProperty.getInfBatchConfig("LOTUS_NOTE_DSA_ACTIVE_STATUS"));
			ps.setString(parameterIndex++, InfBatchProperty.getInfBatchConfig("SYSTEM_USER_ID"));
			ps.setString(parameterIndex++, InfBatchProperty.getInfBatchConfig("LOTUS_NOTE_DSA_KLOP_STATUS"));
			ps.setString(parameterIndex++, lotusNote.getSaleId());
			returnRows = ps.executeUpdate();
		}catch(Exception e){
			logger.fatal("ERROR ",e);
			throw new InfBatchException(e.getLocalizedMessage());
		}finally{
			try{
				closeConnection(conn, ps);
			}catch(Exception e){
				logger.fatal("ERROR ",e);
				throw new InfBatchException(e.getLocalizedMessage());
			}
		}
		return returnRows;
	}
	
	public int updateInactiveSaleInfo() throws InfBatchException{
		int returnRows = 0;
		Connection conn = null;
		PreparedStatement ps = null;
		try{
			conn = getConnection();
			StringBuilder sql = new StringBuilder();
			sql.append("UPDATE MS_SALES_INFO SET ");
			sql.append("ACTIVE_STATUS = ?, ");
			sql.append("UPDATE_DATE = SYSDATE, ");
			sql.append("KLOP_STATUS = ? ");
			sql.append("WHERE TRUNC(UPDATE_DATE) <> TRUNC(SYSDATE) ");
			logger.debug("sql >> "+sql);
			
			ps = conn.prepareStatement(sql.toString());
			int parameterIndex = 1;
			ps.setString(parameterIndex++, InfBatchProperty.getInfBatchConfig("LOTUS_NOTE_DSA_INACTIVE_STATUS"));
			ps.setString(parameterIndex++, InfBatchProperty.getInfBatchConfig("LOTUS_NOTE_DSA_INKLOP_STATUS"));
			returnRows = ps.executeUpdate();
		}catch(Exception e){
			logger.fatal("ERROR ",e);
			throw new InfBatchException(e.getLocalizedMessage());
		}finally{
			try{
				closeConnection(conn, ps);
			}catch(Exception e){
				logger.fatal("ERROR ",e);
				throw new InfBatchException(e.getLocalizedMessage());
			}
		}
		return returnRows;
	}
}
