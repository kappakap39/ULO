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

public class EnNameCIS extends ElementHelper implements ElementInf{
	private static transient Logger logger = Logger.getLogger(EnNameCIS.class);
	String FIELD_ID_TH_TITLE_CODE = SystemConstant.getConstant("FIELD_ID_TH_TITLE_CODE");
	String CIS_EN_FIRST_NAME=SystemConstant.getConstant("CIS_EN_FIRST_NAME");
	String CIS_EN_LAST_NAME=SystemConstant.getConstant("CIS_EN_LAST_NAME");
	String CIS_EN_MID_NAME=SystemConstant.getConstant("CIS_EN_MID_NAME");
	
	@Override
	public String getElement(HttpServletRequest request, Object objectElement) {		
		PersonalInfoDataM personalInfo = (PersonalInfoDataM)objectElement;
		String personalID = personalInfo.getPersonalId();
		logger.debug("personalID>>"+personalID);
		return "/orig/ulo/subform/question/EnNameCIS.jsp?PERSONAL_ID="+personalID;
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
				String EN_FIRST_NAME = request.getParameter("EN_FIRST_NAME_"+personalInfo.getPersonalId());
				String EN_MID_NAME = request.getParameter("EN_MID_NAME_"+personalInfo.getPersonalId());
				String EN_LAST_NAME = request.getParameter("EN_LAST_NAME_"+personalInfo.getPersonalId());				
				logger.debug("EN_FIRST_NAME>>"+EN_FIRST_NAME);
				logger.debug("EN_MID_NAME>>"+EN_MID_NAME);
				logger.debug("EN_LAST_NAME>>"+EN_LAST_NAME);
				personalInfo.setEnFirstName(EN_FIRST_NAME);
				personalInfo.setEnMidName(EN_MID_NAME);
				personalInfo.setEnLastName(EN_LAST_NAME);
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
						   (CIS_EN_FIRST_NAME.equals(fieldNameType) || CIS_EN_LAST_NAME.equals(fieldNameType) || CIS_EN_MID_NAME.equals(fieldNameType))){
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
