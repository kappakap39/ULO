package com.eaf.orig.ulo.pl.app.view.webaction;

import org.apache.log4j.Logger;

import com.eaf.j2ee.pattern.control.event.Event;
import com.eaf.j2ee.pattern.control.event.EventResponse;
import com.eaf.j2ee.pattern.view.webaction.WebAction;
import com.eaf.j2ee.pattern.view.webaction.WebActionHelper;

public class SearchDownLoadWebaction  extends WebActionHelper implements WebAction{
	static Logger logger = Logger.getLogger(SearchDownLoadWebaction.class);
	@Override
	public Event toEvent(){
		return null;
	}

	@Override
	public boolean requiredModelRequest(){
		return false;
	}

	@Override
	public boolean processEventResponse(EventResponse response){
		return false;
	}

	@Override
	public boolean preModelRequest(){
//		DownloadReportFormHandler downloadM = (DownloadReportFormHandler) getRequest().getSession().getAttribute("DOWNLOAD");
//		String download_date = getRequest().getParameter("DATE_REPORT");		
//		logger.debug("download_date >> "+download_date);
//		downloadM.setDate(download_date);
		return true;
	}

	@Override
	public int getNextActivityType(){
		return 0;
	}

	@Override
	public boolean getCSRFToken(){
		return false;
	}

}
