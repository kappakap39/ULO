package com.eaf.orig.ulo.pa;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashMap;

import javax.servlet.http.HttpServletRequest;

import org.apache.log4j.Logger;

import com.eaf.core.ulo.common.display.ElementInf;
import com.eaf.core.ulo.common.message.MessageErrorUtil;
import com.eaf.core.ulo.common.model.FieldElement;
import com.eaf.core.ulo.common.properties.ImplementControl;
import com.eaf.core.ulo.common.properties.SystemConstant;
import com.eaf.core.ulo.common.util.FormDisplayValueUtil;
import com.eaf.core.ulo.common.util.FormErrorUtil;
import com.eaf.core.ulo.common.util.Util;
import com.eaf.orig.shared.view.form.ORIGSubForm;
import com.eaf.orig.ulo.constant.MConstant;
import com.eaf.orig.ulo.control.address.util.DisplayAddressUtil;
import com.eaf.orig.ulo.model.app.AddressDataM;
import com.eaf.orig.ulo.model.app.ApplicationGroupDataM;
import com.eaf.orig.ulo.model.app.PersonalInfoDataM;
import com.eaf.orig.ulo.pl.app.view.webaction.AddressUtil;

@SuppressWarnings("serial")
public class MailingAddressPopup extends ORIGSubForm{
	private static transient Logger logger = Logger.getLogger(MailingAddressPopup.class);
	
	private String PERSONAL_TYPE = SystemConstant.getConstant("PERSONAL_TYPE_APPLICANT");
	
	@Override
	public void setProperties(HttpServletRequest request, Object moduleForm) {
		// TODO Auto-generated method stub
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
