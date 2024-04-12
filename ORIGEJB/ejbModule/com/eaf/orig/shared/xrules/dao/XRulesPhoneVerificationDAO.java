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

import com.eaf.orig.shared.xrules.dao.exception.XRulesPhoneVerificationException;
import com.eaf.xrules.shared.model.XRulesPhoneVerificationDataM;

/**
 * @author Sankom Sanpunya
 *
 * Type: XRulesPhoneVerificationDAO
 */
public interface XRulesPhoneVerificationDAO {
	public void createModelXRulesPhoneVerificationM(XRulesPhoneVerificationDataM prmXRulesPhoneVerificationDataM)throws XRulesPhoneVerificationException ;
	public void deleteModelXRulesPhoneVerificationM(XRulesPhoneVerificationDataM prmXRulesPhoneVerificationDataM)throws XRulesPhoneVerificationException;
	public Vector loadModelXRulesPhoneVerificationM(String personalID)throws XRulesPhoneVerificationException;	 
	public void saveUpdateModelXRulesPhoneVerificationM(XRulesPhoneVerificationDataM prmXRulesPhoneVerificationDataM)throws XRulesPhoneVerificationException;
	public void saveUpdateModelXRulesPhoneVerificationM(Vector vXRulesPhoneVerificationDataM)throws XRulesPhoneVerificationException;
	public void deleteModelXRulesPhoneVerificationM(String applicationRecordId,String cmpCde,Vector idNoVects)throws XRulesPhoneVerificationException;
}
