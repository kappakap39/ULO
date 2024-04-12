/*
 * Created on Oct 3 , 2007
 * Created by weeraya
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

import com.eaf.orig.shared.dao.exceptions.OrigApplicationMException;
import com.eaf.orig.shared.model.ValueListM;
import com.eaf.orig.shared.search.SearchM;


/**
 * @author Administrator
 *
 * 
 * Window - Preferences - Java - Code Style - Code Templates
 */
public interface ValueListDAO {
	public ValueListM getResult(ValueListM valueListM) throws OrigApplicationMException;
	public ValueListM getResult2(ValueListM valueListM) throws OrigApplicationMException;
	public ValueListM getResult_master(ValueListM valueListM, String dataValue) throws OrigApplicationMException;
	public ValueListM getResult_master2(ValueListM valueListM, String dataValue) throws OrigApplicationMException;
	public SearchM getResultSearchM(SearchM searchM) throws OrigApplicationMException;
}
