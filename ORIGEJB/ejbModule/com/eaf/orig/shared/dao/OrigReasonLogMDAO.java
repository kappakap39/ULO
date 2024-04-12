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

import com.eaf.orig.shared.dao.exceptions.OrigReasonLogMException;
import com.eaf.orig.shared.model.ReasonDataM;

/**
 * @author Sankom Sanpunya
 *
 * Type: OrigReasonLogMDAO
 */
public interface OrigReasonLogMDAO {
	public void createModelOrigReasonLogM(ReasonDataM prmReasonLogDataM)throws OrigReasonLogMException;
	public void deleteModelOrigReasonLogM(ReasonDataM prmReasonLogDataM)throws OrigReasonLogMException;
	//public boolean findByPrimaryKey(ReasonLogDataM prmReasonLogDataM)throws OrigReasonLogMException;
	public Vector loadModelOrigReasonLogM(String applicationRecordId)throws OrigReasonLogMException;
	//public void saveModelOrigReasonLogM(ApplicationLogM prmApplicationLogM)throws OrigReasonLogMException;
	public void saveUpdateModelOrigReasonLogM(ReasonDataM prmReasonLogDataM)throws OrigReasonLogMException;
}
