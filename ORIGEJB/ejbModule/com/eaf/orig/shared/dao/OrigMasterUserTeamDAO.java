/*
 * Created on Nov 28, 2007
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

import com.eaf.orig.master.shared.model.UserTeamM;
import com.eaf.orig.shared.dao.exceptions.OrigApplicationMException;

/**
 * @author Administrator
 *
 * Type: OrigMasterUserTeamDAO
 */
public interface OrigMasterUserTeamDAO {
	public Vector getAllUserName()throws OrigApplicationMException;
	public void createModelOrigMasterUserTeamM(UserTeamM userTeamM)throws OrigApplicationMException;
	public UserTeamM selectOrigMasterUserTeamM(String teamEditID)throws OrigApplicationMException;
	public double updateOrigMasterUserTeamM(UserTeamM userTeamM)throws OrigApplicationMException;
	public void deleteOrigMasterUserTeam(String[] userTeamIdToDelete)throws OrigApplicationMException;
	public Vector getAllUserNameCMR() throws OrigApplicationMException;
}
