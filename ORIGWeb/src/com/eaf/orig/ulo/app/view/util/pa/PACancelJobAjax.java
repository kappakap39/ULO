package com.eaf.orig.ulo.app.view.util.pa;
import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;

import javax.servlet.http.HttpServletRequest;

import org.apache.log4j.Logger;

import com.eaf.core.ulo.common.model.ResponseData;
import com.eaf.j2ee.pattern.control.AjaxInf;
import com.eaf.j2ee.pattern.control.ResponseDataController;
import com.eaf.orig.profile.model.UserDetailM;
import com.eaf.orig.shared.dao.OrigObjectDAO;

public class PACancelJobAjax  implements AjaxInf {
	private static transient Logger logger = Logger.getLogger(PACancelJobAjax.class);
	@Override
	public ResponseData processAction(HttpServletRequest request)
	{
		ResponseDataController responseData = new ResponseDataController(request,ResponseData.FunctionId.REALLOCATE_TASK);
		UserDetailM userM =	(UserDetailM) request.getSession().getAttribute("ORIGUser");
		PreparedStatement ps = null;
		Connection conn = null;		
		String userName = userM.getUserName();
		String appGroupNo = request.getParameter("appGroupNo");
		
		OrigObjectDAO orig = new OrigObjectDAO();
		Date datenow = new java.sql.Date(System.currentTimeMillis());
		
		try
		{
			conn = orig.getConnection();
			StringBuilder sql = new StringBuilder("");
			sql.append("UPDATE LOAN_SETUP_CLAIM SET ");
			sql.append(" LOAN_SETUP_STATUS = 'Cancelled' , UPDATE_BY = ? , UPDATE_DATE = ? ");
			sql.append(" WHERE APPLICATION_GROUP_NO = ?");
			
			int cnt=1;
			String dSql = String.valueOf(sql);
			ps = conn.prepareStatement(dSql);
			ps.setString(cnt++, userName);
			ps.setDate(cnt++, datenow);
			ps.setString(cnt++, appGroupNo);
			
			logger.debug("PACancelJobAjax by user: " + userName + " - appGroupNo: " + appGroupNo);
			ps.executeUpdate();
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
