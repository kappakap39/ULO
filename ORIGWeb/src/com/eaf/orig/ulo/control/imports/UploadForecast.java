package com.eaf.orig.ulo.control.imports;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.Part;

import org.apache.log4j.Logger;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import com.eaf.core.ulo.common.file.ImportControl;
import com.eaf.core.ulo.common.properties.SystemConfig;
import com.eaf.j2ee.pattern.control.ErrorController;
import com.eaf.orig.common.excel.ExcelGenerate;
import com.eaf.orig.model.ld.UploadForecastM;
import com.eaf.orig.profile.model.UserDetailM;
import com.eaf.orig.ulo.app.dao.LookupDataFactory;
import com.eaf.orig.ulo.service.process.response.model.ProcessResponse;
import com.eaf.service.rest.model.ServiceResponse;
import com.google.gson.Gson;

public class UploadForecast extends ExcelGenerate implements ImportControl{
	private static transient Logger logger = Logger.getLogger(UploadForecast.class);
	private static final String DATE = "DAY";
	private static final String MONTH = "MONTH";
	private static final String FORECAST = "FORECAST";
	private static final String ERROR = "ERROR";	
	private static final String ERROR_DESC1 = "\u0e02\u0e49\u0e2d\u0e21\u0e39\u0e25\u0e27\u0e31\u0e19\u0e44\u0e21\u0e48\u0e16\u0e39\u0e01\u0e15\u0e49\u0e2d\u0e07";
	private static final String ERROR_DESC2 = "\u0e02\u0e49\u0e2d\u0e21\u0e39\u0e25\u0e40\u0e14\u0e37\u0e2d\u0e19\u0e44\u0e21\u0e48\u0e16\u0e39\u0e01\u0e15\u0e49\u0e2d\u0e07";
	private static final String ERROR_DESC3 = "\u0e23\u0e39\u0e1b\u0e41\u0e1a\u0e1a (Date) \u0e44\u0e21\u0e48\u0e16\u0e39\u0e01\u0e15\u0e49\u0e2d\u0e07";
	private static final String ERROR_DESC4 = "\u0e23\u0e39\u0e1b\u0e41\u0e1a\u0e1a (Forecast App In) \u0e44\u0e21\u0e48\u0e16\u0e39\u0e01\u0e15\u0e49\u0e2d\u0e07";
	private static final String TEMPLATE = SystemConfig.getProperty("FORECAST_ERROR_TEMPLATE_PATH");
	private static final String OUTPUT = SystemConfig.getProperty("FORECAST_ERROR_OUTPUT_PATH");	
	@Override
	public String getFileLocation(HttpServletRequest request, String IMPORT_ID, String FILE_NAME) throws Exception {
		return null;
	}
	@Override
	public ProcessResponse processImport(HttpServletRequest request, String IMPORT_ID, String FILE_NAME, String LOCATION) throws Exception {
		logger.debug("UploadForecast");
		ProcessResponse responseData = new ProcessResponse();
		UserDetailM userM = (UserDetailM) request.getSession().getAttribute("ORIGUser");
		UploadForecastM uploadForecastM = new UploadForecastM();
		uploadForecastM.setCreateBy(userM.getUserName());
	    ArrayList<HashMap<String,String>> errorList = new ArrayList<>();
	    int success = 0;
		try{
			Part filePart = request.getPart("IMPORT_FILE");
			logger.debug("filePart : "+filePart);			
			InputStream fileInput = filePart.getInputStream();
			String fileType = filePart.getContentType();
			String fileName = filePart.getName();
			logger.debug("fileInput : "+fileInput);
			logger.debug("fileType : "+fileType);
			logger.debug("fileName : "+fileName);			
			String templateFileType = convertFileType(fileType);			
			Workbook workbook = null;
	        if(templateFileType.toLowerCase().endsWith("xlsx")){
	            workbook = new XSSFWorkbook(fileInput);
	        }else if(templateFileType.toLowerCase().endsWith("xls")){
	            workbook = new HSSFWorkbook(fileInput);
	        }	        
	        Sheet sheet = workbook.getSheetAt(0);
	        logger.debug("workbook.getSheetName : "+sheet.getSheetName());
	        int lastRow = sheet.getPhysicalNumberOfRows();	        
	        List<String> userNoList = LookupDataFactory.getLookupDataDAO().getUserNo();
//	        Gson gson = new Gson();
//	        logger.debug("User M : "+gson.toJson(userM));
//	        logger.debug("Start read : "+gson.toJson(userNoList));	        
	        LookupDataFactory.getLookupDataDAO().deleteUploadForecast();
	        for(int i=1;i<lastRow;i++){
	        	Row x = sheet.getRow(i);
	        	if(x.getCell(getCell("A"),Row.CREATE_NULL_AS_BLANK).getCellType()!=Cell.CELL_TYPE_BLANK && x.getCell(getCell("B"),Row.CREATE_NULL_AS_BLANK).getCellType()!=Cell.CELL_TYPE_BLANK){
	        		Cell xCell = null;
	        		
	        		xCell = x.getCell(getCell("A"),Row.CREATE_NULL_AS_BLANK);
	        		xCell.setCellType(Cell.CELL_TYPE_STRING);
	        		String forecastDay = xCell.getStringCellValue();
	        		
	        		xCell = x.getCell(getCell("B"),Row.CREATE_NULL_AS_BLANK);
		        	xCell.setCellType(Cell.CELL_TYPE_STRING);
		        	String forecastMonth = xCell.getStringCellValue();
		        	
		        	xCell = x.getCell(getCell("C"),Row.CREATE_NULL_AS_BLANK);
		        	xCell.setCellType(Cell.CELL_TYPE_STRING);
		        	String forecastAppIn = xCell.getStringCellValue();
		        	
	        		if(forecastMonth.matches("[1-9]+")){
	        			if(Integer.parseInt(forecastMonth)<=12){
				        	if(forecastDay.matches("[0-9]+")){
				        		if(Integer.parseInt(forecastDay)<=31){
				        			if(forecastAppIn.matches("[0-9]+")){
					        			uploadForecastM.setForecastDay(forecastDay);
					        			uploadForecastM.setForecastMonth(forecastMonth);
					        			uploadForecastM.setForecastAppIn(forecastAppIn);
						        		logger.debug("insert forecastDay "+forecastDay+" data to table");
						        		LookupDataFactory.getLookupDataDAO().insertUploadForecast(uploadForecastM);
						        		success++;
				        			}else{
				        				HashMap<String,String> error = new HashMap<>();
						        		logger.debug("This forecastDay "+forecastDay+" OT Point is not correct");
						        		error.put(DATE, forecastDay);
						        		error.put(MONTH, forecastMonth);
						        		error.put(FORECAST, forecastAppIn);
						        		error.put(ERROR, ERROR_DESC4);
						        		errorList.add(error);
				        			}
				        		}else{
				        			HashMap<String,String> error = new HashMap<>();
					        		logger.debug("This forecastDay "+forecastDay+" OT Point is not correct");
					        		error.put(DATE, forecastDay);
					        		error.put(MONTH, forecastMonth);
					        		error.put(FORECAST, forecastAppIn);
					        		error.put(ERROR, ERROR_DESC1);
					        		errorList.add(error);
				        		}
				        	}else{
				        		HashMap<String,String> error = new HashMap<>();
				        		logger.debug("This forecastDay "+forecastDay+" OT Point is not correct");
				        		error.put(DATE, forecastDay);
				        		error.put(MONTH, forecastMonth);
				        		error.put(FORECAST, forecastAppIn);
				        		error.put(ERROR, ERROR_DESC3);
				        		errorList.add(error);
				        	}
	        			}else{
	        				HashMap<String,String> error = new HashMap<>();
			        		logger.debug("This forecastDay "+forecastDay+" OT Point is not correct");
			        		error.put(DATE, forecastDay);
			        		error.put(MONTH, forecastMonth);
			        		error.put(FORECAST, forecastAppIn);
			        		error.put(ERROR, ERROR_DESC2);
			        		errorList.add(error);
	        			}
	        		}else{
	        			HashMap<String,String> error = new HashMap<>();
	        			logger.debug("This forecastDay "+forecastDay+" OT Point is not correct");
	        			error.put(DATE, forecastDay);
		        		error.put(MONTH, forecastMonth);
		        		error.put(FORECAST, forecastAppIn);
		        		error.put(ERROR, ERROR_DESC3);
		        		errorList.add(error);
	        		}
	        	}
	        }
	        logger.debug("ERROR LIST : "+errorList);
			createErrorListExcel(errorList);
			HashMap<String,Object> object = new HashMap<>(); 
			object.put("SUCCESS", success);
			object.put("ERROR_LIST", errorList);			
			responseData.setStatusCode(ServiceResponse.Status.SUCCESS);
			responseData.setData(new Gson().toJson(object));
		}catch(Exception e) {
			logger.fatal("ERROR",e);	
			responseData.setStatusCode(ServiceResponse.Status.SYSTEM_EXCEPTION);
			responseData.setErrorData(ErrorController.error(e));
		}
		return responseData;
	}
	
	private void createErrorListExcel(ArrayList<HashMap<String, String>> errorList) {
		createWorkbook(TEMPLATE);
		createBorder();
        Sheet sheet = workbook.getSheetAt(0);
        logger.debug("workbook.getSheetName : "+workbook.getSheetAt(0).getSheetName());
        int i = 1;
		for(HashMap<String,String>error : errorList){
			String date = error.get(DATE);
			String month = error.get(MONTH);
			String forecast = error.get(FORECAST);
			String errorDesc = error.get(ERROR);
			Row row = sheet.getRow(i++);
			Cell cell = null;
			cell = row.getCell(getCell("A"),Row.CREATE_NULL_AS_BLANK);
			cell.setCellValue(date);
			cell.setCellType(Cell.CELL_TYPE_STRING);
			cell.setCellStyle(style);
			cell = row.getCell(getCell("B"),Row.CREATE_NULL_AS_BLANK);
			cell.setCellValue(month);
			cell.setCellType(Cell.CELL_TYPE_STRING);
			cell.setCellStyle(style);
			cell = row.getCell(getCell("C"),Row.CREATE_NULL_AS_BLANK);
			cell.setCellValue(forecast);
			cell.setCellType(Cell.CELL_TYPE_STRING);
			cell.setCellStyle(style);
			cell = row.getCell(getCell("D"),Row.CREATE_NULL_AS_BLANK);
			cell.setCellValue(errorDesc);
			cell.setCellType(Cell.CELL_TYPE_STRING);
			cell.setCellStyle(style);
		}
	    writeFile(OUTPUT);
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
