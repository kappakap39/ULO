package com.eaf.service.common.iib.mapper.util;

import java.math.BigDecimal;

import org.apache.commons.lang.builder.ReflectionToStringBuilder;

public class DecisionQuestion {
	private String setCode;
	private String setId;
	private String groupId;
	private String questionId;
	private BigDecimal randomNo;
	private String type;
	private BigDecimal priority;
	public String getSetCode() {
		return setCode;
	}
	public void setSetCode(String setCode) {
		this.setCode = setCode;
	}
	public String getSetId() {
		return setId;
	}
	public void setSetId(String setId) {
		this.setId = setId;
	}
	public String getGroupId() {
		return groupId;
	}
	public void setGroupId(String groupId) {
		this.groupId = groupId;
	}
	public String getQuestionId() {
		return questionId;
	}
	public void setQuestionId(String questionId) {
		this.questionId = questionId;
	}
	public BigDecimal getRandomNo() {
		return randomNo;
	}
	public void setRandomNo(BigDecimal randomNo) {
		this.randomNo = randomNo;
	}	
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}
	public BigDecimal getPriority() {
		return priority;
	}
	public void setPriority(BigDecimal priority) {
		this.priority = priority;
	}
	@Override
	public int hashCode(){
		return questionId == null?0:questionId.hashCode();
	}
	
	@Override
	public String toString() {
		ReflectionToStringBuilder builder = new ReflectionToStringBuilder(this);
		return builder.toString();
	}	
	
}
