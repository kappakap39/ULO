package com.eaf.orig.ulo.app.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.HashMap;

import com.eaf.core.ulo.common.date.ApplicationDate;
import com.eaf.core.ulo.common.display.FormatUtil;
import com.eaf.core.ulo.common.util.Util;
import com.eaf.orig.shared.constant.OrigConstant;
import com.eaf.orig.shared.dao.OrigObjectDAO;
import com.eaf.orig.ulo.app.dao.exception.ApplicationException;
import com.eaf.orig.ulo.app.factory.ModuleFactory;
import com.eaf.orig.ulo.app.inc.dao.OrigIncomeDAO;
import com.eaf.orig.ulo.app.ncb.dao.OrigNCBInfoDAO;
import com.eaf.orig.ulo.app.util.GenerateUnique;
import com.eaf.orig.ulo.app.xrules.dao.OrigVerificationResultDAO;
import com.eaf.orig.ulo.constant.MConstant;
import com.eaf.orig.ulo.model.app.AccountDataM;
import com.eaf.orig.ulo.model.app.AddressDataM;
import com.eaf.orig.ulo.model.app.BScoreDataM;
import com.eaf.orig.ulo.model.app.CardlinkCardDataM;
import com.eaf.orig.ulo.model.app.CardlinkCustomerDataM;
import com.eaf.orig.ulo.model.app.DebtInfoDataM;
import com.eaf.orig.ulo.model.app.IncomeInfoDataM;
import com.eaf.orig.ulo.model.app.IncomeSourceDataM;
import com.eaf.orig.ulo.model.app.KYCDataM;
import com.eaf.orig.ulo.model.app.NCBDocumentDataM;
import com.eaf.orig.ulo.model.app.NCBDocumentHistoryDataM;
import com.eaf.orig.ulo.model.app.PersonalInfoDataM;
import com.eaf.orig.ulo.model.app.PersonalRelationDataM;
import com.eaf.orig.ulo.model.app.VerificationResultDataM;
import com.eaf.orig.ulo.model.compare.CompareDataM;
import com.eaf.orig.ulo.model.ncb.NcbInfoDataM;

public class OrigPersonalInfoDAOImpl extends OrigObjectDAO implements OrigPersonalInfoDAO {
	public OrigPersonalInfoDAOImpl(String userId){
		this.userId = userId;
	}
	public OrigPersonalInfoDAOImpl(){
		
	}
	private String userId = "";
	@Override
	public void createOrigPersonalInfoM(PersonalInfoDataM personalInfoM) throws ApplicationException {
		logger.debug("personalInfoM.getPersonalId() :"+personalInfoM.getPersonalId());
	    if(Util.empty(personalInfoM.getPersonalId())){
			String personalId = GenerateUnique.generate(OrigConstant.SeqNames.ORIG_PERSONAL_INFO_PK);
			personalInfoM.setPersonalId(personalId);
			personalInfoM.setCreateBy(userId);
	    }
	    personalInfoM.setUpdateBy(userId);
		createTableORIG_PERSONAL_INFO(personalInfoM);
		
		ArrayList<CardlinkCardDataM> cardLinkCardList = personalInfoM.getCardLinkCards();
		if(!Util.empty(cardLinkCardList)) {
			OrigCardLinkCardDAO cardLinkCardDAO = ORIGDAOFactory.getCardLinkCardDAO(userId);
			for(CardlinkCardDataM cardLinkCardM: cardLinkCardList) {
				cardLinkCardM.setPersonalId(personalInfoM.getPersonalId());
				cardLinkCardDAO.createOrigCardlinkCardM(cardLinkCardM);
			}
		}
		
		ArrayList<CardlinkCustomerDataM> cardLinkCustList = personalInfoM.getCardLinkCustomers();
		if(!Util.empty(cardLinkCustList)) {
			OrigCardLinkCustomerDAO cardLinkCustomerDAO = ORIGDAOFactory.getCardLinkCustomerDAO(userId);
			for(CardlinkCustomerDataM cardLinkCustM: cardLinkCustList) {
				cardLinkCustM.setPersonalId(personalInfoM.getPersonalId());
				cardLinkCustomerDAO.createOrigCardlinkCustomerM(cardLinkCustM);
			}
		}
		
		KYCDataM kycM = personalInfoM.getKyc();
		if(!Util.empty(kycM)) {
			OrigKYCDAO kycDAO = ORIGDAOFactory.getKYCDAO(userId);
			kycM.setPersonalId(personalInfoM.getPersonalId());
			kycDAO.createOrigKYCM(kycM);
		}
		
		ArrayList<NCBDocumentDataM> ncbDocList = personalInfoM.getNcbDocuments();
		if(!Util.empty(ncbDocList)) {
			OrigNCBDocumentDAO ncbDocumentDAO = ORIGDAOFactory.getNCBDocumentDAO(userId);
			for(NCBDocumentDataM ncbDocM: ncbDocList) {
				ncbDocM.setPersonalId(personalInfoM.getPersonalId());
				ncbDocumentDAO.createOrigNCBDocumentM(ncbDocM);
			}
		}
		
		ArrayList<NCBDocumentHistoryDataM> ncbDocHistList = personalInfoM.getNcbDocumentHistorys();
		if(!Util.empty(ncbDocHistList)) {
			OrigNCBDocumentHistoryDAO ncbDocumentHistDAO = ORIGDAOFactory.getNCBDocumentHistoryDAO(userId);
			for(NCBDocumentHistoryDataM ncbDocHistM: ncbDocHistList) {
				ncbDocHistM.setPersonalId(personalInfoM.getPersonalId());
				ncbDocumentHistDAO.createOrigNCBDocHistoryM(ncbDocHistM);
			}
		}
		
		ArrayList<AddressDataM> addressList = personalInfoM.getAddresses();
		if(!Util.empty(addressList)) {
			OrigPersonalAddressDAO personalAddressDAO = ORIGDAOFactory.getPersonalAddressDAO(userId);
			for(AddressDataM addressM: addressList) {
				addressM.setPersonalId(personalInfoM.getPersonalId());
				personalAddressDAO.createOrigAddressM(addressM);
			}
		}
		
		ArrayList<IncomeInfoDataM> incomeList = personalInfoM.getIncomeInfos();
		if(!Util.empty(incomeList)) {
			OrigIncomeDAO incomeDAO = ORIGDAOFactory.getIncomeDAO(userId);
			for(IncomeInfoDataM incomeM : incomeList){
				incomeM.setPersonalId(personalInfoM.getPersonalId());
				incomeDAO.createOrigIncomeInfoM(incomeM);
			}
		}
		
		ArrayList<IncomeSourceDataM> incomeSrcList = personalInfoM.getIncomeSources();
		if(!Util.empty(incomeSrcList)) {
			OrigIncomeSourceDAO incomeSrcDAO = ORIGDAOFactory.getIncomeSourceDAO(userId);
			for(IncomeSourceDataM incomeSrcM : incomeSrcList){
				incomeSrcM.setPersonalId(personalInfoM.getPersonalId());
				incomeSrcDAO.createOrigIncomeSourceM(incomeSrcM);
			}
		}
		
		ArrayList<DebtInfoDataM> debtList = personalInfoM.getDebtInfos();
		if(!Util.empty(debtList)) {
			OrigDebtInfoDAO debtDAO = ORIGDAOFactory.getDebtInfoDAO(userId);
			for(DebtInfoDataM debtInfoM : debtList){
				debtInfoM.setPersonalId(personalInfoM.getPersonalId());
				debtDAO.createOrigDebtInfoM(debtInfoM);
			}
		}
		
		ArrayList<NcbInfoDataM> ncbList = personalInfoM.getNcbInfos();
		if(!Util.empty(ncbList)) {
			OrigNCBInfoDAO ncbDAO = ORIGDAOFactory.getNCBInfoDAO(userId);
			for(NcbInfoDataM ncbInfoM : ncbList){
				ncbInfoM.setPersonalId(personalInfoM.getPersonalId());
				ncbDAO.createOrigNcbInfoM(ncbInfoM);
			}
		}
		
		ArrayList<PersonalRelationDataM> personalRelationList = personalInfoM.getPersonalRelations();
		if(!Util.empty(personalRelationList)) {
			OrigPersonalRelationDAO personalRelationDAO = ORIGDAOFactory.getPersonalRelationDAO(userId);
			for(PersonalRelationDataM personalRelationM : personalRelationList){
				personalRelationM.setPersonalId(personalInfoM.getPersonalId());
				/*personalRelationM.setRefId(personalInfoM.getPersonalId());
				personalRelationM.setRelationLevel("P");*/
				personalRelationDAO.createOrigPersonalRelationM(personalRelationM);
			}
		}
		
		VerificationResultDataM verificationM = personalInfoM.getVerificationResult();
		if(!Util.empty(verificationM)) {
			OrigVerificationResultDAO verificationDAO = ORIGDAOFactory.getVerificationResultDAO(userId);
			verificationM.setRefId(personalInfoM.getPersonalId());
			verificationM.setVerLevel(MConstant.REF_LEVEL.PERSONAL_INFO);
			verificationDAO.createOrigVerificationResultM(verificationM);
		}
		
		ArrayList<BScoreDataM> bScoreList = personalInfoM.getBscores();
		if(!Util.empty(bScoreList)) {
			OrigBScoreDAO bScoreDAO = ORIGDAOFactory.getBScoreDAO(userId);
			for(BScoreDataM bScoreM: bScoreList) {
				bScoreM.setPersonalId(personalInfoM.getPersonalId());
				bScoreDAO.createOrigBScoreM(bScoreM);
			}
		}
		ArrayList<AccountDataM> accountList = personalInfoM.getAccounts();
		if(!Util.empty(accountList)) {
			OrigPersonalAccountDAO accountDAO = ORIGDAOFactory.getAccountDAO(userId);
			for(AccountDataM accountM: accountList) {
				accountM.setPersonalId(personalInfoM.getPersonalId());
				accountDAO.createOrigPersonalAccountM(accountM);
			}
		}
	}

	private void createTableORIG_PERSONAL_INFO(PersonalInfoDataM personalInfoM) throws ApplicationException {
		PreparedStatement ps = null;
		Connection conn = null;
		try {
			//conn = Get Connection
			conn = getConnection();
			StringBuffer sql = new StringBuffer("");
			sql.append("INSERT INTO ORIG_PERSONAL_INFO ");
			sql.append("( PERSONAL_ID, IDNO, APPLICATION_GROUP_ID, PERSONAL_TYPE, CUSTOMER_TYPE, ");
			sql.append(" MAILING_ADDRESS, TH_TITLE_CODE, TH_FIRST_NAME, TH_LAST_NAME, TH_MID_NAME, ");
			sql.append(" EN_TITLE_CODE, EN_FIRST_NAME, EN_LAST_NAME, EN_MID_NAME, GENDER, ");
			sql.append(" BIRTH_DATE, RACE, NATIONALITY, MARRIED, CID_TYPE, CID_EXP_DATE, ");
			sql.append(" OCCUPATION, BUSINESS_TYPE, BUSINESS_TYPE_OTHER, BUSINESS_NATURE, ");
			
			sql.append(" POSITION_CODE, POSITION_DESC, POSITION_LEVEL, SALARY, EMPLOYMENT_TYPE, ");
			sql.append(" TOT_WORK_YEAR, TOT_WORK_MONTH, OTHER_INCOME, M_TH_FIRST_NAME, M_TH_LAST_NAME, ");
			sql.append(" M_TH_OLD_LAST_NAME, M_TITLE_CODE, M_COMPANY, M_POSITION, M_INCOME, ");
			sql.append(" M_HOME_TEL, M_OFFICE_TEL, M_PHONE_NO, DEGREE, CIS_NO, EMAIL_PRIMARY, ");
			sql.append(" EMAIL_SECONDARY, MOBILE_NO, CONTACT_TIME, PROFESSION, ");
			
			sql.append(" PROFESSION_OTHER, PREV_COMPANY, PREV_COMPANY_PHONE_NO, PREV_COMPANY_TITLE, ");
			sql.append(" PREV_WORK_YEAR, PREV_WORK_MONTH, CIRCULATION_INCOME, NET_PROFIT_INCOME, ");
			sql.append(" FREELANCE_INCOME, SAVING_INCOME, SORCE_OF_INCOME_OTHER, RECEIVE_INCOME_BANK, ");
			sql.append(" RECEIVE_INCOME_METHOD, MONTHLY_INCOME, MONTHLY_EXPENSE, ASSETS_AMOUNT, ");
			sql.append(" HR_BONUS, HR_INCOME, BRANCH_RECEIVE_CARD, PLACE_RECEIVE_CARD, VAT_REGIST_FLAG, ");
			
			sql.append(" VISA_TYPE, VISA_EXP_DATE, WORK_PERMIT_EXP_DATE, WORK_PERMIT_NO, DISCLOSURE_FLAG, ");
			sql.append(" ESTABLISHMENT_ADDR_FLAG,K_GROUP_FLAG,MATCH_ADDR_CARDLINK_FLAG,MATCH_ADDR_NCB_FLAG, ");
			sql.append(" DEBT_BURDEN, STAFF_FLAG, GROSS_INCOME, SRC_OTH_INC_BONUS, SRC_OTH_INC_COMMISSION, ");
			sql.append(" SRC_OTH_INC_OTHER, RELATIONSHIP_TYPE, RELATIONSHIP_TYPE_DESC, ");
			sql.append(" RECEIVE_INCOME_BANK_BRANCH, INC_POLICY_SEGMENT_CODE, TOT_VERIFIED_INCOME, ");
			
			sql.append(" TYPE_OF_FIN, REFER_CRITERIA, CIS_PRIM_SEGMENT, ");
			sql.append(" CIS_PRIM_SUB_SEGMENT, CIS_SEC_SEGMENT, CIS_SEC_SUB_SEGMENT,SEQ, ");
			sql.append(" BUS_REGIST_ID, BUS_REGIST_DATE, BUS_REGIST_REQ_NO, NO_MAIN_CARD, ");
			sql.append(" NO_SUP_CARD, PERMANENT_STAFF, PHONE_NO_BOL, FREELANCE_TYPE,");
			sql.append(" CREATE_DATE, CREATE_BY, UPDATE_DATE, UPDATE_BY,  ");
			sql.append(" SORCE_OF_INCOME, OCCUPATION_OTHER, COMPANY_CIS_ID, COMPANY_ORG_ID_BOL, ");
			sql.append(" TH_TITLE_DESC, EN_TITLE_DESC, M_TITLE_DESC, BUREAU_REQUIRED_FLAG ,PEGA_PHONE_TYPE,");
			sql.append(" PEGA_PHONE_NO,PEGA_PHONE_EXT,CID_ISSUE_DATE,EMAIL_ID_REF,MOBILE_ID_REF,COMPLETE_DATA,PROCEDURE_TYPE_INCOME, ");
			sql.append(" BRANCH_RECEIVE_CARD_NAME, PERSONAL_ERROR, MAIN_CARD_HOLDER_NAME , MAIN_CARD_NO, ");
			//Add "COMPANY_TYPE" as of KPL_Additional
			sql.append(" NUMBER_OF_ISSUER, SELF_DECLARE_LIMIT ,CIS_CUST_TYPE, CONSENT_MODEL_FLAG, ID_NO_CONSENT, COMPANY_TYPE, PAYROLL_ACCOUNT_NO,APPLICATION_INCOME, ");
			// Add PAYROLL_DATE of CR247
			sql.append(" NCB_TRAN_NO, CONSENT_REF_NO, NO_OF_CHILD, PAYROLL_DATE, BIRTH_DATE_CONSENT, PLACE_CONSENT, WITNESS_CONSENT,CONSENT_DATE_INFO, ");
			sql.append(" CIS_PRIM_DESC,CIS_PRIM_SUB_DESC,CIS_SEC_DESC,CIS_SEC_SUB_DESC,LINE_ID,INCOME_RESOURCE,INCOME_RESOURCE_OTHER,COUNTRY_OF_INCOME) ");
			sql.append(" VALUES(?,?,?,?,?,  ?,?,?,?,?,  ?,?,?,?,?,  ?,?,?,?,?,?,  ?,?,?,?, ");
			sql.append(" ?,?,?,?,?,  ?,?,?,?,?,  ?,?,?,?,?,  ?,?,?,?,?,?,  ?,?,?,?, ");
			sql.append(" ?,?,?,?,  ?,?,?,?,  ?,?,?,?,  ?,?,?,?,  ?,?,?,?,?, ");
			sql.append(" ?,?,?,?,?,  ?,?,?,?,  ?,?,?,?,?,  ?,?,?,  ?,?,?,  ");
			sql.append(" ?,?,?,  ?,?,?,?,  ?,?,?,?,  ?,?,?,?,  ?,?,?,?,");
			sql.append(" ?,?,?,?,  ?,?,?,? ,?,?,?,?, ?,?,?,?, ");
			//Add extra ? for COMPANY_TYPE field
			sql.append(" ?,?,?,?, ?,?,?,?, ? , ?,?,?,  ");
			sql.append(" ?, ?, ?, ?, ?, ?, ?, ?, ");
			sql.append(" ?,?,?,?,?,?,?,? ) ");
			String dSql = String.valueOf(sql);
			logger.debug("Sql=" + dSql);
			ps = conn.prepareStatement(dSql);
			int cnt = 1;
			ps.setString(cnt++, personalInfoM.getPersonalId());
			ps.setString(cnt++, personalInfoM.getIdno());
			ps.setString(cnt++, personalInfoM.getApplicationGroupId());
			ps.setString(cnt++, personalInfoM.getPersonalType());
			ps.setString(cnt++, personalInfoM.getCustomerType());
			
			ps.setString(cnt++, personalInfoM.getMailingAddress());
			ps.setString(cnt++, personalInfoM.getThTitleCode());
			ps.setString(cnt++, personalInfoM.getThFirstName());
			ps.setString(cnt++, personalInfoM.getThLastName());
			ps.setString(cnt++, personalInfoM.getThMidName());
			
			ps.setString(cnt++, personalInfoM.getEnTitleCode());
			ps.setString(cnt++, personalInfoM.getEnFirstName());
			ps.setString(cnt++, personalInfoM.getEnLastName());
			ps.setString(cnt++, personalInfoM.getEnMidName());
			ps.setString(cnt++, personalInfoM.getGender());
			
			ps.setDate(cnt++, personalInfoM.getBirthDate());
			ps.setString(cnt++, personalInfoM.getRace());
			ps.setString(cnt++, personalInfoM.getNationality());
			ps.setString(cnt++, personalInfoM.getMarried());
			ps.setString(cnt++, personalInfoM.getCidType());
			ps.setDate(cnt++, personalInfoM.getCidExpDate());

			ps.setString(cnt++, personalInfoM.getOccupation());
			ps.setString(cnt++, personalInfoM.getBusinessType());
			ps.setString(cnt++, personalInfoM.getBusinessTypeOther());
			ps.setString(cnt++, personalInfoM.getBusinessNature());
			///
			
			ps.setString(cnt++, personalInfoM.getPositionCode());
			ps.setString(cnt++, personalInfoM.getPositionDesc());
			ps.setString(cnt++, personalInfoM.getPositionLevel());
			ps.setBigDecimal(cnt++, personalInfoM.getSalary());
			ps.setString(cnt++, personalInfoM.getEmploymentType());

			ps.setBigDecimal(cnt++, personalInfoM.getTotWorkYear());
			ps.setBigDecimal(cnt++, personalInfoM.getTotWorkMonth());
			ps.setBigDecimal(cnt++, personalInfoM.getOtherIncome());
			ps.setString(cnt++, personalInfoM.getmThFirstName());
			ps.setString(cnt++, personalInfoM.getmThLastName());
			
			ps.setString(cnt++, personalInfoM.getmThOldLastName());
			ps.setString(cnt++, personalInfoM.getmTitleCode());
			ps.setString(cnt++, personalInfoM.getmCompany());
			ps.setString(cnt++, personalInfoM.getmPosition());
			ps.setBigDecimal(cnt++, personalInfoM.getmIncome());
			
			ps.setString(cnt++, personalInfoM.getmHomeTel());
			ps.setString(cnt++, personalInfoM.getmOfficeTel());
			ps.setString(cnt++, personalInfoM.getmPhoneNo());
			ps.setString(cnt++, personalInfoM.getDegree());
			ps.setString(cnt++, personalInfoM.getCisNo());
			ps.setString(cnt++, personalInfoM.getEmailPrimary());
			
			ps.setString(cnt++, personalInfoM.getEmailSecondary());
			ps.setString(cnt++, personalInfoM.getMobileNo());
			ps.setString(cnt++, personalInfoM.getContactTime());
			ps.setString(cnt++, personalInfoM.getProfession());
			
			///

			ps.setString(cnt++, personalInfoM.getProfessionOther());
			ps.setString(cnt++, personalInfoM.getPrevCompany());
			ps.setString(cnt++, personalInfoM.getPrevCompanyPhoneNo());
			ps.setString(cnt++, personalInfoM.getPrevCompanyTitle());

			ps.setBigDecimal(cnt++, personalInfoM.getPrevWorkYear());
			ps.setBigDecimal(cnt++, personalInfoM.getPrevWorkMonth());
			ps.setBigDecimal(cnt++, personalInfoM.getCirculationIncome());
			ps.setBigDecimal(cnt++, personalInfoM.getNetProfitIncome());
			
			ps.setBigDecimal(cnt++, personalInfoM.getFreelanceIncome());
			ps.setBigDecimal(cnt++, personalInfoM.getSavingIncome());
			ps.setString(cnt++, personalInfoM.getSorceOfIncomeOther());
			ps.setString(cnt++, personalInfoM.getReceiveIncomeBank());
			
			ps.setString(cnt++, personalInfoM.getReceiveIncomeMethod());
			ps.setBigDecimal(cnt++, personalInfoM.getMonthlyIncome());
			ps.setBigDecimal(cnt++, personalInfoM.getMonthlyExpense());
			ps.setBigDecimal(cnt++, personalInfoM.getAssetsAmount());
			
			ps.setBigDecimal(cnt++, personalInfoM.getHrBonus());
			ps.setBigDecimal(cnt++, personalInfoM.getHrIncome());
			ps.setString(cnt++, personalInfoM.getBranchReceiveCard());
			ps.setString(cnt++, personalInfoM.getPlaceReceiveCard());
			ps.setString(cnt++, personalInfoM.getVatRegistFlag());
			
			///
			
			ps.setString(cnt++, personalInfoM.getVisaType());
			ps.setDate(cnt++, personalInfoM.getVisaExpDate());
			ps.setDate(cnt++, personalInfoM.getWorkPermitExpDate());
			ps.setString(cnt++, personalInfoM.getWorkPermitNo());
			ps.setString(cnt++, personalInfoM.getDisclosureFlag());
			
			ps.setString(cnt++, personalInfoM.getEstablishmentAddrFlag());
			ps.setString(cnt++, personalInfoM.getkGroupFlag());
			ps.setString(cnt++, personalInfoM.getMatchAddrCardlinkFlag());
			ps.setString(cnt++, personalInfoM.getMatchAddrNcbFlag());
			
			ps.setBigDecimal(cnt++, personalInfoM.getDebtBurden());
			ps.setString(cnt++, personalInfoM.getStaffFlag());
			ps.setBigDecimal(cnt++, personalInfoM.getGrossIncome());
			ps.setString(cnt++, personalInfoM.getSrcOthIncBonus());
			ps.setString(cnt++, personalInfoM.getSrcOthIncCommission());
			
			ps.setString(cnt++, personalInfoM.getSrcOthIncOther());
			ps.setString(cnt++, personalInfoM.getRelationshipType());
			ps.setString(cnt++, personalInfoM.getRelationshipTypeDesc());
			
			ps.setString(cnt++, personalInfoM.getReceiveIncomeBankBranch());
			ps.setString(cnt++, personalInfoM.getIncPolicySegmentCode());
			ps.setBigDecimal(cnt++, personalInfoM.getTotVerifiedIncome());
			
			ps.setString(cnt++, personalInfoM.getTypeOfFin());
			ps.setString(cnt++, personalInfoM.getReferCriteria());
			ps.setString(cnt++, personalInfoM.getCisPrimSegment());
			
			ps.setString(cnt++, personalInfoM.getCisPrimSubSegment());
			ps.setString(cnt++, personalInfoM.getCisSecSegment());
			ps.setString(cnt++, personalInfoM.getCisSecSubSegment());
			ps.setInt(cnt++, personalInfoM.getSeq());
			
			ps.setString(cnt++, personalInfoM.getBusRegistId());
			ps.setDate(cnt++, personalInfoM.getBusRegistDate());
			ps.setString(cnt++, personalInfoM.getBusRegistReqNo());
			ps.setInt(cnt++,FormatUtil.getInt(personalInfoM.getNoMainCard()));
			
			ps.setInt(cnt++,FormatUtil.getInt(personalInfoM.getNoSupCard()));
			ps.setString(cnt++, personalInfoM.getPermanentStaff());
			ps.setString(cnt++, personalInfoM.getPhoneNoBol());
			ps.setString(cnt++, personalInfoM.getFreelanceType());
			
			ps.setTimestamp(cnt++, ApplicationDate.getTimestamp());
			ps.setString(cnt++, personalInfoM.getCreateBy());
			ps.setTimestamp(cnt++, ApplicationDate.getTimestamp());
			ps.setString(cnt++, personalInfoM.getUpdateBy());
			
			ps.setString(cnt++, personalInfoM.getSorceOfIncome());
			ps.setString(cnt++, personalInfoM.getOccpationOther());
			ps.setString(cnt++, personalInfoM.getCompanyCISId());
			ps.setString(cnt++, personalInfoM.getCompanyOrgIdBol());
			
			ps.setString(cnt++, personalInfoM.getThTitleDesc());
			ps.setString(cnt++, personalInfoM.getEnTitleDesc());
			ps.setString(cnt++, personalInfoM.getmTitleDesc());
			ps.setString(cnt++, personalInfoM.getBureauRequiredFlag());
			
			ps.setString(cnt++, personalInfoM.getPegaPhoneType());
			ps.setString(cnt++, personalInfoM.getPegaPhoneNo());
			ps.setString(cnt++, personalInfoM.getPegaPhoneExt());
			ps.setDate(cnt++, personalInfoM.getCidIssueDate());
			
			ps.setString(cnt++, personalInfoM.getEmailIdRef());
			ps.setString(cnt++, personalInfoM.getMobileIdRef());
			ps.setString(cnt++, personalInfoM.getCompleteData());
			ps.setString(cnt++, personalInfoM.getProcedureTypeOfIncome());
			
			ps.setString(cnt++, personalInfoM.getBranchReceiveCardName());
			ps.setString(cnt++, personalInfoM.getPersonalError());
			
			ps.setString(cnt++, personalInfoM.getMainCardHolderName());
			ps.setString(cnt++, personalInfoM.getMainCardNo());
			ps.setBigDecimal(cnt++, personalInfoM.getNumberOfIssuer());
			ps.setBigDecimal(cnt++, personalInfoM.getSelfDeclareLimit());
			ps.setString(cnt++, personalInfoM.getCisCustType());
			ps.setString(cnt++, personalInfoM.getConsentModelFlag());

			ps.setString(cnt++, personalInfoM.getIdNoConsent() );
			
			//KPL Additional COMPANY_TYPE
			ps.setString(cnt++, personalInfoM.getSpecialMerchantType());
			ps.setString(cnt++, personalInfoM.getPayrollAccountNo());
			
			ps.setBigDecimal(cnt++, personalInfoM.getApplicationIncome());
			
			ps.setString(cnt++, personalInfoM.getNcbTranNo());
			ps.setString(cnt++, personalInfoM.getConsentRefNo());
			ps.setBigDecimal(cnt++, personalInfoM.getNoOfChild());
			ps.setString(cnt++, personalInfoM.getPayrollDate());
			ps.setDate(cnt++, personalInfoM.getBirthDateConsent());
			ps.setString(cnt++, personalInfoM.getPlaceConsent());
			ps.setString(cnt++, personalInfoM.getWitnessConsent());
			ps.setDate(cnt++, personalInfoM.getConsentDate());
			
			ps.setString(cnt++, personalInfoM.getCisPrimDesc());
			ps.setString(cnt++, personalInfoM.getCisPrimSubDesc());
			ps.setString(cnt++, personalInfoM.getCisSecDesc());
			ps.setString(cnt++, personalInfoM.getCisSecSubDesc());
			ps.setString(cnt++, personalInfoM.getLineId());
			ps.setString(cnt++, personalInfoM.getIncomeResource());
			ps.setString(cnt++, personalInfoM.getIncomeResourceOther());
			ps.setString(cnt++, personalInfoM.getCountryOfIncome());
			ps.executeUpdate();
		} catch (Exception e) {
			logger.fatal(e.getLocalizedMessage());
			throw new ApplicationException(e.getMessage());
		} finally {
			try {
				closeConnection(conn, ps);
			} catch (Exception e) {
				logger.fatal(e.getLocalizedMessage());
			}
		}
	}

	@Override
	public void deleteOrigPersonalInfoM(String applicationGroupId, String personalId) throws ApplicationException {
		
		OrigCardLinkCardDAO cardLinkCardDAO = ORIGDAOFactory.getCardLinkCardDAO();
		OrigCardLinkCustomerDAO cardLinkCustomerDAO = ORIGDAOFactory.getCardLinkCustomerDAO();
		OrigKYCDAO kycDAO = ORIGDAOFactory.getKYCDAO();
		OrigNCBDocumentDAO ncbDocumentDAO = ORIGDAOFactory.getNCBDocumentDAO();
		OrigNCBDocumentHistoryDAO ncbDocumentHistDAO = ORIGDAOFactory.getNCBDocumentHistoryDAO();
		OrigPersonalAddressDAO personalAddressDAO = ORIGDAOFactory.getPersonalAddressDAO();
		OrigPersonalRelationDAO personalRelationDAO = ORIGDAOFactory.getPersonalRelationDAO();
		OrigIncomeDAO incomeDAO = ORIGDAOFactory.getIncomeDAO();
		OrigIncomeSourceDAO incomeSrcDAO = ORIGDAOFactory.getIncomeSourceDAO();
		OrigDebtInfoDAO debtDAO = ORIGDAOFactory.getDebtInfoDAO();
		OrigNCBInfoDAO ncbDAO = ORIGDAOFactory.getNCBInfoDAO();
		OrigVerificationResultDAO verificationDAO = ORIGDAOFactory.getVerificationResultDAO();
		OrigBScoreDAO bscoreDAO = ORIGDAOFactory.getBScoreDAO();
		OrigPersonalAccountDAO accountDAO = ORIGDAOFactory.getAccountDAO();
		
		if(Util.empty(personalId)) {
			ArrayList<String> personalList = selectPersonalIdORIG_PERSONAL_INFO(applicationGroupId);
			if(!Util.empty(personalList)) {
				for(String personalID : personalList) {
					cardLinkCardDAO.deleteOrigCardlinkCardM(personalID, null);
					cardLinkCustomerDAO.deleteOrigCardlinkCustomerM(personalID, null);
					kycDAO.deleteOrigKYCM(personalID);
					ncbDocumentDAO.deleteOrigNCBDocumentM(personalID, null);
					ncbDocumentHistDAO.deleteOrigNCBDocHistoryM(personalID, null);
					personalAddressDAO.deleteOrigAddressM(personalID, null);
					personalRelationDAO.deleteOrigPersonalRelationM(personalID, null, null);
					incomeDAO.deleteOrigIncomeInfoM(personalID, null);
					incomeSrcDAO.deleteOrigIncomeSourceM(personalID, null);
					debtDAO.deleteOrigDebtInfoM(personalID, null);
					ncbDAO.deleteOrigNcbInfoM(personalID, null);
					verificationDAO.deleteOrigVerificationResultM(personalID, MConstant.REF_LEVEL.PERSONAL_INFO, null);
					bscoreDAO.deleteOrigBScoreM(personalId, null);
					accountDAO.deleteOrigPersonalAccountM(personalId, null);
				}
			}
		} else {
			cardLinkCardDAO.deleteOrigCardlinkCardM(personalId, null);
			cardLinkCustomerDAO.deleteOrigCardlinkCustomerM(personalId, null);
			kycDAO.deleteOrigKYCM(personalId);
			ncbDocumentDAO.deleteOrigNCBDocumentM(personalId, null);
			ncbDocumentHistDAO.deleteOrigNCBDocHistoryM(personalId, null);
			personalAddressDAO.deleteOrigAddressM(personalId, null);
			personalRelationDAO.deleteOrigPersonalRelationM(personalId, null, null);
			incomeDAO.deleteOrigIncomeInfoM(personalId, null);
			incomeSrcDAO.deleteOrigIncomeSourceM(personalId, null);
			debtDAO.deleteOrigDebtInfoM(personalId, null);
			ncbDAO.deleteOrigNcbInfoM(personalId, null);
			verificationDAO.deleteOrigVerificationResultM(personalId, MConstant.REF_LEVEL.PERSONAL_INFO, null);
			bscoreDAO.deleteOrigBScoreM(personalId, null);
		}
		
		deleteTableORIG_PERSONAL_INFO(applicationGroupId, personalId);
	}

	private ArrayList<String> selectPersonalIdORIG_PERSONAL_INFO(
			String applicationGroupId) throws ApplicationException {
		PreparedStatement ps = null;
		ResultSet rs = null;
		Connection conn = null;
		ArrayList<String> personalList = new ArrayList<String>();
		try {
			//conn = Get Connection
			conn = getConnection();
			StringBuffer sql = new StringBuffer("");
			sql.append("SELECT PERSONAL_ID ");
			sql.append(" FROM ORIG_PERSONAL_INFO WHERE APPLICATION_GROUP_ID = ? ");
			String dSql = String.valueOf(sql);
			logger.debug("Sql=" + dSql);
			ps = conn.prepareStatement(dSql);
			rs = null;
			ps.setString(1, applicationGroupId);

			rs = ps.executeQuery();

			while(rs.next()) {
				personalList.add(rs.getString("PERSONAL_ID"));
			}

			return personalList;
		} catch (Exception e) {
			logger.fatal(e.getLocalizedMessage());
			throw new ApplicationException(e.getMessage());
		} finally {
			try {
				closeConnection(conn, rs, ps);
			} catch (Exception e) {
				logger.fatal(e.getLocalizedMessage());
			}
		}
	}

	private void deleteTableORIG_PERSONAL_INFO(String applicationGroupId, String personalId) 
			throws ApplicationException {
		PreparedStatement ps = null;
		Connection conn = null;
		try {
			//conn = Get Connection
			conn = getConnection();
			StringBuffer sql = new StringBuffer("");
			sql.append(" DELETE ORIG_PERSONAL_INFO ");
			sql.append(" WHERE APPLICATION_GROUP_ID = ? ");
			if(personalId != null && !personalId.isEmpty()) {
				sql.append(" AND PERSONAL_ID = ? ");
			}
			String dSql = String.valueOf(sql);
			logger.debug("Sql=" + dSql);
			ps = conn.prepareStatement(dSql);
			ps.setString(1, applicationGroupId);
			if(personalId != null && !personalId.isEmpty()) {
				ps.setString(2, personalId);
			}
			ps.executeUpdate();

		} catch (Exception e) {
			logger.fatal(e.getLocalizedMessage());
			throw new ApplicationException(e.getMessage());
		} finally {
			try {
				closeConnection(conn, ps);
			} catch (Exception e) {
				logger.fatal(e.getLocalizedMessage());
				throw new ApplicationException(e.getMessage());
			}
		}
	}
	@Override
	public ArrayList<PersonalInfoDataM> loadPersonalInfoDocument(String applicationGroupId,String personalId, Connection conn) throws ApplicationException {
		ArrayList<PersonalInfoDataM> personalInfos = selectTableORIG_PERSONAL_INFO(applicationGroupId,conn);
		if(!Util.empty(personalInfos)) {
			for(PersonalInfoDataM personalInfo: personalInfos){
				if(null!=personalInfo.getPersonalId()&&personalInfo.getPersonalId().equals(personalId)){
					OrigVerificationResultDAO verificationDAO = ORIGDAOFactory.getVerificationResultDAO();
					VerificationResultDataM verificationResult = verificationDAO.loadVerificationResultDocument(personalId,MConstant.REF_LEVEL.PERSONAL_INFO,conn);
					if(!Util.empty(verificationResult)) {
						personalInfo.setVerificationResult(verificationResult);
					}
				}
			}
		}
		return personalInfos;
	}
	@Override
	public ArrayList<PersonalInfoDataM> loadOrigPersonalInfoVlink(String applicationGroupId, Connection conn)throws ApplicationException {
		ArrayList<PersonalInfoDataM> personals = selectTableORIG_PERSONAL_INFO(applicationGroupId,conn);
		if(!Util.empty(personals)) {
			for(PersonalInfoDataM personal: personals) {
				OrigCardLinkCardDAO cardLinkCardDAO = ORIGDAOFactory.getCardLinkCardDAO();
				ArrayList<CardlinkCardDataM> cardlinkCards = cardLinkCardDAO.loadOrigCardlinkCardM(personal.getPersonalId(),conn);
				if(cardlinkCards != null) {
					personal.setCardLinkCards(cardlinkCards);
				}
				OrigCardLinkCustomerDAO cardLinkCustomerDAO = ORIGDAOFactory.getCardLinkCustomerDAO();
				ArrayList<CardlinkCustomerDataM> cardlinkCusts = cardLinkCustomerDAO.loadOrigCardlinkCustomerM(personal.getPersonalId(),conn);
				if(cardlinkCusts != null){
					personal.setCardLinkCustomers(cardlinkCusts);
				}
			}
		}
		return personals;
	}
	@Override
	public ArrayList<PersonalInfoDataM> loadOrigPersonalInfoM(
			String applicationGroupId, Connection conn)
			throws ApplicationException {
		ArrayList<PersonalInfoDataM> result = selectTableORIG_PERSONAL_INFO(applicationGroupId,conn);
		
		if(!Util.empty(result)) {
			for(PersonalInfoDataM personalInfoM: result) {
				OrigCardLinkCardDAO cardLinkCardDAO = ORIGDAOFactory.getCardLinkCardDAO();
				ArrayList<CardlinkCardDataM> cardLinkCardList = cardLinkCardDAO.loadOrigCardlinkCardM(personalInfoM.getPersonalId(),conn);
				if(cardLinkCardList != null) {
					personalInfoM.setCardLinkCards(cardLinkCardList);
				}
				
				OrigCardLinkCustomerDAO cardLinkCustomerDAO = ORIGDAOFactory.getCardLinkCustomerDAO();
				ArrayList<CardlinkCustomerDataM> cardLinkCustList = cardLinkCustomerDAO.loadOrigCardlinkCustomerM(personalInfoM.getPersonalId(),conn);
				if(cardLinkCustList != null) {
					personalInfoM.setCardLinkCustomers(cardLinkCustList);
				}
				
				OrigKYCDAO kycDAO = ORIGDAOFactory.getKYCDAO();
				KYCDataM kycM = kycDAO.loadOrigKYCM(personalInfoM.getPersonalId(),conn);
				if(kycM != null) {
					personalInfoM.setKyc(kycM);
				}
				
//				OrigNCBDocumentDAO ncbDocumentDAO = ORIGDAOFactory.getNCBDocumentDAO();
//				ArrayList<NCBDocumentDataM> ncbDocList = ncbDocumentDAO.loadOrigNCBDocumentM(personalInfoM.getPersonalId());
//				if(ncbDocList != null && !ncbDocList.isEmpty()) {
//					personalInfoM.setNcbDocuments(ncbDocList);
//				}
				
				OrigNCBDocumentHistoryDAO ncbDocumentHistDAO = ORIGDAOFactory.getNCBDocumentHistoryDAO();
				ArrayList<NCBDocumentHistoryDataM> ncbDocHistList = ncbDocumentHistDAO.loadOrigNCBDocHistoryM(personalInfoM.getPersonalId(),conn);
				if(ncbDocHistList != null && !ncbDocHistList.isEmpty()) {
					personalInfoM.setNcbDocumentHistorys(ncbDocHistList);
				}
				
				OrigPersonalAddressDAO personalAddressDAO = ORIGDAOFactory.getPersonalAddressDAO();
				ArrayList<AddressDataM> addressList = personalAddressDAO.loadOrigAddressM(personalInfoM.getPersonalId(),conn);
				if(addressList != null && !addressList.isEmpty()) {
					personalInfoM.setAddresses(addressList);
				}
				
				OrigPersonalRelationDAO personalRelationDAO = ORIGDAOFactory.getPersonalRelationDAO();
				ArrayList<PersonalRelationDataM> personalRelationList = personalRelationDAO.loadOrigPersonalRelationM(personalInfoM.getPersonalId(),conn);
				if(!Util.empty(personalRelationList)) {
					personalInfoM.setPersonalRelations(personalRelationList);
				}
				
				OrigIncomeDAO incomeDAO = ORIGDAOFactory.getIncomeDAO();
				ArrayList<IncomeInfoDataM> incomeList = incomeDAO.loadOrigIncomeInfoM(personalInfoM.getPersonalId(),conn);
				if(!Util.empty(incomeList)) {
					personalInfoM.setIncomeInfos(incomeList);
				}
				
				OrigIncomeSourceDAO incomeSrcDAO = ORIGDAOFactory.getIncomeSourceDAO();
				ArrayList<IncomeSourceDataM> incomeSrcList = incomeSrcDAO.loadOrigIncomeSourceM(personalInfoM.getPersonalId(),conn);
				if(!Util.empty(incomeSrcList)) {
					personalInfoM.setIncomeSources(incomeSrcList);
				}
				
				OrigDebtInfoDAO debtDAO = ORIGDAOFactory.getDebtInfoDAO();
				ArrayList<DebtInfoDataM> debtList = debtDAO.loadOrigDebtInfoM(personalInfoM.getPersonalId(),conn);
				if(!Util.empty(debtList)) {
					personalInfoM.setDebtInfos(debtList);
				}
				
				OrigNCBInfoDAO ncbDAO = ORIGDAOFactory.getNCBInfoDAO();
				ArrayList<NcbInfoDataM> ncbList = ncbDAO.loadOrigNcbInfos(personalInfoM.getPersonalId(),conn);
				if(!Util.empty(ncbList)) {
					personalInfoM.setNcbInfos(ncbList);
				}
				
				OrigVerificationResultDAO verificationDAO = ORIGDAOFactory.getVerificationResultDAO();
				VerificationResultDataM verificationM = verificationDAO.loadOrigVerificationResultM(personalInfoM.getPersonalId(), MConstant.REF_LEVEL.PERSONAL_INFO,conn);
				if(!Util.empty(verificationM)) {
					personalInfoM.setVerificationResult(verificationM);
				}
				
				OrigBScoreDAO bScoreDAO = ORIGDAOFactory.getBScoreDAO();
				ArrayList<BScoreDataM> bScoreList = bScoreDAO.loadOrigBScoreM(personalInfoM.getPersonalId(),conn);
				if(!Util.empty(bScoreList)) {
					personalInfoM.setBScores(bScoreList);
				}
				OrigPersonalAccountDAO accountDAO = ORIGDAOFactory.getAccountDAO();
				ArrayList<AccountDataM> accountList = accountDAO.loadOrigPersonalAccountM(personalInfoM.getPersonalId(),conn);
				if(!Util.empty(accountList)) {
					personalInfoM.setAccounts(accountList);
				}
			}
		}
		return result;
	}
	@Override
	public ArrayList<PersonalInfoDataM> loadOrigPersonalInfoM(String applicationGroupId) throws ApplicationException {
		ArrayList<PersonalInfoDataM> result = selectTableORIG_PERSONAL_INFO(applicationGroupId);
		
		if(!Util.empty(result)) {
			for(PersonalInfoDataM personalInfoM: result) {
				OrigCardLinkCardDAO cardLinkCardDAO = ORIGDAOFactory.getCardLinkCardDAO();
				ArrayList<CardlinkCardDataM> cardLinkCardList = cardLinkCardDAO.loadOrigCardlinkCardM(personalInfoM.getPersonalId());
				if(cardLinkCardList != null) {
					personalInfoM.setCardLinkCards(cardLinkCardList);
				}
				
				OrigCardLinkCustomerDAO cardLinkCustomerDAO = ORIGDAOFactory.getCardLinkCustomerDAO();
				ArrayList<CardlinkCustomerDataM> cardLinkCustList = cardLinkCustomerDAO.loadOrigCardlinkCustomerM(personalInfoM.getPersonalId());
				if(cardLinkCustList != null) {
					personalInfoM.setCardLinkCustomers(cardLinkCustList);
				}
				
				OrigKYCDAO kycDAO = ORIGDAOFactory.getKYCDAO();
				KYCDataM kycM = kycDAO.loadOrigKYCM(personalInfoM.getPersonalId());
				if(kycM != null) {
					personalInfoM.setKyc(kycM);
				}
				
//				OrigNCBDocumentDAO ncbDocumentDAO = ORIGDAOFactory.getNCBDocumentDAO();
//				ArrayList<NCBDocumentDataM> ncbDocList = ncbDocumentDAO.loadOrigNCBDocumentM(personalInfoM.getPersonalId());
//				if(ncbDocList != null && !ncbDocList.isEmpty()) {
//					personalInfoM.setNcbDocuments(ncbDocList);
//				}
				
				OrigNCBDocumentHistoryDAO ncbDocumentHistDAO = ORIGDAOFactory.getNCBDocumentHistoryDAO();
				ArrayList<NCBDocumentHistoryDataM> ncbDocHistList = ncbDocumentHistDAO.loadOrigNCBDocHistoryM(personalInfoM.getPersonalId());
				if(ncbDocHistList != null && !ncbDocHistList.isEmpty()) {
					personalInfoM.setNcbDocumentHistorys(ncbDocHistList);
				}
				
				OrigPersonalAddressDAO personalAddressDAO = ORIGDAOFactory.getPersonalAddressDAO();
				ArrayList<AddressDataM> addressList = personalAddressDAO.loadOrigAddressM(personalInfoM.getPersonalId());
				if(addressList != null && !addressList.isEmpty()) {
					personalInfoM.setAddresses(addressList);
				}
				
				OrigPersonalRelationDAO personalRelationDAO = ORIGDAOFactory.getPersonalRelationDAO();
				ArrayList<PersonalRelationDataM> personalRelationList = personalRelationDAO.loadOrigPersonalRelationM(personalInfoM.getPersonalId());
				if(!Util.empty(personalRelationList)) {
					personalInfoM.setPersonalRelations(personalRelationList);
				}
				
				OrigIncomeDAO incomeDAO = ORIGDAOFactory.getIncomeDAO();
				ArrayList<IncomeInfoDataM> incomeList = incomeDAO.loadOrigIncomeInfoM(personalInfoM.getPersonalId());
				if(!Util.empty(incomeList)) {
					personalInfoM.setIncomeInfos(incomeList);
				}
				
				OrigIncomeSourceDAO incomeSrcDAO = ORIGDAOFactory.getIncomeSourceDAO();
				ArrayList<IncomeSourceDataM> incomeSrcList = incomeSrcDAO.loadOrigIncomeSourceM(personalInfoM.getPersonalId());
				if(!Util.empty(incomeSrcList)) {
					personalInfoM.setIncomeSources(incomeSrcList);
				}
				
				OrigDebtInfoDAO debtDAO = ORIGDAOFactory.getDebtInfoDAO();
				ArrayList<DebtInfoDataM> debtList = debtDAO.loadOrigDebtInfoM(personalInfoM.getPersonalId());
				if(!Util.empty(debtList)) {
					personalInfoM.setDebtInfos(debtList);
				}
				
				OrigNCBInfoDAO ncbDAO = ORIGDAOFactory.getNCBInfoDAO();
				ArrayList<NcbInfoDataM> ncbList = ncbDAO.loadOrigNcbInfos(personalInfoM.getPersonalId());
				if(!Util.empty(ncbList)) {
					personalInfoM.setNcbInfos(ncbList);
				}
				
				OrigVerificationResultDAO verificationDAO = ORIGDAOFactory.getVerificationResultDAO();
				VerificationResultDataM verificationM = verificationDAO.loadOrigVerificationResultM(personalInfoM.getPersonalId(), MConstant.REF_LEVEL.PERSONAL_INFO);
				if(!Util.empty(verificationM)) {
					personalInfoM.setVerificationResult(verificationM);
				}
				OrigBScoreDAO bScoreDAO = ORIGDAOFactory.getBScoreDAO();
				ArrayList<BScoreDataM> bScoreList = bScoreDAO.loadOrigBScoreM(personalInfoM.getPersonalId());
				if(!Util.empty(bScoreList)) {
					personalInfoM.setBScores(bScoreList);
				}
				OrigPersonalAccountDAO accountDAO = ORIGDAOFactory.getAccountDAO();
				ArrayList<AccountDataM> accountList = accountDAO.loadOrigPersonalAccountM(personalInfoM.getPersonalId());
				if(!Util.empty(accountList)) {
					personalInfoM.setAccounts(accountList);
				}
			}
		}
		return result;
	}
	private ArrayList<PersonalInfoDataM> selectTableORIG_PERSONAL_INFO(String applicationGroupId) throws ApplicationException {
		Connection conn = null;
		try{
			conn = getConnection();
			return selectTableORIG_PERSONAL_INFO(applicationGroupId,conn);
		}catch (Exception e) {
			logger.fatal(e.getLocalizedMessage());
			throw new ApplicationException(e.getMessage());
		} finally {
			try {
				closeConnection(conn);
			} catch (Exception e) {
				logger.fatal(e.getLocalizedMessage());
				throw new ApplicationException(e.getMessage());
			}
		}
	}
	private ArrayList<PersonalInfoDataM> selectTableORIG_PERSONAL_INFO(String applicationGroupId,Connection conn) throws ApplicationException {
		PreparedStatement ps = null;
		ResultSet rs = null;
		ArrayList<PersonalInfoDataM> personalInfoList = new ArrayList<PersonalInfoDataM>();
		try {
			StringBuffer sql = new StringBuffer("");
			sql.append("SELECT PERSONAL_ID, IDNO, APPLICATION_GROUP_ID, PERSONAL_TYPE, CUSTOMER_TYPE, ");
			sql.append(" MAILING_ADDRESS, TH_TITLE_CODE, TH_FIRST_NAME, TH_LAST_NAME, TH_MID_NAME, ");
			sql.append(" EN_TITLE_CODE, EN_FIRST_NAME, EN_LAST_NAME, EN_MID_NAME, GENDER, ");
			sql.append(" BIRTH_DATE, RACE, NATIONALITY, MARRIED, CID_TYPE, CID_EXP_DATE, ");
			sql.append(" OCCUPATION, BUSINESS_TYPE, BUSINESS_TYPE_OTHER, BUSINESS_NATURE, ");
			
			sql.append(" POSITION_CODE, POSITION_DESC, POSITION_LEVEL, SALARY, EMPLOYMENT_TYPE, ");
			sql.append(" TOT_WORK_YEAR, TOT_WORK_MONTH, OTHER_INCOME, M_TH_FIRST_NAME, M_TH_LAST_NAME, ");
			sql.append(" M_TH_OLD_LAST_NAME, M_TITLE_CODE, M_COMPANY, M_POSITION, M_INCOME, ");
			sql.append(" M_HOME_TEL, M_OFFICE_TEL, M_PHONE_NO, DEGREE, CIS_NO, EMAIL_PRIMARY, ");
			sql.append(" EMAIL_SECONDARY, MOBILE_NO, CONTACT_TIME, PROFESSION, ");
			
			sql.append(" PROFESSION_OTHER, PREV_COMPANY, PREV_COMPANY_PHONE_NO, PREV_COMPANY_TITLE, ");
			sql.append(" PREV_WORK_YEAR, PREV_WORK_MONTH, CIRCULATION_INCOME, NET_PROFIT_INCOME, ");
			sql.append(" FREELANCE_INCOME, SAVING_INCOME, SORCE_OF_INCOME_OTHER, RECEIVE_INCOME_BANK, ");
			sql.append(" RECEIVE_INCOME_METHOD, MONTHLY_INCOME, MONTHLY_EXPENSE, ASSETS_AMOUNT, ");
			sql.append(" HR_BONUS, HR_INCOME, BRANCH_RECEIVE_CARD, BRANCH_RECEIVE_CARD_NAME, PLACE_RECEIVE_CARD, VAT_REGIST_FLAG, ");
			
			sql.append(" VISA_TYPE, VISA_EXP_DATE, WORK_PERMIT_EXP_DATE, WORK_PERMIT_NO, DISCLOSURE_FLAG, ");
			sql.append(" ESTABLISHMENT_ADDR_FLAG, K_GROUP_FLAG, MATCH_ADDR_CARDLINK_FLAG, MATCH_ADDR_NCB_FLAG, ");
			sql.append(" DEBT_BURDEN, STAFF_FLAG, GROSS_INCOME, SRC_OTH_INC_BONUS, SRC_OTH_INC_COMMISSION, ");
			sql.append(" SRC_OTH_INC_OTHER, RELATIONSHIP_TYPE, RELATIONSHIP_TYPE_DESC, ");
			sql.append(" RECEIVE_INCOME_BANK_BRANCH, INC_POLICY_SEGMENT_CODE, TOT_VERIFIED_INCOME, ");
			sql.append(" TYPE_OF_FIN, REFER_CRITERIA, CIS_PRIM_SEGMENT, ");
			sql.append(" CIS_PRIM_SUB_SEGMENT, CIS_SEC_SEGMENT, CIS_SEC_SUB_SEGMENT,SEQ, ");
			sql.append(" BUS_REGIST_ID, BUS_REGIST_DATE, BUS_REGIST_REQ_NO, NO_MAIN_CARD, ");
			sql.append(" NO_SUP_CARD, PERMANENT_STAFF, PHONE_NO_BOL, FREELANCE_TYPE,NO_OF_CHILD, ");
			sql.append(" CREATE_DATE, CREATE_BY, UPDATE_DATE, UPDATE_BY, SORCE_OF_INCOME, OCCUPATION_OTHER, ");
			sql.append(" COMPANY_CIS_ID, COMPANY_ORG_ID_BOL, TH_TITLE_DESC, EN_TITLE_DESC, M_TITLE_DESC, ");
			sql.append(" BUREAU_REQUIRED_FLAG,PEGA_PHONE_TYPE,PEGA_PHONE_NO,PEGA_PHONE_EXT,CID_ISSUE_DATE, ");
			sql.append(" EMAIL_ID_REF,MOBILE_ID_REF,COMPLETE_DATA,PROCEDURE_TYPE_INCOME,PERSONAL_ERROR,MAIN_CARD_HOLDER_NAME,MAIN_CARD_NO, ");
			sql.append(" NUMBER_OF_ISSUER, SELF_DECLARE_LIMIT, CIS_CUST_TYPE, CONSENT_MODEL_FLAG, ID_NO_CONSENT  ");
			sql.append(" , COMPANY_TYPE, PAYROLL_ACCOUNT_NO,APPLICATION_INCOME, "); //KPL Additional
			sql.append(" NCB_TRAN_NO, CONSENT_REF_NO, PAYROLL_DATE, BIRTH_DATE_CONSENT, PLACE_CONSENT, WITNESS_CONSENT ,CONSENT_DATE_INFO, ");
			sql.append(" CIS_PRIM_DESC,CIS_PRIM_SUB_DESC,CIS_SEC_DESC,CIS_SEC_SUB_DESC,LINE_ID,INCOME_RESOURCE,INCOME_RESOURCE_OTHER,COUNTRY_OF_INCOME ");
			sql.append(" FROM ORIG_PERSONAL_INFO WHERE APPLICATION_GROUP_ID = ? ");
			String dSql = String.valueOf(sql);
			logger.debug("Sql=" + dSql);
			ps = conn.prepareStatement(dSql);
			rs = null;
			ps.setString(1, applicationGroupId);

			rs = ps.executeQuery();

			while(rs.next()) {
				PersonalInfoDataM personalInfoM = new PersonalInfoDataM();
				personalInfoM.setPersonalId(rs.getString("PERSONAL_ID"));
				personalInfoM.setIdno(rs.getString("IDNO"));
				personalInfoM.setApplicationGroupId(rs.getString("APPLICATION_GROUP_ID"));
				personalInfoM.setPersonalType(rs.getString("PERSONAL_TYPE"));
				personalInfoM.setCustomerType(rs.getString("CUSTOMER_TYPE"));
				
				personalInfoM.setMailingAddress(rs.getString("MAILING_ADDRESS"));
				personalInfoM.setThTitleCode(rs.getString("TH_TITLE_CODE"));
				personalInfoM.setThFirstName(rs.getString("TH_FIRST_NAME"));
				personalInfoM.setThLastName(rs.getString("TH_LAST_NAME"));
				personalInfoM.setThMidName(rs.getString("TH_MID_NAME"));
				
				personalInfoM.setEnTitleCode(rs.getString("EN_TITLE_CODE"));
				personalInfoM.setEnFirstName(rs.getString("EN_FIRST_NAME"));
				personalInfoM.setEnLastName(rs.getString("EN_LAST_NAME"));
				personalInfoM.setEnMidName(rs.getString("EN_MID_NAME"));
				personalInfoM.setGender(rs.getString("GENDER"));
				
				personalInfoM.setBirthDate(rs.getDate("BIRTH_DATE"));
				personalInfoM.setRace(rs.getString("RACe"));
				personalInfoM.setNationality(rs.getString("NATIONALITY"));
				personalInfoM.setMarried(rs.getString("MARRIED"));
				personalInfoM.setCidType(rs.getString("CID_TYPE"));
				personalInfoM.setCidExpDate(rs.getDate("CID_EXP_DATE"));

				personalInfoM.setOccupation(rs.getString("OCCUPATION"));
				personalInfoM.setBusinessType(rs.getString("BUSINESS_TYPE"));
				personalInfoM.setBusinessTypeOther(rs.getString("BUSINESS_TYPE_OTHER"));
				personalInfoM.setBusinessNature(rs.getString("BUSINESS_NATURE"));
				///
				
				personalInfoM.setPositionCode(rs.getString("POSITION_CODE"));
				personalInfoM.setPositionDesc(rs.getString("POSITION_DESC"));
				personalInfoM.setPositionLevel(rs.getString("POSITION_LEVEL"));
				personalInfoM.setSalary(rs.getBigDecimal("SALARY"));
				personalInfoM.setEmploymentType(rs.getString("EMPLOYMENT_TYPE"));

				personalInfoM.setTotWorkYear(rs.getBigDecimal("TOT_WORK_YEAR"));
				personalInfoM.setTotWorkMonth(rs.getBigDecimal("TOT_WORK_MONTH"));
				personalInfoM.setOtherIncome(rs.getBigDecimal("OTHER_INCOME"));
				personalInfoM.setmThFirstName(rs.getString("M_TH_FIRST_NAME"));
				personalInfoM.setmThLastName(rs.getString("M_TH_LAST_NAME"));
				
				personalInfoM.setmThOldLastName(rs.getString("M_TH_OLD_LAST_NAME"));
				personalInfoM.setmTitleCode(rs.getString("M_TITLE_CODE"));
				personalInfoM.setmCompany(rs.getString("M_COMPANY"));
				personalInfoM.setmPosition(rs.getString("M_POSITION"));
				personalInfoM.setmIncome(rs.getBigDecimal("M_INCOME"));
				
				personalInfoM.setmHomeTel(rs.getString("M_HOME_TEL"));
				personalInfoM.setmOfficeTel(rs.getString("M_OFFICE_TEL"));
				personalInfoM.setmPhoneNo(rs.getString("M_PHONE_NO"));
				personalInfoM.setDegree(rs.getString("DEGREE"));
				personalInfoM.setCisNo(rs.getString("CIS_NO"));
				personalInfoM.setEmailPrimary(rs.getString("EMAIL_PRIMARY"));
				
				personalInfoM.setEmailSecondary(rs.getString("EMAIL_SECONDARY"));
				personalInfoM.setMobileNo(rs.getString("MOBILE_NO"));
				personalInfoM.setContactTime(rs.getString("CONTACT_TIME"));
				personalInfoM.setProfession(rs.getString("PROFESSION"));
				
				///

				personalInfoM.setProfessionOther(rs.getString("PROFESSION_OTHER"));
				personalInfoM.setPrevCompany(rs.getString("PREV_COMPANY"));
				personalInfoM.setPrevCompanyPhoneNo(rs.getString("PREV_COMPANY_PHONE_NO"));
				personalInfoM.setPrevCompanyTitle(rs.getString("PREV_COMPANY_TITLE"));

				personalInfoM.setPrevWorkYear(rs.getBigDecimal("PREV_WORK_YEAR"));
				personalInfoM.setPrevWorkMonth(rs.getBigDecimal("PREV_WORK_MONTH"));
				personalInfoM.setCirculationIncome(rs.getBigDecimal("CIRCULATION_INCOME"));
				personalInfoM.setNetProfitIncome(rs.getBigDecimal("NET_PROFIT_INCOME"));
				
				personalInfoM.setFreelanceIncome(rs.getBigDecimal("FREELANCE_INCOME"));
				personalInfoM.setSavingIncome(rs.getBigDecimal("SAVING_INCOME"));
				personalInfoM.setSorceOfIncomeOther(rs.getString("SORCE_OF_INCOME_OTHER"));
				personalInfoM.setReceiveIncomeBank(rs.getString("RECEIVE_INCOME_BANK"));
				
				personalInfoM.setReceiveIncomeMethod(rs.getString("RECEIVE_INCOME_METHOD"));
				personalInfoM.setMonthlyIncome(rs.getBigDecimal("MONTHLY_INCOME"));
				personalInfoM.setMonthlyExpense(rs.getBigDecimal("MONTHLY_EXPENSE"));
				personalInfoM.setAssetsAmount(rs.getBigDecimal("ASSETS_AMOUNT"));
				
				personalInfoM.setHrBonus(rs.getBigDecimal("HR_BONUS"));
				personalInfoM.setHrIncome(rs.getBigDecimal("HR_INCOME"));
				personalInfoM.setBranchReceiveCard(rs.getString("BRANCH_RECEIVE_CARD"));
				personalInfoM.setBranchReceiveCardName(rs.getString("BRANCH_RECEIVE_CARD_NAME"));
				personalInfoM.setPlaceReceiveCard(rs.getString("PLACE_RECEIVE_CARD"));
				personalInfoM.setVatRegistFlag(rs.getString("VAT_REGIST_FLAG"));
				
				///
				
				personalInfoM.setVisaType(rs.getString("VISA_TYPE"));
				personalInfoM.setVisaExpDate(rs.getDate("VISA_EXP_DATE"));
				personalInfoM.setWorkPermitExpDate(rs.getDate("WORK_PERMIT_EXP_DATE"));
				personalInfoM.setWorkPermitNo(rs.getString("WORK_PERMIT_NO"));
				personalInfoM.setDisclosureFlag(rs.getString("DISCLOSURE_FLAG"));
				
				personalInfoM.setEstablishmentAddrFlag(rs.getString("ESTABLISHMENT_ADDR_FLAG"));
				personalInfoM.setkGroupFlag(rs.getString("K_GROUP_FLAG"));
				personalInfoM.setMatchAddrCardlinkFlag(rs.getString("MATCH_ADDR_CARDLINK_FLAG"));
				personalInfoM.setMatchAddrNcbFlag(rs.getString("MATCH_ADDR_NCB_FLAG"));
				
				personalInfoM.setDebtBurden(rs.getBigDecimal("DEBT_BURDEN"));
				personalInfoM.setStaffFlag(rs.getString("STAFF_FLAG"));
				personalInfoM.setGrossIncome(rs.getBigDecimal("GROSS_INCOME"));
				personalInfoM.setSrcOthIncBonus(rs.getString("SRC_OTH_INC_BONUS"));
				personalInfoM.setSrcOthIncCommission(rs.getString("SRC_OTH_INC_COMMISSION"));
				
				personalInfoM.setSrcOthIncOther(rs.getString("SRC_OTH_INC_OTHER"));
				personalInfoM.setRelationshipType(rs.getString("RELATIONSHIP_TYPE"));
				personalInfoM.setRelationshipTypeDesc(rs.getString("RELATIONSHIP_TYPE_DESC"));
				
				personalInfoM.setReceiveIncomeBankBranch(rs.getString("RECEIVE_INCOME_BANK_BRANCH"));
				personalInfoM.setIncPolicySegmentCode(rs.getString("INC_POLICY_SEGMENT_CODE"));
				personalInfoM.setTotVerifiedIncome(rs.getBigDecimal("TOT_VERIFIED_INCOME"));
				
				personalInfoM.setTypeOfFin(rs.getString("TYPE_OF_FIN"));
				personalInfoM.setReferCriteria(rs.getString("REFER_CRITERIA"));
				personalInfoM.setCisPrimSegment(rs.getString("CIS_PRIM_SEGMENT"));
				personalInfoM.setCisPrimSubSegment(rs.getString("CIS_PRIM_SUB_SEGMENT"));
				personalInfoM.setCisSecSegment(rs.getString("CIS_SEC_SEGMENT"));
				personalInfoM.setCisSecSubSegment(rs.getString("CIS_SEC_SUB_SEGMENT"));				
				personalInfoM.setSeq(rs.getInt("SEQ"));
				
				personalInfoM.setBusRegistId(rs.getString("BUS_REGIST_ID"));
				personalInfoM.setBusRegistDate(rs.getDate("BUS_REGIST_DATE"));
				personalInfoM.setBusRegistReqNo(rs.getString("BUS_REGIST_REQ_NO"));
				personalInfoM.setNoMainCard(rs.getString("NO_MAIN_CARD"));
				
				personalInfoM.setNoSupCard(rs.getString("NO_SUP_CARD"));
				personalInfoM.setPermanentStaff(rs.getString("PERMANENT_STAFF"));
				personalInfoM.setPhoneNoBol(rs.getString("PHONE_NO_BOL"));
				personalInfoM.setFreelanceType(rs.getString("FREELANCE_TYPE"));
				personalInfoM.setNoOfChild(rs.getBigDecimal("NO_OF_CHILD"));
				
				personalInfoM.setCreateBy(rs.getString("CREATE_BY"));
				personalInfoM.setCreateDate(rs.getTimestamp("CREATE_DATE"));
				personalInfoM.setUpdateBy(rs.getString("UPDATE_BY"));
				personalInfoM.setUpdateDate(rs.getTimestamp("UPDATE_DATE"));
				personalInfoM.setSorceOfIncome(rs.getString("SORCE_OF_INCOME"));
				personalInfoM.setOccpationOther(rs.getString("OCCUPATION_OTHER"));
				
				personalInfoM.setCompanyCISId(rs.getString("COMPANY_CIS_ID"));
				personalInfoM.setCompanyOrgIdBol(rs.getString("COMPANY_ORG_ID_BOL"));
				personalInfoM.setThTitleDesc(rs.getString("TH_TITLE_DESC"));
				personalInfoM.setEnTitleDesc(rs.getString("EN_TITLE_DESC"));
				personalInfoM.setmTitleDesc(rs.getString("M_TITLE_DESC"));
				personalInfoM.setBureauRequiredFlag(rs.getString("BUREAU_REQUIRED_FLAG"));
				
				personalInfoM.setPegaPhoneType(rs.getString("PEGA_PHONE_TYPE"));
				personalInfoM.setPegaPhoneNo(rs.getString("PEGA_PHONE_NO"));
				personalInfoM.setPegaPhoneExt(rs.getString("PEGA_PHONE_EXT"));
				personalInfoM.setCidIssueDate(rs.getDate("CID_ISSUE_DATE"));
		 
				personalInfoM.setEmailIdRef(rs.getString("EMAIL_ID_REF"));
				personalInfoM.setMobileIdRef(rs.getString("MOBILE_ID_REF"));
				personalInfoM.setCompleteData(rs.getString("COMPLETE_DATA"));
				personalInfoM.setProcedureTypeOfIncome(rs.getString("PROCEDURE_TYPE_INCOME"));
				personalInfoM.setPersonalError(rs.getString("PERSONAL_ERROR"));
				personalInfoM.setMainCardHolderName(rs.getString("MAIN_CARD_HOLDER_NAME"));
				personalInfoM.setMainCardNo(rs.getString("MAIN_CARD_NO"));
				
				personalInfoM.setNumberOfIssuer(rs.getBigDecimal("NUMBER_OF_ISSUER"));
				personalInfoM.setSelfDeclareLimit(rs.getBigDecimal("SELF_DECLARE_LIMIT"));
				
				personalInfoM.setDisplayEditBTN(MConstant.FLAG.NO);
				personalInfoM.setCisCustType(rs.getString("CIS_CUST_TYPE"));
				personalInfoM.setConsentModelFlag(rs.getString("CONSENT_MODEL_FLAG"));
				
				personalInfoM.setIdNoConsent(rs.getString("ID_NO_CONSENT"));
				
				//KPL Additional
				personalInfoM.setSpecialMerchantType(rs.getString("COMPANY_TYPE"));
				personalInfoM.setPayrollAccountNo(rs.getString("PAYROLL_ACCOUNT_NO"));
				
				personalInfoM.setApplicationIncome(rs.getBigDecimal("APPLICATION_INCOME"));
				personalInfoM.setNcbTranNo(rs.getString("NCB_TRAN_NO"));
				personalInfoM.setConsentRefNo(rs.getString("CONSENT_REF_NO"));
				
				// CR247
				personalInfoM.setPayrollDate(rs.getString("PAYROLL_DATE"));
				personalInfoM.setBirthDateConsent(rs.getDate("BIRTH_DATE_CONSENT"));
				personalInfoM.setPlaceConsent(rs.getString("PLACE_CONSENT"));
				personalInfoM.setWitnessConsent(rs.getString("WITNESS_CONSENT"));
				personalInfoM.setConsentDate(rs.getDate("CONSENT_DATE_INFO"));
				
				personalInfoM.setCisPrimDesc(rs.getString("CIS_PRIM_DESC"));
				personalInfoM.setCisPrimSubDesc(rs.getString("CIS_PRIM_SUB_DESC"));
				personalInfoM.setCisSecDesc(rs.getString("CIS_SEC_DESC"));
				personalInfoM.setCisSecSubDesc(rs.getString("CIS_SEC_SUB_DESC"));
				personalInfoM.setLineId(rs.getString("LINE_ID"));
				personalInfoM.setIncomeResource(rs.getString("INCOME_RESOURCE"));
				personalInfoM.setIncomeResourceOther(rs.getString("INCOME_RESOURCE_OTHER"));
				personalInfoM.setCountryOfIncome(rs.getString("COUNTRY_OF_INCOME"));
				personalInfoList.add(personalInfoM);
			}

			return personalInfoList;
		} catch (Exception e) {
			logger.fatal(e.getLocalizedMessage());
			throw new ApplicationException(e.getMessage());
		} finally {
			try {
				closeConnection(rs, ps);
			} catch (Exception e) {
				logger.fatal(e.getLocalizedMessage());
			}
		}
	}
	@Override
	public void saveUpdateOrigPersonalInfoM(PersonalInfoDataM personalInfoM,
			Connection conn) throws ApplicationException {
		int returnRows = 0;
		personalInfoM.setUpdateBy(userId);
		returnRows = updateTableORIG_PERSONAL_INFO(personalInfoM,conn);
		if (returnRows == 0) {
			personalInfoM.setCreateBy(userId);
			createOrigPersonalInfoM(personalInfoM);
		} else {
			OrigCardLinkCardDAO cardLinkCardDAO = ORIGDAOFactory.getCardLinkCardDAO(userId);
			OrigCardLinkCustomerDAO cardLinkCustomerDAO = ORIGDAOFactory.getCardLinkCustomerDAO(userId);
			OrigKYCDAO kycDAO = ORIGDAOFactory.getKYCDAO(userId);
			OrigNCBDocumentDAO ncbDocumentDAO = ORIGDAOFactory.getNCBDocumentDAO(userId);
			OrigNCBDocumentHistoryDAO ncbDocumentHistDAO = ORIGDAOFactory.getNCBDocumentHistoryDAO(userId);
			OrigPersonalAddressDAO personalAddressDAO = ORIGDAOFactory.getPersonalAddressDAO(userId);
			OrigPersonalRelationDAO personalRelationDAO = ORIGDAOFactory.getPersonalRelationDAO(userId);
			OrigIncomeDAO incomeDAO = ORIGDAOFactory.getIncomeDAO(userId);
			OrigIncomeSourceDAO incomeSrcDAO = ORIGDAOFactory.getIncomeSourceDAO(userId);
			OrigDebtInfoDAO debtDAO = ORIGDAOFactory.getDebtInfoDAO(userId);
			OrigNCBInfoDAO ncbDAO = ORIGDAOFactory.getNCBInfoDAO(userId);
			OrigBScoreDAO bScoreDAO = ORIGDAOFactory.getBScoreDAO(userId);
			OrigPersonalAccountDAO accountDAO = ORIGDAOFactory.getAccountDAO(userId);
			
			ArrayList<CardlinkCardDataM> cardLinkCardList = personalInfoM.getCardLinkCards();
			if(!Util.empty(cardLinkCardList)) {
				for(CardlinkCardDataM cardLinkCardM: cardLinkCardList) {
					cardLinkCardM.setPersonalId(personalInfoM.getPersonalId());
					cardLinkCardDAO.saveUpdateOrigCardlinkCardM(cardLinkCardM);
				}
			}
			cardLinkCardDAO.deleteNotInKeyCardlinkCard(cardLinkCardList, personalInfoM.getPersonalId());
			
			ArrayList<CardlinkCustomerDataM> cardLinkCustList = personalInfoM.getCardLinkCustomers();
			if(!Util.empty(cardLinkCustList)) {
				for(CardlinkCustomerDataM cardLinkCustM: cardLinkCustList) {
					cardLinkCustM.setPersonalId(personalInfoM.getPersonalId());
					cardLinkCustomerDAO.saveUpdateOrigCardlinkCustomerM(cardLinkCustM);
				}
			}
			cardLinkCustomerDAO.deleteNotInKeyCardlinkCustomer(cardLinkCustList, personalInfoM.getPersonalId());
			
			KYCDataM kycM = personalInfoM.getKyc();
			if(!Util.empty(kycM)) {
				kycM.setPersonalId(personalInfoM.getPersonalId());
				kycDAO.saveUpdateOrigKYCM(kycM);
			}
			
			ArrayList<NCBDocumentDataM> ncbDocList = personalInfoM.getNcbDocuments();
			if(!Util.empty(ncbDocList)) {
				for(NCBDocumentDataM ncbDocM: ncbDocList) {
					ncbDocM.setPersonalId(personalInfoM.getPersonalId());
					ncbDocumentDAO.saveUpdateOrigNCBDocumentM(ncbDocM);
				}
			}
			
			ArrayList<NCBDocumentHistoryDataM> ncbDocHistList = personalInfoM.getNcbDocumentHistorys();
			if(!Util.empty(ncbDocHistList)) {
				for(NCBDocumentHistoryDataM ncbDocHistM: ncbDocHistList) {
					ncbDocHistM.setPersonalId(personalInfoM.getPersonalId());
					ncbDocumentHistDAO.saveUpdateOrigNCBDocHistoryM(ncbDocHistM);
				}
			}
			
			ArrayList<AddressDataM> addressList = personalInfoM.getAddresses();
			if(!Util.empty(addressList)) {
				for(AddressDataM addressM: addressList) {
					addressM.setPersonalId(personalInfoM.getPersonalId());
					personalAddressDAO.saveUpdateOrigAddressM(addressM);
				}
			}
			
			ArrayList<IncomeInfoDataM> incomeList = personalInfoM.getIncomeInfos();
			if(!Util.empty(incomeList)) {
				for(IncomeInfoDataM incomeM : incomeList){
					incomeM.setPersonalId(personalInfoM.getPersonalId());
					incomeDAO.saveUpdateOrigIncomeInfoM(incomeM);
				}
			}
			
			ArrayList<IncomeSourceDataM> incomeSrcList = personalInfoM.getIncomeSources();
			if(!Util.empty(incomeSrcList)) {
				for(IncomeSourceDataM incomeSrcM : incomeSrcList){
					incomeSrcM.setPersonalId(personalInfoM.getPersonalId());
					incomeSrcDAO.saveUpdateOrigIncomeSourceM(incomeSrcM);
				}
			}
			incomeSrcDAO.deleteNotInKeyIncomeSource(incomeSrcList, personalInfoM.getPersonalId());
			
			ArrayList<DebtInfoDataM> debtList = personalInfoM.getDebtInfos();
			if(!Util.empty(debtList)) {
				for(DebtInfoDataM debtInfoM : debtList){
					debtInfoM.setPersonalId(personalInfoM.getPersonalId());
					debtDAO.saveUpdateOrigDebtInfoM(debtInfoM);
				}
			}
			
			ArrayList<NcbInfoDataM> ncbList = personalInfoM.getNcbInfos();
			if(!Util.empty(ncbList)) {
				for(NcbInfoDataM ncbInfoM : ncbList){
					ncbInfoM.setPersonalId(personalInfoM.getPersonalId());
					ncbDAO.saveUpdateOrigNcbInfoM(ncbInfoM);
				}
			}
			
			ArrayList<PersonalRelationDataM> personalRelationList = personalInfoM.getPersonalRelations();
			if(!Util.empty(personalRelationList)) {
				for(PersonalRelationDataM personalRelationM : personalRelationList){
					personalRelationM.setPersonalId(personalInfoM.getPersonalId());
					/*personalRelationM.setRefId(personalInfoM.getPersonalId());
					personalRelationM.setRelationLevel("P");*/
					personalRelationDAO.saveUpdateOrigPersonalRelationM(personalRelationM);
				}
			}
			
			VerificationResultDataM verificationM = personalInfoM.getVerificationResult();
			if(!Util.empty(verificationM)) {
				OrigVerificationResultDAO verificationDAO = ORIGDAOFactory.getVerificationResultDAO(userId);
				verificationM.setRefId(personalInfoM.getPersonalId());
				verificationM.setVerLevel(MConstant.REF_LEVEL.PERSONAL_INFO);
				verificationDAO.saveUpdateOrigVerificationResultM(verificationM,conn);
			}
			
			ArrayList<BScoreDataM> bScoreList = personalInfoM.getBscores();
			if(!Util.empty(bScoreList)) {
				for(BScoreDataM bScoreM: bScoreList) {
					bScoreM.setPersonalId(personalInfoM.getPersonalId());
					bScoreDAO.saveUpdateOrigBScoreM(bScoreM);
				}
			}
			
			ArrayList<AccountDataM> accountList = personalInfoM.getAccounts();
			if(!Util.empty(accountList)) {
				for(AccountDataM accountM: accountList) {
					accountM.setPersonalId(personalInfoM.getPersonalId());
					accountDAO.saveUpdateOrigPersonalAccountM(accountM);
				}
			}
			
			ncbDocumentDAO.deleteNotInKeyNCBDocument(ncbDocList, personalInfoM.getPersonalId());
			personalAddressDAO.deleteNotInKeyAddress(addressList, personalInfoM.getPersonalId());
			incomeDAO.deleteNotInKeyIncomeInfo(incomeList, personalInfoM.getPersonalId());
			debtDAO.deleteNotInKeyDebtInfo(debtList, personalInfoM.getPersonalId());
			ncbDAO.deleteNotInKeyNcbInfo(ncbList, personalInfoM.getPersonalId());
			personalRelationDAO.deleteNotInKeyPersonalRelation(personalRelationList, personalInfoM.getPersonalId());
			bScoreDAO.deleteNotInKeyBScore(bScoreList, personalInfoM.getPersonalId());
			accountDAO.deleteNotInKeyAccount(accountList, personalInfoM.getPersonalId());
		}
	}
	@Override
	public void saveUpdateOrigPersonalInfoM(PersonalInfoDataM personalInfoM)throws ApplicationException {
		Connection conn = null;
		try{
			conn = getConnection();
			saveUpdateOrigPersonalInfoM(personalInfoM, conn);
		}catch(Exception e){
			logger.fatal(e);
			throw new ApplicationException(e.getMessage());
		}finally{
			try{
				closeConnection(conn);
			}catch(Exception e){
				logger.fatal(e.getLocalizedMessage());
			}
		}
	}

	private int updateTableORIG_PERSONAL_INFO(PersonalInfoDataM personalInfoM,Connection 
			conn) throws ApplicationException {
		int returnRows = 0;
		PreparedStatement ps = null;
//		Connection conn = null;
		try {
			//conn = Get Connection
//			conn = getConnection();
			StringBuffer sql = new StringBuffer("");
			sql.append("UPDATE ORIG_PERSONAL_INFO ");
			sql.append(" SET IDNO = ?, PERSONAL_TYPE = ?, CUSTOMER_TYPE = ?, MAILING_ADDRESS = ?, ");
			sql.append(" TH_TITLE_CODE = ?, TH_FIRST_NAME = ?, TH_LAST_NAME = ?, TH_MID_NAME = ?, ");
			sql.append(" EN_TITLE_CODE = ?, EN_FIRST_NAME = ?, EN_LAST_NAME =?, EN_MID_NAME =?, GENDER = ?, ");
			sql.append(" BIRTH_DATE = ?, RACE = ?, NATIONALITY = ?, MARRIED = ?, CID_TYPE = ?, CID_EXP_DATE = ?, ");
			sql.append(" OCCUPATION = ?, BUSINESS_TYPE = ?, BUSINESS_TYPE_OTHER = ?, BUSINESS_NATURE = ?, ");
			
			sql.append(" POSITION_CODE = ?, POSITION_DESC = ?, POSITION_LEVEL = ?, SALARY = ?, ");
			sql.append(" EMPLOYMENT_TYPE = ?, TOT_WORK_YEAR = ?, TOT_WORK_MONTH = ?, OTHER_INCOME = ?, ");
			sql.append(" M_TH_FIRST_NAME = ?, M_TH_LAST_NAME = ?, M_TH_OLD_LAST_NAME = ?, M_TITLE_CODE = ?, ");
			sql.append(" M_COMPANY = ?, M_POSITION = ?, M_INCOME = ?, M_HOME_TEL = ?, M_OFFICE_TEL = ?, ");
			sql.append(" M_PHONE_NO = ?, DEGREE = ?, CIS_NO = ?, EMAIL_PRIMARY = ?, EMAIL_SECONDARY = ?, ");
			sql.append(" MOBILE_NO = ?, CONTACT_TIME = ?, PROFESSION = ?, ");
			
			sql.append(" PROFESSION_OTHER = ?, PREV_COMPANY = ?, PREV_COMPANY_PHONE_NO = ?, ");
			sql.append(" PREV_COMPANY_TITLE =?, PREV_WORK_YEAR = ?, PREV_WORK_MONTH = ?, CIRCULATION_INCOME = ?, ");
			sql.append(" NET_PROFIT_INCOME = ?, FREELANCE_INCOME = ?, SAVING_INCOME = ?, SORCE_OF_INCOME_OTHER = ?, ");
			sql.append(" RECEIVE_INCOME_BANK = ?, RECEIVE_INCOME_METHOD = ?, MONTHLY_INCOME = ?, MONTHLY_EXPENSE = ?, ");
			sql.append(" ASSETS_AMOUNT = ?, HR_BONUS = ?, HR_INCOME = ?, BRANCH_RECEIVE_CARD = ?, PLACE_RECEIVE_CARD = ?, ");
			
			sql.append(" VAT_REGIST_FLAG = ?, VISA_TYPE = ?, VISA_EXP_DATE = ?, WORK_PERMIT_EXP_DATE = ?,  WORK_PERMIT_NO = ?,");
			sql.append(" DISCLOSURE_FLAG = ?, ESTABLISHMENT_ADDR_FLAG = ?, K_GROUP_FLAG = ?, ");			
			sql.append(" MATCH_ADDR_CARDLINK_FLAG = ?, MATCH_ADDR_NCB_FLAG = ?, UPDATE_DATE = ?, UPDATE_BY = ?, ");			
			sql.append(" DEBT_BURDEN=?, STAFF_FLAG=?, GROSS_INCOME=?, SRC_OTH_INC_BONUS=?, SRC_OTH_INC_COMMISSION=?, ");
			sql.append(" SRC_OTH_INC_OTHER=?, RELATIONSHIP_TYPE=?, RELATIONSHIP_TYPE_DESC=?, ");
			sql.append(" RECEIVE_INCOME_BANK_BRANCH=?, INC_POLICY_SEGMENT_CODE=?, TOT_VERIFIED_INCOME=?, ");
			sql.append(" TYPE_OF_FIN = ?, REFER_CRITERIA = ?, CIS_PRIM_SEGMENT = ?, ");
			sql.append(" CIS_PRIM_SUB_SEGMENT = ?, CIS_SEC_SEGMENT = ?, CIS_SEC_SUB_SEGMENT = ?,SEQ = ?,");
			sql.append(" BUS_REGIST_ID = ?, BUS_REGIST_DATE = ?, BUS_REGIST_REQ_NO = ?,NO_MAIN_CARD = ?,");			
			sql.append(" NO_SUP_CARD = ?, PERMANENT_STAFF = ?, PHONE_NO_BOL = ? ,FREELANCE_TYPE = ?,SORCE_OF_INCOME = ? ,");
			sql.append(" NO_OF_CHILD = ?, OCCUPATION_OTHER= ?, COMPANY_CIS_ID = ?, ");
			sql.append(" COMPANY_ORG_ID_BOL = ?, TH_TITLE_DESC = ?, EN_TITLE_DESC = ?, M_TITLE_DESC = ?, ");
			sql.append(" BUREAU_REQUIRED_FLAG = ?,PEGA_PHONE_TYPE=?,PEGA_PHONE_NO=?,PEGA_PHONE_EXT=?,CID_ISSUE_DATE=?, ");
			sql.append(" EMAIL_ID_REF=? ,MOBILE_ID_REF=? ,COMPLETE_DATA=?,PROCEDURE_TYPE_INCOME=?,BRANCH_RECEIVE_CARD_NAME=?, ");
			sql.append(" PERSONAL_ERROR=? ,MAIN_CARD_HOLDER_NAME=? ,MAIN_CARD_NO=? ,NUMBER_OF_ISSUER=? ,SELF_DECLARE_LIMIT=? ,CIS_CUST_TYPE = ? ,CONSENT_MODEL_FLAG = ?, ID_NO_CONSENT = ? ");
			sql.append(" , COMPANY_TYPE = ? , PAYROLL_ACCOUNT_NO = ? ,APPLICATION_INCOME =?, "); //KPL Additional
			sql.append(" NCB_TRAN_NO = ?, CONSENT_REF_NO = ?, PAYROLL_DATE = ? , BIRTH_DATE_CONSENT = ?, PLACE_CONSENT = ?, WITNESS_CONSENT = ? , CONSENT_DATE_INFO = ?, ");
			sql.append(" CIS_PRIM_DESC = ?, CIS_PRIM_SUB_DESC = ?, CIS_SEC_DESC = ?, CIS_SEC_SUB_DESC = ?, LINE_ID = ?, ");
			sql.append(" INCOME_RESOURCE = ?, INCOME_RESOURCE_OTHER = ? , COUNTRY_OF_INCOME = ? ");
			sql.append(" WHERE PERSONAL_ID = ? AND APPLICATION_GROUP_ID = ? ");
			String dSql = String.valueOf(sql);
			logger.debug("Sql=" + dSql);
			logger.debug("personalInfoM.getPersonalId()=" + personalInfoM.getPersonalId());
			logger.debug("personalInfoM.getApplicationGroupId()=" + personalInfoM.getApplicationGroupId());
			ps = conn.prepareStatement(dSql);
			int cnt = 1;
			ps.setString(cnt++, personalInfoM.getIdno());
			ps.setString(cnt++, personalInfoM.getPersonalType());
			ps.setString(cnt++, personalInfoM.getCustomerType());			
			ps.setString(cnt++, personalInfoM.getMailingAddress());
			
			ps.setString(cnt++, personalInfoM.getThTitleCode());
			ps.setString(cnt++, personalInfoM.getThFirstName());
			ps.setString(cnt++, personalInfoM.getThLastName());
			ps.setString(cnt++, personalInfoM.getThMidName());
			
			ps.setString(cnt++, personalInfoM.getEnTitleCode());
			ps.setString(cnt++, personalInfoM.getEnFirstName());
			ps.setString(cnt++, personalInfoM.getEnLastName());
			ps.setString(cnt++, personalInfoM.getEnMidName());
			ps.setString(cnt++, personalInfoM.getGender());
			
			ps.setDate(cnt++, personalInfoM.getBirthDate());
			ps.setString(cnt++, personalInfoM.getRace());
			ps.setString(cnt++, personalInfoM.getNationality());
			ps.setString(cnt++, personalInfoM.getMarried());
			ps.setString(cnt++, personalInfoM.getCidType());
			ps.setDate(cnt++, personalInfoM.getCidExpDate());

			ps.setString(cnt++, personalInfoM.getOccupation());
			ps.setString(cnt++, personalInfoM.getBusinessType());
			ps.setString(cnt++, personalInfoM.getBusinessTypeOther());
			ps.setString(cnt++, personalInfoM.getBusinessNature());
			///
			
			ps.setString(cnt++, personalInfoM.getPositionCode());
			ps.setString(cnt++, personalInfoM.getPositionDesc());
			ps.setString(cnt++, personalInfoM.getPositionLevel());
			ps.setBigDecimal(cnt++, personalInfoM.getSalary());
			
			ps.setString(cnt++, personalInfoM.getEmploymentType());
			ps.setBigDecimal(cnt++, personalInfoM.getTotWorkYear());
			ps.setBigDecimal(cnt++, personalInfoM.getTotWorkMonth());
			ps.setBigDecimal(cnt++, personalInfoM.getOtherIncome());
			
			ps.setString(cnt++, personalInfoM.getmThFirstName());
			ps.setString(cnt++, personalInfoM.getmThLastName());			
			ps.setString(cnt++, personalInfoM.getmThOldLastName());
			ps.setString(cnt++, personalInfoM.getmTitleCode());
			
			ps.setString(cnt++, personalInfoM.getmCompany());
			ps.setString(cnt++, personalInfoM.getmPosition());
			ps.setBigDecimal(cnt++, personalInfoM.getmIncome());			
			ps.setString(cnt++, personalInfoM.getmHomeTel());
			ps.setString(cnt++, personalInfoM.getmOfficeTel());
			
			ps.setString(cnt++, personalInfoM.getmPhoneNo());
			ps.setString(cnt++, personalInfoM.getDegree());
			ps.setString(cnt++, personalInfoM.getCisNo());
			ps.setString(cnt++, personalInfoM.getEmailPrimary());			
			ps.setString(cnt++, personalInfoM.getEmailSecondary());
			
			ps.setString(cnt++, personalInfoM.getMobileNo());
			ps.setString(cnt++, personalInfoM.getContactTime());
			ps.setString(cnt++, personalInfoM.getProfession());
			
			///

			ps.setString(cnt++, personalInfoM.getProfessionOther());
			ps.setString(cnt++, personalInfoM.getPrevCompany());
			ps.setString(cnt++, personalInfoM.getPrevCompanyPhoneNo());
			
			ps.setString(cnt++, personalInfoM.getPrevCompanyTitle());
			ps.setBigDecimal(cnt++, personalInfoM.getPrevWorkYear());
			ps.setBigDecimal(cnt++, personalInfoM.getPrevWorkMonth());
			ps.setBigDecimal(cnt++, personalInfoM.getCirculationIncome());
			
			ps.setBigDecimal(cnt++, personalInfoM.getNetProfitIncome());			
			ps.setBigDecimal(cnt++, personalInfoM.getFreelanceIncome());
			ps.setBigDecimal(cnt++, personalInfoM.getSavingIncome());
			ps.setString(cnt++, personalInfoM.getSorceOfIncomeOther());
			
			ps.setString(cnt++, personalInfoM.getReceiveIncomeBank());			
			ps.setString(cnt++, personalInfoM.getReceiveIncomeMethod());
			ps.setBigDecimal(cnt++, personalInfoM.getMonthlyIncome());
			ps.setBigDecimal(cnt++, personalInfoM.getMonthlyExpense());
			
			ps.setBigDecimal(cnt++, personalInfoM.getAssetsAmount());			
			ps.setBigDecimal(cnt++, personalInfoM.getHrBonus());
			ps.setBigDecimal(cnt++, personalInfoM.getHrIncome());
			ps.setString(cnt++, personalInfoM.getBranchReceiveCard());
			ps.setString(cnt++, personalInfoM.getPlaceReceiveCard());
			
			///
			
			ps.setString(cnt++, personalInfoM.getVatRegistFlag());
			ps.setString(cnt++, personalInfoM.getVisaType());
			ps.setDate(cnt++, personalInfoM.getVisaExpDate());
			ps.setDate(cnt++, personalInfoM.getWorkPermitExpDate());
			ps.setString(cnt++, personalInfoM.getWorkPermitNo());
			
			ps.setString(cnt++, personalInfoM.getDisclosureFlag());			
			ps.setString(cnt++, personalInfoM.getEstablishmentAddrFlag());
			ps.setString(cnt++, personalInfoM.getkGroupFlag());
			
			ps.setString(cnt++, personalInfoM.getMatchAddrCardlinkFlag());
			ps.setString(cnt++, personalInfoM.getMatchAddrNcbFlag());			
			ps.setTimestamp(cnt++, ApplicationDate.getTimestamp());
			ps.setString(cnt++, personalInfoM.getUpdateBy());
			
			ps.setBigDecimal(cnt++, personalInfoM.getDebtBurden());
			ps.setString(cnt++, personalInfoM.getStaffFlag());
			ps.setBigDecimal(cnt++, personalInfoM.getGrossIncome());
			ps.setString(cnt++, personalInfoM.getSrcOthIncBonus());
			ps.setString(cnt++, personalInfoM.getSrcOthIncCommission());
			
			ps.setString(cnt++, personalInfoM.getSrcOthIncOther());
			ps.setString(cnt++, personalInfoM.getRelationshipType());
			ps.setString(cnt++, personalInfoM.getRelationshipTypeDesc());
			
			ps.setString(cnt++, personalInfoM.getReceiveIncomeBankBranch());
			ps.setString(cnt++, personalInfoM.getIncPolicySegmentCode());
			ps.setBigDecimal(cnt++, personalInfoM.getTotVerifiedIncome());
			
			ps.setString(cnt++, personalInfoM.getTypeOfFin());
			ps.setString(cnt++, personalInfoM.getReferCriteria());
			ps.setString(cnt++, personalInfoM.getCisPrimSegment());
			
			ps.setString(cnt++, personalInfoM.getCisPrimSubSegment());
			ps.setString(cnt++, personalInfoM.getCisSecSegment());
			ps.setString(cnt++, personalInfoM.getCisSecSubSegment());
			ps.setInt(cnt++, personalInfoM.getSeq());
			
			ps.setString(cnt++, personalInfoM.getBusRegistId());
			ps.setDate(cnt++, personalInfoM.getBusRegistDate());
			ps.setString(cnt++, personalInfoM.getBusRegistReqNo());
			ps.setInt(cnt++, FormatUtil.getInt(personalInfoM.getNoMainCard()));
			
			ps.setInt(cnt++,FormatUtil.getInt(personalInfoM.getNoSupCard()));
			ps.setString(cnt++, personalInfoM.getPermanentStaff());
			ps.setString(cnt++, personalInfoM.getPhoneNoBol());
			ps.setString(cnt++, personalInfoM.getFreelanceType());
			ps.setString(cnt++, personalInfoM.getSorceOfIncome());
			
			ps.setBigDecimal(cnt++, personalInfoM.getNoOfChild());
			ps.setString(cnt++, personalInfoM.getOccpationOther());
			ps.setString(cnt++, personalInfoM.getCompanyCISId());
			
			ps.setString(cnt++, personalInfoM.getCompanyOrgIdBol());
			ps.setString(cnt++, personalInfoM.getThTitleDesc());
			ps.setString(cnt++, personalInfoM.getEnTitleDesc());
			ps.setString(cnt++, personalInfoM.getmTitleDesc());
			
			ps.setString(cnt++, personalInfoM.getBureauRequiredFlag());			
			ps.setString(cnt++, personalInfoM.getPegaPhoneType());
			ps.setString(cnt++, personalInfoM.getPegaPhoneNo());
			ps.setString(cnt++, personalInfoM.getPegaPhoneExt());
			ps.setDate(cnt++, personalInfoM.getCidIssueDate());
			
			ps.setString(cnt++, personalInfoM.getEmailIdRef());
			ps.setString(cnt++, personalInfoM.getMobileIdRef());
			ps.setString(cnt++, personalInfoM.getCompleteData());
			ps.setString(cnt++, personalInfoM.getProcedureTypeOfIncome());
			ps.setString(cnt++, personalInfoM.getBranchReceiveCardName());
			
			ps.setString(cnt++, personalInfoM.getPersonalError());
			ps.setString(cnt++, personalInfoM.getMainCardHolderName());
			ps.setString(cnt++, personalInfoM.getMainCardNo());
			ps.setBigDecimal(cnt++, personalInfoM.getNumberOfIssuer());
			ps.setBigDecimal(cnt++, personalInfoM.getSelfDeclareLimit());
			ps.setString(cnt++, personalInfoM.getCisCustType());
		 	ps.setString(cnt++, personalInfoM.getConsentModelFlag());		 	
		 	ps.setString(cnt++, personalInfoM.getIdNoConsent());
		 	
		 	//KPL Additional
		 	ps.setString(cnt++, personalInfoM.getSpecialMerchantType());
		 	ps.setString(cnt++, personalInfoM.getPayrollAccountNo());
		 	
		 	ps.setBigDecimal(cnt++, personalInfoM.getApplicationIncome());
		 	ps.setString(cnt++, personalInfoM.getNcbTranNo());
		 	ps.setString(cnt++, personalInfoM.getConsentRefNo());
		 // CR247
		 	ps.setString(cnt++, personalInfoM.getPayrollDate());
		 	ps.setDate(cnt++, personalInfoM.getBirthDateConsent());
		 	ps.setString(cnt++, personalInfoM.getPlaceConsent());
		 	ps.setString(cnt++, personalInfoM.getWitnessConsent());
		 	ps.setDate(cnt++, personalInfoM.getConsentDate());
		 	
			ps.setString(cnt++, personalInfoM.getCisPrimDesc());
			ps.setString(cnt++, personalInfoM.getCisPrimSubDesc());
			ps.setString(cnt++, personalInfoM.getCisSecDesc());
			ps.setString(cnt++, personalInfoM.getCisSecSubDesc());
			ps.setString(cnt++, personalInfoM.getLineId());
			ps.setString(cnt++, personalInfoM.getIncomeResource());
			ps.setString(cnt++, personalInfoM.getIncomeResourceOther());
			ps.setString(cnt++, personalInfoM.getCountryOfIncome());
		 	
			// WHERE CLAUSE
			ps.setString(cnt++, personalInfoM.getPersonalId());
			ps.setString(cnt++, personalInfoM.getApplicationGroupId());
			
			returnRows = ps.executeUpdate();
		} catch (Exception e) {
			logger.fatal(e.getLocalizedMessage());
			throw new ApplicationException(e.getMessage());
		} finally {
			try {
//				closeConnection(conn, ps);
				closeConnection(ps);
			} catch (Exception e) {
				logger.fatal(e.getLocalizedMessage());
			}
		}
		return returnRows;
	}

	@Override
	public void deleteNotInKeyPersonalInfo(
			ArrayList<PersonalInfoDataM> personalInfoList,
			String applicationGroupId) throws ApplicationException {
		ArrayList<String> notInKeyIdList = selectNotInKeyTableORIG_PERSONAL_INFO(personalInfoList, applicationGroupId);
		if(!Util.empty(notInKeyIdList)) {
			OrigCardLinkCardDAO cardLinkCardDAO = ORIGDAOFactory.getCardLinkCardDAO();
			OrigCardLinkCustomerDAO cardLinkCustomerDAO = ORIGDAOFactory.getCardLinkCustomerDAO();
			OrigKYCDAO kycDAO = ORIGDAOFactory.getKYCDAO();
			OrigNCBDocumentDAO ncbDocumentDAO = ORIGDAOFactory.getNCBDocumentDAO();
			OrigNCBDocumentHistoryDAO ncbDocumentHistDAO = ORIGDAOFactory.getNCBDocumentHistoryDAO();
			OrigPersonalAddressDAO personalAddressDAO = ORIGDAOFactory.getPersonalAddressDAO();
			OrigDocumentCheckListDAO docChkListDAO = ORIGDAOFactory.getDocumentCheckListDAO();
			OrigPersonalRelationDAO personalRelationDAO = ORIGDAOFactory.getPersonalRelationDAO();
			OrigVerificationResultDAO verificationDAO = ORIGDAOFactory.getVerificationResultDAO();
			OrigIncomeDAO incomeDAO = ORIGDAOFactory.getIncomeDAO();
			OrigIncomeSourceDAO incomeSrcDAO = ORIGDAOFactory.getIncomeSourceDAO();
			OrigDebtInfoDAO debtDAO = ORIGDAOFactory.getDebtInfoDAO();
			OrigNCBInfoDAO ncbDAO = ORIGDAOFactory.getNCBInfoDAO();
			OrigBScoreDAO bscoreDAO = ORIGDAOFactory.getBScoreDAO();
			OrigPersonalAccountDAO accountDAO = ORIGDAOFactory.getAccountDAO();
			
			for(String personalId: notInKeyIdList) {
				cardLinkCardDAO.deleteOrigCardlinkCardM(personalId, null);
				cardLinkCustomerDAO.deleteOrigCardlinkCustomerM(personalId, null);
				kycDAO.deleteOrigKYCM(personalId);
				ncbDocumentDAO.deleteOrigNCBDocumentM(personalId, null);
				ncbDocumentHistDAO.deleteOrigNCBDocHistoryM(personalId, null);
				personalAddressDAO.deleteOrigAddressM(personalId, null);
				docChkListDAO.deleteOrigDocumentCheckListM(applicationGroupId,personalId, null);
				personalRelationDAO.deleteOrigPersonalRelationM(personalId, null, null);
				incomeDAO.deleteOrigIncomeInfoM(personalId, null);
				incomeSrcDAO.deleteOrigIncomeSourceM(personalId, null);
				debtDAO.deleteOrigDebtInfoM(personalId, null);
				ncbDAO.deleteOrigNcbInfoM(personalId, null);
				verificationDAO.deleteOrigVerificationResultM(personalId, MConstant.REF_LEVEL.PERSONAL_INFO, null);
				bscoreDAO.deleteOrigBScoreM(personalId, null);
				accountDAO.deleteOrigPersonalAccountM(personalId, null);
			}
		}
		deleteNotInKeyTableORIG_PERSONAL_INFO(personalInfoList, applicationGroupId);
	}

	private ArrayList<String> selectNotInKeyTableORIG_PERSONAL_INFO(
			ArrayList<PersonalInfoDataM> personalInfoList,
			String applicationGroupId) throws ApplicationException {
		PreparedStatement ps = null;
		Connection conn = null;
		ResultSet rs = null;
		ArrayList<String> idList = new ArrayList<String>();
		try {
            // conn = Get Connection
            conn = getConnection();

            StringBuffer sql = new StringBuffer("");
            sql.append("SELECT PERSONAL_ID FROM ORIG_PERSONAL_INFO ");
            sql.append(" WHERE APPLICATION_GROUP_ID = ? ");
            
            if (personalInfoList != null && !personalInfoList.isEmpty()) {
                sql.append(" AND PERSONAL_ID NOT IN ( ");

                for (PersonalInfoDataM personalM: personalInfoList) {
                    sql.append(" '" + personalM.getPersonalId()+ "' , ");
                }

                if (sql.toString().trim().endsWith(",")) {
                    sql.delete(sql.toString().lastIndexOf(","), sql.toString().length());
                }

                sql.append(" ) ");
            }

            String dSql = String.valueOf(sql);
            ps = conn.prepareStatement(dSql);
            ps.setString(1, applicationGroupId);
            
            rs = ps.executeQuery();
            while(rs.next()) {
            	String personalId =  rs.getString("PERSONAL_ID");
            	idList.add(personalId);
            }

            return idList;
        } catch (Exception e) {
        	logger.error("ERROR >> ",e);
			throw new ApplicationException(e.getMessage());
		} finally {
			try {
				closeConnection(conn, rs, ps);
			} catch (Exception e) {
				logger.error("ERROR >> ",e);
				throw new ApplicationException(e.getMessage());
			}
		}
	}

	private void deleteNotInKeyTableORIG_PERSONAL_INFO(
			ArrayList<PersonalInfoDataM> personalInfoList,
			String applicationGroupId) throws ApplicationException {
		PreparedStatement ps = null;
		Connection conn = null;
		try {
            // conn = Get Connection
            conn = getConnection();

            StringBuffer sql = new StringBuffer("");
            sql.append("DELETE FROM ORIG_PERSONAL_INFO ");
            sql.append(" WHERE APPLICATION_GROUP_ID = ? ");
            
            if (!Util.empty(personalInfoList)) {
                sql.append(" AND PERSONAL_ID NOT IN ( ");
                for (PersonalInfoDataM personalInfoM: personalInfoList) {
                	logger.debug("personalInfoM.getPersonalId() = "+personalInfoM.getPersonalId());
                    sql.append(" '" + personalInfoM.getPersonalId() + "', ");
                }
                
                if (sql.toString().trim().endsWith(",")) {
                    sql.delete(sql.toString().lastIndexOf(","), sql.toString().length());
                }

                sql.append(" ) ");
            }

            String dSql = String.valueOf(sql);
            ps = conn.prepareStatement(dSql);
            ps.setString(1, applicationGroupId);
            
            ps.executeUpdate();
            ps.close();

        } catch (Exception e) {
        	logger.error("ERROR >> ",e);
			throw new ApplicationException(e.getMessage());
		} finally {
			try {
				closeConnection(conn, ps);
			} catch (Exception e) {
				logger.error("ERROR >> ",e);
				throw new ApplicationException(e.getMessage());
			}
		}
	}
	@Override
	public ArrayList<PersonalInfoDataM> loadOrigPersonalInfoForCis(String applicationGroupId) throws ApplicationException {
		ArrayList<PersonalInfoDataM> personalInfos = selectTableORIG_PERSONAL_INFO(applicationGroupId);
		if(!Util.empty(personalInfos)) {
			for(PersonalInfoDataM personalInfo: personalInfos) {
				OrigPersonalAddressDAO personalAddressDAO = ORIGDAOFactory.getPersonalAddressDAO();
				ArrayList<AddressDataM> addressLists = personalAddressDAO.loadOrigAddressM(personalInfo.getPersonalId());
				if(addressLists != null && !addressLists.isEmpty()) {
					personalInfo.setAddresses(addressLists);
				}
			}
		}
		return personalInfos;
	}
	@Override
	public void saveUpdateOrigPersonalInfoForCis(PersonalInfoDataM personalInfo)throws ApplicationException {
		Connection conn = null;
		try{
			conn = getConnection();
			personalInfo.setUpdateBy(userId);
			updateTableORIG_PERSONAL_INFO(personalInfo,conn);
			OrigPersonalAddressDAO personalAddressDAO = ORIGDAOFactory.getPersonalAddressDAO(userId);
			ArrayList<AddressDataM> addressList = personalInfo.getAddresses();
			if(null != addressList){
				for(AddressDataM addressM: addressList) {
					addressM.setPersonalId(personalInfo.getPersonalId());
					personalAddressDAO.saveUpdateOrigAddressM(addressM);
				}
			}
		}catch(Exception e){
			logger.fatal(e);
			throw new ApplicationException(e.getMessage());
		}finally{
			try{
				closeConnection(conn);
			}catch(Exception e){
				logger.fatal(e.getLocalizedMessage());
			}
		}
	}
	@Override
	public void saveUpdateCis(PersonalInfoDataM personalInfo,HashMap<String,CompareDataM> comparisonFields,String applicationGroupId,String srcOfData,int lifeCycle)throws ApplicationException{
		Connection conn = null;
		try{
			conn = getConnection();
			personalInfo.setUpdateBy(userId);
			updateCis(personalInfo,conn);
			OrigComparisionDataDAO compareDAO = ModuleFactory.getOrigComparisionDataDAO(userId);
				compareDAO.deleteOrigComparisionDataMatchSrc(applicationGroupId,srcOfData,lifeCycle,conn);
				compareDAO.saveUpdateOrigComparisionData(comparisonFields,applicationGroupId,lifeCycle,conn);
		}catch(Exception e){
			logger.fatal("ERROR",e);
			throw new ApplicationException(e.getMessage());
		}finally{
			try{
				closeConnection(conn);
			}catch(Exception e){
				logger.fatal("ERROR",e);
				throw new ApplicationException(e.getMessage());
			}
		}
	}
	private int updateCis(PersonalInfoDataM personalInfo,Connection conn)throws ApplicationException{
		PreparedStatement ps = null;
		int returnRows = 0;
		try{
			StringBuilder sql = new StringBuilder("");
			sql.append("UPDATE ORIG_PERSONAL_INFO SET CIS_NO = ?, PERSONAL_ERROR = ?, UPDATE_BY = ?, UPDATE_DATE = ? WHERE PERSONAL_ID = ?");
			String dSql = String.valueOf(sql);
			logger.debug("Sql : " + dSql);
			ps = conn.prepareStatement(dSql);
				int index = 1;
				ps.setString(index++,personalInfo.getCisNo());
				ps.setString(index++,personalInfo.getPersonalError());
				ps.setString(index++,personalInfo.getUpdateBy());
				ps.setTimestamp(index++,personalInfo.getUpdateDate());
				ps.setString(index++, personalInfo.getPersonalId());
			returnRows = ps.executeUpdate();			
		}catch(Exception e){
			logger.fatal("ERROR",e);
			throw new ApplicationException(e.getMessage());
		}finally{
			try{
				closeConnection(ps);
			}catch(Exception e){
				logger.fatal("ERROR",e);
				throw new ApplicationException(e.getMessage());
			}
		}
		return returnRows;
	}
	@Override
	public void saveUpdateCisFailed(PersonalInfoDataM personalInfo,String applicationGroupId,String srcOfData,int lifeCycle)throws ApplicationException{
		Connection conn = null;
		try{
			conn = getConnection();
			personalInfo.setUpdateBy(userId);
			updatePersonalError(personalInfo,conn);
			OrigComparisionDataDAO compareDAO = ModuleFactory.getOrigComparisionDataDAO(userId);
			compareDAO.deleteOrigComparisionDataMatchSrc(applicationGroupId,srcOfData,lifeCycle,conn);
		}catch(Exception e){
			logger.fatal("ERROR",e);
			throw new ApplicationException(e.getMessage());
		}finally{
			try{
				closeConnection(conn);
			}catch(Exception e){
				logger.fatal("ERROR",e);
				throw new ApplicationException(e.getMessage());
			}
		}
	}
	private int updatePersonalError(PersonalInfoDataM personalInfo,Connection conn)throws ApplicationException{
		PreparedStatement ps = null;
		int returnRows = 0;
		try{
			StringBuilder sql = new StringBuilder("");
			sql.append("UPDATE ORIG_PERSONAL_INFO SET CIS_NO = ?, PERSONAL_ERROR = ?, UPDATE_BY = ?, UPDATE_DATE = ? WHERE PERSONAL_ID = ?");
			String dSql = String.valueOf(sql);
			logger.debug("Sql : " + dSql);
			ps = conn.prepareStatement(dSql);
				int index = 1;
				ps.setString(index++,personalInfo.getCisNo());
				ps.setString(index++,personalInfo.getPersonalError());
				ps.setString(index++,personalInfo.getUpdateBy());
				ps.setTimestamp(index++,personalInfo.getUpdateDate());
				ps.setString(index++, personalInfo.getPersonalId());
			returnRows = ps.executeUpdate();			
		}catch(Exception e){
			logger.fatal("ERROR",e);
			throw new ApplicationException(e.getMessage());
		}finally{
			try{
				closeConnection(ps);
			}catch(Exception e){
				logger.fatal("ERROR",e);
				throw new ApplicationException(e.getMessage());
			}
		}
		return returnRows;
	}
}
