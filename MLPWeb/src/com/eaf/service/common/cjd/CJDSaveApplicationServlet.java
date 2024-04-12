package com.eaf.service.common.cjd;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.annotation.WebServlet;
import javax.naming.Context;
import javax.naming.InitialContext;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.io.IOUtils;
import org.apache.log4j.Logger;

import com.eaf.core.ulo.common.performance.TraceController;
import com.eaf.core.ulo.common.postapproval.CardLinkAction;
import com.eaf.core.ulo.common.properties.CacheControl;
import com.eaf.core.ulo.common.properties.SystemConstant;
import com.eaf.j2ee.pattern.control.ErrorController;
import com.eaf.orig.shared.dao.OrigObjectDAO;
import com.eaf.orig.ulo.app.factory.ModuleFactory;
import com.eaf.orig.ulo.app.key.dao.UniqueIDGeneratorDAO;
import com.eaf.orig.ulo.app.postapproval.process.NewCardProcess;
import com.eaf.orig.ulo.constant.MConstant;
import com.eaf.orig.ulo.control.util.ApplicationUtil;
import com.eaf.orig.ulo.model.app.ApplicationDataM;
import com.eaf.orig.ulo.model.app.ApplicationGroupDataM;
import com.eaf.orig.ulo.model.app.CardDataM;
import com.eaf.orig.ulo.model.app.CardlinkCustomerDataM;
import com.eaf.orig.ulo.model.app.PersonalInfoDataM;
import com.eaf.orig.ulo.service.process.response.model.ProcessResponse;
import com.eaf.service.common.mlp.bean.MLPServiceManagerBean;
import com.eaf.service.rest.model.ServiceResponse;
import com.google.gson.Gson;

@WebServlet("/CJDSaveApplication")
public class CJDSaveApplicationServlet extends HttpServlet 
{
	private Logger logger = Logger.getLogger(CJDSaveApplicationServlet.class);

	@Override
    public void init() throws ServletException 
    {}
	
	@Override
	protected void service(HttpServletRequest req, HttpServletResponse resp)throws ServletException, IOException 
	{
		String SUCCESS_CODE = SystemConstant.getConstant("SUCCESS_CODE");
		String CACHE_ORGANIZATION = SystemConstant.getConstant("CACHE_ORGANIZATION");
		String CACHE_BUSINESS_CLASS = SystemConstant.getConstant("CACHE_BUSINESS_CLASS");
		String DECISION_FINAL_DECISION_APPROVE = SystemConstant.getConstant("DECISION_FINAL_DECISION_APPROVE");	
		String JOBSTATE_APPROVED = SystemConstant.getConstant("JOBSTATE_APPROVED");
		String APPLICATION_STATIC_APPROVED = SystemConstant.getConstant("APPLICATION_STATIC_APPROVED");
		String CHANNEL_BRANCH = SystemConstant.getConstant("CHANNEL_BRANCH");
		String PTT_CARD_TYPE = SystemConstant.getConstant("PTT_CARD_TYPE");
		String PTT_PARAM_TYPE = SystemConstant.getConstant("PTT_PARAM_TYPE");
		
		ProcessResponse processResponse = new ProcessResponse();
		processResponse.setStatusCode(ServiceResponse.Status.SUCCESS);
		Gson gson = new Gson();
		try
		{
			String reqString = IOUtils.toString(req.getReader());	
			ApplicationGroupDataM uloApplicationGroup = gson.fromJson(reqString, ApplicationGroupDataM.class);
			
			String transactionId = uloApplicationGroup.getTransactionId();
			TraceController trace = new TraceController(this.getClass().getName(),transactionId);
			logger.debug("CJDProcessActionInitial for " + uloApplicationGroup.getApplicationGroupNo());
			
			trace.create("CJDProcessActionInitial");
			
			String appGroupNo = uloApplicationGroup.getApplicationGroupNo();
			logger.debug("appGroupNo = " + appGroupNo);
			
			UniqueIDGeneratorDAO uniqueIDDAO = ModuleFactory.getUniqueCardGeneratorDAO(OrigObjectDAO.EXECUTION_MANUAL);
	    	for(ApplicationDataM application : uloApplicationGroup.getApplications())
	    	{
	    		//Set Final Decision to Approve
	    		application.setFinalAppDecision(DECISION_FINAL_DECISION_APPROVE);
	    		
	    		String applicationRecordId = application.getApplicationRecordId();
	    		CardDataM cardM = application.getCard();
	    		
	    		//Generate Card No
	    		if(cardM != null)
	    		{
	    			NewCardProcess ncp = new NewCardProcess();
	    			ProcessResponse statusCode = ncp.execute(uloApplicationGroup, application);
	    			if(!SUCCESS_CODE.equals(statusCode.getStatusCode()))
	    			{
	    				if(null != statusCode.getErrorData())
	    				{
							throw new Exception(statusCode.getErrorData().getErrorInformation());
						}
	    			}
	    		}
	    		
	    		//Add membershipNo for PTT card
	    		if(cardM != null && PTT_CARD_TYPE.equals(cardM.getCardType()))
	    		{
	    			String applicationType = uloApplicationGroup.getApplicationType();
	    			if(ApplicationUtil.isGenerateMemberShipNo(applicationType, uloApplicationGroup.getApplicationTemplate(), cardM.getApplicationType())){
	    				String membershipNo = uniqueIDDAO.getGenerateRunningNoStack(cardM.getCardType(),PTT_PARAM_TYPE);
	    				logger.debug("membershipNo : "+membershipNo);
	    				cardM.setMembershipNo(membershipNo);
	    			}
	    		}
	    		
				PersonalInfoDataM personalInfo = uloApplicationGroup.getPersonalInfoByRelation(applicationRecordId);
				
				if(null != personalInfo && null != cardM){
					CardlinkCustomerDataM cardLink = ApplicationUtil.getCardlinkCustomer(uloApplicationGroup, applicationRecordId);
					if(null == cardLink){
						String businessClassId = application.getBusinessClassId();
						logger.info("businessClassId : "+businessClassId);
						String orgId =  CacheControl.getName(CACHE_BUSINESS_CLASS,"BUS_CLASS_ID",businessClassId,"ORG_ID");
						String orgNo = CacheControl.getName(CACHE_ORGANIZATION,"ORG_ID",orgId,"ORG_NO");
						logger.debug("orgId : "+orgId);
						logger.debug("orgNo : "+orgNo);
						cardLink = new CardlinkCustomerDataM();
						cardLink.setPersonalId(personalInfo.getPersonalId());
						cardLink.setOrgId(orgNo);
						String cardlinkCustNo = uniqueIDDAO.getCardLinkNo(cardM);
						logger.debug("cardlinkCustNo : "+cardlinkCustNo);
						cardLink.setCardlinkCustNo(cardlinkCustNo);
						cardLink.setNewCardlinkCustFlag(MConstant.FLAG.YES);				
						personalInfo.addCardLinkCustomer(cardLink);
					}
				}
	    	}
	    	
	    	//OLD KBMF Cardlink Action
	    	ApplicationUtil.defaultCardLinkCustId(uloApplicationGroup);
	    	CardLinkAction processCardLink = new CardLinkAction();
			processCardLink.processCardlinkAction(uloApplicationGroup);
			
			uloApplicationGroup.setJobState(JOBSTATE_APPROVED);
			uloApplicationGroup.setApplicationStatus(APPLICATION_STATIC_APPROVED);
			uloApplicationGroup.setApplyChannel(CHANNEL_BRANCH);
			uloApplicationGroup.setSource("CJD");
			
			//Save uloApplicationGroup to MLP_APP database
			Context ctx = new InitialContext();
			MLPServiceManagerBean mlpSMB = (MLPServiceManagerBean) ctx.lookup("ejblocal:com.eaf.service.common.mlp.bean.MLPServiceManagerBean");
			mlpSMB.saveApplication(uloApplicationGroup, "SYSTEM");
		    
			logger.debug("CJD Initial Process Done.");
			
			trace.end("CJDProcessActionInitial");
			trace.trace();
		}
		catch(Exception e)
		{
			logger.fatal("ERROR",e);
			processResponse.setStatusCode(ServiceResponse.Status.SYSTEM_EXCEPTION);
			processResponse.setErrorData(ErrorController.error(e));
		}
		
		resp.setCharacterEncoding("UTF-8");
		resp.setContentType("text/plain");
		PrintWriter pw = resp.getWriter();
		pw.print(gson.toJson(processResponse));
		pw.flush();
	}
}
