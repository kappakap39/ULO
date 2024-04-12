package com.eaf.orig.ulo.app.view.util.pa;

import java.io.BufferedOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
import org.apache.pdfbox.pdmodel.PDDocument;

@WebServlet("/getStamp")
public class StampServlet extends HttpServlet
{
	private static Logger logger = Logger.getLogger(StampServlet.class);
	@Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response) 
			throws ServletException, IOException {
		logger.debug("doGet - StampServlet");
		String appGroupNo = request.getParameter("appGroupNo");
		String contextPath = getServletContext().getRealPath("/");
		PDDocument document = StampDutyUtil.loadPDF(contextPath, appGroupNo);
		if(document != null)
		{
			response.setContentType("application/pdf") ;
			response.setHeader("Content-Disposition", "inline ");
		
			OutputStream out = new BufferedOutputStream(response.getOutputStream());
			document.save(out);
			document.close();
			response.setContentLength((int)(new CountingOutputStream(out)).getCount());
		}
		else
		{
			PrintWriter out = response.getWriter();  
			response.setContentType("text/html");  
			out.println("<script type=\"text/javascript\">");  
			out.println("alert('Fail to load PDF');");  
			out.println("</script>");
		}
	}
}
