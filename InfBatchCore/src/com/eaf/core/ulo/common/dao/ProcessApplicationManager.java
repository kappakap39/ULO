package com.eaf.core.ulo.common.dao;

import java.sql.Connection;
import java.util.ArrayList;

import org.apache.log4j.Logger;

import com.eaf.core.ulo.common.cache.InfBatchProperty;
import com.eaf.core.ulo.common.postapproval.CardLinkAction;
import com.eaf.core.ulo.common.util.Util;
import com.eaf.orig.shared.dao.OrigObjectDAO;
import com.eaf.orig.shared.dao.utility.EjbUtil;
import com.eaf.orig.shared.service.OrigServiceLocator;
import com.eaf.orig.ulo.app.dao.ORIGDAOFactory;
import com.eaf.orig.ulo.app.dao.OrigApplicationDAO;
import com.eaf.orig.ulo.app.dao.OrigApplicationGroupDAO;
import com.eaf.orig.ulo.app.dao.OrigCardDAO;
import com.eaf.orig.ulo.app.dao.OrigLoanDAO;
import com.eaf.orig.ulo.app.dao.OrigPersonalInfoDAO;
import com.eaf.orig.ulo.app.dao.OrigSaleInfoDAO;
import com.eaf.orig.ulo.app.dao.exception.ApplicationException;
import com.eaf.orig.ulo.app.factory.ModuleFactory;
import com.eaf.orig.ulo.constant.MConstant;
import com.eaf.orig.ulo.model.app.ApplicationDataM;
import com.eaf.orig.ulo.model.app.ApplicationGroupDataM;
import com.eaf.orig.ulo.model.app.CardDataM;
import com.eaf.orig.ulo.model.app.LoanDataM;
import com.eaf.orig.ulo.model.app.PersonalInfoDataM;
import com.eaf.orig.ulo.model.app.SaleInfoDataM;

public class ProcessApplicationManager {
	private static transient Logger logger = Logger.getLogger(CardLinkAction.class);
	public static ArrayList<ApplicationGroupDataM> loadCardLinkApplicationGroups(ArrayList<String>  applicationGroupIds) throws ApplicationException{
		if(null!=applicationGroupIds && applicationGroupIds.size()>0){
			ArrayList<ApplicationGroupDataM> applicationGroups = new ArrayList<ApplicationGroupDataM>();
			for(String applicationGroupId : applicationGroupIds){
				applicationGroups.add(loadApplicationForCardlink(applicationGroupId));
			}
			return applicationGroups;
		}
		return null;		
	}
	public static ApplicationGroupDataM loadApplicationForCardlink(String applicationGroupId) throws ApplicationException{
		try {
			OrigApplicationGroupDAO  applicationGroupDAO = ORIGDAOFactory.getApplicationGroupDAO();
			ApplicationGroupDataM applicationGroup = applicationGroupDAO.loadSingleOrigApplicationGroupM(applicationGroupId);
			
			OrigApplicationDAO  applicationDAO = ORIGDAOFactory.getApplicationDAO();
			ArrayList<ApplicationDataM> applicationList = applicationDAO.loadApplicationsForGroup(applicationGroupId);
			if(!Util.empty(applicationList)) {
				applicationGroup.setApplications(applicationList);
			}
			OrigLoanDAO loanDAO = ORIGDAOFactory.getLoanDAO();	
			OrigCardDAO cardDAO = ORIGDAOFactory.getCardDAO();
			if(null!=applicationList && applicationList.size()>0){
				for(ApplicationDataM app : applicationList){
					ArrayList<LoanDataM>    loans = loanDAO.loadOrigLoanMGroup(app.getApplicationRecordId());
					if(!Util.empty(loans)){
						app.setLoans(loans);
						for(LoanDataM loan : loans){
							CardDataM card = cardDAO.loadOrigCardMGroup(loan.getLoanId());
							if(!Util.empty(card)){
								loan.setCard(card);
							}
						}
					}	
				}
			}
			OrigPersonalInfoDAO personalDAO = ORIGDAOFactory.getPersonalInfoDAO();
			ArrayList<PersonalInfoDataM> personList = personalDAO.loadOrigPersonalInfoM(applicationGroupId);
			if(!Util.empty(personList)) {
				applicationGroup.setPersonalInfos(personList);
			}
			OrigSaleInfoDAO saleDAO = ORIGDAOFactory.getSaleInfoDAO();
			ArrayList<SaleInfoDataM> saleInfoList = saleDAO.loadOrigSaleInfoM(applicationGroupId);
			if(!Util.empty(saleInfoList)) {
				applicationGroup.setSaleInfos(saleInfoList);
			}
			return applicationGroup;
		}catch(ApplicationException e){
			try{
				throw new Exception(e);
			}catch(Exception e1){
				logger.fatal("ERROR",e);
			}
		}
		return null;
	}
	
	public static void saveCardLinkRefApplications(ArrayList<ApplicationDataM> applications,Connection conn) throws ApplicationException{
		String SYSTEM_USER_ID = InfBatchProperty.getInfBatchConfig("SYSTEM_USER_ID");
		if(!Util.empty(applications)){
			OrigApplicationDAO  applicationDAO = ORIGDAOFactory.getApplicationDAO(SYSTEM_USER_ID);
			for(ApplicationDataM application : applications){
				applicationDAO.saveUpdateSigleOrigApplicationM(application,conn);
			}
		}	
	}
//	public static String generateCardLinkRefNo() {
//		return generateCardLinkRefNoBatch(false);
//	}
//	public static String generateCardLinkRefNo(boolean isBatch) {
//		return generateCardLinkRefNoBatch(isBatch);
//	}
//	public static String generateCardLinkRefNoBatch(boolean isBatch) {
//		String cardLinkRefNo = "";
//		try{
//			com.eaf.orig.ulo.app.key.dao.UniqueIDGeneratorDAO uniqueIDDAO = ModuleFactory.getUniqueCardGeneratorDAO();
//			cardLinkRefNo = uniqueIDDAO.getGenerateRunningNo(MConstant.RunningParamId.CARD_LINK_REF_NO, MConstant.RunningParamType.CARD_LINK,isBatch);
//		}catch(Exception e) {
//    		logger.fatal("ERROR ",e);
//    		throw new EJBException(e.getMessage());
//    	}
//		return EjbUtil.appendZero(cardLinkRefNo,7);
//	}
	public static String cardLinkRefNo() throws Exception{
		String cardLinkRefNo = "";
		Connection conn = null;
		try{
			OrigServiceLocator origService = OrigServiceLocator.getInstance();
			conn = origService.getConnection(OrigServiceLocator.ORIG_DB);  
			conn.setAutoCommit(false);
			com.eaf.orig.ulo.app.key.dao.UniqueIDGeneratorDAO uniqueIDGeneratorDAO = ModuleFactory.getUniqueCardGeneratorDAO(OrigObjectDAO.EXECUTION_MANUAL);
			cardLinkRefNo = uniqueIDGeneratorDAO.getGenerateRunningNo(MConstant.RunningParamId.CARD_LINK_REF_NO, MConstant.RunningParamType.CARD_LINK,conn);
			conn.commit();
		}catch(Exception e){
			logger.fatal("ERROR",e);
			conn.rollback();
		}finally{
			if(null!=conn){
				conn.close();
			}
		}
		return EjbUtil.appendZero(cardLinkRefNo,7);
	}
}

