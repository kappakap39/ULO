package com.eaf.orig.ulo.model.app;

import java.math.BigDecimal;
import java.sql.Timestamp;

public class KVIIncomeDataM extends IncomeCategoryDataM {
	public KVIIncomeDataM(){
		super();
	}
	private String kviId;	//INC_KVI.KVI_ID(VARCHAR2)
	private BigDecimal verifiedIncome;	//INC_KVI.VERIFIED_INCOME(NUMBER)
	private BigDecimal percentChequeReturn;	//INC_KVI.PERCENT_CHEQUE_RETURN(NUMBER)
	private String createBy;	//INC_KVI.CREATE_BY(VARCHAR2)
	private Timestamp createDate;	//INC_KVI.CREATE_DATE(DATE)
	private String updateBy;	//INC_KVI.UPDATE_BY(VARCHAR2)
	private Timestamp updateDate;	//INC_KVI.UPDATE_DATE(DATE)
	private String kviFid; //INC_KVI.KVI_FID(VARCHAR2)
	private String tokenId;
		
	public String getKviFid() {
		return kviFid;
	}
	public void setKviFid(String kviFid) {
		this.kviFid = kviFid;
	}
	public String getKviId() {
		return kviId;
	}
	public void setKviId(String kviId) {
		this.kviId = kviId;
	}
	public BigDecimal getVerifiedIncome() {
		return verifiedIncome;
	}
	public void setVerifiedIncome(BigDecimal verifiedIncome) {
		this.verifiedIncome = verifiedIncome;
	}
	public BigDecimal getPercentChequeReturn() {
		return percentChequeReturn;
	}
	public void setPercentChequeReturn(BigDecimal percentChequeReturn) {
		this.percentChequeReturn = percentChequeReturn;
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
	public String getTokenId() {
		return tokenId;
	}
	public void setTokenId(String tokenId) {
		this.tokenId = tokenId;
	}
	@Override
	public String getId() {
		return getKviId();
	}	
}
