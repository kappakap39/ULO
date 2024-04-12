package com.eaf.orig.ulo.control.servlet;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.net.URLEncoder;
import java.util.HashMap;

import javax.servlet.ServletException;
import javax.servlet.annotation.MultipartConfig;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;

import com.eaf.core.ulo.common.file.DownloadControl;
import com.eaf.core.ulo.common.util.Util;
import com.eaf.j2ee.pattern.util.JSONUtil;
import com.eaf.orig.ulo.constant.MConstant;
import com.eaf.orig.ulo.model.download.DownLoadM;
import com.eaf.orig.ulo.service.process.response.model.ProcessResponse;
import com.eaf.service.rest.model.ServiceResponse;

@WebServlet("/DownloadServlet")
@MultipartConfig
public class DownloadServlet extends HttpServlet  implements javax.servlet.Servlet  {
	private static final long serialVersionUID = 1L;
	private static transient Logger logger = Logger.getLogger(DownloadServlet.class);	
    public DownloadServlet() {
        super();
    }
    private static HashMap<String,String> importControl = null;
    public synchronized void create(){
    	importControl = new HashMap<String, String>();
    	importControl.put(MConstant.DOWNLOAD.DOWNLOAD_WITHDRAW_AUTHORITY_EXCEL,"com.eaf.orig.ulo.control.download.DownloadWithdrawAuthority");
    }
	protected void service(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String DOWNLOAD_ID = request.getParameter("DOWNLOAD_ID");
		logger.debug(">> DOWNLOAD_ID>>"+ DOWNLOAD_ID);
    	try{
    		if(null == importControl){
    			importControl = new HashMap<String, String>();
    			create();
    		}
    		String className = importControl.get(DOWNLOAD_ID);
    		logger.debug("className >> "+className);
    		if(!Util.empty(className)){
    			DownloadControl control = null; 		        			 
    		    try{			        		    	
    		    	control = (DownloadControl)Class.forName(className).newInstance();			        		    	
    			} catch (Exception e) {
    				logger.fatal("ERROR ",e);
    			}	
    		    if(null != control){
    		    	if(control.requiredEvent()){
    		    		control.processEvent(request,response);
    		    	}else{
		    		    ProcessResponse processResponse= control.processDownload(request);    	
		    		    if(ServiceResponse.Status.SUCCESS.equals(processResponse.getStatusCode())){
		    		    	JSONUtil json = new JSONUtil();	
			    		    DownLoadM download = json.toJavaModel(processResponse.getStatusCode(), DownLoadM.class);   
			    		    response.setDateHeader("Last-Modified", System.currentTimeMillis());
							response.setDateHeader("Expires", 0);
							response.setHeader("Content-disposition", "attachment;filename="+URLEncoder.encode(download.getFileName(),"UTF-8"));
							response.setContentType(download.getMimeType());				 
							String downloadFilePath = download.getFilePath()+download.getFileName();
							logger.debug("downloadFilePath >> " + downloadFilePath);						
							File downloadFile = new File(downloadFilePath);
							boolean existsFile = downloadFile.exists();
							logger.debug("existsFile >> "+existsFile);
							if(existsFile){
								OutputStream output = response.getOutputStream();
								FileInputStream input = new FileInputStream(downloadFile);
				                try{
				                	 int bytes;
				                     while ((bytes = input.read()) != -1) {
				                         output.write(bytes);
				                     }
				                }catch(Exception e){
				                  logger.fatal("ERROR",e);
				                }finally{
				                    if (output != null) output.close();
				                    if (input != null) input.close();
				                }
							}  
		    		    }
    		    	}
    		    }
    		}
    	}catch(Exception e){
    		logger.fatal("ERROR ",e);
    	}
	}
}
