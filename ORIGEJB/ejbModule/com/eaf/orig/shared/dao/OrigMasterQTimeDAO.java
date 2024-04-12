/*
 * Created on Nov 28, 2007
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

import com.eaf.orig.master.shared.model.QueueTimeOutM;
import com.eaf.orig.shared.dao.exceptions.OrigApplicationMException;

/**
 * @author Administrator
 *
 * Type: OrigMasterQTimeDAO
 */
public interface OrigMasterQTimeDAO {
	public void createModelOrigMasterQTimeOutM(QueueTimeOutM qTimeOutM)throws OrigApplicationMException;
	public QueueTimeOutM selectOrigMasterQTimeM(String qTimeID)throws OrigApplicationMException;
	public double updateOrigMasterQTimeOutM(QueueTimeOutM qTimeOutM)throws OrigApplicationMException;
	public void deleteOrigMasterQTimeOut(String[] qTimeIDToDelete)throws OrigApplicationMException;
	public boolean hvQTimeID(String QTimeID)throws OrigApplicationMException;

}
