package com.eaf.orig.ulo.control.download;

import java.io.OutputStream;
import java.net.URLEncoder;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.io.IOUtils;
import org.apache.log4j.Logger;

import com.eaf.core.ulo.common.file.DownloadControl;
import com.eaf.core.ulo.common.properties.SystemConfig;
import com.eaf.core.ulo.common.util.Util;
import com.eaf.im.rest.attach.model.RetrieveFileRequest;
import com.eaf.im.rest.attach.model.RetrieveFileResponse;
import com.eaf.j2ee.pattern.control.ErrorController;
import com.eaf.orig.profile.model.UserDetailM;
import com.eaf.orig.ulo.app.dao.OrigAttachmentHistoryDAO;
import com.eaf.orig.ulo.app.factory.ModuleFactory;
import com.eaf.orig.ulo.app.olddata.dao.OldDataDAO;
import com.eaf.orig.ulo.app.olddata.dao.OldDataDAOFactory;
import com.eaf.orig.ulo.formcontrol.view.form.ORIGFormHandler;
import com.eaf.orig.ulo.model.app.ApplicationGroupDataM;
import com.eaf.orig.ulo.model.app.AttachmentDataM;
import com.eaf.orig.ulo.model.download.DownLoadM;
import com.eaf.orig.ulo.service.process.response.model.ProcessResponse;
import com.eaf.service.common.model.ServiceRequestDataM;
import com.eaf.service.common.model.ServiceResponseDataM;
import com.eaf.service.common.proxy.ServiceCenterProxy;
import com.eaf.service.module.manual.RetrieveFileControlProxy;
import com.eaf.service.rest.model.ServiceResponse;
import com.google.gson.Gson;

public class DownloadOldAttachmentSubForm implements DownloadControl{
	private static transient Logger logger = Logger.getLogger(DownloadOldAttachmentSubForm.class);	
	@Override
	public ProcessResponse processDownload(HttpServletRequest request) throws Exception {		
		DownLoadM download  = new DownLoadM();
		ProcessResponse responseData = new ProcessResponse();
		try {
			if (request.getParameter("ATTACHMENT_ID") != null) {
				String attachmentId = request.getParameter("ATTACHMENT_ID");
				logger.debug("ATTACHMENT_ID : " + attachmentId);
				AttachmentDataM attachmentM = null;
				OldDataDAO odDAO = OldDataDAOFactory.getOldDataDAO();
				try{
					attachmentM = odDAO.loadAttachmentInfo(attachmentId);
				}catch(Exception e){
					logger.fatal("ERROR",e);
					responseData.setStatusCode(ServiceResponse.Status.SYSTEM_EXCEPTION);
					responseData.setErrorData(ErrorController.error(e));
				}			 
				if(null != attachmentM) {
					download.setFileName(attachmentM.getFileName());
					download.setFilePath(attachmentM.getFilePath());
					download.setMimeType(attachmentM.getMimeType());
				}
			}
			responseData.setStatusCode(ServiceResponse.Status.SUCCESS);
			responseData.setData(new Gson().toJson(download));
		} catch (Exception e) {
			logger.fatal("ERROR",e);
			responseData.setStatusCode(ServiceResponse.Status.SYSTEM_EXCEPTION);
			responseData.setErrorData(ErrorController.error(e));
		}
		
		return responseData;
	}
	
	@Override
	public boolean requiredEvent() {
		return true;
	}
	@Override
	public ProcessResponse processEvent(HttpServletRequest request,HttpServletResponse response) throws Exception {
		ProcessResponse responseData = new ProcessResponse();
		UserDetailM userM = (UserDetailM) request.getSession().getAttribute("ORIGUser");
		ApplicationGroupDataM applicationGroup = ORIGFormHandler.getObjectForm(request);
		String url = SystemConfig.getProperty("IM_ATTACH_DOWNLOAD_URL");
		String attachmentId = request.getParameter("ATTACHMENT_ID");
		logger.debug("url : "+url);
		logger.debug("attachmentId : "+attachmentId);
		if(!Util.empty(attachmentId)){
			AttachmentDataM attachmentM = null;
			OldDataDAO odDAO = OldDataDAOFactory.getOldDataDAO();
			try{
				attachmentM = odDAO.loadAttachmentInfo(attachmentId);
			}catch(Exception e){
				logger.fatal("ERROR",e);
				responseData.setStatusCode(ServiceResponse.Status.SYSTEM_EXCEPTION);
				responseData.setErrorData(ErrorController.error(e));
			}
			try{
				ServiceCenterProxy restProxy = new ServiceCenterProxy();
				ServiceRequestDataM serviceRequest = new ServiceRequestDataM();
					serviceRequest.setServiceId(RetrieveFileControlProxy.serviceId);
					serviceRequest.setEndpointUrl(url);
					serviceRequest.setUserId(userM.getUserName());
					serviceRequest.setUniqueId(applicationGroup.getApplicationGroupId());			
				RetrieveFileRequest retrieveFileRequest = new RetrieveFileRequest();
				retrieveFileRequest.setIMInternalID(attachmentM.getRefId());
				retrieveFileRequest.setImageSize("1");			
				serviceRequest.setObjectData(retrieveFileRequest);			
				ServiceResponseDataM serviceResponse = restProxy.requestService(serviceRequest);			
				RetrieveFileResponse responseEntity = (RetrieveFileResponse)serviceResponse.getObjectData();	
				logger.debug("responseEntity >> "+responseEntity);
				logger.debug("StatusCode >> "+responseEntity.getStatusCode());
				logger.debug("InternalId >> "+responseEntity.getIMInternalID());	
				logger.debug("Error >> "+responseEntity.getError());	
				byte[] contentFile = responseEntity.getContentFile();	
				response.setDateHeader("Last-Modified", System.currentTimeMillis());
				response.setDateHeader("Expires", 0);
				response.setHeader("Content-disposition", "attachment;filename="+URLEncoder.encode(attachmentM.getFileName(),"UTF-8"));
				response.setContentType(attachmentM.getMimeType());				 
				OutputStream output = response.getOutputStream();
				IOUtils.write(contentFile, output);
				output.close();
				responseData.setStatusCode(ServiceResponse.Status.SUCCESS);
			}catch(Exception e){
				logger.fatal("ERROR",e);
				response.setDateHeader("Last-Modified", System.currentTimeMillis());
				response.setDateHeader("Expires", 0);
				response.setHeader("Content-disposition", "attachment;filename="+URLEncoder.encode(attachmentM.getFileName(),"UTF-8"));
				responseData.setStatusCode(ServiceResponse.Status.SYSTEM_EXCEPTION);
				responseData.setErrorData(ErrorController.error(e));
			}
		}
		return responseData;
	}
}
