package com.eaf.orig.ulo.pl.app.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import org.apache.log4j.Logger;
import com.eaf.orig.shared.dao.OrigObjectDAO;
import com.eaf.orig.ulo.pl.app.dao.exception.PLWFDAOException;

public class PLWFLoadCentralJobAmountDAOImpl extends OrigObjectDAO implements PLWFLoadCentralJobAmountDAO{	
	
	static Logger log = Logger.getLogger(PLWFLoadCentralJobAmountDAOImpl.class);
	
	public int loadCentralJob(String ATID, String ATID2) throws PLWFDAOException {
		PreparedStatement ps = null;
		ResultSet rs = null;
		int countJob = 0;
		Connection conn = null;
		try {
			conn = getConnection();
			
			StringBuilder sql = new StringBuilder("");			
			sql.append("SELECT COUNT(JOB_ID) from WF_WORK_QUEUE WHERE ATID IN (?,?) AND OWNER is null");
			
			String dSql = String.valueOf(sql);
			ps = conn.prepareStatement(dSql);
			ps.setString(1, ATID);
			ps.setString(2, ATID2);

			rs = ps.executeQuery();
			if(rs.next()){
				countJob = rs.getInt(1);
			}			
			return countJob;
		}catch(Exception e){
			log.fatal(e.getLocalizedMessage());
			throw new PLWFDAOException(e.getMessage());
		}finally{
			try{
				closeConnection(conn, rs, ps);
			}catch(Exception e){
				log.fatal(e.getLocalizedMessage());
				throw new PLWFDAOException(e.getMessage());
			}
		}
	}

}
