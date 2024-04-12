package com.eaf.orig.ulo.app.dao;

import java.net.URLDecoder;
import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.apache.log4j.Logger;

import com.eaf.core.ulo.common.exception.EngineException;
import com.eaf.core.ulo.common.properties.SystemConstant;
import com.eaf.orig.model.ld.ImportOTDataM;
import com.eaf.orig.model.ld.UploadCompanyNameM;
import com.eaf.orig.model.ld.UploadForecastM;
import com.eaf.orig.model.ld.UploadProductImageM;
import com.eaf.orig.shared.dao.OrigObjectDAO;


public class LookupDataDAOImpl extends OrigObjectDAO implements LookupDataDAO{
	private static transient Logger logger = Logger.getLogger(LookupDataDAOImpl.class);

	public Date parseThaiToEngDateFormat(String date){
		Date oracleDateFormat = null;
		try{
			date = URLDecoder.decode(date,"UTF-8");
			int year = Integer.parseInt(date.substring(6,date.length()))-543;
			date = date.substring(0,6).concat(String.valueOf(year));
			logger.debug("date : "+date);
			SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
			java.util.Date d = sdf.parse(date);
			sdf.applyPattern("yyyy-MM-dd");
			date = sdf.format(d);
			oracleDateFormat = Date.valueOf(date);
		}catch(Exception e){
			logger.fatal("system can not to convert date");
		}
		
		return oracleDateFormat;
	}
	
	@Override
	public List<String> getUserNo() throws EngineException {
		Connection conn = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		List<String> userNoList = new ArrayList<>();
		try{
			conn = getConnection();
			StringBuilder sql = new StringBuilder();
			sql.append("SELECT * FROM US_USER_DETAIL WHERE USER_NO IS NOT NULL ");
			logger.debug("sql : "+sql.toString());
			ps = conn.prepareStatement(sql.toString());
			rs = ps.executeQuery();
			while(rs.next()){
				userNoList.add(rs.getString("USER_NO"));
			}
		}catch(Exception e){
			logger.fatal("ERROR ",e);
			throw new EngineException(e.getLocalizedMessage());
		}finally{
			try {
				closeConnection(conn, rs, ps);
			} catch (Exception e) {
				logger.fatal("ERROR "+e.getMessage());
			}
		}
		return userNoList;
	}
	
	@Override
	public void insertUserOTPoint(ImportOTDataM importOTDataM) throws EngineException {
		Connection conn = null;
		PreparedStatement ps = null;
		try{
			conn = getConnection();
			StringBuilder sql = new StringBuilder();
			sql.append(" INSERT INTO USER_OT_POINT ");
			sql.append(" ( ");
			sql.append(" USER_NAME, POINT, CREATE_DATE, CREATE_BY ");
			sql.append(" ) VALUES ( ");
			sql.append(" ?, ?, ?, ? ");
			sql.append(" ) ");
			logger.debug("sql : "+sql);
			ps = conn.prepareStatement(sql.toString());
			
			ps.setString(1, getUserName(importOTDataM.getUserNo()));
			ps.setString(2, importOTDataM.getPoint());
			ps.setDate(3, parseThaiToEngDateFormat(importOTDataM.getCreateDate()));
			ps.setString(4, importOTDataM.getCreateBy());
			ps.executeUpdate();
		}catch(Exception e){
			logger.fatal("ERROR ",e);
			throw new EngineException(e.getLocalizedMessage());
		}finally{
			try {
				closeConnection(conn, ps);
			} catch (Exception e) {
				logger.fatal("ERROR "+e.getMessage());
			}
		}
	}
	
	public String getUserName(String userNo) throws EngineException {
		Connection conn = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		String userName = null;
		try{
			conn = getConnection();
			StringBuilder sql = new StringBuilder();
			sql.append("SELECT * FROM US_USER_DETAIL WHERE USER_NO = ? ");
			logger.debug("sql : "+sql.toString());
			ps = conn.prepareStatement(sql.toString());
			ps.setString(1, userNo);
			rs = ps.executeQuery();
			if(rs.next()){
				userName = rs.getString("USER_NAME");
			}
		}catch(Exception e){
			logger.fatal("ERROR ",e);
			throw new EngineException(e.getLocalizedMessage());
		}finally{
			try {
				closeConnection(conn, rs, ps);
			} catch (Exception e) {
				logger.fatal("ERROR "+e.getMessage());
			}
		}
		return userName;
	}

	@Override
	public void deleteUserOTPoint() throws EngineException {
		Connection conn = null;
		PreparedStatement ps = null;
		try{
			conn = getConnection();
			StringBuilder sql = new StringBuilder();
			sql.append(" DELETE USER_OT_POINT ");
			logger.debug("sql : "+sql);
			ps = conn.prepareStatement(sql.toString());
			ps.executeUpdate();
		}catch(Exception e){
			logger.fatal("ERROR ",e);
			throw new EngineException(e.getLocalizedMessage());
		}finally{
			try{
				closeConnection(conn, ps);
			}catch(Exception e){
				logger.fatal("ERROR "+e.getMessage());
			}
		}
		
	}

	@Override
	public void deleteUploadForecast() throws EngineException {
		Connection conn = null;
		PreparedStatement ps = null;
		try{
			conn = getConnection();
			StringBuilder sql = new StringBuilder();
			sql.append(" DELETE IMP_FORECAST_APP_IN ");
			logger.debug("sql : "+sql);
			ps = conn.prepareStatement(sql.toString());
			ps.executeUpdate();
		}catch(Exception e){
			logger.fatal("ERROR ",e);
			throw new EngineException(e.getLocalizedMessage());
		}finally{
			try{
				closeConnection(conn, ps);
			}catch(Exception e){
				logger.fatal("ERROR "+e.getMessage());
			}
		}
	}

	@Override
	public void insertUploadForecast(UploadForecastM uploadForecastDataM) throws EngineException {
		Connection conn = null;
		PreparedStatement ps = null;
		try{
			conn = getConnection();
			StringBuilder sql = new StringBuilder();
			sql.append(" INSERT INTO IMP_FORECAST_APP_IN ");
			sql.append(" ( ");
			sql.append(" FORECAST_DAY, FORECAST_MONTH, FORECAST_APP_IN, CREATE_DATE, CREATE_BY ");
			sql.append(" ) VALUES ( ");
			sql.append(" ?, ?, ?, SYSDATE, ? ");
			sql.append(" ) ");
			logger.debug("sql : "+sql);
			ps = conn.prepareStatement(sql.toString());
			ps.setString(1, uploadForecastDataM.getForecastDay());
			ps.setString(2, uploadForecastDataM.getForecastMonth());
			ps.setString(3, uploadForecastDataM.getForecastAppIn());
			ps.setString(4, uploadForecastDataM.getCreateBy());
			ps.executeUpdate();
		}catch(Exception e){
			logger.fatal("ERROR ",e);
			throw new EngineException(e.getLocalizedMessage());
		}finally{
			try {
				closeConnection(conn, ps);
			} catch (Exception e) {
				logger.fatal("ERROR "+e.getMessage());
			}
		}
	}
	
	@Override
	public ArrayList<UploadProductImageM> selectUploadProductImage() throws EngineException {
		Connection conn = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		ArrayList<UploadProductImageM> uploadProductImageList = new ArrayList<>();
		try{
			conn = getConnection();
			StringBuilder sql = new StringBuilder();
			sql.append(" SELECT * FROM MS_PRODUCT_IMAGE ");
			logger.debug("sql : "+sql);
			ps = conn.prepareStatement(sql.toString());
			rs = ps.executeQuery();
			while(rs.next()){
				UploadProductImageM uploadProductImage = new UploadProductImageM();
				uploadProductImage.setProductId(rs.getString("PRODUCT_ID"));
				uploadProductImage.setProductDesc(rs.getString("PRODUCT_DESC"));
				uploadProductImageList.add(uploadProductImage);
			}
		}catch(Exception e){
			logger.fatal("ERROR ",e);
			throw new EngineException(e.getLocalizedMessage());
		}finally{
			try{
				closeConnection(conn, rs, ps);
			}catch(Exception e){
				logger.fatal("ERROR "+e.getMessage());
			}
		}
		return uploadProductImageList;
	}
	
	@Override
	public void updateUploadProductImage(UploadProductImageM uploadProductImageM) throws EngineException {
		Connection conn = null;
		PreparedStatement ps = null;
		try{
			conn = getConnection();
			StringBuilder sql = new StringBuilder();
			sql.append(" UPDATE MS_PRODUCT_IMAGE ");
			sql.append(" SET ");
			sql.append(" PRODUCT_DESC = ?, ");
			sql.append(" IMAGE_PATH = ?, ");
			sql.append(" UPDATE_DATE = SYSDATE, ");
			sql.append(" UPDATE_BY = ? ");
			sql.append(" WHERE PRODUCT_ID = ? ");
			logger.debug("sql : "+sql);
			ps = conn.prepareStatement(sql.toString());
			ps.setString(1, uploadProductImageM.getProductDesc());
			ps.setString(2, uploadProductImageM.getImagePath());
			ps.setString(3, uploadProductImageM.getUpdateBy());
			ps.setString(4, uploadProductImageM.getProductId());
			ps.executeUpdate();
		}catch(Exception e){
			logger.fatal("ERROR ",e);
			throw new EngineException(e.getLocalizedMessage());
		}finally{
			try {
				closeConnection(conn, ps);
			} catch (Exception e) {
				logger.fatal("ERROR "+e.getMessage());
			}
		}
	}

	@Override
	public List<String> selectAllProductCard() throws EngineException {
		Connection conn = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		List<String> ProductCardList = new ArrayList<>();
		try{
			conn = getConnection();
			StringBuilder sql = new StringBuilder();
			sql.append(" SELECT * FROM MS_PRODUCT_IMAGE ");
			logger.debug("sql : "+sql);
			ps = conn.prepareStatement(sql.toString());
			rs = ps.executeQuery();
			while(rs.next()){
				ProductCardList.add(rs.getString("PRODUCT_ID"));
			}
			
		}catch(Exception e){
			logger.fatal("ERROR ",e);
			throw new EngineException(e.getLocalizedMessage());
		}finally{
			try{
				closeConnection(conn, rs, ps);
			}catch(Exception e){
				logger.fatal("ERROR "+e.getMessage());
			}
		}
		return ProductCardList;
	}

	@Override
	public ArrayList<HashMap<String, Object>> getCompanyList() throws EngineException {
		Connection conn = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		ArrayList<HashMap<String, Object>> CompanyList = new ArrayList<>();
		try{
			conn = getConnection();
			StringBuilder sql = new StringBuilder();
			sql.append(" SELECT * FROM MS_COMPANY_NAME ");
			logger.debug("sql : "+sql);
			ps = conn.prepareStatement(sql.toString());
			rs = ps.executeQuery();
			while(rs.next()){
				HashMap<String, Object> data = new HashMap<>();
				data.put("CODE", rs.getString("COMPANY_ID"));
				data.put("VALUE", rs.getString("COMPANY_NAME"));
				CompanyList.add(data);
			}
		}catch(Exception e){
			logger.fatal("ERROR ",e);
			throw new EngineException(e.getLocalizedMessage());
		}finally{
			try{
				closeConnection(conn, rs, ps);
			}catch(Exception e){
				logger.fatal("ERROR "+e.getMessage());
			}
		}
		return CompanyList;
	}

	@Override
	public ArrayList<HashMap<String, Object>> getSavingAccountNoList(String personalId) throws EngineException {
		Connection conn = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		ArrayList<HashMap<String, Object>> CompanyList = new ArrayList<>();
		String SAVING_ACCOUNT_TYPE = SystemConstant.getConstant("SAVING_ACCOUNT_TYPE");
		try{
			conn = getConnection();
			StringBuilder sql = new StringBuilder();
			sql.append(" SELECT  DISTINCT * FROM ORIG_PERSONAL_ACCOUNT ");
			sql.append(" WHERE PERSONAL_ID = ? AND ACCOUNT_TYPE = ? ");
			logger.debug("sql : "+sql);
			logger.debug("SAVING_ACCOUNT_TYPE : "+SAVING_ACCOUNT_TYPE);
			logger.debug("personalId : "+personalId);
			ps = conn.prepareStatement(sql.toString());
			int index = 1;
			ps.setString(index++, personalId);
			ps.setString(index++, SAVING_ACCOUNT_TYPE);
			rs = ps.executeQuery();		
			while(rs.next()){
				HashMap<String, Object> data = new HashMap<>();
				data.put("CODE", rs.getString("ACCOUNT_NO"));
				data.put("VALUE", rs.getString("ACCOUNT_NO"));
				CompanyList.add(data);
			}
		}catch(Exception e){
			logger.fatal("ERROR ",e);
			throw new EngineException(e.getLocalizedMessage());
		}finally{
			try{
				closeConnection(conn, rs, ps);
			}catch(Exception e){
				logger.fatal("ERROR "+e.getMessage());
			}
		}
		return CompanyList;
	}
	
	@Override
	public void deleteUploadCompanyName() throws EngineException {
		Connection conn = null;
		PreparedStatement ps = null;
		try{
			conn = getConnection();
			StringBuilder sql = new StringBuilder();
			sql.append(" DELETE MS_COMPANY_NAME ");
			logger.debug("sql : "+sql);
			ps = conn.prepareStatement(sql.toString());
			ps.executeUpdate();
		}catch(Exception e){
			logger.fatal("ERROR ",e);
			throw new EngineException(e.getLocalizedMessage());
		}finally{
			try{
				closeConnection(conn, ps);
			}catch(Exception e){
				logger.fatal("ERROR "+e.getMessage());
			}
		}
	}

	@Override
	public void insertUploadCompanyName(UploadCompanyNameM uploadCompanyNameM) throws EngineException {
		Connection conn = null;
		PreparedStatement ps = null;
		try{
			conn = getConnection();
			StringBuilder sql = new StringBuilder();
			sql.append(" INSERT INTO MS_COMPANY_NAME ");
			sql.append(" ( ");
			sql.append(" COMPANY_ID, COMPANY_NAME, CREATE_DATE, CREATE_BY ");
			sql.append(" ) VALUES ( ");
			sql.append(" ?, ?, SYSDATE, ? ");
			sql.append(" ) ");
			logger.debug("sql : "+sql);
			ps = conn.prepareStatement(sql.toString());
			ps.setString(1, uploadCompanyNameM.getCompanyId());
			ps.setString(2, uploadCompanyNameM.getCompanyName());
			ps.setString(3, uploadCompanyNameM.getCreateBy());
			ps.executeUpdate();
		}catch(Exception e){
			logger.fatal("ERROR ",e);
			throw new EngineException(e.getLocalizedMessage());
		}finally{
			try {
				closeConnection(conn, ps);
			} catch (Exception e) {
				logger.fatal("ERROR "+e.getMessage());
			}
		}
		
	}
	
	
}