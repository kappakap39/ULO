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
import com.eaf.orig.ulo.model.app.IncomeSourceDataM;

public class OrigIncomeSourceDAOImpl extends OrigObjectDAO implements
		OrigIncomeSourceDAO {
	public OrigIncomeSourceDAOImpl(String userId){
		this.userId = userId;
	}
	public OrigIncomeSourceDAOImpl(){
		
	}
	private String userId = "";
	@Override
	public void createOrigIncomeSourceM(IncomeSourceDataM incomeSourceM)
			throws ApplicationException {
		try {
			//Get Primary Key
		    logger.debug("incomeSourceM.getIncomeSourceId() :"+incomeSourceM.getIncomeSourceId());
		    if(Util.empty(incomeSourceM.getIncomeSourceId())){
				String incomeSourceId =GenerateUnique.generate(OrigConstant.SeqNames.ORIG_INCOME_SOURCE_PK );
				incomeSourceM.setIncomeSourceId(incomeSourceId);
				incomeSourceM.setCreateBy(userId);
		    }
		    incomeSourceM.setUpdateBy(userId);
			createTableORIG_INCOME_SOURCE(incomeSourceM);
		} catch (Exception e) {
			logger.fatal(e.getLocalizedMessage());
			throw new ApplicationException(e.getMessage());
		}
	}

	private void createTableORIG_INCOME_SOURCE(IncomeSourceDataM incomeSourceM) throws ApplicationException {
		PreparedStatement ps = null;
		Connection conn = null;
		try {
			//conn = Get Connection
			conn = getConnection();
			StringBuffer sql = new StringBuffer("");
			sql.append("INSERT INTO ORIG_INCOME_SOURCE ");
			sql.append("( INCOME_SOURCE_ID, PERSONAL_ID, INCOME_SOURCE, ");
			sql.append(" CREATE_DATE, CREATE_BY, UPDATE_DATE, UPDATE_BY ) ");

			sql.append(" VALUES(?,?,?,  ?,?,?,?)");
			String dSql = String.valueOf(sql);
			logger.debug("Sql=" + dSql);
			ps = conn.prepareStatement(dSql);
			int cnt = 1;
			ps.setString(cnt++, incomeSourceM.getIncomeSourceId());
			ps.setString(cnt++, incomeSourceM.getPersonalId());
			ps.setString(cnt++, incomeSourceM.getIncomeSource());
			
			ps.setTimestamp(cnt++, ApplicationDate.getTimestamp());
			ps.setString(cnt++, incomeSourceM.getCreateBy());
			ps.setTimestamp(cnt++, ApplicationDate.getTimestamp());
			ps.setString(cnt++, incomeSourceM.getUpdateBy());
			
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
	public void deleteOrigIncomeSourceM(String personalId, String incomeSourceId)
			throws ApplicationException {
		try {
			deleteTableORIG_INCOME_SOURCE(personalId, incomeSourceId);
		} catch (Exception e) {
			logger.fatal(e.getLocalizedMessage());
			throw new ApplicationException(e.getMessage());
		}
	}

	private void deleteTableORIG_INCOME_SOURCE(String personalId,
			String incomeSourceId) throws ApplicationException {
		PreparedStatement ps = null;
		Connection conn = null;
		try {
			//conn = Get Connection
			conn = getConnection();
			StringBuffer sql = new StringBuffer("");
			sql.append(" DELETE ORIG_INCOME_SOURCE ");
			sql.append(" WHERE PERSONAL_ID = ? ");
			if(!Util.empty(incomeSourceId)) {
				sql.append(" AND INCOME_SOURCE_ID = ? ");
			}
			String dSql = String.valueOf(sql);
			logger.debug("Sql=" + dSql);
			ps = conn.prepareStatement(dSql);
			ps.setString(1, personalId);
			if(!Util.empty(incomeSourceId)) {
				ps.setString(2, incomeSourceId);
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
	public ArrayList<IncomeSourceDataM> loadOrigIncomeSourceM(String personalId)
			throws ApplicationException {
		Connection conn = null;
		try{
			conn = getConnection();
			return selectTableORIG_INCOME_SOURCE(personalId,conn);
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

	@Override
	public ArrayList<IncomeSourceDataM> loadOrigIncomeSourceM(
			String personalId, Connection conn) throws ApplicationException {
		return selectTableORIG_INCOME_SOURCE(personalId, conn);
	}
	
	private ArrayList<IncomeSourceDataM> selectTableORIG_INCOME_SOURCE(
			String personalId,Connection conn) throws ApplicationException {
		PreparedStatement ps = null;
		ResultSet rs = null;
		ArrayList<IncomeSourceDataM> incomeSourceList = new ArrayList<IncomeSourceDataM>();
		try {
			StringBuffer sql = new StringBuffer("");
			sql.append("SELECT INCOME_SOURCE_ID, PERSONAL_ID, INCOME_SOURCE, ");
			sql.append(" CREATE_DATE, CREATE_BY, UPDATE_DATE, UPDATE_BY ");
			sql.append(" FROM ORIG_INCOME_SOURCE WHERE PERSONAL_ID = ? ");
			String dSql = String.valueOf(sql);
			logger.debug("Sql=" + dSql);
			ps = conn.prepareStatement(dSql);
			rs = null;
			ps.setString(1, personalId);

			rs = ps.executeQuery();

			while(rs.next()) {
				IncomeSourceDataM incomeSourceM = new IncomeSourceDataM();
				incomeSourceM.setIncomeSourceId(rs.getString("INCOME_SOURCE_ID"));
				incomeSourceM.setPersonalId(rs.getString("PERSONAL_ID"));
				incomeSourceM.setIncomeSource(rs.getString("INCOME_SOURCE"));
				
				incomeSourceM.setCreateBy(rs.getString("CREATE_BY"));
				incomeSourceM.setCreateDate(rs.getTimestamp("CREATE_DATE"));
				incomeSourceM.setUpdateBy(rs.getString("UPDATE_BY"));
				incomeSourceM.setUpdateDate(rs.getTimestamp("UPDATE_DATE"));
				
				incomeSourceList.add(incomeSourceM);
			}

			return incomeSourceList;
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
	public void saveUpdateOrigIncomeSourceM(IncomeSourceDataM incomeSourceM)
			throws ApplicationException {
		try {
			int returnRows = 0;
			incomeSourceM.setUpdateBy(userId);
			returnRows = updateTableORIG_INCOME_SOURCE(incomeSourceM);
			if (returnRows == 0) {
				incomeSourceM.setCreateBy(userId);
				createOrigIncomeSourceM(incomeSourceM);
			}
		} catch (Exception e) {
			logger.fatal(e.getLocalizedMessage());
			throw new ApplicationException(e.getMessage());
		}
	}

	private int updateTableORIG_INCOME_SOURCE(IncomeSourceDataM incomeSourceM) throws ApplicationException {
		int returnRows = 0;
		PreparedStatement ps = null;
		Connection conn = null;
		try {
			//conn = Get Connection
			conn = getConnection();
			StringBuffer sql = new StringBuffer("");
			sql.append("UPDATE ORIG_INCOME_SOURCE ");
			sql.append(" SET INCOME_SOURCE = ?, UPDATE_DATE = ?, UPDATE_BY = ? ");
			sql.append(" WHERE PERSONAL_ID = ? AND INCOME_SOURCE_ID = ? ");
			String dSql = String.valueOf(sql);
			logger.debug("Sql=" + dSql);
			ps = conn.prepareStatement(dSql);
			int cnt = 1;
			ps.setString(cnt++, incomeSourceM.getIncomeSource());
			ps.setTimestamp(cnt++, ApplicationDate.getTimestamp());
			ps.setString(cnt++, incomeSourceM.getUpdateBy());
			
			// WHERE CLAUSE
			ps.setString(cnt++, incomeSourceM.getPersonalId());
			ps.setString(cnt++, incomeSourceM.getIncomeSourceId());
			
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
	public void deleteNotInKeyIncomeSource(ArrayList<IncomeSourceDataM> incomeSourceList, String personalId)
			throws ApplicationException {
		deleteNotInKeyORIG_INCOME_SOURCE(incomeSourceList, personalId);
	}

	private void deleteNotInKeyORIG_INCOME_SOURCE(
			ArrayList<IncomeSourceDataM> incomeSourceList, String personalId) throws ApplicationException {
		PreparedStatement ps = null;
		Connection conn = null;
		try {
            // conn = Get Connection
            conn = getConnection();

            StringBuffer sql = new StringBuffer("");
            sql.append("DELETE FROM ORIG_INCOME_SOURCE ");
            sql.append(" WHERE PERSONAL_ID = ? ");
            
            if (!Util.empty(incomeSourceList)) {
                sql.append(" AND INCOME_SOURCE_ID NOT IN ( ");
                for (IncomeSourceDataM incomeSourceM: incomeSourceList) {
                	logger.debug("incomeSourceM.getIncomeSourceId() = "+incomeSourceM.getIncomeSourceId());
                    sql.append(" '" +incomeSourceM.getIncomeSourceId() + "', ");
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
