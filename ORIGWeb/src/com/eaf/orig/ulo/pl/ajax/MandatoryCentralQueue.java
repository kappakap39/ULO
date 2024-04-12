package com.eaf.orig.ulo.pl.ajax;

import javax.servlet.http.HttpServletRequest;

import org.apache.log4j.Logger;

import com.eaf.orig.shared.model.ProcessMenuM;
import com.eaf.orig.shared.servlet.AjaxDisplayGenerateInf;
import com.eaf.orig.ulo.pl.app.dao.exception.PLOrigApplicationException;
import com.eaf.orig.ulo.pl.app.utility.JsonObjectUtil;
import com.eaf.orig.ulo.pl.service.PLORIGEJBService;
import com.eaf.orig.ulo.pl.util.ejb.ORIGDAOUtilLocal;

public class MandatoryCentralQueue implements AjaxDisplayGenerateInf{
	static Logger logger = Logger.getLogger(MandatoryCentralQueue.class);
	@Override
	public String getDisplayObject(HttpServletRequest request)throws PLOrigApplicationException{		
		JsonObjectUtil jObj = new JsonObjectUtil();
		try{
			ORIGDAOUtilLocal origBean = PLORIGEJBService.getORIGDAOUtilLocal();
			ProcessMenuM currentMenuM = (ProcessMenuM) request.getSession().getAttribute("CurrentMenuM");
			if(currentMenuM == null) currentMenuM = new ProcessMenuM();
			int total = origBean.GetTotalJobCentralQueue(currentMenuM.getMenuID(), "CQ");
			logger.debug("Total Job CB Central Queue >> "+total);
			jObj.CreateJsonObject("message",String.valueOf(total));
		}catch(Exception e){
			jObj.CreateJsonObject("message","error");
			logger.fatal("Exception ",e);
		}
		return jObj.returnJson();
	}

}
