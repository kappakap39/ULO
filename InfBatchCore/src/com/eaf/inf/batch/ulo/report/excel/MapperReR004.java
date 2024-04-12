package com.eaf.inf.batch.ulo.report.excel;

import java.math.BigDecimal;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

import org.apache.log4j.Logger;
import org.apache.poi.hssf.usermodel.HSSFFormulaEvaluator;

import com.eaf.core.ulo.common.cache.InfBatchProperty;
import com.eaf.core.ulo.common.cont.InfBatchConstant;
import com.eaf.core.ulo.common.display.Formatter;
import com.eaf.core.ulo.common.excel.ExcelMapper;
import com.eaf.core.ulo.common.excel.MySheet;
import com.eaf.core.ulo.common.task.api.TaskWorker;
import com.eaf.core.ulo.common.util.InfBatchUtil;
import com.eaf.core.ulo.common.util.MessageUtil;
import com.eaf.inf.batch.ulo.report.dao.ReportFileFactory;
import com.eaf.inf.batch.ulo.report.model.InfReportJobDataM;
import com.eaf.inf.batch.ulo.report.model.OperatingResultDataM;
import com.eaf.inf.batch.ulo.report.model.PeriodDataM;

public class MapperReR004 extends ExcelMapper{
	private static transient Logger logger = Logger.getLogger(MapperReR004.class);
	int CORE_POOL_SIZE = Integer.parseInt(InfBatchProperty.getInfBatchConfig("DAILY_REPORT2_CORE_POOL_SIZE"));
	int MAX_POOL_SIZE = Integer.parseInt(InfBatchProperty.getInfBatchConfig("DAILY_REPORT2_MAX_POOL_SIZE"));
	int QUEUE_CAPACITY = Integer.parseInt(InfBatchProperty.getInfBatchConfig("QUEUE_CAPACITY"));
	int KEEP_ALIVE_TIME = Integer.parseInt(InfBatchProperty.getInfBatchConfig("KEEP_ALIVE_TIME"));

	private ArrayList<OperatingResultDataM> reR004 = null;
	private HashMap<String , ArrayList<OperatingResultDataM>> reR004HashMap = null;
	private String REPORT_ID = null;
	private int REPORT_TYPE;
	InfReportJobDataM infReportJob = null;
	private void initAction(){
		try {
			if(reR004HashMap==null){
				reR004HashMap = new HashMap<>();
			}
			
//			if(InfBatchConstant.RE_R004.class.getSimpleName().equals(REPORT_ID)){
//				reR004HashMap.put(InfBatchConstant.RE_R004.class.getSimpleName(), ReportFileFactory.getReportFileDAO().getOperatingResult(infReportJob));
//				REPORT_TYPE = 1;
//			}else if(InfBatchConstant.RE_R004.TOTAL.equals(REPORT_ID)){
//				reR004HashMap.put(InfBatchConstant.RE_R004.TOTAL, ReportFileFactory.getReportFileDAO().getReportR004_01());
//				REPORT_TYPE = 1;
//			}else if(InfBatchConstant.RE_R004.NEW.equals(REPORT_ID)){
//				reR004HashMap.put(InfBatchConstant.RE_R004.NEW, ReportFileFactory.getReportFileDAO().getReportR004_02());
//				REPORT_TYPE = 1;
//			}else if(InfBatchConstant.RE_R004.ADD_CC.equals(REPORT_ID)){
//				reR004HashMap.put(InfBatchConstant.RE_R004.ADD_CC, ReportFileFactory.getReportFileDAO().getReportR004_03());
//				REPORT_TYPE = 2;
//			}else if(InfBatchConstant.RE_R004.UPGRADE_CC.equals(REPORT_ID)){
//				reR004HashMap.put(InfBatchConstant.RE_R004.UPGRADE_CC, ReportFileFactory.getReportFileDAO().getReportR004_04());
//				REPORT_TYPE = 2;
//			}else if(InfBatchConstant.RE_R004.INCREASE.equals(REPORT_ID)){
//				reR004HashMap.put(InfBatchConstant.RE_R004.INCREASE, ReportFileFactory.getReportFileDAO().getReportR004_05());
//				REPORT_TYPE = 1;
//			}else if(InfBatchConstant.RE_R004.CC_NEW_KEC.equals(REPORT_ID)){
//				reR004HashMap.put(InfBatchConstant.RE_R004.CC_NEW_KEC, ReportFileFactory.getReportFileDAO().getReportR004_06());
//				REPORT_TYPE = 2;
//			}else if(InfBatchConstant.RE_R004.CC_ADD_KEC.equals(REPORT_ID)){
//				reR004HashMap.put(InfBatchConstant.RE_R004.CC_ADD_KEC, ReportFileFactory.getReportFileDAO().getReportR004_07());
//				REPORT_TYPE = 2;
//			}else if(InfBatchConstant.RE_R004.CC_UPGRADE_KEC.equals(REPORT_ID)){
//				reR004HashMap.put(InfBatchConstant.RE_R004.CC_UPGRADE_KEC, ReportFileFactory.getReportFileDAO().getReportR004_08());
//				REPORT_TYPE = 2;
//			}else if(InfBatchConstant.RE_R004.KEC_KPL.equals(REPORT_ID)){
//				reR004HashMap.put(InfBatchConstant.RE_R004.KEC_KPL, ReportFileFactory.getReportFileDAO().getReportR004_09());
//				REPORT_TYPE = 3;
//			}else if(InfBatchConstant.RE_R004.KEC_HL.equals(REPORT_ID)){
//				reR004HashMap.put(InfBatchConstant.RE_R004.KEC_HL, ReportFileFactory.getReportFileDAO().getReportR004_10());
//				REPORT_TYPE = 3;
//			}else if(InfBatchConstant.RE_R004.KEC_SME.equals(REPORT_ID)){
//				reR004HashMap.put(InfBatchConstant.RE_R004.KEC_SME, ReportFileFactory.getReportFileDAO().getReportR004_11());
//				REPORT_TYPE = 3;
//			}else if(InfBatchConstant.RE_R004.KEC_KL.equals(REPORT_ID)){
//				reR004HashMap.put(InfBatchConstant.RE_R004.KEC_KL, ReportFileFactory.getReportFileDAO().getReportR004_12());
//				REPORT_TYPE = 3;
//			}else if(InfBatchConstant.RE_R004.CC_HL.equals(REPORT_ID)){
//				reR004HashMap.put(InfBatchConstant.RE_R004.CC_HL, ReportFileFactory.getReportFileDAO().getReportR004_13());
//				REPORT_TYPE = 2;
//			}else if(InfBatchConstant.RE_R004.CC_SME.equals(REPORT_ID)){
//				reR004HashMap.put(InfBatchConstant.RE_R004.CC_SME, ReportFileFactory.getReportFileDAO().getReportR004_14());
//				REPORT_TYPE = 2;
//			}else if(InfBatchConstant.RE_R004.CC_KL.equals(REPORT_ID)){
//				reR004HashMap.put(InfBatchConstant.RE_R004.CC_KL, ReportFileFactory.getReportFileDAO().getReportR004_15());
//				REPORT_TYPE = 2;
//			}
			
			if(InfBatchConstant.RE_R004.class.getSimpleName().equals(REPORT_ID)){
				reR004HashMap.put(InfBatchConstant.RE_R004.class.getSimpleName(), ReportFileFactory.getReportFileDAO().getOperatingResult(infReportJob));
				REPORT_TYPE = 1;
			}else if(InfBatchConstant.RE_R004.TOTAL.equals(REPORT_ID)){
				reR004HashMap.put(InfBatchConstant.RE_R004.TOTAL, getReportOR_1_5("1"));
				REPORT_TYPE = 1;
			}else if(InfBatchConstant.RE_R004.NEW.equals(REPORT_ID)){
				reR004HashMap.put(InfBatchConstant.RE_R004.NEW, getReportOR_1_5("2"));
				REPORT_TYPE = 1;
			}else if(InfBatchConstant.RE_R004.ADD_CC.equals(REPORT_ID)){
				reR004HashMap.put(InfBatchConstant.RE_R004.ADD_CC, getReportOR_1_5("3"));
				REPORT_TYPE = 2;
			}else if(InfBatchConstant.RE_R004.UPGRADE_CC.equals(REPORT_ID)){
				reR004HashMap.put(InfBatchConstant.RE_R004.UPGRADE_CC, getReportOR_1_5("4"));
				REPORT_TYPE = 2;
			}else if(InfBatchConstant.RE_R004.INCREASE.equals(REPORT_ID)){
				reR004HashMap.put(InfBatchConstant.RE_R004.INCREASE, getReportOR_1_5("5"));
				REPORT_TYPE = 1;
			}else if(InfBatchConstant.RE_R004.CC_NEW_KEC.equals(REPORT_ID)){
				reR004HashMap.put(InfBatchConstant.RE_R004.CC_NEW_KEC, ReportFileFactory.getReportFileDAO().getReportR004_06());
				REPORT_TYPE = 2;
			}else if(InfBatchConstant.RE_R004.CC_ADD_KEC.equals(REPORT_ID)){
				reR004HashMap.put(InfBatchConstant.RE_R004.CC_ADD_KEC, ReportFileFactory.getReportFileDAO().getReportR004_07());
				REPORT_TYPE = 2;
			}else if(InfBatchConstant.RE_R004.CC_UPGRADE_KEC.equals(REPORT_ID)){
				reR004HashMap.put(InfBatchConstant.RE_R004.CC_UPGRADE_KEC, ReportFileFactory.getReportFileDAO().getReportR004_08());
				REPORT_TYPE = 2;
			}else if(InfBatchConstant.RE_R004.KEC_KPL.equals(REPORT_ID)){
				reR004HashMap.put(InfBatchConstant.RE_R004.KEC_KPL, ReportFileFactory.getReportFileDAO().getReportR004_09());
				REPORT_TYPE = 3;
			}else if(InfBatchConstant.RE_R004.KEC_HL.equals(REPORT_ID)){
				reR004HashMap.put(InfBatchConstant.RE_R004.KEC_HL, ReportFileFactory.getReportFileDAO().getReportR004_10());
				REPORT_TYPE = 3;
			}else if(InfBatchConstant.RE_R004.KEC_SME.equals(REPORT_ID)){
				reR004HashMap.put(InfBatchConstant.RE_R004.KEC_SME, ReportFileFactory.getReportFileDAO().getReportR004_11());
				REPORT_TYPE = 3;
			}else if(InfBatchConstant.RE_R004.KEC_KL.equals(REPORT_ID)){
				reR004HashMap.put(InfBatchConstant.RE_R004.KEC_KL, ReportFileFactory.getReportFileDAO().getReportR004_12());
				REPORT_TYPE = 3;
			}else if(InfBatchConstant.RE_R004.CC_HL.equals(REPORT_ID)){
				reR004HashMap.put(InfBatchConstant.RE_R004.CC_HL, ReportFileFactory.getReportFileDAO().getReportR004_13());
				REPORT_TYPE = 2;
			}else if(InfBatchConstant.RE_R004.CC_SME.equals(REPORT_ID)){
				reR004HashMap.put(InfBatchConstant.RE_R004.CC_SME, ReportFileFactory.getReportFileDAO().getReportR004_14());
				REPORT_TYPE = 2;
			}else if(InfBatchConstant.RE_R004.CC_KL.equals(REPORT_ID)){
				reR004HashMap.put(InfBatchConstant.RE_R004.CC_KL, ReportFileFactory.getReportFileDAO().getReportR004_15());
				REPORT_TYPE = 2;
			}
			
			reR004 = reR004HashMap.get(REPORT_ID);
			
		} catch (Exception e) {
			logger.fatal("ERROR ",e);
		}
	}
	
	public MapperReR004(String inputFilePath, String outputFilePath, String REPORT_ID) {
		super(inputFilePath, outputFilePath);
		this.REPORT_ID = REPORT_ID;
	}
	
	public MapperReR004(String inputFilePath, String outputFilePath, String REPORT_ID, InfReportJobDataM infReportJob) {
		super(inputFilePath, outputFilePath);
		this.REPORT_ID = REPORT_ID;
		this.infReportJob = infReportJob;
	}

	@Override
	protected void processAction() throws Exception{
		super.processAction();
		initAction();
		MySheet sheet = new MySheet(workbook.getSheet(InfBatchConstant.ReportParam.LAYOUT));
		logger.debug("REPORT_ID >> "+REPORT_ID);
		logger.debug("REPORT_TYPE >> "+REPORT_TYPE);
		DateFormat dateFormat = new SimpleDateFormat("HH:mm:ss");
		Date date = new Date();
		String DATE = Formatter.display(InfBatchProperty.getDate(), Formatter.EN, Formatter.Format.dd_MMM_yyyy2);
		String TIME = dateFormat.format(date);
		String DATE_TIME = DATE+" "+TIME;
		sheet.setCellValue(3, "J", DATE_TIME);
		
		String DATE_HEADER = InfBatchConstant.message.DATE_HEADER_REPORT;
		String CONDITION_HEADER = InfBatchConstant.message.CONDITION_HEADER_REPORT;
		if(InfBatchUtil.empty(infReportJob)){
			String DATE_EXECUTE = Formatter.display(InfBatchProperty.getDate(), Formatter.EN, Formatter.Format.dd_MMM_yyyy);
			DATE_HEADER = DATE_HEADER.replace("{DATE_FROM}", DATE_EXECUTE);
			DATE_HEADER = DATE_HEADER.replace("{DATE_TO}", DATE_EXECUTE);
			
			CONDITION_HEADER = CONDITION_HEADER.replace("{CONDITION}", "-");
		}else{
			DATE_HEADER = DATE_HEADER.replace("{DATE_FROM}", MapperReR006.getHeaderDate(infReportJob.getDateFrom()));
			DATE_HEADER = DATE_HEADER.replace("{DATE_TO}", MapperReR006.getHeaderDate(infReportJob.getDateTo()));
			
			if(!InfBatchUtil.empty(infReportJob)){
				CONDITION_HEADER = CONDITION_HEADER.replace("{CONDITION}", MessageUtil.getConditionHeader(infReportJob));
			}else{
				CONDITION_HEADER = CONDITION_HEADER.replace("{CONDITION}", "-");
			}
		}
		sheet.setCellValue(3, "B", DATE_HEADER);
		sheet.setCellValue(4, "B", CONDITION_HEADER);
		
		sheet = new MySheet(workbook.getSheet(InfBatchConstant.ReportParam.DATA_SET));
		if(REPORT_TYPE==1){
			for(int row=3,count=0;count<8;row++,count++){
//				sheet.setCellValue(row, "A", reR004.get(count).getPeriod());
				
				sheet.setCellValue(row, "B", new BigDecimal(reR004.get(count).getCountApprove()));
				sheet.setCellValue(row, "C", new BigDecimal(reR004.get(count).getCountReject()));
				sheet.setCellValue(row, "D", new BigDecimal(reR004.get(count).getCountCancel()));
				
//				sheet.setCellValue(row+8+3+(count), "A", reR004.get(count).getPeriod());
//				sheet.setCellValue(row+8+4+(count), "A", reR004.get(count).getPeriod());
				sheet.setCellValue(row+8+3+(count), "B", new BigDecimal(reR004.get(count).getAppIn()));
				sheet.setCellValue(row+8+3+(count), "C", new BigDecimal(reR004.get(count).getWip()));
				sheet.setCellValue(row+8+4+(count), "C", new BigDecimal(reR004.get(count).getWipFollow()));
			}
			sheet = new MySheet(workbook.getSheet(InfBatchConstant.ReportParam.LAYOUT));
			for(int row=9,count=0;count<8;row++,count++){
				sheet.setCellValue(row, "D", reR004.get(count).getPercentDocComplete());
				sheet.setCellValue(row, "E", formatLineSeparator(reR004.get(count).getTop5DocNotComplete()));
				sheet.setCellValue(row, "F", formatLineSeparator(reR004.get(count).getTop5ReasonReject()));
				sheet.setCellValue(row, "G", formatLineSeparator(reR004.get(count).getTop5CauseNotComplete()));
				sheet.setCellValue(row, "H", formatLineSeparator(reR004.get(count).getVetoSubject()));
				sheet.setCellValue(row, "I", formatLineSeparator(reR004.get(count).getVetoPass()));
			}
		}else if(REPORT_TYPE==2){
			for(int row=3,count=0;count<6;row++,count++){
//				sheet.setCellValue(row, "A", reR004.get(count).getPeriod());
				sheet.setCellValue(row, "B", new BigDecimal(reR004.get(count).getCountApprove()));
				sheet.setCellValue(row, "C", new BigDecimal(reR004.get(count).getCountReject()));
				sheet.setCellValue(row, "D", new BigDecimal(reR004.get(count).getCountCancel()));
				
//				sheet.setCellValue(row+6+3+(count), "A", reR004.get(count).getPeriod());
//				sheet.setCellValue(row+6+4+(count), "A", reR004.get(count).getPeriod());
				sheet.setCellValue(row+6+3+(count), "B", new BigDecimal(reR004.get(count).getAppIn()));
				sheet.setCellValue(row+6+3+(count), "C", new BigDecimal(reR004.get(count).getWip()));
				sheet.setCellValue(row+6+4+(count), "C", new BigDecimal(reR004.get(count).getWipFollow()));
			}
			sheet = new MySheet(workbook.getSheet(InfBatchConstant.ReportParam.LAYOUT));
			for(int row=9,count=0;count<6;row++,count++){
				sheet.setCellValue(row, "D", reR004.get(count).getPercentDocComplete());
				sheet.setCellValue(row, "E", formatLineSeparator(reR004.get(count).getTop5DocNotComplete()));
				sheet.setCellValue(row, "F", formatLineSeparator(reR004.get(count).getTop5ReasonReject()));
				sheet.setCellValue(row, "G", formatLineSeparator(reR004.get(count).getTop5CauseNotComplete()));
				sheet.setCellValue(row, "H", formatLineSeparator(reR004.get(count).getVetoSubject()));
				sheet.setCellValue(row, "I", formatLineSeparator(reR004.get(count).getVetoPass()));
			}
		}else if(REPORT_TYPE==3){
			for(int row=3,count=0;count<1;row++,count++){
//				sheet.setCellValue(row, "A", reR004.get(count).getPeriod());
				sheet.setCellValue(row, "B", new BigDecimal(reR004.get(count).getCountApprove()));
				sheet.setCellValue(row, "C", new BigDecimal(reR004.get(count).getCountReject()));
				sheet.setCellValue(row, "D", new BigDecimal(reR004.get(count).getCountCancel()));
				
//				sheet.setCellValue(row+1+3+(count), "A", reR004.get(count).getPeriod());
//				sheet.setCellValue(row+1+4+(count), "A", reR004.get(count).getPeriod());
				sheet.setCellValue(row+1+3+(count), "B", new BigDecimal(reR004.get(count).getAppIn()));
				sheet.setCellValue(row+1+3+(count), "C", new BigDecimal(reR004.get(count).getWip()));
				sheet.setCellValue(row+1+4+(count), "C", new BigDecimal(reR004.get(count).getWipFollow()));
			}
			sheet = new MySheet(workbook.getSheet(InfBatchConstant.ReportParam.LAYOUT));
			for(int row=9,count=0;count<1;row++,count++){
				sheet.setCellValue(row, "D", reR004.get(count).getPercentDocComplete());
				sheet.setCellValue(row, "E", formatLineSeparator(reR004.get(count).getTop5DocNotComplete()));
				sheet.setCellValue(row, "F", formatLineSeparator(reR004.get(count).getTop5ReasonReject()));
				sheet.setCellValue(row, "G", formatLineSeparator(reR004.get(count).getTop5CauseNotComplete()));
				sheet.setCellValue(row, "H", formatLineSeparator(reR004.get(count).getVetoSubject()));
				sheet.setCellValue(row, "I", formatLineSeparator(reR004.get(count).getVetoPass()));
			}
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
	
	public ArrayList<OperatingResultDataM> getReportOR_1_5(String reportType) throws Exception{
		logger.debug("getReportOR_1_5");
		ArrayList<OperatingResultDataM> result=new ArrayList<OperatingResultDataM>();
		
		ArrayList<PeriodDataM> periodDatas=ReportFileFactory.getReportFileDAO().getPeriodDatas(reportType);
		
		int corePoolSize = CORE_POOL_SIZE;
		int maxPoolSize = MAX_POOL_SIZE;
		int queueCapacity = QUEUE_CAPACITY;
		int keepAliveTime = KEEP_ALIVE_TIME; 
		ThreadPoolExecutor executorPool = new ThreadPoolExecutor(corePoolSize,maxPoolSize,keepAliveTime,TimeUnit.MINUTES, 
				new ArrayBlockingQueue<Runnable>(queueCapacity,true));
		List<Re04Runnable> workers=new ArrayList<>();
		logger.debug("TaskManager.start");
		for(PeriodDataM periodData:periodDatas){
			Re04Runnable worker = new Re04Runnable(periodData,reportType);
			workers.add(worker);
			executorPool.execute(worker);	
		}
		
		executorPool.shutdown();
		while(!executorPool.isTerminated());
		logger.debug("TaskManager.finish");
		
		for(Re04Runnable worker : workers){
			result.addAll(worker.getOperatingResultData());
		}
		
		logger.debug("result-size-"+result.size());	
		return result;
	}
	
	
	
	

}
