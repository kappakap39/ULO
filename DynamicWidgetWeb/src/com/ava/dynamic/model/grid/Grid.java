package com.ava.dynamic.model.grid;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang3.builder.ReflectionToStringBuilder;

public class Grid implements Serializable{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 8991556197097384525L;
	private Long id;
	private String title;
	private String code;
	private String positionLevel;
	private Long childId;
	private Boolean displayMenu;
	private Boolean dynamicItems;
	private List<GridItem> items = new ArrayList<GridItem>();
	private Menu menu;
	private Boolean dirty;
	
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
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
	public List<GridItem> getItems() {
		return items;
	}
	public void setItems(List<GridItem> items) {
		this.items = items;
	}
	public Menu getMenu() {
		return menu;
	}
	public void setMenu(Menu menu) {
		this.menu = menu;
	}
	public Boolean getDisplayMenu() {
		return displayMenu;
	}
	public void setDisplayMenu(Boolean displayMenu) {
		this.displayMenu = displayMenu;
	}
	public Long getChildId() {
		return childId;
	}
	public void setChildId(Long childId) {
		this.childId = childId;
	}
	
	public Boolean getDirty() {
		return dirty;
	}
	public void setDirty(Boolean dirty) {
		this.dirty = dirty;
	}
	public Boolean getDynamicItems() {
		return dynamicItems;
	}
	public void setDynamicItems(Boolean dynamicItems) {
		this.dynamicItems = dynamicItems;
	}
	public void updateTitleByHolder(String...substitutes){
		if(title == null || substitutes == null || substitutes.length < 1)
			return;
		for(int i = 0, size = substitutes.length; i < size; i++){
			title = title.replace("{"+i+"}", substitutes[i]);
		}
		
	}
	@Override
	public String toString() {
		ReflectionToStringBuilder builder = new ReflectionToStringBuilder(this);
		return builder.toString();
	}
	
}
