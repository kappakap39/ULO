package com.eaf.inf.batch.ulo.report.model;

import java.io.Serializable;

public class OperatingResultDataM implements Serializable, Cloneable{
	private String period;
	private String countApprove;
	private String countReject;
	private String countCancel;
	private String appIn;
	private String wip;
	private String wipFollow;
	private String percentDocComplete;
	private String top5DocNotComplete;
	private String top5ReasonReject;
	private String top5CauseNotComplete;
	private String vetoSubject;
	private String vetoPass;

	public String getPeriod() {
		return period;
	}
	public void setPeriod(String period) {
		this.period = period;
	}
	public String getCountApprove() {
		return countApprove;
	}
	public void setCountApprove(String countApprove) {
		this.countApprove = countApprove;
	}
	public String getCountReject() {
		return countReject;
	}
	public void setCountReject(String countReject) {
		this.countReject = countReject;
	}
	public String getCountCancel() {
		return countCancel;
	}
	public void setCountCancel(String countCancel) {
		this.countCancel = countCancel;
	}
	public String getAppIn() {
		return appIn;
	}
	public void setAppIn(String appIn) {
		this.appIn = appIn;
	}
	public String getWip() {
		return wip;
	}
	public void setWip(String wip) {
		this.wip = wip;
	}
	public String getWipFollow() {
		return wipFollow;
	}
	public void setWipFollow(String wipFollow) {
		this.wipFollow = wipFollow;
	}
	public String getPercentDocComplete() {
		return percentDocComplete;
	}
	public void setPercentDocComplete(String percentDocComplete) {
		this.percentDocComplete = percentDocComplete;
	}
	public String getTop5DocNotComplete() {
		return top5DocNotComplete;
	}
	public void setTop5DocNotComplete(String top5DocNotComplete) {
		this.top5DocNotComplete = top5DocNotComplete;
	}
	public String getTop5ReasonReject() {
		return top5ReasonReject;
	}
	public void setTop5ReasonReject(String top5ReasonReject) {
		this.top5ReasonReject = top5ReasonReject;
	}
	public String getTop5CauseNotComplete() {
		return top5CauseNotComplete;
	}
	public void setTop5CauseNotComplete(String top5CauseNotComplete) {
		this.top5CauseNotComplete = top5CauseNotComplete;
	}
	public String getVetoSubject() {
		return vetoSubject;
	}
	public void setVetoSubject(String vetoSubject) {
		this.vetoSubject = vetoSubject;
	}
	public String getVetoPass() {
		return vetoPass;
	}
	public void setVetoPass(String vetoPass) {
		this.vetoPass = vetoPass;
	}
	
}
