package com.eaf.orig.ulo.model.app;

import java.io.Serializable;
import java.sql.Timestamp;
import java.util.ArrayList;

@SuppressWarnings("serial")
public class ApplicationImageDataM implements Serializable,Cloneable{
	public ApplicationImageDataM(){
		super();
		applicationImageSplits = new ArrayList<ApplicationImageSplitDataM>();
	}
	private String applicationGroupId;	//ORIG_APPLICATION_IMG.APPLICATION_GROUP_ID(VARCHAR2)
	private String appImgId;	//ORIG_APPLICATION_IMG.APP_IMG_ID(VARCHAR2)
	private String fileName;	//ORIG_APPLICATION_IMG.FILE_NAME(VARCHAR2)
	private String activeStatus;	//ORIG_APPLICATION_IMG.ACTIVE_STATUS(VARCHAR2)
	private String userId;	//ORIG_APPLICATION_IMG.USER_ID(VARCHAR2)
	private String realPath;	//ORIG_APPLICATION_IMG.REAL_PATH(VARCHAR2)
	private String path;	//ORIG_APPLICATION_IMG.PATH(VARCHAR2)
	private String fileType;	//ORIG_APPLICATION_IMG.FILE_TYPE(VARCHAR2)
	private String refNo;	//ORIG_APPLICATION_IMG.REF_NO(VARCHAR2)
	private String activityType;	//ORIG_APPLICATION_IMG.ACTIVITY_TYPE(VARCHAR2)
	private String createBy;	//ORIG_APPLICATION_IMG.CREATE_BY(VARCHAR2)
	private Timestamp createDate;	//ORIG_APPLICATION_IMG.CREATE_DATE(DATE)
	private String updateBy;	//ORIG_APPLICATION_IMG.UPDATE_BY(VARCHAR2)
	private Timestamp updateDate;	//ORIG_APPLICATION_IMG.UPDATE_DATE(DATE)
	
	private ArrayList<ApplicationImageSplitDataM> applicationImageSplits = new ArrayList<ApplicationImageSplitDataM>();
	
	public String getApplicationGroupId() {
		return applicationGroupId;
	}
	public void setApplicationGroupId(String applicationGroupId) {
		this.applicationGroupId = applicationGroupId;
	}
	public String getAppImgId() {
		return appImgId;
	}
	public void setAppImgId(String appImgId) {
		this.appImgId = appImgId;
	}
	public String getFileName() {
		return fileName;
	}
	public void setFileName(String fileName) {
		this.fileName = fileName;
	}
	public String getActiveStatus() {
		return activeStatus;
	}
	public void setActiveStatus(String activeStatus) {
		this.activeStatus = activeStatus;
	}
	public String getUserId() {
		return userId;
	}
	public void setUserId(String userId) {
		this.userId = userId;
	}
	public String getRealPath() {
		return realPath;
	}
	public void setRealPath(String realPath) {
		this.realPath = realPath;
	}
	public String getPath() {
		return path;
	}
	public void setPath(String path) {
		this.path = path;
	}
	public String getFileType() {
		return fileType;
	}
	public void setFileType(String fileType) {
		this.fileType = fileType;
	}
	public String getRefNo() {
		return refNo;
	}
	public void setRefNo(String refNo) {
		this.refNo = refNo;
	}
	public String getActivityType() {
		return activityType;
	}
	public void setActivityType(String activityType) {
		this.activityType = activityType;
	}
	public String getCreateBy() {
		return createBy;
	}
	public void setCreateBy(String createBy) {
		this.createBy = createBy;
	}
	public Timestamp getCreateDate() {
		return createDate;
	}
	public void setCreateDate(Timestamp createDate) {
		this.createDate = createDate;
	}
	public String getUpdateBy() {
		return updateBy;
	}
	public void setUpdateBy(String updateBy) {
		this.updateBy = updateBy;
	}
	public Timestamp getUpdateDate() {
		return updateDate;
	}
	public void setUpdateDate(Timestamp updateDate) {
		this.updateDate = updateDate;
	}
	public ArrayList<ApplicationImageSplitDataM> getApplicationImageSplits() {
		return applicationImageSplits;
	}
	public void setApplicationImageSplits(ArrayList<ApplicationImageSplitDataM> applicationImageSplits) {
		this.applicationImageSplits = applicationImageSplits;
	}	
	public void add(ApplicationImageSplitDataM applicationImageSplit){
		if(null == this.applicationImageSplits){
			this.applicationImageSplits = new ArrayList<ApplicationImageSplitDataM>();
		}
		applicationImageSplits.add(applicationImageSplit);
	}
}
