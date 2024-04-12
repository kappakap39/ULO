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

import com.eaf.orig.shared.dao.exceptions.OrigApplicationMException;
import com.eaf.orig.shared.model.ApplicationDataM;

/**
 * @author Sankom Sanpunya
 *
 * Type: OrigApplicationMDAO
 */
public interface OrigApplicationMDAO {
	public void createModelOrigApplicationM(ApplicationDataM prmApplicationDataM)throws OrigApplicationMException;
	public void deleteModelOrigApplicationM(ApplicationDataM prmApplicationDataM)throws OrigApplicationMException;
	public ApplicationDataM loadModelOrigApplicationM(String applicationRecordId,int seq, String providerUrlEXT, String jndiEXT)throws OrigApplicationMException;	 
	public Vector saveUpdateModelOrigApplicationM(ApplicationDataM prmApplicationDataM, String role)throws OrigApplicationMException;
	public String selectApplicationNo(String applicationRecordId) throws OrigApplicationMException;
	public int updateSetPriority(String appRecordId, String priority, String userName)throws OrigApplicationMException;
	public int updateReassignApplication(String appRecordId,String userName)throws OrigApplicationMException;
	public int updateApprecordForIMG(String appRecordId, String requestID)throws OrigApplicationMException;
	public int updateStatusForIMG(String requestID)throws OrigApplicationMException;
	public Vector saveUpdateModelOrigApplicationMForCreateCar(ApplicationDataM prmApplicationDataM, String role)throws OrigApplicationMException;
	public ApplicationDataM loadModelOrigApplicationMForDrawDown(String applicationRecordId)throws OrigApplicationMException;
	public ApplicationDataM loadModelOrigApplicationM(String applicationRecordId, int seq, String providerUrlEXT, String jndiEXT, ApplicationDataM result)throws OrigApplicationMException;
	public int updateStatusForReverse(ApplicationDataM applicationDataM)throws OrigApplicationMException;
	public int updateStatus(ApplicationDataM applicationDataM)throws OrigApplicationMException;
	
	/**20120227: Rawi*/
	public void SaveUpdateModelApplicationDataM(ApplicationDataM applicationDataM)throws OrigApplicationMException;
}
