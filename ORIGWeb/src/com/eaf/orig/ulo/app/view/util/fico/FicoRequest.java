package com.eaf.orig.ulo.app.view.util.fico;

import java.io.Serializable;

import com.eaf.orig.ulo.model.app.ApplicationGroupDataM;

public class FicoRequest implements Serializable,Cloneable{
	public FicoRequest(){
		super();
	}
	private String ficoFunction;
	private ApplicationGroupDataM applicationGroup;
	private String roleId;
	public String getFicoFunction() {
		return ficoFunction;
	}
	public void setFicoFunction(String ficoFunction) {
		this.ficoFunction = ficoFunction;
	}
	public ApplicationGroupDataM getApplicationGroup() {
		return applicationGroup;
	}
	public void setApplicationGroup(ApplicationGroupDataM applicationGroup) {
		this.applicationGroup = applicationGroup;
	}
	public String getRoleId() {
		return roleId;
	}
	public void setRoleId(String roleId) {
		this.roleId = roleId;
	}		
}
