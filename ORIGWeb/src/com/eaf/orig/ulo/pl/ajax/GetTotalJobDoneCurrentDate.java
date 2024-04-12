package com.eaf.orig.ulo.pl.ajax;

import javax.servlet.http.HttpServletRequest;

import org.apache.log4j.Logger;

import com.eaf.orig.profile.model.UserDetailM;
import com.eaf.orig.shared.model.ProcessMenuM;
import com.eaf.orig.shared.servlet.AjaxDisplayGenerateInf;
import com.eaf.orig.ulo.pl.app.dao.exception.PLOrigApplicationException;
import com.eaf.orig.ulo.pl.service.PLORIGEJBService;
import com.eaf.orig.ulo.pl.util.ejb.ORIGDAOUtilLocal;

public class GetTotalJobDoneCurrentDate implements AjaxDisplayGenerateInf {
	Logger logger = Logger.getLogger(this.getClass());	
	@Override
	public String getDisplayObject(HttpServletRequest request)throws PLOrigApplicationException {
		
		UserDetailM userM = (UserDetailM) request.getSession().getAttribute("ORIGUser");
		ProcessMenuM menuM = (ProcessMenuM) request.getSession().getAttribute("CurrentMenuM");
		if(menuM == null) menuM = new ProcessMenuM();
		try{
			ORIGDAOUtilLocal origDaoBean = PLORIGEJBService.getORIGDAOUtilLocal();
			int totolJob = origDaoBean.GetTotalJobDoneCurrentDate(userM.getUserName(), userM.getCurrentRole(), menuM.getMenuID());
			return String.valueOf(totolJob);
		}catch (Exception e) {
			logger.fatal("Error "+e.getMessage());
		}		
		return "";
	}

}
