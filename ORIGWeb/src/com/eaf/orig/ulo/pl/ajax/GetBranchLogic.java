package com.eaf.orig.ulo.pl.ajax;

import java.util.Vector;

import javax.servlet.http.HttpServletRequest;

import org.apache.log4j.Logger;

import com.eaf.orig.cache.properties.ORIGCacheDataM;
import com.eaf.orig.shared.constant.OrigConstant;
import com.eaf.orig.shared.dao.utility.UtilityDAO;
import com.eaf.orig.shared.dao.utility.UtilityDAOImpl;
import com.eaf.orig.shared.servlet.AjaxDisplayGenerateInf;
import com.eaf.orig.shared.util.OrigUtil;
import com.eaf.orig.shared.utility.ORIGCacheUtil;
import com.eaf.orig.ulo.pl.app.dao.exception.PLOrigApplicationException;
import com.eaf.orig.ulo.pl.app.utility.JsonObjectUtil;
import com.eaf.orig.ulo.pl.service.PLORIGEJBService;
import com.eaf.orig.ulo.pl.util.ejb.ORIGDAOUtilLocal;

public class GetBranchLogic implements AjaxDisplayGenerateInf {
	static Logger logger = Logger.getLogger(GetBranchLogic.class);
	@Override
	public String getDisplayObject(HttpServletRequest request)throws PLOrigApplicationException {
		
		String attr_code = request.getParameter("attr_code");
		String attr_desc = request.getParameter("attr_desc");
		String attr_coderef = request.getParameter("attr_coderef");
		String attr_descref = request.getParameter("attr_descref");
		
		String branch_code = request.getParameter("branch_code");
		String sale_code = request.getParameter("sale_code");
		String channel   = request.getParameter("channel");
		
		logger.debug("branch_code >> "+branch_code);
		
		if(OrigUtil.isEmptyString(branch_code)){
			return null;
		}
		ORIGCacheUtil oricCache = new ORIGCacheUtil();
		String description = oricCache.getORIGCacheDisplayNameFormDB(branch_code,OrigConstant.CacheName.CACHE_NAME_BRANCHINFO);
		
		if(OrigUtil.isEmptyString(description)){
			return null;
		}
		
		JsonObjectUtil json = new JsonObjectUtil();			
			json.CreateJsonObject(attr_code,branch_code);
			json.CreateJsonObject(attr_desc,description);
			
		if(OrigUtil.isEmptyString(sale_code)){
			json.CreateJsonObject(attr_coderef,"");
			json.CreateJsonObject(attr_descref,"");
		}else{		
			
			logger.debug("channel >> "+channel);
			
			if(!foundChannel(branch_code, channel)){
				channel = null;
			}
			
			UtilityDAO utilDAO = new UtilityDAOImpl();
			String saleDesc = null;
			
			if("seller_title".equalsIgnoreCase(attr_coderef)){
				String channel_group = oricCache.getSystemIDFromListbox(channel,"50","2");
				saleDesc = utilDAO.getSellerName(branch_code,sale_code,channel_group,channel);
				logger.debug("saleDesc >> "+saleDesc);
			}else{
				saleDesc = utilDAO.getSellerNameByBranch(branch_code, sale_code, null);					
			}
			
			if(!OrigUtil.isEmptyString(saleDesc)){
				json.CreateJsonObject(attr_coderef,sale_code);
				json.CreateJsonObject(attr_descref,saleDesc);
			}else{
				json.CreateJsonObject(attr_coderef,"");
				json.CreateJsonObject(attr_descref,"");
			}
		}
		return json.returnJson();
	}
	private boolean foundChannel(String branch_code,String channel){
		ORIGDAOUtilLocal origBean = PLORIGEJBService.getORIGDAOUtilLocal();
		String group = origBean.getBranchGroupByBranchNo(branch_code);		
		ORIGCacheUtil origcUtil = new ORIGCacheUtil();
		Vector vChannel = (Vector)origcUtil.getvListboxbyFieldID(50);
		if(null != vChannel){
			for(ORIGCacheDataM dataM :(Vector<ORIGCacheDataM>) vChannel){
				if(null != dataM.getSystemID1() && dataM.getSystemID1().equals(group)
							&& dataM.getCode().equals(channel)){
					return true;
				}
			}
		}
		return false;
	}
}
