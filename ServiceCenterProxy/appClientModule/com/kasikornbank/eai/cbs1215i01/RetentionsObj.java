/**
 * RetentionsObj.java
 *
 * This file was auto-generated from WSDL
 * by the IBM Web services WSDL2Java emitter.
 * gm1318.02 v5913144247
 */

package com.kasikornbank.eai.cbs1215i01;

public class RetentionsObj  {
    private com.kasikornbank.eai.cbs1215i01.CBSRetention[] retentionsVect;
    private java.math.BigDecimal totalRetentionAmt;

    public RetentionsObj() {
    }

    public com.kasikornbank.eai.cbs1215i01.CBSRetention[] getRetentionsVect() {
        return retentionsVect;
    }

    public void setRetentionsVect(com.kasikornbank.eai.cbs1215i01.CBSRetention[] retentionsVect) {
        this.retentionsVect = retentionsVect;
    }

    public java.math.BigDecimal getTotalRetentionAmt() {
        return totalRetentionAmt;
    }

    public void setTotalRetentionAmt(java.math.BigDecimal totalRetentionAmt) {
        this.totalRetentionAmt = totalRetentionAmt;
    }

}
