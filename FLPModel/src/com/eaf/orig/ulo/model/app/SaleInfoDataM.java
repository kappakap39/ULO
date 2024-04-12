package com.eaf.orig.ulo.model.app;

import java.io.Serializable;
import java.sql.Timestamp;

public class SaleInfoDataM implements Serializable,Cloneable{
	public SaleInfoDataM(){
		super();
	}
//	
//	public class SaleType{
//		public static final String NORMAL_SALES ="01";
//		public static final String REFERENCE_SALES ="02";
//		public static final String CASH_DAY_ONE_SALES="03";
//		public static final String MTL_SALES="04";		 
//	}
	private String applicationGroupId;	//ORIG_SALE_INFO.APPLICATION_GROUP_ID(VARCHAR2)
	private String salesInfoId;	//ORIG_SALE_INFO.SALES_INFO_ID(VARCHAR2)
	private String salesComment;	//ORIG_SALE_INFO.SALES_COMMENT(VARCHAR2)
	private String salesName;	//ORIG_SALE_INFO.SALES_NAME(VARCHAR2)
	private String zone;	//ORIG_SALE_INFO.ZONE(VARCHAR2)
	private String region;	//ORIG_SALE_INFO.REGION(VARCHAR2)
	private String salesBranchCode;	//ORIG_SALE_INFO.SALES_BRANCH_CODE(VARCHAR2)
	private String salesBranchName;	//ORIG_SALE_INFO.SALES_BRANCH_NAME(VARCHAR2)
	private String salesPhoneNo;	//ORIG_SALE_INFO.SALES_PHONE_NO(VARCHAR2)
	private String salesId;	//ORIG_SALE_INFO.SALES_ID(VARCHAR2)
	private String salesType;	//ORIG_SALE_INFO.SALES_TYPE(VARCHAR2)
	private String salesDeptName;	//ORIG_SALE_INFO.SALES_DEPARTMENT_NAME(VARCHAR2)
	private String salesRCCode;	//ORIG_SALE_INFO.SALES_RC_CODE(VARCHAR2)
	private String saleChannel;	//ORIG_SALE_INFO.SALE_CHANNEL(VARCHAR2)
	private String createBy;	//ORIG_SALE_INFO.CREATE_BY(VARCHAR2)
	private Timestamp createDate;	//ORIG_SALE_INFO.CREATE_DATE(DATE)
	private String updateBy;	//ORIG_SALE_INFO.UPDATE_BY(VARCHAR2)
	private Timestamp updateDate;	//ORIG_SALE_INFO.UPDATE_DATE(DATE)
	
	public String getApplicationGroupId() {
		return applicationGroupId;
	}
	public void setApplicationGroupId(String applicationGroupId) {
		this.applicationGroupId = applicationGroupId;
	}
	public String getSalesInfoId() {
		return salesInfoId;
	}
	public void setSalesInfoId(String salesInfoId) {
		this.salesInfoId = salesInfoId;
	}
	public String getSalesComment() {
		return salesComment;
	}
	public void setSalesComment(String salesComment) {
		this.salesComment = salesComment;
	}
	public String getSalesName() {
		return salesName;
	}
	public void setSalesName(String salesName) {
		this.salesName = salesName;
	}
	public String getZone() {
		return zone;
	}
	public void setZone(String zone) {
		this.zone = zone;
	}
	public String getRegion() {
		return region;
	}
	public void setRegion(String region) {
		this.region = region;
	}
	public String getSalesBranchCode() {
		return salesBranchCode;
	}
	public void setSalesBranchCode(String salesBranchCode) {
		this.salesBranchCode = salesBranchCode;
	}
	public String getSalesPhoneNo() {
		return salesPhoneNo;
	}
	public void setSalesPhoneNo(String salesPhoneNo) {
		this.salesPhoneNo = salesPhoneNo;
	}
	public String getSalesId() {
		return salesId;
	}
	public void setSalesId(String salesId) {
		this.salesId = salesId;
	}
	public String getSalesType() {
		return salesType;
	}
	public void setSalesType(String salesType) {
		this.salesType = salesType;
	}
	public String getSalesDeptName() {
		return salesDeptName;
	}
	public void setSalesDeptName(String salesDeptName) {
		this.salesDeptName = salesDeptName;
	}
	public String getSalesRCCode() {
		return salesRCCode;
	}
	public void setSalesRCCode(String salesRCCode) {
		this.salesRCCode = salesRCCode;
	}
	public String getSaleChannel() {
		return saleChannel;
	}
	public void setSaleChannel(String saleChannel) {
		this.saleChannel = saleChannel;
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
	
	public String getSalesBranchName() {
		return salesBranchName;
	}
	public void setSalesBranchName(String salesBranchName) {
		this.salesBranchName = salesBranchName;
	}
	public void clear(){
		this.salesName = null;
		this.zone = null;
		this.region = null;
		this.salesBranchCode = null;
		this.salesBranchName = null;
		this.salesDeptName = null;
		this.saleChannel = null;
		this.salesRCCode = null;
		this.salesPhoneNo = null;
	}
	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("SaleInfoDataM [");
		if (applicationGroupId != null) {
			builder.append("applicationGroupId=");
			builder.append(applicationGroupId);
			builder.append(", ");
		}
		if (salesInfoId != null) {
			builder.append("salesInfoId=");
			builder.append(salesInfoId);
			builder.append(", ");
		}
		if (salesComment != null) {
			builder.append("salesComment=");
			builder.append(salesComment);
			builder.append(", ");
		}
		if (salesName != null) {
			builder.append("salesName=");
			builder.append(salesName);
			builder.append(", ");
		}
		if (zone != null) {
			builder.append("zone=");
			builder.append(zone);
			builder.append(", ");
		}
		if (region != null) {
			builder.append("region=");
			builder.append(region);
			builder.append(", ");
		}
		if (salesBranchCode != null) {
			builder.append("salesBranchCode=");
			builder.append(salesBranchCode);
			builder.append(", ");
		}
		if (salesPhoneNo != null) {
			builder.append("salesPhoneNo=");
			builder.append(salesPhoneNo);
			builder.append(", ");
		}
		if (salesId != null) {
			builder.append("salesId=");
			builder.append(salesId);
			builder.append(", ");
		}
		if (salesType != null) {
			builder.append("salesType=");
			builder.append(salesType);
			builder.append(", ");
		}
		if (salesDeptName != null) {
			builder.append("salesDeptName=");
			builder.append(salesDeptName);
			builder.append(", ");
		}
		if (salesRCCode != null) {
			builder.append("salesRCCode=");
			builder.append(salesRCCode);
			builder.append(", ");
		}
		if (saleChannel != null) {
			builder.append("saleChannel=");
			builder.append(saleChannel);
			builder.append(", ");
		}
		if (createBy != null) {
			builder.append("createBy=");
			builder.append(createBy);
			builder.append(", ");
		}
		if (createDate != null) {
			builder.append("createDate=");
			builder.append(createDate);
			builder.append(", ");
		}
		if (updateBy != null) {
			builder.append("updateBy=");
			builder.append(updateBy);
			builder.append(", ");
		}
		if (updateDate != null) {
			builder.append("updateDate=");
			builder.append(updateDate);
		}
		builder.append("]");
		return builder.toString();
	}
	
}
