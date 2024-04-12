package com.eaf.orig.ulo.model.dm;

import java.io.Serializable;
import java.sql.Timestamp;

public class LocationHubDataM implements Serializable,Cloneable {
	public LocationHubDataM(){
		super();
	}
	private String dmId;	//DM_LOCATION_HUB.DM_ID(VARCHAR2)
	private String shelfNo;	//DM_LOCATION_HUB.SHELF_NO(VARCHAR2)
	private String hubId;	//DM_LOCATION_HUB.HUB_ID(VARCHAR2)
	private String aisleNo;	//DM_LOCATION_HUB.AISLE_NO(VARCHAR2)
	private String stackNo;	//DM_LOCATION_HUB.STACK_NO(VARCHAR2)
	private String createBy;	//DM_LOCATION_HUB.CREATE_BY(VARCHAR2)
	private Timestamp createDate;	//DM_LOCATION_HUB.CREATE_DATE(DATE)
	private String updateBy;	//DM_LOCATION_HUB.UPDATE_BY(VARCHAR2)
	private Timestamp updateDate;	//DM_LOCATION_HUB.UPDATE_DATE(DATE)
}
