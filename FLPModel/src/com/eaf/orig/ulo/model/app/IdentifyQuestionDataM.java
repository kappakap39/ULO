package com.eaf.orig.ulo.model.app;

import java.io.Serializable;
import java.sql.Timestamp;

public class IdentifyQuestionDataM implements Serializable,Cloneable {
	public IdentifyQuestionDataM(){
		super();
	}
	private String identifyQuestionId;	//XRULES_IDENTIFY_QUESTION.INDENTIFY_QUESTION_ID(VARCHAR2)
	private String verResultId;	//XRULES_IDENTIFY_QUESTION.VER_RESULT_ID(VARCHAR2)	
	private String customerAnswer;	//XRULES_IDENTIFY_QUESTION.CUSTOMER_ANSWER(VARCHAR2)
	private int seq;	//XRULES_IDENTIFY_QUESTION.SEQ(NUMBER)
	private String result;	//XRULES_IDENTIFY_QUESTION.RESULT(VARCHAR2)
	private String questionNo;	//XRULES_IDENTIFY_QUESTION.QUESTION_NO(VARCHAR2)
	private String createBy;	//XRULES_IDENTIFY_QUESTION.CREATE_BY(VARCHAR2)
	private Timestamp createDate;	//XRULES_IDENTIFY_QUESTION.CREATE_DATE(DATE)
	private String updateBy;	//XRULES_IDENTIFY_QUESTION.UPDATE_BY(VARCHAR2)
	private Timestamp updateDate;	//XRULES_IDENTIFY_QUESTION.UPDATE_DATE(DATE)
	private String questionSetType; //XRULES_IDENTIFY_QUESTION.QUESTION_SET_TYPE(VARCHAR2)
	private String questionSetCode; //XRULES_IDENTIFY_QUESTION.QUESTION_SET_CODE(VARCHAR2)
	
	public String getIdentifyQuestionId() {
		return identifyQuestionId;
	}
	public void setIdentifyQuestionId(String identifyQuestionId) {
		this.identifyQuestionId = identifyQuestionId;
	}	
	public String getVerResultId() {
		return verResultId;
	}
	public void setVerResultId(String verResultId) {
		this.verResultId = verResultId;
	}
	public String getCustomerAnswer() {
		return customerAnswer;
	}
	public void setCustomerAnswer(String customerAnswer) {
		this.customerAnswer = customerAnswer;
	}
	public int getSeq() {
		return seq;
	}
	public void setSeq(int seq) {
		this.seq = seq;
	}
	public String getResult() {
		return result;
	}
	public void setResult(String result) {
		this.result = result;
	}
	public String getQuestionNo() {
		return questionNo;
	}
	public void setQuestionNo(String questionNo) {
		this.questionNo = questionNo;
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
	public String getQuestionSetType() {
		return questionSetType;
	}
	public void setQuestionSetType(String questionSetType) {
		this.questionSetType = questionSetType;
	}
	public String getQuestionSetCode() {
		return questionSetCode;
	}
	public void setQuestionSetCode(String questionSetCode) {
		this.questionSetCode = questionSetCode;
	}
}
