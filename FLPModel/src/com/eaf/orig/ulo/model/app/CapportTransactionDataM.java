package com.eaf.orig.ulo.model.app;

import java.io.Serializable;
import java.math.BigDecimal;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.logging.LogManager;

@SuppressWarnings("serial")
public class CapportTransactionDataM implements Serializable,Cloneable {
	private static final String	AMT_GRANT_TYPE = "AMT";
	private static final String	APP_GRANT_TYPE = "NUM";
	
	private String	capportId;			// CAP_PORT_TRANSACTION.CAP_PORT_ID
	private String	capportName;		// CAP_PORT_TRANSACTION.CAP_PORT_NAME
	private String	applicationGroupId;	// CAP_PORT_TRANSACTION.APPLICATION_GROUP_ID
	private String	applicationRecordId;// CAP_PORT_TRANSACTION.APPLICATION_RECORD_ID
	private String	grantType;			// CAP_PORT_TRANSACTION.GRANT_TYPE
	private BigDecimal approveAmt;		// CAP_PORT_TRANSACTION.APPROVE_AMOUNT
	private BigDecimal approveApp;		// CAP_PORT_TRANSACTION.APPROVE_NUMBER_OF_APP
	private String	adjustFlag;			// CAP_PORT_TRANSACTION.ADJUST_FLAG
	private Timestamp	createDate;		// CAP_PORT_TRANSACTION.CREATE_DATE
	private String	createBy;			// CAP_PORT_TRANSACTION.CREATE_BY
	private Timestamp	updateDate;		// CAP_PORT_TRANSACTION.UPDATE_DATE
	private String	updateBy;			// CAP_PORT_TRANSACTION.UPDATE_BY

	public String getCapportId() {
		return capportId;
	}
	public void setCapportId(String capportId) {
		this.capportId = capportId;
	}
	public String getCapportName() {
		return capportName;
	}
	public void setCapportName(String capportName) {
		this.capportName = capportName;
	}
	public String getApplicationGroupId() {
		return applicationGroupId;
	}
	public void setApplicationGroupId(String applicationGroupId) {
		this.applicationGroupId = applicationGroupId;
	}
	public String getApplicationRecordId() {
		return applicationRecordId;
	}
	public void setApplicationRecordId(String applicationRecordId) {
		this.applicationRecordId = applicationRecordId;
	}
	public String getGrantType() {
		return grantType;
	}
	public void setGrantType(String grantType) {
		this.grantType = grantType;
	}
	public BigDecimal getApproveAmt() {
		return approveAmt;
	}
	public void setApproveAmt(BigDecimal approveAmt) {
		this.approveAmt = approveAmt;
	}
	public BigDecimal getApproveApp() {
		return approveApp;
	}
	public void setApproveApp(BigDecimal approveApp) {
		this.approveApp = approveApp;
	}
	public String getAdjustFlag() {
		return adjustFlag;
	}
	public void setAdjustFlag(String adjustFlag) {
		this.adjustFlag = adjustFlag;
	}
	public Timestamp getCreateDate() {
		return createDate;
	}
	public void setCreateDate(Timestamp createDate) {
		this.createDate = createDate;
	}
	public String getCreateBy() {
		return createBy;
	}
	public void setCreateBy(String createBy) {
		this.createBy = createBy;
	}
	public Timestamp getUpdateDate() {
		return updateDate;
	}
	public void setUpdateDate(Timestamp updateDate) {
		this.updateDate = updateDate;
	}
	public String getUpdateBy() {
		return updateBy;
	}
	public void setUpdateBy(String updateBy) {
		this.updateBy = updateBy;
	}
	public void setAmtGrantType() {
		this.grantType = AMT_GRANT_TYPE;
	}
	public void setAppGrantType() {
		this.grantType = APP_GRANT_TYPE;
	}
	public boolean isAmtGrantType() {
		return (this.grantType == AMT_GRANT_TYPE);
	}
	public boolean isAppGrantType() {
		return (this.grantType == APP_GRANT_TYPE);
	}
	public String toString() {
		StringBuilder s = new StringBuilder("{");
		s.append("capportId="); s.append(capportId);
		s.append(",capportName="); s.append(capportName);
		s.append(",applicationGroupId="); s.append(applicationGroupId);
		s.append(",applicationRecordId="); s.append(applicationRecordId);
		s.append(",grantType="); s.append(grantType);
		s.append(",approveAmt="); s.append(approveAmt);
		s.append(",approveApp="); s.append(approveApp);
		s.append(",adjustFlag="); s.append(adjustFlag);
		s.append(",createDate="); s.append(createDate);
		s.append(",createBy="); s.append(createBy);
		s.append(",updateDate="); s.append(updateDate);
		s.append(",updateBy="); s.append(updateBy);
		s.append("}");
		return s.toString();
	}
}