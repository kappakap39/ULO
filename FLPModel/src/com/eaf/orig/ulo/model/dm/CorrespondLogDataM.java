package com.eaf.orig.ulo.model.dm;

import java.io.Serializable;
import java.sql.Date;
import java.sql.Timestamp;

public class CorrespondLogDataM implements Serializable,Cloneable {
	public CorrespondLogDataM(){
		super();
	}
	private int contactLogId;	//DM_CORRESPOND_LOG.CONTACT_LOG_ID(NUMBER)
	private String dmId;	//DM_CORRESPOND_LOG.DM_ID(VARCHAR2)
	private String contactType;	//DM_CORRESPOND_LOG.CONTACT_TYPE(VARCHAR2)
	private String templateName;	//DM_CORRESPOND_LOG.TEMPLATE_NAME(VARCHAR2)
	private String message;	//DM_CORRESPOND_LOG.MESSAGE(CLOB)
	private String subject;	//DM_CORRESPOND_LOG.SUBJECT(CLOB)
	private Date sendDate;	//DM_CORRESPOND_LOG.SEND_DATE(DATE)
	private String sendStatus;	//DM_CORRESPOND_LOG.SEND_STATUS(VARCHAR2)
	private String sendBy;	//DM_CORRESPOND_LOG.SEND_BY(VARCHAR2)
	private String sendTo;	//DM_CORRESPOND_LOG.SEND_TO(VARCHAR2)
	private String ccTo;	//DM_CORRESPOND_LOG.CC_TO(VARCHAR2)
	private String createBy;	//DM_CORRESPOND_LOG.CREATE_BY(VARCHAR2)
	private Timestamp createDate;	//DM_CORRESPOND_LOG.CREATE_DATE(DATE)
}
