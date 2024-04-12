package com.eaf.orig.ulo.pl.ajax;

import javax.servlet.http.HttpServletRequest;

import org.apache.log4j.Logger;

import com.eaf.orig.shared.servlet.AjaxDisplayGenerateInf;
import com.eaf.orig.shared.util.OrigUtil;
import com.eaf.orig.ulo.pl.app.dao.exception.PLOrigApplicationException;
import com.eaf.orig.ulo.pl.service.PLORIGEJBService;
import com.eaf.orig.ulo.pl.util.ejb.ORIGDAOUtilLocal;

public class GetAddressDescByCode implements AjaxDisplayGenerateInf {
	Logger logger = Logger.getLogger(GetAddressDescByCode.class);
	@Override
	public String getDisplayObject(HttpServletRequest request) throws PLOrigApplicationException {
		
		String listType = request.getParameter("type");
		String numberValue =  request.getParameter("numberValue");
		String optionalVal = request.getParameter("optionalVal");
		
		if(OrigUtil.isEmptyString(numberValue)||OrigUtil.isEmptyString(listType)){							
			return "NOT_Found";
		}
		if(!OrigUtil.isEmptyString(listType)){
			ORIGDAOUtilLocal dao = PLORIGEJBService.getORIGDAOUtilLocal();
			String result = dao.getAddressDesc(listType, numberValue, optionalVal);
				if(!OrigUtil.isEmptyString(result)){
					return result;
				}else{
					return null;
				}
		}
		return null;
	}
}
