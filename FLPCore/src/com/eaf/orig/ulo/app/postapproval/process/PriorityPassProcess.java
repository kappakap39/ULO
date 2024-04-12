package com.eaf.orig.ulo.app.postapproval.process;

import java.math.BigDecimal;
import java.sql.SQLException;

import org.apache.log4j.Logger;

import com.eaf.core.ulo.common.dao.InfBatchServiceLocator;
import com.eaf.core.ulo.common.display.PostApprovalProcessHelper;
import com.eaf.j2ee.pattern.control.ErrorController;
import com.eaf.orig.shared.dao.OrigObjectDAO;
import com.eaf.orig.ulo.app.factory.ModuleFactory;
import com.eaf.orig.ulo.control.util.ApplicationUtil;
import com.eaf.orig.ulo.model.app.ApplicationDataM;
import com.eaf.orig.ulo.model.app.ApplicationGroupDataM;
import com.eaf.orig.ulo.model.app.CardDataM;
import com.eaf.orig.ulo.model.app.WisdomDataM;
import com.eaf.orig.ulo.service.process.response.model.ProcessResponse;
import com.eaf.service.rest.model.ServiceResponse;

public class PriorityPassProcess extends PostApprovalProcessHelper {
	private static transient Logger logger = Logger.getLogger(PriorityPassProcess.class);
	@Override
	public ProcessResponse execute(ApplicationGroupDataM applicationGroup, Object objElement) {
		ProcessResponse processResponse = new ProcessResponse();
		java.sql.Connection conn = null;
		try{
			InfBatchServiceLocator infBatchService = InfBatchServiceLocator.getInstance();
			conn = infBatchService.getConnection(InfBatchServiceLocator.ORIG_DB);
			conn.setAutoCommit(false);
			ApplicationDataM application = (ApplicationDataM) objElement;
			CardDataM cardM = application.getCard();
			String applicationType = applicationGroup.getApplicationType();
			if(null != cardM){
				WisdomDataM wisdomM = cardM.getWisdom();
				if(null != wisdomM){
					//Generate Priority pass no.
					if(ApplicationUtil.isGenerateMemberShipNo(applicationType, applicationGroup.getApplicationTemplate(), cardM.getApplicationType())){
						com.eaf.orig.ulo.app.key.dao.UniqueIDGeneratorDAO uniqueIDDAO = ModuleFactory.getUniqueCardGeneratorDAO(OrigObjectDAO.EXECUTION_MANUAL);
						String priorityPassNo = uniqueIDDAO.getPriorityPassNo(cardM, conn);
						logger.debug("priorityPassNo : "+priorityPassNo);
						wisdomM.setPriorityPassNo(priorityPassNo);
					}
				}
			}
			processResponse.setStatusCode(ServiceResponse.Status.SUCCESS);
			
			conn.commit();
		}catch(Exception e){
			logger.fatal("ERROR ",e);
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
		if(null != cardM){
			WisdomDataM wisdomM = cardM.getWisdom();
//			return (null != wisdomM && MConstant.FLAG.YES.equals(wisdomM.getPriorityPassFlag()));
			return (null!=wisdomM&&null!=wisdomM.getNoPriorityPass()&&wisdomM.getNoPriorityPass().compareTo(BigDecimal.ZERO)>0);
		}
		return false;
	}
}
