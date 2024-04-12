/*
 * Created on Oct 19, 2007
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
package com.eaf.orig.shared.xrules.dao;

import java.util.Vector;

import com.eaf.orig.shared.xrules.dao.exception.ExtBlacklistException;
import com.eaf.xrules.shared.model.XRulesDataM;

/**
 * @author Sankom Sanpunya
 *
 * Type: ExtBlacklistDAO
 */
public interface ExtBlacklistDAO {
   public Vector getBlacklist(XRulesDataM xrulesDataM) throws ExtBlacklistException;
   public Vector getBlacklist(String cmpCde, String idNo,
			String thaiFirstName, String thaiLastName,String houseId,String customerType) throws ExtBlacklistException;    
}
