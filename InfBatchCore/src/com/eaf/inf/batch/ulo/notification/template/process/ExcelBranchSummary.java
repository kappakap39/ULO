package com.eaf.inf.batch.ulo.notification.template.process;

import java.io.FileNotFoundException;
import java.util.ArrayList;

import org.apache.poi.hssf.usermodel.HSSFCellStyle;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import com.eaf.core.ulo.common.cache.InfBatchProperty;
import com.eaf.core.ulo.common.excel.ExcelMapper;
import com.eaf.core.ulo.common.util.PerformanceController;
import com.eaf.inf.batch.ulo.notification.model.EmailBranchSummaryDataM;

public class ExcelBranchSummary extends ExcelMapper{
	private ArrayList<EmailBranchSummaryDataM> emailBranchSummarys = null;
    public ExcelBranchSummary(String inputFilePath, String outputFilePath, ArrayList<EmailBranchSummaryDataM> emailsSummarys) throws FileNotFoundException {
		super(inputFilePath, outputFilePath);
		this.emailBranchSummarys = emailsSummarys;
		fileType = getFileType(outputFilePath);
    }
    @Override
    protected void processAction() throws Exception{
		super.processAction();
		Sheet  sheet = workbook.createSheet();
		Row rowHeader = sheet.createRow(0);
		int headerIndex=0;
		CellStyle style = workbook.createCellStyle();
		style.setBorderBottom(HSSFCellStyle.BORDER_THIN);
		style.setBorderTop(HSSFCellStyle.BORDER_THIN);
		style.setBorderRight(HSSFCellStyle.BORDER_THIN);
		style.setBorderLeft(HSSFCellStyle.BORDER_THIN);

		setCellData(rowHeader, headerIndex++,InfBatchProperty.getInfBatchConfig("NOTIFICATION_EMAIL_SUMMARY_EXCEL_APP_NO_HEADER"),style);
		setCellData(rowHeader, headerIndex++,InfBatchProperty.getInfBatchConfig("NOTIFICATION_EMAIL_SUMMARY_EXCEL_CUST_NAME_HEADER"),style);
		setCellData(rowHeader, headerIndex++,InfBatchProperty.getInfBatchConfig("NOTIFICATION_EMAIL_SUMMARY_EXCEL_PRODUCT_HEADER"),style);
		setCellData(rowHeader, headerIndex++,InfBatchProperty.getInfBatchConfig("NOTIFICATION_EMAIL_SUMMARY_EXCEL_SELLER_HEADER"),style);
		setCellData(rowHeader, headerIndex++,InfBatchProperty.getInfBatchConfig("NOTIFICATION_EMAIL_SUMMARY_EXCEL_RECOMMENDER_HEADER"),style);
		setCellData(rowHeader, headerIndex++,InfBatchProperty.getInfBatchConfig("NOTIFICATION_EMAIL_SUMMARY_EXCEL_FINAL_DECISION_HEADER"),style);
		setCellData(rowHeader, headerIndex++,InfBatchProperty.getInfBatchConfig("NOTIFICATION_EMAIL_SUMMARY_EXCEL_REASON_HEADER"),style);
		setCellData(rowHeader, headerIndex++,InfBatchProperty.getInfBatchConfig("NOTIFICATION_EMAIL_SUMMARY_EXCEL_NOT_COMPLETE_DOC_HEADER"),style);
		setCellData(rowHeader, headerIndex++,InfBatchProperty.getInfBatchConfig("NOTIFICATION_EMAIL_SUMMARY_EXCEL_CUST_CONTACT_HEADER"),style);	
		
		if(null!=emailBranchSummarys && emailBranchSummarys.size()>0){
			for(int i=0;i<emailBranchSummarys.size();i++){
				EmailBranchSummaryDataM emailBranchSummary = emailBranchSummarys.get(i);
				Row row =    sheet.createRow(i+1);
				int cellIndex=0;
				setCellData(row, cellIndex++,emailBranchSummary.getApplicationNo(),null);
				setCellData(row, cellIndex++,emailBranchSummary.getCustomerFullName(),null);
				setCellData(row, cellIndex++,emailBranchSummary.getProductName(),null);
				setCellData(row, cellIndex++,emailBranchSummary.getSellerId(),null);
				setCellData(row, cellIndex++,emailBranchSummary.getRecommenderId(),null);
				setCellData(row, cellIndex++,emailBranchSummary.getFinalDecision(),null);
				setCellData(row, cellIndex++,emailBranchSummary.getRejectReason(),null);
				setCellData(row, cellIndex++,emailBranchSummary.getNotCompleteDocText(),null);
				setCellData(row, cellIndex++,emailBranchSummary.getCustomerPhoneNo(),null);
			}
		}
    }
    
     
    @Override
    protected void create() throws FileNotFoundException {
		PerformanceController performance = new PerformanceController();
		workbook = null;
		try{
			performance.create("Workbook");
			if(fileType.toLowerCase().endsWith("xlsx")) {
				workbook = new XSSFWorkbook();
			}else if(fileType.toLowerCase().endsWith("xls")){
				workbook = new HSSFWorkbook();
			}
			performance.end("Workbook");
		}catch(Exception e){
			throw new FileNotFoundException(e.getLocalizedMessage());
		}
				
    }
    
    private void setCellData(Row row , int cellIndex,String value,CellStyle style){
    	Cell cellData = row.createCell(cellIndex);
    	cellData.setCellValue(value);
    	if(null!=style){
    		cellData.setCellStyle(style);
    	}
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
