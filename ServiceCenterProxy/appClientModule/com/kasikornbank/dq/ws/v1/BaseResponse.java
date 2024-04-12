/**
 * BaseResponse.java
 *
 * This file was auto-generated from WSDL
 * by the IBM Web services WSDL2Java emitter.
 * gm1318.02 v5913144247
 */

package com.kasikornbank.dq.ws.v1;

public class BaseResponse  {
    private com.kasikornbank.dq.ws.v1.Header header;
    private com.kasikornbank.dq.ws.v1.ResponseStatus responseStatus;

    public BaseResponse() {
    }

    public com.kasikornbank.dq.ws.v1.Header getHeader() {
        return header;
    }

    public void setHeader(com.kasikornbank.dq.ws.v1.Header header) {
        this.header = header;
    }

    public com.kasikornbank.dq.ws.v1.ResponseStatus getResponseStatus() {
        return responseStatus;
    }

    public void setResponseStatus(com.kasikornbank.dq.ws.v1.ResponseStatus responseStatus) {
        this.responseStatus = responseStatus;
    }

}
