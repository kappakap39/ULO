package com.eaf.orig.logon;

import javax.servlet.http.HttpServletRequest;

import com.eaf.core.ulo.common.util.Util;
import com.eaf.orig.profile.model.UserDetailM;

public class LogonEngine {
	public static boolean auth(HttpServletRequest request){
		UserDetailM userM = (UserDetailM) request.getSession().getAttribute("ORIGUser");
		return (null != userM && !Util.empty(userM.getUserName()));
	}
}
