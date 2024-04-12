package com.eaf.core.ulo.common.util;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;

import com.eaf.core.ulo.common.cache.InfBatchProperty;
import com.eaf.core.ulo.common.cont.InfBatchConstant;
import com.eaf.core.ulo.common.properties.SystemConstant;
import com.eaf.inf.batch.ulo.report.model.InfReportJobDataM;

public class MessageUtil {
	private static transient Logger logger = Logger.getLogger(MessageUtil.class);
	
	public static String getConditionHeader(InfReportJobDataM infReportJob){
//		String APPLICATION_DATE = InfBatchProperty.getInfBatchConfig("APPLICATION_DATE");
//		String LASTDECISION_DATE = InfBatchProperty.getInfBatchConfig("LASTDECISION_DATE");
//		String COMPLETE_REPORT_RADIO_A = InfBatchProperty.getInfBatchConfig("COMPLETE_REPORT_RADIO_A");
//		String COMPLETE_REPORT_RADIO_Y = InfBatchProperty.getInfBatchConfig("COMPLETE_REPORT_RADIO_Y");
//		String COMPLETE_REPORT_RADIO_N = InfBatchProperty.getInfBatchConfig("COMPLETE_REPORT_RADIO_N");
//		String REPORT_PRODUCT = InfBatchProperty.getInfBatchConfig("REPORT_PRODUCT");

		String APPLICATION_DATE = SystemConstant.getConstant("APPLICATION_DATE");
		String LASTDECISION_DATE = SystemConstant.getConstant("LASTDECISION_DATE");
		String COMPLETE_REPORT_RADIO_A = SystemConstant.getConstant("COMPLETE_REPORT_RADIO_A");
		String COMPLETE_REPORT_RADIO_Y = SystemConstant.getConstant("COMPLETE_REPORT_RADIO_Y");
		String COMPLETE_REPORT_RADIO_N = SystemConstant.getConstant("COMPLETE_REPORT_RADIO_N");
		String REPORT_PRODUCT = SystemConstant.getConstant("REPORT_PRODUCT");
		
		
		
		String conditionHeader = "";
		String field = "";
		List<String> conditions = new ArrayList<>();
		
		if(!InfBatchUtil.empty(infReportJob.getDateType())){
			field = InfBatchConstant.ReportCondition.DATE_TYPE;
			logger.debug("description.DATE_TYPE >> "+field);
			String dateType = infReportJob.getDateType();
			String dateTypeDesc = "";
			if(APPLICATION_DATE.equals(dateType)){
				dateTypeDesc = InfBatchConstant.ReportCondition.DATE_TYPE_APPLICATION_DATE;
			}else if(LASTDECISION_DATE.equals(dateType)){
				dateTypeDesc = InfBatchConstant.ReportCondition.DATE_TYPE_LASTDECISION_DATE;
			}
			conditions.add(field+" : "+dateTypeDesc);
		}
		
		if(!InfBatchUtil.empty(infReportJob.getProjectCode())){
			field = InfBatchConstant.ReportCondition.PROJECT_NO;
			conditions.add(field+" : "+infReportJob.getProjectCode());
		}
		
		if(!InfBatchUtil.empty(infReportJob.getDocFirstCompleteFlag())){
			field = InfBatchConstant.ReportCondition.COMPLETE_REPORT;
			String docCompleteDesc = "";
			String docCompleteFlag = infReportJob.getDocFirstCompleteFlag();
			if(COMPLETE_REPORT_RADIO_A.equals(docCompleteFlag)){
				docCompleteDesc = InfBatchConstant.ReportCondition.COMPLETE_REPORT_A;
			}else if(COMPLETE_REPORT_RADIO_Y.equals(docCompleteFlag)){
				docCompleteDesc = InfBatchConstant.ReportCondition.COMPLETE_REPORT_Y;
			}else if(COMPLETE_REPORT_RADIO_N.equals(docCompleteFlag)){
				docCompleteDesc = InfBatchConstant.ReportCondition.COMPLETE_REPORT_N;
			}
			conditions.add(field+" : "+docCompleteDesc);
		}

		if(!InfBatchUtil.empty(infReportJob.getStationFrom()) && !InfBatchUtil.empty(infReportJob.getStationTo())){
			field = InfBatchConstant.ReportCondition.STATION_FROM;
			String stationDesc = field+" : "+infReportJob.getStationFrom()+" ";
			field = InfBatchConstant.ReportCondition.STATION_TO;
			stationDesc += field+" : "+infReportJob.getStationTo();
			conditions.add(stationDesc);
		}
				
		if(!InfBatchUtil.empty(infReportJob.getApplicationStatus())){
			field = InfBatchConstant.ReportCondition.REPORT_FINAL;
			conditions.add(field+" : "+infReportJob.getApplicationStatus());
		}
		
		if(!InfBatchUtil.empty(infReportJob.getProductCriteria())){
			field = InfBatchConstant.ReportCondition.BY_PRODUCT;
			conditions.add(field);
			field = InfBatchConstant.ReportCondition.PRODUCT;
			conditions.add("     "+field+" : "+getProductDescription(infReportJob.getProductCriteria()));
		}
		
		if(!InfBatchUtil.empty(infReportJob.getBranchRegion()) || !InfBatchUtil.empty(infReportJob.getBranchZone())
				|| !InfBatchUtil.empty(infReportJob.getDsaZone()) || !InfBatchUtil.empty(infReportJob.getNbdZone())
				|| !InfBatchUtil.empty(infReportJob.getNbdZone()) || !InfBatchUtil.empty(infReportJob.getChannel())){
			field = InfBatchConstant.ReportCondition.BY_CHANNEL;
			conditions.add(field);
			if(!InfBatchUtil.empty(infReportJob.getBranchRegion())){
				field = InfBatchConstant.ReportCondition.CHANNEL_BRANCH+" "+InfBatchConstant.ReportCondition.CHANNEL_BRANCH_REGION;
				conditions.add("     "+field+" : "+infReportJob.getBranchRegion());
			}
			
			if(!InfBatchUtil.empty(infReportJob.getBranchZone())){
				field = InfBatchConstant.ReportCondition.CHANNEL_BRANCH+" "+InfBatchConstant.ReportCondition.CHANNEL_BRANCH_ZONE;
				conditions.add("     "+field+" : "+infReportJob.getBranchZone());
			}
			
			if(!InfBatchUtil.empty(infReportJob.getDsaZone())){
				field = InfBatchConstant.ReportCondition.CHANNEL_DSA+" "+InfBatchConstant.ReportCondition.CHANNEL_DSA_EXPAND_ZONE;
				conditions.add("     "+field+" : "+infReportJob.getDsaZone());
			}
			
			if(!InfBatchUtil.empty(infReportJob.getNbdZone())){
				field = InfBatchConstant.ReportCondition.CHANNEL_NBD+" "+InfBatchConstant.ReportCondition.CHANNEL_NBD_ZONE;
				conditions.add("     "+field+" : "+infReportJob.getNbdZone());
			}
			
			if(!InfBatchUtil.empty(infReportJob.getChannel())){
				field = InfBatchConstant.ReportCondition.CHANNEL;
				conditions.add("     "+field+" : "+getChannelDescription(infReportJob.getChannel()));
			}
		}
		
		conditionHeader = StringUtils.join(conditions, System.lineSeparator());
		conditionHeader = StringUtils.replace(conditionHeader, "\'", "");
		conditionHeader = StringUtils.replace(conditionHeader, ",", ", ");
		logger.debug("conditionHeader >> "+conditionHeader);
		return conditionHeader;
	}
	
	private static String getProductDescription(String productCriterias){
		String productDescription = "";
		productCriterias = StringUtils.replace(productCriterias, "'", "");
		String[] products = StringUtils.split(productCriterias,',');
		List<String> productDescriptions = new ArrayList<>();
		for(String product : products){
			productDescription = InfBatchProperty.getInfBatchConfig("PRODUCT"+"_"+product);
			productDescriptions.add(productDescription);
		}
		productDescription = StringUtils.join(productDescriptions, ',');
		logger.debug("productDescription >> "+productDescription);
		return productDescription;
	}
	
	private static String getChannelDescription(String channels){
		String channelDescription = "";
		String[] channelArray = StringUtils.split(channels,',');
		List<String> channelList = new ArrayList<String>();
		for(String channel : channelArray){
			channelDescription = InfBatchProperty.getInfBatchConfig("CHANNEL"+"_"+channel);
			channelList.add(channelDescription);
		}
		channelDescription = StringUtils.join(channelList, ',');
		logger.debug("channelDescription >> "+channelDescription);
		return channelDescription;
	}
}
