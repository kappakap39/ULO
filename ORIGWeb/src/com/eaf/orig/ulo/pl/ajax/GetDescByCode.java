package com.eaf.orig.ulo.pl.ajax;

import javax.servlet.http.HttpServletRequest;
import org.apache.log4j.Logger;
import com.eaf.orig.shared.servlet.AjaxDisplayGenerateInf;
import com.eaf.orig.shared.utility.ORIGCacheUtil;
import com.eaf.orig.shared.utility.ORIGUtility;
import com.eaf.orig.ulo.pl.app.dao.exception.PLOrigApplicationException;

public class GetDescByCode implements AjaxDisplayGenerateInf {

	Logger logger = Logger.getLogger(this.getClass());
	
	@Override
	public String getDisplayObject(HttpServletRequest request)throws PLOrigApplicationException {
		String titleCode = request.getParameter("title_val");
		String titleTable = request.getParameter("title_table");
		try{
			if(ORIGUtility.isEmptyString(titleCode)){							
				return "NOT_Found";
			}
			ORIGCacheUtil oricCache = new ORIGCacheUtil();
			String code = oricCache.getORIGCacheDisplayNameFormDB(titleCode, titleTable);
			if(ORIGUtility.isEmptyString(code)){
				return "NOT_Found";
			}
			return code;
		
		} catch (Exception e) {
			logger.fatal("Error ",e);
		}		
		return "NOT_Found";	
	}

}
