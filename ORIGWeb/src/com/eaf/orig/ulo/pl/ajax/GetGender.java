package com.eaf.orig.ulo.pl.ajax;

import javax.servlet.http.HttpServletRequest;

import org.apache.log4j.Logger;

import com.eaf.orig.shared.servlet.AjaxDisplayGenerateInf;
import com.eaf.orig.shared.utility.ORIGCacheUtil;
import com.eaf.orig.ulo.pl.app.dao.exception.PLOrigApplicationException;

public class GetGender implements AjaxDisplayGenerateInf {
	Logger log = Logger.getLogger(GetGender.class);
	@Override
	public String getDisplayObject(HttpServletRequest request)	throws PLOrigApplicationException {
		// TODO Auto-generated method stub
		String CacheName = request.getParameter("CacheName");
		String titleCode = request.getParameter("title_thai");
		String fieldID = request.getParameter("fieldID");
		log.debug("CacheName= "+CacheName);
		log.debug("titleCode= "+titleCode);
		log.debug("fieldID= "+fieldID);
		if(titleCode!=null){
			ORIGCacheUtil origc = ORIGCacheUtil.getInstance();
			String result = origc.getGenderChoiseDataM(CacheName, titleCode, fieldID);
			log.debug("result= "+result);
			return result;
		}
		
		return null;
	}

}
