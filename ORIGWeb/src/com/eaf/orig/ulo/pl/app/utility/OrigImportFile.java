package com.eaf.orig.ulo.pl.app.utility;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.text.DecimalFormat;
import java.util.Date;
import java.util.Iterator;
import java.util.Vector;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.fileupload.FileItem;
import org.apache.log4j.Logger;
import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFDateUtil;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

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

public class OrigImportFile implements OrigImportFileInf {
	Logger logger = Logger.getLogger(OrigImportFile.class);
	@Override
	public void processImportFile(HttpServletRequest request, ORIGCacheDataM interfaceTypeCacheM, FileItem importFile, FileItem attachFile) {
		Vector<PLAttachmentHistoryDataM> attachFileVT = new Vector<PLAttachmentHistoryDataM>();
		InterfaceImportResponseDataM responseDetailM = new InterfaceImportResponseDataM();
		UserDetailM userM = (UserDetailM) request.getSession().getAttribute("ORIGUser");
		if(userM == null) userM = new UserDetailM();
		//find max file size
		boolean checkSize = false;
		long maxFileSize=0;
		if(interfaceTypeCacheM.getSystemID9() != null || !"".equals(interfaceTypeCacheM.getSystemID9())){
			checkSize = true;
			maxFileSize=Long.parseLong(interfaceTypeCacheM.getSystemID9())*1024;
		}
		if(importFile != null && checkSize && importFile.getSize() > maxFileSize){
        	String errorDesc = "File name "+importFile.getName()+" size" + importFile.getSize()/1024d +" KB Over "+interfaceTypeCacheM.getSystemID9()+" KB";
        	responseDetailM.setInterfaceType(interfaceTypeCacheM.getCode());
			responseDetailM.setResponseDetail("<span class='BigtodotextRedLeft'>"+errorDesc+"</span>");
        	request.getSession().setAttribute("IMPORT_RESPONSE",responseDetailM);
		}else if(importFile != null){
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
			        importFile.write(backupFile); //write backup file
			        CreateFile(attachFileVT.get(0));
			        AttachmentUtility.getInstance().deleteOnlyFile(importFileCreate); //remove import file after complete import file.
			        
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
	
	private void CreateFile(PLAttachmentHistoryDataM attachmentDataM) throws Exception{
		FileInputStream fis = null;
		BufferedWriter bw = null;
		String delimiter = "|";
		try{
			//File to store data
			String file = attachmentDataM.getFileName().substring(0, attachmentDataM.getFileName().lastIndexOf("."))  + ".txt";
			logger.debug("@@@@@ clone import file:" + file);
		    File f = new File(attachmentDataM.getFilePath() + File.separator + file);
		 
		    OutputStream os = (OutputStream)new FileOutputStream(f);
		    String encoding = "Windows-874"; //need change to UTF-8 for Linux
		    OutputStreamWriter osw = new OutputStreamWriter(os, encoding);
		    bw = new BufferedWriter(osw);
			
			String fileType = attachmentDataM.getFileName().substring(attachmentDataM.getFileName().lastIndexOf(".")+1, attachmentDataM.getFileName().length());
			logger.debug("@@@@@ credit line file type:" + fileType);
			String fileDir = attachmentDataM.getFilePath() + File.separator + attachmentDataM.getFileName();
			logger.debug("@@@@@ credit line file dir:" + fileDir);
			fis = new FileInputStream(fileDir);
			Workbook wb;
	        if("XLS".equalsIgnoreCase(fileType)){
	        	wb = new HSSFWorkbook(fis);
	        }else{
	        	wb = new XSSFWorkbook(fis);
	        }
	        Sheet sheet = wb.getSheetAt(0);
	        Iterator<Row> rows = sheet.rowIterator();
	        int totalCell = 0;
	        int count = 0;
	        while (rows.hasNext()) {
        		Row  row = (Row) rows.next();
	        	if(count == 0){ //find total cell
	        		Iterator<Cell> cells = row.cellIterator();
	        		while (cells.hasNext()){
	        			Cell cell = (Cell)cells.next();
	        			
		        		if(totalCell > 0){
	        				bw.write(delimiter); //write delimiter
	        			}
	        			if(cell != null){ //write cell value
		        			if(cell.getCellType() == 0){ //cell type numeric
		        				double numberValue = cell.getNumericCellValue();
		        				if(numberValue % 1 != 0){
		        					bw.write(String.valueOf(numberValue));
		        				}else{
		        					String numberStr = String.valueOf(numberValue);
		        					numberStr = numberStr.substring(0,numberStr.indexOf("."));
		        					bw.write(numberStr);
		        				}
			            	}else{
			            		bw.write(cell.getStringCellValue().replaceAll("\n", " "));
			            	}
	        			}
	        			
	        			totalCell++;
	        			if(totalCell > 1000){ //Avoid infinity loop;
	        				break;
	        			}
	        		}
	        		bw.newLine(); //write new line
	        		logger.debug("@@@@@ totalCell:" + totalCell);
	        	}else if(count > 0 ){
	        		if(this.isNotEmptyExcelRow(row, totalCell)){
		        		for(int i=0;i<totalCell;i++){
		        			if(i > 0){
		        				bw.write(delimiter); //write delimiter
		        			}
		        			Cell cell = (Cell) row.getCell(i);
		        			if(cell != null){ //write cell value
		        				//logger.debug("@@@@@ cell.getCellType():" + cell.getCellType());
		        				if(cell.getCellType() == HSSFCell.CELL_TYPE_NUMERIC){ //cell type numeric
		        					if(HSSFDateUtil.isCellDateFormatted(cell)){
		        						bw.write(DataFormatUtility.dateEnToString(cell.getDateCellValue(), DataFormatUtility.DateFormatType.FORMAT_DDMMYYY_S));
		        					}else{
				        				double numberValue = cell.getNumericCellValue();
				        				DecimalFormat changeFormat = new DecimalFormat("#0.0#");
			        					//logger.debug(i+"numberValue:"+changeFormat.format(numberValue));
				        				if(numberValue % 1 != 0){
				        					bw.write(changeFormat.format(numberValue));
				        				}else{
				        					String numberStr = changeFormat.format(numberValue);
				        					numberStr = numberStr.substring(0,numberStr.indexOf("."));
				        					bw.write(numberStr);
				        				}
		        					}
				            	}else{
				            		bw.write(cell.getStringCellValue().replaceAll("\n", " "));
				            	}
		        			}
		        		}
		        		bw.newLine(); //write new line
	        		}
	        	}	        	
	            count ++;
	        }
	        bw.flush();
	        bw.close();
	        fis.close();
		}catch (Exception e){
			e.printStackTrace();
			try{
				bw.flush();
		        bw.close();
		        fis.close();
			}catch(Exception ex){
				ex.printStackTrace();
			}
			throw e;
		}
	}
	
	private boolean isNotEmptyExcelRow(Row row, int totalCell){
		boolean result = false;
		short c;
		for (c = row.getFirstCellNum(); c < totalCell; c++) {
			Cell cell = row.getCell(c);
			if (cell != null && row.getCell(c).getCellType() != HSSFCell.CELL_TYPE_BLANK) {
				result = true;
				break;
			}
		}
		return result;
	}
	
}
