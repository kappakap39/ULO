/*
 * Created on Sep 27, 2007
 * Created by weeraya
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

import java.math.BigDecimal;
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
import com.eaf.orig.shared.model.AddressDataM;
import com.eaf.orig.shared.model.ApplicationDataM;
import com.eaf.orig.shared.model.PersonalInfoDataM;
import com.eaf.orig.shared.model.PopulatePopupM;
import com.eaf.orig.shared.utility.ORIGCacheUtil;
import com.eaf.orig.shared.utility.ORIGDisplayFormatUtil;
import com.eaf.orig.shared.utility.ORIGUtility;

/**
 * @author weeraya
 *
 * Type: SaveAddressWebAction
 */
public class SaveAddressWebAction extends WebActionHelper implements WebAction {
    Logger logger = Logger.getLogger(SaveAddressWebAction.class);
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
        ORIGFormHandler formHandler = (ORIGFormHandler) getRequest().getSession().getAttribute(OrigConstant.FORMHANDLER_NAME);
        ORIGUtility utility = new ORIGUtility();
        ORIGCacheUtil cacheUtil = new ORIGCacheUtil();
        
        try{
	      
	        String address_no = getRequest().getParameter("address_no");
	        String room = getRequest().getParameter("room");
	        String floor = getRequest().getParameter("floor");
	        String apartment = getRequest().getParameter("apartment");
	        String moo = getRequest().getParameter("moo");
	        String soi = getRequest().getParameter("soi");
	        String road = getRequest().getParameter("road");
	        String amphur = getRequest().getParameter("amphur");
	        String tambol = getRequest().getParameter("tambol");
	        String province = getRequest().getParameter("province");
	        String zipcode = getRequest().getParameter("zipcode");
	        String phone1 = getRequest().getParameter("phone1");
	        String phone2 = getRequest().getParameter("phone2");
	        String ext1 = getRequest().getParameter("ext1");
	        String ext2 = getRequest().getParameter("ext2");
	        String mobile_no = getRequest().getParameter("mobile_no");
	        String contact_person = getRequest().getParameter("contact_person");
	        String reside_year = getRequest().getParameter("reside_year");
	        String reside_month = getRequest().getParameter("reside_month");
	        String fax_no = getRequest().getParameter("fax_no");
	        String email = getRequest().getParameter("email");
	        String status = getRequest().getParameter("status");
	        String house_id = getRequest().getParameter("house_id");
	        String remark = getRequest().getParameter("remark");
	        String seq = getRequest().getParameter("address_seq");
	        String addressType = getRequest().getParameter("address_type");
	        String personalType = (String) getRequest().getSession().getAttribute("PersonalType");
	        
	        logger.debug("Address seq = "+seq);
	        logger.debug("Address type = "+addressType);
	        logger.debug("Personal Type = "+personalType);
	  	       
	        ApplicationDataM applicationDataM = formHandler.getAppForm();
	        PersonalInfoDataM personalInfoDataM = new PersonalInfoDataM();
	    	if(OrigConstant.PERSONAL_TYPE_GUARANTOR.equals(personalType)){
	    		personalInfoDataM = (PersonalInfoDataM) getRequest().getSession(true).getAttribute("MAIN_POPUP_DATA");
	    	}else if(OrigConstant.PERSONAL_TYPE_SUP_CARD.equals(personalType)){
	    		personalInfoDataM = (PersonalInfoDataM) getRequest().getSession(true).getAttribute("SUPCARD_POPUP_DATA");
	    	}else{
	    		personalInfoDataM = utility.getPersonalInfoByType(formHandler.getAppForm(),OrigConstant.PERSONAL_TYPE_APPLICANT);
	    	}
	        Vector addressVect = personalInfoDataM.getAddressVect();
	        //Get current address data
	        AddressDataM addressDataM;
	        
	        if(Integer.parseInt(seq) == 0){//Add new address
	            addressDataM = new AddressDataM();
	            addressDataM.setAddressType(addressType);
	            addressDataM.setAddressSeq(personalInfoDataM.getAddressIndex());
	            personalInfoDataM.setAddressIndex(personalInfoDataM.getAddressIndex()+1);
	            logger.debug("addressDataM.getAddressSeq() = "+addressDataM.getAddressSeq());
	            personalInfoDataM.getAddressVect().add(addressDataM);
	        }else{
	            addressDataM = utility.getAddressBySeq(personalInfoDataM, Integer.parseInt(seq));
	        }
	        addressDataM.setAddressType(addressType);
	        addressDataM.setAddressNo(address_no);
	        addressDataM.setRoom(room);
	        addressDataM.setFloor(floor);
	        addressDataM.setApartment(apartment);
	        addressDataM.setMoo(moo);
	        addressDataM.setSoi(soi);
	        addressDataM.setRoad(road);
	        addressDataM.setAmphur(amphur);
	        addressDataM.setTambol(tambol);
	        addressDataM.setProvince(province);
	        addressDataM.setZipcode(zipcode);
	        addressDataM.setPhoneNo1(phone1);
	        addressDataM.setPhoneNo2(phone2);
	        addressDataM.setPhoneExt1(ext1);
	        addressDataM.setPhoneExt2(ext2);
	        addressDataM.setMobileNo(mobile_no);
	        addressDataM.setContactPerson(contact_person);
	        int iResideYear=utility.stringToInt(reside_year);
	        int iResideMonth=utility.stringToInt(reside_month);
	        addressDataM.setResideYear(new BigDecimal(iResideYear+(iResideMonth/100d )));
	        addressDataM.setFaxNo(fax_no);
	        addressDataM.setEmail(email);
	        addressDataM.setStatus(status);
	        addressDataM.setHouseID(house_id);
	        addressDataM.setRemark(remark);
	        addressDataM.setAddressFormat(OrigConstant.ADDRESS_FORMAT_NORMAL);
	        addressDataM.setOrigOwner("Y");
	        
	        UserDetailM userM = (UserDetailM) getRequest().getSession().getAttribute("ORIGUser");
	        if(ORIGUtility.isEmptyString(addressDataM.getCreateBy())){
	        	addressDataM.setCreateBy(userM.getUserName());
	        }else{
	        	addressDataM.setUpdateBy(userM.getUserName());
	        }
	        
	        //Rewrite
	        String tableData = utility.getAddressTable(addressVect, getRequest());
	        
	        PopulatePopupM populatePopupM = new PopulatePopupM("Address",ORIGDisplayFormatUtil.replaceDoubleQuot(tableData.toString()));
			Vector popMs = new Vector();
			popMs.add(populatePopupM);
			getRequest().getSession(true).setAttribute("POPULATE_POPUP",popMs);
			popMs = (Vector)getRequest().getSession(true).getAttribute("POPULATE_POPUP");
			getRequest().getSession().setAttribute("MAIN_POPUP_DATA", personalInfoDataM);
        }catch(Exception e){
            logger.error("Error in SaveAddressWebAction.preModelRequest() >> ", e);
        }
        
        return true;
    }

    /* (non-Javadoc)
     * @see com.eaf.j2ee.pattern.view.webaction.WebAction#getNextActivityType()
     */
    public int getNextActivityType() {
        return FrontController.FORWARD;
    }
    /* (non-Javadoc)
	 * @see com.bara.j2ee.pattern.view.webaction.WebActionHelper#getNextActionParameter()
	 */
	public String getNextActionParameter() {
		return "orig/appform/filterMainScreen.jsp";
	}

	@Override
	public boolean getCSRFToken() {
		// TODO Auto-generated method stub
		return false;
	}

}
