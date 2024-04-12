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

import com.eaf.orig.shared.xrules.dao.exception.XRulesDebtDdException;
import com.eaf.xrules.shared.model.XRulesDebtBdDataM;

/**
 * @author Sankom Sanpunya
 * 
 * Type: XrulesDebtBd
 */
public interface XRulesDebtBdDAO {
    public void createModelXRulesDebBdM(XRulesDebtBdDataM prmXRulesDebBdDataM) throws XRulesDebtDdException;

    public void deleteModelXRulesDebBdM(XRulesDebtBdDataM prmXRulesDebBdDataM) throws XRulesDebtDdException;

    public XRulesDebtBdDataM loadModelXRulesDebBdM(String personalID) throws XRulesDebtDdException;

    public void saveUpdateModelXRulesDebBdM(XRulesDebtBdDataM prmXRulesDebBdDataM) throws XRulesDebtDdException;

    //public void saveUpdateModelXRulesDebBdM(Vector vXRulesDebBdDataM)throws
    // XRulesDebtDdException;
    public void deleteModelXRulesDebBdM(String applicationRecordId, String cmpCde, Vector idNoVects) throws XRulesDebtDdException;
}
