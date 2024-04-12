package com.eaf.orig.ulo.model.ia;

import java.io.Serializable;

public class ImageDocumentResponse implements Serializable,Cloneable{
	public ImageDocumentResponse(){
		super();
		kbankHeader = new KBankHeader();
	}
	private KBankHeader kbankHeader;
	public KBankHeader getKbankHeader() {
		return kbankHeader;
	}
	public void setKbankHeader(KBankHeader kbankHeader) {
		this.kbankHeader = kbankHeader;
	}	
}
