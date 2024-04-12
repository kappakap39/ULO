package com.eaf.orig.shared.key.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

import org.apache.log4j.Logger;

import com.eaf.orig.shared.dao.OrigObjectDAO;
import com.eaf.orig.shared.key.dao.exception.DMUniqueIDGeneratorException;
import com.eaf.orig.shared.service.OrigServiceLocator;

public class DMUniqueIDGeneratorDAOImpl extends OrigObjectDAO implements DMUniqueIDGeneratorDAO {
	static Logger log = Logger.getLogger(DMUniqueIDGeneratorDAOImpl.class);
	@Override
	public String getUniqueBySequence(String seqId)throws DMUniqueIDGeneratorException {
		return getUniqueBySequence(seqId,OrigServiceLocator.ORIG_DB);
	}	
	@Override
	public String getUniqueBySequence(String seqId,int dbType) throws DMUniqueIDGeneratorException {
		String uniqueId = null;
		Connection conn = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		try{
			conn = getConnection(dbType);
			StringBuilder SQL = new StringBuilder("");	
			SQL.append("SELECT "+seqId+".NEXTVAL KEY_ID FROM DUAL");
			logger.debug("SQL "+SQL);			
			ps = conn.prepareStatement(SQL.toString());	
			rs = ps.executeQuery();
			if(rs.next()){
				uniqueId = rs.getString("KEY_ID");
			}
		}catch(Exception e) {
			logger.fatal("ERROR",e);
			throw new DMUniqueIDGeneratorException(e.getLocalizedMessage());
		}finally{
			try{
				closeConnection(conn, rs, ps);
			}catch(Exception e){
				logger.fatal("ERROR",e);
				throw new DMUniqueIDGeneratorException(e.getLocalizedMessage());
			}
		}
		return uniqueId;
	}
	
}