/*
 * Created on Nov 29, 2007
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

import com.eaf.orig.master.shared.model.CarBlacklistM;
import com.eaf.orig.shared.dao.exceptions.OrigApplicationMException;

/**
 * @author Administrator
 *
 * Type: OrigMasterCarBlacklistDAO
 */
public interface OrigMasterCarBlacklistDAO {
	public void createModelOrigMasterCarBlacklistM(CarBlacklistM carBlacklistM)throws OrigApplicationMException;
	public CarBlacklistM selectOrigMasterCarBlacklistM(int blackVehID)throws OrigApplicationMException;
	public double updateOrigMasterCarBlacklistM(CarBlacklistM carBlacklistM)throws OrigApplicationMException;
	public void deleteOrigMasterCarBlacklist(int[] blackVehID_intToDelete)throws OrigApplicationMException;
//	public boolean hvFieldID(int fieldID)throws OrigApplicationMException;
}
