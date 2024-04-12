package com.eaf.inf.batch.ulo.letter.reject.model;

import java.io.Serializable;
@SuppressWarnings("serial")
public class RejectLetterPDFProcessRequestDataM implements Serializable,Cloneable{
	private RejectLetterPDFDataM rejectLetterPdf;
	private RejectLetterPDFConfigDataM config;
	public RejectLetterPDFDataM getRejectLetterPdf(){
		return rejectLetterPdf;
	}
	public void setRejectLetterPdf(RejectLetterPDFDataM rejectLetterPdf){
		this.rejectLetterPdf = rejectLetterPdf;
	}
	public RejectLetterPDFConfigDataM getConfig(){
		return config;
	}
	public void setConfig(RejectLetterPDFConfigDataM config){
		this.config = config;
	}
}
