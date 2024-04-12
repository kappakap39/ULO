package com.eaf.inf.batch.ulo.notification.template.process;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.apache.log4j.Logger;

import com.eaf.core.ulo.common.cache.InfBatchProperty;
import com.eaf.core.ulo.common.display.FormatUtil;
import com.eaf.core.ulo.common.display.Formatter;
import com.eaf.core.ulo.common.util.InfBatchUtil;
import com.eaf.core.ulo.common.util.PathUtil;
import com.eaf.core.ulo.common.util.Util;
import com.eaf.core.ulo.service.template.cont.TemplateBuilderConstant;
import com.eaf.core.ulo.service.template.inf.TemplateBuilderHelper;
import com.eaf.core.ulo.service.template.model.TemplateBuilderRequest;
import com.eaf.core.ulo.service.template.model.TemplateVariableDataM;
import com.eaf.inf.batch.ulo.notification.condition.NotificationEODDataM;
import com.eaf.inf.batch.ulo.notification.dao.NotificationFactory;
import com.eaf.inf.batch.ulo.notification.dao.TemplateDAO;
import com.eaf.inf.batch.ulo.notification.model.EmailBranchSummaryDataM;
import com.eaf.notify.task.NotifyTask;

public class EmailTemplateBranchSummary extends TemplateBuilderHelper{
	private static transient Logger logger = Logger.getLogger(EmailTemplateBranchSummary.class);
	private static String NOTIFICATION_EMAIL_SUMMARY_OUTPUT_NAME = InfBatchProperty.getInfBatchConfig("NOTIFICATION_EMAIL_SUMMARY_OUTPUT_NAME");
	private  String APPLICATION_STATUS_REJECT=InfBatchProperty.getInfBatchConfig("NOTIFICATION_APPLICATION_STATUS_REJECT");
	@Override
	public TemplateVariableDataM getTemplateVariable() throws Exception {
		TemplateVariableDataM templateVariable = new TemplateVariableDataM();
		TemplateBuilderRequest templateBuilderRequest = getTemplateBuilderRequest();
		List<NotifyTask> uniqueIds = filterTransaction(templateBuilderRequest.getRequestObject());
		logger.debug("uniqueIds : "+uniqueIds);
		if(!Util.empty(uniqueIds)){
//			String branchName = new ArrayList<String>(branchGroupHash.keySet()).get(0) ; 
//			logger.debug("branchName : "+branchName);
//			ArrayList<NotifyTask> uniqueIds = branchGroupHash.get(branchName);
//			logger.debug("uniqueIds : "+uniqueIds);
			TemplateDAO  tempalteDAO = NotificationFactory.getTemplateDAO();
			ArrayList<EmailBranchSummaryDataM> emailBranchSummarys = tempalteDAO.getBranchSummaryData(findApplicationGroupIds(uniqueIds));
			logger.debug("emailBranchSummarys : "+emailBranchSummarys);
			HashMap<String,Object> templateVariables = mapTemplateVariables(emailBranchSummarys);
			logger.debug("templateVariables : "+templateVariables);
			if(!InfBatchUtil.empty(templateVariables)){
				templateVariable.setTemplateVariable(templateVariables);
			}
			String excelPath = createExcelSummary(emailBranchSummarys);
			logger.debug("excelPath : "+excelPath);
			ArrayList<String> attachments = new ArrayList<String>();
			attachments.add(excelPath);
			templateVariable.setAttachments(attachments);
			templateVariable.setUniqueIds(uniqueIds);
		}
		return templateVariable;
	}
	private ArrayList<String> findApplicationGroupIds(List<NotifyTask> uniqueIds){
		ArrayList<String> applicationGroupIds = new ArrayList<String>();
		if(null!=uniqueIds){
			for(NotifyTask notifyTask : uniqueIds) {
				applicationGroupIds.add(notifyTask.getId());
			}
		}
		return applicationGroupIds;
	}
	@SuppressWarnings("unchecked")
	private List<NotifyTask> filterTransaction(Object notificationObjects){
		String NOTIFICATION_EOD_DAY_NOTIFICATION_TYPE_BRANCH_SUMMARY = InfBatchProperty.getInfBatchConfig("NOTIFICATION_EOD_DAY_NOTIFICATION_TYPE_BRANCH_SUMMARY");
		List<NotifyTask> uniqueIds = new ArrayList<NotifyTask>();
		if(notificationObjects instanceof ArrayList<?>){
			ArrayList<?> notificationObject = (ArrayList<?>)notificationObjects;
			if(!Util.empty(notificationObject) && notificationObject.get(0) instanceof NotificationEODDataM){
				ArrayList<String> applicationGroupIds = new ArrayList<String>();
				ArrayList<NotificationEODDataM> notificationEods = (ArrayList<NotificationEODDataM>)notificationObjects;
				for(NotificationEODDataM notificationEod :notificationEods){
					logger.debug("notificationEod : "+notificationEod);
					if(!applicationGroupIds.contains(notificationEod.getApplicationGroupId()) 
							&& NOTIFICATION_EOD_DAY_NOTIFICATION_TYPE_BRANCH_SUMMARY.equals(notificationEod.getNotificationType())){
						applicationGroupIds.add(notificationEod.getApplicationGroupId());
						uniqueIds.add(new NotifyTask(notificationEod.getApplicationGroupId(), notificationEod.getLifeCycle()));
					}
				}
//				String branchCode = notificationEods.get(0).getBranchCode();
//				if(InfBatchUtil.empty(branchCode)){
//					branchCode = "Branch";
//				}
//				branchGroup.put(branchCode,uniqueIds);
			}
//			else if(!Util.empty(notificationObject) && notificationObject.get(0) instanceof DMNotificationDataM){
//				ArrayList<DMNotificationDataM> dmNotifications= (ArrayList<DMNotificationDataM>)notificationObjects;
//				ArrayList<String> dmApplicationGroupIds = new ArrayList<String>();
//				ArrayList<NotifyTask> uniqueIds = new ArrayList<NotifyTask>();
//				for(DMNotificationDataM dmNotification :dmNotifications){
//					if(!dmApplicationGroupIds.contains(dmNotification.getApplicationGroupId())){
//						dmApplicationGroupIds.add(dmNotification.getApplicationGroupId());
//						uniqueIds.add(new NotifyTask(dmNotification.getApplicationGroupId(), dmNotification.getLifeCycle()));
//					}	
//				}
//				String branchName = dmNotifications.get(0).getBarnchName();
//				if(InfBatchUtil.empty(branchName)){
//					branchName ="";
//				}
//				branchGroup.put(branchName,uniqueIds);
//			}
		}
		return uniqueIds;
	}
	
	private HashMap<String, Object> mapTemplateVariables(ArrayList<EmailBranchSummaryDataM> emailBranchSummarys){
		HashMap<String,Object>  branchSummaryVariables = null;
		if(!Util.empty(emailBranchSummarys)){
			String ARROVED_LIST ="";
			String REJECTED_LIST="";
			for(EmailBranchSummaryDataM summaryBranch : emailBranchSummarys){
				if(APPLICATION_STATUS_REJECT.equals(summaryBranch.getFinalDecision()) && !ARROVED_LIST.contains(summaryBranch.getApplicationNo())){
					ARROVED_LIST =ARROVED_LIST+"  <p>"+summaryBranch.getApplicationNo()+"</p>";
				}else if(APPLICATION_STATUS_REJECT.equals(summaryBranch.getFinalDecision()) && !REJECTED_LIST.contains(summaryBranch.getApplicationNo())){
					REJECTED_LIST=REJECTED_LIST+"  <p>"+summaryBranch.getApplicationNo()+"</p>";
				}
			}
			branchSummaryVariables = new HashMap<String, Object>();
//			branchSummaryVariables.put(TemplateBuilderConstant.TemplateVariableName.BRANCH_NAME, branchName);
			branchSummaryVariables.put(TemplateBuilderConstant.TemplateVariableName.APPROVE,ARROVED_LIST);
			branchSummaryVariables.put(TemplateBuilderConstant.TemplateVariableName.REJECT,REJECTED_LIST);
			branchSummaryVariables.put(TemplateBuilderConstant.TemplateVariableName.PO_TELEPHONE_NO,emailBranchSummarys.get(0).getPoPhoneNo());
			branchSummaryVariables.put(TemplateBuilderConstant.TemplateVariableName.PO_EMAIL,emailBranchSummarys.get(0).getPoEmail());
		}
		return branchSummaryVariables;
	}
 
	public static String createExcelSummary(ArrayList<EmailBranchSummaryDataM> emailSummarys){
		String attachPath = "";
		try{
			if(emailSummarys != null){
				String YYYYMMDD = Formatter.display(InfBatchProperty.getDate(), Formatter.EN, Formatter.Format.YYYYMMDD);
				String OUTPUT_PATH=PathUtil.getPath("NOTIFICATION_EMAIL_SUMMARY_OUTPUT_PATH");
				OUTPUT_PATH +=File.separator+YYYYMMDD+File.separator+FormatUtil.token(5)+File.separator;
				String  OUTPUT_FILE_NAME = NOTIFICATION_EMAIL_SUMMARY_OUTPUT_NAME.replace("YYYYMMDD",YYYYMMDD);
//						OUTPUT_FILE_NAME = OUTPUT_FILE_NAME.replace("{BRANCH_NAME}",branchCode);
				attachPath = OUTPUT_PATH+OUTPUT_FILE_NAME;
				File outputPath = new File(OUTPUT_PATH);
				if (!outputPath.exists()) {
					outputPath.mkdirs();
				}
				ExcelBranchSummary xlsMapper = new ExcelBranchSummary("",attachPath,emailSummarys);
				xlsMapper.export();
			}
		}catch(Exception e){
			logger.fatal("ERROR ",e);
		}
		logger.debug("attachPath : "+attachPath);
		return attachPath;
	}
}
