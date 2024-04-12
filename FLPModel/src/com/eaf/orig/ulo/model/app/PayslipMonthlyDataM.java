package com.eaf.orig.ulo.model.app;

import java.io.Serializable;
import java.sql.Timestamp;
import java.util.ArrayList;

public class PayslipMonthlyDataM implements Serializable,Cloneable{
	public PayslipMonthlyDataM(){
		super();
	}
	private int seq;
	private String payslipId;	//INC_PAYSLIP_MONTHLY.PAYSLIP_ID(VARCHAR2)
	private String payslipMonthlyId;	//INC_PAYSLIP_MONTHLY.PAYSLIP_MONTHLY_ID(VARCHAR2)
	private String incomeDesc;	//INC_PAYSLIP_MONTHLY.INCOME_DESC(VARCHAR2)
	private String incomeType;	//INC_PAYSLIP_MONTHLY.INCOME_TYPE(VARCHAR2)
	private String incomeOthDesc;	//INC_PAYSLIP_MONTHLY.INCOME_OTH_DESC(VARCHAR2)
	private String createBy;	//INC_PAYSLIP_MONTHLY.CREATE_BY(VARCHAR2)
	private Timestamp createDate;	//INC_PAYSLIP_MONTHLY.CREATE_DATE(DATE)
	private String updateBy;	//INC_PAYSLIP_MONTHLY.UPDATE_BY(VARCHAR2)
	private Timestamp updateDate;	//INC_PAYSLIP_MONTHLY.UPDATE_DATE(DATE)
	
	private ArrayList<PayslipMonthlyDetailDataM> payslipMonthlyDetails;
	
	public String getPayslipId() {
		return payslipId;
	}
	public void setPayslipId(String payslipId) {
		this.payslipId = payslipId;
	}
	public int getSeq() {
		return seq;
	}
	public void setSeq(int seq) {
		this.seq = seq;
	}
	public String getPayslipMonthlyId() {
		return payslipMonthlyId;
	}
	public void setPayslipMonthlyId(String payslipMonthlyId) {
		this.payslipMonthlyId = payslipMonthlyId;
	}
	public String getIncomeDesc() {
		return incomeDesc;
	}
	public void setIncomeDesc(String incomeDesc) {
		this.incomeDesc = incomeDesc;
	}
	public String getIncomeType() {
		return incomeType;
	}
	public void setIncomeType(String incomeType) {
		this.incomeType = incomeType;
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
	public String getIncomeOthDesc() {
		return incomeOthDesc;
	}
	public void setIncomeOthDesc(String incomeOthDesc) {
		this.incomeOthDesc = incomeOthDesc;
	}
	public ArrayList<PayslipMonthlyDetailDataM> getPayslipMonthlyDetails() {
		return payslipMonthlyDetails;
	}
	public void setPayslipMonthlyDetails(ArrayList<PayslipMonthlyDetailDataM> payslipMonthlyDetails) {
		this.payslipMonthlyDetails = payslipMonthlyDetails;
	}
	public PayslipMonthlyDetailDataM getPayslipMonthlyDetailById(
			String payslipMonthlyDetailId) {
		if(payslipMonthlyDetailId != null && payslipMonthlyDetails != null) {
			for(PayslipMonthlyDetailDataM monthlyDetailM: payslipMonthlyDetails){
				if(payslipMonthlyDetailId.equals(monthlyDetailM.getPayslipMonthlyDetailId())) {
					return monthlyDetailM;
				}
			}
		}
		return null;
	}
	
}
