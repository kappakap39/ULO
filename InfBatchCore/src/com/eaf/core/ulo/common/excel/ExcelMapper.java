package com.eaf.core.ulo.common.excel;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;

import org.apache.log4j.Logger;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import com.eaf.core.ulo.common.util.PerformanceController;

/**
 * Excel Mapper -- Make mapping excel is easier<br/>
 * You must extend this Class to do mapping by override <b>"mapItNow"</b> method
 * 
 * @author Norrapat Nimmanee
 * @version initial
 */
public class ExcelMapper {
	protected transient Logger logger = Logger.getLogger(this.getClass());
	protected String inputFilePath;
	protected String outputFilePath;
	protected String fileType;
	protected InputStream inputStream;
	protected Workbook workbook;
	public ExcelMapper(String inputFilePath, String outputFilePath) {
		this.inputFilePath = inputFilePath;
		this.outputFilePath = outputFilePath;
		this.fileType = getFileType(inputFilePath);
		logger.debug("Template File : " + inputFilePath);
		logger.debug("Output File : " + outputFilePath);
		logger.debug("File Type : " + fileType);
	}
	public boolean export() throws Exception {
		init();
		processAction();
		return write();
	}
	public boolean init() throws Exception {
		try{
			create();
		}catch(FileNotFoundException e){
			logger.fatal("ERROR",e);
			return false;
		}
		return true;
	}
	protected void processAction()throws Exception{
		
	}
	protected void create() throws FileNotFoundException {
		PerformanceController performance = new PerformanceController();
		performance.create("FileInputStream");
		inputStream = new BufferedInputStream(new FileInputStream(new File(inputFilePath)));
		performance.end("FileInputStream");
		workbook = null;
		try{
			performance.create("Workbook");
			if(fileType.toLowerCase().endsWith("xlsx")) {
				workbook = new XSSFWorkbook(inputStream);
			}else if(fileType.toLowerCase().endsWith("xls")){
				workbook = new HSSFWorkbook(inputStream);
			}
			performance.end("Workbook");
		}catch(IOException e){
			logger.fatal("ERROR",e);
		}
	}
	protected boolean write() {
		try{
			PerformanceController performance = new PerformanceController();
			performance.create("FileOutputStream");
			workbook.write(new FileOutputStream(outputFilePath));
			performance.end("FileOutputStream");
			close();
		}catch(IOException e){
			logger.error("ERROR", e);
			return false;
		}
		return true;
	}
	protected boolean close() {
		try{
			workbook.close();
			if (null != inputStream) {
				inputStream.close();
			}
		}catch(IOException e){
			logger.error("ERROR",e);
		}
		return true;
	}
	private String getFileType(String location) {
		String extension = "";
		int i = location.lastIndexOf('.');
		if (i > 0) {
			extension = location.substring(i + 1);
		}
		return extension;
	}
}
