/**
 * Create Date Mar 13, 2012 
 * Create By Sankom
 * Copyright (c) 2012 Avalant Co.,Ltd.
 * 20 North Sathorn Road, 15 th Floor Bubhajit Bldg., Silom, Bangrak, Bangkok 10500, Thailand
 * All rights reserved.
 *
 * This software is the confidential and prorietary infomation of
 * Avalant Co.,Ltd. ("Confidential Infomation"). You shall not
 * disclose such Confidential Infomation and shall use it only in
 * accordance with the terms of the license agreement you entered into
 * with Avalant Co.,Ltd.
*/
package com.eaf.orig.ulo.pl.app.dao;

import java.util.Vector;

import com.eaf.orig.ulo.pl.app.dao.exception.PLOrigApplicationException;
import com.eaf.orig.ulo.pl.model.app.PLAttachmentHistoryDataM;

/**
 * @author Sankom
 *
 */
public interface PLOrigAttachmentHistoryMDAO {
	public void createModelOrigAttachmentHistoryM(PLAttachmentHistoryDataM prmPLAttachmentHistoryDataM)throws PLOrigApplicationException;
	public void deleteModelOrigAttachmentHistoryM(PLAttachmentHistoryDataM prmPLAttachmentHistoryDataM)throws PLOrigApplicationException;	
	public Vector<PLAttachmentHistoryDataM> loadModelOrigAttachmentHistoryM(String applicationRecordId)throws PLOrigApplicationException;	
	public void saveUpdateModelOrigAttachmentHistoryM(PLAttachmentHistoryDataM prmPLAttachmentHistoryDataM)throws PLOrigApplicationException;
	public void deleteNotInKeyTableORIG_ATTACHMENT_HISTORY(Vector<PLAttachmentHistoryDataM> attachVect, String appRecordID) throws PLOrigApplicationException;
	public void deleteTableORIG_ATTACHMENT_HISTORY(String appRecordID)throws PLOrigApplicationException;
	public PLAttachmentHistoryDataM loadModelOrigAttachmentHistoryMFromAttachId(String attachmentId)throws PLOrigApplicationException;
	public void deleteTableORIG_ATTACHMENT_HISTORY_ByAttachId(String attachId)throws PLOrigApplicationException;
	public Vector<PLAttachmentHistoryDataM> loadModelOrigAttachmentHistory(int day)throws PLOrigApplicationException;
}
