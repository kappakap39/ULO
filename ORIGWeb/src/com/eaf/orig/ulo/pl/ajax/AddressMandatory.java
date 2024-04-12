package com.eaf.orig.ulo.pl.ajax;

import javax.servlet.http.HttpServletRequest;

import org.apache.log4j.Logger;

import com.eaf.orig.shared.model.SearchAddressDataM;
import com.eaf.orig.shared.servlet.AjaxDisplayGenerateInf;
import com.eaf.orig.shared.util.OrigUtil;
import com.eaf.orig.ulo.pl.app.dao.exception.PLOrigApplicationException;
import com.eaf.orig.ulo.pl.app.utility.JsonObjectUtil;
import com.eaf.orig.ulo.pl.service.PLORIGEJBService;
import com.eaf.orig.ulo.pl.util.ejb.ORIGDAOUtilLocal;

public class AddressMandatory implements AjaxDisplayGenerateInf{
	Logger logger = Logger.getLogger(this.getClass());
	@Override
	public String getDisplayObject(HttpServletRequest request)throws PLOrigApplicationException {
		
		String zipcode = request.getParameter("zipcode");	
		String province = request.getParameter("province");
		String amphur = request.getParameter("amphur");
		String tambol = request.getParameter("tambol");
		
		String textbox_id = request.getParameter("textbox_id");
		
		logger.debug("textbox_id >> "+textbox_id);
		logger.debug("zipcode >> "+zipcode);
		logger.debug("province >> "+province);
		logger.debug("amphur >> "+amphur);
		logger.debug("tambol >> "+tambol);
		
		JsonObjectUtil jObj = new JsonObjectUtil();	
		
		try{
			ORIGDAOUtilLocal origDaoBean =  PLORIGEJBService.getORIGDAOUtilLocal();	
						
			SearchAddressDataM addresM = origDaoBean.MandatoryAddress(zipcode, province, amphur, tambol);
			
			if(null == addresM || OrigUtil.isEmptyString(addresM.getZipcode())){
				if("province".equals(textbox_id)){				
					jObj.CreateJsonObject("amphur","");
					jObj.CreateJsonObject("amphur_desc","");
					jObj.CreateJsonObject("tambol","");
					jObj.CreateJsonObject("tambol_desc","");
					jObj.CreateJsonObject("zipcode","");
				}else if("amphur".equals(textbox_id)){
					jObj.CreateJsonObject("tambol","");
					jObj.CreateJsonObject("tambol_desc","");
					jObj.CreateJsonObject("zipcode","");
				}
			}
			
		}catch (Exception e) {
			logger.fatal("Exception ",e);
		}
		
		return jObj.returnJson();
		
	}
	
}
