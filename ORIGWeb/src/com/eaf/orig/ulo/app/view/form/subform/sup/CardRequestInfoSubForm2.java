package com.eaf.orig.ulo.app.view.form.subform.sup;

//import java.math.BigDecimal;
//import java.util.ArrayList;
import java.util.HashMap;

import javax.servlet.http.HttpServletRequest;

//import org.apache.log4j.Logger;

//import com.eaf.core.ulo.common.properties.SystemConstant;
import com.eaf.core.ulo.common.util.FormErrorUtil;
//import com.eaf.core.ulo.common.util.Util;
import com.eaf.orig.shared.view.form.ORIGSubForm;
//import com.eaf.orig.ulo.model.app.ApplicationDataM;
//import com.eaf.orig.ulo.model.app.ApplicationGroupDataM;
//import com.eaf.orig.ulo.model.app.CardDataM;
//import com.eaf.orig.ulo.model.app.LoanDataM;
//import com.eaf.orig.ulo.model.app.PersonalInfoDataM;

@SuppressWarnings("serial")
public class CardRequestInfoSubForm2 extends ORIGSubForm{
//	private static transient Logger logger = Logger.getLogger(CardRequestInfoSubForm2.class);
//	private String PERSONAL_TYPE_APPLICANT =SystemConstant.getConstant("PERSONAL_TYPE_APPLICANT");
	@Override
	public void setProperties(HttpServletRequest request, Object appForm) {
//String cardNo = request.getParameter("CARD_NO");
//		
//		ApplicationGroupDataM applicationGroup = ((ApplicationGroupDataM)appForm);
//		ArrayList<ApplicationDataM> appListM=	applicationGroup.filterApplicationLifeCycle();
//		if(Util.empty(appListM)){
//			appListM  = new ArrayList<ApplicationDataM>();
//
//		}
//		for(ApplicationDataM appM :appListM){
//				ArrayList<LoanDataM> loanListM=	appM.getLoans();
//					for(LoanDataM loanM:loanListM){
//						CardDataM cardM = loanM.getCard();
//						if(Util.empty(cardM)){
//							 cardM = new CardDataM();
//							 loanM.setCard(cardM);
//						}
//						cardM.setCardNo(cardNo);
//					}
//		}	
//		
	}

	@SuppressWarnings({ "rawtypes", "unchecked" })
	@Override
	public HashMap validateForm(HttpServletRequest request, Object appForm) {
//		ApplicationGroupDataM applicationGroup = ((ApplicationGroupDataM)appForm);
//		PersonalInfoDataM personalInfo = applicationGroup.getPersonalInfo(PERSONAL_TYPE_APPLICANT);
		FormErrorUtil formError = new FormErrorUtil();
//		String subformId = "SUP_CARD_REQUEST_INFO_SUBFROM_2";
//		logger.debug("subformId >> "+subformId);		
//		formError.mandatory(subformId,"RELATION_WITH_APPLICANT",personalInfo.getrela,request);		
		return formError.getFormError();
	}

	@Override
	public boolean validateSubForm(HttpServletRequest request) {
		return false;
	}

}
