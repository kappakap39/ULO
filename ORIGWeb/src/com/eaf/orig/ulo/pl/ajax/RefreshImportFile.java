package com.eaf.orig.ulo.pl.ajax;

import java.io.File;
import java.util.Calendar;
import java.util.Date;
import javax.servlet.http.HttpServletRequest;
import org.apache.log4j.Logger;

import com.eaf.orig.cache.properties.ORIGCacheDataM;
import com.eaf.orig.master.shared.model.ReportParam;
import com.eaf.orig.shared.constant.OrigConstant;
import com.eaf.orig.shared.dao.utility.ObjectDAOFactory;
import com.eaf.orig.shared.servlet.AjaxDisplayGenerateInf;
import com.eaf.orig.shared.utility.ORIGCacheUtil;
import com.eaf.orig.ulo.pl.app.dao.exception.PLOrigApplicationException;
import com.eaf.orig.ulo.pl.app.utility.DataFormatUtility;
import com.eaf.orig.ulo.pl.app.utility.ImportTool;
import com.eaf.orig.ulo.pl.app.utility.JsonObjectUtil;
import com.eaf.orig.ulo.pl.app.utility.PLMessageResourceUtil;
import com.eaf.orig.ulo.pl.model.app.PLAttachmentHistoryDataM;
import com.eaf.orig.ulo.pl.service.PLORIGEJBService;

public class RefreshImportFile implements AjaxDisplayGenerateInf {
	private Logger logger = Logger.getLogger(this.getClass());
	@Override
	public String getDisplayObject(HttpServletRequest request) throws PLOrigApplicationException {
		// TODO Auto-generated method stub
		JsonObjectUtil _JsonObjectUtil = new JsonObjectUtil();
		String interfaceType = request.getParameter("interface_type");
		logger.debug("@@@@@ interfaceType:" + interfaceType);
		
		if(interfaceType != null && !"".equals(interfaceType)){
			ORIGCacheDataM cacheM = ORIGCacheUtil.getInstance().GetListboxCacheDataM(OrigConstant.fieldId.INTERFACE_TYPE, OrigConstant.BusClass.FCP_ALL_ALL, interfaceType);
			boolean foundImportFile = false;
			boolean foundAttachFile = false;
			String filePath = cacheM.getSystemID11() + File.separator + OrigConstant.BACKUP + File.separator + DataFormatUtility.dateToStringYYYYMMDD(new Date());
			if(OrigConstant.InterfaceType.INCREASE_CREDIT_LINE.equals(interfaceType)){
				String attachmentId = "CL" + DataFormatUtility.dateToStringYYYYMMDD(new Date());
				PLAttachmentHistoryDataM attachM = PLORIGEJBService.getORIGDAOUtilLocal().loadModelOrigAttachmentHistoryMFromAttachId(attachmentId);
				if(attachM != null){
					filePath = attachM.getFilePath();
				}
			}
			try{
				File directory = new File(filePath);
				if(directory.isDirectory()){
					if(directory.list().length >0){
					   //list all the directory contents
			    	   String files[] = directory.list();
			    	   for (String fileName : files) {
		    			   String importFileLink = "<a onclick=\"downLoadFile('" + fileName + "')\" href=\"#\">"+ fileName +"</a>";
				    	   logger.debug("@@@@@ importFileLink:" + importFileLink);
			    		   if(fileName.indexOf(cacheM.getSystemID8()) >= 0){
			    			   _JsonObjectUtil.CreateJsonObject("div_import_file_name", importFileLink);
			    			   foundImportFile = true;
			    		   }else if (fileName.indexOf(OrigConstant.APPROVE_FILE_NAME) >= 0){
			    			   _JsonObjectUtil.CreateJsonObject("div_approve_file_name", importFileLink);
			    			   foundAttachFile = true;
			    		   }
			    	   }
					}
				}
				if(!foundImportFile){
					_JsonObjectUtil.CreateJsonObject("div_import_file_name", "");
				}
				if(!foundAttachFile){
					_JsonObjectUtil.CreateJsonObject("div_approve_file_name", "");
				}
				String showDetail = "";
				if(foundImportFile || foundAttachFile){
					showDetail = " <span class='BigtodotextGreenLeft'>" +PLMessageResourceUtil.getTextDescription(request, "WAIT_IMPORT_DATA_END_DAY") + "</span>";
				}
				String div_import_button = "<input type=\"button\" name=\"importBT\" value=\"Import\" class=\"button\" onclick=\"importExcel2(this.form)\">&nbsp;&nbsp;";
				
				ReportParam paramM = ObjectDAOFactory.getReportParamDAO().getReportParamM(OrigConstant.ReportParamType.IMPORT_TIME, cacheM.getSystemID13());
				if(paramM.getParamCode() != null && paramM.getParamValue() != null && paramM.getParamValue2() != null){
					Date canImportBegin = toDayImportTime(paramM.getParamValue());
					Date canImportEnd   = toDayImportTime(paramM.getParamValue2());
					Date currentDate	= new Date();
					if(canImportBegin != null && canImportEnd != null && (currentDate.compareTo(canImportBegin) < 0 || currentDate.compareTo(canImportEnd) >0)){
						showDetail = showDetail + " <span class='BigtodotextRedLeft'>" + ImportTool.MessageNotAvalible(paramM, request) + "</span>";
						div_import_button = "<input type=\"button\" name=\"importBT\" value=\"Import\" class=\"button\" disabled>&nbsp;&nbsp;";
					}
				}
				
				_JsonObjectUtil.CreateJsonObject("div_response",showDetail);
				_JsonObjectUtil.CreateJsonObject("div_import_button", div_import_button);
			}catch (Exception e){
				e.printStackTrace();
			}
		}
		else{
			_JsonObjectUtil.CreateJsonObject("div_import_file_name", "");
			_JsonObjectUtil.CreateJsonObject("div_approve_file_name", "");
			_JsonObjectUtil.CreateJsonObject("div_response","");
		}
		return _JsonObjectUtil.returnJson();
	}
	
	private Date toDayImportTime(String importTime){
		if(importTime != null && importTime.trim().length() == 4){
			try{
				String strTime = importTime.trim();
				int hours   = Integer.parseInt(strTime.substring(0,2));
				int minute  = Integer.parseInt(strTime.substring(2));
				Calendar calender = Calendar.getInstance();
				calender.setTime(new Date());
				calender.set(Calendar.HOUR_OF_DAY,hours);
				calender.set(Calendar.MINUTE,minute);
				return calender.getTime();
			}catch(Exception e){
				return null;
			}
		}else{
			return null;
		}
	}
	
}
