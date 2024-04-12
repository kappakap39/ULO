/*
 * Created on Oct 15, 2007
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
package com.eaf.orig.shared.view.webaction;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.math.BigDecimal;
import java.net.URL;
import java.util.HashMap;
import java.util.Vector;

import com.eaf.j2ee.pattern.control.event.Event;
import com.eaf.j2ee.pattern.control.event.EventResponse;
import com.eaf.j2ee.pattern.view.webaction.WebAction;
import com.eaf.j2ee.pattern.view.webaction.WebActionHelper;
import com.eaf.orig.formcontrol.view.form.ORIGFormHandler;
import com.eaf.orig.shared.constant.OrigConstant;
import com.eaf.orig.shared.model.ApplicationDataM;
import com.eaf.orig.shared.model.CollateralDataM;
import com.eaf.orig.shared.model.LoanDataM;
import com.eaf.orig.shared.model.PersonalInfoDataM;
import com.eaf.orig.shared.utility.ORIGUtility;

/**
 * @author Administrator
 * 
 * Type: LoadDocumentList
 */
public class LoadTailorDocumentListWebAction extends WebActionHelper implements WebAction {

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
        ORIGFormHandler ORIGForm = (ORIGFormHandler) getRequest().getSession().getAttribute("ORIGForm");
        ORIGUtility utility = new ORIGUtility();
        ApplicationDataM appM = ORIGForm.getAppForm();

        //Retreive app data to evaluate rules
        //	    String loanType = appM.getLoanType();
        String loanType;
        Vector loanVect = appM.getLoanVect();
        if (loanVect != null && loanVect.size() > 0) {
            loanType = ((LoanDataM) loanVect.elementAt(0)).getLoanType();
        } else {
            loanType = ORIGForm.getAppForm().getLoanType();
        }

        //Wiroon 20100317
        HashMap requiredDoc = new HashMap();

        CollateralDataM collM = (CollateralDataM) appM.getCollateralVect().get(0);
        String propertyType = collM.getPropertyType();

        try {
            System.out.println("loan type >>" + loanType);
            System.out.println("property type >>" + propertyType);

            URL service = new URL("http://larukunb:8080/pau/DoclistService.jsp?actionType=A&loanType=" + loanType + "&propertyType=" + propertyType);
            BufferedReader in = new BufferedReader(new InputStreamReader(service.openStream()));
            String inputLine;
            while ((inputLine = in.readLine()) != null) {
                if (!inputLine.equals("")) {
                    System.out.println("app result: " + inputLine);
                    String[] tmp = inputLine.split(",");
                    for (int i = 0; i < tmp.length; i++) {
                        System.out.println("put temp >>" + tmp[i].trim());
                        requiredDoc.put(tmp[i].trim(), tmp[i].trim());
                    }
                }
            }
            in.close();
        } catch (Exception e) {
            System.out.println("Exception "+e);
        }

        //Retrive doc list for main borrower
        PersonalInfoDataM personalInfoDataM = utility.getPersonalInfoByType(ORIGForm.getAppForm(), OrigConstant.PERSONAL_TYPE_APPLICANT);
        String occ = personalInfoDataM.getOccupation();
        BigDecimal otherIncome = personalInfoDataM.getOtherIncome();
        String businessSize = personalInfoDataM.getBusinessSize();

        try {
            System.out.println("occ >>>" + occ);
            System.out.println("other income >>>" + otherIncome);

            URL service = new URL("http://larukunb:8080/pau/DoclistService.jsp?actionType=B&occ=" + occ + "&otherIncome=" + otherIncome.doubleValue()+"&size="+businessSize);
            BufferedReader in = new BufferedReader(new InputStreamReader(service.openStream()));
            String inputLine;
            while ((inputLine = in.readLine()) != null) {
                if (!inputLine.equals("")) {
                    System.out.println("borrower1 result: " + inputLine);
                    String[] tmp = inputLine.split(",");
                    for (int i = 0; i < tmp.length; i++) {
                        System.out.println("put temp >>" + tmp[i].trim());
                        requiredDoc.put(tmp[i].trim(), tmp[i].trim());
                    }
                }
            }
            in.close();
        } catch (Exception e) {
            System.out.println("Exception "+e);
        }

        //Retrive doc list for main borrower
        try {
            personalInfoDataM = utility.getPersonalInfoByType(ORIGForm.getAppForm(), OrigConstant.PERSONAL_TYPE_GUARANTOR);
            occ = personalInfoDataM.getOccupation();
            otherIncome = personalInfoDataM.getOtherIncome();
            businessSize = personalInfoDataM.getBusinessSize();
            
            System.out.println("occ >>>" + occ);
            System.out.println("other income >>>" + otherIncome);

            URL service = new URL("http://larukunb:8080/pau/DoclistService.jsp?actionType=B&occ=" + occ + "&otherIncome=" + otherIncome.doubleValue()+"&size="+businessSize);
            BufferedReader in = new BufferedReader(new InputStreamReader(service.openStream()));
            String inputLine;
            while ((inputLine = in.readLine()) != null) {
                if (!inputLine.equals("")) {
                    System.out.println("borrower2 result: " + inputLine);
                    String[] tmp = inputLine.split(",");
                    for (int i = 0; i < tmp.length; i++) {
                        System.out.println("put temp >>" + tmp[i].trim());
                        requiredDoc.put(tmp[i].trim(), tmp[i].trim());
                    }
                }
            }
            in.close();
        } catch (Exception e) {
            System.out.println("Exception "+e);
        }

        getRequest().getSession().setAttribute("requiredDocMap", requiredDoc);

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
