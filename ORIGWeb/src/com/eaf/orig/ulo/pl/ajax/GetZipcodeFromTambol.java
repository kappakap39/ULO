package com.eaf.orig.ulo.pl.ajax;

import javax.servlet.http.HttpServletRequest;

import com.eaf.orig.shared.servlet.AjaxDisplayGenerateInf;
//import com.eaf.orig.ulo.pl.app.dao.PLOrigZipcodeDAO;
//import com.eaf.orig.ulo.pl.app.dao.PLOrigZipcodeDAOImpl;
import com.eaf.orig.ulo.pl.app.dao.exception.PLOrigApplicationException;
import com.eaf.orig.ulo.pl.service.PLORIGEJBService;
import com.eaf.orig.ulo.pl.util.ejb.ORIGDAOUtilLocal;

public class GetZipcodeFromTambol implements AjaxDisplayGenerateInf{
	@Override
	public String getDisplayObject(HttpServletRequest request)	throws PLOrigApplicationException{		
		String Tambol = request.getParameter("tambol");
		ORIGDAOUtilLocal origDaoBean =  PLORIGEJBService.getORIGDAOUtilLocal();		
		return  origDaoBean.Zipcode(Tambol);
	}
}
