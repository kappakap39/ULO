/**
 * OthetAddressList.java
 *
 * This file was auto-generated from WSDL
 * by the IBM Web services WSDL2Java emitter.
 * gm1318.02 v5913144247
 */

package com.kasikornbank.dq.ws.v1;

public class OthetAddressList  {
    private com.kasikornbank.dq.ws.v1.DqField[] fields;
    private int seqNo;

    public OthetAddressList() {
    }

    public com.kasikornbank.dq.ws.v1.DqField[] getFields() {
        return fields;
    }

    public void setFields(com.kasikornbank.dq.ws.v1.DqField[] fields) {
        this.fields = fields;
    }

    public com.kasikornbank.dq.ws.v1.DqField getFields(int i) {
        return this.fields[i];
    }

    public void setFields(int i, com.kasikornbank.dq.ws.v1.DqField value) {
        this.fields[i] = value;
    }

    public int getSeqNo() {
        return seqNo;
    }

    public void setSeqNo(int seqNo) {
        this.seqNo = seqNo;
    }

}
