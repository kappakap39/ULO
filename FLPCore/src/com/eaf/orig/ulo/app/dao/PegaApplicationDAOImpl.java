package com.eaf.orig.ulo.app.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;

import org.apache.log4j.Logger;

import com.eaf.core.ulo.common.properties.SystemConstant;
import com.eaf.core.ulo.common.util.Util;
import com.eaf.orig.shared.dao.OrigObjectDAO;
import com.eaf.orig.ulo.app.dao.exception.ApplicationException;

public class PegaApplicationDAOImpl extends OrigObjectDAO implements PegaApplicationDAO{
	private static transient Logger logger = Logger.getLogger(PegaApplicationDAOImpl.class);
	String REASON_TYPE_OTHER = SystemConstant.getConstant("REASON_TYPE_OTHER");
	String REASON_TYPE_CHECK_LIST = SystemConstant.getConstant("REASON_TYPE_CHECK_LIST");
	@Override
	public boolean isCheckListReason(ArrayList<String> reasonIds,String document, boolean isMandatory)throws ApplicationException {
		Connection conn = null;
		ResultSet rs = null;
		PreparedStatement ps = null;
		int count=0;
		try{
			conn = getConnection();
			StringBuilder sql = new StringBuilder();
			sql.append(" SELECT COUNT(1) AS COUNT_CHECK_LIST ");
			sql.append("  FROM MS_DOC_LIST_REASON ");
			sql.append(" WHERE  ");
			if(isMandatory){
				sql.append(" REASON_TYPE_MANDATORY = ?");
			}else{
				sql.append(" REASON_TYPE_OPTIONAL = ?");
			}
			
			String COMMA1 ="";
			sql.append(" AND REASON_ID IN (");
			for(String reasonId : reasonIds){
				sql.append(COMMA1+"?");
				COMMA1=",";
			}
			sql.append(" )");
			
			String COMMA2 ="";
			sql.append(" AND DOCUMENT_CODE =?");

			
			logger.debug("sql : "+sql);
			logger.debug("REASON_TYPE_CHECK_LIST>>"+REASON_TYPE_CHECK_LIST);
			ps = conn.prepareStatement(sql.toString());
			int index =1;
			ps.setString(index++, REASON_TYPE_CHECK_LIST);
			for(String reasonId : reasonIds){
				logger.debug("reasonId : "+reasonId);
				ps.setString(index++, reasonId);
			}
			logger.debug("documentCode : "+document);
			ps.setString(index++, document);
			rs = ps.executeQuery();
			if(rs.next()){
				count = rs.getInt("COUNT_CHECK_LIST");
			}
		}catch(Exception e){
			logger.fatal(e.getLocalizedMessage());
			throw new ApplicationException(e.getMessage());
		}finally{
			try{
				closeConnection(conn,rs,ps);
			}catch(Exception e){
				logger.fatal(e.getLocalizedMessage());
				throw new ApplicationException(e.getMessage());
			}
		}
		return count>0;
	}
	@Override
	public String getDocumentReasonType(ArrayList<String> reasonIds,String documentCode, boolean mandatoryFlag) throws ApplicationException {
		String documentReasonType = REASON_TYPE_OTHER;
		if(Util.empty(reasonIds)){
			return documentReasonType;
		}
		String selectFieldName = (mandatoryFlag)?"REASON_TYPE_MANDATORY":"REASON_TYPE_OPTIONAL";
		logger.debug("selectFieldName : "+selectFieldName);
		Connection conn = null;
		ResultSet rs = null;
		PreparedStatement ps = null;
		try{
			conn = getConnection();
			StringBuilder sql = new StringBuilder();
			sql.append("SELECT "+selectFieldName+" REASON_TYPE ");
			sql.append(" FROM MS_DOC_LIST_REASON WHERE DOCUMENT_CODE = ? ");			
			String COMMA = "";
			sql.append(" AND REASON_ID IN (");
			for(String reasonId : reasonIds){
				sql.append(COMMA+"?");
				COMMA=",";
			}
			sql.append(" )");
			ArrayList<String> reasonTypes = new ArrayList<String>();
			ps = conn.prepareStatement(sql.toString());
			int index = 1;
			ps.setString(index++, documentCode);
			for(String reasonId : reasonIds){
				logger.debug("reasonId : "+reasonId);
				ps.setString(index++, reasonId);
			}
			rs = ps.executeQuery();
			while(rs.next()){
				String reasonType = rs.getString("REASON_TYPE");
				if(!Util.empty(reasonType) && !reasonTypes.contains(reasonType)){
					reasonTypes.add(reasonType);
				}
			}
			if(!Util.empty(reasonTypes)&&reasonTypes.size() == 1){
				documentReasonType = reasonTypes.get(0);
			}else if(!Util.empty(reasonTypes)){
				if(reasonTypes.contains(REASON_TYPE_CHECK_LIST)){
					documentReasonType = REASON_TYPE_CHECK_LIST;
				}
			}
		}catch(Exception e){
			logger.fatal(e.getLocalizedMessage());
			throw new ApplicationException(e.getMessage());
		}finally{
			try{
				closeConnection(conn,rs,ps);
			}catch(Exception e){
				logger.fatal(e.getLocalizedMessage());
				throw new ApplicationException(e.getMessage());
			}
		}
		return documentReasonType;
	}

}
