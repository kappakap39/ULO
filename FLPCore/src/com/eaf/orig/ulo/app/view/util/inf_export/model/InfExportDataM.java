package com.eaf.orig.ulo.app.view.util.inf_export.model;

import java.io.Serializable;
import java.util.Date;
import java.sql.Clob;

@SuppressWarnings("serial")
public class InfExportDataM implements Serializable,Cloneable{
	private String moduleId;		// MODULE_ID VARCHAR2(50) NOT NULL,
	private Date dataDate;			//DATA_DATE DATE NOT NULL,
	private String fileName;		//FILE_NAME VARCHAR2(100),
    private Clob content;			//CONTENT CLOB,
    private Date updateDate;				//UPDATE_DATE DATE,
    private String updateBy;				//UPDATE_BY VARCHAR2(50)
    
	public String getModuleId() {
		return moduleId;
	}
	public void setModuleId(String moduleId) {
		this.moduleId = moduleId;
	}
	public Date getDataDate() {
		return dataDate;
	}
	public void setDataDate(Date dataDate) {
		this.dataDate = dataDate;
	}
	public String getFileName() {
		return fileName;
	}
	public void setFileName(String fileName) {
		this.fileName = fileName;
	}
	public Clob getContent() {
		return content;
	}
	public void setContent(Clob content) {
		this.content = content;
	}
	public Date getUpdateDate() {
		return updateDate;
	}
	public void setUpdateDate(Date updateDate) {
		this.updateDate = updateDate;
	}
	public String getUpdateBy() {
		return updateBy;
	}
	public void setUpdateBy(String updateBy) {
		this.updateBy = updateBy;
	}
}
