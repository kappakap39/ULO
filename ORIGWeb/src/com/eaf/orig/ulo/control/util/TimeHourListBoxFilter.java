package com.eaf.orig.ulo.control.util;

import java.util.ArrayList;
import java.util.HashMap;

import javax.servlet.http.HttpServletRequest;

import org.apache.log4j.Logger;

import com.eaf.core.ulo.common.display.ListBoxFilterInf;

public class TimeHourListBoxFilter implements ListBoxFilterInf{
	private static transient Logger logger = Logger.getLogger(TimeHourListBoxFilter.class);
	
	@Override
	public ArrayList<HashMap<String, Object>> filter(String configId, String cacheId, String typeId, HttpServletRequest request) {
		ArrayList<HashMap<String, Object>> dropdowns = new ArrayList<>();
		for(int i=0;i<24;i++){
			HashMap<String,Object> data = new HashMap<>();
			String HOUR = String.valueOf(i);
			if(HOUR.length()<2){
				HOUR = "0"+HOUR;
			}
			data.put("CODE",HOUR);
			data.put("VALUE",HOUR);
			dropdowns.add(data);
		}
		logger.debug("dropdowns : "+dropdowns);
		return dropdowns;
	}

	@Override
	public void setFilterProperties(String configId, String cacheId,String typeId, HttpServletRequest request) {

	}

	@Override
	public void init(Object objectForm) {
		// TODO Auto-generated method stub
		
	}
	@Override
	public void setElementDisplayMode(String displayMode) {
		
	}
}
