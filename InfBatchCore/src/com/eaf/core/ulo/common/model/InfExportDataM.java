package com.eaf.core.ulo.common.model;

import java.io.Serializable;
import java.sql.Timestamp;

@SuppressWarnings("serial")
public class InfExportDataM implements Serializable,Cloneable{
	private String exportId;
	private String moduleID;
	private Timestamp dataDate;
	private String fileName;
	private String content;
	private Timestamp updateDate;
	private String updateBy;
	public String getExportId() {
		return exportId;
	}
	public void setExportId(String exportId) {
		this.exportId = exportId;
	}
	public String getModuleID() {
		return moduleID;
	}
	public void setModuleID(String moduleID) {
		this.moduleID = moduleID;
	}
	public Timestamp getDataDate() {
		return dataDate;
	}
	public void setDataDate(Timestamp dataDate) {
		this.dataDate = dataDate;
	}
	public String getFileName() {
		return fileName;
	}
	public void setFileName(String fileName) {
		this.fileName = fileName;
	}
	public String getContent() {
		return content;
	}
	public void setContent(String content) {
		this.content = content;
	}
	public Timestamp getUpdateDate() {
		return updateDate;
	}
	public void setUpdateDate(Timestamp updateDate) {
		this.updateDate = updateDate;
	}
	public String getUpdateBy() {
		return updateBy;
	}
	public void setUpdateBy(String updateBy) {
		this.updateBy = updateBy;
	}
}
