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

import com.eaf.orig.shared.dao.exceptions.OrigCustomerAddressMException;
import com.eaf.orig.shared.dao.exceptions.OrigCustomerChangeNameMException;
import com.eaf.orig.shared.model.ChangeNameDataM;


/**
 * @author Sankom Sanpunya
 *
 * Type: OrigCustomerChangeNameMDAO
 */
public interface OrigCustomerChangeNameMDAO {
	public void createModelOrigCustomerChangeNameM(ChangeNameDataM prmOrigchaneNameDataM)throws OrigCustomerChangeNameMException;
	public void deleteModelOrigCustomerChangeNameM(ChangeNameDataM prmOrigchaneNameDataM)throws OrigCustomerChangeNameMException;
	//public boolean findByPrimaryKey(ChangeNameDataM prmOrigchaneNameDataM)throws OrigCustomerChangeNameMException;
	public Vector loadModelOrigCustomerChangeNameM(String applicationRecordId,String idNo, String providerUrlEXT, String jndiEXT)throws OrigCustomerChangeNameMException;
	//public void saveModelOrigCustomerChangeNameM(ChangeNameDataM prmChaneNameDataM)throws OrigCustomerChangeNameMException;
	public void saveUpdateModelOrigCustomerChangeNameM(ChangeNameDataM prmOrigchaneNameDataM, String idNO)throws OrigCustomerChangeNameMException;
	public void deleteNotInKeyTableORIG_CUSTOMER_CHANGE_NAME(Vector changeVect, String appRecordID, String cmpCode, String idNo) throws OrigCustomerAddressMException;
	public void deleteTableORIG_CUSTOMER_CHANGE_NAME(String appRecordID, String cmpCode, String idNo) throws OrigCustomerChangeNameMException;
}
