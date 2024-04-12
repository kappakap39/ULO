package com.eaf.orig.ulo.app.postapproval.process;

import java.sql.SQLException;

import org.apache.log4j.Logger;

import com.eaf.core.ulo.common.dao.InfBatchServiceLocator;
import com.eaf.core.ulo.common.display.PostApprovalProcessHelper;
import com.eaf.core.ulo.common.properties.CacheControl;
import com.eaf.core.ulo.common.properties.SystemConfig;
import com.eaf.core.ulo.common.properties.SystemConstant;
import com.eaf.core.ulo.common.util.Util;
import com.eaf.j2ee.pattern.control.ErrorController;
import com.eaf.orig.shared.dao.OrigObjectDAO;
import com.eaf.orig.ulo.app.factory.ModuleFactory;
import com.eaf.orig.ulo.constant.MConstant;
import com.eaf.orig.ulo.control.util.ApplicationUtil;
import com.eaf.orig.ulo.model.app.ApplicationDataM;
import com.eaf.orig.ulo.model.app.ApplicationGroupDataM;
import com.eaf.orig.ulo.model.app.CardDataM;
import com.eaf.orig.ulo.model.app.CardlinkCustomerDataM;
import com.eaf.orig.ulo.model.app.PersonalInfoDataM;
import com.eaf.orig.ulo.service.process.response.model.ProcessResponse;
import com.eaf.service.rest.model.ServiceResponse;

public class NewCustomerProcess extends PostApprovalProcessHelper {
	private static transient Logger logger = Logger.getLogger(NewCustomerProcess.class);
	String PRODUCT_CRADIT_CARD = SystemConstant.getConstant("PRODUCT_CRADIT_CARD");
	String PERSONAL_TYPE_APPLICANT = SystemConstant.getConstant("PERSONAL_TYPE_APPLICANT");	
	String PERSONAL_TYPE_SUPPLEMENTARY = SystemConstant.getConstant("PERSONAL_TYPE_SUPPLEMENTARY");	
	String PERSONAL_RELATION_APPLICATION_LEVEL = SystemConstant.getConstant("PERSONAL_RELATION_APPLICATION_LEVEL");
	String CACHE_ORGANIZATION = SystemConstant.getConstant("CACHE_ORGANIZATION");
	String CACHE_BUSINESS_CLASS = SystemConstant.getConstant("CACHE_BUSINESS_CLASS");
	String CC_THAIBEV = SystemConfig.getGeneralParam("CC_THAIBEV");
	String CC_CUSTOMER_PREFIX = SystemConstant.getConstant("CC_CUSTOMER_PREFIX");
	String THAIBEV_CUSTOMER_PREFIX = SystemConstant.getConstant("THAIBEV_CUSTOMER_PREFIX");
	@Override
	public ProcessResponse execute(ApplicationGroupDataM applicationGroup, Object objElement) {
		ProcessResponse processResponse = new ProcessResponse();
		java.sql.Connection conn = null;
		try{
			InfBatchServiceLocator infBatchService = InfBatchServiceLocator.getInstance();
			conn = infBatchService.getConnection(InfBatchServiceLocator.ORIG_DB);
			conn.setAutoCommit(false);
			ApplicationDataM application = (ApplicationDataM) objElement;
			String applicationRecordId = application.getApplicationRecordId();
			logger.info("applicationRecordId : "+applicationRecordId);	
			CardDataM cardM = application.getCard();
			PersonalInfoDataM personalInfo = applicationGroup.getPersonalInfoByRelation(applicationRecordId);
			if(null != personalInfo && null != cardM){
				CardlinkCustomerDataM cardLink = ApplicationUtil.getCardlinkCustomer(applicationGroup, applicationRecordId);
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
					com.eaf.orig.ulo.app.key.dao.UniqueIDGeneratorDAO uniqueIDDAO = ModuleFactory.getUniqueCardGeneratorDAO(OrigObjectDAO.EXECUTION_MANUAL);
					String cardlinkCustNo = uniqueIDDAO.getCardLinkNo(cardM, conn);
					logger.debug("cardlinkCustNo : "+cardlinkCustNo);
					cardLink.setCardlinkCustNo(cardlinkCustNo);
					cardLink.setNewCardlinkCustFlag(MConstant.FLAG.YES);				
					personalInfo.addCardLinkCustomer(cardLink);
				}
			}
			processResponse.setStatusCode(ServiceResponse.Status.SUCCESS);
			
			conn.commit();
		}catch(Exception e){
			logger.fatal("ERROR",e);
			processResponse.setStatusCode(ServiceResponse.Status.SYSTEM_EXCEPTION);
			processResponse.setErrorData(ErrorController.error(e));
			try {
				conn.rollback();
			} catch (SQLException e1) {
				e1.printStackTrace();
			}
		}finally{
			if(null!=conn){
				try {
					conn.close();
				} catch (SQLException e) {
					e.printStackTrace();
				}
			}
		}
		return processResponse;
	}
	@Override
	public boolean validate(ApplicationGroupDataM applicationGroup,Object objElement){
		String DECISION_FINAL_DECISION_APPROVE = SystemConstant.getConstant("DECISION_FINAL_DECISION_APPROVE");
		ApplicationDataM application = (ApplicationDataM) objElement;
		String applicationRecordId = application.getApplicationRecordId();
		String applicationType = application.getApplicationType();
		CardDataM cardM = application.getCard();
		String finalAppDecision = application.getFinalAppDecision();
		logger.debug("applicationRecordId : "+applicationRecordId);
		logger.debug("applicationType : "+applicationType);
		logger.debug("cardM : "+cardM);
		logger.debug("finalAppDecision : "+finalAppDecision);
		return (null != cardM && SystemConstant.lookup("CREATE_CARD_APPLICATION_TYPE",applicationType) && !Util.empty(finalAppDecision) && DECISION_FINAL_DECISION_APPROVE.equals(finalAppDecision));
	}
}
