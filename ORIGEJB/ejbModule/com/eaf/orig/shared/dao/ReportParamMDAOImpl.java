package com.eaf.orig.shared.dao;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.Vector;
import com.eaf.orig.master.shared.model.ReportParam;
import com.eaf.orig.ulo.pl.app.dao.ReportParamMDAO;

public class ReportParamMDAOImpl extends OrigObjectDAO implements ReportParamMDAO {

	@Override
	public Vector<ReportParam> getReportParamM() {
		Vector<ReportParam> vReportparam = new Vector<ReportParam>();
		PreparedStatement ps = null;
		ResultSet rs = null;
		Connection conn = null;
		try{
			conn = getConnection();
			StringBuffer sql = new StringBuffer("");
			sql.append("SELECT * FROM MS_REPORT_PARAM ");
			sql.append("WHERE PARAM_TYPE='EXP_REPORT_PATH' ");
			sql.append("ORDER BY PARAM_CODE ");
			String dSql = String.valueOf(sql);
			ps = conn.prepareCall(dSql);
			rs = ps.executeQuery();
			
			while(rs.next()){
				ReportParam reportparam = new ReportParam();
				reportparam.setParamType(rs.getString(1));
				reportparam.setParamCode(rs.getString(2));
				reportparam.setParamValue(rs.getString(3));
				reportparam.setParamValue2(rs.getString(4));
				reportparam.setCreateDate(rs.getDate(5));
				reportparam.setCreateBy(rs.getString(6));
				reportparam.setUpdateDate(rs.getDate(7));
				reportparam.setUpdateBy(rs.getString(8));
				reportparam.setRemark(rs.getString(9));
				vReportparam.add(reportparam);
			}
			
			
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			try {
				closeConnection(conn, ps);
			} catch (Exception e) {
				logger.fatal(e.getLocalizedMessage());

			}
		}
		return vReportparam;

	}

	@Override
	public Vector<ReportParam> getReportParamM(String paramType) {
		Vector<ReportParam> vReportparam = new Vector<ReportParam>();
		PreparedStatement ps = null;
		ResultSet rs = null;
		Connection conn = null;
		try {
			//conn = Get Connection
			conn = getConnection();
			StringBuffer sql = new StringBuffer("");
			sql.append("SELECT rp.param_type, rp.param_code, rp.param_value ");
			sql.append(",pka_util.gen_report_input_4_crystal(rp.param_value2,rp.param_value3) param_value2 ");
			sql.append(",rp.create_date, rp.create_by, rp.update_date, rp.update_by, rp.remark ");
			sql.append("FROM MS_REPORT_PARAM rp ");
			sql.append("WHERE rp.PARAM_TYPE=? ");
			sql.append("ORDER BY rp.PARAM_CODE ");
			
			String dSql = String.valueOf(sql);
			logger.debug("@@@@@ getReportParamM SQL:"+dSql+"|paramType:"+ paramType);
			
			ps = conn.prepareCall(dSql);
			ps.setString(1, paramType);
			rs = ps.executeQuery();
			while(rs.next()){
				ReportParam reportparam = new ReportParam();
				reportparam.setParamType(rs.getString("PARAM_TYPE"));
				reportparam.setParamCode(rs.getString("PARAM_CODE"));
				reportparam.setParamValue(rs.getString("PARAM_VALUE"));
				reportparam.setParamValue2(rs.getString("PARAM_VALUE2"));
				reportparam.setCreateDate(rs.getDate("CREATE_DATE"));
				reportparam.setCreateBy(rs.getString("CREATE_BY"));
				reportparam.setUpdateDate(rs.getDate("UPDATE_DATE"));
				reportparam.setUpdateBy(rs.getString("UPDATE_BY"));
				reportparam.setRemark(rs.getString("REMARK"));
				vReportparam.add(reportparam);
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			try {
				closeConnection(conn, ps);
			} catch (Exception e) {
				logger.fatal(e.getLocalizedMessage());

			}
		}
		return vReportparam;
	}

	@Override
	public ReportParam getReportParamM(String paramType, String paramCode) {
		ReportParam reportParam = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		Connection conn = null;
		try {
			//conn = Get Connection
			conn = getConnection();
			StringBuffer sql = new StringBuffer("");
			sql.append("SELECT * FROM MS_REPORT_PARAM ");
			sql.append("WHERE PARAM_TYPE=? and PARAM_CODE=?");
			
			String dSql = String.valueOf(sql);
			logger.debug("@@@@@ getReportParamM SQL:"+dSql);
			
			ps = conn.prepareCall(dSql);
			ps.setString(1, paramType);
			ps.setString(2, paramCode);
			rs = ps.executeQuery();
			if(rs.next()){
				reportParam = new ReportParam();
				reportParam.setParamType(rs.getString("PARAM_TYPE"));
				reportParam.setParamCode(rs.getString("PARAM_CODE"));
				reportParam.setParamValue(rs.getString("PARAM_VALUE"));
				reportParam.setParamValue2(rs.getString("PARAM_VALUE2"));
				reportParam.setCreateDate(rs.getDate("CREATE_DATE"));
				reportParam.setCreateBy(rs.getString("CREATE_BY"));
				reportParam.setUpdateDate(rs.getDate("UPDATE_DATE"));
				reportParam.setUpdateBy(rs.getString("UPDATE_BY"));
				reportParam.setRemark(rs.getString("REMARK"));
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			try {
				closeConnection(conn, ps);
			} catch (Exception e) {
				logger.fatal(e.getLocalizedMessage());

			}
		}
		return reportParam;
	}

	@Override
	public ArrayList<String> getRejectLetterTemplate() {
		ArrayList<String> templateArray = new ArrayList<String>();
		PreparedStatement ps = null;
		ResultSet rs = null;
		Connection conn = null;
		try {
			//conn = Get Connection
			conn = getConnection();
			StringBuffer sql = new StringBuffer("");
			
			sql.append("select distinct template_id from reject_letter_template");
			String dSql = String.valueOf(sql);
			logger.debug("@@@@@ getRejectLetterTemplate Sql=" + dSql);
			ps = conn.prepareStatement(dSql);
			rs = null;

			rs = ps.executeQuery();

			while(rs.next()) {
				templateArray.add(rs.getString("template_id"));
			}

		} catch (Exception e) {
			logger.fatal("",e);
		} finally {
			try {
				closeConnection(conn, rs, ps);
			} catch (Exception e) {
				logger.fatal("",e);
			}
		}
		return templateArray;
	}

	@Override
	/*
	 * prepare reject transaction and return total file amount
	 */
	public void prepareRejectLetter(String reportDate) {
		CallableStatement callStmt = null;
//		ResultSet rs = null;
		Connection conn = null;
		try {
			//conn = Get Connection
			conn = getConnection();
			StringBuffer sql = new StringBuffer("");
			
			sql.append("{call pka_reject_letter.prepare_reject_letter(?) }");
					
			String dSql = String.valueOf(sql);
			logger.debug("@@@@@ prepareRejectLetter Sql=" + dSql + "| reportDate:" + reportDate);
			callStmt = conn.prepareCall(dSql);
			callStmt.setString(1, reportDate);
			callStmt.execute();
		} catch (Exception e) {
			logger.fatal("",e);
		} finally {
			try {
				closeConnection(conn, callStmt);
			} catch (Exception e) {
				logger.fatal("",e);
			}
		}
	}

	@Override
	public void insertInterfaceLog(String moduleId, String logType, String logCode, String message, String createBy, String refId) {
		PreparedStatement ps = null;
		Connection conn = null;
		try {
			//conn = Get Connection
			conn = getConnection();
			StringBuffer sql = new StringBuffer("");
			
			sql.append("insert into inf_log (module_id, log_type, log_code, message, create_date, create_by, ref_id) ");
			sql.append("values(?,?,?,?,sysdate,?,?)");
			String dSql = String.valueOf(sql);
			logger.debug("@@@@@ insertInterfaceLog Sql=" + dSql);
			ps = conn.prepareStatement(dSql);
			ps.setString(1, moduleId);
			ps.setString(2, logType);
			ps.setString(3, logCode);
			ps.setString(4, message);
			ps.setString(5, createBy);
			ps.setString(6, refId);
			ps.executeUpdate();

		} catch (Exception e) {
			logger.fatal("",e);
		} finally {
			try {
				closeConnection(conn, ps);
			} catch (Exception e) {
				logger.fatal("",e);
			}
		}
	}

	@Override
	public int getMaxFileOfTemplate(String template) {
		PreparedStatement ps = null;
		ResultSet rs = null;
		int maxFile = 0;
		Connection conn = null;
		try {
			//conn = Get Connection
			conn = getConnection();
			StringBuffer sql = new StringBuffer("");
			sql.append("select nvl(max(rt.file_no),0) max_file from reject_letter_trans rt where rt.template_id = ?");
			
			String dSql = String.valueOf(sql);
			logger.debug("@@@@@ getMaxFileOfTemplate SQL:"+dSql);
			
			ps = conn.prepareCall(dSql);
			ps.setString(1, template);
			rs = ps.executeQuery();
			if(rs.next()){
				maxFile = rs.getInt("max_file");
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			try {
				closeConnection(conn, ps);
			} catch (Exception e) {
				logger.fatal(e.getLocalizedMessage());

			}
		}
		return maxFile;
	}

}
