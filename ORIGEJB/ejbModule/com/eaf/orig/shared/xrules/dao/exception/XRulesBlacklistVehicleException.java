/*
 * Created on Oct 5, 2007
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
 * @author Sankom Sanpunya
 *
 * Type: XRulesBlacklistVehicleException
 */
public class XRulesBlacklistVehicleException extends Exception {

	/**
	 * 
	 */
	public XRulesBlacklistVehicleException() {
		super();
		
	}

	/**
	 * @param message
	 */
	public XRulesBlacklistVehicleException(String message) {
		super(message);
		
	}
	/**
	 * @param message
	 * @param cause
	 */
	public XRulesBlacklistVehicleException(String message, Throwable cause) {
		super(message, cause);
		
	}
	/**
	 * @param cause
	 */
	public XRulesBlacklistVehicleException(Throwable cause) {
		super(cause);
		
	}
}
