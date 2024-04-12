package com.eaf.orig.ulo.model.dm;

import java.io.Serializable;
import java.sql.Timestamp;

public class LocationBranchDataM implements Serializable,Cloneable {
	public LocationBranchDataM(){
		super();
	}
	private String dmId;	//DM_LOCATION_BRANCH.DM_ID(VARCHAR2)
	private String branchId;	//DM_LOCATION_BRANCH.BRANCH_ID(VARCHAR2)
	private String floorNo;	//DM_LOCATION_BRANCH.FLOOR_NO(VARCHAR2)
	private String createBy;	//DM_LOCATION_BRANCH.CREATE_BY(VARCHAR2)
	private Timestamp createDate;	//DM_LOCATION_BRANCH.CREATE_DATE(DATE)
	private String updateBy;	//DM_LOCATION_BRANCH.UPDATE_BY(VARCHAR2)
	private Timestamp updateDate;	//DM_LOCATION_BRANCH.UPDATE_DATE(DATE)
}
