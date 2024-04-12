package com.eaf.orig.ulo.pl.ajax;

import javax.servlet.http.HttpServletRequest;

import org.apache.log4j.Logger;

import com.eaf.orig.shared.dao.utility.UtilityDAO;
import com.eaf.orig.shared.dao.utility.UtilityDAOImpl;
import com.eaf.orig.shared.servlet.AjaxDisplayGenerateInf;
import com.eaf.orig.shared.util.OrigUtil;
import com.eaf.orig.shared.utility.ORIGCacheUtil;
import com.eaf.orig.ulo.pl.app.dao.exception.PLOrigApplicationException;
import com.eaf.orig.ulo.pl.app.utility.JsonObjectUtil;

public class GetSellLogic implements AjaxDisplayGenerateInf {
	static Logger logger = Logger.getLogger(GetSellLogic.class);
	@Override
	public String getDisplayObject(HttpServletRequest request)throws PLOrigApplicationException {
		
		String attr_code = request.getParameter("attr_code");
		String attr_desc = request.getParameter("attr_desc");
		
		String branch_code = request.getParameter("branch_code");
		String sale_code = request.getParameter("sale_code");
		String channel   = request.getParameter("channel");
		
		if(OrigUtil.isEmptyString(sale_code)){
			return null;
		}

		UtilityDAO utilDAO = new UtilityDAOImpl();
		String saleDesc = null;
		
		if("seller_title".equalsIgnoreCase(attr_code)){
			ORIGCacheUtil oricCache = new ORIGCacheUtil();
			String channel_group = oricCache.getSystemIDFromListbox(channel,"50","2");
			saleDesc = utilDAO.getSellerName(branch_code,sale_code,channel_group,channel);
		}else{
			saleDesc = utilDAO.getSellerNameByBranch(branch_code, sale_code, null);		
		}
		
		if(OrigUtil.isEmptyString(saleDesc)){
			return null;
		}

		JsonObjectUtil json = new JsonObjectUtil();	
			json.CreateJsonObject(attr_code,sale_code);
			json.CreateJsonObject(attr_desc,saleDesc);				
				
		return json.returnJson();
	}

}
