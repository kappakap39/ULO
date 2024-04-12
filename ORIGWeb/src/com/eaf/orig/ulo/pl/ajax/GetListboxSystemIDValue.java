package com.eaf.orig.ulo.pl.ajax;

import javax.servlet.http.HttpServletRequest;

import com.eaf.orig.shared.servlet.AjaxDisplayGenerateInf;
import com.eaf.orig.shared.utility.ORIGCacheUtil;
import com.eaf.orig.ulo.pl.app.dao.exception.PLOrigApplicationException;
import org.apache.log4j.Logger;


public class GetListboxSystemIDValue implements AjaxDisplayGenerateInf {
	static Logger logger = Logger.getLogger(GetListboxSystemIDValue.class);
	@Override
	public String getDisplayObject(HttpServletRequest request)	throws PLOrigApplicationException{
				
		ORIGCacheUtil origc = ORIGCacheUtil.getInstance();
		
		String choiseValue = request.getParameter("choiseValue");
		String fieldID = request.getParameter("fieldID");
		String systemID = request.getParameter("systemID");
		
		return origc.getSystemIDFromListbox(choiseValue, fieldID, systemID);
	}

}
