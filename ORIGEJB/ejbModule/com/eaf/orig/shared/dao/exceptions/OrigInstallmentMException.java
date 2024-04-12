/*
 * Created on Jul 21, 2008
 * Created by Avalant
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
package com.eaf.orig.shared.dao.exceptions;

/**
 * @author Avalant
 *
 * Type: OrigInstallmentMException
 */
public class OrigInstallmentMException extends Exception {

    /**
     * 
     */
    public OrigInstallmentMException() {
        super();
        
    }
    /**
     * @param message
     */
    public OrigInstallmentMException(String message) {
        super(message);
        
    }
    /**
     * @param message
     * @param cause
     */
    public OrigInstallmentMException(String message, Throwable cause) {
        super(message, cause);
  
    }
    /**
     * @param cause
     */
    public OrigInstallmentMException(Throwable cause) {
        super(cause);
  
    }
}
