/*
 * Created on Nov 16, 2007
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

import com.eaf.ias.shared.model.UserM;
import com.eaf.orig.profile.model.UserDetailM;
import com.eaf.orig.shared.dao.exceptions.OrigApplicationMException;


/**
 * @author Administrator
 *
 * Type: OrigMasterDAO
 */
public interface OrigMasterUserDetailDAO {
	public void createModelOrigMasterUserDetailM(UserDetailM userDetailM)throws OrigApplicationMException;
	public UserDetailM selectOrigMasterUserDetailM(String userNameEdit)throws OrigApplicationMException;
	public double updateOrigMasterUserDetailM(UserDetailM userDetailM)throws OrigApplicationMException;
	public void deleteOrigMasterUserDetailM(String[] userDetailToDelete)throws OrigApplicationMException;
	public boolean hvUsername(String userName)throws OrigApplicationMException;
	public Vector SearchBranchByDesc(String branchDesc)throws OrigApplicationMException;
	public Vector SearchExceptByDesc(String exceptDesc)throws OrigApplicationMException;
	public Vector SearchGroupnameByDesc(String groupName)throws OrigApplicationMException;
	public Vector SearchAllBranch()throws OrigApplicationMException;
	public Vector SearchAllGroupname()throws OrigApplicationMException;
	public Vector SearchAllExcept()throws OrigApplicationMException;
	public void saveUserM(UserM userDetailM)throws OrigApplicationMException;
	public String getUserApprovGroup(String userName) throws OrigApplicationMException;
	
}
