package com.kbank.eappu.dummymodel;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

import com.google.gson.annotations.Expose;

public abstract class Contract {

	public abstract String transform();
	
	@Expose
	private String dual;
	@Expose
	private String name;
	@Expose
	private int contractId;
	@Expose
	private String contractNameTH;
	public String getDual() {
		return dual;
	}
	public void setDual(String dual) {
		this.dual = dual;
	}

	@Expose
	private String contractNameEN;
	@Expose
	private BigDecimal amount;
	@Expose
	private List<Integer> subContract;
	@Expose
	private Date createDate;
	@Expose
	private String empthStr;
	@Expose
	private List<Payload> payloads;
	
	
	
	public List<Payload> getPayloads() {
		return payloads;
	}
	public String getEmpthStr() {
		return empthStr;
	}
	public void setEmpthStr(String empthStr) {
		this.empthStr = empthStr;
	}
	public void setPayloads(List<Payload> payloads) {
		this.payloads = payloads;
	}
	public String getName() {
		return name;
	}
	public Date getCreateDate() {
		return createDate;
	}
	public void setCreateDate(Date createDate) {
		this.createDate = createDate;
	}
	public void setName(String name) {
		this.name = name;
	}
	
	public int getContractId() {
		return contractId;
	}
	public void setContractId(int contractId) {
		this.contractId = contractId;
	}
	public String getContractNameTH() {
		return contractNameTH;
	}
	public void setContractNameTH(String contractNameTH) {
		this.contractNameTH = contractNameTH;
	}
	public String getContractNameEN() {
		return contractNameEN;
	}
	public void setContractNameEN(String contractNameEN) {
		this.contractNameEN = contractNameEN;
	}
	public BigDecimal getAmount() {
		return amount;
	}
	public List<Integer> getSubContract() {
		return subContract;
	}
	public void setSubContract(List<Integer> subContract) {
		this.subContract = subContract;
	}
	public void setAmount(BigDecimal amount) {
		this.amount = amount;
	}
	
}