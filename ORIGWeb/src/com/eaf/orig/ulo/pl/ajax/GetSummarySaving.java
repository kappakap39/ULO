package com.eaf.orig.ulo.pl.ajax;

import java.math.BigDecimal;

import javax.servlet.http.HttpServletRequest;

import org.apache.log4j.Logger;

import com.eaf.orig.shared.servlet.AjaxDisplayGenerateInf;
import com.eaf.orig.shared.util.OrigUtil;
import com.eaf.orig.ulo.pl.app.dao.exception.PLOrigApplicationException;
import com.eaf.orig.ulo.pl.app.utility.DataFormatUtility;
import com.eaf.orig.ulo.pl.app.utility.JsonObjectUtil;

public class GetSummarySaving implements AjaxDisplayGenerateInf {

	@Override
	public String getDisplayObject(HttpServletRequest request) throws PLOrigApplicationException {
		Logger logger = Logger.getLogger(GetSummarySaving.class);
		
		String saving_sum_val = request.getParameter("saving_sum_val");
		String subtractVal = request.getParameter("subtractVal");
		
		logger.debug("saving_sum_val= "+saving_sum_val);
		logger.debug("subtractVall= "+subtractVal);
		
		BigDecimal saving_sum_valBig = null;
		BigDecimal subtractValBig = null;
		
		if(!OrigUtil.isEmptyString(saving_sum_val)){ saving_sum_valBig = DataFormatUtility.StringToBigDecimal(saving_sum_val);}
		if(!OrigUtil.isEmptyString(subtractVal)){ subtractValBig = DataFormatUtility.StringToBigDecimal(subtractVal);}
		
		
		
		BigDecimal saving_result = saving_sum_valBig.subtract(subtractValBig);
		logger.debug("saving_result="+saving_result);
		
		JsonObjectUtil jsonObj = new JsonObjectUtil();
		if(saving_result.compareTo(new BigDecimal(0.0))>0){jsonObj.CreateJsonObject("saving_summary",DataFormatUtility.displayBigDecimal(saving_result));}
		else{jsonObj.CreateJsonObject("saving_summary","0.00");}
		
		return jsonObj.returnJson();
	}

}
