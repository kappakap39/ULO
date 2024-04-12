package com.eaf.orig.ulo.pl.ajax;

import javax.servlet.http.HttpServletRequest;

import org.apache.log4j.Logger;

import com.eaf.orig.profile.model.UserDetailM;
import com.eaf.orig.shared.servlet.AjaxDisplayGenerateInf;
import com.eaf.orig.shared.util.OrigUtil;
import com.eaf.orig.ulo.pl.app.dao.exception.PLOrigApplicationException;
import com.eaf.orig.ulo.pl.app.utility.JsonObjectUtil;
import com.eaf.orig.ulo.pl.app.utility.ORIGLogic;
import com.eaf.orig.ulo.pl.service.PLORIGEJBService;
import com.eaf.orig.ulo.pl.util.ejb.ORIGDAOUtilLocal;

public class GetUserName implements AjaxDisplayGenerateInf {
	
	static Logger logger = Logger.getLogger(GetUserName.class);
	
	@Override
	public String getDisplayObject(HttpServletRequest request)throws PLOrigApplicationException {
		
		String userName = request.getParameter("userName");
		
		UserDetailM userM = (UserDetailM) request.getSession().getAttribute("ORIGUser");
	
		if(null != userName){
			userName = userName.trim();
		}
		
		String role = ORIGLogic.getReassignRole(request,userM);	
				
		logger.debug("UserName >> "+userName);
		logger.debug("Role >> "+role);
		
		ORIGDAOUtilLocal origDaoBean =  PLORIGEJBService.getORIGDAOUtilLocal();
		String userID = origDaoBean.LoadUserName(userName,role);
		logger.debug("userID >> "+userID);
		
		JsonObjectUtil jsonUtil = new JsonObjectUtil();
		if(!OrigUtil.isEmptyString(userID)){
			jsonUtil.CreateJsonObject("reassignTo", userID);
		}
		
		return jsonUtil.returnJson();
	}

}
