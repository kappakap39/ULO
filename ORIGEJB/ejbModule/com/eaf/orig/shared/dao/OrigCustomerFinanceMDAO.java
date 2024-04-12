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

import com.eaf.orig.shared.dao.exceptions.OrigCustomerFinnanceMException;
import com.eaf.orig.shared.model.BankDataM;

/**
 * @author Sankom Sanpunya
 *
 * Type: OrigCustomerFinnanceMDAO
 */
public interface OrigCustomerFinanceMDAO {
	public void createModelOrigCustomerFinnanceM(BankDataM prmBankDataM)throws OrigCustomerFinnanceMException;
	public void deleteModelOrigCustomerFinnanceM(BankDataM prmBankDataM)throws OrigCustomerFinnanceMException;
	//public boolean findByPrimaryKey(BankDataM prmBankDataM)throws OrigCustomerFinnanceMException;
	public Vector loadModelOrigCustomerFinnanceM(String applicationRecordId, String idNo, String providerUrlEXT, String jndiEXT)throws OrigCustomerFinnanceMException;
	//public void saveModelOrigCustomerFinnanceM(ApplicationLogM prmApplicationLogM)throws OrigCustomerFinnanceMException;
	public void saveUpdateModelOrigCustomerFinnanceM(BankDataM prmBankDataM,  String personID)throws OrigCustomerFinnanceMException;
	public void deleteNotInKeyTableORIG_CUSTOMER_FINANCE(Vector financeVectect, String personalID) throws OrigCustomerFinnanceMException;
	public void deleteTableORIG_CUSTOMER_FINANCE(String appRecordID, String cmpCode, String idNo)throws OrigCustomerFinnanceMException;
}
