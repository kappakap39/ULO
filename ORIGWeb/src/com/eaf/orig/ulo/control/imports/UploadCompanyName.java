package com.eaf.orig.ulo.control.imports;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.HashMap;

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
import com.eaf.core.ulo.common.properties.SystemConstant;
import com.eaf.core.ulo.common.util.Util;
import com.eaf.j2ee.pattern.control.ErrorController;
import com.eaf.orig.common.excel.ExcelGenerate;
import com.eaf.orig.model.ld.UploadCompanyNameM;
import com.eaf.orig.profile.model.UserDetailM;
import com.eaf.orig.ulo.app.dao.LookupDataFactory;
import com.eaf.orig.ulo.service.process.response.model.ProcessResponse;
import com.eaf.service.rest.model.ServiceResponse;
import com.google.gson.Gson;

public class UploadCompanyName extends ExcelGenerate implements ImportControl{
	private static transient Logger logger = Logger.getLogger(UploadForecast.class);
	private static final String NAME = "NAME";
	private static final String ERROR = "ERROR";
	private static final String INCORRECT = "\u0e02\u0e49\u0e2d\u0e21\u0e39\u0e25\u0e44\u0e21\u0e48\u0e16\u0e39\u0e01\u0e15\u0e49\u0e2d\u0e07";
	private static final String TEMPLATE = SystemConfig.getProperty("COMPANY_NAME_ERROR_TEMPLATE_PATH");
	private static final String OUTPUT = SystemConfig.getProperty("COMPANY_NAME_ERROR_OUTPUT_PATH");
	private static final int COMPANY_NAME_MAX_LENGTH = Integer.parseInt(SystemConstant.getConstant("COMPANY_NAME_MAX_LENGTH"));
	@Override
	public String getFileLocation(HttpServletRequest request, String IMPORT_ID, String FILE_NAME) throws Exception {
		return null;
	}
	@Override
	public ProcessResponse processImport(HttpServletRequest request, String IMPORT_ID, String FILE_NAME, String LOCATION) throws Exception {
		logger.debug("UploadCompanyName");
		ProcessResponse responseData = new ProcessResponse();
		UserDetailM userM = (UserDetailM) request.getSession().getAttribute("ORIGUser");
		UploadCompanyNameM uploadCompanyNameM = new UploadCompanyNameM();
		uploadCompanyNameM.setCreateBy(userM.getUserName());
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
	        LookupDataFactory.getLookupDataDAO().deleteUploadCompanyName();
	        for(int i=1;i<lastRow;i++){
	        	Row x = sheet.getRow(i);
	        	if(x.getCell(getCell("A"),Row.CREATE_NULL_AS_BLANK).getCellType()!=Cell.CELL_TYPE_BLANK){
	        		Cell xCell = null;
	        		xCell = x.getCell(getCell("A"),Row.CREATE_NULL_AS_BLANK);
	        		xCell.setCellType(Cell.CELL_TYPE_STRING);
	        		String companyName = xCell.getStringCellValue();
		        	logger.debug("company length >> "+companyName.length());
	        		if(COMPANY_NAME_MAX_LENGTH < companyName.length()){
	        			HashMap<String, String> data = new HashMap<>();
	        			data.put(NAME, companyName);
	        			data.put(ERROR, INCORRECT);
	        			errorList.add(data);
	        		}else{
	        			uploadCompanyNameM.setCompanyId(String.valueOf(++success));
	        			uploadCompanyNameM.setCompanyName(companyName);
	        			LookupDataFactory.getLookupDataDAO().insertUploadCompanyName(uploadCompanyNameM);
	        		}
	        	}else{
	        		logger.debug("CELL BLANK!");
	        		HashMap<String, String> data = new HashMap<>();
        			data.put(NAME, "");
        			data.put(ERROR, INCORRECT);
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
	private void createErrorListExcel(ArrayList<HashMap<String, String>> errorList){
		createWorkbook(TEMPLATE);
		createBorder();
        Sheet sheet = workbook.getSheetAt(0);
        logger.debug("workbook.getSheetName : "+workbook.getSheetAt(0).getSheetName());
        if(!Util.empty(errorList)){
	        int i = 1;
    		for(HashMap<String,String>error : errorList){
    			String companyName = error.get(NAME);
    			String errorDesc = error.get(ERROR);
    			Row row = sheet.getRow(i++);
    			Cell cell = null;
    			cell = row.getCell(getCell("A"),Row.CREATE_NULL_AS_BLANK);
    			cell.setCellValue(companyName);
    			cell.setCellType(Cell.CELL_TYPE_STRING);
    			cell.setCellStyle(style);
    			cell = row.getCell(getCell("B"),Row.CREATE_NULL_AS_BLANK);
    			cell.setCellValue(errorDesc);
    			cell.setCellType(Cell.CELL_TYPE_STRING);
    			cell.setCellStyle(style);
    		}
    		writeFile(OUTPUT);
        }else{
        	deleteFile(OUTPUT);
        }
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
