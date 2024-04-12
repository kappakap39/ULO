package com.ava.dynamic.model;

import java.io.Serializable;

import org.apache.commons.lang3.builder.ReflectionToStringBuilder;


/**Simple JavaBean domain object with an id property. Used as a base class for objects needing this property.
 * 
 * @author TOP
 *
 */
public class Widget implements Comparable<Integer>, Serializable{ 
	/**
	 * 
	 */
	private static final long serialVersionUID = 1482506643361180422L;
	protected Long id;
	protected Long gridItemId;
	protected Long typeId;
    protected String typeCode;
    protected String typeName;
    protected String typeDesc;
    protected String typeClass;
    protected String viewPath;
    protected String fragment;
    protected String title;
    protected String yAxisTitle;
    protected Integer yAxisMin;
    protected Integer yAxisMax;
    protected String dataSuffix;
    protected String toolTipSuffix;
    protected Integer width;
    protected Integer height;
    protected String cssClass;
    protected String customStyle;
    protected Integer seq;
    protected String dhbType;
    protected Integer dynamicSeq;
    protected Integer intervalTime;
    
    //For data criteria purpose
    protected String owner;
    protected String positionLevel;
    protected String teamId;
    protected String dashboardGroup;
    
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public String getTypeCode() {
		return typeCode;
	}
	public void setTypeCode(String typeCode) {
		this.typeCode = typeCode;
	}
	public String getTypeName() {
		return typeName;
	}
	public void setTypeName(String typeName) {
		this.typeName = typeName;
	}
	public String getTypeDesc() {
		return typeDesc;
	}
	public void setTypeDesc(String typeDesc) {
		this.typeDesc = typeDesc;
	}
	public String getTypeClass() {
		return typeClass;
	}
	public void setTypeClass(String typeClass) {
		this.typeClass = typeClass;
	}
	public String getViewPath() {
		return viewPath;
	}
	public void setViewPath(String viewPath) {
		this.viewPath = viewPath;
	}
	public String getFragment() {
		return fragment;
	}
	public void setFragment(String fragment) {
		this.fragment = fragment;
	}
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	public String getyAxisTitle() {
		return yAxisTitle;
	}
	public void setyAxisTitle(String yAxisTitle) {
		this.yAxisTitle = yAxisTitle;
	}
	public Integer getyAxisMin() {
		return yAxisMin;
	}
	public void setyAxisMin(Integer yAxisMin) {
		this.yAxisMin = yAxisMin;
	}
	public Integer getyAxisMax() {
		return yAxisMax;
	}
	public void setyAxisMax(Integer yAxisMax) {
		this.yAxisMax = yAxisMax;
	}
	public String getDataSuffix() {
		return dataSuffix;
	}
	public void setDataSuffix(String dataSuffix) {
		this.dataSuffix = dataSuffix;
	}
	public String getToolTipSuffix() {
		return toolTipSuffix;
	}
	public void setToolTipSuffix(String toolTipSuffix) {
		this.toolTipSuffix = toolTipSuffix;
	}
	public Integer getWidth() {
		return width;
	}
	public void setWidth(Integer width) {
		this.width = width;
	}
	public Integer getHeight() {
		return height;
	}
	public void setHeight(Integer height) {
		this.height = height;
	}
	public String getCssClass() {
		return cssClass;
	}
	public void setCssClass(String cssClass) {
		this.cssClass = cssClass;
	}
	public String getCustomStyle() {
		return customStyle;
	}
	public void setCustomStyle(String customStyle) {
		this.customStyle = customStyle;
	}
	public Integer getSeq() {
		return seq;
	}
	public void setSeq(Integer seq) {
		this.seq = seq;
	}
	@Override
	public int compareTo(Integer o) {
		if(o != null && seq != null){
			return seq - 0;
		}
		return 0;
	}
	public String getDhbType() {
		return dhbType;
	}
	public void setDhbType(String dhbType) {
		this.dhbType = dhbType;
	}
	public Long getGridItemId() {
		return gridItemId;
	}
	public void setGridItemId(Long gridItemId) {
		this.gridItemId = gridItemId;
	}	
	public Long getTypeId() {
		return typeId;
	}
	public void setTypeId(Long typeId) {
		this.typeId = typeId;
	}
	public Integer getDynamicSeq() {
		return dynamicSeq;
	}
	public void setDynamicSeq(Integer dynamicSeq) {
		this.dynamicSeq = dynamicSeq;
	}
	public String getOwner() {
		return owner;
	}
	public void setOwner(String owner) {
		this.owner = owner;
	}
	public Integer getIntervalTime() {
		return intervalTime;
	}
	public void setIntervalTime(Integer intervalTime) {
		this.intervalTime = intervalTime;
	}
	public String getPositionLevel() {
		return positionLevel;
	}
	public void setPositionLevel(String positionLevel) {
		this.positionLevel = positionLevel;
	}
	public String getTeamId() {
		return teamId;
	}
	public void setTeamId(String teamId) {
		this.teamId = teamId;
	}
	public String getDashboardGroup() {
		return dashboardGroup;
	}
	public void setDashboardGroup(String dashboardGroup) {
		this.dashboardGroup = dashboardGroup;
	}
	@Override
	public String toString() {
		ReflectionToStringBuilder builder = new ReflectionToStringBuilder(this);
		return builder.toString();
	}

}
