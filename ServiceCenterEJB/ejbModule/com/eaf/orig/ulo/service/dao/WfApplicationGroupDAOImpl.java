package com.eaf.orig.ulo.service.dao;

import java.math.BigDecimal;
import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;

import com.ava.workflow.AddressM;
import com.ava.workflow.ApplicationGroupM;
import com.ava.workflow.ApplicationM;
import com.ava.workflow.CardM;
import com.ava.workflow.CardlinkCustM;
import com.ava.workflow.IncomeInfoM;
import com.ava.workflow.IncomeSourceM;
import com.ava.workflow.LoanM;
import com.ava.workflow.NcbInfoM;
import com.ava.workflow.OrCodeM;
import com.ava.workflow.PersonalInfoM;
import com.ava.workflow.PersonalRefM;
import com.ava.workflow.PreviousIncomeM;
import com.ava.workflow.VerResultM;
import com.ava.workflow.WebsiteM;
import com.eaf.core.ulo.common.display.FormatUtil;
import com.eaf.core.ulo.common.properties.ListBoxControl;
import com.eaf.core.ulo.common.properties.SystemConfig;
import com.eaf.core.ulo.common.properties.SystemConstant;
import com.eaf.core.ulo.common.util.Util;
import com.eaf.orig.ulo.constant.MConstant;
import com.eaf.service.common.api.ServiceCache;
import com.eaf.service.common.exception.ServiceControlException;
import com.eaf.service.common.master.ObjectDAO;
import com.eaf.service.common.util.DataFormat;
import com.eaf.service.common.util.ServiceUtil;
import com.eaf.service.module.model.PaymentMethodDataM;
import com.eaf.service.module.model.SearchWorkFlowInquiryDataM;

public class WfApplicationGroupDAOImpl extends ObjectDAO implements WfApplicationGroupDAO{
	private static transient Logger logger = Logger.getLogger(WfApplicationGroupDAOImpl.class);
	String ADDRESS_TYPE_VAT = SystemConstant.getConstant("ADDRESS_TYPE_VAT");
	String BUNDING_HL = SystemConstant.getConstant("BUNDING_HL");
	String BUNDING_KL = SystemConstant.getConstant("BUNDING_KL");
	String BUNDING_K_SME = SystemConstant.getConstant("BUNDING_K_SME");
	String CACHE_BUSINESS_CLASS = SystemConstant.getConstant("CACHE_BUSINESS_CLASS");
	String CACHE_TEMPLATE = SystemConstant.getConstant("CACHE_TEMPLATE");
	String CACHE_CARD_INFO = SystemConstant.getConstant("CACHE_CARD_INFO");
	String VALIDATION_STATUS_COMPLETED = SystemConstant.getConstant("VALIDATION_STATUS_COMPLETED");
	String VALIDATION_STATUS_COMPLETED_NOT_PASS = SystemConstant.getConstant("VALIDATION_STATUS_COMPLETED_NOT_PASS");
	String VER_CUS_RESULT_PASS = SystemConstant.getConstant("VER_CUS_RESULT_PASS");
	String VER_CUS_RESULT_FAIL = SystemConstant.getConstant("VER_CUS_RESULT_FAIL");
	String PAYMENT_METHOD_AUTO = SystemConstant.getConstant("PAYMENT_METHOD_AUTO");
	String APPLICATION_STATUS_WORK_IN_PROGRESS = SystemConstant.getConstant("APPLICATION_STATUS_WORK_IN_PROGRESS");
	String APPLICATION_STATUS_APPROVED = SystemConstant.getConstant("APPLICATION_STATUS_APPROVED");
	String APPLICATION_STATUS_REJECTED = SystemConstant.getConstant("APPLICATION_STATUS_REJECTED");
	String APPLICATION_STATUS_CANCELLED = SystemConstant.getConstant("APPLICATION_STATUS_CANCELLED");
	String APPLICATION_STATUS_POST_APPROVED = SystemConstant.getConstant("APPLICATION_STATUS_POST_APPROVED");
	String INC_TYPE_PREVIOUS_INCOME = SystemConstant.getConstant("INC_TYPE_PREVIOUS_INCOME");
	String INC_TYPE_PREVIOUS_INCOME2 = SystemConstant.getConstant("INC_TYPE_PREVIOUS_INCOME2");
	String PERSONAL_RELATION_APPLICATION_LEVEL = SystemConstant.getConstant("PERSONAL_RELATION_APPLICATION_LEVEL");
	String PAYMENT_METHOD = "PAYMENT_METHOD";
	String DUE_CYCLE = "DUE_CYCLE";
	String ACCOUNT_NO = "ACCOUNT_NO";
	String JOB_STATE_DE2_APPROVE_SUBMIT = SystemConfig.getGeneralParam("JOB_STATE_DE2_APPROVE_SUBMIT");
	String JOB_STATE_REJECTED = SystemConfig.getGeneralParam("JOB_STATE_REJECTED");
	String JOB_STATE_CANCELLED = SystemConfig.getGeneralParam("JOB_STATE_CANCELLED");
	String JOB_STATE_AFTER_CARDLINK = SystemConfig.getGeneralParam("JOB_STATE_AFTER_CARDLINK");
	String APPLICATION_STATIC_APPROVED = SystemConstant.getConstant("APPLICATION_STATIC_APPROVED");
	String APPLICATION_STATIC_REJECTED = SystemConstant.getConstant("APPLICATION_STATIC_REJECTED");
	String APPLICATION_STATIC_CANCELLED = SystemConstant.getConstant("APPLICATION_STATIC_CANCELLED");
	String POST_APPROVED = SystemConstant.getConstant("POST_APPROVED");
	String FIELD_ID_PAYMENT_METHOD = SystemConstant.getConstant("FIELD_ID_PAYMENT_METHOD");
	String FIELD_ID_PAYMENT_RATIO = SystemConstant.getConstant("FIELD_ID_PAYMENT_RATIO");
	
	@Override
	public ApplicationGroupM loadApplicationGroup(String applicationGroupId) throws ServiceControlException {
		Connection conn = null;
		ResultSet rs = null;
		PreparedStatement ps = null;
		ApplicationGroupM applicationGroup = new ApplicationGroupM();
		try{
			conn = getConnection();
			StringBuilder SQL = new StringBuilder();
			SQL.append(" SELECT * FROM ORIG_APPLICATION_GROUP WHERE APPLICATION_GROUP_ID = ? ");
			logger.debug("SQL >> "+SQL);
			ps = conn.prepareStatement(SQL.toString());
			
			ps.setString(1, applicationGroupId);
			rs = ps.executeQuery();
			if(rs.next()){
				String jobState = rs.getString("JOB_STATE");
				String applicationStatus = "";
				if(JOB_STATE_DE2_APPROVE_SUBMIT.equals(jobState)){
					applicationStatus = APPLICATION_STATUS_APPROVED;
				}else if(JOB_STATE_AFTER_CARDLINK.equals(jobState)){
					applicationStatus = APPLICATION_STATUS_POST_APPROVED;
				}else if(JOB_STATE_REJECTED.equals(jobState)){
					applicationStatus = APPLICATION_STATUS_REJECTED;
				}else if(JOB_STATE_CANCELLED.equals(jobState)){
					applicationStatus = APPLICATION_STATUS_CANCELLED;
				}else{
					applicationStatus = APPLICATION_STATUS_WORK_IN_PROGRESS;
				}
				
				applicationGroup.setAppNo(Util.empty(rs.getString("APPLICATION_GROUP_NO")) ? "" : rs.getString("APPLICATION_GROUP_NO"));
				applicationGroup.setAppDate(DataFormat.getGregorianCalendar(rs.getString("APPLICATION_DATE"),FormatUtil.Format.yyyyMMddHHmmss));
				applicationGroup.getPersonalInfoM().addAll(loadPersonalInfo(rs.getString("APPLICATION_GROUP_ID")));
				applicationGroup.getApplicationM().addAll(loadApplication(rs.getString("APPLICATION_GROUP_ID"),
						rs.getString("APPLICATION_TEMPLATE"), applicationStatus));
			}
		}catch(Exception e){
			logger.fatal("ERROR ",e);
			throw new ServiceControlException(e.getLocalizedMessage());
		}finally{
			try{
				closeConnection(conn, rs, ps);
			}catch(Exception e){
				logger.fatal("ERROR ",e);
			}
		}
		return applicationGroup;
	}
	
	public ArrayList<PersonalInfoM> loadPersonalInfo(String applicationGroupId) throws ServiceControlException{
		Connection conn = null;
		ResultSet rs = null;
		PreparedStatement ps = null;
		ArrayList<PersonalInfoM> personalInfoList = new ArrayList<>();
		try{
			conn = getConnection();
			StringBuilder sql = new StringBuilder();
			sql.append(" SELECT * FROM ORIG_PERSONAL_INFO WHERE APPLICATION_GROUP_ID = ? ");
			logger.debug("sql : "+sql);
			ps = conn.prepareStatement(sql.toString());
			ps.setString(1, applicationGroupId);
			rs = ps.executeQuery();
			while(rs.next()){
				PersonalInfoM personalInfo = new PersonalInfoM();
				personalInfo.setPersonalId(Util.empty(rs.getString("PERSONAL_ID")) ? "" : rs.getString("PERSONAL_ID"));
				personalInfo.setDocNo(Util.empty(rs.getString("IDNO")) ? "" : rs.getString("IDNO"));
				personalInfo.setDocType(Util.empty(rs.getString("CID_TYPE")) ? "" : rs.getString("CID_TYPE"));
				personalInfo.setThFirstname(rs.getString("TH_FIRST_NAME"));
				personalInfo.setThMidname(rs.getString("TH_MID_NAME"));
				personalInfo.setThLastname(rs.getString("TH_LAST_NAME"));
				personalInfo.setCisNo(rs.getString("CIS_NO"));
				personalInfo.setDob(rs.getString("BIRTH_DATE"));
				personalInfo.setApplicantType(Util.empty(rs.getString("PERSONAL_TYPE")) ? "" : rs.getString("PERSONAL_TYPE"));
				personalInfo.setFinalVerifiedIncome(FormatUtil.toBigDecimal(rs.getBigDecimal("TOT_VERIFIED_INCOME"),false));
				personalInfo.setMailingAddress(rs.getString("MAILING_ADDRESS"));
				personalInfo.setTypeOfFin(rs.getString("TYPE_OF_FIN"));
				personalInfo.setMobileNo(rs.getString("MOBILE_NO"));
				personalInfo.setVatCode(getVatCode(rs.getString("PERSONAL_ID")));
				personalInfo.getAddressM().addAll(loadPersonalAddress(rs.getString("PERSONAL_ID")));
				personalInfo.setVerResultM(loadXrulesVerificationResult(rs.getString("PERSONAL_ID"), MConstant.REF_LEVEL.PERSONAL_INFO));
				personalInfo.setCardlinkCustM(loadCardlinkCustomer(rs.getString("PERSONAL_ID")));
				personalInfo.getIncomeInfoM().addAll(loadIncInfo(rs.getString("PERSONAL_ID")));
				IncomeSourceM incomeSource = new IncomeSourceM();
				incomeSource.setIncomeSource(rs.getString("SORCE_OF_INCOME"));
				personalInfo.getIncomeSourceM().add(incomeSource);
				personalInfoList.add(personalInfo);
				personalInfo.setNcbInfoM(loadNcbInfo(rs.getString("PERSONAL_ID")));
			}
		}catch(Exception e){
			logger.fatal("ERROR ",e);
			throw new ServiceControlException(e.getLocalizedMessage());
		}finally{
			try{
				closeConnection(conn, rs, ps);
			}catch(Exception e){
				logger.fatal("ERROR ",e);
			}
		}
		return personalInfoList;
	}
	
	public ArrayList<AddressM> loadPersonalAddress(String personalId) throws ServiceControlException{
		Connection conn = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		ArrayList<AddressM> addresses = new ArrayList<>();
		try{
			conn = getConnection();
			StringBuilder sql = new StringBuilder();
			sql.append(" SELECT * FROM ORIG_PERSONAL_ADDRESS WHERE PERSONAL_ID = ? ");
			ps = conn.prepareStatement(sql.toString());
			ps.setString(1, personalId);
			rs = ps.executeQuery();
			while(rs.next()){
				AddressM addressM = new AddressM();
				addressM.setAddressType(rs.getString("ADDRESS_TYPE"));
				addressM.setHouseNumber(rs.getString("ADDRESS"));
				addressM.setVillage(rs.getString("VILAPT"));
				addressM.setFloorNumber(rs.getString("FLOOR"));
				addressM.setRoom(rs.getString("ROOM"));
				addressM.setBuildingName(rs.getString("BUILDING"));
				addressM.setMoo(rs.getString("MOO"));
				addressM.setSoi(rs.getString("SOI"));
				addressM.setStreet(rs.getString("ROAD"));
				addressM.setSubDistrict(rs.getString("TAMBOL"));
				addressM.setDistrict(rs.getString("AMPHUR"));
				addressM.setProvince(rs.getString("PROVINCE_DESC"));
				addressM.setPostalCode(rs.getString("ZIPCODE"));
				addressM.setCountry(rs.getString("COUNTRY"));
				addresses.add(addressM);
			}
		}catch(Exception e){
			logger.fatal("Error ",e);
			throw new ServiceControlException(e.getLocalizedMessage());
		}finally{
			try{
				closeConnection(conn , rs, ps);
			}catch(Exception e){
				logger.fatal("Error ",e);
			}
		}
		return addresses;
	}
	
	public VerResultM loadXrulesVerificationResult(String refId,String verLevel) throws ServiceControlException{
		Connection conn = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		VerResultM verResult = new VerResultM();
		try{
			conn = getConnection();
			StringBuilder sql = new StringBuilder();
			logger.debug("verLevel : "+verLevel);
			sql.append(" SELECT * FROM XRULES_VERIFICATION_RESULT WHERE REF_ID = ? AND VER_LEVEL = ? ");
			logger.debug("sql : "+sql);
			ps = conn.prepareStatement(sql.toString());
			ps.setString(1, refId);
			ps.setString(2, verLevel);
			
			rs = ps.executeQuery();
			if(rs.next()){
				String VER_CUS_RESULT_CODE = rs.getString("VER_CUS_RESULT_CODE");
				if(MConstant.REF_LEVEL.PERSONAL_INFO.equals(verLevel)){
					if(VALIDATION_STATUS_COMPLETED.equals(VER_CUS_RESULT_CODE)){
						verResult.setVerifyCustResult(VER_CUS_RESULT_PASS);
					}else if(VALIDATION_STATUS_COMPLETED_NOT_PASS.equals(VER_CUS_RESULT_CODE)){
						verResult.setVerifyCustResult(VER_CUS_RESULT_FAIL);
					}
					verResult.getWebsiteM().addAll(loadXrulesWebVerification(rs.getString("VER_RESULT_ID")));
				}else if(MConstant.REF_LEVEL.APPLICATION.equals(verLevel)){
					verResult.getOrCodeM().addAll(loadXrulesPolicyRules(rs.getString("VER_RESULT_ID")));
				}	
			}
		}catch(Exception e){
			logger.fatal("Error ",e);
			throw new ServiceControlException(e.getLocalizedMessage());
		}finally{
			try{
				closeConnection(conn , rs, ps);
			}catch(Exception e){
				logger.fatal("Error ",e);
			}
		}
		return verResult;
	}
	private  String getVerResultId(String refId,String verLevel) throws ServiceControlException{
		Connection conn = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		String VER_RESULT_ID="";
		try{
			conn = getConnection();
			StringBuilder sql = new StringBuilder();
			logger.debug("verLevel : "+verLevel);
			sql.append(" SELECT VER_RESULT_ID FROM XRULES_VERIFICATION_RESULT WHERE REF_ID = ? AND VER_LEVEL = ? ");
			logger.debug("sql : "+sql);
			ps = conn.prepareStatement(sql.toString());
			ps.setString(1, refId);
			ps.setString(2, verLevel);
			
			rs = ps.executeQuery();
			if(rs.next()){
				VER_RESULT_ID= rs.getString("VER_RESULT_ID");
			}
		}catch(Exception e){
			logger.fatal("Error ",e);
			throw new ServiceControlException(e.getLocalizedMessage());
		}finally{
			try{
				closeConnection(conn , rs, ps);
			}catch(Exception e){
				logger.fatal("Error ",e);
			}
		}
		return VER_RESULT_ID;
	}
	
	public CardlinkCustM loadCardlinkCustomer(String personalId) throws ServiceControlException{
		Connection conn = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		CardlinkCustM cardlinkCust = new CardlinkCustM();
		try{
			conn = getConnection();
			StringBuilder sql = new StringBuilder();
			sql.append(" SELECT * FROM ORIG_CARDLINK_CUSTOMER WHERE PERSONAL_ID = ? ");
			logger.debug("sql : "+sql);
			ps = conn.prepareStatement(sql.toString());
			ps.setString(1,personalId);
			rs = ps.executeQuery();
			if(rs.next()){
				cardlinkCust.setCardlinkCustNo(rs.getString("CARDLINK_CUST_NO"));
				cardlinkCust.setOrgId(rs.getString("ORIG_ID"));
			}
		}catch(Exception e){
			logger.fatal("Error ",e);
			throw new ServiceControlException(e.getLocalizedMessage());
		}finally{
			try{
				closeConnection(conn, rs, ps);
			}catch(Exception e){
				logger.fatal("Error ",e);
			}
		}
		return cardlinkCust;
	}
	
	public CardlinkCustM loadCardlinkCustomerByCardlinkCustId(String cardlinkCustId) throws ServiceControlException{
		Connection conn = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		CardlinkCustM cardlinkCust = new CardlinkCustM();
		try{
			conn = getConnection();
			StringBuilder sql = new StringBuilder();
			sql.append(" SELECT * FROM ORIG_CARDLINK_CUSTOMER WHERE CARDLINK_CUST_ID = ? ");
			logger.debug("sql : "+sql);
			ps = conn.prepareStatement(sql.toString());
			ps.setString(1,cardlinkCustId);
			rs = ps.executeQuery();
			if(rs.next()){
				cardlinkCust.setCardlinkCustNo(rs.getString("CARDLINK_CUST_NO"));
				cardlinkCust.setOrgId(rs.getString("ORIG_ID"));
			}
		}catch(Exception e){
			logger.fatal("Error ",e);
			throw new ServiceControlException(e.getLocalizedMessage());
		}finally{
			try{
				closeConnection(conn, rs, ps);
			}catch(Exception e){
				logger.fatal("Error ",e);
			}
		}
		return cardlinkCust;
	}
	
	public CardlinkCustM loadMainCardlinkCustomer(String supCardlinkCustId, String supCustNo, String supOrgNo) 
			throws ServiceControlException {
		Connection conn = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		CardlinkCustM cardlinkCust = new CardlinkCustM();
		try{
			conn = getConnection();
			StringBuilder sql = new StringBuilder();
			sql.append(" SELECT * FROM ( ");
			sql.append("   SELECT CC.CARDLINK_CUST_NO, CC.ORIG_ID, CC.PERSONAL_ID ");
			sql.append("   FROM ORIG_CARDLINK_CUSTOMER SCC ");
			sql.append("   JOIN ORIG_PERSONAL_INFO SPI ON SCC.PERSONAL_ID = SPI.PERSONAL_ID ");
			sql.append("   JOIN ORIG_PERSONAL_INFO PI ON PI.APPLICATION_GROUP_ID = SPI.APPLICATION_GROUP_ID AND PI.PERSONAL_TYPE IN ('A', 'I') ");
			sql.append("   JOIN ORIG_CARDLINK_CUSTOMER CC ON PI.PERSONAL_ID = CC.PERSONAL_ID ");
			sql.append("   WHERE SCC.CARDLINK_CUST_ID = ? AND SUBSTR(CC.CARDLINK_CUST_NO, 1, 2) = SUBSTR(?, 1, 2) AND CC.ORIG_ID = ? ");
			sql.append("   ORDER BY CC.UPDATE_DATE DESC ");
			sql.append(" ) WHERE ROWNUM = 1 ");
			logger.debug("sql : "+sql);
			ps = conn.prepareStatement(sql.toString());
			ps.setString(1, supCardlinkCustId);
			ps.setString(2, supCustNo);
			ps.setString(3, supOrgNo);
			rs = ps.executeQuery();
			if(rs.next()) {
				cardlinkCust.setCardlinkCustNo(rs.getString("CARDLINK_CUST_NO"));
				cardlinkCust.setOrgId(rs.getString("ORIG_ID"));
			}
		} catch(Exception e) {
			logger.fatal("Error ",e);
			throw new ServiceControlException(e.getLocalizedMessage());
		} finally {
			try{
				closeConnection(conn, rs, ps);
			}catch(Exception e) {
				logger.fatal("Error ",e);
			}
		}
		return cardlinkCust;
	}
	
	public ArrayList<WebsiteM> loadXrulesWebVerification(String verResultId) throws ServiceControlException{
		Connection conn = null;
		ResultSet rs = null;
		PreparedStatement ps = null;
		ArrayList<WebsiteM> websiteList = new ArrayList<>();
		try{
			conn = getConnection();
			StringBuilder sql = new StringBuilder();
			sql.append(" SELECT * FROM XRULES_WEB_VERIFICATION WHERE VER_RESULT_ID = ? ");
			logger.debug("sql : "+sql);
			ps = conn.prepareStatement(sql.toString());
			ps.setString(1, verResultId);
			rs = ps.executeQuery();
			while(rs.next()){
				WebsiteM website = new WebsiteM();
				String verResult = rs.getString("VER_RESULT");
				website.setWebsiteCode(rs.getString("WEB_CODE"));
				if(!ServiceUtil.empty(verResult)){
					website.setWebsiteReusult(rs.getString("VER_RESULT"));
				}
				websiteList.add(website);
			}
		}catch(Exception e){
			logger.fatal("Error ",e);
			throw new ServiceControlException(e.getLocalizedMessage());
		}finally{
			try{
				closeConnection(conn, rs, ps);
			}catch(Exception e){
				logger.fatal("Error ",e);
			}
		}
		return websiteList;
	}
	
	public ArrayList<IncomeInfoM> loadIncInfo(String personalId) throws ServiceControlException{
		Connection conn = null;
		ResultSet rs = null;
		PreparedStatement ps = null;
		ArrayList<IncomeInfoM> incomeInfoList = new ArrayList<>();
		try{
			conn = getConnection();
			StringBuilder sql = new StringBuilder();
			sql.append(" SELECT * FROM INC_INFO WHERE PERSONAL_ID = ? ");
			logger.debug("sql : "+sql);
			ps = conn.prepareStatement(sql.toString());
			ps.setString(1,personalId);
			rs = ps.executeQuery();
			while(rs.next()){
				IncomeInfoM incomeInfo = new IncomeInfoM();
				String incomeType = rs.getString("INCOME_TYPE");
				incomeInfo.setIncomeType(incomeType);
				if(INC_TYPE_PREVIOUS_INCOME.equals(incomeType) || INC_TYPE_PREVIOUS_INCOME2.equals(incomeType)){
					incomeInfo.getPreviousIncomeM().addAll(loadIncPreviousIncome(rs.getString("INCOME_ID")));
				}
				incomeInfoList.add(incomeInfo);
			}
		}catch(Exception e){
			logger.fatal("Error ",e);
			throw new ServiceControlException(e.getLocalizedMessage());
		}finally{
			try{
				closeConnection(conn, rs, ps);
			}catch(Exception e){
				logger.fatal("Error ",e);
			}
		}
		return incomeInfoList;
	}
	
	public ArrayList<PreviousIncomeM> loadIncPreviousIncome(String incomeId) throws ServiceControlException{
		Connection conn = null;
		ResultSet rs = null;
		PreparedStatement ps = null;
		ArrayList<PreviousIncomeM> previousIncomeList = new ArrayList<>();
		try{
			conn = getConnection();
			StringBuilder sql = new StringBuilder();
			sql.append(" SELECT * FROM INC_PREVIOUS_INCOME WHERE INCOME_ID = ? ");
			logger.debug("sql : "+sql);
			ps = conn.prepareStatement(sql.toString());
			ps.setString(1, incomeId);
			rs = ps.executeQuery();
			while(rs.next()){
				PreviousIncomeM previousIncome = new PreviousIncomeM();
				previousIncome.setIncome(FormatUtil.toBigDecimal(rs.getBigDecimal("INCOME"),false));
				previousIncome.setIncomeDate(DataFormat.getGregorianCalendar(rs.getString("INCOME_DATE"), FormatUtil.Format.yyyy_MM_dd));
				previousIncomeList.add(previousIncome);
			}
			
		}catch(Exception e){
			logger.fatal("Error ",e);
			throw new ServiceControlException(e.getLocalizedMessage());
		}finally{
			try{
				closeConnection(conn, rs, ps);
			}catch(Exception e){
				logger.fatal("Error : ",e);
			}
		}
		return previousIncomeList;
	}
	
	public ArrayList<ApplicationM> loadApplication(String applicationGroupId,String applicationTemplate,
			String applicationStatus) throws ServiceControlException{
		Connection conn = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		ArrayList<ApplicationM> applicationList = new ArrayList<>();
		try{
			conn = getConnection();
			StringBuilder sql = new StringBuilder();
			sql.append(" SELECT * FROM ORIG_APPLICATION WHERE APPLICATION_GROUP_ID = ? ");
			logger.debug("sql : "+sql);
			ps = conn.prepareStatement(sql.toString());
			ps.setString(1, applicationGroupId);
			rs = ps.executeQuery();
			while(rs.next()){
				ApplicationM application = new ApplicationM();
				String applicationRecordId = rs.getString("APPLICATION_RECORD_ID");
				
				String vetoFlag = MConstant.FLAG.NO;
				BigDecimal bundleCreditLimit = BigDecimal.ZERO;
				application.setAppItemNo(rs.getString("APPLICATION_NO"));
				application.setAppStatus(applicationStatus);
				application.setAppStatusDate(DataFormat.getGregorianCalendar(rs.getString("FINAL_APP_DECISION_DATE"),FormatUtil.Format.yyyyMMddHHmmss));
				application.setProjectCode(rs.getString("PROJECT_CODE"));
				application.setBookingDate(DataFormat.getGregorianCalendar(rs.getString("BOOKING_DATE"),FormatUtil.Format.yyyyMMddHHmmss));
				String BUSINESS_CLASS_ID = rs.getString("BUSINESS_CLASS_ID");		
				String kBankProductCode = ServiceCache.getName(CACHE_BUSINESS_CLASS,"BUS_CLASS_ID",BUSINESS_CLASS_ID,"PRODUCT_CODE_MAP");
				application.setKbankProductCode(kBankProductCode);
				
				if(rs.getInt("LIFE_CYCLE")>1)	vetoFlag = MConstant.FLAG.YES;
				application.setVetoFlag(vetoFlag);
				application.setApplyType(rs.getString("APPLICATION_TYPE"));
				logger.debug("applicationRecordId>>"+applicationRecordId);
				VerResultM verResult = loadXrulesVerificationResult(applicationRecordId, MConstant.REF_LEVEL.APPLICATION);
				String verResultId = getVerResultId(applicationRecordId, MConstant.REF_LEVEL.APPLICATION);
				logger.debug("verResultId>>"+verResultId);
				String reason = getXRulesPolicyInfo(verResultId);
				logger.debug("reason>>"+reason);
				application.setRejectCode(reason);
				application.setApplyType(rs.getString("APPLICATION_TYPE")); 
				
				String bunding = ServiceCache.getName(CACHE_TEMPLATE, "TEMPLATE_ID", applicationTemplate, "BUNDING");
				
				application.setBundleProduct(bunding);
				if(BUNDING_HL.equals(bunding)){
					bundleCreditLimit = FormatUtil.toBigDecimal(loadBundlHL(applicationRecordId).getBundleProductCreditLimit(),false);
				}else if(BUNDING_KL.equals(bunding)){
					bundleCreditLimit = FormatUtil.toBigDecimal(loadBundleKL(applicationRecordId).getBundleProductCreditLimit(),false);
				}else if(BUNDING_K_SME.equals(bunding))	{
					bundleCreditLimit = FormatUtil.toBigDecimal(loadBundleSME(applicationRecordId).getBundleProductCreditLimit(),false);				
				}
				application.setBundleProductCreditLimit(bundleCreditLimit);
				application.setPolicyProgramId(rs.getString("PROLICY_PROGRAM_ID"));
				String mainAppItemNo = getMainAppItemNo(rs.getString("MAINCARD_RECORD_ID"));
				logger.debug("mainAppItemNo : "+mainAppItemNo);
				application.setMainAppItemNo(mainAppItemNo);
				application.setVerResultM(verResult);
				application.getLoanM().addAll(loadLoan(rs.getString("APPLICATION_RECORD_ID"), rs.getString("APPLICATION_DATE")));
				
 				List<String> personalRefIdList = loadPersonalRelation(applicationRecordId);
				application.getPersonalRefM().addAll(loadPersonalRef(personalRefIdList));
				applicationList.add(application);
			}
		}catch(Exception e){
			logger.fatal("Error ",e);
			throw new ServiceControlException(e.getLocalizedMessage());
		}finally{
			try{
				closeConnection(conn, rs, ps);
			}catch(Exception e){
				logger.fatal("Error ",e);
			}
		}
		return applicationList;
	}
	
	public ApplicationM loadBundlHL(String applicationRecordId) throws ServiceControlException{
		Connection conn = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		ApplicationM application = new ApplicationM(); 
		try{
			conn = getConnection();
			StringBuilder sql = new StringBuilder();
			sql.append(" SELECT * FROM ORIG_BUNDLE_HL WHERE APPLICATION_RECORD_ID = ? ");
			logger.debug("sql : "+sql);
			ps = conn.prepareStatement(sql.toString());
			ps.setString(1, applicationRecordId);
			rs = ps.executeQuery();
			if(rs.next()){
				application.setBundleProductCreditLimit(FormatUtil.toBigDecimal(rs.getBigDecimal("APPROVE_CREDIT_LINE"),false));
			}
		}catch(Exception e){
			logger.fatal("Error ",e);
			throw new ServiceControlException(e.getLocalizedMessage());
		}finally{
			try{
				closeConnection(conn, rs, ps);
			}catch(Exception e){
				logger.debug("Error ",e);
			}
		}
		return application;
	}
	
	public ApplicationM loadBundleKL(String applicationRecordId) throws ServiceControlException{
		Connection conn = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		ApplicationM application = new ApplicationM();
		try{
			conn = getConnection();
			StringBuilder sql = new StringBuilder();
			sql.append(" SELECT * FROM ORIG_BUNDLE_KL WHERE APPLICATION_RECORD_ID = ? ");
			logger.debug("sql : "+sql);
			ps = conn.prepareStatement(sql.toString());
			ps.setString(1,applicationRecordId);
			rs = ps.executeQuery();
			if(rs.next()){
				application.setBundleProductCreditLimit(FormatUtil.toBigDecimal(rs.getBigDecimal("ESTIMATED_INCOME"),false));
			}
		}catch(Exception e){
			logger.fatal("Error ",e);
			throw new ServiceControlException(e.getLocalizedMessage());
		}finally{
			try{
				closeConnection(conn, rs, ps);
			}catch(Exception e){
				logger.debug("Error ",e);
			}
		}
		return application;
	}
	
	public ApplicationM loadBundleSME(String applicationRecordId) throws ServiceControlException{
		Connection conn = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		ApplicationM application = new ApplicationM();
		try{
			conn = getConnection();
			StringBuilder sql = new StringBuilder();
			sql.append(" SELECT * FROM ORIG_BUNDLE_SME WHERE APPLICATION_RECORD_ID = ? ");
			logger.debug("sql :"+sql);
			ps = conn.prepareStatement(sql.toString());
			ps.setString(1,applicationRecordId);
			rs = ps.executeQuery();
			if(rs.next()){
				application.setBundleProductCreditLimit(FormatUtil.toBigDecimal(rs.getBigDecimal("APPROVE_CREDIT_LINE"),false));
			}
		}catch(Exception e){
			logger.fatal("Error ",e);
			throw new ServiceControlException(e.getLocalizedMessage());
		}finally{
			try{
				closeConnection(conn, rs, ps);
			}catch(Exception e){
				logger.fatal("Error ",e);
			}
		}
		return application;
	}
	
	public ArrayList<OrCodeM> loadXrulesPolicyRules(String verResultId) throws ServiceControlException{
		Connection conn = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		ArrayList<OrCodeM> orCodeList = new ArrayList<>();
		try{
			conn = getConnection();
			StringBuilder sql = new StringBuilder();
			sql.append(" SELECT * FROM XRULES_POLICY_RULES WHERE VER_RESULT_ID = ? ");
			logger.debug("sql : "+sql);
			ps = conn.prepareStatement(sql.toString());
			ps.setString(1, verResultId);
			rs = ps.executeQuery();
			while(rs.next()){
				OrCodeM orCode = new OrCodeM();
				orCode.setOrCode(rs.getString("POLICY_CODE"));
				orCodeList.add(orCode);
			}
		}catch(Exception e){
			logger.fatal("Error ",e);
			throw new ServiceControlException(e.getLocalizedMessage());
		}finally{
			try{
				closeConnection(conn, rs, ps);
			}catch(Exception e){
				logger.fatal("Error ",e);
			}
		}
		return orCodeList;
	}
	
	public ArrayList<LoanM> loadLoan(String applicationRecordId,String bookFlag) throws ServiceControlException{
		Connection conn = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		ArrayList<LoanM> loanList = new ArrayList<>();
		try{
			conn = getConnection();
			StringBuilder sql = new StringBuilder();
			sql.append(" SELECT * FROM ORIG_LOAN WHERE APPLICATION_RECORD_ID = ? ");
			logger.debug("sql : "+sql);
			ps = conn.prepareStatement(sql.toString());
			ps.setString(1, applicationRecordId);
			rs = ps.executeQuery();
			while(rs.next()){
				LoanM loan = new LoanM();
				PaymentMethodDataM paymentMethod = loadPayMentMethod(rs.getString("PAYMENT_METHOD_ID"));
				
				loan.setAccountNo(rs.getString("ACCOUNT_NO"));
				loan.setFinalCreditLimit(FormatUtil.toBigDecimal(rs.getBigDecimal("FINAL_CREDIT_LIMIT"),false));
				loan.setInstallmentAmt(FormatUtil.toBigDecimal(rs.getBigDecimal("INSTALLMENT_AMT"),false));
				loan.setBookedFlag(Util.empty(bookFlag) ? MConstant.FLAG.NO : MConstant.FLAG.YES);
				loan.setCycleCut(paymentMethod.getDueCycle());
				loan.setPaymentMethod(ListBoxControl.getName(FIELD_ID_PAYMENT_METHOD, "CHOICE_NO", paymentMethod.getPaymentMethod(), "MAPPING1"));
				if(PAYMENT_METHOD_AUTO.equals(paymentMethod.getPaymentMethod())){
					loan.setAutopayAccountNo(paymentMethod.getAutopayAccountNo());
				}
				String paymentRatioString = ListBoxControl.getName(FIELD_ID_PAYMENT_RATIO, "CHOICE_NO", paymentMethod.getPaymentMethod(), "MAPPING1");
				if (!Util.empty(paymentRatioString)) {
					loan.setPaymentRatio(new BigDecimal(paymentRatioString));
				}
				List<String> personalRefIdList = loadPersonalRelation(applicationRecordId);
				loan.getPersonalRefM().addAll(loadPersonalRef(personalRefIdList));
				
				loan.setCardM(loadCard(rs.getString("LOAN_ID"), getPersonalIdByApplicationRecordId(applicationRecordId)));
				
				//Map additional fields [23/04/2019]
				loan.setLoanAmt(rs.getBigDecimal("LOAN_AMT"));
				loan.setTerm(rs.getBigDecimal("TERM"));
				loan.setAccountOpenDate(DataFormat.getGregorianCalendar(rs.getString("ACCOUNT_OPEN_DATE"),FormatUtil.Format.yyyyMMddHHmmss));
				
				loanList.add(loan);
			}
		}catch(Exception e){
			logger.fatal("Error ",e);
			throw new ServiceControlException(e.getLocalizedMessage());
		}finally{
			try{
				closeConnection(conn, rs, ps);
			}catch(Exception e){
				logger.fatal("Error ",e);
			}
		}
		return loanList;
	}
	
	public PaymentMethodDataM loadPayMentMethod(String paymentMethodId) throws ServiceControlException{
		Connection conn = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		PaymentMethodDataM paymentMethod = new PaymentMethodDataM();
		try{
			conn = getConnection();
			StringBuilder sql = new StringBuilder();
			sql.append(" SELECT * FROM ORIG_PAYMENT_METHOD WHERE PAYMENT_METHOD_ID = ? ");
			ps = conn.prepareStatement(sql.toString());
			ps.setString(1, paymentMethodId);
			rs = ps.executeQuery();
			if(rs.next()){
				paymentMethod.setPaymentMethod(rs.getString("PAYMENT_METHOD"));
				paymentMethod.setDueCycle(rs.getString("DUE_CYCLE"));
				paymentMethod.setAutopayAccountNo(rs.getString("ACCOUNT_NO"));
				paymentMethod.setPaymentRatio(rs.getBigDecimal("PAYMENT_RATIO"));
			}
		}catch(Exception e){
			logger.debug("Error ",e);
			throw new ServiceControlException(e.getLocalizedMessage());
		}finally{
			try{
				closeConnection(conn, rs, ps);
			}catch(Exception e){
				logger.debug("Error ",e);
			}
		}
		return paymentMethod;
	}
	public List<String> loadPersonalRelation(String applicationRecordId) throws ServiceControlException{
		Connection conn = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		List<String> personalRefIdList = new ArrayList<>();  
		try{
			conn = getConnection();
			StringBuilder sql = new StringBuilder();
			sql.append(" SELECT * FROM ORIG_PERSONAL_RELATION ")
				.append(" WHERE REF_ID = ? ")
				.append(" AND RELATION_LEVEL = ? ");
			logger.debug("sql : "+sql);
			ps = conn.prepareStatement(sql.toString());
			int indexParameter = 1;
			ps.setString(indexParameter++, applicationRecordId);
			ps.setString(indexParameter++, MConstant.REF_LEVEL.APPLICATION);
			rs = ps.executeQuery();
			while(rs.next()){
				personalRefIdList.add(rs.getString("PERSONAL_ID"));
			}
		}catch(Exception e){
			logger.fatal("Error ",e);
			throw new ServiceControlException(e.getLocalizedMessage());
		}finally{
			try{
				closeConnection(conn, rs, ps);
			}catch(Exception e){
				logger.fatal("Error ",e);
			}
		}
		return personalRefIdList;
	}
	public ArrayList<PersonalRefM> loadPersonalRef(List<String> personalRefIdList) throws ServiceControlException{
		Connection conn = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		ArrayList<PersonalRefM> personalRefList = new ArrayList<>();
		try{
			conn = getConnection();
			StringBuilder sql = new StringBuilder();
			
			String sqlCriteria = "";
			if(!Util.empty(personalRefIdList)){
				String[] conditionSize = new String[personalRefIdList.size()];
				sqlCriteria = " WHERE PERSONAL_ID = ? "+StringUtils.join(conditionSize, " OR PERSONAL_ID = ? ");
			}
			sql.append(" SELECT * FROM ORIG_PERSONAL_INFO ")
				.append(sqlCriteria);
			
			
			logger.debug("sql : "+sql);
			ps = conn.prepareStatement(sql.toString());
			int indexParameter = 1;
			for(String personalRefId : personalRefIdList){
				ps.setString(indexParameter++, personalRefId);
			}
			rs = ps.executeQuery();
			while(rs.next()){
				PersonalRefM personalRef = new PersonalRefM();
				personalRef.setPesonalId(Util.empty(rs.getString("PERSONAL_ID")) ? "" : rs.getString("PERSONAL_ID"));
				personalRef.setApplicantType(Util.empty(rs.getString("PERSONAL_TYPE")) ? "" : rs.getString("PERSONAL_TYPE"));
				personalRefList.add(personalRef);
			}
		}catch(Exception e){
			logger.fatal("Error ",e);
			throw new ServiceControlException(e.getLocalizedMessage());
		}finally{
			try{
				closeConnection(conn, rs, ps);
			}catch(Exception e){
				logger.fatal("Error ",e);
			}
		}
		return personalRefList;
	}
	
	public ArrayList<PersonalRefM> loadPersonalRefCat(String applicationRecordId) throws ServiceControlException{
		Connection conn = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		ArrayList<PersonalRefM> personalRefList = new ArrayList<>();
		try{
			conn = getConnection();
			StringBuilder sql = new StringBuilder();
			
			sql.append(" SELECT PER.PERSONAL_ID, PER.PERSONAL_TYPE FROM ORIG_PERSONAL_INFO_CAT PER ");
			sql.append(" JOIN ORIG_APPLICATION_CAT AP ");
			sql.append(" ON PER.PERSONAL_ID = AP.PERSONAL_ID ");
			sql.append(" WHERE AP.APPLICATION_RECORD_ID = ? ");
			
			logger.debug("sql : "+sql);
			ps = conn.prepareStatement(sql.toString());
			ps.setString(1, applicationRecordId);
			
			rs = ps.executeQuery();
			while(rs.next())
			{
				PersonalRefM personalRef = new PersonalRefM();
				personalRef.setPesonalId(Util.empty(rs.getString("PERSONAL_ID")) ? "" : rs.getString("PERSONAL_ID"));
				personalRef.setApplicantType(Util.empty(rs.getString("PERSONAL_TYPE")) ? "" : rs.getString("PERSONAL_TYPE"));
				personalRefList.add(personalRef);
			}
		}catch(Exception e){
			logger.fatal("Error ",e);
			throw new ServiceControlException(e.getLocalizedMessage());
		}finally{
			try{
				closeConnection(conn, rs, ps);
			}catch(Exception e){
				logger.fatal("Error ",e);
			}
		}
		return personalRefList;
	}
	
	public CardM loadCard(String loanId, String personalId) throws ServiceControlException{
		Connection conn = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		CardM card = new CardM();
		try{
			conn = getConnection();
			StringBuilder sql = new StringBuilder();
			sql.append(" SELECT * FROM ORIG_CARD WHERE LOAN_ID = ? ");
			logger.debug("sql : "+sql);
			logger.debug("loanId:"+loanId);
			ps = conn.prepareStatement(sql.toString());
			ps.setString(1, loanId);
			rs = ps.executeQuery();
			if(rs.next()){
				String cardType = rs.getString("CARD_TYPE");
				String cardCode = ServiceCache.getName(CACHE_CARD_INFO, "CARD_TYPE_ID", cardType, "MAPPING1");
				String cardlinkCustId = rs.getString("CARDLINK_CUST_ID");
				card.setCardCode(cardCode);
				card.setCardLevel(rs.getString("CARD_LEVEL"));
				card.setPlasticType(rs.getString("CARD_FACE"));
				card.setDocNo(loadIdNoByPersonalId(personalId));
				card.setCardApplyType(rs.getString("APPLICATION_TYPE"));
				card.setCardNo(rs.getString("CARD_NO"));
				card.setCardNoEncrypted(rs.getString("CARD_NO_ENCRYPTED2"));
				card.setPriorityPassNo(getPriorityPassNo(rs.getString("CARD_ID")));
				card.setCardType(cardType);
				
				CardlinkCustM cardlinkCust = loadCardlinkCustomerByCardlinkCustId(cardlinkCustId);
				card.setCardlinkCustNo(cardlinkCust.getCardlinkCustNo());
				card.setCardlinkOrgNo(cardlinkCust.getOrgId());
					
				if("B".equals(card.getCardApplyType())) {
					card.setMainCustNo(card.getCardlinkCustNo());
					card.setMainOrgNo(card.getCardlinkOrgNo());
				} else {
					CardlinkCustM mainCardlinkCust = loadMainCardlinkCustomer(cardlinkCustId, 
							card.getCardlinkCustNo(), card.getCardlinkOrgNo());
					card.setMainCustNo(mainCardlinkCust.getCardlinkCustNo());
					card.setMainOrgNo(mainCardlinkCust.getOrgId());
				}
					
				String encryptMainCardNo = rs.getString("MAIN_CARD_NO");
				if(!ServiceUtil.empty(encryptMainCardNo)){
					card.setMainCardNo(encryptMainCardNo);
					card.setMaincardNoEncrypted(encryptMainCardNo);
				}
			}
		}catch(Exception e){
			logger.fatal("Error ",e);
			throw new ServiceControlException(e.getLocalizedMessage());
		}finally{
			try{
				closeConnection(conn, rs, ps);
			}catch(Exception e){
				logger.fatal("Error ",e);
			}
		}
		return card;
	}

	@Override
	public SearchWorkFlowInquiryDataM getApplicationGroupId(String CISCustomerId) throws ServiceControlException {
		Connection conn = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		SearchWorkFlowInquiryDataM workFlowInquiry = new SearchWorkFlowInquiryDataM();
		List<String> applicationGroupIds = new ArrayList<>();
		String personalId ="";
		try{
			conn = getConnection();
			StringBuilder sql = new StringBuilder();
			sql.append(" SELECT DISTINCT APPLICATION_GROUP_ID, PERSONAL_ID FROM ORIG_PERSONAL_INFO ");
			sql.append(" WHERE ");
			sql.append(" CIS_NO = ? ");
			
			logger.debug("sql : "+sql);
			ps = conn.prepareStatement(sql.toString());
			int parameter = 1;
			ps.setString(parameter++, CISCustomerId);

			rs = ps.executeQuery();
			while(rs.next()){
				applicationGroupIds.add(rs.getString("APPLICATION_GROUP_ID"));
				personalId = rs.getString("PERSONAL_ID");
			}
		}catch(Exception e){
			logger.fatal("Error ",e);
		}finally{
			try{
				closeConnection(conn, rs, ps);
			}catch(Exception e){
				logger.fatal("Error ",e);
			}
		}
		workFlowInquiry.setApplicationGroupIds(applicationGroupIds);
		workFlowInquiry.setSearchApplicationGroupId(true);		
		workFlowInquiry.setPersonalId(personalId);	
		return workFlowInquiry;
	}
	
	@Override
	public SearchWorkFlowInquiryDataM getApplicationGroupId(String docId, String docType) throws ServiceControlException {
		Connection conn = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		SearchWorkFlowInquiryDataM workFlowInquiry = new SearchWorkFlowInquiryDataM();
		List<String> applicationGroupIds = new ArrayList<>();
		String personalId="";
		try{
			conn = getConnection();
			StringBuilder sql = new StringBuilder();
			sql.append(" SELECT DISTINCT APPLICATION_GROUP_ID, PERSONAL_ID  FROM ORIG_PERSONAL_INFO ");
			sql.append(" WHERE ");
			sql.append(" IDNO = ? ");
			sql.append(" AND CID_TYPE = ? ");
			
			logger.debug("sql : "+sql);
			ps = conn.prepareStatement(sql.toString());
			int parameter = 1;
			ps.setString(parameter++, docId);
			ps.setString(parameter++, docType);
			rs = ps.executeQuery();
			while(rs.next()){
				applicationGroupIds.add(rs.getString("APPLICATION_GROUP_ID"));
				personalId=rs.getString("PERSONAL_ID");
			}
		}catch(Exception e){
			logger.fatal("Error ",e);
		}finally{
			try{
				closeConnection(conn, rs, ps);
			}catch(Exception e){
				logger.fatal("Error ",e);
			}
		}
		workFlowInquiry.setApplicationGroupIds(applicationGroupIds);
		workFlowInquiry.setSearchApplicationGroupId(true);
		workFlowInquiry.setPersonalId(personalId);
		return workFlowInquiry;
	}
	
	@Override
	public SearchWorkFlowInquiryDataM getApplicationGroupId(String thFirstName, String thLastName, Date birthDate) throws ServiceControlException {
		Connection conn = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		SearchWorkFlowInquiryDataM workFlowInquiry = new SearchWorkFlowInquiryDataM();
		List<String> applicationGroupIds = new ArrayList<>();
		String personalId = "";
		try{
			conn = getConnection();
			StringBuilder sql = new StringBuilder();
			sql.append(" SELECT DISTINCT APPLICATION_GROUP_ID, PERSONAL_ID ");
			sql.append(" FROM ORIG_PERSONAL_INFO ");
			sql.append(" WHERE ");
			sql.append(" TH_FIRST_NAME = ? ");
			sql.append(" AND TH_LAST_NAME = ? ");
			if(!Util.empty(birthDate)){
				sql.append(" AND TO_CHAR(BIRTH_DATE, 'DD-MM-YYYY') = TO_CHAR(?, 'DD-MM-YYYY') ");
			}
			logger.debug("sql : "+sql);
			ps = conn.prepareStatement(sql.toString());
			int parameter = 1;
			ps.setString(parameter++, thFirstName);
			ps.setString(parameter++, thLastName);
			if(!Util.empty(birthDate)){
				ps.setDate(parameter++, birthDate);
			}
			rs = ps.executeQuery();
			while(rs.next()){
				personalId = rs.getString("PERSONAL_ID");
				applicationGroupIds.add(rs.getString("APPLICATION_GROUP_ID"));
			}
		}catch(Exception e){
			logger.fatal("Error ",e);
		}finally{
			try{
				closeConnection(conn, rs, ps);
			}catch(Exception e){
				logger.fatal("Error ",e);
			}
		}
		workFlowInquiry.setApplicationGroupIds(applicationGroupIds);
		workFlowInquiry.setPersonalId(personalId);
		workFlowInquiry.setSearchApplicationGroupId(true);
		return workFlowInquiry;
	}
	
	public String getVatCode(String personalId) throws ServiceControlException {
		Connection conn = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		String vatCode = null;
		try{
			conn = getConnection();
			StringBuilder sql = new StringBuilder();
			sql.append(" SELECT * FROM ORIG_PERSONAL_ADDRESS ");
			sql.append(" WHERE ");
			sql.append(" ADDRESS_TYPE = ? ");
			sql.append(" AND PERSONAL_ID = ? ");
			logger.debug("sql : "+sql);
			logger.debug("ADDRESS_TYPE_VAT : "+ADDRESS_TYPE_VAT);
			ps = conn.prepareStatement(sql.toString());
			ps.setString(1, ADDRESS_TYPE_VAT);
			ps.setString(2, personalId);
			rs = ps.executeQuery();
			while(rs.next()){
				vatCode = rs.getString("VAT_CODE");
			}
		}catch(Exception e){
			logger.fatal("Error ",e);
		}finally{
			try{
				closeConnection(conn, rs, ps);
			}catch(Exception e){
				logger.fatal("Error ",e);
			}
		}
		return vatCode;
	}
	
	public String getPriorityPassNo(String cardId) throws ServiceControlException{
		Connection conn = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		String priorityPassNo = null;
		try{
			conn = getConnection();
			StringBuilder sql = new StringBuilder();
			sql.append(" SELECT * FROM ORIG_WISDOM_INFO ");
			sql.append(" WHERE ");
			sql.append(" CARD_ID = ? ");
			logger.debug("sql : "+sql);
			ps = conn.prepareStatement(sql.toString());
			ps.setString(1, cardId);
			rs = ps.executeQuery();
			if(rs.next()){
				priorityPassNo = rs.getString("PRIORITY_PASS_NO");
			}
		}catch(Exception e){
			logger.fatal("Error ",e);
		}finally{
			try{
				closeConnection(conn, rs, ps);
			}catch(Exception e){
				logger.fatal("Error ",e);
			}
		}
		return priorityPassNo;
	}
	
	public String getMainAppItemNo(String mainCardRecordId) throws ServiceControlException{
		Connection conn = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		String mainAppItemNo = null;
		try{
			conn = getConnection();
			StringBuilder sql = new StringBuilder();
			sql.append(" SELECT APPLICATION_NO FROM ORIG_APPLICATION ");
			sql.append(" WHERE ");
			sql.append(" APPLICATION_RECORD_ID = ? ");
			logger.debug("sql : "+sql);
			ps = conn.prepareStatement(sql.toString());
			ps.setString(1, mainCardRecordId);
			rs = ps.executeQuery();
			while(rs.next()){
				mainAppItemNo = rs.getString("APPLICATION_NO");
			}
		}catch(Exception e){
			logger.fatal("Error ",e);
		}finally{
			try{
				closeConnection(conn, rs, ps);
			}catch(Exception e){
				logger.fatal("Error ",e);
			}
		}
		return mainAppItemNo;
	}
	
	public String getXRulesPolicyInfo(String verResultId){
		Connection conn = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		String REASON = null;
		try{
			conn = getConnection();
			StringBuilder sql = new StringBuilder();
			sql.append(" SELECT PO_RULES.REASON  FROM XRULES_POLICY_RULES PO_RULES ");
			sql.append(" WHERE PO_RULES.VER_RESULT_ID=?");
			sql.append(" AND  PO_RULES.RANK = (SELECT MIN(RANK) FROM  XRULES_POLICY_RULES PO_MIN WHERE  PO_MIN.VER_RESULT_ID= PO_RULES.VER_RESULT_ID)");
			
			ps = conn.prepareStatement(sql.toString());
			ps.setString(1, verResultId);
			rs = ps.executeQuery();
			if(rs.next()){
				REASON = rs.getString("REASON");
			}
		}catch(Exception e){
			logger.fatal("ERROR ",e);
		}finally{
			try{
				closeConnection(conn, rs, ps);
			}catch(Exception e){
				logger.fatal("ERROR ",e);
			}
		}
		return REASON;
	}
	
	public String getPersonalIdByApplicationRecordId(String applicationRecordId){
		Connection conn = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		String personalId = "";
		try{
			StringBuilder sql = new StringBuilder();
			sql.append(" SELECT * FROM ORIG_PERSONAL_RELATION WHERE REF_ID = ? AND RELATION_LEVEL = ? ");
			logger.debug("sql >> "+sql);
			conn = getConnection();
			ps = conn.prepareStatement(sql.toString());
			int parameterIndex = 1;
			ps.setString(parameterIndex++, applicationRecordId);
			ps.setString(parameterIndex++, PERSONAL_RELATION_APPLICATION_LEVEL);
			rs = ps.executeQuery();
			if(rs.next()){
				personalId = rs.getString("PERSONAL_ID");
			}
		}catch(Exception e){
			logger.fatal("ERROR ",e);
		}finally{
			try{
				closeConnection(conn, rs, ps);
			}catch(Exception e){
				logger.fatal("ERROR ",e);
			}
		}
		return personalId;
	}
	
	public String loadIdNoByPersonalId(String personalId){
		Connection conn = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		String idNo = "";
		try{
			StringBuilder sql = new StringBuilder();
			sql.append(" SELECT * FROM ORIG_PERSONAL_INFO WHERE PERSONAL_ID = ? ");
			logger.debug("sql >> "+sql);
			conn = getConnection();
			ps = conn.prepareStatement(sql.toString());
			int parameterIndex = 1;
			ps.setString(parameterIndex++, personalId);
			rs = ps.executeQuery();
			if(rs.next()){
				idNo = rs.getString("IDNO");
			}
		}catch(Exception e){
			logger.fatal("ERROR ",e);
		}finally{
			try{
				closeConnection(conn, rs, ps);
			}catch(Exception e){
				logger.fatal("ERROR ",e);
			}
		}
		return idNo;
	}
	
	public NcbInfoM loadNcbInfo(String personalId){
		Connection conn = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		NcbInfoM ncbInfoM = new NcbInfoM();
		try{
			StringBuilder sql = new StringBuilder();
			//sql.append(" SELECT * FROM NCB_INFO WHERE PERSONAL_ID = ? ");
			//KPL Additional
			sql.append(" SELECT NCBINF.*, ONCBH.OLDEST_NCB_NEW FROM NCB_INFO NCBINF ");
			sql.append(" LEFT JOIN (SELECT PERSONAL_ID, MAX(CREATE_DATE) AS OLDEST_NCB_NEW FROM ORIG_NCB_DOCUMENT_HISTORY ");
			sql.append(" WHERE BUREAU_REQUIRED_FLAG='NCB_NEW' GROUP BY PERSONAL_ID) ONCBH  ");
			sql.append(" ON NCBINF.PERSONAL_ID = ONCBH.PERSONAL_ID ");
			sql.append(" WHERE NCBINF.PERSONAL_ID = ? ");
			logger.debug("sql >> "+sql);
			conn = getConnection();
			ps = conn.prepareStatement(sql.toString());
			int parameterIndex = 1;
			ps.setString(parameterIndex++, personalId);
			rs = ps.executeQuery();
			if(rs.next()){
				ncbInfoM.setTrackingCode(rs.getString("TRACKING_CODE"));
				ncbInfoM.setPersonalId(rs.getString("PERSONAL_ID"));
				ncbInfoM.setFicoScore(rs.getString("FICO_SCORE"));
				ncbInfoM.setFicoErrorCode(rs.getString("FICO_ERROR_CODE"));
				ncbInfoM.setFicoErrorMsg(rs.getString("FICO_ERROR_MSG"));
				ncbInfoM.setFicoReason1Code(rs.getString("FICO_REASON1_CODE"));
				ncbInfoM.setFicoReason1Desc(rs.getString("FICO_REASON1_DESC"));
				ncbInfoM.setFicoReason2Code(rs.getString("FICO_REASON2_CODE"));
				ncbInfoM.setFicoReason2Desc(rs.getString("FICO_REASON2_DESC"));
				ncbInfoM.setFicoReason3Code(rs.getString("FICO_REASON3_CODE"));
				ncbInfoM.setFicoReason3Desc(rs.getString("FICO_REASON3_DESC"));
				ncbInfoM.setFicoReason4Code(rs.getString("FICO_REASON4_CODE"));
				ncbInfoM.setFicoReason4Desc(rs.getString("FICO_REASON4_DESC"));
				ncbInfoM.setConsentRefNo(rs.getString("CONSENT_REF_NO"));
				ncbInfoM.setNoTimesEnquiry(rs.getInt("NO_TIMES_ENQUIRY"));
				ncbInfoM.setNoTimesEnquiry6M(rs.getInt("NO_TIMES_ENQUIRY_6M"));
				ncbInfoM.setNoCcCard(rs.getInt("NO_CC_CARD"));
				ncbInfoM.setTotCcOutStatding(rs.getInt("TOT_CC_OUT_STATDING"));
				ncbInfoM.setPersonalLoanUnderBotIssuer(rs.getInt("PERSONAL_LOAN_UNDER_BOT_ISSUER"));
				ncbInfoM.setOldestNcbNew(DataFormat.getGregorianCalendar(rs.getString("OLDEST_NCB_NEW"), FormatUtil.Format.yyyyMMddHHmmss));
			}
		}catch(Exception e){
			logger.fatal("ERROR ",e);
		}finally{
			try{
				closeConnection(conn, rs, ps);
			}catch(Exception e){
				logger.fatal("ERROR ",e);
			}
		}
		return ncbInfoM;
	}
	
	@Override
	public ApplicationGroupM loadApplicationGroupCat(String applicationGroupId) throws ServiceControlException {
		Connection conn = null;
		ResultSet rs = null;
		PreparedStatement ps = null;
		ApplicationGroupM applicationGroup = new ApplicationGroupM();
		try{
			conn = getConnection();
			StringBuilder SQL = new StringBuilder();
			SQL.append(" SELECT * FROM ORIG_APPLICATION_GROUP_CAT WHERE APPLICATION_GROUP_ID = ? ");
			logger.debug("SQL >> "+SQL);
			ps = conn.prepareStatement(SQL.toString());
			
			ps.setString(1, applicationGroupId);
			rs = ps.executeQuery();
			if(rs.next()){;
				String applicationStatus = rs.getString("APPLICATION_STATUS");
				if(APPLICATION_STATIC_APPROVED.equals(applicationStatus)){
					applicationStatus = APPLICATION_STATUS_APPROVED;
				}else if(POST_APPROVED.equals(applicationStatus)){
					applicationStatus = APPLICATION_STATUS_POST_APPROVED;
				}else if(APPLICATION_STATIC_REJECTED.equals(applicationStatus)){
					applicationStatus = APPLICATION_STATUS_REJECTED;
				}else if(APPLICATION_STATIC_CANCELLED.equals(applicationStatus)){
					applicationStatus = APPLICATION_STATUS_CANCELLED;
				}else{
					applicationStatus = APPLICATION_STATUS_WORK_IN_PROGRESS;
				}
				
				applicationGroup.setAppNo(Util.empty(rs.getString("APPLICATION_GROUP_NO")) ? "" : rs.getString("APPLICATION_GROUP_NO"));
				applicationGroup.setAppDate(DataFormat.getGregorianCalendar(rs.getString("APPLICATION_DATE"),FormatUtil.Format.yyyyMMddHHmmss));
				applicationGroup.getPersonalInfoM().addAll(loadPersonalInfoCat(rs.getString("APPLICATION_GROUP_ID")));
				applicationGroup.getApplicationM().addAll(loadApplicationCat(rs.getString("APPLICATION_GROUP_ID"),
						rs.getString("APPLICATION_TEMPLATE"), applicationStatus));
			}
		}catch(Exception e){
			logger.fatal("ERROR ",e);
			throw new ServiceControlException(e.getLocalizedMessage());
		}finally{
			try{
				closeConnection(conn, rs, ps);
			}catch(Exception e){
				logger.fatal("ERROR ",e);
			}
		}
		return applicationGroup;
	}
	
	public ArrayList<PersonalInfoM> loadPersonalInfoCat(String applicationGroupId) throws ServiceControlException{
		Connection conn = null;
		ResultSet rs = null;
		PreparedStatement ps = null;
		ArrayList<PersonalInfoM> personalInfoList = new ArrayList<>();
		try{
			conn = getConnection();
			StringBuilder sql = new StringBuilder();
			sql.append(" SELECT * FROM ORIG_PERSONAL_INFO_CAT WHERE APPLICATION_GROUP_ID = ? ");
			logger.debug("sql : "+sql);
			ps = conn.prepareStatement(sql.toString());
			ps.setString(1, applicationGroupId);
			rs = ps.executeQuery();
			while(rs.next()){
				PersonalInfoM personalInfo = new PersonalInfoM();
				personalInfo.setPersonalId(Util.empty(rs.getString("PERSONAL_ID")) ? "" : rs.getString("PERSONAL_ID"));
				personalInfo.setDocNo(Util.empty(rs.getString("IDNO")) ? "" : rs.getString("IDNO"));
				personalInfo.setDocType(Util.empty(rs.getString("CID_TYPE")) ? "" : rs.getString("CID_TYPE"));
				personalInfo.setThFirstname(rs.getString("TH_FIRST_NAME"));
				personalInfo.setThMidname(rs.getString("TH_MID_NAME"));
				personalInfo.setThLastname(rs.getString("TH_LAST_NAME"));
				personalInfo.setCisNo(rs.getString("CIS_ID"));
				personalInfo.setDob(rs.getString("BIRTH_DATE"));
				personalInfo.setApplicantType(Util.empty(rs.getString("PERSONAL_TYPE")) ? "" : rs.getString("PERSONAL_TYPE"));
				personalInfo.setFinalVerifiedIncome(FormatUtil.toBigDecimal(rs.getBigDecimal("TOT_VERIFIED_INCOME"),false));
				personalInfo.setTypeOfFin(rs.getString("TYPE_OF_FIN"));
				IncomeSourceM incomeSource = new IncomeSourceM();
				incomeSource.setIncomeSource(rs.getString("SORCE_OF_INCOME"));
				personalInfo.getIncomeSourceM().add(incomeSource);
				personalInfoList.add(personalInfo);
			}
		}catch(Exception e){
			logger.fatal("ERROR ",e);
			throw new ServiceControlException(e.getLocalizedMessage());
		}finally{
			try{
				closeConnection(conn, rs, ps);
			}catch(Exception e){
				logger.fatal("ERROR ",e);
			}
		}
		return personalInfoList;
	}
	
	public ArrayList<ApplicationM> loadApplicationCat(String applicationGroupId,String applicationTemplate,
			String applicationStatus) throws ServiceControlException{
		Connection conn = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		ArrayList<ApplicationM> applicationList = new ArrayList<>();
		try{
			conn = getConnection();
			StringBuilder sql = new StringBuilder();
			sql.append(" SELECT * FROM ORIG_APPLICATION_CAT WHERE APPLICATION_GROUP_ID = ? ");
			logger.debug("sql : "+sql);
			ps = conn.prepareStatement(sql.toString());
			ps.setString(1, applicationGroupId);
			rs = ps.executeQuery();
			while(rs.next()){
				ApplicationM application = new ApplicationM();
				String applicationRecordId = rs.getString("APPLICATION_RECORD_ID");
				
				String vetoFlag = MConstant.FLAG.NO;
				application.setAppStatus(applicationStatus);
				application.setAppStatusDate(DataFormat.getGregorianCalendar(rs.getString("FINAL_APP_DECISION_DATE"),FormatUtil.Format.yyyyMMddHHmmss));
				
				String BUSINESS_CLASS_ID = rs.getString("BUSINESS_CLASS_ID");		
				String kBankProductCode = ServiceCache.getName(CACHE_BUSINESS_CLASS,"BUS_CLASS_ID",BUSINESS_CLASS_ID,"PRODUCT_CODE_MAP");
				application.setKbankProductCode(kBankProductCode);
				
				if(rs.getInt("LIFE_CYCLE")>1)	vetoFlag = MConstant.FLAG.YES;
				application.setVetoFlag(vetoFlag);
				application.setApplyType(rs.getString("APPLICATION_TYPE"));
				logger.debug("applicationRecordId>>"+applicationRecordId);
				
				application.getPersonalRefM().addAll(loadPersonalRefCat(applicationRecordId));
				
				//Create Loan object
				LoanM loan = new LoanM();
				
				if(!Util.empty(rs.getString("CARD_TYPE")))
				{
					CardM card = new CardM();
					card.setCardType(rs.getString("CARD_TYPE"));
					if(!Util.empty(rs.getString("CARDLINK_CARD_CODE")))
					{
						card.setCardCode(ServiceCache.getName(CACHE_CARD_INFO, "CARD_TYPE_ID", rs.getString("CARDLINK_CARD_CODE"), "MAPPING1"));
					}
					card.setCardNo(rs.getString("CARD_NO"));
					loan.setCardM(card);
				}
				
				loan.setAccountNo(rs.getString("ACCOUNT_NO"));
				loan.setAccountOpenDate(DataFormat.getGregorianCalendar(rs.getString("ACCOUNT_OPEN_DATE"),FormatUtil.Format.yyyyMMddHHmmss));
				
				application.getLoanM().add(loan);
				
				applicationList.add(application);
			}
		}catch(Exception e){
			logger.fatal("Error ",e);
			throw new ServiceControlException(e.getLocalizedMessage());
		}finally{
			try{
				closeConnection(conn, rs, ps);
			}catch(Exception e){
				logger.fatal("Error ",e);
			}
		}
		return applicationList;
	}
	
	@Override
	public SearchWorkFlowInquiryDataM getApplicationGroupIdCat(String cisNo, String docNo, String docType, String thFirstName, String thLastName, Date dateOfBirth) throws ServiceControlException {
		Connection conn = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		SearchWorkFlowInquiryDataM workFlowInquiry = new SearchWorkFlowInquiryDataM();
		List<String> applicationGroupIds = new ArrayList<>();
		String personalId = "";
		try{
			conn = getConnection();
			StringBuilder sql = new StringBuilder();
			sql.append(" SELECT DISTINCT APPLICATION_GROUP_ID, PERSONAL_ID ");
			sql.append(" FROM ORIG_PERSONAL_INFO_CAT ");
			sql.append(" WHERE 1=2 ");
			if(!Util.empty(cisNo))
			{sql.append(" OR CIS_ID = ? ");}
			else if(!Util.empty(docNo) && !Util.empty(docType))
			{sql.append(" OR ( IDNO = ? AND CID_TYPE = ? ) ");}
			else if(!Util.empty(thFirstName) && !Util.empty(thLastName))
			{
				sql.append(" OR ( TH_FIRST_NAME = ? AND TH_LAST_NAME = ? ");
				if(!Util.empty(dateOfBirth))
				{
					sql.append(" AND TO_CHAR(BIRTH_DATE, 'DD-MM-YYYY') = TO_CHAR(?, 'DD-MM-YYYY') ");
				}
				sql.append(" ) ");
			}
			logger.debug("sql : "+sql);
			ps = conn.prepareStatement(sql.toString());
			int parameter = 1;

			if(!Util.empty(cisNo)){
				ps.setString(parameter++, cisNo);
			}
			else if(!Util.empty(docNo) && !Util.empty(docType))
			{
				ps.setString(parameter++, docNo);
				ps.setString(parameter++, docType);
			}
			else if(!Util.empty(thFirstName) && !Util.empty(thLastName))
			{
				ps.setString(parameter++, thFirstName);
				ps.setString(parameter++, thLastName);
				if(!Util.empty(dateOfBirth))
				{
					ps.setDate(parameter++, dateOfBirth);
				}
			}
			rs = ps.executeQuery();
			while(rs.next()){
				personalId = rs.getString("PERSONAL_ID");
				applicationGroupIds.add(rs.getString("APPLICATION_GROUP_ID"));
			}
		}catch(Exception e){
			logger.fatal("Error ",e);
		}finally{
			try{
				closeConnection(conn, rs, ps);
			}catch(Exception e){
				logger.fatal("Error ",e);
			}
		}
		workFlowInquiry.setApplicationGroupIds(applicationGroupIds);
		workFlowInquiry.setPersonalId(personalId);
		workFlowInquiry.setSearchApplicationGroupId(true);
		return workFlowInquiry;
	}
}
