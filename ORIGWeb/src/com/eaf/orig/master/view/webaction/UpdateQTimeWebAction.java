/*
 * Created on Nov 27, 2007
 * Created by Prawit Limwattanachai
 * 
 * Copyright (c) 2007 Avalant Co.,Ltd.
 * 20 North Sathorn Road, 15-16th Floor Bubhajit Bldg., Silom, Bangrak, Bangkok 10500, Thailand
 * All rights reserved.
 *
 * This software is the confidential and prorietary infomation of
 * Avalant Co.,Ltd. ("Confidential Infomation"). You shall not
 * disclose such Confidential Infomation and shall use it only in
 * accordance with the terms of the license agreement you entered into
 * with Avalant Co.,Ltd.
 * 
 */
package com.eaf.orig.master.view.webaction;

import java.util.Vector;

import org.apache.log4j.Logger;

import com.eaf.j2ee.pattern.control.FrontController;
import com.eaf.j2ee.pattern.control.event.Event;
import com.eaf.j2ee.pattern.control.event.EventResponse;
import com.eaf.j2ee.pattern.view.webaction.WebAction;
import com.eaf.j2ee.pattern.view.webaction.WebActionHelper;
import com.eaf.j2ee.system.LoadXML;
import com.eaf.orig.master.formcontrol.view.form.ORIGMasterFormHandler;
import com.eaf.orig.master.shared.model.QTimeOutLoanTypeM;
import com.eaf.orig.master.shared.model.QueueTimeOutM;
import com.eaf.orig.profile.model.UserDetailM;
import com.eaf.orig.shared.control.event.QTimeEvent;
import com.eaf.orig.shared.ejb.ORIGApplicationManager;
import com.eaf.orig.shared.service.ORIGEJBService;
import com.eaf.orig.ulo.pl.service.PLORIGEJBService;
import com.eaf.orig.wf.service.ejb.ORIGWFServiceManager;

/**
 * @author Administrator
 *
 * Type: UpdateQTimeWebAction
 */
public class UpdateQTimeWebAction extends WebActionHelper implements WebAction {
	Logger log = Logger.getLogger(UpdateQTimeWebAction.class);
	QueueTimeOutM qTimeOutM; 
	/* (non-Javadoc)
	 * @see com.eaf.j2ee.pattern.view.webaction.WebAction#toEvent()
	 */
	public Event toEvent() {
		QTimeEvent qTimeEvent = new QTimeEvent();
		
		qTimeEvent.setEventType(QTimeEvent.Q_TIME_UPDATE);
		log.debug("QTimeEvent.Q_TIME_UPDATE=" + QTimeEvent.Q_TIME_UPDATE);
		qTimeEvent.setObject(qTimeOutM);
		log.debug("qTimeOutM=" + qTimeOutM);
		log.debug("qTimeEvent=" + qTimeEvent);
		
		return qTimeEvent;
	}

	/* (non-Javadoc)
	 * @see com.eaf.j2ee.pattern.view.webaction.WebAction#requiredModelRequest()
	 */
	public boolean requiredModelRequest() {

		return true;
	}

	/* (non-Javadoc)
	 * @see com.eaf.j2ee.pattern.view.webaction.WebAction#processEventResponse(com.eaf.j2ee.pattern.control.event.EventResponse)
	 */
	public boolean processEventResponse(EventResponse response) {
		
		return defaultProcessResponse(response);
	}

	/* (non-Javadoc)
	 * @see com.eaf.j2ee.pattern.view.webaction.WebAction#preModelRequest()
	 */
	public boolean preModelRequest() {
		String qTimeID = getRequest().getParameter("qTimeID");
		String qDesc = getRequest().getParameter("qDesc");
		String workTime="";
		String hour = getRequest().getParameter("hour");
		String minute = getRequest().getParameter("minute");
		String second = getRequest().getParameter("second");
		int hour_int=0;
		int minute_int=0;
		int second_int=0;
		if((hour==null || "".equals(hour)) || (minute==null || "".equals(minute)) || (second==null || "".equals(second))){
			//Do Nothing
		}else{
			hour_int = Integer.parseInt(hour);
			minute_int = Integer.parseInt(minute);
			second_int = Integer.parseInt(second);
			
			workTime = second+" "+minute+" "+hour+" "+"?"+" "+"*"+" "+"*";
		}
		
		log.debug("hour ="+hour);
		log.debug("minute ="+minute);
		log.debug("second ="+second);
		
		QueueTimeOutM queueTimeOutM = (QueueTimeOutM)getRequest().getSession().getAttribute("EDIT_Q_TIME_M");
		Vector qTimeLoanTypeMVect = queueTimeOutM.getQTimeOutLoanTypeMVect();
		
		//**** pront log checking qTimeOutLoanTypeMVect
/*		QTimeOutLoanTypeM qTimeOutLoanTypeM;
		System.out.println("qTimeLoanTypeMVect size = " + qTimeLoanTypeMVect.size());
		if(qTimeLoanTypeMVect.size() > 0){ 
			//for(int i =0;i<qTimeLoanTypeMVect.size();i++){ 
			for(int i =qTimeLoanTypeMVect.size()-1;i>=0;i--){ 
				qTimeOutLoanTypeM = (QTimeOutLoanTypeM)qTimeLoanTypeMVect.get(i);
				if(qTimeOutLoanTypeM==null){
					qTimeLoanTypeMVect.remove(i);
				}
				//log.debug("qTimeOutLoanTypeM.getLoanType() ="+qTimeOutLoanTypeM.getLoanType());
				//log.debug("qTimeOutLoanTypeM.getLoanType() ="+qTimeOutLoanTypeM.getExpiry());
				//log.debug("qTimeOutLoanTypeM.getLoanType() ="+qTimeOutLoanTypeM.getReasonCode());
				//log.debug("qTimeOutLoanTypeM.getLoanType() ="+qTimeOutLoanTypeM.getReminder());
			}
		}*/
		
//		**** pront log checking qTimeOutLoanTypeMVect
				QTimeOutLoanTypeM qTimeOutLoanTypeM;
				if(qTimeLoanTypeMVect!=null && qTimeLoanTypeMVect.size() > 0){ 
					log.debug("qTimeLoanTypeMVect.size() ="+qTimeLoanTypeMVect.size());
					for(int i =0;i<qTimeLoanTypeMVect.size();i++){ 
						qTimeOutLoanTypeM = (QTimeOutLoanTypeM)qTimeLoanTypeMVect.get(i);
						
						log.debug("qTimeOutLoanTypeM.getLoanType() ="+qTimeOutLoanTypeM.getLoanType());
						log.debug("qTimeOutLoanTypeM.getLoanType() ="+qTimeOutLoanTypeM.getExpiry());
						log.debug("qTimeOutLoanTypeM.getLoanType() ="+qTimeOutLoanTypeM.getReasonCode());
						log.debug("qTimeOutLoanTypeM.getLoanType() ="+qTimeOutLoanTypeM.getReminder());
					}
				}
		
		UserDetailM userM = (UserDetailM) getRequest().getSession().getAttribute("ORIGUser");
		if(null == userM){
			userM = new UserDetailM();
		}
		String userName = userM.getUserName();
		 
		log.debug("qTimeID ="+qTimeID);
		log.debug("qDesc ="+qDesc);
		log.debug("workTime ="+workTime);
		log.debug("userName ="+userName);
		 		 
		qTimeOutM = new QueueTimeOutM(); 
		 
		qTimeOutM.setQTimeOutID(qTimeID);
		qTimeOutM.setQTimeOutDesc(qDesc);
		qTimeOutM.setWorkingTime(workTime);
		qTimeOutM.setHour(hour);
		qTimeOutM.setMinute(minute);
		qTimeOutM.setSecond(second);
		qTimeOutM.setQTimeOutLoanTypeMVect(qTimeLoanTypeMVect);
		qTimeOutM.setUpdateBy(userName);
		
		ORIGMasterFormHandler origMasterForm = (ORIGMasterFormHandler) getRequest().getSession().getAttribute("ORIGMasterForm");
		origMasterForm.setFormErrors(new Vector());
		if (qTimeOutM.getQTimeOutID()==null || "".equalsIgnoreCase(qTimeOutM.getQTimeOutID())){
			origMasterForm.getFormErrors().add("Queue Timeout ID is required");
		}
		if (qTimeOutM.getQTimeOutDesc()==null || "".equalsIgnoreCase(qTimeOutM.getQTimeOutDesc())){
			origMasterForm.getFormErrors().add("Description is required");
		}
		if (qTimeOutM.getQTimeOutLoanTypeMVect()==null || qTimeOutM.getQTimeOutLoanTypeMVect().size()==0){
			origMasterForm.getFormErrors().add("Queue Timeout State is required");
		}
		if (hour_int>23 || hour_int<0 || minute_int>59 || minute_int<0 || second_int>59 || second_int<0){
			origMasterForm.getFormErrors().add("Invalid Working Time");
		}
		if((hour==null || "".equals(hour)) && (minute==null || "".equals(minute)) && (second==null || "".equals(second))){
			origMasterForm.getFormErrors().add("Working Time is required");
		}else{
			if(hour==null || "".equals(hour)){
				origMasterForm.getFormErrors().add("hour is required");
			}
			if(minute==null || "".equals(minute)){
				origMasterForm.getFormErrors().add("minute is required");
			}
			if(second==null || "".equals(second)){
				origMasterForm.getFormErrors().add("second is required");
			}
		}
		
		if (origMasterForm.getFormErrors()!=null && origMasterForm.getFormErrors().size() > 0){
			getRequest().getSession().setAttribute("EDIT_Q_TIME_M",qTimeOutM);
			return false;
		}else {
			return true;
		}
	}

	public int getNextActivityType() {

		return FrontController.ACTION;
    }
    
    /* (non-Javadoc)
     * @see com.eaf.j2ee.pattern.view.webaction.WebActionHelper#getNextActionParameter()
     */
    public String getNextActionParameter() {
        return "action=SearchQueueTime";
    }
    
    protected void doSuccess(EventResponse arg0) {
		ORIGMasterFormHandler origMasterForm = new ORIGMasterFormHandler();
		getRequest().getSession().setAttribute("ORIGMasterForm",origMasterForm);
		
		getRequest().getSession().removeAttribute("EDIT_Q_TIME_M");
		getRequest().getSession().removeAttribute("LOAN_TYPE_SESSION");
		getRequest().getSession().removeAttribute("DB_CLICK_LOAN_TYPE");
		getRequest().getSession().removeAttribute("QTimeOutLoanTypeM_SESSION");
		
//		***Refresh Cache
//    	log.debug("sorry i'm refreshing Cache.!!!" );
//		com.eaf.cache.TableLookupCache.refreshAll();
		
		/** Set Scheduler **/
    	String providerUrlWF = (String)LoadXML.getServiceURL().get("ORIGWF");
		String jndiWF = (String)LoadXML.getServiceJNDI().get("ORIGWF");
    	
		ORIGApplicationManager appManager = ORIGEJBService.getApplicationManager();
//		SchedulerTask schedulerTask = ORIGEJBService.getSchedulerManager();
//		SchedulerDAO schedulerDAO = ORIGDAOFactory.getSchedulerDAO();
		try{
			appManager.deleteOldSchedulerTask("AutoCancel");
//			String startTime = schedulerDAO.loadWorkingTime("AUTO_CANCEL");
			
			String startTime = PLORIGEJBService.getORIGDAOUtilLocal().loadWorkingTime("AUTO_CANCEL");
			
			ORIGWFServiceManager schedManager = ORIGEJBService.getORIGWFServiceManagerHome(providerUrlWF, jndiWF);
			schedManager.StartScheduler(startTime,-1,1,"AutoCancel");
			log.info("##### Startup Scheduler success #####");
		}catch(Exception e){
			log.error("##### Startup Scheduler error #####",e);
		}
	}

	@Override
	public boolean getCSRFToken() {
		// TODO Auto-generated method stub
		return false;
	}

}
