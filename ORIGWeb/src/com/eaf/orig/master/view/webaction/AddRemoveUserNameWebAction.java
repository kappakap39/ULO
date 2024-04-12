/*
 * Created on Dec 3, 2007
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
import com.eaf.orig.master.shared.model.UserNameM;

/**
 * @author Administrator
 * 
 * Type: AddRemoveUserNameWebAction
 */
public class AddRemoveUserNameWebAction extends WebActionHelper implements WebAction {
    static Logger log = Logger.getLogger(AddRemoveUserNameWebAction.class);

    /*
     * (non-Javadoc)
     * 
     * @see com.eaf.j2ee.pattern.view.webaction.WebAction#toEvent()
     */
    public Event toEvent() {

        return null;
    }

    /*
     * (non-Javadoc)
     * 
     * @see com.eaf.j2ee.pattern.view.webaction.WebAction#requiredModelRequest()
     */
    public boolean requiredModelRequest() {

        return false;
    }

    /*
     * (non-Javadoc)
     * 
     * @see com.eaf.j2ee.pattern.view.webaction.WebAction#processEventResponse(com.eaf.j2ee.pattern.control.event.EventResponse)
     */
    public boolean processEventResponse(EventResponse response) {
        return false;
    }

    /*
     * (non-Javadoc)
     * 
     * @see com.eaf.j2ee.pattern.view.webaction.WebAction#preModelRequest()
     */
    public boolean preModelRequest() {
        //*** for show Edit Data Changed

        //*** END

        String groupName = getRequest().getParameter("UsrName");
        String selectedGroupName = getRequest().getParameter("selectedUsrName");
        //String[] UsrName = getRequest().getParameterValues("UsrName");
        //String[] selectedUsrName =
        // getRequest().getParameterValues("selectedUsrName");
        //String[] cusDesc = null;
        //String[] groupName = null;
        //String[] loanType = null;
        //String[] cusType = null;
        //String[] loanDesc = null;
        //String[] selcusDesc = null;
        //String[] selGroupName = null;
        //String[] selLoanType = null;
        //String[] selCusType = null;
        //String[] selLoanDesc = null;
        //String[] temp = null;
        //String[] temp2 = null;

        //if(UsrName!=null && UsrName.length>0){
        //log.debug("------------------> all UsrName length = " +
        // UsrName.length);
        //cusDesc = new String[UsrName.length];
        //groupName = new String[UsrName.length];
        //loanType = new String[UsrName.length];
        //cusType = new String[UsrName.length];
        //loanDesc = new String[UsrName.length];
        //temp = new String[UsrName.length];

        //for(int i = 0; i < UsrName.length; i++){
        //temp = UsrName[i].split(",");
        //cusDesc[i] = temp[0];
        //groupName[i] = temp[1];
        //loanType[i] = temp[2];
        //cusType[i] = temp[3];
        //loanDesc[i] = temp[4];
        //}
        //}
        //if(selectedUsrName!=null && selectedUsrName.length>0){
        //selcusDesc = new String[selectedUsrName.length];
        //selGroupName = new String[selectedUsrName.length];
        //selLoanType = new String[selectedUsrName.length];
        //selCusType = new String[selectedUsrName.length];
        //selLoanDesc = new String[selectedUsrName.length];
        //temp2 = new String[selectedUsrName.length];

        //for(int i = 0; i < selectedUsrName.length; i++){
        // = selectedUsrName[i].split(",");
        //selcusDesc[i] = temp2[0];
        //selGroupName[i] = temp2[1];
        //selLoanType[i] = temp2[2];
        //selCusType[i] = temp2[3];
        //selLoanDesc[i] = temp2[4];
        //}
        // }

        Vector selUserNameVect = (Vector) getRequest().getSession().getAttribute("SELECTED_USER_NAME");
        if (selUserNameVect == null) {
            selUserNameVect = new Vector();
        }
        Vector userNameVect = (Vector) getRequest().getSession().getAttribute("SEARCH_USER_NAME");
        if (userNameVect == null) {
            userNameVect = new Vector();
        }
        String addOrRemove = getRequest().getParameter("addOrRemove");
        log.debug("------------------> addOrRemove = " + addOrRemove);
        if ((addOrRemove != null) && "add".equalsIgnoreCase(addOrRemove)) {
            log.debug("///im adding selUserNameVect");
            if (groupName != null && !"".equals(groupName)) {
                log.debug("------------------> all UsrName   = " + groupName);

                //for (int i=0; i<UsrName.length;i++) {
                //	String cusDesc1= cusDesc[i];
                //	String groupName1= groupName[i];
                //	String loanType1= loanType[i];
                //	String cusType1= cusType[i];
                //// String loanDesc1= loanDesc[i];
                UserNameM userNameM = new UserNameM();

                //	userNameM.setCusDesc(cusDesc1);
                userNameM.setGroupName(groupName);
                //	userNameM.setLoanType(loanType1);
                //	userNameM.setCusType(cusType1);
                //	userNameM.setLoanDesc(loanDesc1);
                selUserNameVect.add(userNameM);
                /* remove from busVect */
                if (userNameVect.size() > 0) {
                    UserNameM userNameSearchM = new UserNameM();
                    for (int j = 0; j < userNameVect.size(); j++) {
                        userNameSearchM = (UserNameM) userNameVect.get(j);
                        if (groupName.equals(userNameSearchM.getGroupName())) {
                            userNameVect.removeElementAt(j);
                        }
                    }
                }
                //}
            }
        } else if ((addOrRemove != null) && "remove".equalsIgnoreCase(addOrRemove)) {
            log.debug("///im removing selUserNameVect");
            if (selectedGroupName != null) {
                log.debug("------------------> all selectedUsrName   = " + selectedGroupName);

                //for (int i=0; i<selectedUsrName.length;i++) {
                //	String selCusDesc1 = selcusDesc[i];
                //	String selGroupName1 = selGroupName[i];
                //	String selLoanType1 = selLoanType[i];
                //	String selCusType1 = selCusType[i];
                //	String selLoanDesc1 = selLoanDesc[i];
                UserNameM userNameM = new UserNameM();

                //userNameM.setCusDesc(selCusDesc1);
                userNameM.setGroupName(selectedGroupName);
                //userNameM.setLoanType(selLoanType1);
                //userNameM.setCusType(selCusType1);
                //userNameM.setLoanDesc(selLoanDesc1);
                userNameVect.add(userNameM);

                /* remove from selBusVect */
                if (selUserNameVect.size() > 0) {
                    UserNameM userNameSelM = new UserNameM();
                    for (int j = 0; j < selUserNameVect.size(); j++) {
                        userNameSelM = (UserNameM) selUserNameVect.get(j);
                        if (selectedGroupName.equals(userNameSelM.getGroupName())) {
                            selUserNameVect.removeElementAt(j);
                        }
                    }
                }
            }

        }

        getRequest().getSession().setAttribute("SELECTED_USER_NAME", selUserNameVect);
        getRequest().getSession().setAttribute("SEARCH_USER_NAME", userNameVect);

        return true;
    }

    /*
     * (non-Javadoc)
     * 
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
