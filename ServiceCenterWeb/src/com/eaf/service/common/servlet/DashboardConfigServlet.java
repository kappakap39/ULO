package com.eaf.service.common.servlet;

import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.HashMap;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang3.exception.ExceptionUtils;
import org.apache.log4j.Logger;

import com.eaf.core.ulo.common.display.FormatUtil;
import com.eaf.orig.shared.dao.OrigObjectDAO;
import com.eaf.orig.ulo.constant.MConstant;
import com.eaf.service.common.utils.ResponseUtils;

@WebServlet("/DashboardConfig")
public class DashboardConfigServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private static transient Logger logger = Logger.getLogger(DashboardConfigServlet.class);
	
	@Override
	protected void service(HttpServletRequest req, HttpServletResponse resp)throws ServletException, IOException {
		logger.debug("DashboardConfig Servlet");
		HashMap<String,Object> data = new HashMap<String,Object>();		
		
		String userName = (req.getSession().getAttribute("userName") == null) ? "" : req.getSession().getAttribute("userName").toString();
		String mode = req.getParameter("mode");
    	try
    	{
    		if("search".equals(mode))
    		{
	    		HashMap<Integer,HashMap<String,String>> dhbConfHMS = getDashboardConfig();
	    		StringBuilder tableBuilder = new StringBuilder();
	    		if(dhbConfHMS.size() > 0)
	    		{
	    			tableBuilder.append("<tr><th>ENABLE_FLAG</th><th>DHB_TYPE</th><th>INTERVAL</th>" +
		    				"<th>RESET_INTERVAL</th><th>NUM_DAY</th><th>STATUS</th><th>START_DATE</th><th>END_DATE</th>" +
		    				"<th>USED_TIME</th><th>UPDATE_DATE</th><th>UPDATE_BY</th></tr>");
		    		
		    		for(int i = 0 ; i < dhbConfHMS.size() ; i++)
		    		{
		    			HashMap<String,String> dhbConf = dhbConfHMS.get(i);
		    			String dhbType = dhbConf.get("DHB_TYPE");
		    			String enableFlag = dhbConf.get("ENABLE_FLAG");
		    			String checkBox = "<input type='checkbox' name='ENABLE_FLAG_" + dhbType + "' " 
		    			+ ((MConstant.FLAG_Y.equals(enableFlag)) ? "checked" : "")  + " >";
		    			String textBoxInterval = "<input type='number' name='INTERVAL_" + dhbType 
		    			+ "' value='" + dhbConf.get("INTERVAL") + "' />";
		    			String textBoxResetInterval = "<input type='number' name='RESET_INTERVAL_" + dhbType 
		    	    	+ "' value='" + dhbConf.get("RESET_INTERVAL") + "' />";
		    			tableBuilder.append("<tr>");
			    		tableBuilder.append("<td>" + checkBox + "</td>");
			    		tableBuilder.append("<td>" + dhbType + "</td>");
			    		tableBuilder.append("<td>" + textBoxInterval + "</td>");
			    		tableBuilder.append("<td>" + textBoxResetInterval + "</td>");
			    		tableBuilder.append("<td>" + dhbConf.get("NUM_DAY") + "</td>");
			    		tableBuilder.append("<td>" + dhbConf.get("STATUS") + "</td>");
			    		tableBuilder.append("<td>" + dhbConf.get("START_DATE") + "</td>");
			    		tableBuilder.append("<td>" + dhbConf.get("END_DATE") + "</td>");
			    		tableBuilder.append("<td>" + dhbConf.get("USED_TIME") + "</td>");
			    		tableBuilder.append("<td>" + dhbConf.get("UPDATE_DATE") + "</td>");
			    		tableBuilder.append("<td>" + dhbConf.get("UPDATE_BY") + "</td>");
			    		tableBuilder.append("</tr>");
		    		}
	    		}
	    		data.put("jsonRs", tableBuilder.toString());
    		}
    		else if("save".equals(mode))
    		{
    			String updateResponse = "";
    			String dhbType = req.getParameter("dhbType");
    			String enableFlag = req.getParameter("enableFlag");
    			String interval = req.getParameter("interval");
    			String resetInterval = req.getParameter("resetInterval");
    			logger.debug("Save dhbConfig type [" + dhbType 
    					+ "] enableFlag = " + enableFlag
    					+ " ,interval = " + interval
    					+ " ,resetInterval = " + resetInterval);
    			updateResponse = updateDashboardConfig(dhbType, enableFlag, interval, resetInterval, userName);
    			data.put("jsonRs", updateResponse);
    		}
    		
    		ResponseUtils.sendJsonResponse(resp, data);
    	}
    	catch(Exception e)
    	{
    		logger.fatal("ERROR ",e);
    		resp.sendError(400, ExceptionUtils.getStackTrace(e));
    	}
	}
	
	private static HashMap<Integer,HashMap<String,String>> getDashboardConfig()
	{
		OrigObjectDAO orig = new OrigObjectDAO();
		PreparedStatement ps = null;
		ResultSet rs = null;
		Connection conn = null;	
		HashMap<Integer,HashMap<String,String>> dhbConfHMS = new HashMap<>();
		
		try
		{
			conn = orig.getConnection();
			StringBuilder sql = new StringBuilder("");
			sql.append("SELECT * FROM DHB_CONFIG ");
			ps = conn.prepareStatement(sql.toString());
			rs = ps.executeQuery();
			int index = 0;
			while(rs.next())
			{
				HashMap<String,String> dhbConfHM = new HashMap<>();
				dhbConfHM.put("DHB_TYPE", FormatUtil.display(rs.getString("DHB_TYPE")));
				dhbConfHM.put("INTERVAL", FormatUtil.display(rs.getString("INTERVAL")));
				dhbConfHM.put("RESET_INTERVAL", FormatUtil.display(rs.getString("RESET_INTERVAL")));
				dhbConfHM.put("NUM_DAY", FormatUtil.display(rs.getString("NUM_DAY")));
				dhbConfHM.put("STATUS", FormatUtil.display(rs.getString("STATUS")));
				dhbConfHM.put("START_DATE", FormatUtil.display(rs.getString("START_DATE")));
				dhbConfHM.put("END_DATE", FormatUtil.display(rs.getString("END_DATE")));
				dhbConfHM.put("USED_TIME", FormatUtil.display(rs.getString("USED_TIME")));
				dhbConfHM.put("CREATE_DATE", FormatUtil.display(rs.getString("CREATE_DATE")));
				dhbConfHM.put("CREATE_BY", FormatUtil.display(rs.getString("CREATE_BY")));
				dhbConfHM.put("UPDATE_DATE", FormatUtil.display(rs.getString("UPDATE_DATE")));
				dhbConfHM.put("UPDATE_BY", FormatUtil.display(rs.getString("UPDATE_BY")));
				dhbConfHM.put("ENABLE_FLAG", FormatUtil.display(rs.getString("ENABLE_FLAG")));
				dhbConfHMS.put(index, dhbConfHM);
				index++;
			}
		}
		catch(Exception e)
		{
			logger.fatal("ERROR ",e);
		}
		
		return dhbConfHMS;
	}
	
	private static String updateDashboardConfig(String dhbType, String enableFlag,
	String interval, String resetInterval, String userName)
	{
		PreparedStatement ps = null;
		Connection conn = null;	
		
		OrigObjectDAO orig = new OrigObjectDAO();
		
		try
		{
			conn = orig.getConnection();
			StringBuilder sql = new StringBuilder("");
			sql.append("UPDATE DHB_CONFIG SET ENABLE_FLAG = ? ");
			sql.append(" , INTERVAL = ? , RESET_INTERVAL = ? ");
			sql.append(" , UPDATE_DATE = SYSDATE , UPDATE_BY = ?  "); 
			sql.append(" WHERE DHB_TYPE = ? ");
			
			int cnt=1;
			String dSql = String.valueOf(sql);
			ps = conn.prepareStatement(dSql);
			ps.setString(cnt++, enableFlag);
			ps.setInt(cnt++, Integer.parseInt(interval));
			ps.setInt(cnt++, Integer.parseInt(resetInterval));
			ps.setString(cnt++, userName);
			ps.setString(cnt++, dhbType);
			
			ps.executeUpdate();
			return "dhbType : " + dhbType + " [Updated] \n";
		}
		catch(Exception e)
		{
			logger.fatal("ERROR ",e);
			return "dhbType : " + dhbType + " [ERROR] " + e.getMessage() + "\n";
		}
	}
}
