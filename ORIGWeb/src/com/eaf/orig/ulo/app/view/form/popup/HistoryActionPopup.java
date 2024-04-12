package com.eaf.orig.ulo.app.view.form.popup;

import java.util.ArrayList;
import java.util.HashMap;

import javax.servlet.http.HttpServletRequest;

import org.apache.log4j.Logger;

import com.eaf.core.ulo.common.util.Util;
import com.eaf.orig.shared.view.form.ORIGSubForm;
import com.eaf.orig.ulo.formcontrol.view.form.ORIGFormHandler;
import com.eaf.orig.ulo.model.app.ApplicationGroupDataM;
import com.eaf.orig.ulo.model.app.ApplicationLogDataM;

public class HistoryActionPopup extends ORIGSubForm{
	private static transient Logger logger = Logger.getLogger(HistoryActionPopup.class);
	@Override
	public void setProperties(HttpServletRequest request, Object appForm) {
		ORIGFormHandler ORIGForm = (ORIGFormHandler)request.getSession().getAttribute(ORIGFormHandler.ORIGForm);
		ApplicationGroupDataM applicationGroup = ORIGForm.getObjectForm();
		ArrayList<ApplicationLogDataM> applicationLogs= applicationGroup.getApplicationLogs();
		
		if(Util.empty(applicationLogs)){
			applicationLogs = new ArrayList<ApplicationLogDataM>();
			applicationGroup.setApplicationLogs(applicationLogs);
		}
		if(!Util.empty(applicationLogs)){
			for(ApplicationLogDataM applicationLog:applicationLogs){
				//
			}
		}
		
		
	}

	@Override
	public HashMap<String,Object> validateForm(
			HttpServletRequest request, Object appForm) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public boolean validateSubForm(HttpServletRequest request) {
		// TODO Auto-generated method stub
		return false;
	}

}
