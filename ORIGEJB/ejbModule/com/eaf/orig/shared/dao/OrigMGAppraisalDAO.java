/*
 * Created on Mar 15, 2010
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

import com.eaf.orig.shared.dao.exceptions.OrigApplicationMException;
//import com.eaf.orig.shared.dao.utility.exception.UniqueIDGeneratorException;
import com.eaf.orig.shared.key.dao.exception.UniqueIDGeneratorException;
import com.eaf.orig.shared.model.AddressDataM;
import com.eaf.orig.shared.model.AppraisalDataM;

/**
 * @author wichaya
 *
 * Type: OrigMGAppraisalDAO
 */
public interface OrigMGAppraisalDAO {
    public void createAppraisalDataM(AppraisalDataM appraisalDataM, String collateral_id, String username) throws OrigApplicationMException, UniqueIDGeneratorException;
    public void updateAppraisalDataM(AppraisalDataM appraisalDataM, String collateral_id, String username) throws OrigApplicationMException;
    public AppraisalDataM loadAppraisalDataM(String collatera_id)throws OrigApplicationMException;
    public void deleteAppraisalDataM(String collateral_id)throws OrigApplicationMException;
    public void deleteAllAppraisalDataM(String appRecordID)throws OrigApplicationMException;
}
