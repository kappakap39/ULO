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

import java.util.Date;

import org.apache.log4j.Logger;

import com.eaf.j2ee.pattern.control.FrontController;
import com.eaf.j2ee.pattern.control.event.Event;
import com.eaf.j2ee.pattern.control.event.EventResponse;
import com.eaf.j2ee.pattern.view.webaction.WebAction;
import com.eaf.j2ee.pattern.view.webaction.WebActionHelper;
import com.eaf.orig.shared.model.SearchConsentDataM;
import com.eaf.orig.shared.model.ValueListM;
import com.eaf.orig.shared.utility.ORIGDisplayFormatUtil;
import com.eaf.orig.shared.utility.ORIGUtility;

/**
 * @author Sankom
 *
 * Type: SearchConsentReportWebAction
 */
public class SearchConsentReportWebAction extends WebActionHelper implements
        WebAction {
    Logger logger = Logger.getLogger(SearchConsentReportWebAction.class);
    private String nextAction = null;
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
		SearchConsentDataM searchConsentDataM = (SearchConsentDataM)getRequest().getSession().getAttribute("SEARCH_CONSENT_DATAM");
		String cmrCodeName;
		String strBookingDateFrom;
		String strBookingDateTo;
		String consentOption;
		Date bookingDateFrom=null;
		Date bookingDateTo=null;
        String execelExportFlag=null;
		String identificationNo=null;
		//if(searchConsentDataM == null){			
		
		     cmrCodeName=getRequest().getParameter("fcmr_code");
			 strBookingDateFrom=getRequest().getParameter("f_book_date");
			 strBookingDateTo=getRequest().getParameter("t_book_date");
			 consentOption=getRequest().getParameter("consent_option");
			 execelExportFlag=getRequest().getParameter("execelExportFlag");
			 identificationNo=getRequest().getParameter("identificationNo");
			 try {
			     if(strBookingDateFrom!=null&&!"".equals(strBookingDateFrom)){                                   
                 bookingDateFrom=ORIGUtility.parseThaiToEngDate( strBookingDateFrom);
			     }
			     if(strBookingDateTo!=null&&!"".equals(strBookingDateTo)){ 
                 bookingDateTo=ORIGUtility.parseThaiToEngDate( strBookingDateTo); 
			     }
            } catch (Exception e1) {                
               // e1.printStackTrace();
            }
	        
	        logger.debug("cmrCodeName = "+cmrCodeName);
	        logger.debug("strBookingFormDate = "+strBookingDateFrom);
	        logger.debug("strBookingToDate = "+strBookingDateTo);
	        logger.debug("consentOption = "+consentOption);            
	        searchConsentDataM = new SearchConsentDataM();	        
	        searchConsentDataM.setCmrCodeName(cmrCodeName);
	        searchConsentDataM.setBookingDateFrom(bookingDateFrom);
	        searchConsentDataM.setBookingDateTo(bookingDateTo);
	        searchConsentDataM.setConsentOption(consentOption) ;   
	        searchConsentDataM.setIdentificationNo(identificationNo);
            getRequest().getSession().setAttribute("SEARCH_CONSENT_DATAM", searchConsentDataM);
		/*}else{
		    cmrCodeName = searchConsentDataM.getCmrCodeName();
		    consentOption = searchConsentDataM.getConsentOption();
		    bookingDateFrom = searchConsentDataM.getBookingDateFrom();
		    bookingDateTo= searchConsentDataM.getBookingDateTo();
			 	
		}*/
        
        try {
        	StringBuffer sql = new StringBuffer();
			ValueListM valueListM = new ValueListM();
			int index = 0;
			sql.append("SELECT  name_ncb.FIRST_NAME,name_ncb.FAMILY_NAME1,cust.IDNO,ncb.CONSENT_UPDATE_BY");
			//sql.append("SELECT  custname.THNAME,custname.THSURN,custname.IDNO,ncb.CONSENT_UPDATE_BY");
			 
			sql.append(" ,TRIM(TO_CHAR (ncb.consent_date, 'DD/MM/'))||TRIM(TO_CHAR (TO_NUMBER(TO_CHAR (ncb.consent_date,'YYYY'),'9999')+ 543, '9999'))||' '||TRIM(TO_CHAR (ncb.consent_date, 'HH24:MI:SS')) consentDate");
			//sql.append(" ,ncb.consent_date ");
			sql.append(" ,ncb.CONSENT_FLAG,ncb.CONSENT_REMARK ,ncb.TRACKING_CODE,ncb.CREATE_BY ");
			sql.append(" ,loan.MARKETING_CODE loanMktCode,prescore.MARKETING_CODE prescoreMktCode  ,app.APPLICATION_NO");
			sql.append(" ,TRIM(TO_CHAR (ncb.create_date, 'DD/MM/'))||TRIM(TO_CHAR (TO_NUMBER(TO_CHAR (ncb.create_date,'YYYY'),'9999')+ 543, '9999'))||' '||TRIM(TO_CHAR (ncb.create_date, 'HH24:MI:SS')) createDate");
			sql.append(" ,cust.PERSONAL_TYPE,cust.COBORROWER_FLAG ");
			sql.append(" FROM orig_application app,orig_application_customer cust,NCB_REQ_RESP ncb,ORIG_LOAN loan ,orig_pre_score prescore ");
			//sql.append(" FROM orig_application app,orig_application_customer cust,HPMSHP00 custname,NCB_REQ_RESP ncb,ORIG_LOAN loan ,orig_pre_score prescore ");
			sql.append(" ,id_req  id_ncb,name_req name_ncb ");
			//sql.append(" WHERE app.APPLICATION_RECORD_ID=cust.APPLICATION_RECORD_ID  and cust.PERSONAL_TYPE='A' ");			
			sql.append(" WHERE app.APPLICATION_RECORD_ID=cust.APPLICATION_RECORD_ID   ");
			//sql.append("  and cust.IDNO=custname.IDNO(+) and  app.APPLICATION_NO=ncb.APPLICATION_NO and app.APPLICATION_RECORD_ID=loan.APPLICATION_RECORD_ID(+)");
			sql.append(" AND  app.APPLICATION_NO=ncb.APPLICATION_NO and app.APPLICATION_RECORD_ID=loan.APPLICATION_RECORD_ID(+)");
			sql.append(" AND app.application_record_id=prescore.application_record_id(+) ");
			sql.append(" AND id_ncb.ID_NUMBER=cust.IDNO  AND  id_ncb.TRACKING_CODE=ncb.TRACKING_CODE AND  name_ncb.TRACKING_CODE=ncb.TRACKING_CODE	 ");
			if(cmrCodeName!=null&&!ORIGUtility.isEmptyString(cmrCodeName)){
            	sql.append(" AND loan.MARKETING_CODE=? ");
            	valueListM.setString(++index,cmrCodeName );
			
			}
			if( bookingDateFrom!=null && bookingDateTo!=null){
            	sql.append(" and (ncb.CREATE_DATE  between  TO_DATE(TO_CHAR(?,'DD/MM/YYYY')||' 00:00:00','DD/MM/YYYY HH24:MI:SS')" +
            			" and TO_DATE(TO_CHAR(?,'DD/MM/YYYY')||' 23:59:59','DD/MM/YYYY HH24:MI:SS')   ) ");
            	valueListM.setDate(++index,ORIGDisplayFormatUtil.parseDate(bookingDateFrom));
            	valueListM.setDate(++index,ORIGDisplayFormatUtil.parseDate(bookingDateTo) );
			}
			//add 20080409 add  id no
			if(identificationNo!=null&&!ORIGUtility.isEmptyString(identificationNo)){
			   sql.append(" and cust.idno =?"); 
			   valueListM.setString(++index,identificationNo );
			}
			if(consentOption!=null&&!ORIGUtility.isEmptyString(consentOption)){
			    if(!"ALL".equalsIgnoreCase(consentOption)){			        			         			         
			        sql.append(" and ncb.CONSENT_FLAG=? ");			        
			        valueListM.setString(++index,consentOption );
			      }			    
			    }
			sql.append(" order by app.APPLICATION_NO ");			
	            valueListM.setSQL(String.valueOf(sql)); 
	            valueListM.setNextPage(false);
	            valueListM.setItemsPerPage(20);
	            valueListM.setReturnToAction("page=RPT_CONSENT_SCREEN");
	            getRequest().getSession().setAttribute("VALUE_LIST", valueListM);
	            nextAction = "action=ValueListWebAction";			 			
       } catch (Exception e) {
           logger.error("exception ",e);
       }               
        return true;
    }

    /* (non-Javadoc)
     * @see com.eaf.j2ee.pattern.view.webaction.WebActionHelper#getNextActionParameter()
     */
    public String getNextActionParameter() {        
        return nextAction;
    }
    /* (non-Javadoc)
     * @see com.eaf.j2ee.pattern.view.webaction.WebAction#getNextActivityType()
     */
    public int getNextActivityType() {
        return FrontController.ACTION;
    }

	@Override
	public boolean getCSRFToken() {
		// TODO Auto-generated method stub
		return false;
	}

}
