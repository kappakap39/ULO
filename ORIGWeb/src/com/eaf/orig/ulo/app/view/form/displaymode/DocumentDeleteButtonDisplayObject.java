package com.eaf.orig.ulo.app.view.form.displaymode;

import org.apache.log4j.Logger;

import com.eaf.core.ulo.common.display.FormDisplayObjectHelper;
import com.eaf.core.ulo.common.display.HtmlUtil;

public class DocumentDeleteButtonDisplayObject extends FormDisplayObjectHelper {
	
	private static transient Logger logger = Logger.getLogger(DocumentDeleteButtonDisplayObject.class);

	@Override
	public String getObjectFormMode(String objectId, String objectType, Object objectElement) {
		String displayMode = HtmlUtil.EDIT;
		return displayMode;
	}
	
}
