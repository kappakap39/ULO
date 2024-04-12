package com.eaf.core.ulo.common.model;

import java.io.Serializable;

import com.eaf.ulo.cache.constant.CacheConstant;

@SuppressWarnings("serial")
public class InfBatchRequestDataM implements Serializable,Cloneable{
	public InfBatchRequestDataM(){
		super();
	}
	private String batchId;
	private boolean loadCache = false;
	private String runtime = CacheConstant.Runtime.JAVA;	
	private String configLibPath;
	private String systemDate;
	public String getBatchId() {
		return batchId;
	}
	public void setBatchId(String batchId) {
		this.batchId = batchId;
	}
	public boolean isLoadCache() {
		return loadCache;
	}
	public void setLoadCache(boolean loadCache) {
		this.loadCache = loadCache;
	}
	public String getRuntime() {
		return runtime;
	}
	public void setRuntime(String runtime) {
		this.runtime = runtime;
	}
	
	public String getConfigLibPath() {
		return configLibPath;
	}
	public void setConfigLibPath(String configLibPath) {
		this.configLibPath = configLibPath;
	}
	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("InfBatchRequestDataM [batchId=");
		builder.append(batchId);
		builder.append(", loadCache=");
		builder.append(loadCache);
		builder.append(", runtime=");
		builder.append(runtime);
		builder.append(", InfBatchLibPath=");
		builder.append(configLibPath);
		builder.append("]");
		return builder.toString();
	}
	public String getSystemDate() {
		return systemDate;
	}
	public void setSystemDate(String systemDate) {
		this.systemDate = systemDate;
	}	
}
