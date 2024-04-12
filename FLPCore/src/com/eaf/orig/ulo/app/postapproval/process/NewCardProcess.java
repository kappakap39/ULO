package com.eaf.orig.ulo.app.postapproval.process;

import java.sql.SQLException;

import org.apache.log4j.Logger;

import com.eaf.core.ulo.common.dao.InfBatchServiceLocator;
import com.eaf.core.ulo.common.display.FormatUtil;
import com.eaf.core.ulo.common.display.PostApprovalProcessHelper;
import com.eaf.core.ulo.common.properties.SystemConstant;
import com.eaf.core.ulo.common.util.Util;
import com.eaf.core.ulo.security.encryptor.Encryptor;
import com.eaf.core.ulo.security.encryptor.EncryptorFactory;
import com.eaf.core.ulo.security.hash.Hasher;
import com.eaf.core.ulo.security.hash.HashingFactory;
import com.eaf.j2ee.pattern.control.ErrorController;
import com.eaf.orig.shared.dao.OrigObjectDAO;
import com.eaf.orig.ulo.app.factory.ModuleFactory;
import com.eaf.orig.ulo.model.app.ApplicationDataM;
import com.eaf.orig.ulo.model.app.ApplicationGroupDataM;
import com.eaf.orig.ulo.model.app.CardDataM;
import com.eaf.orig.ulo.service.process.response.model.ProcessResponse;
import com.eaf.service.rest.model.ServiceResponse;

public class NewCardProcess extends PostApprovalProcessHelper {
	private static transient Logger logger = Logger.getLogger(NewCardProcess.class);
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
			logger.debug("applicationRecordId : "+applicationRecordId);
			CardDataM cardM = application.getCard();		
			
			//Generate Card No
			com.eaf.orig.ulo.app.key.dao.UniqueIDGeneratorDAO uniqueIDDAO = ModuleFactory.getUniqueCardGeneratorDAO(OrigObjectDAO.EXECUTION_MANUAL);
			String cardNo = uniqueIDDAO.getCardNo(cardM, conn);
			logger.debug("cardNo : "+cardNo);
			cardM.setCardNo(cardNo);
			cardM.setCardNoMark(FormatUtil.maskNumber(cardNo,"######XXXXXX####"));	
			
			//Encrypt Card No for DIH
			Encryptor dihEnc = EncryptorFactory.getDIHEncryptor();
			String encryptedCardDIH = dihEnc.encrypt(cardM.getCardNo());
			logger.debug("encryptedCardDIH : "+encryptedCardDIH);	
			
			//Encrypt CardNo for KmAlert
			Encryptor kmEnc = EncryptorFactory.getKmAlertEncryptor();
			String encryptedCardKmAlert = kmEnc.encrypt(cardM.getCardNo());
			logger.debug("encryptedCardKmAlert : "+encryptedCardKmAlert);
			cardM.setCardNoEncrypted(encryptedCardKmAlert);
			
			//Hashing Card No.
			Hasher hash = HashingFactory.getSHA256Hasher();
			String hashedCardNo = hash.getHashCode(cardM.getCardNo());
			logger.debug("hashedCardNo : "+hashedCardNo);
			cardM.setHashingCardNo(hashedCardNo);
			cardM.setCardNo(encryptedCardDIH);		
			
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
