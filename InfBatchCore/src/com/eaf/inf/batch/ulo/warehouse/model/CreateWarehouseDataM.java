package com.eaf.inf.batch.ulo.warehouse.model;

import java.io.Serializable;

public class CreateWarehouseDataM implements Serializable, Cloneable{
	private String interfaceCode;
	private String instantId;
	private String applicationGroupId;
	private String applicationGroupNo;
	private int maxLifeCycle;
	
	public String getInterfaceCode() {
		return interfaceCode;
	}
	public void setInterfaceCode(String interfaceCode) {
		this.interfaceCode = interfaceCode;
	}
	public String getInstantId() {
		return instantId;
	}
	public void setInstantId(String instantId) {
		this.instantId = instantId;
	}
	public String getApplicationGroupId() {
		return applicationGroupId;
	}
	public void setApplicationGroupId(String applicationGroupId) {
		this.applicationGroupId = applicationGroupId;
	}
	public String getApplicationGroupNo() {
		return applicationGroupNo;
	}
	public void setApplicationGroupNo(String applicationGroupNo) {
		this.applicationGroupNo = applicationGroupNo;
	}
	public int getMaxLifeCycle() {
		return maxLifeCycle;
	}
	public void setMaxLifeCycle(int maxLifeCycle) {
		this.maxLifeCycle = maxLifeCycle;
	}
}
