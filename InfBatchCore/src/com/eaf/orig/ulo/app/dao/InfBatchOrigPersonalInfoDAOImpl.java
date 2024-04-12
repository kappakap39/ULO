package com.eaf.orig.ulo.app.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;

import org.apache.log4j.Logger;

import com.eaf.core.ulo.common.dao.InfBatchObjectDAO;
import com.eaf.core.ulo.common.exception.InfBatchException;
import com.eaf.core.ulo.common.util.InfBatchUtil;
import com.eaf.orig.ulo.app.factory.InfBatchOrigDAOFactory;
import com.eaf.orig.ulo.app.model.PersonalInfoDataM;
import com.eaf.orig.ulo.app.model.PersonalRelationDataM;

public class InfBatchOrigPersonalInfoDAOImpl extends InfBatchObjectDAO implements InfBatchOrigPersonalInfoDAO{
	private static transient Logger logger = Logger.getLogger(InfBatchOrigPersonalInfoDAOImpl.class);
	public InfBatchOrigPersonalInfoDAOImpl(){
	
	}
	@Override
	public ArrayList<PersonalInfoDataM> loadOrigPersonalInfo(String applicationGroupId, Connection conn)throws InfBatchException{
		ArrayList<PersonalInfoDataM> personalInfoList = selectTableORIG_PERSONAL_INFO(applicationGroupId,conn);
		if(!InfBatchUtil.empty(personalInfoList)){
			InfBatchOrigPersonalRelationDAO personalRelationDAO = InfBatchOrigDAOFactory.getPersonalRelationDAO();
			for(PersonalInfoDataM personalInfo:personalInfoList){
				ArrayList<PersonalRelationDataM> personalRelationList = personalRelationDAO.loadOrigPersonalRelationM(personalInfo.getPersonalId(), conn);
				if(!InfBatchUtil.empty(personalRelationList)){
					personalInfo.setPersonalRelations(personalRelationList);
				}
			}
		}
		return personalInfoList;
	}
	private ArrayList<PersonalInfoDataM> selectTableORIG_PERSONAL_INFO(String applicationGroupId,Connection conn)throws InfBatchException{
		PreparedStatement ps = null;
		ResultSet rs = null;
		ArrayList<PersonalInfoDataM> personalInfoList = new ArrayList<PersonalInfoDataM>();
		try{
			StringBuffer sql = new StringBuffer("");
				sql.append("	SELECT PERSONAL_ID,IDNO,APPLICATION_GROUP_ID,PERSONAL_TYPE	");
				sql.append("	,TH_TITLE_CODE,TH_FIRST_NAME,TH_LAST_NAME,TH_MID_NAME	");
				sql.append("	,EN_TITLE_CODE,EN_FIRST_NAME,EN_LAST_NAME,EN_MID_NAME	");
				sql.append("	,EMAIL_PRIMARY,MOBILE_NO,TH_TITLE_DESC,EN_TITLE_DESC	");
				sql.append("	,M_TITLE_DESC,NATIONALITY	");
				sql.append("	,CREATE_BY,CREATE_DATE,UPDATE_BY,UPDATE_DATE	");
				sql.append("	FROM ORIG_PERSONAL_INFO	");
				sql.append("	WHERE APPLICATION_GROUP_ID = ?	");
			String dSql = String.valueOf(sql);
			logger.debug("Sql=" + dSql);
			ps = conn.prepareStatement(dSql);
				ps.setString(1, applicationGroupId);
			rs = ps.executeQuery();
			while(rs.next()){
				PersonalInfoDataM personalInfo = new PersonalInfoDataM();
					personalInfo.setPersonalId(rs.getString("PERSONAL_ID"));
					personalInfo.setIdno(rs.getString("IDNO"));
					personalInfo.setApplicationGroupId(rs.getString("APPLICATION_GROUP_ID"));
					personalInfo.setPersonalType(rs.getString("PERSONAL_TYPE"));
					personalInfo.setThTitleCode(rs.getString("TH_TITLE_CODE"));
					personalInfo.setThFirstName(rs.getString("TH_FIRST_NAME"));
					personalInfo.setThLastName(rs.getString("TH_LAST_NAME"));
					personalInfo.setThMidName(rs.getString("TH_MID_NAME"));
					personalInfo.setEnTitleCode(rs.getString("EN_TITLE_CODE"));
					personalInfo.setEnFirstName(rs.getString("EN_FIRST_NAME"));
					personalInfo.setEnLastName(rs.getString("EN_LAST_NAME"));
					personalInfo.setEnMidName(rs.getString("EN_MID_NAME"));
					personalInfo.setNationality(rs.getString("NATIONALITY"));
					personalInfo.setEmailPrimary(rs.getString("EMAIL_PRIMARY"));
					personalInfo.setMobileNo(rs.getString("MOBILE_NO"));
					personalInfo.setThTitleDesc(rs.getString("TH_TITLE_DESC"));
					personalInfo.setEnTitleDesc(rs.getString("EN_TITLE_DESC"));
					personalInfo.setmTitleDesc(rs.getString("M_TITLE_DESC"));
					personalInfo.setCreateBy(rs.getString("CREATE_BY"));
					personalInfo.setCreateDate(rs.getTimestamp("CREATE_DATE"));
					personalInfo.setUpdateBy(rs.getString("UPDATE_BY"));
					personalInfo.setUpdateDate(rs.getTimestamp("UPDATE_DATE"));
				personalInfoList.add(personalInfo);
			}
			return personalInfoList;
		}catch(Exception e){
			logger.fatal("ERROR",e);
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
