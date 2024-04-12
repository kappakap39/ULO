/*
 * Created on Dec 8, 2008
 * Created by wichaya
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
package com.eaf.orig.shared.servlet;

import javax.servlet.http.HttpServletRequest;

import com.eaf.orig.ulo.pl.app.dao.exception.PLOrigApplicationException;

/**
 * @author wichaya
 *
 * Type: AjaxDisplayGenerateInf
 */
public interface AjaxDisplayGenerateInf {
    
    public String getDisplayObject(HttpServletRequest request) throws PLOrigApplicationException;

}
