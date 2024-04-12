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

public class GetProvince implements AjaxDisplayGenerateInf  {
	Logger logger = Logger.getLogger(this.getClass());
	@Override
	public String getDisplayObject(HttpServletRequest request)throws PLOrigApplicationException {
		
		String province = request.getParameter("province");	
		logger.debug("province >> "+province);
		
		if(OrigUtil.isEmptyString(province)){
			return null;
		}
		
		JsonObjectUtil jObj = new JsonObjectUtil();
		try{
			ORIGDAOUtilLocal origDaoBean =  PLORIGEJBService.getORIGDAOUtilLocal();				
			SearchAddressDataM addresM = origDaoBean.SearchProvince(province);			
			if(null != addresM && !OrigUtil.isEmptyString(addresM.getProvince())){
				jObj.CreateJsonObject("province",  HTMLRenderUtil.replaceNull(addresM.getProvince()));
				jObj.CreateJsonObject("province_desc",  HTMLRenderUtil.replaceNull(addresM.getProvince_desc()));
			}
		}catch (Exception e){
			logger.fatal("Exception ",e);
		}		
		
		return jObj.returnJson();
	}

}
