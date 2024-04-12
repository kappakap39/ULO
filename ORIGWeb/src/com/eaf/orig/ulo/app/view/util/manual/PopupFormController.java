package com.eaf.orig.ulo.app.view.util.manual;

import javax.servlet.http.HttpServletRequest;

import org.apache.log4j.Logger;

import com.eaf.core.ulo.common.model.ResponseData;
import com.eaf.j2ee.pattern.control.AjaxInf;

public class PopupFormController implements AjaxInf{
	private static transient Logger logger = Logger.getLogger(PopupFormController.class);
	@Override
	public ResponseData processAction(HttpServletRequest request) {
		return null;
	}

}
