package com.ava.flp.cjd.model;

import java.io.Serializable;


public class CJDResponse implements Serializable, Cloneable{
	public CJDResponse(){
		super();
	}
	CJDResponseHeader header;
	
	public CJDResponseHeader getHeader() {
		return header;
	}
	public void setHeader(CJDResponseHeader header) {
		this.header = header;
	}
}
