package com.eaf.orig.ulo.pl.app.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

import org.apache.log4j.Logger;

import com.eaf.orig.shared.constant.OrigConstant;
import com.eaf.orig.shared.dao.OrigObjectDAO;
import com.eaf.orig.shared.service.OrigServiceLocator;
import com.eaf.orig.shared.util.OrigUtil;
//import com.eaf.orig.ulo.pl.app.utility.ORIGLogic;
import com.eaf.orig.ulo.pl.model.app.TrackingDataM;

public class PLORIGTargetPointValueImpl extends OrigObjectDAO implements PLORIGTargetPointValue {
	private static Logger log = Logger.getLogger(PLORIGTargetPointValueImpl.class);
	@Override
	public String getCurrentTargetPointValue(String User) {
		PreparedStatement ps = null;
		ResultSet rs = null;Connection conn = null;
		String CurrentTargetPoint = null;
		if(null==User){
			return "-";
		}		
		try{
			conn = getConnection();
			StringBuilder sql = new StringBuilder();
			if(!OrigUtil.isEmptyString(User)&&!OrigConstant.ROLE_CA.equalsIgnoreCase(User)){
				sql.append("SELECT nvl(WORK_TARGET, '0') FROM US_USER_DETAIL ");
			}else if(OrigUtil.isEmptyString(User)&&OrigConstant.ROLE_CA.contains(User)){
				sql.append("SELECT CA_POINT_TARGET FROM US_USER_DETAIL ");
			}
				sql.append("WHERE USER_NAME = ? ");

			String dSql = String.valueOf(sql);
			log.debug("Sql=" + dSql);
			ps = conn.prepareStatement(dSql);
			ps.setString(1, User);

			rs = ps.executeQuery();
			if(rs.next()){
				CurrentTargetPoint = rs.getString(1);
			}			
		}catch(Exception e){
			log.fatal("ERROR ",e);
		}finally{
			try{
				closeConnection(conn, rs, ps);
			}catch(Exception e){
				log.fatal("ERROR ",e);
			}
		}
		return CurrentTargetPoint;
	}

	@Override
	public String getFinishTargetPointValue(String User, String Role) {
		PreparedStatement ps = null;
		ResultSet rs = null;
		String CurrentTargetPoint = null;
		if(null==User||null==Role){
			return "-";
		}		Connection conn = null;
		try{
			conn = getConnection();
			StringBuilder sql = new StringBuilder();
				sql.append("SELECT nvl(WORKFLOW_SEARCH.COUNT_TOTALJOB_DONE_NON_TDID(?,?),'0') ");
				sql.append("FROM DUAL ");

			String dSql = String.valueOf(sql);
			log.debug("Sql=" + dSql);
			ps = conn.prepareStatement(dSql);
			ps.setString(1, User);
			ps.setString(2, Role);

			rs = ps.executeQuery();
			if(rs.next()){
				CurrentTargetPoint = rs.getString(1);
			}
			
		}catch(Exception e){
			log.fatal("ERROR ",e);
		}finally{
			try{
				closeConnection(conn, rs, ps);
			}catch(Exception e){
				log.fatal("ERROR ",e);
			}
		}
		return CurrentTargetPoint;
	}
	
	public String getFinishTargetPointValue(String User, String Role, String tdid){
		PreparedStatement ps = null;
		ResultSet rs = null;
		String CurrentTargetPoint = null;
		if(null==User||null==Role){
			return "-";
		}		Connection conn = null;
		try{
			conn = getConnection(OrigServiceLocator.WORKFLOW_DB);
			StringBuilder sql = new StringBuilder();
				sql.append("SELECT nvl(WORKFLOW_SEARCH.COUNT_TOTALJOB_DONE(?,?,?),'0') ");
				sql.append("FROM DUAL ");

			String dSql = String.valueOf(sql);
			log.debug("Sql=" + dSql);
			ps = conn.prepareStatement(dSql);
			ps.setString(1, User);
			ps.setString(2, tdid);
			ps.setString(3, Role);
			rs = ps.executeQuery();
			
			if(rs.next()){
				CurrentTargetPoint = rs.getString(1);
			}			
		}catch(Exception e){
			log.fatal("ERROR ",e);
		}finally{
			try{
				closeConnection(conn, rs, ps);
			}catch(Exception e){
				log.fatal("ERROR ",e);
			}
		}
		return CurrentTargetPoint;
	}

	@Override
	public String getTotolJobWorking(TrackingDataM trackM) {
		PreparedStatement ps = null;
		ResultSet rs = null;
		String TOTAL = "0";Connection conn = null;
		try{
			conn = getConnection();
			StringBuilder sql = new StringBuilder("");
			
				sql.append(" SELECT ");
				sql.append("     SUM( ");
				sql.append("         CASE ");
				sql.append("             WHEN NVL(OUTPUT.OUTPUT,0) - NVL(SEND_BACK.SEND_BACK,0) -NVL(REASSIGN.REASSIGN,0) -(NVL( ");
				sql.append("                 BLOCKER.BLOCKED,NVL(PREV_BLOCKER.PREV_BLOCKED,0)) -NVL(PREV_BLOCKER.PREV_BLOCKED,0) ");
				sql.append("                 ) - NVL(CANCEL.CANCELED,0) > 0 ");
				sql.append("             THEN NVL(OUTPUT.OUTPUT,0) - NVL(SEND_BACK.SEND_BACK,0) -NVL(REASSIGN.REASSIGN,0) -(NVL( ");
				sql.append("                 BLOCKER.BLOCKED,NVL(PREV_BLOCKER.PREV_BLOCKED,0)) -NVL(PREV_BLOCKER.PREV_BLOCKED,0) ");
				sql.append("                 ) - NVL(CANCEL.CANCELED,0) ");
				sql.append("             ELSE 0 ");
				sql.append("         END ) + SUM(NVL(SEND_BACK.SEND_BACK,0))+ SUM(NVL(CANCEL.CANCELED,0)) OUTPUT ");
				sql.append(" FROM ");
				sql.append("     US_USER_DETAIL US, ");
				sql.append("     ( ");
				sql.append("         SELECT ");
				sql.append("             WF_HIS.OWNER , ");
				sql.append("             NVL(COUNT(WF_HIS.JOB_ID),0) OUTPUT ");
				sql.append("         FROM ");
				sql.append("             WF_INSTANT_HISTORY WF_HIS , ");
				sql.append("             WF_ACTIVITY_TEMPLATE ACT ");
				sql.append("         WHERE ");
				sql.append("             WF_HIS.ATID = ACT.ATID ");
				sql.append("         AND ACT.ROLE_ID = ? ");
//				if(ORIGLogic.RoleICDC(trackM.getRole())){					
//					sql.append("         AND ACT.PTID = 'KLOP002' ");
//				}else{
//					sql.append("         AND ACT.PTID IN ('KLOP001','KLOP003') ");
//				}				
				sql.append("         AND ");
				sql.append("             ( ");
				sql.append("                 WF_HIS.ATID LIKE 'STI%' ");
				sql.append("              OR WF_HIS.ATID IN ('STC0401','STC0402','STC0403','STC0411') ");
				sql.append("             ) ");
				sql.append("         AND WF_HIS.COMPLETE_DATE BETWEEN TRUNC(SYSDATE) AND TRUNC(SYSDATE) + .99999 ");
				sql.append("         AND WF_HIS.SEQ = ");
				sql.append("             ( ");
				sql.append("                 SELECT ");
				sql.append("                     MAX(WF.SEQ) ");
				sql.append("                 FROM ");
				sql.append("                     WF_INSTANT_HISTORY WF , ");
				sql.append("                     WF_ACTIVITY_TEMPLATE TEMP ");
				sql.append("                 WHERE ");
				sql.append("                     WF.ATID = TEMP.ATID ");
				sql.append("                 AND WF.JOB_ID =WF_HIS.JOB_ID ");
				sql.append("                 AND TEMP.ROLE_ID =ACT.ROLE_ID ");
				sql.append("                 AND WF.OWNER = WF_HIS.OWNER ");
				sql.append("                 GROUP BY ");
				sql.append("                     WF.JOB_ID ");
				sql.append("             ) ");
				sql.append("         GROUP BY ");
				sql.append("             WF_HIS.OWNER ");
				sql.append("     ) ");
				sql.append("     OUTPUT , ");
				sql.append("     ( ");
				sql.append("         SELECT ");
				sql.append("             WF.OWNER , ");
				sql.append("             COUNT(WF.JOB_ID) BLOCKED ");
				sql.append("         FROM ");
				sql.append("             WF_INSTANT_HISTORY WF, ");
				sql.append("             WF_ACTIVITY_TEMPLATE TEMP, ");
				sql.append("             ( ");
				sql.append("                 SELECT ");
				sql.append("                     JOB_ID, ");
				sql.append("                     FROM_ATID, ");
				sql.append("                     SEQ ");
				sql.append("                 FROM ");
				sql.append("                     WF_INSTANT_HISTORY WF_HIS ");
				sql.append("                 WHERE ");
				sql.append("                     SEQ = ");
				sql.append("                     ( ");
				sql.append("                         SELECT ");
				sql.append("                             MAX(SEQ) ");
				sql.append("                         FROM ");
				sql.append("                             WF_INSTANT_HISTORY ");
				sql.append("                         WHERE ");
				sql.append("                             JOB_ID = WF_HIS.JOB_ID ");
				sql.append("                     ) ");
				sql.append("                 AND ACTION LIKE '%Block%' ");
				sql.append("                 AND SUBSTR(WF_HIS.ATID,1,7) = FROM_ATID ");
				sql.append("                 AND ");
				sql.append("                     ( ");
				sql.append("                         WF_HIS.FROM_ATID NOT LIKE 'STC%' ");
				sql.append("                     AND WF_HIS.FROM_ATID NOT LIKE 'STA%' ");
				sql.append("                     ) ");
				sql.append("                 AND ");
				sql.append("                     ( ");
				sql.append("                         WF_HIS.ATID NOT LIKE 'STC%' ");
				sql.append("                     AND WF_HIS.ATID NOT LIKE 'STA%' ");
				sql.append("                     ) ");
				sql.append("             ) ");
				sql.append("             BLOCKER ");
				sql.append("         WHERE ");
				sql.append("             WF.ATID = TEMP.ATID ");
				sql.append("         AND TEMP.ROLE_ID = ? ");
//				if(ORIGLogic.RoleICDC(trackM.getRole())){					
//					sql.append("         AND TEMP.PTID = 'KLOP002' ");
//				}else{
//					sql.append("         AND TEMP.PTID IN ('KLOP001','KLOP003') ");
//				}
				sql.append("         AND WF.JOB_ID = BLOCKER.JOB_ID ");
				sql.append("         AND WF.ATID = BLOCKER.FROM_ATID ");
				sql.append("         AND WF.SEQ = (BLOCKER.SEQ - 1) ");
				sql.append("         GROUP BY ");
				sql.append("             OWNER ");
				sql.append("     ) ");
				sql.append("     BLOCKER , ");
				sql.append("     ( ");
				sql.append("         SELECT ");
				sql.append("             WF.OWNER , ");
				sql.append("             COUNT(WF.JOB_ID) PREV_BLOCKED ");
				sql.append("         FROM ");
				sql.append("             WF_INSTANT_HISTORY WF, ");
				sql.append("             WF_ACTIVITY_TEMPLATE TEMP, ");
				sql.append("             ( ");
				sql.append("                 SELECT ");
				sql.append("                     JOB_ID, ");
				sql.append("                     FROM_ATID, ");
				sql.append("                     SEQ ");
				sql.append("                 FROM ");
				sql.append("                     WF_INSTANT_HISTORY WF_HIS ");
				sql.append("                 WHERE ");
				sql.append("                     SEQ = ");
				sql.append("                     ( ");
				sql.append("                         SELECT ");
				sql.append("                             MAX(SEQ) ");
				sql.append("                         FROM ");
				sql.append("                             WF_INSTANT_HISTORY ");
				sql.append("                         WHERE ");
				sql.append("                             JOB_ID = WF_HIS.JOB_ID ");
				sql.append("                         AND WF_INSTANT_HISTORY.CREATE_DATE < TRUNC(SYSDATE) ");
				sql.append("                     ) ");
				sql.append("                 AND ACTION LIKE '%Block%' ");
				sql.append("                 AND SUBSTR(WF_HIS.ATID,1,7) = FROM_ATID ");
				sql.append("                 AND ");
				sql.append("                     ( ");
				sql.append("                         WF_HIS.FROM_ATID NOT LIKE 'STC%' ");
				sql.append("                     AND WF_HIS.FROM_ATID NOT LIKE 'STA%' ");
				sql.append("                     ) ");
				sql.append("                 AND ");
				sql.append("                     ( ");
				sql.append("                         WF_HIS.ATID NOT LIKE 'STC%' ");
				sql.append("                     AND WF_HIS.ATID NOT LIKE 'STA%' ");
				sql.append("                     ) ");
				sql.append("                 AND WF_HIS.CREATE_DATE < TRUNC(SYSDATE) ");
				sql.append("             ) ");
				sql.append("             BLOCKER ");
				sql.append("         WHERE ");
				sql.append("             WF.ATID = TEMP.ATID ");
				sql.append("         AND TEMP.ROLE_ID = ? ");
//				if(ORIGLogic.RoleICDC(trackM.getRole())){					
//					sql.append("         AND TEMP.PTID = 'KLOP002' ");
//				}else{
//					sql.append("         AND TEMP.PTID IN ('KLOP001','KLOP003') ");
//				}
				sql.append("         AND WF.JOB_ID = BLOCKER.JOB_ID ");
				sql.append("         AND WF.ATID = BLOCKER.FROM_ATID ");
				sql.append("         AND WF.SEQ = (BLOCKER.SEQ - 1) ");
				sql.append("         GROUP BY ");
				sql.append("             OWNER ");
				sql.append("     ) ");
				sql.append("     PREV_BLOCKER , ");
				sql.append("     ( ");
				sql.append("         SELECT ");
				sql.append("             WF.OWNER , ");
				sql.append("             COUNT(DISTINCT WF.JOB_ID) REASSIGN ");
				sql.append("         FROM ");
				sql.append("             WF_INSTANT_HISTORY WF, ");
				sql.append("             WF_ACTIVITY_TEMPLATE TEMP, ");
				sql.append("             ( ");
				sql.append("                 SELECT DISTINCT ");
				sql.append("                     T.ROLE_ID, ");
				sql.append("                     JOB_ID, ");
				sql.append("                     FROM_ATID, ");
				sql.append("                     SEQ ");
				sql.append("                 FROM ");
				sql.append("                     WF_INSTANT_HISTORY WF_HIS, ");
				sql.append("                     WF_ACTIVITY_TEMPLATE T ");
				sql.append("                 WHERE ");
				sql.append("                     WF_HIS.ATID = T.ATID ");
				sql.append("                 AND T.ROLE_ID = ? ");
//				if(ORIGLogic.RoleICDC(trackM.getRole())){					
//					sql.append("         AND T.PTID = 'KLOP002' ");
//				}else{
//					sql.append("         AND T.PTID IN ('KLOP001','KLOP003') ");
//				}
				sql.append("                 AND ACTION IN('Reassign','Reallocate') ");
				sql.append("                 AND WF_HIS.CREATE_DATE BETWEEN TRUNC(SYSDATE) AND TRUNC(SYSDATE) +0.99999 ");
				sql.append("             ) ");
				sql.append("             REASSING ");
				sql.append("         WHERE ");
				sql.append("             WF.ATID = TEMP.ATID ");
				sql.append("         AND TEMP.ROLE_ID = ? ");
//				if(ORIGLogic.RoleICDC(trackM.getRole())){					
//					sql.append("         AND TEMP.PTID = 'KLOP002' ");
//				}else{
//					sql.append("         AND TEMP.PTID IN ('KLOP001','KLOP003') ");
//				}
				sql.append("         AND WF.JOB_ID = REASSING.JOB_ID ");
				sql.append("         AND WF.ATID = REASSING.FROM_ATID ");
				sql.append("         AND WF.SEQ = (REASSING.SEQ - 1) ");
				sql.append("         AND NOT EXISTS ");
				sql.append("             ( ");
				sql.append("                 SELECT ");
				sql.append("                     1 ");
				sql.append("                 FROM ");
				sql.append("                     WF_INSTANT_HISTORY TMP_WF, ");
				sql.append("                     WF_ACTIVITY_TEMPLATE TEMP, ");
				sql.append("                     ( ");
				sql.append("                         SELECT ");
				sql.append("                             JOB_ID, ");
				sql.append("                             FROM_ATID, ");
				sql.append("                             SEQ ");
				sql.append("                         FROM ");
				sql.append("                             WF_INSTANT_HISTORY WF_HIS ");
				sql.append("                         WHERE ");
				sql.append("                             SEQ = ");
				sql.append("                             ( ");
				sql.append("                                 SELECT ");
				sql.append("                                     MAX(SEQ) ");
				sql.append("                                 FROM ");
				sql.append("                                     WF_INSTANT_HISTORY ");
				sql.append("                                 WHERE ");
				sql.append("                                     JOB_ID = WF_HIS.JOB_ID ");
				sql.append("                             ) ");
				sql.append("                         AND ACTION LIKE '%Block%' ");
				sql.append("                         AND SUBSTR(WF_HIS.ATID,1,7) = FROM_ATID ");
				sql.append("                         AND ");
				sql.append("                             ( ");
				sql.append("                                 WF_HIS.FROM_ATID NOT LIKE 'STC%' ");
				sql.append("                             AND WF_HIS.FROM_ATID NOT LIKE 'STA%' ");
				sql.append("                             ) ");
				sql.append("                         AND ");
				sql.append("                             ( ");
				sql.append("                                 WF_HIS.ATID NOT LIKE 'STC%' ");
				sql.append("                             AND WF_HIS.ATID NOT LIKE 'STA%' ");
				sql.append("                             ) ");
				sql.append("                     ) ");
				sql.append("                     BLOCKER ");
				sql.append("                 WHERE ");
				sql.append("                     TMP_WF.ATID = TEMP.ATID ");
				sql.append("                 AND TEMP.ROLE_ID = ? ");
//				if(ORIGLogic.RoleICDC(trackM.getRole())){					
//					sql.append("         AND TEMP.PTID = 'KLOP002' ");
//				}else{
//					sql.append("         AND TEMP.PTID IN ('KLOP001','KLOP003') ");
//				}
				sql.append("                 AND TMP_WF.JOB_ID = BLOCKER.JOB_ID ");
				sql.append("                 AND TMP_WF.ATID = BLOCKER.FROM_ATID ");
				sql.append("                 AND TMP_WF.SEQ = (BLOCKER.SEQ - 1) ");
				sql.append("                 AND TMP_WF.JOB_ID = WF.JOB_ID ");
				sql.append("                 AND TMP_WF.OWNER = WF.OWNER ");
				sql.append("             ) ");
				sql.append("         AND NOT EXISTS ");
				sql.append("             ( ");
				sql.append("                 SELECT ");
				sql.append("                     JOB_ID, ");
				sql.append("                     OWNER ");
				sql.append("                 FROM ");
				sql.append("                     WF_WORK_QUEUE WQ, ");
				sql.append("                     USER_ROLE_JOIN_ROLE UR ");
				sql.append("                 WHERE ");
				sql.append("                     UR.USER_NAME = WQ.OWNER ");
				sql.append("                 AND UR.ROLE_NAME = ? ");
				sql.append("                 AND UR.USER_NAME = WF.OWNER ");
				sql.append("                 AND WQ.JOB_ID = WF.JOB_ID ");
				sql.append("             ) ");
				sql.append("         AND NOT EXISTS ");
				sql.append("             ( ");
				sql.append("                 SELECT ");
				sql.append("                     1 ");
				sql.append("                 FROM ");
				sql.append("                     ORIG_APPLICATION , ");
				sql.append("                     WF_JOBID_MAPPING ");
				sql.append("                 WHERE ");
				sql.append("                     ORIG_APPLICATION.FINAL_APP_DECISION_DATE BETWEEN TRUNC(SYSDATE) AND TRUNC( ");
				sql.append("                     SYSDATE)+0.99999 ");
				sql.append("                 AND WF_JOBID_MAPPING.APPLICATION_RECORD_ID = ORIG_APPLICATION.APPLICATION_RECORD_ID ");
				sql.append("                 AND WF_JOBID_MAPPING.JOB_ID = WF.JOB_ID ");
				sql.append("                 AND WF_JOBID_MAPPING.JOB_STATUS='ACTIVE' ");
				sql.append("                 AND ORIG_APPLICATION.FINAL_APP_DECISION_BY = WF.OWNER ");
				sql.append("                 AND JOB_STATE IN ");
				sql.append("                     ( ");
				sql.append("                         SELECT ");
				sql.append("                             SUBSTR(CSV, INSTR(CSV,',',1,LEV) + 1, INSTR(CSV,',',1,LEV+1 )-INSTR(CSV ");
				sql.append("                             ,',',1,LEV)-1) ");
				sql.append("                         FROM ");
				sql.append("                             ( ");
				sql.append("                                 SELECT ");
				sql.append("                                     ','||PARAM_VALUE||',' CSV ");
				sql.append("                                 FROM ");
				sql.append("                                     GENERAL_PARAM ");
				sql.append("                                 WHERE ");
				sql.append("                                     PARAM_CODE = 'JOBSTATE_CANCEL' ");
				sql.append("                             ) ");
				sql.append("                             , ");
				sql.append("                             ( ");
				sql.append("                                 SELECT ");
				sql.append("                                     LEVEL LEV ");
				sql.append("                                 FROM ");
				sql.append("                                     DUAL CONNECT BY LEVEL <= 100 ");
				sql.append("                             ) ");
				sql.append("                         WHERE ");
				sql.append("                             LEV <= LENGTH(CSV)-LENGTH(REPLACE(CSV,','))-1 ");
				sql.append("                     ) ");
				sql.append("             ) ");
				sql.append("         AND EXISTS ");
				sql.append("             ( ");
				sql.append("                 SELECT ");
				sql.append("                     1 ");
				sql.append("                 FROM ");
				sql.append("                     WF_INSTANT_HISTORY ");
				sql.append("                 WHERE ");
				sql.append("                     JOB_ID = WF.JOB_ID ");
				sql.append("                 AND ACTION IN('Reassign','Reallocate') ");
				sql.append("                 AND SEQ = ");
				sql.append("                     ( ");
				sql.append("                         SELECT ");
				sql.append("                             MAX(SEQ) +1 ");
				sql.append("                         FROM ");
				sql.append("                             WF_INSTANT_HISTORY ");
				sql.append("                         WHERE ");
				sql.append("                             JOB_ID = WF.JOB_ID ");
				sql.append("                         AND OWNER = WF.OWNER ");
				sql.append("                     ) ");
				sql.append("             ) ");
				sql.append("         GROUP BY ");
				sql.append("             OWNER ");
				sql.append("     ) ");
				sql.append("     REASSIGN , ");
				sql.append("     ( ");
				sql.append("         SELECT ");
				sql.append("             ORIG_APPLICATION.FINAL_APP_DECISION_BY , ");
				sql.append("             COUNT(1) CANCELED ");
				sql.append("         FROM ");
				sql.append("             ORIG_APPLICATION ");
				sql.append("         WHERE ");
				sql.append("             ORIG_APPLICATION.FINAL_APP_DECISION_DATE BETWEEN TRUNC(SYSDATE) AND TRUNC(SYSDATE)+ ");
				sql.append("             0.99999 ");
				sql.append("         AND JOB_STATE IN ");
				sql.append("             ( ");
				sql.append("                 SELECT ");
				sql.append("                     SUBSTR(CSV, INSTR(CSV,',',1,LEV) + 1, INSTR(CSV,',',1,LEV+1 )-INSTR(CSV,',',1, ");
				sql.append("                     LEV)-1) ");
				sql.append("                 FROM ");
				sql.append("                     ( ");
				sql.append("                         SELECT ");
				sql.append("                             ','||PARAM_VALUE||',' CSV ");
				sql.append("                         FROM ");
				sql.append("                             GENERAL_PARAM ");
				sql.append("                         WHERE ");
				sql.append("                             PARAM_CODE = 'JOBSTATE_CANCEL' ");
				sql.append("                     ) ");
				sql.append("                     , ");
				sql.append("                     ( ");
				sql.append("                         SELECT ");
				sql.append("                             LEVEL LEV ");
				sql.append("                         FROM ");
				sql.append("                             DUAL CONNECT BY LEVEL <= 100 ");
				sql.append("                     ) ");
				sql.append("                 WHERE ");
				sql.append("                     LEV <= LENGTH(CSV)-LENGTH(REPLACE(CSV,','))-1 ");
				sql.append("             ) ");
				sql.append("         GROUP BY ");
				sql.append("             ORIG_APPLICATION.FINAL_APP_DECISION_BY ");
				sql.append("     ) ");
				sql.append("     CANCEL , ");
				sql.append("     ( ");
				sql.append("         SELECT ");
				sql.append("             WF.OWNER , ");
				sql.append("             COUNT( WF.JOB_ID) SEND_BACK ");
				sql.append("         FROM ");
				sql.append("             WF_INSTANT_HISTORY WF, ");
				sql.append("             WF_ACTIVITY_TEMPLATE TEMP, ");
				sql.append("             ( ");
				sql.append("                 SELECT ");
				sql.append("                     T.ROLE_ID, ");
				sql.append("                     JOB_ID, ");
				sql.append("                     FROM_ATID, ");
				sql.append("                     SEQ ");
				sql.append("                 FROM ");
				sql.append("                     WF_INSTANT_HISTORY WF_HIS, ");
				sql.append("                     WF_ACTIVITY_TEMPLATE T ");
				sql.append("                 WHERE ");
				sql.append("                     WF_HIS.ATID = T.ATID ");
				sql.append("                 AND SEQ = ");
				sql.append("                     ( ");
				sql.append("                         SELECT ");
				sql.append("                             MAX(SEQ) ");
				sql.append("                         FROM ");
				sql.append("                             WF_INSTANT_HISTORY T1, ");
				sql.append("                             WF_ACTIVITY_TEMPLATE T2 ");
				sql.append("                         WHERE ");
				sql.append("                             T1.JOB_ID = WF_HIS.JOB_ID ");
				sql.append("                         AND T1.ATID = T2.ATID ");
				sql.append("                         AND T2.ROLE_ID = T.ROLE_ID ");
				sql.append("                         AND ");
				sql.append("                             ( ");
				sql.append("                                 PROCESS_STATE IN ( 'SEND_BACKX','SEND_BACK') ");
				sql.append("                              OR ");
				sql.append("                                 ( ");
				sql.append("                                     ACTION LIKE 'Send back to % Block' ");
				sql.append("                                 AND PROCESS_STATE NOT IN ('SEND_M', 'SENDX_M','SENDX') ");
				sql.append("                                 ) ");
				sql.append("                             ) ");
				sql.append("                         AND WF_HIS.CREATE_DATE BETWEEN TRUNC(SYSDATE) AND TRUNC(SYSDATE) +0.99999 ");
				sql.append("                     ) ");
				sql.append("             ) ");
				sql.append("             SEND_BACK ");
				sql.append("         WHERE ");
				sql.append("             WF.ATID = TEMP.ATID ");
				sql.append("         AND TEMP.ROLE_ID = ? ");
//				if(ORIGLogic.RoleICDC(trackM.getRole())){					
//					sql.append("         AND TEMP.PTID = 'KLOP002' ");
//				}else{
//					sql.append("         AND TEMP.PTID IN ('KLOP001','KLOP003') ");
//				}
				sql.append("         AND WF.JOB_ID = SEND_BACK.JOB_ID ");
				sql.append("         AND WF.ATID = SEND_BACK.FROM_ATID ");
				sql.append("         AND WF.SEQ = (SEND_BACK.SEQ - 1) ");
				sql.append("         AND NOT EXISTS ");
				sql.append("             ( ");
				sql.append("                 SELECT ");
				sql.append("                     JOB_ID, ");
				sql.append("                     OWNER ");
				sql.append("                 FROM ");
				sql.append("                     WF_WORK_QUEUE WQ, ");
				sql.append("                     USER_ROLE_JOIN_ROLE UR ");
				sql.append("                 WHERE ");
				sql.append("                     UR.USER_NAME = WQ.OWNER ");
				sql.append("                 AND UR.ROLE_NAME = ? ");
				sql.append("                 AND UR.USER_NAME = WF.OWNER ");
				sql.append("                 AND WQ.JOB_ID = WF.JOB_ID ");
				sql.append("             ) ");
				sql.append("         GROUP BY ");
				sql.append("             OWNER ");
				sql.append("     ) ");
				sql.append("     SEND_BACK ");
				sql.append(" WHERE ");
				sql.append("     US.USER_NAME = OUTPUT.OWNER(+) ");
				sql.append(" AND US.USER_NAME = BLOCKER.OWNER(+) ");
				sql.append(" AND US.USER_NAME = REASSIGN.OWNER(+) ");
				sql.append(" AND US.USER_NAME = CANCEL.FINAL_APP_DECISION_BY(+) ");
				sql.append(" AND US.USER_NAME = SEND_BACK.OWNER(+) ");
				sql.append(" AND US.USER_NAME = PREV_BLOCKER.OWNER(+) ");
				sql.append(" AND US.ACTIVE_STATUS = 'A' ");
				sql.append(" AND US.USER_NAME = ? ");
				
				log.debug("SQL >. "+sql);
				
				int index = 1;
				ps = conn.prepareStatement(sql.toString());
				ps.setString(index++, trackM.getWfRole());
				ps.setString(index++, trackM.getWfRole());
				ps.setString(index++, trackM.getWfRole());
				ps.setString(index++, trackM.getWfRole());
				ps.setString(index++, trackM.getWfRole());
				ps.setString(index++, trackM.getWfRole());
				ps.setString(index++, trackM.getRole());
				ps.setString(index++, trackM.getWfRole());
				ps.setString(index++, trackM.getRole());
				ps.setString(index++, trackM.getUserName());
				rs = ps.executeQuery();				
				if(rs.next()){
					TOTAL = rs.getString("OUTPUT");
				}			
			
		}catch(Exception e){
			log.fatal("ERROR ",e);
		}finally{
			try{
				closeConnection(conn, rs, ps);
			}catch(Exception e){
				log.fatal("ERROR ",e);
			}
		}	
		return TOTAL;
	}

}
