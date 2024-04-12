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

import com.eaf.orig.shared.dao.exceptions.OrigApplicationApprvGroupMException;
import com.eaf.orig.shared.model.ApplicationApprvGroupM;

/**
 * @author Sankom Sanpunya
 *
 * Type: OrigApplicationAPPRVGroup
 */
public interface OrigApplicationApprvGroupMDAO {
	public void createModelOrigApplicationApprvGroupM(ApplicationApprvGroupM prmApplicationApprvGroupM)throws OrigApplicationApprvGroupMException;
	public void deleteModelOrigApplicationApprvGroupM(ApplicationApprvGroupM prmApplicationApprvGroupM)throws OrigApplicationApprvGroupMException;	 
	public ApplicationApprvGroupM loadModelOrigApplicationApprvGroupM(String applicationRecordId)throws OrigApplicationApprvGroupMException;	 
	public void saveUpdateModelOrigApplicationApprvGroupM(ApplicationApprvGroupM prmApplicationApprvGroupM)throws OrigApplicationApprvGroupMException;
	public void deleteNotInKeyTableORIG_APPLICATION_APPV_GRP(String escalateGroup, String appRecordID) throws OrigApplicationApprvGroupMException;
	public void saveUpdateModelOrigApplicationApprvGroupM(String escalateGroup, String appRecordID, String loanType, String userName, String customerType)throws OrigApplicationApprvGroupMException;
}
