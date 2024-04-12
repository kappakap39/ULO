package com.eaf.orig.ulo.app.ncb.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;

import com.eaf.core.ulo.common.date.ApplicationDate;
import com.eaf.core.ulo.common.util.Util;
import com.eaf.orig.shared.constant.OrigConstant;
import com.eaf.orig.shared.dao.OrigObjectDAO;
import com.eaf.orig.ulo.app.dao.ORIGDAOFactory;
import com.eaf.orig.ulo.app.dao.exception.ApplicationException;
import com.eaf.orig.ulo.app.util.GenerateUnique;
import com.eaf.orig.ulo.model.ncb.NcbAccountDataM;
import com.eaf.orig.ulo.model.ncb.NcbAddressDataM;
import com.eaf.orig.ulo.model.ncb.NcbIdDataM;
import com.eaf.orig.ulo.model.ncb.NcbInfoDataM;
import com.eaf.orig.ulo.model.ncb.NcbNameDataM;

public class OrigNCBInfoDAOImpl extends OrigObjectDAO implements OrigNCBInfoDAO {
	public OrigNCBInfoDAOImpl(String userId){
		this.userId = userId;
	}
	public OrigNCBInfoDAOImpl(){
		
	}
	private String userId = "";	
	@Override
	public void createOrigNcbInfoM(NcbInfoDataM ncbInfoM)
			throws ApplicationException {
		try {
			//Get Primary Key
		    logger.debug("ncbInfoM.getTrackingCode() :"+ncbInfoM.getTrackingCode());
		    if(Util.empty(ncbInfoM.getTrackingCode())){
				String trackingCode = GenerateUnique.generate(OrigConstant.SeqNames.NCB_INFO_PK);
				ncbInfoM.setTrackingCode(trackingCode);
		    }
		    ncbInfoM.setCreateBy(userId);
		    ncbInfoM.setUpdateBy(userId);
			createTableNCB_INFO(ncbInfoM);
			
			ArrayList<NcbAddressDataM> ncbAddressList = ncbInfoM.getNcbAddress();
			if(!Util.empty(ncbAddressList)) {
				OrigNCBAddressDAO ncbAddressDAO = ORIGDAOFactory.getNCBAddressDAO(userId);
				int i = 0;
				for(NcbAddressDataM ncbAddressM: ncbAddressList){
					if(ncbAddressM.getSeq() == 0){
						ncbAddressM.setSeq(++i);
					}
					ncbAddressM.setTrackingCode(ncbInfoM.getTrackingCode());
					ncbAddressDAO.createOrigNcbAddressM(ncbAddressM);
				}
			}
			
			ArrayList<NcbAccountDataM> ncbAccountList = ncbInfoM.getNcbAccounts();
			if(!Util.empty(ncbAccountList)) {
				OrigNCBAccountDAO ncbAccountDAO = ORIGDAOFactory.getNCBAccountDAO(userId);
				int i = 0;
				for(NcbAccountDataM ncbAccountM: ncbAccountList){
					if(ncbAccountM.getSeq() == 0){
						ncbAccountM.setSeq(++i);
					}
					ncbAccountM.setTrackingCode(ncbInfoM.getTrackingCode());
					ncbAccountDAO.createOrigNcbAccountM(ncbAccountM);
				}
			}
			
			ArrayList<NcbIdDataM> ncbIdList = ncbInfoM.getNcbIds();
			if(!Util.empty(ncbIdList)) {
				OrigNCBIdDAO ncbIdDAO = ORIGDAOFactory.getNCBIdDAO(userId);
				int i = 0;
				for(NcbIdDataM ncbIdM: ncbIdList){
					if(ncbIdM.getSeq() == 0){
						ncbIdM.setSeq(++i);
					}
					ncbIdM.setTrackingCode(ncbInfoM.getTrackingCode());
					ncbIdDAO.createOrigNcbIdM(ncbIdM);
				}
			}
			
			ArrayList<NcbNameDataM> ncbNameList = ncbInfoM.getNcbNames();
			if(!Util.empty(ncbNameList)) {
				OrigNCBNameDAO ncbNameDAO = ORIGDAOFactory.getNCBNameDAO(userId);
				int i = 0;
				for(NcbNameDataM ncbNameM: ncbNameList){
					if(ncbNameM.getSeq() == 0){
						ncbNameM.setSeq(++i);
					}
					ncbNameM.setTrackingCode(ncbInfoM.getTrackingCode());
					ncbNameDAO.createOrigNcbNameM(ncbNameM);
				}
			}
			
		} catch (Exception e) {
			logger.fatal(e.getLocalizedMessage());
			throw new ApplicationException(e.getMessage());
		}
	}

	private void createTableNCB_INFO(NcbInfoDataM ncbInfoM) throws ApplicationException {
		PreparedStatement ps = null;
		Connection conn = null;
		try {
			//conn = Get Connection
			conn = getConnection();
			StringBuffer sql = new StringBuffer("");
			sql.append("INSERT INTO NCB_INFO ");
			sql.append("( TRACKING_CODE, PERSONAL_ID, FICO_SCORE, FICO_ERROR_CODE, ");
			sql.append(" FICO_ERROR_MSG, FICO_REASON1_CODE, FICO_REASON1_DESC, ");
			sql.append(" FICO_REASON2_CODE, FICO_REASON2_DESC, FICO_REASON3_CODE, ");
			sql.append(" FICO_REASON3_DESC, FICO_REASON4_CODE, FICO_REASON4_DESC, ");
			sql.append(" CONSENT_REF_NO, NO_TIMES_ENQUIRY, NO_TIMES_ENQUIRY_6M, ");
			sql.append(" NO_CC_CARD, TOT_CC_OUT_STATDING, PERSONAL_LOAN_UNDER_BOT_ISSUER, ");
			sql.append(" DATE_OF_SEARCH, CREATE_DATE, CREATE_BY, UPDATE_DATE, UPDATE_BY) ");

			sql.append(" VALUES(?,?,?,?,  ?,?,?,  ?,?,?,  ?,?,?,  ?,?,?,  ?,?,?,  ?,?,?,?,? )");
			String dSql = String.valueOf(sql);
			logger.debug("Sql=" + dSql);
			ps = conn.prepareStatement(dSql);
			int cnt = 1;
			ps.setString(cnt++, ncbInfoM.getTrackingCode());
			ps.setString(cnt++, ncbInfoM.getPersonalId());
			ps.setString(cnt++, ncbInfoM.getFicoScore());
			ps.setString(cnt++, ncbInfoM.getFicoErrorCode());
			
			ps.setString(cnt++, ncbInfoM.getFicoErrorMsg());
			ps.setString(cnt++, ncbInfoM.getFicoReason1Code());
			ps.setString(cnt++, ncbInfoM.getFicoReason1Desc());
			
			ps.setString(cnt++, ncbInfoM.getFicoReason2Code());
			ps.setString(cnt++, ncbInfoM.getFicoReason2Desc());
			ps.setString(cnt++, ncbInfoM.getFicoReason3Code());
			
			ps.setString(cnt++, ncbInfoM.getFicoReason3Desc());
			ps.setString(cnt++, ncbInfoM.getFicoReason4Code());
			ps.setString(cnt++, ncbInfoM.getFicoReason4Desc());
			
			ps.setString(cnt++, ncbInfoM.getConsentRefNo());
			ps.setLong(cnt++, ncbInfoM.getNoOfTimesEnquiry());
			ps.setLong(cnt++, ncbInfoM.getNoOfTimesEnquiry6M());
			
			ps.setLong(cnt++, ncbInfoM.getNoOfCCCard());
			ps.setLong(cnt++, ncbInfoM.getTotCCOutStanding());
			ps.setBigDecimal(cnt++, ncbInfoM.getPersonalLoanUnderBotIssuer());
			
			ps.setDate(cnt++, ncbInfoM.getDateOfSearch());
			ps.setTimestamp(cnt++, ApplicationDate.getTimestamp());
			ps.setString(cnt++, ncbInfoM.getCreateBy());
			ps.setTimestamp(cnt++, ApplicationDate.getTimestamp());
			ps.setString(cnt++, ncbInfoM.getUpdateBy());
			
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
	public void deleteOrigNcbInfoM(String personalId, String trackingCode)
			throws ApplicationException {
		OrigNCBAddressDAO ncbAddressDAO = ORIGDAOFactory.getNCBAddressDAO();
		OrigNCBAccountDAO ncbAccountDAO = ORIGDAOFactory.getNCBAccountDAO();
		OrigNCBIdDAO ncbIdDAO = ORIGDAOFactory.getNCBIdDAO();
		OrigNCBNameDAO ncbNameDAO = ORIGDAOFactory.getNCBNameDAO();
		
		if(Util.empty(trackingCode)) {
			ArrayList<NcbInfoDataM> ncbInfoList = loadOrigNcbInfos(personalId);
			if(!Util.empty(ncbInfoList)) {
				for(NcbInfoDataM ncbInfoM : ncbInfoList) {
					ncbAddressDAO.deleteOrigNcbAddressM(ncbInfoM.getTrackingCode(), 0);
					ncbAccountDAO.deleteOrigNcbAccountM(ncbInfoM.getTrackingCode(), 0);
					ncbIdDAO.deleteOrigNcbIdM(ncbInfoM.getTrackingCode(), 0);
					ncbNameDAO.deleteOrigNcbNameM(ncbInfoM.getTrackingCode(), 0);
				}
			}
		} else {
			ncbAddressDAO.deleteOrigNcbAddressM(trackingCode, 0);
			ncbAccountDAO.deleteOrigNcbAccountM(trackingCode, 0);
			ncbIdDAO.deleteOrigNcbIdM(trackingCode, 0);
			ncbNameDAO.deleteOrigNcbNameM(trackingCode, 0);
		}
		
		deleteTableNCB_INFO(personalId, trackingCode);
	}

	private void deleteTableNCB_INFO(String personalId, String trackingCode) throws ApplicationException {
		PreparedStatement ps = null;
		Connection conn = null;
		try {
			//conn = Get Connection
			conn = getConnection();
			StringBuffer sql = new StringBuffer("");
			sql.append("DELETE NCB_INFO ");
			sql.append(" WHERE PERSONAL_ID = ?");
			if(!Util.empty(trackingCode)) {
				sql.append(" AND TRACKING_CODE = ? ");
			}
			String dSql = String.valueOf(sql);
			logger.debug("Sql=" + dSql);
			ps = conn.prepareStatement(dSql);
			ps.setString(1, personalId);
			if(!Util.empty(trackingCode)) {
				ps.setString(2, trackingCode);
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
	public ArrayList<NcbInfoDataM> loadOrigNcbInfos(String personalID,
			Connection conn) throws ApplicationException {
		ArrayList<NcbInfoDataM> result = selectTableNCB_INFO_LIST(personalID,conn);
		if(!Util.empty(result)) {
			OrigNCBAddressDAO ncbAddressDAO = ORIGDAOFactory.getNCBAddressDAO();
			OrigNCBAccountDAO ncbAccountDAO = ORIGDAOFactory.getNCBAccountDAO();
			OrigNCBIdDAO ncbIdDAO = ORIGDAOFactory.getNCBIdDAO();
			OrigNCBNameDAO ncbNameDAO = ORIGDAOFactory.getNCBNameDAO();
			for(NcbInfoDataM ncbInfoM : result) {
				ArrayList<NcbAddressDataM> ncbAddressList = ncbAddressDAO.loadOrigNcbAddressM(ncbInfoM.getTrackingCode(),conn);
				if(!Util.empty(ncbAddressList)) {
					ncbInfoM.setNcbAddress(ncbAddressList);
				}
				
				ArrayList<NcbAccountDataM> ncbAccountList = ncbAccountDAO.loadOrigNcbAccountM(ncbInfoM.getTrackingCode(),0,conn);
				if(!Util.empty(ncbAccountList)) {
					ncbInfoM.setNcbAccounts(ncbAccountList);
				}
				
				ArrayList<NcbIdDataM> ncbIdList = ncbIdDAO.loadOrigNcbIdM(ncbInfoM.getTrackingCode(),conn);
				if(!Util.empty(ncbIdList)) {
					ncbInfoM.setNcbIds(ncbIdList);
				}
				
				ArrayList<NcbNameDataM> ncbNameList = ncbNameDAO.loadOrigNcbNameM(ncbInfoM.getTrackingCode(),conn);
				if(!Util.empty(ncbNameList)) {
					ncbInfoM.setNcbNames(ncbNameList);
				}
				
			}
		}
		return result;
	}
	@Override
	public ArrayList<NcbInfoDataM> loadOrigNcbInfos(String personalID)
			throws ApplicationException {
		ArrayList<NcbInfoDataM> result = selectTableNCB_INFO_LIST(personalID);
		if(!Util.empty(result)) {
			OrigNCBAddressDAO ncbAddressDAO = ORIGDAOFactory.getNCBAddressDAO();
			OrigNCBAccountDAO ncbAccountDAO = ORIGDAOFactory.getNCBAccountDAO();
			OrigNCBIdDAO ncbIdDAO = ORIGDAOFactory.getNCBIdDAO();
			OrigNCBNameDAO ncbNameDAO = ORIGDAOFactory.getNCBNameDAO();
			for(NcbInfoDataM ncbInfoM : result) {
				ArrayList<NcbAddressDataM> ncbAddressList = ncbAddressDAO.loadOrigNcbAddressM(ncbInfoM.getTrackingCode());
				if(!Util.empty(ncbAddressList)) {
					ncbInfoM.setNcbAddress(ncbAddressList);
				}
				
				ArrayList<NcbAccountDataM> ncbAccountList = ncbAccountDAO.loadOrigNcbAccountM(ncbInfoM.getTrackingCode(),0);
				if(!Util.empty(ncbAccountList)) {
					ncbInfoM.setNcbAccounts(ncbAccountList);
				}
				
				ArrayList<NcbIdDataM> ncbIdList = ncbIdDAO.loadOrigNcbIdM(ncbInfoM.getTrackingCode());
				if(!Util.empty(ncbIdList)) {
					ncbInfoM.setNcbIds(ncbIdList);
				}
				
				ArrayList<NcbNameDataM> ncbNameList = ncbNameDAO.loadOrigNcbNameM(ncbInfoM.getTrackingCode());
				if(!Util.empty(ncbNameList)) {
					ncbInfoM.setNcbNames(ncbNameList);
				}
				
			}
		}
		return result;
	}
	
	@Override
	public NcbInfoDataM loadOrigNcbInfoM(String personalID)throws ApplicationException {
		NcbInfoDataM ncbInfoM = selectTableNCB_INFO(personalID);
		if(!Util.empty(ncbInfoM)) {
			OrigNCBAddressDAO ncbAddressDAO = ORIGDAOFactory.getNCBAddressDAO();
			OrigNCBAccountDAO ncbAccountDAO = ORIGDAOFactory.getNCBAccountDAO();
			OrigNCBIdDAO ncbIdDAO = ORIGDAOFactory.getNCBIdDAO();
			OrigNCBNameDAO ncbNameDAO = ORIGDAOFactory.getNCBNameDAO();
				ArrayList<NcbAddressDataM> ncbAddressList = ncbAddressDAO.loadOrigNcbAddressM(ncbInfoM.getTrackingCode());
				if(!Util.empty(ncbAddressList)) {
					ncbInfoM.setNcbAddress(ncbAddressList);
				}
				
				ArrayList<NcbAccountDataM> ncbAccountList = ncbAccountDAO.loadOrigNcbAccountM(ncbInfoM.getTrackingCode(),0);
				if(!Util.empty(ncbAccountList)) {
					ncbInfoM.setNcbAccounts(ncbAccountList);
				}
				
				ArrayList<NcbIdDataM> ncbIdList = ncbIdDAO.loadOrigNcbIdM(ncbInfoM.getTrackingCode());
				if(!Util.empty(ncbIdList)) {
					ncbInfoM.setNcbIds(ncbIdList);
				}
				
				ArrayList<NcbNameDataM> ncbNameList = ncbNameDAO.loadOrigNcbNameM(ncbInfoM.getTrackingCode());
				if(!Util.empty(ncbNameList)) {
					ncbInfoM.setNcbNames(ncbNameList);
				}
		}
		return ncbInfoM;
	}
	private ArrayList<NcbInfoDataM> selectTableNCB_INFO_LIST(String personalID) throws ApplicationException {
		Connection conn = null;
		try{
			conn = getConnection();
			return selectTableNCB_INFO_LIST(personalID,conn);
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

	private ArrayList<NcbInfoDataM> selectTableNCB_INFO_LIST(String personalID,Connection conn) throws ApplicationException {
		PreparedStatement ps = null;
		ResultSet rs = null;
		ArrayList<NcbInfoDataM> ncbInfoList = new ArrayList<NcbInfoDataM>();
		try {
			StringBuffer sql = new StringBuffer("");
			sql.append("SELECT TRACKING_CODE, PERSONAL_ID, FICO_SCORE, FICO_ERROR_CODE, ");
			sql.append(" FICO_ERROR_MSG, FICO_REASON1_CODE, FICO_REASON1_DESC, ");
			sql.append(" FICO_REASON2_CODE, FICO_REASON2_DESC, FICO_REASON3_CODE, ");
			sql.append(" FICO_REASON3_DESC, FICO_REASON4_CODE, FICO_REASON4_DESC, ");
			sql.append(" CONSENT_REF_NO, NO_TIMES_ENQUIRY, NO_TIMES_ENQUIRY_6M, ");
			sql.append(" NO_CC_CARD, TOT_CC_OUT_STATDING, PERSONAL_LOAN_UNDER_BOT_ISSUER, ");
			sql.append(" DATE_OF_SEARCH, CREATE_DATE, CREATE_BY, UPDATE_DATE, UPDATE_BY ");
			sql.append(" FROM NCB_INFO WHERE PERSONAL_ID = ? ");
			String dSql = String.valueOf(sql);
			logger.debug("Sql=" + dSql);
			ps = conn.prepareStatement(dSql);
			rs = null;
			ps.setString(1, personalID);

			rs = ps.executeQuery();

			while(rs.next()) {
				NcbInfoDataM ncbInfoM = new NcbInfoDataM();
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
				ncbInfoM.setNoOfTimesEnquiry(rs.getLong("NO_TIMES_ENQUIRY"));
				ncbInfoM.setNoOfTimesEnquiry6M(rs.getLong("NO_TIMES_ENQUIRY_6M"));
				ncbInfoM.setNoOfCCCard(rs.getLong("NO_CC_CARD"));
				ncbInfoM.setTotCCOutStanding(rs.getLong("TOT_CC_OUT_STATDING"));
				ncbInfoM.setPersonalLoanUnderBotIssuer(rs.getBigDecimal("PERSONAL_LOAN_UNDER_BOT_ISSUER"));
				
				ncbInfoM.setDateOfSearch(rs.getDate("DATE_OF_SEARCH"));
				ncbInfoM.setCreateBy(rs.getString("CREATE_BY"));
				ncbInfoM.setCreateDate(rs.getTimestamp("CREATE_DATE"));
				ncbInfoM.setUpdateBy(rs.getString("UPDATE_BY"));
				ncbInfoM.setUpdateDate(rs.getTimestamp("UPDATE_DATE"));
				ncbInfoList.add(ncbInfoM);
			}

			return ncbInfoList;
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
	private NcbInfoDataM  selectTableNCB_INFO(String personalID) throws ApplicationException {
		PreparedStatement ps = null;
		ResultSet rs = null;
		Connection conn = null;
		NcbInfoDataM  ncbInfoM = new NcbInfoDataM();
		try {
			//conn = Get Connection
			conn = getConnection();
			StringBuffer sql = new StringBuffer("");
			sql.append("SELECT TRACKING_CODE, PERSONAL_ID, FICO_SCORE, FICO_ERROR_CODE, ");
			sql.append(" FICO_ERROR_MSG, FICO_REASON1_CODE, FICO_REASON1_DESC, ");
			sql.append(" FICO_REASON2_CODE, FICO_REASON2_DESC, FICO_REASON3_CODE, ");
			sql.append(" FICO_REASON3_DESC, FICO_REASON4_CODE, FICO_REASON4_DESC, ");
			sql.append(" CONSENT_REF_NO, NO_TIMES_ENQUIRY, NO_TIMES_ENQUIRY_6M, ");
			sql.append(" NO_CC_CARD, TOT_CC_OUT_STATDING, PERSONAL_LOAN_UNDER_BOT_ISSUER, ");
			sql.append(" DATE_OF_SEARCH, CREATE_DATE, CREATE_BY, UPDATE_DATE, UPDATE_BY ");
			sql.append(" FROM NCB_INFO WHERE PERSONAL_ID = ? ");
			String dSql = String.valueOf(sql);
			logger.debug("Sql=" + dSql);
			ps = conn.prepareStatement(dSql);
			rs = null;
			ps.setString(1, personalID);
			
			rs = ps.executeQuery();
			
			if(rs.next()) {
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
				ncbInfoM.setNoOfTimesEnquiry(rs.getLong("NO_TIMES_ENQUIRY"));
				ncbInfoM.setNoOfTimesEnquiry6M(rs.getLong("NO_TIMES_ENQUIRY_6M"));
				ncbInfoM.setNoOfCCCard(rs.getLong("NO_CC_CARD"));
				ncbInfoM.setTotCCOutStanding(rs.getLong("TOT_CC_OUT_STATDING"));
				ncbInfoM.setPersonalLoanUnderBotIssuer(rs.getBigDecimal("PERSONAL_LOAN_UNDER_BOT_ISSUER"));
				
				ncbInfoM.setDateOfSearch(rs.getDate("DATE_OF_SEARCH"));
				ncbInfoM.setCreateBy(rs.getString("CREATE_BY"));
				ncbInfoM.setCreateDate(rs.getTimestamp("CREATE_DATE"));
				ncbInfoM.setUpdateBy(rs.getString("UPDATE_BY"));
				ncbInfoM.setUpdateDate(rs.getTimestamp("UPDATE_DATE"));
			}
			
			return ncbInfoM;
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

	@Override
	public void saveUpdateOrigNcbInfoM(NcbInfoDataM ncbInfoM)
			throws ApplicationException {
		int returnRows = 0;
		ncbInfoM.setUpdateBy(userId);
		returnRows = updateTableNCB_INFO(ncbInfoM);
		if (returnRows == 0) {
			logger.debug("New record then can't update record in table NCB_INFO then call Insert method");
			ncbInfoM.setCreateBy(userId);
		    ncbInfoM.setUpdateBy(userId);
			createOrigNcbInfoM(ncbInfoM);
		} else {
			ArrayList<NcbAddressDataM> ncbAddressList = ncbInfoM.getNcbAddress();
			if(!Util.empty(ncbAddressList)) {
				OrigNCBAddressDAO ncbAddressDAO = ORIGDAOFactory.getNCBAddressDAO(userId);
				for(NcbAddressDataM ncbAddressM: ncbAddressList){
					ncbAddressM.setTrackingCode(ncbInfoM.getTrackingCode());
					ncbAddressDAO.saveUpdateOrigNcbAddressM(ncbAddressM);
				}
			}
			
			ArrayList<NcbAccountDataM> ncbAccountList = ncbInfoM.getNcbAccounts();
			if(!Util.empty(ncbAccountList)) {
				OrigNCBAccountDAO ncbAccountDAO = ORIGDAOFactory.getNCBAccountDAO(userId);
				for(NcbAccountDataM ncbAccountM: ncbAccountList){
					ncbAccountM.setTrackingCode(ncbInfoM.getTrackingCode());
					ncbAccountDAO.saveUpdateOrigNcbAccountM(ncbAccountM);
				}
			}
			
			ArrayList<NcbIdDataM> ncbIdList = ncbInfoM.getNcbIds();
			if(!Util.empty(ncbIdList)) {
				OrigNCBIdDAO ncbIdDAO = ORIGDAOFactory.getNCBIdDAO(userId);
				int i = 0;
				for(NcbIdDataM ncbIdM: ncbIdList){
					if(ncbIdM.getSeq() == 0){
						ncbIdM.setSeq(++i);
					}
					ncbIdM.setTrackingCode(ncbInfoM.getTrackingCode());
					ncbIdDAO.saveUpdateOrigNcbIdM(ncbIdM);
				}
			}
			
			ArrayList<NcbNameDataM> ncbNameList = ncbInfoM.getNcbNames();
			if(!Util.empty(ncbNameList)) {
				OrigNCBNameDAO ncbNameDAO = ORIGDAOFactory.getNCBNameDAO(userId);
				int i = 0;
				for(NcbNameDataM ncbNameM: ncbNameList){
					if(ncbNameM.getSeq() == 0){
						ncbNameM.setSeq(++i);
					}
					ncbNameM.setTrackingCode(ncbInfoM.getTrackingCode());
					ncbNameDAO.saveUpdateOrigNcbNameM(ncbNameM);
				}
			}
			
		}
	}

	private int updateTableNCB_INFO(NcbInfoDataM ncbInfoM) throws ApplicationException {
		int returnRows = 0;
		PreparedStatement ps = null;
		Connection conn = null;
		try {
			//conn = Get Connection
			conn = getConnection();
			StringBuffer sql = new StringBuffer("");
			sql.append("UPDATE NCB_INFO ");
			sql.append(" SET FICO_SCORE = ?, FICO_ERROR_CODE = ?, FICO_ERROR_MSG = ?, ");
			sql.append(" FICO_REASON1_CODE = ?, FICO_REASON1_DESC = ?, ");
			sql.append(" FICO_REASON2_CODE = ?, FICO_REASON2_DESC = ?, FICO_REASON3_CODE = ?, ");
			sql.append(" FICO_REASON3_DESC = ?, FICO_REASON4_CODE = ?, FICO_REASON4_DESC = ?, ");
			sql.append(" CONSENT_REF_NO = ?, NO_TIMES_ENQUIRY = ?, NO_TIMES_ENQUIRY_6M = ?, ");
			sql.append(" NO_CC_CARD = ?, TOT_CC_OUT_STATDING = ?, PERSONAL_LOAN_UNDER_BOT_ISSUER = ?, ");
			sql.append(" UPDATE_BY = ?, UPDATE_DATE = ?, DATE_OF_SEARCH = ? ");			
			sql.append(" WHERE PERSONAL_ID = ? AND TRACKING_CODE = ? ");
			String dSql = String.valueOf(sql);
			logger.debug("Sql=" + dSql);
			ps = conn.prepareStatement(dSql);
			int cnt = 1;
			ps.setString(cnt++, ncbInfoM.getFicoScore());
			ps.setString(cnt++, ncbInfoM.getFicoErrorCode());
			ps.setString(cnt++, ncbInfoM.getFicoErrorMsg());
			
			ps.setString(cnt++, ncbInfoM.getFicoReason1Code());
			ps.setString(cnt++, ncbInfoM.getFicoReason1Desc());
			
			ps.setString(cnt++, ncbInfoM.getFicoReason2Code());
			ps.setString(cnt++, ncbInfoM.getFicoReason2Desc());
			ps.setString(cnt++, ncbInfoM.getFicoReason3Code());
			
			ps.setString(cnt++, ncbInfoM.getFicoReason3Desc());
			ps.setString(cnt++, ncbInfoM.getFicoReason4Code());
			ps.setString(cnt++, ncbInfoM.getFicoReason4Desc());
			
			ps.setString(cnt++, ncbInfoM.getConsentRefNo());
			ps.setLong(cnt++, ncbInfoM.getNoOfTimesEnquiry());
			ps.setLong(cnt++, ncbInfoM.getNoOfTimesEnquiry6M());
			
			ps.setLong(cnt++, ncbInfoM.getNoOfCCCard());
			ps.setLong(cnt++, ncbInfoM.getTotCCOutStanding());
			ps.setBigDecimal(cnt++, ncbInfoM.getPersonalLoanUnderBotIssuer());
			
			ps.setString(cnt++, ncbInfoM.getUpdateBy());
			ps.setTimestamp(cnt++, ApplicationDate.getTimestamp());
			ps.setDate(cnt++, ncbInfoM.getDateOfSearch());
			// WHERE CLAUSE
			ps.setString(cnt++, ncbInfoM.getPersonalId());
			ps.setString(cnt++, ncbInfoM.getTrackingCode());
			
			returnRows = ps.executeUpdate();
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
		return returnRows;
	}

	@Override
	public void deleteNotInKeyNcbInfo(ArrayList<NcbInfoDataM> ncbInfoList,
			String personalID) throws ApplicationException {
		ArrayList<String> ncbList = selectNotInKeyTableNCB_INFO(ncbInfoList, personalID);
		if(!Util.empty(ncbList)) {
			OrigNCBAddressDAO ncbAddressDAO = ORIGDAOFactory.getNCBAddressDAO();
			OrigNCBAccountDAO ncbAccountDAO = ORIGDAOFactory.getNCBAccountDAO();
			OrigNCBIdDAO ncbIdDAO = ORIGDAOFactory.getNCBIdDAO();
			OrigNCBNameDAO ncbNameDAO = ORIGDAOFactory.getNCBNameDAO();
			for(String trackingCode: ncbList) {
				ncbAddressDAO.deleteOrigNcbAddressM(trackingCode, 0);
				ncbAccountDAO.deleteOrigNcbAccountM(trackingCode, 0);
				ncbIdDAO.deleteOrigNcbIdM(trackingCode, 0);
				ncbNameDAO.deleteOrigNcbNameM(trackingCode, 0);
			}
		}
		
		deleteNotInKeyNCB_INFO(ncbInfoList, personalID);
	}

	private ArrayList<String> selectNotInKeyTableNCB_INFO(
			ArrayList<NcbInfoDataM> ncbInfoList, String personalID) throws ApplicationException {
		PreparedStatement ps = null;
		ResultSet rs = null;
		Connection conn = null;
		ArrayList<String> ncbList = new ArrayList<String>();
		try {
			//conn = Get Connection
			conn = getConnection();
			StringBuffer sql = new StringBuffer("");
			sql.append("SELECT TRACKING_CODE ");
			sql.append(" FROM NCB_INFO WHERE PERSONAL_ID = ? ");
			if (!Util.empty(ncbInfoList)) {
                sql.append(" AND TRACKING_CODE NOT IN ( ");

                for (NcbInfoDataM ncbInfoM: ncbInfoList) {
                    sql.append(" '" + ncbInfoM.getTrackingCode() + "' , ");
                }

                if (sql.toString().trim().endsWith(",")) {
                    sql.delete(sql.toString().lastIndexOf(","), sql.toString().length());
                }

                sql.append(" ) ");
            }
			String dSql = String.valueOf(sql);
			logger.debug("Sql=" + dSql);
			ps = conn.prepareStatement(dSql);
			rs = null;
			ps.setString(1, personalID);

			rs = ps.executeQuery();

			while(rs.next()) {
				String trackingCode = rs.getString("TRACKING_CODE");
				ncbList.add(trackingCode);
			}

			return ncbList;
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

	private void deleteNotInKeyNCB_INFO(ArrayList<NcbInfoDataM> ncbInfoList,
			String personalID) throws ApplicationException {
		PreparedStatement ps = null;
		Connection conn = null;
		try {
            // conn = Get Connection
            conn = getConnection();

            StringBuffer sql = new StringBuffer("");
            sql.append("DELETE FROM NCB_INFO ");
            sql.append(" WHERE PERSONAL_ID = ? ");
            
            if (!Util.empty(ncbInfoList)) {
                sql.append(" AND TRACKING_CODE NOT IN ( ");
                for (NcbInfoDataM ncbInfoM: ncbInfoList) {
                	logger.debug("ncbInfoM.getTrackingCode() = "+ncbInfoM.getTrackingCode());
                    sql.append(" '" + ncbInfoM.getTrackingCode() + "', ");
                }
                
                if (sql.toString().trim().endsWith(",")) {
                    sql.delete(sql.toString().lastIndexOf(","), sql.toString().length());
                }

                sql.append(" ) ");
            }

            String dSql = String.valueOf(sql);
            ps = conn.prepareStatement(dSql);
            ps.setString(1, personalID);
            
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

}
