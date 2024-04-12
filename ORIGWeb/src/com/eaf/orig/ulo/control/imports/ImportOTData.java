package com.eaf.orig.ulo.control.imports;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.Part;

import org.apache.log4j.Logger;
import org.apache.poi.hssf.usermodel.HSSFCellStyle;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import com.eaf.core.ulo.common.file.ImportControl;
import com.eaf.core.ulo.common.properties.SystemConfig;
import com.eaf.j2ee.pattern.control.ErrorController;
import com.eaf.orig.common.excel.ExcelGenerate;
import com.eaf.orig.model.ld.ImportOTDataM;
import com.eaf.orig.profile.model.UserDetailM;
import com.eaf.orig.ulo.app.dao.LookupDataFactory;
import com.eaf.orig.ulo.service.process.response.model.ProcessResponse;
import com.eaf.service.rest.model.ServiceResponse;
import com.google.gson.Gson;

public class ImportOTData extends ExcelGenerate implements ImportControl{
	private static transient Logger logger = Logger.getLogger(ImportOTData.class);
	private static final String USER_NO = "USER_NO";
	private static final String OT_POINT = "OT_POINT";
	private static final String ERROR = "ERROR";
	private static final String ERROR_DESC1 = "\u0e44\u0e21\u0e48\u0e1e\u0e1a\u0e23\u0e2b\u0e31\u0e2a\u0e1e\u0e19\u0e31\u0e01\u0e07\u0e32\u0e19\u0e19\u0e35\u0e49\u0e43\u0e19\u0e23\u0e30\u0e1a\u0e1a";
	private static final String ERROR_DESC2 = "\u0e23\u0e39\u0e1b\u0e41\u0e1a\u0e1a OT(\u0e15\u0e32\u0e21\u0e04\u0e33\u0e02\u0e2d Point) \u0e44\u0e21\u0e48\u0e16\u0e39\u0e01\u0e15\u0e49\u0e2d\u0e07";
	private static final String ERROR_DESC3 = "\u0E15\u0E49\u0E2D\u0E07\u0E23\u0E30\u0E1A\u0E38\u0E27\u0E31\u0E19\u0E17\u0E35\u0E48\u0E02\u0E2D\u0E07\u0E02\u0E49\u0E2D\u0E21\u0E39\u0E25";
	private static final String TEMPLATE = SystemConfig.getProperty("IMPORT_OT_DATA_TEMPLATE_PATH");
	private static final String OUTPUT = SystemConfig.getProperty("IMPORT_OT_DATA_OUTPUT_PATH");	
	@Override
	public String getFileLocation(HttpServletRequest request, String IMPORT_ID,	String FILE_NAME) throws Exception {
		return null;
	}
	@Override
	public ProcessResponse processImport(HttpServletRequest request, String IMPORT_ID, String FILE_NAME, String LOCATION) throws Exception {
		logger.debug("ImportOTData");
		ProcessResponse responseData = new ProcessResponse();
		String IMPORT_DATE = request.getParameter("IMPORT_DATE_CALENDAR");
		UserDetailM userM = (UserDetailM) request.getSession().getAttribute("ORIGUser");
		ImportOTDataM importOTDataM = new ImportOTDataM();
	    importOTDataM.setCreateDate(IMPORT_DATE);
	    importOTDataM.setCreateBy(userM.getUserName());
	    ArrayList<HashMap<String,String>> errorList = new ArrayList<>();
	    int success = 0;
		try{
			Part filePart = request.getPart("IMPORT_FILE");
			logger.debug("IMPORT_DATE : "+IMPORT_DATE);
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
	        CellStyle style = workbook.createCellStyle();
            style.setBorderBottom(HSSFCellStyle.BORDER_THIN);
            style.setBorderTop(HSSFCellStyle.BORDER_THIN);
            style.setBorderRight(HSSFCellStyle.BORDER_THIN);
            style.setBorderLeft(HSSFCellStyle.BORDER_THIN);
            
	        logger.debug("workbook.getSheetName : "+sheet.getSheetName());
	        int lastRow = sheet.getPhysicalNumberOfRows();
	        
	        List<String> userNoList = LookupDataFactory.getLookupDataDAO().getUserNo();
//	        Gson gson = new Gson();
//	        logger.debug("User M : "+gson.toJson(userM));
//	        logger.debug("Start read : "+gson.toJson(userNoList));
	        
	        LookupDataFactory.getLookupDataDAO().deleteUserOTPoint();
	        for(int i=1;i<lastRow;i++){
	        	Row x = sheet.getRow(i);
	        	if(x.getCell(getCell("A"),Row.CREATE_NULL_AS_BLANK).getCellType()!=Cell.CELL_TYPE_BLANK && x.getCell(getCell("B"),Row.CREATE_NULL_AS_BLANK).getCellType()!=Cell.CELL_TYPE_BLANK){
	        		Cell xCell = null;
	        		
	        		xCell = x.getCell(getCell("A"),Row.CREATE_NULL_AS_BLANK);
	        		xCell.setCellType(Cell.CELL_TYPE_STRING);
	        		String userNo = xCell.getStringCellValue();
	        		
	        		xCell = x.getCell(getCell("B"),Row.CREATE_NULL_AS_BLANK);
		        	xCell.setCellType(Cell.CELL_TYPE_STRING);
		        	String otPoint = xCell.getStringCellValue();
		        	
		        	
		        	if(IMPORT_DATE==null || "".equals(IMPORT_DATE)){
		        		HashMap<String,String> error = new HashMap<>();
		        		logger.debug("Date must be specified!");
		        		error.put(USER_NO, userNo);
		        		error.put(OT_POINT, otPoint);
		        		error.put(ERROR, ERROR_DESC3);
		        		errorList.add(error);
		        	}else if(userNoList.contains(userNo)){
			        	if(otPoint.matches("[0-9]+") && otPoint.charAt(0)!='0'){
			        		importOTDataM.setUserNo(userNo);
			        		importOTDataM.setPoint(otPoint);
			        		logger.debug("insert UserNo "+userNo+" data to table");
			        		LookupDataFactory.getLookupDataDAO().insertUserOTPoint(importOTDataM);
			        		success++;
			        	}else{
			        		HashMap<String,String> error = new HashMap<>();
			        		logger.debug("This UserNo "+userNo+" OT Point is not correct");
			        		error.put(USER_NO, userNo);
			        		error.put(OT_POINT, otPoint);
			        		error.put(ERROR, ERROR_DESC2);
			        		errorList.add(error);
			        	}
			        	
	        		}else{
	        			if(otPoint.matches("[0-9]+") && otPoint.charAt(0)!='0'){
	        				HashMap<String,String> error = new HashMap<>();
		        			logger.debug("UserNo "+userNo+" Not Found In Database");
		        			error.put(USER_NO, userNo);
			        		error.put(OT_POINT, otPoint);
			        		error.put(ERROR, ERROR_DESC1);
			        		errorList.add(error);
			        	}else{
		        			HashMap<String,String> error = new HashMap<>();
		        			logger.debug("UserNo "+userNo+" Not Found In Database");
		        			error.put(USER_NO, userNo);
			        		error.put(OT_POINT, otPoint);
			        		error.put(ERROR, ERROR_DESC1+","+ERROR_DESC2);
			        		errorList.add(error);
			        	}
	        		}
	        	}

	        }
	        logger.debug("ERROR LIST : "+errorList);
			createErrorListExcel(errorList);
			HashMap<String,Object> object = new HashMap<>(); 
			object.put("SUCCESS", success);
			object.put("ERROR_LIST", errorList.size());			
			responseData.setStatusCode(ServiceResponse.Status.SUCCESS);
			responseData.setData(new Gson().toJson(object));
		}catch(Exception e) {
			logger.fatal("ERROR",e);	
			responseData.setStatusCode(ServiceResponse.Status.SYSTEM_EXCEPTION);
			responseData.setErrorData(ErrorController.error(e));
		}
		return responseData;
	}		
	private void createErrorListExcel(ArrayList<HashMap<String, String>> errorList){
		createWorkbook(TEMPLATE);
		createBorder();
        Sheet sheet = workbook.getSheetAt(0);
        logger.debug("workbook.getSheetName : "+workbook.getSheetAt(0).getSheetName());
        int i = 1;
		for(HashMap<String,String>error : errorList){
			String userNo = error.get(USER_NO);
			String otPoint = error.get(OT_POINT);
			String errorDesc = error.get(ERROR);
			Row row = sheet.getRow(i++);
			Cell cell = null;
			cell = row.getCell(getCell("A"),Row.CREATE_NULL_AS_BLANK);
			cell.setCellValue(userNo);
			cell.setCellType(Cell.CELL_TYPE_STRING);
			cell.setCellStyle(style);
			cell = row.getCell(getCell("B"),Row.CREATE_NULL_AS_BLANK);
			cell.setCellValue(otPoint);
			cell.setCellType(Cell.CELL_TYPE_STRING);
			cell.setCellStyle(style);
			cell = row.getCell(getCell("C"),Row.CREATE_NULL_AS_BLANK);
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
