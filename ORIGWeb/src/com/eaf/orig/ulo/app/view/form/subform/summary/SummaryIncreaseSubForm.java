package com.eaf.orig.ulo.app.view.form.subform.summary;

import java.util.HashMap;

import javax.servlet.http.HttpServletRequest;

import com.eaf.orig.shared.view.form.ORIGSubForm;

public class SummaryIncreaseSubForm extends ORIGSubForm {

@Override
public void setProperties(HttpServletRequest request, Object appForm) {
}

@Override
public HashMap<String,Object> validateForm(
		HttpServletRequest request, Object appForm) {
	return null;
}

@Override
public boolean validateSubForm(HttpServletRequest request) {
	return false;
}

}
