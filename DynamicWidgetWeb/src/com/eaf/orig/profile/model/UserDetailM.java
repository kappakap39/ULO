package com.eaf.orig.profile.model;

import java.io.Serializable;
import java.sql.Timestamp;
import java.util.ArrayList;

public class UserDetailM implements Serializable,Cloneable{
	public UserDetailM(){
		super();
	}
	private String userName;
	private Timestamp lastLogonDate;
	private ArrayList<String> systems = new ArrayList<String>();
	private String positionId;
	private String teamId;
	private String gridId;	
	private String mode;
	private String displayDate;
	private ArrayList<String> authGrids = new ArrayList<String>();;
	public String getUserName() {
		return userName;
	}
	public void setUserName(String userName) {
		this.userName = userName;
	}	
	public Timestamp getLastLogonDate() {
		return lastLogonDate;
	}
	public void setLastLogonDate(Timestamp lastLogonDate) {
		this.lastLogonDate = lastLogonDate;
	}
	public ArrayList<String> getSystems() {
		return systems;
	}
	public void setSystems(ArrayList<String> systems) {
		this.systems = systems;
	}
	public boolean contains(String systemId){
		return systems.contains(systemId);
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
	public String getGridId() {
		return gridId;
	}
	public void setGridId(String gridId) {
		this.gridId = gridId;
	}
	public String getMode() {
		return mode;
	}
	public void setMode(String mode) {
		this.mode = mode;
	}
	public ArrayList<String> getAuthGrids() {
		return authGrids;
	}
	public void setAuthGrids(ArrayList<String> authGrids) {
		this.authGrids = authGrids;
	}
	public boolean auth(String gridId){
		return authGrids.contains(gridId);
	}
	public String getDisplayDate() {
		return displayDate;
	}
	public void setDisplayDate(String displayDate) {
		this.displayDate = displayDate;
	}
	@Override
	public String toString() {
		return "UserDetailM [userName=" + userName + ", lastLogonDate="
				+ lastLogonDate + ", systems=" + systems + ", positionId="
				+ positionId + ", teamId=" + teamId + ", gridId=" + gridId
				+ ", mode=" + mode + ", displayDate=" + displayDate + ", authGrids=" + authGrids + "]";
	}	
}
