package com.eaf.inf.batch.ulo.notification.condition;

import java.util.ArrayList;
import java.util.HashMap;

import org.apache.log4j.Logger;

import com.eaf.core.ulo.common.cache.InfBatchProperty;
import com.eaf.core.ulo.common.util.InfBatchUtil;
import com.eaf.core.ulo.service.notify.model.EmailRecipientDataM;
import com.eaf.core.ulo.service.notify.model.RecipientTypeDataM;
import com.eaf.inf.batch.ulo.notification.dao.NotificationDAO;
import com.eaf.inf.batch.ulo.notification.dao.NotificationFactory;
import com.eaf.inf.batch.ulo.notification.model.JobCodeDataM;
import com.eaf.inf.batch.ulo.notification.model.VCEmpInfoDataM;
public class NotificationCondition {
	private static transient Logger logger = Logger.getLogger(NotificationCondition.class);	
	String NOTIFICATION_PATTERN_VALUE_2 = InfBatchProperty.getInfBatchConfig("NOTIFICATION_PATTERN_VALUE_2");
	public ArrayList<VCEmpInfoDataM> getSendToVCEmpManagers(String saleId,ArrayList<JobCodeDataM> jobCodes) throws Exception{
		logger.debug("saleId : "+saleId);
		logger.debug("jobCodes : "+jobCodes);
		ArrayList<VCEmpInfoDataM> empManagers = new ArrayList<VCEmpInfoDataM>();
		 if(!InfBatchUtil.empty(saleId)){
			 NotificationDAO  notificationDAO = NotificationFactory.getNotificationDAO();
			 VCEmpInfoDataM empManagerInfo =  notificationDAO.selectVCEmpManagerInfo(saleId);
			 logger.debug("empManagerInfo : "+empManagerInfo);
			 if(!InfBatchUtil.empty(empManagerInfo)){	 
				HashMap<String,ArrayList<VCEmpInfoDataM>> headEmpHash = notificationDAO.selectVCEmpInfoJobCode(empManagerInfo.getEmpId(),jobCodes);		
//				logger.debug("headEmpHash : "+headEmpHash);
				if(!InfBatchUtil.empty(headEmpHash) && headEmpHash.size()>0){
					//Check DEPT_ID[S] =DEPT_ID [H]  AND JOB_CODE[S] = JOB_CODE[H]
					logger.debug("DeptId : "+empManagerInfo.getDeptId());
					empManagers = getSendToVCEmpDataCodition(jobCodes, headEmpHash, empManagerInfo.getDeptId());
					logger.debug("empManagers.DeptId : "+empManagers);
					//Check DEPT_ID[S] = SUB_UNIT_CNTR_CD[S]			
					String deptId = empManagerInfo.getDeptId();
					logger.debug("SubUnitCntrCd : "+empManagerInfo.getSubUnitCntrCd());
					if(InfBatchUtil.empty(empManagers) && null!=deptId&&deptId.equals(empManagerInfo.getSubUnitCntrCd())){
						empManagers = getSendToVCEmpDataCodition(jobCodes, headEmpHash, empManagerInfo.getUnitCntrCd());
					}
					logger.debug("empManagers.SubUnitCntrCd : "+empManagers);
					// check PRN_DEPT_ID[S] = SUB_UNIT_CNTR_CD[S]
					logger.debug("PrnDeptId : "+empManagerInfo.getPrnDeptId());
					String prntDeptId = empManagerInfo.getPrnDeptId();
					// FIX: Parent dept id should be compared to Unit Center ID (this is the case that Sub Unit ID is not set
					// then, determine that parent is unit level or not
					if(InfBatchUtil.empty(empManagers) &&null!=prntDeptId&&prntDeptId.equals(empManagerInfo.getUnitCntrCd())){
						empManagers = getSendToVCEmpDataCodition(jobCodes, headEmpHash, empManagerInfo.getUnitCntrCd());
					}
					logger.debug("empManagers.PrnDeptId : "+empManagers);
					//DEPT_CNTR_CD[S] = DEPT_CNTR_CD[H] #Case Pattern
					if(InfBatchUtil.empty(empManagers)){
						empManagers = getSendToVCEmpDataPatternCodition(jobCodes, empManagerInfo, headEmpHash);
					}
					logger.debug("empManagers.headEmpHash : "+empManagers);
				}
			 }
		 }
		 logger.debug("empManagers : "+empManagers);
		 return empManagers;
	}
	
	private ArrayList<VCEmpInfoDataM> getSendToVCEmpDataPatternCodition(ArrayList<JobCodeDataM> jobList,VCEmpInfoDataM vcEmpMainCondition,HashMap<String, ArrayList<VCEmpInfoDataM>> hVCEmpInfoData){
		ArrayList<VCEmpInfoDataM> sendToVCEmpInfoList = new ArrayList<VCEmpInfoDataM>();
		for(JobCodeDataM jobData : jobList){
			if(NOTIFICATION_PATTERN_VALUE_2.equals(jobData.getPattern())){
				String KEY_PATTERN ="PATTERN_"+vcEmpMainCondition.getDeptCntrCd()+"_"+jobData.getJobCode();
				ArrayList<VCEmpInfoDataM> vcEmpInfos =  hVCEmpInfoData.get(KEY_PATTERN);
				if(!InfBatchUtil.empty(vcEmpInfos)){
					sendToVCEmpData(vcEmpInfos,sendToVCEmpInfoList);
				}
			}
		}
		return sendToVCEmpInfoList;
	}
	private ArrayList<VCEmpInfoDataM> getSendToVCEmpDataCodition(ArrayList<JobCodeDataM> jobList,HashMap<String, ArrayList<VCEmpInfoDataM>> hVCEmpInfoData,String suffix ){
		ArrayList<VCEmpInfoDataM> sendToVCEmpInfoList = new ArrayList<VCEmpInfoDataM>();
		if(!InfBatchUtil.empty(jobList)){
			for(JobCodeDataM jobData : jobList){
				String KEY_JOB_CODE=jobData.getJobCode()+"_"+suffix;
				String KEY_OPTIONAL1=jobData.getOptional1()+"_"+suffix;
				String KEY_OPTIONAL2=jobData.getOptional2()+"_"+suffix;
				String KEY_OPTIONAL3=jobData.getOptional3()+"_"+suffix;
				String KEY_OPTIONAL4=jobData.getOptional4()+"_"+suffix;
				String KEY_OPTIONAL5=jobData.getOptional4()+"_"+suffix;
				ArrayList<VCEmpInfoDataM> vcEmpJobCode =  hVCEmpInfoData.get(KEY_JOB_CODE);
				ArrayList<VCEmpInfoDataM> vcEmpOptional1 =  hVCEmpInfoData.get(KEY_OPTIONAL1);
				ArrayList<VCEmpInfoDataM> vcEmpOptional2 =  hVCEmpInfoData.get(KEY_OPTIONAL2);
				ArrayList<VCEmpInfoDataM> vcEmpOptional3 =  hVCEmpInfoData.get(KEY_OPTIONAL3);
				ArrayList<VCEmpInfoDataM> vcEmpOptional4 =  hVCEmpInfoData.get(KEY_OPTIONAL4);
				ArrayList<VCEmpInfoDataM> vcEmpOptional5 =  hVCEmpInfoData.get(KEY_OPTIONAL5);
				
				if(!InfBatchUtil.empty(vcEmpJobCode)){
					sendToVCEmpData(vcEmpJobCode,sendToVCEmpInfoList);
					break;
				}else if(!InfBatchUtil.empty(vcEmpOptional1)){
					sendToVCEmpData(vcEmpOptional1,sendToVCEmpInfoList);
					break;
				}else if(!InfBatchUtil.empty(vcEmpOptional2)){
					sendToVCEmpData(vcEmpOptional2,sendToVCEmpInfoList);
					break;
				}else if(!InfBatchUtil.empty(vcEmpOptional3)){
					sendToVCEmpData(vcEmpOptional3,sendToVCEmpInfoList);
					break;
				}else if(!InfBatchUtil.empty(vcEmpOptional4)){
					sendToVCEmpData(vcEmpOptional4,sendToVCEmpInfoList);
					break;
				}else if(!InfBatchUtil.empty(vcEmpOptional5)){
					sendToVCEmpData(vcEmpOptional5,sendToVCEmpInfoList);
					break;
				}
			}
		}
		return sendToVCEmpInfoList;
	}
	private void sendToVCEmpData(ArrayList<VCEmpInfoDataM> empInfos,ArrayList<VCEmpInfoDataM> filterEmpInfos){
		if(!InfBatchUtil.empty(empInfos)){
			int minCorpTtlCd = this.getMinCorpTtlCd(empInfos);
			logger.debug("minCorpTtlCd : "+minCorpTtlCd);
			for(VCEmpInfoDataM vcEmpInfo : empInfos){
				logger.debug("CorpTtlCd : "+vcEmpInfo.getCorpTtlCd());
				if(minCorpTtlCd ==vcEmpInfo.getCorpTtlCd()){
					filterEmpInfos.add(vcEmpInfo);
				}
			}
		}
	}
	private int getMinCorpTtlCd(ArrayList<VCEmpInfoDataM> vcEmpList){
		int min = 0;
		if(!InfBatchUtil.empty(vcEmpList)){
			for(int i =0;i<vcEmpList.size();i++){
				  VCEmpInfoDataM vcmInfo = vcEmpList.get(i);
				  if(i==0){
					  min = vcmInfo.getCorpTtlCd();
				  }
				min = Math.min(vcmInfo.getCorpTtlCd(),min);
			}
		}
		return min;
	}
	
	public static ArrayList<String> getFixEmailJobCodeData(ArrayList<JobCodeDataM> jobCodeList){
		ArrayList<String> elementList = new ArrayList<String>();
		 if(!InfBatchUtil.empty(jobCodeList)){
			 for(JobCodeDataM  jobCode :jobCodeList ){
				 if(!InfBatchUtil.empty(jobCode.getFixEmail())){
					 elementList.add(jobCode.getFixEmail());
				 }
			 }
		 }
		return elementList;
	}
	
	public static void setEmailRecipients(ArrayList<JobCodeDataM> jobCodeList,RecipientTypeDataM recipientType){
		if(!InfBatchUtil.empty(jobCodeList)){
			for(JobCodeDataM  jobCode :jobCodeList ){
				if(!InfBatchUtil.empty(jobCode.getFixEmail())){
					EmailRecipientDataM  emailRecipient  = new EmailRecipientDataM();
					emailRecipient.setEmail(jobCode.getFixEmail());
					recipientType.put(emailRecipient);
				}
			}
		}
	}
	
	public static String getSpace(int spaceNum){
    	StringBuilder space = new StringBuilder("");
    	for(int i=0 ;i<spaceNum;i++){
    		space.append("&nbsp;");
    	}
    	return space.toString();
    }
}
