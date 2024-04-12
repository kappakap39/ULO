package com.eaf.orig.ulo.pl.ajax;

import javax.servlet.http.HttpServletRequest;

import org.apache.log4j.Logger;

import com.eaf.orig.profile.model.UserDetailM;
import com.eaf.orig.shared.servlet.AjaxDisplayGenerateInf;
import com.eaf.orig.shared.utility.ORIGCacheUtil;
import com.eaf.orig.ulo.pl.app.dao.exception.PLOrigApplicationException;

public class GetTargetApplication implements AjaxDisplayGenerateInf{
	Logger logger = Logger.getLogger(this.getClass());	
	@Override
	public String getDisplayObject(HttpServletRequest request)throws PLOrigApplicationException {
		UserDetailM userM = (UserDetailM) request.getSession().getAttribute("ORIGUser");
		String busClassGroup = request.getParameter("busClassGroup");	
		ORIGCacheUtil cacheUtil = new ORIGCacheUtil();
		String code  = ("TARGET_APP_"+userM.getCurrentRole()).toUpperCase();
		logger.debug("GeneralParam Code >> "+code);
		String targetJob = cacheUtil.getGeneralParamValue(code, busClassGroup);
		logger.debug("targetJob >> "+targetJob);		
		return targetJob;
	}

}
