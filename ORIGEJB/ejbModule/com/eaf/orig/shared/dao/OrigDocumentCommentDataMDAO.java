/*
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

import com.eaf.orig.shared.dao.exceptions.ApplicationException;
import com.eaf.orig.ulo.model.app.DocumentCommentDataM;
import com.eaf.orig.ulo.pl.app.dao.exception.PLOrigApplicationException;

public interface OrigDocumentCommentDataMDAO {
	public void createModelOrigDocumentCommentDataM(DocumentCommentDataM prmNotePadDataM) throws ApplicationException;

	public void deleteModelOrigDocumentCommentDataM(DocumentCommentDataM prmNotePadDataM) throws ApplicationException;

	public Vector<DocumentCommentDataM> loadModelOrigNotePadDataM(String applicationRecordId) throws ApplicationException;

	public void saveUpdateModelOrigDocumentCommentDataM(DocumentCommentDataM prmNotePadDataM) throws ApplicationException;

	public void saveUpdateModelOrigDocumentCommentDataVect(Vector<DocumentCommentDataM> notePadVect, String appRecId) throws PLOrigApplicationException;
}
