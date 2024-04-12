package com.eaf.orig.ulo.pl.app.utility;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.Date;
import java.util.Vector;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.fileupload.FileItem;
import org.apache.log4j.Logger;

import com.eaf.j2ee.pattern.util.ErrorUtil;
import com.eaf.orig.cache.properties.ORIGCacheDataM;
import com.eaf.orig.profile.model.UserDetailM;
import com.eaf.orig.shared.constant.OrigConstant;
import com.eaf.orig.shared.model.InterfaceImportResponseDataM;
import com.eaf.orig.ulo.pl.app.ejb.PLORIGSchedulerManager;
import com.eaf.orig.ulo.pl.app.utility.AttachmentUtility;
import com.eaf.orig.ulo.pl.app.utility.DataFormatUtility;
import com.eaf.orig.ulo.pl.app.utility.PLMessageResourceUtil;
import com.eaf.orig.ulo.pl.model.app.PLAttachmentHistoryDataM;
import com.eaf.orig.ulo.pl.service.PLORIGEJBService;

public class OrigImportTXTFile implements OrigImportFileInf {
	Logger logger = Logger.getLogger(OrigImportTXTFile.class);
	@Override
	public void processImportFile(HttpServletRequest request, ORIGCacheDataM interfaceTypeCacheM, FileItem importFile, FileItem attachFile) {

		Vector<PLAttachmentHistoryDataM> attachFileVT = new Vector<PLAttachmentHistoryDataM>();
		InterfaceImportResponseDataM responseDetailM = new InterfaceImportResponseDataM();
		UserDetailM userM = (UserDetailM) request.getSession().getAttribute("ORIGUser");
		if(userM == null) userM = new UserDetailM();

		if(importFile != null){
			//change credit line file name to "credit_import.*"
	    	String fileName = interfaceTypeCacheM.getSystemID8() + importFile.getName().substring(importFile.getName().lastIndexOf("."),importFile.getName().length());
	    	logger.debug("import file name :"+fileName);
	    	//prepare data for create file
	    	PLAttachmentHistoryDataM attachmentDataM = AttachmentUtility.getInstance().createImportFile(importFile,fileName, interfaceTypeCacheM);
	        attachFileVT.add(attachmentDataM);
		}
		if(attachFileVT.size() > 0){
			File importFileCreate = null;
			File backupFile       = null;
			try{
				AttachmentUtility.getInstance().removeOnlyFileInDir(interfaceTypeCacheM.getSystemID11()); //remove all file before create new file
				String backupPath = attachFileVT.get(0).getFilePath() + File.separator + OrigConstant.BACKUP + File.separator + DataFormatUtility.dateToStringYYYYMMDD(new Date());
				File directory = new File(backupPath);
				if(!directory.exists()) directory.mkdirs();
				
				AttachmentUtility.getInstance().removeFile(backupPath); //remove all backup file
				if(attachFileVT.get(0)!=null){ //import file
					String importFileDir = attachFileVT.get(0).getFilePath() + File.separator + attachFileVT.get(0).getFileName();
					String backupFileDir = backupPath + File.separator + attachFileVT.get(0).getFileName();
			        importFileCreate = new File(importFileDir);
			        backupFile = new File(backupFileDir);
			        importFile.write(importFileCreate); //write import file to disk
			        writeBackupFile(importFileDir,backupFileDir);//write backup file
			        //String zipFileDir = attachFileVT.get(0).getFilePath() + File.separator + interfaceTypeCacheM.getSystemID8() + ".zip";
			        //zipAndDeleteFile(importFileDir, zipFileDir, attachFileVT.get(0).getFileName());
			        
			        PLORIGSchedulerManager manager = PLORIGEJBService.getPLORIGSchedulerManager();
			        manager.processImportInterface(interfaceTypeCacheM, userM);
			        
			        responseDetailM.setImportFileName(attachFileVT.get(0).getFileName()); //Set import file name for display to download
				}
				//create success message
				String successMessage = "<span class='BigtodotextGreenLeft'>"+PLMessageResourceUtil.getTextDescription(request,"UPLOAD_WAIT_IMPORT")+"</span>";
				responseDetailM.setInterfaceType(interfaceTypeCacheM.getCode());
				responseDetailM.setResponseDetail(successMessage);
				request.getSession().setAttribute("IMPORT_RESPONSE",responseDetailM);
			}catch(Exception e){
				e.printStackTrace();
				PLOrigSchedulerUtil util = new PLOrigSchedulerUtil();
				util.deleteFile(importFileCreate);
				util.deleteFile(backupFile);
				String errorMessage = "<span class='BigtodotextRedLeft'>"+ErrorUtil.getShortErrorMessage(request, "SYSTEM_ERROR")+"</span>";
				responseDetailM.setInterfaceType(interfaceTypeCacheM.getCode());
				responseDetailM.setImportFileName(null);
				responseDetailM.setAttachFileName(null);
				responseDetailM.setResponseDetail(errorMessage);
				request.getSession().setAttribute("IMPORT_RESPONSE",responseDetailM);
			}
		}
	}
	public void writeBackupFile(String filePath, String backupPath) throws Exception{
		try{
			File f1 = new File(filePath);
	     	File f2 = new File(backupPath);
	     	InputStream in = new FileInputStream(f1);

	      	//For Append the file.
	      	//OutputStream out = new FileOutputStream(f2,true);

	     	//For Overwrite the file.
	     	OutputStream out = new FileOutputStream(f2);

	    	byte[] buf = new byte[1024];
	    	int len;
	     	while ((len = in.read(buf)) > 0){
	        	out.write(buf, 0, len);
	        }
	     	in.close();
	     	out.close();
	          System.out.println("File copied.");
		}catch(FileNotFoundException ex){
	    	System.out.println(ex.getMessage() + " in the specified directory.");
	    	System.exit(0);
	  	}catch(IOException e){
	    	System.out.println(e.getMessage());   
	    	throw e;
	   	}
	}
	
	public void zipAndDeleteFile(String fileFullName, String fileZipName, String inZipName){
		byte[] buffer = new byte[1024];
		logger.debug("fileFullName:" +fileFullName);
		logger.debug("fileZipName:" +fileZipName);
		logger.debug("inZipName:" +inZipName);
    	try{
 
    		FileOutputStream fos = new FileOutputStream(fileZipName);
    		ZipOutputStream zos = new ZipOutputStream(fos);
    		ZipEntry ze= new ZipEntry(inZipName);
    		zos.putNextEntry(ze);
    		FileInputStream in = new FileInputStream(fileFullName);
 
    		int len;
    		while ((len = in.read(buffer)) > 0) {
    			zos.write(buffer, 0, len);
    		}
 
    		in.close();
    		zos.closeEntry();
 
    		//remember close it
    		zos.close();
    		fos.close();
 
    		System.out.println("Zip Done");
    		
    		PLOrigSchedulerUtil util = new PLOrigSchedulerUtil();
    		util.deleteFile(new File(fileFullName));
 
    	}catch(IOException ex){
    	   ex.printStackTrace();
    	}
	}
}
