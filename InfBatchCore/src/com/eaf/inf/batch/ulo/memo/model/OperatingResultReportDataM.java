package com.eaf.inf.batch.ulo.memo.model;

import java.io.Serializable;
import java.util.List;

public class OperatingResultReportDataM implements Serializable,Cloneable{
	private String dateType;
	private String dateFrom;
	private String dateTo;
	private String projectCode;
	private String productCriteria;
	private String channel;
	private String createBy;
	private String createDate;
	
	public String getDateType() {
		return dateType;
	}
	public void setDateType(String dateType) {
		this.dateType = dateType;
	}
	public String getDateFrom() {
		return dateFrom;
	}
	public void setDateFrom(String dateFrom) {
		this.dateFrom = dateFrom;
	}
	public String getDateTo() {
		return dateTo;
	}
	public void setDateTo(String dateTo) {
		this.dateTo = dateTo;
	}
	public String getProjectCode() {
		return projectCode;
	}
	public void setProjectCode(String projectCode) {
		this.projectCode = projectCode;
	}
	public String getProductCriteria() {
		return productCriteria;
	}
	public void setProductCriteria(String productCriteria) {
		this.productCriteria = productCriteria;
	}
	public String getChannel() {
		return channel;
	}
	public void setChannel(String channel) {
		this.channel = channel;
	}
	public String getCreateBy() {
		return createBy;
	}
	public void setCreateBy(String createBy) {
		this.createBy = createBy;
	}
	public String getCreateDate() {
		return createDate;
	}
	public void setCreateDate(String createDate) {
		this.createDate = createDate;
	}
}
