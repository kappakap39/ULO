package com.eaf.orig.ulo.app.view.form.displaymode;

import java.util.ArrayList;

import javax.servlet.http.HttpServletRequest;

import org.apache.log4j.Logger;

import com.eaf.core.ulo.common.display.FormDisplayModeHelper;
import com.eaf.core.ulo.common.display.HtmlUtil;
import com.eaf.core.ulo.common.properties.FormControl;
import com.eaf.core.ulo.common.properties.SystemConstant;
import com.eaf.orig.ulo.formcontrol.view.form.ORIGFormHandler;
import com.eaf.orig.ulo.model.app.ApplicationDataM;
import com.eaf.orig.ulo.model.app.ApplicationGroupDataM;
import com.eaf.orig.ulo.model.compare.CompareDataM;

public class DE1_1IncreaseBIRTH_DATEDisplayMode extends FormDisplayModeHelper{
	private static transient Logger logger = Logger.getLogger(IncreaseIdNoDisplayMode.class);
	String FLIP_TYPE_INC = SystemConstant.getConstant("FLIP_TYPE_INC");
	
	@Override
	public String displayMode(Object objectElement, HttpServletRequest request) {
		logger.debug("IdNoDisplayMode.displayMode");
		ApplicationGroupDataM applicationGroup = ORIGFormHandler.getObjectForm(request);
		if(applicationGroup==null){
			applicationGroup= (ApplicationGroupDataM)FormControl.getObjectForm(request);		
		}
		boolean existSrcOfDataCis = applicationGroup.existSrcOfDataNotRemove(CompareDataM.SoruceOfData.CIS);
		if(null == applicationGroup){
			applicationGroup = new ApplicationGroupDataM();
		}
		ArrayList<ApplicationDataM> applications = applicationGroup.filterApplicationLifeCycle();
		String displayMode = HtmlUtil.EDIT;
		logger.debug("IS_VETO : "+applicationGroup.isVeto());
		logger.debug("existSrcOfDataCis : "+existSrcOfDataCis);
//		logger.debug("CoverpageType "+applicationGroup.getCoverpageType());
		logger.debug("applications.size() "+applications.size());
		logger.debug("applicationGroup.getFlipType()"+applicationGroup.getFlipType());
		if(applicationGroup.isVeto() || existSrcOfDataCis || applications.size() > 0){
			displayMode = HtmlUtil.VIEW;
		}
		return displayMode;
	}
}
