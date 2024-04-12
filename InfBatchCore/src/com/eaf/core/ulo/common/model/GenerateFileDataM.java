package com.eaf.core.ulo.common.model;

import java.io.Serializable;
import java.util.Date;

public class GenerateFileDataM implements Serializable,Cloneable{
	public GenerateFileDataM(){
		super();
	}
	private int orderNo;
	private String uniqueId;
	private String generateId;
	private String reportPath;
	private String fileTemplate;
	private String fileOutputPath;
	private String fileOutputName;
	private String encode;
	private Date systemDate;
	private Object object;
	
	public int getOrderNo() {
		return orderNo;
	}
	public void setOrderNo(int orderNo) {
		this.orderNo = orderNo;
	}
	public String getUniqueId() {
		return uniqueId;
	}
	public void setUniqueId(String uniqueId) {
		this.uniqueId = uniqueId;
	}	
	public String getGenerateId() {
		return generateId;
	}
	public void setGenerateId(String generateId) {
		this.generateId = generateId;
	}
	public String getReportPath() {
		return reportPath;
	}
	public void setReportPath(String reportPath) {
		this.reportPath = reportPath;
	}
	public String getFileTemplate() {
		return fileTemplate;
	}
	public void setFileTemplate(String fileTemplate) {
		this.fileTemplate = fileTemplate;
	}
	public String getFileOutputPath() {
		return fileOutputPath;
	}
	public void setFileOutputPath(String fileOutputPath) {
		this.fileOutputPath = fileOutputPath;
	}
	public String getFileOutputName() {
		return fileOutputName;
	}
	public void setFileOutputName(String fileOutputName) {
		this.fileOutputName = fileOutputName;
	}
	public String getEncode() {
		return encode;
	}
	public void setEncode(String encode) {
		this.encode = encode;
	}
	public Date getSystemDate() {
		return systemDate;
	}
	public void setSystemDate(Date systemDate) {
		this.systemDate = systemDate;
	}	
	public Object getObject() {
		return object;
	}
	public void setObject(Object object) {
		this.object = object;
	}
}
