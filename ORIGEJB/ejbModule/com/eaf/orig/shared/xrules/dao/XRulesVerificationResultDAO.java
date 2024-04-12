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

import com.eaf.orig.shared.xrules.dao.exception.XRulesVerificationResultException;
import com.eaf.xrules.shared.model.XRulesVerificationResultDataM;

/**
 * @author Sankom Sanpunya
 *
 * Type: XRulesVerificationResultDAO
 */
public interface XRulesVerificationResultDAO {
	public void createModelXRulesVerificationResultM(XRulesVerificationResultDataM prmXRulesVerificationResultDataM)throws XRulesVerificationResultException ;
	public void deleteModelXRulesVerificationResultM(XRulesVerificationResultDataM prmXRulesVerificationResultDataM)throws XRulesVerificationResultException;
	//public XRulesVerificationResultDataM loadModelXRulesVerificationResultM(String applicationRecordId,String cmpcde,String idno)throws XRulesVerificationResultException;	 
	public XRulesVerificationResultDataM loadModelXRulesVerificationResultM(String personalID)throws XRulesVerificationResultException;	 
	public void saveUpdateModelXRulesVerificationResultM(XRulesVerificationResultDataM prmXRulesVerificationResultDataM)throws XRulesVerificationResultException;
	public void deleteModelXRulesVerificationResultM(String applicationRecordId,String cmpCde,Vector idNoVects)throws XRulesVerificationResultException;
}
