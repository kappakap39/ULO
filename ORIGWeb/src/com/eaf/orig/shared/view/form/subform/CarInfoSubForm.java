/*
 * Created on Sep 21, 2007
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
package com.eaf.orig.shared.view.form.subform;

import java.util.HashMap;
import java.util.Vector;

import javax.servlet.http.HttpServletRequest;

import org.apache.log4j.Logger;

import com.eaf.orig.formcontrol.view.form.ORIGFormHandler;
import com.eaf.orig.profile.model.UserDetailM;
import com.eaf.orig.shared.constant.OrigConstant;
import com.eaf.orig.shared.model.ApplicationDataM;
import com.eaf.orig.shared.model.VehicleDataM;
import com.eaf.orig.shared.utility.ORIGUtility;
import com.eaf.orig.shared.view.form.ORIGSubForm;

/**
 * @author weeraya
 *
 * Type: CarInfoSubForm
 */
public class CarInfoSubForm extends ORIGSubForm {
    Logger logger = Logger.getLogger(CarInfoSubForm.class);
    /* (non-Javadoc)
     * @see com.eaf.orig.shared.view.form.ORIGSubForm#setProperties(javax.servlet.http.HttpServletRequest, java.lang.Object)
     */
    public void setProperties(HttpServletRequest request, Object appForm) {
        try{
            ORIGFormHandler formHandler = (ORIGFormHandler) request.getSession().getAttribute(OrigConstant.FORMHANDLER_NAME);
            ORIGUtility utility = new ORIGUtility();
            
            ApplicationDataM applicationDataM = formHandler.getAppForm();
            
	        String car_category = request.getParameter("car_category");
	        String car_engine_no = request.getParameter("car_engine_no");
	        String car = request.getParameter("car_type_tmp");
	        String car_brand = request.getParameter("car_brand");
	        String car_register_no = request.getParameter("car_register_no");
	        String car_gear = request.getParameter("car_gear_tmp");
	        String car_model = request.getParameter("car_model");
	        String car_classis_no = request.getParameter("car_classis_no");
	        String car_weight = request.getParameter("car_weight");
	        String car_license_type = request.getParameter("car_license_type");
	        String car_cc = request.getParameter("car_cc");
	        String car_year = request.getParameter("car_year");
	        String car_province = request.getParameter("car_province");
	        String car_kilometers = request.getParameter("car_kilometers");
	        String car_register_date = request.getParameter("car_register_date");
	        String car_user = request.getParameter("car_user");
	        String car_occupy_date = request.getParameter("car_occupy_date");
	        String car_expiry_date = request.getParameter("car_expiry_date");
	        String car_objective = request.getParameter("car_objective_tmp");
	        //String car_park = request.getParameter("car_park");
	        //String car_travaling = request.getParameter("car_travaling");
	        String car_color = request.getParameter("car_color");
	        
	        String dateFormat = "dd/mm/yyyy";
			
	        VehicleDataM vehicleDataM = applicationDataM.getVehicleDataM();
	        logger.debug("vehicleDataM = "+vehicleDataM);
	        
	        if(vehicleDataM == null){
	            vehicleDataM = new VehicleDataM();
	            applicationDataM.setVehicleDataM(vehicleDataM);
	        }
	        logger.debug("vehicleDataM.getVehicleID() = "+vehicleDataM.getVehicleID());
	        if(car_gear==null || ("null").equals(car_gear)){
	          car_gear="";	
	        }
	        if(car==null || ("null").equals(car)){
		          car="";	
		    }
	        if(car_objective==null || ("null").equals(car_objective)){
	        	car_objective="";	
		    }
	        vehicleDataM.setCategory(car_category);
	        vehicleDataM.setEngineNo(car_engine_no);
	        vehicleDataM.setCar(car);
	        vehicleDataM.setBrand(car_brand);
	        vehicleDataM.setRegisterNo(car_register_no);
	        vehicleDataM.setGear(car_gear);
	        vehicleDataM.setModel(car_model);
	        vehicleDataM.setChassisNo(car_classis_no);
	        vehicleDataM.setWeight(utility.stringToDouble(car_weight));
	        vehicleDataM.setLicenseType(car_license_type);
	        vehicleDataM.setCC(utility.stringToDouble(car_cc));
	        vehicleDataM.setYear(utility.stringToInt(car_year));
	        vehicleDataM.setProvince(car_province);
	        vehicleDataM.setKelometers(utility.stringToDouble(car_kilometers));
	        vehicleDataM.setRegisterDate(ORIGUtility.parseThaiToEngDate(car_register_date));
	        vehicleDataM.setCarUser(car_user);
	        vehicleDataM.setOccupyDate(ORIGUtility.parseThaiToEngDate(car_occupy_date));
	        vehicleDataM.setExpiryDate(ORIGUtility.parseThaiToEngDate(car_expiry_date));
	        vehicleDataM.setCarParkLocation(OrigConstant.DefaultValue.CAR_PARK);
	        vehicleDataM.setLicenseType(car_license_type);
	        vehicleDataM.setTravalingRout(OrigConstant.DefaultValue.CAR_TRAVELING);
	        if(ORIGUtility.isEmptyString(car_objective)){
	        	car_objective = OrigConstant.DefaultValue.DEFAULT_OBJECTIVE;
	        }
	        vehicleDataM.setObjective(car_objective);
	        vehicleDataM.setColor(car_color);
	        
	        UserDetailM userM = (UserDetailM) request.getSession().getAttribute("ORIGUser");
	        vehicleDataM.setAppRecordID(applicationDataM.getAppRecordID());
	        if(ORIGUtility.isEmptyString(vehicleDataM.getCreateBy())){
	        	vehicleDataM.setCreateBy(userM.getUserName());
	        }else{
	        	vehicleDataM.setUpdateBy(userM.getUserName());
	        }
	        
        }catch(Exception e){
            logger.error("Error in CarInfoSubForm.setProperties()", e);
        }
    }

    /* (non-Javadoc)
     * @see com.eaf.orig.shared.view.form.ORIGSubForm#validateForm(javax.servlet.http.HttpServletRequest, java.lang.Object)
     */
    public HashMap validateForm(HttpServletRequest request, Object appForm) {
         
        return null;
    }
    
    /* (non-Javadoc)
     * @see com.eaf.orig.shared.view.form.ORIGSubForm#validateSubForm(javax.servlet.http.HttpServletRequest)
     */
    public boolean validateSubForm(HttpServletRequest request) {
        boolean result = false;
        ORIGFormHandler formHandler = (ORIGFormHandler) request.getSession().getAttribute(OrigConstant.FORMHANDLER_NAME);
        ApplicationDataM appForm = formHandler.getAppForm();
        if (appForm == null) {
            appForm = new ApplicationDataM();
        }
        ORIGUtility utility = new ORIGUtility();
        Vector vErrors = formHandler.getFormErrors();
        Vector vErrorFields = formHandler.getErrorFields();
        Vector vNotErrorFields = formHandler.getNotErrorFields();
        if (vErrors == null) {
            vErrors = new Vector();
        }
        String errorMsg = null;

        if (OrigConstant.CAR_TYPE_USE.equals(request.getParameter("car"))) {
            if (ORIGUtility.isEmptyString(request.getParameter("car_engine_no"))) {
                errorMsg = "Engine No. is required.";
                vErrors.add(errorMsg);
                vErrorFields.add("car_engine_no");
                result = true;
            } else {
                vNotErrorFields.add("car_engine_no");
            }
            if (ORIGUtility.isEmptyString(request.getParameter("car_register_no"))) {
                errorMsg = "Register No. is required.";
                vErrors.add(errorMsg);
                vErrorFields.add("car_register_no");
                result = true;
            } else {
                vNotErrorFields.add("car_register_no");
            }
            if (ORIGUtility.isEmptyString(request.getParameter("car_classis_no"))) {
                errorMsg = "Chassis No. is required.";
                vErrors.add(errorMsg);
                vErrorFields.add("car_classis_no");
                result = true;
            } else {
                vNotErrorFields.add("car_classis_no");
            }
            if (ORIGUtility.isEmptyString(request.getParameter("car_weight"))) {
                errorMsg = "Car Weight is required.";
                vErrors.add(errorMsg);
                vErrorFields.add("car_weight");
                result = true;
            } else {
                vNotErrorFields.add("car_weight");
            }
            if (ORIGUtility.isZero(request.getParameter("car_year"))) {
                errorMsg = "Car Year is required.";
                vErrors.add(errorMsg);
                vErrorFields.add("car_year");
                result = true;
            } else {
                vNotErrorFields.add("car_year");
            }
            if (ORIGUtility.isZero(request.getParameter("car_kilometers"))) {
                errorMsg = "Car Kilometers is required.";
                vErrors.add(errorMsg);
                vErrorFields.add("car_kilometers");
                result = true;
            } else {
                vNotErrorFields.add("car_kilometers");
            }
            //Occupation
            if (ORIGUtility.isEmptyString(request.getParameter("car_register_date"))) {
                errorMsg = "Register Date is required.";
                vErrors.add(errorMsg);
                vErrorFields.add("car_register_date");
                result = true;
            } else {
                vNotErrorFields.add("car_register_date");
            }
            if (ORIGUtility.isEmptyString(request.getParameter("car_expiry_date"))) {
                errorMsg = "Expiry Date is required.";
                vErrors.add(errorMsg);
                vErrorFields.add("car_expiry_date");
                result = true;
            } else {
                vNotErrorFields.add("car_expiry_date");
            }
            if (ORIGUtility.isEmptyString(request.getParameter("car_occupy_date"))) {
                errorMsg = "Occupy Date is required.";
                vErrors.add(errorMsg);
                vErrorFields.add("car_occupy_date");
                result = true;
            } else {
                vNotErrorFields.add("car_occupy_date");
            }
        } else {
            vNotErrorFields.add("car_engine_no");
            vNotErrorFields.add("car_register_no");
            vNotErrorFields.add("car_classis_no");
            vNotErrorFields.add("car_weight");
            vNotErrorFields.add("car_year");
            vNotErrorFields.add("car_kilometers");
            vNotErrorFields.add("car_register_date");
            vNotErrorFields.add("car_expiry_date");
            vNotErrorFields.add("car_occupy_date");
        }
        return result;
    }

}
