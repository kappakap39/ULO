package com.eaf.orig.ulo.app.xrules.dao;

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
import com.eaf.orig.ulo.model.app.PrivilegeProjectCodeDataM;
import com.eaf.orig.ulo.model.app.PrivilegeProjectCodeDocDataM;
import com.eaf.orig.ulo.model.app.PrivilegeProjectCodeProductCCADataM;
import com.eaf.orig.ulo.model.app.PrivilegeProjectCodeProductInsuranceDataM;
import com.eaf.orig.ulo.model.app.PrivilegeProjectCodeProductSavingDataM;
import com.eaf.orig.ulo.model.app.PrivilegeProjectCodeRccmdPrjCdeDataM;

public class OrigPrivilegeProjectCodeDAOImpl extends OrigObjectDAO implements
		OrigPrivilegeProjectCodeDAO {
	public OrigPrivilegeProjectCodeDAOImpl(String userId){
		this.userId = userId;
	}
	public OrigPrivilegeProjectCodeDAOImpl(){
		
	}
	private String userId = "";
	@Override
	public void createOrigPrivilegeProjectCodeM(
			PrivilegeProjectCodeDataM privilegeProjectCodeM) throws ApplicationException {
		try {
			logger.debug("privilegeProjectCodeM.getPrvlgPrjCdeId() :"+privilegeProjectCodeM.getPrvlgPrjCdeId());
		    if(Util.empty(privilegeProjectCodeM.getPrvlgPrjCdeId())){
				String prvlgPrjCdeId = GenerateUnique.generate(OrigConstant.SeqNames.XRULES_PRVLG_PRJ_CDE_PK); 
				privilegeProjectCodeM.setPrvlgPrjCdeId(prvlgPrjCdeId);
			}
		    privilegeProjectCodeM.setCreateBy(userId);
		    privilegeProjectCodeM.setUpdateBy(userId);
			createTableXRULES_PRVLG_PRJ_CDE(privilegeProjectCodeM);
			
			ArrayList<PrivilegeProjectCodeDocDataM> projCodeDocList = privilegeProjectCodeM.getPrivilegeProjectCodeDocs();
			if(!Util.empty(projCodeDocList)) {
				OrigPrivilegeProjectCodeDocDAO docDAO = ORIGDAOFactory.getPrivilegeProjectCodeDocDAO(userId);
				for(PrivilegeProjectCodeDocDataM projCodeDocM: projCodeDocList){
					projCodeDocM.setPrvlgPrjCdeId(privilegeProjectCodeM.getPrvlgPrjCdeId());
					docDAO.createOrigPrivilegeProjectCodeDocM(projCodeDocM);
				}
			}
			
			ArrayList<PrivilegeProjectCodeProductInsuranceDataM> privProdInsuranceList = privilegeProjectCodeM.getPrivilegeProjectCodeProductInsurances();
			if(!Util.empty(privProdInsuranceList)) {
				OrigPrivilegeProductInsuranceDAO insuranceDAO = ORIGDAOFactory.getPrivilegeProductInsuranceDAO();
				for(PrivilegeProjectCodeProductInsuranceDataM insuranceM: privProdInsuranceList){
					insuranceM.setPrvlgPrjCdeId(privilegeProjectCodeM.getPrvlgPrjCdeId());
					insuranceDAO.createOrigPrivilegeProductInsuranceM(insuranceM);
				}
			}
			
			ArrayList<PrivilegeProjectCodeProductCCADataM> privProdCCAList = privilegeProjectCodeM.getPrivilegeProjectCodeProductCCAs();
			if(!Util.empty(privProdCCAList)) {
				OrigPrivilegeProductCCADAO ccaDAO = ORIGDAOFactory.getPrivilegeProductCCADAO();
				for(PrivilegeProjectCodeProductCCADataM prodCCAM: privProdCCAList){
					prodCCAM.setPrvlgPrjCdeId(privilegeProjectCodeM.getPrvlgPrjCdeId());
					ccaDAO.createOrigPrivilegeProductCCAM(prodCCAM);
				}
			}
			
			ArrayList<PrivilegeProjectCodeRccmdPrjCdeDataM> privRccmProjList = privilegeProjectCodeM.getPrivilegeProjectCodePrjCdes();
			if(!Util.empty(privRccmProjList)) {
				OrigPrivilegeRecommProjCodeDAO rccmdProjDAO = ORIGDAOFactory.getPrivilegeRecommProjCodeDAO();
				for(PrivilegeProjectCodeRccmdPrjCdeDataM rccmdProjM: privRccmProjList){
					rccmdProjM.setPrvlgPrjCdeId(privilegeProjectCodeM.getPrvlgPrjCdeId());
					rccmdProjDAO.createOrigPrivilegeRecommProjCodeM(rccmdProjM);
				}
			}
			
			ArrayList<PrivilegeProjectCodeProductSavingDataM> prodSavingList = privilegeProjectCodeM.getPrivilegeProjectCodeProductSavings();
			if(!Util.empty(prodSavingList)) {
				OrigPrivilegeProductSavingDAO privProdSavingDAO = ORIGDAOFactory.getPrivilegeProductSavingDAO();
				for(PrivilegeProjectCodeProductSavingDataM prodSavingM: prodSavingList){
					prodSavingM.setPrvlgPrjCdeId(privilegeProjectCodeM.getPrvlgPrjCdeId());
					privProdSavingDAO.createOrigPrivilegeProductSavingM(prodSavingM);
				}
			}
			
		} catch (Exception e) {
			logger.fatal(e.getLocalizedMessage());
			throw new ApplicationException(e.getMessage());
		}
	}

	private void createTableXRULES_PRVLG_PRJ_CDE(
			PrivilegeProjectCodeDataM privilegeProjectCodeM) throws ApplicationException {
		PreparedStatement ps = null;
		Connection conn = null;
		try {
			//conn = Get Connection
			conn = getConnection();
			StringBuffer sql = new StringBuffer("");
			sql.append("INSERT INTO XRULES_PRVLG_PRJ_CDE ");
			sql.append("( PRVLG_PRJ_CDE_ID, VER_RESULT_ID, PROJECT_CODE, PRVLG_TYPE, ");
			sql.append(" CREATE_BY, CREATE_DATE, UPDATE_BY, UPDATE_DATE )");

			sql.append(" VALUES(?,?,?,?,  ?,?,?,? )");
			String dSql = String.valueOf(sql);
			logger.debug("Sql=" + dSql);
			ps = conn.prepareStatement(dSql);
			int cnt = 1;
			ps.setString(cnt++, privilegeProjectCodeM.getPrvlgPrjCdeId());
			ps.setString(cnt++, privilegeProjectCodeM.getVerResultId());
			ps.setString(cnt++, privilegeProjectCodeM.getProjectCode());
			ps.setString(cnt++, privilegeProjectCodeM.getProjectType());
			
			ps.setString(cnt++, privilegeProjectCodeM.getCreateBy());
			ps.setTimestamp(cnt++, ApplicationDate.getTimestamp());
			ps.setString(cnt++, privilegeProjectCodeM.getUpdateBy());
			ps.setTimestamp(cnt++, ApplicationDate.getTimestamp());
			
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
	public void deleteOrigPrivilegeProjectCodeM(String verResultId,
			String prvlgPrjCdeId) throws ApplicationException {
		try {
			OrigPrivilegeProjectCodeDocDAO docDAO = ORIGDAOFactory.getPrivilegeProjectCodeDocDAO();
			OrigPrivilegeProductInsuranceDAO insuranceDAO = ORIGDAOFactory.getPrivilegeProductInsuranceDAO();
			OrigPrivilegeProductCCADAO ccaDAO = ORIGDAOFactory.getPrivilegeProductCCADAO();
			OrigPrivilegeRecommProjCodeDAO rccmdProjDAO = ORIGDAOFactory.getPrivilegeRecommProjCodeDAO();
			OrigPrivilegeProductSavingDAO privProdSavingDAO = ORIGDAOFactory.getPrivilegeProductSavingDAO();
			if(Util.empty(prvlgPrjCdeId)) {
				ArrayList<PrivilegeProjectCodeDataM> prvProjCodeList = loadOrigPrivilegeProjectCodeM(verResultId);
				if(!Util.empty(prvProjCodeList)) {
					for(PrivilegeProjectCodeDataM prvProjCodeM: prvProjCodeList) {
						docDAO.deleteOrigPrivilegeProjectCodeDocM(prvProjCodeM.getPrvlgPrjCdeId(), null);
						insuranceDAO.deleteOrigPrivilegeProductInsuranceM(prvProjCodeM.getPrvlgPrjCdeId(), null);
						ccaDAO.deleteOrigPrivilegeProductCCAM(prvProjCodeM.getPrvlgPrjCdeId(), null);
						rccmdProjDAO.deleteOrigPrivilegeRecommProjCodeM(prvProjCodeM.getPrvlgPrjCdeId(), null);
						privProdSavingDAO.deleteOrigPrivilegeProductSavingM(prvProjCodeM.getPrvlgPrjCdeId(), null);
					}
				}
			} else {
				docDAO.deleteOrigPrivilegeProjectCodeDocM(prvlgPrjCdeId, null);
				insuranceDAO.deleteOrigPrivilegeProductInsuranceM(prvlgPrjCdeId, null);
				ccaDAO.deleteOrigPrivilegeProductCCAM(prvlgPrjCdeId, null);
				rccmdProjDAO.deleteOrigPrivilegeRecommProjCodeM(prvlgPrjCdeId, null);
				privProdSavingDAO.deleteOrigPrivilegeProductSavingM(prvlgPrjCdeId, null);
			}
			
			deleteTableXRULES_PRVLG_PRJ_CDE(verResultId, prvlgPrjCdeId);
		} catch (Exception e) {
			logger.fatal(e.getLocalizedMessage());
			throw new ApplicationException(e.getMessage());
		}
	}

	private void deleteTableXRULES_PRVLG_PRJ_CDE(String verResultId,
			String prvlgPrjCdeId) throws ApplicationException {
		PreparedStatement ps = null;
		Connection conn = null;
		try {
			//conn = Get Connection
			conn = getConnection();
			StringBuffer sql = new StringBuffer("");
			sql.append(" DELETE XRULES_PRVLG_PRJ_CDE ");
			sql.append(" WHERE VER_RESULT_ID = ? ");
			if(!Util.empty(prvlgPrjCdeId)) {
				sql.append(" AND PRVLG_PRJ_CDE_ID = ? ");
			}
			String dSql = String.valueOf(sql);
			logger.debug("Sql=" + dSql);
			ps = conn.prepareStatement(dSql);
			ps.setString(1, verResultId);
			if(!Util.empty(prvlgPrjCdeId)) {
				ps.setString(2, prvlgPrjCdeId);
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
	public ArrayList<PrivilegeProjectCodeDataM> loadOrigPrivilegeProjectCodeM(
			String verResultId, Connection conn) throws ApplicationException {
		return loadOrigPrivilegeProjectCodeM(verResultId);
	}
	@Override
	public ArrayList<PrivilegeProjectCodeDataM> loadOrigPrivilegeProjectCodeM(
			String verResultId) throws ApplicationException {
		ArrayList<PrivilegeProjectCodeDataM> result = selectTableXRULES_PRVLG_PRJ_CDE(verResultId);
		if(!Util.empty(result)) {
			OrigPrivilegeProjectCodeDocDAO docDAO = ORIGDAOFactory.getPrivilegeProjectCodeDocDAO();
			OrigPrivilegeProductInsuranceDAO insuranceDAO = ORIGDAOFactory.getPrivilegeProductInsuranceDAO();
			OrigPrivilegeProductCCADAO ccaDAO = ORIGDAOFactory.getPrivilegeProductCCADAO();
			OrigPrivilegeRecommProjCodeDAO rccmdProjDAO = ORIGDAOFactory.getPrivilegeRecommProjCodeDAO();
			OrigPrivilegeProductSavingDAO privProdSavingDAO = ORIGDAOFactory.getPrivilegeProductSavingDAO();
			
			for(PrivilegeProjectCodeDataM projCodeM: result) {
				ArrayList<PrivilegeProjectCodeDocDataM> projCodeDocList = docDAO.loadOrigPrivilegeProjectCodeDocM(projCodeM.getPrvlgPrjCdeId());
				if(!Util.empty(projCodeDocList)) {
					projCodeM.setPrivilegeProjectCodeDocs(projCodeDocList);
				}
			
				ArrayList<PrivilegeProjectCodeProductInsuranceDataM> privProdInsuranceList = insuranceDAO.loadOrigPrivilegeProductInsuranceM(projCodeM.getPrvlgPrjCdeId());
				if(!Util.empty(privProdInsuranceList)) {
					projCodeM.setPrivilegeProjectCodeProductInsurances(privProdInsuranceList);
				}
				
				ArrayList<PrivilegeProjectCodeProductCCADataM> privProdCCAList = ccaDAO.loadOrigPrivilegeProductCCAM(projCodeM.getPrvlgPrjCdeId());
				if(!Util.empty(privProdCCAList)) {
					projCodeM.setPrivilegeProjectCodeProductCCAs(privProdCCAList);
				}
				
				ArrayList<PrivilegeProjectCodeRccmdPrjCdeDataM> privRccmProjList = rccmdProjDAO.loadOrigPrivilegeRecommProjCodeM(projCodeM.getPrvlgPrjCdeId());
				if(!Util.empty(privRccmProjList)) {
					projCodeM.setPrivilegeProjectCodePrjCdes(privRccmProjList);
				}
				
				ArrayList<PrivilegeProjectCodeProductSavingDataM> prodSavingList = privProdSavingDAO.loadOrigPrivilegeProductSavingM(projCodeM.getPrvlgPrjCdeId());
				if(!Util.empty(prodSavingList)) {
					projCodeM.setPrivilegeProjectCodeProductSavings(prodSavingList);
				}
			}
		}
		return result;
	}

	private ArrayList<PrivilegeProjectCodeDataM> selectTableXRULES_PRVLG_PRJ_CDE(
			String verResultId) throws ApplicationException {
		PreparedStatement ps = null;
		ResultSet rs = null;
		Connection conn = null;
		ArrayList<PrivilegeProjectCodeDataM> prvProjCodeList = new ArrayList<PrivilegeProjectCodeDataM>();
		try {
			//conn = Get Connection
			conn = getConnection();
			StringBuffer sql = new StringBuffer("");
			sql.append("SELECT PRVLG_PRJ_CDE_ID, VER_RESULT_ID, PROJECT_CODE,PRVLG_TYPE, ");
			sql.append(" CREATE_DATE, CREATE_BY, UPDATE_DATE, UPDATE_BY ");
			sql.append(" FROM XRULES_PRVLG_PRJ_CDE WHERE VER_RESULT_ID = ? ");
			String dSql = String.valueOf(sql);
			logger.debug("Sql=" + dSql);
			ps = conn.prepareStatement(dSql);
			rs = null;
			ps.setString(1, verResultId);

			rs = ps.executeQuery();

			while(rs.next()) {
				PrivilegeProjectCodeDataM privProjCodeM = new PrivilegeProjectCodeDataM();
				privProjCodeM.setPrvlgPrjCdeId(rs.getString("PRVLG_PRJ_CDE_ID"));
				privProjCodeM.setVerResultId(rs.getString("VER_RESULT_ID"));
				privProjCodeM.setProjectCode(rs.getString("PROJECT_CODE"));
				privProjCodeM.setProjectType(rs.getString("PRVLG_TYPE"));
				
				privProjCodeM.setCreateBy(rs.getString("CREATE_BY"));
				privProjCodeM.setCreateDate(rs.getTimestamp("CREATE_DATE"));
				privProjCodeM.setUpdateBy(rs.getString("UPDATE_BY"));
				privProjCodeM.setUpdateDate(rs.getTimestamp("UPDATE_DATE"));
				
				prvProjCodeList.add(privProjCodeM);
			}

			return prvProjCodeList;
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
	public void saveUpdateOrigPrivilegeProjectCodeM(
			PrivilegeProjectCodeDataM privilegeProjectCodeM) throws ApplicationException {
		try { 
			int returnRows = 0;
			privilegeProjectCodeM.setUpdateBy(userId);
			returnRows = updateTableXRULES_PRVLG_PRJ_CDE(privilegeProjectCodeM);
			if (returnRows == 0) {
				privilegeProjectCodeM.setCreateBy(userId);
			    privilegeProjectCodeM.setUpdateBy(userId);
				createOrigPrivilegeProjectCodeM(privilegeProjectCodeM);
			} else {
				OrigPrivilegeProjectCodeDocDAO docDAO = ORIGDAOFactory.getPrivilegeProjectCodeDocDAO(userId);
				OrigPrivilegeProductInsuranceDAO insuranceDAO = ORIGDAOFactory.getPrivilegeProductInsuranceDAO(userId);
				OrigPrivilegeProductCCADAO ccaDAO = ORIGDAOFactory.getPrivilegeProductCCADAO(userId);
				OrigPrivilegeRecommProjCodeDAO rccmdProjDAO = ORIGDAOFactory.getPrivilegeRecommProjCodeDAO(userId);
				OrigPrivilegeProductSavingDAO privProdSavingDAO = ORIGDAOFactory.getPrivilegeProductSavingDAO(userId);
				
				ArrayList<PrivilegeProjectCodeDocDataM> projCodeDocList = privilegeProjectCodeM.getPrivilegeProjectCodeDocs();
				if(!Util.empty(projCodeDocList)) {
					for(PrivilegeProjectCodeDocDataM projCodeDocM: projCodeDocList){
						projCodeDocM.setPrvlgPrjCdeId(privilegeProjectCodeM.getPrvlgPrjCdeId());
						docDAO.saveUpdateOrigPrivilegeProjectCodeDocM(projCodeDocM);
					}
				}
				
				ArrayList<PrivilegeProjectCodeProductInsuranceDataM> privProdInsuranceList = privilegeProjectCodeM.getPrivilegeProjectCodeProductInsurances();
				if(!Util.empty(privProdInsuranceList)) {
					for(PrivilegeProjectCodeProductInsuranceDataM insuranceM: privProdInsuranceList){
						insuranceM.setPrvlgPrjCdeId(privilegeProjectCodeM.getPrvlgPrjCdeId());
						insuranceDAO.saveUpdateOrigPrivilegeProductInsuranceM(insuranceM);
					}
				}
				
				ArrayList<PrivilegeProjectCodeProductCCADataM> privProdCCAList = privilegeProjectCodeM.getPrivilegeProjectCodeProductCCAs();
				if(!Util.empty(privProdCCAList)) {
					for(PrivilegeProjectCodeProductCCADataM prodCCAM: privProdCCAList){
						prodCCAM.setPrvlgPrjCdeId(privilegeProjectCodeM.getPrvlgPrjCdeId());
						ccaDAO.saveUpdateOrigPrivilegeProductCCAM(prodCCAM);
					}
				}
				
				ArrayList<PrivilegeProjectCodeRccmdPrjCdeDataM> privRccmProjList = privilegeProjectCodeM.getPrivilegeProjectCodePrjCdes();
				if(!Util.empty(privRccmProjList)) {
					for(PrivilegeProjectCodeRccmdPrjCdeDataM rccmdProjM: privRccmProjList){
						rccmdProjM.setPrvlgPrjCdeId(privilegeProjectCodeM.getPrvlgPrjCdeId());
						rccmdProjDAO.saveUpdateOrigPrivilegeRecommProjCodeM(rccmdProjM);
					}
				}
				
				ArrayList<PrivilegeProjectCodeProductSavingDataM> prodSavingList = privilegeProjectCodeM.getPrivilegeProjectCodeProductSavings();
				if(!Util.empty(prodSavingList)) {
					for(PrivilegeProjectCodeProductSavingDataM prodSavingM: prodSavingList){
						prodSavingM.setPrvlgPrjCdeId(privilegeProjectCodeM.getPrvlgPrjCdeId());
						privProdSavingDAO.saveUpdateOrigPrivilegeProductSavingM(prodSavingM);
					}
				}
				
				docDAO.deleteNotInKeyPrivilegeProjectCodeDoc(projCodeDocList, privilegeProjectCodeM.getPrvlgPrjCdeId());
				insuranceDAO.deleteNotInKeyPrivilegeProductInsurance(privProdInsuranceList, privilegeProjectCodeM.getPrvlgPrjCdeId());
				ccaDAO.deleteNotInKeyPrivilegeProductCCA(privProdCCAList, privilegeProjectCodeM.getPrvlgPrjCdeId());
				rccmdProjDAO.deleteNotInKeyPrivilegeRecommProjCode(privRccmProjList, privilegeProjectCodeM.getPrvlgPrjCdeId());
				privProdSavingDAO.deleteNotInKeyPrivilegeProductSaving(prodSavingList, privilegeProjectCodeM.getPrvlgPrjCdeId());
			}
		} catch (Exception e) {
			logger.fatal(e.getLocalizedMessage());
			throw new ApplicationException(e.getMessage());
		}
	}

	private int updateTableXRULES_PRVLG_PRJ_CDE(
			PrivilegeProjectCodeDataM privilegeProjectCodeM) throws ApplicationException {
		int returnRows = 0;
		PreparedStatement ps = null;
		Connection conn = null;
		try {
			//conn = Get Connection
			conn = getConnection();
			StringBuffer sql = new StringBuffer("");
			sql.append("UPDATE XRULES_PRVLG_PRJ_CDE ");
			sql.append(" SET PROJECT_CODE = ?, PRVLG_TYPE = ?, UPDATE_DATE = ?, UPDATE_BY = ? ");
			sql.append(" WHERE VER_RESULT_ID = ? AND PRVLG_PRJ_CDE_ID = ? ");
			String dSql = String.valueOf(sql);
			logger.debug("Sql=" + dSql);
			ps = conn.prepareStatement(dSql);
			int cnt = 1;
			ps.setString(cnt++, privilegeProjectCodeM.getProjectCode());
			ps.setString(cnt++, privilegeProjectCodeM.getProjectType());
			ps.setTimestamp(cnt++, ApplicationDate.getTimestamp());
			ps.setString(cnt++, privilegeProjectCodeM.getUpdateBy());
			
			// WHERE CLAUSE
			ps.setString(cnt++, privilegeProjectCodeM.getVerResultId());
			ps.setString(cnt++, privilegeProjectCodeM.getPrvlgPrjCdeId());
			
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
	public void deleteNotInKeyPrivilegeProjectCode(
			ArrayList<PrivilegeProjectCodeDataM> privilegeProjectCodeList,
			String verResultId) throws ApplicationException {
		ArrayList<String> notInKeyList = selectNotInKeyTableXRULES_PRVLG_PRJ_CDE(privilegeProjectCodeList, verResultId);
		if(!Util.empty(notInKeyList)) {
			OrigPrivilegeProjectCodeDocDAO docDAO = ORIGDAOFactory.getPrivilegeProjectCodeDocDAO();
			OrigPrivilegeProductInsuranceDAO insuranceDAO = ORIGDAOFactory.getPrivilegeProductInsuranceDAO();
			OrigPrivilegeProductCCADAO ccaDAO = ORIGDAOFactory.getPrivilegeProductCCADAO();
			OrigPrivilegeRecommProjCodeDAO rccmdProjDAO = ORIGDAOFactory.getPrivilegeRecommProjCodeDAO();
			OrigPrivilegeProductSavingDAO privProdSavingDAO = ORIGDAOFactory.getPrivilegeProductSavingDAO();
			for(String prvlgPrjCdeId: notInKeyList) {
				docDAO.deleteOrigPrivilegeProjectCodeDocM(prvlgPrjCdeId, null);
				insuranceDAO.deleteOrigPrivilegeProductInsuranceM(prvlgPrjCdeId, null);
				ccaDAO.deleteOrigPrivilegeProductCCAM(prvlgPrjCdeId, null);
				rccmdProjDAO.deleteOrigPrivilegeRecommProjCodeM(prvlgPrjCdeId, null);
				privProdSavingDAO.deleteOrigPrivilegeProductSavingM(prvlgPrjCdeId, null);
			}
		}
		
		deleteNotInKeyTableXRULES_PRVLG_PRJ_CDE(privilegeProjectCodeList, verResultId);
	}

	private ArrayList<String> selectNotInKeyTableXRULES_PRVLG_PRJ_CDE(
			ArrayList<PrivilegeProjectCodeDataM> privilegeProjectCodeList,
			String verResultId) throws ApplicationException {
		PreparedStatement ps = null;
		ResultSet rs = null;
		Connection conn = null;
		ArrayList<String> notInKeyList = new ArrayList<String>();
		try {
			//conn = Get Connection
			conn = getConnection();
			StringBuffer sql = new StringBuffer("");
			sql.append("SELECT PRVLG_PRJ_CDE_ID ");
			sql.append(" FROM XRULES_PRVLG_PRJ_CDE WHERE VER_RESULT_ID = ? ");
			if (!Util.empty(privilegeProjectCodeList)) {
                sql.append(" AND PRVLG_PRJ_CDE_ID NOT IN ( ");

                for (PrivilegeProjectCodeDataM prvProjCodeM: privilegeProjectCodeList) {
                    sql.append(" '" + prvProjCodeM.getPrvlgPrjCdeId() + "' , ");
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
			ps.setString(1, verResultId);

			rs = ps.executeQuery();

			while (rs.next()) {
				String notInKeyId = rs.getString("PRVLG_PRJ_CDE_ID");
				notInKeyList.add(notInKeyId);
			}

			return notInKeyList;
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

	private void deleteNotInKeyTableXRULES_PRVLG_PRJ_CDE(
			ArrayList<PrivilegeProjectCodeDataM> privilegeProjectCodeList,
			String verResultId) throws ApplicationException {
		PreparedStatement ps = null;
		Connection conn = null;
        try {
            // conn = Get Connection
            conn = getConnection();

            StringBuffer sql = new StringBuffer("");
            sql.append("DELETE FROM XRULES_PRVLG_PRJ_CDE ");
            sql.append(" WHERE VER_RESULT_ID = ? ");
            
            if (!Util.empty(privilegeProjectCodeList)) {
                sql.append(" AND PRVLG_PRJ_CDE_ID NOT IN ( ");

                for (PrivilegeProjectCodeDataM projCodeM: privilegeProjectCodeList) {
                    sql.append(" '" + projCodeM.getPrvlgPrjCdeId() + "' , ");
                }

                if (sql.toString().trim().endsWith(",")) {
                    sql.delete(sql.toString().lastIndexOf(","), sql.toString().length());
                }

                sql.append(" ) ");
            }

            String dSql = String.valueOf(sql);
            ps = conn.prepareStatement(dSql);
            ps.setString(1, verResultId);
            
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
