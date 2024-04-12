/*
 * Created on Nov 28, 2007
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

import com.eaf.orig.shared.xrules.dao.exception.XRulesFICOException;
import com.eaf.xrules.shared.model.XRulesFICODataM;

/**
 * @author Sankom
 *
 * Type: XRulesFICODAO
 */
public interface XRulesFICODAO {
    public void createModelXRulesFICODataM(XRulesFICODataM prmXRulesFICODataM)throws XRulesFICOException ;
	public void deleteModelXRulesFICODataM(XRulesFICODataM prmXRulesFICODataM)throws XRulesFICOException;
	public XRulesFICODataM loadModelXRulesFICODataM(String personalID)throws XRulesFICOException;	 
	public void saveUpdateModelXRulesFICODataM(XRulesFICODataM prmXRulesFICODataM)throws XRulesFICOException;
	public void deleteModelXRulesFICODataM(String applicationRecordId,String cmpCde,Vector idNoVects)throws XRulesFICOException;
}
