package com.eaf.inf.batch.ulo.notification.model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import com.eaf.core.ulo.common.cache.InfBatchProperty;
import com.eaf.core.ulo.common.util.Util;

@SuppressWarnings("serial")
public class NotifyApplicationSegment implements Serializable,Cloneable{
	public NotifyApplicationSegment(){
		
	}
	List<NotifyApplication> notifyApplications = new ArrayList<NotifyApplication>();
	boolean caflow = false;
	boolean eapp = false;
	public List<NotifyApplication> getNotifyApplications() {
		return notifyApplications;
	}
	public void setNotifyApplications(List<NotifyApplication> notifyApplications) {
		this.notifyApplications = notifyApplications;
	}
	public boolean isCaflow() {
		return caflow;
	}
	public void setCaflow(boolean caflow){
		this.caflow = caflow;
	}
	public boolean isEapp() {
		return eapp;
	}
	public void setEapp(boolean eapp) {
		this.eapp = eapp;
	}
	public Reason findReasonApplication(String applicationId){
		Reason reason = new Reason();
		if(null!=notifyApplications){
			for (NotifyApplication notifyApplication : notifyApplications) {
				if(null!=notifyApplication.getApplicationRecordId()&&notifyApplication.getApplicationRecordId().equals(applicationId)){
					String lookupId = (caflow || eapp)?"POST_CA_VERIFIED_RESULT":"PRE_CA_VERIFIED_RESULT";
					List<ApplicationResult> applicationResults = notifyApplication.getApplicationResults();
					if(null!=applicationResults){
						for(ApplicationResult applicationResult : applicationResults){
							if(InfBatchProperty.lookup(lookupId, applicationResult.getRuleResult())){
								if(applicationResult.getReasonRank()<reason.getReasonRank()&&applicationResult.getReasonRank()!=0){
									reason.setReasonCode(applicationResult.getReasonCode());
									reason.setReasonRank(applicationResult.getReasonRank());
								}
							}
						}
					}
				}
			}
		}
		return reason;
	}
	public List<String> findReasonApplications(String applicationId){
		List<String> reasons = new ArrayList<>();
		Reason reason = new Reason();
		if(null!=notifyApplications){
			for (NotifyApplication notifyApplication : notifyApplications) {
				if(null!=notifyApplication.getApplicationRecordId()&&notifyApplication.getApplicationRecordId().equals(applicationId)){
					String lookupId = (caflow || eapp)?"POST_CA_VERIFIED_RESULT":"PRE_CA_VERIFIED_RESULT";
					List<ApplicationResult> applicationResults = notifyApplication.getApplicationResults();
					if(null!=applicationResults){
						for(ApplicationResult applicationResult : applicationResults){
							if(InfBatchProperty.lookup(lookupId, applicationResult.getRuleResult())){
								if(applicationResult.getReasonRank()<=reason.getReasonRank()&&applicationResult.getReasonRank()!=0){
									reason.setReasonCode(applicationResult.getReasonCode());
									reason.setReasonRank(applicationResult.getReasonRank());
									if(!Util.empty(applicationResult.getReasonCode())&&!reasons.contains(applicationResult.getReasonCode()))
										reasons.add(applicationResult.getReasonCode());
								}
							}
						}
					}
				}
			}
		}
		return reasons;
	}
	public Reason findReasonPerson(String personalId){
		Reason reason = new Reason();
		if(null!=notifyApplications){
			for (NotifyApplication notifyApplication : notifyApplications) {
				if(null!=notifyApplication.getPersonalId()&&notifyApplication.getPersonalId().equals(personalId)){
					String lookupId = (caflow || eapp)?"POST_CA_VERIFIED_RESULT":"PRE_CA_VERIFIED_RESULT";
					List<ApplicationResult> applicationResults = notifyApplication.getApplicationResults();
					if(null!=applicationResults){
						for(ApplicationResult applicationResult : applicationResults){
							if(InfBatchProperty.lookup(lookupId, applicationResult.getRuleResult())){
								if(applicationResult.getReasonRank()<reason.getReasonRank()&&applicationResult.getReasonRank()!=0){
									reason.setReasonCode(applicationResult.getReasonCode());
									reason.setReasonRank(applicationResult.getReasonRank());
								}
							}
						}
					}
				}
			}
		}
		return reason;
	}
	public Reason findReasonProduct(String productName){
		Reason reason = new Reason();
		if(null!=notifyApplications){
			for (NotifyApplication notifyApplication : notifyApplications) {
				if(null!=notifyApplication.getProductName()&&notifyApplication.getProductName().equals(productName)){
					String lookupId = (caflow || eapp)?"POST_CA_VERIFIED_RESULT":"PRE_CA_VERIFIED_RESULT";
					List<ApplicationResult> applicationResults = notifyApplication.getApplicationResults();
					if(null!=applicationResults){
						for(ApplicationResult applicationResult : applicationResults){
							if(InfBatchProperty.lookup(lookupId, applicationResult.getRuleResult())){
								if(applicationResult.getReasonRank()<reason.getReasonRank()&&applicationResult.getReasonRank()!=0){
									reason.setReasonCode(applicationResult.getReasonCode());
									reason.setReasonRank(applicationResult.getReasonRank());
								}
							}
						}
					}
				}
			}
		}
		return reason;
	}
	public Reason findReasonApplicationGroup(String applicationGroupId){
		Reason reason = new Reason();
		if(null!=notifyApplications){
			for (NotifyApplication notifyApplication : notifyApplications) {
				if(null!=notifyApplication.getProductName()&&notifyApplication.getApplpicationGroupId().equals(applicationGroupId)){
					String lookupId = (caflow || eapp)?"POST_CA_VERIFIED_RESULT":"PRE_CA_VERIFIED_RESULT";
					List<ApplicationResult> applicationResults = notifyApplication.getApplicationResults();
					if(null!=applicationResults){
						for(ApplicationResult applicationResult : applicationResults){
							if(InfBatchProperty.lookup(lookupId, applicationResult.getRuleResult())){
								if(applicationResult.getReasonRank()<reason.getReasonRank()&&applicationResult.getReasonRank()!=0){
									reason.setReasonCode(applicationResult.getReasonCode());
									reason.setReasonRank(applicationResult.getReasonRank());
								}
							}
						}
					}
				}
			}
		}
		return reason;
	}
	public int lifeCycle(){
		int lifeCycle = 1;
		if(null != notifyApplications){
			for(NotifyApplication notifyApplication:notifyApplications){
				lifeCycle = Math.max(notifyApplication.getLifeCycle(),lifeCycle);
			}
		}
		return lifeCycle;
	}
	
	public Reason findReasonPersonAndProduct(String personalId, String product){
		Reason reason = new Reason();
		if(null!=notifyApplications){
			for (NotifyApplication notifyApplication : notifyApplications) {
				if(null!=notifyApplication.getPersonalId()&&notifyApplication.getPersonalId().equals(personalId)&&notifyApplication.getProductName().equals(product)){
					String lookupId = (caflow || eapp)?"POST_CA_VERIFIED_RESULT":"PRE_CA_VERIFIED_RESULT";
					List<ApplicationResult> applicationResults = notifyApplication.getApplicationResults();
					if(null!=applicationResults){
						for(ApplicationResult applicationResult : applicationResults){
							if(InfBatchProperty.lookup(lookupId, applicationResult.getRuleResult())){
								if(applicationResult.getReasonRank()<reason.getReasonRank()&&applicationResult.getReasonRank()!=0){
									reason.setReasonCode(applicationResult.getReasonCode());
									reason.setReasonRank(applicationResult.getReasonRank());
								}
							}
						}
					}
				}
			}
		}
		return reason;
	}
}
