package com.eaf.orig.logon;

import javax.servlet.http.HttpServletRequest;

import org.apache.log4j.Logger;

import com.eaf.orig.profile.model.LogonDataM;
import com.eaf.orig.shared.constant.OrigConstant;
import com.eaf.orig.shared.ejb.ORIGApplicationManager;
import com.eaf.orig.shared.service.ORIGEJBService;
import com.eaf.orig.shared.util.OrigUtil;

public class LogonTool {
	static Logger logger = Logger.getLogger(LogonTool.class);
	public static void Logout(HttpServletRequest request ,String flag){
		if(OrigConstant.FLAG_Y.equals(flag)){
			Logout(request);
		}
	}
	public static void Logout(HttpServletRequest request){
		try{
			String userName = (String) request.getSession().getAttribute("userName");	
			logger.debug("Logout >> UserName "+userName);
			try{
				boolean logonFlag = false;
				try{
					ORIGApplicationManager origBean = ORIGEJBService.getApplicationManager();
					if(!OrigUtil.isEmptyString(userName)){
						LogonDataM logonM = (LogonDataM) request.getSession().getAttribute("LogonData");		
						if(null == logonM){
							logonM = new LogonDataM();								
						}			
						logonM.setUserName(userName);
						logonFlag = origBean.CancelLogonOrig(userName,logonM.getIp(),logonM.getClientName());
					}
					logger.debug(">>> logonFlag " + logonFlag);
				}catch(Exception ex){
					logger.fatal(">>> Logon ORIG error ", ex);
				}
			}catch(Exception e){
				logger.error("Exception >> ", e);
			}						
			try{			
				if(!OrigUtil.isEmptyString(userName)){
					ORIGApplicationManager origBean = ORIGEJBService.getApplicationManager();
						origBean.cancleclaimByUserId(userName);
				}
			}catch(Exception e){
				logger.fatal("Exception >> ",e);
			}
		}catch(Exception e) {
			logger.fatal("Exception >> ",e);
		}
	}
}
