package com.ava.dynamic.model.grid;

import java.io.Serializable;
import java.util.List;

public class Menu implements Serializable{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = -1316945530297766504L;
	private Long id;
	private Long gridId;
	private String title;
	private List<MenuItem> items;
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public Long getGridId() {
		return gridId;
	}
	public void setGridId(Long gridId) {
		this.gridId = gridId;
	}
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	public List<MenuItem> getItems() {
		return items;
	}
	public void setItems(List<MenuItem> items) {
		this.items = items;
	}
	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("Menu [id=");
		builder.append(id);
		builder.append(", gridId=");
		builder.append(gridId);
		builder.append(", title=");
		builder.append(title);
		builder.append(", items=");
		builder.append(items);
		builder.append("]");
		return builder.toString();
	}
	
	
	
}
