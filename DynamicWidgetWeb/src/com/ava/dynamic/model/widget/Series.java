package com.ava.dynamic.model.widget;

import java.util.ArrayList;
import java.util.List;

public class Series {
	private String name;
	private List<Double> data = new ArrayList<Double>();
	private String stack;
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public List<Double> getData() {
		return data;
	}
	public void setData(List<Double> data) {
		this.data = data;
	}
	public String getStack() {
		return stack;
	}
	public void setStack(String stack) {
		this.stack = stack;
	}
	
}
