package com.eaf.orig.ulo.control.download;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;

import com.eaf.core.ulo.common.file.DownloadControl;
import com.eaf.core.ulo.common.properties.SystemConfig;
import com.eaf.j2ee.pattern.control.ErrorController;
import com.eaf.orig.ulo.model.download.DownLoadM;
import com.eaf.orig.ulo.service.process.response.model.ProcessResponse;
import com.eaf.service.rest.model.ServiceResponse;
import com.google.gson.Gson;

public class DownloadWithdrawAuthority implements DownloadControl{
	private static transient Logger logger = Logger.getLogger(DownloadWithdrawAuthority.class);
	
	@Override
	public ProcessResponse processDownload(HttpServletRequest request) throws Exception {
		logger.debug("DownloadExcel");
		ProcessResponse processResponse = new ProcessResponse();
		try {
			DownLoadM download = new DownLoadM();
			String fileName = request.getParameter("FILE_NAME");
	        String filePath =  SystemConfig.getProperty("DM_WITHDRAW_AUTHORITY_DOWLOAD_PATH");
	        download.setFileName(fileName);
			download.setFilePath(filePath);
			download.setMimeType("multipart/form-data");
			processResponse.setStatusCode(ServiceResponse.Status.SUCCESS);
			processResponse.setData(new Gson().toJson(download));
		} catch (Exception e) {
			logger.fatal("ERROR",e);
			processResponse.setStatusCode(ServiceResponse.Status.SYSTEM_EXCEPTION);
			processResponse.setErrorData(ErrorController.error(e));
		}
		
		return processResponse;
	}

	@Override
	public boolean requiredEvent() {
		return false;
	}

	@Override
	public ProcessResponse processEvent(HttpServletRequest request,HttpServletResponse response) throws Exception {
		return null;
	}
}
