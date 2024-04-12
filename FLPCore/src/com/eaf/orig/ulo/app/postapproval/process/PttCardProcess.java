package com.eaf.orig.ulo.app.postapproval.process;

import java.sql.SQLException;

import org.apache.log4j.Logger;

import com.eaf.core.ulo.common.dao.InfBatchServiceLocator;
import com.eaf.core.ulo.common.display.PostApprovalProcessHelper;
import com.eaf.core.ulo.common.properties.SystemConstant;
import com.eaf.core.ulo.common.util.Util;
import com.eaf.j2ee.pattern.control.ErrorController;
import com.eaf.orig.shared.dao.OrigObjectDAO;
import com.eaf.orig.ulo.app.factory.ModuleFactory;
import com.eaf.orig.ulo.control.util.ApplicationUtil;
import com.eaf.orig.ulo.model.app.ApplicationDataM;
import com.eaf.orig.ulo.model.app.ApplicationGroupDataM;
import com.eaf.orig.ulo.model.app.CardDataM;
import com.eaf.orig.ulo.service.process.response.model.ProcessResponse;
import com.eaf.service.rest.model.ServiceResponse;

public class PttCardProcess extends PostApprovalProcessHelper{
	private static transient Logger logger = Logger.getLogger(PttCardProcess.class);
	String PTT_CARD_TYPE = SystemConstant.getConstant("PTT_CARD_TYPE");
	String PTT_PARAM_TYPE = SystemConstant.getConstant("PTT_PARAM_TYPE");
	String CACHE_CARD_INFO = SystemConstant.getConstant("CACHE_CARD_INFO");
	@Override
	public ProcessResponse execute(ApplicationGroupDataM applicationGroup,Object objElement){
		ProcessResponse processResponse = new ProcessResponse();	
		java.sql.Connection conn = null;
		try{
			InfBatchServiceLocator infBatchService = InfBatchServiceLocator.getInstance();
			conn = infBatchService.getConnection(InfBatchServiceLocator.ORIG_DB);
			conn.setAutoCommit(false);
			ApplicationDataM application = (ApplicationDataM) objElement;	
			CardDataM card = application.getCard();	
			String applicationType = applicationGroup.getApplicationType();
			if(ApplicationUtil.isGenerateMemberShipNo(applicationType, applicationGroup.getApplicationTemplate(), card.getApplicationType())){
				com.eaf.orig.ulo.app.key.dao.UniqueIDGeneratorDAO uniqueIDDAO = ModuleFactory.getUniqueCardGeneratorDAO(OrigObjectDAO.EXECUTION_MANUAL);
				String membershipNo = uniqueIDDAO.getGenerateRunningNoStack(card.getCardType(),PTT_PARAM_TYPE, conn);
				logger.debug("membershipNo : "+membershipNo);
				card.setMembershipNo(membershipNo);
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
		CardDataM card = application.getCard();
		logger.debug("CardType : "+card.getCardType());
		logger.debug("MembershipNo : "+card.getMembershipNo());
		logger.debug("finalAppDecision : "+application.getFinalAppDecision());
		return (null != card && PTT_CARD_TYPE.equals(card.getCardType()) && Util.empty(card.getMembershipNo()) && !Util.empty(application.getFinalAppDecision()) && DECISION_FINAL_DECISION_APPROVE.equals(application.getFinalAppDecision()));
	}
}
