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

import javax.servlet.http.HttpServletRequest;

import org.apache.log4j.Logger;

import com.eaf.orig.formcontrol.view.form.ORIGFormHandler;
import com.eaf.orig.profile.model.UserDetailM;
import com.eaf.orig.shared.constant.OrigConstant;
import com.eaf.orig.shared.model.ApplicationDataM;
import com.eaf.orig.shared.model.InsuranceDataM;
import com.eaf.orig.shared.model.VehicleDataM;
import com.eaf.orig.shared.utility.ORIGUtility;
import com.eaf.orig.shared.view.form.ORIGSubForm;

/**
 * @author weeraya
 *
 * Type: InsurenceSubForm
 */
public class InsurenceSubForm extends ORIGSubForm {
    Logger logger = Logger.getLogger(InsurenceSubForm.class);
    /* (non-Javadoc)
     * @see com.eaf.orig.shared.view.form.ORIGSubForm#setProperties(javax.servlet.http.HttpServletRequest, java.lang.Object)
     */
    public void setProperties(HttpServletRequest request, Object appForm) {
        try{
	        String owner_ins = request.getParameter("owner_ins");
	        String guarantee_amount = request.getParameter("guarantee_amount");
	        String ins_type = request.getParameter("ins_type");
	        String policy_no = request.getParameter("policy_no");
	        String ins_company = request.getParameter("ins_company");
	        String premium_amount = request.getParameter("premium_amount");
	        String gross_premium_amount = request.getParameter("gross_premium_amount");
	        String expiry_date = request.getParameter("ins_expiry_date");
	        String effective_date = request.getParameter("ins_effective_date");
	        String acc_policy_no = request.getParameter("acc_policy_no");
	        String acc_insurence = request.getParameter("acc_insurence");
	        String acc_promiun_amount = request.getParameter("acc_promiun_amount");
	        String acc_ins_company = request.getParameter("acc_ins_company");
	        String acc_expiry_date = request.getParameter("acc_expiry_date");
	        String notification_no = request.getParameter("notification_no");
	        String payment_type_ins = request.getParameter("payment_type_ins");
	        String acc_gross_amount = request.getParameter("acc_gross_amount");
	        String coverage_amount = request.getParameter("coverage_amount");
	        String acc_effective_date = request.getParameter("acc_effective_date");
	        String confirm_amount = request.getParameter("confirm_amount");
	        String notification_date = request.getParameter("notification_date");
	        String acc_confirm_amount = request.getParameter("acc_confirm_amount");
	        String payment_inst_by= request.getParameter("payment_inst_by");
	        String vat_amt= request.getParameter("vat_amt");
	        String  duty_amt= request.getParameter("duty_amt");
	        String wh_tax_amt= request.getParameter("wh_tax_amt");
	        String wh_tax_req= request.getParameter("wh_tax_req");
	        String acc_vat_amt= request.getParameter("acc_vat_amt");
	        String acc_duty_amt= request.getParameter("acc_duty_amt");
	        String  acc_wh_tax_amt= request.getParameter("acc_wh_tax_amt");
	        String acc_wh_tax_req= request.getParameter("acc_wh_tax_req");
	        String disc_cust_amt= request.getParameter("disc_cust_amt");
	        String rebate_dealer_amt= request.getParameter("rebate_dealer_amt");
	        String deducted_by_sales= request.getParameter("deducted_by_sales");
	        String acc_disc_cust_amt= request.getParameter("acc_disc_cust_amt");
	        String acc_rebate_dealer_amt= request.getParameter("acc_rebate_dealer_amt");
	        String acc_deducted_by_sales = request.getParameter("acc_deducted_by_sales");
	        ORIGFormHandler formHandler = (ORIGFormHandler) request.getSession().getAttribute(OrigConstant.FORMHANDLER_NAME);
	        ORIGUtility utility = new ORIGUtility();
	        
	        ApplicationDataM applicationDataM = formHandler.getAppForm();
	        
	        VehicleDataM vehicleDataM = applicationDataM.getVehicleDataM();
	        if(vehicleDataM == null){
	            vehicleDataM = new VehicleDataM();
	            applicationDataM.setVehicleDataM(vehicleDataM);
	        }
	        InsuranceDataM insuranceDataM = vehicleDataM.getInsuranceDataM();
	        if(insuranceDataM == null){
	            insuranceDataM = new InsuranceDataM();
	            vehicleDataM.setInsuranceDataM(insuranceDataM);
	        }
	        String dateFormat = "dd/mm/yyyy";
	        insuranceDataM.setOwerInsurance(owner_ins);
	        insuranceDataM.setGuaranteeAmount(utility.stringToBigDecimal(guarantee_amount));
	        insuranceDataM.setInsuranceType(ins_type);
	        insuranceDataM.setPolicyNo(policy_no);
	        insuranceDataM.setInsuranceCompany(ins_company);
	        insuranceDataM.setNetPremiumAmt(utility.stringToBigDecimal(premium_amount));
	        insuranceDataM.setGrossPremiumAmt(utility.stringToBigDecimal(gross_premium_amount));
	        insuranceDataM.setExpiryDate(ORIGUtility.parseThaiToEngDate(expiry_date));
	        insuranceDataM.setEffectiveDate(ORIGUtility.parseThaiToEngDate(effective_date));
	        insuranceDataM.setAccPolicyNo(acc_policy_no);
	        insuranceDataM.setAccNetPremiunAmt(utility.stringToBigDecimal(acc_promiun_amount));
	        insuranceDataM.setAccInsuranceComany(acc_ins_company);
	        insuranceDataM.setAccExpiryDate(ORIGUtility.parseThaiToEngDate(acc_expiry_date));
	        insuranceDataM.setNotificationNo(notification_no);
	        insuranceDataM.setPaymentType(payment_type_ins);
	        insuranceDataM.setAccGrossPremiumAmt(utility.stringToBigDecimal(acc_gross_amount));
	        insuranceDataM.setAccInsurance(acc_insurence);
	        insuranceDataM.setCoverageAmt(utility.stringToBigDecimal(coverage_amount));
	        insuranceDataM.setAccEffectiveDate(ORIGUtility.parseThaiToEngDate(acc_effective_date));
	        insuranceDataM.setConfirmAmt(utility.stringToBigDecimal(confirm_amount));
	        insuranceDataM.setNotificationDate(ORIGUtility.parseThaiToEngDate(notification_date));
	        insuranceDataM.setAccConfirmAmt(utility.stringToBigDecimal(acc_confirm_amount));
	        insuranceDataM.setPayInsuranceBy(payment_inst_by);
	        insuranceDataM.setVatAmount(utility.stringToBigDecimal(vat_amt));
	        insuranceDataM.setDutyAmount(utility.stringToBigDecimal(duty_amt));
	        insuranceDataM.setWithHoldingTaxAmount(utility.stringToBigDecimal(wh_tax_amt));
	        insuranceDataM.setWithHoldingTaxRequire(wh_tax_req);
	        insuranceDataM.setAccountVatAmount(utility.stringToBigDecimal(acc_vat_amt));
	        insuranceDataM.setAccountDutyAmount(utility.stringToBigDecimal(acc_duty_amt));
	        insuranceDataM.setAccountWithHoldingTaxAmount(utility.stringToBigDecimal(acc_wh_tax_amt));
	        insuranceDataM.setAccountWithHoldingTaxRequire(acc_wh_tax_req);
	        insuranceDataM.setDistcountToCustAmount(utility.stringToBigDecimal(disc_cust_amt));
	        insuranceDataM.setRebateToDealerAmount(utility.stringToBigDecimal(rebate_dealer_amt));
	        insuranceDataM.setDeductBySales(utility.stringToBigDecimal(deducted_by_sales));
	        insuranceDataM.setAccountDistcountToCustAmount(utility.stringToBigDecimal(acc_disc_cust_amt));
	        insuranceDataM.setAccountRebateToDealerAmount(utility.stringToBigDecimal(acc_rebate_dealer_amt));
	        insuranceDataM.setAccountDeductBySales(utility.stringToBigDecimal(acc_deducted_by_sales));  
	        UserDetailM userM = (UserDetailM) request.getSession().getAttribute("ORIGUser");
	        if(ORIGUtility.isEmptyString(insuranceDataM.getCreateBy())){
	        	insuranceDataM.setCreateBy(userM.getUserName());
	        }else{
	        	insuranceDataM.setUpdateBy(userM.getUserName());
	        }
        }catch(Exception e){
            logger.error("Error in InsurenceSubForm.setProperties()", e);
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
        // TODO Auto-generated method stub
        return false;
    }

}
