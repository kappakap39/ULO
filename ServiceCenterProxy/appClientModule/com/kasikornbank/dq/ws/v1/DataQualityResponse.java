/**
 * DataQualityResponse.java
 *
 * This file was auto-generated from WSDL
 * by the IBM Web services WSDL2Java emitter.
 * gm1318.02 v5913144247
 */

package com.kasikornbank.dq.ws.v1;

public class DataQualityResponse  extends com.kasikornbank.dq.ws.v1.BaseResponse  {
    private com.kasikornbank.dq.ws.v1.ContactEmailList[] contactEmails;
    private com.kasikornbank.dq.ws.v1.ContactNumberList[] contactNumbers;
    private com.kasikornbank.dq.ws.v1.DqField[] fields;
    private com.kasikornbank.dq.ws.v1.OthetAddressList[] otherAddresses;

    public DataQualityResponse() {
    }

    public com.kasikornbank.dq.ws.v1.ContactEmailList[] getContactEmails() {
        return contactEmails;
    }

    public void setContactEmails(com.kasikornbank.dq.ws.v1.ContactEmailList[] contactEmails) {
        this.contactEmails = contactEmails;
    }

    public com.kasikornbank.dq.ws.v1.ContactEmailList getContactEmails(int i) {
        return this.contactEmails[i];
    }

    public void setContactEmails(int i, com.kasikornbank.dq.ws.v1.ContactEmailList value) {
        this.contactEmails[i] = value;
    }

    public com.kasikornbank.dq.ws.v1.ContactNumberList[] getContactNumbers() {
        return contactNumbers;
    }

    public void setContactNumbers(com.kasikornbank.dq.ws.v1.ContactNumberList[] contactNumbers) {
        this.contactNumbers = contactNumbers;
    }

    public com.kasikornbank.dq.ws.v1.ContactNumberList getContactNumbers(int i) {
        return this.contactNumbers[i];
    }

    public void setContactNumbers(int i, com.kasikornbank.dq.ws.v1.ContactNumberList value) {
        this.contactNumbers[i] = value;
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

    public com.kasikornbank.dq.ws.v1.OthetAddressList[] getOtherAddresses() {
        return otherAddresses;
    }

    public void setOtherAddresses(com.kasikornbank.dq.ws.v1.OthetAddressList[] otherAddresses) {
        this.otherAddresses = otherAddresses;
    }

    public com.kasikornbank.dq.ws.v1.OthetAddressList getOtherAddresses(int i) {
        return this.otherAddresses[i];
    }

    public void setOtherAddresses(int i, com.kasikornbank.dq.ws.v1.OthetAddressList value) {
        this.otherAddresses[i] = value;
    }

}
