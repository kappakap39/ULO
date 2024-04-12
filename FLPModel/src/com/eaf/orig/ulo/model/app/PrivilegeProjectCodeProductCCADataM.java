package com.eaf.orig.ulo.model.app;

import java.io.Serializable;
import java.sql.Timestamp;

public class PrivilegeProjectCodeProductCCADataM implements Serializable,Cloneable{
	public PrivilegeProjectCodeProductCCADataM(){
		super();
	}
	private String productCcaId;	//XRULES_PRVLG_PRODUCT_CCA.PRODUCT_CCA_ID(VARCHAR2)
	private String prvlgPrjCdeId;	//XRULES_PRVLG_PRODUCT_CCA.PRVLG_PRJ_CDE_ID(VARCHAR2)
	private String ccaProduct;	//XRULES_PRVLG_PRODUCT_CCA.CCA_PRODUCT(VARCHAR2)
	private String result;	//XRULES_PRVLG_PRODUCT_CCA.RESULT(VARCHAR2)
	private String createBy;	//XRULES_PRVLG_PRODUCT_CCA.CREATE_BY(VARCHAR2)
	private Timestamp createDate;	//XRULES_PRVLG_PRODUCT_CCA.CREATE_DATE(DATE)
	private String updateBy;	//XRULES_PRVLG_PRODUCT_CCA.UPDATE_BY(VARCHAR2)
	private Timestamp updateDate;	//XRULES_PRVLG_PRODUCT_CCA.UPDATE_DATE(DATE)
	
	public String getProductCcaId() {
		return productCcaId;
	}
	public void setProductCcaId(String productCcaId) {
		this.productCcaId = productCcaId;
	}
	public String getPrvlgPrjCdeId() {
		return prvlgPrjCdeId;
	}
	public void setPrvlgPrjCdeId(String prvlgPrjCdeId) {
		this.prvlgPrjCdeId = prvlgPrjCdeId;
	}
	public String getCcaProduct() {
		return ccaProduct;
	}
	public void setCcaProduct(String ccaProduct) {
		this.ccaProduct = ccaProduct;
	}
	public String getResult() {
		return result;
	}
	public void setResult(String result) {
		this.result = result;
	}
	public String getCreateBy() {
		return createBy;
	}
	public void setCreateBy(String createBy) {
		this.createBy = createBy;
	}
	public Timestamp getCreateDate() {
		return createDate;
	}
	public void setCreateDate(Timestamp createDate) {
		this.createDate = createDate;
	}
	public String getUpdateBy() {
		return updateBy;
	}
	public void setUpdateBy(String updateBy) {
		this.updateBy = updateBy;
	}
	public Timestamp getUpdateDate() {
		return updateDate;
	}
	public void setUpdateDate(Timestamp updateDate) {
		this.updateDate = updateDate;
	}
}
