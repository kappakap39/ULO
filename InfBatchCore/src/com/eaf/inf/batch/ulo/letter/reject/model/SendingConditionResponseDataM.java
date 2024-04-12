package com.eaf.inf.batch.ulo.letter.reject.model;

import java.io.Serializable;

public class SendingConditionResponseDataM implements Serializable, Cloneable{
	private String letterType;
	private boolean isGenEmail;
	private boolean isGenPaper;
	public String getLetterType() {
		return letterType;
	}
	public void setLetterType(String letterType) {
		this.letterType = letterType;
	}
	public boolean isGenEmail() {
		return isGenEmail;
	}
	public void setGenEmail(boolean isGenEmail) {
		this.isGenEmail = isGenEmail;
	}
	public boolean isGenPaper() {
		return isGenPaper;
	}
	public void setGenPaper(boolean isGenPaper) {
		this.isGenPaper = isGenPaper;
	}
}
