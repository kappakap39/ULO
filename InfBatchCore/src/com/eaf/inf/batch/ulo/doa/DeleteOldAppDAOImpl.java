package com.eaf.inf.batch.ulo.doa;

import java.io.File;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.HashMap;

import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.Logger;

import com.eaf.core.ulo.common.cache.InfBatchProperty;
import com.eaf.core.ulo.common.dao.InfBatchObjectDAO;
import com.eaf.core.ulo.common.dao.InfBatchServiceLocator;
import com.eaf.core.ulo.common.exception.InfBatchException;
import com.eaf.core.ulo.common.properties.SystemConstant;
import com.eaf.core.ulo.common.util.InfBatchUtil;
import com.eaf.core.ulo.common.util.Util;
import com.eaf.inf.batch.ulo.ctoa.ApplicationGroupCatDataM;

public class DeleteOldAppDAOImpl extends InfBatchObjectDAO implements DeleteOldAppDAO
{
	private static transient Logger logger = Logger.getLogger(DeleteOldAppDAOImpl.class);
	private static String DELETE_OLD_APP_TASK_LIMIT = InfBatchProperty.getInfBatchConfig("DELETE_OLD_APP_TASK_LIMIT");
	private static String DELETE_OLD_APP_FILE_BACKUP_PATH = InfBatchProperty.getInfBatchConfig("DELETE_OLD_APP_FILE_BACKUP_PATH");
	boolean writeJsonAll = Boolean.valueOf(InfBatchProperty.getInfBatchConfig("DELETE_OLD_APP_THREAD_WRITE_JSON_FILE"));
	
	@Override
	public ArrayList<ApplicationGroupCatDataM> loadOldAppToDelete() throws Exception 
	{
		ArrayList<ApplicationGroupCatDataM> appGroupCatList = new ArrayList<ApplicationGroupCatDataM>();
		Connection conn = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		try{
			int resultLimit = Integer.parseInt(DELETE_OLD_APP_TASK_LIMIT);
			conn = getConnection();
			StringBuilder sql = new StringBuilder();
			sql.append(" SELECT * FROM ORIG_APPLICATION_GROUP_CAT ");
			sql.append(" WHERE ARC_STATUS = '1' ");
			
			logger.debug("sql : "+sql);
			ps = conn.prepareStatement(sql.toString());
			if(resultLimit > 0)
			{ps.setMaxRows(resultLimit);}
			rs = ps.executeQuery();
			while(rs.next())
			{
				ApplicationGroupCatDataM appGroupCat = new ApplicationGroupCatDataM();
				appGroupCat.setApplicationGroupId(rs.getString("APPLICATION_GROUP_ID"));
				appGroupCat.setApplicationGroupNo(rs.getString("APPLICATION_GROUP_NO"));
				appGroupCat.setApplicationDate(rs.getDate("APPLICATION_DATE"));
				appGroupCat.setApplicationTemplate(rs.getString("APPLICATION_TEMPLATE"));
				appGroupCat.setApplicationStatus(rs.getString("APPLICATION_STATUS"));
				appGroupCat.setLifecycle(rs.getInt("LIFE_CYCLE"));
				appGroupCat.setArcDate(rs.getDate("ARC_DATE"));
				appGroupCat.setArcStatus(rs.getString("ARC_STATUS"));
				appGroupCat.setPurgeDateOrigApp(rs.getDate("PURGE_DATE_ORIG_APP"));
				appGroupCat.setPurgeDateBPMDBS(rs.getDate("PURGE_DATE_BPMDBS"));
				appGroupCat.setPurgeDateResDB(rs.getDate("PURGE_DATE_RESDB"));
				appGroupCat.setPurgeDateOLData(rs.getDate("PURGE_DATE_OL_DATA"));
				appGroupCat.setPurgeDateIMApp(rs.getDate("PURGE_DATE_IM_APP"));
				appGroupCat.setPurgeDateImageFile(rs.getDate("PURGE_DATE_IMAGE_FILE"));
				appGroupCatList.add(appGroupCat);
			}
			
		}catch(Exception e){
			logger.fatal("ERROR ",e);
			throw new InfBatchException(e.getMessage());
		}finally{
			try{
				closeConnection(conn, rs, ps);
			}catch(Exception e){
				logger.fatal("ERROR ", e);
			}
		}
		return appGroupCatList;
	}
	
	@Override
	public void deleteUtil1(Connection conn, String table, 
			String keyName, ArrayList<String> keyValueList) throws Exception
	{
		deleteUtil1(conn, table, keyName, keyValueList, true);
	}

	@Override
	public void deleteUtil1(Connection conn, String table, 
			String keyName,ArrayList<String> keyValueList, boolean writeJsonTable)throws Exception 
	{
		if(keyValueList.size() > 0)
		{
			String jsonString = "";
			if(writeJsonAll && writeJsonTable)
			{
				jsonString = backupBeforeDelete1(conn, table, keyName, keyValueList);
			}
			
			PreparedStatement ps = null;
			try{
				StringBuilder sql = new StringBuilder();
				sql.append(" DELETE FROM " + table);
				sql.append(" WHERE " + keyName + " = ? ");
				//logger.debug("sql : "+sql);
				ps = conn.prepareStatement(sql.toString());
				
				for(String keyValue : keyValueList)
				{
					ps.setString(1, keyValue);
					ps.addBatch();
				}
				
				ps.executeBatch();
				
				//Write jsonString to file
				if(!Util.empty(jsonString) && writeJsonAll && writeJsonTable)
				{writeBackupJsonStringToFile(conn.getSchema(), table, jsonString);}
			}
			catch(Exception e)
			{
				//logger.fatal("ERROR ",e);
				logger.debug("ERROR delete table " + table + " - " + e.getMessage());
				throw new InfBatchException(e.getMessage());
			}
			finally
			{
				try{
					closeConnection(ps);
				}catch(Exception e){
					logger.fatal("ERROR ", e);
				}
			}
		}
	}
	
	@Override
	public void deleteUtil2(Connection conn, String table, 
			String keyName1, ArrayList<String> keyValueList1, 
			String keyName2, String keyValue2) throws Exception
	{
		deleteUtil2(conn, table, keyName1, keyValueList1, keyName2, keyValue2, true);
	}

	@Override
	public void deleteUtil2(Connection conn, String table, 
			String keyName1, ArrayList<String> keyValueList1, 
			String keyName2, String keyValue2, boolean writeJsonTable) throws Exception 
	{
		if(keyValueList1.size() > 0)
		{
			String jsonString = "";
			if(writeJsonAll && writeJsonTable)
			{
				jsonString = backupBeforeDelete2(conn, table, keyName1, keyValueList1, keyName2, keyValue2);
			}
			PreparedStatement ps = null;
			try{
				StringBuilder sql = new StringBuilder();
				sql.append(" DELETE FROM " + table);
				sql.append(" WHERE " + keyName1 + " = ? ");
				sql.append(" AND " + keyName2 + " = '" + keyValue2 + "' " );
				//logger.debug("sql : "+sql);
				ps = conn.prepareStatement(sql.toString());
				
				for(String keyValue : keyValueList1)
				{
					ps.setString(1, keyValue);
					ps.addBatch();
				}
				
				ps.executeBatch();
				
				//Write jsonString to file
				if(!Util.empty(jsonString) && writeJsonAll && writeJsonTable)
				{writeBackupJsonStringToFile(conn.getSchema(), table, jsonString);}
			}
			catch(Exception e)
			{
				//logger.fatal("ERROR ",e);
				logger.debug("ERROR delete table " + table + " - " + e.getMessage());
				throw new InfBatchException(e.getMessage());
			}
			finally
			{
				try{
					closeConnection(ps);
				}catch(Exception e){
					logger.fatal("ERROR ", e);
				}
			}
		}
	}
	
	@Override
	public void deleteUtil1_P(HashMap<String,PreparedStatement> psHm, String table, ArrayList<String> keyValueList)throws Exception
	{
		deleteUtil1_P(psHm.get("DEL_" + table), psHm.get("SEL_" + table), table, keyValueList, true);
	}
	
	@Override
	public void deleteUtil1_P(HashMap<String,PreparedStatement> psHm, String table, ArrayList<String> keyValueList, boolean writeJsonTable)throws Exception
	{
		deleteUtil1_P(psHm.get("DEL_" + table), psHm.get("SEL_" + table), table, keyValueList, writeJsonTable);
	}
	
	@Override
	public void deleteUtil1_P(PreparedStatement deletePs, PreparedStatement backupPs, String table,
			ArrayList<String> keyValueList, boolean writeJsonTable)throws Exception 
	{
		if(keyValueList.size() > 0)
		{
			String jsonString = "";
			if(writeJsonAll && writeJsonTable)
			{jsonString = backupBeforeDeleteP(backupPs, table, keyValueList);}
			
			try
			{
				for(String keyValue : keyValueList)
				{
					deletePs.setString(1, keyValue);
					deletePs.addBatch();
				}
					
				deletePs.executeBatch();
				
				//Write jsonString to file
				if(!Util.empty(jsonString) && writeJsonAll && writeJsonTable)
				{writeBackupJsonStringToFile(deletePs.getConnection().getSchema(), table, jsonString);}
			}
			catch(Exception e)
			{
				//logger.fatal("ERROR ",e);
				logger.debug("ERROR delete table " + table + " - " + e.getMessage());
				throw new InfBatchException(e.getMessage());
			}
		}
	}
	
	@Override
	public void deleteUtil2_P(HashMap<String,PreparedStatement> psHm, String table,
			ArrayList<String> keyValueList1) throws Exception
	{
		deleteUtil2_P(psHm.get("DEL_" + table), psHm.get("SEL_" + table), table, keyValueList1, true);
	}
	
	@Override
	public void deleteUtil2_P(HashMap<String,PreparedStatement> psHm, String table,
			ArrayList<String> keyValueList1, boolean writeJsonTable) throws Exception
	{
		deleteUtil2_P(psHm.get("DEL_" + table), psHm.get("SEL_" + table), table, keyValueList1, writeJsonTable);
	}

	@Override
	public void deleteUtil2_P(PreparedStatement deletePs, PreparedStatement backupPs, String table, 
			ArrayList<String> keyValueList1, boolean writeJsonTable) throws Exception 
	{
		if(keyValueList1.size() > 0)
		{
			String jsonString = "";
			if(writeJsonAll && writeJsonTable)
			{jsonString = backupBeforeDeleteP(backupPs, table, keyValueList1);}
			
			try
			{
				for(String keyValue : keyValueList1)
				{
					deletePs.setString(1, keyValue);
					deletePs.addBatch();
				}
				
				deletePs.executeBatch();
				
				//Write jsonString to file
				if(!Util.empty(jsonString) && writeJsonAll && writeJsonTable)
				{writeBackupJsonStringToFile(deletePs.getConnection().getSchema(), table, jsonString);}
			}
			catch(Exception e)
			{
				//logger.fatal("ERROR ",e);
				logger.debug("ERROR delete table " + table + " - " + e.getMessage());
				throw new InfBatchException(e.getMessage());
			}
		}
	}
	
	@Override
	public void updateArcStatusComplete(Connection conn, ArrayList<String> appGroupIdList) throws Exception 
	{
		PreparedStatement ps = null;
		try{
			StringBuilder sql = new StringBuilder();
			sql.append(" UPDATE ORIG_APPLICATION_GROUP_CAT ");
			sql.append(" SET ARC_STATUS = '2' ");
			sql.append(" WHERE APPLICATION_GROUP_ID = ? ");
			//logger.debug("sql : "+sql);
			ps = conn.prepareStatement(sql.toString());
			
			for(String appGroupId : appGroupIdList)
			{
				ps.setString(1, appGroupId);
				ps.addBatch();
			}
			
			ps.executeBatch();
		}
		catch(Exception e)
		{
			logger.fatal("ERROR ",e);
			throw new InfBatchException(e.getMessage());
		}
	}
	
	public ArrayList<String> getIMImagePathList(ArrayList<String> hashIdList) throws Exception
	{
		ArrayList<String> imgPath = new ArrayList<String>();
		Connection imConn = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		try{
			imConn = InfBatchObjectDAO.getConnection(InfBatchServiceLocator.IMG_DB);
			StringBuilder sql = new StringBuilder();
			sql.append(" SELECT IMGPATH FROM IM_IMAGE ");
			sql.append(" WHERE HASHING_ID = ? ");
			logger.debug("sql : "+sql);
			ps = imConn.prepareStatement(sql.toString());
			
			for(String hashId : hashIdList)
			{
				ps.setString(1, hashId);
				rs = ps.executeQuery();
				while(rs.next())
				{
					if(!Util.empty(rs.getString("IMGPATH")))
					{imgPath.add(rs.getString("IMGPATH"));}
				}
			}
			
		}catch(Exception e){
			logger.fatal("ERROR ",e);
			throw new InfBatchException(e.getMessage());
		}finally{
			try{
				closeConnection(imConn, rs, ps);
			}catch(Exception e){
				logger.fatal("ERROR ", e);
			}
		}
		return imgPath;
	}
	
	public ArrayList<String> getIMImageCatPathList(ArrayList<String> appGroupIdList) throws Exception
	{
		ArrayList<String> imgPath = new ArrayList<String>();
		Connection conn = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		try{
			conn = getConnection();
			StringBuilder sql = new StringBuilder();
			sql.append(" SELECT IMGPATH FROM IM_IMAGE_CAT ");
			sql.append(" WHERE APPLICATION_GROUP_ID = ? ");
			//logger.debug("sql : "+sql);
			ps = conn.prepareStatement(sql.toString());
			
			for(String appGroupId : appGroupIdList)
			{
				ps.setString(1, appGroupId);
				rs = ps.executeQuery();
				while(rs.next())
				{
					if(!Util.empty(rs.getString("IMGPATH")))
					{imgPath.add(rs.getString("IMGPATH"));}
				}
			}
			
		}catch(Exception e){
			logger.fatal("ERROR ",e);
			throw new InfBatchException(e.getMessage());
		}finally{
			try{
				closeConnection(conn, rs, ps);
			}catch(Exception e){
				logger.fatal("ERROR ", e);
			}
		}
		return imgPath;
	}
	
	@Override
	public ArrayList<String> getIMTransactionIdList(Connection conn, ArrayList<String> hashIdList) throws Exception 
	{
		ArrayList<String> imTransactionIdList = new ArrayList<String>();
		if(hashIdList != null && hashIdList.size() > 0)
		{
			PreparedStatement ps = null;
			ResultSet rs = null;
			try{
				StringBuilder sql = new StringBuilder();
				sql.append(" SELECT TRANSACTIONID FROM IM_IMAGE ");
				sql.append(" WHERE HASHING_ID = ?");
				//logger.debug("sql : "+sql);
				ps = conn.prepareStatement(sql.toString());
				
				for(String hashId : hashIdList)
				{
					ps.setString(1, hashId);
					rs = ps.executeQuery();
					while(rs.next())
					{
						if(!imTransactionIdList.contains(rs.getString("TRANSACTIONID")))
						{imTransactionIdList.add(rs.getString("TRANSACTIONID"));}
					}
				}
				
			}catch(Exception e){
				logger.fatal("ERROR ",e);
				throw new InfBatchException(e.getMessage());
			}finally{
				try{
					closeConnection(ps, rs);
				}catch(Exception e){
					logger.fatal("ERROR ", e);
				}
			}
		}
		return imTransactionIdList;
	}

	@Override
	public void updatePurgeDate(Connection conn, String field, String key,
			ArrayList<String> keyValueList) throws Exception 
	{
		PreparedStatement ps = null;
		try{
			StringBuilder sql = new StringBuilder();
			sql.append(" UPDATE ORIG_APPLICATION_GROUP_CAT ");
			sql.append(" SET " + field + " = SYSDATE ");
			sql.append(" WHERE " + key + " = ? ");
			//logger.debug("sql : "+sql);
			ps = conn.prepareStatement(sql.toString());
			
			for(String keyValue : keyValueList)
			{
				ps.setString(1, keyValue);
				ps.addBatch();
			}
			
			ps.executeBatch();
			
		}
		catch(Exception e)
		{
			logger.fatal("ERROR ",e);
			throw new InfBatchException(e.getMessage());
		}
		finally
		{
			try{
				closeConnection(ps);
			}catch(Exception e){
				logger.fatal("ERROR ", e);
			}
		}
	}
	
	@Override
	public void updatePurgeDateP(PreparedStatement ps, ArrayList<String> keyValueList) 
			throws Exception 
	{
		try
		{
			for(String keyValue : keyValueList)
			{
				ps.setString(1, keyValue);
				ps.addBatch();
			}
			
			ps.executeBatch();
			
		}
		catch(Exception e)
		{
			logger.fatal("ERROR ",e);
			throw new InfBatchException(e.getMessage());
		}
	}

	@Override
	public ArrayList<String> getExecutionIdList(Connection conn, ArrayList<String> appGroupNoList) throws Exception {
		ArrayList<String> executionIdList = new ArrayList<String>();
		if(appGroupNoList != null && appGroupNoList.size() > 0)
		{
			PreparedStatement ps = null;
			ResultSet rs = null;
			try{
				StringBuilder sql = new StringBuilder();
				sql.append(" SELECT CONCAT(SERVICE_DATA, TRANSACTION_ID) AS EXECUTION_ID ");
				sql.append(" FROM SERVICE_REQ_RESP ");
				sql.append(" WHERE SERVICE_ID = 'DecisionService' ");
				sql.append(" AND REF_CODE = ? AND ACTIVITY_TYPE = 'O' ");
				ps = conn.prepareStatement(sql.toString());
				//logger.debug("sql : "+sql);
				
				for(String appGroupNo : appGroupNoList)
				{
					ps.setString(1, appGroupNo);
					rs = ps.executeQuery();
					while(rs.next())
					{
						if(!Util.empty(rs.getString("EXECUTION_ID")))
						{executionIdList.add(rs.getString("EXECUTION_ID"));}
					}
				}
				
			}catch(Exception e){
				logger.fatal("ERROR ",e);
				throw new InfBatchException(e.getMessage());
			}finally{
				try{
					closeConnection(ps, rs);
				}catch(Exception e){
					logger.fatal("ERROR ", e);
				}
			}
		}
		return executionIdList;
	}
	
	@Override
	public void call_LSW_BPD_INSTANCE_DELETE(Connection bpmConn, ArrayList<Integer> instantIdList) throws Exception {
		CallableStatement cs = null;
		try{
			StringBuilder sql = new StringBuilder();
			sql.append(" { call LSW_BPD_INSTANCE_DELETE(?) } ");
			//logger.debug("sql : "+sql);
			cs = bpmConn.prepareCall(sql.toString());
			for(Integer instantId : instantIdList)
			{
				cs.setInt(1, instantId);
				cs.addBatch();
			}
			cs.executeBatch();
		}catch(Exception e){
			logger.fatal("ERROR ",e);
			throw new InfBatchException(e.getLocalizedMessage());
		}finally{
			try{
				closeConnection(cs);
			}catch(Exception e){
				logger.fatal("ERROR ",e);
			}
		}
	}

	@Override
	public ArrayList<String> selectUtil1(Connection conn, String table, String keyToGet,
			String key, ArrayList<String> keyValueList) throws Exception 
	{
		ArrayList<String> resultValueList = new ArrayList<String>();
		if(keyValueList != null && keyValueList.size() > 0)
		{
			PreparedStatement ps = null;
			ResultSet rs = null;
			try{
				StringBuilder sql = new StringBuilder();
				sql.append(" SELECT " + keyToGet);
				sql.append(" FROM " + table);
				sql.append(" WHERE " + key + " = ? ");
				//logger.debug("sql : "+sql);
				ps = conn.prepareStatement(sql.toString());
				
				for(String keyValue : keyValueList)
				{
					ps.setString(1, keyValue);
					rs = ps.executeQuery();
					while(rs.next())
					{
						resultValueList.add(rs.getString(keyToGet));
					}
				}
				
			}catch(Exception e){
				logger.fatal("ERROR ",e);
				throw new InfBatchException(e.getMessage());
			}finally{
				try{
					closeConnection(ps, rs);
				}catch(Exception e){
					logger.fatal("ERROR ", e);
				}
			}
		}
		return resultValueList;
	}

	@Override
	public ArrayList<String> selectUtil2(Connection conn, String table, String keyToGet,
			String key, ArrayList<String> keyValueList, String key2,
			String keyValue2) throws Exception 
	{
		ArrayList<String> resultValueList = new ArrayList<String>();
		if(keyValueList != null && keyValueList.size() > 0)
		{
			PreparedStatement ps = null;
			ResultSet rs = null;
			try{
				StringBuilder sql = new StringBuilder();
				sql.append(" SELECT " + keyToGet);
				sql.append(" FROM " + table);
				sql.append(" WHERE " + key + " = ? ");
				sql.append(" AND " + key2 + " = '" + keyValue2 + "' ");
				//logger.debug("sql : "+sql);
				ps = conn.prepareStatement(sql.toString());
				
				for(String keyValue : keyValueList)
				{
					ps.setString(1, keyValue);
					rs = ps.executeQuery();
					while(rs.next())
					{
						resultValueList.add(rs.getString(keyToGet));
					}
				}
				
			}catch(Exception e){
				logger.fatal("ERROR ",e);
				throw new InfBatchException(e.getMessage());
			}finally{
				try{
					closeConnection(ps, rs);
				}catch(Exception e){
					logger.fatal("ERROR ", e);
				}
			}
		}
		return resultValueList;
	}
	
	@Override
	public ArrayList<String> selectUtilP(PreparedStatement ps, String table, 
			String keyToGet, ArrayList<String> keyValueList) throws Exception 
	{
		ArrayList<String> resultValueList = new ArrayList<String>();
		if(keyValueList != null && keyValueList.size() > 0)
		{
			ResultSet rs = null;
			try{
				for(String keyValue : keyValueList)
				{
					ps.setString(1, keyValue);
					rs = ps.executeQuery();
					while(rs.next())
					{
						resultValueList.add(rs.getString(keyToGet));
					}
				}
				
			}catch(Exception e){
				logger.fatal("ERROR ",e);
				throw new InfBatchException(e.getMessage());
			}finally{
				try{
					if(rs != null){rs.close();}
				}catch(Exception e){
					logger.fatal("ERROR ", e);
				}
			}
		}
		return resultValueList;
	}

	@Override
	public ArrayList<String> getIMFllwReasonIdList(Connection conn, ArrayList<String> transactionIdList) 
			throws Exception {
		ArrayList<String> reasonIdList = new ArrayList<String>();
		PreparedStatement ps = null;
		ResultSet rs = null;
		try{
			StringBuilder sql = new StringBuilder();
			sql.append(" SELECT DISTINCT REASON_ID FROM IM_FOLLOW_REASON ");
			sql.append(" WHERE TRANSACTIONID = ? ");
			//logger.debug("sql : "+sql);
			ps = conn.prepareStatement(sql.toString());
			
			for(String transactionId : transactionIdList)
			{
				ps.setString(1, transactionId);
				rs = ps.executeQuery();
				while(rs.next())
				{
					if(!Util.empty(rs.getString("REASON_ID")))
					{reasonIdList.add(rs.getString("REASON_ID"));}
				}
			}
			
		}catch(Exception e){
			logger.fatal("ERROR ",e);
			throw new InfBatchException(e.getMessage());
		}finally{
			try{
				closeConnection(ps, rs);
			}catch(Exception e){
				logger.fatal("ERROR ", e);
			}
		}
		return reasonIdList;
	}
	
	@Override
	public ArrayList<Integer> loadUnusedInstantXDaysUp(Integer day) throws InfBatchException{
		Connection conn = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		ArrayList<Integer> instants = new ArrayList<Integer>();
		try{
			conn = getConnection();
			StringBuilder sql = new StringBuilder();
			sql.append(" SELECT BPD_INSTANCE_ID FROM LSW_BPD_INSTANCE LBI ");
			sql.append(" WHERE NOT EXISTS ");
			sql.append(" (SELECT 1 FROM ORIG_APPLICATION_GROUP AG WHERE LBI.BPD_INSTANCE_ID = AG.INSTANT_ID) ");
			sql.append(" AND ( SYSDATE - TRUNC(CREATE_DATETIME) > ? ) ");
			sql.append(" ORDER BY CREATE_DATETIME ASC ");
			ps = conn.prepareStatement(sql.toString());
			
			int parameterIndex = 1;
			ps.setInt(parameterIndex++, day);
			rs = ps.executeQuery();
			
			while(rs.next()){
				instants.add(rs.getInt("BPD_INSTANCE_ID"));		
			}
			
		}catch(Exception e){
			logger.fatal("ERROR ",e);
			throw new InfBatchException(e);
		}finally{
			try{
				closeConnection(conn, rs, ps);
			}catch(Exception e){
				logger.fatal("ERROR ",e);
			}
		}
		return instants;
	}
	
	@Override
	public boolean checkNotHaveApp(Integer instantId) throws InfBatchException{
		Connection conn = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		Boolean result = true;
		try{
			conn = getConnection();
			StringBuilder sql = new StringBuilder();
			sql.append(" SELECT 1 ");
			sql.append(" FROM ORIG_APPLICATION_GROUP");
			sql.append(" WHERE ");
			sql.append(" INSTANT_ID = ?  ");
			
			ps = conn.prepareStatement(sql.toString());
			
			int parameterIndex = 1;
			
			ps.setInt(parameterIndex++, instantId);
						
			rs = ps.executeQuery();
			while(rs.next()){
				result = false;
			}
		}catch(Exception e){
			logger.fatal("ERROR ",e);
			result = false;
			throw new InfBatchException(e);
		}finally{
			try{
				closeConnection(conn, rs, ps);
			}catch(Exception e){
				logger.fatal("ERROR ",e);
			}
		}
		return result;
	}
	
	@Override
	public void callCloneTaskData() throws Exception {
		Connection conn = null;
		CallableStatement cs = null;
		try{
			conn = getConnection();
			StringBuilder sql = new StringBuilder();
			sql.append(" { call PKA_DASHBOARD.p_clone_task() } ");
			logger.debug("sql : "+sql);
			cs = conn.prepareCall(sql.toString());
			cs.execute();
		}catch(Exception e){
			logger.fatal("ERROR ",e);
			throw new InfBatchException(e.getLocalizedMessage());
		}finally{
			try{
				closeConnection(conn, cs);
			}catch(Exception e){
				logger.fatal("ERROR ",e);
			}
		}
	}

	@Override
	public String backupBeforeDelete1(Connection conn, String table, String key, ArrayList<String> keyValueList) 
			throws Exception 
	{
		String jsonString = "";
        if(keyValueList != null && keyValueList.size() > 0)
		{	
        	PreparedStatement ps = null;
    		ResultSet rs = null;
    		
    		try{
				StringBuilder sql = new StringBuilder();
				sql.append(" SELECT * ");
				sql.append(" FROM " + table);
				sql.append(" WHERE " + key + " = ? ");
				//logger.debug("sql : "+sql);
				ps = conn.prepareStatement(sql.toString());
				
				for(String keyValue : keyValueList)
				{
					ps.setString(1, keyValue);
					rs = ps.executeQuery();
					jsonString += InfBatchUtil.resultAsJSONString(rs);
				}
				
			}catch(Exception e){
				logger.fatal("ERROR ",e);
				throw new InfBatchException(e.getMessage());
			}finally
			{
				try{
					closeConnection(ps, rs);
				}catch(Exception e){
					logger.fatal("ERROR ", e);
				}
			}
		}
        return  jsonString;
	}

	@Override
	public String backupBeforeDelete2(Connection conn, String table, String key, ArrayList<String> keyValueList,
			String key2, String keyValue2) throws Exception 
	{
		String jsonString = "";
		if(keyValueList != null && keyValueList.size() > 0)
		{
        	PreparedStatement ps = null;
    		ResultSet rs = null;
    		try{
				StringBuilder sql = new StringBuilder();
				sql.append(" SELECT * ");
				sql.append(" FROM " + table);
				sql.append(" WHERE " + key + " = ? ");
				sql.append(" AND " + key2 + " = '" + keyValue2 + "' ");
				//logger.debug("sql : "+sql);
				ps = conn.prepareStatement(sql.toString());
				
				for(String keyValue : keyValueList)
				{
					ps.setString(1, keyValue);
					rs = ps.executeQuery();
					jsonString += InfBatchUtil.resultAsJSONString(rs);
				}
				
			}catch(Exception e){
				logger.fatal("ERROR ",e);
				throw new InfBatchException(e.getMessage());
			}finally{
				try{
					closeConnection(ps, rs);
				}catch(Exception e){
					logger.fatal("ERROR ", e);
				}
			}
		}
		return  jsonString;
	}
	
	public String backupBeforeDeleteP(PreparedStatement ps, String table, ArrayList<String> keyValueList) 
			throws Exception 
	{
		String jsonString = "";
        if(keyValueList != null && keyValueList.size() > 0)
		{	
        	ResultSet rs = null;
    		
    		try{
				
				for(String keyValue : keyValueList)
				{
					ps.setString(1, keyValue);
					rs = ps.executeQuery();
					jsonString += InfBatchUtil.resultAsJSONString(rs);
				}
				
			}catch(Exception e){
				logger.fatal("ERROR ",e);
				throw new InfBatchException(e.getMessage());
			}finally
			{
				try{
					if(rs != null){rs.close();}
				}catch(Exception e){
					logger.fatal("ERROR ", e);
				}
			}
		}
        return  jsonString;
	}

	public static void writeBackupJsonStringToFile(String schema, String table, String jsonString) throws Exception
	{
		ArrayList<String> lines = new ArrayList<>();
		lines.add(jsonString);

		String outputPathStr = DELETE_OLD_APP_FILE_BACKUP_PATH + File.separator + schema + File.separator + table + ".json";
		Path outputPath = Paths.get(outputPathStr);
		File file = new File(outputPathStr);
		if(!file.getParentFile().exists())
		{file.getParentFile().mkdirs();}
		Files.write(outputPath, lines, Charset.forName("UTF-8"), StandardOpenOption.CREATE, StandardOpenOption.APPEND);
	}

	@Override
	public void updateArcStatusFail(Connection conn, String errorMessage,
			ArrayList<String> appGroupIdList) throws Exception
	{
		PreparedStatement ps = null;
		if(errorMessage != null)
		{
			errorMessage = "<" + StringUtils.abbreviate(errorMessage, 1995) + ">";
		}
		try{
			StringBuilder sql = new StringBuilder();
			sql.append(" UPDATE ORIG_APPLICATION_GROUP_CAT ");
			sql.append(" SET ARC_STATUS = 'F' ");
			sql.append(" ,PURGE_COMMENT = CONCAT(PURGE_COMMENT, ?) ");
			sql.append(" WHERE APPLICATION_GROUP_ID = ? ");
			//logger.debug("sql : "+sql);
			ps = conn.prepareStatement(sql.toString());
			
			for(String appGroupId : appGroupIdList)
			{
				ps.setString(1, errorMessage);
				ps.setString(2, appGroupId);
				ps.addBatch();
			}
			
			ps.executeBatch();
		}
		catch(Exception e)
		{
			logger.fatal("ERROR ",e);
			throw new InfBatchException(e.getMessage());
		}
		finally
		{
			try{
				closeConnection(ps);
			}catch(Exception e){
				logger.fatal("ERROR ", e);
			}
		}
	}
	
	@Override
	public HashMap<String,String> loadOldAppToDeleteMLP() throws Exception 
	{
		String DELETE_OLD_APP_CUT_OFF = InfBatchProperty.getInfBatchConfig("DELETE_OLD_APP_CUT_OFF");
		String JOBSTATE_POST_APPROVED = SystemConstant.getConstant("JOBSTATE_POST_APPROVED");
		HashMap<String,String> appGroupInfoList = new HashMap<String,String>();
		Connection conn = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		try{
			conn = getConnection(InfBatchServiceLocator.MLP_DB);
			StringBuilder sql = new StringBuilder();
			sql.append(" SELECT APPLICATION_GROUP_ID, APPLICATION_GROUP_NO FROM ORIG_APPLICATION_GROUP ");
			sql.append(" WHERE (SYSDATE - TRUNC(CREATE_DATE)) > " + DELETE_OLD_APP_CUT_OFF);
			sql.append(" AND JOB_STATE = ? ");
			logger.debug("sql : "+sql);
			ps = conn.prepareStatement(sql.toString());
			ps.setString(1, JOBSTATE_POST_APPROVED);
			rs = ps.executeQuery();
			while(rs.next())
			{
				appGroupInfoList.put(rs.getString("APPLICATION_GROUP_ID"), rs.getString("APPLICATION_GROUP_NO"));
			}
			
		}catch(Exception e){
			logger.fatal("ERROR ",e);
			throw new InfBatchException(e.getMessage());
		}finally{
			try{
				closeConnection(conn, rs, ps);
			}catch(Exception e){
				logger.fatal("ERROR ", e);
			}
		}
		return appGroupInfoList;
	}
	
}
