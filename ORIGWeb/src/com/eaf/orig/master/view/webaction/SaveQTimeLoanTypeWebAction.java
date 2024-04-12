/*
 * Created on Nov 30, 2007
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
import com.eaf.orig.profile.model.UserDetailM;
import com.eaf.orig.shared.utility.ORIGUtility;

/**
 * @author Administrator
 *
 * Type: SaveQTimeLoanTypeWebAction
 */
public class SaveQTimeLoanTypeWebAction extends WebActionHelper implements
		WebAction {
	Logger log = Logger.getLogger(SaveQTimeLoanTypeWebAction.class);

	/* (non-Javadoc)
	 * @see com.eaf.j2ee.pattern.view.webaction.WebAction#toEvent()
	 */
	public Event toEvent() {
		 
		return null;
	}

	/* (non-Javadoc)
	 * @see com.eaf.j2ee.pattern.view.webaction.WebAction#requiredModelRequest()
	 */
	public boolean requiredModelRequest() {
		 
		return false;
	}

	/* (non-Javadoc)
	 * @see com.eaf.j2ee.pattern.view.webaction.WebAction#processEventResponse(com.eaf.j2ee.pattern.control.event.EventResponse)
	 */
	public boolean processEventResponse(EventResponse response) {
		 
		return false;
	}

	/* (non-Javadoc)
	 * @see com.eaf.j2ee.pattern.view.webaction.WebAction#preModelRequest()
	 */
	public boolean preModelRequest() {
		String shwAddFrm = getRequest().getParameter("shwAddFrm");
		ORIGUtility utility = new ORIGUtility();
		
		QueueTimeOutM qTimeOutM;
		QTimeOutLoanTypeM qTimeLoanTypeM =new QTimeOutLoanTypeM();
		
		if("edit".equalsIgnoreCase(shwAddFrm)){
			qTimeOutM = (QueueTimeOutM)getRequest().getSession().getAttribute("EDIT_Q_TIME_M");
		}else{
			qTimeOutM = (QueueTimeOutM)getRequest().getSession().getAttribute("ADD_Q_TIME_M");
		}
		
		if(qTimeOutM==null){
			qTimeOutM = new QueueTimeOutM();
		}
		Vector QTimeLoanTypeMVect = qTimeOutM.getQTimeOutLoanTypeMVect();
		
		UserDetailM userM = (UserDetailM) getRequest().getSession().getAttribute("ORIGUser");
		if(null == userM){
			userM = new UserDetailM();
		}
		String userName = userM.getUserName();
		
		String loanType = (String)getRequest().getSession().getAttribute("LOAN_TYPE_SESSION");
		
		String qTimeID = getRequest().getParameter("qTimeID");
		String qDesc = getRequest().getParameter("qDesc");
		String hour = getRequest().getParameter("hour");
		String minute = getRequest().getParameter("minute");
		String second = getRequest().getParameter("second");
		
		String expiry = getRequest().getParameter("expiry");
		String reasonCode = getRequest().getParameter("reasonCode");
		String reminder = getRequest().getParameter("reminder");
		
		 log.debug("qTimeID ="+qTimeID);
		 log.debug("qDesc ="+qDesc);
		 log.debug("hour ="+hour);
		 log.debug("minute ="+minute);
		 log.debug("second ="+second);
		 log.debug("loanType ="+loanType);
		 log.debug("expiry ="+expiry);
		 log.debug("reasonCode ="+reasonCode);
		 log.debug("reminder ="+reminder);
		 log.debug("userName ="+userName);
		 
		 
		 qTimeLoanTypeM.setQTimeOutID(qTimeID);
		 qTimeLoanTypeM.setLoanType(loanType);
		 qTimeLoanTypeM.setExpiry(utility.stringToInt(expiry));
		 qTimeLoanTypeM.setReasonCode(reasonCode);
		 qTimeLoanTypeM.setReminder(utility.stringToInt(reminder));
		 qTimeLoanTypeM.setUpdateBy(userName);
		 
		 if(QTimeLoanTypeMVect!=null && QTimeLoanTypeMVect.size()>0){
		 	QTimeOutLoanTypeM qLoanTypeM = new QTimeOutLoanTypeM();
		 	int index=-1;
		 	boolean hvLoanType=false;

		 	for(int i =0;i<QTimeLoanTypeMVect.size();i++){
		 		qLoanTypeM = (QTimeOutLoanTypeM)QTimeLoanTypeMVect.get(i);
		 		if(qLoanTypeM !=null){
			 		if( qLoanTypeM.getLoanType().equalsIgnoreCase(loanType) ){
			 			hvLoanType=true;
			 			index=i;
			 		}
		 		}
		 	}
		 	
		 	if(hvLoanType){
		 		log.debug("i'm updating QTimeLoanTypeMVect for loanType = "+qTimeLoanTypeM.getLoanType());
		 		QTimeLoanTypeMVect.setElementAt(qTimeLoanTypeM,index);
		 	}else{
		 		log.debug("i'm adding QTimeLoanTypeMVect for loanType = "+qTimeLoanTypeM.getLoanType());
		 		QTimeLoanTypeMVect.add(qTimeLoanTypeM);
		 	}
		 	
		 }else{
		 	QTimeLoanTypeMVect = new Vector();
		 	QTimeLoanTypeMVect.add(qTimeLoanTypeM);
		 }
		 
		 qTimeOutM.setQTimeOutID(qTimeID);
		 qTimeOutM.setQTimeOutDesc(qDesc);
		 qTimeOutM.setHour(hour);
		 qTimeOutM.setMinute(minute);
		 qTimeOutM.setSecond(second);
		 qTimeOutM.setQTimeOutLoanTypeMVect(QTimeLoanTypeMVect);
		 
		 if("edit".equalsIgnoreCase(shwAddFrm)){
			getRequest().getSession().setAttribute("EDIT_Q_TIME_M",qTimeOutM);
		 }else{
			getRequest().getSession().setAttribute("ADD_Q_TIME_M",qTimeOutM);
		 }
		 		 
		 getRequest().getSession().setAttribute("QTimeOutLoanTypeM_SESSION",qTimeLoanTypeM);
		 
//		**** pront log checking qTimeOutLoanTypeMVect
		 /*
			QTimeOutLoanTypeM qTimeOutLoanTypeM;
				System.out.println("QTimeLoanTypeMVect size = " + QTimeLoanTypeMVect.size());
				if(QTimeLoanTypeMVect.size() > 0){ 
					for(int i =0;i<QTimeLoanTypeMVect.size();i++){ 
						qTimeOutLoanTypeM = (QTimeOutLoanTypeM)QTimeLoanTypeMVect.get(i);
						log.debug("qTimeOutLoanTypeM.getQTimeOutID() ="+qTimeOutLoanTypeM.getQTimeOutID());
						log.debug("qTimeOutLoanTypeM.getLoanType() ="+qTimeOutLoanTypeM.getLoanType());
						log.debug("qTimeOutLoanTypeM.getLoanType() ="+qTimeOutLoanTypeM.getExpiry());
						log.debug("qTimeOutLoanTypeM.getLoanType() ="+qTimeOutLoanTypeM.getReasonCode());
						log.debug("qTimeOutLoanTypeM.getLoanType() ="+qTimeOutLoanTypeM.getReminder());
					}
				}*/
		
		
			return true;
	}

	/* (non-Javadoc)
	 * @see com.eaf.j2ee.pattern.view.webaction.WebAction#getNextActivityType()
	 */
	public int getNextActivityType() {
		 
		return 0;
	}

	@Override
	public boolean getCSRFToken() {
		// TODO Auto-generated method stub
		return false;
	}

}
