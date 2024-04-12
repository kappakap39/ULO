package com.eaf.orig.ulo.pl.app.view.form.subform;

import java.util.HashMap;
import javax.servlet.http.HttpServletRequest;
import com.eaf.orig.shared.constant.OrigConstant;
import com.eaf.orig.shared.util.OrigUtil;
import com.eaf.orig.shared.view.form.ORIGSubForm;
import com.eaf.orig.ulo.pl.app.utility.DataFormatUtility;
import com.eaf.orig.ulo.pl.formcontrol.view.form.PLOrigFormHandler;
import com.eaf.orig.ulo.pl.model.app.PLApplicationDataM;
import com.eaf.orig.ulo.pl.model.app.PLCardDataM;
import com.eaf.orig.ulo.pl.model.app.PLPersonalInfoDataM;

public class CardICDCSubForm extends ORIGSubForm {

	@Override
	public void setProperties(HttpServletRequest request, Object appForm) {
		PLOrigFormHandler plOrigform = (PLOrigFormHandler)request.getSession().getAttribute("PLORIGForm");
		
		PLApplicationDataM applicationM = plOrigform.getAppForm();		
		if(OrigUtil.isEmptyObject(applicationM)){
			applicationM = new PLApplicationDataM();
		}
		
		PLPersonalInfoDataM personalM = applicationM.getPLPersonalInfoDataM(OrigConstant.PERSONAL_TYPE_APPLICANT);
		PLCardDataM cardM = personalM.getCardInformation();

		if (cardM == null) {
			cardM = new PLCardDataM();
			personalM.setCardInformation(cardM);
		}
	      
		String card_info_card_id = request.getParameter("card_info_card_no");
		String card_info_card_name = request.getParameter("card_info_card_name");
		String card_info_card_type = request.getParameter("card_info_card_type");
		String card_info_card_face = request.getParameter("card_info_card_face");
		String card_info_credit_request = request.getParameter("card_info_credit_request");
		String card_info_current_credit = request.getParameter("card_info_current_credit");

		cardM.setCardNo(card_info_card_id);
		cardM.setCardFace(card_info_card_face);
		cardM.setCardType(card_info_card_type);
		cardM.setEmbossName(card_info_card_name);
		if (!OrigUtil.isEmptyString(card_info_credit_request)) {
			cardM.setReqCreditLine(DataFormatUtility.StringToBigDecimal(card_info_credit_request));
		}
		if (!OrigUtil.isEmptyString(card_info_current_credit)) {
			cardM.setCurCreditLine(DataFormatUtility.StringToBigDecimal(card_info_current_credit));
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
