package com.eaf.orig.ulo.app.view.form.displaymode;

import java.util.ArrayList;

import javax.servlet.http.HttpServletRequest;

import org.apache.log4j.Logger;

import com.eaf.core.ulo.common.display.FormDisplayModeHelper;
import com.eaf.core.ulo.common.display.HtmlUtil;
import com.eaf.core.ulo.common.properties.FormControl;
import com.eaf.core.ulo.common.properties.SystemConstant;
import com.eaf.core.ulo.common.util.Util;
import com.eaf.orig.ulo.model.app.ApplicationDataM;
import com.eaf.orig.ulo.model.app.ApplicationGroupDataM;
//import com.eaf.orig.ulo.model.app.PersonalInfoDataM;

public class IncreasePersonalInfoSubformDisplayMode extends FormDisplayModeHelper{
	private static transient Logger logger = Logger.getLogger(IncreasePersonalInfoSubformDisplayMode.class);
	String PERSONAL_TYPE = SystemConstant.getConstant("PERSONAL_TYPE_APPLICANT");
	String FLIP_TYPE_INC = SystemConstant.getConstant("FLIP_TYPE_INC");
//	String COVERPAGE_TYPE_VETO = SystemConstant.getConstant("COVERPAGE_TYPE_VETO");
	
	@Override
	public String displayMode(Object objectElement, HttpServletRequest request) {
		Object masterObjectForm = FormControl.getMasterObjectForm(request);		
		ApplicationGroupDataM applicationGroup = (ApplicationGroupDataM)masterObjectForm;
		ArrayList<ApplicationDataM> applications = applicationGroup.filterApplicationLifeCycle();
		logger.debug("applications "+applications);
		
		String displayMode = HtmlUtil.VIEW;
		if(Util.empty(applications) || FLIP_TYPE_INC.equals(applicationGroup.getFlipType())) {
			displayMode =  HtmlUtil.EDIT;
		}
		if(applicationGroup.isVeto()){
			displayMode = HtmlUtil.VIEW;
		}
		return displayMode;
	}
}
