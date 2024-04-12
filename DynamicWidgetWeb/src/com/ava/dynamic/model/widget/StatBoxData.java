package com.ava.dynamic.model.widget;

public class StatBoxData {
	private Double value1;
	private Double value2;
	private Double value3;
	private Double value4;
	private String firstBoxUnit;
	private String firstBoxUnitDesc;
	private String secondBoxUnit;
	private String secondBoxUnitDesc;
	public Double getValue1() {
		return value1;
	}
	public void setValue1(Double value1) {
		this.value1 = value1;
	}
	public Double getValue2() {
		return value2;
	}
	public void setValue2(Double value2) {
		this.value2 = value2;
	}
	public Double getValue3() {
		return value3;
	}
	public void setValue3(Double value3) {
		this.value3 = value3;
	}
	public Double getValue4() {
		return value4;
	}
	public void setValue4(Double value4) {
		this.value4 = value4;
	}
	public String getFirstBoxUnit() {
		return firstBoxUnit;
	}
	public void setFirstBoxUnit(String firstBoxUnit) {
		this.firstBoxUnit = firstBoxUnit;
	}
	public String getFirstBoxUnitDesc() {
		return firstBoxUnitDesc;
	}
	public void setFirstBoxUnitDesc(String firstBoxUnitDesc) {
		this.firstBoxUnitDesc = firstBoxUnitDesc;
	}
	public String getSecondBoxUnit() {
		return secondBoxUnit;
	}
	public void setSecondBoxUnit(String secondBoxUnit) {
		this.secondBoxUnit = secondBoxUnit;
	}
	public String getSecondBoxUnitDesc() {
		return secondBoxUnitDesc;
	}
	public void setSecondBoxUnitDesc(String secondBoxUnitDesc) {
		this.secondBoxUnitDesc = secondBoxUnitDesc;
	}
	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("StatBoxData [value1=");
		builder.append(value1);
		builder.append(", value2=");
		builder.append(value2);
		builder.append(", value3=");
		builder.append(value3);
		builder.append(", value4=");
		builder.append(value4);
		builder.append(", firstBoxUnit=");
		builder.append(firstBoxUnit);
		builder.append(", firstBoxUnitDesc=");
		builder.append(firstBoxUnitDesc);
		builder.append(", secondBoxUnit=");
		builder.append(secondBoxUnit);
		builder.append(", secondBoxUnitDesc=");
		builder.append(secondBoxUnitDesc);
		builder.append("]");
		return builder.toString();
	}
	
	
}
