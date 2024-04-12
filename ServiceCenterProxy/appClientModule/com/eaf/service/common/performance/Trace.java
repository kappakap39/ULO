package com.eaf.service.common.performance;

import java.io.Serializable;

@SuppressWarnings("serial")
public class Trace implements Serializable,Cloneable {
public Trace(){
		
	}
	String traceId;
	String functionId;
	long startTime;
	long finishedTime;
	
	public String getTraceId() {
		return traceId;
	}
	public void setTraceId(String traceId) {
		this.traceId = traceId;
	}
	public String getFunctionId() {
		return functionId;
	}
	public void setFunctionId(String funcationId) {
		this.functionId = funcationId;
	}
	public long getStartTime() {
		return startTime;
	}
	public void setStartTime(long startTime) {
		this.startTime = startTime;
	}
	public long getFinishedTime() {
		return finishedTime;
	}
	public void setFinishedTime(long finishedTime) {
		this.finishedTime = finishedTime;
	}	
}
