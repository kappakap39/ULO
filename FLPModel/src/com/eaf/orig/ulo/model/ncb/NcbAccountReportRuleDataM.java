package com.eaf.orig.ulo.model.ncb;

import java.io.Serializable;
import java.sql.Timestamp;

/**
 * @author septemwi
 *
 */
public class NcbAccountReportRuleDataM implements Serializable,Cloneable{
	public NcbAccountReportRuleDataM(){
		super();
	}
	private String accountReportId;	//NCB_ACCOUNT_RULE_REPORT.ACCOUNT_REPORT_ID(VARCHAR2)
	private String ruleId;			//NCB_ACCOUNT_RULE_REPORT.RULE_ID(NUMBER)
	private String ruleResult;		//NCB_ACCOUNT_RULE_REPORT.RULE_RESULT(VARCHAR2)
	private String productCode;		//NCB_ACCOUNT_RULE_REPORT.PRODUCT_CODE(VARCHAR2)
	private String createBy;		//NCB_ACCOUNT_RULE_REPORT.CREATE_BY(VARCHAR2)
	private Timestamp createDate;	//NCB_ACCOUNT_RULE_REPORT.CREATE_DATE(DATE)
	private String updateBy;		//NCB_ACCOUNT_RULE_REPORT.UPDATE_BY(VARCHAR2)
	private Timestamp updateDate;	//NCB_ACCOUNT_RULE_REPORT.UPDATE_DATE(DATE)
	private String ruleCondition;
	
	public String getAccountReportId() {
		return accountReportId;
	}
	public void setAccountReportId(String accountReportId) {
		this.accountReportId = accountReportId;
	}
	public String getRuleId() {
		return ruleId;
	}
	public void setRuleId(String ruleId) {
		this.ruleId = ruleId;
	}
	public String getRuleResult() {
		return ruleResult;
	}
	public void setRuleResult(String ruleResult) {
		this.ruleResult = ruleResult;
	}
	public String getProductCode() {
		return productCode;
	}
	public void setProductCode(String productCode) {
		this.productCode = productCode;
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
	public String getRuleCondition() {
		return ruleCondition;
	}
	public void setRuleCondition(String ruleCondition) {
		this.ruleCondition = ruleCondition;
	}	
}
