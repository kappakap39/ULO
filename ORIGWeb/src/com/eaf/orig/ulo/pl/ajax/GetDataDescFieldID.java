package com.eaf.orig.ulo.pl.ajax;

import javax.servlet.http.HttpServletRequest;

import org.apache.log4j.Logger;

import com.eaf.orig.shared.servlet.AjaxDisplayGenerateInf;
import com.eaf.orig.shared.util.OrigUtil;
import com.eaf.orig.ulo.pl.app.dao.exception.PLOrigApplicationException;
import com.eaf.orig.ulo.pl.app.utility.JsonObjectUtil;
import com.eaf.orig.ulo.pl.service.PLORIGEJBService;
import com.eaf.orig.ulo.pl.util.ejb.ORIGDAOUtilLocal;

public class GetDataDescFieldID implements AjaxDisplayGenerateInf{
	Logger log = Logger.getLogger(GetDataDescFieldID.class);
	@Override
	public String getDisplayObject(HttpServletRequest request) throws PLOrigApplicationException {
				
		String textbox_value = request.getParameter("textbox_value");
		
		if(OrigUtil.isEmptyString(textbox_value))
			return null;
		
		String obj_value = request.getParameter("obj_value");
		
		log.debug("textbox_value ... "+textbox_value);	
		
		if(null == obj_value){
			obj_value = "";
		}
		
		String[] object = obj_value.split("\\|");
		
		ORIGDAOUtilLocal origDaoBean =  PLORIGEJBService.getORIGDAOUtilLocal();			
		String choiceNo = origDaoBean.GetChoiceNOFieldIDByDesc(object[0], textbox_value);
		
		log.debug("Choice No ... "+choiceNo);	
		
		JsonObjectUtil jObj = new JsonObjectUtil();		
		if(!OrigUtil.isEmptyString(choiceNo)){
			jObj.CreateJsonObject(object[3], choiceNo);
		}		
		return jObj.returnJson();
	}

}
