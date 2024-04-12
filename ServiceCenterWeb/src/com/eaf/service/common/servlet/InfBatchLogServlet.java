package com.eaf.service.common.servlet;

import java.io.IOException;
import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.text.SimpleDateFormat;
import java.util.HashMap;
import java.util.Map.Entry;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
import org.json.JSONObject;

import com.eaf.core.ulo.common.display.FormatUtil;
import com.eaf.core.ulo.common.properties.SystemConfig;
import com.eaf.core.ulo.common.properties.SystemConstant;
import com.eaf.core.ulo.common.util.Util;
import com.eaf.orig.shared.dao.OrigObjectDAO;
import com.eaf.service.common.utils.ResponseUtils;
import com.google.gson.Gson;

@WebServlet("/InfBatchLog")
public class InfBatchLogServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private static transient Logger logger = Logger.getLogger(InfBatchLogServlet.class);

	@Override
	protected void service(HttpServletRequest req, HttpServletResponse resp)throws ServletException, IOException {
		logger.debug("InfBatchLog Servlet");
    	String process = req.getParameter("process");
    	String SYSTEM_USER = SystemConstant.getConstant("SYSTEM_USER");
    	
    	try{
	    		String mode = req.getParameter("mode");
	    		String interfaceCode = req.getParameter("interfaceCode");
	    		String appGroupId = req.getParameter("appGroupId");
	    		String appRecordId = req.getParameter("appRecordId");
	    		String refId = req.getParameter("refId");
	    		String infStatus = req.getParameter("infStatus");
	    		String createDateFromStr = req.getParameter("createDateFrom");
	    		String createDateToStr = req.getParameter("createDateTo");
	    		SimpleDateFormat sdf = new SimpleDateFormat("MM/dd/yyyy");
	    		Date createDateFrom = Util.empty(createDateFromStr) ? null : new Date(sdf.parse(createDateFromStr).getTime());
	    		Date createDateTo = Util.empty(createDateToStr) ? null : new Date(sdf.parse(createDateToStr).getTime());
	    		String interfaceLogId = req.getParameter("interfaceLogId");
	    		
	    		logger.debug("createDateFrom = " + createDateFromStr);
	    		logger.debug("createDateTo = " + createDateToStr);

	    		HashMap<String,Object> data = new HashMap<String,Object>();			
				Gson gson = new Gson();
				data.put("jsonRq", "");
	    		
				logger.debug("mode : " + mode);
	    		if("search".equals(mode))
	    		{
		    		HashMap<Integer,HashMap<String,String>> iblHMS = getInfBatchLogData(interfaceCode, appGroupId, appRecordId, refId, infStatus, createDateFrom, createDateTo);
		    		StringBuilder tableBuilder = new StringBuilder();
		    		if(iblHMS.size() > 0)
		    		{
		    			tableBuilder.append("<tr><th></th><th>INTERFACE_LOG_ID</th><th>APPLICATION_GROUP_ID</th>" +
			    				"<th>APPLICATION_RECORD_ID</th><th>INTERFACE_CODE</th><th>CREATE_DATE</th><th>INTERFACE_STATUS</th><th>REF_ID</th>" +
			    				"<th>SYSTEM_01</th><th>SYSTEM_02</th><th>SYSTEM_03</th><th>SYSTEM_04</th><th>SYSTEM_05</th><th>SYSTEM_06</th>" +
			    				"<th>SYSTEM_07</th><th>SYSTEM_08</th><th>SYSTEM_09</th><th>SYSTEM_10</th><th>LOG_MESSAGE</th></tr>");
			    		
			    		for(int i = 0 ; i < iblHMS.size() ; i++)
			    		{
			    			HashMap<String,String> iblHM = iblHMS.get(i);
			    			String infLogId = iblHM.get("INTERFACE_LOG_ID");
			    			String interfaceStatus = "<input type='text' name='INTERFACE_STATUS_" + infLogId + "' value='" + iblHM.get("INTERFACE_STATUS") + "' >";
			    			String checkBox = "<input type='checkbox' name='CHECK_BOX_" + infLogId + "' >";
			    			tableBuilder.append("<tr>");
				    		tableBuilder.append("<td>" + checkBox + "</td>");
				    		tableBuilder.append("<td>" + infLogId + "</td>");
				    		tableBuilder.append("<td>" + iblHM.get("APPLICATION_GROUP_ID") + "</td>");
				    		tableBuilder.append("<td>" + iblHM.get("APPLICATION_RECORD_ID") + "</td>");
				    		tableBuilder.append("<td name='INTERFACE_CODE_" + infLogId + "' >" + iblHM.get("INTERFACE_CODE") + "</td>");
				    		tableBuilder.append("<td>" + iblHM.get("CREATE_DATE") + "</td>");
				    		tableBuilder.append("<td>" + interfaceStatus + "</td>");
				    		tableBuilder.append("<td>" + iblHM.get("REF_ID") + "</td>");
				    		tableBuilder.append("<td>" + iblHM.get("SYSTEM01") + "</td>");
				    		tableBuilder.append("<td>" + iblHM.get("SYSTEM02") + "</td>");
				    		tableBuilder.append("<td>" + iblHM.get("SYSTEM03") + "</td>");
				    		tableBuilder.append("<td>" + iblHM.get("SYSTEM04") + "</td>");
				    		tableBuilder.append("<td>" + iblHM.get("SYSTEM05") + "</td>");
				    		tableBuilder.append("<td>" + iblHM.get("SYSTEM06") + "</td>");
				    		tableBuilder.append("<td>" + iblHM.get("SYSTEM07") + "</td>");
				    		tableBuilder.append("<td>" + iblHM.get("SYSTEM08") + "</td>");
				    		tableBuilder.append("<td>" + iblHM.get("SYSTEM09") + "</td>");
				    		tableBuilder.append("<td>" + iblHM.get("SYSTEM10") + "</td>");
				    		tableBuilder.append("<td>" + iblHM.get("LOG_MESSAGE") + "</td>");
				    		tableBuilder.append("</tr>");
			    		}
		    		}
		    		//logger.debug("tableBuilder = " + tableBuilder);
					data.put("jsonRs", tableBuilder.toString());
	    		}
	    		else if("save".equals(mode))
	    		{
	    			String interfaceStatus = req.getParameter("interfaceStatus");
	    			logger.debug("Update InfBatchLog - " + interfaceLogId + " set status to " + interfaceStatus);
	    			String updateInterfaceStatus = updateInterfaceStatus(interfaceLogId, interfaceStatus);
	    			data.put("jsonRs", updateInterfaceStatus);
	    		}
	    		else
	    		{
	    			data.put("jsonRs", "Invalid Mode");
	    		}
				
				ResponseUtils.sendJsonResponse(resp, data);			
	    	
    	}catch(Exception e){
    		logger.fatal("ERROR ",e);
    	}
	}
	private static String updateInterfaceStatus(String interfaceLogId, String interfaceStatus)
	{
		PreparedStatement ps = null;
		Connection conn = null;	
		
		OrigObjectDAO orig = new OrigObjectDAO();
		
		try
		{
			conn = orig.getConnection();
			StringBuilder sql = new StringBuilder("");
			sql.append("UPDATE INF_BATCH_LOG SET INTERFACE_STATUS = ? ");
			sql.append(" WHERE INTERFACE_LOG_ID = ?");
			
			int cnt=1;
			String dSql = String.valueOf(sql);
			ps = conn.prepareStatement(dSql);
			ps.setString(cnt++, interfaceStatus);
			ps.setString(cnt++, interfaceLogId);
			
			ps.executeUpdate();
			return "interfaceLogId : " + interfaceLogId + " [OK] \n";
		}
		catch(Exception e)
		{
			logger.fatal("ERROR ",e);
			return "interfaceLogId : " + interfaceLogId + " [ERROR] " + e.getMessage() + "\n";
		}
	}
	
	private static HashMap<Integer,HashMap<String,String>> getInfBatchLogData(String interfaceCode, String appGroupId, String appRecordId, String refId,
			String infStatus, Date createDateFrom, Date createDateTo)
	{
		OrigObjectDAO orig = new OrigObjectDAO();
		PreparedStatement ps = null;
		ResultSet rs = null;
		Connection conn = null;	
		HashMap<Integer,HashMap<String,String>> iblHMS = new HashMap<>();
		
		try
		{
			conn = orig.getConnection();
			StringBuilder sql = new StringBuilder("");
			sql.append("SELECT * FROM INF_BATCH_LOG WHERE 1=1 ");
			if(!Util.empty(interfaceCode))
			{
				sql.append(" AND INTERFACE_CODE = ? ");
			}
			if(!Util.empty(appGroupId))
			{
				sql.append(" AND APPLICATION_GROUP_ID = ? ");
			}
			if(!Util.empty(appRecordId))
			{
				sql.append(" AND APPLICATION_RECORD_ID = ? ");
			}
			if(!Util.empty(refId))
			{
				sql.append(" AND REF_ID = ? ");
			}
			if(!Util.empty(infStatus))
			{
				sql.append(" AND INTERFACE_STATUS = ? ");
			}
			if(!Util.empty(createDateFrom) && !Util.empty(createDateTo))
			{
				sql.append(" AND CREATE_DATE BETWEEN TRUNC( ? ) AND TRUNC( ? )+0.99999 ");
			}
			if(!Util.empty(createDateFrom) && Util.empty(createDateTo))
			{
				sql.append(" AND CREATE_DATE BETWEEN TRUNC( ? ) AND TRUNC( ? )+0.99999 ");
			}
			logger.debug("sql = " + sql);
			ps = conn.prepareStatement(sql.toString());
			int cnt = 1;
			if(!Util.empty(interfaceCode))
			{ps.setString(cnt++, interfaceCode);}
			if(!Util.empty(appGroupId))
			{ps.setString(cnt++, appGroupId);}
			if(!Util.empty(appRecordId))
			{ps.setString(cnt++, appRecordId);}
			if(!Util.empty(refId))
			{ps.setString(cnt++, refId);}
			if(!Util.empty(infStatus))
			{ps.setString(cnt++, infStatus);}
			if(!Util.empty(createDateFrom) && !Util.empty(createDateTo))
			{
				ps.setDate(cnt++, createDateFrom);
				ps.setDate(cnt++, createDateTo);
			}
			if(!Util.empty(createDateFrom) && Util.empty(createDateTo))
			{
				ps.setDate(cnt++, createDateFrom);
				ps.setDate(cnt++, createDateFrom);
			}
			
			rs = ps.executeQuery();
			int index = 0;
			while(rs.next())
			{
				HashMap<String,String> iblHM = new HashMap<>();
				iblHM.put("INTERFACE_LOG_ID", FormatUtil.display(rs.getString("INTERFACE_LOG_ID")));
				iblHM.put("APPLICATION_GROUP_ID", FormatUtil.display(rs.getString("APPLICATION_GROUP_ID")));
				iblHM.put("APPLICATION_RECORD_ID", FormatUtil.display(rs.getString("APPLICATION_RECORD_ID")));
				iblHM.put("CREATE_DATE", FormatUtil.display(rs.getString("CREATE_DATE")));
				iblHM.put("INTERFACE_CODE", FormatUtil.display(rs.getString("INTERFACE_CODE")));
				iblHM.put("INTERFACE_STATUS", FormatUtil.display(rs.getString("INTERFACE_STATUS")));
				iblHM.put("REF_ID", FormatUtil.display(rs.getString("REF_ID")));
				iblHM.put("SYSTEM01", FormatUtil.display(rs.getString("SYSTEM01")));
				iblHM.put("SYSTEM02", FormatUtil.display(rs.getString("SYSTEM02")));
				iblHM.put("SYSTEM03", FormatUtil.display(rs.getString("SYSTEM03")));
				iblHM.put("SYSTEM04", FormatUtil.display(rs.getString("SYSTEM04")));
				iblHM.put("SYSTEM05", FormatUtil.display(rs.getString("SYSTEM05")));
				iblHM.put("SYSTEM06", FormatUtil.display(rs.getString("SYSTEM06")));
				iblHM.put("SYSTEM07", FormatUtil.display(rs.getString("SYSTEM07")));
				iblHM.put("SYSTEM08", FormatUtil.display(rs.getString("SYSTEM08")));
				iblHM.put("SYSTEM09", FormatUtil.display(rs.getString("SYSTEM09")));
				iblHM.put("SYSTEM10", FormatUtil.display(rs.getString("SYSTEM10")));
				iblHM.put("LOG_MESSAGE", FormatUtil.display(rs.getString("LOG_MESSAGE")));
				iblHMS.put(index, iblHM);
				index++;
			}
		}
		catch(Exception e)
		{
			logger.fatal("ERROR ",e);
		}
		
		return iblHMS;
	}
}
