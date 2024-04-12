package com.eaf.core.ulo.common.task.model;

import java.io.Serializable;

public class TaskObjectDataM implements Serializable,Cloneable{
	public TaskObjectDataM(){
		super();
	}
	private String uniqueId;
	private String uniqueType;
	private Object object;
	private Object configuration;
	public String getUniqueId() {
		return uniqueId;
	}
	public void setUniqueId(String uniqueId) {
		this.uniqueId = uniqueId;
	}
	public Object getObject() {
		return object;
	}
	public void setObject(Object object) {
		this.object = object;
	}
	public String getUniqueType() {
		return uniqueType;
	}
	public void setUniqueType(String uniqueType) {
		this.uniqueType = uniqueType;
	}
	public Object getConfiguration() {
		return configuration;
	}
	public void setConfiguration(Object configuration) {
		this.configuration = configuration;
	}
	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("TaskObjectDataM [uniqueId=");
		builder.append(uniqueId);
		builder.append(", uniqueType=");
		builder.append(uniqueType);
		builder.append(", object=");
		builder.append(object);
		builder.append(", configuration=");
		builder.append(configuration);
		builder.append("]");
		return builder.toString();
	}	
}
