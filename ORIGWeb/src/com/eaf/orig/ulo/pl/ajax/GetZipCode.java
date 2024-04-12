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

public class GetZipCode  implements AjaxDisplayGenerateInf {
	Logger logger = Logger.getLogger(this.getClass());
	@Override
	public String getDisplayObject(HttpServletRequest request)throws PLOrigApplicationException{		
		
		String zipcode = request.getParameter("zipcode");	
		String province = request.getParameter("province");
		String amphur = request.getParameter("amphur");
		String tambol = request.getParameter("tambol");
		
		logger.debug("zipcode >> "+zipcode);
		logger.debug("province >> "+province);
		logger.debug("amphur >> "+amphur);
		logger.debug("tambol >> "+tambol);
		
		String searchZipCode = "N";
		
		if(null != zipcode && zipcode.length() == 5){
			if(OrigUtil.isEmptyString(province) && OrigUtil.isEmptyString(amphur) && OrigUtil.isEmptyString(tambol)){
				searchZipCode = "Y";
			}
		}
		
		if(!"Y".equals(searchZipCode)){
			if(OrigUtil.isEmptyString(zipcode) || OrigUtil.isEmptyString(province) 
						|| OrigUtil.isEmptyString(amphur) || OrigUtil.isEmptyString(tambol)){
				return null;
			}
		}
				
		try{
			ORIGDAOUtilLocal origDaoBean =  PLORIGEJBService.getORIGDAOUtilLocal();	
						
			SearchAddressDataM addresM = origDaoBean.SearchZipCode(zipcode, province, amphur, tambol);
			
			if(null != addresM && !OrigUtil.isEmptyString(addresM.getZipcode())){
				JsonObjectUtil json = new JsonObjectUtil();		
					json.CreateJsonObject("province", HTMLRenderUtil.replaceNull(addresM.getProvince()) );
					json.CreateJsonObject("province_desc",  HTMLRenderUtil.replaceNull(addresM.getProvince_desc()));
					json.CreateJsonObject("amphur",  HTMLRenderUtil.replaceNull(addresM.getAmphur()));
					json.CreateJsonObject("amphur_desc", HTMLRenderUtil.replaceNull(addresM.getAmphur_desc()));
					json.CreateJsonObject("tambol",  HTMLRenderUtil.replaceNull(addresM.getTambol()));
					json.CreateJsonObject("tambol_desc",  HTMLRenderUtil.replaceNull(addresM.getTambol_desc()));
				return json.returnJson();
			}
			
		}catch (Exception e) {
			logger.fatal("Exception ",e);
		}		
		return null;
	}

}
