package com.eaf.orig.ulo.control.util;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.apache.log4j.Logger;

import com.eaf.cache.data.CacheDataM;
import com.eaf.core.ulo.common.display.ListBoxFilterInf;
import com.eaf.orig.shared.model.DownloadReportFormHandler;

public class DateReportListBoxFilter implements ListBoxFilterInf{
	private static transient Logger logger = Logger.getLogger(DateReportListBoxFilter.class);
	@Override
	public ArrayList<HashMap<String, Object>> filter(String configId, String cacheId, String typeId, HttpServletRequest request) {
		ArrayList<HashMap<String,Object>> reportDates = new ArrayList<>();
		try{
			DownloadReportFormHandler formHandler = (DownloadReportFormHandler)request.getSession().getAttribute("DownloadReportForm");
			if(null==formHandler){
				formHandler = new DownloadReportFormHandler();
				request.getSession().setAttribute("DownloadReportForm",formHandler);
			}
			List<CacheDataM> dates = formHandler.getReportDates();
			if(null!=dates)
				for(CacheDataM date : dates){
					HashMap<String, Object> hm = new HashMap<>();
						hm.put("CODE", date.getCode());
						hm.put("VALUE", date.getEnDesc());
					reportDates.add(hm);
				}
		}catch(Exception e){
			logger.fatal("ERROR ",e);
		}
		logger.debug("reportDates : "+reportDates);
		return reportDates;
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
