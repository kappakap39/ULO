package com.eaf.orig.ulo.app.view.form.question;

import java.util.HashMap;

import javax.servlet.http.HttpServletRequest;

import org.apache.log4j.Logger;

import com.eaf.core.ulo.common.display.ElementHelper;
import com.eaf.core.ulo.common.display.ElementInf;
import com.eaf.core.ulo.common.properties.SystemConstant;
import com.eaf.core.ulo.common.util.Util;
import com.eaf.orig.ulo.constant.MConstant;
import com.eaf.orig.ulo.formcontrol.view.form.EntityFormHandler;
import com.eaf.orig.ulo.model.app.ApplicationGroupDataM;
import com.eaf.orig.ulo.model.app.ComparisonGroupDataM;
import com.eaf.orig.ulo.model.app.PersonalInfoDataM;
import com.eaf.orig.ulo.model.compare.CompareDataM;

public class ThNameCIS extends ElementHelper implements ElementInf {
	private static transient Logger logger = Logger.getLogger(ThNameCIS.class);
	String FIELD_ID_TH_TITLE_CODE = SystemConstant.getConstant("FIELD_ID_TH_TITLE_CODE");
	String CIS_TH_FIRST_NAME=SystemConstant.getConstant("CIS_TH_FIRST_NAME");
	String CIS_TH_LAST_NAME=SystemConstant.getConstant("CIS_TH_LAST_NAME");
	String CIS_TH_MID_NAME=SystemConstant.getConstant("CIS_TH_MID_NAME");
	@Override
	public String getElement(HttpServletRequest request, Object objectElement) {
		PersonalInfoDataM personalInfo = (PersonalInfoDataM)objectElement;
		String personalID = personalInfo.getPersonalId();
		logger.debug("personalID>>"+personalID);
		return "/orig/ulo/subform/question/ThNameCIS.jsp?PERSONAL_ID="+personalID;
	}
	
	
	@Override
	public String getDisplayType() {
		return ElementInf.DISPLAY_JSP;
	}
	
	@Override
	public String processElement(HttpServletRequest request,Object objectElement) {
		PersonalInfoDataM personalInfo = (PersonalInfoDataM)objectElement;
		try {
			if(!Util.empty(personalInfo)){
				String TH_FIRST_NAME = request.getParameter("TH_FIRST_NAME_"+personalInfo.getPersonalId());
				String TH_MID_NAME = request.getParameter("TH_MID_NAME_"+personalInfo.getPersonalId());
				String TH_LAST_NAME = request.getParameter("TH_LAST_NAME_"+personalInfo.getPersonalId());				
				logger.debug("TH_FIRST_NAME>>"+TH_FIRST_NAME);
				logger.debug("TH_MID_NAME>>"+TH_MID_NAME);
				logger.debug("TH_LAST_NAME>>"+TH_LAST_NAME);
				personalInfo.setThFirstName(TH_FIRST_NAME);
				personalInfo.setThMidName(TH_MID_NAME);
				personalInfo.setThLastName(TH_LAST_NAME);
			}	
		} catch (Exception e) {
			logger.fatal("ERROR",e);
		}
		return null;
	}
	
	@Override
	public String renderElementFlag(HttpServletRequest request,Object objectElement) {
		EntityFormHandler	EntityForm	=	(EntityFormHandler)request.getSession().getAttribute("EntityForm");
		ApplicationGroupDataM applicationGroup = (ApplicationGroupDataM)EntityForm.getObjectForm();
		try {
			ComparisonGroupDataM compareGroupData = applicationGroup.getComparisonGroups(CompareDataM.SoruceOfData.CIS);
			if(!Util.empty(compareGroupData)) {
				if(!Util.empty(compareGroupData.getComparisonFields())) {
					HashMap<String, CompareDataM> compareDataList = compareGroupData.getComparisonFields();
					logger.debug("compareDataList>"+compareDataList);
					for(String  keyName : compareDataList.keySet()){
						CompareDataM compareDataM = compareDataList.get(keyName);
						String fieldNameType =compareDataM.getFieldNameType();
						logger.debug("compareDataM.getRefId()="+compareDataM.getRefId());
						logger.debug("compareDataM.getFieldName()="+compareDataM.getFieldName());
						logger.debug("compareDataM.getFieldNameType()="+fieldNameType);
						logger.debug("compareDataM.getCompareFlag()="+compareDataM.getCompareFlag());
						if(MConstant.FLAG.WRONG.equals(compareDataM.getCompareFlag()) && 
						   (CIS_TH_FIRST_NAME.equals(fieldNameType) || CIS_TH_LAST_NAME.equals(fieldNameType) || CIS_TH_MID_NAME.equals(fieldNameType))){
							return MConstant.FLAG.YES;
						}
					}
				}
			}
		} catch (Exception e) {
			logger.fatal("ERROR",e);
		}
		return  MConstant.FLAG.NO;
	}
}
