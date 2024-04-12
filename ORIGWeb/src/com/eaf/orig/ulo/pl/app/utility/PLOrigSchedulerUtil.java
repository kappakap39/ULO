package com.eaf.orig.ulo.pl.app.utility;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.OutputStreamWriter;
import java.text.DecimalFormat;
import java.util.Calendar;
import java.util.Iterator;
import java.util.Vector;
import org.apache.log4j.Logger;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import com.eaf.orig.ulo.pl.model.app.PLImportCreditLineDataM;
import com.eaf.orig.ulo.pl.model.app.PLResponseImportDataM;

public class PLOrigSchedulerUtil {
	private Logger logger = Logger.getLogger(PLOrigSchedulerUtil.class);
	
	public java.sql.Date getSysTimeStamp() {
        Calendar cal = Calendar.getInstance();

        return new java.sql.Date(cal.getTime().getTime());
    }
	
	public Vector<PLImportCreditLineDataM> loadXLSToModel(String fileName, String filePath, String sessionId){
		FileInputStream fis = null;
		try{
			logger.debug("@@@@@ Import credit line sessionId:"+sessionId);
			String fileType = fileName.substring(fileName.lastIndexOf(".")+1, fileName.length());
			logger.debug("@@@@@ credit line file type:" + fileType);
			String fileDir = filePath+File.separator+fileName;
			logger.debug("@@@@@ credit line file dir:" + fileDir);
			fis = new FileInputStream(fileDir);
			Workbook wb;
	        if("XLS".equalsIgnoreCase(fileType)){
	        	wb = new HSSFWorkbook(fis);
	        }else{
	        	wb = new XSSFWorkbook(fis);
	        }
	        Sheet sheet = wb.getSheetAt(0);
	        Iterator rows = sheet.rowIterator();
	        
	        Vector<PLImportCreditLineDataM> importCreditLineVect = new Vector<PLImportCreditLineDataM>();
	        int count = 0;
	        while (rows.hasNext()) {
	        	Row  row = (Row ) rows.next();
	        	if(count > 0 ){
//		            Iterator cells = row.cellIterator();
		            
		            PLImportCreditLineDataM importCreditLineM = new PLImportCreditLineDataM();
		            
		            Cell cell_0 = (Cell) row.getCell(0);
		            logger.debug("cell_0.getCellType()"+cell_0.getCellType());
		            Cell cell_1 = (Cell) row.getCell(1);
		            logger.debug("cell_1.getCellType()"+cell_1.getCellType());
		            importCreditLineM.setSessionId(sessionId);
		            if(cell_0 != null){
			            if(cell_0.getCellType() == 0){
			            	importCreditLineM.setCardNo(new DecimalFormat("#").format(cell_0.getNumericCellValue()));
			            }else{
			            	importCreditLineM.setCardNo(cell_0.getStringCellValue());	            	
			            }
		            }
		            if(cell_1 != null){
			            if(cell_1.getCellType() == 0){
			            	importCreditLineM.setCreditLine(new DecimalFormat("#").format(cell_1.getNumericCellValue()));
			            }else{
			            	importCreditLineM.setCreditLine(cell_1.getStringCellValue());
			            }
		            }
		            importCreditLineM.setCreateBy("Scheduler");
		            importCreditLineM.setUpdateBy("Scheduler");
		            logger.debug("@@@@@ [cardNo="+importCreditLineM.getCardNo()+"][creditLine="+importCreditLineM.getCreditLine()+"]");
		            importCreditLineVect.add(importCreditLineM);
	        	}
	            count ++;
	        }
	        if(importCreditLineVect.size() > 0){
	        	return importCreditLineVect;
	        }else{
	        	return null;
	        }
		}catch (Exception e){
			e.printStackTrace();
			return null;
		}
	}
	
	public String createMessageCreditLine(PLResponseImportDataM importResponseM){
		logger.debug("@@@@@ now createMessageCreditLine");
		StringBuilder renderResult = new StringBuilder("");
		renderResult.append("<table align='center' width='100%'>");
			renderResult.append("<tr>");
				renderResult.append("<td algin='left' width='30%'><b>Total</b>&nbsp;</td>");
				renderResult.append("<td algin='left' width='70%'><b>"+importResponseM.getTotalRecord()+"</b></td>");
			renderResult.append("</tr>");
			renderResult.append("<tr>");
				renderResult.append("<td algin='left' width='30%'>Success&nbsp;</td>");
				renderResult.append("<td algin='left' width='70%'>"+importResponseM.getSuccessRecord()+"</td>");
			renderResult.append("</tr>");
			renderResult.append("<tr>");
				renderResult.append("<td algin='left' width='30%'>Reject&nbsp;</td>");
				renderResult.append("<td algin='left' width='70%'>"+importResponseM.getErrorRecord()+"</td>");
			renderResult.append("</tr>");
				if(importResponseM.getErrorImportVt()!=null && importResponseM.getErrorImportVt().size()>0){
			renderResult.append("<tr height='25'><td colspan='2'></td></tr>");
			renderResult.append("<tr>");
				renderResult.append("<td algin='left' colspan='2'>Please see reject details at attached file.</td>");
			renderResult.append("</tr>");
				}
			renderResult.append("</table>");
		return renderResult.toString();
	}
	
	public String createRejectFile(Vector<PLImportCreditLineDataM> errImportVect, String filePath){
		String rejectFileDir = filePath + File.separator + "Reject_Details.txt";
		logger.debug("@@@@@ create file :" + rejectFileDir);
		try{
			BufferedWriter output = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(rejectFileDir),"UTF8"));

			for (int i = 0; i < errImportVect.size(); i++) {
				PLImportCreditLineDataM errImportM = errImportVect.get(i);
				output.write(errImportM.getCardNo() + "," + errImportM.getCreditLine() + "," + DataFormatUtility.getThaiTextDescMessage(errImportM.getReason()));
				output.newLine();
			}
			output.close();
		}catch (Exception e){
			e.printStackTrace();
			return null;
		}
		return rejectFileDir;
	}
	
	private void delete(File file){
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
			if(file.getName().indexOf("approve_attach") < 0){
				file.delete();
				logger.debug("@@@@@ File is deleted : " + file.getAbsolutePath());
			}
		}
	}
	
	public void removeFileAceptAttachment(String filePath, String attachFileName){
		logger.debug("@@@@@ remove directory:" + filePath);
		try{
			File file = new File(filePath);
	    	//make sure directory exists
	    	if(!file.exists()){
	           logger.debug("@@@@@ Directory does not exist.");
	           //System.exit(0);
	        }else{
	        	if(file.isDirectory() && file.list().length > 0){
	        		delete(file);
	        	}else{
	        		//if file, then delete it
	    			if(file.getName().indexOf(attachFileName) < 0){
	    				file.delete();
	    				logger.debug("@@@@@ File is deleted : " + file.getAbsolutePath());
	    			}
	        	}
	        }
		}catch (Exception e){
			e.printStackTrace();
		}
	}
	
	public void deleteFile(String fileName){
		delete(new File(fileName));
	}
	
	public void deleteFile(File file){
		if(file != null && file.exists()){
			delete(file);
		}
	}
}
