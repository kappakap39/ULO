package com.ava.dynamic.model;

import java.util.List;

import org.apache.commons.lang3.builder.ReflectionToStringBuilder;

import com.ava.dynamic.model.nav.Navigation;

public class Owner {
	private String userId;
	private String name;
	private String positionId;
	private String teamId;
	private String teamName;
	private String defaultGridId;
	private List<Owner> otherTeamMember;
	private Navigation navigation;
	
	public Owner(){
		navigation = new Navigation();
	}
	
	public String getUserId() {
		return userId;
	}
	public void setUserId(String userId) {
		this.userId = userId;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getPositionId() {
		return positionId;
	}
	public void setPositionId(String positionId) {
		this.positionId = positionId;
	}
	public String getTeamId() {
		return teamId;
	}
	public void setTeamId(String teamId) {
		this.teamId = teamId;
	}
	public String getTeamName() {
		return teamName;
	}
	public void setTeamName(String teamName) {
		this.teamName = teamName;
	}
	public List<Owner> getOtherTeamMember() {
		return otherTeamMember;
	}
	public void setOtherTeamMember(List<Owner> otherTeamMember) {
		this.otherTeamMember = otherTeamMember;
	}
	public String getDefaultGridId() {
		return defaultGridId;
	}
	public void setDefaultGridId(String defaultGridId) {
		this.defaultGridId = defaultGridId;
	}
	public Navigation getNavigation() {
		return navigation;
	}
	public void setNavigation(Navigation navigation) {
		this.navigation = navigation;
	}
	@Override
	public String toString() {
		ReflectionToStringBuilder builder = new ReflectionToStringBuilder(this);
		return builder.toString();
	}
	
	
}
