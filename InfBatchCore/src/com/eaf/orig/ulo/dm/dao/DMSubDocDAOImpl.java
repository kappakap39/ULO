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
import com.eaf.orig.ulo.model.dm.DocumentDataM;

public class DMSubDocDAOImpl extends OrigObjectDAO implements DMSubDocDAO{
private static transient Logger logger = Logger.getLogger(DMSubDocDAOImpl.class);
	@Override
	public void saveTableDMSubDoc(String dmId,ArrayList<DocumentDataM> documentDataMList,Connection conn) throws DMException {
		try{
			if(!Util.empty(documentDataMList)){
				for(DocumentDataM documentM : documentDataMList){
					documentM.setDmId(dmId);
					int returnRows = this.updateTableDMSubDoc(documentM,conn);
					if(returnRows == 0){								
						documentM.setDmSubId(DMGenerateUnique.generate(MConstant.DM_SEQUENCE_NAME.PK_DM_SUB_ID, OrigServiceLocator.WAREHOUSE_DB));
						this.insertTableDMSubDoc(documentM,conn);
					}						 
				}
			}
		}catch(Exception e){
			logger.fatal("ERROR",e);
			throw new DMException(e.getLocalizedMessage());
		}
		
	}
	private void insertTableDMSubDoc(DocumentDataM documentDataM,Connection conn) throws DMException{
		PreparedStatement ps = null;
		try{
			StringBuilder sql = new StringBuilder("");
			sql.append("INSERT INTO DM_SUB_DOC ");
			sql.append("(DM_ID, DM_SUB_ID, STATUS, DOC_TYPE, NO_OF_PAGE,");
			sql.append(" CREATE_BY, CREATE_DATE, UPDATE_BY, UPDATE_DATE");
			sql.append(" )");
			sql.append(" VALUES (?,?,?,?,?  ,?,?,?,?) ");
		
			String dSql = String.valueOf(sql);
			logger.debug("Sql=" + dSql);
			ps = conn.prepareStatement(dSql);
			
			int index = 1;
			ps.setString(index++, documentDataM.getDmId());
			ps.setString(index++, documentDataM.getDmSubId());
			ps.setString(index++, documentDataM.getStatus());
			ps.setString(index++, documentDataM.getDocType());
			ps.setInt(index++, documentDataM.getNoOfPage());

			ps.setString(index++, documentDataM.getCreateBy());
			ps.setTimestamp(index++, documentDataM.getCreateDate());
			ps.setString(index++, documentDataM.getUpdateBy());
			ps.setTimestamp(index++, documentDataM.getUpdateDate());
						
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
	
	private int updateTableDMSubDoc(DocumentDataM documentDataM,Connection conn) throws DMException {
		int returnRows = 0;
		PreparedStatement ps = null;
		try{
			StringBuilder sql = new StringBuilder("");										
			sql.append("UPDATE DM_SUB_DOC ");
			sql.append("SET DM_ID=?, DM_SUB_ID=?, STATUS=?, DOC_TYPE=?, NO_OF_PAGE=?,");
			sql.append(" CREATE_BY=?, CREATE_DATE=?, UPDATE_BY=?, UPDATE_DATE=?");
			sql.append(" WHERE DM_ID=?  AND DM_SUB_ID=?"); 
								
			String dSql = String.valueOf(sql);
			logger.debug("Sql=" + dSql);
			ps = conn.prepareStatement(dSql);
			
			int index = 1;
			ps.setString(index++, documentDataM.getDmId());
			ps.setString(index++, documentDataM.getDmSubId());
			ps.setString(index++, documentDataM.getStatus());
			ps.setString(index++, documentDataM.getDocType());
			ps.setInt(index++, documentDataM.getNoOfPage());

			ps.setString(index++, documentDataM.getCreateBy());
			ps.setTimestamp(index++, documentDataM.getCreateDate());
			ps.setString(index++, documentDataM.getUpdateBy());
			ps.setTimestamp(index++, documentDataM.getUpdateDate());
			
			ps.setString(index++, documentDataM.getDmId());
			ps.setString(index++, documentDataM.getDmSubId());
			
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
	public ArrayList<DocumentDataM> selectTableDMSubDoc(String dmId) throws DMException {
		PreparedStatement ps = null;
		ResultSet rs = null;
		Connection conn = null;
		ArrayList<DocumentDataM> documentList = new ArrayList<DocumentDataM>();	
		try{			
			conn = getConnection(OrigServiceLocator.WAREHOUSE_DB);
			StringBuilder sql = new StringBuilder("");
			sql.append("SELECT *  FROM DM_SUB_DOC ");
			sql.append("WHERE DM_ID = ? ");			
			
			String dSql = String.valueOf(sql);
			logger.debug("Sql=" + dSql);
			ps = conn.prepareStatement(dSql);
			rs = null;
			ps.setString(1, dmId);
			rs = ps.executeQuery();					
			while (rs.next()) {				
				DocumentDataM documentM = new DocumentDataM();	
				
				documentM.setDmId(rs.getString("DM_ID"));			
				documentM.setDmSubId(rs.getString("DM_SUB_ID"));
				documentM.setStatus(rs.getString("STATUS"));
				documentM.setDocType(rs.getString("DOC_TYPE"));
				documentM.setNoOfPage(rs.getInt("NO_OF_PAGE"));
				documentM.setCreateBy(rs.getString("CREATE_BY"));
				documentM.setCreateDate(rs.getTimestamp("CREATE_DATE"));
				documentM.setUpdateBy(rs.getString("UPDATE_BY"));
				documentM.setUpdateDate(rs.getTimestamp("UPDATE_DATE"));
				documentList.add(documentM);				
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
		return documentList;
		
	}
	@Override
	public void deleteNotInKeyDMSubDoc(String dmId,ArrayList<DocumentDataM> documentDataMList,Connection conn) throws DMException {
		PreparedStatement ps = null;
        try{
            StringBuffer sql = new StringBuffer("");
            sql.append("DELETE FROM DM_SUB_DOC ");
            sql.append(" WHERE DM_ID = ? ");	            
            if (!Util.empty(documentDataMList)) {
                sql.append(" AND DM_SUB_ID NOT IN ( ");
                for (DocumentDataM documentDataM: documentDataMList) {
                    sql.append(" '" + documentDataM.getDmSubId() + "' , ");
                }
                if (sql.toString().trim().endsWith(",")) {
                    sql.delete(sql.toString().lastIndexOf(","), sql.toString().length());
                }
                sql.append(" ) ");
            }
            String dSql = String.valueOf(sql);
            logger.debug("dSql >> "+dSql);	            
            ps = conn.prepareStatement(dSql);
            ps.setString(1, dmId);
            
            ps.executeUpdate();
            ps.close();
        }catch(Exception e){
        	logger.error("ERROR >> ",e);
        	throw new DMException(e.getLocalizedMessage());
		}finally{
			try{
				closeConnection(ps);
			}catch(Exception e){
				logger.error("ERROR >> ",e);
				throw new DMException(e.getLocalizedMessage());
			}
		}
	}
}
