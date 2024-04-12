package com.eaf.orig.ulo.app.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;

import com.eaf.core.ulo.common.date.ApplicationDate;
import com.eaf.core.ulo.common.util.Util;
import com.eaf.orig.shared.constant.OrigConstant;
import com.eaf.orig.shared.dao.OrigObjectDAO;
import com.eaf.orig.ulo.app.dao.exception.ApplicationException;
import com.eaf.orig.ulo.app.util.GenerateUnique;
import com.eaf.orig.ulo.model.app.AddressDataM;

public class OrigPersonalAddressDAOImpl extends OrigObjectDAO implements
		OrigPersonalAddressDAO {
	public OrigPersonalAddressDAOImpl(String userId){
		this.userId = userId;
	}
	public OrigPersonalAddressDAOImpl(){
		
	}
	private String userId = "";
	@Override
	public void createOrigAddressM(AddressDataM addressM)
			throws ApplicationException {
		try {
			//Get Primary Key
		    logger.debug("addressM.getAddressId() :"+addressM.getAddressId());
		    if(Util.empty(addressM.getAddressId())){
				String addressId =GenerateUnique.generate(OrigConstant.SeqNames.ORIG_PERSONAL_ADDRESS_PK );
				addressM.setAddressId(addressId);				
		    }
		    if(Util.empty(addressM.getCreateBy())){
		    	addressM.setCreateBy(userId);
		    }
		    addressM.setUpdateBy(userId);
			createTableORIG_PERSONAL_ADDRESS(addressM);
		} catch (Exception e) {
			logger.fatal(e.getLocalizedMessage());
			throw new ApplicationException(e.getMessage());
		}
	}

	private void createTableORIG_PERSONAL_ADDRESS(AddressDataM addressM) throws ApplicationException {
		PreparedStatement ps = null;
		Connection conn = null;
		try {
			//conn = Get Connection
			conn = getConnection();
			StringBuffer sql = new StringBuffer("");
			sql.append("INSERT INTO ORIG_PERSONAL_ADDRESS ");
			sql.append("( ADDRESS_ID, PERSONAL_ID, ADDRESS_TYPE, SEQ, ADDRESS, ");
			sql.append(" FLOOR, ROOM, MOO, SOI, ROAD, TAMBOL, AMPHUR, PROVINCE, ");
			sql.append(" ZIPCODE, PHONE1, EXT1, PHONE2, EXT2, MOBILE, FAX, ADRSTS, ");
			sql.append(" RESIDEY, RESIDEM, RENTS, BUILDING, COMPANY_NAME, ");
			sql.append(" COMPANY_TITLE, STATE, COUNTRY, DEPARTMENT, ADDRESS1, PROVINCE_DESC, ");
			sql.append(" VAT_REGISTRATION, BRANCH_TYPE, BRANCH_NAME, VAT_CODE, BUILDING_TYPE, ");
			sql.append(" ADDRESS2, ADDRESS3, ADDRESS_FORMAT, ");
			sql.append(" CREATE_DATE, CREATE_BY, UPDATE_DATE, UPDATE_BY, ");
			sql.append(" PHONE_ID_REF, ADDRESS_ID_REF, EDIT_FLAG, VILAPT, RESIDENCE_PROVINCE )");
			

			sql.append(" VALUES(?,?,?,?,?,  ?,?,?,?,?,?,?,?,  ?,?,?,?,?,?,?,?, ");
			sql.append(" ?,?,?,?,?,  ?,?,?,?,?,?,  ?,?,?,?,?,  ?,?,?, ");
			sql.append(" ?,?,?,?, ?,?,?, ?, ? )");
			String dSql = String.valueOf(sql);
			logger.debug("Sql=" + dSql);
			ps = conn.prepareStatement(dSql);
			int cnt = 1;
			ps.setString(cnt++, addressM.getAddressId());
			ps.setString(cnt++, addressM.getPersonalId());
			ps.setString(cnt++, addressM.getAddressType());
			ps.setInt(cnt++, addressM.getSeq());
			ps.setString(cnt++, addressM.getAddress());
			
			ps.setString(cnt++, addressM.getFloor());
			ps.setString(cnt++, addressM.getRoom());
			ps.setString(cnt++, addressM.getMoo());
			ps.setString(cnt++, addressM.getSoi());
			ps.setString(cnt++, addressM.getRoad());
			ps.setString(cnt++, addressM.getTambol());
			ps.setString(cnt++, addressM.getAmphur());
			ps.setBigDecimal(cnt++, addressM.getProvince());
			
			ps.setString(cnt++, addressM.getZipcode());
			ps.setString(cnt++, addressM.getPhone1());
			ps.setString(cnt++, addressM.getExt1());
			ps.setString(cnt++, addressM.getPhone2());
			ps.setString(cnt++, addressM.getExt2());
			ps.setString(cnt++, addressM.getMobile());
			ps.setString(cnt++, addressM.getFax());
			ps.setString(cnt++, addressM.getAdrsts());
			
			ps.setBigDecimal(cnt++, addressM.getResidey());
			ps.setBigDecimal(cnt++, addressM.getResidem());
			ps.setBigDecimal(cnt++, addressM.getRents());
			ps.setString(cnt++, addressM.getBuilding());
			ps.setString(cnt++, addressM.getCompanyName());
			
			ps.setString(cnt++, addressM.getCompanyTitle());
			ps.setString(cnt++, addressM.getState());
			ps.setString(cnt++, addressM.getCountry());
			ps.setString(cnt++, addressM.getDepartment());
			ps.setString(cnt++, addressM.getAddress1());
			ps.setString(cnt++, addressM.getProvinceDesc());
			
			ps.setString(cnt++, addressM.getVatRegistration());
			ps.setString(cnt++, addressM.getBranchType());
			ps.setString(cnt++, addressM.getBranchName());
			ps.setString(cnt++, addressM.getVatCode());
			ps.setString(cnt++, addressM.getBuildingType());
			
			ps.setString(cnt++, addressM.getAddress2());
			ps.setString(cnt++, addressM.getAddress3());
			ps.setString(cnt++, addressM.getAddressFormat());
			
			ps.setTimestamp(cnt++, ApplicationDate.getTimestamp());
			ps.setString(cnt++, addressM.getCreateBy());
			ps.setTimestamp(cnt++, ApplicationDate.getTimestamp());
			ps.setString(cnt++, addressM.getUpdateBy());

			ps.setString(cnt++, addressM.getPhoneIdRef());
			ps.setString(cnt++, addressM.getAddressIdRef());
			ps.setString(cnt++, addressM.getEditFlag());
			
			ps.setString(cnt++, addressM.getVilapt());
			ps.setString(cnt++, addressM.getResidenceProvince());
			
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
	public void deleteOrigAddressM(String personalID, String addressId)
			throws ApplicationException {
		deleteTableORIG_PERSONAL_ADDRESS(personalID, addressId);
	}

	private void deleteTableORIG_PERSONAL_ADDRESS(String personalID, String addressId) throws ApplicationException {
		PreparedStatement ps = null;
		Connection conn = null;
		try {
			//conn = Get Connection
			conn = getConnection();
			StringBuffer sql = new StringBuffer("");
			sql.append(" DELETE ORIG_PERSONAL_ADDRESS ");
			sql.append(" WHERE PERSONAL_ID = ? ");
			if(addressId != null && !addressId.isEmpty()) {
				sql.append(" AND ADDRESS_ID = ? ");
			}
			String dSql = String.valueOf(sql);
			logger.debug("Sql=" + dSql);
			ps = conn.prepareStatement(dSql);
			ps.setString(1, personalID);
			if(addressId != null && !addressId.isEmpty()) {
				ps.setString(2, addressId);
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
	public ArrayList<AddressDataM> loadOrigAddressM(String personalID,
			Connection conn) throws ApplicationException {
		return selectTableORIG_PERSONAL_ADDRESS(personalID, conn);
	}
	
	@Override
	public ArrayList<AddressDataM> loadOrigAddressM(String personalID)
			throws ApplicationException {
		Connection conn = null;
		try{
			conn = getConnection();
			return selectTableORIG_PERSONAL_ADDRESS(personalID,conn);
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

	private ArrayList<AddressDataM> selectTableORIG_PERSONAL_ADDRESS(
			String personalID,Connection conn) throws ApplicationException {
		PreparedStatement ps = null;
		ResultSet rs = null;
		ArrayList<AddressDataM> addressList = new ArrayList<AddressDataM>();
		try {
			StringBuffer sql = new StringBuffer("");
			sql.append("SELECT ADDRESS_ID, PERSONAL_ID, ADDRESS_TYPE, SEQ, ADDRESS, ");
			sql.append(" FLOOR, ROOM, MOO, SOI, ROAD, TAMBOL, AMPHUR, PROVINCE, ");
			sql.append(" ZIPCODE, PHONE1, EXT1, PHONE2, EXT2, MOBILE, FAX, ADRSTS, ");
			sql.append(" RESIDEY, RESIDEM, RENTS, BUILDING, COMPANY_NAME, ");
			sql.append(" COMPANY_TITLE, STATE, COUNTRY, DEPARTMENT, ADDRESS1, PROVINCE_DESC,");
			sql.append(" VAT_REGISTRATION, BRANCH_TYPE, BRANCH_NAME, VAT_CODE,BUILDING_TYPE , ");
			sql.append(" ADDRESS2, ADDRESS3, CREATE_DATE, CREATE_BY, UPDATE_DATE, UPDATE_BY, ");
			sql.append(" VILAPT, ADDRESS_FORMAT,PHONE_ID_REF, ADDRESS_ID_REF, ");
			sql.append(" EDIT_FLAG, RESIDENCE_PROVINCE ");
			sql.append(" FROM ORIG_PERSONAL_ADDRESS WHERE PERSONAL_ID = ? ");
			String dSql = String.valueOf(sql);
			logger.debug("Sql=" + dSql);
			ps = conn.prepareStatement(dSql);
			rs = null;
			ps.setString(1, personalID);

			rs = ps.executeQuery();

			while(rs.next()) {
				AddressDataM addressM = new AddressDataM();
				addressM.setAddressId(rs.getString("ADDRESS_ID"));
				addressM.setPersonalId(rs.getString("PERSONAL_ID"));
				addressM.setAddressType(rs.getString("ADDRESS_TYPE"));
				addressM.setSeq(rs.getInt("SEQ"));
				addressM.setAddress(rs.getString("ADDRESS"));
				
				addressM.setFloor(rs.getString("FLOOR"));
				addressM.setRoom(rs.getString("ROOM"));
				addressM.setMoo(rs.getString("MOO"));
				addressM.setSoi(rs.getString("SOI"));
				addressM.setRoad(rs.getString("ROAD"));
				addressM.setTambol(rs.getString("TAMBOL"));
				addressM.setAmphur(rs.getString("AMPHUR"));
				addressM.setProvince(rs.getBigDecimal("PROVINCE"));
				
				addressM.setZipcode(rs.getString("ZIPCODE"));
				addressM.setPhone1(rs.getString("PHONE1"));
				addressM.setExt1(rs.getString("EXT1"));
				addressM.setPhone2(rs.getString("PHONE2"));
				addressM.setExt2(rs.getString("EXT2"));
				addressM.setMobile(rs.getString("MOBILE"));
				addressM.setFax(rs.getString("FAX"));
				addressM.setAdrsts(rs.getString("ADRSTS"));
				
				addressM.setResidey(rs.getBigDecimal("RESIDEY"));
				addressM.setResidem(rs.getBigDecimal("RESIDEM"));
				addressM.setRents(rs.getBigDecimal("RENTS"));
				addressM.setBuilding(rs.getString("BUILDING"));
				addressM.setCompanyName(rs.getString("COMPANY_NAME"));
				
				addressM.setCompanyTitle(rs.getString("COMPANY_TITLE"));
				addressM.setState(rs.getString("STATE"));
				addressM.setCountry(rs.getString("COUNTRY"));
				addressM.setDepartment(rs.getString("DEPARTMENT"));
				addressM.setAddress1(rs.getString("ADDRESS1"));
				addressM.setProvinceDesc(rs.getString("PROVINCE_DESC"));
				
				addressM.setVatRegistration(rs.getString("VAT_REGISTRATION"));
				addressM.setBranchType(rs.getString("BRANCH_TYPE"));
				addressM.setBranchName(rs.getString("BRANCH_NAME"));
				addressM.setVatCode(rs.getString("VAT_CODE"));
				addressM.setBuildingType(rs.getString("BUILDING_TYPE"));

				addressM.setAddress2(rs.getString("ADDRESS2"));
				addressM.setAddress3(rs.getString("ADDRESS3"));
				
				addressM.setCreateBy(rs.getString("CREATE_BY"));
				addressM.setCreateDate(rs.getTimestamp("CREATE_DATE"));
				addressM.setUpdateBy(rs.getString("UPDATE_BY"));
				addressM.setUpdateDate(rs.getTimestamp("UPDATE_DATE"));
				
				addressM.setVilapt(rs.getString("VILAPT"));
				addressM.setAddressFormat(rs.getString("ADDRESS_FORMAT"));
				addressM.setPhoneIdRef(rs.getString("PHONE_ID_REF"));
				addressM.setAddressIdRef(rs.getString("ADDRESS_ID_REF"));
				addressM.setEditFlag(rs.getString("EDIT_FLAG"));
				addressM.setResidenceProvince(rs.getString("RESIDENCE_PROVINCE"));
				
				addressList.add(addressM);
			}

			return addressList;
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
	public void saveUpdateOrigAddressM(AddressDataM addressM)
			throws ApplicationException {
		int returnRows = 0;
		addressM.setUpdateBy(userId);
		returnRows = updateTableORIG_PERSONAL_ADDRESS(addressM);
		if (returnRows == 0) {
			logger.debug("New record then can't update record in table ORIG_PERSONAL_ADDRESS then call Insert method");
			addressM.setCreateBy(userId);
			createOrigAddressM(addressM);
		}
	}

	private int updateTableORIG_PERSONAL_ADDRESS(AddressDataM addressM) throws ApplicationException {
		int returnRows = 0;
		PreparedStatement ps = null;
		Connection conn = null;
		try {
			//conn = Get Connection
			conn = getConnection();
			StringBuffer sql = new StringBuffer("");
			sql.append("UPDATE ORIG_PERSONAL_ADDRESS ");
			sql.append(" SET ADDRESS_TYPE = ?, SEQ = ?, ADDRESS = ?, FLOOR = ?, ROOM = ?, ");
			sql.append(" MOO = ?, SOI = ?, ROAD = ?, TAMBOL = ?, AMPHUR = ?, PROVINCE = ?, ");
			sql.append(" ZIPCODE = ?, PHONE1 = ?, EXT1 = ?, PHONE2 = ?, EXT2 = ?, MOBILE = ?, ");
			sql.append(" FAX = ?, ADRSTS = ?, RESIDEY = ?, RESIDEM = ?, RENTS = ?, BUILDING = ?, ");
			sql.append(" COMPANY_NAME = ?, COMPANY_TITLE = ?, STATE = ?, COUNTRY = ?, DEPARTMENT = ?,");
			sql.append(" ADDRESS1 = ?, PROVINCE_DESC = ?, UPDATE_BY = ?,UPDATE_DATE = ?, ");			
			sql.append(" VAT_REGISTRATION = ?, BRANCH_TYPE = ?, BRANCH_NAME = ?, VAT_CODE= ? ,");
			sql.append(" BUILDING_TYPE = ?, ADDRESS2 = ?, ADDRESS3 = ?, ");
			sql.append(" VILAPT = ?, ADDRESS_FORMAT = ?, ADDRESS_ID_REF=?, PHONE_ID_REF=?, ");
			sql.append(" EDIT_FLAG = ?, RESIDENCE_PROVINCE = ? ");
			sql.append(" WHERE ADDRESS_ID = ? AND PERSONAL_ID = ?");
			String dSql = String.valueOf(sql);
			logger.debug("Sql=" + dSql);
			ps = conn.prepareStatement(dSql);
			int cnt = 1;
			ps.setString(cnt++, addressM.getAddressType());
			ps.setInt(cnt++, addressM.getSeq());
			ps.setString(cnt++, addressM.getAddress());			
			ps.setString(cnt++, addressM.getFloor());
			ps.setString(cnt++, addressM.getRoom());
			
			ps.setString(cnt++, addressM.getMoo());
			ps.setString(cnt++, addressM.getSoi());
			ps.setString(cnt++, addressM.getRoad());
			ps.setString(cnt++, addressM.getTambol());
			ps.setString(cnt++, addressM.getAmphur());
			ps.setBigDecimal(cnt++, addressM.getProvince());
			
			ps.setString(cnt++, addressM.getZipcode());
			ps.setString(cnt++, addressM.getPhone1());
			ps.setString(cnt++, addressM.getExt1());
			ps.setString(cnt++, addressM.getPhone2());
			ps.setString(cnt++, addressM.getExt2());
			ps.setString(cnt++, addressM.getMobile());
			
			ps.setString(cnt++, addressM.getFax());
			ps.setString(cnt++, addressM.getAdrsts());
			ps.setBigDecimal(cnt++, addressM.getResidey());
			ps.setBigDecimal(cnt++, addressM.getResidem());
			ps.setBigDecimal(cnt++, addressM.getRents());
			ps.setString(cnt++, addressM.getBuilding());
			
			ps.setString(cnt++, addressM.getCompanyName());			
			ps.setString(cnt++, addressM.getCompanyTitle());
			ps.setString(cnt++, addressM.getState());
			ps.setString(cnt++, addressM.getCountry());			
			ps.setString(cnt++, addressM.getDepartment());
			
			ps.setString(cnt++, addressM.getAddress1());			
			ps.setString(cnt++, addressM.getProvinceDesc());			
			ps.setString(cnt++, addressM.getUpdateBy());
			ps.setTimestamp(cnt++, ApplicationDate.getTimestamp());
			
			ps.setString(cnt++, addressM.getVatRegistration());
			ps.setString(cnt++, addressM.getBranchType());
			ps.setString(cnt++, addressM.getBranchName());
			ps.setString(cnt++, addressM.getVatCode());
			
			ps.setString(cnt++, addressM.getBuildingType());
			ps.setString(cnt++, addressM.getAddress2());
			ps.setString(cnt++, addressM.getAddress3());
			ps.setString(cnt++, addressM.getVilapt());
			ps.setString(cnt++, addressM.getAddressFormat());
			
			ps.setString(cnt++, addressM.getAddressIdRef());
			ps.setString(cnt++, addressM.getPhoneIdRef());
			
			ps.setString(cnt++, addressM.getEditFlag());
			ps.setString(cnt++, addressM.getResidenceProvince());
			// WHERE CLAUSE
			ps.setString(cnt++, addressM.getAddressId());
			ps.setString(cnt++, addressM.getPersonalId());
			
			
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
	public void deleteNotInKeyAddress(ArrayList<AddressDataM> addressList,
			String personalID) throws ApplicationException {
		deleteNotInKeyORIG_PERSONAL_ADDRESS(addressList, personalID);
	}

	private void deleteNotInKeyORIG_PERSONAL_ADDRESS(
			ArrayList<AddressDataM> addressList, String personalID) throws ApplicationException {
		PreparedStatement ps = null;
		Connection conn = null;
		try {
            // conn = Get Connection
            conn = getConnection();

            StringBuffer sql = new StringBuffer("");
            sql.append("DELETE FROM ORIG_PERSONAL_ADDRESS ");
            sql.append(" WHERE PERSONAL_ID = ? ");
            
            if (addressList != null && !addressList.isEmpty()) {
                sql.append(" AND ADDRESS_ID NOT IN ( ");
                for (AddressDataM addressM: addressList) {
                	logger.debug("addressM.getAddressId() = "+addressM.getAddressId());
                    sql.append(" '" +addressM.getAddressId() + "', ");
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
