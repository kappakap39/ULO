package com.eaf.orig.ulo.pl.app.view.webaction;

import java.io.File;
import java.io.FilenameFilter;
import java.util.Collections;
import java.util.Comparator;
import java.util.Vector;

import org.apache.log4j.Logger;

import com.eaf.cache.data.CacheDataM;
import com.eaf.j2ee.pattern.control.FrontController;
import com.eaf.j2ee.pattern.control.event.Event;
import com.eaf.j2ee.pattern.control.event.EventResponse;
import com.eaf.j2ee.pattern.view.webaction.WebAction;
import com.eaf.j2ee.pattern.view.webaction.WebActionHelper;
import com.eaf.orig.master.shared.model.ReportParam;
import com.eaf.orig.shared.constant.OrigConstant;
import com.eaf.orig.shared.dao.utility.ObjectDAOFactory;
import com.eaf.orig.shared.dao.utility.ReportParamDAO;
import com.eaf.orig.shared.model.DownloadReportFormHandler;
 
public class LoadReportMWebaction extends WebActionHelper implements WebAction {
	Logger logger = Logger.getLogger(LoadReportMWebaction.class);
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
	
	private Vector<CacheDataM> dateVect = new Vector<CacheDataM>();
		
	@Override
	public boolean preModelRequest(){
//				
//		String DOWNLOAD_TYPE = getRequest().getParameter("download_type");
//		
//		DownloadReportFormHandler downloadM = new DownloadReportFormHandler();
//		
//		Vector<ReportParam> pathVect = setDownLoadM(downloadM, DOWNLOAD_TYPE);
//		
//		if(ReportConstant.DOWNLOAD.DOWNLOAD_EXPORT_FILE.equals(DOWNLOAD_TYPE)){
//			getDATE();
//		}else{
//			getDATE(pathVect);
//		}
//		
//		if(!OrigUtil.isEmptyVector(dateVect)){
//			CacheDataM dataM = (CacheDataM) dateVect.firstElement();
//			downloadM.setDate(dataM.getCode());
//		}
//		
//		downloadM.setType(DOWNLOAD_TYPE);
//		downloadM.setDateVect(dateVect);
//		downloadM.setPathVect(pathVect);
//		
//		getRequest().getSession().setAttribute("DOWNLOAD",downloadM);	
			
		return true;
		
	}
	public void getDATE(){
		ReportParamDAO reportParamDAO = ObjectDAOFactory.getReportParamDAO();
		dateVect = reportParamDAO.getDATE();
	}
	public void getDATE(Vector<ReportParam> pathVect){
		
		if(null != pathVect){
			for(ReportParam reportParamM : pathVect){
				File file = new File(reportParamM.getParamValue());	
				String[] list = file.list(new FilenameFilter(){
					  @Override
					  public boolean accept(File dir, String name){
					    return new File(dir, name).isDirectory();
					  }
				});
				getDATE(list);
			}
		}
		
		Collections.sort(dateVect, new Comparator<CacheDataM>(){ 
		    public int compare(CacheDataM f1, CacheDataM f2){
		    	try{
		    		return Integer.valueOf(f2.getCode()).compareTo(Integer.valueOf(f1.getCode()));
		    	}catch(Exception e){
					return -1;
				}
		    }
		});
		
	}
	public Vector<ReportParam> setDownLoadM(DownloadReportFormHandler downloadM,String DOWNLOAD_TYPE){
		ReportParamDAO reportParamDAO = ObjectDAOFactory.getReportParamDAO();
		Vector<ReportParam> pathVect = null;
//		if(ReportConstant.DOWNLOAD.DOWNLOAD_REPORT.equals(DOWNLOAD_TYPE)){			
//			pathVect = reportParamDAO.getReportParamM(ReportConstant.PARAM.EXP_REPORT_PATH);
//			downloadM.setParam_path(ReportConstant.PARAM.EXP_REPORT_PATH);
//			downloadM.setParam_time(ReportConstant.PARAM.EXP_REPORT_TIME);			
//		}else if(ReportConstant.DOWNLOAD.DOWNLOAD_REJECT_LETTER.equals(DOWNLOAD_TYPE)){			
//			pathVect = reportParamDAO.getReportParamM(ReportConstant.PARAM.EXP_REJECT_LETTER_PATH);	
//			downloadM.setParam_path(ReportConstant.PARAM.EXP_REJECT_LETTER_PATH);
//			downloadM.setParam_time(ReportConstant.PARAM.EXP_REJECT_LETTER_TIME);			
//		}else if(ReportConstant.DOWNLOAD.DOWNLOAD_CONSENT_IMAGE.equals(DOWNLOAD_TYPE)){		
//			pathVect = reportParamDAO.getReportParamM(ReportConstant.PARAM.EXP_CONSENT_IMAGE_PATH);	
//			downloadM.setParam_path(ReportConstant.PARAM.EXP_CONSENT_IMAGE_PATH);
//			downloadM.setParam_time(ReportConstant.PARAM.EXP_CONSENT_IMAGE_TIME);		
//		}else if(ReportConstant.DOWNLOAD.DOWNLOAD_EXPORT_FILE.equals(DOWNLOAD_TYPE)){
//			pathVect = reportParamDAO.getReportParamM(ReportConstant.PARAM.EXP_EXPORT_FILE_PATH);	
//			downloadM.setParam_path(ReportConstant.PARAM.EXP_EXPORT_FILE_PATH);
//			downloadM.setParam_time(ReportConstant.PARAM.EXP_EXPORT_FILE_TIME);	
//		}
		return pathVect;
	}
	public void getDATE(String list[]){
		if(null != list){
			for(String date:list){
//				logger.debug("folder date "+date);
				if(!FOUND(date)){
					CacheDataM cacheM = new CacheDataM();
						cacheM.setCode(date);
						cacheM.setEnDesc(date);
						cacheM.setThDesc(date);
						cacheM.setActiveStatus(OrigConstant.ACTIVE_FLAG);
					dateVect.add(cacheM);
				}
			}
		}
	}
	public boolean FOUND(String date){
		for(CacheDataM cacheM : dateVect){
			if(cacheM.getCode().equals(date)){
				return true;
			}
		}
		return false;
	}
	
    public int getNextActivityType(){
        return FrontController.PAGE;
    }
    public String getNextActionParameter(){
        return "page=REPORT_SCREEN_DL";
    }

	@Override
	public boolean getCSRFToken(){
		return false;
	}

}
