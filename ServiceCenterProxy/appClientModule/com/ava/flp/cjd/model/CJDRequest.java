package com.ava.flp.cjd.model;

import java.io.Serializable;


public class CJDRequest implements Serializable, Cloneable{
	public CJDRequest(){
		super();
	}
	CJDRequestHeader header;
	CJDRequestBody body;
	
	public CJDRequestHeader getHeader() {
		return header;
	}
	public void setHeader(CJDRequestHeader header) {
		this.header = header;
	}
	public CJDRequestBody getBody() {
		return body;
	}
	public void setBody(CJDRequestBody body) {
		this.body = body;
	}

	
}
