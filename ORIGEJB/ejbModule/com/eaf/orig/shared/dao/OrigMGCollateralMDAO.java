/*
 * Created on Mar 13, 2010
 * Created by wichaya
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

import java.rmi.RemoteException;
import java.sql.Timestamp;
import java.util.Vector;

import com.eaf.orig.shared.dao.exceptions.OrigApplicationMException;
//import com.eaf.orig.shared.dao.utility.exception.UniqueIDGeneratorException;
import com.eaf.orig.shared.key.dao.exception.UniqueIDGeneratorException;

/**
 * @author wichaya
 *
 * Type: OrigMGCollateralMDAOImpl
 */
public interface OrigMGCollateralMDAO {

    public void createCollateralM(Vector vCollatralM, String appRecordID, String username) throws OrigApplicationMException, UniqueIDGeneratorException, RemoteException;
    public void updateCollateralM(Vector vCollatralM, String appRecordID, String username) throws OrigApplicationMException;
    public Vector loadCollateralM(String applicationRecordId)throws OrigApplicationMException;
}
