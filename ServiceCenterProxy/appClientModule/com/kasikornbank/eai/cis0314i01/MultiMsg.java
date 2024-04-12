/**
 * MultiMsg.java
 *
 * This file was auto-generated from WSDL
 * by the IBM Web services WSDL2Java emitter.
 * gm1318.02 v5913144247
 */

package com.kasikornbank.eai.cis0314i01;

public class MultiMsg  {
    private java.lang.Integer status;
    private java.lang.Integer reasonCode;
    private java.lang.String reasonDesc;
    private com.kasikornbank.eai.cis0314i01.RefField[] refFieldVect;

    public MultiMsg() {
    }

    public java.lang.Integer getStatus() {
        return status;
    }

    public void setStatus(java.lang.Integer status) {
        this.status = status;
    }

    public java.lang.Integer getReasonCode() {
        return reasonCode;
    }

    public void setReasonCode(java.lang.Integer reasonCode) {
        this.reasonCode = reasonCode;
    }

    public java.lang.String getReasonDesc() {
        return reasonDesc;
    }

    public void setReasonDesc(java.lang.String reasonDesc) {
        this.reasonDesc = reasonDesc;
    }

    public com.kasikornbank.eai.cis0314i01.RefField[] getRefFieldVect() {
        return refFieldVect;
    }

    public void setRefFieldVect(com.kasikornbank.eai.cis0314i01.RefField[] refFieldVect) {
        this.refFieldVect = refFieldVect;
    }

}
