package com.eaf.orig.ulo.app.view.util.ajax;

import javax.servlet.http.HttpServletRequest;

import org.apache.log4j.Logger;

import com.eaf.core.ulo.common.model.ResponseData;
import com.eaf.j2ee.pattern.control.AjaxInf;

public class BundleHLDate implements AjaxInf {
	private static transient Logger logger = Logger.getLogger(BundleHLDate.class);
	@Override
	public ResponseData processAction(HttpServletRequest request) {
		String FieldName = request.getParameter("elementFieldId");
		String Date = request.getParameter(FieldName);
		logger.debug("FieldName >>"+ FieldName);
		logger.debug("Date >>"+Date);
		return null;
	}

}
