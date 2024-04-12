package com.eaf.orig.ulo.pl.app.utility;

import javax.servlet.http.HttpServletRequest;

import org.apache.log4j.Logger;

import com.eaf.j2ee.pattern.util.MessageResourceUtil;
import com.eaf.orig.master.shared.model.ReportParam;

public class ImportTool {
	static Logger logger = Logger.getLogger(ImportTool.class);
	public static String MessageNotAvalible(ReportParam timeM,HttpServletRequest request){
		String starttime = timeM.getParamValue();
		String endtime = timeM.getParamValue2();
		StringBuilder STR = new StringBuilder();
			STR.append(MessageResourceUtil.getTextDescription(request,"MSG_IMPORT_NOT_AVALIBLE"));
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
}
