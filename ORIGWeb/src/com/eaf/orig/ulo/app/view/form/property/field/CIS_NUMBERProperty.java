package com.eaf.orig.ulo.app.view.form.property.field;

import javax.servlet.http.HttpServletRequest;

import org.apache.log4j.Logger;

import com.eaf.core.ulo.common.display.FieldPropertyHelper;
import com.eaf.core.ulo.common.display.HtmlUtil;

public class CIS_NUMBERProperty extends FieldPropertyHelper {
	private static transient Logger logger = Logger.getLogger(CIS_NUMBERProperty.class);
	@Override
	public boolean invokeDisplayMode() {
		return true;
	}
	@Override
	public String displayMode(Object objectElement, HttpServletRequest request){
		return HtmlUtil.VIEW;
	}
}
