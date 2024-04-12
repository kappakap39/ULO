package com.eaf.inf.batch.ulo.notification.memo.model;

import java.io.Serializable;
import java.util.ArrayList;

@SuppressWarnings("serial")
public class InstructionMemoRequest implements Serializable,Cloneable{
	private String applicationGroupId;
	private int lifeCycle;
	private String customerName;
	private String idNo;
	private String productName;
	private ArrayList<String> instructionMemoFiles;	
	public String getApplicationGroupId() {
		return applicationGroupId;
	}
	public void setApplicationGroupId(String applicationGroupId) {
		this.applicationGroupId = applicationGroupId;
	}
	public ArrayList<String> getInstructionMemoFiles() {
		return instructionMemoFiles;
	}
	public void setInstructionMemoFiles(ArrayList<String> instructionMemoFiles) {
		this.instructionMemoFiles = instructionMemoFiles;
	}
	public int getLifeCycle() {
		return lifeCycle;
	}
	public void setLifeCycle(int lifeCycle) {
		this.lifeCycle = lifeCycle;
	}
	public String getCustomerName() {
		return customerName;
	}
	public void setCustomerName(String customerName) {
		this.customerName = customerName;
	}
	public String getIdNo() {
		return idNo;
	}
	public void setIdNo(String idNo) {
		this.idNo = idNo;
	}
	public String getProductName() {
		return productName;
	}
	public void setProductName(String productName) {
		this.productName = productName;
	}	
}
