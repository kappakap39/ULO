package com.eaf.service.common.servlet;

import java.io.DataOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.StringEscapeUtils;
import org.apache.commons.lang3.exception.ExceptionUtils;
import org.apache.log4j.Logger;

import com.eaf.core.ulo.common.properties.SystemConfig;
import com.eaf.core.ulo.common.util.Util;
import com.eaf.service.common.utils.ResponseUtils;

@WebServlet("/LogBrowser")
public class LogBrowserServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private static transient Logger logger = Logger.getLogger(LogBrowserServlet.class);
	private static String LOG_BROWSER_URL_01 = SystemConfig.getProperty("LOG_BROWSER_URL_01");
	private static String LOG_BROWSER_URL_02 = SystemConfig.getProperty("LOG_BROWSER_URL_02");
	
	@Override
	protected void service(HttpServletRequest req, HttpServletResponse resp)throws ServletException, IOException {
		logger.debug("LogBrowser Servlet");
		HashMap<String,Object> data = new HashMap<String,Object>();			
		
    	try{
    			String serverIndex = req.getParameter("serverIndex");
    			if(!Util.empty(serverIndex))
    			{
    				String urlStr = "";
    				
    				if("01".equals(serverIndex))
    				{
    					urlStr = LOG_BROWSER_URL_01;
    				}
    				else if("02".equals(serverIndex))
    	    		{
    	    			urlStr = LOG_BROWSER_URL_02;		
    	    		}
    				
    				logger.debug("urlStr = " + urlStr);
    				
    				String postParams = "mode="+req.getParameter("mode")
    	    				+ "&logType="+req.getParameter("logType");
    	    		if(req.getParameter("filePath") != null)
    	    		{
    	    			postParams += "&filePath="+URLEncoder.encode(req.getParameter("filePath"), "UTF-8");
    	    		}
    	    		
    				byte[] postData = postParams.getBytes("UTF-8");
    				int postDataLength = postData.length;

    				URL url = new URL(urlStr);
    				HttpURLConnection conn = (HttpURLConnection)url.openConnection();
    				conn.setRequestMethod("POST");
    				conn.setRequestProperty("Content-Type", "application/x-www-form-urlencoded"); 
    				conn.setRequestProperty("charset", "utf-8");
    				conn.setRequestProperty("Content-Length", Integer.toString(postDataLength));
    				conn.setUseCaches(false);
    				conn.setDoOutput(true);
    				
    				Cookie[] cookies = req.getCookies();
    				if(cookies != null)
		    		{
    					for(Cookie cookie : cookies)
    					{
    						if("JSESSIONID".equals(cookie.getName()))
    						{
    							//logger.debug("JSESSIONID="+cookie.getValue());
    							conn.setRequestProperty("Cookie", "JSESSIONID="+cookie.getValue());
    						}
    					}
		    		}
    				
    				DataOutputStream wr = new DataOutputStream(conn.getOutputStream());
    				wr.write(postData);
    				wr.flush();
    				
    				InputStream remoteResponse = conn.getInputStream();
    				
    				Map<String, List<String>> map = conn.getHeaderFields();
    				//Construct remote HttpHeader and set it to local response
    				for (Map.Entry<String, List<String>> entry : map.entrySet())
    				{
    					String key = "";
    					String valueString = "";
    				    if (entry.getKey() == null) 
    				    {
    				    	continue;
    				    }
    				    else
    				    {
    				    	key = entry.getKey();
    				    	List<String> values = entry.getValue();
    				    	if(values != null && values.size() > 0)
    				    	{
	    				    	for(String value : values)
	    				    	{
	    				    		valueString += value + ", ";
	    				    	}
	    				    	if(!Util.empty(valueString))
	    				    	{
	    				    		valueString = valueString.substring(0, valueString.length() - 2);
	    				    	}
    				    	}
    				    	resp.setHeader(key, valueString);
    				    }
    				}
    				
    				OutputStream localResponder = resp.getOutputStream();
    				int c;
    				while((c = remoteResponse.read()) != -1)
    				{
    					localResponder.write(c);
    				}
    				wr.close();
    				remoteResponse.close();
    				localResponder.flush();
    				localResponder.close();
    				conn.disconnect();
    			}
    			else
    			{
		    		String mode = req.getParameter("mode");
		    		String logType = req.getParameter("logType");
		    		String filePath = req.getParameter("filePath");
		    		
		    		data.put("jsonRq", "");
		    		logger.debug("mode = " + mode);
		    		logger.debug("logType = " + logType);
		    		logger.debug("filePath = " + filePath);
		    		
		    		if("browse".equals(mode))
		    		{
		    			StringBuilder logDirectoryTree = new StringBuilder();
		    			
		    			File f = null;
		    			File[] files;
		    			
		    			logDirectoryTree.append("<div class='treeview'>");
		    			logDirectoryTree.append("<hr>");
		    			  
		    			if("Batch".equals(logType))
		    			{
		    				f = new File("/flp/batch/log");
		    			}
		    			else if("WebServer".equals(logType))
		    			{
		    				f = new File("/WAS_log");
		    			}
		    			else if("WebArchive".equals(logType))
		    			{
		    				f = new File("/WAS_archive");
		    			}
		    			else
		    			{
		    				throw new Exception("Invalid logType : " + logType);
		    			}
		    		    	 
		    			files = f.listFiles();
		    			for(File file:files) 
		    			{
		    				buildDirectory(logDirectoryTree, file);
			    		   	logDirectoryTree.append("<br>\n");
		    			}
		    			
		    			logDirectoryTree.append("</div>");
		    			data.put("responseData", logDirectoryTree.toString());
						ResponseUtils.sendJsonResponse(resp, data);		
			    	}
		    		else if("viewFile".equals(mode) || "downloadFile".equals(mode))
		    		{
		    			File file = new File(filePath);
		    			logger.debug("File :" + file.getAbsolutePath());
		    			resp.setHeader("fileName", file.getName());
		    			resp.setHeader("fileSize", String.valueOf(file.length()));
		    			
		    			if("downloadFile".equals(mode))
		    			{
		    				ServletContext context = getServletContext();
		    				String mimeType = context.getMimeType(filePath);
		    				logger.debug("mimeType = " + mimeType);
		    				if (mimeType == null) 
		    				{mimeType = "application/octet-stream";}
		    				resp.setContentType(mimeType);
			    			resp.setHeader("fileType", mimeType);
		    			}
		    			else
		    			{
		    				resp.setContentType("text/plain");
		    			}
		    			
		    			OutputStream out = resp.getOutputStream();
						FileInputStream input = new FileInputStream(file);
						
						byte[] b = new byte[8192];
						int bytes;
		                while ((bytes = input.read(b)) != -1) 
		                {
		                	out.write(b, 0, bytes);
		                }
		                
		                logger.debug("Flush file data to reponse.");
		                out.flush();
		                out.close();
		                input.close();
		    		}
		    		else
		    		{
		    			throw new Exception("Invalid mode : " + mode);
		    		}
    			}
    	}catch(Exception e)
    	{
    		logger.fatal("ERROR ",e);
    		resp.sendError(400, ExceptionUtils.getStackTrace(e));
    	}
	}
	
	public static String getFullURL(HttpServletRequest request) {
	    StringBuilder requestURL = new StringBuilder(request.getRequestURL().toString());
	    String queryString = request.getQueryString();

	    if (queryString == null) {
	        return requestURL.toString();
	    } else {
	        return requestURL.append('?').append(queryString).toString();
	    }
	}
	
	public static void buildDirectory(StringBuilder logDirectoryTree, File file)
	{
		if(file.isDirectory())
   	 	{
			logger.debug("directory " + file.getName());
			logDirectoryTree.append("<li style='list-style: none;'><span class='list-group-item active directory'><i class='glyphicon glyphicon-folder-close'></i>" + file.getName() + "</span>\n");
			logDirectoryTree.append("<ul class='list-group nested'>\n");
				for(File f:file.listFiles()) 
		 		{
		   			 buildDirectory(logDirectoryTree, f);
		 		}
	   		 logDirectoryTree.append("</ul>\n");
	   		 logDirectoryTree.append("</li>\n");
   	 	}
   	 	else
   	 	{
   	 		String filePathJS = StringEscapeUtils.escapeJavaScript(file.getAbsolutePath());
   	 		logDirectoryTree.append("<li class='list-group-item'><i class='glyphicon glyphicon-file'></i>" + file.getName());
   	 		logDirectoryTree.append("<button type='button' class='btn btn-default btn-sm' onclick=\"viewFile('" + filePathJS +"');\">");
   	 		logDirectoryTree.append("<span class='glyphicon glyphicon-zoom-in'></span> View </button>");
   	 		logDirectoryTree.append("<button type='button' class='btn btn-default btn-sm' onclick=\"downloadFile('" + filePathJS +"');\">");
   	 		logDirectoryTree.append("<span class='glyphicon glyphicon-save'></span> Download </button>");
   	 		logDirectoryTree.append("&emsp; File size: " + file.length());
   	 		logDirectoryTree.append("</li>");
   	 	}
	}
}
