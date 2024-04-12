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

public class PACompleteJobAjax  implements AjaxInf {
	private static transient Logger logger = Logger.getLogger(PACompleteJobAjax.class);
	@Override
	public ResponseData processAction(HttpServletRequest request)
	{
		ResponseDataController responseData = new ResponseDataController(request,"PACompleteJobAjax");
		UserDetailM userM =	(UserDetailM) request.getSession().getAttribute("ORIGUser");
		PreparedStatement ps = null;
		Connection conn = null;		
		String userName = userM.getUserName();
		String claimId = request.getParameter("claimId");
		
		OrigObjectDAO orig = new OrigObjectDAO();
		Date datenow = new java.sql.Date(System.currentTimeMillis());
		
		try
		{
			conn = orig.getConnection();
			StringBuilder sql = new StringBuilder("");
			sql.append("UPDATE LOAN_SETUP_CLAIM SET COMPLETE_FLAG = 'Y', ");
			sql.append(" LOAN_SETUP_STATUS = 'Completed' , COMPLETE_BY= ? , COMPLETE_DATE = ? ");
			sql.append(" , UPDATE_BY = ? , UPDATE_DATE= ? ");
			sql.append(" WHERE CLAIM_ID = ?");
			
			int cnt=1;
			String dSql = String.valueOf(sql);
			ps = conn.prepareStatement(dSql);
			ps.setString(cnt++, userName);
			ps.setDate(cnt++, datenow);
			ps.setString(cnt++, userName);
			ps.setDate(cnt++, datenow);
			ps.setString(cnt++, claimId);
			
			logger.debug("PACompleteJobAjax by user: " + userName + " - claimId: " + claimId);
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
