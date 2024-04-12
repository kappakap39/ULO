package com.ava.dynamic.model.grid;

import java.io.Serializable;
import java.util.List;

public class MenuItem implements Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = -5850409342830418847L;
	private Long id;
	private Long menuId;
	private Long parentId;
	private Integer seq;
	private String link;
	private String absoluteLink;
	private String title;
	private String icon;
	private Boolean active;
	private List<MenuItem> subMenu;
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public Long getMenuId() {
		return menuId;
	}
	public void setMenuId(Long menuId) {
		this.menuId = menuId;
	}
	
	public Long getParentId() {
		return parentId;
	}
	public void setParentId(Long parentId) {
		this.parentId = parentId;
	}
	public Integer getSeq() {
		return seq;
	}
	public void setSeq(Integer seq) {
		this.seq = seq;
	}
	public String getLink() {
		return link;
	}
	public void setLink(String link) {
		this.link = link;
	}
	public String getAbsoluteLink() {
		return absoluteLink;
	}
	public void setAbsoluteLink(String absoluteLink) {
		this.absoluteLink = absoluteLink;
	}
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	public String getIcon() {
		return icon;
	}
	public void setIcon(String icon) {
		this.icon = icon;
	}
	public Boolean getActive() {
		return active;
	}
	public void setActive(Boolean active) {
		this.active = active;
	}
	public List<MenuItem> getSubMenu() {
		return subMenu;
	}
	public void setSubMenu(List<MenuItem> subMenu) {
		this.subMenu = subMenu;
	}
	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("MenuItem [id=");
		builder.append(id);
		builder.append(", menuId=");
		builder.append(menuId);
		builder.append(", parentId=");
		builder.append(parentId);
		builder.append(", seq=");
		builder.append(seq);
		builder.append(", link=");
		builder.append(link);
		builder.append(", absoluteLink=");
		builder.append(absoluteLink);
		builder.append(", title=");
		builder.append(title);
		builder.append(", icon=");
		builder.append(icon);
		builder.append(", active=");
		builder.append(active);
		builder.append(", subMenu=");
		builder.append(subMenu);
		builder.append("]");
		return builder.toString();
	}
	
	
}
