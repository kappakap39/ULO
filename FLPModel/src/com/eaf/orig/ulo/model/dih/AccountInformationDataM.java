package com.eaf.orig.ulo.model.dih;

import java.io.Serializable;

public class AccountInformationDataM implements Serializable,Cloneable{
	public AccountInformationDataM(){
		super();
	}
	private String branchNo;
	private String accountNumber;
	private String accountName;
	private String status;
	
	public static enum AcctTableName {SAVING("72", "AR_SHR.VC_SVG_DEP_AR"), CURRENT("73", "AR_SHR.VC_CRN_DEP_AR"), 
		FIXED("74", "AR_SHR.VC_FIX_DEP_AR"), SFIXED("75","VC_TWD_DEP_AR");
		private String code;
		private String tableName;
		private AcctTableName(String code, String tableName) {
			this.code = code;
			this.tableName = tableName;
		}
		public String getCode() {
			return code;
		}
		public String getTableName() {
			return tableName;
		}
	}
	
	public String getAccountNumber() {
		return accountNumber;
	}
	public void setAccountNumber(String accountNumber) {
		this.accountNumber = accountNumber;
	}
	public String getAccountName() {
		return accountName;
	}
	public void setAccountName(String accountName) {
		this.accountName = accountName;
	}
	public String getBranchNo() {
		return branchNo;
	}
	public void setBranchNo(String branchNo) {
		this.branchNo = branchNo;
	}
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	
	public static String getAcctTableName(String srcStmCd) {
		if(srcStmCd == null || srcStmCd == "") {
			return null;
		}
		int code = Integer.parseInt(srcStmCd);
		switch(code) {
			case 72:
				return AcctTableName.SAVING.tableName;
			case 73:
				return AcctTableName.CURRENT.tableName;
			case 74:
				return AcctTableName.FIXED.tableName;
			case 75:
				return AcctTableName.SFIXED.tableName;
		}
		return null;
	}
}
