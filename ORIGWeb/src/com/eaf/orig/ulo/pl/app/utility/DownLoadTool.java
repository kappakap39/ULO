package com.eaf.orig.ulo.pl.app.utility;

import java.io.File;
import java.io.FilenameFilter;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.apache.log4j.Logger;

import com.eaf.core.ulo.common.cont.InfBatchConstant;
import com.eaf.core.ulo.common.util.PathUtil;
import com.eaf.core.ulo.common.util.Util;
import com.eaf.j2ee.pattern.util.MessageResourceUtil;
import com.eaf.orig.master.shared.model.ReportParam;
import com.eaf.orig.shared.constant.ReportConstant;
import com.eaf.orig.shared.dao.utility.ObjectDAOFactory;
import com.eaf.orig.shared.dao.utility.ReportParamDAO;
import com.eaf.orig.shared.model.DownloadReportFormHandler;
import com.eaf.orig.shared.util.OrigUtil;

public class DownLoadTool {
	static Logger logger = Logger.getLogger(DownLoadTool.class);
	public DownLoadTool(){
		super();
	}
	public static String DisplayTableDownload(HttpServletRequest request,String date ,ReportParam paramM,DownloadReportFormHandler formHandler){
		return getHTMLEXPORTFILE(request, date, paramM, formHandler);
	}
	private static String getHTMLEXPORTFILE(HttpServletRequest request,String date ,ReportParam paramM,DownloadReportFormHandler downloadM){
		StringBuilder HTML = new StringBuilder("");
//		HashMap<String,String> FILE_REPORT = (HashMap<String, String>) request.getSession().getAttribute("FILE_REPORT");
//		if(null == FILE_REPORT){
//			FILE_REPORT = new HashMap<String, String>();
//			request.getSession().setAttribute("FILE_REPORT",FILE_REPORT);
//		}
//		
//		logger.debug("ParamCode >> "+paramM.getParamCode());
//		logger.debug("date >> "+date);
//		ReportParamDAO reportParamDAO = ObjectDAOFactory.getReportParamDAO();
//		Vector<INFExportDataM> exportVect = reportParamDAO.getINFExport(paramM.getParamCode(),date);
//		if(!OrigUtil.isEmptyVector(exportVect)){
//			ReportParam timeM = time(paramM,downloadM.getParam_time());
//			int time = getTime();
//			if(avalible(timeM,time)){
//				int i = 1;
//				int length = exportVect.size();
//				for(INFExportDataM infM : exportVect){
//					if(null == paramM.getParamValue()) continue;
//					String file = getName(length,i,date,paramM);
//					HTML.append("<tr class='nev-download' height='25px'>");
//						HTML.append("<td width='80%'><div class='textL'>"+file+"</div></td>");
//						HTML.append("<td>");
//						String id = paramM.getParamCode()+"|"+infM.getDateID();
//						HTML.append(HTMLRenderUtil.DisplayButton(id,"Download","EDIT","button export_file"));
//						HTML.append("</td>");
//					HTML.append("</tr>");
//					FILE_REPORT.put(id,file);
//					i++;
//				}
//			}else{
//				HTML.append("<tr>");
//					HTML.append("<td colspan='2' style='color:red;'>"+MessageNotAvalible(timeM,request)+"</td>");
//				HTML.append("</tr>");
//			}
//		}else{
//			HTML.append("<tr>");
//				HTML.append("<td colspan='2' style='color:red;'>No data found.</td>");
//			HTML.append("</tr>");
//		}
		return HTML.toString();
	}
	private static String getName(int length,int index,String date,ReportParam paramM){
		if(length == 1){
			return paramM.getParamValue().replaceAll("DATE_TIME",date);
		}
		return paramM.getParamValue().replaceAll("DATE_TIME",date+"_"+index);
	}
	private static String getHTMLREPORT(HttpServletRequest request,String date ,ReportParam paramM,DownloadReportFormHandler downloadM){
//		HashMap<String,String> FILE_REPORT = (HashMap<String, String>) request.getSession().getAttribute("FILE_REPORT");
//		if(null == FILE_REPORT){
//			FILE_REPORT = new HashMap<String, String>();
//			request.getSession().setAttribute("FILE_REPORT", FILE_REPORT);
//		}
//		StringBuilder HTML = new StringBuilder("");
//		HashMap<String, List<String>> hashMap = getList(date,paramM,downloadM.getType());
//		if(!Util.empty(hashMap)){
//			List<String> pathList = hashMap.get("pathList");
//			List<String> fileList = hashMap.get("fileList");
//			String REPORT_NAME = InfBatchProperty.getInfBatchConfig(paramM.getParamCode()+"_"+InfBatchConstant.ReportParam.REPORT_NAME);
//			if(null != fileList && fileList.size() > 0){
//				ReportParam timeM = time(paramM,downloadM.getParam_time());
//				int time = getTime();
////				if(avalible(timeM,time)){
//					int i = 1;
//					for(int count = 0 ; count<fileList.size() ; count++){
//						HTML.append("<tr class='nev-download text-center' height='25px'>");
//							HTML.append("<td>"+REPORT_NAME+"</td>");
//							HTML.append("<td>"+fileList.get(count)+"</td>");
//							HTML.append("<td>"+paramM.getCreateBy()+"</td>");
//							HTML.append("<td>");
//							String id = paramM.getParamCode()+"|"+String.valueOf(i);
//							HTML.append(HTMLRenderUtil.DisplayButton(id,"Download","EDIT","btn btn-download-green report_file"));
//							FILE_REPORT.put("report_name"+id,fileList.get(count));
//							FILE_REPORT.put("report_file"+id,pathList.get(count)+File.separator+date);
//							HTML.append("</td>");
//						HTML.append("</tr>");
//						i++;
//					}
////				}else{
////					HTML.append("<tr>");
////						HTML.append("<td colspan='2' style='color:red;'>"+MessageNotAvalible(timeM,request)+"</td>");
////					HTML.append("</tr>");
////				}
//			}else{
//				HTML.append("<tr class='text-center'>");
//					HTML.append("<td>"+REPORT_NAME+"</td>");
//					HTML.append("<td colspan='3' style='color:red;'>File not found.</td>");
//				HTML.append("</tr>");
//			}
//		}
//		return HTML.toString();
		return "";
	}
	public static String MessageNotAvalible(ReportParam timeM,HttpServletRequest request){
		String starttime = timeM.getParamValue();
		String endtime = timeM.getParamValue2();
		StringBuilder STR = new StringBuilder();
			STR.append(MessageResourceUtil.getTextDescription(request,"MSG_DOWNLOAD_NOT_AVALIBLE"));
			STR.append(STRTIME(starttime));
			STR.append(" - ");
			STR.append(STRTIME(endtime));
			STR.append(" \u0E19\u002E");
		return STR.toString();
	}
	public static String STRTIME(String time){
		if(null == time || time.length() < 4) return "";
		return time.substring(0,2)+":"+time.substring(2,4);
	}
	public static int getTime(){
		Date date = new Date(); 
		DateFormat dateFormat = new SimpleDateFormat("HHmm");
		Calendar calendar = Calendar.getInstance();
			calendar.setTime(date);
			calendar.set(Calendar.HOUR_OF_DAY,calendar.get(Calendar.HOUR_OF_DAY));
			calendar.set(Calendar.MINUTE,calendar.get(Calendar.MINUTE));
		return Integer.valueOf(dateFormat.format(calendar.getTime()));
	}
	
	public static ReportParam time(ReportParam paramM,String param_time){
		String reportID = paramM.getParamCode();
		ReportParamDAO reportParamDAO = ObjectDAOFactory.getReportParamDAO();
		return reportParamDAO.getReportParamM(param_time, reportID);
	}
	
	public static boolean avalible(ReportParam timeM,int time){
		if(null == timeM.getParamValue() || null == timeM.getParamValue2()){
			return false;
		}
		int starttime = Integer.valueOf(timeM.getParamValue());
		int endtime = Integer.valueOf(timeM.getParamValue2());
		logger.debug("current time >> "+time);
		logger.debug("starttime >>"+starttime);
		logger.debug("endtime >> "+endtime);
		if(time >= starttime && time <= endtime){
			return true;
		}
		return false;
	}
	
	public static String getPath(String file,String date,ReportParam paramM,String download_type){
		StringBuilder PATH = new StringBuilder();
			PATH.append(paramM.getParamValue2());
			PATH.append(File.separator);
			PATH.append(date);
//			PATH.append(File.separator);
			if(ReportConstant.DOWNLOAD.DOWNLOAD_REJECT_LETTER.equals(download_type)){
				PATH.append(paramM.getParamCode());
				PATH.append(File.separator);
			}
//			PATH.append(file);
		return PATH.toString();
	}
	
	public static HashMap<String, List<String>> getList(String date,ReportParam paramM,String download_type){
		HashMap<String, List<String>> hashMap = new HashMap<>();
		List<String> pathList = new ArrayList<>();
		List<String> fileList = new ArrayList<>();
		if(OrigUtil.isEmptyString(date) ){
			logger.debug("empty return null");
			return null;
		}
		try{
			StringBuilder PATH = new StringBuilder();
			List<String> directorys = new ArrayList<>();
			if(Util.empty(paramM.getParamValue())){
				String directory = PathUtil.getPath(paramM.getParamCode()+"_"+InfBatchConstant.PATH.BACKUP);
				if(!Util.empty(directory)){
					directorys.add(directory);
				}
			}else{
				String[] params = paramM.getParamValue().split(",");
				for(String param : params){
					String directory = PathUtil.getPath(param+"_"+InfBatchConstant.PATH.BACKUP);
					if(!Util.empty(directory)){
						directorys.add(directory);
					}
				}
				
				//remove duplicate directory
				HashSet<String> hs = new HashSet<>();
				hs.addAll(directorys);
				directorys.clear();
				directorys.addAll(hs);
			}
			for(String path : directorys){
				PATH.append(path);
				PATH.append(File.separator);
				
				PATH.append(date);
				if(ReportConstant.DOWNLOAD.DOWNLOAD_REJECT_LETTER.equals(download_type)){
					PATH.append(File.separator);
					PATH.append(paramM.getParamCode());
				}
				File directory = new File(PATH.toString());
				if(directory.exists()){
					logger.debug("directory : "+directory);
					logger.debug("date : "+date);
					if(filter(download_type)){
						FilenameFilter filter = new FilenameFilter(){				
							@Override
							public boolean accept(File dir, String name){
								String lowercaseName = name.toLowerCase();
								if (lowercaseName.endsWith(".tgz")) {
									return true;
								} else {
									return false;
								}
							}
						};
						String[] fileFilters = directory.list(filter);
						for(String fileFilter : fileFilters){
							pathList.add(path);
							fileList.add(fileFilter);
						}
					}else{
						String[] files = directory.list();
						for(String file : files){
							pathList.add(path);
							fileList.add(file);
						}
					}
				}
			}
			hashMap.put("pathList", pathList);
			hashMap.put("fileList", fileList);
			return hashMap;
		}catch(Exception e){
			return null;
		}
	}
	public static boolean filter(String download_type){
		if(ReportConstant.DOWNLOAD.DOWNLOAD_REJECT_LETTER.equals(download_type)
				|| ReportConstant.DOWNLOAD.DOWNLOAD_CONSENT_IMAGE.equals(download_type)){
			return true;
		}
		return false;
	}
}
