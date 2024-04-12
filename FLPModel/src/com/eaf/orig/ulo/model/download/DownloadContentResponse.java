package com.eaf.orig.ulo.model.download;

import java.io.Serializable;
import java.sql.Date;
import java.util.List;

import com.eaf.orig.ulo.model.pega.ErrorDataM;

public class DownloadContentResponse implements Serializable, Cloneable{
	private String FuncNm;
	private String RqUID;
	private String RsAppId;
	private String RsUID;
	private Date RsDt;
	private String StatusCode;
	private List<ErrorDataM> Error;
	private String CorrID;
	private byte[] ContentFile;
	
	public String getFuncNm() {
		return FuncNm;
	}
	public void setFuncNm(String funcNm) {
		FuncNm = funcNm;
	}
	public String getRqUID() {
		return RqUID;
	}
	public void setRqUID(String rqUID) {
		RqUID = rqUID;
	}
	public String getRsAppId() {
		return RsAppId;
	}
	public void setRsAppId(String rsAppId) {
		RsAppId = rsAppId;
	}
	public String getRsUID() {
		return RsUID;
	}
	public void setRsUID(String rsUID) {
		RsUID = rsUID;
	}
	public Date getRsDt() {
		return RsDt;
	}
	public void setRsDt(Date rsDt) {
		RsDt = rsDt;
	}
	public String getStatusCode() {
		return StatusCode;
	}
	public void setStatusCode(String statusCode) {
		StatusCode = statusCode;
	}
	public List<ErrorDataM> getError() {
		return Error;
	}
	public void setError(List<ErrorDataM> error) {
		Error = error;
	}
	public String getCorrID() {
		return CorrID;
	}
	public void setCorrID(String corrID) {
		CorrID = corrID;
	}
	public byte[] getContentFile() {
		return ContentFile;
	}
	public void setContentFile(byte[] contentFile) {
		ContentFile = contentFile;
	}

}
