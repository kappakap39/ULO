package com.eaf.orig.ulo.app.view.util.ajax;

import java.sql.Date;
import java.util.Calendar;

import javax.servlet.http.HttpServletRequest;

import com.eaf.core.ulo.common.display.FormatUtil;
import com.eaf.core.ulo.common.file.ExcelTemplate.logger;
import com.eaf.core.ulo.common.model.ResponseData;
import com.eaf.core.ulo.common.util.Util;
import com.eaf.j2ee.pattern.control.AjaxInf;
import com.eaf.j2ee.pattern.control.ResponseDataController;
import com.eaf.j2ee.pattern.util.JSONUtil;
import com.eaf.orig.shared.model.Age;

public class GetCalculateAge implements AjaxInf {
	@Override
	public ResponseData processAction(HttpServletRequest request){
		ResponseDataController responseData = new ResponseDataController(request,ResponseData.FunctionId.GET_CALCULATE_AGE);		
		try{
		JSONUtil json = new JSONUtil();
		logger.debug("request birthDate >>>"+request.getParameter("BIDTH_DATE"));
		logger.debug("request consentDate >>>"+request.getParameter("CONSENT_DATE"));
		String tmpBIRTH_DATE =  request.getParameter("BIDTH_DATE");
		String tmpCONSENT_DATE =  request.getParameter("CONSENT_DATE");
		Date thBirthDate = !Util.empty(tmpBIRTH_DATE) ? FormatUtil.toDate(tmpBIRTH_DATE,FormatUtil.EN) : null ;
		Date thConsentDate = !Util.empty(tmpCONSENT_DATE) ? FormatUtil.toDate(tmpCONSENT_DATE,FormatUtil.EN) : null ;
	
//		java.sql.Date sqlDate = new java.sql.Date(Calendar.getInstance().getTimeInMillis());
		
		Age personDate = Util.age(thBirthDate, thConsentDate);
		json.put("year",String.valueOf(personDate.getYears()));
		json.put("mounth",String.valueOf(personDate.getMonths()));
		json.put("Day",String.valueOf(personDate.getDays()));
		logger.debug("ageYear :"+personDate.getYears());
		return responseData.success(json.getJSON());
		}catch(Exception e){
			logger.debug("ERROR >>>"+e);
			//logger.fatal("ERROR ",e);
			return responseData.error(e);
		}
	}

}
