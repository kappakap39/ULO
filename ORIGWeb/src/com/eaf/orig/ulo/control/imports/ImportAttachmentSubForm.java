package com.eaf.orig.ulo.control.imports;

import java.math.BigDecimal;
import java.net.URLDecoder;
import java.util.ArrayList;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.Part;

import org.apache.log4j.Logger;

import com.eaf.core.ulo.common.date.ApplicationDate;
import com.eaf.core.ulo.common.file.FileUtil;
import com.eaf.core.ulo.common.file.ImportControl;
import com.eaf.core.ulo.common.properties.SystemConfig;
import com.eaf.im.rest.attach.model.AttachFileRequest;
import com.eaf.im.rest.attach.model.AttachFileResponse;
import com.eaf.j2ee.pattern.control.ErrorController;
import com.eaf.orig.profile.model.UserDetailM;
import com.eaf.orig.ulo.app.dao.ORIGDAOFactory;
import com.eaf.orig.ulo.app.dao.OrigAttachmentHistoryDAO;
import com.eaf.orig.ulo.control.util.PersonalInfoUtil;
import com.eaf.orig.ulo.formcontrol.view.form.ORIGFormHandler;
import com.eaf.orig.ulo.model.app.ApplicationGroupDataM;
import com.eaf.orig.ulo.model.app.AttachmentDataM;
import com.eaf.orig.ulo.model.app.PersonalInfoDataM;
import com.eaf.orig.ulo.service.process.response.model.ProcessResponse;
import com.eaf.service.common.model.ServiceRequestDataM;
import com.eaf.service.common.model.ServiceResponseDataM;
import com.eaf.service.common.proxy.ServiceCenterProxy;
import com.eaf.service.module.manual.AttachFileControlProxy;
import com.eaf.service.rest.model.ServiceResponse;

public class ImportAttachmentSubForm implements ImportControl{
	private static transient Logger logger = Logger.getLogger(ImportAttachmentSubForm.class);	
	@Override
	public ProcessResponse processImport(HttpServletRequest request, String FILE_TYPE,String FILE_NAME, String refId) throws Exception {
		return null;
	}
	@Override
	public String getFileLocation(HttpServletRequest request, String IMPORT_ID,String FILE_NAME) throws Exception {
		return null;
	}
	@Override
	public boolean requiredEvent() {
		return true;
	}
	@Override
	public ProcessResponse processEvent(Part part, HttpServletRequest request,HttpServletResponse response) throws Exception {	
		ProcessResponse responseData = new ProcessResponse();
		try{
			ServiceResponseDataM serviceResponse = executeAttachFile(part,request,response);
			String attachFileResult = serviceResponse.getStatusCode();
			logger.debug("attachFileResult : "+attachFileResult);
		    if(ServiceResponse.Status.SUCCESS.equals(attachFileResult)){
		    	AttachFileResponse attachFileResponse = (AttachFileResponse)serviceResponse.getObjectData();
		    	String FILE_TYPE = request.getParameter("FILE_TYPE");
		    	String OTHER_FILE_TYPE = URLDecoder.decode(request.getParameter("OTHER_FILE_TYPE"),"UTF-8");
		    	logger.debug("FILE_TYPE : "+FILE_TYPE);
		    	logger.debug("OTHER_FILE_TYPE : "+OTHER_FILE_TYPE);
				UserDetailM userM = (UserDetailM) request.getSession().getAttribute("ORIGUser");
				ORIGFormHandler ORIGForm =(ORIGFormHandler)request.getSession().getAttribute("ORIGForm");
				ArrayList<AttachmentDataM> attachments  = ORIGForm.getObjectForm().getAttachments();
				if(null == attachments){
					attachments = new ArrayList<AttachmentDataM>();
					ORIGForm.getObjectForm().setAttachments(attachments);
				}
				String userId = userM.getUserName();
				AttachmentDataM  attachmentM  = new AttachmentDataM();
					attachmentM.setApplicationGroupId(ORIGForm.getObjectForm().getApplicationGroupId());
					attachmentM.setFileName(FileUtil.getFilename(part));
					attachmentM.setRefId(attachFileResponse.getIMInternalID());
					attachmentM.setFileType(FILE_TYPE);
					attachmentM.setMimeType(request.getContentType());					
					BigDecimal fileSize = new BigDecimal(part.getSize());
					BigDecimal fileSizeKb = fileSize.divide(new BigDecimal(1024),2,2);	
					logger.debug("fileSize : "+fileSize);			
					logger.debug("fileSizeKb : "+fileSizeKb);				 
					attachmentM.setFileSize(fileSizeKb);					
					attachmentM.setCreateBy(userId);
					attachmentM.setCreateDate(ApplicationDate.getTimestamp());
					attachmentM.setUpdateBy(userId);
					attachmentM.setUpdateDate(ApplicationDate.getTimestamp());
					attachmentM.setFileTypeOth(OTHER_FILE_TYPE);				 
				OrigAttachmentHistoryDAO attachDAO = ORIGDAOFactory.getAttachmentHistoryDAO(userId);
				attachDAO.saveUpdateOrigAttachmentHistoryM(attachmentM);				 
				attachments.add(attachmentM);			
				ORIGForm.getObjectForm().setAttachments(attachments);
				responseData.setStatusCode(ServiceResponse.Status.SUCCESS);
		    }else{
		    	responseData.setStatusCode(serviceResponse.getStatusCode());
		    	responseData.setErrorData(ErrorController.error(serviceResponse.getErrorInfo(),ErrorController.ERROR_MESSAGE_NAME.ERROR_REFER_TO_WORK_SHEET));
		    }
		}catch(Exception e){
			logger.fatal("ERROR",e);	
			responseData.setStatusCode(ServiceResponse.Status.SYSTEM_EXCEPTION);
			responseData.setErrorData(ErrorController.error(e));
		}
		return responseData;
	}
	
	public ServiceResponseDataM executeAttachFile(Part part, HttpServletRequest request,HttpServletResponse response) throws Exception{
		UserDetailM userM = (UserDetailM) request.getSession().getAttribute("ORIGUser");
		String FILE_TYPE = request.getParameter("FILE_TYPE");
		String url = SystemConfig.getProperty("IM_ATTACH_UPLOAD_URL");
		logger.debug("UPLOAD_URL : "+url);				
		ApplicationGroupDataM applicationGroup = ORIGFormHandler.getObjectForm(request);
		PersonalInfoDataM personalM = PersonalInfoUtil.getApplicationTypePersonalInfo(applicationGroup);		
		ServiceCenterProxy restProxy = new ServiceCenterProxy();
		ServiceRequestDataM serviceRequest = new ServiceRequestDataM();
		serviceRequest.setServiceId(AttachFileControlProxy.serviceId);
		serviceRequest.setEndpointUrl(url);
		serviceRequest.setUserId(userM.getUserName());
		serviceRequest.setUniqueId(applicationGroup.getApplicationGroupId());	
		serviceRequest.setRefId(applicationGroup.getApplicationGroupNo());
		AttachFileRequest attachFileRequest = new AttachFileRequest();
			attachFileRequest.setSetID(applicationGroup.getApplicationGroupNo());
			attachFileRequest.setCustomerType(PersonalInfoUtil.getIMPersonalType(applicationGroup));
			attachFileRequest.setCustomerID(personalM.getIdno());
			attachFileRequest.setCustomerIDType(personalM.getCidType());
			attachFileRequest.setCISNumber(personalM.getCisNo());
			attachFileRequest.setDocumentType(FILE_TYPE);
			attachFileRequest.setDocumentName(FileUtil.getFilename(part));
			attachFileRequest.setDocumentFormat(part.getContentType());
		serviceRequest.setObjectData(attachFileRequest);
		serviceRequest.putRawData("ContentFile", part);		
		logger.debug("attachFileRequest >> "+attachFileRequest);		   
		ServiceResponseDataM serviceResponse = restProxy.requestService(serviceRequest);
//		AttachFileResponse attachFileResponse = (AttachFileResponse)serviceResponse.getObjectData();
//		if(Util.empty(attachFileResponse)) {
//			attachFileResponse = new AttachFileResponse();
//			attachFileResponse.setStatusCode(ServiceResponse.Status.SYSTEM_EXCEPTION);
//		}
		return serviceResponse;
	}
	
}
