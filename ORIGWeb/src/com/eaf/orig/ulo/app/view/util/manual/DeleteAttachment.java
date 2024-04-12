package com.eaf.orig.ulo.app.view.util.manual;

import java.util.ArrayList;
import java.util.Iterator;

import javax.servlet.http.HttpServletRequest;

import org.apache.log4j.Logger;

import com.eaf.core.ulo.common.model.ResponseData;
import com.eaf.core.ulo.common.properties.SystemConfig;
import com.eaf.core.ulo.common.util.Util;
import com.eaf.im.rest.attach.model.DeleteFileRequest;
import com.eaf.im.rest.attach.model.DeleteFileResponse;
import com.eaf.j2ee.pattern.control.AjaxInf;
import com.eaf.j2ee.pattern.control.ResponseDataController;
import com.eaf.orig.profile.model.UserDetailM;
import com.eaf.orig.ulo.app.dao.ORIGDAOFactory;
import com.eaf.orig.ulo.app.dao.OrigAttachmentHistoryDAO;
import com.eaf.orig.ulo.formcontrol.view.form.ORIGFormHandler;
import com.eaf.orig.ulo.model.app.ApplicationGroupDataM;
import com.eaf.orig.ulo.model.app.AttachmentDataM;
import com.eaf.service.common.model.ServiceRequestDataM;
import com.eaf.service.common.model.ServiceResponseDataM;
import com.eaf.service.common.proxy.ServiceCenterProxy;
import com.eaf.service.module.manual.DeleteFileControlProxy;
import com.eaf.service.rest.model.ServiceResponse;

public class DeleteAttachment implements AjaxInf {
	private static transient Logger logger = Logger.getLogger(DeleteAttachment.class);
	String IM_ATTACH_DELETE_URL = SystemConfig.getProperty("IM_ATTACH_DELETE_URL");	
	@Override
	public ResponseData processAction(HttpServletRequest request){
		ResponseDataController responseData = new ResponseDataController(request,ResponseData.FunctionId.DELETE_ATTACHMENT);
		try{
			String attachId = request.getParameter("ATTACH_ID");
			logger.info("attachId : "+attachId);
			UserDetailM userM = (UserDetailM) request.getSession().getAttribute("ORIGUser");
			ApplicationGroupDataM applicationGroup = ORIGFormHandler.getObjectForm(request);
			ArrayList<AttachmentDataM> attachments = applicationGroup.getAttachments();
			if(!Util.empty(attachments)){
				for (Iterator<AttachmentDataM> iterator = attachments.iterator(); iterator.hasNext();) {
					AttachmentDataM attachment = iterator.next();
					if(!Util.empty(attachId) && attachId.equals(attachment.getAttachId())){					
						DeleteFileRequest deleteFileRequest = new DeleteFileRequest();
							deleteFileRequest.setSetID(applicationGroup.getApplicationGroupNo());
							deleteFileRequest.addIMInternalID(attachment.getRefId());
						ServiceRequestDataM serviceRequest = new ServiceRequestDataM();
							serviceRequest.setServiceId(DeleteFileControlProxy.serviceId);
							serviceRequest.setEndpointUrl(IM_ATTACH_DELETE_URL);
							serviceRequest.setUserId(userM.getUserName());
							serviceRequest.setUniqueId(applicationGroup.getApplicationGroupId());
							serviceRequest.setObjectData(deleteFileRequest);	
							String transactionId = (String)request.getSession().getAttribute("transactionId");
						ServiceCenterProxy serviceCenterProxy = new ServiceCenterProxy();
						ServiceResponseDataM serviceResponse = serviceCenterProxy.requestService(serviceRequest,transactionId);
						if(ServiceResponse.Status.SUCCESS.equals(serviceResponse.getStatusCode())){
							DeleteFileResponse deleteFileResponse = (DeleteFileResponse)serviceResponse.getObjectData();
							String deleteFileStatusCode = deleteFileResponse.getStatusCode();
							logger.info("deleteFileStatusCode : "+deleteFileStatusCode);			
							if(ServiceResponse.Status.SUCCESS.equals(deleteFileStatusCode)) {
								OrigAttachmentHistoryDAO attachDAO = ORIGDAOFactory.getAttachmentHistoryDAO();
								attachDAO.deleteOrigAttachmentHistoryM(attachment.getApplicationGroupId(), attachment.getAttachId());
								iterator.remove();
							}else{
								return responseData.error(serviceResponse.getErrorInfo());
							}
						}else{
							return responseData.error(serviceResponse.getErrorInfo());
						}						
					}
				}
			}
		}catch(Exception e){
			logger.fatal("ERROR ",e);
			return responseData.error(e);
		}
		return responseData.success("ATTACHMENT_SUBFORM");
	}
}
