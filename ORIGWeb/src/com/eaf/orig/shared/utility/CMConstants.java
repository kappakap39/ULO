/*
 * Created on Dec 14, 2007
 * Created by admin
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
package com.eaf.orig.shared.utility;

/**
 * @author admin
 *
 * Type: CMConstants
 */
public class CMConstants {
	public static String DB_NAME = "DatabaseName";
	public static String DB_USERNAME = "DatabaseUser";
	public static String DB_PASSWORD = "DatabasePassword";
	public static String DB_QUERY = "DatabaseQuery";
	public static String REQUEST_UPLOAD_PATH = "RequestUploadPath";
	public static String DOC_UPLOAD_PATH = "DocUploadPath";
	public static String FAX_FILE_UPLOAD_SERVER = "FaxFileUploadServer";
	public static String FAX_FILE_UPLOAD_FOLDER = "FaxFileUploadFolder";
	public static String FAX_FILE_UPLOAD_USER = "FaxFileUploadUser";
	public static String FAX_FILE_UPLOAD_PASSWORD = "FaxFileUploadPassword";
	public static String FAX_FILE_UPLOAD_TEMP_PATH = "FaxFileUploadTempPath";
	public static String DB_REQUEST_ITEM_TYPE = "CM_MERGE_IMAGE_ITEMTYPE";
	public static String DB_DOC_ITEM_TYPE = "CM_DOC_ITEMTYPE";
	public static String DB_IMAGE_ITEM_TYPE = "CM_IMAGE_ITEMTYPE";

	public static String IMAGE_CM_FIELD = "FAX_FILE_ID";
	public static String MERGED_REQUEST_ID_FIELD = "REQUEST_ID";
	public static String MERGED_ATTACH_ID_FIELD = "ATTACH_ID";
	public static String MIME_TYPE_TIFF = "image/tiff";
	
	// Tiwa added for Singapore 16/5/2006
	public static String DOC_APP_REC_ID_CM_FIELD = "APP_REC_ID";
	public static String DOC_FILE_NAME_CM_FIELD = "FILE_NAME";
	public static String DOC_ID_CM_FIELD = "DOC_ID";
	public static String DOC_SIZE_CM_FIELD = "SIZE";
	public static String DOC_TYPE_CM_FIELD = "DOC_TYPE";
	
	public static String DOC_TYPE_WORD = "WORD";
	public static String DOC_TYPE_EXCEL = "EXCEL";
	public final static String UPLOAD_APP_REC_ID = "appRecId";
	public final static String UPLOAD_REDIRECT_URL = "redirectUrl";
	public final static String UPLOAD_DOC_ID = "docId";
	public static String MERGED_NRIC_FIELD = "ID_NUMBER";
	public static String MERGED_FIRST_NAME_FIELD = "FIRST_NAME";
	public static String MERGED_LAST_NAME_FIELD = "LAST_NAME";
	public static String MERGED_APP_NO_FIELD = "APPLICATION_NO";
	public static String MERGED_ACCOUNT_NO_FIELD = "ACCOUNT_NO";
	public final static String UPLOAD_NRIC = "nric";
	public final static String UPLOAD_FIRST_NAME = "firstName";
	public final static String UPLOAD_LAST_NAME = "lastName";
	public final static String UPLOAD_APP_NO = "appNo";
	public final static String UPLOAD_ACC_NO = "accNo";
	public final static String UPLOAD_REQUEST_ID = "imgId";
	public final static String UPLOAD_COUNT = "count";
	public final static String UPLOAD_LOOP = "loop";
	// end Tiwa added for Singapore
	
	//Add for KLeasing
	public static String MERGED_PAGE_TYPE_FIELD = "PAGE_TYPE";
	public static String MERGED_MAIN_CUSTOMER_TYPE_FIELD = "MAIN_CUST_TYPE";
	public static String MERGED_LOAN_TYPE_FIELD = "LOAN_TYPE";
	public final static String UPLOAD_PAGE_TYPE = "docType";
	public final static String UPLOAD_LOAN_TYPE = "loanType";
	public final static String UPLOAD_MAIN_CUSTOMER_TYPE = "mainCustomerType";
	
	public static String TIFF_TYPE = ".TIF";
	public static String TIFF_ZIP = ".ZIP";

	public final static String UPLOAD_ATTACH_ID = "attachId";
	public final static String UPLOAD_TYPE = "uploadType";
	public final static String UPLOAD_CLIENT_FILE = "mergedFile";
	public final static String UPLOAD_TYPE_CREATE = "1";
	public final static String UPLOAD_TYPE_UPDATE = "2";
	public final static String UPLOAD_TYPE_DELETE = "3";	// Tiwa added
}
