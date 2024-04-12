package com.ava.dynamic.service;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.stereotype.Service;

import com.ava.dynamic.controller.FrontAction;
import com.eaf.core.ulo.common.dao.Factory;
import com.eaf.core.ulo.common.dao.UserDetailDAO;
import com.eaf.core.ulo.common.properties.ApplicationDate;
import com.eaf.orig.profile.model.UserDetailM;

@Service
public class UserDetailService {
	private static final transient Logger logger = LogManager.getLogger(FrontAction.class);
	
	public UserDetailM getWidgetUser(String userName,UserDetailM userM) {
		if(null==userM){
			userM = new UserDetailM();
		}
		userM.setUserName(userName);
		if(null==userM.getLastLogonDate()){
			logger.debug("No lastLogonDate, getting from cache : "+ApplicationDate.getTimestamp());
			userM.setLastLogonDate(ApplicationDate.getTimestamp());
		}
		try {
			UserDetailDAO userDao = Factory.getUserDetailDAO();
			userDao.setUserTeam(userM);
			userDao.setGridId(userM);
			userDao.setAuthGrid(userM);
		} catch (Exception e) {
			logger.fatal("ERROR", e);
		}
		return userM;
	}

}
