package com.eaf.orig.ulo.app.view.form.manual;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;

import com.eaf.core.ulo.common.cache.InfBatchProperty;
import com.eaf.core.ulo.common.cont.InfBatchConstant;
import com.eaf.core.ulo.common.display.HtmlUtil;
import com.eaf.core.ulo.common.message.LabelUtil;
import com.eaf.core.ulo.common.model.ResponseData;
import com.eaf.core.ulo.common.properties.SystemConstant;
import com.eaf.core.ulo.common.util.Util;
import com.eaf.inf.batch.ulo.memo.model.OperatingResultReportDataM;
import com.eaf.inf.batch.ulo.report.dao.ReportFileFactory;
import com.eaf.j2ee.pattern.control.AjaxInf;
import com.eaf.j2ee.pattern.control.ResponseDataController;
import com.eaf.orig.profile.model.UserDetailM;
import com.eaf.orig.ulo.app.view.util.report.dao.ReportDAO;
import com.eaf.orig.ulo.app.view.util.report.dao.ReportDAOImpl;
import com.eaf.orig.ulo.app.view.util.report.model.InfReportJobDataM;
import com.google.gson.Gson;

public class GenerateOperatingReportForm implements AjaxInf{
	private static transient Logger logger = Logger.getLogger(GenerateOperatingReportForm.class);
	String OUTPUT_PATH = InfBatchConstant.ReportParam.OUTPUT_PATH;
	String OUTPUT_NAME = InfBatchConstant.ReportParam.OUTPUT_NAME;
	String REPORT_ON_REQUEST = InfBatchConstant.ReportParam.REPORT_ON_REQUEST;
	String MAX_TASK = InfBatchConstant.ReportParam.MAX_TASK;
	String RE_R004_ON_REQUEST_MAX_TASK = InfBatchProperty.getInfBatchConfig("RE_R004_ON_REQUEST_MAX_TASK");
	String RE_R006_ON_REQUEST_MAX_TASK = InfBatchProperty.getInfBatchConfig("RE_R006_ON_REQUEST_MAX_TASK");
	String RE_R012_ON_REQUEST_MAX_TASK = InfBatchProperty.getInfBatchConfig("RE_R012_ON_REQUEST_MAX_TASK");
	String FLAG_YES = SystemConstant.getConstant("FLAG_YES");
	String APPLICATION_DATE = SystemConstant.getConstant("APPLICATION_DATE");
	String LASTDECISION_DATE = SystemConstant.getConstant("LASTDECISION_DATE");
	String CHANNEL_S = SystemConstant.getConstant("CHANNEL_S");
	String CHANNEL_D = SystemConstant.getConstant("CHANNEL_D");
	String CHANNEL_O = SystemConstant.getConstant("CHANNEL_O");
	String COMPLETE_REPORT_RADIO_A = SystemConstant.getConstant("COMPLETE_REPORT_RADIO_A");
	String COMPLETE_REPORT_RADIO_Y = SystemConstant.getConstant("COMPLETE_REPORT_RADIO_Y");
	String COMPLETE_REPORT_RADIO_N = SystemConstant.getConstant("COMPLETE_REPORT_RADIO_N");
	String REPORT_PRODUCT = SystemConstant.getConstant("REPORT_PRODUCT");
	String REPORT_VALUE_ALL = SystemConstant.getConstant("REPORT_VALUE_ALL");	
	@Override
	public ResponseData processAction(HttpServletRequest request){
		ResponseDataController responseData = new ResponseDataController(request,ResponseData.FunctionId.GENERATE_OPERATING_REPORT);		
		try{
			ReportDAO report = new ReportDAOImpl();
			String reportType = request.getParameter("REPORT_TYPE");
			boolean isGenerateReport = isGenerateReport(reportType);
			logger.debug("isGenerateReport >> "+isGenerateReport);
			if(isGenerateReport){
				String GENERATE_BUTTON_FLAG = request.getParameter("GENERATE_BUTTON_FLAG");
				logger.debug("GENERATE_BUTTON_FLAG >> "+GENERATE_BUTTON_FLAG);
				String dateType = request.getParameter("DATE_TYPE_RADIO");
				String dateFrom = request.getParameter("DATE_FROM_CALENDAR");
				String dateTo = request.getParameter("DATE_TO_CALENDAR");
				String projectCode = request.getParameter("PROJECT_NO_BOX");
				String[] productCriterias = request.getParameterValues("PRODUCT_TYPE_BOX");
				String[] branchRegions = request.getParameterValues("BRANCH_REGION_TRANSFER");
				String[] branchZones = request.getParameterValues("BRANCH_ZONE_TRANSFER");
				String[] dsaZones = request.getParameterValues("DSA_EXPAND_ZONE_TRANSFER");
				String[] nbdZones = request.getParameterValues("NBD_ZONE_TRANSFER");
				String[] channels = request.getParameterValues("CHANNEL_TYPE_BOX");
				String docCompleteFlag = request.getParameter("COMPLETE_REPORT_RADIO");
				String stationFrom = request.getParameter("STATION_FROM_DROPDOWN");
				String stationTo = request.getParameter("STATION_TO_DROPDOWN");
				String[] applicationStatusArray = request.getParameterValues("STATUS_BOX");
				
				if(FLAG_YES.equals(GENERATE_BUTTON_FLAG)){
					String productCriteria = null;
					String branchRegion = null;
					String branchZone = null;
					String dsaZone = null;
					String nbdZone = null;
					String channel = null;
					String applicationStatus = null;
					if(!Util.empty(productCriterias)){
						productCriteria = StringUtils.join(productCriterias, ",");
					}					
					if(!Util.empty(branchRegions)){
						branchRegion = "'"+StringUtils.join(branchRegions, "','")+"'";
					}else{
						logger.debug("branchRegions is null");
						branchRegion = request.getParameter("BRANCH_REGION_TRANSFER");				
					}					
					logger.debug("branchRegion >> "+branchRegion);					
					if(!Util.empty(branchZones)){
						if(REPORT_VALUE_ALL.equals(branchZones[0])){
							branchZone = branchZones[0];
						}else{
							branchZone = "'"+StringUtils.join(branchZones, "','")+"'";
						}
					}
					if(!Util.empty(dsaZones)){
						if(REPORT_VALUE_ALL.equals(dsaZones[0])){
							dsaZone = dsaZones[0];
						}else{
							dsaZone = "'"+StringUtils.join(dsaZones, "','")+"'";
						}
					}
					if(!Util.empty(nbdZones)){
						if(REPORT_VALUE_ALL.equals(nbdZones[0])){
							nbdZone = nbdZones[0];
						}else{
							nbdZone = "'"+StringUtils.join(nbdZones, "','")+"'";
						}
					}
					if(!Util.empty(channels)){
						channel = StringUtils.join(channels, ",");
					}
					
					if(!Util.empty(applicationStatusArray)){
						applicationStatus = StringUtils.join(applicationStatusArray,',');
					}
					
					UserDetailM userM = (UserDetailM) request.getSession().getAttribute("ORIGUser");
					String userId = userM.getUserName();
					InfReportJobDataM infReportJob = new InfReportJobDataM();
					infReportJob.setDateType(dateType);
					infReportJob.setDateFrom(dateFrom);
					infReportJob.setDateTo(dateTo);
					infReportJob.setProjectCode(projectCode);
					infReportJob.setProductCriteria(productCriteria);
					infReportJob.setBranchRegion(branchRegion);
					infReportJob.setBranchZone(branchZone);
					infReportJob.setDsaZone(dsaZone);
					infReportJob.setNbdZone(nbdZone);
					infReportJob.setChannel(channel);
					infReportJob.setReportType(reportType);
					infReportJob.setDocFirstCompleteFlag(docCompleteFlag);
					infReportJob.setStationFrom(stationFrom);
					infReportJob.setStationTo(stationTo);
					infReportJob.setApplicationStatus(applicationStatus);
					infReportJob.setCREATE_BY(userId);
					
					report.insertReportIntoTable(infReportJob);
				}
				logger.debug("reportType >> "+reportType);
			}
			ArrayList<OperatingResultReportDataM> reportList = getReport(request, reportType);
			HashMap<String, Object> data = new HashMap<>();
			data.put("reportList", reportList);
			data.put("isGenerateReport",isGenerateReport);
			return responseData.success(new Gson().toJson(data));
		}catch(Exception e){
			logger.fatal("ERROR ",e);
			return responseData.error(e);
		}
	}
	public ArrayList<OperatingResultReportDataM> getReport(HttpServletRequest request,String reportType) throws Exception{
		ReportDAO report = new ReportDAOImpl();
		ArrayList<OperatingResultReportDataM> dataList = new ArrayList<OperatingResultReportDataM>();
		ArrayList<InfReportJobDataM> reportList = report.getReportData(reportType);
		for(InfReportJobDataM reportData : reportList){
			OperatingResultReportDataM data = new OperatingResultReportDataM();
			if(!Util.empty(reportList)){
				String criteria = "";
				data.setDateFrom(reportData.getDateFrom());
				data.setDateTo(reportData.getDateTo());
				
				if(!Util.empty(reportData.getDateType())){
					String dateTypeDesc = "";
					String dateType = reportData.getDateType();
					if(APPLICATION_DATE.equals(dateType)){
						dateTypeDesc = LabelUtil.getText(request,"DATE_TYPE_APPLICATION_DATE");
					}else if(LASTDECISION_DATE.equals(dateType)){
						dateTypeDesc = LabelUtil.getText(request,"DATE_TYPE_LASTDECISION_DATE");
					}
					criteria += getTag(LabelUtil.getText(request,"PT_REPORT_DATE_TYPE")+ 
							" : "+dateTypeDesc);
					criteria += "<br>";
				}
				
				if(!Util.empty(reportData.getDateFrom()) && !Util.empty(reportData.getDateTo())){
					
					criteria += getTag(LabelUtil.getText(request,"PT_REPORT_DATE_FROM")+ 
							" : "+reportData.getDateFrom()+" "+LabelUtil.getText(request,"PT_REPORT_DATE_TO")+ 
							" : "+reportData.getDateTo());
					criteria += "<br>";
				}
				
				if(!Util.empty(reportData.getProjectCode())){
					criteria += getTag(LabelUtil.getText(request,"PT_REPORT_PROJECT_NO")+ 
							" : "+reportData.getProjectCode());
					criteria += "<br>";
				}
				
				if(!Util.empty(reportData.getDocFirstCompleteFlag())){
					String docCompleteDesc = "";
					String docCompleteFlag = reportData.getDocFirstCompleteFlag();
					if(COMPLETE_REPORT_RADIO_A.equals(docCompleteFlag)){
						docCompleteDesc = LabelUtil.getText(request,"COMPLETE_REPORT_A");
					}else if(COMPLETE_REPORT_RADIO_Y.equals(docCompleteFlag)){
						docCompleteDesc = LabelUtil.getText(request,"COMPLETE_REPORT_RADIO_Y");
					}else if(COMPLETE_REPORT_RADIO_N.equals(docCompleteFlag)){
						docCompleteDesc = LabelUtil.getText(request,"COMPLETE_REPORT_RADIO_N");
					}
					criteria += getTag(LabelUtil.getText(request,"PT_REPORT_COMPLETE")+ 
							" : "+docCompleteDesc);
					criteria += "<br>";
				}
				
				if(!Util.empty(reportData.getStationFrom()) && !Util.empty(reportData.getStationTo())){
					criteria += getTag(LabelUtil.getText(request,"PT_REPORT_STATION_FROM")+ 
							" : "+reportData.getStationFrom())+ " "+LabelUtil.getText(request,"PT_REPORT_STATION_TO")+ 
							" : "+reportData.getStationTo()	;
					criteria += "<br>";
				}
				
				if(!Util.empty(reportData.getApplicationStatus())){
					criteria += getTag(LabelUtil.getText(request,"PT_REPORT_FINAL")+ 
							" : "+reportData.getApplicationStatus());
					criteria += "<br>";
				}
				
				if(!Util.empty(reportData.getProductCriteria())){
					criteria += getTag(LabelUtil.getText(request,"PT_REPORT_PRODUCT"));
					criteria += "<br>";
					criteria += getShiftTag1(LabelUtil.getText(request,"PRODUCT")+ 
							" : "+getProductDescription(reportData.getProductCriteria(), request));
				}
									
				if(!Util.empty(reportData.getBranchRegion()) || !Util.empty(reportData.getBranchZone())
						|| !Util.empty(reportData.getBranchZone()) || !Util.empty(reportData.getDsaZone())
						|| !Util.empty(reportData.getNbdZone()) || !Util.empty(reportData.getChannel())){
					criteria += getTag(LabelUtil.getText(request,"PT_REPORT_CHANNEL"));
					criteria += "<br>";
				
					if(!Util.empty(reportData.getBranchRegion()) || !Util.empty(reportData.getBranchZone())){
						criteria += getShiftTag1(LabelUtil.getText(request, "CHANNEL_BRANCH"));
						if(!Util.empty(reportData.getBranchRegion())){
							criteria += getShiftTag2(LabelUtil.getText(request,"PT_REPORT_REGION")+ 
									" : "+reportData.getBranchRegion());
						}
						
						if(!Util.empty(reportData.getBranchZone())){
							criteria += getShiftTag2(LabelUtil.getText(request,"PT_REPORT_ZONE")+ 
									" : "+reportData.getBranchZone());
						}
					}
					
					if(!Util.empty(reportData.getDsaZone())){
						criteria += getShiftTag1(LabelUtil.getText(request, "CHANNEL_DSA"));
						criteria += getShiftTag2(LabelUtil.getText(request,"PT_REPORT_EXPAND_ZONE")+ 
								" : "+reportData.getDsaZone());
					}
					
					
					if(!Util.empty(reportData.getNbdZone())){
						criteria += getShiftTag1(LabelUtil.getText(request, "CHANNEL_NBD"));
						criteria += getShiftTag2(LabelUtil.getText(request,"PT_REPORT_ZONE")+ 
								" : "+reportData.getNbdZone());
					}
					
					if(!Util.empty(reportData.getChannel())){
						String[] channels = reportData.getChannel().split(",");
						List<String> channelDesc = new ArrayList<>();
						for(String channel : channels){
							if(CHANNEL_S.equals(channel)){
								channelDesc.add(HtmlUtil.getText(request, "CHANNEL_S"));	
							}else if(CHANNEL_D.equals(channel)){
								channelDesc.add(HtmlUtil.getText(request, "CHANNEL_D"));
							}else if(CHANNEL_O.equals(channel)){
								channelDesc.add(HtmlUtil.getText(request, "CHANNEL_O"));
							}
						}
						criteria += getShiftTag1(LabelUtil.getText(request,"CHANNEL")+ 
								" : "+StringUtils.join(channelDesc, ','));
					}
				}
				
				criteria = StringUtils.replace(criteria, "\'", "");
				criteria = StringUtils.replace(criteria, ",", ", ");
				
				data.setProductCriteria(criteria);
				data.setCreateBy(reportData.getCREATE_BY());
				data.setCreateDate(reportData.getCREATE_DATE());
				dataList.add(data);
			}
		}
		return dataList;
	}
	
	public boolean isGenerateReport(String reportType){
		try {
			int LIMIT_TASK = getLimitTask(reportType);
			int TASK = ReportFileFactory.getReportFileDAO().getInfReportJob(reportType);
			logger.debug("LIMIT_TASK >> "+LIMIT_TASK);
			logger.debug("TASK >> "+TASK);
			if(LIMIT_TASK <= TASK){
				return false;
			}
		} catch (Exception e) {
			logger.fatal("ERROR ",e);
		}
		return true;
	}
	
	public int getLimitTask(String reportType){
		int LIMIT_TASK = Integer.parseInt(InfBatchProperty.getInfBatchConfig(reportType+"_"+REPORT_ON_REQUEST+"_"+MAX_TASK));
		return LIMIT_TASK;
	}
	
	public String getTag(String text){
		String tag = "<span style='white-space:normal;'>";
		tag += text;
		tag += "</span>";
		return tag;
	}
	public String getShiftTag1(String text){
		String tag = "<div style=\"white-space:normal; margin-left:20px;\">";
		tag += text;
		tag += "</div>";
		return tag;
	}
	public String getShiftTag2(String text){
		String tag = "<div style=\"white-space:normal; margin-left:40px;\">";
		tag += text;
		tag += "</div>";
		return tag;
	}	
	String getProductDescription(String productCriterias, HttpServletRequest request){
		String productDescription = "";
		productCriterias = StringUtils.replace(productCriterias, "'", "");
		String[] products = StringUtils.split(productCriterias,',');
		List<String> productDescriptions = new ArrayList<>();
		for(String product : products){
			productDescription = LabelUtil.getText(request, REPORT_PRODUCT+"_"+product);
			productDescriptions.add(productDescription);
		}
		productDescription = StringUtils.join(productDescriptions, ',');
		logger.debug("productDescription >> "+productDescription);
		return productDescription;
	}
}
