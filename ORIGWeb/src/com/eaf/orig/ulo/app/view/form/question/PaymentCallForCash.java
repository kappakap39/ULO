package com.eaf.orig.ulo.app.view.form.question;

import javax.servlet.http.HttpServletRequest;

import org.apache.log4j.Logger;

import com.eaf.core.ulo.common.display.ElementHelper;
import com.eaf.core.ulo.common.display.ElementInf;
import com.eaf.orig.shared.view.form.ORIGSubForm;
import com.eaf.orig.ulo.app.view.form.subform.product.kec.CashDay1SubForm;
import com.eaf.orig.ulo.formcontrol.view.form.EntityFormHandler;
import com.eaf.orig.ulo.model.app.ApplicationGroupDataM;

public class PaymentCallForCash  extends ElementHelper implements ElementInf {
	private static transient Logger logger = Logger.getLogger(PaymentCashdayOne.class);

	@Override
	public String getElement(HttpServletRequest request, Object objectElement) {
		String personalId = (String)objectElement;
		logger.debug("PaymentCashdayOne personalId >>> "+personalId);
		return "/orig/ulo/subform/question/PaymentCallForCash.jsp";
	}
	
	@Override
	public String getDisplayType() {
		return ElementInf.DISPLAY_JSP;
	}

	@Override
	public String processElement(HttpServletRequest request, Object objectElement) {	
		EntityFormHandler EntityForm = (EntityFormHandler)request.getSession().getAttribute("EntityForm");
		ApplicationGroupDataM applicationGroup = (ApplicationGroupDataM)EntityForm.getObjectForm();
		ORIGSubForm cashDay1SubForm = new CashDay1SubForm();
		cashDay1SubForm.setProperties(request,applicationGroup);
		return null;	
	}
	
}