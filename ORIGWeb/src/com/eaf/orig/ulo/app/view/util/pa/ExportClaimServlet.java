package com.eaf.orig.ulo.app.view.util.pa;

import java.io.BufferedOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.util.Date;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.HashMap;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import com.eaf.core.ulo.common.engine.SearchFormHandler;
import com.eaf.core.ulo.common.engine.SearchQueryEngine;
import com.eaf.orig.profile.model.UserDetailM;

@SuppressWarnings("serial")
@WebServlet("/exportClaim")
public class ExportClaimServlet extends HttpServlet
{
	private static transient Logger logger = Logger.getLogger(ExportClaimServlet.class);
	
	@Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response) 
			throws ServletException, IOException 
	{
		UserDetailM userM = (UserDetailM) request.getSession().getAttribute("ORIGUser");
		String userName = userM.getUserName();
		Object sfhObj = request.getSession().getAttribute("SearchForm");
		String claimType = request.getParameter("claimType");
		String contextPath = getServletContext().getRealPath("/");
		String errMesg = "";
		if(sfhObj != null)
		{
			SearchFormHandler SearchForm = (SearchFormHandler) sfhObj;
			if(SearchForm != null && SearchForm.getCount() > 0)
			{
				//Search again with all results index max 1,000,000 record.
				SearchForm.setAtPage(1);
				SearchForm.setItemsPerPage(1000000);
				try
				{
					SearchQueryEngine SearchEngine = new SearchQueryEngine();
					SearchEngine.search(SearchForm);
				}
				catch(Exception e)
				{
					logger.fatal("ERROR ",e);
					errMesg = e.getMessage();
				}	
				ArrayList<HashMap<String, Object>> results = SearchForm.getResults();
				//logger.debug("results = " + results);
				try
				{
					XSSFWorkbook wb = PAUtil.exportClaimResults(contextPath,results,claimType,userName);
					
					if(wb != null)
					{
						logger.debug("wb != null - write wb to OutputStream");
						 
						String YYYYMMDD = new SimpleDateFormat("yyyyMMdd").format(new Date());
						response.setContentType("application/vnd.openxmlformats-officedocument.spreadsheetml.sheet") ;
						response.setHeader("Content-Disposition", "attachment;filename=FLP_CliamJob_Result_" + YYYYMMDD + ".xlsx");
					
						OutputStream out = new BufferedOutputStream(response.getOutputStream());
						wb.write(out);
						wb.close();
						response.setContentLength((int)(new CountingOutputStream(out)).getCount());
						out.flush();
					}
					return;
				}
				catch(Exception e)
				{
					errMesg = e.getMessage();
				}
				
			}
			else
			{
				errMesg = "null results";
			}
		}
		else{errMesg = "sfhObj is null";}
		
		PrintWriter out = response.getWriter();  
		response.setContentType("text/html");  
		out.println("<script type=\"text/javascript\">");  
		out.println("alert('Fail to export Excel - " + errMesg + "');");  
		out.println("</script>");
		out.flush();
	}
}