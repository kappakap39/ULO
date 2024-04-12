package com.eaf.orig.ulo.model.app;

import java.io.Serializable;
import java.sql.Date;

/**Associated with Table ORIG_LOAN_PRICING
 * @author Avalant
 *
 */
public class LoanPricingDataM implements Serializable {
	private static final long serialVersionUID = -1983092363718473896L;
	private String pricingId;					//PRICING_ID
	private String loanId;						//LOAN_ID
	private Integer feeStartMonth;				//FEE_START_MONTH
	private Integer feeEndMonth;				//FEE_END_MONTH
	private String terminationFeeFlag;			//TERMINATION_FEE_FLAG
	private String terminationFeeCode;			//TERMINATION_FEE_CODE
	private String feeWaiveCode;				//FEE_WAIVE_CODE
	private Date createDate;					//CREATE_DATE
	private String createBy;					//CREATE_BY
	private Date updateDate;					//UPDATE_DATE
	private String updateBy;					//UPDATE_BY
	public String getPricingId() {
		return pricingId;
	}
	public void setPricingId(String pricingId) {
		this.pricingId = pricingId;
	}
	public String getLoanId() {
		return loanId;
	}
	public void setLoanId(String loanId) {
		this.loanId = loanId;
	}
	public Integer getFeeStartMonth() {
		return feeStartMonth;
	}
	public void setFeeStartMonth(Integer feeStartMonth) {
		this.feeStartMonth = feeStartMonth;
	}
	public Integer getFeeEndMonth() {
		return feeEndMonth;
	}
	public void setFeeEndMonth(Integer feeEndMonth) {
		this.feeEndMonth = feeEndMonth;
	}
	public String getTerminationFeeFlag() {
		return terminationFeeFlag;
	}
	public void setTerminationFeeFlag(String terminationFeeFlag) {
		this.terminationFeeFlag = terminationFeeFlag;
	}
	public String getTerminationFeeCode() {
		return terminationFeeCode;
	}
	public void setTerminationFeeCode(String terminationFeeCode) {
		this.terminationFeeCode = terminationFeeCode;
	}
	public String getFeeWaiveCode() {
		return feeWaiveCode;
	}
	public void setFeeWaiveCode(String feeWaiveCode) {
		this.feeWaiveCode = feeWaiveCode;
	}
	public Date getCreateDate() {
		return createDate;
	}
	public void setCreateDate(Date createDate) {
		this.createDate = createDate;
	}
	public String getCreateBy() {
		return createBy;
	}
	public void setCreateBy(String createBy) {
		this.createBy = createBy;
	}
	public Date getUpdateDate() {
		return updateDate;
	}
	public void setUpdateDate(Date updateDate) {
		this.updateDate = updateDate;
	}
	public String getUpdateBy() {
		return updateBy;
	}
	public void setUpdateBy(String updateBy) {
		this.updateBy = updateBy;
	}
	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("LoanPricingDataM [pricingId=");
		builder.append(pricingId);
		builder.append(", loanId=");
		builder.append(loanId);
		builder.append(", feeStartMonth=");
		builder.append(feeStartMonth);
		builder.append(", feeEndMonth=");
		builder.append(feeEndMonth);
		builder.append(", terminationFeeFlag=");
		builder.append(terminationFeeFlag);
		builder.append(", terminationFeeCode=");
		builder.append(terminationFeeCode);
		builder.append(", feeWaiveCode=");
		builder.append(feeWaiveCode);
		builder.append(", createDate=");
		builder.append(createDate);
		builder.append(", createBy=");
		builder.append(createBy);
		builder.append(", updateDate=");
		builder.append(updateDate);
		builder.append(", updateBy=");
		builder.append(updateBy);
		builder.append("]");
		return builder.toString();
	}	
}
