package com.eaf.orig.ulo.app.view.form.manual;

import java.util.ArrayList;

import org.apache.log4j.Logger;

import com.eaf.j2ee.pattern.control.FormAction;
import com.eaf.j2ee.pattern.control.FormHelper;
import com.eaf.orig.ulo.app.view.util.ajax.CallServiceCBS1215I01;
import com.eaf.orig.ulo.model.app.FixedGuaranteeDataM;

public class CBS1215I01PopupForm extends FormHelper implements FormAction{
	private static transient Logger logger = Logger.getLogger(CBS1215I01PopupForm.class);
	@Override
	public Object getObjectForm() {
		ArrayList<FixedGuaranteeDataM> list = (ArrayList<FixedGuaranteeDataM>)request.getSession().getAttribute(CallServiceCBS1215I01.CBS1215_DATA);
		return list;
	}
	@Override
	public String processForm() {
		return null;
	}
}
