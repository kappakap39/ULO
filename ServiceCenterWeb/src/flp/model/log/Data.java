package flp.model.log;

import java.sql.Clob;
import java.sql.Timestamp;

public class Data {

	String transactionLogId;
	String transactionId;
	String terminalType;
	String terminalId;
	String serviceId;
	String refCode;
	String param4;
	String param3;
	String param2;
	String param1;
	Timestamp createDate;
	String activityType;
	String binaryData;
	
	public String getTransactionLogId() {
		return transactionLogId;
	}
	public void setTransactionLogId(String transactionLogId) {
		this.transactionLogId = transactionLogId;
	}
	public String getTransactionId() {
		return transactionId;
	}
	public void setTransactionId(String transactionId) {
		this.transactionId = transactionId;
	}
	public String getTerminalType() {
		return terminalType;
	}
	public void setTerminalType(String terminalType) {
		this.terminalType = terminalType;
	}
	public String getTerminalId() {
		return terminalId;
	}
	public void setTerminalId(String terminalId) {
		this.terminalId = terminalId;
	}
	public String getServiceId() {
		return serviceId;
	}
	public void setServiceId(String serviceId) {
		this.serviceId = serviceId;
	}
	public String getRefCode() {
		return refCode;
	}
	public void setRefCode(String refCode) {
		this.refCode = refCode;
	}
	public String getParam4() {
		return param4;
	}
	public void setParam4(String param4) {
		this.param4 = param4;
	}
	public String getParam3() {
		return param3;
	}
	public void setParam3(String param3) {
		this.param3 = param3;
	}
	public String getParam2() {
		return param2;
	}
	public void setParam2(String param2) {
		this.param2 = param2;
	}
	public String getParam1() {
		return param1;
	}
	public void setParam1(String param1) {
		this.param1 = param1;
	}
	public Timestamp getCreateDate() {
		return createDate;
	}
	public void setCreateDate(Timestamp createDate) {
		this.createDate = createDate;
	}
	public String getActivityType() {
		return activityType;
	}
	public void setActivityType(String activityType) {
		this.activityType = activityType;
	}
	public String getBinaryData() {
		return binaryData;
	}
	public void setBinaryData(String binaryData) {
		this.binaryData = binaryData;
	}
	
	
	
}
