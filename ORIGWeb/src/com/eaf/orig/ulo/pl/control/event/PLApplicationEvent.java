package com.eaf.orig.ulo.pl.control.event;

import java.io.Serializable;

import com.eaf.j2ee.pattern.control.event.Event;
import com.eaf.j2ee.pattern.control.event.EventHelper;
import com.eaf.orig.profile.model.UserDetailM;
import com.eaf.orig.ulo.pl.model.app.PLApplicationDataM;

public class PLApplicationEvent extends EventHelper implements Event, Serializable{

	private static final long serialVersionUID = 1L;
	
	public static final int DE_CREDATE_MANUAL = 100;
	public static final int DE_SAVE_DRAFT = 101;
	public static final int DE_SUBMIT = 102;
	public static final int DE_SET_PRIORITY = 103;
	public static final int DE_CONFIRM_REJECT = 104;
	public static final int DE_UNBLOCK = 105;
	public static final int DE_REASSIGN = 106;
	
	public static final int REWORK = 107;
	
	public static final int DC_SAVE_DRAFT = 201;
	public static final int DC_SUBMIT = 202;
	public static final int DC_SET_PRIORITY = 203;
	public static final int DC_CREATE_ICDC = 204;
	public static final int DC_CREATE_BUNDLING = 205;
	
	public static final int FU_SAVE_DRAFT = 301;
	public static final int FU_SUBMIT = 302;
 
	
	public static final int CA_SAVE_DRAFT = 501;
	public static final int CA_SUBMIT = 502;
	public static final int CA_SET_PRIORITY = 503;
	public static final int CA_SAVE_REOPEN_DRAFT = 504;
	
	public static final int SUBMIT_REOPEN = 1101;
	public static final int REOPEN_DUP = 1102;
	public static final int REOPEN_DUP_CANCEL = 1103;
	
	public static final int VC_SAVE_DRAFT = 601;
	public static final int VC_SUBMIT = 602;
	
	public static final int CB_PULL_JOB = 401;
	public static final int CB_SEND_WEB_BUREAU = 402;
	public static final int CB_SEND_BACK = 403;
	public static final int CB_REALLOCATE = 404;
	public static final int CB_VERIFY = 405;
	public static final int CB_SAVE = 406;
	
	public static final int DF_SAVE_DRAFT = 701;
	public static final int DF_SUBMIT = 702;
	
	public static final int RE_ISSUE_CARD_NO = 801;
	public static final int STATUS_ON_JOB = 802;
	public static final int SS_CANCEL = 803;
	public static final int RE_ISSUE_CUST_NO = 804;
	
	public static final int DEPLICATE_APPLICATION = 901;
	public static final int SEND_NCB = 902;
	
	public static final int CASHDAY5_SAVE = 1001;
	
	public static final int BLOCK_CANCEL = 1201;
	
	private UserDetailM userM;
	
	/**Event Constant*/	
	private PLApplicationDataM cloanPlApplicationM;
	
	@Override
	public String getEventName() {
		 return "PLApplicationEvent";
	}
	
	public PLApplicationEvent(){
	}
	
	public PLApplicationEvent(int eventType, Object object){
		this.setEventType(eventType);
		this.setObject(object);
	}
	
	public PLApplicationEvent(int eventType, Object object, UserDetailM userM){
		this.setEventType(eventType);
		this.setObject(object);
		this.setUserM(userM);
		this.setUserName((userM!=null)?userM.getUserName():"");
	}	
	public UserDetailM getUserM() {
	    return userM;
	}
	public void setUserM(UserDetailM userM) {
	    this.userM = userM;
	}
	public PLApplicationDataM getCloanPlApplicationM() {
		return cloanPlApplicationM;
	}
	public void setCloanPlApplicationM(PLApplicationDataM cloanPlApplicationM) {
		this.cloanPlApplicationM = cloanPlApplicationM;
	}		
}
