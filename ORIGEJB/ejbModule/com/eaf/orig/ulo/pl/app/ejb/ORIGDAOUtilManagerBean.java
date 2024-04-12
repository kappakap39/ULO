package com.eaf.orig.ulo.pl.app.ejb;

import java.math.BigDecimal;
import java.sql.Connection;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.TreeMap;
import java.util.Vector;

import javax.ejb.EJBException;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;

import org.apache.log4j.Logger;

import com.ava.bpm.model.response.WorkflowResponse;
import com.ava.bpm.model.todolist.UserWorkQueueDataM;
import com.eaf.cache.data.CacheDataM;
import com.eaf.orig.cache.properties.IntSchemeCacheProperties;
import com.eaf.orig.cache.properties.ORIGCacheDataM;
import com.eaf.orig.inf.log.dao.ServiceReqRespDAO;
import com.eaf.orig.inf.log.dao.exception.ServiceReqRespException;
import com.eaf.orig.inf.log.model.ServiceReqRespDataM;
import com.eaf.orig.master.shared.model.FicoM;
import com.eaf.orig.master.shared.model.GeneralParamM;
import com.eaf.orig.master.shared.model.OrigPolicyVersionDataM;
import com.eaf.orig.master.shared.model.ReportParam;
import com.eaf.orig.master.shared.model.ScoreCharacterM;
import com.eaf.orig.master.shared.model.ScoreM;
import com.eaf.orig.menu.DAO.MenuMDAO;
import com.eaf.orig.menu.DAO.exception.MenuMException;
import com.eaf.orig.profile.DAO.exception.UserProfileException;
import com.eaf.orig.profile.model.UserDetailM;
import com.eaf.orig.scheduler.dao.Scheduler.SchedulerDAO;
import com.eaf.orig.shared.constant.OrigConstant;
import com.eaf.orig.shared.dao.EjbUtilException;
import com.eaf.orig.shared.dao.ORIGDAOFactory;
import com.eaf.orig.shared.dao.OrigMasterDAOFactory;
import com.eaf.orig.shared.dao.OrigMasterGenParamDAO;
import com.eaf.orig.shared.dao.PLORIGDAOFactory;
import com.eaf.orig.shared.dao.ValueListDAO;
import com.eaf.orig.shared.dao.exceptions.OrigApplicationMException;
import com.eaf.orig.shared.dao.exceptions.OrigAttachmentHistoryMException;
import com.eaf.orig.shared.dao.exceptions.OrigLoanMException;
import com.eaf.orig.shared.dao.exceptions.OrigMasterMException;
import com.eaf.orig.shared.dao.exceptions.OrigVehicleMException;
import com.eaf.orig.shared.dao.exceptions.SearchApplicationUtilDAOException;
import com.eaf.orig.shared.dao.utility.PLOrigUnBlockDAO;
import com.eaf.orig.shared.dao.utility.UtilityDAO;
import com.eaf.orig.shared.key.dao.exception.UniqueIDGeneratorException;
import com.eaf.orig.shared.model.AttachmentHistoryDataM;
import com.eaf.orig.shared.model.EmailTemplateMasterM;
import com.eaf.orig.shared.model.NotePadDataM;
import com.eaf.orig.shared.model.ProcessMenuM;
import com.eaf.orig.shared.model.SearchAddressDataM;
import com.eaf.orig.shared.model.ValueListM;
import com.eaf.orig.shared.search.SearchM;
import com.eaf.orig.shared.service.OrigServiceLocator;
import com.eaf.orig.shared.util.OrigUtil;
import com.eaf.orig.shared.xrules.dao.XRulesNCBDAO;
import com.eaf.orig.shared.xrules.dao.XRulesScoringLogDAO;
import com.eaf.orig.shared.xrules.dao.exception.XRulesScoringException;
import com.eaf.orig.ulo.pl.app.dao.ORIGWorkflowDAO;
import com.eaf.orig.ulo.pl.app.dao.OrigEnquiryLogDAO;
import com.eaf.orig.ulo.pl.app.dao.PLOrigAccountCardDAO;
import com.eaf.orig.ulo.pl.app.dao.PLOrigAccountDAO;
import com.eaf.orig.ulo.pl.app.dao.PLOrigAddressDAO;
import com.eaf.orig.ulo.pl.app.dao.PLOrigAddressDAOImpl;
import com.eaf.orig.ulo.pl.app.dao.PLOrigApplicationDAO;
import com.eaf.orig.ulo.pl.app.dao.PLOrigApplicationLogDAO;
import com.eaf.orig.ulo.pl.app.dao.PLOrigAuditTrailDAO;
import com.eaf.orig.ulo.pl.app.dao.PLOrigCountJobCompleteDAO;
import com.eaf.orig.ulo.pl.app.dao.PLOrigEmailSMSDAO;
import com.eaf.orig.ulo.pl.app.dao.PLOrigNCBDocumentHistoryDAO;
import com.eaf.orig.ulo.pl.app.dao.PLOrigReasonDAO;
import com.eaf.orig.ulo.pl.app.dao.PLOrigReasonLogDAO;
import com.eaf.orig.ulo.pl.app.dao.PLOrigTrackingDAO;
import com.eaf.orig.ulo.pl.app.dao.PLOrigZipcodeDAO;
import com.eaf.orig.ulo.pl.app.dao.PLOrigZipcodeDAOImpl;
import com.eaf.orig.ulo.pl.app.dao.PLProjectCodeDAO;
import com.eaf.orig.ulo.pl.app.dao.WFLoadCentralJob;
import com.eaf.orig.ulo.pl.app.dao.exception.PLOrigApplicationException;
import com.eaf.orig.ulo.pl.app.dao.exception.PLWFDAOException;
import com.eaf.orig.ulo.pl.app.utility.LoadListBoxMasterUtility;
import com.eaf.orig.ulo.pl.model.app.CapportGroupDataM;
import com.eaf.orig.ulo.pl.model.app.EnquiryLogDataM;
import com.eaf.orig.ulo.pl.model.app.ORIGWorkflowDataM;
import com.eaf.orig.ulo.pl.model.app.OverrideCapportDataM;
import com.eaf.orig.ulo.pl.model.app.PLAccountCardDataM;
import com.eaf.orig.ulo.pl.model.app.PLAccountDataM;
import com.eaf.orig.ulo.pl.model.app.PLAccountLogDataM;
import com.eaf.orig.ulo.pl.model.app.PLApplicationDataM;
import com.eaf.orig.ulo.pl.model.app.PLApplicationImageSplitDataM;
import com.eaf.orig.ulo.pl.model.app.PLAttachmentHistoryDataM;
import com.eaf.orig.ulo.pl.model.app.PLAuditTrailDataM;
import com.eaf.orig.ulo.pl.model.app.PLImportSpecialPointDataM;
import com.eaf.orig.ulo.pl.model.app.PLInboxUnBlockDataM;
import com.eaf.orig.ulo.pl.model.app.PLNCBDocDataM;
import com.eaf.orig.ulo.pl.model.app.PLPersonalInfoDataM;
import com.eaf.orig.ulo.pl.model.app.PLProjectCodeDataM;
import com.eaf.orig.ulo.pl.model.app.PLReasonDataM;
import com.eaf.orig.ulo.pl.model.app.PLSaleInfoDataM;
import com.eaf.orig.ulo.pl.model.app.PLTrackingDataM;
import com.eaf.orig.ulo.pl.model.app.RulesDetailsDataM;
import com.eaf.orig.ulo.pl.model.app.TrackingDataM;
import com.eaf.orig.ulo.pl.model.app.UserPointDataM;
import com.eaf.orig.ulo.pl.model.app.search.PLSearchHistoryActionLogDataM;
import com.eaf.orig.ulo.pl.util.ejb.ORIGDAOUtilLocal;
import com.eaf.orig.ulo.pl.util.ejb.ORIGDAOUtilRemote;
import com.eaf.xrules.shared.model.XRulesScoringLogDataM;
import com.eaf.xrules.ulo.pl.model.app.rule.PLXRulesFUVerificationDataM;
import com.eaf.xrules.ulo.pl.model.app.rule.PLXRulesNCBVerificationDataM;
import com.orig.bpm.proxy.BpmProxyService;
import com.orig.bpm.ulo.model.WorkflowDataM;
import com.orig.bpm.ulo.proxy.BpmWorkflow;
//import com.eaf.orig.ulo.pl.app.utility.PLOrigEmailUtil;
//import com.eaf.orig.ulo.pl.app.utility.PLOrigSMSUtil;

/**
 * Session Bean implementation class ORIGDAOUtilManagerBean
 */
//@Stateless(mappedName="ORIGDAOUtilManagerBean") 
public class ORIGDAOUtilManagerBean implements ORIGDAOUtilLocal, ORIGDAOUtilRemote{	
	private static Logger log = Logger.getLogger(ORIGDAOUtilManagerBean.class);
    public ORIGDAOUtilManagerBean() {
       super();
    }
	@Override
	public GeneralParamM loadModelOrigMasterGenParamM(String genParamCde,String busID){	
		try {
			OrigMasterGenParamDAO genParamDAO = OrigMasterDAOFactory.getOrigMasterGenParamDAO();
			return genParamDAO.loadModelOrigMasterGenParamM(OrigConstant.ParamCode.MAIL_CONTENT_UW, OrigConstant.BUSINESS_CLASS_AL);
		} catch (OrigApplicationMException e){
			log.fatal(e.getLocalizedMessage());
		}
		return new GeneralParamM();
	}
	@Override
	public TreeMap getProcessMenuByID(Vector allmenuID) {
		MenuMDAO menudao = (MenuMDAO)ORIGDAOFactory.getMenuMDAO();	
		try {
			return menudao.getProcessMenuByID(allmenuID);
		} catch (MenuMException e) {
			log.fatal(e.getLocalizedMessage());
		}
		return new TreeMap();
	}
	@Override
	public int getLastScoring(String applicationRecordId){
		try {
			XRulesScoringLogDAO xruleScoreLogDAO = com.eaf.orig.shared.dao.ORIGDAOFactory.getXRulesScoringLogDAO();
			return xruleScoreLogDAO.getLastScoring(applicationRecordId);
		} catch (XRulesScoringException e) {
			log.fatal(e.getLocalizedMessage());
		}
		return 0;
	}
	@Override
	public Vector getImageAttachedFiles(String requestId) {
		 try {
			 UtilityDAO utilDAO = ORIGDAOFactory.getUtilityDAO();
			return utilDAO.getImageAttachedFiles(requestId);
		} catch (EjbUtilException e) {
			log.fatal(e.getLocalizedMessage());
		}
		return new Vector();
	}
	@Override
	public ValueListM getResult(ValueListM valueListM) {
		try {
			ValueListDAO valueListDAO = ORIGDAOFactory.getValueListDAO();
			return valueListDAO.getResult(valueListM);
		} catch (OrigApplicationMException e) {
			log.fatal(e.getLocalizedMessage());
		}
		return new ValueListM();
	}
	@Override
	public ValueListM getResult2(ValueListM valueListM) {		
		try {
			ValueListDAO valueListDAO = ORIGDAOFactory.getValueListDAO();
			return valueListDAO.getResult2(valueListM);
		} catch (OrigApplicationMException e) {
			log.fatal(e.getLocalizedMessage());
		}
		return new ValueListM();
	}
	@Override
	public Vector getAppNumberByGroupID(String groupID, String appRecordID){		
		try {
			UtilityDAO dao = ORIGDAOFactory.getUtilityDAO();
			return dao.getAppNumberByGroupID(groupID,appRecordID);
		} catch (EjbUtilException e) {
			log.fatal(e.getLocalizedMessage());
		}
		return new Vector();
	}
	@Override
	public String loadWorkingTime(String queueTimeOutID){		
		try {
			SchedulerDAO schedulerDAO = ORIGDAOFactory.getSchedulerDAO();
			return schedulerDAO.loadWorkingTime(queueTimeOutID);
		} catch (Exception e) {
			log.fatal(e.getLocalizedMessage());
		}
		return null;
	}
	@Override
	public Vector getRekeyFields(){		
		try {
			UtilityDAO utilityDAO = ORIGDAOFactory.getUtilityDAO();
			return utilityDAO.getRekeyFields();
		} catch (EjbUtilException e) {
			log.fatal(e.getLocalizedMessage());
		}
		return new Vector();
	}
	@Override
	public Vector<ORIGCacheDataM> loadByChoice_No(String Field_ID){		
		try {
			LoadListBoxMasterUtility listBox = new LoadListBoxMasterUtility();
			return (Vector)listBox.loadByChoice_No(Field_ID);
		} catch (PLOrigApplicationException e){
			log.fatal(e.getLocalizedMessage());
		}
		return new Vector<ORIGCacheDataM>();
	}
	@Override
	public int LoadCentralJob(String ATID, String ATID2){		
		try {
			WFLoadCentralJob centralJob = new WFLoadCentralJob();
			return centralJob.LoadCentralJob(ATID, ATID2) ;
		} catch (PLWFDAOException e) {
			log.fatal(e.getLocalizedMessage());
		}
		return 0;
	}
	@Override
	public Vector<PLTrackingDataM> queryTracking(String owner) {
		// TODO Auto-generated method stub
		return null;
	}
	@Override
	public int countUser(String role, String firstName, String lastName, String logOn, String onJob){		
		try {
			PLOrigTrackingDAO plOrigTrackingDAO = PLORIGDAOFactory.getPLOrigTrackingDAO();
			return plOrigTrackingDAO.countUser(role, firstName, lastName, logOn, onJob);
		} catch (PLOrigApplicationException e) {
			log.fatal(e.getLocalizedMessage());
		}
		return 0;
	}
	@Override
	public int countLogOnStatus(String role, String firstName, String lastName,	String logOn, String onJob){		
		try {
			PLOrigTrackingDAO plOrigTrackingDAO = PLORIGDAOFactory.getPLOrigTrackingDAO();
			return plOrigTrackingDAO.countLogOnStatus(role, firstName, lastName, logOn, onJob);
		} catch (PLOrigApplicationException e) {
			log.fatal(e.getLocalizedMessage());
		}
		return 0;
	}
	@Override
	public int countOnjobStatus(String role, String firstName, String lastName,	String logOn, String onJob){		
		try {
			PLOrigTrackingDAO plOrigTrackingDAO = PLORIGDAOFactory.getPLOrigTrackingDAO();
			return plOrigTrackingDAO.countOnjobStatus(role, firstName, lastName, logOn, onJob);
		} catch (PLOrigApplicationException e) {
			log.fatal(e.getLocalizedMessage());
		}
		return 0;
	}
	@Override
	public int countPreviousJob(String role, String firstName, String lastName,	String logOn, String onJob) {		
		try {
			PLOrigTrackingDAO plOrigTrackingDAO = PLORIGDAOFactory.getPLOrigTrackingDAO();
			return plOrigTrackingDAO.countPreviousJob(role, firstName, lastName, logOn, onJob);
		} catch (PLOrigApplicationException e) {
			log.fatal(e.getLocalizedMessage());
		}
		return 0;
	}
	@Override
	public int countPreviousJobEdit(String role, String firstName,	String lastName, String logOn, String onJob) {		
		try {
			PLOrigTrackingDAO plOrigTrackingDAO = PLORIGDAOFactory.getPLOrigTrackingDAO();
			return plOrigTrackingDAO.countPreviousJobEdit(role, firstName, lastName, logOn, onJob);
		} catch (PLOrigApplicationException e){
			log.fatal(e.getLocalizedMessage());
		}
		return 0;
	}
	@Override
	public int countNewJob(String role, String firstName, String lastName,String logOn, String onJob) {		
		try {
			PLOrigTrackingDAO plOrigTrackingDAO = PLORIGDAOFactory.getPLOrigTrackingDAO();
			return plOrigTrackingDAO.countNewJob(role, firstName, lastName, logOn, onJob);
		} catch (PLOrigApplicationException e) {
			log.fatal(e.getLocalizedMessage());
		}
		return 0;
	}
	@Override
	public int countSubmitJob(String role, String firstName, String lastName,String logOn, String onJob) {		
		try{
			PLOrigTrackingDAO origTrackingDAO = PLORIGDAOFactory.getPLOrigTrackingDAO();
			return  origTrackingDAO.countSubmitJob(role, firstName, lastName, logOn, onJob);
		}catch(PLOrigApplicationException e){
			log.fatal(e.getLocalizedMessage());
		}
		return 0;
	}
	@Override
	public int countSubmitEditJob(String role, String firstName,String lastName, String logOn, String onJob) {		
		try {
			PLOrigTrackingDAO plOrigTrackingDAO = PLORIGDAOFactory.getPLOrigTrackingDAO();
			return plOrigTrackingDAO.countSubmitEditJob(role, firstName, lastName, logOn, onJob);
		} catch (PLOrigApplicationException e) {
			log.fatal(e.getLocalizedMessage());
		}
		return 0;
	}
	@Override
	public int countRemainJob(String role, String firstName,String lastName, String logOn, String onJob) {		
		try {
			PLOrigTrackingDAO plOrigTrackingDAO = PLORIGDAOFactory.getPLOrigTrackingDAO();
			return plOrigTrackingDAO.countRemainJob(role, firstName, lastName, logOn, onJob);
		} catch (PLOrigApplicationException e) {
			// TODO Auto-generated catch block
			log.fatal(e.getLocalizedMessage());
		}
		return 0;
	}
	@Override
	public int countAutoQueue(String role ,String roleWf){		
		try{
			PLOrigTrackingDAO origTrackingDAO = PLORIGDAOFactory.getPLOrigTrackingDAO();
			return origTrackingDAO.countAutoQueue(role,roleWf);
		}catch(PLOrigApplicationException e){		
			log.fatal(e.getLocalizedMessage());
		}
		return 0;
	}
	@Override
	public int countInboxAutoQueueSup(String menuId, String role ,String roleWf){		
		try{
			PLOrigTrackingDAO origTrackingDAO = PLORIGDAOFactory.getPLOrigTrackingDAO();
			return origTrackingDAO.countInboxAutoQueueSup(menuId, role,roleWf);
		}catch(PLOrigApplicationException e){		
			log.fatal(e.getLocalizedMessage());
		}
		return 0;
	}
	@Override
	public Vector<PLTrackingDataM> trackingAction(String owner, String role) {
		try {
			PLOrigTrackingDAO origTrackingDAO = PLORIGDAOFactory.getPLOrigTrackingDAO();
			return origTrackingDAO.trackingAction(owner, role);
		}catch(PLOrigApplicationException e){
			log.fatal(e.getLocalizedMessage());
		}
		return new Vector<PLTrackingDataM>();
	}
	@Override
	public Vector<String[]> renderOnjobFlag (String user) {
		try {
			PLOrigTrackingDAO origTrackingDAO = PLORIGDAOFactory.getPLOrigTrackingDAO();
			return origTrackingDAO.loadRenderOnjobFlag(user);
		} catch (PLOrigApplicationException e) {
			log.fatal(e.getLocalizedMessage());
		}
		return new Vector<String[]>();
	}
	@Override
	public String Zipcode(String Tambol) {		
		try {
			PLOrigZipcodeDAO zipcode = new PLOrigZipcodeDAOImpl();
			return zipcode.Zipcode(Tambol);
		} catch (PLOrigApplicationException e) {
			log.fatal(e.getLocalizedMessage());
		}
		return null;
	}
	@Override
	public PLProjectCodeDataM Loadtable(String projectcode) {		
		try {
			PLProjectCodeDAO projectCodeDAO = PLORIGDAOFactory.getPLProjectCodeDAO();
			return projectCodeDAO.Loadtable(projectcode);
		} catch (PLOrigApplicationException e){
			log.fatal(e.getLocalizedMessage());
		}
		return null;
	}
	@Override
	public int countJobComplete(String userName) {		
		try{
			PLOrigCountJobCompleteDAO countDAO = PLORIGDAOFactory.getPLOrigCountJobCompleteDAO();
			return countDAO.countJobComplete(userName);
		}catch(PLOrigApplicationException e){
			log.fatal(e.getLocalizedMessage());
		}
		return 0;
	}
	@Override
	public PLAccountCardDataM loadAccountCardByCardNo(String cardNo) {
		try{
			PLOrigAccountCardDAO accountDAO = PLORIGDAOFactory.getPLOrigAccountCardDAO();
			return accountDAO.loadAccountCardByCardNo(cardNo);
		}catch(PLOrigApplicationException e){
			log.fatal(e.getLocalizedMessage());
		}
		return new PLAccountCardDataM();
	}
	
	public PLAccountDataM loadAccountNoCardData(String accountId){
		try {
			return PLORIGDAOFactory.getPLOrigAccountCardDAO().loadAccountNoCardData(accountId);
		} catch (PLOrigApplicationException e) {
			log.fatal(e.getLocalizedMessage());
		}
		return new PLAccountDataM();
	}
	
	public boolean sendEmailWithEmailTemplate(String emailTemplate,	PLApplicationDataM appM) {
//		PLOrigEmailUtil emailUtil = new PLOrigEmailUtil();
//		try {
//			return emailUtil.sendEmailWithEmailTemplate(emailTemplate,appM);
//		} catch (Exception e) {
//			log.fatal(e.getLocalizedMessage());
//		}
		return false;
	}
	
	public boolean sendEmailFollowDocToBranch(String emailTemplate, PLApplicationDataM applicationM, String ccTo){		
//		try{
//			PLOrigEmailUtil emailUtil = new PLOrigEmailUtil();
//			return emailUtil.sendEmailFollowDocToBranch(emailTemplate,applicationM,ccTo);
//		}catch(Exception e){
//			log.fatal("ERROR "+e.getLocalizedMessage());
//			throw new EJBException(e.getMessage());
//		}
		return false;
	}

	
	@Override
	public boolean sendSMSWithSMSCode(String smsCode, PLApplicationDataM applicationM){
//		PLOrigSMSUtil smsUtil = new PLOrigSMSUtil();
//		try{
//			return smsUtil.sendSMSWithSMSCode(smsCode, applicationM);
//		}catch(Exception e){
//			log.fatal("ERROR ",e);
//			throw new EJBException(e.getMessage());
//		}
		return false;
	}
	
	@Override
	public String getAppRecordIdByCardNo(String cardNo) {
		try {
			return PLORIGDAOFactory.getPLOrigAccountCardDAO().getAppRecordIdByCardNo(cardNo);
		} catch (PLOrigApplicationException e){			
			log.fatal(e.getLocalizedMessage());
		}
		return null;
	}
	/*@Override
	public PLApplicationDataM loadApplication_IncreaseDecrease(String appRecId, PLApplicationDataM currentAppM) {
		try {
			return PLORIGDAOFactory.getPLOrigApplicationDAO().loadApplication_IncreaseDecrease(appRecId, currentAppM);
		} catch (PLOrigApplicationException e){
			log.fatal(e.getLocalizedMessage());
		}
		return new PLApplicationDataM();
	}*/
	@Override
	public Vector<PLInboxUnBlockDataM> loadSubTableUnBlock(String IDNO,	String role, String userName) {		
		try {
			PLOrigUnBlockDAO origUnBlockDAO = PLORIGDAOFactory.getPLOrigUnBlockDAO();
			return origUnBlockDAO.loadSubTableUnBlock(IDNO, role, userName);
		} catch (PLOrigApplicationException e) {
			log.fatal(e.getLocalizedMessage());
		}
		return new Vector<PLInboxUnBlockDataM>();
	}
	@Override
	public Vector<String[]> loadCBConsent(String dateFrom, String dateTo) {		
		try{
			PLOrigApplicationDAO applicationDAO = PLORIGDAOFactory.getPLOrigApplicationDAO();
			return applicationDAO.loadCBConsent(dateFrom, dateTo);
		}catch(PLOrigApplicationException e){
			log.fatal(e.getLocalizedMessage());
		}
		return new Vector<String[]>();
	}
	@Override
	public Vector<PLImportSpecialPointDataM> saveTableOrig_Special_Point(Vector<PLImportSpecialPointDataM> importSpePointVect, Date dataDate) {
		try{
			return PLORIGDAOFactory.getPLOrigImportSpecialPointDAO().saveTableOrig_Special_Point(importSpePointVect, (java.sql.Date) dataDate);
		}catch(PLOrigApplicationException e){
			log.fatal(e.getLocalizedMessage());
		}
		return new Vector<PLImportSpecialPointDataM>();
	}
	@Override
	public Vector<String> loadDisplayReasonLog(String appLogId) {		
		try{
			PLOrigReasonLogDAO origReasonLogDAO = PLORIGDAOFactory.getPLOrigReasonLogDAO();
			return origReasonLogDAO.loadDisplayReasonLog(appLogId);
		}catch(PLOrigApplicationException e){
			log.fatal(e.getLocalizedMessage());
		}
		return new Vector<String>();
	}
	@Override
	public Vector<String> loadReasonEnquiry(String appRecId) {		
		try {
			PLOrigReasonDAO origReasonDAO = PLORIGDAOFactory.getPLOrigReasonDAO();
			return origReasonDAO.loadReasonEnquiry(appRecId);
		} catch (PLOrigApplicationException e) {
			log.fatal(e.getLocalizedMessage());
		}
		return new Vector<String>();
	}
	
	@Override
	public Vector<String> loadReasonCallCenterTracking(String appRecId, String jobState) {		
		try {
			PLOrigReasonDAO origReasonDAO = PLORIGDAOFactory.getPLOrigReasonDAO();
			return origReasonDAO.loadReasonCallCenterTracking(appRecId, jobState);
		} catch (PLOrigApplicationException e) {
			log.fatal(e.getLocalizedMessage());
		}
		return new Vector<String>();
	}
	
	@Override
	public String loadStatusOnJob(String userName, String menuId) {
		try{
			WorkflowResponse response = new WorkflowResponse();
			BpmWorkflow bpmWf = BpmProxyService.getBpmWorkflow();
			response = bpmWf.WorkQueueBox(menuId, userName, this.getWorkFlowConnection());			
			UserWorkQueueDataM userWorkQueueM = response.getUserWorkQueueM();			
			if(userWorkQueueM == null)
				userWorkQueueM = new UserWorkQueueDataM();
			return userWorkQueueM.getJobOnFlag();
		}catch (Exception e) {
			log.fatal("Error ",e);
		}
		return "";
	}
	@Override
	public PLApplicationDataM loadAppInFo(String appRecID) {
		PLOrigApplicationDAO applicationDAO = PLORIGDAOFactory.getPLOrigApplicationDAO();
		try{
			return applicationDAO.loadAppInFo(appRecID);
		}catch(PLOrigApplicationException e){
			log.fatal(e.getLocalizedMessage());
		}
		return new PLApplicationDataM();
	}
	@Override
	public PLApplicationDataM loadOrigApplication(String appId) {
		PLOrigApplicationDAO applicationDAO = PLORIGDAOFactory.getPLOrigApplicationDAO();
		try {
			return applicationDAO.loadOrigApplication(appId);
		} catch (PLOrigApplicationException e){
			log.fatal(e.getLocalizedMessage());
		}
		return new PLApplicationDataM();
	}
	@Override
	public PLAccountDataM loadAccount(String accId) {
		try{
			PLOrigAccountDAO accountDAO = PLORIGDAOFactory.getPLOrigAccountDAO();
			return accountDAO.loadAccount(accId);
		}catch(PLOrigApplicationException e){
			log.fatal(e.getLocalizedMessage());
		}
		return new PLAccountDataM();
	}
	@Override
	public void saveUpdateModelOrigAttachmentHistoryM(PLAttachmentHistoryDataM prmPLAttachmentHistoryDataM) {
		try {
			PLORIGDAOFactory.getPLOrigAttachmentHistoryMDAO().saveUpdateModelOrigAttachmentHistoryM(prmPLAttachmentHistoryDataM);
		} catch (PLOrigApplicationException e){
			log.fatal(e.getLocalizedMessage());
		}
	}
	@Override
	public PLPersonalInfoDataM LoadPersonalInfoModel(String idNo,String customerType) {
		try {
			return PLORIGDAOFactory.getPLOrigPersonalInfoDAO().LoadPersonalInfoModel(idNo, customerType);
		} catch (PLOrigApplicationException e) {
			log.fatal(e.getLocalizedMessage());
		}
		return new PLPersonalInfoDataM();
	}
	@Override
	public PLAttachmentHistoryDataM loadModelOrigAttachmentHistoryMFromAttachId(String attachmentId) {
		try {
			return PLORIGDAOFactory.getPLOrigAttachmentHistoryMDAO().loadModelOrigAttachmentHistoryMFromAttachId(attachmentId);
		} catch (PLOrigApplicationException e) {
			log.fatal(e.getLocalizedMessage());
		}
		return new PLAttachmentHistoryDataM();
	}
	@Override
	public void deleteNotInKeyTableORIG_ATTACHMENT_HISTORY(	Vector<PLAttachmentHistoryDataM> attachVect, String appRecordID) {
		 try {
			PLORIGDAOFactory.getPLOrigAttachmentHistoryMDAO().deleteNotInKeyTableORIG_ATTACHMENT_HISTORY(attachVect, appRecordID);
		} catch (PLOrigApplicationException e) {
			log.fatal(e.getLocalizedMessage());
		}		
	}
	@Override
	public void deleteTableORIG_ATTACHMENT_HISTORY_ByAttachId(String attachId) {
		try {
			PLORIGDAOFactory.getPLOrigAttachmentHistoryMDAO().deleteTableORIG_ATTACHMENT_HISTORY_ByAttachId(attachId);
		} catch (PLOrigApplicationException e) {
			log.fatal(e.getLocalizedMessage());
		}
	}
	
	@Override
	public Vector<PLSearchHistoryActionLogDataM> loadHistoryActionLog(String appRecId) {		
		try{
			PLOrigApplicationLogDAO applicationLogDAO = PLORIGDAOFactory.getPLOrigApplicationLogDAO();
			return applicationLogDAO.loadHistoryActionLog(appRecId);
		}catch(PLOrigApplicationException e){
			log.fatal(e.getLocalizedMessage());
		}
		return new Vector<PLSearchHistoryActionLogDataM>();
	}
	@Override
	public String getPreviousAppStatus(String appId) {
		try {
			return PLORIGDAOFactory.getPLOrigApplicationLogDAO().getPreviousAppStatus(appId);
		} catch (PLOrigApplicationException e) {
			log.fatal(e.getLocalizedMessage());
		}
		return null;
	}
	@Override
	public String getDLA(BigDecimal creditLimit,String jobType) throws Exception {
		try {
			return PLORIGDAOFactory.getPLOrigAppUtilDAO().getDLA(creditLimit,jobType);
		} catch (EjbUtilException e) {
			log.fatal(e.getLocalizedMessage());
			throw new Exception(e.getLocalizedMessage());
		}
	}
	@Override
	public UserPointDataM getUserPointDetails(String userName, ProcessMenuM menuM) {
		try {
			return PLORIGDAOFactory.getPLOrigUserDAO().getUserPointDetails(userName, menuM);
		} catch (PLOrigApplicationException e) {
			log.fatal(e.getLocalizedMessage());
		}
		return new UserPointDataM();
	}
	@Override
	public Vector<PLApplicationImageSplitDataM> loadImageSplitFromAppRecId(String appRecId) {
		try {
			return PLORIGDAOFactory.getPLOrigApplicationSplitedImageDAO().loadImageSplitFromAppRecId(appRecId);
		} catch (PLOrigApplicationException e) {
			log.fatal(e.getLocalizedMessage());
		}
		return new Vector<PLApplicationImageSplitDataM>();
	}
	@Override
	public CapportGroupDataM getCapportGroupDetails(String capportNo) {
		try {
			return PLORIGDAOFactory.getPLOrigCapportDAO().getCapportGroupDetails(capportNo);
		} catch (PLOrigApplicationException e) {
			log.fatal(e.getLocalizedMessage());
		}
		return new CapportGroupDataM();
	}
	public OverrideCapportDataM getOverrideCapport(String appRecordID){
		try {
			return PLORIGDAOFactory.getPLOrigCapportDAO().getOverrideCapport(appRecordID);
		} catch (PLOrigApplicationException e) {
			log.fatal(e.getLocalizedMessage());
		}
		return new OverrideCapportDataM();
	}
	@Override
	public BigDecimal getLendingLimit(String username, String busID) {		
		try {
			 UtilityDAO utilDAO = ORIGDAOFactory.getUtilityDAO();
			return utilDAO.getLendingLimit(username,busID);
		} catch (EjbUtilException e){
			log.fatal(e.getLocalizedMessage());
		}
		return null;
	}
	@Override
	public ValueListM getResult_master(ValueListM valueListM, String dataValue) {
		try {
			ValueListDAO valueListDAO = ORIGDAOFactory.getValueListDAO();
			return valueListDAO.getResult_master(valueListM, dataValue);
		} catch (OrigApplicationMException e) {
			log.fatal(e.getLocalizedMessage());
		}
		return new ValueListM();
	}
	@Override
	public ValueListM getResult_master2(ValueListM valueListM, String dataValue) {
		try {
			ValueListDAO valueListDAO = ORIGDAOFactory.getValueListDAO();
			return valueListDAO.getResult_master2(valueListM, dataValue);
		} catch (OrigApplicationMException e) {
			log.fatal(e.getLocalizedMessage());
		}
		return new ValueListM();
	}
	@Override
	public Vector<PLAuditTrailDataM> loadOrigAuditTrailByRoleAppRecId(String appRecId) {		
		try{
			PLOrigAuditTrailDAO auditTrailDAO = PLORIGDAOFactory.getPLOrigAuditTrailDAO();
			return auditTrailDAO.loadOrigAuditTrailByRoleAppRecId(appRecId);
		}catch(PLOrigApplicationException e){
			log.fatal(e.getLocalizedMessage());
		}
		return new Vector<PLAuditTrailDataM>();
	}
	@Override
	public String loandCarModelDesc(String carModel, String carBrand) {
		return ORIGDAOFactory.getOrigVehicleMDAO().loandCarModelDesc(carModel, carBrand);
	}
	@Override
	public int getMaxScoringSequence(XRulesScoringLogDataM prmXRulesScoringLogDataM) {		 
		 try {
			 XRulesScoringLogDAO xRulesScoringLogDAO = ORIGDAOFactory.getXRulesScoringLogDAO();
			return xRulesScoringLogDAO.getMaxScoringSequence(prmXRulesScoringLogDataM);
		} catch (XRulesScoringException e) {
			log.fatal(e.getLocalizedMessage());
		}
		return 0;
	}
	@Override
	public void createXRulesScoringLogM(XRulesScoringLogDataM prmXRulesScoringLogDataM) {
		try {
			ORIGDAOFactory.getXRulesScoringLogDAO().createXRulesScoringLogM(prmXRulesScoringLogDataM);
		} catch (XRulesScoringException e){
			log.fatal(e.getLocalizedMessage());
		}
	}
	@Override
	public BigDecimal getUserLendingLimit(String userName, String busClass) {
		try {
			return PLORIGDAOFactory.getPLOrigUserDAO().getUserLendingLimit(userName, busClass);
		} catch (PLOrigApplicationException e) {
			log.fatal(e.getLocalizedMessage());
		}
		return null;
	}
	@Override
	public Vector<RulesDetailsDataM> getRulesDetailsConfig(String busClass) {
		try{
			return  PLORIGDAOFactory.getPLOrigRuleDAO().getRulesDetailsConfig(busClass);
		}catch(PLOrigApplicationException e){
			log.fatal(e.getLocalizedMessage());
		}
		return new Vector<RulesDetailsDataM>();
	}
	@Override
	public Date getDateExtendWorkingDay(Date startCalDate, int extendDay) {
		try {
			return (Date) PLORIGDAOFactory.getPLOrigAppUtilDAO().getDateExtendWorkingDay(startCalDate, extendDay);
		} catch (PLOrigApplicationException e) {
			log.fatal(e.getLocalizedMessage());
		}
		return null;
	}
	@Override
	public String getDefaultCycleCut(String businessClass){
		try {
			return (String) PLORIGDAOFactory.getPLOrigAppUtilDAO().getDefaultCycleCut(businessClass);
		} catch (PLOrigApplicationException e) {
			log.fatal(e.getLocalizedMessage());
		}
		return null;
	}
	@Override
	public ArrayList getCostFee(String origType) {
		try {
			return  ORIGDAOFactory.getOrigLoanMDAO().getCostFee(origType);
		} catch (OrigLoanMException e) {
			log.fatal(e.getLocalizedMessage());
		}
		return new ArrayList();
	}
	@Override
	public void saveUpdateModelOrigAttachmentHistoryM(	AttachmentHistoryDataM prmAttachmentHistoryDataM) {
		try {
			ORIGDAOFactory.getOrigAttachmentHistoryMDAO().saveUpdateModelOrigAttachmentHistoryM(prmAttachmentHistoryDataM);
		} catch (OrigAttachmentHistoryMException e) {
			log.fatal(e.getLocalizedMessage());
		}
	}
	@Override
	public IntSchemeCacheProperties getIntSchemeForCode(String schemeCode) {		
		try {
			UtilityDAO dao = ORIGDAOFactory.getUtilityDAO();
			return dao.getIntSchemeForCode(schemeCode);
		} catch (EjbUtilException e) {
			log.fatal(e.getLocalizedMessage());
		}
		return new IntSchemeCacheProperties();
	}
	@Override
	public Vector<CacheDataM> getAutoCompleteCacheDataM(String lookUp , String codeParam ,String username ,String brand){
		UtilityDAO dao = ORIGDAOFactory.getUtilityDAO();
		Vector<CacheDataM> vData = null;
		try {
			if(lookUp.equals("areaCode")) {				
				vData = dao.getAreaCodesLike(codeParam, username);
			} else if(lookUp.equals("clientGroup")) {
				vData = dao.getClientGroupsLike(codeParam);
			} else if(lookUp.equals("mktCode")) {
				vData = dao.getMarketingCodesLike(codeParam);
			} else if(lookUp.equals("carCategory")) {
				vData = dao.getCarCategorysLike(codeParam);
			} else if(lookUp.equals("carBrand")) {
				vData = dao.getCarBrandsLike(codeParam);
			} else if(lookUp.equals("carModel")) {
				vData = dao.getCarModelsLike(codeParam, brand);
			} else if(lookUp.equals("carColor")) {
				vData = dao.getCarColorsLike(codeParam);
			} else if(lookUp.equals("carLicenseType")) {
				vData = dao.getCarLicenseTypesLike(codeParam);
			} else if(lookUp.equals("carProvince")) {
				vData = dao.getCarProvincesLike(codeParam);
			}
		} catch (EjbUtilException e) {
			log.fatal(e.getLocalizedMessage());
		}
		return vData;
	}
	@Override
	public Vector loadModelOrigPolicyRulesExceptionDataM(String policyVersion) {
		try {
			return ORIGDAOFactory.getOrigPolicyRulesExceptionDAO().loadModelOrigPolicyRulesExceptionDataM(policyVersion);
		} catch (OrigMasterMException e) {
			log.fatal(e.getLocalizedMessage());
		}
		return new Vector();
	}
	@Override
	public String getMainApplicatinRecordIdByApplicaionNo(String applicationNo) {
		try {
			return ORIGDAOFactory.getUtilityDAO().getMainApplicatinRecordIdByApplicaionNo(applicationNo);
		} catch (EjbUtilException e) {
			log.fatal(e.getLocalizedMessage());
		}
		return null;
	}
	@Override
	public void beforeCallReportCAApp(java.sql.Date fromDate, java.sql.Date toDate) {		
		try {
			UtilityDAO  utilDAO = ORIGDAOFactory.getUtilityDAO();
			utilDAO.beforeCallReportCAApp(fromDate,toDate);
		} catch (EjbUtilException e) {
			log.fatal(e.getLocalizedMessage());
		} 		
	}
	@Override
	public void beforeCallReportCADecision(String fromOfficer,String toOfficer, String fromCACode, String toCACode,java.sql.Date fromDate, java.sql.Date toDate) {
		try {
			UtilityDAO  utilDAO = ORIGDAOFactory.getUtilityDAO();
			utilDAO.beforeCallReportCADecision(fromOfficer,toOfficer,fromCACode,toCACode,fromDate,toDate);
		} catch (EjbUtilException e) {
			log.fatal(e.getLocalizedMessage());
		}
	}
	@Override
	public void beforeCallReportCAOveride(String fromOfficer, String toOfficer,	String fromCACode, String toCACode, java.sql.Date fromDate, java.sql.Date toDate) {
		try {
			UtilityDAO  utilDAO = ORIGDAOFactory.getUtilityDAO();
			utilDAO.beforeCallReportCAOveride(fromOfficer,toOfficer,fromCACode,toCACode,fromDate,toDate);
		} catch (EjbUtilException e) {
			log.fatal(e.getLocalizedMessage());
		}	
	}
	@Override
	public Vector loadVehicleDetailByCustomerId(String idNo) {
		try {
			return ORIGDAOFactory.getOrigVehicleMDAO().loadVehicleDetailByCustomerId(idNo);
		} catch (OrigVehicleMException e) {
			log.fatal(e.getLocalizedMessage());
		}
		return new Vector();
	}
	@Override
	public Vector loadMenus(Vector vtMenuID, Vector vtRows) {		
		try {
			MenuMDAO menudao = (MenuMDAO)ORIGDAOFactory.getMenuMDAO();	
			return menudao.loadMenus(vtMenuID,vtRows);
		} catch (MenuMException e) {
			log.fatal(e.getLocalizedMessage());
		}
		return new Vector();
	}
	@Override
	public Vector getAllUserNameCMR() {
		try {
			return OrigMasterDAOFactory.getOrigMasterUserTeamDAO().getAllUserNameCMR();
		} catch (OrigApplicationMException e) {
			log.fatal(e.getLocalizedMessage());
		}
		return new Vector();
	}
	@Override
	public Vector getCharTypeSpecificVect(String scoreCode, String charCode) {
		try {
			return OrigMasterDAOFactory.getOrigMasterAppScoreDAO().getCharTypeSpecificVect(scoreCode, charCode);
		} catch (OrigApplicationMException e) {
			log.fatal(e.getLocalizedMessage());
		}
		return new Vector();
	}
	@Override
	public Vector SearchAllBranch() {
		try {
			return OrigMasterDAOFactory.getOrigMasterUserDetailDAO().SearchAllBranch();
		} catch (OrigApplicationMException e) {
			log.fatal(e.getLocalizedMessage());
		}
		return new Vector();
	}
	@Override
	public Vector SearchAllGroupname() {
		try {
			return OrigMasterDAOFactory.getOrigMasterUserDetailDAO().SearchAllGroupname();
		} catch (OrigApplicationMException e) {
			log.fatal(e.getLocalizedMessage());
		}
		return new Vector();
	}
	@Override
	public Vector SearchAllExcept() {
		try {
			return OrigMasterDAOFactory.getOrigMasterUserDetailDAO().SearchAllExcept();
		} catch (OrigApplicationMException e) {
			log.fatal(e.getLocalizedMessage());
		}
		return new Vector();
	}
	@Override
	public Vector SearchAllBusinessClass() {
		try {
			return OrigMasterDAOFactory.getOrigMasterListBoxDAO().SearchAllBusinessClass();
		} catch (OrigApplicationMException e) {
			log.fatal(e.getLocalizedMessage());
		}
		return new Vector();
	}
	@Override
	public Vector getScoreFacterType(String cusType) {
		try {
			return OrigMasterDAOFactory.getOrigMasterAppScoreDAO().getScoreFacterType(cusType);
		} catch (OrigApplicationMException e) {
			log.fatal(e.getLocalizedMessage());
		}
		return new Vector();
	}
	@Override
	public String getCusThaiDesc(String cusType) {
		try {
			return OrigMasterDAOFactory.getOrigMasterAppScoreDAO().getCusThaiDesc(cusType);
		} catch (OrigApplicationMException e) {
			log.fatal(e.getLocalizedMessage());
		}
		return null;
	}
	@Override
	public HashMap getOriginalScoreTypeHash(String cusType) {
		try {
			return OrigMasterDAOFactory.getOrigMasterAppScoreDAO().getOriginalScoreTypeHash(cusType);
		} catch (OrigApplicationMException e) {
			log.fatal(e.getLocalizedMessage());
		}
		return new HashMap();
	}
	@Override
	public ScoreM getOriginalScoreM(String cusType) {
		try {
			return OrigMasterDAOFactory.getOrigMasterAppScoreDAO().getOriginalScoreM(cusType);
		} catch (OrigApplicationMException e) {
			log.fatal(e.getLocalizedMessage());
		}
		return new ScoreM();
	}
	@Override
	public HashMap getOriginalCharTypeHash(Vector scoreCodeVect) {
		try {
			return OrigMasterDAOFactory.getOrigMasterAppScoreDAO().getOriginalCharTypeHash(scoreCodeVect);
		} catch (OrigApplicationMException e) {
			log.fatal(e.getLocalizedMessage());
		}
		return new HashMap();
	}
	@Override
	public Vector selectCharM(String scoreCode, String charCode) {
		try {
			return OrigMasterDAOFactory.getOrigMasterAppScoreDAO().selectCharM(scoreCode, charCode);
		} catch (OrigApplicationMException e) {
			log.fatal(e.getLocalizedMessage());
		}
		return new Vector();
	}
	@Override
	public Vector SearchGroupnameByDesc(String groupName) {
		try {
			return OrigMasterDAOFactory.getOrigMasterUserDetailDAO().SearchGroupnameByDesc(groupName);
		} catch (OrigApplicationMException e) {
			log.fatal(e.getLocalizedMessage());
		}
		return new Vector();
	}
	@Override
	public Vector SearchExceptByDesc(String exceptDesc) {
		try {
			return OrigMasterDAOFactory.getOrigMasterUserDetailDAO().SearchExceptByDesc(exceptDesc);
		} catch (OrigApplicationMException e) {
			log.fatal(e.getLocalizedMessage());
		}
		return new Vector();
	}
	@Override
	public Vector SearchBusinessClassByDesc(String busClassDesc) {
		try {
			return OrigMasterDAOFactory.getOrigMasterListBoxDAO().SearchBusinessClassByDesc(busClassDesc);
		} catch (OrigApplicationMException e) {
			log.fatal(e.getLocalizedMessage());
		}
		return new Vector();
	}
	@Override
	public Vector getScoreTypeVect(String cusType) {
		try {
			return OrigMasterDAOFactory.getOrigMasterAppScoreDAO().getScoreTypeVect(cusType);
		} catch (OrigApplicationMException e) {
			log.fatal(e.getLocalizedMessage());
		}
		return new Vector();
	}
	@Override
	public HashMap getCharTypeHashMapByScoreCode(Vector scoreTypeVect) {
		try {
			return OrigMasterDAOFactory.getOrigMasterAppScoreDAO().getCharTypeHashMapByScoreCode(scoreTypeVect);
		} catch (OrigApplicationMException e) {
			log.fatal(e.getLocalizedMessage());
		}
		return new HashMap();
	}
	@Override
	public int getMaxScoreSeq(String custype) {
		try {
			return OrigMasterDAOFactory.getOrigMasterAppScoreDAO().getMaxScoreSeq(custype);
		} catch (OrigApplicationMException e) {
			log.fatal(e.getLocalizedMessage());
		}
		return 0;
	}
	@Override
	public FicoM getFicoLastInfo() {
		try {
			return OrigMasterDAOFactory.getOrigMasterFICODAO().getFicoLastInfo();
		} catch (OrigApplicationMException e) {
			log.fatal(e.getLocalizedMessage());
		}
		return new FicoM();
	}
	@Override
	public Vector getCharTypeVect(String scoreCode) {
		try {
			return OrigMasterDAOFactory.getOrigMasterAppScoreDAO().getCharTypeVect(scoreCode);
		} catch (OrigApplicationMException e) {
			log.fatal(e.getLocalizedMessage());
		}
		return new Vector();
	}
	@Override
	public ScoreCharacterM getNewSpecDesc(ScoreCharacterM characterM) {
		try {
			return OrigMasterDAOFactory.getOrigMasterAppScoreDAO().getNewSpecDesc(characterM);
		} catch (OrigApplicationMException e) {
			log.fatal(e.getLocalizedMessage());
		}
		return new ScoreCharacterM();
	}
	@Override
	public OrigPolicyVersionDataM loadModelOrigPolicyVersionDataM(String policyVersion) {
		try {
			return ORIGDAOFactory.getOrigPolicyVersionMDAO().loadModelOrigPolicyVersionDataM(policyVersion);
		} catch (OrigMasterMException e) {
			log.fatal(e.getLocalizedMessage());
		}
		return new OrigPolicyVersionDataM();
	}
	@Override
	public Vector loadPolicyRules() {
		try {
			return OrigMasterDAOFactory.getOrigMasterPolicyRulesDAO().loadPolicyRules();
		} catch (OrigApplicationMException e) {
			log.fatal(e.getLocalizedMessage());
		}
		return new Vector();
	}
	@Override
	public UserDetailM getUserProfile(String userName) {
		try {
			return ORIGDAOFactory.getUserProfileDAO().getUserProfile(userName);
		} catch (UserProfileException e) {
			log.fatal(e.getLocalizedMessage());
		}
		return new UserDetailM();
	}
	@Override
	public Vector getSearchImage(String searchType, String requestId,
							String dealer, String channelCode, String userName,
									String faxInDateFrom, String faxInDateTo, String nric,
											String firstName, String lastName, String sortBy, String appNo,
													String mainCustomerType, String dealerFaxNo) {
		try {
			return ORIGDAOFactory.getSearchApplicationUtilDAO().getSearchImage(searchType, requestId, dealer, channelCode, userName, faxInDateFrom, faxInDateTo, nric, firstName, lastName, sortBy, appNo, mainCustomerType, dealerFaxNo);
		} catch (SearchApplicationUtilDAOException e) {
			log.fatal(e.getLocalizedMessage());
		}
		return new Vector();
	}
	@Override
	public String getUniqByName(String systemType) {
		try {
			return ORIGDAOFactory.getUniqueIDGeneratorDAO2().getUniqByName(systemType);
		} catch (UniqueIDGeneratorException e) {
			log.fatal(e.getLocalizedMessage());
		}
		return null;
	}
	@Override
	public String getORIGCacheDisplayNameFormDB(String code, String type,String param1) {
		UtilityDAO utilityDAO = ORIGDAOFactory.getUtilityDAO();
    	try{
	    	if(OrigConstant.CacheName.CACHE_NAME_BRANCH.equals(type)){
	    		return utilityDAO.getBranchDescription(code, param1);
	    	}else if(OrigConstant.CacheName.CACHE_NAME_CAR_MODEL.equals(type)){
	    		return utilityDAO.getModelDescription(code, param1);
			}else if(OrigConstant.CacheName.CACHE_NAME_AREA.equals(type)){
				return utilityDAO.getAreaDescription(code, param1);
			}
    	}catch(Exception e){
    	}
    	return null;
	}
	@Override
	public String getORIGCacheDisplayNameFormDB(String code, String type) {
		UtilityDAO utilityDAO = ORIGDAOFactory.getUtilityDAO();
		String description = null;		
    	try{
	    	if(OrigConstant.CacheName.CACHE_NAME_BRANCH.equals(type)){
	    		description = utilityDAO.getBranchDescription(code);
	    	}else if(OrigConstant.CacheName.CACHE_NAME_CAR.equals(type)){
	    		description = utilityDAO.getCarModelDescription(code);
	    	}else if(OrigConstant.CacheName.CACHE_NAME_DEALER.equals(type)){
	    		description = utilityDAO.getDealerDescription(code);
	    	}else if(OrigConstant.CacheName.CACHE_NAME_SELLER.equals(type)){
	    		description = utilityDAO.getSellerDescription(code);
	    	}else if(OrigConstant.CacheName.CACHE_NAME_ZIPCODE.equals(type)){
	    		description = utilityDAO.getZipCodeDescription(code);
	    	}else if(OrigConstant.CacheName.CACHE_NAME_PROVINCE.equals(type)){
	    		description = utilityDAO.getProvinceDescription(code);
	    	}else if(OrigConstant.CacheName.CACHE_NAME_AMPHUR.equals(type)){
	    		description = utilityDAO.getAmphurDescription(code);
	    	}else if(OrigConstant.CacheName.CACHE_NAME_TAMBOL.equals(type)){
	    		description = utilityDAO.getTambolDescription(code);
	    	}else if(OrigConstant.CacheName.CACHE_NAME_SALEINFO.equals(type)){
	    		description = utilityDAO.getSellerName(code);
	    	}else if(OrigConstant.CacheName.CACHE_NAME_BRANCHINFO.equals(type)){
	    		description = utilityDAO.getRefBranchName(code);
	    	}else if(OrigConstant.CacheName.CACHE_NAME_FILE_CATEGORY.equals(type)){
	    		description = utilityDAO.getFileCategoryDesc(code);
	    	}	    		    	
    	}catch(Exception e){
    	}
    	return description;
	}
	@Override
	public Vector SearchBranchByDesc(String branchDesc) {
		try {
			return OrigMasterDAOFactory.getOrigMasterUserDetailDAO().SearchBranchByDesc(branchDesc);
		} catch (OrigApplicationMException e) {
			log.fatal(e.getLocalizedMessage());
		}
		return new Vector();
	}
	@Override
    public String getUserTimeRemain(String role,String appRecId){
    	try {
			return PLORIGDAOFactory.getPLBPMUtilityDAO().getUserTimeRemain(role, appRecId);
		} catch (EjbUtilException e) {
			log.fatal(e.getLocalizedMessage());
		}
		return "";
    }
	@Override
    public String getSystemTimeRemain(String bussClass,String appRecId){    
    	try {
			return PLORIGDAOFactory.getPLBPMUtilityDAO().getSystemTimeRemain(bussClass, appRecId);
		} catch (EjbUtilException e) {			 
			log.fatal(e.getLocalizedMessage());
		}
		return "";
		
    }
	@Override
	public String generateApplicationNo(PLApplicationDataM appM) {
		// TODO Auto-generated method stub
		try{
			return PLORIGDAOFactory.getPLUniqueIDGeneratorDAO().getApplicationNo(appM);
		}catch (Exception e){
			log.fatal(e.getLocalizedMessage());
		}
		return "";
	}
	@Override
	public Vector<PLPersonalInfoDataM> loadOrigPersonalInfo(String appRecId) {
		try {
			return PLORIGDAOFactory.getPLOrigPersonalInfoDAO().loadOrigPersonalInfo(appRecId);
		} catch (PLOrigApplicationException e) {
			log.fatal(e.getLocalizedMessage());
		}
		return new Vector<PLPersonalInfoDataM>();
	}
	
	@Override
	public Vector<String[]> loadAuditTrailField (String SubFormID) {
		try {
			return PLORIGDAOFactory.getPLOrigApplicationDAO().loadAuditTrailField(SubFormID);
		} catch (PLOrigApplicationException e) {
			log.fatal(e.getLocalizedMessage());
		}
		return new Vector<String[]>();
	}
	@Override
	public String getAddressDesc(String type, String numberVal, String optionalCase) {
		try {
			PLOrigAddressDAO plOrigAdd = new PLOrigAddressDAOImpl();
			if(!OrigUtil.isEmptyString(type)&&type.equalsIgnoreCase("S")){ return plOrigAdd.getSubDistrict(numberVal, optionalCase);}
			else if(!OrigUtil.isEmptyString(type)&&type.equalsIgnoreCase("D")){ return plOrigAdd.getDistrict(numberVal, optionalCase);}
			else if(!OrigUtil.isEmptyString(type)&&type.equalsIgnoreCase("P")){return plOrigAdd.getProvince(numberVal);}
			else if(!OrigUtil.isEmptyString(type)&&type.equalsIgnoreCase("Z")){return plOrigAdd.getZipcode(numberVal);}
		} catch (PLOrigApplicationException e) {
			log.fatal(e.getLocalizedMessage());
		}
		return null;
	}
	@Override
	public String getAddressID(String type, String descVal, String optionalID) {
		try {
			PLOrigAddressDAO plOrigAdd = new PLOrigAddressDAOImpl();
			if(!OrigUtil.isEmptyString(type)&&type.equalsIgnoreCase(OrigConstant.Address.SUB_DISTRIC)){ return plOrigAdd.getSubDistrictID(descVal, optionalID);}
			else if(!OrigUtil.isEmptyString(type)&&type.equalsIgnoreCase(OrigConstant.Address.DISTRIC)){ return plOrigAdd.getDistrictID(descVal, optionalID);}
			else if(!OrigUtil.isEmptyString(type)&&type.equalsIgnoreCase(OrigConstant.Address.PROVINCE)){return plOrigAdd.getProvinceID(descVal);}
		} catch (PLOrigApplicationException e) {
			log.fatal(e.getLocalizedMessage());
		}
		return null;
	}
	@Override
	public Vector<String> loadTrackingDoclistName(String appRecId) {
		try {
			return PLORIGDAOFactory.getPLOrigDocumentCheckListDAO().loadTrackingDoclistName(appRecId);
		} catch (PLOrigApplicationException e) {
			log.fatal(e.getLocalizedMessage());
		}
		return new Vector<String>();
	}
	private Connection getWorkFlowConnection() {
		try {
			OrigServiceLocator origService = OrigServiceLocator.getInstance();
			return origService.getConnection(OrigServiceLocator.WORKFLOW_DB);
		} catch (Exception e) {
			log.fatal("Connection is null"+e.getMessage());
		}
		return null;
	}
	@Override
	public int GetTotalJobDoneCurrentDate(String userName, String roleId,String tdid) {
		try{
			BpmWorkflow bpmWf = BpmProxyService.getBpmWorkflow();
			WorkflowDataM wfM = new WorkflowDataM();
				wfM.setUserID(userName);
				wfM.setUserRole(roleId);
				wfM.setTdID(tdid);
			WorkflowResponse reponse = bpmWf.GetTotalJobDoneCurrentDate(wfM, this.getWorkFlowConnection());
			return reponse.getTotolJobDone();
		}catch (Exception e) {
			log.fatal("GetTotalJobDoneCurrentDate Error "+e.getMessage());
		}		
		return 0;
	}
	
	@Override
	public PLAccountCardDataM loadAccountCardByAppNo(String appNo) {
		try {
			return PLORIGDAOFactory.getPLOrigAccountCardDAO().loadAccountCardByAppNo(appNo);
		} catch (PLOrigApplicationException e) {
			log.fatal(e.getLocalizedMessage());
			return null;
		}
	}
	
	@Override
	public String loadBlockFlag(String appRecID){		
		try{
			PLOrigApplicationDAO applicationDAO = PLORIGDAOFactory.getPLOrigApplicationDAO();
			return applicationDAO.loadBlockFlag(appRecID);
		}catch(Exception e){
			log.fatal("Exception >>"+e.getLocalizedMessage());
			throw new EJBException(e.getMessage());
		}
		
	}
	
	public HashMap<String, String> getBusGroupByBusClass(String busClass){
		try {
			return PLORIGDAOFactory.getORIGBusinessClassDAO().getBusGroupByBusClass(busClass);
		} catch (PLOrigApplicationException e) {
			log.fatal(e.getLocalizedMessage());
			return null;
		}
	}
	public HashMap<String, String> getBusClassByBusGroup(String busGroup){
		try{
			return PLORIGDAOFactory.getORIGBusinessClassDAO().getBusClassByBusGroup(busGroup);
		}catch(PLOrigApplicationException e){
			log.fatal(e.getLocalizedMessage());
			return null;
		}
	}
	@Override
	/*20121213 Sankom  Add Parameter Credit Limit for CR*/
	public String getJobTypeByPolicy(String policyRules,BigDecimal creditLimit) {
		try {
			return PLORIGDAOFactory.getPLOrigAppUtilDAO().getDLAPolicy(policyRules,creditLimit);
		}  catch (EjbUtilException e) {
			log.fatal(e.getLocalizedMessage());
			e.printStackTrace();
		}
		return null;
	}
	@Override
	public String getJobID(String appRecId) {
		try {
			return PLORIGDAOFactory.getPLOrigApplicationDAO().loadJobID(appRecId);
		} catch (PLOrigApplicationException e) {
			log.fatal(e.getLocalizedMessage());
			e.printStackTrace();
		}
		return null;
	}

	@Override
	public GeneralParamM getGeneralParamvalueByBussClass(String paramCode,	String bussClass) {
		try {
			return PLORIGDAOFactory.getPLORIGGeneralParamValueWithBussclass().getGeneralParamvalueWithBussclass(paramCode, bussClass);
		}  catch (PLOrigApplicationException e) {
			log.fatal(e.getLocalizedMessage());
			e.printStackTrace();
		}
		return null;
	}
	
	@Override
	public GeneralParamM getGeneralParamvalue(String paramCode) {
		try{
			return PLORIGDAOFactory.getPLORIGGeneralParamValue().getGeneralParamvalue(paramCode);
		}catch(Exception e){
			log.fatal("Exception ",e);		
		}
		return null;
	}
	
	@Override
	@TransactionAttribute(TransactionAttributeType.REQUIRES_NEW)
	public void saveLogServiceReqResp(ServiceReqRespDataM servReqRespM) {
		try {
			PLORIGDAOFactory.getServiceReqRespDAO().InsertServiceReqResp(servReqRespM);
		} catch (ServiceReqRespException e){
			log.fatal(e.getLocalizedMessage());
		}
	}
	@Override
	public String getBranchGroupByBranchNo(String BranchNo) {
		try {
			return PLORIGDAOFactory.getBranchGroupByBranchNo().getBranchGroupValue(BranchNo);
		} catch (Exception e){
			log.fatal(e.getLocalizedMessage());
		}
		return null;
	}
	
	@Override
	public String getChannelGroupByChannel(String ChannelNo) {
		try {
			return PLORIGDAOFactory.getBranchGroupByBranchNo().getChannelGroupValue(ChannelNo);
		} catch (Exception e){
			log.fatal(e.getLocalizedMessage());
		}
		return null;
	}
	
	@Override
	public String getTargetPointCurrentFromUser(String User) {
		try {
			return PLORIGDAOFactory.getCurrentTargetPoint().getCurrentTargetPointValue(User);
		} catch (Exception e){
			log.fatal(e.getLocalizedMessage());
		}
		return null;
	}
	@Override
	public String getTargetPointFinishFromRole(String User, String Role) {
		try {
			return PLORIGDAOFactory.getCurrentTargetPoint().getFinishTargetPointValue(User, Role);
		} catch (Exception e){
			log.fatal(e.getLocalizedMessage());
		}
		return null;
	}
	@Override
	public String getTargetPointFinishFromRole(String User, String Role, String tdid){
		try {
			return PLORIGDAOFactory.getCurrentTargetPoint().getFinishTargetPointValue(User, Role, tdid);
		} catch (Exception e){
			log.fatal(e.getLocalizedMessage());
		}
		return null;
	}
	
	@Override
	public Vector<ReportParam> getReportParamVect() {
		try {
			return PLORIGDAOFactory.getReportParamMDAO().getReportParamM();
		} catch (Exception e){
			log.fatal(e.getLocalizedMessage());
		}
		return null;
	}
	@Override
	public Vector<ReportParam> getReportParamVect(String paramType) {
		try {
			return PLORIGDAOFactory.getReportParamMDAO().getReportParamM(paramType);
		} catch (Exception e){
			log.fatal(e.getLocalizedMessage());
		}
		return null;
	}
	@Override
	public ReportParam getReportParam(String paramType, String paramCode) {
		try {
			return PLORIGDAOFactory.getReportParamMDAO().getReportParamM(paramType, paramCode);
		} catch (Exception e){
			log.fatal(e.getLocalizedMessage());
		}
		return null;
	}
	@Override
	public ArrayList<String> getRejectLetterTemplate() {
		try {
			return PLORIGDAOFactory.getReportParamMDAO().getRejectLetterTemplate();
		} catch (Exception e){
			log.fatal(e.getLocalizedMessage());
		}
		return null;
	}
	@Override
	public void prepareRejectLetter(String reportDate) {
		try {
			PLORIGDAOFactory.getReportParamMDAO().prepareRejectLetter(reportDate);
		} catch (Exception e){
			log.fatal(e.getLocalizedMessage());
		}
	}
	@Override
	public int getMaxFileOfTemplate(String template) {
		try {
			return PLORIGDAOFactory.getReportParamMDAO().getMaxFileOfTemplate(template);
		} catch (Exception e){
			log.fatal(e.getLocalizedMessage());
		}
		return 0;
	}
	@Override
	public void insertInterfaceLog(String moduleId, String logType, String logCode, String message, String createBy, String refId){
		try {
			PLORIGDAOFactory.getReportParamMDAO().insertInterfaceLog(moduleId, logType, logCode, message, createBy, refId);
		} catch (Exception e){
			log.fatal(e.getLocalizedMessage());
		}
	}
	@Override
	public PLSaleInfoDataM loadPLOrigSaleInfoDataM(String appRecId) {
		try {
			return PLORIGDAOFactory.getPLOrigSalesInfoDAO().loadPLOrigSaleInfoDataM(appRecId);
		} catch (Exception e){
			log.fatal(e.getLocalizedMessage());
		}
		return null;
	}
	@Override
	public String loadCitizenNoByCardNo(String cardNo) {
		try{
			return PLORIGDAOFactory.getPLOrigAccountCardDAO().getCitizenNoByCardNo(cardNo);
		}catch(Exception e){
			log.fatal(e.getLocalizedMessage());
			return null;
		}
	}
	@Override
	public boolean isCitizenRelateCardNo(String citizenId, String cardNo) {
		try{
			PLOrigAccountCardDAO accountDAO = PLORIGDAOFactory.getPLOrigAccountCardDAO();
			return accountDAO.isCitizenRelateCardNo(citizenId, cardNo);
		}catch(Exception e){
			log.fatal(e.getLocalizedMessage());
			return false;
		}
	}
	@Override
	public String loadallNameInformationByRole(String username) {
		try {
			return PLORIGDAOFactory.GetallNameInformationByRoleDAO().getallNameInformationByRole(username);
		} catch (Exception e) {
			log.fatal(e.getLocalizedMessage());
			return null;
		}
	}
	@Override
	public void saveApplication(PLApplicationDataM appM, UserDetailM userM) {
		try {
			PLORIGDAOFactory.getPLOrigApplicationDAO().updateSaveOrigApplication(appM, userM);
		} catch (Exception e) {
			log.fatal(e.getLocalizedMessage());
		}
	}
	
	@Override
	public Vector<String> loadBlockApplication(String appRecID) {
		try {
			PLOrigApplicationDAO applicationDAO = PLORIGDAOFactory.getPLOrigApplicationDAO();
			return applicationDAO.loadBlockApplication(appRecID);
		}catch(Exception e){
			log.fatal(e.getLocalizedMessage());
			return null;
		}
	}
	@Override
	public Vector<NotePadDataM> loadNotePad(String appRecId) {
		try{
			return PLORIGDAOFactory.getOrigNotePadDataMDAO().loadModelOrigNotePadDataM(appRecId);
		}catch(Exception e){
			log.fatal(e.getLocalizedMessage());
			return null;
		}
	}
	@Override
	public void saveNotePad(Vector<NotePadDataM> noteVect, String appRecId) {
		try {
			PLORIGDAOFactory.getOrigNotePadDataMDAO().saveUpdateModelOrigNotePadDataVect(noteVect, appRecId);
		} catch (Exception e) {
			log.fatal(e.getLocalizedMessage());
		}
	}
	@Override
	public Vector<PLAccountLogDataM> loadAccLogSortAsc(String accId) {
		try{
			return PLORIGDAOFactory.getPLorigAccountLogDAO().loadAccLogSortAsc(accId);
		}catch(Exception e){
			log.fatal(e.getLocalizedMessage());
			return null;
		}
	}
	@Override
	public String[] loadAccLogFirstRow(String accId) {
		try {
			return PLORIGDAOFactory.getPLorigAccountLogDAO().loadLogFirstRow(accId);
		} catch (Exception e) {
			log.fatal(e.getLocalizedMessage());
			return null;
		}
	}
	@Override
	public String deCodeCardNo(String cardNo) {
		try {
			return PLORIGDAOFactory.getPLorigAccountLogDAO().deCodeCardNo(cardNo);
		} catch (Exception e) {
			log.fatal(e.getLocalizedMessage());
			return null;
		}
	}
	@Override
	public void InactiveAccount(String appNo, UserDetailM userM) {
		try {
			PLORIGDAOFactory.getPLOrigAccountDAO().InactiveAccount(appNo, userM);
		} catch (Exception e) {
			log.fatal(e.getLocalizedMessage());
		}
		
	}
	@Override
	public void InactiveAccountCard(String accId, UserDetailM userM) {
		try {
			PLORIGDAOFactory.getPLOrigAccountCardDAO().InactiveAccountCard(accId, userM);
		} catch (Exception e) {
			log.fatal(e.getLocalizedMessage());
		}
		
	}
	@Override
	public void setFinalAppDecision(PLApplicationDataM appM) {
		try {
			PLORIGDAOFactory.getPLOrigApplicationDAO().updateFinalAppDecision(appM);
		} catch (Exception e) {
			log.fatal(e.getLocalizedMessage());
		}
	}
	@Override
	public int getCardLinkStatus(String accId) {
		try {
			return PLORIGDAOFactory.getPLOrigAccountDAO().getCardLinkStatus(accId);
		} catch (Exception e) {
			log.fatal(e.getLocalizedMessage());
			return 999;
		}
	}
	@Override
	public void updateAppStatusNotJoinWF(PLApplicationDataM appM, UserDetailM userM) {
		try {
			PLORIGDAOFactory.getPLOrigApplicationDAO().updateAppStatusNotJoinWF(appM, userM);
		} catch (Exception e) {
			log.fatal(e.getLocalizedMessage());
		}
	}
	
//	#septemwi comment not used
//	@Override
//	public void saveReasonM(Vector<PLReasonDataM> reasonVect, String appRecId) {
//		try {
//			PLORIGDAOFactory.getPLOrigReasonDAO().updateSaveOrigReason(reasonVect, appRecId);
//		} catch (Exception e) {
//			log.fatal(e.getLocalizedMessage());
//		}
//	}
	
	@Override
	public String reasonFromRejectCancel(String appRecId) {
		try {
			return PLORIGDAOFactory.getPLOrigReasonLogDAO().reasonFromRejectCancel(appRecId);
		} catch (Exception e) {
			log.fatal(e.getLocalizedMessage());
			return null;
		}
	}
	@Override
	public String GetGeneralParam(String paramCode, String busClassID) {
		try{
			OrigMasterGenParamDAO origGenParamDAO = PLORIGDAOFactory.getMasterGenParamDAO();
			return origGenParamDAO.GetGeneralParam(paramCode, busClassID);
		}catch (Exception e) {
			log.fatal(e.getLocalizedMessage());
			return "";
		}
	}
	@Override
	public String GetChoiceNOFieldIDByDesc(String fieldID, String desc) {
		try{
			UtilityDAO utilDAO = ORIGDAOFactory.getUtilityDAO();
			return utilDAO.GetChoiceNOFieldIDByDesc(fieldID, desc);
		}catch (Exception e) {
			log.fatal(e.getLocalizedMessage());			
		}
		return null;
	}
	@Override
	public void saveNcbDochistVect(Vector<PLNCBDocDataM> ncbDocVect, UserDetailM userM, String consentRefNo) {
		try{
			PLOrigNCBDocumentHistoryDAO ncbDocDAO = PLORIGDAOFactory.getPLOrigNCBDocumentHistoryDAO();
					ncbDocDAO.SaveNCB_DOCUMENT_HISTORY(ncbDocVect, userM, consentRefNo);
		}catch(Exception e){
			log.fatal(e.getLocalizedMessage());
			throw new EJBException(e.getMessage());
		}
	}
	@Override
	public SearchAddressDataM SearchZipCode(String zipcode, String province,String amphur, String tambol) {
		try{
			UtilityDAO utilDAO = ORIGDAOFactory.getUtilityDAO();
			return utilDAO.SearchZipCode(zipcode, province, amphur, tambol);
		}catch (Exception e) {
			log.fatal(e.getLocalizedMessage());			
		}
		return null;
	}
	@Override
	public SearchAddressDataM SearchTambol(String province, String amphur,String tambol) {
		try{
			UtilityDAO utilDAO = ORIGDAOFactory.getUtilityDAO();
			return utilDAO.SearchTambol(province, amphur, tambol);
		}catch (Exception e) {
			log.fatal(e.getLocalizedMessage());			
		}
		return null;
	}
	@Override
	public SearchAddressDataM SearchAmphur(String province, String amphur) {
		try{
			UtilityDAO utilDAO = ORIGDAOFactory.getUtilityDAO();
			return utilDAO.SearchAmphur(province, amphur);
		}catch (Exception e) {
			log.fatal(e.getLocalizedMessage());			
		}
		return null;
	}
	@Override
	public SearchAddressDataM SearchProvince(String province) {
		try{
			UtilityDAO utilDAO = ORIGDAOFactory.getUtilityDAO();
			return utilDAO.SearchProvince(province);
		}catch (Exception e) {
			log.fatal(e.getLocalizedMessage());			
		}
		return null;
	}
	@Override
	public SearchAddressDataM MandatoryAddress(String zipcode, String province,String amphur, String tambol) {
		try{
			UtilityDAO utilDAO = ORIGDAOFactory.getUtilityDAO();
			return utilDAO.MandatoryAddress(zipcode, province, amphur, tambol);
		}catch (Exception e) {
			log.fatal(e.getLocalizedMessage());			
		}
		return null;
	}
	@Override
	public String GetGeneralParamValue(String paramCode) {
		try{
			OrigMasterGenParamDAO origGenParamDAO = PLORIGDAOFactory.getMasterGenParamDAO();
			return origGenParamDAO.GetGeneralParam(paramCode);
		}catch (Exception e) {
			log.fatal(e.getLocalizedMessage());
			return "";
		}
	}
	@Override
	public void saveEnquiryLog(EnquiryLogDataM enqLogM) {
		try {
			OrigEnquiryLogDAO enqLogDAO = ORIGDAOFactory.getOrigEnquiryLogDAO();
			enqLogDAO.insertTable_OrigEnquiryLog(enqLogM);
		} catch (Exception e) {
			log.fatal(e.getLocalizedMessage());			
		}
	}
	@Override
	public PLApplicationDataM loadOrigApplicationForNCB(String appRecId) {
		try{
			PLOrigApplicationDAO applicationDAO = PLORIGDAOFactory.getPLOrigApplicationDAO();
			return applicationDAO.loadOrigApplicationForNCB(appRecId);
		}catch(Exception e){
			log.fatal(e.getLocalizedMessage());	
			throw new EJBException(e.getMessage());
		}
	}
	@Override
	public ArrayList<String> LoadORIGApplication(String jobState ,int size) {
		try{
			UtilityDAO utilDAO = ORIGDAOFactory.getUtilityDAO();
			return utilDAO.LoadORIGApplication(jobState,size);
		}catch(Exception e){
			log.fatal(e.getLocalizedMessage());			
		}
		return new ArrayList<String>();
	}
	@Override
	public int GetTotalJobCentralQueue(String tdID, String queueType){
		try{
			UtilityDAO utilDAO = ORIGDAOFactory.getUtilityDAO();
			return utilDAO.GetTotalJobCentralQueue(tdID, queueType);
		}catch(Exception e){
			throw new EJBException(e.getMessage());
		}
	}
	@Override
	public String LoadUserName(String userName,String role){
		try{
			UtilityDAO utilDAO = ORIGDAOFactory.getUtilityDAO();
			return utilDAO.LoadUserName(userName,role);
		}catch(Exception e){
			throw new EJBException(e.getMessage());
		}
	}
	@Override
	public Vector<ORIGWorkflowDataM> getQueueByOwner(String owner, String tdID) {
		try{
			ORIGWorkflowDAO wfDAO = ORIGDAOFactory.getORIGWorkflowDAO();
			return wfDAO.getQueueByOwner(owner, tdID);
		}catch(Exception e){
			throw new EJBException(e.getMessage());
		}
	}
	@Override
	public String isDataFinalCompleteApprove(String appRecordId) {
		try{
			ORIGWorkflowDAO wfDAO = ORIGDAOFactory.getORIGWorkflowDAO();
			return wfDAO.isDataFinalCompleteApprove(appRecordId);
		}catch(Exception e){
			throw new EJBException(e.getMessage());
		}
	}
	@Override
	public PLProjectCodeDataM Loadtable(String projectcode,String productfeature,String busClassID,String exception_project) {
		try{
			PLProjectCodeDAO projectCodeDAO = PLORIGDAOFactory.getPLProjectCodeDAO();
			return projectCodeDAO.Loadtable(projectcode,productfeature,busClassID,exception_project);
		}catch(PLOrigApplicationException e){
			log.fatal(e.getLocalizedMessage());
		}
		return null;
	}
	
	@Override
	public int countUser(TrackingDataM trackM){
		try{
			PLOrigTrackingDAO trackingDao = PLORIGDAOFactory.getPLOrigTrackingDAO();
				return trackingDao.countUser(trackM);
		}catch(Exception e){
			log.fatal(e.getLocalizedMessage());
		}
		return 0;
	}
	
	@Override
	public String countLogOn(TrackingDataM trackM) {
		try{
			PLOrigTrackingDAO trackingDao = PLORIGDAOFactory.getPLOrigTrackingDAO();
				return trackingDao.countLogOn(trackM);
		}catch(Exception e){
			log.fatal(e.getLocalizedMessage());
		}
		return "0/0";
	}
	@Override
	public String countOnJob(TrackingDataM trackM) {
		try{
			PLOrigTrackingDAO trackingDao = PLORIGDAOFactory.getPLOrigTrackingDAO();
				return trackingDao.countOnJob(trackM);
		}catch(Exception e){
			log.fatal(e.getLocalizedMessage());
		}
		return "0/0";
	}
	@Override
	public HashMap<String, Integer> countWfJob(TrackingDataM trackM) {
		try{
			PLOrigTrackingDAO trackingDao = PLORIGDAOFactory.getPLOrigTrackingDAO();
			return trackingDao.countWfJob(trackM);
		}catch(Exception e){
			log.fatal(e.getLocalizedMessage());
		}
		return null;
	}
	
	@Override
	public EmailTemplateMasterM getEMailTemplateMaster(String templateID,String busClassID){
		try{
			PLOrigEmailSMSDAO emailDAO = PLORIGDAOFactory.getPLOrigEmailSMSDAO();
			return emailDAO.getEMailTemplateMaster(templateID,busClassID);
		}catch(Exception e){
			log.fatal(e.getLocalizedMessage());
		}
		return null;
	}
	
	@Override
	public int getContactLogID(){
		try{
			return PLORIGDAOFactory.getPLOrigEmailSMSDAO().getContactLogID();
		}catch(Exception e){
			log.fatal(e.getLocalizedMessage());
		}
		return 0;
	}
	
	@Override
	public int countAutoQueue(TrackingDataM trackM) {
		try{
			PLOrigTrackingDAO origTrackingDAO = PLORIGDAOFactory.getPLOrigTrackingDAO();
			return origTrackingDAO.countAutoQueue(trackM);
		}catch (Exception e) {
			log.fatal(e.getLocalizedMessage());
		}
		return 0;
	}
	
	@Override
	public void SendSMSStartFollow(PLApplicationDataM applicationM) {
//		try{
//			PLOrigSMSUtil sms = new PLOrigSMSUtil();	
//			
////			septem comment change to manual method map data	
////			sms.sendSMSWithSMSCode(OrigConstant.EmailSMS.SMS_FOLLOW_UP, applicationM);	
//			
//			sms.sendSMSManual(OrigConstant.EmailSMS.SMS_FOLLOW_UP, applicationM);
//			
//			applicationM.setSmsFollowUp(OrigConstant.FLAG_Y);
//			applicationM.setSmsFollowUpDate(new Timestamp(new Date().getTime()));
//			PLOrigApplicationDAO applicationDAO = PLORIGDAOFactory.getPLOrigApplicationDAO();
//				applicationDAO.updateSMSFollowDocFLAG(applicationM);
//			sms.saveFollowDetail(applicationM);			
//		}catch(Exception e){
//			applicationM.setSmsFollowUp(null);
//			applicationM.setSmsFollowUpDate(null);
//			log.fatal("ERROR ",e);
//			throw new EJBException(e.getMessage());
//		}
	}
	@Override
	public void saveFollowDetail(PLApplicationDataM applicationM,PLXRulesFUVerificationDataM xrulesFuVerM) {
//		boolean sendSMS = false;
//		try{			
//			if(!OrigConstant.FLAG_Y.equals(applicationM.getSmsFollowUp()) 
//					&& OrigConstant.Channel.DIRECT_MAIL.equals(applicationM.getApplyChannel())){
//				sendSMS = true;
//				PLOrigSMSUtil sms = new PLOrigSMSUtil();	
//				
////				septem comment change to manual method map data
////					sms.sendSMSWithSMSCode(OrigConstant.EmailSMS.SMS_FOLLOW_UP, applicationM);	
//				
//					sms.sendSMSManual(OrigConstant.EmailSMS.SMS_FOLLOW_UP, applicationM);
//					
//				applicationM.setSmsFollowUp(OrigConstant.FLAG_Y);
//				applicationM.setSmsFollowUpDate(new Timestamp(new Date().getTime()));
//				PLOrigApplicationDAO applicationDAO = PLORIGDAOFactory.getPLOrigApplicationDAO();
//					applicationDAO.updateSMSFollowDocFLAG(applicationM);
//			}
//			
////			#septemwi modify set createDate , updateDate
//			xrulesFuVerM.setCreateDate(new Timestamp((new Date()).getTime()));
//			xrulesFuVerM.setUpdateDate(new Timestamp((new Date()).getTime()));
//			
//			PLORIGDAOFactory.getPLOrigEmailSMSDAO().insertFollowDetail(xrulesFuVerM);		
//			
//		}catch(Exception e){
//			if(sendSMS){
//				applicationM.setSmsFollowUp(null);
//				applicationM.setSmsFollowUpDate(null);
//			}
//			log.fatal("ERROR ",e);
//			throw new EJBException(e.getMessage());
//		}
	}
	
	@Override
	public SearchM getResultSearchM(SearchM searchM) {
		try{
			ValueListDAO valueListDAO = ORIGDAOFactory.getValueListDAO();
			searchM = valueListDAO.getResultSearchM(searchM);
		}catch(Exception e){
			log.fatal("ERROR ",e);
		}
		return searchM;
	}
	
	@Override
	public void SendSMSFollowDoc(PLApplicationDataM applicationM) {
//		try{
//			PLOrigSMSUtil sms = new PLOrigSMSUtil();				
////			septem comment change to manual method map data			
////				sms.sendSMSWithSMSCode(OrigConstant.EmailSMS.SMS_FOLLOW_UP, applicationM);	
//			
//			sms.sendSMSManual(OrigConstant.EmailSMS.SMS_FOLLOW_UP, applicationM);
//			
//			applicationM.setSmsFollowUp(OrigConstant.FLAG_Y);
//			applicationM.setSmsFollowUpDate(new Timestamp(new Date().getTime()));
//			PLOrigApplicationDAO applicationDAO = PLORIGDAOFactory.getPLOrigApplicationDAO();
//				applicationDAO.updateSMSFollowDocFLAG(applicationM);
//		}catch(Exception e){
//			applicationM.setSmsFollowUp(null);
//			applicationM.setSmsFollowUpDate(null);
//			log.fatal("ERROR ",e);
//			throw new EJBException(e.getMessage());
//		}
	}
	@Override
	public Vector<PLXRulesNCBVerificationDataM> getDisplayNCB(String trackingCode, PLApplicationDataM appM) {
		try{
			if(appM != null){
				PLPersonalInfoDataM personM = appM.getPLPersonalInfoDataM(OrigConstant.PERSONAL_TYPE_APPLICANT);
				if(personM != null){
					XRulesNCBDAO ncbDao = ORIGDAOFactory.getXRulesNCBDAO();
					return ncbDao.getDisplayNCB(trackingCode, personM.getPersonalID());
				}
			}
		}catch(Exception e){
			log.fatal("ERROR ",e);
			throw new EJBException(e.getMessage());
		}
		return null;
	}
	
	@Override
	public HashMap<String, String> getServiceLOG() {
		try{
			ServiceReqRespDAO DAO = PLORIGDAOFactory.getServiceReqRespDAO();
			return DAO.getServiceLOG();
		}catch(Exception e){
			log.fatal("ERROR ",e);
			throw new EJBException(e.getMessage());
		}
	}
	@Override
	public String getTotolJobWorking(TrackingDataM trackM) {
		String totol = "0";
		try{
			return PLORIGDAOFactory.getCurrentTargetPoint().getTotolJobWorking(trackM);
		}catch(Exception e){
			log.fatal("ERROR ",e);
		}
		return totol;
	}
	
	@Override
	public Vector<PLReasonDataM> getConfirmRejectReasons(String appRecordID, UserDetailM userM){
		try{
			return ORIGDAOFactory.getOrigReasonMDAO().getConfirmRejectReasons(appRecordID, userM);
		}catch(Exception e){
			log.fatal("ERROR ",e);
		}
		return null;
	}
}
