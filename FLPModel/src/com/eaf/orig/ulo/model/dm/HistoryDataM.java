package com.eaf.orig.ulo.model.dm;

import java.io.Serializable;
import java.sql.Date;
import java.sql.Timestamp;
import java.util.Comparator;

public class HistoryDataM implements Serializable,Cloneable,Comparator<Object>{
	public HistoryDataM(){
		super();
	}
	private String dmId;	//DM_TRANSACTION.DM_ID(VARCHAR2)
	private String dmTransactionId;	//DM_TRANSACTION.DM_TRANSACTION_ID(VARCHAR2)
	private String phoneNo;	//DM_TRANSACTION.PHONE_NO(VARCHAR2)
	private String remark;	//DM_TRANSACTION.REMARK(VARCHAR2)
	private String status;	//DM_TRANSACTION.STATUS(VARCHAR2)
	private Date dueDate;	//DM_TRANSACTION.DUE_DATE(DATE)
	private String action;	//DM_TRANSACTION.ACTION(VARCHAR2)
	private String mobileNo;	//DM_TRANSACTION.MOBILE_NO(VARCHAR2)
	private Date actionDate;	//DM_TRANSACTION.ACTION_DATE(DATE)
	private String requestedUser;	//DM_TRANSACTION.REQUESTED_USER(VARCHAR2)
	private int seq;	//DM_TRANSACTION.SEQ(NUMBER)
	private String createBy;	//DM_TRANSACTION.CREATE_BY(VARCHAR2)
	private Timestamp createDate;	//DM_TRANSACTION.CREATE_DATE(DATE)
	private String requesterDepartment;	//DM_TRANSACTION.REQUESTER_DEPARTMENT
	private String requesterName;	//DM_TRANSACTION.REQUESTER_NAME
	
	public String getDmId() {
		return dmId;
	}
	public void setDmId(String dmId) {
		this.dmId = dmId;
	}
	public String getDmTransactionId() {
		return dmTransactionId;
	}
	public void setDmTransactionId(String dmTransactionId) {
		this.dmTransactionId = dmTransactionId;
	}
	public String getPhoneNo() {
		return phoneNo;
	}
	public void setPhoneNo(String phoneNo) {
		this.phoneNo = phoneNo;
	}
	public String getRemark() {
		return remark;
	}
	public void setRemark(String remark) {
		this.remark = remark;
	}
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	public Date getDueDate() {
		return dueDate;
	}
	public void setDueDate(Date dueDate) {
		this.dueDate = dueDate;
	}
	public String getAction() {
		return action;
	}
	public void setAction(String action) {
		this.action = action;
	}
	public String getMobileNo() {
		return mobileNo;
	}
	public void setMobileNo(String mobileNo) {
		this.mobileNo = mobileNo;
	}
	public Date getActionDate() {
		return actionDate;
	}
	public void setActionDate(Date actionDate) {
		this.actionDate = actionDate;
	}
	public String getRequestedUser() {
		return requestedUser;
	}
	public void setRequestedUser(String requestedUser) {
		this.requestedUser = requestedUser;
	}
	public int getSeq() {
		return seq;
	}
	public void setSeq(int seq) {
		this.seq = seq;
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
	public String getRequesterDepartment() {
		return requesterDepartment;
	}
	public void setRequesterDepartment(String requesterDepartment) {
		this.requesterDepartment = requesterDepartment;
	}
	public String getRequesterName() {
		return requesterName;
	}
	public void setRequesterName(String requesterName) {
		this.requesterName = requesterName;
	}
	@Override
	public int compare(Object object1, Object object2) {
		HistoryDataM history1 = (HistoryDataM)object1;
		if(null==history1){
			history1 = new HistoryDataM();
		}
		HistoryDataM history2 = (HistoryDataM)object2;
		if(null==history2){
			history2 = new HistoryDataM();
		}
		int compareNum=0;
		 compareNum =history2.getCreateDate().compareTo(history1.getCreateDate());			   
        if (compareNum != 0) return compareNum;
        compareNum =history2.getDmTransactionId().compareTo(history1.getDmTransactionId());	
        if (compareNum != 0) return compareNum;
        return compareNum;
	}	
	
	
}
