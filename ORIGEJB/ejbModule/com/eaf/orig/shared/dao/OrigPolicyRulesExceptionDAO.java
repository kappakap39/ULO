/*
 * Created on Nov 10, 2008
 * Created by Avalant
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

import java.util.Vector;

import com.eaf.orig.master.shared.model.OrigPolicyRulesExceptionDataM;
import com.eaf.orig.shared.dao.exceptions.OrigMasterMException;

/**
 * @author Sankom
 *
 * Type: OrigPolicyRulesExceptionDAO
 */
public interface OrigPolicyRulesExceptionDAO {
    public void createModelOrigPolicyRulesExceptionDataM(OrigPolicyRulesExceptionDataM prmOrigPolicyRulesExceptionDataM)throws OrigMasterMException;
	public void deleteModelOrigPolicyRulesExceptionDataM(OrigPolicyRulesExceptionDataM prmOrigPolicyRulesExceptionDataM)throws OrigMasterMException;
	public Vector loadModelOrigPolicyRulesExceptionDataM(String policyVersion)throws OrigMasterMException;
	public void saveUpdateModelOrigPolicyRulesExceptionDataM(OrigPolicyRulesExceptionDataM prmOrigPolicyRulesExceptionDataM)throws OrigMasterMException;
	public void deleteTableORIG_POLICY_RULES_EX(String policyVersion)throws OrigMasterMException;
}
