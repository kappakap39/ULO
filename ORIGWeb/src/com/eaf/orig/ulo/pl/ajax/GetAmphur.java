package com.eaf.orig.ulo.pl.ajax;

import javax.servlet.http.HttpServletRequest;

import org.apache.log4j.Logger;

import com.eaf.orig.shared.model.SearchAddressDataM;
import com.eaf.orig.shared.servlet.AjaxDisplayGenerateInf;
import com.eaf.orig.shared.util.OrigUtil;
import com.eaf.orig.ulo.pl.app.dao.exception.PLOrigApplicationException;
import com.eaf.orig.ulo.pl.app.utility.HTMLRenderUtil;
import com.eaf.orig.ulo.pl.app.utility.JsonObjectUtil;
import com.eaf.orig.ulo.pl.service.PLORIGEJBService;
import com.eaf.orig.ulo.pl.util.ejb.ORIGDAOUtilLocal;

public class GetAmphur implements AjaxDisplayGenerateInf {
	Logger logger = Logger.getLogger(this.getClass());
	@Override
	public String getDisplayObject(HttpServletRequest request)throws PLOrigApplicationException {
		String province = request.getParameter("province");
		String amphur = request.getParameter("amphur");
		
		logger.debug("province >> "+province);
		logger.debug("amphur >> "+amphur);
		
		if(OrigUtil.isEmptyString(province) || OrigUtil.isEmptyString(amphur)){
			return null;
		}
		
		JsonObjectUtil jObj = new JsonObjectUtil();
		try{
			ORIGDAOUtilLocal origDaoBean =  PLORIGEJBService.getORIGDAOUtilLocal();				
			SearchAddressDataM addresM = origDaoBean.SearchAmphur(province, amphur);			
			if(null != addresM && !OrigUtil.isEmptyString(addresM.getAmphur())){
				jObj.CreateJsonObject("amphur",  HTMLRenderUtil.replaceNull(addresM.getAmphur()));
				jObj.CreateJsonObject("amphur_desc",  HTMLRenderUtil.replaceNull(addresM.getAmphur_desc()));
			}
		}catch (Exception e){
			logger.fatal("Exception ",e);
		}		
		
		return jObj.returnJson();
	}

}
