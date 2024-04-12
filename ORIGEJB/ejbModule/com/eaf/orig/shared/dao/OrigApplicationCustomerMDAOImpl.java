/*
 * Created on Sep 21, 2007
 * Created by Sankom Sanpunya
 * 
 * Copyright (c) 2007 Avalant Co.,Ltd.
 * 20 North Sathorn Road, 15-16th Floor Bubhajit Bldg., Silom, Bangrak, Bangkok 10500, Thailand
 * All rights reserved.
 *
 * This software is the confidential and prorietary infomation of
 * Avalant Co.,Ltd. ("Confidential Infomation"). You shall not
 * disclose such Confidential Infomation and shall use it only in
 * accordance with the terms of the license agreement you entered into
 * with Avalant Co.,Ltd.
 * 
 */
package com.eaf.orig.shared.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.Vector;

import org.apache.log4j.Logger;

import com.eaf.orig.shared.constant.EJBConstant;
import com.eaf.orig.shared.dao.exceptions.OrigApplicationCustomerMException;
import com.eaf.orig.shared.ejb.ORIGGeneratorManager;
import com.eaf.orig.shared.model.AddressDataM;
import com.eaf.orig.shared.model.BankDataM;
import com.eaf.orig.shared.model.ChangeNameDataM;
import com.eaf.orig.shared.model.CorperatedDataM;
import com.eaf.orig.shared.model.PersonalInfoDataM;
import com.eaf.orig.shared.model.PreScoreDataM;
import com.eaf.orig.shared.service.ORIGEJBService;
import com.eaf.orig.shared.xrules.dao.XRulesVerificationResultDAO;
import com.eaf.xrules.shared.model.XRulesVerificationResultDataM;

/**
 * @author Sankom Sanpunya
 * 
 * Type: OrigApplicationCustomerMDAOImpl
 */
public class OrigApplicationCustomerMDAOImpl extends OrigObjectDAO implements
		OrigApplicationCustomerMDAO {
	private static Logger log = Logger
			.getLogger(OrigApplicationCustomerMDAOImpl.class);

	/**
	 *  
	 */
	public OrigApplicationCustomerMDAOImpl() {
		super();

	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.eaf.orig.share.dao.OrigApplicationCustomerMDAO#createModelOrigApplicationCustomerM(com.eaf.orig.shared.model.PersonalInfoDataM)
	 */
	public void createModelOrigApplicationCustomerM(
			PersonalInfoDataM prmPersonalInfoDataM)
			throws OrigApplicationCustomerMException {
		try {
			createTableORIG_APPLICATION_CUSTOMER(prmPersonalInfoDataM);
			String appRecdId = prmPersonalInfoDataM.getAppRecordID();
			log.debug(">>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>appRecdId" + appRecdId);
			//create address
			Vector vPersonalAddress = prmPersonalInfoDataM.getAddressVect();
			OrigCustomerAddressMDAO origCustomerAddressMDAO = ORIGDAOFactory.getOrigCustomerAddressMDAO();
			if (vPersonalAddress != null) {
				for (int i = 0; i < vPersonalAddress.size(); i++) {
					AddressDataM origCustomerAddressDataM = (AddressDataM) vPersonalAddress
							.get(i);
					origCustomerAddressDataM.setApplicationRecordId(appRecdId);
					origCustomerAddressDataM.setCmpCode(prmPersonalInfoDataM.getCmpCode());
					origCustomerAddressDataM.setPersonalID(prmPersonalInfoDataM.getPersonalID());
					origCustomerAddressMDAO.createModelOrigCustomerAddressM(origCustomerAddressDataM, prmPersonalInfoDataM.getIdNo());
				}
			}
			
			//create finance;
			Vector vFinance = prmPersonalInfoDataM.getFinanceVect();
			OrigCustomerFinanceMDAO origCustomerFinanceMDAO = ORIGDAOFactory
					.getOrigCustomerFinanceMDAO();
			if (vFinance != null) {
				for (int i = 0; i < vFinance.size(); i++) {
					BankDataM  bankDataM = (BankDataM) vFinance.get(i);
					bankDataM.setAppRecodeID(prmPersonalInfoDataM.getAppRecordID());
					bankDataM.setPersonalID(prmPersonalInfoDataM.getIdNo());
					bankDataM.setCmpCode(prmPersonalInfoDataM.getCmpCode());
					origCustomerFinanceMDAO.createModelOrigCustomerFinnanceM(bankDataM);
				}
			}
			
			//create change_name
			Vector vChangeName = prmPersonalInfoDataM.getChangeNameVect();
			OrigCustomerChangeNameMDAO origCustomerChaneNameMDAO = ORIGDAOFactory
					.getOrigCustomerChangeNameMDAO();
			if (vChangeName != null) {
				for (int i = 0; i < vChangeName.size(); i++) {
					ChangeNameDataM changeNameDataM = (ChangeNameDataM) vChangeName.get(i);
					changeNameDataM.setApplicationRecordId(prmPersonalInfoDataM.getAppRecordID());
					changeNameDataM.setIdNo(prmPersonalInfoDataM.getIdNo());
					changeNameDataM.setCmpCode(prmPersonalInfoDataM.getCmpCode());
					origCustomerChaneNameMDAO.createModelOrigCustomerChangeNameM(changeNameDataM);
				}
			}
			
			//create corperated
			Vector vCorperated = prmPersonalInfoDataM.getCorperatedVect();
			OrigCorperatedMDAO origCorperatedMDAO = ORIGDAOFactory
					.getOrigCorperatedMDAO();
			if (vCorperated != null) {
				for (int i = 0; i < vCorperated.size(); i++) {
					CorperatedDataM corperatedDataM = (CorperatedDataM) vCorperated.get(i);
					corperatedDataM.setAppRecordID(prmPersonalInfoDataM.getAppRecordID());
					corperatedDataM.setIdNo(prmPersonalInfoDataM.getIdNo());
					corperatedDataM.setCmpCode(prmPersonalInfoDataM.getCmpCode());
					origCorperatedMDAO.createModelOrigCorperatedM(corperatedDataM);
				}
			}
			//Add Save Verification Result
			//===================================
			XRulesVerificationResultDataM  prmXRuesVerficationResultDataM=prmPersonalInfoDataM.getXrulesVerification();
			if(prmXRuesVerficationResultDataM!=null){
			prmXRuesVerficationResultDataM.setApplicationRecordId(appRecdId);
			prmXRuesVerficationResultDataM.setPersonalID(prmPersonalInfoDataM.getPersonalID());
			log.debug(">>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>appRecdId " + appRecdId + "  getPersonalID " + prmPersonalInfoDataM.getPersonalID());
			//prmXRuesVerficationResultDataM.setCmpCode(prmPersonalInfoDataM.getPersonalID());
			//prmXRuesVerficationResultDataM.setIdNo(prmPersonalInfoDataM.getIdNo());
			//prmXRuesVerficationResultDataM.setUpdateBy(prmPersonalInfoDataM.getUpdateBy());
			XRulesVerificationResultDAO xRuesVerificationDAO=ORIGDAOFactory.getXRulesVerificationResultDAO();
			xRuesVerificationDAO.createModelXRulesVerificationResultM(prmXRuesVerficationResultDataM);
			}
			//=====================
            //create pre Score
			PreScoreDataM prmPreScoredataM=prmPersonalInfoDataM.getPreScoreDataM();
			log.debug(" PreScore is "+prmPreScoredataM);
			if(prmPreScoredataM!=null){
			    prmPreScoredataM.setApplicationRecordId(prmPersonalInfoDataM.getAppRecordID());
			    prmPreScoredataM.setCreateBy(prmPersonalInfoDataM.getCreateBy());
			    prmPreScoredataM.setUpdateBy(prmPersonalInfoDataM.getUpdateBy());
			    //Remove CMPCODE
			    //prmPreScoredataM.setCmpCode(prmPersonalInfoDataM.getCmpCode());
			    
			    prmPreScoredataM.setIdNo(prmPersonalInfoDataM.getIdNo());
			    ORIGDAOFactory.getPreScoreDataMDAO().createModelOrigPreScoreM(prmPreScoredataM);
			}
		} catch (Exception e) {
			log.fatal(e.getLocalizedMessage());
			throw new OrigApplicationCustomerMException(e.getMessage());
		}

	}

	/**
	 * @param prmPersonalInfoDataM
	 */
	private void createTableORIG_APPLICATION_CUSTOMER(
			PersonalInfoDataM prmPersonalInfoDataM)
			throws OrigApplicationCustomerMException {
		PreparedStatement ps = null;
		Connection conn = null;
		try {
			//conn = Get Connection
			conn = getConnection();
			StringBuffer sql = new StringBuffer("");
			/*sql.append("INSERT INTO ORIG_PERSONAL_INFO ");
			sql.append("( APPLICATION_RECORD_ID,PERSONAL_ID,IDNO,PERSONAL_TYPE");
			sql.append(", CREATE_DATE,CREATE_BY,UPDATE_DATE ,UPDATE_BY, CUSTOMER_TYPE");
			sql.append(", MAILING_ADDRESS, PREVIOUS_RECORD, TIME_IN_CURRENT_ADDRESS,CONSENT_FLAG,CONSENT_DATE");
			sql.append(", K_CONSENT_FLAG,K_CONSENT_DATE,COBORROWER_FLAG,DEBT_INCLUDE_FLAG)");
			sql.append(" VALUES(?,?,?,?  ,SYSDATE,?,SYSDATE,?,?, ?,?,?,?,?, ?,?,?,?) ");
			String dSql = String.valueOf(sql);
			log.debug("Sql=" + dSql);
			ps = conn.prepareStatement(dSql);
			ps.setString(1, prmPersonalInfoDataM.getAppRecordID());
			ps.setString(2, prmPersonalInfoDataM.getIdNo());
			ps.setString(3, prmPersonalInfoDataM.getIdNo());
			ps.setString(4, prmPersonalInfoDataM.getPersonalType());
			ps.setString(5, prmPersonalInfoDataM.getCreateBy());
			ps.setString(6, prmPersonalInfoDataM.getUpdateBy());
			ps.setString(7, prmPersonalInfoDataM.getCustomerType());
			ps.setString(8, prmPersonalInfoDataM.getMailingAddress());
			ps.setString(9, prmPersonalInfoDataM.getPreviousRecord());
			ps.setDouble(10, prmPersonalInfoDataM.getTimeInCurrentAddress());
			ps.setString(11,prmPersonalInfoDataM.getConsentFlag());
			ps.setDate(12,this.parseDate(prmPersonalInfoDataM.getConsentDate()));
			ps.setString(13,prmPersonalInfoDataM.getKConsentFlag());
			ps.setDate(14,this.parseDate(prmPersonalInfoDataM.getKConsentDate()));
			ps.setString(15,prmPersonalInfoDataM.getCoborrowerFlag());
			ps.setString(16,prmPersonalInfoDataM.getDebtIncludeFlag());*/
			sql.append("INSERT INTO ORIG_PERSONAL_INFO (");
			sql.append("  PERSONAL_ID,APPLICATION_RECORD_ID,IDNO,PERSONAL_TYPE,CUSTOMER_TYPE ");
			sql.append(" ,MAILING_ADDRESS,PREVIOUS_RECORD,TIME_IN_CURRENT_ADDRESS,K_CONSENT_FLAG,K_CONSENT_DATE");
			sql.append(" ,CONSENT_FLAG,CONSENT_DATE,COBORROWER_FLAG,DEBT_INCLUDE_FLAG,CUSTOMER_NO");
			sql.append(" ,TH_TITLE_CODE,TH_FIRST_NAME,TH_LAST_NAME,TH_MID_NAME,EN_TITLE_CODE");
			sql.append(" ,EN_FIRST_NAME,EN_LAST_NAME,EN_MID_NAME,GENDER,BIRTH_DATE");
			sql.append(" ,RACE,NATIONALITY,MARRIED,TAX_ID,CID_TYPE");
			sql.append(" ,CID_ISSUE_DATE,CID_EXP_DATE,CID_ISSUE_BY,OCCUPATION,COMPANY");
			sql.append(" ,BUSINESS_TYPE,POSITION_CODE,POSITION_DESC,DEPARTMENT,SALARY");
			sql.append(" ,TOT_WORK_YEAR,TOT_WORK_MONTH,OTHER_INCOME,SORCE_OF_INCOME,MCID_TYPE");
			sql.append(" ,MCID_NO,M_TITLE_CODE,M_TH_FIRST_NAME,M_TH_MID_NAME,M_TH_LAST_NAME");
			sql.append(" ,M_ETITLE_CODE,M_EN_FIRST_NAME,M_EN_MID_NAME,M_EN_LAST_NAME,M_BIRTH_DATE");
			sql.append(" ,M_GENDER,M_OCCUPATION,M_COMPANY,M_POSITION,M_DEPARTMENT");
			sql.append(" ,M_INCOME,BUSINESS_SIZE,DEGREE,LANDOWNER,BUILDING_TYPE");
			sql.append(" ,CREATE_DATE,CREATE_BY,UPDATE_DATE,UPDATE_BY,HOUSE_REGISTER_STATUS,CUSTSEGM");
			sql.append(") VALUES (?,?,?,?,?  ,?,?,?,?,?  ,?,?,?,?,?  ,?,?,?,?,? "
							+ " ,?,?,?,?,?  ,?,?,?,?,?  ,?,?,?,?,?  ,?,?,?,?,?  "
							+ " ,?,?,?,?,?  ,?,?,?,?,?  ,?,?,?,?,?  ,?,?,?,?,?"
							+ " ,?,?,?,?,?  ,SYSDATE,?,SYSDATE,?,?,?) ");
			
			String dSql = String.valueOf(sql);
			ps = conn.prepareStatement(dSql);
			logger.debug("createTableHPMSHP00M sql=" + dSql);
			logger.debug("prmPersonalInfoDataM.getIdNo() " + prmPersonalInfoDataM.getIdNo());
			logger.debug("prmPersonalInfoDataM.getAppRecordID() " + prmPersonalInfoDataM.getAppRecordID());
			logger.debug("prmPersonalInfoDataM.get IDNO() " + prmPersonalInfoDataM.getIdNo());
			logger.debug("prmPersonalInfoDataM.getPersonal() " + prmPersonalInfoDataM.getPersonalType());
			logger.debug("prmPersonalInfoDataM.getCustomer() " + prmPersonalInfoDataM.getPersonalType());
			ps.setString(1, prmPersonalInfoDataM.getPersonalID());
			ps.setString(2, prmPersonalInfoDataM.getAppRecordID());
			ps.setString(3, prmPersonalInfoDataM.getIdNo());
			ps.setString(4, prmPersonalInfoDataM.getPersonalType());
			ps.setString(5, prmPersonalInfoDataM.getCustomerType());
			
			ps.setString(6, prmPersonalInfoDataM.getMailingAddress());
			ps.setString(7, prmPersonalInfoDataM.getPreviousRecord());
			ps.setString(8, Double.toString(prmPersonalInfoDataM.getTimeInCurrentAddress()));
			ps.setString(9, prmPersonalInfoDataM.getKConsentFlag());
			ps.setDate(10, this.parseDate(prmPersonalInfoDataM.getKConsentDate()));
			
			ps.setString(11, prmPersonalInfoDataM.getConsentFlag());
			ps.setDate(12, this.parseDate(prmPersonalInfoDataM.getConsentDate()));
			ps.setString(13, prmPersonalInfoDataM.getCoborrowerFlag());
			ps.setString(14, prmPersonalInfoDataM.getDebtIncludeFlag());
			ps.setString(15, prmPersonalInfoDataM.getCustomerNO());

			ps.setString(16, prmPersonalInfoDataM.getThaiTitleName());
			ps.setString(17, prmPersonalInfoDataM.getThaiFirstName());
			ps.setString(18, prmPersonalInfoDataM.getThaiLastName());
			ps.setString(19, prmPersonalInfoDataM.getThaiMidName());
			ps.setString(20, prmPersonalInfoDataM.getEngTitleName());

			ps.setString(21, prmPersonalInfoDataM.getEngFirstName());
			ps.setString(22, prmPersonalInfoDataM.getEngLastName());
			ps.setString(23, prmPersonalInfoDataM.getEngMidName());
			ps.setString(24, prmPersonalInfoDataM.getGender());
			ps.setDate(25, this.parseDate(prmPersonalInfoDataM.getBirthDate()));

			ps.setString(26, prmPersonalInfoDataM.getRace());
			ps.setString(27, prmPersonalInfoDataM.getNationality());
			ps.setString(28, prmPersonalInfoDataM.getMaritalStatus());
			ps.setString(29, prmPersonalInfoDataM.getTaxID());
			ps.setString(30, prmPersonalInfoDataM.getCardIdentity());

			ps.setDate(31, this.parseDate(prmPersonalInfoDataM.getIssueDate()));
			ps.setDate(32, this.parseDate(prmPersonalInfoDataM.getExpiryDate()));
			ps.setString(33, prmPersonalInfoDataM.getIssueBy());
			ps.setString(34, prmPersonalInfoDataM.getOccupation());
			ps.setString(35, prmPersonalInfoDataM.getCompanyName());
			
			ps.setString(36, prmPersonalInfoDataM.getBusinessType());
			ps.setString(37, prmPersonalInfoDataM.getPosition());
			ps.setString(38, prmPersonalInfoDataM.getPositionDesc());
			ps.setString(39, prmPersonalInfoDataM.getDepartment());
			ps.setBigDecimal(40, prmPersonalInfoDataM.getSalary());
			
			ps.setInt(41, prmPersonalInfoDataM.getYearOfWork());
			ps.setInt(42, prmPersonalInfoDataM.getMonthOfWork());
			//ps.setBigDecimal(33, prmPersonalInfoDataM.getOtherIncome());
			ps.setBigDecimal(43, prmPersonalInfoDataM.getOtherIncome());
			ps.setString(44, prmPersonalInfoDataM.getSourceOfOtherIncome());
			ps.setString(45, prmPersonalInfoDataM.getMCardType());
			
			ps.setString(46, prmPersonalInfoDataM.getMIDNo());
			ps.setString(47, prmPersonalInfoDataM.getMThaiTitleName());
			ps.setString(48, prmPersonalInfoDataM.getMThaiFirstName());
			ps.setString(49, prmPersonalInfoDataM.getMThaiMidName());
			ps.setString(50, prmPersonalInfoDataM.getMThaiLastName());

			ps.setString(51, prmPersonalInfoDataM.getMEngTitleName());
			ps.setString(52, prmPersonalInfoDataM.getMEngFirstName());
			ps.setString(53, prmPersonalInfoDataM.getMEngMidName());
			ps.setString(54, prmPersonalInfoDataM.getMEngLastName());
			ps.setDate(55, this.parseDate(prmPersonalInfoDataM.getMBirthDate()));

			ps.setString(56, prmPersonalInfoDataM.getMGender());
			ps.setString(57, prmPersonalInfoDataM.getMOccupation());
			ps.setString(58, prmPersonalInfoDataM.getMCompany());
			ps.setString(59, prmPersonalInfoDataM.getMPosition());
			ps.setString(60, prmPersonalInfoDataM.getMDepartment());

			ps.setBigDecimal(61, prmPersonalInfoDataM.getMIncome());
			ps.setString(62, prmPersonalInfoDataM.getBusinessSize());
			ps.setString(63, prmPersonalInfoDataM.getQualification());
			ps.setString(64, prmPersonalInfoDataM.getLandOwnerShip());
			ps.setString(65, prmPersonalInfoDataM.getBuildingType());

			//ps.setDate(56, this.parseDate(prmPersonalInfoDataM.getCreateDate()));
			ps.setString(66, prmPersonalInfoDataM.getCreateBy());
			//ps.setDate(67, this.parseDate(prmPersonalInfoDataM.getUpdateDate()));
			ps.setString(67, prmPersonalInfoDataM.getCreateBy());
			ps.setString(68, prmPersonalInfoDataM.getHouseRegisStatus());
			ps.setString(69, prmPersonalInfoDataM.getCustomerSegment());
			ps.executeUpdate();
		} catch (Exception e) {
			log.fatal(e.getLocalizedMessage());
			throw new OrigApplicationCustomerMException(e.getMessage());
		} finally {
			try {
				closeConnection(conn, ps);
			} catch (Exception e) {
				log.fatal(e.getLocalizedMessage());
			}
		}

	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.eaf.orig.share.dao.OrigApplicationCustomerMDAO#deleteModelOrigApplicationCustomerM(com.eaf.orig.shared.model.PersonalInfoDataM)
	 */
	public void deleteModelOrigApplicationCustomerM(
			PersonalInfoDataM prmPersonalInfoDataM)
			throws OrigApplicationCustomerMException {
		try {
			OrigCustomerAddressMDAO origCustomerAddressDAO = ORIGDAOFactory
					.getOrigCustomerAddressMDAO();
			Vector vOrigCutomerAddress = prmPersonalInfoDataM.getAddressVect();
			if (vOrigCutomerAddress != null) {
				for (int i = 0; i < vOrigCutomerAddress.size(); i++) {
					AddressDataM addressDataM = (AddressDataM) vOrigCutomerAddress
							.get(i);
					origCustomerAddressDAO
							.deleteModelOrigCustomerAddressM(addressDataM);
				}
			}
            //delete Verification Result
			//===================================
			XRulesVerificationResultDataM  prmXRuesVerficationResultDataM=prmPersonalInfoDataM.getXrulesVerification();
			XRulesVerificationResultDAO xRuesVerificationDAO=ORIGDAOFactory.getXRulesVerificationResultDAO();
			xRuesVerificationDAO.deleteModelXRulesVerificationResultM(prmXRuesVerficationResultDataM);
			//=====================
			ORIGDAOFactory.getPreScoreDataMDAO().deleteModelOrigPreScoreM(prmPersonalInfoDataM.getPreScoreDataM());
			deleteTableORIG_APPLICATION_CUSTOMER(prmPersonalInfoDataM);
		} catch (Exception e) {
			log.fatal(e.getLocalizedMessage());
			throw new OrigApplicationCustomerMException(e.getMessage());
		}

	}

	/**
	 * @param prmPersonalInfoDataM
	 */
	private void deleteTableORIG_APPLICATION_CUSTOMER(
			PersonalInfoDataM prmPersonalInfoDataM)
			throws OrigApplicationCustomerMException {
		PreparedStatement ps = null;
		Connection conn = null;
		try {
			//conn = Get Connection
			conn = getConnection();
			StringBuffer sql = new StringBuffer("");
			sql.append("DELETE ORIG_APPLICATION_CUSTOMER ");
			sql
					.append(" WHERE APPLICATION_RECORD_ID = ? AND CMPCDE=? AND IDNO=?");
			String dSql = String.valueOf(sql);
			log.debug("Sql=" + dSql);
			ps = conn.prepareStatement(dSql);
			ps.setString(1, prmPersonalInfoDataM.getAppRecordID());
			ps.setString(2, prmPersonalInfoDataM.getCmpCode());
			ps.setString(3, prmPersonalInfoDataM.getIdNo());
			ps.executeUpdate();

		} catch (Exception e) {
			log.fatal(e.getLocalizedMessage());
			throw new OrigApplicationCustomerMException(e.getMessage());
		} finally {
			try {
				closeConnection(conn, ps);
			} catch (Exception e) {
				log.fatal(e.getLocalizedMessage());
				throw new OrigApplicationCustomerMException(e.getMessage());
			}
		}

	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.eaf.orig.share.dao.OrigApplicationCustomerMDAO#loadModelOrigApplicationCustomerM(java.lang.String)
	 */
	public Vector loadModelOrigApplicationCustomerM(String applicationRecordId, String providerUrlEXT, String jndiEXT)
			throws OrigApplicationCustomerMException {
		try {
			Vector vResult = selectTableORIG_APPLICATION_CUSTOMER(applicationRecordId, providerUrlEXT, jndiEXT);
			OrigCustomerAddressMDAO origCustomerAddressDAO = ORIGDAOFactory.getOrigCustomerAddressMDAO();
			OrigCustomerFinanceMDAO customerFinanceMDAO = ORIGDAOFactory.getOrigCustomerFinanceMDAO();
			OrigCustomerChangeNameMDAO changeNameMDAO = ORIGDAOFactory.getOrigCustomerChangeNameMDAO();
			OrigCorperatedMDAO origCorperatedMDAO = ORIGDAOFactory.getOrigCorperatedMDAO();
			if (vResult != null) {
				PersonalInfoDataM prmPersonalInfoDataM;
				for (int i = 0; i < vResult.size(); i++) {
					prmPersonalInfoDataM = (PersonalInfoDataM) vResult.get(i);
					
					//Load Address
					Vector vOrigAddress = origCustomerAddressDAO.loadModelOrigCustomerAddressM(applicationRecordId,prmPersonalInfoDataM.getPersonalID(), providerUrlEXT, jndiEXT);
					prmPersonalInfoDataM.setAddressVect(vOrigAddress);
					
					//Load Finance(Bank)
					Vector vOrigFinance = customerFinanceMDAO.loadModelOrigCustomerFinnanceM(applicationRecordId, prmPersonalInfoDataM.getPersonalID(), providerUrlEXT, jndiEXT);
					prmPersonalInfoDataM.setFinanceVect(vOrigFinance);
					
					//Load Change Name
					Vector vOrigChangeName = changeNameMDAO.loadModelOrigCustomerChangeNameM(applicationRecordId, prmPersonalInfoDataM.getIdNo(), providerUrlEXT, jndiEXT);
					prmPersonalInfoDataM.setChangeNameVect(vOrigChangeName);
					
					//Load Corperated 
					Vector vOrigCorperated = origCorperatedMDAO.loadModelOrigCorperatedM(prmPersonalInfoDataM.getCmpCode(), prmPersonalInfoDataM.getIdNo());
					prmPersonalInfoDataM.setCorperatedVect(vOrigCorperated);
					//load Verification Result
					//===================================
					//XRulesVerificationResultDataM  xRuesVerficationResultDataM=prmPersonalInfoDataM.getXrulesVerification();
					XRulesVerificationResultDAO xRuesVerificationDAO=ORIGDAOFactory.getXRulesVerificationResultDAO();
					log.debug(">>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>....Test");
					XRulesVerificationResultDataM  prmXRuesVerficationResultDataM=  xRuesVerificationDAO.loadModelXRulesVerificationResultM(prmPersonalInfoDataM.getPersonalID());					
					prmPersonalInfoDataM.setXrulesVerification(prmXRuesVerficationResultDataM);
					//=====================
//					load PreScore
					OrigPreScoreMDAO  preScoreDAO=ORIGDAOFactory.getPreScoreDataMDAO();
					prmPersonalInfoDataM.setPreScoreDataM(preScoreDAO.loadModelOrigPreScoreM(applicationRecordId,prmPersonalInfoDataM.getCmpCode(),prmPersonalInfoDataM.getIdNo()));
				}
			}
			return vResult;
		} catch (Exception e) {
			log.fatal(e.getLocalizedMessage());
			throw new OrigApplicationCustomerMException(e.getMessage());
		}
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.eaf.orig.share.dao.OrigApplicationCustomerMDAO#loadModelOrigApplicationCustomerM(java.lang.String)
	 */
	public PersonalInfoDataM loadModelOrigApplicationCustomerM(PersonalInfoDataM prmPersonalInfoDataM)
			throws OrigApplicationCustomerMException {
		
		PersonalInfoDataM personalInfoDataM = selectTableORIG_APPLICATION_CUSTOMER(prmPersonalInfoDataM);
		OrigCustomerAddressMDAO origCustomerAddressDAO = ORIGDAOFactory.getOrigCustomerAddressMDAO();
		OrigCustomerFinanceMDAO customerFinanceMDAO = ORIGDAOFactory.getOrigCustomerFinanceMDAO();
		OrigCustomerChangeNameMDAO changeNameMDAO = ORIGDAOFactory.getOrigCustomerChangeNameMDAO();
		//OrigCorperatedMDAO origCorperatedMDAO = ORIGDAOFactory.getOrigCorperatedMDAO();
		try {
			if (personalInfoDataM != null) {
				//Load Address
				Vector vOrigAddress = origCustomerAddressDAO.loadModelOrigCustomerAddressMByPersonalID(personalInfoDataM.getPersonalID());
				personalInfoDataM.setAddressVect(vOrigAddress);
				personalInfoDataM.setAddressTmpVect(new Vector());
				
				//Load Finance(Bank)
				Vector vOrigFinance = customerFinanceMDAO.loadModelOrigCustomerFinnanceM("", personalInfoDataM.getPersonalID(), "", "");
				personalInfoDataM.setFinanceVect(vOrigFinance);
				
				//Load Change Name
				Vector vOrigChangeName = changeNameMDAO.loadModelOrigCustomerChangeNameM("", personalInfoDataM.getIdNo(), "", "");
				personalInfoDataM.setChangeNameVect(vOrigChangeName);
			}
		} catch(Exception e) {
			logger.fatal(e.getLocalizedMessage());
			throw new OrigApplicationCustomerMException(e.getMessage());
		}
		return personalInfoDataM;
	}
	
	/**
	 * @param personalID
	 * @return
	 */
	private PersonalInfoDataM selectTableORIG_APPLICATION_CUSTOMER(PersonalInfoDataM prmPersonalInfoDataM)
			throws OrigApplicationCustomerMException {
		PreparedStatement ps = null;
		ResultSet rs = null;
		Connection conn = null;
		try {
			//conn = Get Connection
			conn = getConnection();
			StringBuffer sql = new StringBuffer("");
			/*sql
					.append("SELECT APPLICATION_RECORD_ID,CMPCDE,IDNO,PERSONAL_TYPE, CUSTOMER_TYPE, MAILING_ADDRESS, PREVIOUS_RECORD, TIME_IN_CURRENT_ADDRESS,CONSENT_FLAG,CONSENT_DATE,K_CONSENT_FLAG,K_CONSENT_DATE ,COBORROWER_FLAG,DEBT_INCLUDE_FLAG ");
			sql
					.append("FROM ORIG_APPLICATION_CUSTOMER WHERE APPLICATION_RECORD_ID = ? ");
			*/
			sql.append(" SELECT APPLICATION_RECORD_ID,PERSONAL_ID,IDNO,PERSONAL_TYPE,CUSTOMER_TYPE ");
			sql.append(" ,MAILING_ADDRESS,PREVIOUS_RECORD,TIME_IN_CURRENT_ADDRESS,K_CONSENT_FLAG,K_CONSENT_DATE");
			sql.append(" ,CONSENT_FLAG,CONSENT_DATE,COBORROWER_FLAG,DEBT_INCLUDE_FLAG,CUSTOMER_NO");
			sql.append(" ,TH_TITLE_CODE,TH_FIRST_NAME,TH_LAST_NAME,TH_MID_NAME,EN_TITLE_CODE");
			sql.append(" ,EN_FIRST_NAME,EN_LAST_NAME,EN_MID_NAME,GENDER,BIRTH_DATE");
			sql.append(" ,RACE,NATIONALITY,MARRIED,TAX_ID,CID_TYPE");
			sql.append(" ,CID_ISSUE_DATE,CID_EXP_DATE,CID_ISSUE_BY,OCCUPATION,COMPANY");
			sql.append(" ,BUSINESS_TYPE,POSITION_CODE,POSITION_DESC,DEPARTMENT,SALARY");
			sql.append(" ,TOT_WORK_YEAR,TOT_WORK_MONTH,OTHER_INCOME,SORCE_OF_INCOME,MCID_TYPE");
			sql.append(" ,MCID_NO,M_TITLE_CODE,M_TH_FIRST_NAME,M_TH_MID_NAME,M_TH_LAST_NAME");
			sql.append(" ,M_ETITLE_CODE,M_EN_FIRST_NAME,M_EN_MID_NAME,M_EN_LAST_NAME,M_BIRTH_DATE");
			sql.append(" ,M_GENDER,M_OCCUPATION,M_COMPANY,M_POSITION,M_DEPARTMENT");
			sql.append(" ,M_INCOME,BUSINESS_SIZE,DEGREE,LANDOWNER,BUILDING_TYPE,HOUSE_REGISTER_STATUS, CUSTSEGM ");
			sql.append(" FROM ORIG_PERSONAL_INFO WHERE IDNO = ? ORDER BY PERSONAL_ID DESC");
			String dSql = String.valueOf(sql);
			log.debug("Sql=" + dSql);
			ps = conn.prepareStatement(dSql);
			rs = null;
			ps.setString(1, prmPersonalInfoDataM.getIdNo());

			rs = ps.executeQuery();
			//Vector vt = new Vector();
			//int seq = 0;
			//PersonalInfoDataM prmPersonalInfoDataM = new PersonalInfoDataM();
			if (rs.next()) {
				
				prmPersonalInfoDataM.setAppRecordID(rs.getString(1));
				prmPersonalInfoDataM.setPersonalID(rs.getString(2));
				prmPersonalInfoDataM.setIdNo(rs.getString(3));
				prmPersonalInfoDataM.setPersonalType(rs.getString(4));
				prmPersonalInfoDataM.setCustomerType(rs.getString(5));
				
				prmPersonalInfoDataM.setMailingAddress(rs.getString(6));
				prmPersonalInfoDataM.setPreviousRecord(rs.getString(7));
				prmPersonalInfoDataM.setTimeInCurrentAddress(rs.getDouble(8));
				prmPersonalInfoDataM.setKConsentFlag(rs.getString(9));
				prmPersonalInfoDataM.setKConsentDate(this.parseDate(rs.getDate(10)));
				
				prmPersonalInfoDataM.setConsentFlag(rs.getString(11));
				prmPersonalInfoDataM.setConsentDate(this.parseDate(rs.getDate(12)));
				prmPersonalInfoDataM.setCoborrowerFlag(rs.getString(13));
				prmPersonalInfoDataM.setDebtIncludeFlag(rs.getString(14));
				prmPersonalInfoDataM.setCustomerNO(rs.getString(15));

				prmPersonalInfoDataM.setThaiTitleName(rs.getString(16));
				prmPersonalInfoDataM.setThaiFirstName(rs.getString(17));
				prmPersonalInfoDataM.setThaiLastName(rs.getString(18));
				prmPersonalInfoDataM.setThaiMidName(rs.getString(19));
				prmPersonalInfoDataM.setEngTitleName(rs.getString(20));

				prmPersonalInfoDataM.setEngFirstName(rs.getString(21));
				prmPersonalInfoDataM.setEngLastName(rs.getString(22));
				prmPersonalInfoDataM.setEngMidName(rs.getString(23));
				prmPersonalInfoDataM.setGender(rs.getString(24));
				prmPersonalInfoDataM.setBirthDate(this.parseDate(rs.getDate(25)));

				prmPersonalInfoDataM.setRace(rs.getString(26));
				prmPersonalInfoDataM.setNationality(rs.getString(27));
				prmPersonalInfoDataM.setMaritalStatus(rs.getString(28));
				prmPersonalInfoDataM.setTaxID(rs.getString(29));
				prmPersonalInfoDataM.setCardIdentity(rs.getString(30));

				prmPersonalInfoDataM.setIssueDate(this.parseDate(rs.getDate(31)));
				prmPersonalInfoDataM.setExpiryDate(this.parseDate(rs.getDate(32)));
				prmPersonalInfoDataM.setIssueBy(rs.getString(33));
				prmPersonalInfoDataM.setOccupation(rs.getString(34));
				prmPersonalInfoDataM.setCompanyName(rs.getString(35));
				
				prmPersonalInfoDataM.setBusinessSize(rs.getString(36));
				prmPersonalInfoDataM.setPosition(rs.getString(37));
				prmPersonalInfoDataM.setPositionDesc(rs.getString(37));
				prmPersonalInfoDataM.setDepartment(rs.getString(38));
				prmPersonalInfoDataM.setSalary(rs.getBigDecimal(40));
				
				prmPersonalInfoDataM.setYearOfWork(rs.getInt(41));
				prmPersonalInfoDataM.setMonthOfWork(rs.getInt(42));
				prmPersonalInfoDataM.setOtherIncome(rs.getBigDecimal(43));
				prmPersonalInfoDataM.setSourceOfOtherIncome(rs.getString(44));
				prmPersonalInfoDataM.setMCardType(rs.getString(45));
				
				prmPersonalInfoDataM.setMIDNo(rs.getString(46));
				prmPersonalInfoDataM.setMThaiTitleName(rs.getString(47));
				prmPersonalInfoDataM.setMThaiFirstName(rs.getString(48));
				prmPersonalInfoDataM.setMThaiMidName(rs.getString(49));
				prmPersonalInfoDataM.setMThaiLastName(rs.getString(50));


				prmPersonalInfoDataM.setMEngTitleName(rs.getString(51));
				prmPersonalInfoDataM.setMEngFirstName(rs.getString(52));
				prmPersonalInfoDataM.setMEngMidName(rs.getString(53));
				prmPersonalInfoDataM.setMEngLastName(rs.getString(54));
				prmPersonalInfoDataM.setMBirthDate(this.parseDate(rs.getDate(55)));
				
				prmPersonalInfoDataM.setMGender(rs.getString(56));
				prmPersonalInfoDataM.setMOccupation(rs.getString(57));
				prmPersonalInfoDataM.setMCompany(rs.getString(58));
				prmPersonalInfoDataM.setMPosition(rs.getString(59));
				prmPersonalInfoDataM.setMDepartment(rs.getString(60));
				
				prmPersonalInfoDataM.setMIncome(rs.getBigDecimal(61));
				prmPersonalInfoDataM.setBusinessSize(rs.getString(62));
				prmPersonalInfoDataM.setQualification(rs.getString(63));
				prmPersonalInfoDataM.setLandOwnerShip(rs.getString(64));
				prmPersonalInfoDataM.setBuildingType(rs.getString(65));
				prmPersonalInfoDataM.setHouseRegisStatus(rs.getString(66));
				prmPersonalInfoDataM.setCustomerSegment(rs.getString(67));
				log.debug("prmPersonalInfoDataM.getPreviousRecord() = "+prmPersonalInfoDataM.getPreviousRecord());
				
			}
			return prmPersonalInfoDataM;
		} catch (Exception e) {
			log.fatal(e.getLocalizedMessage());
			throw new OrigApplicationCustomerMException(e.getMessage());
		} finally {
			try {
				closeConnection(conn, rs, ps);
			} catch (Exception e) {
				log.fatal(e.getLocalizedMessage());
			}
		}
	}
	
	/**
	 * @param applicationRecordId
	 * @return
	 */
	private Vector selectTableORIG_APPLICATION_CUSTOMER(
			String applicationRecordId, String providerUrlEXT, String jndiEXT)
			throws OrigApplicationCustomerMException {
		PreparedStatement ps = null;
		ResultSet rs = null;
		Connection conn = null;
		try {
			//conn = Get Connection
			conn = getConnection();
			StringBuffer sql = new StringBuffer("");
			/*sql
					.append("SELECT APPLICATION_RECORD_ID,CMPCDE,IDNO,PERSONAL_TYPE, CUSTOMER_TYPE, MAILING_ADDRESS, PREVIOUS_RECORD, TIME_IN_CURRENT_ADDRESS,CONSENT_FLAG,CONSENT_DATE,K_CONSENT_FLAG,K_CONSENT_DATE ,COBORROWER_FLAG,DEBT_INCLUDE_FLAG ");
			sql
					.append("FROM ORIG_APPLICATION_CUSTOMER WHERE APPLICATION_RECORD_ID = ? ");
			*/
			sql.append(" SELECT APPLICATION_RECORD_ID,PERSONAL_ID,IDNO,PERSONAL_TYPE,CUSTOMER_TYPE ");
			sql.append(" ,MAILING_ADDRESS,PREVIOUS_RECORD,TIME_IN_CURRENT_ADDRESS,K_CONSENT_FLAG,K_CONSENT_DATE");
			sql.append(" ,CONSENT_FLAG,CONSENT_DATE,COBORROWER_FLAG,DEBT_INCLUDE_FLAG,CUSTOMER_NO");
			sql.append(" ,TH_TITLE_CODE,TH_FIRST_NAME,TH_LAST_NAME,TH_MID_NAME,EN_TITLE_CODE");
			sql.append(" ,EN_FIRST_NAME,EN_LAST_NAME,EN_MID_NAME,GENDER,BIRTH_DATE");
			sql.append(" ,RACE,NATIONALITY,MARRIED,TAX_ID,CID_TYPE");
			sql.append(" ,CID_ISSUE_DATE,CID_EXP_DATE,CID_ISSUE_BY,OCCUPATION,COMPANY");
			sql.append(" ,BUSINESS_TYPE,POSITION_CODE,POSITION_DESC,DEPARTMENT,SALARY");
			sql.append(" ,TOT_WORK_YEAR,TOT_WORK_MONTH,OTHER_INCOME,SORCE_OF_INCOME,MCID_TYPE");
			sql.append(" ,MCID_NO,M_TITLE_CODE,M_TH_FIRST_NAME,M_TH_MID_NAME,M_TH_LAST_NAME");
			sql.append(" ,M_ETITLE_CODE,M_EN_FIRST_NAME,M_EN_MID_NAME,M_EN_LAST_NAME,M_BIRTH_DATE");
			sql.append(" ,M_GENDER,M_OCCUPATION,M_COMPANY,M_POSITION,M_DEPARTMENT");
			sql.append(" ,M_INCOME,BUSINESS_SIZE,DEGREE,LANDOWNER,BUILDING_TYPE,HOUSE_REGISTER_STATUS, CUSTSEGM ");
			sql.append(" FROM ORIG_PERSONAL_INFO WHERE APPLICATION_RECORD_ID = ? ");
			String dSql = String.valueOf(sql);
			log.debug("Sql=" + dSql);
			ps = conn.prepareStatement(dSql);
			rs = null;
			ps.setString(1, applicationRecordId);

			rs = ps.executeQuery();
			Vector vt = new Vector();
//			int seq = 0;
			while (rs.next()) {
				PersonalInfoDataM prmPersonalInfoDataM = new PersonalInfoDataM();
				/*prmPersonalInfoDataM.setAppRecordID(rs.getString(1));
				//prmPersonalInfoDataM.setCmpCode(rs.getString(2));
				prmPersonalInfoDataM.setPersonalID(rs.getString(2));
				prmPersonalInfoDataM.setIdNo(rs.getString(3));
				prmPersonalInfoDataM.setPersonalType(rs.getString(4));
				prmPersonalInfoDataM.setCustomerType(rs.getString(5));
				
				if(OrigConstant.PERSONAL_TYPE_GUARANTOR.equals(prmPersonalInfoDataM.getPersonalType()) || OrigConstant.PERSONAL_TYPE_SUP_CARD.equals(prmPersonalInfoDataM.getPersonalType())){
					prmPersonalInfoDataM.setPersonalSeq(++seq);
				}
				prmPersonalInfoDataM.setMailingAddress(rs.getString(6));
				//prmPersonalInfoDataM.setPreviousRecord(rs.getString(7));//recive form EXT
				prmPersonalInfoDataM.setTimeInCurrentAddress(rs.getDouble(8));
				prmPersonalInfoDataM.setConsentFlag(rs.getString(9));
				prmPersonalInfoDataM.setConsentDate(rs.getDate(10));
				prmPersonalInfoDataM.setKConsentFlag(rs.getString(11));
				prmPersonalInfoDataM.setKConsentDate(rs.getDate(12));
				prmPersonalInfoDataM.setCoborrowerFlag(rs.getString(13));
				prmPersonalInfoDataM.setDebtIncludeFlag(rs.getString(14));
				//Load in EXT
				ApplicationEXTManager applicationEXTManager = ORIGEJBService.getApplicationEXTManager(providerUrlEXT, jndiEXT);
				prmPersonalInfoDataM = applicationEXTManager.loadPersonal(prmPersonalInfoDataM);*/
				prmPersonalInfoDataM.setAppRecordID(rs.getString(1));
				prmPersonalInfoDataM.setPersonalID(rs.getString(2));
				prmPersonalInfoDataM.setIdNo(rs.getString(3));
				prmPersonalInfoDataM.setPersonalType(rs.getString(4));
				prmPersonalInfoDataM.setCustomerType(rs.getString(5));
				
				prmPersonalInfoDataM.setMailingAddress(rs.getString(6));
				prmPersonalInfoDataM.setPreviousRecord(rs.getString(7));
				prmPersonalInfoDataM.setTimeInCurrentAddress(rs.getDouble(8));
				prmPersonalInfoDataM.setKConsentFlag(rs.getString(9));
				prmPersonalInfoDataM.setKConsentDate(this.parseDate(rs.getDate(10)));
				
				prmPersonalInfoDataM.setConsentFlag(rs.getString(11));
				prmPersonalInfoDataM.setConsentDate(this.parseDate(rs.getDate(12)));
				prmPersonalInfoDataM.setCoborrowerFlag(rs.getString(13));
				prmPersonalInfoDataM.setDebtIncludeFlag(rs.getString(14));
				prmPersonalInfoDataM.setCustomerNO(rs.getString(15));

				prmPersonalInfoDataM.setThaiTitleName(rs.getString(16));
				prmPersonalInfoDataM.setThaiFirstName(rs.getString(17));
				prmPersonalInfoDataM.setThaiLastName(rs.getString(18));
				prmPersonalInfoDataM.setThaiMidName(rs.getString(19));
				prmPersonalInfoDataM.setEngTitleName(rs.getString(20));

				prmPersonalInfoDataM.setEngFirstName(rs.getString(21));
				prmPersonalInfoDataM.setEngLastName(rs.getString(22));
				prmPersonalInfoDataM.setEngMidName(rs.getString(23));
				prmPersonalInfoDataM.setGender(rs.getString(24));
				prmPersonalInfoDataM.setBirthDate(this.parseDate(rs.getDate(25)));

				prmPersonalInfoDataM.setRace(rs.getString(26));
				prmPersonalInfoDataM.setNationality(rs.getString(27));
				prmPersonalInfoDataM.setMaritalStatus(rs.getString(28));
				prmPersonalInfoDataM.setTaxID(rs.getString(29));
				prmPersonalInfoDataM.setCardIdentity(rs.getString(30));

				prmPersonalInfoDataM.setIssueDate(this.parseDate(rs.getDate(31)));
				prmPersonalInfoDataM.setExpiryDate(this.parseDate(rs.getDate(32)));
				prmPersonalInfoDataM.setIssueBy(rs.getString(33));
				prmPersonalInfoDataM.setOccupation(rs.getString(34));
				prmPersonalInfoDataM.setCompanyName(rs.getString(35));
				
				prmPersonalInfoDataM.setBusinessType(rs.getString(36));
				prmPersonalInfoDataM.setPosition(rs.getString(37));
				prmPersonalInfoDataM.setPositionDesc(rs.getString(38));
				prmPersonalInfoDataM.setDepartment(rs.getString(38));
				prmPersonalInfoDataM.setSalary(rs.getBigDecimal(40));
				
				prmPersonalInfoDataM.setYearOfWork(rs.getInt(41));
				prmPersonalInfoDataM.setMonthOfWork(rs.getInt(42));
				prmPersonalInfoDataM.setOtherIncome(rs.getBigDecimal(43));
				prmPersonalInfoDataM.setSourceOfOtherIncome(rs.getString(44));
				prmPersonalInfoDataM.setMCardType(rs.getString(45));
				
				prmPersonalInfoDataM.setMIDNo(rs.getString(46));
				prmPersonalInfoDataM.setMThaiTitleName(rs.getString(47));
				prmPersonalInfoDataM.setMThaiFirstName(rs.getString(48));
				prmPersonalInfoDataM.setMThaiMidName(rs.getString(49));
				prmPersonalInfoDataM.setMThaiLastName(rs.getString(50));


				prmPersonalInfoDataM.setMEngTitleName(rs.getString(51));
				prmPersonalInfoDataM.setMEngFirstName(rs.getString(52));
				prmPersonalInfoDataM.setMEngMidName(rs.getString(53));
				prmPersonalInfoDataM.setMEngLastName(rs.getString(54));
				prmPersonalInfoDataM.setMBirthDate(this.parseDate(rs.getDate(55)));
				
				prmPersonalInfoDataM.setMGender(rs.getString(56));
				prmPersonalInfoDataM.setMOccupation(rs.getString(57));
				prmPersonalInfoDataM.setMCompany(rs.getString(58));
				prmPersonalInfoDataM.setMPosition(rs.getString(59));
				prmPersonalInfoDataM.setMDepartment(rs.getString(60));
				
				prmPersonalInfoDataM.setMIncome(rs.getBigDecimal(61));
				prmPersonalInfoDataM.setBusinessSize(rs.getString(62));
				prmPersonalInfoDataM.setQualification(rs.getString(63));
				prmPersonalInfoDataM.setLandOwnerShip(rs.getString(64));
				prmPersonalInfoDataM.setBuildingType(rs.getString(65));
				prmPersonalInfoDataM.setHouseRegisStatus(rs.getString(66));
				prmPersonalInfoDataM.setCustomerSegment(rs.getString(67));
				log.debug("prmPersonalInfoDataM.getThaiFirstName() = "+prmPersonalInfoDataM.getThaiFirstName());
				vt.add(prmPersonalInfoDataM);
			}
			return vt;
		} catch (Exception e) {
			log.fatal(e.getLocalizedMessage());
			throw new OrigApplicationCustomerMException(e.getMessage());
		} finally {
			try {
				closeConnection(conn, rs, ps);
			} catch (Exception e) {
				log.fatal(e.getLocalizedMessage());
			}
		}
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.eaf.orig.share.dao.OrigApplicationCustomerMDAO#saveUpdateModelOrigApplicationCustomerM(com.eaf.orig.shared.model.PersonalInfoDataM)
	 */
	public void saveUpdateModelOrigApplicationCustomerM(
			PersonalInfoDataM prmPersonalInfoDataM)
			throws OrigApplicationCustomerMException {
		double returnRows = 0;
		try {
			returnRows = updateTableORIG_APPLICATION_CUSTOMER(prmPersonalInfoDataM);

			if (returnRows == 0) {
				log.debug("New record then can't update record in table ORIG_APPLICATION_CUSTOMER then call Insert method");
				//String personalID = ORIGDAOFactory.getUniqueIDGeneratorDAO2().getUniqByName(EJBConstant.PERSONAL_ID);
				//prmPersonalInfoDataM.setPersonalID(personalID);
				ORIGGeneratorManager generatorManager = ORIGEJBService.getGeneratorManager();
				String personalID = generatorManager.generateUniqueIDByName(EJBConstant.PERSONAL_ID);
				prmPersonalInfoDataM.setPersonalID(personalID);
				createTableORIG_APPLICATION_CUSTOMER(prmPersonalInfoDataM);
			}
			
			String personalID = prmPersonalInfoDataM.getPersonalID();
			
			//Save update Address
			Vector vOrigCustomerAddress = prmPersonalInfoDataM.getAddressVect();
			//Vector vOrigCustomerAddress_tmp = prmPersonalInfoDataM.getAddressTmpVect();
			//log.debug(">>>>>>>>>>>>>>>>>>>>>>>>> vOrigCutomerAddress.size() " + vOrigCustomerAddress.size());
			if (vOrigCustomerAddress != null) {
				OrigCustomerAddressMDAO origCustomerAddressMDAO = ORIGDAOFactory.getOrigCustomerAddressMDAO();
				AddressDataM addressDataM;
				for (int i = 0; i < vOrigCustomerAddress.size(); i++) {
					addressDataM = (AddressDataM) vOrigCustomerAddress.get(i);
					addressDataM.setApplicationRecordId(prmPersonalInfoDataM.getAppRecordID());
					addressDataM.setCmpCode(prmPersonalInfoDataM.getCmpCode());
					if(addressDataM.getCreateBy() == null || ("").equals(addressDataM.getCreateBy())){
						addressDataM.setCreateBy(prmPersonalInfoDataM.getUpdateBy());
					}else{
						addressDataM.setUpdateBy(prmPersonalInfoDataM.getUpdateBy());
					}
					origCustomerAddressMDAO.saveUpdateModelOrigCustomerAddressM(addressDataM, personalID);
				}
			}
			//create finance;
			Vector vFinance = prmPersonalInfoDataM.getFinanceVect();
			OrigCustomerFinanceMDAO origCustomerFinanceMDAO = ORIGDAOFactory.getOrigCustomerFinanceMDAO();
			if (vFinance != null) {
				BankDataM  bankDataM;
				for (int i = 0; i < vFinance.size(); i++) {
					bankDataM = (BankDataM) vFinance.get(i);
					//bankDataM.setAppRecodeID(prmPersonalInfoDataM.getAppRecordID());
					//bankDataM.setPersonalID(prmPersonalInfoDataM.getIdNo());
					//bankDataM.setCmpCode(prmPersonalInfoDataM.getCmpCode());
					if(bankDataM.getCreateBy() == null || ("").equals(bankDataM.getCreateBy())){
						bankDataM.setCreateBy(prmPersonalInfoDataM.getUpdateBy());
					}else{
						bankDataM.setUpdateBy(prmPersonalInfoDataM.getUpdateBy());
					}
					origCustomerFinanceMDAO.saveUpdateModelOrigCustomerFinnanceM(bankDataM, personalID);
				}
			}
			
			//create change_name
			Vector vChangeName = prmPersonalInfoDataM.getChangeNameVect();
			OrigCustomerChangeNameMDAO origCustomerChaneNameMDAO = ORIGDAOFactory.getOrigCustomerChangeNameMDAO();
			if (vChangeName != null) {
				ChangeNameDataM changeNameDataM;
				for (int i = 0; i < vChangeName.size(); i++) {
					changeNameDataM = (ChangeNameDataM) vChangeName.get(i);
					changeNameDataM.setApplicationRecordId(prmPersonalInfoDataM.getAppRecordID());
					changeNameDataM.setIdNo(prmPersonalInfoDataM.getIdNo());
					changeNameDataM.setCmpCode(prmPersonalInfoDataM.getCmpCode());
					if(changeNameDataM.getCreateBy() == null || ("").equals(changeNameDataM.getCreateBy())){
						changeNameDataM.setCreateBy(prmPersonalInfoDataM.getUpdateBy());
					}else{
						changeNameDataM.setUpdateBy(prmPersonalInfoDataM.getUpdateBy());
					}
					origCustomerChaneNameMDAO.saveUpdateModelOrigCustomerChangeNameM(changeNameDataM, prmPersonalInfoDataM.getIdNo());
				}
			}
			/*
			//create corperated
			Vector vCorperated = prmPersonalInfoDataM.getCorperatedVect();
			OrigCorperatedMDAO origCorperatedMDAO = ORIGDAOFactory
					.getOrigCorperatedMDAO();
			if (vCorperated != null) {
				for (int i = 0; i < vCorperated.size(); i++) {
					CorperatedDataM corperatedDataM = (CorperatedDataM) vCorperated.get(i);
					corperatedDataM.setAppRecordID(prmPersonalInfoDataM.getAppRecordID());
					corperatedDataM.setIdNo(prmPersonalInfoDataM.getIdNo());
					corperatedDataM.setCmpCode(prmPersonalInfoDataM.getCmpCode());
					if(corperatedDataM.getCreateBy() == null || ("").equals(corperatedDataM.getCreateBy())){
						corperatedDataM.setCreateBy(prmPersonalInfoDataM.getUpdateBy());
					}else{
						corperatedDataM.setUpdateBy(prmPersonalInfoDataM.getUpdateBy());
					}
					origCorperatedMDAO.saveUpdateModelOrigCorperatedM(corperatedDataM);
				}
			}*/
			//Delete not in key
			deleteNotInKeyCustomerData(prmPersonalInfoDataM);
            //Add Save Verification Result
			//===================================			 
			XRulesVerificationResultDataM  prmXRuesVerficationResultDataM=prmPersonalInfoDataM.getXrulesVerification();
			if(prmXRuesVerficationResultDataM!=null){
				log.debug(">>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>appRecdId " + prmPersonalInfoDataM.getAppRecordID() + "  getPersonalID " + prmPersonalInfoDataM.getPersonalID());
				//prmXRuesVerficationResultDataM.setApplicationRecordId(prmPersonalInfoDataM.getAppRecordID());
				prmXRuesVerficationResultDataM.setPersonalID(prmPersonalInfoDataM.getPersonalID());
				//prmXRuesVerficationResultDataM.setCmpCode("01");
				//prmXRuesVerficationResultDataM.setIdNo(prmPersonalInfoDataM.getIdNo());
				//prmXRuesVerficationResultDataM.setUpdateBy(prmPersonalInfoDataM.getUpdateBy());
			    XRulesVerificationResultDAO xRuesVerificationDAO=ORIGDAOFactory.getXRulesVerificationResultDAO();
			    xRuesVerificationDAO.saveUpdateModelXRulesVerificationResultM(prmXRuesVerficationResultDataM);
			}
			//=====================		
			  //create pre Score
			PreScoreDataM prmPreScoredataM=prmPersonalInfoDataM.getPreScoreDataM();
			log.debug(" PreScore is "+prmPreScoredataM);
			if(prmPreScoredataM!=null){
			    prmPreScoredataM.setApplicationRecordId(prmPersonalInfoDataM.getAppRecordID());
			    prmPreScoredataM.setCreateBy(prmPersonalInfoDataM.getCreateBy());
			    prmPreScoredataM.setUpdateBy(prmPersonalInfoDataM.getUpdateBy());
			    prmPreScoredataM.setCmpCode(prmPersonalInfoDataM.getCmpCode());
			    prmPreScoredataM.setIdNo(prmPersonalInfoDataM.getIdNo());
			    
			    log.debug("prmPersonalInfoDataM.getAppRecordID() "+prmPersonalInfoDataM.getAppRecordID());
			    log.debug("prmPersonalInfoDataM.getCmpCode() "+prmPersonalInfoDataM.getCmpCode());
			    log.debug("prmPersonalInfoDataM.getIdNo() "+prmPersonalInfoDataM.getIdNo());
			    
			    ORIGDAOFactory.getPreScoreDataMDAO().saveUpdateModelOrigPreScoreM(prmPreScoredataM);
			}
		} catch (Exception e) {
			log.fatal(e.getLocalizedMessage());
			throw new OrigApplicationCustomerMException(e.getMessage());
		}

	}

	/**
	 * @param prmPersonalInfoDataM
	 * @return
	 */
	private double updateTableORIG_APPLICATION_CUSTOMER(
			PersonalInfoDataM prmPersonalInfoDataM)
			throws OrigApplicationCustomerMException {
		double returnRows = 0;
		PreparedStatement ps = null;
		Connection conn = null;
		try {
			//conn = Get Connection
			conn = getConnection();
			StringBuffer sql = new StringBuffer("");
			sql.append("UPDATE ORIG_PERSONAL_INFO ");
			/*sql.append(" SET  IDNO=?,PERSONAL_TYPE=?,CUSTOMER_TYPE=?, MAILING_ADDRESS=?, PREVIOUS_RECORD=?, TIME_IN_CURRENT_ADDRESS=? ,CONSENT_FLAG=?,CONSENT_DATE=?,K_CONSENT_FLAG=?,K_CONSENT_DATE=?,UPDATE_BY=? ");
			sql.append(" ,COBORROWER_FLAG=?,DEBT_INCLUDE_FLAG =? ");*/
			//sql.append(" , ");
			sql.append(" SET PERSONAL_TYPE = ?,CUSTOMER_TYPE = ? ");
			sql.append(" ,MAILING_ADDRESS = ?,PREVIOUS_RECORD = ?,TIME_IN_CURRENT_ADDRESS = ?,K_CONSENT_FLAG = ?,K_CONSENT_DATE = ?");
			sql.append(" ,CONSENT_FLAG = ?,CONSENT_DATE = ?,COBORROWER_FLAG = ?,DEBT_INCLUDE_FLAG = ?,CUSTOMER_NO = ?");
			sql.append(" ,TH_TITLE_CODE = ?,TH_FIRST_NAME = ?,TH_LAST_NAME = ?,TH_MID_NAME = ?,EN_TITLE_CODE = ?");
			sql.append(" ,EN_FIRST_NAME = ?,EN_LAST_NAME = ?,EN_MID_NAME = ?,GENDER = ?,BIRTH_DATE = ?");
			sql.append(" ,RACE = ?,NATIONALITY = ?,MARRIED = ?,TAX_ID = ?,CID_TYPE = ?");
			sql.append(" ,CID_ISSUE_DATE = ?,CID_EXP_DATE = ?,CID_ISSUE_BY = ?,OCCUPATION = ?,COMPANY = ?");
			sql.append(" ,BUSINESS_TYPE = ?,POSITION_CODE = ?,POSITION_DESC = ?,DEPARTMENT = ?,SALARY = ?");
			sql.append(" ,TOT_WORK_YEAR = ?,TOT_WORK_MONTH = ?,OTHER_INCOME = ?,SORCE_OF_INCOME = ?,MCID_TYPE = ?");
			sql.append(" ,MCID_NO = ?,M_TITLE_CODE = ?,M_TH_FIRST_NAME = ?,M_TH_MID_NAME = ?,M_TH_LAST_NAME = ?");
			sql.append(" ,M_ETITLE_CODE = ?,M_EN_FIRST_NAME = ?,M_EN_MID_NAME = ?,M_EN_LAST_NAME = ?,M_BIRTH_DATE = ?");
			sql.append(" ,M_GENDER = ?,M_OCCUPATION = ?,M_COMPANY = ?,M_POSITION = ?,M_DEPARTMENT = ?");
			sql.append(" ,M_INCOME = ?,BUSINESS_SIZE = ?,DEGREE = ?,LANDOWNER = ?,BUILDING_TYPE = ?");
			sql.append(" ,UPDATE_DATE = SYSDATE,UPDATE_BY = ? ,HOUSE_REGISTER_STATUS = ?, CUSTSEGM = ?");
			sql.append(" WHERE APPLICATION_RECORD_ID = ? AND PERSONAL_ID=? ");
			String dSql = String.valueOf(sql);
			log.debug("Sql=" + dSql);
			ps = conn.prepareStatement(dSql);
			/*ps.setString(1, prmPersonalInfoDataM.getIdNo());
			ps.setString(2, prmPersonalInfoDataM.getPersonalType());
			ps.setString(3, prmPersonalInfoDataM.getCustomerType());
			ps.setString(4, prmPersonalInfoDataM.getMailingAddress());
			ps.setString(5, prmPersonalInfoDataM.getPreviousRecord());
			ps.setDouble(6, prmPersonalInfoDataM.getTimeInCurrentAddress());
			ps.setString(7,prmPersonalInfoDataM.getConsentFlag());
			ps.setDate(8,this.parseDate(prmPersonalInfoDataM.getConsentDate()));
			ps.setString(9,prmPersonalInfoDataM.getKConsentFlag());
			ps.setDate(10,this.parseDate(prmPersonalInfoDataM.getKConsentDate()));
			ps.setString(11, prmPersonalInfoDataM.getUpdateBy());
			ps.setString(12,prmPersonalInfoDataM.getCoborrowerFlag());
			ps.setString(13,prmPersonalInfoDataM.getDebtIncludeFlag());*/
			ps.setString(1, prmPersonalInfoDataM.getPersonalType());
			ps.setString(2, prmPersonalInfoDataM.getCustomerType());
			
			ps.setString(3, prmPersonalInfoDataM.getMailingAddress());
			ps.setString(4, prmPersonalInfoDataM.getPreviousRecord());
			ps.setString(5, Double.toString(prmPersonalInfoDataM.getTimeInCurrentAddress()));
			ps.setString(6, prmPersonalInfoDataM.getKConsentFlag());
			ps.setDate(7, this.parseDate(prmPersonalInfoDataM.getKConsentDate()));
			
			ps.setString(8, prmPersonalInfoDataM.getConsentFlag());
			ps.setDate(9, this.parseDate(prmPersonalInfoDataM.getConsentDate()));
			ps.setString(10, prmPersonalInfoDataM.getCoborrowerFlag());
			ps.setString(11, prmPersonalInfoDataM.getDebtIncludeFlag());
			ps.setString(12, prmPersonalInfoDataM.getCustomerNO());

			ps.setString(13, prmPersonalInfoDataM.getThaiTitleName());
			ps.setString(14, prmPersonalInfoDataM.getThaiFirstName());
			ps.setString(15, prmPersonalInfoDataM.getThaiLastName());
			ps.setString(16, prmPersonalInfoDataM.getThaiMidName());
			ps.setString(17, prmPersonalInfoDataM.getEngTitleName());

			ps.setString(18, prmPersonalInfoDataM.getEngFirstName());
			ps.setString(19, prmPersonalInfoDataM.getEngLastName());
			ps.setString(20, prmPersonalInfoDataM.getEngMidName());
			ps.setString(21, prmPersonalInfoDataM.getGender());
			ps.setDate(22, this.parseDate(prmPersonalInfoDataM.getBirthDate()));

			ps.setString(23, prmPersonalInfoDataM.getRace());
			ps.setString(24, prmPersonalInfoDataM.getNationality());
			ps.setString(25, prmPersonalInfoDataM.getMaritalStatus());
			ps.setString(26, prmPersonalInfoDataM.getTaxID());
			ps.setString(27, prmPersonalInfoDataM.getCardIdentity());

			ps.setDate(28, this.parseDate(prmPersonalInfoDataM.getIssueDate()));
			ps.setDate(29, this.parseDate(prmPersonalInfoDataM.getExpiryDate()));
			ps.setString(30, prmPersonalInfoDataM.getIssueBy());
			ps.setString(31, prmPersonalInfoDataM.getOccupation());
			ps.setString(32, prmPersonalInfoDataM.getCompanyName());
			
			ps.setString(33, prmPersonalInfoDataM.getBusinessType());
			ps.setString(34, prmPersonalInfoDataM.getPosition());
			ps.setString(35, prmPersonalInfoDataM.getPositionDesc());
			ps.setString(36, prmPersonalInfoDataM.getDepartment());
			ps.setBigDecimal(37, prmPersonalInfoDataM.getSalary());
			
			ps.setInt(38, prmPersonalInfoDataM.getYearOfWork());
			ps.setInt(39, prmPersonalInfoDataM.getMonthOfWork());
			//ps.setBigDecimal(33, prmPersonalInfoDataM.getOtherIncome());
			ps.setBigDecimal(40, prmPersonalInfoDataM.getOtherIncome());
			ps.setString(41, prmPersonalInfoDataM.getSourceOfOtherIncome());
			ps.setString(42, prmPersonalInfoDataM.getMCardType());
			
			ps.setString(43, prmPersonalInfoDataM.getMIDNo());
			ps.setString(44, prmPersonalInfoDataM.getMThaiTitleName());
			ps.setString(45, prmPersonalInfoDataM.getMThaiFirstName());
			ps.setString(46, prmPersonalInfoDataM.getMThaiMidName());
			ps.setString(47, prmPersonalInfoDataM.getMThaiLastName());

			ps.setString(48, prmPersonalInfoDataM.getMEngTitleName());
			ps.setString(49, prmPersonalInfoDataM.getMEngFirstName());
			ps.setString(50, prmPersonalInfoDataM.getMEngMidName());
			ps.setString(51, prmPersonalInfoDataM.getMEngLastName());
			ps.setDate(52, this.parseDate(prmPersonalInfoDataM.getMBirthDate()));

			ps.setString(53, prmPersonalInfoDataM.getMGender());
			ps.setString(54, prmPersonalInfoDataM.getMOccupation());
			ps.setString(55, prmPersonalInfoDataM.getMCompany());
			ps.setString(56, prmPersonalInfoDataM.getMPosition());
			ps.setString(57, prmPersonalInfoDataM.getMDepartment());

			ps.setBigDecimal(58, prmPersonalInfoDataM.getMIncome());
			ps.setString(59, prmPersonalInfoDataM.getBusinessSize());
			ps.setString(60, prmPersonalInfoDataM.getQualification());
			ps.setString(61, prmPersonalInfoDataM.getLandOwnerShip());
			ps.setString(62, prmPersonalInfoDataM.getBuildingType());

			//ps.setDate(56, this.parseDate(prmPersonalInfoDataM.getCreateDate()));
			ps.setString(63, prmPersonalInfoDataM.getUpdateBy());
			ps.setString(64, prmPersonalInfoDataM.getHouseRegisStatus());
			ps.setString(65, prmPersonalInfoDataM.getCustomerSegment());
			//ps.setDate(64, this.parseDate(prmPersonalInfoDataM.getUpdateDate()));
			//ps.setString(59, prmPersonalInfoDataM.getUpdateBy());
			
			ps.setString(66, prmPersonalInfoDataM.getAppRecordID());
			ps.setString(67, prmPersonalInfoDataM.getPersonalID());
						
			returnRows = ps.executeUpdate();
		} catch (Exception e) {
			log.fatal(e.getLocalizedMessage());
			throw new OrigApplicationCustomerMException(e.getMessage());
		} finally {
			try {
				closeConnection(conn, ps);
			} catch (Exception e) {
				log.fatal(e.getLocalizedMessage());
			}
		}
		return returnRows;
	}
	
	/** Delete not in key**/
	private void deleteNotInKeyCustomerData(PersonalInfoDataM personalInfoDataM) throws OrigApplicationCustomerMException {
		OrigCustomerAddressMDAO addressMDAO = ORIGDAOFactory.getOrigCustomerAddressMDAO();
		OrigCustomerFinanceMDAO financeMDAO = ORIGDAOFactory.getOrigCustomerFinanceMDAO();
//		OrigCustomerChangeNameMDAO changeNameMDAO = ORIGDAOFactory.getOrigCustomerChangeNameMDAO();
		OrigCorperatedMDAO origCorperatedMDAO = ORIGDAOFactory.getOrigCorperatedMDAO();
//		XRulesVerificationResultDAO xRuesVerificationDAO=ORIGDAOFactory.getXRulesVerificationResultDAO();
		try{
			//delete address
			if(personalInfoDataM.getAddressVect() == null || personalInfoDataM.getAddressVect().size() == 0){
				addressMDAO.deleteTableORIG_CUSTOMER_ADDRESS(personalInfoDataM.getPersonalID());
			}else{
				addressMDAO.deleteNotInKeyTableORIG_CUSTOMER_ADDRESS(personalInfoDataM.getAddressVect(), personalInfoDataM.getPersonalID());
			}
			
			//delete finance
			if(personalInfoDataM.getFinanceVect() == null || personalInfoDataM.getFinanceVect().size() == 0){
				financeMDAO.deleteTableORIG_CUSTOMER_FINANCE(personalInfoDataM.getAppRecordID(), personalInfoDataM.getCmpCode(), personalInfoDataM.getIdNo());
			}else{
				financeMDAO.deleteNotInKeyTableORIG_CUSTOMER_FINANCE(personalInfoDataM.getFinanceVect(), personalInfoDataM.getPersonalID());
			}
			
			//delete change name
			/*if(personalInfoDataM.getChangeNameVect() == null || personalInfoDataM.getChangeNameVect().size() == 0){
				changeNameMDAO.deleteTableORIG_CUSTOMER_CHANGE_NAME(personalInfoDataM.getAppRecordID(), personalInfoDataM.getCmpCode(), personalInfoDataM.getIdNo());
			}else{
				changeNameMDAO.deleteNotInKeyTableORIG_CUSTOMER_CHANGE_NAME(personalInfoDataM.getChangeNameVect(), personalInfoDataM.getAppRecordID(), personalInfoDataM.getCmpCode(), personalInfoDataM.getIdNo());
			}*/
			
			//delete corperated
			if(personalInfoDataM.getCorperatedVect() == null || personalInfoDataM.getCorperatedVect().size() == 0){
				origCorperatedMDAO.deleteTableORIG_Corperated(personalInfoDataM.getCmpCode(), personalInfoDataM.getIdNo());
			}else{
				origCorperatedMDAO.deleteNotInKeyTableORIG_Corperated(personalInfoDataM.getCorperatedVect(), personalInfoDataM.getCmpCode(), personalInfoDataM.getIdNo());
			}
        	//=====================
             		 			 			
			 
		}catch (Exception e) {
			log.fatal(e.getLocalizedMessage());
			throw new OrigApplicationCustomerMException(e.getMessage());
		}
	}
	
	/** delete not in key**/
	
	public void deleteNotInKeyTableORIG_APPLICATION_CUSTOMER(Vector personalVect, String appRecordID) throws OrigApplicationCustomerMException{
		PreparedStatement ps = null;
		String cmpCode = null;
		Connection conn = null;
        try {
            // conn = Get Connection
            conn = getConnection();

            StringBuffer sql = new StringBuffer("");
            sql.append("DELETE FROM ORIG_APPLICATION_CUSTOMER");
            sql.append(" WHERE APPLICATION_RECORD_ID = ? AND CMPCDE = ? ");
            
            if ((personalVect != null) && (personalVect.size() != 0)) {
                sql.append(" AND IDNO NOT IN ( ");
                PersonalInfoDataM personalInfoDataM = null;
                for (int i = 0; i < personalVect.size(); i++) {
                    personalInfoDataM = (PersonalInfoDataM) personalVect.get(i);
                    sql.append(" '" + personalInfoDataM.getIdNo() + "' , ");
                }
                
                if (sql.toString().trim().endsWith(",")) {
                    sql.delete(sql.toString().lastIndexOf(","), sql.toString().length());
                }

                sql.append(" ) ");
                cmpCode = personalInfoDataM.getCmpCode();
            }

            String dSql = String.valueOf(sql);

            ps = conn.prepareStatement(dSql);

            ps.setString(1, appRecordID);
            ps.setString(2, cmpCode);
            
            ps.executeUpdate();
            ps.close();

        } catch (Exception e) {
        	logger.error("ERROR >> ",e);
			throw new OrigApplicationCustomerMException(e.getMessage());
		} finally {
			try {
				closeConnection(conn, ps);
			} catch (Exception e) {
				logger.error("ERROR >> ",e);
				throw new OrigApplicationCustomerMException(e.getMessage());
			}
		}
	}
	
	/** select personal id that not in model**/
	public Vector selectPersonalIDNotInModel(Vector personalVect, String appRecordID)throws OrigApplicationCustomerMException {
		PreparedStatement ps = null;
		ResultSet rs = null;
		Connection conn = null;
		try {
			//conn = Get Connection
			conn = getConnection();
			StringBuffer sql = new StringBuffer("");
			sql.append("SELECT IDNO ");
			sql.append("FROM ORIG_APPLICATION_CUSTOMER ");
			sql.append(" WHERE APPLICATION_RECORD_ID = ? ");
            
            if ((personalVect != null) && (personalVect.size() != 0)) {
                sql.append(" AND IDNO NOT IN ( ");
                PersonalInfoDataM personalInfoDataM = null;
                for (int i = 0; i < personalVect.size(); i++) {
                    personalInfoDataM = (PersonalInfoDataM) personalVect.get(i);
                    sql.append(" '" + personalInfoDataM.getIdNo() + "' , ");
                }
                
                if (sql.toString().trim().endsWith(",")) {
                    sql.delete(sql.toString().lastIndexOf(","), sql.toString().length());
                }

                sql.append(" ) ");
            }
            
            String dSql = String.valueOf(sql);
			log.debug("Sql=" + dSql);
			ps = conn.prepareStatement(dSql);
			rs = null;
			ps.setString(1, appRecordID);

			rs = ps.executeQuery();
			Vector vt = new Vector();
			while (rs.next()) {
				vt.add(rs.getString(1));
			}
			return vt;
		} catch (Exception e) {
			log.fatal(e.getLocalizedMessage());
			throw new OrigApplicationCustomerMException(e.getMessage());
		} finally {
			try {
				closeConnection(conn, rs, ps);
			} catch (Exception e) {
				log.fatal(e.getLocalizedMessage());
			}
		}
	}
	/**
	 * 
	 * @param appRecordID
	 * @return
	 * @throws OrigApplicationCustomerMException
	 */
	public String selectCustomerTypeByAppRecordId(String appRecordID)throws OrigApplicationCustomerMException {
		PreparedStatement ps = null;
		ResultSet rs = null;
		Connection conn = null;
		try {
			//conn = Get Connection
			conn = getConnection();
			StringBuffer sql = new StringBuffer("");
			sql.append("SELECT CUSTOMER_TYPE ");
			sql.append("FROM ORIG_APPLICATION_CUSTOMER ");
			sql.append(" WHERE APPLICATION_RECORD_ID = ? ");
            
            String dSql = String.valueOf(sql);
			log.debug("Sql=" + dSql);
			ps = conn.prepareStatement(dSql);
			rs = null;
			ps.setString(1, appRecordID);

			rs = ps.executeQuery();
			String customerType = null;
			while (rs.next()) {
				customerType = rs.getString(1);
			}
			return customerType;
		} catch (Exception e) {
			log.fatal(e.getLocalizedMessage());
			throw new OrigApplicationCustomerMException(e.getMessage());
		} finally {
			try {
				closeConnection(conn, rs, ps);
			} catch (Exception e) {
				log.fatal(e.getLocalizedMessage());
			}
		}
	}
}
