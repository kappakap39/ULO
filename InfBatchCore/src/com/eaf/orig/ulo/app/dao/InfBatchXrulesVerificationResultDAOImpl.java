package com.eaf.orig.ulo.app.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;

import org.apache.log4j.Logger;

import com.eaf.core.ulo.common.cache.InfBatchProperty;
import com.eaf.core.ulo.common.dao.InfBatchObjectDAO;
import com.eaf.core.ulo.common.exception.InfBatchException;
import com.eaf.core.ulo.common.util.InfBatchUtil;
import com.eaf.orig.ulo.app.factory.InfBatchOrigDAOFactory;
import com.eaf.orig.ulo.app.model.PolicyRulesDataM;
import com.eaf.orig.ulo.app.model.VerificationResultDataM;

public class InfBatchXrulesVerificationResultDAOImpl extends InfBatchObjectDAO implements InfBatchXrulesVerificationResultDAO{
	private static transient Logger logger = Logger.getLogger(InfBatchXrulesVerificationResultDAOImpl.class);
	@Override
	public VerificationResultDataM loadVerificationResult(String refId,String verLevel,Connection conn)throws InfBatchException{
		VerificationResultDataM verificationResult = selectTableXRULES_VERIFICATION_RESULT(refId,verLevel,conn);
		if(!InfBatchUtil.empty(verificationResult)){
			String XRULES_VER_LEVEL_APPLICATION=InfBatchProperty.getInfBatchConfig("XRULES_VER_LEVEL_APPLICATION");
			InfBatchXrulesPolicyRulesDAO policyRuleDAO = InfBatchOrigDAOFactory.getXrulesPolicyRulesDAO();
			if(XRULES_VER_LEVEL_APPLICATION.equals(verLevel)){
				ArrayList<PolicyRulesDataM> policyRulesList = policyRuleDAO.loadPolicyRules(verificationResult.getVerResultId(),conn);
				if(!InfBatchUtil.empty(policyRulesList)){
					verificationResult.setPolicyRules(policyRulesList);
				}
			}
		}
		return verificationResult;
	}
	@Override
	public VerificationResultDataM loadVerificationResult(String refId,String verLevel)throws InfBatchException{
		Connection conn = null;
		try{
			conn = getConnection();
			VerificationResultDataM verificationResult = loadVerificationResult(refId,verLevel,conn);
			return verificationResult;
		}catch(Exception e){
			logger.fatal("ERROR ",e);
			throw new InfBatchException(e);
		}finally{
			try {
				closeConnection(conn);
			}catch(Exception e2){
				logger.fatal("ERROR ",e2);
			}
		}
	}
	private VerificationResultDataM selectTableXRULES_VERIFICATION_RESULT(String refId, String verLevel, Connection conn)throws InfBatchException{
		PreparedStatement ps = null;
		ResultSet rs = null;
		try{
			StringBuffer sql = new StringBuffer("");
				sql.append("	SELECT VER_RESULT_ID,VER_LEVEL,REF_ID,DOC_COMPLETED_FLAG	");
				sql.append("	,CREATE_BY,CREATE_DATE,UPDATE_BY,UPDATE_DATE	");
				sql.append("	FROM XRULES_VERIFICATION_RESULT	");
				sql.append("	WHERE REF_ID=? AND VER_LEVEL=?	");
			String dSql = String.valueOf(sql);
			logger.debug("Sql=" + dSql);
			ps = conn.prepareStatement(dSql);
				ps.setString(1, refId);
				ps.setString(2, verLevel);
			rs = ps.executeQuery();
			if(rs.next()) {
				VerificationResultDataM verResult = new VerificationResultDataM();
					verResult.setVerResultId(rs.getString("VER_RESULT_ID"));
					verResult.setVerLevel(rs.getString("VER_LEVEL"));
					verResult.setRefId(rs.getString("REF_ID"));
					verResult.setDocCompletedFlag(rs.getString("DOC_COMPLETED_FLAG"));
					verResult.setCreateBy(rs.getString("CREATE_BY"));
					verResult.setCreateDate(rs.getTimestamp("CREATE_DATE"));
					verResult.setUpdateBy(rs.getString("UPDATE_BY"));
					verResult.setUpdateDate(rs.getTimestamp("UPDATE_DATE"));
				return verResult;
			}
		}catch(Exception e){
			logger.fatal("ERROR ",e);
		}finally{
			try {
				closeConnection(ps,rs);
			} catch (Exception e) {
				logger.fatal("ERROR ",e);
			}
		}
		return null;
	}

	
}
