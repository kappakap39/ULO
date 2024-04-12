/*
 * Created on Mar 14, 2010
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

import java.util.Vector;

import com.eaf.orig.shared.dao.exceptions.OrigApplicationMException;
//import com.eaf.orig.shared.dao.utility.exception.UniqueIDGeneratorException;
import com.eaf.orig.shared.key.dao.exception.UniqueIDGeneratorException;
import com.eaf.orig.shared.model.AddressDataM;

/**
 * @author wichaya
 *
 * Type: OrigMGCollateralAddressDAO
 */
public interface OrigMGCollateralAddressDAO {
    public void createCollateralAddressM(AddressDataM addressDataM, String collatera_id, String username) throws OrigApplicationMException, UniqueIDGeneratorException;
    public void updateCollateralAddressM(AddressDataM addressDataM, String collatera_id, String username) throws OrigApplicationMException;
    public AddressDataM loadCollateralAddressM(String collatera_id)throws OrigApplicationMException;
    public void deleteCollateralAddressM(String collatera_id)throws OrigApplicationMException;
    public void deleteAllCollateralAddressM(String appRecordID)throws OrigApplicationMException;
}
