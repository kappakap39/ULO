package com.eaf.orig.ulo.pl.app.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.util.Vector;
import org.apache.log4j.Logger;
import com.eaf.orig.profile.model.UserDetailM;
import com.eaf.orig.shared.dao.OrigObjectDAO;
import com.eaf.orig.shared.util.OrigUtil;
import com.eaf.orig.ulo.pl.app.dao.exception.PLOrigApplicationException;
import com.eaf.orig.ulo.pl.model.app.PLNCBDocDataM;

public class PLOrigNCBDocumentHistoryDAOImpl extends OrigObjectDAO implements PLOrigNCBDocumentHistoryDAO {

	private Logger log = Logger.getLogger(this.getClass());
	
	@Override
	public void SaveNCB_DOCUMENT_HISTORY(Vector<PLNCBDocDataM> ncbDocVect, UserDetailM userM, String consentRefNo) throws PLOrigApplicationException {
		
		if(!OrigUtil.isEmptyVector(ncbDocVect)){
			for (int i = 0; i < ncbDocVect.size(); i++) {
				PLNCBDocDataM ncbDocM = ncbDocVect.get(i);
				insertTable_ORIG_NCB_DOCUMENT_HISTORY(ncbDocM, userM, consentRefNo);
			}
		}
	}
	
	private void insertTable_ORIG_NCB_DOCUMENT_HISTORY(PLNCBDocDataM ncbDocM, UserDetailM userM, String consentRefNo) throws PLOrigApplicationException {
		
		PreparedStatement ps = null;
		Connection conn = null;
		try {
			
			conn = getConnection();
			StringBuilder sql = new StringBuilder("");
			sql.append("INSERT INTO ORIG_NCB_DOCUMENT_HISTORY ");
			sql.append("( PERSONAL_ID, DOCUMENT_CODE, IMG_ID, CONSENT_REF_NO, CREATE_DATE, ");
			sql.append("CREATE_BY, UPDATE_DATE, UPDATE_BY )");
			sql.append(" VALUES(?,?,?,?,SYSDATE, ?,SYSDATE,? ) ");
			String dSql = String.valueOf(sql);
			log.debug("Sql=" + dSql);
			ps = conn.prepareStatement(dSql);
			ps.setString(1, ncbDocM.getPersonalID());
			ps.setString(2, ncbDocM.getDocCode());
			ps.setString(3, ncbDocM.getImgID());
			ps.setString(4, consentRefNo);
			
			ps.setString(5, userM.getUserName());
			ps.setString(6, userM.getUserName());
			
			ps.executeUpdate();
			
		} catch (Exception e) {
			log.fatal(e.getLocalizedMessage());
			throw new PLOrigApplicationException(e.getMessage());
		} finally {
			try {
				closeConnection(conn, ps);
			} catch (Exception e) {
				log.fatal(e.getLocalizedMessage());
				throw new PLOrigApplicationException(e.getMessage());
			}
		}
		
	}

}
