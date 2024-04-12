package com.eaf.orig.ulo.app.view.form.displaymode;

import javax.servlet.http.HttpServletRequest;

import org.apache.log4j.Logger;

import com.eaf.core.ulo.common.display.FormDisplayModeHelper;
import com.eaf.core.ulo.common.display.HtmlUtil;
import com.eaf.core.ulo.common.properties.FormControl;
import com.eaf.orig.ulo.formcontrol.view.form.ORIGFormHandler;
import com.eaf.orig.ulo.model.app.ApplicationGroupDataM;
import com.eaf.orig.ulo.model.compare.CompareDataM;

public class IAPersonalInfoSubformBIRT_DATEDisplayMode extends FormDisplayModeHelper{
	private static transient Logger logger = Logger.getLogger(IAPersonalInfoSubformBIRT_DATEDisplayMode.class);
	@Override
	public String displayMode(Object objectElement, HttpServletRequest request) {
		logger.debug("IAPersonalInfoSubformDisplayMode .....");
		ApplicationGroupDataM applicationGroup = ORIGFormHandler.getObjectForm(request);
		logger.debug("applicationGroup ....."+applicationGroup);
		if(applicationGroup==null){
			applicationGroup= (ApplicationGroupDataM)FormControl.getObjectForm(request);		
		}
		boolean existSrcOfDataCis = applicationGroup.existSrcOfDataNotRemove(CompareDataM.SoruceOfData.CIS);

		String displayMode = HtmlUtil.EDIT;
		logger.debug("IS_VETO : "+applicationGroup.isVeto());
		logger.debug("existSrcOfDataCis : "+existSrcOfDataCis);

		if(applicationGroup.isVeto()  || existSrcOfDataCis){
			displayMode =  HtmlUtil.VIEW;
		}
		return displayMode;
	}
}
