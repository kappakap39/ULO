package com.ava.dynamic.model.widget;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import com.ava.dynamic.model.Widget;

public class Chart extends Widget implements Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = 8740791635590069823L;
	private List<String> categories = new ArrayList<String>();
	private List<Series> series = new ArrayList<Series>();
	public List<String> getCategories() {
		return categories;
	}
	public void setCategories(List<String> categories) {
		this.categories = categories;
	}
	public List<Series> getSeries() {
		return series;
	}
	public void setSeries(List<Series> series) {
		this.series = series;
	}
	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("Chart [categories=");
		builder.append(categories);
		builder.append(", series=");
		builder.append(series);
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
