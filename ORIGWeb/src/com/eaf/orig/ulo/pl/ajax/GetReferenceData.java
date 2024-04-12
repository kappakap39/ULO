package com.eaf.orig.ulo.pl.ajax;

import javax.servlet.http.HttpServletRequest;

import org.apache.log4j.Logger;

import com.eaf.orig.cache.properties.ListboxProperties;
import com.eaf.orig.shared.servlet.AjaxDisplayGenerateInf;
import com.eaf.orig.shared.util.OrigUtil;
import com.eaf.orig.shared.utility.ORIGCacheUtil;
import com.eaf.orig.ulo.pl.app.dao.exception.PLOrigApplicationException;
import com.eaf.orig.ulo.pl.app.utility.HTMLRenderUtil;
import com.eaf.orig.ulo.pl.app.utility.JsonObjectUtil;

public class GetReferenceData  implements AjaxDisplayGenerateInf {
	Logger logger = Logger.getLogger(GetGender.class);
	@Override
	public String getDisplayObject(HttpServletRequest request)throws PLOrigApplicationException {
		String textbox_code = request.getParameter("textbox_code");
		String choice_no = request.getParameter("choice_no");	
		
		if(OrigUtil.isEmptyString(choice_no)
				|| OrigUtil.isEmptyString(textbox_code))
			return null;
		
		logger.debug("textbox_code >> "+textbox_code);
		logger.debug("choice_no >> "+choice_no);
		
		JsonObjectUtil jObj = new JsonObjectUtil();
		ListboxProperties listM = null;
		ORIGCacheUtil origCache = new ORIGCacheUtil();	
		if("titleThai".equals(textbox_code)){
			listM = origCache.GetListBoxListboxChoiceNo(choice_no, "3");
			listM = origCache.GetListBoxListboxChoiceNo(listM.getSystemID2(), "4");
			logger.debug("listM.getSystemID1 >> "+listM.getSystemID1());
			if(null != listM && !OrigUtil.isEmptyString(listM.getEnDesc())){
				jObj.CreateJsonObject("titleEng",HTMLRenderUtil.replaceNull(listM.getEnDesc()));
				jObj.CreateJsonObject("title_eng",HTMLRenderUtil.replaceNull(listM.getChoiceNo()));
				jObj.CreateJsonObject("gender",HTMLRenderUtil.replaceNull(listM.getSystemID1()));
			}
		}else if("titleEng".equals(textbox_code)){
			listM = origCache.GetListBoxListboxChoiceNo(choice_no, "4");
			listM = origCache.GetListBoxListboxChoiceNo(listM.getSystemID2(), "3");
			logger.debug("listM.getSystemID1 >> "+listM.getSystemID1());
			if(null != listM && !OrigUtil.isEmptyString(listM.getThDesc())){
				jObj.CreateJsonObject("titleThai",HTMLRenderUtil.replaceNull(listM.getThDesc()));
				jObj.CreateJsonObject("title_thai",HTMLRenderUtil.replaceNull(listM.getChoiceNo()));
				jObj.CreateJsonObject("gender",HTMLRenderUtil.replaceNull(listM.getSystemID1()));
			}
		}
		return jObj.returnJson();
		
	}

}
