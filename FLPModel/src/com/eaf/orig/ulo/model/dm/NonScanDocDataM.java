package com.eaf.orig.ulo.model.dm;

import java.io.Serializable;
import java.sql.Timestamp;

public class NonScanDocDataM implements Serializable,Cloneable{
	public NonScanDocDataM(){
		super();
	}
	private String dmId;	//DM_NON_SCAN_DOC.DM_ID(VARCHAR2)
	private String nonScanDocId;	//DM_NON_SCAN_DOC.NON_SCAN_DOC_ID(VARCHAR2)
	private String nonScanDocName;	//DM_NON_SCAN_DOC.NON_SCAN_DOC_NAME(VARCHAR2)
	private String nonScanDocDesc;	//DM_NON_SCAN_DOC.NON_SCAN_DOC_DESC(VARCHAR2)
	private String createBy;	//DM_NON_SCAN_DOC.CREATE_BY(VARCHAR2)
	private Timestamp createDate;	//DM_NON_SCAN_DOC.CREATE_DATE(DATE)
	private String updateBy;	//DM_NON_SCAN_DOC.UPDATE_BY(VARCHAR2)
	private Timestamp updateDate;	//DM_NON_SCAN_DOC.UPDATE_DATE(DATE)
}
