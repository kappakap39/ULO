package com.eaf.orig.ulo.control.util;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.apache.log4j.Logger;

import com.eaf.orig.ulo.app.dao.ORIGDAOFactory;
import com.eaf.orig.ulo.app.dao.exception.ApplicationException;
import com.eaf.orig.ulo.formcontrol.view.form.ORIGFormHandler;
import com.eaf.orig.ulo.model.app.ApplicationGroupDataM;

public class CancleClaimUtil {
	private static transient Logger logger = Logger.getLogger(CancleClaimUtil.class);
	public static void cancleClaim(HttpServletRequest request){
		try{
			ORIGFormHandler formHandler = (ORIGFormHandler)request.getSession().getAttribute("ORIGForm");
			if(null != formHandler){
				ApplicationGroupDataM applicationGroup = formHandler.getObjectForm();
				if(null != applicationGroup){
					String applicationGroupId = applicationGroup.getApplicationGroupId();
					try{
						ORIGDAOFactory.getApplicationGroupDAO().updateClaimBy(applicationGroupId,null);
					}catch(ApplicationException e){
						logger.fatal("ERROR",e);
					}
				}
			}
		}catch(Exception e){
			logger.fatal("ERROR",e);
		}
	}
	public static void cancleClaim(HttpSession session){
		try{
			ORIGFormHandler formHandler = (ORIGFormHandler)session.getAttribute("ORIGForm");
			if(null != formHandler){
				ApplicationGroupDataM applicationGroup = formHandler.getObjectForm();
				if(null != applicationGroup){
					String applicationGroupId = applicationGroup.getApplicationGroupId();
					try{
						ORIGDAOFactory.getApplicationGroupDAO().updateClaimBy(applicationGroupId,null);
					}catch(ApplicationException e){
						logger.fatal("ERROR",e);
					}
				}
			}
		}catch(Exception e){
			logger.fatal("ERROR",e);
		}
	}
}
