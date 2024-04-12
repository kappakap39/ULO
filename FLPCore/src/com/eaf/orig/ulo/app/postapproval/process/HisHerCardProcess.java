package com.eaf.orig.ulo.app.postapproval.process;

import java.sql.SQLException;

import org.apache.log4j.Logger;

import com.eaf.core.ulo.common.dao.InfBatchServiceLocator;
import com.eaf.core.ulo.common.display.PostApprovalProcessHelper;
import com.eaf.core.ulo.common.properties.CacheControl;
import com.eaf.core.ulo.common.properties.SystemConstant;
import com.eaf.core.ulo.common.util.Util;
import com.eaf.j2ee.pattern.control.ErrorController;
import com.eaf.orig.shared.dao.OrigObjectDAO;
import com.eaf.orig.ulo.app.factory.ModuleFactory;
import com.eaf.orig.ulo.control.util.ApplicationUtil;
import com.eaf.orig.ulo.control.util.PersonalInfoUtil;
import com.eaf.orig.ulo.model.app.ApplicationDataM;
import com.eaf.orig.ulo.model.app.ApplicationGroupDataM;
import com.eaf.orig.ulo.model.app.CardDataM;
import com.eaf.orig.ulo.model.app.PersonalInfoDataM;
import com.eaf.orig.ulo.service.process.response.model.ProcessResponse;
import com.eaf.service.rest.model.ServiceResponse;

public class HisHerCardProcess extends PostApprovalProcessHelper {
	private static transient Logger logger = Logger.getLogger(HisHerCardProcess.class);
	String HIS_HER_CARD_TYPE = SystemConstant.getConstant("HIS_HER_CARD_TYPE");
	String CACHE_CARD_INFO = SystemConstant.getConstant("CACHE_CARD_INFO");
	@Override
	public ProcessResponse execute(ApplicationGroupDataM applicationGroup, Object objElement){	
		ProcessResponse processResponse = new ProcessResponse();	
		java.sql.Connection conn = null;
		try{
			InfBatchServiceLocator infBatchService = InfBatchServiceLocator.getInstance();
			conn = infBatchService.getConnection(InfBatchServiceLocator.ORIG_DB);
			conn.setAutoCommit(false);
			com.eaf.orig.ulo.app.key.dao.UniqueIDGeneratorDAO uniqueIDDAO = ModuleFactory.getUniqueCardGeneratorDAO(OrigObjectDAO.EXECUTION_MANUAL);
			ApplicationDataM application = (ApplicationDataM) objElement;	
			CardDataM cardM = application.getCard();			
			String applicationType = applicationGroup.getApplicationType();
			PersonalInfoDataM personalInfo = PersonalInfoUtil.getApplicationTypePersonalInfo(applicationGroup);
			if(ApplicationUtil.isGenerateMemberShipNo(applicationType, applicationGroup.getApplicationTemplate(), cardM.getApplicationType())){
				String membershipNo = uniqueIDDAO.getHisHerMembershipNo(cardM, conn);
				logger.debug("membershipNo : "+membershipNo);
				cardM.setMembershipNo(membershipNo);
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
		ApplicationDataM application = (ApplicationDataM) objElement;
		CardDataM cardM = application.getCard();
		String cardCode = CacheControl.getName(CACHE_CARD_INFO,"CARD_TYPE_ID",cardM.getCardType(),"CARD_CODE");
		return (null != cardM && HIS_HER_CARD_TYPE.equals(cardCode) && Util.empty(cardM.getMembershipNo()));
	}
}
