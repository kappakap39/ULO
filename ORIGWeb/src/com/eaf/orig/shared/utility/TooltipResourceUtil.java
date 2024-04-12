/*
 * Created on Jan 18, 2008
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
package com.eaf.orig.shared.utility;

import java.util.Locale;
import java.util.ResourceBundle;

import javax.servlet.http.HttpServletRequest;

/**
 * @author Avalant
 *
 * Type: TooltipResourceUtil
 */
public class TooltipResourceUtil {
    private static TooltipResourceUtil me;
	
	public synchronized static TooltipResourceUtil getInstance() {
		if (me == null) {
			me = new TooltipResourceUtil();
		}
		return me;
	}
	
	public   String getTooltip(HttpServletRequest request,String textCode) {
	    
			Locale locale = Locale.getDefault();
			
			Locale currentLocale =
				(Locale) (request.getSession().getAttribute("LOCALE"));
			if (currentLocale != null)
				locale = currentLocale;

			ResourceBundle resource =
			ResourceBundle.getBundle(locale + "/properties/getTooltip", locale);

			if (textCode != null && !textCode.equals("")) {
				try {
				    String scriptFont=" Tip('";
				    String scriptBack=" ', BALLOON, true, ABOVE, true,CLICKCLOSE ,true,DURATION ,3000 )";
					return scriptFont+resource.getString(textCode)+scriptBack;
				} catch (Exception e) {
					e.printStackTrace();

				}
			}

			return null;
		}
}
