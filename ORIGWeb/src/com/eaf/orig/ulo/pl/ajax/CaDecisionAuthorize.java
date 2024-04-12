package com.eaf.orig.ulo.pl.ajax;

import javax.servlet.http.HttpServletRequest;
import org.apache.log4j.Logger;
import com.eaf.orig.profile.model.UserDetailM;
import com.eaf.orig.shared.constant.OrigConstant;
import com.eaf.orig.shared.dao.utility.OrigApplicationUtil;
import com.eaf.orig.shared.model.ProcessMenuM;
import com.eaf.orig.shared.servlet.AjaxDisplayGenerateInf;
import com.eaf.orig.ulo.pl.app.dao.exception.PLOrigApplicationException;
import com.eaf.orig.ulo.pl.app.utility.HTMLRenderUtil;
import com.eaf.orig.ulo.pl.app.view.form.subform.CADecisionSubForm;
import com.eaf.orig.ulo.pl.formcontrol.view.form.PLOrigFormHandler;
import com.eaf.orig.ulo.pl.model.app.PLApplicationDataM;

public class CaDecisionAuthorize implements AjaxDisplayGenerateInf {
	private Logger logger = Logger.getLogger(this.getClass());
	@Override
	public String getDisplayObject(HttpServletRequest request) throws PLOrigApplicationException {
		String decision = request.getParameter("decision_option");
		String result = "";
		logger.debug("@@@@@ decision:" + decision);
		if(OrigConstant.Action.APPROVE.equals(decision) || OrigConstant.Action.OVERRIDE.equals(decision) 
				|| OrigConstant.Action.ESCALATE.equals(decision) || OrigConstant.Action.POLICY_EXCEPTION.equals(decision)){
			PLOrigFormHandler plOrigForm = (PLOrigFormHandler) request.getSession().getAttribute(PLOrigFormHandler.PLORIGForm);
			UserDetailM userM = (UserDetailM) request.getSession().getAttribute("ORIGUser");
		
			CADecisionSubForm caDecision = new CADecisionSubForm();
			caDecision.setProperties(request, plOrigForm);
			
			PLApplicationDataM applicationM = plOrigForm.getAppForm();
			
			ProcessMenuM menuM = (ProcessMenuM) request.getSession().getAttribute("CurrentMenuM");
			if(menuM == null) menuM = new ProcessMenuM();
			applicationM = OrigApplicationUtil.getInstance().defaultCaDecision(userM, menuM, applicationM);
			logger.debug("@@@@@ default decision :"+ applicationM.getCaDecision());

			//set object to display on screen
			result = applicationM.getCaDecision();
		}
		if(!"".equals(result)){
			return HTMLRenderUtil.displayHTML(result);
		}else{
			return HTMLRenderUtil.displayHTML(decision);
		}
		
	}

}
