/*
 * Created on Sep 27, 2007
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
import com.eaf.orig.shared.dao.exceptions.OrigCustomerAddressMException;
import com.eaf.orig.shared.ejb.ORIGGeneratorManager;
import com.eaf.orig.shared.model.AddressDataM;
import com.eaf.orig.shared.service.ORIGEJBService;

/**
 * @author Sankom Sanpunya
 *
 * Type: OrigCustomerAddressMDAOImpl
 */
public class OrigCustomerAddressMDAOImpl extends OrigObjectDAO implements
		OrigCustomerAddressMDAO {
	private static Logger log = Logger.getLogger(OrigCustomerAddressMDAOImpl.class);
	/**
	 * 
	 */
	public OrigCustomerAddressMDAOImpl() {
		super();
		
	}

	/* (non-Javadoc)
	 * @see com.eaf.orig.share.dao.OrigCustomerAddressMDAO#createModelOrigCustomerAddressM(com.eaf.orig.shared.model.AddressDataM)
	 */
	public void createModelOrigCustomerAddressM(AddressDataM prmAddressDataM)
			throws OrigCustomerAddressMException {
		try {
			createTableORIG_CUSTOMER_ADDRESS(prmAddressDataM, "");
		} catch (Exception e) {
			log.fatal(e.getLocalizedMessage());
			throw new OrigCustomerAddressMException(e.getMessage());
		}

	}

	/**
	 * @param prmAddressDataM
	 */
	private void createTableORIG_CUSTOMER_ADDRESS(AddressDataM prmAddressDataM, String personalID) throws OrigCustomerAddressMException{
		PreparedStatement ps = null;
		Connection conn = null;
		try {
			//conn = Get Connection
			conn = getConnection();
			StringBuffer sql = new StringBuffer("");
			sql.append("INSERT INTO ORIG_PERSONAL_ADDRESS ");
			sql.append("( PERSONAL_ID,ADDRESS_ID,ADDRESS_TYPE,ADDRESS");
			sql.append(",VILAPT,FLOOR,ROOM,MOO,SOI");
			sql.append(",ROAD,TAMBOL,AMPHUR,PROVINCE,ZIPCODE");
			sql.append(",PHONE1,EXT1,PHONE2,EXT2,MOBILE");
			sql.append(",FAX,FAX_EXT,EMAIL,RESIDEY,ADRSTS");
			sql.append(",CONTACT,HOUSEIDNO,REMARK,CREATE_DATE,CREATE_BY");
			sql.append(",UPDATE_DATE,UPDATE_BY, SEQ");
			sql.append(" ) VALUES (?,?,?,?  ,?,?,?,?,?  ,?,?,?,?,? ,?,?,?,?,?");
			sql.append(" ,?,?,?,?,? ,?,?,?,SYSDATE,? ,SYSDATE,?     ,1)");
			String dSql = String.valueOf(sql);
			log.debug("Sql="+dSql);
			ps = conn.prepareStatement(dSql);
			ps.setString(1, personalID);
			ps.setString(2, prmAddressDataM.getAddressID());
			ps.setString(3, prmAddressDataM.getAddressType());
			ps.setString(4, prmAddressDataM.getAddressNo());
			
			ps.setString(5, prmAddressDataM.getApartment());
			ps.setString(6, prmAddressDataM.getFloor());
			ps.setString(7, prmAddressDataM.getRoom());
			ps.setString(8, prmAddressDataM.getMoo());
			ps.setString(9, prmAddressDataM.getSoi());
			
			ps.setString(10, prmAddressDataM.getRoad());
			ps.setString(11, prmAddressDataM.getTambol());
			ps.setString(12, prmAddressDataM.getAmphur());
			ps.setString(13, prmAddressDataM.getProvince());
			ps.setString(14, prmAddressDataM.getZipcode());
			
			ps.setString(15, prmAddressDataM.getPhoneNo1());
			ps.setString(16, prmAddressDataM.getPhoneExt1());
			ps.setString(17, prmAddressDataM.getPhoneNo2());
			ps.setString(18, prmAddressDataM.getPhoneExt2());
			ps.setString(19, prmAddressDataM.getMobileNo());
			
			ps.setString(20, prmAddressDataM.getFaxNo());
			ps.setString(21, prmAddressDataM.getFaxExt());
			ps.setString(22, prmAddressDataM.getEmail());
			ps.setBigDecimal(23, prmAddressDataM.getResideYear());
			ps.setString(24, prmAddressDataM.getStatus());
			
			ps.setString(25, prmAddressDataM.getContactPerson());
			ps.setString(26, prmAddressDataM.getHouseID());
			ps.setString(27, prmAddressDataM.getRemark());
			ps.setString(28, prmAddressDataM.getCreateBy());
			
			ps.setString(29, prmAddressDataM.getUpdateBy());
			
			ps.executeUpdate();
		} catch (Exception e) {
			log.fatal(e.getLocalizedMessage());
			throw new OrigCustomerAddressMException(e.getMessage());
		} finally {
			try {
				closeConnection(conn, ps);
			} catch (Exception e) {
				log.fatal(e.getLocalizedMessage());
			}
		}
		
	}

	/* (non-Javadoc)
	 * @see com.eaf.orig.share.dao.OrigCustomerAddressMDAO#deleteModelOrigCustomerAddressM(com.eaf.orig.shared.model.AddressDataM)
	 */
	public void deleteModelOrigCustomerAddressM(AddressDataM prmAddressDataM)
			throws OrigCustomerAddressMException {
		try {
			deleteTableORIG_CUSTOMER_ADDRESS(prmAddressDataM);
		} catch (Exception e) {
			log.fatal(e.getLocalizedMessage());
			throw new OrigCustomerAddressMException(e.getMessage());
		}

	}

 

	/**
	 * @param prmAddressDataM
	 */
	private void deleteTableORIG_CUSTOMER_ADDRESS(AddressDataM prmAddressDataM)throws OrigCustomerAddressMException {
		PreparedStatement ps = null;
		Connection conn = null;
		try {
			//conn = Get Connection
			conn = getConnection();
			StringBuffer sql = new StringBuffer("");
			sql.append("DELETE ORIG_CUSTOMER_ADDRESS ");
			sql.append(" WHERE APPLICATION_RECORD_ID = ? and IDNO=? and ADDRESS_TYPE=? and SEQ=? and CMPCDE=?");
			String dSql = String.valueOf(sql);
			log.debug("Sql="+dSql);
			ps = conn.prepareStatement(dSql);
			ps.setString(1, prmAddressDataM.getApplicationRecordId());
			ps.setString(2, prmAddressDataM.getIdNo());
			ps.setString(3, prmAddressDataM.getAddressType());
			ps.setInt(4,prmAddressDataM.getAddressSeq());
			ps.setString(5,prmAddressDataM.getCmpCode());
			ps.executeUpdate();

		} catch (Exception e) {
			log.fatal(e.getLocalizedMessage());
			throw new OrigCustomerAddressMException(e.getMessage());
		} finally {
			try {
				closeConnection(conn, ps);
			} catch (Exception e) {
				log.fatal(e.getLocalizedMessage());
				throw new OrigCustomerAddressMException(e.getMessage());
			}
		}
		
	}

	/* (non-Javadoc)
	 * @see com.eaf.orig.share.dao.OrigCustomerAddressMDAO#loadModelOrigCustomerAddressM(java.lang.String)
	 */
	public Vector loadModelOrigCustomerAddressM(String applicationRecordId, String personalID, String providerUrlEXT, String jndiEXT)
			throws OrigCustomerAddressMException {
		try {
			Vector result = selectTableORIG_CUSTOMER_ADDRESS(applicationRecordId,personalID, providerUrlEXT, jndiEXT);
			return result;
		} catch (Exception e) {
			log.fatal(e.getLocalizedMessage());
			throw new OrigCustomerAddressMException(e.getMessage());
		}
	}

 

	/**
	 * @param applicationRecordId
	 * @return
	 */
	private Vector selectTableORIG_CUSTOMER_ADDRESS(String applicationRecordId,String personalID, String providerUrlEXT, String jndiEXT)throws OrigCustomerAddressMException {
		PreparedStatement ps = null;
		ResultSet rs = null;
		Connection conn = null;
		try {
			//conn = Get Connection
			conn = getConnection();
			StringBuffer sql = new StringBuffer("");
			/*sql
					.append("SELECT IDNO ,ADDRESS_TYPE  ,SEQ,CREATE_BY,CREATE_DATE  ,UPDATE_BY,UPDATE_DATE,ORIG_OWNER ,CMPCDE   ");
			sql
					.append("FROM ORIG_CUSTOMER_ADDRESS WHERE APPLICATION_RECORD_ID = ? AND IDNO=? ");
			*/
			
			sql.append("SELECT  ");
			sql.append("ADDRESS_ID,PERSONAL_ID,ADDRESS_TYPE,ADDRESS");
			sql.append(",VILAPT,FLOOR,ROOM,MOO,SOI");
			sql.append(",ROAD,TAMBOL,AMPHUR,PROVINCE,ZIPCODE");
			sql.append(",PHONE1,EXT1,PHONE2,EXT2,MOBILE");
			sql.append(",FAX,FAX_EXT,EMAIL,RESIDEY,ADRSTS");
			sql.append(",CONTACT,HOUSEIDNO,REMARK,CREATE_DATE,CREATE_BY");
			sql.append(",UPDATE_DATE,UPDATE_BY");
			sql.append(" FROM ORIG_PERSONAL_ADDRESS WHERE PERSONAL_ID = ? ORDER BY ADDRESS_TYPE");
			
			String dSql = String.valueOf(sql);
			log.debug("Sql="+dSql);
			ps = conn.prepareStatement(dSql);
			rs = null;
			/*ps.setString(1, applicationRecordId);
			ps.setString(2, personalID);*/
			ps.setString(1, personalID);
			rs = ps.executeQuery();
			int count = 1;
			Vector vt = new Vector();
			while (rs.next()) {
				AddressDataM prmAddressDataM = new AddressDataM();
				/*prmAddressDataM.setApplicationRecordId(applicationRecordId);
				prmAddressDataM.setIdNo(rs.getString(1));
				prmAddressDataM.setAddressType(rs.getString(2));
				prmAddressDataM.setAddressSeq(rs.getInt(3));
				prmAddressDataM.setCreateBy(rs.getString(4));
				prmAddressDataM.setCreateDate(rs.getTimestamp(5));
				prmAddressDataM.setUpdateBy(rs.getString(6));
				prmAddressDataM.setUpdateDate(rs.getTimestamp(7));
				prmAddressDataM.setOrigOwner(rs.getString(8));
				prmAddressDataM.setCmpCode(rs.getString(9));*/
				prmAddressDataM.setAddressID(rs.getString(1));
				prmAddressDataM.setAddressType(rs.getString(3));
				prmAddressDataM.setAddressSeq(count++);		//	For manage table list 
				prmAddressDataM.setAddressNo(rs.getString(4));
				
				prmAddressDataM.setApartment(rs.getString(5));
				prmAddressDataM.setFloor(rs.getString(6));
				prmAddressDataM.setRoom(rs.getString(7));
				prmAddressDataM.setMoo(rs.getString(8));
				prmAddressDataM.setSoi(rs.getString(9));

				prmAddressDataM.setRoad(rs.getString(10));
				prmAddressDataM.setTambol(rs.getString(11));
				prmAddressDataM.setAmphur(rs.getString(12));
				prmAddressDataM.setProvince(rs.getString(13));
				prmAddressDataM.setZipcode(rs.getString(14));
				
				prmAddressDataM.setPhoneNo1(rs.getString(15));
				prmAddressDataM.setPhoneExt1(rs.getString(16));
				prmAddressDataM.setPhoneNo2(rs.getString(17));
				prmAddressDataM.setPhoneExt2(rs.getString(18));
				prmAddressDataM.setMobileNo(rs.getString(19));
				
				prmAddressDataM.setFaxNo(rs.getString(20));
				prmAddressDataM.setFaxExt(rs.getString(21));
				prmAddressDataM.setEmail(rs.getString(22));
				prmAddressDataM.setResideYear(rs.getBigDecimal(23));
				prmAddressDataM.setStatus(rs.getString(24));
				
				prmAddressDataM.setContactPerson(rs.getString(25));
				prmAddressDataM.setHouseID(rs.getString(26));
				prmAddressDataM.setRemark(rs.getString(27));
				prmAddressDataM.setCreateDate(rs.getTimestamp(28));
				prmAddressDataM.setCreateBy(rs.getString(29));
				
				prmAddressDataM.setUpdateDate(rs.getTimestamp(30));
				prmAddressDataM.setUpdateBy(rs.getString(31));
				
				//Load in EXT
				/*ApplicationEXTManager applicationEXTManager = ORIGEJBService.getApplicationEXTManager(providerUrlEXT, jndiEXT);
				prmAddressDataM = applicationEXTManager.loadPersonalAddress(prmAddressDataM);
				*/
				vt.add(prmAddressDataM);
			}
			return vt;
		} catch (Exception e) {
			log.fatal(e.getLocalizedMessage());
			throw new OrigCustomerAddressMException(e.getMessage());
		} finally {
			try {
				closeConnection(conn, rs, ps);
			} catch (Exception e) {
				log.fatal(e.getLocalizedMessage());
			}
		}
	}
	
	/* (non-Javadoc)
	 * @see com.eaf.orig.share.dao.OrigCustomerAddressMDAO#loadModelOrigCustomerAddressM(java.lang.String)
	 */
	public Vector loadModelOrigCustomerAddressMByPersonalID(String personalID)
			throws OrigCustomerAddressMException {
		try {
			Vector result = selectTableORIG_CUSTOMER_ADDRESS(personalID);
			return result;
		} catch (Exception e) {
			log.fatal(e.getLocalizedMessage());
			throw new OrigCustomerAddressMException(e.getMessage());
		}
	}
	
	/**
	 * @param applicationRecordId
	 * @return
	 */
	private Vector selectTableORIG_CUSTOMER_ADDRESS(String personalID)throws OrigCustomerAddressMException {
		PreparedStatement ps = null;
		ResultSet rs = null;
		Connection conn = null;
		try {
			//conn = Get Connection
			conn = getConnection();
			StringBuffer sql = new StringBuffer("");
			/*sql
					.append("SELECT IDNO ,ADDRESS_TYPE  ,SEQ,CREATE_BY,CREATE_DATE  ,UPDATE_BY,UPDATE_DATE,ORIG_OWNER ,CMPCDE   ");
			sql
					.append("FROM ORIG_CUSTOMER_ADDRESS WHERE APPLICATION_RECORD_ID = ? AND IDNO=? ");
			*/
			
			sql.append("SELECT  ");
			sql.append("ADDRESS_ID,PERSONAL_ID,ADDRESS_TYPE,ADDRESS");
			sql.append(",VILAPT,FLOOR,ROOM,MOO,SOI");
			sql.append(",ROAD,TAMBOL,AMPHUR,PROVINCE,ZIPCODE");
			sql.append(",PHONE1,EXT1,PHONE2,EXT2,MOBILE");
			sql.append(",FAX,FAX_EXT,EMAIL,RESIDEY,ADRSTS");
			sql.append(",CONTACT,HOUSEIDNO,REMARK,CREATE_DATE,CREATE_BY");
			sql.append(",UPDATE_DATE,UPDATE_BY");
			sql.append(" FROM ORIG_PERSONAL_ADDRESS WHERE PERSONAL_ID = ? ORDER BY ADDRESS_TYPE");
			
			String dSql = String.valueOf(sql);
			log.debug("Sql="+dSql);
			ps = conn.prepareStatement(dSql);
			rs = null;
			/*ps.setString(1, applicationRecordId);
			ps.setString(2, personalID);*/
			ps.setString(1, personalID);
			rs = ps.executeQuery();
			int count = 1;
			Vector vt = new Vector();
			while (rs.next()) {
				AddressDataM prmAddressDataM = new AddressDataM();
				/*prmAddressDataM.setApplicationRecordId(applicationRecordId);
				prmAddressDataM.setIdNo(rs.getString(1));
				prmAddressDataM.setAddressType(rs.getString(2));
				prmAddressDataM.setAddressSeq(rs.getInt(3));
				prmAddressDataM.setCreateBy(rs.getString(4));
				prmAddressDataM.setCreateDate(rs.getTimestamp(5));
				prmAddressDataM.setUpdateBy(rs.getString(6));
				prmAddressDataM.setUpdateDate(rs.getTimestamp(7));
				prmAddressDataM.setOrigOwner(rs.getString(8));
				prmAddressDataM.setCmpCode(rs.getString(9));*/
				prmAddressDataM.setAddressID(rs.getString(1));
				prmAddressDataM.setAddressType(rs.getString(3));
				prmAddressDataM.setAddressSeq(count++);
				prmAddressDataM.setAddressNo(rs.getString(4));
				
				prmAddressDataM.setApartment(rs.getString(5));
				prmAddressDataM.setFloor(rs.getString(6));
				prmAddressDataM.setRoom(rs.getString(7));
				prmAddressDataM.setMoo(rs.getString(8));
				prmAddressDataM.setSoi(rs.getString(9));

				prmAddressDataM.setRoad(rs.getString(10));
				prmAddressDataM.setTambol(rs.getString(11));
				prmAddressDataM.setAmphur(rs.getString(12));
				prmAddressDataM.setProvince(rs.getString(13));
				prmAddressDataM.setZipcode(rs.getString(14));
				
				prmAddressDataM.setPhoneNo1(rs.getString(15));
				prmAddressDataM.setPhoneExt1(rs.getString(16));
				prmAddressDataM.setPhoneNo2(rs.getString(17));
				prmAddressDataM.setPhoneExt2(rs.getString(18));
				prmAddressDataM.setMobileNo(rs.getString(19));
				
				prmAddressDataM.setFaxNo(rs.getString(20));
				prmAddressDataM.setFaxExt(rs.getString(21));
				prmAddressDataM.setEmail(rs.getString(22));
				prmAddressDataM.setResideYear(rs.getBigDecimal(23));
				prmAddressDataM.setStatus(rs.getString(24));
				
				prmAddressDataM.setContactPerson(rs.getString(25));
				prmAddressDataM.setHouseID(rs.getString(26));
				prmAddressDataM.setRemark(rs.getString(27));
				prmAddressDataM.setCreateDate(rs.getTimestamp(28));
				prmAddressDataM.setCreateBy(rs.getString(29));
				
				prmAddressDataM.setUpdateDate(rs.getTimestamp(30));
				prmAddressDataM.setUpdateBy(rs.getString(31));
				
				//Load in EXT
				/*ApplicationEXTManager applicationEXTManager = ORIGEJBService.getApplicationEXTManager(providerUrlEXT, jndiEXT);
				prmAddressDataM = applicationEXTManager.loadPersonalAddress(prmAddressDataM);
				*/
				vt.add(prmAddressDataM);
			}
			return vt;
		} catch (Exception e) {
			log.fatal(e.getLocalizedMessage());
			throw new OrigCustomerAddressMException(e.getMessage());
		} finally {
			try {
				closeConnection(conn, rs, ps);
			} catch (Exception e) {
				log.fatal(e.getLocalizedMessage());
			}
		}
	}

	/* (non-Javadoc)
	 * @see com.eaf.orig.share.dao.OrigCustomerAddressMDAO#saveUpdateModelOrigCustomerAddressM(com.eaf.orig.shared.model.AddressDataM)
	 */
	public void saveUpdateModelOrigCustomerAddressM(AddressDataM prmAddressDataM, String personalID)
			throws OrigCustomerAddressMException {
		double returnRows = 0;
		try {
			returnRows = updateTableORIG_CUSTOMER_ADDRESS(prmAddressDataM, personalID);
			if (returnRows == 0) {
				log.debug("New record then can't update record in table ORIG_CUSTOMER_ADDRESS then call Insert method");
				//String addressID = ORIGDAOFactory.getUniqueIDGeneratorDAO2().getUniqByName(EJBConstant.ADDRESS_ID);
				//prmAddressDataM.setAddressID(addressID);
				ORIGGeneratorManager generatorManager = ORIGEJBService.getGeneratorManager();
				String addressID = generatorManager.generateUniqueIDByName(EJBConstant.ADDRESS_ID);
				prmAddressDataM.setAddressID(addressID);
				createTableORIG_CUSTOMER_ADDRESS(prmAddressDataM, personalID);
			}
		} catch (Exception e) {
			log.fatal(e.getLocalizedMessage());
			throw new OrigCustomerAddressMException(e.getMessage());
		}


	}

	/**
	 * @param prmAddressDataM
	 * @return
	 */
	private double updateTableORIG_CUSTOMER_ADDRESS(AddressDataM prmAddressDataM, String personalID) throws OrigCustomerAddressMException{
		double returnRows = 0;
		PreparedStatement ps = null;
		Connection conn = null;
		try {
			//conn = Get Connection
			conn = getConnection();
			StringBuffer sql = new StringBuffer("");
			/*sql.append("ADDRESS_ID,PERSONAL_ID,ADDRESS_TYPE,SEQ,ADDRESS");
			sql.append(",VILAPT,FLOOR,ROOM,MOO,SOI");
			sql.append(",ROAD,TAMBOL,AMPHUR,PROVINCE,ZIPCODE");
			sql.append(",PHONE1,EXT1,PHONE2,EXT2,MOBILE");
			sql.append(",FAX,FAX_EXT,EMAIL,RESIDEY,ADRSTS");
			sql.append(",CONTACT,HOUSEIDNO,REMARK,CREATE_DATE,CREATE_BY");
			sql.append(",UPDATE_DATE,UPDATE_BY");
			*/
			
			sql.append("UPDATE ORIG_PERSONAL_ADDRESS ");
			sql.append(" SET ADDRESS_TYPE = ?,ADDRESS = ?,VILAPT = ?,FLOOR = ?,ROOM = ?");
			sql.append(" ,SOI = ?, MOO = ?,ROAD = ?,TAMBOL = ?,AMPHUR = ?");
			sql.append(" ,PROVINCE = ?, ZIPCODE = ?,PHONE1 = ?,EXT1 = ?,PHONE2 = ?");
			sql.append(" ,EXT2 = ?, MOBILE = ?,FAX = ?,FAX_EXT = ?,EMAIL = ?");
			sql.append(" ,RESIDEY = ?,ADRSTS = ?,CONTACT = ?, HOUSEIDNO = ?,REMARK = ?");
			sql.append(" ,UPDATE_BY = ?,UPDATE_DATE = SYSDATE ");
			sql.append(" WHERE PERSONAL_ID = ? AND ADDRESS_ID = ? AND ADDRESS_TYPE = ? ");
			String dSql = String.valueOf(sql);
			log.debug("Sql="+dSql);
			log.debug(">>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>personalID" + personalID);
			log.debug(">>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>prmAddressDataM.getAddressID()" + prmAddressDataM.getAddressID());
			log.debug(">>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>prmAddressDataM.getAddressType()" + prmAddressDataM.getAddressType());
			ps = conn.prepareStatement(dSql);
			ps.setString(1, prmAddressDataM.getAddressType());
			ps.setString(2, prmAddressDataM.getAddressNo());
			ps.setString(3, prmAddressDataM.getApartment());
			ps.setString(4, prmAddressDataM.getFloor());
			ps.setString(5, prmAddressDataM.getRoom());
			
			ps.setString(6, prmAddressDataM.getSoi());
			ps.setString(7, prmAddressDataM.getMoo());
			ps.setString(8, prmAddressDataM.getRoad());
			ps.setString(9, prmAddressDataM.getTambol());
			ps.setString(10, prmAddressDataM.getAmphur());
			
			ps.setString(11, prmAddressDataM.getProvince());
			ps.setString(12, prmAddressDataM.getZipcode());
			ps.setString(13, prmAddressDataM.getPhoneNo1());
			ps.setString(14, prmAddressDataM.getPhoneExt1());
			ps.setString(15, prmAddressDataM.getPhoneNo2());
			
			ps.setString(16, prmAddressDataM.getPhoneExt2());
			ps.setString(17, prmAddressDataM.getMobileNo());
			ps.setString(18, prmAddressDataM.getFaxNo());
			ps.setString(19, prmAddressDataM.getFaxExt());
			ps.setString(20, prmAddressDataM.getEmail());
			
			ps.setBigDecimal(21, prmAddressDataM.getResideYear());
			ps.setString(22, prmAddressDataM.getStatus());
			ps.setString(23, prmAddressDataM.getContactPerson());
			ps.setString(24, prmAddressDataM.getHouseID());
			ps.setString(25, prmAddressDataM.getRemark());
			
			ps.setString(26, prmAddressDataM.getUpdateBy());
			
	     	ps.setString(27, personalID);
	     	ps.setString(28, prmAddressDataM.getAddressID());
	    	ps.setString(29, prmAddressDataM.getAddressType());
			returnRows = ps.executeUpdate();
		} catch (Exception e) {
			log.fatal(e.getLocalizedMessage());
			throw new OrigCustomerAddressMException(e.getMessage());
		} finally {
			try {
				closeConnection(conn, ps);
			} catch (Exception e) {
				log.fatal(e.getLocalizedMessage());
			}
		}
		return returnRows;
	}
	
	/** delete not in key**/
	/*public void deleteNotInKeyTableORIG_CUSTOMER_ADDRESS(Vector addressVect, String appRecordID, String cmpCode, String idNo) throws OrigCustomerAddressMException{
		PreparedStatement ps = null;
        try {
            // conn = Get Connection
            conn = getConnection();

            StringBuffer sql = new StringBuffer("");
            sql.append("DELETE FROM ORIG_CUSTOMER_ADDRESS");
            sql.append(" WHERE APPLICATION_RECORD_ID = ? AND CMPCDE = ? AND IDNO = ? ");
            
            if ((addressVect != null) && (addressVect.size() != 0)) {
                sql.append(" AND SEQ NOT IN ( ");

                for (int i = 0; i < addressVect.size(); i++) {
                    AddressDataM addressDataM = (AddressDataM) addressVect.get(i);
                    sql.append(" '" + addressDataM.getAddressSeq() + "' , ");
                }

                if (sql.toString().trim().endsWith(",")) {
                    sql.delete(sql.toString().lastIndexOf(","), sql.toString().length());
                }

                sql.append(" ) ");
            }

            String dSql = String.valueOf(sql);

            ps = conn.prepareStatement(dSql);

            ps.setString(1, appRecordID);
            ps.setString(2, cmpCode);
            ps.setString(3, idNo);
            
            ps.executeUpdate();
            ps.close();

        } catch (Exception e) {
        	logger.error("ERROR >> ",e);
			throw new OrigCustomerAddressMException(e.getMessage());
		} finally {
			try {
				closeConnection(conn, ps);
			} catch (Exception e) {
				logger.error("ERROR >> ",e);
				throw new OrigCustomerAddressMException(e.getMessage());
			}
		}
	}*/
	
	public void deleteNotInKeyTableORIG_CUSTOMER_ADDRESS(Vector addressVect, String personalID) throws OrigCustomerAddressMException{
		PreparedStatement ps = null;
		Connection conn = null;
        try {
            // conn = Get Connection
            conn = getConnection();

            StringBuffer sql = new StringBuffer("");
            sql.append("DELETE FROM ORIG_PERSONAL_ADDRESS");
            sql.append(" WHERE PERSONAL_ID = ? ");
            
            if ((addressVect != null) && (addressVect.size() != 0)) {
                sql.append(" AND ADDRESS_ID NOT IN ( ");

                for (int i = 0; i < addressVect.size(); i++) {
                    AddressDataM addressDataM = (AddressDataM) addressVect.get(i);
                    sql.append(" '" + addressDataM.getAddressID() + "' , ");
                }

                if (sql.toString().trim().endsWith(",")) {
                    sql.delete(sql.toString().lastIndexOf(","), sql.toString().length());
                }

                sql.append(" ) ");
            }

            log.debug(">>>>>>>>>>>>>>>>>>>>>>>>SQL : " + sql);
            String dSql = String.valueOf(sql);
            ps = conn.prepareStatement(dSql);
            ps.setString(1, personalID);
     
            ps.executeUpdate();
            ps.close();

        } catch (Exception e) {
        	logger.error("ERROR >> ",e);
			throw new OrigCustomerAddressMException(e.getMessage());
		} finally {
			try {
				closeConnection(conn, ps);
			} catch (Exception e) {
				logger.error("ERROR >> ",e);
				throw new OrigCustomerAddressMException(e.getMessage());
			}
		}
	}
	
	/**
	 * @param appRecordID
	 */
	/*public void deleteTableORIG_CUSTOMER_ADDRESS(String appRecordID, String cmpCode, String idNo)throws OrigCustomerAddressMException {
		PreparedStatement ps = null;
		try {
			//conn = Get Connection
			conn = getConnection();
			StringBuffer sql = new StringBuffer("");
			sql.append("DELETE ORIG_CUSTOMER_ADDRESS ");
			sql.append(" WHERE CMPCDE = ? AND APPLICATION_RECORD_ID = ? AND IDNO = ? ");
			String dSql = String.valueOf(sql);
			log.debug("Sql="+dSql);
			ps = conn.prepareStatement(dSql);
			ps.setString(1, cmpCode);
			ps.setString(2, appRecordID);
			ps.setString(3, idNo);
			
			ps.executeUpdate();

		} catch (Exception e) {
			log.fatal(e.getLocalizedMessage());
			throw new OrigCustomerAddressMException(e.getMessage());
		} finally {
			try {
				closeConnection(conn, ps);
			} catch (Exception e) {
				log.fatal(e.getLocalizedMessage());
				throw new OrigCustomerAddressMException(e.getMessage());
			}
		}
		
	}*/
	/**
	 * @param personalID
	 */
	public void deleteTableORIG_CUSTOMER_ADDRESS(String personalID)throws OrigCustomerAddressMException {
		PreparedStatement ps = null;
		Connection conn = null;
		try {
			//conn = Get Connection
			conn = getConnection();
			StringBuffer sql = new StringBuffer("");
			sql.append("DELETE ORIG_PERSONAL_ADDRESS ");
			sql.append(" WHERE PERSONAL_ID = ?");
			String dSql = String.valueOf(sql);
			log.debug("Sql="+dSql);
			ps = conn.prepareStatement(dSql);
			ps.setString(1, personalID);
			ps.executeUpdate();

		} catch (Exception e) {
			log.fatal(e.getLocalizedMessage());
			throw new OrigCustomerAddressMException(e.getMessage());
		} finally {
			try {
				closeConnection(conn, ps);
			} catch (Exception e) {
				log.fatal(e.getLocalizedMessage());
				throw new OrigCustomerAddressMException(e.getMessage());
			}
		}
		
	}

	public void createModelOrigCustomerAddressM(AddressDataM prmAddressDataM, String personalID) throws OrigCustomerAddressMException {
		// TODO Auto-generated method stub
		
	}
}
