package com.eaf.orig.ulo.model.app;

import java.io.Serializable;
import java.sql.Timestamp;

public class WebVerificationDataM implements Serializable,Cloneable{
	public WebVerificationDataM(){
		super();
	}
	private String verResultId;	//XRULES_WEB_VERIFICATION.VER_RESULT_ID(VARCHAR2)	
	private String webVerifyId;	//XRULES_WEB_VERIFICATION.WEB_VERIFY_ID(VARCHAR2)
//	private String judgePosition;	//XRULES_WEB_VERIFICATION.JUDGE_POSITION(VARCHAR2)
	private String remark;	//XRULES_WEB_VERIFICATION.REMARK(VARCHAR2)
	private String webCode;	//XRULES_WEB_VERIFICATION.WEB_CODE(VARCHAR2)
	private String verResult;	//XRULES_WEB_VERIFICATION.VER_RESULT(VARCHAR2)
	private String createBy;	//XRULES_WEB_VERIFICATION.CREATE_BY(VARCHAR2)
	private Timestamp createDate;	//XRULES_WEB_VERIFICATION.CREATE_DATE(DATE)
	private String updateBy;	//XRULES_WEB_VERIFICATION.UPDATE_BY(VARCHAR2)
	private Timestamp updateDate;	//XRULES_WEB_VERIFICATION.UPDATE_DATE(DATE)
		
	public String getVerResultId() {
		return verResultId;
	}
	public void setVerResultId(String verResultId) {
		this.verResultId = verResultId;
	}
	public String getWebVerifyId() {
		return webVerifyId;
	}
	public void setWebVerifyId(String webVerifyId) {
		this.webVerifyId = webVerifyId;
	}
//	public String getJudgePosition() {
//		return judgePosition;
//	}
//	public void setJudgePosition(String judgePosition) {
//		this.judgePosition = judgePosition;
//	}
	public String getRemark() {
		return remark;
	}
	public void setRemark(String remark) {
		this.remark = remark;
	}
	public String getWebCode() {
		return webCode;
	}
	public void setWebCode(String webCode) {
		this.webCode = webCode;
	}
	public String getVerResult() {
		return verResult;
	}
	public void setVerResult(String verResult) {
		this.verResult = verResult;
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
	
}
