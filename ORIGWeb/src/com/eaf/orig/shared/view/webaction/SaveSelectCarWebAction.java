/*
 * Created on Oct 27, 2007
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
import com.eaf.orig.shared.ejb.ORIGApplicationManager;
import com.eaf.orig.shared.model.ApplicationDataM;
import com.eaf.orig.shared.model.InsuranceDataM;
import com.eaf.orig.shared.model.PopulatePopupM;
import com.eaf.orig.shared.model.VehicleDataM;
import com.eaf.orig.shared.service.ORIGEJBService;
import com.eaf.orig.shared.utility.ORIGCacheUtil;
import com.eaf.orig.shared.utility.ORIGDisplayFormatUtil;
import com.eaf.orig.shared.utility.ORIGUtility;

/**
 * @author weeraya
 *
 * Type: SaveAddressWebAction
 */
public class SaveSelectCarWebAction extends WebActionHelper implements WebAction {
    Logger logger = Logger.getLogger(SaveSelectCarWebAction.class);
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
		ORIGFormHandler ORIGForm = (ORIGFormHandler) getRequest().getSession().getAttribute("ORIGForm");	
		UserDetailM userM = (UserDetailM) getRequest().getSession().getAttribute("ORIGUser");
        try{
            ApplicationDataM applicationDataM = ORIGForm.getAppForm();
            if(applicationDataM==null){
                applicationDataM = new ApplicationDataM();
                }
            String vehicleId = getRequest().getParameter("selectRadio");
            logger.debug("vehicle id is:"+vehicleId);
            
            VehicleDataM vehicleDataMOld = applicationDataM.getVehicleDataM();
            ORIGApplicationManager applicationManager = ORIGEJBService.getApplicationManager();
			ApplicationDataM applicationDataMNew = applicationManager.loadCarDetailDataM(applicationDataM, vehicleId);
			ORIGForm.setAppForm(applicationDataMNew);
			VehicleDataM vehicleM = applicationDataMNew.getVehicleDataM();
			InsuranceDataM insuranceM = vehicleM.getInsuranceDataM();
			
//            if(vehicleDataMOld != null){
//            	int vehicleIdOld = vehicleDataMOld.getVehicleID();
//            	if(vehicleIdOld != vehicleM.getVehicleID()){
//    				HashMap vehicleHash = (HashMap) getRequest().getSession(true).getAttribute("VEHICLE_TMP");
//    				if(vehicleHash == null){
//    					vehicleHash = new HashMap();
//    					getRequest().getSession(true).setAttribute("VEHICLE_TMP",vehicleHash);
//    				}
//    				vehicleHash.put(String.valueOf(vehicleIdOld), vehicleDataMOld);
//    			}
//            }
			
			String categoryDesc = cacheUtil.getORIGMasterDisplayNameDataM("CarCategoryType", vehicleM.getCategory());
			String brandDesc = cacheUtil.getORIGMasterDisplayNameDataM("CarBrand", vehicleM.getBrand());
			String modelDesc = cacheUtil.getORIGCacheDisplayNameFormDB(vehicleM.getModel(), "CarModel", vehicleM.getBrand());
			String[] modelDescs;
			if(modelDesc != null){
				modelDescs = modelDesc.split(",");
				if(modelDescs != null){
					modelDesc = modelDescs[0];
				}
			}
			String colorDesc = cacheUtil.getORIGMasterDisplayNameDataM("CarColorCode", vehicleM.getColor());
			String licenseTypeDesc = cacheUtil.getORIGMasterDisplayNameDataM("LicenseType", vehicleM.getLicenseType());
			String provinceDesc = cacheUtil.getORIGMasterDisplayNameDataM("Province", vehicleM.getProvince());
			
			//Rewrite Car
			PopulatePopupM populatePopupM10 = null;
			PopulatePopupM populatePopupM11 = null;
			PopulatePopupM populatePopupM13 = null;
			PopulatePopupM populatePopupM14 = null;
			PopulatePopupM populatePopupM15 = null;
			PopulatePopupM populatePopupM16 = null;
			PopulatePopupM populatePopupM17 = null;
			PopulatePopupM populatePopupM18 = null;
			PopulatePopupM populatePopupM19 = null;
			PopulatePopupM populatePopupM44 = null;
			PopulatePopupM populatePopupM22 = null;
			PopulatePopupM populatePopupM23 = null;
			PopulatePopupM populatePopupM24 = null;
			PopulatePopupM populatePopupM25 = null;
			PopulatePopupM populatePopupM26 = null;
			PopulatePopupM populatePopupM27 = null;
			PopulatePopupM populatePopupM28 = null;
			PopulatePopupM populatePopupM29 = null;
			PopulatePopupM populatePopupM30 = null;
			PopulatePopupM populatePopupM31 = null;
			PopulatePopupM populatePopupM32 = null;
			PopulatePopupM populatePopupM33 = null;
			PopulatePopupM populatePopupM34 = null;
			PopulatePopupM populatePopupM35 = null;
			PopulatePopupM populatePopupM36 = null;
			PopulatePopupM populatePopupM37 = null;
			PopulatePopupM populatePopupM38 = null;
			PopulatePopupM populatePopupM39 = null;
			PopulatePopupM populatePopupM40 = null;
			PopulatePopupM populatePopupM41 = null;
			PopulatePopupM populatePopupM42 = null;
			PopulatePopupM populatePopupM43 = null;
			PopulatePopupM populatePopupM48 = null;
			PopulatePopupM populatePopupM52 = null;
			PopulatePopupM populatePopupM53 = null;
			PopulatePopupM populatePopupM54 = null;
			PopulatePopupM populatePopupM55 = null;
			if(!OrigConstant.ROLE_CMR.equals(userM.getRoles().elementAt(0))){
				populatePopupM15 = new PopulatePopupM("car_register_date",ORIGDisplayFormatUtil.displayHTML(ORIGDisplayFormatUtil.datetoString(vehicleM.getRegisterDate())),PopulatePopupM.VALUE);
				populatePopupM16 = new PopulatePopupM("car_user",ORIGDisplayFormatUtil.displayHTML(vehicleM.getCarUser()),PopulatePopupM.VALUE);
				populatePopupM55 = new PopulatePopupM("car_user_tmp",ORIGDisplayFormatUtil.displayHTML(vehicleM.getCarUser()),PopulatePopupM.VALUE);
				populatePopupM17 = new PopulatePopupM("car_occupy_date",ORIGDisplayFormatUtil.displayHTML(ORIGDisplayFormatUtil.datetoString(vehicleM.getOccupyDate())),PopulatePopupM.VALUE);
				populatePopupM18 = new PopulatePopupM("car_expiry_date",ORIGDisplayFormatUtil.displayHTML(ORIGDisplayFormatUtil.datetoString(vehicleM.getExpiryDate())),PopulatePopupM.VALUE);
				populatePopupM19 = new PopulatePopupM("car_objective",ORIGDisplayFormatUtil.displayHTML(vehicleM.getObjective()),PopulatePopupM.VALUE);
				populatePopupM48 = new PopulatePopupM("car_objective_tmp",ORIGDisplayFormatUtil.displayHTML(vehicleM.getObjective()),PopulatePopupM.VALUE);
				populatePopupM11 = new PopulatePopupM("car_cc",ORIGDisplayFormatUtil.displayHTML(ORIGDisplayFormatUtil.displayTwoDigitsNumber(vehicleM.getCC())),PopulatePopupM.VALUE);
				populatePopupM13 = new PopulatePopupM("car_province",ORIGDisplayFormatUtil.displayHTML(vehicleM.getProvince()),PopulatePopupM.VALUE);
				populatePopupM52 = new PopulatePopupM("car_province_desc",ORIGDisplayFormatUtil.displayHTML(provinceDesc),PopulatePopupM.VALUE);
				populatePopupM14 = new PopulatePopupM("car_kilometers",ORIGDisplayFormatUtil.displayHTML(ORIGDisplayFormatUtil.displayTwoDigitsNumber(vehicleM.getKelometers())),PopulatePopupM.VALUE);
				populatePopupM44 = new PopulatePopupM("car_color",ORIGDisplayFormatUtil.displayHTML(vehicleM.getColor()),PopulatePopupM.VALUE);
				populatePopupM53 = new PopulatePopupM("car_color_desc",ORIGDisplayFormatUtil.displayHTML(colorDesc),PopulatePopupM.VALUE);
				populatePopupM10 = new PopulatePopupM("car_license_type",ORIGDisplayFormatUtil.displayHTML(vehicleM.getLicenseType()),PopulatePopupM.VALUE);
				populatePopupM54 = new PopulatePopupM("car_license_type_desc",ORIGDisplayFormatUtil.displayHTML(licenseTypeDesc),PopulatePopupM.VALUE);
				
				//Rewrite Insurance
				populatePopupM22 = new PopulatePopupM("owner_ins",ORIGDisplayFormatUtil.displayHTML(insuranceM.getOwerInsurance()),PopulatePopupM.VALUE);
				populatePopupM23 = new PopulatePopupM("guarantee_amount",ORIGDisplayFormatUtil.displayHTML(ORIGDisplayFormatUtil.displayTwoDigitsNumber(insuranceM.getGuaranteeAmount().doubleValue())),PopulatePopupM.VALUE);
				populatePopupM24 = new PopulatePopupM("ins_type",ORIGDisplayFormatUtil.displayHTML(insuranceM.getInsuranceType()),PopulatePopupM.VALUE);
				populatePopupM25 = new PopulatePopupM("policy_no",ORIGDisplayFormatUtil.displayHTML(insuranceM.getPolicyNo()),PopulatePopupM.VALUE);
				populatePopupM26 = new PopulatePopupM("ins_company",ORIGDisplayFormatUtil.displayHTML(insuranceM.getInsuranceCompany()),PopulatePopupM.VALUE);
				populatePopupM27 = new PopulatePopupM("premium_amount",ORIGDisplayFormatUtil.displayHTML(ORIGDisplayFormatUtil.displayTwoDigitsNumber(insuranceM.getNetPremiumAmt().doubleValue())),PopulatePopupM.VALUE);
				populatePopupM28 = new PopulatePopupM("gross_premium_amount",ORIGDisplayFormatUtil.displayHTML(ORIGDisplayFormatUtil.displayTwoDigitsNumber(insuranceM.getGrossPremiumAmt().doubleValue())),PopulatePopupM.VALUE);
				populatePopupM29 = new PopulatePopupM("ins_expiry_date",ORIGDisplayFormatUtil.displayHTML(ORIGDisplayFormatUtil.datetoString(insuranceM.getExpiryDate())),PopulatePopupM.VALUE);
				populatePopupM30 = new PopulatePopupM("ins_effective_date",ORIGDisplayFormatUtil.displayHTML(ORIGDisplayFormatUtil.datetoString(insuranceM.getEffectiveDate())),PopulatePopupM.VALUE);
				populatePopupM31 = new PopulatePopupM("acc_policy_no",ORIGDisplayFormatUtil.displayHTML(insuranceM.getAccPolicyNo()),PopulatePopupM.VALUE);
				populatePopupM32 = new PopulatePopupM("acc_insurence",ORIGDisplayFormatUtil.displayHTML(insuranceM.getAccInsurance()),PopulatePopupM.VALUE);
				populatePopupM33 = new PopulatePopupM("acc_promiun_amount",ORIGDisplayFormatUtil.displayHTML(ORIGDisplayFormatUtil.displayTwoDigitsNumber(insuranceM.getAccNetPremiunAmt().doubleValue())),PopulatePopupM.VALUE);
				populatePopupM34 = new PopulatePopupM("acc_ins_company",ORIGDisplayFormatUtil.displayHTML(insuranceM.getAccInsuranceComany()),PopulatePopupM.VALUE);
				populatePopupM35 = new PopulatePopupM("acc_expiry_date",ORIGDisplayFormatUtil.displayHTML(ORIGDisplayFormatUtil.datetoString(insuranceM.getAccExpiryDate())),PopulatePopupM.VALUE);
				populatePopupM36 = new PopulatePopupM("notification_no",ORIGDisplayFormatUtil.displayHTML(insuranceM.getNotificationNo()),PopulatePopupM.VALUE);
				populatePopupM37 = new PopulatePopupM("payment_type_ins",ORIGDisplayFormatUtil.displayHTML(insuranceM.getPaymentType()),PopulatePopupM.VALUE);
				populatePopupM38 = new PopulatePopupM("acc_gross_amount",ORIGDisplayFormatUtil.displayHTML(ORIGDisplayFormatUtil.displayTwoDigitsNumber(insuranceM.getAccGrossPremiumAmt().doubleValue())),PopulatePopupM.VALUE);
				populatePopupM39 = new PopulatePopupM("coverage_amount",ORIGDisplayFormatUtil.displayHTML(ORIGDisplayFormatUtil.displayTwoDigitsNumber(insuranceM.getCoverageAmt().doubleValue())),PopulatePopupM.VALUE);
				populatePopupM40 = new PopulatePopupM("acc_effective_date",ORIGDisplayFormatUtil.displayHTML(ORIGDisplayFormatUtil.datetoString(insuranceM.getAccEffectiveDate())),PopulatePopupM.VALUE);
				populatePopupM41 = new PopulatePopupM("confirm_amount",ORIGDisplayFormatUtil.displayHTML(ORIGDisplayFormatUtil.displayTwoDigitsNumber(insuranceM.getConfirmAmt().doubleValue())),PopulatePopupM.VALUE);
				populatePopupM42 = new PopulatePopupM("notification_date",ORIGDisplayFormatUtil.displayHTML(ORIGDisplayFormatUtil.datetoString(insuranceM.getNotificationDate())),PopulatePopupM.VALUE);
				populatePopupM43 = new PopulatePopupM("acc_confirm_amount",ORIGDisplayFormatUtil.displayHTML(ORIGDisplayFormatUtil.displayTwoDigitsNumber(insuranceM.getAccConfirmAmt().doubleValue())),PopulatePopupM.VALUE);
			}
			PopulatePopupM populatePopupM1 = new PopulatePopupM("car_category",ORIGDisplayFormatUtil.displayHTML(vehicleM.getCategory()),PopulatePopupM.VALUE);
			PopulatePopupM populatePopupM49 = new PopulatePopupM("car_category_desc",ORIGDisplayFormatUtil.displayHTML(categoryDesc),PopulatePopupM.VALUE);
			PopulatePopupM populatePopupM2 = new PopulatePopupM("car_engine_no",ORIGDisplayFormatUtil.displayHTML(vehicleM.getEngineNo()),PopulatePopupM.VALUE);
			PopulatePopupM populatePopupM3 = new PopulatePopupM("car",ORIGDisplayFormatUtil.displayHTML(vehicleM.getCar()),PopulatePopupM.VALUE);
			PopulatePopupM populatePopupM47 = new PopulatePopupM("car_type_tmp",ORIGDisplayFormatUtil.displayHTML(vehicleM.getCar()),PopulatePopupM.VALUE);
			PopulatePopupM populatePopupM4 = new PopulatePopupM("car_brand",ORIGDisplayFormatUtil.displayHTML(vehicleM.getBrand()),PopulatePopupM.VALUE);
			PopulatePopupM populatePopupM50 = new PopulatePopupM("car_brand_desc",ORIGDisplayFormatUtil.displayHTML(brandDesc),PopulatePopupM.VALUE);
			PopulatePopupM populatePopupM5 = new PopulatePopupM("car_register_no",ORIGDisplayFormatUtil.displayHTML(vehicleM.getRegisterNo()),PopulatePopupM.VALUE);
			PopulatePopupM populatePopupM6 = new PopulatePopupM("car_gear",ORIGDisplayFormatUtil.displayHTML(vehicleM.getGear()),PopulatePopupM.VALUE);
			PopulatePopupM populatePopupM46 = new PopulatePopupM("car_gear_tmp",ORIGDisplayFormatUtil.displayHTML(vehicleM.getGear()),PopulatePopupM.VALUE);
			PopulatePopupM populatePopupM7 = new PopulatePopupM("car_model",ORIGDisplayFormatUtil.displayHTML(vehicleM.getModel()),PopulatePopupM.VALUE);
			PopulatePopupM populatePopupM51 = new PopulatePopupM("car_model_desc",ORIGDisplayFormatUtil.displayHTML(modelDesc),PopulatePopupM.VALUE);
			PopulatePopupM populatePopupM8 = new PopulatePopupM("car_classis_no",ORIGDisplayFormatUtil.displayHTML(vehicleM.getChassisNo()),PopulatePopupM.VALUE);
			PopulatePopupM populatePopupM9 = new PopulatePopupM("car_weight",ORIGDisplayFormatUtil.displayHTML(ORIGDisplayFormatUtil.displayTwoDigitsNumber(vehicleM.getWeight())),PopulatePopupM.VALUE);
			PopulatePopupM populatePopupM12 = new PopulatePopupM("car_year",ORIGDisplayFormatUtil.displayHTML(String.valueOf(vehicleM.getYear())),PopulatePopupM.VALUE);
			
			
			//PopulatePopupM populatePopupM20 = new PopulatePopupM("car_park",ORIGDisplayFormatUtil.displayHTML(vehicleM.getCarParkLocation()),PopulatePopupM.VALUE);
			//PopulatePopupM populatePopupM21 = new PopulatePopupM("car_travaling",ORIGDisplayFormatUtil.displayHTML(vehicleM.getTravalingRout()),PopulatePopupM.VALUE);
				
	        //Rewrite Loan
	        String tableData = utility.getLoanTable(applicationDataMNew.getVehicleDataM().getLoanDataM(), getRequest());
	        
	        PopulatePopupM populatePopupM = new PopulatePopupM("Loan",ORIGDisplayFormatUtil.replaceDoubleQuot(tableData));
			Vector popMs = new Vector();
			popMs.add(populatePopupM);
			popMs.add(populatePopupM1);
			popMs.add(populatePopupM2);
			popMs.add(populatePopupM3);
			popMs.add(populatePopupM4);
			popMs.add(populatePopupM5);
			popMs.add(populatePopupM6);
			popMs.add(populatePopupM7);
			popMs.add(populatePopupM8);
			popMs.add(populatePopupM9);
			popMs.add(populatePopupM12);
			popMs.add(populatePopupM46);
			popMs.add(populatePopupM47);
			popMs.add(populatePopupM49);
			popMs.add(populatePopupM50);
			popMs.add(populatePopupM51);
			
			if(!OrigConstant.ROLE_CMR.equals(userM.getRoles().elementAt(0))){
				popMs.add(populatePopupM10);
				popMs.add(populatePopupM11);
				popMs.add(populatePopupM13);
				popMs.add(populatePopupM14);
				popMs.add(populatePopupM15);
				popMs.add(populatePopupM16);
				popMs.add(populatePopupM17);
				popMs.add(populatePopupM18);
				popMs.add(populatePopupM19);
				
				popMs.add(populatePopupM22);
				popMs.add(populatePopupM23);
				popMs.add(populatePopupM24);
				popMs.add(populatePopupM25);
				popMs.add(populatePopupM26);
				popMs.add(populatePopupM27);
				popMs.add(populatePopupM28);
				popMs.add(populatePopupM29);
				popMs.add(populatePopupM30);
				popMs.add(populatePopupM31);
				popMs.add(populatePopupM32);
				popMs.add(populatePopupM33);
				popMs.add(populatePopupM34);
				popMs.add(populatePopupM35);
				popMs.add(populatePopupM36);
				popMs.add(populatePopupM37);
				popMs.add(populatePopupM38);
				popMs.add(populatePopupM39);
				popMs.add(populatePopupM40);
				popMs.add(populatePopupM41);
				popMs.add(populatePopupM42);
				popMs.add(populatePopupM43);
				popMs.add(populatePopupM48);
				
				popMs.add(populatePopupM52);
				popMs.add(populatePopupM53);
				popMs.add(populatePopupM54);
				popMs.add(populatePopupM55);
			}
			//popMs.add(populatePopupM20);
			//popMs.add(populatePopupM21);
			
			getRequest().getSession(true).setAttribute("POPULATE_POPUP",popMs);
			popMs = (Vector)getRequest().getSession(true).getAttribute("POPULATE_POPUP");
			
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
