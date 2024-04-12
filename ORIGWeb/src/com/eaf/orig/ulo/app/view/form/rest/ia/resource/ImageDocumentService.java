package com.eaf.orig.ulo.app.view.form.rest.ia.resource;

import java.io.IOException;
import java.io.Serializable;
import java.sql.Date;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Random;

import org.apache.log4j.Logger;

import com.eaf.core.ulo.common.display.FormatUtil;
import com.eaf.core.ulo.common.engine.SQLQueryEngine;
import com.eaf.core.ulo.common.properties.CacheControl;
import com.eaf.core.ulo.common.properties.SystemConfig;
import com.eaf.core.ulo.common.properties.SystemConstant;
import com.eaf.core.ulo.common.util.Util;
import com.eaf.orig.profile.model.UserDetailM;
import com.eaf.orig.shared.constant.OrigConstant;
import com.eaf.orig.shared.utility.SerializeUtil;
import com.eaf.orig.ulo.app.dao.ORIGDAOFactory;
import com.eaf.orig.ulo.app.dao.OrigApplicationGroupDAO;
import com.eaf.orig.ulo.app.factory.ModuleFactory;
import com.eaf.orig.ulo.app.proxy.ORIGServiceProxy;
import com.eaf.orig.ulo.app.util.GenerateUnique;
import com.eaf.orig.ulo.app.view.util.dih.DIHProxy;
import com.eaf.orig.ulo.app.view.util.dih.model.DIHQueryResult;
import com.eaf.orig.ulo.app.view.util.dih.model.KbankBranchInfoDataM;
import com.eaf.orig.ulo.constant.MConstant;
import com.eaf.orig.ulo.model.app.ApplicationDataM;
import com.eaf.orig.ulo.model.app.ApplicationGroupDataM;
import com.eaf.orig.ulo.model.app.ApplicationImageDataM;
import com.eaf.orig.ulo.model.app.ApplicationImageSplitDataM;
import com.eaf.orig.ulo.model.app.CashTransferDataM;
import com.eaf.orig.ulo.model.app.LoanDataM;
import com.eaf.orig.ulo.model.app.LoanFeeDataM;
import com.eaf.orig.ulo.model.app.LoanTierDataM;
import com.eaf.orig.ulo.model.app.ORPolicyRulesDataM;
import com.eaf.orig.ulo.model.app.ORPolicyRulesDetailDataM;
import com.eaf.orig.ulo.model.app.PaymentMethodDataM;
import com.eaf.orig.ulo.model.app.PersonalInfoDataM;
import com.eaf.orig.ulo.model.app.PersonalRelationDataM;
import com.eaf.orig.ulo.model.app.PolicyRulesDataM;
import com.eaf.orig.ulo.model.app.SpecialAdditionalServiceDataM;
import com.eaf.orig.ulo.model.compare.CompareDataM;
import com.eaf.orig.ulo.model.ia.DocumentSegment;
import com.eaf.orig.ulo.model.ia.ImageDocumentRequest;
import com.eaf.orig.ulo.model.ia.ImageDocumentResponse;
import com.eaf.orig.ulo.model.ia.KBankHeader;
import com.eaf.service.rest.model.ServiceResponse;
import com.orig.bpm.ulo.workflow.model.WorkflowRequestDataM;
import com.orig.bpm.ulo.workflow.model.WorkflowResponseDataM;
import com.orig.bpm.workflow.handle.WorkflowManager;
//import javax.xml.bind.DatatypeConverter;
//import javax.ws.rs.Consumes;
//import javax.ws.rs.PUT;
//import javax.ws.rs.Path;
//import javax.ws.rs.core.Context;
//import javax.ws.rs.core.MediaType;
//import javax.ws.rs.core.Response;
//import javax.ws.rs.core.UriInfo;
//import org.apache.openjpa.lib.util.Base16Encoder;

//@Path("/service")
public class ImageDocumentService {
	private static transient Logger logger = Logger.getLogger(ImageDocumentService.class);
	String COVER_PAGE_TYPE_NEW = SystemConstant.getConstant("COVER_PAGE_TYPE_NEW");
	String COVER_PAGE_TYPE_VETO = SystemConstant.getConstant("COVER_PAGE_TYPE_VETO");
	String COVER_PAGE_TYPE_FOLLOW = SystemConstant.getConstant("COVER_PAGE_TYPE_FOLLOW");
	String BPM_HOST = SystemConfig.getProperty("BPM_HOST");
	String BPM_PORT = SystemConfig.getProperty("BPM_PORT");
	String BPM_USER_ID = SystemConfig.getProperty("BPM_USER_ID");
	String BPM_PASSWORD = SystemConfig.getProperty("BPM_PASSWORD");	
	String PRODUCT_CRADIT_CARD = SystemConstant.getConstant("PRODUCT_CRADIT_CARD");
	String[] VETO_APPLICATION_DECISION = SystemConstant.getArrayConstant("VETO_APPLICATION_DECISION");	
	String BORROWER = SystemConstant.getConstant("APPLICATION_CARD_TYPE_BORROWER");
	String SUPPLEMENTARY = SystemConstant.getConstant("APPLICATION_CARD_TYPE_SUPPLEMENTARY");	
	String APPLICATION_TYPE_NEW = SystemConstant.getConstant("APPLICATION_TYPE_NEW");
	String APPLICATION_TYPE_ADD = SystemConstant.getConstant("APPLICATION_TYPE_ADD");
	String APPLICATION_TYPE_UPGRADE = SystemConstant.getConstant("APPLICATION_TYPE_UPGRADE");
	String APPLICATION_LEVEL = SystemConstant.getConstant("PERSONAL_RELATION_APPLICATION_LEVEL");
	String CACHE_QR1 = SystemConstant.getConstant("CACHE_QR1");
	String APPLICATION_RANDOM_NO1_MAX = SystemConstant.getConstant("CREATE_APPLICATION_RANDOM_NO1_MAX");
	String APPLICATION_RANDOM_NO1_MIN = SystemConstant.getConstant("CREATE_APPLICATION_RANDOM_NO1_MIN");
	String CACHE_BRANCH_INFO = SystemConstant.getConstant("CACHE_BRANCH_INFO");
	String PERSONAL_TYPE_APPLICANT = SystemConstant.getConstant("PERSONAL_TYPE_APPLICANT");
	
//	@PUT
//	@Path("/create")
//    @Consumes(MediaType.APPLICATION_JSON)
    public ImageDocumentResponse create(ImageDocumentRequest imageDocumentRequest){
		logger.debug(imageDocumentRequest);
		ImageDocumentResponse imageDocumentResponse = new ImageDocumentResponse();
		KBankHeader responseHeader = new KBankHeader();
		String qrApplyType = imageDocumentRequest.getQrApplyType();
		String qr1 = imageDocumentRequest.getQr1();
		String qr2 = imageDocumentRequest.getQr2();
		logger.debug("qrApplyType >> "+qrApplyType);
		logger.debug("qr1 >> "+qr1);
		logger.debug("qr2 >> "+qr2);
		UserDetailM userM = new UserDetailM();
		userM.setUserName(imageDocumentRequest.getKbankHeader().getUserId());
		try{
			if(!Util.empty(qrApplyType)){
				if(COVER_PAGE_TYPE_NEW.equals(qrApplyType)){
					createNewApplication(imageDocumentRequest,userM);
				}else if(COVER_PAGE_TYPE_VETO.equals(qrApplyType)){
					String applicationGroupId = getApplicationGroupId(imageDocumentRequest.getQr2());
					logger.debug("applicationGroupId >> "+applicationGroupId);
					if(!Util.empty(applicationGroupId)){
						createVetoApplication(imageDocumentRequest,userM);
					}else{
						responseHeader.setStatusCode(ServiceResponse.Status.SYSTEM_EXCEPTION);	
						responseHeader.error(ServiceResponse.Status.SYSTEM_EXCEPTION,"Not found Qr2.");
					}
				}
				responseHeader.setStatusCode(ServiceResponse.Status.SUCCESS);	
			}else{
				responseHeader.setStatusCode(ServiceResponse.Status.SYSTEM_EXCEPTION);	
				responseHeader.error(ServiceResponse.Status.SYSTEM_EXCEPTION,"Not found Qr Apply Type.");
			}
		}catch(Exception e){
			logger.fatal("ERROR",e);
			responseHeader.setStatusCode(ServiceResponse.Status.SYSTEM_EXCEPTION);	
			responseHeader.error(ServiceResponse.Status.SYSTEM_EXCEPTION,e.getMessage());
		}
		imageDocumentResponse.setKbankHeader(responseHeader);
//		return Response.ok(imageDocumentResponse).build();
		return imageDocumentResponse;
    }
 
	private void createNewApplication(ImageDocumentRequest imageDocumentRequest,UserDetailM  userM)  throws Exception, IOException{
		try{
			ApplicationGroupDataM applicationGroup = new ApplicationGroupDataM();
				applicationGroup.setApplicationDate(imageDocumentRequest.getScanDate());
				applicationGroup.setApplyDate(imageDocumentRequest.getScanDate());
				applicationGroup.setCoverpageType(imageDocumentRequest.getQrApplyType());
				applicationGroup.setApplicationGroupNo(imageDocumentRequest.getQr2());
				applicationGroup.setDocSetNo(imageDocumentRequest.getQr2());
				String templateId = CacheControl.getName(CACHE_QR1,imageDocumentRequest.getQr1(),"TEMPLATE_ID");
				logger.debug("templateId >> "+templateId);
				applicationGroup.setApplicationTemplate(templateId);
				applicationGroup.setRcCode(imageDocumentRequest.getRcCode());
				applicationGroup.setApplyChannel(imageDocumentRequest.getChannel());
				applicationGroup.setBranchNo(imageDocumentRequest.getBranchCode());
				applicationGroup.setUserId(userM.getUserName());
				applicationGroup.setRandomNo1(getRandomNo());
				applicationGroup.setRandomNo2(getRandomNo());
				applicationGroup.setRandomNo3(getRandomNo());
//				applicationGroup.setBranchName(CacheControl.getName(CACHE_BRANCH_INFO,imageDocumentRequest.getBranchCode(),"TH_CNTR_NM"));
//				applicationGroup.setBranchRegion(CacheControl.getName(CACHE_BRANCH_INFO,imageDocumentRequest.getBranchCode(),"KBNK_RGON_CD"));
//				applicationGroup.setBranchZone(CacheControl.getName(CACHE_BRANCH_INFO,imageDocumentRequest.getBranchCode(),"KBNK_ZON_NO"));
				
				DIHQueryResult<KbankBranchInfoDataM> queryResult = DIHProxy.getKbankBranchData(imageDocumentRequest.getBranchCode());
				KbankBranchInfoDataM kbankBranchInfo = queryResult.getResult();
						applicationGroup.setBranchName(kbankBranchInfo.getBranchName());
						applicationGroup.setBranchRegion(kbankBranchInfo.getBranchRegion());
						applicationGroup.setBranchZone(kbankBranchInfo.getBranchZone());
				
				logger.debug("RandomNo1 >> "+applicationGroup.getRandomNo1());
			ArrayList<DocumentSegment> documentSegments = imageDocumentRequest.getDocumentSegments();
			ArrayList<ApplicationImageDataM> applicationImages = applicationGroup.getApplicationImages();
			if(null == applicationImages){
				applicationImages = new ArrayList<ApplicationImageDataM>();
				applicationGroup.setApplicationImages(applicationImages);
			}		
			if(!Util.empty(documentSegments)){
				ApplicationImageDataM applicationImage = new ApplicationImageDataM();
					for(DocumentSegment documentSegment:documentSegments){
						ApplicationImageSplitDataM applicationImageSplit = new ApplicationImageSplitDataM();
						applicationImageSplit.setImageId(documentSegment.getImageId());		
						applicationImageSplit.setSeq(documentSegment.getSeq());
						applicationImageSplit.setDocTypeGroup(documentSegment.getDocTypeGroup());
						applicationImageSplit.setDocType(documentSegment.getDocTypeCode());
						applicationImageSplit.setDocTypeSeq(documentSegment.getDocTypeSeq());
						applicationImage.add(applicationImageSplit);
					}
				applicationImages.add(applicationImage);
			}
			createApplication(applicationGroup,userM);
		}catch(Exception e){
			logger.fatal("ERROR ",e);
		}	
	}	
	private void createApplication(ApplicationGroupDataM applicationGroup,UserDetailM userM)throws Exception{
		ORIGServiceProxy.getApplicationManager().createApplication(applicationGroup, userM);
		WorkflowRequestDataM workflowRequest = new WorkflowRequestDataM();
			workflowRequest.setApplicationGroup(applicationGroup);
			workflowRequest.setUniqueId(applicationGroup.getApplicationGroupId());
			workflowRequest.setUserId(userM.getUserName());
			WorkflowManager workflowManager = new WorkflowManager(BPM_HOST,BPM_PORT,BPM_USER_ID,BPM_PASSWORD);
		WorkflowResponseDataM workflowResponse = workflowManager.createWorkflowTask(workflowRequest);
		applicationGroup.setInstantId(workflowResponse.getPiid());
	}
	private void saveApplication(ApplicationGroupDataM applicationGroup,UserDetailM userM)throws Exception{
		String userId = userM.getUserName();
		String applicationGroupId = applicationGroup.getApplicationGroupId();
		ORIGServiceProxy.getApplicationManager().saveApplication(applicationGroup, userM);
		int lifeCycle = applicationGroup.getMaxLifeCycle();
		if(applicationGroup.isClearCompareData()){
			ModuleFactory.getOrigComparisionDataDAO(userId)
			.deleteComparisonDataNotMatchUniqueId(applicationGroupId, CompareDataM.SoruceOfData.TWO_MAKER, null,lifeCycle);
		}
		WorkflowRequestDataM workflowRequest = new WorkflowRequestDataM();
			workflowRequest.setApplicationGroup(applicationGroup);
			workflowRequest.setUniqueId(applicationGroup.getApplicationGroupId());
			workflowRequest.setUserId(userM.getUserName());
			WorkflowManager workflowManager = new WorkflowManager(BPM_HOST,BPM_PORT,BPM_USER_ID,BPM_PASSWORD);
		WorkflowResponseDataM workflowResponse = workflowManager.createWorkflowTask(workflowRequest);
		applicationGroup.setInstantId(workflowResponse.getPiid());
	}
	
	@SuppressWarnings("unchecked")
	private String getApplicationGroupId(String qr2){
		SQLQueryEngine queryEngine = new SQLQueryEngine();
		@SuppressWarnings("rawtypes")
		HashMap Hash = queryEngine.LoadModule("SELECT * FROM ORIG_APPLICATION_GROUP WHERE APPLICATION_GROUP_NO = ?",qr2);
		return SQLQueryEngine.display(Hash, "APPLICATION_GROUP_ID");
	}
	private void createVetoApplication(ImageDocumentRequest imageDocumentRequest,UserDetailM userM) throws Exception{
		String applicationGroupId = getApplicationGroupId(imageDocumentRequest.getQr2());
		logger.debug("applicationGroupId >> "+applicationGroupId);
//		if(!Util.empty(applicationGroupId)){
//			OrigApplicationGroupDAO applicationGroupDAO = ORIGDAOFactory.getApplicationGroupDAO();
//			ApplicationGroupDataM applicationGroup = applicationGroupDAO.loadOrigApplicationGroupM(applicationGroupId);
//			applicationGroup.setCoverpageType(imageDocumentRequest.getQrApplyType());	
//			applicationGroup.setUserId(userM.getUserName());
////			#rawi comment: not create randomno1 on create veto application.
////			applicationGroup.setRandomNo1(getRandomNo1());
////			logger.debug("RandomNo1 >> "+applicationGroup.getRandomNo1());
//			int maxLifeCycle = applicationGroup.getMaxLifeCycle();
//			int lifeCycle = maxLifeCycle+1;
//			String applicationType = applicationGroup.getApplicationType();
//			logger.debug("maxLifeCycle >> "+maxLifeCycle);
//			logger.debug("lifeCycle >> "+lifeCycle);
//			logger.debug("applicationType >> "+applicationType);
//			ArrayList<String> products = applicationGroup.getProducts(maxLifeCycle);
//			logger.debug("products >> "+products);
//			logger.debug("before applicationGroup.sizeApplication >> "+applicationGroup.sizeApplication());
//			if(!Util.empty(products)){
//				for (String product : products) {
//					logger.debug("Product >> "+product);
//					if(PRODUCT_CRADIT_CARD.equals(product)&& applicationType().contains(applicationType)){
//						@SuppressWarnings("unchecked")
//						ArrayList<ApplicationDataM> borrowers = (ArrayList<ApplicationDataM>)SerializeUtil.clone(
//								(Serializable)filter(product,maxLifeCycle,BORROWER,applicationGroup));
//						logger.debug("borrowers.size >> "+borrowers.size());
//						if(!Util.empty(borrowers)){
//							for (ApplicationDataM borrower : borrowers) {
//								createItem(lifeCycle,borrower,applicationGroup);
//								String mainCardId = borrower.getApplicationRecordId();
//								String cloneApplicationRecordId = borrower.getRefApplicationRecordId();
//								@SuppressWarnings("unchecked")
//								ArrayList<ApplicationDataM> supplemantarys = (ArrayList<ApplicationDataM>)SerializeUtil.clone(
//										(Serializable)filter(cloneApplicationRecordId,SUPPLEMENTARY,applicationGroup));
//								logger.debug("supplemantarys.size >> "+supplemantarys.size());
//								if(!Util.empty(supplemantarys)){
//									for (ApplicationDataM supplemantary : supplemantarys) {
//										supplemantary.setMaincardRecordId(mainCardId);
//										createItem(lifeCycle,supplemantary,applicationGroup);
//									}
//								}
//							}
//						}
//					}else{
//						@SuppressWarnings("unchecked")
//						ArrayList<ApplicationDataM> applications = (ArrayList<ApplicationDataM>)SerializeUtil.clone(
//								(Serializable)filter(product,maxLifeCycle,applicationGroup));
//						logger.debug("applications.size >> "+applications.size());
//						if(!Util.empty(applications)){
//							for (ApplicationDataM application: applications) {
//								createItem(lifeCycle,application,applicationGroup);
//							}
//						}
//					}
//				}
//			}	
//			logger.debug("after applicationGroup.sizeApplication >> "+applicationGroup.sizeApplication());
//			ArrayList<DocumentSegment> documentSegments = imageDocumentRequest.getDocumentSegments();
//			ArrayList<ApplicationImageDataM> applicationImages = applicationGroup.getApplicationImages();
//			if(null == applicationImages){
//				applicationImages = new ArrayList<ApplicationImageDataM>();
//				applicationGroup.setApplicationImages(applicationImages);
//			}
//			if(!Util.empty(documentSegments)){
//				ApplicationImageDataM applicationImage = new ApplicationImageDataM();
//					for(DocumentSegment documentSegment:documentSegments){
//						ApplicationImageSplitDataM applicationImageSplit = new ApplicationImageSplitDataM();
//						applicationImageSplit.setImageId(documentSegment.getImageId());		
//						applicationImageSplit.setSeq(documentSegment.getSeq());
//						applicationImageSplit.setDocTypeGroup(documentSegment.getDocTypeGroup());
//						applicationImageSplit.setDocType(documentSegment.getDocTypeCode());
//						applicationImageSplit.setDocTypeSeq(documentSegment.getDocTypeSeq());
//						applicationImage.add(applicationImageSplit);
//					}
//				applicationImages.add(applicationImage);
//			}
//			saveApplication(applicationGroup,userM);
//		}
		
		if(!Util.empty(applicationGroupId)){
			OrigApplicationGroupDAO applicationGroupDAO = ORIGDAOFactory.getApplicationGroupDAO();
			ApplicationGroupDataM applicationGroup = applicationGroupDAO.loadOrigApplicationGroupM(applicationGroupId);
			applicationGroup.setUserId(userM.getUserName());
			applicationGroup.setCoverpageType(imageDocumentRequest.getQrApplyType());	
//			#rawi comment not clear compare data
//			applicationGroup.setClearCompareData(true);
			String userId = userM.getUserName();
			String coverPageType = applicationGroup.getCoverpageType();
			boolean isVetoEligible = isVetoEligible(applicationGroup);
			logger.debug("isVetoEligible >> "+isVetoEligible);
			logger.debug("coverPageType >> "+coverPageType);
			logger.debug("userId >> "+userId);
			if(isVetoEligible){
				reOpenExistingApplication(applicationGroup,coverPageType,userId);
			}
			saveApplication(applicationGroup,userM);
		}		
	}
	String FIELD_ID_COVER_PAGE_TYPE = SystemConstant.getConstant("FIELD_ID_COVER_PAGE_TYPE");
	public String getCoverageType(String qrCoverpage){
		String coverpageType = CacheControl.getName(FIELD_ID_COVER_PAGE_TYPE,"SYSTEM_ID2",qrCoverpage,"CHOICE_NO");
		if(coverpageType == null){
			coverpageType = CacheControl.getName(FIELD_ID_COVER_PAGE_TYPE,"SYSTEM_ID2",null,"CHOICE_NO");
		}
		logger.debug("coverpageType >> "+coverpageType);
		return coverpageType;
	}
	
	private void reOpenExistingApplication(ApplicationGroupDataM applicationGroup,String coverpageType,String userId) throws Exception{
		applicationGroup.setCoverpageType(getCoverageType(coverpageType));	
		int maxLifeCycle = applicationGroup.getMaxLifeCycle();
		int vetoLifeCycle = maxLifeCycle+1;
		logger.debug("maxLifeCycle >> "+maxLifeCycle);
		logger.debug("vetoLifeCycle >> "+vetoLifeCycle);
		ArrayList<String> products = applicationGroup.getProducts(maxLifeCycle);
		String applicationType = applicationGroup.getApplicationType();		
		if(!Util.empty(products)){
			for (String product : products) {
				logger.debug("Product >> "+product);
				if(PRODUCT_CRADIT_CARD.equals(product)&& getNormalApplicationType().contains(applicationType)){
					@SuppressWarnings("unchecked")
					ArrayList<ApplicationDataM> borrowers = (ArrayList<ApplicationDataM>)SerializeUtil.clone(
							(Serializable)filter(product,maxLifeCycle,BORROWER,applicationGroup));
					logger.debug("borrowers.size >> "+borrowers.size());
					if(!Util.empty(borrowers)){
						for (ApplicationDataM borrower : borrowers) {
							createItem(vetoLifeCycle,borrower,applicationGroup);
							String mainCardId = borrower.getApplicationRecordId();
							String cloneApplicationRecordId = borrower.getRefApplicationRecordId();
							@SuppressWarnings("unchecked")
							ArrayList<ApplicationDataM> supplemantarys = (ArrayList<ApplicationDataM>)SerializeUtil.clone(
									(Serializable)filter(cloneApplicationRecordId,SUPPLEMENTARY,applicationGroup));
							logger.debug("supplemantarys.size >> "+supplemantarys.size());
							if(!Util.empty(supplemantarys)){
								for (ApplicationDataM supplemantary : supplemantarys) {
									supplemantary.setMaincardRecordId(mainCardId);
									createItem(vetoLifeCycle,supplemantary,applicationGroup);
								}
							}
						}
					}
				}else{
					@SuppressWarnings("unchecked")
					ArrayList<ApplicationDataM> applications = (ArrayList<ApplicationDataM>)SerializeUtil.clone(
							(Serializable)filter(product,maxLifeCycle,applicationGroup));
					logger.debug("applications.size >> "+applications.size());
					if(!Util.empty(applications)){
						for (ApplicationDataM application: applications) {
							createItem(vetoLifeCycle,application,applicationGroup);
						}
					}
					
				}
			}		
			ArrayList<ApplicationDataM> applications = applicationGroup.filterApplicationLifeCycle();
			if(null != applications){
				for (ApplicationDataM application : applications) {
					LoanDataM loan = application.getLoan();
					String genPaymentMethod = loan.getGenPaymentMethod();
					String genSpecialAdditionalService = loan.getGenSpecialAdditionalService();					
					logger.debug("genPaymentMethod >> "+genPaymentMethod);				
					if(MConstant.FLAG_Y.equals(genPaymentMethod)){
						String lastPaymentMethodId = loan.getPaymentMethodId();	
						logger.debug("lastPaymentMethodId >> "+lastPaymentMethodId);
						if(!Util.empty(lastPaymentMethodId)){
							PaymentMethodDataM paymentMethod = (PaymentMethodDataM)SerializeUtil.clone(applicationGroup.getPaymentMethodById(lastPaymentMethodId));
							logger.debug("paymentMethod >> "+paymentMethod);
							if(null != paymentMethod){								
								String paymentMethodId = GenerateUnique.generate(OrigConstant.SeqNames.ORIG_PAYMENT_METHOD_PK);
								logger.debug("paymentMethodId >> "+paymentMethodId);
								paymentMethod.init(paymentMethodId);
								applicationGroup.addPaymentMethod(paymentMethod);
								loan.setPaymentMethodId(paymentMethodId);
							}else{
								loan.setPaymentMethodId(null);
							}
						}
					}else{
						loan.setPaymentMethodId(null);
					}

					logger.debug("genSpecialAdditionalService >> "+genSpecialAdditionalService);	
					if(MConstant.FLAG_Y.equals(genPaymentMethod)){
						ArrayList<String> specialAdditionalServiceMapIds = new ArrayList<String>();
						ArrayList<String> lastSpecialAdditionalServiceIds = loan.getSpecialAdditionalServiceIds();
						if(!Util.empty(lastSpecialAdditionalServiceIds)){
							for (String lastSpecialAdditionalServiceId : lastSpecialAdditionalServiceIds) {
								SpecialAdditionalServiceDataM specialAdditionalService = (SpecialAdditionalServiceDataM)SerializeUtil
										.clone(applicationGroup.getSpecialAdditionalService(lastSpecialAdditionalServiceId));
								logger.debug("specialAdditionalService >> "+specialAdditionalService);
								if(null != specialAdditionalService){
									String serviceId = GenerateUnique.generate(OrigConstant.SeqNames.ORIG_ADDITIONAL_SERVICE_PK);
									logger.debug("serviceId >> "+serviceId);
									specialAdditionalService.init(serviceId);
									specialAdditionalServiceMapIds.add(serviceId);
									applicationGroup.addSpecialAdditionalService(specialAdditionalService);
								}
							}
						}
						loan.setSpecialAdditionalServiceIds(specialAdditionalServiceMapIds);
					}else{
						loan.setSpecialAdditionalServiceIds(new ArrayList<String>());
					}
				}
			}
		}
	}
		
	private void createItem(int vetoLifeCycle,ApplicationDataM application,ApplicationGroupDataM applicationGroup) throws Exception{
		String IsVetoEligibleFlag = application.getIsVetoEligibleFlag();
		logger.debug("IsVetoEligibleFlag >> "+IsVetoEligibleFlag);	
		if(MConstant.FLAG_Y.equals(IsVetoEligibleFlag)){
			String refId = application.getApplicationRecordId();
			String applicationRecordId = GenerateUnique.generate(OrigConstant.SeqNames.ORIG_APPLICATION_PK);

			String lastApplicationNo = application.getApplicationNo();
			String currentApplicationNo = GenerateUnique.generateApplicationNo(lastApplicationNo,vetoLifeCycle);
			
			logger.debug("refId >> "+refId);
			logger.debug("applicationRecordId >> "+applicationRecordId);	
			logger.debug("lastApplicationNo >> "+lastApplicationNo);
			logger.debug("currentApplicationNo >> "+currentApplicationNo);
			
			application.clearValue();
			
			application.setApplicationRecordId(applicationRecordId);
			application.setApplicationNo(currentApplicationNo);
			application.setRefApplicationRecordId(refId);
			application.setLifeCycle(vetoLifeCycle);
			
			if(null != application.getBundleHL()){
				application.getBundleHL().init(applicationRecordId);
			}		
			if(null != application.getBundleKL()){
				application.getBundleKL().init(applicationRecordId);
			}
			if(null != application.getBundleSME()){
				application.getBundleSME().init(applicationRecordId);
			}
			if(!Util.empty(application.getLoans())){
				for(LoanDataM loan:application.getLoans()){
					String loanId = GenerateUnique.generate(OrigConstant.SeqNames.ORIG_LOAN_PK);
					loan.init(applicationRecordId,loanId);
//					loan.setPaymentMethodId(null);
//					loan.setSpecialAdditionalServiceIds(new ArrayList<String>());
					if(!Util.empty(loan.getPaymentMethodId())){
						loan.setGenPaymentMethod(MConstant.FLAG_Y);
					}
					if(!Util.empty(loan.getSpecialAdditionalServiceIds())){
						loan.setGenSpecialAdditionalService(MConstant.FLAG_Y);
					}
					if(null != loan.getCard()){
						String cardId = GenerateUnique.generate(OrigConstant.SeqNames.ORIG_CARD_PK);
						loan.getCard().init(loanId, cardId);
						if(null!=loan.getCard().getWisdom()){
							loan.getCard().getWisdom().init(cardId);
						}

					}
					if(null != loan.getCashTransfers()){
						for(CashTransferDataM cashTransfer :loan.getCashTransfers()){
							String cashTransferId = GenerateUnique.generate(OrigConstant.SeqNames.ORIG_CASH_TRANSFER_PK);
							cashTransfer.init(loanId,cashTransferId);
						}						 
					}
					if(null != loan.getLoanTiers()){
						for (LoanTierDataM loanTier : loan.getLoanTiers()) {
							String tierId = GenerateUnique.generate(OrigConstant.SeqNames.ORIG_LOAN_TIER_PK); 
							loanTier.init(loanId,tierId);
						}
					}
					if(null != loan.getLoanFees()){
						for (LoanFeeDataM loanFee : loan.getLoanFees()) {
							String loanFeeId = GenerateUnique.generate(OrigConstant.SeqNames.ORIG_LOAN_FEE_PK); 
							loanFee.init(loanId,loanFeeId);
						}
					}		
//					#rawi comment change logic cloan SpecialAdditionalService,PaymentMethod
//					if(!Util.empty(loan.getSpecialAdditionalServiceIDs())){
//						ArrayList<String> additionalServiceIdList = new ArrayList<String>();
//						for(String serviceId : loan.getSpecialAdditionalServiceIDs()){
//							SpecialAdditionalServiceDataM  specialAdditionalService = applicationGroup.getSpecialAdditionalServiceById(serviceId);
//							if(!Util.empty(specialAdditionalService)){
//								String newServiceId= GenerateUnique.generate(OrigConstant.SeqNames.ORIG_ADDITIONAL_SERVICE_PK);
//								specialAdditionalService.init(newServiceId);
//								additionalServiceIdList.add(newServiceId);
//							}
//						}
//						loan.setSpecialAdditionalServiceIDs(additionalServiceIdList);
//					}
//					if(!Util.empty(loan.getPaymentMethodId())){
//						PaymentMethodDataM paymentMethod = applicationGroup.getPaymentMethodByID(loan.getPaymentMethodId());
//						if(!Util.empty(paymentMethod)){
//							String paymentMethodId= GenerateUnique.generate(OrigConstant.SeqNames.ORIG_PAYMENT_METHOD_PK);
//							paymentMethod.init(paymentMethodId);
//							loan.setPaymentMethodId(paymentMethodId);
//						}
//					}
				}
			}
			
			if(null!=application.getVerificationResult()){
				String verResultId  = GenerateUnique.generate(OrigConstant.SeqNames.XRULES_VERIFICATION_RESULT_PK);
				application.getVerificationResult().init(applicationRecordId, verResultId);
				if(!Util.empty(application.getVerificationResult().getPolicyRules())){
					for(PolicyRulesDataM policyRulesDataM : application.getVerificationResult().getPolicyRules()){
						String policyRuleId  = GenerateUnique.generate(OrigConstant.SeqNames.XRULES_POLICY_RULES_PK);
						policyRulesDataM.init(verResultId, policyRuleId);
						if(!Util.empty(policyRulesDataM.getOrPolicyRules())){
							for(ORPolicyRulesDataM orPolicyRulesDataM :policyRulesDataM.getOrPolicyRules()){
								String orPolicyRuleId  = GenerateUnique.generate(OrigConstant.SeqNames.XRULES_OR_POLICY_RULES_PK);
								orPolicyRulesDataM.init(policyRuleId, orPolicyRuleId);
								if(!Util.empty(orPolicyRulesDataM.getOrPolicyRulesDetails())){
									for(ORPolicyRulesDetailDataM orPolicyRulesDetailDataM: orPolicyRulesDataM.getOrPolicyRulesDetails()){
										String orPolicyRuleDetailId  = GenerateUnique.generate(OrigConstant.SeqNames.XRULES_OR_POLICY_RULES_DTL_PK);
										orPolicyRulesDetailDataM.init(orPolicyRuleId, orPolicyRuleDetailId);
									}
								}
							}
						}
					}
				}
			}
			
			PersonalRelationDataM personalRelation = applicationGroup.getPersonalReation(refId,APPLICATION_LEVEL);
			String personalId = personalRelation.getPersonalId();
			String personalType = personalRelation.getPersonalType();
			logger.debug("personalId >> "+personalId);
			logger.debug("personalType >> "+personalType);
			PersonalInfoDataM personalInfo = applicationGroup.getPersonalById(personalId);
			ArrayList<PersonalRelationDataM> personalRelations = personalInfo.getPersonalRelations();
			if (null == personalRelations) {
				personalRelations = new ArrayList<PersonalRelationDataM>();
				personalInfo.setPersonalRelations(personalRelations);
			}
			personalInfo.addPersonalRelation(applicationRecordId,personalType,APPLICATION_LEVEL);
			if(PERSONAL_TYPE_APPLICANT.equals(personalInfo.getPersonalType())){
				application.setMaincardRecordId(applicationRecordId);
			}
			applicationGroup.addApplications(application);	
		}			
	}
		
	
	String DECISION_FINAL_DECISION_REJECT = SystemConstant.getConstant("DECISION_FINAL_DECISION_REJECT");	
	String MAX_LIFE_CYCLE_CONFIG = SystemConstant.getConstant("MAX_LIFE_CYCLE");	
	String CACHE_WORKFLOW_PARAM = SystemConstant.getConstant("CACHE_WORKFLOW_PARAM");	
	String VETO_DAY = SystemConstant.getConstant("WORK_FLOW_PARAM_VETO_DAY");
	String CACHE_TEMPLATE = SystemConstant.getConstant("CACHE_TEMPLATE");
	
	private boolean isVetoEligible(ApplicationGroupDataM applicationGroup){
		int VETO_DAYS =FormatUtil.getInt(CacheControl.getName(CACHE_WORKFLOW_PARAM,VETO_DAY));
		int LIFE_CYCLE_TIMES = FormatUtil.getInt(MAX_LIFE_CYCLE_CONFIG);
		int MAX_LIFE_CYCLE = applicationGroup.getMaxLifeCycle();
		int MIN_LIFE_CYCLE = 1;
		Date applicationDate = applicationGroup.getApplicationDate();
		ArrayList<String> finalAppDecions = applicationGroup.getFinalDecisionLifeCycle();
		ArrayList<ApplicationDataM> applicationList = applicationGroup.getApplications();
		ArrayList<String> vetoEligibleFlags = applicationGroup.getVetoEligibleFlagLifeCycle();		
		logger.debug("VETO_DAYS >> "+VETO_DAYS);
		logger.debug("LIFE_CYCLE_TIMES >> "+LIFE_CYCLE_TIMES);
		logger.debug("MAX_LIFE_CYCLE >> "+MAX_LIFE_CYCLE);		
		if(null!=applicationList){
			for(ApplicationDataM application :applicationList){
				if(MIN_LIFE_CYCLE ==application.getLifeCycle()){						
					long DIFF_DAY = getDiffDay(applicationDate,application.getFinalAppDecisionDate());
					logger.debug("DIFF_DAY>>"+DIFF_DAY);
					if(finalAppDecions.contains(DECISION_FINAL_DECISION_REJECT) && vetoEligibleFlags.contains(MConstant.FLAG_Y) 
							&& DIFF_DAY <=VETO_DAYS && MAX_LIFE_CYCLE<=LIFE_CYCLE_TIMES){
						return true;
					}		 
				}
			}
		}
		return false;
	}
	private ArrayList<String> getNormalApplicationType(){
		ArrayList<String> applicationTypes = new ArrayList<String>();
			applicationTypes.add(APPLICATION_TYPE_NEW);
			applicationTypes.add(APPLICATION_TYPE_ADD);
			applicationTypes.add(APPLICATION_TYPE_UPGRADE);
		return applicationTypes;
	}
	private ArrayList<ApplicationDataM> filter(String product,int maxLifeCycle,ApplicationGroupDataM applicationGroup){
		return applicationGroup.filterApplicationDecision(product,maxLifeCycle,VETO_APPLICATION_DECISION);
	}
	private ArrayList<ApplicationDataM> filter(String product,int maxLifeCycle,String applicationCardType,ApplicationGroupDataM applicationGroup){
		return applicationGroup.filterApplicationDecision(product,maxLifeCycle,VETO_APPLICATION_DECISION,applicationCardType);
	}
	private ArrayList<ApplicationDataM> filter(String applicationRecordId,String applicationCardType,ApplicationGroupDataM applicationGroup){
		return applicationGroup.filterLinkApplicationDecision(applicationRecordId,VETO_APPLICATION_DECISION,applicationCardType);
	}	
	private long getDiffDay(Date firstDate , Date lastDate){
		if(null!=firstDate && null!=lastDate){
			long DIFF_TIME = firstDate.getTime() - lastDate.getTime();
			long DIFF_DAY = DIFF_TIME/(1000*60*60*24);
			return DIFF_DAY;
		}
		return 0;
	}
	
//	private ArrayList<String> applicationType(){
//		ArrayList<String> applicationTypes = new ArrayList<String>();
//			applicationTypes.add(APPLICATION_TYPE_NEW);
//			applicationTypes.add(APPLICATION_TYPE_ADD);
//			applicationTypes.add(APPLICATION_TYPE_UPGRADE);
//		return applicationTypes;
//	}
//	private ArrayList<ApplicationDataM> filter(String product,int maxLifeCycle,ApplicationGroupDataM applicationGroup){
//		return applicationGroup.filterApplicationDecision(product,maxLifeCycle
//				,VETO_APPLICATION_DECISION);
//	}
//	private ArrayList<ApplicationDataM> filter(String product,int maxLifeCycle,String applicationCardType,ApplicationGroupDataM applicationGroup){
//		return applicationGroup.filterApplicationDecision(product,maxLifeCycle
//				,VETO_APPLICATION_DECISION,applicationCardType);
//	}
//	private ArrayList<ApplicationDataM> filter(String applicationRecordId,String applicationCardType,ApplicationGroupDataM applicationGroup){
//		return applicationGroup.filterLinkApplicationDecision(applicationRecordId
//				,VETO_APPLICATION_DECISION,applicationCardType);
//	}
//	private void createItem(int lifeCycle,ApplicationDataM application,ApplicationGroupDataM applicationGroup) throws Exception{
//		String refId = application.getApplicationRecordId();
//		String applicationRecordId = GenerateUnique.generate(OrigConstant.SeqNames.ORIG_APPLICATION_PK);
//		logger.debug("refId >> "+refId);
//		logger.debug("applicationRecordId >> "+applicationRecordId);		
//		application.setApplicationRecordId(applicationRecordId);
//		String applicationNo = application.getApplicationNo();
//		application.setRefApplicationRecordId(refId);
//		application.setLifeCycle(lifeCycle);
//		application.clearValue();
//		if(null != application.getBundleHL()){
//			application.getBundleHL().init(applicationRecordId);
//		}		
//		if(null != application.getBundleKL()){
//			application.getBundleKL().init(applicationRecordId);
//		}
//		if(null != application.getBundleSME()){
//			application.getBundleSME().init(applicationRecordId);
//		}
//		if(!Util.empty(application.getLoans())){
//			for(LoanDataM loan:application.getLoans()){
//				String loanId = GenerateUnique.generate(OrigConstant.SeqNames.ORIG_LOAN_PK);
//				loan.init(applicationRecordId,loanId);
//				if(null != loan.getCard()){
//					String cardId = GenerateUnique.generate(OrigConstant.SeqNames.ORIG_CARD_PK);
//					loan.getCard().init(loanId, cardId);
//				}
//				if(null != loan.getCashTransfers()){
//					for(CashTransferDataM cashTransfer :loan.getCashTransfers()){
//						String cashTransferId = GenerateUnique.generate(OrigConstant.SeqNames.ORIG_CASH_TRANSFER_PK);
//						cashTransfer.init(loanId,cashTransferId);
//					}						 
//				}
//				if(null != loan.getLoanTiers()){
//					for (LoanTierDataM loanTier : loan.getLoanTiers()) {
//						String tierId = GenerateUnique.generate(OrigConstant.SeqNames.ORIG_LOAN_TIER_PK); 
//						loanTier.init(loanId,tierId);
//					}
//				}
//				if(null != loan.getLoanFees()){
//					for (LoanFeeDataM loanFee : loan.getLoanFees()) {
//						String loanFeeId = GenerateUnique.generate(OrigConstant.SeqNames.ORIG_LOAN_FEE_PK); 
//						loanFee.init(loanId,loanFeeId);
//					}
//				}
//			}
//		}
//		PersonalRelationDataM personalRelation = applicationGroup.getPersonalReation(refId,APPLICATION_LEVEL);
//		String personalId = personalRelation.getPersonalId();
//		String personalType = personalRelation.getPersonalType();
//		logger.debug("personalId >> "+personalId);
//		logger.debug("personalType >> "+personalType);
//		PersonalInfoDataM personalInfo = applicationGroup.getPersonalById(personalId);
//		ArrayList<PersonalRelationDataM> personalRelations = personalInfo.getPersonalRelations();
//		if (null == personalRelations) {
//			personalRelations = new ArrayList<PersonalRelationDataM>();
//			personalInfo.setPersonalRelations(personalRelations);
//		}
//		personalInfo.addPersonalRelation(applicationRecordId,personalType,APPLICATION_LEVEL);
//		if(PERSONAL_TYPE_APPLICANT.equals(personalInfo.getPersonalType())){
//			application.setMaincardRecordId(applicationRecordId);
//		}
//		application.setApplicationNo(GenerateUnique.generateApplicationNo(applicationNo,lifeCycle));
//		application.setFinalAppDecision(null);
//		application.setFinalAppDecisionDate(null);
//		applicationGroup.addApplications(application);
//	}
	
	private Integer getRandomNo(){
		try { 
			Random rand = new Random();
			StringBuilder randomNo = new StringBuilder();
			randomNo.append(rand.nextInt(10)).append(rand.nextInt(10)).append(rand.nextInt(10));					 
			return Integer.valueOf(randomNo.toString());	
		} catch (Exception e) {
			logger.fatal("ERROR",e);
		}
		return null;
	}

}
