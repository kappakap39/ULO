package com.eaf.orig.ulo.model.app;

import java.io.Serializable;
import java.sql.Timestamp;

public class NotePadDataM implements Serializable,Cloneable{
	public NotePadDataM(){
		super();
	}
	private String applicationGroupId;	//ORIG_NOTE_PAD_DATA.APPLICATION_GROUP_ID(VARCHAR2)
	private String notePadId;	//ORIG_NOTE_PAD_DATA.NOTE_PAD_ID(VARCHAR2)
	private String notePadDesc;	//ORIG_NOTE_PAD_DATA.NOTE_PAD_DESC(VARCHAR2)
	private String createBy;	//ORIG_NOTE_PAD_DATA.CREATE_BY(VARCHAR2)
	private Timestamp createDate;	//ORIG_NOTE_PAD_DATA.CREATE_DATE(DATE)
	private String updateBy;	//ORIG_NOTE_PAD_DATA.UPDATE_BY(VARCHAR2)
	private Timestamp updateDate;	//ORIG_NOTE_PAD_DATA.UPDATE_DATE(DATE)
	private String status;	//ORIG_NOTE_PAD_DATA.STATUS(VARCHAR2)
	private String userRole; //ORIG_NOTE_PAD_DATA.USER_ROLE(VARCHAR2)

	private int seq; // Display
	
	public String getUserRole() {
		return userRole;
	}
	public void setUserRole(String userRole) {
		this.userRole = userRole;
	}
	public String getApplicationGroupId() {
		return applicationGroupId;
	}
	public void setApplicationGroupId(String applicationGroupId) {
		this.applicationGroupId = applicationGroupId;
	}
	public String getNotePadId() {
		return notePadId;
	}
	public void setNotePadId(String notePadId) {
		this.notePadId = notePadId;
	}
	public String getNotePadDesc() {
		return notePadDesc;
	}
	public void setNotePadDesc(String notePadDesc) {
		this.notePadDesc = notePadDesc;
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
	public int getSeq() {
		return seq;
	}
	public void setSeq(int seq) {
		this.seq = seq;
	}
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	
	
}
