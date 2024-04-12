package com.eaf.orig.ulo.pl.app.view.webaction;

import java.io.File;
import java.io.FilenameFilter;
import java.io.IOException;
import java.net.HttpURLConnection;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashSet;
import java.util.List;
import java.util.Vector;

import javax.net.ssl.HostnameVerifier;
import javax.net.ssl.HttpsURLConnection;
import javax.net.ssl.SSLSession;

import org.apache.log4j.Logger;
import org.springframework.http.ResponseEntity;
import org.springframework.http.client.SimpleClientHttpRequestFactory;
import org.springframework.web.client.RestTemplate;

import com.eaf.cache.data.CacheDataM;
import com.eaf.core.ulo.common.cont.InfBatchConstant;
import com.eaf.core.ulo.common.properties.SystemConfig;
import com.eaf.core.ulo.common.util.PathUtil;
import com.eaf.core.ulo.common.util.Util;
import com.eaf.ias.shared.model.ObjectM;
import com.eaf.ias.shared.model.service.IASServiceRequest;
import com.eaf.ias.shared.model.service.IASServiceResponse;
import com.eaf.j2ee.pattern.control.event.Event;
import com.eaf.j2ee.pattern.control.event.EventResponse;
import com.eaf.j2ee.pattern.view.webaction.WebAction;
import com.eaf.j2ee.pattern.view.webaction.WebActionHelper;
import com.eaf.orig.master.shared.model.ReportParam;
import com.eaf.orig.profile.model.UserDetailM;
import com.eaf.orig.shared.constant.OrigConstant;
import com.eaf.orig.shared.constant.ReportConstant;
import com.eaf.orig.shared.dao.utility.ObjectDAOFactory;
import com.eaf.orig.shared.dao.utility.ReportParamDAO;
import com.eaf.orig.shared.model.DownloadReportFormHandler;

public class SearchDownloadReportWebAction extends WebActionHelper implements WebAction {
	private static transient Logger logger = Logger.getLogger(SearchDownloadReportWebAction.class);	
	
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
	public boolean preModelRequest() {
		try{
			DownloadReportFormHandler formHandler = (DownloadReportFormHandler)getRequest().getSession().getAttribute("DownloadReportForm");
			if(null==formHandler){
				formHandler = new DownloadReportFormHandler();
				getRequest().getSession().setAttribute("DownloadReportForm",formHandler);
			}
			String reportDate = getRequest().getParameter("reportDate");
			logger.debug("reportDate : "+reportDate);
			if(!Util.empty(reportDate)){
				formHandler.setReportDate(reportDate);
				return true;
			}
			UserDetailM userM = (UserDetailM) getRequest().getSession().getAttribute("ORIGUser");
			if (userM == null) {
				userM = new UserDetailM();
			}
			String userName = userM.getUserName();
			logger.debug("userName : "+userName);
			String IAS_SERVICE_OBJECTROLE_URL = SystemConfig.getProperty("IAS_SERVICE_OBJECTROLE_URL");
			logger.debug("IAS_SERVICE_OBJECTROLE_URL : "+IAS_SERVICE_OBJECTROLE_URL);
			IASServiceRequest serviceRequest = new IASServiceRequest();		
			serviceRequest.setUserName(userName);
			serviceRequest.setSystemName(OrigConstant.SYSTEM_NAME);	
			RestTemplate restTemplate = new RestTemplate(new SimpleClientHttpRequestFactory(){
				@Override
				protected void prepareConnection(HttpURLConnection connection, String httpMethod)throws IOException {
			        if(connection instanceof HttpsURLConnection ){
			            ((HttpsURLConnection) connection).setHostnameVerifier(new HostnameVerifier(){									
							@Override
							public boolean verify(String arg0, SSLSession arg1) {
								return true;
							}
						});
			        }
					super.prepareConnection(connection, httpMethod);
				}
			});
			ResponseEntity<IASServiceResponse> responseEntity = restTemplate.postForEntity(IAS_SERVICE_OBJECTROLE_URL,serviceRequest,IASServiceResponse.class);
			IASServiceResponse serviceResponse = responseEntity.getBody();
			logger.debug("serviceResponse : "+serviceResponse);
			Vector<ObjectM> vObject = serviceResponse.getObjects();
			List<String> accessReportList = new ArrayList<String>();	
			if(null!=vObject){
				for(int i=0;i<vObject.size();i++){
					ObjectM object = (ObjectM) vObject.get(i);				
					if(OrigConstant.OBJECT_TYPE_REPORT.equalsIgnoreCase(object.getObjectType())){
						String objectName = object.getObjectName();
						accessReportList.add(objectName);	
					}
				}
			}
			logger.debug("Access Report List : "+accessReportList);
			ReportParamDAO reportParamDAO = ObjectDAOFactory.getReportParamDAO();
			Vector<ReportParam> masterReports = reportParamDAO.getReportParamM(ReportConstant.PARAM.EXP_REPORT_PATH);
			List<ReportParam> reportParams = new ArrayList<>();
			if(!Util.empty(masterReports)){
				for(ReportParam masterReport:masterReports){
					if(accessReportList.contains(masterReport.getParamCode())){
						reportParams.add(masterReport);
					}
				}
			}
			formHandler.setReportParams(reportParams);
			processReportData(formHandler);
			try{
				List<CacheDataM> reportDates = formHandler.getReportDates();
				if(!Util.empty(reportDates)){
					formHandler.setReportDate(reportDates.get(0).getCode());
				}
			}catch(Exception e){
				logger.fatal("ERROR",e);
			}
			getRequest().getSession().setAttribute("DownloadReportForm",formHandler);
		}catch(Exception e){
			logger.fatal("ERROR",e);
		}
		return true;
	}
	public void processReportData(DownloadReportFormHandler formHandler){
		try{
			List<CacheDataM> reportDates = new ArrayList<CacheDataM>();
			List<ReportParam> reportParams = formHandler.getReportParams();
			List<String> dates = new ArrayList<String>();
			if(null != reportParams){
				for(ReportParam reportParam:reportParams){
					List<String> directorys = new ArrayList<>();
					if(Util.empty(reportParam.getParamValue())){
						String directory = PathUtil.getPath(reportParam.getParamCode()+"_"+InfBatchConstant.PATH.BACKUP);
						directorys.add(directory);
					}else{
						String[] params = reportParam.getParamValue().split(",");
						for(String param : params){
							String directory = PathUtil.getPath(param+"_"+InfBatchConstant.PATH.BACKUP);
							directorys.add(directory);
						}
						HashSet<String> hs = new HashSet<>();
						hs.addAll(directorys);
						directorys.clear();
						directorys.addAll(hs);
					}
					logger.debug("directorys : "+directorys);
					reportParam.setReportPaths(directorys);
					for(String directory : directorys){
						if(!Util.empty(directory)){
							File file = new File(directory);	
							String[] fileList = file.list(new FilenameFilter(){
								  @Override
								  public boolean accept(File dir, String name){
								    return new File(dir, name).isDirectory();
								  }
							});
							filterReportDate(fileList,dates);
						}
					}
				}
			}
			logger.debug("dates : "+dates);
			Collections.sort(dates,new Comparator<String>(){
				@Override
				public int compare(String o1,String o2){
					try{
						return Integer.valueOf(o2).compareTo(Integer.valueOf(o1));
					}catch(Exception e){}
					return 0;
				}
			});
			for(String date:dates){
				CacheDataM cacheM = new CacheDataM();
					cacheM.setCode(date);
					cacheM.setEnDesc(date);
					cacheM.setActiveStatus(OrigConstant.ACTIVE_FLAG);
				reportDates.add(cacheM);
			}
			formHandler.setReportDates(reportDates);
		}catch(Exception e){
			logger.fatal("ERROR ",e);
		}
	}
	public void filterReportDate(String fileList[],List<String> dates){
		if(null != fileList){
			for(String fileName:fileList){
				if(!dates.contains(fileName)){
					dates.add(fileName);
				}
			}
		}
	}
	@Override
	public int getNextActivityType(){
		return 0;
	}
}
