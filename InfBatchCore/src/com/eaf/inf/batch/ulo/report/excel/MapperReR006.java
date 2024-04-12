	package com.eaf.inf.batch.ulo.report.excel;

import java.text.DateFormat;
import java.text.DateFormatSymbols;
import java.text.DecimalFormat;
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
import com.eaf.core.ulo.common.util.Util;
import com.eaf.inf.batch.ulo.report.model.InfReportJobDataM;
import com.eaf.inf.batch.ulo.report.model.SlaDataM;
import com.eaf.inf.batch.ulo.report.model.TaskReportData;

public class MapperReR006 extends ExcelMapper{
	private static transient Logger logger = Logger.getLogger(MapperReR006.class);
	private ArrayList<SlaDataM> ReR006 = null;
	private String APP_TYPE = null;
	InfReportJobDataM infReportJob = null;
	
	public MapperReR006(String inputFilePath, String outputFilePath,String APP_TYPE) {
		super(inputFilePath, outputFilePath);
		this.APP_TYPE = APP_TYPE;
	}
	
	public MapperReR006(String inputFilePath, String outputFilePath,String APP_TYPE, InfReportJobDataM infReportJob) {
		super(inputFilePath, outputFilePath);
		this.APP_TYPE = APP_TYPE;
		this.infReportJob = infReportJob;
	}
	
	@SuppressWarnings({ "unchecked", "rawtypes" })
	@Override
	protected void processAction() throws Exception{
		super.processAction();
		MySheet sheet = new MySheet(workbook.getSheet(InfBatchConstant.ReportParam.LAYOUT));
		DateFormat dateFormat = new SimpleDateFormat("HH:mm:ss");
		Date date = new Date();
		String DATE = Formatter.display(InfBatchProperty.getDate(), Formatter.EN, Formatter.Format.dd_MMM_yyyy2);
		String TIME = dateFormat.format(date);
		String DATE_TIME = DATE+" "+TIME;
		sheet.setCellValue(3, "S", DATE_TIME);
		
		String DATE_HEADER = InfBatchConstant.message.DATE_HEADER_REPORT;
		String CONDITION_HEADER = InfBatchConstant.message.CONDITION_HEADER_REPORT;
		if(infReportJob==null){
			String DATE_EXECUTE = Formatter.display(InfBatchProperty.getDate(), Formatter.EN, Formatter.Format.dd_MMM_yyyy);
			DATE_HEADER = DATE_HEADER.replace("{DATE_FROM}", DATE_EXECUTE);
			DATE_HEADER = DATE_HEADER.replace("{DATE_TO}", DATE_EXECUTE);
			
			CONDITION_HEADER = CONDITION_HEADER.replace("{CONDITION}", "-");
		}else{
			DATE_HEADER = DATE_HEADER.replace("{DATE_FROM}", getHeaderDate(infReportJob.getDateFrom()));
			DATE_HEADER = DATE_HEADER.replace("{DATE_TO}", getHeaderDate(infReportJob.getDateTo()));
			
			if(!InfBatchUtil.empty(infReportJob)){
				CONDITION_HEADER = CONDITION_HEADER.replace("{CONDITION}", MessageUtil.getConditionHeader(infReportJob));
			}else{
				CONDITION_HEADER = CONDITION_HEADER.replace("{CONDITION}", "-");
			}
		}
		sheet.setCellValue(3, "E", DATE_HEADER);
		sheet.setCellValue(4, "E", CONDITION_HEADER);
		
		InfBatchResponseDataM infBatchResponse = new InfBatchResponseDataM();
		    infBatchResponse.setBatchId("RER006");
		    List<String> taskIds = new ArrayList<String>();
		    taskIds.add("SLA3");
		    taskIds.add("SLA4");
		    taskIds.add("SLA1");
		    taskIds.add("SLA2");
		ProcessTaskDataM processTask = new ProcessTaskDataM("RER006_TASK");
		    processTask.setCorePoolSize(8);
		    processTask.setMaxPoolSize(16);
		ExecuteTaskManager executeTask = new ExecuteTaskManager(processTask);
		    for(String taskId : taskIds){
		      TaskObjectDataM taskObject = new TaskObjectDataM();
		      TaskReportData<SlaDataM> taskReportData = new TaskReportData<SlaDataM>(taskId, this.APP_TYPE, this.infReportJob);
		      taskObject.setUniqueId(taskId);
		      taskObject.setObject(taskReportData);
		      TaskDataM task = new TaskDataM();
		      task.setTaskId(taskId);
		      task.setTaskObject(taskObject);
		      executeTask.execute(task);
		    }
		    executeTask.shutdown();
		
//		if(infReportJob==null){
//			ReR006 = ReportFileFactory.getReportFileDAO().getReportSLA3(APP_TYPE);
//		}else{
//			ReR006 = ReportFileFactory.getReportFileDAO().getSLA3(infReportJob);
//		}
		    
		this.ReR006 = ((ArrayList)processTask.find("SLA3").getResponseObject());
		if(null!=this.ReR006){
			for(int row=44,count=0;count<ReR006.size();row++,count++){
				sheet.setCellValue(row, "K", ReR006.get(count).getWipPeriod());
				sheet.setCellValue(sheet, row, "L", ReR006.get(count).getCcInfinite());
				sheet.setCellValue(sheet, row, "M", ReR006.get(count).getCcWisdom());
				sheet.setCellValue(sheet, row, "N", ReR006.get(count).getCcPremier());
				sheet.setCellValue(sheet, row, "O", ReR006.get(count).getCcPlatinum());
				sheet.setCellValue(sheet, row, "P", ReR006.get(count).getCcGeneric());
				sheet.setCellValue(sheet, row, "Q", ReR006.get(count).getCcGrandTotal());
				sheet.setCellValue(sheet, row, "R", ReR006.get(count).getKec());
				sheet.setCellValue(sheet, row, "S", ReR006.get(count).getKpl());
			}
		}
		
//		if(infReportJob==null){
//			ReR006 = ReportFileFactory.getReportFileDAO().getReportSLA4(APP_TYPE);
//		}else{
//			ReR006 = ReportFileFactory.getReportFileDAO().getSLA4(infReportJob);
//		}
		
		this.ReR006 = ((ArrayList)processTask.find("SLA4").getResponseObject());
		if(null!=this.ReR006){
		for(int row = 0, count=0;count<ReR006.size();count++){
			row = getRowFromSlaType(ReR006.get(count).getSlaType());
//			sheet.setCellValue(sheet, row, "C", ReR006.get(count).getE2eAcheive());
//			sheet.setCellValue(sheet, row, "D", ReR006.get(count).getLeg1Acheive());
//			sheet.setCellValue(sheet, row, "E", ReR006.get(count).getLeg2Acheive());
//			sheet.setCellValue(sheet, row, "F", ReR006.get(count).getLeg3Acheive());
//			sheet.setCellValue(sheet, row, "G", ReR006.get(count).getLeg4Acheive());

			sheet.setCellValue(sheet, row, "C", strFormatNumber(ReR006.get(count).getE2eAcheive()));
			sheet.setCellValue(sheet, row, "D", strFormatNumber(ReR006.get(count).getLeg1Acheive()));
			sheet.setCellValue(sheet, row, "E", strFormatNumber(ReR006.get(count).getLeg2Acheive()));
			sheet.setCellValue(sheet, row, "F", strFormatNumber(ReR006.get(count).getLeg3Acheive()));
			sheet.setCellValue(sheet, row, "G", strFormatNumber(ReR006.get(count).getLeg4Acheive()));
			
			if(!"Credit Card".equals(ReR006.get(count).getSlaType())){
				sheet.setCellValue(sheet, row+1, "C", ReR006.get(count).getE2eTarget());
				sheet.setCellValue(sheet, row+2, "C", ReR006.get(count).getE2eCountApp());
				
				sheet.setCellValue(sheet, row+1, "D", ReR006.get(count).getLeg1Target());
				sheet.setCellValue(sheet, row+2, "D", ReR006.get(count).getLeg1CountApp());
				
				sheet.setCellValue(sheet, row+1, "E", ReR006.get(count).getLeg2Target());
				sheet.setCellValue(sheet, row+2, "E", ReR006.get(count).getLeg2CountApp());
				
				sheet.setCellValue(sheet, row+1, "F", ReR006.get(count).getLeg3Target());
				sheet.setCellValue(sheet, row+2, "F", ReR006.get(count).getLeg3CountApp());
				
				sheet.setCellValue(sheet, row+1, "G", ReR006.get(count).getLeg4Target());
				sheet.setCellValue(sheet, row+2, "G", ReR006.get(count).getLeg4CountApp());
			}
		}
		}
		sheet = new MySheet(workbook.getSheet(InfBatchConstant.ReportParam.DATA_SET));
		
//		if(infReportJob==null){
//			ReR006 = ReportFileFactory.getReportFileDAO().getReportSLA1(APP_TYPE);
//		}else{
//			ReR006 = ReportFileFactory.getReportFileDAO().getSLA1(infReportJob);
//		}
		
		this.ReR006 = ((ArrayList)processTask.find("SLA1").getResponseObject());
		if(null!=this.ReR006){
		for(int row=3,count=0;count<ReR006.size();row++,count++){
			sheet.setCellValue(row, "A", ReR006.get(count).getMonth());
			sheet.setCellValue(sheet, row, "B", ReR006.get(count).getAppIn());
			sheet.setCellValue(sheet, row, "C", ReR006.get(count).getCapacitySLA());
			sheet.setCellValue(sheet, row, "D", ReR006.get(count).getWipSLA());
			sheet.setCellValue(sheet, row, "E", ReR006.get(count).getSlaInfinite());
			sheet.setCellValue(sheet, row, "F", ReR006.get(count).getSlaWisdom());
			sheet.setCellValue(sheet, row, "G", ReR006.get(count).getSlaPremier());
			sheet.setCellValue(sheet, row, "H", ReR006.get(count).getSlaPremier());
			sheet.setCellValue(sheet, row, "I", ReR006.get(count).getSlaGerneric());
			sheet.setCellValue(sheet, row, "J", ReR006.get(count).getSlaKEC());
			sheet.setCellValue(sheet, row, "K", ReR006.get(count).getSlaKPL());
			sheet.setCellValue(sheet, row, "L", ReR006.get(count).getPercentSlaTarget());
			
			sheet.setCellValue(row+15, "A", ReR006.get(count).getMonth());
			sheet.setCellValue(sheet, row+15, "B", ReR006.get(count).getAppIn());
			sheet.setCellValue(sheet, row+15, "C", ReR006.get(count).getCapacityOLA());
			sheet.setCellValue(sheet, row+15, "D", ReR006.get(count).getWipOLA());
			sheet.setCellValue(sheet, row+15, "E", ReR006.get(count).getOlaInfinite());
			sheet.setCellValue(sheet, row+15, "F", ReR006.get(count).getOlaWisdom());
			sheet.setCellValue(sheet, row+15, "G", ReR006.get(count).getOlaPremier());
			sheet.setCellValue(sheet, row+15, "H", ReR006.get(count).getOlaPremier());
			sheet.setCellValue(sheet, row+15, "I", ReR006.get(count).getOlaGerneric());
			sheet.setCellValue(sheet, row+15, "J", ReR006.get(count).getOlaKEC());
			sheet.setCellValue(sheet, row+15, "K", ReR006.get(count).getOlaKPL());
			sheet.setCellValue(sheet, row+15, "L", ReR006.get(count).getPercentSlaTarget());
		}
		}
		
//		if(infReportJob==null){
//			ReR006 = ReportFileFactory.getReportFileDAO().getReportSLA2(APP_TYPE);
//		}else{
//			ReR006 = ReportFileFactory.getReportFileDAO().getSLA2(infReportJob);
//		}
		
		this.ReR006 = ((ArrayList)processTask.find("SLA2").getResponseObject());
		if(null!=this.ReR006){
		for(int row=34,count=0;count<ReR006.size();row++,count++){
			sheet.setCellValue(row, "A", ReR006.get(count).getPeriod());
			sheet.setCellValue(sheet, row, "B", ReR006.get(count).getNoAppSlaInf());
			sheet.setCellValue(sheet, row, "C", ReR006.get(count).getTargetSlaInf());
			sheet.setCellValue(sheet, row, "D", ReR006.get(count).getNoAppSlaWis());
			sheet.setCellValue(sheet, row, "E", ReR006.get(count).getTargetSlaWis());
			sheet.setCellValue(sheet, row, "F", ReR006.get(count).getNoAppSlaPre());
			sheet.setCellValue(sheet, row, "G", ReR006.get(count).getTargetSlaPre());
			sheet.setCellValue(sheet, row, "H", ReR006.get(count).getNoAppSlaPlt());
			sheet.setCellValue(sheet, row, "I", ReR006.get(count).getTargetSlaPlt());
			sheet.setCellValue(sheet, row, "J", ReR006.get(count).getNoAppSlaGen());
			sheet.setCellValue(sheet, row, "K", ReR006.get(count).getTargetSlaGen());
			sheet.setCellValue(sheet, row, "L", ReR006.get(count).getNoAppSlaKEC());
			sheet.setCellValue(sheet, row, "M", ReR006.get(count).getTargetSlaKEC());
			sheet.setCellValue(sheet, row, "N", ReR006.get(count).getNoAppSlaKPL());
			sheet.setCellValue(sheet, row, "O", ReR006.get(count).getTargetSlaKPL());
			
			sheet.setCellValue(row+13, "A", ReR006.get(count).getPeriod());
			sheet.setCellValue(sheet, row+13, "B", ReR006.get(count).getNoAppOlaInf());
			sheet.setCellValue(sheet, row+13, "C", ReR006.get(count).getTargetOlaInf());
			sheet.setCellValue(sheet, row+13, "D", ReR006.get(count).getNoAppOlaWis());
			sheet.setCellValue(sheet, row+13, "E", ReR006.get(count).getTargetOlaWis());
			sheet.setCellValue(sheet, row+13, "F", ReR006.get(count).getNoAppOlaPre());
			sheet.setCellValue(sheet, row+13, "G", ReR006.get(count).getTargetOlaPre());
			sheet.setCellValue(sheet, row+13, "H", ReR006.get(count).getNoAppOlaPlt());
			sheet.setCellValue(sheet, row+13, "I", ReR006.get(count).getTargetOlaPlt());
			sheet.setCellValue(sheet, row+13, "J", ReR006.get(count).getNoAppOlaGen());
			sheet.setCellValue(sheet, row+13, "K", ReR006.get(count).getTargetOlaGen());
			sheet.setCellValue(sheet, row+13, "L", ReR006.get(count).getNoAppOlaKEC());
			sheet.setCellValue(sheet, row+13, "M", ReR006.get(count).getTargetOlaKEC());
			sheet.setCellValue(sheet, row+13, "N", ReR006.get(count).getNoAppOlaKPL());
			sheet.setCellValue(sheet, row+13, "O", ReR006.get(count).getTargetOlaKPL());
		}
		}
		HSSFFormulaEvaluator.evaluateAllFormulaCells(workbook);
	}
	
	private static int getRowFromSlaType(String slaType){
		int row = 0;
		if(slaType.equals("Credit Card")){
			row = 44;
		}else if(slaType.equals("Infinite")){
			row = 45;
		}else if(slaType.equals("Wisdom")){
			row = 48;
		}else if(slaType.equals("Premier")){
			row = 51;
		}else if(slaType.equals("Platinum")){
			row = 54;
		}else if(slaType.equals("Generic")){
			row = 57;
		}else if(slaType.equals("KEC")){
			row = 61;
		}else if(slaType.equals("KPL")){
			row = 64;
		}
		return row;
	}
	
	public static String getHeaderDate(String date){
		String headerDate = "";
		
		String[] dates = date.substring(0, 10).split("-");
		String year = dates[0];
		String month = new DateFormatSymbols().getShortMonths()[Integer.parseInt(dates[1])-1]; ;
		String day = dates[2];
		
		headerDate = day+" "+month+" "+year;
		return headerDate;
	}
	
	public String strFormatNumber(String value){
		DecimalFormat dFormat = new DecimalFormat("#.##");
		if(Util.isNumeric(value)){
			value = dFormat.format(Double.parseDouble(value))+"%";
			return value;
		}
		return value;
	}
}
