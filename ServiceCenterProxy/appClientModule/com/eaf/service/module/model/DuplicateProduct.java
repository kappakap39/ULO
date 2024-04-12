package com.eaf.service.module.model;

import java.io.Serializable;

import com.fasterxml.jackson.annotation.JsonProperty;

@SuppressWarnings("serial")
public class DuplicateProduct implements Serializable, Cloneable
{
	@JsonProperty("product") 
	private String product;
	
	@JsonProperty("remark")
	private String remark;
	
	public DuplicateProduct()
	{
	}
	
	public DuplicateProduct(String product, String remark)
	{
		this.product = product;
		this.remark = remark;
	}

	public String getProduct() {
		return product;
	}

	public void setProduct(String product) {
		this.product = product;
	}

	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}
}
