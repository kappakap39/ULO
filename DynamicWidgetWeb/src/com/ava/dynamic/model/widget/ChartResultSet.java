package com.ava.dynamic.model.widget;

public class ChartResultSet {
	private String category;
	private String seriesName;
	private String stack;
	private Double value;
	private Double value2;
	private Double value3;
	private Double value4;
	private String dataTypeId;
	
	public String getCategory() {
		return category;
	}
	public void setCategory(String category) {
		this.category = category;
	}
	public String getSeriesName() {
		return seriesName;
	}
	public void setSeriesName(String seriesName) {
		this.seriesName = seriesName;
	}
	public double getValue() {
		return value;
	}
	public void setValue(double value) {
		this.value = value;
	}
	public double getValue2() {
		return value2;
	}
	public void setValue2(double value2) {
		this.value2 = value2;
	}
	public double getValue3() {
		return value3;
	}
	public void setValue3(double value3) {
		this.value3 = value3;
	}
	public double getValue4() {
		return value4;
	}
	public void setValue4(double value4) {
		this.value4 = value4;
	}
	public String getDataTypeId() {
		return dataTypeId;
	}
	public void setDataTypeId(String dataTypeId) {
		this.dataTypeId = dataTypeId;
	}
	public String getStack() {
		return stack;
	}
	public void setStack(String stack) {
		this.stack = stack;
	}
	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("ChartResultSet [category=");
		builder.append(category);
		builder.append(", seriesName=");
		builder.append(seriesName);
		builder.append(", stack=");
		builder.append(stack);
		builder.append(", value=");
		builder.append(value);
		builder.append(", value2=");
		builder.append(value2);
		builder.append(", value3=");
		builder.append(value3);
		builder.append(", value4=");
		builder.append(value4);
		builder.append(", dataTypeId=");
		builder.append(dataTypeId);
		builder.append("]");
		return builder.toString();
	}
	
}
