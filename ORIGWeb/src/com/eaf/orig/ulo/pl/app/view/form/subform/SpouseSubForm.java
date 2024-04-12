package com.eaf.orig.ulo.pl.app.view.form.subform;

import java.util.HashMap;
import javax.servlet.http.HttpServletRequest;
import com.eaf.orig.shared.view.form.ORIGSubForm;
import com.eaf.orig.ulo.pl.app.utility.DataFormatUtility;
import com.eaf.orig.ulo.pl.formcontrol.view.form.PLOrigFormHandler;
import com.eaf.orig.ulo.pl.model.app.PLApplicationDataM;
import com.eaf.orig.ulo.pl.model.app.PLPersonalInfoDataM;

public class SpouseSubForm extends ORIGSubForm {

	@Override
	public void setProperties(HttpServletRequest request, Object appForm) {
		// TODO Auto-generated method stub
		PLOrigFormHandler plorigform = (PLOrigFormHandler)request.getSession().getAttribute("PLORIGForm");
		PLApplicationDataM plappdataM = plorigform.getAppForm();
		PLPersonalInfoDataM plpersonalinfodatam = plappdataM.getPLPersonalInfoDataM(PLPersonalInfoDataM.PersonalType.PERSONAL_TYPE_APPLICANT);
		
		
		String Mtitlethai = request.getParameter("m_title_thai");
		String MnameThai = request.getParameter("m_name_th");
		String Msurnamethai = request.getParameter("m_surname_th");
		String Mold_surname_th = request.getParameter("m_old_surname_th");
		String Mworkplace = request.getParameter("m_workplace");
		String Mposition = request.getParameter("m_position");
		String Msalary = request.getParameter("m_salary");
		String Moffice_tel = request.getParameter("m_office_tel");
		String Moffice_tel_ext = request.getParameter("m_ext_tel");
		String Mhome_tel = request.getParameter("m_home_tel");
		String Mcell_phone = request.getParameter("m_cell_phone");
		
		plpersonalinfodatam.setMThaiTitleName(Mtitlethai);
		plpersonalinfodatam.setMThaiFirstName(MnameThai);
		plpersonalinfodatam.setMThaiLastName(Msurnamethai);
		plpersonalinfodatam.setmThaiOldLastName(Mold_surname_th);
		plpersonalinfodatam.setMCompany(Mworkplace);
		plpersonalinfodatam.setMPosition(Mposition);
		plpersonalinfodatam.setMIncome(DataFormatUtility.replaceCommaForBigDecimal(Msalary));
		plpersonalinfodatam.setmOfficeTel(Moffice_tel);
		plpersonalinfodatam.setmOfficeTelExt(Moffice_tel_ext);
		plpersonalinfodatam.setmHomeTel(Mhome_tel);
		plpersonalinfodatam.setmPhoneNo(Mcell_phone);
	
	
	
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
