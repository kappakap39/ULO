package com.eaf.inf.batch.ulo.notification.eod.sendto;

import java.io.Serializable;

@SuppressWarnings("serial")
public class EodEmployee implements Serializable,Cloneable{
	public EodEmployee(){
		
	}
	private String empId;
	private String notificationType;
	
	public String getEmpId() {
		return empId;
	}
	public void setEmpId(String empId) {
		this.empId = empId;
	}
	public String getNotificationType() {
		return notificationType;
	}
	public void setNotificationType(String notificationType) {
		this.notificationType = notificationType;
	}
	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("EodEmployee [");
		if (empId != null) {
			builder.append("empId=");
			builder.append(empId);
			builder.append(", ");
		}
		if (notificationType != null) {
			builder.append("notificationType=");
			builder.append(notificationType);
		}
		builder.append("]");
		return builder.toString();
	}
	
}
