package com.eaf.orig.ulo.control.util.model;

import java.io.Serializable;

@SuppressWarnings("serial")
public class ApplicationResultResponse implements Serializable,Cloneable{
	private String projectCode;
	private String applyType;
	private String recommendDecision;
	
	public String getProjectCode() {
		return projectCode;
	}
	public void setProjectCode(String projectCode) {
		this.projectCode = projectCode;
	}
	public String getApplyType() {
		return applyType;
	}
	public void setApplyType(String applyType) {
		this.applyType = applyType;
	}
	
	public String getRecommendDecision() {
		return recommendDecision;
	}
	public void setRecommendDecision(String recommendDecision) {
		this.recommendDecision = recommendDecision;
	}
	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("ApplicationResultResponse [projectCode=");
		builder.append(projectCode);
		builder.append(", applyType=");
		builder.append(applyType);
		builder.append(", recommendDecision=");
		builder.append(recommendDecision);
		builder.append("]");
		return builder.toString();
	}	
}
