package com.eaf.orig.ulo.model.membership;

import java.io.Serializable;
import java.math.BigDecimal;
import java.sql.Timestamp;
@SuppressWarnings("serial")
public class RunningParamStackDataM implements Serializable,Cloneable{
	private String paramId;
	private String paramType;
	private String paramDesc;
	private String paramValue;
	private String status;
	private String createBy;
	private Timestamp createDate;
	private String updateBy;
	private Timestamp updateDate;
	private String rowId;
	private BigDecimal remainingPercent;
	public String getParamId(){
		return paramId;
	}
	public void setParamId(String paramId){
		this.paramId = paramId;
	}
	public String getParamType(){
		return paramType;
	}
	public void setParamType(String paramType){
		this.paramType = paramType;
	}
	public String getParamDesc(){
		return paramDesc;
	}
	public void setParamDesc(String paramDesc){
		this.paramDesc = paramDesc;
	}
	public String getParamValue(){
		return paramValue;
	}
	public void setParamValue(String paramValue){
		this.paramValue = paramValue;
	}
	public String getStatus(){
		return status;
	}
	public void setStatus(String status){
		this.status = status;
	}
	public String getCreateBy(){
		return createBy;
	}
	public void setCreateBy(String createBy){
		this.createBy = createBy;
	}
	public Timestamp getCreateDate(){
		return createDate;
	}
	public void setCreateDate(Timestamp createDate){
		this.createDate = createDate;
	}
	public String getUpdateBy(){
		return updateBy;
	}
	public void setUpdateBy(String updateBy){
		this.updateBy = updateBy;
	}
	public Timestamp getUpdateDate(){
		return updateDate;
	}
	public void setUpdateDate(Timestamp updateDate){
		this.updateDate = updateDate;
	}
	public String getRowId(){
		return rowId;
	}
	public void setRowId(String rowId){
		this.rowId = rowId;
	}
	public BigDecimal getRemainingPercent(){
		return remainingPercent;
	}
	public void setRemainingPercent(BigDecimal remainingPercent){
		this.remainingPercent = remainingPercent;
	}
}
