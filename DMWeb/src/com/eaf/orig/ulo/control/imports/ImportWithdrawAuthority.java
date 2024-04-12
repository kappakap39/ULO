package com.eaf.orig.ulo.control.imports;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.TreeMap;
import java.util.Vector;

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

import com.eaf.core.ulo.common.display.FormatUtil;
import com.eaf.core.ulo.common.display.HtmlUtil;
import com.eaf.core.ulo.common.file.ExcelUtil;
import com.eaf.core.ulo.common.file.ImportControl;
import com.eaf.core.ulo.common.model.ResponseData;
import com.eaf.core.ulo.common.properties.SystemConfig;
import com.eaf.core.ulo.common.util.Util;
import com.eaf.j2ee.pattern.control.ErrorController;
import com.eaf.j2ee.pattern.util.JSONUtil;
import com.eaf.orig.profile.model.UserDetailM;
import com.eaf.orig.ulo.app.factory.DMModuleFactory;
import com.eaf.orig.ulo.app.view.util.dih.DIHProxy;
import com.eaf.orig.ulo.app.view.util.dih.model.DIHQueryResult;
import com.eaf.orig.ulo.constant.MConstant;
import com.eaf.orig.ulo.dm.dao.DMImportExcelDAO;
import com.eaf.orig.ulo.service.process.response.model.ProcessResponse;
import com.eaf.service.rest.model.ServiceResponse;

public class ImportWithdrawAuthority implements ImportControl{
	private static transient Logger logger = Logger.getLogger(ImportWithdrawAuthority.class);	
	private static final String EMPLOYEE_ID_REJECT_REASON = "EMPLOYEE_ID_REJECT_REASON";
	
	@Override
	public ProcessResponse processImport(HttpServletRequest request, String IMPORT_ID,String FILE_NAME, String LOCATION) throws Exception {
		logger.debug("IMPORT_ID >> "+IMPORT_ID);
		logger.debug("FILE_NAME >> "+FILE_NAME);
		logger.debug("LOCATION >> "+LOCATION); 
		UserDetailM userM = (UserDetailM) request.getSession().getAttribute("ORIGUser");
		String response ="";
		ProcessResponse responseData = new ProcessResponse();
		try {
			DMImportExcelDAO dao = DMModuleFactory.getDMImportExcelDAO();
			DIHProxy dihProxy = new DIHProxy();
			ExcelUtil excelUtil  = new ExcelUtil();			
			HashMap<String, ArrayList<HashMap<String, Object>>>  readExcelList  = excelUtil.read(LOCATION,MConstant.EXCEL_ID.IMPORT_WITHDRAW_AUTHORITY,FILE_NAME);
			logger.debug("####readExcelList##"+readExcelList);			
			ArrayList<HashMap<String, Object>> excelDataList  = readExcelList.get("0");
			 
			ArrayList< Map<String, Object[]>> rejectList  = new ArrayList<Map<String, Object[]>>();
			
			ArrayList<String> createEmployeeAuthtList  = new ArrayList<String>();
			Vector vCheckDupData = new Vector();
			int countAll=0;
			if(!Util.empty(excelDataList)){	
				logger.debug("excelDataList : "+excelDataList.size());
				for(int i =1;i< excelDataList.size()-1;i++){
					HashMap<String, Object> excelData = excelDataList.get(i);
					String employeeId = (String)excelData.get("EMPLOYEE_ID");
					Map<String, Object[]> hReject = new TreeMap<String, Object[]>();
					logger.debug("####"+excelData);
					DIHQueryResult<String> queryResult = dihProxy.existUserNo(employeeId);
					String existUserNo="";
					if(ResponseData.SUCCESS.equals(queryResult.getStatusCode())){
						existUserNo = queryResult.getResult();
					}
										
					if(!Util.empty(employeeId)){
						countAll++;
						if(!Util.number(employeeId)){
							hReject.put(EMPLOYEE_ID_REJECT_REASON, new Object[] {employeeId, MConstant.EXCEL_REJECT_REASON.INCORRECT_ID_FORMAT});
							rejectList.add(hReject);
						}else if(vCheckDupData.contains(employeeId.trim())){
							hReject.put(EMPLOYEE_ID_REJECT_REASON, new Object[] {employeeId, MConstant.EXCEL_REJECT_REASON.ID_DUPLICATE});
							if(createEmployeeAuthtList.contains(employeeId.trim())){
								rejectList.add(hReject);
							}
						}else if(!MConstant.FLAG_Y.equals(existUserNo)){
							hReject.put(EMPLOYEE_ID_REJECT_REASON, new Object[] {employeeId, MConstant.EXCEL_REJECT_REASON.ID_NOT_FOUND});
							rejectList.add(hReject);						
						}else{
							createEmployeeAuthtList.add(employeeId);
						}
						vCheckDupData.add(employeeId.trim());
					}
				}
			}
			
			logger.debug("createEmployeeAuthtList : "+createEmployeeAuthtList);
			logger.debug("countAll : "+countAll);
			if(!Util.empty(createEmployeeAuthtList) && createEmployeeAuthtList.size()==countAll){
				dao.deleteAllDmWithdrawalAuthEmployee();
				for(String employee :createEmployeeAuthtList){
					dao.createDmWithdrawalAuth(employee,userM.getUserName());
				}
			}
			
			logger.debug("rejectList : "+rejectList);
			if(!Util.empty(rejectList)){
				try {
					createRejectList(rejectList,FILE_NAME);
				} catch (Exception e) {
					logger.debug("ERROR",e);
				}
			}
			
			int rejectSize = rejectList.size();
			JSONUtil json = new JSONUtil();	
			json.put("DM_TOTAL",FormatUtil.toString(countAll));
			json.put("DM_SUCCESS", !Util.empty(createEmployeeAuthtList)?FormatUtil.toString(createEmployeeAuthtList.size()):"0");
			json.put("DM_REJECT", !Util.empty(rejectSize)?FormatUtil.toString(rejectSize):"0");
			json.put("DM_EXPORT_REJECT",!Util.empty(rejectSize)?getLinkForDownload(request,FILE_NAME):"");
			response = json.getJSON();
			responseData.setStatusCode(ServiceResponse.Status.SUCCESS);
			responseData.setData(response);
		} catch (Exception e) {
			logger.fatal("ERROR",e);	
			responseData.setStatusCode(ServiceResponse.Status.SYSTEM_EXCEPTION);
			responseData.setErrorData(ErrorController.error(e));
		}		
		logger.debug(">>>response>>"+response);
		return responseData;
	}

	private String getLinkForDownload(HttpServletRequest request,String FILE_NAME){
		String linkDownLoad="";
		if(!Util.empty(FILE_NAME)){
			String param ="DOWNLOAD_ID="+MConstant.DOWNLOAD.DOWNLOAD_WITHDRAW_AUTHORITY_EXCEL+"&FILE_NAME="+FILE_NAME;
			linkDownLoad ="<img class='imginbox' src='images/ulo/exel.png'></img>"+HtmlUtil.hyperlink("DM_EXPORT_REJECT", FILE_NAME, request.getContextPath()+"/DownloadServlet?"+param, "", HtmlUtil.EDIT, "",request);
		}
		return linkDownLoad;
	}
	
	@Override
	public String getFileLocation(HttpServletRequest request, String IMPORT_ID,String FILE_NAME) throws Exception {		
		return null;
	}
	//create reject list file
	private void createRejectList(ArrayList<Map<String, Object[]>> rejectList,String FILE_NAME) {
		String templateFile = SystemConfig.getProperty("DM_WITHDRAW_AUTHORITY_TEMPLATE_PATH");
		String outputFile = SystemConfig.getProperty("DM_WITHDRAW_AUTHORITY_DOWLOAD_PATH")+FILE_NAME;
		try{
			File outputPath = new File(SystemConfig.getProperty("DM_WITHDRAW_AUTHORITY_DOWLOAD_PATH"));
			if(!outputPath.exists()){
				outputPath.mkdirs();
			}
			File deleteFile = new File(outputFile);
			if(deleteFile.exists()){
				deleteFile.delete();
			}
			logger.debug("rejectList.size >> "+rejectList.size());
			logger.debug("templateFile >> "+templateFile);
			logger.debug("outputFile >> "+outputFile);
			FileInputStream fis = new FileInputStream(new File(templateFile));
			Workbook workbook = null;
            workbook = new HSSFWorkbook(fis);
            CellStyle style = workbook.createCellStyle();
            style.setBorderBottom(HSSFCellStyle.BORDER_THIN);
            style.setBorderTop(HSSFCellStyle.BORDER_THIN);
            style.setBorderRight(HSSFCellStyle.BORDER_THIN);
            style.setBorderLeft(HSSFCellStyle.BORDER_THIN);
            
	        Sheet sheet = workbook.getSheetAt(0);
	        logger.debug("workbook.getSheetName : "+workbook.getSheetAt(0).getSheetName());
	        int rownum = 1;
	        for(Map<String, Object[]> mReject : rejectList){
	        	  Object [] rejectArr = mReject.get(EMPLOYEE_ID_REJECT_REASON);
	              int cellnum = 0;
	              Row row = sheet.createRow(rownum++);
	              for (Object rejectData : rejectArr)
	              {
	                 Cell cell = row.createCell(cellnum++);
	                 cell.setCellValue((String)rejectData);
	                 cell.setCellType(Cell.CELL_TYPE_STRING);
	     			 cell.setCellStyle(style);
	              }
	        	
	        }
	//     	Write Excel
        	workbook.write(new FileOutputStream(outputFile));
        	workbook.close();
        	if(null != fis){
        		fis.close();
        	}
        	logger.debug("close file");
		}catch(Exception e){
			logger.fatal("ERROR ",e);
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
