/**
 * DqField.java
 *
 * This file was auto-generated from WSDL
 * by the IBM Web services WSDL2Java emitter.
 * gm1318.02 v5913144247
 */

package com.kasikornbank.dq.ws.v1;

public class DqField  {
    private com.kasikornbank.dq.ws.v1.DqError[] errors;
    private int id;
    private java.lang.String name;

    public DqField() {
    }

    public com.kasikornbank.dq.ws.v1.DqError[] getErrors() {
        return errors;
    }

    public void setErrors(com.kasikornbank.dq.ws.v1.DqError[] errors) {
        this.errors = errors;
    }

    public com.kasikornbank.dq.ws.v1.DqError getErrors(int i) {
        return this.errors[i];
    }

    public void setErrors(int i, com.kasikornbank.dq.ws.v1.DqError value) {
        this.errors[i] = value;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public java.lang.String getName() {
        return name;
    }

    public void setName(java.lang.String name) {
        this.name = name;
    }

}
