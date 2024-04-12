package com.eaf.orig.ulo.pl.app.view.webaction;

import org.apache.log4j.Logger;

import com.eaf.j2ee.pattern.control.FrontController;
import com.eaf.j2ee.pattern.control.event.Event;
import com.eaf.j2ee.pattern.control.event.EventResponse;
import com.eaf.j2ee.pattern.view.webaction.WebAction;
import com.eaf.j2ee.pattern.view.webaction.WebActionHelper;
import com.eaf.orig.shared.constant.OrigConstant;
import com.eaf.orig.ulo.pl.service.PLORIGEJBService;
import com.eaf.orig.ulo.pl.util.ejb.ORIGDAOUtilLocal;

public class ValidateIDWithCardWebAction extends WebActionHelper implements WebAction {
	Logger logger = Logger.getLogger(ValidateIDWithCardWebAction.class);
	@Override
	public Event toEvent(){
		return null;
	}

	@Override
	public boolean requiredModelRequest() {
		String citizenId = getRequest().getParameter("idNo");
		String cardNo = getRequest().getParameter("cardNo");
		
		logger.debug("ValidateIDWithCard()..idNo >> "+citizenId);
		logger.debug("ValidateIDWithCard()..cardNo >> "+cardNo);
		
		try{			
			ORIGDAOUtilLocal origBean = PLORIGEJBService.getORIGDAOUtilLocal();
			boolean isMatch = origBean.isCitizenRelateCardNo(citizenId, cardNo);
			logger.debug("ValidateIDWithCard()..match >> "+isMatch);
			if(isMatch){				
				this.getResponse().getWriter().write(OrigConstant.ORIG_RESULT_PASS);
			}else{
				this.getResponse().getWriter().write(OrigConstant.ORIG_RESULT_FAIL);
			}			
		}catch(Exception e){
			logger.error("ERROR "+e.getMessage());
		}
		return true;
	}

	@Override
	public boolean processEventResponse(EventResponse response) {
		return false;
	}

	@Override
	public boolean preModelRequest(){
		return false;
	}

	@Override
	public int getNextActivityType() {
		return FrontController.NOTFORWARD;
	}

	@Override
	public boolean getCSRFToken(){
		return false;
	}

}
