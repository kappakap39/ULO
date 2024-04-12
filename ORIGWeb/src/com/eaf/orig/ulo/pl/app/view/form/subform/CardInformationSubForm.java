package com.eaf.orig.ulo.pl.app.view.form.subform;

import java.util.HashMap;
import javax.servlet.http.HttpServletRequest;
import org.apache.log4j.Logger;
import com.eaf.orig.shared.constant.OrigConstant;
import com.eaf.orig.shared.view.form.ORIGSubForm;
import com.eaf.orig.ulo.pl.formcontrol.view.form.PLOrigFormHandler;
import com.eaf.orig.ulo.pl.model.app.PLApplicationDataM;
import com.eaf.orig.ulo.pl.model.app.PLCardDataM;
import com.eaf.orig.ulo.pl.model.app.PLPersonalInfoDataM;

public class CardInformationSubForm extends ORIGSubForm {
	Logger logger = Logger.getLogger(CardInformationSubForm.class);
	@Override
	public void setProperties(HttpServletRequest request, Object appForm) {
		PLOrigFormHandler formHandler = (PLOrigFormHandler) request.getSession().getAttribute(OrigConstant.PL_FORMHANDLER_NAME);
		PLApplicationDataM  applicationM = formHandler.getAppForm();
		
		PLPersonalInfoDataM personalM = applicationM.getPLPersonalInfoDataM(OrigConstant.PERSONAL_TYPE_APPLICANT);
		PLCardDataM cardM = personalM.getCardInformation();
		if (cardM == null) {
			cardM = new PLCardDataM();
			personalM.setCardInformation(cardM);
		}
		String card_info_card_id = request.getParameter("card_info_card_id");
		String card_info_card_name = request.getParameter("card_info_card_name");
		String card_info_card_type = request.getParameter("card_info_card_type");
		String card_info_card_face = request.getParameter("card_info_card_face");
		
//		logger.debug("card_info_card_id=" + card_info_card_id);
//		logger.debug("card_info_card_id=" + card_info_card_name);
//		logger.debug("card_info_card_type=" + card_info_card_type);
//		logger.debug("card_info_card_face=" + card_info_card_face);
		
		cardM.setCardNo(card_info_card_id);
		cardM.setCardFace(card_info_card_face);
		cardM.setCardType(card_info_card_type);
		cardM.setEmbossName(card_info_card_name);	       
	}

	@Override
	public HashMap validateForm(HttpServletRequest request, Object appForm) {
		return null;
	}

	@Override
	public boolean validateSubForm(HttpServletRequest request) {
		return false;
	}

}
