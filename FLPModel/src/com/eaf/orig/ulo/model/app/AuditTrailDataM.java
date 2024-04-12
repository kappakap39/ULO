package com.eaf.orig.ulo.model.app;

import java.io.Serializable;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;

public class AuditTrailDataM implements Serializable,Cloneable,Comparator<AuditTrailDataM>{
	public AuditTrailDataM(){
		super();
	}
	private String applicationGroupId;	//ORIG_AUDIT_TRAIL.APPLICATION_GROUP_ID(VARCHAR2)
	private String applicationGroupNo;
	private String auditTrailId;	//ORIG_AUDIT_TRAIL.AUDIT_TRAIL_ID(VARCHAR2)
	private String fieldName;	//ORIG_AUDIT_TRAIL.FIELD_NAME(VARCHAR2)
	private String oldValue;	//ORIG_AUDIT_TRAIL.OLD_VALUE(VARCHAR2)
	private String newValue;	//ORIG_AUDIT_TRAIL.NEW_VALUE(VARCHAR2)
	private String role;	//ORIG_AUDIT_TRAIL.ROLE(VARCHAR2)
	private String firstValueFlag; //FIRST_VALUE_FLAG VARCHAR2(1)
	private String createRole;	//ORIG_AUDIT_TRAIL.CREATE_ROLE(VARCHAR2)
	private String createBy;	//ORIG_AUDIT_TRAIL.CREATE_BY(VARCHAR2)
	private Timestamp createDate;	//ORIG_AUDIT_TRAIL.CREATE_DATE(DATE)
	private String updateBy;	//ORIG_AUDIT_TRAIL.UPDATE_BY(VARCHAR2)
	private Timestamp updateDate;	//ORIG_AUDIT_TRAIL.UPDATE_DATE(DATE)
	
	public String getApplicationGroupId() {
		return applicationGroupId;
	}
	public void setApplicationGroupId(String applicationGroupId) {
		this.applicationGroupId = applicationGroupId;
	}
	public String getAuditTrailId() {
		return auditTrailId;
	}
	public void setAuditTrailId(String auditTrailId) {
		this.auditTrailId = auditTrailId;
	}
	public String getFieldName() {
		return fieldName;
	}
	public void setFieldName(String fieldName) {
		this.fieldName = fieldName;
	}
	public String getOldValue() {
		return oldValue;
	}
	public void setOldValue(String oldValue) {
		this.oldValue = oldValue;
	}
	public String getNewValue() {
		return newValue;
	}
	public void setNewValue(String newValue) {
		this.newValue = newValue;
	}
	public String getRole() {
		return role;
	}
	public void setRole(String role) {
		this.role = role;
	}
	public String getCreateRole() {
		return createRole;
	}
	public void setCreateRole(String createRole) {
		this.createRole = createRole;
	}
	public String getCreateBy() {
		return createBy;
	}
	public String getFirstValueFlag() {
		return firstValueFlag;
	}
	public void setFirstValueFlag(String firstValueFlag) {
		this.firstValueFlag = firstValueFlag;
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
	public String getApplicationGroupNo() {
		return applicationGroupNo;
	}
	public void setApplicationGroupNo(String applicationGroupNo) {
		this.applicationGroupNo = applicationGroupNo;
	}
	@Override
	public int compare(AuditTrailDataM o1, AuditTrailDataM o2) {
		int compareNum=0;
		 compareNum = compareDescUpdateDate(o2,o1);			   
        if (compareNum != 0) return compareNum;
        return compareNum;
	}	
	
	private  int compareDescUpdateDate(AuditTrailDataM obj1 ,AuditTrailDataM obj2){ 
		try{
			if(null != obj1 && null !=  obj2 ){
				AuditTrailDataM auditTrail1 = (AuditTrailDataM)obj1;
				AuditTrailDataM auditTrail2 = (AuditTrailDataM)obj2;
				String  fieldName1 = auditTrail1.getFieldName();
				if(null==fieldName1){fieldName1="";}
				String fieldName2 = auditTrail2.getFieldName();
				if(null==fieldName2){fieldName2=""; }
				int result = fieldName2.compareTo(fieldName2);
		        if (result != 0){
			          return result;
			    }
			    return auditTrail1.getUpdateDate().compareTo(auditTrail2.getUpdateDate());
			}
		}catch(Exception e){
			return 0;
		}
		return 0;
	}
	private static int compareAuditTrialId(AuditTrailDataM obj1 ,AuditTrailDataM obj2){ 
		try{
			if(null != obj1 && null !=  obj2 ){
				AuditTrailDataM auditTrail1 = (AuditTrailDataM)obj1;
				AuditTrailDataM auditTrail2 = (AuditTrailDataM)obj2;
				String  fieldName1 = auditTrail1.getFieldName();
				if(null==fieldName1){fieldName1="";}
				String fieldName2 = auditTrail2.getFieldName();
				if(null==fieldName2){fieldName2=""; }
				int result = fieldName2.compareTo(fieldName2);
		        if (result != 0){
			          return result;
			    }
			    return auditTrail1.getAuditTrailId().compareTo(auditTrail2.getAuditTrailId());
			}
		}catch(Exception e){
			return 0;
		}
		return 0;
	}
	public static AuditTrailDataM getCreateFirstAuditTrail(String fieldName, ArrayList<AuditTrailDataM> auditTrailList){ 
		for(AuditTrailDataM auditTrial: auditTrailList){
			if(fieldName.equals(auditTrial.getFieldName())){
				return auditTrial;
			}
		}
		return null;
		
			
	}
	public static void sortAuditTrialData(ArrayList<AuditTrailDataM> lastAuditTrailList) {
		Collections.sort(lastAuditTrailList,new Comparator<AuditTrailDataM>(){
			@Override
			public int compare(AuditTrailDataM o1,AuditTrailDataM o2) {
				return compareAuditTrialId(o1, o2);
			}
		});
	}
}
