package com.eaf.orig.ulo.pl.app.view.form.subform;

import java.util.HashMap;

import javax.servlet.http.HttpServletRequest;

import com.eaf.orig.profile.model.UserDetailM;
import com.eaf.orig.shared.constant.OrigConstant;
import com.eaf.orig.shared.util.OrigUtil;
import com.eaf.orig.shared.view.form.ORIGSubForm;
import com.eaf.orig.ulo.pl.app.utility.DataFormatUtility;
import com.eaf.orig.ulo.pl.app.utility.PLMessageResourceUtil;
import com.eaf.orig.ulo.pl.formcontrol.view.form.PLOrigFormHandler;
import com.eaf.orig.ulo.pl.model.app.PLApplicationDataM;
import com.eaf.orig.ulo.pl.model.app.PLKYCDataM;
import org.apache.log4j.Logger;

public class ServicePoliticsSubForm extends ORIGSubForm {
	
	static Logger logger = Logger.getLogger(ServicePoliticsSubForm.class);
	
	@Override
	public void setProperties(HttpServletRequest request, Object appForm) {
		PLOrigFormHandler origForm = (PLOrigFormHandler)request.getSession().getAttribute("PLORIGForm");
		
		PLApplicationDataM applicationM = origForm.getAppForm();		
		if(null == applicationM){
			applicationM = new PLApplicationDataM();
		}
		
		PLKYCDataM kYCM =  applicationM.getKycM();	
		if(null == kYCM){
			kYCM = new PLKYCDataM();
			applicationM.setKycM(kYCM);
		}
		
		try{
			
			String ser_title_thai = request.getParameter("ser_title_thai");
			String ser_name_th = request.getParameter("ser_name_th");
			String ser_surname_thai = request.getParameter("ser_surname_thai");
			String ser_position = request.getParameter("ser_position");
			String ser_relation = request.getParameter("ser_relation");
			String ser_position_duration = request.getParameter("ser_position_duration");
			String ser_to_years = request.getParameter("ser_to_years");
			String ser_sanction_list = request.getParameter("ser_sanction_list");
			String ser_customer_risk = request.getParameter("ser_customer_risk");
			String have_related = request.getParameter("have_related");
			
			kYCM.setRelationFlag(have_related);
			kYCM.setRelTitleName(ser_title_thai);
			kYCM.setRelName(ser_name_th);
			kYCM.setRelSurName(ser_surname_thai);
			kYCM.setRelPosition(ser_position);
			kYCM.setRelDetail(ser_relation);
			kYCM.setSanctionList(ser_sanction_list);
			kYCM.setCustmoerRiskGrade(ser_customer_risk);
			
			if(!OrigUtil.isEmptyString(ser_position_duration)){
				kYCM.setWorkStartDate(DataFormatUtility.stringToDateTH(ser_position_duration, "dd/mm/yyyy"));
			}
			
			if(!OrigUtil.isEmptyString(ser_to_years)){
				kYCM.setWorkEndDate(DataFormatUtility.stringToDateTH(ser_to_years, "dd/mm/yyyy"));				
			}
			
		}catch(Exception e){
			logger.fatal("Exception ",e);
		}
	}

	@Override
	public HashMap validateForm(HttpServletRequest request, Object appForm) {
		// TODO Auto-generated method stub
		return null;
	}
	private boolean result = false;
	@Override
	public boolean validateSubForm(HttpServletRequest request) {
		PLOrigFormHandler formHandler = (PLOrigFormHandler) request.getSession().getAttribute(OrigConstant.PL_FORMHANDLER_NAME);
		UserDetailM userM =	(UserDetailM) request.getSession().getAttribute("ORIGUser");
		
		if(OrigConstant.SUBMIT_TYPE_SEND.equals(formHandler.getSaveType()) &&
				(OrigConstant.Mandatory.MANDATORY_TYPE_SUMMIT == formHandler.getMandatoryType())){
			
			//Praisan 20121218 validate only type submit (1)
			
			String have_related = request.getParameter("have_related");
			String sertitleThai = request.getParameter("sertitleThai");
			String ser_name_th = request.getParameter("ser_name_th");
			String ser_surname_thai = request.getParameter("ser_surname_thai");
			String ser_position = request.getParameter("ser_position");
			String ser_relation = request.getParameter("ser_relation");
			String ser_position_duration = request.getParameter("ser_position_duration");
			String ser_to_years = request.getParameter("ser_to_years");
			
			String errorMsg="";
			
			String currentRole = userM.getCurrentRole();
			
			if("Y".equalsIgnoreCase(have_related) && !OrigConstant.ROLE_DF_REJECT.equals(currentRole)){
				
				if(OrigUtil.isEmptyString(sertitleThai)){
					errorMsg = PLMessageResourceUtil.getTextDescription(request,"WARNING_KYC_TITLE");
					formHandler.PushErrorMessage("sertitleThai", errorMsg);
					result = true;
				}
				if(OrigUtil.isEmptyString(ser_name_th)){
					errorMsg = PLMessageResourceUtil.getTextDescription(request,"WARNING_KYC_NAME_TH");
					formHandler.PushErrorMessage("ser_name_th", errorMsg);
					result = true;
				}
				if(OrigUtil.isEmptyString(ser_surname_thai)){
					errorMsg = PLMessageResourceUtil.getTextDescription(request,"WARNING_KYC_SURNAME_TH");
					formHandler.PushErrorMessage("ser_surname_thai", errorMsg);
					result = true;
				}
				if(OrigUtil.isEmptyString(ser_position)){
					errorMsg = PLMessageResourceUtil.getTextDescription(request,"WARNING_KYC_POSITION");
					formHandler.PushErrorMessage("ser_position", errorMsg);
					result = true;
				}
				if(OrigUtil.isEmptyString(ser_relation)){
					errorMsg = PLMessageResourceUtil.getTextDescription(request,"WARNING_KYC_RELATION");
					formHandler.PushErrorMessage("ser_relation", errorMsg);
					result = true;
				}
				if(OrigUtil.isEmptyString(ser_position_duration)){
					errorMsg = PLMessageResourceUtil.getTextDescription(request,"WARNING_KYC_POSITION_DURATION");
					formHandler.PushErrorMessage("ser_position_duration", errorMsg);
					result = true;
				}
				if(OrigUtil.isEmptyString(ser_to_years)){
					errorMsg = PLMessageResourceUtil.getTextDescription(request,"WARNING_KYC_TO_YEAR");
					formHandler.PushErrorMessage("ser_to_years", errorMsg);
					result = true;
				}				
			}
		}
		return result;
	}

}
