package com.eaf.orig.ulo.pl.app.view.form.subform;

import java.util.HashMap;

import javax.servlet.http.HttpServletRequest;
import com.eaf.orig.shared.view.form.ORIGSubForm;
import com.eaf.orig.ulo.pl.constant.WorkflowConstant;
import com.eaf.orig.ulo.pl.formcontrol.view.form.PLOrigFormHandler;
import com.eaf.orig.ulo.pl.model.app.PLApplicationDataM;
import org.apache.log4j.Logger;

public class DFKycInfoSubForm extends ORIGSubForm {
	private Logger logger = Logger.getLogger(this.getClass());
	@Override
	public void setProperties(HttpServletRequest request, Object appForm) {
		// TODO Auto-generated method stub
		PLOrigFormHandler plorigform = (PLOrigFormHandler)request.getSession().getAttribute("PLORIGForm");
		PLApplicationDataM plappdataM = plorigform.getAppForm();
		
		String df_decision = request.getParameter("df_decesion");
		logger.debug("df_decesion= "+df_decision);
		if(df_decision!=null&& df_decision.equalsIgnoreCase("on")){
			plappdataM.setDF_Decision(WorkflowConstant.Action.SUBMIT_APPROVED_APPLICATION);			
		}
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
