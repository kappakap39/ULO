package com.eaf.orig.ulo.pl.ajax;

import javax.servlet.http.HttpServletRequest;

import org.apache.log4j.Logger;

import com.eaf.orig.profile.model.UserDetailM;
import com.eaf.orig.shared.constant.OrigConstant;
import com.eaf.orig.shared.model.ProcessMenuM;
//import com.eaf.orig.shared.dao.PLORIGDAOFactory;
import com.eaf.orig.shared.servlet.AjaxDisplayGenerateInf;
import com.eaf.orig.shared.util.OrigUtil;
//import com.eaf.orig.shared.utility.ORIGUtility;
//import com.eaf.orig.ulo.pl.app.dao.PLOrigCountJobCompleteDAO;
import com.eaf.orig.ulo.pl.app.dao.exception.PLOrigApplicationException;
import com.eaf.orig.ulo.pl.app.utility.WebActionUtil;
import com.eaf.orig.ulo.pl.model.app.TrackingDataM;
import com.eaf.orig.ulo.pl.service.PLORIGEJBService;
import com.eaf.orig.ulo.pl.util.ejb.ORIGDAOUtilLocal;

public class CountJobComplete implements AjaxDisplayGenerateInf {
	static Logger logger = Logger.getLogger(CountJobComplete.class);
	@Override
	public String getDisplayObject(HttpServletRequest request)	throws PLOrigApplicationException{		
		UserDetailM userM =	(UserDetailM) request.getSession().getAttribute("ORIGUser");
		ORIGDAOUtilLocal bean = PLORIGEJBService.getORIGDAOUtilLocal();
//		int count = bean.countJobComplete(userM.getUserName());
//		logger.debug("[CountJobComplete]... "+count);
//		return String.valueOf(count);
		
		String userName = userM.getUserName();
		String role =  userM.getCurrentRole();
		
		String menuID = request.getParameter("MenuID");		
		if (OrigUtil.isEmptyString(menuID)) {
			ProcessMenuM currentMenuM = (ProcessMenuM) request.getSession().getAttribute("CurrentMenuM");
			if(currentMenuM == null) currentMenuM = new ProcessMenuM();;
			menuID = currentMenuM.getMenuID();
		}
		
		if("CA102".equals(menuID)){
			role = OrigConstant.ROLE_I_SUP_CA1;
		}
		
		logger.debug("userName >> "+userName);
		logger.debug("role >> "+role);
		
		TrackingDataM trackM = new TrackingDataM();
			trackM.setUserName(userName);
			trackM.setRole(role);
			trackM.setWfRole(WebActionUtil.getRoleWf(role));
			
		String targetFinish = bean.getTotolJobWorking(trackM);
		
		return targetFinish;
	}

}
