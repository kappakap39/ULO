package com.eaf.orig.ulo.pl.app.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import org.apache.log4j.Logger;
import com.eaf.orig.shared.dao.OrigObjectDAO;
import com.eaf.orig.ulo.pl.app.dao.exception.PLOrigApplicationException;
import com.eaf.orig.ulo.pl.model.app.EnquiryLogDataM;

public class OrigEnquiryLogDAOImpl extends OrigObjectDAO implements OrigEnquiryLogDAO {

	private Logger log = Logger.getLogger(this.getClass());
	
	@Override
	public void insertTable_OrigEnquiryLog(EnquiryLogDataM enqLogM) throws PLOrigApplicationException {

		PreparedStatement ps = null;
		Connection conn = null;
		try {
			
			conn = getConnection();
			StringBuilder sql = new StringBuilder("");
			sql.append("insert into ORIG_ENQUIRY_LOG el ");
			sql.append("(el.APPLICATION_RECORD_ID, el.ENQUIRY_DATE, el.USERNAME) ");
			sql.append(" VALUES (?,SYSDATE,? ) ");
			String dSql = String.valueOf(sql);
//			log.debug("Sql=" + dSql);
			ps = conn.prepareStatement(dSql);
			int index = 1;
			
			ps.setString(index++, enqLogM.getAppRecId());
			ps.setString(index++, enqLogM.getUserName());
			
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
