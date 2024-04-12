package com.eaf.orig.ulo.model.app;

import java.io.Serializable;
import java.sql.Timestamp;

@SuppressWarnings("serial")
public class ReprocessLogDataM implements Serializable,Cloneable{
	private String reprocessLogId;		//ORIG_REPROCESS_LOG.REPROCESS_LOG_ID(VARCHAR2)
	private String applicationGroupId;	//ORIG_REPROCESS_LOG.APPLICATION_GROUP_ID(VARCHAR2)
	private int lifeCycle;				//ORIG_REPROCESS_LOG.LIFE_CYCLE(N)
	private String createBy;			//ORIG_REPROCESS_LOG.CREATE_BY(VARCHAR2)
	private Timestamp createDate;		//ORIG_REPROCESS_LOG.CREATE_DATE(DATE)
	private String updateBy;			//ORIG_REPROCESS_LOG.UPDATE_BY(VARCHAR2)
	private Timestamp updateDate;		//ORIG_REPROCESS_LOG.UPDATE_DATE(DATE)
	
	public ReprocessLogDataM(){
		super();
	}
	public String getReprocessLogId(){
		return reprocessLogId;
	}
	public void setReprocessLogId(String reprocessLogId){
		this.reprocessLogId = reprocessLogId;
	}
	public String getApplicationGroupId(){
		return applicationGroupId;
	}
	public void setApplicationGroupId(String applicationGroupId){
		this.applicationGroupId = applicationGroupId;
	}
	public int getLifeCycle(){
		return lifeCycle;
	}
	public void setLifeCycle(int lifeCycle){
		this.lifeCycle = lifeCycle;
	}
	public String getCreateBy(){
		return createBy;
	}
	public void setCreateBy(String createBy){
		this.createBy = createBy;
	}
	public Timestamp getCreateDate(){
		return createDate;
	}
	public void setCreateDate(Timestamp createDate){
		this.createDate = createDate;
	}
	public String getUpdateBy(){
		return updateBy;
	}
	public void setUpdateBy(String updateBy){
		this.updateBy = updateBy;
	}
	public Timestamp getUpdateDate(){
		return updateDate;
	}
	public void setUpdateDate(Timestamp updateDate){
		this.updateDate = updateDate;
	}
	@Override
	public String toString(){
		StringBuilder builder = new StringBuilder();
			builder.append("ReprocessLogDataM [reprocessLogId=");
			builder.append(reprocessLogId);
			builder.append(", applicationGroupId=");
			builder.append(applicationGroupId);
			builder.append(", lifeCycle=");
			builder.append(lifeCycle);
			builder.append(", createBy=");
			builder.append(createBy);
			builder.append(", createDate=");
			builder.append(createDate);
			builder.append(", updateBy=");
			builder.append(updateBy);
			builder.append(", updateDate=");
			builder.append(updateDate);
			builder.append("]");
		return builder.toString();
	}	
}