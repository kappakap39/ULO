package com.eaf.inf.batch.ulo.notification.model;

import java.io.Serializable;
import java.util.ArrayList;
@SuppressWarnings("serial")
public class NotifyEODSalesInfoDataM implements Cloneable, Serializable{
	private String applicationGroupId;
	private String saleId;
	private String saleChannel;
	private String recommend;
	private String recommendChannel;
	private String managerChannel;
	private ArrayList<JobCodeDataM>  jobCodes;
	public String getApplicationGroupId(){
		return applicationGroupId;
	}
	public void setApplicationGroupId(String applicationGroupId){
		this.applicationGroupId = applicationGroupId;
	}
	public String getSaleId(){
		return saleId;
	}
	public void setSaleId(String saleId){
		this.saleId = saleId;
	}
	public String getSaleChannel(){
		return saleChannel;
	}
	public void setSaleChannel(String saleChannel){
		this.saleChannel = saleChannel;
	}
	public String getRecommend(){
		return recommend;
	}
	public void setRecommend(String recommend){
		this.recommend = recommend;
	}
	public String getRecommendChannel(){
		return recommendChannel;
	}
	public void setRecommendChannel(String recommendChannel){
		this.recommendChannel = recommendChannel;
	}
	public String getManagerChannel(){
		return managerChannel;
	}
	public void setManagerChannel(String managerChannel){
		this.managerChannel = managerChannel;
	}
	public ArrayList<JobCodeDataM> getJobCodes(){
		return jobCodes;
	}
	public void setJobCodes(ArrayList<JobCodeDataM> jobCodes){
		this.jobCodes = jobCodes;
	}
	public void addJobCodes(JobCodeDataM jobCode){
		if(null==jobCodes){
			jobCodes = new ArrayList<JobCodeDataM>();
		}
		jobCodes.add(jobCode);
	}
}
