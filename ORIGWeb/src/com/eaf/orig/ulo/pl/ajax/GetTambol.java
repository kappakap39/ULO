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

public class GetTambol implements AjaxDisplayGenerateInf {
	Logger logger = Logger.getLogger(this.getClass());
	@Override
	public String getDisplayObject(HttpServletRequest request)throws PLOrigApplicationException {

		String province = request.getParameter("province");
		String amphur = request.getParameter("amphur");
		String tambol = request.getParameter("tambol");
		String zipcode = request.getParameter("zipcode");
		
		logger.debug("province >> "+province);
		logger.debug("amphur >> "+amphur);
		logger.debug("tambol >> "+tambol);
		logger.debug("zipcode >> "+zipcode);
		
		if(OrigUtil.isEmptyString(province) || OrigUtil.isEmptyString(amphur)
				|| OrigUtil.isEmptyString(tambol)){
			return null;
		}
		
		JsonObjectUtil json = new JsonObjectUtil();
		try{
			ORIGDAOUtilLocal origDaoBean =  PLORIGEJBService.getORIGDAOUtilLocal();				
			SearchAddressDataM addresM = origDaoBean.SearchTambol(province, amphur, tambol);			
			if(null != addresM && !OrigUtil.isEmptyString(addresM.getTambol())){
				if(addresM.getNum() == 1){
					String $zipcode = "";
					if(null != addresM.getZipcode()){
						$zipcode = addresM.getZipcode().replaceAll("\\|", "");
					}
					json.CreateJsonObject("zipcode",  HTMLRenderUtil.replaceNull($zipcode));
				}else{
					String $zipcode = "";
					if(!OrigUtil.isEmptyString(zipcode)){
						String $object = addresM.getZipcode();
						if(null != $object){
							String[] object = $object.split("\\|");
							for (String string : object) {
								if(zipcode.equals(string)){
									$zipcode = string;
									break;
								}
							}
						}
					}else{
						$zipcode = "";
					}
					json.CreateJsonObject("zipcode",  HTMLRenderUtil.replaceNull($zipcode));
				}
				json.CreateJsonObject("tambol",  HTMLRenderUtil.replaceNull(addresM.getTambol()));
				json.CreateJsonObject("tambol_desc",  HTMLRenderUtil.replaceNull(addresM.getTambol_desc()));
			}
		}catch (Exception e){
			logger.fatal("Exception ",e);
		}		
		
		return json.returnJson();		
	}

}
