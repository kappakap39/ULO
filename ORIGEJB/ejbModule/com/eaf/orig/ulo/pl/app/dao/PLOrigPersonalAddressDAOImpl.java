package com.eaf.orig.ulo.pl.app.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.Vector;
import org.apache.log4j.Logger;
import com.eaf.orig.shared.constant.EJBConstant;
import com.eaf.orig.shared.constant.OrigConstant;
import com.eaf.orig.shared.dao.OrigObjectDAO;
import com.eaf.orig.shared.util.OrigUtil;
import com.eaf.orig.ulo.pl.app.dao.exception.PLOrigApplicationException;
import com.eaf.orig.ulo.pl.model.app.PLAddressDataM;
import com.eaf.orig.ulo.pl.model.app.PLApplicationDataM;
import com.eaf.orig.ulo.pl.model.app.PLPersonalInfoDataM;
import com.eaf.orig.ulo.pl.service.PLORIGEJBService;

public class PLOrigPersonalAddressDAOImpl extends OrigObjectDAO implements
		PLOrigPersonalAddressDAO {
	
	private static Logger log = Logger.getLogger(PLOrigPersonalAddressDAOImpl.class);

	@Override
	public void updateSavePersonalAddress(Vector<PLAddressDataM> addrVect, String personalID)throws PLOrigApplicationException {
		try{
			if(!OrigUtil.isEmptyVector(addrVect)){				
				Vector<String> addressIDVect = new Vector<String>();
				for(PLAddressDataM addrM :addrVect){
					addrM.setPersonalID(personalID);
					
					if(OrigUtil.isEmptyString(addrM.getAddressID())){
						String addrId = PLORIGEJBService.getGeneratorManager().generateUniqueIDByName(EJBConstant.ADDRESS_ID);							
						addrM.setAddressID(addrId);
					}
					
					int returnRows = updateTableOrig_Personal_Address(addrM, addrM.getAddressID());					
					if(returnRows == 0){
						insertTableOrig_Personal_Address(addrM, addrM.getAddressID());
					}
					
					addressIDVect.add(addrM.getAddressID());
					
				}
				if(!OrigUtil.isEmptyVector(addressIDVect)){
					deleteTableOrig_Personal_Address(addressIDVect, personalID);	
				}
			}
			
		}catch(Exception e){
			log.fatal(e.getMessage());
			e.printStackTrace();
			throw new PLOrigApplicationException(e.getMessage());
		}

	}
	
	private void insertTableOrig_Personal_Address(PLAddressDataM addrM, String addrId) throws PLOrigApplicationException {
		
		PreparedStatement ps = null;
		Connection conn = null;
		try {
			
//			log.debug("addrId = "+addrId);
//			log.debug("addrM.getPersonalID() = "+addrM.getPersonalID());
			conn = getConnection();
			StringBuilder sql = new StringBuilder("");
			sql.append("INSERT INTO ORIG_PERSONAL_ADDRESS ");
			sql.append("( ADDRESS_ID, PERSONAL_ID, ADDRESS_TYPE, SEQ, ADDRESS ");
			sql.append(", VILAPT, FLOOR, ROOM, MOO, SOI ");
			sql.append(", ROAD, TAMBOL, AMPHUR, PROVINCE, ZIPCODE ");
			sql.append(", PHONE1, EXT1, PHONE2, EXT2, MOBILE ");
			sql.append(", FAX, FAX_EXT, EMAIL, RESIDEY, ADRSTS ");
			sql.append(", CONTACT, HOUSEIDNO, REMARK, CREATE_DATE, CREATE_BY ");
			sql.append(", UPDATE_DATE, UPDATE_BY, ADDRESS_TYPE_COPY, RESIDEM, BUILDING_TYPE ");
			sql.append(", RENTS, PLACE_NAME, BUILDING, HOUSING_ESTATE, COUNTRY ,COMPANY_TITLE,COMPANY_NAME )");
			sql.append(" VALUES(?,?,?,?,?   ,?,?,?,?,?   ,?,?,?,?,?   ,?,?,?,?,? ");
			sql.append(",?,?,?,?,?   ,?,?,?,SYSDATE,?   ,SYSDATE,?,?,?,?  ,?,?,?,?,?,?,? ) ");
			String dSql = String.valueOf(sql);
			log.debug("Sql=" + dSql);
			ps = conn.prepareStatement(dSql);
			
			int index = 1;
			
			ps.setString(index++, addrId);
			ps.setString(index++, addrM.getPersonalID());
			ps.setString(index++, addrM.getAddressType());
			ps.setInt(index++, addrM.getAddressSeq());
			ps.setString(index++, addrM.getAddressNo());
			
			ps.setString(index++, addrM.getVilapt());
			ps.setString(index++, addrM.getFloor());
			ps.setString(index++, addrM.getRoom());
			ps.setString(index++, addrM.getMoo());
			ps.setString(index++, addrM.getSoi());
			
			ps.setString(index++, addrM.getRoad());
			ps.setString(index++, addrM.getTambol());
			ps.setString(index++, addrM.getAmphur());
			ps.setString(index++, addrM.getProvince());
			ps.setString(index++, addrM.getZipcode());
			
			ps.setString(index++, addrM.getPhoneNo1());
			ps.setString(index++, addrM.getPhoneExt1());
			ps.setString(index++, addrM.getPhoneNo2());
			ps.setString(index++, addrM.getPhoneExt2());
			ps.setString(index++, addrM.getMobileNo());
			
			ps.setString(index++, addrM.getFaxNo());
			ps.setString(index++, addrM.getFaxExt());
			ps.setString(index++, addrM.getEmail());
			ps.setInt(index++, addrM.getResidey());
			ps.setString(index++, addrM.getAdrsts());
			
			ps.setString(index++, addrM.getContactPerson());
			ps.setString(index++, addrM.getHouseID());
			ps.setString(index++, addrM.getRemark());
			ps.setString(index++, addrM.getCreateBy());
			
			ps.setString(index++, addrM.getUpdateBy());
			ps.setString(index++, addrM.getAddressTypeCopy());
			ps.setInt(index++, addrM.getResidem());
			ps.setString(index++, addrM.getBuildingType());
			
			ps.setBigDecimal(index++, addrM.getRents());
			ps.setString(index++, addrM.getPlaceName());
			ps.setString(index++, addrM.getBuilding());
			ps.setString(index++, addrM.getHousingEstate());
			ps.setString(index++, addrM.getCountry());
			
			ps.setString(index++, addrM.getCompanyTitle());
			ps.setString(index++, addrM.getCompanyName());
			
			ps.executeUpdate();
			
		}catch (Exception e) {
			log.fatal(e.getLocalizedMessage());
			throw new PLOrigApplicationException(e.getMessage());
		} finally {
			try {
				closeConnection(conn, ps);
			} catch (Exception e) {
				log.fatal(e.getLocalizedMessage());
				throw new PLOrigApplicationException(e.getMessage());
			}
		}
		
	}
	
	private void deleteTableOrig_Personal_Address(Vector<String> addressId, String personalId)throws PLOrigApplicationException {
		Connection conn = null;
		PreparedStatement ps = null;
		try {
			conn = getConnection();
			StringBuilder sql = new StringBuilder("");
			sql.append("DELETE ORIG_PERSONAL_ADDRESS ");
			sql.append(" WHERE PERSONAL_ID = ? AND ADDRESS_ID NOT IN ");
			sql.append("( ");
			sql.append(ParameterINVect(addressId));
			sql.append(" )");
			
			String dSql = String.valueOf(sql);
			log.debug("Sql="+dSql);
			ps = conn.prepareStatement(dSql);
			int index = 1;
			ps.setString(index++, personalId);
			PreparedStatementParameter(index, ps, addressId);
			ps.executeUpdate();

		} catch (Exception e) {
			log.fatal(e.getMessage());
			throw new PLOrigApplicationException(e.getMessage());
		} finally {
			try {
				closeConnection(conn, ps);
			} catch (Exception e) {
				log.fatal(e.getMessage());
				throw new PLOrigApplicationException(e.getMessage());
			}
		}
		
	}

	private int updateTableOrig_Personal_Address(PLAddressDataM addrM, String addrId)
			throws PLOrigApplicationException {
		int returnRows = 0;
		PreparedStatement ps = null;
		Connection conn = null;
		try {
			conn = getConnection();
			StringBuilder sql = new StringBuilder("");
//log.debug("addrId = "+addrId);
//log.debug("addrM.getPersonalID() = "+addrM.getPersonalID());
			sql.append("UPDATE ORIG_PERSONAL_ADDRESS ");
			sql.append(" SET ADDRESS_TYPE=?, SEQ=?, ADDRESS=?, VILAPT=?, FLOOR=? ");
			sql.append(", ROOM=?, MOO=?, SOI=?, ROAD=?, TAMBOL=? ");
			sql.append(", AMPHUR=?, PROVINCE=?, ZIPCODE=?, PHONE1=?, EXT1=? ");
			sql.append(", PHONE2=?, EXT2=?, MOBILE=?, FAX=?, FAX_EXT=? ");
			sql.append(", EMAIL=?, RESIDEY=?, ADRSTS=?, CONTACT=?, HOUSEIDNO=? ");
			sql.append(", REMARK=?, UPDATE_DATE=SYSDATE, UPDATE_BY=?, ADDRESS_TYPE_COPY=?, RESIDEM=? ");
			sql.append(", BUILDING_TYPE=?, RENTS=?, PLACE_NAME=?, BUILDING=?, HOUSING_ESTATE=?");
			sql.append(", COUNTRY=? ,COMPANY_TITLE=? , COMPANY_NAME = ? ");
			sql.append(" WHERE ADDRESS_ID=? ");
			String dSql = String.valueOf(sql);
			log.debug("Sql=" + dSql);
			ps = conn.prepareStatement(dSql);
			
			int index = 1;
			
			ps.setString(index++, addrM.getAddressType());
			ps.setInt(index++, addrM.getAddressSeq());
			ps.setString(index++, addrM.getAddressNo());
			ps.setString(index++, addrM.getVilapt());
			ps.setString(index++, addrM.getFloor());
			
			ps.setString(index++, addrM.getRoom());
			ps.setString(index++, addrM.getMoo());
			ps.setString(index++, addrM.getSoi());
			ps.setString(index++, addrM.getRoad());
			ps.setString(index++, addrM.getTambol());
			
			ps.setString(index++, addrM.getAmphur());
			ps.setString(index++, addrM.getProvince());
			ps.setString(index++, addrM.getZipcode());
			ps.setString(index++, addrM.getPhoneNo1());
			ps.setString(index++, addrM.getPhoneExt1());
			
			ps.setString(index++, addrM.getPhoneNo2());
			ps.setString(index++, addrM.getPhoneExt2());
			ps.setString(index++, addrM.getMobileNo());
			ps.setString(index++, addrM.getFaxNo());
			ps.setString(index++, addrM.getFaxExt());
			
			ps.setString(index++, addrM.getEmail());
			ps.setInt(index++, addrM.getResidey());
			ps.setString(index++, addrM.getAdrsts());
			ps.setString(index++, addrM.getContactPerson());
			ps.setString(index++, addrM.getHouseID());
			
			ps.setString(index++, addrM.getRemark());
			ps.setString(index++, addrM.getUpdateBy());
			ps.setString(index++, addrM.getAddressTypeCopy());
			ps.setInt(index++, addrM.getResidem());
			ps.setString(index++, addrM.getBuildingType());
			
			ps.setBigDecimal(index++, addrM.getRents());
			ps.setString(index++, addrM.getPlaceName());
			ps.setString(index++, addrM.getBuilding());
			ps.setString(index++, addrM.getHousingEstate());
			
			ps.setString(index++, addrM.getCountry());
			
			ps.setString(index++, addrM.getCompanyTitle());
			ps.setString(index++, addrM.getCompanyName());
			
			ps.setString(index++, addrId);

			returnRows = ps.executeUpdate();
			
		} catch (Exception e) {
			log.fatal(e.getMessage());
			throw new PLOrigApplicationException(e.getMessage());
		} finally {
			try {
				closeConnection(conn, ps);
			} catch (Exception e) {
				log.fatal(e.getMessage());
				throw new PLOrigApplicationException(e.getMessage());
			}
		}
		return returnRows;
	}

	@Override
	public Vector<PLAddressDataM> loadPersonalAddress(String personalId) throws PLOrigApplicationException{		
		PreparedStatement ps = null;
		ResultSet rs = null;		
		Connection conn = null;
		Vector<PLAddressDataM> addressVect = new Vector<PLAddressDataM>();		
		try{
			
			conn = getConnection();
			StringBuilder sql = new StringBuilder("");
			sql.append(" SELECT ADDRESS_ID, ADDRESS_TYPE, SEQ, ADDRESS, VILAPT ");
			sql.append(", FLOOR, ROOM, MOO, SOI, ROAD ");
			sql.append(", TAMBOL, AMPHUR, PROVINCE, ZIPCODE, PHONE1 ");
			sql.append(", EXT1, PHONE2, EXT2, MOBILE, FAX ");
			sql.append(", FAX_EXT, EMAIL, RESIDEY, ADRSTS, CONTACT ");
			sql.append(", HOUSEIDNO, REMARK, CREATE_DATE, CREATE_BY, UPDATE_DATE ");
			sql.append(", UPDATE_BY, ADDRESS_TYPE_COPY, RESIDEM, BUILDING_TYPE, RENTS ");
			sql.append(", PLACE_NAME, BUILDING, HOUSING_ESTATE, COUNTRY , COMPANY_TITLE ,COMPANY_NAME ");
			sql.append(" FROM ORIG_PERSONAL_ADDRESS WHERE PERSONAL_ID = ? ");
			String dSql = String.valueOf(sql);
			
			ps = conn.prepareStatement(dSql);
			
			ps.setString(1, personalId);

			rs = ps.executeQuery();
			
			PLAddressDataM addrM = null;
			
			while(rs.next()){				
				int index = 1;
				addrM = new PLAddressDataM();
				
				addrM.setAddressID(rs.getString(index++));
				addrM.setAddressType(rs.getString(index++));
				addrM.setSeqTmp(rs.getString(index++));
				addrM.setAddressNo(rs.getString(index++));
				addrM.setVilapt(rs.getString(index++));
				
				addrM.setFloor(rs.getString(index++));
				addrM.setRoom(rs.getString(index++));
				addrM.setMoo(rs.getString(index++));
				addrM.setSoi(rs.getString(index++));
				addrM.setRoad(rs.getString(index++));
				
				addrM.setTambol(rs.getString(index++));
				addrM.setAmphur(rs.getString(index++));
				addrM.setProvince(rs.getString(index++));
				addrM.setZipcode(rs.getString(index++));
				addrM.setPhoneNo1(rs.getString(index++));
				
				addrM.setPhoneExt1(rs.getString(index++));
				addrM.setPhoneNo2(rs.getString(index++));
				addrM.setPhoneExt2(rs.getString(index++));
				addrM.setMobileNo(rs.getString(index++));
				addrM.setFaxNo(rs.getString(index++));
				
				addrM.setFaxExt(rs.getString(index++));
				addrM.setEmail(rs.getString(index++));
				addrM.setResidey(rs.getInt(index++));
				addrM.setAdrsts(rs.getString(index++));
				addrM.setContactPerson(rs.getString(index++));
				
				addrM.setHouseID(rs.getString(index++));
				addrM.setRemark(rs.getString(index++));
				addrM.setCreateDate(rs.getTimestamp(index++));
				addrM.setCreateBy(rs.getString(index++));
				addrM.setUpdateDate(rs.getTimestamp(index++));
				
				addrM.setUpdateBy(rs.getString(index++));
				addrM.setAddressTypeCopy(rs.getString(index++));
				addrM.setResidem(rs.getInt(index++));
				addrM.setBuildingType(rs.getString(index++));
				addrM.setRents(rs.getBigDecimal(index++));
				
				addrM.setPlaceName(rs.getString(index++));
				addrM.setBuilding(rs.getString(index++));
				addrM.setHousingEstate(rs.getString(index++));
				addrM.setCountry(rs.getString(index++));
				addrM.setCompanyTitle(rs.getString(index++));
				addrM.setCompanyName(rs.getString(index++));
				
				addressVect.add(addrM);
			}			
		}catch(Exception e){
			log.fatal("Error ",e);
			throw new PLOrigApplicationException(e.getMessage());
		}finally{
			try{
				closeConnection(conn, rs, ps);
			}catch(Exception e){
				log.fatal(e.getMessage());				
				throw new PLOrigApplicationException(e.getMessage());
			}
		}
		return addressVect;
	}
	
	public Vector<PLAddressDataM> loadPersonalAddress_IncreaseDecrease(String personalId, PLApplicationDataM currentAppM) throws PLOrigApplicationException {
		
		PreparedStatement ps = null;
		ResultSet rs = null;
		
		Vector<PLAddressDataM> plAddressVect = new Vector<PLAddressDataM>();
		Connection conn = null;
		try {
			
			conn = getConnection();
			StringBuilder sql = new StringBuilder("");
			sql.append(" SELECT ADDRESS_TYPE, SEQ, ADDRESS, VILAPT ");
			sql.append(", FLOOR, ROOM, MOO, SOI, ROAD ");
			sql.append(", TAMBOL, AMPHUR, PROVINCE, ZIPCODE, PHONE1 ");
			sql.append(", EXT1, PHONE2, EXT2, MOBILE, FAX ");
			sql.append(", FAX_EXT, EMAIL, RESIDEY, ADRSTS, CONTACT ");
			sql.append(", HOUSEIDNO, REMARK, CREATE_DATE, CREATE_BY, UPDATE_DATE ");
			sql.append(", UPDATE_BY, ADDRESS_TYPE_COPY, RESIDEM, BUILDING_TYPE, RENTS ");
			sql.append(", PLACE_NAME, BUILDING, HOUSING_ESTATE, COUNTRY , COMPANY_TITLE ,COMPANY_NAME ");
			sql.append(" FROM ORIG_PERSONAL_ADDRESS WHERE PERSONAL_ID = ? ");
			String dSql = String.valueOf(sql);
			
//			log.debug("[loadPersonalAddress] sql" + dSql);
//			log.debug("[loadPersonalAddress] PersonalId "+personalId);
			
			ps = conn.prepareStatement(dSql);
			
			ps.setString(1, personalId);

			rs = ps.executeQuery();
			
			PLAddressDataM addrM = null;
			
			PLPersonalInfoDataM currentPersonM = currentAppM.getPLPersonalInfoDataM(OrigConstant.PERSONAL_TYPE_APPLICANT);
			if(currentPersonM == null) currentPersonM = new PLPersonalInfoDataM();
			
			while (rs.next()) {				
				
				int index = 1;
				addrM = new PLAddressDataM();
				
				addrM.setAddressType(rs.getString(index++));
				addrM.setSeqTmp(rs.getString(index++));
				addrM.setAddressNo(rs.getString(index++));
				addrM.setVilapt(rs.getString(index++));
				
				addrM.setFloor(rs.getString(index++));
				addrM.setRoom(rs.getString(index++));
				addrM.setMoo(rs.getString(index++));
				addrM.setSoi(rs.getString(index++));
				addrM.setRoad(rs.getString(index++));
				
				addrM.setTambol(rs.getString(index++));
				addrM.setAmphur(rs.getString(index++));
				addrM.setProvince(rs.getString(index++));
				addrM.setZipcode(rs.getString(index++));
				addrM.setPhoneNo1(rs.getString(index++));
				
				addrM.setPhoneExt1(rs.getString(index++));
				addrM.setPhoneNo2(rs.getString(index++));
				addrM.setPhoneExt2(rs.getString(index++));
				addrM.setMobileNo(rs.getString(index++));
				addrM.setFaxNo(rs.getString(index++));
				
				addrM.setFaxExt(rs.getString(index++));
				addrM.setEmail(rs.getString(index++));
				addrM.setResidey(rs.getInt(index++));
				addrM.setAdrsts(rs.getString(index++));
				addrM.setContactPerson(rs.getString(index++));
				
				addrM.setHouseID(rs.getString(index++));
				addrM.setRemark(rs.getString(index++));
				addrM.setCreateDate(rs.getTimestamp(index++));
				addrM.setCreateBy(rs.getString(index++));
				addrM.setUpdateDate(rs.getTimestamp(index++));
				
				addrM.setUpdateBy(rs.getString(index++));
				addrM.setAddressTypeCopy(rs.getString(index++));
				addrM.setResidem(rs.getInt(index++));
				addrM.setBuildingType(rs.getString(index++));
				addrM.setRents(rs.getBigDecimal(index++));
				
				addrM.setPlaceName(rs.getString(index++));
				addrM.setBuilding(rs.getString(index++));
				addrM.setHousingEstate(rs.getString(index++));
				addrM.setCountry(rs.getString(index++));
				
				addrM.setCompanyTitle(rs.getString(index++));
				addrM.setCompanyName(rs.getString(index++));
				
				addrM.setPersonalID(currentPersonM.getPersonalID());
				
				plAddressVect.add(addrM);
			}			
		} catch (Exception e) {
			log.fatal("Error ",e);
			throw new PLOrigApplicationException(e.getMessage());
		} finally {
			try {
				closeConnection(conn, rs, ps);
			} catch (Exception e) {
				log.fatal(e.getMessage());				
				throw new PLOrigApplicationException(e.getMessage());
			}
		}
		return plAddressVect;
	}

	@Override
	public void deletePersonalAddress(String personalId) throws PLOrigApplicationException {
		Connection conn = null;
		PreparedStatement ps = null;
		try {
			conn = getConnection();
			StringBuilder sql = new StringBuilder("");
			sql.append(" DELETE ORIG_PERSONAL_ADDRESS WHERE PERSONAL_ID = ? ");			
			String dSql = String.valueOf(sql);
			log.debug("Sql="+dSql);
			ps = conn.prepareStatement(dSql);
			ps.setString(1, personalId);
			ps.executeUpdate();

		} catch (Exception e) {
			log.fatal(e.getMessage());
			throw new PLOrigApplicationException(e.getMessage());
		} finally {
			try {
				closeConnection(conn, ps);
			} catch (Exception e) {
				log.fatal(e.getMessage());
				throw new PLOrigApplicationException(e.getMessage());
			}
		}
		
	}
	
}
