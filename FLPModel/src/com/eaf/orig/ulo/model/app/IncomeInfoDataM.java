package com.eaf.orig.ulo.model.app;

import java.io.Serializable;
import java.sql.Timestamp;
import java.util.ArrayList;

public class IncomeInfoDataM implements Cloneable,Serializable{
	public IncomeInfoDataM(){
		super();
	}
	private int seq;
	private String incomeId;	//INC_INFO.INCOME_ID(VARCHAR2)
	private String personalId;	//INC_INFO.PERSONAL_ID(VARCHAR2)
	private String incomeType;	//INC_INFO.INCOME_TYPE(VARCHAR2)
	private String createBy;	//INC_INFO.CREATE_BY(VARCHAR2)
	private Timestamp createDate;	//INC_INFO.CREATE_DATE(DATE)
	private String updateBy;	//INC_INFO.UPDATE_BY(VARCHAR2)
	private Timestamp updateDate;	//INC_INFO.UPDATE_DATE(DATE)
	private String compareFlag; //COMPARE_FLAG(VARCHAR2)
	private String remark; //INC_INFO.REMARK(VARCHAR2)
	private ArrayList<IncomeCategoryDataM> allIncomes;
	
	
//	private ArrayList<ClosedEndFundDataM> closeEndFounds;
//	private ArrayList<FinancialInstrumentDataM> financialInstruments;
//	private ArrayList<FixedAccountDataM> fixedAccounts;
//	private ArrayList<FixedGuaranteeDataM> fixedGuarantees;
//	private ArrayList<KVIIncomeDataM> kviIncomes;
//	private ArrayList<MonthlyTawi50DataM> monthlyTawi50s;
//	private ArrayList<OpenedEndFundDataM> opendEndFounds;
//	private ArrayList<PayrollDataM> payRolls;
//	private ArrayList<PayslipDataM> payslips;
//	private ArrayList<SalaryCertDataM> salaryCerts;
//	private ArrayList<SavingAccountDataM> savingAccounts;
//	private ArrayList<TaweesapDataM> taweesaps;
//	private ArrayList<YearlyTawi50DataM> yearlyTawi50s;
//	private ArrayList<BankStatementDataM> bankStatements;
//	private ArrayList<PreviousIncomeDataM> previousIncomes;
	
	public int getSeq() {
		return seq;
	}
	public void setSeq(int seq) {
		this.seq = seq;
	}
	public String getIncomeId() {
		return incomeId;
	}
	public void setIncomeId(String incomeId) {
		this.incomeId = incomeId;
	}
	public String getPersonalId() {
		return personalId;
	}
	public void setPersonalId(String personalId) {
		this.personalId = personalId;
	}
	public String getIncomeType() {
		return incomeType;
	}
	public void setIncomeType(String incomeType) {
		this.incomeType = incomeType;
	}
	public ArrayList<IncomeCategoryDataM> getAllIncomes() {
		return allIncomes;
	}
	public void setAllIncomes(ArrayList<IncomeCategoryDataM> allIncomes) {
		this.allIncomes = allIncomes;
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
	
	public String getCompareFlag() {
		return compareFlag;
	}
	public void setCompareFlag(String compareFlag) {
		this.compareFlag = compareFlag;
	}	
	public String getRemark() {
		return remark;
	}
	public void setRemark(String remark) {
		this.remark = remark;
	}
	
	public IncomeCategoryDataM getIncomeCategoryById(String id) {
		if(id != null && allIncomes != null ) {
			for(IncomeCategoryDataM incomeTypeM: allIncomes){
				if(id.equals(incomeTypeM.getId())) {
					return incomeTypeM;
				}
			}
		}
		return null;
	}
	
	public IncomeCategoryDataM getIncomeCategoryBySeq(int seq) {
		if(allIncomes != null ) {
			for(IncomeCategoryDataM incomeTypeM: allIncomes){
				if(incomeTypeM.getSeq() == seq) {
					return incomeTypeM;
				}
			}
		}
		return null;
	}
	public ArrayList<IncomeCategoryDataM> getAllIncomeCategoryByType(String incomeType) {
		if(allIncomes != null && incomeType != null) {
			ArrayList<IncomeCategoryDataM> incomeByTypeList = new ArrayList<IncomeCategoryDataM>();
			for(IncomeCategoryDataM incomeTypeM: allIncomes){
				if(incomeType.equals(incomeTypeM.getIncomeType())) {
					incomeByTypeList.add(incomeTypeM);
				}
			}
			return incomeByTypeList;
		}
		return null;
	}
	public void addIncomeCategory(IncomeCategoryDataM incomeCategory){
		if(allIncomes ==null){
			allIncomes = new ArrayList<IncomeCategoryDataM>();
		}
		allIncomes.add(incomeCategory);	
	}
}
