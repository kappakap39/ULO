package com.eaf.core.ulo.common.dao;

import java.io.Serializable;

public class InfBatchDriverDataM implements Serializable,Cloneable{
	public InfBatchDriverDataM(){
		
	}
	public InfBatchDriverDataM(String host,String user,String password){
		this.host = host;
		this.user = user;
		this.password = password;
	}
	private String host;
	private String user;
	private String password;
	public String getHost() {
		return host;
	}
	public String getUser() {
		return user;
	}
	public String getPassword() {
		return password;
	}
	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("InfBatchDriverDataM [host=");
		builder.append(host);
		builder.append(", user=");
		builder.append(user);
		builder.append(", password=");
		builder.append(password);
		builder.append("]");
		return builder.toString();
	}	
	
}
