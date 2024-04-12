package com.eaf.inf.batch.ulo.letter.reject.model;

import java.io.Serializable;

public class SendingConditionRequestDataM implements Serializable, Cloneable{
	 private boolean isInfiniteWisdomPremierApplication;
	 private RejectLetterDataM rejectLetterDataM;
	 private RejectLetterConfigDataM config;
	 private TemplateReasonCodeDetailDataM templateReasonCodeDetailDataM;
	public boolean isInfiniteWisdomPremierApplication() {
		return isInfiniteWisdomPremierApplication;
	}
	public void setInfiniteWisdomPremierApplication(
			boolean isInfiniteWisdomPremierApplication) {
		this.isInfiniteWisdomPremierApplication = isInfiniteWisdomPremierApplication;
	}
	public RejectLetterDataM getRejectLetterDataM() {
		return rejectLetterDataM;
	}
	public void setRejectLetterDataM(RejectLetterDataM rejectLetterDataM) {
		this.rejectLetterDataM = rejectLetterDataM;
	}
	public RejectLetterConfigDataM getConfig() {
		return config;
	}
	public void setConfig(RejectLetterConfigDataM config) {
		this.config = config;
	}
	public TemplateReasonCodeDetailDataM getTemplateReasonCodeDetailDataM() {
		return templateReasonCodeDetailDataM;
	}
	public void setTemplateReasonCodeDetailDataM(
			TemplateReasonCodeDetailDataM templateReasonCodeDetailDataM) {
		this.templateReasonCodeDetailDataM = templateReasonCodeDetailDataM;
	}
}
