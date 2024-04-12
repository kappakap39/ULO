package com.eaf.orig.rest.controller.smartdata.model;

import java.util.Date;

import org.codehaus.jackson.map.annotate.JsonSerialize;

@JsonSerialize(include=JsonSerialize.Inclusion.NON_DEFAULT)
public class ResponseM {
	Object data;
	String message;
	int code;
	Date timestamp;
	int totalRecordCount;

	public Object getData() {
		return data;
	}
	public void setData(Object data) {
		this.data = data;
	}
	public String getMessage() {
		return message;
	}
	public void setMessage(String message) {
		this.message = message;
	}
	public int getCode() {
		return code;
	}
	public void setCode(int code) {
		this.code = code;
	}
	public Date getTimestamp() {
		return timestamp;
	}
	public void setTimestamp(Date timestamp) {
		this.timestamp = timestamp;
	}
	public int getTotalRecordCount() {
		return totalRecordCount;
	}
	public void setTotalRecordCount(int totalRecordCount) {
		this.totalRecordCount = totalRecordCount;
	}
	@Override
	public String toString() {
		return "ResponseM [data=" + data + ", message=" + message + ", code=" + code + ", timestamp=" + timestamp
				+ ", totalRecordCount=" + totalRecordCount + "]";
	}
}
