package com.eaf.orig.ulo.pl.ajax;

import javax.servlet.http.HttpServletRequest;

import org.apache.log4j.Logger;

import com.eaf.orig.shared.ejb.ORIGApplicationManager;
import com.eaf.orig.shared.service.ORIGEJBService;
import com.eaf.orig.shared.servlet.AjaxDisplayGenerateInf;
import com.eaf.orig.shared.util.OrigUtil;
import com.eaf.orig.ulo.pl.app.dao.exception.PLOrigApplicationException;

@Deprecated
public class Logout implements AjaxDisplayGenerateInf {
	static Logger logger = Logger.getLogger(Logout.class);
	@Override
	public String getDisplayObject(HttpServletRequest request)throws PLOrigApplicationException {		
		String userName = (String) request.getSession().getAttribute("userName");	
		logger.debug("Logout userName >> "+userName);
		if(!OrigUtil.isEmptyString(userName)){
			ORIGApplicationManager manager = ORIGEJBService.getApplicationManager();
			manager.setUserLogonFlag(userName,"");
			manager.cancleclaimByUserId(userName);
		}
		return null;
	}

}
