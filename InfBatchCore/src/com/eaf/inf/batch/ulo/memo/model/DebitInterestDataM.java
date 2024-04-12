package com.eaf.inf.batch.ulo.memo.model;

import java.io.Serializable;
import java.util.Date;

public class DebitInterestDataM implements Serializable, Cloneable {

	private static final long serialVersionUID = 1L;

	private Date startDate;			// 24
	private String fixedRate;		// 25
	private String referenceRate;		// 26
	private String spreadRate;			// 27

	public Date getStartDate() {
		return startDate;
	}

	public void setStartDate(Date startDate) {
		this.startDate = startDate;
	}

	public String getFixedInterestRate() {
		return fixedRate;
	}

	public void setFixedInterestRate(String fixedInterestRate) {
		this.fixedRate = fixedInterestRate;
	}

	public String getReferenceRate() {
		return referenceRate;
	}

	public void setReferenceRate(String referenceRate) {
		this.referenceRate = referenceRate;
	}

	public String getSpreadRate() {
		return spreadRate;
	}

	public void setSpreadRate(String spreadRate) {
		this.spreadRate = spreadRate;
	}

}
