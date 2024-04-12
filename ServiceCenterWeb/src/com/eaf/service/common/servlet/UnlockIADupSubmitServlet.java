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
import com.eaf.orig.ulo.app.dao.exception.ApplicationException;
import com.eaf.service.common.utils.ResponseUtils;

@WebServlet("/UnlockIADupSubmit")
public class UnlockIADupSubmitServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private static transient Logger logger = Logger.getLogger(UnlockIADupSubmitServlet.class);
	
	@Override
	protected void service(HttpServletRequest req, HttpServletResponse resp)throws ServletException, IOException {
		logger.debug("UnlockIADupSubmit Servlet");
		HashMap<String,Object> data = new HashMap<String,Object>();		
		
		String mode = req.getParameter("mode");
		
    	try
    	{
    		if("search".equals(mode))
    		{
	    		HashMap<Integer,HashMap<String,String>> submitIAHMS = getOrigSubmitIATimestamp();
	    		StringBuilder tableBuilder = new StringBuilder();
	    		if(submitIAHMS.size() > 0)
	    		{
	    			tableBuilder.append("<tr><th></th><th>APPLICATION_GROUP_NO</th><th>IDNO</th><th>SUBMIT_DATE</th></tr>");
		    		
		    		for(int i = 0 ; i < submitIAHMS.size() ; i++)
		    		{
		    			HashMap<String,String> submitIAHM = submitIAHMS.get(i);
		    			String appGroupNo = submitIAHM.get("APPLICATION_GROUP_NO");
		    			String idNo = submitIAHM.get("IDNO");
		    			String submitDate = submitIAHM.get("SUBMIT_DATE");
		    			String checkBox = "<input type='checkbox' name='ENABLE_FLAG_" + (i+1) + "' >";
		    			tableBuilder.append("<tr>");
			    		tableBuilder.append("<td>" + checkBox + "</td>");
			    		tableBuilder.append("<td>" + appGroupNo + "</td>");
			    		tableBuilder.append("<td name='idNo_" + (i+1) + "' >" + idNo + "</td>");
			    		tableBuilder.append("<td>" + submitDate + "</td>");
			    		tableBuilder.append("</tr>");
		    		}
	    		}
	    		data.put("jsonRs", tableBuilder.toString());
    		}
    		else if("unlock".equals(mode))
    		{
    			String updateResponse = "";
    			String idNo = req.getParameter("idNo");
    			
    			logger.debug("Unlock IA submit for IdNo : " + idNo);
    			updateResponse = deleteOldSubmitIATimestamp(idNo);
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
	
	private static HashMap<Integer,HashMap<String,String>> getOrigSubmitIATimestamp()
	{
		OrigObjectDAO orig = new OrigObjectDAO();
		PreparedStatement ps = null;
		ResultSet rs = null;
		Connection conn = null;	
		HashMap<Integer,HashMap<String,String>> submitIAHMS = new HashMap<>();
		
		try
		{
			conn = orig.getConnection();
			StringBuilder sql = new StringBuilder("");
			sql.append("SELECT * FROM ORIG_SUBMIT_IA_TIMESTAMP ");
			ps = conn.prepareStatement(sql.toString());
			rs = ps.executeQuery();
			int index = 0;
			while(rs.next())
			{
				HashMap<String,String> submitIAHM = new HashMap<>();
				submitIAHM.put("APPLICATION_GROUP_NO", FormatUtil.display(rs.getString("APPLICATION_GROUP_NO")));
				submitIAHM.put("IDNO", FormatUtil.display(rs.getString("IDNO")));
				submitIAHM.put("SUBMIT_DATE", FormatUtil.display(rs.getString("SUBMIT_DATE")));
				submitIAHMS.put(index, submitIAHM);
				index++;
			}
		}
		catch(Exception e)
		{
			logger.fatal("ERROR ",e);
		}
		
		return submitIAHMS;
	}
	
	private String deleteOldSubmitIATimestamp(String idNo) throws ApplicationException {
		PreparedStatement ps = null;
		Connection conn = null;
		try {
			OrigObjectDAO origObjectDAO = new OrigObjectDAO();
			conn = origObjectDAO.getConnection();
			StringBuilder sql = new StringBuilder("");
			sql.append(" DELETE FROM ORIG_SUBMIT_IA_TIMESTAMP ");
			sql.append(" WHERE IDNO = ? ");
			String dSql = sql.toString();
			logger.debug("Sql=" + dSql);
			ps = conn.prepareStatement(dSql);
			ps.setString(1, idNo);
			ps.executeUpdate();
			
			return idNo + " Unlocked. ";
		} catch (Exception e) {
			logger.fatal(e.getLocalizedMessage());
			throw new ApplicationException(e.getMessage());
		} finally {
			try {
				if(ps != null){ps.close();}
				if(conn != null){conn.close();}
			} catch (Exception e) {
				logger.fatal(e.getLocalizedMessage());
				throw new ApplicationException(e.getMessage());
			}
		}
	}
}
