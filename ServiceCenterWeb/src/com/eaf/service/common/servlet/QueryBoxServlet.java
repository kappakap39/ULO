package com.eaf.service.common.servlet;

import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.sql.Types;
import java.util.ArrayList;
import java.util.HashMap;

import javax.naming.InitialContext;
import javax.rmi.PortableRemoteObject;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.sql.DataSource;

import org.apache.log4j.Logger;

import com.eaf.core.ulo.common.util.Util;
import com.eaf.service.common.qrinfo.QRInfoUtil;
import com.eaf.service.common.utils.ResponseUtils;

@WebServlet("/QueryBox")
public class QueryBoxServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private static transient Logger logger = Logger.getLogger(QueryBoxServlet.class);
	
	@Override
	protected void service(HttpServletRequest req, HttpServletResponse resp)throws ServletException, IOException {
		logger.debug("QueryBox Servlet");
		Connection conn = null;
		HashMap<String, Object> data = new HashMap<String,Object>();	
		String resultsTable = "";
		
    	try{
	    		String database = req.getParameter("database");
	    		String query = req.getParameter("query");
	    		String maxRow = req.getParameter("maxRow");
	    		String mode = req.getParameter("mode");
	    				
	    		data.put("jsonRq", "");
	    		
	    		if(!Util.empty(query) && query.trim().toUpperCase().startsWith("SELECT"))
	    		{
		    		logger.debug("database = " + database);
		    		
		    		InitialContext ctx = new InitialContext();
		    		Object obj = ctx.lookup(database);
		    		DataSource dataSrc = (DataSource)PortableRemoteObject.narrow(obj, DataSource.class);
		    		conn = dataSrc.getConnection();
		    		
		    		resultsTable = selectDatabase(query, maxRow, conn);
	    		}
	    		else
	    		{
	    			resultsTable = "<div class='alert alert-danger'>[ERROR] Only simple SELECT statement is allowed. </div>";
	    		}
	    		
    	}catch(Exception e){
    		logger.fatal("ERROR ",e);
    		resultsTable = e.getMessage();
    	}
    	
    	data.put("jsonRs",resultsTable.toString());
		ResponseUtils.sendJsonResponse(resp, data);	
	}
	
	private static String selectDatabase(String queryString, String maxRow, Connection conn) throws Exception
	{
		PreparedStatement ps = null;
		ResultSet rs = null;
		int row = 0;
		String errorDiv = "";
		StringBuilder resultsTable = new StringBuilder();
		resultsTable.append("<table class='table table-bordered'>");
		try
		{
			logger.debug("sql = " + queryString);
			ps = conn.prepareStatement(queryString);
			rs = ps.executeQuery();
			
			try
			{ps.setMaxRows(Integer.parseInt(maxRow));}
			catch(Exception e){}

			ResultSetMetaData md = rs.getMetaData();
			int columns = md.getColumnCount();
			ArrayList<String> columnList = new ArrayList<String>();
			ArrayList<Integer> columnTypeList = new ArrayList<Integer>();
			
			for(int i=1 ; i<=columns ; i++)
			{
				resultsTable.append("<th>" + md.getColumnName(i) + "</th>");
				columnList.add(md.getColumnName(i));
				columnTypeList.add(md.getColumnType(i));
			}
			
			while(rs.next())
			{
				resultsTable.append("<tr>");
				for(int colIndex=0 ; colIndex<columnList.size() ; colIndex++)
				{
					if(Types.CLOB == columnTypeList.get(colIndex))
					{
						String id = "col" + String.valueOf(row) + String.valueOf(colIndex);
						resultsTable.append("<td><a id='" + id + "' onclick='displayDiv(this);'>Clob</a><div id='div" + id + "' style='display: none;'><pre>"+QRInfoUtil.prettyJSON(rs.getString(columnList.get(colIndex)))+"</pre></div></td>");
					}
					else
					{
						resultsTable.append("<td>"+rs.getString(columnList.get(colIndex))+"</td>");
					}
				}
				resultsTable.append("</tr>");
				row++;
			}
			
			if(row == 0)
			{
				resultsTable.append("<tr><td colspan='100%'>No results found.</td></tr>");
			}
			
			resultsTable.append("</table>");
		}
		catch(Exception e)
		{
			logger.fatal("ERROR ",e);
			errorDiv += "<div class='alert alert-danger'>[ERROR] " + e.getMessage() + "</div>";
		}
		finally
		{
			if(rs != null)
			{
				rs.close();
			}
			if(ps != null)
			{
				ps.close();
			}
			if(conn != null)
			{
				conn.close();
			}
		}
		
		String divResultsCount = (row == 0) ? "" : "<div class='alert alert-success'> Results count : " + row + "</div>";
		
		return  divResultsCount + errorDiv + resultsTable.toString();
	}
}
