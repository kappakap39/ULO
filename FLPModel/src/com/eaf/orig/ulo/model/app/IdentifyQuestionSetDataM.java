package com.eaf.orig.ulo.model.app;

import java.io.Serializable;
import java.sql.Timestamp;

public class IdentifyQuestionSetDataM implements Serializable,Cloneable {
	public IdentifyQuestionSetDataM(){
		super();
	}
	private String questionSetId;	//XRULES_IDENTIFY_QUESTION_SET.QUESTION_SET_ID(VARCHAR2)
	private String verResultId;	//XRULES_IDENTIFY_QUESTION_SET.VER_RESULT_ID(VARCHAR2)
	private String questionSetCode;	//XRULES_IDENTIFY_QUESTION_SET.QUESTION_SET_CODE(VARCHAR2)
	private String questionSetType;	//XRULES_IDENTIFY_QUESTION_SET.QUESTION_SET_TYPE(VARCHAR2)
	private String callTo;	//XRULES_IDENTIFY_QUESTION_SET.CALL_TO(VARCHAR2)
	private String createBy;	//XRULES_IDENTIFY_QUESTION_SET.CREATE_BY(VARCHAR2)
	private Timestamp createDate;	//XRULES_IDENTIFY_QUESTION_SET.CREATE_DATE(DATE)
	private String updateBy;	//XRULES_IDENTIFY_QUESTION_SET.UPDATE_BY(VARCHAR2)
	private Timestamp updateDate;	//XRULES_IDENTIFY_QUESTION_SET.UPDATE_DATE(DATE)
	
	public String getQuestionSetId() {
		return questionSetId;
	}
	public void setQuestionSetId(String questionSetId) {
		this.questionSetId = questionSetId;
	}
	public String getVerResultId() {
		return verResultId;
	}
	public void setVerResultId(String verResultId) {
		this.verResultId = verResultId;
	}
	public String getQuestionSetCode() {
		return questionSetCode;
	}
	public void setQuestionSetCode(String questionSetCode) {
		this.questionSetCode = questionSetCode;
	}
	public String getQuestionSetType() {
		return questionSetType;
	}
	public void setQuestionSetType(String questionSetType) {
		this.questionSetType = questionSetType;
	}
	public String getCallTo() {
		return callTo;
	}
	public void setCallTo(String callTo) {
		this.callTo = callTo;
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
