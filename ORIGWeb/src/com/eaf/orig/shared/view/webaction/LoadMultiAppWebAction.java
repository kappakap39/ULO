package com.eaf.orig.shared.view.webaction;

import java.util.HashMap;
import java.util.Vector;

import org.apache.log4j.Logger;

import com.eaf.j2ee.pattern.control.FrontController;
import com.eaf.j2ee.pattern.control.event.Event;
import com.eaf.j2ee.pattern.control.event.EventResponse;
import com.eaf.j2ee.pattern.view.webaction.WebAction;
import com.eaf.j2ee.pattern.view.webaction.WebActionHelper;
import com.eaf.orig.formcontrol.view.form.ORIGFormHandler;
import com.eaf.orig.profile.model.UserDetailM;
import com.eaf.orig.shared.constant.OrigConstant;
import com.eaf.orig.shared.model.ApplicationDataM;
import com.eaf.orig.shared.model.PersonalInfoDataM;
import com.eaf.orig.shared.model.SLADataM;
import com.eaf.orig.shared.utility.ORIGUtility;

/**
 * @author Administrator
 * 
 * To change this generated comment edit the template variable "typecomment":
 * Window>Preferences>Java>Templates. To enable and disable the creation of type
 * comments go to Window>Preferences>Java>Code Generation.
 */
public class LoadMultiAppWebAction extends WebActionHelper implements WebAction {
    static Logger log = Logger.getLogger(LoadMultiAppWebAction.class);

    private String action = "";

    private int next = 0;

    /**
     * @see com.eaf.j2ee.pattern.view.webaction.WebAction#toEvent()
     */
    public Event toEvent() {
        return null;
    }

    /**
     * @see com.eaf.j2ee.pattern.view.webaction.WebAction#requiredModelRequest()
     */
    public boolean requiredModelRequest() {
        return false;
    }

    /**
     * @see com.eaf.j2ee.pattern.view.webaction.WebAction#processEventResponse(EventResponse)
     */
    public boolean processEventResponse(EventResponse response) {
        return false;
    }

    /**
     * @see com.eaf.j2ee.pattern.view.webaction.WebAction#preModelRequest()
     */
    public boolean preModelRequest() {
        log.debug("++++++++++++++ Start LoadMultiAppWebAction  ++++++++++++++++++++");
        getRequest().getSession().removeAttribute("ORIGForm");
        ORIGFormHandler ORIGForm = new ORIGFormHandler();
        ORIGForm = (ORIGFormHandler) getRequest().getSession().getAttribute("ORIGForm");
        
        ApplicationDataM appM = new ApplicationDataM();
        
        PersonalInfoDataM personalInfoM  = new PersonalInfoDataM();
        personalInfoM.setPersonalType("A");
        Vector vPersonnals = new Vector(); 
        vPersonnals.add(personalInfoM);
        appM.setPersonalInfoVect(vPersonnals);
        
        
        getRequest().getSession().setAttribute("fromMultiApp","Y");
        
        if (ORIGForm == null) {
            ORIGForm = new ORIGFormHandler();
        }
        ORIGForm.setAppForm(appM);
        //NaosApplicationUtil appUtil = NaosApplicationUtil.getInstance_SG();
        //ORIGForm.setAppForm(appUtil.setupApplication_SG(new ApplicationM()));
        //log.debug("**********getPersonalInfos ==== " +
        // ORIGForm.getAppForm().getPersonalInfos().size());
        getRequest().getSession().setAttribute("ORIGForm", ORIGForm);
        String searchType = (String) getRequest().getSession().getAttribute("searchType");
        if (searchType == null || ("").equals(searchType)) {
            searchType = getRequest().getParameter("searchType");
        }

        getRequest().getSession().setAttribute("PersonalType","A");
        
        String first = getRequest().getParameter("first");
        String cancelApp = getRequest().getParameter("cancelApp");
        UserDetailM userM = (UserDetailM) getRequest().getSession().getAttribute("ORIGUser");
        Vector userRoles = userM.getRoles();
        // set current role
        String role = getRequest().getParameter("role");
        if (role != null) {
            if (!role.equals(userRoles.elementAt(0))) {
                userRoles.insertElementAt(role, 0);
            }
            userM.setRoles(userRoles);
        }
        // end set

        log.debug("LoadApplicationWebAction : userRoles(0) = " + userRoles.get(0));

        log.debug("//////////////////");
        log.debug("searchType=" + searchType);
        log.debug("FirstTime == " + first);
        log.debug("//////////////////");
        if (OrigConstant.ROLE_DE.equals(userRoles.get(0))) {
          
            next = FrontController.ACTION;
            action = "page=MULTI_APPFORM";      
        } 
        String firstLogonApp = (String) getRequest().getSession().getAttribute("FIRST_LOGON_APP");
        if ("Y".equals(firstLogonApp)) {
            ORIGUtility utility = new ORIGUtility();
            //Vector applicationSLAVect = null;
            HashMap applicationSLAHash = new HashMap();
            String qName = null;
            String status = null;
            String action = null;
            String jobState = null;
            double time = 0;
            Vector slaQ;
            SLADataM dataM;
            Vector SLARoles = new Vector();
            for (int i = 0; i < userRoles.size(); i++) {
                String userRole = (String) userRoles.get(i);
                if (userRole.indexOf(OrigConstant.ROLE_CMR) == 0) {
                    SLARoles.add(OrigConstant.ROLE_CMR);
                    break;
                }
            }
            for (int i = 0; i < userRoles.size(); i++) {
                String userRole = (String) userRoles.get(i);
                if (userRole.indexOf(OrigConstant.ROLE_XCMR) == 0) {
                    SLARoles.add(OrigConstant.ROLE_XCMR);
                    break;
                }
            }
            for (int i = 0; i < userRoles.size(); i++) {
                String userRole = (String) userRoles.get(i);
                if (userRole.indexOf(OrigConstant.ROLE_UW)==0) {
                    SLARoles.add(OrigConstant.ROLE_UW);
                    break;
                }
            }
            for (int i = 0; i < userRoles.size(); i++) {
                String userRole = (String) userRoles.get(i);
                if (userRole.indexOf(OrigConstant.ROLE_XUW) == 0) {
                    SLARoles.add(OrigConstant.ROLE_XUW);
                    break;
                }
            }
            log.debug("SLARoles size=" + SLARoles.size());
            //String roleName;
            for (int k = 0; k < SLARoles.size(); k++) {
                String roleName = (String) SLARoles.get(k);
                slaQ = utility.getSLAQName(roleName);
                if (slaQ.size() > 0) {
                    HashMap hQueue = new HashMap();
                    for (int i = 0; i < slaQ.size(); i++) {

                        dataM = (SLADataM) slaQ.get(i);

                        qName = dataM.getQName();
                        status = dataM.getAppStatus();
                        action = dataM.getAction();
                        jobState = dataM.getJobState();
                        Vector vQueue = new Vector();
                        log.debug("qName = " + qName);
                        log.debug("status = " + status);
                        log.debug("action = " + action);
                        log.debug("jobState = " + jobState);
                        log.debug("roleName = " + roleName);
                        //Vector applicationSLAVect = utility.getSLA(qName,
                        // status, action, jobState, userM.getUserName(), role,
                        // time);
                        Vector applicationSLAVect = utility.getSLA(qName, status, action, jobState, userM.getUserName(), roleName, time);
                        if (applicationSLAVect != null && applicationSLAVect.size() > 1) {
                            //applicationSLAHash.put(qName+","+dataM.getRole(),
                            // applicationSLAVect);
                            hQueue.put(qName, applicationSLAVect);
                        }
                        log.debug("qName + role = " + (qName + "," + dataM.getRole()));
                        log.debug("applicationSLAVect = " + applicationSLAVect.size());

                    }
                    if (hQueue != null && !hQueue.isEmpty()) {
                        applicationSLAHash.put(roleName, hQueue);
                    }
                }

            }
            log.debug("applicationSLAHash = " + applicationSLAHash);
            getRequest().getSession(true).setAttribute("SLA", applicationSLAHash);
        } else {
            getRequest().getSession(true).removeAttribute("SLA");
        }

        log.debug("searchType = " + searchType);
        getRequest().getSession().setAttribute("searchType", searchType);
        getRequest().getSession().setAttribute("cancelApp",cancelApp);
        log.debug("++++++++++++++ End LoadMultiAppWebAction  ++++++++++++++++++++");
        return true;
    }

    /**
     * @see com.eaf.j2ee.pattern.view.webaction.WebAction#getNextActivityType()
     */
    public int getNextActivityType() {
        return next;
    }

    /**
     * @see com.eaf.j2ee.pattern.view.webaction.WebActionHelper#getNextActionParameter()
     */
    public String getNextActionParameter() {
        return action;
    }

	@Override
	public boolean getCSRFToken() {
		// TODO Auto-generated method stub
		return false;
	}

}
