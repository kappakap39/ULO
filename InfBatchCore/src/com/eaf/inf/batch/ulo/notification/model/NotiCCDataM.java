package com.eaf.inf.batch.ulo.notification.model;

import java.io.Serializable;

public class NotiCCDataM implements Serializable,Cloneable {
	public NotiCCDataM(){
		super();
	}
	
	private int ccRoundFrom;//CC_ROUND_FROM
	private int ccRoundTo;//CC_ROUND_TO
	private String ccSendTo;//CC_SEND_TO
	private String channelGroupCode;
	private String ccJobCode;//CC_JOB_CODE
	private String ccFixEmail;//CC_FIX_EMAIL
	public int getCcRoundFrom() {
		return ccRoundFrom;
	}
	public void setCcRoundFrom(int ccRoundFrom) {
		this.ccRoundFrom = ccRoundFrom;
	}
	public int getCcRoundTo() {
		return ccRoundTo;
	}
	public void setCcRoundTo(int ccRoundTo) {
		this.ccRoundTo = ccRoundTo;
	}
	public String getCcSendTo() {
		return ccSendTo;
	}
	public void setCcSendTo(String ccSendTo) {
		this.ccSendTo = ccSendTo;
	}
	public String getChannelGroupCode() {
		return channelGroupCode;
	}
	public void setChannelGroupCode(String channelGroupCode) {
		this.channelGroupCode = channelGroupCode;
	}
	public String getCcJobCode() {
		return ccJobCode;
	}
	public void setCcJobCode(String ccJobCode) {
		this.ccJobCode = ccJobCode;
	}
	public String getCcFixEmail() {
		return ccFixEmail;
	}
	public void setCcFixEmail(String ccFixEmail) {
		this.ccFixEmail = ccFixEmail;
	}
	
	
}
