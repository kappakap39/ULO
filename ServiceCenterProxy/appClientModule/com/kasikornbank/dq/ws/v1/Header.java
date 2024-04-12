/**
 * Header.java
 *
 * This file was auto-generated from WSDL
 * by the IBM Web services WSDL2Java emitter.
 * gm1318.02 v5913144247
 */

package com.kasikornbank.dq.ws.v1;

public class Header  {
    private java.util.Calendar beginTimestamp;
    private java.util.Calendar endTimestamp;
    private java.lang.String serviceId;
    private java.lang.String sourceSystem;
    private int status;
    private java.lang.String transactionId;
    private java.lang.String sourceConfig;
    
    public Header() {
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

    public java.lang.String getServiceId() {
        return serviceId;
    }

    public void setServiceId(java.lang.String serviceId) {
        this.serviceId = serviceId;
    }

    public java.lang.String getSourceSystem() {
        return sourceSystem;
    }

    public void setSourceSystem(java.lang.String sourceSystem) {
        this.sourceSystem = sourceSystem;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public java.lang.String getTransactionId() {
        return transactionId;
    }

    public void setTransactionId(java.lang.String transactionId) {
        this.transactionId = transactionId;
    }

	public java.lang.String getSourceConfig() {
		return sourceConfig;
	}

	public void setSourceConfig(java.lang.String sourceConfig) {
		this.sourceConfig = sourceConfig;
	}
    
    

}
