package com.eaf.orig.ulo.pl.app.view.form.subform;

import java.util.HashMap;
import javax.servlet.http.HttpServletRequest;
import org.apache.log4j.Logger;
import com.eaf.orig.shared.utility.ORIGUtility;
import com.eaf.orig.shared.view.form.ORIGSubForm;
import com.eaf.orig.ulo.pl.formcontrol.view.form.PLOrigFormHandler;
import com.eaf.orig.ulo.pl.model.app.PLApplicationDataM;

public class ProjectSubForm extends ORIGSubForm {	
	Logger logger = Logger.getLogger(ProjectSubForm.class);
	@Override
	public void setProperties(HttpServletRequest request, Object appForm) {

		PLOrigFormHandler origForm = (PLOrigFormHandler)request.getSession().getAttribute("PLORIGForm");
		PLApplicationDataM appM = origForm.getAppForm();
		
		if(null == appM){
			appM = new PLApplicationDataM();
		}
		
		String projectCode = request.getParameter("projectCode");		
		String exception_project = request.getParameter("exception_project");
		String exception_memo = request.getParameter("exception_memo");
		String priority = request.getParameter("Priority");
		
		logger.debug("Project Code ... "+projectCode);
		logger.debug("Priority ... "+priority);
		
			appM.setProjectCode(projectCode);
			appM.setExceptionProject(exception_project);
			appM.setExceptionProjectMemo(exception_memo);
			
		if(ORIGUtility.isEmptyString(priority)){
			appM.setPriority("0");
		}else{
			appM.setPriority(priority);
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
