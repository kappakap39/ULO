package com.ava.dynamic.model.grid;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang3.builder.ReflectionToStringBuilder;

import com.ava.dynamic.model.Widget;

public class GridItem implements Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = 7033656894245837884L;
	private Long id;
	private int posX;
	private int posY;
	private int width;
	private int height;
	private Integer seq;
	private Long gridId;
	private String title;
	private String headerIcon;
	private String footer;
	private Integer minWidth;
	private Integer maxWidth;
	private Integer minHeight;
	private Integer maxHeight;
	private Boolean noResize;
	private Boolean noMove;
	private String widgetAlignment;
	
	private List<Widget> widgetList = new ArrayList<Widget>();
	
	//for dynamic items
	private String teamId;
	private String owner;
	
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public int getPosX() {
		return posX;
	}
	public void setPosX(int posX) {
		this.posX = posX;
	}
	public int getPosY() {
		return posY;
	}
	public void setPosY(int posY) {
		this.posY = posY;
	}
	public int getWidth() {
		return width;
	}
	public void setWidth(int width) {
		this.width = width;
	}
	public int getHeight() {
		return height;
	}
	public void setHeight(int height) {
		this.height = height;
	}
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	public List<Widget> getWidgetList() {
		return widgetList;
	}
	public void setWidgetList(List<Widget> widgetList) {
		this.widgetList = widgetList;
	}
	public Integer getSeq() {
		return seq;
	}
	public void setSeq(Integer seq) {
		this.seq = seq;
	}
	public Long getGridId() {
		return gridId;
	}
	public void setGridId(Long gridId) {
		this.gridId = gridId;
	}
	public String getHeaderIcon() {
		return headerIcon;
	}
	public void setHeaderIcon(String headerIcon) {
		this.headerIcon = headerIcon;
	}
	public String getFooter() {
		return footer;
	}
	public void setFooter(String footer) {
		this.footer = footer;
	}
	public Integer getMinWidth() {
		return minWidth;
	}
	public void setMinWidth(Integer minWidth) {
		this.minWidth = minWidth;
	}
	public Integer getMaxWidth() {
		return maxWidth;
	}
	public void setMaxWidth(Integer maxWidth) {
		this.maxWidth = maxWidth;
	}
	public Integer getMinHeight() {
		return minHeight;
	}
	public void setMinHeight(Integer minHeight) {
		this.minHeight = minHeight;
	}
	public Integer getMaxHeight() {
		return maxHeight;
	}
	public void setMaxHeight(Integer maxHeight) {
		this.maxHeight = maxHeight;
	}
	public Boolean getNoResize() {
		return noResize;
	}
	public void setNoResize(Boolean noResize) {
		this.noResize = noResize;
	}
	public Boolean getNoMove() {
		return noMove;
	}
	public void setNoMove(Boolean noMove) {
		this.noMove = noMove;
	}
	public String getWidgetAlignment() {
		return widgetAlignment;
	}
	public void setWidgetAlignment(String widgetAlignment) {
		this.widgetAlignment = widgetAlignment;
	}
	public String getTeamId() {
		return teamId;
	}
	public void setTeamId(String teamId) {
		this.teamId = teamId;
	}
	public String getOwner() {
		return owner;
	}
	public void setOwner(String owner) {
		this.owner = owner;
	}
	@Override
	public String toString() {
		ReflectionToStringBuilder builder = new ReflectionToStringBuilder(this);
		return builder.toString();
	}
}
