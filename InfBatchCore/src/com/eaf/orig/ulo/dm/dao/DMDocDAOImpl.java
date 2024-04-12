package com.eaf.orig.ulo.dm.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Savepoint;

import org.apache.log4j.Logger;

import com.eaf.core.ulo.common.util.Util;
import com.eaf.orig.shared.dao.OrigObjectDAO;
import com.eaf.orig.shared.service.OrigServiceLocator;
import com.eaf.orig.ulo.app.factory.DMModuleFactory;
import com.eaf.orig.ulo.app.util.DMGenerateUnique;
import com.eaf.orig.ulo.constant.MConstant;
import com.eaf.orig.ulo.dm.dao.exception.DMException;
import com.eaf.orig.ulo.model.dm.DocumentManagementDataM;

public class DMDocDAOImpl extends OrigObjectDAO implements DMDocDAO{
	private static transient Logger logger = Logger.getLogger(DMDocDAOImpl.class);
	@Override
	public void saveTableDMDoc(DocumentManagementDataM docManageM,boolean autoCommitFlag) throws Exception {	
		Connection conn = null;
		Savepoint savepoint = null;
		logger.debug("autoCommitFlag : "+autoCommitFlag);
		try{
			conn = getConnection(OrigServiceLocator.WAREHOUSE_DB);
			if(!autoCommitFlag){
				conn.setAutoCommit(false);
				savepoint = conn.setSavepoint("PROCESSCONTROL_SAVEPOINT");
			}
			if(Util.empty(docManageM)){
				docManageM = new DocumentManagementDataM();
			}
			String dmId = docManageM.getDmId();
			int returnRows = this.updateTableDmDoc(docManageM,conn);
			if(returnRows == 0){
				dmId= DMGenerateUnique.generate(MConstant.DM_SEQUENCE_NAME.PK_DM_ID, OrigServiceLocator.WAREHOUSE_DB);
				docManageM.setDmId(dmId);
				this.insertTableDmDoc(docManageM,conn);
			}
			DMTransactionDAO dmTransDao = DMModuleFactory.getDMTransactionDAO();
			dmTransDao.saveTableDMTransaction(dmId,docManageM.getHistory(),conn);
			
			DMSubDocDAO  dmSubDoc = DMModuleFactory.getDMSubDoc();
			dmSubDoc.saveTableDMSubDoc(dmId,docManageM.getDocument(),conn);
			dmSubDoc.deleteNotInKeyDMSubDoc(dmId,docManageM.getDocument(),conn);
			if(!autoCommitFlag){
				conn.commit();
			}
		}catch(Exception e){
			logger.fatal("ERROR",e);
			if(!autoCommitFlag){
				conn.rollback(savepoint);
			}
			throw new DMException(e.getLocalizedMessage());
		}finally{
	    	try{
	    		if(null != conn){
	    			conn.close();
	    		}
	        }catch(Exception e){
	        	logger.fatal("ERROR",e);
	        	throw new Exception(e.getLocalizedMessage());
	        }
	        conn = null;
	    }		
	}
 

	private void insertTableDmDoc(DocumentManagementDataM docManageM,Connection conn) throws DMException{
		PreparedStatement ps = null;
		try{
			StringBuilder sql = new StringBuilder("");
			sql.append("INSERT INTO DM_DOC ");
			sql.append("(DM_ID, LOCATION_ID, POLICY_ID, REF_NO1, TH_FIRST_NAME,");
			sql.append(" ID_NO, EN_FIRST_NAME, TH_LAST_NAME, ID_TYPE, REF_NO2,");
			sql.append(" CONTAINER_NO, DOC_SET_TYPE, STATUS, EN_TITLE_NAME, SYSTEM_ID,");
			sql.append(" BOX_NO, TH_TITLE_NAME, EN_LAST_NAME, CREATE_BY, CREATE_DATE,");
			sql.append(" UPDATE_BY, UPDATE_DATE, DOC_NAME, DOC_DESC,BRANCH_ID,");
			sql.append(" DOC_SET_ID, PARAM1, PARAM2, PARAM3, PARAM4,");
			sql.append(" PARAM5, PARAM6, PARAM7, PARAM8, PARAM9");
			sql.append(" )");
			sql.append(" VALUES (?,?,?,?,?  ,?,?,?,?,?  ,?,? ,?,?,?  ,?,?,?,?,?  ,?,?,?,?,? ,?,?,?,?,?	,?,?,?,?,?) ");
		
			String dSql = String.valueOf(sql);
			logger.debug("Sql=" + dSql);
			ps = conn.prepareStatement(dSql);
			
			int index = 1;
			ps.setString(index++, docManageM.getDmId());
			ps.setString(index++, docManageM.getLocationId());
			ps.setString(index++, docManageM.getPolicyId());
			ps.setString(index++, docManageM.getRefNo1());
			ps.setString(index++, docManageM.getThFirstName());

			ps.setString(index++, docManageM.getIdno());
			ps.setString(index++, docManageM.getEnFirstName());
			ps.setString(index++, docManageM.getThLastName());
			ps.setString(index++, docManageM.getIdType());
			ps.setString(index++, docManageM.getRefNo2());
			
			ps.setString(index++, docManageM.getContainerNo());
			ps.setString(index++, docManageM.getDocSetType());
			ps.setString(index++, docManageM.getStatus());
			ps.setString(index++, docManageM.getEnTitleName());
			ps.setString(index++, docManageM.getSystemId());
			
			ps.setString(index++, docManageM.getBoxNo());
			ps.setString(index++, docManageM.getThTitleName());
			ps.setString(index++, docManageM.getEnLastName());
			ps.setString(index++, docManageM.getCreateBy());
			ps.setTimestamp(index++, docManageM.getCreateDate());
			
			ps.setString(index++, docManageM.getUpdateBy());
			ps.setTimestamp(index++, docManageM.getUpdateDate());
			ps.setString(index++, docManageM.getDocSetName());
			ps.setString(index++, docManageM.getDocSetDesc());
			ps.setString(index++, docManageM.getBranchId());
			
			ps.setString(index++, docManageM.getDocSetId());
			ps.setString(index++, docManageM.getParam1());
			ps.setString(index++, docManageM.getParam2());
			ps.setString(index++, docManageM.getParam3());
			ps.setString(index++, docManageM.getParam4());
			
			ps.setString(index++, docManageM.getParam5());
			ps.setString(index++, docManageM.getParam6());
			ps.setString(index++, docManageM.getParam7());
			ps.setString(index++, docManageM.getParam8());
			ps.setString(index++, docManageM.getParam9());
						
			ps.executeUpdate();
			
		} catch (Exception e) {
			logger.fatal("ERROR",e);
			throw new DMException(e.getLocalizedMessage());
		} finally {
			try {
				closeConnection(ps);
			} catch (Exception e) {
				logger.fatal("ERROR",e);
				throw new DMException(e.getLocalizedMessage());
			}
		}
	
	}

	private int updateTableDmDoc(DocumentManagementDataM docManageM,Connection conn) throws DMException {
		int returnRows = 0;
		PreparedStatement ps = null;
		try{
			StringBuilder sql = new StringBuilder("");					
			sql.append("UPDATE  DM_DOC ");
			sql.append("SET DM_ID=?, LOCATION_ID=?, POLICY_ID=?, REF_NO1=?, TH_FIRST_NAME=?,");
			sql.append(" ID_NO=?, EN_FIRST_NAME=?, TH_LAST_NAME=?, ID_TYPE=?, REF_NO2=?,");
			sql.append(" CONTAINER_NO=?, DOC_SET_TYPE=?, STATUS=?, EN_TITLE_NAME=?, SYSTEM_ID=?,");
			sql.append(" BOX_NO=?, TH_TITLE_NAME=?, EN_LAST_NAME=?, CREATE_BY=?, CREATE_DATE=?,");
			sql.append(" UPDATE_BY=?, UPDATE_DATE=?,DOC_NAME=?, DOC_DESC=?,BRANCH_ID=?,");
			sql.append(" DOC_SET_ID=?, PARAM1=?, PARAM2=?, PARAM3=?, PARAM4=?,");
			sql.append(" PARAM5=?, PARAM6=?, PARAM7=?, PARAM8=?, PARAM9=?");
			sql.append(" WHERE DM_ID=? "); 
			
			String dSql = String.valueOf(sql);
			logger.debug("Sql=" + dSql);
			ps = conn.prepareStatement(dSql);
			
			int index = 1;
			ps.setString(index++, docManageM.getDmId());
			ps.setString(index++, docManageM.getLocationId());
			ps.setString(index++, docManageM.getPolicyId());
			ps.setString(index++, docManageM.getRefNo1());
			ps.setString(index++, docManageM.getThFirstName());

			ps.setString(index++, docManageM.getIdno());
			ps.setString(index++, docManageM.getEnFirstName());
			ps.setString(index++, docManageM.getThLastName());
			ps.setString(index++, docManageM.getIdType());
			ps.setString(index++, docManageM.getRefNo2());
			
			ps.setString(index++, docManageM.getContainerNo());
			ps.setString(index++, docManageM.getDocSetType());
			ps.setString(index++, docManageM.getStatus());
			ps.setString(index++, docManageM.getEnTitleName());
			ps.setString(index++, docManageM.getSystemId());
			
			ps.setString(index++, docManageM.getBoxNo());
			ps.setString(index++, docManageM.getThTitleName());
			ps.setString(index++, docManageM.getEnLastName());
			ps.setString(index++, docManageM.getCreateBy());
			ps.setTimestamp(index++, docManageM.getCreateDate());
			
			ps.setString(index++, docManageM.getUpdateBy());
			ps.setTimestamp(index++, docManageM.getUpdateDate());
			ps.setString(index++, docManageM.getDocSetName());
			ps.setString(index++, docManageM.getDocSetDesc());
			ps.setString(index++, docManageM.getBranchId());
			
			ps.setString(index++, docManageM.getDocSetId());
			ps.setString(index++, docManageM.getParam1());
			ps.setString(index++, docManageM.getParam2());
			ps.setString(index++, docManageM.getParam3());
			ps.setString(index++, docManageM.getParam4());
			
			ps.setString(index++, docManageM.getParam5());
			ps.setString(index++, docManageM.getParam6());
			ps.setString(index++, docManageM.getParam7());
			ps.setString(index++, docManageM.getParam8());
			ps.setString(index++, docManageM.getParam9());
			
			
			ps.setString(index++, docManageM.getDmId());
			
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
	public DocumentManagementDataM selectTableDMDoc(String dmId)throws DMException {
		PreparedStatement ps = null;
		ResultSet rs = null;
		Connection conn = null;
		DocumentManagementDataM docManageM = new DocumentManagementDataM();
		try{			
			conn = getConnection(OrigServiceLocator.WAREHOUSE_DB);
			StringBuilder sql = new StringBuilder("");
			sql.append("SELECT *  FROM DM_DOC ");
			sql.append("WHERE DM_ID = ? ");			
						
			String dSql = String.valueOf(sql);
			logger.debug("Sql=" + dSql);
			ps = conn.prepareStatement(dSql);
			rs = null;
			ps.setString(1, dmId);
			rs = ps.executeQuery();					
			if (rs.next()) {								
				docManageM.setDmId(rs.getString("DM_ID"));
				docManageM.setLocationId(rs.getString("LOCATION_ID"));
				docManageM.setPolicyId(rs.getString("POLICY_ID"));
				docManageM.setRefNo1(rs.getString("REF_NO1"));
				docManageM.setThFirstName(rs.getString("TH_FIRST_NAME"));
				docManageM.setIdno(rs.getString("ID_NO"));
				docManageM.setEnFirstName(rs.getString("EN_FIRST_NAME"));
				docManageM.setThLastName(rs.getString("TH_LAST_NAME"));
				docManageM.setIdType(rs.getString("ID_TYPE"));
				docManageM.setRefNo2(rs.getString("REF_NO2"));
				docManageM.setContainerNo(rs.getString("CONTAINER_NO"));
				docManageM.setDocSetType(rs.getString("DOC_SET_TYPE"));
				docManageM.setStatus(rs.getString("STATUS"));
				docManageM.setEnTitleName(rs.getString("EN_TITLE_NAME"));
				docManageM.setSystemId(rs.getString("SYSTEM_ID"));
				docManageM.setBoxNo(rs.getString("BOX_NO"));
				docManageM.setThTitleName(rs.getString("TH_TITLE_NAME"));
				docManageM.setEnLastName(rs.getString("EN_LAST_NAME"));
				docManageM.setCreateBy(rs.getString("CREATE_BY"));
				docManageM.setCreateDate(rs.getTimestamp("CREATE_DATE"));
				docManageM.setUpdateBy(rs.getString("UPDATE_BY"));
				docManageM.setUpdateDate(rs.getTimestamp("UPDATE_DATE"));
				docManageM.setDocSetName(rs.getString("DOC_NAME"));
				docManageM.setDocSetDesc(rs.getString("DOC_DESC"));
				docManageM.setBranchId(rs.getString("BRANCH_ID"));
				docManageM.setDocSetId(rs.getString("DOC_SET_ID"));
				docManageM.setParam1(rs.getString("PARAM1"));
				docManageM.setParam2(rs.getString("PARAM2"));
				docManageM.setParam3(rs.getString("PARAM3"));
				docManageM.setParam4(rs.getString("PARAM4"));
				docManageM.setParam5(rs.getString("PARAM5"));
				docManageM.setParam6(rs.getString("PARAM6"));
				docManageM.setParam7(rs.getString("PARAM7"));
				docManageM.setParam8(rs.getString("PARAM8"));
				docManageM.setParam9(rs.getString("PARAM9"));
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
		return docManageM;
	}



	
}