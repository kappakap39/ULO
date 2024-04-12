package com.eaf.orig.rest.controller;

import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.HashMap;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.StringEscapeUtils;
import org.apache.log4j.Logger;

import com.eaf.core.ulo.common.display.FormatUtil;
import com.eaf.core.ulo.common.display.HtmlUtil;
import com.eaf.core.ulo.common.engine.SQLQueryEngine;
import com.eaf.core.ulo.common.properties.SystemConfig;
import com.eaf.core.ulo.common.properties.SystemConstant;
import com.eaf.core.ulo.common.util.Util;
import com.eaf.orig.profile.model.UserDetailM;
import com.eaf.orig.shared.constant.OrigConstant;
import com.eaf.orig.ulo.app.dao.ORIGDAOFactory;
import com.eaf.orig.ulo.app.dao.OrigApplicationGroupDAO;
import com.eaf.orig.ulo.app.proxy.ORIGServiceProxy;
import com.eaf.orig.ulo.app.util.GenerateUnique;
import com.eaf.orig.ulo.model.app.ApplicationDataM;
import com.eaf.orig.ulo.model.app.ApplicationGroupDataM;
import com.eaf.orig.ulo.model.app.ApplicationImageDataM;
import com.eaf.orig.ulo.model.app.ApplicationImageSplitDataM;
import com.eaf.orig.ulo.model.app.CashTransferDataM;
import com.eaf.orig.ulo.model.app.LoanDataM;
import com.eaf.orig.ulo.model.app.PersonalInfoDataM;
import com.eaf.orig.ulo.model.app.PersonalRelationDataM;
import com.eaf.orig.ulo.model.app.ReasonDataM;
import com.eaf.orig.ulo.model.app.ReasonLogDataM;
import com.google.gson.Gson;
import com.orig.bpm.ulo.workflow.model.WorkflowRequestDataM;
import com.orig.bpm.ulo.workflow.model.WorkflowResponseDataM;
import com.orig.bpm.workflow.handle.WorkflowManager;

//@WebServlet(name="ULORest",urlPatterns={"/ia"},description="ULO Rest-API to start flow")
public class ULOFlowServlet extends HttpServlet{
	private static transient Logger logger = Logger.getLogger(ULOFlowServlet.class);
	private static final long serialVersionUID = 4770719360787843151L;	
	String COVER_PAGE_TYPE_VETO = SystemConstant.getConstant("COVER_PAGE_TYPE_VETO");
	String BPM_HOST = SystemConfig.getProperty("BPM_HOST");
	String BPM_PORT = SystemConfig.getProperty("BPM_PORT");
	String BPM_USER_ID = SystemConfig.getProperty("BPM_USER_ID");
	String BPM_PASSWORD = SystemConfig.getProperty("BPM_PASSWORD");	
	String PRODUCT_CRADIT_CARD = SystemConstant.getConstant("PRODUCT_CRADIT_CARD");
	String[] VETO_APPLICATION_DECISION = SystemConstant.getArrayConstant("VETO_APPLICATION_DECISION");	
	String APPLICATION_CARD_TYPE_BORROWER = SystemConstant.getConstant("APPLICATION_CARD_TYPE_BORROWER");
	String APPLICATION_CARD_TYPE_SUPPLEMENTARY = SystemConstant.getConstant("APPLICATION_CARD_TYPE_SUPPLEMENTARY");
	@Override
	protected void service(HttpServletRequest request, HttpServletResponse response)throws ServletException, IOException {
		try{
			RestResponse respObj = null;
			UserDetailM userM = (UserDetailM)request.getSession().getAttribute("ORIGUser");			
			String action = request.getParameter("action");
			logger.debug("action >> "+action);
			
			if("start".equalsIgnoreCase(action)){
				respObj = createApplication(userM,request);
			}else{
				respObj = createMessage("action parameter is not valid, action="+action);
			}	
			returnJson(respObj,response);
		}catch(Exception e){
			logger.fatal("ERROR ",e);
		}
	}	
	private RestResponse createMessage(String message){
		RestResponse respObj = new RestResponse();
		respObj.result = "Error";
		respObj.resultDesc = message;
		return respObj;
	}	
	private void returnJson(RestResponse respObj,HttpServletResponse response){
		try{
			if(null != respObj){
				PrintWriter out = response.getWriter();
				Gson gson = new Gson();
				String json = gson.toJson(respObj);
				json = StringEscapeUtils.unescapeJavaScript(json);
				out.println(json);
				out.flush();
			}
		}catch(Exception e){
			logger.fatal("ERROR ",e);
		}
	}
	private RestResponse createApplication(UserDetailM userM ,HttpServletRequest request)  throws ServletException, IOException{
		String DOC_SET_NO = request.getParameter("DOC_SET_NO");
		String COVERPAGE_TYPE = request.getParameter("COVERPAGE_TYPE");	
		logger.debug("BPM_HOST >> "+BPM_HOST);	
		logger.debug("BPM_PORT >> "+BPM_PORT);	
		logger.debug("BPM_USER_ID >> "+BPM_USER_ID);	
		logger.debug("BPM_PASSWORD >> "+BPM_PASSWORD);
		RestResponse respObj = new RestResponse();
		try{
			ApplicationGroupDataM applicationGroup = null;
			if(COVER_PAGE_TYPE_VETO.equals(COVERPAGE_TYPE)){
				logger.debug("createVetoApplication..");
				createVetoApplication(DOC_SET_NO, COVERPAGE_TYPE, applicationGroup, userM);
			}else{
				logger.debug("createApplication..");
				applicationGroup = new ApplicationGroupDataM();
				String SCAN_DATE = request.getParameter("SCAN_DATE");
				String TEMPLATE = request.getParameter("TEMPLATE");
				String CHANNEL = request.getParameter("CHANNEL");
				String BRANCH_NO = request.getParameter("BRANCH_NO");
				logger.debug("SCAN_DATE >> "+SCAN_DATE);
				logger.debug("TEMPLATE >> "+TEMPLATE);
				logger.debug("CHANNEL >> "+CHANNEL);
				logger.debug("BRANCH_NO >> "+BRANCH_NO);
				applicationGroup.setApplicationDate(FormatUtil.toDate(SCAN_DATE,HtmlUtil.TH));
				applicationGroup.setCoverpageType(COVERPAGE_TYPE);
				applicationGroup.setDocSetNo(DOC_SET_NO);	
				applicationGroup.setApplicationTemplate(TEMPLATE);
				applicationGroup.setApplyChannel(CHANNEL);
				applicationGroup.setBranchNo(BRANCH_NO);
				applicationGroup.setApplicationGroupNo(DOC_SET_NO);
				createApplication(DOC_SET_NO,COVERPAGE_TYPE,applicationGroup,userM);
			}
			respObj.appGroup = applicationGroup;
			respObj.result = "Success";
		}catch(Exception e){
			logger.fatal("ERROR ",e);
			respObj.result = "Error";
			respObj.resultDesc = e.getMessage();
		}	
		return respObj;
	}	
	private void createApplication(String DOC_SET_NO ,String COVERPAGE_TYPE,ApplicationGroupDataM applicationGroup,UserDetailM userM)throws Exception{
		ArrayList<ApplicationImageDataM> applicationImages = new ArrayList<ApplicationImageDataM>();
		ApplicationImageDataM applicationImage = new ApplicationImageDataM();
		applicationImage.setApplicationImageSplits(getApplicationImageSplits("CC_KEC_INCREASE"));
		applicationImages.add(applicationImage);
		applicationGroup.setApplicationImages(applicationImages);
		ORIGServiceProxy.getApplicationManager().createApplication(applicationGroup, userM);
		WorkflowRequestDataM workflowRequest = new WorkflowRequestDataM();
			workflowRequest.setApplicationGroup(applicationGroup);
			workflowRequest.setUniqueId(applicationGroup.getApplicationGroupId());
			workflowRequest.setUserId(userM.getUserName());
			WorkflowManager workflowManager = new WorkflowManager(BPM_HOST, BPM_PORT, BPM_USER_ID, BPM_PASSWORD);
		WorkflowResponseDataM workflowResponse = workflowManager.createWorkflowTask(workflowRequest);
		applicationGroup.setInstantId(workflowResponse.getPiid());
	}
	private void createVetoApplication(String DOC_SET_NO ,String COVERPAGE_TYPE,ApplicationGroupDataM applicationGroup,UserDetailM userM) throws Exception{
		SQLQueryEngine queryEngine = new SQLQueryEngine();
		HashMap<String, Object> hashApplicationGroup = queryEngine.LoadModule("SELECT * FROM ORIG_APPLICATION_GROUP WHERE DOC_SET_NO = ?",DOC_SET_NO);				
		String applicationGroupId = SQLQueryEngine.display(hashApplicationGroup, "APPLICATION_GROUP_ID");
		logger.debug("applicationGroupId >> "+applicationGroupId);
		if(!Util.empty(applicationGroupId)){
			OrigApplicationGroupDAO applicationGroupDAO = ORIGDAOFactory.getApplicationGroupDAO();
			applicationGroup = applicationGroupDAO.loadOrigApplicationGroupM(applicationGroupId);
			applicationGroup.setCoverpageType(COVERPAGE_TYPE);	
			ArrayList<String> products = applicationGroup.getProducts(SystemConstant.getArrayConstant("DISPLAY_PRODUCT"));
			if(!Util.empty(products)){
				for (String product : products) {
					logger.debug("Product >> "+product);
					if(PRODUCT_CRADIT_CARD.equals(product)){
						ArrayList<ApplicationDataM> borrowerApplications = new ArrayList<ApplicationDataM>(
									applicationGroup.filterApplicationDecision(product,VETO_APPLICATION_DECISION,APPLICATION_CARD_TYPE_BORROWER));								
						if(!Util.empty(borrowerApplications)){
							for (ApplicationDataM borrowerApplication : borrowerApplications) {										
								String refBorrowerApplicationRecordId = borrowerApplication.getApplicationRecordId();
								setModelApplication(refBorrowerApplicationRecordId,borrowerApplication,applicationGroup);										
								String borrowerApplicationRecordId = borrowerApplication.getApplicationRecordId();
								setModelPersonalRelation(refBorrowerApplicationRecordId,borrowerApplicationRecordId,applicationGroup);																				
								ArrayList<ApplicationDataM> supplemantaryApplications = new ArrayList<ApplicationDataM>(
											applicationGroup.filterLinkApplicationDecision(product,
													VETO_APPLICATION_DECISION,APPLICATION_CARD_TYPE_SUPPLEMENTARY,refBorrowerApplicationRecordId));
								if(!Util.empty(supplemantaryApplications)){
									for(ApplicationDataM supplemantaryApplication : supplemantaryApplications){		
										String refSupplemantaryApplicationRecordId = supplemantaryApplication.getApplicationRecordId();
										setModelApplication(refSupplemantaryApplicationRecordId,supplemantaryApplication,applicationGroup);												
										String supplemantaryApplicationRecordId = supplemantaryApplication.getApplicationRecordId();
										setModelPersonalRelation(refSupplemantaryApplicationRecordId,supplemantaryApplicationRecordId,applicationGroup);
										supplemantaryApplication.setMaincardRecordId(borrowerApplicationRecordId);
									}
								}	
							}
						}else{
							ArrayList<ApplicationDataM> supplemantaryApplications = new ArrayList<ApplicationDataM>(applicationGroup.filterApplicationDecision(product,VETO_APPLICATION_DECISION,APPLICATION_CARD_TYPE_SUPPLEMENTARY));
							if(!Util.empty(supplemantaryApplications)){
								for (ApplicationDataM supplementaryApplication : supplemantaryApplications) {											
									String refSuplementaryApplicationRecordId = supplementaryApplication.getApplicationRecordId();
									setModelApplication(refSuplementaryApplicationRecordId,supplementaryApplication,applicationGroup);											
									String supplementaryApplicationRecordId = supplementaryApplication.getApplicationRecordId();
									setModelPersonalRelation(refSuplementaryApplicationRecordId,supplementaryApplicationRecordId,applicationGroup);											
									supplementaryApplication.setMaincardRecordId(supplementaryApplicationRecordId);											 
								}
							}
						}
					}else{
						ArrayList<ApplicationDataM> applications = new ArrayList<ApplicationDataM>(applicationGroup.filterApplicationDecision(product,VETO_APPLICATION_DECISION));
						if(!Util.empty(applications)){
							for (ApplicationDataM application: applications) {
								String refApplicationRecordId = application.getApplicationRecordId();
								setModelApplication(refApplicationRecordId,application,applicationGroup);										
								String applicationRecordId = application.getApplicationRecordId();
								setModelPersonalRelation(refApplicationRecordId,applicationRecordId,applicationGroup);
							}
						}
					}
				}
			}					
			ORIGServiceProxy.getApplicationManager().createApplication(applicationGroup, userM);
			WorkflowRequestDataM workflowRequest = new WorkflowRequestDataM();
				workflowRequest.setApplicationGroup(applicationGroup);
				workflowRequest.setUniqueId(applicationGroup.getApplicationGroupId());
				workflowRequest.setUserId(userM.getUserName());
				WorkflowManager workflowManager = new WorkflowManager(BPM_HOST, BPM_PORT, BPM_USER_ID, BPM_PASSWORD);
			WorkflowResponseDataM workflowResponse = workflowManager.createWorkflowTask(workflowRequest);
			applicationGroup.setInstantId(workflowResponse.getPiid());
		}
	}
	private ArrayList<ApplicationImageSplitDataM> getApplicationImageSplits(String templateId){
		ArrayList<ApplicationImageSplitDataM> applicationImages = new ArrayList<ApplicationImageSplitDataM>();
//		String IMAGE_TEMPLATE_PATH = SystemConfig.getProperty("IMAGE_TEMPLATE_PATH");
//		logger.debug("IMAGE_TEMPLATE_PATH >> "+IMAGE_TEMPLATE_PATH);
//		String TEMPLATE_PATH = IMAGE_TEMPLATE_PATH+templateId+File.separator+"l";
//		logger.debug("TEMPLATE_PATH >> "+TEMPLATE_PATH);
//		File folder = new File(TEMPLATE_PATH);
//		File[] listOfFiles = folder.listFiles();
//		for(File file : listOfFiles){
//			String fileName = file.getName();
//			logger.debug("fileName >> "+fileName);
//			applicationImages.add(getApplicationImageSplit(fileName,templateId));
//		}
		return applicationImages;
	}
	private ApplicationImageSplitDataM getApplicationImageSplit(String fileName,String templateId){
		ApplicationImageSplitDataM applicationImageSplit = new ApplicationImageSplitDataM();
//		if(null != fileName){
//			applicationImageSplit.setFileName(fileName.replaceFirst("[.][^.]+$", ""));
//			applicationImageSplit.setFileType(getExtension(fileName).replace(".",""));
//		}
//		applicationImageSplit.setRealPath(templateId);
		return applicationImageSplit;
	}
	public static String getExtension(String filename) {
	    if (filename == null) {
	        return null;
	    }
	    int index = indexOfExtension(filename);
	    if (index == -1) {
	        return filename;
	    }else{
	        return filename.substring(index);
	    }
	}
	private static final char EXTENSION_SEPARATOR = '.';
	private static final char DIRECTORY_SEPARATOR = '/';

	public static int indexOfExtension(String filename) {
	    if (filename == null) {
	        return -1;
	    }
	    int extensionPos = filename.lastIndexOf(EXTENSION_SEPARATOR);
	    int lastDirSeparator = filename.lastIndexOf(DIRECTORY_SEPARATOR);
	    if(lastDirSeparator > extensionPos){	       
	        return -1;
	    }
	    return extensionPos;
	}
	public class RestResponse{
		String result;
		String resultDesc;
		ApplicationGroupDataM appGroup;		
	}
	private void setModelPersonalRelation(String refApplicationRecordId,String applicationRecordId,ApplicationGroupDataM applicationGroup) throws Exception{
		String APPLICATION_LEVEL = SystemConstant.getConstant("PERSONAL_RELATION_APPLICATION_LEVEL");
		ArrayList<PersonalRelationDataM> lastPersonalRelations = new ArrayList<>(applicationGroup.getPersonalRelation(refApplicationRecordId,APPLICATION_LEVEL));
		if(!Util.empty(lastPersonalRelations)){
			for (PersonalRelationDataM personalRelation: lastPersonalRelations) {
				String personalId = personalRelation.getPersonalId();
				PersonalInfoDataM personalInfo = applicationGroup.getPersonalInfoId(personalId);
				if(null != personalInfo){
					personalInfo.addPersonalRelation(applicationRecordId, personalRelation.getPersonalType(),personalRelation.getRelationLevel());
				}
			}
		}
	}
	private void setModelApplication(String refApplicationRecordId,ApplicationDataM application,ApplicationGroupDataM applicationGroup) throws Exception{
		String applicationRecordId = GenerateUnique.generate(OrigConstant.SeqNames.ORIG_APPLICATION_PK); 
		int lifeCycle = application.getLifeCycle();
		
		application.setApplicationRecordId(applicationRecordId);
		application.setRefApplicationRecordId(refApplicationRecordId);
		application.setLifeCycle(lifeCycle+1);
		
		application.setVerificationResult(null);
		if(!Util.empty(application.getBundleHL())){
			application.getBundleHL().init(applicationRecordId);
		}
		
		if(!Util.empty(application.getBundleKL())){
			application.getBundleKL().init(applicationRecordId);
		}
		if(!Util.empty(application.getBundleSME())){
			application.getBundleSME().init(applicationRecordId);
		}
		
		if(!Util.empty(application.getReasons())){
			for(ReasonDataM reason:application.getReasons()){
				reason.init(applicationRecordId);
			}
		} 
		if(!Util.empty(application.getReasonLogs())){
			for(ReasonLogDataM reasonLog:application.getReasonLogs()){
				String reasonLogId = GenerateUnique.generate(OrigConstant.SeqNames.ORIG_REASON_LOG_PK);
				reasonLog.init(applicationRecordId,reasonLogId);
			}
		} 		
		if(!Util.empty(application.getLoans())){
			for(LoanDataM loan:application.getLoans()){
				String loanId = GenerateUnique.generate(OrigConstant.SeqNames.ORIG_LOAN_PK);
				loan.init(applicationRecordId,loanId);
				if(!Util.empty(loan.getCard())){
					String cardId= GenerateUnique.generate(OrigConstant.SeqNames.ORIG_CARD_PK);
					loan.getCard().init(loanId, cardId);
				}					
				if(!Util.empty(loan.getCashTransfers())){
					for(CashTransferDataM cashTransfer :loan.getCashTransfers()){
						cashTransfer.init(loanId, cashTransfer.getCashTransferType());
					}						 
				}
//				if(!Util.empty(loan.getSpecialAdditionalServiceIDs())){
//					for(SpecialAdditionalServiceDataM specialAdditionalService :loan.getSpecialAdditionalServices()){
//						String serviceId= GenerateUnique.generate(OrigConstant.SeqNames.ORIG_ADDITIONAL_SERVICE_PK);
//						specialAdditionalService.init(loanId,serviceId);
//					}
//				}
//				if(!Util.empty(loan.getPaymentMethods())){
//					for(PaymentMethodDataM paymentMethod :loan.getPaymentMethods()){
//						String paymentMethodId= GenerateUnique.generate(OrigConstant.SeqNames.ORIG_PAYMENT_METHOD_PK);
//						paymentMethod.init(loanId,paymentMethodId);
//					}
//				}
			}
		}
		applicationGroup.addApplications(application);
	}
}
