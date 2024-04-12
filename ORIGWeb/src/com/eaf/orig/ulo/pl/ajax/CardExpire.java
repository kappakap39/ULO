package com.eaf.orig.ulo.pl.ajax;

import java.util.Calendar;
import java.util.Date;

import javax.servlet.http.HttpServletRequest;

import org.apache.log4j.Logger;

import com.eaf.orig.shared.servlet.AjaxDisplayGenerateInf;
import com.eaf.orig.ulo.pl.app.dao.exception.PLOrigApplicationException;
import com.eaf.orig.ulo.pl.app.utility.DataFormatUtility;
import com.eaf.orig.ulo.pl.formcontrol.view.form.PLOrigFormHandler;
import com.eaf.orig.ulo.pl.model.app.PLApplicationDataM;

public class CardExpire implements AjaxDisplayGenerateInf{
	static Logger logger = Logger.getLogger(CardExpire.class);
	@Override
	public String getDisplayObject(HttpServletRequest request)throws PLOrigApplicationException {
		try{
			String card_expire_date = request.getParameter("card_expire_date");		
			logger.debug("card_expire_date >> "+card_expire_date);
			
			PLOrigFormHandler origForm = (PLOrigFormHandler) request.getSession().getAttribute("PLORIGForm");
			PLApplicationDataM applicationM = origForm.getAppForm();
			
			Date date1 = setTimeToMidnight(DataFormatUtility.StringThToDateEn(card_expire_date,DataFormatUtility.DateFormatType.FORMAT_DDMMYYY_S));
			Date date2 = setTimeToMidnight(new Date(applicationM.getAppDate().getTime()));
			
			if(date1 != null && date2 != null){
				if(date1.compareTo(date2) <= 0){
					logger.error("APPLICANT_CARD_EXPIRED !! ");
					return "ERROR";
				}
			}
		
		}catch(Exception e){
			logger.error("CardExpire().ERROR: "+e.getMessage());
		}
		return "";
	}
	public static Date setTimeToMidnight(Date date) {
		if(null == date) return null;		
	    Calendar calendar = Calendar.getInstance();
	    calendar.setTime( date );
	    calendar.set(Calendar.HOUR_OF_DAY, 0);
	    calendar.set(Calendar.MINUTE, 0);
	    calendar.set(Calendar.SECOND, 0);
	    calendar.set(Calendar.MILLISECOND, 0);
	    return calendar.getTime();
	}
}
