package com.eaf.orig.ulo.pl.app.view.webaction;

import java.util.Vector;

import org.apache.log4j.Logger;
import com.eaf.j2ee.pattern.control.FrontController;
import com.eaf.j2ee.pattern.control.event.Event;
import com.eaf.j2ee.pattern.control.event.EventResponse;
//import com.eaf.j2ee.pattern.util.ErrorUtil;
import com.eaf.j2ee.pattern.view.webaction.WebAction;
import com.eaf.j2ee.pattern.view.webaction.WebActionHelper;
import com.eaf.orig.shared.constant.OrigConstant;
import com.eaf.orig.shared.util.OrigUtil;
import com.eaf.orig.ulo.control.message.Message;
import com.eaf.orig.ulo.pl.formcontrol.view.form.SearchHandler;
import com.eaf.orig.ulo.pl.model.app.PLAccountCardDataM;
import com.eaf.orig.ulo.pl.model.app.PLAccountDataM;
import com.eaf.orig.ulo.pl.service.PLORIGEJBService;
import com.eaf.orig.ulo.pl.util.ejb.ORIGDAOUtilLocal;

public class LoadCardInfoWebAction extends WebActionHelper implements WebAction {
	
	static Logger log = Logger.getLogger(LoadCardInfoWebAction.class);

	@Override
	public Event toEvent() {
		return null;
	}

	@Override
	public boolean requiredModelRequest() {
		return false;
	}

	@Override
	public boolean processEventResponse(EventResponse response) {
		return false;
	}

	@Override
	public boolean preModelRequest(){
		String accountID = getRequest().getParameter("LaccId");
		try{
			PLAccountDataM accountM = (PLAccountDataM) getRequest().getSession().getAttribute("accountDataM");
			if(accountM == null){
				accountM = new PLAccountDataM();
			}
			ORIGDAOUtilLocal origBean = PLORIGEJBService.getORIGDAOUtilLocal();
			accountM = origBean.loadAccount(accountID);
						
			SearchHandler HandlerM = (SearchHandler) getRequest().getSession().getAttribute("SEARCH_DATAM");
			if(HandlerM == null){
				HandlerM = new SearchHandler();
			}
			SearchHandler.SearchDataM searchDataM = HandlerM.getSearchM();
			if(searchDataM == null){
				searchDataM = new SearchHandler.SearchDataM();
				HandlerM.setSearchM(searchDataM);
			}
			if(OrigUtil.isEmptyString(searchDataM.getProductCode())){
				searchDataM.setProductCode(OrigConstant.PRODUCT_KEC);
			}
			
			Vector<PLAccountCardDataM> accCardVector = accountM.getAccCardVect();
			if(accCardVector == null){
				accCardVector = new Vector<PLAccountCardDataM>();
				accountM.setAccCardVect(accCardVector);
			}
			
			PLAccountCardDataM accCardM = searchDataM.getAccCardDataM();
			if(OrigUtil.isEmptyObject(accCardM)){
				accCardM = new PLAccountCardDataM();
				searchDataM.setAccCardDataM(accCardM);
			}
			
			String cardNo = getRequest().getParameter("LcardNo");
			String cardType = getRequest().getParameter("LcardType");
			String cardFace = getRequest().getParameter("LcardFace");
			String cardRefId = getRequest().getParameter("LcardRefId");
			String cardStatus = getRequest().getParameter("LcardStatus");
			
			accCardM.setCardNo(cardNo);
			accCardM.setCardType(cardType);
			accCardM.setCardFace(cardFace);
			accCardM.setAccountId(accountID);
			searchDataM.setAppRecId(cardRefId);
			accCardM.setCardStatus(cardStatus);

			accCardVector.add(accCardM);
			
			getRequest().getSession().setAttribute("accountDataM", accountM);
			
		}catch(Exception e){
//			log.fatal(e.getLocalizedMessage());
//			SearchHandler.PushErrorMessage(getRequest(), ErrorUtil.getShortErrorMessage(getRequest(),"INTERNAL_SERVER_ERROR"));
			String MSG = Message.error(e);
			SearchHandler.PushErrorMessage(getRequest(), MSG);
			return false;
		}		
		return true;
	}
	
	@Override
	public int getNextActivityType(){
		return FrontController.PAGE;
	}
	
	@Override
	public boolean getCSRFToken(){
		return false;
	}

}
