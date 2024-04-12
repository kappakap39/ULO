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
import com.eaf.orig.ulo.model.app.PrivilegeProjectCodeDocDataM;
import com.eaf.orig.ulo.model.app.PrivilegeProjectCodeExceptionDocDataM;
import com.eaf.orig.ulo.model.app.PrivilegeProjectCodeKassetDocDataM;
import com.eaf.orig.ulo.model.app.PrivilegeProjectCodeMGMDocDataM;
import com.eaf.orig.ulo.model.app.PrivilegeProjectCodeProductTradingDataM;
import com.eaf.orig.ulo.model.app.PrivilegeProjectCodeTransferDocDataM;

public class OrigPrivilegeProjectCodeDocDAOImpl extends OrigObjectDAO implements
		OrigPrivilegeProjectCodeDocDAO {
	public OrigPrivilegeProjectCodeDocDAOImpl(String userId){
		this.userId = userId;
	}
	public OrigPrivilegeProjectCodeDocDAOImpl(){
		
	}
	private String userId = "";
	@Override
	public void createOrigPrivilegeProjectCodeDocM(
			PrivilegeProjectCodeDocDataM privilegeProjectCodeDocM)
			throws ApplicationException {
		try {
			logger.debug("privilegeProjectCodeDocM.getPrvlgDocId() :"+privilegeProjectCodeDocM.getPrvlgDocId());
		    if(Util.empty(privilegeProjectCodeDocM.getPrvlgDocId())){
				String prvlgDocId = GenerateUnique.generate(OrigConstant.SeqNames.XRULES_PRVLG_DOC_PK); 
				privilegeProjectCodeDocM.setPrvlgDocId(prvlgDocId);
			}
		    privilegeProjectCodeDocM.setCreateBy(userId);
		    privilegeProjectCodeDocM.setUpdateBy(userId);
			createTableXRULES_PRVLG_DOC(privilegeProjectCodeDocM);

			ArrayList<PrivilegeProjectCodeKassetDocDataM> kassetDocList = privilegeProjectCodeDocM.getPrivilegeProjectCodeKassetDocs();
			if(!Util.empty(kassetDocList)) {
				OrigPrivilegeProjectCodeKassetDocDAO docDAO = ORIGDAOFactory.getPrivilegeProjectCodeKassetDocDAO(userId);
				for(PrivilegeProjectCodeKassetDocDataM kassetDocM: kassetDocList){
					kassetDocM.setPrvlgDocId(privilegeProjectCodeDocM.getPrvlgDocId());
					docDAO.createOrigPrivilegeProjectCodeKassetDocM(kassetDocM);
				}
			}
			
			ArrayList<PrivilegeProjectCodeProductTradingDataM> tradingDocList = privilegeProjectCodeDocM.getPrivilegeProjectCodeProductTradings();
			if(!Util.empty(tradingDocList)) {
				OrigPrivilegeProjectCodeTradingDocDAO docDAO = ORIGDAOFactory.getPrivilegeProjectCodeTradingDocDAO(userId);
				for(PrivilegeProjectCodeProductTradingDataM tradingDocM : tradingDocList){
					tradingDocM.setPrvlgDocId(privilegeProjectCodeDocM.getPrvlgDocId());
					docDAO.createOrigPrivilegeProjectCodeTradingDocM(tradingDocM);
				}
			}
			
			ArrayList<PrivilegeProjectCodeMGMDocDataM> mgmDocList = privilegeProjectCodeDocM.getPrivilegeProjectCodeMGMDocs();
			if(!Util.empty(mgmDocList)) {
				OrigPrivilegeProjectCodeMGMDocDAO docDAO = ORIGDAOFactory.getPrivilegeProjectCodeMGMDocDAO(userId);
				for(PrivilegeProjectCodeMGMDocDataM mgmDocM: mgmDocList){
					mgmDocM.setPrvlgDocId(privilegeProjectCodeDocM.getPrvlgDocId());
					docDAO.createOrigPrivilegeProjectCodeMGMDocM(mgmDocM);
				}
			}
			
			ArrayList<PrivilegeProjectCodeExceptionDocDataM> exceptionDocList = privilegeProjectCodeDocM.getPrivilegeProjectCodeExceptionDocs();
			if(!Util.empty(exceptionDocList)) {
				OrigPrivilegeProjectCodeExceptionDocDAO docDAO = ORIGDAOFactory.getPrivilegeProjectCodeExceptionDocDAO(userId);
				for(PrivilegeProjectCodeExceptionDocDataM exceptionDocM: exceptionDocList){
					exceptionDocM.setPrvlgDocId(privilegeProjectCodeDocM.getPrvlgDocId());
					docDAO.createOrigPrivilegeProjectCodeExceptionDocM(exceptionDocM);
				}
			}
			
			ArrayList<PrivilegeProjectCodeTransferDocDataM> transferDocList = privilegeProjectCodeDocM.getPrivilegeProjectCodeTransferDocs();
			if(!Util.empty(transferDocList)) {
				OrigPrivilegeProjectCodeTransferDocDAO docDAO = ORIGDAOFactory.getPrivilegeProjectCodeTransferDocDAO(userId);
				for(PrivilegeProjectCodeTransferDocDataM transferDocM: transferDocList){
					transferDocM.setPrvlgDocId(privilegeProjectCodeDocM.getPrvlgDocId());
					docDAO.createOrigPrivilegeProjectCodeTransferDocM(transferDocM);
				}
			}
			
		} catch (Exception e) {
			logger.fatal(e.getLocalizedMessage());
			throw new ApplicationException(e.getMessage());
		}
	}

	private void createTableXRULES_PRVLG_DOC(
			PrivilegeProjectCodeDocDataM privilegeProjectCodeDocM) throws ApplicationException {
		PreparedStatement ps = null;
		Connection conn = null;
		try {
			//conn = Get Connection
			conn = getConnection();
			StringBuffer sql = new StringBuffer("");
			sql.append("INSERT INTO XRULES_PRVLG_DOC ");
			sql.append("( PRVLG_DOC_ID, PRVLG_PRJ_CDE_ID, DOC_TYPE, ");
			sql.append(" CREATE_BY, CREATE_DATE, UPDATE_BY, UPDATE_DATE )");

			sql.append(" VALUES(?,?,?,  ?,?,?,? )");
			String dSql = String.valueOf(sql);
			logger.debug("Sql=" + dSql);
			ps = conn.prepareStatement(dSql);
			int cnt = 1;
			ps.setString(cnt++, privilegeProjectCodeDocM.getPrvlgDocId());
			ps.setString(cnt++, privilegeProjectCodeDocM.getPrvlgPrjCdeId());
			ps.setString(cnt++, privilegeProjectCodeDocM.getDocType());
			
			ps.setString(cnt++, privilegeProjectCodeDocM.getCreateBy());
			ps.setTimestamp(cnt++, ApplicationDate.getTimestamp());
			ps.setString(cnt++, privilegeProjectCodeDocM.getUpdateBy());
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
	public void deleteOrigPrivilegeProjectCodeDocM(String prvlgPrjCdeId,
			String prvlgDocId) throws ApplicationException {
		try {
			OrigPrivilegeProjectCodeKassetDocDAO kassetDocDAO = ORIGDAOFactory.getPrivilegeProjectCodeKassetDocDAO();
			OrigPrivilegeProjectCodeTradingDocDAO tradingDocDAO = ORIGDAOFactory.getPrivilegeProjectCodeTradingDocDAO();
			OrigPrivilegeProjectCodeMGMDocDAO mgmDocDAO = ORIGDAOFactory.getPrivilegeProjectCodeMGMDocDAO();
			OrigPrivilegeProjectCodeExceptionDocDAO exceptionDocDAO = ORIGDAOFactory.getPrivilegeProjectCodeExceptionDocDAO();
			OrigPrivilegeProjectCodeTransferDocDAO transferDocDAO = ORIGDAOFactory.getPrivilegeProjectCodeTransferDocDAO();
			if(Util.empty(prvlgDocId)) {
				ArrayList<PrivilegeProjectCodeDocDataM> prvProjCodeDocList = loadOrigPrivilegeProjectCodeDocM(prvlgPrjCdeId);
				if(!Util.empty(prvProjCodeDocList)) {
					for(PrivilegeProjectCodeDocDataM prvProjCodeDocM: prvProjCodeDocList) {
						kassetDocDAO.deleteOrigPrivilegeProjectCodeKassetDocM(prvProjCodeDocM.getPrvlgDocId(), null);
						mgmDocDAO.deleteOrigPrivilegeProjectCodeMGMDocM(prvProjCodeDocM.getPrvlgDocId(), null);
						exceptionDocDAO.deleteOrigPrivilegeProjectCodeExceptionDocM(prvProjCodeDocM.getPrvlgDocId(), null);
						transferDocDAO.deleteOrigPrivilegeProjectCodeTransferDocM(prvProjCodeDocM.getPrvlgDocId(), null);
						tradingDocDAO.deleteOrigPrivilegeProjectCodeTradingDocM(prvProjCodeDocM.getPrvlgDocId(), null);
					}
				}
			} else {
				tradingDocDAO.deleteOrigPrivilegeProjectCodeTradingDocM(prvlgDocId, null);
				kassetDocDAO.deleteOrigPrivilegeProjectCodeKassetDocM(prvlgDocId, null);
				mgmDocDAO.deleteOrigPrivilegeProjectCodeMGMDocM(prvlgDocId, null);
				exceptionDocDAO.deleteOrigPrivilegeProjectCodeExceptionDocM(prvlgDocId, null);
				transferDocDAO.deleteOrigPrivilegeProjectCodeTransferDocM(prvlgDocId, null);
			}
			
			deleteTableXRULES_PRVLG_DOC(prvlgPrjCdeId, prvlgDocId);
		} catch (Exception e) {
			logger.fatal(e.getLocalizedMessage());
			throw new ApplicationException(e.getMessage());
		}
	}

	private void deleteTableXRULES_PRVLG_DOC(String prvlgPrjCdeId,
			String prvlgDocId) throws ApplicationException {
		PreparedStatement ps = null;
		Connection conn = null;
		try {
			//conn = Get Connection
			conn = getConnection();
			StringBuffer sql = new StringBuffer("");
			sql.append(" DELETE XRULES_PRVLG_DOC ");
			sql.append(" WHERE PRVLG_PRJ_CDE_ID = ? ");
			if(!Util.empty(prvlgDocId)) {
				sql.append(" AND PRVLG_DOC_ID = ? ");
			}
			String dSql = String.valueOf(sql);
			logger.debug("Sql=" + dSql);
			ps = conn.prepareStatement(dSql);
			ps.setString(1, prvlgPrjCdeId);
			if(!Util.empty(prvlgDocId)) {
				ps.setString(2, prvlgDocId);
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
	public ArrayList<PrivilegeProjectCodeDocDataM> loadOrigPrivilegeProjectCodeDocM(
			String prvlgPrjCdeId) throws ApplicationException {
		ArrayList<PrivilegeProjectCodeDocDataM> result = selectTableXRULES_PRVLG_DOC(prvlgPrjCdeId);
		if(!Util.empty(result)) {
			OrigPrivilegeProjectCodeKassetDocDAO kassetDocDAO = ORIGDAOFactory.getPrivilegeProjectCodeKassetDocDAO();
			OrigPrivilegeProjectCodeTradingDocDAO tradingDocDAO = ORIGDAOFactory.getPrivilegeProjectCodeTradingDocDAO();
			OrigPrivilegeProjectCodeMGMDocDAO mgmDocDAO = ORIGDAOFactory.getPrivilegeProjectCodeMGMDocDAO();
			OrigPrivilegeProjectCodeExceptionDocDAO exceptionDocDAO = ORIGDAOFactory.getPrivilegeProjectCodeExceptionDocDAO();
			OrigPrivilegeProjectCodeTransferDocDAO transferDocDAO = ORIGDAOFactory.getPrivilegeProjectCodeTransferDocDAO();
			for(PrivilegeProjectCodeDocDataM projCodeDocM: result) {
				ArrayList<PrivilegeProjectCodeKassetDocDataM> kassetDocList = kassetDocDAO.loadOrigPrivilegeProjectCodeKassetDocM(projCodeDocM.getPrvlgDocId());
				if(!Util.empty(kassetDocList)) {
					projCodeDocM.setPrivilegeProjectCodeKassetDocs(kassetDocList);
				}
				
				ArrayList<PrivilegeProjectCodeProductTradingDataM> tradingDocList = tradingDocDAO.loadPrivilegeProjectCodeTradingDocM(projCodeDocM.getPrvlgDocId());
				if(!Util.empty(tradingDocList)) {
					projCodeDocM.setPrivilegeProjectCodeProductTradings(tradingDocList);
				}
				
				ArrayList<PrivilegeProjectCodeMGMDocDataM> mgmDocList = mgmDocDAO.loadOrigPrivilegeProjectCodeMGMDocM(projCodeDocM.getPrvlgDocId());
				if(!Util.empty(mgmDocList)) {
					projCodeDocM.setPrivilegeProjectCodeMGMDocs(mgmDocList);
				}
				
				ArrayList<PrivilegeProjectCodeExceptionDocDataM> exceptionDocList = exceptionDocDAO.loadOrigPrivilegeProjectCodeExceptionDocM(projCodeDocM.getPrvlgDocId());
				if(!Util.empty(exceptionDocList)) {
					projCodeDocM.setPrivilegeProjectCodeExceptionDocs(exceptionDocList);
				}
				
				ArrayList<PrivilegeProjectCodeTransferDocDataM> transferDocList = transferDocDAO.loadOrigPrivilegeProjectCodeTransferDocM(projCodeDocM.getPrvlgDocId());
				if(!Util.empty(transferDocList)) {
					projCodeDocM.setPrivilegeProjectCodeTransferDocs(transferDocList);
				}
				
			}
		}
		return result;
	}

	private ArrayList<PrivilegeProjectCodeDocDataM> selectTableXRULES_PRVLG_DOC(
			String prvlgPrjCdeId) throws ApplicationException {
		PreparedStatement ps = null;
		ResultSet rs = null;
		Connection conn = null;
		ArrayList<PrivilegeProjectCodeDocDataM> prvProjCodeDocList = new ArrayList<PrivilegeProjectCodeDocDataM>();
		try {
			//conn = Get Connection
			conn = getConnection();
			StringBuffer sql = new StringBuffer("");
			sql.append("SELECT PRVLG_DOC_ID, PRVLG_PRJ_CDE_ID, DOC_TYPE, ");
			sql.append(" CREATE_DATE, CREATE_BY, UPDATE_DATE, UPDATE_BY ");
			sql.append(" FROM XRULES_PRVLG_DOC WHERE PRVLG_PRJ_CDE_ID = ? ");
			String dSql = String.valueOf(sql);
			logger.debug("Sql=" + dSql);
			ps = conn.prepareStatement(dSql);
			rs = null;
			ps.setString(1, prvlgPrjCdeId);

			rs = ps.executeQuery();

			while(rs.next()) {
				PrivilegeProjectCodeDocDataM privProjCodeDocM = new PrivilegeProjectCodeDocDataM();
				privProjCodeDocM.setPrvlgDocId(rs.getString("PRVLG_DOC_ID"));
				privProjCodeDocM.setPrvlgPrjCdeId(rs.getString("PRVLG_PRJ_CDE_ID"));
				privProjCodeDocM.setDocType(rs.getString("DOC_TYPE"));
				
				privProjCodeDocM.setCreateBy(rs.getString("CREATE_BY"));
				privProjCodeDocM.setCreateDate(rs.getTimestamp("CREATE_DATE"));
				privProjCodeDocM.setUpdateBy(rs.getString("UPDATE_BY"));
				privProjCodeDocM.setUpdateDate(rs.getTimestamp("UPDATE_DATE"));
				
				prvProjCodeDocList.add(privProjCodeDocM);
			}

			return prvProjCodeDocList;
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
	public void saveUpdateOrigPrivilegeProjectCodeDocM(
			PrivilegeProjectCodeDocDataM privilegeProjectCodeDocM)
			throws ApplicationException {
		try { 
			int returnRows = 0;
			privilegeProjectCodeDocM.setUpdateBy(userId);
			returnRows = updateTableXRULES_PRVLG_DOC(privilegeProjectCodeDocM);
			if (returnRows == 0) {
				privilegeProjectCodeDocM.setCreateBy(userId);
			    privilegeProjectCodeDocM.setUpdateBy(userId);
				createOrigPrivilegeProjectCodeDocM(privilegeProjectCodeDocM);
			} else {
				OrigPrivilegeProjectCodeKassetDocDAO kassetDocDAO = ORIGDAOFactory.getPrivilegeProjectCodeKassetDocDAO(userId);
				OrigPrivilegeProjectCodeTradingDocDAO tradingDocDAO = ORIGDAOFactory.getPrivilegeProjectCodeTradingDocDAO(userId);
				OrigPrivilegeProjectCodeMGMDocDAO mgmDocDAO = ORIGDAOFactory.getPrivilegeProjectCodeMGMDocDAO(userId);
				OrigPrivilegeProjectCodeExceptionDocDAO exceptionDocDAO = ORIGDAOFactory.getPrivilegeProjectCodeExceptionDocDAO(userId);
				OrigPrivilegeProjectCodeTransferDocDAO transferDocDAO = ORIGDAOFactory.getPrivilegeProjectCodeTransferDocDAO(userId);
				
				ArrayList<PrivilegeProjectCodeKassetDocDataM> kassetDocList = privilegeProjectCodeDocM.getPrivilegeProjectCodeKassetDocs();
				if(!Util.empty(kassetDocList)) {
					for(PrivilegeProjectCodeKassetDocDataM kassetDocM: kassetDocList){
						kassetDocM.setPrvlgDocId(privilegeProjectCodeDocM.getPrvlgDocId());
						kassetDocDAO.saveUpdateOrigPrivilegeProjectCodeKassetDocM(kassetDocM);
					}
				}
				
				ArrayList<PrivilegeProjectCodeProductTradingDataM> tradingDocList = privilegeProjectCodeDocM.getPrivilegeProjectCodeProductTradings();
				if(!Util.empty(tradingDocList)) {
					for(PrivilegeProjectCodeProductTradingDataM tradingDocM: tradingDocList){
						tradingDocM.setPrvlgDocId(privilegeProjectCodeDocM.getPrvlgDocId());
						tradingDocDAO.saveUpdatePrivilegeProjectCodeTradingDocM(tradingDocM);
					}
				}
				
				ArrayList<PrivilegeProjectCodeMGMDocDataM> mgmDocList = privilegeProjectCodeDocM.getPrivilegeProjectCodeMGMDocs();
				if(!Util.empty(mgmDocList)) {
					for(PrivilegeProjectCodeMGMDocDataM mgmDocM: mgmDocList){
						mgmDocM.setPrvlgDocId(privilegeProjectCodeDocM.getPrvlgDocId());
						mgmDocDAO.saveUpdateOrigPrivilegeProjectCodeMGMDocM(mgmDocM);
					}
				}
				
				ArrayList<PrivilegeProjectCodeExceptionDocDataM> exceptionDocList = privilegeProjectCodeDocM.getPrivilegeProjectCodeExceptionDocs();
				if(!Util.empty(exceptionDocList)) {
					for(PrivilegeProjectCodeExceptionDocDataM exceptionDocM: exceptionDocList){
						exceptionDocM.setPrvlgDocId(privilegeProjectCodeDocM.getPrvlgDocId());
						exceptionDocDAO.saveUpdateOrigPrivilegeProjectCodeExceptionDocM(exceptionDocM);
					}
				}
				
				ArrayList<PrivilegeProjectCodeTransferDocDataM> transferDocList = privilegeProjectCodeDocM.getPrivilegeProjectCodeTransferDocs();
				if(!Util.empty(transferDocList)) {
					for(PrivilegeProjectCodeTransferDocDataM transferDocM: transferDocList){
						transferDocM.setPrvlgDocId(privilegeProjectCodeDocM.getPrvlgDocId());
						transferDocDAO.saveUpdateOrigPrivilegeProjectCodeTransferDocM(transferDocM);
					}
				}
				
				tradingDocDAO.deleteNotInKeyPrivilegeProjectCodeTradingDocM(tradingDocList,privilegeProjectCodeDocM.getPrvlgDocId());
				kassetDocDAO.deleteNotInKeyPrivilegeProjectCodeKassetDoc(kassetDocList, privilegeProjectCodeDocM.getPrvlgDocId());
				mgmDocDAO.deleteNotInKeyPrivilegeProjectCodeMGMDoc(mgmDocList, privilegeProjectCodeDocM.getPrvlgDocId());
				exceptionDocDAO.deleteNotInKeyPrivilegeProjectCodeExceptionDoc(exceptionDocList, privilegeProjectCodeDocM.getPrvlgDocId());
				transferDocDAO.deleteNotInKeyPrivilegeProjectCodeTransferDoc(transferDocList, privilegeProjectCodeDocM.getPrvlgDocId());
			}
		} catch (Exception e) {
			logger.fatal(e.getLocalizedMessage());
			throw new ApplicationException(e.getMessage());
		}
	}

	private int updateTableXRULES_PRVLG_DOC(
			PrivilegeProjectCodeDocDataM privilegeProjectCodeDocM) throws ApplicationException {
		int returnRows = 0;
		PreparedStatement ps = null;
		Connection conn = null;
		try {
			//conn = Get Connection
			conn = getConnection();
			StringBuffer sql = new StringBuffer("");
			sql.append("UPDATE XRULES_PRVLG_DOC ");
			sql.append(" SET DOC_TYPE = ?, UPDATE_DATE = ?, UPDATE_BY = ? ");
			sql.append(" WHERE PRVLG_PRJ_CDE_ID = ? AND PRVLG_DOC_ID = ? ");
			String dSql = String.valueOf(sql);
			logger.debug("Sql=" + dSql);
			ps = conn.prepareStatement(dSql);
			int cnt = 1;
			ps.setString(cnt++, privilegeProjectCodeDocM.getDocType());
			ps.setTimestamp(cnt++, ApplicationDate.getTimestamp());
			ps.setString(cnt++, privilegeProjectCodeDocM.getUpdateBy());
			
			// WHERE CLAUSE
			ps.setString(cnt++, privilegeProjectCodeDocM.getPrvlgPrjCdeId());
			ps.setString(cnt++, privilegeProjectCodeDocM.getPrvlgDocId());
			
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
	public void deleteNotInKeyPrivilegeProjectCodeDoc(
			ArrayList<PrivilegeProjectCodeDocDataM> privilegeProjectCodeDocList,
			String prvlgPrjCdeId) throws ApplicationException {
		ArrayList<String> notInKeyList = selectNotInKeyTableXRULES_PRVLG_DOC(privilegeProjectCodeDocList, prvlgPrjCdeId);
		if(!Util.empty(notInKeyList)) {
			OrigPrivilegeProjectCodeKassetDocDAO kassetDocDAO = ORIGDAOFactory.getPrivilegeProjectCodeKassetDocDAO();
			OrigPrivilegeProjectCodeMGMDocDAO mgmDocDAO = ORIGDAOFactory.getPrivilegeProjectCodeMGMDocDAO();
			OrigPrivilegeProjectCodeExceptionDocDAO exceptionDocDAO = ORIGDAOFactory.getPrivilegeProjectCodeExceptionDocDAO();
			OrigPrivilegeProjectCodeTransferDocDAO transferDocDAO = ORIGDAOFactory.getPrivilegeProjectCodeTransferDocDAO();
			OrigPrivilegeProjectCodeTradingDocDAO tradingDocDAO = ORIGDAOFactory.getPrivilegeProjectCodeTradingDocDAO();
			for(String prvlgDocId: notInKeyList) {
				kassetDocDAO.deleteOrigPrivilegeProjectCodeKassetDocM(prvlgDocId, null);
				mgmDocDAO.deleteOrigPrivilegeProjectCodeMGMDocM(prvlgDocId, null);
				exceptionDocDAO.deleteOrigPrivilegeProjectCodeExceptionDocM(prvlgDocId, null);
				transferDocDAO.deleteOrigPrivilegeProjectCodeTransferDocM(prvlgDocId, null);
				tradingDocDAO.deleteOrigPrivilegeProjectCodeTradingDocM(prvlgDocId, null);
			}
		}
		
		deleteNotInKeyTableXRULES_PRVLG_DOC(privilegeProjectCodeDocList, prvlgPrjCdeId);
	}

	private ArrayList<String> selectNotInKeyTableXRULES_PRVLG_DOC(
			ArrayList<PrivilegeProjectCodeDocDataM> privilegeProjectCodeDocList,
			String prvlgPrjCdeId) throws ApplicationException {
		PreparedStatement ps = null;
		ResultSet rs = null;
		Connection conn = null;
		ArrayList<String> notInKeyList = new ArrayList<String>();
		try {
			//conn = Get Connection
			conn = getConnection();
			StringBuffer sql = new StringBuffer("");
			sql.append("SELECT PRVLG_DOC_ID ");
			sql.append(" FROM XRULES_PRVLG_DOC WHERE PRVLG_PRJ_CDE_ID = ? ");
			if (!Util.empty(privilegeProjectCodeDocList)) {
                sql.append(" AND PRVLG_DOC_ID NOT IN ( ");

                for (PrivilegeProjectCodeDocDataM prvProjCodeDocM: privilegeProjectCodeDocList) {
                    sql.append(" '" + prvProjCodeDocM.getPrvlgDocId() + "' , ");
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
			ps.setString(1, prvlgPrjCdeId);

			rs = ps.executeQuery();

			while (rs.next()) {
				String notInKeyId = rs.getString("PRVLG_DOC_ID");
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

	private void deleteNotInKeyTableXRULES_PRVLG_DOC(
			ArrayList<PrivilegeProjectCodeDocDataM> privilegeProjectCodeDocList,
			String prvlgPrjCdeId) throws ApplicationException {
		PreparedStatement ps = null;
		Connection conn = null;
        try {
            // conn = Get Connection
            conn = getConnection();

            StringBuffer sql = new StringBuffer("");
            sql.append("DELETE FROM XRULES_PRVLG_DOC ");
            sql.append(" WHERE PRVLG_PRJ_CDE_ID = ? ");
            
            if (!Util.empty(privilegeProjectCodeDocList)) {
                sql.append(" AND PRVLG_DOC_ID NOT IN ( ");

                for (PrivilegeProjectCodeDocDataM projCodeDocM: privilegeProjectCodeDocList) {
                    sql.append(" '" + projCodeDocM.getPrvlgDocId() + "' , ");
                }

                if (sql.toString().trim().endsWith(",")) {
                    sql.delete(sql.toString().lastIndexOf(","), sql.toString().length());
                }

                sql.append(" ) ");
            }

            String dSql = String.valueOf(sql);
            ps = conn.prepareStatement(dSql);
            ps.setString(1, prvlgPrjCdeId);
            
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
