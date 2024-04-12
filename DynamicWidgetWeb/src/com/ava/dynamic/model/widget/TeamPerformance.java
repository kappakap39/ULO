package com.ava.dynamic.model.widget;

import java.util.List;

import com.ava.dynamic.model.Widget;

public class TeamPerformance extends Widget{
	/**
	 * 
	 */
	private static final long serialVersionUID = -93412382316596489L;
	private List<TeamPerformanceDetail> teamPerformanceDetailList;

	public List<TeamPerformanceDetail> getTeamPerformanceDetailList() {
		return teamPerformanceDetailList;
	}

	public void setTeamPerformanceDetailList(List<TeamPerformanceDetail> teamPerformanceDetailList) {
		this.teamPerformanceDetailList = teamPerformanceDetailList;
	}

	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("TeamPerformance [teamPerformanceDetailList=");
		builder.append(teamPerformanceDetailList);
		builder.append(", id=");
		builder.append(id);
		builder.append(", gridItemId=");
		builder.append(gridItemId);
		builder.append(", typeId=");
		builder.append(typeId);
		builder.append(", typeCode=");
		builder.append(typeCode);
		builder.append(", typeName=");
		builder.append(typeName);
		builder.append(", typeDesc=");
		builder.append(typeDesc);
		builder.append(", typeClass=");
		builder.append(typeClass);
		builder.append(", viewPath=");
		builder.append(viewPath);
		builder.append(", fragment=");
		builder.append(fragment);
		builder.append(", title=");
		builder.append(title);
		builder.append(", yAxisTitle=");
		builder.append(yAxisTitle);
		builder.append(", yAxisMin=");
		builder.append(yAxisMin);
		builder.append(", yAxisMax=");
		builder.append(yAxisMax);
		builder.append(", dataSuffix=");
		builder.append(dataSuffix);
		builder.append(", toolTipSuffix=");
		builder.append(toolTipSuffix);
		builder.append(", width=");
		builder.append(width);
		builder.append(", height=");
		builder.append(height);
		builder.append(", cssClass=");
		builder.append(cssClass);
		builder.append(", customStyle=");
		builder.append(customStyle);
		builder.append(", seq=");
		builder.append(seq);
		builder.append(", dhbType=");
		builder.append(dhbType);
		builder.append("]");
		return builder.toString();
	}

	
	
}
