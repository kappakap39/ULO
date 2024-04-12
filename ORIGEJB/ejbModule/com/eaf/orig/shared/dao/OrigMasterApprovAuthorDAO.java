/*
 * Created on Nov 28, 2007
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

import java.math.BigDecimal;
import java.util.Vector;

import com.eaf.orig.master.shared.model.ApprovAuthorM;
import com.eaf.orig.shared.dao.exceptions.OrigApplicationMException;
import com.eaf.orig.shared.dao.exceptions.OrigMasterMException;

/**
 * @author Administrator
 *
 * Type: OrigMasterApprovAuthorDAO
 */
public interface OrigMasterApprovAuthorDAO {
	public void createModelOrigMasterApprovAuthorM(ApprovAuthorM approvAuthorM)throws OrigApplicationMException;
	public ApprovAuthorM selectOrigMasterApprovalAuthority(String groupName, String loanType,String carType,String custType,String scoringResult,String policyVersion,boolean policyNotFail ,BigDecimal totalExposure, String appType,
            BigDecimal installmentTerm, BigDecimal downPayment)throws OrigApplicationMException;
	public int updateOrigMasterApprovAuthor(ApprovAuthorM approvAuthorM)throws OrigApplicationMException;
	public void deleteOrigMasterApprovAuthorM(String[] appAuthorToDelete)throws OrigApplicationMException;
	public boolean hvColumn(String grpNm, String lnTyp, String cusTyp)throws OrigApplicationMException;
	public Vector loadOrigMasterApprovalAuthority(String policyVersion)throws OrigApplicationMException;
	public void saveUpdateModelOrigMasterApprovalAuthority(ApprovAuthorM approvAuthorM) throws OrigApplicationMException;
	public void deleteTableAPPROVAL_AUTHORITY(String policyVersion)throws OrigApplicationMException;
}
