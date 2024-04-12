package com.ava.dynamic.model.widget;

import java.util.ArrayList;
import java.util.List;

import com.ava.dynamic.model.Widget;

public class Lane {
	private Long id;
	private Long dashBoardId;
	private String name;
	private Integer seq;
	private List<Widget> widgetList = new ArrayList<Widget>();

	public List<Widget> getWidgetList() {
		return widgetList;
	}

	public void setWidgetList(List<Widget> widgetList) {
		this.widgetList = widgetList;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Integer getSeq() {
		return seq;
	}

	public void setSeq(Integer seq) {
		this.seq = seq;
	}

	public Long getDashBoardId() {
		return dashBoardId;
	}

	public void setDashBoardId(Long dashBoardId) {
		this.dashBoardId = dashBoardId;
	}

	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("Lane [id=");
		builder.append(id);
		builder.append(", dashBoardId=");
		builder.append(dashBoardId);
		builder.append(", name=");
		builder.append(name);
		builder.append(", seq=");
		builder.append(seq);
		builder.append(", widgetList=");
		builder.append(widgetList);
		builder.append("]");
		return builder.toString();
	}

}
