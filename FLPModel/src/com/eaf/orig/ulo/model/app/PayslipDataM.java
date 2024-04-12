package com.eaf.orig.ulo.model.app;

import java.math.BigDecimal;
import java.sql.Date;
import java.sql.Timestamp;
import java.util.ArrayList;

public class PayslipDataM extends IncomeCategoryDataM {
	public PayslipDataM(){
		super();
	}
//	private int seq;
//	private String incomeId;	//INC_PAYSLIP.INCOME_ID(VARCHAR2)
	private String payslipId;	//INC_PAYSLIP.PAYSLIP_ID(VARCHAR2)
	private String bonusMonth;	//INC_PAYSLIP.BONUS_MONTH(VARCHAR2)
	private String bonusYear;	//INC_PAYSLIP.BONUS_YEAR(VARCHAR2)
	private BigDecimal bonus;	//INC_PAYSLIP.BONUS(NUMBER)
	private BigDecimal sumSso;	//INC_PAYSLIP.SUM_SSO(NUMBER)
	private int noMonth;	//INC_PAYSLIP.NO_MONTH(NUMBER)
	private BigDecimal sumOthIncome;	//INC_PAYSLIP.ACCUM_OTH_INCOME(NUMBER)
	private BigDecimal spacialPension;	//INC_PAYSLIP.SPACIAL_PENSION(NUMBER)
	private BigDecimal sumIncome;	//INC_PAYSLIP.SUM_INCOME(NUMBER)
	private String createBy;	//INC_PAYSLIP.CREATE_BY(VARCHAR2)
	private Timestamp createDate;	//INC_PAYSLIP.CREATE_DATE(DATE)
	private String updateBy;	//INC_PAYSLIP.UPDATE_BY(VARCHAR2)
	private Timestamp updateDate;	//INC_PAYSLIP.UPDATE_DATE(DATE)
	private Date salaryDate; //INC_PAYSLIP.SALARY_DATE(DATE)
	
	private ArrayList<PayslipMonthlyDataM> payslipMonthlys;
	
	public String getPayslipId() {
		return payslipId;
	}
	public void setPayslipId(String payslipId) {
		this.payslipId = payslipId;
	}	
	public String getBonusMonth() {
		return bonusMonth;
	}
	public void setBonusMonth(String bonusMonth) {
		this.bonusMonth = bonusMonth;
	}
	public String getBonusYear() {
		return bonusYear;
	}
	public void setBonusYear(String bonusYear) {
		this.bonusYear = bonusYear;
	}
	public BigDecimal getBonus() {
		return bonus;
	}
	public void setBonus(BigDecimal bonus) {
		this.bonus = bonus;
	}
	public BigDecimal getSumSso() {
		return sumSso;
	}
	public void setSumSso(BigDecimal sumSso) {
		this.sumSso = sumSso;
	}
	public int getNoMonth() {
		return noMonth;
	}
	public void setNoMonth(int noMonth) {
		this.noMonth = noMonth;
	}
	public BigDecimal getSumOthIncome() {
		return sumOthIncome;
	}
	public void setSumOthIncome(BigDecimal sumOthIncome) {
		this.sumOthIncome = sumOthIncome;
	}
	public BigDecimal getSpacialPension() {
		return spacialPension;
	}
	public void setSpacialPension(BigDecimal spacialPension) {
		this.spacialPension = spacialPension;
	}
	public BigDecimal getSumIncome() {
		return sumIncome;
	}
	public void setSumIncome(BigDecimal sumIncome) {
		this.sumIncome = sumIncome;
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
	public Date getSalaryDate() {
		return salaryDate;
	}
	public void setSalaryDate(Date salaryDate) {
		this.salaryDate = salaryDate;
	}
	public ArrayList<PayslipMonthlyDataM> getPayslipMonthlys() {
		return payslipMonthlys;
	}
	public void setPayslipMonthlys(ArrayList<PayslipMonthlyDataM> payslipMonthlys) {
		this.payslipMonthlys = payslipMonthlys;
	}
	public PayslipMonthlyDataM getPayslipMonthlyById(String payslipMonthlyId) {
		if(payslipMonthlyId != null && payslipMonthlys != null) {
			for(PayslipMonthlyDataM payslipMonthlyM: payslipMonthlys){
				if(payslipMonthlyId.equals(payslipMonthlyM.getPayslipMonthlyId())) {
					return payslipMonthlyM;
				}
			}
		}
		return null;
	}
	public ArrayList<PayslipMonthlyDataM> getPayslipMonthlyByType(String incomeType) {
		ArrayList<PayslipMonthlyDataM> monthlyList = new ArrayList<PayslipMonthlyDataM>();
		if(incomeType != null && payslipMonthlys != null) {
			for(PayslipMonthlyDataM payslipMonthlyM: payslipMonthlys){
				if(incomeType.equals(payslipMonthlyM.getIncomeType())) {
					monthlyList.add(payslipMonthlyM);
				}
			}
		}
		return monthlyList;
	}
	@Override
	public String getId() {
		return getPayslipId();
	}
}
