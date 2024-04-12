/*
 * Created on Sep 20, 2007
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
package com.eaf.orig.shared.dao;

/**
 * @author Weeraya
 *
 * Type: OrigImageException
 */
public class OrigImageException extends Exception {

	/**
	 * 
	 */
	public OrigImageException() {
		super();
		
	}

	/**
	 * @param message
	 */
	public OrigImageException(String message) {
		super(message);
		
	}

	/**
	 * @param message
	 * @param cause
	 */
	public OrigImageException(String message,
			Throwable cause) {
		super(message, cause);
		
	}

	/**
	 * @param cause
	 */
	public OrigImageException(Throwable cause) {
		super(cause);
		
	}

}
