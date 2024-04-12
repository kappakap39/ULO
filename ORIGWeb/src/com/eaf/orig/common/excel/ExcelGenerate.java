package com.eaf.orig.common.excel;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;

import org.apache.log4j.Logger;
import org.apache.poi.hssf.usermodel.HSSFCellStyle;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

public class ExcelGenerate {
	private static transient Logger logger = Logger.getLogger(ExcelGenerate.class);
	public static Workbook workbook = null;
	public static FileInputStream fis = null;
	public static CellStyle style = null;
	
	public void createWorkbook(String TEMPLATE){
		try{
			fis = new FileInputStream(new File(TEMPLATE));
			if(TEMPLATE.toLowerCase().endsWith("xlsx")){
	            workbook = new XSSFWorkbook(fis);
	        }else if(TEMPLATE.toLowerCase().endsWith("xls")){
	            workbook = new HSSFWorkbook(fis);
	        }
		}catch(Exception e){
			logger.fatal("ERROR ",e);
		}
	}
	
	public void createBorder(){
		if(null != workbook){
			style = workbook.createCellStyle();
		    style.setBorderBottom(HSSFCellStyle.BORDER_THIN);
		    style.setBorderTop(HSSFCellStyle.BORDER_THIN);
		    style.setBorderRight(HSSFCellStyle.BORDER_THIN);
		    style.setBorderLeft(HSSFCellStyle.BORDER_THIN);
		}
	}
	
	public void writeFile(String OUTPUT){
		File deleteFile = new File(OUTPUT);
		if(deleteFile.exists()){
			deleteFile.delete();
		}
		
		try{
			workbook.write(new FileOutputStream(OUTPUT));
	    	workbook.close();
	    	if(null != fis){
	    		fis.close();
	    		logger.debug("close file");
	    	}
		}catch(Exception e){
			logger.fatal("ERROR ",e);
		}
	}
	
	public void deleteFile(String FILE_PATH){
		File deleteFile = new File(FILE_PATH);
		if(deleteFile.exists()){
			deleteFile.delete();
		}
	}
	
	public String convertFileType(String fileTypeHTML){
		String fileTypeJava = null;
		if(fileTypeHTML.equals("application/vnd.ms-excel")){
			fileTypeJava = "xls";
		}else{
			fileTypeJava = "xlsx";
		}
		return fileTypeJava;
	}
	
	public static int getCell(String column){
        int result = 0;
        for (int i = 0; i < column.length(); i++) {
            result *= 26;
            result += column.charAt(i) - 'A' + 1;
        }
        return result-1;
    }
}
