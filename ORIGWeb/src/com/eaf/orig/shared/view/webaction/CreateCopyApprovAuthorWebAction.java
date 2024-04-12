/*
 * Created on Sep 29, 2008
 * Created by Sankom
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
package com.eaf.orig.shared.view.webaction;

import java.sql.Date;
import java.util.Vector;

import org.apache.log4j.Logger;

import com.eaf.j2ee.pattern.control.FrontController;
import com.eaf.j2ee.pattern.control.event.Event;
import com.eaf.j2ee.pattern.control.event.EventResponse;
import com.eaf.j2ee.pattern.view.webaction.WebAction;
import com.eaf.j2ee.pattern.view.webaction.WebActionHelper;
import com.eaf.orig.master.shared.model.ApprovAuthorM;
import com.eaf.orig.master.shared.model.OrigPolicyLevelGroupDataM;
import com.eaf.orig.master.shared.model.OrigPolicyLevelMapDataM;
import com.eaf.orig.master.shared.model.OrigPolicyVersionDataM;
import com.eaf.orig.master.shared.model.OrigPolicyVersionLevelDataM;
import com.eaf.orig.profile.model.UserDetailM;
import com.eaf.orig.shared.utility.ORIGUtility;

/**
 * @author Sankom
 * 
 * Type: CreateCopyApprovAuthorWebAction
 */
public class CreateCopyApprovAuthorWebAction extends WebActionHelper implements WebAction {
    Logger log = Logger.getLogger(CreateCopyApprovAuthorWebAction.class);

    /*
     * (non-Javadoc)
     * 
     * @see com.eaf.j2ee.pattern.view.webaction.WebAction#toEvent()
     */
    public Event toEvent() {
        // TODO Auto-generated method stub
        return null;
    }

    /*
     * (non-Javadoc)
     * 
     * @see com.eaf.j2ee.pattern.view.webaction.WebAction#requiredModelRequest()
     */
    public boolean requiredModelRequest() {
        // TODO Auto-generated method stub
        return false;
    }

    /*
     * (non-Javadoc)
     * 
     * @see com.eaf.j2ee.pattern.view.webaction.WebAction#processEventResponse(com.eaf.j2ee.pattern.control.event.EventResponse)
     */
    public boolean processEventResponse(EventResponse response) {
        // TODO Auto-generated method stub
        return false;
    }

    /*
     * (non-Javadoc)
     * 
     * @see com.eaf.j2ee.pattern.view.webaction.WebAction#preModelRequest()
     */
    public boolean preModelRequest() {
        log.debug("Create New Copy");
        String versionName = getRequest().getParameter("newCopyVersion");
        log.debug("VersionName=" + versionName);
        UserDetailM userM = (UserDetailM) getRequest().getSession().getAttribute("ORIGUser");
        String strEffectiveDate = getRequest().getParameter("effectiveDate");
        String strExpireDate = getRequest().getParameter("expireDate");
        ORIGUtility utility = ORIGUtility.getInstance();
        Date effectiveDate = ORIGUtility.parseThaiToEngDate(strEffectiveDate);
        Date expireDate = ORIGUtility.parseThaiToEngDate(strExpireDate);
        OrigPolicyVersionDataM policyVersionDataM = (OrigPolicyVersionDataM) getRequest().getSession().getAttribute("POLICY_VERSION");
        OrigPolicyVersionDataM copyOrigPolicyVersionDataM = this.copyVersion(policyVersionDataM, versionName, userM, effectiveDate, expireDate);
        getRequest().getSession().setAttribute("POLICY_VERSION", copyOrigPolicyVersionDataM);
        //Level
        if (copyOrigPolicyVersionDataM != null && copyOrigPolicyVersionDataM.getVPolicyVersionLevel() != null) {
            getRequest().getSession().setAttribute("POLICY_VERSION_LEVEL", copyOrigPolicyVersionDataM.getVPolicyVersionLevel());
        }
        //Map code
        if (copyOrigPolicyVersionDataM != null && copyOrigPolicyVersionDataM.getVPolicyMapRules() != null) {

            getRequest().getSession().setAttribute("POLICY_VERSION_MAP_RULES", copyOrigPolicyVersionDataM.getVPolicyMapRules());
        }
        //Approval
        if (copyOrigPolicyVersionDataM != null && copyOrigPolicyVersionDataM.getVPolicyApprovalDetails() != null) {
            log.debug("Approval Detail size " + copyOrigPolicyVersionDataM.getVPolicyApprovalDetails().size());
            getRequest().getSession().setAttribute("POLICY_VERSION_APPRV_DETAIL", copyOrigPolicyVersionDataM.getVPolicyApprovalDetails());
        }
        //Group
        Vector vPolicyVersionGroup = copyOrigPolicyVersionDataM.getVPolicyVesionGroup();
        if (copyOrigPolicyVersionDataM != null && vPolicyVersionGroup != null) {
            getRequest().getSession().setAttribute("POLICY_VERSION_GROUPS", vPolicyVersionGroup);
        }
        
        //Group Total
        Vector vPolicyVersionGroupTotal = copyOrigPolicyVersionDataM.getVPolicyVesionGroupTotal();
        if (copyOrigPolicyVersionDataM != null && vPolicyVersionGroupTotal != null) {
            getRequest().getSession().setAttribute("POLICY_VERSION_GROUPS_TOTAL", vPolicyVersionGroupTotal);
        }
        return true;
    }

    /*
     * (non-Javadoc)
     * 
     * @see com.eaf.j2ee.pattern.view.webaction.WebAction#getNextActivityType()
     */
    public int getNextActivityType() {
        return FrontController.PAGE;
    }

    private OrigPolicyVersionDataM copyVersion(OrigPolicyVersionDataM origPolicyVersionDataM, String versionName, UserDetailM userM, Date effectiveDate,
            Date expireDate) {
        if (null == userM) {
            userM = new UserDetailM();
        }
        String userName = userM.getUserName();
        OrigPolicyVersionDataM copyOrigPolicyVersionDataM = new OrigPolicyVersionDataM();
        copyOrigPolicyVersionDataM.setPolicyVersion(versionName);
        copyOrigPolicyVersionDataM.setCreateBy(userName);
        copyOrigPolicyVersionDataM.setUpdateBy(userName);
        copyOrigPolicyVersionDataM.setEffectiveDate(effectiveDate);
        copyOrigPolicyVersionDataM.setExpireDate(expireDate);
        copyOrigPolicyVersionDataM.setDescription(origPolicyVersionDataM.getDescription());

        if (origPolicyVersionDataM != null) {
            if (origPolicyVersionDataM.getVPolicyVersionLevel() != null) {
                copyOrigPolicyVersionDataM.setVPolicyVersionLevel((Vector) ORIGUtility.cloneObject(origPolicyVersionDataM.getVPolicyVersionLevel()));
                Vector vPolicyVersionLevel = copyOrigPolicyVersionDataM.getVPolicyVersionLevel();
                if (vPolicyVersionLevel != null) {
                    for (int i = 0; i < vPolicyVersionLevel.size(); i++) {
                        OrigPolicyVersionLevelDataM origPolicyVersionLevel = (OrigPolicyVersionLevelDataM) vPolicyVersionLevel.get(i);
                        origPolicyVersionDataM.setCreateBy(userName);
                        origPolicyVersionDataM.setUpdateBy(userName);
                    }
                }
            }
            if (origPolicyVersionDataM.getVPolicyVesionGroup() != null) {
                copyOrigPolicyVersionDataM.setVPolicyVesionGroup((Vector) ORIGUtility.cloneObject(origPolicyVersionDataM.getVPolicyVesionGroup()));
                Vector vPolicyVersionGroup = copyOrigPolicyVersionDataM.getVPolicyVesionGroup();
                if (vPolicyVersionGroup != null) {
                    for (int i = 0; i < vPolicyVersionGroup.size(); i++) {
                        OrigPolicyLevelGroupDataM  origPolicyLevelGroup = (OrigPolicyLevelGroupDataM) vPolicyVersionGroup.get(i);
                        origPolicyLevelGroup.setCreateBy(userName);
                        origPolicyLevelGroup.setUpdateBy(userName);
                    }
                }
            }
            if (origPolicyVersionDataM.getVPolicyMapRules() != null) {
                copyOrigPolicyVersionDataM.setVPolicyMapRules((Vector) ORIGUtility.cloneObject(origPolicyVersionDataM.getVPolicyMapRules()));
                Vector vPolicyMapRules=copyOrigPolicyVersionDataM.getVPolicyMapRules();
                if (vPolicyMapRules != null) {
                    for (int i = 0; i < vPolicyMapRules.size(); i++) {
                        OrigPolicyLevelMapDataM  origPolicyLevelMapDataM = (OrigPolicyLevelMapDataM) vPolicyMapRules.get(i);
                        origPolicyLevelMapDataM.setCreateBy(userName);
                        origPolicyLevelMapDataM.setUpdateBy(userName);
                    }
                }
            }
            if (origPolicyVersionDataM.getVPolicyApprovalDetails() != null) {
                copyOrigPolicyVersionDataM.setVPolicyApprovalDetails((Vector) ORIGUtility.cloneObject(origPolicyVersionDataM.getVPolicyApprovalDetails()));
                Vector vPolicyApprovalDetail=copyOrigPolicyVersionDataM.getVPolicyApprovalDetails();
                if (vPolicyApprovalDetail != null) {
                    for (int i = 0; i < vPolicyApprovalDetail.size(); i++) {
                        ApprovAuthorM  origApprovAuthorM = (ApprovAuthorM) vPolicyApprovalDetail.get(i);
                        origApprovAuthorM.setCreateBy(userName);
                        origApprovAuthorM.setUpdateBy(userName);
                    }
                }
            }
            
            if (origPolicyVersionDataM.getVPolicyVesionGroupTotal() != null) {
                copyOrigPolicyVersionDataM.setVPolicyVesionGroupTotal((Vector) ORIGUtility.cloneObject(origPolicyVersionDataM.getVPolicyVesionGroupTotal()));
                Vector vPolicyVersionGroupTotal = copyOrigPolicyVersionDataM.getVPolicyVesionGroupTotal();
                if (vPolicyVersionGroupTotal != null) {
                    for (int i = 0; i < vPolicyVersionGroupTotal.size(); i++) {
                        OrigPolicyLevelGroupDataM  origPolicyLevelGroup = (OrigPolicyLevelGroupDataM) vPolicyVersionGroupTotal.get(i);
                        origPolicyLevelGroup.setCreateBy(userName);
                        origPolicyLevelGroup.setUpdateBy(userName);
                    }
                }
            }
        }
        return copyOrigPolicyVersionDataM;
    }

	@Override
	public boolean getCSRFToken() {
		// TODO Auto-generated method stub
		return false;
	}
}
