package com.eaf.inf.batch.ulo.report.excel;

import java.util.ArrayList;

import com.eaf.inf.batch.ulo.report.dao.ReportFileFactory;
import com.eaf.inf.batch.ulo.report.model.OperatingResultDataM;
import com.eaf.inf.batch.ulo.report.model.PeriodDataM;

public class Re04Runnable implements Runnable{
	private PeriodDataM periodData=null;
	private String reportType=null;
	private ArrayList<OperatingResultDataM> operatingResultData=null;
	Re04Runnable(PeriodDataM periodData,String reportType){
		this.periodData=periodData;
		this.reportType=reportType;
	}
	
	
	@Override
	public void run() {
		// TODO Auto-generated method stub
		try {
			ArrayList<OperatingResultDataM> operatingResultData=ReportFileFactory.getReportFileDAO().getF_RPT_R004_OR_1_5_PER_PERIOD(null, reportType
					, periodData.getPeriod(), periodData.getPeriodCon(), periodData.getSortField());
			
		
			this.operatingResultData=operatingResultData;
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		
	}
	public ArrayList<OperatingResultDataM> getOperatingResultData(){
		return this.operatingResultData;
	}

}
