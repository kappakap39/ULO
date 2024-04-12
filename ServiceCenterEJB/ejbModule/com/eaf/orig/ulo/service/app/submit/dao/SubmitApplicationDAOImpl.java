package com.eaf.orig.ulo.service.app.submit.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;

import org.apache.log4j.Logger;

import com.eaf.core.ulo.common.properties.SystemConstant;
import com.eaf.core.ulo.common.util.Util;
import com.eaf.orig.ulo.constant.MConstant;
import com.eaf.service.common.exception.ServiceControlException;
import com.eaf.service.common.master.ObjectDAO;

public class SubmitApplicationDAOImpl extends ObjectDAO implements SubmitApplicationDAO {
	private static  transient Logger logger = Logger.getLogger(SubmitApplicationDAOImpl.class);
	String REASON_TYPE_OTHER = SystemConstant.getConstant("REASON_TYPE_OTHER");
	String REASON_TYPE_CHECK_LIST = SystemConstant.getConstant("REASON_TYPE_CHECK_LIST");
	@Override
	public String getDocumentReasonType(ArrayList<String> reasonIds,String documentCode, boolean fixedZoneFlag) throws ServiceControlException {
		String documentReasonType = REASON_TYPE_OTHER;
		if(Util.empty(reasonIds)){
			return documentReasonType;
		}
		String selectFieldName = (fixedZoneFlag)?"REASON_TYPE_MANDATORY":"REASON_TYPE_OPTIONAL";
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
			throw new ServiceControlException(e.getMessage());
		}finally{
			try{
				closeConnection(conn,rs,ps);
			}catch(Exception e){
				logger.fatal(e.getLocalizedMessage());
				throw new ServiceControlException(e.getMessage());
			}
		}
		return documentReasonType;
	}
	
	
	@Override
	public boolean isContainCheckList(ArrayList<String> reasonIds ,String documentType,String fixedZoneFlag)throws ServiceControlException {
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
			if(MConstant.FLAG_Y.equals(fixedZoneFlag)){
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
			
			sql.append(" AND DOCUMENT_CODE =?");
			/*String COMMA2 ="";
			sql.append(" AND DOCUMENT_CODE IN (");
			for(String documentCode: documents){
				sql.append(COMMA2+"?");
				COMMA2=",";
			}
			sql.append(" )");*/

			
			logger.debug("sql : "+sql);
			logger.debug("REASON_TYPE_CHECK_LIST>>"+REASON_TYPE_CHECK_LIST);
			ps = conn.prepareStatement(sql.toString());
			int index =1;
			ps.setString(index++, REASON_TYPE_CHECK_LIST);
			for(String reasonId : reasonIds){
				logger.debug("reasonId : "+reasonId);
				ps.setString(index++, reasonId);
			}
			
			logger.debug("documentCode : "+documentType);
			ps.setString(index++, documentType);

			rs = ps.executeQuery();
			if(rs.next()){
				count = rs.getInt("COUNT_CHECK_LIST");
			}
		}catch(Exception e){
			logger.fatal("ERROR ",e);
			throw new ServiceControlException(e.getLocalizedMessage());
		}finally{
			try{
				closeConnection(conn, rs, ps);
			}catch(Exception e){
				logger.fatal("ERROR ",e);
			}
		}
		return count>0;
	}
}
