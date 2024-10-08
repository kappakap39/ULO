package com.eaf.orig.ulo.app.view.form.displaymode;

import javax.servlet.http.HttpServletRequest;

import org.apache.log4j.Logger;

import com.eaf.core.ulo.common.display.FormDisplayModeHelper;
import com.eaf.core.ulo.common.display.FormDisplayObjectHelper;
import com.eaf.core.ulo.common.display.HtmlUtil;
import com.eaf.core.ulo.common.properties.SystemConstant;
import com.eaf.core.ulo.common.util.SessionControl;
import com.eaf.core.ulo.common.util.Util;
import com.eaf.orig.ulo.model.control.FlowControlDataM;

public class INCOME_TAWI50DisplayObject extends FormDisplayObjectHelper{
	private static transient Logger logger = Logger.getLogger(INCOME_TAWI50DisplayObject.class);
	private static String ROLE_DE1_2 = SystemConstant.getConstant("ROLE_DE1_2");
	private static String ROLE_DV = SystemConstant.getConstant("ROLE_DV");
	private static String INC_TYPE_MONTHLY_50TAWI = SystemConstant.getConstant("INC_TYPE_MONTHLY_50TAWI");
	@Override
	public String getObjectFormMode(String objectId, String objectType,	Object objectElement) {
		FlowControlDataM flowControl = (FlowControlDataM) request.getSession().getAttribute(SessionControl.FlowControl);
		String chk50Tawi = (String) request.getSession().getAttribute("chk50Tawi_MON");
		String displayMode = HtmlUtil.EDIT;	
		String roleId = flowControl.getRole();
		logger.debug("chk50Tawi >>"+chk50Tawi);
		if((!ROLE_DE1_2.equals(roleId) && !ROLE_DV.equals(roleId)) 
				|| Util.empty(chk50Tawi) 
				|| !INC_TYPE_MONTHLY_50TAWI.equals(chk50Tawi)){
			displayMode = HtmlUtil.VIEW;
		}
		logger.debug("INCOME_TAWI50DisplayMode.displayMode >>"+displayMode);
		return displayMode;
	}
}
