package com.eaf.orig.ulo.app.view.form.popup.product.kcc;
import java.util.ArrayList;
import java.util.HashMap;
import javax.servlet.http.HttpServletRequest;
import org.apache.log4j.Logger;

import com.eaf.core.ulo.common.properties.SystemConstant;
import com.eaf.core.ulo.common.util.Util;
import com.eaf.orig.shared.view.form.ORIGSubForm;
import com.eaf.orig.ulo.model.app.ApplicationDataM;
import com.eaf.orig.ulo.model.app.ApplicationGroupDataM;
import com.eaf.orig.ulo.model.app.CardDataM;
import com.eaf.orig.ulo.model.app.LoanDataM;


public class MainCreditCardPopup extends ORIGSubForm {
	private static transient Logger logger = Logger.getLogger(MainCreditCardPopup.class);

	@Override
	public void setProperties(HttpServletRequest request, Object appForm) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public HashMap validateForm(HttpServletRequest request, Object appForm) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public boolean validateSubForm(HttpServletRequest request) {
		// TODO Auto-generated method stub
		return false;
	}

}
