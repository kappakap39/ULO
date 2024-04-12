package com.eaf.orig.shared.dao.utility;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.Vector;
import org.apache.log4j.Logger;
import com.eaf.orig.shared.constant.OrigConstant;
import com.eaf.orig.shared.dao.OrigObjectDAO;
import com.eaf.orig.ulo.pl.app.dao.exception.PLOrigApplicationException;
import com.eaf.orig.ulo.pl.constant.WorkflowConstant;
import com.eaf.orig.ulo.pl.model.app.PLInboxUnBlockDataM;

public class PLOrigUnBlockDAOImpl extends OrigObjectDAO implements PLOrigUnBlockDAO {
	
	static Logger log = Logger.getLogger(PLOrigUnBlockDAOImpl.class);

	@Override
	public Vector<PLInboxUnBlockDataM> loadSubTableUnBlock(String IDNO, String role, String userName) throws PLOrigApplicationException {
		
		PreparedStatement ps = null;
		ResultSet rs = null;

		Vector<PLInboxUnBlockDataM> blockVect = new Vector<PLInboxUnBlockDataM>();
		Connection conn = null;
		try {
			conn = getConnection();
			
			StringBuilder SQL = new StringBuilder("");			
			SQL.append(" SELECT ");
			SQL.append("     A.UPDATE_DATE UPDATE_DATE, ");
			SQL.append("     A.APPLICATION_RECORD_ID APPLICATION_RECORD_ID, ");
			SQL.append("     A.APPLICATION_NO APPLICATION_NO, ");
			SQL.append("     Q.APP_STATUS APPLICATION_STATUS, ");
			SQL.append("     P.TH_FIRST_NAME TH_FIRST_NAME, ");
			SQL.append("     P.TH_LAST_NAME TH_LAST_NAME, ");
			SQL.append("     (SELECT MP.PRODUCT_DESC FROM MS_PRODUCT MP WHERE MP.PRODUCT_CODE = B.PRODUCT_ID AND ROWNUM = 1) PRODUCT, ");
			SQL.append("     A.APPLICATION_DATE APPLICATION_DATE, ");
			SQL.append("     SUBSTR(WORKFLOW_SEARCH.GETREMAINAPPLICATIONTIME (I.FIELD_01, T.ACTIVITY_TYPE, I.APPLICATION_DATE, SYSDATE, I.UPDATE_DATE), 3, 9) REMAIN_APP_TIME, ");
			SQL.append("     PKA_UNLOCK.GETOWNERFROMHISTORY(Q.JOB_ID) OWNER, ");
			SQL.append("     Q.ATID JOB_STATE, ");
			SQL.append("     T.ACTIVITY_TYPE ACTIVITY_TYPE, ");
			SQL.append("     (SELECT DISPLAY_NAME FROM LIST_BOX_MASTER LBM WHERE LBM.FIELD_ID = '61' AND LBM.CHOICE_NO = A.PRIORITY AND ROWNUM =1) PRIORITY ");
			SQL.append(" FROM ");
			SQL.append("     ORIG_APPLICATION A, ");
			SQL.append("     ORIG_PERSONAL_INFO P, ");
			SQL.append("     BUSINESS_CLASS B, ");
			SQL.append("     WF_JOBID_MAPPING J, ");
			SQL.append("     WF_INSTANT I, ");
			SQL.append("     WF_WORK_QUEUE Q, ");
			SQL.append("     WF_ACTIVITY_TEMPLATE T ");
			SQL.append(" WHERE ");
			SQL.append("     A.APPLICATION_RECORD_ID = P.APPLICATION_RECORD_ID ");
			SQL.append(" AND A.BUSINESS_CLASS_ID = B.BUS_CLASS_ID ");
			SQL.append(" AND A.BLOCK_FLAG = ? ");
			SQL.append(" AND P.IDNO = ? ");
			SQL.append(" AND P.PERSONAL_TYPE = ? ");
			SQL.append(" AND A.APPLICATION_RECORD_ID = J.APPLICATION_RECORD_ID ");
			SQL.append(" AND J.JOB_STATUS = ? ");
			SQL.append(" AND J.JOB_ID = I.JOB_ID ");
			SQL.append(" AND J.JOB_ID = Q.JOB_ID ");
			SQL.append(" AND Q.ATID = T.ATID ");
			SQL.append(" ORDER BY A.UPDATE_DATE,A.APPLICATION_DATE ");
			
			String dSql = String.valueOf(SQL);
			
//			log.debug("Sql=" + dSql);
			
			ps = conn.prepareStatement(dSql);
			
			int index = 1;			
			ps.setString(index++, OrigConstant.BLOCK_FLAG);
			ps.setString(index++, IDNO);
			ps.setString(index++, OrigConstant.PERSONAL_TYPE_APPLICANT);
			ps.setString(index++, WorkflowConstant.JobStatus.ACTIVE);
			
			rs = ps.executeQuery();
			
			PLInboxUnBlockDataM unBlockM = null;
			
			while(rs.next()){				
				int param = 1;
				unBlockM = new PLInboxUnBlockDataM();
				
				unBlockM.setBlockDate(rs.getTimestamp("UPDATE_DATE"));
				unBlockM.setAppRecId(rs.getString("APPLICATION_RECORD_ID"));
				unBlockM.setAppNo(rs.getString("APPLICATION_NO"));
				unBlockM.setAppStatus(rs.getString("APPLICATION_STATUS"));
				unBlockM.setFirstName(rs.getString("TH_FIRST_NAME"));
				
				unBlockM.setLastName(rs.getString("TH_LAST_NAME"));
				unBlockM.setProduct(rs.getString("PRODUCT"));
				unBlockM.setAppDate(rs.getTimestamp("APPLICATION_DATE"));
				unBlockM.setSlaId(rs.getString("REMAIN_APP_TIME"));				
				unBlockM.setOwner(rs.getString("OWNER"));
				
				unBlockM.setJobState(rs.getString("JOB_STATE"));
				unBlockM.setActivityType(rs.getString("ACTIVITY_TYPE"));
				unBlockM.setPriority(rs.getString("PRIORITY"));
				
				blockVect.add(unBlockM);				
			}		
		}catch(Exception e){
			log.fatal(e.getLocalizedMessage());
			throw new PLOrigApplicationException(e.getMessage());
		}finally{
			try{
				closeConnection(conn, rs, ps);
			}catch(Exception e){
				log.fatal(e.getLocalizedMessage());
				throw new PLOrigApplicationException(e.getMessage());
			}
		}		
		return blockVect;
	}

}
