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

import com.eaf.orig.shared.xrules.dao.exception.XRulesDedupException;
import com.eaf.xrules.shared.model.XRulesDedupDataM;

/**
 * @author Sankom Sanpunya
 *
 * Type: XRulesDedupDAO
 */
public interface XRulesDedupDAO {
	public void createModelXRulesDedupM(XRulesDedupDataM prmXRulesDedupDataM)throws XRulesDedupException ;
	public void deleteModelXRulesDedupM(XRulesDedupDataM prmXRulesDedupDataM)throws XRulesDedupException;
	public Vector loadModelXRulesDedupM(String personalID)throws XRulesDedupException;	 
	public void saveUpdateModelXRulesDedupM(XRulesDedupDataM prmXRulesDedupDataM)throws XRulesDedupException;
	public void saveUpdateModelXRulesDedupM(Vector vXRulesDedupDataM)throws XRulesDedupException;
	public void deleteModelXRulesDedupM(String applicationRecordId,String cmpCde,Vector idNoVects)throws XRulesDedupException;
}
