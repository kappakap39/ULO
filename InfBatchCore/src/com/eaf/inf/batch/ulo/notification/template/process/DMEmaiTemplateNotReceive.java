package com.eaf.inf.batch.ulo.notification.template.process;

import java.util.ArrayList;
import java.util.HashMap;

import org.apache.log4j.Logger;

import com.eaf.core.ulo.common.display.Formatter;
import com.eaf.core.ulo.common.util.InfBatchUtil;
import com.eaf.core.ulo.service.template.cont.TemplateBuilderConstant;
import com.eaf.core.ulo.service.template.inf.TemplateBuilderHelper;
import com.eaf.core.ulo.service.template.model.TemplateBuilderRequest;
import com.eaf.core.ulo.service.template.model.TemplateVariableDataM;
import com.eaf.inf.batch.ulo.notification.dao.NotificationFactory;
import com.eaf.inf.batch.ulo.notification.dao.TemplateDAO;
import com.eaf.inf.batch.ulo.notification.model.DMNotificationDataM;
import com.eaf.inf.batch.ulo.notification.model.VCEmpInfoDataM;

public class DMEmaiTemplateNotReceive extends TemplateBuilderHelper{
	private static transient Logger logger = Logger.getLogger(DMEmaiTemplateNotReceive.class);
	@Override
	public TemplateVariableDataM getTemplateVariable() {
		TemplateVariableDataM templateVariable = new TemplateVariableDataM();
		TemplateBuilderRequest templateBuilderRequest = getTemplateBuilderRequest();
		try {
			ArrayList<DMNotificationDataM> dmNotifications= (ArrayList<DMNotificationDataM>)templateBuilderRequest.getRequestObject();
			if(!InfBatchUtil.empty(dmNotifications)){
				TemplateDAO  dao = NotificationFactory.getTemplateDAO();
				String userWebScan = dmNotifications.get(0).getUserWebScan();
				VCEmpInfoDataM vcEmpInfo  = NotificationFactory.getNotificationDAO().selectVCEmpInfo(userWebScan);
				String telephoneNo  = NotificationFactory.getNotificationDAO().getDMGeneralParam("WH_NOTI_DEPARTMENT_TEL_NO");
				String departmentTeam = NotificationFactory.getNotificationDAO().getDMGeneralParam("WH_NOTI_DEPARTMENT_TEAM");
				HashMap<String,Object> mapData = dao.getDMNotReceivePhysicalDocumentValues(dmNotifications);
				mapData.put(TemplateBuilderConstant.TemplateVariableName.BRANCH_NAME,Formatter.displayText(vcEmpInfo.getBranchName()));
				mapData.put(TemplateBuilderConstant.TemplateVariableName.DEPARTMENT_NAME, Formatter.displayText(vcEmpInfo.getDepartMentName()));
				mapData.put(TemplateBuilderConstant.TemplateVariableName.TIMES, String.valueOf(dmNotifications.get(0).getRoundOfNotification()+1));
				mapData.put(TemplateBuilderConstant.TemplateVariableName.TELEPHONE_NO, telephoneNo);
				mapData.put(TemplateBuilderConstant.TemplateVariableName.DEPARTMENT_TEAM, departmentTeam);
				templateVariable.setTemplateVariable(mapData);
			}
		} catch (Exception e) {
			logger.fatal("ERROR",e);
		}
		return templateVariable;
	}

}
