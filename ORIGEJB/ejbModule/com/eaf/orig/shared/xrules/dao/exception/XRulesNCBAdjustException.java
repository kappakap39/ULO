/*
 * Created on Aug 21, 2008
 * Created by Sankom Sanpunya
 * 
 * Copyright (c) 2007 Avalant Co.,Ltd.
 * 20 North Sathorn Road, 15-16th Floor Bubhajit Bldg., Silom, Bangrak, Bangkok 10500, Thailand
 * All rights reserved.
 *
 * This software is the confidential and prorietary infomation of
 * Avalant Co.,Ltd. ("Confidential Infomation"). You shall not
 * disclose such Confidential Infomation and shall use it only in
 * accordance with the terms of the license agreement you entered into
 * with Avalant Co.,Ltd.
 * 
 */
package com.eaf.orig.shared.xrules.dao.exception;

/**
 * @author Sankom
 *
 * Type: XRulesNCBAdjustException
 */
public class XRulesNCBAdjustException extends Exception {

    /**
     * @param message
     */
    public XRulesNCBAdjustException(String message) {
        super(message);      
    }
    /**
     * @param message
     * @param cause
     */
    public XRulesNCBAdjustException(String message, Throwable cause) {
        super(message, cause);
    }
    /**
     * @param cause
     */
    public XRulesNCBAdjustException(Throwable cause) {
        super(cause);
    }
    /**
     * 
     */
    public XRulesNCBAdjustException() {
        super();         
    }

}
