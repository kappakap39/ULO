package com.eaf.orig.ulo.pl.app.view.form.subform;

import java.util.HashMap;
import javax.servlet.http.HttpServletRequest;
import org.apache.log4j.Logger;
import com.eaf.orig.profile.model.UserDetailM;
import com.eaf.orig.shared.view.form.ORIGSubForm;
import com.eaf.orig.ulo.pl.formcontrol.view.form.PLOrigFormHandler;
import com.eaf.orig.ulo.pl.model.app.PLApplicationDataM;
import com.eaf.orig.ulo.pl.model.app.PLReferencePersonDataM;


public class ReferencePersonalSubForm extends ORIGSubForm {
	Logger logger = Logger.getLogger(ReferencePersonalSubForm.class);
	@Override
	public void setProperties(HttpServletRequest request, Object appForm) {
		// TODO Auto-generated method stub
		PLOrigFormHandler origForm = (PLOrigFormHandler)request.getSession().getAttribute("PLORIGForm");
		
		UserDetailM userM =	(UserDetailM) request.getSession().getAttribute("ORIGUser");
		
		PLApplicationDataM applicationM = origForm.getAppForm();
		PLReferencePersonDataM referenceM = applicationM.getReferencePerson();
		
		if(null == referenceM){
			referenceM = new PLReferencePersonDataM();
			applicationM.setReferencePerson(referenceM);
		}
		
		String title_thai = request.getParameter("r_title_thai");
		String name_th = request.getParameter("r_name_th");
		String surname_th = request.getParameter("r_surname_th");
		String relation = request.getParameter("r_relation");
		String cell_phone = request.getParameter("r_cell_phone");
		String office_tel = request.getParameter("r_office_tel");
		String ext_tel = request.getParameter("ext_tel");
		String home_tel = request.getParameter("r_home_tel");
				
		logger.debug("++++++++++title_thai = "+ title_thai);
		
		referenceM.setThaiTitleCode(title_thai);
		referenceM.setThaiFirstName(name_th);
		referenceM.setThaiLastName(surname_th);
		referenceM.setRelation(relation);
		referenceM.setMobile(cell_phone);
		referenceM.setPhone(home_tel);
		referenceM.setPhone2(office_tel);
		referenceM.setPhoneExt2(ext_tel);
		referenceM.setCreateBy(userM.getUserName());
		referenceM.setUpdateBy(userM.getUserName());
		
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
