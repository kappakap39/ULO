package com.eaf.inf.batch.ulo.notification.eod.sendto.util;

import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;

import com.eaf.core.ulo.common.util.InfBatchUtil;
import com.eaf.inf.batch.ulo.notification.condition.NotificationCondition;
import com.eaf.inf.batch.ulo.notification.config.model.NotificationConfig;
import com.eaf.inf.batch.ulo.notification.model.JobCodeDataM;
import com.eaf.inf.batch.ulo.notification.model.NotifyEODApplicationDataM;
import com.eaf.inf.batch.ulo.notification.model.NotifyEODManagerInfoDataM;
import com.eaf.inf.batch.ulo.notification.model.NotifyEODSalesInfoDataM;
import com.eaf.inf.batch.ulo.notification.model.VCEmpInfoDataM;

public class EODSendToManagerUtil {
	private static transient Logger logger = Logger.getLogger(EODSendToManagerUtil.class);
	public static void addEODSaleInfo(List<NotifyEODSalesInfoDataM> eodSaleInfos,NotifyEODApplicationDataM notifyEOD,NotificationConfig notificationConfig){
		if(null==eodSaleInfos){
			eodSaleInfos = new ArrayList<NotifyEODSalesInfoDataM>();
		}
		String applicationGroupId = notifyEOD.getApplicationGroupId();
		String saleId = notifyEOD.getSaleId();
		String recommendId = notifyEOD.getRecommendId();
//		if(!isSaleExists(eodSaleInfos,applicationGroupId,saleId,recommendId)){
//			NotifyEODSalesInfoDataM saleInfo = new NotifyEODSalesInfoDataM();
//				saleInfo.setApplicationGroupId(applicationGroupId);
//				saleInfo.setSaleId(saleId);
//				saleInfo.setSaleChannel(notifyEOD.getSaleChannel());
//				saleInfo.setRecommend(recommendId);
//				saleInfo.setRecommendChannel(notifyEOD.getRecommendChannel());
//				saleInfo.setManagerChannel(notificationConfig.getManagerChannel());
//					JobCodeDataM jobCode = new JobCodeDataM();
//						jobCode.setJobCode(notificationConfig.getJobCode());
//						jobCode.setOptional1(notificationConfig.getOptional1());
//						jobCode.setOptional2(notificationConfig.getOptional2());
//						jobCode.setOptional3(notificationConfig.getOptional3());
//						jobCode.setOptional4(notificationConfig.getOptional4());
//						jobCode.setOptional5(notificationConfig.getOptional5());
//						jobCode.setPattern(notificationConfig.getPattern());
//				saleInfo.addJobCodes(jobCode);
//			eodSaleInfos.add(saleInfo);
//		}
		NotifyEODSalesInfoDataM saleInfo = getEODSaleInfo(eodSaleInfos, notifyEOD, notificationConfig);
		if(!InfBatchUtil.empty(saleInfo)){ // Exist
			JobCodeDataM jobCode = new JobCodeDataM();
				jobCode.setJobCode(notificationConfig.getJobCode());
				jobCode.setOptional1(notificationConfig.getOptional1());
				jobCode.setOptional2(notificationConfig.getOptional2());
				jobCode.setOptional3(notificationConfig.getOptional3());
				jobCode.setOptional4(notificationConfig.getOptional4());
				jobCode.setOptional5(notificationConfig.getOptional5());
				jobCode.setPattern(notificationConfig.getPattern());
			saleInfo.addJobCodes(jobCode);
		}else{ // New
			saleInfo = new NotifyEODSalesInfoDataM();
				saleInfo.setApplicationGroupId(applicationGroupId);
				saleInfo.setSaleId(saleId);
				saleInfo.setSaleChannel(notifyEOD.getSaleChannel());
				saleInfo.setRecommend(recommendId);
				saleInfo.setRecommendChannel(notifyEOD.getRecommendChannel());
				saleInfo.setManagerChannel(notificationConfig.getManagerChannel());
					JobCodeDataM jobCode = new JobCodeDataM();
						jobCode.setJobCode(notificationConfig.getJobCode());
						jobCode.setOptional1(notificationConfig.getOptional1());
						jobCode.setOptional2(notificationConfig.getOptional2());
						jobCode.setOptional3(notificationConfig.getOptional3());
						jobCode.setOptional4(notificationConfig.getOptional4());
						jobCode.setOptional5(notificationConfig.getOptional5());
						jobCode.setPattern(notificationConfig.getPattern());
				saleInfo.addJobCodes(jobCode);
			eodSaleInfos.add(saleInfo);
		}
	}
	public static NotifyEODSalesInfoDataM getEODSaleInfo(List<NotifyEODSalesInfoDataM> eodSaleInfos,NotifyEODApplicationDataM notifyEOD,NotificationConfig notificationConfig){
		if(InfBatchUtil.empty(eodSaleInfos)){
			return null;
		}
		String applicationGroupId = notifyEOD.getApplicationGroupId();
		String saleId = notifyEOD.getSaleId();
		String recommendId = notifyEOD.getRecommendId();
		for(NotifyEODSalesInfoDataM saleInfo : eodSaleInfos){
			if(saleInfo.getApplicationGroupId().equals(applicationGroupId) 
					&& (!InfBatchUtil.empty(saleInfo.getSaleId()) && saleInfo.getSaleId().equals(saleId))
					&& ((InfBatchUtil.empty(saleInfo.getRecommend()) && InfBatchUtil.empty(recommendId)) || (!InfBatchUtil.empty(saleInfo.getRecommend()) && saleInfo.getRecommend().equals(recommendId)))){
				return saleInfo;
			}
		}
		return null;
	}
	public static boolean isSaleExists(List<NotifyEODSalesInfoDataM> eodSaleInfos,String applicationGroupId,String saleId,String recommendId){
		if(InfBatchUtil.empty(eodSaleInfos)){
			return false;
		}
		for(NotifyEODSalesInfoDataM saleInfo : eodSaleInfos){
//			if(saleInfo.getApplicationGroupId().equals(applicationGroupId) 
//					&& saleInfo.getSaleId().equals(saleId) 
//					&& saleInfo.getRecommend().equals(recommendId)){
//				return true;
//			}
			if(saleInfo.getApplicationGroupId().equals(applicationGroupId) 
					&& (!InfBatchUtil.empty(saleInfo.getSaleId()) && saleInfo.getSaleId().equals(saleId))
					&& ((InfBatchUtil.empty(saleInfo.getRecommend()) && InfBatchUtil.empty(recommendId)) || (!InfBatchUtil.empty(saleInfo.getRecommend()) && saleInfo.getRecommend().equals(recommendId)))){
				return true;
			}
		}
		return false;
	}
	public static NotifyEODManagerInfoDataM addManagerOfSeller(List<NotifyEODManagerInfoDataM> eodManagerInfos,NotifyEODSalesInfoDataM eodSaleInfo)throws Exception{
		String saleId = eodSaleInfo.getSaleId();
		String saleChannel = eodSaleInfo.getSaleChannel();
		String managerChannel = eodSaleInfo.getManagerChannel();
		ArrayList<JobCodeDataM> jobCodes = eodSaleInfo.getJobCodes();
		
		return addManagerInfo(eodManagerInfos,saleId,saleChannel,managerChannel,jobCodes);
	}
	public static NotifyEODManagerInfoDataM addManagerOfRecommend(List<NotifyEODManagerInfoDataM> eodManagerInfos,NotifyEODSalesInfoDataM eodSaleInfo)throws Exception{
		String saleId = eodSaleInfo.getRecommend();
		String saleChannel = eodSaleInfo.getRecommendChannel();
		String managerChannel = eodSaleInfo.getManagerChannel();
		ArrayList<JobCodeDataM> jobCodes = eodSaleInfo.getJobCodes();
		
		return addManagerInfo(eodManagerInfos,saleId,saleChannel,managerChannel,jobCodes);
	}
	private static NotifyEODManagerInfoDataM addManagerInfo(List<NotifyEODManagerInfoDataM> eodManagerInfos,String saleId,String saleChannel,String managerChannel,ArrayList<JobCodeDataM> jobCodes)throws Exception{
		NotifyEODManagerInfoDataM managerInfo = null;
		try{
			if(null==eodManagerInfos){
				eodManagerInfos = new ArrayList<NotifyEODManagerInfoDataM>();
			}
			if(!isManagerExists(eodManagerInfos,saleId) && saleChannel.equals(managerChannel)){
				NotificationCondition notificationCondition = new NotificationCondition();
				ArrayList<VCEmpInfoDataM> empSaleManagers = notificationCondition.getSendToVCEmpManagers(saleId,jobCodes);
				logger.debug("empSaleManagers : "+empSaleManagers);
				if(!InfBatchUtil.empty(empSaleManagers)){
					managerInfo = new NotifyEODManagerInfoDataM();
					 	managerInfo.setSaleId(saleId);
					 	managerInfo.setManageInfos(empSaleManagers);
					 eodManagerInfos.add(managerInfo);
				}
			}
		}catch(Exception e){
			logger.fatal("ERROR",e);
			throw new Exception(e);
		}
		return managerInfo;
	}
	public static boolean isManagerExists(List<NotifyEODManagerInfoDataM> eodManagerInfos,String saleId){
		if(InfBatchUtil.empty(eodManagerInfos)){
			return false;
		}
		for(NotifyEODManagerInfoDataM managerInfo : eodManagerInfos){
			if(managerInfo.getSaleId().equals(saleId)){
				return true;
			}
		}
		return false;
	}
}
