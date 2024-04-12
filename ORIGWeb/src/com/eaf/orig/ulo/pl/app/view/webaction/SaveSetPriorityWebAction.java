package com.eaf.orig.ulo.pl.app.view.webaction;

import java.util.Vector;
import javax.ejb.EJBException;
import org.apache.log4j.Logger;
import com.eaf.j2ee.pattern.control.FrontController;
import com.eaf.j2ee.pattern.control.event.Event;
import com.eaf.j2ee.pattern.control.event.EventResponse;
import com.eaf.j2ee.pattern.util.ErrorUtil;
import com.eaf.j2ee.pattern.view.webaction.WebAction;
import com.eaf.j2ee.pattern.view.webaction.WebActionHelper;
import com.eaf.orig.master.view.webaction.SaveAppScoreWebAction;
import com.eaf.orig.profile.model.UserDetailM;
import com.eaf.orig.shared.util.OrigUtil;
import com.eaf.orig.ulo.pl.app.ejb.PLORIGApplicationManager;
import com.eaf.orig.ulo.pl.formcontrol.view.form.SearchHandler;
import com.eaf.orig.ulo.pl.model.app.PLApplicationDataM;
import com.eaf.orig.ulo.pl.service.PLORIGEJBService;

public class SaveSetPriorityWebAction extends WebActionHelper implements WebAction {
	
	static Logger log = Logger.getLogger(SaveAppScoreWebAction.class);
	Vector<PLApplicationDataM> appDataVect = new Vector<PLApplicationDataM>();
	UserDetailM userM = new UserDetailM();
	
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
	public boolean preModelRequest() {
		
		String setPriority = getRequest().getParameter("setPriority");
		String[] StringApp = getRequest().getParameterValues("checkAppRecId");
		userM = (UserDetailM) getRequest().getSession().getAttribute("ORIGUser");
		PLORIGApplicationManager manager = PLORIGEJBService.getPLORIGApplicationManager();
		
		SearchHandler handlerM = (SearchHandler) getRequest().getSession().getAttribute("SEARCH_DATAM");
		
		SearchHandler.SearchDataM searchDataM = handlerM.getSearchM();
		if (searchDataM == null) {
			searchDataM = new SearchHandler.SearchDataM();
		}
		handlerM.setErrorFields(null);
		
		try {
			
			if (StringApp != null && StringApp.length > 0) {
				for (int i = 0; i < StringApp.length; i++) {
					String appError = null;
					try {
						PLApplicationDataM appDataM = new PLApplicationDataM();
						String[] appRecIdAppNo = StringApp[i].split("\\|");
						
						appDataM.setAppRecordID(appRecIdAppNo[0]);
						appDataM.setApplicationNo(appRecIdAppNo[1]);
						appDataM.setPriority(setPriority);
						appDataM.setUpdateBy(userM.getUserName());
						appError = manager.updateSetPriority(appDataM, userM);
						
						if (appError != null && appError.length() > 0) {
							handlerM.PushErrorMessage("error", appError);
						}
						
					} catch (EJBException e){
						log.fatal("EJBException = "+e.getMessage());
					} catch (Exception e){
						log.fatal("Exception = "+e.getMessage());
					}
				}
				
			}
			getRequest().getSession().removeAttribute("VALUE_LIST");
		
			if (!OrigUtil.isEmptyObject(handlerM.getErrorFields()) && handlerM.getErrorFields().length()>0){
				throw new Exception();
			}
		} catch (Exception e) {
			log.fatal("Exception SaveSetPriorityWebAction >> ",e);
			SearchHandler.PushErrorMessage(getRequest(), ErrorUtil.getShortErrorMessage(getRequest(), "APP_NO")
					+handlerM.getErrorMessage()+" "
					+ErrorUtil.getShortErrorMessage(getRequest(), "CLAIMED"));
			return false;
		}
		return true;
	}

	@Override
	public int getNextActivityType() {
		return FrontController.ACTION;
	}
	@Override
	public String getNextActionParameter() {
		return "action=SearchSetPriorityWebAction";
	}

	@Override
	protected void doSuccess(EventResponse erp) {
		getRequest().getSession().setAttribute("EventResponse", erp);
		super.doSuccess(erp);
	}

	@Override
	protected void doFail(EventResponse erp) {
		super.doFail(erp);
	}

	@Override
	public boolean getCSRFToken(){
		return true;
	}

}
