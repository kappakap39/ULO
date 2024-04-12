package com.eaf.orig.ulo.app.view.form.question;

import javax.servlet.http.HttpServletRequest;

import org.apache.log4j.Logger;

import com.eaf.core.ulo.common.display.ElementHelper;
import com.eaf.core.ulo.common.display.ElementInf;

public class PaymentMethodSupCC extends ElementHelper implements ElementInf {
	private static transient Logger logger = Logger.getLogger(PaymentMethodSupCC.class);
	@Override
	public String getElement(HttpServletRequest request, Object objectElement) {
		String personalId = (String)objectElement;	
		/*return "/orig/ulo/product/kcc/SupCreditCardSubForm.jsp";*/
		return "";
	}

	@Override
	public String getDisplayType() {
		return ElementInf.DISPLAY_JSP;
	}

	@Override
	public String processElement(HttpServletRequest request,Object objectElement) {
		/*String personalId = (String)objectElement;
		
		EntityFormHandler	EntityForm	=	(EntityFormHandler)request.getSession().getAttribute("EntityForm");
		ApplicationGroupDataM applicationGroup = (ApplicationGroupDataM)EntityForm.getObjectForm();
		ORIGSubForm paymentMethodSubForm = new SupCreditCardSubForm();
		
		ArrayList<String>  personalApplicationInfos = applicationGroup.getPersonalApplication(personalId, PERSONAL_RELATION_APPLICATION_LEVEL);
		if(!Util.empty(personalApplicationInfos)){
			for(String personalApplicationInfo:personalApplicationInfos){
				paymentMethodSubForm.setProperties(request,personalApplicationInfo);
			}
			
		}*/
	
		return null;

	}

}
