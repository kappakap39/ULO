package com.eaf.service.common.performance;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.apache.log4j.Logger;

import com.google.gson.Gson;




public class TraceController {
	
	private static transient Logger logger = Logger.getLogger(TraceController.class);
	List<Trace> traces = new ArrayList<Trace>();
	String className;
	String traceId;
	Timestamp startTime;
	Timestamp finishTime;
	public TraceController(String className){
		this.className = className;
		this.startTime = new Timestamp(new Date().getTime());
	}
	public TraceController(String className,String traceId){
		this.className = className;
		this.traceId = traceId;
		this.startTime = new Timestamp(new Date().getTime());
	}
	public void create(String functionId){
		Trace trace = new Trace();
		trace.traceId = traceId;
		trace.functionId = functionId;
		trace.startTime = System.currentTimeMillis();
		traces.add(trace);
	}
	private Trace find(String funcationId){
		if(null!=traces){
			for(Trace trace:traces){
				if(null!=trace.functionId&&trace.functionId.equals(funcationId)){
					return trace;
				}
			}
		}
		return null;
	}
	public void end(String funcationId){
		Trace trace = find(funcationId);
		if(null!=trace){
			trace.finishedTime = System.currentTimeMillis();
		}
	}
	public void trace(){
		this.finishTime = new Timestamp(new Date().getTime());
		TraceController trace = new TraceController(this.className);
		trace.setClassName(this.className);
		trace.setFinishTime(this.finishTime);
		trace.setStartTime(this.startTime);
		trace.setTraceId(this.traceId);
		trace.setTraces(this.traces);
		logger.info("TRACE : "+new Gson().toJson(trace));
	}
	public String getTraceId() {
		return traceId;
	}
	public void setTraceId(String traceId) {
		this.traceId = traceId;
	}
	public List<Trace> getTraces() {
		return traces;
	}
	public void setTraces(List<Trace> traces) {
		this.traces = traces;
	}
	public String getClassName() {
		return className;
	}
	public void setClassName(String className) {
		this.className = className;
	}
	public Timestamp getStartTime() {
		return startTime;
	}
	public void setStartTime(Timestamp startTime) {
		this.startTime = startTime;
	}
	public Timestamp getFinishTime() {
		return finishTime;
	}
	public void setFinishTime(Timestamp finishTime) {
		this.finishTime = finishTime;
	}	
	
}
