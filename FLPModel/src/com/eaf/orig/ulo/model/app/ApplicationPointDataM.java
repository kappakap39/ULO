package com.eaf.orig.ulo.model.app;

import java.io.Serializable;
import java.math.BigDecimal;
import java.sql.Date;
import java.sql.Timestamp;

public class ApplicationPointDataM implements Serializable,Cloneable{
	public ApplicationPointDataM(){
		super();
	}
	private String applicationGroupId;	//ORIG_APPLICATION_POINT.APPLICATION_GROUP_ID(VARCHAR2)
	private Date pointDate;	//ORIG_APPLICATION_POINT.POINT_DATE(DATE)
	private String action;	//ORIG_APPLICATION_POINT.ACTION(VARCHAR2)
	private String applicationPointId;	//ORIG_APPLICATION_POINT.APPLICATION_POINT_ID(VARCHAR2)
	private BigDecimal point;	//ORIG_APPLICATION_POINT.POINT(NUMBER)
	private String createBy;	//ORIG_APPLICATION_POINT.CREATE_BY(VARCHAR2)
	private Timestamp createDate;	//ORIG_APPLICATION_POINT.CREATE_DATE(DATE)
	private String updateBy;	//ORIG_APPLICATION_POINT.UPDATE_BY(VARCHAR2)
	private Timestamp updateDate;	//ORIG_APPLICATION_POINT.UPDATE_DATE(DATE)
	
	public String getApplicationGroupId() {
		return applicationGroupId;
	}
	public void setApplicationGroupId(String applicationGroupId) {
		this.applicationGroupId = applicationGroupId;
	}
	public Date getPointDate() {
		return pointDate;
	}
	public void setPointDate(Date pointDate) {
		this.pointDate = pointDate;
	}
	public String getAction() {
		return action;
	}
	public void setAction(String action) {
		this.action = action;
	}
	public String getApplicationPointId() {
		return applicationPointId;
	}
	public void setApplicationPointId(String applicationPointId) {
		this.applicationPointId = applicationPointId;
	}
	public BigDecimal getPoint() {
		return point;
	}
	public void setPoint(BigDecimal point) {
		this.point = point;
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
}
