package com.eaf.orig.ulo.control.util;

import java.sql.Date;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;

import javax.servlet.http.HttpServletRequest;

import com.eaf.core.ulo.common.date.ApplicationDate;
import com.eaf.core.ulo.common.display.ListBoxFilterInf;
import com.eaf.orig.ulo.formcontrol.view.form.ORIGFormHandler;

public class YearListBoxFilter implements ListBoxFilterInf {
	private int thaiYearAdj = 543;
	@Override
	public ArrayList<HashMap<String, Object>> filter(String configId,
			String cacheId, String typeId, HttpServletRequest request) 
	{		
		ORIGFormHandler ORIGForm = (ORIGFormHandler)request.getSession().getAttribute(ORIGFormHandler.ORIGForm);
		Date applicationDate = ORIGForm.getObjectForm().getApplicationDate();
		if(applicationDate == null) {
			applicationDate = ApplicationDate.getDate();
		}
		
		Calendar calen = Calendar.getInstance();
		calen.setTime(applicationDate);
		calen.add(Calendar.YEAR, 0);
		
		int yearVal = calen.get(Calendar.YEAR);
		int thaiYear = yearVal+thaiYearAdj;
		String year = String.valueOf(yearVal);
		
		ArrayList<HashMap<String,Object>> vYear = new ArrayList<>();
		HashMap<String,Object> year1 = new HashMap<String,Object>();
		year1.put("CODE", year);
		year1.put("VALUE", String.valueOf(thaiYear));
		vYear.add(year1);
		
		calen.add(Calendar.YEAR, -1);
		yearVal = calen.get(Calendar.YEAR);
		thaiYear = yearVal+thaiYearAdj;
		year = String.valueOf(yearVal);		
		
		HashMap<String,Object> year2 = new HashMap<String,Object>();
		year2.put("CODE", year);
		year2.put("VALUE", String.valueOf(thaiYear));
		vYear.add(year2);
		
		return vYear;
	}
	@Override
	public void setFilterProperties(String configId, String cacheId, String typeId,HttpServletRequest request) {
		
	}
	@Override
	public void init(Object objectForm) {
		// TODO Auto-generated method stub
		
	}
	@Override
	public void setElementDisplayMode(String displayMode) {
		
	}
}
