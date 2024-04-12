package com.eaf.service.common.servlet;

import java.io.IOException;
import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.HashMap;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang3.exception.ExceptionUtils;
import org.apache.log4j.Logger;

import com.eaf.core.ulo.common.display.FormatUtil;
import com.eaf.core.ulo.common.properties.SystemConstant;
import com.eaf.core.ulo.common.util.Util;
import com.eaf.orig.shared.dao.OrigObjectDAO;
import com.eaf.orig.ulo.app.dao.exception.ApplicationException;
import com.eaf.service.common.utils.ResponseUtils;

@WebServlet("/PatchSetupDate")
public class PatchSetupDateServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private static transient Logger logger = Logger.getLogger(PatchSetupDateServlet.class);

	@Override
	protected void service(HttpServletRequest req, HttpServletResponse resp)throws ServletException, IOException {
		logger.debug("PatchSetupDate Servlet");
		HashMap<String,Object> data = new HashMap<String,Object>();		
		
		String mode = req.getParameter("mode");
		
    	try
    	{
    		if("search".equals(mode))
    		{
    			String appGroupNo = req.getParameter("appGroupNo");
    			logger.debug("appGroupNo:" + appGroupNo);
    			
	    		HashMap<Integer,HashMap<String,String>> ASDDatas = getAppSetupDateData(appGroupNo);
	    		StringBuilder tableBuilder = new StringBuilder();
	    		logger.debug("ASDDatas.size(): " + ASDDatas.size());
	    		if(ASDDatas.size() > 0)
	    		{
	    			tableBuilder.append("<tr></tr>");
	    			tableBuilder.append("<th>APPLICATION_GROUP_NO</th>");
	    			tableBuilder.append("<th>APPLICATION_RECORD_ID</th>");
	    			tableBuilder.append("<th>ORG_ID</th>");
	    			tableBuilder.append("<th>LAST_DECISION_DATE</th>");
	    			tableBuilder.append("<th>APPLICATION_STATUS</th>");
	    			tableBuilder.append("<th>SETUP_DATE(dd/MM/yyyy HH:mm:ss) </th>");
	    			tableBuilder.append("</tr>");
	    			
		    		for(int i = 0 ; i < ASDDatas.size() ; i++)
		    		{
		    			HashMap<String,String> ASDData = ASDDatas.get(i);
		    			tableBuilder.append("<tr>");
			    		tableBuilder.append("<td>" + ASDData.get("APPLICATION_GROUP_NO") + "</td>");
			    		tableBuilder.append("<td name='appRecordId_" + (i+1) + "' >" + ASDData.get("APPLICATION_RECORD_ID") + "</td>");
			    		tableBuilder.append("<td name='orgId_" + (i+1) + "' >" + ASDData.get("ORG_ID") + "</td>");
			    		tableBuilder.append("<td >" + ASDData.get("LAST_DECISION_DATE") + "</td>");
			    		tableBuilder.append("<td>" + ASDData.get("APPLICATION_STATUS") + "</td>");
			    		tableBuilder.append("<td><input name='setupDate_" + (i+1) + "' value='" + ASDData.get("SETUP_DATE") + "'></input></td>");
			    		tableBuilder.append("</tr>");
		    		}
	    		}
	    		data.put("jsonRs", tableBuilder.toString());
    		}
    		else if("patch".equals(mode))
    		{
    			String patchResponse = "";
    			String orgId = req.getParameter("orgId");
    			String appRecordId = req.getParameter("appRecordId");
    			String setupDate = req.getParameter("setupDate");
    			
    			logger.debug("setupDate: " + setupDate);
    			
    			Timestamp newSetupDate = null;
    			if(!"null".equals(setupDate))
    			{
    				SimpleDateFormat format = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
        			java.util.Date date = format.parse(setupDate);
        			newSetupDate = new Timestamp(date.getTime());
        			logger.debug("setupDate parsed: " + newSetupDate);
    			}
    			
    			patchResponse = patchSetupDateData(orgId, appRecordId, newSetupDate);
    			logger.debug("Patch setup date for appRecordId : " + appRecordId + " Done.");
    			
    			data.put("jsonRs", patchResponse);
    		}
    		
    		ResponseUtils.sendJsonResponse(resp, data);
    	}
    	catch(Exception e)
    	{
    		logger.fatal("ERROR ",e);
    		resp.sendError(400, ExceptionUtils.getStackTrace(e));
    	}
	}
	
	private static HashMap<Integer,HashMap<String,String>> getAppSetupDateData(String applicationGroupNo) throws Exception
	{
		OrigObjectDAO orig = new OrigObjectDAO();
		PreparedStatement ps = null;
		ResultSet rs = null;
		Connection conn = null;	
		HashMap<Integer,HashMap<String,String>> ASDDatas = new HashMap<>();
		
		try
		{
			conn = orig.getConnection();
			StringBuilder sql = new StringBuilder("");
			sql.append("SELECT AG.APPLICATION_GROUP_NO, AP.APPLICATION_RECORD_ID, ");
			sql.append(" BUS.ORG_ID, AG.LAST_DECISION_DATE, AG.APPLICATION_STATUS, ");
			sql.append(" CASE WHEN BUS.ORG_ID = 'KPL' THEN LOAN.ACCOUNT_OPEN_DATE ELSE AP.BOOKING_DATE END AS SETUP_DATE ");
			sql.append(" FROM ORIG_APPLICATION_GROUP AG ");
			sql.append(" JOIN ORIG_APPLICATION AP ON AP.APPLICATION_GROUP_ID = AG.APPLICATION_GROUP_ID ");
			sql.append(" JOIN BUSINESS_CLASS BUS ON BUS.BUS_CLASS_ID = AP.BUSINESS_CLASS_ID ");
			sql.append(" JOIN ORIG_LOAN LOAN ON LOAN.APPLICATION_RECORD_ID = AP.APPLICATION_RECORD_ID ");
			sql.append(" WHERE 1=1 ");
			sql.append(" AND AG.APPLICATION_GROUP_NO = ? ");
			ps = conn.prepareStatement(sql.toString());
			ps.setString(1, applicationGroupNo);
			rs = ps.executeQuery();
			int index = 0;
			while(rs.next())
			{
				HashMap<String,String> ASDData = new HashMap<>();
				ASDData.put("APPLICATION_GROUP_NO", FormatUtil.display(rs.getString("APPLICATION_GROUP_NO")));
				ASDData.put("APPLICATION_RECORD_ID", FormatUtil.display(rs.getString("APPLICATION_RECORD_ID")));
				ASDData.put("ORG_ID", FormatUtil.display(rs.getString("ORG_ID")));
				ASDData.put("LAST_DECISION_DATE", FormatUtil.display(rs.getString("LAST_DECISION_DATE")));
				ASDData.put("APPLICATION_STATUS", FormatUtil.display(rs.getString("APPLICATION_STATUS")));				
				ASDData.put("SETUP_DATE", (Util.empty(rs.getTimestamp("SETUP_DATE"))) ? "null" : FormatUtil.display(rs.getTimestamp("SETUP_DATE"), FormatUtil.EN, "YYYY-MM-DD HH:mm:ss"));
				ASDDatas.put(index, ASDData);
				index++;
			}
		}
		catch(Exception e)
		{
			logger.fatal("ERROR ",e);
		}
		finally {
			try {
				if(rs != null){rs.close();}
				if(ps != null){ps.close();}
				if(conn != null){conn.close();}
			} catch (Exception e) {
				logger.fatal(e.getLocalizedMessage());
				throw new Exception(e.getMessage());
			}
		}
		
		return ASDDatas;
	}
	
	private String patchSetupDateData(String orgId, String appRecordId, Timestamp newSetupDate) throws Exception {
		String PRODUCT_K_PERSONAL_LOAN = SystemConstant.getConstant("PRODUCT_K_PERSONAL_LOAN");
		OrigObjectDAO orig = new OrigObjectDAO();
		PreparedStatement ps = null;
		Connection conn = null;
		try 
		{
			conn = orig.getConnection();
			StringBuilder sql = new StringBuilder("");
			if(PRODUCT_K_PERSONAL_LOAN.equals(orgId))
			{
				sql.append(" UPDATE ORIG_LOAN SET ACCOUNT_OPEN_DATE = ? ");
			}
			else
			{
				sql.append(" UPDATE ORIG_APPLICATION SET BOOKING_DATE = ? ");
			}
			sql.append(" WHERE APPLICATION_RECORD_ID = ? ");
			String dSql = sql.toString();
			logger.debug("Sql=" + dSql);
			ps = conn.prepareStatement(dSql);
			ps.setTimestamp(1, newSetupDate);
			ps.setString(2, appRecordId);
			ps.executeUpdate();
			
			return appRecordId + " Setup Date Updated.";
		} catch (Exception e) {
			logger.fatal(e.getLocalizedMessage());
			throw new Exception(e.getMessage());
		} finally {
			try {
				if(ps != null){ps.close();}
				if(conn != null){conn.close();}
			} catch (Exception e) {
				logger.fatal(e.getLocalizedMessage());
				throw new Exception(e.getMessage());
			}
		}
	}
}
