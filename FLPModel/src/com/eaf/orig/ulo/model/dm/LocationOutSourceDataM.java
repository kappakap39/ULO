package com.eaf.orig.ulo.model.dm;

import java.io.Serializable;
import java.sql.Timestamp;

public class LocationOutSourceDataM implements Serializable,Cloneable{
	public LocationOutSourceDataM(){
		super();
	}
	private String dmId;	//DM_LOCATION_OUTSOURCE.DM_ID(VARCHAR2)
	private String shelfNo;	//DM_LOCATION_OUTSOURCE.SHELF_NO(VARCHAR2)
	private String outsourceId;	//DM_LOCATION_OUTSOURCE.OUTSOURCE_ID(VARCHAR2)
	private String stackNo;	//DM_LOCATION_OUTSOURCE.STACK_NO(VARCHAR2)
	private String aisleNo;	//DM_LOCATION_OUTSOURCE.AISLE_NO(VARCHAR2)
	private String buildingNo;	//DM_LOCATION_OUTSOURCE.BUILDING_NO(VARCHAR2)
	private String createBy;	//DM_LOCATION_OUTSOURCE.CREATE_BY(VARCHAR2)
	private Timestamp createDate;	//DM_LOCATION_OUTSOURCE.CREATE_DATE(DATE)
	private String updateBy;	//DM_LOCATION_OUTSOURCE.UPDATE_BY(VARCHAR2)
	private Timestamp updateDate;	//DM_LOCATION_OUTSOURCE.UPDATE_DATE(DATE)
}
