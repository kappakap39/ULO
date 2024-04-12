/*
 * Created on Jul 21, 2008
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

import com.eaf.orig.shared.dao.exceptions.OrigInstallmentMException;
import com.eaf.orig.shared.model.InstallmentDataM;

/**
 * @author Avalant
 *
 * Type: OrigInstallmentMDAO
 */
public interface OrigInstallmentMDAO {
	public void createModelOrigInstallmentM(InstallmentDataM prmInstallmentDataM)throws OrigInstallmentMException;
	public void deleteModelOrigInstallmentM(InstallmentDataM prmInstallmentDataM)throws OrigInstallmentMException;
	//public boolean findByPrimaryKey(ReasonLogDataM prmReasonLogDataM)throws OrigReasonLogMException;
	public Vector loadModelOrigInstallmentM(String applicationRecordId)throws OrigInstallmentMException;
	//public void saveModelOrigReasonLogM(ApplicationLogM prmApplicationLogM)throws OrigReasonLogMException;
	public void saveUpdateModelOrigInstallmentM(InstallmentDataM prmInstallmentDataM)throws OrigInstallmentMException;
	public void deleteNotInKeyTableORIG_Installment(Vector vOrigInstallment, String appRecordID) throws OrigInstallmentMException;
	public void deleteTableORIG_INSTALLMENT(String appRecordID)throws OrigInstallmentMException;
}
