package com.eaf.orig.ulo.pl.app.view.form.subform;

import java.util.HashMap;

import javax.servlet.http.HttpServletRequest;

import org.apache.log4j.Logger;

import com.eaf.orig.shared.view.form.ORIGSubForm;
import com.eaf.orig.ulo.pl.formcontrol.view.form.PLOrigFormHandler;
import com.eaf.orig.ulo.pl.model.app.PLApplicationDataM;

public class ApplicationDetailsSubForm extends ORIGSubForm {
	
	Logger logger = Logger.getLogger(ApplicationDetailsSubForm.class);
	
	@Override
	public void setProperties(HttpServletRequest request, Object appForm) {
		
		PLApplicationDataM plappdataM = PLOrigFormHandler.getPLApplicationDataM(request);
		
		String prDomain = request.getParameter("productdomain");
		String prGroup = request.getParameter("productgroup");
		String prFamily = request.getParameter("productfamily");
		String product = request.getParameter("product");
		String saleType = request.getParameter("saleType");
		String channel = request.getParameter("channel");
		String product_feature = request.getParameter("product_feature");
		
		plappdataM.setProductDomain(prDomain);
		plappdataM.setProductGroup(prGroup);
		plappdataM.setProductFamily(prFamily);
		plappdataM.setProduct(product);
		plappdataM.setSaleType(saleType);
		plappdataM.setApplyChannel(channel);
		plappdataM.setProductFeature(product_feature);

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
