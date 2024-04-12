/*
 * Created on Oct 19, 2007
 * Created by Weeraya
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

import com.eaf.orig.shared.dao.exceptions.OrigOtherNameMException;
import com.eaf.orig.shared.model.OtherNameDataM;

/**
 * @author Weeraya
 *
 * Type: OrigOtherNameDAO
 */
public interface OrigOtherNameMDAO {
	public void createModelOtherNameDataM(OtherNameDataM otherNameDataM) throws OrigOtherNameMException;
	public Vector loadModelOtherNameDataM(String applicationRecordId) throws OrigOtherNameMException;
	public void saveUpdateModelOtherNameDataM(OtherNameDataM otherNameDataM) throws OrigOtherNameMException;
	public void deleteNotInKeyTableORIG_OTHER_NAME(Vector otherNameVect, String appRecordID) throws OrigOtherNameMException;
	public void deleteTableORIG_OTHER_NAME(String appRecordID)throws OrigOtherNameMException;
}
