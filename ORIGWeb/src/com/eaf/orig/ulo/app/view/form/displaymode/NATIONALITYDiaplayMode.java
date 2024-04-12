package com.eaf.orig.ulo.app.view.form.displaymode;

import javax.servlet.http.HttpServletRequest;

import com.eaf.core.ulo.common.display.FormDisplayModeHelper;
import com.eaf.core.ulo.common.display.HtmlUtil;
import com.eaf.core.ulo.common.properties.FormControl;
import com.eaf.core.ulo.common.properties.SystemConstant;
import com.eaf.core.ulo.common.util.Util;
import com.eaf.orig.ulo.formcontrol.view.form.ORIGFormHandler;
import com.eaf.orig.ulo.model.app.ApplicationGroupDataM;
import com.eaf.orig.ulo.model.app.PersonalApplicationInfoDataM;
import com.eaf.orig.ulo.model.app.PersonalInfoDataM;

public class NATIONALITYDiaplayMode extends FormDisplayModeHelper{

	String CIDTYPE_NON_THAI_NATINALITY = SystemConstant.getConstant("CIDTYPE_NON_THAI_NATINALITY");
	String PERSONAL_TYPE_APPLICANT = SystemConstant.getConstant("PERSONAL_TYPE_APPLICANT");
	@Override
	public String displayMode(Object objectElement, HttpServletRequest request) {
		
		PersonalInfoDataM personalInfo = null;
		Object masterObjectForm = FormControl.getMasterObjectForm(request);	
		if(masterObjectForm instanceof ApplicationGroupDataM){			
			ApplicationGroupDataM applicationGroup = (ApplicationGroupDataM)masterObjectForm;
			personalInfo = applicationGroup.getPersonalInfo(PERSONAL_TYPE_APPLICANT);
		}else if (masterObjectForm instanceof PersonalApplicationInfoDataM){
			PersonalApplicationInfoDataM personalApplicationInfo = (PersonalApplicationInfoDataM)masterObjectForm;
			personalInfo = personalApplicationInfo.getPersonalInfo();
		}	
		if(null == personalInfo){
			personalInfo = new PersonalInfoDataM();
		}
		String CID_TYPE = personalInfo.getCidType();
		String idNo = personalInfo.getIdno();
		String displayMode = HtmlUtil.EDIT;
		
		if(CIDTYPE_NON_THAI_NATINALITY.equals(CID_TYPE)){
			String ID_NO = request.getParameter("ID_NO");
			
			if(!Util.empty(ID_NO)){
				idNo=ID_NO;
			}
			
			if(!Util.empty(idNo)&&idNo.length()>=13){
				if("00".equals(idNo.substring(0, 2))){
					
				}else if("0".equals(idNo.substring(0, 1))){
					
					displayMode = HtmlUtil.VIEW;
				}else if("6".equals(idNo.substring(0, 1))&&inLength(Integer.valueOf(idNo.substring(5, 7)),0,49)){
					
				}else if("6".equals(idNo.substring(0, 1))&&inLength(Integer.valueOf(idNo.substring(5, 7)),50,72)){
					displayMode = HtmlUtil.VIEW;
				}else{
					displayMode = HtmlUtil.VIEW;
				}
			}
			
		}
		
		
		return displayMode;
	}
	
	public boolean inLength(Integer value,Integer start ,Integer end){
		if(value>=start && value <= end){
			return true;
		}else{
			return false;
		}
	}
}
