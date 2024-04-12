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

import java.util.ArrayList;
import java.util.Vector;

import com.eaf.orig.shared.dao.exceptions.OrigLoanMException;
import com.eaf.orig.shared.model.LoanDataM;

/**
 * @author Sankom Sanpunya
 *
 * Type: OrigLoanMDAO
 */
public interface OrigLoanMDAO {
	public void createModelOrigLoanM(LoanDataM prmLoanDataM)throws OrigLoanMException;
	public void deleteModelOrigLoanM(LoanDataM prmLoanDataM)throws OrigLoanMException;
	//public boolean findByPrimaryKey(LoanDataM prmLoanDataM)throws OrigLoanMException;
	public Vector loadModelOrigLoanM(String applicationRecordId)throws OrigLoanMException;
	//public void saveModelOrigLoanM(LoanDataM prmLoanDataM)throws OrigLoanMException;
	public void saveUpdateModelOrigLoanM(LoanDataM prmLoanDataM)throws OrigLoanMException;
	public void deleteNotInKeyTableORIG_LOAN(Vector loanVect, String appRecordID) throws OrigLoanMException;
	public void deleteTableORIG_LOAN(String appRecordID)throws OrigLoanMException;
	
	public LoanDataM loadModelOrigLoanMByVehicleId(int vehicleId)throws OrigLoanMException;
	public void saveUpdateModelOrigLoanMByVehicleId(LoanDataM prmLoanDataM)throws OrigLoanMException;
	public void deleteModelOrigLoanMByIdNo(String IdNo,String vehicleId)throws OrigLoanMException;
	public void saveUpdateModelOrigLoanMForCreateProposal(LoanDataM prmLoanDataM)throws OrigLoanMException;
	public int updateLoanForChangeCar(int loanId, String apprecordID)throws OrigLoanMException;
	
	//Wiroon 20100316
	//cost fee
	public ArrayList getCostFee(String origType) throws OrigLoanMException;
}
