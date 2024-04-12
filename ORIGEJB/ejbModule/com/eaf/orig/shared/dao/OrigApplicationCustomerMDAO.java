/*
 * Created on Sep 20, 2007
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
package com.eaf.orig.shared.dao;

import java.util.Vector;

import com.eaf.orig.shared.dao.exceptions.OrigApplicationCustomerMException;
import com.eaf.orig.shared.model.PersonalInfoDataM;

/**
 * @author Sankom Sanpunya
 *
 * Type: OrigApplicationCustomerMDAO
 */
public interface OrigApplicationCustomerMDAO {
	public void createModelOrigApplicationCustomerM(PersonalInfoDataM prmPersonalInfoDataM)throws OrigApplicationCustomerMException;
	public void deleteModelOrigApplicationCustomerM(PersonalInfoDataM prmPersonalInfoDataM)throws OrigApplicationCustomerMException;	 
	public Vector loadModelOrigApplicationCustomerM(String applicationRecordId, String providerUrlEXT, String jndiEXT)throws OrigApplicationCustomerMException; 
	public PersonalInfoDataM loadModelOrigApplicationCustomerM(PersonalInfoDataM prmPersonalInfoDataM)throws OrigApplicationCustomerMException;
	public void saveUpdateModelOrigApplicationCustomerM(PersonalInfoDataM prmPersonalInfoDataM)throws OrigApplicationCustomerMException;
	public void deleteNotInKeyTableORIG_APPLICATION_CUSTOMER(Vector personalVect, String appRecordID) throws OrigApplicationCustomerMException;
	public Vector selectPersonalIDNotInModel(Vector personalVect, String appRecordID)throws OrigApplicationCustomerMException;
	public String selectCustomerTypeByAppRecordId(String appRecordID)throws OrigApplicationCustomerMException;
}
