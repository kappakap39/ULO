/**
 * Create Date Mar 12, 2012 
 * Create By Sankom
 * Copyright (c) 2012 Avalant Co.,Ltd.
 * 20 North Sathorn Road, 15 th Floor Bubhajit Bldg., Silom, Bangrak, Bangkok 10500, Thailand
 * All rights reserved.
 *
 * This software is the confidential and prorietary infomation of
 * Avalant Co.,Ltd. ("Confidential Infomation"). You shall not
 * disclose such Confidential Infomation and shall use it only in
 * accordance with the terms of the license agreement you entered into
 * with Avalant Co.,Ltd.
*/
package com.eaf.orig.ulo.pl.app.utility;

import java.io.File;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.math.BigDecimal;
import java.net.URLDecoder;
import java.sql.Timestamp;
import java.text.DecimalFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Vector;

import org.apache.commons.fileupload.FileItem;
import org.apache.commons.io.FilenameUtils;
import org.apache.log4j.Logger;

import com.eaf.orig.cache.properties.ORIGCacheDataM;
import com.eaf.orig.shared.constant.EJBConstant;
import com.eaf.orig.shared.constant.OrigConstant;
import com.eaf.orig.shared.dao.utility.EjbUtil;
import com.eaf.orig.shared.ejb.ORIGGeneratorManager;
import com.eaf.orig.shared.service.ORIGEJBService;
import com.eaf.orig.shared.util.OrigUtil;
import com.eaf.orig.shared.utility.ORIGCacheUtil;
import com.eaf.orig.ulo.pl.config.ORIGConfig;
import com.eaf.orig.ulo.pl.model.app.PLAttachmentHistoryDataM;


/**
 * @author Sankom
 *
 */
public class AttachmentUtility {
    private String attachmentPath;
    private String attachmentTempPath;
    private double maxFileSize;
    private static AttachmentUtility me;
    Logger logger = Logger.getLogger(AttachmentUtility.class);
	public String getAttachmentPath() {
		return attachmentPath;
	}

	public void setAttachmentPath(String attachmentPath) {
		this.attachmentPath = attachmentPath;
	}
	private AttachmentUtility(){
		
		attachmentPath =  ORIGConfig.LOCAL_IMAGE_PATH;  //(String)LoadXML.getAtttachment().get("LOCAL_IMAGE_PATH");	
		attachmentTempPath=ORIGConfig.LOCAL_IMAGE_TEMP_PATH;//(String)LoadXML.getAtttachment().get("LOCAL_IMAGE_TEMP_PATH");	
		try {
			maxFileSize= ORIGConfig.MAX_FILE_SIZE;//Integer.parseInt((String)LoadXML.getAtttachment().get("MAX_FILE_SIZE"));		 
		} catch (NumberFormatException e) {
			maxFileSize=1024;
			e.printStackTrace();
		}	
	}
    public synchronized static AttachmentUtility getInstance(){
    	  if (me == null) {
              me = new AttachmentUtility();
          }
          return me;
    }
   public  PLAttachmentHistoryDataM createFile(FileItem item){
//	   boolean result=true;
	   PLAttachmentHistoryDataM  attachmentDataM=new PLAttachmentHistoryDataM();
	     
		ORIGGeneratorManager generatorManager = ORIGEJBService.getGeneratorManager();
		String attachmentId = generatorManager.generateUniqueIDByName(EJBConstant.ATTACHMENT_ID);
		attachmentId="AT"+EjbUtil.appendZero(  attachmentId,10);
		logger.debug("attachmentId="+attachmentId);
		String filePath=getFilePath(attachmentId) ;
		logger.debug("filePath="+filePath);		
		File directory=new File(filePath);
		if(!directory.exists()){
			directory.mkdirs();
		}
		
		 String fileName = item.getName();
		    if (fileName != null) { 
		    	fileName = FilenameUtils.getName(fileName);		    	
		    	try {
					fileName=  URLDecoder.decode(fileName, "UTF-8");
			        System.out.println("Server should URL-decode with UTF-8: " + fileName);
						//new String(fileName.getBytes("UTF-8"),"TIS-620");
				} catch (UnsupportedEncodingException e) {				 
					logger.fatal("Encoding Error ",e);
				}
		    }
		try {
			String fileCreate=filePath+File.separator+fileName;
			logger.debug("File Create"+fileCreate);		 
			attachmentDataM.setAttachId(attachmentId );
			attachmentDataM.setFilePath(filePath);
			attachmentDataM.setFileName(fileName);
			attachmentDataM.setMimeType(item.getContentType());
			attachmentDataM.setFileSize(item.getSize());
			attachmentDataM.setFileType(fileName.substring(fileName.lastIndexOf("."),fileName.length()));
			attachmentDataM.setCreateDate(new Timestamp( new java.util.Date().getTime()));
		} catch ( Exception e) {			 
			logger.fatal("Error",e);
		}
	   return attachmentDataM;
   }    
  public String  getFilePath( String attachmentId){
	  String path="";
	  DecimalFormat df4=new DecimalFormat("0000");
	  DecimalFormat df2=new DecimalFormat("00");
	  Calendar cl=Calendar.getInstance();
	  path= File.separator+"ATTACHES"+File.separator+df4.format(cl.get(Calendar.YEAR))+File.separator+df2.format(cl.get(Calendar.MONTH)+1)
	  +File.separator+df2.format(cl.get(Calendar.DAY_OF_MONTH))+File.separator+df2.format(cl.get(Calendar.HOUR_OF_DAY));
	  path=attachmentPath+path+File.separator+attachmentId;	  
	  return path;
  }

public String getAttachmentTempPath() {
	return attachmentTempPath;
}

public void setAttachmentTempPath(String attachmentTempPath) {
	this.attachmentTempPath = attachmentTempPath;
}

public double getMaxFileSize() {
	return maxFileSize;
}

public void setMaxFileSize(double maxFileSize) {
	this.maxFileSize = maxFileSize;
} 

	public String drawAttachmentTable(Vector<PLAttachmentHistoryDataM> attachmentVect, String role, String displayMode, String userName){
		StringBuffer bResult = new StringBuffer();
		ORIGCacheUtil cacheUtil = ORIGCacheUtil.getInstance();
		bResult.append("<table class=\"TableFrame\">");
		
		bResult.append("<tr height=25 class=Header>");
		if(HTMLRenderUtil.DISPLAY_MODE_VIEW.equals(displayMode)){
			bResult.append("<td align=center>").append("<INPUT  name=\"attCheckAll\" id=\"attCheckAll\" value=\"\"  type=\"checkbox\" disabled=\"disabled\" ></td>");
		} else {
			bResult.append("<td align=center>").append("<INPUT  name=\"attCheckAll\" id=\"attCheckAll\" value=\"\"  type=\"checkbox\" onClick=\"checkItemAll(this)\" ></td>");
		}
		bResult.append("<td align=center>File Name</td>");
		bResult.append("<td align=center>File Category</td>");
		bResult.append("<td align=center>File Size(KB)</td>");
		bResult.append("</tr>");
		
		try {
			if (attachmentVect != null && attachmentVect.size() > 0) {
				for (int i = 0; i < attachmentVect.size(); i++) {
					String styleTr = (i%2==0)?"ResultEven":"ResultOdd";
				    PLAttachmentHistoryDataM plAttachmentHistoryDataM = attachmentVect.get(i);
				    bResult.append("<tr class=\"Result-Obj ").append(styleTr).append(" \" >");
				    
				    if(!OrigUtil.isEmptyString(displayMode) && displayMode.equals(HTMLRenderUtil.DISPLAY_MODE_VIEW)){
				    	bResult.append("<td align=center><input type=\"checkbox\" name=\"chkAttachmentId\" value=\"\" disabled=\"disabled\"></td>");
				    } else {
				    	if(OrigUtil.isEmptyString(role) && role.equals(OrigConstant.ROLE_PO)){
				    		bResult.append("<td align=center><input type=\"checkbox\" name=\"chkAttachmentId\" value=\"\" disabled=\"disabled\"></td>");
				    	} else if(!plAttachmentHistoryDataM.getCreateBy().equals(userName)){
				    		bResult.append("<td align=center><input type=\"checkbox\" name=\"chkAttachmentId\" value=\"\" disabled=\"disabled\"></td>");
				    	} else {
				    		bResult.append("<td align=center><input type=\"checkbox\" name=\"chkAttachmentId\" value=\""+plAttachmentHistoryDataM.getAttachId()+"\" onclick=\"checkAttachmentClick(this)\"></td>");
				    	}
				    }
				    
				    
				    /*if (!OrigUtil.isEmptyString(displayMode) && displayMode.equals(DisplayFormatUtil.DISPLAY_MODE_EDIT)) {
				    	bResult.append("<td align=center><input type=\"checkbox\" name=\"chkAttachmentId\" value=\""+plAttachmentHistoryDataM.getAttachId()+"\" onclick=\"checkAttachmentClick(this)\"></td>");
					} else if ((!OrigUtil.isEmptyString(role) && role.equals(OrigConstant.ROLE_PO) && !plAttachmentHistoryDataM.getCreateBy().equals(userName)) || (!OrigUtil.isEmptyString(displayMode) && displayMode.equals(DisplayFormatUtil.DISPLAY_MODE_VIEW))) {
						bResult.append("<td align=center><input type=\"checkbox\" name=\"chkAttachmentId\" value=\"\" disabled=\"disabled\"></td>");
				    } else if (!OrigUtil.isEmptyString(displayMode) && displayMode.equals(DisplayFormatUtil.DISPLAY_MODE_EDIT) && plAttachmentHistoryDataM.getCreateBy().equals(userName)){
				    	bResult.append("<td align=center><input type=\"checkbox\" name=\"chkAttachmentId\" value=\""+plAttachmentHistoryDataM.getAttachId()+"\" onclick=\"checkAttachmentClick(this)\"></td>");
				    } else if(!OrigUtil.isEmptyString(plAttachmentHistoryDataM.getIsNew()) && plAttachmentHistoryDataM.getIsNew().equals(OrigConstant.FLAG_Y) ){
				    	bResult.append("<td align=center><input type=\"checkbox\" name=\"chkAttachmentId\" value=\""+plAttachmentHistoryDataM.getAttachId()+"\" onclick=\"checkAttachmentClick(this)\"></td>");
				    }*/
				    
				    logger.debug("end tr");
				    bResult.append("<td align=center><a href=\"#\" onclick=\"downLoadAttach('"+plAttachmentHistoryDataM.getAttachId()+"')\">"+HTMLRenderUtil.displayHTML(plAttachmentHistoryDataM.getFileName()) +"</a></td>");
				    bResult.append("<td align=center>"+HTMLRenderUtil.displayHTML(cacheUtil.getNaosCacheDisplayNameDataM(40,plAttachmentHistoryDataM.getFileType()))+"</td>");
				    bResult.append("<td align=center>"+DataFormatUtility.displayCommaNumber(new BigDecimal(plAttachmentHistoryDataM.getFileSize()/1024d)) +"</td>");
				    bResult.append("</tr>");
				}
			} else {
				bResult.append("<tr class=\"ResultNotFound\" id=\"notFoundTable\"><td colspan=\"4\">No record found</td></tr>");
			}
		} catch (Exception e) {	 
			e.printStackTrace();
		}
		bResult.append("</table>");
		return bResult.toString();
	}
	
    //create by praisan 20120619
    public  PLAttachmentHistoryDataM createImportCreditFile(FileItem item, String fileName){
    	PLAttachmentHistoryDataM  attachmentDataM = new PLAttachmentHistoryDataM();
		String attachmentId = "CL" + DataFormatUtility.dateToStringYYYYMMDD(new Date());
		logger.debug("attachmentId=" + attachmentId);
		String filePath = getImportCreditFilePath();
		logger.debug("@@@@@ filePath="+filePath);		
		File directory=new File(filePath);
		if(!directory.exists()){
			logger.debug("@@@@@ create directory:"+filePath);
			directory.mkdirs();
		}
	    if (fileName != null) {
	    	fileName = FilenameUtils.getName(fileName);
	    	try {
				fileName=new String(fileName.getBytes("UTF-8"));
			} catch (UnsupportedEncodingException e) {				 
				logger.fatal("Encoding Error ",e);
			}
	    }
		try {
			String fileCreate = filePath+File.separator + fileName;
			logger.debug("File location :" + fileCreate);		 
			attachmentDataM.setAttachId(attachmentId );
			attachmentDataM.setFilePath(filePath);
			attachmentDataM.setFileName(fileName);
			attachmentDataM.setMimeType(item.getContentType());
			attachmentDataM.setFileSize(item.getSize());
			attachmentDataM.setFileType(fileName.substring(fileName.lastIndexOf("."), fileName.length()));			 
		} catch ( Exception e) {			 
			logger.fatal("Error",e);
		}
	   return attachmentDataM;
    }
    
  //create by praisan 20120919
    public  PLAttachmentHistoryDataM createImportFile(FileItem item, String fileName, ORIGCacheDataM interfaceTypeCacheM){
    	PLAttachmentHistoryDataM  attachmentDataM = new PLAttachmentHistoryDataM();
		String filePath = interfaceTypeCacheM.getSystemID11() ;
		logger.debug("filePath="+filePath);		
		File directory=new File(filePath);
		if(!directory.exists()){
			directory.mkdirs();
		}
	    if (fileName != null) {
	    	fileName = FilenameUtils.getName(fileName);
	    	try {
				fileName=new String(fileName.getBytes("UTF-8"));
			} catch (UnsupportedEncodingException e) {				 
				logger.fatal("Encoding Error ",e);
			}
	    }
		try {
			String fileCreate = filePath+File.separator + fileName;
			logger.debug("File location :" + fileCreate);
			attachmentDataM.setFilePath(filePath);
			attachmentDataM.setFileName(fileName);
			attachmentDataM.setMimeType(item.getContentType());
			attachmentDataM.setFileSize(item.getSize());
			attachmentDataM.setFileType(fileName.substring(fileName.lastIndexOf("."), fileName.length()));			 
		} catch ( Exception e) {			 
			logger.fatal("Error",e);
		}
	   return attachmentDataM;
    }
    
    public String  getImportCreditFilePath(){
  	  String path="";
  	  DecimalFormat df4=new DecimalFormat("0000");
  	  DecimalFormat df2=new DecimalFormat("00");
  	  Calendar cl=Calendar.getInstance();
  	  path= File.separator+"ATTACHES"+File.separator+df4.format(cl.get(Calendar.YEAR))+File.separator+df2.format(cl.get(Calendar.MONTH)+1)
  	  +File.separator+df2.format(cl.get(Calendar.DAY_OF_MONTH)) + File.separator + "CreditLine";
  	  path=attachmentPath+path;	  
  	  return path;
    }
    
    public void delete(File file) throws IOException{
		if(file.isDirectory()){
			//directory is empty, then delete it
			if(file.list().length==0){
			   file.delete();
			   logger.debug("@@@@@ Directory is deleted : " + file.getAbsolutePath());
			}else{
			   //list all the directory contents
	    	   String files[] = file.list();
	    	   for (String temp : files) {
	    	      //construct the file structure
	    	      File fileDelete = new File(file, temp);
	    	      //recursive delete
	    	      delete(fileDelete);
	    	   }
	    	   //check the directory again, if empty then delete it
	    	   if(file.list().length==0){
	       	     file.delete();
	    	     logger.debug("@@@@@ Directory is deleted : " + file.getAbsolutePath());
	    	   }
			}
		}else{
			//if file, then delete it
			file.delete();
			logger.debug("@@@@@ File is deleted : " + file.getAbsolutePath());
		}
	}
	
    public void removeFile(String filePath){
		logger.debug("@@@@@ remove directory:" + filePath);
		File directory = new File(filePath);
    	//make sure directory exists
    	if(!directory.exists()){
           logger.debug("@@@@@ Directory does not exist.");
           //System.exit(0);
        }else{
           try{
        	   deleteOnlyFile(directory);
           }catch(IOException e){
               e.printStackTrace();
               //System.exit(0);
           }
        }
	}
    
    public void removeOnlyFileInDir(String dirPath){
    	logger.debug("@@@@@ begin removeFileInDir:" + dirPath);
		File directory = new File(dirPath);
		if(!directory.exists()){
	           logger.debug("@@@@@ Directory does not exist.");
	    }else{
	    	if(directory.list().length >0){
				   //list all the directory contents
		    	   String files[] = directory.list();
		    	   for (String temp : files) {
		    	      //construct the file structure
		    	      File fileDelete = new File(directory, temp);
		    	      if(!fileDelete.isDirectory()){
		    	    	  fileDelete.delete();
		    	    	  logger.debug("@@@@@ File is deleted : " + fileDelete.getAbsolutePath());
		    	      }
		    	   }
			}
	    }
		logger.debug("@@@@@ end removeFileInDir:" + dirPath);
    }
	
    public void deleteOnlyFile(File file) throws IOException{
		if(file.isDirectory()){
			//directory is empty, then delete it
			if(file.list().length >0){
			   //list all the directory contents
	    	   String files[] = file.list();
	    	   for (String temp : files) {
	    	      //construct the file structure
	    	      File fileDelete = new File(file, temp);
	    	      //recursive delete
	    	      delete(fileDelete);
	    	   }
			}
		}else{
			//if file, then delete it
			file.delete();
			logger.debug("@@@@@ File is deleted : " + file.getAbsolutePath());
		}
	}
}
