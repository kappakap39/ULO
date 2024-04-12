package com.eaf.orig.ulo.app.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;

import org.apache.log4j.Logger;

import com.eaf.core.ulo.common.dao.InfBatchObjectDAO;
import com.eaf.core.ulo.common.exception.InfBatchException;
import  com.eaf.orig.ulo.app.model.PersonalRelationDataM;

public class InfBatchOrigPersonalRelationDAOImpl extends InfBatchObjectDAO implements InfBatchOrigPersonalRelationDAO{
	private static transient Logger logger = Logger.getLogger(InfBatchOrigPersonalRelationDAOImpl.class);
	@Override
	public ArrayList<PersonalRelationDataM> loadOrigPersonalRelationM(String personalId, Connection conn)throws InfBatchException{
		return selectTableORIG_PERSONAL_RELATION(personalId, conn);
	}
	private ArrayList<PersonalRelationDataM> selectTableORIG_PERSONAL_RELATION(String personalId,Connection conn) throws InfBatchException{
		PreparedStatement ps = null;
		ResultSet rs = null;
		ArrayList<PersonalRelationDataM> personalRelationList = new ArrayList<PersonalRelationDataM>();
		try{
			StringBuffer sql = new StringBuffer("");
				sql.append("SELECT PERSONAL_ID, REF_ID, PERSONAL_TYPE, RELATION_LEVEL, ");
				sql.append(" CREATE_DATE, CREATE_BY, UPDATE_DATE, UPDATE_BY ");
				sql.append(" FROM ORIG_PERSONAL_RELATION WHERE PERSONAL_ID = ? ");
			String dSql = String.valueOf(sql);
			logger.debug("Sql : " + dSql);
			logger.debug("personalId : "+personalId);
			ps = conn.prepareStatement(dSql);
			ps.setString(1, personalId);
			rs = ps.executeQuery();
			while(rs.next()){
				PersonalRelationDataM personalRelationM = new PersonalRelationDataM();
					personalRelationM.setPersonalId(rs.getString("PERSONAL_ID"));
					personalRelationM.setRefId(rs.getString("REF_ID"));
					personalRelationM.setPersonalType(rs.getString("PERSONAL_TYPE"));
					personalRelationM.setRelationLevel(rs.getString("RELATION_LEVEL"));
					personalRelationM.setCreateBy(rs.getString("CREATE_BY"));
					personalRelationM.setCreateDate(rs.getTimestamp("CREATE_DATE"));
					personalRelationM.setUpdateBy(rs.getString("UPDATE_BY"));
					personalRelationM.setUpdateDate(rs.getTimestamp("UPDATE_DATE"));
				personalRelationList.add(personalRelationM);
			}
			return personalRelationList;
		}catch(Exception e){
			logger.fatal(e.getLocalizedMessage());
			throw new InfBatchException(e);
		}finally{
			try {
				closeConnection(ps,rs);
			} catch (Exception e) {
				logger.fatal(e.getLocalizedMessage());
			}
		}
	}
}
