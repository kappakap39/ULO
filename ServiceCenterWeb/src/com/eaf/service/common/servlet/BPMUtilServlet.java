package com.eaf.service.common.servlet;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Properties;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.exception.ExceptionUtils;
import org.apache.log4j.Logger;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import bpm.rest.client.BPMClientImpl;
import com.bpm.rest.client.GenericClient;
import bpm.rest.client.authentication.AuthenticationTokenHandler;
import bpm.rest.client.authentication.was.WASAuthenticationTokenHandler;

import com.ava.bpm.common.api.BPMCacheControl;
import com.eaf.core.ulo.common.properties.SystemConfig;
import com.eaf.core.ulo.common.util.Util;
import com.eaf.core.ulo.flp.util.FLPPasswordUtil;
import com.eaf.orig.shared.dao.OrigObjectDAO;
import com.eaf.service.common.qrinfo.QRInfoUtil;
import com.orig.bpm.workflow.util.JSONUtil;

import org.springframework.http.HttpMethod;

@WebServlet("/BPMUtil")
public class BPMUtilServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private static transient Logger logger = Logger.getLogger(BPMUtilServlet.class);
	private static String BPM_HOST = SystemConfig.getProperty("BPM_HOST");
	private static String BPM_PORT = SystemConfig.getProperty("BPM_PORT");
	private static String BPM_USER_ID = SystemConfig.getProperty("BPM_USER_ID");
	private static String BPM_PASSWORD = SystemConfig.getProperty("BPM_PASSWORD");
	
	@Override
	protected void service(HttpServletRequest req, HttpServletResponse resp)throws ServletException, IOException {
		logger.debug("BPMUtil Servlet");		
		
		String mode = req.getParameter("mode");
		String appGrpNo = req.getParameter("appGrpNo");
		logger.debug("mode : " + mode);
		logger.debug("appGrpNo : " + appGrpNo);
		
		String jsonString = "";
    	try
    	{
    		if(!Util.empty(mode))
    		{
	    		//Find INSTANT_ID from appGrpNo
	    		long instantId = getInstantIdByQRNo(appGrpNo);
	    		AuthenticationTokenHandler handler = new WASAuthenticationTokenHandler(BPM_USER_ID, FLPPasswordUtil.decrypt(BPM_PASSWORD));
	    		boolean useSSL = Boolean.valueOf(BPMCacheControl.getProperty("BPM_USE_SSL"));
	    		GenericClient client = createClient(BPM_HOST, Integer.valueOf(Integer.parseInt(BPM_PORT)), handler, useSSL);
	    		
	    		if(instantId > -1)
	    		{
	    			if("process".equals(mode))
		    		{
		    			String[] parts = { "data" };
	    				Map<String, Object> arguments = new HashMap<String, Object>();
	    				arguments.put("parts", StringUtils.join(parts, ','));
	    				JSONObject jsonData = client.executeRESTCall("/process/" + instantId, arguments, HttpMethod.GET, false);
	    				
	    				JSONObject jsonObj = JSONUtil.getJson(jsonData, "data");
	    				if(jsonObj != null)
	    				{
	    					jsonString = "<pre>" + QRInfoUtil.prettyJSON(jsonObj.toString()) + "</pre>";
	    				}
	    				
	    				resp.setCharacterEncoding("UTF-8");
	    	    		resp.setContentType("text/html");
	    	    		PrintWriter pw = resp.getWriter();
	    	    		pw.print(jsonString);
	    	    		pw.flush();
		    		}
	    			else if("task".equals(mode))
		    		{
	    				String getTaskData = req.getParameter("getTaskData");
	    				
	    				String[] columns = { "instanceId", "bpdName", 
	    				//"instanceCreateDate", "instanceDueDate", "instanceStatus", "instanceProcessApp", "assignedToUser", "assignedToRole", 
	    				"taskStatus", "taskSubject", "taskDueDate", "taskPriority", "taskReceivedDate", "taskActivityName", "taskClosedDate", "taskClosedBy" };
	    				
	    				List<String> conList = new ArrayList<String>();
	    			    conList.add("instanceId|" + (int)instantId);
	    			    String[] conditions = (String[])conList.toArray(new String[conList.size()]);
	    				
	    			    Map<String, Object> criteria = new HashMap<String, Object>();
	    			    criteria.put("filterByCurrentUser", Boolean.valueOf(false));
	    			    
	    				Map<String, Object> arguments = new HashMap<String, Object>();
	    				arguments.put("columns", StringUtils.join(columns, ','));
	    				arguments.put("condition", conditions);
	    				arguments.put("sort", "taskReceivedDate");
	    				arguments.put("secondSort", "taskDueDate");
	    				arguments.put("organization", "byTask");
	    				arguments.putAll(criteria);
	    				
	    				JSONObject jsonData = client.executeRESTCall("/search/query", arguments, HttpMethod.PUT, false);
		    			logger.debug("jsonData : " + jsonData.toString());
		    			
		    			JSONArray jsonArray = JSONUtil.getArray(jsonData, "data.data");
		    			if(jsonArray != null)
	    				{jsonString = jsonArray.toString();}
		    			
		    			StringBuilder tableBuilder = new StringBuilder();
		    			SimpleDateFormat sdfT = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss'Z'");
		    			SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
		    			tableBuilder.append("<tr>");
		    			tableBuilder.append("<th>taskId</th><th>taskActivityName</th><th>taskReceivedDate</th><th>taskDueDate</th><th>taskAssignedTo</th><th>taskStatus</th>");
		    			tableBuilder.append("<th>taskClosedDate</th><th>taskClosedBy</th>");
		    			if(!Util.empty(getTaskData))
		    			{tableBuilder.append("<th>taskData</th>");}
		    			tableBuilder.append("</tr>");
		    			
		    			//Sort jsonArray by taskId
		    			List<JSONObject> list = new ArrayList<JSONObject>();
		    			for(int i = 0; i < jsonArray.length(); i++)
		    			{
		    				list.add(jsonArray.getJSONObject(i));
		    			}
		    			Collections.sort(list, new Comparator<JSONObject>()
		    			{
		    				@Override
		    			    public int compare(JSONObject objA, JSONObject objB) 
		    				{
		    					try
		    			        {
		    			            int valA = Integer.valueOf(objA.getString("taskId"));
		    			            int valB = Integer.valueOf(objB.getString("taskId"));
		    			            return Integer.compare(valA, valB);
		    			        }
		    					catch(JSONException e)
		    			        {
		    			        	e.printStackTrace();
		    			        }
		    					return 0;
		    				}
		    			});
		    			
		    			for(int i=0 ; i < list.size(); i++) 
		    			{
		    				JSONObject jsonTemp = list.get(i);
		    				String taskId = JSONUtil.getString(jsonTemp, "taskId");
		    				tableBuilder.append("<tr>");
		    				tableBuilder.append("<td>" + taskId + "</td>");
		    				tableBuilder.append("<td>" + JSONUtil.getString(jsonTemp, "taskActivityName") + "</td>");
		    				tableBuilder.append("<td>" + (!Util.empty(JSONUtil.getString(jsonTemp, "taskReceivedDate")) ? sdf.format(sdfT.parse(JSONUtil.getString(jsonTemp, "taskReceivedDate"))) : "-") + "</td>");
		    				tableBuilder.append("<td>" + (!Util.empty(JSONUtil.getString(jsonTemp, "taskDueDate")) ? sdf.format(sdfT.parse(JSONUtil.getString(jsonTemp, "taskDueDate"))) : "-") + "</td>");
		    				tableBuilder.append("<td>" + JSONUtil.getString(jsonTemp, "taskAssignedTo.who") + "</td>");
		    				tableBuilder.append("<td>" + JSONUtil.getString(jsonTemp, "taskStatus") + "</td>");
		    				tableBuilder.append("<td>" + (!Util.empty(JSONUtil.getString(jsonTemp, "taskClosedDate")) ? sdf.format(sdfT.parse(JSONUtil.getString(jsonTemp, "taskClosedDate"))) : "-") + "</td>");
		    				tableBuilder.append("<td>" + JSONUtil.getString(jsonTemp, "taskClosedBy") + "</td>");
		    				
		    				//Get Task Data
		    				if(!Util.empty(getTaskData))
		    				{
			    				String taskData = "";
			    				try
			    				{
			    					Map<String, Object> taskArguments = new HashMap<String, Object>();
			    					taskArguments.put("action", "getData");
			    					JSONObject jsonTaskData = client.executeRESTCall("/task/" + taskId, taskArguments, HttpMethod.GET, false);
			    					if(jsonTaskData != null)
			    					{
			    						taskData = "<a id=\"Task_" + taskId + "\" onclick=\"displayDiv(this);\"> Data </a><div id=\"divTask_" + taskId + "\" style=\"display: none;\"><pre>" + QRInfoUtil.prettyJSON(JSONUtil.getString(jsonTaskData, "data")) + "</pre></div>";
			    					}
			    				}
			    				catch(Exception e)
			    				{
			    					taskData = "Fail to get taskData : " + e.getLocalizedMessage();
			    				}
			    				tableBuilder.append("<td>" + taskData + "</td>");
		    				}
		    				
		    				tableBuilder.append("</tr>");
		    			}
		    			
		    			resp.setCharacterEncoding("UTF-8");
		        		resp.setContentType("text/html");
		        		PrintWriter pw = resp.getWriter();
		        		pw.print(tableBuilder.toString());
		        		pw.flush();
		    		}
	    		}
	    		else
	    		{
	    			throw new Exception("Instance not found.");
	    		}
    		}
    		else 
    		{
    			throw new Exception("Invalid mode.");
    		}
    	}
    	catch(Exception e)
    	{
    		logger.fatal("ERROR ",e);
    		resp.sendError(400, ExceptionUtils.getStackTrace(e));
    	}
	}
	
	private static GenericClient createClient(String hostname, Integer port, AuthenticationTokenHandler handler, Boolean useSSL)
	throws Exception
	{
		GenericClient client = null;
		Properties props = new Properties();

		props.load(BPMClientImpl.class.getResourceAsStream("bpm-client.properties"));
		String URI = props.getProperty("uri", "/rest/bpm/wle/v1");
		logger.debug("URI >> " + URI);
		int readTimeOut = Integer.valueOf(props.getProperty("readTimeOut", "300000")).intValue();
		int connectionTimeOut = Integer.valueOf(props.getProperty("connectionTimeOut", "8000")).intValue();
	      
		boolean finalUseSSL = (useSSL == null ? Boolean.valueOf(BPMCacheControl.getProperty("BPM_USE_SSL")) : useSSL).booleanValue();
		logger.debug("useSSL >> " + finalUseSSL);
		client = new GenericClient(hostname, URI, port.intValue(), handler, finalUseSSL, readTimeOut, connectionTimeOut);
	    
		return client;
	}
	
	public static long getInstantIdByQRNo(String QRNo)
	{
		PreparedStatement ps = null;
		ResultSet rs = null;
		Connection conn = null;	
		
		OrigObjectDAO orig = new OrigObjectDAO();
		try
		{
			conn = orig.getConnection();
			StringBuilder sql = new StringBuilder("");
			sql.append(" SELECT INSTANT_ID FROM ORIG_APPLICATION_GROUP ");
			sql.append(" WHERE APPLICATION_GROUP_NO = ? ");
			sql.append(" UNION ALL ");
			sql.append(" SELECT INSTANT_ID FROM MLP_ORIG_APPLICATION_GROUP ");
			sql.append(" WHERE APPLICATION_GROUP_NO = ? ");
			
			String dSql = String.valueOf(sql);
			logger.debug("dSql = " + dSql);
			ps = conn.prepareStatement(dSql);
			ps.setString(1, QRNo);
			ps.setString(2, QRNo);
			
			rs = ps.executeQuery();
			
			if(rs.next())
			{
				return rs.getLong("INSTANT_ID");
			}
		}
		catch(Exception e)
		{
			logger.fatal("ERROR ",e);
		}
		finally 
		{
			try{orig.closeConnection(conn, rs, ps);}
			catch(Exception e) 
			{logger.fatal(e.getLocalizedMessage());}
		}
		return -1;
	}

}
