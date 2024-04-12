package com.eaf.orig.ulo.pl.app.utility;

import javax.servlet.http.HttpServletRequest;

import com.eaf.orig.shared.servlet.AjaxDisplayGenerateInf;
import com.eaf.orig.ulo.pl.app.dao.exception.PLOrigApplicationException;
import com.eaf.orig.ulo.pl.formcontrol.view.form.PLOrigFormHandler;

public class NotificationMessage implements AjaxDisplayGenerateInf{
	@Override
	public String getDisplayObject(HttpServletRequest request)throws PLOrigApplicationException {
		PLOrigFormHandler fromHandler = (PLOrigFormHandler) request.getSession().getAttribute(PLOrigFormHandler.PLORIGForm);
		if(null == fromHandler) 
			return null;
		return fromHandler.GetErrorFields();
	}
	
}
