/**
 * Create Date May 16, 2012 
 * Create By Sankom
 * Copyright (c) 2012 Avalant Co.,Ltd.
 * 20 North Sathorn Road, 15 th Floor Bubhajit Bldg., Silom, Bangrak, Bangkok 10500, Thailand
 * All rights reserved.
 *
 * This software is the confidential and prorietary infomation of
 * Avalant Co.,Ltd. ("Confidential Infomation"). You shall not
 * disclose such Confidential Infomation and shall use it only in
 * accordance with the terms of the license agreement you entered into
 * with Avalant Co.,Ltd.
 */
package com.eaf.orig.ulo.pl.app.utility;

import java.util.Locale;
import java.util.ResourceBundle;
import javax.servlet.http.HttpServletRequest;
import com.eaf.cache.MandatoryFieldCache;
import com.eaf.orig.profile.model.UserDetailM;
import com.eaf.orig.shared.constant.OrigConstant;

/**
 * @author Sankom
 */

public class PLMessageResourceUtil {
	private static PLMessageResourceUtil me;
	
	private PLMessageResourceUtil() {
		super();
	}

	public synchronized static PLMessageResourceUtil getInstance() {
		if (me == null) {
			me = new PLMessageResourceUtil();
		}
		return me;
	}

	public static String getTextDescription(HttpServletRequest request,String textCode,UserDetailM userM,String customerType, String subFormID ,String fieldName ) {
		 String msg = getTextDescriptionMessage(request,textCode);
		 String role =(String)userM.getRoles().get(0);
		 String   searchType=(String) request.getSession().getAttribute("searchType");		 
		 boolean flag=MandatoryFieldCache.isMandatory_ORIG(customerType,"VERIFY_CUST",subFormID,fieldName);
		 if( !OrigConstant.SEARCH_TYPE_CASH_DAY5.equals(searchType)&&(OrigConstant.ROLE_DC.equalsIgnoreCase(role)||OrigConstant.ROLE_VC.equalsIgnoreCase(role))&& flag ){
			 msg="<font class=\"text-verify-bold\">"+msg+"</font>";
		 }
		return msg;
	}
	
	public static String getTextDescriptionMessage(HttpServletRequest request,String textCode) {

		Locale locale = Locale.getDefault();

		Locale currentLocale = (Locale) (request.getSession().getAttribute("LOCALE"));

		if (currentLocale != null) locale = currentLocale;
		ResourceBundle resource = ResourceBundle.getBundle(locale+ "/properties/getMessage", locale);
		if (textCode != null && !textCode.equals("")) {
			try {
				return resource.getString(textCode);
			} catch (Exception e) {
				e.printStackTrace();
			}
		}

		return "";
	}
	
	public static String getTextDescription(HttpServletRequest request,String textCode) {
	    
		Locale locale = Locale.getDefault();
		
		Locale currentLocale = (Locale) (request.getSession().getAttribute("LOCALE"));
		
		if (currentLocale != null)
			locale = currentLocale;
		
		ResourceBundle resource = ResourceBundle.getBundle(locale + "/properties/getMessage", locale);
		if (textCode != null && !textCode.equals("")) {
			try {
				return resource.getString(textCode);
			} catch (Exception e) {
				e.printStackTrace();
			}
		}

		return "";
	}
	
	public static String getTextDescriptionTh(String textCode) {
		Locale locale = new Locale("th", "TH");
	
		ResourceBundle resource = ResourceBundle.getBundle(locale + "/properties/getMessage", locale);

		if (textCode != null && !textCode.equals("")) {
			try {
				return resource.getString(textCode);
			} catch (Exception e) {
				e.printStackTrace();
			}
		}

		return "";
	}

}
