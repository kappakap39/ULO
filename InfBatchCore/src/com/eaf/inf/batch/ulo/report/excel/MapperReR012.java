package com.eaf.inf.batch.ulo.report.excel;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.apache.log4j.Logger;
import org.apache.poi.hssf.usermodel.HSSFFormulaEvaluator;

import com.eaf.core.ulo.common.cache.InfBatchProperty;
import com.eaf.core.ulo.common.cont.InfBatchConstant;
import com.eaf.core.ulo.common.display.Formatter;
import com.eaf.core.ulo.common.excel.ExcelMapper;
import com.eaf.core.ulo.common.excel.MySheet;
import com.eaf.core.ulo.common.model.InfBatchResponseDataM;
import com.eaf.core.ulo.common.task.api.ExecuteTaskManager;
import com.eaf.core.ulo.common.task.model.ProcessTaskDataM;
import com.eaf.core.ulo.common.task.model.TaskDataM;
import com.eaf.core.ulo.common.task.model.TaskObjectDataM;
import com.eaf.core.ulo.common.util.InfBatchUtil;
import com.eaf.core.ulo.common.util.MessageUtil;
import com.eaf.inf.batch.ulo.report.model.InfReportJobDataM;
import com.eaf.inf.batch.ulo.report.model.ProcessingTimeDataM;
import com.eaf.inf.batch.ulo.report.model.TaskReportData;

public class MapperReR012 extends ExcelMapper{
	private static transient Logger logger = Logger.getLogger(MapperReR012.class);
	private ArrayList<ProcessingTimeDataM> reR012 = null;
	InfReportJobDataM infReportJob = null;
	
	public MapperReR012(String inputFilePath, String outputFilePath) {
		super(inputFilePath, outputFilePath);
	}
	
	public MapperReR012(String inputFilePath, String outputFilePath, InfReportJobDataM infReportJob) {
		super(inputFilePath, outputFilePath);
		this.infReportJob = infReportJob;
	}
	@SuppressWarnings({ "unchecked", "rawtypes" })
	@Override
	protected void processAction() throws Exception {
		super.processAction();

		MySheet sheet = new MySheet(workbook.getSheet(InfBatchConstant.ReportParam.LAYOUT));
		DateFormat dateFormat = new SimpleDateFormat("HH:mm:ss");
		Date date = new Date();
		String DATE = Formatter.display(InfBatchProperty.getDate(), Formatter.EN, Formatter.Format.dd_MMM_yyyy2);
		String TIME = dateFormat.format(date);
		String DATE_TIME = DATE + " " + TIME;
		sheet.setCellValue(3, "X", DATE_TIME);

		String DATE_HEADER = InfBatchConstant.message.DATE_HEADER_REPORT;
		String CONDITION_HEADER = InfBatchConstant.message.CONDITION_HEADER_REPORT;
		if (infReportJob == null) {
			String DATE_EXECUTE = Formatter.display(InfBatchProperty.getDate(), Formatter.EN, Formatter.Format.dd_MMM_yyyy);
			DATE_HEADER = DATE_HEADER.replace("{DATE_FROM}", DATE_EXECUTE);
			DATE_HEADER = DATE_HEADER.replace("{DATE_TO}", DATE_EXECUTE);

			CONDITION_HEADER = CONDITION_HEADER.replace("{CONDITION}", "-");
		} else {
			DATE_HEADER = DATE_HEADER.replace("{DATE_FROM}", MapperReR006.getHeaderDate(infReportJob.getDateFrom()));
			DATE_HEADER = DATE_HEADER.replace("{DATE_TO}", MapperReR006.getHeaderDate(infReportJob.getDateTo()));

			if (!InfBatchUtil.empty(infReportJob)) {
				CONDITION_HEADER = CONDITION_HEADER.replace("{CONDITION}", MessageUtil.getConditionHeader(infReportJob));
			} else {
				CONDITION_HEADER = CONDITION_HEADER.replace("{CONDITION}", "-");
			}
		}
		sheet.setCellValue(3, "D", DATE_HEADER);
		sheet.setCellValue(4, "D", CONDITION_HEADER);

		InfBatchResponseDataM infBatchResponse = new InfBatchResponseDataM();
		infBatchResponse.setBatchId("RER012");
		List<String> taskIds = new ArrayList<String>();
		taskIds.add("R012_SUMMARY");
		taskIds.add("R012_TOP_IA");
		taskIds.add("R012_BOTTOM_IA");
		taskIds.add("R012_TOP_DE1.1");
		taskIds.add("R012_BOTTOM_DE1.1");
		taskIds.add("R012_TOP_DE1.2");
		taskIds.add("R012_BOTTOM_DE1.2");
		taskIds.add("R012_TOP_DV");
		taskIds.add("R012_BOTTOM_DV");
		taskIds.add("R012_TOP_DE2");
		taskIds.add("R012_BOTTOM_DE2");
		taskIds.add("R012_TOP_VT");
		taskIds.add("R012_BOTTOM_VT");
		taskIds.add("R012_TOP_CA");
		taskIds.add("R012_BOTTOM_CA");
		taskIds.add("R012_TOP_FU");
		taskIds.add("R012_BOTTOM_FU");
		taskIds.add("R012_PROCESSING_TIME");
		ProcessTaskDataM processTask = new ProcessTaskDataM("RER012_TASK");
		processTask.setCorePoolSize(8);
		processTask.setMaxPoolSize(16);
		ExecuteTaskManager executeTask = new ExecuteTaskManager(processTask);
		for (String taskId : taskIds) {
			TaskObjectDataM taskObject = new TaskObjectDataM();
			TaskReportData<ProcessingTimeDataM> taskReportData = new TaskReportData<ProcessingTimeDataM>(taskId, this.infReportJob);
			taskObject.setUniqueId(taskId);
			taskObject.setObject(taskReportData);
			TaskDataM task = new TaskDataM();
			task.setTaskId(taskId);
			task.setTaskObject(taskObject);
			executeTask.execute(task);
		}
		executeTask.shutdown();

		//		
		//		ReportFileDAO reportDAO = ReportFileFactory.getReportFileDAO();
		//		if(infReportJob==null){
		//			reR012 = reportDAO.getReportR012Summary();
		//		}else{
		//			reR012 = reportDAO.getReportR012SummaryC(infReportJob);
		//		}
		this.reR012 = ((ArrayList) processTask.find("R012_SUMMARY").getResponseObject());
		if (null != this.reR012) {
			for (int count = 0; count < reR012.size(); count++) {
				String COLUMN = getColumnFromRoleNameForSummary(reR012.get(count).getRoleName());
				logger.debug("reR012.get(count).getRoleName() >> " + reR012.get(count).getRoleName());
				logger.debug("COLUMN >> " + COLUMN);
				logger.debug("reR012.get(count).getStandard() >> " + reR012.get(count).getStandard());
				sheet.setCellValue(sheet, 24, COLUMN, reR012.get(count).getStandard());
				sheet.setCellValue(sheet, 25, COLUMN, reR012.get(count).getProcessTime());
				sheet.setCellValue(sheet, 26, COLUMN, reR012.get(count).getWaitingTime());
			}
		}

		//IA
		this.reR012 = ((ArrayList) processTask.find("R012_TOP_IA").getResponseObject());
		int rowIA = 64;
		if (null != this.reR012) {
			for (int count = 0; count < reR012.size(); count++) {
				sheet.setCellValue(sheet, rowIA + count, "A", reR012.get(count).getCardTypeDesc());
				sheet.setCellValue(sheet, rowIA + count, "C", reR012.get(count).getStandard());
				sheet.setCellValue(sheet, rowIA + count, "D", reR012.get(count).getProcessTime());
				sheet.setCellValue(sheet, rowIA + count, "E", reR012.get(count).getWaitingTime());
			}
		}

		this.reR012 = ((ArrayList) processTask.find("R012_BOTTOM_IA").getResponseObject());
		if (null != this.reR012) {
			for (int count = 0; count < reR012.size(); count++) {
				sheet.setCellValue(sheet, rowIA + count, "I", reR012.get(count).getCardTypeDesc());
				sheet.setCellValue(sheet, rowIA + count, "K", reR012.get(count).getStandard());
				sheet.setCellValue(sheet, rowIA + count, "L", reR012.get(count).getProcessTime());
				sheet.setCellValue(sheet, rowIA + count, "M", reR012.get(count).getWaitingTime());
			}
		}

		//DE1.1
		this.reR012 = ((ArrayList) processTask.find("R012_TOP_DE1.1").getResponseObject());
		int rowDE1_1 = 74;
		if (null != this.reR012) {
			for (int count = 0; count < reR012.size(); count++) {
				sheet.setCellValue(sheet, rowDE1_1 + count, "A", reR012.get(count).getCardTypeDesc());
				sheet.setCellValue(sheet, rowDE1_1 + count, "C", reR012.get(count).getStandard());
				sheet.setCellValue(sheet, rowDE1_1 + count, "D", reR012.get(count).getProcessTime());
				sheet.setCellValue(sheet, rowDE1_1 + count, "E", reR012.get(count).getWaitingTime());
			}
		}

		this.reR012 = ((ArrayList) processTask.find("R012_BOTTOM_DE1.1").getResponseObject());
		if (null != this.reR012) {
			for (int count = 0; count < reR012.size(); count++) {
				sheet.setCellValue(sheet, rowDE1_1 + count, "I", reR012.get(count).getCardTypeDesc());
				sheet.setCellValue(sheet, rowDE1_1 + count, "K", reR012.get(count).getStandard());
				sheet.setCellValue(sheet, rowDE1_1 + count, "L", reR012.get(count).getProcessTime());
				sheet.setCellValue(sheet, rowDE1_1 + count, "M", reR012.get(count).getWaitingTime());
			}
		}

		//DE1.2
		this.reR012 = ((ArrayList) processTask.find("R012_TOP_DE1.2").getResponseObject());
		int rowDE1_2 = 84;
		if (null != this.reR012) {
			for (int count = 0; count < reR012.size(); count++) {
				sheet.setCellValue(sheet, rowDE1_2 + count, "A", reR012.get(count).getCardTypeDesc());
				sheet.setCellValue(sheet, rowDE1_2 + count, "C", reR012.get(count).getStandard());
				sheet.setCellValue(sheet, rowDE1_2 + count, "D", reR012.get(count).getProcessTime());
				sheet.setCellValue(sheet, rowDE1_2 + count, "E", reR012.get(count).getWaitingTime());
			}
		}

		this.reR012 = ((ArrayList) processTask.find("R012_BOTTOM_DE1.2").getResponseObject());
		if (null != this.reR012) {
			for (int count = 0; count < reR012.size(); count++) {
				sheet.setCellValue(sheet, rowDE1_2 + count, "I", reR012.get(count).getCardTypeDesc());
				sheet.setCellValue(sheet, rowDE1_2 + count, "K", reR012.get(count).getStandard());
				sheet.setCellValue(sheet, rowDE1_2 + count, "L", reR012.get(count).getProcessTime());
				sheet.setCellValue(sheet, rowDE1_2 + count, "M", reR012.get(count).getWaitingTime());
			}
		}

		//DV
		this.reR012 = ((ArrayList) processTask.find("R012_TOP_DV").getResponseObject());
		int rowDV = 94;
		if (null != this.reR012) {
			for (int count = 0; count < reR012.size(); count++) {
				sheet.setCellValue(sheet, rowDV + count, "A", reR012.get(count).getCardTypeDesc());
				sheet.setCellValue(sheet, rowDV + count, "C", reR012.get(count).getStandard());
				sheet.setCellValue(sheet, rowDV + count, "D", reR012.get(count).getProcessTime());
				sheet.setCellValue(sheet, rowDV + count, "E", reR012.get(count).getWaitingTime());
			}
		}

		this.reR012 = ((ArrayList) processTask.find("R012_BOTTOM_DV").getResponseObject());
		if (null != this.reR012) {
			for (int count = 0; count < reR012.size(); count++) {
				sheet.setCellValue(sheet, rowDV + count, "I", reR012.get(count).getCardTypeDesc());
				sheet.setCellValue(sheet, rowDV + count, "K", reR012.get(count).getStandard());
				sheet.setCellValue(sheet, rowDV + count, "L", reR012.get(count).getProcessTime());
				sheet.setCellValue(sheet, rowDV + count, "M", reR012.get(count).getWaitingTime());
			}
		}

		//DE2
		this.reR012 = ((ArrayList) processTask.find("R012_TOP_DE2").getResponseObject());
		int rowDE2 = 104;
		if (null != this.reR012) {
			for (int count = 0; count < reR012.size(); count++) {
				sheet.setCellValue(sheet, rowDE2 + count, "A", reR012.get(count).getCardTypeDesc());
				sheet.setCellValue(sheet, rowDE2 + count, "C", reR012.get(count).getStandard());
				sheet.setCellValue(sheet, rowDE2 + count, "D", reR012.get(count).getProcessTime());
				sheet.setCellValue(sheet, rowDE2 + count, "E", reR012.get(count).getWaitingTime());
			}
		}

		this.reR012 = ((ArrayList) processTask.find("R012_BOTTOM_DE2").getResponseObject());
		if (null != this.reR012) {
			for (int count = 0; count < reR012.size(); count++) {
				sheet.setCellValue(sheet, rowDE2 + count, "I", reR012.get(count).getCardTypeDesc());
				sheet.setCellValue(sheet, rowDE2 + count, "K", reR012.get(count).getStandard());
				sheet.setCellValue(sheet, rowDE2 + count, "L", reR012.get(count).getProcessTime());
				sheet.setCellValue(sheet, rowDE2 + count, "M", reR012.get(count).getWaitingTime());
			}
		}

		//VT
		this.reR012 = ((ArrayList) processTask.find("R012_TOP_VT").getResponseObject());
		int rowVT = 114;
		if (null != this.reR012) {
			for (int count = 0; count < reR012.size(); count++) {
				sheet.setCellValue(sheet, rowVT + count, "A", reR012.get(count).getCardTypeDesc());
				sheet.setCellValue(sheet, rowVT + count, "C", reR012.get(count).getStandard());
				sheet.setCellValue(sheet, rowVT + count, "D", reR012.get(count).getProcessTime());
				sheet.setCellValue(sheet, rowVT + count, "E", reR012.get(count).getWaitingTime());
			}
		}

		this.reR012 = ((ArrayList) processTask.find("R012_BOTTOM_VT").getResponseObject());
		if (null != this.reR012) {
			for (int count = 0; count < reR012.size(); count++) {
				sheet.setCellValue(sheet, rowVT + count, "I", reR012.get(count).getCardTypeDesc());
				sheet.setCellValue(sheet, rowVT + count, "K", reR012.get(count).getStandard());
				sheet.setCellValue(sheet, rowVT + count, "L", reR012.get(count).getProcessTime());
				sheet.setCellValue(sheet, rowVT + count, "M", reR012.get(count).getWaitingTime());
			}
		}

		//CA
		this.reR012 = ((ArrayList) processTask.find("R012_TOP_CA").getResponseObject());
		int rowCA = 124;
		if (null != this.reR012) {
			for (int count = 0; count < reR012.size(); count++) {
				sheet.setCellValue(sheet, rowCA + count, "A", reR012.get(count).getCardTypeDesc());
				sheet.setCellValue(sheet, rowCA + count, "C", reR012.get(count).getStandard());
				sheet.setCellValue(sheet, rowCA + count, "D", reR012.get(count).getProcessTime());
				sheet.setCellValue(sheet, rowCA + count, "E", reR012.get(count).getWaitingTime());
			}
		}

		this.reR012 = ((ArrayList) processTask.find("R012_BOTTOM_CA").getResponseObject());
		if (null != this.reR012) {
			for (int count = 0; count < reR012.size(); count++) {
				sheet.setCellValue(sheet, rowCA + count, "I", reR012.get(count).getCardTypeDesc());
				sheet.setCellValue(sheet, rowCA + count, "K", reR012.get(count).getStandard());
				sheet.setCellValue(sheet, rowCA + count, "L", reR012.get(count).getProcessTime());
				sheet.setCellValue(sheet, rowCA + count, "M", reR012.get(count).getWaitingTime());
			}
		}

		//FU
		this.reR012 = ((ArrayList) processTask.find("R012_TOP_FU").getResponseObject());
		int rowFU = 134;
		if (null != this.reR012) {
			for (int count = 0; count < reR012.size(); count++) {
				sheet.setCellValue(sheet, rowFU + count, "A", reR012.get(count).getCardTypeDesc());
				sheet.setCellValue(sheet, rowFU + count, "C", reR012.get(count).getStandard());
				sheet.setCellValue(sheet, rowFU + count, "D", reR012.get(count).getProcessTime());
				sheet.setCellValue(sheet, rowFU + count, "E", reR012.get(count).getWaitingTime());
			}
		}

		this.reR012 = ((ArrayList) processTask.find("R012_BOTTOM_FU").getResponseObject());
		if (null != this.reR012) {
			for (int count = 0; count < reR012.size(); count++) {
				sheet.setCellValue(sheet, rowFU + count, "I", reR012.get(count).getCardTypeDesc());
				sheet.setCellValue(sheet, rowFU + count, "K", reR012.get(count).getStandard());
				sheet.setCellValue(sheet, rowFU + count, "L", reR012.get(count).getProcessTime());
				sheet.setCellValue(sheet, rowFU + count, "M", reR012.get(count).getWaitingTime());
			}
		}

		sheet = new MySheet(workbook.getSheet(InfBatchConstant.ReportParam.DATA_SET));

		//		if(infReportJob==null){
		//			reR012 = reportDAO.getReportR012();
		//		}else{
		//			reR012 = reportDAO.getProcessingTime(infReportJob);
		//		}
		this.reR012 = ((ArrayList) processTask.find("R012_PROCESSING_TIME").getResponseObject());
		if (null != this.reR012) {
			for (int count = 0; count < reR012.size(); count++) {
				logger.debug("CARD_TYPE_DESC : " + reR012.get(count).getCardTypeDesc());
				int row = getRowFromCardType(reR012.get(count).getCardTypeDesc());
				logger.debug("ROW : " + row);
				if (row == 0) {
					logger.debug(reR012.get(count).getCardTypeDesc() + " field not found");
				} else {
					String COLUMN = getColumnFromRoleName(reR012.get(count).getRoleName());
					logger.debug("ROLE_NAME : " + reR012.get(count).getRoleName());
					logger.debug("COLUMN : " + COLUMN);
					logger.debug("STANDARD : " + reR012.get(count).getStandard());
					logger.debug("PROCESSING TIME : " + reR012.get(count).getProcessTime());
					logger.debug("WAITING TIME : " + reR012.get(count).getWaitingTime());
					if (!InfBatchUtil.empty(COLUMN)) {
						sheet.setCellValue(sheet, row + 2, COLUMN, reR012.get(count).getStandard());
						sheet.setCellValue(sheet, row + 3, COLUMN, reR012.get(count).getProcessTime());
						sheet.setCellValue(sheet, row + 4, COLUMN, reR012.get(count).getWaitingTime());
					}
				}
			}
		}
		HSSFFormulaEvaluator.evaluateAllFormulaCells(workbook);
	}
	
	private static int getRowFromCardType(String cardTypeDesc){
		int row=0;
		if(cardTypeDesc.equals("CC_INFINITE")){
			row = 1;
		}else if(cardTypeDesc.equals("CC_WISDOM")){
			row = 8;
		}else if(cardTypeDesc.equals("CC_PREMIER")){
			row = 15;
		}else if(cardTypeDesc.equals("CC_PLATINUM")){
			row = 22;
		}else if(cardTypeDesc.equals("CC_GENERIC")){
			row = 29;
		}else if(cardTypeDesc.equals("KEC")){
			row = 36;
		}else if(cardTypeDesc.equals("KPL")){
			row = 43;
		}
		return row;
	}
	
	private static String getColumnFromRoleNameForSummary(String roleName){
		String COLUMN = null;
		roleName = roleName.replace("_", ".");
		if(roleName.equals("IA")){
			COLUMN = "C";
		}else if(roleName.equals("DE1.1")){
			COLUMN = "D";
		}else if(roleName.equals("DE1.2")){
			COLUMN = "E";
		}else if(roleName.equals("DV")){
			COLUMN = "F";
		}else if(roleName.equals("DE2")){
			COLUMN = "G";
		}else if(roleName.equals("VT")){
			COLUMN = "H";
		}else if(roleName.equals("CA")){
			COLUMN = "I";
		}else if(roleName.equals("FU")){
			COLUMN = "J";
		}
		return COLUMN;
	}
	
	private static String getColumnFromRoleName(String roleName){
		String COLUMN = null;
		roleName = roleName.replace("_", ".");
		if(roleName.equals("IA")){
			COLUMN = "B";
		}else if(roleName.equals("DE1.1")){
			COLUMN = "C";
		}else if(roleName.equals("DE1.2")){
			COLUMN = "D";
		}else if(roleName.equals("DV")){
			COLUMN = "E";
		}else if(roleName.equals("DE2")){
			COLUMN = "F";
		}else if(roleName.equals("VT")){
			COLUMN = "G";
		}else if(roleName.equals("CA")){
			COLUMN = "H";
		}else if(roleName.equals("FU")){
			COLUMN = "I";
		}
		return COLUMN;
	}

}
