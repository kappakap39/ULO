/*
 * Created on Nov 7, 2007
 * Created by Prawit Limwattanachai
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
import com.eaf.orig.shared.model.CorperatedDataM;

/**
 * @author Administrator
 *
 * Type: OrigCorperatedMDAO
 */
public interface OrigCorperatedMDAO {
	public void createModelOrigCorperatedM(CorperatedDataM prmCorperatedDataM)throws OrigCustomerAddressMException;
	//public void deleteModelOrigCorperatedM(CorperatedDataM prmCorperatedDataM)throws OrigCustomerAddressMException;
	public Vector loadModelOrigCorperatedM(String cmpcde,String personalID)throws OrigCustomerAddressMException;
	public void saveUpdateModelOrigCorperatedM(CorperatedDataM prmCorperatedDataM)throws OrigCustomerAddressMException;
	public void deleteNotInKeyTableORIG_Corperated(Vector addressvect, String cmpCode, String idNo) throws OrigCustomerAddressMException;
	public void deleteTableORIG_Corperated(String cmpCode, String idNo)throws OrigCustomerAddressMException;
}
