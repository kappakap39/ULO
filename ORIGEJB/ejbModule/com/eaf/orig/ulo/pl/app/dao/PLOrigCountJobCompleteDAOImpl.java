package com.eaf.orig.ulo.pl.app.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import org.apache.log4j.Logger;
import com.eaf.orig.shared.dao.OrigObjectDAO;
import com.eaf.orig.ulo.pl.app.dao.exception.PLOrigApplicationException;

public class PLOrigCountJobCompleteDAOImpl extends OrigObjectDAO implements PLOrigCountJobCompleteDAO{
	
	static Logger log = Logger.getLogger(PLOrigCountJobCompleteDAOImpl.class);
	
	@Override
	public int countJobComplete(String userName) throws PLOrigApplicationException{		
		PreparedStatement ps = null;
		ResultSet rs = null;
		int count = 0;
		Connection conn = null;
		try{
			conn = getConnection();
			
			StringBuilder SQL = new StringBuilder("");			
			SQL.append(" SELECT ");
			SQL.append("     NVL(COUNT(WF_HIS.JOB_ID),0) OUTPUT ");
			SQL.append(" FROM ");
			SQL.append("     WF_INSTANT_HISTORY WF_HIS, ");
			SQL.append("     ( ");
			SQL.append("         SELECT ");
			SQL.append("             MAX(WF.SEQ) SEQ, ");
			SQL.append("             JOB_ID JOB_ID ");
			SQL.append("         FROM ");
			SQL.append("             WF_INSTANT_HISTORY WF ");
			SQL.append("         WHERE ");
			SQL.append("             WF.COMPLETE_BY = ? AND COMPLETE_DATE BETWEEN TRUNC(SYSDATE) AND TRUNC(SYSDATE)+.99999 ");
			SQL.append("         GROUP BY ");
			SQL.append("             WF.JOB_ID ");
			SQL.append("     ) ");
			SQL.append("     TMP ");
			SQL.append(" WHERE ");
			SQL.append("     ( ");
			SQL.append("         WF_HIS.ATID LIKE 'STI%' ");
			SQL.append("      OR WF_HIS.ATID IN ('STC0402','STC0403','STC0411') ");
			SQL.append("     ) ");
			SQL.append(" AND WF_HIS.COMPLETE_DATE BETWEEN TRUNC(SYSDATE) AND TRUNC(SYSDATE)+.99999 ");
			SQL.append(" AND WF_HIS.SEQ = TMP.SEQ ");
			SQL.append(" AND WF_HIS.JOB_ID = TMP.JOB_ID ");
			SQL.append(" AND WF_HIS.COMPLETE_BY = ? ");
			
			String dSql = String.valueOf(SQL);
			log.debug("Sql=" + dSql);
			
			ps = conn.prepareStatement(dSql);
			
			int index = 1;
			ps.setString(index++, userName);
			ps.setString(index++, userName);
			
			rs = ps.executeQuery();
			if (rs.next()){
				count = rs.getInt(1);
			}
			
		}catch(Exception e){
			log.fatal(e.getLocalizedMessage());
			throw new PLOrigApplicationException(e.getMessage());
		}finally{
			try{
				closeConnection(conn, rs, ps);
			}catch(Exception e){
				log.fatal(e.getLocalizedMessage());
				throw new PLOrigApplicationException(e.getMessage());
			}
		}
		return count;
	}

}
