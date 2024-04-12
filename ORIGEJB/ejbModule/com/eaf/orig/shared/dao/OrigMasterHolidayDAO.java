/*
 * Created on Dec 6, 2007
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

import java.util.Date;

import com.eaf.orig.master.shared.model.HolidayM;
import com.eaf.orig.shared.dao.exceptions.OrigApplicationMException;

/**
 * @author Administrator
 *
 * Type: OrigMasterHolidayDAO
 */
public interface OrigMasterHolidayDAO {

	public void createModelOrigMasterHolidayM(HolidayM holidayM)throws OrigApplicationMException;
	public HolidayM selectOrigMasterHolidayM(Date holDate)throws OrigApplicationMException;
	public double updateOrigMasterHolidayM(HolidayM holidayM)throws OrigApplicationMException;
	public void deleteOrigMasterHoliday(String[] holDateToDelete)throws OrigApplicationMException;
	public boolean hvHolDate(Date holDate)throws OrigApplicationMException;
}
