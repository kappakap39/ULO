package com.eaf.inf.batch.ulo.report.model;

import java.io.Serializable;

public class ExportReportDataM implements Serializable,Cloneable{
	public ExportReportDataM(){
		super();
	}
	private String reportId;
	private String reportTemplate;
	private String reportOutputPath;
	private String reportOutputName;
	private Object object;
	private String reportPath;
	public String getReportId() {
		return reportId;
	}
	public void setReportId(String reportId) {
		this.reportId = reportId;
	}
	public String getReportTemplate() {
		return reportTemplate;
	}
	public void setReportTemplate(String reportTemplate) {
		this.reportTemplate = reportTemplate;
	}
	public String getReportOutputPath() {
		return reportOutputPath;
	}
	public void setReportOutputPath(String reportOutputPath) {
		this.reportOutputPath = reportOutputPath;
	}
	public String getReportOutputName() {
		return reportOutputName;
	}
	public void setReportOutputName(String reportOutputName) {
		this.reportOutputName = reportOutputName;
	}
	public Object getObject() {
		return object;
	}
	public void setObject(Object object) {
		this.object = object;
	}
	public String getReportPath() {
		return reportPath;
	}
	public void setReportPath(String reportPath) {
		this.reportPath = reportPath;
	}
	
}
