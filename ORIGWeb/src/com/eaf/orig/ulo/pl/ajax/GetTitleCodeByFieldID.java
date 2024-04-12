package com.eaf.orig.ulo.pl.ajax;

import javax.servlet.http.HttpServletRequest;

import org.apache.log4j.Logger;

import com.eaf.orig.shared.servlet.AjaxDisplayGenerateInf;
import com.eaf.orig.shared.utility.ORIGCacheUtil;
import com.eaf.orig.shared.utility.ORIGUtility;
import com.eaf.orig.ulo.pl.app.dao.exception.PLOrigApplicationException;
import com.eaf.orig.ulo.pl.app.utility.DataFormatUtility;

public class GetTitleCodeByFieldID implements AjaxDisplayGenerateInf {
	
	Logger logger = Logger.getLogger(GetTitleCodeByFieldID.class);
	
	@Override
	public String getDisplayObject(HttpServletRequest request)throws PLOrigApplicationException {
		String titleValue = request.getParameter("title_val");
		try{
			if(ORIGUtility.isEmptyString(titleValue)){							
				return "NOT_Found";
			}
			ORIGCacheUtil oricCache = new ORIGCacheUtil();
			String fieldID=request.getParameter("field_id");
			logger.debug("title_val= "+titleValue);
			logger.debug("fieldID= "+fieldID);
			String code = oricCache.getORIGTitleCodeCacheDataM(DataFormatUtility.StringToInt(fieldID), titleValue);
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
