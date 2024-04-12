package com.eaf.inf.batch.ulo.report.excel;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;

import org.apache.log4j.Logger;
import org.apache.poi.hssf.usermodel.HSSFFormulaEvaluator;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.IndexedColors;

import com.eaf.core.ulo.common.cache.InfBatchProperty;
import com.eaf.core.ulo.common.cont.InfBatchConstant;
import com.eaf.core.ulo.common.display.Formatter;
import com.eaf.core.ulo.common.excel.ExcelMapper;
import com.eaf.core.ulo.common.excel.MySheet;
import com.eaf.core.ulo.common.util.InfBatchUtil;
import com.eaf.inf.batch.ulo.report.dao.ReportFileFactory;
import com.eaf.inf.batch.ulo.report.model.InfReportJobDataM;
import com.eaf.inf.batch.ulo.report.model.OperatingResultDataM;
import com.eaf.inf.batch.ulo.report.model.AuditTrailDataM;

public class MapperReR021 extends ExcelMapper{
	private static transient Logger logger = Logger.getLogger(MapperReR021.class);
	private ArrayList<AuditTrailDataM> reR021List = null;
	private String P_AS_OF_DATE = null;
	InfReportJobDataM infReportJob = null;
	private void initAction(){
		try {
		} catch (Exception e) {
			logger.fatal("ERROR ",e);
		}
	}
	
	public MapperReR021(String inputFilePath, String outputFilePath, String systemDate,ArrayList<AuditTrailDataM> reR021) {
		super(inputFilePath, outputFilePath);
		this.reR021List = reR021;
		this.P_AS_OF_DATE = systemDate;
	}
	
	public MapperReR021(String inputFilePath, String outputFilePath, String systemDate, InfReportJobDataM infReportJob,ArrayList<AuditTrailDataM> reR021) {
		super(inputFilePath, outputFilePath);
		this.reR021List = reR021;
		this.P_AS_OF_DATE = systemDate;
		this.infReportJob = infReportJob;
	}

	@Override
	protected void processAction() throws Exception{
		super.processAction();
		initAction();
		MySheet sheet = new MySheet(workbook.getSheet(InfBatchConstant.ReportParam.LAYOUT));
		logger.debug("P_AS_OF_DATE: "+P_AS_OF_DATE);
		DateFormat dateFormat = new SimpleDateFormat("HH:mm:ss");
		SimpleDateFormat formatter = new SimpleDateFormat(Formatter.Format.yyyy_MM_dd);
		Date date = new Date();
		String DATE = Formatter.display(InfBatchProperty.getDate(), Formatter.EN, Formatter.Format.ddMMyyyy);
		String TIME = dateFormat.format(date);
		String DATE_TIME = DATE+" "+TIME;
		Date asOfDate = formatter.parse(P_AS_OF_DATE);
		sheet.setCellValue(2, "J", DATE_TIME);
		
		String DATE_HEADER = InfBatchConstant.message.AS_OF_DATE_HEADER_REPORT;
		if(InfBatchUtil.empty(infReportJob)){
			String DATE_EXECUTE = Formatter.display(asOfDate, Formatter.EN, Formatter.Format.MMMMddyyyy);
			DATE_HEADER = DATE_HEADER.replace("{AS_OF_DATE}", DATE_EXECUTE);
		}else{
			DATE_HEADER = DATE_HEADER.replace("{AS_OF_DATE}", MapperReR006.getHeaderDate(infReportJob.getDateFrom()));
		}
		sheet.setCellValue(2, "A", DATE_HEADER);
		sheet = new MySheet(workbook.getSheet(InfBatchConstant.ReportParam.LAYOUT));
		CellStyle centerAligned = workbook.createCellStyle();
		centerAligned.setAlignment(CellStyle.ALIGN_CENTER);
		centerAligned.setBorderBottom(CellStyle.BORDER_THIN);
		centerAligned.setBottomBorderColor(IndexedColors.BLACK.getIndex());
	    centerAligned.setBorderLeft(CellStyle.BORDER_THIN);
	    centerAligned.setLeftBorderColor(IndexedColors.BLACK.getIndex());
	    centerAligned.setBorderRight(CellStyle.BORDER_THIN);
	    centerAligned.setRightBorderColor(IndexedColors.BLACK.getIndex());
	    centerAligned.setBorderTop(CellStyle.BORDER_THIN);
	    centerAligned.setTopBorderColor(IndexedColors.BLACK.getIndex());
		CellStyle leftAligned = workbook.createCellStyle();
		leftAligned.setAlignment(CellStyle.ALIGN_LEFT);
		leftAligned.setBorderBottom(CellStyle.BORDER_THIN);
		leftAligned.setBottomBorderColor(IndexedColors.BLACK.getIndex());
		leftAligned.setBorderLeft(CellStyle.BORDER_THIN);
		leftAligned.setLeftBorderColor(IndexedColors.BLACK.getIndex());
		leftAligned.setBorderRight(CellStyle.BORDER_THIN);
		leftAligned.setRightBorderColor(IndexedColors.BLACK.getIndex());
		leftAligned.setBorderTop(CellStyle.BORDER_THIN);
		leftAligned.setTopBorderColor(IndexedColors.BLACK.getIndex());
		logger.debug("RE_R021 Data Size: "+reR021List.size());
		for(int row=6,count=0;count<reR021List.size();row++,count++){
				sheet.setCellValue(sheet, row, "A",String.valueOf(count+1) ,0,centerAligned);
				sheet.setCellValue(sheet, row, "B", reR021List.get(count).getApplicationGroupNo(),1,centerAligned);
				sheet.setCellValue(sheet, row, "C", reR021List.get(count).getFieldName(),2,leftAligned);
				sheet.setCellValue(sheet, row, "D", reR021List.get(count).getOldValue(),3,leftAligned);
				sheet.setCellValue(sheet, row, "E", reR021List.get(count).getNewValue(),4,leftAligned);
				sheet.setCellValue(sheet, row, "F", reR021List.get(count).getUpdateDate(),5,centerAligned);
				sheet.setCellValue(sheet, row, "G", reR021List.get(count).getCreateBy(),6,leftAligned);
				sheet.setCellValue(sheet, row, "H", reR021List.get(count).getCreateRole(),7,centerAligned);
				sheet.setCellValue(sheet, row, "I", reR021List.get(count).getUpdateBy(),8,leftAligned);
				sheet.setCellValue(sheet, row, "J", reR021List.get(count).getRole(),9,centerAligned);
			}
		
		HSSFFormulaEvaluator.evaluateAllFormulaCells(workbook);
	}
}
