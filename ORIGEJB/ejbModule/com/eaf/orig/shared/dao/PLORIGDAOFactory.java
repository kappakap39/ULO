package com.eaf.orig.shared.dao;

import com.eaf.orig.inf.log.dao.ServiceReqRespDAO;
import com.eaf.orig.inf.log.dao.ServiceReqRespDAOImpl;
import com.eaf.orig.profile.DAO.UserProfileDAO;
import com.eaf.orig.profile.DAO.UserProfileDAOImpl;
import com.eaf.orig.shared.dao.utility.PLOrigUnBlockDAO;
import com.eaf.orig.shared.dao.utility.PLOrigUnBlockDAOImpl;
import com.eaf.orig.ulo.pl.app.dao.*;
import com.eaf.orig.ulo.pl.app.utility.PLBPMUtilityDAO;
import com.eaf.orig.ulo.pl.app.utility.PLBPMUtilityDAOImpl;
import com.eaf.orig.ulo.pl.key.dao.PLUniqueIDGeneratorDAO;
import com.eaf.orig.ulo.pl.key.dao.PLUniqueIDGeneratorDAOImpl;

public class PLORIGDAOFactory {
	
	public static PLOrigApplicationDAO getPLOrigApplicationDAO(){
		return new PLOrigApplicationDAOImpl();
	}
	public static PLOrigPaymentMethodDAO getPLOrigPaymentMethodDAO(){
		return new PLOrigPaymentMethodDAOImpl();
	}
	public static PLOrigReferencePersonDAO getPLOrigReferencePersonDAO(){
		return new PLOrigReferencePersonDAOImpl();
	}
	public static PLOrigCashTransferDAO getPLOrigCashTransferDAO(){
		return new PLOrigCashTransferDAOImpl();
	}
	public static PLOrigSalesInfoDAO getPLOrigSalesInfoDAO(){
		return new PLOrigSalesInfoDAOImpl();
	}
	public static PLOrigDocumentCheckListDAO getPLOrigDocumentCheckListDAO(){
		return new PLOrigDocumentCheckListDAOImpl();
	}
	public static PLOrigApplicationLogDAO getPLOrigApplicationLogDAO(){
		return new PLOrigApplicationLogDAOImpl();
	}
	public static PLOrigDocumentCheckListReasonDAO getPLOrigDocumentCheckListReasonDAO(){
		return new PLOrigDocumentCheckListReasonDAOImpl();
	}
	public static PLOrigReasonLogDAO getPLOrigReasonLogDAO(){
		return new PLOrigReasonLogDAOImpl();
	}
	public static PLOrigAuditTrailDAO getPLOrigAuditTrailDAO(){
		return new PLOrigAuditTrailDAOImpl();
	}
	public static PLOrigApplicationImageDAO getPLOrigApplicationImageDAO(){
		return new PLOrigApplicationImageDAOImpl();
	}
	public static PLOrigPersonalInfoDAO getPLOrigPersonalInfoDAO(){
		return new PLOrigPersonalInfoDAOImpl();
	}
	public static PLOrigApplicationSplitedImageDAO getPLOrigApplicationSplitedImageDAO(){
		return new PLOrigApplicationSplitedImageDAOImpl();
	}
	public static PLOrigReasonDAO getPLOrigReasonDAO(){
		return new PLOrigReasonDAOImpl();
	}
	public static PLOrigAccountDAO getPLOrigAccountDAO(){
		return new PLOrigAccountDAOImpl();
	}
	public static PLOrigAccountCardDAO getPLOrigAccountCardDAO(){
		return new PLOrigAccountCardDAOImpl();
	}
	public static OrigNotePadDataMDAO getOrigNotePadDataMDAO(){
		return new OrigNotePadDataMDAOImpl();
	}
	// load project code	
	public static PLProjectCodeDAO getPLProjectCodeDAO(){
		return new PLProjectCodeDAOImpl();
	}	
	public static PLOrigPersonalAddressDAO getPLOrigPersonalAddressDAO(){
		return new PLOrigPersonalAddressDAOImpl();
	}
	public static PLOrigNCBDocumentDAO getPLOrigNCBDocumentDAO(){
		return new PLOrigNCBDocumentDAOImpl();
	}
	public static PLOrigFinancialInfoDAO getPLOrigFinancialInfoDAO(){
		return new PLOrigFinancialInfoDAOImpl();
	}
	public static PLOrigCardInformationDAO getPLOrigCardInformationDAO(){
		return new PLOrigCardInformationDAOImpl();
	}
	public static PLOrigAttachmentHistoryMDAO getPLOrigAttachmentHistoryMDAO(){
		return new PLOrigAttachmentHistoryMDAOImpl();
	}
	public static PLUniqueIDGeneratorDAO getPLUniqueIDGeneratorDAO(){
		return new PLUniqueIDGeneratorDAOImpl();
	}
	public static PLOrigImportSpecialPointDAO getPLOrigImportSpecialPointDAO(){
		return new PLOrigImportSpecialPointDAOImpl();
	}
	//load tracking
	public static PLOrigTrackingDAO getPLOrigTrackingDAO(){
		return new PLOrigTrackingDAOImpl();
	}
	
	//load countJobComplete
	public static PLOrigCountJobCompleteDAO getPLOrigCountJobCompleteDAO(){
		return new PLOrigCountJobCompleteDAOImpl();
	}
	
	//load inboxUnblock
	public static PLOrigUnBlockDAO getPLOrigUnBlockDAO(){
		return new PLOrigUnBlockDAOImpl();
	}
	
	public static PLOrigZipcodeDAO getPLOrigZipcodeDAO(){
		return new PLOrigZipcodeDAOImpl();
	}
	
	public static PLOrigUserDAO getPLOrigUserDAO(){
		return new PLOrigUserDAOImpl();
	}
	public static PLOrigCapportDAO getPLOrigCapportDAO(){
		return new PLOrigCapportDAOImpl();
	}	
	//Utility
	public static PLOrigListBoxMasterUtilityDAO getPLOrigListBoxMasterUtilityDAO(){
		return new PLOrigListBoxmasterUtilityDAOImpl();
	}
	public static PLWFLoadCentralJobAmountDAO getPLWFLoadCentralJobAmountDAO(){
		return new PLWFLoadCentralJobAmountDAOImpl();
	}
	public static PLWFLoadStatusOnJobDAO getPLWFLoadStatusOnJobDAO(){
		return new PLWFLoadStatusOnJobDAOImpl();
	}
	
	
	public static PLOrigRuleDAO getPLOrigRuleDAO(){
		return new PLOrigRuleDAOImpl();
	}
	
	
	//us user detail
	public static UserProfileDAO getUserProfileDAO(){
		return new UserProfileDAOImpl();
	}
	
	public static PLOrigEmailSMSDAO getPLOrigEmailSMSDAO(){
		return new PLOrigEmailSMSDAOImpl();
	}
	
	public static PLOrigAppUtilDAO getPLOrigAppUtilDAO(){
		return new PLOrigAppUtilDAOImpl();
	}
	
	public static PLOrigImportCreditLineDataDAO getPLOrigImportCreditLineDataDAO(){
		return new PLOrigImportCreditLineDataDAOImpl();
	}
	
	public static PLORIGSchedulerDAO getPLORIGSchedulerDAO(){
		return new PLORIGSchedulerDAOImpl();
	}
	//BPMUtility DAO
	public static PLBPMUtilityDAO getPLBPMUtilityDAO(){
		return new PLBPMUtilityDAOImpl();
	}
	//load us_user_detail
	public static PLORIGUsUserDetailDAO getPLORIGUsUserDetailDAO(){
		return new PLORIGUsUserDetailDAOImpl();
	}
	public static PLOrigBundleALDAO getPLOrigBundleALDAO(){
		return new PLOrigBundleALDAOImpl();
	}
	public static PLOrigBundleCCDAO getPLOrigBundleCCDAO(){
		return new PLOrigBundleCCDAOImpl();
	}
	public static PLOrigBundleHLDAO getPLOrigBundleHLDAO(){
		return new PLOrigBundleHLDAOImpl();
	}
	public static PLOrigBundleSMEDAO getPLOrigBundleSMEDAO(){
		return new PLOrigBundleSMEDAOImpl();
	}
	public static PLOrigKYCDAO getPLOrigKYCDAO(){
		return new PLOrigKYCDAOImpl();
	}
	public static PLOrigAccountLogDAO getPLorigAccountLogDAO(){
		return new PLOrigAccountLogDAOImpl();
	}
	
	public static ORIGBusinessClassDAO getORIGBusinessClassDAO(){
		return new ORIGBusinessClassDAOImpl();
	}
	
	public static PLORIGGeneralParamValue getPLORIGGeneralParamValueWithBussclass(){
		return new PLORIGGeneralParamValueImpl();
	}
	
	public static PLORIGGeneralParamValue getPLORIGGeneralParamValue(){
		return new PLORIGGeneralParamValueImpl();
	}	
	//service req resp
	public static ServiceReqRespDAO getServiceReqRespDAO(){
		return new ServiceReqRespDAOImpl();
	}
	
	public static PLORIGBranchGroupValue getBranchGroupByBranchNo(){
		return new PLORIGBranchGroupValueImpl();
	}
	
	public static PLORIGBranchGroupValue getChannelGroupValue(){
		return new PLORIGBranchGroupValueImpl();
	}
	
	public static PLORIGTargetPointValue getCurrentTargetPoint(){
		return new PLORIGTargetPointValueImpl();
	}
	
	public static PLORIGTargetPointValue getFinishTargetPoint(){
		return new PLORIGTargetPointValueImpl();
	}
	public static ORIGManualImportDAO getORIGManualImportDAO(){
		return new ORIGManualImportDAOImpl();
	}
	public static ReportParamMDAO getReportParamMDAO(){
		return new ReportParamMDAOImpl();
	}
	public static GetAllNameInformationByRoleDAO GetallNameInformationByRoleDAO(){
		return new GetAllNameInformationByRoleDAOImpl();
	}
	public static PLOrigApplicationPointDAO getPLOrigApplicationPointDAO(){
		return new PLOrigApplicationPointDAOImpl();
	}
	public static OrigMasterGenParamDAO getMasterGenParamDAO(){
		return new OrigMasterGenParamDAOImpl();
	}
	public static PLOrigNCBDocumentHistoryDAO getPLOrigNCBDocumentHistoryDAO(){
		return new PLOrigNCBDocumentHistoryDAOImpl();
	}
	public static ApplicationInfoDAO getApplicationInfoDAO(){
		return new ApplicationDAOImpl();
	}
	public static Bot5XDAO getBot5xDAO(){
		return new Bot5XDAOImpl();
	}
}
