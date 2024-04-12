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

import com.eaf.orig.shared.xrules.dao.exception.XRulesNCBException;
import com.eaf.xrules.shared.model.XRulesNCBDataM;
import com.eaf.xrules.ulo.pl.model.app.rule.PLXRulesNCBVerificationDataM;

/**
 * @author Sankom Sanpunya
 *
 * Type: XRulesNCBDAO
 */
public interface XRulesNCBDAO {
	public void createModelXRulesNCBM(XRulesNCBDataM prmXRulesNCBDataM)throws XRulesNCBException ;
	public void deleteModelXRulesNCBM(XRulesNCBDataM prmXRulesNCBDataM)throws XRulesNCBException;
	public Vector loadModelXRulesNCBM(String personalID)throws XRulesNCBException;	 
	public void saveUpdateModelXRulesNCBM(XRulesNCBDataM prmXRulesNCBDataM)throws XRulesNCBException;
	public void saveUpdateModelXRulesNCBM(Vector prmXRulesNCBDataM)throws XRulesNCBException;
	public void deleteModelXRulesNCBM(String applicationRecordId,String cmpCde,Vector idNoVects)throws XRulesNCBException;
	public Vector<PLXRulesNCBVerificationDataM> getDisplayNCB(String trackingCode, String personalId) throws XRulesNCBException;
}
