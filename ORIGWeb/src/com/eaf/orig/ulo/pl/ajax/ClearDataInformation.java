package com.eaf.orig.ulo.pl.ajax;

import java.util.Vector;

import javax.servlet.http.HttpServletRequest;
import org.apache.log4j.Logger;

import com.eaf.orig.profile.model.UserDetailM;
import com.eaf.orig.shared.constant.OrigConstant;
import com.eaf.orig.shared.servlet.AjaxDisplayGenerateInf;
import com.eaf.orig.shared.util.OrigUtil;
import com.eaf.orig.shared.utility.SerializeUtil;
import com.eaf.orig.ulo.pl.app.dao.exception.PLOrigApplicationException;
import com.eaf.orig.ulo.pl.app.utility.HTMLRenderUtil;
import com.eaf.orig.ulo.pl.app.utility.JsonObjectUtil;
import com.eaf.orig.ulo.pl.app.view.webaction.AddressUtil;
import com.eaf.orig.ulo.pl.formcontrol.view.form.PLOrigFormHandler;
import com.eaf.orig.ulo.pl.model.app.PLApplicationDataM;
import com.eaf.orig.ulo.pl.model.app.PLCardDataM;
import com.eaf.orig.ulo.pl.model.app.PLPersonalInfoDataM;
import com.eaf.xrules.ulo.pl.model.app.rule.PLXRulesSpecialCustomerDataM;
import com.eaf.xrules.ulo.pl.model.app.rule.PLXRulesVerificationResultDataM;

public class ClearDataInformation implements AjaxDisplayGenerateInf{	
	Logger logger = Logger.getLogger(ClearDataInformation.class);	
	@Override
	public String getDisplayObject(HttpServletRequest request)throws PLOrigApplicationException{	
		JsonObjectUtil jsonObj = new JsonObjectUtil();	
		try{	
			UserDetailM userM = (UserDetailM)request.getSession().getAttribute("ORIGUser");	
			PLApplicationDataM appM = PLOrigFormHandler.getPLApplicationDataM(request);	
			
			this.ClearDataPLApplicationDataM(appM);
			
			String cardtype = request.getParameter("cardtype");
			logger.debug("cardtype = "+cardtype);
			
			this.ClearPLPersonalInfoDataM(jsonObj, appM, cardtype);
			this.ClearPLXRulesVerificationResultDataM(jsonObj, appM, userM);	
			this.ClearPLAddressDataM(jsonObj, appM, request);
			this.ClearPLSpecialCustomerDataM(jsonObj, appM);
			this.clearDataOrigCard(jsonObj, appM); //Praisan for clear currnet credit line
			
			PLApplicationDataM sensitiveappM = (PLApplicationDataM)SerializeUtil.clone(appM);
			request.getSession(true).setAttribute(PLOrigFormHandler.PLORIGSensitive,sensitiveappM);
						
		}catch(Exception e){
			logger.fatal("Error ",e);
		}		
		return jsonObj.returnJson();
	}
	public void clearDataOrigCard(JsonObjectUtil jsonObj ,PLApplicationDataM appM){
		PLPersonalInfoDataM personalM = appM.getPLPersonalInfoDataM(PLPersonalInfoDataM.PersonalType.PERSONAL_TYPE_APPLICANT);
		if(personalM != null){
			PLCardDataM cardM = personalM.getCardInformation();
			if(cardM != null){
				cardM.setCurCreditLine(null);
			}
		}
		jsonObj.CreateJsonObject("card_info_current_credit", "");
	}
	public void ClearDataPLApplicationDataM(PLApplicationDataM appM){
		appM.setDocCheckListVect(null);
		appM.setDocListNote(null);
		appM.setDocListNotepad(null);
		appM.setDocListResult(null);
		appM.setDocListResultCode(null);
		appM.setDocumentCheckListDataM(null);
	}
	public void ClearPLPersonalInfoDataM(JsonObjectUtil jsonObj ,PLApplicationDataM appM, String cardtype){
		if(null == appM) appM = new PLApplicationDataM();
		
		PLPersonalInfoDataM personalM = appM.getPLPersonalInfoDataM(PLPersonalInfoDataM.PersonalType.PERSONAL_TYPE_APPLICANT);
		
		//personalM.setCustomerType("01");		
		personalM.setCidType(null);
		personalM.setThaiTitleName(null);
		personalM.setThaiFirstName(null);
		personalM.setThaiLastName(null);
		personalM.setThaiMidName(null);
		personalM.setThaiOldLastName(null);
		personalM.setEngTitleName(null);
		personalM.setEngFirstName(null);
		personalM.setEngLastName(null);
		personalM.setEngMidName(null);
		personalM.setGender(null);
		personalM.setDegree(null);
		personalM.setNationality("01");
		personalM.setRace(null);
		personalM.setMaritalStatus(null);
		personalM.setEmail1(null);
		personalM.setEmail2(null);
		personalM.setLoanObjective(null);

		personalM.setLoanObjectiveDesc(null);	
		personalM.setIdNo(null);
		personalM.setAge(0);

		personalM.setNoOfchild(0);			
		personalM.setCompanyTitle(null);
		personalM.setCompanyName(null);
		personalM.setCidExpDate(null);					
		personalM.setBirthDate(null);
		personalM.setCisNo(null);
		personalM.setCardLinkCustNo(null);
		
		personalM.setMailingAddress(null);
		personalM.setAddressDocLine1(null);
		personalM.setAddressDocLine2(null);
		
		
		if(OrigUtil.isEmptyString(cardtype)){
			cardtype = "01";
		}
		
		//jsonObj.CreateJsonObject("customertype", "");
		jsonObj.CreateJsonObject("cis", "-");
		jsonObj.CreateJsonObject("cardtype", cardtype);
		jsonObj.CreateJsonObject("identification_no", "");
		jsonObj.CreateJsonObject("card_expire_date", "");
		jsonObj.CreateJsonObject("titleThai", "");
		jsonObj.CreateJsonObject("name_th", "");
		jsonObj.CreateJsonObject("surname_th", "");
		jsonObj.CreateJsonObject("middlename_th", "");
		jsonObj.CreateJsonObject("old_surname_th", "");
		jsonObj.CreateJsonObject("titleEng", "");
		jsonObj.CreateJsonObject("name_eng", "");
		jsonObj.CreateJsonObject("surname_eng", "");
		jsonObj.CreateJsonObject("middlename_eng", "");
		jsonObj.CreateJsonObject("birth_date", "");
		jsonObj.CreateJsonObject("age_desc", "");
		jsonObj.CreateJsonObject("gender", "");
		jsonObj.CreateJsonObject("education", "");
		jsonObj.CreateJsonObject("nationality", "TH");
		jsonObj.CreateJsonObject("race", "01");
		jsonObj.CreateJsonObject("element_age", "");
		jsonObj.CreateJsonObject("marriage_status", "");
		jsonObj.CreateJsonObject("no_of_children", "");
		jsonObj.CreateJsonObject("email1", "");
		jsonObj.CreateJsonObject("sub_email", "");
		jsonObj.CreateJsonObject("loan_objective", "");
		jsonObj.CreateJsonObject("other_loan_objective", "");
		jsonObj.CreateJsonObject("occ_workplace_Title", "");
		jsonObj.CreateJsonObject("occ_name_th", "");
		jsonObj.CreateJsonObject("occ_workplaceTitle2", "");
		jsonObj.CreateJsonObject("result_1038", "-");
		jsonObj.CreateJsonObject("result_1039", "-");
		jsonObj.CreateJsonObject("title_thai", "");
		jsonObj.CreateJsonObject("title_eng", "");
		jsonObj.CreateJsonObject("main_workplace_Title", "");
		
		jsonObj.CreateJsonObject("payment_method_bankAccountNo", "");
		jsonObj.CreateJsonObject("payment_method_bankAccountName", "");
		jsonObj.CreateJsonObject("cash_day1_account_no", "");
		jsonObj.CreateJsonObject("cash_day1_account_name", "");
		
		AddressUtil addressUtil = new AddressUtil();
		
		jsonObj.CreateJsonObject("addressResult",addressUtil.CreateNorecPLAddressM());
		jsonObj.CreateJsonObject("card_link_address1", "");
		jsonObj.CreateJsonObject("card_link_address2","");
		jsonObj.CreateJsonObject("mailling-address",HTMLRenderUtil.displaySelectTagScriptAction_ORIG(
										new Vector(), "","mailling_address", HTMLRenderUtil.DISPLAY_MODE_EDIT,"onChange='GetCardlink()'"));		
		
	}
	public void ClearPLXRulesVerificationResultDataM(JsonObjectUtil jsonObj ,PLApplicationDataM appM,UserDetailM userM){
		
		if(null == jsonObj) jsonObj = new JsonObjectUtil();
		
		if(null == appM) appM = new PLApplicationDataM();
			PLPersonalInfoDataM personalM = appM.getPLPersonalInfoDataM(PLPersonalInfoDataM.PersonalType.PERSONAL_TYPE_APPLICANT);
				
			PLXRulesVerificationResultDataM xrulesVerM = new PLXRulesVerificationResultDataM();
				xrulesVerM.setPersonalID(personalM.getPersonalID());
			
			personalM.setXrulesVerification(xrulesVerM);
	}
	public void ClearPLAddressDataM(JsonObjectUtil jsonObj ,PLApplicationDataM appM,HttpServletRequest request){
		if(null == appM) appM = new PLApplicationDataM();
		PLPersonalInfoDataM personalM = appM.getPLPersonalInfoDataM(PLPersonalInfoDataM.PersonalType.PERSONAL_TYPE_APPLICANT);
		personalM.setAddressVect(null);
	}
	public void ClearPLSpecialCustomerDataM(JsonObjectUtil jsonObj ,PLApplicationDataM appM){
		if(null == appM) appM = new PLApplicationDataM();
		String busClass = appM.getBusinessClassId();
		
		if(!OrigConstant.BusClass.FCP_KEC_XS.equalsIgnoreCase(busClass)){return;}
		PLPersonalInfoDataM plpersonalinfodatam = appM.getPLPersonalInfoDataM(PLPersonalInfoDataM.PersonalType.PERSONAL_TYPE_APPLICANT);
		PLXRulesVerificationResultDataM plXruleResultDataM =  plpersonalinfodatam.getXrulesVerification();
		if(OrigUtil.isEmptyObject(plXruleResultDataM)){
			plXruleResultDataM = new PLXRulesVerificationResultDataM();
			plpersonalinfodatam.setXrulesVerification(plXruleResultDataM);
		}
		
		PLXRulesSpecialCustomerDataM plXruleSpecialDataM =  plXruleResultDataM.getXrulesSpecialCusM();
		if(OrigUtil.isEmptyObject(plXruleSpecialDataM)){
			plXruleSpecialDataM = new PLXRulesSpecialCustomerDataM();
			plXruleResultDataM.setXrulesSpecialCusM(plXruleSpecialDataM);
		}
		
		jsonObj.CreateJsonObject("customer_group_name","");
		jsonObj.CreateJsonObject("csa_risk_name","");
		jsonObj.CreateJsonObject("aum_avg_month","0.00");
		jsonObj.CreateJsonObject("payroll_type","");
		jsonObj.CreateJsonObject("final_proxy_income","0.00");
		jsonObj.CreateJsonObject("credit_card_line","0.00");
		jsonObj.CreateJsonObject("contact_type","");
		jsonObj.CreateJsonObject("premium_on_top","");
	}
}
