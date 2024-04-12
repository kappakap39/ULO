package com.eaf.orig.ulo.control.servlet;

import java.io.IOException;
import java.util.Collection;
import java.util.HashMap;

import javax.servlet.ServletException;
import javax.servlet.annotation.MultipartConfig;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.Part;

import org.apache.log4j.Logger;

import com.eaf.core.ulo.common.file.FileUtil;
import com.eaf.core.ulo.common.file.ImportControl;
import com.eaf.core.ulo.common.util.Util;
import com.eaf.j2ee.pattern.control.ResponseDataController;
import com.eaf.orig.ulo.constant.MConstant;
import com.eaf.orig.ulo.service.process.response.model.ProcessResponse;

@WebServlet("/UploadServlet")
@MultipartConfig
public class UploadServlet extends HttpServlet  implements javax.servlet.Servlet  {
	private static final long serialVersionUID = 1L;
	private static transient Logger logger = Logger.getLogger(UploadServlet.class);	
    public UploadServlet() {
        super();
    }
    private static HashMap<String,String> importControl = null;
    public synchronized void create(){
    	importControl = new HashMap<String, String>();
    	importControl.put(MConstant.IMPORT.IMPORT_WITHDRAW_AUTHORITY,"com.eaf.orig.ulo.control.imports.ImportWithdrawAuthority");
    }
	protected void service(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		request.setCharacterEncoding("utf8");
		response.setContentType("text/html; charset=utf-8");
		if(null == importControl){
			importControl = new HashMap<String, String>();
			create();
		}
		String responseMsg = "";
		String IMPORT_ID = request.getParameter("IMPORT_ID");
		String FILE_TYPE = request.getParameter("FILE_TYPE");
		logger.debug("IMPORT_ID >> "+IMPORT_ID);
		logger.debug("FILE_TYPE >> "+FILE_TYPE);
		Collection<Part> parts = request.getParts();
		if(null != parts){
	        for(Part part : parts){
	        	logger.debug("PATH NAME "+part.getName());
	        	if("IMPORT_FILE".equals(part.getName())){	        		
	        		String FILE_NAME =   FileUtil.getFilename(part);	        		
	        		if(!Util.empty(FILE_NAME)){
		        		String className = importControl.get(IMPORT_ID);
		        		if(!Util.empty(className)){
				        	try{
				        		logger.debug("className >> "+className);
				        		if(!Util.empty(className)){
				        			ImportControl control = null; 		        			 
				        		    try{			        		    	
				        		    	control = (ImportControl)Class.forName(className).newInstance();			        		    	
				        			} catch (Exception e) {
				        				logger.fatal("ERROR ",e);
				        			}	
				        		    if(null != control){
				        		    	if(control.requiredEvent()){
				        		    		
				        		    		 ProcessResponse processResponse = control.processEvent(part, request, response);
				        		    		 responseMsg  =processResponse.getData();
				        		    	}else{
						        			String LOCATION = control.getFileLocation(request, IMPORT_ID, FILE_NAME);
						        			if(Util.empty(LOCATION)){
						        				LOCATION = FileUtil.getLocation(FILE_NAME);
						        			}
								        	logger.debug("FILE_NAME "+FILE_NAME);
								        	logger.debug("LOCATION "+LOCATION);
								        	FileUtil.WriteFile(part.getInputStream(),LOCATION,(int)part.getSize());						        		     
						        		    ProcessResponse processResponse= control.processImport(request,FILE_TYPE,FILE_NAME,LOCATION);
						        		    responseMsg = processResponse.getData();
				        		    	}
				        		    }
				        		}
				        	}catch(Exception e){
				        		logger.fatal("ERROR ",e);
				        	}
		        		}else{
		        			String LOCATION = FileUtil.getLocation(FILE_NAME);
				        	logger.debug("FILE_NAME "+FILE_NAME);
				        	logger.debug("LOCATION "+LOCATION);
				        	FileUtil.WriteFile(part.getInputStream(),LOCATION,(int)part.getSize());
		        		}
	        		}
	        	}
	        }
		}
		logger.debug("responseMsg >> "+responseMsg);
		ResponseDataController responseData = new ResponseDataController();
			responseData.response(response,responseMsg);
	}
}
