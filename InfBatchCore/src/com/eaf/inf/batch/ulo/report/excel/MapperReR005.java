package com.eaf.inf.batch.ulo.report.excel;

import java.math.BigDecimal;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.HashMap;

import org.apache.log4j.Logger;
import org.apache.poi.hssf.usermodel.HSSFFormulaEvaluator;

import com.eaf.core.ulo.common.cont.InfBatchConstant;
import com.eaf.core.ulo.common.excel.ExcelMapper;
import com.eaf.core.ulo.common.excel.MySheet;
import com.eaf.core.ulo.common.util.InfBatchUtil;
import com.eaf.inf.batch.ulo.orsum.dao.ORSummaryDAOImpl;
import com.eaf.inf.batch.ulo.report.dao.ReportFileFactory;
import com.eaf.inf.batch.ulo.report.model.OverrideResultDataM;


public class MapperReR005 extends ExcelMapper {
	private static transient Logger logger = Logger.getLogger(MapperReR005.class);

	private HashMap<String , ArrayList<OverrideResultDataM>> reR005HashMap = null;
	private String REPORT_ID = null;
	private int REPORT_TYPE;
	private Date asOfDate;
	private String product;
	
	private void initAction(){
		REPORT_TYPE = 1;
		reR005HashMap = new HashMap<String , ArrayList<OverrideResultDataM>>();
		try {
			reR005HashMap = ReportFileFactory.getReportFileDAO().getOverrideResult((java.sql.Date)asOfDate, product);
		}
		catch (Exception e) {
			logger.fatal("ERROR ",e);
		}
	}
	
	public MapperReR005(String inputFilePath, String outputFilePath, String REPORT_ID, Date asOfDate, String product) {
		super(inputFilePath, outputFilePath);
		this.REPORT_ID = REPORT_ID;
		this.asOfDate = asOfDate;
		this.product = product;
	}
	
	private void prepareMonthLabel(MySheet sheet, Date asOfDate) {
		String excelColumn = "ABCDEFGHIJKL";
		SimpleDateFormat df = new SimpleDateFormat("MMM yyyy");
		for (int i = 0; i < 12; i++) {
			Date date = ORSummaryDAOImpl.dateFromReferenceDate(asOfDate, false, true, 1, true, i, false, 0);
			sheet.setCellValue(1, i+1, df.format(date));
		}
	}
	
	
	private void prepareOrData(MySheet sheet, int startRow, String product) {
		ArrayList<OverrideResultDataM> product_or = reR005HashMap.get(product);
		if (product_or != null && product_or.size() > 0) {
			for(int i=0; i < product_or.size(); i++){
				OverrideResultDataM or_data = product_or.get(i);
				String[] period = or_data.getPeriod().split("-");
				int row = startRow + Integer.valueOf(period[1]) - 1;
				sheet.setCellValue(row, "A", or_data.getPeriod());
				sheet.setCellValue(row, "B",  new BigDecimal(or_data.getTotalAppIn()));
				sheet.setCellValue(row, "C",  new BigDecimal(or_data.getTotalApprove()));
				sheet.setCellValue(row, "D",  new BigDecimal(or_data.getTotalReject()));
				sheet.setCellValue(row, "E",  new BigDecimal(or_data.getTotalApproveWithOR()));
				sheet.setCellValue(row, "F",  new BigDecimal(or_data.getTotalApproveWithExemptionOR()));
				sheet.setCellValue(row, "G",  new BigDecimal(or_data.getTotalApproveWithNonLowsideOR()));
				sheet.setCellValue(row, "H",  or_data.getProduct());
				sheet.setCellValue(row, "I",  new BigDecimal(or_data.getNumberOfOR_01()));
				sheet.setCellValue(row, "J",  new BigDecimal(or_data.getNumberOfOR_02()));
				sheet.setCellValue(row, "K",  new BigDecimal(or_data.getNumberOfOR_03()));
				sheet.setCellValue(row, "L",  new BigDecimal(or_data.getNumberOfOR_04()));
				sheet.setCellValue(row, "M",  new BigDecimal(or_data.getNumberOfOR_05()));
				sheet.setCellValue(row, "N",  new BigDecimal(or_data.getNumberOfOR_06()));
				sheet.setCellValue(row, "O",  new BigDecimal(or_data.getNumberOfOR_07()));
				sheet.setCellValue(row, "P",  new BigDecimal(or_data.getNumberOfOR_08()));
				sheet.setCellValue(row, "Q",  new BigDecimal(or_data.getNumberOfOR_09()));
				sheet.setCellValue(row, "R",  new BigDecimal(or_data.getNumberOfOR_10()));
				sheet.setCellValue(row, "S",  new BigDecimal(or_data.getNumberOfOR_11()));
				sheet.setCellValue(row, "T",  new BigDecimal(or_data.getNumberOfOR_12()));
				sheet.setCellValue(row, "U",  new BigDecimal(or_data.getNumberOfOR_13()));
				sheet.setCellValue(row, "V",  new BigDecimal(or_data.getNumberOfOR_14()));
				sheet.setCellValue(row, "W",  new BigDecimal(or_data.getNumberOfOR_15()));
				sheet.setCellValue(row, "X",  new BigDecimal(or_data.getNumberOfOR_16()));
				sheet.setCellValue(row, "Y",  new BigDecimal(or_data.getNumberOfOR_17()));
				sheet.setCellValue(row, "Z",  new BigDecimal(or_data.getNumberOfOR_18()));
				sheet.setCellValue(row, "AA", new BigDecimal(or_data.getNumberOfOR_19()));
				sheet.setCellValue(row, "AB", new BigDecimal(or_data.getNumberOfOR_20()));
				sheet.setCellValue(row, "AC", new BigDecimal(or_data.getNumberOfOR_21()));
				sheet.setCellValue(row, "AD", new BigDecimal(or_data.getNumberOfOR_22()));
				sheet.setCellValue(row, "AE", new BigDecimal(or_data.getNumberOfOR_23()));
				sheet.setCellValue(row, "AF", new BigDecimal(or_data.getNumberOfOR_24()));
				sheet.setCellValue(row, "AG", new BigDecimal(or_data.getNumberOfOR_25()));
				sheet.setCellValue(row, "AH", new BigDecimal(or_data.getTotalApproveWithOR_01_02_03()));
				sheet.setCellValue(row, "AI", new BigDecimal(or_data.getTotalApproveWithOR_04()));
				sheet.setCellValue(row, "AJ", new BigDecimal(or_data.getTotalApproveWithOR_XX()));
				sheet.setCellValue(row, "AK", new BigDecimal(or_data.getTotalApproveWithLowsideOR()));
			}
		}
	}
	
	
	@Override
	protected void processAction() throws Exception{
		MySheet sheet;
		
		super.processAction();
		initAction();
		logger.debug("REPORT_ID >> "+REPORT_ID);
		logger.debug("REPORT_TYPE >> "+REPORT_TYPE);
		
		// Generate heading on CC OR
		String reportDate = new SimpleDateFormat("yyyyMMdd").format(asOfDate);
		sheet = new MySheet(workbook.getSheet(InfBatchConstant.ReportParam.OR_CC_LAYOUT));
		sheet.setCellValue(3, "O", "CC as of " + reportDate);
		// Generate heading on KEC OR
		sheet = new MySheet(workbook.getSheet(InfBatchConstant.ReportParam.OR_XPC_LAYOUT));
		sheet.setCellValue(3, "O", "XPC as of " + reportDate);
		// Generate heading on KEC OR
		sheet = new MySheet(workbook.getSheet(InfBatchConstant.ReportParam.OR_XPL_LAYOUT));
		sheet.setCellValue(3, "O", "XPL as of " + reportDate);
		
		// Generate information on Data Set worksheet
		sheet = new MySheet(workbook.getSheet(InfBatchConstant.ReportParam.DATA_SET));
		prepareMonthLabel(sheet, asOfDate);
		if(product == null || product.equals("CC")) {
			prepareOrData(sheet, 5, "CC");
		}
		if(product == null || product.equals("KEC")) {
			prepareOrData(sheet, 20, "KEC");
		}
		if(product == null || product.equals("KPL")) {
			prepareOrData(sheet, 35, "KPL");
		}
		HSSFFormulaEvaluator.evaluateAllFormulaCells(workbook);
	}
	
	public String formatLineSeparator(String text){
		String newFormat = "";
		if(!InfBatchUtil.empty(text)){
			newFormat = text.replace("\\n", System.lineSeparator());
		}
		return newFormat;
	}
}
