package com.eaf.orig.rest.controller.smartdata.model;

import java.io.Serializable;
import java.util.List;

@SuppressWarnings("serial")
public class SmartImageResponse implements Serializable,Cloneable{
	public SmartImageResponse(){
		
	}
	private String resultCode;
	private String resultDesc;
	private List<SMImgM> images;
	private List<SMMainM> fields;

	public List<SMImgM> getImages() {
		return images;
	}
	public void setImages(List<SMImgM> images) {
		this.images = images;
	}
	public List<SMMainM> getFields() {
		return fields;
	}
	public void setFields(List<SMMainM> fields) {
		this.fields = fields;
	}	
	public String getResultCode() {
		return resultCode;
	}
	public void setResultCode(String resultCode) {
		this.resultCode = resultCode;
	}
	public String getResultDesc() {
		return resultDesc;
	}
	public void setResultDesc(String resultDesc) {
		this.resultDesc = resultDesc;
	}
	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("SmartImageResponse [resultCode=");
		builder.append(resultCode);
		builder.append(", resultDesc=");
		builder.append(resultDesc);
		builder.append(", images=");
		builder.append(images);
		builder.append(", fields=");
		builder.append(fields);
		builder.append("]");
		return builder.toString();
	}
}
