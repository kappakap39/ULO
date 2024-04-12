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

import java.util.Vector;

import com.eaf.orig.shared.dao.exceptions.OrigNotePadDataMException;
import com.eaf.orig.shared.model.NotePadDataM;
import com.eaf.orig.ulo.pl.app.dao.exception.PLOrigApplicationException;

/**
 * @author Sankom Sanpunya
 *
 * Type: OrigNotePadDataMDAO
 */
public interface OrigNotePadDataMDAO {
	public void createModelOrigNotePadDataM(NotePadDataM prmNotePadDataM)throws OrigNotePadDataMException;
	public void deleteModelOrigNotePadDataM(NotePadDataM prmNotePadDataM)throws OrigNotePadDataMException;
	//public boolean findByPrimaryKey(NotePadDataM prmNotePadDataM)throws OrigNotePadDataMException;
	public Vector<NotePadDataM> loadModelOrigNotePadDataM(String applicationRecordId)throws OrigNotePadDataMException;
	//public void saveModelOrigNotePadDataM(ApplicationLogM prmApplicationLogM)throws OrigNotePadDataMException;
	public void saveUpdateModelOrigNotePadDataM(NotePadDataM prmNotePadDataM)throws OrigNotePadDataMException;
	public void saveUpdateModelOrigNotePadDataVect(Vector<NotePadDataM> notePadVect, String appRecId)throws PLOrigApplicationException;
}
