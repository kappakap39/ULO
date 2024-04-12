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

import com.eaf.orig.shared.xrules.dao.exception.XRulesLPMException;
import com.eaf.xrules.shared.model.XRulesLPMDataM;

/**
 * @author Sankom Sanpunya
 *
 * Type: XRulesLPM
 */
public interface XRulesLPMDAO {
	public void createModelXRulesLPMM(XRulesLPMDataM prmXRulesLPMDataM)throws XRulesLPMException ;
	public void deleteModelXRulesLPMM(XRulesLPMDataM prmXRulesLPMDataM)throws XRulesLPMException;
	public Vector loadModelXRulesLPMM(String personalID)throws XRulesLPMException;	 
	public void saveUpdateModelXRulesLPMM(XRulesLPMDataM prmXRulesLPMDataM)throws XRulesLPMException;
	public void saveUpdateModelXRulesLPMM(Vector vXRulesLPMDataM)throws XRulesLPMException;
	public void deleteModelXRulesLPMM(String applicationRecordId,String cmpCde,Vector idNoVects)throws XRulesLPMException;
}
