/*
 * Created on Oct 19, 2007
 * Created by Weeraya
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
 * @author Weeraya
 *
 * Type: OrigOtherNameMException
 */
public class OrigOtherNameMException extends Exception {

	/**
	 * 
	 */
	public OrigOtherNameMException() {
		super();
		
	}

	/**
	 * @param message
	 */
	public OrigOtherNameMException(String message) {
		super(message);
		
	}

	/**
	 * @param message
	 * @param cause
	 */
	public OrigOtherNameMException(String message, Throwable cause) {
		super(message, cause);
		
	}

	/**
	 * @param cause
	 */
	public OrigOtherNameMException(Throwable cause) {
		super(cause);
		
	}

}
