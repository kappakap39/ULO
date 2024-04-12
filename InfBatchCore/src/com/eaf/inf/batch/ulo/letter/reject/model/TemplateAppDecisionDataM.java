package com.eaf.inf.batch.ulo.letter.reject.model;

import java.io.Serializable;
@SuppressWarnings("serial")
public class TemplateAppDecisionDataM implements Serializable, Cloneable{
	public TemplateAppDecisionDataM(){
		super();
	}
	boolean isRejectAll;
	boolean isAddSupApplication;
	boolean isMainApprove;
	boolean isSupReject;
	public boolean isRejectAll() {
		return isRejectAll;
	}
	public void setRejectAll(boolean isRejectAll) {
		this.isRejectAll = isRejectAll;
	}
	public boolean isAddSupApplication() {
		return isAddSupApplication;
	}
	public void setAddSupApplication(boolean isAddSupApplication) {
		this.isAddSupApplication = isAddSupApplication;
	}
	public boolean isMainApprove() {
		return isMainApprove;
	}
	public void setMainApprove(boolean isMainApprove) {
		this.isMainApprove = isMainApprove;
	}
	public boolean isSupReject() {
		return isSupReject;
	}
	public void setSupReject(boolean isSupReject) {
		this.isSupReject = isSupReject;
	}
	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("TemplateAppDecisionDataM [isRejectAll=");
		builder.append(isRejectAll);
		builder.append(", isAddSupApplication=");
		builder.append(isAddSupApplication);
		builder.append(", isMainApprove=");
		builder.append(isMainApprove);
		builder.append(", isSupReject=");
		builder.append(isSupReject);
		builder.append("]");
		return builder.toString();
	}
}
