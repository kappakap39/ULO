package com.eaf.orig.ulo.service.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;

import com.eaf.service.common.exception.ServiceControlException;
import com.eaf.service.common.master.ObjectDAO;

public class XmlDAOImpl extends ObjectDAO implements XmlDAO{
	private static transient Logger logger = Logger.getLogger(XmlDAOImpl.class);
	
	@Override
	public List<String> getPrimaryKeyAppGroup() throws ServiceControlException {
		Connection conn = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		List<String> keyList = new ArrayList<>();
		try{
			conn = getConnection();
			StringBuilder sql = new StringBuilder();
			sql.append(" SELECT DISTINCT FORMATUTIL_ATTRIBUTE(A.COLUMN_NAME) FROM ALL_CONS_COLUMNS A ");
			sql.append(" JOIN ALL_CONSTRAINTS C ON A.OWNER = C.OWNER AND A.CONSTRAINT_NAME = C.CONSTRAINT_NAME ");
			sql.append(" WHERE A.OWNER = 'ORIG_APP' AND REGEXP_LIKE (A.TABLE_NAME,'ORIG|XRULES|NCB|INC') ");
			logger.debug("sql : "+sql);
			ps = conn.prepareStatement(sql.toString());
			rs = ps.executeQuery();
			while(rs.next()){
				keyList.add(rs.getString("FORMATUTIL_ATTRIBUTE(A.COLUMN_NAME)"));
			}
		}catch(Exception e){
			logger.fatal("ERROR ",e);
		}
		return keyList;
	}

}
