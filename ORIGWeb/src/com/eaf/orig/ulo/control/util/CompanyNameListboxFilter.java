package com.eaf.orig.ulo.control.util;

import java.util.ArrayList;
import java.util.HashMap;

import javax.servlet.http.HttpServletRequest;

import org.apache.log4j.Logger;

import com.eaf.core.ulo.common.display.ListBoxFilterInf;
import com.eaf.orig.ulo.app.dao.LookupDataFactory;

public class CompanyNameListboxFilter implements ListBoxFilterInf{
	Logger logger = Logger.getLogger(CompanyNameListboxFilter.class);
	
	@Override
	public ArrayList<HashMap<String, Object>> filter(String configId, String cacheId, String typeId, HttpServletRequest request) {
		logger.debug("CompanyNameListboxFilter.filter");
		ArrayList<HashMap<String, Object>> list = new ArrayList<>();
		try{
			ArrayList<HashMap<String, Object>> companyNames = LookupDataFactory.getLookupDataDAO().getCompanyList();
			for(HashMap<String, Object> companyName : companyNames){
				list.add(companyName);
			}
		}catch(Exception e){
			logger.fatal("ERROR ",e);
		}
		return list;
	}

	@Override
	public void setFilterProperties(String configId, String cacheId, String typeId, HttpServletRequest request) {
		
	}

	@Override
	public void init(Object objectForm) {
		
	}
	@Override
	public void setElementDisplayMode(String displayMode) {
		
	}
}
