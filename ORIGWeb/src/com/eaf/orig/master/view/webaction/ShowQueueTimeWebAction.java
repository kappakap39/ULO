/*
 * Created on Nov 18, 2007
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

import com.eaf.j2ee.pattern.control.event.Event;
import com.eaf.j2ee.pattern.control.event.EventResponse;
import com.eaf.j2ee.pattern.view.webaction.WebAction;
import com.eaf.j2ee.pattern.view.webaction.WebActionHelper;
import com.eaf.orig.master.shared.model.QTimeOutLoanTypeM;
import com.eaf.orig.master.shared.model.QueueTimeOutM;
import com.eaf.orig.shared.control.event.QTimeEvent;

/**
 * @author Administrator
 *
 * Type: ShowQueueTimeWebAction
 */
public class ShowQueueTimeWebAction extends WebActionHelper implements
		WebAction {
	Logger log = Logger.getLogger(ShowQueueTimeWebAction.class);
	QueueTimeOutM qTimeOutMForEdit;
	/* (non-Javadoc)
	 * @see com.eaf.j2ee.pattern.view.webaction.WebAction#toEvent()
	 */
	public Event toEvent() {
		QTimeEvent qTimeEvent = new QTimeEvent();
		
		qTimeEvent.setEventType(QTimeEvent.Q_TIME_SELECT);
		
		log.debug("QTimeEvent.Q_TIME_SELECT=" + QTimeEvent.Q_TIME_SELECT);
		
		qTimeEvent.setObject(qTimeOutMForEdit);
		
		log.debug("qTimeOutMForEdit = " + qTimeOutMForEdit);
		log.debug("qTimeOutMForEdit.getQTimeOutID() = " + qTimeOutMForEdit.getQTimeOutID());
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
	public boolean processEventResponse(EventResponse arg0) {

		return defaultProcessResponse(arg0);
	}

	/* (non-Javadoc)
	 * @see com.eaf.j2ee.pattern.view.webaction.WebAction#preModelRequest()
	 */
	public boolean preModelRequest() {
		String loanType = getRequest().getParameter("loanType");
		String shwAddFrm = getRequest().getParameter("shwAddFrm");
		String firstAdd = getRequest().getParameter("firstAdd");
		String loanTypeDesc="";
		
		if(loanType!=null){
			String[] temp;
			temp = loanType.split(",");
			loanType = temp[0];
			loanTypeDesc =	temp[1];
			
			log.debug("loanTypeDesc From DbClick loanType ="+loanTypeDesc);
		}
		
		log.debug("LoanType From DbClick loanType ="+loanType);
		log.debug("firstAdd ="+firstAdd);
		
		getRequest().getSession().setAttribute("DB_CLICK_LOAN_TYPE",loanTypeDesc);
		getRequest().getSession().setAttribute("LOAN_TYPE_SESSION",loanType);
		
		// *** Get QTimeOutLoanTypeM Data ***
		
		QueueTimeOutM qTimeOutM = new QueueTimeOutM(); 
		QTimeOutLoanTypeM qTimeLoanTypeM = new QTimeOutLoanTypeM();
		Vector QTimeLoanTypeMVect = new Vector();
		
		if("edit".equalsIgnoreCase(shwAddFrm)){
			qTimeOutM = (QueueTimeOutM)getRequest().getSession().getAttribute("EDIT_Q_TIME_M");
		}else{
			qTimeOutM = (QueueTimeOutM)getRequest().getSession().getAttribute("ADD_Q_TIME_M");
		}
		
		if(qTimeOutM == null){
			qTimeOutM = new QueueTimeOutM();
		}else{
			QTimeLoanTypeMVect = qTimeOutM.getQTimeOutLoanTypeMVect();
		}
		
		String qTimeID = getRequest().getParameter("qTimeID");
		String qDesc = getRequest().getParameter("qDesc");
		String hour = getRequest().getParameter("hour");
		String minute = getRequest().getParameter("minute");
		String second = getRequest().getParameter("second");
//		String workTime="";
		
		if(hour==null){
			hour = "";
		}
		
		if(minute==null){
			minute = "";
		}
		
		if(second==null){
			second = "";
		}
		
		log.debug("hour ="+hour);
		log.debug("minute ="+minute);
		log.debug("second ="+second);
		
//		workTime = second+" "+minute+" "+hour+" "+"?"+" "+"*"+" "+"*";
		
		qTimeOutM.setQTimeOutID(qTimeID);
		qTimeOutM.setQTimeOutDesc(qDesc);
//		qTimeOutM.setWorkingTime(workTime);
		qTimeOutM.setHour(hour);
		qTimeOutM.setMinute(minute);
		qTimeOutM.setSecond(second);
		qTimeOutM.setQTimeOutLoanTypeMVect(QTimeLoanTypeMVect);
		
		if(QTimeLoanTypeMVect!=null && QTimeLoanTypeMVect.size()>0 && loanType!=null){
			boolean haveValue = false;
			int index = 0;
			
			for(int i =0;i<QTimeLoanTypeMVect.size();i++){
				qTimeLoanTypeM = (QTimeOutLoanTypeM)QTimeLoanTypeMVect.get(i);
				
				if(loanType.equalsIgnoreCase(qTimeLoanTypeM.getLoanType())){
					haveValue = true;
					index = i;
				}
			}
			
			if(haveValue){
				qTimeLoanTypeM = (QTimeOutLoanTypeM)QTimeLoanTypeMVect.get(index);
			}else{
				qTimeLoanTypeM = new QTimeOutLoanTypeM();
			}
			
		}
		
		getRequest().getSession().setAttribute("QTimeOutLoanTypeM_SESSION",qTimeLoanTypeM);
		
		log.debug("qTimeLoanTypeM ="+qTimeLoanTypeM);
		log.debug("qTimeLoanTypeM.getExpiry() = "+qTimeLoanTypeM.getExpiry());
		log.debug("qTimeLoanTypeM.getReasonCode() = "+qTimeLoanTypeM.getReasonCode());
		log.debug("qTimeLoanTypeM.getReminder() = "+qTimeLoanTypeM.getReminder());
		
		// ******************** End !!! Get QTimeOutLoanTypeM Data********************
		
		if("edit".equalsIgnoreCase(shwAddFrm)){
			getRequest().getSession().setAttribute("EDIT_Q_TIME_M",qTimeOutM);
		}else{
			getRequest().getSession().setAttribute("ADD_Q_TIME_M",qTimeOutM);
		}
		
		// just show add form & clear session
		if(!"cancelEdit".equals(shwAddFrm) && !"edit".equalsIgnoreCase(shwAddFrm)){
			getRequest().getSession().removeAttribute("FIRST_SEARCH_qTimeOutID");
			getRequest().getSession().removeAttribute("FIRST_SEARCH_qTimeOutDesc");
			if("first".equalsIgnoreCase(firstAdd)){
				getRequest().getSession().removeAttribute("ADD_Q_TIME_M");
			}
			return false;
		}
		
		//	show edit form
		log.debug("///ShowFieldIDWebAction/// shwAddFrm = " + shwAddFrm);
		if("edit".equals(shwAddFrm)){
			String qTimeOutIdEdit = getRequest().getParameter("qTimeOutIdEdit");
			
			qTimeOutMForEdit = new QueueTimeOutM();
			qTimeOutMForEdit.setQTimeOutID(qTimeOutIdEdit);
			
			return true;
		}
		
		return false;
	}

	/* (non-Javadoc)
	 * @see com.eaf.j2ee.pattern.view.webaction.WebAction#getNextActivityType()
	 */
	public int getNextActivityType() {

		return 0;
	}
	protected void doFail(EventResponse arg0) {
		log.debug("sorry i'm in do fail.!!!" );
	}
	protected void doSuccess(EventResponse arg0) {
		log.debug("//from Action//queueTimeOutM = " + (QueueTimeOutM)arg0.getEncapData());
		
		QueueTimeOutM queueTimeOutM = (QueueTimeOutM)arg0.getEncapData();
		String workTime = queueTimeOutM.getWorkingTime();
		
		if(workTime!=null && !"".equals(workTime)){
			String temp[] = workTime.split(" ");
			String second = temp[0];
			String minute = temp[1];
			String hour = temp[2];
			
			log.debug("second ="+second);
			log.debug("minute ="+minute);
			log.debug("hour ="+hour);
			
			queueTimeOutM.setHour(hour);
			queueTimeOutM.setMinute(minute);
			queueTimeOutM.setSecond(second);
		}
		
		getRequest().getSession().setAttribute("EDIT_Q_TIME_M", queueTimeOutM);
	}

	@Override
	public boolean getCSRFToken() {
		// TODO Auto-generated method stub
		return false;
	}

}
