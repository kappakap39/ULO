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

import com.eaf.orig.profile.model.UserDetailM;
import com.eaf.orig.shared.dao.exceptions.OrigReasonMException;
import com.eaf.orig.shared.model.ReasonDataM;
import com.eaf.orig.ulo.pl.model.app.PLReasonDataM;

/**
 * @author Sankom Sanpunya
 *
 * Type: OrigReasonMDAO
 */
public interface OrigReasonMDAO {
	public void createModelOrigReasonM(ReasonDataM prmReasonDataM)throws OrigReasonMException;
	public void deleteModelOrigReasonM(ReasonDataM prmReasonDataM)throws OrigReasonMException;
	//public boolean findByPrimaryKey(ReasonDataM prmReasonDataM)throws OrigReasonMException;
	public Vector loadModelOrigReasonM(String applicationRecordId)throws OrigReasonMException;
	//public void saveModelOrigReasonM(ApplicationLogM prmApplicationLogM)throws OrigReasonMException;
	public void saveUpdateModelOrigReasonM(ReasonDataM prmReasonDataM)throws OrigReasonMException;
	public void deleteNotInKeyTableORIG_REASON(Vector reasonVect, String appRecordID, String roleID) throws OrigReasonMException;
	public void deleteTableORIG_REASON(String appRecordID)throws OrigReasonMException;
	public Vector<PLReasonDataM> getConfirmRejectReasons(String appRecordID, UserDetailM userM)throws OrigReasonMException;
}
