package com.eaf.orig.ulo.model.app;

import java.io.Serializable;
import java.sql.Timestamp;

@SuppressWarnings("serial")
public class InfBatchLogDataM implements Serializable,Cloneable{
	public InfBatchLogDataM(){
		super();
	}
	public void init(String referId,String uniqueId){
		this.applicationRecordId = referId;
		this.interfaceLogId=uniqueId;
	}
	private String applicationGroupId;
	private String applicationRecordId;	//INF_BATCH_LOG.APPLICATION_RECORD_ID(VARCHAR2)
	private String interfaceLogId;	//INF_BATCH_LOG.INTERFACE_LOG_ID(VARCHAR2)
	private Timestamp interfaceDate;	//INF_BATCH_LOG.INTERFACE_DATE(DATE)
	private String interfaceCode;	//INF_BATCH_LOG.INTERFACE_CODE(VARCHAR2)
	private String createBy;	//INF_BATCH_LOG.CREATE_BY(VARCHAR2)
	private String interfaceStatus;	//INTERFACE_STATUS
	private String refId;			//REF_ID
	private String refSeq;			//REF_SEQ
	private Timestamp createDate;	//INF_BATCH_LOG.CREATE_DATE(DATE)
	private String system01;
	private String system02;
	private String system03;
	private String system04;
	private String system05;
	private String system06;
	private String system07;
	private String system08;
	private String system09;
	private String system10;
	private int lifeCycle;
	private String logMessage;
	
	public String getApplicationGroupId() {
		return applicationGroupId;
	}
	public void setApplicationGroupId(String applicationGroupId) {
		this.applicationGroupId = applicationGroupId;
	}
	public String getApplicationRecordId() {
		return applicationRecordId;
	}
	public void setApplicationRecordId(String applicationRecordId) {
		this.applicationRecordId = applicationRecordId;
	}
	public String getInterfaceLogId() {
		return interfaceLogId;
	}
	public void setInterfaceLogId(String interfaceLogId) {
		this.interfaceLogId = interfaceLogId;
	}	
	public Timestamp getInterfaceDate() {
		return interfaceDate;
	}
	public void setInterfaceDate(Timestamp interfaceDate) {
		this.interfaceDate = interfaceDate;
	}
	public String getInterfaceCode() {
		return interfaceCode;
	}
	public void setInterfaceCode(String interfaceCode) {
		this.interfaceCode = interfaceCode;
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
	public String getInterfaceStatus() {
		return interfaceStatus;
	}
	public void setInterfaceStatus(String interfaceStatus) {
		this.interfaceStatus = interfaceStatus;
	}
	public String getRefId() {
		return refId;
	}
	public void setRefId(String refId) {
		this.refId = refId;
	}
	public String getRefSeq() {
		return refSeq;
	}
	public void setRefSeq(String refSeq) {
		this.refSeq = refSeq;
	}
	public String getSystem01() {
		return system01;
	}
	public void setSystem01(String system01) {
		this.system01 = system01;
	}
	public String getSystem02() {
		return system02;
	}
	public void setSystem02(String system02) {
		this.system02 = system02;
	}
	public int getLifeCycle() {
		return lifeCycle;
	}
	public void setLifeCycle(int lifeCycle) {
		this.lifeCycle = lifeCycle;
	}
	public String getSystem03() {
		return system03;
	}
	public void setSystem03(String system03) {
		this.system03 = system03;
	}
	public String getSystem04() {
		return system04;
	}
	public void setSystem04(String system04) {
		this.system04 = system04;
	}
	public String getSystem05() {
		return system05;
	}
	public void setSystem05(String system05) {
		this.system05 = system05;
	}
	public String getSystem06() {
		return system06;
	}
	public void setSystem06(String system06) {
		this.system06 = system06;
	}
	public String getSystem07() {
		return system07;
	}
	public void setSystem07(String system07) {
		this.system07 = system07;
	}
	public String getSystem08() {
		return system08;
	}
	public void setSystem08(String system08) {
		this.system08 = system08;
	}
	public String getSystem09() {
		return system09;
	}
	public void setSystem09(String system09) {
		this.system09 = system09;
	}
	public String getSystem10() {
		return system10;
	}
	public void setSystem10(String system10) {
		this.system10 = system10;
	}
	public String getLogMessage() {
		return logMessage;
	}
	public void setLogMessage(String logMessage) {
		this.logMessage = logMessage;
	}	
}
