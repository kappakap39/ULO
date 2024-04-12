/*
 * Created on Oct 24, 2007
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

import com.eaf.orig.shared.xrules.dao.exception.ExtExistingCustomerException;
import com.eaf.xrules.shared.model.XRulesDataM;
import java.math.BigDecimal;

/**
 * @author Sankom Sanpunya
 *
 * Type: ExtExistingCustomerDAO
 */
public interface ExtExistingCustomerDAO {
 public Vector getExisitingCustomerInprogressEXTHire(XRulesDataM xRulesDataM)throws ExtExistingCustomerException;
 public Vector getExisitingCustomerInprogressEXTGuarantor(XRulesDataM xRulesDataM)throws ExtExistingCustomerException;
 public Vector getExisitingCustomerInprogressOrig(XRulesDataM xRulesDataM)throws ExtExistingCustomerException;	
 public Vector getExisitingCustomerBookedHire(XRulesDataM xRulesDataM)throws ExtExistingCustomerException;
 public Vector getExisitingCustomerBookedGuarantor(XRulesDataM xRulesDataM)throws ExtExistingCustomerException;
 
 public BigDecimal getInstallmentApplicationInprogressOrig(String applicationRecordId )throws ExtExistingCustomerException;
 public BigDecimal getInstallmentApplicationBooked(String contractNo )throws ExtExistingCustomerException;
 public BigDecimal getInstallmentApplicationInprogress(String applicationNo )throws ExtExistingCustomerException;
 public Vector getExisitingCustomerInprogressEXTHireMatchSurname(XRulesDataM xRulesDataM)throws ExtExistingCustomerException;
 public Vector getExisitingCustomerInprogressEXTGuarantorMatchSurname(XRulesDataM xRulesDataM)throws ExtExistingCustomerException;
 public Vector getExisitingCustomerInprogressOrigMatchSurname(XRulesDataM xRulesDataM)throws ExtExistingCustomerException;	
 public Vector getExisitingCustomerBookedHireMatchSurname(XRulesDataM xRulesDataM)throws ExtExistingCustomerException;
 public Vector getExisitingCustomerBookedGuarantorMatchSurname(XRulesDataM xRulesDataM)throws ExtExistingCustomerException;
}
