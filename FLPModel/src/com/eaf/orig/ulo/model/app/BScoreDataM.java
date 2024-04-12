package com.eaf.orig.ulo.model.app;

import java.io.Serializable;
import java.sql.Date;
import java.sql.Timestamp;

public class BScoreDataM implements Serializable,Cloneable{
	public BScoreDataM(){
		super();
	}
	private String bscoreId;	//ORIG_BSCORE.BSCORE_ID(VARCHAR2)
	private String personalId;	//ORIG_BSCORE.PERSONAL_ID(VARCHAR2)
	private String lpmId;	//ORIG_BSCORE.LPM_ID(VARCHAR2)
	private Date postDate;	//ORIG_BSCORE.POST_DATE(DATE)
	private String productType;	//ORIG_BSCORE.PRODUCT_TYPE(VARCHAR2)
	private String riskGrade; //ORIG_BSCORE.RISK_GRADE(VARCHAR2)
	private String createBy;  //ORIG_BSCORE.CREATE_BY(VARCHAR2)
	private String updateBy;  //ORIG_BSCORE.UPDATE_BY(VARCHAR2)
	private Timestamp createDate;	//ORIG_NCB_DOCUMENT.CREATE_DATE(DATE)
	private Timestamp updateDate;	//ORIG_NCB_DOCUMENT.UPDATE_DATE(DATE)
	
	public String getBscoreId() {
		return bscoreId;
	}
	public void setBscoreId(String bscoreId) {
		this.bscoreId = bscoreId;
	}
	public String getPersonalId() {
		return personalId;
	}
	public void setPersonalId(String personalId) {
		this.personalId = personalId;
	}
	public String getLpmId() {
		return lpmId;
	}
	public void setLpmId(String lpmId) {
		this.lpmId = lpmId;
	}
	public Date getPostDate() {
		return postDate;
	}
	public void setPostDate(Date postDate) {
		this.postDate = postDate;
	}
	public String getProductType() {
		return productType;
	}
	public void setProductType(String productType) {
		this.productType = productType;
	}
	public String getRiskGrade() {
		return riskGrade;
	}
	public void setRiskGrade(String riskGrade) {
		this.riskGrade = riskGrade;
	}
	public String getCreateBy() {
		return createBy;
	}
	public void setCreateBy(String createBy) {
		this.createBy = createBy;
	}
	public String getUpdateBy() {
		return updateBy;
	}
	public void setUpdateBy(String updateBy) {
		this.updateBy = updateBy;
	}
	public Timestamp getCreateDate() {
		return createDate;
	}
	public void setCreateDate(Timestamp createDate) {
		this.createDate = createDate;
	}
	public Timestamp getUpdateDate() {
		return updateDate;
	}
	public void setUpdateDate(Timestamp updateDate) {
		this.updateDate = updateDate;
	}
	@Override
	public String toString() {
		return "BScoreDataM [bscoreId=" + bscoreId + ", personalId="
				+ personalId + ", lpmId=" + lpmId + ", postDate=" + postDate
				+ ", productType=" + productType + ", riskGrade=" + riskGrade
				+ "]";
	}
	
}
