package com.eaf.orig.ulo.control.download;

import java.io.File;
import java.io.FileInputStream;
import java.io.OutputStream;
import java.net.URLEncoder;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
import org.springframework.util.Base64Utils;

import com.eaf.core.ulo.common.file.DownloadControl;
import com.eaf.orig.ulo.service.process.response.model.ProcessResponse;

public class DownloadLinkReport implements DownloadControl{
	private static transient Logger logger = Logger.getLogger(DownloadLinkReport.class);
	@Override
	public ProcessResponse processDownload(HttpServletRequest request) throws Exception {
		return null;
	}

	@Override
	public boolean requiredEvent() {
		return true;
	}

	@Override
	public ProcessResponse processEvent(HttpServletRequest request,HttpServletResponse response) throws Exception{
		String reportId = request.getParameter("reportId");
		logger.debug("reportId : "+reportId);
		String reportPath = new String(Base64Utils.decodeFromString(reportId));
		logger.debug("reportPath : "+reportPath);
		response.setDateHeader("Last-Modified", System.currentTimeMillis());
		response.setDateHeader("Expires", 0);
		File downloadFile = new File(reportPath);
		response.setHeader("Content-disposition", "attachment;filename="+URLEncoder.encode(downloadFile.getName(),"UTF-8"));
		response.setContentType("multipart/form-data");
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
		return null;
	}
	
}
