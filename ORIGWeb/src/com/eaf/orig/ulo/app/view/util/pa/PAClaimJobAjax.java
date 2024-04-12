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

public class PAClaimJobAjax  implements AjaxInf {
	private static transient Logger logger = Logger.getLogger(PAClaimJobAjax.class);
	@Override
	public ResponseData processAction(HttpServletRequest request)
	{
		ResponseDataController responseData = new ResponseDataController(request,ResponseData.FunctionId.REALLOCATE_TASK);
		UserDetailM userM =	(UserDetailM) request.getSession().getAttribute("ORIGUser");
		PreparedStatement ps = null;
		Connection conn = null;		
		String userName = userM.getUserName();
		String claimId = request.getParameter("claimId");
		String claimFlag = request.getParameter("claimFlag");
		String isClaimed = request.getParameter("isClaimed");
		boolean claimFlagB = claimFlag.equalsIgnoreCase("true");
		boolean isClaimedB = isClaimed.equalsIgnoreCase("true");
		
		OrigObjectDAO orig = new OrigObjectDAO();
		Date datenow = new java.sql.Date(System.currentTimeMillis());
		
		try
		{
			conn = orig.getConnection();
			StringBuilder sql = new StringBuilder("");
			sql.append("UPDATE LOAN_SETUP_CLAIM SET CLAIM_FLAG = ?, ");
			sql.append(" LOAN_SETUP_STATUS = ?, CLAIM_BY= ? , CLAIM_DATE = ? ");
			
			if(claimFlagB && !isClaimedB)
			{
				//Claim
			}
			else if(!claimFlagB && isClaimedB)
			{
				//Unclaim
				sql.append(" , UN_CLAIM_FLAG_BY = ? , UN_CLAIM_FLAG_DATE = ? ");
			}
			else
			{
				//Case (!claimFlagB && !isClaimedB)
				//Case (claimFlagB && isClaimedB)
				return responseData.success();
			}
			
			sql.append(" WHERE CLAIM_ID = ?");
			
			int cnt=1;
			String dSql = String.valueOf(sql);
			ps = conn.prepareStatement(dSql);
			ps.setString(cnt++, claimFlagB ? "Y" : "N");
			ps.setString(cnt++, claimFlagB ? "Claimed" : "Not Claim");
			if(!claimFlagB && isClaimedB)
			{
				ps.setString(cnt++, null);
				ps.setDate(cnt++, null);
				ps.setString(cnt++, userName);
				ps.setDate(cnt++, datenow);
			}
			else
			{
				ps.setString(cnt++, userName);
				ps.setDate(cnt++, datenow);
			}
			ps.setString(cnt++, claimId);
			logger.debug("PAClaimJobAjax by user: " + userName + " - claimId: " + claimId);
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
