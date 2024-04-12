/*
 * Created on Nov 22, 2007
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

import com.eaf.orig.master.shared.model.ListBoxMasterM;
import com.eaf.orig.shared.dao.exceptions.OrigApplicationMException;

/**
 * @author Administrator
 *
 * Type: OrigMasterListBoxDAO
 */
public interface OrigMasterListBoxDAO {
	public Vector SearchBusinessClassByDesc(String busClassDesc)throws OrigApplicationMException;
	public void createModelOrigMasterListBoxMasterM(ListBoxMasterM listBoxM)throws OrigApplicationMException;
	public ListBoxMasterM selectOrigMasterListBoxM(String listID)throws OrigApplicationMException;
	public double updateOrigMasterListBoxM(ListBoxMasterM listBoxM)throws OrigApplicationMException;
	public double updateLisboxBusCLass(String ListID,String BusID,ListBoxMasterM listBoxM)throws OrigApplicationMException;
	public void insertLisboxBusCLass(String ListID,String BusID,ListBoxMasterM listBoxM)throws OrigApplicationMException;
	public void deleteNotInKeyTableLisboxBusCLass(ListBoxMasterM listBoxM)throws OrigApplicationMException;
	public void deleteOrigMasterListBoxM(String[] listBoxToDelete)throws OrigApplicationMException;
	public Vector SearchAllBusinessClass()throws OrigApplicationMException;
	
}
