package com.eaf.inf.batch.ulo.report.dao;

import java.util.ArrayList;
import java.sql.Date;
import java.util.HashMap;
import java.util.List;

import com.eaf.core.ulo.common.exception.InfBatchException;
import com.eaf.inf.batch.ulo.report.model.InfReportJobDataM;
import com.eaf.inf.batch.ulo.report.model.OperatingResultDataM;
import com.eaf.inf.batch.ulo.report.model.OverrideResultDataM;
import com.eaf.inf.batch.ulo.report.model.PeriodDataM;
import com.eaf.inf.batch.ulo.report.model.ProcessingTimeDataM;
import com.eaf.inf.batch.ulo.report.model.SlaDataM;
import com.eaf.inf.batch.ulo.report.model.AuditTrailDataM;

public interface ReportFileDAO {
	public void insertReportIntoTable(InfReportJobDataM infReportJob) throws Exception;
	public int getInfReportJob(String reportType) throws Exception;
	public List<String> getRegion() throws Exception;
	public List<String> getZone(String region) throws Exception;
	public List<String> getTeamZone() throws Exception;
	public ArrayList<SlaDataM> getReportSLA1(String P_APP_TYPE) throws Exception;
	public ArrayList<SlaDataM> getReportSLA2(String P_APP_TYPE) throws Exception;
	public ArrayList<SlaDataM> getReportSLA3(String P_APP_TYPE) throws Exception;
	public ArrayList<SlaDataM> getReportSLA4(String P_APP_TYPE) throws Exception;
	public ArrayList<ProcessingTimeDataM> getReportR012() throws Exception;
	public ArrayList<ProcessingTimeDataM> getReportR012_Top_Bottom(String role, String position) throws Exception;
	public ArrayList<ProcessingTimeDataM> getReportR012Summary() throws Exception;
	public ArrayList<OperatingResultDataM> getReportR004_01() throws Exception;
	public ArrayList<OperatingResultDataM> getReportR004_02() throws Exception;
	public ArrayList<OperatingResultDataM> getReportR004_03() throws Exception;
	public ArrayList<OperatingResultDataM> getReportR004_04() throws Exception;
	public ArrayList<OperatingResultDataM> getReportR004_05() throws Exception;
	public ArrayList<OperatingResultDataM> getReportR004_06() throws Exception;
	public ArrayList<OperatingResultDataM> getReportR004_07() throws Exception;
	public ArrayList<OperatingResultDataM> getReportR004_08() throws Exception;
	public ArrayList<OperatingResultDataM> getReportR004_09() throws Exception;
	public ArrayList<OperatingResultDataM> getReportR004_10() throws Exception;
	public ArrayList<OperatingResultDataM> getReportR004_11() throws Exception;
	public ArrayList<OperatingResultDataM> getReportR004_12() throws Exception;
	public ArrayList<OperatingResultDataM> getReportR004_13() throws Exception;
	public ArrayList<OperatingResultDataM> getReportR004_14() throws Exception;
	public ArrayList<OperatingResultDataM> getReportR004_15() throws Exception;
	public ArrayList<InfReportJobDataM> getInfReportJob() throws InfBatchException;
	public ArrayList<OperatingResultDataM> getOperatingResult(InfReportJobDataM infReportJob) throws InfBatchException;
	public ArrayList<SlaDataM> getSLA1(InfReportJobDataM infReportJob) throws InfBatchException;
	public ArrayList<SlaDataM> getSLA2(InfReportJobDataM infReportJob) throws InfBatchException;
	public ArrayList<ProcessingTimeDataM> getProcessingTime(InfReportJobDataM infReportJob) throws InfBatchException;
	public void updateInfReportJobFlag(String infReportJobId) throws InfBatchException;
	public ArrayList<ProcessingTimeDataM> getReportR012SummaryC(InfReportJobDataM infReportJob) throws Exception;
	public ArrayList<ProcessingTimeDataM> getReportR012_Top_BottomC(InfReportJobDataM infReportJob, String role, String position) throws Exception;
	public ArrayList<SlaDataM> getSLA3(InfReportJobDataM infReportJob) throws InfBatchException;
	public ArrayList<SlaDataM> getSLA4(InfReportJobDataM infReportJob) throws InfBatchException;
	public ArrayList<AuditTrailDataM> getReportR021(String P_AS_OF_DATE,String P_JOB_STATE) throws Exception;
	public void callPrepareReportData() throws Exception;
	public ArrayList<OperatingResultDataM> getF_RPT_R004_OR_1_5_PER_PERIOD(
			String productCode, String reportType, String period,
			String periodCon, String sortField) throws Exception;
	public ArrayList<PeriodDataM> getPeriod68Datas() throws Exception;
	
	public ArrayList<PeriodDataM> getPeriodDatas(String reportType) throws Exception;
	public HashMap<String, ArrayList<OverrideResultDataM>> getOverrideResult(Date asOfDate, String product) throws InfBatchException;
}
