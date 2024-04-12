package com.avalant.wm.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;

import com.avalant.wm.common.ObjectDAO;
import com.avalant.wm.model.Task;
import com.avalant.wm.model.WmTask;
import com.avalant.wm.model.WmTaskData;
import com.avalant.wm.model.WmTaskHistory;
import com.avalant.wm.model.WmTaskLog;
import com.eaf.core.ulo.common.properties.SystemConfig;
import com.eaf.core.ulo.common.properties.SystemConstant;
import com.eaf.core.ulo.common.util.Util;
import com.eaf.orig.ulo.app.util.GenerateUnique;
import com.eaf.orig.ulo.constant.MConstant;
import com.eaf.service.common.api.ServiceApplicationDate;

public class WmDAOImpl extends ObjectDAO implements WmDAO {
	
	private Logger logger = Logger.getLogger(WmDAOImpl.class);
	

	@Override
	public void saveTaskManager(WmTask task, WmTaskHistory taskHistory) throws Exception {
		if(updateTaskManager(task) == 0){
			createTaskManager(task);
			
			if(null != task.getData() && !Util.empty(task.getData().getTaskData())){
				task.getData().setTaskId(task.getTaskId());
				createTaskData(task.getData());
			}
			
			createTaskHistory(taskHistory);
		}
		else{
			if(null != task.getData() && !Util.empty(task.getData().getTaskData())){
				updateTaskData(task.getData());
			}
		}
	}
	
	private int updateTaskManager(WmTask task) throws Exception{
		PreparedStatement ps = null;	
		Connection conn = null;
		int result = 0;
		try{			
			conn = getConnection();					
			StringBuilder sql = new StringBuilder("");			
			sql.append(" UPDATE WM_TASK ");
			sql.append(" SET NAME = ?, TASK = ?, REF_ID = ?, REF_CODE = ?, TASK_DATA = ?, STATUS = ? ");
			sql.append(" WHERE TASK_ID = ? ");
			
			String dSql = String.valueOf(sql);
			logger.debug("Sql=" + dSql);
			ps = conn.prepareStatement(dSql);
			int index = 1;
			ps.setString(index++, task.getServerName());
			ps.setString(index++, task.getWmFunc());
			ps.setString(index++, task.getRefId());
			ps.setString(index++, task.getRefCode());
			ps.setString(index++, task.getTaskData());
			ps.setString(index++, task.getStatus());
			
			//where
			ps.setString(index++, task.getTaskId());
			
			result = ps.executeUpdate();
			
		}catch (Exception e) {
			logger.fatal(e.getLocalizedMessage());
			throw new Exception(e.getMessage());
		} finally {
			try {
				closeConnection(conn, ps);
			} catch (Exception e) {
				logger.fatal(e.getLocalizedMessage());
				throw new Exception(e.getMessage());
			}
		}
		return result;
	}
	
	private void createTaskManager(WmTask task) throws Exception{
		PreparedStatement ps = null;	
		Connection conn = null;	
		try{			
			conn = getConnection();					
			StringBuilder sql = new StringBuilder("");			
			sql.append(" INSERT INTO WM_TASK ");
			sql.append("( TASK_ID, NAME, TASK, CREATE_TIME, TASK_DATA, REF_ID, REF_CODE, ERROR_COUNT ) ");
			sql.append("VALUES (?,?,?,SYSTIMESTAMP(6), ?, ?, ?, ? )");
			
			String dSql = String.valueOf(sql);
			logger.debug("Sql=" + dSql);
			ps = conn.prepareStatement(dSql);
			int index = 1;
			ps.setString(index++, task.getTaskId());
			ps.setString(index++, task.getServerName());
			ps.setString(index++, task.getWmFunc());
			ps.setString(index++, task.getTaskData());
			ps.setString(index++, task.getRefId());
			ps.setString(index++, task.getRefCode());
			ps.setInt(index++, task.getErrorCount());
			
			ps.executeUpdate();
			
		}catch (Exception e) {
			logger.fatal(e.getLocalizedMessage());
			throw new Exception(e.getMessage());
		} finally {
			try {
				closeConnection(conn, ps);
			} catch (Exception e) {
				logger.fatal(e.getLocalizedMessage());
				throw new Exception(e.getMessage());
			}
		}
	}

	@Override
	public Task getTask(String serverName) throws Exception {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<WmTask> getNotTaskAssign() throws Exception {
		Connection conn = null;	
		PreparedStatement ps = null;
		ResultSet rs = null;
		List<WmTask> taskList = new ArrayList<WmTask>();
		try{			
			conn = getConnection();					
			StringBuilder sql = new StringBuilder("");			
			sql.append(" SELECT TASK_ID, NAME, TASK, REF_ID, REF_CODE, TASK_DATA, STATUS ");
			sql.append(" FROM WM_TASK ");
			sql.append(" WHERE NAME IS NULL AND STATUS IS NULL AND (NEXT_RUN_TIME IS NULL OR NEXT_RUN_TIME < ?) ");
			sql.append(" ORDER BY NVL(NEXT_RUN_TIME, CREATE_TIME) ");
			
			String dSql = String.valueOf(sql);
			logger.debug("Sql=" + dSql);
			ps = conn.prepareStatement(dSql);
			int index = 1;
			ps.setTimestamp(index++, ServiceApplicationDate.getTimestamp());
			rs = ps.executeQuery();
			
			while(rs.next()){
				WmTask task = new WmTask();
				task.setTaskId(rs.getString("TASK_ID"));
				task.setServerName(rs.getString("NAME"));
				task.setWmFunc(rs.getString("TASK"));
				task.setRefId(rs.getString("REF_ID"));
				task.setRefCode(rs.getString("REF_CODE"));
				task.setTaskData(rs.getString("TASK_DATA"));
				task.setStatus(rs.getString("STATUS"));
				taskList.add(task);
			}
			
			
		}catch (Exception e) {
			logger.fatal(e.getLocalizedMessage());
			throw new Exception(e.getMessage());
		} finally {
			try {
				closeConnection(conn, rs, ps);
			} catch (Exception e) {
				logger.fatal(e.getLocalizedMessage());
				throw new Exception(e.getMessage());
			}
		}
		return taskList;
	}
	
	@Override
	public List<WmTask> getNotTaskAssign(ArrayList<String> taskGroup) throws Exception {
		Connection conn = null;	
		PreparedStatement ps = null;
		ResultSet rs = null;
		List<WmTask> taskList = new ArrayList<WmTask>();
		try{			
			conn = getConnection();					
			StringBuilder sql = new StringBuilder("");			
			sql.append(" SELECT TASK_ID, NAME, TASK, REF_ID, REF_CODE, TASK_DATA, STATUS ");
			sql.append(" FROM WM_TASK ");
			sql.append(" WHERE NAME IS NULL AND STATUS IS NULL AND (NEXT_RUN_TIME IS NULL OR NEXT_RUN_TIME < ?) ");
			if(!Util.empty(taskGroup)){
				sql.append(" AND TASK IN ( ");
				for(int i = 1; i <= taskGroup.size(); i++){
					if(i == taskGroup.size()){
						sql.append(" ? ");
					}
					else{
						sql.append(" ?, ");
					}
				}
				sql.append(" ) ");
			}
			sql.append(" ORDER BY NVL(NEXT_RUN_TIME, CREATE_TIME) ");
			
			String dSql = String.valueOf(sql);
			logger.debug("Sql=" + dSql);
			ps = conn.prepareStatement(dSql);
			int index = 1;
			ps.setTimestamp(index++, ServiceApplicationDate.getTimestamp());
			if(!Util.empty(taskGroup)){
				for(String task : taskGroup){
					ps.setString(index++, task);
				}
			}
			rs = ps.executeQuery();
			
			while(rs.next()){
				WmTask task = new WmTask();
				task.setTaskId(rs.getString("TASK_ID"));
				task.setServerName(rs.getString("NAME"));
				task.setWmFunc(rs.getString("TASK"));
				task.setRefId(rs.getString("REF_ID"));
				task.setRefCode(rs.getString("REF_CODE"));
				task.setTaskData(rs.getString("TASK_DATA"));
				task.setStatus(rs.getString("STATUS"));
				taskList.add(task);
			}
			
			
		}catch (Exception e) {
			logger.fatal(e.getLocalizedMessage());
			throw new Exception(e.getMessage());
		} finally {
			try {
				closeConnection(conn, rs, ps);
			} catch (Exception e) {
				logger.fatal(e.getLocalizedMessage());
				throw new Exception(e.getMessage());
			}
		}
		return taskList;
	}

	@Override
	public String getLastAssign() throws Exception {
		Connection conn = null;	
		PreparedStatement ps = null;
		ResultSet rs = null;
		String name = "";
		try{			
			conn = getConnection();					
			StringBuilder sql = new StringBuilder("");			
			sql.append(" SELECT NAME ");
			sql.append(" FROM WM_TASK ");
			sql.append(" WHERE NAME IS NOT NULL ORDER BY CREATE_TIME desc ");
			
			String dSql = String.valueOf(sql);
			logger.debug("Sql=" + dSql);
			ps = conn.prepareStatement(dSql);
			rs = ps.executeQuery();
			
			if(rs.next()){
				name = rs.getString("NAME");
			}
			
			
		}catch (Exception e) {
			logger.fatal(e.getLocalizedMessage());
			throw new Exception(e.getMessage());
		} finally {
			try {
				closeConnection(conn, rs, ps);
			} catch (Exception e) {
				logger.fatal(e.getLocalizedMessage());
				throw new Exception(e.getMessage());
			}
		}
		return name;
	}

	@Override
	public void deleteCompleteTask(String taskId) throws Exception {
//		deleteWmTask(taskId);
//		deleteTaskData(taskId);
		updateFinishTime(taskId);
	}
	
	private void deleteWmTask(String taskId) throws Exception{
		Connection conn = null;	
		PreparedStatement ps = null;
		try{			
			conn = getConnection();					
			StringBuilder sql = new StringBuilder("");			
			sql.append(" DELETE FROM WM_TASK ");
			sql.append(" WHERE TASK_ID = ? ");
			
			String dSql = String.valueOf(sql);
			logger.debug("Sql=" + dSql);
			int index = 1;
			ps = conn.prepareStatement(dSql);
			ps.setString(index++, taskId);
			ps.executeUpdate();
			
		}catch (Exception e) {
			logger.fatal(e.getLocalizedMessage());
			throw new Exception(e.getMessage());
		} finally {
			try {
				closeConnection(conn, ps);
			} catch (Exception e) {
				logger.fatal(e.getLocalizedMessage());
				throw new Exception(e.getMessage());
			}
		}
	}

	@Override
	public List<WmTask> getAssignTask(String processName) throws Exception {
		Connection conn = null;	
		PreparedStatement ps = null;
		ResultSet rs = null;
		List<WmTask> tasks = new ArrayList<WmTask>();
		try{			
			conn = getConnection();					
			StringBuilder sql = new StringBuilder("");			
			sql.append(" SELECT TASK_ID, NAME, TASK, REF_ID, REF_CODE, TASK_DATA, ERROR_COUNT, ERROR_TOTAL, STATUS ");
			sql.append(" FROM WM_TASK ");
			sql.append(" WHERE NAME = ? and STATUS is null ORDER BY CREATE_TIME desc ");
			
			String dSql = String.valueOf(sql);
			logger.debug("Sql=" + dSql);
			int index = 1;
			ps = conn.prepareStatement(dSql);
			ps.setString(index++, processName);
			rs = ps.executeQuery();
			
			while(rs.next()){
				WmTask wmTask = new WmTask();
				wmTask.setTaskId(rs.getString("TASK_ID"));
				wmTask.setServerName(rs.getString("NAME"));
				wmTask.setWmFunc(rs.getString("TASK"));
				wmTask.setRefId(rs.getString("REF_ID"));
				wmTask.setRefCode(rs.getString("REF_CODE"));
				wmTask.setTaskData(rs.getString("TASK_DATA"));
				wmTask.setErrorCount(rs.getInt("ERROR_COUNT"));
				wmTask.setErrorTotal(rs.getInt("ERROR_TOTAL"));
				wmTask.setStatus(rs.getString("STATUS"));
				
				tasks.add(wmTask);
			}
			
			
		}catch (Exception e) {
			logger.fatal(e.getLocalizedMessage());
			throw new Exception(e.getMessage());
		} finally {
			try {
				closeConnection(conn, rs, ps);
			} catch (Exception e) {
				logger.fatal(e.getLocalizedMessage());
				throw new Exception(e.getMessage());
			}
		}
		
		return tasks;
	}
	
	@Override
	public void updateTaskStatus(String taskId, String status) throws Exception {
		Connection conn = null;	
		PreparedStatement ps = null;
		try{			
			conn = getConnection();					
			StringBuilder sql = new StringBuilder("");			
			sql.append(" UPDATE WM_TASK ");
			sql.append(" SET STATUS = ? ");
			sql.append(" WHERE TASK_ID = ? ");
			
			String dSql = String.valueOf(sql);
			logger.debug("Sql=" + dSql);
			int index = 1;
			ps = conn.prepareStatement(dSql);
			ps.setString(index++, status);
			ps.setString(index++, taskId);
			ps.executeUpdate();
			
		}catch (Exception e) {
			logger.fatal(e.getLocalizedMessage());
			throw new Exception(e.getMessage());
		} finally {
			try {
				closeConnection(conn, ps);
			} catch (Exception e) {
				logger.fatal(e.getLocalizedMessage());
				throw new Exception(e.getMessage());
			}
		}
	}

	@Override
	public List<WmTask> getAssignTaskByTime(String processName)
			throws Exception {
		List<WmTask> tasks = selectWmAssignTask(processName);
		for(WmTask wmTask : tasks){
			WmTaskData taskData = getTaskData(wmTask.getTaskId());
			wmTask.setData(taskData);
		}
		
		return tasks;
	}
	
	@Override
	public List<WmTask> getAssignTaskByTime(String processName, ArrayList<String> taskGroup)
			throws Exception {
		List<WmTask> tasks = selectWmAssignTask(processName, taskGroup);
		for(WmTask wmTask : tasks){
			WmTaskData taskData = getTaskData(wmTask.getTaskId());
			wmTask.setData(taskData);
		}
		
		return tasks;
	}
	
	private List<WmTask> selectWmAssignTask(String processName) throws Exception {
		Connection conn = null;	
		PreparedStatement ps = null;
		ResultSet rs = null;
		List<WmTask> tasks = new ArrayList<WmTask>();
		try{			
			conn = getConnection();					
			StringBuilder sql = new StringBuilder("");			
			sql.append(" SELECT TASK_ID, NAME, TASK, REF_ID, REF_CODE, TASK_DATA, ERROR_COUNT, ERROR_TOTAL, STATUS ");
			sql.append(" FROM WM_TASK ");
			sql.append(" WHERE NAME = ? and STATUS is null and NEXT_RUN_TIME < ? ORDER BY CREATE_TIME desc ");
			
			String dSql = String.valueOf(sql);
			logger.debug("Sql=" + dSql);
			int index = 1;
			ps = conn.prepareStatement(dSql);
			ps.setString(index++, processName);
			ps.setTimestamp(index++, ServiceApplicationDate.getTimestamp());
			rs = ps.executeQuery();
			
			while(rs.next()){
				WmTask wmTask = new WmTask();
				wmTask.setTaskId(rs.getString("TASK_ID"));
				wmTask.setServerName(rs.getString("NAME"));
				wmTask.setWmFunc(rs.getString("TASK"));
				wmTask.setRefId(rs.getString("REF_ID"));
				wmTask.setRefCode(rs.getString("REF_CODE"));
				wmTask.setTaskData(rs.getString("TASK_DATA"));
				wmTask.setErrorCount(rs.getInt("ERROR_COUNT"));
				wmTask.setErrorTotal(rs.getInt("ERROR_TOTAL"));
				wmTask.setStatus(rs.getString("STATUS"));
				
				tasks.add(wmTask);
			}
			
			
		}catch (Exception e) {
			logger.fatal(e.getLocalizedMessage());
			throw new Exception(e.getMessage());
		} finally {
			try {
				closeConnection(conn, rs, ps);
			} catch (Exception e) {
				logger.fatal(e.getLocalizedMessage());
				throw new Exception(e.getMessage());
			}
		}
		
		return tasks;
	}
	
	private List<WmTask> selectWmAssignTask(String processName, ArrayList<String> taskGroup) throws Exception {
		Connection conn = null;	
		PreparedStatement ps = null;
		ResultSet rs = null;
		List<WmTask> tasks = new ArrayList<WmTask>();
		try{			
			conn = getConnection();					
			StringBuilder sql = new StringBuilder("");			
			sql.append(" SELECT TASK_ID, NAME, TASK, REF_ID, REF_CODE, TASK_DATA, ERROR_COUNT, ERROR_TOTAL, STATUS ");
			sql.append(" FROM WM_TASK ");
			sql.append(" WHERE NAME = ? and STATUS is null and NEXT_RUN_TIME < ? ");
			if(!Util.empty(taskGroup)){
				sql.append(" AND TASK IN ( ");
				for(int i = 1; i <= taskGroup.size(); i++){
					if(i == taskGroup.size()){
						sql.append(" ? ");
					}
					else{
						sql.append(" ?, ");
					}
				}
				sql.append(" ) ");
			}
			sql.append(" ORDER BY CREATE_TIME desc ");
			
			String dSql = String.valueOf(sql);
			logger.debug("Sql=" + dSql);
			int index = 1;
			ps = conn.prepareStatement(dSql);
			ps.setString(index++, processName);
			ps.setTimestamp(index++, ServiceApplicationDate.getTimestamp());
			if(!Util.empty(taskGroup)){
				for(String task : taskGroup){
					ps.setString(index++, task);
				}
			}
			rs = ps.executeQuery();
			
			while(rs.next()){
				WmTask wmTask = new WmTask();
				wmTask.setTaskId(rs.getString("TASK_ID"));
				wmTask.setServerName(rs.getString("NAME"));
				wmTask.setWmFunc(rs.getString("TASK"));
				wmTask.setRefId(rs.getString("REF_ID"));
				wmTask.setRefCode(rs.getString("REF_CODE"));
				wmTask.setTaskData(rs.getString("TASK_DATA"));
				wmTask.setErrorCount(rs.getInt("ERROR_COUNT"));
				wmTask.setErrorTotal(rs.getInt("ERROR_TOTAL"));
				wmTask.setStatus(rs.getString("STATUS"));
				
				tasks.add(wmTask);
			}
			
			
		}catch (Exception e) {
			logger.fatal(e.getLocalizedMessage());
			throw new Exception(e.getMessage());
		} finally {
			try {
				closeConnection(conn, rs, ps);
			} catch (Exception e) {
				logger.fatal(e.getLocalizedMessage());
				throw new Exception(e.getMessage());
			}
		}
		
		return tasks;
	}

	@Override
	public void updateLastRunTime(String taskId) throws Exception {
		Connection conn = null;	
		PreparedStatement ps = null;
		try{			
			conn = getConnection();					
			StringBuilder sql = new StringBuilder("");			
			sql.append(" UPDATE WM_TASK ");
			sql.append(" SET LAST_RUN_TIME = ? ");
			sql.append(" WHERE TASK_ID = ? ");
			
			String dSql = String.valueOf(sql);
			logger.debug("Sql=" + dSql);
			int index = 1;
			ps = conn.prepareStatement(dSql);
			ps.setTimestamp(index++, ServiceApplicationDate.getTimestamp());
			ps.setString(index++, taskId);
			ps.executeUpdate();
			
		}catch (Exception e) {
			logger.fatal(e.getLocalizedMessage());
			throw new Exception(e.getMessage());
		} finally {
			try {
				closeConnection(conn, ps);
			} catch (Exception e) {
				logger.fatal(e.getLocalizedMessage());
				throw new Exception(e.getMessage());
			}
		}
	}

	@Override
	public void updateNextRunTime(String taskId, int addTime) throws Exception {
		Connection conn = null;	
		PreparedStatement ps = null;
		try{			
			conn = getConnection();					
			StringBuilder sql = new StringBuilder("");			
			sql.append(" UPDATE WM_TASK ");
			sql.append(" SET NEXT_RUN_TIME = ? + (1/24/60/60 * ?) ");
			sql.append(" WHERE TASK_ID = ? ");
			
			String dSql = String.valueOf(sql);
			logger.debug("Sql=" + dSql);
			int index = 1;
			ps = conn.prepareStatement(dSql);
			ps.setTimestamp(index++, ServiceApplicationDate.getTimestamp());
			ps.setInt(index++, addTime);
			ps.setString(index++, taskId);
			ps.executeUpdate();
			
		}catch (Exception e) {
			logger.fatal(e.getLocalizedMessage());
			throw new Exception(e.getMessage());
		} finally {
			try {
				closeConnection(conn, ps);
			} catch (Exception e) {
				logger.fatal(e.getLocalizedMessage());
				throw new Exception(e.getMessage());
			}
		}
	}

	@Override
	public void increaseErrorCount(String taskId) throws Exception {
		Connection conn = null;	
		PreparedStatement ps = null;
		try{			
			conn = getConnection();					
			StringBuilder sql = new StringBuilder("");			
			sql.append(" UPDATE WM_TASK ");
			sql.append(" SET ERROR_COUNT = NVL(ERROR_COUNT,0) + 1, ERROR_TOTAL = NVL(ERROR_TOTAL,0) + 1 ");
			sql.append(" WHERE TASK_ID = ? ");
			
			String dSql = String.valueOf(sql);
			logger.debug("Sql=" + dSql);
			int index = 1;
			ps = conn.prepareStatement(dSql);
			ps.setString(index++, taskId);
			ps.executeUpdate();
			
		}catch (Exception e) {
			logger.fatal(e.getLocalizedMessage());
			throw new Exception(e.getMessage());
		} finally {
			try {
				closeConnection(conn, ps);
			} catch (Exception e) {
				logger.fatal(e.getLocalizedMessage());
				throw new Exception(e.getMessage());
			}
		}
	}

	@Override
	public int countServerTask(String serverName) throws Exception {
		Connection conn = null;	
		PreparedStatement ps = null;
		ResultSet rs = null;
		int result = 0;
		try{			
			conn = getConnection();					
			StringBuilder sql = new StringBuilder("");			
			sql.append(" SELECT COUNT(1) AS COUNT FROM WM_TASK ");
			sql.append(" WHERE NAME = ? and (STATUS is null or STATUS = ? ) ");
			
			String dSql = String.valueOf(sql);
			logger.debug("Sql=" + dSql);
			int index = 1;
			ps = conn.prepareStatement(dSql);
			ps.setString(index++, serverName);
			ps.setString(index++, MConstant.TASK_STATUS.RUNNING);
			rs = ps.executeQuery();
			if(rs.next()){
				result = rs.getInt("COUNT");
			}
			
		}catch (Exception e) {
			logger.fatal(e.getLocalizedMessage());
			throw new Exception(e.getMessage());
		} finally {
			try {
				closeConnection(conn, rs, ps);
			} catch (Exception e) {
				logger.fatal(e.getLocalizedMessage());
				throw new Exception(e.getMessage());
			}
		}
		return result;
	}

	@Override
	public void createErrorLog(WmTaskLog taskLog) throws Exception {
		PreparedStatement ps = null;	
		Connection conn = null;	
		try{			
			conn = getConnection();					
			StringBuilder sql = new StringBuilder("");
			sql.append(" INSERT INTO WM_TASK_ERROR ");
			sql.append(" ( TASK_ID, TASK_ERROR_ID, TASK, RESP_CODE, ERROR_MSG, MSG, CREATE_DATE ) ");
			sql.append(" VALUES (?, ?, ?, ?, ?, ?, SYSTIMESTAMP(6) ) ");
			
			String dSql = String.valueOf(sql);
			logger.debug("Sql=" + dSql);
			ps = conn.prepareStatement(dSql);
			int index = 1;
			ps.setString(index++, taskLog.getTaskId());
			ps.setString(index++, taskLog.getTaskErrorId());
			ps.setString(index++, taskLog.getTask());
			ps.setString(index++, taskLog.getRespCode());
			ps.setString(index++, taskLog.getErrorMsg());
			ps.setString(index++, taskLog.getMsg());
			
			ps.executeUpdate();
			
		}catch (Exception e) {
			logger.fatal(e.getLocalizedMessage());
			throw new Exception(e.getMessage());
		} finally {
			try {
				closeConnection(conn, ps);
			} catch (Exception e) {
				logger.fatal(e.getLocalizedMessage());
				throw new Exception(e.getMessage());
			}
		}
	}
	
	private void updateTaskData(WmTaskData taskData) throws Exception{
		PreparedStatement ps = null;	
		Connection conn = null;	
		try{			
			conn = getConnection();					
			StringBuilder sql = new StringBuilder("");
			sql.append(" UPDATE WM_TASK_DATA ");
			sql.append(" SET TASK_DATA = ? ");
			sql.append(" WHERE TASK_ID = ? ");
			
			String dSql = String.valueOf(sql);
			logger.debug("Sql=" + dSql);
			ps = conn.prepareStatement(dSql);
			int index = 1;
			ps.setString(index++, taskData.getTaskData());
			ps.setString(index++, taskData.getTaskId());
			
			ps.executeUpdate();
			
		}catch (Exception e) {
			logger.fatal(e.getLocalizedMessage());
			throw new Exception(e.getMessage());
		} finally {
			try {
				closeConnection(conn, ps);
			} catch (Exception e) {
				logger.fatal(e.getLocalizedMessage());
				throw new Exception(e.getMessage());
			}
		}
	}
	
	private void createTaskData(WmTaskData taskData) throws Exception{
		PreparedStatement ps = null;	
		Connection conn = null;	
		try{			
			conn = getConnection();					
			StringBuilder sql = new StringBuilder("");
			sql.append(" INSERT INTO WM_TASK_DATA ");
			sql.append("( TASK_ID, TASK_DATA) ");
			sql.append(" VALUES (?, ? ) ");
			
			String dSql = String.valueOf(sql);
			logger.debug("Sql=" + dSql);
			ps = conn.prepareStatement(dSql);
			int index = 1;
			ps.setString(index++, taskData.getTaskId());
			ps.setString(index++, taskData.getTaskData());
			
			ps.executeUpdate();
			
		}catch (Exception e) {
			logger.fatal(e.getLocalizedMessage());
			throw new Exception(e.getMessage());
		} finally {
			try {
				closeConnection(conn, ps);
			} catch (Exception e) {
				logger.fatal(e.getLocalizedMessage());
				throw new Exception(e.getMessage());
			}
		}
	}
	
	private WmTaskData getTaskData(String taskId) throws Exception{
		PreparedStatement ps = null;	
		Connection conn = null;
		ResultSet rs = null;
		WmTaskData taskData = null;
		try{			
			conn = getConnection();					
			StringBuilder sql = new StringBuilder("");
			sql.append(" SELECT TASK_ID, TASK_DATA ");
			sql.append(" FROM WM_TASK_DATA ");
			sql.append(" WHERE TASK_ID = ? ");
			
			String dSql = String.valueOf(sql);
			logger.debug("Sql=" + dSql);
			ps = conn.prepareStatement(dSql);
			int index = 1;
			ps.setString(index++, taskId);
			
			rs = ps.executeQuery();
			if(rs.next()){
				taskData = new WmTaskData();
				taskData.setTaskId(rs.getString("TASK_ID"));
				taskData.setTaskData(rs.getString("TASK_DATA"));
			}
		}catch (Exception e) {
			logger.fatal(e.getLocalizedMessage());
			throw new Exception(e.getMessage());
		} finally {
			try {
				closeConnection(conn, rs, ps);
			} catch (Exception e) {
				logger.fatal(e.getLocalizedMessage());
				throw new Exception(e.getMessage());
			}
		}
		return taskData;
	}
	
	private void deleteTaskData(String taskId) throws Exception{
		PreparedStatement ps = null;	
		Connection conn = null;
		try{			
			conn = getConnection();					
			StringBuilder sql = new StringBuilder("");
			sql.append(" DELETE FROM WM_TASK_DATA ");
			sql.append(" WHERE TASK_ID = ? ");
			
			String dSql = String.valueOf(sql);
			logger.debug("Sql=" + dSql);
			ps = conn.prepareStatement(dSql);
			int index = 1;
			ps.setString(index++, taskId);
			
			ps.executeQuery();
		}catch (Exception e) {
			logger.fatal(e.getLocalizedMessage());
			throw new Exception(e.getMessage());
		} finally {
			try {
				closeConnection(conn, ps);
			} catch (Exception e) {
				logger.fatal(e.getLocalizedMessage());
				throw new Exception(e.getMessage());
			}
		}
	}

	private void createTaskHistory(WmTaskHistory task) throws Exception{
		PreparedStatement ps = null;	
		Connection conn = null;	
		try{			
			conn = getConnection();					
			StringBuilder sql = new StringBuilder("");			
			sql.append(" INSERT INTO WM_TASK_HISTORY ");
			sql.append("( TASK_ID, REF_ID, REF_CODE, TASK, TASK_DATA, CREATE_TIME ) ");
			sql.append("VALUES (?, ?, ?, ?, ?, SYSTIMESTAMP(6) )");
			
			String dSql = String.valueOf(sql);
			logger.debug("Sql=" + dSql);
			ps = conn.prepareStatement(dSql);
			int index = 1;
			ps.setString(index++, task.getTaskId());
			ps.setString(index++, task.getRefId());
			ps.setString(index++, task.getRefCode());
			ps.setString(index++, task.getWmFunc());
			ps.setString(index++, task.getTaskData());
			
			ps.executeUpdate();
			
		}catch (Exception e) {
			logger.fatal(e.getLocalizedMessage());
			throw new Exception(e.getMessage());
		} finally {
			try {
				closeConnection(conn, ps);
			} catch (Exception e) {
				logger.fatal(e.getLocalizedMessage());
				throw new Exception(e.getMessage());
			}
		}
	}
	
	private void updateFinishTime(String taskId) throws Exception{
		PreparedStatement ps = null;	
		Connection conn = null;	
		try{			
			conn = getConnection();					
			StringBuilder sql = new StringBuilder("");			
			sql.append(" UPDATE WM_TASK_HISTORY ");
			sql.append(" SET FINISH_TIME = SYSTIMESTAMP(6) ");
			sql.append(" WHERE TASK_ID = ? ");
			
			String dSql = String.valueOf(sql);
			logger.debug("Sql=" + dSql);
			ps = conn.prepareStatement(dSql);
			int index = 1;
			ps.setString(index++, taskId);
			
			ps.executeUpdate();
			
		}catch (Exception e) {
			logger.fatal(e.getLocalizedMessage());
			throw new Exception(e.getMessage());
		} finally {
			try {
				closeConnection(conn, ps);
			} catch (Exception e) {
				logger.fatal(e.getLocalizedMessage());
				throw new Exception(e.getMessage());
			}
		}
	}

	@Override
	public List<WmTask> getLongRunTask() throws Exception {
		Connection conn = null;	
		PreparedStatement ps = null;
		ResultSet rs = null;
		List<WmTask> tasks = new ArrayList<WmTask>();
		String MAX_TASK_RUN_TIME = SystemConfig.getProperty("MAX_TASK_RUN_TIME");
		try{			
			conn = getConnection();					
			StringBuilder sql = new StringBuilder("");			
			sql.append(" SELECT TASK_ID, NAME, TASK, REF_ID, REF_CODE, TASK_DATA, ERROR_COUNT, ERROR_TOTAL, STATUS ");
			sql.append(" FROM WM_TASK ");
			sql.append(" WHERE STATUS = ? AND (to_date(to_char(?, 'yyyy-mm-dd HH24:MI:SS'),'yyyy-mm-dd HH24:MI:SS') - ");
			sql.append(" to_date(to_char(last_run_time, 'yyyy-mm-dd HH24:MI:SS'),'yyyy-mm-dd HH24:MI:SS')) *24*60*60 > ? ");
			
			String dSql = String.valueOf(sql);
			logger.debug("Sql=" + dSql);
			logger.debug("ServiceApplicationDate.getTimestamp() : " + ServiceApplicationDate.getTimestamp());
			int index = 1;
			ps = conn.prepareStatement(dSql);
			ps.setString(index++, MConstant.TASK_STATUS.RUNNING);
			ps.setTimestamp(index++, ServiceApplicationDate.getTimestamp());
			ps.setString(index++, MAX_TASK_RUN_TIME);
			rs = ps.executeQuery();
			
			while(rs.next()){
				WmTask wmTask = new WmTask();
				wmTask.setTaskId(rs.getString("TASK_ID"));
				wmTask.setServerName(rs.getString("NAME"));
				wmTask.setWmFunc(rs.getString("TASK"));
				wmTask.setRefId(rs.getString("REF_ID"));
				wmTask.setRefCode(rs.getString("REF_CODE"));
				wmTask.setTaskData(rs.getString("TASK_DATA"));
				wmTask.setErrorCount(rs.getInt("ERROR_COUNT"));
				wmTask.setErrorTotal(rs.getInt("ERROR_TOTAL"));
				wmTask.setStatus(rs.getString("STATUS"));
				
				tasks.add(wmTask);
			}
			
			
		}catch (Exception e) {
			logger.fatal(e.getLocalizedMessage());
			throw new Exception(e.getMessage());
		} finally {
			try {
				closeConnection(conn, rs, ps);
			} catch (Exception e) {
				logger.fatal(e.getLocalizedMessage());
				throw new Exception(e.getMessage());
			}
		}
		
		return tasks;
	}

	@Override
	public void updateNextDayRunTime(String taskId) throws Exception {
		Connection conn = null;	
		PreparedStatement ps = null;
		String WORK_MANAGER_START_TIME = SystemConfig.getGeneralParam("WORK_MANAGER_START_TIME");
		
		String startWorkTime = WORK_MANAGER_START_TIME + ":00";
		try{
			conn = getConnection();					
			StringBuilder sql = new StringBuilder("");			
			sql.append(" UPDATE WM_TASK ");
			sql.append(" SET NEXT_RUN_TIME = to_date(to_char(? + 1,'YYYY-MM-DD \"" +startWorkTime+ "\"'),'YYYY-MM-DD HH24:MI:SS')  ");
			sql.append(" WHERE TASK_ID = ? ");
			
			String dSql = String.valueOf(sql);
			logger.debug("Sql=" + dSql);
			int index = 1;
			ps = conn.prepareStatement(dSql);
			ps.setTimestamp(index++, ServiceApplicationDate.getTimestamp());
			ps.setString(index++, taskId);
			ps.executeUpdate();
			
		}catch (Exception e) {
			logger.fatal(e.getLocalizedMessage());
			throw new Exception(e.getMessage());
		} finally {
			try {
				closeConnection(conn, ps);
			} catch (Exception e) {
				logger.fatal(e.getLocalizedMessage());
				throw new Exception(e.getMessage());
			}
		}
		
	}

	@Override
	public boolean createCardlinkTask(String applicationNo) throws Exception {
		String WM_ACTION_CARDLINK_RESULT = SystemConstant.getConstant("WM_ACTION_CARDLINK_RESULT");
		boolean result = false;
		WmTask wmTask = haveTask(applicationNo, WM_ACTION_CARDLINK_RESULT);
		
		if(!Util.empty(wmTask.getTaskId())){
			logger.debug("found task for " + applicationNo + " with taskId " + wmTask.getTaskId());
			if(!Util.empty(wmTask.getStatus()) && MConstant.TASK_STATUS.COMPLETE.equals(wmTask.getStatus())){
				logger.debug("task status complete then rerun this task");
				//update status to null for rerun
				wmTask.setStatus("");
				
				WmTaskHistory taskHistory = new WmTaskHistory();
				taskHistory.setTaskId(wmTask.getTaskId());
				taskHistory.setWmFunc(wmTask.getWmFunc());
				taskHistory.setRefId(wmTask.getRefId());
				taskHistory.setRefCode(wmTask.getRefCode());
				if(null != wmTask.getData() && !Util.empty(wmTask.getData().getTaskData())){
					taskHistory.setTaskData(wmTask.getData().getTaskData());
				}

				//update task
				saveTaskManager(wmTask, taskHistory);
				result = true;
			}
		}
		else{
			logger.debug("not found task for " + applicationNo + " create new task");
			//prepare new task data
			String taskId = GenerateUnique.generate(MConstant.WM_TASK_ID_PK_SEQUENCE);
			WmTask task = new WmTask();
			task.setWmFunc(WM_ACTION_CARDLINK_RESULT);
			task.setRefCode(applicationNo);
			task.setTaskId(taskId);
			
			WmTaskData taskData = new WmTaskData();
			taskData.setTaskData(applicationNo);
			task.setData(taskData);
			
			WmTaskHistory taskHistory = new WmTaskHistory();
			taskHistory.setTaskId(task.getTaskId());
			taskHistory.setWmFunc(task.getWmFunc());
			taskHistory.setRefId(task.getRefId());
			taskHistory.setRefCode(task.getRefCode());
			if(null != task.getData() && !Util.empty(task.getData().getTaskData())){
				taskHistory.setTaskData(task.getData().getTaskData());
			}

			//create new task
			saveTaskManager(task, taskHistory);
			result = true;
		}
		return result;
	}

	private WmTask haveTask(String applicationNo, String task) throws Exception {
		Connection conn = null;	
		PreparedStatement ps = null;
		ResultSet rs = null;
		WmTask wmTask = new WmTask();
		try{			
			conn = getConnection();					
			StringBuilder sql = new StringBuilder("");			
			sql.append(" SELECT TASK_ID, NAME, TASK, REF_ID, REF_CODE, TASK_DATA, ERROR_COUNT, ERROR_TOTAL, STATUS ");
			sql.append(" FROM WM_TASK ");
			sql.append(" WHERE REF_CODE = ? AND TASK = ? ");
			
			String dSql = String.valueOf(sql);
			logger.debug("Sql=" + dSql);
			int index = 1;
			ps = conn.prepareStatement(dSql);
			ps.setString(index++, applicationNo);
			ps.setString(index++, task);
			rs = ps.executeQuery();
			
			if(rs.next()){
				wmTask.setTaskId(rs.getString("TASK_ID"));
				wmTask.setServerName(rs.getString("NAME"));
				wmTask.setWmFunc(rs.getString("TASK"));
				wmTask.setRefId(rs.getString("REF_ID"));
				wmTask.setRefCode(rs.getString("REF_CODE"));
				wmTask.setTaskData(rs.getString("TASK_DATA"));
				wmTask.setErrorCount(rs.getInt("ERROR_COUNT"));
				wmTask.setErrorTotal(rs.getInt("ERROR_TOTAL"));
				wmTask.setStatus(rs.getString("STATUS"));
			}
			
		}catch (Exception e) {
			logger.fatal(e.getLocalizedMessage());
			throw new Exception(e.getMessage());
		} finally {
			try {
				closeConnection(conn, rs, ps);
			} catch (Exception e) {
				logger.fatal(e.getLocalizedMessage());
				throw new Exception(e.getMessage());
			}
		}
		return wmTask;
	}

	@Override
	public List<WmTask> getTaskErrors() throws Exception {
		Connection conn = null;	
		PreparedStatement ps = null;
		ResultSet rs = null;
		List<WmTask> tasks = new ArrayList<WmTask>();
		try{			
			conn = getConnection();					
			StringBuilder sql = new StringBuilder("");			
			sql.append(" SELECT TASK_ID, TASK, REF_ID, REF_CODE, CREATE_TIME, LAST_RUN_TIME, ERROR_TOTAL ");
			sql.append(" FROM WM_TASK ");
			sql.append(" WHERE STATUS NOT IN (?, ?) AND ERROR_TOTAL > 0 ");
			
			String dSql = String.valueOf(sql);
			logger.debug("Sql=" + dSql);
			int index = 1;
			ps = conn.prepareStatement(dSql);
			ps.setString(index++, MConstant.TASK_STATUS.RUNNING);
			ps.setString(index++, MConstant.TASK_STATUS.COMPLETE);
			rs = ps.executeQuery();
			
			while(rs.next()){
				WmTask wmTask = new WmTask();
				wmTask.setTaskId(rs.getString("TASK_ID"));
				wmTask.setWmFunc(rs.getString("TASK"));
				wmTask.setRefId(rs.getString("REF_ID"));
				wmTask.setRefCode(rs.getString("REF_CODE"));
				wmTask.setCreateTime(rs.getTimestamp("CREATE_TIME"));
				wmTask.setLastRuntime(rs.getTimestamp("LAST_RUN_TIME"));
				wmTask.setErrorTotal(rs.getInt("ERROR_TOTAL"));
				tasks.add( wmTask );
			}
		}catch (Exception e) {
			logger.fatal(e.getLocalizedMessage());
			throw new Exception(e.getMessage());
		} finally {
			try {
				closeConnection(conn, ps);
			} catch (Exception e) {
				logger.fatal(e.getLocalizedMessage());
				throw new Exception(e.getMessage());
			}
		}
		return tasks;
	}
	
	private boolean isCompleteTask(String applicationNo, String task) throws Exception {
		Connection conn = null;	
		PreparedStatement ps = null;
		ResultSet rs = null;
		boolean result = false;
		try{			
			conn = getConnection();					
			StringBuilder sql = new StringBuilder("");			
			sql.append(" SELECT 1 ");
			sql.append(" FROM WM_TASK ");
			sql.append(" WHERE REF_CODE = ? AND TASK = ? ");
			
			String dSql = String.valueOf(sql);
			logger.debug("Sql=" + dSql);
			int index = 1;
			ps = conn.prepareStatement(dSql);
			ps.setString(index++, applicationNo);
			ps.setString(index++, task);
			rs = ps.executeQuery();
			
			if(rs.next()){
				result = true;
			}
			
			
		}catch (Exception e) {
			logger.fatal(e.getLocalizedMessage());
			throw new Exception(e.getMessage());
		} finally {
			try {
				closeConnection(conn, rs, ps);
			} catch (Exception e) {
				logger.fatal(e.getLocalizedMessage());
				throw new Exception(e.getMessage());
			}
		}
		return result;
	}
}
