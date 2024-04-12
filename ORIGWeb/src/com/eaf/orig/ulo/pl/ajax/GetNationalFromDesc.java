package com.eaf.orig.ulo.pl.ajax;

import javax.servlet.http.HttpServletRequest;

import org.apache.log4j.Logger;

import com.eaf.orig.shared.servlet.AjaxDisplayGenerateInf;
import com.eaf.orig.shared.util.OrigUtil;
import com.eaf.orig.ulo.pl.app.dao.exception.PLOrigApplicationException;
import com.eaf.orig.ulo.pl.app.utility.JsonObjectUtil;
import com.eaf.orig.ulo.pl.service.PLORIGEJBService;
import com.eaf.orig.ulo.pl.util.ejb.ORIGDAOUtilLocal;

public class GetNationalFromDesc implements AjaxDisplayGenerateInf {
	Logger logger = Logger.getLogger(this.getClass());
	@Override
	public String getDisplayObject(HttpServletRequest request)throws PLOrigApplicationException {
		
		String country_desc = request.getParameter("country_desc");
		
		logger.debug("country_desc >> "+country_desc);
		
		if(OrigUtil.isEmptyString(country_desc)){
			return null;
		}		
		
		JsonObjectUtil jObj = new JsonObjectUtil();
		
		try{		
			ORIGDAOUtilLocal origDaoBean =  PLORIGEJBService.getORIGDAOUtilLocal();			
			String choiceNo = origDaoBean.GetChoiceNOFieldIDByDesc("104", country_desc);
			
			logger.debug("ChoiceNo >> "+choiceNo);
			
			if(!OrigUtil.isEmptyString(choiceNo)){
				jObj.CreateJsonObject("country_no", choiceNo);
			}
		
		}catch (Exception e){
			logger.fatal("Exception >> ",e);
		}		
		
		return jObj.returnJson();
		
	}

}
