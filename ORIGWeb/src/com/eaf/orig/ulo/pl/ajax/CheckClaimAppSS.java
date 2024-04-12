package com.eaf.orig.ulo.pl.ajax;

import javax.servlet.http.HttpServletRequest;

import org.apache.log4j.Logger;

import com.eaf.orig.shared.servlet.AjaxDisplayGenerateInf;
import com.eaf.orig.ulo.pl.app.dao.exception.PLOrigApplicationException;
import com.eaf.orig.ulo.pl.service.PLORIGEJBService;

public class CheckClaimAppSS implements AjaxDisplayGenerateInf {

	private Logger log = Logger.getLogger(this.getClass());
	@Override
	public String getDisplayObject(HttpServletRequest request) throws PLOrigApplicationException {
		
		String accId = request.getParameter("accId");
		int returnStatus = PLORIGEJBService.getORIGDAOUtilLocal().getCardLinkStatus(accId);
		log.debug("returnStatus = "+returnStatus);
		return String.valueOf(returnStatus);
	}

}
