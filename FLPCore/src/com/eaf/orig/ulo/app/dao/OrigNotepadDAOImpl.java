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
import com.eaf.orig.ulo.model.app.NotePadDataM;

public class OrigNotepadDAOImpl extends OrigObjectDAO implements OrigNotepadDAO {
	public OrigNotepadDAOImpl(String userId){
		this.userId = userId;
	}
	public OrigNotepadDAOImpl(){
		
	}
	private String userId = "";
	@Override
	public void createOrigNotePadM(NotePadDataM notepadM)
			throws ApplicationException {
		logger.debug("notepadM.getNotePadId() :"+notepadM.getNotePadId());
	    if(Util.empty(notepadM.getNotePadId())){
			String notepadId = GenerateUnique.generate(OrigConstant.SeqNames.ORIG_NOTE_PAD_DATA_PK);
			notepadM.setNotePadId(notepadId);
			notepadM.setCreateBy(userId);
	    }
	    notepadM.setUpdateBy(userId);
		createTableORIG_NOTE_PAD_DATA(notepadM);
	}

	private void createTableORIG_NOTE_PAD_DATA(NotePadDataM notepadM) 
			throws ApplicationException {
		PreparedStatement ps = null;
		Connection conn = null;
		try {
			//conn = Get Connection
			conn = getConnection();
			StringBuffer sql = new StringBuffer("");
			sql.append("INSERT INTO ORIG_NOTE_PAD_DATA ");
			sql.append("( NOTE_PAD_ID, APPLICATION_GROUP_ID, NOTE_PAD_DESC, ");
			sql.append("CREATE_DATE, CREATE_BY, UPDATE_DATE, UPDATE_BY, STATUS, USER_ROLE ) ");

			sql.append(" VALUES(?,?,?,  ?,?,?,?,?,?)");
			String dSql = String.valueOf(sql);
			logger.debug("Sql=" + dSql);
			ps = conn.prepareStatement(dSql);
			int cnt = 1;
			ps.setString(cnt++, notepadM.getNotePadId());
			ps.setString(cnt++, notepadM.getApplicationGroupId());
			ps.setString(cnt++, notepadM.getNotePadDesc());
			
			ps.setTimestamp(cnt++, ApplicationDate.getTimestamp());
			ps.setString(cnt++, notepadM.getCreateBy());
			ps.setTimestamp(cnt++, ApplicationDate.getTimestamp());
			ps.setString(cnt++, notepadM.getUpdateBy());
			ps.setString(cnt++, notepadM.getStatus());
			ps.setString(cnt++, notepadM.getUserRole());
			
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
	public void deleteOrigNotePadM(String applicationGroupId, String notePadId)
			throws ApplicationException {
		deleteTableORIG_NOTE_PAD_DATA(applicationGroupId, notePadId);
	}

	private void deleteTableORIG_NOTE_PAD_DATA(String applicationGroupId, String notePadId) 
			throws ApplicationException {
		PreparedStatement ps = null;
		Connection conn = null;
		try {
			//conn = Get Connection
			conn = getConnection();
			StringBuffer sql = new StringBuffer("");
			sql.append(" DELETE ORIG_NOTE_PAD_DATA ");
			sql.append(" WHERE APPLICATION_GROUP_ID = ? ");
			if(notePadId != null && !notePadId.isEmpty()) {
				sql.append(" AND NOTE_PAD_ID = ? ");
			}
			String dSql = String.valueOf(sql);
			logger.debug("Sql=" + dSql);
			ps = conn.prepareStatement(dSql);
			ps.setString(1, applicationGroupId);
			if(notePadId != null && !notePadId.isEmpty()) {
				ps.setString(2, notePadId);
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
	public ArrayList<NotePadDataM> loadOrigNotePadM(String applicationGroupId,
			Connection conn) throws ApplicationException {
		return selectTableORIG_NOTE_PAD_DATA(applicationGroupId, conn);
	}
	@Override
	public ArrayList<NotePadDataM> loadOrigNotePadM(String applicationGroupId)
			throws ApplicationException {
		Connection conn = null;
		try{
			conn = getConnection();
			return selectTableORIG_NOTE_PAD_DATA(applicationGroupId,conn);
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

	private ArrayList<NotePadDataM> selectTableORIG_NOTE_PAD_DATA(
			String applicationGroupId,Connection conn) throws ApplicationException {
		PreparedStatement ps = null;
		ResultSet rs = null;
		ArrayList<NotePadDataM> notepadList = new ArrayList<NotePadDataM>();
		try {
			StringBuffer sql = new StringBuffer("");
			sql.append("SELECT NOTE_PAD_ID, APPLICATION_GROUP_ID, NOTE_PAD_DESC, ");
			sql.append(" CREATE_DATE, CREATE_BY, UPDATE_DATE, UPDATE_BY, STATUS, USER_ROLE ");
			sql.append(" FROM ORIG_NOTE_PAD_DATA WHERE APPLICATION_GROUP_ID = ? ");
			//Fix incident 916985 ORDER BY Comment popup
			sql.append(" ORDER BY CREATE_DATE ASC ");
			String dSql = String.valueOf(sql);
			logger.debug("Sql=" + dSql);
			ps = conn.prepareStatement(dSql);
			rs = null;
			ps.setString(1, applicationGroupId);

			rs = ps.executeQuery();

			while(rs.next()) {
				NotePadDataM notepadM = new NotePadDataM();
				notepadM.setNotePadId(rs.getString("NOTE_PAD_ID"));
				notepadM.setApplicationGroupId(rs.getString("APPLICATION_GROUP_ID"));
				notepadM.setNotePadDesc(rs.getString("NOTE_PAD_DESC"));
				
				notepadM.setCreateBy(rs.getString("CREATE_BY"));
				notepadM.setCreateDate(rs.getTimestamp("CREATE_DATE"));
				notepadM.setUpdateBy(rs.getString("UPDATE_BY"));
				notepadM.setUpdateDate(rs.getTimestamp("UPDATE_DATE"));
				notepadM.setStatus(rs.getString("STATUS"));
				notepadM.setUserRole(rs.getString("USER_ROLE"));
				notepadList.add(notepadM);
			}

			return notepadList;
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
	public void saveUpdateOrigNotePadM(NotePadDataM notepadM)
			throws ApplicationException {
		int returnRows = 0;
		notepadM.setUpdateBy(userId);
		returnRows = updateTableORIG_NOTE_PAD_DATA(notepadM);
		if (returnRows == 0) {
			notepadM.setCreateBy(userId);
			createOrigNotePadM(notepadM);
		}
	}

	private int updateTableORIG_NOTE_PAD_DATA(NotePadDataM notepadM) throws ApplicationException {
		int returnRows = 0;
		PreparedStatement ps = null;
		Connection conn = null;
		try {
			//conn = Get Connection
			conn = getConnection();
			StringBuffer sql = new StringBuffer("");
			sql.append("UPDATE ORIG_NOTE_PAD_DATA ");
			sql.append(" SET NOTE_PAD_DESC = ?, UPDATE_DATE = ?, UPDATE_BY = ?, STATUS=?, USER_ROLE=? ");			
			sql.append(" WHERE NOTE_PAD_ID = ? AND APPLICATION_GROUP_ID = ? ");
			String dSql = String.valueOf(sql);
			logger.debug("Sql=" + dSql);
			ps = conn.prepareStatement(dSql);
			int cnt = 1;
			ps.setString(cnt++, notepadM.getNotePadDesc());
			ps.setTimestamp(cnt++, ApplicationDate.getTimestamp());
			ps.setString(cnt++, notepadM.getUpdateBy());
			ps.setString(cnt++, notepadM.getStatus());
			ps.setString(cnt++, notepadM.getUserRole());
			// WHERE CLAUSE
			ps.setString(cnt++, notepadM.getNotePadId());		
			ps.setString(cnt++, notepadM.getApplicationGroupId());			
			
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
	public void deleteNotInKeyNotePad(ArrayList<NotePadDataM> notepadMList,
			String applicationGroupId, Connection conn)
			throws ApplicationException {
		deleteNotInKeyTableORIG_NOTE_PAD_DATA(notepadMList, applicationGroupId, conn);
	}
	@Override
	public void deleteNotInKeyNotePad(ArrayList<NotePadDataM> notepadMList,
			String applicationGroupId) throws ApplicationException {
		Connection conn = null;
		try{
			conn = getConnection();
			deleteNotInKeyNotePad(notepadMList,applicationGroupId,conn);
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

	private void deleteNotInKeyTableORIG_NOTE_PAD_DATA(
			ArrayList<NotePadDataM> notepadMList, String applicationGroupId,Connection conn) 
					throws ApplicationException {
		PreparedStatement ps = null;
//		Connection conn = null;
		try {
            // conn = Get Connection
//            conn = getConnection();

            StringBuffer sql = new StringBuffer("");
            sql.append("DELETE FROM ORIG_NOTE_PAD_DATA ");
            sql.append(" WHERE APPLICATION_GROUP_ID = ? ");
            
            if (notepadMList != null && !notepadMList.isEmpty()) {
                sql.append(" AND NOTE_PAD_ID NOT IN ( ");
                for (NotePadDataM notepadM: notepadMList) {
                	logger.debug("notepadM.getNotePadId() = "+notepadM.getNotePadId());
                    sql.append(" '" + notepadM.getNotePadId() + "', ");
                }
                
                if (sql.toString().trim().endsWith(",")) {
                    sql.delete(sql.toString().lastIndexOf(","), sql.toString().length());
                }

                sql.append(" ) ");
            }

            String dSql = String.valueOf(sql);
            ps = conn.prepareStatement(dSql);
            ps.setString(1, applicationGroupId);
            
            ps.executeUpdate();
            ps.close();

        } catch (Exception e) {
        	logger.error("ERROR >> ",e);
			throw new ApplicationException(e.getMessage());
		} finally {
			try {
//				closeConnection(conn, ps);
				closeConnection(ps);
			} catch (Exception e) {
				logger.error("ERROR >> ",e);
				throw new ApplicationException(e.getMessage());
			}
		}
	}

}
