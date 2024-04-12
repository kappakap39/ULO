package com.eaf.inf.batch.ulo.notification.util;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.apache.log4j.Logger;

import com.eaf.core.ulo.common.cache.InfBatchProperty;
import com.eaf.core.ulo.common.exception.InfBatchException;
import com.eaf.core.ulo.common.util.InfBatchUtil;
import com.eaf.inf.batch.ulo.notification.dao.ApplicationInfoDAO;
import com.eaf.inf.batch.ulo.notification.dao.NotificationDAO;
import com.eaf.inf.batch.ulo.notification.dao.NotificationFactory;
import com.eaf.inf.batch.ulo.notification.model.NotifyApplication;
import com.eaf.inf.batch.ulo.notification.model.NotifyApplicationSegment;
import com.eaf.inf.batch.ulo.notification.model.Reason;
import com.eaf.inf.batch.ulo.notification.model.VCEmpInfoDataM;
import com.eaf.orig.ulo.app.dao.InfBatchOrigApplicationGroupDAO;
import com.eaf.orig.ulo.app.dao.InfBatchOrigApplicationLogDAO;
import com.eaf.orig.ulo.app.factory.InfBatchOrigDAOFactory;
import com.eaf.orig.ulo.app.model.ApplicationGroupDataM;
import com.eaf.orig.ulo.app.model.ApplicationLogDataM;
import com.eaf.orig.ulo.control.util.ApplicationUtil;

public class NotificationUtil {
	private static String APPLICATION_TYPE_ADD_SUP=InfBatchProperty.getInfBatchConfig("APPLICATION_TYPE_ADD_SUP");
	private static String TEMPLATE_INCREASE_PARAM_NAME=InfBatchProperty.getInfBatchConfig("NOTIFICATION_TEMPLATE_INCREASE_PARAM_NAME");
	private static String NOTIFICATION_CARD_LINK_FLAG_YES=InfBatchProperty.getInfBatchConfig("NOTIFICATION_CARD_LINK_FLAG_YES");
	private static String APPLICATION_TYPE_NEW=InfBatchProperty.getInfBatchConfig("APPLICATION_TYPE_NEW");
	private static String APPLICATION_TYPE_ADD=InfBatchProperty.getInfBatchConfig("APPLICATION_TYPE_ADD");
	private static String APPLICATION_TYPE_INCREASE=InfBatchProperty.getInfBatchConfig("APPLICATION_TYPE_INCREASE");
	private static transient Logger logger = Logger.getLogger(NotificationUtil.class);
	public static String getApplicationType(String appGroupId,String applyType,String templateId){
		try{
			NotificationDAO notificationDAO = NotificationFactory.getNotificationDAO();	
			if(APPLICATION_TYPE_ADD_SUP.equals(applyType)){
				String generalParamIncrease = InfBatchProperty.getGeneralParam(TEMPLATE_INCREASE_PARAM_NAME);
				String templateCode = notificationDAO.getTemplateCode(templateId);
				String cardLinkCustFlag = notificationDAO.getCardLinkCustFlag(appGroupId);
				if(null!=generalParamIncrease&&generalParamIncrease.contains(templateCode)){
					return APPLICATION_TYPE_INCREASE;
				}else if(NOTIFICATION_CARD_LINK_FLAG_YES.equals(cardLinkCustFlag)){
					return APPLICATION_TYPE_NEW;
				}else{
					return APPLICATION_TYPE_ADD;
				}
			}
		}catch(Exception e){
			logger.fatal("ERROR",e);
		}
		return applyType;
	}
	public static void mapVcEmpMobileNo(ArrayList<VCEmpInfoDataM> vcEmplist,HashMap<String,String> hMobileNo){
		if(null!=vcEmplist){
			for(VCEmpInfoDataM vcEmpInfo : vcEmplist){
				String saleId = vcEmpInfo.getEmpId();
				String vcEmpMobilePhone = vcEmpInfo.getMobilePhone();
				String mobileNo = hMobileNo.get(saleId);
				if(InfBatchUtil.empty(mobileNo)){
					hMobileNo.put(saleId, vcEmpMobilePhone);
				}
			}
		}
	}
	public static ApplicationGroupDataM loadApplicationGroup(String applicationGroupId)throws InfBatchException{
		ApplicationGroupDataM  appplicationGroup = new ApplicationGroupDataM();
		try{
			InfBatchOrigApplicationGroupDAO applicationGroupDAO = InfBatchOrigDAOFactory.getApplicationGroupDAO();
			appplicationGroup = applicationGroupDAO.loadOrigApplicationGroup(applicationGroupId);
		}catch(Exception e){
			logger.fatal("ERROR ",e);
			throw new InfBatchException(e);
		}
		return appplicationGroup;
	}
	public static boolean workflowAction(String applicationGroupId,int lifeCycle,String paramId)throws InfBatchException{
		try{
			InfBatchOrigApplicationLogDAO applicationLogDAO = InfBatchOrigDAOFactory.getApplicationLogDAO();
			ArrayList<ApplicationLogDataM> applicationLogs = applicationLogDAO.loadOrigApplicationLogM(applicationGroupId);
			if(null!=applicationLogs){
				for (ApplicationLogDataM applicationLog : applicationLogs) {
					String jobState = applicationLog.getJobState();
					if(null==applicationLog.getLifeCycle()){
						applicationLog.setLifeCycle(new BigDecimal(1));
					}
					if(InfBatchProperty.loopGeneralParam(paramId, jobState)&&null!=applicationLog.getLifeCycle()
								&&applicationLog.getLifeCycle().intValue()==lifeCycle){
						return true;
					}
				}
			}
		}catch(Exception e){
			logger.fatal("ERROR ",e);
			throw new InfBatchException(e);
		}
		return false;
	}
	public static NotifyApplicationSegment notifyApplication(String applicationGroupId)throws InfBatchException{
		NotifyApplicationSegment notifyApplicationSegment = new NotifyApplicationSegment();
		try{
			logger.debug("applicationGroupId : "+applicationGroupId);
			ApplicationInfoDAO applicationInfoDAO = NotificationFactory.getApplicationInfoDAO();
			List<NotifyApplication> notifyApplications = applicationInfoDAO.loadNotifyApplication(applicationGroupId);
			if(null!=notifyApplications){
				notifyApplicationSegment.setNotifyApplications(notifyApplications);
			}
			notifyApplicationSegment.setCaflow(workflowAction(applicationGroupId,notifyApplicationSegment.lifeCycle(),"WIP_JOBSTATE_CA"));
			//DF1273 set new attribute for eapp
			notifyApplicationSegment.setEapp( isEapp( applicationGroupId ) );
		}catch(Exception e){
			logger.fatal("ERROR ",e);
			throw new InfBatchException(e);
		}
		return notifyApplicationSegment;
	}
	public static boolean isRejectSanction(NotifyApplicationSegment notifyApplicationSegment,String finalAppDecision,String personalId){
		String NOTIFICATION_FINAL_APP_DECISION_REJECT = InfBatchProperty.getInfBatchConfig("NOTIFICATION_FINAL_APP_DECISION_REJECT");
		logger.debug("finalAppDecision : "+finalAppDecision);
		if(NOTIFICATION_FINAL_APP_DECISION_REJECT.equals(finalAppDecision)){
			Reason reason = notifyApplicationSegment.findReasonPerson(personalId);
			logger.debug("reasonCode : "+reason.getReasonCode());
			if(InfBatchProperty.lookup("NOTIFICATION_SANCTION_LIST_REJECT_CODE",reason.getReasonCode())){
				return true;
			}
		}
		return false;
	}
	public static boolean isEapp(String applicationGroupId) throws InfBatchException{
		boolean isEapp = false;
		ApplicationGroupDataM applicationGroup = loadApplicationGroup(applicationGroupId);
		if ( null != applicationGroup ) {
			isEapp = ApplicationUtil.eApp( applicationGroup.getSource() );
		}
		return isEapp;
	}
}