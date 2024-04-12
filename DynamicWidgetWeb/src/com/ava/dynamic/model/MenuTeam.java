package com.ava.dynamic.model;

public class MenuTeam {
	private String teamId;
	private String teamName;
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
	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("MenuTeam [teamId=");
		builder.append(teamId);
		builder.append(", teamName=");
		builder.append(teamName);
		builder.append("]");
		return builder.toString();
	}
	
	
}
