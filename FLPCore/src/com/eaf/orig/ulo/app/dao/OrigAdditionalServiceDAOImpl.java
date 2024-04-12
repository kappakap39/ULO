/**
 * 
 */
package com.eaf.orig.ulo.app.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import com.eaf.core.ulo.common.date.ApplicationDate;
import com.eaf.core.ulo.common.util.Util;
import com.eaf.orig.shared.constant.OrigConstant;
import com.eaf.orig.shared.dao.OrigObjectDAO;
import com.eaf.orig.ulo.app.dao.exception.ApplicationException;
import com.eaf.orig.ulo.app.util.GenerateUnique;
import com.eaf.orig.ulo.model.app.ApplicationDataM;
import com.eaf.orig.ulo.model.app.ApplicationGroupDataM;
import com.eaf.orig.ulo.model.app.LoanDataM;
import com.eaf.orig.ulo.model.app.SpecialAdditionalServiceDataM;
import com.eaf.orig.ulo.model.app.SpecialAdditionalServiceMapDataM;

/**
 * @author avalant
 *
 */
public class OrigAdditionalServiceDAOImpl extends OrigObjectDAO implements
		OrigAdditionalServiceDAO {
	public static final String PERSONAL_SERVICE_ID_KEY = "P";
	public static final String LOAN_SERVICE_ID_KEY = "L";
	public OrigAdditionalServiceDAOImpl(){
		
	}
	public OrigAdditionalServiceDAOImpl(String userId){
		this.userId = userId;
	}
	private String userId = "";
	@Override
	public void createOrigAdditionalServiceM(SpecialAdditionalServiceDataM addServiceDataM) 
			throws ApplicationException {
		Connection conn = null;
		try{
			conn = getConnection();
			createOrigAdditionalServiceM(addServiceDataM, conn);
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
	public void createOrigAdditionalServiceM(
			SpecialAdditionalServiceDataM addServiceDataM, Connection conn)
			throws ApplicationException {
		logger.debug("addServiceDataM.getServiceId() :"+addServiceDataM.getServiceId());
	    if(Util.empty(addServiceDataM.getServiceId())){
			String serviceId = GenerateUnique.generate(OrigConstant.SeqNames.ORIG_ADDITIONAL_SERVICE_PK,conn);
			addServiceDataM.setCreateBy(userId);
			addServiceDataM.setServiceId(serviceId);
	    }
	    addServiceDataM.setUpdateBy(userId);
		createTableORIG_ADDITIONAL_SERVICE(addServiceDataM,conn);
	}
	private void createTableORIG_ADDITIONAL_SERVICE(SpecialAdditionalServiceDataM addServiceDataM,Connection conn) 
			throws ApplicationException {
		PreparedStatement ps = null;
//		Connection conn = null;
		try {
			//conn = Get Connection
//			conn = getConnection();
			StringBuffer sql = new StringBuffer("");
			sql.append("INSERT INTO ORIG_ADDITIONAL_SERVICE ");
			sql.append("( SERVICE_ID, CURRENT_ACC_NAME, CURRENT_ACC_NO, SAVING_ACC_NAME, ");
			sql.append(" SAVING_ACC_NO, SERVICE_TYPE, COMPLETE_DATA, COMPLETE_DATA_SV_AC, ");
			sql.append(" CREATE_DATE, CREATE_BY, UPDATE_DATE, UPDATE_BY, PERSONAL_ID ) ");

			sql.append(" VALUES(?,?,?,?,  ?,?,?,?,  ?,?,?,?, ? )");
			String dSql = String.valueOf(sql);
			logger.debug("Sql=" + dSql);
			ps = conn.prepareStatement(dSql);
			int cnt = 1;
			ps.setString(cnt++, addServiceDataM.getServiceId());
			ps.setString(cnt++, addServiceDataM.getCurrentAccName());
			ps.setString(cnt++, addServiceDataM.getCurrentAccNo());
			ps.setString(cnt++, addServiceDataM.getSavingAccName());
			
			ps.setString(cnt++, addServiceDataM.getSavingAccNo());
			ps.setString(cnt++, addServiceDataM.getServiceType());
			ps.setString(cnt++, addServiceDataM.getCompleteData());
			ps.setString(cnt++, addServiceDataM.getCompleteDataSaving());
			
			ps.setTimestamp(cnt++, ApplicationDate.getTimestamp());
			ps.setString(cnt++, addServiceDataM.getCreateBy());
			ps.setTimestamp(cnt++, ApplicationDate.getTimestamp());
			ps.setString(cnt++, addServiceDataM.getUpdateBy());
			ps.setString(cnt++, addServiceDataM.getPersonalId());
			
			ps.executeUpdate();
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
	}

	@Override
	public ArrayList<SpecialAdditionalServiceDataM> loadOrigAdditionalServiceM(
			List<String> serviceIDList) throws ApplicationException {
		ArrayList<SpecialAdditionalServiceDataM> serviceList = new ArrayList<SpecialAdditionalServiceDataM>();
		if(!Util.empty(serviceIDList)) {
			for(String serviceId : serviceIDList) {
				SpecialAdditionalServiceDataM result = selectTableORIG_ADDITIONAL_SERVICE(serviceId);
				serviceList.add(result);
			}
		}
		return serviceList;
	}
	@Override
	public ArrayList<SpecialAdditionalServiceDataM> loadOrigAdditionalService(
			ArrayList<String> personaIds, Connection conn)
			throws ApplicationException {
		return selectTableORIG_ADDITIONAL_SERVICE(personaIds,conn);
	}
	@Override
	public SpecialAdditionalServiceDataM loadOrigAdditionalServiceM(
			String serviceId) throws ApplicationException {
		SpecialAdditionalServiceDataM result = selectTableORIG_ADDITIONAL_SERVICE(serviceId);
		return result;
	}
	
	@Override
	public ArrayList<SpecialAdditionalServiceDataM> loadOrigAdditionalService(ArrayList<String> personaIds) throws ApplicationException {
		if(Util.empty(personaIds))return new ArrayList<SpecialAdditionalServiceDataM>();
		Connection conn = null;
		try{
			conn = getConnection();
			return selectTableORIG_ADDITIONAL_SERVICE(personaIds,conn);
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

	private SpecialAdditionalServiceDataM selectTableORIG_ADDITIONAL_SERVICE(
			String serviceId) throws ApplicationException {
		PreparedStatement ps = null;
		ResultSet rs = null;
		Connection conn = null;
		try {
			//conn = Get Connection
			conn = getConnection();
			StringBuffer sql = new StringBuffer("");
			sql.append("SELECT SERVICE_ID, CURRENT_ACC_NAME, CURRENT_ACC_NO, SAVING_ACC_NAME, ");
			sql.append(" SAVING_ACC_NO, SERVICE_TYPE, COMPLETE_DATA, COMPLETE_DATA_SV_AC, ");
			sql.append(" CREATE_DATE,CREATE_BY,UPDATE_DATE,UPDATE_BY, PERSONAL_ID ");
			sql.append(" FROM ORIG_ADDITIONAL_SERVICE WHERE SERVICE_ID = ? ");
			String dSql = String.valueOf(sql);
			logger.debug("Sql=" + dSql);
			ps = conn.prepareStatement(dSql);
			rs = null;
			ps.setString(1, serviceId);

			rs = ps.executeQuery();

			if(rs.next()) {
				SpecialAdditionalServiceDataM addServiceDataM = new SpecialAdditionalServiceDataM();
				addServiceDataM.setServiceId(rs.getString("SERVICE_ID"));
				addServiceDataM.setCurrentAccName(rs.getString("CURRENT_ACC_NAME"));
				addServiceDataM.setCurrentAccNo(rs.getString("CURRENT_ACC_NO"));
				addServiceDataM.setSavingAccName(rs.getString("SAVING_ACC_NAME"));
				
				addServiceDataM.setSavingAccNo(rs.getString("SAVING_ACC_NO"));
				addServiceDataM.setServiceType(rs.getString("SERVICE_TYPE"));
				addServiceDataM.setCompleteData(rs.getString("COMPLETE_DATA"));
				addServiceDataM.setCompleteDataSaving(rs.getString("COMPLETE_DATA_SV_AC"));
				
				addServiceDataM.setCreateBy(rs.getString("CREATE_BY"));
				addServiceDataM.setCreateDate(rs.getTimestamp("CREATE_DATE"));
				addServiceDataM.setUpdateBy(rs.getString("UPDATE_BY"));
				addServiceDataM.setUpdateDate(rs.getTimestamp("UPDATE_DATE"));
				
				addServiceDataM.setPersonalId(rs.getString("PERSONAL_ID"));
				
				return addServiceDataM;
			}
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
		return null;
	}
	
	private ArrayList<SpecialAdditionalServiceDataM> selectTableORIG_ADDITIONAL_SERVICE(ArrayList<String> personalIds,Connection conn) throws ApplicationException {
		PreparedStatement ps = null;
		ResultSet rs = null;
		ArrayList<SpecialAdditionalServiceDataM> result = new ArrayList<SpecialAdditionalServiceDataM>();
		try{
			StringBuilder sql = new StringBuilder("");
			sql.append("SELECT SERVICE_ID, CURRENT_ACC_NAME, CURRENT_ACC_NO, SAVING_ACC_NAME, ");
			sql.append(" SAVING_ACC_NO, SERVICE_TYPE, COMPLETE_DATA, COMPLETE_DATA_SV_AC, ");
			sql.append(" CREATE_DATE, CREATE_BY, UPDATE_DATE, UPDATE_BY, PERSONAL_ID ");
			sql.append(" FROM ORIG_ADDITIONAL_SERVICE ");
			sql.append(" WHERE PERSONAL_ID IN (");
			sql.append(joinSQLString(personalIds));
			sql.append("  )");
			String dSql = String.valueOf(sql);
			logger.debug("Sql=" + dSql);
			ps = conn.prepareStatement(dSql);
			rs = null;			
			rs = ps.executeQuery();
			while(rs.next()) {
				SpecialAdditionalServiceDataM addServiceDataM = new SpecialAdditionalServiceDataM();
				addServiceDataM.setServiceId(rs.getString("SERVICE_ID"));
				addServiceDataM.setCurrentAccName(rs.getString("CURRENT_ACC_NAME"));
				addServiceDataM.setCurrentAccNo(rs.getString("CURRENT_ACC_NO"));
				addServiceDataM.setSavingAccName(rs.getString("SAVING_ACC_NAME"));
				
				addServiceDataM.setSavingAccNo(rs.getString("SAVING_ACC_NO"));
				addServiceDataM.setServiceType(rs.getString("SERVICE_TYPE"));
				addServiceDataM.setCompleteData(rs.getString("COMPLETE_DATA"));
				addServiceDataM.setCompleteDataSaving(rs.getString("COMPLETE_DATA_SV_AC"));
				
				addServiceDataM.setCreateBy(rs.getString("CREATE_BY"));
				addServiceDataM.setCreateDate(rs.getTimestamp("CREATE_DATE"));
				addServiceDataM.setUpdateBy(rs.getString("UPDATE_BY"));
				addServiceDataM.setUpdateDate(rs.getTimestamp("UPDATE_DATE"));
				
				addServiceDataM.setPersonalId(rs.getString("PERSONAL_ID"));
				
				result.add(addServiceDataM);
			}
		}catch(Exception e){
			logger.fatal(e.getLocalizedMessage());
			throw new ApplicationException(e.getMessage());
		}finally{
			try{
				closeConnection(rs, ps);
			}catch(Exception e){
				logger.fatal(e.getLocalizedMessage());
			}
		}
		return result;
	}

	@Override
	public void saveUpdateOrigAdditionalServiceM(SpecialAdditionalServiceDataM addServiceDataM)
			throws ApplicationException {
		Connection conn = null;
		try{
			conn = getConnection();
			saveUpdateOrigAdditionalServiceM(addServiceDataM, conn);
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
	public void saveUpdateOrigAdditionalServiceM(SpecialAdditionalServiceDataM addServiceDataM, Connection conn)
			throws ApplicationException {
		int returnRows = 0;
		addServiceDataM.setUpdateBy(userId);
		returnRows = updateTableORIG_ADDITIONAL_SERVICE(addServiceDataM,conn);
		if (returnRows == 0) {
			addServiceDataM.setCreateBy(userId);
			createOrigAdditionalServiceM(addServiceDataM,conn);
		}
	}
	private int updateTableORIG_ADDITIONAL_SERVICE(SpecialAdditionalServiceDataM addServiceDataM,Connection conn) throws ApplicationException {
		int returnRows = 0;
		PreparedStatement ps = null;
//		Connection conn = null;
		try {
			//conn = Get Connection
//			conn = getConnection();
			StringBuffer sql = new StringBuffer("");
			sql.append("UPDATE ORIG_ADDITIONAL_SERVICE ");
			sql.append(" SET CURRENT_ACC_NAME = ?, CURRENT_ACC_NO = ?, SAVING_ACC_NAME = ?, ");
			sql.append(" SAVING_ACC_NO = ?, SERVICE_TYPE = ?, COMPLETE_DATA = ?, ");
			sql.append(" COMPLETE_DATA_SV_AC = ?, UPDATE_BY = ?,UPDATE_DATE = ?, PERSONAL_ID = ? ");
			
			sql.append(" WHERE SERVICE_ID = ?");
			String dSql = String.valueOf(sql);
			logger.debug("Sql=" + dSql);
			ps = conn.prepareStatement(dSql);
			int cnt = 1;
			ps.setString(cnt++, addServiceDataM.getCurrentAccName());
			ps.setString(cnt++, addServiceDataM.getCurrentAccNo());
			ps.setString(cnt++, addServiceDataM.getSavingAccName());
			
			ps.setString(cnt++, addServiceDataM.getSavingAccNo());
			ps.setString(cnt++, addServiceDataM.getServiceType());
			ps.setString(cnt++, addServiceDataM.getCompleteData());
			
			ps.setString(cnt++, addServiceDataM.getCompleteDataSaving());
			ps.setString(cnt++, addServiceDataM.getUpdateBy());
			ps.setTimestamp(cnt++, ApplicationDate.getTimestamp());
			ps.setString(cnt++, addServiceDataM.getPersonalId());
			
			// WHERE CLAUSE
			ps.setString(cnt++, addServiceDataM.getServiceId());
			
			
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
	@Deprecated
	public void deleteNotInKeySpecialAdditionalServices(List<SpecialAdditionalServiceDataM> services, ApplicationGroupDataM appGroup) throws ApplicationException{
		if(services == null)return;
		//Prepare data
		Map<String, Set<String>> loanIdMap = new HashMap<String, Set<String>>();
		Map<String, Set<String>> personalIdMap = new HashMap<String, Set<String>>();
		for(SpecialAdditionalServiceDataM service : services){
			List<SpecialAdditionalServiceMapDataM> mappings = service.getSpecialAdditionalServiceMaps();
			if(mappings == null || mappings.isEmpty())continue;
			for(SpecialAdditionalServiceMapDataM mapping : mappings){
				String currentLoanId = mapping.getLoanId();
				String currentServiceId = service.getServiceId();
				
				//Map loan
				Set<String> loanServiceIds = loanIdMap.get(currentLoanId);
				if(loanServiceIds == null){
					loanServiceIds = new HashSet<String>();
					loanIdMap.put(currentLoanId, loanServiceIds);//Key : LoanId, Value : Service Id Set associated with the loan
				}
				loanServiceIds.add(currentServiceId);
				
				//Map personal
				Set<String> personalServiceIds = personalIdMap.get(currentLoanId);
				if(personalServiceIds == null){
					personalServiceIds = new HashSet<String>();
					personalIdMap.put(currentLoanId, personalServiceIds);//Key : PersonalId, Value : Service Id Set associated with the loan
				}
				personalServiceIds.add(currentServiceId);
			}
		}
		
		List<ApplicationDataM> activeApps = appGroup.getActiveApplications();
		//Start delete logic
		if(activeApps != null){
			for(ApplicationDataM app : activeApps){
				List<LoanDataM> loans = app.getLoans();
				if(loans == null || loans.isEmpty())continue;
				for(LoanDataM loan : loans){
					String loanId = loan.getLoanId();
					Set<String> serviceIds = loanIdMap.get(loanId);
					
					//Delete by loan
					deleteAdditionalServiceByLoanId(loanId, serviceIds);
				}
			}
		}
		
		//Delete inconsistency
		deleteAdditionalServiceByApplicationGroupId(appGroup.getApplicationGroupId(), loanIdMap.keySet());
		

	}
	
	private void deleteAdditionalServiceByLoanId(String loanId, Set<String> existingServiceIds) throws ApplicationException{
		if(loanId == null)return;
		StringBuilder sql = new StringBuilder();
		if(existingServiceIds == null || existingServiceIds.isEmpty()){
			sql.append("DELETE FROM ORIG_ADDITIONAL_SERVICE WHERE SERVICE_ID IN (");
			sql.append("	SELECT A.SERVICE_ID ");
			sql.append("	FROM ORIG_ADDITIONAL_SERVICE A ");
			sql.append("	JOIN ORIG_ADDITIONAL_SERVICE_MAP M ON A.SERVICE_ID = M.SERVICE_ID ");
			sql.append("	WHERE M.LOAN_ID = ? ");
			sql.append(")");
		}else{
			sql.append("DELETE FROM ORIG_ADDITIONAL_SERVICE WHERE SERVICE_ID IN (");
			sql.append("	SELECT A.SERVICE_ID ");
			sql.append("	FROM ORIG_ADDITIONAL_SERVICE A ");
			sql.append("	JOIN ORIG_ADDITIONAL_SERVICE_MAP M ON A.SERVICE_ID = M.SERVICE_ID ");
			sql.append("	WHERE M.LOAN_ID = ? AND M.SERVICE_ID NOT IN(");
			sql.append(joinSQLString(existingServiceIds));
			sql.append("	)");
			sql.append(")");
		}
		
		Connection conn = getConnection();
		PreparedStatement ps = null;
		try{
			logger.debug("deleteAdditionalServiceByLoanId() LoanId : "+loanId+" , SQL = "+sql.toString());
			ps = conn.prepareStatement(sql.toString());
			ps.setString(1, loanId);
			int result = ps.executeUpdate();
			logger.debug("deleteAdditionalServiceByLoanId() result : "+result);
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
	
	private void deleteAdditionalServiceByApplicationGroupId(String appGroupId, Set<String> existingLoanIds) throws ApplicationException{
		if(appGroupId == null)return;
		StringBuilder sql = new StringBuilder();
		if(existingLoanIds == null || existingLoanIds.isEmpty()){
			sql.append("DELETE FROM ORIG_ADDITIONAL_SERVICE WHERE SERVICE_ID IN (");
			sql.append("	SELECT A.SERVICE_ID ");
			sql.append("	FROM ORIG_ADDITIONAL_SERVICE A ");
			sql.append("	JOIN ORIG_ADDITIONAL_SERVICE_MAP M ON A.SERVICE_ID = M.SERVICE_ID ");
			sql.append("	JOIN ORIG_LOAN L ON M.LOAN_ID = L.LOAN_ID ");
			sql.append("	JOIN ORIG_APPLICATION A ON  L.APPLICATION_RECORD_ID = A.APPLICATION_RECORD_ID ");
			sql.append("	JOIN ORIG_APPLICATION_GROUP G ON A.APPLICATION_GROUP_ID = G.APPLICATION_GROUP_ID ");
			sql.append("	 WHERE G.APPLICATION_GROUP_ID = ? AND L.LOAN_ID NOT IN ( ");
			sql.append(joinSQLString(existingLoanIds));
			sql.append(")");
			sql.append(")");
		}
		
		Connection conn = getConnection();
		PreparedStatement ps = null;
		try{
			logger.debug("deleteAdditionalServiceByApplicationGroupId() appGroupId : "+appGroupId+" , SQL = "+sql.toString());
			ps = conn.prepareStatement(sql.toString());
			ps.setString(1, appGroupId);
			int result = ps.executeUpdate();
			logger.debug("deleteAdditionalServiceByApplicationGroupId() result : "+result);
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
	
	/**Query service ids and categorize by LoanId or PersonalId respectively,
	 * 
	 * @param appGroupId
	 * @return ServiceId Map
	 * @throws ApplicationException
	 */
	public Map<String,Map<String,Set<String>>> getServicesByApplicationGroupId(String appGroupId) throws ApplicationException{
		if(appGroupId == null)return null;
		String sql = "SELECT S.SERVICE_ID, S.SERVICE_TYPE, S.PERSONAL_ID, M.LOAN_ID, G.APPLICATION_GROUP_ID " +
				"FROM ORIG_ADDITIONAL_SERVICE S " +
				"LEFT JOIN ORIG_ADDITIONAL_SERVICE_MAP M ON S.SERVICE_ID = M.SERVICE_ID " +
				"JOIN ORIG_PERSONAL_INFO P ON S.PERSONAL_ID = P.PERSONAL_ID " +
				"JOIN ORIG_APPLICATION_GROUP G ON P.APPLICATION_GROUP_ID = G.APPLICATION_GROUP_ID "+
				"WHERE G.APPLICATION_GROUP_ID = ?";
		
		Connection conn = getConnection();
		PreparedStatement ps = null;
		ResultSet rs = null;
		Map<String,Map<String,Set<String>>> result = new HashMap<String, Map<String,Set<String>>>();
		Map<String,Set<String>> loanIdMap = new HashMap<String, Set<String>>();
		Map<String,Set<String>> personalIdMap = new HashMap<String, Set<String>>();
		result.put(LOAN_SERVICE_ID_KEY, loanIdMap);
		result.put(PERSONAL_SERVICE_ID_KEY, personalIdMap);
		try{
			logger.debug("getServicesByApplicationGroupId() appGroupId : "+appGroupId+" , SQL = "+sql);
			ps = conn.prepareStatement(sql);
			ps.setString(1, appGroupId);
			rs = ps.executeQuery();
			int num = 0;
			while(rs.next()){
				//Map Loan Id map
				String serviceId = rs.getString("SERVICE_ID");
				String loanId = rs.getString("LOAN_ID");
				String personalId = rs.getString("PERSONAL_ID");
				
				if(loanId != null){
					Set<String> serviceIds = loanIdMap.get(loanId);
					if(serviceIds == null){
						serviceIds = new HashSet<String>();
						loanIdMap.put(loanId, serviceIds);
					}
					serviceIds.add(serviceId);
				}
				
				//Map personal
				if(personalId == null){
					personalId = ""+(--num);//Dummy id which will be deleted in deletion process
				}
				Set<String> serviceIds = personalIdMap.get(personalId);
				if(serviceIds == null){
					serviceIds = new HashSet<String>();
					personalIdMap.put(personalId, serviceIds);
				}
				serviceIds.add(serviceId);
				
			}
		} catch (Exception e) {
			logger.error("ERROR >> ",e);
			throw new ApplicationException(e.getMessage());
		} finally {
			try {
				closeConnection(conn,rs,ps);
			} catch (Exception e) {
				logger.error("ERROR >> ",e);
				throw new ApplicationException(e.getMessage());
			}
		}
		return result;		
	}
	
	/**Get existing services from DB, if the process can't find matching service id the Data Model, then it will be deleted.
	 * 
	 * @param appGroup
	 * @return Service Id Set
	 * @throws ApplicationException
	 */
	public Set<String> getToDeleteSpecialAdditionalService(ApplicationGroupDataM appGroup) throws ApplicationException{
		//Validation
		if(appGroup == null)return null;
		Map<String,Map<String,Set<String>>> existingAdditionalServices = getServicesByApplicationGroupId(appGroup.getApplicationGroupId());
		if(existingAdditionalServices == null || existingAdditionalServices.isEmpty()){
			return null;
		}
		
		Map<String,Set<String>> existingPersonaIdMap = existingAdditionalServices.get(PERSONAL_SERVICE_ID_KEY);//From DB
		Map<String,Set<String>> existingLoanIdMap = existingAdditionalServices.get(LOAN_SERVICE_ID_KEY);//From DB
		if((existingPersonaIdMap == null || existingPersonaIdMap.isEmpty())
				&& (existingLoanIdMap == null || existingLoanIdMap.isEmpty()))return null; //Nothing to delete
		
		//Prepare data
		Set<String> serviceIdsToDelete = new HashSet<String>();
		List<SpecialAdditionalServiceDataM> services = appGroup.getSpecialAdditionalServices();
		if(logger.isDebugEnabled())
			logger.debug("getToDeleteSpecialAdditionalService() Services in model : "+services);
		if(services != null && !services.isEmpty()){
			Map<String, Set<String>> loanIdMap = new HashMap<String, Set<String>>();//From Data Model
			Map<String, Set<String>> personalIdMap = new HashMap<String, Set<String>>();//From Data Model
			for(SpecialAdditionalServiceDataM service : services){
				List<SpecialAdditionalServiceMapDataM> mappings = service.getSpecialAdditionalServiceMaps();
				if(mappings == null || mappings.isEmpty())continue;
				for(SpecialAdditionalServiceMapDataM mapping : mappings){
					String currentLoanId = mapping.getLoanId();
					String currentServiceId = service.getServiceId();
					
					//Map loan
					Set<String> loanServiceIds = loanIdMap.get(currentLoanId);
					if(loanServiceIds == null){
						loanServiceIds = new HashSet<String>();
						loanIdMap.put(currentLoanId, loanServiceIds);//Key : LoanId, Value : Service Id Set associated with the loan
					}
					loanServiceIds.add(currentServiceId);
					
					//Map personal
					Set<String> personalServiceIds = personalIdMap.get(currentLoanId);
					if(personalServiceIds == null){
						personalServiceIds = new HashSet<String>();
						personalIdMap.put(currentLoanId, personalServiceIds);//Key : PersonalId, Value : Service Id Set associated with the loan
					}
					personalServiceIds.add(currentServiceId);
				}
			}
			
			//Synchronize loan data (Verify existing data whether to be deleted or not)
			for(Map.Entry<String,Set<String>> entry : existingLoanIdMap.entrySet()){
				String loanId = entry.getKey();
				Set<String> serviceIds = entry.getValue();
				if(serviceIds == null)continue;
				
				Set<String> modelServiceIds = loanIdMap.get(loanId);
				if(modelServiceIds == null || modelServiceIds.isEmpty()){
					//Delete all
					serviceIdsToDelete.addAll(serviceIds);
				}
				else{
					for(String id : serviceIds){
						if(id != null && !modelServiceIds.contains(id)){
							serviceIdsToDelete.add(id);
						}
					}
				}
				
			}
			
			//Synchronize personal data (Verify existing data whether to be deleted or not)
			for(Map.Entry<String,Set<String>> entry : existingPersonaIdMap.entrySet()){
				String personalId = entry.getKey();
				Set<String> serviceIds = entry.getValue();
				if(serviceIds == null)continue;
				
				Set<String> modelServiceIds = personalIdMap.get(personalId);
				if(modelServiceIds == null || modelServiceIds.isEmpty()){
					//Delete all
					serviceIdsToDelete.addAll(serviceIds);
				}
				else{
					for(String id : serviceIds){
						if(id != null && !modelServiceIds.contains(id)){
							serviceIdsToDelete.add(id);
						}
					}
				}
				
			}
		}else{
			//Delete all
			//Delete existing loan id
			if(existingLoanIdMap != null){
				for(Map.Entry<String,Set<String>> entry : existingLoanIdMap.entrySet()){
					Set<String> serviceIds = entry.getValue();
					if(serviceIds != null && !serviceIds.isEmpty()){
						serviceIdsToDelete.addAll(serviceIds);
					}
				}
			}
			
			//Delete existing personal id
			if(existingPersonaIdMap != null){
				for(Map.Entry<String,Set<String>> entry : existingPersonaIdMap.entrySet()){
					Set<String> serviceIds = entry.getValue();
					if(serviceIds != null && !serviceIds.isEmpty()){
						serviceIdsToDelete.addAll(serviceIds);
					}
				}
			}

		}
		return serviceIdsToDelete;
	}
	
	public void deleteSpecialAdditionalServiceById(Collection<String> serviceIds) throws ApplicationException{
		Connection conn = getConnection();
		PreparedStatement ps = null;
		StringBuilder sql = new StringBuilder();
		sql.append("DELETE ORIG_ADDITIONAL_SERVICE WHERE SERVICE_ID IN (");
		sql.append(joinSQLString(serviceIds));
		sql.append(")");
		try{
			logger.debug("deleteSpecialAdditionalServiceById()  SQL = "+sql.toString());
			ps = conn.prepareStatement(sql.toString());
			int result = ps.executeUpdate();
			logger.debug("deleteSpecialAdditionalServiceById() result : "+result);
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
	public void preDeleteSpecialAdditionalServices(ApplicationGroupDataM appGroup) throws ApplicationException{
		if(appGroup == null)return;
		Set<String> toDeleteServiceIds = getToDeleteSpecialAdditionalService(appGroup);
		if(toDeleteServiceIds == null )return;
		deleteSpecialAdditionalServiceById(toDeleteServiceIds);
	}

}
