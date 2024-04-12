package com.eaf.orig.ulo.app.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

import com.eaf.core.ulo.common.exception.EngineException;
import com.eaf.core.ulo.common.properties.SystemConstant;
import com.eaf.orig.shared.dao.OrigObjectDAO;
import com.eaf.orig.ulo.model.app.ApplicationGroupDataM;
import com.eaf.orig.ulo.model.app.PersonalInfoDataM;
import com.eaf.orig.ulo.model.comparesignature.CompareSignatureDataM;

public class CompareSignatureDAOImpl extends OrigObjectDAO implements CompareSignatureDAO {
	private String PERSONAL_TYPE = SystemConstant.getConstant("PERSONAL_TYPE_APPLICANT");
	@Override
	public void selectOldApplicationInfo(CompareSignatureDataM compareSignatureDataM,ApplicationGroupDataM applicationGroup) throws Exception {
		Connection conn = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		String CIDTYPE_IDCARD = SystemConstant.getConstant("CIDTYPE_IDCARD");
		try{
			conn = getConnection();
			StringBuilder sql = new StringBuilder();
			sql.append("  SELECT G.APPLICATION_GROUP_NO ");
			sql.append("  FROM ORIG_PERSONAL_INFO P");
			sql.append("  JOIN ORIG_PERSONAL_INFO P_NEW ON P_NEW.IDNO = P.IDNO ");
			sql.append("  JOIN ORIG_APPLICATION_GROUP G ON G.APPLICATION_GROUP_ID = P.APPLICATION_GROUP_ID");
			sql.append("  WHERE P_NEW.APPLICATION_GROUP_ID = ?");
			sql.append("  AND P.APPLICATION_GROUP_ID <> P_NEW.APPLICATION_GROUP_ID");
			sql.append("  AND P_NEW.TH_FIRST_NAME = (CASE WHEN P_NEW.CID_TYPE = ? THEN P_NEW.TH_FIRST_NAME ELSE ? END)");
			sql.append("  AND P_NEW.TH_LAST_NAME = (CASE WHEN P_NEW.CID_TYPE = ? THEN P_NEW.TH_LAST_NAME ELSE ? END)");
			sql.append("  AND P_NEW.BIRTH_DATE = (CASE WHEN P_NEW.CID_TYPE = ? THEN TRUNC(P_NEW.BIRTH_DATE) ELSE TRUNC(?) END)");
			sql.append("  ORDER BY G.APPLY_DATE DESC");
			
			logger.debug("sql : "+sql.toString());
			logger.debug("applicationGroup.getApplicationGroupId() : "+applicationGroup.getApplicationGroupId());
			logger.debug("CIDTYPE_IDCARD : "+CIDTYPE_IDCARD);
			PersonalInfoDataM personalInfo = applicationGroup.getPersonalInfo(PERSONAL_TYPE);
			if(null==personalInfo){
				personalInfo = new PersonalInfoDataM();
			}
			ps = conn.prepareStatement(sql.toString());
			int index = 1;
			ps.setString(index++, applicationGroup.getApplicationGroupId());
			ps.setString(index++, CIDTYPE_IDCARD);
			ps.setString(index++, personalInfo.getThFirstName());
			ps.setString(index++, CIDTYPE_IDCARD);
			ps.setString(index++, personalInfo.getThLastName());
			ps.setString(index++, CIDTYPE_IDCARD);
			ps.setDate(index++, personalInfo.getBirthDate());
			rs = ps.executeQuery();		
			if(rs.next()){
				String APPLICATION_GROUP_NO = rs.getString("APPLICATION_GROUP_NO");
				logger.debug("APPLICATION_GROUP_NO>>"+APPLICATION_GROUP_NO);
				compareSignatureDataM.setOldSetID(APPLICATION_GROUP_NO);
			}
		}catch(Exception e){
			logger.fatal("ERROR ",e);
			throw new EngineException(e.getLocalizedMessage());
		}finally{
			try {
				closeConnection(conn, rs, ps);
			} catch (Exception e) {
				logger.fatal("ERROR "+e.getMessage());
			}
		}	
	}

}
