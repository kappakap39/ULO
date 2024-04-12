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
package com.eaf.orig.shared.xrules.dao;

import java.util.Vector;

import com.eaf.orig.shared.xrules.dao.exception.XRulesNCBAdjustException;
import com.eaf.xrules.shared.model.XRulesNCBAdjustDataM;

/**
 * @author Sankom Sanpunya
 *
 * Type: XRulesNCBDAO
 */
public interface XRulesNCBAdjustDAO {
	public void createModelXRulesNCBAdjustM(XRulesNCBAdjustDataM prmXRulesNCBAdjustDataM)throws XRulesNCBAdjustException ;
	public void deleteModelXRulesNCBAdjustM(XRulesNCBAdjustDataM prmXRulesNCBAdjustDataM)throws XRulesNCBAdjustException;
	public Vector loadModelXRulesNCBAdjustM(String personalID,String trackingCode)throws XRulesNCBAdjustException;	 
	public void saveUpdateModelXRulesNCBAdjustM(XRulesNCBAdjustDataM prmXRulesNCBAdjustDataM)throws XRulesNCBAdjustException;
	public void saveUpdateModelXRulesNCBAdjustM(Vector prmXRulesNCBAdjustDataM)throws XRulesNCBAdjustException;
	public void deleteModelXRulesNCBAdjustM(String personalID)throws XRulesNCBAdjustException;
	public void deleteModelXRulesNCBAdjustM(String applicationRecordId,String cmpCde,Vector idNoVects)throws XRulesNCBAdjustException;
}
