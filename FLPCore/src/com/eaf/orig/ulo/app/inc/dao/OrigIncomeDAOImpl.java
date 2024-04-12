package com.eaf.orig.ulo.app.inc.dao;

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
import com.eaf.orig.ulo.model.app.IncomeCategoryDataM;
import com.eaf.orig.ulo.model.app.IncomeInfoDataM;

public class OrigIncomeDAOImpl extends OrigObjectDAO implements OrigIncomeDAO {
	public OrigIncomeDAOImpl(String userId){
		this.userId = userId;
	}
	public OrigIncomeDAOImpl(){
		
	}
	private String userId = "";
	@Override
	public void createOrigIncomeInfoM(IncomeInfoDataM incomeInfoM)
			throws ApplicationException {
		logger.debug("incomeInfoM.getIncomeId() :"+incomeInfoM.getIncomeId());
	    if(Util.empty(incomeInfoM.getIncomeId())){
			String incomeId = GenerateUnique.generate(OrigConstant.SeqNames.INC_INFO_PK);
			incomeInfoM.setIncomeId(incomeId);
			incomeInfoM.setCreateBy(userId);
	    }
	    incomeInfoM.setUpdateBy(userId);
		createTableINC_INFO(incomeInfoM);
		
		ArrayList<IncomeCategoryDataM> allIncomeList = incomeInfoM.getAllIncomes();
		if(!Util.empty(allIncomeList)) {
			OrigIncomeCategoryDAO categoryDAO = ORIGDAOFactory.getIncomeCategoryDAO(userId);
			for(IncomeCategoryDataM incomeCategoryM: allIncomeList) {
				incomeCategoryM.setIncomeId(incomeInfoM.getIncomeId());
				categoryDAO.createOrigIncomeCategory(incomeCategoryM);
			}
		}
		
	}

	private void createTableINC_INFO(IncomeInfoDataM incomeInfoM) 
			throws ApplicationException {
		PreparedStatement ps = null;
		Connection conn = null;
		try {
			//conn = Get Connection
			conn = getConnection();
			StringBuffer sql = new StringBuffer("");
			sql.append("INSERT INTO INC_INFO ");
			sql.append("( INCOME_ID, PERSONAL_ID, INCOME_TYPE, COMPARE_FLAG, REMARK, ");
			sql.append(" CREATE_DATE, CREATE_BY, UPDATE_DATE, UPDATE_BY ) ");

			sql.append(" VALUES(?,?,?,?,?,  ?,?,?,? )");
			String dSql = String.valueOf(sql);
			logger.debug("Sql=" + dSql);
			ps = conn.prepareStatement(dSql);
			int cnt = 1;
			ps.setString(cnt++, incomeInfoM.getIncomeId());		
			ps.setString(cnt++, incomeInfoM.getPersonalId());
			ps.setString(cnt++, incomeInfoM.getIncomeType());
			ps.setString(cnt++, incomeInfoM.getCompareFlag());
			ps.setString(cnt++, incomeInfoM.getRemark());
			
			ps.setTimestamp(cnt++, ApplicationDate.getTimestamp());
			ps.setString(cnt++, incomeInfoM.getCreateBy());
			ps.setTimestamp(cnt++, ApplicationDate.getTimestamp());
			ps.setString(cnt++, incomeInfoM.getUpdateBy());
			
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
	public void deleteOrigIncomeInfoM(String personalId, String incomeId)
			throws ApplicationException {
		
		OrigIncomeCategoryDAO categoryDAO = ORIGDAOFactory.getIncomeCategoryDAO();
		if(!Util.empty(personalId)) {
			ArrayList<IncomeInfoDataM> incomeListM = loadOrigIncomeInfoM(personalId);
			if(!Util.empty(incomeListM)) {
				for(IncomeInfoDataM incomeM: incomeListM) {
					incomeId = incomeM.getIncomeId();
					categoryDAO.deleteOrigIncomeCategory(incomeId, incomeM.getIncomeType());
				}
			}
		} else if(!Util.empty(incomeId)){
			IncomeInfoDataM incomeM = selectTableINC_INCOMEById(incomeId);
			if(!Util.empty(incomeM)){
				categoryDAO.deleteOrigIncomeCategory(incomeId, incomeM.getIncomeType());
			}
		}
		
		deleteTableINC_INFO(personalId, incomeId);
	}

	private IncomeInfoDataM selectTableINC_INCOMEById(String incomeId) throws ApplicationException {
		PreparedStatement ps = null;
		ResultSet rs = null;
		Connection conn = null;
		IncomeInfoDataM incomeM = null;
		try {
			//conn = Get Connection
			conn = getConnection();
			StringBuffer sql = new StringBuffer("");
			sql.append("SELECT INCOME_ID, PERSONAL_ID, INCOME_TYPE, COMPARE_FLAG, REMARK, ");
			sql.append(" CREATE_DATE, CREATE_BY, UPDATE_DATE, UPDATE_BY ");
			sql.append(" FROM INC_INFO WHERE INCOME_ID = ? ");
			String dSql = String.valueOf(sql);
			logger.debug("Sql=" + dSql);
			ps = conn.prepareStatement(dSql);
			rs = null;
			ps.setString(1, incomeId);

			rs = ps.executeQuery();

			while(rs.next()) {
				incomeM = new IncomeInfoDataM();
				incomeM.setIncomeId(rs.getString("INCOME_ID"));		
				incomeM.setPersonalId(rs.getString("PERSONAL_ID"));
				incomeM.setIncomeType(rs.getString("INCOME_TYPE"));	
				incomeM.setCompareFlag(rs.getString("COMPARE_FLAG"));
				incomeM.setRemark(rs.getString("REMARK"));
				
				incomeM.setCreateBy(rs.getString("CREATE_BY"));
				incomeM.setCreateDate(rs.getTimestamp("CREATE_DATE"));
				incomeM.setUpdateBy(rs.getString("UPDATE_BY"));
				incomeM.setUpdateDate(rs.getTimestamp("UPDATE_DATE"));
			}

			return incomeM;
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

	private void deleteTableINC_INFO(String personalId, String incomeId) throws ApplicationException {
		PreparedStatement ps = null;
		Connection conn = null;
		try {
			//conn = Get Connection
			conn = getConnection();
			StringBuffer sql = new StringBuffer("");
			sql.append("DELETE INC_INFO ");
			sql.append(" WHERE PERSONAL_ID = ? ");
			if(incomeId != null && !incomeId.isEmpty()) {
				sql.append(" AND INCOME_ID = ? ");
			}
			String dSql = String.valueOf(sql);
			logger.debug("Sql=" + dSql);
			ps = conn.prepareStatement(dSql);
			ps.setString(1, personalId);
			if(incomeId != null && !incomeId.isEmpty()) {
				ps.setString(2, incomeId);
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
	public ArrayList<IncomeInfoDataM> loadOrigIncomeInfoM(String personalId)
			throws ApplicationException {
		OrigIncomeCategoryDAO categoryDAO = ORIGDAOFactory.getIncomeCategoryDAO();
		ArrayList<IncomeInfoDataM> result = selectTableINC_INFO(personalId);
		
		if(!Util.empty(result)) {
			for(IncomeInfoDataM incomeM : result) {
				String incomeId = incomeM.getIncomeId();
				ArrayList<IncomeCategoryDataM> allIncomeList = categoryDAO.loadOrigIncomeCategory(incomeId, incomeM.getIncomeType());
				if(!Util.empty(allIncomeList)) {
					incomeM.setAllIncomes(allIncomeList);
				}
			}
		}
		return result;
	}
	@Override
	public ArrayList<IncomeInfoDataM> loadOrigIncomeInfoM(String personalId,
			Connection conn) throws ApplicationException {
		OrigIncomeCategoryDAO categoryDAO = ORIGDAOFactory.getIncomeCategoryDAO();
		ArrayList<IncomeInfoDataM> result = selectTableINC_INFO(personalId,conn);
		
		if(!Util.empty(result)) {
			for(IncomeInfoDataM incomeM : result) {
				String incomeId = incomeM.getIncomeId();
				ArrayList<IncomeCategoryDataM> allIncomeList = categoryDAO.loadOrigIncomeCategory(incomeId, incomeM.getIncomeType());
				if(!Util.empty(allIncomeList)) {
					incomeM.setAllIncomes(allIncomeList);
				}
			}
		}
		return result;
	}
	private ArrayList<IncomeInfoDataM> selectTableINC_INFO(String personalId) 
			throws ApplicationException {
		Connection conn = null;
		try{
			conn = getConnection();
			return selectTableINC_INFO(personalId,conn);
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
	private ArrayList<IncomeInfoDataM> selectTableINC_INFO(String personalId,Connection conn) 
			throws ApplicationException {
		PreparedStatement ps = null;
		ResultSet rs = null;
		ArrayList<IncomeInfoDataM> incomeList = new ArrayList<IncomeInfoDataM>();
		try {
			StringBuffer sql = new StringBuffer("");
			sql.append("SELECT INCOME_ID, PERSONAL_ID, INCOME_TYPE, COMPARE_FLAG, REMARK, ");
			sql.append(" CREATE_DATE,CREATE_BY,UPDATE_DATE,UPDATE_BY ");
			sql.append(" FROM INC_INFO WHERE PERSONAL_ID = ? ");
			sql.append(" ORDER BY INCOME_ID ");
			String dSql = String.valueOf(sql);
			logger.debug("Sql=" + dSql);
			ps = conn.prepareStatement(dSql);
			rs = null;
			ps.setString(1, personalId);

			rs = ps.executeQuery();

			while(rs.next()) {
				IncomeInfoDataM incomeM = new IncomeInfoDataM();
				incomeM.setSeq(incomeList.size()+1);
				incomeM.setIncomeId(rs.getString("INCOME_ID"));		
				incomeM.setPersonalId(rs.getString("PERSONAL_ID"));
				incomeM.setIncomeType(rs.getString("INCOME_TYPE"));
				incomeM.setCompareFlag(rs.getString("COMPARE_FLAG"));
				incomeM.setRemark(rs.getString("REMARK"));
				
				incomeM.setCreateBy(rs.getString("CREATE_BY"));
				incomeM.setCreateDate(rs.getTimestamp("CREATE_DATE"));
				incomeM.setUpdateBy(rs.getString("UPDATE_BY"));
				incomeM.setUpdateDate(rs.getTimestamp("UPDATE_DATE"));
				incomeList.add(incomeM);
			}

			return incomeList;
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
	public void saveUpdateOrigIncomeInfoM(IncomeInfoDataM incomeInfoM)
			throws ApplicationException {
		int returnRows = 0;
		incomeInfoM.setUpdateBy(userId);
		returnRows = updateTableINC_INFO(incomeInfoM);
		if (returnRows == 0) {
			logger.debug("New record then can't update record in table INC_INFO then call Insert method");
			incomeInfoM.setCreateBy(userId);
			createOrigIncomeInfoM(incomeInfoM);
		} else {
			OrigIncomeCategoryDAO categoryDAO = ORIGDAOFactory.getIncomeCategoryDAO(userId);
			ArrayList<IncomeCategoryDataM> allIncomeList = incomeInfoM.getAllIncomes();
			if(!Util.empty(allIncomeList)) {
				for(IncomeCategoryDataM incomeCategoryM: allIncomeList) {
					incomeCategoryM.setIncomeId(incomeInfoM.getIncomeId());
					categoryDAO.saveUpdateOrigIncomeCategory(incomeCategoryM);
				}
			}
			categoryDAO.deleteNotInKeyIncomeCategory(incomeInfoM);
		}
	}

	private int updateTableINC_INFO(IncomeInfoDataM incomeInfoM) throws ApplicationException {
		int returnRows = 0;
		PreparedStatement ps = null;
		Connection conn = null;
		try {
			//conn = Get Connection
			conn = getConnection();
			StringBuffer sql = new StringBuffer("");
			sql.append("UPDATE INC_INFO ");
			sql.append(" SET INCOME_TYPE = ?, COMPARE_FLAG = ?, REMARK = ?, ");
			sql.append(" UPDATE_BY = ?, UPDATE_DATE = ? ");			
			sql.append(" WHERE INCOME_ID = ? ");
			String dSql = String.valueOf(sql);
			logger.debug("Sql=" + dSql);
			ps = conn.prepareStatement(dSql);
			int cnt = 1;
			ps.setString(cnt++, incomeInfoM.getIncomeType());
			ps.setString(cnt++, incomeInfoM.getCompareFlag());
			ps.setString(cnt++, incomeInfoM.getRemark());
			
			ps.setString(cnt++, incomeInfoM.getUpdateBy());
			ps.setTimestamp(cnt++, ApplicationDate.getTimestamp());
			// WHERE CLAUSE
			ps.setString(cnt++, incomeInfoM.getIncomeId());
			
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
	public void deleteNotInKeyIncomeInfo(ArrayList<IncomeInfoDataM> incomeInfoList, String personalId)
			throws ApplicationException {
		ArrayList<String> notInKeyIdList = selectNotInKeyTableINC_INFO(incomeInfoList, personalId);
		if(!Util.empty(notInKeyIdList)) {
			OrigIncomeCategoryDAO categoryDAO = ORIGDAOFactory.getIncomeCategoryDAO();
			for(String incomeId : notInKeyIdList) {
				IncomeInfoDataM incomeM = selectTableINC_INCOMEById(incomeId);
				categoryDAO.deleteOrigIncomeCategory(incomeId, incomeM.getIncomeType());
			}
		}
		deleteNotInKeyTableINC_INFO(incomeInfoList, personalId);
	}

	private ArrayList<String> selectNotInKeyTableINC_INFO(
			ArrayList<IncomeInfoDataM> incomeInfoList, String personalId) throws ApplicationException {
		PreparedStatement ps = null;
		Connection conn = null;
		ResultSet rs = null;
		ArrayList<String> idList = new ArrayList<String>();
		try {
            // conn = Get Connection
            conn = getConnection();

            StringBuffer sql = new StringBuffer("");
            sql.append("SELECT INCOME_ID FROM INC_INFO ");
            sql.append(" WHERE PERSONAL_ID = ? ");
            
            if (!Util.empty(incomeInfoList)) {
                sql.append(" AND INCOME_ID NOT IN ( ");

                for (IncomeInfoDataM incomeM: incomeInfoList) {
                    sql.append(" '" + incomeM.getIncomeId()+ "' , ");
                }

                if (sql.toString().trim().endsWith(",")) {
                    sql.delete(sql.toString().lastIndexOf(","), sql.toString().length());
                }

                sql.append(" ) ");
            }

            String dSql = String.valueOf(sql);
            ps = conn.prepareStatement(dSql);
            ps.setString(1, personalId);
            
            rs = ps.executeQuery();
            while(rs.next()) {
            	String incomeId =  rs.getString("INCOME_ID");
            	idList.add(incomeId);
            }

            return idList;
        } catch (Exception e) {
        	logger.error("ERROR >> ",e);
			throw new ApplicationException(e.getMessage());
		} finally {
			try {
				closeConnection(conn, rs, ps);
			} catch (Exception e) {
				logger.error("ERROR >> ",e);
				throw new ApplicationException(e.getMessage());
			}
		}
	}

	private void deleteNotInKeyTableINC_INFO(
			ArrayList<IncomeInfoDataM> incomeInfoList, String personalId) 
					throws ApplicationException {
		PreparedStatement ps = null;
		Connection conn = null;
        try {
            // conn = Get Connection
            conn = getConnection();

            StringBuffer sql = new StringBuffer("");
            sql.append("DELETE FROM INC_INFO ");
            sql.append(" WHERE PERSONAL_ID = ? ");
            
            if (!Util.empty(incomeInfoList)) {
                sql.append(" AND INCOME_ID NOT IN ( ");

                for (IncomeInfoDataM incomeM: incomeInfoList) {
                    sql.append(" '" + incomeM.getIncomeId()+ "' , ");
                }

                if (sql.toString().trim().endsWith(",")) {
                    sql.delete(sql.toString().lastIndexOf(","), sql.toString().length());
                }

                sql.append(" ) ");
            }

            String dSql = String.valueOf(sql);
            ps = conn.prepareStatement(dSql);
            ps.setString(1, personalId);
            
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
