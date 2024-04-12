/**
 * __doServiceResponse_CIS0222I01Response_EAIHeader.java
 *
 * This file was auto-generated from WSDL
 * by the IBM Web services WSDL2Java emitter.
 * gm1318.02 v5913144247
 */

package com.kasikornbank.eai.cis0222i01;

public class __doServiceResponse_CIS0222I01Response_EAIHeader  {
    private java.lang.String serviceId;
    private java.util.Calendar beginTimestamp;
    private java.util.Calendar endTimestamp;
    private java.lang.String transactionId;
    private java.lang.String sourceTransactionId;
    private java.lang.String sourceSystem;
    private java.lang.String userId;
    private java.lang.String password;
    private java.lang.Integer status;
    private java.lang.Integer reasonCode;
    private java.lang.String reasonDesc;
    private java.lang.String multiMsgFlg;
    private java.lang.Integer orderMsgTypeCode;
    private com.kasikornbank.eai.cis0222i01.MultiMsg[] multiMsgVect;

    public __doServiceResponse_CIS0222I01Response_EAIHeader() {
    }

    public java.lang.String getServiceId() {
        return serviceId;
    }

    public void setServiceId(java.lang.String serviceId) {
        this.serviceId = serviceId;
    }

    public java.util.Calendar getBeginTimestamp() {
        return beginTimestamp;
    }

    public void setBeginTimestamp(java.util.Calendar beginTimestamp) {
        this.beginTimestamp = beginTimestamp;
    }

    public java.util.Calendar getEndTimestamp() {
        return endTimestamp;
    }

    public void setEndTimestamp(java.util.Calendar endTimestamp) {
        this.endTimestamp = endTimestamp;
    }

    public java.lang.String getTransactionId() {
        return transactionId;
    }

    public void setTransactionId(java.lang.String transactionId) {
        this.transactionId = transactionId;
    }

    public java.lang.String getSourceTransactionId() {
        return sourceTransactionId;
    }

    public void setSourceTransactionId(java.lang.String sourceTransactionId) {
        this.sourceTransactionId = sourceTransactionId;
    }

    public java.lang.String getSourceSystem() {
        return sourceSystem;
    }

    public void setSourceSystem(java.lang.String sourceSystem) {
        this.sourceSystem = sourceSystem;
    }

    public java.lang.String getUserId() {
        return userId;
    }

    public void setUserId(java.lang.String userId) {
        this.userId = userId;
    }

    public java.lang.String getPassword() {
        return password;
    }

    public void setPassword(java.lang.String password) {
        this.password = password;
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

    public java.lang.String getMultiMsgFlg() {
        return multiMsgFlg;
    }

    public void setMultiMsgFlg(java.lang.String multiMsgFlg) {
        this.multiMsgFlg = multiMsgFlg;
    }

    public java.lang.Integer getOrderMsgTypeCode() {
        return orderMsgTypeCode;
    }

    public void setOrderMsgTypeCode(java.lang.Integer orderMsgTypeCode) {
        this.orderMsgTypeCode = orderMsgTypeCode;
    }

    public com.kasikornbank.eai.cis0222i01.MultiMsg[] getMultiMsgVect() {
        return multiMsgVect;
    }

    public void setMultiMsgVect(com.kasikornbank.eai.cis0222i01.MultiMsg[] multiMsgVect) {
        this.multiMsgVect = multiMsgVect;
    }

}
