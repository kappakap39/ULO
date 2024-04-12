package com.eaf.orig.ulo.app.view.form.image;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.OutputStream;

import javax.servlet.ServletException;
//import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;

//import com.eaf.core.ulo.common.properties.SystemConfig;
import com.eaf.core.ulo.common.util.Util;
//import com.eaf.orig.ulo.formcontrol.view.form.ORIGFormHandler;
//import com.eaf.orig.ulo.model.app.ApplicationGroupDataM;
//import com.eaf.orig.ulo.model.app.ApplicationImageSplitDataM;

//@WebServlet("/DisplayImage")
public class DisplayImage extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private static final transient Logger logger = Logger.getLogger(DisplayImage.class);
	
	protected void service(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		logger.debug("DisplayImageServlet()..");
		String imgPageId = request.getParameter("imgPageId");
		String imgSize = request.getParameter("size");
		String imageFilePath = getImageFilePath(imgPageId,imgSize,request);				
		logger.debug("imageFilePath >> "+imageFilePath);
		if(!Util.empty(imageFilePath)){
			try{
				getImage(imageFilePath,response);
			}catch(Exception e){
				logger.fatal("ERROR ",e);
			}
		}
	}
	private void getImage(String imageFilePath,HttpServletResponse response) throws Exception{
		// DON'T USE CACHE-CONTROL  THANK YOU
//		response.setDateHeader("Last-Modified",System.currentTimeMillis());
//		response.setHeader("Cache-Control", "no-cache,no-store");
//		response.setDateHeader("Expires",0);
		if (imageFilePath.toLowerCase().endsWith("gif")) {
			response.setContentType("image/gif");
		}else if(imageFilePath.toLowerCase().endsWith("png")) {
			response.setContentType("image/png");
		}else{
			response.setContentType("image/jpg");
		}
		OutputStream output = null;
		FileInputStream is = null;
		try{
			if(!Util.empty(imageFilePath)){
				is = new FileInputStream(new File(imageFilePath));
			 	output = response.getOutputStream();		
				if(null != is){			
					int read = 0;
					byte[] bytes = new byte[1024];
					while((read = is.read(bytes)) != -1){
						output.write(bytes, 0, read);
					}				
				}
			}
		}catch(Exception e){
			logger.error("ERROR "+e.getMessage());
		}finally{
			if(null != output){
				output.close();
				output.flush();
			}
			if(null != is){
				is.close();				
			}
			is = null;
			output = null;
		}
	}
	private String getImageFilePath(String imgPageId,String imgSize,HttpServletRequest request){
//		logger.debug("imgPageId >> "+imgPageId);
//		logger.debug("imgSize >> "+imgSize);
		String imageFilePath = "";
//		ORIGFormHandler ORIGForm = (ORIGFormHandler) request.getSession().getAttribute("ORIGForm");
//		ApplicationGroupDataM applicationGroup = ORIGForm.getObjectForm();
//		ApplicationImageSplitDataM imageSplit = applicationGroup.getImageSplit(imgPageId);
//		if(null != imageSplit){
//			imageFilePath = SystemConfig.getProperty("IMAGE_TEMPLATE_PATH");
//			imageFilePath += imageSplit.getRealPath();
//			imageFilePath += File.separator;
//			imageFilePath += imgSize;
//			imageFilePath += File.separator;
//			imageFilePath += imageSplit.getFileName();
//			imageFilePath += ".";
//			imageFilePath += imageSplit.getFileType();
//		}
		return imageFilePath;
	}
}
