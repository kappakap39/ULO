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
package com.eaf.orig.ulo.app.dao;

import java.sql.Connection;
import java.util.ArrayList;

import com.eaf.orig.ulo.app.dao.exception.ApplicationException;
import com.eaf.orig.ulo.model.app.AttachmentDataM;

/**
 * @author Anu
 *
 * Type: OrigAttachmentHistoryMDAO
 */
public interface OrigAttachmentHistoryDAO {
	public void createOrigAttachmentHistoryM(AttachmentDataM attachmentHistoryDataM)throws ApplicationException;
	public void deleteOrigAttachmentHistoryM(String applicationGroupId, String attachId)throws ApplicationException;	
	public ArrayList<AttachmentDataM> loadOrigAttachmentHistoryM(String applicationGroupId)throws ApplicationException;	
	public ArrayList<AttachmentDataM> loadOrigAttachmentHistoryM(String applicationGroupId,Connection conn)throws ApplicationException;	
	public void saveUpdateOrigAttachmentHistoryM(AttachmentDataM attachmentHistoryDataM)throws ApplicationException;
	public void deleteNotInKeyAttachmentHistory(ArrayList<AttachmentDataM> attachList, String applicationGroupId) throws ApplicationException;
	public void deleteNotInKeyAttachmentHistory(ArrayList<AttachmentDataM> attachList, String applicationGroupId,Connection conn) throws ApplicationException;
	public AttachmentDataM loadAttachmentInfo(String attachId) throws ApplicationException;		
}
