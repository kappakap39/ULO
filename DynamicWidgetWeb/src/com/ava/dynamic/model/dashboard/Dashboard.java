package com.ava.dynamic.model.dashboard;

import java.util.ArrayList;
import java.util.List;

import com.ava.dynamic.model.widget.Lane;

public class Dashboard{
	private Long id;
	private String title;
	private String code;
	private String positionLevel;
	private List<Lane> lanes = new ArrayList<Lane>();
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public List<Lane> getLanes() {
		return lanes;
	}
	public void setLanes(List<Lane> lanes) {
		this.lanes = lanes;
	}
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	public String getCode() {
		return code;
	}
	public void setCode(String code) {
		this.code = code;
	}
	public String getPositionLevel() {
		return positionLevel;
	}
	public void setPositionLevel(String positionLevel) {
		this.positionLevel = positionLevel;
	}
	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("DashBoard [id=");
		builder.append(id);
		builder.append(", title=");
		builder.append(title);
		builder.append(", code=");
		builder.append(code);
		builder.append(", positionLevel=");
		builder.append(positionLevel);
		builder.append(", lanes=");
		builder.append(lanes);
		builder.append("]");
		return builder.toString();
	}
	
}
