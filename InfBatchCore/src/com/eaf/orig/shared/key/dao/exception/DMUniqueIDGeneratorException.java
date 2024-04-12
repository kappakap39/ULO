/*
 * Created on Aug 17, 2011
 * Created by Tanawat Suriyachai
 * 
 * Copyright (c) 2011 Avalant Co.,Ltd.
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
package com.eaf.orig.shared.key.dao.exception;

/**
 * @author Tanawat Suriyachai
 *
 * Type: UniqueIDGeneratorException
 */
public class DMUniqueIDGeneratorException extends Exception {
	/**
	 * 
	 */
	public DMUniqueIDGeneratorException() {
		super();
		
	}

	/**
	 * @param message
	 */
	public DMUniqueIDGeneratorException(String message) {
		super(message);
		
	}

	/**
	 * @param message
	 * @param cause
	 */
	public DMUniqueIDGeneratorException(String message, Throwable cause) {
		super(message, cause);
		
	}

	/**
	 * @param cause
	 */
	public DMUniqueIDGeneratorException(Throwable cause) {
		super(cause);
		
	}
}
