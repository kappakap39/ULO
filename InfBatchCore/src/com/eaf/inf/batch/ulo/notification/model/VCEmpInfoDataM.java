package com.eaf.inf.batch.ulo.notification.model;

import java.io.Serializable;
import java.util.ArrayList;

public class VCEmpInfoDataM implements Serializable,Cloneable {
	private String empId;
	private String deptId; //DEPT_ID
	private String orgCode;//ORGCODE
	private String prnDeptId; //PRN_DEPT_ID
	private String subUnitCntrCd; //SUB_UNIT_CNTR_CD 
	private String unitCntrCd;//UNIT_CNTR_CD
	private String deptCntrCd; //DEPT_CNTR_CD
	private String jobCode;//JOB_CD
	private int corpTtlCd;//CORP_TTL_CD
	private String mobilePhone; //MBL_PH1,MBL_PH2
	private String email;
	private String suffixValue;
	private String bsnLineDeptId;//BSN_LINE_DEPT_ID
	private String ntwCntrCd;//NTW_CNTR_CD
	private String departMentName;//TH_DEPT_NM
	private String branchNo;//KBNK_BR_NO
	private String branchName;//VC_RC_CD.TH_CNTR_NM
	public String getEmpId() {
		return empId;
	}
	public void setEmpId(String empId) {
		this.empId = empId;
	}
	public String getDeptId() {
		return deptId;
	}
	public void setDeptId(String deptId) {
		this.deptId = deptId;
	}
	public String getOrgCode() {
		return orgCode;
	}
	public void setOrgCode(String orgCode) {
		this.orgCode = orgCode;
	}
	public String getSubUnitCntrCd() {
		return subUnitCntrCd;
	}
	public void setSubUnitCntrCd(String subUnitCntrCd) {
		this.subUnitCntrCd = subUnitCntrCd;
	}
	public String getUnitCntrCd() {
		return unitCntrCd;
	}
	public void setUnitCntrCd(String unitCntrCd) {
		this.unitCntrCd = unitCntrCd;
	}
	public String getDeptCntrCd() {
		return deptCntrCd;
	}
	public void setDeptCntrCd(String deptCntrCd) {
		this.deptCntrCd = deptCntrCd;
	}
	public String getJobCode() {
		return jobCode;
	}
	public void setJobCode(String jobCode) {
		this.jobCode = jobCode;
	}
	public int getCorpTtlCd() {
		return corpTtlCd;
	}
	public void setCorpTtlCd(int corpTtlCd) {
		this.corpTtlCd = corpTtlCd;
	}
	public String getMobilePhone() {
		return mobilePhone;
	}
	public void setMobilePhone(String mobilePhone) {
		this.mobilePhone = mobilePhone;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public String getSuffixValue() {
		return suffixValue;
	}
	public void setSuffixValue(String suffixValue) {
		this.suffixValue = suffixValue;
	}
	public String getPrnDeptId() {
		return prnDeptId;
	}
	public void setPrnDeptId(String prnDeptId) {
		this.prnDeptId = prnDeptId;
	}
	public String getBsnLineDeptId() {
		return bsnLineDeptId;
	}
	public void setBsnLineDeptId(String bsnLineDeptId) {
		this.bsnLineDeptId = bsnLineDeptId;
	}
	public String getNtwCntrCd() {
		return ntwCntrCd;
	}
	public void setNtwCntrCd(String ntwCntrCd) {
		this.ntwCntrCd = ntwCntrCd;
	}	
	public String getDepartMentName() {
		return departMentName;
	}
	public void setDepartMentName(String departMentName) {
		this.departMentName = departMentName;
	}
	public String getBranchNo() {
		return branchNo;
	}
	public void setBranchNo(String branchNo) {
		this.branchNo = branchNo;
	}
	public String getBranchName() {
		return branchName;
	}
	public void setBranchName(String branchName) {
		this.branchName = branchName;
	}
	public ArrayList<String> getManagerConditionList() {
		ArrayList<String> conditionList = new ArrayList<String>();
		if(null!=bsnLineDeptId && !"".equals(bsnLineDeptId)){
			conditionList.add(bsnLineDeptId);
		}
		if(null!=ntwCntrCd && !"".equals(ntwCntrCd)){
			conditionList.add(ntwCntrCd);
		}
		if(null!=deptCntrCd && !"".equals(deptCntrCd)){
			conditionList.add(deptCntrCd);
		}
		return conditionList;
	}
	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("VCEmpInfoDataM [");
		if (empId != null) {
			builder.append("empId=");
			builder.append(empId);
			builder.append(", ");
		}
		if (deptId != null) {
			builder.append("deptId=");
			builder.append(deptId);
			builder.append(", ");
		}
		if (orgCode != null) {
			builder.append("orgCode=");
			builder.append(orgCode);
			builder.append(", ");
		}
		if (prnDeptId != null) {
			builder.append("prnDeptId=");
			builder.append(prnDeptId);
			builder.append(", ");
		}
		if (subUnitCntrCd != null) {
			builder.append("subUnitCntrCd=");
			builder.append(subUnitCntrCd);
			builder.append(", ");
		}
		if (unitCntrCd != null) {
			builder.append("unitCntrCd=");
			builder.append(unitCntrCd);
			builder.append(", ");
		}
		if (deptCntrCd != null) {
			builder.append("deptCntrCd=");
			builder.append(deptCntrCd);
			builder.append(", ");
		}
		if (jobCode != null) {
			builder.append("jobCode=");
			builder.append(jobCode);
			builder.append(", ");
		}
		builder.append("corpTtlCd=");
		builder.append(corpTtlCd);
		builder.append(", ");
		if (mobilePhone != null) {
			builder.append("mobilePhone=");
			builder.append(mobilePhone);
			builder.append(", ");
		}
		if (email != null) {
			builder.append("email=");
			builder.append(email);
			builder.append(", ");
		}
		if (suffixValue != null) {
			builder.append("suffixValue=");
			builder.append(suffixValue);
			builder.append(", ");
		}
		if (bsnLineDeptId != null) {
			builder.append("bsnLineDeptId=");
			builder.append(bsnLineDeptId);
			builder.append(", ");
		}
		if (ntwCntrCd != null) {
			builder.append("ntwCntrCd=");
			builder.append(ntwCntrCd);
			builder.append(", ");
		}
		if (departMentName != null) {
			builder.append("departMentName=");
			builder.append(departMentName);
			builder.append(", ");
		}
		if (branchNo != null) {
			builder.append("branchNo=");
			builder.append(branchNo);
			builder.append(", ");
		}
		if (branchName != null) {
			builder.append("branchName=");
			builder.append(branchName);
		}
		builder.append("]");
		return builder.toString();
	}	
	
}
