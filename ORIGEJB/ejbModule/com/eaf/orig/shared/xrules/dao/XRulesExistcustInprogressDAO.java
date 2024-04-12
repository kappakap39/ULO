/*
 * Created on Dec 20, 2007
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

import com.eaf.orig.shared.xrules.dao.exception.XRulesExistcustInprogressException;
import com.eaf.xrules.shared.model.XRulesExistcustInprogressDataM;

/**
 * @author Sankom
 *
 * Type: XRulesExistcustInprogressDAO
 */
public interface XRulesExistcustInprogressDAO {
    public void createModelXRulesExistcustInprogressM(XRulesExistcustInprogressDataM prmXRulesExistcustInprogressDataM)throws XRulesExistcustInprogressException ;
	public void deleteModelXRulesExistcustInprogressM(XRulesExistcustInprogressDataM prmXRulesExistcustInprogressDataM)throws XRulesExistcustInprogressException;
	public Vector loadModelXRulesExistcustInprogressM(String personalID)throws XRulesExistcustInprogressException;	 
	public void saveUpdateModelXRulesExistcustInprogressM(XRulesExistcustInprogressDataM prmXRulesExistcustInprogressDataM)throws XRulesExistcustInprogressException;
	public void saveUpdateModelXRulesExistcustInprogressM(Vector vXRulesExistcustInprogressDataM)throws XRulesExistcustInprogressException;
	public void deleteModelXRulesExistcustInprogressM(String applicationRecordId,String cmpCde,Vector idNoVects)throws XRulesExistcustInprogressException;
}
