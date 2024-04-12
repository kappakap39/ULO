/*
 * Created on Nov 25, 2007
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

import java.util.Vector;

import com.eaf.orig.master.shared.model.RunningParamM;
import com.eaf.orig.shared.dao.exceptions.OrigApplicationMException;

/**
 * @author Administrator
 *
 * Type: OrigMasterRunParamDAO
 */
public interface OrigMasterRunParamDAO {
	public RunningParamM selectOrigMasterRunParamM(String runPrmID, String runPrmType)throws OrigApplicationMException;
	public double updateOrigMasterRunParamM(RunningParamM runParamM)throws OrigApplicationMException;
//	public void deleteOrigMasterFieldIdM(int[] fieldIdToDelete)throws OrigApplicationMException;
    /**
     * @param runParamToDelete
     */
    public void deleteOrigMasterRunParamM(Vector runParamToDelete)throws OrigApplicationMException;
}
