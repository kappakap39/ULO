package com.eaf.service.common.activemq.model;

public class SSFulfillmentChangeAppointment extends SSFulfillmentInfoDefault {
	
	private static final long serialVersionUID = 3239193368206558335L;
	private Integer NoOfAppointmentChange;
	private String HasUpdatedKCPS;
	
	public Integer getNoOfAppointmentChange() {
		return NoOfAppointmentChange;
	}
	public void setNoOfAppointmentChange(Integer noOfAppointmentChange) {
		NoOfAppointmentChange = noOfAppointmentChange;
	}
	public String getHasUpdatedKCPS() {
		return HasUpdatedKCPS;
	}
	public void setHasUpdatedKCPS(String hasUpdatedKCPS) {
		HasUpdatedKCPS = hasUpdatedKCPS;
	}
	
	
}
