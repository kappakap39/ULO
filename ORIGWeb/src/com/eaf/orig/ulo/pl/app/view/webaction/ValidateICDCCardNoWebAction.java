package com.eaf.orig.ulo.pl.app.view.webaction;

import org.apache.log4j.Logger;
import com.eaf.j2ee.pattern.control.FrontController;
import com.eaf.j2ee.pattern.control.event.Event;
import com.eaf.j2ee.pattern.control.event.EventResponse;
import com.eaf.j2ee.pattern.view.webaction.WebAction;
import com.eaf.j2ee.pattern.view.webaction.WebActionHelper;
import com.eaf.orig.shared.util.OrigUtil;
import com.eaf.orig.ulo.pl.app.utility.JsonObjectUtil;
import com.eaf.orig.ulo.pl.model.app.PLAccountCardDataM;
import com.eaf.orig.ulo.pl.service.PLORIGEJBService;
import com.eaf.orig.ulo.pl.util.ejb.ORIGDAOUtilLocal;

public class ValidateICDCCardNoWebAction extends WebActionHelper implements WebAction {
	Logger logger = Logger.getLogger(this.getClass().getName());
	@Override
	public Event toEvent(){
		return null;
	}

	@Override
	public boolean requiredModelRequest(){
		return false;
	}

	@Override
	public boolean processEventResponse(EventResponse response){
		return false;
	}

	@Override
	public boolean preModelRequest() {
		String cardNo = getRequest().getParameter("card_info_card_no");
		logger.debug("Card No >> "+cardNo);
		PLAccountCardDataM accCardM = null;
		String result = "Fail";
		try{
			ORIGDAOUtilLocal origBean = PLORIGEJBService.getORIGDAOUtilLocal();
			accCardM = origBean.loadAccountCardByCardNo(cardNo);
			if(null != accCardM && !OrigUtil.isEmptyString(accCardM.getAccountCardId())){		
				result ="Success";
			}
		}catch(Exception e){
			logger.fatal("Exception ",e);
		}
		try{
			JsonObjectUtil json = new JsonObjectUtil();
				json.CreateJsonObject("result",result);		
				json.ResponseJsonArray(getResponse());
		}catch(Exception e){
			logger.fatal("Exception ",e);
		}
		return true;
	}

	@Override
	public int getNextActivityType(){
		return FrontController.NOTFORWARD;
	}

	@Override
	public boolean getCSRFToken(){
		return false;
	}

}
