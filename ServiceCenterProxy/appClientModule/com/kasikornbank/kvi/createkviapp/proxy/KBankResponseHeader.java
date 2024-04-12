/**
 * KBankResponseHeader.java
 *
 * This file was auto-generated from WSDL
 * by the IBM Web services WSDL2Java emitter.
 * gm1318.02 v5913144247
 */

package com.kasikornbank.kvi.createkviapp.proxy;

public class KBankResponseHeader  {
    private java.lang.String funcNm;
    private java.lang.String rqUID;
    private java.lang.String rsAppId;
    private java.lang.String rsDt;
    private java.lang.String rsUID;
    private java.lang.String statusCode;
    private com.kasikornbank.kvi.createkviapp.proxy.KBankResponseError[] error;
    private java.lang.String corrID;
    private java.lang.String filler;

    public KBankResponseHeader() {
    }

    public java.lang.String getFuncNm() {
        return funcNm;
    }

    public void setFuncNm(java.lang.String funcNm) {
        this.funcNm = funcNm;
    }

    public java.lang.String getRqUID() {
        return rqUID;
    }

    public void setRqUID(java.lang.String rqUID) {
        this.rqUID = rqUID;
    }

    public java.lang.String getRsAppId() {
        return rsAppId;
    }

    public void setRsAppId(java.lang.String rsAppId) {
        this.rsAppId = rsAppId;
    }

    public java.lang.String getRsDt() {
        return rsDt;
    }

    public void setRsDt(java.lang.String rsDt) {
        this.rsDt = rsDt;
    }

    public java.lang.String getRsUID() {
        return rsUID;
    }

    public void setRsUID(java.lang.String rsUID) {
        this.rsUID = rsUID;
    }

    public java.lang.String getStatusCode() {
        return statusCode;
    }

    public void setStatusCode(java.lang.String statusCode) {
        this.statusCode = statusCode;
    }

    public com.kasikornbank.kvi.createkviapp.proxy.KBankResponseError[] getError() {
        return error;
    }

    public void setError(com.kasikornbank.kvi.createkviapp.proxy.KBankResponseError[] error) {
        this.error = error;
    }

    public java.lang.String getCorrID() {
        return corrID;
    }

    public void setCorrID(java.lang.String corrID) {
        this.corrID = corrID;
    }

    public java.lang.String getFiller() {
        return filler;
    }

    public void setFiller(java.lang.String filler) {
        this.filler = filler;
    }

}
