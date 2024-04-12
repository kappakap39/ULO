package com.eaf.orig.ulo.app.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import org.apache.log4j.Logger;

import com.eaf.core.ulo.common.date.ApplicationDate;
import com.eaf.core.ulo.common.properties.SystemConstant;
import com.eaf.core.ulo.common.util.Util;
import com.eaf.orig.shared.dao.OrigObjectDAO;
import com.eaf.orig.ulo.app.dao.exception.ApplicationException;
import com.eaf.orig.ulo.model.app.ComparisonGroupDataM;
import com.eaf.orig.ulo.model.compare.CompareDataM;
import com.google.gson.Gson;

public class OrigComparisionDataDAOImpl extends OrigObjectDAO implements OrigComparisionDataDAO  {
	private static transient Logger logger = Logger.getLogger(OrigComparisionDataDAOImpl.class);
	public OrigComparisionDataDAOImpl(){
		
	}
	public OrigComparisionDataDAOImpl(String userId){
		this.userId = userId;
	}
	private String userId = "";
	@Override
	public void saveUpdateOrigComparisionData(CompareDataM compareData) throws ApplicationException {		
		int returnRows = updateTableORIG_COMPARISON_DATA(compareData);
		if(returnRows == 0){
			insertTableORIG_COMPARISON_DATA(compareData);
		}
	}
	private void insertTableORIG_COMPARISON_DATA(CompareDataM compareData) throws ApplicationException {
		PreparedStatement ps = null;
		Connection conn = null;
		try {
			//conn = Get Connection
			conn = getConnection();
			StringBuffer sql = new StringBuffer("");
			sql.append("INSERT INTO ORIG_COMPARISON_DATA ");
			sql.append("( APPLICATION_GROUP_ID, ROLE, FIELD_NAME, FIELD_NAME_TYPE, ");
			sql.append(" VALUE, COMPARE_FLAG, SRC_OF_DATA, REF_ID, REF_LEVEL, ");
			sql.append(" CREATE_DATE, CREATE_BY, UPDATE_DATE, UPDATE_BY, LIFE_CYCLE, ");
			sql.append(" UNIQUE_ID, UNIQUE_LEVEL, CONFIG_DATA ) ");

			sql.append(" VALUES(?,?,?,?,  ?,?,?,?,?,  ?,?,?,?,?, ?,?,? )");
			String dSql = String.valueOf(sql);
			logger.debug("Sql=" + dSql);
			ps = conn.prepareStatement(dSql);
			int cnt = 1;
			ps.setString(cnt++, compareData.getApplicationGroupId());		
			ps.setString(cnt++, compareData.getRole());
			ps.setString(cnt++, compareData.getFieldName());
			ps.setString(cnt++, compareData.getFieldNameType());
			
			ps.setString(cnt++, compareData.getValue());
			ps.setString(cnt++, compareData.getCompareFlag());
			ps.setString(cnt++, compareData.getSrcOfData());
			ps.setString(cnt++, compareData.getRefId());
			ps.setString(cnt++, compareData.getRefLevel());
			
			ps.setTimestamp(cnt++, ApplicationDate.getTimestamp());
			if(Util.empty(compareData.getCreateBy())){
				compareData.setCreateBy(userId);
			}
			ps.setString(cnt++, compareData.getCreateBy());
			ps.setTimestamp(cnt++, ApplicationDate.getTimestamp());
			ps.setString(cnt++, userId);
			ps.setInt(cnt++, compareData.getLifeCycle());
			
			ps.setString(cnt++, compareData.getUniqueId());
			ps.setString(cnt++, compareData.getUniqueLevel());
			ps.setString(cnt++, compareData.getConfigData());
			
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

	private int updateTableORIG_COMPARISON_DATA(CompareDataM compareData) throws ApplicationException {
		int returnRows = 0;
		PreparedStatement ps = null;
		Connection conn = null;
		String srcOfData = compareData.getSrcOfData();
		try {
			//conn = Get Connection
			conn = getConnection();
			StringBuffer sql = new StringBuffer("");
			sql.append("UPDATE ORIG_COMPARISON_DATA ");
			sql.append(" SET VALUE = ?, COMPARE_FLAG = ?, REF_ID = ?, REF_LEVEL = ?, ");
			sql.append(" UPDATE_BY = ?, UPDATE_DATE = ?,");		
			sql.append(" UNIQUE_ID = ?, UNIQUE_LEVEL = ?, CONFIG_DATA=? ");		
			
			sql.append(" WHERE APPLICATION_GROUP_ID = ? "+
					((CompareDataM.SoruceOfData.TWO_MAKER.equals(srcOfData))?" AND ROLE = ? ":"")+
					" AND FIELD_NAME = ? AND SRC_OF_DATA = ? ");
			
			if(isLifeCycleCondition(srcOfData)){
				sql.append("AND LIFE_CYCLE = ?");
			}
			
			String dSql = String.valueOf(sql);
			logger.debug("Sql=" + dSql);
			ps = conn.prepareStatement(dSql);
			int cnt = 1;
			ps.setString(cnt++, compareData.getValue());
			ps.setString(cnt++, compareData.getCompareFlag());
			ps.setString(cnt++, compareData.getRefId());
			ps.setString(cnt++, compareData.getRefLevel());
			if(Util.empty(compareData.getUpdateBy())){
				compareData.setUpdateBy(userId);
			}
			ps.setString(cnt++, compareData.getUpdateBy());
			ps.setTimestamp(cnt++, ApplicationDate.getTimestamp());
			
			ps.setString(cnt++, compareData.getUniqueId());
			ps.setString(cnt++, compareData.getUniqueLevel());
			ps.setString(cnt++, compareData.getConfigData());
			
			
			// WHERE CLAUSE
			ps.setString(cnt++, compareData.getApplicationGroupId());
			if(CompareDataM.SoruceOfData.TWO_MAKER.equals(srcOfData)){
				ps.setString(cnt++, compareData.getRole());
			}
			ps.setString(cnt++, compareData.getFieldName());
			ps.setString(cnt++, srcOfData);
			
			if(isLifeCycleCondition(srcOfData)){
				ps.setInt(cnt++, compareData.getLifeCycle());
			}
			
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
	public ComparisonGroupDataM loadComparisonGroup(String applicationGroupId,String srcOfData) throws ApplicationException {
		ComparisonGroupDataM comparisonGroup = new ComparisonGroupDataM(srcOfData);
		HashMap<String, CompareDataM> comparisonFields = new HashMap<String, CompareDataM>();
			comparisonGroup.setComparisonFields(comparisonFields);
		PreparedStatement ps = null;
		ResultSet rs = null;
		Connection conn = null;
		try {
			conn = getConnection();
			StringBuffer sql = new StringBuffer();
			sql.append("SELECT * FROM ORIG_COMPARISON_DATA ");
			sql.append(" WHERE APPLICATION_GROUP_ID = ? AND SRC_OF_DATA = ? ");		
			String dSql = String.valueOf(sql);
			logger.debug("Sql=" + dSql);
			ps = conn.prepareStatement(dSql);
			int index = 1;
			ps.setString(index++, applicationGroupId);	
			ps.setString(index++, srcOfData);
			rs = ps.executeQuery();			
			while(rs.next()) {
				CompareDataM compareData = new CompareDataM();		
				String FIELD_NAME = rs.getString("FIELD_NAME");
					compareData.setApplicationGroupId(rs.getString("APPLICATION_GROUP_ID"));
					compareData.setRole(rs.getString("ROLE"));
					compareData.setValue(rs.getString("VALUE"));
					/*if(!CompareDataM.SoruceOfData.CIS.equals(srcOfData)){
						compareData.setCompareFlag(rs.getString("COMPARE_FLAG"));
					}*/
					compareData.setCompareFlag(rs.getString("COMPARE_FLAG"));
					compareData.setFieldName(FIELD_NAME);
					compareData.setSrcOfData(rs.getString("SRC_OF_DATA"));
					compareData.setRefId(rs.getString("REF_ID"));
					compareData.setRefLevel(rs.getString("REF_LEVEL"));
					compareData.setFieldNameType(rs.getString("FIELD_NAME_TYPE"));
					compareData.setUpdateBy(rs.getString("UPDATE_BY"));
					compareData.setCreateBy(rs.getString("CREATE_BY"));
					compareData.setUniqueId(rs.getString("UNIQUE_ID"));
					compareData.setUniqueLevel(rs.getString("UNIQUE_LEVEL"));
					compareData.setConfigData(rs.getString("CONFIG_DATA"));
				comparisonFields.put(FIELD_NAME, compareData);
			}			
		} catch (Exception e) {
			logger.fatal("ERROR ",e);
			throw new ApplicationException(e.getMessage());
		} finally {
			try {
				closeConnection(conn, rs, ps);
			} catch (Exception e) {
				logger.fatal("ERROR ",e);
			}
		}
		return comparisonGroup;
	}
	private boolean isLifeCycleCondition(String srcOfData){
		return CompareDataM.SoruceOfData.TWO_MAKER.equals(srcOfData)||CompareDataM.SoruceOfData.PREV_ROLE.equals(srcOfData);
	}
	@Override
	public ComparisonGroupDataM loadComparisonGroup(String applicationGroupId,String srcOfData, String roleId,int lifeCycle) throws ApplicationException {
		ComparisonGroupDataM comparisonGroup = new ComparisonGroupDataM(srcOfData);
		HashMap<String, CompareDataM> comparisonFields = new HashMap<String, CompareDataM>();
			comparisonGroup.setComparisonFields(comparisonFields);
		PreparedStatement ps = null;
		ResultSet rs = null;
		Connection conn = null;
		try {
			conn = getConnection();
			StringBuffer sql = new StringBuffer();
			sql.append("SELECT * FROM ORIG_COMPARISON_DATA ");
			sql.append(" WHERE APPLICATION_GROUP_ID = ? AND SRC_OF_DATA = ? AND ROLE = ? ");
			
			if(isLifeCycleCondition(srcOfData)){
				sql.append(" AND LIFE_CYCLE = ? ");
			}
			
			String dSql = String.valueOf(sql);
			logger.debug("Sql=" + dSql);
			ps = conn.prepareStatement(dSql);
			int index = 1;
			ps.setString(index++, applicationGroupId);	
			ps.setString(index++, srcOfData);	
			ps.setString(index++, roleId);
			
			if(isLifeCycleCondition(srcOfData)){
				ps.setInt(index++, lifeCycle);	
			}
			
			rs = ps.executeQuery();			
			while(rs.next()) {				
				String FIELD_NAME = rs.getString("FIELD_NAME");
				CompareDataM compareData = new CompareDataM();	
					compareData.setApplicationGroupId(rs.getString("APPLICATION_GROUP_ID"));
					compareData.setRole(rs.getString("ROLE"));
					compareData.setValue(rs.getString("VALUE"));
					compareData.setCompareFlag(rs.getString("COMPARE_FLAG"));
					compareData.setFieldName(FIELD_NAME);
					compareData.setSrcOfData(rs.getString("SRC_OF_DATA"));
					compareData.setRefId(rs.getString("REF_ID"));
					compareData.setRefLevel(rs.getString("REF_LEVEL"));
					compareData.setFieldNameType(rs.getString("FIELD_NAME_TYPE"));
					compareData.setUpdateBy(rs.getString("UPDATE_BY"));
					compareData.setCreateBy(rs.getString("CREATE_BY"));
					compareData.setLifeCycle(rs.getInt("LIFE_CYCLE"));
					compareData.setUniqueId(rs.getString("UNIQUE_ID"));
					compareData.setUniqueLevel(rs.getString("UNIQUE_LEVEL"));
					compareData.setConfigData(rs.getString("CONFIG_DATA"));
				comparisonFields.put(FIELD_NAME, compareData);
			}			
		} catch (Exception e) {
			logger.fatal("ERROR ",e);
			throw new ApplicationException(e.getMessage());
		} finally {
			try {
				closeConnection(conn, rs, ps);
			} catch (Exception e) {
				logger.fatal("ERROR ",e);
			}
		}
		return comparisonGroup;
	}
	@Override
	public ArrayList<CompareDataM> loadComparison(String applicationGroupId,String srcOfData, String roleId,int lifeCycle) throws ApplicationException {
		ArrayList<CompareDataM> compares = new ArrayList<CompareDataM>();
		PreparedStatement ps = null;
		ResultSet rs = null;
		Connection conn = null;
		try {
			conn = getConnection();
			StringBuffer sql = new StringBuffer();
			sql.append("SELECT * FROM ORIG_COMPARISON_DATA ");
			sql.append(" WHERE APPLICATION_GROUP_ID = ? AND SRC_OF_DATA = ? AND ROLE = ? ");	
			
			if(isLifeCycleCondition(srcOfData)){
				sql.append(" AND LIFE_CYCLE = ? ");
			}
			
			String dSql = String.valueOf(sql);
			logger.debug("Sql=" + dSql);
			ps = conn.prepareStatement(dSql);
			int index = 1;
			ps.setString(index++, applicationGroupId);	
			ps.setString(index++, srcOfData);	
			ps.setString(index++, roleId);	
			
			if(isLifeCycleCondition(srcOfData)){
				ps.setInt(index++, lifeCycle);	
			}	
			
			rs = ps.executeQuery();			
			while(rs.next()) {				
				String FIELD_NAME = rs.getString("FIELD_NAME");
				CompareDataM compareData = new CompareDataM();	
					compareData.setApplicationGroupId(rs.getString("APPLICATION_GROUP_ID"));
					compareData.setRole(rs.getString("ROLE"));
					compareData.setValue(rs.getString("VALUE"));
					compareData.setCompareFlag(rs.getString("COMPARE_FLAG"));
					compareData.setFieldName(FIELD_NAME);
					compareData.setSrcOfData(rs.getString("SRC_OF_DATA"));
					compareData.setRefId(rs.getString("REF_ID"));
					compareData.setRefLevel(rs.getString("REF_LEVEL"));
					compareData.setFieldNameType(rs.getString("FIELD_NAME_TYPE"));
					compareData.setUpdateBy(rs.getString("UPDATE_BY"));
					compareData.setCreateBy(rs.getString("CREATE_BY"));
					compareData.setLifeCycle(rs.getInt("LIFE_CYCLE"));
					compareData.setUniqueId(rs.getString("UNIQUE_ID"));
					compareData.setUniqueLevel(rs.getString("UNIQUE_LEVEL"));
					compareData.setConfigData(rs.getString("CONFIG_DATA"));
				compares.add(compareData);
			}			
		} catch (Exception e) {
			logger.fatal("ERROR ",e);
			throw new ApplicationException(e.getMessage());
		} finally {
			try {
				closeConnection(conn, rs, ps);
			} catch (Exception e) {
				logger.fatal("ERROR ",e);
			}
		}
		return compares;
	}
	@Override
	public void deleteComparisonDataForRoleForGroup(String applicationGroupId,String srcOfData, String roleId,int lifeCycle) throws ApplicationException {
		PreparedStatement ps = null;
		Connection conn = null;
		try {
			//conn = Get Connection
			conn = getConnection();
			StringBuffer sql = new StringBuffer("");
			sql.append("DELETE FROM ORIG_COMPARISON_DATA ");
			sql.append(" WHERE APPLICATION_GROUP_ID = ? AND SRC_OF_DATA = ? AND ROLE = ? ");	
			
			if(isLifeCycleCondition(srcOfData)){
				sql.append(" AND LIFE_CYCLE = ? ");
			}
			
			String dSql = String.valueOf(sql);
			logger.debug("Sql=" + dSql);
			ps = conn.prepareStatement(dSql);
			int index = 1;
			ps.setString(index++, applicationGroupId);	
			ps.setString(index++, srcOfData);	
			ps.setString(index++, roleId);	
			
			if(isLifeCycleCondition(srcOfData)){
				ps.setInt(index++, lifeCycle);	
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
	public void deleteComparisonDataNotMatchUniqueId(String applicationId,String srcOfData, ArrayList<String> refIds,int lifeCycle)	throws ApplicationException {
		PreparedStatement ps = null;
		Connection conn = null;
		try {
			//conn = Get Connection
			conn = getConnection();
			StringBuffer sql = new StringBuffer("");
			sql.append("DELETE FROM ORIG_COMPARISON_DATA ");
			sql.append(" WHERE APPLICATION_GROUP_ID = ? AND SRC_OF_DATA = ? ");		
			if(!Util.empty(refIds)){
				sql.append(" AND REF_ID NOT IN (");
				String COMMA = "";
				for (String refId : refIds) {
					sql.append(COMMA+"'"+refId+"'");
					COMMA = ",";
				}
				sql.append(")");
			}
			
			if(isLifeCycleCondition(srcOfData)){
				sql.append(" AND LIFE_CYCLE = ? ");
			}
			
			String dSql = String.valueOf(sql);
			logger.debug("Sql=" + dSql);
			ps = conn.prepareStatement(dSql);
			int index = 1;
			ps.setString(index++, applicationId);	
			ps.setString(index++, srcOfData);	
			
			if(isLifeCycleCondition(srcOfData)){
				ps.setInt(index++, lifeCycle);	
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
	public boolean isContainIASuplementtary(String applicationGroupId,String refLevel, String srcOfData,int lifeCycle)throws ApplicationException {
		PreparedStatement ps = null;
		ResultSet rs = null;
		Connection conn = null;
		try {
			conn = getConnection();
			StringBuffer sql = new StringBuffer();
			sql.append("SELECT COUNT(1) AS COUNT_SUP  FROM ORIG_COMPARISON_DATA");
			sql.append(" WHERE  SUBSTR(REF_ID,1,INSTR(REF_ID,'_')-1) = ? AND APPLICATION_GROUP_ID = ? AND SRC_OF_DATA = ? AND ROLE = ? AND REF_LEVEL=?");	
			if(isLifeCycleCondition(srcOfData)){
				sql.append(" AND LIFE_CYCLE = ? ");
			}
			String dSql = String.valueOf(sql);
			logger.debug("Sql=" + dSql);
			ps = conn.prepareStatement(dSql);
			int index = 1;
			ps.setString(index++, SystemConstant.getConstant("PERSONAL_TYPE_SUPPLEMENTARY"));	
			ps.setString(index++, applicationGroupId);	
			ps.setString(index++, srcOfData);	
			ps.setString(index++, SystemConstant.getConstant("ROLE_IA"));	
			ps.setString(index++, refLevel);
			if(isLifeCycleCondition(srcOfData)){
				ps.setInt(index++, lifeCycle);	
			}
			rs = ps.executeQuery();			
			if(rs.next()) {				
				return rs.getInt("COUNT_SUP")>0;
			}			
		} catch (Exception e) {
			logger.fatal("ERROR ",e);
			throw new ApplicationException(e.getMessage());
		} finally {
			try {
				closeConnection(conn, rs, ps);
			} catch (Exception e) {
				logger.fatal("ERROR ",e);
			}
		}
		return false;
	}
	@Override
	public void deleteComparisonDataNotMatchCIS_NUMBER(String applicationId,String srcOfData,String REF_ID,int lifeCycle)throws ApplicationException {

		PreparedStatement ps = null;
		Connection conn = null;
		try {
			//conn = Get Connection
			conn = getConnection();
			StringBuffer sql = new StringBuffer("");
			sql.append("DELETE FROM ORIG_COMPARISON_DATA ");
			sql.append(" WHERE APPLICATION_GROUP_ID = ? AND SRC_OF_DATA = ? AND REF_ID = ? ");			
			if(isLifeCycleCondition(srcOfData)){
				sql.append(" AND LIFE_CYCLE = ? ");
			}
			
			String dSql = String.valueOf(sql);
			logger.debug("Sql=" + dSql);
			ps = conn.prepareStatement(dSql);
			int index = 1;
			ps.setString(index++, applicationId);	
			ps.setString(index++, srcOfData);	
			ps.setString(index++, REF_ID);
			
			if(isLifeCycleCondition(srcOfData)){
				ps.setInt(index++, lifeCycle);	
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
	public Map<String,ComparisonGroupDataM> loadComparisonGroupData(String applicationGroupId,String roleId,String prevRoleId,int lifeCycle) throws ApplicationException {
		Map<String,ComparisonGroupDataM> comparisonGroupData = new HashMap<String,ComparisonGroupDataM>();
		PreparedStatement ps = null;
		ResultSet rs = null;
		Connection conn = null;
		try{
			conn = getConnection();
			StringBuffer sql = new StringBuffer();
			sql.append("SELECT * FROM ORIG_COMPARISON_DATA ");		
			sql.append(" WHERE APPLICATION_GROUP_ID = ? ");
			sql.append(" AND ((SRC_OF_DATA = ? AND ROLE = ? AND LIFE_CYCLE = ?) ");
			if(!Util.empty(prevRoleId))
				sql.append(" OR (SRC_OF_DATA = ? AND ROLE = ? AND LIFE_CYCLE = ?) ");
			if(!SystemConstant.getConstant("ROLE_IA").equals(roleId))
				sql.append(" OR (SRC_OF_DATA = ? AND ROLE = ? AND LIFE_CYCLE = ?) ");
				sql.append(" OR SRC_OF_DATA IN(?,?))");
			String dSql = String.valueOf(sql);
			logger.debug("Sql=" + dSql);
			ps = conn.prepareStatement(dSql);
			int index = 1;
			ps.setString(index++,applicationGroupId);	
			ps.setString(index++, CompareDataM.SoruceOfData.TWO_MAKER);	
			ps.setString(index++, roleId);
			ps.setInt(index++, lifeCycle);
			if(!Util.empty(prevRoleId)){
				ps.setString(index++, CompareDataM.SoruceOfData.TWO_MAKER);	
				ps.setString(index++, prevRoleId);
				ps.setInt(index++, lifeCycle);
			}
			if(!SystemConstant.getConstant("ROLE_IA").equals(roleId)){
				ps.setString(index++, CompareDataM.SoruceOfData.TWO_MAKER);	
				ps.setString(index++, SystemConstant.getConstant("ROLE_IA"));
				ps.setInt(index++, lifeCycle);
			}
			ps.setString(index++, CompareDataM.SoruceOfData.CIS);
			ps.setString(index++, CompareDataM.SoruceOfData.CARD_LINK);
			
			rs = ps.executeQuery();			
			while(rs.next()) {
				String SRC_OF_DATA = rs.getString("SRC_OF_DATA");
				String ROLE_ID = rs.getString("ROLE");
				if(CompareDataM.SoruceOfData.TWO_MAKER.equals(SRC_OF_DATA)){
					if(!SystemConstant.getConstant("ROLE_IA").equals(roleId)&&SystemConstant.getConstant("ROLE_IA").equals(ROLE_ID)){
						SRC_OF_DATA = CompareDataM.SoruceOfData.IA_TWO_MAKER;
						createComparisonGroupData(comparisonGroupData,SRC_OF_DATA,rs);
					}
				}
				if(CompareDataM.SoruceOfData.TWO_MAKER.equals(rs.getString("SRC_OF_DATA"))){
					if(null!=ROLE_ID&&ROLE_ID.equals(prevRoleId)){
						createComparisonGroupData(comparisonGroupData,CompareDataM.SoruceOfData.PREV_ROLE,rs);
					}else if(null!=ROLE_ID&&ROLE_ID.equals(roleId)){
						createComparisonGroupData(comparisonGroupData,SRC_OF_DATA,rs);
					}
				}else{
					createComparisonGroupData(comparisonGroupData,SRC_OF_DATA,rs);
				}
			}			
		}catch(Exception e){
			logger.fatal("ERROR ",e);
			throw new ApplicationException(e.getMessage());
		}finally{
			try{
				closeConnection(conn, rs, ps);
			}catch(Exception e){
				logger.fatal("ERROR ",e);
			}
		}
		return comparisonGroupData;
	}
	
	public void createComparisonGroupData(Map<String,ComparisonGroupDataM> comparisonGroupData,String SRC_OF_DATA,ResultSet rs) throws SQLException{
		ComparisonGroupDataM comparisonGroup = comparisonGroupData.get(SRC_OF_DATA);
		if(null==comparisonGroup){
			comparisonGroup = new ComparisonGroupDataM(SRC_OF_DATA);
			comparisonGroupData.put(SRC_OF_DATA, comparisonGroup);
		}
		HashMap<String, CompareDataM> comparisonFields = comparisonGroup.getComparisonFields();
		if(null==comparisonFields){
			comparisonFields = new HashMap<String, CompareDataM>();
			comparisonGroup.setComparisonFields(comparisonFields);
		}
		CompareDataM compareData = new CompareDataM();	
		String FIELD_NAME = rs.getString("FIELD_NAME");
			compareData.setApplicationGroupId(rs.getString("APPLICATION_GROUP_ID"));
			compareData.setRole(rs.getString("ROLE"));
			compareData.setValue(rs.getString("VALUE"));
			compareData.setCompareFlag(rs.getString("COMPARE_FLAG"));
			compareData.setFieldName(FIELD_NAME);
			compareData.setSrcOfData(SRC_OF_DATA);
			compareData.setRefId(rs.getString("REF_ID"));
			compareData.setRefLevel(rs.getString("REF_LEVEL"));
			compareData.setFieldNameType(rs.getString("FIELD_NAME_TYPE"));
			compareData.setUpdateBy(rs.getString("UPDATE_BY"));
			compareData.setCreateBy(rs.getString("CREATE_BY"));
			compareData.setUniqueId(rs.getString("UNIQUE_ID"));
			compareData.setUniqueLevel(rs.getString("UNIQUE_LEVEL"));
			compareData.setConfigData(rs.getString("CONFIG_DATA"));
			comparisonFields.put(FIELD_NAME, compareData);
	}
	@Override
	public void deleteOrigComparisionData(String applicationGroupId,
			String roleId, int lifeCycle) throws ApplicationException {
		Connection conn = null;
		try{
			conn = getConnection();
			deleteOrigComparisionData(applicationGroupId, roleId, lifeCycle, conn);
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
	public void deleteOrigComparisionData(String applicationGroupId,String roleId, int lifeCycle,Connection conn) throws ApplicationException {
		PreparedStatement ps = null;
//		Connection conn = null;
		try{
//			conn = getConnection();
			StringBuilder sql = new StringBuilder("");
			sql.append("DELETE FROM ORIG_COMPARISON_DATA ");
			sql.append(" WHERE APPLICATION_GROUP_ID = ? AND ((SRC_OF_DATA = ? AND ROLE = ? AND LIFE_CYCLE = ?) OR SRC_OF_DATA IN(?,?))");
			logger.debug("Sql=" + sql);
			ps = conn.prepareStatement(sql.toString());
			int index = 1;
			ps.setString(index++, applicationGroupId);	
			ps.setString(index++, CompareDataM.SoruceOfData.TWO_MAKER);	
			ps.setString(index++, roleId);
			ps.setInt(index++, lifeCycle);
			ps.setString(index++, CompareDataM.SoruceOfData.CIS);
			ps.setString(index++, CompareDataM.SoruceOfData.CARD_LINK);
			ps.executeUpdate();
		}catch (Exception e){
			logger.fatal("ERROR",e);
			throw new ApplicationException(e.getMessage());
		}finally{
			try{
//				closeConnection(conn, ps);
				closeConnection(ps);
			}catch(Exception e){
				logger.fatal("ERROR",e);
				throw new ApplicationException(e.getMessage());
			}
		}
	}
	@Override
	public void saveUpdateOrigComparisionData(ArrayList<ComparisonGroupDataM> comparisonGroups,String applicationGroupId,int lifeCycle,Connection conn)throws ApplicationException{
		PreparedStatement ps = null;
//		Connection conn = null;
		try{
//			conn = getConnection();
			StringBuilder sql = new StringBuilder("");
			sql.append("INSERT INTO ORIG_COMPARISON_DATA ");
			sql.append("( APPLICATION_GROUP_ID, ROLE, FIELD_NAME, FIELD_NAME_TYPE, ");
			sql.append(" VALUE, COMPARE_FLAG, SRC_OF_DATA, REF_ID, REF_LEVEL, ");
			sql.append(" CREATE_DATE, CREATE_BY, UPDATE_DATE, UPDATE_BY, LIFE_CYCLE, ");
			sql.append(" UNIQUE_ID, UNIQUE_LEVEL, CONFIG_DATA ) ");
			sql.append(" VALUES(?,?,?,?,  ?,?,?,?,?,  ?,?,?,?,?, ?,?,? )");
			String dSql = String.valueOf(sql);
			logger.debug("Sql=" + dSql);
			ps = conn.prepareStatement(dSql);
			if(null!=comparisonGroups){
				for (ComparisonGroupDataM comparisonGroup : comparisonGroups) {
					if(!CompareDataM.SoruceOfData.PREV_ROLE.equals(comparisonGroup.getSrcOfData())
							&&!CompareDataM.SoruceOfData.IA_TWO_MAKER.equals(comparisonGroup.getSrcOfData())){
						HashMap<String,CompareDataM> comparisonFields = comparisonGroup.getComparisonFields();
						for (CompareDataM compareData : comparisonFields.values()) {
							if(!CompareDataM.CompareDataType.DUMMY.equals(compareData.getCompareDataType())){
								int cnt = 1;
								ps.setString(cnt++, applicationGroupId);		
								ps.setString(cnt++, compareData.getRole());
								ps.setString(cnt++, compareData.getFieldName());
								ps.setString(cnt++, compareData.getFieldNameType());
								ps.setString(cnt++, compareData.getValue());
								ps.setString(cnt++, compareData.getCompareFlag());
								ps.setString(cnt++, compareData.getSrcOfData());
								ps.setString(cnt++, compareData.getRefId());
								ps.setString(cnt++, compareData.getRefLevel());
								ps.setTimestamp(cnt++, ApplicationDate.getTimestamp());
								if(Util.empty(compareData.getCreateBy())){
									compareData.setCreateBy(userId);
								}
								ps.setString(cnt++, compareData.getCreateBy());
								ps.setTimestamp(cnt++, ApplicationDate.getTimestamp());
								ps.setString(cnt++, userId);
								ps.setInt(cnt++, lifeCycle);
								
								ps.setString(cnt++, compareData.getUniqueId());
								ps.setString(cnt++, compareData.getUniqueLevel());
								ps.setString(cnt++, compareData.getConfigData());
								ps.addBatch();
							}
						}
					}
				}
			}
			ps.executeBatch();
		}catch (Exception e){
			logger.fatal("ERROR",e);
			throw new ApplicationException(e.getMessage());
		}finally{
			try{
//				closeConnection(conn, ps);
				closeConnection(ps);
			}catch(Exception e){
				logger.fatal("ERROR",e);
				throw new ApplicationException(e.getMessage());
			}
		}
	}
	@Override
	public void saveUpdateOrigComparisionData(HashMap<String, CompareDataM> comparisonFields,String applicationGroupId, int lifeCycle, Connection conn)throws ApplicationException {
		PreparedStatement ps = null;
		try{
			StringBuilder sql = new StringBuilder("");
			sql.append("INSERT INTO ORIG_COMPARISON_DATA ");
			sql.append("( APPLICATION_GROUP_ID, ROLE, FIELD_NAME, FIELD_NAME_TYPE, ");
			sql.append(" VALUE, COMPARE_FLAG, SRC_OF_DATA, REF_ID, REF_LEVEL, ");
			sql.append(" CREATE_DATE, CREATE_BY, UPDATE_DATE, UPDATE_BY, LIFE_CYCLE, ");
			sql.append(" UNIQUE_ID, UNIQUE_LEVEL, CONFIG_DATA ) ");
			sql.append(" VALUES(?,?,?,?,  ?,?,?,?,?,  ?,?,?,?,?, ?,?,? )");
			String dSql = String.valueOf(sql);
			logger.debug("Sql=" + dSql);
			ps = conn.prepareStatement(dSql);
			if(null!=comparisonFields){
				for(CompareDataM compareData : comparisonFields.values()){
					if(!CompareDataM.CompareDataType.DUMMY.equals(compareData.getCompareDataType())){
						int cnt = 1;
						ps.setString(cnt++, applicationGroupId);		
						ps.setString(cnt++, compareData.getRole());
						ps.setString(cnt++, compareData.getFieldName());
						ps.setString(cnt++, compareData.getFieldNameType());
						ps.setString(cnt++, compareData.getValue());
						ps.setString(cnt++, compareData.getCompareFlag());
						ps.setString(cnt++, compareData.getSrcOfData());
						ps.setString(cnt++, compareData.getRefId());
						ps.setString(cnt++, compareData.getRefLevel());
						ps.setTimestamp(cnt++, ApplicationDate.getTimestamp());
						if(Util.empty(compareData.getCreateBy())){
							compareData.setCreateBy(userId);
						}
						ps.setString(cnt++, compareData.getCreateBy());
						ps.setTimestamp(cnt++, ApplicationDate.getTimestamp());
						ps.setString(cnt++, userId);
						ps.setInt(cnt++, lifeCycle);
						
						ps.setString(cnt++, compareData.getUniqueId());
						ps.setString(cnt++, compareData.getUniqueLevel());
						ps.setString(cnt++, compareData.getConfigData());
						ps.addBatch();
					}
				}
			}
			ps.executeBatch();
		}catch (Exception e){
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
	}
	@Override
	public void updateCompareFlag(String srcOfData,HashMap<String,CompareDataM> comparisonFields,Connection conn)throws ApplicationException {
		PreparedStatement ps = null;
//		Connection conn = null;
		try{
//		conn = getConnection();
		StringBuilder sql = new StringBuilder("");
		sql.append("UPDATE ORIG_COMPARISON_DATA SET ");
		sql.append("COMPARE_FLAG = ? ");
		sql.append("WHERE APPLICATION_GROUP_ID = ? AND SRC_OF_DATA = ? AND ROLE = ? AND FIELD_NAME = ? ");
		if(isLifeCycleCondition(srcOfData)){
			sql.append("AND LIFE_CYCLE = ?");
		}
		String dSql = String.valueOf(sql);
		logger.debug("Sql=" + dSql);
		ps = conn.prepareStatement(dSql);
		if(!Util.empty(comparisonFields)){
			for(CompareDataM compare :comparisonFields.values()){
				int cnt = 1;
				ps.setString(cnt++, compare.getCompareFlag());
				
				//Where Cause
				ps.setString(cnt++, compare.getApplicationGroupId());
				ps.setString(cnt++, CompareDataM.SoruceOfData.TWO_MAKER);
				ps.setString(cnt++, compare.getRole());
				ps.setString(cnt++, compare.getFieldName());
				
				if(isLifeCycleCondition(srcOfData)){
					ps.setInt(cnt++, compare.getLifeCycle());
				}
				ps.addBatch();
			}
			ps.executeBatch();
		}
		
		}catch (Exception e){
			logger.fatal("ERROR",e);
			throw new ApplicationException(e.getMessage());
		}finally{
			try{
//				closeConnection(conn, ps);
				closeConnection(ps);
			}catch(Exception e){
				logger.fatal("ERROR",e);
				throw new ApplicationException(e.getMessage());
			}
		}
	}
	@Override
	public void deleteOrigComparisionDataMatchSrc(String applicationGroupId,String srcOfData,int lifeCycle)throws ApplicationException {
		Connection conn = null;
		try{
			conn = getConnection();
			deleteOrigComparisionDataMatchSrc(applicationGroupId,srcOfData,lifeCycle,conn);
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
	public void deleteOrigComparisionDataMatchSrc(String applicationGroupId,String srcOfData, int lifeCycle, Connection conn)throws ApplicationException {
		PreparedStatement ps = null;
		try {
			//conn = Get Connection
			StringBuffer sql = new StringBuffer("");
			sql.append("DELETE FROM ORIG_COMPARISON_DATA ");
			sql.append(" WHERE APPLICATION_GROUP_ID = ? AND SRC_OF_DATA = ? ");	
			if(isLifeCycleCondition(srcOfData)){
				sql.append(" AND LIFE_CYCLE = ? ");
			}
			String dSql = String.valueOf(sql);
			logger.debug("Sql=" + dSql);
			ps = conn.prepareStatement(dSql);
				int index = 1;
				ps.setString(index++, applicationGroupId);	
				ps.setString(index++, srcOfData);	
				if(isLifeCycleCondition(srcOfData)){
					ps.setInt(index++, lifeCycle);	
				}	
			ps.executeUpdate();
		} catch (Exception e) {
			logger.fatal("ERROR",e);
			throw new ApplicationException(e.getMessage());
		} finally {
			try {
				closeConnection(ps);
			} catch (Exception e) {
				logger.fatal("ERROR",e);
				throw new ApplicationException(e.getMessage());
			}
		}
	}
}
