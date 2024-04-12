package com.eaf.service.common.api;

import java.io.Serializable;

public class ServiceModuleMSGDataM implements Serializable,Cloneable{
	private static final long serialVersionUID = 1L;	
	private String className;
	private Object value;
	public ServiceModuleMSGDataM(String className, Object value) {
		this.className = className;
		this.value = value;
	}
	public String getClassName() {
		return className;
	}
	public void setClassName(String className) {
		this.className = className;
	}
	public Object getValue() {
		return value;
	}
	public void setValue(Object value) {
		this.value = value;
	}
	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("ModuleMSGDataM [className=");
		builder.append(className);
		builder.append(", value=");
		builder.append(value);
		builder.append("]\n");
		return builder.toString();
	}
}
