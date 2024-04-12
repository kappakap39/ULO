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

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Properties;

/**
 * @author admin
 *
 * Type: CacheParam
 */
public class CacheParam {
	private static Properties prop = null;
	private static String username;
	private static String password;
	private static String dbName;
	private static String dbQuery;
	private static String requestUploadPath;
	private static String docUploadPath;
	private static String requestItemType;
	private static String docItemType;
	private static String imageItemType;
	private static Properties mimeTypeProp = null;
	private static String faxFileUploadServer;
	private static String faxFileUploadFolder;
	private static String faxFileUploadUser;
	private static String faxFileUploadPassword;
	private static String faxFileUploadTempPath;
	

	public static synchronized void loadConfiguration(String config) {
		try {
			if (prop == null) {
				prop = new Properties();
				if(dbName==null || dbName.equals("")){
					System.out.println("#### read config file ("+config+") ####");					
					File f = new File(config);
					FileInputStream fis = new FileInputStream(f);
					prop.load(fis);
					fis.close();
					dbName = prop.getProperty(CMConstants.DB_NAME);
					username = prop.getProperty(CMConstants.DB_USERNAME);
					password = prop.getProperty(CMConstants.DB_PASSWORD);
					dbQuery = prop.getProperty(CMConstants.DB_QUERY);
					requestUploadPath = prop.getProperty(CMConstants.REQUEST_UPLOAD_PATH);
					docUploadPath = prop.getProperty(CMConstants.DOC_UPLOAD_PATH);
					requestItemType = prop.getProperty(CMConstants.DB_REQUEST_ITEM_TYPE);
					docItemType = prop.getProperty(CMConstants.DB_DOC_ITEM_TYPE);
					imageItemType = prop.getProperty(CMConstants.DB_IMAGE_ITEM_TYPE);
					faxFileUploadServer = prop.getProperty(CMConstants.FAX_FILE_UPLOAD_SERVER);
					faxFileUploadFolder = prop.getProperty(CMConstants.FAX_FILE_UPLOAD_FOLDER);
					faxFileUploadUser = prop.getProperty(CMConstants.FAX_FILE_UPLOAD_USER);
					faxFileUploadPassword = prop.getProperty(CMConstants.FAX_FILE_UPLOAD_PASSWORD);
					faxFileUploadTempPath = prop.getProperty(CMConstants.FAX_FILE_UPLOAD_TEMP_PATH);
					
					System.out.println("DbName : " + dbName);
					System.out.println("Username : " + username);
					System.out.println("Password : " + password);
					System.out.println("DbQuery : " + dbQuery);
					System.out.println("requestUploadPath : " + requestUploadPath);
					System.out.println("docUploadPath : " + docUploadPath);
					System.out.println("RequestItemType : " + requestItemType);
					System.out.println("DocItemType : " + docItemType);
					System.out.println("ImageItemType : " + imageItemType);
					System.out.println("faxFileUploadServer : " + faxFileUploadServer);
					System.out.println("faxFileUploadFolder : " + faxFileUploadFolder);
					System.out.println("faxFileUploadUser : " + faxFileUploadUser);
					System.out.println("faxFileUploadPassword : " + faxFileUploadPassword);
					System.out.println("faxFileUploadTempPath : " + faxFileUploadTempPath);
					
//					/** check and create requestUploadPath */
//					File requestUploadDir = new File(requestUploadPath);
//					if(!requestUploadDir.exists() && !requestUploadDir.mkdir()){
//						System.out.println("cannot find and create "+requestUploadPath);
//					}
//					
//					/** check and create docUploadPath */
//					File docUploadDir = new File(docUploadPath);
//					if(!docUploadDir.exists() && !docUploadDir.mkdir()){
//						System.out.println("cannot find and create "+docUploadDir);
//					}
				}
			}
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public static synchronized void loadMimeTypeConfig(String mimeConfigFile) {
		try {
			if (mimeTypeProp == null) {
				System.out.println("#### read mime type config file ("+mimeConfigFile+") ####");
				
				mimeTypeProp = new Properties();
				File f = new File(mimeConfigFile);
				FileInputStream fis = new FileInputStream(f);
				mimeTypeProp.load(fis);
				fis.close();
			}
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	
	
	/**
	 * @return
	 */
	public static String getDbName() {
		return dbName;
	}

	/**
	 * @return
	 */
	public static String getDbQuery() {
		return dbQuery;
	}

	/**
	 * @return
	 */
	public static String getDocItemType() {
		return docItemType;
	}

	/**
	 * @return
	 */
	public static String getImageItemType() {
		return imageItemType;
	}

	/**
	 * @return
	 */
	public static Properties getMimeTypeProp() {
		return mimeTypeProp;
	}

	/**
	 * @return
	 */
	public static String getPassword() {
		return password;
	}

	/**
	 * @return
	 */
	public static Properties getProp() {
		return prop;
	}

	/**
	 * @return
	 */
	public static String getRequestItemType() {
		return requestItemType;
	}

	/**
	 * @return
	 */
	public static String getDocUploadPath() {
		return docUploadPath;
	}

	/**
	 * @return
	 */
	public static String getRequestUploadPath() {
		return requestUploadPath;
	}

	/**
	 * @return
	 */
	public static String getUsername() {
		return username;
	}

	/**
	 * @return
	 */
	public static String getFaxFileUploadPassword() {
		return faxFileUploadPassword;
	}

	/**
	 * @return
	 */
	public static String getFaxFileUploadTempPath() {
		return faxFileUploadTempPath;
	}

	/**
	 * @return
	 */
	public static String getFaxFileUploadUser() {
		return faxFileUploadUser;
	}

	/**
	 * @param string
	 */
	public static void setFaxFileUploadPassword(String string) {
		faxFileUploadPassword = string;
	}

	/**
	 * @param string
	 */
	public static void setFaxFileUploadTempPath(String string) {
		faxFileUploadTempPath = string;
	}

	/**
	 * @param string
	 */
	public static void setFaxFileUploadUser(String string) {
		faxFileUploadUser = string;
	}

	/**
	 * @return
	 */
	public static String getFaxFileUploadFolder() {
		return faxFileUploadFolder;
	}

	/**
	 * @return
	 */
	public static String getFaxFileUploadServer() {
		return faxFileUploadServer;
	}

	/**
	 * @param string
	 */
	public static void setFaxFileUploadFolder(String string) {
		faxFileUploadFolder = string;
	}

	/**
	 * @param string
	 */
	public static void setFaxFileUploadServer(String string) {
		faxFileUploadServer = string;
	}

}
