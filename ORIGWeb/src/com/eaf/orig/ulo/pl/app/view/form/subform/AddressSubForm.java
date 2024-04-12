package com.eaf.orig.ulo.pl.app.view.form.subform;

import java.util.HashMap;
import java.util.Vector;

import javax.servlet.http.HttpServletRequest;
import org.apache.log4j.Logger;
import com.eaf.j2ee.pattern.util.ErrorUtil;
import com.eaf.orig.profile.model.UserDetailM;
import com.eaf.orig.shared.constant.OrigConstant;
import com.eaf.orig.shared.util.OrigUtil;
import com.eaf.orig.shared.utility.ORIGCacheUtil;
import com.eaf.orig.shared.view.form.ORIGSubForm;
import com.eaf.orig.ulo.pl.ajax.CardLinkLine;
import com.eaf.orig.ulo.pl.formcontrol.view.form.PLOrigFormHandler;
import com.eaf.orig.ulo.pl.model.app.PLAddressDataM;
import com.eaf.orig.ulo.pl.model.app.PLApplicationDataM;
import com.eaf.orig.ulo.pl.model.app.PLPersonalInfoDataM;

public class AddressSubForm extends ORIGSubForm {
	private Logger logger = Logger.getLogger(this.getClass());
	@Override
	public void setProperties(HttpServletRequest request, Object appForm) {
		PLOrigFormHandler formHandler = (PLOrigFormHandler)request.getSession().getAttribute("PLORIGForm");
		PLApplicationDataM applicationM = formHandler.getAppForm();
		PLPersonalInfoDataM personalM = applicationM.getPLPersonalInfoDataM(PLPersonalInfoDataM.PersonalType.PERSONAL_TYPE_APPLICANT);
		
		String mailaddress = request.getParameter("mailling_address");
		String card_link_address1 = request.getParameter("card_link_address1");
		String card_link_address2 = request.getParameter("card_link_address2");
		
		personalM.setMailingAddress(mailaddress);
		personalM.setAddressDocLine1(card_link_address1);
		personalM.setAddressDocLine2(card_link_address2);
	}


	@Override
	public boolean validateSubForm(HttpServletRequest request) {
		 logger.debug("validateSubForm ");
         boolean result = false;
         PLOrigFormHandler formHandler = (PLOrigFormHandler) request.getSession().getAttribute(OrigConstant.PL_FORMHANDLER_NAME);
         /**New Logic Manual Validate Subform #Sankom*/         
         String errorMsg="";      
         PLApplicationDataM applicationM = formHandler.getAppForm();
 		 UserDetailM userM = (UserDetailM) request.getSession().getAttribute("ORIGUser"); 		 
    	 PLPersonalInfoDataM personalM = applicationM.getPLPersonalInfoDataM(PLPersonalInfoDataM.PersonalType.PERSONAL_TYPE_APPLICANT);
 		 
         if(OrigConstant.SUBMIT_TYPE_SEND.equals(formHandler.getSaveType()) 
        		 &&  !OrigConstant.ROLE_DF_REJECT.equals(userM.getCurrentRole()) &&
        		 	(OrigConstant.Mandatory.MANDATORY_TYPE_SUMMIT == formHandler.getMandatoryType())){ //Praisan 20121218 validate only type submit (1)
        	 PLAddressDataM addressDataM= personalM.getAddressDataM(OrigConstant.ADDRESS_TYPE_HOME);
        	 if(!OrigConstant.ADDRESS_TYPE_HOME.equals(addressDataM.getAddressType())){
        		 errorMsg = ErrorUtil.getShortErrorMessage(request, "ADDRESS_HOME");            	 
            	 formHandler.PushErrorMessage("addressHome", errorMsg);
        		 result=true;
        	 }
         }
         ORIGCacheUtil origc = new ORIGCacheUtil();	
         Vector<PLAddressDataM> addressVect = personalM.getAddressVect();
         if(null != addressVect){
        	for(PLAddressDataM addressM : addressVect){
        		
        		String number = addressM.getAddressNo();
        		String building = addressM.getBuilding();
        		String soi = addressM.getSoi();
        		String moo = addressM.getMoo();
        		String road = addressM.getRoad();
        		
        		String province = addressM.getProvince();
        		String province_desc = origc.getManualORIGCache(addressM.getProvince(),"Province");
        		String amphur_desc = origc.getManualORIGCache(addressM.getAmphur(),"Amphur");
        		String tambol_desc = origc.getManualORIGCache(addressM.getTambol(),"Tambol");
        		
        		String companyTitle = addressM.getCompanyTitle();
        		String companyName = addressM.getCompanyName();
        		String department = personalM.getDepartment();
        		
        		CardLinkLine cardlink = new CardLinkLine();		
        		String cardLinkLine1 = cardlink.getCardLinkLine1(addressM.getAddressType(), number, building, soi, moo, road, companyTitle, companyName, department);
        		String cardLinkLine2 = cardlink.getCardLinkLine2(province, province_desc, amphur_desc, tambol_desc);
        		
        		String typeDesc = origc.getDisplayNameCache(12, addressM.getAddressType());
        		if(null == typeDesc){
        			typeDesc = "";
        		}        		
        		if(OrigUtil.isEmptyString(cardLinkLine1)){	
        			formHandler.PushErrorMessage("",typeDesc+" "+ErrorUtil.getShortErrorMessage(request,"REQUIRE_CARDLINK1"));
        		}else{
        			if(!OrigUtil.isEmptyString(cardLinkLine1)){
        				if(cardLinkLine1.length() > 50){
        					formHandler.PushErrorMessage("",typeDesc+" "+ErrorUtil.getShortErrorMessage(request,"ERROR_CARDLINK1_MORE_50"));
        				}
        			}
        		}
        		
        		if(OrigUtil.isEmptyString(cardLinkLine2)){	
        			formHandler.PushErrorMessage("",typeDesc+" "+ErrorUtil.getShortErrorMessage(request,"REQUIRE_CARDLINK2"));
        		}else{
        			if(!OrigUtil.isEmptyString(cardLinkLine2)){	
        				if(cardLinkLine2.length() > 40){
        					formHandler.PushErrorMessage("",typeDesc+" "+ErrorUtil.getShortErrorMessage(request,"ERROR_CARDLINK2_MORE_40"));
        				}
        			}
        		}
        		
			}
         }
         return result;
	}


	@Override
	public HashMap validateForm(HttpServletRequest request, Object appForm) {
		return null;
	}

}
