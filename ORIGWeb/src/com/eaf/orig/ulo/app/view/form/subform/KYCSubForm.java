package com.eaf.orig.ulo.app.view.form.subform;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;

import javax.servlet.http.HttpServletRequest;

import org.apache.log4j.Logger;

import com.eaf.core.ulo.common.display.FormatUtil;
import com.eaf.core.ulo.common.properties.SystemConstant;
import com.eaf.core.ulo.common.util.DateValidateUtil;
import com.eaf.core.ulo.common.util.FormErrorUtil;
import com.eaf.core.ulo.common.util.Util;
import com.eaf.orig.shared.view.form.ORIGSubForm;
import com.eaf.orig.ulo.app.view.util.kpl.KPLUtil;
import com.eaf.orig.ulo.constant.MConstant;
import com.eaf.orig.ulo.model.app.ApplicationGroupDataM;
import com.eaf.orig.ulo.model.app.KYCDataM;
import com.eaf.orig.ulo.model.app.PersonalInfoDataM;
public class KYCSubForm extends ORIGSubForm{
	private static transient Logger logger = Logger.getLogger(KYCSubForm.class);
	private String PERSONAL_TYPE = SystemConstant.getConstant("PERSONAL_TYPE_APPLICANT");	

	@Override
	public void setProperties(HttpServletRequest request, Object appForm) {
		ApplicationGroupDataM applicationGroup = ((ApplicationGroupDataM)appForm);
		PersonalInfoDataM personalInfo = applicationGroup.getPersonalInfo(PERSONAL_TYPE);
				
		KYCDataM kyc = personalInfo.getKyc();
		if(Util.empty(kyc)){
			kyc = new KYCDataM();
			personalInfo.setKyc(kyc);
		}
		String RELATION_FLAG = request.getParameter("RELATION_FLAG");
		String REL_TITLE_NAME = request.getParameter("REL_TITLE_NAME");
		String REL_TITLE_DESC = request.getParameter("REL_TITLE_DESC");
		String REL_NAME = request.getParameter("REL_NAME");
		String REL_SURNAME = request.getParameter("REL_SURNAME");
		String REL_POSITION = request.getParameter("REL_POSITION");
		String REL_DETAIL = request.getParameter("REL_DETAIL");
		String WORK_START_DATE = request.getParameter("WORK_START_DATE");
		String WORK_END_DATE = request.getParameter("WORK_END_DATE");
		
		kyc.setRelationFlag(RELATION_FLAG);
		kyc.setRelTitleName(REL_TITLE_NAME);
		kyc.setRelTitleDesc(REL_TITLE_DESC);
		kyc.setRelName(REL_NAME);
		kyc.setRelSurname(REL_SURNAME);
		kyc.setRelPosition(REL_POSITION);
		kyc.setRelDetail(REL_DETAIL);
		kyc.setWorkStartDate(FormatUtil.toDate(WORK_START_DATE,FormatUtil.EN));
		kyc.setWorkEndDate(FormatUtil.toDate(WORK_END_DATE,FormatUtil.EN));
	}

	@Override
	public HashMap validateForm(HttpServletRequest request, Object appForm) {
		ApplicationGroupDataM applicationGroup = ((ApplicationGroupDataM)appForm);
		PersonalInfoDataM personalInfo = applicationGroup.getPersonalInfo(PERSONAL_TYPE);
		KYCDataM kycd = personalInfo.getKyc();
		if(Util.empty(kycd)){
			kycd = new KYCDataM();
		}
		FormErrorUtil formError = new FormErrorUtil();
		String subformId = "KYC_SUBFORM";
		logger.debug("subformId >> "+subformId);
		logger.debug("FLAG >> "+FormatUtil.display( kycd.getRelationFlag()));

		if(!KPLUtil.isKPL_TOPUP(applicationGroup))
		{
			formError.mandatory(subformId,"RELATION_FLAG",kycd.getRelationFlag(),request);
			formError.mandatory(subformId,"REFERENCE_NAME",kycd,request);
			formError.mandatory(subformId,"REL_SURNAME",kycd.getRelSurname(),request);
			formError.mandatory(subformId,"REL_POSITION",kycd.getRelPosition(),request);
			formError.mandatory(subformId,"REL_DETAIL",kycd.getRelDetail(),request);
			formError.mandatoryDate(subformId,"WORK_START_DATE",kycd.getWorkStartDate(),request,DateValidateUtil.EN);
			formError.mandatoryDate(subformId,"WORK_END_DATE",kycd.getWorkEndDate(),request,DateValidateUtil.EN);
		}
		
		return formError.getFormError();
	}

	@Override
	public boolean validateSubForm(HttpServletRequest request) {
		return false;
	}
	
	@Override
	public String renderSubformFlag(HttpServletRequest request,Object objectForm) {
		String[] KYC_EXCEPTION_APPLICATION_TEMPLATE = SystemConstant.getArrayConstant("KYC_EXCEPTION_APPLICATION_TEMPLATE");	
		ArrayList<String> templateConditions = new ArrayList<String>(Arrays.asList(KYC_EXCEPTION_APPLICATION_TEMPLATE));
		ApplicationGroupDataM applicationGroup = ((ApplicationGroupDataM)objectForm);
		String templateId = applicationGroup.getApplicationTemplate();
		logger.debug(">>>templateId>>>"+templateId);
		if(templateConditions.contains(templateId)){
			return MConstant.FLAG_N;
		}
		return  MConstant.FLAG_Y;
	}
}
