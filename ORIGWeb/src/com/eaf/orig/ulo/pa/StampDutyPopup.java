package com.eaf.orig.ulo.pa;

import java.util.HashMap;

import javax.servlet.http.HttpServletRequest;

import org.apache.log4j.Logger;

import com.eaf.orig.shared.view.form.ORIGSubForm;

@SuppressWarnings("serial")
public class StampDutyPopup extends ORIGSubForm{
	private static transient Logger logger = Logger.getLogger(StampDutyPopup.class);
	@Override
	public void setProperties(HttpServletRequest request, Object moduleForm)
	{
		// TODO Auto-generated method stub
		logger.debug("StampDutyPopup - setProperties");
	}

	@Override
	public HashMap<String, Object> validateForm(HttpServletRequest arg0,
			Object arg1) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public boolean validateSubForm(HttpServletRequest arg0) {
		// TODO Auto-generated method stub
		return false;
	}
}
