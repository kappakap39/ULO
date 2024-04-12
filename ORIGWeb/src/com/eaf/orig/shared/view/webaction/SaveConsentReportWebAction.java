/*
 * Created on Dec 11, 2007
 * Created by Sankom Sanpunya
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

import java.util.Vector;

import org.apache.log4j.Logger;

import com.eaf.j2ee.pattern.control.FrontController;
import com.eaf.j2ee.pattern.control.event.Event;
import com.eaf.j2ee.pattern.control.event.EventResponse;
import com.eaf.j2ee.pattern.view.webaction.WebAction;
import com.eaf.j2ee.pattern.view.webaction.WebActionHelper;
import com.eaf.ncb.model.NCBReqRespConsentDataM;
import com.eaf.ncb.service.ejb.NCBServiceManager;
import com.eaf.orig.profile.model.UserDetailM;
import com.eaf.orig.shared.model.ValueListM;
//import com.eaf.xrules.shared.factory.XRulesEJBFactory;

/**
 * @author Sankom
 *
 * Type: SaveConsentReportWebAction
 */
public class SaveConsentReportWebAction extends WebActionHelper implements
        WebAction {
     private String nextActionParaMeter;
     Logger log = Logger.getLogger(SaveConsentReportWebAction.class);
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
        /*Enumeration  enum=getRequest().getParameterNames();
        while( enum.hasMoreElements()){
            
         System.out.print("param "+enum.nextElement());    
         System.out.println("  value "+getRequest().getParameter((String)enum.nextElement()));
        }*/
       //get Data From Value List
        UserDetailM userM = (UserDetailM) getRequest().getSession().getAttribute("ORIGUser");
		if(null == userM){
			userM = new UserDetailM();
		}
        ValueListM  valuelist=(ValueListM)getRequest().getSession(true).getAttribute("VALUE_LIST");
        Vector vConsentUpdate=new Vector(); 
        if(valuelist!=null){
           Vector  vConsentResult=valuelist.getResult();
           if(vConsentResult!=null){
           for(int i=0;i<vConsentResult.size();i++){
               Vector elementList = (Vector)vConsentResult.get(i);
                String trackingCode=(String)elementList.elementAt(8);
				String chkConsentName="chk_"+trackingCode;
				String remarkConsentName="remark_"+trackingCode;
				String chkConsentValue=getRequest().getParameter(chkConsentName);
				String remarkConsentValue=getRequest().getParameter(remarkConsentName);
				if(chkConsentValue!=null||(remarkConsentValue!=null&&!"".equals(remarkConsentValue) )){
				    NCBReqRespConsentDataM ncbReqRespConsentDataM=new NCBReqRespConsentDataM();
				    if("".equals(chkConsentValue)) {
				        chkConsentValue="N";
				     }
				    ncbReqRespConsentDataM.setConsentFlag(chkConsentValue);
				    ncbReqRespConsentDataM.setConsentRemark(remarkConsentValue);
				    ncbReqRespConsentDataM.setConsentUpdateBy(userM.getUserName());
				    ncbReqRespConsentDataM.setTrackingCode(trackingCode);				    
				    vConsentUpdate.add(ncbReqRespConsentDataM);
				}
				
            }        
           }
        }
        if(vConsentUpdate.size()>0){
            try {
                //call ncb ejb update    
//                NCBServiceManager ncbService=XRulesEJBFactory.getNCBServiceManager();
//                ncbService.saveUpdateNCBReqRespConsent(vConsentUpdate);
            } catch ( Exception e) {
                log.debug(e.getMessage());
            }
            
        }
        this.nextActionParaMeter="action=ValueListWebAction";
        return true;        
    }

    /* (non-Javadoc)
     * @see com.eaf.j2ee.pattern.view.webaction.WebAction#getNextActivityType()
     */    
    public int getNextActivityType() {        
        return FrontController.ACTION;
    }
   
    /* (non-Javadoc)
     * @see com.eaf.j2ee.pattern.view.webaction.WebActionHelper#getNextActionParameter()
     */
    public String getNextActionParameter() {         
        return nextActionParaMeter;
    }

	@Override
	public boolean getCSRFToken() {
		// TODO Auto-generated method stub
		return false;
	}
}
