package com.eaf.orig.ulo.pl.ajax;

import java.util.Calendar;
import java.util.Date;

import javax.servlet.http.HttpServletRequest;

import org.apache.log4j.Logger;

import com.eaf.orig.shared.servlet.AjaxDisplayGenerateInf;
import com.eaf.orig.shared.util.OrigUtil;
import com.eaf.orig.ulo.pl.app.dao.exception.PLOrigApplicationException;
import com.eaf.orig.ulo.pl.app.utility.DataFormatUtility;
import com.eaf.orig.ulo.pl.app.utility.DataFormatUtility.DateFormatType;
import com.eaf.orig.ulo.pl.app.utility.JsonObjectUtil;
import com.eaf.orig.ulo.pl.formcontrol.view.form.PLOrigFormHandler;
import com.eaf.orig.ulo.pl.model.app.PLApplicationDataM;
import com.eaf.orig.ulo.pl.model.app.PLPersonalInfoDataM;

public class GetAge  implements AjaxDisplayGenerateInf {
	
	Logger logger = Logger.getLogger(GetAge.class);
	
	@Override
	public String getDisplayObject(HttpServletRequest request)throws PLOrigApplicationException {
		PLOrigFormHandler plOrigForm = (PLOrigFormHandler) request.getSession().getAttribute("PLORIGForm");
		PLApplicationDataM appM = plOrigForm.getAppForm();
		if(null == appM ){
			appM = new PLApplicationDataM();
			plOrigForm.setAppForm(appM);
		}
		PLPersonalInfoDataM personlM = appM.getPLPersonalInfoDataM(PLPersonalInfoDataM.PersonalType.PERSONAL_TYPE_APPLICANT);
		try{	
			String birthDate = request.getParameter("birth_date");
			
			if(OrigUtil.isEmptyString(birthDate)){
				JsonObjectUtil jObj = new JsonObjectUtil();				
					jObj.CreateJsonObject("element_age","");				
					jObj.CreateJsonObject("age_desc","");				
				return jObj.returnJson();
			}
			
//			logger.debug("birthDate >> "+birthDate);
			
			Date date = DataFormatUtility.StringEnToDateEn(birthDate, DateFormatType.FORMAT_DDMMYYY_S);
			
			Calendar cAppDate = Calendar.getInstance();
			Date appDate = appM.getAppDate();			
			if(null == appDate)	appDate = appM.getCreateDate();			
				cAppDate.setTime(appDate);
			int currentYear = cAppDate.get(Calendar.YEAR);
			
			Calendar cBirthDate = Calendar.getInstance();				
				cBirthDate.setTime(date);			
			int birthYear = cBirthDate.get(Calendar.YEAR);
						
//			logger.debug("currentYear >> "+currentYear);
//			logger.debug("birthYear >> "+birthYear);
						
			int age = currentYear-birthYear;

//			logger.debug("Age >> "+age);
			
			personlM.setAge(age);
			
			String calAge = (age<=0)?"":String.valueOf(age);
			
			JsonObjectUtil jObj = new JsonObjectUtil();
			
			jObj.CreateJsonObject("element_age",calAge);			
			jObj.CreateJsonObject("age_desc",calAge);			
			return jObj.returnJson();
		
		} catch (Exception e) {
			logger.fatal("Error ",e);
		}		
		return "";		
	}

}
