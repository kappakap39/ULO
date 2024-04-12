/*
 * Created on Nov 14, 2007
 * Created by Weeraya
 *  
 * Copyright (c) 2007 Avalant Co.,Ltd.
 * 20 North Sathorn Road, 15-16th Floor Bubhajit Bldg., Silom, Bangrak, Bangkok 10500, Thailand
 * All rights reserved.
 *
 * This software is the confidential and prorietary infomation of
 * Avalant Co.,Ltd. ("Confidential Infomation"). You shall not
 * disclose such Confidential Infomation and shall use it only in
 * accordance with the terms of the license agreement you entered into
 * with Avalant Co.,Ltd.
 * 
 */
package com.eaf.orig.shared.dao.utility;

import java.math.BigDecimal;
import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Vector;

import org.apache.log4j.Logger;

import com.eaf.cache.data.CacheDataM;
import com.eaf.img.shared.model.ImgAttachmentDataM;
import com.eaf.orig.cache.properties.IntSchemeCacheProperties;
import com.eaf.orig.profile.model.UserDetailM;
import com.eaf.orig.shared.constant.OrigConstant;
import com.eaf.orig.shared.dao.EjbUtilException;
import com.eaf.orig.shared.dao.OrigObjectDAO;
import com.eaf.orig.shared.model.CampaignDataM;
import com.eaf.orig.shared.model.RekeyDataM;
import com.eaf.orig.shared.model.SLADataM;
import com.eaf.orig.shared.model.SearchAddressDataM;
import com.eaf.orig.shared.service.OrigServiceLocator;
import com.eaf.orig.shared.util.OrigUtil;
import com.eaf.orig.ulo.app.dao.exception.ApplicationException;
//import com.eaf.orig.shared.utility.ORIGCacheUtil;
//import com.eaf.orig.ulo.pl.app.utility.HTMLRenderUtil;
import com.eaf.orig.ulo.pl.constant.WorkflowConstant;
import com.eaf.orig.ulo.pl.model.app.PLApplicationImageSplitDataM;
import com.eaf.orig.ulo.pl.model.app.PLNCBDocDataM;
import com.eaf.xrules.ulo.pl.model.app.rule.PLXRulesDedupDataM;
import com.orig.bpm.ulo.model.WorkflowDataM;

/**
 * @author Weeraya
 *
 * Type: UtilityDAOImpl
 */
public class UtilityDAOImpl extends OrigObjectDAO implements UtilityDAO {
	Logger logger = Logger.getLogger(UtilityDAOImpl.class);
	
	/**
	 * 
	 * @param dealerCode
	 * @return
	 * @throws EjbUtilException
	 */
	public String getDealerDescription(String code) throws EjbUtilException{
		PreparedStatement ps = null;
		ResultSet rs = null;Connection conn = null;
		try {
			//conn = Get Connection
			conn = getConnection();
			StringBuilder sql = new StringBuilder("");			
			sql.append("SELECT TH_NAME");
			sql.append(" FROM MS_DEALER WHERE DEALER_CODE = ? ");
			String dSql = String.valueOf(sql);
//			logger.debug("Sql="+dSql);
			ps = conn.prepareStatement(dSql);
			rs = null;
			ps.setString(1, code);

			rs = ps.executeQuery();
			String result = null;
			if (rs.next()) {
				result = rs.getString(1);
			}
			return result;
		} catch (Exception e) {
			logger.error("Error >> ", e);
			throw new EjbUtilException(e.getMessage());
		} finally {
			try {
				closeConnection(conn, rs, ps);
			} catch (Exception e) {
				logger.error("Error >> ", e);
			}
		}
	}
	
	public String getZipCodeDescription(String code) throws EjbUtilException{
		PreparedStatement ps = null;
		ResultSet rs = null;Connection conn = null;
		try {
			//conn = Get Connection
			conn = getConnection();
			StringBuilder sql = new StringBuilder("");
			sql.append("SELECT THDESC");
			sql.append(" FROM HPTBHP44 WHERE CMPCDE = ? AND ZIPCDE = ? ");
			String dSql = String.valueOf(sql);
//			logger.debug("Sql="+dSql);
			ps = conn.prepareStatement(dSql);
			rs = null;
			ps.setString(1, OrigConstant.ORIG_CMPCODE);
			ps.setString(2, code);
			
			rs = ps.executeQuery();
			String result = null;
			if (rs.next()) {
				result = rs.getString(1);
			}
			return result;
		} catch (Exception e) {
			logger.error("Error >> ", e);
			throw new EjbUtilException(e.getMessage());
		} finally {
			try {
				closeConnection(conn, rs, ps);
			} catch (Exception e) {
				logger.error("Error >> ", e);
			}
		}
	}
	
	public String getSellerDescription(String code) throws EjbUtilException{
		PreparedStatement ps = null;
		ResultSet rs = null;Connection conn = null;
		try {
			//conn = Get Connection
			conn = getConnection();
			StringBuilder sql = new StringBuilder("");
			sql.append("SELECT THNAME");
			sql.append(" FROM HPTBHP30 WHERE CMPCDE = ? AND SELLCDE = ? ");
			String dSql = String.valueOf(sql);
//			logger.debug("Sql="+dSql);
			ps = conn.prepareStatement(dSql);
			rs = null;
			ps.setString(1, OrigConstant.ORIG_CMPCODE);
			ps.setString(2, code);

			rs = ps.executeQuery();
			String result = null;
			if (rs.next()) {
				result = rs.getString(1);
			}
			return result;
		} catch (Exception e) {
			logger.error("Error >> ", e);
			throw new EjbUtilException(e.getMessage());
		} finally {
			try {
				closeConnection(conn, rs, ps);
			} catch (Exception e) {
				logger.error("Error >> ", e);
			}
		}
	}
	
	public String getCarModelDescription(String code) throws EjbUtilException{
		PreparedStatement ps = null;
		ResultSet rs = null;Connection conn = null;
		try {
			//conn = Get Connection
			conn = getConnection();
			StringBuilder sql = new StringBuilder("");
			sql.append("SELECT TH_DESC");
			sql.append(" FROM MS_CAR_MODEL WHERE MODEL = ? ");
			String dSql = String.valueOf(sql);
//			logger.debug("Sql="+dSql);
			ps = conn.prepareStatement(dSql);
			rs = null;
			ps.setString(1, code);

			rs = ps.executeQuery();
			String result = null;
			if (rs.next()) {
				result = rs.getString(1);
			}
			return result;
		} catch (Exception e) {
			logger.error("Error >> ", e);
			throw new EjbUtilException(e.getMessage());
		} finally {
			try {
				closeConnection(conn, rs, ps);
			} catch (Exception e) {
				logger.error("Error >> ", e);
			}
		}
	}
	
	public String getBranchDescription(String code) throws EjbUtilException{
		PreparedStatement ps = null;
		ResultSet rs = null;Connection conn = null;
		try {
			//conn = Get Connection
			conn = getConnection();
			StringBuilder sql = new StringBuilder("");
			sql.append("SELECT TH_DESC");
			sql.append(" FROM MS_BANK_BRANCH WHERE BRANCH_CODE = ? ");
			String dSql = String.valueOf(sql);
//			logger.debug("Sql="+dSql);
			ps = conn.prepareStatement(dSql);
			rs = null;
			ps.setString(1, code);

			rs = ps.executeQuery();
			String result = null;
			if (rs.next()) {
				result = rs.getString(1);
			}
			return result;
		} catch (Exception e) {
			logger.error("Error >> ", e);
			throw new EjbUtilException(e.getMessage());
		} finally {
			try {
				closeConnection(conn, rs, ps);
			} catch (Exception e) {
				logger.error("Error >> ", e);
			}
		}
	}
	
	public String getBranchDescription(String code, String bankCode) throws EjbUtilException{
		PreparedStatement ps = null;
		ResultSet rs = null;Connection conn = null;
		try {
			//conn = Get Connection
			conn = getConnection();
			StringBuilder sql = new StringBuilder("");
			sql.append("SELECT TH_DESC");
			sql.append(" FROM MS_BANK_BRANCH WHERE BRANCH_CODE = ? AND BANK_CODE = ? ");
			String dSql = String.valueOf(sql);
//			logger.debug("Sql="+dSql);
			ps = conn.prepareStatement(dSql);
			rs = null;
			ps.setString(1, code);
			ps.setString(2, bankCode);

			rs = ps.executeQuery();
			String result = null;
			if (rs.next()) {
				result = rs.getString(1);
			}
			return result;
		} catch (Exception e) {
			logger.error("Error >> ", e);
			throw new EjbUtilException(e.getMessage());
		} finally {
			try {
				closeConnection(conn, rs, ps);
			} catch (Exception e) {
				logger.error("Error >> ", e);
			}
		}
	}
	
	/**
	 * 
	 * @param code
	 * @return
	 * @throws EjbUtilException
	 */
	public String getModelDescription(String code, String carBrand) throws EjbUtilException{
		PreparedStatement ps = null;
		ResultSet rs = null;Connection conn = null;
		try {
			//conn = Get Connection
			conn = getConnection();
			StringBuilder sql = new StringBuilder("");
			sql.append("SELECT TH_DESC, GEAR, CC, WEIGHT ");
			sql.append(" FROM MS_CAR_MODEL WHERE BRAND = ? AND MODEL = ? ");
			String dSql = String.valueOf(sql);
//			logger.debug("Sql="+dSql);
			ps = conn.prepareStatement(dSql);
			rs = null;
			ps.setString(1, carBrand);
			ps.setString(2, code);

			rs = ps.executeQuery();
			StringBuilder result = new StringBuilder("");
			if (rs.next()) {
				result.append(rs.getString(1));
				result.append(",");
				result.append(rs.getString(2));
				result.append(",");
				result.append(rs.getString(3));
				result.append(",");
				result.append(rs.getString(4));
			}
			return result.toString();
		} catch (Exception e) {
			logger.error("Error >> ", e);
			throw new EjbUtilException(e.getMessage());
		} finally {
			try {
				closeConnection(conn, rs, ps);
			} catch (Exception e) {
				logger.error("Error >> ", e);
			}
		}
	}
	
	public Vector getSLAForUW(String qName, String status, String action, String jobState, String userName, double time) throws EjbUtilException{
		PreparedStatement ps = null;
		ResultSet rs = null;Connection conn = null;
		try {
			//conn = Get Connection
			conn = getConnection();
			StringBuilder sql = new StringBuilder("");
			
			sql.append("select distinct a.application_no, a.application_status,c.th_title_code,c.th_first_name,c.th_last_name, l.internal_ckecker, l.marketing_code, a.create_date, a.update_date");
			sql.append(" from orig_application a,");
			sql.append("     orig_application_log b,");
			sql.append("     orig_application_appv_grp ag,");
			sql.append("     user_approval_authority ua,");
			sql.append("	 orig_personal_info c,");
			sql.append("     orig_loan l ");
			sql.append(" where a.application_record_id = b.application_record_id");
			sql.append(" and a.update_date < (sysdate - ? / 24)");
			sql.append(" and ag.group_name = ua.group_name");
		  //sql.append(" and ag.loan_type = ua.loan_type");
		  //sql.append(" and ag.customer_type = ua.customer_type");
			sql.append(" and upper(ua.user_id) = upper(?)");
			sql.append(" and ag.application_record_id = a.application_record_id");
			sql.append(" and a.application_record_id = c.application_record_id");
			sql.append(" and c.personal_type = ?");
			sql.append(" and a.application_record_id = l.application_record_id");
			sql.append(" and a.job_state = ?");
			sql.append(" and b.action = ?");
			sql.append(" and b.log_seq =");
			sql.append("                (select max (log_seq)");
			sql.append("                   from orig_application_log c");
			sql.append("                  where c.application_record_id = a.application_record_id)");
			sql.append(" order by a.create_date");
			
			String dSql = String.valueOf(sql);
//			logger.debug("Sql="+dSql);
//			logger.debug("qName="+qName);
//			logger.debug("status="+status);
//			logger.debug("action="+action);
//			logger.debug("jobState="+jobState);
//			logger.debug("userName="+userName);
			ps = conn.prepareStatement(dSql);
			rs = null;
			
			ps.setDouble(1, time);
			ps.setString(2, userName);
			ps.setString(3, OrigConstant.PERSONAL_TYPE_APPLICANT);
			ps.setString(4, jobState);
			ps.setString(5, action);
			

			rs = ps.executeQuery();
			Vector result = new Vector();
			SLADataM dataM;
			int recordCount = 0;
			while (rs.next() && rs.getRow()<6) {
//				logger.debug("rs.getRow = "+rs.getRow());
				dataM = new SLADataM();
				dataM.setAppNo(rs.getString(1));
				dataM.setAppStatus(rs.getString(2));
				dataM.setTitleName(rs.getString(3));
				dataM.setFirstName(rs.getString(4));
				dataM.setLastName(rs.getString(5));
				dataM.setInernalCheckCode(rs.getString(6));
				dataM.setMktCode(rs.getString(7));
				dataM.setCreateDate(rs.getTimestamp(8));
				dataM.setUpdateDate(rs.getTimestamp(9));
				result.add(dataM);
				recordCount++;
			}
//			logger.debug("rs = "+rs);
			if(rs.getRow() == 6){
				recordCount++;
			}
			while(rs.next()){
				/*if(rs.getRow() == 7){
					recordCount++;
				}*/
				recordCount++;
			}
			//int recordCount = rs.getFetchSize();
//			logger.debug("recordCount = "+recordCount);
			result.add(String.valueOf(recordCount));
			
			return result;
			
		} catch (Exception e) {
			logger.error("Error >> ", e);
			throw new EjbUtilException(e.getMessage());
		} finally {
			try {
				closeConnection(conn, rs, ps);
			} catch (Exception e) {
				logger.error("Error >> ", e);
			}
		}
	}
	
	public Vector getSLAForXCMR(String qName, String status, String action, String jobState, String userName, double time) throws EjbUtilException{
		PreparedStatement ps = null;
		ResultSet rs = null;Connection conn = null;
		try {
			//conn = Get Connection
			conn = getConnection();
			StringBuilder sql = new StringBuilder("");
			
			sql.append("select distinct a.application_no, a.application_status,c.th_title_code,c.th_first_name,c.th_last_name, l.internal_ckecker, l.marketing_code, a.create_date, a.update_date");
			sql.append(" from orig_application a,");
			sql.append("     orig_application_log b,");
			sql.append("     orig_personal_info c,");
			sql.append("     orig_loan l ");
			sql.append(" where a.application_record_id = b.application_record_id");
			sql.append(" and a.update_date < (sysdate - ? / 24)");
			sql.append(" and a.application_record_id = c.application_record_id");
			sql.append(" and c.personal_type = ?");
			sql.append(" and l.application_record_id = a.application_record_id");
			sql.append(" and a.job_state = ?");
			sql.append(" and b.action = ?");
			sql.append(" and b.log_seq =");
			sql.append("                (select max (log_seq)");
			sql.append("                   from orig_application_log c");
			sql.append("                  where c.application_record_id = a.application_record_id)");
			sql.append(" order by a.create_date");
			
			String dSql = String.valueOf(sql);
//			logger.debug("Sql="+dSql);
//			logger.debug("qName="+qName);
//			logger.debug("status="+status);
//			logger.debug("action="+action);
//			logger.debug("jobState="+jobState);
			ps = conn.prepareStatement(dSql);
			rs = null;
			
			ps.setDouble(1, time);
			ps.setString(2, OrigConstant.PERSONAL_TYPE_APPLICANT);
			ps.setString(3, jobState);
			ps.setString(4, action);
			

			rs = ps.executeQuery();
			Vector result = new Vector();
			SLADataM dataM;
			int recordCount = 0;
			while (rs.next() && rs.getRow()<6) {
//				logger.debug("rs.getRow = "+rs.getRow());
				dataM = new SLADataM();
				dataM.setAppNo(rs.getString(1));
				dataM.setAppStatus(rs.getString(2));
				dataM.setTitleName(rs.getString(3));
				dataM.setFirstName(rs.getString(4));
				dataM.setLastName(rs.getString(5));
				dataM.setInernalCheckCode(rs.getString(6));
				dataM.setMktCode(rs.getString(7));
				dataM.setCreateDate(rs.getTimestamp(8));
				dataM.setUpdateDate(rs.getTimestamp(9));
				result.add(dataM);
				recordCount++;
			}
//			logger.debug("rs = "+rs);
			if(rs.getRow() == 6){
				recordCount++;
			}
			while(rs.next()){
				/*if(rs.getRow() == 7){
					recordCount++;
				}*/
				recordCount++;
			}
			//int recordCount = rs.getFetchSize();
//			logger.debug("recordCount = "+recordCount);
			result.add(String.valueOf(recordCount));
			
			return result;
			
		} catch (Exception e) {
			logger.error("Error >> ", e);
			throw new EjbUtilException(e.getMessage());
		} finally {
			try {
				closeConnection(conn, rs, ps);
			} catch (Exception e) {
				logger.error("Error >> ", e);
			}
		}
	}
	
	public Vector getSLAForCMR(String qName, String status, String action, String jobState, String userName, double time) throws EjbUtilException{
		PreparedStatement ps = null;
		ResultSet rs = null;Connection conn = null;
		try {
			//conn = Get Connection
			conn = getConnection();
			StringBuilder sql = new StringBuilder("");
			
			sql.append("select distinct a.application_no, a.application_status,c.th_title_code,c.th_first_name,c.th_last_name, a.create_date, a.update_date");
			sql.append(" from orig_application a,");
			sql.append("     orig_application_log b,");
			sql.append("     orig_personal_info c");
			sql.append(" where a.application_record_id = b.application_record_id");
			sql.append(" and a.update_date < (sysdate - ? / 24)");
			sql.append(" and a.application_record_id = c.application_record_id");
			sql.append(" and c.personal_type = ?");
			sql.append(" and exists(select 'x'");
			sql.append("		from team_member tm");
			sql.append("		where upper(tm.member_id) = upper(a.cmr_first_id)");
			sql.append("		and (upper(tm.member_id) = upper(?)");
			sql.append("		or exists (select 'x'");
			sql.append("		from team_member tmt");
			sql.append("		WHERE upper(tmt.member_id) = upper(?)");
			sql.append("		and tmt.leader_flag = 'Y'");
			sql.append("		and tmt.team_id = tm.team_id)))");
			sql.append(" and a.job_state = ?");
			sql.append(" and b.application_status = ?");
			sql.append(" and b.log_seq =");
			sql.append("                (select max (al.log_seq)");
			sql.append("                   from orig_application_log al");
			sql.append("                  where al.application_record_id = a.application_record_id)");
			sql.append(" order by a.create_date");
			
			String dSql = String.valueOf(sql);
//			logger.debug("Sql="+dSql);
//			logger.debug("qName="+qName);
//			logger.debug("status="+status);
//			logger.debug("action="+action);
//			logger.debug("jobState="+jobState);
//			logger.debug("userName="+userName);
			ps = conn.prepareStatement(dSql);
			rs = null;
			
			ps.setDouble(1, time);
			ps.setString(2, OrigConstant.PERSONAL_TYPE_APPLICANT);
			ps.setString(3, userName);
			ps.setString(4, userName);
			ps.setString(5, jobState);
			ps.setString(6, status);
			
			
			rs = ps.executeQuery();
			Vector result = new Vector();
			SLADataM dataM;
			int recordCount = 0;
			while (rs.next() && rs.getRow()<6) {
//				logger.debug("rs.getRow = "+rs.getRow());
				dataM = new SLADataM();
				dataM.setAppNo(rs.getString(1));
				dataM.setAppStatus(rs.getString(2));
				dataM.setTitleName(rs.getString(3));
				dataM.setFirstName(rs.getString(4));
				dataM.setLastName(rs.getString(5));
				dataM.setCreateDate(rs.getTimestamp(6));
				dataM.setUpdateDate(rs.getTimestamp(7));
				result.add(dataM);
				recordCount++;
			}
//			logger.debug("rs = "+rs);
			if(rs.getRow() == 6){
				recordCount++;
			}
			while(rs.next()){
				/*if(rs.getRow() == 7){
					recordCount++;
				}*/
				recordCount++;
			}
			//int recordCount = rs.getFetchSize();
//			logger.debug("recordCount = "+recordCount);
			result.add(String.valueOf(recordCount));
			
			return result;
			
		} catch (Exception e) {
			logger.error("Error >> ", e);
			throw new EjbUtilException(e.getMessage());
		} finally {
			try {
				closeConnection(conn, rs, ps);
			} catch (Exception e) {
				logger.error("Error >> ", e);
			}
		}
	}
	
	public Vector getSLAForUWCMR(String qName, String status, String action, String jobState, String userName, double time) throws EjbUtilException{
		PreparedStatement ps = null;
		ResultSet rs = null;Connection conn = null;
		try {
			//conn = Get Connection
			conn = getConnection();
			StringBuilder sql = new StringBuilder("");
			
			sql.append("select distinct a.application_no, a.application_status,c.th_title_code,c.th_first_name,c.th_last_name, l.internal_ckecker, l.marketing_code,a.create_date,a.update_date");
			sql.append(" from orig_application a,");
			sql.append("     orig_application_log b,");
			sql.append("	 orig_personal_info c,");
			sql.append("     orig_loan l ");
			sql.append(" where a.application_record_id = b.application_record_id");
			sql.append(" and a.update_date < (sysdate - ? / 24)");
			sql.append(" and a.application_record_id = c.application_record_id");
			sql.append(" and c.personal_type = ?");
			sql.append(" and a.application_record_id = l.application_record_id");
			sql.append(" and l.marketing_code in (select m.LOAN_OFFICER from MS_LOAN_OFFICER m where exists(select 'x'");
			sql.append("							from team_member tm");
			sql.append("							where upper(tm.member_id) = upper(a.cmr_first_id)");
			sql.append("							and (upper(tm.member_id) = upper(?)");
			sql.append("							or exists (select 'x'");
			sql.append("							from team_member tmt");
			sql.append("							where upper(tmt.member_id) = upper(?)");
			sql.append("							and tmt.leader_flag = 'Y'");
			sql.append("							and tmt.team_id = tm.team_id))))");
			sql.append(" and a.job_state = ?");
			sql.append(" and b.action = ?");
			sql.append(" and b.log_seq =");
			sql.append("                (select max (log_seq)");
			sql.append("                   from orig_application_log c");
			sql.append("                  where c.application_record_id = a.application_record_id)");
			
			sql.append(" union");
			
			sql.append(" select   a.application_no, a.application_status, c.th_title_code,c.th_first_name,c.th_last_name,");
			sql.append(" l.internal_ckecker, l.marketing_code, a.create_date, a.update_date");
			sql.append(" from orig_application a,");
			sql.append(" orig_application_log b,");
			sql.append(" orig_personal_info c,");
			sql.append(" orig_loan l");
			sql.append(" where a.application_record_id = b.application_record_id");
			sql.append(" and a.update_date < (sysdate - ? / 24)");
			sql.append(" and a.application_record_id = c.application_record_id");
			sql.append(" and c.personal_type = ?");
			sql.append(" and a.application_record_id = l.application_record_id");
			sql.append(" and l.marketing_code in (");
			sql.append("     select m.LOAN_OFFICER ");
			sql.append("     from MS_LOAN_OFFICER m");
			sql.append("     where exists (");
			sql.append("              select 'x'");
			sql.append("                from team_member tm");
			sql.append("               where upper(tm.member_id) = upper(a.cmr_first_id)");
			sql.append("                 and (   upper(tm.member_id) = upper(?)");
			sql.append("                      or exists (");
			sql.append("                            select 'x'");
			sql.append("                             from team_member tmt");
			sql.append("                             where upper(tmt.member_id) = upper(?)");
			sql.append("                               and tmt.leader_flag = 'Y'");
			sql.append("                               and tmt.team_id = tm.team_id)");
			sql.append("                    )))");
			sql.append("and a.job_state = ?");
			sql.append("and b.action = ?");
			sql.append("and b.log_seq =");
			sql.append("           (select max (log_seq)");
			sql.append("              from orig_application_log c");
			sql.append("              where c.application_record_id = a.application_record_id)");

			sql.append(" order by create_date");
			
			String dSql = String.valueOf(sql);
			
//			logger.debug("Sql="+dSql);
//			logger.debug("qName="+qName);
//			logger.debug("status="+status);
//			logger.debug("action="+action);
//			logger.debug("jobState="+jobState);
//			logger.debug("userName="+userName);
			
			ps = conn.prepareStatement(dSql);
			rs = null;
			
			ps.setDouble(1, time);
			ps.setString(2, OrigConstant.PERSONAL_TYPE_APPLICANT);
			ps.setString(3, userName);
			ps.setString(4, userName);
			ps.setString(5, jobState);
			ps.setString(6, action);
			
			ps.setDouble(7, time);
			ps.setString(8, OrigConstant.PERSONAL_TYPE_APPLICANT);
			ps.setString(9, userName);
			ps.setString(10, userName);
			ps.setString(11, jobState);
			ps.setString(12, action);
			
			rs = ps.executeQuery();
			Vector result = new Vector();
			SLADataM dataM;
			int recordCount = 0;
			while (rs.next() && rs.getRow()<6) {
//				logger.debug("rs.getRow = "+rs.getRow());
				dataM = new SLADataM();
				dataM.setAppNo(rs.getString(1));
				dataM.setAppStatus(rs.getString(2));
				dataM.setTitleName(rs.getString(3));
				dataM.setFirstName(rs.getString(4));
				dataM.setLastName(rs.getString(5));
				dataM.setInernalCheckCode(rs.getString(6));
				dataM.setMktCode(rs.getString(7));
				dataM.setCreateDate(rs.getTimestamp(8));
				dataM.setUpdateDate(rs.getTimestamp(9));
				result.add(dataM);
				recordCount++;
			}
//			logger.debug("rs = "+rs);
			if(rs.getRow() == 6){
				recordCount++;
			}
			while(rs.next()){
				/*if(rs.getRow() == 7){
					recordCount++;
				}*/
				recordCount++;
			}
			//int recordCount = rs.getFetchSize();
			logger.debug("recordCount = "+recordCount);
			result.add(String.valueOf(recordCount));
			
			return result;
			
		} catch (Exception e) {
			logger.error("Error >> ", e);
			throw new EjbUtilException(e.getMessage());
		} finally {
			try {
				closeConnection(conn, rs, ps);
			} catch (Exception e) {
				logger.error("Error >> ", e);
			}
		}
	}
	
	public String getMainApplicatinRecordIdByApplicaionNo(String applicationNo) throws EjbUtilException{
		PreparedStatement ps = null;
		ResultSet rs = null;Connection conn = null;
		try {
			//conn = Get Connection
			conn = getConnection();
			StringBuilder sql = new StringBuilder("");
			sql.append("SELECT APPLICATION_RECORD_ID");
			sql.append(" FROM ORIG_APPLICATION WHERE  APPLICATION_NO=? ");
			String dSql = String.valueOf(sql);
//			logger.debug("Sql="+dSql);
			ps = conn.prepareStatement(dSql);
			rs = null;
			ps.setString(1, applicationNo);			 
			rs = ps.executeQuery();
			String result = null;
			if (rs.next()) {
				result = rs.getString(1);
			}
			return result;
		} catch (Exception e) {
			logger.error("Error >> ", e);
			throw new EjbUtilException(e.getMessage());
		} finally {
			try {
				closeConnection(conn, rs, ps);
			} catch (Exception e) {
				logger.error("Error >> ", e);
			}
		}
	}
	
	public Vector getCMRUser(String userName) throws EjbUtilException{
		PreparedStatement ps = null;
		ResultSet rs = null;Connection conn = null;
		try {
			//conn = Get Connection
			conn = getConnection();
			StringBuilder sql = new StringBuilder("");
			sql.append("select distinct t.member_id, u.email_address, u.contact_channel, u.mobilephone, t.leader_flag");
			sql.append(" from team_member t, hptbhp31 c, us_user_detail u");
			sql.append(" where t.leader_flag = 'Y'");
			sql.append(" and t.team_id in (select m.team_id");
			sql.append("                   from team_member m");
			sql.append("				   where  upper(m.member_id) = upper(c.userid))");
			sql.append(" and  c.loanoff =?");
			sql.append(" and upper(u.user_name) = upper(t.member_id)");
			sql.append(" union");
			sql.append(" select c.userid, u.email_address, u.contact_channel, u.mobilephone, 'x'");
			sql.append(" from hptbhp31 c, us_user_detail u");
			sql.append(" where  c.loanoff  = ?");
			sql.append(" and upper(u.user_name) = upper(c.userid)");
			
			String dSql = String.valueOf(sql);
//			logger.debug("Sql="+dSql);
			ps = conn.prepareStatement(dSql);
			rs = null;
			ps.setString(1, userName);
			ps.setString(2, userName);
			rs = ps.executeQuery();
			UserDetailM userDetailM;
			Vector result = new Vector();
			while (rs.next()) {
				userDetailM = new UserDetailM();
				userDetailM.setUserName(rs.getString(1));
				userDetailM.setEmail(rs.getString(2));
				userDetailM.setCommunicate_channel(rs.getString(3));
				userDetailM.setMobilePhone(rs.getString(4));
				if(!"Y".equals(rs.getString(5))){
					userDetailM.setIs_valid(true);
				}
				result.add(userDetailM);
			}
			return result;
		} catch (Exception e) {
			logger.error("Error >> ", e);
			throw new EjbUtilException(e.getMessage());
		} finally {
			try {
				closeConnection(conn, rs, ps);
			} catch (Exception e) {
				logger.error("Error >> ", e);
			}
		}
	}
	
	public String getAppRecordForCreateDrawDown(String personalID) throws EjbUtilException{
		PreparedStatement ps = null;
		ResultSet rs = null;Connection conn = null;
		try {
			//conn = Get Connection
			conn = getConnection();
			StringBuilder sql = new StringBuilder("");
			sql.append("select p.application_record_id");
			sql.append(" from orig_proposal p");
			sql.append(" where p.client_group = (select c.cltgrp");
			sql.append("						 from hpmshp00 c");
			sql.append("						 where c.idno = ?)");
			sql.append(" and p.update_date = (select max(op.update_date)");
			sql.append(" 				  	  from orig_proposal op");
			sql.append("					  where op.client_group = p.client_group)");	
			
			String dSql = String.valueOf(sql);
//			logger.debug("Sql="+dSql);
			ps = conn.prepareStatement(dSql);
			rs = null;
			ps.setString(1, personalID);
			rs = ps.executeQuery();
			
			String appRecordID = null;
			if (rs.next()) {
				appRecordID = rs.getString(1);
			}
			return appRecordID;
		} catch (Exception e) {
			logger.error("Error >> ", e);
			throw new EjbUtilException(e.getMessage());
		} finally {
			try {
				closeConnection(conn, rs, ps);
			} catch (Exception e) {
				logger.error("Error >> ", e);
			}
		}
	}
	
	public String getPersonIdAndAppRecordIDForCreateDrawDown(String personalID) throws EjbUtilException{
		PreparedStatement ps = null;
		ResultSet rs = null;Connection conn = null;
		try {
			//conn = Get Connection
			conn = getConnection();
			StringBuilder sql = new StringBuilder("");
			sql.append("select c.idno, c.application_record_id, c.customer_type");
			sql.append("  from orig_application_customer c");
			sql.append(" where c.application_record_id =");
			sql.append("          (select p.application_record_id");
			sql.append("             from orig_proposal p");
			sql.append("           where p.client_group = (select c.cltgrp");
			sql.append("                                      from hpmshp00 c");
			sql.append("                                     where c.idno = ?)");
			sql.append("             and p.update_date = (select max(op.update_date)");
			sql.append("				  	 from orig_proposal op");
			sql.append("					 where p.client_group = op.client_group))");
			
			String dSql = String.valueOf(sql);
//			logger.debug("Sql="+dSql);
			ps = conn.prepareStatement(dSql);
			rs = null;
			ps.setString(1, personalID);
			rs = ps.executeQuery();
			
			StringBuilder result = new StringBuilder("");
			if (rs.next()) {
				result.append(rs.getString(1));
				result.append(",");
				result.append(rs.getString(2));
				result.append(",");
				result.append(rs.getString(3));
			}
			return result.toString();
		} catch (Exception e) {
			logger.error("Error >> ", e);
			throw new EjbUtilException(e.getMessage());
		} finally {
			try {
				closeConnection(conn, rs, ps);
			} catch (Exception e) {
				logger.error("Error >> ", e);
			}
		}
	}
	
	public String getTitleDescription(String code) throws EjbUtilException{
		PreparedStatement ps = null;
		ResultSet rs = null;Connection conn = null;
		try {
			//conn = Get Connection
			conn = getConnection();
			StringBuilder sql = new StringBuilder("");
			sql.append("SELECT TH_DESC");
			sql.append(" FROM MS_TITLE_NAME WHERE TITLE_CODE = ? ");
			String dSql = String.valueOf(sql);
//			logger.debug("Sql="+dSql);
			ps = conn.prepareStatement(dSql);
			rs = null;
			ps.setString(1, code);

			rs = ps.executeQuery();
			String result = null;
			if (rs.next()) {
				result = rs.getString(1);
			}
			return result;
		} catch (Exception e) {
			logger.error("Error >> ", e);
			throw new EjbUtilException(e.getMessage());
		} finally {
			try {
				closeConnection(conn, rs, ps);
			} catch (Exception e) {
				logger.error("Error >> ", e);
			}
		}
	}
	public String getAreaDescription(String code, String userId) throws EjbUtilException{
		PreparedStatement ps = null;
		ResultSet rs = null;Connection conn = null;
		try {
			//conn = Get Connection
			conn = getConnection();
			StringBuilder sql = new StringBuilder("");
			sql.append("SELECT A.TH_DESC ");
			sql.append("FROM MS_OFFICE_AREA A, USER_BRANCH U ");
			sql.append("WHERE A.AREA = U.AREA ");
			sql.append("AND UPPER(U.USER_ID) = UPPER(?) AND A.AREA = ? ");
			
			/*sql.append("SELECT A.THDESC ");
			sql.append("FROM HPTBHP15 A, USER_BRANCH U ");
			sql.append("WHERE A.AREA = U.AREA ");
			sql.append("AND UPPER(U.USER_ID) = UPPER(?) AND A.CMPCDE = ? AND A.AREA = ?");*/
			String dSql = String.valueOf(sql);
			
//			logger.debug("Sql="+dSql);
//			logger.debug("code="+code);
//			logger.debug("userId="+userId);
			
			ps = conn.prepareStatement(dSql);
			rs = null;
			
			/*ps.setString(1, userId);
			ps.setString(2, OrigConstant.ORIG_CMPCODE);
			ps.setString(3, code);*/

			ps.setString(1, userId);
			ps.setString(2, code);
			
			rs = ps.executeQuery();
			String result = null;
			if (rs.next()) {
				result = rs.getString(1);
			}
			return result;
		} catch (Exception e) {
			logger.error("Error >> ", e);
			throw new EjbUtilException(e.getMessage());
		} finally {
			try {
				closeConnection(conn, rs, ps);
			} catch (Exception e) {
				logger.error("Error >> ", e);
			}
		}
	}
	
	public Vector<CacheDataM> getAreaCodesLike(String code, String userId) throws EjbUtilException{
		PreparedStatement ps = null;
		ResultSet rs = null;
		Vector<CacheDataM> vResult = new Vector<CacheDataM>();	
		Connection conn = null;
		try {
			//conn = Get Connection
			conn = getConnection();
			StringBuilder sql = new StringBuilder("");
			sql.append("SELECT A.AREA, A.TH_DESC, ACTIVE_STATUS ");
			sql.append("FROM MS_OFFICE_AREA A, USER_BRANCH U ");
			sql.append("WHERE A.AREA = U.AREA ");
			sql.append("AND UPPER(U.USER_ID) = UPPER(?) AND UPPER(A.AREA) like ? ");
			sql.append("ORDER BY A.AREA ");
			String dSql = String.valueOf(sql);
			
//			logger.debug("Sql="+dSql);
//			logger.debug("code="+code);
//			logger.debug("userId="+userId);
			
			ps = conn.prepareStatement(dSql);
			rs = null;
			
			ps.setString(1, userId);
			ps.setString(2, "%"+code.toUpperCase().replace("%", "chr(37)")+"%");
			
			rs = ps.executeQuery();
			while(rs.next()) {
				CacheDataM  cacheDataM=new CacheDataM();
			    cacheDataM.setCode(rs.getString(1));
			    cacheDataM.setShortDesc(rs.getString(2));
			    cacheDataM.setActiveStatus(rs.getString(3).trim());
			    vResult.add(cacheDataM);
			}
			return vResult;
		} catch (Exception e) {
			logger.error("Error >> ", e);
			throw new EjbUtilException(e.getMessage());
		} finally {
			try {
				closeConnection(conn, rs, ps);
			} catch (Exception e) {
				logger.error("Error >> ", e);
			}
		}
	}
	
	public Vector<CacheDataM> getClientGroupsLike(String code) throws EjbUtilException{
		PreparedStatement ps = null;
		ResultSet rs = null;
		Vector<CacheDataM> vResult = new Vector<CacheDataM>();	
		Connection conn = null;
		try {
			//conn = Get Connection
			conn = getConnection();
			StringBuilder sql = new StringBuilder("");
			sql.append("SELECT CLENT_GROUP, TH_DESC, ACTIVE_STATUS ");
			sql.append("FROM MS_CLIENT_GROUP ");
			sql.append("WHERE UPPER(CLENT_GROUP) like ? ");
			sql.append("ORDER BY CLENT_GROUP ");
			String dSql = String.valueOf(sql);
			
//			logger.debug("Sql="+dSql);
//			logger.debug("code="+code);
			
			ps = conn.prepareStatement(dSql);
			rs = null;
			
			/*ps.setString(1, userId);
			ps.setString(2, OrigConstant.ORIG_CMPCODE);
			ps.setString(3, code);*/

			ps.setString(1, "%"+code.toUpperCase().replace("%", "chr(37)")+"%");
			
			rs = ps.executeQuery();
			while(rs.next()) {
				CacheDataM  cacheDataM=new CacheDataM();
			    cacheDataM.setCode(rs.getString(1));
			    cacheDataM.setShortDesc(rs.getString(2));
			    cacheDataM.setActiveStatus(rs.getString(3).trim());
			    vResult.add(cacheDataM);
			}
			return vResult;
		} catch (Exception e) {
			logger.error("Error >> ", e);
			throw new EjbUtilException(e.getMessage());
		} finally {
			try {
				closeConnection(conn, rs, ps);
			} catch (Exception e) {
				logger.error("Error >> ", e);
			}
		}
	}
	
	public Vector<CacheDataM> getCarCategorysLike(String code) throws EjbUtilException{
		PreparedStatement ps = null;
		ResultSet rs = null;
		Vector<CacheDataM> vResult = new Vector<CacheDataM>();	
		Connection conn = null;
		try {
			//conn = Get Connection
			conn = getConnection();
			StringBuilder sql = new StringBuilder("");
			sql.append("SELECT CATEGORY, TH_DESC, ACTIVE_STATUS ");
			sql.append("FROM MS_CAR_CATEGORY_TYPE ");
			sql.append("WHERE UPPER(CATEGORY) like ? ");
			sql.append("ORDER BY CATEGORY ");
			String dSql = String.valueOf(sql);
			
//			logger.debug("Sql="+dSql);
//			logger.debug("code="+code);
			
			ps = conn.prepareStatement(dSql);
			rs = null;
			
			ps.setString(1, "%"+code.toUpperCase().replace("%", "chr(37)")+"%");
			
			rs = ps.executeQuery();
			while(rs.next()) {
				CacheDataM  cacheDataM=new CacheDataM();
			    cacheDataM.setCode(rs.getString(1));
			    cacheDataM.setShortDesc(rs.getString(2));
			    cacheDataM.setActiveStatus(rs.getString(3).trim());
			    vResult.add(cacheDataM);
			}
			return vResult;
		} catch (Exception e) {
			logger.error("Error >> ", e);
			throw new EjbUtilException(e.getMessage());
		} finally {
			try {
				closeConnection(conn, rs, ps);
			} catch (Exception e) {
				logger.error("Error >> ", e);
			}
		}
	}
	
	public Vector<CacheDataM> getCarBrandsLike(String code) throws EjbUtilException{
		PreparedStatement ps = null;
		ResultSet rs = null;
		Vector<CacheDataM> vResult = new Vector<CacheDataM>();	
		Connection conn = null;
		try {
			//conn = Get Connection
			conn = getConnection();
			StringBuilder sql = new StringBuilder("");
			sql.append("SELECT BRAND, TH_DESC, ACTIVE_STATUS ");
			sql.append("FROM MS_CAR_BRAND ");
			sql.append("WHERE UPPER(BRAND) like ? ");
			sql.append("ORDER BY BRAND ");
			String dSql = String.valueOf(sql);
			
//			logger.debug("Sql="+dSql);
//			logger.debug("code="+code);
			
			ps = conn.prepareStatement(dSql);
			rs = null;
			
			ps.setString(1, "%"+code.toUpperCase().replace("%", "chr(37)")+"%");
			
			rs = ps.executeQuery();
			while(rs.next()) {
				CacheDataM  cacheDataM=new CacheDataM();
			    cacheDataM.setCode(rs.getString(1));
			    cacheDataM.setShortDesc(rs.getString(2));
			    cacheDataM.setActiveStatus(rs.getString(3).trim());
			    vResult.add(cacheDataM);
			}
			return vResult;
		} catch (Exception e) {
			logger.error("Error >> ", e);
			throw new EjbUtilException(e.getMessage());
		} finally {
			try {
				closeConnection(conn, rs, ps);
			} catch (Exception e) {
				logger.error("Error >> ", e);
			}
		}
	}
	
	public Vector<CacheDataM> getCarModelsLike(String code, String brand) throws EjbUtilException{
		PreparedStatement ps = null;
		ResultSet rs = null;
		Vector<CacheDataM> vResult = new Vector<CacheDataM>();	
		Connection conn = null;
		try {
			//conn = Get Connection
			conn = getConnection();
			StringBuilder sql = new StringBuilder("");
			sql.append("SELECT MODEL, TH_DESC, ACTIVE_STATUS ");
			sql.append("FROM MS_CAR_MODEL ");
			sql.append("WHERE BRAND = ? AND UPPER(MODEL) like ? ");
			sql.append("ORDER BY MODEL ");
			String dSql = String.valueOf(sql);
			
//			logger.debug("Sql="+dSql);
//			logger.debug("code="+code);
			
			ps = conn.prepareStatement(dSql);
			rs = null;
			
			ps.setString(1, brand);
			ps.setString(2, "%"+code.toUpperCase().replace("%", "chr(37)")+"%");
			
			rs = ps.executeQuery();
			while(rs.next()) {
				CacheDataM  cacheDataM=new CacheDataM();
			    cacheDataM.setCode(rs.getString(1));
			    cacheDataM.setShortDesc(rs.getString(2));
			    cacheDataM.setActiveStatus(rs.getString(3).trim());
			    vResult.add(cacheDataM);
			}
			return vResult;
		} catch (Exception e) {
			logger.error("Error >> ", e);
			throw new EjbUtilException(e.getMessage());
		} finally {
			try {
				closeConnection(conn, rs, ps);
			} catch (Exception e) {
				logger.error("Error >> ", e);
			}
		}
	}
	public Vector<CacheDataM> getCarColorsLike(String code) throws EjbUtilException{
		PreparedStatement ps = null;
		ResultSet rs = null;
		Connection conn = null;
		Vector<CacheDataM> vResult = new Vector<CacheDataM>();	
		try {
			//conn = Get Connection
			conn = getConnection();
			StringBuilder sql = new StringBuilder("");
			sql.append("SELECT COLOR, TH_DESC, ACTIVE_STATUS ");
			sql.append("FROM MS_CAR_COLOR ");
			sql.append("WHERE UPPER(COLOR) like ? ");
			sql.append("ORDER BY COLOR ");
			String dSql = String.valueOf(sql);
			
//			logger.debug("Sql="+dSql);
//			logger.debug("code="+code);
			
			ps = conn.prepareStatement(dSql);
			rs = null;
			
			ps.setString(1, "%"+code.toUpperCase().replace("%", "chr(37)")+"%");
			
			rs = ps.executeQuery();
			while(rs.next()) {
				CacheDataM  cacheDataM=new CacheDataM();
			    cacheDataM.setCode(rs.getString(1));
			    cacheDataM.setShortDesc(rs.getString(2));
			    cacheDataM.setActiveStatus(rs.getString(3).trim());
			    vResult.add(cacheDataM);
			}
			return vResult;
		} catch (Exception e) {
			logger.error("Error >> ", e);
			throw new EjbUtilException(e.getMessage());
		} finally {
			try {
				closeConnection(conn, rs, ps);
			} catch (Exception e) {
				logger.error("Error >> ", e);
			}
		}
	}
	
	public Vector<CacheDataM> getCarLicenseTypesLike(String code) throws EjbUtilException{
		PreparedStatement ps = null;
		ResultSet rs = null;
		Vector<CacheDataM> vResult = new Vector<CacheDataM>();	
		Connection conn = null;
		try {
			//conn = Get Connection
			conn = getConnection();
			StringBuilder sql = new StringBuilder("");
			sql.append("SELECT LICENSE_TYPE, TH_DESC, ACTIVE_STATUS ");
			sql.append("FROM MS_LICENSE_TYPE ");
			sql.append("WHERE UPPER(LICENSE_TYPE) like ? ");
			sql.append("ORDER BY LICENSE_TYPE ");
			String dSql = String.valueOf(sql);
			
//			logger.debug("Sql="+dSql);
//			logger.debug("code="+code);
			
			ps = conn.prepareStatement(dSql);
			rs = null;
			
			ps.setString(1, "%"+code.toUpperCase().replace("%", "chr(37)")+"%");
			
			rs = ps.executeQuery();
			while(rs.next()) {
				CacheDataM  cacheDataM=new CacheDataM();
			    cacheDataM.setCode(rs.getString(1));
			    cacheDataM.setShortDesc(rs.getString(2));
			    cacheDataM.setActiveStatus(rs.getString(3).trim());
			    vResult.add(cacheDataM);
			}
			return vResult;
		} catch (Exception e) {
			logger.error("Error >> ", e);
			throw new EjbUtilException(e.getMessage());
		} finally {
			try {
				closeConnection(conn, rs, ps);
			} catch (Exception e) {
				logger.error("Error >> ", e);
			}
		}
	}
	
	public Vector<CacheDataM> getCarProvincesLike(String code) throws EjbUtilException{
		PreparedStatement ps = null;
		ResultSet rs = null;
		Connection conn = null;
		Vector<CacheDataM> vResult = new Vector<CacheDataM>();	
		try {
			//conn = Get Connection
			conn = getConnection();
			StringBuilder sql = new StringBuilder("");
			sql.append("SELECT PROVINCE_ID, PROVINCE_DESC ");
			sql.append("FROM MS_PROVINCE ");
			sql.append("WHERE UPPER(PROVINCE_ID) like ? ");
			sql.append("ORDER BY PROVINCE_ID ");
			String dSql = String.valueOf(sql);
			
//			logger.debug("Sql="+dSql);
//			logger.debug("code="+code);
			
			ps = conn.prepareStatement(dSql);
			rs = null;
			
			ps.setString(1, "%"+code.toUpperCase().replace("%", "chr(37)")+"%");
			
			rs = ps.executeQuery();
			while(rs.next()) {
				CacheDataM  cacheDataM=new CacheDataM();
			    cacheDataM.setCode(rs.getString(1));
			    cacheDataM.setShortDesc(rs.getString(2));
			    cacheDataM.setActiveStatus("A");
			    vResult.add(cacheDataM);
			}
			return vResult;
		} catch (Exception e) {
			logger.error("Error >> ", e);
			throw new EjbUtilException(e.getMessage());
		} finally {
			try {
				closeConnection(conn, rs, ps);
			} catch (Exception e) {
				logger.error("Error >> ", e);
			}
		}
	}
	
	public Vector<CacheDataM> getMarketingCodesLike(String code) throws EjbUtilException{
		PreparedStatement ps = null;
		ResultSet rs = null;
		Connection conn = null;
		Vector<CacheDataM> vResult = new Vector<CacheDataM>();	
		try {
			//conn = Get Connection
			conn = getConnection();
			StringBuilder sql = new StringBuilder("");
			sql.append("SELECT LOAN_OFFICER, TH_NAME, ACTIVE_STATUS ");
			sql.append("FROM MS_LOAN_OFFICER ");
			sql.append("WHERE UPPER(LOAN_OFFICER) like ? ");
			sql.append(" ORDER BY LOAN_OFFICER ");
			String dSql = String.valueOf(sql);
			
//			logger.debug("Sql="+dSql);
//			logger.debug("code="+code);
			
			ps = conn.prepareStatement(dSql);
			rs = null;
			
			ps.setString(1, "%"+code.toUpperCase().replace("%", "chr(37)")+"%");
			
			rs = ps.executeQuery();
			while(rs.next()) {
				CacheDataM  cacheDataM=new CacheDataM();
			    cacheDataM.setCode(rs.getString(1));
			    cacheDataM.setShortDesc(rs.getString(2));
			    cacheDataM.setActiveStatus(rs.getString(3).trim());
			    vResult.add(cacheDataM);
			}
			return vResult;
		} catch (Exception e) {
			logger.error("Error >> ", e);
			throw new EjbUtilException(e.getMessage());
		} finally {
			try {
				closeConnection(conn, rs, ps);
			} catch (Exception e) {
				logger.error("Error >> ", e);
			}
		}
	}
	
	public IntSchemeCacheProperties getIntSchemeForCode(String schemeCode) throws EjbUtilException {
		PreparedStatement ps = null;
		ResultSet resultSet = null;
		IntSchemeCacheProperties data = new IntSchemeCacheProperties();	Connection conn = null;
		try {
			//conn = Get Connection
			conn = getConnection();
			StringBuilder sql = new StringBuilder("");
			sql.append("SELECT sch.SCHEME_CODE, sch.SCHEME_DESC, sch.INT_RATE1, sch.TERM1, sch.INT_RATE2, sch.TERM2, ");
			sql.append("sch.INT_RATE3, sch.TERM3, sch.INT_RATE4, sch.TERM4 ");
			sql.append("FROM INT_SCHEME sch ");
			sql.append("WHERE UPPER(sch.SCHEME_CODE) = ? ");
			String dSql = String.valueOf(sql);
			
//			logger.debug("Sql="+dSql);
//			logger.debug("code="+schemeCode);
			
			ps = conn.prepareStatement(dSql);
			resultSet = null;
			
			ps.setString(1, schemeCode.toUpperCase());
			
			resultSet = ps.executeQuery();
			while(resultSet.next()) {
				data.setSchemeCode( resultSet.getString("SCHEME_CODE") );
				data.setSchemeDesc( resultSet.getString("SCHEME_DESC") );
				data.setIntRate1( resultSet.getBigDecimal("INT_RATE1") );
				data.setIntRate2( resultSet.getBigDecimal("INT_RATE2") );
				data.setIntRate3( resultSet.getBigDecimal("INT_RATE3") );
				data.setIntRate4( resultSet.getBigDecimal("INT_RATE4") );
				
				data.setTerm1( resultSet.getInt("TERM1") );
				data.setTerm2( resultSet.getInt("TERM2") );
				data.setTerm3( resultSet.getInt("TERM3") );
				data.setTerm4( resultSet.getInt("TERM4") );
			}
			return data;
		} catch (Exception e) {
			logger.error("Error >> ", e);
			throw new EjbUtilException(e.getMessage());
		} finally {
			try {
				closeConnection(conn, resultSet, ps);
			} catch (Exception e) {
				logger.error("Error >> ", e);
			}
		}
	}
	
	public Vector getImageAttachedFiles(  String requestId)throws EjbUtilException {
		ResultSet rs = null;
		PreparedStatement ps=null;
		Vector vResult = new Vector();								Connection conn = null;
		try{
		    conn = getConnection();
			StringBuilder sql = new StringBuilder("");			
			sql.append("  Select REQUEST_ID,IA.ATTACH_ID,IA.FILE_TYPE_ID "); 
			sql.append("  From  IMG_REQUEST IR, IMG_ATTACH IA "); 
			sql.append("  Where REQUEST_ID =? And IA.IMG_REQUEST = REQUEST_ID ORDER BY IA.ATTACH_ID");
			String dSql = String.valueOf( sql);
//			logger.debug("dSql = " + dSql);
			ps = conn.prepareStatement(dSql);			
			ps.setString(1,requestId);
			rs = ps.executeQuery();
			while(rs.next()) {
				ImgAttachmentDataM attachDataM = new ImgAttachmentDataM();
				attachDataM.setAttachId(rs.getString("ATTACH_ID"));
				attachDataM.setAttachType(rs.getString("FILE_TYPE_ID"));
				String  attach_id=rs.getString("ATTACH_ID");				 				 												
				vResult.add(attachDataM);
			}				
		}catch(Exception e){
		    logger.error("Error >> ", e);
			throw new EjbUtilException(e.getMessage());
		}finally{
			try{	
				if(rs!=null){					
					rs.close();
				}
			}catch(Exception e){
				e.printStackTrace();
			}
			try{	
				if(ps!=null){					
					ps.close();
				}
			}catch(Exception e){
				e.printStackTrace();
			}
		}
		return vResult;			
	}
	public CampaignDataM getCampaign(String campaignCode) throws EjbUtilException{
	    CampaignDataM result=null;
	    PreparedStatement ps = null;
		ResultSet rs = null;Connection conn = null;
		try {
			//conn = Get Connection
			conn = getConnection();
			StringBuilder sql = new StringBuilder("");
			
			sql.append("SELECT  cmpcde, offcde, campcde, campdesc, effdte, expdte, mindownamt, ");
			sql.append("  maxterm, subsidydesc, rebatedesc, minrate, remark, recsts   "); 
			sql.append(" FROM  HPTBHP64 ");
			sql.append(" WHERE CMPCDE = ? ");
			sql.append(" AND CAMPCDE=?");
			String dSql = String.valueOf(sql);
			
//			logger.debug("Sql="+dSql);
//			logger.debug("Campaign code="+campaignCode);
	 
			ps = conn.prepareStatement(dSql);
			rs = null;			 
			ps.setString(1, OrigConstant.ORIG_CMPCODE);
			ps.setString(2, campaignCode);

			rs = ps.executeQuery();
			//String result = null;
			if (rs.next()) {
			    result=new CampaignDataM();
				result.setCAMPCDE(rs.getString(1));
				result.setOFFCDE(rs.getString(2));
				result.setCAMPCDE(rs.getString(3));
				result.setCAMPDESC(rs.getString(4));
				result.setEFFDTE(rs.getDate(5));
				result.setEXPDTE(rs.getDate(6));
				result.setMINDOWNAMT(rs.getBigDecimal(7));
				result.setMAXTERM(rs.getInt(8));
				result.setSUBSIDYDESC(rs.getString(9));
				result.setMINRATE(rs.getBigDecimal(10));
				result.setREMARK(rs.getString(11));
				result.setRECSTS(rs.getString(12));
			}
			return result;
		} catch (Exception e) {
			logger.error("Error >> ", e);
			throw new EjbUtilException(e.getMessage());
		} finally {
			try {
				closeConnection(conn, rs, ps);
			} catch (Exception e) {
				logger.error("Error >> ", e);
			}
		}
	    
	}

    /* (non-Javadoc)
     * @see com.eaf.orig.shared.dao.utility.UtilityDAO#getEscarateGroup(java.lang.String, boolean, java.lang.String, java.lang.String, java.lang.String, java.lang.String, java.math.BigDecimal, java.lang.String, java.math.BigDecimal, java.math.BigDecimal)
     */
    public Vector getEscarateGroup(String policyVersion, String customerType, String carType, String loanType, String scoringResult, BigDecimal financeAmount, String appType, BigDecimal installmentTerm, BigDecimal downPayment) throws EjbUtilException {
//        logger.debug("Get escerate Group");
//        logger.debug("Policy Version " + policyVersion );
        //logger.debug(" Policy not Fail "+ policyNotFail);
//        logger.debug(" Customer Type "+customerType);
//        logger.debug(" Car Type "+carType);
//        logger.debug(" Loan type "+ loanType);
//        logger.debug("scoringResult "+scoringResult);
//        logger.debug("financeAmount "+financeAmount);
//        logger.debug("appType "+appType);
//        logger.debug("installmentTerm "+installmentTerm);
//        logger.debug("downPayment "+downPayment);
        CampaignDataM result=null;
	    PreparedStatement ps = null;
		ResultSet rs = null;
		Vector vResult=new Vector();Connection conn = null;
		try {
			//conn = Get Connection
			conn = getConnection();
			StringBuilder sql = new StringBuilder("");
			
			sql.append("SELECT GROUP_NAME   ");
			sql.append(" FROM  APPROVAL_AUTHORITY ");
			sql.append(" WHERE POLICY_VERSION = ? ");
			sql.append(" AND LOAN_TYPE=?");
			sql.append(" AND CUSTOMER_TYPE=?");
			sql.append(" AND CAR_TYPE=?");
			sql.append(" AND SCORING_RESULT=?");
			//if(policyNotFail){
			   // sql.append(" AND ( CREDIT_APPROVAL >= ?  OR  CREDIT_APPROVAL=0)");
			     //if( OrigConstant.HAVE_GUARANTOR.equals(appType)){
			     //   sql.append(" AND ( ? >= MIN_DOWN_GUA OR MIN_DOWN_GUA=0 )");
			     //   sql.append(" AND  (? BETWEEN 	MIN_TERM_GUA AND MAX_TERM_GUA  OR MAX_TERM_GUA=0 )");
			    //    
			    // }else{
			    //   sql.append(" AND ( ? >= MIN_DOWN_NO_GUA OR MIN_DOWN_NO_GUA=0 ) ");
				//   sql.append(" AND (? BETWEEN 	MIN_TERM_NO_GUA AND MAX_TERM_NO_GUA  OR MAX_TERM_NO_GUA=0 )"); 
			   //  }
			
			//}else{
			 //   sql.append(" and (F_CREDIT_APPROVAL >= ? OR F_CREDIT_APPROVAL=0 ) ");
			  //  if( OrigConstant.HAVE_GUARANTOR.equals(appType)){
			  //      sql.append(" AND (? >= F_MIN_DOWN_GUA OR F_MIN_DOWN_GUA =0)");
			  //      sql.append(" AND (? BETWEEN 	F_MIN_TERM_GUA AND F_MAX_TERM_GUA  OR F_MAX_TERM_GUA=0)");
			  //   }else{
			 //       sql.append(" AND (? >= F_MIN_DOWN_NO_GUA OR F_MIN_DOWN_NO_GUA=0) ");
			//		sql.append(" AND ( ? BETWEEN 	F_MIN_TERM_NO_GUA AND F_MAX_TERM_NO_GUA OR F_MAX_TERM_NO_GUA=0 )");
			   //  }			
			//}
			sql.append( "  ORDER BY  GROUP_NAME ");
			String dSql = String.valueOf(sql);			
//			logger.debug("Sql="+dSql);
			//logger.debug("Campaign code="+campaignCode);
	 
			ps = conn.prepareStatement(dSql);
			rs = null;			 
			ps.setString(1, policyVersion);
			ps.setString(2,loanType);
			ps.setString(3,customerType);
			ps.setString(4,carType);
			ps.setString(5,scoringResult);
		    ps.setBigDecimal(6,financeAmount);
	        ps.setBigDecimal(7,downPayment);
	        ps.setBigDecimal(8,installmentTerm);
			rs = ps.executeQuery();
			//String result = null;
			while (rs.next()) {
			    CacheDataM  cacheDataM=new CacheDataM();
			    cacheDataM.setCode(rs.getString(1));
			    cacheDataM.setShortDesc(rs.getString(1));
			    vResult.add(cacheDataM);
			}
			 return vResult;
		} catch (Exception e) {
			logger.error("Error >> ", e);
			throw new EjbUtilException(e.getMessage());
		} finally {
			try {
				closeConnection(conn, rs, ps);
			} catch (Exception e) {
				logger.error("Error >> ", e);
			}
		}
                                                
    }

    public Vector getSLAForXUW(String qName, String status, String action, String jobState, String userName, double time) throws EjbUtilException{
		PreparedStatement ps = null;
		ResultSet rs = null;Connection conn = null;
		try {
			//conn = Get Connection
			conn = getConnection();
			StringBuilder sql = new StringBuilder("");
			
//			sql.append("select distinct  a.application_no, a.application_status,c.titcde,c.thname,c.thsurn, l.internal_ckecker, l.marketing_code, a.create_date, a.update_date");
//			sql.append(" from orig_application a,");
//			sql.append("     orig_application_log b,");
//			sql.append("     hpmshp00 c,");
//			sql.append("     orig_loan l,");
//			sql.append("     orig_application_customer oc");
//			sql.append(" where a.application_record_id = b.application_record_id");
//			sql.append(" and a.update_date < (sysdate - ? / 24)");
//			sql.append(" and a.application_record_id = oc.application_record_id");
//			sql.append(" and oc.idno = c.idno");
//			sql.append(" and oc.personal_type = ?");
//			sql.append(" and l.application_record_id = a.application_record_id");
//			sql.append(" and a.job_state = ?");
//			sql.append(" and b.action = ?");
//			sql.append(" and b.log_seq =");
//			sql.append("                (select max (log_seq)");
//			sql.append("                   from orig_application_log c");
//			sql.append("                  where c.application_record_id = a.application_record_id)");
//			sql.append(" order by a.create_date");
			
			sql.append(" SELECT DISTINCT a.application_no,a.application_status,c.th_title_code,c.th_first_name," +
							"c.th_last_name,l.internal_ckecker,l.marketing_code,a.create_date,a.update_date ");
			sql.append(" FROM orig_application a, orig_application_log b, orig_personal_info c, orig_loan l ");
			sql.append(" WHERE a.application_record_id = b.application_record_id ");
			sql.append(" AND a.update_date < (sysdate - ? / 24) ");
			sql.append(" AND a.application_record_id = C.APPLICATION_RECORD_ID ");
			sql.append(" AND c.personal_type = ? ");
			sql.append(" AND l.application_record_id = a.application_record_id ");
			sql.append(" AND a.job_state = ? ");
			sql.append(" AND b.action = ? ");
			sql.append(" AND b.log_seq = " +
						" ( SELECT MAX (log_seq) FROM orig_application_log c "+
						"  WHERE c.application_record_id = a.application_record_id "+
						"  ) ORDER BY a.create_date ");
			
			
			String dSql = String.valueOf(sql);
//			logger.debug("Sql="+dSql);
//			logger.debug("qName="+qName);
//			logger.debug("status="+status);
//			logger.debug("action="+action);
//			logger.debug("jobState="+jobState);
			ps = conn.prepareStatement(dSql);
			rs = null;
			
			ps.setDouble(1, time);
			ps.setString(2, OrigConstant.PERSONAL_TYPE_APPLICANT);
			ps.setString(3, jobState);
			ps.setString(4, action);
			

			rs = ps.executeQuery();
			Vector result = new Vector();
			SLADataM dataM;
			int recordCount = 0;
			while (rs.next() && rs.getRow()<6) {
//				logger.debug("rs.getRow = "+rs.getRow());
				dataM = new SLADataM();
				dataM.setAppNo(rs.getString(1));
				dataM.setAppStatus(rs.getString(2));
				dataM.setTitleName(rs.getString(3));
				dataM.setFirstName(rs.getString(4));
				dataM.setLastName(rs.getString(5));
				dataM.setInernalCheckCode(rs.getString(6));
				dataM.setMktCode(rs.getString(7));
				dataM.setCreateDate(rs.getTimestamp(8));
				dataM.setUpdateDate(rs.getTimestamp(9));
				result.add(dataM);
				recordCount++;
			}
//			logger.debug("rs = "+rs);
			if(rs.getRow() == 6){
				recordCount++;
			}
			while(rs.next()){
				/*if(rs.getRow() == 7){
					recordCount++;
				}*/
				recordCount++;
			}
			//int recordCount = rs.getFetchSize();
//			logger.debug("recordCount = "+recordCount);
			result.add(String.valueOf(recordCount));
			
			return result;
			
		} catch (Exception e) {
			logger.error("Error >> ", e);
			throw new EjbUtilException(e.getMessage());
		} finally {
			try {
				closeConnection(conn, rs, ps);
			} catch (Exception e) {
				logger.error("Error >> ", e);
			}
		}
	}
	public Vector getAllUserNameXUW() throws EjbUtilException {
		Vector userNameVect = new Vector() ;
		PreparedStatement ps = null;
		ResultSet rs = null;Connection conn = null;
		try {
			//conn = Get Connection
			conn = getConnection();
			StringBuilder sql = new StringBuilder("");
			
			sql.append(" SELECT DISTINCT U.USER_NAME ");
			sql.append("    FROM US_USER_DETAIL U, USER_ROLE UR, ROLE R ");
			sql.append("   WHERE UPPER (U.USER_NAME) = UPPER (UR.USER_NAME) ");
			sql.append("   	AND UR.ROLE_ID  = R.ROLE_ID ");
			sql.append("     AND R.ROLE_NAME = 'XUW' ");
			sql.append(" ORDER BY UPPER (USER_NAME) ");
			
			String dSql = String.valueOf(sql);
//			logger.debug("Sql=" + dSql);
			ps = conn.prepareStatement(dSql);
			rs = null;

			rs = ps.executeQuery();
			
			while(rs.next()) {
				userNameVect.add(rs.getString(1));
			}
			return userNameVect;

		} catch (Exception e) {
		    logger.fatal("",e);
			throw new EjbUtilException(e.getMessage());
		} finally {
			try {
				closeConnection(conn, rs, ps);
			} catch (Exception e) {
			    logger.fatal("",e);
			}
		}
	}

    /* (non-Javadoc)
     * @see com.eaf.orig.shared.dao.utility.UtilityDAO#beforeCallReportCAApp(java.sql.Date, java.sql.Date)
     */
    public void beforeCallReportCAApp(Date fromDate, Date toDate) throws EjbUtilException {
    	CallableStatement myStmt = null;				
    	Connection conn = null;
		try{
		 
//			logger.debug("fromDate = "+fromDate);
//			logger.debug("toDate = "+toDate);
			
			conn = getConnection();
			
			String sql = "{call BEFORE_CALL_CA_APP(?, ?) }";
			
			myStmt = conn.prepareCall(sql);	
			
			myStmt.setDate(1, fromDate);
			myStmt.setDate(2, toDate);
	 
		
			myStmt.execute();
						
		}catch (Exception e) {
			logger.fatal("[beforeCallReportCAApp]....Error >> " + e);			
			throw new EjbUtilException(e.getMessage());
		}finally{
			try{
				this.closeConnection(conn, myStmt);
			}catch (Exception e) {
				logger.fatal("[beforeCallReportCAApp]....Error >> " + e);
			}
		}
        
    }

    /* (non-Javadoc)
     * @see com.eaf.orig.shared.dao.utility.UtilityDAO#beforeCallReportCADecision(java.lang.String, java.lang.String, java.lang.String, java.lang.String, java.sql.Date, java.sql.Date)
     */
    public void beforeCallReportCADecision(String fromOfficer, String toOfficer, String fromCACode, String toCACode, Date fromDate, Date toDate) throws EjbUtilException {
    	CallableStatement myStmt = null;				
    	Connection conn = null;
		try{
		    
//		    logger.debug("fromOfficer = "+fromOfficer);
//			logger.debug("toOfficer = "+toOfficer);		
//			logger.debug("fromCACode = "+fromCACode);
//		    logger.debug("toCACode = "+toCACode);		    
//		    logger.debug("fromDate = "+fromDate);
//			logger.debug("toDate = "+toDate);
			
			conn = getConnection();
			
			String sql = "{call BEFORE_CALL_CA_DECIS(?, ?, ?, ?, ?, ?) }";
			
			myStmt = conn.prepareCall(sql);	
			
			myStmt.setString(1, fromOfficer);
			myStmt.setString(2, toOfficer);
			myStmt.setString(3, fromCACode);
			myStmt.setString(4, toCACode);
			myStmt.setDate(5, fromDate);
			myStmt.setDate(6, toDate);			 			
			myStmt.execute();
						
		}catch (Exception e) {
			logger.fatal("[BEFORE_CALL_CA_DECIS]....Error >> " + e);			
			throw new EjbUtilException(e.getMessage());
		}finally{
			try{
				this.closeConnection(conn, myStmt);
			}catch (Exception e) {
				logger.fatal("[BEFORE_CALL_CA_DECIS]....Error >> " + e);
			}
		}
        
    }

    /* (non-Javadoc)
     * @see com.eaf.orig.shared.dao.utility.UtilityDAO#beforeCallReportCAOveride(java.lang.String, java.lang.String, java.lang.String, java.lang.String, java.sql.Date, java.sql.Date)
     */
    public void beforeCallReportCAOveride(String fromOfficer, String toOfficer, String fromCACode, String toCACode, Date fromDate, Date toDate) throws EjbUtilException {
    	CallableStatement myStmt = null;				
    	Connection conn = null;
		try{
		    
//		    logger.debug("fromOfficer = "+fromOfficer);
//			logger.debug("toOfficer = "+toOfficer);		
//			logger.debug("fromCACode = "+fromCACode);
//		    logger.debug("toCACode = "+toCACode);		    
//		    logger.debug("fromDate = "+fromDate);
//			logger.debug("toDate = "+toDate);			
			conn = getConnection();
			
			String sql = "{call BEFORE_CALL_CA_OVERIDE(?, ?, ?, ?, ?, ?) }";			
			myStmt = conn.prepareCall(sql);				
			myStmt.setString(1, fromOfficer);
			myStmt.setString(2, toOfficer);
			myStmt.setString(3, fromCACode);
			myStmt.setString(4, toCACode);
			myStmt.setDate(5, fromDate);
			myStmt.setDate(6, toDate);			 			
			myStmt.execute();
		 						
		}catch (Exception e) {
			logger.fatal("[BEFORE_CALL_CA_OVERIDE]....Error >> " + e);			
			throw new EjbUtilException(e.getMessage());
		}finally{
			try{
				this.closeConnection(conn, myStmt);
			}catch (Exception e) {
				logger.fatal("[BEFORE_CALL_CA_OVERIDE]....Error >> " + e);
			}
		}
        
    }

    /* (non-Javadoc)
     * @see com.eaf.orig.shared.dao.utility.UtilityDAO#getPolicyGroup(java.lang.String)
     */
    public Vector getPolicyGroup(String policyVersion) throws EjbUtilException {
        PreparedStatement ps = null;
		ResultSet rs = null;
		Vector  result = new Vector();Connection conn = null;
		try {
			//conn = Get Connection
			conn = getConnection();
			StringBuilder sql = new StringBuilder("");
			sql.append("SELECT distinct a.GROUP_NAME from APPROVAL_AUTHORITY a");
			sql.append(" WHERE a.POLICY_VERSION=?  ORDER BY 1");
			String dSql = String.valueOf(sql);
//			logger.debug("Sql="+dSql);
			ps = conn.prepareStatement(dSql);
			rs = null;
			ps.setString(1,policyVersion  );			 
			rs = ps.executeQuery();			
			while (rs.next()) {
			    CacheDataM  cacheDataM=new CacheDataM();
			    cacheDataM.setCode( rs.getString(1));			    
			    result.add(cacheDataM);
			}
			return result;
		} catch (Exception e) {
			logger.error("Error >> ", e);
			throw new EjbUtilException(e.getMessage());
		} finally {
			try {
				closeConnection(conn, rs, ps);
			} catch (Exception e) {
				logger.error("Error >> ", e);
			}
		}
       // return result;
    }
    
    public BigDecimal getLendingLimit(String username, String busID) throws EjbUtilException {
        PreparedStatement ps = null;
		ResultSet rs = null;
		Vector  result = new Vector();
		Connection conn = null;
		try {
			//conn = Get Connection
			conn = getConnection();
			StringBuilder sql = new StringBuilder("");
			sql.append("SELECT u.LENDING_LIMIT");
			sql.append(" FROM user_profile u, profile_bus_grp p, buss_class_grp_map bc");
			sql.append(" WHERE u.PROFILE_ID = p.PROFILE_ID");
			sql.append(" and p.BUS_GRP_ID = bc.BUSS_GRP_ID");
			sql.append(" and ? in bc.BUS_CLASS_ID");
			sql.append(" and u.USER_NAME = ? ");
			String dSql = String.valueOf(sql);
//			logger.debug("Sql="+dSql);
			
			ps = conn.prepareStatement(dSql);
			rs = null;
			ps.setString(1,busID);
			ps.setString(2,username);
			
			rs = ps.executeQuery();
			rs.next();
			
			return rs.getBigDecimal(1);
		} catch (Exception e) {
			logger.error("Error >> ", e);
			throw new EjbUtilException(e.getMessage());
		} finally {
			try {
				closeConnection(conn, rs, ps);
			} catch (Exception e) {
				logger.error("Error >> ", e);
			}
		}
       // return result;
    }
    
	public Vector getRekeyFields() throws EjbUtilException {
		Vector result = new Vector() ;
		PreparedStatement ps = null;
		ResultSet rs = null;
		Connection conn = null;
		try {
			//conn = Get Connection
			conn = getConnection();
			StringBuilder sql = new StringBuilder("");
			
			sql.append(" SELECT PK_REKEY, FIELD_ID, SEQ FROM ORIG_REKEY_FIELD WHERE SHOW_FLAG ='Y' ORDER BY SEQ ");
			
			String dSql = String.valueOf(sql);
//			logger.debug("Sql=" + dSql);
			ps = conn.prepareStatement(dSql);
			rs = null;

			rs = ps.executeQuery();
			
			while(rs.next()) {
				RekeyDataM rekeyM = new RekeyDataM();
				rekeyM.setPkRekey(rs.getString("PK_REKEY"));
				rekeyM.setFieldID(rs.getString("FIELD_ID"));
				rekeyM.setSeq(rs.getInt("SEQ"));
				result.addElement(rekeyM);
			}
			return result;

		} catch (Exception e) {
		    logger.fatal("",e);
			throw new EjbUtilException(e.getMessage());
		} finally {
			try {
				closeConnection(conn, rs, ps);
			} catch (Exception e) {
			    logger.fatal("",e);
			}
		}
	}
 
	
	public Vector getAppNumberByGroupID(String groupID,String appRecordID) throws EjbUtilException {
		Vector result = new Vector() ;
		PreparedStatement ps = null;
		ResultSet rs = null;
		Connection conn = null;
		try {
			//conn = Get Connection
			conn = getConnection();
			StringBuilder sql = new StringBuilder("");
			
			sql.append(" SELECT APPLICATION_NO FROM ORIG_APPLICATION  WHERE GROUP_APP_ID = ? AND APPLICATION_RECORD_ID <> ? ");
			
			String dSql = String.valueOf(sql);
//			logger.debug("Sql=" + dSql);
			ps = conn.prepareStatement(dSql);
			ps.setString(1,groupID);
			ps.setString(2,appRecordID);
			rs = null;
			
			rs = ps.executeQuery();
			
			while(rs.next()) {
			    String appNo = rs.getString("APPLICATION_NO");
			    result.addElement(appNo);
			}
			return result;

		} catch (Exception e) {
		    logger.fatal("",e);
			throw new EjbUtilException(e.getMessage());
		} finally {
			try {
				closeConnection(conn, rs, ps);
			} catch (Exception e) {
			    logger.fatal("",e);
			}
		}
	}

	@Override
	public String getProvinceDescription(String code) throws EjbUtilException {
		PreparedStatement ps = null;
		ResultSet rs = null;Connection conn = null;
		try {
			//conn = Get Connection
			conn = getConnection();
			StringBuilder sql = new StringBuilder("");
			sql.append("SELECT PROVINCE_DESC");
			sql.append(" FROM MS_PROVINCE WHERE PROVINCE_ID = ? ");
			String dSql = String.valueOf(sql);
//			logger.debug("Sql="+dSql);
			ps = conn.prepareStatement(dSql);
			rs = null;
			ps.setString(1, code);

			rs = ps.executeQuery();
			String result = null;
			if (rs.next()) {
				result = rs.getString(1);
			}
			return result;
		} catch (Exception e) {
			logger.error("Error >> ", e);
			throw new EjbUtilException(e.getMessage());
		} finally {
			try {
				closeConnection(conn, rs, ps);
			} catch (Exception e) {
				logger.error("Error >> ", e);
			}
		}
	}

	@Override
	public String getAmphurDescription(String code) throws EjbUtilException {
		PreparedStatement ps = null;
		ResultSet rs = null;
		Connection conn = null;
		try {
			//conn = Get Connection
			conn = getConnection();
			StringBuilder sql = new StringBuilder("");
			sql.append("SELECT DISTRICT_DESC");
			sql.append(" FROM MS_DISTRICT WHERE DISTRICT_ID = ? ");
			String dSql = String.valueOf(sql);
//			logger.debug("Sql="+dSql);
			ps = conn.prepareStatement(dSql);
			rs = null;
			ps.setString(1, code);

			rs = ps.executeQuery();
			String result = null;
			if (rs.next()) {
				result = rs.getString(1);
			}
			return result;
		} catch (Exception e) {
			logger.error("Error >> ", e);
			throw new EjbUtilException(e.getMessage());
		} finally {
			try {
				closeConnection(conn, rs, ps);
			} catch (Exception e) {
				logger.error("Error >> ", e);
			}
		}
	}

	@Override
	public String getTambolDescription(String code) throws EjbUtilException {
		PreparedStatement ps = null;
		ResultSet rs = null;
		Connection conn = null;
		try {
			//conn = Get Connection
			conn = getConnection();
			StringBuilder sql = new StringBuilder("");
			sql.append("SELECT SUB_DISTRICT_DESC");
			sql.append(" FROM MS_SUB_DISTRICT WHERE SUB_DISTRICT_ID = ? ");
			String dSql = String.valueOf(sql);
//			logger.debug("Sql="+dSql);
			ps = conn.prepareStatement(dSql);
			rs = null;
			ps.setString(1, code);

			rs = ps.executeQuery();
			String result = null;
			if (rs.next()) {
				result = rs.getString(1);
			}
			return result;
		} catch (Exception e) {
			logger.error("Error >> ", e);
			throw new EjbUtilException(e.getMessage());
		} finally {
			try {
				closeConnection(conn, rs, ps);
			} catch (Exception e) {
				logger.error("Error >> ", e);
			}
		}
	}
	
	/*Sale Information Subform*/	
	@Override
	public String getRefBranchName(String code) throws EjbUtilException {
		PreparedStatement ps = null;
		ResultSet rs = null;
		String result = null;
		Connection conn = null;
		try{
			//conn = Get Connection
			conn = getConnection();
			StringBuilder sql = new StringBuilder("");
			sql.append("SELECT ADD_THAI_SHORT_ADDR FROM VW_MS_BRANCH_CODE WHERE KBANK_BR_NO = ? ");
			String dSql = String.valueOf(sql);
//			logger.debug("Sql="+dSql);
			ps = conn.prepareStatement(dSql);
			rs = null;
			ps.setString(1, code);

			rs = ps.executeQuery();
			
			if(rs.next()){
				result = rs.getString(1);
			}			
		}catch(Exception e){
			logger.error("Error >> ", e);
			throw new EjbUtilException(e.getMessage());
		}finally{
			try{
				closeConnection(conn, rs, ps);
			}catch(Exception e){
				logger.error("Error >> ", e);
			}
		}
		return result;
	}

	@Override
	public String getSellerName(String code) throws EjbUtilException {
		PreparedStatement ps = null;
		ResultSet rs = null;Connection conn = null;
		try {
			//conn = Get Connection
			conn = getConnection();
			StringBuilder sql = new StringBuilder("");
			sql.append("SELECT SALE_NAME");
			sql.append(" FROM VW_MS_SALE_PERSON WHERE SALE_ID = ? ");
			String dSql = String.valueOf(sql);
//			logger.debug("Sql="+dSql);
			ps = conn.prepareStatement(dSql);
			rs = null;
			ps.setString(1, code);

			rs = ps.executeQuery();
			String result = null;
			if (rs.next()) {
				result = rs.getString(1);
			}
			return result;
		} catch (Exception e) {
			logger.error("Error >> ", e);
			throw new EjbUtilException(e.getMessage());
		} finally {
			try {
				closeConnection(conn, rs, ps);
			} catch (Exception e) {
				logger.error("Error >> ", e);
			}
		}
	}
	@Override
	public String getFileCategoryDesc(String code) throws EjbUtilException {
		PreparedStatement ps = null;
		ResultSet rs = null;Connection conn = null;
		try {
			//conn = Get Connection
			conn = getConnection();
			StringBuilder sql = new StringBuilder("");
			sql.append("SELECT L.DISPLAY_NAME ");
			sql.append(" FROM LIST_BOX_MASTER L WHERE  L.FIELD_ID =40   and  L.CHOICE_NO = ?  ");
			String dSql = String.valueOf(sql);
			logger.debug("Sql="+dSql);
			ps = conn.prepareStatement(dSql);
			rs = null;
			ps.setString(1, code);

			rs = ps.executeQuery();
			String result = null;
			if (rs.next()) {
				result = rs.getString(1);
			}
			return result;
		} catch (Exception e) {
			logger.error("Error >> ", e);
			throw new EjbUtilException(e.getMessage());
		} finally {
			try {
				closeConnection(conn, rs, ps);
			} catch (Exception e) {
				logger.error("Error >> ", e);
			}
		}
	}
	@Override
	public String getWFDLAGroup(String jobType,String allocationType,String allocationGroup) throws EjbUtilException{
		String result="";
		PreparedStatement ps = null;
		ResultSet rs = null;Connection conn = null;
		try{
			if(OrigUtil.isEmptyString(allocationGroup)){
				return "";
			}

			conn = getConnection();
			
			StringBuilder SQL = new StringBuilder("");	
				SQL.append(" SELECT ");
				SQL.append("     DLA_ID ");
				SQL.append(" FROM ");
				SQL.append("     MS_DLA  ");
				SQL.append(" WHERE ");
				SQL.append("     DLA_ID LIKE ? ");
				SQL.append(" AND DLA_ID <= ? ");
				SQL.append(" AND ");
				SQL.append("     ( ");
				SQL.append("         ALLOCATION_TYPE = 'A/E' ");
				SQL.append("      OR ALLOCATION_TYPE = ? ");
				SQL.append("     ) ");
				SQL.append(" ORDER BY ");
				SQL.append("     DLA_ID DESC ");
			
			String dSql = String.valueOf(SQL);
			
			ps = conn.prepareStatement(dSql);
			
			rs = null;
			if(null != jobType){
				ps.setString(1,jobType.replace("%", "chr(37)")+"%");
			}else{
				ps.setString(1,jobType);
			}
			ps.setString(2,allocationGroup);
			ps.setString(3,allocationType);
			
			rs = ps.executeQuery();			 
			if(rs.next()){
				result = rs.getString(1);
			}else{
				rs.close();
				ps.close();
				
				SQL = new StringBuilder("");
				
				SQL.append(" SELECT ");
				SQL.append("     DLA_ID ");
				SQL.append(" FROM ");
				SQL.append("     MS_DLA ");
				SQL.append(" WHERE ");
				SQL.append("     DLA_ID LIKE ? ");
				SQL.append(" AND ");
				SQL.append("     ( ");
				SQL.append("         ALLOCATION_TYPE = 'A/E' ");
				SQL.append("      OR ALLOCATION_TYPE = ? ");
				SQL.append("     ) ");
				SQL.append(" ORDER BY ");
				SQL.append(" 	CREDIT_LIMIT DESC, ");
				SQL.append(" 	DLA_ID DESC ");
				
			    dSql = String.valueOf(SQL);
				ps = conn.prepareStatement(dSql);
								
				ps.setString(1,jobType.replace("%","chr(37)")+"%");				 
				ps.setString(2,allocationType);
				
				rs = ps.executeQuery();	
				if(rs.next()){
					result = rs.getString(1);
				}	
			}			
		}catch(Exception e){
			logger.error("Error >> ", e);		
			throw new EjbUtilException(e.getMessage());
		}finally{
			try{
				closeConnection(conn, rs, ps);
			}catch(Exception e){
				logger.error("Error >> ", e);
			}
		}
		return result;		 
	}

	@Override
	public String GetChoiceNOFieldIDByDesc(String fieldID, String desc)throws EjbUtilException {
		PreparedStatement ps = null;
		ResultSet rs = null;Connection conn = null;
		try{
			conn = getConnection();
			StringBuilder sql = new StringBuilder("");
				sql.append(" SELECT CHOICE_NO ");
				sql.append(" FROM LIST_BOX_MASTER WHERE FIELD_ID = ? AND ACTIVE_STATUS = ? AND DISPLAY_NAME = ? ");
			String dSql = String.valueOf(sql);
//			logger.debug("Sql="+dSql);
			ps = conn.prepareStatement(dSql);
			rs = null;
			int index = 1;
			ps.setString(index++, fieldID);
			ps.setString(index++, OrigConstant.ACTIVE_FLAG);
			ps.setString(index++, desc);

			rs = ps.executeQuery();
			String result = null;
			if (rs.next()) {
				result = rs.getString(1);
			}
			return result;
		}catch(Exception e){
			logger.error("Error >> ", e);
			throw new EjbUtilException(e.getMessage());
		}finally{
			try{
				closeConnection(conn, rs, ps);
			}catch(Exception e){
				logger.error("Error >> ", e);
			}
		}
	}

	@Override
	public SearchAddressDataM SearchZipCode(String zipcode, String province,String amphur, String tambol){
		PreparedStatement ps = null;
		ResultSet rs = null;
		Connection conn = null;
		try{			
			String searchZipCode = "N";
			
			if(null != zipcode && zipcode.length() == 5){
				if(OrigUtil.isEmptyString(province) && OrigUtil.isEmptyString(amphur) && OrigUtil.isEmptyString(tambol)){
					searchZipCode = "Y";
				}
			}
			
			conn = getConnection();
			StringBuilder sql = new StringBuilder("");
			
			sql.append(" SELECT TMP.ZIPCODE ZIPCODE,TMP.SUB_DISTRICT_ID SUB_DISTRICT_ID,TMP.SUB_DISTRICT_DESC SUB_DISTRICT_DESC,TMP.DISTRICT_ID DISTRICT_ID, ");
			sql.append(" TMP.DISTRICT_DESC DISTRICT_DESC, TMP.PROVINCE_ID PROVINCE_ID,TMP.PROVINCE_DESC PROVINCE_DESC ");
			sql.append(" FROM( SELECT ZIPCODE ,");
			sql.append(" Z.SUB_DISTRICT_ID,(SELECT SUB_DISTRICT_DESC FROM MS_SUB_DISTRICT S WHERE S.SUB_DISTRICT_ID=Z.SUB_DISTRICT_ID) SUB_DISTRICT_DESC, ");
			sql.append(" Z.DISTRICT_ID,(SELECT DISTRICT_DESC FROM MS_DISTRICT D  WHERE D.DISTRICT_ID=Z.DISTRICT_ID  ) DISTRICT_DESC , ");
			sql.append(" Z.PROVINCE_ID,  (  SELECT PROVINCE_DESC FROM MS_PROVINCE P WHERE P.PROVINCE_ID=Z.PROVINCE_ID ) PROVINCE_DESC ");
			sql.append(" FROM   MS_ZIPCODE Z  )   TMP  ");
			if("Y".equals(searchZipCode)){
				sql.append(" WHERE  TMP.ZIPCODE = ? ");
			}else{
				sql.append(" WHERE  TMP.ZIPCODE = ? AND TMP.PROVINCE_ID = ? ");
				sql.append(" AND TMP.DISTRICT_ID = ? AND  TMP.SUB_DISTRICT_ID = ? ");
			}
			String dSql = String.valueOf(sql);
			
//			logger.debug("Sql="+dSql);
			
			ps = conn.prepareStatement(dSql);
			rs = null;
			int index = 1;
			
			if("Y".equals(searchZipCode)){
				ps.setString(index++, zipcode);
			}else{
				ps.setString(index++, zipcode);
				ps.setString(index++, province);
				ps.setString(index++, amphur);
				ps.setString(index++, tambol);
			}
		
			rs = ps.executeQuery();
			SearchAddressDataM addressM = null;
			int rowSize = 0;
			while(rs.next()){
				rowSize ++;
				addressM  = new SearchAddressDataM();
				addressM.setZipcode(rs.getString("ZIPCODE"));
				addressM.setTambol(rs.getString("SUB_DISTRICT_ID"));
				addressM.setTambol_desc(rs.getString("SUB_DISTRICT_DESC"));
				addressM.setAmphur(rs.getString("DISTRICT_ID"));
				addressM.setAmphur_desc(rs.getString("DISTRICT_DESC"));
				addressM.setProvince(rs.getString("PROVINCE_ID"));
				addressM.setProvince_desc(rs.getString("PROVINCE_DESC"));
				if(rowSize >1) break;
			}
			if(rowSize == 1) return addressM;
		}catch(Exception e){
			logger.error("Error >> ", e);
		}finally{
			try{
				closeConnection(conn, rs, ps);
			}catch(Exception e){
				logger.error("Error >> ", e);
			}
		}
		return null;
	}

	@Override
	public SearchAddressDataM SearchTambol(String province, String amphur,String tambol) {
		PreparedStatement ps = null;
		ResultSet rs = null;Connection conn = null;
		try{
			conn = getConnection();
			StringBuilder sql = new StringBuilder("");
			
			sql.append(" SELECT ");
			sql.append("     TMP.SUB_DISTRICT_ID SUB_DISTRICT_ID, ");
			sql.append("     TMP.SUB_DISTRICT_DESC SUB_DISTRICT_DESC, ");
			sql.append("     TMP.ZIPCODE ZIPCODE, ");
			sql.append(" 	 TMP.NUM ");
			sql.append(" FROM ");
			sql.append("     ( ");
			sql.append("         SELECT ");
			sql.append("             COUNT(Z.ZIPCODE) NUM, ");
			sql.append("             LISTAGG(Z.ZIPCODE ||'|')WITHIN GROUP (ORDER BY Z.ZIPCODE) ZIPCODE , ");
			sql.append("             Z.SUB_DISTRICT_ID, ");
			sql.append("             Z.DISTRICT_ID, ");
			sql.append("             Z.PROVINCE_ID, ");
			sql.append("             (SELECT SUB_DISTRICT_DESC  FROM MS_SUB_DISTRICT S  WHERE S.SUB_DISTRICT_ID=Z.SUB_DISTRICT_ID) SUB_DISTRICT_DESC ");
			sql.append("         FROM ");
			sql.append("             MS_ZIPCODE Z ");
			sql.append("         GROUP BY  ");
			sql.append("             Z.SUB_DISTRICT_ID, ");
			sql.append("             Z.DISTRICT_ID, ");
			sql.append("             Z.PROVINCE_ID ");
			sql.append("     ) TMP ");
			
			sql.append(" WHERE TMP.PROVINCE_ID = ? AND TMP.DISTRICT_ID = ? AND  TMP.SUB_DISTRICT_ID = ? ");
			
			String dSql = String.valueOf(sql);
			
			ps = conn.prepareStatement(dSql);
			rs = null;
			int index = 1;
			
			ps.setString(index++, province);
			ps.setString(index++, amphur);
			ps.setString(index++, tambol);

			rs = ps.executeQuery();
			SearchAddressDataM addressM = null;
			int rowSize = 0;
			while(rs.next()){
				rowSize ++;
				addressM  = new SearchAddressDataM();
				addressM.setZipcode(rs.getString("ZIPCODE"));
				addressM.setTambol(rs.getString("SUB_DISTRICT_ID"));
				addressM.setTambol_desc(rs.getString("SUB_DISTRICT_DESC"));
				addressM.setNum(rs.getInt("NUM"));
				if(rowSize >1) break;
			}
			if(rowSize == 1) return addressM;
		}catch(Exception e){
			logger.error("Error >> ", e);
		}finally{
			try{
				closeConnection(conn, rs, ps);
			}catch(Exception e){
				logger.error("Error >> ", e);
			}
		}
		return null;
	}

	@Override
	public SearchAddressDataM SearchAmphur(String province, String amphur) {
		PreparedStatement ps = null;
		ResultSet rs = null;Connection conn = null;
		try{
			conn = getConnection();
			StringBuilder sql = new StringBuilder("");
			
			sql.append(" SELECT DISTRICT_ID, DISTRICT_DESC ");
			sql.append(" FROM MS_DISTRICT WHERE DISTRICT_ID = ? AND PROVINCE_ID = ? ");
			
			String dSql = String.valueOf(sql);
			
//			logger.debug("Sql="+dSql);
			
			ps = conn.prepareStatement(dSql);
			rs = null;
			int index = 1;
			
			ps.setString(index++, amphur);
			ps.setString(index++, province);			

			rs = ps.executeQuery();
			SearchAddressDataM addressM = null;
			int rowSize = 0;
			while(rs.next()){
				rowSize ++;
				addressM  = new SearchAddressDataM();
				addressM.setAmphur(rs.getString("DISTRICT_ID"));
				addressM.setAmphur_desc(rs.getString("DISTRICT_DESC"));
				if(rowSize >1) break;
			}
			if(rowSize == 1) return addressM;
		}catch(Exception e){
			logger.error("Error >> ", e);
		}finally{
			try{
				closeConnection(conn, rs, ps);
			}catch(Exception e){
				logger.error("Error >> ", e);
			}
		}
		return null;
	}

	@Override
	public SearchAddressDataM SearchProvince(String province) {
		PreparedStatement ps = null;
		ResultSet rs = null;Connection conn = null;
		try{
			conn = getConnection();
			StringBuilder sql = new StringBuilder("");
			
			sql.append(" SELECT PROVINCE_ID, PROVINCE_DESC ");
			sql.append(" FROM MS_PROVINCE WHERE PROVINCE_ID = ? ");
			
			String dSql = String.valueOf(sql);
			
//			logger.debug("Sql="+dSql);
			
			ps = conn.prepareStatement(dSql);
			rs = null;
			int index = 1;
			
			ps.setString(index++, province);			

			rs = ps.executeQuery();
			SearchAddressDataM addressM = null;
			int rowSize = 0;
			while(rs.next()){
				rowSize ++;
				addressM  = new SearchAddressDataM();
				addressM.setProvince(rs.getString("PROVINCE_ID"));
				addressM.setProvince_desc(rs.getString("PROVINCE_DESC"));
				if(rowSize >1) break;
			}
			if(rowSize == 1) return addressM;
		}catch(Exception e){
			logger.error("Error >> ", e);
		}finally{
			try{
				closeConnection(conn, rs, ps);
			}catch(Exception e){
				logger.error("Error >> ", e);
			}
		}
		return null;
	}

	@Override
	public SearchAddressDataM MandatoryAddress(String zipcode, String province,String amphur, String tambol) {
		PreparedStatement ps = null;
		ResultSet rs = null;Connection conn = null;
		try{
			conn = getConnection();
			StringBuilder sql = new StringBuilder("");

			sql.append(" SELECT Z.ZIPCODE FROM MS_ZIPCODE Z ");
			sql.append(" WHERE  Z.ZIPCODE = ? AND Z.PROVINCE_ID = ? ");
			sql.append(" AND Z.DISTRICT_ID = ? AND Z.SUB_DISTRICT_ID = ? ");
			
			String dSql = String.valueOf(sql);
			
//			logger.debug("Sql="+dSql);
			
			ps = conn.prepareStatement(dSql);
			rs = null;
			int index = 1;
			
			ps.setString(index++, zipcode);
			ps.setString(index++, province);
			ps.setString(index++, amphur);
			ps.setString(index++, tambol);

			rs = ps.executeQuery();
			SearchAddressDataM addressM = null;
			int rowSize = 0;
			while(rs.next()){
				rowSize ++;
				addressM  = new SearchAddressDataM();
				addressM.setZipcode(rs.getString("ZIPCODE"));
				if(rowSize >1) break;
			}
			if(rowSize == 1) return addressM;
		}catch(Exception e){
			logger.error("Error >> ", e);
		}finally{
			try{
				closeConnection(conn, rs, ps);
			}catch(Exception e){
				logger.error("Error >> ", e);
			}
		}
		return null;
	}

	@Override
	public ArrayList<String> LoadORIGApplication(String jobState ,int size) {
		PreparedStatement ps = null;
		ResultSet rs = null;Connection conn = null;
		try{
			conn = getConnection();			
			StringBuilder sql = new StringBuilder("");
			sql.append(" SELECT APPLICATION_RECORD_ID FROM ORIG_APPLICATION WHERE JOB_STATE = ? ");
			
			ps = conn.prepareStatement(sql.toString());
			ps.setString(1, jobState);
			rs = ps.executeQuery();
			
			ArrayList<String> list = new ArrayList<String>();
			int i = 1;
			while(rs.next()){
				if(i <= size){
					list.add(rs.getString(1));
				}else{
					break;
				}
				i++;
			}
			return list;
		}catch(Exception e){
			logger.error("Error >> ", e);
		}finally{
			try{
				closeConnection(conn, rs, ps);
			}catch(Exception e){
				logger.error("Error >> ", e);
			}
		}
		return new ArrayList<String>();
	}

	@Override
	public int GetTotalJobCentralQueue(String tdID, String queueType)throws EjbUtilException{
		PreparedStatement ps = null;
		ResultSet rs = null;Connection conn = null;
		int total = 0;
		try{
			conn = getConnection(OrigServiceLocator.WORKFLOW_DB);			
			StringBuilder sql = new StringBuilder("");
			sql.append(" SELECT COUNT(WWQ.JOB_ID) TOTAL FROM WF_WORK_QUEUE WWQ, WF_TODO_LIST WTL, WF_ACTIVITY_TEMPLATE WAT ");
			sql.append(" WHERE WWQ.ATID = WTL.ATID AND WWQ.ATID = WAT.ATID AND WTL.TDID = ? AND WAT.ACTIVITY_TYPE = ? ");
			ps = conn.prepareStatement(sql.toString());
			ps.setString(1, tdID);
			ps.setString(2, queueType);
			rs = ps.executeQuery();
			if(rs.next()){
				total = rs.getInt(1);
			}
		}catch(Exception e){
			logger.error("Error >> ", e);
			throw new EjbUtilException();
		}finally{
			try{
				closeConnection(conn, rs, ps);
			}catch(Exception e){
				logger.error("Error >> ", e);
			}
		}
		return total;
	}

	@Override
	public String LoadUserName(String userName,String role) throws EjbUtilException {
		PreparedStatement ps = null;
		ResultSet rs = null;
		String userID = null;Connection conn = null;
		try{
			conn = getConnection(OrigServiceLocator.ORIG_DB);			
			StringBuilder sql = new StringBuilder("");
			sql.append(" SELECT  US.USER_NAME USER_NAME FROM USER_ROLE_JOIN_ROLE UR, US_USER_DETAIL US ");
			sql.append(" WHERE UR.USER_NAME = US.USER_NAME AND UR.ROLE_NAME = ? ");
			sql.append(" AND TRIM(UPPER(REPLACE(US.THAI_FIRSTNAME|| US.THAI_LASTNAME,' ',''))) = TRIM(UPPER(REPLACE(?,' ',''))) ");
			ps = conn.prepareStatement(sql.toString());
			int index = 1;
			ps.setString(index++,role);
			ps.setString(index++,userName);
			rs = ps.executeQuery();
			if(rs.next()){
				userID = rs.getString("USER_NAME");
			}
		}catch(Exception e){
			logger.error("Error >> ", e);
			throw new EjbUtilException();
		}finally{
			try{
				closeConnection(conn, rs, ps);
			}catch(Exception e){
				logger.error("Error >> ", e);
			}
		}
		return userID;
	}
	@Override
	public String LoadUserID(String userName, String role)throws EjbUtilException {
		PreparedStatement ps = null;
		ResultSet rs = null;
		String userID = null;Connection conn = null;
		try{
			conn = getConnection(OrigServiceLocator.ORIG_DB);			
			StringBuilder SQL = new StringBuilder("");
			
			SQL.append(" SELECT  US.USER_NAME USER_NAME ");
			SQL.append(" FROM USER_ROLE_JOIN_ROLE UR, US_USER_DETAIL US ");
			SQL.append(" WHERE UR.USER_NAME = US.USER_NAME ");
			SQL.append(" AND UR.ROLE_NAME = ? ");
			SQL.append(" AND US.USER_NAME = ? ");
			
			ps = conn.prepareStatement(SQL.toString());
			
			int index = 1;
			ps.setString(index++,role);
			ps.setString(index++,userName);
			
			rs = ps.executeQuery();
			if(rs.next()){
				userID = rs.getString("USER_NAME");
			}
		}catch(Exception e){
			logger.error("Error >> ", e);
			throw new EjbUtilException();
		}finally{
			try{
				closeConnection(conn, rs, ps);
			}catch(Exception e){
				logger.error("Error >> ", e);
			}
		}
		return userID;
	}
	@Override
	public String getApplicationNo(String appRecID) {
		PreparedStatement ps = null;
		ResultSet rs = null;
		String applicationNo = "";Connection conn = null;
		try{
			conn = getConnection(OrigServiceLocator.ORIG_DB);			
			StringBuilder sql = new StringBuilder("");
			sql.append(" SELECT APPLICATION_NO FROM ORIG_APPLICATION WHERE APPLICATION_RECORD_ID = ? ");
			ps = conn.prepareStatement(sql.toString());
			int index = 1;
			ps.setString(index++, appRecID);
			rs = ps.executeQuery();
			if(rs.next()){
				applicationNo = rs.getString("APPLICATION_NO");
			}
		}catch(Exception e){
			logger.error("Error >> ", e);
		}finally{
			try{
				closeConnection(conn, rs, ps);
			}catch(Exception e){
				logger.error("Error >> ", e);
			}
		}
		return applicationNo;
	}
	
	@Override
	public Vector<PLXRulesDedupDataM> LoadDataDupApplication(ArrayList<String> list) {
		Vector<PLXRulesDedupDataM> dupVect = new Vector<PLXRulesDedupDataM>();
		PreparedStatement ps = null;
		ResultSet rs = null;
		PLXRulesDedupDataM deDupM = null;Connection conn = null;
		try{									
			conn = getConnection();			
			StringBuilder SQL = new StringBuilder();
			
			SQL.append(" SELECT ");
			SQL.append("     ROWNUM NO, ");
			SQL.append("     A.APPLICATION_NO APPLICATION_NO, ");
			SQL.append("     A.APPLICATION_RECORD_ID APPLICATION_RECORD_ID, ");
			SQL.append("     I.APP_STATUS APPLICATION_STATUS, ");
			SQL.append("     I.OWNER OWNER, ");
			SQL.append("     I.APPLICATION_DATE CREATE_DATE, ");
			SQL.append("     A.APPLY_CHANNEL CHANNEL, ");
			SQL.append("     A.FINAL_APP_DECISION_DATE CANCEL_REJECT_DATE, ");
			SQL.append("     A.BUSINESS_CLASS_ID BUSINESS_CLASS_ID , ");
			SQL.append("     A.FINAL_APP_DECISION_DATE FINAL_APP_DECISION_DATE ");
			SQL.append(" FROM ");
			SQL.append("     ORIG_APPLICATION A, ");
			SQL.append("     ORIG_PERSONAL_INFO P, ");
			SQL.append("     WF_INSTANT I, ");
			SQL.append("     WF_JOBID_MAPPING J ");
			SQL.append(" WHERE ");
			SQL.append("     A.APPLICATION_RECORD_ID = P.APPLICATION_RECORD_ID ");
			SQL.append(" AND J.JOB_ID = I.JOB_ID ");
			SQL.append(" AND J.APPLICATION_RECORD_ID = A.APPLICATION_RECORD_ID ");
			SQL.append(" AND P.PERSONAL_TYPE = ? ");
			SQL.append(" AND J.JOB_VERSION = ");
			SQL.append("     ( ");
			SQL.append("         SELECT ");
			SQL.append("             MAX(TMP.JOB_VERSION) ");
			SQL.append("         FROM ");
			SQL.append("             WF_JOBID_MAPPING TMP ");
			SQL.append("         WHERE ");
			SQL.append("             TMP.JOB_ID = J.JOB_ID ");
			SQL.append("     )");
			
			if(null!=list && list.size()>0){			
				SQL.append(" AND A.APPLICATION_NO IN(" );				
				SQL.append(ParameterINVect(list));				
				SQL.append(	") ");
			}
			
			ps = conn.prepareStatement(SQL.toString());
			int index = 1;
			ps.setString(index++, OrigConstant.PERSONAL_TYPE_APPLICANT);
			PreparedStatementParameter(index, ps, list);
			rs = ps.executeQuery();
			while(rs.next()){
				deDupM = new PLXRulesDedupDataM();	
				
				String appRecID   = rs.getString("APPLICATION_RECORD_ID");
				String busClassID = rs.getString("BUSINESS_CLASS_ID");
				String appStatus  = rs.getString("APPLICATION_STATUS");
				
				deDupM.setApplicationNo(rs.getString("APPLICATION_NO"));
				deDupM.setApplicationRecordId(appRecID);
				deDupM.setApplicationStatus(appStatus);
				deDupM.setLastOwner(rs.getString("OWNER"));
				deDupM.setAppDate(rs.getTimestamp("CREATE_DATE"));
//				deDupM.setDecisionDate(rs.getTimestamp("CANCEL_REJECT_DATE"));
				deDupM.setChannel(rs.getString("CHANNEL"));
								
				if(isCancleRejectStatus(busClassID, appStatus)){
					deDupM.setDecisionReason(getReasonFromRejectCancel(appRecID,busClassID));
					deDupM.setDecisionDate(rs.getTimestamp("FINAL_APP_DECISION_DATE"));
				}
				dupVect.add(deDupM);
			}
		}catch(Exception e){
			logger.error("Exception >> ", e);
		}finally{
			try{
				closeConnection(conn, rs, ps);
			}catch(Exception e){
				logger.error("Error >> ", e);
			}
		}
		return dupVect;
	}
	
	private boolean isCancleRejectStatus(String busClassID,String appStatus){
//		logger.debug("appStatus >> "+appStatus);
		try{
			String param = GetGeneralParamDataM("DUPLICATE_CANCLE_STATUS", busClassID);
//			logger.debug("param >> "+param);
			if(null != param && null != appStatus){
				String[] $param = param.split(",");
				for(String id :$param){
					if(null != id){
						if(id.replaceAll(" ", "").trim().toUpperCase().equals(appStatus.replaceAll(" ", "").trim().toUpperCase())){
//							logger.debug("isCancleRejectStatus << true >>");
							return true;
						}
					}
				}
			}
		}catch(Exception e){
			
		}
		return false;		
	}
	
	private String getReasonFromRejectCancel(String appRecID ,String busClassID){
		PreparedStatement ps = null;
		ResultSet rs = null;
		StringBuilder strB = new StringBuilder("");
		Connection conn = null;
//		logger.debug("busClassID >> "+busClassID);
		try{						
//			String param = GetGeneralParamDataM("ACTION_REJECT_CANCLE", busClassID);
//			String action = OrigUtil.ConvertStringParamToNotEmptyString(param);	
//			logger.debug("Action >> "+action);			
			conn = getConnection();
			StringBuilder SQL = new StringBuilder("");			
				SQL.append("         SELECT DISTINCT R.REASON_TYPE||'-'||R.REASON_CODE REASON  ");
				SQL.append("         FROM ");
				SQL.append("             ORIG_REASON R, ");
				SQL.append("             (SELECT ROLE, APPLICATION_RECORD_ID ");
				SQL.append("                 FROM (SELECT ROLE, APPLICATION_RECORD_ID ");
				SQL.append("                         FROM ORIG_REASON TR ");
				SQL.append("                         WHERE TR.APPLICATION_RECORD_ID = ? ");
				SQL.append("                         ORDER BY TR.CREATE_DATE DESC ");
				SQL.append("                         ) ");
				SQL.append("                  WHERE  ROWNUM = 1 ");
				SQL.append("             ) ");
				SQL.append("             TMP ");
				SQL.append("         WHERE ");
				SQL.append("             R.APPLICATION_RECORD_ID = TMP.APPLICATION_RECORD_ID ");
				SQL.append("         AND TMP.ROLE = R.ROLE ");
				SQL.append("         AND R.APPLICATION_RECORD_ID = ? ");
			
			ps = conn.prepareStatement(SQL.toString());
//			rs = null;
			int index = 1;			
//			if(!OrigUtil.isEmptyString(action)){
//				String[] obj = action.split(",");
//				for(int i=0; i<obj.length; i++){
//					ps.setString(index++,(String)obj[i]);
//				}
//			}
			ps.setString(index++, appRecID);
			ps.setString(index++, appRecID);
			rs = ps.executeQuery();
			
			while(rs.next()){
				strB.append(rs.getString(1));
				strB.append("|");
			}
			if (strB.length() > 0) {
				strB.deleteCharAt(strB.length()-1);
			}
			
		}catch(Exception e){
			logger.fatal(e.getLocalizedMessage());
		}finally{
			try{
				closeConnection(conn, rs, ps);
			}catch(Exception e){
				logger.fatal(e.getLocalizedMessage());
			}
		}
		logger.debug("STR "+strB.toString());
		return strB.toString();		
	}
	
	private Timestamp GetFinalDecisionDate(String appRecID){
		PreparedStatement ps = null;
		ResultSet rs = null;
		Timestamp time = null;
		Connection conn = null;
		try {
			conn = getConnection();
			StringBuilder sql = new StringBuilder("");
			sql.append(" SELECT FINAL_APP_DECISION_DATE FROM ORIG_APPLICATION WHERE APPLICATION_RECORD_ID = ? ");
			String dSql = String.valueOf(sql);
			ps = conn.prepareStatement(dSql);
			rs = null;
			ps.setString(1, appRecID);
			rs = ps.executeQuery();
			
			if (rs.next()) {
				time =  rs.getTimestamp(1);
			}			
		}catch(Exception e){
			logger.fatal(e.getLocalizedMessage());
		}finally{
			try{
				closeConnection(conn, rs, ps);
			}catch(Exception e){
				logger.fatal(e.getLocalizedMessage());
			}
		}
		return time;		
	}
	
	public String GetGeneralParamDataM(String paramCode,String busClassID){
		String param = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		Connection conn = null;
		try{
			conn = getConnection();
			StringBuilder sql = new StringBuilder();
			sql.append(" SELECT GP.* FROM GENERAL_PARAM GP ,BUSINESS_CLASS_GROUP BCG ,BUS_CLASS_GRP_MAP CGM ");
			sql.append(" WHERE GP.PARAM_CODE = ? ");
			sql.append(" AND BCG.BUS_GRP_ID = CGM.BUS_GRP_ID ");
			sql.append(" AND GP.BUS_CLASS_ID = CGM.BUS_GRP_ID ");
			sql.append(" AND CGM.BUS_CLASS_ID = ? ");
			ps = conn.prepareStatement(sql.toString());
			int index = 1;
				ps.setString(index++,paramCode);
				ps.setString(index++,busClassID);
			rs = ps.executeQuery();
			if(rs.next()){
				param = rs.getString("PARAM_VALUE");
			}
		}catch (Exception e) {
			logger.fatal(e.getLocalizedMessage());
		}finally{
			try {
				closeConnection(conn, rs, ps);
			} catch (Exception e) {
				logger.fatal(e.getLocalizedMessage());
			}
		}
		return param;
	}
	
	@Override
	public String getSellerName(String branch_code,String sale_code,String channel_group,String channel){
		PreparedStatement ps = null;
		ResultSet rs = null;
		String result = "";
		Connection conn = null;
		try{
			conn = getConnection();
			StringBuilder sql = new StringBuilder("");
				sql.append(" SELECT SALE_NAME FROM VW_MS_SALE_PERSON WHERE SALE_ID = ? ");
			
			if(!"0400".equals(branch_code)){
				sql.append("AND BRANCH_CODE = ? ");
			}
				
			if("02".equals(channel_group)){
				sql.append("AND SALE_TYPE = ? ");
			}else{
				sql.append("AND CHANNEL_GROUP = ? ");
			}
			sql.append("AND STATUS = ? ");
						
			String dSql = String.valueOf(sql);
			
			ps = conn.prepareStatement(dSql);
			int index = 1;
			
			ps.setString(index++,sale_code);			
			if(!"0400".equals(branch_code)){
				ps.setString(index++,branch_code);
			}
			
			if("02".equals(channel_group)){
				ps.setString(index++,channel);
			}else{
				ps.setString(index++,channel_group);
			}			
			ps.setString(index++,OrigConstant.Status.STATUS_ACTIVE);
			
			rs = ps.executeQuery();
			ArrayList<String> saleList = new ArrayList<String>();
			while(rs.next()){
				String saleName = rs.getString(1);
				if(null != saleName){
					saleList.add(saleName);
				}			
			}
			if(null != saleList && saleList.size() == 1){
				result = saleList.get(0);
			}else{
				result = null;
			}
		}catch(Exception e){
			logger.error("Error >> ", e);
		}finally{
			try{
				closeConnection(conn, rs, ps);
			}catch(Exception e){
				logger.error("Error >> ", e);
			}
		}
		return result;
	}
	@Override
	public String getSellerNameByBranch(String branch_code, String sale_code,String branchGroup) {
		PreparedStatement ps = null;
		ResultSet rs = null;
		String result = "";
		Connection conn = null;
		try{
			conn = getConnection();
			StringBuilder sql = new StringBuilder("");
						
			sql.append(" SELECT SALE_NAME FROM VW_MS_SALE_PERSON WHERE SALE_ID = ? ");
			
			if("0400".equals(branch_code)){
				sql.append("AND CHANNEL_GROUP = ? ");
			}else{
				sql.append("AND BRANCH_CODE = ? ");				
			}
			sql.append("AND STATUS = ? ");
			
			String dSql = String.valueOf(sql);
			ps = conn.prepareStatement(dSql);
			
			int index = 1;
			ps.setString(index++,sale_code);
			
			if("0400".equals(branch_code)){
				ps.setString(index++,"03");
			}else{
				ps.setString(index++,branch_code);
			}
			ps.setString(index++,OrigConstant.Status.STATUS_ACTIVE);
			
			rs = ps.executeQuery();
			ArrayList<String> saleList = new ArrayList<String>();
			while(rs.next()){
				String saleName = rs.getString(1);
				if(null != saleName){
					saleList.add(saleName);
				}			
			}
			if(null != saleList && saleList.size() == 1){
				result = saleList.get(0);
			}else{
				result = null;
			}
		}catch(Exception e){
			logger.error("Error >> ", e);
		}finally{
			try{
				closeConnection(conn, rs, ps);
			}catch(Exception e){
				logger.error("Error >> ", e);
			}
		}
		return result;
	}
	
	@Override
	public HashMap<String,String> getWorkflowInfo(String appRecID){
		HashMap<String,String> info = new HashMap<String, String>();
		
		PreparedStatement ps = null;
		ResultSet rs = null;
		Connection conn = null;
		try{
			conn = getConnection(OrigServiceLocator.WORKFLOW_DB);			
			
			StringBuilder SQL = new StringBuilder("");
			
			SQL.append(" SELECT ");
			SQL.append("     T.PTID PTID, ");
			SQL.append("     T.ROLE_ID ROLE ");
			SQL.append(" FROM ");
			SQL.append("     WF_JOBID_MAPPING J, ");
			SQL.append("     WF_WORK_QUEUE Q, ");
			SQL.append("     WF_ACTIVITY_TEMPLATE T ");
			SQL.append(" WHERE ");
			SQL.append("     J.JOB_ID = Q.JOB_ID ");
			SQL.append(" AND J.APPLICATION_RECORD_ID = ? ");
			SQL.append(" AND Q.ATID = T.ATID ");
			SQL.append(" AND J.JOB_STATUS = 'ACTIVE' ");
			
			ps = conn.prepareStatement(SQL.toString());
			
			int index = 1;
			ps.setString(index++,appRecID);
			
			rs = ps.executeQuery();
			
			if(rs.next()){
				info.put(OrigConstant.WorkflowInfo.PTID,rs.getString("PTID"));
				info.put(OrigConstant.WorkflowInfo.ROLE,rs.getString("ROLE"));
			}
			
		}catch(Exception e){
			logger.error("Error >> ", e);
		}finally{
			try{
				closeConnection(conn, rs, ps);
			}catch(Exception e){
				logger.error("Error >> ", e);
			}
		}		
		return info;
	}
	@Override
	public String getReasonDesc(String appRecID){
		PreparedStatement ps = null;
		ResultSet rs = null;		
		String STR = null;
		Connection conn = null;
		try{
			conn = getConnection();
			
			StringBuilder SQL = new StringBuilder("");
				SQL.append(" SELECT ");
				SQL.append("     LISTAGG(X.REASON||' ,') WITHIN GROUP (ORDER BY X.REASON) REASON_DESC ");
				SQL.append(" FROM ");
				SQL.append("     ( ");
				SQL.append("         SELECT DISTINCT ");
				SQL.append("             (  ");
				SQL.append("                 SELECT DISPLAY_NAME FROM LIST_BOX_MASTER ");
				SQL.append("                 WHERE FIELD_ID = R.REASON_TYPE AND CHOICE_NO = R.REASON_CODE ");
				SQL.append("             ) ");
				SQL.append("             REASON ");
				SQL.append("         FROM ");
				SQL.append("             ORIG_REASON R, ");
				SQL.append("             (SELECT ROLE, APPLICATION_RECORD_ID ");
				SQL.append("                 FROM (SELECT ROLE, APPLICATION_RECORD_ID ");
				SQL.append("                         FROM ORIG_REASON TR ");
				SQL.append("                         WHERE TR.APPLICATION_RECORD_ID = ? ");
				SQL.append("                         ORDER BY TR.CREATE_DATE DESC ");
				SQL.append("                         ) ");
				SQL.append("                  WHERE  ROWNUM = 1 ");
				SQL.append("             ) ");
				SQL.append("             TMP ");
				SQL.append("         WHERE ");
				SQL.append("             R.APPLICATION_RECORD_ID = TMP.APPLICATION_RECORD_ID ");
				SQL.append("         AND TMP.ROLE = R.ROLE ");
				SQL.append("         AND R.APPLICATION_RECORD_ID = ? ");
				SQL.append("     ) ");
				SQL.append("     X ");
			
			ps = conn.prepareStatement(SQL.toString());
			
			int index = 1;
			ps.setString(index++,appRecID);
			ps.setString(index++,appRecID);
			
			rs = ps.executeQuery();			
			if(rs.next()){
				STR = rs.getString("REASON_DESC");
			}
			if(null != STR && STR.length() >1){
				STR = STR.substring(0,STR.length()-1);
			}
		}catch(Exception e) {
			logger.error("ERROR"+e.getMessage());
		}finally{
			try{
				closeConnection(conn,rs, ps);
			}catch(Exception e){
				logger.error("ERROR"+e.getMessage());
			}
		}
		if(null == STR) STR = "-";
		return STR;
	}
	
	@Override
	public String getJobState(String appRecID) throws Exception{
		PreparedStatement ps = null;
		ResultSet rs = null;
		String JOBSTATE = "";
		Connection conn = null;
		try{
			conn = getConnection(OrigServiceLocator.ORIG_DB);			
			StringBuilder sql = new StringBuilder("");
			sql.append(" SELECT JOB_STATE FROM ORIG_APPLICATION WHERE APPLICATION_RECORD_ID = ? ");
			ps = conn.prepareStatement(sql.toString());
			int index = 1;
			ps.setString(index++, appRecID);
			rs = ps.executeQuery();
			if(rs.next()){
				JOBSTATE = rs.getString("JOB_STATE");
			}
		}catch(Exception e){
			logger.error("Error >> ", e);
			throw new Exception(e.getMessage());
		}finally{
			try{
				closeConnection(conn, rs, ps);
			}catch(Exception e){
				logger.error("Error >> ", e);
			}
		}
		return JOBSTATE;
	}
	
	@Override
	public ArrayList<String> getParamByUserID(String paramCode, String userID) {
		PreparedStatement ps = null;
		ResultSet rs = null;
		Connection conn = null;
		ArrayList<String> list = new ArrayList<String>();
		try{
			conn = getConnection(OrigServiceLocator.ORIG_DB);			
			StringBuilder SQL = new StringBuilder("");
			
			SQL.append(" SELECT DISTINCT ");
			SQL.append("     TRIM(GP.BUS_CLASS_ID) BUSGROUP ");
			SQL.append(" FROM ");
			SQL.append("     GENERAL_PARAM GP, ");
			SQL.append("     BUS_CLASS_GRP_MAP BGM, ");
			SQL.append("     TABLE(BUS_CLASS.GETBUSCLASSBYUSER(?)) BU ");
			SQL.append(" WHERE ");
			SQL.append("     GP.PARAM_CODE = ? ");
			SQL.append(" AND GP.BUS_CLASS_ID = BGM.BUS_GRP_ID ");
			SQL.append(" AND BU.BUS_CLASS_ID = BGM.BUS_CLASS_ID ");
			
			ps = conn.prepareStatement(SQL.toString());
			int index = 1;
			ps.setString(index++, userID);
			ps.setString(index++, paramCode);
			
			rs = ps.executeQuery();
			while(rs.next()){
				list.add(rs.getString("BUSGROUP"));
			}
		}catch(Exception e){
			logger.error("Error >> ", e);
		}finally{
			try{
				closeConnection(conn, rs, ps);
			}catch(Exception e){
				logger.error("Error >> ", e);
			}
		}
		return list;
	}

	@Override
	public Vector<String> loadReasonCS(String appRecID, String jobState, String finalDecision) {
		PreparedStatement ps = null;
		ResultSet rs = null;
		Vector<String> reasonVect = new Vector<String>();Connection conn = null;
		try {
			conn = getConnection();
			StringBuilder SQL = new StringBuilder("");
			
			String rejectState = null;//ORIGCacheUtil.getInstance().getGeneralParamByCode(OrigConstant.GeneralParamCode.JOBSTATE_REJECT_ALL);
			
			logger.debug("reject state >>"+rejectState);
			logger.debug("job state >>"+jobState);
			
			if(rejectState.indexOf(jobState) >= 0){			
				SQL.append("SELECT PKA_REJECT_LETTER.GET_REJECT_TEMPLATE_REASON(?) FROM DUAL");
			}else{		
				if(OrigUtil.isEmptyString(finalDecision)){
					return null;
				}				
				SQL.append(" SELECT DISTINCT LB.DISPLAY_NAME ");
				SQL.append(" FROM ORIG_REASON R, LIST_BOX_MASTER LB,  ");
				SQL.append("     (SELECT ROLE, APPLICATION_RECORD_ID ");
				SQL.append("       FROM (SELECT ROLE, APPLICATION_RECORD_ID ");
				SQL.append("             FROM ORIG_REASON TR ");
				SQL.append("             WHERE TR.APPLICATION_RECORD_ID = ? ");
				SQL.append("             ORDER BY TR.CREATE_DATE DESC ");
				SQL.append("             ) ");
				SQL.append("              WHERE  ROWNUM = 1 ");
				SQL.append("     ) ");
				SQL.append("     TMP ");
				SQL.append(" WHERE LB.FIELD_ID = R.REASON_TYPE ");
				SQL.append(" AND LB.CHOICE_NO = R.REASON_CODE");
				SQL.append(" AND R.APPLICATION_RECORD_ID = TMP.APPLICATION_RECORD_ID ");
				SQL.append(" AND R.ROLE = TMP.ROLE ");
				SQL.append(" AND R.APPLICATION_RECORD_ID = ? ");
			}
			String dSql = String.valueOf(SQL);
			
			ps = conn.prepareStatement(dSql);
			rs = null;
			int index = 1;
			if(rejectState.indexOf(jobState) >= 0){
				ps.setString(index++ , appRecID);
			}else{
				ps.setString(index++ , appRecID);
				ps.setString(index++ , appRecID);
			}			
			rs = ps.executeQuery();
						
			while(rs.next()){
				reasonVect.add(rs.getString(1));
			}						
		}catch(Exception e){
			logger.fatal(e.getLocalizedMessage());
		}finally{
			try{
				closeConnection(conn, rs, ps);
			}catch(Exception e){
				logger.fatal(e.getLocalizedMessage());
			}
		}
		return reasonVect;
	}

	@Override
	public Vector<String> loadTrackingDoclistName(String appRecID){
		Vector<String> checkDoc = new Vector<String>();
		PreparedStatement ps = null;
		ResultSet rs = null;Connection conn = null;
		try{
			conn = getConnection();
			StringBuilder SQL = new StringBuilder("");
				SQL.append("SELECT MCL.TH_DESC FROM ");
				SQL.append("ORIG_DOC_CHECK_LIST CL, MS_DOC_LIST MCL ");
				SQL.append("WHERE  CL.DOCUMENT_CODE = MCL.DOCUMENT_CODE AND MCL.ACTIVE_STATUS = ? ");
				SQL.append("AND CL.APPLICATION_RECORD_ID = ? AND CL.RECEIVE = ? ");

			ps = conn.prepareStatement(SQL.toString());
			rs = null;
			ps.setString(1, OrigConstant.ACTIVE_FLAG);
			ps.setString(2, appRecID);
//			ps.setString(3, HTMLRenderUtil.RadioBoxCompare.TrackDoc);

			rs = ps.executeQuery();
			while (rs.next()){
				checkDoc.add(rs.getString(1));
			}			
		}catch(Exception e){
			logger.fatal(e.getLocalizedMessage());
		}finally{
			try{
				closeConnection(conn, rs, ps);
			}catch(Exception e){
				logger.fatal(e.getLocalizedMessage());
			}
		}
		return checkDoc;
	}
	
	@Override
	public void getWfLastData(String appRecID, WorkflowDataM workflowM)throws EjbUtilException{
		PreparedStatement ps = null;
		ResultSet rs = null;Connection conn = null;
		try{
			conn = getConnection();
			StringBuilder SQL = new StringBuilder("");
				SQL.append(" SELECT ");
				SQL.append("     I.FIELD_02, ");
				SQL.append("     I.FIELD_03 ");
				SQL.append(" FROM ");
				SQL.append("     WF_JOBID_MAPPING J, ");
				SQL.append("     WF_INSTANT I ");
				SQL.append(" WHERE ");
				SQL.append("     J.JOB_ID = I.JOB_ID ");
				SQL.append(" AND J.JOB_STATUS = 'ACTIVE' ");
				SQL.append(" AND J.APPLICATION_RECORD_ID = ? ");

			ps = conn.prepareStatement(SQL.toString());
			int index = 1;
			
			ps.setString(index++, appRecID);
			rs = ps.executeQuery();
			if(rs.next()){
				workflowM.setGroupAllocateID(rs.getString("FIELD_02"));
				workflowM.setLevelID(rs.getString("FIELD_03"));
			}
		}catch(Exception e){
			logger.fatal(e.getLocalizedMessage());
			throw new EjbUtilException(e.getLocalizedMessage());
		}finally{
			try{
				closeConnection(conn, rs, ps);
			}catch(Exception e){
				logger.fatal(e.getLocalizedMessage());
				throw new EjbUtilException(e.getLocalizedMessage());
			}
		}
	}

	@Override
	public String getAppRecordID(String applicationNo) {
		PreparedStatement ps = null;
		ResultSet rs = null;
		String appRecID = "";Connection conn = null;
		try{
			conn = getConnection(OrigServiceLocator.ORIG_DB);			
			StringBuilder sql = new StringBuilder("");
			sql.append(" SELECT APPLICATION_RECORD_ID FROM ORIG_APPLICATION WHERE APPLICATION_NO = ? ");
			ps = conn.prepareStatement(sql.toString());
			int index = 1;
			ps.setString(index++, applicationNo);
			rs = ps.executeQuery();
			if(rs.next()){
				appRecID = rs.getString("APPLICATION_RECORD_ID");
			}
		}catch(Exception e){
			logger.error("Error >> ", e);
		}finally{
			try{
				closeConnection(conn, rs, ps);
			}catch(Exception e){
				logger.error("Error >> ", e);
			}
		}
		return appRecID;
	}

	@Override
	public String getNextWfSendBack(String appRecID ,String role) throws EjbUtilException{
		PreparedStatement ps = null;
		ResultSet rs = null;
		String FLAG = "N";Connection conn = null;
		try{
			conn = getConnection(OrigServiceLocator.WORKFLOW_DB);	
			
			StringBuilder SQL = new StringBuilder("");			
				SQL.append(" SELECT '1' ");
				SQL.append(" FROM  WF_JOBID_MAPPING J,  WF_INSTANT_HISTORY H ");
				SQL.append("     , WF_ACTIVITY_TEMPLATE T ");
				SQL.append(" WHERE ");
				SQL.append("     J.JOB_ID = H.JOB_ID AND H.FROM_ATID = T.ATID ");
				SQL.append(" AND H.PROCESS_STATE = 'SEND_BACK' ");
				SQL.append(" AND T.ROLE_ID = ? ");
				SQL.append(" AND J.APPLICATION_RECORD_ID = ? ");
				SQL.append(" AND J.JOB_STATUS = ? ");
			
			ps = conn.prepareStatement(SQL.toString());
			int index = 1;
			ps.setString(index++, role);
			ps.setString(index++, appRecID);
			ps.setString(index++, WorkflowConstant.JOB_STATUS_ACTIVE);
			rs = ps.executeQuery();
			if(rs.next()){
				FLAG = "Y";
			}
		}catch(Exception e){
			logger.fatal("Error >> ", e);
			throw new EjbUtilException(e.getLocalizedMessage());
		}finally{
			try{
				closeConnection(conn, rs, ps);
			}catch(Exception e){
				logger.error("Error >> ", e);
			}
		}
		return FLAG;
	}
	@Override
	public String getICDCFLAG(String appRecID) {
		PreparedStatement ps = null;
		ResultSet rs = null;
		String FLAG = "";Connection conn = null;
		try{
			conn = getConnection(OrigServiceLocator.ORIG_DB);			
			StringBuilder sql = new StringBuilder("");
			sql.append(" SELECT ICDC_FLAG FROM ORIG_APPLICATION WHERE APPLICATION_RECORD_ID = ? ");
			ps = conn.prepareStatement(sql.toString());
			int index = 1;
			ps.setString(index++, appRecID);
			rs = ps.executeQuery();
			if(rs.next()){
				FLAG = rs.getString("ICDC_FLAG");
			}
		}catch(Exception e){
			logger.error("Error >> ", e);
		}finally{
			try{
				closeConnection(conn, rs, ps);
			}catch(Exception e){
				logger.error("Error >> ", e);
			}
		}
		return FLAG;
	}

	@Override
	public HashMap<String, String> getVcInboxData(String appRecID) {
		PreparedStatement ps = null;
		ResultSet rs = null;
		HashMap<String, String> hash = new HashMap<String, String>();Connection conn = null;
		try{
			conn = getConnection(OrigServiceLocator.ORIG_DB);
			
			StringBuilder sql = new StringBuilder("");
				sql.append(" SELECT ");
				sql.append("     ( ");
				sql.append("         SELECT ");
				sql.append("             COUNT(1) ");
				sql.append("         FROM ");
				sql.append("             XRULES_PHONE_VERIFICATION ");
				sql.append("         WHERE ");
				sql.append("             XRULES_PHONE_VERIFICATION.PERSONAL_ID = ORIG_PERSONAL_INFO.PERSONAL_ID ");
				sql.append("         AND TRUNC(XRULES_PHONE_VERIFICATION.CREATE_DATE) = TRUNC(SYSDATE) ");
				sql.append("     ) ");
				sql.append("     VERIFY_CUS_COUNT, ");
				sql.append("     ( ");
				sql.append("         SELECT ");
				sql.append("             COUNT(DISTINCT TRUNC(XRULES_PHONE_VERIFICATION.CREATE_DATE)) ");
				sql.append("         FROM ");
				sql.append("             XRULES_PHONE_VERIFICATION ");
				sql.append("         WHERE ");
				sql.append("             XRULES_PHONE_VERIFICATION.PERSONAL_ID = ORIG_PERSONAL_INFO.PERSONAL_ID ");
				sql.append("     ) ");
				sql.append("     VERIFY_CUS, ");
				sql.append("     ( ");
				sql.append("         SELECT ");
				sql.append("             COUNT(1) ");
				sql.append("         FROM ");
				sql.append("             XRULES_HR_VERIFICATION ");
				sql.append("         WHERE ");
				sql.append("             XRULES_HR_VERIFICATION.PERSONAL_ID = ORIG_PERSONAL_INFO.PERSONAL_ID ");
				sql.append("         AND TRUNC(XRULES_HR_VERIFICATION.CREATE_DATE) = TRUNC(SYSDATE) ");
				sql.append("     ) ");
				sql.append("     VERIFY_HR_COUNT, ");
				sql.append("     ( ");
				sql.append("         SELECT ");
				sql.append("             COUNT(DISTINCT TRUNC(XRULES_HR_VERIFICATION.CREATE_DATE)) ");
				sql.append("         FROM ");
				sql.append("             XRULES_HR_VERIFICATION ");
				sql.append("         WHERE ");
				sql.append("             XRULES_HR_VERIFICATION.PERSONAL_ID = ORIG_PERSONAL_INFO.PERSONAL_ID ");
				sql.append("     ) ");
				sql.append("     VERIFY_HR ,ORIG_PERSONAL_INFO.PERSONAL_ID ");
				sql.append(" FROM ");
				sql.append("     ORIG_PERSONAL_INFO ");
				sql.append(" WHERE ");
				sql.append("     ORIG_PERSONAL_INFO.APPLICATION_RECORD_ID = ? ");
			
					
			ps = conn.prepareStatement(sql.toString());
			
			int index = 1;	
			ps.setString(index++, appRecID);
//			ps.setString(index++, appRecID);
//			ps.setString(index++, appRecID);
			
			rs = ps.executeQuery();
			if(rs.next()){
				hash.put("VERIFY_CUS_COUNT", rs.getString("VERIFY_CUS_COUNT"));
				hash.put("VERIFY_CUS", rs.getString("VERIFY_CUS"));
				hash.put("VERIFY_HR_COUNT", rs.getString("VERIFY_HR_COUNT"));
				hash.put("VERIFY_HR", rs.getString("VERIFY_HR"));
				String personalID = rs.getString("PERSONAL_ID");
				hash.put("UPDATE_BY", getVCLastUpdateBy(personalID));
			}
			
		}catch(Exception e){
			logger.error("ERROR "+e.getMessage());
		}finally{
			try{
				closeConnection(conn, rs, ps);
			}catch(Exception e){
				logger.error("ERROR "+e.getMessage());
			}
		}
		return hash;
	}
	
	private String getVCLastUpdateBy(String personalID){
		PreparedStatement ps = null;
		ResultSet rs = null;
		String updateBy = "";Connection conn = null;
		try{
			conn = getConnection(OrigServiceLocator.ORIG_DB);			
			StringBuilder sql = new StringBuilder("");
				sql.append(" SELECT X.CREATE_BY FROM ( ");
				sql.append(" SELECT ");
				sql.append("     M.CREATE_BY ");
				sql.append(" FROM ");
				sql.append("     ( ");
				sql.append("         SELECT XRULES_PHONE_VERIFICATION.CREATE_DATE, ");
				sql.append("             XRULES_PHONE_VERIFICATION.CREATE_BY ");
				sql.append("         FROM  XRULES_PHONE_VERIFICATION ");
				sql.append("         WHERE XRULES_PHONE_VERIFICATION.PERSONAL_ID = ? ");
				sql.append("         AND XRULES_PHONE_VERIFICATION.SEQ = ");
				sql.append("             ( SELECT MAX(PHONE_TMP.SEQ) FROM  XRULES_PHONE_VERIFICATION PHONE_TMP WHERE PHONE_TMP.PERSONAL_ID = ? ) ");
				sql.append("         UNION ");
				sql.append("         SELECT XRULES_HR_VERIFICATION.CREATE_DATE, ");
				sql.append("             XRULES_HR_VERIFICATION.CREATE_BY ");
				sql.append("         FROM  XRULES_HR_VERIFICATION ");
				sql.append("         WHERE XRULES_HR_VERIFICATION.PERSONAL_ID = ? ");
				sql.append("         AND XRULES_HR_VERIFICATION.SEQ = ");
				sql.append("             (SELECT MAX(HR_TMP.SEQ) FROM XRULES_HR_VERIFICATION HR_TMP WHERE HR_TMP.PERSONAL_ID = ? ) ");
				sql.append("     ) M ");
				sql.append(" ORDER BY  M.CREATE_DATE ");
				sql.append(" )X WHERE ROWNUM = 1 ");
			ps = conn.prepareStatement(sql.toString());
			int index = 1;
			ps.setString(index++, personalID);
			ps.setString(index++, personalID);
			ps.setString(index++, personalID);
			ps.setString(index++, personalID);
			
			rs = ps.executeQuery();
			if(rs.next()){
				updateBy = rs.getString("CREATE_BY");
			}
		}catch(Exception e){
			logger.error("Error >> ", e);
		}finally{
			try{
				closeConnection(conn, rs, ps);
			}catch(Exception e){
				logger.error("Error >> ", e);
			}
		}
		return updateBy;
	}
	
	@Override
	public HashMap<String, String> getTambol() throws EjbUtilException {
		PreparedStatement ps = null;
		ResultSet rs = null;Connection conn = null;
		HashMap<String, String> data = new HashMap<String, String>();
		try {
			conn = getConnection();
			StringBuilder sql = new StringBuilder("");
			sql.append(" SELECT SUB_DISTRICT_ID,SUB_DISTRICT_DESC FROM MS_SUB_DISTRICT ");
			String dSql = String.valueOf(sql);
			ps = conn.prepareStatement(dSql);
			rs = null;

			rs = ps.executeQuery();
			while(rs.next()) {
				data.put(rs.getString("SUB_DISTRICT_ID"), rs.getString("SUB_DISTRICT_DESC"));
			}
		} catch (Exception e) {
			logger.error("Error >> ", e);
			throw new EjbUtilException(e.getMessage());
		} finally {
			try {
				closeConnection(conn, rs, ps);
			} catch (Exception e) {
				logger.error("Error >> ", e);
			}
		}
		return data;
	}
	
	@Override
	public HashMap<String, String> getAmphur() throws EjbUtilException {
		PreparedStatement ps = null;
		ResultSet rs = null;
		HashMap<String, String> data = new HashMap<String, String>();Connection conn = null;
		try {
			conn = getConnection();
			StringBuilder sql = new StringBuilder("");
			sql.append(" SELECT DISTRICT_ID,DISTRICT_DESC FROM MS_DISTRICT ");
			String dSql = String.valueOf(sql);
			ps = conn.prepareStatement(dSql);
			rs = null;

			rs = ps.executeQuery();
			while(rs.next()) {
				data.put(rs.getString("DISTRICT_ID"), rs.getString("DISTRICT_DESC"));
			}
		} catch (Exception e) {
			logger.error("Error >> ", e);
			throw new EjbUtilException(e.getMessage());
		} finally {
			try {
				closeConnection(conn, rs, ps);
			} catch (Exception e) {
				logger.error("Error >> ", e);
			}
		}
		return data;
	}
	
	@Override
	public HashMap<String, String> getProvince() throws EjbUtilException {
		PreparedStatement ps = null;
		ResultSet rs = null;
		HashMap<String, String> data = new HashMap<String, String>();Connection conn = null;
		try {
			conn = getConnection();
			StringBuilder sql = new StringBuilder("");
			sql.append(" SELECT PROVINCE_ID,PROVINCE_DESC FROM MS_PROVINCE ");
			String dSql = String.valueOf(sql);
			ps = conn.prepareStatement(dSql);
			rs = ps.executeQuery();
			while(rs.next()) {
				data.put(rs.getString("PROVINCE_ID"), rs.getString("PROVINCE_DESC"));
			}
		} catch (Exception e) {
			logger.error("Error >> ", e);
			throw new EjbUtilException(e.getMessage());
		} finally {
			try {
				closeConnection(conn, rs, ps);
			} catch (Exception e) {
				logger.error("Error >> ", e);
			}
		}
		return data;
	}

	@Override
	public Vector<PLApplicationImageSplitDataM> getApplicationImageM(Vector<PLNCBDocDataM> ncbDocVect) throws EjbUtilException {
		Vector<PLApplicationImageSplitDataM> imageVect = new Vector<PLApplicationImageSplitDataM>();
		PreparedStatement ps = null;
		ResultSet rs = null;
		Connection conn = null;
		try{
			conn = getConnection();
			StringBuilder SQL = new StringBuilder("");
				SQL.append("SELECT * FROM ORIG_APPLICATION_IMG_SPLIT WHERE IMG_ID ");
				SQL.append(" IN (");
				int i = 0;
				for(PLNCBDocDataM imgM : ncbDocVect){
					if(i == 0){
						SQL.append("?");
					}else{
						SQL.append(",?");
					}
					i++;
				}
				SQL.append(")");
			logger.debug("SQL >> "+SQL);
			int index = 1;
			ps = conn.prepareStatement(SQL.toString());
			for(PLNCBDocDataM imgM : ncbDocVect){
				ps.setString(index++, imgM.getImgID());
			}
			rs = ps.executeQuery();
			while (rs.next()) {
				PLApplicationImageSplitDataM imageM = new PLApplicationImageSplitDataM();
					imageM.setImgID(rs.getString("IMG_ID"));
					imageM.setRealPath(rs.getString("REAL_PATH"));
					imageM.setFileName(rs.getString("FILE_NAME"));
					imageM.setFileType(rs.getString("FILE_TYPE"));
				imageVect.add(imageM);
			}
		}catch(Exception e){
			logger.fatal("ERROR ",e);
			throw new EjbUtilException(e.getLocalizedMessage());
		} finally {
			try {
				closeConnection(conn, rs, ps);
			} catch (Exception e) {
				logger.fatal("ERROR ",e);
				throw new EjbUtilException(e.getLocalizedMessage());
			}
		}
		return imageVect;
	}
	
	public void deleteOldSubmitIATimestamp(String appGroupNo, String idNo, int seconds) throws ApplicationException {
		PreparedStatement ps = null;
		Connection conn = null;
		try {
			OrigObjectDAO origObjectDAO = new OrigObjectDAO();
			conn = origObjectDAO.getConnection();
			StringBuilder sql = new StringBuilder("");
			sql.append(" DELETE FROM ORIG_SUBMIT_IA_TIMESTAMP ");
			sql.append(" WHERE IDNO = ? ");
			sql.append(" AND ((SYSDATE - SUBMIT_DATE) * 86400) > ? ");
//			sql.append(" AND (((SYSDATE - SUBMIT_DATE) * 86400) > ? OR APPLICATION_GROUP_NO = ?) ");
			String dSql = sql.toString();
			logger.debug("Sql=" + dSql);
			ps = conn.prepareStatement(dSql);
			ps.setString(1, idNo);
			ps.setInt(2, seconds);
//			ps.setString(3, appGroupNo);
			ps.executeUpdate();
		} catch (Exception e) {
			logger.fatal(e.getLocalizedMessage());
			throw new ApplicationException(e.getMessage());
		} finally {
			try {
				if(ps != null){ps.close();}
				if(conn != null){conn.close();}
			} catch (Exception e) {
				logger.fatal(e.getLocalizedMessage());
				throw new ApplicationException(e.getMessage());
			}
		}
	}
	
	public void deleteErrorSubmitIATimestamp(String idNo) throws ApplicationException {
		PreparedStatement ps = null;
		Connection conn = null;
		try {
			OrigObjectDAO origObjectDAO = new OrigObjectDAO();
			conn = origObjectDAO.getConnection();
			StringBuilder sql = new StringBuilder("");
			sql.append(" DELETE FROM ORIG_SUBMIT_IA_TIMESTAMP ");
			sql.append(" WHERE IDNO = ? ");
			String dSql = sql.toString();
			logger.debug("Sql=" + dSql);
			ps = conn.prepareStatement(dSql);
			ps.setString(1, idNo);
			ps.executeUpdate();

		} catch (Exception e) {
			logger.fatal(e.getLocalizedMessage());
			throw new ApplicationException(e.getMessage());
		} finally {
			try {
				if(ps != null){ps.close();}
				if(conn != null){conn.close();}
			} catch (Exception e) {
				logger.fatal(e.getLocalizedMessage());
				throw new ApplicationException(e.getMessage());
			}
		}
	}
	
	public void insertSubmitIATimestamp(String appGroupNo, String idNo) throws Exception {
		PreparedStatement ps = null;
		Connection conn = null;
		try {
			OrigObjectDAO origObjectDAO = new OrigObjectDAO();
			conn = origObjectDAO.getConnection();
			StringBuffer sql = new StringBuffer("");
			sql.append("INSERT INTO ORIG_SUBMIT_IA_TIMESTAMP(APPLICATION_GROUP_NO, IDNO, SUBMIT_DATE) ");
			sql.append(" VALUES(?, ?, TRUNC(SYSDATE,'MI')) ");
			String dSql = String.valueOf(sql);
			logger.debug("Sql=" + dSql);
			ps = conn.prepareStatement(dSql);
			int cnt = 1;
			ps.setString(cnt++, appGroupNo);
			ps.setString(cnt++, idNo);
			
			ps.executeUpdate();
		} catch (Exception e) {
			logger.fatal(e.getLocalizedMessage());
			throw e;
		} finally {
			try {
				if(ps != null){ps.close();}
				if(conn != null){conn.close();}
			} catch (Exception e) {
				logger.fatal(e.getLocalizedMessage());
			}
		}
	}
	
	public boolean isAppWaitForApprove(String idNo, String appGroupNo, ArrayList<String> jobStates, ArrayList<String> products, String cjdSource) throws Exception {
		boolean result = false;
		
		PreparedStatement ps = null;
		Connection conn = null;
		try {
			OrigObjectDAO origObjectDAO = new OrigObjectDAO();
			conn = origObjectDAO.getConnection();
			StringBuilder sql = new StringBuilder("");
			sql.append(" SELECT AG.APPLICATION_GROUP_ID ");
			sql.append(" FROM ORIG_PERSONAL_INFO PI ");
			sql.append(" INNER JOIN ORIG_PERSONAL_RELATION PR ON PR.PERSONAL_ID = PI.PERSONAL_ID ");
			sql.append(" INNER JOIN ORIG_APPLICATION A ON PR.REF_ID = A.APPLICATION_RECORD_ID ");
			if(null != products && products.contains("CC") && !products.contains("KEC") && !products.contains("KPL")) {
				sql.append(" AND SUBSTR(A.BUSINESS_CLASS_ID, 1, 2) = 'CC' ");
			} else if(null != products && !products.contains("CC") && (products.contains("KEC") || products.contains("KPL"))) {
				sql.append(" AND SUBSTR(A.BUSINESS_CLASS_ID, 1, 3) IN ('KEC', 'KPL') ");
			}
			sql.append(" INNER JOIN ORIG_APPLICATION_GROUP AG ON AG.APPLICATION_GROUP_ID = A.APPLICATION_GROUP_ID ");
			sql.append("     AND AG.JOB_STATE IN ( ");
			int jobStateSize = jobStates.size();
			for(int i = 0; i < jobStateSize; i++) {
				sql.append(" ?, ");
			}
			if(jobStateSize > 0) {
				sql.replace(sql.length() - 2, sql.length(), " ");
			}
			sql.append("     ) ");
			sql.append("     AND INSTR(?, AG.SOURCE) < 1 ");
			sql.append("     AND AG.APPLICATION_GROUP_NO <> ? ");
			sql.append(" WHERE PI.IDNO = ? AND PI.PERSONAL_TYPE IN (?, ?) ");
			String dSql = sql.toString();
			logger.debug("Sql=" + dSql);
			ps = conn.prepareStatement(dSql);
			int index = 1;
			for(String jobState : jobStates) {
				ps.setString(index++, jobState);
			}
			ps.setString(index++, cjdSource);
			ps.setString(index++, appGroupNo);
			ps.setString(index++, idNo);
			ps.setString(index++, OrigConstant.PERSONAL_TYPE_APPLICANT);
			ps.setString(index++, OrigConstant.PERSONAL_TYPE_SUP_CARD);
			
			ResultSet rs = ps.executeQuery();
			if(rs.next()){
				result = true;
			}
		} catch (Exception e) {
			logger.fatal(e.getLocalizedMessage());
			throw new ApplicationException(e.getMessage());
		} finally {
			try {
				if(ps != null){ps.close();}
				if(conn != null){conn.close();}
			} catch (Exception e) {
				logger.fatal(e.getLocalizedMessage());
				throw new ApplicationException(e.getMessage());
			}
		}
		return result;
	}
	
}
