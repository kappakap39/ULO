package flp.model.log;

public class TransactionLogInput {
	
	String transactionLogId;
	String refCode;
	String idNo;
	String transactionId;
	String serviceId;
	String decisionService;
	
	public String getTransactionLogId() {
		return transactionLogId;
	}
	public void setTransactionLogId(String transactionLogId) {
		this.transactionLogId = transactionLogId;
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
	public String getTransactionId() {
		return transactionId;
	}
	public void setTransactionId(String transactionId) {
		this.transactionId = transactionId;
	}
	public String getDecisionService() {
		return decisionService;
	}
	public void setDecisionService(String decisionService) {
		this.decisionService = decisionService;
	}
	public String getIdNo() {
		return idNo;
	}
	public void setIdNo(String idNo) {
		this.idNo = idNo;
	}
	
	
	
}
