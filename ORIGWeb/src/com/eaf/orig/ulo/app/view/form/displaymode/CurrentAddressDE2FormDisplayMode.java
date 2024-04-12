package com.eaf.orig.ulo.app.view.form.displaymode;

import java.util.HashMap;

import javax.servlet.http.HttpServletRequest;

import org.apache.log4j.Logger;

import com.eaf.core.ulo.common.display.HtmlUtil;
import com.eaf.core.ulo.common.properties.SystemConstant;
import com.eaf.core.ulo.common.util.Util;
import com.eaf.orig.ulo.formcontrol.view.form.ModuleFormHandler;
import com.eaf.orig.ulo.formcontrol.view.form.ORIGFormHandler;
import com.eaf.orig.ulo.model.app.AddressDataM;
import com.eaf.orig.ulo.model.app.ApplicationGroupDataM;

public class CurrentAddressDE2FormDisplayMode extends DE2FormDisplayMode{
	private static transient Logger logger = Logger.getLogger(CurrentAddressDE2FormDisplayMode.class);
	private String ADDRESS_FORMAT_MANUAL = SystemConstant.getConstant("ADDRESS_FORMAT_MANUAL");
	private String ADDRESS_FORMAT_AUTO = SystemConstant.getConstant("ADDRESS_FORMAT_AUTO");
	private String ADDRESS_TYPE_CURRENT = SystemConstant.getConstant("ADDRESS_TYPE_CURRENT");
	
	@Override
	public String displayMode(Object objectElement, HttpServletRequest request) {
		ApplicationGroupDataM applicationGroup = ORIGFormHandler.getObjectForm(request);
		if(null == applicationGroup){
			applicationGroup = new ApplicationGroupDataM();
		}
		String displayMode = HtmlUtil.VIEW;
		logger.debug("JobState >> "+applicationGroup.getJobState());
		if(SystemConstant.lookup("JOBSTATE_APPROVE",applicationGroup.getJobState())){
			if(!isHasDataPreviousRole(applicationGroup)){
				displayMode =  HtmlUtil.EDIT;
			}else{
				displayMode =  HtmlUtil.VIEW;
			}
		}else if(SystemConstant.lookup("JOBSTATE_REJECT",applicationGroup.getJobState())){
			if(Util.empty(objectElement)){
				displayMode =  HtmlUtil.EDIT;
			}
		}
		ModuleFormHandler moduleHandler = (ModuleFormHandler) request.getSession().getAttribute("ModuleForm");
		HashMap<String, AddressDataM> hashAddress = (HashMap<String, AddressDataM>)moduleHandler.getObjectForm();
		AddressDataM address = hashAddress.get(ADDRESS_TYPE_CURRENT);
				
		String addressFormat = address.getAddressFormat();
		logger.debug("addressFormat : "+addressFormat);
		if(ADDRESS_FORMAT_AUTO.equals(addressFormat)){
			displayMode =  HtmlUtil.VIEW;
		}
		
		logger.debug("objectElement : "+objectElement);
		logger.debug("displayMode : "+displayMode);
		return displayMode;
	}
}
