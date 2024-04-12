package com.eaf.orig.ulo.app.view.form.manual;

import java.util.HashMap;

import org.apache.log4j.Logger;

import com.eaf.j2ee.pattern.control.FormAction;
import com.eaf.j2ee.pattern.control.FormHelper;
import com.eaf.orig.ulo.app.wmtask.WMTaskDAO;
import com.eaf.orig.ulo.app.wmtask.WMTaskDAOImpl;
import com.eaf.orig.ulo.formcontrol.view.form.ModuleFormHandler;

public class WMTaskViewErrorPopupForm extends FormHelper implements FormAction {
	
	private static transient Logger logger = Logger.getLogger(WMTaskViewErrorPopupForm.class);
	
	@Override
	public Object getObjectForm() {
		String taskId = request.getParameter("taskId");
		WMTaskDAO wmTaskDAO = new WMTaskDAOImpl();
		String errorMessage;
		try {
			errorMessage = wmTaskDAO.getMessage(taskId);
		} catch(Exception e) {
			logger.fatal("ERROR",e);
			return null;
		}
		return errorMessage;
	}

}
