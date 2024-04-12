package com.eaf.orig.ulo.pl.app.view.webaction;

import java.util.Vector;

import org.apache.log4j.Logger;
import com.eaf.j2ee.pattern.control.FrontController;
import com.eaf.j2ee.pattern.control.event.Event;
import com.eaf.j2ee.pattern.control.event.EventResponse;
import com.eaf.j2ee.pattern.util.ErrorUtil;
import com.eaf.j2ee.pattern.view.webaction.WebAction;
import com.eaf.j2ee.pattern.view.webaction.WebActionHelper;
import com.eaf.orig.profile.model.UserDetailM;
import com.eaf.orig.shared.constant.OrigConstant;
import com.eaf.orig.shared.dao.utility.UtilityDAO;
import com.eaf.orig.shared.dao.utility.UtilityDAOImpl;
import com.eaf.orig.shared.model.ProcessMenuM;
import com.eaf.orig.shared.util.OrigUtil;
import com.eaf.orig.ulo.control.message.Message;
import com.eaf.orig.ulo.pl.app.ejb.PLORIGApplicationManager;
import com.eaf.orig.ulo.pl.formcontrol.view.form.ErrorDataM;
import com.eaf.orig.ulo.pl.formcontrol.view.form.SearchHandler;
import com.eaf.orig.ulo.pl.model.app.ORIGWorkflowDataM;
import com.eaf.orig.ulo.pl.model.app.PLApplicationDataM;
import com.eaf.orig.ulo.pl.service.PLORIGEJBService;
import com.eaf.orig.ulo.pl.util.ejb.ORIGDAOUtilLocal;

public class CBAllocateJobWebAction extends WebActionHelper implements WebAction{
	static Logger logger = Logger.getLogger(CBAllocateJobWebAction.class);
		
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
	public boolean preModelRequest(){
		UserDetailM userM = (UserDetailM) getRequest().getSession().getAttribute("ORIGUser");
		ProcessMenuM menuM = (ProcessMenuM) getRequest().getSession().getAttribute("CurrentMenuM");
		if (menuM == null)menuM = new ProcessMenuM();	
		
		SearchHandler HandlerM = (SearchHandler) getRequest().getSession(true).getAttribute("SEARCH_DATAM");
		if(null == HandlerM){
			HandlerM = new SearchHandler();
		}
		HandlerM.setErrorMSG(null);
		HandlerM.setErrorList(null);
		logger.debug("allocate job username >> "+userM.getUserName());
		logger.debug("tdid >> "+menuM.getMenuID());
		
		ErrorDataM errorM = null;		
		try{
			ORIGDAOUtilLocal origBean = PLORIGEJBService.getORIGDAOUtilLocal();		
			Vector<ORIGWorkflowDataM> jobVect = origBean.getQueueByOwner(userM.getUserName(), menuM.getMenuID());			
			if(!OrigUtil.isEmptyVector(jobVect)){
				PLORIGApplicationManager appBean = PLORIGEJBService.getPLORIGApplicationManager();
				PLApplicationDataM applicationM  = null;
				UtilityDAO utilDAO = new UtilityDAOImpl();	
				for(ORIGWorkflowDataM wfM:jobVect){
					try{						
						logger.debug(wfM.toString());						
						applicationM = this.MapObject(wfM);
						appBean.AllocateApplication(applicationM, userM);						
					}catch(Exception e){	
						String MSG = Message.error(e);
						errorM =  new ErrorDataM(utilDAO.getApplicationNo(applicationM.getAppRecordID()),MSG);
						HandlerM.add(errorM);
					}
				}
				HandlerM.GenarateErrorMessage(getRequest(), ErrorUtil.getShortErrorMessage(getRequest(), "APP_NO"));
			}else{
				logger.warn("not found job in queue!!");
			}
		}catch(Exception e){
			String MSG = Message.error(e);
			SearchHandler.PushErrorMessage(getRequest(), MSG);
			return false;
		}
		return true;
	}
	
	private PLApplicationDataM MapObject(ORIGWorkflowDataM wfM){
		PLApplicationDataM applicationM = new PLApplicationDataM();
			applicationM.setAppRecordID(wfM.getAppRecID());
			applicationM.setJobState(wfM.getJobState());
			applicationM.setApplicationStatus(null);
			applicationM.setOwner(null);
			applicationM.setAppInfo(wfM.getAppInfo());
			applicationM.setPriority(String.valueOf(wfM.getPriority()));
			applicationM.setAppDecision(OrigConstant.Action.REALLOCATE);
			applicationM.setBusinessClassId(wfM.getBusClassID());
		return applicationM;
	}
	
	@Override
	public int getNextActivityType(){
		return FrontController.ACTION;
	}
	
	@Override
	public String getNextActionParameter(){
		return "action=FristPLApp";
	}
		
	@Override
	public boolean getCSRFToken(){
		return false;
	}

}
