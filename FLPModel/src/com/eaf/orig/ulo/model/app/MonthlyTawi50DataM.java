package com.eaf.orig.ulo.model.app;

import java.sql.Timestamp;
import java.util.ArrayList;

public class MonthlyTawi50DataM extends IncomeCategoryDataM{
	public MonthlyTawi50DataM(){
		super();
	}
	private String monthlyTawiId;	//INC_MONTHLY_TAWI.MONTHLY_TAWI_ID(VARCHAR2)
	private String companyName;	//INC_MONTHLY_TAWI.COMPANY_NAME(VARCHAR2)
	private String tawiIncomeType;	//INC_MONTHLY_TAWI.INCOME_TYPE(VARCHAR2)
	private String createBy;	//INC_MONTHLY_TAWI.CREATE_BY(VARCHAR2)
	private Timestamp createDate;	//INC_MONTHLY_TAWI.CREATE_DATE(DATE)
	private String updateBy;	//INC_MONTHLY_TAWI.UPDATE_BY(VARCHAR2)
	private Timestamp updateDate;	//INC_MONTHLY_TAWI.UPDATE_DATE(DATE)
	
	private ArrayList<MonthlyTawi50DetailDataM> monthlyTaxi50Details;
		
	public String getMonthlyTawiId() {
		return monthlyTawiId;
	}
	public void setMonthlyTawiId(String monthlyTawiId) {
		this.monthlyTawiId = monthlyTawiId;
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
	public String getCompanyName() {
		return companyName;
	}
	public void setCompanyName(String companyName) {
		this.companyName = companyName;
	}	
	public String getTawiIncomeType() {
		return tawiIncomeType;
	}
	public void setTawiIncomeType(String tawiIncomeType) {
		this.tawiIncomeType = tawiIncomeType;
	}
	public ArrayList<MonthlyTawi50DetailDataM> getMonthlyTaxi50Details() {
		return monthlyTaxi50Details;
	}
	public void setMonthlyTaxi50Details(ArrayList<MonthlyTawi50DetailDataM> monthlyTaxi50Details) {
		this.monthlyTaxi50Details = monthlyTaxi50Details;
	}
	public MonthlyTawi50DetailDataM getMonthlyTawiDetailById(String monthlyTawiDetailId) {
		if(monthlyTawiDetailId != null && monthlyTaxi50Details != null) {
			for(MonthlyTawi50DetailDataM monthlyDetailM: monthlyTaxi50Details){
				if(monthlyTawiDetailId.equals(monthlyDetailM.getMonthlyTawiDetailId())) {
					return monthlyDetailM;
				}
			}
		}
		return null;
	}
	@Override
	public String getId() {
		return getMonthlyTawiId();
	}
	
}
