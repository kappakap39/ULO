package com.eaf.orig.ulo.model.ncb;

import java.io.Serializable;
import java.sql.Timestamp;
import java.util.ArrayList;

/**
 * @author septemwi
 *
 */
public class NcbAccountReportDataM implements Serializable,Cloneable{
	public NcbAccountReportDataM(){
		super();
	}
	private String accountReportId;	//NCB_ACCOUNT_REPORT.ACCOUNT_REPORT_ID(VARCHAR2)
	private int seq;				//NCB_ACCOUNT_REPORT.SEQ(NUMBER)
	private String trackingCode;	//NCB_ACCOUNT_REPORT.TRACKING_CODE(VARCHAR2)
	private String restructureGT1Yr;//NCB_ACCOUNT_REPORT.RESTRUCTURE_GT_1YEAR(VARCHAR2)
	private String createBy;		//NCB_ACCOUNT_REPORT.CREATE_BY(VARCHAR2)
	private Timestamp createDate;	//NCB_ACCOUNT_REPORT.CREATE_DATE(DATE)
	private String updateBy;		//NCB_ACCOUNT_REPORT.UPDATE_BY(VARCHAR2)
	private Timestamp updateDate;	//NCB_ACCOUNT_REPORT.UPDATE_DATE(DATE)
	
	private ArrayList<NcbAccountReportRuleDataM> rules;
	
	public String getAccountReportId() {
		return accountReportId;
	}
	public void setAccountReportId(String accountReportId) {
		this.accountReportId = accountReportId;
	}
	public int getSeq() {
		return seq;
	}
	public void setSeq(int seq) {
		this.seq = seq;
	}
	public String getTrackingCode() {
		return trackingCode;
	}
	public void setTrackingCode(String trackingCode) {
		this.trackingCode = trackingCode;
	}
	public String getRestructureGT1Yr() {
		return restructureGT1Yr;
	}
	public void setRestructureGT1Yr(String restructureGT1Yr) {
		this.restructureGT1Yr = restructureGT1Yr;
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
	public ArrayList<NcbAccountReportRuleDataM> getRules() {
		return rules;
	}
	public void setRules(ArrayList<NcbAccountReportRuleDataM> rules) {
		this.rules = rules;
	}	
}
