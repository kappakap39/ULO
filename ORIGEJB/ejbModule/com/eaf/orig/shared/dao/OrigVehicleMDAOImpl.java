/*
 * Created on Oct 1, 2007
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
import com.eaf.orig.shared.dao.exceptions.OrigVehicleMException;
import com.eaf.orig.shared.ejb.ORIGGeneratorManager;
import com.eaf.orig.shared.model.InsuranceDataM;
import com.eaf.orig.shared.model.LoanDataM;
import com.eaf.orig.shared.model.VehicleDataM;
import com.eaf.orig.shared.service.ORIGEJBService;

/**
 * @author Sankom Sanpunya
 * 
 * Type: OrigVehicleMDAOImpl
 */
public class OrigVehicleMDAOImpl extends OrigObjectDAO implements
		OrigVehicleMDAO {
	private static Logger log = Logger.getLogger(OrigVehicleMDAOImpl.class);

	/**
	 *  
	 */
	public OrigVehicleMDAOImpl() {
		super();

	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.eaf.orig.share.dao.OrigVehicleMDAO#createModelVehicleDataM(com.eaf.orig.shared.model.VehicleDataM)
	 */
	public void createModelVehicleDataM(VehicleDataM prmVehicleDataM)
			throws OrigVehicleMException {
		try {
			//int vehicleID = Integer.parseInt(ORIGDAOFactory.getUniqueIDGeneratorDAO2().getUniqByName(EJBConstant.DUP_VEHICLE_ID));
			//prmVehicleDataM.setVehicleID(vehicleID);
			ORIGGeneratorManager generatorManager = ORIGEJBService.getGeneratorManager();
			int vehicleID = Integer.parseInt(generatorManager.generateUniqueIDByName(EJBConstant.DUP_VEHICLE_ID));
			prmVehicleDataM.setVehicleID(vehicleID);
			createTableORIG_VEHICLE(prmVehicleDataM);
			//get application record id;			
			String appRecId = prmVehicleDataM.getAppRecordID();
			InsuranceDataM prmInsuranceDataM = prmVehicleDataM
					.getInsuranceDataM();
			prmInsuranceDataM.setAppRecordID(appRecId);
			prmInsuranceDataM.setVenhicleId(vehicleID);
			ORIGDAOFactory.getOrigInsuranceMDAO().createModelOrigInsuranceM(
					prmInsuranceDataM);
		} catch (Exception e) {
			log.fatal(e.getLocalizedMessage());
			throw new OrigVehicleMException(e.getMessage());
		}

	}

	/**
	 * @param prmVehicleDataM
	 */
	private void createTableORIG_VEHICLE(VehicleDataM prmVehicleDataM)
			throws OrigVehicleMException {
		PreparedStatement ps = null;
		Connection conn = null;
		try {
			//conn = Get Connection
			conn = getConnection();
			StringBuffer sql = new StringBuffer("");
			sql.append("INSERT INTO ORIG_VEHICLE ");
			sql
					.append("( VEHICLE_ID,APPLICATION_RECORD_ID,CATEGORY,ENGINE_NUMBER,CAR");
			sql.append(",BRAND,REGISTER_NUMBER,GEAR,MODEL,CHASSIS_NUMBER");
			sql.append(",LICENSE_TYPE,CC,YEAR,PROVINCE,KELOMETERS");
			sql
					.append(",REGISTER_DATE,CAR_USER,OCCUPY_DATE,EXPIRY_DATE,OBJECTIVE");
			sql
					.append(",CAR_PARK_LOCATION,TRAVALING_ROUT,CREATE_BY,CREATE_DATE,UPDATE_BY");
			sql.append(",UPDATE_DATE,CONTRACT_NO, DRAW_DOWN_STATUS, IDNO, WEIGHT, COLOR)");
			sql
					.append(" VALUES(?,?,?,?,?  ,?,?,?,?,? ,?,?,?,?,?  ,?,?,?,?,?  ,?,?,?,SYSDATE,? ,SYSDATE,?,?,?,?,? ) ");
			String dSql = String.valueOf(sql);
			log.debug("Sql=" + dSql);
			ps = conn.prepareStatement(dSql);
			//TODO creacte uniq vehicle id
			ps.setInt(1, prmVehicleDataM.getVehicleID());
			ps.setString(2, prmVehicleDataM.getAppRecordID());
			ps.setString(3, prmVehicleDataM.getCategory());
			ps.setString(4, prmVehicleDataM.getEngineNo());
			ps.setString(5, prmVehicleDataM.getCar());
			ps.setString(6, prmVehicleDataM.getBrand());
			ps.setString(7, prmVehicleDataM.getRegisterNo());
			ps.setString(8, prmVehicleDataM.getGear());
			ps.setString(9, prmVehicleDataM.getModel());
			ps.setString(10, prmVehicleDataM.getChassisNo());
			ps.setString(11, prmVehicleDataM.getLicenseType());
			ps.setDouble(12, prmVehicleDataM.getCC());
			ps.setInt(13, prmVehicleDataM.getYear());
			ps.setString(14, prmVehicleDataM.getProvince());
			ps.setDouble(15, prmVehicleDataM.getKelometers());
			ps.setDate(16, this.parseDate(prmVehicleDataM.getRegisterDate()));
			ps.setString(17, prmVehicleDataM.getCarUser());
			ps.setDate(18, this.parseDate(prmVehicleDataM.getOccupyDate()));
			ps.setDate(19, this.parseDate(prmVehicleDataM.getExpiryDate()));
			ps.setString(20, prmVehicleDataM.getObjective());
			ps.setString(21, prmVehicleDataM.getCarParkLocation());
			ps.setString(22, prmVehicleDataM.getTravalingRout());
			ps.setString(23, prmVehicleDataM.getCreateBy());
			ps.setString(24, prmVehicleDataM.getUpdateBy());
			logger.debug("prmVehicleDataM.getContractNo() = "+prmVehicleDataM.getContractNo());
			ps.setString(25, prmVehicleDataM.getContractNo());
			ps.setString(26, prmVehicleDataM.getDrawDownStatus());
			ps.setString(27, prmVehicleDataM.getIdNo());
			ps.setDouble(28, prmVehicleDataM.getWeight());
			ps.setString(29, prmVehicleDataM.getColor());
			
			ps.executeUpdate();
		} catch (Exception e) {
			log.fatal(e.getLocalizedMessage());
			throw new OrigVehicleMException(e.getMessage());
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
	 * @see com.eaf.orig.share.dao.OrigVehicleMDAO#deleteModelVehicleDataM(com.eaf.orig.shared.model.VehicleDataM)
	 */
	public void deleteModelVehicleDataM(VehicleDataM prmVehicleDataM)
			throws OrigVehicleMException {
		try {
			InsuranceDataM prmInsuranceDataM = prmVehicleDataM
					.getInsuranceDataM();
			ORIGDAOFactory.getOrigInsuranceMDAO().deleteModelOrigInsuranceM(
					prmInsuranceDataM);
			deleteTableORIG_VEHICLE(prmVehicleDataM);
		} catch (Exception e) {
			log.fatal(e.getLocalizedMessage());
			throw new OrigVehicleMException(e.getMessage());
		}

	}

	/**
	 * @param prmVehicleDataM
	 */
	private void deleteTableORIG_VEHICLE(VehicleDataM prmVehicleDataM)
			throws OrigVehicleMException {
		PreparedStatement ps = null;
		Connection conn = null;
		try {
			//conn = Get Connection
			conn = getConnection();
			StringBuffer sql = new StringBuffer("");
			sql.append("DELETE ORIG_VEHICLE ");
			sql.append(" WHERE APPLICATION_RECORD_ID = ?  AND VEHICLE_ID =?");
			String dSql = String.valueOf(sql);
			log.debug("Sql=" + dSql);
			ps = conn.prepareStatement(dSql);
			ps.setString(1, prmVehicleDataM.getAppRecordID());
			ps.setInt(2, prmVehicleDataM.getVehicleID());
			ps.executeUpdate();

		} catch (Exception e) {
			log.fatal(e.getLocalizedMessage());
			throw new OrigVehicleMException(e.getMessage());
		} finally {
			try {
				closeConnection(conn, ps);
			} catch (Exception e) {
				log.fatal(e.getLocalizedMessage());
				throw new OrigVehicleMException(e.getMessage());
			}
		}

	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.eaf.orig.share.dao.OrigVehicleMDAO#loadModelVehicleDataM(java.lang.String)
	 */
	public VehicleDataM loadModelVehicleDataM(String applicationRecordId)
			throws OrigVehicleMException {
		try {
			VehicleDataM result = selectTableORIG_VEHICLE(applicationRecordId);
			if(result != null){
				InsuranceDataM insuranceDataM = ORIGDAOFactory.getOrigInsuranceMDAO().loadModelOrigInsuranceM(applicationRecordId);
				result.setInsuranceDataM(insuranceDataM);
			}
			return result;
		} catch (Exception e) {
			log.fatal("Error >> ", e);
			throw new OrigVehicleMException(e.getMessage());
		}
	}

	/**
	 * @param applicationRecordId
	 * @return
	 */
	private VehicleDataM selectTableORIG_VEHICLE(String applicationRecordId)
			throws OrigVehicleMException {
		PreparedStatement ps = null;
		ResultSet rs = null;
		Connection conn = null;
		try {
			//conn = Get Connection
			conn = getConnection();
			StringBuffer sql = new StringBuffer("");
			sql.append("SELECT CATEGORY,ENGINE_NUMBER,CAR ");
			sql.append(",BRAND,REGISTER_NUMBER,GEAR,MODEL,CHASSIS_NUMBER");
			sql.append(",LICENSE_TYPE,CC,YEAR,PROVINCE,KELOMETERS");
			sql
					.append(",REGISTER_DATE,CAR_USER,OCCUPY_DATE,EXPIRY_DATE,OBJECTIVE");
			sql
					.append(",CAR_PARK_LOCATION,TRAVALING_ROUT,CREATE_BY,CREATE_DATE,UPDATE_BY");
			sql.append(",UPDATE_DATE,CONTRACT_NO,VEHICLE_ID, WEIGHT, COLOR, DRAW_DOWN_STATUS ");
			sql.append(" FROM ORIG_VEHICLE WHERE APPLICATION_RECORD_ID = ? ");
			String dSql = String.valueOf(sql);
			log.debug("Sql=" + dSql);
			ps = conn.prepareStatement(dSql);
			rs = null;
			ps.setString(1, applicationRecordId);

			rs = ps.executeQuery();
			VehicleDataM vehicleDataM = null;
			if (rs.next()) {
				vehicleDataM = new VehicleDataM();
				vehicleDataM.setCategory(rs.getString(1));
				vehicleDataM.setEngineNo(rs.getString(2));
				vehicleDataM.setCar(rs.getString(3));
				vehicleDataM.setBrand(rs.getString(4));
				vehicleDataM.setRegisterNo(rs.getString(5));
				vehicleDataM.setGear(rs.getString(6));
				vehicleDataM.setModel(rs.getString(7));
				vehicleDataM.setChassisNo(rs.getString(8));
				vehicleDataM.setLicenseType(rs.getString(9));
				vehicleDataM.setCC(rs.getDouble(10));
				vehicleDataM.setYear(rs.getInt(11));
				vehicleDataM.setProvince(rs.getString(12));
				vehicleDataM.setKelometers(rs.getDouble(13));
				vehicleDataM.setRegisterDate(rs.getDate(14));
				vehicleDataM.setCarUser(rs.getString(15));
				vehicleDataM.setOccupyDate(rs.getDate(16));
				vehicleDataM.setExpiryDate(rs.getDate(17));
				vehicleDataM.setObjective(rs.getString(18));
				vehicleDataM.setCarParkLocation(rs.getString(19));
				vehicleDataM.setTravalingRout(rs.getString(20));
				vehicleDataM.setCreateBy(rs.getString(21));
				vehicleDataM.setCreateDate(rs.getTimestamp(22));
				vehicleDataM.setUpdateBy(rs.getString(23));
				vehicleDataM.setUpdateDate(rs.getTimestamp(24));
				vehicleDataM.setContractNo(rs.getString(25));
				vehicleDataM.setVehicleID(rs.getInt(26));
				vehicleDataM.setWeight(rs.getDouble(27));
				vehicleDataM.setColor(rs.getString(28));
				vehicleDataM.setDrawDownStatus(rs.getString(29));
				vehicleDataM.setAppRecordID(applicationRecordId);
			}
			return vehicleDataM;
		} catch (Exception e) {
			log.fatal(e.getLocalizedMessage());
			throw new OrigVehicleMException(e.getMessage());
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
	 * @see com.eaf.orig.share.dao.OrigVehicleMDAO#saveUpdateModelVehicleDataM(com.eaf.orig.shared.model.VehicleDataM)
	 */
	public void saveUpdateModelVehicleDataM(VehicleDataM prmVehicleDataM)
			throws OrigVehicleMException {
		double returnRows = 0;
		try {
				returnRows = updateTableORIG_VEHICLE(prmVehicleDataM);
				
				if (returnRows == 0) {
					log
							.debug("New record then can't update record in table ORIG_VEHICLE then call Insert method");
					//int vehicleID=Integer.parseInt(ORIGDAOFactory.getUniqueIDGeneratorDAO2().getUniqByName(EJBConstant.DUP_VEHICLE_ID));
					//prmVehicleDataM.setVehicleID(vehicleID);
					ORIGGeneratorManager generatorManager = ORIGEJBService.getGeneratorManager();
					int vehicleID = Integer.parseInt(generatorManager.generateUniqueIDByName(EJBConstant.DUP_VEHICLE_ID));
					prmVehicleDataM.setVehicleID(vehicleID);
					createTableORIG_VEHICLE(prmVehicleDataM);
				}
				InsuranceDataM insuranceDataM = prmVehicleDataM.getInsuranceDataM();
				logger.debug("insuranceDataM = "+insuranceDataM);
				if(insuranceDataM != null){
					insuranceDataM.setAppRecordID(prmVehicleDataM.getAppRecordID());
					insuranceDataM.setVenhicleId(prmVehicleDataM.getVehicleID());
					ORIGDAOFactory.getOrigInsuranceMDAO().saveUpdateModelOrigInsuranceM(insuranceDataM);
					
				}
			
		} catch (Exception e) {
			log.fatal(e.getLocalizedMessage());
			throw new OrigVehicleMException(e.getMessage());
		}

	}

	/**
	 * @param prmVehicleDataM
	 * @return
	 */
	private double updateTableORIG_VEHICLE(VehicleDataM prmVehicleDataM)
			throws OrigVehicleMException {
		double returnRows = 0;
		PreparedStatement ps = null;
		Connection conn = null;
		try {
			//conn = Get Connection
			conn = getConnection();
			StringBuffer sql = new StringBuffer("");
			sql.append("UPDATE ORIG_VEHICLE ");
			sql.append(" SET CATEGORY=?,ENGINE_NUMBER=?,CAR=?");
			sql
					.append(",BRAND=?,REGISTER_NUMBER=?,GEAR=?,MODEL=?,CHASSIS_NUMBER=?");
			sql.append(",LICENSE_TYPE=?,CC=?,YEAR=?,PROVINCE=?,KELOMETERS=?");
			sql
					.append(",REGISTER_DATE=?,CAR_USER=?,OCCUPY_DATE=?,EXPIRY_DATE=?,OBJECTIVE=?");
			sql
					.append(",CAR_PARK_LOCATION=?,TRAVALING_ROUT=?,UPDATE_BY=?,UPDATE_DATE=SYSDATE,CONTRACT_NO=?, WEIGHT=?, COLOR=?, DRAW_DOWN_STATUS=? ");

			sql.append("  ,VEHICLE_ID=? WHERE APPLICATION_RECORD_ID = ?  ");
			String dSql = String.valueOf(sql);
			log.debug("Sql=" + dSql);
			log.debug(" prmVehicleDataM.getAppRecordID()=" + prmVehicleDataM.getAppRecordID());
			log.debug(" prmVehicleDataM.getVehicleID()=" + prmVehicleDataM.getVehicleID());
			ps = conn.prepareStatement(dSql);
			ps.setString(1, prmVehicleDataM.getCategory());
			ps.setString(2, prmVehicleDataM.getEngineNo());
			ps.setString(3, prmVehicleDataM.getCar());
			ps.setString(4, prmVehicleDataM.getBrand());
			ps.setString(5, prmVehicleDataM.getRegisterNo());
			ps.setString(6, prmVehicleDataM.getGear());
			ps.setString(7, prmVehicleDataM.getModel());
			ps.setString(8, prmVehicleDataM.getChassisNo());
			ps.setString(9, prmVehicleDataM.getLicenseType());
			ps.setDouble(10, prmVehicleDataM.getCC());
			ps.setInt(11, prmVehicleDataM.getYear());
			ps.setString(12, prmVehicleDataM.getProvince());
			ps.setDouble(13, prmVehicleDataM.getKelometers());
			ps.setDate(14, this.parseDate(prmVehicleDataM.getRegisterDate()));
			ps.setString(15, prmVehicleDataM.getCarUser());
			ps.setDate(16, this.parseDate(prmVehicleDataM.getOccupyDate()));
			ps.setDate(17, this.parseDate(prmVehicleDataM.getExpiryDate()));
			ps.setString(18, prmVehicleDataM.getObjective());
			ps.setString(19, prmVehicleDataM.getCarParkLocation());
			ps.setString(20, prmVehicleDataM.getTravalingRout());
			ps.setString(21, prmVehicleDataM.getUpdateBy());
			ps.setString(22, prmVehicleDataM.getContractNo());
			ps.setDouble(23, prmVehicleDataM.getWeight());
			ps.setString(24, prmVehicleDataM.getColor());
			ps.setString(25, prmVehicleDataM.getDrawDownStatus());
			
			ps.setInt(26, prmVehicleDataM.getVehicleID());
			ps.setString(27, prmVehicleDataM.getAppRecordID());
			
			
			returnRows = ps.executeUpdate();
		} catch (Exception e) {
			log.fatal(e.getLocalizedMessage());
			throw new OrigVehicleMException(e.getMessage());
		} finally {
			try {
				closeConnection(conn, ps);
			} catch (Exception e) {
				log.fatal(e.getLocalizedMessage());
			}
		}
		return returnRows;
	}
	
	/*
	 * (non-Javadoc)
	 * 
	 * @see com.eaf.orig.share.dao.OrigVehicleMDAO#saveUpdateModelVehicleDataM(com.eaf.orig.shared.model.VehicleDataM)
	 */
	public void saveUpdateModelVehicleDataMForCreateProposal(VehicleDataM prmVehicleDataM)
			throws OrigVehicleMException {
		double returnRows = 0;
		try {
			returnRows = updateTableORIG_VEHICLEForCreateCar(prmVehicleDataM);
			
			if (returnRows == 0) {
				log
						.debug("New record then can't update record in table ORIG_VEHICLE then call Insert method");
				//int vehicleID=Integer.parseInt(ORIGDAOFactory.getUniqueIDGeneratorDAO2().getUniqByName(EJBConstant.DUP_VEHICLE_ID));
				//prmVehicleDataM.setVehicleID(vehicleID);
				ORIGGeneratorManager generatorManager = ORIGEJBService.getGeneratorManager();
				int vehicleID = Integer.parseInt(generatorManager.generateUniqueIDByName(EJBConstant.DUP_VEHICLE_ID));
				prmVehicleDataM.setVehicleID(vehicleID);
				createTableORIG_VEHICLE(prmVehicleDataM);
			}
			InsuranceDataM insuranceDataM = prmVehicleDataM.getInsuranceDataM();
			if(insuranceDataM != null){
				insuranceDataM.setAppRecordID(prmVehicleDataM.getAppRecordID());
				insuranceDataM.setVenhicleId(prmVehicleDataM.getVehicleID());
				ORIGDAOFactory.getOrigInsuranceMDAO().saveUpdateModelOrigInsuranceMForCreateCar(insuranceDataM);
			}
		} catch (Exception e) {
			log.fatal(e.getLocalizedMessage());
			throw new OrigVehicleMException(e.getMessage());
		}

	}

	/**
	 * @param prmVehicleDataM
	 * @return
	 */
	private double updateTableORIG_VEHICLEForCreateCar(VehicleDataM prmVehicleDataM)
			throws OrigVehicleMException {
		double returnRows = 0;
		PreparedStatement ps = null;
		Connection conn = null;
		try {
			//conn = Get Connection
			conn = getConnection();
			StringBuffer sql = new StringBuffer("");
			sql.append("UPDATE ORIG_VEHICLE ");
			sql.append(" SET CATEGORY=?,ENGINE_NUMBER=?,CAR=?");
			sql
					.append(",BRAND=?,REGISTER_NUMBER=?,GEAR=?,MODEL=?,CHASSIS_NUMBER=?");
			sql.append(",LICENSE_TYPE=?,CC=?,YEAR=?,PROVINCE=?,KELOMETERS=?");
			sql
					.append(",REGISTER_DATE=?,CAR_USER=?,OCCUPY_DATE=?,EXPIRY_DATE=?,OBJECTIVE=?");
			sql
					.append(",CAR_PARK_LOCATION=?,TRAVALING_ROUT=?,UPDATE_BY=?,UPDATE_DATE=SYSDATE,CONTRACT_NO=?, WEIGHT=?, COLOR=?, DRAW_DOWN_STATUS=?, APPLICATION_RECORD_ID=? ");

			sql.append(" WHERE VEHICLE_ID=?  ");
			String dSql = String.valueOf(sql);
			log.debug("Sql=" + dSql);
			ps = conn.prepareStatement(dSql);
			ps.setString(1, prmVehicleDataM.getCategory());
			ps.setString(2, prmVehicleDataM.getEngineNo());
			ps.setString(3, prmVehicleDataM.getCar());
			ps.setString(4, prmVehicleDataM.getBrand());
			ps.setString(5, prmVehicleDataM.getRegisterNo());
			ps.setString(6, prmVehicleDataM.getGear());
			ps.setString(7, prmVehicleDataM.getModel());
			ps.setString(8, prmVehicleDataM.getChassisNo());
			ps.setString(9, prmVehicleDataM.getLicenseType());
			ps.setDouble(10, prmVehicleDataM.getCC());
			ps.setInt(11, prmVehicleDataM.getYear());
			ps.setString(12, prmVehicleDataM.getProvince());
			ps.setDouble(13, prmVehicleDataM.getKelometers());
			ps.setDate(14, this.parseDate(prmVehicleDataM.getRegisterDate()));
			ps.setString(15, prmVehicleDataM.getCarUser());
			ps.setDate(16, this.parseDate(prmVehicleDataM.getOccupyDate()));
			ps.setDate(17, this.parseDate(prmVehicleDataM.getExpiryDate()));
			ps.setString(18, prmVehicleDataM.getObjective());
			ps.setString(19, prmVehicleDataM.getCarParkLocation());
			ps.setString(20, prmVehicleDataM.getTravalingRout());
			ps.setString(21, prmVehicleDataM.getUpdateBy());
			ps.setString(22, prmVehicleDataM.getContractNo());
			ps.setDouble(23, prmVehicleDataM.getWeight());
			ps.setString(24, prmVehicleDataM.getColor());
			ps.setString(25, prmVehicleDataM.getDrawDownStatus());
			ps.setString(26, prmVehicleDataM.getAppRecordID());
			
			ps.setInt(27, prmVehicleDataM.getVehicleID());
			
			returnRows = ps.executeUpdate();
		} catch (Exception e) {
			log.fatal(e.getLocalizedMessage());
			throw new OrigVehicleMException(e.getMessage());
		} finally {
			try {
				closeConnection(conn, ps);
			} catch (Exception e) {
				log.fatal(e.getLocalizedMessage());
			}
		}
		return returnRows;
	}
	
	public Vector loadModelVehicleDataMByCoClientID(String coClientId)
	throws OrigVehicleMException {
		try {
		    Vector resultVt = selectTableORIG_VEHICLE_BY_ID(coClientId);
			VehicleDataM vehicleM = null;
			if(resultVt != null&&resultVt.size()>0){
			    for(int i=0;i<resultVt.size();i++){
			        vehicleM = (VehicleDataM)resultVt.get(i);
			        InsuranceDataM insuranceDataM = ORIGDAOFactory.getOrigInsuranceMDAO().loadModelOrigInsuranceMByVehicleId(vehicleM.getVehicleID());
			        vehicleM.setInsuranceDataM(insuranceDataM);
			        LoanDataM loanDataM = ORIGDAOFactory.getOrigLoanMDAO().loadModelOrigLoanMByVehicleId(vehicleM.getVehicleID());
			        vehicleM.setLoanDataM(loanDataM);
			        }
				
			}
			return resultVt;
		} catch (Exception e) {
			log.fatal("Error >> ", e);
			throw new OrigVehicleMException(e.getMessage());
		}
	}
	
	private Vector selectTableORIG_VEHICLE_BY_ID(String coClientId)
	throws OrigVehicleMException {
		PreparedStatement ps = null;
		ResultSet rs = null;
		Vector resultVt = new Vector();
		Connection conn = null;
		try {
			//conn = Get Connection
			conn = getConnection();
			StringBuffer sql = new StringBuffer("");
			sql.append("SELECT CATEGORY,ENGINE_NUMBER,CAR ");
			sql.append(",BRAND,REGISTER_NUMBER,GEAR,MODEL,CHASSIS_NUMBER");
			sql.append(",LICENSE_TYPE,CC,YEAR,PROVINCE,KELOMETERS");
			sql
					.append(",REGISTER_DATE,CAR_USER,OCCUPY_DATE,EXPIRY_DATE,OBJECTIVE");
			sql
					.append(",CAR_PARK_LOCATION,TRAVALING_ROUT,CREATE_BY,CREATE_DATE,UPDATE_BY");
			sql.append(",UPDATE_DATE,CONTRACT_NO,VEHICLE_ID, DRAW_DOWN_STATUS, IDNO, WEIGHT, COLOR, APPLICATION_RECORD_ID ");
			sql.append(" FROM ORIG_VEHICLE WHERE IDNO = ? ");
			String dSql = String.valueOf(sql);
			log.debug("Sql=" + dSql);
			ps = conn.prepareStatement(dSql);
			rs = null;
			ps.setString(1, coClientId);
		
			rs = ps.executeQuery();
			VehicleDataM vehicleDataM = null;
			while (rs.next()) {
				vehicleDataM = new VehicleDataM();
				vehicleDataM.setCategory(rs.getString(1));
				vehicleDataM.setEngineNo(rs.getString(2));
				vehicleDataM.setCar(rs.getString(3));
				vehicleDataM.setBrand(rs.getString(4));
				vehicleDataM.setRegisterNo(rs.getString(5));
				vehicleDataM.setGear(rs.getString(6));
				vehicleDataM.setModel(rs.getString(7));
				vehicleDataM.setChassisNo(rs.getString(8));
				vehicleDataM.setLicenseType(rs.getString(9));
				vehicleDataM.setCC(rs.getDouble(10));
				vehicleDataM.setYear(rs.getInt(11));
				vehicleDataM.setProvince(rs.getString(12));
				vehicleDataM.setKelometers(rs.getDouble(13));
				vehicleDataM.setRegisterDate(rs.getDate(14));
				vehicleDataM.setCarUser(rs.getString(15));
				vehicleDataM.setOccupyDate(rs.getDate(16));
				vehicleDataM.setExpiryDate(rs.getDate(17));
				vehicleDataM.setObjective(rs.getString(18));
				vehicleDataM.setCarParkLocation(rs.getString(19));
				vehicleDataM.setTravalingRout(rs.getString(20));
				vehicleDataM.setCreateBy(rs.getString(21));
				vehicleDataM.setCreateDate(rs.getTimestamp(22));
				vehicleDataM.setUpdateBy(rs.getString(23));
				vehicleDataM.setUpdateDate(rs.getTimestamp(24));
				vehicleDataM.setContractNo(rs.getString(25));
				vehicleDataM.setVehicleID(rs.getInt(26));
				vehicleDataM.setDrawDownStatus(rs.getString(27));
				vehicleDataM.setIdNo(rs.getString(28));
				vehicleDataM.setWeight(rs.getDouble(29));
				vehicleDataM.setColor(rs.getString(30));
				vehicleDataM.setAppRecordID(rs.getString(31));
				
				resultVt.add(vehicleDataM);
			}
			return resultVt;
		} catch (Exception e) {
			log.fatal(e.getLocalizedMessage());
			throw new OrigVehicleMException(e.getMessage());
		} finally {
			try {
				closeConnection(conn, rs, ps);
			} catch (Exception e) {
				log.fatal(e.getLocalizedMessage());
			}
		}
		}
	
	public void saveUpdateModelVehicleDataMByVehicleId(VehicleDataM prmVehicleDataM)
	throws OrigVehicleMException {
	double returnRows = 0;
	try {
		logger.debug("prmVehicleDataM.getAppRecordID() = "+prmVehicleDataM.getAppRecordID());
		returnRows = updateTableORIG_VEHICLE_BY_ID(prmVehicleDataM);
		
		if (returnRows == 0) {
			log.debug("New record then can't update record in table ORIG_VEHICLE then call Insert method");
			createTableORIG_VEHICLE(prmVehicleDataM);
		}
		InsuranceDataM insuranceDataM = prmVehicleDataM.getInsuranceDataM();
		if(insuranceDataM != null){
			insuranceDataM.setVenhicleId(prmVehicleDataM.getVehicleID());
			insuranceDataM.setIdNo(prmVehicleDataM.getIdNo());
			insuranceDataM.setAppRecordID(prmVehicleDataM.getAppRecordID());
			logger.debug("prmVehicleDataM.getAppRecordID() = "+prmVehicleDataM.getAppRecordID());
			ORIGDAOFactory.getOrigInsuranceMDAO().saveUpdateModelOrigInsuranceMByVehicleId(insuranceDataM);
		}
		LoanDataM loanDataM = prmVehicleDataM.getLoanDataM();
		if(loanDataM != null){
		    loanDataM.setVehicleId(prmVehicleDataM.getVehicleID());
		    loanDataM.setIdNo(prmVehicleDataM.getIdNo());
		    loanDataM.setAppRecordID(prmVehicleDataM.getAppRecordID());
		    logger.debug("prmVehicleDataM.getAppRecordID() = "+prmVehicleDataM.getAppRecordID());
			ORIGDAOFactory.getOrigLoanMDAO().saveUpdateModelOrigLoanMForCreateProposal(loanDataM);
		}
		
	} catch (Exception e) {
		log.fatal(e.getLocalizedMessage());
		throw new OrigVehicleMException(e.getMessage());
	}
	
	}
	
	private double updateTableORIG_VEHICLE_BY_ID(VehicleDataM prmVehicleDataM)
	throws OrigVehicleMException {
		double returnRows = 0;
		PreparedStatement ps = null;
		Connection conn = null;
		try {
			//conn = Get Connection
			conn = getConnection();
			StringBuffer sql = new StringBuffer("");
			sql.append("UPDATE ORIG_VEHICLE ");
			sql.append(" SET CATEGORY=?,ENGINE_NUMBER=?,CAR=?");
			sql.append(",BRAND=?,REGISTER_NUMBER=?,GEAR=?,MODEL=?,CHASSIS_NUMBER=?");
			sql.append(",LICENSE_TYPE=?,CC=?,YEAR=?,PROVINCE=?,KELOMETERS=?");
			sql.append(",REGISTER_DATE=?,CAR_USER=?,OCCUPY_DATE=?,EXPIRY_DATE=?,OBJECTIVE=?");
			sql.append(",CAR_PARK_LOCATION=?,TRAVALING_ROUT=?,UPDATE_BY=?,UPDATE_DATE=SYSDATE,CONTRACT_NO=?, WEIGHT=?, COLOR=?, DRAW_DOWN_STATUS=?, APPLICATION_RECORD_ID=? ");
		
			sql.append(" WHERE VEHICLE_ID=?  ");
			String dSql = String.valueOf(sql);
			log.debug("Sql=" + dSql);
			ps = conn.prepareStatement(dSql);
			ps.setString(1, prmVehicleDataM.getCategory());
			ps.setString(2, prmVehicleDataM.getEngineNo());
			ps.setString(3, prmVehicleDataM.getCar());
			ps.setString(4, prmVehicleDataM.getBrand());
			ps.setString(5, prmVehicleDataM.getRegisterNo());
			ps.setString(6, prmVehicleDataM.getGear());
			ps.setString(7, prmVehicleDataM.getModel());
			ps.setString(8, prmVehicleDataM.getChassisNo());
			ps.setString(9, prmVehicleDataM.getLicenseType());
			ps.setDouble(10, prmVehicleDataM.getCC());
			ps.setInt(11, prmVehicleDataM.getYear());
			ps.setString(12, prmVehicleDataM.getProvince());
			ps.setDouble(13, prmVehicleDataM.getKelometers());
			ps.setDate(14, this.parseDate(prmVehicleDataM.getRegisterDate()));
			ps.setString(15, prmVehicleDataM.getCarUser());
			ps.setDate(16, this.parseDate(prmVehicleDataM.getOccupyDate()));
			ps.setDate(17, this.parseDate(prmVehicleDataM.getExpiryDate()));
			ps.setString(18, prmVehicleDataM.getObjective());
			ps.setString(19, prmVehicleDataM.getCarParkLocation());
			ps.setString(20, prmVehicleDataM.getTravalingRout());
			ps.setString(21, prmVehicleDataM.getUpdateBy());
			ps.setString(22, prmVehicleDataM.getContractNo());
			
			ps.setDouble(23, prmVehicleDataM.getWeight());
			ps.setString(24, prmVehicleDataM.getColor());
			ps.setString(25, prmVehicleDataM.getDrawDownStatus());
			ps.setString(26, prmVehicleDataM.getAppRecordID());
			
			ps.setInt(27, prmVehicleDataM.getVehicleID());
			
			returnRows = ps.executeUpdate();
		} catch (Exception e) {
			log.fatal(e.getLocalizedMessage());
			throw new OrigVehicleMException(e.getMessage());
		} finally {
			try {
				closeConnection(conn, ps);
			} catch (Exception e) {
				log.fatal(e.getLocalizedMessage());
			}
		}
		return returnRows;
		}
	
	public void deleteNewVehicleDataMByIdNo(String IdNo,String vehicleId)
	throws OrigVehicleMException {
		try {
		    String vehicleIdForInsAndLoan = "";
		    if(vehicleId==null||vehicleId.equals("")){
		        vehicleIdForInsAndLoan = select_VEHICLE_BY_IDNO(IdNo);
		        }else{
		            vehicleIdForInsAndLoan = vehicleId;
		            }
			ORIGDAOFactory.getOrigInsuranceMDAO().deleteModelOrigInsuranceMByIdNo(IdNo,vehicleId);
			ORIGDAOFactory.getOrigLoanMDAO().deleteModelOrigLoanMByIdNo(IdNo,vehicleId);
			deleteNEW_VEHICLE_BY_IDNO(IdNo,vehicleId);
		} catch (Exception e) {
			log.fatal(e.getLocalizedMessage());
			throw new OrigVehicleMException(e.getMessage());
		}
		
	}
	
	private void deleteNEW_VEHICLE_BY_IDNO(String IdNo,String vehicleId)
	throws OrigVehicleMException {
		PreparedStatement ps = null;
		Connection conn = null;
		try {
			//conn = Get Connection
			conn = getConnection();
			StringBuffer sql = new StringBuffer("");
			sql.append("DELETE ORIG_VEHICLE ");
			sql.append(" WHERE IDNO =? AND DRAW_DOWN_STATUS = 'NEW'");
			if(vehicleId!=null&&!vehicleId.equals("")){
				sql.append(" AND VEHICLE_ID NOT IN ("+vehicleId+")");
			    }
			String dSql = String.valueOf(sql);
			log.debug("Sql=" + dSql);
			ps = conn.prepareStatement(dSql);
			ps.setString(1, IdNo);
			ps.executeUpdate();
		
		} catch (Exception e) {
			log.fatal(e.getLocalizedMessage());
			throw new OrigVehicleMException(e.getMessage());
		} finally {
			try {
				closeConnection(conn, ps);
			} catch (Exception e) {
				log.fatal(e.getLocalizedMessage());
				throw new OrigVehicleMException(e.getMessage());
			}
		}
		
	}
	
	
	private String select_VEHICLE_BY_IDNO(String idNo)
	throws OrigVehicleMException {
		PreparedStatement ps = null;
		ResultSet rs = null;
		Connection conn = null;
		StringBuffer vehicleIdBf = new StringBuffer("");
		try {
			//conn = Get Connection
			conn = getConnection();
			StringBuffer sql = new StringBuffer("");
			sql.append("SELECT VEHICLE_ID ");
			sql.append(" FROM ORIG_VEHICLE WHERE IDNO = ? AND DRAW_DOWN_STATUS IN ('BOOKED','PROGRESS') ");
			String dSql = String.valueOf(sql);
			log.debug("Sql=" + dSql);
			ps = conn.prepareStatement(dSql);
			rs = null;
			ps.setString(1, idNo);
		
			rs = ps.executeQuery();
			VehicleDataM vehicleDataM = null;
			while (rs.next()) {
				vehicleIdBf.append("'"+rs.getString(1)+"',");
			}
			return String.valueOf(vehicleIdBf.substring(0,vehicleIdBf.length()-1));
		} catch (Exception e) {
			log.fatal(e.getLocalizedMessage());
			throw new OrigVehicleMException(e.getMessage());
		} finally {
			try {
				closeConnection(conn, rs, ps);
			} catch (Exception e) {
				log.fatal(e.getLocalizedMessage());
			}
		}
	}
	
	public VehicleDataM loadModelVehicleDataMByVehicleID(String vehicleId)
	throws OrigVehicleMException {
		try {
			VehicleDataM vehicleM = selectTableORIG_VEHICLE_BY_VEHICLE_ID(vehicleId);
			log.debug("vehicle id is: "+vehicleM.getVehicleID());
			log.debug("vehicleM.getAppRecordID() : "+vehicleM.getAppRecordID());
	        InsuranceDataM insuranceDataM = ORIGDAOFactory.getOrigInsuranceMDAO().loadModelOrigInsuranceMByVehicleId(vehicleM.getVehicleID());
	        vehicleM.setInsuranceDataM(insuranceDataM);
	        LoanDataM loanDataM = ORIGDAOFactory.getOrigLoanMDAO().loadModelOrigLoanMByVehicleId(vehicleM.getVehicleID());
	        vehicleM.setLoanDataM(loanDataM);
			return vehicleM;
		} catch (Exception e) {
			log.fatal("Error >> ", e);
			throw new OrigVehicleMException(e.getMessage());
		}
	}
	
	private VehicleDataM selectTableORIG_VEHICLE_BY_VEHICLE_ID(String vehicleId)
	throws OrigVehicleMException {
		PreparedStatement ps = null;
		ResultSet rs = null;
		Connection conn = null;
		try {
			//conn = Get Connection
			conn = getConnection();
			StringBuffer sql = new StringBuffer("");
			sql.append("SELECT CATEGORY,ENGINE_NUMBER,CAR ");
			sql.append(",BRAND,REGISTER_NUMBER,GEAR,MODEL,CHASSIS_NUMBER");
			sql.append(",LICENSE_TYPE,CC,YEAR,PROVINCE,KELOMETERS");
			sql
					.append(",REGISTER_DATE,CAR_USER,OCCUPY_DATE,EXPIRY_DATE,OBJECTIVE");
			sql
					.append(",CAR_PARK_LOCATION,TRAVALING_ROUT,CREATE_BY,CREATE_DATE,UPDATE_BY");
			sql.append(",UPDATE_DATE,CONTRACT_NO,VEHICLE_ID, DRAW_DOWN_STATUS, IDNO, WEIGHT, COLOR, APPLICATION_RECORD_ID ");
			sql.append(" FROM ORIG_VEHICLE WHERE VEHICLE_ID = ? ");
			String dSql = String.valueOf(sql);
			log.debug("Sql=" + dSql);
			ps = conn.prepareStatement(dSql);
			rs = null;
			ps.setString(1, vehicleId);
		
			rs = ps.executeQuery();
			VehicleDataM vehicleDataM = null;
			if (rs.next()) {
				vehicleDataM = new VehicleDataM();
				vehicleDataM.setCategory(rs.getString(1));
				vehicleDataM.setEngineNo(rs.getString(2));
				vehicleDataM.setCar(rs.getString(3));
				vehicleDataM.setBrand(rs.getString(4));
				vehicleDataM.setRegisterNo(rs.getString(5));
				vehicleDataM.setGear(rs.getString(6));
				vehicleDataM.setModel(rs.getString(7));
				vehicleDataM.setChassisNo(rs.getString(8));
				vehicleDataM.setLicenseType(rs.getString(9));
				vehicleDataM.setCC(rs.getDouble(10));
				vehicleDataM.setYear(rs.getInt(11));
				vehicleDataM.setProvince(rs.getString(12));
				vehicleDataM.setKelometers(rs.getDouble(13));
				vehicleDataM.setRegisterDate(rs.getDate(14));
				vehicleDataM.setCarUser(rs.getString(15));
				vehicleDataM.setOccupyDate(rs.getDate(16));
				vehicleDataM.setExpiryDate(rs.getDate(17));
				vehicleDataM.setObjective(rs.getString(18));
				vehicleDataM.setCarParkLocation(rs.getString(19));
				vehicleDataM.setTravalingRout(rs.getString(20));
				vehicleDataM.setCreateBy(rs.getString(21));
				vehicleDataM.setCreateDate(rs.getTimestamp(22));
				vehicleDataM.setUpdateBy(rs.getString(23));
				vehicleDataM.setUpdateDate(rs.getTimestamp(24));
				vehicleDataM.setContractNo(rs.getString(25));
				vehicleDataM.setVehicleID(rs.getInt(26));
				vehicleDataM.setDrawDownStatus(rs.getString(27));
				vehicleDataM.setIdNo(rs.getString(28));
				vehicleDataM.setWeight(rs.getDouble(29));
				vehicleDataM.setColor(rs.getString(30));
				vehicleDataM.setAppRecordID(rs.getString(31));
			}
			return vehicleDataM;
		} catch (Exception e) {
			log.fatal(e.getLocalizedMessage());
			throw new OrigVehicleMException(e.getMessage());
		} finally {
			try {
				closeConnection(conn, rs, ps);
			} catch (Exception e) {
				log.fatal(e.getLocalizedMessage());
			}
		}
		}
	
	/**
	 * @param prmVehicleDataM
	 * @return
	 */
	private double updateTableORIG_VEHICLEForChangeCar(VehicleDataM prmVehicleDataM)
			throws OrigVehicleMException {
		double returnRows = 0;
		PreparedStatement ps = null;
		Connection conn = null;
		try {
			//conn = Get Connection
			conn = getConnection();
			StringBuffer sql = new StringBuffer("");
			sql.append("UPDATE ORIG_VEHICLE ");
			sql.append(" SET CATEGORY=?,ENGINE_NUMBER=?,CAR=?");
			sql.append(",BRAND=?,REGISTER_NUMBER=?,GEAR=?,MODEL=?,CHASSIS_NUMBER=?");
			sql.append(",LICENSE_TYPE=?,CC=?,YEAR=?,PROVINCE=?,KELOMETERS=?");
			sql.append(",REGISTER_DATE=?,CAR_USER=?,OCCUPY_DATE=?,EXPIRY_DATE=?,OBJECTIVE=?");
			sql.append(",CAR_PARK_LOCATION=?,TRAVALING_ROUT=?,UPDATE_BY=?,UPDATE_DATE=SYSDATE,CONTRACT_NO=?, WEIGHT=?, COLOR=?, DRAW_DOWN_STATUS=? ");

			sql.append(" WHERE APPLICATION_RECORD_ID = ? AND VEHICLE_ID=?  ");
			String dSql = String.valueOf(sql);
			log.debug("Sql=" + dSql);
			ps = conn.prepareStatement(dSql);
			ps.setString(1, prmVehicleDataM.getCategory());
			ps.setString(2, prmVehicleDataM.getEngineNo());
			ps.setString(3, prmVehicleDataM.getCar());
			ps.setString(4, prmVehicleDataM.getBrand());
			ps.setString(5, prmVehicleDataM.getRegisterNo());
			ps.setString(6, prmVehicleDataM.getGear());
			ps.setString(7, prmVehicleDataM.getModel());
			ps.setString(8, prmVehicleDataM.getChassisNo());
			ps.setString(9, prmVehicleDataM.getLicenseType());
			ps.setDouble(10, prmVehicleDataM.getCC());
			ps.setInt(11, prmVehicleDataM.getYear());
			ps.setString(12, prmVehicleDataM.getProvince());
			ps.setDouble(13, prmVehicleDataM.getKelometers());
			ps.setDate(14, this.parseDate(prmVehicleDataM.getRegisterDate()));
			ps.setString(15, prmVehicleDataM.getCarUser());
			ps.setDate(16, this.parseDate(prmVehicleDataM.getOccupyDate()));
			ps.setDate(17, this.parseDate(prmVehicleDataM.getExpiryDate()));
			ps.setString(18, prmVehicleDataM.getObjective());
			ps.setString(19, prmVehicleDataM.getCarParkLocation());
			ps.setString(20, prmVehicleDataM.getTravalingRout());
			ps.setString(21, prmVehicleDataM.getUpdateBy());
			ps.setString(22, prmVehicleDataM.getContractNo());
			ps.setDouble(23, prmVehicleDataM.getWeight());
			ps.setString(24, prmVehicleDataM.getColor());
			ps.setString(25, prmVehicleDataM.getDrawDownStatus());
			
			ps.setString(26, prmVehicleDataM.getAppRecordID());
			ps.setInt(27, prmVehicleDataM.getVehicleID());
			
			returnRows = ps.executeUpdate();
		} catch (Exception e) {
			log.fatal(e.getLocalizedMessage());
			throw new OrigVehicleMException(e.getMessage());
		} finally {
			try {
				closeConnection(conn, ps);
			} catch (Exception e) {
				log.fatal(e.getLocalizedMessage());
			}
		}
		return returnRows;
	}
	
	public String selectAppRecordIdByVehicleId(int idNo)throws OrigVehicleMException {
		PreparedStatement ps = null;
		ResultSet rs = null;
//		StringBuffer vehicleIdBf = new StringBuffer("");
		Connection conn = null;
		try {
			//conn = Get Connection
			conn = getConnection();
			StringBuffer sql = new StringBuffer("");
			sql.append("SELECT APPLICATION_RECORD_ID ");
			sql.append(" FROM ORIG_VEHICLE WHERE VEHICLE_ID = ?");
			String dSql = String.valueOf(sql);
			log.debug("Sql=" + dSql);
			log.debug("idNo=" + idNo);
			ps = conn.prepareStatement(dSql);
			rs = null;
			ps.setInt(1, idNo);
		
			rs = ps.executeQuery();
			String appRecordId = null;
			while (rs.next()) {
				appRecordId = rs.getString(1);
			}
			return appRecordId;
		} catch (Exception e) {
			log.fatal("Error >> ", e);
			throw new OrigVehicleMException(e.getMessage());
		} finally {
			try {
				closeConnection(conn, rs, ps);
			} catch (Exception e) {
				log.fatal("Error >> ", e);
			}
		}
	}
	public int updateVehicleForChangeCar(VehicleDataM vehicleDataM)throws OrigVehicleMException{
		int returnRow = 0;
		try {
			
			returnRow = updateVehicleForOldCar(vehicleDataM.getVehicleID(), vehicleDataM.getAppRecordID());
			returnRow = updateVehicleForNewCar(vehicleDataM.getVehicleID(), vehicleDataM.getAppRecordID());
			InsuranceDataM insuranceDataM = vehicleDataM.getInsuranceDataM();
			ORIGDAOFactory.getOrigInsuranceMDAO().updateInsuranceForChangeCar(insuranceDataM.getInsuranceID(), vehicleDataM.getAppRecordID());
			
			return returnRow;
		} catch (Exception e) {
			log.fatal("Error >> ", e);
			throw new OrigVehicleMException(e.getMessage());
		}
	}
	
	private int updateVehicleForNewCar(int vecicleId, String apprecordID)throws OrigVehicleMException{
		int returnRows = 0;
		PreparedStatement ps = null;
		Connection conn = null;
		try {
			//conn = Get Connection
			conn = getConnection();
			StringBuffer sql = new StringBuffer("");
			sql.append("UPDATE ORIG_VEHICLE ");
			sql.append(" SET APPLICATION_RECORD_ID=?, DRAW_DOWN_STATUS='PROGRESS'");
			sql.append(" WHERE VEHICLE_ID = ? ");
			
			String dSql = String.valueOf(sql);
			log.debug("Sql=" + dSql);
			log.debug("vecicleId=" + vecicleId);
			
			ps = conn.prepareStatement(dSql);
			ps.setString(1, apprecordID);
			ps.setInt(2, vecicleId);
			
			returnRows = ps.executeUpdate();
		} catch (Exception e) {
			log.fatal("Error >> ", e);
			throw new OrigVehicleMException(e.getMessage());
		} finally {
			try {
				closeConnection(conn, ps);
			} catch (Exception e) {
				log.fatal("Error >> ", e);
			}
		}
		return returnRows;
	}
	private int updateVehicleForOldCar(int vecicleId, String apprecordID)throws OrigVehicleMException {
		int returnRows = 0;
		PreparedStatement ps = null;
		Connection conn = null;
		try {
			//conn = Get Connection
			conn = getConnection();
			StringBuffer sql = new StringBuffer("");
			sql.append("UPDATE ORIG_VEHICLE ");
			sql.append(" SET APPLICATION_RECORD_ID=null,DRAW_DOWN_STATUS='NEW'");
			sql.append(" WHERE VEHICLE_ID <> ? AND APPLICATION_RECORD_ID = ? ");
			
			String dSql = String.valueOf(sql);
			log.debug("Sql=" + dSql);
			log.debug("vecicleId=" + vecicleId);
			
			ps = conn.prepareStatement(dSql);
			ps.setInt(1, vecicleId);
			ps.setString(2, apprecordID);
			
			returnRows = ps.executeUpdate();
		} catch (Exception e) {
			log.fatal("Error >> ", e);
			throw new OrigVehicleMException(e.getMessage());
		} finally {
			try {
				closeConnection(conn, ps);
			} catch (Exception e) {
				log.fatal("Error >> ", e);
			}
		}
		return returnRows;
	}
	
   public String loandCarModelDesc(String carModel,String carBrand){
   	String result="";
    if(carModel!=null&&!"".equalsIgnoreCase(carModel)&& carBrand!=null&&!"".equalsIgnoreCase(carBrand)){
    	PreparedStatement ps = null;
    	ResultSet rs=null;    	
    	Connection conn = null;
    	try {
    	StringBuffer sql = new StringBuffer();    	
    	sql.append("SELECT MODEL, THDESC, ENDESC, BRAND, GEAR, HORPWR, WEIGHT ");
		sql.append("FROM HPTBHP28 ");	
		sql.append("WHERE MODEL = ? ");
		sql.append("AND BRAND = ? ");		  
		conn = getConnection();
		String dSql = String.valueOf(sql);
		log.debug("Sql=" + dSql);
		ps = conn.prepareStatement(dSql);
		ps.setString(1,carModel);
		ps.setString(2,carBrand);
		rs=ps.executeQuery();
		if(rs.next()){
		 result=rs.getString(2);
		}
    	}catch (Exception e) {
			log.fatal("Error >> ", e);			 
		} finally {
			try {
				closeConnection(conn,rs, ps);
			} catch (Exception e) {
				log.fatal("Error >> ", e);
			}
		}
        
    }	
   return result;
   }
   
   public Vector loadVehicleDetailByCustomerId(String idNo)throws OrigVehicleMException {
		PreparedStatement ps = null;
		ResultSet rs = null;
//		StringBuffer vehicleIdBf = new StringBuffer("");
		Connection conn = null;
		try {
			//conn = Get Connection
			conn = getConnection();
			StringBuffer sql = new StringBuffer("");
			sql.append("SELECT V.VEHICLE_ID, (SELECT K.CLTGRP FROM ORIG_PERSONAL_INFO K WHERE K.IDNO = V.IDNO ) AS CLIENT_GROUP, V.CATEGORY, " +
					"V.BRAND, V.CAR, V.CHASSIS_NUMBER, V.GEAR, V.DRAW_DOWN_STATUS, V.IDNO, " +
					"(SELECT K.CUSTOMER_TYPE FROM ORIG_PERSONAL_INFO K WHERE K.IDNO = V.IDNO ) " +
					"AS CUST_TYPE, CONTRACT_NO FROM ORIG_VEHICLE V");
			sql.append(" WHERE V.IDNO = ? ");
			sql.append(" ORDER BY V.VEHICLE_ID ");
			String dSql = String.valueOf(sql);
			log.debug("Sql=" + dSql);
			log.debug("idNo=" + idNo);
			
			ps = conn.prepareStatement(dSql);
			rs = null;
			ps.setString(1, idNo);
		
			rs = ps.executeQuery();
			VehicleDataM vehicleDataM;
			Vector result = new Vector();
			while (rs.next()) {
				vehicleDataM = new VehicleDataM();
				vehicleDataM.setVehicleID(rs.getInt(1));
				vehicleDataM.setClientGroup(rs.getString(2));
				vehicleDataM.setCategory(rs.getString(3));
				vehicleDataM.setBrand(rs.getString(4));
				vehicleDataM.setCar(rs.getString(5));
				vehicleDataM.setChassisNo(rs.getString(6));
				vehicleDataM.setGear(rs.getString(7));
				vehicleDataM.setDrawDownStatus(rs.getString(8));
				vehicleDataM.setIdNo(rs.getString(9));
				vehicleDataM.setCustomerType(rs.getString(10));
				vehicleDataM.setContractNo(rs.getString(11));
				
				result.add(vehicleDataM);
			}
			return result;
		} catch (Exception e) {
			log.fatal("Error >> ", e);
			throw new OrigVehicleMException(e.getMessage());
		} finally {
			try {
				closeConnection(conn, rs, ps);
			} catch (Exception e) {
				log.fatal("Error >> ", e);
			}
		}
	}
}
