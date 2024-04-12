package com.eaf.orig.ulo.app.view.util.pa;
import java.io.UnsupportedEncodingException;
import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;

import javax.servlet.http.HttpServletRequest;

import org.apache.log4j.Logger;

import com.eaf.core.ulo.common.model.ResponseData;
import com.eaf.core.ulo.common.properties.SystemConstant;
import com.eaf.j2ee.pattern.control.AjaxInf;
import com.eaf.j2ee.pattern.control.ResponseDataController;
import com.eaf.orig.profile.model.UserDetailM;
import com.eaf.orig.shared.dao.OrigObjectDAO;
import com.eaf.orig.ulo.constant.MConstant;

public class ResendLetterAjax  implements AjaxInf {
	private static transient Logger logger = Logger.getLogger(ResendLetterAjax.class);
	
	@Override
	public ResponseData processAction(HttpServletRequest request)
	{
		String RESEND_TYPE_EMAIL = SystemConstant.getConstant("RESEND_TYPE_EMAIL");
		String RESEND_TYPE_PAPER = SystemConstant.getConstant("RESEND_TYPE_PAPER");
		String RESEND_TYPE_EMAIL_STR = SystemConstant.getConstant("RESEND_TYPE_EMAIL_STR");
		String RESEND_TYPE_PAPER_STR = SystemConstant.getConstant("RESEND_TYPE_PAPER_STR");
		ResponseDataController responseData = new ResponseDataController(request,ResponseData.FunctionId.REALLOCATE_TASK);
		UserDetailM userM =	(UserDetailM) request.getSession().getAttribute("ORIGUser");
		PreparedStatement ps = null;
		Connection conn = null;		
		String userName = userM.getUserName();
		String letterId = request.getParameter("letterId");
		
		if (letterId != null)
		{
			try {
			letterId = java.net.URLDecoder.decode(letterId, "UTF-8");
			} 
			catch (UnsupportedEncodingException e) {}
		}
		
		String resendType = request.getParameter("resendType"); //01 EMAIL, 02 PAPER
		String resendEmail = request.getParameter("resendEmail");
		logger.debug("letterId = " + letterId);
		logger.debug("resendType = " + resendType);
		logger.debug("resendEmail = " + resendEmail);
		
		OrigObjectDAO orig = new OrigObjectDAO();
		
		try
		{
			//Update LETTER_HISTORY data
			Date datenow = new java.sql.Date(System.currentTimeMillis());
				
			conn = orig.getConnection();
			StringBuilder sql = new StringBuilder("");
			sql.append("UPDATE LETTER_HISTORY SET ");
			sql.append(" SEND_FLAG = ? , CUSTOMER_RESEND_SEND_METHOD = ? , CUSTOMER_RESEND_EMAIL_ADDRESS = ? , ");
			sql.append(" CUSTOMER_RESEND_DATE = ? , CUSTOMER_RESEND_BY = ? , ");
			sql.append(" CUSTOMER_SEND_FLAG = ? , CUSTOMER_RESEND_COUNT = (CUSTOMER_RESEND_COUNT + 1) ");
			sql.append(" WHERE LETTER_NUMBER = ?");
				
			int cnt=1;
			String dSql = String.valueOf(sql);
			ps = conn.prepareStatement(dSql);
			
			ps.setString(cnt++, MConstant.FLAG_Y);
			ps.setString(cnt++, (RESEND_TYPE_EMAIL_STR.equals(resendType)) ? RESEND_TYPE_EMAIL : RESEND_TYPE_PAPER);
			ps.setString(cnt++, (RESEND_TYPE_PAPER_STR.equals(resendType)) ? "" : resendEmail);
			ps.setDate(cnt++, datenow);
			ps.setString(cnt++, userName);
			ps.setString(cnt++, MConstant.FLAG_R);
			ps.setString(cnt++, letterId);
			logger.debug("dSql = " + dSql);
			logger.debug("ResendLetterAjax by user: " + userName + " - letter number: " + letterId);
			int count = ps.executeUpdate();
			logger.debug("update count = " + count);
			
			return responseData.success();
		}
		catch(Exception e)
		{
			logger.fatal("ERROR ",e);
			return responseData.error(e);
		}
		finally
		{
			try 
			{
				orig.closeConnection(conn, ps);
			} catch (Exception e) {
				logger.fatal(e.getLocalizedMessage());
			}
		}
	}
	
}
