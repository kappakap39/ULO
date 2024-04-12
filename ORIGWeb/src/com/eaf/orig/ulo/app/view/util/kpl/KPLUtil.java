package com.eaf.orig.ulo.app.view.util.kpl;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.List;
import java.util.Locale;
import java.util.ResourceBundle;

import org.apache.log4j.Logger;

import com.ava.flp.eapp.iib.model.DocumentSuggestions;
import com.eaf.core.ulo.common.date.ApplicationDate;
import com.eaf.core.ulo.common.display.FormatUtil;
import com.eaf.core.ulo.common.message.ResourceBundleUtil;
import com.eaf.core.ulo.common.properties.CacheControl;
import com.eaf.core.ulo.common.properties.SystemConfig;
import com.eaf.core.ulo.common.properties.SystemConstant;
import com.eaf.core.ulo.common.util.Util;
import com.eaf.orig.shared.constant.OrigConstant;
import com.eaf.orig.shared.dao.OrigObjectDAO;
import com.eaf.orig.shared.service.OrigServiceLocator;
import com.eaf.orig.ulo.app.util.GenerateUnique;
import com.eaf.orig.ulo.app.view.form.verification.VerifyUtil;
import com.eaf.orig.ulo.app.view.util.pa.PAUtil;
import com.eaf.orig.ulo.constant.MConstant;
import com.eaf.orig.ulo.control.util.PersonalInfoUtil;
import com.eaf.orig.ulo.model.app.AddressDataM;
import com.eaf.orig.ulo.model.app.ApplicationDataM;
import com.eaf.orig.ulo.model.app.ApplicationGroupDataM;
import com.eaf.orig.ulo.model.app.DocumentCheckListDataM;
import com.eaf.orig.ulo.model.app.DocumentCheckListReasonDataM;
import com.eaf.orig.ulo.model.app.IncomeCategoryDataM;
import com.eaf.orig.ulo.model.app.IncomeInfoDataM;
import com.eaf.orig.ulo.model.app.LoanDataM;
import com.eaf.orig.ulo.model.app.PaymentMethodDataM;
import com.eaf.orig.ulo.model.app.PayrollDataM;
import com.eaf.orig.ulo.model.app.PersonalInfoDataM;
import com.eaf.orig.ulo.model.app.RequiredDocDataM;
import com.eaf.orig.ulo.model.app.RequiredDocDetailDataM;
import com.eaf.orig.ulo.model.app.VerificationResultDataM;
import com.eaf.orig.ulo.model.ncb.NcbInfoDataM;
import com.eaf.service.common.iib.mapper.util.ProcessUtil;
import com.eaf.service.common.util.ServiceUtil;

public class KPLUtil 
{
	public static String[] KPL_APPLICATION_TEMPLATE = SystemConstant.getArrayConstant("KPL_APPLICATION_TEMPLATE");
	static ArrayList<String> kplApplicationTemplate = new ArrayList<String>(Arrays.asList(KPL_APPLICATION_TEMPLATE));
	public static String[] KPL_PRODUCT  = SystemConstant.getArrayConstant("KPL_PRODUCT");
	static ArrayList<String> kplProduct = new ArrayList<String>(Arrays.asList(KPL_PRODUCT));

	public static String KPL_SINGLE_TEMPLATE=SystemConstant.getConstant("KPL_SINGLE");
	public static String KPL_TOPUP_TEMPLATE=SystemConstant.getConstant("KPL_TOPUP_TEMPLATE");
	public static String KPL_MARKET_TEMPLATE=SystemConstant.getConstant("KPL_MARKET_TEMPLATE");
	public static String INCREASE_APPLICATION_FORM = SystemConstant.getConstant("INCREASE_APPLICATION_FORM");
	public static String NORMAL_APPLICATION_FORM = SystemConstant.getConstant("NORMAL_APPLICATION_FORM");
	private static String PRODUCT_K_PERSONAL_LOAN = SystemConstant.getConstant("PRODUCT_K_PERSONAL_LOAN");
	private static String APPLICATION_STATUS_APPROVE = SystemConstant.getConstant("APPLICATION_STATUS_APPROVE");
	private static String RECOMMEND_DECISION_APPROVED = SystemConstant.getConstant("RECOMMEND_DECISION_APPROVED");
	private static String SAVINGPLUS_DOC_CODE = SystemConstant.getConstant("SAVINGPLUS_DOC_CODE");
	
	private static transient Logger logger = Logger.getLogger(KPLUtil.class);
	
	public static boolean isKPL(String templateId)
	{
		if(kplApplicationTemplate.contains(templateId))
		{	
			return true;
		}
		return false;
	}
	
	public static boolean isKPL_TOPUP(ApplicationGroupDataM applicationGroup) 
	{
		String templateId = "";
		if(applicationGroup != null && applicationGroup.getApplicationTemplate() != null)
		{
			templateId = applicationGroup.getApplicationTemplate();
			if(KPL_TOPUP_TEMPLATE.equals(templateId))
			{	
				return true;
			}
		}
		return false;
	}
	
	public static boolean isKPL_MARKET(ApplicationGroupDataM applicationGroup) 
	{
		String templateId = "";
		if(applicationGroup != null && applicationGroup.getApplicationTemplate() != null)
		{
			templateId = applicationGroup.getApplicationTemplate();
			if(KPL_MARKET_TEMPLATE.equals(templateId))
			{	
				return true;
			}
		}
		return false;
	}

	public static boolean isKPL(ApplicationGroupDataM applicationGroup) 
	{
		return (isKPLTemplate(applicationGroup) || hasKPLProduct(applicationGroup));
	}
	
	public static boolean isKPLTemplate(ApplicationGroupDataM applicationGroup) 
	{
		String templateId = "";
		if(applicationGroup != null && applicationGroup.getApplicationTemplate() != null)
		{
			templateId = applicationGroup.getApplicationTemplate();
			if(kplApplicationTemplate.contains(templateId))
			{	
				return true;
			}
		}
		return false;
	}
	
	public static boolean hasKPLProduct(ApplicationGroupDataM applicationGroup)
	{
		ArrayList<ApplicationDataM> applications = applicationGroup.getApplicationsProduct(PRODUCT_K_PERSONAL_LOAN);
		if(!Util.empty(applications))
		{
			for(ApplicationDataM applicationItem : applications)
			{
				return true;
			}
		}
		return false;
	}
	
	public static String getKPLFlag(ApplicationGroupDataM applicationGroup)
	{
		if(isKPL(applicationGroup))
		{return "_KPL";}
		else
		{return "";}
	}
	
	public static String getTopUpFlag(ApplicationGroupDataM applicationGroup)
	{
		if(isKPL_TOPUP(applicationGroup))
		{return "_TOPUP";}
		else
		{return "";}
	}
	
	public static String checkKPLOrg(String product)
	{
		System.out.println("kplProduct : " + kplProduct);
		if(kplProduct.contains(product))
		{return "KPL";}
		else
		{return product;}
	}
	
	public static String getKPLTopupForm(String appTemplate, String formId)
	{
		if(KPLUtil.isKPL(appTemplate) && formId.equals(INCREASE_APPLICATION_FORM))
		{
			return NORMAL_APPLICATION_FORM;
		}
		else
		{return formId;}
	}
	
	public static LoanDataM getKPLLoanDataM(ApplicationGroupDataM applicationGroup)
	{
		ApplicationDataM applicationM = applicationGroup.filterApplicationProductLifeCycle(PRODUCT_K_PERSONAL_LOAN);
		if (Util.empty(applicationM)) 
		{
			applicationM = new ApplicationDataM();
		}
		ArrayList<LoanDataM>  loans = applicationM.getLoans();
		if(Util.empty(loans))
		{
		
			loans= new ArrayList<LoanDataM>();
		}
		LoanDataM loan = new LoanDataM();
		for(LoanDataM loanMTmp:loans)
	    {
			loan = loanMTmp;
		}
		return loan;
	}
	
	
	
	public static String getSavingPlusFlag(ApplicationGroupDataM applicationGroup)
	{
		String savingPlus = "";
		
		try
		{
			ApplicationDataM application = KPLUtil.getKPLApplication(applicationGroup);
			savingPlus = application.getSavingPlusFlag();
		}
		catch(Exception e){}
	
		return savingPlus;
	}
	
	public static String isKFC(ApplicationGroupDataM applicationGroup)
	{
		//If found Franchise document return "Y"
		String FRANCHISE_DOC_CODE = SystemConstant.getConstant("FRANCHISE_DOC_CODE");
		if(applicationGroup != null && applicationGroup.getDocumentCheckLists() != null)
		{
			List<String> imageSplitsDocType = applicationGroup.getImageSplitsDocType();

			if (Util.empty(imageSplitsDocType)) {
				imageSplitsDocType = new ArrayList<String>();
			}
			
			if (imageSplitsDocType.contains(FRANCHISE_DOC_CODE)) {
				return "Y";
			}
		}
		return "";
	}
	
	public static ApplicationDataM getKPLApplication(ApplicationGroupDataM applicationGroup)
	{
		ArrayList<ApplicationDataM> applications = applicationGroup.filterApplicationLifeCycle(PRODUCT_K_PERSONAL_LOAN);
		
		if(!Util.empty(applications))
		{
			for(ApplicationDataM applicationItem : applications)
			{
				return applicationItem;
			}
		}
		
		return new ApplicationDataM();
	}
	public static boolean haveKbankSavingDoc(ApplicationGroupDataM applicationGroup)
	{
		//If found Franchise document return "Y"
		if(applicationGroup != null && applicationGroup.getDocumentCheckLists() != null)
		{
			List<String> imageSplitsDocType = applicationGroup.getImageSplitsDocType();

			if (Util.empty(imageSplitsDocType)) {
				imageSplitsDocType = new ArrayList<String>();
			}
					
			if (imageSplitsDocType.contains(SAVINGPLUS_DOC_CODE)) 
			{
				return true;
			}
		}
		return false;
	}
	
	public static boolean isDSA(ApplicationGroupDataM applicationGroup)
	{
		String CHANNEL_DSA = SystemConstant.getConstant("CHANNEL_DSA");
		Boolean isDSA = false;
		if(applicationGroup != null && !Util.empty(applicationGroup.getApplyChannel()))
		{
			isDSA = applicationGroup.getApplyChannel().equals(CHANNEL_DSA);
		}
		return isDSA;
	}
	
	public static String getPayrollAccNo(ApplicationGroupDataM applicationGroup)
	{
		PersonalInfoDataM personalInfo = PersonalInfoUtil.getApplicationTypePersonalInfo(applicationGroup);
		
		if(personalInfo != null)
		{
			if(!Util.empty(personalInfo.getPayrollAccountNo()))
			{
				return personalInfo.getPayrollAccountNo();
			}
		}
		
		return null;
	}
	
	public static boolean isFoundPayroll(ApplicationGroupDataM applicationGroup)
	{
		PersonalInfoDataM personalInfo = PersonalInfoUtil.getApplicationTypePersonalInfo(applicationGroup);
		
		if(personalInfo != null)
		{
			if(!Util.empty(personalInfo.getPayrollAccountNo()))
			{return true;}
		}
		
		return false;
	}
	
	public decisionservice_iib.CardDataM KPLCard()
	{
		decisionservice_iib.CardDataM iibCard = new decisionservice_iib.CardDataM();	
		return iibCard;		
	}
	
	public static String getAccountSetupClaimStatus(String appGroupNo)
	{
		return PAUtil.getAccountSetupClaimStatus(appGroupNo);
	}
	
	public static void createLoanSetupClaim(ApplicationGroupDataM applicationGroup)
	{
		PAUtil.createLoanSetupClaim(applicationGroup);
	}
	
	public static boolean followSavingPlusDoc(ApplicationGroupDataM applicationGroup)
	{
		ApplicationDataM appKPL = KPLUtil.getKPLApplication(applicationGroup);
		if(KPLUtil.isKPL(applicationGroup) 
			&& KPLUtil.isSavingPlus(appKPL.getSavingPlusFlag())
		    && applicationGroup.getApplicationStatus().equals(APPLICATION_STATUS_APPROVE)
		    && appKPL.getFinalAppDecision().equals(RECOMMEND_DECISION_APPROVED)
		    && KPLUtil.haveKbankSavingDoc(applicationGroup)
		  )
		{return true;}
		else 
		{return false;}
	}
	
	public static boolean isSavingPlus(String savingPlusFlag)
	{
		if(savingPlusFlag != null && savingPlusFlag.equals(MConstant.FLAG.ACTIVE))
		{return true;}
		return false;
	}
	
	public static boolean isSavingPlus(ApplicationGroupDataM applicationGroup)
	{
		try
		{
			ApplicationDataM application = KPLUtil.getKPLApplication(applicationGroup);
			return isSavingPlus(application.getSavingPlusFlag());
		}
		catch(Exception e){}
	
		return false;
	}
	
	public static boolean isDiffReq(ApplicationDataM uloApplication)
	{
		if(uloApplication != null)
		{return MConstant.FLAG.YES.equals(uloApplication.getDiffRequestFlag());}
		else{return false;}
	}
	
	public static boolean isDiffReq(ApplicationGroupDataM appGroup)
	{
		ApplicationDataM uloApplication = KPLUtil.getKPLApplication(appGroup);
		return isDiffReq(uloApplication);
	}
	
	public static void addFollowSavingPlusDocReason(ApplicationGroupDataM appGroup)
	{
		String SAVING_PLUS_FOLLOW_REASON = SystemConfig.getGeneralParam("SAVING_PLUS_FOLLOW_REASON");
		String IM_PERSONAL_TYPE_APPLICANT = SystemConstant.getConstant("IM_PERSONAL_TYPE_APPLICANT");
		String DOC_FOLLOW_UP_REASON_CODE_NOT_RECEIVE = SystemConstant.getConstant("DOC_FOLLOW_UP_REASON_CODE_NOT_RECEIVE");
		String INCOME_DOC  = SystemConstant.getConstant("INCOME_DOC");
		DocumentCheckListDataM documentCheckList = appGroup.getDocumentCheckList(IM_PERSONAL_TYPE_APPLICANT, SAVINGPLUS_DOC_CODE);
		if(null == documentCheckList)
		{
			//create docCheckList + docReason
			documentCheckList = new DocumentCheckListDataM();
			documentCheckList.setApplicantTypeIM(IM_PERSONAL_TYPE_APPLICANT);
			documentCheckList.setDocumentCode(SAVINGPLUS_DOC_CODE);
			documentCheckList.setDocTypeId(INCOME_DOC);
			String docCheckListId = GenerateUnique.generate(OrigConstant.SeqNames.ORIG_DOC_CHECK_LIST_PK);
			documentCheckList.setDocCheckListId(docCheckListId);
			appGroup.addDocumentCheckList(documentCheckList);
		
			ArrayList<DocumentCheckListReasonDataM> documentReasons = new ArrayList<DocumentCheckListReasonDataM>();
			DocumentCheckListReasonDataM reasonM = new DocumentCheckListReasonDataM();
			reasonM.setDocReason(DOC_FOLLOW_UP_REASON_CODE_NOT_RECEIVE);
			documentReasons.add(reasonM);
			documentCheckList.setDocumentCheckListReasons(documentReasons);
		}
		if(!Util.empty(documentCheckList))
		{
			documentCheckList.setRemark(SAVING_PLUS_FOLLOW_REASON);
		}
	}
	
	public static boolean isPaymentAccountNoBlank(ApplicationGroupDataM appGroup, LoanDataM loan)
	{
		boolean isSavingAccountBlank = false;
		PaymentMethodDataM paymentMethod = appGroup.getPaymentMethodById(loan.getPaymentMethodId());
		if(paymentMethod == null || (paymentMethod != null && Util.empty(paymentMethod.getAccountNo())))
		{
			isSavingAccountBlank = true;
		}
		
		return isSavingAccountBlank;
	}
	
	public static boolean isSavingPlusNoDoc(ApplicationGroupDataM appGroup)
	{
		return (isSavingPlus(appGroup) && !haveKbankSavingDoc(appGroup));
	}
	
	public static boolean isSavingPlusDoc(String docCode)
	{
		return SAVINGPLUS_DOC_CODE.equals(docCode);
	}
	
	public static boolean de2SubmitDateInOneDay(Date de2SubmitDate)
	{
		boolean result = false;
		if(de2SubmitDate != null)
		{
			/*Date currentDate = new Date(System.currentTimeMillis());
			long diff = currentDate.getTime() - de2SubmitDate.getTime();
			long diffHours = diff / (60 * 60 * 1000);
			logger.debug("diffHours : " + diffHours);
			if(diffHours > 24)
			{result = true;}*/
			
			//DE2 Submit Date
			Calendar cal = new GregorianCalendar();
		    cal.setTime(de2SubmitDate);
		    
		    //Current Date
		    Calendar calCur = Calendar.getInstance();			   
		    
			//Same day in same year -> return true
		    if((cal.get(Calendar.DAY_OF_YEAR) == calCur.get(Calendar.DAY_OF_YEAR)) 
		    	&& (cal.get(Calendar.YEAR) == calCur.get(Calendar.YEAR)))
		    {
		    	return true;
		    }
		}
		return result;
	}
	
	public static String getLabel(String textCode){    
		Locale locale = new Locale("th","TH");
		ResourceBundle resource = null;
		String baseName = locale+"/properties/getLabel";
		if(ResourceBundleUtil.isUsed()){
			resource = ResourceBundle.getBundle(baseName,locale,ResourceBundleUtil.get(baseName));
		}else{
			resource = ResourceBundle.getBundle(baseName, locale);
		}
		if (!Util.empty(textCode)) {
			try{
				return resource.getString(textCode);
			}catch(Exception e){
				logger.error("Error "+e.getMessage());
			}
		}
		return "";
	}
	
	public static boolean enabledReprocessCheckbox(String actionType, String jobState, String interfaceCode, Date de2SubmitDate, String closeAppFlag, Date dateOfSearch)
	  {
		boolean enabledCheck  = false;
	    String INTERFACE_CODE_CARDLINK = SystemConstant.getConstant("INTERFACE_CODE_CARDLINK");
	    String WIP_JOBSTATE_IA = SystemConfig.getGeneralParam("WIP_JOBSTATE_IA");
	    String WIP_JOBSTATE_FRAUD = SystemConfig.getGeneralParam("WIP_JOBSTATE_FRAUD");
	    String CACHE_WORKFLOW_PARAM = SystemConstant.getConstant("CACHE_WORKFLOW_PARAM");
	    String VETO_DAY = SystemConstant.getConstant("WORK_FLOW_PARAM_VETO_DAY");
	    String JOBSTATE_PENDING_FULLFRAUD = SystemConstant.getConstant("JOBSTATE_PENDING_FULLFRAUD");
	    String JOBSTATE_APPROVED = SystemConstant.getConstant("JOBSTATE_APPROVED");
	    String JOBSTATE_POST_APPROVED = SystemConstant.getConstant("JOBSTATE_POST_APPROVED");
	    
	    int VETO_DAYS = FormatUtil.getInt(CacheControl.getName(CACHE_WORKFLOW_PARAM, VETO_DAY));
	    ArrayList<String> WIP_JOBSTATE_END = SystemConfig.getArrayListGeneralParam("WIP_JOBSTATE_END");
	    if (SystemConstant.getConstant("ACTION_TYPE_ENQUIRY").equals(actionType)) {
	    	enabledCheck = (!WIP_JOBSTATE_IA.contains(jobState)) && (!WIP_JOBSTATE_FRAUD.contains(jobState)) && (!WIP_JOBSTATE_END.contains(jobState)) && (!interfaceCode.equals(INTERFACE_CODE_CARDLINK));
	    }
	    logger.debug("actionType: "+actionType);
	    logger.debug("DIFF_DAY: "+de2SubmitDate);
	    if (SystemConstant.getConstant("ACTION_TYPE_REPROCESS").equals(actionType))
	    {
	    	long DIFF_DAY = getDiffDay(ApplicationDate.getDate(), dateOfSearch);
	    	logger.debug("DIFF_DAY: "+DIFF_DAY);
	    	logger.debug("jobState: "+jobState);
	    	logger.debug("de2SubmitDateInOneDay: "+KPLUtil.de2SubmitDateInOneDay(de2SubmitDate));
	    	if(JOBSTATE_APPROVED.equals(jobState) || JOBSTATE_POST_APPROVED.equals(jobState)
	    	   || SystemConstant.lookup("JOB_STATE_EXCEPTION_REPROCESS", jobState)
	    	   || (DIFF_DAY > VETO_DAYS)
	    	   || MConstant.FLAG.YES.equals(closeAppFlag))
	    	{
	    		enabledCheck = false;
	    	}
	    	else if(JOBSTATE_PENDING_FULLFRAUD.equals(jobState)
		    	   && (DIFF_DAY <= VETO_DAYS)
		    	   && KPLUtil.de2SubmitDateInOneDay(de2SubmitDate))
		    {
	    		enabledCheck = true;
		    }
	    	else
	    	{
	    		enabledCheck = true;
	    	}
		    
		}
	    return enabledCheck;
	  }
		
	public static boolean checkVetoDate(Date de2SubmitDate, Date dateOfSearch)
	  {
		boolean enabledCheck  = false;
	    String VETO_DAY = SystemConstant.getConstant("WORK_FLOW_PARAM_VETO_DAY");
	    String CACHE_WORKFLOW_PARAM = SystemConstant.getConstant("CACHE_WORKFLOW_PARAM");
	    int VETO_DAYS = FormatUtil.getInt(CacheControl.getName(CACHE_WORKFLOW_PARAM, VETO_DAY));
	  
	    	long DIFF_DAY = getDiffDay(ApplicationDate.getDate(), !Util.empty(dateOfSearch)?dateOfSearch:de2SubmitDate);
	    	logger.debug("DIFF_DAY: "+DIFF_DAY);
	    	logger.debug("VETO_DAYS: "+VETO_DAYS);
	    	if( (DIFF_DAY > VETO_DAYS))	    	{
	    		enabledCheck = false;
	    	}else{
	    		enabledCheck = true;
	    	}
	    	
	    return enabledCheck;
	  }
	
	  private static long getDiffDay(Date firstDate, Date lastDate)
	  {
	    if ((firstDate != null) && (lastDate != null))
	    {
	      long DIFF_TIME = firstDate.getTime() - lastDate.getTime();
	      long DIFF_DAY = DIFF_TIME / 86400000L;
	      return DIFF_DAY;
	    }
	    return 0L;
	  }
	  
		public static boolean isRequireDiffRequestResult(ApplicationGroupDataM applicationGroup)
		{
			if(isKPL(applicationGroup))
			{
				ApplicationDataM kplApp = KPLUtil.getKPLApplication(applicationGroup);
				if(MConstant.FLAG.YES.equals(kplApp.getDiffRequestFlag()) && Util.empty(kplApp.getDiffRequestResult()))
				{
					return true;
				}
			}
			return false;
		}
}
