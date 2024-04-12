package com.eaf.orig.ulo.control.util;

import java.util.ArrayList;
import java.util.HashMap;

import javax.servlet.http.HttpServletRequest;

import org.apache.log4j.Logger;

import com.eaf.core.ulo.common.display.ListBoxFilterInf;

public class TimeMinuteListBoxFilter implements ListBoxFilterInf{
	private static transient Logger logger = Logger.getLogger(TimeMinuteListBoxFilter.class);
	
	@Override
	public ArrayList<HashMap<String, Object>> filter(String configId, String cacheId, String typeId, HttpServletRequest request) {
		ArrayList<HashMap<String, Object>> dropdowns = new ArrayList<>();
		for(int i=0;i<60;i++){
			HashMap<String,Object> data = new HashMap<>();
			String MINUTE = String.valueOf(i);
			if(MINUTE.length()<2){
				MINUTE = "0"+MINUTE;
			}
			data.put("CODE", MINUTE);
			data.put("VALUE", MINUTE);
			dropdowns.add(data);
		}
		logger.debug("dropdowns : "+dropdowns);
		return dropdowns;
	}

	@Override
	public void setFilterProperties(String configId, String cacheId, String typeId, HttpServletRequest request) {
		
	}

	@Override
	public void init(Object objectForm) {
		// TODO Auto-generated method stub
		
	}
	@Override
	public void setElementDisplayMode(String displayMode) {
		
	}
}
