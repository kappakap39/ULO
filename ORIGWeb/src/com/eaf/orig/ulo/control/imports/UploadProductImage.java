package com.eaf.orig.ulo.control.imports;

import java.io.File;
import java.io.InputStream;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.Part;

import org.apache.commons.io.FileUtils;
import org.apache.log4j.Logger;

import com.eaf.core.ulo.common.file.ImportControl;
import com.eaf.core.ulo.common.properties.SystemConfig;
import com.eaf.j2ee.pattern.control.ErrorController;
import com.eaf.orig.model.ld.UploadProductImageM;
import com.eaf.orig.profile.model.UserDetailM;
import com.eaf.orig.ulo.app.dao.LookupDataFactory;
import com.eaf.orig.ulo.service.process.response.model.ProcessResponse;
import com.eaf.service.rest.model.ServiceResponse;

public class UploadProductImage implements ImportControl{
	private static transient Logger logger = Logger.getLogger(UploadProductImage.class);
	private static String PRODUCT_IMAGE_PATH = SystemConfig.getProperty("PRODUCT_IMAGE_PATH");	
	@Override
	public String getFileLocation(HttpServletRequest request, String IMPORT_ID,	String FILE_NAME) throws Exception {
		return null;
	}
	@Override
	public ProcessResponse processImport(HttpServletRequest request, String IMPORT_ID, String FILE_NAME, String LOCATION) throws Exception {
		UserDetailM userM = (UserDetailM) request.getSession().getAttribute("ORIGUser");
		String fileName = String.valueOf(System.currentTimeMillis());
		ProcessResponse responseData = new ProcessResponse();
		File targetFile = new File(PRODUCT_IMAGE_PATH+fileName+".jpg");
		logger.debug("File Path : "+targetFile);
		logger.debug("File Path : "+targetFile.getAbsolutePath());
		try{
			String productId = request.getParameter("productId");
			String description = request.getParameter("description");
			logger.debug("productId : "+productId);
			logger.debug("description : "+description);
			Part filePart = request.getPart("IMPORT_FILE");
			InputStream inputStream = filePart.getInputStream();
			FileUtils.copyInputStreamToFile(inputStream, targetFile);
			inputStream.close();
			UploadProductImageM uploadProductImageM = new UploadProductImageM();
				uploadProductImageM.setProductId(productId);
				uploadProductImageM.setProductDesc(description);
				uploadProductImageM.setImagePath(targetFile.getAbsolutePath());
				uploadProductImageM.setUpdateBy(userM.getUserName());
			LookupDataFactory.getLookupDataDAO().updateUploadProductImage(uploadProductImageM);
			responseData.setStatusCode(ServiceResponse.Status.SUCCESS);
		}catch(Exception e){
			logger.fatal("ERROR",e);	
			responseData.setStatusCode(ServiceResponse.Status.SYSTEM_EXCEPTION);
			responseData.setErrorData(ErrorController.error(e));
		}
		return responseData;
	}
	@Override
	public boolean requiredEvent() {
		return false;
	}
	@Override
	public ProcessResponse processEvent(Part part, HttpServletRequest request,HttpServletResponse response) throws Exception {
		return null;
	}
}
