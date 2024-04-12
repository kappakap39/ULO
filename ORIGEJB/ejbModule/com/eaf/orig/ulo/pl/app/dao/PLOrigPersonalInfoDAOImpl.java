package com.eaf.orig.ulo.pl.app.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Vector;
import org.apache.log4j.Logger;

import com.eaf.ncb.model.output.NCBOutputDataM;
import com.eaf.ncb.service.ejb.NCBServiceManager;
import com.eaf.orig.shared.constant.EJBConstant;
import com.eaf.orig.shared.constant.OrigConstant;
import com.eaf.orig.shared.dao.OrigObjectDAO;
import com.eaf.orig.shared.dao.PLORIGDAOFactory;
import com.eaf.orig.shared.service.ORIGEJBService;
import com.eaf.orig.shared.util.OrigUtil;
import com.eaf.orig.ulo.pl.app.dao.exception.PLOrigApplicationException;
import com.eaf.orig.ulo.pl.model.app.PLApplicationDataM;
import com.eaf.orig.ulo.pl.model.app.PLNCBDocDataM;
import com.eaf.orig.ulo.pl.model.app.PLPersonalInfoDataM;
import com.eaf.orig.ulo.pl.service.PLORIGEJBService;
import com.eaf.xrules.ulo.pl.dao.util.XRulesRemoteDAOUtilManager;
import com.eaf.xrules.ulo.pl.model.app.rule.PLXRulesVerificationResultDataM;

public class PLOrigPersonalInfoDAOImpl extends OrigObjectDAO implements PLOrigPersonalInfoDAO {
	
	private static Logger log = Logger.getLogger(PLOrigPersonalInfoDAOImpl.class);

	@Override
	public void saveUpdateOrigPersonalInfo(Vector<PLPersonalInfoDataM> personalVect, String appRecId, PLApplicationDataM appM) throws PLOrigApplicationException {
		try{			
			if(!OrigUtil.isEmptyVector(personalVect)){				
				Vector<String> personalIDVect = new Vector<String>();				
				for(PLPersonalInfoDataM personalM : personalVect){
					
					if(OrigUtil.isEmptyString(personalM.getPersonalID())){
						String personalID = PLORIGEJBService.getGeneratorManager().generateUniqueIDByName(EJBConstant.PERSONAL_ID);
						personalM.setPersonalID(personalID);
					}
					
					int returnRows = this.updateTableOrig_Personal_Info(personalM, appRecId);
					if(returnRows == 0){	
						this.insertTableOrig_Personal_Info(personalM, appRecId);
					}
					
					this.updateSaveSubTable(personalM, personalM.getPersonalID(), appM);

					personalIDVect.add(personalM.getPersonalID());
					
				}
				if(!OrigUtil.isEmptyVector(personalIDVect)){
					this.deleteTableORIG_PERSONAL_INFO(personalIDVect, appRecId);	
				}
			}
						
		}catch(Exception e){
			log.fatal("Exception >> ",e);
			throw new PLOrigApplicationException(e.getMessage());
		}
	}
	
	@Override
	public int updateTableOrig_Personal_Info(PLPersonalInfoDataM personalM, String appRecId) throws PLOrigApplicationException{
		Connection conn = null;
		int returnRows = 0;
		PreparedStatement ps = null;
		try {

			conn = getConnection();
			StringBuilder sql = new StringBuilder("");

			sql.append("UPDATE ORIG_PERSONAL_INFO ");
			sql.append(" SET IDNO=?, PERSONAL_TYPE=?, CUSTOMER_TYPE=?, MAILING_ADDRESS=?, PREVIOUS_RECORD=? ");
			sql.append(", TIME_IN_CURRENT_ADDRESS=?, K_CONSENT_FLAG=?, K_CONSENT_DATE=?, CONSENT_FLAG=?, CONSENT_DATE=? ");
			sql.append(", COBORROWER_FLAG=?, DEBT_INCLUDE_FLAG=?, CUSTOMER_NO=?, TH_TITLE_CODE=?, TH_FIRST_NAME=? ");
			sql.append(", TH_LAST_NAME=?, TH_MID_NAME=?, EN_TITLE_CODE=?, EN_FIRST_NAME=?, EN_LAST_NAME=? ");
			sql.append(", EN_MID_NAME=?, GENDER=?, BIRTH_DATE=?, RACE=?, NATIONALITY=? ");
			sql.append(", MARRIED=?, TAX_ID=?, CID_TYPE=?, CID_ISSUE_DATE=?, CID_EXP_DATE=? ");
			sql.append(", CID_ISSUE_BY=?, OCCUPATION=?, COMPANY=?, BUSINESS_TYPE=?, POSITION_CODE=? ");
			sql.append(", POSITION_DESC=?, DEPARTMENT=?, SALARY=?, TOT_WORK_YEAR=?, TOT_WORK_MONTH=? ");
			sql.append(", OTHER_INCOME=?, SORCE_OF_INCOME=?, MCID_TYPE=?, MCID_NO=?, M_TITLE_CODE=? ");
			sql.append(", M_TH_FIRST_NAME=?, M_TH_MID_NAME=?, M_TH_LAST_NAME=?, M_ETITLE_CODE=?, M_EN_FIRST_NAME=? ");    //50
			sql.append(", M_EN_MID_NAME=?, M_EN_LAST_NAME=?, M_BIRTH_DATE=?, M_GENDER=?, M_OCCUPATION=? ");
			sql.append(", M_COMPANY=?, M_POSITION=?, M_DEPARTMENT=?, M_INCOME=?, BUSINESS_SIZE=? ");
			sql.append(", DEGREE=?, LANDOWNER=?, BUILDING_TYPE=?, UPDATE_DATE=SYSDATE, UPDATE_BY=? ");   //64
			sql.append(", HOUSE_REGISTER_STATUS=?, COMPANY_TYPE=?, EMPLOYMENT_STATUS=?, WEALTH_KBANK=?, WEALTH_NON_KBANK=?  ");
			sql.append(", TH_OLD_LAST_NAME=?, M_TH_OLD_LAST_NAME=?, M_OFFICE_TEL=?, M_HOME_TEL=?, CIS_NO=? ");  //74
			sql.append(", AGE=?, NO_OF_CHILD=?, EMAIL_PRIMARY=?, EMAIL_SECONDARY=?, M_PHONE_NO=? ");
			sql.append(", PROFESSION=?, CURRENT_WORK_YEAR=?, CURRENT_WORK_MONTH=?, CONTACT_TIME=?, BUSINESS_NATURE=? ");
			sql.append(", BUSINESS_TYPE_OTHER=?, PREV_COMPANY=?, PREV_COMPANY_PHONE_NO=?, PREV_WORK_YEAR=?, PREV_WORK_MONTH=? ");
			sql.append(", CIRCULATION_INCOME=?, NET_PROFIT_INCOME=?, FREELANCE_INCOME=?, BANK_CUSTOMER_INCOME=?, SAVING_INCOME=? ");
			sql.append(", SORCE_OF_INCOME_OTHER=?, RECEIVE_INCOME_METHOD=?, RECEIVE_INCOME_BANK=?, MONTHLY_INCOME=?, POSITION_LEVEL=? ");  //99
			sql.append(", APPLICANT_INCOME_TYPE=?, M_OFFICE_TEL_EXT=?, ADDRESS_SEND_DOCUMENT=?, ADDRESS_DOCUMENT_LINE1=?, ADDRESS_DOCUMENT_LINE2=? ");
			sql.append(", LOAN_OBJECTIVE=?, LOAN_OBJECTIVE_DESC=?, PREV_COMPANY_TITLE=?, COMPANY_TITLE=?, EMPLOYMENT_TYPE=? ");
			sql.append(", PROFESSION_OTHER=?, ASSETS_AMOUNT=?, SUMMARY_WEALTH=?, SP_FLAG=?, LPM_NO=? ");
			sql.append(", C_CAR_OWNCAR=?, C_CAR_LAST_INST_YEAR=?, C_CAR_LAST_INST_MONTH=?, C_CAR_LOAN_WITH=? , C_CAR_FINANCIAL_AMOUNT=? ");
			sql.append(", C_CAR_TYPE=?, C_CAR_INSURE_EXP_MONTH=?, C_INSURANCE=?, CARDLINK_CUST_NO=?, SOLO_VIEW=?, LEC=?");
			sql.append(" WHERE APPLICATION_RECORD_ID=? AND PERSONAL_ID=?");
			String dSql = String.valueOf(sql);
			log.debug("Sql=" + dSql);
			ps = conn.prepareStatement(dSql);
			
			int index = 1;
			ps.setString(index++, personalM.getIdNo());
			ps.setString(index++, personalM.getPersonalType());
			ps.setString(index++, personalM.getCustomerType());
			ps.setString(index++, personalM.getMailingAddress());
			ps.setString(index++, personalM.getPreviousRecord());
			
			ps.setDouble(index++, personalM.getTimeInCurrentAddress());
			ps.setString(index++, personalM.getKConsentFlag());
			ps.setDate(index++, this.parseDate(personalM.getKConsentDate()));
			ps.setString(index++, personalM.getConsentFlag());
			ps.setDate(index++, this.parseDate(personalM.getConsentDate()));
			
			ps.setString(index++, personalM.getCoborrowerFlag());
			ps.setString(index++, personalM.getDebtIncludeFlag());
			ps.setString(index++, personalM.getCustomerNO());
			ps.setString(index++, personalM.getThaiTitleName());
			ps.setString(index++, personalM.getThaiFirstName());

			ps.setString(index++, personalM.getThaiLastName());
			ps.setString(index++, personalM.getThaiMidName());
			ps.setString(index++, personalM.getEngTitleName());
			ps.setString(index++, personalM.getEngFirstName());
			ps.setString(index++, personalM.getEngLastName());
			
			ps.setString(index++, personalM.getEngMidName());
			ps.setString(index++, personalM.getGender());
			ps.setDate(index++, this.parseDate(personalM.getBirthDate()));
			ps.setString(index++, personalM.getRace());
						
			ps.setString(index++, personalM.getNationality());			
			ps.setString(index++, personalM.getMaritalStatus());
			ps.setString(index++, personalM.getTaxID());
			ps.setString(index++, personalM.getCidType());
			ps.setDate(index++, this.parseDate(personalM.getCidIssueDate()));
			ps.setDate(index++, this.parseDate(personalM.getCidExpDate()));
			
			ps.setString(index++, personalM.getCidIssueBy());
			ps.setString(index++, personalM.getOccupation());
			ps.setString(index++, personalM.getCompanyName());
			ps.setString(index++, personalM.getBusinessType());
			ps.setString(index++, personalM.getPosition());
			
			ps.setString(index++, personalM.getPositionDesc());
			ps.setString(index++, personalM.getDepartment());
			ps.setBigDecimal(index++, personalM.getSalary());
			ps.setInt(index++, personalM.getTotWorkYear());
			ps.setInt(index++, personalM.getTotWorkMonth());
			
			ps.setBigDecimal(index++, personalM.getOtherIncome());
			ps.setString(index++, personalM.getSourceOfIncome());
			ps.setString(index++, personalM.getMcidType());
			ps.setString(index++, personalM.getMcidNo());
			ps.setString(index++, personalM.getMThaiTitleName());
			
			ps.setString(index++, personalM.getMThaiFirstName());
			ps.setString(index++, personalM.getMThaiMidName());
			ps.setString(index++, personalM.getMThaiLastName());
			ps.setString(index++, personalM.getMEngTitleName());
			ps.setString(index++, personalM.getMEngFirstName());    //50
			
			ps.setString(index++, personalM.getMEngMidName());
			ps.setString(index++, personalM.getMEngLastName());
			ps.setDate(index++, this.parseDate(personalM.getMBirthDate()));
			ps.setString(index++, personalM.getMGender());
			ps.setString(index++, personalM.getMOccupation());
			
			ps.setString(index++, personalM.getMCompany());
			ps.setString(index++, personalM.getMPosition());
			ps.setString(index++, personalM.getMDepartment());
			ps.setBigDecimal(index++, personalM.getMIncome());
			ps.setString(index++, personalM.getBusinessSize());
			
			ps.setString(index++, personalM.getDegree());
			ps.setString(index++, personalM.getLandOwnerShip());
			ps.setString(index++, personalM.getBuildingType());
			ps.setString(index++, personalM.getUpdateBy());
			
			ps.setString(index++, personalM.getHouseRegisStatus());
			ps.setString(index++, personalM.getCompanyType());
			ps.setString(index++, personalM.getEmploymentStatus());
			ps.setBigDecimal(index++, personalM.getWealthKBank());
			ps.setBigDecimal(index++, personalM.getWealthNonKBank());
			
			ps.setString(index++, personalM.getThaiOldLastName());
			ps.setString(index++, personalM.getmThaiOldLastName());
			ps.setString(index++, personalM.getmOfficeTel());
			ps.setString(index++, personalM.getmHomeTel());
			ps.setString(index++, personalM.getCisNo());
			
			ps.setInt(index++, personalM.getAge());
			ps.setInt(index++, personalM.getNoOfchild());
			ps.setString(index++, personalM.getEmail1());
			ps.setString(index++, personalM.getEmail2());
			ps.setString(index++, personalM.getmPhoneNo());  //80
			
			ps.setString(index++, personalM.getProfession());
			ps.setInt(index++, personalM.getCurrentWorkYear());
			ps.setInt(index++, personalM.getCurrentWorkMonth());
			ps.setString(index++, personalM.getContactTime());
			ps.setString(index++, personalM.getBusNature());
			
			ps.setString(index++, personalM.getBusTypeOther());
			ps.setString(index++, personalM.getPrevCompany());
			ps.setString(index++, personalM.getPrevCompanyPhoneNo());
			ps.setInt(index++, personalM.getPrevWorkYear());
			ps.setInt(index++, personalM.getPrevWorkMonth());
			
			ps.setBigDecimal(index++, personalM.getCirculationIncome());
			ps.setBigDecimal(index++, personalM.getNetProfitIncome());
			ps.setBigDecimal(index++, personalM.getFreelanceIncome());
			ps.setBigDecimal(index++, personalM.getBankCustomerIncome());
			ps.setBigDecimal(index++, personalM.getSavingIncome());
			
			ps.setString(index++, personalM.getSourceOfOtherIncome());
			ps.setString(index++, personalM.getReceiveIncomeMethod());
			ps.setString(index++, personalM.getReceiveIncomeBank());
			ps.setBigDecimal(index++, personalM.getMonthlyIncome());
			ps.setString(index++, personalM.getPositionLevel());
			
			ps.setString(index++, personalM.getApplicationIncomeType());
			ps.setString(index++, personalM.getmOfficeTelExt());
			ps.setString(index++, personalM.getAddressSentDoc());
			ps.setString(index++, personalM.getAddressDocLine1());
			ps.setString(index++, personalM.getAddressDocLine2());
			
			ps.setString(index++, personalM.getLoanObjective());
			ps.setString(index++, personalM.getLoanObjectiveDesc());
			ps.setString(index++, personalM.getPrevCompanyTitle());
			ps.setString(index++, personalM.getCompanyTitle());
			ps.setString(index++, personalM.getCompanyType());
			
			ps.setString(index++, personalM.getProfessionOther());
			ps.setBigDecimal(index++, personalM.getAssetAmount());
			ps.setBigDecimal(index++, personalM.getSummaryWealth());
			ps.setString(index++, personalM.getSpFlag());
			ps.setString(index++, personalM.getLpmNo());
			
			ps.setString(index++, personalM.getCarOwner());
			ps.setInt(index++, personalM.getCarLastInst_year());
			ps.setInt(index++, personalM.getCarLastInst_month());
			ps.setString(index++, personalM.getCarLoanWith());
			ps.setBigDecimal(index++, personalM.getCarFinancialAmount());
			
			ps.setString(index++, personalM.getCarType());
			ps.setInt(index++, personalM.getCarInsureExpMonth());
			ps.setString(index++, personalM.getInsurance());
			ps.setString(index++, personalM.getCardLinkCustNo());
			
			ps.setString(index++, personalM.getSoloView());
			ps.setString(index++, personalM.getLec());
			
			ps.setString(index++, appRecId);
			ps.setString(index++, personalM.getPersonalID());

			returnRows = ps.executeUpdate();
			
		} catch (Exception e) {
			log.fatal(e.getLocalizedMessage());
			throw new PLOrigApplicationException(e.getMessage());
		}finally{
			try{
				closeConnection(conn, ps);
			}catch(Exception e){
				log.fatal(e.getLocalizedMessage());
				throw new PLOrigApplicationException(e.getMessage());
			}
		}
		return returnRows;
		
	}
	
	@Override
	public void insertTableOrig_Personal_Info(PLPersonalInfoDataM personalM, String appRecId) throws PLOrigApplicationException{
		
		PreparedStatement ps = null;
		Connection conn = null;
		try {
			
			conn = getConnection();
			StringBuilder sql = new StringBuilder("");
			sql.append("INSERT INTO ORIG_PERSONAL_INFO ");
			sql.append("( PERSONAL_ID, APPLICATION_RECORD_ID, IDNO, PERSONAL_TYPE, CUSTOMER_TYPE ");
			sql.append(", MAILING_ADDRESS, PREVIOUS_RECORD, TIME_IN_CURRENT_ADDRESS, K_CONSENT_FLAG, K_CONSENT_DATE ");
			sql.append(", CONSENT_FLAG, CONSENT_DATE, COBORROWER_FLAG, DEBT_INCLUDE_FLAG, CUSTOMER_NO ");
			sql.append(", TH_TITLE_CODE, TH_FIRST_NAME, TH_LAST_NAME, TH_MID_NAME, EN_TITLE_CODE ");
			sql.append(", EN_FIRST_NAME, EN_LAST_NAME, EN_MID_NAME, GENDER, BIRTH_DATE ");
			sql.append(", RACE, NATIONALITY, MARRIED, TAX_ID, CID_TYPE ");
			sql.append(", CID_ISSUE_DATE, CID_EXP_DATE, CID_ISSUE_BY, OCCUPATION, COMPANY ");
			sql.append(", BUSINESS_TYPE, POSITION_CODE, POSITION_DESC, DEPARTMENT, SALARY ");
			sql.append(", TOT_WORK_YEAR, TOT_WORK_MONTH, OTHER_INCOME, SORCE_OF_INCOME, MCID_TYPE ");
			sql.append(", MCID_NO, M_TITLE_CODE, M_TH_FIRST_NAME, M_TH_MID_NAME, M_TH_LAST_NAME ");
			sql.append(", M_ETITLE_CODE, M_EN_FIRST_NAME, M_EN_MID_NAME, M_EN_LAST_NAME, M_BIRTH_DATE ");
			sql.append(", M_GENDER, M_OCCUPATION, M_COMPANY, M_POSITION, M_DEPARTMENT ");
			sql.append(", M_INCOME, BUSINESS_SIZE, DEGREE, LANDOWNER, BUILDING_TYPE ");
			sql.append(", CREATE_DATE, CREATE_BY, UPDATE_DATE, UPDATE_BY, HOUSE_REGISTER_STATUS ");
			sql.append(", COMPANY_TYPE, EMPLOYMENT_STATUS, WEALTH_KBANK, WEALTH_NON_KBANK, TH_OLD_LAST_NAME ");
			sql.append(", M_TH_OLD_LAST_NAME, M_OFFICE_TEL, M_HOME_TEL, CIS_NO, AGE ");
			sql.append(", NO_OF_CHILD, EMAIL_PRIMARY, EMAIL_SECONDARY, M_PHONE_NO, PROFESSION ");
			sql.append(", CURRENT_WORK_YEAR, CURRENT_WORK_MONTH, CONTACT_TIME, BUSINESS_NATURE, BUSINESS_TYPE_OTHER ");
			sql.append(", PREV_COMPANY, PREV_COMPANY_PHONE_NO, PREV_WORK_YEAR, PREV_WORK_MONTH, CIRCULATION_INCOME ");
			sql.append(", NET_PROFIT_INCOME, FREELANCE_INCOME, BANK_CUSTOMER_INCOME, SAVING_INCOME, SORCE_OF_INCOME_OTHER ");
			sql.append(", RECEIVE_INCOME_METHOD, RECEIVE_INCOME_BANK, MONTHLY_INCOME, POSITION_LEVEL, APPLICANT_INCOME_TYPE ");
			sql.append(", M_OFFICE_TEL_EXT, ADDRESS_SEND_DOCUMENT, ADDRESS_DOCUMENT_LINE1, ADDRESS_DOCUMENT_LINE2, LOAN_OBJECTIVE ");
			sql.append(", LOAN_OBJECTIVE_DESC, PREV_COMPANY_TITLE, COMPANY_TITLE, EMPLOYMENT_TYPE, PROFESSION_OTHER ");
			sql.append(", ASSETS_AMOUNT, SUMMARY_WEALTH, SP_FLAG, LPM_NO, C_CAR_OWNCAR ");
			sql.append(", C_CAR_LAST_INST_YEAR, C_CAR_LAST_INST_MONTH, C_CAR_LOAN_WITH, C_CAR_FINANCIAL_AMOUNT, C_CAR_TYPE ");
			sql.append(", C_CAR_INSURE_EXP_MONTH, C_INSURANCE, CARDLINK_CUST_NO, SOLO_VIEW, LEC) ");
			sql.append(" VALUES(?,?,?,?,?  ,?,?,?,?,?  ,?,?,?,?,?  ,?,?,?,?,? ");
			sql.append(",?,?,?,?,?  ,?,?,?,?,?  ,?,?,?,?,?  ,?,?,?,?,? ");
			sql.append(",?,?,?,?,?  ,?,?,?,?,?  ,?,?,?,?,?  ,?,?,?,?,? ");
			sql.append(",?,?,?,?,?  ,SYSDATE,?,SYSDATE,?,?  ,?,?,?,?,?  ,?,?,?,?,? ");
			sql.append(",?,?,?,?,?  ,?,?,?,?,?  ,?,?,?,?,?  ,?,?,?,?,? ");
			sql.append(",?,?,?,?,?  ,?,?,?,?,?  ,?,?,?,?,?  ,?,?,?,?,? ");
			sql.append(",?,?,?,?,?  ,?,?,?,?,? ) ");
			String dSql = String.valueOf(sql);
			log.debug("Sql=" + dSql);
			ps = conn.prepareStatement(dSql);
			
			int index = 1;
			
			ps.setString(index++, personalM.getPersonalID());
			ps.setString(index++, appRecId);
			ps.setString(index++, personalM.getIdNo());
			ps.setString(index++, personalM.getPersonalType());
			ps.setString(index++, personalM.getCustomerType());

			ps.setString(index++, personalM.getMailingAddress());
			ps.setString(index++, personalM.getPreviousRecord());
			ps.setDouble(index++, personalM.getTimeInCurrentAddress());
			ps.setString(index++, personalM.getKConsentFlag());
			ps.setDate(index++, this.parseDate(personalM.getKConsentDate()));
			
			ps.setString(index++, personalM.getConsentFlag());
			ps.setDate(index++, this.parseDate(personalM.getConsentDate()));
			ps.setString(index++, personalM.getCoborrowerFlag());
			ps.setString(index++, personalM.getDebtIncludeFlag());
			ps.setString(index++, personalM.getCustomerNO());
			
			ps.setString(index++, personalM.getThaiTitleName());
			ps.setString(index++, personalM.getThaiFirstName());
			ps.setString(index++, personalM.getThaiLastName());
			ps.setString(index++, personalM.getThaiMidName());
			ps.setString(index++, personalM.getEngTitleName());
			
			ps.setString(index++, personalM.getEngFirstName());
			ps.setString(index++, personalM.getEngLastName());
			ps.setString(index++, personalM.getEngMidName());
			ps.setString(index++, personalM.getGender());
			ps.setDate(index++, this.parseDate(personalM.getBirthDate()));
			
			ps.setString(index++, personalM.getRace());
			ps.setString(index++, personalM.getNationality());
			ps.setString(index++, personalM.getMaritalStatus());
			ps.setString(index++, personalM.getTaxID());
			ps.setString(index++, personalM.getCidType());
			
			ps.setDate(index++, this.parseDate(personalM.getCidIssueDate()));
			ps.setDate(index++, this.parseDate(personalM.getCidExpDate()));
			ps.setString(index++, personalM.getCidIssueBy());
			ps.setString(index++, personalM.getOccupation());
			ps.setString(index++, personalM.getCompanyName());
			
			ps.setString(index++, personalM.getBusinessType());
			ps.setString(index++, personalM.getPosition());
			ps.setString(index++, personalM.getPositionDesc());
			ps.setString(index++, personalM.getDepartment());
			ps.setBigDecimal(index++, personalM.getSalary());
			
			ps.setInt(index++, personalM.getTotWorkYear());
			ps.setInt(index++, personalM.getTotWorkMonth());
			ps.setBigDecimal(index++, personalM.getOtherIncome());
			ps.setString(index++, personalM.getSourceOfIncome());
			ps.setString(index++, personalM.getMcidType());
			
			ps.setString(index++, personalM.getMcidNo());
			ps.setString(index++, personalM.getMThaiTitleName());
			ps.setString(index++, personalM.getMThaiFirstName());
			ps.setString(index++, personalM.getMThaiMidName());
			ps.setString(index++, personalM.getMThaiLastName());
			
			ps.setString(index++, personalM.getMEngTitleName());
			ps.setString(index++, personalM.getMEngFirstName());
			ps.setString(index++, personalM.getMEngMidName());
			ps.setString(index++, personalM.getMEngLastName());
			ps.setDate(index++, this.parseDate(personalM.getMBirthDate()));
				
			ps.setString(index++, personalM.getMGender());
			ps.setString(index++, personalM.getMOccupation());
			ps.setString(index++, personalM.getMCompany());
			ps.setString(index++, personalM.getMPosition());
			ps.setString(index++, personalM.getMDepartment());
			
			ps.setBigDecimal(index++, personalM.getMIncome());
			ps.setString(index++, personalM.getBusinessSize());
			ps.setString(index++, personalM.getDegree());
			ps.setString(index++, personalM.getLandOwnerShip());
			ps.setString(index++, personalM.getBuildingType());
			
			ps.setString(index++, personalM.getCreateBy());
			ps.setString(index++, personalM.getUpdateBy());
			ps.setString(index++, personalM.getHouseRegisStatus());
			
			ps.setString(index++, personalM.getCompanyType());
			ps.setString(index++, personalM.getEmploymentStatus());
			ps.setBigDecimal(index++, personalM.getWealthKBank());
			ps.setBigDecimal(index++, personalM.getWealthNonKBank());
			ps.setString(index++, personalM.getThaiOldLastName());
			
			ps.setString(index++, personalM.getmThaiOldLastName());
			ps.setString(index++, personalM.getmOfficeTel());
			ps.setString(index++, personalM.getmHomeTel());
			ps.setString(index++, personalM.getCisNo());
			ps.setInt(index++, personalM.getAge());
			
			ps.setInt(index++, personalM.getNoOfchild());
			ps.setString(index++, personalM.getEmail1());
			ps.setString(index++, personalM.getEmail2());
			ps.setString(index++, personalM.getmPhoneNo());
			ps.setString(index++, personalM.getProfession());
			
			ps.setInt(index++, personalM.getCurrentWorkYear());
			ps.setInt(index++, personalM.getCurrentWorkMonth());
			ps.setString(index++, personalM.getContactTime());
			ps.setString(index++, personalM.getBusNature());
			ps.setString(index++, personalM.getBusTypeOther());
			
			ps.setString(index++, personalM.getPrevCompany());
			ps.setString(index++, personalM.getPrevCompanyPhoneNo());
			ps.setInt(index++, personalM.getPrevWorkYear());
			ps.setInt(index++, personalM.getPrevWorkMonth());
			ps.setBigDecimal(index++, personalM.getCirculationIncome());
			
			ps.setBigDecimal(index++, personalM.getNetProfitIncome());
			ps.setBigDecimal(index++, personalM.getFreelanceIncome());
			ps.setBigDecimal(index++, personalM.getBankCustomerIncome());
			ps.setBigDecimal(index++, personalM.getSavingIncome());
			ps.setString(index++, personalM.getSourceOfOtherIncome());
			
			ps.setString(index++, personalM.getReceiveIncomeMethod());
			ps.setString(index++, personalM.getReceiveIncomeBank());
			ps.setBigDecimal(index++, personalM.getMonthlyIncome());
			ps.setString(index++, personalM.getPositionLevel());
			ps.setString(index++, personalM.getApplicationIncomeType());
			
			ps.setString(index++, personalM.getmOfficeTelExt());
			ps.setString(index++, personalM.getAddressSentDoc());
			ps.setString(index++, personalM.getAddressDocLine1());
			ps.setString(index++, personalM.getAddressDocLine2());
			ps.setString(index++, personalM.getLoanObjective());
			
			ps.setString(index++, personalM.getLoanObjectiveDesc());
			ps.setString(index++, personalM.getPrevCompanyTitle());
			ps.setString(index++, personalM.getCompanyTitle());
			ps.setString(index++, personalM.getCompanyType());
			ps.setString(index++, personalM.getProfessionOther());
			
			ps.setBigDecimal(index++, personalM.getAssetAmount());
			ps.setBigDecimal(index++, personalM.getSummaryWealth());
			ps.setString(index++, personalM.getSpFlag());
			ps.setString(index++, personalM.getLpmNo());
			ps.setString(index++, personalM.getCarOwner());
			
			ps.setInt(index++, personalM.getCarLastInst_year());
			ps.setInt(index++, personalM.getCarLastInst_month());
			ps.setString(index++, personalM.getCarLoanWith());
			ps.setBigDecimal(index++, personalM.getCarFinancialAmount());
			ps.setString(index++, personalM.getCarType());
			
			ps.setInt(index++, personalM.getCarInsureExpMonth());
			ps.setString(index++, personalM.getInsurance());
			ps.setString(index++, personalM.getCardLinkCustNo());
			
			ps.setString(index++, personalM.getSoloView());
			ps.setString(index++, personalM.getLec());
						
			ps.executeUpdate();
		}catch (Exception e){
			log.fatal(e.getLocalizedMessage());
			throw new PLOrigApplicationException(e.getMessage());
		}finally{
			try {
				closeConnection(conn, ps);
			}catch(Exception e){
				log.fatal(e.getLocalizedMessage());
				throw new PLOrigApplicationException(e.getMessage());
			}
		}
		
	}
	
	private void deleteTableORIG_PERSONAL_INFO(Vector<String> personalIDVect, String appRecId)throws PLOrigApplicationException{	
		PreparedStatement ps = null;
		Connection conn = null;
		try{
			conn = getConnection();
			StringBuilder sql = new StringBuilder("");
			sql.append(" DELETE ORIG_PERSONAL_INFO ");
			sql.append(" WHERE APPLICATION_RECORD_ID = ? AND PERSONAL_ID NOT IN ");
			sql.append("( ");
			sql.append(ParameterINVect(personalIDVect));			
			sql.append(" )");			
			String dSql = String.valueOf(sql);
			log.debug("Sql= "+dSql);
			int index = 1;
			ps = conn.prepareStatement(dSql);
			ps.setString(index++, appRecId);			
			PreparedStatementParameter(index, ps, personalIDVect);		
			ps.executeUpdate();

		}catch(Exception e){
			log.fatal(e.getLocalizedMessage());
			throw new PLOrigApplicationException(e.getMessage());
		}finally{
			try {
				closeConnection(conn, ps);
			} catch (Exception e) {
				log.fatal(e.getLocalizedMessage());
				throw new PLOrigApplicationException(e.getMessage());
			}
		}		
	}
	
	private String getOrigPersonalInfoSQL(){
		StringBuilder SQL = new StringBuilder("");
			SQL.append("SELECT PERSONAL_ID, IDNO, PERSONAL_TYPE, CUSTOMER_TYPE, MAILING_ADDRESS ");
			SQL.append(", PREVIOUS_RECORD, TIME_IN_CURRENT_ADDRESS, K_CONSENT_FLAG, K_CONSENT_DATE, CONSENT_FLAG ");
			SQL.append(", CONSENT_DATE, COBORROWER_FLAG, DEBT_INCLUDE_FLAG, CUSTOMER_NO, TH_TITLE_CODE ");
			SQL.append(", TH_FIRST_NAME, TH_LAST_NAME, TH_MID_NAME, EN_TITLE_CODE, EN_FIRST_NAME ");
			SQL.append(", EN_LAST_NAME, EN_MID_NAME, GENDER, BIRTH_DATE, RACE ");
			SQL.append(", NATIONALITY, MARRIED, TAX_ID, CID_TYPE, CID_ISSUE_DATE ");
			SQL.append(", CID_EXP_DATE, CID_ISSUE_BY, OCCUPATION, COMPANY, BUSINESS_TYPE ");
			SQL.append(", POSITION_CODE, POSITION_DESC, DEPARTMENT, SALARY, TOT_WORK_YEAR ");
			SQL.append(", TOT_WORK_MONTH, OTHER_INCOME, SORCE_OF_INCOME, MCID_TYPE, MCID_NO ");
			SQL.append(", M_TITLE_CODE, M_TH_FIRST_NAME, M_TH_MID_NAME, M_TH_LAST_NAME, M_ETITLE_CODE ");
			SQL.append(", M_EN_FIRST_NAME, M_EN_MID_NAME, M_EN_LAST_NAME, M_BIRTH_DATE, M_GENDER ");
			SQL.append(", M_OCCUPATION, M_COMPANY, M_POSITION, M_DEPARTMENT, M_INCOME ");
			SQL.append(", BUSINESS_SIZE, DEGREE, LANDOWNER, BUILDING_TYPE, CREATE_DATE ");
			SQL.append(", CREATE_BY, UPDATE_DATE, UPDATE_BY, HOUSE_REGISTER_STATUS, COMPANY_TYPE ");
			SQL.append(", EMPLOYMENT_STATUS, WEALTH_KBANK, WEALTH_NON_KBANK, TH_OLD_LAST_NAME, M_TH_OLD_LAST_NAME ");
			SQL.append(", M_OFFICE_TEL, M_HOME_TEL, CIS_NO, AGE, NO_OF_CHILD ");
			SQL.append(", EMAIL_PRIMARY, EMAIL_SECONDARY, M_PHONE_NO, PROFESSION, CURRENT_WORK_YEAR ");
			SQL.append(", CURRENT_WORK_MONTH, CONTACT_TIME, BUSINESS_NATURE, BUSINESS_TYPE_OTHER, PREV_COMPANY ");
			SQL.append(", PREV_COMPANY_PHONE_NO, PREV_WORK_YEAR, PREV_WORK_MONTH, CIRCULATION_INCOME, NET_PROFIT_INCOME ");
			SQL.append(", FREELANCE_INCOME, BANK_CUSTOMER_INCOME, SAVING_INCOME, SORCE_OF_INCOME_OTHER, RECEIVE_INCOME_METHOD ");
			SQL.append(", RECEIVE_INCOME_BANK, MONTHLY_INCOME, POSITION_LEVEL, APPLICANT_INCOME_TYPE, M_OFFICE_TEL_EXT ");
			SQL.append(", ADDRESS_SEND_DOCUMENT, ADDRESS_DOCUMENT_LINE1, ADDRESS_DOCUMENT_LINE2, LOAN_OBJECTIVE, LOAN_OBJECTIVE_DESC ");
			SQL.append(", PREV_COMPANY_TITLE, COMPANY_TITLE, EMPLOYMENT_TYPE, PROFESSION_OTHER, ASSETS_AMOUNT ");
			SQL.append(", SUMMARY_WEALTH, SP_FLAG, LPM_NO, C_CAR_OWNCAR , C_CAR_LAST_INST_YEAR ");
			SQL.append(", C_CAR_LAST_INST_MONTH, C_CAR_LOAN_WITH, C_CAR_FINANCIAL_AMOUNT, C_CAR_TYPE, C_CAR_INSURE_EXP_MONTH ");
			SQL.append(", C_INSURANCE, CARDLINK_CUST_NO, SOLO_VIEW, LEC ");
			SQL.append(" FROM ORIG_PERSONAL_INFO WHERE APPLICATION_RECORD_ID = ? ");		
		return SQL.toString();
	}
	
	private String getOrigPersonalInfoIDNoSQL(){
		StringBuilder SQL = new StringBuilder("");
			SQL.append("SELECT PERSONAL_ID, IDNO, PERSONAL_TYPE, CUSTOMER_TYPE, MAILING_ADDRESS ");
			SQL.append(", PREVIOUS_RECORD, TIME_IN_CURRENT_ADDRESS, K_CONSENT_FLAG, K_CONSENT_DATE, CONSENT_FLAG ");
			SQL.append(", CONSENT_DATE, COBORROWER_FLAG, DEBT_INCLUDE_FLAG, CUSTOMER_NO, TH_TITLE_CODE ");
			SQL.append(", TH_FIRST_NAME, TH_LAST_NAME, TH_MID_NAME, EN_TITLE_CODE, EN_FIRST_NAME ");
			SQL.append(", EN_LAST_NAME, EN_MID_NAME, GENDER, BIRTH_DATE, RACE ");
			SQL.append(", NATIONALITY, MARRIED, TAX_ID, CID_TYPE, CID_ISSUE_DATE ");
			SQL.append(", CID_EXP_DATE, CID_ISSUE_BY, OCCUPATION, COMPANY, BUSINESS_TYPE ");
			SQL.append(", POSITION_CODE, POSITION_DESC, DEPARTMENT, SALARY, TOT_WORK_YEAR ");
			SQL.append(", TOT_WORK_MONTH, OTHER_INCOME, SORCE_OF_INCOME, MCID_TYPE, MCID_NO ");
			SQL.append(", M_TITLE_CODE, M_TH_FIRST_NAME, M_TH_MID_NAME, M_TH_LAST_NAME, M_ETITLE_CODE ");
			SQL.append(", M_EN_FIRST_NAME, M_EN_MID_NAME, M_EN_LAST_NAME, M_BIRTH_DATE, M_GENDER ");
			SQL.append(", M_OCCUPATION, M_COMPANY, M_POSITION, M_DEPARTMENT, M_INCOME ");
			SQL.append(", BUSINESS_SIZE, DEGREE, LANDOWNER, BUILDING_TYPE, CREATE_DATE ");
			SQL.append(", CREATE_BY, UPDATE_DATE, UPDATE_BY, HOUSE_REGISTER_STATUS, COMPANY_TYPE ");
			SQL.append(", EMPLOYMENT_STATUS, WEALTH_KBANK, WEALTH_NON_KBANK, TH_OLD_LAST_NAME, M_TH_OLD_LAST_NAME ");
			SQL.append(", M_OFFICE_TEL, M_HOME_TEL, CIS_NO, AGE, NO_OF_CHILD ");
			SQL.append(", EMAIL_PRIMARY, EMAIL_SECONDARY, M_PHONE_NO, PROFESSION, CURRENT_WORK_YEAR ");
			SQL.append(", CURRENT_WORK_MONTH, CONTACT_TIME, BUSINESS_NATURE, BUSINESS_TYPE_OTHER, PREV_COMPANY ");
			SQL.append(", PREV_COMPANY_PHONE_NO, PREV_WORK_YEAR, PREV_WORK_MONTH, CIRCULATION_INCOME, NET_PROFIT_INCOME ");
			SQL.append(", FREELANCE_INCOME, BANK_CUSTOMER_INCOME, SAVING_INCOME, SORCE_OF_INCOME_OTHER, RECEIVE_INCOME_METHOD ");
			SQL.append(", RECEIVE_INCOME_BANK, MONTHLY_INCOME, POSITION_LEVEL, APPLICANT_INCOME_TYPE, M_OFFICE_TEL_EXT ");
			SQL.append(", ADDRESS_SEND_DOCUMENT, ADDRESS_DOCUMENT_LINE1, ADDRESS_DOCUMENT_LINE2, LOAN_OBJECTIVE, LOAN_OBJECTIVE_DESC ");
			SQL.append(", PREV_COMPANY_TITLE, COMPANY_TITLE, EMPLOYMENT_TYPE, PROFESSION_OTHER, ASSETS_AMOUNT ");
			SQL.append(", SUMMARY_WEALTH, SP_FLAG, LPM_NO, C_CAR_OWNCAR , C_CAR_LAST_INST_YEAR ");
			SQL.append(", C_CAR_LAST_INST_MONTH, C_CAR_LOAN_WITH, C_CAR_FINANCIAL_AMOUNT, C_CAR_TYPE, C_CAR_INSURE_EXP_MONTH ");
			SQL.append(", C_INSURANCE, CARDLINK_CUST_NO , SOLO_VIEW, LEC ");
			SQL.append(" FROM ORIG_PERSONAL_INFO WHERE IDNO = ? ");		
		return SQL.toString();
	}
	
	private void setModelPersonalInfoM(int index,PLPersonalInfoDataM personalM,ResultSet rs) throws SQLException{
		
			personalM.setPersonalID(rs.getString(index++));
			personalM.setIdNo(rs.getString(index++));
			personalM.setPersonalType(rs.getString(index++));
			personalM.setCustomerType(rs.getString(index++));
			personalM.setMailingAddress(rs.getString(index++));
			
			personalM.setPreviousRecord(rs.getString(index++));
			personalM.setTimeInCurrentAddress(rs.getDouble(index++));
			personalM.setKConsentFlag(rs.getString(index++));
			personalM.setKConsentDate(rs.getTimestamp(index++));
			personalM.setConsentFlag(rs.getString(index++));
			
			personalM.setConsentDate(rs.getTimestamp(index++));
			personalM.setCoborrowerFlag(rs.getString(index++));
			personalM.setDebtIncludeFlag(rs.getString(index++));
			personalM.setCustomerNO(rs.getString(index++));
			personalM.setThaiTitleName(rs.getString(index++));
			
			personalM.setThaiFirstName(rs.getString(index++));
			personalM.setThaiLastName(rs.getString(index++));
			personalM.setThaiMidName(rs.getString(index++));
			personalM.setEngTitleName(rs.getString(index++));
			personalM.setEngFirstName(rs.getString(index++));
			
			personalM.setEngLastName(rs.getString(index++));
			personalM.setEngMidName(rs.getString(index++));
			personalM.setGender(rs.getString(index++));
			personalM.setBirthDate(rs.getDate(index++));
			personalM.setRace(rs.getString(index++));
			
			personalM.setNationality(rs.getString(index++));
			personalM.setMaritalStatus(rs.getString(index++));
			personalM.setTaxID(rs.getString(index++));
			personalM.setCidType(rs.getString(index++));
			personalM.setCidIssueDate(rs.getDate(index++));
			
			personalM.setCidExpDate(rs.getDate(index++));
			personalM.setCidIssueBy(rs.getString(index++));
			personalM.setOccupation(rs.getString(index++));
			personalM.setCompanyName(rs.getString(index++));
			personalM.setBusinessType(rs.getString(index++));
			
			personalM.setPosition(rs.getString(index++));
			personalM.setPositionDesc(rs.getString(index++));
			personalM.setDepartment(rs.getString(index++));
			personalM.setSalary(rs.getBigDecimal(index++));
			personalM.setTotWorkYear(rs.getInt(index++));
			
			personalM.setTotWorkMonth(rs.getInt(index++));
			personalM.setOtherIncome(rs.getBigDecimal(index++));
			personalM.setSourceOfIncome(rs.getString(index++));
			personalM.setMcidType(rs.getString(index++));
			personalM.setMcidNo(rs.getString(index++));
			
			personalM.setMThaiTitleName(rs.getString(index++));
			personalM.setMThaiFirstName(rs.getString(index++));
			personalM.setMThaiMidName(rs.getString(index++));
			personalM.setMThaiLastName(rs.getString(index++));
			personalM.setMEngTitleName(rs.getString(index++));
			
			personalM.setMEngFirstName(rs.getString(index++));
			personalM.setMEngMidName(rs.getString(index++));
			personalM.setMEngLastName(rs.getString(index++));
			personalM.setMBirthDate(rs.getDate(index++));
			personalM.setMGender(rs.getString(index++));
			
			personalM.setMOccupation(rs.getString(index++));
			personalM.setMCompany(rs.getString(index++));
			personalM.setMPosition(rs.getString(index++));
			personalM.setMDepartment(rs.getString(index++));
			personalM.setMIncome(rs.getBigDecimal(index++));
			
			personalM.setBusinessSize(rs.getString(index++));
			personalM.setDegree(rs.getString(index++));
			personalM.setLandOwnerShip(rs.getString(index++));
			personalM.setBuildingType(rs.getString(index++));
			personalM.setCreateDate(rs.getTimestamp(index++));
			
			personalM.setCreateBy(rs.getString(index++));
			personalM.setUpdateDate(rs.getTimestamp(index++));
			personalM.setUpdateBy(rs.getString(index++));
			personalM.setHouseRegisStatus(rs.getString(index++));
			personalM.setCompanyType(rs.getString(index++));
			
			personalM.setEmploymentStatus(rs.getString(index++));
			personalM.setWealthKBank(rs.getBigDecimal(index++));
			personalM.setWealthNonKBank(rs.getBigDecimal(index++));
			personalM.setThaiOldLastName(rs.getString(index++));
			personalM.setmThaiOldLastName(rs.getString(index++));
			
			personalM.setmOfficeTel(rs.getString(index++));
			personalM.setmHomeTel(rs.getString(index++));
			personalM.setCisNo(rs.getString(index++));
			personalM.setAge(rs.getInt(index++));
			personalM.setNoOfchild(rs.getInt(index++));
			
			personalM.setEmail1(rs.getString(index++));
			personalM.setEmail2(rs.getString(index++));
			personalM.setmPhoneNo(rs.getString(index++));
			personalM.setProfession(rs.getString(index++));
			personalM.setCurrentWorkYear(rs.getInt(index++));
			
			personalM.setCurrentWorkMonth(rs.getInt(index++));
			personalM.setContactTime(rs.getString(index++));
			personalM.setBusNature(rs.getString(index++));
			personalM.setBusTypeOther(rs.getString(index++));
			personalM.setPrevCompany(rs.getString(index++));
			
			personalM.setPrevCompanyPhoneNo(rs.getString(index++));
			personalM.setPrevWorkYear(rs.getInt(index++));
			personalM.setPrevWorkMonth(rs.getInt(index++));
			personalM.setCirculationIncome(rs.getBigDecimal(index++));
			personalM.setNetProfitIncome(rs.getBigDecimal(index++));
			
			personalM.setFreelanceIncome(rs.getBigDecimal(index++));
			personalM.setBankCustomerIncome(rs.getBigDecimal(index++));
			personalM.setSavingIncome(rs.getBigDecimal(index++));
			personalM.setSourceOfOtherIncome(rs.getString(index++));
			personalM.setReceiveIncomeMethod(rs.getString(index++));
			
			personalM.setReceiveIncomeBank(rs.getString(index++));
			personalM.setMonthlyIncome(rs.getBigDecimal(index++));
			personalM.setPositionLevel(rs.getString(index++));
			personalM.setApplicationIncomeType(rs.getString(index++));
			personalM.setmOfficeTelExt(rs.getString(index++));
			
			personalM.setAddressSentDoc(rs.getString(index++));
			personalM.setAddressDocLine1(rs.getString(index++));
			personalM.setAddressDocLine2(rs.getString(index++));
			personalM.setLoanObjective(rs.getString(index++));
			personalM.setLoanObjectiveDesc(rs.getString(index++));
			
			personalM.setPrevCompanyTitle(rs.getString(index++));
			personalM.setCompanyTitle(rs.getString(index++));
			personalM.setCompanyType(rs.getString(index++));
			personalM.setProfessionOther(rs.getString(index++));
			personalM.setAssetAmount(rs.getBigDecimal(index++));
			
			personalM.setSummaryWealth(rs.getBigDecimal(index++));
			personalM.setSpFlag(rs.getString(index++));
			personalM.setLpmNo(rs.getString(index++));
			personalM.setCarOwner(rs.getString(index++));
			personalM.setCarLastInst_year(rs.getInt(index++));
			
			personalM.setCarLastInst_month(rs.getInt(index++));
			personalM.setCarLoanWith(rs.getString(index++));
			personalM.setCarFinancialAmount(rs.getBigDecimal(index++));
			personalM.setCarType(rs.getString(index++));
			personalM.setCarInsureExpMonth(rs.getInt(index++));
			
			personalM.setInsurance(rs.getString(index++));
			personalM.setCardLinkCustNo(rs.getString(index++));
			
			personalM.setSoloView(rs.getString(index++));
			personalM.setLec(rs.getString(index++));
	}
	
	@Override
	public Vector<PLPersonalInfoDataM> loadOrigPersonalInfo(String appRecId, String busClassId)throws PLOrigApplicationException {
		PreparedStatement ps = null;
		ResultSet rs = null;		

		Vector<PLPersonalInfoDataM> personalVect = new Vector<PLPersonalInfoDataM>();
		Connection conn = null;
		try{
			conn = getConnection();
			
			String SQL = getOrigPersonalInfoSQL();			
			log.debug("SQL >> "+SQL);
			
			ps = conn.prepareStatement(SQL);
			rs = null;
			ps.setString(1, appRecId);

			rs = ps.executeQuery();
						
			while(rs.next()){				
				PLPersonalInfoDataM personalM = new PLPersonalInfoDataM();
				
				int index = 1;				
				setModelPersonalInfoM(index, personalM, rs);
				
				this.LoadSubTable(personalM, busClassId);
				
				personalVect.add(personalM);
			}						
		}catch(Exception e){
			log.fatal(e.getLocalizedMessage());
			throw new PLOrigApplicationException(e.getMessage());
		}finally{
			try{
				closeConnection(conn, rs, ps);
			}catch(Exception e){
				log.fatal(e.getLocalizedMessage());
				throw new PLOrigApplicationException(e.getMessage());
			}
		}
		return personalVect;
	}
	
	public void LoadSubTable(PLPersonalInfoDataM personalM, String busClassId) throws PLOrigApplicationException{
		
		try{
			PLOrigPersonalAddressDAO addressDAO = PLORIGDAOFactory.getPLOrigPersonalAddressDAO();			
			personalM.setAddressVect(addressDAO.loadPersonalAddress(personalM.getPersonalID()));
			
			PLOrigNCBDocumentDAO ncbDocumentDAO = PLORIGDAOFactory.getPLOrigNCBDocumentDAO();
			personalM.setNcbDocVect(ncbDocumentDAO.loadNCBDocument(personalM.getPersonalID()));
			
			PLOrigFinancialInfoDAO financialInfoDAO = PLORIGDAOFactory.getPLOrigFinancialInfoDAO();
			personalM.setFinancialInfoVect(financialInfoDAO.loadFinancialInfo(personalM.getPersonalID()));
			
			PLOrigCardInformationDAO cardInformationDAO = PLORIGDAOFactory.getPLOrigCardInformationDAO();
			personalM.setCardInformation(cardInformationDAO.loadOrigCard(personalM.getPersonalID(), busClassId));
			
			XRulesRemoteDAOUtilManager xRulesDAOUtil = ORIGEJBService.getORIGXRulesDAOUtilManager();
			personalM.setXrulesVerification(xRulesDAOUtil.loadPLXRulesVerificationResults(personalM.getPersonalID()));
			
			PLXRulesVerificationResultDataM xrulesVerM = personalM.getXrulesVerification();
			if(null == xrulesVerM){
				xrulesVerM = new PLXRulesVerificationResultDataM();
				personalM.setXrulesVerification(xrulesVerM);
			}
			if(!OrigUtil.isEmptyString(xrulesVerM.getNCBTrackingCode())){
				NCBServiceManager ncbBean = ORIGEJBService.getNCBServiceManager();
				NCBOutputDataM ncbOutPutM = ncbBean.getNCBOutput(xrulesVerM.getNCBTrackingCode());				
				xrulesVerM.setNcbOutPutM(ncbOutPutM);
			}
			
		} catch (Exception e) {
			log.fatal("Error ",e);
			throw new PLOrigApplicationException(e.getMessage());
		}
		
	}
	
	public void LoadSubTable(PLPersonalInfoDataM personalM) throws PLOrigApplicationException{
		
		try{
			PLOrigPersonalAddressDAO origPersonalAddress = PLORIGDAOFactory.getPLOrigPersonalAddressDAO();			
			personalM.setAddressVect(origPersonalAddress.loadPersonalAddress(personalM.getPersonalID()));
			
			PLOrigNCBDocumentDAO plOrigNCBDocumentDAO = PLORIGDAOFactory.getPLOrigNCBDocumentDAO();
			personalM.setNcbDocVect(plOrigNCBDocumentDAO.loadNCBDocument(personalM.getPersonalID()));
			
			PLOrigFinancialInfoDAO plOrigFinancialInfoDAO = PLORIGDAOFactory.getPLOrigFinancialInfoDAO();
			personalM.setFinancialInfoVect(plOrigFinancialInfoDAO.loadFinancialInfo(personalM.getPersonalID()));
			
			PLOrigCardInformationDAO plOrigCardInformationDAO = PLORIGDAOFactory.getPLOrigCardInformationDAO();
			personalM.setCardInformation(plOrigCardInformationDAO.loadOrigCard(personalM.getPersonalID(), null));
			
			XRulesRemoteDAOUtilManager xRulesDAOUtil = ORIGEJBService.getORIGXRulesDAOUtilManager();
			personalM.setXrulesVerification(xRulesDAOUtil.loadPLXRulesVerificationResults(personalM.getPersonalID()));
			
			PLXRulesVerificationResultDataM xrulesVerM = personalM.getXrulesVerification();
			if(null == xrulesVerM){
				xrulesVerM = new PLXRulesVerificationResultDataM();
			}
			if(!OrigUtil.isEmptyString(xrulesVerM.getNCBTrackingCode())){
				NCBServiceManager ncbBean = ORIGEJBService.getNCBServiceManager();
				NCBOutputDataM ncbOutPutM = ncbBean.getNCBOutput(xrulesVerM.getNCBTrackingCode());				
				xrulesVerM.setNcbOutPutM(ncbOutPutM);
			}
			
		} catch (Exception e) {
			log.fatal("Error ",e);
			throw new PLOrigApplicationException(e.getMessage());
		}
		
	}
	
	private void updateSaveSubTable(PLPersonalInfoDataM personalM, String personalID, PLApplicationDataM appM) throws PLOrigApplicationException{	
		try{			
			if(!OrigUtil.isEmptyVector(personalM.getAddressVect())){
				PLOrigPersonalAddressDAO addressDAO = PLORIGDAOFactory.getPLOrigPersonalAddressDAO();
					addressDAO.updateSavePersonalAddress(personalM.getAddressVect(), personalID);
			}else{
				PLOrigPersonalAddressDAO addressDAO = PLORIGDAOFactory.getPLOrigPersonalAddressDAO();
					addressDAO.deletePersonalAddress(personalID);
			}
			if(!OrigUtil.isEmptyVector(personalM.getNcbDocVect())){
				PLOrigNCBDocumentDAO ncbDocumentDAO = PLORIGDAOFactory.getPLOrigNCBDocumentDAO();
					ncbDocumentDAO.updateSaveNCBDocument(personalM.getNcbDocVect(), personalID);			
			}else{
				PLOrigNCBDocumentDAO ncbDocumentDAO = PLORIGDAOFactory.getPLOrigNCBDocumentDAO();
					ncbDocumentDAO.deleteORIG_NCB_DOCUMENT(personalID);
			}
			if(!OrigUtil.isEmptyVector(personalM.getFinancialInfoVect())){
				PLOrigFinancialInfoDAO financialInfoDAO = PLORIGDAOFactory.getPLOrigFinancialInfoDAO();
					financialInfoDAO.updateSaveFinancialInfo(personalM.getFinancialInfoVect(), personalID);
			}			
			if(!OrigUtil.isEmptyObject(personalM.getCardInformation())){
				PLOrigCardInformationDAO cardInformationDAO = PLORIGDAOFactory.getPLOrigCardInformationDAO();
					cardInformationDAO.saveUpdateOrigCard(personalM.getCardInformation(), personalID, appM);
			}
			if(!OrigUtil.isEmptyObject(personalM.getXrulesVerification())){
				XRulesRemoteDAOUtilManager xRulesDAOBean = ORIGEJBService.getORIGXRulesDAOUtilManager();
					xRulesDAOBean.updateSavePLXRulesVerificationResult(personalM.getXrulesVerification(), personalID);
			}			
		} catch (Exception e) {
			log.fatal(e.getLocalizedMessage());
			throw new PLOrigApplicationException(e.getMessage());
		}		
	}

	@Override
	public PLPersonalInfoDataM LoadPersonalInfoModel(String idNo,String personalType) throws PLOrigApplicationException {
		return LoadPLPersonalInfoDataM(idNo,personalType);
	}
	
	private PLPersonalInfoDataM LoadPLPersonalInfoDataM(String idNo,String personalType) throws PLOrigApplicationException {
		
		PreparedStatement ps = null;
		
		ResultSet rs = null;
		
		PLPersonalInfoDataM personalM = new PLPersonalInfoDataM();
		Connection conn = null;
		try{
			conn = getConnection();
			
			String SQL = getOrigPersonalInfoIDNoSQL();			
			log.debug("SQL >> "+SQL);
	
			ps = conn.prepareStatement(SQL);
					
			ps.setString(1, idNo);			
			rs = ps.executeQuery();
			
			if(rs.next()){				
				int index = 1;
				setModelPersonalInfoM(index, personalM, rs);
				
				personalM.setAddressVect(PLORIGDAOFactory.getPLOrigPersonalAddressDAO()
													.loadPersonalAddress(personalM.getPersonalID()));				
			}			
		}catch (Exception e) {
			log.fatal(e.getLocalizedMessage());
			throw new PLOrigApplicationException(e.getMessage());
		} finally {
			try {
				closeConnection(conn, rs, ps);
			} catch (Exception e) {
				log.fatal(e.getLocalizedMessage());
				throw new PLOrigApplicationException(e.getMessage());
			}
		}		
		return (personalM == null)? new PLPersonalInfoDataM():personalM;
	}

	@Override
	public PLPersonalInfoDataM loadPersonalM(String appRecId) throws PLOrigApplicationException {
		PLPersonalInfoDataM personalM = new PLPersonalInfoDataM();
		PreparedStatement ps = null;
		ResultSet rs = null;
		Connection conn = null;
		try{
			conn = getConnection();
			String SQL = getOrigPersonalInfoSQL();			
			log.debug("SQL >> "+SQL);
			
			ps = conn.prepareStatement(SQL);
			rs = null;
			ps.setString(1, appRecId);

			rs = ps.executeQuery();
						
			if(rs.next()){				
				int index = 1;				
				setModelPersonalInfoM(index, personalM, rs);	
			}			
		}catch(Exception e){
			log.fatal(e.getLocalizedMessage());
			throw new PLOrigApplicationException(e.getMessage());
		}finally{
			try{
				closeConnection(conn, rs, ps);
			}catch(Exception e){
				log.fatal(e.getLocalizedMessage());
				throw new PLOrigApplicationException(e.getMessage());
			}
		}		
		return personalM;	
	}
	
	public Vector<PLPersonalInfoDataM> loadOrigPersonalInfo_IncreaseDecrease(String appRecId, PLApplicationDataM currentAppM)throws PLOrigApplicationException {
		PreparedStatement ps = null;
		ResultSet rs = null;		

		Vector<PLPersonalInfoDataM> personalVect = new Vector<PLPersonalInfoDataM>();		
		Connection conn = null;
		try{
			conn = getConnection();
			
			String SQL = getOrigPersonalInfoSQL();			
			log.debug("SQL >> "+SQL);
			
			ps = conn.prepareStatement(SQL);
			rs = null;
			ps.setString(1, appRecId);

			rs = ps.executeQuery();
			
			while(rs.next()){				
				PLPersonalInfoDataM personalM = new PLPersonalInfoDataM();
				
				int index = 1;				
				setModelPersonalInfoM(index, personalM, rs);
				
				LoadTableAddressCard(personalM, currentAppM);
				
				// set null
				PLPersonalInfoDataM currentPersonM = currentAppM.getPLPersonalInfoDataM(OrigConstant.PERSONAL_TYPE_APPLICANT);
				if(currentPersonM == null) currentPersonM = new PLPersonalInfoDataM();
				personalM.setPersonalID(currentPersonM.getPersonalID());
				
				personalVect.add(personalM);
			}
			
		}catch(Exception e){
			log.fatal(e.getLocalizedMessage());
			throw new PLOrigApplicationException(e.getMessage());
		}finally{
			try{
				closeConnection(conn, rs, ps);
			}catch(Exception e){
				log.fatal(e.getLocalizedMessage());
				throw new PLOrigApplicationException(e.getMessage());
			}
		}
		return personalVect;		
	}
	
	private void LoadTableAddressCard(PLPersonalInfoDataM personalM, PLApplicationDataM currentAppM) throws PLOrigApplicationException{		
		try{												
			personalM.setAddressVect(PLORIGDAOFactory.getPLOrigPersonalAddressDAO().loadPersonalAddress_IncreaseDecrease(personalM.getPersonalID(), currentAppM));
			personalM.setCardInformation(PLORIGDAOFactory.getPLOrigCardInformationDAO().loadOrigCard_IncreaseDecrease(personalM.getPersonalID(), currentAppM));
			
		}catch(Exception e){
			log.fatal("Error LoadTableAddress ",e);
			throw new PLOrigApplicationException(e.getMessage());
		}		
	}

	@Override
	public void SaveUpdateNcbORIGPersonalInfo(Vector<PLPersonalInfoDataM> personalVect, String appRecId)throws PLOrigApplicationException {
		try{			
			if(!OrigUtil.isEmptyVector(personalVect)){				
				Vector<String> personalIdVect = new Vector<String>();				
				for(PLPersonalInfoDataM personalM : personalVect){								
					String personalID = personalM.getPersonalID();
					int returnRows = 0;					
					returnRows = this.updateTableOrig_Personal_Info(personalM, appRecId);
					if(returnRows == 0){	
						if(OrigUtil.isEmptyString(personalID)){
							personalID = PLORIGEJBService.getGeneratorManager().generateUniqueIDByName(EJBConstant.PERSONAL_ID);
							personalM.setPersonalID(personalID);
						}
						this.insertTableOrig_Personal_Info(personalM, appRecId);
					}				
					if(!OrigUtil.isEmptyString(personalM.getPersonalID())){
						personalIdVect.add(personalID);						
						PLXRulesVerificationResultDataM xrulesVerM = personalM.getXrulesVerification();
						if(null == xrulesVerM){
							xrulesVerM = new PLXRulesVerificationResultDataM();
							personalM.setXrulesVerification(xrulesVerM);
						}
						XRulesRemoteDAOUtilManager xrulesBean = PLORIGEJBService.getXRulesDAOUtilManager();
							xrulesBean.updateSavePLXRulesVerificationResult(xrulesVerM, personalM.getPersonalID());
					}	
				}
				this.deleteTableORIG_PERSONAL_INFO(personalIdVect, appRecId);
			}
			
		}catch(Exception e){
			log.fatal(e.getLocalizedMessage());
			throw new PLOrigApplicationException(e.getMessage());
		}
		
	}

	@Override
	public Vector<PLPersonalInfoDataM> loadOrigPersonalInfo(String appRecId) throws PLOrigApplicationException {
		PreparedStatement ps = null;
		ResultSet rs = null;
		Vector<PLPersonalInfoDataM> personalVect = new Vector<PLPersonalInfoDataM>();
		Connection conn = null;
		try {
			conn = getConnection();
			
			String SQL = getOrigPersonalInfoSQL();			
			log.debug("SQL >> "+SQL);
			
			ps = conn.prepareStatement(SQL);
			rs = null;
			ps.setString(1, appRecId);

			rs = ps.executeQuery();
					
			while(rs.next()){				
				PLPersonalInfoDataM personalM = new PLPersonalInfoDataM();
				
				int index = 1;				
				setModelPersonalInfoM(index, personalM, rs);
				
				LoadSubTable(personalM);				
				personalVect.add(personalM);
			}			
		}catch(Exception e){
			log.fatal(e.getLocalizedMessage());
			throw new PLOrigApplicationException(e.getMessage());
		}finally{
			try{
				closeConnection(conn, rs, ps);
			}catch(Exception e){
				log.fatal(e.getLocalizedMessage());
				throw new PLOrigApplicationException(e.getMessage());
			}
		}		
		return personalVect;		
	}

	@Override
	public PLPersonalInfoDataM loadPersonalMForNCB(String appRecId) throws PLOrigApplicationException {

		PLPersonalInfoDataM personalM = this.loadPersonalM(appRecId);
		if(!OrigUtil.isEmptyObject(personalM) && !OrigUtil.isEmptyString(personalM.getPersonalID())){
			PLOrigNCBDocumentDAO ncbDocDAO = PLORIGDAOFactory.getPLOrigNCBDocumentDAO();
			Vector<PLNCBDocDataM> ncbDocVect = ncbDocDAO.loadNCBDocument(personalM.getPersonalID());
			personalM.setNcbDocVect(ncbDocVect);
		}		
		return personalM;
	}

	@Override
	public Vector<PLPersonalInfoDataM> ImageORIGPersonalInfo(String appRecID)throws PLOrigApplicationException {
		PreparedStatement ps = null;
		ResultSet rs = null;

		Vector<PLPersonalInfoDataM> personalVect = new Vector<PLPersonalInfoDataM>();
		Connection conn = null;
		try{
			conn = getConnection();
			String SQL = getOrigPersonalInfoSQL();			
			log.debug("SQL >> "+SQL);

			ps = conn.prepareStatement(SQL);
			rs = null;
			ps.setString(1, appRecID);

			rs = ps.executeQuery();
			
			PLPersonalInfoDataM personalM = null;
			
			PLOrigNCBDocumentDAO ncbDocDAO = PLORIGDAOFactory.getPLOrigNCBDocumentDAO();
			
			while(rs.next()){				
				personalM = new PLPersonalInfoDataM();
				
				int index = 1;
				setModelPersonalInfoM(index, personalM, rs);
				
				personalM.setNcbDocVect(ncbDocDAO.loadNCBDocument(personalM.getPersonalID()));
				
				personalVect.add(personalM);
			}			
		}catch(Exception e){
			log.fatal(e.getLocalizedMessage());
			throw new PLOrigApplicationException(e.getMessage());
		}finally{
			try{
				closeConnection(conn, rs, ps);
			}catch(Exception e){
				log.fatal(e.getLocalizedMessage());
				throw new PLOrigApplicationException(e.getMessage());
			}
		}		
		return personalVect;
	}
	
}
