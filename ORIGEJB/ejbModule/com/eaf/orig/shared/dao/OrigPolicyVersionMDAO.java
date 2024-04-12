/*
 * Created on Sep 9, 2008
 * Created by Sankom
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
package com.eaf.orig.shared.dao;

import com.eaf.orig.master.shared.model.OrigPolicyVersionDataM;
import com.eaf.orig.shared.dao.exceptions.OrigMasterMException;

/**
 * @author Sankom
 *
 * Type: OrigPolicyVersionMDAO
 */
public interface OrigPolicyVersionMDAO {
    public void createModelOrigPolicyVersionDataM(OrigPolicyVersionDataM prmOrigPolicyVersionDataM)throws OrigMasterMException;
	public void deleteModelOrigPolicyVersionDataM(OrigPolicyVersionDataM prmOrigPolicyVersionDataM)throws OrigMasterMException;
	public OrigPolicyVersionDataM loadModelOrigPolicyVersionDataM(String policyVersion)throws OrigMasterMException;
	public void saveUpdateModelOrigPolicyVersionDataM(OrigPolicyVersionDataM prmOrigPolicyVersionDataM)throws OrigMasterMException;	 	
	public OrigPolicyVersionDataM getPolicyVersion(String createDate ) throws OrigMasterMException;
	public void deleteModelOrigPolicyVersionDataM(String[] policyVersion)throws OrigMasterMException;
}
