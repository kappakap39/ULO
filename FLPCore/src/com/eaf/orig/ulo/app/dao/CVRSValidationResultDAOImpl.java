package com.eaf.orig.ulo.app.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;

import com.eaf.core.ulo.common.util.Util;
import com.eaf.orig.shared.constant.OrigConstant;
import com.eaf.orig.shared.dao.OrigObjectDAO;
import com.eaf.orig.ulo.app.dao.exception.ApplicationException;
import com.eaf.orig.ulo.app.util.GenerateUnique;
import com.eaf.orig.ulo.model.app.CVRSValidationResultDataM;


public class CVRSValidationResultDAOImpl extends OrigObjectDAO implements CVRSValidationResultDAO{
	
	public CVRSValidationResultDAOImpl(){}
	@Override
	public void createCVRSValidationResultTable(CVRSValidationResultDataM cvrsValidationResult)throws ApplicationException {
		try {
			logger.debug("cvrsValidationResult.getResultId() :"+ cvrsValidationResult.getResultId());
			  if(Util.empty(cvrsValidationResult.getResultId())){
					String resultId = GenerateUnique.generate(OrigConstant.SeqNames.CVRS_VALIDATION_RESULT_PK); 
					cvrsValidationResult.setResultId(resultId);
				}
			  createTableCVRS_VALIDATION_RESULT(cvrsValidationResult);
		} catch (Exception e) {
			logger.fatal(e.getLocalizedMessage());
			throw new ApplicationException(e.getMessage());
		}
	}
	private void createTableCVRS_VALIDATION_RESULT(CVRSValidationResultDataM cvrsValidationResult) throws ApplicationException {
		PreparedStatement ps = null;
		Connection conn = null;
		try {
			conn = getConnection();
			StringBuilder sql = new StringBuilder("");
			sql.append("INSERT INTO CVRS_VALIDATION_RESULT ");
			sql.append(" ( RESULT_ID, PERSONAL_ID, FIELD_GROUP, FIELD_ID, FIELD_NAME,");
			sql.append(" ERROR_CODE, ERROR_DESC, CREATE_DATE, CREATE_BY)");
			sql.append(" VALUES(?,?,?,?,?, ?,?,?,?) ");
			String dSql = sql.toString();
			logger.debug("Sql=" + dSql);
			ps = conn.prepareStatement(dSql);
			int cnt = 1;
			ps.setString(cnt++, cvrsValidationResult.getResultId());
			ps.setString(cnt++, cvrsValidationResult.getPersonalId());
			ps.setString(cnt++, cvrsValidationResult.getFieldGroup());
			ps.setString(cnt++, cvrsValidationResult.getFieldId());
			ps.setString(cnt++, cvrsValidationResult.getFieldName());
			
			ps.setString(cnt++, cvrsValidationResult.getErrorCode());		
			ps.setString(cnt++, cvrsValidationResult.getErrorDesc());			
			ps.setDate(cnt++, 	cvrsValidationResult.getCreateDate());
			ps.setString(cnt++, cvrsValidationResult.getCreateBy());
			
			ps.executeUpdate();
		} catch (Exception e) {
			logger.fatal(e.getLocalizedMessage());
			throw new ApplicationException(e.getMessage());
		} finally {
			try {
				closeConnection(conn, ps);
			} catch (Exception e) {
				logger.fatal(e.getLocalizedMessage());
			}
		}
	}
}
