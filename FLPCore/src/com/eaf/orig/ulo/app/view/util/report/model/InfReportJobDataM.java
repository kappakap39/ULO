package com.eaf.orig.ulo.app.view.util.report.model;

import java.io.Serializable;

public class InfReportJobDataM implements Serializable, Cloneable{
	private String reportJobId;
	private String projectCode;
	private String channel;
	private String branchRegion;
	private String branchZone;
	private String dsaZone;
	private String nbdZone;
	private String dateType;
	private String dateFrom;
	private String dateTo;
	private String productCriteria;
	private String docFirstCompleteFlag;
	private String stationFrom;
	private String stationTo;
	private String applicationStatus;
	private String reportType;
	private String CREATE_BY;
	private String CREATE_DATE;
	
	public String getReportJobId() {
		return reportJobId;
	}
	public void setReportJobId(String reportJobId) {
		this.reportJobId = reportJobId;
	}
	public String getProjectCode() {
		return projectCode;
	}
	public void setProjectCode(String projectCode) {
		this.projectCode = projectCode;
	}
	public String getChannel() {
		return channel;
	}
	public void setChannel(String channel) {
		this.channel = channel;
	}
	public String getBranchRegion() {
		return branchRegion;
	}
	public void setBranchRegion(String branchRegion) {
		this.branchRegion = branchRegion;
	}
	public String getBranchZone() {
		return branchZone;
	}
	public void setBranchZone(String branchZone) {
		this.branchZone = branchZone;
	}
	public String getDsaZone() {
		return dsaZone;
	}
	public void setDsaZone(String dsaZone) {
		this.dsaZone = dsaZone;
	}
	public String getNbdZone() {
		return nbdZone;
	}
	public void setNbdZone(String nbdZone) {
		this.nbdZone = nbdZone;
	}
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
	public String getProductCriteria() {
		return productCriteria;
	}
	public void setProductCriteria(String productCriteria) {
		this.productCriteria = productCriteria;
	}
	public String getDocFirstCompleteFlag() {
		return docFirstCompleteFlag;
	}
	public void setDocFirstCompleteFlag(String docFirstCompleteFlag) {
		this.docFirstCompleteFlag = docFirstCompleteFlag;
	}
	public String getStationFrom() {
		return stationFrom;
	}
	public void setStationFrom(String stationFrom) {
		this.stationFrom = stationFrom;
	}
	public String getStationTo() {
		return stationTo;
	}
	public void setStationTo(String stationTo) {
		this.stationTo = stationTo;
	}
	public String getApplicationStatus() {
		return applicationStatus;
	}
	public void setApplicationStatus(String applicationStatus) {
		this.applicationStatus = applicationStatus;
	}
	public String getReportType() {
		return reportType;
	}
	public void setReportType(String reportType) {
		this.reportType = reportType;
	}
	public String getCREATE_BY() {
		return CREATE_BY;
	}
	public void setCREATE_BY(String cREATE_BY) {
		CREATE_BY = cREATE_BY;
	}
	public String getCREATE_DATE() {
		return CREATE_DATE;
	}
	public void setCREATE_DATE(String cREATE_DATE) {
		CREATE_DATE = cREATE_DATE;
	}
	
}
