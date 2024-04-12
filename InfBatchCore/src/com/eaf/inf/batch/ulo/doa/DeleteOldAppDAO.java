package com.eaf.inf.batch.ulo.doa;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.util.ArrayList;
import java.util.HashMap;

import com.eaf.inf.batch.ulo.ctoa.ApplicationGroupCatDataM;

public interface DeleteOldAppDAO 
{
	public ArrayList<ApplicationGroupCatDataM> loadOldAppToDelete() throws Exception;
	public ArrayList<String> getIMImageCatPathList(ArrayList<String> appGroupNoList) throws Exception;
	public ArrayList<String> getIMImagePathList(ArrayList<String> hashIdList) throws Exception;
	public ArrayList<String> getIMFllwReasonIdList(Connection conn, ArrayList<String> transactionIdList) throws Exception;
	public ArrayList<String> getExecutionIdList(Connection conn, ArrayList<String> appGroupNoList) throws Exception;
	public void updateArcStatusComplete(Connection conn, ArrayList<String> appGroupIdList) throws Exception;
	public void updatePurgeDate(Connection conn, String field, String key, ArrayList<String> keyValueList) throws Exception;
	public void call_LSW_BPD_INSTANCE_DELETE(Connection bpmConn, ArrayList<Integer> instantId) throws Exception;
	public ArrayList<Integer> loadUnusedInstantXDaysUp(Integer day) throws Exception;
	public boolean checkNotHaveApp(Integer instantId) throws Exception;
	public void callCloneTaskData() throws Exception;
	public void deleteUtil1(Connection conn, String table, String keyName, ArrayList<String> keyValueList, boolean writeJsonTable) throws Exception;
	public void deleteUtil1(Connection conn, String table, String keyName, ArrayList<String> keyValueList) throws Exception;
	public void deleteUtil2(Connection conn, String table, String keyName1, ArrayList<String> keyValueList1, String keyName2, String keyValue2, boolean writeJsonTable) throws Exception;
	public void deleteUtil2(Connection conn, String table,String keyName1, ArrayList<String> keyListValue1, String keyName2, String keyValue2) throws Exception;
	public ArrayList<String> selectUtil1(Connection conn, String table, String keyToGet, String key, ArrayList<String> keyValueList) throws Exception;
	public ArrayList<String> selectUtil2(Connection conn, String table, String keyToGet, String key, ArrayList<String> keyValueList, String key2, String keyValue2) throws Exception;
	public String backupBeforeDelete1(Connection conn, String table, String keyName,ArrayList<String> keyValueList)  throws Exception;
	public String backupBeforeDelete2(Connection conn, String table,String keyName1, ArrayList<String> keyListValue1, String keyName2, String keyValue2) throws Exception;
	public void updateArcStatusFail(Connection conn, String errorMessage, ArrayList<String> appGroupIdList) throws Exception;
	public ArrayList<String> getIMTransactionIdList(Connection conn, ArrayList<String> hashIdList) throws Exception;
	public ArrayList<String> selectUtilP(PreparedStatement ps, String table, String keyToGet, ArrayList<String> keyValueList)throws Exception;
	public void deleteUtil1_P(PreparedStatement deletePs, PreparedStatement backupPs, String table, ArrayList<String> keyValueList, boolean writeJsonTable) throws Exception;
	public void deleteUtil1_P(HashMap<String, PreparedStatement> psHm, String table, ArrayList<String> keyValueList, boolean writeJsonTable) throws Exception;
	public void deleteUtil1_P(HashMap<String, PreparedStatement> psHm, String table, ArrayList<String> keyValueList) throws Exception;
	public void deleteUtil2_P(PreparedStatement deletePs, PreparedStatement backupPs, String table, ArrayList<String> keyValueList1, boolean writeJsonTable) throws Exception;
	public void deleteUtil2_P(HashMap<String,PreparedStatement> psHm, String table, ArrayList<String> keyValueList1, boolean writeJsonTable) throws Exception;
	public void deleteUtil2_P(HashMap<String,PreparedStatement> psHm, String table, ArrayList<String> keyValueList1) throws Exception;
	public void updatePurgeDateP(PreparedStatement ps, ArrayList<String> keyValueList) throws Exception;
	public HashMap<String,String> loadOldAppToDeleteMLP() throws Exception;
	
}
