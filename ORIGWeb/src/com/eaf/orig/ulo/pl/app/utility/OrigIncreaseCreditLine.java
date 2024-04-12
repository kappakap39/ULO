package com.eaf.orig.ulo.pl.app.utility;

import java.io.File;
import java.util.Vector;
import javax.servlet.http.HttpServletRequest;
import org.apache.commons.fileupload.FileItem;
import org.apache.log4j.Logger;
import com.eaf.j2ee.pattern.util.ErrorUtil;
import com.eaf.orig.cache.properties.ORIGCacheDataM;
import com.eaf.orig.profile.model.UserDetailM;
import com.eaf.orig.shared.constant.OrigConstant;
import com.eaf.orig.shared.model.InterfaceImportResponseDataM;
import com.eaf.orig.ulo.pl.app.utility.AttachmentUtility;
import com.eaf.orig.ulo.pl.app.utility.PLMessageResourceUtil;
import com.eaf.orig.ulo.pl.model.app.PLAttachmentHistoryDataM;
import com.eaf.orig.ulo.pl.service.PLORIGEJBService;

public class OrigIncreaseCreditLine implements OrigImportFileInf {
	Logger logger = Logger.getLogger(OrigIncreaseCreditLine.class);
	@Override
	public void processImportFile(HttpServletRequest request, ORIGCacheDataM interfaceTypeCacheM, FileItem importFile, FileItem attachFile) {
		Vector<PLAttachmentHistoryDataM> attachFileVT = new Vector<PLAttachmentHistoryDataM>();
		UserDetailM userM =	(UserDetailM) request.getSession().getAttribute("ORIGUser");
		InterfaceImportResponseDataM responseDetailM = new InterfaceImportResponseDataM();
		
		//find max file size
		if(interfaceTypeCacheM.getSystemID9() == null || "".equals(interfaceTypeCacheM.getSystemID9())){
			interfaceTypeCacheM.setSystemID9("1024"); //default 1 MB.
		}
		long maxFileSize=Long.parseLong(interfaceTypeCacheM.getSystemID9())*1024;
		if(importFile != null && importFile.getSize() > maxFileSize){//validate import file size
			String errorDesc = "File name "+importFile.getName()+" size" + importFile.getSize()/1024d +" KB Over "+interfaceTypeCacheM.getSystemID9()+" KB";
        	responseDetailM.setInterfaceType(interfaceTypeCacheM.getCode());
			responseDetailM.setResponseDetail("<span class='BigtodotextRedLeft'>"+errorDesc+"</span>");
        	request.getSession().setAttribute("IMPORT_RESPONSE",responseDetailM);
		}else if(attachFile != null && attachFile.getSize() > maxFileSize){//validate attached file size
			String errorDesc = "File name "+attachFile.getName()+" size" + attachFile.getSize()/1024d +" KB Over "+interfaceTypeCacheM.getSystemID9()+" KB";
        	responseDetailM.setInterfaceType(interfaceTypeCacheM.getCode());
			responseDetailM.setResponseDetail("<span class='BigtodotextRedLeft'>"+errorDesc+"</span>");
        	request.getSession().setAttribute("IMPORT_RESPONSE",responseDetailM);
		}else{
			if(importFile != null){
				//change credit line file name to "credit_import.*"
		    	String fileName = interfaceTypeCacheM.getSystemID8() + importFile.getName().substring(importFile.getName().lastIndexOf("."),importFile.getName().length());
		    	logger.debug("import file name :"+fileName);
		    	//prepare data for create file
		    	PLAttachmentHistoryDataM attachmentDataM = AttachmentUtility.getInstance().createImportCreditFile(importFile,fileName);
		        attachFileVT.add(attachmentDataM);
			}
			if(attachFile != null){
				//change approve file name to "approve_attach.*"
		    	String fileName = OrigConstant.APPROVE_FILE_NAME + attachFile.getName().substring(attachFile.getName().lastIndexOf("."),attachFile.getName().length());
		    	logger.debug("attach file name :"+fileName);
		    	//prepare data for create file
		    	PLAttachmentHistoryDataM attachmentDataM = AttachmentUtility.getInstance().createImportCreditFile(attachFile, fileName);
				attachFileVT.add(attachmentDataM);
			}
		}
		if(attachFileVT.size() > 0){
			try{
				//AttachmentUtility.getInstance().removeFile(interfaceTypeCacheM.getSystemID11() + File.separator + DataFormatUtility.dateToStringYYYYMMDD(new Date())); //remove all file before create new file
				if(attachFileVT.get(0)!=null){ //credit line file need create file only
					AttachmentUtility.getInstance().removeFile(attachFileVT.get(0).getFilePath());
			        File importFileCreate = new File(attachFileVT.get(0).getFilePath() + File.separator + attachFileVT.get(0).getFileName());
			        importFile.write(importFileCreate); //write credit line file to disk
			        responseDetailM.setImportFileName(attachFileVT.get(0).getFileName());
				}
				if(attachFileVT.size() > 1 && attachFileVT.get(1)!=null){ //approve file need create and insert data to attachment history
			        File attachFileCreate = new File(attachFileVT.get(1).getFilePath()+File.separator+attachFileVT.get(1).getFileName());
			        attachFile.write(attachFileCreate); //write approve file to disk			        
			        attachFileVT.get(1).setCreateBy(userM.getUserName());
			        attachFileVT.get(1).setUpdateBy(userM.getUserName());
			        PLORIGEJBService.getORIGDAOUtilLocal().saveUpdateModelOrigAttachmentHistoryM(attachFileVT.get(1));
			        responseDetailM.setAttachFileName(attachFileVT.get(1).getFileName());
				}
				//create success message
				String successMessage = "<span class='BigtodotextGreenLeft'>"+PLMessageResourceUtil.getTextDescription(request,"UPLOAD_WAIT_IMPORT")+"</span>";
				responseDetailM.setInterfaceType(interfaceTypeCacheM.getCode());
				responseDetailM.setResponseDetail(successMessage);
				request.getSession().setAttribute("IMPORT_RESPONSE",responseDetailM);
			}catch(Exception e){
				e.printStackTrace();
				String errorMessage = "<span class='BigtodotextRedLeft'>"+ErrorUtil.getShortErrorMessage(request, "SYSTEM_ERROR")+"</span>";
				responseDetailM.setInterfaceType(interfaceTypeCacheM.getCode());
				responseDetailM.setResponseDetail(errorMessage);
				responseDetailM.setImportFileName(null);
				responseDetailM.setAttachFileName(null);
				request.getSession().setAttribute("IMPORT_RESPONSE",responseDetailM);
			}
		}
	}

}
